//========================================================================
//
//File:      $RCSfile: AssignComponentTestGenerics.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 04:30:25 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.actions.GenericPackageAssignComponentOnCL_ICAction;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.ui.canvas.Cl_c;
import org.xtuml.bp.ui.canvas.test.CanvasTest;
import org.xtuml.bp.ui.explorer.ExplorerView;

@RunWith(OrderedRunner.class)
public class AssignComponentTestGenerics extends CanvasTest {

	IWorkbenchPage m_wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
			.getActivePage();
	static ExplorerView m_bp_view = null;
	static TreeViewer m_bp_tree = null;
	String test_id = null;
	private static boolean generateResults = false;
	private static boolean initialized = false;
	static String workspace_path = "";

	private static Selection selection = Selection.getInstance();

	public AssignComponentTestGenerics() {
		super("Default Project", null);
	}

	protected String getResultName() {
		return "AssignComponent" + "_" + test_id;
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		Display d = Display.getCurrent();
		while (d.readAndDispatch());
		if (!initialized) {
			loadProject("AssignComponentTests");
			initialized = true;
		}
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	public void setGenerateResults() {
		try {
			generateResults = true;
			this.setUp();
			this.testAssignComponent();
			this.testCheckTableItemForAssignComponent();
		} catch (Exception e) {
			System.out.println("Exception encountered by test result creator: "
					+ e);
		}

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
		assertNotNull(uut);
		CanvasTestUtils.openCanvasEditor(uut);
	}

	@Test
	public void testAssignComponent() {
		test_id = "1";
		ComponentReference_c ic = ComponentReference_c
				.ComponentReferenceInstance(modelRoot);
		Cl_c.Clearselection();
		Selection.getInstance().addToSelection(ic);
		GenericPackageAssignComponentOnCL_ICAction aca = new GenericPackageAssignComponentOnCL_ICAction();
		Action a = new Action() {
		};
		aca.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) Selection
				.getInstance().getSelection();
		TestUtil.okToDialog(2000);
		aca.CL_IC_GenericPackageAssignComponent(structuredSelection);

	}
	@Test
	public void testCheckTableItemForAssignComponent() {
		test_id = "2";
		BaseTest.ensureFolderExists(m_workspace_path
				+ "actual_results/AssignComponentTestGenerics");
		String actualResultFilePath = m_workspace_path
				+ "actual_results/AssignComponentTestGenerics/TableContent1.txt";
		ComponentReference_c ic = ComponentReference_c
				.ComponentReferenceInstance(modelRoot);
		Cl_c.Clearselection();
		Selection.getInstance().addToSelection(ic);
		GenericPackageAssignComponentOnCL_ICAction aca = new GenericPackageAssignComponentOnCL_ICAction();
		Action a = new Action() {
		};
		aca.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) Selection
				.getInstance().getSelection();

		TestUtil.checkTableItems(2000, 0, false, actualResultFilePath);
		aca.CL_IC_GenericPackageAssignComponent(structuredSelection);
		TestingUtilities
				.fileContentsCompare(
						m_workspace_path
								+ "expected_results/AssignComponentTestGenerics/TableContent1.txt",
						actualResultFilePath);

	}

}