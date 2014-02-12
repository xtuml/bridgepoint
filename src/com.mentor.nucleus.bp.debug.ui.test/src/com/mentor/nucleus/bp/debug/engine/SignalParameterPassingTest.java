//========================================================================
//
//File:      $RCSfile: SignalParameterPassingTest.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 22:41:58 $
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
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.IDebugUIConstants;
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

import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
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
import com.mentor.nucleus.bp.test.common.CVSUtils;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;

public class SignalParameterPassingTest extends BaseTest {
	private static String projectName = "dts0100807430";

	private boolean initialized = false;

	public SignalParameterPassingTest(String testName) throws Exception {
		super.setName(testName);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		if (!initialized) {
			CorePlugin.disableParseAllOnResourceChange();

			// set perspective switch dialog on launch
			DebugUIPlugin.getDefault().getPluginPreferences().setValue(
					IDebugUIConstants.PLUGIN_ID + ".switch_to_perspective",
			"always");

			CVSUtils.checkoutProject(projectName, true);

			// initialize test model
			final IProject project = ResourcesPlugin.getWorkspace().getRoot()
			.getProject(projectName);

			m_sys = SystemModel_c.SystemModelInstance(Ooaofooa
					.getDefaultInstance(), new ClassQueryInterface_c() {

				public boolean evaluate(Object candidate) {
					return ((SystemModel_c) candidate).getName().equals(
							project.getName());
				}

			});
			modelRoot = (Ooaofooa) m_sys.getModelRoot();

			initialized = true;
		}
	}
	
	public void tearDown() throws Exception {
		DebugUITestUtilities.stopSession(m_sys, projectName);
	}
	
	public void testSignalParameterPassing() {
		ModelRoot [] roots = Ooaofooa.getInstancesUnderSystem(projectName);
		assertTrue("Expected 2 roots found: " + roots.length,
                                                             roots.length == 2);
		ModelRoot root = null;
		for(int i = 0; i < roots.length; i++) {
			if(!roots[i].getId().contains("Datatypes")) {
				root = roots[i];
				break;
			}
		}
		Function_c testFunc = Function_c.FunctionInstance(root,
                                           new Function_by_name_c("startTest"));
		assertNotNull(testFunc);
		
		ClassStateMachine_c csm = ClassStateMachine_c.
		       ClassStateMachineInstance(root, new CSM_by_name_c("Tester"));
		
		assertNotNull(csm);
		
		StateMachineState_c testState = StateMachineState_c.
		          getOneSM_STATEOnR501(StateMachine_c.getOneSM_SMOnR517(csm),
		        		  new State_by_name_c("Testing"));
		
		assertNotNull(testState);
		setAndConfirmBreakpoint(testState);
		
		Selection.getInstance().setSelection(new StructuredSelection(m_sys));
		runVerifier();
		
		BPDebugUtils.executeElement(testFunc);
		
		DebugUITestUtilities.waitForExecution();

		Component_c[] comps = Component_c.ComponentInstances(root);
		assertTrue("Expected 2 components found: " + comps.length, comps.length == 2);
		ComponentInstance_c engine = ComponentInstance_c
				.getOneI_EXEOnR2955(comps[0]);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);
		DebugUITestUtilities.waitForExecution();

		// check that execution was suspended
		IProcess process = DebugUITestUtilities.getProcessForEngine(engine);
		assertNotNull(process);
		IDebugTarget target = process.getLaunch().getDebugTarget();
		assertTrue("Process was not suspended by breakpoint in state.", target
				.isSuspended());

		DebugUITestUtilities.processDebugEvents();
		TestingUtilities.processDisplayEvents();
		
		// verify the parameters
		// Commenting out known failure tests.  See dts0100656068
//		String xValue = DebugUITestUtilities.getValueForVariable("literalParam");
//		assertEquals(
//				"Default parameter value was not as expected for variable literalParam.",
//				"2", xValue);
//		String yValue = DebugUITestUtilities.getValueForVariable("computedParam");
//		assertEquals(
//				"Default parameter value was not as expected for variable computedParam.",
//				"5", yValue);

	}

    private void runVerifier() {
    	Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(m_sys);
    	Component_c[] components = Component_c.getManyC_CsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkgs)); 
    	  assertNotNull(components);
    	  
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
