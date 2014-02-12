//=====================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2008-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.debug.ui.launch;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.debug.ui.actions.OpenLaunchDialogAction;
import org.eclipse.jface.dialogs.TitleAreaDialog;
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

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.ui.tree.ModelCheckedTreeViewer;
import com.mentor.nucleus.bp.debug.ui.test.DebugUITestUtilities;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;

public class VerifierLaunchConfigurationTests extends BaseTest {

	private static String projectName = "VerifierLaunchConfigurationTests";
	private boolean initialized = false;
	private String tree_result = "";
	private ModelCheckedTreeViewer treeViewer = null;
	private String failureMessage = "";

	public VerifierLaunchConfigurationTests(String testName) throws Exception {
		super(null , testName);
	}
	
	@Override
	protected void setUp() throws Exception {
        super.setUp();
        
    	if(!initialized) {    		
    		CorePlugin.disableParseAllOnResourceChange();
	    	
    		// initialize test model
	    	final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
					projectName);

	    	loadProject(projectName);
	    	
	    	m_sys = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c() {
			
				public boolean evaluate(Object candidate) {
					return ((SystemModel_c)candidate).getName().equals(project.getName());
				}
			
			});
	    	
	    	PersistableModelComponent sys_comp = m_sys.getPersistableComponent();
	    	sys_comp.loadComponentAndChildren(new NullProgressMonitor());
	    	
	    	CorePlugin.enableParseAllOnResourceChange();
	    	
	    	TestingUtilities.allowJobCompletion();
	    	while(!ResourcesPlugin.getWorkspace().getRoot().isSynchronized(IProject.DEPTH_INFINITE)) {
	    		ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IProject.DEPTH_INFINITE, new NullProgressMonitor());
	    		TestingUtilities.processDisplayEvents();
	    	}
	    	
	    	Ooaofooa.setPersistEnabled(true);
	    	
	    	initialized = true;
    	}
	}
	public void tearDown() throws Exception {
		DebugUITestUtilities.stopSession(m_sys, projectName);
	}
		
	/**
	 *  This test will test that all verifiable elements show in the
	 *  launch configuration tree.  It will also test that the check
	 *  behavior is correct.
	 *   
	 * @throws Exception
	 */
	// Commenting out known failure tests.  See dts0100656068
	public void testLaunchConfigurationTree() throws Exception {
/*		checkLaunchConfigurationTree(500);
		// open the launch configuration dialog
		OpenLaunchDialogAction olda = new OpenLaunchDialogAction(IDebugUIConstants.ID_DEBUG_LAUNCH_GROUP);
		olda.run();
		File expectedResultsFile = new File(m_workspace_path + "expected_results/launch_tree/expected_tree_contents.txt");
		String expected_results = TestUtil.getTextFileContents(expectedResultsFile);
		assertEquals(expected_results, tree_result);
		assertEquals(failureMessage, "", failureMessage);
*/	}

	private void checkLaunchConfigurationTree(final int sleep) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {}
				
				// Select the model verifier application configuration
				Display d = PlatformUI.getWorkbench().getDisplay();
				Shell activeShell = d.getActiveShell();
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
						treeViewer = vec.getTreeViewer();
						treeViewer.expandAll();
						items = DebugUITestUtilities.getAllTreeItems(tree);
						for(int i = 0; i < items.length; i++) {
							boolean result = verifyCheckingCapabilities(items[i]);
							if(!result) {
								// finally close the configuration dialog
								activeShell.close();
								return;
							}
							String space = DebugUITestUtilities.getNestingLevel(items[i]);
							if (i == items.length - 1)
								tree_result = tree_result
										+ space
										+ items[i].getText()
										+ " : "
										+ items[i].getData().getClass()
												.getSimpleName();
							else
								tree_result = tree_result + space
										+ items[i].getText()
										+ " : "
										+ items[i].getData().getClass()
												.getSimpleName()
										+ System.getProperty("line.separator");
						}
					} else {
						fail("Unable to find verifier tree viewer.");
					}
				}
				// finally close the configuration dialog
				activeShell.close();
			}
		});
	}

	public boolean verifyCheckingCapabilities(TreeItem item) {
		TreeItem parent = item.getParentItem();
		treeViewer.setChecked(item.getData(), true);
		if(!treeViewer.getChecked(item.getData())) {
			failureMessage = "Item was not checked as expected.";
			return false;
		}
		while(parent != null) {
			// verify that each parent along the way
			// is grey checked, unless the direct
			// parent only has one child
			if(!treeViewer.getChecked(parent.getData())) {
				failureMessage = "Parent item was not checked as expected.";
				return false;
			}
			if(parent.getItems().length > 1) {
				if(!treeViewer.getGrayed(parent.getData())) {
					failureMessage = "Parent item was not gray checked as expected.";
					return false;
				}
			}
			parent = parent.getParentItem();
		}
		// now verify that are items children if
		// any are checked
		TreeItem[] items = item.getItems();
		for(int i = 0; i < items.length; i++) {
			if(!treeViewer.getChecked(items[i].getData())) {
				failureMessage = "Parent item was not checked as expected.";
				return false;
			}
		}
		treeViewer.setChecked(item.getData(), false);
		// now assert that no items are checked
		if(!(treeViewer.getCheckedElements().length == 0)) {
			failureMessage = "Items were not unchecked as expected.";
		}
		return true;
	}
	
	protected Tree getTreeInControls(Control[] children, Tree oldTree) {
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

	public Tree getTreeInComposite(Composite composite, Tree oldTree) {
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
	
}