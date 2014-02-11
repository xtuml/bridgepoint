.//====================================================================
.//
.// File:      $RCSfile: value.inc,v $
.// Version:   $Revision: 1.20 $
.// Modified:  $Date: 2013/01/10 23:16:29 $
.//
.// (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.// This set of functions is run through translate_oal.inc (in java.arc)
.// _prior_ to the functions in block.inc and statement.arc
.// These functions set up the implementation of the values (V_VAL on
.// OOA of BPAL) Each of the functions is fairly clean, with the
.// exception of  unary_op, and bridge - they look too complicated and
.// might be able to be simplified.
.//
.//==================================================================== 
.//
.//
.//==================================================================== 
.//
.function gen_value
  .param inst_ref p_val
  .//
  .// Note:- this function must offer conversions for
  .// all value types BELOW AND INCLUDING the first 
  .// function in (translate_oal.inc) translate_values that 
  .// uses this utility function. Otherwise, buffers 
  .// may not have been initialized due to ordering 
  .// problems.
  .//
  .select one brv related by p_val->V_BRV[R801]
  .if (not_empty brv)
    .select one brg related by brv->ACT_BRG[R810]
    .select many parms related by brg->V_PAR[R628]
    .invoke result = gen_bridge_value(p_val, brg, parms)
    .assign p_val.buffer = "${result.body}"
  .end if
  .select one trv related by p_val->V_TRV[R801]
  .if (not_empty trv)
    .// this one is special so that when called from traverse_where_clause
    .//  (statement.inc) we don't overwrite the value used by the query class
    .if ( p_val.buffer == "" )
      .select one tfm related by trv->ACT_TFM[R811]
      .select many parms related by tfm->V_PAR[R627]
      .invoke result = gen_transform_value(p_val, tfm, parms)
      .assign p_val.buffer = "${result.body}"
    .end if
  .end if
  .select one func_val related by p_val->V_FNC[R801]
  .if (not_empty func_val)
    .select one func related by func_val->ACT_FNC[R817]
    .select many parms related by func->V_PAR[R669]
    .invoke result = gen_synch_service_value(p_val, func, parms)
    .assign p_val.buffer = "${result.body}"
  .end if
  .select one unary_op related by p_val->V_UNY[R801]
  .if (not_empty unary_op)
    .invoke result = gen_unary_op_value(p_val)
    .assign p_val.buffer = "${result.body}"
  .end if
  .select one binary_op related by p_val->V_BIN[R801]
  .if (not_empty binary_op)
    .invoke result = gen_binary_op_value(p_val)
    .assign p_val.buffer = "${result.body}"
  .end if
  .select one paren_expr related by p_val->V_PEX[R801]
  .if (not_empty paren_expr)
    .invoke result = gen_paren_expr_value(p_val)
    .assign p_val.buffer = "${result.body}"
  .end if
.end function
.//
.function gen_literal_boolean_values
  .select many values from instances of V_LBO
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .assign val.buffer = "$l{value.value}"
  .end for
.end function
.//
.function gen_literal_string_values
  .select many values from instances of V_LST
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .assign val.buffer = """${value.value}"""
  .end for
.end function
.//
.function gen_literal_integer_values
  .select many values from instances of V_LIN
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .assign val.buffer = "$l{value.value}"
  .end for
.end function
.//
.function gen_literal_real_values
  .select many values from instances of V_LRL
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .assign val.buffer = "$l{value.value}f"
  .end for
.end function
.//
.function gen_literal_enumerations
  .select many values from instances of V_LEN
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .select any dt from instances of S_DT where ( "${selected.DT_ID}" == val.DT_ID )
    .invoke val_type = get_enumeration_class_name( dt )
    .invoke en = get_enumerator_name_from_str( value.Value )
    .assign val.buffer = "${val_type.body}.${en.body}"
  .end for
.end function
.//
.function gen_transient_values
  .select many values from instances of V_TVL
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .if ( "$l{value.Variable_Name}" == "self" )
      .assign val.buffer = "this"
    .else
      .assign val.buffer = "v_${value.Variable_Name}"
    .end if
  .end for
.end function
.//
.function gen_attribute_values
  .select many values from instances of V_AVL
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .invoke aa = get_attribute_accessor_from_str( value.Member_Variable_Name )
    .if ("$l{value.Instance_Variable_Name}" != "self")
      .assign val.buffer = "v_${value.Instance_Variable_Name}.${aa.body}()"
    .else
      .assign val.buffer = "${aa.body}()"
    .end if
  .end for
