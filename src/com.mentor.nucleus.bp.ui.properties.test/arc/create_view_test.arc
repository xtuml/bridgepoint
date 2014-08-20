.//========================================================================
.//
.//File:      $RCSfile: create_view_test.arc,v $
.//Version:   $Revision: 1.47 $
.//Modified:  $Date: 2013/05/12 22:30:18 $
.//
.//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
.//
.//========================================================================
.// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
.// use this file except in compliance with the License.  You may obtain a copy 
.// of the License at
.//
.//       http://www.apache.org/licenses/LICENSE-2.0
.//
.// Unless required by applicable law or agreed to in writing, software 
.// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
.// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
.// License for the specific language governing permissions and limitations under
.// the License.
.//========================================================================
.//
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.//
.include "${mc_archetypes}/arch_utils.inc"
.function get_ref_value
  .param inst_ref child_node   .// T_TNS
  .param string child_var_name
  .//
  .if ( child_node.Key_Lett == "R_REL" )
            "R$${${child_var_name}.Numb}" },
  .elif ( child_node.Key_Lett == "O_IOBJ" )
    ..select one obj related by O_IOBJclass->O_OBJ[R101]
            "$${obj.Name}" },
  .elif ( child_node.Key_Lett == "SM_ASM" )
            "$${O_OBJclass.Name}" },
  .elif ( child_node.Key_Lett == "SM_ISM" )
            "$${O_OBJclass.Name}" },
  .elif ( child_node.Key_Lett == "SM_PEVT" )
     ..assign result = SM_PEVTclass.localEventMning; 
     ..if(result == "")
       ..assign result = "test_poly"
     ..end if
            "$${result}" },
  .elif(child_node.Key_Lett == "SM_NLEVT")
            "test_sm::B Class" },
  .elif(child_node.Key_Lett == "SM_TXN")
            "test_sm::B Class" },
  .elif ( child_node.Key_Lett == "O_RTIDA" )
     ..select one cia related by O_RTIDAclass->O_OIDA[R110]
     ..select one assoc related by O_RTIDAclass->R_RTO[R110]->R_OIR[R203]->R_REL[R201]
     ..assign attrName = cia.localAttributeName;
     ..if(attrName == "")
       ..assign attrName = "id"
     ..end if
     ..assign result = attrName + " {R"
     ..assign result = result + "$${assoc.Numb}"
     ..assign result = result + "}"
            "$${result}" },  
  .elif ( child_node.Key_Lett == "SM_SM" )
    ..select one obj related by SM_SMclass->SM_ISM[R517]->O_OBJ[R518]
    ..if ( not_empty obj )
            "$${obj.Name}" },
    ..else
      ..select one obj related by SM_SMclass->SM_ASM[R517]->O_OBJ[R519]
            "$${obj.Name}" },
    ..end if
  .elif ( child_node.Key_Lett == "SM_SUPDT")
    ..select many edi_set related by ${child_var_name}->SM_SDI[R522]->SM_EVTDI[R522];
    ..assign result = "("
    ..assign first = true
    ..for each edi in edi_set
      ..if ( not first )
        ..assign result = result + ","
      ..end if
      ..assign result = result + edi.Name
      ..assign first = false
    ..end for
            "$${result}" + ")" },
  .elif ( child_node.Key_Lett == "SM_EVT" )
    ..select one nlevt related by SM_EVTclass->SM_SEVT[R525]->SM_NLEVT[R526]
    ..if(not_empty nlevt)
      ..select one obj related by nlevt->SM_PEVT[R527]->SM_EVT[R525]->SM_SM[R502]->SM_ISM[R517]->O_OBJ[R518]
            "$${SM_EVTclass.Mning}::$${obj.Name}" },
    ..else
      ..select one obj related by SM_EVTclass->SM_SM[R502]->SM_ISM[R517]->O_OBJ[R518]
      ..if ( not_empty obj )
            "$${SM_EVTclass.Drv_Lbl}: $${SM_EVTclass.Mning}" },
      ..else
        ..select one obj related by SM_EVTclass->SM_SM[R502]->SM_ASM[R517]->O_OBJ[R519]
            "$${SM_EVTclass.Drv_Lbl}: $${SM_EVTclass.Mning}" },
      ..end if
    ..end if
  .elif ( child_node.Key_Lett == "SM_SEME" )
      ..select one evt related by SM_SEMEclass->SM_SEVT[R503]->SM_EVT[R525]
      ..select one state related by SM_SEMEclass->SM_STATE[R503]
            "State Event Matrix Entry (test_poly::B Class)" },
  .elif ( child_node.Key_Lett == "SM_ACT" )
      ..select one state related by SM_ACTclass->SM_AH[R514]->SM_MOAH[R513]->SM_STATE[R511]
      ..if(empty state)
            "Transition Action" },
      ..else
            "State Action" },
      ..end if
  .elif ( child_node.Key_Lett == "O_ID" )
      ..assign id_val = O_IDclass.Oid_ID + 1
              "*$${id_val}" },
  .elif ( child_node.Key_Lett == "S_CDT" )
      ..select one dt related by S_CDTclass->S_DT[R17]
            "$${dt.Name}" },
  .elif ( child_node.Key_Lett == "S_EEEVT" )
      ..select one ee related by S_EEEVTclass->S_EE[R10]
            "$${ee.Name}.$${S_EEEVTclass.Drv_Lbl}" },
  .elif ( child_node.Key_Lett == "R_PART" )
      ..select one obj related by R_PARTclass->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
            "$${obj.Name}" },
  .elif ( child_node.Key_Lett == "R_FORM" )
      ..select one obj related by R_FORMclass->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
            "$${obj.Name}" },
  .elif ( child_node.Key_Lett == "SM_CH" )
      ..select one evt related by SM_CHclass->SM_SEME[R504]->SM_SEVT[R503]->SM_EVT[R525]
      ..select one state related by SM_CHclass->SM_SEME[R504]->SM_STATE[R503]
            "$${evt.Drv_Lbl}/$${state.Name}" },
  .elif ( child_node.Key_Lett == "R_CONE" )
      ..select one obj related by R_CONEclass->R_OIR[R203]->O_OBJ[R201]
            "$${obj.Name}" },
  .elif ( child_node.Key_Lett == "R_COTH" )
      ..select one obj related by R_COTHclass->R_OIR[R203]->O_OBJ[R201]
            "$${obj.Name}" },
  .elif ( child_node.Key_Lett == "SM_SDI" )
      ..select one edi related by SM_SDIclass->SM_EVTDI[R522]
            "$${edi.Name}" },
  .elif ( child_node.Key_Lett == "S_UDT" )
  	..select any dt related by S_UDTclass->S_DT[R17]
  			"$${dt.Name}" },
  .elif ( child_node.Key_Lett == "S_SDT" )
  	..select any dt related by S_SDTclass->S_DT[R17]
  			"$${dt.Name}" },
  .elif ( child_node.Key_Lett == "S_EDT" )
  	..select any dt related by S_EDTclass->S_DT[R17]
  			"$${dt.Name}" },
  .elif(((child_node.Key_Lett == "SQ_CP") or (child_node.Key_Lett == "MSG_A")) or (child_node.Key_Lett == "SQ_AV"))
    				"$${${child_var_name}.InformalName}" },
  .elif((child_node.Key_Lett == "MSG_SM") or (child_node.Key_Lett == "MSG_AM"))
        "$${${child_var_name}.InformalName}" },
  .elif(child_node.Key_Lett == "MSG_ISM")
    ..select one sm related by ${child_var_name}->MSG_SM[R1020]  
        "$${sm.InformalName}" },
  .elif(child_node.Key_Lett == "MSG_IAM")
    ..select one am related by ${child_var_name}->MSG_AM[R1019]  
        "$${am.InformalName}" },
  .elif ( child_node.Key_Lett == "A_FJ" )
  			"$${${child_var_name}.GuardCondition}" },
  .elif ( child_node.Key_Lett == "A_DM" )
  			"$${${child_var_name}.DecisionInput}" },
  .elif ( child_node.Key_Lett == "A_E" )
  			"$${${child_var_name}.Guard}" },
  .else
            "$${${child_var_name}.Name}" },
  .end if
