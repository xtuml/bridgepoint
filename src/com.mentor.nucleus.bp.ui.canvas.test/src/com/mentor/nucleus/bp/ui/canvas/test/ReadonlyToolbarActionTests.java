//=====================================================================
//
//File:      $RCSfile: ReadonlyToolbarActionTests.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 22:43:50 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.ui.canvas.test;


import com.mentor.nucleus.bp.test.common.BaseTest;

public class ReadonlyToolbarActionTests extends BaseTest {
//
//	public ReadonlyToolbarActionTests(String arg0) {
//        super("com.mentor.nucleus.bp.canvas.ui.test", arg0);
//	}
//
//    static private boolean firstTime = true;
//	public void setUp() throws Exception {
//        if (firstTime) {
//            PersistableModelComponent domain_pmc = ensureAvailableAndLoaded("Models", "ContextMenuTests", false, false);
//            setResourceToReadonly(domain_pmc);
//            firstTime = false;
//		}
//	}
//
//	public void testReadonlyToolBarActionInDomain() {
//		String name = "Domain Package Diagram";
//		Domain_c dom = Domain_c.DomainInstance(modelRoot);
//		CanvasTestUtils.openDiagramEditor(dom);
//		IEditorPart editor = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//		checkEditorToolActions(editor, name, true);
//	}
//
//	public void testReadonlyToolBarActionInDatatypePackage() {
//		String name = "Datatype Package Diagram";
//		DataTypePackage_c dpkg = DataTypePackage_c
//				.DataTypePackageInstance(modelRoot);
//		CanvasTestUtils.openDiagramEditor(dpkg);
//		IEditorPart editor = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//		checkEditorToolActions(editor, name, true);
//	}
//
//	public void testReadonlyToolBarActionInFunctionPackage() {
//		String name = "Function Package Diagram";
//		FunctionPackage_c fpkg = FunctionPackage_c
//				.FunctionPackageInstance(modelRoot);
//		CanvasTestUtils.openDiagramEditor(fpkg);
//		IEditorPart editor = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//		checkEditorToolActions(editor, name, true);
//	}
//
//	public void testReadonlyToolBarActionInEEPackage() {
//		String name = "External Entity Package Diagram";
//		ExternalEntityPackage_c epkg = ExternalEntityPackage_c
//				.ExternalEntityPackageInstance(modelRoot);
//		CanvasTestUtils.openDiagramEditor(epkg);
//		IEditorPart editor = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//		checkEditorToolActions(editor, name, true);
//	}
//
//	public void testReadonlyToolBarActionInSubsystemPackage() {
//		String name = "Subsystem Package Diagram";
//		Subsystem_c subsystem = Subsystem_c.SubsystemInstance(modelRoot);
//		CanvasTestUtils.openDiagramEditor(subsystem);
//		IEditorPart editor = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//		checkEditorToolActions(editor, name, true);
//	}
//
//	public void testReadonlyToolBarActionInInstanceStateMachine() {
//		String name = "Instance State Machine Diagram";
//		InstanceStateMachine_c ism = InstanceStateMachine_c
//				.InstanceStateMachineInstance(modelRoot);
//		CanvasTestUtils.openDiagramEditor(ism);
//		IEditorPart editor = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//		checkEditorToolActions(editor, name, true);
//	}
//
//	public void testReadonlyToolBarActionInClassStateMachine() {
//		String name = "Class State Machine Diagram";
//		ClassStateMachine_c asm = ClassStateMachine_c
//				.ClassStateMachineInstance(modelRoot);
//		CanvasTestUtils.openDiagramEditor(asm);
//		IEditorPart editor = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//		checkEditorToolActions(editor, name, true);
//	}
//
//	public void testEnabledToolBarActionAfterReadonlyChangeWithMouseMove() {
//		String name = "Class State Machine Diagram";
//		ClassStateMachine_c asm = ClassStateMachine_c
//				.ClassStateMachineInstance(modelRoot);
//		CanvasTestUtils.openDiagramEditor(asm);
//		// change model to writable
//        IFile modelFile = asm.getFile();
//		TestUtil.changeFileReadonlyStatus(false, modelFile);
//		IEditorPart editor = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//		// generate a mouse move to update the toolbar
//		CanvasTestUtils.createMouseEvent(100, 100, "MouseMove");
//		// check that the actions are set to enabled
//		checkEditorToolActions(editor, name, false);
//		TestUtil.changeFileReadonlyStatus(true, modelFile);
//		// generate a mouse move to update the toolbar
//		CanvasTestUtils.createMouseEvent(200, 200, "MouseMove");
//		// check that they return to disabled
//		checkEditorToolActions(editor, name, true);
//	}
//
//	public void testEnabledToolBarActionAfterReadonlyChangeWithMousePress() {
//		String name = "Class State Machine Diagram";
//		ClassStateMachine_c asm = ClassStateMachine_c
//				.ClassStateMachineInstance(modelRoot);
//		CanvasTestUtils.openDiagramEditor(asm);
//		IEditorPart editor = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//		// change model to writable
//        IFile modelFile = asm.getFile();
//		TestUtil.changeFileReadonlyStatus(false, modelFile);
//		// generate a mouse move to update the toolbar
//		CanvasTestUtils.createMouseEvent(100, 100, "MousePress");
//		// check that the actions are set to enabled
//		checkEditorToolActions(editor, name, false);
//		TestUtil.changeFileReadonlyStatus(true, modelFile);
//		// generate a mouse move to update the toolbar
//		CanvasTestUtils.createMouseEvent(200, 200, "MousePress");
//		// check that they return to disabled
//		checkEditorToolActions(editor, name, true);
//	}
//
//	public void testEnabledToolBarActionAfterReadonlyChangeWithMouseContextPress() {
//		String name = "Class State Machine Diagram";
//		ClassStateMachine_c asm = ClassStateMachine_c
//				.ClassStateMachineInstance(modelRoot);
//		CanvasTestUtils.openDiagramEditor(asm);
//		IEditorPart editor = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//		// change model to writable
//        IFile modelFile = asm.getFile();
//		TestUtil.changeFileReadonlyStatus(false, modelFile);
//		// generate a mouse move to update the toolbar
//		CanvasTestUtils.createMouseEvent(100, 100, "MouseContextPress");
//		// check that the actions are set to enabled
//		checkEditorToolActions(editor, name, false);
//		TestUtil.changeFileReadonlyStatus(true, modelFile);
//		// generate a mouse move to update the toolbar
//		CanvasTestUtils.createMouseEvent(200, 200, "MouseContextPress");
//		// check that they return to disabled
//		checkEditorToolActions(editor, name, true);
//	}
//
//	public void testEnabledToolBarActionAfterReadonlyChangeWithSpacePress() {
//		String name = "Class State Machine Diagram";
//		ClassStateMachine_c asm = ClassStateMachine_c
//				.ClassStateMachineInstance(modelRoot);
//		CanvasTestUtils.openDiagramEditor(asm);
//		// change model to writable
//        IFile modelFile = asm.getFile();
//		TestUtil.changeFileReadonlyStatus(false, modelFile);
//		IEditorPart editor = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//		// generate a mouse move to update the toolbar
//		CanvasTestUtils.createKeyEvent(' ');
//		// check that the actions are set to enabled
//		checkEditorToolActions(editor, name, false);
//		TestUtil.changeFileReadonlyStatus(true, modelFile);
//		// generate a mouse move to update the toolbar
//		CanvasTestUtils.createKeyEvent(' ');
//		// check that they return to disabled
//		checkEditorToolActions(editor, name, true);
//	}
//
//	public void testClearPartialShapeCreationWhenReadonly() {
//		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
//				.closeAllEditors(false);
//		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
//			;
//		FunctionPackage_c fpkg = FunctionPackage_c.FunctionPackageInstance(modelRoot);
//		IFile modelFile = fpkg.getFile();
//        TestUtil.changeFileReadonlyStatus(false, modelFile);
//		CanvasTestUtils.openDiagramEditor(fpkg);
//		ShapeTool_c shptool = CanvasTestUtils.getShapeTool(graphicsModelRoot,
//				"Function Package");
//		CanvasTestUtils.controlShapeToolState(graphicsModelRoot, shptool,
//				"activate");
//
//		// create mouse move to draw selection rectangle
//		CanvasTestUtils.createMouseEvent(100, 100, "MouseMove");
//		CanvasTestUtils.createMouseEvent(100, 100, "MouseDown");
//		CanvasTestUtils.createMouseEvent(200, 200, "MouseMove");
//		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
//			;
//
//		CanvasEditor editor = (CanvasEditor) PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//		int height_before = editor.ptCanvas.getSelrecth();
//		int width_before = editor.ptCanvas.getSelrectw();
//
//		TestUtil.changeFileReadonlyStatus(true, modelFile);
//		CanvasTestUtils.createMouseEvent(300, 300, "MouseMove");
//		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
//			;
//
//		int height_after = editor.ptCanvas.getSelrecth();
//		int width_after = editor.ptCanvas.getSelrectw();
//		assertTrue(
//				"Partially drawn shape was not cleaned up after a deactivate",
//				((height_before != height_after) && (width_before != width_after)));
//		assertTrue("Shape tool state was "
//				+ Integer.toString(shptool.get_current_state())
//				+ ", expected state 1.", (shptool.get_current_state() == 1));
//
//		CanvasTestUtils.controlShapeToolState(graphicsModelRoot, shptool,
//				"deactivate");
//	}
//
//	private void checkEditorToolActions(IEditorPart editor, String name,
//			boolean disabled) {
//		// process all pending UI events
//		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
//			;
//		// get the contributed actions
//		IActionBars actionBars = editor.getEditorSite().getActionBars();
//		IContributionItem[] items = actionBars.getToolBarManager().getItems();
//
//		// for each action check verify that it is disabled
//		for (int i = 0; i < items.length; i++) {
//			if (i > (items.length - 1)) {
//				if (disabled) {
//					assertFalse(name + "'s Toolbar Actions were not disabled",
//							items[i].isEnabled());
//				} else {
//					assertTrue(name + "'s Toolbar Actions were disabled",
//							items[i].isEnabled());
//				}
//			}
//		}
//	}
}
