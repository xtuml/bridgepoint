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

import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;
import com.mentor.nucleus.bp.debug.ui.ModelElementLocation;

public class BPClassCreateDeleteBreakpoint extends BPBreakpoint {
	
	public static final int CREATE=1;
	public static final int DELETE=2;
	public static final int ALL=3;
	
	/**
	 * Default constructor is required for the breakpoint manager
	 * to re-create persisted breakpoints. After instantiating a breakpoint,
	 * the <code>setMarker(...)</code> method is called to restore
	 * this breakpoint's attributes.
	 */
	public BPClassCreateDeleteBreakpoint() {
	}
	
	/**
	 * @param resource file on which to set the breakpoint
	 * @throws CoreException if unable to create the breakpoint
	 */
	public BPClassCreateDeleteBreakpoint(ModelClass_c cl)
			throws CoreException {
		super(CLASS_BREAKPOINT_ID, cl, ALL);
	}
	
	public boolean isCreate() {
		return getFlag(CREATE, ALL, true);
	}

	public boolean isDelete() {
		return getFlag(DELETE, ALL, true);
	}

	public void setCreate(boolean cr) throws CoreException {
		setFlag(cr, CREATE, ALL);
	}

	public void setDelete(boolean del) throws CoreException {
		setFlag(del, DELETE, ALL);
	}

	public String getLabel(Object o) {
		return "Class Create/Delete Breakpoint";
	}

	public void createTargetBreakpoint() {
		BPDebugUIPlugin.logError("ClassCreateDeleteBreakpoint.createTargetBreakpoint() not implemented", null);
	}

	public void deleteTargetBreakpoint() {
		BPDebugUIPlugin.logError("ClassCreateDeleteBreakpoint.deleteTargetBreakpoint() not implemented", null);
	}

	public void modifyTargetBreakpoint(IMarkerDelta delta) {
		BPDebugUIPlugin.logError("ClassCreateDeleteBreakpoint.modifyTargetBreakpoint() not implemented", null);
	}

}
