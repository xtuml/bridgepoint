//========================================================================
//
//File:      $RCSfile: ComponentResourceListener.java,v $
//Version:   $Revision: 1.27 $
//Modified:  $Date: 2013/05/12 22:31:27 $
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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.XtUMLNature;
import com.mentor.nucleus.bp.core.ui.IModelImport;
import com.mentor.nucleus.bp.core.ui.IModelImport.IFileHeader;
import com.mentor.nucleus.bp.core.ui.marker.UmlProblem;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;

public class ComponentResourceListener implements IResourceChangeListener, IResourceDeltaVisitor{
    
    static ComponentResourceListener instance = null;
    
    // Ignore resource changes created internally
    static private boolean ignoreResourceChanges = false;

	private static boolean ignoreResourceChangesMarker = false;
    
	private boolean rescanAfterSystemNodeDeleted = false;
	private static final int SYSTEM_MODEL_FILE = 1;
	private static final int MODELS_FOLDER = 2;
	private List<PersistableModelComponent> componentsToLoad = new ArrayList<PersistableModelComponent>();
	private List<Ooaofooa> modelRootsToDelete = new ArrayList<Ooaofooa>();
	private List<IProject> disabledSystems = new ArrayList<IProject>();
	
    static public ComponentResourceListener getDefaultInstance()
    {
        if ( instance == null )
        {
            new ComponentResourceListener();
        }
        return instance;
    }
    private ComponentResourceListener(){
        instance = this;
    }
    
    public void resourceChanged(IResourceChangeEvent event) {
    	componentsToLoad.clear();
    	modelRootsToDelete.clear();
        // if the event has no delta
        IResourceDelta delta = event.getDelta();
        
        if (delta == null) {
            // we aren't interested in it
            return;
        }
      CorePlugin.logResourceActivity(delta);
      Job buildJob = Job.getJobManager().currentJob();
		if (buildJob != null
				&& (buildJob.belongsTo(ResourcesPlugin.FAMILY_AUTO_BUILD)
                        || (buildJob
                                .belongsTo(ResourcesPlugin.FAMILY_MANUAL_BUILD))
                        || (buildJob
                                .belongsTo(TransactionManager.FAMILY_TRANSACTION)) || (buildJob
                        .belongsTo(CorePlugin.UPGRADE_FAMILY)))) {
			return;
		}
        if ( !ignoreResourceChanges() && !isIgnoreResourceChangesMarkerSet() )
        {
            try {
                delta.accept(this);
            } catch (CoreException e) {
                CorePlugin.logError("Could not respond to resource change event", e);
            }
            loadCollectedComponents();
            deleteModelRoots();
        } else
        	setIgnoreResourceChanges(false);

    }
    