.end function
.//
.function gen_instance_reference_values
  .select many values from instances of V_IRF
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .assign val.buffer = "v_${value.Variable_Name}"
  .end for
.end function
.//
.function gen_selection_test_values
  .select many values from instances of V_SLR
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .invoke aa = get_attribute_accessor_from_str( value.Variable_Name )
    .assign val.buffer = "selected.${aa.body}()"
  .end for
.end function
.//
.function gen_inst_ref_set_values
  .select many values from instances of V_ISR
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .assign val.buffer = "v_${value.Variable_Name}"
  .end for
.end function
.//
.function gen_event_values
  .select many values from instances of V_EDV
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .select any epr related by value->V_EPR[R834]
    .select any evtdi from instances of SM_EVTDI where ("${selected.SMedi_ID}" == epr.SMedi_ID)
    .assign val.buffer = "arch_event.edi_$l_{evtdi.Name}"
  .end for
.end function
.//
.function gen_parameter_values
  .select many values from instances of V_PVL
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .assign val.buffer = "p_$cr{value.Parameter_Name}"
  .end for
.end function
.//
.function gen_unary_op_values
  .select many values from instances of V_VAL
  .select many unary_ops related by values->V_UNY[R801]
  .for each unary_op in unary_ops
    .select one val related by unary_op->V_VAL[R801]
    .invoke result = gen_unary_op_value(val)
    .assign val.buffer = "${result.body}"
  .end for
.end function
.//
.// The following function needs to be broken up better.  unary_op
.// needs to be subtyped into the many cases:
.//  - empty, not_empty, cardinality for both
.//    inst_ref and inst_ref_set
.//
.function gen_unary_op_value
  .param inst_ref val
  .select one unary_op related by val->V_UNY[R801]
  .if (not_empty unary_op)
    .select one p_val related by unary_op->V_VAL[R804]
    .invoke gen_value(p_val)
    .if ( "$Ur{unary_op.operator}" == "NOT_EMPTY" )
      .select one ir_val related by p_val->V_IRF[R801]
      .if (not_empty ir_val)
  (${p_val.buffer} != null)\
      .else
  (${p_val.buffer}.length != 0)\
      .end if
    .elif ("$Ur{unary_op.operator}" == "EMPTY")
      .select one ir_val related by p_val->V_IRF[R801]
      .if (not_empty ir_val)
  (${p_val.buffer} == null)\
      .else
  (${p_val.buffer}.length == 0)\
      .end if
    .elif ("$Ur{unary_op.operator}" == "CARDINALITY")
      .select one ir_val related by p_val->V_IRF[R801]
      .if (not_empty ir_val)
        .print "Error, cardinality on non-set variables not supported in this architecture"
           >>>>>>>>>>>> Error >>>>>>>>>>>>>>>>>>>> ${p_val.body} == null\
      .else
  (${p_val.buffer}.length)\
      .end if
    .elif ("$Ur{unary_op.operator}" == "NOT")
  !${p_val.buffer}\
    .else
  ${unary_op.operator} ${p_val.buffer}\
    .end if
  .else
    ${val.buffer}\
  .end if
.end function
.//
.function gen_binary_op_values
  .select many binary_ops from instances of V_BIN
  .for each binary_op in binary_ops
    .select one val related by binary_op->V_VAL[R801]
    .invoke result = gen_binary_op_value(val)
    .assign val.buffer = "${result.body}"
  .end for
.end function
.//
.function gen_binary_op_value
  .param inst_ref val
  .select one binary_op related by val->V_BIN[R801]
  .if (not_empty binary_op)
    .select one l_val related by binary_op->V_VAL[R802]
    .select one r_val related by binary_op->V_VAL[R803]
    .invoke gen_value(l_val)
    .invoke gen_value(r_val)
    .select any dt from instances of S_DT where ( "${selected.DT_ID}" == val.DT_ID )
    .if (("${binary_op.operator}" == "==") AND ((dt.Name == "string") OR (dt.Name == "unique_id")))
      .// Test for string equality using the equals method otherwise
      .// test will fail unless strings are exactly the same instance
