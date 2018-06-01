//======================================================================
//
//File: RangeValueInputValidator.java
//
//======================================================================

package org.xtuml.bp.core.ui;

import java.util.UUID;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.common.InputValueValidator;

public class RangeValueInputValidator implements ICellEditorValidator, IInputValidator {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.IInputValidator#isValid(java.lang.String)
     */
    public String isValid(String newText) {
        // Return error string, or null if no error
        return isValueValid(newText);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ICellEditorValidator#isValid(java.lang.Object)
     */
    public String isValid(Object value) {
        String newValue = (String) value;
        return isValueValid(newValue);
    }

    /*
     * Validate the input against the underlying type.
     */
    public static String isValueValid( String newValue ) {
        IStructuredSelection currentSelection = Selection.getInstance().getStructuredSelection();
        Object context = currentSelection.iterator().next();
        UserDataType_c s_udt = (UserDataType_c) context;
        UUID dt_id = s_udt.Getcoretype();
        DataType_c v_dt = (DataType_c)s_udt.getModelRoot().getInstanceList( DataType_c.class ).getGlobal( dt_id );
        return InputValueValidator.isValid( v_dt, newValue, true );
    }

}
