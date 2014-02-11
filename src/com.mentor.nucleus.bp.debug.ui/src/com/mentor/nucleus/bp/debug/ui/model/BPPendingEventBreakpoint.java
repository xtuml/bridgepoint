//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
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
