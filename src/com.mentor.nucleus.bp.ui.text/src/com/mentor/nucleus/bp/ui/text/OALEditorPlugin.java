//========================================================================
//
// File:      $RCSfile: OALEditorPlugin.java,v $
// Version:   $Revision: 1.12 $
// Modified:  $Date: 2013/01/10 23:20:56 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
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
