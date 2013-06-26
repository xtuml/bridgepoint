//=====================================================================
//
//File:      $RCSfile: BridgepointPropertySheetSorter.java,v $
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

import org.eclipse.ui.views.properties.PropertySheetSorter;

/**
 * Extends the normal sorter used for sorting property sheet categories
 * (and the properties they contain) to achieve the particular sorting order
 * that we desire.
 */
public class BridgepointPropertySheetSorter extends PropertySheetSorter
{
    /* (non-Javadoc)
     * @see org.eclipse.ui.views.properties.PropertySheetSorter#compareCategories(java.lang.String, java.lang.String)
     */
    public int compareCategories(String categoryA, String categoryB)
    {
        // have the basic property category always come first in the sheet
        String basicCategoryName = BridgepointPropertySheetPage.basicCategoryName;
        if (categoryA.equals(basicCategoryName)) return -1;
        if (categoryB.equals(basicCategoryName)) return 1;
        
        return super.compareCategories(categoryA, categoryB);
    }
}
