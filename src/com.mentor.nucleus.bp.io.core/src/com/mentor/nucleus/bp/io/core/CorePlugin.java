package com.mentor.nucleus.bp.io.core;

import org.eclipse.ui.plugin.*;
import org.eclipse.core.resources.*;

import java.util.*;

/**
 * The main plugin class to be used in the desktop.
 */
public class CorePlugin extends AbstractUIPlugin {
	//The shared instance.
	private static CorePlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;
	
	/**
	 * The constructor.
	 */
	public CorePlugin() {
		super();
		plugin = this;
		try {
			resourceBundle= ResourceBundle.getBundle("com.mentor.nucleus.bp.io.core.CorePluginResources");
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	/**
	 * Returns the shared instance.
	 */
	public static CorePlugin getDefault() {
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
		ResourceBundle bundle= CorePlugin.getDefault().getResourceBundle();
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
     * See method wrapped.
     */
    public static void logError(String msg, Exception e) 
    {
        com.mentor.nucleus.bp.core.CorePlugin.logError(msg, e);
    }
}
