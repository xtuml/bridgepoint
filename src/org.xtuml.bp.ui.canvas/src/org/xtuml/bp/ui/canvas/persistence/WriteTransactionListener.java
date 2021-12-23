package org.xtuml.bp.ui.canvas.persistence;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.xtuml.bp.core.Modeleventnotification_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.common.AttributeChangeModelDelta;
import org.xtuml.bp.core.common.IModelDelta;
import org.xtuml.bp.core.common.ITransactionListener;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.ui.canvas.CanvasPlugin;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;

public class WriteTransactionListener implements ITransactionListener {

	@SuppressWarnings("unchecked")
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
			// and core element renames, as any persistence will have to rely
			// on the element path for resolution
			Map<AttributeChangeModelDelta, PersistableModelComponent> renamedComponents = new HashMap<>();
			IModelDelta[] coreDeltas = transaction.getDeltas(Ooaofooa.getDefaultInstance());
			if (coreDeltas != null) {
				Stream.of(coreDeltas).filter(d -> d.getKind() == Modeleventnotification_c.DELTA_ATTRIBUTE_CHANGE && ((AttributeChangeModelDelta) d).getAttributeName().equals("Name")).forEach(delta -> {
					PersistableModelComponent pmc = ((NonRootModelElement) delta.getModelElement())
							.getPersistableComponent();
					renamedComponents.put((AttributeChangeModelDelta) delta, pmc);
					pmc.getChildren().forEach(child -> {
						addPmcAndChildren(collectedComponents, pmc);
					});
				});
			}
			// handle renames first
			renamedComponents.entrySet().stream().forEach(entry -> {
				writer.write(entry.getValue().getRootModelElement(), entry.getKey());
			});
			collectedComponents.forEach(component -> {
				writer.write(component.getRootModelElement());
			});
		});
	}

	@SuppressWarnings("unchecked")
	private void addPmcAndChildren(Set<PersistableModelComponent> collection, PersistableModelComponent pmc) {
		collection.add(pmc);
		pmc.getChildren().forEach(child -> {
			addPmcAndChildren(collection, (PersistableModelComponent) child);
		});
	}
}
