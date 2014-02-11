// ========================================================================
//
//File: $RCSfile: PlaceHolderEntry.java,v $
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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.ui.WorkbenchException;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.ui.text.IModelElementEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.ModelAdapter;
import com.mentor.nucleus.bp.ui.text.ModelElementID;
import com.mentor.nucleus.bp.ui.text.TextPlugin;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditorInputFactory;

public class PlaceHolderEntry {
	
	public static String DELETE_THREAD_NAME = "__PlaceHolderManger_deleteFile_Thread"; //$NON-NLS-1$
	public static String DELETE_MARKER_THREAD_NAME = "__PlaceHolderManger_deleteMarker_Thread"; //$NON-NLS-1$
	
	private ModelElementID modelElementID;
	private String lastModelElementName;

	private PlaceHolderManager placeHolderManager = null;
	private List placeHolderFiles = new Vector(2);

    PersistableModelComponent component = null;

	PlaceHolderEntry(PlaceHolderManager manager){
		placeHolderManager = manager;
	}

	ModelElementID getModelElementID() {
		return modelElementID;
	}

	NonRootModelElement getModelElement(){
		return modelElementID.resolve();
	}
	
    PersistableModelComponent getComponent(){
        return component;
    }
	
	Ooaofooa getModelRoot(){
		return modelElementID.getModelRoot();
	}
	
	IPath getPlaceHolderLocation(){
        PersistableModelComponent pmc = getComponent();
        if ( pmc == null) {
            if(modelElementID != null && modelElementID.getComponentPath() != null){
            	return new Path(modelElementID.getComponentPath());
            }else{
            	return null;
            }
		}
        return pmc.getContainingDirectoryPath();
	}

	String getLastModelElementName(){
		return lastModelElementName;
	}
	
	IFile getPlaceHolderFile(ModelElementID id, String extension, Object requester, boolean toCreateNew){
		PlaceHolderFileProxy placeHolderFile = getPlaceHolderFileFor(extension); 

		if(placeHolderFile == null && toCreateNew){
			placeHolderFile = createPlaceHolderFile(id, extension, null);
		}
		
		if(placeHolderFile != null){
			placeHolderFile.addReference(requester);
		}
		
		return placeHolderFile;
	}
	
	public void releasePlaceHolderFile(String extension, Object requester){
		PlaceHolderFileProxy file = getPlaceHolderFileFor(extension);
		if(file != null){
			file.removeReference(requester);
		}
	}
	
	PlaceHolderFileProxy getPlaceHolderFileFor(String extension){
		for (int i = 0; i < placeHolderFiles.size(); i++) {
			PlaceHolderFileProxy file = (PlaceHolderFileProxy)placeHolderFiles.get(i);
			if(extension.equals(file.getFileExtension())){
				return file;
			}
		}
		return null;
	}
	
	PlaceHolderFileProxy[] getPlaceHolderFiles(){
		synchronized(placeHolderFiles){
			PlaceHolderFileProxy[] placeHolders = new PlaceHolderFileProxy[placeHolderFiles.size()];
			for(int i=0; i<placeHolders.length; i++){
				placeHolders[i] = (PlaceHolderFileProxy)placeHolderFiles.get(i);
			}
			return placeHolders;
		}
	}
	
	boolean isSafeToRemove(){
		return (placeHolderFiles.size() == 0);
	}
	
	void init(IFile file, ModelElementID id){
		boolean toAdd = true;
		for (int i = 0; i < placeHolderFiles.size(); i++) {
			if(placeHolderFiles.get(i).equals(file)){
				toAdd = false;
			}
		}
		if(toAdd){
			createPlaceHolderFile(id, file);
		}
	}
	
	void updateName(){
		for (int i = 0; i < placeHolderFiles.size(); i++) {
			PlaceHolderFileProxy file = (PlaceHolderFileProxy)placeHolderFiles.get(i);
			file.updateName();
		}
	}
	
	/**
	 * Deletes all the created files. 
	 */
	
