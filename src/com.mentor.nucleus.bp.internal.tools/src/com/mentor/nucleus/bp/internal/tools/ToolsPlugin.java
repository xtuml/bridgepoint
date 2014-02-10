//========================================================================
//
//File:      $RCSfile: ToolsPlugin.java,v $
//Version:   $Revision: 1.10.24.1 $
//Modified:  $Date: 2013/07/26 10:13:33 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License
//======================================================================== 

package com.mentor.nucleus.bp.internal.tools;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;

import com.mentor.nucleus.bp.mc.AbstractActivator;

/**
 * The main plugin class to be used in the desktop.
 */
public class ToolsPlugin extends AbstractActivator  {
	//The shared instance.
	private static ToolsPlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;
	
	private static String PLUGIN_ID = "com.mentor.nucleus.bp.internal.tools.ToolsPluginResources";
	
	/**
	 * The constructor.
	 */
	public ToolsPlugin() {
		super(PLUGIN_ID);
		plugin = this;
		try {
			resourceBundle = ResourceBundle.getBundle(PLUGIN_ID);
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}

	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		setBundle(context.getBundle());
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 */
	public static ToolsPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the string from the plugin's resource bundle,
	 * or 'key' if not found.
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = ToolsPlugin.getDefault().getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	@Override
	public void earlyStartup() {
		// No early startup behavior required.
		
	}
}
