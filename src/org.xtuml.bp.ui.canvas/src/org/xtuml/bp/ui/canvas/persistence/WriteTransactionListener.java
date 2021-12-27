package org.xtuml.bp.ui.canvas.persistence;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.AsynchronousMessage_c;
import org.xtuml.bp.core.ClassInstanceParticipant_c;
import org.xtuml.bp.core.ClassParticipant_c;
import org.xtuml.bp.core.CommunicationLink_c;
import org.xtuml.bp.core.ComponentParticipant_c;
import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.CreationTransition_c;
import org.xtuml.bp.core.ExternalEntityParticipant_c;
import org.xtuml.bp.core.InterfaceReference_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.PackageParticipant_c;
import org.xtuml.bp.core.ReturnMessage_c;
import org.xtuml.bp.core.SynchronousMessage_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.common.AttributeChangeModelDelta;
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
						// look at participant formalization
						if (rcmd.getModelElement() instanceof PackageParticipant_c) {
							if (rcmd.getRelationName().equals("956")) {
								triggersWrite = true;
							}
						}
						if (rcmd.getModelElement() instanceof ClassParticipant_c) {
							if (rcmd.getRelationName().equals("939")) {
								triggersWrite = true;
							}
						}
						if (rcmd.getModelElement() instanceof ClassInstanceParticipant_c) {
							if (rcmd.getRelationName().equals("934")) {
								triggersWrite = true;
							}
						}
						if (rcmd.getModelElement() instanceof ComponentParticipant_c) {
							if (rcmd.getRelationName().equals("955")) {
								triggersWrite = true;
							}
						}
						if (rcmd.getModelElement() instanceof ExternalEntityParticipant_c) {
							if (rcmd.getRelationName().equals("933")) {
								triggersWrite = true;
							}
						}
						// look at event assignment
						if (rcmd.getDestinationModelElement() instanceof Transition_c) {
							if (rcmd.getRelationName().equals("507")) {
								triggersWrite = true;
							}
						}
						if (rcmd.getModelElement() instanceof CreationTransition_c) {
							if (rcmd.getRelationName().equals("509")) {
								triggersWrite = true;
							}
						}
						if (rcmd.getModelElement() instanceof SynchronousMessage_c) {
							if (rcmd.getRelationName().equals("1020")) {
								triggersWrite = true;
							}
						}
						if (rcmd.getModelElement() instanceof AsynchronousMessage_c) {
							if (rcmd.getRelationName().equals("1019")) {
								triggersWrite = true;
							}
						}
						if (triggersWrite) {
							if (modelElement == null) {
								modelElement = (NonRootModelElement) delta.getModelElement();
							}
							PersistableModelComponent pmc = modelElement.getPersistableComponent();
							if (pmc != null) {
								collectedComponents.add(pmc);
							}
						}
					}
					if (delta instanceof AttributeChangeModelDelta) {
						boolean triggersWrite = false;
						NonRootModelElement modelElement = null;
						// communication link name change will create a path element
						// we need to write it on first name change
						AttributeChangeModelDelta attrDelta = (AttributeChangeModelDelta) delta;
						if (attrDelta.getModelElement() instanceof CommunicationLink_c
								&& attrDelta.getAttributeName().equals("Numb")) {
							triggersWrite = true;
						}
						if (attrDelta.getModelElement() instanceof SynchronousMessage_c
								&& attrDelta.getAttributeName().equals("Informalname")) {
							triggersWrite = true;
						}
						if (attrDelta.getModelElement() instanceof AsynchronousMessage_c
								&& attrDelta.getAttributeName().equals("Informalname")) {
							triggersWrite = true;
						}
						if (attrDelta.getModelElement() instanceof ReturnMessage_c
								&& attrDelta.getAttributeName().equals("Name")) {
							triggersWrite = true;
						}
						if (attrDelta.getModelElement() instanceof Association_c && attrDelta.getAttributeName().equals("Numb")) {
							triggersWrite = true;
						}
						if (triggersWrite) {
							if(modelElement == null) {
								modelElement = (NonRootModelElement) delta.getModelElement();
							}
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
