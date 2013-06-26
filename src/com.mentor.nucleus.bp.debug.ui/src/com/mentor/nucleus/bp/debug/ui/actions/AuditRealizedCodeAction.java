package com.mentor.nucleus.bp.debug.ui.actions;

//=====================================================================
//
// File:      $RCSfile: AuditRealizedCodeAction.java,v $
// Version:   $Revision: 1.4 $
// Modified:  $Date: 2013/01/10 23:17:40 $
//
//(c) Copyright 2007-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================
import java.util.Iterator;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.debug.java.access.VerifierInvocationAuditor;

public class AuditRealizedCodeAction implements IViewActionDelegate {

	public AuditRealizedCodeAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// The part is mainly needed to locate the selection provider, and
		// we cache our selection in core so no action is needed here.
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		IStructuredSelection selection = (IStructuredSelection) Selection
				.getInstance().getSelection();
		Iterator<?> it = selection.iterator();
		String result = "";
		while (it.hasNext()) {
			Object selectedElem = it.next();
			if (selectedElem instanceof Component_c) {
				result += VerifierInvocationAuditor
						.performRealizedCodeAudit((Component_c) selectedElem);
			} else if (selectedElem instanceof Package_c) {
				result += VerifierInvocationAuditor
						.performRealizedCodeAudit((Package_c) selectedElem);
			}
		}
		UIUtil.openScrollableTextDialog(PlatformUI.getWorkbench().getDisplay()
				.getActiveShell(), false, "Audit Complete", result,
				"The following checks were made:", null, null, true);
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		try {
		} catch (Exception e) {
			CorePlugin
					.logError(
							"Exception encountered changing selection in Execute action.",
							e);
		}
	}

	/**
	 * @Override
	 */
	public void init(IViewPart view) {
		// nothing to do
	}
}
