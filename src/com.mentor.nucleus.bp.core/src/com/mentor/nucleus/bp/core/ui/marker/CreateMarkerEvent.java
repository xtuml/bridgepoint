package com.mentor.nucleus.bp.core.ui.marker;

//========================================================================
//
//File:      $RCSfile: CreateMarkerEvent.java,v $
//Version:   $Revision: 1.12 $
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
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.osgi.framework.Bundle;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.util.CoreUtil;
import com.mentor.nucleus.bp.core.util.UIUtil;

public class CreateMarkerEvent extends MarkerEvent implements
        IWorkspaceRunnable {
    private NonRootModelElement rto;

    private NonRootModelElement rgo;

    public CreateMarkerEvent(NonRootModelElement rto, NonRootModelElement rgo) {
        this.rto = rto;
        this.rgo = rgo;
    }

    public void run(IProgressMonitor monitor) throws CoreException {
        IResource rgoFile = rgo.getFile();
        Object markerID = UmlProblem.createMarkerID(rgo, rto);
        IMarker existingMarker = UmlProblem.findMarker(rgoFile, markerID);

        if (existingMarker == null && rgoFile.exists()) {
            Map<String,Object> attributes = new HashMap<String,Object>(11);
            attributes.put(UmlProblem.ATTR_XT_ID, markerID);
            attributes.put(UmlProblem.ATTR_RTO_PATH, rto.getFile()
                    .getFullPath().toString());

            IPath filePath = rto.getFile().getFullPath();
            
            String message = getElementName(rgo) + " (" + CoreUtil.getName(rto)
                    + ") is referring to missing or incorrect " + getElementName(rto) + " file " + "("
                    + filePath.toString() + ")";

            attributes.put(IMarker.MESSAGE, message);
            attributes.put(IMarker.SEVERITY,
                    new Integer(IMarker.SEVERITY_ERROR));

            IMarker marker = rgoFile.createMarker(UmlProblem.xtUMLMarker);
            marker.setAttributes(attributes);
            UIUtil.refresh(rgoFile.getProject());
        }
    }

    private static String getElementName(NonRootModelElement me) {
        String temp = me.getClass().getName();
        if (temp.length() > 3) {
            return temp.substring(temp.lastIndexOf('.') + 1, temp.length() - 2);
        }
        return temp;
    }

    public void process() throws CoreException {
        IResource rgoFile = rgo.getFile();
        IResource rtoFile = rto.getFile();
        if (rgoFile!=null && rtoFile!=null && rgoFile.exists()) {
            Object markerID = UmlProblem.createMarkerID(rgo, rto);
            IMarker existingMarker = UmlProblem.findMarker(rgoFile, markerID);

            if (existingMarker == null) {
                internalProcess(rgo.getFile().getParent());
            }
        }
    }
}