//=====================================================================
//
//File:      $RCSfile: TestPlugin_Generics.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:00:38 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.als.oal.test;

import org.eclipse.ui.plugin.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.resources.*;
import org.osgi.framework.Constants;

import java.util.*;

/**
 * The main plugin class to be used in the desktop.
 */
public class TestPlugin_Generics extends AbstractUIPlugin {
	//The shared instance.
	private static TestPlugin_Generics plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;
	
	/**
	 * The constructor.
	 */
	public TestPlugin_Generics() {
		super();
		plugin = this;
		try {
			resourceBundle= ResourceBundle.getBundle("com.mentor.nucleus.bp.als.oal.test.TestPluginResources");
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	/**
	 * Returns the shared instance.
	 */
	public static TestPlugin_Generics getDefault() {
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
		ResourceBundle bundle= TestPlugin_Generics.getDefault().getResourceBundle();
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
    
    /**
     * Logs the given exception (with accompanying message) to this
     * plug-in's log.
     */
    public static void log(String msg, Exception e) 
    {
        TestPlugin_Generics plugin = getDefault();
        Status status = new Status(IStatus.ERROR,
            (String)plugin.getBundle().getHeaders().get(Constants.BUNDLE_NAME),
            IStatus.ERROR, msg, e);
        plugin.getLog().log(status);
    }
}
