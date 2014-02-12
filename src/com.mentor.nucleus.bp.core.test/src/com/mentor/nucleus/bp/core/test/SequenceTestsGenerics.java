//========================================================================
//
//File:      $RCSfile: SequenceTestsGenerics.java,v $
//Version:   $Revision: 1.11 $
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

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.AsynchronousMessage_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BridgeParameter_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.ClassInstanceParticipant_c;
import com.mentor.nucleus.bp.core.ClassParticipant_c;
import com.mentor.nucleus.bp.core.ComponentParticipant_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.ExternalEntityParticipant_c;
import com.mentor.nucleus.bp.core.FunctionParameter_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.InstanceAttributeValue_c;
import com.mentor.nucleus.bp.core.InteractionParticipant_c;
import com.mentor.nucleus.bp.core.MessageArgument_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.OperationParameter_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.PackageParticipant_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.StateMachineEventDataItem_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.SynchronousMessage_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.BridgeOperationFormalizeOnMSG_SMWizard;
import com.mentor.nucleus.bp.core.ui.BridgeOperationFormalizeOnMSG_SMWizardPage2;
import com.mentor.nucleus.bp.core.ui.ClassEventFormalizeOnMSG_AMWizard;
import com.mentor.nucleus.bp.core.ui.ClassEventFormalizeOnMSG_AMWizardPage2;
import com.mentor.nucleus.bp.core.ui.ClassOperationFormalizeOnMSG_SMWizard;
import com.mentor.nucleus.bp.core.ui.ClassOperationFormalizeOnMSG_SMWizardPage2;
import com.mentor.nucleus.bp.core.ui.InstanceClassEventFormalizeOnMSG_AMWizard;
import com.mentor.nucleus.bp.core.ui.InstanceClassEventFormalizeOnMSG_AMWizardPage3;
import com.mentor.nucleus.bp.core.ui.InstanceEventFormalizeOnMSG_AMWizard;
import com.mentor.nucleus.bp.core.ui.InstanceEventFormalizeOnMSG_AMWizardPage2;
import com.mentor.nucleus.bp.core.ui.InstanceOperationFormalizeOnMSG_SMWizard;
import com.mentor.nucleus.bp.core.ui.InstanceOperationFormalizeOnMSG_SMWizardPage2;
import com.mentor.nucleus.bp.core.ui.PackageFunctionFormalizeOnMSG_SMWizard;
import com.mentor.nucleus.bp.core.ui.PackageFunctionFormalizeOnMSG_SMWizardPage3;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.actions.FormalizeOnSQ_PPAction;
import com.mentor.nucleus.bp.core.ui.actions.GenericPackageFormalizeOnSQ_CIPAction;
import com.mentor.nucleus.bp.core.ui.actions.GenericPackageFormalizeOnSQ_CPAction;
import com.mentor.nucleus.bp.core.ui.actions.GenericPackageFormalizeOnSQ_EEPAction;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.FailableRunnable;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTestUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

/**
 * Contains tests that exercise the functionality of sequence diagrams.
 */
public class SequenceTestsGenerics extends CanvasTest {
	private String test_id;

	private boolean generateResults = false;

	public SequenceTestsGenerics(String arg0) {
		super(null, arg0);

	}

	/**
	 * Whether the first test of this class is the one that's currently being
	 * run.
	 */
	private static boolean firstTest = true;
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

		//m_sys = getSystemModel("Default Project");

