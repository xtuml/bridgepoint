//====================================================================
//
//File:      $RCSfile: ConstantValuePropertyDescriptor.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:20:17 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
package com.mentor.nucleus.bp.ui.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.mentor.nucleus.bp.core.ui.ConstantValueInputValidator;

public class ConstantValuePropertyDescriptor extends PropertyDescriptor
{

	/**
	 * Creates a property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public ConstantValuePropertyDescriptor(Object id, String displayName)
	{
		super(id, displayName);
        setValidator(new ConstantValueInputValidator());
	}

    private class ConstantValueCellEditor extends TextCellEditor
    {

        public ConstantValueCellEditor(Composite parent)
        {
            super(parent);
        }
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.CellEditor#doGetValue()
		 */
		protected Object doGetValue()
		{
			return (String)super.doGetValue();
		}
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.CellEditor#doSetValue(java.lang.Object)
		 */
		protected void doSetValue(Object value)
		{
            super.doSetValue( value.toString() );
		}
    }
  
	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent)
	{
		CellEditor editor = new ConstantValueCellEditor(parent);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}
    
    // used by unit test
    public ICellEditorValidator getValidator()
    {
        return super.getValidator();
    }
}