    private void deleteModelRoots() {
    	for(Ooaofooa root : modelRootsToDelete) {
    		root.delete();
    	}
	}
	private void loadCollectedComponents() {
		// we need to capture the old and new elements during the load
		// run, they will be used later during change notification
		Map<ModelElement, ModelElement> elementMap = new HashMap<ModelElement, ModelElement>();
		for(final PersistableModelComponent component : componentsToLoad) {
			NonRootModelElement rootME = component.getRootModelElement();
            Ooaofooa.getDefaultInstance().fireModelElementAboutToBeReloaded(rootME);
			try {
				component.load(new NullProgressMonitor(), false, true);
				WorkspaceJob job = new WorkspaceJob("Integrity check job") {
					
					@Override
					public IStatus runInWorkspace(IProgressMonitor monitor)
							throws CoreException {
						IntegrityChecker.createIntegrityIssues(component.getRootModelElement());
						return Status.OK_STATUS;
					}
				};
				job.schedule();
				elementMap.put(rootME, component.getRootModelElement());
			} catch (CoreException e) {
				CorePlugin.logError("Unable to load replaced component.", e);
			}
		}
		// we must fire notification after all components have been
		// loaded, otherwise change notification may be disabled
		// while loading another component causing lost notification
		Set<ModelElement> keySet = elementMap.keySet();
		for(ModelElement key : keySet) {
			ModelElement newElement = elementMap.get(key);
            // reload will cause the rootME to be deleted/created
            // so get the new instance 
            Ooaofooa.getDefaultInstance().fireModelElementReloaded(key, newElement);
		}
	}
	public boolean visit(IResourceDelta delta) {
        IResource resource = delta.getResource();
        IPath path = resource.getFullPath();
        
        if(path.segmentCount() < 1){
            return true;
        }
        
        // ignore any events not related with model directory
        if(path.segmentCount() == 2){
        	if (isModelsFolder(path)) {
        		return true;
        	} else {
        		return false;
        	}
        }
        
        // ignore any events which are on folders under the model directory which
        // do not match the system name, or files which are under the model directory
        if(path.segmentCount() >= 3) {
        	if(path.segmentCount() == 3) {
        		if(resource.getType() != IResource.FOLDER) {
        			return false;
        		}
        	}
        	if(!path.segment(2).equals(resource.getProject().getName())) {
        		return false;
        	}
        }
        
        switch (delta.getKind()) {
        case IResourceDelta.ADDED:
            if ((delta.getFlags() & IResourceDelta.MOVED_FROM) == IResourceDelta.MOVED_FROM) {
                //if component renamed
                IPath oldPath = delta.getMovedFromPath();
                IPath newPath = resource.getFullPath();
                
                if(resource instanceof IProject){
                    IProject project = (IProject)resource;
                    if(XtUMLNature.hasNature(project)){
                        // we only want to handle renames of xtUML projects
                        // we need to get the root component path, as the path for the project is just the
                        // root directory
                        IPath componentPath = PersistableModelComponent.getRootComponentPath(oldPath.lastSegment());
                        handleComponentResourceRenamed(PersistenceManager.findComponent(componentPath), resource, componentPath);
                        return false;
                    }
                }else if(resource instanceof IFolder){
                    String fileName = oldPath.lastSegment() + "." + Ooaofooa.MODELS_EXT;   //$NON_NLS-1$
                    IPath newComponentFilePath = oldPath.append(fileName);
                    PersistableModelComponent com = PersistenceManager.findComponent(newComponentFilePath);
                    if(com != null){
                        handleComponentResourceRemoved(com, resource);
                        return false;
                    }else
                    {
                        //incase folder was rename backed, we ll try to create component
                        fileName=newPath.lastSegment() + "." + Ooaofooa.MODELS_EXT;   //$NON_NLS-1$
                        newComponentFilePath = newPath.append(fileName);
                        PersistableModelComponent.create(newComponentFilePath);                        
                        UIUtil.refresh(null);
                    }
                }else if(isComponentFile(oldPath)){
                    PersistableModelComponent com = PersistenceManager.findComponent(oldPath);
                    if(com != null){
                        handleComponentResourceRemoved(com, resource);
                        return false;
					}
				} else if (isComponentFile(newPath)) {// incase file was renamed
														// back and have valid
														// path
					IFile file = ResourcesPlugin.getWorkspace().getRoot()
							.getFile(newPath);
					IContainer parent = file.getParent();
					PersistableModelComponent.create(newPath);
					// refresh component children
					try {
						PersistenceManager
								.traverseResourceContainer((IFolder) parent);
					} catch (CoreException e) {
						CorePlugin.logError(
								"Unable to refresh component file tree.", e);
					}
					UIUtil.refresh(null);
				}

            }else{//if component added
                if (isComponentFile(resource)) {
					PersistableModelComponent com = PersistenceManager
							.findOrCreateComponent(resource.getFullPath());
					if(com.isRootComponent()) {
						if(checkForDuplicateProjects(resource.getProject(), com)) {
							// found a duplicate system, no need to continue and the
							// above check has also removed the nature on the
							// containing project
							return false;
						}
					}
					handleComponentReplaced(com, delta);
					UIUtil.refresh(null);
					if (com != null) {
						// A new model file has been added this may correct some
						// problmes, give a chance to the Marker framwork
						UmlProblem.handleComponentAdded(com);
					}
                }
                else if(resource instanceof IProject){
                    IProject project = (IProject)resource;
                    if(XtUMLNature.hasNature(project) ){
                        // we only want to handle renames of xtUML projects
                        // we need to get the root component path, as the path for the project is just the
                        // root directory
                        IPath newPath = resource.getFullPath();
                        IPath componentPath = PersistableModelComponent.getRootComponentPath(newPath.segment(0).toString());
                        PersistableModelComponent com = PersistenceManager.findComponent(componentPath);
                        if (com == null){
                      		addRootComponent(resource);
                        }else {
                            handleComponentReplaced(com, delta);
                        }
                        UIUtil.refresh(null);
                        return false;
                    }
                }

            }
            break;
        case IResourceDelta.REMOVED:
        	if ((delta.getFlags() & IResourceDelta.MOVED_TO) != IResourceDelta.MOVED_TO) {
				if (isComponentFile(resource)) {
					PersistableModelComponent component = PersistenceManager
							.findComponent(resource.getFullPath());
					if (component != null) {
						
						boolean deletingSystemNode = false;
						if (component.isLoaded()) {
							NonRootModelElement root = component.getRootModelElement();
							if (root instanceof SystemModel_c) {
								deletingSystemNode = true;
							}
						}
						
						handleComponentResourceRemoved(component, resource);
						
						if (rescanAfterSystemNodeDeleted && deletingSystemNode) {
							rescanAfterSystemNodeDeleted = false;
							IProject prj = resource.getProject();
							PersistenceManager.ensureRootExists(prj);
						}
						return false;
					} else {
						PersistableModelComponent inconsistentComponent = PersistenceManager
								.findInconsistentComponent(resource.getFullPath());
						if(inconsistentComponent != null) {
							PersistenceManager.removeInconsistentComponent(inconsistentComponent);
						}
					}
				}
			} else if((delta.getFlags() & IResourceDelta.MOVED_TO) == IResourceDelta.MOVED_TO) {
				System.out.println();
			}
			return true;
        case IResourceDelta.CHANGED:
        	if(resource instanceof IProject && (delta.getFlags() & IResourceDelta.OPEN)==IResourceDelta.OPEN)
    		{
    			IProject prj = resource.getProject();    			
    			if (prj.isOpen())////check to see, existing project opening
    			{
//    				Creating the 4 segment path to load the root element
    				IPath p = path;        				
    				p  = p.append(Ooaofooa.MODELS_DIRNAME);
    				p = p.append(path);
    				p = p.append(path+ "." + Ooaofooa.MODELS_EXT);
    				if(prj.exists(p.removeFirstSegments(1)))
    				{								  				
	    				IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
    					IResource res =  wsRoot.getFile(p);
                        addRootComponent(res);///adding the root element resource
                        UIUtil.refresh(resource);
    					return false;
    				}
    			}
    		}
        	if (isComponentFile(resource) && ((delta.getFlags() & IResourceDelta.CONTENT)==IResourceDelta.CONTENT)) {
                PersistableModelComponent component = PersistenceManager.findOrCreateComponent(resource.getFullPath());
                if (component != null) {
                	if (!component.isPersisting()) {
                		handleComponentReplaced(component, delta);
                    }
                } else {
                	component = PersistenceManager.findInconsistentComponent(resource.getFullPath());
                	if(component != null) {
                		// if the component was previously stored as inconsistent
                		// remove it and try to create it again.  This will allow
                		// it to be correctly created if the inconsistency was
                		// fixed
                		PersistenceManager.removeInconsistentComponent(component);
                		component = PersistenceManager.findOrCreateComponent(resource.getFullPath());
                		if(component != null) {
                			if(!component.isPersisting()) {
                				handleComponentReplaced(component, delta);
                				UIUtil.refresh(component.getRootModelElement());
                			}
                		}
                	}
                }
            }
        }
        
        if (!rescanAfterSystemNodeDeleted && resource instanceof IProject && resource.getProject().isOpen()) {
			// If the System model file was deleted, we may need to recreate it.
			// We do this to make sure the model is not removed from ME. An
			// example of where this is important is if the user does a replace-
        	// with previous version and pulls-in a version of the model that is 
        	// not in the correct format.
        	IResourceDelta systemModelDelta = findResourceInDelta(delta, SYSTEM_MODEL_FILE);
        	if (systemModelDelta != null) {
				if (systemModelDelta.getKind() == IResourceDelta.REMOVED
						&& ((systemModelDelta.getFlags() & IResourceDelta.MOVED_TO) != IResourceDelta.MOVED_TO)) {
	        		IResourceDelta modelsFolderDelta = findResourceInDelta(delta, MODELS_FOLDER);
	        		// If the models folder was deleted we don't want to do the rescan
	        		if (modelsFolderDelta.getKind() != IResourceDelta.REMOVED) {
	        			rescanAfterSystemNodeDeleted = true;
	        		}
				}
        	}
        }
        
        return true;
    }

