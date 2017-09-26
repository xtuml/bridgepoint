package org.xtuml.bp.core.editors.actions;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.editors.input.IModelEditorInput;
import org.xtuml.bp.core.editors.input.ModelEditorInput;

public class OpenModelEditorAction implements IActionDelegate {

	@Override
	public void run(IAction action) {
		// open a new Model Editor
		IWorkbenchPage page = (IWorkbenchPage) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IEditorReference[] editorReferences = page.getEditorReferences();
		for(int i = 0; i < editorReferences.length; i++) {
			if(editorReferences[i].getId().equals("org.xtuml.bp.core.editors.ModelEditor")) {
				// just activate this editor
				page.activate(editorReferences[i].getPart(true));
				return;
			}
		}
		IModelEditorInput input = new ModelEditorInput(ResourcesPlugin.getWorkspace().getRoot());
		try {
			page.openEditor(input, "org.xtuml.bp.core.editors.ModelEditor");
		} catch (PartInitException e) {
			CorePlugin.logError("Unable to open model editor.", e);
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// do nothing
	}

}
