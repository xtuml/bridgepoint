//====================================================================
//
// File: RangeValuePropertyDescriptor.java
//
//====================================================================
package org.xtuml.bp.ui.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import org.xtuml.bp.core.ui.RangeValueInputValidator;

public class RangeValuePropertyDescriptor extends PropertyDescriptor
{

    /**
     * Creates a property descriptor with the given id and display name.
     * 
     * @param id the id of the property
     * @param displayName the name to display for the property
     */
    public RangeValuePropertyDescriptor(Object id, String displayName)
    {
        super(id, displayName);
        setValidator(new RangeValueInputValidator());
    }

    private class RangeValueCellEditor extends TextCellEditor
    {

        public RangeValueCellEditor(Composite parent)
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
        CellEditor editor = new RangeValueCellEditor(parent);
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
