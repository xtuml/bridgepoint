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

import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;
import com.mentor.nucleus.bp.debug.ui.model.BPAttributeBreakpoint;
import com.mentor.nucleus.bp.debug.ui.model.BPBreakpoint;

/**
 * Property page for editing breakpoints of type
 * <code>org.eclipse.jdt.debug.core.IJavaLineBreakpoint</code>.
 */
public class AttributeBreakpointPage extends BPBreakpointPage {

	protected Button fAccessButton;
	protected Button fModificationButton;
	protected Button fUninitAccessButton;
	
	/**
	 * @see com.mentor.nucleus.bp.debug.ui.propertypages.BPBreakpointPage#doStore()
	 */
	protected void doStore() throws CoreException {
		BPAttributeBreakpoint breakpoint= (BPAttributeBreakpoint) getBreakpoint();
		super.doStore();
		boolean access= fAccessButton.getSelection();
		breakpoint.setAccess(access);
		boolean modification = fModificationButton.getSelection();
		breakpoint.setModification(modification);
		boolean uninitAccess = fUninitAccessButton.getSelection();
		breakpoint.setUninitAccess(uninitAccess);
	}

	
	protected void createTypeSpecificLocation(Composite parent, String location) {
		String classLocation= location.substring(0, location.lastIndexOf("::"));
		createLabel(parent, classLocation);
	}


	/**
	 * @see com.mentor.nucleus.bp.debug.ui.propertypages.BPBreakpointPage#createTypeSpecificLabels(org.eclipse.swt.widgets.Composite)
	 */
	protected void createTypeSpecificLabels(Composite parent) {
		// Line number
		BPBreakpoint breakpoint = (BPBreakpoint) getBreakpoint();
		String attrLoc = "Unknown";
		try {
			attrLoc = breakpoint.getLocation();
		} catch (CoreException ce) {
			BPDebugUIPlugin.logError("Unable to get location for breakpoint", ce);
		}
		createLabel(parent, "Attribute:"); 
		String [] names = attrLoc.split("::");
		createLabel(parent, names[names.length-1]);
	}
	
	/**
	 * Create the condition editor and associated editors.
	 * @see com.mentor.nucleus.bp.debug.ui.propertypages.BPBreakpointPage#createTypeSpecificEditors(org.eclipse.swt.widgets.Composite)
	 */
	protected void createTypeSpecificEditors(Composite parent) throws CoreException {
		createLabel(parent, "Suspend on:"); 
		fAccessButton = createCheckButton(parent, "Attribute Access");
		fAccessButton.setSelection(((BPAttributeBreakpoint)getBreakpoint()).isAccess());
		fModificationButton = createCheckButton(parent, "Attribute Modification");
		fModificationButton.setSelection(((BPAttributeBreakpoint)getBreakpoint()).isModification());
		fUninitAccessButton = createCheckButton(parent, "Uninitialized Access");
		fUninitAccessButton.setSelection(((BPAttributeBreakpoint)getBreakpoint()).isUninitAccess());
		
		BPAttributeBreakpoint breakpoint= (BPAttributeBreakpoint) getBreakpoint();
		if (breakpoint.supportsCondition()) {
			createConditionEditor(parent);
		}
	}

	// for use by unit tests
	public void setAccessButton(boolean enabled) {
		fAccessButton.setSelection(enabled);
	}
	public void setModificationButton(boolean enabled) {
		fModificationButton.setSelection(enabled);
	}
	public void setUninitAccessButton(boolean enabled) {
		fUninitAccessButton.setSelection(enabled);
	}

}
