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
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;
import com.mentor.nucleus.bp.debug.ui.model.BPClassCreateDeleteBreakpoint;

/**
 * Property page for editing breakpoints of type
 * <code>org.eclipse.jdt.debug.core.IJavaLineBreakpoint</code>.
 */
public class ClassCreateDeleteBreakpointPage extends BPBreakpointPage {

	protected Button fCreateButton;
	protected Button fDeleteButton;
	
	/**
	 * @see com.mentor.nucleus.bp.debug.ui.propertypages.BPBreakpointPage#doStore()
	 */
	protected void doStore() throws CoreException {
		BPClassCreateDeleteBreakpoint breakpoint= (BPClassCreateDeleteBreakpoint) getBreakpoint();
		super.doStore();
		boolean access= fCreateButton.getSelection();
		breakpoint.setCreate(access);
		boolean modification = fDeleteButton.getSelection();
		breakpoint.setDelete(modification);
	}

	/**
	 * @see com.mentor.nucleus.bp.debug.ui.propertypages.BPBreakpointPage#createTypeSpecificLabels(org.eclipse.swt.widgets.Composite)
	 */
	protected void createTypeSpecificLabels(Composite parent) {
		// nothing
	}
	
	/**
	 * Create the condition editor and associated editors.
	 * @see com.mentor.nucleus.bp.debug.ui.propertypages.BPBreakpointPage#createTypeSpecificEditors(org.eclipse.swt.widgets.Composite)
	 */
	protected void createTypeSpecificEditors(Composite parent) throws CoreException {
		BPClassCreateDeleteBreakpoint breakpoint= (BPClassCreateDeleteBreakpoint) getBreakpoint();
		fCreateButton = createCheckButton(parent, "Creation");
		fCreateButton.setSelection(breakpoint.isCreate());
		fDeleteButton = createCheckButton(parent, "Deletion");
		fDeleteButton.setSelection(breakpoint.isDelete());
		
		if (breakpoint.supportsCondition()) {
			createConditionEditor(parent);
		}
	}
	
}
