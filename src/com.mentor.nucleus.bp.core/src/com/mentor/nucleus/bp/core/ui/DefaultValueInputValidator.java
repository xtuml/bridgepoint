//=====================================================================
//
//File:      $RCSfile $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2012/01/23 21:28:00 $
//
//(c) Copyright 2005-2012 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================
package com.mentor.nucleus.bp.core.ui;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.EnumerationDataType_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.common.InputValueValidator;

public class DefaultValueInputValidator
		implements
			ICellEditorValidator,
			IInputValidator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IInputValidator#isValid(java.lang.String)
	 */
	public String isValid(String newText) {
		// Return error string, or null if no error
		return isValueValid(newText);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ICellEditorValidator#isValid(java.lang.Object)
	 */
	public String isValid(Object value) {
		String newValue = (String) value;
		return isValueValid(newValue);
	}

	/*
	 * Validate the input against the underlying type.
	 */
	public static String isValueValid(String newValue) {
		if (newValue.isEmpty()) {
			return "";
		} else {
			IStructuredSelection currentSelection = Selection.getInstance()
					.getStructuredSelection();
			Object context = currentSelection.iterator().next();
			if (context instanceof Attribute_c) {
				DataType_c dt = DataType_c
						.getOneS_DTOnR114((Attribute_c) context);
				return InputValueValidator.isValid(dt, newValue);
			} else if (context instanceof UserDataType_c) {
				DataType_c dt = DataType_c
						.getOneS_DTOnR17((UserDataType_c) context);
				return InputValueValidator.isValid(dt, newValue);
			} else if (context instanceof EnumerationDataType_c) {
				DataType_c dt = DataType_c
						.getOneS_DTOnR17((EnumerationDataType_c) context);
				return InputValueValidator.isValid(dt, newValue);
			} else {
				return "Unexpected selected input context.";
			}
		}
	}
}
