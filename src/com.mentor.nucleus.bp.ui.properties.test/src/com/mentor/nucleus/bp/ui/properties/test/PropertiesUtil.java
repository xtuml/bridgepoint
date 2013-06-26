//=====================================================================
//
//File:      $RCSfile: PropertiesUtil.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 23:15:24 $
//
//(c) Copyright 2006-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
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
