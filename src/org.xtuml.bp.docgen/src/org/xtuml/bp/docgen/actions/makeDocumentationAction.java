package org.xtuml.bp.docgen.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.docgen.generator.Generator;

public class makeDocumentationAction implements IObjectActionDelegate {

	private Object curSel = null;
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	@Override
	public void run(IAction action) {
	    if (curSel instanceof SystemModel_c) {
			Generator.genAll((SystemModel_c)curSel);
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sSel = (IStructuredSelection)selection;
			Object selected = sSel.getFirstElement();
			if (selected instanceof SystemModel_c) {
				curSel = selected;
			}
		}

	}

}
