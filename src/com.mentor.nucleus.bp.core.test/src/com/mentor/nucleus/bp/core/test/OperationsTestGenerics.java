//=====================================================================
//
//File:      $RCSfile: OperationsTestGenerics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 04:30:26 $
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

package com.mentor.nucleus.bp.core.test;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.contentoutline.ContentOutline;

import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;
import com.mentor.nucleus.bp.ui.graphics.outline.GraphicalOutlinePage;

public class OperationsTestGenerics extends CoreTest {

	static ContentOutline outlineView = null;
	static TreeViewer outlineTree = null;
	private static final String HIDE_STATICS = "Hide Static Members";
	protected ViewerFilter filterStatics;
	protected ViewerFilter filterOutlineStatics;
	protected Action hideStatics;
	protected Action hideOutlineStatics;
	static boolean isDone = false;
	private static boolean initialized = false;

	public OperationsTestGenerics() {
		super();
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (!initialized) {

			loadProject("OperationsTest");
			initialized = true;

		}
	}

	protected void tearDown() throws Exception {
		if (isDone) {
			if (modelRoot != null) {
				modelRoot.clearDatabase(new NullProgressMonitor());
				initialized = false;
			}
		}
		super.tearDown();
	}

	public class ModelClass_by_name_c implements ClassQueryInterface_c {

		public boolean evaluate(Object candidate) {
			ModelClass_c selected = (ModelClass_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public ModelClass_by_name_c(String name) {
			m_name = name;
		}
		private String m_name;
	}

	public class Package_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			Package_c selected = (Package_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public Package_by_name_c(String name) {
			m_name = name;
		}
		private String m_name;
	}

	public void openTestPKGDiagram(String title) {
		Package_c uut = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c(title));
		CanvasTestUtils.openCanvasEditor(uut);
	}

	// Model Explorer view helper methods
	protected void createFilters() {
		filterStatics = new ViewerFilter() {
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				if (element instanceof Operation_c) {
					return (((Operation_c) element).getInstance_based() == 1);
				}
				if (element instanceof Function_c) {
					return false;
				}
				return true;
			}
		};
	}

	protected void createActions() {
		hideStatics = new Action(HIDE_STATICS) {
			public void run() {
				updateFilter(hideStatics);
			}
		};
		hideStatics.setChecked(true);
	}
	protected void updateFilter(Action action) {
		if (action == hideStatics) {
			if (action.isChecked()) {
				ExplorerUtil.getTreeViewer().addFilter(filterStatics);
			} else {
				ExplorerUtil.getTreeViewer().removeFilter(filterStatics);
			}
		}
	} // End Model Explorer view helper methods

	//Outline view Helper methods
	protected void createOutlineFilters() {
		filterOutlineStatics = new ViewerFilter() {
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				if (element instanceof Operation_c) {
					return (((Operation_c) element).getInstance_based() == 1);
				}
				if (element instanceof Function_c) {
					return false;
				}
				return true;
			}
		};
	}

	protected void createOutlineActions() {
		hideOutlineStatics = new Action(HIDE_STATICS) {
			public void run() {
				updateOutlineFilter(hideOutlineStatics);
			}
		};
		hideOutlineStatics.setChecked(true);
	}
	protected void updateOutlineFilter(Action action) {
		if (action == hideOutlineStatics) {
			if (action.isChecked()) {
				outlineTree.addFilter(filterOutlineStatics);
			} else {
				outlineTree.removeFilter(filterOutlineStatics);
			}
		}
	} //End Outline view Helper methods  

	public void testOperationDefaultType() {
		//test_id = "1";
		openTestPKGDiagram("TestOperations");
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("TestClass"));
		mc.Newoperation();
		Operation_c op = Operation_c.getOneO_TFROnR115(mc);
		assertTrue(op.getInstance_based() == 1);
	}

	public void testHideStaticMembersExplorerView() {
		//test_id = "2";	
		boolean instOperationIsVisible = false;
		createActions();
		createFilters();
		hideStatics.run();
		ExplorerUtil.expandAll();
		ExplorerUtil.getTreeViewer().getTree().selectAll();
		TreeItem x[] = ExplorerUtil.getTreeViewer().getTree().getSelection();
		assertNotNull("Tree is empty", x);
		for (int i = 0; i < x.length; ++i) {
			String item = x[i].getText();
			assertFalse(item.equals("classOperation1"));
			assertFalse(item.equals("classOperation2"));
			if (item.equals("instOperation"))
				instOperationIsVisible = true;
		}
		assertTrue(instOperationIsVisible);
	}

	public void testHideStaticMembersOutlineView() {
		//test_id = "2";	 
		boolean instOperationIsVisible = false;
		openTestPKGDiagram("TestOperations");
		outlineTree = getOutlinePageTreeViewer();
		createOutlineActions();
		createOutlineFilters();
		hideOutlineStatics.run();
		outlineTree.expandAll();
		outlineTree.getTree().selectAll();
		TreeItem x[] = outlineTree.getTree().getSelection();
		assertNotNull("Tree is empty", x);
		for (int i = 0; i < x.length; ++i) {
			String item = x[i].getText();
			assertFalse(item.equals("classOperation1"));
			assertFalse(item.equals("classOperation2"));
			if (item.equals("instOperation"))
				instOperationIsVisible = true;
		}
		assertTrue(instOperationIsVisible);
	}

	public TreeViewer getOutlinePageTreeViewer() {
		GraphicalOutlinePage modelContentOutline = null;
		IWorkbenchPage[] pages = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getPages();
		for (int j = 0; j < pages.length; ++j) {
			IViewReference[] views = pages[j].getViewReferences();
			for (int k = 0; k < views.length; ++k) {
				if (views[k].getTitle().equals("Outline")) {
					ContentOutline outlineView = (ContentOutline) views[k]
							.getView(true);
					modelContentOutline = (GraphicalOutlinePage) outlineView
							.getCurrentPage();
				}
			}
		}
		return modelContentOutline.getTreeViewer();
	}
}
