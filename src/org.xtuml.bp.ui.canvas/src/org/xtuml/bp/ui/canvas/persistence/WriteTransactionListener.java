package org.xtuml.bp.ui.canvas.persistence;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.xtuml.bp.core.common.IModelDelta;
import org.xtuml.bp.core.common.ITransactionListener;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
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
			// we are interested in Ooaofgraphics changes only
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
				collectedComponents.forEach(component -> {
					writer.write(component.getRootModelElement());
				});
			}
		});
	}
}