		// if it's the first test of this class that's being setup
		if (firstTest) {
			if (!initialized) {
				loadProject("SequenceTestModel");
				initialized = true;
			}
			firstTest = false;

			workbench = PlatformUI.getWorkbench();
		}
	}

	/**
	 * Tests that formal sequence modeling is drawn correctly
	 */
	public void testFormalSequenceModeling() {
		test_id = "1";
		String diagramName = "Formal SQ In SS";
		Package_c formalSequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(formalSequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");
		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that informal sequence modeling is drawn correctly as well as
	 * sequences in domains
	 */
	public void testInformalSequenceModeling() {
		test_id = "2";
		final String diagramName = "Informal SQ In Domain";
		Package_c informalSequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(informalSequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");
		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that sequences can be drawn in sequences
	 */
	public void testSequenceInSequenceModeling() {
		test_id = "3";
		final String diagramName = "SQ In SQ";
		Package_c sis = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sis);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");
		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that an instance can be formalized with a nested class
	 */
	public void testFormalizeInstanceWithNestedClass() {
		test_id = "4";
		// open the canvas editor to test on
		String diagramName = "Informal SQ In Domain";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to formalize
		ClassInstanceParticipant_c instance = ClassInstanceParticipant_c
				.ClassInstanceParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ClassInstanceParticipant_c selected = (ClassInstanceParticipant_c) candidate;
								return selected.getName().equals(
										"Informal Inst");
							}

						});
		assertNotNull(instance);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(instance);
		// before calling the action setup a thread that will
		// configure the necessary values
		FailableRunnable runnable = TestUtil.chooseItemInDialog(200, "Nested Class A");
		TestUtil.okElementSelectionDialog(runnable);
		// get the action and execute it
		GenericPackageFormalizeOnSQ_CIPAction action = new GenericPackageFormalizeOnSQ_CIPAction();
		action.run(null);
		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that a formalized instance can be formalized again
	 */
	public void testFormalizeFormalizedInstance() {
		test_id = "5";
		// open the canvas editor to test on
		String diagramName = "Informal SQ In Domain";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to formalize
		ClassInstanceParticipant_c instance = ClassInstanceParticipant_c
				.ClassInstanceParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ClassInstanceParticipant_c selected = (ClassInstanceParticipant_c) candidate;
								return selected.getName().equals(
										"Informal Inst");
							}

						});
		assertNotNull(instance);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(instance);
		// before calling the action setup a thread that will
		// configure the necessary values
		FailableRunnable runnable = TestUtil.chooseItemInDialog(200, "Nested Subclass");
		TestUtil.okElementSelectionDialog(runnable);
		// get the action and execute it
		GenericPackageFormalizeOnSQ_CIPAction action = new GenericPackageFormalizeOnSQ_CIPAction();
		action.run(null);
		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that a when a formalized instances is unformalized the previous
	 * informal attribute values are restored
	 */
	public void testUnformalizeInstanceRestoresInformalAVs() {
		test_id = "6";
		// open the canvas editor to test on
		String diagramName = "Informal SQ In Domain";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to formalize
		ClassInstanceParticipant_c instance = ClassInstanceParticipant_c
				.ClassInstanceParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ClassInstanceParticipant_c selected = (ClassInstanceParticipant_c) candidate;
								return selected.getName().equals(
										"Informal Inst");
							}

						});
		assertNotNull(instance);

		instance.Unformalize();

		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that an imported class can be formalized with a nested class
	 */
	public void testFormalizeImportedClassWithNestedClass() {
		test_id = "7";
		// open the canvas editor to test on
		String diagramName = "Informal SQ In Domain";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the imported class to formalize
		ClassParticipant_c importedClass = ClassParticipant_c
				.ClassParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ClassParticipant_c selected = (ClassParticipant_c) candidate;
								return selected.getInformalname().equals(
										"Informal Class A");
							}

						});
		assertNotNull(importedClass);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(importedClass);
		// before calling the action setup a thread that will
		// configure the necessary values
		FailableRunnable runnable = TestUtil.chooseItemInDialog(200, "Nested Class A");
		TestUtil.okElementSelectionDialog(runnable);
		// get the action and execute it
		GenericPackageFormalizeOnSQ_CPAction action = new GenericPackageFormalizeOnSQ_CPAction();
		action.run(null);
		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that an imported class can be formalized with a nested class
	 */
	public void testFormalizeFormalizedImportedClassWithClass() {
		test_id = "8";
		// open the canvas editor to test on
		String diagramName = "Informal SQ In Domain";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the imported class to formalize
		ClassParticipant_c importedClass = ClassParticipant_c
				.ClassParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ClassParticipant_c selected = (ClassParticipant_c) candidate;
								return selected.getInformalname().equals(
										"Informal Class A");
							}

						});
		assertNotNull(importedClass);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(importedClass);
		// before calling the action setup a thread that will
		// configure the necessary values
		FailableRunnable runnable = TestUtil.chooseItemInDialog(200, "Class A");
		TestUtil.okElementSelectionDialog(runnable);
		// get the action and execute it
		GenericPackageFormalizeOnSQ_CPAction action = new GenericPackageFormalizeOnSQ_CPAction();
		action.run(null);
		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that when an imported class is unformalized the informal attributes
	 * are restored
	 */
	public void testUnformalizeImportedClassRestoresInformalAttributes() {
		test_id = "9";
		// open the canvas editor to test on
		String diagramName = "Informal SQ In Domain";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the imported class to unformalize
		ClassParticipant_c importedClass = ClassParticipant_c
				.ClassParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ClassParticipant_c selected = (ClassParticipant_c) candidate;
								return selected.getInformalname().equals(
										"Informal Class A");
							}

						});
		assertNotNull(importedClass);

		importedClass.Unformalize();

		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that an imported function package may be formalized with a nested
	 * function package
	 */
	public void testFormalizeFunctionPackage() {
		test_id = "10";
		// open the canvas editor to test on
		String diagramName = "Informal SQ In Domain";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to formalize
		PackageParticipant_c fpkg = PackageParticipant_c
				.PackageParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								PackageParticipant_c selected = (PackageParticipant_c) candidate;
								return selected.getInformalname().equals(
										"Informal Function Package A");
							}

						});
		assertNotNull(fpkg);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(fpkg);
		// before calling the action setup a thread that will
		// configure the necessary values
		FailableRunnable runnable = TestUtil.chooseItemInDialog(200, "Nested Function PKG");
		TestUtil.okElementSelectionDialog(runnable);
		// get the action and execute it
		FormalizeOnSQ_PPAction action = new FormalizeOnSQ_PPAction();
		action.run(null);
		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that an formalized imported function package may be formalized with
	 * a function package
	 */
	public void testFormalizeFormalizedFunctionPackage() {
		test_id = "11";
		// open the canvas editor to test on
		String diagramName = "Informal SQ In Domain";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to formalize
		PackageParticipant_c fpkg = PackageParticipant_c
				.PackageParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								PackageParticipant_c selected = (PackageParticipant_c) candidate;
								return selected.getInformalname().equals(
										"Informal Function Package A");
							}

						});
		assertNotNull(fpkg);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(fpkg);
		// before calling the action setup a thread that will
		// configure the necessary values
		FailableRunnable runnable = TestUtil.chooseItemInDialog(200, "Function PKG");
		TestUtil.okElementSelectionDialog(runnable);
		// get the action and execute it
		FormalizeOnSQ_PPAction action = new FormalizeOnSQ_PPAction();
		action.run(null);
		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that an imported ee may be formalized with a nested ee
	 */
	public void testFormalizeEE() {
		test_id = "12";
		// open the canvas editor to test on
		String diagramName = "Informal SQ In Domain";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to formalize
		ExternalEntityParticipant_c ee = ExternalEntityParticipant_c
				.ExternalEntityParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ExternalEntityParticipant_c selected = (ExternalEntityParticipant_c) candidate;
								return selected.getInformalname().equals(
										"Informal External Entity A");
							}

						});
		assertNotNull(ee);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(ee);
		// before calling the action setup a thread that will
		// configure the necessary values
		FailableRunnable runnable = TestUtil.chooseItemInDialog(200, "Nested EE A");
		TestUtil.okElementSelectionDialog(runnable);
		// get the action and execute it
		GenericPackageFormalizeOnSQ_EEPAction action = new GenericPackageFormalizeOnSQ_EEPAction();
		action.run(null);
		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that an imported ee may be formalized with a nested ee
	 */
	public void testFormalizeFormalizedEE() {
		test_id = "13";
		// open the canvas editor to test on
		String diagramName = "Informal SQ In Domain";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to formalize
		ExternalEntityParticipant_c ee = ExternalEntityParticipant_c
				.ExternalEntityParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ExternalEntityParticipant_c selected = (ExternalEntityParticipant_c) candidate;
								return selected.getInformalname().equals(
										"Informal External Entity A");
							}

						});
		assertNotNull(ee);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(ee);
		// before calling the action setup a thread that will
		// configure the necessary values
		FailableRunnable runnable = TestUtil.chooseItemInDialog(200, "EE A");
		TestUtil.okElementSelectionDialog(runnable);
		// get the action and execute it
		GenericPackageFormalizeOnSQ_EEPAction action = new GenericPackageFormalizeOnSQ_EEPAction();
		action.run(null);
		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that a synchronous message may be formalized against a Class Based
	 * operation
	 */
	public void testFormalizeSynchronousMessageCB() {
		test_id = "14";
		// open the canvas editor to test on
		String diagramName = "Formal SQ In SS";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to formalize
		SynchronousMessage_c message = SynchronousMessage_c
				.SynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								SynchronousMessage_c selected = (SynchronousMessage_c) candidate;
								return selected.getInformalname().equals(
										"Informal Synchronous Message A");
							}

						});
		assertNotNull(message);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(message);
		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();
		// initialize the wizard
		ClassOperationFormalizeOnMSG_SMWizard wizard = new ClassOperationFormalizeOnMSG_SMWizard();
		wizard.init(workbench, sel, null);
		WizardDialog dialog = new WizardDialog(workbench
				.getActiveWorkbenchWindow().getShell(), wizard);
		dialog.create();
		// initialize the pages
		ClassOperationFormalizeOnMSG_SMWizardPage2 page1 = (ClassOperationFormalizeOnMSG_SMWizardPage2) wizard
				.getStartingPage();
		page1.createControl(workbench.getActiveWorkbenchWindow().getShell());
		Combo combo = page1.MessageCombo;
		selectItemInList("CBOperation", combo);
		assertTrue("Class Based Operation: " + "CBOperation, "
				+ "was not selected in the wizard.", wizard.canFinish());
		assertTrue("Could not formalize message with class based operation.",
				wizard.performFinish());

		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that a formalized synchronous message may be formalized against a
	 * Class Based operation
	 */
	public void testFormalizeFormalizedSynchronousMessageCB() {
		test_id = "15";
		// open the canvas editor to test on
		String diagramName = "Formal SQ In SS";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to formalize
		SynchronousMessage_c message = SynchronousMessage_c
				.SynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								SynchronousMessage_c selected = (SynchronousMessage_c) candidate;
								return selected.getInformalname().equals(
										"Informal Synchronous Message A");
							}

						});
		assertNotNull(message);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(message);
		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();
		// initialize the wizard
		ClassOperationFormalizeOnMSG_SMWizard wizard = new ClassOperationFormalizeOnMSG_SMWizard();
		wizard.init(workbench, sel, null);
		WizardDialog dialog = new WizardDialog(workbench
				.getActiveWorkbenchWindow().getShell(), wizard);
		dialog.create();
		// initialize the pages
		ClassOperationFormalizeOnMSG_SMWizardPage2 page1 = (ClassOperationFormalizeOnMSG_SMWizardPage2) wizard
				.getStartingPage();
		page1.createControl(workbench.getActiveWorkbenchWindow().getShell());
		Combo combo = page1.MessageCombo;
		selectItemInList("CBOperationOther", combo);
		assertTrue("Class Based Operation: " + "CBOperationOther, "
				+ "was not selected in the wizard.", wizard.canFinish());
		assertTrue("Could not formalize message with class based operation.",
				wizard.performFinish());

		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that a synchronous message may be formalized against an Instance
	 * Based operation
	 */
	public void testFormalizeSynchronousMessageIB() {
		test_id = "16";
		// open the canvas editor to test on
		String diagramName = "Formal SQ In SS";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to formalize
		SynchronousMessage_c message = SynchronousMessage_c
				.SynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								SynchronousMessage_c selected = (SynchronousMessage_c) candidate;
								return selected.getInformalname().equals(
										"Informal Synchronous Message B");
							}

						});
		assertNotNull(message);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(message);
		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();
		// initialize the wizard
		InstanceOperationFormalizeOnMSG_SMWizard wizard = new InstanceOperationFormalizeOnMSG_SMWizard();
		wizard.init(workbench, sel, null);
		WizardDialog dialog = new WizardDialog(workbench
				.getActiveWorkbenchWindow().getShell(), wizard);
		dialog.create();
		// initialize the pages
		InstanceOperationFormalizeOnMSG_SMWizardPage2 page1 = (InstanceOperationFormalizeOnMSG_SMWizardPage2) wizard
				.getStartingPage();
		page1.createControl(workbench.getActiveWorkbenchWindow().getShell());
		Combo combo = page1.MessageCombo;
		selectItemInList("Operation", combo);
		assertTrue("Instance Based Operation: " + "Operation, "
				+ "was not selected in the wizard.", wizard.canFinish());
		assertTrue(
				"Could not formalize message with instance based operation.",
				wizard.performFinish());

		validateOrGenerateResults(ce, generateResults);

	}

	/**
	 * Tests that a synchronous message may be formalized against a function
	 */
	public void testFormalizeSynchronousMessageWithFunction() {
		test_id = "17";
		// open the canvas editor to test on
		String diagramName = "Informal SQ In Domain";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to formalize
		SynchronousMessage_c message = SynchronousMessage_c
				.SynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								SynchronousMessage_c selected = (SynchronousMessage_c) candidate;
								return selected.getInformalname().equals(
										"Informal Synchronous Message C");
							}

						});
		assertNotNull(message);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(message);
		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();
		// initialize the wizard
		PackageFunctionFormalizeOnMSG_SMWizard wizard = new PackageFunctionFormalizeOnMSG_SMWizard();
		wizard.init(workbench, sel, null);
		WizardDialog dialog = new WizardDialog(workbench
				.getActiveWorkbenchWindow().getShell(), wizard);
		dialog.create();
		// initialize the pages
		PackageFunctionFormalizeOnMSG_SMWizardPage3 page1 = (PackageFunctionFormalizeOnMSG_SMWizardPage3) wizard
				.getStartingPage();
		page1.createControl(workbench.getActiveWorkbenchWindow().getShell());
		Combo combo = page1.MessageCombo;
		selectItemInList("NestedFunctionA", combo);
		assertTrue("Function: " + "NestedFunctionA, "
				+ "was not selected in the wizard.", wizard.canFinish());
		assertTrue("Could not formalize message with function.", wizard
				.performFinish());

		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that a synchronous message may be formalized against a bridge
	 * operation
	 */
	public void testFormalizeSynchronousMessageWithBridgeOperation() {
		test_id = "18";
		// open the canvas editor to test on
		String diagramName = "Informal SQ In Domain";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to formalize
		SynchronousMessage_c message = SynchronousMessage_c
				.SynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								SynchronousMessage_c selected = (SynchronousMessage_c) candidate;
								return selected.getInformalname().equals(
										"Informal Synchronous Message D");
							}

						});
		assertNotNull(message);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(message);
		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();
		// initialize the wizard
		BridgeOperationFormalizeOnMSG_SMWizard wizard = new BridgeOperationFormalizeOnMSG_SMWizard();
		wizard.init(workbench, sel, null);
		WizardDialog dialog = new WizardDialog(workbench
				.getActiveWorkbenchWindow().getShell(), wizard);
		dialog.create();
		// initialize the pages
		BridgeOperationFormalizeOnMSG_SMWizardPage2 page1 = (BridgeOperationFormalizeOnMSG_SMWizardPage2) wizard
				.getStartingPage();
		page1.createControl(workbench.getActiveWorkbenchWindow().getShell());
		Combo combo = page1.MessageCombo;
		selectItemInList("NestedBridgeOperation", combo);
		assertTrue("Bridge Operation: " + "NestedBridgeOperation, "
				+ "was not selected in the wizard.", wizard.canFinish());
		assertTrue("Could not formalize message with bridge operation.", wizard
				.performFinish());

		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that an asynchronous message may be formalized against a class
	 * based event
	 */
	public void testFormalizeAsynchronousMessageWithCBEvent() {
		test_id = "19";
		// open the canvas editor to test on
		String diagramName = "Formal SQ In SS";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to formalize
		AsynchronousMessage_c message = AsynchronousMessage_c
				.AsynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								AsynchronousMessage_c selected = (AsynchronousMessage_c) candidate;
								return selected.getInformalname().equals(
										"Informal Asynchronous Message A");
							}

						});
		assertNotNull(message);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(message);
		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();
		// initialize the wizard
		ClassEventFormalizeOnMSG_AMWizard wizard = new ClassEventFormalizeOnMSG_AMWizard();
		wizard.init(workbench, sel, null);
		WizardDialog dialog = new WizardDialog(workbench
				.getActiveWorkbenchWindow().getShell(), wizard);
		dialog.create();
		// initialize the pages
		ClassEventFormalizeOnMSG_AMWizardPage2 page1 = (ClassEventFormalizeOnMSG_AMWizardPage2) wizard
				.getStartingPage();
		page1.createControl(workbench.getActiveWorkbenchWindow().getShell());
		Combo combo = page1.MessageCombo;
		selectItemInList("SS_CA_A1: CB Event A", combo);
		assertTrue("Event: " + "SS_CA_A1: CB Event A, "
				+ "was not selected in the wizard.", wizard.canFinish());
		assertTrue("Could not formalize message with class based event.",
				wizard.performFinish());

		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that an asynchronous message may be formalized against a creation
	 * event
	 */
	public void testFormalizeAsynchronousMessageWithCreationEvent() {
		test_id = "20";
		// open the canvas editor to test on
		String diagramName = "Informal SQ In Domain";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to formalize
		AsynchronousMessage_c message = AsynchronousMessage_c
				.AsynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								AsynchronousMessage_c selected = (AsynchronousMessage_c) candidate;
								return selected.getInformalname().equals(
										"Informal Asynchronous Message B");
							}

						});
		assertNotNull(message);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(message);
		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();
		// initialize the wizard
		InstanceClassEventFormalizeOnMSG_AMWizard wizard = new InstanceClassEventFormalizeOnMSG_AMWizard();
		wizard.init(workbench, sel, null);
		WizardDialog dialog = new WizardDialog(workbench
				.getActiveWorkbenchWindow().getShell(), wizard);
		dialog.create();
		// initialize the pages
		InstanceClassEventFormalizeOnMSG_AMWizardPage3 page1 = (InstanceClassEventFormalizeOnMSG_AMWizardPage3) wizard
				.getStartingPage();
		page1.createControl(workbench.getActiveWorkbenchWindow().getShell());
		Combo combo = page1.MessageCombo;
		selectItemInList("N_SC1: Nested Creation Event", combo);
		assertTrue("Event: " + "N_SC1: Nested Creation Event, "
				+ "was not selected in the wizard.", wizard.canFinish());
		assertTrue("Could not formalize message with creation event.", wizard
				.performFinish());

		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that a formalized asynchronous message may be formalized against a
	 * class based event
	 */
	public void testFormalizeFormalizedAsynchronousMessageWithCBEvent() {
		test_id = "21";
		// open the canvas editor to test on
		String diagramName = "Formal SQ In SS";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to formalize
		AsynchronousMessage_c message = AsynchronousMessage_c
				.AsynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								AsynchronousMessage_c selected = (AsynchronousMessage_c) candidate;
								return selected.getInformalname().equals(
										"Informal Asynchronous Message A");
							}

						});
		assertNotNull(message);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(message);
		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();
		// initialize the wizard
		ClassEventFormalizeOnMSG_AMWizard wizard = new ClassEventFormalizeOnMSG_AMWizard();
		wizard.init(workbench, sel, null);
		WizardDialog dialog = new WizardDialog(workbench
				.getActiveWorkbenchWindow().getShell(), wizard);
		dialog.create();
		// initialize the pages
		ClassEventFormalizeOnMSG_AMWizardPage2 page1 = (ClassEventFormalizeOnMSG_AMWizardPage2) wizard
				.getStartingPage();
		page1.createControl(workbench.getActiveWorkbenchWindow().getShell());
		Combo combo = page1.MessageCombo;
		selectItemInList("SS_CA_A2: CB Event Other", combo);
		assertTrue("Event: " + "SS_CA_A2: CB Event Other, "
				+ "was not selected in the wizard.", wizard.canFinish());
		assertTrue("Could not formalize message with class based event.",
				wizard.performFinish());

		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that an asynchronous message may be formalized against an instance
	 * based event, as well as an inherited event
	 */
	public void testFormalizeAsynchronousMessageWithIBEvent() {
		test_id = "22";
		// open the canvas editor to test on
		String diagramName = "Informal SQ In Domain";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to formalize
		AsynchronousMessage_c message = AsynchronousMessage_c
				.AsynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								AsynchronousMessage_c selected = (AsynchronousMessage_c) candidate;
								return selected.getInformalname().equals(
										"Informal Asynchronous Message C");
							}

						});
		assertNotNull(message);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(message);
		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();
		// initialize the wizard
		InstanceEventFormalizeOnMSG_AMWizard wizard = new InstanceEventFormalizeOnMSG_AMWizard();
		wizard.init(workbench, sel, null);
		WizardDialog dialog = new WizardDialog(workbench
				.getActiveWorkbenchWindow().getShell(), wizard);
		dialog.create();
		// initialize the pages
		InstanceEventFormalizeOnMSG_AMWizardPage2 page1 = (InstanceEventFormalizeOnMSG_AMWizardPage2) wizard
				.getStartingPage();
		page1.createControl(workbench.getActiveWorkbenchWindow().getShell());
		Combo combo = page1.MessageCombo;
		selectItemInList("N_CA2: Nested Inherited Event", combo);
		assertTrue("Event: " + "N_CA2: Nested Inherited Event, "
				+ "was not selected in the wizard.", wizard.canFinish());
		assertTrue("Could not formalize message with instance based event.",
				wizard.performFinish());

		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests unformalizing a Package
	 */
	public void testUnformalizeFunctionPackage() {
		test_id = "23";
		// open the canvas editor to test on
		String diagramName = "Formal SQ In SS";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to unformalize
		PackageParticipant_c fpkg = PackageParticipant_c
				.PackageParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								PackageParticipant_c selected = (PackageParticipant_c) candidate;
								return selected.getInformalname().equals(
										"Informal Package");
							}

						});
		assertNotNull(fpkg);

		fpkg.Unformalize();

		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests unformalizing an ee
	 */
	public void testUnformalizeEE() {
		test_id = "24";
		// open the canvas editor to test on
		String diagramName = "Formal SQ In SS";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to unformalize
		ExternalEntityParticipant_c ee = ExternalEntityParticipant_c
				.ExternalEntityParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ExternalEntityParticipant_c selected = (ExternalEntityParticipant_c) candidate;
								return selected.getInformalname().equals(
										"Informal External Entity");
							}

						});
		assertNotNull(ee);

		ee.Unformalize();

		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests unformalizing a synchronous message
	 */
	public void testUnformalizeSynchronousMessage() {
		test_id = "25";
		// open the canvas editor to test on
		String diagramName = "Formal SQ In SS";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to unformalize
		SynchronousMessage_c message = SynchronousMessage_c
				.SynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								SynchronousMessage_c selected = (SynchronousMessage_c) candidate;
								return selected.getLabel().equals(
										"RecursiveOperation");
							}

						});
		assertNotNull(message);

		message.Unformalize();

		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests unformalizing a asynchronous message
	 */
	public void testUnformalizeAsynchronousMessage() {
		test_id = "26";
		// open the canvas editor to test on
		String diagramName = "Formal SQ In SS";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// select the instance to unformalize
		AsynchronousMessage_c message = AsynchronousMessage_c
				.AsynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								AsynchronousMessage_c selected = (AsynchronousMessage_c) candidate;
								return selected.getLabel().equals(
										"SS_CA1: Recursive Event");
							}

						});
		assertNotNull(message);

		message.Unformalize();

		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that changes made to elements that are used to formalize sequence
	 * elements are reflected in the Package Diagram
	 * 
	 */
	public void testFormalizedInstanceElementChangesAreReflected() {
		// open the canvas editor to test on
		String diagramName = "SQ Reflected Changes";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		test_id = "27";

		// test that adding an attribute adds instance attribute
		// value
		ClassInstanceParticipant_c instance = ClassInstanceParticipant_c
				.ClassInstanceParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ClassInstanceParticipant_c selected = (ClassInstanceParticipant_c) candidate;
								return selected.getName().equals("Inst");
							}

						});

		assertNotNull(instance);

		ModelClass_c mclass = ModelClass_c.getOneO_OBJOnR934(instance);
		mclass.Newattribute();

		validateOrGenerateResults(ce, generateResults);

		test_id = "28";

		// test that deleting an attribute removes the
		// corresponding attribute value
		Attribute_c attribute = Attribute_c.getOneO_ATTROnR102(mclass,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Attribute_c selected = (Attribute_c) candidate;
						return selected.getName().equals("Unnamed Attribute");
					}

				});

		assertNotNull(attribute);

		attribute.Dispose();

		validateOrGenerateResults(ce, generateResults);

		// tests that adding an operation parameter adds
		// a corresponding message arguments
		test_id = "29";

		Operation_c operation = Operation_c.OperationInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Operation_c selected = (Operation_c) candidate;
						return selected.getName().equals("ClassBCBOp");
					}

				});

		assertNotNull(operation);

		operation.Newparameter();

		validateOrGenerateResults(ce, generateResults);

		// tests that removing an operation paramter removes
		// corresponding message argument
		test_id = "30";

		OperationParameter_c parameter = OperationParameter_c
				.getOneO_TPARMOnR117(operation);
		assertNotNull(parameter);

		parameter.Dispose();

		validateOrGenerateResults(ce, generateResults);

		// tests that deleting an operation
		// unformalizes a corresponding message
		test_id = "31";

		operation.Dispose();

		validateOrGenerateResults(ce, generateResults);

		// tests that adding a data item creates a
		// corresponding message argument
		test_id = "32";

		StateMachineEvent_c event = StateMachineEvent_c
				.StateMachineEventInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								StateMachineEvent_c selected = (StateMachineEvent_c) candidate;
								return selected.getMning()
										.equals("ClassBEvent");
							}

						});

		assertNotNull(event);

		StateMachine_c machine = StateMachine_c.getOneSM_SMOnR502(event);
		event.Newparameter();
		StateMachineEventDataItem_c dataItem = StateMachineEventDataItem_c
				.getOneSM_EVTDIOnR516(machine);

		validateOrGenerateResults(ce, generateResults);

		// tests that removing a data item
		// removes the corresponding message
		// argument
		test_id = "33";

		dataItem.Dispose();

		validateOrGenerateResults(ce, generateResults);

		// tests that deleting an event unformalizes
		// a corresponding message
		test_id = "34";

		event.Dispose();

		validateOrGenerateResults(ce, generateResults);

	}

	/**
	 * Tests that changes made to elements that are used to formalize sequence
	 * elements are reflected in the Package Diagram
	 * 
	 */
	public void testFormalizedEEElementChangesAreReflected() {
		// open the canvas editor to test on
		String diagramName = "SQ Reflected Changes";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// tests that adding a parameter to a bridge
		// creates a corresponding message argument
		test_id = "35";

		Bridge_c bridge = Bridge_c.BridgeInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Bridge_c selected = (Bridge_c) candidate;
						return selected.getName().equals("EEBOperation");
					}

				});

		assertNotNull(bridge);

		bridge.Newparameter();

		validateOrGenerateResults(ce, generateResults);

		// tests that deleting a bridge parameter
		// removes the corresponding message argument
		test_id = "36";

		BridgeParameter_c parameter = BridgeParameter_c
				.getOneS_BPARMOnR21(bridge);
		assertNotNull(parameter);

		parameter.Dispose();

		validateOrGenerateResults(ce, generateResults);

		// test that deleting a bridge unformalizes
		// a corresponding message
		test_id = "37";

		bridge.Dispose();

		validateOrGenerateResults(ce, generateResults);

	}

	/**
	 * Tests that changes made to elements that are used to formalize sequence
	 * elements are reflected in the Package Diagram
	 * 
	 */
	public void testFormalizedFPElementChangesAreReflected() {
		// open the canvas editor to test on
		String diagramName = "SQ Reflected Changes";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// tests that adding a parameter to a function
		// creates a corresponding message argument
		test_id = "38";

		Function_c function = Function_c.FunctionInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Function_c selected = (Function_c) candidate;
						return selected.getName().equals("FunctionB");
					}

				});

		assertNotNull(function);

		function.Newparameter();

		validateOrGenerateResults(ce, generateResults);

		// tests that deleting a function parameter
		// removes the corresponding message argument
		test_id = "39";
		FunctionParameter_c parameter = FunctionParameter_c
				.getOneS_SPARMOnR24(function);
		assertNotNull(parameter);

		parameter.Dispose();

		validateOrGenerateResults(ce, generateResults);

		// tests that deleting a function unformalizes
		// corresponding message
		test_id = "40";

		function.Dispose();

		validateOrGenerateResults(ce, generateResults);

	}

	/**
	 * Tests that rename and delete are disabled for message arguments that are
	 * formalized
	 * 
	 */
	public void testFormalMessageArgumentCannotDeleteRename() {
		// select any formal message argument
		MessageArgument_c argument = MessageArgument_c.MessageArgumentInstance(
				modelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						MessageArgument_c selected = (MessageArgument_c) candidate;
						return selected.getIsformal();
					}

				});

		assertNotNull(argument);

		assertFalse("Formal argument could be deleted.", argument.Candelete());
		assertFalse("Formal argument could be renamed.", argument.Canrename());
	}

	/**
	 * Tests that rename and delete are disabled for attribute values that are
	 * formalized
	 * 
	 */
	public void testFormalAttributeValueCannotDeleteRename() {
		// create a formal attribute value
		ClassInstanceParticipant_c cip = new ClassInstanceParticipant_c(
				modelRoot);
		InteractionParticipant_c part = new InteractionParticipant_c(modelRoot);
		part.relateAcrossR930To(cip);
		InstanceAttributeValue_c value = new InstanceAttributeValue_c(modelRoot);
		cip.relateAcrossR937To(value);

		assertFalse("Formal attribute value could be deleted.", value
				.Candelete());
		assertFalse("Formal attribute value could be renamed.", value
				.Canrename());
	}

	/**
	 * Tests that unformalize menu item is not available when elements are not
	 * formal
	 */
	public void testUnformalizeDisabled() {
		// test that an informal instance does not have the menu item
		ClassInstanceParticipant_c instance = ClassInstanceParticipant_c
				.ClassInstanceParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ClassInstanceParticipant_c selected = (ClassInstanceParticipant_c) candidate;
								return selected.getName().equals(
										"UnformalizeDisabledTest");
							}

						});

		assertNotNull(instance);

		assertFalse("Unformalize menu item was present.", instance
				.Actionfilter("can", "unform"));

		// test that an informal class participant does not have the menu item
		ClassParticipant_c cp = ClassParticipant_c.ClassParticipantInstance(
				modelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						ClassParticipant_c selected = (ClassParticipant_c) candidate;
						return selected.getInformalname().equals(
								"UnformalizeDisabledTest");
					}

				});

		assertNotNull(cp);

		assertFalse("Unformalize menu item was present.", cp.Actionfilter(
				"can", "unform"));

		// test that an informal ee does not have the menu item
		ExternalEntityParticipant_c eep = ExternalEntityParticipant_c
				.ExternalEntityParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ExternalEntityParticipant_c selected = (ExternalEntityParticipant_c) candidate;
								return selected.getInformalname().equals(
										"UnformalizeDisabledTest");
							}

						});

		assertNotNull(eep);

		assertFalse("Unformalize menu item was present.", eep.Actionfilter(
				"can", "unform"));

		// test that an informal fpp does not have the menu item
		PackageParticipant_c fpp = PackageParticipant_c
				.PackageParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								PackageParticipant_c selected = (PackageParticipant_c) candidate;
								return selected.getInformalname().equals(
										"UnformalizeDisabledTest");
							}

						});

		assertNotNull(fpp);

		assertFalse("Unformalize menu item was present.", fpp.Actionfilter(
				"can", "unform"));

		// test that an informal synchronous message does not have the menu item
		SynchronousMessage_c smessage = SynchronousMessage_c
				.SynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								SynchronousMessage_c selected = (SynchronousMessage_c) candidate;
								return selected.getLabel().equals(
										"UnformalizeDisabledTest");
							}

						});

		assertNotNull(smessage);

		assertFalse("Unformalize menu item was present.", smessage
				.Actionfilter("can", "unform"));

		// test that an informal asynchronous message does not have the menu
		// item
		AsynchronousMessage_c amessage = AsynchronousMessage_c
				.AsynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								AsynchronousMessage_c selected = (AsynchronousMessage_c) candidate;
								return selected.getLabel().equals(
										"UnformalizeDisabledTest");
							}

						});

		assertNotNull(amessage);

		assertFalse("Unformalize menu item was present.", amessage
				.Actionfilter("can", "unform"));
	}

	/**
	 * Tests that Attribute values do not have the label populated in the text
	 * editor used for renaming elements
	 * 
	 */
	public void testRenamingAttributeValueRepresentedByLabel() {
		InstanceAttributeValue_c value = InstanceAttributeValue_c
				.InstanceAttributeValueInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								InstanceAttributeValue_c selected = (InstanceAttributeValue_c) candidate;
								return (!selected.getValue().equals("") && (!selected
										.getIsformal()));
							}

						});
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(value);

		try {
			CanvasTestUtilities.updateTreeItem(value, "NewName");
		} catch (Exception e) {
			CoreTestPlugin.logError("Unable to update tree item: "
					+ value.getLabel(), e);
		}

		assertTrue("Editor text included label for attribute value.", value
				.getInformalname().indexOf("=") == -1);

	}

	/**
	 * Tests that sequences are shown correctly in subsystems
	 */
	public void testSequenceInSubsystemModeling() {
		test_id = "41";
		final String diagramName = "SS A";
		Package_c subsystem = Package_c.PackageInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Package_c selected = (Package_c) candidate;
						return selected.getName().equals(diagramName);
					}

				});
		CanvasUtilities.openCanvasEditor(subsystem);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");
		validateOrGenerateResults(ce, generateResults);
	}

	/**
	 * Tests that sequences are shown correctly in subsystems
	 */
	public void testSequenceInDomainModeling() {
		test_id = "42";
		final String diagramName = "SequenceTestModel";
		Package_c domain = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c(diagramName));
		CanvasUtilities.openCanvasEditor(domain);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");
		validateOrGenerateResults(ce, generateResults);
	}

	/**

	 * Tests that a nested sequence element can be formalized

	 */

	public void testFormalizationOfNestedSequenceElement() {
		ClassInstanceParticipant_c cip = ClassInstanceParticipant_c
				.ClassInstanceParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {
							public boolean evaluate(Object candidate) {
								return ((ClassInstanceParticipant_c) candidate)
										.getName().equals("SQinSQ Inst");
							}
						});
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(cip);
		// before calling the action setup a thread that will
		// configure the necessary values
		FailableRunnable runnable = TestUtil.chooseItemInDialog(200, "Nested Class A");
		TestUtil.okElementSelectionDialog(runnable);
		// get the action and execute it
		GenericPackageFormalizeOnSQ_CIPAction action = new GenericPackageFormalizeOnSQ_CIPAction();
		action.run(null);
	}

	/**
	 * Test creating sequences under the system level
	 */
	public void testCreateInformalSequenceUnderSystem() {
		test_id = "43";
		CanvasUtilities.openCanvasEditor(m_sys);
		GraphicalEditor activeEditor = (GraphicalEditor) UITestingUtilities
				.getActiveEditor();
		assertNotNull(activeEditor);
		createShapeInDiagram(activeEditor, new Point(300, 300), "Package");
		Package_c seq = Package_c.getOneEP_PKGOnR1401(m_sys,
				new Package_by_name_c("Unnamed Package"));
		assertNotNull("Sequence could not be created under the system.", seq);
		graphicsModelRoot = (Ooaofgraphics) activeEditor.getModel()
				.getModelRoot();
		if (!BaseTest.testGlobals){
			validateOrGenerateResultsGenerics(activeEditor, generateResults, false);
		}
		// create lifespans and component part
		test_id = "44";
		CanvasUtilities.openCanvasEditor(seq);
		activeEditor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		assertNotNull(activeEditor);
		createLifespansAndComponentParticipant(activeEditor);
		graphicsModelRoot = (Ooaofgraphics) activeEditor.getModel()
				.getModelRoot();
		validateOrGenerateResultsGenerics(activeEditor, generateResults, false);
	}

	/**
	 * Test creating sequence under component package
	 */

	//	public void testCreateInformalSequenceUnderComponentPackage() {
	//		test_id = "45";
	//		CanvasTestUtilities.openCanvasEditor(m_sys);
	//		GraphicalEditor activeEditor = (GraphicalEditor) UITestingUtilities
	//				.getActiveEditor();
	//		assertNotNull(activeEditor);
	//		createShapeInDiagram(activeEditor, new Point(600, 300),
	//				"Component Package Diagram");
	//		ComponentPackage_c cp = ComponentPackage_c.getOneCP_CPOnR4602(m_sys);
	//		assertNotNull("Unable to create test component package.", cp);
	//		CanvasTestUtilities.openCanvasEditor(cp);
	//		activeEditor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
	//		assertNotNull(activeEditor);
	//		createShapeInDiagram(activeEditor, new Point(300, 300),
	//				"Package Diagram");
	//		Package_c seq = Package_c.getOneEP_PKGOnR1401(m_sys);
	//		assertNotNull("Sequence could not be created under component package.",
	//				seq);
	//		graphicsModelRoot = (Ooaofgraphics) activeEditor.getModel()
	//				.getModelRoot();
	//		validateOrGenerateResults(activeEditor, generateResults);
	//	}

	/**
	 * Test creating sequence under component
	 */

	public void testCreateInformalSequenceUnderComponent() {
		resizeMainWindow();
		Display d = Display.getCurrent();
		while (d.readAndDispatch());
		test_id = "46";

		CanvasUtilities.openCanvasEditor(m_sys);
		GraphicalEditor activeEditor = (GraphicalEditor) UITestingUtilities
				.getActiveEditor();
		assertNotNull(activeEditor);
		createShapeInDiagram(activeEditor, new Point(300, 300), "Package");
		Package_c compPkg = Package_c.getOneEP_PKGOnR1401(m_sys,
				new Package_by_name_c("Unnamed Package-1"));
		assertNotNull(compPkg);
		//		graphicsModelRoot = (Ooaofgraphics) activeEditor.getModel()
		//				.getModelRoot();

		CanvasUtilities.openCanvasEditor(compPkg);
		activeEditor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		assertNotNull(activeEditor);
		createShapeInDiagram(activeEditor, new Point(300, 300), "Components",
				"Component");
		Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(compPkg));
		assertNotNull("Unable to create test component.", comp);
		CanvasUtilities.openCanvasEditor(comp);
		activeEditor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		assertNotNull(activeEditor);
		TestUtil.sleepWithDispatchOfEvents(1000);
		//activeEditor.zoomAll();
		createShapeInDiagram(activeEditor, new Point(100, 100), "Package");
		TestUtil.sleepWithDispatchOfEvents(1000);
		Package_c pkg = Package_c.getOneEP_PKGOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8003(comp));
		assertNotNull("Package could not be created under component.", pkg);
		graphicsModelRoot = (Ooaofgraphics) activeEditor.getModel()
				.getModelRoot();
		validateOrGenerateResultsGenerics(activeEditor, generateResults, false);
	}

	public void testDeleteReferredToComponent() {
		resizeMainWindow();
		Display d = Display.getCurrent();
		while (d.readAndDispatch());

		CanvasUtilities.openCanvasEditor(m_sys);
		GraphicalEditor activeEditor = (GraphicalEditor) UITestingUtilities
				.getActiveEditor();
		assertNotNull(activeEditor);
		createShapeInDiagram(activeEditor, new Point(500, 500), "Package");
		Package_c compPkg = Package_c.getOneEP_PKGOnR1401(m_sys,
				new Package_by_name_c("Unnamed Package-2"));
		assertNotNull(compPkg);

		CanvasUtilities.openCanvasEditor(compPkg);
		activeEditor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		assertNotNull(activeEditor);
		createShapeInDiagram(activeEditor, new Point(300, 300), "Components",
				"Component");
		Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(compPkg));
		assertNotNull("Unable to create test component.", comp);
		createShapeInDiagram(activeEditor, new Point(500, 500), "Interaction", "Component");
		ComponentParticipant_c part = ComponentParticipant_c
				.getOneSQ_COPOnR930(InteractionParticipant_c
						.getManySQ_PsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(compPkg)));
		part.Formalize(comp.getId());
		comp.Dispose();
		assertTrue(
				"Component Participant was incorrectly disposed along with its referred to component.",
				!part.isOrphaned());
		assertTrue(
				"Component Participant was left as formal after disposing its referred to component",
				!part.getIsformal());
	}
	
	public static void resizeMainWindow() {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
				.setSize(1200, 1024);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
				.redraw();
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
				.update();
		GraphicalViewer viewer = (GraphicalViewer) UITestingUtilities
				.getActiveEditor().getAdapter(GraphicalViewer.class);
		((AbstractGraphicalEditPart) viewer.getRootEditPart()).getFigure()
				.getUpdateManager().performValidation();
		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch());
	}

	static String[] elements = {"Component", "Instance", "Class",
			"External Entity", "Package", "Actor"};
	static void createLifespansAndComponentParticipant(
			GraphicalEditor activeEditor) {
		int x = 100;
		int y = 100;
		for (int i = 0; i < elements.length; i++) {
			createShapeInDiagram(activeEditor, CanvasTestUtils
					.convertToMouseCoor(new Point(x, y), activeEditor
							.getModel()), elements[i]);
			// zoom the contents
			activeEditor.zoomAll();
			// attach life line if sequence
			if (activeEditor.getModel().getRepresents() instanceof Package_c) {
				createConnectorInDiagram(activeEditor, CanvasTestUtils
						.convertToMouseCoor(new Point(x, y), activeEditor
								.getModel()), "Communication Line");
			}
			x = x + 300;
			y = y + 300;
		}
	}

	private static void createConnectorInDiagram(GraphicalEditor activeEditor,
			Point location, String toolName) {
		AbstractTool tool = UITestingUtilities.getTool(toolName);
		UITestingUtilities.activateTool(tool);
		Dimension size = new Dimension(50, 400);
		CanvasTestUtils.translateDimension(size, activeEditor.getModel());
		CanvasTestUtilities.doMouseMove(location.x + size.width, location.y
				+ size.width);
		CanvasTestUtilities.doMousePress(location.x + size.width, location.y
				+ size.width);
		CanvasTestUtilities.doMouseMove(location.x + size.width, location.y
				+ size.height);
		CanvasTestUtilities.doMouseRelease(location.x + size.width, location.y
				+ size.height);
		UITestingUtilities.deactivateTool(tool);
	}

	static void createShapeInDiagram(GraphicalEditor activeEditor,
			Point location, String toolName) {
		Dimension size = new Dimension(200, 200);
		CanvasTestUtils.translateDimension(size, activeEditor.getModel());
		if(toolName == "Package") {
			UITestingUtilities.createShapeInDiagram(activeEditor, new Rectangle(
					location.x, location.y, size.width, size.height), toolName);			
		} else {
			UITestingUtilities.createShapeInDiagram(activeEditor, new Rectangle(
					location.x, location.y, size.width, size.height), "Interaction", toolName);
		}
	}
	static void createShapeInDiagram(GraphicalEditor activeEditor,
			Point location, String toolSet, String toolName) {
		Dimension size = new Dimension(200, 200);
		CanvasTestUtils.translateDimension(size, activeEditor.getModel());
		UITestingUtilities.createShapeInDiagram(activeEditor, new Rectangle(
				location.x, location.y, size.width, size.height), toolSet,
				toolName);
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
	 * Returns a sequence with the given name
	 */
	private Package_c getPackage(final String name) {
		Package_c sequence = Package_c.PackageInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Package_c selected = (Package_c) candidate;
						return selected.getName().equals(name);
					}

				});
		return sequence;
	}

	protected String getResultName() {
		return "SequenceTest" + "_" + test_id;
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
