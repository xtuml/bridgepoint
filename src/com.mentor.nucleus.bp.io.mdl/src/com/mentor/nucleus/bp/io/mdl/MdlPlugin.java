//====================================================================
//
//File:      $RCSfile: MdlPlugin.java,v $
//Version:   $Revision: 1.14 $
//Modified:  $Date: 2013/01/10 23:35:07 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
package com.mentor.nucleus.bp.io.mdl;

import org.eclipse.ui.plugin.*;
import org.eclipse.core.resources.*;
import java.util.*;

/**s
 * The main plugin class to be used in the desktop.
 */
public class MdlPlugin extends AbstractUIPlugin {
	//The shared instance.
	private static MdlPlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;

	/**
																																																																													* The constructor.
																																																																													*/
	public MdlPlugin() {
		super();
		plugin = this;
		try {
			resourceBundle =
				ResourceBundle.getBundle(
					"com.mentor.nucleus.bp.io.mdl.MdlPluginResources");
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	/**
					 * Returns the shared instance.
					 */
	public static MdlPlugin getDefault() {
		return plugin;
	}

	/**
						 * Returns the workspace instance.
						 */
	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	/**
						 * Returns the string from the plugin's resource bundle,
						 * or 'key' if not found.
						 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = MdlPlugin.getDefault().getResourceBundle();
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
										 * Returns the plugin's resource bundle,
										 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}
}
