//====================================================================
//
//File:      $RCSfile: IntegerPropertyDescriptor.java,v $
//Version:   $Revision: 1.12 $
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

public class IntegerPropertyDescriptor extends PropertyDescriptor
{

	/**
	 * Creates a property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public IntegerPropertyDescriptor(Object id, String displayName,
        int min_val, int max_val, boolean readonly)
	{
		super(id, displayName);
        setValidator(new IntegerCellValidator(min_val, max_val));
	}

    private class IntegerCellEditor extends TextCellEditor
    {

        public IntegerCellEditor(Composite parent)
        {
            super(parent);
        }
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.CellEditor#doGetValue()
		 */
		protected Object doGetValue()
		{
			return new Integer((String)super.doGetValue());
		}
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.CellEditor#doSetValue(java.lang.Object)
		 */
		protected void doSetValue(Object value)
		{
            super.doSetValue( value.toString() );
		}

    }

    // used by unit test
    public class IntegerCellValidator implements ICellEditorValidator
    {
        private int m_min_value;
        private int m_max_value;

        public IntegerCellValidator(int min_val, int max_val)
        {
            m_min_value = min_val;
            m_max_value = max_val;
        }

        
    	/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ICellEditorValidator#isValid(java.lang.Object)
		 */
		public String isValid(Object value)
		{
            int val;
            String val_str = (String)value;
            if( val_str.length() == 0)
              return "Value between " + m_min_value + " and " + m_max_value + " required";
            else
            {
                try {
                    val = Integer.parseInt(val_str);
                }
                catch(NumberFormatException e)
                {
                    return "Value is not a number";
                }
            }
              
            if ( val < m_min_value )            
              return "Value must be >= " + m_min_value;
            else  if ( val > m_max_value )            
              return "Value must be <= " + m_max_value;

			return null;
		}

}
    
	/**
	    * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	    */
	public CellEditor createPropertyEditor(Composite parent)
	{
		CellEditor editor = new IntegerCellEditor(parent);
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
