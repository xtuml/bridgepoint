package org.xtuml.bp.welcome;

import java.io.IOException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class WelcomePlugin extends AbstractUIPlugin {
	//The shared instance.
	private static WelcomePlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;
	
	/**
	 * The constructor.
	 */
	public WelcomePlugin() {
		super();
		plugin = this;
		try {
			resourceBundle = ResourceBundle.getBundle("org.xtuml.bp.welcome.WelcomePluginResources");//$NON-NLS-1$
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
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
	public static WelcomePlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the string from the plugin's resource bundle,
	 * or 'key' if not found.
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = WelcomePlugin.getDefault().getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key) : key;
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
	
	public static String getEntryPath(String entry) {
		URL url = getDefault().getBundle().getEntry(entry);
		URL resolvedURL = null;
		String result = "";
		try {
			resolvedURL =  FileLocator.resolve(url);
			result = resolvedURL.getPath();
		} catch (IOException e) {
			System.err.println("Unable to resolve URL for entry: " + entry + ".  " + e.getLocalizedMessage()); //$NON-NLS-1$
		}
		return result;
	}
	
    public static String getPluginPathAbsolute() {
        IPath relPath = new Path( getEntryPath("") ); //$NON-NLS-1$
        IPath absPath = relPath.makeAbsolute();
        return absPath.toString();
    }
}
