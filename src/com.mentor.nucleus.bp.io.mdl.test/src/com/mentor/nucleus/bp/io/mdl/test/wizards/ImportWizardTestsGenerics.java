//========================================================================
//
//File:      $RCSfile: ImportWizardTestsGenerics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 05:25:40 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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
//
//  This class is responsible for testing the BridgePoint import
//  wizard
//
package com.mentor.nucleus.bp.io.mdl.test.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.DataTypePackage_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.SystemDatatypePackage_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.io.mdl.wizards.ModelExportPage;
import com.mentor.nucleus.bp.io.mdl.wizards.ModelExportWizard;
import com.mentor.nucleus.bp.io.mdl.wizards.ModelImportPage;
import com.mentor.nucleus.bp.io.mdl.wizards.ModelImportWizard;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTestUtilities;

public class ImportWizardTestsGenerics extends BaseTest {

	/**
	 * Tests that when importing a core data types package, the graphical
	 * elements for the removed core types are also removed
	 * 
	 * @throws CoreException
	 */
	public void testNoOrphanedElementsOnImportOfCoreDataTypePackage()
			throws CoreException {
		IProject testProject = TestingUtilities
				.createProject("TestImportCoreDTPackage");
		// get the system model associated with the project
		// created above
		SystemModel_c systemModel = getSystemModel(testProject.getName());
		assertNotNull("Error getting the system model for this test.",
				systemModel);

		UITestingUtilities.getGraphicalEditorFor(systemModel, true);
		
		Ooaofgraphics sysGraphicsRoot = Ooaofgraphics.getInstance(systemModel
				.getModelRoot().getId());
		AbstractTool tool = UITestingUtilities.getTool("Package");
		UITestingUtilities.activateTool(tool);
		// now create mouse events to create a new user data type
		CanvasTestUtilities.doMouseMove(2000, 2000);
		CanvasTestUtilities.doMousePress(2000, 2000);
		CanvasTestUtilities.doMouseMove(2200, 2200);
		CanvasTestUtilities.doMouseRelease(2200, 2200);
		// deactivate the tool
		UITestingUtilities.deactivateTool(tool);
		
		Package_c dtPackage = Package_c.getOneEP_PKGOnR1401(systemModel,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName()
								.equals("Unnamed Package");
					}

				});		
		UITestingUtilities.getGraphicalEditorFor(dtPackage, true);
		Ooaofgraphics dtGraphicsRoot = Ooaofgraphics.getInstance(dtPackage
				.getModelRoot().getId());		
		tool = UITestingUtilities.getTool("User Data Type");
		UITestingUtilities.activateTool(tool);
		// now create mouse events to create a new user data type
		CanvasTestUtilities.doMouseMove(2000, 2000);
		CanvasTestUtilities.doMousePress(2000, 2000);
		CanvasTestUtilities.doMouseMove(2200, 2200);
		CanvasTestUtilities.doMouseRelease(2200, 2200);
		UITestingUtilities.deactivateTool(tool);
		Selection.getInstance().clear();
		
		Selection.getInstance().addToSelection(dtPackage);
		// now export the dt package to a single file
		ModelExportWizard exportWizard = new ModelExportWizard();
		exportWizard.init(PlatformUI.getWorkbench(), Selection.getInstance()
				.getStructuredSelection());
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), exportWizard);
		dialog.create();
		ModelExportPage exportPage = (ModelExportPage) exportWizard
				.getStartingPage();
		// export the model to a file in the test project
		exportPage.setDestinationField(testProject.getLocation().append(
				"coretypetestGenerics.xtuml").toString());
		boolean result = exportWizard.performFinish();
		assertTrue("Unable to perform export.", result);
		// now import the exported file back into the project
		// select the project so that the check box does not
		// need to be checked
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(systemModel);
		ModelImportWizard importWizard = new ModelImportWizard();
		importWizard.init(PlatformUI.getWorkbench(), Selection.getInstance()
				.getStructuredSelection());
		dialog = new WizardDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), importWizard);
		dialog.create();
		ModelImportPage importPage = (ModelImportPage) importWizard
				.getStartingPage();
		importPage.setSourceField(testProject.getLocation().append(
				"coretypetestGenerics.xtuml").toString());
		result = importWizard.performFinish();
		importWizard.dispose();
		importWizard.getShell().dispose();
		assertTrue("Unable to perform import.", result);
		// and finally assert that there is only one
		// graphical element present in the new 
		// package
		Package_c newDtPackage = Package_c.getOneEP_PKGOnR1401(systemModel,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName()
								.equals("Unnamed Package-1");
					}

				});
		assertNotNull("Unable to locate imported data type package",
				newDtPackage);
		Ooaofgraphics newGraphicsRoot = Ooaofgraphics.getInstance(newDtPackage
				.getModelRoot().getId());
		GraphicalElement_c[] elements = GraphicalElement_c
				.GraphicalElementInstances(newGraphicsRoot);
		assertTrue(
				"Core type's graphical elements were not removed on import.",
				elements.length == 1);
	}
}