${l_val.buffer}.equals(${r_val.buffer})\
    .elif (("${binary_op.operator}" == "!=") AND ((dt.Name == "string") OR (dt.Name == "unique_id")))
! ${l_val.buffer}.equals(${r_val.buffer})\
    .elif ((dt.Name == "string") AND ((("${binary_op.operator}" == ">") OR ("${binary_op.operator}" == "<")) OR (("${binary_op.operator}" == ">=") OR ("${binary_op.operator}" == "<="))))
${l_val.buffer}.compareTo(${r_val.buffer}) ${binary_op.operator} 0\
    .else
      .if ("$Ur{binary_op.operator}" == "AND")
	    .assign binary_op.operator = "&&"
      .end if
      .if ("$Ur{binary_op.operator}" == "OR")
        .assign binary_op.operator = "||"
      .end if
${l_val.buffer} ${binary_op.operator} ${r_val.buffer}\
    .end if
  .else
${val.buffer}\
  .end if
.end function
.//
.function gen_bridge_values
.select many values from instances of V_BRV
.for each value in values
  .select one val related by value->V_VAL[R801]
  .select one bridge related by value->ACT_BRG[R810]
  .select many parameters related by bridge->V_PAR[R628]
  .invoke result = gen_bridge_value(val,bridge,parameters)
  .assign val.buffer = "${result.body}"
.end for
.end function
.//
.function gen_bridge_value
  .param inst_ref value
  .param inst_ref bridge
  .param inst_ref_set parameters
  .invoke eecn = get_ee_class_name_from_str( bridge.External_Entity_KL )
  .invoke params = gen_parameter_list( parameters, false )
${eecn.body}.$cr{bridge.Method_Name}(${params.body})\
.end function
.//
.function gen_transform_values
.select many values from instances of V_TRV
.for each value in values
  .select one val related by value->V_VAL[R801]
  .select one tfm related by value->ACT_TFM[R811]
  .select many parameters related by tfm->V_PAR[R627]
  .invoke result = gen_transform_value(val,tfm,parameters)
  .assign val.buffer = "${result.body}"
.end for
.end function
.//
.function gen_transform_value
  .param inst_ref value
  .param inst_ref transform
  .param inst_ref_set parameters
  .assign root_arg_included = false
  .select any tfmobj from instances of O_OBJ where (selected.Key_Lett == transform.Object_KL)
  .select any oper related by tfmobj->O_TFR[R115] where (selected.Name == transform.Method_Name)
  .if (oper.Instance_Based != 1)
    .assign root_arg_included = true
    .invoke cn = get_class_name( tfmobj )
${cn.body}.$cr{transform.Method_Name}(modelRoot\
  .else
    .if ("$l{transform.Variable_Name}" != "self")
v_${transform.Variable_Name}.\
    .end if
$cr{transform.Method_Name}(\
  .end if
  .invoke params = gen_parameter_list( parameters, root_arg_included )
${params.body})\
.end function
.//
.function gen_synch_service_values
.select many values from instances of V_FNC
.for each value in values
  .select one val related by value->V_VAL[R801]
  .select one func related by value->ACT_FNC[R817]
  .select many parameters related by func->V_PAR[R669]
  .invoke result = gen_synch_service_value(val,func,parameters)
  .assign val.buffer = "${result.body}"
.end for
.end function
.//
.function gen_synch_service_value
  .param inst_ref value
  .param inst_ref func
  .param inst_ref_set parameters
  .//
  .select any dom from instances of S_DOM
  .invoke gdn_result = get_domain_name( dom )
  .invoke params = gen_parameter_list( parameters, true )
${gdn_result.body}.$cr{func.Function_Name}(modelRoot${params.body})\
.end function
.//
.function gen_paren_expr_values
  .select many paren_exprs from instances of V_PEX
  .for each paren_expr in paren_exprs
    .select one val related by paren_expr->V_VAL[R801]
    .invoke result = gen_paren_expr_value(val)
    .assign val.buffer = "${result.body}"
  .end for
.end function
.//
.function gen_paren_expr_value
  .param inst_ref val
  .select one paren_expr related by val->V_PEX[R801]
  .if (not_empty paren_expr)
    .select one p_val related by paren_expr->V_VAL[R824]
    .invoke gen_value(p_val)
(${p_val.buffer})\
  .else
    .print "ERROR:  A non-PEX passed to gen_paren_expr_value"
    .exit 100
  .end if
.end function
.//
