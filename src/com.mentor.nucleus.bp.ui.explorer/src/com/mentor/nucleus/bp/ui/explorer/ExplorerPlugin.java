package com.mentor.nucleus.bp.ui.explorer;
//=====================================================================
//
// File:      $RCSfile: ExplorerPlugin.java,v $
// Version:   $Revision: 1.19 $
// Modified:  $Date: 2013/01/10 23:15:44 $
//
// (c) Copyright 2003-2013 Mentor Graphics Corporation All rights reserved.
//
//=====================================================================
// This document contains information proprietary and confidential to
// Project Technology, Inc. and is not for external distribution.
//=====================================================================
//
// This class is responsible for loading the resources for the Eclipse
// BridgePoint Model Explorer plugin.
//
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.mentor.nucleus.bp.core.common.PersistenceManager;

/**
 * The main plugin class to be used in the desktop.
 */
public class ExplorerPlugin extends AbstractUIPlugin {
  //The shared instance.
  private static ExplorerPlugin plugin;
  //Resource bundle.
  private ResourceBundle resourceBundle;
  /**
   * The constructor.
   */
  public ExplorerPlugin() {
    super();
    plugin = this;
    try {
      resourceBundle = ResourceBundle.getBundle(
                "com.mentor.nucleus.bp.ui.explorer.ExplorerPluginResources");
    } catch (MissingResourceException x) {
        resourceBundle = null;
    }
  }
  /* (non-Javadoc)
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
   */
  public void start(BundleContext context) throws Exception {
      super.start(context);
      try {
    	  PersistenceManager.getDefaultInstance();   // causes initialization
      } catch (Exception e) {
    	  ExplorerPlugin.logError("Unable to initialize persistable components.", e);
      }
  }
/**
   * Returns the shared instance.
   */
  public static ExplorerPlugin getDefault() {
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
    ResourceBundle bundle = ExplorerPlugin.getDefault().getResourceBundle();
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
  
  public static void logError( String msg, Throwable e )
			{
      ExplorerPlugin plugin = getDefault();
      // plugin can be null when running unit test or during initialization.
      if(plugin != null){
           plugin.getLog().log(createStatus(IStatus.ERROR, msg, e));
		} else {
			System.err.println(msg);
			if (e != null) {
				e.printStackTrace();
			}
		}
	}
  
  public static IStatus createStatus(int severity, String message, Throwable e){
      String pluginName = "com.mentor.nucleus.bp.ui.explorer";
      ExplorerPlugin plugin = getDefault();
      // plugin and bundle can be null when running unit test or during initialization.
      if(plugin != null && plugin.getBundle() != null){
           pluginName = plugin.getBundle().getSymbolicName();
	  }  
	      
      return new Status(severity, pluginName, IStatus.ERROR, message, e);
  }
}