package com.mentor.nucleus.bp.core.ui.cells.editors;
//========================================================================
//
//File:      $RCSfile: BooleanCellEditor.java,v $
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
import org.eclipse.swt.widgets.Composite;

public class BooleanCellEditor extends ComboBoxCellEditor {
	
	static public final String[] boolean_values = { "false", "true" };
	
	public BooleanCellEditor(Composite parent) {
		super(parent, boolean_values);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.CellEditor#doGetValue()
	 */
	protected Object doGetValue()
	{
        Integer val = (Integer)super.doGetValue();
        if ( val.intValue() == 0 )
            return new Boolean(false);
        else
            return new Boolean(true);
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.CellEditor#doSetValue(java.lang.Object)
	 */
	protected void doSetValue(Object value)
	{
        if (Boolean.valueOf((String)value))
			super.doSetValue(new Integer(1));
        else
            super.doSetValue(new Integer(0));
	}
	
}
