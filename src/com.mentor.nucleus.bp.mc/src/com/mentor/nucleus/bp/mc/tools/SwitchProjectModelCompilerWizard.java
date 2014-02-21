//========================================================================
//
// File: SwitchProjectModelCompilerWizard.java
//
// Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
// This document contains information proprietary and confidential to
// Mentor Graphics Corp. and is not for external distribution.
//======================================================================== 
//
//
package com.mentor.nucleus.bp.mc.tools;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ui.DelegatingWizard;
import com.mentor.nucleus.bp.core.ui.WizardDelegateChooserPage;


public class SwitchProjectModelCompilerWizard extends DelegatingWizard {

    private static final String MC_EXTENSION_POINT_NAME = "com.mentor.nucleus.bp.core.model-compilers";

    // Flag that unit tests can use.
    private boolean createdByUnitTest = false;
    private IProject currentProject;
    
    SwitchProjectModelCompilerWizard(IProject project) {
        currentProject = project; 
        setNeedsProgressMonitor(true);
        String[] mcis = null;
        if (getDelegatingWizard() != null) {
            mcis = getDelegatingWizard().getChoices();
            if (mcis.length >= 1) {
                addPage(new WizardDelegateChooserPage(
                        "switchxtUMLModelCompilerChooser",
                        "Set Model Compiler",
                        "Select the model compiler to use with this (" + project.getName() + ") xtUML project",
                        "Available xtUML model compilers:"));
            } 
        }
    }
    
    public String getExtensionPoint() {
        return MC_EXTENSION_POINT_NAME;
    }
    public void setIsCreatedByUnitTest() {
        createdByUnitTest = true;
    }
    private class myRunnable implements IRunnableWithProgress {
        public boolean result;
        public void run(IProgressMonitor monitor)
                throws InvocationTargetException, InterruptedException {
            result = performMCSwitch(monitor);
        }
    }
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#performFinish()
     */
    public boolean performFinish() {
        myRunnable op = new myRunnable();
        try {
            IWizardContainer container = getContainer();
            if (container != null && !createdByUnitTest) {
                container.run(false, false, op);
            } else {
                //Unit test invoke it without setting container/progress monitor.
                return performMCSwitch(new NullProgressMonitor());
            }
        } catch (InvocationTargetException e) {
            CorePlugin.logError("Internal error: " + e.getMessage(), e); //$NON-NLS-1$
            return false;
        } catch (InterruptedException e) {
            CorePlugin.logError("Internal error: " + e.getMessage(), e); //$NON-NLS-1$
            return false;
        }

        return op.result;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#performFinish()
     */
    public boolean performMCSwitch(IProgressMonitor monitor) {
        return super.performFinish(currentProject);
    }

}