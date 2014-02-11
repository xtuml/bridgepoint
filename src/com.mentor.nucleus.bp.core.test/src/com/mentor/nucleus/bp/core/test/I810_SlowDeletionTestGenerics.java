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

package com.mentor.nucleus.bp.core.test;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

//import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;
public class I810_SlowDeletionTestGenerics extends BaseTest {
	static IFile testModel = null;
	private static boolean initialized = false;
    private String testProject = "ModifyNonFullyLoadedModelTest";
    private String testModelName = "ooaofgraphics";

	public I810_SlowDeletionTestGenerics(String projectName, String name)
			throws CoreException {
		super(projectName, name);

	}
	public I810_SlowDeletionTestGenerics(String name) throws CoreException {
		super(null, name); //$NON-NLS-1$

	}

	protected void setUp() throws Exception {
		super.setUp();
		if (!initialized) {
			/* NOTE (2013/05) - when we switched the way test models are used, we wanted
			 * here to use the actual ui.canvas project, but couldn't because the plugin 
			 * had not yet been updated to GPs.  So, for now, we use a test model from git.
			 * Later, this could be changed to use this commented out code if we wanted...
			TestingUtilities.importDevelopmentProjectIntoWorkspace("com.mentor.nucleus.bp.ui.canvas");
			m_sys = getSystemModel("com.mentor.nucleus.bp.ui.canvas");
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

	public void testSlowDeletion() {
		IWorkbenchPage page = TestUtil.showBridgePointPerspective();
		ExplorerView explorer = null;
		Display display = Display.getCurrent();
		while (display.readAndDispatch());
		Package_c[] domains = Package_c.PackageInstances(modelRoot);

		try {
			explorer = (ExplorerView) page.showView(
					"com.mentor.nucleus.bp.ui.explorer.ExplorerView", null, //$NON-NLS-1$
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
