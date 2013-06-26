//=====================================================================
//
//File:      $RCSfile: ComparePlugin.java,v $
//Version:   $Revision: 1.16 $
//Modified:  $Date: 2013/01/10 22:49:51 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================
package com.mentor.nucleus.bp.compare;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * The main plugin class to be used in the desktop.
 */
public class ComparePlugin extends AbstractUIPlugin {
	//The shared instance.
	private static ComparePlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;

	private ModelCacheManager modelCacheManager = new ModelCacheManager();

	private static final String RESOURCE_BUNDLE = "com.mentor.nucleus.bp.compare.ComparePluginMessages"; //$NON-NLS-1$

	public ComparePlugin() {
		super();
		plugin = this;
		try {
			resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE);
		} catch (MissingResourceException x) {
			resourceBundle = null;
			x.printStackTrace();
		}
	}

	public static ComparePlugin getDefault() {
		return plugin;
	}

	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	public static String getResourceString(String key) {
		ResourceBundle bundle = ComparePlugin.getDefault().getResourceBundle();
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	public ModelCacheManager getModelCacheManager() {
		return modelCacheManager;
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public static void writeToLog(
		String errorMsg,
		Throwable e,
		Object originatingClass) {
        ComparePlugin plugin = getDefault();
		getDefault().getLog().log(
			new Status(
				IStatus.ERROR,
                plugin.getBundle().getSymbolicName(),
				IStatus.ERROR,
				errorMsg + " in class " + originatingClass.getClass().getName(), //$NON-NLS-1$
				e));

	}
}
