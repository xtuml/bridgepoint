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
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.XtUMLNature;
import com.mentor.nucleus.bp.core.common.BaseModelDelta;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ComponentResourceListener;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.ui.DelegatingWizard;
import com.mentor.nucleus.bp.core.ui.WizardDelegateChooserPage;
import com.mentor.nucleus.bp.core.ui.WizardNewSystemCreationPage;


public class SwitchProjectModelCompilerWizard extends DelegatingWizard {

    private static final String MC_EXTENSION_POINT_NAME = "com.mentor.nucleus.bp.core.model-compilers";

    // Flag that unit tests can use.
    private boolean createdByUnitTest = false;
    private IProject currentProject;
    
    SwitchProjectModelCompilerWizard(IProject project) {
        currentProject = project;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        super.init(workbench, selection);
        setNeedsProgressMonitor(true);
        String[] mcis = null;
        if (getDelegatingWizard() != null) {
            mcis = getDelegatingWizard().getChoices();
            if (mcis.length > 1) {
                addPage(new WizardDelegateChooserPage(
                        "switchxtUMLModelCompilerChooser",
                        "Multiple xtUML Model Compilers found",
                        "Select the model compiler to use with this (" + currentProject.getName() + ") xtUML project",
                        "Available xtUML model compilers:"));
            } else if (mcis.length == 1) {
                setDelegate(mcis[0]);
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
    
    class ProjectMCSwitchRunnable extends WorkspaceJob {
        public ProjectMCSwitchRunnable(String name) {
            super(name);
        }

        boolean returnVal = false;

        @Override
        public IStatus runInWorkspace(IProgressMonitor monitor)
                throws CoreException {
            // we must do this on the UI Thread
            PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
                
                @Override
                public void run() {
                    // TODO SKB - the meat goes here
                    /*IProject newProject = createProject();
                    if (newProject != null) {
                        XtUMLNature.addNature(newProject);
                        final String name = m_creationPage.getProjectName();
                        SwitchProjectModelCompilerWizard.createSystemModel(newProject, name);
                        returnVal = SwitchProjectModelCompilerWizard.super.performFinish(newProject);
                    } */
                    returnVal = true;
                }
            });
            if(!returnVal) {
                return Status.CANCEL_STATUS;
            }
            return Status.OK_STATUS;
        }
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#performFinish()
     */
    public boolean performMCSwitch(IProgressMonitor monitor) {
        ProjectMCSwitchRunnable runnable = new ProjectMCSwitchRunnable("xtUML Project MC Switch Job");
        runnable.setPriority(Job.INTERACTIVE);
        runnable.schedule();
        try {
            runnable.join();
        } catch (InterruptedException e) {
            CorePlugin.logError("Unable to switch xtUML project model compiler", e);
        }
        return runnable.returnVal;
    }

}