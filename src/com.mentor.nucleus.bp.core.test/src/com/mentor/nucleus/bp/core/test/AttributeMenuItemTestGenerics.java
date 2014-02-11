//=====================================================================
//
//File:      $RCSfile: AttributeMenuItemTestGenerics.java,v $
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

package com.mentor.nucleus.bp.core.test;

import java.util.UUID;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.ReferentialAttribute_c;
import com.mentor.nucleus.bp.core.SystemDatatypePackage_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.AddToIdentifierOnO_ATTRAction;
import com.mentor.nucleus.bp.core.ui.AddToIdentifierOnO_ATTRWizardPage1;
import com.mentor.nucleus.bp.core.ui.BinaryFormalizeOnR_RELAction;
import com.mentor.nucleus.bp.core.ui.BinaryFormalizeOnR_RELWizardPage1;
import com.mentor.nucleus.bp.core.ui.BinaryFormalizeOnR_RELWizardPage2;
import com.mentor.nucleus.bp.core.ui.MoveDownOnO_ATTRAction;
import com.mentor.nucleus.bp.core.ui.MoveUpOnO_ATTRAction;
import com.mentor.nucleus.bp.core.ui.RemoveFromIdentifierOnO_ATTRAction;
import com.mentor.nucleus.bp.core.ui.RemoveFromIdentifierOnO_ATTRWizardPage1;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.SetAsBaseAttributeOnO_ATTRAction;
import com.mentor.nucleus.bp.core.ui.SetAsDerivedAttributeOnO_ATTRAction;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphconnector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphedge_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;

public class AttributeMenuItemTestGenerics extends CanvasTest {

	String test_id = null;
	private static boolean generateResults = false;
	private boolean reloadModel = false;
	private static boolean initialized = false;
	private static Selection selection = Selection.getInstance();

	public AttributeMenuItemTestGenerics(String name) {
		super("Default Project", name);
	}

	protected String getResultName() {
		return "AttributeMenuItem" + "_" + test_id;
	}

	public void setReloadModel(boolean val) {
		reloadModel = val;
	}