	void dispose() throws CoreException{
		PlaceHolderFileProxy[] files = getPlaceHolderFiles();
		for(int i=0; i<files.length; i++){
			files[i].dispose();
		}
	}
	
	
	static ThreadedDeletor deletor = new ThreadedDeletor();


	// Factory methods for PlaceHolderFileProxy
	private PlaceHolderFileProxy createPlaceHolderFile(ModelElementID aModelElementID, IFile existingFile){
		return createPlaceHolderFile(aModelElementID, existingFile.getFileExtension(), existingFile);
	}
	
	private PlaceHolderFileProxy createPlaceHolderFile(ModelElementID aModelElementID, String extension, IFile existingFile){
		NonRootModelElement editableME = aModelElementID.resolve();
        lastModelElementName = aModelElementID.getLastValidName();
		
		if(editableME == null && lastModelElementName == null){
			throw new IllegalArgumentException("Cannot create place holder for ModelElementID that is un-resolvable and does not have lastname"); //$NON-NLS-1$
		}
		
		modelElementID = aModelElementID;
		
        if(editableME != null){
            component = PersistenceManager.getComponent(editableME);            
        }else{
            component = PersistenceManager.findOrCreateComponent(new Path(modelElementID.getComponentPath()));
		}
		
		IPath placeHolderPath = getPlaceHolderLocation();
		if(placeHolderPath == null){
			return null;
		}
		
		if(existingFile == null){
			existingFile = placeHolderManager.getFileWithUniqueName(aModelElementID, placeHolderPath, PlaceHolderManager.getLegalFileName(lastModelElementName), extension);
		}
		
		if(existingFile.getLocation() == null && existingFile.getProject().exists()){
			return null;
		}

		return new PlaceHolderFileProxy(existingFile);
	}
	
	public class PlaceHolderFileProxy implements IFile {
		private ModelElementID requiredModelElementID;
		
		private int referenceCount = 0;
		
		private IFile originalFile;
		
		private PlaceHolderFileProxy(IFile aOriginalFile) {

			originalFile = aOriginalFile;

			requiredModelElementID = getRequiredModelElementID();
			
			placeHolderFiles.add(this);
			
		}
		
		public PlaceHolderManager getPlaceHolderManager() {
			return placeHolderManager;
		}
		
		/* (non-Javadoc)
         * @see org.eclipse.core.resources.IFile#createLink(java.net.URI, int, org.eclipse.core.runtime.IProgressMonitor)
         */
        public void createLink(URI location, int updateFlags,
                IProgressMonitor monitor) throws CoreException {
        }

        /* (non-Javadoc)
         * @see org.eclipse.core.resources.IResource#createProxy()
         */
        public IResourceProxy createProxy() {
            return null;
        }

        /* (non-Javadoc)
         * @see org.eclipse.core.resources.IResource#getLocationURI()
         */
        public URI getLocationURI() {
            return null;
        }

        /* (non-Javadoc)
         * @see org.eclipse.core.resources.IResource#getRawLocationURI()
         */
        public URI getRawLocationURI() {
            return null;
        }

        /* (non-Javadoc)
         * @see org.eclipse.core.resources.IResource#isLinked(int)
         */
        public boolean isLinked(int options) {
            return false;
        }

        /* (non-Javadoc)
         * @see org.eclipse.core.resources.IResource#findMaxProblemSeverity(java.lang.String, boolean, int)
         */
        public int findMaxProblemSeverity(String type, boolean includeSubtypes,
                int depth) throws CoreException {
            return 0;
        }

        public NonRootModelElement getModelElement(){
			return modelElementID.resolve();
		}
		
		ModelElementID getRequiredModelElementID(){
			if(requiredModelElementID == null){
				NonRootModelElement resolvedModelElement = getModelElement();
				if(resolvedModelElement != null){
					NonRootModelElement requiredME = getFactory().getRequiredModelElement(resolvedModelElement);
					if(requiredME == resolvedModelElement){
						requiredModelElementID = modelElementID;
					}else{
						requiredModelElementID = ModelAdapter.getModelElementAdapter(requiredME).createModelElementID(requiredME);
					}
				}
			}
			
			return requiredModelElementID;
		}

