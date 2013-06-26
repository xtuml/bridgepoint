package com.mentor.nucleus.bp.debug.ui.actions;

import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.SameShellProvider;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.dialogs.PropertyDialogAction;

import com.mentor.nucleus.bp.debug.ui.model.VerifierExceptionBreakpoint;

public class AddVerifierExceptionAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow fPart;

	public void run(IAction action) {
		final IBreakpoint bp = VerifierExceptionBreakpoint.getDummyInstance();
		PropertyDialogAction propertyAction= 
			new PropertyDialogAction(new SameShellProvider(fPart.getShell()), new ISelectionProvider() {
				public void addSelectionChangedListener(ISelectionChangedListener listener) {
				}
				public ISelection getSelection() {
					return new StructuredSelection ( new Object[] { bp } ) ;
				}
				public void removeSelectionChangedListener(ISelectionChangedListener listener) {
				}
				public void setSelection(ISelection selection) {
				}
			});
		propertyAction.run();	
	}
	
	public void dispose() {
		// nothing to do
	}

	public void init(IWorkbenchWindow window) {
		fPart = window;
		
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// don't care what the selection is
	}

}
