package com.mentor.nucleus.bp.debug.ui.launch;


//====================================================================
//
// File:      $RCSfile: LaunchShortcutTester.java,v $
// Version:   $Revision: 1.9 $
// Modified:  $Date: 2013/01/10 23:17:46 $
//
// (c) Copyright 2005-2013 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
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
