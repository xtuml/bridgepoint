// ========================================================================
//
// File:      $RCSfile: DanglingReferenceDecorator.java,v $
// Version:   $Revision: 1.11 $
// Modified:  $Date: 2013/01/10 23:15:56 $
//
// (c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
//
// ========================================================================
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
// ======================================================================== 
package com.mentor.nucleus.bp.ui.explorer.decorators;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.ListenerList;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.IPersistenceHierarchyMetaData;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.ui.marker.UmlProblem;

public class DanglingReferenceDecorator implements ILightweightLabelDecorator {
    private static final ImageDescriptor ERROR = CorePlugin
            .getImageDescriptor("error_co.gif");

    private static ListenerList listeners = new ListenerList();

    private static DanglingReferenceDecorator defaultInst = null;

    public DanglingReferenceDecorator() {
        if (defaultInst == null)
            defaultInst = this;
    }

    public static DanglingReferenceDecorator getDefaultInstance() {
        return defaultInst;
    }

    public void decorate(Object element, IDecoration decoration) {
        if (!CorePlugin.getProblemMarkerDecorationRequestsDisabled() && needsDecoration(element)) {
            decoration.addOverlay(ERROR, IDecoration.BOTTOM_LEFT);
            postLabelEvent(element);
        }
    }

    private boolean needsDecoration(Object element) {
        IPersistenceHierarchyMetaData hmd = PersistenceManager
                .getHierarchyMetaData();
        IResource resource = null;
        if (element instanceof NonRootModelElement) {
            NonRootModelElement me = (NonRootModelElement) element;
            if (hmd.isComponentRoot(me)) {
                resource = me.getFile();
                if (resource != null)// unit test creates some MEs
                    resource = resource.getParent();
                return hasXtUMLMarker(resource, IResource.DEPTH_INFINITE);
            } else {
                return hasAnyChildXtUMLMarker(me);
            }
        }
        return false;
    }

    private boolean hasAnyChildXtUMLMarker(NonRootModelElement me) {
        IPersistenceHierarchyMetaData hmd = PersistenceManager
                .getHierarchyMetaData();
        IFile resource = me.getFile();
        String markerID = me.getCompUniqueID();
        boolean result = false;
        if (markerID != null) {
            result = hasXtUMLMarker(resource, IResource.DEPTH_ZERO, markerID);
            if (result) {
                return true;
            }
        }
        List children = hmd.getChildren(me, true);
        for (Iterator iter = children.iterator(); iter.hasNext();) {
            NonRootModelElement child = (NonRootModelElement) iter.next();
            result = hasAnyChildXtUMLMarker(child);
            if (result)
                return true;
        }
        return false;
    }

    private boolean hasXtUMLMarker(IResource resource, int depth,
            String idStartsFrom) {
        if (resource != null) {
            try {
                IMarker[] markers = resource.findMarkers(
                        UmlProblem.xtUMLMarker, true, depth);
                for (int i = 0; i < markers.length; i++) {
                    String markerID = (String) markers[i]
                            .getAttribute(UmlProblem.ATTR_XT_ID);
                    if (markerID != null && markerID.startsWith(idStartsFrom)) {
                        return true;
                    }
                }
            } catch (CoreException e1) {
            }
        }
        return false;
    }

    private boolean hasXtUMLMarker(IResource resource, int depth) {
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

    public void addListener(ILabelProviderListener listener) {
        listeners.add(listener);
    }

    public void dispose() {
        // TODO Auto-generated method stub

    }

    public boolean isLabelProperty(Object element, String property) {
        // TODO Auto-generated method stub
        return false;
    }

    public void removeListener(ILabelProviderListener listener) {
        listeners.remove(listener);
    }

    private void postLabelEvent(Object element) {
        final LabelProviderChangedEvent event = new LabelProviderChangedEvent(
                this, element);
        Object[] list = listeners.getListeners();
        for (int i = 0; i < list.length; i++) {
            ILabelProviderListener listener = (ILabelProviderListener) list[i];
            // we need this only for the unit tests, otherwise
            // DecorationManager add it self to the listners list and
            // and manager keeps scheduling with no decoration appearance
            // on the model explorer
            if (listener.getClass().getName().startsWith(
                    "com.mentor.nucleus.bp.io.mdl.test.dangle"))
                listener.labelProviderChanged(event);

        }
    }
}
