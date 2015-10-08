//=====================================================================
//
//File:      $RCSfile: CombineSplitReferentialsTestGenerics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 04:30:24 $
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

package org.xtuml.bp.core.test;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Package_c;

import org.xtuml.bp.core.ui.CombineWithOnO_ATTRAction;
import org.xtuml.bp.core.ui.CombineWithOnO_ATTRWizardPage1;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.SplitOnO_ATTRAction;
import org.xtuml.bp.core.ui.SplitOnO_ATTRWizardPage1;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.ui.canvas.Cl_c;
import org.xtuml.bp.ui.canvas.test.CanvasTest;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.ui.graphics.editor.ModelEditor;

public class CombineSplitReferentialsTestGenerics extends CanvasTest {

	String test_id = null;
	private static boolean generateResults = false;
	private static boolean initialized = false;
	private static Selection selection = Selection.getInstance();

	public CombineSplitReferentialsTestGenerics(String name) {
		super(null, name);
	}

	protected String getResultName() {
		return "CombineSplitReferentials" + "_" + test_id;
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (!initialized) {
			loadProject("CombineSplitReferentialsTest");
			initialized = true;
		}
		Display d = Display.getCurrent();
		while (d.readAndDispatch());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void setGenerateResults() {
		try {
			generateResults = true;
			setUp();
			doTestSelectNonReferentialAttribute();
			doTestClassWithOneReferentialAttribute();
			doTestReferentialAttributesWithDiffBaseTypes();
			doTestTwoReferentialAttributesWithSameBaseTypes();
			doTestSelectCombinedReferentialAttributes();
			doTestTwoReferentialAttributesSameBaseTypesOneDifferent();
			doTestTwoCombinedReferentials();
			doTestThreeReferentials();
			doTestCombineTwoCombinedRefs();
			doTestCombineRefWithIDRef();
			doTestSplitNameLoopGood();
			doTestSplitNameLoopBad();
			doTestSplitPrefixLoopGood();
			doTestSplitPrefixLoopBad();
		} catch (Exception e) {
			System.out.println("Exception encountered by test result creator: "
					+ e);
		}

	}

	public void openTestPKGDiagram(String title) {
		Package_c uut = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c(title));
		CanvasTestUtils.openCanvasEditor(uut);
	}
	
	public void doTestCombineSplitReferentialsTest(){
		 doTestSelectNonReferentialAttribute();
	      doTestClassWithOneReferentialAttribute();
	      doTestReferentialAttributesWithDiffBaseTypes();
	      doTestTwoReferentialAttributesWithSameBaseTypes();
	      doTestSelectCombinedReferentialAttributes();
	      doTestTwoReferentialAttributesSameBaseTypesOneDifferent();
	      doTestTwoCombinedReferentials();
	      doTestThreeReferentials();
	      doTestCombineTwoCombinedRefs();
	      doTestCombineRefWithIDRef();
	      doTestSplitNameLoopGood();
	      doTestSplitNameLoopBad();
	      doTestSplitPrefixLoopGood();
	      doTestSplitPrefixLoopBad();
	}

	public void doTestSelectNonReferentialAttribute() {
		test_id = "1";

		openTestPKGDiagram("TestCombineSplitReferentials");
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testNonRefAttr"));
		Attribute_c uut = Attribute_c.getOneO_ATTROnR102(mc);
		assertFalse(uut.Actionfilter("can", "combine"));
		assertFalse(uut.Actionfilter("can", "split"));
	}

	public void doTestClassWithOneReferentialAttribute() {
		// only 1 referential attribute in class
		test_id = "2";

		openTestPKGDiagram("TestCombineSplitReferentials");
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testOneRefAttr"));
		Attribute_c ref_attr = Attribute_c.getOneO_ATTROnR102(mc,
				new Attribute_by_name_c("attr1"));
		assertFalse(ref_attr.Actionfilter("can", "combine"));
		assertFalse(ref_attr.Actionfilter("can", "split"));
	}

	public void doTestReferentialAttributesWithDiffBaseTypes() {
		test_id = "3";

		openTestPKGDiagram("TestCombineSplitReferentials");
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testRefAttrsDiffBaseType"));
		Attribute_c ref_attr = Attribute_c.getOneO_ATTROnR102(mc,
				new Attribute_by_name_c("attr1"));
		assertFalse(ref_attr.Actionfilter("can", "combine"));
		assertFalse(ref_attr.Actionfilter("can", "split"));
	}

