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
package com.mentor.nucleus.bp.core.common;

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

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.util.CoreUtil;

public class ComponentTransactionListener implements ITransactionListener {

	// don't change the resource when a model element is changed
	// if the resource has already been updated
	static private boolean dontMakeResourceChanges = false;

	private HashSet<PersistableModelComponent> persisted = new HashSet<PersistableModelComponent>();

	private Transaction transaction = null;

	protected static Object INTEGRITY_ISSUE_JOB_FAMILY = "__intergrity_marker_job";

	public ComponentTransactionListener() {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mentor.nucleus.bp.core.common.ITransactionListener#transactionStarted(com.mentor.nucleus.bp.core.common.Transaction)
	 */
	public void transactionStarted(Transaction transaction) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mentor.nucleus.bp.core.common.ITransactionListener#transactionCancelled(com.mentor.nucleus.bp.core.common.Transaction)
	 */
	public void transactionCancelled(Transaction transaction) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mentor.nucleus.bp.core.common.ITransactionListener#transactionEnded(com.mentor.nucleus.bp.core.common.Transaction)
	 */
	public void transactionEnded(Transaction transaction) {

		persisted.clear();
		ModelRoot[] modelRoots = transaction.getParticipatingModelRoots();

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
										persistRenamedME(persisted,
												(NonRootModelElement) delta
												.getModelElement());
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
		Domain_c dom = null;
		Component_c comp = null;
		SystemModel_c sys = null;
		for (int i = 0; i < modelRoots.length; i++) {
		  IModelDelta[] modelDeltas = transaction.getDeltas(modelRoots[i]);
		  for (int j = 0; j < modelDeltas.length; j++) {
			IModelDelta delta = modelDeltas[j];
		    if (delta.getKind() ==
		    	               Modeleventnotification_c.DELTA_ELEMENT_RELATED) {
			  RelationshipChangeModelDelta rcmd = (RelationshipChangeModelDelta)delta;
			  // If this delta describes a new relate established
			  // between Domain and Component across R4204 . . .
			  if (rcmd.getRelationName().equals("4204")) {
			    if (rcmd.getDestinationModelElement() instanceof
			    		                                     Domain_c) {
                dom = (Domain_c)rcmd.getDestinationModelElement();
              }
              if (rcmd.getDestinationModelElement() instanceof
              		                                  Component_c) {
                comp = (Component_c)rcmd.getDestinationModelElement();
              }
              if (dom != null && comp != null && !transaction.getDisplayName().equals("Paste")) { //$NON-NLS-1$
                handleComponentFormalization(dom, comp);
				}
			  }
			  if (rcmd.getRelationName().equals("28")) {
			    if (rcmd.getModelElement() instanceof Domain_c) {
                  dom = (Domain_c)rcmd.getModelElement();
                  sys = (SystemModel_c)rcmd.getDestinationModelElement();
			    }
			    if (dom != null && sys != null && comp != null) {
                  handleComponentUnformalization(dom, comp, sys);
				}
			  }
			}
			if (delta.getKind() == Modeleventnotification_c.DELTA_ELEMENT_UNRELATED) {
			  RelationshipChangeModelDelta rcmd = (RelationshipChangeModelDelta)delta;
			  // If this delta describes a new relate established
			  // between Domain and Component across R4204 . . .
			  if (rcmd.getRelationName().equals("4204")) {
                if (rcmd.getDestinationModelElement() instanceof
              		                                  Component_c) {
                  comp = (Component_c)rcmd.getDestinationModelElement();
                }
			    if (dom != null && sys != null && comp != null) {
                  handleComponentUnformalization(dom, comp, sys);
				}
			  }
			}
		  }
		}
		Ooaofooa[] instances = Ooaofooa.getInstances();
		for(int i = 0; i < instances.length; i++) {
			instances[i].clearUnreferencedProxies();
		}
		// run this in a workspace job so that it does not
		// interfere with our file writing
		WorkspaceJob job = new WorkspaceJob("Create integrity issues") {
			
			/* (non-Javadoc)
			 * @see org.eclipse.core.runtime.jobs.Job#belongsTo(java.lang.Object)
			 */
			@Override
			public boolean belongsTo(Object family) {
				return family.equals(INTEGRITY_ISSUE_JOB_FAMILY);
			}

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor)
					throws CoreException {
				// for all persisted files run an integrity check
				for(PersistableModelComponent component: persisted) {
					// deletions will have a null root element
					if(component.getRootModelElement() != null) {
						IntegrityChecker.createIntegrityIssues(component.getRootModelElement());
					}
				}
				return Status.OK_STATUS;
			}
		};
		job.setPriority(Job.DECORATE);
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();
	}
    private void handleComponentFormalization(Domain_c dom, Component_c comp) {
      final IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
      PersistableModelComponent domPmc = dom.getPersistableComponent();
      ModelRoot oldModelRoot = dom.getModelRoot();
      IFolder oldFolder = wsRoot.getFolder(domPmc.
    		                    getParentDirectoryPath().append(dom.getName()));
      dom.updateRootForSelfAndChildren(oldModelRoot, comp.getModelRoot());
      domPmc.resetComponentForChildren(
    		  comp.getPersistableComponent().getContainingDirectoryPath(),
    		          comp.getName(), PersistenceManager.getChildrenOf(domPmc));
      dom.setParentModelRoot(comp.getModelRoot());
      dom.setComponent(comp.getPersistableComponent(), false);
      dom.updateComponentForSelfAndChildren(dom, dom.getPersistableComponent(), false);
      try {
        comp.getPersistableComponent().persistSelfAndChildren();
      }
      catch (CoreException ce) {
        CorePlugin.logError(
        		  "Failure persisting Component file during formalization", ce);
      }
      try {
        oldFolder.delete(true, new NullProgressMonitor());
      }
      catch (CoreException ce) {
        CorePlugin.logError(
               "Failure removing old Component files during formalization", ce);
      }
    }
    private void handleComponentUnformalization(Domain_c dom,
                                          Component_c comp, SystemModel_c sys) {
      final IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
      PersistableModelComponent sysPmc = sys.getPersistableComponent();
      PersistableModelComponent oldDomPmc = dom.getPersistableComponent();
      IPath[] oldFolders = getFoldersToBeRemoved(oldDomPmc);
      IPath newPath = sysPmc.getContainingDirectoryPath().append(dom.getName());
      String Id = newPath.append(dom.getName()).
                               addFileExtension(Ooaofooa.MODELS_EXT).toString();
      dom.updateRootForSelfAndChildren(comp.getModelRoot(),
                                                      Ooaofooa.getInstance(Id));
      oldDomPmc.resetComponentForChildren(newPath, dom.getName(),
                                             getChildrenOfDomainPMC(oldDomPmc));
      dom.setParentModelRoot(sys.getModelRoot());
      dom.setComponent(null, false);
      dom.setComponent(dom.getPersistableComponent(), false);
      dom.updateComponentForSelfAndChildren(dom, dom.getPersistableComponent(), false);
      try {
        dom.getPersistableComponent().persistSelfAndChildren();
      }
      catch (CoreException ce) {
        CorePlugin.logError(
        		"Failure persisting Component file during unformalization", ce);
      }
      for (int m = 0; m < oldFolders.length; m++) {
        IFolder oldFolder = wsRoot.getFolder(oldFolders[m]);
        try {
          oldFolder.delete(true, new NullProgressMonitor());
        }
        catch (CoreException ce) {
          CorePlugin.logError("Failure removing old Component folder " +
	                       oldFolder.toString() + "during unformalization", ce);
        }
      }
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
        PersistableModelComponent domainPmc = null;
        for (Iterator k = children.iterator(); k.hasNext();) {
          PersistableModelComponent childPmc =
                                              (PersistableModelComponent)k.next();
          if (childPmc.getRootModelElement() instanceof Domain_c) {
            domainPmc = childPmc;
            break;
          }
        }
        if (domainPmc != null) {
          children.remove(domainPmc);
        }
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

	private void persistRenamedME(HashSet<PersistableModelComponent> persisted,
			NonRootModelElement me) {

		IPersistenceHierarchyMetaData metaData = PersistenceManager
				.getHierarchyMetaData();

		if (!metaData.isComponentRoot(me)) {
			return;
		}
		// we need to update all proxy reference to renamed component and all of
		// its children
		PersistableModelComponent comp = me.getPersistableComponent();
		Collection children = comp.getChildren();
		// now persist any other proxy data that is out there
		// referring to this component
		for (Iterator iter = children.iterator(); iter.hasNext();) {
			PersistableModelComponent child = (PersistableModelComponent) iter
					.next();
			try {
				if (!child.isLoaded()) {
					NullProgressMonitor nullMon = new NullProgressMonitor();
					child.load(nullMon);
				}
				persistRenamedME(persisted, child.getRootModelElement());
			} catch (CoreException e) {
				CorePlugin.logError("Could not load component for updation. "
						+ child.getFullPath(), e);
			}
		}
		// Persist this
		persist(comp);
		// now persist all RGO proxies
		List selfExternalRGOs = metaData.findExternalRGOsToContainingComponent(
				me, true);
		for (Iterator iterator = selfExternalRGOs.iterator(); iterator
				.hasNext();) {
			PersistableModelComponent target = ((NonRootModelElement) iterator
					.next()).getPersistableComponent();
			if (target != null && !persisted.contains(target)) {
				try {
					if (!target.isLoaded()) {
						NullProgressMonitor nullMon = new NullProgressMonitor();
						target.load(nullMon);
					}
					persist(target);
				} catch (CoreException e) {
					CorePlugin.logError(
							"Could not update persisted model file.", e);
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

	public static void setDontMakeResourceChanges(boolean newValue) {
		dontMakeResourceChanges = newValue;
	}

	private static boolean dontMakeResourceChanges() {
		return dontMakeResourceChanges;
	}

}