//=====================================================================
//
//File:      $RCSfile: MC3020TestPlugin.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 23:22:08 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================
package com.mentor.nucleus.bp.mc.mc3020.test;

import org.eclipse.ui.plugin.*;
import org.eclipse.core.resources.*;
import java.util.*;


public class MC3020TestPlugin extends AbstractUIPlugin {
	//The shared instance.
	private static MC3020TestPlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;
	
	public MC3020TestPlugin() {
		super();
		plugin = this;

		try {
			resourceBundle= ResourceBundle.getBundle("com.mentor.nucleus.bp.compare.test.CompareTestPluginResources"); //$NON-NLS-1$
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	public static MC3020TestPlugin getDefault() {
		return plugin;
	}

	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	public static String getResourceString(String key) {
		ResourceBundle bundle= MC3020TestPlugin.getDefault().getResourceBundle();
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}
}
