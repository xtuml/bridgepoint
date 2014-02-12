//=====================================================================
//
//File:      $RCSfile: RemoveSignalTests.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 04:30:26 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.core.test;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ExecutableProperty_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.InterfaceSignal_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public class RemoveSignalTests extends BaseTest {
	/**
	 * The editor upon which these tests operate.
	 */
	private static GraphicalEditor editor = null;

	/**
	 * A boolean to determine whether the test shall be performed in a read only
	 * environment.
	 */
	public boolean m_readonly;

	static protected boolean first_time = true;

	/**
	 * Constructor.
	 */

	public RemoveSignalTests(String name) {
		super(null, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();

		if (first_time) {
			Ooaofooa.setPersistEnabled(true);
			CorePlugin.disableParseAllOnResourceChange();
			loadProject("ComponentContextMenuTests");
			modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(getProject().getName(), "Component Context Menu Test", true));
			// open a class diagram editor on any subsystem
			ModelClass_c clazz = ModelClass_c
					.getOneO_OBJOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000(Package_c
									.getManyEP_PKGsOnR1405(m_sys)));
			Package_c uut = Package_c.getOneEP_PKGOnR8000(PackageableElement_c
					.getOnePE_PEOnR8001(clazz));
			CanvasTestUtils.openDiagramEditor(uut);
			editor = (GraphicalEditor) UITestingUtilities.getActiveEditor();

			first_time = false;
		}
	}

	public void testRemoveSignal() {
		/*
		 * _- Find class TC's class based state machine _- Locate a transition
		 * between the states and right click on it _R The Assign Signal...
		 * entry exists _- Assign the signal to the transition _- Right click on
		 * the transition _R The Assign Signal... entry does not exist _R The
		 * Remove Signal entry does exist _R In the model explorer view the
		 * "aSignal" event is present under the CSM _- Select Remove Signal _R
		 * The Assign Signal... entry does exist _R The Remove Signal entry does
		 * not exist _R In the model explorer view the "aSignal" event is NOT
		 * present under the CSM
		 */
		StateMachineState_c state = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								return ((StateMachineState_c) candidate)
										.getName().equals("B");
							}

						});
		assertNotNull(state);

		Transition_c[] txns = Transition_c.getManySM_TXNsOnR506(state);
		assertTrue(txns.length == 2);

		Transition_c obj = txns[0];

		IFile file = obj.getFile();
		TestUtil.changeFileReadonlyStatus(m_readonly, file);

		UITestingUtilities.clearGraphicalSelection();
		editor = UITestingUtilities.addElementToGraphicalSelection(obj);

		Port_c port = Port_c.PortInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Port_c) candidate).getName().equals("Port_TC");
					}

				});
		assertNotNull(port);

		InterfaceReference_c reference = InterfaceReference_c
				.getOneC_IROnR4016(port);

		InterfaceSignal_c isig = InterfaceSignal_c.getOneC_ASOnR4004(
				ExecutableProperty_c.getOneC_EPOnR4003(Interface_c
						.getOneC_IOnR4012(InterfaceReference_c
								.getOneC_IROnR4016(port))),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((InterfaceSignal_c) candidate).getName()
								.equals("aSignal");
					}

				});
		assertNotNull(isig);
		obj.Addsignal(reference.getId(), isig.getId());

		editor = UITestingUtilities.addElementToGraphicalSelection(obj);

		// get the menu from the SWT Canvas
		Menu menu = editor.getCanvas().getMenu();

		// check the status of the action (assure the signal is assigned)
		assertFalse(UITestingUtilities.checkItemStatusInContextMenu(menu,
				"Assign Signal", "", m_readonly));
		assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu,
				"Remove Signal", "", m_readonly));

		// Make sure the signal is present in ME
		IPath stateFilePath = state.getPersistableComponent().getFile()
				.getFullPath();
		TreeItem stateTreeItem = ExplorerUtil
				.selectMEInModelExplorer(stateFilePath);
		TreeItem[] stateChildren = ExplorerUtil.getChildren(stateTreeItem);
		TreeItem sigTreeItem = ExplorerUtil.findItem("aSignal", stateChildren);
		assertTrue("The signal is present in Model Explorer",
				sigTreeItem != null);

		// Remove the signal
		obj.Removesignal();

		while (Display.getCurrent().readAndDispatch())
			;

		editor = UITestingUtilities.addElementToGraphicalSelection(obj);

		while (Display.getCurrent().readAndDispatch())
			;

		// check the status of the action (assure the signal not assigned)
		assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu,
				"Assign Signal", "", m_readonly));
		assertFalse(UITestingUtilities.checkItemStatusInContextMenu(menu,
				"Remove Signal", "", m_readonly));

		UITestingUtilities.clearGraphicalSelection();

		// Make sure the signal is not present in ME
		stateTreeItem = ExplorerUtil.selectMEInModelExplorer(stateFilePath);
		stateChildren = ExplorerUtil.getChildren(stateTreeItem);
		sigTreeItem = ExplorerUtil.findItem("aSignal", stateChildren);
		assertTrue("The signal is not present in Model Explorer",
				sigTreeItem == null);
		stateTreeItem.setExpanded(false);
		while (Display.getCurrent().readAndDispatch())
			;
	}

	public void testDeleteTransitionThatHasAnAssignedSignal() {
		/*
		 * _- Find class TC's class based state machine _- Locate a transition
		 * between the states and right click on it _R The Assign Signal...
		 * entry exists _- Assign the signal to the transition _- Right click on
		 * the transition _R The Assign Signal... entry does not exist _R The
		 * Remove Signal entry does exist _R In the model explorer view the
		 * "aSignal" event is present under the CSM _- Select Delete (delete the
		 * transition) _R In the model explorer view the "aSignal" event is NOT
		 * present under the CSM
		 */
		StateMachineState_c state = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								return ((StateMachineState_c) candidate)
										.getName().equals("B");
							}

						});
		assertNotNull(state);

		Transition_c[] txns = Transition_c.getManySM_TXNsOnR506(state);
		assertTrue(txns.length == 2);

		Transition_c obj = txns[0];

		IFile file = obj.getFile();
		TestUtil.changeFileReadonlyStatus(m_readonly, file);

		UITestingUtilities.clearGraphicalSelection();
		editor = UITestingUtilities.addElementToGraphicalSelection(obj);

		Port_c port = Port_c.PortInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Port_c) candidate).getName().equals("Port_TC");
					}

				});
		assertNotNull(port);

		InterfaceReference_c reference = InterfaceReference_c
				.getOneC_IROnR4016(port);

		InterfaceSignal_c isig = InterfaceSignal_c.getOneC_ASOnR4004(
				ExecutableProperty_c.getOneC_EPOnR4003(Interface_c
						.getOneC_IOnR4012(InterfaceReference_c
								.getOneC_IROnR4016(port))),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((InterfaceSignal_c) candidate).getName()
								.equals("aSignal");
					}

				});
		assertNotNull(isig);
		obj.Addsignal(reference.getId(), isig.getId());

		editor = UITestingUtilities.addElementToGraphicalSelection(obj);

		// get the menu from the SWT Canvas
		Menu menu = editor.getCanvas().getMenu();

		// check the status of the action (assure the signal is assigned)
		assertFalse(UITestingUtilities.checkItemStatusInContextMenu(menu,
				"Assign Signal", "", m_readonly));
		assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu,
				"Remove Signal", "", m_readonly));

		// Make sure the signal is present in ME
		IPath stateFilePath = state.getPersistableComponent().getFile()
				.getFullPath();
		TreeItem stateTreeItem = ExplorerUtil
				.selectMEInModelExplorer(stateFilePath);
		TreeItem[] stateChildren = ExplorerUtil.getChildren(stateTreeItem);
		TreeItem sigTreeItem = ExplorerUtil.findItem("aSignal", stateChildren);
		assertTrue("The signal is present in Model Explorer",
				sigTreeItem != null);

		while (Display.getCurrent().readAndDispatch())
			;

		UITestingUtilities.clearGraphicalSelection();
		editor = UITestingUtilities.addElementToGraphicalSelection(obj);
		// get the menu from the SWT Canvas
		menu = editor.getCanvas().getMenu();

		while (Display.getCurrent().readAndDispatch())
			;

		// Delete the transition
		UITestingUtilities.activateMenuItem(menu, "Delete");

		while (Display.getCurrent().readAndDispatch())
			;

		// Make sure the signal is not present in ME
		stateTreeItem = ExplorerUtil.selectMEInModelExplorer(stateFilePath);
		stateChildren = ExplorerUtil.getChildren(stateTreeItem);
		sigTreeItem = ExplorerUtil.findItem("aSignal", stateChildren);
		assertTrue("The signal is not present in Model Explorer",
				sigTreeItem == null);

		// --------------------------
		// Below is just here is setup for the next tests.
		// Our unit tests are too tightly coupled.
		// ----------------------------
		state = StateMachineState_c.StateMachineStateInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName()
								.equals("B");
					}

				});
		assertNotNull(state);

		txns = Transition_c.getManySM_TXNsOnR506(state);
		assertTrue(txns.length == 1);

		obj = txns[0];

		file = obj.getFile();
		TestUtil.changeFileReadonlyStatus(m_readonly, file);
		UITestingUtilities.clearGraphicalSelection();
		editor = UITestingUtilities.addElementToGraphicalSelection(obj);

		m_bp_tree.collapseAll();
		while (Display.getCurrent().readAndDispatch())
			;
	}
}
