//========================================================================
//
//File:      $RCSfile: XMIExport.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 22:44:48 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//======================================================================== 
package com.mentor.nucleus.bp.mc.xmiexport;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


/**
 * The plugin class for the Resource Programming Plugin. Instantiated by the
 * platform when the plug-in is started.
 */
public class XMIExport extends AbstractUIPlugin implements
    IStartup {

  //Reference to the shared instance, returned when requested.
  private static XMIExport plugin;

  public XMIExport() {
    super();
    plugin = this;
  }


  public void earlyStartup() {
    // add listener when plugin is started
  }

  public static XMIExport getDefault() {
    return plugin;
  }
  
	public static String getPluginPath() {
	// getLocation returns the location prepended with "update@/"
	return getDefault().getBundle().getLocation().replaceFirst("update@", "");
	}


	public static void logError(String msg, Exception e) {
		XMIExport plugin = getDefault();
		// plugin can be null when running unit tests
		if (plugin != null) {
			String pluginName = "com.mentor.nucleus.bp.mc.xmiexport"; //$NON-NLS-1$
			if (plugin.getBundle() != null) // may be null during initialization
			{
				pluginName = plugin.getBundle().getSymbolicName();
			}
			Status status = new Status(
				IStatus.ERROR,
				pluginName,
				IStatus.ERROR,
				msg,
				e);
			plugin.getLog().log(status);
		} else {
			System.err.println(msg);
			if (e != null) {
				e.printStackTrace();
			}
		}
	}	

}