.end function
.function generateTestCase
  .param String kl
  .assign attr_result = true
  .if(kl == "S_EEDI")
    .assign attr_result = false
  .elif(kl == "CP_CP")
    .assign attr_result = false
  .elif(kl == "C_SF")
    .assign attr_result = false
  .elif(kl == "O_OIDA")
    .assign attr_result = false
  .elif(kl == "EP_PKG")
    .assign attr_result = false
  .elif(kl == "IP_IP")
    .assign attr_result = false
  .elif(kl == "C_C")
    .assign attr_result = false
  .elif(kl == "CL_IP")
    .assign attr_result = false
  .elif(kl == "CL_IR")
    .assign attr_result = false
  .elif(kl == "C_I")
    .assign attr_result = false
  .elif(kl == "C_P")
    .assign attr_result = false
  .elif(kl == "C_R")
    .assign attr_result = false
  .elif(kl == "C_AS")
    .assign attr_result = false
  .elif(kl == "C_IO")
    .assign attr_result = false
  .elif(kl == "C_DG")
    .assign attr_result = false
  .elif(kl == "C_PO")
    .assign attr_result = false
  .elif(kl == "C_PP")
    .assign attr_result = false
  .elif(kl == "CL_IC")
    .assign attr_result = false
  .elif(kl == "SPR_RO")
    .assign attr_result = false
  .elif(kl == "SPR_PO")
    .assign attr_result = false
  .elif(kl == "SPR_RS")
    .assign attr_result = false
  .elif(kl == "SPR_PS")
    .assign attr_result = false
  .elif(kl == "I_INS")
    .assign attr_result = false
  .elif(kl == "I_AVL")
    .assign attr_result = false
  .elif(kl == "I_EVI")
    .assign attr_result = false
  .elif(kl == "RV_RVL")
    .assign attr_result = false
  .elif(kl == "I_EXE")
    .assign attr_result = false
  .elif(kl == "I_LIP")
    .assign attr_result = false
  .elif(kl == "CSME_CIE")
    .assign attr_result = false
  .elif(kl == "SM_CRTXN")
    .assign attr_result = false
   .elif(kl == "CL_POR")
    .assign attr_result = false      
