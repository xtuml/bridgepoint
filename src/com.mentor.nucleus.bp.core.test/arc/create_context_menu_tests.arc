.//=======================================================================
.//
.// File:      $RCSfile: create_context_menu_tests.arc,v $
.// Version:   $Revision: 1.34 $
.// Modified:  $Date: 2013/05/10 04:34:14 $
.//
.// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
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
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.//
.include "${mc_archetypes}/arch_utils.inc"
.function needsQuery
  .param String kl
  .param String cmeLabel
  .param String specialism
  .assign attr_needsQuery = false
  .assign attr_nameAccessor = "getName()"
  .assign attr_query = ""
  .if(kl == "S_UDT")
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "Get_name()"
  .elif(kl == "S_DPK")
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif(kl == "O_ATTR")
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif(kl == "SM_EVT")
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getMning()"
  .elif(kl == "SM_STATE")
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "S_MBR") and (cmeLabel == "Move Up"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "O_TPARM") and (cmeLabel == "Move Up"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "S_SPARM") and (cmeLabel == "Move Up"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "SM_EVTDI") and (cmeLabel == "Move Up"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "O_TFR") and (cmeLabel == "Move Up"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "S_ENUM") and (cmeLabel == "Move Up"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "S_MBR") and (cmeLabel == "Move Down"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "O_TPARM") and (cmeLabel == "Move Down"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "S_SPARM") and (cmeLabel == "Move Down"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "SM_EVTDI") and (cmeLabel == "Move Down"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "O_TFR") and (cmeLabel == "Move Down"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "S_ENUM") and (cmeLabel == "Move Down"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"   
  .end if
  .select any mclass from instances of O_OBJ where (selected.Key_Lett == kl)
  .if((cmeLabel == "Instance State Machine") or (cmeLabel == "Class State Machine"))
    .assign attr_needsQuery = true
       Subsystem_c subsystem = Subsystem_c.SubsystemInstance(modelRoot, new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				if(((Subsystem_c)candidate).getName().equals("Formalize Menu Tests")) {
					return true;
				}
				return false;
			}

		});

	    $r{mclass.Name}_c obj = $r{mclass.Name}_c.getOneO_OBJOnR2(subsystem);
  .elif((cmeLabel == "Publish References") or (cmeLabel == "Unpublish References"))
     .assign attr_needsQuery = true
        String v_prefEnableInstanceReferences = "bridgepoint_prefs_enable_instance_references";
        if ((Pref_c.Getboolean(v_prefEnableInstanceReferences) == false)) {
          return;
        }

        .select any obj from instances of O_OBJ where (selected.Key_Lett == kl)
        $r{obj.Name}_c obj = $r{obj.Name}_c.$r{obj.Name}Instance(modelRoot);
  .elif((cmeLabel == "Formalize") or (cmeLabel == "Unformalize"))
    .if(((kl == "R_REL") or (kl == "R_ASSR")) or (kl == "R_SUB"))
      .assign attr_needsQuery = true
      .assign attr_nameAccessor = ""
      .select any mclass from instances of O_OBJ where (selected.Key_Lett == kl)
	  Subsystem_c subsystem = Subsystem_c.SubsystemInstance(modelRoot, new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				if(((Subsystem_c)candidate).getName().equals(\
				.if(cmeLabel == "Formalize")
"Formalize Menu Tests"\
				.else
"Test Subsystem"\
				.end if
)) {
					return true;
				}
				return false;
			}

		});
    .end if
    .if(kl == "R_ASSR")
		$r{mclass.Name}_c obj = $r{mclass.Name}_c.getOneR_ASSROnR211(LinkedAssociation_c.getOneR_ASSOCOnR206(Association_c.getManyR_RELsOnR4(subsystem)));
    .elif(kl == "R_SUB")
	  	ClassAsSubtype_c obj = ClassAsSubtype_c.getOneR_SUBOnR213(SubtypeSupertypeAssociation_c.getOneR_SUBSUPOnR206(Association_c.getManyR_RELsOnR4(subsystem)));
    .else
		$r{mclass.Name}_c obj = $r{mclass.Name}_c.getOneR_RELOnR4(subsystem);
    .end if
  .elif((cmeLabel == "Assign Event") or (cmeLabel == "Remove Event"))
    .assign attr_needsQuery = true
Subsystem_c subsystem = Subsystem_c.SubsystemInstance(modelRoot, new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				if(((Subsystem_c)candidate).getName().equals("Test Subsystem")) {
					return true;
				}
				return false;
			}

		});
		ModelClass_c mc = ModelClass_c.getOneO_OBJOnR2(subsystem, new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				if(((ModelClass_c)candidate).getName().equals(\
				.if(cmeLabel == "Remove Event")
"Class A"\
    .else
"Class B"\
    .end if
)) {
					return true;
				}
    			return false;
			}

		});
    .if("$r{mclass.Name}" == "CreationTransition")
    $r{mclass.Name}_c obj = $r{mclass.Name}_c.getOneSM_CRTXNOnR507(Transition_c.getOneSM_TXNOnR505(StateMachine_c.getOneSM_SMOnR517(InstanceStateMachine_c.getManySM_ISMsOnR518(mc))));
    .else
		$r{mclass.Name}_c obj = $r{mclass.Name}_c.getOneSM_TXNOnR505(StateMachine_c.getOneSM_SMOnR517(InstanceStateMachine_c.getManySM_ISMsOnR518(mc)));
		.end if
 .end if
