package org.xtuml.bp.core.editors.association.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.editors.association.dialogs.AssociationTableDialog;
import org.xtuml.bp.core.ui.Selection;

public class ConfigureAssociation implements IActionDelegate {

	private IStructuredSelection selectedElements;

	@Override
	public void run(IAction action) {
		AssociationTableDialog dialog = new AssociationTableDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor().getSite().getShell(), "Edit Associations", selectedElements);
		dialog.open();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// capture the association selected
		if (selection instanceof IStructuredSelection) {
			// Eclipse configuration will only enable this
			// for class selections
			IStructuredSelection ss = (IStructuredSelection) selection;
			NonRootModelElement[] nrmeSelection = Selection.getSelectedNonRootModelElements(ss);
			selectedElements = new StructuredSelection(nrmeSelection);
		}
	}

}