.// START: dts0100656086  
  .elif(kl == "S_DOM")
    .assign attr_result = false
  .elif(kl == "S_SS")
    .assign attr_result = false
  .elif(kl == "O_OBJ")
    .assign attr_result = false
  .elif(kl == "O_TFR")
    .assign attr_result = false
  .elif(kl == "O_TPARM")
    .assign attr_result = false
  .elif(kl == "SM_ISM")
    .assign attr_result = false
  .elif(kl == "SM_EVT")
    .assign attr_result = false
  .elif(kl == "SM_EVTDI")
    .assign attr_result = false
  .elif(kl == "O_ATTR")
    .assign attr_result = false
  .elif(kl == "O_REF")
    .assign attr_result = false
  .elif(kl == "SM_SEME")
    .assign attr_result = false
  .elif(kl == "S_DPK")
    .assign attr_result = false
  .elif(kl == "SQ_CIP")
    .assign attr_result = false
  .elif(kl == "SQ_CP")
    .assign attr_result = false
  .elif(kl == "SQ_PP")
    .assign attr_result = false
  .elif(kl == "S_FPK")
    .assign attr_result = false
  .elif(kl == "S_EEPK")
    .assign attr_result = false
  .elif(kl == "SQ_S")
    .assign attr_result = false
  .elif(kl == "SQ_FPP")
    .assign attr_result = false
  .elif(kl == "COMM_COMM")
    .assign attr_result = false
  .elif(kl == "UC_UCC")
    .assign attr_result = false
  .elif(kl == "A_A")
    .assign attr_result = false
.// END: dts0100656086  
  .end if
.end function
.// 
.assign path = "com/mentor/nucleus/bp/ui/properties/test"
.assign class_name = "PropertiesViewTest"
//======================================================================
//
// File: ${path}/${class_name}.java
//
// WARNING:      Do not edit this generated file
// Generated by: ${info.arch_file_name}
// Version:      $$Revision: 1.47 $$
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//======================================================================
//
// This class is responsible for determing the dependency between
// classes for displaying property changes.
//
package com.mentor.nucleus.bp.ui.properties.test;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.ui.properties.*;

