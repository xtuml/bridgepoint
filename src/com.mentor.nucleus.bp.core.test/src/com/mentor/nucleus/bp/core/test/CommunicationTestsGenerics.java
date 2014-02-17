//========================================================================
//
//File:      $RCSfile: CommunicationTestsGenerics.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 04:30:25 $
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

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CommunicationLink_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.LinkFormalizeOnCOMM_LNKWizard;
import com.mentor.nucleus.bp.core.ui.LinkFormalizeOnCOMM_LNKWizardPage2;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTestUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

/**
 * Contains tests that exercise the functionality of communication diagrams.
 */
public class CommunicationTestsGenerics extends CanvasTest {
	private String test_id;

	private boolean generateResults = false;

	public CommunicationTestsGenerics(String arg0) {
		super(arg0);
	}

	/**
	 * Whether the first test of this class is the one that's currently being
	 * run.
	 */
	private static boolean initialized = false;

	/**
	 * The workbench that is used throughout these tests.
	 */
	private static IWorkbench workbench;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		// if it's the first test of this class that's being setup
		if (!initialized) {
			//    ensureAvailableAndLoaded("Models", "CommunicationTestModel", true, false);
			loadProject("CommunicationTestModel");
			initialized = true;
			workbench = PlatformUI.getWorkbench();
		}
	}

	/**
	 * Tests that communications can be drawn in a domain
	 */
	public void testCommunicationInDomain() {
		test_id = "1";
//		String diagramName = "CommunicationTestModel";
//		Domain_c domain = Domain_c.DomainInstance(modelRoot);
//		CanvasTestUtilities.openCanvasEditor(domain);
//		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
//				+ ": Domain Package Diagram");
//		validateOrGenerateResults(ce, generateResults);
//		test_id = "2";
//		diagramName = "Communication in Domain";
//		Communication_c communication = getCommunication(diagramName);
//		CanvasTestUtilities.openCanvasEditor(communication);
//		ce = CanvasTestUtilities.getCanvasEditor(diagramName
//				+ ": Communication Diagram");
//		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that communications can be drawn in a subsystem
	 */
	public void testCommunicationInSubsystem() {
		test_id = "3";
//		String diagramName = "Subsystem";
//		Subsystem_c subsystem = Subsystem_c.SubsystemInstance(modelRoot);
//		CanvasTestUtilities.openCanvasEditor(subsystem);
//		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
//				+ ": Class Diagram");
//		validateOrGenerateResults(ce, generateResults);
//		test_id = "4";
//		diagramName = "Communication in Subsystem";
//		Communication_c communication = getCommunication(diagramName);
//		CanvasTestUtilities.openCanvasEditor(communication);
//		ce = CanvasTestUtilities.getCanvasEditor(diagramName
//				+ ": Communication Diagram");
//		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that MC-Java will not return duplicates when the same instance is
	 * contained across an association more that once.
	 */
	public void testGetManyReturnsNoDuplicates() {
		CommunicationLink_c link = CommunicationLink_c
				.CommunicationLinkInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								CommunicationLink_c selected = (CommunicationLink_c) candidate;
								return selected.getStarttext().equals(
										"reflexive");
							}

						});
		assertNotNull(link);

		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(link);
		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();

		// if the wizard page contains two R3 entries
		// then the bug still exists
		// create and initialize the wizard
		LinkFormalizeOnCOMM_LNKWizard wizard = new LinkFormalizeOnCOMM_LNKWizard();
		wizard.init(workbench, sel, null);
		WizardDialog dialog = new WizardDialog(workbench
				.getActiveWorkbenchWindow().getShell(), wizard);
		dialog.create();

		// Select the association in the wizard
		LinkFormalizeOnCOMM_LNKWizardPage2 page = (LinkFormalizeOnCOMM_LNKWizardPage2) wizard
				.getStartingPage();
		page.createControl(workbench.getActiveWorkbenchWindow().getShell());
		Combo associationCombo = page.AssociationCombo;
		String[] items = associationCombo.getItems();
		int r3Count = 0;
		for (int i = 0; i < items.length; i++) {
			if (items[i].equals("R3")) {
				r3Count += 1;
			}
		}
		assertTrue("Duplicates were returned with a get many call.",
				r3Count == 1);
	}

