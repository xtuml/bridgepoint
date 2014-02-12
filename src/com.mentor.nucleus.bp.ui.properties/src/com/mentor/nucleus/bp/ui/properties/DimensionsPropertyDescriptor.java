//====================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
package com.mentor.nucleus.bp.ui.properties;

import java.util.Vector;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class DimensionsPropertyDescriptor extends PropertyDescriptor
{        
    boolean m_readonly = true;
    
	/**
	 * Creates a property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public DimensionsPropertyDescriptor(Object id, String displayName, boolean readonly)
	{
		super(id, displayName);
		m_readonly = readonly;
	}

    private class DimensionsCellEditor extends TextCellEditor
    {
        public DimensionsCellEditor(Composite parent, int style)
        {
            super(parent, style);
        }
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.CellEditor#doGetValue()
		 */
		protected Object doGetValue()
		{
            return super.doGetValue();
		}
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.CellEditor#doSetValue(java.lang.Object)
		 */
		protected void doSetValue(Object value)
		{
            super.doSetValue(value);
		}

    }

	/**
	    * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	    */
	public CellEditor createPropertyEditor(Composite parent)
	{
		CellEditor editor = null;
		if ( !m_readonly ) {
			editor = new DimensionsCellEditor(parent, SWT.SINGLE);
			if (getValidator() != null) {
				editor.setValidator(getValidator());
			}
		}
		return editor;
	}

}
