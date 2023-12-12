package org.xtuml.bp.ui.text.xtuml;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.ui.text.TextPlugin;

public class ShowXtumlElementAction implements IActionDelegate {
	IStructuredSelection currentSelection;

	public void run(IAction action) {
		Object current = currentSelection.iterator().next();
		if (current != null) {
			try {
				IEditorInput input = new FileEditorInput(
						((NonRootModelElement) current).getPersistableComponent().getFile());
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input,
						"org.xtuml.bp.ui.text.xtuml.XtumlTextEditor");
			} catch (CoreException e) {
				TextPlugin.logError("Could not activate xtUML Text Editor", e);
			}
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		currentSelection = (IStructuredSelection) selection;
	}
}
