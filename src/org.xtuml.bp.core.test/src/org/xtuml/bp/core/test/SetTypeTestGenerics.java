//========================================================================
//
//File:      $RCSfile: SetTypeTestGenerics.java,v $
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
package org.xtuml.bp.core.test;

import java.util.UUID;

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
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.StructuredDataType_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.actions.SetTypeOnO_ATTRAction;
import org.xtuml.bp.core.ui.actions.SetTypeOnS_UDTAction;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.test.common.FailableRunnable;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.ui.canvas.Cl_c;
import org.xtuml.bp.ui.canvas.test.CanvasTest;
import org.xtuml.bp.ui.explorer.ExplorerView;

@RunWith(OrderedRunner.class)
public class SetTypeTestGenerics extends CanvasTest {

	IWorkbenchPage m_wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
			.getActivePage();
	static ExplorerView m_bp_view = null;
	static TreeViewer m_bp_tree = null;
	String test_id = null;
	private static Package_c testPKG;
	private static UserDataType_c testUDT;
	private static boolean generateResults = false;
	private static boolean initialized = false;
	static String workspace_path = "";
	static String[] actualFirstColumn = null;
	static String[] actualSecondColumn = null;


	public SetTypeTestGenerics() {
		super("Delete Test", null);
	}

	protected String getResultName() {
		return "SetTypeChooserWindow" + "_" + test_id;
	}