.end function
.function isExcluded
  .param String kl
  .param String label
  .param String specialism
  .assign attr_result = false  
  
  
  .if(kl == "S_SYS")
    .assign attr_result = true
  .elif(kl == "EP_PKG")
    .assign attr_result = true
  .elif(kl == "SQ_S")
    .assign attr_result = true
  .elif(kl == "SQ_CIP")
    .assign attr_result = true
  .elif(kl == "SQ_COP")
    .assign attr_result = true
  .elif(kl == "SQ_CP")
    .assign attr_result = true
  .elif(kl == "SQ_CPA")
    .assign attr_result = true
  .elif(kl == "SQ_AV")
    .assign attr_result = true
  .elif(kl == "SQ_EEP")
    .assign attr_result = true
  .elif(kl == "SQ_FPP")
    .assign attr_result = true
  .elif(kl == "SQ_LS")
    .assign attr_result = true
  .elif(kl == "SQ_TM")
    .assign attr_result = true
  .elif(kl == "SQ_TS")
    .assign attr_result = true
  .elif(kl == "SQ_AP")
    .assign attr_result = true
  .elif(kl == "MSG_SM")
    .assign attr_result = true
  .elif(kl == "MSG_AM")
    .assign attr_result = true
  .elif(kl == "MSG_R")
    .assign attr_result = true
  .elif(kl == "MSG_A")
    .assign attr_result = true
  .elif(kl == "CL_IC")
    .assign attr_result = true
  .elif(kl == "IP_IP")
    .assign attr_result = true
  .elif(kl == "PA_DIC")
    .assign attr_result = true
  .elif(kl == "PA_SIC")
    .assign attr_result = true
  .elif(kl == "PA_SICP")
    .assign attr_result = true
  .elif (kl == "SQ_PP")
    .assign attr_result = true     
  .elif(kl == "SM_TXN")
    .if ((label == "Assign Signal") or (label == "Remove Signal"))
      .assign attr_result = true
    .end if
  .end if
  .select any obj from instances of O_OBJ where (selected.Key_Lett == kl)
  .select one pkg related by obj->PE_PE[R8001]->EP_PKG[R8000]
  .if(not_empty obj)
    .if((((pkg.Name == "Component") or (pkg.Name == "Component Packaging")) or (pkg.Name == "Interface Diagram")) or (pkg.Name == "Component Nesting"))
      .assign attr_result = true
    .end if
  .end if
  // Start : Visiblity menu entry test removal, the SMA QA manual test  dts0100689750  is testing this part until it got automated
  .if ((kl == "S_UDT") and (label == "Make Public"))
    .assign attr_result = true
  .end if
  .if ((kl == "S_UDT") and (label == "Make Private"))
    .assign attr_result = true
  .end if 
  .if ((kl == "S_UDT") and (label == "Make Protected"))
    .assign attr_result = true
  .end if 
  
  .if ((kl == "S_EDT") and (label == "Make Public"))
    .assign attr_result = true
  .end if 
  .if ((kl == "S_EDT") and (label == "Make Private"))
    .assign attr_result = true
  .end if 
  .if ((kl == "S_EDT") and (label == "Make Protected"))
    .assign attr_result = true
  .end if 
  
  .if ((kl == "S_SDT") and (label == "Make Public"))
    .assign attr_result = true
  .end if 
  .if ((kl == "S_SDT") and (label == "Make Private"))
    .assign attr_result = true
  .end if 
  .if ((kl == "S_SDT") and (label == "Make Protected"))
    .assign attr_result = true
  .end if 
  
  .if ((kl == "O_OBJ") and (label == "Make Public"))
    .assign attr_result = true
  .end if 
  .if ((kl == "O_OBJ") and (label == "Make Private"))
    .assign attr_result = true
  .end if 
  .if ((kl == "O_OBJ") and (label == "Make Protected"))
    .assign attr_result = true
  .end if 
  
  .if ((kl == "CNST_CSP") and (label == "Make Public"))
    .assign attr_result = true
  .end if 
  .if ((kl == "CNST_CSP") and (label == "Make Private"))
    .assign attr_result = true
  .end if 
  .if ((kl == "CNST_CSP") and (label == "Make Protected"))
    .assign attr_result = true
  .end if 
  // End : : Visiblity menu entry test removal   
  // START: (consistent menu entries) dts0100573206 test removal 
  .if (kl == "S_DOM")
    .if((((label == "Subsystem") or (label == "External Entity Package")) or (label == "Function Package")) or (label == "DataType Package"))
      .assign attr_result = true
    .end if
    .if((((label == "Sequence Diagram") or (label == "Communication Diagram")) or (label == "Usecase Diagram")) or (label == "Activity Diagram"))
      .assign attr_result = true
    .end if
  .end if
  .if (kl == "S_DPK")
    .if((((label == "User DataType") or (label == "Structured DataType")) or (label == "Enumeration DataType")) or (label == "Constant Specification"))
      .assign attr_result = true
    .end if
    .if(label == "DataType Package")
      .assign attr_result = true
    .end if
  .end if
  .if ((kl == "S_EDT") and (label == "Enumerator"))
    .assign attr_result = true
  .end if
  .if ((kl == "CNST_CSP") and (label == "Constant"))
    .assign attr_result = true
  .end if
  .if (kl == "S_FPK")
    .if((label == "Function") or (label == "Function Package"))
      .assign attr_result = true
    .end if
  .end if
  .if ((kl == "S_SYNC") and (label == "Parameter"))
    .assign attr_result = true
  .end if
  .if (kl == "S_EEPK")
    .if((label == "External Entity") or (label == "External Entity Package"))
      .assign attr_result = true
    .end if
  .end if
  .if ((kl == "S_EE") and (label == "Bridge Operation"))
    .assign attr_result = true
  .end if
  .if ((kl == "S_BRG") and (label == "Parameter"))
    .assign attr_result = true
  .end if
  .if (kl == "S_SS")
    .if((((label == "Class") or (label == "Imported Class")) or (label == "Subsystem")) or (label == "Sequence Diagram"))
      .assign attr_result = true
    .end if
    .if((((label == "Communication Diagram") or (label == "Usecase Diagram")) or (label == "Activity Diagram")) )
      .assign attr_result = true
    .end if
  .end if
  .if (kl == "O_OBJ")
    .if((((label == "Attribute") or (label == "Operation")) or (label == "Instance State Machine")) or (label == "Class State Machine"))
      .assign attr_result = true
    .end if
  .end if
  .if ((kl == "O_TFR") and (label == "Parameter"))
    .assign attr_result = true
  .end if
  .if (kl == "SM_ISM")
    .if((label == "Event") or (label == "State"))
      .assign attr_result = true
    .end if
  .end if
  .if (kl == "SM_ASM")
    .if((label == "Event") or (label == "State"))
      .assign attr_result = true
    .end if
  .end if
  .if ((kl == "SM_EVT") and (label == "Parameter"))
    .assign attr_result = true
  .end if
  .if (kl == "COMM_COMM")
    .if((((label == "Communication Diagram") or (label == "Component Participant")) or (label == "Instance")) or (label == "Actor Participant"))
      .assign attr_result = true
    .end if
    .if((((label == "External Entity") or (label == "Class Participant")) or (label == "Function Package Participant")) )
      .assign attr_result = true
    .end if
  .end if
  .if (kl == "UC_UCC")
    .if((((label == "Use Case diagram") or (label == "Usecase")) or (label == "Actor")) )
      .assign attr_result = true
    .end if
  .end if
  .if (kl == "A_A")
    .assign attr_result = true
  .end if
  .if ((kl == "S_SDT") and (label == "Member"))
    .assign attr_result = true
  .end if
  .if (kl == "CL_IP")
    .assign attr_result = true
  .end if
  .if (kl == "CL_IR")
    .assign attr_result = true
  .end if
  .if (kl == "SPR_PO")
    .assign attr_result = true
  .end if
  .if (kl == "SPR_PS")
    .assign attr_result = true
  .end if
  .if (kl == "SPR_RO")
    .assign attr_result = true
  .end if
  .if (kl == "SPR_RS")
    .assign attr_result = true
  .end if
  // END: (consistent menu entries) dts0100573206 test removal 