		private IModelElementEditorInputFactory getFactory(){
			String fileExtension = getFileExtension();
			if(fileExtension.equalsIgnoreCase(ActivityEditorInputFactory.PLACEHOLDER_EXTENSION)){
				return ActivityEditorInputFactory.getDefaultInstance();
			}else if(fileExtension.equalsIgnoreCase(DescriptionEditorInputFactory.PLACEHOLDER_EXTENSION)){
				return DescriptionEditorInputFactory.getDefaultInstance();
			}else{
				throw new IllegalArgumentException("Unsupported file extension:" + fileExtension); //$NON-NLS-1$
			}
		}
		
		private void createOriginalFileIfNotPresent() throws CoreException{
			if(!originalFile.exists()){
								
				getModelElementID().saveTo(originalFile);
				originalFile.setDerived(true);
				originalFile.getResourceAttributes().setReadOnly(true);
			}
		}
		
		void updateSystemProjectName(String name){
			IPath newPath = new Path(name);
			newPath = newPath.append(originalFile.getFullPath().removeFirstSegments(1));

			originalFile = originalFile.getWorkspace().getRoot().getFile(newPath); 
		}
		
		void reCreate() throws CoreException{
			if(!originalFile.exists()){
				throw new IllegalStateException("Underlying file does not exist");
			}

			//make it writable (temporarily - we restore in createFile()
			originalFile.getResourceAttributes().setReadOnly(false);
			
            // serialize this object to the existing, underlying 
            // java.io file, overwriting the previous contents;
            // note that we don't delete the file and then re-create it,
            // because the deletion would cause Eclipse to 
            // destroy any associated markers
			try {
				originalFile.setContents(getModelElementID().createSerializedInputStream(), true, false, null);
			} catch (IOException e) {
				throw new WorkbenchException("Error while recreating place holder file" + getName(), e);
			}
			originalFile.getResourceAttributes().setReadOnly(true);
		}
		
		ISchedulingRule getReCreateRule(IResourceRuleFactory factory){
			return factory.modifyRule(originalFile);
		}

		private void addReference(Object requester){
			if(requester == null){
				throw new IllegalArgumentException("Requestor cannot be null");
			}

			referenceCount++;			
		}
		
		private void removeReference(Object requester){
			referenceCount--;
			if(referenceCount == 0 && isSafeToDelete()){
				dispose();
			}
		}
		
		private boolean isSafeToDelete(){
			boolean safe = true;
			IMarker[] markers = null;
			try{
				markers = findMarkers(IMarker.MARKER, true, IResource.DEPTH_ZERO);
				if (markers != null && markers.length > 0) {
					safe = false;
				}
				markers = findMarkers("com.mentor.nucleus.bp.ui.text.stepmarker", true, IResource.DEPTH_ZERO);
				if (markers != null && markers.length > 0) {
					safe = false;
				}
			}catch(CoreException e){
				TextPlugin.logError("Exception while finding markers" , e); //$NON-NLS-1$
			}
			return safe;
		}
		
		private void updateName(){
			IPath placeHolderPath = getPlaceHolderLocation();
			if(placeHolderPath == null){
				throw new IllegalStateException("place holder path is null for " + modelElementID.getModelRootID()); //$NON-NLS-1$
			}
			
            // since we recieve notification after component structure synchronizer
            // File must have moved already, we now just need to determine if we
            // need to rename the file. 
			
            // we shall use the current container path because it must have been 
            // moved/renamed already.
            
            IPath oldFilePath = placeHolderPath.append(originalFile.getName());
            originalFile = ResourcesPlugin.getWorkspace().getRoot().getFile(oldFilePath);
            
            String newFileName = PlaceHolderManager.getLegalFileName(getModelElementID().getLastValidName());
            
            if (!PlaceHolderManager.getLegalFileName(getLastModelElementName()).equals(newFileName)){
			    String proposedFileName = PlaceHolderManager.getLegalFileName(getLastModelElementName());
                IFile newFile = placeHolderManager.getFileWithUniqueName(getModelElementID(), placeHolderPath, proposedFileName, getFileExtension());
				if (originalFile.exists() && !originalFile.getName().equals(newFile.getName())){
					try {
						originalFile.move(newFile.getFullPath(), true, true, null);
                        originalFile = ResourcesPlugin.getWorkspace().getRoot().getFile(oldFilePath.removeLastSegments(1).append(newFileName + "." +getFileExtension()));
					} catch (CoreException e) {
						TextPlugin.logError("Error while renaming file " + originalFile.getFullPath(), e); //$NON-NLS-1$
					}
				}
			}
		}
		
