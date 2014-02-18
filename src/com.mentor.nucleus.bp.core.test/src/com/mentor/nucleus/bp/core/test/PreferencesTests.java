//========================================================================
//
// File: PreferencesTests.java
//
// Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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

package com.mentor.nucleus.bp.core.test;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Pref_c;
import com.mentor.nucleus.bp.core.ui.preferences.BridgePointProjectActionLanguagePreferences;
import com.mentor.nucleus.bp.core.ui.preferences.BridgePointProjectPreferences;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

public class PreferencesTests extends BaseTest {
	public void testWorkspaceDefaults() {
		boolean enabledForNonRealized = CorePlugin
				.getDefault()
				.getPreferenceStore()
				.getBoolean(
						BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE);
		boolean enabledForRealized = CorePlugin
				.getDefault()
				.getPreferenceStore()
				.getBoolean(
						BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED);
		assertTrue(
				"Default for empty synchronous parse errors is not correct.",
				enabledForNonRealized);
		assertFalse(
				"Default for realized empty synchronous parse errors is not correct.",
				enabledForRealized);
	}

	public void testProjectDefaults() throws CoreException {
		IProject project = TestingUtilities
				.createProject("test_project_preferences");
		boolean enabledForNonRealized = Pref_c
				.Getsystemboolean(
						BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE,
						project.getName());
		boolean enabledForRealized = Pref_c
				.Getsystemboolean(
						BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED,
						project.getName());
		assertTrue(
				"Default for empty synchronous parse errors is not correct.",
				enabledForNonRealized);
		assertFalse(
				"Default for realized empty synchronous parse errors is not correct.",
				enabledForRealized);
	}

	public void testProjectDefaultsFromWorkspaceDefaults() {
		IProject project = getProjectHandle("test_project_preferences");
		// set the values in the workspace to non-defaults
		CorePlugin
				.getDefault()
				.getPreferenceStore()
				.setValue(
						BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE,
						false);
		CorePlugin
				.getDefault()
				.getPreferenceStore()
				.setValue(
						BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED,
						false);
		boolean enabledForNonRealized = Pref_c
				.Getsystemboolean(
						BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE,
						project.getName());
		boolean enabledForRealized = Pref_c
				.Getsystemboolean(
						BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED,
						project.getName());
		assertFalse(
				"Default for empty synchronous parse errors is not correct.",
				enabledForNonRealized);
		assertFalse(
				"Default for realized empty synchronous parse errors is not correct.",
				enabledForRealized);
		CorePlugin
				.getDefault()
				.getPreferenceStore()
				.setValue(
						BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE,
						true);
		CorePlugin
				.getDefault()
				.getPreferenceStore()
				.setValue(
						BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED,
						true);
		enabledForNonRealized = Pref_c
				.Getsystemboolean(
						BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE,
						project.getName());
		enabledForRealized = Pref_c
				.Getsystemboolean(
						BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED,
						project.getName());
		assertTrue(
				"Default for empty synchronous parse errors is not correct.",
				enabledForNonRealized);
		assertTrue(
				"Default for realized empty synchronous parse errors is not correct.",
				enabledForRealized);
		CorePlugin
				.getDefault()
				.getPreferenceStore()
				.setValue(
						BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED,
						false);
	}

	public void testProjectDefaultsOverrideWorkspaceDefaults()
			throws BackingStoreException {
		IProject project = getProjectHandle("test_project_preferences");
		IScopeContext projectScope = new ProjectScope(project);
		Preferences projectNode = projectScope
				.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode
				.putBoolean(
						BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE,
						false);
		projectNode.sync();
		boolean enabledForNonRealized = Pref_c
				.Getsystemboolean(
						BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE,
						project.getName());
		assertFalse("Project preference did not override workspace preference",
				enabledForNonRealized);
	}
}
