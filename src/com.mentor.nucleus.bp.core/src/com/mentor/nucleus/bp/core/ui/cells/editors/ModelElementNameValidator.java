//====================================================================
//
//File:      $RCSfile: ModelElementNameValidator.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:38:52 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
package com.mentor.nucleus.bp.core.ui.cells.editors;

import org.eclipse.jface.viewers.ICellEditorValidator;

import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.util.UIUtil;

/**
 * Validates certain qualities of model element names that apply across
 * most of the element types.
 */
public class ModelElementNameValidator implements ICellEditorValidator 
{
    /**
     * The model element whose name value this validator is to validate.
     */
    private ModelElement element;
    
    /**
     * Constructor.
     */
    public ModelElementNameValidator(ModelElement element)
    {
        this.element = element;
    }
    
    /*
     * @see org.eclipse.jface.viewers.ICellEditorValidator#isValid(java.lang.Object)
     */
    public String isValid(Object value) 
    {
        String name = (String)value;
        return UIUtil.validateNameUsingRenameAction(name, element);
    }

}