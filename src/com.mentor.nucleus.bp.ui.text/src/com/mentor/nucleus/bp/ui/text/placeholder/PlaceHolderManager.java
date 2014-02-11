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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;

import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.AttributeChangeModelDelta;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.ui.text.AbstractModelElementListener;
import com.mentor.nucleus.bp.ui.text.ModelElementID;
import com.mentor.nucleus.bp.ui.text.TextPlugin;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInputFactory;
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
	
	class PlaceHolderStateSynchronizer extends AbstractModelElementListener{
		
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