package org.xtuml.bp.ui.canvas.persistence;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.InterfaceReference_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.common.IModelDelta;
import org.xtuml.bp.core.common.ITransactionListener;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.RelationshipChangeModelDelta;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.ui.canvas.CanvasPlugin;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;

public class WriteTransactionListener implements ITransactionListener {

	@Override
	public void transactionEnded(Transaction transaction) {
		PersistenceExtensionRegistry persistenceExtensionRegistry = CanvasPlugin.getDefault()
				.getPersistenceExtensionRegistry();
		persistenceExtensionRegistry.registeredExtensions.stream().forEach(pe -> {
			IGraphicalWriter writer = pe.getWriter();
			// we are interested in Ooaofgraphics changes
			Set<PersistableModelComponent> collectedComponents = new HashSet<>();
			IModelDelta[] deltas = transaction.getDeltas(Ooaofgraphics.getDefaultInstance());
			if (deltas != null) {
				Stream.of(deltas).forEach(delta -> {
					PersistableModelComponent pmc = ((NonRootModelElement) delta.getModelElement())
							.getPersistableComponent();
					if (pmc != null) {
						collectedComponents.add(pmc);
					}
				});
			}
			// some ooaofooa changes modify the path link to elements
			IModelDelta[] coreDeltas = transaction.getDeltas(Ooaofooa.getDefaultInstance());
			if (coreDeltas != null) {
				Stream.of(coreDeltas).forEach(delta -> {
					if (delta instanceof RelationshipChangeModelDelta) {
						// look at component assignments, as they change the resolution
						// path
						boolean triggersWrite = false;
						NonRootModelElement modelElement = null;
						RelationshipChangeModelDelta rcmd = (RelationshipChangeModelDelta) delta;
						if (rcmd.getModelElement() instanceof ComponentReference_c) {
							if (rcmd.getRelationName().equals("4201")) {
								triggersWrite = true;
								modelElement = (NonRootModelElement) delta.getModelElement();
							}
						}
						// look at interface ref formalization
						if (rcmd.getModelElement() instanceof InterfaceReference_c) {
							if (rcmd.getRelationName().equals("4012")) {
								triggersWrite = true;
								// we need the component container for graphics
								modelElement = ((NonRootModelElement) delta.getModelElement()).getPersistableComponent()
										.getParent().getRootModelElement();
							}
						}
						if (triggersWrite) {
							PersistableModelComponent pmc = modelElement.getPersistableComponent();
							if (pmc != null) {
								collectedComponents.add(pmc);
							}
						}
					}
				});
			}
			collectedComponents.forEach(component -> {
				writer.write(component.getRootModelElement());
			});
		});
	}

}