.end function
.assign path = "com.mentor.nucleus.bp.core.test/src/com/mentor/nucleus/bp/core/test/ui/"
.assign file = path + "ContextMenuTests.java";
//========================================================================
//
// File: com.mentor.nucleus.bp.ui.canvas.test/src/com/mentor/nucleus/bp/ui/canvas/test/ContextMenuTests.java
//
// WARNING:      Do not edit this generated file
// Generated by: ${info.arch_file_name}
// Version:      $$Revision: 1.34 $$
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
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

package com.mentor.nucleus.bp.core.test.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.*;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.CanvasEditorUtils;

/**
 * Contains various tests involving context menu behavior
 */
public class ContextMenuTests extends BaseTest
{
    /**
     * The editor upon which these tests operate.
     */
    private static GraphicalEditor editor = null;

    /**
     * A boolean to determine whether the test shall be performed
     * in a read only environment.
     */
    public boolean m_readonly;

    static protected boolean first_time = true;

    /**
     * Constructor.
     */

    public ContextMenuTests(String name)
    {
        super("ContextMenuTests", name);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() throws Exception
    {
    	super.setUp();

    	if ( first_time ) {
    	    Ooaofooa.setPersistEnabled(true);
            CorePlugin.disableParseAllOnResourceChange();
	        ensureAvailableAndLoaded("Models", "ContextMenuTests", false, true);

            // open a class diagram editor on any subsystem
            Subsystem_c uut = Subsystem_c.SubsystemInstance(modelRoot);
            CanvasTestUtils.openDiagramEditor(uut);
            editor = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
        }
    }
.select many cme_entries from instances of CME;
.for each cme_entry in cme_entries
  .invoke isExcluded = isExcluded(cme_entry.Key_Lett, cme_entry.Label, cme_entry.Specialism);
  .if(isExcluded.result != true)
    .assign actionName = ""
    .if(cme_entry.Specialism != "")
      .if(cme_entry.Specialism == "New")
        .assign actionName = cme_entry.Label
      .elif (cme_entry.Specialism == "Set")
        .assign actionName = cme_entry.Specialism + " "
        .assign actionName = actionName + "${cme_entry.Label}"
      .else
        .if ((cme_entry.Specialism == "Specialized Package") or (cme_entry.Specialism == "Generic Package"))
          .assign actionName = cme_entry.Label
        .else
          .assign actionName = cme_entry.Specialism + cme_entry.Label
        .end if
      .end if
    .else
      .assign actionName = cme_entry.Label;
    .end if
    public void testContextMenu$r{cme_entry.Specialism}$r{cme_entry.Label}ActionOn${cme_entry.Key_Lett}() {
    .select any obj from instances of O_OBJ where (selected.Key_Lett == cme_entry.Key_Lett)
    .invoke result = needsQuery(obj.Key_Lett, cme_entry.Label, cme_entry.Specialism);
    .if(cme_entry.Label == "Formalize")
      .invoke result = needsQuery(obj.Key_Lett, cme_entry.Label, cme_entry.Specialism);
      .assign actionName = cme_entry.Label
    .end if
    .assign name = "TestElement" + obj.Key_Lett
    .assign name = name + "for$r{actionName}"
    .if(result.needsQuery == true)
      .if(result.body == "")
		$r{obj.Name}_c obj = $r{obj.Name}_c.$r{obj.Name}Instance(modelRoot, new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				if((($r{obj.Name}_c) candidate).${result.nameAccessor}.equals("${name}")) {
					return true;
				}
				return false;
			}

		});
      .else
${result.body}
      .end if
    .else
		$r{obj.Name}_c obj = $r{obj.Name}_c.$r{obj.Name}Instance(modelRoot);
    .end if

			IFile file = obj.getFile();
			TestUtil.changeFileReadonlyStatus(m_readonly, file);

    	UITestingUtilities.clearGraphicalSelection();
    	editor = UITestingUtilities.addElementToGraphicalSelection(obj);
    	
