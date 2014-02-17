.//=======================================================================
.//
.// File:      $RCSfile: gen_Formalize_tests.arc,v $
.// Version:   $Revision: 1.27 $
.// Modified:  $Date: 2013/05/10 04:34:15 $
.//
.// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
.//
.//=======================================================================
.// Licensed under the Apache License, Version 2.0 (the "License"); you may not
.// use this file except in compliance with the License.  You may obtain a copy
.// of the License at
.//
.//      http://www.apache.org/licenses/LICENSE-2.0
.//
.// Unless required by applicable law or agreed to in writing, software
.// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
.// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
.// License for the specific language governing permissions and limitations under
.// the License.
.//=======================================================================
.//
.function formalize_test
  .param inst_ref class    .// O_OBJ
  .param boolean is_special
  .//
  .assign operand_kl = "R_REL"
  .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
  .if (class.Descrip == "Imported Class")
    .select one ic related by class->O_IOBJ[R101]
  	.select one ss related by ic->PE_PE[R8001]->EP_PKG[R8000]
  		openTestSSDiagram("${ss.Name}");
  		Shape_c shp = getModelIClassShape("${class.Name}");
  .else
    .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
		openTestSSDiagram("${ss.Name}");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot, "${class.Name}");
  .end if
  		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
  		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
        Connector_c con = Connector_c.getOneGD_CONOnR20(
            Graphedge_c.getOneDIM_EDOnR320(
                Graphconnector_c.getManyDIM_CONsOnR311(
                    Graphelement_c.getOneDIM_GEOnR23(ge))));
  		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);		
  		Cl_c.Clearselection();	
      	selection.addToSelection(ge2.getRepresents());
  .if (ss.Name == "FSA Tests")
		BinaryFormalizeOn${operand_kl}Action fa = (BinaryFormalizeOn${operand_kl}Action) new BinaryFormalizeOn${operand_kl}Action();
		Action a = new Action() {};
		fa.setActivePart(a, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance().getStructuredSelection();
		WizardDialog wd = fa.${operand_kl}_BinaryFormalize(structuredSelection);
		BinaryFormalizeOn${operand_kl}WizardPage1 page1 = (BinaryFormalizeOn${operand_kl}WizardPage1) wd.getCurrentPage();
        assertEquals ("Class", page1.Non_formalizerLabel.getText());
		BinaryFormalizeOn${operand_kl}WizardPage2 page2 = (BinaryFormalizeOn${operand_kl}WizardPage2) page1.getNextPage();
		String strings[] = page1.Non_formalizerCombo.getItems();
		for (int i = 0; i < strings.length; ++i) {
			if (strings[i].equals("${class.Name}")) {
				page1.Non_formalizerCombo.select(i);
			}
		}
		page1.updateSelectedNon_formalizer();
		page2.onPageEntry(); 
  .elif (ss.Name == "FSS Tests")
    .if ( is_special )
      .assign operand_kl = "R_SUB"
    .end if
    .assign wizard_page_num = "1"
    .if ( is_special )
      .// special means we execute this on a subtype line
  		if (ge2.getRepresents() instanceof Association_c) { 
            Association_c assoc = (Association_c)ge2.getRepresents();
            ClassAsSubtype_c sub = ClassAsSubtype_c.getOneR_SUBOnR213(
               SubtypeSupertypeAssociation_c.getOneR_SUBSUPOnR206(assoc));
  			Cl_c.Clearselection();	
			selection.addToSelection(sub);
		}
	.end if
  		InheritanceFormalizeOn${operand_kl}Action fa = (InheritanceFormalizeOn${operand_kl}Action) new InheritanceFormalizeOn${operand_kl}Action();
		Action a = new Action() {};
		fa.setActivePart(a, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance().getStructuredSelection();
		WizardDialog wd = fa.${operand_kl}_InheritanceFormalize(structuredSelection);
		InheritanceFormalizeOn${operand_kl}WizardPage${wizard_page_num} page1 = (InheritanceFormalizeOn${operand_kl}WizardPage${wizard_page_num}) wd.getCurrentPage();
  .elif (ss.Name == "FAL Tests")
    .if (is_special)
      .assign operand_kl = "R_ASSR"
    .end if
    .assign wizard_page_num = "1"
    .if ( not is_special )
      .// special means we execute this on the link line, which what we find
      .// the not special case has to find the main assoc line
  		if (ge2.getRepresents() instanceof ClassAsLink_c) { 
  			Cl_c.Clearselection();	
            GraphicalElement_c elem = 
                GraphicalElement_c.getOneGD_GEOnR23(
                    Graphelement_c.getOneDIM_GEOnR311(
                        Graphconnector_c.getOneDIM_CONOnR321(
                            Graphedge_c.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
	.else
  		if (ge2.getRepresents() instanceof Association_c) { 
            Association_c assoc = (Association_c)ge2.getRepresents();
            ClassAsLink_c link = ClassAsLink_c.getOneR_ASSROnR211(
               LinkedAssociation_c.getOneR_ASSOCOnR206(assoc));
  			Cl_c.Clearselection();	
			selection.addToSelection(link);
		}
	.end if
		LinkedFormalizeOn${operand_kl}Action fa = (LinkedFormalizeOn${operand_kl}Action) new LinkedFormalizeOn${operand_kl}Action();
		Action a = new Action() {};
		fa.setActivePart(a, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance().getStructuredSelection();
		WizardDialog wd = fa.${operand_kl}_LinkedFormalize(structuredSelection);
		LinkedFormalizeOn${operand_kl}WizardPage${wizard_page_num} page1 = (LinkedFormalizeOn${operand_kl}WizardPage${wizard_page_num}) wd.getCurrentPage();
    .select any oir related by class->R_OIR[R201]
    .select one rel related by oir->R_REL[R201]
    .select one aone_obj related by rel->R_ASSOC[R206]->R_AONE[R209]->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
    .select one aoth_obj related by rel->R_ASSOC[R206]->R_AOTH[R210]->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
        assertEquals( "${aone_obj.Name}", page1.One_idLabel.getText() );
        assertEquals( "${aoth_obj.Name}", page1.Oth_idLabel.getText() );
  .end if
  		page1.onPageEntry();
		IWizard w = page1.getWizard();		
		w.performFinish();  
		wd.close();
  		verifyRefAttrDatatype(ge2);
  .if (ss.Name == "FSS Tests")
  		if (ge2.getRepresents() instanceof Association_c) { 
			Association_c assoc = (Association_c)ge2.getRepresents();
	        ClassAsSupertype_c sup = ClassAsSupertype_c.getOneR_SUPEROnR212(
	                SubtypeSupertypeAssociation_c.getOneR_SUBSUPOnR206(assoc));
	        ModelClass_c mc = ModelClass_c.getOneO_OBJOnR201(ClassInAssociation_c.getOneR_OIROnR203(
	        		ReferredToClassInAssoc_c.getOneR_RTOOnR204(sup)));
	        ReferredToIdentifierAttribute_c [] rtida_set = ReferredToIdentifierAttribute_c.getManyO_RTIDAsOnR110(
	        		ClassIdentifierAttribute_c.getManyO_OIDAsOnR105(Attribute_c.getManyO_ATTRsOnR102(mc)));
    .if ( class.Name == "FormalizeSUBSUP OBJ_OBJ OBJ" )
      .// this class has two attributes for its identifier
	        assertEquals( 2, rtida_set.length );
    .else
	        assertEquals( 1, rtida_set.length );
    .end if
        }
  .end if 
		validateOrGenerateResults(ce, generateResults);
.end function
.//
.//=======================================================================
.//
.function unformalize_test
  .param inst_ref class    .// O_OBJ
  .param boolean is_special
  .//
  .assign operand_kl = "R_REL"
  .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
  .if (class.Descrip == "Imported Class")
    .select one ic related by class->O_IOBJ[R101]
  	.select one ss related by ic->PE_PE[R8001]->EP_PKG[R8000]
  		openTestSSDiagram("${ss.Name}");
		ModelClass_c mc=getModelClass("${class.Name}");
  		Association_c assoc = Association_c.getOneR_RELOnR201(mc, new getAssocByDescrip(""));
  .else
    .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
		openTestSSDiagram("${ss.Name}");
		ModelClass_c mc = getModelClass("${class.Name}");
  		Association_c assoc = Association_c.getOneR_RELOnR201(mc, new getAssocByDescrip(""));
  .end if
  		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
  		Cl_c.Clearselection();	
  .if ( is_special )
    .if (ss.Name == "FSS Tests")
      .assign operand_kl = "R_SUB"
        ClassAsSubtype_c sub = ClassAsSubtype_c.getOneR_SUBOnR213(
           SubtypeSupertypeAssociation_c.getOneR_SUBSUPOnR206(assoc));
        selection.addToSelection(sub);
    .elif (ss.Name == "FAL Tests")
      .assign operand_kl = "R_ASSR"
        ClassAsLink_c link = ClassAsLink_c.getOneR_ASSROnR211(
           LinkedAssociation_c.getOneR_ASSOCOnR206(assoc));
        selection.addToSelection(link);
    .end if
  .else
      	selection.addToSelection(assoc);
  .end if
		UnformalizeOn${operand_kl}Action ufa = new UnformalizeOn${operand_kl}Action();
		Action a = new Action() {};
		ufa.setActivePart(a, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);				
		validateOrGenerateResults(ce, generateResults);
.end function
.//
.//=======================================================================
.//  Main code
.//
//========================================================================
//
// File: FormalizeUnformalizeTest.java
//
// WARNING:      Do not edit this generated file
// Generated by: ${info.arch_file_name}
// Version:      $$Revision: 1.27 $$
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
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

public class FormalizeUnformalizeTest extends CanvasTest {

	String test_id = null;
	private static boolean generateResults = false;
    private boolean reloadModel = false;
	
	private static boolean firstTime=true;
	
    private static Selection selection = Selection.getInstance();

	public FormalizeUnformalizeTest(String name) {
		super("FormalizeUnformalizeTest", name);
	}

	protected String getResultName() {
		return "FormalizeUnformalize" + "_" + test_id;
	}	

    public void setReloadModel( boolean val ) {
        reloadModel = val;
    }
    
	protected void setUp() throws Exception {
		super.setUp();
		Display d = Display.getCurrent();
		while ( d.readAndDispatch() ) ;
        ensureAvailableAndLoaded("FormalizeUnformalizeTests", false, reloadModel);
		if(firstTime){
		m_bp_tree.expandAll();
		firstTime=false;
		}
		while ( d.readAndDispatch() ) ;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public class Subsystem_by_name_c implements ClassQueryInterface_c {
	public boolean evaluate(Object candidate) {
		Subsystem_c selected = (Subsystem_c) candidate;
		return (selected.getName().equals(m_name));
	}
	public Subsystem_by_name_c(String name) {
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
	ImportedClass_c ic =
		ImportedClass_c.ImportedClassInstance(modelRoot, new ImportedClass_by_name_c(name));
	GraphicalElement_c ge =
		GraphicalElement_c.GraphicalElementInstance(graphicsModelRoot,
			ctu.new findGraphicalElementByOOAID(ic.getIobj_id(), 23));
	return Shape_c.getOneGD_SHPOnR2(ge);
	}
	public void openTestSSDiagram(String title) {
		Subsystem_c uut = Subsystem_c.SubsystemInstance(modelRoot, new Subsystem_by_name_c(title));
		assertNotNull(uut);
		CanvasTestUtils.openCanvasEditor(uut);
	}
  	private void verifyRefAttrDatatype(GraphicalElement_c ge2) {
		if (ge2.getRepresents() instanceof Association_c) { 
	        Association_c assoc = (Association_c)ge2.getRepresents();
	        Attribute_c [] attr_set = Attribute_c.getManyO_ATTRsOnR106(
	                ReferentialAttribute_c.getManyO_RATTRsOnR108(
	                AttributeReferenceInClass_c.getManyO_REFsOnR111(
	                        ReferringClassInAssoc_c.getManyR_RGOsOnR203(
	                                ClassInAssociation_c.getManyR_OIRsOnR201(assoc)))));
	        UUID expectedId = getSameAsBaseAttributeUUID(assoc.getModelRoot());
	        for ( int i = 0; i < attr_set.length; ++ i )
	        {
	            // 524296 is the old id for same_as<Base_Attribute>
	            assertEquals( expectedId, attr_set[i].getDt_id());   
	        }
        }
        else if (ge2.getRepresents() instanceof ClassAsLink_c) { 
            ClassAsLink_c cal = (ClassAsLink_c)ge2.getRepresents();
            Attribute_c [] attr_set = Attribute_c.getManyO_ATTRsOnR106(
                    ReferentialAttribute_c.getManyO_RATTRsOnR108(
                    AttributeReferenceInClass_c.getManyO_REFsOnR111(
                            ReferringClassInAssoc_c.getOneR_RGOOnR205(cal))));
            UUID expectedId = getSameAsBaseAttributeUUID(cal.getModelRoot());
            for ( int i = 0; i < attr_set.length; ++ i )
            {
                // 524296 is the old id for same_as<Base_Attribute>
                assertEquals( expectedId, attr_set[i].getDt_id());   
            }
        }
	}
	public void setGenerateResults() {
		try {
			generateResults = true;
			this.setUp();
.select many classes from instances of O_OBJ where (selected.KEY_LETT == "T_FORM")
.for each class in classes
  			this.test$r{class.Name}();
.end for
.for each class in classes
			this.testUn$r{class.Name}();
.end for
.//
.// test formalize and unformalize on ClassAsLink and ClassAsSubtype
.// use new test result, as GEF auto-shifts scroll
.//
.for each class in classes
  .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
  .if (class.Descrip == "Imported Class")
    .select one ic related by class->O_IOBJ[R101]
  	.select one ss related by ic->PE_PE[R8001]->EP_PKG[R8000]
  .end if
  .if ((ss.Name == "FSS Tests") or (ss.Name == "FAL Tests"))
  	       this.test$r{class.Name}Special();
  .end if
.end for
.for each class in classes
  .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
  .if (class.Descrip == "Imported Class")
    .select one ic related by class->O_IOBJ[R101]
  	.select one ss related by ic->PE_PE[R8001]->EP_PKG[R8000]
  .end if
  .if ((ss.Name == "FSS Tests") or (ss.Name == "FAL Tests"))
  		   this.testUn$r{class.Name}Special();
  .end if
.end for
.select many classes from instances of O_OBJ where (selected.KEY_LETT == "T_UNFORM")
.for each class in classes
			this.test$r{class.Name}();
.end for
		} catch (Exception e) {
			System.out.println(
				"Exception encountered by test result creator: " + e);
		}

	}
.assign count = 0
.select many classes from instances of O_OBJ where (selected.KEY_LETT == "T_FORM")
.for each class in classes
  .assign count = count + 1
  	public void test$r{class.Name}() {
  		test_id = "test_${count}";
  .invoke ft = formalize_test( class, false )
${ft.body}\
	}
.end for
.for each class in classes
  .assign count = count + 1
  	public void testUn$r{class.Name}() {
  		test_id = "test_${count}";
  .invoke ft = unformalize_test( class, false )
${ft.body}\
	}
.end for
.//
.// test formalize and unformalize on ClassAsLink and ClassAsSubtype
.// use new test result, as GEF auto-shifts scroll
.//
.assign count = 0
.for each class in classes
  .assign count = count + 1
  .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
  .if (class.Descrip == "Imported Class")
    .select one ic related by class->O_IOBJ[R101]
  	.select one ss related by ic->PE_PE[R8001]->EP_PKG[R8000]
  .end if
  .if ((ss.Name == "FSS Tests") or (ss.Name == "FAL Tests"))
  	public void test$r{class.Name}Special() {
  		test_id = "test_Special_${count}";
    .invoke ft = formalize_test( class, true )
${ft.body}\
	}
  .end if
.end for
.for each class in classes
  .assign count = count + 1
  .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
  .if (class.Descrip == "Imported Class")
    .select one ic related by class->O_IOBJ[R101]
  	.select one ss related by ic->PE_PE[R8001]->EP_PKG[R8000]
  .end if
  .if ((ss.Name == "FSS Tests") or (ss.Name == "FAL Tests"))
  	public void testUn$r{class.Name}Special() {
  	    // for special tests do not reuse the
  	    // existing result
  		test_id = "test_Special_${count}";
    .invoke ft = unformalize_test( class, true )
${ft.body}\
	}
  .end if
.end for
.//
.select many classes from instances of O_OBJ where (selected.KEY_LETT == "T_UNFORM")
.for each class in classes
  .assign count = count + 1
  .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
  	public void test$r{class.Name}() {
  		test_id = "test_${count}";
  .if (class.Descrip == "Imported Class")
    .select one ic related by class->O_IOBJ[R101]
  	.select one ss related by ic->PE_PE[R8001]->EP_PKG[R8000]
  		openTestSSDiagram("${ss.Name}");
  		Shape_c shp = getModelIClassShape("${class.Name}");
		ModelClass_c mc = getModelClass("${class.Name}");
  		Association_c assoc = Association_c.getOneR_RELOnR201(mc, new getAssocByDescrip(""));
  .else
    .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
		openTestSSDiagram("${ss.Name}");
				ModelClass_c mc = getModelClass("${class.Name}");
  		Association_c assoc = Association_c.getOneR_RELOnR201(mc, new getAssocByDescrip(""));
  .end if
  		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
  		Cl_c.Clearselection();	
      	selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {};
		ufa.setActivePart(a, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);				
		validateOrGenerateResults(ce, generateResults);
	}
.end for

    boolean simp_results[] = {
        false, false, false, true, false, true, false, true, true, false, 
        false, false, false, 
        false, false };

    boolean assoc_results[] = {
        false, false, false, false, false, false, false, false, false, false, 
        false, false, true, 
        false, false };

    boolean subsup_results[] = {
        false, false, false, false, false, false, false, false, false, false, 
        false, false, false, 
        false, true };

    public void testFormalizeActionFilter() {
        Subsystem_c uut = Subsystem_c.SubsystemInstance(modelRoot, new Subsystem_by_name_c("actionFilter Tests"));
        Association_c [] assoc_set = Association_c.getManyR_RELsOnR4(uut);
        for ( int i = 0; i < assoc_set.length; ++i )
        {
            String status = "Association R" + assoc_set[i].getNumb();
            assertEquals( status, simp_results[i], assoc_set[i].Actionfilter("type", "unform simp"));
            assertEquals( status, assoc_results[i], assoc_set[i].Actionfilter("type", "unform assoc"));
            assertEquals( status, subsup_results[i], assoc_set[i].Actionfilter("type", "unform subsup"));
        }
    }

}    
.emit to file "src/com/mentor/nucleus/bp/core/test/FormalizeUnformalizeTest.java"

.//
.//=======================================================================
.//
.function formalize_test_generics
  .param inst_ref class    .// O_OBJ
  .param boolean is_special
  .//
  .assign operand_kl = "R_REL"
  .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
  .if (class.Descrip == "Imported Class")
    .select one ic related by class->O_IOBJ[R101]
  	.select one ss related by ic->PE_PE[R8001]->EP_PKG[R8000]
  		openTestPKGDiagram("${ss.Name}");
  		Shape_c shp = getModelIClassShape("${class.Name}");
  .else
    .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
		openTestPKGDiagram("${ss.Name}");
		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot, "${class.Name}");
  .end if
  		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
  		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
        Connector_c con = Connector_c.getOneGD_CONOnR20(
            Graphedge_c.getOneDIM_EDOnR320(
                Graphconnector_c.getManyDIM_CONsOnR311(
                    Graphelement_c.getOneDIM_GEOnR23(ge))));
  		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);		
  		Cl_c.Clearselection();	
      	selection.addToSelection(ge2.getRepresents());
  .if (ss.Name == "FSA Tests")
		BinaryFormalizeOn${operand_kl}Action fa = (BinaryFormalizeOn${operand_kl}Action) new BinaryFormalizeOn${operand_kl}Action();
		Action a = new Action() {};
		fa.setActivePart(a, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance().getStructuredSelection();
		WizardDialog wd = fa.${operand_kl}_BinaryFormalize(structuredSelection);
		BinaryFormalizeOn${operand_kl}WizardPage1 page1 = (BinaryFormalizeOn${operand_kl}WizardPage1) wd.getCurrentPage();
		BinaryFormalizeOn${operand_kl}WizardPage2 page2 = (BinaryFormalizeOn${operand_kl}WizardPage2) page1.getNextPage();
		String strings[] = page1.Non_formalizerCombo.getItems();
		boolean selected = false;
		for (int i = 0; i < strings.length; ++i) {
			if (strings[i].equals("${class.Name}")) {
				page1.Non_formalizerCombo.select(i);
				selected = true;
			}
		}
		if (!selected){
			page1.Non_formalizerCombo.select(0);
		}
		page1.updateSelectedNon_formalizer();
		page2.onPageEntry(); 
  .elif (ss.Name == "FSS Tests")
    .if ( is_special )
      .assign operand_kl = "R_SUB"
    .end if
    .assign wizard_page_num = "1"
    .if ( is_special )
      .// special means we execute this on a subtype line
  		if (ge2.getRepresents() instanceof Association_c) { 
            Association_c assoc = (Association_c)ge2.getRepresents();
            ClassAsSubtype_c sub = ClassAsSubtype_c.getOneR_SUBOnR213(
               SubtypeSupertypeAssociation_c.getOneR_SUBSUPOnR206(assoc));
  			Cl_c.Clearselection();	
			selection.addToSelection(sub);
		}
	.end if
  		InheritanceFormalizeOn${operand_kl}Action fa = (InheritanceFormalizeOn${operand_kl}Action) new InheritanceFormalizeOn${operand_kl}Action();
		Action a = new Action() {};
		fa.setActivePart(a, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance().getStructuredSelection();
		WizardDialog wd = fa.${operand_kl}_InheritanceFormalize(structuredSelection);
		InheritanceFormalizeOn${operand_kl}WizardPage${wizard_page_num} page1 = (InheritanceFormalizeOn${operand_kl}WizardPage${wizard_page_num}) wd.getCurrentPage();
  .elif (ss.Name == "FAL Tests")
    .if (is_special)
      .assign operand_kl = "R_ASSR"
    .end if
    .assign wizard_page_num = "1"
    .if ( not is_special )
      .// special means we execute this on the link line, which what we find
      .// the not special case has to find the main assoc line
  		if (ge2.getRepresents() instanceof ClassAsLink_c) { 
  			Cl_c.Clearselection();	
            GraphicalElement_c elem = 
                GraphicalElement_c.getOneGD_GEOnR23(
                    Graphelement_c.getOneDIM_GEOnR311(
                        Graphconnector_c.getOneDIM_CONOnR321(
                            Graphedge_c.getOneDIM_EDOnR20(con))));
			selection.addToSelection(elem.getRepresents());
		}
	.else
  		if (ge2.getRepresents() instanceof Association_c) { 
            Association_c assoc = (Association_c)ge2.getRepresents();
            ClassAsLink_c link = ClassAsLink_c.getOneR_ASSROnR211(
               LinkedAssociation_c.getOneR_ASSOCOnR206(assoc));
  			Cl_c.Clearselection();	
			selection.addToSelection(link);
		}
	.end if
		LinkedFormalizeOn${operand_kl}Action fa = (LinkedFormalizeOn${operand_kl}Action) new LinkedFormalizeOn${operand_kl}Action();
		Action a = new Action() {};
		fa.setActivePart(a, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = Selection.getInstance().getStructuredSelection();
		WizardDialog wd = fa.${operand_kl}_LinkedFormalize(structuredSelection);
		LinkedFormalizeOn${operand_kl}WizardPage${wizard_page_num} page1 = (LinkedFormalizeOn${operand_kl}WizardPage${wizard_page_num}) wd.getCurrentPage();
    .select any oir related by class->R_OIR[R201]
    .select one rel related by oir->R_REL[R201]
  .end if
  		page1.onPageEntry();
		IWizard w = page1.getWizard();		
		w.performFinish();  
		wd.close();
  		verifyRefAttrDatatype(ge2);
  .if (ss.Name == "FSS Tests")
  		if (ge2.getRepresents() instanceof Association_c) { 
			Association_c assoc = (Association_c)ge2.getRepresents();
	        ClassAsSupertype_c sup = ClassAsSupertype_c.getOneR_SUPEROnR212(
	                SubtypeSupertypeAssociation_c.getOneR_SUBSUPOnR206(assoc));
	        ModelClass_c mc = ModelClass_c.getOneO_OBJOnR201(ClassInAssociation_c.getOneR_OIROnR203(
	        		ReferredToClassInAssoc_c.getOneR_RTOOnR204(sup)));
	        ReferredToIdentifierAttribute_c [] rtida_set = ReferredToIdentifierAttribute_c.getManyO_RTIDAsOnR110(
	        		ClassIdentifierAttribute_c.getManyO_OIDAsOnR105(Attribute_c.getManyO_ATTRsOnR102(mc)));
    .if ( class.Name == "FormalizeSUBSUP OBJ_OBJ OBJ" )
      .// this class has two attributes for its identifier
	        assertEquals( 2, rtida_set.length );
    .else
	        assertEquals( 1, rtida_set.length );
    .end if
        }
  .end if 
		validateOrGenerateResults(ce, generateResults);
.end function
.//
.//=======================================================================
.//=======================================================================
.//
.function unformalize_test_generics
  .param inst_ref class    .// O_OBJ
  .param boolean is_special
  .//
  .assign operand_kl = "R_REL"
  .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
  .if (class.Descrip == "Imported Class")
    .select one ic related by class->O_IOBJ[R101]
  	.select one ss related by ic->PE_PE[R8001]->EP_PKG[R8000]
  		openTestPKGDiagram("${ss.Name}");
		ModelClass_c mc=getModelClass("${class.Name}");
  		Association_c assoc = Association_c.getOneR_RELOnR201(mc, new getAssocByDescrip(""));
  .else
    .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
		openTestPKGDiagram("${ss.Name}");
		ModelClass_c mc = getModelClass("${class.Name}");
  		Association_c assoc = Association_c.getOneR_RELOnR201(mc, new getAssocByDescrip(""));
  .end if
  		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
  		Cl_c.Clearselection();	
  .if ( is_special )
    .if (ss.Name == "FSS Tests")
      .assign operand_kl = "R_SUB"
        ClassAsSubtype_c sub = ClassAsSubtype_c.getOneR_SUBOnR213(
           SubtypeSupertypeAssociation_c.getOneR_SUBSUPOnR206(assoc));
        selection.addToSelection(sub);
    .elif (ss.Name == "FAL Tests")
      .assign operand_kl = "R_ASSR"
        ClassAsLink_c link = ClassAsLink_c.getOneR_ASSROnR211(
           LinkedAssociation_c.getOneR_ASSOCOnR206(assoc));
        selection.addToSelection(link);
    .end if
  .else
      	selection.addToSelection(assoc);
  .end if
		UnformalizeOn${operand_kl}Action ufa = new UnformalizeOn${operand_kl}Action();
		Action a = new Action() {};
		ufa.setActivePart(a, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);				
		validateOrGenerateResults(ce, generateResults);
.end function
.//
.//=======================================================================
.//=======================================================================
.//  Main code
.//
//========================================================================
//
// File: FormalizeUnformalizeTestGenerics.java
//
// WARNING:      Do not edit this generated file
// Generated by: ${info.arch_file_name}
// Version:      $$Revision: 1.27 $$
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
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
//
package com.mentor.nucleus.bp.core.test;

import java.util.UUID;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.core.ui.*;
import com.mentor.nucleus.bp.ui.canvas.*;
import com.mentor.nucleus.bp.ui.graphics.editor.*;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public class FormalizeUnformalizeTestGenerics extends CanvasTest {

	String test_id = null;
	private static boolean generateResults = false;
    private boolean reloadModel = false;
    
	private static boolean initialized = false;
	private static boolean firstTime=true;
	
    private static Selection selection = Selection.getInstance();

	public FormalizeUnformalizeTestGenerics(String name) {
		super("Default Project", name);
	}

	protected String getResultName() {
		return "FormalizeUnformalize" + "_" + test_id;
	}	

    public void setReloadModel( boolean val ) {
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
	ImportedClass_c ic =
		ImportedClass_c.ImportedClassInstance(modelRoot, new ImportedClass_by_name_c(name));
	GraphicalElement_c ge =
		GraphicalElement_c.GraphicalElementInstance(graphicsModelRoot,
			ctu.new findGraphicalElementByOOAID(ic.getIobj_id(), 23));
	return Shape_c.getOneGD_SHPOnR2(ge);
	}
	public void openTestPKGDiagram(String title) {
		Package_c uut = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c(title));
		assertNotNull(uut);
		CanvasTestUtils.openCanvasEditor(uut);
	}
  	private void verifyRefAttrDatatype(GraphicalElement_c ge2) {
		if (ge2.getRepresents() instanceof Association_c) { 
	        Association_c assoc = (Association_c)ge2.getRepresents();
	        Attribute_c [] attr_set = Attribute_c.getManyO_ATTRsOnR106(
	                ReferentialAttribute_c.getManyO_RATTRsOnR108(
	                AttributeReferenceInClass_c.getManyO_REFsOnR111(
	                        ReferringClassInAssoc_c.getManyR_RGOsOnR203(
	                                ClassInAssociation_c.getManyR_OIRsOnR201(assoc)))));
	        UUID expectedId = getSameAsBaseAttributeUUID(Ooaofooa.getDefaultInstance());
	        for ( int i = 0; i < attr_set.length; ++ i )
	        {
	            // 524296 is the old id for same_as<Base_Attribute>
	            assertEquals( expectedId, attr_set[i].getDt_id());   
	        }
        }
        else if (ge2.getRepresents() instanceof ClassAsLink_c) { 
            ClassAsLink_c cal = (ClassAsLink_c)ge2.getRepresents();
            Attribute_c [] attr_set = Attribute_c.getManyO_ATTRsOnR106(
                    ReferentialAttribute_c.getManyO_RATTRsOnR108(
                    AttributeReferenceInClass_c.getManyO_REFsOnR111(
                            ReferringClassInAssoc_c.getOneR_RGOOnR205(cal))));
            UUID expectedId = getSameAsBaseAttributeUUID(Ooaofooa.getDefaultInstance());
            for ( int i = 0; i < attr_set.length; ++ i )
            {
                // 524296 is the old id for same_as<Base_Attribute>
                assertEquals( expectedId, attr_set[i].getDt_id());   
            }
        }
	}
	public void setGenerateResults() {
		try {
			generateResults = true;
			this.setUp();
.select many classes from instances of O_OBJ where (selected.KEY_LETT == "T_FORM")
.for each class in classes
  			this.test$r{class.Name}();
.end for
.for each class in classes
			this.testUn$r{class.Name}();
.end for
.//
.// test formalize and unformalize on ClassAsLink and ClassAsSubtype
.// use new test result, as GEF auto-shifts scroll
.//
.for each class in classes
  .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
  .if (class.Descrip == "Imported Class")
    .select one ic related by class->O_IOBJ[R101]
  	.select one ss related by ic->PE_PE[R8001]->EP_PKG[R8000]
  .end if
  .if ((ss.Name == "FSS Tests") or (ss.Name == "FAL Tests"))
  	       this.test$r{class.Name}Special();
  .end if
.end for
.for each class in classes
  .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
  .if (class.Descrip == "Imported Class")
    .select one ic related by class->O_IOBJ[R101]
  	.select one ss related by ic->PE_PE[R8001]->EP_PKG[R8000]
  .end if
  .if ((ss.Name == "FSS Tests") or (ss.Name == "FAL Tests"))
  		   this.testUn$r{class.Name}Special();
  .end if
.end for
.select many classes from instances of O_OBJ where (selected.KEY_LETT == "T_UNFORM")
.for each class in classes
			this.test$r{class.Name}();
.end for
		} catch (Exception e) {
			System.out.println(
				"Exception encountered by test result creator: " + e);
		}

	}
.assign count = 0
.select many classes from instances of O_OBJ where (selected.KEY_LETT == "T_FORM")
.for each class in classes
  .assign count = count + 1
  	public void test$r{class.Name}() {
  		test_id = "test_${count}";
  .invoke ft = formalize_test_generics( class, false )
${ft.body}\
	}
.end for
.for each class in classes
  .assign count = count + 1
  	public void testUn$r{class.Name}() {
  		test_id = "test_${count}";
  .invoke ft = unformalize_test_generics( class, false )
${ft.body}\
	}
.end for
.//
.// test formalize and unformalize on ClassAsLink and ClassAsSubtype
.// use new test result, as GEF auto-shifts scroll
.//
.assign count = 0
.for each class in classes
  .assign count = count + 1
  .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
  .if (class.Descrip == "Imported Class")
    .select one ic related by class->O_IOBJ[R101]
  	.select one ss related by ic->PE_PE[R8001]->EP_PKG[R8000]
  .end if
  .if ((ss.Name == "FSS Tests") or (ss.Name == "FAL Tests"))
  	public void test$r{class.Name}Special() {
  		test_id = "test_Special_${count}";
    .invoke ft = formalize_test_generics( class, true )
${ft.body}\
	}
  .end if
.end for
.for each class in classes
  .assign count = count + 1
  .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
  .if (class.Descrip == "Imported Class")
    .select one ic related by class->O_IOBJ[R101]
  	.select one ss related by ic->PE_PE[R8001]->EP_PKG[R8000]
  .end if
  .if ((ss.Name == "FSS Tests") or (ss.Name == "FAL Tests"))
  	public void testUn$r{class.Name}Special() {
  	    // for special tests do not reuse the
  	    // existing result
  		test_id = "test_Special_${count}";
    .invoke ft = unformalize_test_generics( class, true )
${ft.body}\
	}
  .end if
.end for
.//
.select many classes from instances of O_OBJ where (selected.KEY_LETT == "T_UNFORM")
.for each class in classes
  .assign count = count + 1
  .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
  	public void test$r{class.Name}() {
  		test_id = "test_${count}";
  .if (class.Descrip == "Imported Class")
    .select one ic related by class->O_IOBJ[R101]
  	.select one ss related by ic->PE_PE[R8001]->EP_PKG[R8000]
  		openTestPKGDiagram("${ss.Name}");
  		Shape_c shp = getModelIClassShape("${class.Name}");
		ModelClass_c mc = getModelClass("${class.Name}");
  		Association_c assoc = Association_c.getOneR_RELOnR201(mc, new getAssocByDescrip(""));
  .else
    .select one ss related by class->PE_PE[R8001]->EP_PKG[R8000]
		openTestPKGDiagram("${ss.Name}");
				ModelClass_c mc = getModelClass("${class.Name}");
  		Association_c assoc = Association_c.getOneR_RELOnR201(mc, new getAssocByDescrip(""));
  .end if
  		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
  		Cl_c.Clearselection();	
      	selection.addToSelection(assoc);
		UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
		Action a = new Action() {};
		ufa.setActivePart(a, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
		ufa.run(a);				
		validateOrGenerateResults(ce, generateResults);
	}
.end for

    boolean simp_results[] = {
        false, false, false, true, false, true, false, true, true, false, 
        false, false, false, 
        false, false };

    boolean assoc_results[] = {
        false, false, false, false, false, false, false, false, false, false, 
        false, false, true, 
        false, false };

    boolean subsup_results[] = {
        false, false, false, false, false, false, false, false, false, false, 
        false, false, false, 
        false, true };

    public void testFormalizeActionFilter() {
		Package_c uut = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("actionFilter Tests"));
		Association_c[] assoc_set = Association_c.getManyR_RELsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(uut));
		for (int i = 0; i < assoc_set.length; ++i) {
			String status = "Association R" + assoc_set[i].getNumb();
			assertEquals(status, simp_results[i], assoc_set[i].Actionfilter(
					"type", "unform simp"));
			assertEquals(status, assoc_results[i], assoc_set[i].Actionfilter(
					"type", "unform assoc"));
			assertEquals(status, subsup_results[i], assoc_set[i].Actionfilter(
					"type", "unform subsup"));
		}
    }

    /**
     * This test case is added to test the work done to resolve issue 
     * dts0100909056
     * The test steps are descriped in dts0100909056.dnt section 10.1
     * */
    public void testDeletingForwardedIdentifierAttr()
    {
        String projectName = "orphaned_ref_attribute";
        try {
            loadProject(projectName);
        } catch (CoreException e) {
            e.printStackTrace();
        }
        Package_c pkg1 = Package_c.getOneEP_PKGOnR1401(m_sys, new Package_by_name_c("pkg1")); 
        
        // Open the pkg1 diagram
        CanvasUtilities.openCanvasEditor(pkg1);
        // Unformalize R1.
        ModelClass_c mc = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg1), new ClassQueryInterface_c() {

            @Override
            public boolean evaluate(Object candidate) {
                ModelClass_c selected = (ModelClass_c) candidate;
                return (selected.getName().equals("C1"));

            }
        });

        Association_c assoc = Association_c.getOneR_RELOnR201(mc,
                new ClassQueryInterface_c() {

                    @Override
                    public boolean evaluate(Object candidate) {
                        Association_c selected = (Association_c) candidate;
                        return selected.getName().equals("R1");
                    }
                });
        GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
        Cl_c.Clearselection();
        selection.addToSelection(assoc);
        UnformalizeOnR_RELAction ufa = new UnformalizeOnR_RELAction();
        Action a = new Action() {
        };
        ufa.setActivePart(a, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
        ufa.run(a);

        // Unformalize R4.
        mc = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg1), new ClassQueryInterface_c() {

            @Override
            public boolean evaluate(Object candidate) {
                ModelClass_c selected = (ModelClass_c) candidate;
                return (selected.getName().equals("C3"));

            }
        });

        assoc = Association_c.getOneR_RELOnR201(mc,
                new ClassQueryInterface_c() {

                    @Override
                    public boolean evaluate(Object candidate) {
                        Association_c selected = (Association_c) candidate;
                        return selected.getName().equals("R4");
                    }
                });
        ce = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
        Cl_c.Clearselection();
        selection.addToSelection(assoc);
        UnformalizeOnR_RELAction ufa2 = new UnformalizeOnR_RELAction();
        Action a2 = new Action() {
        };
        ufa2.setActivePart(a2, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
        ufa2.run(a2);
        // In model explorer, delete C2.C1_ID
        ModelClass_c mc2 = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg1), new ClassQueryInterface_c() {

            @Override
            public boolean evaluate(Object candidate) {
                ModelClass_c selected = (ModelClass_c) candidate;
                return (selected.getName().equals("C2"));

            }
        });

        Attribute_c attr = Attribute_c.getOneO_ATTROnR102(mc2,
                new ClassQueryInterface_c() {

                    @Override
                    public boolean evaluate(Object candidate) {
                        Attribute_c selected = (Attribute_c) candidate;
                        return (selected.getName().equals("C1_ID"));
                    }
                });
        String itemName = attr.getName();

        while (Display.getCurrent().readAndDispatch())
            ;

        ExplorerUtil.getTreeViewer().refresh();
        TreeItem systemItem = ExplorerUtil.findItem(m_sys.getName());
        ExplorerUtil.getTreeViewer().expandToLevel(5);

        TreeItem attrItem = systemItem.getItems()[0].getItems()[2].getItems()[2];
        ExplorerUtil.getTreeViewer().setSelection(
                new StructuredSelection(new Object[] { attrItem.getData() }));
        while (Display.getCurrent().readAndDispatch())
            ;
        // Warning message stating that "Operation
        // not allowed. The attribute is being used
        // to formalize one or more associations."
        // is shown
        TestUtil.okToDialog(300);
        DeleteAction del = new DeleteAction(null);
        del.run();

        // C2.C1_ID is not disposed

        ExplorerUtil.getTreeViewer().refresh();
        TreeItem systemItem2 = ExplorerUtil.findItem(m_sys.getName());
        ExplorerUtil.getTreeViewer().expandToLevel(5);

        TreeItem attrItem2 = systemItem.getItems()[0].getItems()[2].getItems()[2];
        ExplorerUtil.getTreeViewer().setSelection(
                new StructuredSelection(new Object[] { attrItem.getData() }));
        while (Display.getCurrent().readAndDispatch())
            ;
        assertNotNull(attrItem2);
        assertTrue(((Attribute_c) attrItem2.getData()).getName().equals(
                attr.getName()));
        try {
            project.delete(true, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}    
.emit to file "src/com/mentor/nucleus/bp/core/test/FormalizeUnformalizeTestGenerics.java"