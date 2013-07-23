package com.mentor.nucleus.bp.core.ui.cells.editors;
//========================================================================
//
//File:      $RCSfile: FloatCellEditor.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:38:52 $
//
//(c) Copyright 2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================

import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

public class FloatCellEditor extends TextCellEditor {

	public FloatCellEditor(Composite parent) {
		super(parent);
		setValidator(new FloatCellValidator());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.CellEditor#doGetValue()
	 */
	protected Object doGetValue() {
		return new Float((String) super.doGetValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.CellEditor#doSetValue(java.lang.Object)
	 */
	protected void doSetValue(Object value) {
		super.doSetValue(value.toString());
	}

	public class FloatCellValidator implements ICellEditorValidator {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ICellEditorValidator#isValid(java.lang.
		 * Object)
		 */
		public String isValid(Object value) {
			String val_str = (String) value;
			if (val_str.length() == 0)
				return "Value required";
			else {
				try {
					Float.parseFloat(val_str);
				} catch (NumberFormatException e) {
					return "Value is not a valid floating point number";
				}
			}
			return null;
		}

	}

}
