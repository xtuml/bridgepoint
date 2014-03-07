// ========================================================================
//
//File: $RCSfile: PlaceHolderManager.java,v $
//Version: $Revision: 1.22 $
//Modified: $Date: 2013/05/10 13:25:23 $
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
package com.mentor.nucleus.bp.ui.text.placeholder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import org.eclipse.core.internal.resources.Marker;
import org.eclipse.core.internal.resources.ProjectPreferences;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.AttributeChangeModelDelta;
import com.mentor.nucleus.bp.core.common.IAllActivityModifier;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.ui.preferences.BridgePointProjectActionLanguagePreferences;
import com.mentor.nucleus.bp.ui.text.AbstractModelElementListener;
import com.mentor.nucleus.bp.ui.text.ModelElementID;
import com.mentor.nucleus.bp.ui.text.TextPlugin;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.activity.AllActivityModifier;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.placeholder.PlaceHolderEntry.PlaceHolderFileProxy;

public class PlaceHolderManager {
	
	/**
	 * The WorkspaceRunnable's thread name used to the reWrite the placeholder files
	 * This string used by the renaming SystemModel and Domain TestCases to wait until 
	 * this thread completes.
	 * @see PlaceHolderManager#reWritePlaceHolders(ArrayList list)
	 * @see PlaceHolderFileReWriter
	 */ 
	public static String PLACEHOLDER_REWRITER_THREAD_NAME = "__PlaceHolderFileReWriter_Thread"; //$NON-NLS-1$
	static String[] supportedExtensions = new String[]{ActivityEditorInputFactory.PLACEHOLDER_EXTENSION, DescriptionEditorInputFactory.PLACEHOLDER_EXTENSION};

	Map<ModelElementID, PlaceHolderEntry> placeholderMap = new TreeMap<ModelElementID, PlaceHolderEntry>();
	private boolean parseInProgress = false;
	
	protected PlaceHolderManager() {
		PlaceHolderStateSynchronizer stateSynchronizer = new PlaceHolderStateSynchronizer();
		
		initializeFor(PersistenceManager.findRootComponentInstances());
		Ooaofooa.getDefaultInstance().addModelChangeListener(stateSynchronizer);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(stateSynchronizer);
		
		CorePlugin.addProjectPreferenceListener(stateSynchronizer);
	}

	private static PlaceHolderManager defaultInstance = null;

	public static PlaceHolderManager getDefaultInstance() {
		if (defaultInstance == null) {
			defaultInstance = new PlaceHolderManager();
		}
		return defaultInstance;
	}

	public boolean parseInProgress() {
		return parseInProgress;
	}
	
	public void setParseInProgress(boolean flag) {
		parseInProgress = flag;
	}
	
	/**
	 * This method always returns a valid IFile instance. It creates a new PlaceHolderEntry if none already  
	 * exits.
	 * 
	 * @param modelElementID Must not be null, used as the key for the PlaceHolderMap
	 * @param extension Currently can only be "oal" or "dsc"
	 * @param requester Must not be null
	 * @return An already exsiting IFile or a newly created instance
	 * 	 * @throws IllegalArgumentException if the extension is not knows
	 */
	public IFile getPlaceholderFile(ModelElementID modelElementID, String extension, Object requester) {
		return getPlaceholderFile(modelElementID, extension, requester, true);
	}
	
	public IFile getPlaceholderFile(ModelElementID modelElementID, String extension, Object requester, boolean toCreateNew) {
		if(!isSupportedExtension(extension)){
			throw new IllegalArgumentException("extension " + extension + " is not supported");  //$NON-NLS-1$//$NON-NLS-2$
		}
		if (modelElementID != null) {
		synchronized(placeholderMap){
			PlaceHolderEntry entry = placeholderMap.get(modelElementID);
			if(entry == null){
				/**
				 * Creating a new entry only when it is requested, otherwise this function works as 
				 */
				if(toCreateNew){
					entry = new PlaceHolderEntry(this);
					placeholderMap.put(modelElementID, entry);
				}else{
					return null;
				}
			}
			return entry.getPlaceHolderFile(modelElementID, extension, requester, toCreateNew);
		}
	}
		return null;
	}

