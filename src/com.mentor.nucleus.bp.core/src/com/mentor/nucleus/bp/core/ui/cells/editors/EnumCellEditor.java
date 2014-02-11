package com.mentor.nucleus.bp.core.ui.cells.editors;
//========================================================================
//
//File:      $RCSfile: EnumCellEditor.java,v $
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

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class EnumCellEditor extends ComboBoxCellEditor {
    public EnumCellEditor(Composite parent, String[] enum_values)
    {
        super(parent, enum_values, SWT.READ_ONLY);
    }
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.CellEditor#doGetValue()
	 */
	protected Object doGetValue()
	{
        return (Integer)super.doGetValue();
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.CellEditor#doSetValue(java.lang.Object)
	 */
	protected void doSetValue(Object value)
	{
        for ( int i = 0; i < getItems().length; ++i )
        {
            if ( value.toString().equals(getItems()[i]) )
            {
                super.doSetValue(new Integer(i));
            }
        }
	}

}
