//========================================================================
//
// File:      $RCSfile: OALEditorPlugin.java,v $
// Version:   $Revision: 1.12 $
// Modified:  $Date: 2013/01/10 23:20:56 $
//
// (c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// This document contains information proprietary and confidential to
// Mentor Graphics Corp., and is not for external distribution.
//========================================================================

package com.mentor.nucleus.bp.ui.text;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.ui.text.editor.SyntaxHighlightingPreferences;
import com.mentor.nucleus.bp.ui.text.editor.SyntaxHighlightingPreferencesStore;
import com.mentor.nucleus.bp.ui.preference.BasePlugin;

/**
 * The main plugin class to be used in the desktop.
 */
public class OALEditorPlugin extends BasePlugin {
    //The shared instance.
    private static OALEditorPlugin plugin;
    //Resource bundle.
    private ResourceBundle resourceBundle;

    public static final String SYNTAX_HIGHLIGHTING_PREFERENCES = "com.mentor.nucleus.bp.ui.text.editor.SyntaxHighlightingPreferences";

    /**
     * The constructor.
     */
    public OALEditorPlugin() {
        super();
        plugin = this;
        try {
            resourceBundle = ResourceBundle.getBundle("ActionEditor.ActionEditorPluginResources");
        } catch (MissingResourceException x) {
            resourceBundle = null;
        }
    }

    protected void initPreferencesModels() throws CoreException {
        registerPreferencesStore(new SyntaxHighlightingPreferencesStore(), true);
    }

    public SyntaxHighlightingPreferences getSyntaxHighlightingPreferences() {
        return (SyntaxHighlightingPreferences) getPreferenceModel(SYNTAX_HIGHLIGHTING_PREFERENCES);
    }

    /**
     * Returns the shared instance.
     */
    public static OALEditorPlugin getDefaultOALPlugin() {
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
        ResourceBundle bundle = OALEditorPlugin.getDefaultOALPlugin().getResourceBundle();
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
