//=====================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.ui.properties.test;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.AsynchronousMessage_c;
import com.mentor.nucleus.bp.core.ClassInstanceParticipant_c;
import com.mentor.nucleus.bp.core.ClassParticipant_c;
import com.mentor.nucleus.bp.core.ExternalEntityParticipant_c;
import com.mentor.nucleus.bp.core.FunctionPackageParticipant_c;
import com.mentor.nucleus.bp.core.MessageArgument_c;
import com.mentor.nucleus.bp.core.PackageParticipant_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.SynchronousMessage_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTestUtilities;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

/**
 * Contains tests that exercise various aspects of the properties view.
 */
public class RefreshTestProp extends BaseTest {
	/**
	 * Whether the first test of this class is the one that's currently being
	 * run.
	 */
	private static boolean firstTest = true;


    public RefreshTestProp(String arg0){
        super("RefreshTest", arg0);
    }
	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		// if it's the first test of this class that's being setup
		if (firstTest) {
			ensureAvailableAndLoaded("testProp", false);
			firstTest = false;
		}
	}

	/**
	 * Tests that 'Formal Class Operation' appears in the Properties Display as
	 * a child under 'Operation'
	 * @throws PartInitException 
	 */
	public void testPropertiesFormalClassOperationMessage() throws PartInitException {
		// open the canvas editor to test on
		String diagramName = "Test SQ";
		Package_c sequence = getSequence(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		CanvasTestUtilities.getCanvasEditor(diagramName + ": Sequence Diagram");

        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		ExplorerView view = (ExplorerView) page.showView(
				"com.mentor.nucleus.bp.ui.explorer.ExplorerView", null,
				IWorkbenchPage.VIEW_CREATE);
		page.activate(view);
		
		SynchronousMessage_c SynMsg = SynchronousMessage_c
				.SynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								SynchronousMessage_c selected = (SynchronousMessage_c) candidate;
								return selected.getInformalname().equals(
										"Informal Message1");
							}
						});

		assertNotNull(SynMsg);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(SynMsg);

		boolean itemFound = findItemInTree("Formal Operation",
				"Formal Operation");

		assertTrue("Formal Class Operation not found in Properties Display.",
				itemFound);

	}

	/**
	 * Tests that 'Formal Bridge Operation' appears in the Properties Display as
	 * a child under 'Bridges'
	 * @throws PartInitException 
	 */
	public void testPropertiesFormalBridgeOperationMessage() throws PartInitException {
		// open the canvas editor to test on
		String diagramName = "Test SQ";
		Package_c sequence = getSequence(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		CanvasTestUtilities.getCanvasEditor(diagramName + ": Sequence Diagram");

        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		ExplorerView view = (ExplorerView) page.showView(
				"com.mentor.nucleus.bp.ui.explorer.ExplorerView", null,
				IWorkbenchPage.VIEW_CREATE);
		page.activate(view);
		
		SynchronousMessage_c SynMsg = SynchronousMessage_c
				.SynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								SynchronousMessage_c selected = (SynchronousMessage_c) candidate;
								return selected.getInformalname().equals(
										"Informal Message2");
							}
						});

		assertNotNull(SynMsg);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(SynMsg);

		boolean itemFound = findItemInTree("Formal Bridge Operation", "Formal Bridge Operation");

		assertTrue("Formal Bridge Operation not found in Properties Display.",
				itemFound);
	}

	/**
	 * Tests that 'Formal Event' appears in the Properties Display as a child
	 * under 'Events'
	 * @throws PartInitException 
	 */
	public void testPropertiesFormalEventMessage() throws PartInitException {
		// open the canvas editor to test on
		String diagramName = "Test SQ";
		Package_c sequence = getSequence(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		CanvasTestUtilities.getCanvasEditor(diagramName + ": Sequence Diagram");

        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		ExplorerView view = (ExplorerView) page.showView(
				"com.mentor.nucleus.bp.ui.explorer.ExplorerView", null,
				IWorkbenchPage.VIEW_CREATE);
		page.activate(view);
		
		AsynchronousMessage_c AsynMsg = AsynchronousMessage_c
				.AsynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								AsynchronousMessage_c selected = (AsynchronousMessage_c) candidate;
								return selected.getInformalname().equals(
										"Informal Message3");
							}
						});

		assertNotNull(AsynMsg);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(AsynMsg);

		boolean itemFound = findItemInTree("Formal Event", "Formal Event");

		assertTrue("Formal Event not found in Properties Display.", itemFound);
	}

	/**
	 * Tests that 'Formal Function' appears in the Properties Display as a child
	 * under 'Functions'
	 * @throws PartInitException 
	 */
	public void testPropertiesFormalFunctionMessage() throws PartInitException {
		// open the canvas editor to test on
		String diagramName = "Test SQ";
		Package_c sequence = getSequence(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		CanvasTestUtilities.getCanvasEditor(diagramName + ": Sequence Diagram");

        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		ExplorerView view = (ExplorerView) page.showView(
				"com.mentor.nucleus.bp.ui.explorer.ExplorerView", null,
				IWorkbenchPage.VIEW_CREATE);
		page.activate(view);
		
		SynchronousMessage_c SynMsg = SynchronousMessage_c
				.SynchronousMessageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								SynchronousMessage_c selected = (SynchronousMessage_c) candidate;
								return selected.getInformalname().equals(
										"Informal Message4");
							}
						});

		assertNotNull(SynMsg);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(SynMsg);

		boolean itemFound = findItemInTree("Formal Function", "Formal Function");

		assertTrue("Formal Function not found in Properties Display.",
				itemFound);
	}

	/**
	 * Tests that 'Formal Instance' appears in the Properties Display as a child
	 * under 'Classes'
	 * @throws PartInitException 
	 */
	public void testPropertiesFormalInstanceEntity() throws PartInitException {
		// open the canvas editor to test on
		String diagramName = "Test SQ";
		Package_c sequence = getSequence(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		CanvasTestUtilities.getCanvasEditor(diagramName + ": Sequence Diagram");

        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		ExplorerView view = (ExplorerView) page.showView(
				"com.mentor.nucleus.bp.ui.explorer.ExplorerView", null,
				IWorkbenchPage.VIEW_CREATE);
		page.activate(view);
		
		ClassInstanceParticipant_c ClsIns = ClassInstanceParticipant_c
				.ClassInstanceParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ClassInstanceParticipant_c selected = (ClassInstanceParticipant_c) candidate;
								return selected.getInformalclassname().equals(
										"Informal Class2");
							}
						});

		assertNotNull(ClsIns);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(ClsIns);

		boolean itemFound = findItemInTree("Class", "Formal Instance");

		assertTrue("Formal Instance not found in Properties Display.",
				itemFound);
	}

	/**
	 * Tests that 'Formal External Entity' appears in the Properties Display as
	 * a child under 'External Entities'
	 * @throws PartInitException 
	 */
	public void testPropertiesFormalExternalEntity() throws PartInitException {
		// open the canvas editor to test on
		String diagramName = "Test SQ";
		Package_c sequence = getSequence(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		CanvasTestUtilities.getCanvasEditor(diagramName + ": Sequence Diagram");

        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		ExplorerView view = (ExplorerView) page.showView(
				"com.mentor.nucleus.bp.ui.explorer.ExplorerView", null,
				IWorkbenchPage.VIEW_CREATE);
		page.activate(view);
		
		ExternalEntityParticipant_c EE = ExternalEntityParticipant_c
				.ExternalEntityParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ExternalEntityParticipant_c selected = (ExternalEntityParticipant_c) candidate;
								return selected.getInformalname().equals(
										"Informal External Entity2");
							}
						});

		assertNotNull(EE);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(EE);

		boolean itemFound = findItemInTree("External Entity",
				"Formal External Entity");

		assertTrue("Formal External Entity not found in Properties Display.",
				itemFound);
	}

	/**
	 * Tests that 'Formal Class' appears in the Properties Display as a child
	 * under 'Classes'
	 * @throws PartInitException 
	 */
	public void testPropertiesFormalClassEntity() throws PartInitException {
		// open the canvas editor to test on
		String diagramName = "Test SQ";
		Package_c sequence = getSequence(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		CanvasTestUtilities.getCanvasEditor(diagramName + ": Sequence Diagram");

        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		ExplorerView view = (ExplorerView) page.showView(
				"com.mentor.nucleus.bp.ui.explorer.ExplorerView", null,
				IWorkbenchPage.VIEW_CREATE);
		page.activate(view);
		
		ClassParticipant_c ImpCls = ClassParticipant_c
				.ClassParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ClassParticipant_c selected = (ClassParticipant_c) candidate;
								return selected.getInformalname().equals(
										"Informal Class3");
							}
						});

		assertNotNull(ImpCls);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(ImpCls);

		boolean itemFound = findItemInTree("Class", "Formal Class");

		assertTrue("Formal Class not found in Properties Display.", itemFound);
	}

	/**
	 * Tests that 'Formal Function Package' appears in the Properties Display as
	 * a child under 'Function Packages'
	 * @throws PartInitException 
	 */
	public void testPropertiesFormalFunctionPkgEntity() throws PartInitException {
		// open the canvas editor to test on
		String diagramName = "Test SQ";
		Package_c sequence = getSequence(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		CanvasTestUtilities.getCanvasEditor(diagramName + ": Sequence Diagram");

        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		ExplorerView view = (ExplorerView) page.showView(
				"com.mentor.nucleus.bp.ui.explorer.ExplorerView", null,
				IWorkbenchPage.VIEW_CREATE);
		page.activate(view);
		
		PackageParticipant_c FncPkg = PackageParticipant_c
				.PackageParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								PackageParticipant_c selected = (PackageParticipant_c) candidate;
								return selected.getInformalname().equals(
										"Informal Function Package2");
							}
						});

		assertNotNull(FncPkg);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(FncPkg);

		boolean itemFound = findItemInTree("Packages",
				"Formal Package");
		assertTrue("Formal Function Package not found in Properties Display.",
				itemFound);
	}

	/**
	 * Tests that 'Formal Class Operation Parameter' appears in the Properties
	 * Display as a child under 'Operation Parameters'
	 * @throws PartInitException 
	 */
	public void testPropertiesFormalClassOperationMessageArgument() throws PartInitException {
		// open the canvas editor to test on
		String diagramName = "Test SQ";
		Package_c sequence = getSequence(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		CanvasTestUtilities.getCanvasEditor(diagramName + ": Sequence Diagram");

        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		ExplorerView view = (ExplorerView) page.showView(
				"com.mentor.nucleus.bp.ui.explorer.ExplorerView", null,
				IWorkbenchPage.VIEW_CREATE);
		page.activate(view);
		
		MessageArgument_c MsgArg = MessageArgument_c.MessageArgumentInstance(
				modelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						MessageArgument_c selected = (MessageArgument_c) candidate;
						return selected.getInformalname().equals("Arg1");
					}
				});

		assertNotNull(MsgArg);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(MsgArg);

		boolean itemFound = findItemInTree("Operation Parameter",
				"Formal Class Operation Parameter");

		assertTrue(
				"Formal Class Operation Parameter not found in Properties Display.",
				itemFound);

	}

	/**
	 * Tests that 'Formal Bridge Operation Parameter' appears in the Properties
	 * Display as a child under 'Bridge Parameters'
	 * @throws PartInitException 
	 */
	public void testPropertiesFormalBridgeOperationMessageArgument() throws PartInitException {
		// open the canvas editor to test on
		String diagramName = "Test SQ";
		Package_c sequence = getSequence(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		CanvasTestUtilities.getCanvasEditor(diagramName + ": Sequence Diagram");

        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		ExplorerView view = (ExplorerView) page.showView(
				"com.mentor.nucleus.bp.ui.explorer.ExplorerView", null,
				IWorkbenchPage.VIEW_CREATE);
		page.activate(view);
		
		MessageArgument_c MsgArg = MessageArgument_c.MessageArgumentInstance(
				modelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						MessageArgument_c selected = (MessageArgument_c) candidate;
						return selected.getInformalname().equals("Arg2");
					}
				});

		assertNotNull(MsgArg);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(MsgArg);

		boolean itemFound = findItemInTree("Bridge Parameter",
				"Formal Bridge Operation Parameter");

		assertTrue(
				"Formal Bridge Operation Paramter not found in Properties Display.",
				itemFound);
	}

	/**
	 * Tests that 'Formal Event Data Item' appears in the Properties Display as
	 * a child under 'Event Data'
	 * @throws PartInitException 
	 */
	public void testPropertiesFormalEventMessageArgument() throws PartInitException {
		// open the canvas editor to test on
		String diagramName = "Test SQ";
		Package_c sequence = getSequence(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		CanvasTestUtilities.getCanvasEditor(diagramName + ": Sequence Diagram");

        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		ExplorerView view = (ExplorerView) page.showView(
				"com.mentor.nucleus.bp.ui.explorer.ExplorerView", null,
				IWorkbenchPage.VIEW_CREATE);
		page.activate(view);
		
		MessageArgument_c MsgArg = MessageArgument_c.MessageArgumentInstance(
				modelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						MessageArgument_c selected = (MessageArgument_c) candidate;
						return selected.getInformalname().equals("Arg3");
					}
				});

		assertNotNull(MsgArg);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(MsgArg);

		boolean itemFound = findItemInTree("Event Data",
				"Formal Event Data Item");

		assertTrue("Formal Event Data Item not found in Properties Display.",
				itemFound);
	}

	/**
	 * Tests that 'Formal Function Parameter' appears in the Properties Display
	 * as a child under 'Function Parameters'
	 * @throws PartInitException 
	 */
	public void testPropertiesFormalFunctionMessageArgument() throws PartInitException {
		// open the canvas editor to test on
		String diagramName = "Test SQ";
		Package_c sequence = getSequence(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		CanvasTestUtilities.getCanvasEditor(diagramName + ": Sequence Diagram");

        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		ExplorerView view = (ExplorerView) page.showView(
				"com.mentor.nucleus.bp.ui.explorer.ExplorerView", null,
				IWorkbenchPage.VIEW_CREATE);
		page.activate(view);
		
		MessageArgument_c MsgArg = MessageArgument_c.MessageArgumentInstance(
				modelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						MessageArgument_c selected = (MessageArgument_c) candidate;
						return selected.getInformalname().equals("Arg4");
					}
				});

		assertNotNull(MsgArg);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(MsgArg);

		boolean itemFound = findItemInTree("Function Parameter",
				"Formal Function Parameter");

		assertTrue("Formal Function Parameter not found in Properties Display.",
				itemFound);
	}

	private boolean findItemInTree(String folderName, String itemName) {
		Tree tree = UIUtil.getPropertyTree();

		boolean itemFound = false;

		for (int j = 0; j < tree.getItems().length; j++) {

			if (tree.getItems()[j].getText().equals(folderName)) {

				for (int i = 0; i < tree.getItems()[j].getItems().length; i++) {

					if (tree.getItems()[j].getItems()[i].getText().equals(
							itemName)) {
						itemFound = true;
						break;
					}
				}
			}
		}
		return itemFound;
	}

	/**
	 * Returns a sequence with the given name
	 */
	private Package_c getSequence(final String name) {
		Package_c sequence = Package_c.PackageInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Package_c selected = (Package_c) candidate;
						return selected.getName().equals(name);
					}

				});
		return sequence;
	}
}
