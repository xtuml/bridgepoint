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
package com.mentor.nucleus.bp.debug.ui.model;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;
import com.mentor.nucleus.bp.debug.ui.ModelElementLocation;

public class BPAttributeBreakpoint extends BPBreakpoint {
	
	public static final int ACCESS=1;
	public static final int MODIFICATION=2;
	public static final int UNINIT_ACCESS=4;
	public static final int ALL=7;
	
	/**
	 * Default constructor is required for the breakpoint manager
	 * to re-create persisted breakpoints. After instantiating a breakpoint,
	 * the <code>setMarker(...)</code> method is called to restore
	 * this breakpoint's attributes.
	 */
	public BPAttributeBreakpoint() {
	}
	
	/**
	 * @param resource file on which to set the breakpoint
	 * @throws CoreException if unable to create the breakpoint
	 */
	public BPAttributeBreakpoint(Attribute_c attr)
			throws CoreException {
			super(ATTRIBUTE_BREAKPOINT_ID, attr, ALL); 
	}
	
	public boolean isAccess() {
		return getFlag(ACCESS, ALL, true);
	}

	public boolean isModification() {
		return getFlag(MODIFICATION, ALL, true);
	}

	public boolean isUninitAccess() {
		return getFlag(UNINIT_ACCESS, ALL, true);
	}

	public void setAccess(boolean access) throws CoreException {
		setFlag(access, ACCESS, ALL);
	}

	public void setModification(boolean modification) throws CoreException {
		setFlag(modification, MODIFICATION, ALL);
	}

	public void setUninitAccess(boolean uninitAccess) throws CoreException {
		setFlag(uninitAccess, UNINIT_ACCESS, ALL);
	}

	public String getLabel(Object o) {
		return "Attribute Breakpoint";
	}

	public void createTargetBreakpoint() {
		BPDebugUIPlugin.logError("AttributeBreakpoint.createTargetBreakpoint() not implemented", null);
	}

	public void deleteTargetBreakpoint() {
		BPDebugUIPlugin.logError("AttributeBreakpoint.deleteTargetBreakpoint() not implemented", null);
	}

	public void modifyTargetBreakpoint(IMarkerDelta delta) {
		BPDebugUIPlugin.logError("AttributeBreakpoint.modifyTargetBreakpoint() not implemented", null);
	}

}
