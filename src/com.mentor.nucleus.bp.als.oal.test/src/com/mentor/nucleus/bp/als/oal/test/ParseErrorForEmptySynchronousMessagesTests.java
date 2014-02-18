//========================================================================
//
// File: ParseErrorForEmptySynchronousMessagesTests.java
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

package com.mentor.nucleus.bp.als.oal.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.internal.resources.Marker;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.osgi.framework.Bundle;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IAllActivityModifier;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.preferences.BridgePointProjectActionLanguagePreferences;
import com.mentor.nucleus.bp.core.ui.preferences.BridgePointProjectPreferences;
import com.mentor.nucleus.bp.test.common.BaseTest;

public class ParseErrorForEmptySynchronousMessagesTests extends BaseTest {
	String projectName = "testParseErrorForEmptySynchronousMessage";

	@Override
	public void initialSetup() throws Exception {
		loadProject(projectName);
	}

	public void testParseErrorPreferencesPackage() throws BackingStoreException, CoreException {
		Package_c testPkg = Package_c.getOneEP_PKGOnR1401(m_sys,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								"Package-Test");
					}
				});
		IMarker[] errors = parseModel(testPkg);
		assertEquals("Incorrect number of parse errors were found.", 5,
				errors.length);
		ExternalEntity_c ee = ExternalEntity_c
				.getOneS_EEOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPkg));
		ee.setIsrealized(true);
		errors = parseModel(testPkg);
		assertEquals("Incorrect number of parse errors were found.", 4,
				errors.length);
		setProjectPreference(
				BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED,
				true);
		errors = parseModel(testPkg);
		assertEquals("Incorrect number of parse errors were found.", 5,
				errors.length);
		setProjectPreference(
				BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED,
				false);
		setProjectPreference(
				BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE,
				false);
		errors = parseModel(testPkg);
		assertEquals("Incorrect number of parse errors were found.", 0,
				errors.length);
	}

	public void testParseErrorPreferencesComponent() throws BackingStoreException, CoreException {
		project = getProjectHandle(projectName);
		Package_c testPkg = Package_c.getOneEP_PKGOnR1401(m_sys,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								"Component-Test");
					}
				});
		setProjectPreference(
				BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE,
				true);
		setProjectPreference(
				BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED,
				false);
		IMarker[] errors = parseModel(testPkg);
		assertEquals("Incorrect number of parse errors were found.", 0,
				errors.length);
		ExternalEntity_c ee = ExternalEntity_c
				.getOneS_EEOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(Component_c
								.getManyC_CsOnR8001(PackageableElement_c
										.getManyPE_PEsOnR8000(testPkg))))));
		Bridge_c bridge = Bridge_c.getOneS_BRGOnR19(ee);
		bridge.setAction_semantics_internal("// test comment");
		errors = parseModel(testPkg);
		assertEquals("Incorrect number of parse errors were found.", 1,
				errors.length);
		setProjectPreference(
				BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED,
				true);
		errors = parseModel(testPkg);
		assertEquals("Incorrect number of parse errors were found.", 5,
				errors.length);
		setProjectPreference(
				BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE_REALIZED,
				false);
		setProjectPreference(
				BridgePointProjectActionLanguagePreferences.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE,
				false);
		errors = parseModel(testPkg);
		assertEquals("Incorrect number of parse errors were found.", 1,
				errors.length);
	}
	
	private void setProjectPreference(String key, boolean value)
			throws BackingStoreException {
		IScopeContext projectScope = new ProjectScope(project);
		Preferences projectNode = projectScope
				.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.putBoolean(key, value);
	}

	private IMarker[] parseModel(NonRootModelElement testElement) throws CoreException {
        try {
            Bundle ui_text = Platform.getBundle("com.mentor.nucleus.bp.ui.text");//$NON-NLS-1$
            Class<?> factoryClass = ui_text.loadClass("com.mentor.nucleus.bp.ui.text.activity.AllActivityModifier"); //$NON-NLS-1$

            // find the constructor that takes package and monitor as parameters
            Class<?> [] arg_types = new Class[] { testElement.getClass(), IProgressMonitor.class };
            Constructor<?> ctor = factoryClass.getDeclaredConstructor(arg_types);

            // invoke the constructor to create an instance
            Object [] args = new Object[] { testElement, new NullProgressMonitor() };
            IAllActivityModifier aam = (IAllActivityModifier)ctor.newInstance(args);

            aam.processAllActivities(IAllActivityModifier.PARSE);
        } catch (ClassNotFoundException cnf) {
            CorePlugin.logError("Problem loading activity parser ", cnf);
        } catch (IllegalAccessException i) {
            CorePlugin.logError("Problem loading activity parser ", i);
        } catch (InstantiationException i) {
            CorePlugin.logError("Problem loading activity parser ", i);
        } catch (InvocationTargetException e) {
            CorePlugin.logError("Problem running activity parser ", e);
        } catch (SecurityException e) {
            CorePlugin.logError("Problem running activity parser ", e);
        } catch (NoSuchMethodException e) {
            CorePlugin.logError("Problem running activity parser ", e);
        }
		return collectParseErrors(testElement);
	}

	private IMarker[] collectParseErrors(NonRootModelElement testPkg)
			throws CoreException {
		IContainer parent = testPkg.getFile().getParent();
		return parent.findMarkers(Marker.PROBLEM, false,
				IResource.DEPTH_INFINITE);
	}
}
