//========================================================================
//
//File:      $RCSfile: CommunicationMessageTestsGenerics.java,v $
//Version:   $Revision: 1.9 $
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

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.AsynchronousMessage_c;
import com.mentor.nucleus.bp.core.ClassInstanceParticipant_c;
import com.mentor.nucleus.bp.core.ClassParticipant_c;
import com.mentor.nucleus.bp.core.ExternalEntityParticipant_c;
import com.mentor.nucleus.bp.core.PackageParticipant_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.SynchronousMessage_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.ClassUnformalizeOnSQ_CPAction;
import com.mentor.nucleus.bp.core.ui.CommunicationBridgeOperationFormalizeOnMSG_SMWizard;
import com.mentor.nucleus.bp.core.ui.CommunicationBridgeOperationFormalizeOnMSG_SMWizardPage4;
import com.mentor.nucleus.bp.core.ui.CommunicationClassEventFormalizeOnMSG_AMWizard;
import com.mentor.nucleus.bp.core.ui.CommunicationClassEventFormalizeOnMSG_AMWizardPage4;
import com.mentor.nucleus.bp.core.ui.CommunicationClassOperationFormalizeOnMSG_SMWizard;
import com.mentor.nucleus.bp.core.ui.CommunicationClassOperationFormalizeOnMSG_SMWizardPage4;
import com.mentor.nucleus.bp.core.ui.CommunicationInstanceEventFormalizeOnMSG_AMWizard;
import com.mentor.nucleus.bp.core.ui.CommunicationInstanceEventFormalizeOnMSG_AMWizardPage4;
import com.mentor.nucleus.bp.core.ui.CommunicationInstanceOperationFormalizeOnMSG_SMWizard;
import com.mentor.nucleus.bp.core.ui.CommunicationInstanceOperationFormalizeOnMSG_SMWizardPage4;
import com.mentor.nucleus.bp.core.ui.CommunicationPackageFunctionFormalizeOnMSG_SMWizard;
import com.mentor.nucleus.bp.core.ui.CommunicationPackageFunctionFormalizeOnMSG_SMWizardPage3;
import com.mentor.nucleus.bp.core.ui.ExternalEntityUnformalizeOnSQ_EEPAction;
import com.mentor.nucleus.bp.core.ui.InstanceUnformalizeOnSQ_CIPAction;
import com.mentor.nucleus.bp.core.ui.PackageParticipantUnformalizeOnSQ_PPAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.actions.FormalizeOnSQ_PPAction;
import com.mentor.nucleus.bp.core.ui.actions.GenericPackageFormalizeOnSQ_CIPAction;
import com.mentor.nucleus.bp.core.ui.actions.GenericPackageFormalizeOnSQ_CPAction;
import com.mentor.nucleus.bp.core.ui.actions.GenericPackageFormalizeOnSQ_EEPAction;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.FailableRunnable;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTestUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

/**
 * Contains tests that exercise the functionality of communication diagrams.
 */
public class CommunicationMessageTestsGenerics extends CanvasTest {
	private String test_id;

	private boolean generateResults = false;

