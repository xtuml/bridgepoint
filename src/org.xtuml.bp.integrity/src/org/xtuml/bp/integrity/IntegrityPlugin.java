/********************************************************************************
 * Copyright (c) 2017 One Fact Inc
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache Software License
 * 2.0 which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-1.0 OR Apache-2.0
 ********************************************************************************/

package org.xtuml.bp.integrity;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class IntegrityPlugin extends AbstractUIPlugin {

	//The shared instance.
	private static IntegrityPlugin plugin;
	
	/**
	 * The constructor.
	 */
	public IntegrityPlugin() {
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
	public static IntegrityPlugin getDefault() {
		return plugin;
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
