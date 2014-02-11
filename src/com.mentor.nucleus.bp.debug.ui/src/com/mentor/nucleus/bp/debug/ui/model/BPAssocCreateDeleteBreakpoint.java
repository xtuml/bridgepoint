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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;
import com.mentor.nucleus.bp.debug.ui.ModelElementLocation;

public class BPAssocCreateDeleteBreakpoint extends BPBreakpoint {
	
	public static final int CREATE=1;
	public static final int DELETE=2;
	public static final int ALL=3;
	
	/**
	 * Default constructor is required for the breakpoint manager
	 * to re-create persisted breakpoints. After instantiating a breakpoint,
	 * the <code>setMarker(...)</code> method is called to restore
	 * this breakpoint's attributes.
	 */
	public BPAssocCreateDeleteBreakpoint() {
	}
	
	/**
	 * @param resource file on which to set the breakpoint
	 * @throws CoreException if unable to create the breakpoint
	 */
	public BPAssocCreateDeleteBreakpoint(Association_c assoc)
			throws CoreException {
		super(ASSOC_BREAKPOINT_ID, assoc, ALL);
	}
	
	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.debug.ui.model.IBPBreakpoint#getTypeName()
	 */
	public String getTypeName() throws CoreException {
		return "Association:";
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
		return "Association Create/Delete Breakpoint";
	}

	public void createTargetBreakpoint() {
		BPDebugUIPlugin.logError("AssocCreateDeleteBreakpoint.createTargetBreakpoint() not implemented", null);
	}

	public void deleteTargetBreakpoint() {
		BPDebugUIPlugin.logError("AssocCreateDeleteBreakpoint.deleteTargetBreakpoint() not implemented", null);
	}

	public void modifyTargetBreakpoint(IMarkerDelta delta) {
		BPDebugUIPlugin.logError("AssocCreateDeleteBreakpoint.modifyTargetBreakpoint() not implemented", null);
	}

}
