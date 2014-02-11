package com.mentor.nucleus.bp.core.ui.cells.editors;
//========================================================================
//
//File:      $RCSfile: IntegerCellEditor.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:38:52 $
//
//(c) Copyright 2013-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

public class IntegerCellEditor extends TextCellEditor {

	public IntegerCellEditor(Composite parent, int min, int max) {
		super(parent);
		setValidator(new IntegerCellValidator(min, max));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.CellEditor#doGetValue()
	 */
	protected Object doGetValue() {
		return new Integer((String) super.doGetValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.CellEditor#doSetValue(java.lang.Object)
	 */
	protected void doSetValue(Object value) {
		super.doSetValue(value.toString());
	}

	public class IntegerCellValidator implements ICellEditorValidator {
		private int m_min_value;
		private int m_max_value;

		public IntegerCellValidator(int min_val, int max_val) {
			m_min_value = min_val;
			m_max_value = max_val;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ICellEditorValidator#isValid(java.lang.
		 * Object)
		 */
		public String isValid(Object value) {
			int val;
			String val_str = (String) value;
			if (val_str.length() == 0)
				return "Value between " + m_min_value + " and " + m_max_value
						+ " required";
			else {
				try {
					val = Integer.parseInt(val_str);
				} catch (NumberFormatException e) {
					return "Value is not a number";
				}
			}

			if (val < m_min_value)
				return "Value must be >= " + m_min_value;
			else if (val > m_max_value)
				return "Value must be <= " + m_max_value;

			return null;
		}

	}

}
