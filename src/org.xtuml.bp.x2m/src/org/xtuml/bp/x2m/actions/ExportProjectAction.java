package org.xtuml.bp.x2m.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.Deployment_c;
import org.xtuml.bp.x2m.generator.Generator;

public class ExportProjectAction implements IObjectActionDelegate {

	private Object curSel = null;
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// Auto-generated method stub
	}

	@Override
	public void run(IAction action) {
	    if (curSel instanceof Package_c) {
	        Generator.exportProject((Package_c)curSel);
	    }
	    else if (curSel instanceof Deployment_c) {
	        Generator.exportProject((Deployment_c)curSel);
	    }
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sSel = (IStructuredSelection)selection;
			Object selected = sSel.getFirstElement();
			curSel = selected;
		}

	}

}
