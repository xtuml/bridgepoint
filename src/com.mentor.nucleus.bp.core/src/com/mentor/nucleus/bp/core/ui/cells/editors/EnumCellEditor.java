package com.mentor.nucleus.bp.core.ui.cells.editors;
//========================================================================
//
//File:      $RCSfile: EnumCellEditor.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:38:52 $
//
//(c) Copyright 2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
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