	static boolean isFirstTime = true; 
	@Override
	@Before 
	public void initialSetup() throws Exception {
		// create the necessary elements for UDT type assignment
		if ( !isFirstTime)
			return;
		isFirstTime = false;
		m_sys.Newpackage();
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(m_sys);
		testPKG = pkgs[pkgs.length - 1];
		testPKG.setName("UDTPackage");
		testPKG.Newdatatype();
		UserDataType_c[] udts = UserDataType_c.getManyS_UDTsOnR17(DataType_c
				.getManyS_DTsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPKG)));
		testUDT = udts[udts.length - 1];
		testPKG.Newdatatype();
		udts = UserDataType_c.getManyS_UDTsOnR17(DataType_c
				.getManyS_DTsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPKG)));
		UserDataType_c otherUDT = udts[udts.length - 1];
		DataType_c supertype = DataType_c.getOneS_DTOnR17(otherUDT);
		supertype.setName("otherUDT");
		testPKG.Newenumeration();
		EnumerationDataType_c[] edts = EnumerationDataType_c
				.getManyS_EDTsOnR17(DataType_c
						.getManyS_DTsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(testPKG)));
		EnumerationDataType_c edt = edts[edts.length - 1];
		supertype = DataType_c.getOneS_DTOnR17(edt);
		supertype.setName("EDT");
		testPKG.Newstructureddatatype();
		StructuredDataType_c[] sdts = StructuredDataType_c
				.getManyS_SDTsOnR17(DataType_c
						.getManyS_DTsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(testPKG)));
		StructuredDataType_c sdt = sdts[sdts.length - 1];
		supertype = DataType_c.getOneS_DTOnR17(sdt);
		supertype.setName("SDT");
		testPKG.Newclass();
		ModelClass_c[] classes = ModelClass_c
				.getManyO_OBJsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPKG));
		ModelClass_c clazz = classes[classes.length - 1];
		clazz.setName("IRDT");
		clazz.Newinstancereferencedatatype();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		Display d = Display.getCurrent();
		while (d.readAndDispatch());
		if (!initialized) {
			loadProject("AssignClassTests");
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
			this.testSetTypeAttribute();
			this.testCheckTableItemForSetType();
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
	
	
//	@Test
//	public void testSetTypeTestGenerrics(){
//		doTestSetTypeAttribute();
//		doTestCheckTableItemForSetType();
//		doTestUDTBaseTypeAssignmentToCoreType();
//		doTestUDTBaseTypeAssignmentToUDT();
//		doTestUDTBaseTypeAssignmentToEDT();
//		doTestUDTBaseTypeAssignmentToSDT();
//		doTestUDTBaseTypeAssignmentToIRDT();
//	}

	@Test
	public void testSetTypeAttribute() {
		test_id = "1";
		Attribute_c attr = Attribute_c.AttributeInstance(modelRoot);
		Cl_c.Clearselection();
		Selection.getInstance().addToSelection(attr);
		SetTypeOnO_ATTRAction aca = new SetTypeOnO_ATTRAction();
		Action a = new Action() {
		};
		aca.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) Selection
				.getInstance().getSelection();
		TestUtil.okToDialog(2000);
		SetTypeOnO_ATTRAction.O_ATTR_SetType(structuredSelection);

	}

	@Test
	public void testCheckTableItemForSetType() {
		test_id = "2";
		BaseTest.ensureFolderExists(m_workspace_path
				+ "actual_results/SetTypeTestGenerics");
		String actualResultFilePath = m_workspace_path
				+ "actual_results/SetTypeTestGenerics/TableContent1.txt";
		Attribute_c attr = Attribute_c.AttributeInstance(modelRoot);
		Cl_c.Clearselection();
		Selection.getInstance().addToSelection(attr);
		SetTypeOnO_ATTRAction aca = new SetTypeOnO_ATTRAction();
		Action a = new Action() {
		};
		aca.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) Selection
				.getInstance().getSelection();

		TestUtil.checkTableItems(2000, 0, false, actualResultFilePath);
		SetTypeOnO_ATTRAction.O_ATTR_SetType(structuredSelection);
		TestingUtilities.fileContentsCompare(m_workspace_path
				+ "expected_results/SetTypeTestGenerics/TableContent1.txt",
				actualResultFilePath);

	}
	
	@Test
	public void testUDTBaseTypeAssignmentToCoreType() {
		Selection.getInstance().clear(); Selection.getInstance().addToSelection(testUDT);
		UUID coreId = m_sys.Getcoretypeid("boolean");
		PackageableElement_c pe = (PackageableElement_c) m_sys.getModelRoot()
				.getInstanceList(PackageableElement_c.class).getGlobal(coreId);
		SetTypeOnS_UDTAction action = new SetTypeOnS_UDTAction();
		Action a = new Action(){};
		action.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		FailableRunnable chooseItemInDialog = TestUtil.chooseItemInDialog(500, "boolean");
		TestUtil.okElementSelectionDialog(chooseItemInDialog);
		action.run(a);
		assertEquals(chooseItemInDialog.getFailure(), "");
		DataType_c assignedDt = DataType_c.getOneS_DTOnR18(testUDT);
		assertTrue("Expected core type was not assigned to UDT.", assignedDt.getName().equals("boolean"));
	}

	@Test
	public void testUDTBaseTypeAssignmentToUDT() {
		Selection.getInstance().clear(); Selection.getInstance().addToSelection(testUDT);
		DataType_c otherUDT = DataType_c.getOneS_DTOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(testPKG), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((DataType_c) candidate).getName().equals("otherUDT");
			}
		});
		PackageableElement_c pe = PackageableElement_c.getOnePE_PEOnR8001(otherUDT);
		SetTypeOnS_UDTAction action = new SetTypeOnS_UDTAction();
		Action a = new Action(){};
		action.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		FailableRunnable chooseItemInDialog = TestUtil.chooseItemInDialog(500, "otherUDT");
		TestUtil.okElementSelectionDialog(chooseItemInDialog);
		action.run(a);
		assertEquals(chooseItemInDialog.getFailure(), "");
		PackageableElement_c assignedPE = PackageableElement_c
				.getOnePE_PEOnR8001(DataType_c.getManyS_DTsOnR18(testUDT));
		assertTrue("Expected UDT was not assigned to UDT.", pe == assignedPE);
	}

	@Test
	public void testUDTBaseTypeAssignmentToEDT() {
		Selection.getInstance().clear(); Selection.getInstance().addToSelection(testUDT);
		DataType_c otherUDT = DataType_c.getOneS_DTOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(testPKG), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((DataType_c) candidate).getName().equals("EDT");
			}
		});
		PackageableElement_c pe = PackageableElement_c.getOnePE_PEOnR8001(otherUDT);
		SetTypeOnS_UDTAction action = new SetTypeOnS_UDTAction();
		Action a = new Action(){};
		action.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		FailableRunnable chooseItemInDialog = TestUtil.chooseItemInDialog(500, "EDT");
		TestUtil.okElementSelectionDialog(chooseItemInDialog);
		action.run(a);
		assertEquals(chooseItemInDialog.getFailure(), "");
		PackageableElement_c assignedPE = PackageableElement_c
				.getOnePE_PEOnR8001(DataType_c.getManyS_DTsOnR18(testUDT));
		assertTrue("Expected EDT was not assigned to UDT.", pe == assignedPE);
	}

	@Test
	public void testUDTBaseTypeAssignmentToSDT() {
		Selection.getInstance().clear(); Selection.getInstance().addToSelection(testUDT);
		DataType_c otherUDT = DataType_c.getOneS_DTOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(testPKG), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((DataType_c) candidate).getName().equals("SDT");
			}
		});
		PackageableElement_c pe = PackageableElement_c.getOnePE_PEOnR8001(otherUDT);
		SetTypeOnS_UDTAction action = new SetTypeOnS_UDTAction();
		Action a = new Action(){};
		action.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		FailableRunnable chooseItemInDialog = TestUtil.chooseItemInDialog(500, "SDT");
		TestUtil.okElementSelectionDialog(chooseItemInDialog);
		action.run(a);
		assertEquals(chooseItemInDialog.getFailure(), "");
		PackageableElement_c assignedPE = PackageableElement_c
				.getOnePE_PEOnR8001(DataType_c.getManyS_DTsOnR18(testUDT));
		assertTrue("Expected SDT was not assigned to UDT.", pe == assignedPE);
	}

	@Test
	public void testUDTBaseTypeAssignmentToIRDT() {
		Selection.getInstance().clear(); Selection.getInstance().addToSelection(testUDT);
		DataType_c otherUDT = DataType_c.getOneS_DTOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(testPKG), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((DataType_c) candidate).getName().equals("inst_ref<IRDT>");
			}
		});
		PackageableElement_c pe = PackageableElement_c.getOnePE_PEOnR8001(otherUDT);
		SetTypeOnS_UDTAction action = new SetTypeOnS_UDTAction();
		Action a = new Action(){};
		action.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		FailableRunnable chooseItemInDialog = TestUtil.chooseItemInDialog(500, "inst_ref<IRDT>");
		TestUtil.okElementSelectionDialog(chooseItemInDialog);
		action.run(a);
		assertEquals(chooseItemInDialog.getFailure(), "");
		PackageableElement_c assignedPE = PackageableElement_c
				.getOnePE_PEOnR8001(DataType_c.getManyS_DTsOnR18(testUDT));
		assertTrue("Expected IRDT was not assigned to UDT.", pe == assignedPE);
	}

}