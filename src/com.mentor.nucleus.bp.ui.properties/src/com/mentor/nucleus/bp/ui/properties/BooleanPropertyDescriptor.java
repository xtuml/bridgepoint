//====================================================================
//
//File:      $RCSfile: BooleanPropertyDescriptor.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 23:20:17 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
package com.mentor.nucleus.bp.ui.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class BooleanPropertyDescriptor extends PropertyDescriptor
{
    static public final String[] boolean_values = { "false", "true" };
        
	/**
	 * Creates a property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public BooleanPropertyDescriptor(Object id, String displayName, boolean readonly)
	{
		super(id, displayName);
	}

    private class BooleanCellEditor extends ComboBoxCellEditor
    {

        public BooleanCellEditor(Composite parent, String[] items)
        {
            super(parent, items);
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
            if ( ((Boolean)value).booleanValue() )
    			super.doSetValue(new Integer(1));
            else
                super.doSetValue(new Integer(0));
		}

    }

	/**
	    * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	    */
	public CellEditor createPropertyEditor(Composite parent)
	{
		CellEditor editor = new BooleanCellEditor(parent, boolean_values);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}

}
