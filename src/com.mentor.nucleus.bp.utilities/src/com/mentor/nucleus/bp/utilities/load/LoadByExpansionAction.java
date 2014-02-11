//====================================================================
//
// File:      $RCSfile: LoadByExpansionAction.java,v $
// Version:   $Revision: 1.6 $
// Modified:  $Date: 2013/01/10 23:21:54 $
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
package com.mentor.nucleus.bp.utilities.load;

import org.eclipse.jface.action.IAction;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.util.UIUtil;

public class LoadByExpansionAction implements IActionDelegate {

	private Object fElement;

	@Override
	public void run(IAction action) {
		long time = LoadingUtilities.timeLoadByTreeExpansion(fElement);
		UIUtil.openInformation(PlatformUI.getWorkbench().getDisplay()
				.getActiveShell(), "Load Time Report",
				"Load by expansion duration: " + time);
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// cache selection of model element
		fElement = ((IStructuredSelection) selection).getFirstElement();
	}

}
