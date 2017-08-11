package org.xtuml.bp.integrity.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import org.xtuml.bp.integrity.generator.Generator;

public class checkReferentialIntegrityAction implements IObjectActionDelegate {

	private Object curSel = null;
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// Auto-generated method stub
	}

	@Override
	public void run(IAction action) {
	  Generator.genAll();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sSel = (IStructuredSelection)selection;
			Object selected = sSel.getFirstElement();
		}

	}

}
