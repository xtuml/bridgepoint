//=====================================================================
//
//File:      $RCSfile: ComparePlugin.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/10 22:49:58 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//========================================================================
package org.xtuml.bp.model.compare;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * The main plugin class to be used in the desktop.
 */
public class ComparePlugin extends AbstractUIPlugin {
	//The shared instance.
	private static ComparePlugin plugin;
	private static ImageRegistry imageRegistry;
	//Resource bundle.
	private ResourceBundle resourceBundle;

	private ModelCacheManager modelCacheManager = new ModelCacheManager();

	private static final String RESOURCE_BUNDLE = "org.xtuml.bp.model.compare.ComparePluginMessages"; //$NON-NLS-1$

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

    /**
     * This routine initializes the static member imageRegistry if it has not been initialized.
     * This is done here instead of in a member initializer for plugin start-up purposes.
     * When called from the command-line there may not be a workbench.
     */
	private static ImageRegistry getStaticImageRegistry() {
		if (imageRegistry == null) {
			if (Display.getCurrent() != null) {
				imageRegistry = new ImageRegistry(Display.getCurrent());
			} else {
				imageRegistry = new ImageRegistry(PlatformUI.getWorkbench().getDisplay());	
			}
		}
		return imageRegistry;
	}
	
	
	public static ImageDescriptor getImageDescriptor(String path) {
		try {
			URL installURL = plugin.getBundle().getEntry("/");
			URL url = new URL(installURL, path);
	        ImageDescriptor imageDescriptor = getStaticImageRegistry().getDescriptor(path);
	        if (imageDescriptor == null) {
	            imageDescriptor = ImageDescriptor.createFromURL(url);
	            getStaticImageRegistry().put(path, imageDescriptor);
	        }
	        return imageDescriptor;
		} catch (MalformedURLException e) {
			writeToLog("Invalid image path.", e, ComparePlugin.class);
		}
        return null;
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