	protected void setUp() throws Exception {
		super.setUp();
		Display d = Display.getCurrent();
		while (d.readAndDispatch());
		if (!initialized) {
			loadProject("AttributeMenuItemTests");
			initialized = true;
		}
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void setGenerateResults() {
		try {
			generateResults = true;
			setUp();
			testMoveUpOneAttribute();
			testMoveWithTwoAttributes();
			testMoveWithThreeAttributes();
			testBaseAttributeToDerived();
			testDerivedAttributeToBase();
			testAddToIdentifier();
			testRemoveFromIdentifier();
			testAddToIdentifierReferencedSimple();
			testAddToIdentifierReferencedAssoc();
			testAddToIdentifierReferencedInher();
			testFormalizeAfterMoveDown();
			testFormalizeAfterMoveUp();
		} catch (Exception e) {
			System.out.println("Exception encountered by test result creator: "
					+ e);
		}

	}

	public class Attribute_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			Attribute_c selected = (Attribute_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public Attribute_by_name_c(String name) {
			m_name = name;
		}
		private String m_name;
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

	public void testMoveUpOneAttribute() {
		test_id = "1";

		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testOneAttribute"));
		Attribute_c uut = Attribute_c.getOneO_ATTROnR102(mc);
		assertFalse(uut.Actionfilter("can", "move up"));
	}

	public void testMoveDownOneAttribute() {
		test_id = "2";

		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testOneAttribute"));
		Attribute_c uut = Attribute_c.getOneO_ATTROnR102(mc);
		assertFalse(uut.Actionfilter("can", "move down"));
	}

	public void testMoveWithTwoAttributes() {

		openTestPKGDiagram("Attribute Tests");

		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testMoveTwoAttributes"));
		Attribute_c top = Attribute_c.getOneO_ATTROnR102(mc,
				new Attribute_by_name_c("top"));
		Attribute_c bottom = Attribute_c.getOneO_ATTROnR102(mc,
				new Attribute_by_name_c("bottom"));
		assertTrue(top.Actionfilter("can", "move down"));
		assertFalse(top.Actionfilter("can", "move up"));
		assertFalse(bottom.Actionfilter("can", "move down"));
		assertTrue(bottom.Actionfilter("can", "move up"));
		Cl_c.Clearselection();
		selection.addToSelection(top);
		MoveDownOnO_ATTRAction mda = new MoveDownOnO_ATTRAction();
		Action a = new Action() {
		};
		mda.run(a);
		performTest("3");

		assertFalse(top.Actionfilter("can", "move down"));
		assertTrue(top.Actionfilter("can", "move up"));
		assertTrue(bottom.Actionfilter("can", "move down"));
		assertFalse(bottom.Actionfilter("can", "move up"));
		MoveUpOnO_ATTRAction mua = new MoveUpOnO_ATTRAction();
		mua.run(a);
		performTest("4");
	}

	public void testMoveWithThreeAttributes() {

		openTestPKGDiagram("Attribute Tests");

		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testMoveThreeAttributes"));
		Attribute_c top = Attribute_c.getOneO_ATTROnR102(mc,
				new Attribute_by_name_c("top"));
		Attribute_c middle = Attribute_c.getOneO_ATTROnR102(mc,
				new Attribute_by_name_c("middle"));
		assertTrue(top.Actionfilter("can", "move down"));
		assertFalse(top.Actionfilter("can", "move up"));
		assertTrue(middle.Actionfilter("can", "move down"));
		assertTrue(middle.Actionfilter("can", "move up"));
		Cl_c.Clearselection();
		selection.addToSelection(middle);
		MoveUpOnO_ATTRAction mua = new MoveUpOnO_ATTRAction();
		Action a = new Action() {
		};
		mua.run(a);
		performTest("5");

		assertTrue(top.Actionfilter("can", "move down"));
		assertTrue(top.Actionfilter("can", "move up"));
		assertTrue(middle.Actionfilter("can", "move down"));
		assertFalse(middle.Actionfilter("can", "move up"));
		Cl_c.Clearselection();
		selection.addToSelection(top);
		MoveDownOnO_ATTRAction mda = new MoveDownOnO_ATTRAction();
		mda.run(a);
		performTest("6");
	}

	public void testBaseAttributeToDerived() {

		openTestPKGDiagram("Attribute Tests");

		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testBaseToDerived"));
		Attribute_c base = Attribute_c.getOneO_ATTROnR102(mc,
				new Attribute_by_name_c("base"));
		assertTrue(base.Actionfilter("subtype", "O_NBATTR"));
		assertFalse(base.Actionfilter("subtype", "O_DBATTR"));
		Cl_c.Clearselection();
		selection.addToSelection(base);
		SetAsDerivedAttributeOnO_ATTRAction b2da = new SetAsDerivedAttributeOnO_ATTRAction();
		Action a = new Action() {
		};
		b2da.run(a);
		performTest("7");
		assertFalse(base.Actionfilter("subtype", "O_NBATTR"));
		assertTrue(base.Actionfilter("subtype", "O_DBATTR"));
	}

	public void testDerivedAttributeToBase() {

		openTestPKGDiagram("Attribute Tests");

		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testBaseToDerived"));
		Attribute_c derived = Attribute_c.getOneO_ATTROnR102(mc,
				new Attribute_by_name_c("derived"));
		assertFalse(derived.Actionfilter("subtype", "O_NBATTR"));
		assertTrue(derived.Actionfilter("subtype", "O_DBATTR"));
		Cl_c.Clearselection();
		selection.addToSelection(derived);
		SetAsBaseAttributeOnO_ATTRAction d2ba = new SetAsBaseAttributeOnO_ATTRAction();
		Action a = new Action() {
		};
		d2ba.run(a);
		performTest("8");
		assertTrue(derived.Actionfilter("subtype", "O_NBATTR"));
		assertFalse(derived.Actionfilter("subtype", "O_DBATTR"));
	}

	public void testAddToIdentifier() {

		openTestPKGDiagram("Attribute Tests");

		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testOneAttribute"));
		Attribute_c attr = Attribute_c.getOneO_ATTROnR102(mc,
				new Attribute_by_name_c("one"));
		assertTrue(attr.Actionfilter("id", "not all"));
		assertFalse(attr.Actionfilter("id", "some"));
		Cl_c.Clearselection();
		selection.addToSelection(attr);
		addToId(new String[]{"*1", "*2", "*3"});
		performTest("9");
		assertTrue(attr.Actionfilter("id", "not all"));
		assertTrue(attr.Actionfilter("id", "some"));

		addToId(new String[]{"*2", "*3"});
		performTest("10");
		assertTrue(attr.Actionfilter("id", "not all"));
		assertTrue(attr.Actionfilter("id", "some"));

		addToId(new String[]{"*3"});
		performTest("11");
		assertFalse(attr.Actionfilter("id", "not all"));
		assertTrue(attr.Actionfilter("id", "some"));

	}

	private Action addToId(String[] possible_ids) {
		Action a = new Action() {
		};
		AddToIdentifierOnO_ATTRAction atia = new AddToIdentifierOnO_ATTRAction();
		atia.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) Selection
				.getInstance().getSelection();
		WizardDialog wd = atia.O_ATTR_AddToIdentifier(structuredSelection);
		AddToIdentifierOnO_ATTRWizardPage1 page = (AddToIdentifierOnO_ATTRWizardPage1) wd
				.getCurrentPage();
		page.IdentifierCombo.select(0);
		String[] items = page.IdentifierCombo.getItems();
		assertEquals(possible_ids.length, items.length);
		for (int i = 0; i < possible_ids.length; ++i) {
			assertEquals(possible_ids[i], items[i]);
		}
		IWizard w = page.getWizard();
		w.performFinish();
		wd.close();
		return a;
	}

	public void testRemoveFromIdentifier() {
		openTestPKGDiagram("Attribute Tests");

		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testOneAttribute"));
		Attribute_c attr = Attribute_c.getOneO_ATTROnR102(mc,
				new Attribute_by_name_c("one"));
		assertFalse(attr.Actionfilter("id", "not all"));
		assertTrue(attr.Actionfilter("id", "some"));
		Cl_c.Clearselection();
		selection.addToSelection(attr);
		removefromId(new String[]{"*1", "*2", "*3"});
		performTest("12");
		assertTrue(attr.Actionfilter("id", "not all"));
		assertTrue(attr.Actionfilter("id", "some"));

		removefromId(new String[]{"*2", "*3"});
		performTest("13");
		assertTrue(attr.Actionfilter("id", "not all"));
		assertTrue(attr.Actionfilter("id", "some"));

		removefromId(new String[]{"*3"});
		performTest("14");
		assertTrue(attr.Actionfilter("id", "not all"));
		assertFalse(attr.Actionfilter("id", "some"));

		Action a = new Action() {
		};
		AddToIdentifierOnO_ATTRAction atia = new AddToIdentifierOnO_ATTRAction();
		atia.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) Selection
				.getInstance().getSelection();
		WizardDialog wd = atia.O_ATTR_AddToIdentifier(structuredSelection);
		AddToIdentifierOnO_ATTRWizardPage1 page = (AddToIdentifierOnO_ATTRWizardPage1) wd
				.getCurrentPage();
		String[] items = page.IdentifierCombo.getItems();
		assertEquals(3, items.length);
		assertEquals("*1", items[0]);
		assertEquals("*2", items[1]);
		assertEquals("*3", items[2]);

	}

