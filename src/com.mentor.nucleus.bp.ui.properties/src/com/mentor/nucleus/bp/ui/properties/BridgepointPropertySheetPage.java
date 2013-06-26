//=====================================================================
//
//File:      $RCSfile: BridgepointPropertySheetPage.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:20:17 $
//
//(c) Copyright 2006-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.ui.properties;

import org.eclipse.ui.views.properties.PropertySheetPage;

/**
 * A property-sheet-page modified to exhibit BridgePoint-specfic behavior.
 */
public class BridgepointPropertySheetPage extends PropertySheetPage
{
    /**
     * The name of the properties category to which a model element's 
     * attributes will be assigned.
     */
    public static final String basicCategoryName = "Basic";
    
    /**
     * Constructor.
     */
    public BridgepointPropertySheetPage() 
    {
        super();
        
        // use our own sorter
        setSorter(new BridgepointPropertySheetSorter());
    }
}
