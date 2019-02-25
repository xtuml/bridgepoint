package org.xtuml.bp.mc.tools;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.SystemModel_c;


public class SwitchProjectModelCompilerAction implements IActionDelegate {

    private IStructuredSelection selection;

    @Override
    public void run(IAction action) {
        
        // UI guarantees only IProjects are selected
        for (Iterator<?> iterator = selection.iterator(); iterator.hasNext();) {
			IProject project = (IProject) ((SystemModel_c)iterator.next()).getAdapter(IProject.class);
            switchMC(project);
        }
    }

    private static void switchMC(IProject project) {
        WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
                new SwitchProjectModelCompilerWizard(project));
        wizardDialog.open();        
    }
    
    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = (IStructuredSelection) selection;
    }

}