		void dispose(){
			if(originalFile.exists()){
				try {
					deleteOriginalFile();
				} catch (CoreException e) {
					TextPlugin.logError("Error while removing original file", e); //$NON-NLS-1$
				}
			}
			placeHolderFiles.remove(this);			
		}

		/***************************************************************************
		 * handling events
		 */
		
		void handleMarkersUpdated(IResourceDelta delta){
			if(originalFile.exists() && isSafeToDelete()){
				try {
					deleteOriginalFile();
				} catch (CoreException e) {
					TextPlugin.logError("error while deleting file", e); //$NON-NLS-1$
				}
			}
		}
		
		/***************************************************************************
		 * Deferred deletion 
		 */
		
		private void deleteOriginalFile() throws CoreException {
			if (!originalFile.getWorkspace().isTreeLocked() && !placeHolderManager.parseInProgress()) {
				originalFile.delete(true, false, null);
			} else {
				deletor.scheduleDeletion(this);
			}
		}
		
		/***************************************************************************
		 * Methods that require custom implementation for the PlaceHolderEntry files 
		 * handling   
		 */
		public IMarker createMarker(String type) throws CoreException {
			createOriginalFileIfNotPresent();
			return originalFile.createMarker(type);
		}

		public void deleteMarkers(final String type, final boolean includeSubtypes, 
            final int depth) throws CoreException {
			if(originalFile.exists()){
                // if the workspace resource tree is locked (likely because
                // this code is being called during a resource change
                // notification)
                if (CorePlugin.getWorkspace().isTreeLocked()) {
                    // do the enclosed in a new thread
                    (new Thread(DELETE_MARKER_THREAD_NAME) {
                        public void run() {
                            // wait for the tree to be unlocked
                            WorkspaceUtil.waitForWorkspaceTreeToUnlock(false);
    
                            // do the deletion
                            try {
                    			originalFile.deleteMarkers(type, includeSubtypes, depth);
                            } catch (CoreException e) {
                                TextPlugin.logError("Could not delete problem markers for changed activity", e);
                            }
                        }
                    }).start();
                }

                // otherwise
                else {
                    // do the deletion right away
                    try {
                        originalFile.deleteMarkers(type, includeSubtypes, depth);
                    } catch (CoreException e) {
                        TextPlugin.logError("Could not delete problem markers for changed activity", e);
                    }
                }
			}
			// deletion of file if no more markers exist is done by handleMarkersUpdated,
			// which listen to resource changes.
		}
		
		public IMarker[] findMarkers(String type, boolean includeSubtypes, int depth)
				throws CoreException {
			if (!originalFile.exists()){
				return new IMarker[0];
			}
			return originalFile.findMarkers(type, includeSubtypes, depth);
		}
		
		public IMarker findMarker(long id) {
			if (!originalFile.exists()){
				return null;
			}
			try {
				return originalFile.findMarker(id);
			} catch (CoreException e) {
				TextPlugin.logError("Could not find marker for: " + originalFile.getName(), e);
			}
			return null;
		}

		public IMarker getMarker(long id) {
			return originalFile.getMarker(id);
		}
		
		public int hashCode() {
			if (!originalFile.exists()){
				return originalFile.getFullPath().toString().hashCode();
			}
			return originalFile.hashCode();
		}
		