	public CommunicationMessageTestsGenerics(String arg0) {
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
	 * Tests the following: a) an external entity participant can be formalized
	 * against an external entity b) a message can be formalized against a
	 * bridge operation using the multiple selection technique employed by the
	 * communication diagram c) an external entity can be unformalized and the
	 * message formalized against its operations will be unformalized
	 */
	public void testFormalizeMessageWithBridgeOperation() {
		test_id = "1";
		String diagramName = "Communication in Package";

		Package_c communication = getPackage(diagramName);

		communication = Package_c.getOneEP_PKGOnR1405(m_sys,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Package_c selected = (Package_c) candidate;
						return selected.getName().equals(
								"Communication in Package");
					}

				});

		CanvasUtilities.openCanvasEditor(communication);

		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");
		// test that an external entity participant may
		// be formalized against an external entity
		ExternalEntityParticipant_c eep = ExternalEntityParticipant_c
				.ExternalEntityParticipantInstance(
						communication.getModelRoot(),
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ExternalEntityParticipant_c selected = (ExternalEntityParticipant_c) candidate;
								return selected.getLabel().equals(
										"Informal External Entity");
							}

						});
		assertNotNull(eep);

		selection.clear();
		selection.addToSelection(eep);

		// before calling the action setup a thread that will
		// configure the necessary values
		FailableRunnable runnable = TestUtil.chooseItemInDialog(200, "Time");
		TestUtil.okElementSelectionDialog(runnable);
		// get the action and execute it
		GenericPackageFormalizeOnSQ_EEPAction action = new GenericPackageFormalizeOnSQ_EEPAction();
		action.run(null);

		validateOrGenerateResults(ce, generateResults);

		test_id = "2";

		// test formalizing the message against one of the operations
		SynchronousMessage_c synchronousMessage = getSynchronousMessage("Informal Synchronous Message");

		assertNotNull(synchronousMessage);

		selection.clear();
		selection.addToSelection(synchronousMessage);
		selection.addToSelection(eep);

		IStructuredSelection sel = Selection.getInstance().getStructuredSelection();

		// create and initialize the wizard
		CommunicationBridgeOperationFormalizeOnMSG_SMWizard wizard2 = new CommunicationBridgeOperationFormalizeOnMSG_SMWizard();
		wizard2.init(workbench, sel, null);
		WizardDialog dialog = new WizardDialog(workbench.getActiveWorkbenchWindow()
				.getShell(), wizard2);
		dialog.create();

		// Select the association in the wizard
		CommunicationBridgeOperationFormalizeOnMSG_SMWizardPage4 page3 = (CommunicationBridgeOperationFormalizeOnMSG_SMWizardPage4) wizard2
				.getStartingPage();
		page3.createControl(workbench.getActiveWorkbenchWindow().getShell());
		Combo combo = page3.MessageCombo;
		selectItemInList("current_date", combo);
		assertTrue("Bridge Operation: " + "current_date, "
				+ "was not selected in the wizard.", wizard2.performFinish());

		validateOrGenerateResults(ce, generateResults);

		// test unformalizing and external entity and the message
		// through the external entity
		test_id = "3";

		selection.clear();
		selection.addToSelection(eep);

		ExternalEntityUnformalizeOnSQ_EEPAction unformalizeAction = new ExternalEntityUnformalizeOnSQ_EEPAction();
		unformalizeAction.run(new Action() {
		});

		validateOrGenerateResults(ce, generateResults);

	}

	/**
	 * Tests the following: a) a function package participant can be formalized
	 * against a function package b) a message can be formalized against a
	 * function using the multiple selection technique employed by the
	 * communication diagram c) a function package can be unformalized and the
	 * message formalized against its functions will be unformalized
	 */
	public void testFormalizeMessageWithFunction() {
		test_id = "4";
		final String diagramName = "Communication in Package";

		Package_c communication = getPackage(diagramName);

		communication = Package_c.getOneEP_PKGOnR1405(m_sys,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Package_c selected = (Package_c) candidate;
						return selected.getName().equals(diagramName);
					}

				});
		CanvasUtilities.openCanvasEditor(communication);

		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");
		// test that an external entity participant may
		// be formalized against an external entity
		PackageParticipant_c fpp = PackageParticipant_c.PackageParticipantInstance(communication.getModelRoot(),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						PackageParticipant_c selected = (PackageParticipant_c) candidate;
						return selected.getName().equals(
								"Informal Package");
					}

				});
		assertNotNull(fpp);

		selection.clear();
		selection.addToSelection(fpp);

		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();

		// before calling the action setup a thread that will
		// configure the necessary values
		FailableRunnable runnable = TestUtil.chooseItemInDialog(200, "Function Package");
		TestUtil.okElementSelectionDialog(runnable);
		// get the action and execute it
		FormalizeOnSQ_PPAction action = new FormalizeOnSQ_PPAction();
		action.run(null);
		validateOrGenerateResults(ce, generateResults);

		test_id = "5";

		// test formalizing the message against one of the operations
		SynchronousMessage_c synchronousMessage = getSynchronousMessage("Informal Synchronous Message");

		assertNotNull(synchronousMessage);

		selection.clear();
		selection.addToSelection(synchronousMessage);
		selection.addToSelection(fpp);

		sel = Selection.getInstance().getStructuredSelection();

		// create and initialize the wizard
		CommunicationPackageFunctionFormalizeOnMSG_SMWizard wizard2 = new CommunicationPackageFunctionFormalizeOnMSG_SMWizard();
		wizard2.init(workbench, sel, null);
		WizardDialog dialog = new WizardDialog(workbench.getActiveWorkbenchWindow()
				.getShell(), wizard2);
		dialog.create();

		// Select the association in the wizard
		CommunicationPackageFunctionFormalizeOnMSG_SMWizardPage3 page2 = (CommunicationPackageFunctionFormalizeOnMSG_SMWizardPage3) wizard2
				.getStartingPage();
		page2.createControl(workbench.getActiveWorkbenchWindow().getShell());
		Combo combo = page2.MessageCombo;
		selectItemInList("function", combo);
		assertTrue("Package: " + "function, "
				+ "was not selected in the wizard.", wizard2.performFinish());

		validateOrGenerateResults(ce, generateResults);

		// test unformalizing and external entity and the message
		// through the external entity
		test_id = "6";

		selection.clear();
		selection.addToSelection(fpp);

		PackageParticipantUnformalizeOnSQ_PPAction unformalizeAction = new PackageParticipantUnformalizeOnSQ_PPAction();
		unformalizeAction.run(new Action() {
		});

		validateOrGenerateResults(ce, generateResults);

	}

	/**
	 * Tests the following: a) a class instance participant can be formalized
	 * against a class b) a message can be formalized against an instance based
	 * operation using the multiple selection technique employed by the
	 * communication diagram c) a class instance participant can be unformalized
	 * and the message formalized against its operation will be unformalized
	 */
	public void testFormalizeMessageWithInstanceBasedOperation() {
		test_id = "7";
		String diagramName = "Communication in Package";

		Package_c communication = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(communication);

		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");
		// test that an external entity participant may
		// be formalized against an external entity
		ClassInstanceParticipant_c cip = ClassInstanceParticipant_c
				.ClassInstanceParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ClassInstanceParticipant_c selected = (ClassInstanceParticipant_c) candidate;
								return selected.getName().equals("Test");
							}

						});
		assertNotNull(cip);

		selection.clear();
		selection.addToSelection(cip);

		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();

		// before calling the action setup a thread that will
		// configure the necessary values
		FailableRunnable runnable = TestUtil.chooseItemInDialog(200, "Supertype");
		TestUtil.okElementSelectionDialog(runnable);
		// get the action and execute it
		GenericPackageFormalizeOnSQ_CIPAction action = new GenericPackageFormalizeOnSQ_CIPAction();
		action.run(null);
		validateOrGenerateResults(ce, generateResults);

		test_id = "8";

		// test formalizing the message against one of the operations
		SynchronousMessage_c synchronousMessage = getSynchronousMessage("Informal Synchronous Message");

		assertNotNull(synchronousMessage);

		selection.clear();
		selection.addToSelection(synchronousMessage);
		selection.addToSelection(cip);

		sel = Selection.getInstance().getStructuredSelection();

		// create and initialize the wizard
		CommunicationInstanceOperationFormalizeOnMSG_SMWizard wizard2 = new CommunicationInstanceOperationFormalizeOnMSG_SMWizard();
		wizard2.init(workbench, sel, null);
		WizardDialog dialog = new WizardDialog(workbench.getActiveWorkbenchWindow()
				.getShell(), wizard2);
		dialog.create();

		// Select the association in the wizard
		CommunicationInstanceOperationFormalizeOnMSG_SMWizardPage4 page2 = (CommunicationInstanceOperationFormalizeOnMSG_SMWizardPage4) wizard2
				.getStartingPage();
		page2.createControl(workbench.getActiveWorkbenchWindow().getShell());
		Combo combo = page2.MessageCombo;
		selectItemInList("IBOperation", combo);
		assertTrue("Instance Based Operation: " + "IBOperation, "
				+ "was not selected in the wizard.", wizard2.performFinish());

		validateOrGenerateResults(ce, generateResults);

		// test unformalizing and external entity and the message
		// through the external entity
		test_id = "9";

		selection.clear();
		selection.addToSelection(cip);

		InstanceUnformalizeOnSQ_CIPAction unformalizeAction = new InstanceUnformalizeOnSQ_CIPAction();
		unformalizeAction.run(new Action() {
		});

		validateOrGenerateResults(ce, generateResults);

	}

	/**
	 * Tests the following: a) a class participant can be formalized against a
	 * class b) a message can be formalized against an class based operation
	 * using the multiple selection technique employed by the communication
	 * diagram c) a class participant can be unformalized and the message
	 * formalized against its operation will be unformalized
	 */
	public void testFormalizeMessageWithClassBasedOperation() {
		test_id = "10";
		String diagramName = "Communication in Package";

		Package_c communication = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(communication);

		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");
		// test that an external entity participant may
		// be formalized against an external entity
		ClassParticipant_c cp = ClassParticipant_c.ClassParticipantInstance(
				modelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						ClassParticipant_c selected = (ClassParticipant_c) candidate;
						return selected.getLabel().equals("Informal Class");
					}

				});
		assertNotNull(cp);

		selection.clear();
		selection.addToSelection(cp);

		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();

		// before calling the action setup a thread that will
		// configure the necessary values
		FailableRunnable runnable = TestUtil.chooseItemInDialog(200, "Supertype");
		TestUtil.okElementSelectionDialog(runnable);
		// get the action and execute it
		GenericPackageFormalizeOnSQ_CPAction action = new GenericPackageFormalizeOnSQ_CPAction();
		action.run(null);
		validateOrGenerateResults(ce, generateResults);

		test_id = "11";

		// test formalizing the message against one of the operations
		SynchronousMessage_c synchronousMessage = getSynchronousMessage("Informal Synchronous Message");

		assertNotNull(synchronousMessage);

		selection.clear();
		selection.addToSelection(synchronousMessage);
		selection.addToSelection(cp);

		sel = Selection.getInstance().getStructuredSelection();

		// create and initialize the wizard
		CommunicationClassOperationFormalizeOnMSG_SMWizard wizard2 = new CommunicationClassOperationFormalizeOnMSG_SMWizard();
		wizard2.init(workbench, sel, null);
		WizardDialog dialog = new WizardDialog(workbench.getActiveWorkbenchWindow()
				.getShell(), wizard2);
		dialog.create();

		// Select the association in the wizard
		CommunicationClassOperationFormalizeOnMSG_SMWizardPage4 page2 = (CommunicationClassOperationFormalizeOnMSG_SMWizardPage4) wizard2
				.getStartingPage();
		page2.createControl(workbench.getActiveWorkbenchWindow().getShell());
		Combo combo = page2.MessageCombo;
		selectItemInList("CBOperation", combo);
		assertTrue("Operation: " + "CBOperation, "
				+ "was not selected in the wizard.", wizard2.performFinish());

		validateOrGenerateResults(ce, generateResults);

		// test unformalizing and external entity and the message
		// through the external entity
		test_id = "12";

		selection.clear();
		selection.addToSelection(cp);

		ClassUnformalizeOnSQ_CPAction unformalizeAction = new ClassUnformalizeOnSQ_CPAction();
		unformalizeAction.run(new Action() {
		});

		validateOrGenerateResults(ce, generateResults);

	}

	/**
	 * Tests the following: a) a class participant can be formalized against a
	 * class b) a message can be formalized against an class based event using
	 * the multiple selection technique employed by the communication diagram c)
	 * a class participant can be unformalized and the message formalized
	 * against its operation will be unformalized
	 */
	public void testFormalizeMessageWithClassBasedEvent() {
		test_id = "13";
		String diagramName = "Communication in Package";

		Package_c communication = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(communication);

		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");
		// test that an external entity participant may
		// be formalized against an external entity
		ClassParticipant_c cp = ClassParticipant_c.ClassParticipantInstance(
				modelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						ClassParticipant_c selected = (ClassParticipant_c) candidate;
						return selected.getLabel().equals("Informal Class");
					}

				});
		assertNotNull(cp);

		selection.clear();
		selection.addToSelection(cp);

		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();

		// before calling the action setup a thread that will
		// configure the necessary values
		FailableRunnable runnable = TestUtil.chooseItemInDialog(200, "Supertype");
		TestUtil.okElementSelectionDialog(runnable);
		// get the action and execute it
		GenericPackageFormalizeOnSQ_CPAction action = new GenericPackageFormalizeOnSQ_CPAction();
		action.run(null);

		validateOrGenerateResults(ce, generateResults);

		test_id = "14";

		// test formalizing the message against one of the operations
		AsynchronousMessage_c asynchronousMessage = getAsynchronousMessage("Informal Asynchronous Message");

		assertNotNull(asynchronousMessage);

		selection.clear();
		selection.addToSelection(asynchronousMessage);
		selection.addToSelection(cp);

		sel = Selection.getInstance().getStructuredSelection();

		// create and initialize the wizard
		CommunicationClassEventFormalizeOnMSG_AMWizard wizard2 = new CommunicationClassEventFormalizeOnMSG_AMWizard();
		wizard2.init(workbench, sel, null);
		WizardDialog dialog = new WizardDialog(workbench.getActiveWorkbenchWindow()
				.getShell(), wizard2);
		dialog.create();

		// Select the association in the wizard
		CommunicationClassEventFormalizeOnMSG_AMWizardPage4 page2 = (CommunicationClassEventFormalizeOnMSG_AMWizardPage4) wizard2
				.getStartingPage();
		page2.createControl(workbench.getActiveWorkbenchWindow().getShell());
		Combo combo = page2.MessageCombo;
		selectItemInList("TC_ST_A1: CB Event", combo);
		assertTrue("Operation: " + "TC_ST_A1: CB Event, "
				+ "was not selected in the wizard.", wizard2.performFinish());

		validateOrGenerateResults(ce, generateResults);

		// test unformalizing and external entity and the message
		// through the external entity
		test_id = "15";

		selection.clear();
		selection.addToSelection(cp);

		ClassUnformalizeOnSQ_CPAction unformalizeAction = new ClassUnformalizeOnSQ_CPAction();
		unformalizeAction.run(new Action() {
		});

		validateOrGenerateResults(ce, generateResults);

	}

	/**
	 * Tests the following: a) a class instance participant can be formalized
	 * against a class b) a message can be formalized against an instance based
	 * event using the multiple selection technique employed by the
	 * communication diagram c) a class instance participant can be unformalized
	 * and the message formalized against its operation will be unformalized
	 */
	public void testFormalizeMessageWithInstanceBasedEvent() {
		test_id = "16";
		String diagramName = "Communication in Package";

		Package_c communication = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(communication);

		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");
		// test that an external entity participant may
		// be formalized against an external entity
		ClassInstanceParticipant_c cip = ClassInstanceParticipant_c
				.ClassInstanceParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ClassInstanceParticipant_c selected = (ClassInstanceParticipant_c) candidate;
								return selected.getName().equals("Test");
							}

						});
		assertNotNull(cip);

		selection.clear();
		selection.addToSelection(cip);

		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();

		// before calling the action setup a thread that will
		// configure the necessary values
		FailableRunnable runnable = TestUtil.chooseItemInDialog(200, "Supertype");
		TestUtil.okElementSelectionDialog(runnable);
		// get the action and execute it
		GenericPackageFormalizeOnSQ_CIPAction action = new GenericPackageFormalizeOnSQ_CIPAction();
		action.run(null);

		validateOrGenerateResults(ce, generateResults);

		test_id = "17";

		// test formalizing the message against one of the operations
		AsynchronousMessage_c asynchronousMessage = getAsynchronousMessage("Informal Asynchronous Message");

		assertNotNull(asynchronousMessage);

		selection.clear();
		selection.addToSelection(asynchronousMessage);
		selection.addToSelection(cip);

		sel = Selection.getInstance().getStructuredSelection();

		// create and initialize the wizard
		CommunicationInstanceEventFormalizeOnMSG_AMWizard wizard2 = new CommunicationInstanceEventFormalizeOnMSG_AMWizard();
		wizard2.init(workbench, sel, null);
		WizardDialog dialog = new WizardDialog(workbench.getActiveWorkbenchWindow()
				.getShell(), wizard2);
		dialog.create();

		// Select the association in the wizard
		CommunicationInstanceEventFormalizeOnMSG_AMWizardPage4 page2 = (CommunicationInstanceEventFormalizeOnMSG_AMWizardPage4) wizard2
				.getStartingPage();
		page2.createControl(workbench.getActiveWorkbenchWindow().getShell());
		Combo combo = page2.MessageCombo;
		selectItemInList("TC_ST1: IB Event", combo);
		assertTrue("Operation: " + "TC_ST1: IB Event, "
				+ "was not selected in the wizard.", wizard2.performFinish());

		validateOrGenerateResults(ce, generateResults);

		// test unformalizing and external entity and the message
		// through the external entity
		test_id = "18";

		selection.clear();
		selection.addToSelection(cip);

		InstanceUnformalizeOnSQ_CIPAction unformalizeAction = new InstanceUnformalizeOnSQ_CIPAction();
		unformalizeAction.run(new Action() {
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

	/**
	 * Returns a Synchronous Message with the given name
	 */
	private SynchronousMessage_c getSynchronousMessage(final String name) {
		SynchronousMessage_c message = SynchronousMessage_c
				.SynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								SynchronousMessage_c selected = (SynchronousMessage_c) candidate;
								return selected.getLabel().equals(name);
							}

						});
		return message;
	}

	/**
	 * Returns a Synchronous Message with the given name
	 */
	private AsynchronousMessage_c getAsynchronousMessage(final String name) {
		AsynchronousMessage_c message = AsynchronousMessage_c
				.AsynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								AsynchronousMessage_c selected = (AsynchronousMessage_c) candidate;
								return selected.getLabel().equals(name);
							}

						});
		return message;
	}

	protected String getResultName() {
		return "CommunicationMessageTest" + "_" + test_id;
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
