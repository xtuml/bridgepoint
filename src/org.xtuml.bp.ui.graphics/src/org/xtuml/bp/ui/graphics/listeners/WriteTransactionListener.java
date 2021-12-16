package org.xtuml.bp.ui.graphics.listeners;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.xtuml.bp.core.common.IModelDelta;
import org.xtuml.bp.core.common.ITransactionListener;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.graphics.Activator;
import org.xtuml.bp.ui.graphics.persistence.IGraphicalWriter;

public class WriteTransactionListener implements ITransactionListener {

	@Override
	public void transactionEnded(Transaction transaction) {
		IGraphicalWriter writer = Activator.getDefault().getRegisteredWriter();
		if (writer != null) {
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
		}
	}
}