    	Menu menu = null;
    	
    	if(editor == null) {
    	  // this element does not have a diagram
    	  // representation, add the element to the
    	  // core selection and use the explorer
    	  // context menu
    	  Selection.getInstance().clear();
    	  Selection.getInstance().addToSelection(obj);
    	  menu = getExplorerView().getTreeViewer().getControl().getMenu();
    	} else {
    	  // get the menu from the SWT Canvas
    	  menu = editor.getCanvas().getMenu();
		}

    	// check the status of the action
    	.if(actionName != "")
    	  .if(cme_entry.Specialism == "New")
    	assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "${cme_entry.Label}", "${cme_entry.Specialism}", m_readonly));
    	  .else
    	assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "${cme_entry.Label}", "", m_readonly));
    	  .end if
    	.else
    	  .exit 100;
    	.end if
    }
  .end if
.end for
    /**
     * Test the clearing of redo/undo stacks
     * after using replace with functionality
     */
    public void testClearingUndoRedoStacksAfterReplaceWith() throws CoreException {
        if(m_readonly){
            //Under MFP, this unit test is disabled as all the editors dont
            //allow editing if under lying file is read-only. This test was
            //written with respect to single file persistence, which allowed
            //editing thru the editor even if underlying file is marked as read-
            //only. The persistence code used to remove the read-only attribute
            //before writing the file.
            return;
        }
        
        Subsystem_c uut = Subsystem_c.SubsystemInstance(modelRoot);
        CanvasTestUtils.openDiagramEditor(uut);
        editor = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();

        // get the menu from the SWT Canvas
    	Menu menu = editor.getCanvas().getMenu();

    	// bend an association
    	Subsystem_c subsystem = Subsystem_c.SubsystemInstance(modelRoot, new ClassQueryInterface_c() {

            public boolean evaluate(Object candidate) {
                if(((Subsystem_c)candidate).getName().equals("Formalize Menu Tests")) {
                    return true;
                }
                return false;
            }

        });
        ModelClass_c mc = ModelClass_c.getOneO_OBJOnR2(subsystem, new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate) {
                if(((ModelClass_c)candidate).getName().equals("Class A")) {
                    return true;
                }
                return false;
            }

        });
    	Shape_c shp = CanvasEditorUtils.getShape(mc, true);
    	Connector_c con = CanvasTestUtils.getAnyConnectorAttachedToShape(shp);
    	GraphicalElement_c element = GraphicalElement_c.getOneGD_GEOnR2(con);
    	UITestingUtilities.addElementToGraphicalSelection(element.getRepresents());
    	editor.zoomSelected();
        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
    	Point center = CanvasTestUtils.getConnectorCenter(con);
    	center = CanvasTestUtils.convertToMouseCoor(center, editor.getModel());
    	CanvasTestUtils.doMousePress(center.x, center.y);
    	CanvasTestUtils.doMouseMove(center.x, center.y + 20);
    	CanvasTestUtils.doMouseRelease(center.x, center.y + 20);
    	// bend the line again
    	CanvasTestUtils.doMousePress(center.x, center.y + 20);
    	CanvasTestUtils.doMouseMove(center.x + 20, center.y + 20);
    	CanvasTestUtils.doMouseRelease(center.x + 20, center.y + 20);
    	// assert that the undo item is available
    	assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "Undo", "", m_readonly));

    	// undo the last transaction to populate the redo stack
    	// get the top level menu items
    	Action undo = mc.getTransactionManager().getUndoAction();
    	undo.run();

    	// assert that the redo item is available
    	assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "Redo", "", m_readonly));

        // Replace model file contents with the local history
        Subsystem_c ss = Subsystem_c.SubsystemInstance(modelRoot);
        if ( m_readonly ) {
            IFile file = ss.getFile();
            TestUtil.changeFileReadonlyStatus(!m_readonly, file);
        }

        IFileState[] fh = ss.getFile().getHistory(null);
        ss.getFile().setContents(fh[0], true, true, null);

    	assertTrue("The undo menu item was still available after a Replace With", UITestingUtilities.checkItemStatusInContextMenu(menu, "Undo", "", true));
    	assertTrue("The redo menu item was still available after a Replace With", UITestingUtilities.checkItemStatusInContextMenu(menu, "Redo", "", true));
    }
 }
.emit to file "../${file}";
.assign subclass = 0
.assign file = "com.mentor.nucleus.bp.core.test/src/com/mentor/nucleus/bp/core/test/ui/ContextMenuTest.java"
.assign type = "Writable"
.while(subclass < 2)
  .assign subclass = subclass + 1;
  .if(subclass == 2)
    .assign type = "Readonly"
  .end if
  .assign file = "com.mentor.nucleus.bp.core.test/src/com/mentor/nucleus/bp/core/test/ui/${type}ContextMenuTest.java"
//========================================================================
//
// File: ${file}
//
// WARNING:      Do not edit this generated file
// Generated by: arc/create_context_menu_tests.arc
// Version:      $$Revision: 1.34 $$
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
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

package com.mentor.nucleus.bp.core.test.ui;

/**
 * Extends the ContextMenuTests class
 * used to test context menu actions
 * in a ${type} environment
 */
public class ${type}ContextMenuTest extends ContextMenuTests
{

    static boolean my_first_time = true;
	public ${type}ContextMenuTest(String name) {
		super(name);
		.if(type == "Readonly")
		super.m_readonly = true;
		.end if
	}
    public void setUp() throws Exception
    {
        ContextMenuTests.first_time = my_first_time;
        super.setUp();
        my_first_time = false;
    }

}
  .emit to file "../${file}"
