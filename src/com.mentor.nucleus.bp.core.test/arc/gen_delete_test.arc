.//=====================================================================
.//
.// File:      $RCSfile: gen_delete_test.arc,v $
.// Version:   $Revision: 1.24 $
.// Modified:  $Date: 2013/05/10 04:34:14 $
.//
.// (c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
.//
.//=====================================================================
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
.//=====================================================================
.//
.//
.function processClass
  .param Inst_Ref ir
  .param String objtype
  .param String ss
    .assign attr_GroupTest = false
    .assign attr_First = false
    .assign attr_Second = false
    .assign attr_name = "Unassigned Imported Class"
    .if (objtype == "IOBJ")
      .select one obj related by ir->O_OBJ[R101];
      .if (not_empty obj)
        .assign attr_name = "${obj.Name}"
        .if (ss == "O_IOBJ_Delete")
          .invoke gt_result = processForGroupTest(obj)
          .assign attr_GroupTest = gt_result.isGroupTest
          .assign attr_First = gt_result.isFirst
          .assign attr_Second = gt_result.isSecond
        .end if
      .end if
    .elif (objtype == "OBJ")
      .assign attr_name = "${ir.Name}"
    .end if
.end function
.function processForGroupTest
  .param Inst_Ref obj
    .assign attr_isGroupTest = false
    .assign attr_isFirst = false
    .assign attr_isSecond = false
    .if (obj.Descrip == "GT:First")
      .assign attr_isGroupTest = true
      .assign attr_isFirst = true
    .elif (obj.Descrip == "GT:Second")
      .assign attr_isGroupTest = true
      .assign attr_isSecond = true
    .end if
.end function
.function genTestCode
  .param Inst_Ref ic
  .param String ss
  .param Integer count
  .param Integer test_count
  .param String objtype
  .param String testtype
    .assign test_name = ""
    .assign groupTest = false
    .assign isFirst = false
    .assign isSecond = false
    .assign ooa_type = 0
    .if (objtype == "State")
      .assign test_name = ic.Name
      .assign ooa_type = 8
    .else
      .invoke result = processClass(ic, "${objtype}", "${ss}")
      .assign test_name = result.name
      .assign groupTest = result.GroupTest
      .assign isFirst = result.First
      .assign isSecond = result.Second
      .assign ooa_type = 5
    .end if
  	public void test$r{test_name}() {
  		test_id = "test_${count}";
    .if (test_count == 1)
      .if (objtype == "State")
        openTestISCDiagram("${ss}");
      .else
  		openTestSSDiagram("${ss}");
  	  .end if
    .end if
  		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
      .if (objtype == "IOBJ")
  		Shape_c shp = getModelIClassShape("${test_name}");
  	  .elif (objtype == "OBJ")
  		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot, "${test_name}");
  	  .elif (objtype == "State")
  		Shape_c shp = CanvasTestUtils.getModelStateShape(modelRoot, "${test_name}");
  	  .end if
  		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
  		Cl_c.Clearselection();
    .if (groupTest)
        Connector_c con = Connector_c.getOneGD_CONOnR20(
            Graphedge_c.getOneDIM_EDOnR320(
                Graphconnector_c.getManyDIM_CONsOnR311(
                    Graphelement_c.getOneDIM_GEOnR23(ge))));
  		if(con == null)
            con = Connector_c.getOneGD_CONOnR20(
                Graphedge_c.getOneDIM_EDOnR321(
                    Graphconnector_c.getManyDIM_CONsOnR311(
                        Graphelement_c.getOneDIM_GEOnR23(ge))));
  		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
      .if (isFirst)
      	Selection.getInstance().addToSelection(ge.getRepresents());
      	Selection.getInstance().addToSelection(ge2.getRepresents());
      .elif (isSecond)
    	Selection.getInstance().addToSelection(ge2.getRepresents());
    	Selection.getInstance().addToSelection(ge.getRepresents());
      .else
        Selection.getInstance().addToSelection(ge2.getRepresents());
      .end if
    .elif (groupTest == false)
      .if (testtype == "Connector")
        Connector_c con = Connector_c.getOneGD_CONOnR20(
            Graphedge_c.getOneDIM_EDOnR320(
                Graphconnector_c.getManyDIM_CONsOnR311(
                    Graphelement_c.getOneDIM_GEOnR23(ge))));
  		if(con == null)
            con = Connector_c.getOneGD_CONOnR20(
                Graphedge_c.getOneDIM_EDOnR321(
                    Graphconnector_c.getManyDIM_CONsOnR311(
                        Graphelement_c.getOneDIM_GEOnR23(ge))));
  		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
     	Selection.getInstance().addToSelection(ge2.getRepresents());
      .else
      	Selection.getInstance().addToSelection(ge.getRepresents());
      .end if
    .end if
		DeleteAction da = new DeleteAction(CorePlugin.getImageDescriptor("delete_edit.gif"));
  		da.run();

		validateOrGenerateResults(ce, generateResults);
  	}
