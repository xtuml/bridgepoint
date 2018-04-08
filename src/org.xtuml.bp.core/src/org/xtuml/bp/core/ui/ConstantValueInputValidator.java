//======================================================================
//
//File:      $RCSfile: ConstantValueInputValidator.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2012/01/23 21:28:00 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//======================================================================

package org.xtuml.bp.core.ui;

import java.util.UUID;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.IStructuredSelection;

import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.LeafSymbolicConstant_c;
import org.xtuml.bp.core.LiteralSymbolicConstant_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SymbolicConstant_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.common.InputValueValidator;

public class ConstantValueInputValidator implements ICellEditorValidator, IInputValidator {

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
    public static String isValueValid(String newValue) {
        IStructuredSelection currentSelection = Selection.getInstance().getStructuredSelection();
        Object context = currentSelection.iterator().next();
        LiteralSymbolicConstant_c v_lsc = (LiteralSymbolicConstant_c) context;

        SymbolicConstant_c v_symConst = SymbolicConstant_c
                .getOneCNST_SYCOnR1502(LeafSymbolicConstant_c
                        .getOneCNST_LFSCOnR1503(v_lsc));

        DataType_c v_dt = DataType_c.getOneS_DTOnR1500(v_symConst);
        UserDataType_c udt = UserDataType_c.getOneS_UDTOnR17(v_dt);
        if(udt != null) {
        	UUID coreTypeId = udt.Getcoretype();
        	v_dt = (DataType_c) Ooaofooa.getDefaultInstance().getInstanceList(DataType_c.class).getGlobal(coreTypeId);
        }
        
        return InputValueValidator.isValid(v_dt, newValue);
    }

}
