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
import org.eclipse.swt.widgets.Composite;

import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;
import com.mentor.nucleus.bp.debug.ui.model.BPLineBreakpoint;

/**
 * Property page for editing breakpoints of type
 * <code>org.eclipse.jdt.debug.core.IJavaLineBreakpoint</code>.
 */
public class OALLineBreakpointPage extends BPBreakpointPage {
	
	
	/**
	 * @see com.mentor.nucleus.bp.debug.ui.propertypages.BPBreakpointPage#doStore()
	 */
	protected void doStore() throws CoreException {
		BPLineBreakpoint breakpoint= (BPLineBreakpoint) getBreakpoint();
		super.doStore();
	}

	/**
	 * @see com.mentor.nucleus.bp.debug.ui.propertypages.BPBreakpointPage#createTypeSpecificLabels(org.eclipse.swt.widgets.Composite)
	 */
	protected void createTypeSpecificLabels(Composite parent) {
		// Line number
		BPLineBreakpoint breakpoint = (BPLineBreakpoint) getBreakpoint();
		StringBuffer lineNumber = new StringBuffer(4);
		try {
			int lNumber = breakpoint.getLineNumber();
			if (lNumber > 0) {
				lineNumber.append(lNumber);
			}
		} catch (CoreException ce) {
			BPDebugUIPlugin.logError("Unable to get linenumber for breakpoint", ce);
		}
		if (lineNumber.length() > 0) {
			createLabel(parent, "&Line Number:"); 
			createLabel(parent, lineNumber.toString());
		}
	}
	
	/**
	 * Create the condition editor and associated editors.
	 * @see com.mentor.nucleus.bp.debug.ui.propertypages.BPBreakpointPage#createTypeSpecificEditors(org.eclipse.swt.widgets.Composite)
	 */
	protected void createTypeSpecificEditors(Composite parent) throws CoreException {
		BPLineBreakpoint breakpoint= (BPLineBreakpoint) getBreakpoint();
		if (breakpoint.supportsCondition()) {
			createConditionEditor(parent);
		}
	}
	
}
