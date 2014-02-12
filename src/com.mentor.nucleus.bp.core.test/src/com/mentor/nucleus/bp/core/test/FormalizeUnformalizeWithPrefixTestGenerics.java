//=====================================================================
//
//File:      $RCSfile: FormalizeUnformalizeWithPrefixTestGenerics.java,v $
//Version:   $Revision: 1.1.2.4 $
//Modified:  $Date: 2013/03/25 19:20:07 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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
//
package com.mentor.nucleus.bp.core.test;

import java.util.UUID;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.core.ui.*;
import com.mentor.nucleus.bp.ui.canvas.*;
import com.mentor.nucleus.bp.ui.graphics.editor.*;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;

public class FormalizeUnformalizeWithPrefixTestGenerics extends CanvasTest {

	String test_id = null;
	private static boolean generateResults = false;
	private boolean reloadModel = false;

	private static boolean initialized = false;
	private static boolean firstTime = true;
	private static String projectName = "FormalizeWithPrefix";

	private static Selection selection = Selection.getInstance();

	public FormalizeUnformalizeWithPrefixTestGenerics(String name) {
		super(projectName, name);
	}

	protected String getResultName() {
		return "FormalizeUnformalizeWithPrefix" + "_" + test_id;
	}

	public void setReloadModel(boolean val) {
		reloadModel = val;
	}

	protected void setUp() throws Exception {
		super.setUp();
		Display d = Display.getCurrent();
		while (d.readAndDispatch());

		if (!initialized) {
			ensureAvailableAndLoaded("com.mentor.nucleus.bp.core.test",
					"FormalizeUnformalizeTests", false, false, "Package");
			initialized = true;
			m_bp_tree.expandAll();

		}
		while (d.readAndDispatch());

	}