.end while
.//========================================================================
.//========================================================================
.function needsQueryGenerics
  .param String kl
  .param String cmeLabel
  .param String specialism
  .assign attr_needsQuery = false
  .assign attr_nameAccessor = "getName()"
  .assign attr_query = ""
  .if(kl == "S_UDT")
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "Get_name()"
  .elif(kl == "S_DPK")
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif(kl == "O_ATTR")
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif(kl == "SM_EVT")
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getMning()"
  .elif(kl == "SM_STATE")
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "S_MBR") and (cmeLabel == "Move Up"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "O_TPARM") and (cmeLabel == "Move Up"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "S_SPARM") and (cmeLabel == "Move Up"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "SM_EVTDI") and (cmeLabel == "Move Up"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "O_TFR") and (cmeLabel == "Move Up"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "S_ENUM") and (cmeLabel == "Move Up"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "S_MBR") and (cmeLabel == "Move Down"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "O_TPARM") and (cmeLabel == "Move Down"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "S_SPARM") and (cmeLabel == "Move Down"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "SM_EVTDI") and (cmeLabel == "Move Down"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "O_TFR") and (cmeLabel == "Move Down"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"
  .elif((kl == "S_ENUM") and (cmeLabel == "Move Down"))
    .assign attr_needsQuery = true
    .assign attr_nameAccessor = "getName()"   
  .end if
  .select any mclass from instances of O_OBJ where (selected.Key_Lett == kl)
  .if((cmeLabel == "Instance State Machine") or (cmeLabel == "Class State Machine"))
    .assign attr_needsQuery = true
      Package_c subsystem = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				if(((Package_c)candidate).getName().equals("Formalize Menu Tests")) {
					return true;
				}
				return false;
			}

		});

	    $r{mclass.Name}_c obj = $r{mclass.Name}_c.getOneR_RELOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(subsystem));
  .elif((cmeLabel == "Publish References") or (cmeLabel == "Unpublish References"))
     .assign attr_needsQuery = true
        String v_prefEnableInstanceReferences = "bridgepoint_prefs_enable_instance_references";
        if ((Pref_c.Getboolean(v_prefEnableInstanceReferences) == false)) {
          return;
        }
  
        .select any obj from instances of O_OBJ where (selected.Key_Lett == kl)
        $r{obj.Name}_c obj = $r{obj.Name}_c.$r{obj.Name}Instance(modelRoot);
  .elif((cmeLabel == "Formalize") or (cmeLabel == "Unformalize"))
    .if(((kl == "R_REL") or (kl == "R_ASSR")) or (kl == "R_SUB"))
      .assign attr_needsQuery = true
      .assign attr_nameAccessor = ""
      .select any mclass from instances of O_OBJ where (selected.Key_Lett == kl)
	  Package_c subsystem = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {
  
			public boolean evaluate(Object candidate) {
				if(((Package_c)candidate).getName().equals(\
				.if(cmeLabel == "Formalize")
"Formalize Menu Tests"\
				.else
"Test Subsystem"\
				.end if
)) {
					return true;
				}
				return false;
			}

		});
    .end if
    .if(kl == "R_ASSR")
		$r{mclass.Name}_c obj = $r{mclass.Name}_c.getOneR_ASSROnR211(LinkedAssociation_c.getOneR_ASSOCOnR206(Association_c.getManyR_RELsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(subsystem))));
    .elif(kl == "R_SUB")
	  	ClassAsSubtype_c obj = ClassAsSubtype_c.getOneR_SUBOnR213(SubtypeSupertypeAssociation_c.getOneR_SUBSUPOnR206(Association_c.getManyR_RELsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(subsystem))));
    .else
		$r{mclass.Name}_c obj = $r{mclass.Name}_c.getOneR_RELOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(subsystem));
    .end if
  .elif((cmeLabel == "Assign Event") or (cmeLabel == "Remove Event"))
    .assign attr_needsQuery = true
Package_c subsystem = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				if(((Package_c)candidate).getName().equals("Test Subsystem")) {
					return true;
				}
				return false;
			}

		});
		ModelClass_c mc = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(subsystem), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				if(((ModelClass_c)candidate).getName().equals(\
				.if(cmeLabel == "Remove Event")
"Class A"\
    .else
"Class B"\
    .end if
)) {
					return true;
				}
    			return false;
			}

		});
    .if("$r{mclass.Name}" == "CreationTransition")
    $r{mclass.Name}_c obj = $r{mclass.Name}_c.getOneSM_CRTXNOnR507(Transition_c.getManySM_TXNsOnR505(StateMachine_c.getOneSM_SMOnR517(InstanceStateMachine_c.getManySM_ISMsOnR518(mc))));
    .else
		$r{mclass.Name}_c obj = $r{mclass.Name}_c.getOneSM_TXNOnR505(StateMachine_c.getOneSM_SMOnR517(InstanceStateMachine_c.getManySM_ISMsOnR518(mc)));
		.end if
 .end if
.end function
.//========================================================================
.//========================================================================
.assign path = "com.mentor.nucleus.bp.core.test/src/com/mentor/nucleus/bp/core/test/ui/"
.assign file = path + "ContextMenuTestsGenerics.java";
//========================================================================
// 
// File: com.mentor.nucleus.bp.ui.canvas.test/src/com/mentor/nucleus/bp/ui/canvas/test/ContextMenuTestsGenerics.java
//
// WARNING:      Do not edit this generated file
// Generated by: ${info.arch_file_name}
// Version:      $$Revision: 1.34 $$
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
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

package com.mentor.nucleus.bp.core.test.ui;

import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.*;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.CanvasEditorUtils;

/**
 * Contains various tests involving context menu behavior
 */
public class ContextMenuTestsGenerics extends BaseTest
{
    /**
     * The editor upon which these tests operate.
     */
    private static GraphicalEditor editor = null;

    /**
     * A boolean to determine whether the test shall be performed
     * in a read only environment.
     */
    public boolean m_readonly;

    static protected boolean first_time = true;

    /**
     * Constructor.
     */