	/**
	 * Removes an PlaceHolderProxyFile from the map. Also removes the PlaceHolderEntry if it doesnt have any 
	 * more instances left. 
	 * 
	 * @param modelElementID Must not be null, used as the key for the PlaceHolderMap 
	 * @param extension Currently can only be "oal" or "dsc"
	 * @param requester Must not be null
	 * @throws IllegalArgumentException if the extension is not knows
	 */
	public void releasePlaceholderFile(ModelElementID modelElementID, String extension, Object requester) {
		if(!isSupportedExtension(extension)){
			throw new IllegalArgumentException("extension " + extension + " is not supported"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		PlaceHolderEntry entry = placeholderMap.get(modelElementID);
		if (entry != null) {
			synchronized(placeholderMap){
				entry.releasePlaceHolderFile(extension, requester);
				if(entry.isSafeToRemove()){
						placeholderMap.remove(modelElementID);
				}
			}
		}
	}

	/*
	 * This method is only called on construction of the manager. It initializes
	 * the place holders for all components that are already loaded. Later it is
	 * job of state synchronizer to maintain the state on loading and unloading
	 * of the components.
	 */
	
	private void initializeFor(PersistableModelComponent[] components){
		for (int i = 0; i < components.length; i++) {
		    initializeFor(components[i]);
			
			if(components[i].getChildrenCount() > 0){
				Collection children = components[i].getChildren();
				initializeFor((PersistableModelComponent[])children.toArray(new PersistableModelComponent[children.size()]));
			}
		}
	}
	
	private void initializeFor(PersistableModelComponent component){
		if(!component.isLoaded()){
			return;
		}
		
		IPath folderPath = component.getContainingDirectoryPath(); 
		
		IResource members[] = null;
		
		//Getting all placeholder files
		IContainer container = ResourcesPlugin.getWorkspace().getRoot()
				.getFolder(folderPath);
		
        if(!container.exists())
        {
            // this occurs when models are being converted from 
            // single-file repository to multi-file repository
            return;
        }
        
		try {
			members = container.members();
		} catch (CoreException e) {
			TextPlugin.logError("Exception while getting children of models folder",e); //$NON-NLS-1$
			return;
		}

		//Loading only those whose modelRootId meets with id of this component.
		for (int j = 0 ; j < members.length; j++){
			if (members[j] instanceof IFile){
				IFile file = (IFile) members[j];
				if(isSupportedExtension(file.getFileExtension()) && !isLoaded(file)){
					ModelElementID modelElementID = null;
					try {
						modelElementID = ModelElementID.createInstance(file);
					} catch (CoreException e) {
						TextPlugin.logError("Can't load some required project files", e); //$NON-NLS-1$
					}
					synchronized(placeholderMap){
						if (modelElementID == null) {
							TextPlugin.logError("The memento data could not be loaded", null); //$NON-NLS-1$
						}
						else if (modelElementID.getModelRootID().equals(component.getUniqueID())){
							PlaceHolderEntry entry = placeholderMap.get(modelElementID);
							if(entry == null){
								entry = new PlaceHolderEntry(PlaceHolderManager.this);
								placeholderMap.put(modelElementID, entry);
							}
							entry.init(file, modelElementID);
						}
					}					
				}
			}
		}
	}
	
	private boolean isLoaded(IFile file){
		return (findPlaceHolder(file) == null)?false:true;
	}

	private synchronized PlaceHolderFileProxy findPlaceHolder(IFile file){
		
		Collection placeholders = placeholderMap.values();
		
		synchronized(placeholders){
			for (Iterator iterator = placeholders.iterator(); iterator.hasNext();) {
				PlaceHolderEntry entry = (PlaceHolderEntry) iterator.next();
				PlaceHolderFileProxy[] placeHolders= entry.getPlaceHolderFiles();
				for (int i = 0; i < placeHolders.length; i++) {
					if(placeHolders[i].equals(file)){
						return placeHolders[i];
					}
				}
			}
		}

		return null;
	}
	
    private boolean isReservedByMap(String proposedName, String ext){
        String proposedFileName = proposedName + "." + ext;  //$NON-NLS-1$
        synchronized(placeholderMap){
            for (Iterator iter = placeholderMap.values().iterator(); iter.hasNext();) {
                PlaceHolderEntry entry = (PlaceHolderEntry) iter.next();
                IFile file = entry.getPlaceHolderFileFor(ext);
                if(file != null && file.getName().equals(proposedFileName)){
                    return false;
                }
            }
        }
        return true;
    }
	
	IFile getFileWithUniqueName(ModelElementID modelElementID, IPath path, String fileNameWithoutExt, String extension) {
		fileNameWithoutExt = getLegalFileName(fileNameWithoutExt);
        String proposedName = fileNameWithoutExt;
		
		int suffix = 1;
		
		// check if the given name is already reserved by some model element id
        boolean isUnique = false;
        while(!isUnique){
            isUnique = isReservedByMap(proposedName, extension);
            if(!isUnique){
                proposedName = fileNameWithoutExt + "." + suffix ; //$NON-NLS-1$
						suffix++;
					}
				}
		
        String fullName = proposedName + "." + extension; //$NON-NLS-1$

		// check if there is no file present on the given path with same name
		// this is possible as there might be place holder files, which are not
		// loaded because associated model is not loaded.
		
		// It is also possible that we require to create editor input(which requires
		// place holder file), whose model element is not currently avaiable (like if
		// project is close). We want to provide it with valid placeholderfile, which
		// may not be initialized, that is why we are not using resource api here.
		
		IPath filePath = null;
		while(true){
			filePath = path.append(fullName);
			File file = filePath.toFile();
			if(file.exists() && !modelElementID.isRepresentedBy(file)){
                fullName = fileNameWithoutExt + "." + suffix + "." + extension; //$NON-NLS-1$ //$NON-NLS-2$
				suffix++;
			}else{
				break;
			}
		}
		
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		
		IPath rootPath = workspaceRoot.getLocation();
		filePath = filePath.removeFirstSegments(filePath.matchingFirstSegments(rootPath));
		
		return workspaceRoot.getFile(filePath);
	}
	
	static String illegalCharacters = "\\/:*?\"<>| "; //$NON-NLS-1$
	static String getLegalFileName(String invalidName){
		char[] nameChars = invalidName.toCharArray();
		for (int i = 0; i < nameChars.length; i++) {
			if(illegalCharacters.indexOf(nameChars[i]) >= 0){
				nameChars[i] = '_';
			}
		}
		return new String(nameChars);
	}
	
	static boolean isSupportedExtension(String extension){
		for (int i = 0; i < supportedExtensions.length; i++) {
			if(supportedExtensions[i].equals(extension)){
				return true;
			}
		}
		return false;
	}
	
	class PlaceHolderStateSynchronizer extends AbstractModelElementListener implements IPreferenceChangeListener {
		
		protected void handleResourceMarkersChanged(IResourceDelta delta) {
			PlaceHolderFileProxy placeHolderFile = findPlaceHolder((IFile)delta.getResource());
			if(placeHolderFile != null){
				placeHolderFile.handleMarkersUpdated(delta);
			}
		}

        protected void handleComponentRenamed(PersistableModelComponent component, String oldName, String newName) {
            updatePlaceHolders(component);
        }
        
        private void updatePlaceHolders(PersistableModelComponent component){
            Object[] entries = null;
            synchronized(placeholderMap){
                entries = placeholderMap.entrySet().toArray();
            }

            for (int i = 0; i < entries.length; i++) {
                Map.Entry entry = (Map.Entry)entries[i];
                PlaceHolderEntry placeHolderEntry = (PlaceHolderEntry) entry.getValue();
                if(placeHolderEntry != null &&
                        placeHolderEntry.getComponent() == component){
                    placeHolderEntry.updateName();
                }
            }
            
            for (Iterator iterator = component.getChildren().iterator(); iterator.hasNext();) {
                PersistableModelComponent child = (PersistableModelComponent) iterator.next();
                updatePlaceHolders(child);
            }
        }

        protected void handleModelElementAttributeChanged(ModelChangedEvent event, AttributeChangeModelDelta delta, ModelElementID changedModelElementID) {
			Object[] entries = null;
			synchronized(placeholderMap){
				entries = placeholderMap.entrySet().toArray();
			}

            /*
             * We still need to iterate the PlaceHolderMap, to  update names of the any other model
             * element. All entries are iterated so that a interdependent names are updated for all
             * effected entries.
             */
            for (int i = 0; i < entries.length; i++) {
                Map.Entry entry = (Map.Entry)entries[i];
                PlaceHolderEntry placeHolderEntry = (PlaceHolderEntry) entry.getValue();
                if(placeHolderEntry != null &&
                        placeHolderEntry.getModelRoot() == event.getSourceModelRoot()){
                    placeHolderEntry.updateName();
                }
            }
		}
		
		protected void handleModelElementDeleted(ModelChangedEvent event, IModelDelta delta, ModelElementID deletedModelElementID) {
			Object[] placeHolderEntries = null;
			synchronized(placeholderMap){
				placeHolderEntries = placeholderMap.values().toArray();  
			}
			
			for (int index = 0; index < placeHolderEntries.length; index++) {
				
				PlaceHolderEntry placeHolderEntry = (PlaceHolderEntry) placeHolderEntries[index];

				if (placeHolderEntry.getModelElementID().equals(deletedModelElementID)){
					try {
						synchronized(placeholderMap){
							placeholderMap.remove(placeHolderEntry.getModelElementID());
							placeHolderEntry.dispose();
						}
					} catch (CoreException e) {
						TextPlugin.logError(e.getMessage(), e);
					}
					break;
				} else{	//Finding against the required model element id
					PlaceHolderFileProxy[] files = placeHolderEntry.getPlaceHolderFiles();
					for (int i = 0; i < files.length; i++) {
						ModelElementID requireModelElement = files[i].getRequiredModelElementID();
						if(requireModelElement != null && requireModelElement.equals(deletedModelElementID)){
							files[i].dispose();
						}
					}
					synchronized(placeholderMap){
						if(placeHolderEntry.isSafeToRemove()){
							placeholderMap.remove(placeHolderEntry.getModelElementID());
						}
					}
				}
			}					
		}
		
		/**
		 * Initializes the PlaceHolderMap and laods any existing placeholder files.
		 */
		protected void handleComponentLoaded(ModelChangedEvent event) {
			PersistableModelComponent component = PersistenceManager
			.getDefaultInstance().getComponent((NonRootModelElement)event.getModelElement());
			
			if(component == null){
				return;
			}

            initializeFor(component);
		}
		
		protected void handleComponentUnloaded(ModelChangedEvent event) {
			ModelElement me = event.getModelElement();
			if(!(me instanceof NonRootModelElement)){
				return;
			}
			
			PersistableModelComponent component = PersistenceManager.getComponent((NonRootModelElement)me);

			if(component == null){
				return;
			}
			
			Object[] placeHolderEntries = null;

			synchronized(placeholderMap){
				placeHolderEntries =   placeholderMap.values().toArray();
				
			}

            String compDirPath = component.getFullPath().toString();
			for (int index = 0; index < placeHolderEntries.length; index++) {
				PlaceHolderEntry placeHolderEntry = (PlaceHolderEntry) placeHolderEntries[index];
				if(compDirPath.equals(placeHolderEntry.getModelElementID().getComponentPath())){
					try {
						synchronized(placeholderMap){
							placeHolderEntry.dispose();
							placeholderMap.remove(placeHolderEntry.getModelElementID());
						}
					} catch (CoreException e) {
						TextPlugin.logError("Error while disposing place holder entry", e); //$NON-NLS-1$
					}
				}
			}
		}

        @Override
		public void systemAboutToBeDisabled(SystemModel_c system) {
			Object[] placeHolderEntries = null;
			synchronized(placeholderMap){
				placeHolderEntries =   placeholderMap.values().toArray();
				
			}
			for (int index = 0; index < placeHolderEntries.length; index++) {
				PlaceHolderEntry placeHolderEntry = (PlaceHolderEntry) placeHolderEntries[index];
				if(placeHolderEntry.getModelElementID().getModelRoot().getRoot() == system) {
					try {
						synchronized(placeholderMap){
							placeHolderEntry.dispose();
							placeholderMap.remove(placeHolderEntry.getModelElementID());
						}
					} catch (CoreException e) {
						TextPlugin.logError("Error while disposing place holder entry", e); //$NON-NLS-1$
					}
				}
			}
		}

		protected void handleModelReloaded(ModelChangedEvent event) {}

		@Override
		public void preferenceChange(PreferenceChangeEvent event) {
			if (event.getKey() == BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE) {
				Object newValue = event.getNewValue();
				if (newValue instanceof String) {
					boolean enabled = Boolean.valueOf(newValue.toString());
					if (!enabled) {
						if (event.getSource() instanceof ProjectPreferences) {
							String absolutePath = ((ProjectPreferences) event
									.getSource()).absolutePath();
							Path path = new Path(absolutePath);
							if(path.segmentCount() > 1) {
								String projectName = path.segments()[1];
								IProject project = ResourcesPlugin.getWorkspace()
										.getRoot().getProject(projectName);
								reparseSynchronousMessages(project);
							}
						}
					}
				}
			}
			if (event.getKey() == BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED) {
				Object newValue = event.getNewValue();
				if (newValue instanceof String) {
					boolean enabled = Boolean.valueOf(newValue.toString());
					if (!enabled) {
						if (event.getSource() instanceof ProjectPreferences) {
							String absolutePath = ((ProjectPreferences) event
									.getSource()).absolutePath();
							Path path = new Path(absolutePath);
							if(path.segmentCount() > 1) {
								String projectName = path.segments()[1];
								IProject project = ResourcesPlugin.getWorkspace()
										.getRoot().getProject(projectName);
								reparseRealizedSynchronousMessages(project);
							}
						}
					}
				}
			}
		}

		private void reparseSynchronousMessages(IProject project) {
			reparseRealizedAndNonRealizedSynchronousMessages(project, false);
		}

		private void reparseRealizedSynchronousMessages(IProject project) {
			reparseRealizedAndNonRealizedSynchronousMessages(project, true);
		}
		
		private void reparseRealizedAndNonRealizedSynchronousMessages(IProject project, boolean checkRealized) {
			IMarker[] markers = new IMarker[0];
			try {
				markers = project.findMarkers(Marker.PROBLEM, true,
						IResource.DEPTH_INFINITE);
			} catch (CoreException e) {
				CorePlugin
						.logError(
								"Unable to locate problem markers to clear parse errors.",
								e);
			}
			// note that we must do this from the markers rather than use the
			// local place holder map as on restart the map is not populated
			// until a place holder file is requested
			//
			// we need a map as we parse at one time rather than multiple times
			// and the parse process requires a model root which there can be
			// many
			final HashMap<ModelRoot, List<NonRootModelElement>> elementsToParse = new HashMap<ModelRoot, List<NonRootModelElement>>();
			final List<IMarker> markersToDelete = new ArrayList<IMarker>();
			for(IMarker marker : markers) {
				if(marker.getResource() instanceof IFile) {
					IFile resource = (IFile) marker.getResource();
					// if the resource does not exist skip
					if(!resource.exists()) {
						continue;
					}
					ModelElementID key = null;
					try {
						key = ModelElementID.createInstance(resource);
					} catch (CoreException e) {
						// do not log an error here, this will most likely mean
						// that we are looking at a problem marker that is not
						// related to parse issues
					}
					if(key != null) {
						key.resolve(); 
						String className = key.getType();
						final Ooaofooa modelRoot = key.getModelRoot();
						Object[] ids = new Object[key.getIdCount()];
						for(int i = 0; i < key.getIdCount(); i++) {
							ids[i] = UUID.fromString(key.getId(i));
						}
						try {
							final NonRootModelElement element = (NonRootModelElement) modelRoot
									.getInstanceList(Class.forName(className)).get(ids);
							if(element != null) {
								boolean synchronousMessage = isSynchronousMessage(element);
								if(synchronousMessage) {
									boolean reparse = false;
									boolean realized = isRealizedElement(element);
									if(realized && checkRealized) {
										// reparse this element
										reparse = true;
									} else {
										// reparse this element
										reparse = true;
									}
									if(reparse) {
										List<NonRootModelElement> list = elementsToParse.get(modelRoot);
										if(list == null) {
											list = new ArrayList<NonRootModelElement>();
										}
										list.add(element);
										elementsToParse.put(modelRoot, list);
										markersToDelete.add(marker);
									}
								}
							}
						} catch (ClassNotFoundException e) {
							CorePlugin
							.logError(
									"Unable to locate element class from given model element id.",
									e);
						}
					}
				}
			}
			if (!elementsToParse.isEmpty()) {
				BusyIndicator.showWhile(PlatformUI.getWorkbench().getDisplay(),
						new Runnable() {

							@Override
							public void run() {
								// clear all markers that are going to be
								// reparsed
								AbstractModelElementListener.setIgnoreResourceChangesMarker(true);
								try {
									WorkspaceJob job = new WorkspaceJob("Remove problem markers") {
										
										@Override
										public IStatus runInWorkspace(IProgressMonitor monitor)
												throws CoreException {
											for(IMarker marker : markersToDelete) {
												try {
													if(marker.exists()) {
														marker.delete();
														// also remove the oal file associated
														marker.getResource()
																.delete(true,
																		new NullProgressMonitor());
													}
												} catch (CoreException e) {
													CorePlugin.logError("Unable to delete marker.", e);
												}
											}
											return Status.OK_STATUS;
										}
									};
									job.setRule(ResourcesPlugin.getWorkspace().getRoot());
									job.schedule();
								} finally {
									AbstractModelElementListener.setIgnoreResourceChangesMarker(false);
								}
								AbstractModelElementListener.setIgnoreResourceChangesMarker(false);
								Set<ModelRoot> keySet = elementsToParse
										.keySet();
								for (Iterator<ModelRoot> iterator = keySet
										.iterator(); iterator.hasNext();) {
									ModelRoot modelRoot = iterator.next();
									AllActivityModifier aam = new AllActivityModifier(
											modelRoot, elementsToParse.get(
													modelRoot).toArray(),
											new NullProgressMonitor());
									aam.processAllActivities(IAllActivityModifier.PARSE);
								}
							}
						});
			}
		}
		
		private boolean isRealizedElement(NonRootModelElement element) {
			// is realized if contained in a component that
			// is realized
			UUID componentId = IdAssigner.NULL_UUID;
			if (element instanceof Operation_c) {
				componentId = ((Operation_c) element)
						.Getcontainingcomponentid();
			}
			if (element instanceof Bridge_c) {
				componentId = ((Bridge_c) element).Getcontainingcomponentid();
			}
			if (element instanceof Function_c) {
				componentId = ((Function_c) element).Getcontainingcomponentid();
			}
			if (element instanceof RequiredOperation_c) {
				componentId = ((RequiredOperation_c) element).Getcomponentid();
			}
			if (element instanceof ProvidedOperation_c) {
				componentId = ((ProvidedOperation_c) element).Getcomponentid();
			}
			if (componentId.equals(IdAssigner.NULL_UUID)) {
				return false;
			}
			Component_c component = (Component_c) element.getModelRoot()
					.getInstanceList(Component_c.class).get(componentId);
			if (component != null && component.getIsrealized()) {
				return true;
			}
			// otherwise it can be realized if a bridge with an EE
			// that has its realized path value set
			if (element instanceof Bridge_c) {
				ExternalEntity_c ee = ExternalEntity_c
						.getOneS_EEOnR19((Bridge_c) element);
				return ee.getIsrealized();
			}
			return false;
		}

		private boolean isSynchronousMessage(NonRootModelElement element) {
			if (element instanceof Operation_c) {
				return true;
			}
			if (element instanceof Bridge_c) {
				return true;
			}
			if (element instanceof Function_c) {
				return true;
			}
			if (element instanceof RequiredOperation_c) {
				return true;
			}
			if (element instanceof ProvidedOperation_c) {
				return true;
			}
			return false;
		}

    }
	
	/**
	 * @see PlaceHolderManager#reWritePlaceHolders(ArrayList list)
	 */
	static class PlaceHolderFileReWriter implements Runnable{
		List filesToReWrite;
		
		PlaceHolderFileReWriter(ArrayList placeHolderFiles){
			filesToReWrite = (List)placeHolderFiles.clone();
		}
		
		public void run(){
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IResourceRuleFactory ruleFactory = workspace.getRuleFactory();

			ISchedulingRule[] rules = new ISchedulingRule[filesToReWrite.size()];
			for (int i = 0; i < filesToReWrite.size(); i++) {
				rules[i] = ((PlaceHolderFileProxy)filesToReWrite.get(i)).getReCreateRule(ruleFactory);
			}
			
			MultiRule batchUpdationRule = new MultiRule(rules);
			
			try {
				workspace.run(new IWorkspaceRunnable(){
					public void run(IProgressMonitor monitor){
						monitor.setTaskName("Updating Place holders");
						reWriteAll(filesToReWrite);
					}
				}, batchUpdationRule, 0, null);
			} catch (CoreException e) {
				TextPlugin.logError("Error while recreating place holder with new root id", e); //$NON-NLS-1$
			}
		}

		private static void reWriteAll(List finalList){
			for (int i = 0; i < finalList.size(); i++) {
				try{
					((PlaceHolderFileProxy)finalList.get(i)).reCreate();
				}catch (CoreException e) {
					TextPlugin.logError("Error while recreating place holder with new root id", e); //$NON-NLS-1$
				}
			}
		}
	
	}
}
