package org.xtuml.bp.ui.canvas.references;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.xtuml.bp.core.Modeleventnotification_c;
import org.xtuml.bp.core.common.Activepoller_c;
import org.xtuml.bp.core.common.AttributeChangeModelDelta;
import org.xtuml.bp.core.common.IModelDelta;
import org.xtuml.bp.core.common.ITransactionListener;
import org.xtuml.bp.core.common.ModelChangeAdapter;
import org.xtuml.bp.core.common.ModelChangedEvent;
import org.xtuml.bp.core.common.ModelElement;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.paths.ElementNameChangeListener;
import org.xtuml.bp.ui.canvas.CanvasPlugin;
import org.xtuml.bp.ui.canvas.Objectreference_c;
import org.xtuml.bp.ui.canvas.Objectreferenceidentifyingattribute_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.Referencepath_c;
import org.xtuml.bp.ui.canvas.persistence.IGraphicalWriter;

public class ReferencePathNameChangeListener extends ModelChangeAdapter
		implements ElementNameChangeListener, ITransactionListener {

	private static Map<Object, Referencepath_c> managing = new HashMap<>();

	@Override
	public void nameChanged(ModelElement modelElement, Object newValue, Object oldValue) {
		NonRootModelElement nrme = (NonRootModelElement) modelElement;
		/**
		 * Capture any paths that reference the element with name changed
		 */
		for (Object managed : managing.keySet()) {
			Referencepath_c path = managing.get(managed);
			Objectreference_c[] objectRefs = Objectreference_c.getManyR_ORsOnR500(path,
					or -> ((Objectreference_c) or).getElement().equals(nrme));
			for (Objectreference_c objectRef : objectRefs) {
				/**
				 * Update the old/new values for the identifying attribute and collect this
				 * path's root pmc
				 */
				Objectreferenceidentifyingattribute_c oria = Objectreferenceidentifyingattribute_c
						.getOneR_ORIAOnR501(objectRef);
				oria.Attributechanged(true, (String) newValue, (String) oldValue);
				Activepoller_c.Oneshot();
			}
		}
	}

	@Override
	public void modelElementDeleted(ModelChangedEvent event, IModelDelta delta) {
		// clean up path references
		Referencepath_c path = managing.get(delta.getModelElement());
		if (path != null) {
			path.Dispose();
		}
	}

	@Override
	public boolean isBatchedNotificationEnabled() {
		return false;
	}

	public static void remove(Referencepath_c path) {
		managing.remove(path.getElement());
	}

	public static void add(Referencepath_c path) {
		managing.put(path.getElement(), path);
	}

	@Override
	public void transactionEnded(Transaction transaction) {
		// we want to update graphical files after xtuml persistence
		IModelDelta[] deltas = transaction.getDeltas(Ooaofgraphics.getDefaultInstance());
		if (deltas != null) {
			List<NonRootModelElement> written = new ArrayList<>();
			Stream.of(deltas)
					.filter(d -> d.getKind() == Modeleventnotification_c.DELTA_ATTRIBUTE_CHANGE
							&& d.getModelElement() instanceof Objectreferenceidentifyingattribute_c
							&& ((AttributeChangeModelDelta) d).getAttributeName().equals("Value"))
					.forEach(delta -> {
						Objectreferenceidentifyingattribute_c oria = (Objectreferenceidentifyingattribute_c) delta
								.getModelElement();
						Referencepath_c path = Referencepath_c
								.getOneR_RPOnR500(Objectreference_c.getManyR_ORsOnR501(oria));
						Objectreference_c ref = Objectreference_c.getOneR_OROnR501(oria);
						if (path != null) {
							NonRootModelElement pathElement = (NonRootModelElement) path.getElement();
							// if this identifying attribute's element matches the path element we need
							// to rename the graphics file if the element is also the root of the PMC
							boolean requiresFileRename = pathElement.equals(ref.getElement())
									&& pathElement.getPersistableComponent().getRootModelElement() == pathElement;
							/**
							 * Rewrite the graphical data that is associated with the element name change
							 */
							CanvasPlugin.getDefault().getPersistenceExtensionRegistry().getExtensions().forEach(pe -> {
								IGraphicalWriter writer = pe.getWriter();
								if (writer != null) {
									if (!requiresFileRename && !written.contains(path.getElement())) {
										writer.write(((NonRootModelElement) path.getElement()).getPersistableComponent()
												.getRootModelElement());
										written.add(pathElement);
									}
									if (requiresFileRename && !written.contains(path.getElement())) {
										writer.nameChange((NonRootModelElement) path.getElement(),
												oria.getPrevious_value());
										writer.write((NonRootModelElement) path.getElement());
										written.add((NonRootModelElement) path.getElement());
										// write the parent as well
										writer.write(PersistenceManager.getHierarchyMetaData().getParent(pathElement));
										written.add(PersistenceManager.getHierarchyMetaData().getParent(pathElement));
									}
								}
							});
						}
					});
		}
	}

}
