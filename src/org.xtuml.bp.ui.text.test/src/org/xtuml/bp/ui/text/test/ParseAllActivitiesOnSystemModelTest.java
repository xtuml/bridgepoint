//=====================================================================
//
//File:      $RCSfile: ParseAllOnModelReloadTest.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/05/10 06:02:35 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

package org.xtuml.bp.ui.text.test;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.ComponentResourceListener;
import org.xtuml.bp.core.common.IdAssigner;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.test.common.TextEditorUtils;
import org.xtuml.bp.ui.text.TextPlugin;
import org.xtuml.bp.ui.text.activity.ParseAllActivitiesAction;

/**
 * Holds tests pertaining to the parse-all action that occurs when a model is
 * reloaded from disk.
 */
public class ParseAllActivitiesOnSystemModelTest extends UITextTest {
	/**
	 * Constructor.
	 */
	public ParseAllActivitiesOnSystemModelTest(String name)
			throws CoreException {
		super(null, name);
	}

	String projOneName = "ProjectOne";
	String projTwoName = "ProjectTwo";

	IProject projectOne;
	IProject projectTwo;

	SystemModel_c m_sysOne;
	SystemModel_c m_sysTwo;

	protected void setUp() throws Exception {
		super.setUp();

		if (!projectExists(projOneName)) {
			IdAssigner.setSeedOfAllInstances(projOneName.hashCode());
			try {
				projectOne = TestingUtilities.createProject(projOneName);
			} catch (CoreException e) {
				fail(e.getMessage());
			}
			IdAssigner.setSeedOfAllInstances(0);
		} else {
			projectOne = getProjectHandle(projOneName);
		}

		if (!projectExists(projTwoName)) {
			IdAssigner.setSeedOfAllInstances(projTwoName.hashCode());
			try {
				projectTwo = TestingUtilities.createProject(projTwoName);
			} catch (CoreException e) {
				fail(e.getMessage());
			}

			IdAssigner.setSeedOfAllInstances(0);
		} else {
			projectTwo = getProjectHandle(projTwoName);
		}

		m_sysOne = getSystemModel(projOneName);
		m_sysTwo = getSystemModel(projTwoName);

		String test_repository_location = System
				.getenv("XTUML_TEST_MODEL_REPOSITORY");
		if (test_repository_location == null
				|| test_repository_location.equals("")) {
			// use the default location
			test_repository_location = BaseTest.DEFAULT_XTUML_TEST_MODEL_REPOSITORY;
		}

		String locOne = test_repository_location+ "/"
				+ "ParseAllOnSystemTestModels/" + projOneName + ".xtuml";//$NON-NLS-1$
		TestingUtilities.importModelUsingWizard(m_sysOne, locOne, false);

		String locTwo = test_repository_location+"/"
				+ "ParseAllOnSystemTestModels/" + projTwoName + ".xtuml";//$NON-NLS-1$
		TestingUtilities.importModelUsingWizard(m_sysTwo, locTwo, false);

	}

	/**
	 * For issue 863. Tests that a parse-all occurs when a model is reloaded
	 * from disk due to its having changed outside of Eclipse.
	 */
	public void testParseAllActivitiesOnSystemModel() throws Exception {
		// ensure the parse on resource change preference is enabled

		Package_c packageOne = Package_c.getOneEP_PKGOnR1405(m_sysOne,
				new Package_by_name_c("PackageOne"));
		Package_c PackageTwo = Package_c.getOneEP_PKGOnR1405(m_sysOne,
				new Package_by_name_c("PackageTwo"));
		Package_c nestedOne = Package_c.getOneEP_PKGOnR1405(m_sysOne,
				new Package_by_name_c("nestedOne"));
		Package_c NestedTwo = Package_c.getOneEP_PKGOnR1405(m_sysOne,
				new Package_by_name_c("NestedTwo"));

		ExternalEntity_c ee = ExternalEntity_c
				.getOneS_EEOnR8001(PackageableElement_c
						.getOnePE_PEOnR8000(packageOne));
		ee.setKey_lett("BadKeyLet");

		Package_c packageOneP2 = Package_c.getOneEP_PKGOnR1405(m_sysTwo,
				new Package_by_name_c("PackageOne"));
		Package_c PackageTwoP2 = Package_c.getOneEP_PKGOnR1405(m_sysTwo,
				new Package_by_name_c("PackageTwo"));
		Package_c nestedOneP2 = Package_c.getOneEP_PKGOnR1405(m_sysTwo,
				new Package_by_name_c("nestedOne"));
		Package_c NestedTwoP2 = Package_c.getOneEP_PKGOnR1405(m_sysTwo,
				new Package_by_name_c("NestedTwo"));

		ExternalEntity_c eeP2 = ExternalEntity_c
				.getOneS_EEOnR8001(PackageableElement_c
						.getOnePE_PEOnR8000(packageOneP2));
		eeP2.setKey_lett("BadKeyLet");

		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(m_sysOne);
		Selection.getInstance().addToSelection(m_sysTwo);

		// Parse All
		ParseAllActivitiesAction paaa = new ParseAllActivitiesAction();
		paaa.run(null);

		PersistenceManager manager = PersistenceManager.getDefaultInstance();
		IWorkspace workspace = TextPlugin.getWorkspace();

		Package_c[] testPackages = { PackageTwo, nestedOne, NestedTwo,
				PackageTwoP2, nestedOneP2, NestedTwoP2 };

		for (int i = 0; i < testPackages.length; i++) {

			PersistableModelComponent PackageComponent = manager
					.getComponent(testPackages[i]);
			IPath PackageComponentPath = PackageComponent
					.getContainingDirectoryPath();
			IFile placeholder = workspace.getRoot().getFile(
					PackageComponentPath.append(testPackages[i] + "__fn.oal"));
			assertTrue(
					"Activity file which should have Errors does not have problem markers",
					TextEditorUtils.getMarkers(placeholder).length >= 0);

		}

		ee.setKey_lett("E");
		eeP2.setKey_lett("E");

		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(m_sysOne);
		Selection.getInstance().addToSelection(m_sysTwo);

		// Parse All
		ParseAllActivitiesAction paa2 = new ParseAllActivitiesAction();
		paa2.run(null);

		for (int i = 0; i < testPackages.length; i++) {

			PersistableModelComponent PackageComponent = manager
					.getComponent(testPackages[i]);
			IPath PackageComponentPath = PackageComponent
					.getContainingDirectoryPath();
			IFile placeholder = workspace.getRoot().getFile(
					PackageComponentPath.append(testPackages[i] + "__fn.oal"));
			assertTrue(
					"Activity file which should have No Errors has problem markers",
					TextEditorUtils.getMarkers(placeholder).length == 0);

		}


	}

}
