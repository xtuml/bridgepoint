//========================================================================
//
//File:      com.mentor.nucleus.bp.utilities/src/com/mentor/nucleus/bp/utilities/actions/CheckModelIntegrity.java
//
//Copyright 2005-2013 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
package com.mentor.nucleus.bp.utilities.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.IntegrityIssue_c;
import com.mentor.nucleus.bp.core.common.IntegrityChecker;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.core.util.UIUtil.BPMessageTypes;

public class CheckModelIntegrity implements IActionDelegate {

	private ISelection selection;

	@Override
	public void run(IAction action) {
		List<IntegrityIssue_c> issues = new ArrayList<IntegrityIssue_c>();
		IStructuredSelection ss = (IStructuredSelection) selection;
		for (Iterator<?> iterator = ss.iterator(); iterator.hasNext();) {
			NonRootModelElement element = (NonRootModelElement) iterator.next();
			issues.addAll(Arrays.asList(IntegrityChecker
					.runIntegrityCheck(element)));
		}
		if (!issues.isEmpty()) {
			UIUtil.openScrollableTextDialog(PlatformUI.getWorkbench()
					.getDisplay().getActiveShell(), false,
					"Model Integrity Issues",
					IntegrityChecker.createReportForIssues(issues
							.toArray(new IntegrityIssue_c[issues.size()])),
					"The following integrity issues were found.", null, null,
					false);
		} else {
			UIUtil.openMessageDialog(PlatformUI.getWorkbench().getDisplay()
					.getActiveShell(), "Model Integrity Issues", null,
					"No integrity issues found.", BPMessageTypes.INFORMATION,
					new String[] { "OK" }, 0);
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// cache the selection
		this.selection = selection;
	}

}
