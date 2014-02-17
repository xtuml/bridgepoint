//=====================================================================
//
//File:      $RCSfile: SpinnerBoxCellEditor.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2012/01/23 21:28:06 $
//
//(c) Copyright 2008-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================
package com.mentor.nucleus.bp.core.ui.tree;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Tree;

public class SpinnerBoxCellEditor extends CellEditor {
	private Spinner fSpinner;
	
	public SpinnerBoxCellEditor(Composite parent, int minimum, int maximum) {
		super(parent);
		fSpinner.setMinimum(minimum);
		fSpinner.setMaximum(maximum);
		fSpinner.setSelection(minimum);
	}

	public void doSetValue(Object element) {
		fSpinner.setSelection((Integer)element);
	}
	
	public Composite createControl(Composite parent) {
		fSpinner = new Spinner(parent, SWT.BORDER);
		fSpinner.setIncrement(1);
		return fSpinner;
	}
	
	public Object doGetValue() {
		if(fSpinner.getSelection() == 0) {
			return "Disabled";
		}
		return fSpinner.getSelection();
	}
	
	public void doSetFocus() {
		if(fSpinner != null) {
			fSpinner.setFocus();
		}
	}

	public Spinner getSpinner() {
		return fSpinner;
	}
}