		public boolean equals(Object target) {
			if(target instanceof IFile){
				return originalFile.getFullPath().equals(((IFile)target).getFullPath());
			}
			return false;
		}
		
		public boolean isDerived() {
			//A placeholder file is always derived.
			return true;
		}

		/***************************************************************************
		 * Methods that are not allowed to be called
		 */
		public void copy(IProjectDescription destDesc, int updateFlags,
				IProgressMonitor monitor) throws CoreException {
			throw new Error("Not allowed"); //$NON-NLS-1$
		}
		public void copy(IProjectDescription destDesc, boolean force,
				IProgressMonitor monitor) throws CoreException {
			throw new Error("Not allowed"); //$NON-NLS-1$
		}
		public void copy(IPath destination, int updateFlags,
				IProgressMonitor monitor) throws CoreException {
			throw new Error("Not allowed"); //$NON-NLS-1$
		}
		public void copy(IPath destination, boolean force, IProgressMonitor monitor) throws CoreException {
			throw new Error("Not allowed"); //$NON-NLS-1$
		}
		public void delete(boolean force, IProgressMonitor monitor) throws CoreException {
			throw new Error("Not allowed; use PlaceHolderManager.releasePlaceHolder() method"); //$NON-NLS-1$
		}
		public void delete(int updateFlags, IProgressMonitor monitor) throws CoreException {
			throw new Error("Not allowed; use PlaceHolderManager.releasePlaceHolder() method"); //$NON-NLS-1$
		}
		public void delete(boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
			throw new Error("Not allowed; use PlaceHolderManager.releasePlaceHolder() method"); //$NON-NLS-1$
		}
		public void move(IProjectDescription description, boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
			throw new Error("Not allowed"); //$NON-NLS-1$
		}
		public void move(IProjectDescription description, int updateFlags, IProgressMonitor monitor) throws CoreException {
			throw new Error("Not allowed"); //$NON-NLS-1$
		}
		public void move(IPath destination, boolean force, IProgressMonitor monitor) throws CoreException {
			throw new Error("Not allowed"); //$NON-NLS-1$
		}
		public void move(IPath destination, boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
			throw new Error("Not allowed"); //$NON-NLS-1$
		}
		public void move(IPath destination, int updateFlags, IProgressMonitor monitor) throws CoreException {
			throw new Error("Not allowed"); //$NON-NLS-1$
		}
		public void setDerived(boolean isDerived) throws CoreException {
			throw new Error("Not allowed, A placeholder file is always a derived file"); //$NON-NLS-1$
		}

