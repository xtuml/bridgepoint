//=====================================================================
//
//File:      $RCSfile: PropertiesUtil.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 23:15:24 $
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

package com.mentor.nucleus.bp.ui.properties.test;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Holds utility methods pertaining to properties.
 */
public class PropertiesUtil
{
    /**
     * Returns the property-descriptor of the given ID that's associated with
     * the given property-source.
     */
    public static IPropertyDescriptor getDescriptor(IPropertySource source,
        String id)
    {
        // for each property descriptor associated with the given property-source
        IPropertyDescriptor[] descriptors = source.getPropertyDescriptors();
        for (int i = 0; i < descriptors.length; i++) {
            // if this is the descriptor of the given id
            if (descriptors[i].getId().equals(id)) {
                return descriptors[i];
            }
        }
        
        return null;
    }
}