    public ContextMenuTestsGenerics(String name)
    {
        super(null, name);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() throws Exception
    {
    	super.setUp();

    	if ( first_time ) {
    	    Ooaofooa.setPersistEnabled(true);
            CorePlugin.disableParseAllOnResourceChange();
	       loadProject("ContextMenuTests");
	    	project = ResourcesPlugin.getWorkspace().getRoot().getProject("ContextMenuTests");
	    	m_sys = getSystemModel("ContextMenuTests");
            // open a class diagram editor on any subsystem
            Package_c uut = Package_c.PackageInstance(modelRoot,new Package_by_name_c("Test Subsystem"));
            CanvasTestUtils.openDiagramEditor(uut);
            editor = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
        }
    }
    
    public void testComparePaletteWithCME(){
    	Package_c obj = Package_c.PackageInstance(modelRoot , new Package_by_name_c("Test Subsystem"));
    	IFile file = obj.getFile();
    	TestUtil.changeFileReadonlyStatus(m_readonly, file);

    	UITestingUtilities.clearGraphicalSelection();
    	editor = UITestingUtilities.addElementToGraphicalSelection(obj);

    	Menu menu = null;
    	if(editor == null) {
    		// this element does not have a diagram
    		// representation, add the element to the
    		// core selection and use the explorer
    		// context menu
    		Selection.getInstance().clear();
    		Selection.getInstance().addToSelection(obj);
    		menu = getExplorerView().getTreeViewer().getControl().getMenu();
    	} else {
    		// get the menu from the SWT Canvas
    		menu = editor.getCanvas().getMenu();
    	}

    	HashMap< String, Integer> CME = new HashMap<String,Integer>();
    	CME.put( "Activity",2);
    	CME.put( "Classes",3);
    	CME.put( "Components",4);
    	CME.put( "External",5);
    	CME.put( "Interaction",6);
    	CME.put( "Types",7);
    	CME.put( "Use Case",8);

    	HashMap< String, Integer> Activity = new HashMap<String,Integer>();
    	Activity.put( "Accept Event Action",2);
    	Activity.put( "Action",3);
    	Activity.put( "Object Node",4);
    	Activity.put( "Send Signal Action",5);
    	Activity.put( "Interaction",6);
    	Activity.put( "Accept Time Event Action",7);

    	HashMap< String, Integer> Classes = new HashMap<String,Integer>();
    	Classes.put( "Class",2);
    	Classes.put( "Imported Class",3);

    	HashMap< String, Integer> Components = new HashMap<String,Integer>();
    	Components.put( "Component",2);
    	Components.put( "Component Reference",3);
    	Components.put( "Interface",4);

    	HashMap< String, Integer> External = new HashMap<String,Integer>();
    	External.put( "External Entity",2);

    	HashMap< String, Integer> Types = new HashMap<String,Integer>();
    	Types.put( "Constant Specification",2);
    	Types.put( "Enumeration Data Type",3);
    	Types.put( "Structured Data Type",4);
    	Types.put( "User Data Type",5);
    	Types.put( "Interaction",6);

    	HashMap< String, Integer> Interaction = new HashMap<String,Integer>();
    	Interaction.put( "Actor",2);
    	Interaction.put( "Class",3);
    	Interaction.put( "Component",4);
    	Interaction.put( "Instance",5);
    	Interaction.put( "External Entity",6);
    	Interaction.put( "Package Participant",7);

    	HashMap< String, Integer> Usecase = new HashMap<String,Integer>();
    	Usecase.put( "Use Case",2);


    	boolean cme_label = false;
    	boolean found = false;
    	GraphicalEditor editor = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();

    	PaletteRoot root = editor.getDomain().getPaletteViewer().getPaletteRoot();
    	List<?> children = root.getChildren();
    	for(Object paletteChild : children) {
    		if(paletteChild instanceof PaletteDrawer) {
    			PaletteDrawer drawer = (PaletteDrawer) paletteChild;
    			String drawerName = drawer.getLabel();
    			List<?> drawerChildren = drawer.getChildren();
    			for(Object drawerChild : drawerChildren) {
    				if(drawerChild instanceof ToolEntry) {
    					ToolEntry entry = (ToolEntry) drawerChild;
    					String toolName = entry.getLabel();
    					cme_label = false;
    					if (CME.get(drawerName) != null){
    						if ( drawerName.equalsIgnoreCase("Activity")){
    							if ( Activity.get(toolName)!= null){
    								cme_label = true;
    							}


    						}else if (drawerName.equalsIgnoreCase( "Classes")){
    							if ( Classes.get(toolName)!= null){
    								cme_label = true;
    							}

    						}else if (drawerName.equalsIgnoreCase( "Components")){
    							if ( Components.get(toolName)!= null){
    								cme_label = true;
    							}
    						}
    						else if (drawerName.equalsIgnoreCase( "External")){
    							if ( External.get(toolName)!= null){
    								cme_label = true;
    							}
    						}
    						else if (drawerName.equalsIgnoreCase( "Interaction")){
    							if ( Interaction.get(toolName)!= null){
    								cme_label = true;
    							}
    						}
    						else if (drawerName.equalsIgnoreCase( "Types")){
    							if ( Types.get(toolName)!= null){
    								cme_label = true;
    							}
    						}

    						else if (drawerName.equalsIgnoreCase( "Use Case")){
    							if ( Usecase.get(toolName)!= null){
    								cme_label = true;
    							}
    						}

    						if (cme_label){
    							CanvasUtilities.checkItemStatusInContextMenu(menu,
    									toolName, "", false);

    							MenuItem[] items2 = UITestingUtilities.getMenuItems(menu, "New");
    							Menu menu2 = items2[CME.get(drawerName) ].getMenu();
    							MenuItem[] items3 = UITestingUtilities.getMenuItems(menu2, "");
    							for ( int i = 0; i < items3.length; i++){
    								if ( items3[i].getText().replace(" ", "").equalsIgnoreCase(toolName.replace(" ", ""))){
    									found = true;
    									break;
    								}
    							}
    							assertTrue(found);
    							found = false;
    						}
    					}
    				}
    			}
    		} else if(paletteChild instanceof ToolEntry) {
    			String toolName = ((ToolEntry) paletteChild).getLabel();
    		}
    	}
    }
.select many cme_entries from instances of CME;
.for each cme_entry in cme_entries
  .invoke isExcluded = isExcluded(cme_entry.Key_Lett, cme_entry.Label, cme_entry.Specialism);
  .if(isExcluded.result != true)
    .assign actionName = ""
    .if(cme_entry.Specialism != "")
      .if(cme_entry.Specialism == "New")
        .assign actionName = cme_entry.Label
      .elif (cme_entry.Specialism == "Set")
        .assign actionName = cme_entry.Specialism + " "
        .assign actionName = actionName + "${cme_entry.Label}"
      .else
        .if ((cme_entry.Specialism == "Specialized Package") or (cme_entry.Specialism == "Generic Package"))
           .assign actionName = cme_entry.Label
        .else
          .assign actionName = cme_entry.Specialism + cme_entry.Label
        .end if
      .end if
    .else
      .assign actionName = cme_entry.Label;
    .end if
    .if ( cme_entry.Specialism != "Specialized Package")
    public void testContextMenu$r{cme_entry.Specialism}$r{cme_entry.Label}ActionOn${cme_entry.Key_Lett}() {
   
    .select any obj from instances of O_OBJ where (selected.Key_Lett == cme_entry.Key_Lett)
       .if  (obj.Key_Lett == "S_DOM" ) 
        Package_c obj = Package_c.PackageInstance(modelRoot , new Package_by_name_c("ContextMenuTests"));
      .elif (obj.Key_Lett  == "S_DPK")
     	Package_c obj = Package_c.PackageInstance(modelRoot , new Package_by_name_c("TestElementS_DPKforDelete"));
      .elif (obj.Key_Lett  == "S_FPK")
      	Package_c obj = Package_c.PackageInstance(modelRoot , new Package_by_name_c("TestFunctionPKG"));
      .elif (obj.Key_Lett  == "S_EEPK")
     	Package_c obj = Package_c.PackageInstance(modelRoot , new Package_by_name_c("External Entities"));
      .elif (obj.Key_Lett  == "S_SS")
      	Package_c obj = Package_c.PackageInstance(modelRoot , new Package_by_name_c("Formalize Menu Tests"));
      .elif (obj.Key_Lett  == "COMM_COMM")
      	Package_c obj = Package_c.PackageInstance(modelRoot , new Package_by_name_c("Communication"));
      .elif (obj.Key_Lett == "UC_UCC")
      	Package_c obj = Package_c.PackageInstance(modelRoot , new Package_by_name_c("UC Package"));
	  .else
    
    .invoke result = needsQueryGenerics(obj.Key_Lett, cme_entry.Label, cme_entry.Specialism);
    .if(cme_entry.Label == "Formalize")
      .invoke result = needsQueryGenerics(obj.Key_Lett, cme_entry.Label, cme_entry.Specialism);
      .assign actionName = cme_entry.Label
    .end if
    .assign name = "TestElement" + obj.Key_Lett
    .assign name = name + "for$r{actionName}"
    .if(result.needsQuery == true)
      .if(result.body == "")
        .if(( obj.Key_Lett  == "S_MBR")or(obj.Key_Lett  == "S_ENUM" ))
        $r{obj.Name}_c obj = null;
        if (BaseTest.testGlobals)
        	obj  = $r{obj.Name}_c.$r{obj.Name}Instance(Package_c.getOneEP_PKGOnR1401(m_sys,new ClassQueryInterface_c() {

                public boolean evaluate(Object candidate) {
                    if(((Package_c)candidate).getName().equals("Datatypes")) {
                        return true;
                    }
                    return false;
                }

            }).getModelRoot(), new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				if((($r{obj.Name}_c) candidate).${result.nameAccessor}.equals("${name}")) {
					return true;
				}
				return false;
			}
		});
        else 
	        obj  = $r{obj.Name}_c.$r{obj.Name}Instance(DataTypePackage_c.getOneS_DPKOnR4400(m_sys).getModelRoot(), new ClassQueryInterface_c() {
	
				public boolean evaluate(Object candidate) {
					if((($r{obj.Name}_c) candidate).${result.nameAccessor}.equals("${name}")) {
						return true;
					}
					return false;
				}
	
			});
		.else
		$r{obj.Name}_c obj  = $r{obj.Name}_c.$r{obj.Name}Instance(modelRoot, new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				if((($r{obj.Name}_c) candidate).${result.nameAccessor}.equals("${name}")) {
					return true;
				}
				return false;
			}

		});
		.end if
      .else
