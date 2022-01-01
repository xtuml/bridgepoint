package org.xtuml.bp.ui.canvas.persistence;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.xtuml.bp.core.Modeleventnotification_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.Requirement_c;
import org.xtuml.bp.core.common.AttributeChangeModelDelta;
import org.xtuml.bp.core.common.BaseModelDelta;
import org.xtuml.bp.core.common.IModelDelta;
import org.xtuml.bp.core.common.ITransactionListener;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.common.RelationshipChangeModelDelta;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.ui.canvas.CanvasPlugin;
import org.xtuml.bp.ui.canvas.Cl_c;
import org.xtuml.bp.ui.canvas.Objectreference_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.Referencepath_c;
import org.xtuml.bp.ui.canvas.references.ReferencePathManagement;

public class WriteTransactionListener implements ITransactionListener {

	class WriteComponent {
		PersistableModelComponent component;
		String previousName = "";

		public WriteComponent(PersistableModelComponent component, String previousName) {
			this.component = component;
			this.previousName = previousName;
		}

		@Override
		public boolean equals(Object obj) {
			if (component == null && ((WriteComponent) obj).component == null) {
				return true;
			}
			if (obj instanceof WriteComponent) {
				return component.equals(((WriteComponent) obj).component)
						&& previousName.equals(((WriteComponent) obj).previousName);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return component != null ? component.hashCode() : 31 * previousName.hashCode();
		}
	}

	@Override
	public void transactionEnded(Transaction transaction) {
		PersistenceExtensionRegistry persistenceExtensionRegistry = CanvasPlugin.getDefault()
				.getPersistenceExtensionRegistry();
		persistenceExtensionRegistry.registeredExtensions.stream().forEach(pe -> {
			IGraphicalWriter writer = pe.getWriter();
			// we are interested in all Ooaofgraphics changes
			Set<WriteComponent> collectedWrites = new HashSet<>();
			IModelDelta[] deltas = transaction.getDeltas(Ooaofgraphics.getDefaultInstance());
			if (deltas != null) {
				Stream.of(deltas).forEach(delta -> {
					PersistableModelComponent pmc = ((NonRootModelElement) delta.getModelElement())
							.getPersistableComponent();
					if (pmc != null) {
						collectedWrites.add(new WriteComponent(pmc, ""));
					}
				});
			}
			// we are interested in some ooa changes
			IModelDelta[] coreDeltas = transaction.getDeltas(Ooaofooa.getDefaultInstance());
			if (coreDeltas != null) {
				Stream.of(coreDeltas).forEach(delta -> {
					// interested in additions and removals
					if (delta instanceof BaseModelDelta) {
						if (ReferencePathManagement
								.elementHasDiagramRepresentation((NonRootModelElement) delta.getModelElement())) {
							// collect the owning PMC for the changed element
							collectedWrites.add(new WriteComponent(
									((NonRootModelElement) delta.getModelElement()).getPersistableComponent(), ""));
							// if a deletion clean up path references
							if (delta.getKind() == Modeleventnotification_c.DELTA_DELETE) {
								// if this object is used anywhere in a path the path is no good
								Objectreference_c[] refs = Objectreference_c.ObjectreferenceInstances(
										Ooaofgraphics.getDefaultInstance(),
										ref -> ((Objectreference_c) ref).getElement() == delta.getModelElement());
								Stream.of(refs).forEach(ref -> {
									Referencepath_c path = Referencepath_c.getOneR_RPOnR500(ref);
									ReferencePathManagement.removePath(path);
									path.Dispose();
								});
							}
						}
					}
					// interested in attr/rel changes if paths are modified as a result
					if (delta instanceof RelationshipChangeModelDelta || delta instanceof AttributeChangeModelDelta) {
						Stream.of(Referencepath_c.ReferencepathInstances(Ooaofgraphics.getDefaultInstance()))
								.forEach(path -> {
									Stream.of(Objectreference_c.getManyR_ORsOnR500(path)).forEach(objRef -> {
										if (!Cl_c.Getname(objRef.getElement()).equals(objRef.getLastname())) {
											// collect
											String previousName = "";
											NonRootModelElement pathElement = (NonRootModelElement) path.getElement();
											// if the elements pmc is gone, this is an unrelate before delete
											// just ignore
											if (pathElement.getPersistableComponent() != null) {
												String lastname = objRef.getLastname();
												String[] parts = pathElement.getPath().split("::");
												String pathElementName = parts[parts.length - 1];
												boolean requiresFileRename = pathElement.equals(objRef.getElement())
														&& pathElement.getPersistableComponent()
																.getRootModelElement() == pathElement
														&& !objRef.getLastname().equals(pathElementName);
												if (requiresFileRename) {
													previousName = objRef.getLastname();
												}
												WriteComponent write = new WriteComponent(
														((NonRootModelElement) path.getElement())
																.getPersistableComponent(),
														previousName);
												// in the case of a Provision/Requirement we must also
												// write the package as formalization state changes path
												// references
												if (pathElement instanceof Provision_c
														|| pathElement instanceof Requirement_c) {
													WriteComponent parentWrite = new WriteComponent(
															pathElement.getFirstParentComponent()
																	.getFirstParentPackage().getPersistableComponent(),
															"");
													collectedWrites.add(parentWrite);
												}
												collectedWrites.add(write);
												// update last
												objRef.setLastname(Cl_c.Getname(objRef.getElement()));
											}
										}
									});
								});

					}
				});
			}
			collectedWrites.forEach(write -> {
				if (!write.previousName.equals("")) {
					// update file name
					writer.nameChange((NonRootModelElement) write.component.getRootModelElement(), write.previousName);
					writer.write(write.component.getRootModelElement());
					// write the parent as well
					writer.write(
							PersistenceManager.getHierarchyMetaData().getParent(write.component.getRootModelElement()));
				} else {
					if (write.component != null) {
						writer.write(write.component.getRootModelElement());
					}
				}
			});
		});
	}

}
