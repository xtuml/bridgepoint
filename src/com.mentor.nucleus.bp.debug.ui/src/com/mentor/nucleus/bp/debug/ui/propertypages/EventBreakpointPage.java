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
import com.mentor.nucleus.bp.debug.ui.model.BPEventBreakpoint;

/**
 * Property page for editing breakpoints of type
 * <code>com.mentor.nucleus.bp.debug.ui.model.BPEventBreakpoint</code>.
 */
public class EventBreakpointPage extends BPBreakpointPage {

	protected Button fQueuedButton;
	protected Button fDequeuedButton;
	protected Button fEventIgnoredButton;
	protected Button fCantHappenButton;
	
	/**
	 * @see com.mentor.nucleus.bp.debug.ui.propertypages.BPBreakpointPage#doStore()
	 */
	protected void doStore() throws CoreException {
		BPEventBreakpoint breakpoint= (BPEventBreakpoint) getBreakpoint();
		super.doStore();
		boolean queued= fQueuedButton.getSelection();
		breakpoint.setQueued(queued);
		boolean dequeued= fDequeuedButton.getSelection();
		breakpoint.setDequeued(dequeued);
		boolean ignored = fEventIgnoredButton.getSelection();
		breakpoint.setEventIgnored(ignored);
		boolean cantHappen = fCantHappenButton.getSelection();
		breakpoint.setCantHappen(cantHappen);
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
		String attrLoc = "Unknown";
		try {
			attrLoc = breakpoint.getLocation();
		} catch (CoreException ce) {
			BPDebugUIPlugin.logError("Unable to get location for breakpoint", ce);
		}
		createLabel(parent, "Event:"); 
		String [] names = attrLoc.split("::");
		createLabel(parent, names[names.length-1]);
	}
	
	/**
	 * Create the condition editor and associated editors.
	 * @see com.mentor.nucleus.bp.debug.ui.propertypages.BPBreakpointPage#createTypeSpecificEditors(org.eclipse.swt.widgets.Composite)
	 */
	protected void createTypeSpecificEditors(Composite parent) throws CoreException {
		createLabel(parent, "Suspend on:"); 
		BPEventBreakpoint breakpoint= (BPEventBreakpoint) getBreakpoint();
		fQueuedButton = createCheckButton(parent, "Queued");
		fQueuedButton.setSelection(breakpoint.isQueued());
		fDequeuedButton = createCheckButton(parent, "Dequeued");
		fDequeuedButton.setSelection(breakpoint.isDequeued());
		fEventIgnoredButton = createCheckButton(parent, "Event Ignored");
		fEventIgnoredButton.setSelection(breakpoint.isEventIgnored());
		fCantHappenButton = createCheckButton(parent, "Can't Happen");
		fCantHappenButton.setSelection(breakpoint.isCantHappen());
		
		if (breakpoint.supportsCondition()) {
			createConditionEditor(parent);
		}
	}
	
}