		/***************************************************************************
		 * Methods that shall delicate its functionality to original implementation
		 */
		public void appendContents(InputStream content, boolean force,
				boolean keepHistory, IProgressMonitor monitor) throws CoreException {
			originalFile.appendContents(content, force, keepHistory, monitor);
		}
		public void appendContents(InputStream content, int updateFlags,
				IProgressMonitor monitor) throws CoreException {
			originalFile.appendContents(content, updateFlags, monitor);
		}
		public void create(InputStream content, boolean force,
				IProgressMonitor monitor) throws CoreException {
			originalFile.create(content, force, monitor);
		}
		public void create(InputStream content, int updateFlags,
				IProgressMonitor monitor) throws CoreException {
			originalFile.create(content, updateFlags, monitor);
		}
		public String getCharset() throws CoreException {
			return originalFile.getCharset();
		}
		public String getCharset(boolean checkImplicit) throws CoreException {
			return originalFile.getCharset(checkImplicit);
		}
		public IContentDescription getContentDescription() throws CoreException {
			return originalFile.getContentDescription();
		}
		public InputStream getContents() throws CoreException {
			return originalFile.getContents();
		}
		public InputStream getContents(boolean force) throws CoreException {
			return originalFile.getContents(force);
		}
		//We are using this deprecated API because we are just delegating the control
		public int getEncoding() throws CoreException {
			return originalFile.getEncoding();
		}
		public IFileState[] getHistory(IProgressMonitor monitor)
				throws CoreException {
			return originalFile.getHistory(monitor);
		}
		public int getType() {
			return originalFile.getType();
		}
		public void refreshLocal(int depth, IProgressMonitor monitor)
				throws CoreException {
			originalFile.refreshLocal(depth, monitor);
		}
		public void setCharset(String newCharset, IProgressMonitor monitor)
				throws CoreException {
			originalFile.setCharset(newCharset, monitor);
		}
		//We are using this deprecated API because we are just delegating the control
		public void setCharset(String newCharset) throws CoreException {
			originalFile.setCharset(newCharset);
		}
		public void setContents(IFileState source, boolean force,
				boolean keepHistory, IProgressMonitor monitor) throws CoreException {
			originalFile.setContents(source, force, keepHistory, monitor);
		}
		public void setContents(IFileState content, int updateFlags,
				IProgressMonitor monitor) throws CoreException {
			originalFile.setContents(content, updateFlags, monitor);
		}
		public void setContents(InputStream content, boolean force,
				boolean keepHistory, IProgressMonitor monitor) throws CoreException {
			originalFile.setContents(content, force, keepHistory, monitor);
		}
		public void setContents(InputStream content, int updateFlags,
				IProgressMonitor monitor) throws CoreException {
			originalFile.setContents(content, updateFlags, monitor);
		}
		public long setLocalTimeStamp(long value) throws CoreException {
			return originalFile.setLocalTimeStamp(value);
		}
		public void accept(IResourceProxyVisitor visitor, int memberFlags)
				throws CoreException {
			originalFile.accept(visitor, memberFlags);
		}
		public void accept(IResourceVisitor visitor) throws CoreException {
			originalFile.accept(visitor);
		}
		public void accept(IResourceVisitor visitor, int depth,
				boolean includePhantoms) throws CoreException {
			originalFile.accept(visitor, depth, includePhantoms);
		}
		public void accept(IResourceVisitor visitor, int depth, int memberFlags)
				throws CoreException {
			originalFile.accept(visitor, depth, memberFlags);
		}
		public void clearHistory(IProgressMonitor monitor) {
			try {
				originalFile.clearHistory(monitor);
			} catch (CoreException e) {
				TextPlugin.logError("Could not clear local history for file: " + originalFile.getName(), e);
			}
		}
		public boolean contains(ISchedulingRule rule) {
			return originalFile.contains(rule);
		}
		public void createLink(IPath localLocation, int updateFlags,
				IProgressMonitor monitor) throws CoreException {
			originalFile.createLink(localLocation, updateFlags, monitor);
		}
		public boolean exists() {
			return originalFile.exists();
		}
		public String getFileExtension() {
			return originalFile.getFileExtension();
		}
		public IPath getFullPath() {
			return originalFile.getFullPath();
		}
		public long getLocalTimeStamp() {
			return originalFile.getLocalTimeStamp();
		}
		public IPath getLocation() {
			return originalFile.getLocation();
		}
		public long getModificationStamp() {
			return originalFile.getModificationStamp();
		}
		public String getName() {
			return originalFile.getName();
		}
		public IContainer getParent() {
			return originalFile.getParent();
		}
		public String getPersistentProperty(QualifiedName key) throws CoreException {
			return originalFile.getPersistentProperty(key);
		}
		public IProject getProject() {
			return originalFile.getProject();
		}
		public IPath getProjectRelativePath() {
			return originalFile.getProjectRelativePath();
		}
		public IPath getRawLocation() {
			return originalFile.getRawLocation();
		}
		public Object getSessionProperty(QualifiedName key) throws CoreException {
			return originalFile.getSessionProperty(key);
		}
		public IWorkspace getWorkspace() {
			return originalFile.getWorkspace();
		}
		public boolean isAccessible() {
			return originalFile.isAccessible();
		}
		public boolean isConflicting(ISchedulingRule rule) {
			return originalFile.isConflicting(rule);
		}
		public boolean isLocal(int depth) {
			return originalFile.isLocal(depth);
		}
		public boolean isPhantom() {
			return originalFile.isPhantom();
		}
		public boolean isReadOnly() {
			return originalFile.isReadOnly();
		}
		public boolean isSynchronized(int depth) {
			return originalFile.isSynchronized(depth);
		}
		public void setLocal(boolean flag, int depth, IProgressMonitor monitor)
				throws CoreException {
			originalFile.setLocal(flag, depth, monitor);
		}
		public void setPersistentProperty(QualifiedName key, String value)
				throws CoreException {
			originalFile.setPersistentProperty(key, value);
		}
		public void setReadOnly(boolean readonly) {
			originalFile.getResourceAttributes().setReadOnly(readonly);
		}
		public void setSessionProperty(QualifiedName key, Object value)
				throws CoreException {
			originalFile.setSessionProperty(key, value);
		}
		public String toString() {
			return originalFile.toString();
		}
		public void touch(IProgressMonitor monitor) throws CoreException {
			originalFile.touch(monitor);
		}
		public boolean isLinked() {
			return originalFile.isLinked();
		}
		public boolean isTeamPrivateMember() {
			return originalFile.isTeamPrivateMember();
		}
		public void setTeamPrivateMember(boolean isTeamPrivate)
				throws CoreException {
			originalFile.setTeamPrivateMember(isTeamPrivate);
		}
		public Object getAdapter(Class adapter) {
			return originalFile.getAdapter(adapter);
		}

