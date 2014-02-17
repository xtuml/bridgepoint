//=====================================================================
//
//	File:      $RCSfile: AbstractModelElementListener.java,v $
//	Version:   $Revision: 1.15 $
//	Modified:  $Date: 2013/05/10 13:25:24 $
//
//	(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//	=====================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
//	=====================================================================
package com.mentor.nucleus.bp.ui.text;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.AttributeChangeModelDelta;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.IPersistenceHierarchyMetaData;
import com.mentor.nucleus.bp.core.common.ModelChangeAdapter;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;

public abstract class AbstractModelElementListener extends ModelChangeAdapter
        implements IResourceChangeListener, IResourceDeltaVisitor {

    /**
     * Is set to true when a pre-reload-all event arrives, to instruct this
     * listener to ignore the next delete-all event, which is issued when the
     * reload occurs. In other words, this flag lets this listener know the
     * model is not going away, but is only being reloaded.
     */
    private boolean ignoreNextDeleteAll = false;
    private static boolean ignoreResourceChangesMarker = false;

    public void resourceChanged(IResourceChangeEvent event) {
    	if(!ignoreResourceChangesMarker) {
	        IResourceDelta delta = event.getDelta();
	        try {
	            if (delta != null) {
	                delta.accept(this);
	            }
	        } catch (CoreException e) {
	            TextPlugin.logError("Error while handling resource change", e); //$NON-NLS-1$
	        }
    	}
    }

    public boolean visit(IResourceDelta delta) throws CoreException {
        if (delta != null) {
            if (delta.getResource() instanceof IProject) {
                if (delta.getKind() == IResourceDelta.ADDED
                        && (delta.getFlags() & IResourceDelta.MOVED_FROM) != 0) {
                    return false;
                }
            } else if (delta.getResource() instanceof IFile) {
                switch (delta.getKind()) {
                case IResourceDelta.ADDED:
                case IResourceDelta.CHANGED:
                    if ((delta.getFlags() & IResourceDelta.MARKERS) != 0) {
                        handleResourceMarkersChanged(delta);
                    }
                    break;
                }
            }
        }
        return true;
    }

    public void modelElementLoaded(ModelChangedEvent event) {
        Object source = event.getModelElement();
        handleComponentLoaded(event);
    }

    public void modelElementUnloaded(ModelChangedEvent event) {
        if (ignoreNextDeleteAll) {
            ignoreNextDeleteAll = false;
            return;
        }

        handleComponentUnloaded(event);
    }

    public void modelElementAboutToBeReloaded(ModelChangedEvent event) {
        // this event signals that a model-unload event is forthcoming,
        // but this synchronizer should ignore it, because it's caused
        // by a reload
        ignoreNextDeleteAll = true;
    }

    public void modelElementReloaded(ModelChangedEvent event) {
        handleModelReloaded(event);
    }

    public void modelElementAttributeChanged(ModelChangedEvent event,
            IModelDelta delta) {
        NonRootModelElement changedME = (NonRootModelElement)delta.getModelElement();
        AttributeChangeModelDelta changeDelta = (AttributeChangeModelDelta) delta;

        IPersistenceHierarchyMetaData metaData = PersistenceManager.getHierarchyMetaData();
        if(metaData.isComponentRoot(changedME) && changeDelta.getAttributeName().equalsIgnoreCase("name")){
            PersistableModelComponent renamedComponent = PersistenceManager.getDefaultInstance().getComponent(changedME);
            if(renamedComponent != null){
                handleComponentRenamed(renamedComponent, (String)changeDelta.getOldValue(), (String)changeDelta.getNewValue());
            }
        }else{
            ModelAdapter.IModelElementAdapter modelElementAdapter = ModelAdapter.getModelElementAdapter(changedME);
            if (modelElementAdapter == null) {
                return;
            }
            
            ModelElementID modelElementID = modelElementAdapter.createModelElementID(changedME);
            if (modelElementID != null && modelElementID.resolve() != null) {
                handleModelElementAttributeChanged(event, changeDelta,
                        modelElementAdapter.createModelElementID(changedME));
            }
        }
    }

    public void modelElementDeleted(ModelChangedEvent event, IModelDelta delta) {
        NonRootModelElement deletedElement = (NonRootModelElement) delta
                .getModelElement();

        ModelAdapter.IModelElementAdapter modelElementAdapter = ModelAdapter
                .getModelElementAdapter(deletedElement);
        if (modelElementAdapter == null) {
            return;
        }

        handleModelElementDeleted(event, delta, modelElementAdapter
                .createModelElementID(deletedElement));
    }
    
    public static void setIgnoreResourceChangesMarker(boolean value) {
    	ignoreResourceChangesMarker = value;
    }

    protected abstract void handleResourceMarkersChanged(IResourceDelta delta);

    protected abstract void handleComponentLoaded(ModelChangedEvent event);

    protected abstract void handleComponentUnloaded(ModelChangedEvent event);

    public abstract void systemAboutToBeDisabled(SystemModel_c system);
    
    protected abstract void handleModelElementDeleted(ModelChangedEvent event,
            IModelDelta delta, ModelElementID deletedModelElementID);

    protected abstract void handleModelElementAttributeChanged(
            ModelChangedEvent event, AttributeChangeModelDelta delta,
            ModelElementID changedModelElementID);

    protected abstract void handleComponentRenamed(PersistableModelComponent component,
            String oldName, String newName);

    protected abstract void handleModelReloaded(ModelChangedEvent event);
}
