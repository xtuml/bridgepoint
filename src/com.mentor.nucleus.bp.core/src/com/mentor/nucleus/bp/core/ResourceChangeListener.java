//=====================================================================
//
//File:      $RCSfile: ResourceChangeListener.java,v $
//Version:   $Revision: 1.14 $
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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.common.BaseModelDelta;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;

/**
 * Listens for changes to resources in the workspace that have a bearing
 * on model elements.
 */
public class ResourceChangeListener implements IResourceChangeListener
{
    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
     */
    public void resourceChanged(IResourceChangeEvent event) 
    {
        // if the event has no delta
        IResourceDelta delta = event.getDelta();
        if (delta == null) {
            // we aren't interested in it
            return;
        }
        CorePlugin.logResourceActivity(delta);
        try {
            // have the enclosed visitor visit the delta
            delta.accept(new IResourceDeltaVisitor() {
                public boolean visit(IResourceDelta delta) throws CoreException {
                    // if the delta represents an added resource (such as when after a
                    // model file is deleted, a version is retrieved from CVS)
                	// or a changed resource
                    if ((delta.getKind() == IResourceDelta.ADDED) || 
                    	(delta.getKind() == IResourceDelta.CHANGED) ) {
                        // fire a model-change event that we know will cause all
                        // listening views to completely refresh, to get what
                        // the added file represents to be shown
                        Ooaofooa defaultRoot = Ooaofooa.getDefaultInstance();
                        defaultRoot.fireModelElementUnloaded(defaultRoot);

                        // all views will have been completely refreshed, 
                        // so there's no need to look at any children
                        return false;
                    }

                    return true;
                }
            });

            // have the enclosed visitor visit the delta
            delta.accept(new IResourceDeltaVisitor() {
                public boolean visit(IResourceDelta delta) throws CoreException {
                    // if the delta represents a removed (or renamed) resource 
                    if (delta.getKind() == IResourceDelta.REMOVED) {
                        // if the resource is a project
                        final IResource resource = delta.getResource();
                        if (resource instanceof IProject) {
                            // if the project was renamed
                            IProject project = (IProject)resource;
                            if ((delta.getFlags() & IResourceDelta.MOVED_TO) != 0) {
                                // if there's a system-model in memory with
                                // with the same name as the project's old name 
                                final String oldName = project.getFullPath().
                                    removeFileExtension().lastSegment();
                                SystemModel_c system = getSystem(oldName);
                                if (system != null) {
                                    // rename the system-model to the project's
                                    // new name
                                    system.setName(delta.getMovedToPath().lastSegment());
                                }
                            }

                            // otherwise, the project was deleted
                            else {
                                // delete the associated system-model (if any)
                                deleteAssociatedSystemModel((IProject) resource);
                            }

                            // we don't care about a project's children for a 
                            // removed-event
                            return false;
                        }
                    }

                    // visit this delta's children, in search of a project
                    return true;
                }
            });
            
            // have the enclosed visitor visit the delta; note that we put this visitor that
            // checks for open and closed projects after the one above that checks for renamings,
            // since a rename also triggers an open-event for the renamed project, and we want
            // to make sure that by this point, the renaming has taken place above, so that
            // we can detm it's ok to skip the open-event here
            delta.accept(new IResourceDeltaVisitor() {
                public boolean visit(IResourceDelta delta) throws CoreException {
                    // if the delta represents an open or closed resource 
                    if ((delta.getFlags() & IResourceDelta.OPEN) != 0) {
                        // if the resource is a project
                        IResource resource = delta.getResource();
                        if (resource instanceof IProject) {
                            // if the project is now open
                            final IProject project = (IProject) resource;
                            if (project.isOpen()) {
                                // if the project has an xtUML nature
                                if (XtUMLNature.hasNature(project)) {
                                    // if there's no extant system of the project's name
                                    // (there would be one if the open-event is just a 
                                    // side effect of the project being renamed, which 
                                    // can happen)
                                    SystemModel_c system = getSystem(project.getName());
                                    if (system == null) {
                                        // create a system-model for the project
                                        system = new SystemModel_c(Ooaofooa.getDefaultInstance());
                                        system.setName(project.getName());
    
                                        // inform listeners of the newly opened system
                                        system.getModelRoot().fireModelElementCreated(new BaseModelDelta(Modeleventnotification_c.DELTA_NEW, system));
                                    }
                                }
                            }

                            // otherwise, the project is now closed
                            else {
                                // delete the associated system-model (if any)
                                deleteAssociatedSystemModel(project);
                            }

                            // we don't care about a project's children for an 
                            // open-event
                            return false;
                        }
                    }

                    // visit this delta's children, in search of a newly
                    // open or closed project
                    return true;
                }
            });
        } catch (CoreException x) {
            CorePlugin.logError("Could not respond to resource change event", x);
        }
    }
    
    /**
     * A utility method that returns the system-model of the given name.
     */
    private SystemModel_c getSystem(final String name)
    {
        return SystemModel_c.SystemModelInstance(
            Ooaofooa.getDefaultInstance(),
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((SystemModel_c)candidate).getName().equals(name);
                }
            });
    }
    
    /**
     * Deletes the system-model (if there is one) associated with the given project. 
     */
    private void deleteAssociatedSystemModel(final IProject project) 
    {
        // if we can find a system-model associated with
        // the project
        SystemModel_c system = getSystem(project.getName());
        if (system != null) {
            // dispose of it
            system.Dispose();
        }
    }
}