	protected void tearDown() throws Exception {
		super.tearDown();
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
	public class getAssocByDescrip implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			Association_c selected = (Association_c) candidate;
			return (selected.getDescrip().equals(m_name));
		}
		public getAssocByDescrip(String name) {
			m_name = name;
		}
		private String m_name;
	}
	public class ImportedClass_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			ImportedClass_c selected = (ImportedClass_c) candidate;
			return (selected.Get_name().equals(m_name));
		}
		public ImportedClass_by_name_c(String name) {
			m_name = name;
		}
		private String m_name;
	}
	public Shape_c getModelIClassShape(String name) {
		CanvasTestUtils ctu = new CanvasTestUtils();
		ImportedClass_c ic = ImportedClass_c.ImportedClassInstance(modelRoot,
				new ImportedClass_by_name_c(name));
		GraphicalElement_c ge = GraphicalElement_c.GraphicalElementInstance(
				graphicsModelRoot,
				ctu.new findGraphicalElementByOOAID(ic.getIobj_id(), 23));
		return Shape_c.getOneGD_SHPOnR2(ge);
	}
	public void openTestPKGDiagram(String title) {
		Package_c uut = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c(title));
		assertNotNull(uut);
		CanvasTestUtils.openCanvasEditor(uut);
	}
	private void validatePrefix(Association_c rel, String mode,
			String prefix1, String prefix2) {
		if ( rel == null){
			assertFalse(true);
		}
		if ( SimpleAssociation_c.getOneR_SIMPOnR206(rel) != null){



			Attribute_c[] refIDs = Attribute_c.getManyO_ATTRsOnR106(
					ReferentialAttribute_c.getManyO_RATTRsOnR108(
							AttributeReferenceInClass_c.getManyO_REFsOnR111(
									ReferringClassInAssoc_c.getOneR_RGOOnR205(
											ClassAsSimpleFormalizer_c.getOneR_FORMOnR208(
													SimpleAssociation_c.getOneR_SIMPOnR206(rel))))));
			for (Attribute_c refID : refIDs) {
				if (1 != refID.getPfx_mode()){
					assertFalse("Prefix mode for referential attribute did not get set.", true);
				}
				if (!refID.getPrefix().equalsIgnoreCase(prefix1)){
					assertFalse("Prefix value for referential attribute did not get set.", true);
				}

			}
		}else if (LinkedAssociation_c.getManyR_ASSOCsOnR206(rel) != null){
			Attribute_c[] refIDs = Attribute_c.getManyO_ATTRsOnR106(
					ReferentialAttribute_c.getManyO_RATTRsOnR108(
							AttributeReferenceInClass_c.getManyO_REFsOnR111(
									ReferringClassInAssoc_c.getOneR_RGOOnR205(
											ClassAsLink_c.getOneR_ASSROnR211(
													LinkedAssociation_c.getOneR_ASSOCOnR206(rel))))));
			for (Attribute_c refID : refIDs) {
				if ( !prefix1.equalsIgnoreCase("")){
					if (refID.getPrefix().equalsIgnoreCase(prefix1)){
						if (1 != refID.getPfx_mode()){
							assertFalse("Prefix mode for referential attribute did not get set.", true);
						}
						continue;
					}
				}
				if ( !prefix2.equalsIgnoreCase("")){
					if(refID.getPrefix().equalsIgnoreCase(prefix2)){
						if (1 != refID.getPfx_mode()){
							assertFalse("Prefix mode for referential attribute did not get set.", true);
						}
						continue;
					}
				}
			}
			
		}else{
			assertFalse(true);
		}
		
	}
	private void verifyRefAttrDatatype(GraphicalElement_c ge2) {
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			Attribute_c[] attr_set = Attribute_c
					.getManyO_ATTRsOnR106(ReferentialAttribute_c.getManyO_RATTRsOnR108(AttributeReferenceInClass_c
							.getManyO_REFsOnR111(ReferringClassInAssoc_c
									.getManyR_RGOsOnR203(ClassInAssociation_c
											.getManyR_OIRsOnR201(assoc)))));
			UUID expectedId = getSameAsBaseAttributeUUID(Ooaofooa
					.getDefaultInstance());
			for (int i = 0; i < attr_set.length; ++i) {
				// 524296 is the old id for same_as<Base_Attribute>
				assertEquals(expectedId, attr_set[i].getDt_id());
			}
		} else if (ge2.getRepresents() instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) ge2.getRepresents();
			Attribute_c[] attr_set = Attribute_c
					.getManyO_ATTRsOnR106(ReferentialAttribute_c.getManyO_RATTRsOnR108(AttributeReferenceInClass_c
							.getManyO_REFsOnR111(ReferringClassInAssoc_c
									.getOneR_RGOOnR205(cal))));
			UUID expectedId = getSameAsBaseAttributeUUID(Ooaofooa
					.getDefaultInstance());
			for (int i = 0; i < attr_set.length; ++i) {
				// 524296 is the old id for same_as<Base_Attribute>
				assertEquals(expectedId, attr_set[i].getDt_id());
			}
		}
	}
	public void setGenerateResults() {
		try {
			generateResults = true;
			this.setUp();
			this.testFormalizeSimpleAssocOBJ_OBJ();
			this.testFormalizeSimpleAssocOBJ_IOBJ();
			this.testFormalizeSimpleAssocReflexiveOBJ();
			this.testFormalizeSimpleAssocIOBJ_OBJ();
			this.testFormalizeSimpleAssocIOBJ_IOBJ();
			this.testFormalizeSimpleAssocReflexiveIOBJ();
			this.testFormalizeASSROBJ_OBJOBJ();
			this.testFormalizeASSROBJ_IOBJOBJ();
			this.testFormalizeASSROBJ_IOBJIOBJ();
			this.testFormalizeASSRReflexiveOBJ_OBJ();
			this.testFormalizeASSRReflexiveOBJ_IOBJ();
			this.testFormalizeASSRIOBJ_OBJOBJ();
			this.testFormalizeASSRIOBJ_OBJIOBJ();
			this.testFormalizeASSRIOBJ_IOBJIOBJ();
			this.testFormalizeASSRReflexiveIOBJ_OBJ();
			this.testFormalizeASSRReflexiveIOBJ_IOBJ();
			this.testUnFormalizeSimpleAssocOBJ_OBJ();
			this.testUnFormalizeSimpleAssocOBJ_IOBJ();
			this.testUnFormalizeSimpleAssocReflexiveOBJ();
			this.testUnFormalizeSimpleAssocIOBJ_OBJ();
			this.testUnFormalizeSimpleAssocIOBJ_IOBJ();
			this.testUnFormalizeSimpleAssocReflexiveIOBJ();
			this.testUnFormalizeASSROBJ_OBJOBJ();
			this.testUnFormalizeASSROBJ_IOBJOBJ();
			this.testUnFormalizeASSROBJ_IOBJIOBJ();
			this.testUnFormalizeASSRReflexiveOBJ_OBJ();
			this.testUnFormalizeASSRReflexiveOBJ_IOBJ();
			this.testUnFormalizeASSRIOBJ_OBJOBJ();
			this.testUnFormalizeASSRIOBJ_OBJIOBJ();
			this.testUnFormalizeASSRIOBJ_IOBJIOBJ();
			this.testUnFormalizeASSRReflexiveIOBJ_OBJ();
			this.testUnFormalizeASSRReflexiveIOBJ_IOBJ();
			this.testFormalizeASSROBJ_OBJOBJSpecial();
			this.testFormalizeASSROBJ_IOBJOBJSpecial();
			this.testFormalizeASSROBJ_IOBJIOBJSpecial();
			this.testFormalizeASSRReflexiveOBJ_OBJSpecial();
			this.testFormalizeASSRReflexiveOBJ_IOBJSpecial();
			this.testFormalizeASSRIOBJ_OBJOBJSpecial();
			this.testFormalizeASSRIOBJ_OBJIOBJSpecial();
			this.testFormalizeASSRIOBJ_IOBJIOBJSpecial();
			this.testFormalizeASSRReflexiveIOBJ_OBJSpecial();
			this.testFormalizeASSRReflexiveIOBJ_IOBJSpecial();
			this.testUnFormalizeASSROBJ_OBJOBJSpecial();
			this.testUnFormalizeASSROBJ_IOBJOBJSpecial();
			this.testUnFormalizeASSROBJ_IOBJIOBJSpecial();
			this.testUnFormalizeASSRReflexiveOBJ_OBJSpecial();
			this.testUnFormalizeASSRReflexiveOBJ_IOBJSpecial();
			this.testUnFormalizeASSRIOBJ_OBJOBJSpecial();
			this.testUnFormalizeASSRIOBJ_OBJIOBJSpecial();
			this.testUnFormalizeASSRIOBJ_IOBJIOBJSpecial();
			this.testUnFormalizeASSRReflexiveIOBJ_OBJSpecial();
			this.testUnFormalizeASSRReflexiveIOBJ_IOBJSpecial();
			this.testUnformalizeSimpleAssocUsedRefOBJ_OBJ();
			this.testUnformalizeSimpleAssocUsedRefOBJ_IOBJ();
			this.testUnformalizeSimpleAssocReflexiveUsedRefOBJ_OBJ();
			this.testUnformalizeSimpleAssocUsedRefIOBJ_OBJ();
			this.testUnformalizeSimpleAssocUsedRefIOBJ_IOBJ();
			this.testUnformalizeSimpleAssocReflexiveUsedRefIOBJ();
			this.testUnformalizeASSROBJ_OBJOBJUsedRef();
			this.testUnformalizeASSROBJ_OBJIOBJUsedRef();
			this.testUnformalizeASSROBJ_IOBJIOBJUsedRef();
			this.testUnformalizeASSRReflexiveOBJ_OBJUsedRef();
			this.testUnformalizeASSRReflexiveOBJ_IOBJUsedRef();
			this.testUnformalizeASSRIOBJ_OBJOBJUsedRef();
			this.testUnformalizeASSRIOBJ_IOBJOBJUsedRef();
			this.testUnformalizeASSRIOBJ_IOBJIOBJUsedRef();
			this.testUnformalizeASSRReflexiveIOBJ_OBJUsedRef();
			this.testUnformalizeASSRReflexiveIOBJ_IOBJUsedRef();
			this.testUnformalizeSUBSUPUsedRefOBJ_OBJOBJ();
			this.testUnformalizeSUBSUPUsedRefOBJ_IOBJOBJ();
			this.testUnformalizeSUBSUPUsedRefOBJ_IOBJIOBJ();
			this.testUnformalizeSUBSUPUsedRefIOBJ_OBJOBJ();
			this.testUnformalizeSUBSUPUsedRefIOBJ_OBJIOBJ();
			this.testUnformalizeSUBSUPUsedRefIOBJ_IOBJIOBJ();
		} catch (Exception e) {
			System.out.println("Exception encountered by test result creator: "
					+ e);
		}

	}
	public void testFormalizeSimpleAssocOBJ_OBJ() {
		test_id = "test_1";
		String prefix1 = "pre1";
		openTestPKGDiagram("FSA Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeSimpleAssoc OBJ_OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		BinaryFormalizeOnR_RELAction fa = (BinaryFormalizeOnR_RELAction) new BinaryFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_BinaryFormalize(structuredSelection);
		BinaryFormalizeOnR_RELWizardPage1 page1 = (BinaryFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		assertEquals("Class", page1.Non_formalizerLabel.getText());
		BinaryFormalizeOnR_RELWizardPage2 page2 = (BinaryFormalizeOnR_RELWizardPage2) page1
				.getNextPage();
		String strings[] = page1.Non_formalizerCombo.getItems();
		for (int i = 0; i < strings.length; ++i) {
			if (strings[i].equals("FormalizeSimpleAssoc OBJ_OBJ")) {
				page1.Non_formalizerCombo.select(i);
			}
		}
		page1.updateSelectedNon_formalizer();
		page2.onPageEntry();
		IWizard w = page1.getWizard();
		BinaryFormalizeOnR_RELWizard binaryWizard = null;
		if (w instanceof BinaryFormalizeOnR_RELWizard)
			binaryWizard = (BinaryFormalizeOnR_RELWizard) w;
		binaryWizard.v_IdentifierPrefix = prefix1;
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FSA Tests", prefix1, null);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeSimpleAssocOBJ_IOBJ() {
		test_id = "test_2";
		String prefix1 = "pre1";
		openTestPKGDiagram("FSA Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeSimpleAssoc OBJ_IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		BinaryFormalizeOnR_RELAction fa = (BinaryFormalizeOnR_RELAction) new BinaryFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_BinaryFormalize(structuredSelection);
		BinaryFormalizeOnR_RELWizardPage1 page1 = (BinaryFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		assertEquals("Class", page1.Non_formalizerLabel.getText());
		BinaryFormalizeOnR_RELWizardPage2 page2 = (BinaryFormalizeOnR_RELWizardPage2) page1
				.getNextPage();
		String strings[] = page1.Non_formalizerCombo.getItems();
		for (int i = 0; i < strings.length; ++i) {
			if (strings[i].equals("FormalizeSimpleAssoc OBJ_IOBJ")) {
				page1.Non_formalizerCombo.select(i);
			}
		}
		page1.updateSelectedNon_formalizer();
		page2.onPageEntry();
		IWizard w = page1.getWizard();
		BinaryFormalizeOnR_RELWizard binaryWizard = null;
		if (w instanceof BinaryFormalizeOnR_RELWizard)
			binaryWizard = (BinaryFormalizeOnR_RELWizard) w;
		binaryWizard.v_IdentifierPrefix = prefix1;
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FSA Tests", prefix1, null);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeSimpleAssocReflexiveOBJ() {
		test_id = "test_3";
		String prefix1 = "pre1";
		openTestPKGDiagram("FSA Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeSimpleAssocReflexive OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		BinaryFormalizeOnR_RELAction fa = (BinaryFormalizeOnR_RELAction) new BinaryFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_BinaryFormalize(structuredSelection);
		BinaryFormalizeOnR_RELWizardPage1 page1 = (BinaryFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		assertEquals("Direction", page1.Non_formalizerLabel.getText());
		BinaryFormalizeOnR_RELWizardPage2 page2 = (BinaryFormalizeOnR_RELWizardPage2) page1
				.getNextPage();
		String strings[] = page1.Non_formalizerCombo.getItems();
		for (int i = 0; i < strings.length; ++i) {
			if (strings[i].equals("FormalizeSimpleAssocReflexive OBJ  ''")) {
				page1.Non_formalizerCombo.select(i);
			}
		}
		page1.updateSelectedNon_formalizer();
		page2.onPageEntry();
		IWizard w = page1.getWizard();
		BinaryFormalizeOnR_RELWizard binaryWizard = null;
		if (w instanceof BinaryFormalizeOnR_RELWizard)
			binaryWizard = (BinaryFormalizeOnR_RELWizard) w;
		binaryWizard.v_IdentifierPrefix = prefix1;
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FSA Tests", prefix1, null);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeSimpleAssocIOBJ_OBJ() {
		test_id = "test_4";
		String prefix1 = "pre1";
		openTestPKGDiagram("FSA Tests");
		Shape_c shp = getModelIClassShape("FormalizeSimpleAssoc IOBJ_OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		BinaryFormalizeOnR_RELAction fa = (BinaryFormalizeOnR_RELAction) new BinaryFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_BinaryFormalize(structuredSelection);
		BinaryFormalizeOnR_RELWizardPage1 page1 = (BinaryFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		assertEquals("Class", page1.Non_formalizerLabel.getText());
		BinaryFormalizeOnR_RELWizardPage2 page2 = (BinaryFormalizeOnR_RELWizardPage2) page1
				.getNextPage();
		String strings[] = page1.Non_formalizerCombo.getItems();
		for (int i = 0; i < strings.length; ++i) {
			if (strings[i].equals("FormalizeSimpleAssoc IOBJ_OBJ")) {
				page1.Non_formalizerCombo.select(i);
			}
		}
		page1.updateSelectedNon_formalizer();
		page2.onPageEntry();
		IWizard w = page1.getWizard();
		BinaryFormalizeOnR_RELWizard binaryWizard = null;
		if (w instanceof BinaryFormalizeOnR_RELWizard)
			binaryWizard = (BinaryFormalizeOnR_RELWizard) w;
		binaryWizard.v_IdentifierPrefix = prefix1;
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FSA Tests", prefix1, null);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeSimpleAssocIOBJ_IOBJ() {
		test_id = "test_5";
		String prefix1 = "pre1";
		openTestPKGDiagram("FSA Tests");
		Shape_c shp = getModelIClassShape("FormalizeSimpleAssoc IOBJ_IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		BinaryFormalizeOnR_RELAction fa = (BinaryFormalizeOnR_RELAction) new BinaryFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_BinaryFormalize(structuredSelection);
		BinaryFormalizeOnR_RELWizardPage1 page1 = (BinaryFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		assertEquals("Class", page1.Non_formalizerLabel.getText());
		BinaryFormalizeOnR_RELWizardPage2 page2 = (BinaryFormalizeOnR_RELWizardPage2) page1
				.getNextPage();
		String strings[] = page1.Non_formalizerCombo.getItems();
		for (int i = 0; i < strings.length; ++i) {
			if (strings[i].equals("FormalizeSimpleAssoc IOBJ_IOBJ")) {
				page1.Non_formalizerCombo.select(i);
			}
		}
		page1.updateSelectedNon_formalizer();
		page2.onPageEntry();
		IWizard w = page1.getWizard();
		BinaryFormalizeOnR_RELWizard binaryWizard = null;
		if (w instanceof BinaryFormalizeOnR_RELWizard)
			binaryWizard = (BinaryFormalizeOnR_RELWizard) w;
		binaryWizard.v_IdentifierPrefix = prefix1;
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FSA Tests", prefix1, null);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeSimpleAssocReflexiveIOBJ() {
		test_id = "test_6";
		String prefix1 = "pre1";
		openTestPKGDiagram("FSA Tests");
		Shape_c shp = getModelIClassShape("FormalizeSimpleAssocReflexive IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		BinaryFormalizeOnR_RELAction fa = (BinaryFormalizeOnR_RELAction) new BinaryFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_BinaryFormalize(structuredSelection);
		BinaryFormalizeOnR_RELWizardPage1 page1 = (BinaryFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		assertEquals("Direction", page1.Non_formalizerLabel.getText());
		BinaryFormalizeOnR_RELWizardPage2 page2 = (BinaryFormalizeOnR_RELWizardPage2) page1
				.getNextPage();
		String strings[] = page1.Non_formalizerCombo.getItems();
		for (int i = 0; i < strings.length; ++i) {
			if (strings[i].equals("FormalizeSimpleAssocReflexive IOBJ  ''")) {
				page1.Non_formalizerCombo.select(i);
			}
		}
		page1.updateSelectedNon_formalizer();
		page2.onPageEntry();
		IWizard w = page1.getWizard();
		BinaryFormalizeOnR_RELWizard binaryWizard = null;
		if (w instanceof BinaryFormalizeOnR_RELWizard)
			binaryWizard = (BinaryFormalizeOnR_RELWizard) w;
		binaryWizard.v_IdentifierPrefix = prefix1;
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FSA Tests", prefix1, null);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSROBJ_OBJOBJ() {
		test_id = "test_7";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSR OBJ_OBJ OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassOtherSide 1\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassOtherSide 2\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}

	public void testFormalizeASSROBJ_OBJOBJ_OnePrefix() {
		test_id = "test_7_OnePrefix";
		String prefix1 = "pre1";
		String prefix2 = "";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSR OBJ_OBJ OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassOtherSide 1\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassOtherSide 2\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}

	public void testFormalizeASSROBJ_IOBJOBJ() {
		test_id = "test_8";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSR OBJ_IOBJ OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassOtherSide 5\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassImp 1\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSROBJ_IOBJOBJ_OnePrefix() {
		test_id = "test_8_OnePrefix";
		String prefix1 = "pre1";
		String prefix2 = "";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSR OBJ_IOBJ OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassOtherSide 5\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassImp 1\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSROBJ_IOBJIOBJ() {
		test_id = "test_9";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSR OBJ_IOBJ IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassImp 2\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassImp 3\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSROBJ_IOBJIOBJ_OnePrefix() {
		test_id = "test_9_OnePrefix";
		String prefix1 = "";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSR OBJ_IOBJ IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassImp 2\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassImp 3\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRReflexiveOBJ_OBJ() {
		test_id = "test_10";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSRReflexive OBJ_OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"FormalizeASSRReflexive OBJ_OBJ\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"FormalizeASSRReflexive OBJ_OBJ\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRReflexiveOBJ_OBJ_OnePrefix() {
		test_id = "test_10_OnePrefix";
		String prefix1 = "pre1";
		String prefix2 = "";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSRReflexive OBJ_OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"FormalizeASSRReflexive OBJ_OBJ\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"FormalizeASSRReflexive OBJ_OBJ\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRReflexiveOBJ_IOBJ() {
		test_id = "test_11";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSRReflexive OBJ_IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"FormalizeASSRReflexive OBJ_IOBJ\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"FormalizeASSRReflexive OBJ_IOBJ\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRReflexiveOBJ_IOBJ_OnePrefix() {
		test_id = "test_11_OnePrefix";
		String prefix1 = "";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSRReflexive OBJ_IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"FormalizeASSRReflexive OBJ_IOBJ\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"FormalizeASSRReflexive OBJ_IOBJ\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRIOBJ_OBJOBJ() {
		test_id = "test_12";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSR IOBJ_OBJ OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassOtherSide 3\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassOtherSide 4\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRIOBJ_OBJOBJ_OnePrefix() {
		test_id = "test_12_OnePrefix";
		String prefix1 = "pre1";
		String prefix2 = "";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSR IOBJ_OBJ OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassOtherSide 3\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassOtherSide 4\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRIOBJ_OBJIOBJ() {
		test_id = "test_13";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSR IOBJ_OBJ IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassImp 4\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassOtherSide 8\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRIOBJ_OBJIOBJ_OnePrefix() {
		test_id = "test_13_OnePrefix";
		String prefix1 = "";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSR IOBJ_OBJ IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassImp 4\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassOtherSide 8\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRIOBJ_IOBJIOBJ() {
		test_id = "test_14";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSR IOBJ_IOBJ IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassImp 5\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassImp 6\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRIOBJ_IOBJIOBJ_OnePrefix() {
		test_id = "test_14_OnePrefix";
		String prefix1 = "pre1";
		String prefix2 = "";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSR IOBJ_IOBJ IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassImp 5\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassImp 6\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRReflexiveIOBJ_OBJ() {
		test_id = "test_15";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSRReflexive IOBJ_OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"FormalizeASSRReflexive IOBJ_OBJ\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"FormalizeASSRReflexive IOBJ_OBJ\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRReflexiveIOBJ_OBJ_OnePrefix() {
		test_id = "test_15_OnePrefix";
		String prefix1 = "";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSRReflexive IOBJ_OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"FormalizeASSRReflexive IOBJ_OBJ\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"FormalizeASSRReflexive IOBJ_OBJ\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRReflexiveIOBJ_IOBJ() {
		test_id = "test_16";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSRReflexive IOBJ_IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"FormalizeASSRReflexive IOBJ_IOBJ\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"FormalizeASSRReflexive IOBJ_IOBJ\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRReflexiveIOBJ_IOBJ_OnePrefix() {
		test_id = "test_16_OnePrefix";
		String prefix1 = "";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSRReflexive IOBJ_IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof ClassAsLink_c) {
			Cl_c.Clearselection();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR23(Graphelement_c
							.getOneDIM_GEOnR311(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
		LinkedFormalizeOnR_RELAction fa = (LinkedFormalizeOnR_RELAction) new LinkedFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_REL_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_RELWizardPage1 page1 = (LinkedFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_RELWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_RELWizard) {
			linkedWizard = (LinkedFormalizeOnR_RELWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"FormalizeASSRReflexive IOBJ_IOBJ\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"FormalizeASSRReflexive IOBJ_IOBJ\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeSimpleAssocOBJ_OBJ() {
		test_id = "test_23";
		openTestPKGDiagram("FSA Tests");
		ModelClass_c mc = getModelClass("FormalizeSimpleAssoc OBJ_OBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeSimpleAssocOBJ_IOBJ() {
		test_id = "test_24";
		openTestPKGDiagram("FSA Tests");
		ModelClass_c mc = getModelClass("FormalizeSimpleAssoc OBJ_IOBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeSimpleAssocReflexiveOBJ() {
		test_id = "test_25";
		openTestPKGDiagram("FSA Tests");
		ModelClass_c mc = getModelClass("FormalizeSimpleAssocReflexive OBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeSimpleAssocIOBJ_OBJ() {
		test_id = "test_26";
		openTestPKGDiagram("FSA Tests");
		ModelClass_c mc = getModelClass("FormalizeSimpleAssoc IOBJ_OBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeSimpleAssocIOBJ_IOBJ() {
		test_id = "test_27";
		openTestPKGDiagram("FSA Tests");
		ModelClass_c mc = getModelClass("FormalizeSimpleAssoc IOBJ_IOBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeSimpleAssocReflexiveIOBJ() {
		test_id = "test_28";
		openTestPKGDiagram("FSA Tests");
		ModelClass_c mc = getModelClass("FormalizeSimpleAssocReflexive IOBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSROBJ_OBJOBJ() {
		test_id = "test_29";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSR OBJ_OBJ OBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSROBJ_IOBJOBJ() {
		test_id = "test_30";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSR OBJ_IOBJ OBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSROBJ_IOBJIOBJ() {
		test_id = "test_31";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSR OBJ_IOBJ IOBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSRReflexiveOBJ_OBJ() {
		test_id = "test_32";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSRReflexive OBJ_OBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSRReflexiveOBJ_IOBJ() {
		test_id = "test_33";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSRReflexive OBJ_IOBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSRIOBJ_OBJOBJ() {
		test_id = "test_34";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSR IOBJ_OBJ OBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSRIOBJ_OBJIOBJ() {
		test_id = "test_35";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSR IOBJ_OBJ IOBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSRIOBJ_IOBJIOBJ() {
		test_id = "test_36";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSR IOBJ_IOBJ IOBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSRReflexiveIOBJ_OBJ() {
		test_id = "test_37";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSRReflexive IOBJ_OBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSRReflexiveIOBJ_IOBJ() {
		test_id = "test_38";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSRReflexive IOBJ_IOBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSROBJ_OBJOBJSpecial() {
		test_id = "test_Special_7";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSR OBJ_OBJ OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassOtherSide 1\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassOtherSide 2\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSROBJ_OBJOBJSpecial_OnePrefix() {
		test_id = "test_Special_7_OnePrefix";
		String prefix1 = "pre1";
		String prefix2 = "";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSR OBJ_OBJ OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassOtherSide 1\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassOtherSide 2\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSROBJ_IOBJOBJSpecial() {
		test_id = "test_Special_8";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSR OBJ_IOBJ OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassOtherSide 5\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassImp 1\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSROBJ_IOBJOBJSpecial_OnePrefix() {
		test_id = "test_Special_8_OnePrefix";
		String prefix1 = "";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSR OBJ_IOBJ OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassOtherSide 5\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassImp 1\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSROBJ_IOBJIOBJSpecial() {
		test_id = "test_Special_9";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSR OBJ_IOBJ IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassImp 2\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassImp 3\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSROBJ_IOBJIOBJSpecial_OnePrefix() {
		test_id = "test_Special_9_OnePrefix";
		String prefix1 = "pre1";
		String prefix2 = "";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSR OBJ_IOBJ IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassImp 2\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassImp 3\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRReflexiveOBJ_OBJSpecial() {
		test_id = "test_Special_10";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSRReflexive OBJ_OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"FormalizeASSRReflexive OBJ_OBJ\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"FormalizeASSRReflexive OBJ_OBJ\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRReflexiveOBJ_OBJSpecial_OnePrefix() {
		test_id = "test_Special_10_OnePrefix";
		String prefix1 = "";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSRReflexive OBJ_OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"FormalizeASSRReflexive OBJ_OBJ\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"FormalizeASSRReflexive OBJ_OBJ\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRReflexiveOBJ_IOBJSpecial() {
		test_id = "test_Special_11";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSRReflexive OBJ_IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"FormalizeASSRReflexive OBJ_IOBJ\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"FormalizeASSRReflexive OBJ_IOBJ\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRReflexiveOBJ_IOBJSpecial_OnePrefix() {
		test_id = "test_Special_11_OnePrefix";
		String prefix1 = "pre1";
		String prefix2 = "";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot,
				"FormalizeASSRReflexive OBJ_IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"FormalizeASSRReflexive OBJ_IOBJ\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"FormalizeASSRReflexive OBJ_IOBJ\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRIOBJ_OBJOBJSpecial() {
		test_id = "test_Special_12";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSR IOBJ_OBJ OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassOtherSide 3\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassOtherSide 4\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRIOBJ_OBJOBJSpecial_OnePrefix() {
		test_id = "test_Special_12_OnePrefix";
		String prefix1 = "";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSR IOBJ_OBJ OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassOtherSide 3\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassOtherSide 4\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRIOBJ_OBJIOBJSpecial() {
		test_id = "test_Special_13";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSR IOBJ_OBJ IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassImp 4\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassOtherSide 8\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRIOBJ_OBJIOBJSpecial_OnePrefix() {
		test_id = "test_Special_13_OnePrefix";
		String prefix1 = "pre1";
		String prefix2 = "";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSR IOBJ_OBJ IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassImp 4\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassOtherSide 8\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRIOBJ_IOBJIOBJSpecial() {
		test_id = "test_Special_14";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSR IOBJ_IOBJ IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassImp 5\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassImp 6\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRIOBJ_IOBJIOBJSpecial_OnePrefix() {
		test_id = "test_Special_14_OnePrefix";
		String prefix1 = "";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSR IOBJ_IOBJ IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) obj;
			rel = Association_c.getOneR_RELOnR206(LinkedAssociation_c
					.getOneR_ASSOCOnR211(cal));
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"TestClassImp 5\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"TestClassImp 6\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRReflexiveIOBJ_OBJSpecial() {
		test_id = "test_Special_15";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSRReflexive IOBJ_OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"FormalizeASSRReflexive IOBJ_OBJ\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"FormalizeASSRReflexive IOBJ_OBJ\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRReflexiveIOBJ_OBJSpecial_OnePrefix() {
		test_id = "test_Special_15_OnePrefix";
		String prefix1 = "pre1";
		String prefix2 = "";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSRReflexive IOBJ_OBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"FormalizeASSRReflexive IOBJ_OBJ\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"FormalizeASSRReflexive IOBJ_OBJ\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRReflexiveIOBJ_IOBJSpecial() {
		test_id = "test_Special_16";
		String prefix1 = "pre1";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSRReflexive IOBJ_IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"FormalizeASSRReflexive IOBJ_IOBJ\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"FormalizeASSRReflexive IOBJ_IOBJ\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testFormalizeASSRReflexiveIOBJ_IOBJSpecial_OnePrefix() {
		test_id = "test_Special_16_OnePrefix";
		String prefix1 = "";
		String prefix2 = "pre2";
		openTestPKGDiagram("FAL Tests");
		Shape_c shp = getModelIClassShape("FormalizeASSRReflexive IOBJ_IOBJ");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		Association_c rel = null;
		Object obj = ge2.getRepresents();
		if (obj instanceof Association_c) {
			rel = (Association_c) obj;
		}
		selection.addToSelection(ge2.getRepresents());
		if (ge2.getRepresents() instanceof Association_c) {
			Association_c assoc = (Association_c) ge2.getRepresents();
			ClassAsLink_c link = ClassAsLink_c
					.getOneR_ASSROnR211(LinkedAssociation_c
							.getOneR_ASSOCOnR206(assoc));
			Cl_c.Clearselection();
			selection.addToSelection(link);
		}
		LinkedFormalizeOnR_ASSRAction fa = (LinkedFormalizeOnR_ASSRAction) new LinkedFormalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		WizardDialog wd = fa.R_ASSR_LinkedFormalize(structuredSelection);
		LinkedFormalizeOnR_ASSRWizardPage1 page1 = (LinkedFormalizeOnR_ASSRWizardPage1) wd
				.getCurrentPage();
		IWizard w = page1.getWizard();
		LinkedFormalizeOnR_ASSRWizard linkedWizard = null;
		if (w instanceof LinkedFormalizeOnR_ASSRWizard) {
			linkedWizard = (LinkedFormalizeOnR_ASSRWizard) w;
			linkedWizard.v_one_idPrefix = prefix1;
			linkedWizard.v_oth_idPrefix = prefix2;
		}

		assertEquals("of class \"FormalizeASSRReflexive IOBJ_IOBJ\"",
				page1.One_idPhrase.getText());
		assertEquals("of class \"FormalizeASSRReflexive IOBJ_IOBJ\"",
				page1.Oth_idPhrase.getText());
		page1.onPageEntry();
		w.performFinish();
		wd.close();
		verifyRefAttrDatatype(ge2);
		validatePrefix(rel, "FAL Tests", prefix1, prefix2);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSROBJ_OBJOBJSpecial() {
		// for special tests do not reuse the
		// existing result
		test_id = "test_Special_29";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSR OBJ_OBJ OBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		ClassAsLink_c link = ClassAsLink_c
				.getOneR_ASSROnR211(LinkedAssociation_c
						.getOneR_ASSOCOnR206(assoc));
		selection.addToSelection(link);
		UnformalizeOnR_ASSRAction ufa = new UnformalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSROBJ_IOBJOBJSpecial() {
		// for special tests do not reuse the
		// existing result
		test_id = "test_Special_30";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSR OBJ_IOBJ OBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		ClassAsLink_c link = ClassAsLink_c
				.getOneR_ASSROnR211(LinkedAssociation_c
						.getOneR_ASSOCOnR206(assoc));
		selection.addToSelection(link);
		UnformalizeOnR_ASSRAction ufa = new UnformalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSROBJ_IOBJIOBJSpecial() {
		// for special tests do not reuse the
		// existing result
		test_id = "test_Special_31";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSR OBJ_IOBJ IOBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		ClassAsLink_c link = ClassAsLink_c
				.getOneR_ASSROnR211(LinkedAssociation_c
						.getOneR_ASSOCOnR206(assoc));
		selection.addToSelection(link);
		UnformalizeOnR_ASSRAction ufa = new UnformalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSRReflexiveOBJ_OBJSpecial() {
		// for special tests do not reuse the
		// existing result
		test_id = "test_Special_32";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSRReflexive OBJ_OBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		ClassAsLink_c link = ClassAsLink_c
				.getOneR_ASSROnR211(LinkedAssociation_c
						.getOneR_ASSOCOnR206(assoc));
		selection.addToSelection(link);
		UnformalizeOnR_ASSRAction ufa = new UnformalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSRReflexiveOBJ_IOBJSpecial() {
		// for special tests do not reuse the
		// existing result
		test_id = "test_Special_33";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSRReflexive OBJ_IOBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		ClassAsLink_c link = ClassAsLink_c
				.getOneR_ASSROnR211(LinkedAssociation_c
						.getOneR_ASSOCOnR206(assoc));
		selection.addToSelection(link);
		UnformalizeOnR_ASSRAction ufa = new UnformalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSRIOBJ_OBJOBJSpecial() {
		// for special tests do not reuse the
		// existing result
		test_id = "test_Special_34";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSR IOBJ_OBJ OBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		ClassAsLink_c link = ClassAsLink_c
				.getOneR_ASSROnR211(LinkedAssociation_c
						.getOneR_ASSOCOnR206(assoc));
		selection.addToSelection(link);
		UnformalizeOnR_ASSRAction ufa = new UnformalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSRIOBJ_OBJIOBJSpecial() {
		// for special tests do not reuse the
		// existing result
		test_id = "test_Special_35";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSR IOBJ_OBJ IOBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		ClassAsLink_c link = ClassAsLink_c
				.getOneR_ASSROnR211(LinkedAssociation_c
						.getOneR_ASSOCOnR206(assoc));
		selection.addToSelection(link);
		UnformalizeOnR_ASSRAction ufa = new UnformalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSRIOBJ_IOBJIOBJSpecial() {
		// for special tests do not reuse the
		// existing result
		test_id = "test_Special_36";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSR IOBJ_IOBJ IOBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		ClassAsLink_c link = ClassAsLink_c
				.getOneR_ASSROnR211(LinkedAssociation_c
						.getOneR_ASSOCOnR206(assoc));
		selection.addToSelection(link);
		UnformalizeOnR_ASSRAction ufa = new UnformalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSRReflexiveIOBJ_OBJSpecial() {
		// for special tests do not reuse the
		// existing result
		test_id = "test_Special_37";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSRReflexive IOBJ_OBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		ClassAsLink_c link = ClassAsLink_c
				.getOneR_ASSROnR211(LinkedAssociation_c
						.getOneR_ASSOCOnR206(assoc));
		selection.addToSelection(link);
		UnformalizeOnR_ASSRAction ufa = new UnformalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnFormalizeASSRReflexiveIOBJ_IOBJSpecial() {
		// for special tests do not reuse the
		// existing result
		test_id = "test_Special_38";
		openTestPKGDiagram("FAL Tests");
		ModelClass_c mc = getModelClass("FormalizeASSRReflexive IOBJ_IOBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		ClassAsLink_c link = ClassAsLink_c
				.getOneR_ASSROnR211(LinkedAssociation_c
						.getOneR_ASSOCOnR206(assoc));
		selection.addToSelection(link);
		UnformalizeOnR_ASSRAction ufa = new UnformalizeOnR_ASSRAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeSimpleAssocUsedRefOBJ_OBJ() {
		test_id = "test_45";
		openTestPKGDiagram("USA Tests");
		ModelClass_c mc = getModelClass("UnformalizeSimpleAssocUsedRef OBJ_OBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeSimpleAssocUsedRefOBJ_IOBJ() {
		test_id = "test_46";
		openTestPKGDiagram("USA Tests");
		ModelClass_c mc = getModelClass("UnformalizeSimpleAssocUsedRef OBJ_IOBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeSimpleAssocReflexiveUsedRefOBJ_OBJ() {
		test_id = "test_47";
		openTestPKGDiagram("USA Tests");
		ModelClass_c mc = getModelClass("UnformalizeSimpleAssocReflexiveUsedRef OBJ_OBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeSimpleAssocUsedRefIOBJ_OBJ() {
		test_id = "test_48";
		openTestPKGDiagram("USA Tests");
		//		Shape_c shp = getModelIClassShape("UnformalizeSimpleAssocUsedRef IOBJ_OBJ");
		ModelClass_c mc = getModelClass("UnformalizeSimpleAssocUsedRef IOBJ_OBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeSimpleAssocUsedRefIOBJ_IOBJ() {
		test_id = "test_49";
		openTestPKGDiagram("USA Tests");
		//		Shape_c shp = getModelIClassShape("UnformalizeSimpleAssocUsedRef IOBJ_IOBJ");
		ModelClass_c mc = getModelClass("UnformalizeSimpleAssocUsedRef IOBJ_IOBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeSimpleAssocReflexiveUsedRefIOBJ() {
		test_id = "test_50";
		openTestPKGDiagram("USA Tests");
		//		Shape_c shp = getModelIClassShape("UnformalizeSimpleAssocReflexiveUsedRef IOBJ");
		ModelClass_c mc = getModelClass("UnformalizeSimpleAssocReflexiveUsedRef IOBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeASSROBJ_OBJOBJUsedRef() {
		test_id = "test_51";
		openTestPKGDiagram("UAL Tests");
		ModelClass_c mc = getModelClass("UnformalizeASSR OBJ_OBJ OBJ UsedRef");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeASSROBJ_OBJIOBJUsedRef() {
		test_id = "test_52";
		openTestPKGDiagram("UAL Tests");
		ModelClass_c mc = getModelClass("UnformalizeASSR OBJ_OBJ IOBJ UsedRef");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeASSROBJ_IOBJIOBJUsedRef() {
		test_id = "test_53";
		openTestPKGDiagram("UAL Tests");
		ModelClass_c mc = getModelClass("UnformalizeASSR OBJ_IOBJ IOBJ UsedRef");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeASSRReflexiveOBJ_OBJUsedRef() {
		test_id = "test_54";
		openTestPKGDiagram("UAL Tests");
		ModelClass_c mc = getModelClass("UnformalizeASSRReflexive OBJ_OBJ UsedRef");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeASSRReflexiveOBJ_IOBJUsedRef() {
		test_id = "test_55";
		openTestPKGDiagram("UAL Tests");
		ModelClass_c mc = getModelClass("UnformalizeASSRReflexive OBJ_IOBJ UsedRef");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeASSRIOBJ_OBJOBJUsedRef() {
		test_id = "test_56";
		openTestPKGDiagram("UAL Tests");
		//		Shape_c shp = getModelIClassShape("UnformalizeASSR IOBJ_OBJ OBJ UsedRef");
		ModelClass_c mc = getModelClass("UnformalizeASSR IOBJ_OBJ OBJ UsedRef");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeASSRIOBJ_IOBJOBJUsedRef() {
		test_id = "test_57";
		openTestPKGDiagram("UAL Tests");
		//		Shape_c shp = getModelIClassShape("UnformalizeASSR IOBJ_IOBJ OBJ UsedRef");
		ModelClass_c mc = getModelClass("UnformalizeASSR IOBJ_IOBJ OBJ UsedRef");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeASSRIOBJ_IOBJIOBJUsedRef() {
		test_id = "test_58";
		openTestPKGDiagram("UAL Tests");
		//		Shape_c shp = getModelIClassShape("UnformalizeASSR IOBJ_IOBJ IOBJ UsedRef");
		ModelClass_c mc = getModelClass("UnformalizeASSR IOBJ_IOBJ IOBJ UsedRef");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeASSRReflexiveIOBJ_OBJUsedRef() {
		test_id = "test_59";
		openTestPKGDiagram("UAL Tests");
		//		Shape_c shp = getModelIClassShape("UnformalizeASSRReflexive IOBJ_OBJ UsedRef");
		ModelClass_c mc = getModelClass("UnformalizeASSRReflexive IOBJ_OBJ UsedRef");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeASSRReflexiveIOBJ_IOBJUsedRef() {
		test_id = "test_60";
		openTestPKGDiagram("UAL Tests");
		//		Shape_c shp = getModelIClassShape("UnformalizeASSRReflexive IOBJ_IOBJ UsedRef");
		ModelClass_c mc = getModelClass("UnformalizeASSRReflexive IOBJ_IOBJ UsedRef");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeSUBSUPUsedRefOBJ_OBJOBJ() {
		test_id = "test_61";
		openTestPKGDiagram("USS Tests");
		ModelClass_c mc = getModelClass("UnformalizeSUBSUPUsedRef OBJ_OBJ OBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeSUBSUPUsedRefOBJ_IOBJOBJ() {
		test_id = "test_62";
		openTestPKGDiagram("USS Tests");
		ModelClass_c mc = getModelClass("UnformalizeSUBSUPUsedRef OBJ_IOBJ OBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeSUBSUPUsedRefOBJ_IOBJIOBJ() {
		test_id = "test_63";
		openTestPKGDiagram("USS Tests");
		ModelClass_c mc = getModelClass("UnformalizeSUBSUPUsedRef OBJ_IOBJ IOBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeSUBSUPUsedRefIOBJ_OBJOBJ() {
		test_id = "test_64";
		openTestPKGDiagram("USS Tests");
		//		Shape_c shp = getModelIClassShape("UnformalizeSUBSUPUsedRef IOBJ_OBJ OBJ");
		ModelClass_c mc = getModelClass("UnformalizeSUBSUPUsedRef IOBJ_OBJ OBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeSUBSUPUsedRefIOBJ_OBJIOBJ() {
		test_id = "test_65";
		openTestPKGDiagram("USS Tests");
		//		Shape_c shp = getModelIClassShape("UnformalizeSUBSUPUsedRef IOBJ_OBJ IOBJ");
		ModelClass_c mc = getModelClass("UnformalizeSUBSUPUsedRef IOBJ_OBJ IOBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}
	public void testUnformalizeSUBSUPUsedRefIOBJ_IOBJIOBJ() {
		test_id = "test_66";
		openTestPKGDiagram("USS Tests");
		//		Shape_c shp = getModelIClassShape("UnformalizeSUBSUPUsedRef IOBJ_IOBJ IOBJ");
		ModelClass_c mc = getModelClass("UnformalizeSUBSUPUsedRef IOBJ_IOBJ IOBJ");
		Association_c assoc = Association_c.getOneR_RELOnR201(mc,
				new getAssocByDescrip(""));
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Cl_c.Clearselection();
		selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {
		};
		ufa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);
		validateOrGenerateResults(ce, generateResults);
	}

	boolean simp_results[] = {false, false, false, true, false, true, false,
			true, true, false, false, false, false, false, false};

	boolean assoc_results[] = {false, false, false, false, false, false, false,
			false, false, false, false, false, true, false, false};

	boolean subsup_results[] = {false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, true};

	public void testFormalizeActionFilter() {
		Package_c uut = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("actionFilter Tests"));
		Association_c[] assoc_set = Association_c
				.getManyR_RELsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(uut));
		for (int i = 0; i < assoc_set.length; ++i) {
			String status = "Association R" + assoc_set[i].getNumb();
			assertEquals(status, simp_results[i],
					assoc_set[i].Actionfilter("type", "unform simp"));
			assertEquals(status, assoc_results[i],
					assoc_set[i].Actionfilter("type", "unform assoc"));
			assertEquals(status, subsup_results[i],
					assoc_set[i].Actionfilter("type", "unform subsup"));
		}
	}

}
