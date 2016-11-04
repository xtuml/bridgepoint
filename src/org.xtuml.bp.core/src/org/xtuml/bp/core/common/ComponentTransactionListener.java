// ========================================================================
//
//File: $RCSfile: ComponentTransactionListener.java,v $
//Version: $Revision: 1.23 $
//Modified: $Date: 2013/01/10 22:54:09 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//========================================================================
//
package org.xtuml.bp.core.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.Modeleventnotification_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.ui.PasteAction;
import org.xtuml.bp.core.util.CoreUtil;

public class ComponentTransactionListener implements ITransactionListener {

	// don't change the resource when a model element is changed
	// if the resource has already been updated
	static private boolean dontMakeResourceChanges = false;

	private HashSet<PersistableModelComponent> persisted = new HashSet<PersistableModelComponent>();

	private Transaction transaction = null;

	public ComponentTransactionListener() {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.xtuml.bp.core.common.ITransactionListener#transactionStarted(org.xtuml.bp.core.common.Transaction)
	 */
	public void transactionStarted(Transaction transaction) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.xtuml.bp.core.common.ITransactionListener#transactionCancelled(org.xtuml.bp.core.common.Transaction)
	 */
	public void transactionCancelled(Transaction transaction) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.xtuml.bp.core.common.ITransactionListener#transactionEnded(org.xtuml.bp.core.common.Transaction)
	 */
	public void transactionEnded(Transaction transaction) {

		persisted.clear();
		ModelRoot[] modelRoots = transaction.getParticipatingModelRoots();
		HashSet<PersistableModelComponent> rgosAffectedByMove = new HashSet<PersistableModelComponent> ();
		
		// first persist all model elements created
		// this is so later proxy changes in parent will work correctly
		for (int i = 0; i < modelRoots.length; i++) {
			// Persist changes for all model roots with matching id
			if (modelRoots[i].persistEnabled()) {
				IModelDelta[] modelDeltas = transaction
						.getDeltas(modelRoots[i]);
				for (int j = 0; j < modelDeltas.length; j++) {
					IModelDelta delta = modelDeltas[j];
					if (delta.getKind() == Modeleventnotification_c.DELTA_NEW) {
						NonRootModelElement newME = (NonRootModelElement) delta
								.getModelElement();
						PersistableModelComponent target = PersistenceManager
								.findElementComponent(newME, true);
						persist(target);
					}
				}
			}
		}
		
		for (int i = 0; i < modelRoots.length; i++) {
			// Persist changes for all model roots with matching id
			// i.e., graphics changes and model changes
			if (modelRoots[i].persistEnabled()) {
				IModelDelta[] modelDeltas = transaction
						.getDeltas(modelRoots[i]);
				for (int j = 0; j < modelDeltas.length; j++) {
					IModelDelta delta = modelDeltas[j];
					if (delta.getKind() == Modeleventnotification_c.DELTA_DELETE) {
						NonRootModelElement delME = (NonRootModelElement) delta
								.getModelElement();
						if ((delME != null)
								&& (PersistenceManager.getHierarchyMetaData()
										.isComponentRoot(delME))) {
							IFile delFile = delME.getFile(); 
							if (delFile != null) {
								PersistableModelComponent target = PersistenceManager
										.findComponent(delME.getFile()
												.getFullPath());
								if (target != null) {
									persisted.add(target);
									modelElementDeleted(target);
								}
							}
						}
					} else if (delta instanceof ModelElementMovedModelDelta) {
						// When multiple elements are being moved we will re-persist elements
						persisted.clear();
						
						NonRootModelElement sourceElement = (NonRootModelElement) delta.getModelElement();
						NonRootModelElement destinationElement = ((ModelElementMovedModelDelta)delta).getDestination();
	
						PersistableModelComponent sourcePMC = PasteAction.getContainerForMove(sourceElement).getPersistableComponent();
						boolean errorDuringFileMove = false;

						try {
							ComponentTransactionListener.moveElement(sourceElement, destinationElement);
						} catch (CoreException e) {
							// This is a re-thrown exception, the problem was already logged in movePMC. Not need to log it again.
							errorDuringFileMove = true;
							// revert the in-memory changes
							transaction.revert(true);
							// This is move transaction and we processed the  move delta before anything else.
							// in this case of failure there is nothing more to do, just return.
							return;
						}		

						if (!errorDuringFileMove) {
							
							// persist the moved element and all its RGOs
							PersistableModelComponent destinationPMC = destinationElement.getPersistableComponent(true);
							persistRenamedME(sourceElement, destinationPMC, false);
							
							// In case it was not yet persisted above in persistRenamedME, do it now
							persist(sourcePMC);
							
							// assure all rgos are persisted
							rgosAffectedByMove.addAll(((ModelElementMovedModelDelta) delta)
									.getRGOsAffectedByMove());
						}
					}
				}

				for (int j = 0; j < modelDeltas.length; j++) {
					IModelDelta delta = modelDeltas[j];
					PersistableModelComponent target = null;
					if (delta.getKind() == Modeleventnotification_c.DELTA_DELETE) {
						NonRootModelElement delME = (NonRootModelElement) delta
								.getModelElement();
						// we have processed RootElements in last loop
						if (!PersistenceManager.getHierarchyMetaData()
								.isComponentRoot(delME)) {
							target = delME.getPersistableComponent();
							persist(target);
						}
					} else if (delta instanceof RelationshipChangeModelDelta) {
						RelationshipChangeModelDelta relDelta = (RelationshipChangeModelDelta) delta;
						NonRootModelElement element = (NonRootModelElement) relDelta
								.getModelElement();
						target=element.getPersistableComponent();
                        if(target==null){
                            //ElementInResize  does not have a component, Moreover
                            //we need to get component of element being resized
                            target=CoreUtil.getComponentOfElementInResize(element);
                        }
                        if (target != null && !target.isLoaded()) {
							if (!PersistenceManager.getHierarchyMetaData()
									.isComponentRoot(element)
									&& target.getRootModelElement() != element
									&& !element.isProxy())
                        		target.setRootModelElement(PersistenceManager
									.getHierarchyMetaData()
									.getComponentRootModelElement(
												element));
						}
						persist(target);
                    } else if (delta instanceof AttributeChangeModelDelta) {
                        NonRootModelElement element=(NonRootModelElement) delta.getModelElement();
						target = PersistenceManager.findElementComponent(element, true);
						if (target != null) {
							AttributeChangeModelDelta modelDelta = (AttributeChangeModelDelta) delta;
							if (modelDelta.isPersistenceRelatedChange()) {
								if ("Name".equals(modelDelta.getAttributeName())) { //$NON-NLS-1$
									NonRootModelElement modelElement = (NonRootModelElement) modelDelta
									.getModelElement();
									if (PersistenceManager
											.getHierarchyMetaData()
											.isComponentRoot(modelElement)) {
										modelElementRenamed((AttributeChangeModelDelta) delta);
										persistRenamedME(element, element.getPersistableComponent(), true);
									}
								} else if(modelDelta.getAttributeName().equals("Represents")) {
									// special case to avoid persistence caused by the setting
									// of the represents attributes of GD_GE, GD_MD, and DIM_EL
									// this will be removed when issue 2711 is fixed.
									continue;
								}
								persist(target);
							}
						}
					}
				}
			}
		}

		/**
		 * Persist all the RGOs, if any, associated with the Move operation
		 */
		for (Iterator<PersistableModelComponent> iter = rgosAffectedByMove.iterator(); iter.hasNext();) {
			PersistableModelComponent rgo = (PersistableModelComponent) iter.next();
			persist(rgo);
		}
		
		Ooaofooa[] instances = Ooaofooa.getInstances();
		for(int i = 0; i < instances.length; i++) {
			instances[i].clearUnreferencedProxies();
		}
		IntegrityChecker.startIntegrityChecker(persisted);
	}
    private IPath[] getFoldersToBeRemoved(PersistableModelComponent pmc) {
    	Collection children = getChildrenOfDomainPMC(pmc);
        IPath[] oldFolders = new IPath[children.size()];
        int m = 0;
        for (Iterator k = children.iterator(); k.hasNext();) {
          PersistableModelComponent childPmc =
                                              (PersistableModelComponent)k.next();
          oldFolders[m++] = childPmc.getContainingDirectoryPath();
        }
        return oldFolders;
    }
    private Collection getChildrenOfDomainPMC(PersistableModelComponent pmc) {
        Collection children = PersistenceManager.getChildrenOf(pmc);

        return children;
    }
    private void modelElementDeleted(PersistableModelComponent topComponent) {
		if (dontMakeResourceChanges() || topComponent == null) {
			return;
		}
		final IFolder directoryToDelete = (IFolder) topComponent.getFile()
				.getParent();

		try {
			if (directoryToDelete.exists()) {
				directoryToDelete.delete(true, true, new NullProgressMonitor());
				topComponent.deleteSelfAndChildren();
				// Persist Graphical Data in parent component,
				if (topComponent != null) {
					PersistableModelComponent parent = topComponent.getParent();
					persist(parent);
				}
			}
		} catch (CoreException e) {
			CorePlugin.logError("Could not delete component directory", e);
		}

	}

	private boolean persist(PersistableModelComponent component) {
		if (component == null || component.isOrphaned())
			return false;

		if (!persisted.contains(component)
				&& !component.getRootModelElement().isOrphaned()) {
			try {
				component.persist();
				persisted.add(component);
				return true;
			} catch (CoreException e) {
				CorePlugin
						.logError("Could not update persisted model file.", e);
			}
		}
		return false;
	}

	private void persistRenamedME(NonRootModelElement elementRenamed, PersistableModelComponent newPMC, boolean persistRGOs) {

		IPersistenceHierarchyMetaData metaData = PersistenceManager
				.getHierarchyMetaData();


		// Persist this PMC and all PMCs under it
		persist(newPMC);
		Collection<PersistableModelComponent> children = TransactionManager.gatherChildrenComponents(elementRenamed.getPersistableComponent());
		for (Iterator<PersistableModelComponent> iter = children.iterator(); iter.hasNext();) {
			PersistableModelComponent child = (PersistableModelComponent) iter.next();
			persist(child);
		}

		if (persistRGOs) {
			// now persist all RGO proxies		
	 		List selfExternalRGOs;
	 		Package_c packageContainer;
			if (elementRenamed instanceof Package_c) {
				packageContainer = (Package_c)elementRenamed;
			} else {			
				packageContainer = elementRenamed.getFirstParentPackage();
			}
			selfExternalRGOs = metaData.findExternalRGOsToContainingComponent(packageContainer, true);
			
			for (Iterator iterator = selfExternalRGOs.iterator(); iterator.hasNext();) {
				PersistableModelComponent target = ((NonRootModelElement) iterator.next()).getPersistableComponent();
				if (target != null && !persisted.contains(target)) {
					persist(target);
				}
			}
		}
	}

	private static void modelElementRenamed(
			final AttributeChangeModelDelta delta) {
		if (dontMakeResourceChanges()) {
			return;
		}
		final String oldName = (String) delta.getOldValue();
		final String newName = (String) delta.getNewValue();
		if (oldName.equals("")) // $NON-NLS-1$
		{
			// the model element is being created
			// the resource hasn't been created yet
			return;
		} else if (newName.equals("")) {
			// the model element is being deleted
			// ignore
			return;
		}
		final PersistableModelComponent component = PersistenceManager
				.getComponent((NonRootModelElement) delta.getModelElement());
		if (component != null) {
			// the component is already named correctly
			// this occurs during copying
			if(component.getName().equals(newName)) {
				return;
			}
			final IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace()
					.getRoot();

			IFile oldFile = wsRoot
					.getFile(component.getParentDirectoryPath()
							.append(
									oldName + "/" + oldName + "."
											+ Ooaofooa.MODELS_EXT));
			IFile newFileOldFolder = wsRoot
					.getFile(component.getParentDirectoryPath()
							.append(
									oldName + "/" + newName + "."
											+ Ooaofooa.MODELS_EXT));
			IFolder oldFolder = wsRoot.getFolder(component
					.getParentDirectoryPath().append(oldName));
			IFolder newFolder = wsRoot.getFolder(component
					.getParentDirectoryPath().append(newName));

			try {
				// Rename both the file and the folder
				oldFile.move(newFileOldFolder.getFullPath(), true, true, null);
				oldFolder.move(newFolder.getFullPath(), true, true, null);
				if (component.isRootComponent()) {
					IProject oldProject = wsRoot.getProject(oldName);
					IProject newProject = wsRoot.getProject(newName);

					IPath newPath = newProject.getFullPath();
					oldProject.move(newPath, true, null);
					IFile newFile = wsRoot.getFile(PersistableModelComponent
							.getRootComponentPath(newName));
					component.updateResource(newFile);
				} else {
					IFile newFile = wsRoot.getFile(component
							.getParentDirectoryPath().append(
									newName + "/" + newName + "."
											+ Ooaofooa.MODELS_EXT));
					component.updateResource(newFile);
				}
			} catch (CoreException e) {
				CorePlugin.logError("Could not rename component resources", e);
			}
		}
	}

	/**
	 * Move the PMC from the source to the destination
	 * 
	 * Example:
	 * 		Before:
	 * 			source_package/
	 * 							source_package.xtuml
	 * 							component1/component1.xtuml
	 * 		After:
	 * 			destination_package/
	 * 								destination_package.xtuml
	 * 								component1/component1.xtuml
	 * 
	 * @param elementMoved This is the NonRootModelElement being moved
	 * @param destination This is the destination selected by the user
	 */
	private static void moveElement(NonRootModelElement sourceElement, NonRootModelElement destinationElement) throws CoreException {
		final IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
		IPersistenceHierarchyMetaData metadata = PersistenceManager.getHierarchyMetaData();
		PersistableModelComponent destinationPMC = destinationElement.getPersistableComponent(true);
		NonRootModelElement rtoForResolution = sourceElement.getRTOElementForResolution();
		
		// start: move the element to the new ModelRoot in memory
		// 		To implement undo, this in-memory section will be moved
		//		from here into PasteAction, and any NRME modified here will
		//		be be saved-off into the transaction in its state BEFORE
		//		any changes are made to it. Similar to what we do for a 
		//		ModelElementChanged transaction. In fact we can use a Transaction
		//		group for move and use ModelElementChanged to store these before and
		//		after NRMEs.
		if (sourceElement.getModelRoot() != destinationElement.getModelRoot()) {
			sourceElement.updateRootForSelfAndChildren(sourceElement.getModelRoot(), destinationElement.getModelRoot());			
		}		
		// end: move the element to the new ModelRoot in memory

		// Move the folder on disk if the sourceElement is associated 
		// with a folder/file on disk.
		if (metadata.isComponentRoot(sourceElement)) {
			IFile newFile = destinationPMC.getFile();
			IPath sourceElementFolderPath = sourceElement.getPersistableComponent().getContainingDirectoryPath();
			IFolder sourceElementFolder = wsRoot.getFolder(sourceElementFolderPath);
			IPath destinationPath = destinationPMC.getContainingDirectoryPath().append(sourceElement.getName());			

			// move the folder from the original location to the destination folder.
			try {
				// Allow the move to keep the local history
				sourceElementFolder.move(destinationPath, true, true, null);
				// Update the underlying resource and its children (if any)
				String elementName = sourceElement.getName();
				newFile = wsRoot.getFile(destinationPath.append(elementName + "." + Ooaofooa.MODELS_EXT));
			} catch (CoreException e) {
				CorePlugin.logError("Could not move the folder from  " + sourceElementFolder.toString() + " to "
						+ destinationPath.toString() + "   Element being moved: " + sourceElement.getName(), e);
				throw e;
			}				
			
			// Update the moved PMCs file resource to point at its new file
			sourceElement.getPersistableComponent().updateResource(newFile);
		} else {
			// Update the PMC in the moved element to point at it new home
			sourceElement.setComponent(destinationPMC);			
			if (rtoForResolution instanceof DataType_c) {
				rtoForResolution.setComponent(destinationPMC);			
			}
		}		
	}

	public static void setDontMakeResourceChanges(boolean newValue) {
		dontMakeResourceChanges = newValue;
	}

	private static boolean dontMakeResourceChanges() {
		return dontMakeResourceChanges;
	}

}