	private void removefromId(String[] possible_ids) {
		RemoveFromIdentifierOnO_ATTRAction rfia = new RemoveFromIdentifierOnO_ATTRAction();
		Action a = new Action() {
		};
		rfia.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) Selection
				.getInstance().getSelection();
		WizardDialog wd = rfia.O_ATTR_RemoveFromIdentifier(structuredSelection);
		RemoveFromIdentifierOnO_ATTRWizardPage1 page = (RemoveFromIdentifierOnO_ATTRWizardPage1) wd
				.getCurrentPage();
		page.IdentifierCombo.select(0);
		String[] items = page.IdentifierCombo.getItems();
		assertEquals(possible_ids.length, items.length);
		for (int i = 0; i < possible_ids.length; ++i) {
			assertEquals(possible_ids[i], items[i]);
		}

		IWizard w = page.getWizard();
		wd.close();
		w.performFinish();
	}

	public void testAddToIdentifierReferencedSimple() {

		openTestPKGDiagram("Attribute Tests");

		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testAddIdToRefPart"));
		Attribute_c attr = Attribute_c.getOneO_ATTROnR102(mc,
				new Attribute_by_name_c("new"));
		assertTrue(attr.Actionfilter("id", "not all"));
		assertFalse(attr.Actionfilter("id", "some"));
		Cl_c.Clearselection();
		selection.addToSelection(attr);
		addToId(new String[]{"*1", "*2", "*3"});
		performTest("15");
		assertTrue(attr.Actionfilter("id", "not all"));
		assertTrue(attr.Actionfilter("id", "some"));
		ModelClass_c ref_class = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testAddIdToRefForm"));
		Attribute_c new_ref_attr = Attribute_c.getOneO_ATTROnR102(ref_class,
				new Attribute_by_name_c("new"));
		UUID expectedId = getSameAsBaseAttributeUUID(Ooaofooa.getDefaultInstance());
		assertEquals(expectedId, new_ref_attr.getDt_id());

		removefromId(new String[]{"*1"});
		performTest("16");
		assertTrue(attr.Actionfilter("id", "not all"));
		assertFalse(attr.Actionfilter("id", "some"));

		Attribute_c orf_attr = Attribute_c.getOneO_ATTROnR102(ref_class,
				new Attribute_by_name_c("Orphaned"));
		assertNull(orf_attr);
		orf_attr = Attribute_c.getOneO_ATTROnR102(ref_class,
				new Attribute_by_name_c("new"));
		assertNull(orf_attr);
		Attribute_c ref_attr = Attribute_c.getOneO_ATTROnR102(ref_class,
				new Attribute_by_name_c("id"));
		ReferentialAttribute_c ref_rattr = ReferentialAttribute_c
				.getOneO_RATTROnR106(ref_attr);
		assertFalse(ref_rattr.Isorphaned());

	}

	public void testAddToIdentifierReferencedAssoc() {

		openTestPKGDiagram("Attribute Tests");

		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testAddIdToAone"));
		Attribute_c attr = Attribute_c.getOneO_ATTROnR102(mc,
				new Attribute_by_name_c("new_one_id"));
		assertTrue(attr.Actionfilter("id", "not all"));
		assertFalse(attr.Actionfilter("id", "some"));
		Cl_c.Clearselection();
		selection.addToSelection(attr);
		addToId(new String[]{"*1", "*2", "*3"});
		performTest("18");
		assertTrue(attr.Actionfilter("id", "not all"));
		assertTrue(attr.Actionfilter("id", "some"));
		ModelClass_c ref_class = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testAddIdToLink"));
		Attribute_c new_ref_attr = Attribute_c.getOneO_ATTROnR102(ref_class,
				new Attribute_by_name_c("new_one_id"));
		UUID expectedId = getSameAsBaseAttributeUUID(Ooaofooa.getDefaultInstance());
		assertEquals(expectedId, new_ref_attr.getDt_id());

		removefromId(new String[]{"*1"});
		performTest("19");
		assertTrue(attr.Actionfilter("id", "not all"));
		assertFalse(attr.Actionfilter("id", "some"));

		Attribute_c orf_attr = Attribute_c.getOneO_ATTROnR102(ref_class,
				new Attribute_by_name_c("Orphaned"));
		assertNull(orf_attr);
		orf_attr = Attribute_c.getOneO_ATTROnR102(ref_class,
				new Attribute_by_name_c("new_one_id"));
		// this attribute is still there because it's referenced by R8
		assertNotNull(orf_attr);
		Attribute_c ref_attr = Attribute_c.getOneO_ATTROnR102(ref_class,
				new Attribute_by_name_c("one_id"));
		ReferentialAttribute_c ref_rattr = ReferentialAttribute_c
				.getOneO_RATTROnR106(ref_attr);
		assertFalse(ref_rattr.Isorphaned());

		ModelClass_c oth_mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testAddIdToAoth"));
		Attribute_c oth_attr = Attribute_c.getOneO_ATTROnR102(oth_mc,
				new Attribute_by_name_c("new_oth_id"));
		assertTrue(oth_attr.Actionfilter("id", "not all"));
		assertFalse(oth_attr.Actionfilter("id", "some"));
		Cl_c.Clearselection();
		selection.addToSelection(oth_attr);
		addToId(new String[]{"*1", "*2", "*3"});
		performTest("21");
		assertTrue(oth_attr.Actionfilter("id", "not all"));
		assertTrue(oth_attr.Actionfilter("id", "some"));
		new_ref_attr = Attribute_c.getOneO_ATTROnR102(ref_class,
				new Attribute_by_name_c("new_oth_id"));
		expectedId = getSameAsBaseAttributeUUID(Ooaofooa.getDefaultInstance());
		assertEquals(expectedId, new_ref_attr.getDt_id());

		removefromId(new String[]{"*1"});
		performTest("22");
		assertTrue(oth_attr.Actionfilter("id", "not all"));
		assertFalse(oth_attr.Actionfilter("id", "some"));

		orf_attr = Attribute_c.getOneO_ATTROnR102(ref_class,
				new Attribute_by_name_c("Orphaned"));
		assertNull(orf_attr);
		orf_attr = Attribute_c.getOneO_ATTROnR102(ref_class,
				new Attribute_by_name_c("new_oth_id"));
		assertNotNull(orf_attr);
		ref_attr = Attribute_c.getOneO_ATTROnR102(ref_class,
				new Attribute_by_name_c("oth_id"));
		ref_rattr = ReferentialAttribute_c.getOneO_RATTROnR106(ref_attr);
		assertFalse(ref_rattr.Isorphaned());

	}

	public void testAddToIdentifierReferencedInher() {

		openTestPKGDiagram("Attribute Tests");

		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testAddIdSup"));
		Attribute_c attr = Attribute_c.getOneO_ATTROnR102(mc,
				new Attribute_by_name_c("new_id"));
		assertTrue(attr.Actionfilter("id", "not all"));
		assertFalse(attr.Actionfilter("id", "some"));
		Cl_c.Clearselection();
		selection.addToSelection(attr);
		addToId(new String[]{"*1", "*2", "*3"});
		performTest("24");
		assertTrue(attr.Actionfilter("id", "not all"));
		assertTrue(attr.Actionfilter("id", "some"));
		ModelClass_c ref_class = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testAddIdSub"));
		Attribute_c new_ref_attr = Attribute_c.getOneO_ATTROnR102(ref_class,
				new Attribute_by_name_c("new_id"));
		UUID expectedId = getSameAsBaseAttributeUUID(Ooaofooa.getDefaultInstance());
		assertEquals(expectedId, new_ref_attr.getDt_id());

		removefromId(new String[]{"*1"});
		performTest("25");
		assertTrue(attr.Actionfilter("id", "not all"));
		assertFalse(attr.Actionfilter("id", "some"));

		Attribute_c orf_attr = Attribute_c.getOneO_ATTROnR102(ref_class,
				new Attribute_by_name_c("Orphaned"));
		assertNull(orf_attr);
		orf_attr = Attribute_c.getOneO_ATTROnR102(ref_class,
				new Attribute_by_name_c("new_id"));
		// this attribute is still there because it's referenced by R9
		assertNotNull(orf_attr);
		Attribute_c ref_attr = Attribute_c.getOneO_ATTROnR102(ref_class,
				new Attribute_by_name_c("id"));
		ReferentialAttribute_c ref_rattr = ReferentialAttribute_c
				.getOneO_RATTROnR106(ref_attr);
		assertFalse(ref_rattr.Isorphaned());

	}

	public void testFormalizeAfterMoveDown() {

		openTestPKGDiagram("Attribute Tests");
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testFormalizeAfterMoveDown"));
		Attribute_c top = Attribute_c.getOneO_ATTROnR102(mc,
				new Attribute_by_name_c("TestAttrFormalizerMoveDown"));
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"testFormalizeAfterMoveDown");
		Cl_c.Clearselection();
		selection.addToSelection(top);
		Action a = new Action() {
		};
		MoveDownOnO_ATTRAction mda = new MoveDownOnO_ATTRAction();
		mda.run(a);
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		selection.addToSelection(ge2.getRepresents());
		BinaryFormalizeOnR_RELAction fa = new BinaryFormalizeOnR_RELAction();
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) Selection
				.getInstance().getSelection();
		WizardDialog wd = fa.R_REL_BinaryFormalize(structuredSelection);
		BinaryFormalizeOnR_RELWizardPage1 page1 = (BinaryFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		BinaryFormalizeOnR_RELWizardPage2 page2 = (BinaryFormalizeOnR_RELWizardPage2) page1
				.getNextPage();
		String strings[] = page1.Non_formalizerCombo.getItems();
		for (int i = 0; i < strings.length; ++i) {
			if (strings[i].equals("testFormalizeAfterMoveDown")) {
				page1.Non_formalizerCombo.select(i);
			}
		}
		page1.updateSelectedNon_formalizer();
		page2.onPageEntry();
		page1.onPageEntry();
		IWizard w = page1.getWizard();
		boolean exceptionCaught = false;
		try {
			w.performFinish();
		} catch (Exception e) {
			exceptionCaught = true;
		}
		wd.close();
		assertFalse(exceptionCaught);
		performTest("27");
	}

	public void testFormalizeAfterMoveUp() {

		openTestPKGDiagram("Attribute Tests");
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("testFormalizeAfterMoveUp"));
		Attribute_c top = Attribute_c.getOneO_ATTROnR102(mc,
				new Attribute_by_name_c("TestAttrFormalizerMoveUp"));
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"testFormalizeAfterMoveUp");
		Cl_c.Clearselection();
		selection.addToSelection(top);
		Action a = new Action() {
		};
		MoveUpOnO_ATTRAction mua = new MoveUpOnO_ATTRAction();
		mua.run(a);
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		selection.addToSelection(ge2.getRepresents());
		BinaryFormalizeOnR_RELAction fa = new BinaryFormalizeOnR_RELAction();
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) Selection
				.getInstance().getSelection();
		WizardDialog wd = fa.R_REL_BinaryFormalize(structuredSelection);
		BinaryFormalizeOnR_RELWizardPage1 page1 = (BinaryFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		BinaryFormalizeOnR_RELWizardPage2 page2 = (BinaryFormalizeOnR_RELWizardPage2) page1
				.getNextPage();
		String strings[] = page1.Non_formalizerCombo.getItems();
		for (int i = 0; i < strings.length; ++i) {
			if (strings[i].equals("testFormalizeAfterMoveUp")) {
				page1.Non_formalizerCombo.select(i);
			}
		}
		page1.updateSelectedNon_formalizer();
		page2.onPageEntry();
		page1.onPageEntry();
		IWizard w = page1.getWizard();
		boolean exceptionCaught = false;
		try {
			w.performFinish();
		} catch (Exception e) {
			exceptionCaught = true;
		}
		wd.close();
		assertFalse(exceptionCaught);
		performTest("28");
	}

	public boolean fSkipValidate = false;
	private void performTest(String test_num) {
		if (fSkipValidate)
			return;
		test_id = test_num;
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		validateOrGenerateResults(ce, generateResults);
	}
}