package com.mentor.nucleus.bp.debug.ui.launch;


//====================================================================
//
// File:      $RCSfile: LaunchShortcutTester.java,v $
// Version:   $Revision: 1.9 $
// Modified:  $Date: 2013/01/10 23:17:46 $
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
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
import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IFileEditorInput;

import com.mentor.nucleus.bp.core.XtUMLNature;


public class LaunchShortcutTester extends PropertyTester {
    private static final String PROJECT_NATURE = "XtUMLNature"; //$NON-NLS-1$

    public LaunchShortcutTester() {
        super();
    }

    public boolean test(Object receiver, String property, Object[] args,
        Object expectedValue) {
        if (property.equals(PROJECT_NATURE)) {
            IProject project = null;

            //if selection in project
            IResource resource = (IResource) ((IAdaptable) receiver).getAdapter(IResource.class);

            if (resource != null) {
                project = resource.getProject();
            }
            //if selection is an editor
            else {
                IFileEditorInput editorInput = (IFileEditorInput) ((IAdaptable) receiver).getAdapter(IFileEditorInput.class);

                if (editorInput != null) {
                    project = editorInput.getFile().getProject();
                }
            }

            if ((project != null) && project.isAccessible()) {
                return (XtUMLNature.hasNature(project));
            }
        }

        return false;
    }
}