	private boolean checkForDuplicateProjects(IProject project, PersistableModelComponent com) {
        IFile file = project.getFile("/"+ Ooaofooa.MODELS_DIRNAME + "/"+  project.getName() + "/"+  project.getName() +"."+ Ooaofooa.MODELS_EXT);

		IModelImport importer;
		try {
			if (file.exists()) {
				importer = CorePlugin
						.getModelImportFactory()
						.create(file, Ooaofooa.getDefaultInstance(),
								com, false, false, false, false);
				IFileHeader header = importer.getHeader();
				InputStream contents = file.getContents();
				SystemModel_c systemModel = header
						.readSystemModel(contents);
				if (systemModel != null && !systemModel.isProxy()) {
					XtUMLNature.removeNature(project);
					// do the following in a workspace job to allow for the
					// removal of the xtuml nature before proceeding
					WorkspaceJob job = new WorkspaceJob("Display warning dialog") {
						
						@Override
						public IStatus runInWorkspace(IProgressMonitor monitor)
								throws CoreException {
							PlatformUI.getWorkbench().getDisplay()
							.asyncExec(new Runnable() {

								@Override
								public void run() {
									UIUtil
											.openInformation(
													PlatformUI
															.getWorkbench()
															.getDisplay()
															.getActiveShell(),
													"Duplicate model found",

													"The project being imported or checked out contains an xtUML model with "
													+ "content that conflicts with another model already present in the workspace."
													+ "\n\nYour selected project has been brought into the workspace, but its "
													+ "BridgePoint features have been disabled.");
								}
							});
							return Status.OK_STATUS;
						}
					};
					job.schedule();
					return true;
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			return true;
		} catch (CoreException e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}
	/**
	 * See if the specified type of resource is included in the given delta
	 * 
	 * Note that this routine is recursive.  
	 * 
     * @param delta  This delta will be queried to determine if it includes
     *               the system node.  
     * @param type  The type to search for.  This will be either SYSTEM_MODEL_FILE, or MODELS_FOLDER.
     * 
     * @return The IResourceDelta for the system node of null if one is not found.
	 */
    private IResourceDelta findResourceInDelta(IResourceDelta thisDelta, int type) {
		IResourceDelta systemNodeDelta = null;

		if (type == SYSTEM_MODEL_FILE) {
			if (isSystemComponent(thisDelta.getResource())) {
				return thisDelta;
			}
		} else if (type == MODELS_FOLDER) {
			if (isModelsFolder(thisDelta.getResource().getFullPath())) {
				return thisDelta;
			}
		}
		IResourceDelta[] nextDeltas = thisDelta.getAffectedChildren();
		for (int i = 0; i < nextDeltas.length && (systemNodeDelta == null); i++) {
			systemNodeDelta = findResourceInDelta(nextDeltas[i], type);
		}

		return systemNodeDelta;
	}

    /**
     * Determine if the given resource refers to a system component file.  This
     * routine does NOT rely on the project being open.  In fact the project does
     * not even need to exist.
     * 
     * @param resource IResource that will be interogated to see if it is the system component.
     * @return True if the given resource is a system component, false if it is not.
     */
    private boolean isSystemComponent(IResource resource) {
    	boolean isSysComponent = false;
		IPath resourcePath = resource.getFullPath();
    	int segCount = resourcePath.segmentCount();
        if(segCount == 4 && (resource instanceof IFile)) {
        	String extension = resourcePath.getFileExtension();
        	String baseFileName = resourcePath.removeFileExtension().lastSegment();
        	String systemFolder = resourcePath.segment(2);
        	String projName = resource.getProject().getName();
        	if( systemFolder.equals(projName) && systemFolder.equals(baseFileName) && extension.equals(Ooaofooa.MODELS_EXT)) {
        		isSysComponent = true;
        	}
        }    	
        return isSysComponent;
    }
    
    /**
     * Determine if the given IFile refers to a project's models folder.  This
     * routine does NOT rely on the project being open.  In fact the project does
     * not even need to exist.
     * 
     * @param path IPath that will be interogated to see if it is the models folder.
     * @return True if the given resource is a system component, false if it is not.
     */
    private boolean isModelsFolder(IPath path) {
    	boolean isModels = false;
	    if(path.segmentCount() == 2){
	        if(path.segment(1).equalsIgnoreCase(Ooaofooa.MODELS_DIRNAME)){
	        	isModels = true; 
	        }
	    }
	    return isModels;
    }
    
    private void handleComponentResourceRenamed(PersistableModelComponent component, IResource newResource, IPath oldPath){

        if ( component == null )
            return;
        
        IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
        
        IPath newPath = newResource.getFullPath();
        
        String newName = null;
        String oldName = null;
        
        IResource oldResource = null;

        final IPersistenceHierarchyMetaData metaData = PersistenceManager.getHierarchyMetaData();

        boolean renameAllowed = true;
        boolean projectRenamed = false;
        if(!metaData.isRootElementRenamable(component)){
            //component rename not allowed so rename the resource back to old name
            oldResource = newResource;
            newPath = oldPath;
            renameAllowed = false;
        }else{
            if(newResource instanceof IProject){
                newName = newPath.lastSegment();
                oldName = oldPath.removeFileExtension().lastSegment();   // we're passed the file name
                // rename the models/<project> folder
                oldResource = wsRoot.getFolder(new Path("/" + newName +  //$NON_NLS-1$
                        "/" + Ooaofooa.MODELS_DIRNAME +  //$NON_NLS-1$
                        "/" + oldName)); //$NON_NLS-1$
                newPath = new Path("/" + newName +  //$NON_NLS-1$
                        "/" + Ooaofooa.MODELS_DIRNAME +  //$NON_NLS-1$
                        "/" + newName); //$NON_NLS-1$
                projectRenamed = true;
            }
            else if(newResource instanceof IFile){
                newName = newPath.removeFileExtension().lastSegment();
                oldName = oldPath.removeFileExtension().lastSegment();
                if ( !newPath.getFileExtension().equals("xtuml") ) {  //$NON_NLS-1$
                    //file extension change not allowed so rename the resource back to old name
                    oldResource = newResource;
                    newPath = oldPath;
                    renameAllowed = false;
                }
                else {
                    // file renamed, also need to rename folder
                    IPath cPath = component.getParentDirectoryPath();
                    oldResource = wsRoot.getFolder(cPath.append(oldName));
                    newPath = cPath.append(newName);
                }
            }else{
                // folder renamed, also need to rename file
                newName = newPath.lastSegment();
                oldName = oldPath.lastSegment();
                oldResource = wsRoot.getFile(newPath.append(oldName + "." + Ooaofooa.MODELS_EXT));  //$NON_NLS-1$
                newPath = newPath.append(newName + "." + Ooaofooa.MODELS_EXT);  //$NON_NLS-1$
            }
        }

        Runnable renameOtherResource = new RenameOtherResource(component, oldResource, newPath, renameAllowed, projectRenamed, newName, oldName);

        // the tree is locked right now, so do this as soon as we can
        Display.getDefault().asyncExec( renameOtherResource );
        
    }
        
    /**
     *  Create and load the PersistableModelComponent for resource
     *  Also used by unit tests
     *  
     * @param resource  the resource we want to create a component for
     */
    static public void addComponentResource(IResource resource) {
        IPath resourcePath = resource.getFullPath();

        // project roots being added are handled differently
        if ( resourcePath.segmentCount() == 4 )
        {
            // a project root was added
            PersistableModelComponent newComponent = 
                PersistenceManager.findComponent(resourcePath);
            if(newComponent==null || !newComponent.isPersisting())
                addRootComponent(resource);
        }
        else
        {
            PersistableModelComponent newComponent = 
              PersistenceManager.findOrCreateComponent(resourcePath);            

            // if this resource change is not due to 
            // the component being created in the BridgePoint perspective
            if ( !newComponent.isPersisting() ) {
                try {
                    // don't do the parse now, we'll get a resource conflict
                    newComponent.load(new NullProgressMonitor(), false,false);
    
                    final NonRootModelElement rootME = newComponent.getRootModelElement();
                    Ooaofooa.getDefaultInstance().fireModelElementLoaded(rootME);
                    
                    // now do the parse in it's own progress dialog
                    if ( Display.getCurrent() != null ) { 
                        parseAllInDialog(rootME);
                    }
                    else {
                        // not in the UI thread, do the parse later
                        // the tree is locked right now, so do this as soon as we can
                        Display.getDefault().asyncExec( new Runnable() {

                            public void run() {
                                parseAllInDialog(rootME);
                            }
                            
                        });
                    }
                } catch (CoreException e) {
                    CorePlugin.logError("Could not load component data", e);
                }
            }
        }
    }
    private static void parseAllInDialog(final NonRootModelElement rootME) {
        if ( CorePlugin.getDefault().getParseAllOnResourceChange () ) {
            ProgressMonitorDialog monitorDialog = new ProgressMonitorDialog(null); 
            try {
                monitorDialog.run(false, false, new IRunnableWithProgress() {
                    public void run(IProgressMonitor monitor) {
                        Domain_c dom = Domain_c.DomainInstance(rootME.getModelRoot());
                        CorePlugin.parseAll(dom, monitor);
                    }
                });
            } catch (InvocationTargetException e) {
                CorePlugin.logError("Could not parse newly loaded component data", e);
            } catch (InterruptedException e) {
                CorePlugin.logError("Could not parse newly loaded component data", e);
            }
        }
    }

    private void handleComponentResourceRemoved(PersistableModelComponent component, IResource resource){
    	if(component.isLoaded()) {
    		NonRootModelElement rootElement = component.getRootModelElement();
			if (component != null
					&& component.getFullPath()
							.toString()
					.equals(rootElement.getModelRoot().getId())) {
				modelRootsToDelete.add(((Ooaofooa) rootElement.getModelRoot()));
			}
			// if this is a system model component, remove all
			// model roots
			if(rootElement instanceof SystemModel_c) {
				Ooaofooa[] roots = Ooaofooa.getInstancesUnderSystem(rootElement.getName());
				for(int i = 0; i < roots.length; i++) {
					modelRootsToDelete.add(roots[i]);
				}
			}
    	}
		component.deleteSelfAndChildren();
    }
    
    private void handleComponentReplaced(final PersistableModelComponent component, IResourceDelta fileDelta){
        // we need to ask the Persistence Manager
        // if this should trigger an unload in preparation
        // for another upgrade
        if(PersistenceManager.requiresUpgradeBeforeUse(component)) {
        	// fire disabled event
        	SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
        			.getDefaultInstance(), new ClassQueryInterface_c() {

        		@Override
        		public boolean evaluate(Object candidate) {
        			return ((SystemModel_c) candidate).getName().equals(
        					component.getFile().getProject().getName());
        		}
        	});
        	if(system == null) {
        		return;
        	}
        	IProject project = component.getFile()
        			.getProject();
        	Ooaofooa.getDefaultInstance().fireSystemAboutToBeDisabled(system);
        	// if this is not the root component, we may need to mark the system
        	// model file as not up to date so that this project will not load until
        	// the required upgrade has occurred
        	if(!disabledSystems.contains(project)) {
        		disabledSystems.add(project);
	        	UpgradeJob job = new UpgradeJob("Disable system", component) {

					@Override
					public IStatus runInWorkspace(IProgressMonitor monitor)
							throws CoreException {
						IProject project = component.getFile().getProject();
						IStatus result = super.runInWorkspace(monitor);
						disabledSystems.remove(project);
						return result;
					}
	        		
	        	};
				job.setRule(ResourcesPlugin.getWorkspace().getRoot());
				job.schedule();
        	}
        	return;
        }
    	if(component != null && component.isLoaded()){
            NonRootModelElement rootME = component.getRootModelElement();
            UmlProblem.removeXtUMLProblems(rootME.getFile());
            // do not load here, we need to do this after all resource events
            // have been handled otherwise invalid elements can be used
            // during batchRelate
            if(!componentsToLoad.contains(component)) {
            	componentsToLoad.add(component);
            }
    	}
    }
    
    static private PersistableModelComponent addRootComponent(IResource resource) {
        PersistableModelComponent newComponent = null;
        try {
            IProject project = resource.getProject();
          	PersistenceManager.getDefaultInstance().traverseProject(project);
          	newComponent = PersistenceManager.getRootComponent(project);
          	if (newComponent == null){// in case when project resource is added from Resource Navigator
          		return null;
          	}
        } catch (CoreException e) {
            CorePlugin.logError("Could not load component", e);
        }
        return newComponent;
    }

	private void triggerModelElementRename(
			final PersistableModelComponent component, final String oldName,
			final String newName) {
		if (PersistenceManager.getHierarchyMetaData().isRootElementRenamable(
				component)) {
			if (!component.isLoaded()) {
				try {
					component.load(new NullProgressMonitor());
				} catch (CoreException e) {
					CorePlugin.logError("Error while renaming the ME", e);
					return;
				}
			}
			NonRootModelElement rootME = component.getRootModelElement();
			final TransactionManager transactionManager = rootME
					.getTransactionManager();
			Transaction transaction = null;
			ComponentTransactionListener.setDontMakeResourceChanges(true);
			try {
				// we need to use transaction api so that model transaction
				// listener can save the change caused by rename in the model
				// moreover we want to control when model transaction listener
				// is asked to persist. Its after when we have done moving
				// the component tree
				transaction = transactionManager.startTransaction(
						"Renaming component thru rename of resource", Ooaofooa
								.getDefaultInstance());
				PersistenceManager.getHierarchyMetaData().setRootElementName(
						rootME, newName);
				transactionManager.endTransaction(transaction, false);
			} catch (TransactionException e) {
				CorePlugin.logError("Error while renaming the ME", e);
			} finally {
				ComponentTransactionListener.setDontMakeResourceChanges(false);
			}
		}
	}

    public static boolean isComponentFile(IResource resource){
        if(resource instanceof IFile){
            return isComponentFile(resource.getFullPath());
        }
        return false;
    }

    private static boolean isComponentFile(IPath path){
        if(Ooaofooa.MODELS_EXT.equalsIgnoreCase(path.getFileExtension())){
            int size = path.segmentCount();
            if(path.removeFileExtension().lastSegment().equals(path.segment(size-2))){
                return true;
            }
        }
        return false;
    }

    public static void setIgnoreResourceChanges(boolean newValue) {
        ignoreResourceChanges = newValue;
    }
    public static boolean getIgnoreResourceChanges() {
        return ignoreResourceChanges;
    }
    private static boolean ignoreResourceChanges() {
        return ignoreResourceChanges;
    }
    
    private class RenameOtherResource implements Runnable {
        private PersistableModelComponent fComponent;
        private IResource fOldResource;
        private IPath fNewPath;
        private boolean fRenameAllowed;
        private boolean fProjectRenamed;
        private String fNewName;
        private String fOldName;
        private IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();

        public RenameOtherResource(PersistableModelComponent component, IResource oldResource, IPath newPath, boolean renameAllowed, boolean projectRenamed, String newName, String oldName) {
            fComponent = component;
            fOldResource = oldResource;
            fNewPath = newPath;
            fRenameAllowed = renameAllowed;
            fProjectRenamed = projectRenamed;
            fNewName = newName;
            fOldName = oldName;
        }
        
        
        public void run() {
            try {
                ProgressMonitorDialog monitorDialog = new ProgressMonitorDialog(null); 
                monitorDialog.run(false, false, new IRunnableWithProgress(){
                    public void run(IProgressMonitor monitor) {
                        try {
                            IFile newFile=wsRoot.getFile(fComponent.getParentDirectoryPath().append(
                                    fNewName + "/" + fNewName + "." + Ooaofooa.MODELS_EXT)); //$NON_NLS-1$//$NON_NLS-2$
                            setIgnoreResourceChangesMarker(true);
                            if(fOldResource instanceof IFile){
                                ((IFile)fOldResource).move(fNewPath, true, true, monitor);
                            }else{
                                fOldResource.move(fNewPath, true, monitor);
                            }
                            setIgnoreResourceChangesMarker(false);
                    
                            if(fComponent.isRootComponent()){
                                newFile = wsRoot.getFile(PersistableModelComponent.getRootComponentPath(fNewName));
                                if ( fProjectRenamed )
                                {
                                    // rename the models/<project>/<project>.xtuml file
                                    IFile oldModelFile = wsRoot.getFile(fNewPath.append(fOldName + "." + Ooaofooa.MODELS_EXT)); //$NON_NLS-1$
                                    setIgnoreResourceChangesMarker(true);
                                    oldModelFile.move(newFile.getFullPath(), true, true, monitor);
                                    setIgnoreResourceChangesMarker(false);
                                }
                                else {
                                    // rename the project
                                    IProject oldProject = wsRoot.getProject(fOldName);
                                    IProject newProject = wsRoot.getProject(fNewName);
                                    
                                    IPath newPath = newProject.getFullPath();
                                    setIgnoreResourceChangesMarker(true);
                                    oldProject.move(newPath, true, monitor);
                                    setIgnoreResourceChangesMarker(false);
                                }
                            }
                            if ( fRenameAllowed )
                            {
                                 fComponent.updateResource(newFile);
                            }
                    
                            if ( fRenameAllowed ) {
                                triggerModelElementRename(fComponent, fOldName, fNewName);
                            }
                            
                        } catch (CoreException e) {
                            CorePlugin.logError("Could not rename component resources", e);
                        }
                        finally {
                        	setIgnoreResourceChangesMarker(false);
                        }
                    }
                    
                });
            } catch (InvocationTargetException e) {
                CorePlugin.logError("Could not rename component resources", e);
            } catch (InterruptedException e) {
                CorePlugin.logError("Could not rename component resources", e);
            }
            
        }

    }
    /**
     * This method is used when resource changes should me marked
     * as ignored for a period of time. Should be used anytime
     * resource changes are not batched.
     */
	public static void setIgnoreResourceChangesMarker(boolean b) {
		ignoreResourceChangesMarker = b;
	}
	
	public static boolean isIgnoreResourceChangesMarkerSet() {
		return ignoreResourceChangesMarker;
	}
    
}