//	/**
//	 * Test creating communication under the system level
//	 */
//	public void testCreateInformalCommunicationUnderSystem() {
//		test_id = "5";
//		CanvasTestUtilities.openCanvasEditor(m_sys);
//		GraphicalEditor activeEditor = (GraphicalEditor) UITestingUtilities
//				.getActiveEditor();
//		assertNotNull(activeEditor);
//		SequenceTests.createShapeInDiagram(activeEditor, new Point(300, 300),
//				"Communication Diagram");
//		Communication_c comm = Communication_c.getOneCOMM_COMMOnR1136(m_sys);
//		assertNotNull("Communication could not be created under the system.",
//				comm);
//		graphicsModelRoot = (Ooaofgraphics) activeEditor.getModel()
//				.getModelRoot();
//		validateOrGenerateResults(activeEditor, generateResults);
//		// create lifespans and component part
//		test_id = "6";
//		CanvasTestUtilities.openCanvasEditor(comm);
//		activeEditor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
//		assertNotNull(activeEditor);
//		SequenceTests.createLifespansAndComponentParticipant(activeEditor);
//		graphicsModelRoot = (Ooaofgraphics) activeEditor.getModel()
//				.getModelRoot();
//		validateOrGenerateResults(activeEditor, generateResults);
//	}

	/**
	 * Test creating communication under component package
	 */

//	public void testCreateInformalCommunicationUnderPackage() {
//		test_id = "7";
//		CanvasTestUtilities.openCanvasEditor(m_sys);
//		GraphicalEditor activeEditor = (GraphicalEditor) UITestingUtilities
//				.getActiveEditor();
//		assertNotNull(activeEditor);
//		SequenceTests.createShapeInDiagram(activeEditor, new Point(600, 300),
//				"Package Diagram");
//		Package_c cp = Package_c.getOneEP_PKGOnR1405(m_sys);
//		assertNotNull("Unable to create test component package.", cp);
//		CanvasTestUtilities.openCanvasEditor(cp);
//		activeEditor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
//		assertNotNull(activeEditor);
//		SequenceTests.createShapeInDiagram(activeEditor, new Point(300, 300),
//				"Communication Diagram");
//		Package_c comm = Package_c.getOneCOMM_COMMOnR1137(cp);
//		assertNotNull(
//				"Communication could not be created under component package.",
//				comm);
//		graphicsModelRoot = (Ooaofgraphics) activeEditor.getModel()
//				.getModelRoot();
//		validateOrGenerateResults(activeEditor, generateResults);
//	}

	/**
	 * Test creating communication under component
	 */

//	public void testCreateInformalCommunicationUnderComponent() {
//		SequenceTests.resizeMainWindow();
//		test_id = "8";
//		ComponentPackage_c cp = ComponentPackage_c.getOneCP_CPOnR4602(m_sys);
//		CanvasTestUtilities.openCanvasEditor(cp);
//		GraphicalEditor activeEditor = (GraphicalEditor) UITestingUtilities
//				.getActiveEditor();
//		assertNotNull(activeEditor);
//		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch());
//		SequenceTests.createShapeInDiagram(activeEditor, new Point(300, 300),
//				"Component");
//		Component_c comp = Component_c.getOneC_COnR4604(cp);
//		assertNotNull("Unable to create test component.", comp);
//		CanvasTestUtilities.openCanvasEditor(comp);
//		activeEditor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
//		((GraphicalEditPart) activeEditor.getGraphicalViewer().getContents())
//				.getFigure().getUpdateManager().performUpdate();
//		assertNotNull(activeEditor);
//		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch());
//		SequenceTests.createShapeInDiagram(activeEditor, new Point(300, 300),
//				"Communication Diagram");
//		Communication_c comm = Communication_c.getOneCOMM_COMMOnR1138(comp);
//		assertNotNull("Communication could not be created under component.",
//				comm);
//		graphicsModelRoot = (Ooaofgraphics) activeEditor.getModel()
//				.getModelRoot();
//		validateOrGenerateResults(activeEditor, generateResults);
//	}

	/**
	 * Returns a communication with the given name
	 */
	private Package_c getPackage(final String name) {
		Package_c communication = Package_c.PackageInstance(
				modelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Package_c selected = (Package_c) candidate;
						return selected.getName().equals(name);
					}

				});
		return communication;
	}

	protected String getResultName() {
		return "CommunicationTest" + "_" + test_id;
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