.end function
.//======================================================================
.//======================================================================
.function processClassGenerics
  .param Inst_Ref ir
  .param String objtype
  .param String ss
    .assign attr_GroupTest = false
    .assign attr_First = false
    .assign attr_Second = false
    .assign attr_name = "Unassigned Imported Class"
    .if (objtype == "IOBJ")
      .select one obj related by ir->O_OBJ[R101];
      .if (not_empty obj)
        .assign attr_name = "${obj.Name}"
        .if (ss == "O_IOBJ_Delete")
          .invoke gt_result = processForGroupTestGenerics(obj)
          .assign attr_GroupTest = gt_result.isGroupTest
          .assign attr_First = gt_result.isFirst
          .assign attr_Second = gt_result.isSecond
        .end if
      .end if
    .elif (objtype == "OBJ")
      .assign attr_name = "${ir.Name}"
    .end if
.end function
.function processForGroupTestGenerics
  .param Inst_Ref obj
    .assign attr_isGroupTest = false
    .assign attr_isFirst = false
    .assign attr_isSecond = false
    .if (obj.Descrip == "GT:First")
      .assign attr_isGroupTest = true
      .assign attr_isFirst = true
    .elif (obj.Descrip == "GT:Second")
      .assign attr_isGroupTest = true
      .assign attr_isSecond = true
    .end if
.end function
.function genTestCodeGenerics
  .param Inst_Ref ic
  .param String ss
  .param Integer count
  .param Integer test_count
  .param String objtype
  .param String testtype
    .assign test_name = ""
    .assign groupTest = false
    .assign isFirst = false
    .assign isSecond = false
    .assign ooa_type = 0
    .if (objtype == "State")
      .assign test_name = ic.Name
      .assign ooa_type = 8
    .else
      .invoke result = processClassGenerics(ic, "${objtype}", "${ss}")
      .assign test_name = result.name
      .assign groupTest = result.GroupTest
      .assign isFirst = result.First
      .assign isSecond = result.Second
      .assign ooa_type = 5
    .end if
  	public void test$r{test_name}() {
  		test_id = "test_${count}";
    .if (test_count == 1)
      .if (objtype == "State")
        openTestISCDiagram("${ss}");
      .else
  		openTestPKGDiagram("${ss}");
  	  .end if
    .end if
  		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
      .if (objtype == "IOBJ")
  		Shape_c shp = getModelIClassShape("${test_name}");
  	  .elif (objtype == "OBJ")
  		Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot, "${test_name}");
  	  .elif (objtype == "State")
  		Shape_c shp = CanvasTestUtils.getModelStateShape(modelRoot, "${test_name}");
  	  .end if
  		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
  		Cl_c.Clearselection();
    .if (groupTest)
        Connector_c con = Connector_c.getOneGD_CONOnR20(
            Graphedge_c.getOneDIM_EDOnR320(
                Graphconnector_c.getManyDIM_CONsOnR311(
                    Graphelement_c.getOneDIM_GEOnR23(ge))));
  		if(con == null)
            con = Connector_c.getOneGD_CONOnR20(
                Graphedge_c.getOneDIM_EDOnR321(
                    Graphconnector_c.getManyDIM_CONsOnR311(
                        Graphelement_c.getOneDIM_GEOnR23(ge))));
  		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
      .if (isFirst)
      	Selection.getInstance().addToSelection(ge.getRepresents());
      	Selection.getInstance().addToSelection(ge2.getRepresents());
      .elif (isSecond)
    	Selection.getInstance().addToSelection(ge2.getRepresents());
    	Selection.getInstance().addToSelection(ge.getRepresents());
      .else
        Selection.getInstance().addToSelection(ge2.getRepresents());
      .end if
    .elif (groupTest == false)
      .if (testtype == "Connector")
        Connector_c con = Connector_c.getOneGD_CONOnR20(
            Graphedge_c.getOneDIM_EDOnR320(
                Graphconnector_c.getManyDIM_CONsOnR311(
                    Graphelement_c.getOneDIM_GEOnR23(ge))));
  		if(con == null)
            con = Connector_c.getOneGD_CONOnR20(
                Graphedge_c.getOneDIM_EDOnR321(
                    Graphconnector_c.getManyDIM_CONsOnR311(
                        Graphelement_c.getOneDIM_GEOnR23(ge))));
  		GraphicalElement_c ge2 = GraphicalElement_c.getOneGD_GEOnR2(con);
     	Selection.getInstance().addToSelection(ge2.getRepresents());
      .else
      	Selection.getInstance().addToSelection(ge.getRepresents());
      .end if
    .end if
		DeleteAction da = new DeleteAction(CorePlugin.getImageDescriptor("delete_edit.gif"));
  		da.run();

		validateOrGenerateResults(ce, generateResults);
  	}
