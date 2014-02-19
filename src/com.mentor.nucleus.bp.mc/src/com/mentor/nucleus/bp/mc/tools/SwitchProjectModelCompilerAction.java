//========================================================================
//
// File: SwitchProjectModelCompilerAction.java
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

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.XtUMLNature;


public class SwitchProjectModelCompilerAction implements IActionDelegate {

    private IStructuredSelection selection;
    private static final String EXTERNALTOOLBUILDER_FOLDER = ".externalToolBuilders"; //$NON-NLS-1$

    private static final String OLD_MC3020_NATURE_ID = "com.mentor.nucleus.bp.mc.mc3020.MC3020Nature"; //$NON-NLS-1$
    public static final String  OLD_EXPORT_BUILDER_ID = "com.mentor.nucleus.bp.mc.mc3020.export_builder"; //$NON-NLS-1$
    public static final String  OLD_MC3020_LAUNCH_ID = "MC-3020 Model Compiler.launch"; //$NON-NLS-1$
    
    @Override
    public void run(IAction action) {
        
        // UI guarantees only IProjects are selected
        for (Iterator<?> iterator = selection.iterator(); iterator.hasNext();) {
            IProject project = (IProject) iterator.next();
            try {
                if (!project.hasNature(XtUMLNature.ID)) {
                    // TODO - If we don't have the xtuml nature, maybe we want to add it here???
                    continue;
                }
            } catch (CoreException e) {
                CorePlugin
                        .logError(
                                "Unable to determine if the project has the xtUML nature.",
                                e);
                continue;
            }
            switchMC(project);
        }
    }

    private static void switchMC(IProject project) {
        WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
                new SwitchProjectModelCompilerWizard(project));
        wizardDialog.open();        
    }
    
    // TODO - check if we have xtUML MC nature (old or new)
    // TODO - just call the MC configure() and let it do the work???
    
    /*private static void configureRefreshOptionForBuildConfiguration(IFile launchFile)
            throws CoreException {
        launchFile.refreshLocal(IFile.DEPTH_ONE, new NullProgressMonitor());
        ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
        ILaunchConfiguration launchConfiguration = manager
                .getLaunchConfiguration(launchFile);
        IWorkingSetManager workingSetManager = PlatformUI.getWorkbench()
                .getWorkingSetManager();
        IWorkingSet workingSet = workingSetManager.createWorkingSet(
                "working set", new IAdaptable[] { launchFile.getProject() });
        String scope = RefreshTab.getRefreshAttribute(workingSet);
        ILaunchConfigurationWorkingCopy workingCopy = launchConfiguration
                .getWorkingCopy();
        workingCopy.setAttribute(RefreshTab.ATTR_REFRESH_SCOPE, scope);
        workingCopy.setAttribute(RefreshTab.ATTR_REFRESH_RECURSIVE, true);
        workingCopy.doSave();
    }*/

    
    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = (IStructuredSelection) selection;
    }

}
