//========================================================================
//
//File:      $RCSfile: I810_SlowDeletionTestGenerics.java,v $
//Version:   $Revision: 1.6 $
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

package org.xtuml.bp.core.test;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.xtuml.bp.core.Domain_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.util.UIUtil;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.ui.explorer.ExplorerView;
@RunWith(OrderedRunner.class)
public class I810_SlowDeletionTestGenerics extends BaseTest {
	static IFile testModel = null;
	private static boolean initialized = false;
    private String testProject = "ModifyNonFullyLoadedModelTest";
    private String testModelName = "ooaofgraphics";

//	public I810_SlowDeletionTestGenerics(String projectName, String name)
//			throws CoreException {
//		super(projectName, name);
//
//	}
	public I810_SlowDeletionTestGenerics() throws CoreException {
		super(null, null); //$NON-NLS-1$

	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		if (!initialized) {
			/* NOTE (2013/05) - when we switched the way test models are used, we wanted
			 * here to use the actual ui.canvas project, but couldn't because the plugin 
			 * had not yet been updated to GPs.  So, for now, we use a test model from git.
			 * Later, this could be changed to use this commented out code if we wanted...
			TestingUtilities.importDevelopmentProjectIntoWorkspace("org.xtuml.bp.ui.canvas");
			m_sys = getSystemModel("org.xtuml.bp.ui.canvas");
			PersistableModelComponent domainComponent = m_sys
					.getPersistableComponent().getChild("ooaofgraphics");
			modelRoot = (Ooaofooa) domainComponent.getRootModelElement().getModelRoot();
			testModel = domainComponent.getFile();*/
			
			// load test model
    		TestingUtilities.importTestingProjectIntoWorkspace(testProject);
    		BaseTest.dispatchEvents(0);
    		m_sys = getSystemModel(testProject); 
    		Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys,
    				new ClassQueryInterface_c() {

    					@Override
    					public boolean evaluate(Object candidate) {
    						return ((Package_c) candidate).getName().equals(
    								testModelName);
    					}
    				});
    		modelRoot = (Ooaofooa) pkg.getModelRoot();
    		testModel = pkg.getFile();
    		initialized = true;
		}
	}

	@Test
	public void testSlowDeletion() {
		IWorkbenchPage page = TestUtil.showBridgePointPerspective();
		ExplorerView explorer = null;
		Display display = Display.getCurrent();
		while (display.readAndDispatch());
		Package_c[] domains = Package_c.PackageInstances(modelRoot);

		try {
			explorer = (ExplorerView) page.showView(
					"org.xtuml.bp.ui.explorer.ExplorerView", null, //$NON-NLS-1$
					IWorkbenchPage.VIEW_CREATE);
		} catch (PartInitException e) {
			fail(e.toString());
		}
		TreeViewer viewer = explorer.getTreeViewer();
		viewer.expandToLevel(2);
		UIUtil.refreshViewer(viewer);
		while (display.readAndDispatch());
		// select the only domain node that should be present
		viewer.setSelection(new StructuredSelection(new Object[]{domains[0]}),
				false);
		while (display.readAndDispatch()) {
		}

		long startTime = System.currentTimeMillis();
		TransactionManager.getSingleton().disableDialog = true;
		explorer.doDelete();
		TransactionManager.getSingleton().disableDialog = false;
		while (display.readAndDispatch()) {
		}

		long endTime = System.currentTimeMillis();
		System.out
				.println("Deletion Took : " + (float) (endTime - startTime) / 1000 + " sec");//$NON-NLS-1$//$NON-NLS-2$
	}

	private TreeItem findItem(String text, TreeItem[] items) {
		// for each of the items given 
		for (int i = 0; i < items.length; i++) {
			// if this item's text matches that given
			if (items[i].getText().equals(text)) {
				// return this item
				return items[i];
			}
		}

		return null;
	}

}