${result.body}
      .end if
    .else
         .if(( ((obj.Key_Lett  == "CNST_CSP") or (obj.Key_Lett  == "CNST_LSC"))or ((obj.Key_Lett  == "S_MBR") or (obj.Key_Lett  == "S_ENUM" )))  or (obj.Key_Lett  == "S_SDT"))
         $r{obj.Name}_c obj = null;
         if (BaseTest.testGlobals)
         	obj = $r{obj.Name}_c.$r{obj.Name}Instance(Package_c.getOneEP_PKGOnR1401(m_sys,new ClassQueryInterface_c() {

                public boolean evaluate(Object candidate) {
                    if(((Package_c)candidate).getName().equals("Datatypes")) {
                        return true;
                    }
                    return false;
                }

            }).getModelRoot());
         else	
         	obj = $r{obj.Name}_c.$r{obj.Name}Instance(DataTypePackage_c.getOneS_DPKOnR4400(m_sys).getModelRoot());
         
         .else
		 $r{obj.Name}_c obj = $r{obj.Name}_c.$r{obj.Name}Instance(modelRoot);
		 .end if
    .end if
.end if 
			IFile file = obj.getFile();
			TestUtil.changeFileReadonlyStatus(m_readonly, file);

    	UITestingUtilities.clearGraphicalSelection();
    	editor = UITestingUtilities.addElementToGraphicalSelection(obj);
    	
    	Menu menu = null;
    	
    	if(editor == null) {
    	  // this element does not have a diagram
    	  // representation, add the element to the
    	  // core selection and use the explorer
    	  // context menu
    	  Selection.getInstance().clear();
    	  Selection.getInstance().addToSelection(obj);
    	  menu = getExplorerView().getTreeViewer().getControl().getMenu();
    	} else {
    	  // get the menu from the SWT Canvas
    	  menu = editor.getCanvas().getMenu();
		}

    	// check the status of the action
    	.if(actionName != "")
    	  .if(cme_entry.Specialism == "New")
    	assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "${cme_entry.Label}", "${cme_entry.Specialism}", m_readonly));
    	  .else
    	assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "${cme_entry.Label}", "", m_readonly));
    	  .end if
    	.else
    	  .exit 100;
    	.end if
    }
  .end if
  .end if