public class ${class_name} extends BaseTest
{
    public ${class_name}(String name) {
        super(null, name);
    }
.select many node_set from instances of T_TNS
.for each node in node_set
  .invoke generateTestCase = generateTestCase(node.Key_Lett);
  .if(generateTestCase.result)
    .select any meta_model_obj from instances of O_OBJ where (selected.Key_Lett == node.Key_Lett)
    .invoke formatted_name = get_class_name(meta_model_obj)
    .invoke gia = get_instance_accessor(meta_model_obj)
    public void test$r{meta_model_obj.Name}_$r{node.CategoryName}Properties() throws Exception
    {
    .if ( node.Key_Lett == "S_SYS" )
       .// the SystemModel instances are in the default model root
        ${formatted_name.body} inst = ${formatted_name.body}.${gia.body}(Ooaofooa.getDefaultInstance());
    .elif ( node.Key_Lett == "S_DT" )
       .// the instances are loaded in a different order, so search for specific data type
        ${formatted_name.body} inst = ${formatted_name.body}.${gia.body}(Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate) {
                ${formatted_name.body} selected = (${formatted_name.body}) candidate;
                return selected.getName().equals("void");
            }
        });
	.elif(node.Key_Lett == "O_OBJ")
	ModelClass_c inst = ModelClass_c.ModelClassInstance(modelRoot);
	.elif(node.Key_Lett == "SM_STATE")
		StateMachineState_c inst = StateMachineState_c
				..StateMachineStateInstance(modelRoot, new ClassQueryInterface_c() {
					
					@Override
					public boolean evaluate(Object candidate) {
								ModelClass_c clazz = ModelClass_c
										..getOneO_OBJOnR518(InstanceStateMachine_c
												..getOneSM_ISMOnR517(StateMachine_c
														..getOneSM_SMOnR501((StateMachineState_c) candidate)));
								if(clazz != null && clazz.getName().equals("C class"))
									return true;
								return false;
					}
				});
    .elif((node.Key_Lett == "S_UDT") or (node.Key_Lett == "S_CDT"))
        ${formatted_name.body} inst = ${formatted_name.body}.${gia.body}(Ooaofooa.getDefaultInstance());
    .else
        ${formatted_name.body} inst = ${formatted_name.body}.${gia.body}(modelRoot);
    .end if
        $r{node.CategoryName}${meta_model_obj.Key_Lett}PropertySource ps = new $r{node.categoryName}${meta_model_obj.Key_Lett}PropertySource(inst);
        IPropertyDescriptor [] pd_set = ps.getPropertyDescriptors();
        for ( int i = 0; i < ${class_name}Data.$r{meta_model_obj.Name}_$r{node.CategoryName}.length; ++i )
        {
            assertEquals( ${class_name}Data.$r{meta_model_obj.Name}_$r{node.CategoryName}[i][0], pd_set[i].getClass().getName() );
            assertEquals( ${class_name}Data.$r{meta_model_obj.Name}_$r{node.CategoryName}[i][1], pd_set[i].getId() );
            assertEquals( ${class_name}Data.$r{meta_model_obj.Name}_$r{node.CategoryName}[i][2], pd_set[i].getDisplayName() );
    .select any descrip_attr related by meta_model_obj->O_ATTR[R102] where (selected.name == "Descrip")
    .select any act_attr related by meta_model_obj->O_ATTR[R102] where (selected.name == "Action_Semantics")
    .if ( (not_empty descrip_attr) and (not_empty act_attr) )
            if ( ${class_name}Data.$r{meta_model_obj.Name}_$r{node.CategoryName}[i][1].equals("Descrip"))
            {
                assertEquals( inst.getDescrip().replace('\n','/'), ps.getPropertyValue(pd_set[i].getId()));
            }
            else if ( ${class_name}Data.$r{meta_model_obj.Name}_$r{node.CategoryName}[i][1].equals("Action_Semantics"))
            {
                assertEquals( inst.getAction_semantics().replace('\n','/'), ps.getPropertyValue(pd_set[i].getId()));
            }
            else
            {
                assertEquals( ${class_name}Data.$r{meta_model_obj.Name}_$r{node.CategoryName}[i][3], ps.getPropertyValue(pd_set[i].getId()).toString());
            }
    .elif ( not_empty descrip_attr )
            if ( ${class_name}Data.$r{meta_model_obj.Name}_$r{node.CategoryName}[i][1].equals("Descrip"))
            {
                assertEquals( inst.getDescrip().replace('\n','/'), ps.getPropertyValue(pd_set[i].getId()));
            }
            else
            {
                assertEquals( ${class_name}Data.$r{meta_model_obj.Name}_$r{node.CategoryName}[i][3], ps.getPropertyValue(pd_set[i].getId()).toString());
            }
    .elif ( not_empty act_attr )
            if ( ${class_name}Data.$r{meta_model_obj.Name}_$r{node.CategoryName}[i][1].equals("Action_Semantics"))
            {
                assertEquals( inst.getAction_semantics().replace('\n','/'), ps.getPropertyValue(pd_set[i].getId()));
            }
            else
            {
                assertEquals( ${class_name}Data.$r{meta_model_obj.Name}_$r{node.CategoryName}[i][3], ps.getPropertyValue(pd_set[i].getId()).toString());
            }
     .else
            assertEquals( ${class_name}Data.$r{meta_model_obj.Name}_$r{node.CategoryName}[i][3], ps.getPropertyValue(pd_set[i].getId()).toString());
     .end if
        }
    }
  .end if .// ( node.Key_Lett != "S_EEDI" )
