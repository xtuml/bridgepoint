package com.mentor.nucleus.bp.io.image.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.io.image.generator.Generator;

public class makeImagesAction implements IObjectActionDelegate {

	private Object curSel = null;
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// Auto-generated method stub

	}

	@Override
	public void run(IAction action) {
		if (curSel instanceof Component_c) {
			Generator.run((Component_c)curSel);
		}
		else if (curSel instanceof SystemModel_c) {
			Generator.genAll((SystemModel_c)curSel);
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sSel = (IStructuredSelection)selection;
			Object selected = sSel.getFirstElement();
			if (selected instanceof Component_c || selected instanceof SystemModel_c) {
				curSel = selected;
			}
		}

	}

}
