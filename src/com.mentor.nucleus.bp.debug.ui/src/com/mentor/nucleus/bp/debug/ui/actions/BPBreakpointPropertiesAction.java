//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.debug.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.dialogs.PropertyDialogAction;

import com.mentor.nucleus.bp.debug.ui.model.BPBreakpoint;


/**
 * Presents the standard properties dialog to configure
 * the attibutes of a Breakpoint from the popup menu of a breakpoint instance 
 */
public class BPBreakpointPropertiesAction implements IObjectActionDelegate {

	private IWorkbenchPart fPart;
	private BPBreakpoint fBreakpoint;


	public void run(IAction action) {
		PropertyDialogAction propertyAction= 
			new PropertyDialogAction(fPart.getSite(), new ISelectionProvider() {
				public void addSelectionChangedListener(ISelectionChangedListener listener) {
				}
				public ISelection getSelection() {
					return new StructuredSelection(getBreakpoint());
				}
				public void removeSelectionChangedListener(ISelectionChangedListener listener) {
				}
				public void setSelection(ISelection selection) {
				}
			});
		propertyAction.run();	
	}
	
	protected BPBreakpoint getBreakpoint() {
		return fBreakpoint;
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		fPart = targetPart;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss= (IStructuredSelection)selection;
			if (ss.isEmpty() || ss.size() > 1) {
				return;
			}
			Object element= ss.getFirstElement();
			if (element instanceof BPBreakpoint) {
				setBreakpoint((BPBreakpoint)element);
			}
		}
	}

	private void setBreakpoint(BPBreakpoint breakpoint) {
		fBreakpoint = breakpoint;
	}
}