.end for
}
.emit to file "src/${path}/${class_name}.java"
.//
.// Now generate the archetype that will generate the test data!
.//
//======================================================================
//
// File: ${path}/${class_name}Data.java
//
// WARNING:      Do not edit this generated file
// Generated by: $${info.arch_file_name}
// Version:      $$$$Revision: 1.47 $$$$
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//======================================================================
///
// This class is responsible for determing the dependency between
// classes for displaying property changes.
//
package com.mentor.nucleus.bp.ui.properties.test;

import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.mentor.nucleus.bp.ui.properties.ActivityPropertyDescriptor;
import com.mentor.nucleus.bp.ui.properties.BooleanPropertyDescriptor;
import com.mentor.nucleus.bp.ui.properties.DescriptionPropertyDescriptor;
import com.mentor.nucleus.bp.ui.properties.DimensionsPropertyDescriptor;
import com.mentor.nucleus.bp.ui.properties.ChooserPropertyDescriptor;
import com.mentor.nucleus.bp.ui.properties.EnumPropertyDescriptor;
import com.mentor.nucleus.bp.ui.properties.IntegerPropertyDescriptor;
import com.mentor.nucleus.bp.ui.properties.ConstantValuePropertyDescriptor;

public class ${class_name}Data
{
.for each node in node_set
  .select any meta_model_obj from instances of O_OBJ where (selected.Key_Lett == node.Key_Lett)
  .assign mm_obj_var = "${meta_model_obj.Key_Lett}class"
  .invoke generateTestCase = generateTestCase(node.Key_Lett);
  .if(generateTestCase.result)
	public static final String[][] $r{meta_model_obj.Name}_$r{node.CategoryName} =
      {
    .select many attr_set related by meta_model_obj->O_ATTR[R102]
  .if(node.Key_Lett == "S_DT")
  ..select any ${mm_obj_var} from instances of ${meta_model_obj.Key_Lett} where (selected.Name == "void")
  .elif(node.Key_Lett == "S_UDT")
  ..select any dt from instances of S_DT where (selected.Name == "date")
  ..select one ${mm_obj_var} related by dt->S_UDT[R17];
  .else
  ..select any ${mm_obj_var} from instances of ${meta_model_obj.Key_Lett}
  .end if
  .// if the test being generated is for datatype packages
  .// there will be two instances, one for the system level dts and one
  .// for the domain level dts
  .// here we need to use the second as it is the instance that the
  .// test is expecting
  .if(node.Key_Lett == "S_DPK")
  ..select many ${mm_obj_var}s from instances of ${meta_model_obj.Key_Lett}
  ..for each mobj in ${mm_obj_var}s
    ..if(last ${mm_obj_var}s)
      ..assign ${mm_obj_var} = mobj
    ..end if
  ..end for
  .end if
  ..print "Generating class ${meta_model_obj.Name} for $r{node.CategoryName}"
    .for each attr in attr_set
      .if(("$l{attr.Descrip:In Properties}" == "true") or ("$l{attr.Descrip:In Properties}" == ""))
        .select one dt related by attr->S_DT[R114]
        .select one edt related by dt->S_EDT[R17]
        .select one rattr related by attr->O_RATTR[R106]
        .if (((dt.Name != "unique_id") and (empty rattr)) and ("$l{attr.Descrip:User_Visible}" != "false"))
			{
          .assign readonly = "$l{attr.Descrip:readonly}"
          .select one dbattr related by attr->O_BATTR[R106]->O_DBATTR[R107]
          .if ( ((not_empty dbattr) or (readonly == "true")) and (attr.Name != "Action_Semantics") )
            .if ( ("${attr.Descrip:enum0}" == "") and (empty edt) )
                PropertyDescriptor.class.getName(),
            .else
                EnumPropertyDescriptor.class.getName(),
            .end if
          .else
            .if (dt.Name == "boolean" )
               BooleanPropertyDescriptor.class.getName(),
            .elif ((dt.Name == "integer") or (not_empty edt))
              .if ( ("${attr.Descrip:enum0}" == "") and (empty edt) )
               IntegerPropertyDescriptor.class.getName(),
              .else
                EnumPropertyDescriptor.class.getName(),
              .end if
            .elif (dt.Name == "string" )
              .if (attr.Name == "Descrip" )
               DescriptionPropertyDescriptor.class.getName(),
              .elif ( attr.Name == "Action_Semantics" )
               ActivityPropertyDescriptor.class.getName(),
              .elif (( attr.Name == "Dimensions" ) or ( attr.Name == "Return_Dimensions"))
               DimensionsPropertyDescriptor.class.getName(),
              .elif (( attr.Name == "Value" ) and ( node.Key_Lett == "CNST_LSC"))
               ConstantValuePropertyDescriptor.class.getName(),
              .else
               .//
               .// The default datatype package "Datatypes" has a read-only
               .// name attribute.
               .// This corresponds to the 'canRename' special case in
               .// BuildPropertySource.arc
                 .if ( (meta_model_obj.Key_Lett == "S_DPK") and (attr.Name == "Name") )
               PropertyDescriptor.class.getName(),
                 .else
               TextPropertyDescriptor.class.getName(),
              .end if
            .end if
          .end if
        .end if
				"${attr.Name}",
				"${attr.Descrip:Full Name}",
        .if (attr.Name == "Descrip" )
				"" },
        .elif ( attr.Name == "Action_Semantics" )
				"" },
        .else
          .if ( dt.Name == "boolean" )
            .if((meta_model_obj.Key_Lett == "S_EE") and (attr.Name == "isRealized"))
				"true" },            
            .else
				"$$l{${mm_obj_var}.${attr.Name}}" },
		    .end if
		  .elif ( ("${attr.Descrip:enum0}" != "") or (not_empty edt) )
            .if ( attr.Name == "Is_Lbl_U" )
				EnumRangeTest.${meta_model_obj.Key_Lett}_${attr.name}_vals[$${${mm_obj_var}.${attr.Name}}] },
		    .else
				EnumRangeTest.${attr.name}_vals[$${${mm_obj_var}.${attr.Name}}] },
            .end if
          .else
              .if(node.Key_Lett == "SM_NLEVT")
                .if(attr.Name == "Name")
        ..select one polyClass related by ${mm_obj_var}->SM_PEVT[R527]->SM_EVT[R525]->SM_SM[R502]->SM_ISM[R517]->O_OBJ[R518]
        ..select one evt related by ${mm_obj_var}->SM_SEVT[R526]->SM_EVT[R525]
				"$${evt.Mning}::$${polyClass.Name}" },
                .end if
              .else
				"$${${mm_obj_var}.${attr.Name}}" },
				      .end if
            .end if
          .end if
        .end if
      .end if
    .end for
    .//
    .//  Now do the referential attributes
    .//
    .select many child_specs related by node->T_TPS[R1000] where (selected.ExcludedFromProperties == false)
    .for each child in child_specs
      .select one child_node related by child->T_TNS[R1001]
      .select any child_class from instances of O_OBJ where (selected.Key_Lett == child_node.Key_Lett)
      .assign child_var_name = "${child_class.Key_Lett}class"
  ..select many ${child_var_name}_set related by ${mm_obj_var}${child.ParentChildRelChain}
  ..assign count = 0
