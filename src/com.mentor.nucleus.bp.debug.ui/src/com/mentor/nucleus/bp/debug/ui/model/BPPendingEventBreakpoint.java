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
import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.PendingEvent_c;
import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;

public class BPPendingEventBreakpoint extends BPBreakpoint {
	
	/**
	 * Default constructor is required for the breakpoint manager
	 * to re-create persisted breakpoints. After instantiating a breakpoint,
	 * the <code>setMarker(...)</code> method is called to restore
	 * this breakpoint's attributes.
	 */
	public BPPendingEventBreakpoint() {
	}
	
	/**
	 * @param resource file on which to set the breakpoint
	 * @throws CoreException if unable to create the breakpoint
	 */
	public BPPendingEventBreakpoint(PendingEvent_c event)
			throws CoreException {
		super(PENDING_EVENT_BREAKPOINT_ID, event, 0);
	}
	
	public String getLabel(Object o) {
		return "Pending Event Breakpoint";
	}

	public boolean supportsCondition() {
		return false;
	}

	public boolean supportsHitCount() {
		return false;
	}
	
	public void createTargetBreakpoint() {
		BPDebugUIPlugin.logError("BPPendingEventBreakpoint.createTargetBreakpoint() not implemented", null);
	}

	public void deleteTargetBreakpoint() {
		BPDebugUIPlugin.logError("BPPendingEventBreakpoint.deleteTargetBreakpoint() not implemented", null);
	}

	public void modifyTargetBreakpoint(IMarkerDelta delta) {
		BPDebugUIPlugin.logError("BPPendingEventBreakpoint.modifyTargetBreakpoint() not implemented", null);
	}

}
