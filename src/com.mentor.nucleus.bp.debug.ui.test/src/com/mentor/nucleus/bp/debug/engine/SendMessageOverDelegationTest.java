//========================================================================
//
//File:      $RCSfile: SendMessageOverDelegationTest.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 04:28:38 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.debug.engine;

import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.Launch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.perspective.BridgePointPerspective;
import com.mentor.nucleus.bp.core.ui.tree.ModelCheckedTreeViewer;
import com.mentor.nucleus.bp.debug.test.BPTestLaunchConfiguration;
import com.mentor.nucleus.bp.debug.ui.launch.BPDebugUtils;
import com.mentor.nucleus.bp.debug.ui.launch.BPLaunchDelegate;
import com.mentor.nucleus.bp.debug.ui.launch.VerifiableElementComposite;
import com.mentor.nucleus.bp.debug.ui.model.BPDebugTarget;
import com.mentor.nucleus.bp.debug.ui.test.DebugUITestUtilities;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;

public class SendMessageOverDelegationTest extends BaseTest {
	private static String projectName = "Delegation";

	static private boolean initialized = false;

	public SendMessageOverDelegationTest(String testName) throws Exception {
		super(null, testName);
	}

	@Override
	protected void setUp() throws Exception {
		if (!initialized){
			super.setUp();
			final IProject project = ResourcesPlugin.getWorkspace().getRoot()
			.getProject(projectName);

			loadProject(projectName);

			m_sys = SystemModel_c.SystemModelInstance(Ooaofooa
					.getDefaultInstance(), new ClassQueryInterface_c() {

				public boolean evaluate(Object candidate) {
					return ((SystemModel_c) candidate).getName().equals(
							project.getName());
				}

			});

			m_sys.getPersistableComponent().loadComponentAndChildren(new NullProgressMonitor());
			
			dispatchEvents(0);
			
			initialized = true;
		}
	}
	
	public void tearDown() throws Exception {
		DebugUITestUtilities.stopSession(m_sys, projectName);
	}
	
	public void testSendInterfaceOperationOverDele() {
		ModelRoot root = Ooaofooa.getInstance("/Delegation/models/Delegation/System/System.xtuml");
		Function_c testFunc = Function_c.FunctionInstance(root,
                                           new Function_by_name_c("sendOpWithoutReturn"));
		assertNotNull(testFunc);
		
		Selection.getInstance().setSelection(new StructuredSelection(m_sys));
		runVerifier();
		
		BPDebugUtils.executeElement(testFunc);

		DebugUITestUtilities.waitForExecution();

		DebugUITestUtilities.waitForBPThreads(m_sys);
		DebugUITestUtilities.waitForExecution();
		
		String expectedConsoleText = "User invoked function: sendOpWithoutReturn\r\nLogSuccess:  LevelOneDelegationComponent has consumed the messsage as expected\r\nLogSuccess:  LevelTwoDelegationComponent has consumed the messsage as expected\r\n";
		String actualConsoleText = DebugUITestUtilities.getConsoleText("null");
		assertEquals(expectedConsoleText, actualConsoleText);

	}
	
/* Test is commented until we support launch verifier with component and component reference 
	preselected */
	
//	public void testSendInterfaceOperationWithReturnOverDele() {
//		ModelRoot [] roots = Ooaofooa.getInstancesUnderSystem(projectName);
//		Function_c testFunc = Function_c.FunctionInstance(roots[3],
//				new Function_by_name_c("sendOpWithReturn"));
//		assertNotNull(testFunc);
//		
//		Selection.getInstance().setSelection(new StructuredSelection(m_sys));
//		runVerifier();
//		
//		BPDebugUtils.setSelectionInSETree(new StructuredSelection(testFunc));
//		
//		Menu menu = DebugUITestUtilities.getMenuInSETree(testFunc);
//		
//		assertTrue(
//				"The execute menu item was not available for a required operation.",
//				UITestingUtilities.checkItemStatusInContextMenu(menu,
//						"Execute", "", false));
//		
//		
//		UITestingUtilities.activateMenuItem(menu, "Execute");
//		
//		DebugUITestUtilities.waitForExecution();
//
//		DebugUITestUtilities.waitForBPThreads(m_sys);
//		DebugUITestUtilities.waitForExecution();
//		
//		String expectedConsoleText = "User invoked function: sendOpWithReturn\r\nLogSuccess:  LevelTwoDelegationComponentReference has consumed the messsage as expected\r\nLogSuccess:  Correct return value from message\r\n";
//		String actualConsoleText = DebugUITestUtilities.getConsoleText("null");
//		assertEquals(expectedConsoleText, actualConsoleText);
//		
//	}
	