.end function
//======================================================================
//
// File: DeleteTestGenerics.java
//
// WARNING:      Do not edit this generated file
// Generated by: ${info.arch_file_name}
// Version:      $$Revision: 1.24 $$
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//======================================================================
//
package com.mentor.nucleus.bp.core.test;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.swt.widgets.Display;

import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DataTypePackage_c;

import com.mentor.nucleus.bp.core.ImportedClass_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.SystemDatatypePackage_c;

import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.XtUMLNature;
import com.mentor.nucleus.bp.core.ui.DeleteAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.perspective.BridgePointPerspective;
import com.mentor.nucleus.bp.io.mdl.ImportModel;
import com.mentor.nucleus.bp.ui.graphics.editor.*;
import com.mentor.nucleus.bp.ui.canvas.CanvasPlugin;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphconnector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphedge_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;

public class DeleteTestGenerics extends CanvasTest {

	String test_id = null;
	private static boolean generateResults = false;
    private static boolean initialized = false;
	static String workspace_path = "";

	public DeleteTestGenerics(String name) {
		super("Delete Test", name);
	}

	protected String getResultName() {
		return "DeleteTest" + "_" + test_id;
	} 

	protected void setUp() throws Exception {
		super.setUp();

		IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
		store.setValue(BridgePointPreferencesStore.DISABLE_GRADIENTS, true);

      if (!initialized) {
			ensureAvailableAndLoaded("com.mentor.nucleus.bp.core.test",
					"DeleteTestModel", false, false, "Package");
			initialized = true;
		}

		Display d = Display.getCurrent();
		while ( d.readAndDispatch() ) ;
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
	public void openTestISCDiagram(String title) {
		CanvasTestUtils ctu = new CanvasTestUtils();
		InstanceStateMachine_c uut = InstanceStateMachine_c.getOneSM_ISMOnR518(ModelClass_c.ModelClassInstance(modelRoot, ctu.new Class_by_name_c("Test Import Class")));
		CanvasTestUtils.openCanvasEditor(uut);
	}
	public void setGenerateResults() {
		try {
			generateResults = true;
			this.setUp();
.select any ss from instances of EP_PKG where (selected.Name == "O_IOBJ_Delete")
.select many ics related by ss->PE_PE[R8000]->O_IOBJ[R8001] where (selected.Obj_KL != "T_IMPOTH")
.for each ic in ics
  .invoke result = processClass(ic, "IOBJ", "${ss.Name}")
  			this.test$r{result.name}();
.end for
.select any ss from instances of EP_PKG where (selected.Name == "R_REL_Delete")
.select many ics related by ss->PE_PE[R8000]->O_IOBJ[R8001] where (selected.Obj_KL == "T_IMPREL")
.for each ic in ics
			this.test$r{ic.Obj_Name}();
.end for
.select many classes from instances of O_OBJ where (selected.KEY_LETT == "T_CLSRELCON")
.for each class in classes
  			this.test$r{class.Name}();
.end for
.select any ss from instances of EP_PKG where (selected.Name == "R_SUPER_Delete")
.select many ics related by ss->PE_PE[R8000]->O_IOBJ[R8001] where (selected.Obj_KL == "T_IMPSUPER")
.for each ic in ics
			this.test$r{ic.Obj_Name}();
.end for
.select many classes from instances of O_OBJ where (selected.KEY_LETT == "T_CLSSUPERCON")
.for each class in classes
  			this.test$r{class.Name}();
.end for
.select any ss from instances of EP_PKG where (selected.Name == "R_SUB_Delete")
.select many ics related by ss->PE_PE[R8000]->O_IOBJ[R8001] where (selected.Obj_KL == "T_IMPSUB")
.for each ic in ics
			this.test$r{ic.Obj_Name}();
.end for
.select many classes from instances of O_OBJ where (selected.KEY_LETT == "T_CLSSUBCON")
.for each class in classes
  			this.test$r{class.Name}();
.end for
.select any ss from instances of EP_PKG where (selected.Name == "R_ASSR_Delete")
.select many ics related by ss->PE_PE[R8000]->O_IOBJ[R8001] where (selected.Obj_KL == "T_IMPASSR")
.for each ic in ics
			this.test$r{ic.Obj_Name}();
.end for
.select many classes from instances of O_OBJ where (selected.KEY_LETT == "T_CLSASSRCON")
.for each class in classes
  			this.test$r{class.Name}();
.end for
.select any ss from instances of EP_PKG where (selected.Name == "Import Subsystem")
.select one class related by ss->PE_PE[R8000]->O_OBJ[R8001] where (selected.Name == "Test Import Class")
.select one isc related by class->SM_ISM[R518]->SM_SM[R517]
.select many states related by isc->SM_STATE[R501] where (selected.Name != "Test State OtherSide")
.for each state in states
			this.test$r{state.Name}();
.end for
.select any ss from instances of EP_PKG where (selected.Name == "O_OBJ_Delete")
.select many mc_set related by ss->PE_PE[R8000]->O_OBJ[R8001] where (selected.Key_Lett != "T_OTH")
.for each mc in mc_set
  			this.test$r{mc.Name}();
.end for
		} catch (Exception e) {
			System.out.println(
				"Exception encountered by test result creator: " + e);
		}

	}
.//
.// Create O_IOBJ delete tests
.//
.assign count = 0
.assign test_count = 0
.select any ss from instances of EP_PKG where (selected.Name == "O_IOBJ_Delete")
.select many ics related by ss->PE_PE[R8000]->O_IOBJ[R8001] where (selected.Obj_KL != "T_IMPOTH")
.for each ic in ics
  .assign count = count + 1
  .assign test_count = test_count + 1
  .invoke test = genTestCodeGenerics(ic, "${ss.Name}", count, test_count, "IOBJ", "Imported Object")
  ${test.body}
.end for
.//
.// Create R_REL delete tests
.//
.select any ss from instances of EP_PKG where (selected.Name == "R_REL_Delete")
.select many ics related by ss->PE_PE[R8000]->O_IOBJ[R8001] where (selected.Obj_KL == "T_IMPREL")
.assign test_count = 0
.for each ic in ics
  .assign count = count + 1
  .assign test_count = test_count + 1
  .invoke test = genTestCodeGenerics(ic, "${ss.Name}", count, test_count, "IOBJ", "Connector")
  ${test.body}
.end for
.select many classes from instances of O_OBJ where (selected.KEY_LETT == "T_CLSRELCON")
.for each class in classes
  .assign count = count + 1
  .assign test_count = test_count + 1
  .invoke test = genTestCodeGenerics(class, "${ss.Name}", count, test_count, "OBJ", "Connector")
  ${test.body}
.end for
.//
.// Create R_SUPER delete tests
.//
.select any ss from instances of EP_PKG where (selected.Name == "R_SUPER_Delete")
.select many ics related by ss->PE_PE[R8000]->O_IOBJ[R8001] where (selected.Obj_KL == "T_IMPSUPER")
.assign test_count = 0
.for each ic in ics
  .assign count = count + 1
  .assign test_count = test_count + 1
  .invoke test = genTestCodeGenerics(ic, "${ss.Name}", count, test_count, "IOBJ", "Connector")
  ${test.body}
.end for
.select many classes from instances of O_OBJ where (selected.KEY_LETT == "T_CLSSUPERCON")
.for each class in classes
  .assign count = count + 1
  .assign test_count = test_count + 1
  .invoke test = genTestCodeGenerics(class, "${ss.Name}", count, test_count, "OBJ", "Connector")
  ${test.body}
.end for
.//
.// Create R_SUB delete tests
.//
.select any ss from instances of EP_PKG where (selected.Name == "R_SUB_Delete")
.select many ics related by ss->PE_PE[R8000]->O_IOBJ[R8001] where (selected.Obj_KL == "T_IMPSUB")
.assign test_count = 0
.for each ic in ics
  .assign count = count + 1
  .assign test_count = test_count + 1
  .invoke test = genTestCodeGenerics(ic, "${ss.Name}", count, test_count, "IOBJ", "Connector")
  ${test.body}
.end for
.select many classes from instances of O_OBJ where (selected.KEY_LETT == "T_CLSSUBCON")
.for each class in classes
  .assign count = count + 1
  .assign test_count = test_count + 1
  .invoke test = genTestCodeGenerics(class, "${ss.Name}", count, test_count, "OBJ", "Connector")
  ${test.body}
.end for
.//
.// Create R_ASSR delete tests
.//
.select any ss from instances of EP_PKG where (selected.Name == "R_ASSR_Delete")
.select many ics related by ss->PE_PE[R8000]->O_IOBJ[R8001] where (selected.Obj_KL == "T_IMPASSR")
.assign test_count = 0
.for each ic in ics
  .assign count = count + 1
  .assign test_count = test_count + 1
  .invoke test = genTestCodeGenerics(ic, "${ss.Name}", count, test_count, "IOBJ", "Connector")
  ${test.body}
.end for
.select many classes from instances of O_OBJ where (selected.KEY_LETT == "T_CLSASSRCON")
.for each class in classes
  .assign count = count + 1
  .assign test_count = test_count + 1
  .invoke test = genTestCodeGenerics(class, "${ss.Name}", count, test_count, "OBJ", "Connector")
  ${test.body}
.end for
.//
.// Create SM_TXN delete tests
.//
.select any ss from instances of EP_PKG where (selected.Name == "Import Subsystem")
.select one class related by ss->PE_PE[R8000]->O_OBJ[R8001] where (selected.Name == "Test Import Class")
.select one isc related by class->SM_ISM[R518]->SM_SM[R517]
.select many states related by isc->SM_STATE[R501] where (selected.Name != "Test State OtherSide")
.assign test_count = 0
.for each state in states
  .assign count = count + 1
  .assign test_count = test_count + 1
  .invoke test = genTestCodeGenerics(state, "${ss.Name}", count, test_count, "State", "Connector")
  ${test.body}
.end for
.//
.// Create O_OBJ link class delete tests
.//
.assign test_count = 0
.select any ss from instances of EP_PKG where (selected.Name == "O_OBJ_Delete")
.select many mc_set related by ss->PE_PE[R8000]->O_OBJ[R8001] where (selected.Key_Lett != "T_OTH")
.for each mc in mc_set
  .assign count = count + 1
  .assign test_count = test_count + 1
  .invoke test = genTestCodeGenerics(mc, "${ss.Name}", count, test_count, "OBJ", "Model Class")
  ${test.body}
.end for
}
.emit to file "src/com/mentor/nucleus/bp/core/test/DeleteTestGenerics.java"