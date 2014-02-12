//========================================================================
//
//File:      $RCSfile: CommunicationLinkTestsGenerics.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 04:30:27 $
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

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CommunicationLink_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.LinkFormalizeOnCOMM_LNKWizard;
import com.mentor.nucleus.bp.core.ui.LinkFormalizeOnCOMM_LNKWizardPage2;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.ToggleEndVisibilityOnCOMM_LNKAction;
import com.mentor.nucleus.bp.core.ui.ToggleStartVisibilityOnCOMM_LNKAction;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTestUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

/**
 * Contains tests that exercise the functionality of communication diagrams.
 */
public class CommunicationLinkTestsGenerics extends CanvasTest {
	private String test_id;

	private boolean generateResults;

	public CommunicationLinkTestsGenerics(String arg0) {
		super(null, arg0);
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

	private Selection selection = Selection.getInstance();

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		// if it's the first test of this class that's being setup
		if (!initialized) {
			loadProject("CommunicationTestModel");
			initialized = true;
			workbench = PlatformUI.getWorkbench();
		}
	}

	/**
	 * Tests that communications can be drawn in communications
	 */
	public void testCommunicationInCommunication() {
		test_id = "1";
		String diagramName = "Communication in Communication";
		Package_c communication = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(communication);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");
		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that a link can be formalized against a supertype subtype
	 * association
	 */
	public void testFormalizeLinkWithSupertypeSubtype() {
		test_id = "2";
		String diagramName = "Communication in Communication";
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		CommunicationLink_c testLink = CommunicationLink_c
				.CommunicationLinkInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								CommunicationLink_c selected = (CommunicationLink_c) candidate;
								return selected.getStarttext().equals(
										"superSub");
							}

						});
		assertNotNull(testLink);
		selection.clear();
		selection.addToSelection(testLink);
		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();

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
		selectItemInList("R1", associationCombo);
		assertTrue("Supertype association: " + "R1, "
				+ "was not selected in the wizard.", wizard.performFinish());

		validateOrGenerateResults(ce, generateResults);

	}

	/**
	 * Tests that a link can be formalized against a simple association
	 */
	public void testFormalizeLinkWithSimpleAssociation() {
		test_id = "3";
		String diagramName = "Communication in Communication";
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		CommunicationLink_c testLink = CommunicationLink_c
				.CommunicationLinkInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								CommunicationLink_c selected = (CommunicationLink_c) candidate;
								return selected.getStarttext().equals("simple");
							}

						});
		assertNotNull(testLink);
		selection.clear();
		selection.addToSelection(testLink);
		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();

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
		selectItemInList("R2", associationCombo);
		assertTrue("Simple association: " + "R2, "
				+ "was not selected in the wizard.", wizard.performFinish());

		validateOrGenerateResults(ce, generateResults);

	}

	/**
	 * Tests that a link can be formalized against an associative link,
	 * including the simple association
	 */
	public void testFormalizeLinkWithLinkAssociation() {
		test_id = "4";
		String diagramName = "Communication in Communication";
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		CommunicationLink_c testLink = CommunicationLink_c
				.CommunicationLinkInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								CommunicationLink_c selected = (CommunicationLink_c) candidate;
								return selected.getStarttext().equals(
										"one side");
							}

						});
		assertNotNull(testLink);
		selection.clear();
		selection.addToSelection(testLink);
		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();

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
		selectItemInList("R4", associationCombo);
		assertTrue("Simple association of link: " + "R4, "
				+ "was not selected in the wizard.", wizard.performFinish());

		validateOrGenerateResults(ce, generateResults);

		test_id = "5";
		testLink = CommunicationLink_c.CommunicationLinkInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						CommunicationLink_c selected = (CommunicationLink_c) candidate;
						return selected.getStarttext().equals("link");
					}

				});
		assertNotNull(testLink);
		selection.clear();
		selection.addToSelection(testLink);
		sel = Selection.getInstance().getStructuredSelection();

		// create and initialize the wizard
		wizard = new LinkFormalizeOnCOMM_LNKWizard();
		wizard.init(workbench, sel, null);
		dialog = new WizardDialog(workbench.getActiveWorkbenchWindow()
				.getShell(), wizard);
		dialog.create();

		// Select the association in the wizard
		page = (LinkFormalizeOnCOMM_LNKWizardPage2) wizard.getStartingPage();
		page.createControl(workbench.getActiveWorkbenchWindow().getShell());
		associationCombo = page.AssociationCombo;
		selectItemInList("R4", associationCombo);
		assertTrue("Link: " + "R4, " + "was not selected in the wizard.",
				wizard.performFinish());

		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that a link can be formalized against a reflexive association
	 */
	public void testFormalizeLinkWithReflexiveAssociation() {
		test_id = "6";
		String diagramName = "Communication in Communication";
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		CommunicationLink_c testLink = CommunicationLink_c
				.CommunicationLinkInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								CommunicationLink_c selected = (CommunicationLink_c) candidate;
								return selected.getStarttext().equals(
										"reflexive");
							}

						});
		assertNotNull(testLink);
		selection.clear();
		selection.addToSelection(testLink);
		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();

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
		selectItemInList("R3", associationCombo);
		assertTrue("Reflexive association: " + "R3, "
				+ "was not selected in the wizard.", wizard.performFinish());

		validateOrGenerateResults(ce, generateResults);

	}

	/**
	 * Tests that a link can be formalized against a reflexive associative link
	 */
	public void testFormalizeLinkWithReflexiveLinkedAssociation() {
		test_id = "7";
		String diagramName = "Communication in Communication";
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		CommunicationLink_c testLink = CommunicationLink_c
				.CommunicationLinkInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								CommunicationLink_c selected = (CommunicationLink_c) candidate;
								return selected.getStarttext().equals(
										"reflexive 2");
							}

						});
		assertNotNull(testLink);
		selection.clear();
		selection.addToSelection(testLink);
		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();

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
		selectItemInList("R5", associationCombo);
		assertTrue("Reflexive association: " + "R5, "
				+ "was not selected in the wizard.", wizard.performFinish());

		validateOrGenerateResults(ce, generateResults);

		test_id = "8";

		testLink = CommunicationLink_c.CommunicationLinkInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						CommunicationLink_c selected = (CommunicationLink_c) candidate;
						return selected.getStarttext().equals("reflexive link");
					}

				});
		assertNotNull(testLink);
		selection.clear();
		selection.addToSelection(testLink);
		sel = Selection.getInstance().getStructuredSelection();

		// create and initialize the wizard
		wizard = new LinkFormalizeOnCOMM_LNKWizard();
		wizard.init(workbench, sel, null);
		dialog = new WizardDialog(workbench.getActiveWorkbenchWindow()
				.getShell(), wizard);
		dialog.create();

		// Select the association in the wizard
		page = (LinkFormalizeOnCOMM_LNKWizardPage2) wizard.getStartingPage();
		page.createControl(workbench.getActiveWorkbenchWindow().getShell());
		associationCombo = page.AssociationCombo;
		selectItemInList("R5", associationCombo);
		assertTrue("Reflexive link: " + "R5, "
				+ "was not selected in the wizard.", wizard.performFinish());

		validateOrGenerateResults(ce, generateResults);

	}

	/**
	 * Tests the toggle switch for visibility
	 */
	public void testToggleLinkVisibility() {
		test_id = "9";
		String diagramName = "Communication in Communication";
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		CommunicationLink_c testLink = CommunicationLink_c
				.CommunicationLinkInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								CommunicationLink_c selected = (CommunicationLink_c) candidate;
								return selected.getStarttext().equals(
										"superSub");
							}

						});
		assertNotNull(testLink);
		selection.clear();
		selection.addToSelection(testLink);

		ToggleStartVisibilityOnCOMM_LNKAction toggleStartAction = new ToggleStartVisibilityOnCOMM_LNKAction();
		toggleStartAction.run(new Action() {
		});

		ToggleEndVisibilityOnCOMM_LNKAction toggleEndAction = new ToggleEndVisibilityOnCOMM_LNKAction();
		toggleEndAction.run(new Action() {
		});

		validateOrGenerateResults(ce, generateResults);

		test_id = "10";

		toggleStartAction.run(new Action() {
		});

		toggleEndAction.run(new Action() {
		});

		validateOrGenerateResults(ce, generateResults);

	}

	/**
	 * Selects an item, in a combo list, with the given string if found
	 * 
	 * @param string
	 */
	private void selectItemInList(String item, Combo combo) {
		String[] items = combo.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i].equals(item)) {
				combo.select(i);
				combo.notifyListeners(SWT.Selection, null);
			}
		}
	}

	/**
	 * Returns a communication with the given name
	 */
	private Package_c getPackage(final String name) {
		Package_c communication = Package_c.PackageInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Package_c selected = (Package_c) candidate;
						return selected.getName().equals(name);
					}

				});
		return communication;
	}

	protected String getResultName() {
		return "CommunicationLinkTest" + "_" + test_id;
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
