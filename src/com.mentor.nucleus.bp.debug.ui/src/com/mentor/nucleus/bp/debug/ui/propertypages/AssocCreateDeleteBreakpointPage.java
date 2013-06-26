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
package com.mentor.nucleus.bp.debug.ui.propertypages;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.mentor.nucleus.bp.debug.ui.model.BPAssocCreateDeleteBreakpoint;

/**
 * Property page for editing breakpoints of type
 * <code>org.eclipse.jdt.debug.core.IJavaLineBreakpoint</code>.
 */
public class AssocCreateDeleteBreakpointPage extends BPBreakpointPage {

	protected Button fCreateButton;
	protected Button fDeleteButton;
	
	/**
	 * @see com.mentor.nucleus.bp.debug.ui.propertypages.BPBreakpointPage#doStore()
	 */
	protected void doStore() throws CoreException {
		BPAssocCreateDeleteBreakpoint breakpoint= (BPAssocCreateDeleteBreakpoint) getBreakpoint();
		super.doStore();
		boolean access= fCreateButton.getSelection();
		breakpoint.setCreate(access);
		boolean modification = fDeleteButton.getSelection();
		breakpoint.setDelete(modification);
	}

	/**
	 * @see com.mentor.nucleus.bp.debug.ui.propertypages.BPBreakpointPage#createTypeSpecificLabels(org.eclipse.swt.widgets.Composite)
	 */
	protected void createTypeSpecificLabels(Composite parent) {
		// nothing
	}
	
	/**
	 * Create the condition editor and associated editors.
	 * @see com.mentor.nucleus.bp.debug.ui.propertypages.BPBreakpointPage#createTypeSpecificEditors(org.eclipse.swt.widgets.Composite)
	 */
	protected void createTypeSpecificEditors(Composite parent) throws CoreException {
		BPAssocCreateDeleteBreakpoint breakpoint= (BPAssocCreateDeleteBreakpoint) getBreakpoint();
		fCreateButton = createCheckButton(parent, "Creation");
		fCreateButton.setSelection(breakpoint.isCreate());
		fDeleteButton = createCheckButton(parent, "Deletion");
		fDeleteButton.setSelection(breakpoint.isDelete());
		
		if (breakpoint.supportsCondition()) {
			createConditionEditor(parent);
		}
	}
	
}
