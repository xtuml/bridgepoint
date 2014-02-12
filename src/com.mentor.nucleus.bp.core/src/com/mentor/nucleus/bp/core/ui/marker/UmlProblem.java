package com.mentor.nucleus.bp.core.ui.marker;

//========================================================================
//
//File:      $RCSfile: UmlProblem.java,v $
//Version:   $Revision: 1.15 $
//Modified:  $Date: 2012/01/23 21:28:28 $
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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;

public class UmlProblem {
    public static final String xtUMLMarker = "com.mentor.nucleus.bp.ui.explorer.xtumlproblem";

    public static final String ATTR_XT_ID = "xtUMLID";

    public static final String ATTR_RTO_PATH = "RTOPath";

    public static void handleDanglingReference(NonRootModelElement rgo,
            NonRootModelElement rto) {
    	if(rgo.getModelRoot().getId().equals(Ooaofooa.CLIPBOARD_MODEL_ROOT_NAME)
    			|| rgo.getModelRoot().getId().equals(Ooaofooa.COMPARE_MODEL_ROOT_NAME))
    		return;
        if (rgo == null || rto == null)
            return;
        if (rgo.getPersistableComponent(false) == rto.getPersistableComponent(false))
            return; // not cross component

        MarkerEvent event = new RelationshipChangeEvent(rgo, rto);
        DelayedMarkerJob.addJob(event);
    }

    public static boolean isDangling(NonRootModelElement rto, boolean force) {
        if (rto.isProxy()) {
            PersistableModelComponent rtoComp = PersistenceManager
                    .findComponent(rto.getContent());
            if (rtoComp != null) {
                IFile file = rtoComp.getFile();
                if (file != null && !file.isAccessible()) {
                    return true;
                }
                if (rtoComp.isLoaded()) {
                    // component is loaded but still proxy, hmm this element is
                    // not in current revision of the file
                    return true;
                }
            } else {
                // component is null
                return true;
            }
        }
        return false;
    }

    public static String createMarkerID(NonRootModelElement rgo,
            NonRootModelElement rto) {
        return rgo.getCompUniqueID() + "_" + rto.getCompUniqueID();
    }

    public static IMarker findMarker(IResource file, Object markerID) {
        if (file != null) {
            try {
                IMarker[] markers = file.findMarkers(xtUMLMarker, false,
                        IResource.DEPTH_ZERO);
                for (int i = 0; i < markers.length; i++) {
                    IMarker marker = markers[i];
                    if (marker.getAttribute(ATTR_XT_ID) != null
                            && marker.getAttribute(ATTR_XT_ID).equals(markerID)) {
                        return marker;
                    }
                }
            } catch (CoreException e1) {
            }
        }
        return null;
    }

    public static boolean hasXtUMLMarker(IResource resource, int depth) {
        if (resource != null) {
            try {
                IMarker[] markers = resource.findMarkers(
                        UmlProblem.xtUMLMarker, true, depth);
                return markers.length > 0;
            } catch (CoreException e1) {
            }
        }
        return false;
    }

    public static boolean hasXtUMLMarkerAsRTO(IResource resource,
            String rtoMarkerID) {
        if(resource==null)
            return false;
        IProject proj = resource.getProject();
        if (proj != null) {
            try {
                IMarker[] markers = proj.findMarkers(xtUMLMarker, false,
                        IResource.DEPTH_INFINITE);
                for (int i = 0; i < markers.length; i++) {
                    IMarker marker = markers[i];
                    Object rtoPath = marker.getAttribute(ATTR_RTO_PATH);
                    String mID = (String) marker.getAttribute(ATTR_XT_ID);
                    if (rtoPath != null
                            && rtoPath
                                    .equals(resource.getFullPath().toString())) {
                        if (rtoMarkerID == null || mID.endsWith(rtoMarkerID))
                            return true;
                    }
                }
            } catch (CoreException e) {
            }

        }
        return false;
    }

    /**
     * Remove all xtUMLProblem of given file.
     * 
     * @param markerIdInitial
     * 
     * @param file
     * @param markerIdInitial
     *            TODO
     */
    public static void removeXtUMLProblems(IFile file) {
        if (file == null)
            return;

        MarkerEvent event = new RemoveAllEvent(file, null);
        DelayedMarkerJob.addJob(event);
    }

    public static void handleComponentAdded(PersistableModelComponent pmc) {
        if (pmc.isLoaded()) {
            // if component is already loaded it has been processed by
            // ProblemModelChangeListener
            return;
        }
        if (hasXtUMLMarkerAsRTO(pmc.getFile(),null)) {
            try {
                pmc.load(new NullProgressMonitor());
            } catch (CoreException e) {

            }
        }
    }

    public static void removeXtUMLProblem(NonRootModelElement rgo,
            NonRootModelElement rto) {
    	// do not add the job if either element is in the
    	// clipboard root
    	if (!rgo.getModelRoot().getId().equals(
				Ooaofooa.CLIPBOARD_MODEL_ROOT_NAME)
				&& (!rto.getModelRoot().getId()
						.equals(Ooaofooa.CLIPBOARD_MODEL_ROOT_NAME))) {
			MarkerEvent event = new RemoveMarkerEvent(rgo, rto);
			DelayedMarkerJob.addJob(event);
		}
    }

    public static void elementDeleted(NonRootModelElement rgo) {
        if(UmlProblem.hasXtUMLMarker(rgo.getFile(), IResource.DEPTH_ZERO)){
        MarkerEvent event = new RemoveAllEvent(rgo.getFile(), rgo
                .getCompUniqueID());
        DelayedMarkerJob.addJob(event);
        }
    }

    public static void proxyResolved(NonRootModelElement element) {
        MarkerEvent event = new ProxyResolvedEvent(element);
        DelayedMarkerJob.addJob(event);
    }

    private static boolean  collectProxies;
    public static boolean collectNextProxy = true;
    private static Set<NonRootModelElement>  collectedProxies = new HashSet<NonRootModelElement>();
    public static void convertedToProxy(NonRootModelElement element) {
        if(collectNextProxy) {
            if(collectProxies){
                collectedProxies.add(element);
            }else{
                MarkerEvent event = new ProxyCreatedEvent(element);
                DelayedMarkerJob.addJob(event);
            }
        }
    }

    public static void startCollectingProxies() {
        collectProxies=true;
    }
    public static void endCollectingProxies() {
        collectProxies=false;
        for (Iterator iter = collectedProxies.iterator(); iter.hasNext();) {
            NonRootModelElement element = (NonRootModelElement) iter.next();
            if(element.isProxy() && !element.isOrphaned()){
                convertedToProxy(element);
            }
        }
    }
}
