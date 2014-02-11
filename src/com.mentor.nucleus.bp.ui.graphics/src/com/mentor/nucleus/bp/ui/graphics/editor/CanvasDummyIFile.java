//========================================================================
//
//File:      $RCSfile: CanvasDummyIFile.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/03/14 02:49:38 $
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
// This class provides an implementation of the IFile interface that
// does not have an underlying file.
//
//  All methods have the default implementation except:
//
//  getLocation() - returns an empty path
//  getCharsetFor() - returns workspace default
//  getResourceAttributes() - returns the following values
//
//  archive = false
//  readonly = true
//  executable = false
//
package com.mentor.nucleus.bp.ui.graphics.editor;

import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.jobs.ISchedulingRule;


class CanvasDummyIFile implements IFile
  {
	private ResourceAttributes resourceAttributes;

    /**
     * If supplied, is used by this dummy file instance to answer 
     * queries such as "what is this file's enclosing project?" 
     */
    private IFile backingFile;
    
    /**
     * Constructor.
     */
    public CanvasDummyIFile(IFile backingFile)
    {
        this.backingFile = backingFile;
    }
    
	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#appendContents(java.io.InputStream, boolean, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void appendContents(InputStream source, boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#appendContents(java.io.InputStream, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void appendContents(InputStream source, int updateFlags, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#create(java.io.InputStream, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void create(InputStream source, boolean force, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#create(java.io.InputStream, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void create(InputStream source, int updateFlags, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#createLink(org.eclipse.core.runtime.IPath, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void createLink(IPath localLocation, int updateFlags, IProgressMonitor monitor) throws CoreException {
	}

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IFile#createLink(java.net.URI, int, org.eclipse.core.runtime.IProgressMonitor)
     */
    public void createLink(URI location, int updateFlags, IProgressMonitor monitor) throws CoreException {
    }

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#delete(boolean, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void delete(boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getCharset()
	 */
	public String getCharset() throws CoreException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getCharset(boolean)
	 */
	public String getCharset(boolean checkImplicit) throws CoreException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getContentDescription()
	 */
	public IContentDescription getContentDescription() throws CoreException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getContents()
	 */
	public InputStream getContents() throws CoreException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getContents(boolean)
	 */
	public InputStream getContents(boolean force) throws CoreException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getFullPath()
	 */
	public IPath getFullPath() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getHistory(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public IFileState[] getHistory(IProgressMonitor monitor) throws CoreException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getName()
	 */
	public String getName() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#isReadOnly()
	 */
	public boolean isReadOnly() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#move(org.eclipse.core.runtime.IPath, boolean, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void move(IPath destination, boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#setCharset(java.lang.String)
	 */
	public void setCharset(String newCharset) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#setCharset(java.lang.String, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void setCharset(String newCharset, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#setContents(java.io.InputStream, boolean, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void setContents(InputStream source, boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#setContents(org.eclipse.core.resources.IFileState, boolean, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void setContents(IFileState source, boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#setContents(java.io.InputStream, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void setContents(InputStream source, int updateFlags, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#setContents(org.eclipse.core.resources.IFileState, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void setContents(IFileState source, int updateFlags, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources.IResourceProxyVisitor, int)
	 */
	public void accept(IResourceProxyVisitor visitor, int memberFlags) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources.IResourceVisitor)
	 */
	public void accept(IResourceVisitor visitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources.IResourceVisitor, int, boolean)
	 */
	public void accept(IResourceVisitor visitor, int depth, boolean includePhantoms) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources.IResourceVisitor, int, int)
	 */
	public void accept(IResourceVisitor visitor, int depth, int memberFlags) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#clearHistory(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void clearHistory(IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#copy(org.eclipse.core.runtime.IPath, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void copy(IPath destination, boolean force, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#copy(org.eclipse.core.runtime.IPath, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void copy(IPath destination, int updateFlags, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#copy(org.eclipse.core.resources.IProjectDescription, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void copy(IProjectDescription description, boolean force, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#copy(org.eclipse.core.resources.IProjectDescription, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void copy(IProjectDescription description, int updateFlags, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#createMarker(java.lang.String)
	 */
	public IMarker createMarker(String type) throws CoreException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#delete(boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void delete(boolean force, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#delete(int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void delete(int updateFlags, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#deleteMarkers(java.lang.String, boolean, int)
	 */
	public void deleteMarkers(String type, boolean includeSubtypes, int depth) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#exists()
	 */
	public boolean exists() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#findMarker(long)
	 */
	public IMarker findMarker(long id) throws CoreException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#findMarkers(java.lang.String, boolean, int)
	 */
	public IMarker[] findMarkers(String type, boolean includeSubtypes, int depth) throws CoreException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getFileExtension()
	 */
	public String getFileExtension() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getLocalTimeStamp()
	 */
	public long getLocalTimeStamp() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getLocation()
	 */
	public IPath getLocation() {
		return new Path ("");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getMarker(long)
	 */
	public IMarker getMarker(long id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getModificationStamp()
	 */
	public long getModificationStamp() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getParent()
	 */
	public IContainer getParent() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getPersistentProperty(org.eclipse.core.runtime.QualifiedName)
	 */
	public String getPersistentProperty(QualifiedName key) throws CoreException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getProject()
	 */
	public IProject getProject() {
		return backingFile != null ? backingFile.getProject() : null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getProjectRelativePath()
	 */
	public IPath getProjectRelativePath() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getRawLocation()
	 */
	public IPath getRawLocation() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getSessionProperty(org.eclipse.core.runtime.QualifiedName)
	 */
	public Object getSessionProperty(QualifiedName key) throws CoreException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getType()
	 */
	public int getType() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getWorkspace()
	 */
	public IWorkspace getWorkspace() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#isAccessible()
	 */
	public boolean isAccessible() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#isDerived()
	 */
	public boolean isDerived() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#isLocal(int)
	 */
	public boolean isLocal(int depth) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#isLinked()
	 */
	public boolean isLinked() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#isPhantom()
	 */
	public boolean isPhantom() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#isSynchronized(int)
	 */
	public boolean isSynchronized(int depth) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#isTeamPrivateMember()
	 */
	public boolean isTeamPrivateMember() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#move(org.eclipse.core.runtime.IPath, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void move(IPath destination, boolean force, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#move(org.eclipse.core.runtime.IPath, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void move(IPath destination, int updateFlags, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#move(org.eclipse.core.resources.IProjectDescription, boolean, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void move(IProjectDescription description, boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#move(org.eclipse.core.resources.IProjectDescription, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void move(IProjectDescription description, int updateFlags, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#refreshLocal(int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void refreshLocal(int depth, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#setDerived(boolean)
	 */
	public void setDerived(boolean isDerived) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#setLocal(boolean, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void setLocal(boolean flag, int depth, IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#setLocalTimeStamp(long)
	 */
	public long setLocalTimeStamp(long value) throws CoreException {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#setPersistentProperty(org.eclipse.core.runtime.QualifiedName, java.lang.String)
	 */
	public void setPersistentProperty(QualifiedName key, String value) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#setReadOnly(boolean)
	 */
	public void setReadOnly(boolean readOnly) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#setSessionProperty(org.eclipse.core.runtime.QualifiedName, java.lang.Object)
	 */
	public void setSessionProperty(QualifiedName key, Object value) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#setTeamPrivateMember(boolean)
	 */
	public void setTeamPrivateMember(boolean isTeamPrivate) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#touch(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void touch(IProgressMonitor monitor) throws CoreException {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.ISchedulingRule#contains(org.eclipse.core.runtime.jobs.ISchedulingRule)
	 */
	public boolean contains(ISchedulingRule rule) {
		return this == rule;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.ISchedulingRule#isConflicting(org.eclipse.core.runtime.jobs.ISchedulingRule)
	 */
	public boolean isConflicting(ISchedulingRule rule) {
		return this == rule;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getEncoding()
	 */
	public int getEncoding() throws CoreException {
		return 0;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getCharsetFor()
	 */
	public String getCharsetFor(Reader reader) throws CoreException {
		return ResourcesPlugin.getWorkspace().getRoot().getDefaultCharset();
	}
	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getResourceAttributes()
	 */
	public ResourceAttributes getResourceAttributes() {
		resourceAttributes.setArchive(false);
		resourceAttributes.setExecutable(false);
		resourceAttributes.setReadOnly(true);
		return resourceAttributes;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#revertModificationStamp()
	 */
	public void revertModificationStamp(long value) throws CoreException {
	}
	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#setResourceAttributes()
	 */
	public void setResourceAttributes(ResourceAttributes attributes) throws CoreException {
	}

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IResource#getRawLocationURI()
     */
    public URI getRawLocationURI() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IResource#createProxy()
     */
    public IResourceProxy createProxy() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IResource#findMaxProblemSeverity(java.lang.String, boolean, int)
     */
    public int findMaxProblemSeverity(String type, boolean includeSubtypes,
            int depth) throws CoreException {
        return 0;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IResource#getLocationURI()
     */
    public URI getLocationURI() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IResource#isLinked(int)
     */
    public boolean isLinked(int options) {
        return false;
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