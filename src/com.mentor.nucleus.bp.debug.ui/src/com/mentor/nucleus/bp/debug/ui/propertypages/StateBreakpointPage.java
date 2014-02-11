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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;
import com.mentor.nucleus.bp.debug.ui.model.BPBreakpoint;
import com.mentor.nucleus.bp.debug.ui.model.BPStateBreakpoint;

/**
 * Property page for editing breakpoints of type
 * <code>org.eclipse.jdt.debug.core.IJavaLineBreakpoint</code>.
 */
public class StateBreakpointPage extends BPBreakpointPage {

	protected Button fIntoButton;
	protected Button fOutofButton;
	
	/**
	 * @see com.mentor.nucleus.bp.debug.ui.propertypages.BPBreakpointPage#doStore()
	 */
	protected void doStore() throws CoreException {
		BPStateBreakpoint breakpoint= (BPStateBreakpoint) getBreakpoint();
		super.doStore();
		boolean into = fIntoButton.getSelection();
		breakpoint.setInto(into);
		boolean outof = fOutofButton.getSelection();
		breakpoint.setOutof(outof);
		breakpoint.updateTargetBreakpoint();
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
		String stateLoc = "Unknown";
		try {
			stateLoc = breakpoint.getLocation();
		} catch (CoreException ce) {
			BPDebugUIPlugin.logError("Unable to get location for breakpoint", ce);
		}
		createLabel(parent, "State:"); 
		String [] names = stateLoc.split("::");
		createLabel(parent, names[names.length-1]);
	}
	
	/**
	 * Create the condition editor and associated editors.
	 * @see com.mentor.nucleus.bp.debug.ui.propertypages.BPBreakpointPage#createTypeSpecificEditors(org.eclipse.swt.widgets.Composite)
	 */
	protected void createTypeSpecificEditors(Composite parent) throws CoreException {
		BPStateBreakpoint breakpoint= (BPStateBreakpoint) getBreakpoint();
		createLabel(parent, "Suspend on:"); 
		fIntoButton = createCheckButton(parent, "Transition Into");
		fIntoButton.setSelection(breakpoint.isInto());
		fOutofButton = createCheckButton(parent, "Transition Out of");
		fOutofButton.setSelection(breakpoint.isOutof());
		
		if (breakpoint.supportsCondition()) {
			createConditionEditor(parent);
		}
	}
	
}