.//
  	  .if ((((node.Key_Lett == "S_CDT") or ( node.Key_Lett == "S_UDT")) or (node.Key_Lett == "S_SDT")) and ((child_node.Key_Lett == "S_DT") and (child.NameOnly == "")) )
    //Skipping special case for core data types and user data types.
      .elif ( (node.Key_Lett == "CNST_CSP") and (child_node.Key_Lett == "CNST_LSC") )
    //Skipping special case for constants
  	  .elif (node.Key_Lett == "S_SYS")
    //Skipping special case for System Model
  	 .else
.//
  ..for each ${child_var_name} in ${child_var_name}_set
     	.if ( (child_node.Key_Lett == "S_DT") and (node.Key_Lett != "S_EDT"))
            { ChooserPropertyDescriptor.class.getName(),
            "$r{child_node.CategoryName}",
      .else
            { PropertyDescriptor.class.getName(),
            "$r{child_node.CategoryName}$r{child.NameOnly}.$${count}",
      .end if
      .if ( child.NameOnly != "" )
            "${child.NameOnly}",
      .else
            "${child_class.name}",
      .end if
      .invoke grv = get_ref_value( child_node, child_var_name )
${grv.body}\
    ..assign count = count + 1
  ..end for
  	  .end if
    .end for
      };
  .end if   .// not S_EEDI
.end for
}
..emit to file "src/${path}/${class_name}Data.java"
.emit to file "arc/create_view_test_data.arc"
