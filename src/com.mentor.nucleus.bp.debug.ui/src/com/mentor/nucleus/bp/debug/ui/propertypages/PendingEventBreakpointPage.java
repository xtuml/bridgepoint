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
package com.mentor.nucleus.bp.debug.ui.propertypages;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Composite;

import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;
import com.mentor.nucleus.bp.debug.ui.model.BPBreakpoint;

/**
 * Property page for editing breakpoints of type
 * <code>com.mentor.nucleus.bp.debug.ui.model.BPPendingEventBreakpoint</code>.
 */
public class PendingEventBreakpointPage extends BPBreakpointPage {
	protected void createTypeSpecificLocation(Composite parent, String location) {
		String eventLocation= location.substring(0, location.lastIndexOf("::"));
		String classLocation= eventLocation.substring(0, location.lastIndexOf("::"));
		createLabel(parent, classLocation);
	}

	/**
	 * @see com.mentor.nucleus.bp.debug.ui.propertypages.BPBreakpointPage#createTypeSpecificLabels(org.eclipse.swt.widgets.Composite)
	 */
	protected void createTypeSpecificLabels(Composite parent) {
		// Line number
		BPBreakpoint breakpoint = (BPBreakpoint) getBreakpoint();
		String eventLoc = "Unknown";
		try {
			eventLoc = breakpoint.getLocation();
		} catch (CoreException ce) {
			BPDebugUIPlugin.logError("Unable to get location for breakpoint", ce);
		}
		String [] names = eventLoc.split("::");
		createLabel(parent, "Event:"); 
		createLabel(parent, names[names.length-2]);
		createLabel(parent, "Event Instance:"); 
		createLabel(parent, names[names.length-1]);
	}
	
}
