//=====================================================================
//
//File:      $RCSfile: SpinnerBoxCellEditor.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2012/01/23 21:28:06 $
//
//(c) Copyright 2008-2012 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
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