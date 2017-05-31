//=====================================================================
//
//File:      $RCSfile: BridgepointPropertySheetSorter.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:20:17 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//=====================================================================

package org.xtuml.bp.ui.properties;

import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.PropertySheetSorter;
import org.xtuml.bp.core.sorter.MetadataSortingManager.ISorter;
import org.xtuml.bp.ui.properties.sorters.BridgePointPropertySheetSorters;

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

    /**
     * Override to allow custom ordering for elements
     */
	@Override
	public void sort(IPropertySheetEntry[] entries) {
		ISorter sorter = BridgePointPropertySheetSorters.getSorter();
		if(sorter != null) {
			sorter.sort(entries);
		} else {
			// default to super
			super.sort(entries);
		}
	}
        
}
