package com.mentor.nucleus.bp.als.oal;
//====================================================================
//
// File:      $RCSfile: OalPlugin.java,v $
// Version:   $Revision: 1.13 $
// Modified:  $Date: 2013/01/10 23:43:47 $
//
// (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
//
//====================================================================
//
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * The main plugin class to be used in the desktop.
 */
public class OalPlugin extends AbstractUIPlugin {
  //The shared instance.
  private static OalPlugin plugin;
  //Resource bundle.
  private ResourceBundle resourceBundle;
  /**
   * The constructor.
   */
  public OalPlugin() {
    super();
    plugin = this;
    try {
      resourceBundle= ResourceBundle.getBundle("com.mentor.nucleus.bp.als.oal.OalPluginResources");
    } catch (MissingResourceException x) {
      resourceBundle = null;
    }
  }
  /**
   * Returns the shared instance.
   */
  public static OalPlugin getDefault() {
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
    ResourceBundle bundle= OalPlugin.getDefault().getResourceBundle();
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
}