		public String getCharsetFor(Reader reader) throws CoreException {
			return ResourcesPlugin.getWorkspace().getRoot().getDefaultCharset();
		}

		public ResourceAttributes getResourceAttributes() {
			return originalFile.getResourceAttributes();
	}
	
		public void revertModificationStamp(long value) throws CoreException {
			originalFile.revertModificationStamp(value);
		}

		public void setResourceAttributes(ResourceAttributes attributes) throws CoreException {
			originalFile.setResourceAttributes(attributes);
		}

        @Override
        public Map getPersistentProperties() throws CoreException {
            return null;
        }

        @Override
        public Map getSessionProperties() throws CoreException {
            return null;
        }

        @Override
        public boolean isDerived(int options) {
            return false;
        }

        @Override
        public boolean isHidden() {
            return false;
        }

        @Override
        public void setHidden(boolean isHidden) throws CoreException {
        }

        @Override
        public boolean isHidden(int options) {
            return false;
        }

        @Override
        public boolean isTeamPrivateMember(int options) {
            return false;
        }

		@Override
		public IPathVariableManager getPathVariableManager() {
			return null;
		}

		@Override
		public boolean isVirtual() {
			return false;
		}

		@Override
		public void setDerived(boolean isDerived, IProgressMonitor monitor)
				throws CoreException {
		}
	}
	
	static class ThreadedDeletor implements Runnable {
		Thread deletingThread = null;

		List filesToDelete = new Vector();

		public ThreadedDeletor() {

		}

		public void scheduleDeletion(PlaceHolderFileProxy file) {
			synchronized (filesToDelete) {
				filesToDelete.add(file);
			}
			if (deletingThread == null) {
				deletingThread = new Thread(this, PlaceHolderEntry.DELETE_THREAD_NAME);
				deletingThread.setPriority(2);
				deletingThread.start();
			}

		}

		public void run() {
			while (filesToDelete.size() > 0) {
				for (int i = 0; i < filesToDelete.size(); i++) {
					PlaceHolderFileProxy file = (PlaceHolderFileProxy) filesToDelete
							.get(0);
					try {
						
						if (file.getWorkspace().isTreeLocked() || file.getPlaceHolderManager().parseInProgress()) {
							Thread.sleep(200);
							continue;
						}
						file.originalFile.delete(true, false, null);							
						filesToDelete.remove(0);
					} catch (InterruptedException e) {
						return;
					} catch (CoreException e) {
						TextPlugin.logError("Could not delete file", e); //$NON-NLS-1$
					}
				}
			}
			deletingThread = null;
		}

	}
	
}