.end for
    /**
     * Test the clearing of redo/undo stacks
     * after using replace with functionality
     */
    public void testClearingUndoRedoStacksAfterReplaceWith() throws CoreException {
        if(m_readonly){
            //Under MFP, this unit test is disabled as all the editors dont
            //allow editing if under lying file is read-only. This test was
            //written with respect to single file persistence, which allowed
            //editing thru the editor even if underlying file is marked as read-
            //only. The persistence code used to remove the read-only attribute
            //before writing the file.
            return;
        }
        
       Package_c uut = Package_c.PackageInstance(modelRoot , new Package_by_name_c("Formalize Menu Tests"));
        CanvasTestUtils.openDiagramEditor(uut);
        editor = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();

        // get the menu from the SWT Canvas
    	Menu menu = editor.getCanvas().getMenu();

    	// bend an association
    	    	Package_c subsystem = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {

            public boolean evaluate(Object candidate) {
                if(((Package_c)candidate).getName().equals("Formalize Menu Tests")) {
                    return true;
                }
                return false;
            }

        }); 

        ModelClass_c mc = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(subsystem), new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate) {
                if(((ModelClass_c)candidate).getName().equals("Class A")) {
                    return true;
                }
                return false;
            }

        });
    	Shape_c shp = CanvasEditorUtils.getShape(mc, true);
    	Connector_c con = CanvasTestUtils.getAnyConnectorAttachedToShape(shp);
    	GraphicalElement_c element = GraphicalElement_c.getOneGD_GEOnR2(con);
    	UITestingUtilities.addElementToGraphicalSelection(element.getRepresents());
    	editor.zoomSelected();
        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
    	Point center = CanvasTestUtils.getConnectorCenter(con);
    	center = CanvasTestUtils.convertToMouseCoor(center, editor.getModel());
    	CanvasTestUtils.doMousePress(center.x, center.y);
    	CanvasTestUtils.doMouseMove(center.x, center.y + 20);
    	CanvasTestUtils.doMouseRelease(center.x, center.y + 20);
    	// bend the line again
    	CanvasTestUtils.doMousePress(center.x, center.y + 20);
    	CanvasTestUtils.doMouseMove(center.x + 20, center.y + 20);
    	CanvasTestUtils.doMouseRelease(center.x + 20, center.y + 20);
    	// assert that the undo item is available
    	assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "Undo", "", m_readonly));

    	// undo the last transaction to populate the redo stack
    	// get the top level menu items
    	Action undo = mc.getTransactionManager().getUndoAction();
    	undo.run();

    	// assert that the redo item is available
    	assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "Redo", "", m_readonly));

        // Replace model file contents with the local history
        Package_c ss = Package_c.PackageInstance(modelRoot , new Package_by_name_c("Formalize Menu Tests"));
        if ( m_readonly ) {
            IFile file = ss.getFile();
            TestUtil.changeFileReadonlyStatus(!m_readonly, file);
        }

        IFileState[] fh = ss.getFile().getHistory(null);
        ss.getFile().setContents(fh[0], true, true, null);

    	assertTrue("The undo menu item was still available after a Replace With", UITestingUtilities.checkItemStatusInContextMenu(menu, "Undo", "", true));
    	assertTrue("The redo menu item was still available after a Replace With", UITestingUtilities.checkItemStatusInContextMenu(menu, "Redo", "", true));
    }
 }
.emit to file "../${file}";
.assign subclass = 0
.assign file = "com.mentor.nucleus.bp.core.test/src/com/mentor/nucleus/bp/core/test/ui/ContextMenuTestGenerics.java"
.assign type = "Writable"
.while(subclass < 2)
  .assign subclass = subclass + 1;
  .if(subclass == 2)
    .assign type = "Readonly"
  .end if
  .assign file = "com.mentor.nucleus.bp.core.test/src/com/mentor/nucleus/bp/core/test/ui/${type}ContextMenuTestGenerics.java"
//========================================================================
//
// File: ${file}
//
// WARNING:      Do not edit this generated file
// Generated by: arc/create_context_menu_tests.arc
// Version:      $$Revision: 1.34 $$
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
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

package com.mentor.nucleus.bp.core.test.ui;

/**
 * Extends the ContextMenuTests class
 * used to test context menu actions
 * in a ${type} environment
 */
public class ${type}ContextMenuTestGenerics extends ContextMenuTestsGenerics
{

    static boolean my_first_time = true;
	public ${type}ContextMenuTestGenerics(String name) {
		super(name);
		.if(type == "Readonly")
		super.m_readonly = true;
		.end if
	}
    public void setUp() throws Exception
    {
        ContextMenuTestsGenerics.first_time = my_first_time;
        super.setUp();
        my_first_time = false;
    }

}
  .emit to file "../${file}"
.end while