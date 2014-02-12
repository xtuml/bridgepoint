//=====================================================================
//
//File:      $RCSfile: PlaceHolderUtil.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2012/01/23 21:27:40 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.core.util;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.mentor.nucleus.bp.core.CorePlugin;

/**
 * Utility methods which affect place-holder files for 
 * entities such as the activities and descriptions of a domain.
 * These methods use reflection to access members of the UI-Text plug-in, 
 * thus avoiding the creation of a plug-in dependency loop.
 */
public class PlaceHolderUtil
{
    /**
     * Initialize the place holder manager so that it can keep
     * the place holders in sync. 
     */
    private static boolean alreadyLoaded = false;
	public static void initPlaceholderManager()
    {
		if(!alreadyLoaded){
			try {
		        final String pluginName = "com.mentor.nucleus.bp.ui.text"; //$NON-NLS-1$
		        Bundle bundle = Platform.getBundle(pluginName);
		        Class clazz = null;
		        String packageName = pluginName + ".placeholder"; //$NON-NLS-1$
		        try {
		            clazz = bundle.loadClass(
		                packageName + ".PlaceHolderManager"); //$NON-NLS-1$ 
		        } catch (ClassNotFoundException e) {
		            CorePlugin.logError("Could not retrieve PlaceHolderManager class", e);//$NON-NLS-1$ 
		            return;
		        }
				
				
				clazz.getMethod("getDefaultInstance", null).invoke(null, null); //$NON-NLS-1$
				alreadyLoaded = true;
			} catch (Exception e) {
				CorePlugin.logError("Error while initializing PlaceHolderManager", e); //$NON-NLS-1$ 
			} 
		}
    }
}
