package com.mentor.nucleus.bp.utilities.actions;

import java.util.Iterator;

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
		IntegrityIssue_c[] issues = null;
		IStructuredSelection ss = (IStructuredSelection) selection;
		for(Iterator<?> iterator = ss.iterator(); iterator.hasNext();) {
			NonRootModelElement element = (NonRootModelElement) iterator.next();
			issues = IntegrityChecker.runIntegrityCheck(element);
		}
		if(issues != null && issues.length != 0) {
			UIUtil.openScrollableTextDialog(PlatformUI.getWorkbench()
					.getDisplay().getActiveShell(), false,
					"Model Integrity Issues",
					IntegrityChecker.createReportForIssues(issues),
					"The following integrity issues were found.", null, null, false);
		} else {
			UIUtil.openMessageDialog(PlatformUI.getWorkbench().getDisplay()
					.getActiveShell(), "Model Integrity Issues", null,
					"No integrity issues found.",
					BPMessageTypes.INFORMATION, new String[] { "OK" }, 0);
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// cache the selection
		this.selection = selection;
	}

}