	public void testSendInterfaceOperationWithReturnOverLongDele() {
		ModelRoot root = Ooaofooa.getInstance("/Delegation/models/Delegation/System/System.xtuml");
		Function_c testFunc = Function_c.FunctionInstance(root,
				new Function_by_name_c("longdelegationWithReturn"));
		assertNotNull(testFunc);
		
		Selection.getInstance().setSelection(new StructuredSelection(m_sys));
		runVerifier();
		
		BPDebugUtils.executeElement(testFunc);
		
		DebugUITestUtilities.waitForExecution();
		
		DebugUITestUtilities.waitForBPThreads(m_sys);
		DebugUITestUtilities.waitForExecution();
		
		String expectedConsoleText = "User invoked function: longdelegationWithReturn" +
				"\r\nLogSuccess:  FourOfFourDelegation has consumed the messsage as expected" +
				"\r\nLogSuccess:  Correct return value from delegated message\r\n";
		String actualConsoleText = DebugUITestUtilities.getConsoleText("null");
		assertEquals(expectedConsoleText, actualConsoleText);
		
	}

	/* Test is commented until we support launch verifier with component and component reference 
	preselected */
	
//	public void testSendInterfaceOperationWithReturnOutOfDele() {
//		ModelRoot [] roots = Ooaofooa.getInstancesUnderSystem(projectName);
//		Function_c testFunc = Function_c.FunctionInstance(roots[1],
//				new Function_by_name_c("sendMessageOut"));
//		assertNotNull(testFunc);
//		
//		Selection.getInstance().setSelection(new StructuredSelection(m_sys));
//		runVerifier();
//		
//		BPDebugUtils.setSelectionInSETree(new StructuredSelection(testFunc));
//		
//		Menu menu = DebugUITestUtilities.getMenuInSETree(testFunc);
//		
//		assertTrue(
//				"The execute menu item was not available for a required operation.",
//				UITestingUtilities.checkItemStatusInContextMenu(menu,
//						"Execute", "", false));
//		
//		
//		UITestingUtilities.activateMenuItem(menu, "Execute");
//		
//		DebugUITestUtilities.waitForExecution();
//		
//		
//		DebugUITestUtilities.waitForBPThreads(m_sys);
//		DebugUITestUtilities.waitForExecution();
//		
//		String expectedConsoleText = "User invoked function: sendOpWithReturn\r\nLogSuccess:  LevelTwoDelegationComponentReference has consumed the messsage as expected\r\nLogSuccess:  Correct return value from message\r\n";
//		String actualConsoleText = DebugUITestUtilities.getConsoleText("null");
//		assertEquals(expectedConsoleText, actualConsoleText);
//		
//	}
	
	public void testSendInterfaceOperationWithReturnOutOfLongDele() {
		ModelRoot [] roots = Ooaofooa.getInstancesUnderSystem(projectName);
		Function_c testFunc = null;
		for(int i = 0; i < roots.length; i++) {
			testFunc = Function_c.FunctionInstance(roots[i],
				new Function_by_name_c("LongDelegation"));
			if(testFunc != null) {
				break;
			}
		}
		assertNotNull(testFunc);
		
		Selection.getInstance().setSelection(new StructuredSelection(m_sys));
		runVerifier();
		
		BPDebugUtils.executeElement(testFunc);
		
		DebugUITestUtilities.waitForExecution();
		
		
		
		DebugUITestUtilities.waitForBPThreads(m_sys);
		DebugUITestUtilities.waitForExecution();
		
		String expectedConsoleText = "User invoked function: LongDelegation" +
				"\r\nLogSuccess:  TestDriverComponent has consumed the messsage as expected" +
				"\r\nLogSuccess:  Correct return value from delegated message\r\n";
		String actualConsoleText = DebugUITestUtilities.getConsoleText("null");
		assertEquals(expectedConsoleText, actualConsoleText);
		
	}
	
