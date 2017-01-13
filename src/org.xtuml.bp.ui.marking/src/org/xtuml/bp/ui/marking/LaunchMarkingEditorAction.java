package org.xtuml.bp.ui.marking;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;


public class LaunchMarkingEditorAction implements IActionDelegate {

    private IStructuredSelection selection;

    @Override
    public void run(IAction action) {
        
        // UI guarantees only IProjects are selected
        for (Iterator<?> iterator = selection.iterator(); iterator.hasNext();) {
            IProject project = (IProject) iterator.next();
            markProject(project);
        }
    }

    private static void markProject(IProject project) {
        MarkingEditorDialog dlg = new MarkingEditorDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Manage Project Markings", project);
        dlg.open();        
    }
    
    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = (IStructuredSelection) selection;
    }

}
