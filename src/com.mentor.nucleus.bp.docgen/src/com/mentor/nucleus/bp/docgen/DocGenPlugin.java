//====================================================================
//
//File:      $RCSfile: DocGenPlugin.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:43:44 $
//
//(c) Copyright 2008-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
package com.mentor.nucleus.bp.docgen;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class DocGenPlugin extends AbstractUIPlugin {

	//The shared instance.
	private static DocGenPlugin plugin;
	
	/**
	 * The constructor.
	 */
	public DocGenPlugin() {
		plugin = this;
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
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static DocGenPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("com.mentor.nucleus.bp.docgen", path);
	}

    public static String getEntryPath(String entry) throws IOException {
        URL url = getDefault().getBundle().getEntry(entry);
        URL resolvedURL = null;
        try {
            resolvedURL =  FileLocator.resolve(url);
        } catch (IOException e) {
            throw e;
        }
        return resolvedURL.getPath();
    }

}
