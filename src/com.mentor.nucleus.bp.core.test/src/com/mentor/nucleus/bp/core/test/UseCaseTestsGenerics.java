//========================================================================
//
//File:      $RCSfile: UseCaseTestsGenerics.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/05/10 04:30:28 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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

import java.lang.reflect.Method;

import org.eclipse.gef.tools.AbstractTool;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTestUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

/**
 * Contains tests that exercise the functionality of communication diagrams.
 */
public class UseCaseTestsGenerics extends CanvasTest {
	private String test_id;

	private boolean generateResults = false;

	public UseCaseTestsGenerics(String arg0) {
		super(null, arg0);
	}

	/**
	 * Whether the first test of this class is the one that's currently being
	 * run.
	 */
	private static boolean firstTest = true;
	private static boolean initialized = false;
	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		// if it's the first test of this class that's being setup
		if (firstTest) {
			if (!initialized) {
				loadProject("blankTest");
				initialized = true;
			}
			IdAssigner.setSeedOfAllInstances(1);
			firstTest = false;
		}
	}

	/**
	 * Tests that use cases can be drawn in a domain
	 */
	public void testUseCaseInDomain() {
		test_id = "1";
		String diagramName = "blankTest";
		Package_c domain = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c(diagramName));
		CanvasUtilities.openCanvasEditor(domain);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		AbstractTool tool = UITestingUtilities.getTool("Package");
		UITestingUtilities.activateTool(tool);

		CanvasTestUtilities.doMouseMove(0, 0);
		CanvasTestUtilities.doMousePress(0, 0);
		CanvasTestUtilities.doMouseMove(200, 200);
		CanvasTestUtilities.doMouseRelease(200, 200);

		UITestingUtilities.deactivateTool(tool);
		Package_c pkg = Package_c.getOneEP_PKGOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(domain));
		Ooaofooa.setPersistEnabled(true);
		pkg.setName("Use Case Diagram");
		Ooaofooa.setPersistEnabled(false);
		validateOrGenerateResultsGenerics(ce, generateResults, false);
	}

	/**
	 * Tests that use cases symbols can be drawn
	 */
	public void testUseCaseSymbols() {
		test_id = "2";
		Package_c ucc = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("Use Case Diagram"));
		CanvasUtilities.openCanvasEditor(ucc);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(ucc.getName()
				+ ": Package Diagram");

		AbstractTool tool = UITestingUtilities.getTool("Use Case");
		UITestingUtilities.activateTool(tool);

		// create the use case symbol
		CanvasTestUtilities.doMouseMove(1, 1);
		CanvasTestUtilities.doMousePress(1, 1);
		CanvasTestUtilities.doMouseMove(200, 200);
		CanvasTestUtilities.doMouseRelease(200, 200);

		// create a second use case symbol
		CanvasTestUtilities.doMouseMove(400, 1);
		CanvasTestUtilities.doMousePress(400, 1);
		CanvasTestUtilities.doMouseMove(600, 200);
		CanvasTestUtilities.doMouseRelease(600, 200);

		UITestingUtilities.deactivateTool(tool);

		tool = UITestingUtilities.getTool("Actor");
		UITestingUtilities.activateTool(tool);

		// create an actor
		CanvasTestUtilities.doMouseMove(1, 400);
		CanvasTestUtilities.doMousePress(1, 400);
		CanvasTestUtilities.doMouseMove(200, 600);
		CanvasTestUtilities.doMouseRelease(200, 600);

		// create a second actor
		CanvasTestUtilities.doMouseMove(600, 400);
		CanvasTestUtilities.doMousePress(600, 400);
		CanvasTestUtilities.doMouseMove(800, 600);
		CanvasTestUtilities.doMouseRelease(800, 600);

		UITestingUtilities.deactivateTool(tool);

		//		tool = UITestingUtilities.getTool("Use Case Diagram");
		//		UITestingUtilities.activateTool(tool);
		//
		//		// create a package
		//		CanvasTestUtilities.doMouseMove(800, 1);
		//		CanvasTestUtilities.doMousePress(800, 1);
		//		CanvasTestUtilities.doMouseMove(1000, 200);
		//		CanvasTestUtilities.doMouseRelease(1000, 200);
		//
		//		UITestingUtilities.deactivateTool(tool);

		tool = UITestingUtilities.getTool("Use Case", "Association");
		UITestingUtilities.activateTool(tool);

		// create the associations
		// Association, between use case and actor
		CanvasTestUtilities.doMouseMove(100, 10);
		CanvasTestUtilities.doMousePress(100, 10);
		CanvasTestUtilities.doMouseMove(100, 500);
		CanvasTestUtilities.doMouseRelease(100, 500);

		UITestingUtilities.deactivateTool(tool);

		tool = UITestingUtilities.getTool("Generalization");
		UITestingUtilities.activateTool(tool);

		// Generalization, between to use cases
		CanvasTestUtilities.doMouseMove(100, 10);
		CanvasTestUtilities.doMousePress(100, 10);
		CanvasTestUtilities.doMouseMove(500, 10);
		CanvasTestUtilities.doMouseRelease(500, 10);

		// Generalization, between to actors
		CanvasTestUtilities.doMouseMove(100, 500);
		CanvasTestUtilities.doMousePress(100, 500);
		CanvasTestUtilities.doMouseMove(700, 500);
		CanvasTestUtilities.doMouseRelease(700, 500);

		UITestingUtilities.deactivateTool(tool);

		tool = UITestingUtilities.getTool("Include");
		UITestingUtilities.activateTool(tool);

		// Include, between to use cases
		CanvasTestUtilities.doMouseMove(100, 50);
		CanvasTestUtilities.doMousePress(100, 50);
		CanvasTestUtilities.doMouseMove(500, 50);
		CanvasTestUtilities.doMouseRelease(500, 50);

		UITestingUtilities.deactivateTool(tool);

		tool = UITestingUtilities.getTool("Extend");
		UITestingUtilities.activateTool(tool);

		CanvasTestUtilities.doMouseMove(100, 100);
		CanvasTestUtilities.doMousePress(100, 100);
		CanvasTestUtilities.doMouseMove(500, 100);
		CanvasTestUtilities.doMouseRelease(500, 100);

		UITestingUtilities.deactivateTool(tool);

		validateOrGenerateResultsGenerics(ce, generateResults, false);

	}

	/**
	 * Tests that use cases can be drawn in a subsystem
	 */
	//	public void testUseCaseInSS() {
	//		test_id = "3";
	//		String diagramName = "blankTest";
	//		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
	//				.closeAllEditors(false);
	//		dispatchEvents(10);
	//		Package_c domain = Package_c.PackageInstance(modelRoot,
	//				new Package_by_name_c(diagramName));
	//		CanvasTestUtilities.openCanvasEditor(domain);
	//		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
	//				+ ": Package Diagram");
	//
	//		AbstractTool tool = UITestingUtilities.getTool("Package");
	//		UITestingUtilities.activateTool(tool);
	//
	//		CanvasTestUtilities.doMouseMove(400, 0);
	//		CanvasTestUtilities.doMousePress(400, 0);
	//		CanvasTestUtilities.doMouseMove(600, 200);
	//		CanvasTestUtilities.doMouseRelease(600, 200);
	//
	//		UITestingUtilities.deactivateTool(tool);
	//
	//		Package_c ss = Package_c.PackageInstance(modelRoot,
	//				new Package_by_name_c("Unnamed Package"));
	//		CanvasTestUtilities.openCanvasEditor(ss);
	//		ce = CanvasTestUtilities.getCanvasEditor(ss.getName()
	//				+ ": Class Diagram");
	//
	//		tool = UITestingUtilities.getTool("Use Case Diagram");
	//		UITestingUtilities.activateTool(tool);
	//
	//		CanvasTestUtilities.doMouseMove(0, 0);
	//		CanvasTestUtilities.doMousePress(0, 0);
	//		CanvasTestUtilities.doMouseMove(200, 200);
	//		CanvasTestUtilities.doMouseRelease(200, 200);
	//
	//		UITestingUtilities.deactivateTool(tool);
	//
	//		validateOrGenerateResults(ce, generateResults);
	//	}

	/**
	 * Test creating use case under component package
	 */

	//	public void testCreateInformalUseCaseUnderComponentPackage() {
	//		test_id = "5";
	//		CanvasTestUtilities.openCanvasEditor(m_sys);
	//		GraphicalEditor activeEditor = (GraphicalEditor) UITestingUtilities
	//				.getActiveEditor();
	//		assertNotNull(activeEditor);
	//		SequenceTests.createShapeInDiagram(activeEditor, new Point(600, 300),
	//				"Package");
	//		Package_c cp = Package_c.getOneEP_PKGOnR1401(m_sys,
	//				new Package_by_name_c("Unnamed Package"));
	//		assertNotNull("Unable to create test component package.", cp);
	//		CanvasTestUtilities.openCanvasEditor(cp);
	//		activeEditor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
	//		assertNotNull(activeEditor);
	//		SequenceTests.createShapeInDiagram(activeEditor, new Point(300, 300),
	//				"Package");
	//		UseCaseDiagram_c ucd = UseCaseDiagram_c.getOneUC_UCCOnR1212(cp);
	//		assertNotNull("Use Case could not be created under component package.",
	//				ucd);
	//		graphicsModelRoot = (Ooaofgraphics) activeEditor.getModel()
	//				.getModelRoot();
	//		validateOrGenerateResults(activeEditor, generateResults);
	//	}

	/**
	 * Test creating use case under component
	 */

	//	public void testCreateInformalUseCaseUnderComponent() {
	//		SequenceTestsGenerics.resizeMainWindow();
	//		test_id = "6";
	//		Package_c cp = Package_c.getOneEP_PKGOnR1401(m_sys);
	//		CanvasTestUtilities.openCanvasEditor(cp);
	//		GraphicalEditor activeEditor = (GraphicalEditor) UITestingUtilities
	//				.getActiveEditor();
	//		assertNotNull(activeEditor);
	//		SequenceTestsGenerics.createShapeInDiagram(activeEditor, new Point(300,
	//				300), "Componenets", "Component");
	//		Component_c comp = Component_c.getOneC_COnR4604(cp);
	//		assertNotNull("Unable to create test component.", comp);
	//		CanvasTestUtilities.openCanvasEditor(comp);
	//		activeEditor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
	//		((GraphicalEditPart) activeEditor.getGraphicalViewer().getContents())
	//				.getFigure().getUpdateManager().performUpdate();
	//		assertNotNull(activeEditor);
	//		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch());
	//		SequenceTestsGenerics.createShapeInDiagram(activeEditor, new Point(300,
	//				300), "Use Case Diagram");
	//		UseCaseDiagram_c ucd = UseCaseDiagram_c.getOneUC_UCCOnR1213(comp);
	//		assertNotNull("Use Case could not be created under component.", ucd);
	//		graphicsModelRoot = (Ooaofgraphics) activeEditor.getModel()
	//				.getModelRoot();
	//		validateOrGenerateResults(activeEditor, generateResults);
	//	}

	protected String getResultName() {
		return "UseCaseTest" + "_" + test_id;
	}

	public void setGenerateResults() {
		try {
			generateResults = true;
			setUp();
			Method methods[] = this.getClass().getMethods();
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getName().startsWith("test")) {
					methods[i].invoke(this, new Object[]{});
				}
			}
		} catch (Exception e) {
			System.out.println("Exception encountered by test result creator: "
					+ e);
		}
	}
}
