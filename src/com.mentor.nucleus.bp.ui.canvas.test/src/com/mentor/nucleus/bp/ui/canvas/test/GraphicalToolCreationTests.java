//========================================================================
//
//File:      $RCSfile: GraphicalToolCreationTests.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 22:43:50 $
//
//Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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
package com.mentor.nucleus.bp.ui.canvas.test;

import org.eclipse.gef.Tool;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;
import com.mentor.nucleus.bp.ui.graphics.tools.GraphicalPanningSelectionTool;

public class GraphicalToolCreationTests extends BaseTest {

	private static Package_c testPackage;
	protected boolean checkDialogComplete;

	@Override
	protected void initialSetup() throws Exception {
		// create a test package and open the diagram
		m_sys.Newpackage();
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1405(m_sys);
		testPackage = pkgs[pkgs.length - 1];
		CanvasTestUtilities.openDiagramEditor(testPackage);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
			.toggleZoom(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().getActivePartReference());
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
	}

	/**
	 * Note this test is disabled until the rework of the initial name dialog issue
	 */
	public void testShapeCreationStickyModeUseDefaultNamesDisabled() {
		CorePlugin.getDefault().getPreferenceStore().setValue(
				BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION, false);
		AbstractTool tool = UITestingUtilities.getTool("Classes", "Class");
		UITestingUtilities.activateTool(tool);
		UITestingUtilities.ctrlDown = true;
		// assert that there are no rename dialogs
		checkAndCloseRenameDialog(false);
		// create three classes
		CanvasTestUtilities.createMouseEvent(100, 100, "MouseDown");
		CanvasTestUtilities.createMouseEvent(100, 100, "MouseUp");
		CanvasTestUtilities.createMouseEvent(500, 100, "MouseDown");
		CanvasTestUtilities.createMouseEvent(500, 100, "MouseUp");
		CanvasTestUtilities.createMouseEvent(250, 300, "MouseDown");
		CanvasTestUtilities.createMouseEvent(250, 300, "MouseUp");
		while(!checkDialogComplete) {
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		}
		checkDialogComplete = false;
		ModelClass_c[] classes = ModelClass_c
				.getManyO_OBJsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPackage));
		assertEquals("Not all elements were created", classes.length, 3);
		UITestingUtilities.ctrlDown = false;
		sendCtrlKeyEvent(false);
		Tool activeTool = ((GraphicalEditor) UITestingUtilities
				.getActiveEditor()).getDomain().getActiveTool();
		assertTrue(
				"Tool was not properly deactivated when letting go of control",
				activeTool instanceof GraphicalPanningSelectionTool);
		// test connectors
		tool = UITestingUtilities.getTool("Classes", "Association");
		UITestingUtilities.activateTool(tool);
		UITestingUtilities.ctrlDown = true;
		checkAndCloseRenameDialog(false);
		CanvasTestUtilities.createMouseEvent(150, 150, "MouseDown");
		CanvasTestUtilities.createMouseEvent(550, 150, "MouseMove");
		CanvasTestUtilities.createMouseEvent(550, 150, "MouseUp");
		CanvasTestUtilities.createMouseEvent(550, 150, "MouseDown");
		CanvasTestUtilities.createMouseEvent(300, 350, "MouseMove");
		CanvasTestUtilities.createMouseEvent(300, 350, "MouseUp");
		while(!checkDialogComplete) {
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		}
		checkDialogComplete = false;
		Association_c[] associations = Association_c
				.getManyR_RELsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPackage));
		assertEquals("Not all connectors were created", associations.length, 2);
		sendCtrlKeyEvent(false);
		UITestingUtilities.ctrlDown = false;
		activeTool = ((GraphicalEditor) UITestingUtilities
				.getActiveEditor()).getDomain().getActiveTool();
		assertTrue(
				"Tool was not properly deactivated when letting go of control",
				activeTool instanceof GraphicalPanningSelectionTool);
		// test that rename dialog is enabled again
		tool = UITestingUtilities.getTool("Classes", "Class");
		UITestingUtilities.activateTool(tool);
		checkAndCloseRenameDialog(true);
		// create a class
		CanvasTestUtilities.createMouseEvent(300, 100, "MouseDown");
		CanvasTestUtilities.createMouseEvent(300, 100, "MouseUp");
		while(!checkDialogComplete) {
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		}
		checkDialogComplete = false;
	}

	public void testShapeCreationStickyModeUseDefaultNamesEnabled() {
		testPackage.Dispose();
		m_sys.Newpackage();
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1405(m_sys);
		testPackage = pkgs[pkgs.length - 1];
		CanvasTestUtilities.openDiagramEditor(testPackage);
		CorePlugin.getDefault().getPreferenceStore().setValue(
				BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION, true);
		AbstractTool tool = UITestingUtilities.getTool("Classes", "Class");
		UITestingUtilities.activateTool(tool);
		UITestingUtilities.ctrlDown = true;
		// assert that there are no rename dialogs
		checkAndCloseRenameDialog(false);
		// create three classes
		CanvasTestUtilities.createMouseEvent(100, 100, "MouseDown");
		CanvasTestUtilities.createMouseEvent(100, 100, "MouseUp");
		CanvasTestUtilities.createMouseEvent(500, 100, "MouseDown");
		CanvasTestUtilities.createMouseEvent(500, 100, "MouseUp");
		CanvasTestUtilities.createMouseEvent(250, 300, "MouseDown");
		CanvasTestUtilities.createMouseEvent(250, 300, "MouseUp");
		while(!checkDialogComplete) {
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		}
		checkDialogComplete = false;
		ModelClass_c[] classes = ModelClass_c
				.getManyO_OBJsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPackage));
		assertEquals("Not all elements were created", classes.length, 3);
		sendCtrlKeyEvent(false);
		UITestingUtilities.ctrlDown = false;
		Tool activeTool = ((GraphicalEditor) UITestingUtilities
				.getActiveEditor()).getDomain().getActiveTool();
		assertTrue(
				"Tool was not properly deactivated when letting go of control",
				activeTool instanceof GraphicalPanningSelectionTool);
		// test connectors
		tool = UITestingUtilities.getTool("Classes", "Association");
		UITestingUtilities.activateTool(tool);
		UITestingUtilities.ctrlDown = true;
		checkAndCloseRenameDialog(false);
		CanvasTestUtilities.createMouseEvent(150, 150, "MouseDown");
		CanvasTestUtilities.createMouseEvent(550, 150, "MouseMove");
		CanvasTestUtilities.createMouseEvent(550, 150, "MouseUp");
		CanvasTestUtilities.createMouseEvent(550, 150, "MouseDown");
		CanvasTestUtilities.createMouseEvent(300, 350, "MouseMove");
		CanvasTestUtilities.createMouseEvent(300, 350, "MouseUp");
		while(!checkDialogComplete) {
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		}
		checkDialogComplete = false;
		Association_c[] associations = Association_c
				.getManyR_RELsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPackage));
		assertEquals("Not all connectors were created", associations.length, 2);
		sendCtrlKeyEvent(false);
		UITestingUtilities.ctrlDown = false;
		activeTool = ((GraphicalEditor) UITestingUtilities
				.getActiveEditor()).getDomain().getActiveTool();
		assertTrue(
				"Tool was not properly deactivated when letting go of control",
				activeTool instanceof GraphicalPanningSelectionTool);
		// test that rename dialog is enabled again
		tool = UITestingUtilities.getTool("Classes", "Class");
		UITestingUtilities.activateTool(tool);
		checkAndCloseRenameDialog(false);
		// create a class
		CanvasTestUtilities.createMouseEvent(300, 100, "MouseDown");
		CanvasTestUtilities.createMouseEvent(300, 100, "MouseUp");
		while(!checkDialogComplete) {
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		}
		checkDialogComplete = false;
	}
	
	private void checkAndCloseRenameDialog(final boolean expected) {
		PlatformUI.getWorkbench().getDisplay().timerExec(500, new Runnable() {

			@Override
			public void run() {
				try {
					Shell activeShell = PlatformUI.getWorkbench().getDisplay()
							.getActiveShell();
					if(activeShell == null) {
						if(expected) {
							fail("Rename dialog was not found");
							return;
						}
						return;
					}
					if (activeShell.getData() instanceof Dialog) {
						((Dialog) activeShell.getData()).close();
						if (!expected) {
							fail("A rename dialog was found");
						}
					} else {
						if (expected) {
							fail("Rename dialog was not found");
						}
					}
				} finally {
					checkDialogComplete = true;
				}
			}
		});
	}

	private void sendCtrlKeyEvent(boolean pressed) {
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Event ke = new Event();
		ke.keyCode = SWT.CTRL;
		ke.stateMask = SWT.CTRL;
		if(!pressed) {
			ce.getCanvas().notifyListeners(SWT.KeyUp, ke);
		} else {
			ce.getCanvas().notifyListeners(SWT.KeyDown, ke);
		}
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
	}
}
