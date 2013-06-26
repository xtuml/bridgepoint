package com.mentor.nucleus.bp.core.test;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Constants;

/**
 * The main plugin class to be used in the desktop.
 */
public class CoreTestPlugin extends AbstractUIPlugin {
	//The shared instance.
	private static CoreTestPlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;
	
	/**
	 * The constructor.
	 */
	public CoreTestPlugin() {
		plugin = this;
		try {
			resourceBundle= ResourceBundle.getBundle("com.mentor.nucleus.bp.core.test.CoreTestPluginResources");
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	/**
	 * Returns the shared instance.
	 */
	public static CoreTestPlugin getDefault() {
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
		ResourceBundle bundle= CoreTestPlugin.getDefault().getResourceBundle();
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
	public static void logError(String msg, Throwable e) {
		CoreTestPlugin ctp = getDefault();
		// plugin is null when running unit tests
		if (ctp != null) {
			Status status = new Status(IStatus.ERROR, (String) ctp.getBundle()
				.getHeaders().get(Constants.BUNDLE_NAME), IStatus.ERROR, msg, e);
			getDefault().getLog().log(status);
		} else {
			System.err.println(msg);
			if (e != null) {
				e.printStackTrace();
			}
		}
	}

}
