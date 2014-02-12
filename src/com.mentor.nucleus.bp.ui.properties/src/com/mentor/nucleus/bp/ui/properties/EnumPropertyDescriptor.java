//====================================================================
//
//File:      $RCSfile: EnumPropertyDescriptor.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 23:20:17 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
package com.mentor.nucleus.bp.ui.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class EnumPropertyDescriptor extends PropertyDescriptor
{
    String[] m_enum_values = null;
    boolean m_readonly = true;
        
	/**
	 * Creates a property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public EnumPropertyDescriptor(Object id, String displayName,
        String[] enum_values, boolean readonly)
	{
		super(id, displayName);
        m_enum_values = enum_values;
        m_readonly = readonly;
	}

    private class EnumCellEditor extends ComboBoxCellEditor
    {

        public EnumCellEditor(Composite parent)
        {
            super(parent, m_enum_values, SWT.READ_ONLY);
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
            for ( int i = 0; i < m_enum_values.length; ++i )
            {
                if ( value.toString().equals(m_enum_values[i]) )
                {
                    super.doSetValue(new Integer(i));
                }
            }
		}

    }

	/**
	    * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	    */
	public CellEditor createPropertyEditor(Composite parent)
	{
        if ( m_readonly )
        {
            return null;
        }
        else
        {
            CellEditor editor = new EnumCellEditor(parent);
            if (getValidator() != null)
                editor.setValidator(getValidator());
            return editor;
        }
	}

    // used by unit test
    public String enumValue(int i)
    {
        return m_enum_values[i];
    }
    
    // used by unit test
    public int numValues()
    {
        return m_enum_values.length;
    }
}
