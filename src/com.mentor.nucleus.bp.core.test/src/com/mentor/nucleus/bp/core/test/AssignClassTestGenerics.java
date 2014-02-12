//========================================================================
//
//File:      $RCSfile: AssignClassTestGenerics.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 04:30:26 $
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
package com.mentor.nucleus.bp.core.test;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ImportedClass_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.actions.GenericPackageAssignClassOnO_IOBJAction;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;

public class AssignClassTestGenerics extends CanvasTest {

	IWorkbenchPage m_wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
			.getActivePage();
	static ExplorerView m_bp_view = null;
	static TreeViewer m_bp_tree = null;
	String test_id = null;
	private static boolean generateResults = false;
	private static boolean initialized = false;
	static String workspace_path = "";

	private static Selection selection = Selection.getInstance();

	public AssignClassTestGenerics(String name) {
		super("Default Project", name);
	}

	protected String getResultName() {
		return "AssignClass" + "_" + test_id;
	}

	protected void setUp() throws Exception {
		super.setUp();
		Display d = Display.getCurrent();
		while (d.readAndDispatch());
		if (!initialized) {
			loadProject("AssignClassTests");
			initialized = true;
		}
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void setGenerateResults() {
		try {
			generateResults = true;
			this.setUp();
			this.testAssignClassWithOneSubsystem();
			this.testCheckTableItemForAssignClass();
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

	public void testAssignClassWithOneSubsystem() {
		test_id = "1";
		ImportedClass_c ic = ImportedClass_c.ImportedClassInstance(modelRoot);
		Cl_c.Clearselection();
		Selection.getInstance().addToSelection(ic);
		GenericPackageAssignClassOnO_IOBJAction aca = new GenericPackageAssignClassOnO_IOBJAction();
		Action a = new Action() {
		};
		aca.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) Selection
				.getInstance().getSelection();
		TestUtil.okToDialog(2000);
		aca .O_IOBJ_GenericPackageAssignClass(structuredSelection);

	}
	public void testCheckTableItemForAssignClass() {
		test_id = "2";
		BaseTest.ensureFolderExists(m_workspace_path
				+ "actual_results/AssignClassTestGenerics");
		String actualResultFilePath = m_workspace_path
				+ "actual_results/AssignClassTestGenerics/TableContent1.txt";
		ImportedClass_c ic = ImportedClass_c.ImportedClassInstance(modelRoot);
		Cl_c.Clearselection();
		Selection.getInstance().addToSelection(ic);
		GenericPackageAssignClassOnO_IOBJAction aca = new GenericPackageAssignClassOnO_IOBJAction();
		Action a = new Action() {
		};
		aca.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) Selection
		.getInstance().getSelection();
		
		
	    TestUtil.checkTableItems(2000, 0, false, actualResultFilePath  );
		aca .O_IOBJ_GenericPackageAssignClass(structuredSelection);
		TestingUtilities.fileContentsCompare(m_workspace_path
				+ "expected_results/AssignClassTestGenerics/TableContent1.txt",
				actualResultFilePath);
		
	}

}