	public void testSendInterfaceSignalOverDele() {
		ModelRoot root = Ooaofooa.getInstance("/Delegation/models/Delegation/System/System.xtuml");
		Function_c testFunc = Function_c.FunctionInstance(root,
				new Function_by_name_c("sendSignal"));
		assertNotNull(testFunc);
		
		Selection.getInstance().setSelection(new StructuredSelection(m_sys));
		runVerifier();
		
		BPDebugUtils.executeElement(testFunc);
		
		DebugUITestUtilities.waitForExecution();
		
		
		
		DebugUITestUtilities.waitForBPThreads(m_sys);
		DebugUITestUtilities.waitForExecution();
		
		String expectedConsoleText = "User invoked function: sendSignal\r\nLogSuccess:  LevelOneDelegationComponent has consumed the messsage as expected\r\nLogSuccess:  LevelTwoDelegationComponent has consumed the messsage as expected\r\n";
		String actualConsoleText = DebugUITestUtilities.getConsoleText("null");
		assertEquals(expectedConsoleText, actualConsoleText);
		
	}

    private void runVerifier() {
    	Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(m_sys);
    	NonRootModelElement[] selectedElement = null;
    	NonRootModelElement[] compRefs = null;
    	NonRootModelElement[] components = null;
    	for (Package_c pkg : pkgs) {
			if (pkg.getName().equalsIgnoreCase("system")){
				 components = Component_c.getManyC_CsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg)); 
				assertNotNull(components);
				 compRefs = ComponentReference_c.getManyCL_ICsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg));
				assertNotNull(compRefs);
				selectedElement = new NonRootModelElement[components.length + compRefs.length];
				System.arraycopy(components, 0, selectedElement, 0, components.length);
				System.arraycopy(compRefs, 0, selectedElement, components.length, compRefs.length);
			}
		}
    	openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);    	  
    	Selection.getInstance().setSelection(new StructuredSelection(components));
    	Menu menu = m_bp_tree.getControl().getMenu();
    	assertTrue(
    			"The Launch Verifier action was not present for a component.",
    			UITestingUtilities.checkItemStatusInContextMenu(menu,
    					"Launch Verifier", "", false));
    	MenuItem launchVerifierItem = DebugUITestUtilities.getLaunchVerifierItem(menu);
    	assertNotNull(launchVerifierItem);
    	ComponentInstance_c[] engines = ComponentInstance_c.ComponentInstanceInstances(components[0].getModelRoot());
    	assertTrue("Unexpected test state, there should be no Component Instances.", engines.length == 0);
    	TestUtil.debugToDialog(200);
    	launchVerifierItem.notifyListeners(SWT.Selection, null);
    	TestingUtilities.processDisplayEvents();

    	menu = m_bp_tree.getControl().getMenu();
    	assertFalse(
    			"The Launch Verifier action was present for an unassigned imported component.",
    			UITestingUtilities.menuItemExists(menu, "", "Launch Verifier"));
  	  
	}

	private void setAndConfirmBreakpoint(NonRootModelElement st) {
		Selection.getInstance().setSelection(new StructuredSelection(st));
		ActivityEditor editor = DebugUITestUtilities
		                                .openActivityEditorForSelectedElement();
		editor.setFocus();
        DebugUITestUtilities.setBreakpointAtLine(editor, 2);
        IBreakpointManager bpm = DebugPlugin.getDefault().getBreakpointManager();
        IBreakpoint[] bps = bpm.getBreakpoints();
        assertTrue("Breakpoint not set", bps.length == 1);
	}

	private void checkLaunchConfigurationTree(final int sleep) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {}
				
				// Select the model verifier application configuration
				Display d = PlatformUI.getWorkbench().getDisplay();
				Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				while (null != activeShell && !(activeShell.getData() instanceof TitleAreaDialog)) {
					activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				}
				if(activeShell.getData() instanceof TitleAreaDialog) {
					Tree tree = getTreeInControls(activeShell.getChildren(), null);
					TreeItem[] items = tree.getItems();
					for(int i = 0; i < items.length; i++) {
						if(items[i].getText().equals("Model Verifier Application")) {
							tree.setSelection(new TreeItem[] {items[i]});
							Event event = new Event();
							event.type = SWT.Selection;
							tree.notifyListeners(SWT.Selection, event);
							Menu menu = tree.getMenu();
							MenuItem[] menuItems = UITestingUtilities.getMenuItems(menu, "");
							for(int j = 0; j < menuItems.length; j++) {
								if(menuItems[j].getText().equals("Ne&w")) {
									menuItems[j].notifyListeners(event.type, event);
									break;
								}
							}
							break;
						}
					}
					while(d.readAndDispatch());
					tree = getTreeInControls(activeShell.getChildren(), tree);
					if(tree.getParent().getParent() instanceof VerifiableElementComposite) {
						VerifiableElementComposite vec = (VerifiableElementComposite) tree.getParent().getParent();
						ModelCheckedTreeViewer treeViewer = vec.getTreeViewer();
						treeViewer.expandAll();
						items = DebugUITestUtilities.getAllTreeItems(tree);
						assertTrue(items.length == 4);
						items[3].setChecked(true);
						items[2].setChecked(true);
					} else {
						fail("Unable to find verifier tree viewer.");
					}
				}
				// finally close the configuration dialog
				activeShell.close();
			}
		});
	}

	private Tree getTreeInControls(Control[] children, Tree oldTree) {
		for(int i = 0; i < children.length; i++) {
			if(children[i] instanceof Tree && children[i] != oldTree) {
				return (Tree) children[i];
			}
		}
		// no tree found yet search for a composite
		for(int i = 0; i < children.length; i++) {
			if(children[i] instanceof Composite) {
				return getTreeInComposite((Composite) children[i], oldTree);
			}
		}
		return null;
	}

	private Tree getTreeInComposite(Composite composite, Tree oldTree) {
		Control[] children = composite.getChildren();
		for(int i = 0; i < children.length; i++) {
			if(children[i] instanceof Tree && children[i] != oldTree) {
				return (Tree) children[i];
			}
		}
		// no tree was found yet, search for a composite
		for(int i = 0; i < children.length; i++) {
			if(children[i] instanceof Composite) {
				Tree tree = getTreeInComposite((Composite) children[i], oldTree);
				if(tree != null) {
					return tree;
				}
			}
		}
		return null;
	}
	
    public BPDebugTarget startVerifier() {
    	Vector<String> modelNames = new Vector<String>();
    	modelNames.add("Signal Param Receiver");
    	modelNames.add("Signal Param Sender");
		BPTestLaunchConfiguration bplc = new BPTestLaunchConfiguration(
				              projectName, modelNames , new Vector<Ooaofooa>());
		Launch testLaunch = new Launch(bplc, "debug", null);
		BPLaunchDelegate bpld = new BPLaunchDelegate();
		try {
		  // TODO Not currently working, because BPTestLaunchConfiguration
		  // getAttribute(String, Map) only works for domains.
		  bpld.launch(bplc, "debug", testLaunch, new NullProgressMonitor());
		}
		catch (CoreException ce) {
			fail(ce.toString());
		}
		BPDebugTarget bpdbt = null; 
		while (bpdbt == null || bpdbt.isTerminated()) {
			try {
			  Thread.sleep(1000);
			}
			catch (InterruptedException ie) {
				// do nothing
			}
			bpdbt = (BPDebugTarget)testLaunch.getDebugTarget();
		}
		return bpdbt;
    }



}