	public void doTestTwoReferentialAttributesWithSameBaseTypes() {
		openTestPKGDiagram("TestCombineSplitReferentials");
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testTwoRefAttr"));
		Attribute_c[] ref_attrs = Attribute_c.getManyO_ATTRsOnR102(mc);
		Attribute_c ref_attr1 = ref_attrs[0];
		assertTrue(ref_attr1.Actionfilter("can", "combine"));
		assertFalse(ref_attr1.Actionfilter("can", "split"));

		Cl_c.Clearselection();
		selection.addToSelection(ref_attr1);

		Action a = new Action() {
		};
		CombineWithOnO_ATTRAction cwooa = new CombineWithOnO_ATTRAction();
		cwooa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) selection
				.getSelection();
		WizardDialog wd = cwooa.O_ATTR_CombineWith(structuredSelection);
		CombineWithOnO_ATTRWizardPage1 page = (CombineWithOnO_ATTRWizardPage1) wd
				.getCurrentPage();
		String[] items = page.Combine_withCombo.getItems();
		assertEquals(1, items.length);
		assertEquals("attr1 (R9)", items[0]);
		page.Combine_withCombo.select(0);
		IWizard w = page.getWizard();
		w.performFinish();
		wd.close();
		performTest("4");

	}

	public void doTestSelectCombinedReferentialAttributes() {
		openTestPKGDiagram("TestCombineSplitReferentials");
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testTwoRefAttr"));
		Attribute_c[] ref_attrs = Attribute_c.getManyO_ATTRsOnR102(mc);
		Attribute_c ref_attr1 = ref_attrs[0];
		assertFalse(ref_attr1.Actionfilter("can", "combine"));
		assertTrue(ref_attr1.Actionfilter("can", "split"));

		Cl_c.Clearselection();
		selection.addToSelection(ref_attr1);

		Action a = new Action() {
		};
		SplitOnO_ATTRAction sooa = new SplitOnO_ATTRAction();
		sooa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) selection
				.getSelection();
		WizardDialog wd = sooa.O_ATTR_Split(structuredSelection);
		SplitOnO_ATTRWizardPage1 page = (SplitOnO_ATTRWizardPage1) wd
				.getCurrentPage();
		String[] items = page.Split_fromCombo.getItems();
		assertEquals(2, items.length);
		assertEquals("e.attr1(R8)", items[0]);
		assertEquals("testOneRefAttr.attr1(R9)", items[1]);
		page.Split_fromCombo.select(0);
		IWizard w = page.getWizard();
		w.performFinish();
		wd.close();
		performTest("5");

	}

	public void doTestTwoReferentialAttributesSameBaseTypesOneDifferent() {
		openTestPKGDiagram("TestCombineSplitReferentials");
		ModelClass_c mc = ModelClass_c
				.ModelClassInstance(modelRoot, new ModelClass_by_name_c(
						"testTwoRefAttrSameBaseOneRefAttrDiff"));
		Attribute_c[] ref_attrs = Attribute_c.getManyO_ATTRsOnR102(mc);
		Attribute_c ref_attr1 = ref_attrs[1];
		assertTrue(ref_attr1.Actionfilter("can", "combine"));
		assertFalse(ref_attr1.Actionfilter("can", "split"));

		Cl_c.Clearselection();
		selection.addToSelection(ref_attr1);

		Action a = new Action() {
		};
		CombineWithOnO_ATTRAction cwooa = new CombineWithOnO_ATTRAction();
		cwooa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) selection
				.getSelection();
		WizardDialog wd = cwooa.O_ATTR_CombineWith(structuredSelection);
		CombineWithOnO_ATTRWizardPage1 page = (CombineWithOnO_ATTRWizardPage1) wd
				.getCurrentPage();
		String[] items = page.Combine_withCombo.getItems();
		assertEquals(1, items.length);
		assertEquals("attr1 (R11)", items[0]);
		page.Combine_withCombo.select(0);
		IWizard w = page.getWizard();
		w.performCancel();
		wd.close();
		performTest("6");
	}

	public void doTestTwoCombinedReferentials() {
		openTestPKGDiagram("TestCombineSplitReferentials");
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testTwoCombinedRefAttrs"));
		Attribute_c[] ref_attrs = Attribute_c.getManyO_ATTRsOnR102(mc);
		Attribute_c ref_attr1 = ref_attrs[0];
		assertTrue(ref_attr1.Actionfilter("can", "combine"));
		assertTrue(ref_attr1.Actionfilter("can", "split"));

		Cl_c.Clearselection();
		selection.addToSelection(ref_attr1);

		Action a = new Action() {
		};
		SplitOnO_ATTRAction sooa = new SplitOnO_ATTRAction();
		sooa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) selection
				.getSelection();
		WizardDialog wd = sooa.O_ATTR_Split(structuredSelection);
		SplitOnO_ATTRWizardPage1 page = (SplitOnO_ATTRWizardPage1) wd
				.getCurrentPage();
		String[] items = page.Split_fromCombo.getItems();
		assertEquals(2, items.length);
		assertEquals("f.attr2(R18)", items[0]);
		assertEquals("testNonRefAttr.attr2(R19)", items[1]);
		page.Split_fromCombo.select(0);
		IWizard w = page.getWizard();
		w.performCancel();
		wd.close();
		performTest("7");

	}

	public void doTestThreeReferentials() {
		openTestPKGDiagram("TestCombineSplitReferentials");
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testThreeRefAttrs"));
		Attribute_c[] ref_attrs = Attribute_c.getManyO_ATTRsOnR102(mc);
		Attribute_c ref_attr1 = ref_attrs[0];
		assertTrue(ref_attr1.Actionfilter("can", "combine"));
		assertFalse(ref_attr1.Actionfilter("can", "split"));

		Cl_c.Clearselection();
		selection.addToSelection(ref_attr1);

		Action a = new Action() {
		};
		CombineWithOnO_ATTRAction cwooa = new CombineWithOnO_ATTRAction();
		cwooa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) selection
				.getSelection();
		WizardDialog wd = cwooa.O_ATTR_CombineWith(structuredSelection);
		CombineWithOnO_ATTRWizardPage1 page = (CombineWithOnO_ATTRWizardPage1) wd
				.getCurrentPage();
		String[] items = page.Combine_withCombo.getItems();
		assertEquals(2, items.length);
		assertEquals("attr1 (R5)", items[0]);
		assertEquals("attr1 (R20)", items[1]);
		page.Combine_withCombo.select(0);
		IWizard w = page.getWizard();
		w.performFinish();
		wd.close();
		performTest("8");

		assertTrue(ref_attr1.Actionfilter("can", "combine"));
		assertTrue(ref_attr1.Actionfilter("can", "split"));

		Cl_c.Clearselection();
		selection.addToSelection(ref_attr1);

		a = new Action() {
		};
		cwooa = new CombineWithOnO_ATTRAction();
		cwooa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		structuredSelection = (IStructuredSelection) selection.getSelection();
		wd = cwooa.O_ATTR_CombineWith(structuredSelection);
		page = (CombineWithOnO_ATTRWizardPage1) wd.getCurrentPage();
		items = page.Combine_withCombo.getItems();
		assertEquals(1, items.length);
		assertEquals("attr1 (R20)", items[0]);
		page.Combine_withCombo.select(0);
		w = page.getWizard();
		w.performFinish();

		performTest("9");

		assertFalse(ref_attr1.Actionfilter("can", "combine"));
		assertTrue(ref_attr1.Actionfilter("can", "split"));

		Cl_c.Clearselection();
		selection.addToSelection(ref_attr1);

		a = new Action() {
		};
		SplitOnO_ATTRAction sooa = new SplitOnO_ATTRAction();
		sooa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		structuredSelection = (IStructuredSelection) selection.getSelection();
		wd = sooa.O_ATTR_Split(structuredSelection);
		SplitOnO_ATTRWizardPage1 pg = (SplitOnO_ATTRWizardPage1) wd
				.getCurrentPage();
		items = pg.Split_fromCombo.getItems();
		assertEquals(3, items.length);
		assertEquals("e.attr1(R7)", items[0]);
		assertEquals("testOneRefAttr.attr1(R5)", items[1]);
		assertEquals("a.attr1(R20)", items[2]);
		pg.Split_fromCombo.select(0);
		w = pg.getWizard();
		w.performFinish();

		performTest("10");

		assertTrue(ref_attr1.Actionfilter("can", "combine"));
		assertTrue(ref_attr1.Actionfilter("can", "split"));

		Cl_c.Clearselection();
		selection.addToSelection(ref_attr1);

		a = new Action() {
		};
		sooa = new SplitOnO_ATTRAction();
		sooa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		structuredSelection = (IStructuredSelection) selection.getSelection();
		wd = sooa.O_ATTR_Split(structuredSelection);
		pg = (SplitOnO_ATTRWizardPage1) wd.getCurrentPage();
		items = pg.Split_fromCombo.getItems();
		assertEquals(2, items.length);
		assertEquals("testOneRefAttr.attr1(R5)", items[0]);
		assertEquals("a.attr1(R20)", items[1]);
		pg.Split_fromCombo.select(0);
		w = pg.getWizard();
		w.performFinish();

		performTest("11");

	}

	public void doTestCombineTwoCombinedRefs() {
		openTestPKGDiagram("TestCombineSplitReferentials");
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testTwoCombinedRefAttrs"));
		Attribute_c[] ref_attrs = Attribute_c.getManyO_ATTRsOnR102(mc);
		Attribute_c ref_attr1 = ref_attrs[0];
		assertTrue(ref_attr1.Actionfilter("can", "combine"));
		assertTrue(ref_attr1.Actionfilter("can", "split"));

		Cl_c.Clearselection();
		selection.addToSelection(ref_attr1);

		Action a = new Action() {
		};
		CombineWithOnO_ATTRAction cwooa = new CombineWithOnO_ATTRAction();
		cwooa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) selection
				.getSelection();
		WizardDialog wd = cwooa.O_ATTR_CombineWith(structuredSelection);
		CombineWithOnO_ATTRWizardPage1 page = (CombineWithOnO_ATTRWizardPage1) wd
				.getCurrentPage();
		String[] items = page.Combine_withCombo.getItems();
		assertEquals(1, items.length);
		assertEquals("attr2 (R25) (R26)", items[0]);
		page.Combine_withCombo.select(0);
		IWizard w = page.getWizard();
		w.performFinish();
		wd.close();
		performTest("12");

	}

	public void doTestCombineRefWithIDRef() {
		openTestPKGDiagram("TestCombineSplitReferentials");
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testCombineRefWithIDRef"));
		Attribute_c[] ref_attrs = Attribute_c.getManyO_ATTRsOnR102(mc);
		Attribute_c ref_attr2 = ref_attrs[1];
		assertTrue(ref_attr2.Actionfilter("can", "combine"));
		assertFalse(ref_attr2.Actionfilter("can", "split"));

		Cl_c.Clearselection();
		selection.addToSelection(ref_attr2);

		Action a = new Action() {
		};
		CombineWithOnO_ATTRAction cwooa = new CombineWithOnO_ATTRAction();
		cwooa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) selection
				.getSelection();
		WizardDialog wd = cwooa.O_ATTR_CombineWith(structuredSelection);
		CombineWithOnO_ATTRWizardPage1 page = (CombineWithOnO_ATTRWizardPage1) wd
				.getCurrentPage();
		String[] items = page.Combine_withCombo.getItems();
		assertEquals(1, items.length);
		assertEquals("attr2 (R27)", items[0]);
		page.Combine_withCombo.select(0);
		IWizard w = page.getWizard();
		w.performFinish();
		wd.close();
		performTest("13");
	}

	public void doTestSplitNameLoopGood() {
		doTestSplitLoop("testSplitNameLoopGood", new String[]{
				"i1051-name.id(R29)", "testSplitNameLoopGood.id(R30)"}, 1, "14");
	}

	public void doTestSplitNameLoopBad() {
		doTestSplitLoop("testSplitNameLoopBad", new String[]{
				"i1051-name.id(R31)", "testSplitNameLoopBad.id(R32)"}, 0, "15");
	}

	public void doTestSplitPrefixLoopGood() {
		doTestSplitLoop("testSplitPrefixLoopGood", new String[]{
				"i1051-prefix.prefix_root(R33)",
				"testSplitPrefixLoopGood.prefix_local(R34)"}, 1, "16");
	}

	public void doTestSplitPrefixLoopBad() {
		doTestSplitLoop("testSplitPrefixLoopBad", new String[]{
				"i1051-prefix.prefix_root(R35)",
				"testSplitPrefixLoopBad.prefix_local(R36)"}, 0, "17");
	}

	private void doTestSplitLoop(String className, String[] itemValues,
			int testItem, String testNum) {
		openTestPKGDiagram("TestCombineSplitReferentials");
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c(className));
		Attribute_c[] ref_attrs = Attribute_c.getManyO_ATTRsOnR102(mc);
		Attribute_c ref_attr2 = ref_attrs[0];
		assertFalse(ref_attr2.Actionfilter("can", "combine"));
		assertTrue(ref_attr2.Actionfilter("can", "split"));

		Cl_c.Clearselection();
		selection.addToSelection(ref_attr2);

		Action a = new Action() {
		};
		SplitOnO_ATTRAction sooa = new SplitOnO_ATTRAction();
		sooa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) selection
				.getSelection();
		WizardDialog wd = sooa.O_ATTR_Split(structuredSelection);
		SplitOnO_ATTRWizardPage1 pg = (SplitOnO_ATTRWizardPage1) wd
				.getCurrentPage();
		String[] items = pg.Split_fromCombo.getItems();
		assertEquals(2, items.length);
		assertEquals(itemValues[0], items[0]);
		assertEquals(itemValues[1], items[1]);
		pg.Split_fromCombo.select(testItem);
		pg.updateSelectedSplit_from();
		IWizard w = pg.getWizard();
		w.performFinish();
		wd.close();
		Attribute_c[] post_attrs = Attribute_c.getManyO_ATTRsOnR102(mc);
		assertEquals(2, post_attrs.length);

		performTest(testNum);
	}

	private void performTest(String test_num) {
		test_id = test_num;
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		validateOrGenerateResults(ce, generateResults);
	}
}
