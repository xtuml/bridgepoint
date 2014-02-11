//=====================================================================
//
//File:      $RCSfile: ResourceUpdaterModelChangeListener.java,v $
//Version:   $Revision: 1.16 $
//Modified:  $Date: 2013/01/10 22:54:14 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

package com.mentor.nucleus.bp.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.mentor.nucleus.bp.core.common.AttributeChangeModelDelta;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.IPersistenceHierarchyMetaData;
import com.mentor.nucleus.bp.core.common.ModelChangeAdapter;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;

/**
 * Listens for model-changes that have bearing on the filesystem
 * resources of the model elements involved.  
 */
public class ResourceUpdaterModelChangeListener extends ModelChangeAdapter 
{
	private IResource getAssociatedResourceFromName(String newName, String oldName, NonRootModelElement modelElement){
		ModelRoot.disableChangeNotification();
		IResource resource = null;
		try {
        IPersistenceHierarchyMetaData hmd = PersistenceManager.getHierarchyMetaData();
        if (hmd.isComponentRoot(modelElement)) {
		    hmd.setRootElementName(modelElement, oldName);
            resource = (IResource)((IAdaptable)modelElement).getAdapter(IResource.class);
            hmd.setRootElementName(modelElement, newName);
		}	
		}
		finally {
			ModelRoot.enableChangeNotification();
		}
		return resource; 
	}	
	
	public void modelElementAttributeChanged(ModelChangedEvent event, IModelDelta delta) {
        NonRootModelElement me = (NonRootModelElement)delta.getModelElement();        
        IPersistenceHierarchyMetaData hmd = PersistenceManager.getHierarchyMetaData();
        if (hmd.isComponentRoot(me)) {
            // if the component has no name, then it is a test-generated
            // instance and will have no resource to update
            if ( hmd.getRootElementName(me).equals("") ) //$NON-NLS-1$
            {
                // do nothing
                return;                
            }
            AttributeChangeModelDelta attrDelta = (AttributeChangeModelDelta) delta; 

            // if the change is a rename of the domain and
            // if the initial value is "", then there's nothing to change
            if (attrDelta.getAttributeName().equals("Name") && !attrDelta.getOldValue().toString().equals("") &&   //$NON-NLS-1$//$NON-NLS-2$
            		!attrDelta.getNewValue().equals(attrDelta.getOldValue())) {
            	
                // if the persistance file for the domain is still named with 
                // the domain's old name (the case where it would not 
                // be would be when the file was renamed from the filesystem, 
                // in which case this event is a result of the domain being renamed 
                // by the model-root's resource-change-listener to match the new 
                // filename)
                IFile file = (IFile) getAssociatedResourceFromName((String)attrDelta.getNewValue(), (String)attrDelta.getOldValue(), me);
                if (file != null) {
                    // rename the persistance file to match the new domain name
                    IPath path = file.getFullPath().removeLastSegments(1).append(
                          attrDelta.getNewValue().toString() + "." + Ooaofooa.MODELS_EXT); //$NON-NLS-1$
                    IFolder containingFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(
                            file.getFullPath().removeLastSegments(1));
                    IPath newContainingFolder = file.getFullPath().removeLastSegments(2).append(
                            attrDelta.getNewValue().toString());
                    try {
                        file.move(path, true, new NullProgressMonitor());
                        containingFolder.move(newContainingFolder, true, new NullProgressMonitor());
                    } catch (CoreException e) {
                        CorePlugin.logError("Could not rename model file to match new model name", e);
                    }
                }
           }            
        }
	}
	
	public void modelElementDeleted(ModelChangedEvent event, IModelDelta delta) {
        NonRootModelElement me = (NonRootModelElement)delta.getModelElement();        
        IPersistenceHierarchyMetaData hmd = PersistenceManager.getHierarchyMetaData();
        if (hmd.isComponentRoot(me)) {
            // if the component has no name, then it is a test-generated
            // instance and will have no resource to update
            if ( hmd.getRootElementName(me).equals("") ) //$NON-NLS-1$
            {
                // do nothing
                return;                
            }
            //if the component has a persistance file
            PersistableModelComponent target = PersistenceManager.getDefaultInstance().getComponent(me);
            if (target != null && target.isPersisted()) {
                // if component's project is open (as this deletion could be
                // a result of the project being closed, in which case we
                // shouldn't delete the component)
                IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(target.getFullPath());
                IFolder containingFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(file.getFullPath().removeLastSegments(1));
                
                IProject project = file.getProject();
                if (project.isOpen()) {
                    // delete it
                    try {
                        file.delete(IResource.FORCE | IResource.KEEP_HISTORY, new NullProgressMonitor());
                        containingFolder.delete(true, true, new NullProgressMonitor());
                    } catch (CoreException e) {
                        CorePlugin.logError("Could not delete xtUML model file", e);
                    }
                    target.deleteSelfAndChildren();
                }
            }
    		
    	}
    }    
}