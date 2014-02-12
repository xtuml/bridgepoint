//=====================================================================
//
//File:      $RCSfile: BridgepointPropertySheetPage.java,v $
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
