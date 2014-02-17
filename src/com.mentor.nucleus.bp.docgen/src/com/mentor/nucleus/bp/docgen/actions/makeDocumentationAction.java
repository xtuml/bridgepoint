//====================================================================
//
// File:      $RCSfile: makeDocumentationAction.java,v $
// Version:   $Revision: 1.7 $
// Modified:  $Date: 2013/01/10 23:43:38 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
package com.mentor.nucleus.bp.docgen.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.docgen.generator.Generator;

public class makeDocumentationAction implements IObjectActionDelegate {

	private Object curSel = null;
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// Auto-generated method stub
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
