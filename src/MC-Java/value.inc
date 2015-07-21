.//====================================================================
.//
.// File:      $RCSfile: value.inc,v $
.// Version:   $Revision: 1.20.32.1 $
.// Modified:  $Date: 2013/07/19 12:26:00 $
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
  .param string root_pkg_name
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
    .select one brg related by brv->S_BRG[R828]
    .select many parms related by brv->V_PAR[R810]
    .invoke result = gen_bridge_value(root_pkg_name, p_val, brg, parms)
    .assign p_val.buffer = "${result.body}"
  .end if
  .select one trv related by p_val->V_TRV[R801]
  .if (not_empty trv)
    .// this one is special so that when called from traverse_where_clause
    .//  (statement.inc) we don't overwrite the value used by the query class
    .if ( p_val.buffer == "" )
      .select one tfm related by trv->O_TFR[R829]
      .select many parms related by trv->V_PAR[R811]
      .invoke result = gen_transform_value(root_pkg_name, trv, tfm, parms)
      .assign p_val.buffer = "${result.body}"
    .end if
  .end if
  .select one func_val related by p_val->V_FNV[R801]
  .if (not_empty func_val)
    .select one func related by func_val->S_SYNC[R827]
    .select many parms related by func_val->V_PAR[R817]
    .invoke result = gen_synch_service_value(root_pkg_name, p_val, func, parms)
    .assign p_val.buffer = "${result.body}"
  .end if
  .select one unary_op related by p_val->V_UNY[R801]
  .if (not_empty unary_op)
    .invoke result = gen_unary_op_value(root_pkg_name, p_val)
    .assign p_val.buffer = "${result.body}"
  .end if
  .select one binary_op related by p_val->V_BIN[R801]
  .if (not_empty binary_op)
    .invoke result = gen_binary_op_value(root_pkg_name, p_val)
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
    .select any dt from instances of S_DT where ( selected.DT_ID == val.DT_ID )
    .invoke val_type = get_enumeration_class_name( dt )
    .invoke en = get_enumerator_name_from_value(value)
    .assign val.buffer = "${val_type.body}.${en.body}"
  .end for
.end function
.//
.function gen_transient_values
  .select many values from instances of V_TVL
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .select one var related by value->V_VAR[R805]
    .if ( "$l{var.Name}" == "self" )
      .assign val.buffer = "this"
    .else
      .assign val.buffer = "v_${var.Name}"
    .end if
  .end for
.end function
.//
.function gen_attribute_values
  .select many values from instances of V_AVL
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .select one attr related by value->O_ATTR[R806]
    .invoke aa = get_attribute_accessor(attr)
    .select one irf related by value->V_VAL[R807]->V_IRF[R801]
    .select one slr related by value->V_VAL[R807]->V_SLR[R801]
    .if (not_empty irf)
      .select one var related by irf->V_VAR[R808]
      .if ("$l{var.Name}" != "self")
        .assign val.buffer = "v_${var.Name}.${aa.body}()"
      .else
        .assign val.buffer = "${aa.body}()"
      .end if
    .elif (not_empty slr)
      .assign val.buffer = "selected.${aa.body}()"
    .else
      .select one obj related by attr->O_OBJ[R102]
      .print "ERROR: In attribute value reference ${obj.Name}.${attr.Name}. Unexpected root instance type in gen_attribute_values."
    .end if
  .end for
.end function
.//
.function gen_instance_reference_values
  .select many values from instances of V_IRF
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .select one var related by value->V_VAR[R808]
    .if ("$l{var.Name}" != "self")
      .assign val.buffer = "v_${var.Name}"
    .else
      .assign val.buffer = "this"
    .end if
  .end for
.end function
.//
.function gen_selection_test_values
  .select many values from instances of V_SLR
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .//invoke aa = get_attribute_accessor_from_str( value.Variable_Name )
    .assign val.buffer = "selected"
  .end for
.end function
.//
.function gen_inst_ref_set_values
  .select many values from instances of V_ISR
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .select one var related by value->V_VAR[R809]
    .assign val.buffer = "v_${var.Name}"
  .end for
.end function
.//
.function gen_event_values
  .select many values from instances of V_EDV
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .select any epr related by value->V_EPR[R834]
    .select any evtdi related by epr->SM_EVTDI[R846]
    .assign val.buffer = "arch_event.edi_$l_{evtdi.Name}"
  .end for
.end function
.//
.function gen_parameter_values
  .select many values from instances of V_PVL
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .select one sparm related by value->S_SPARM[R832]
    .select one oparm related by value->O_TPARM[R833]
    .if (not_empty sparm)
      .assign val.buffer = "p_$cr{sparm.Name}"
    .elif (not_empty oparm)
      .assign val.buffer = "p_$cr{oparm.Name}"
    .end if
  .end for
.end function
.//
.function gen_unary_op_values
  .param string root_pkg_name
  .select many values from instances of V_VAL
  .select many unary_ops related by values->V_UNY[R801]
  .for each unary_op in unary_ops
    .select one val related by unary_op->V_VAL[R801]
    .invoke result = gen_unary_op_value(root_pkg_name, val)
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
  .param string root_pkg_name
  .param inst_ref val
  .select one unary_op related by val->V_UNY[R801]
  .if (not_empty unary_op)
    .select one p_val related by unary_op->V_VAL[R804]
    .invoke gen_value(root_pkg_name, p_val)
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
  !(${p_val.buffer})\
    .else
  ${unary_op.operator} (${p_val.buffer})\
    .end if
  .else
    ${val.buffer}\
  .end if
.end function
.//
.function gen_binary_op_values
  .param string root_pkg_name
  .select many binary_ops from instances of V_BIN
  .for each binary_op in binary_ops
    .select one val related by binary_op->V_VAL[R801]
    .invoke result = gen_binary_op_value(root_pkg_name, val)
    .assign val.buffer = "${result.body}"
  .end for
.end function
.//
.function gen_binary_op_value
  .param string root_pkg_name
  .param inst_ref val
  .select one binary_op related by val->V_BIN[R801]
  .select one l_val related by binary_op->V_VAL[R802]
  .select one r_val related by binary_op->V_VAL[R803]
  .invoke gen_value(root_pkg_name, l_val)
  .invoke gen_value(root_pkg_name, r_val)
  .select one dt related by l_val->S_DT[R820]
  .if (("${binary_op.operator}" == "==") AND ((dt.Name == "string") OR (dt.Name == "unique_id")))
    .// Test for string equality using the equals method otherwise
    .// test will fail unless strings are exactly the same instance
(${l_val.buffer}.equals(${r_val.buffer}))\
  .elif (("${binary_op.operator}" == "!=") AND ((dt.Name == "string") OR (dt.Name == "unique_id")))
(! ${l_val.buffer}.equals(${r_val.buffer}))\
  .elif ((dt.Name == "string") AND ((("${binary_op.operator}" == ">") OR ("${binary_op.operator}" == "<")) OR (("${binary_op.operator}" == ">=") OR ("${binary_op.operator}" == "<="))))
(${l_val.buffer}.compareTo(${r_val.buffer}) ${binary_op.operator} 0)\
  .else
    .if ("$Ur{binary_op.operator}" == "AND")
      .assign binary_op.operator = "&&"
    .end if
    .if ("$Ur{binary_op.operator}" == "OR")
      .assign binary_op.operator = "||"
    .end if
(${l_val.buffer} ${binary_op.operator} ${r_val.buffer})\
  .end if
.end function
.//
.function gen_bridge_values
  .param string root_pkg_name
  .select many values from instances of V_BRV
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .select one bridge related by value->S_BRG[R828]
    .select many parameters related by value->V_PAR[R810]
    .invoke result = gen_bridge_value(root_pkg_name, val, bridge, parameters)
    .assign val.buffer = "${result.body}"
  .end for
.end function
.//
.function gen_bridge_value
  .param string root_pkg_name
  .param inst_ref value
  .param inst_ref bridge
  .param inst_ref_set parameters
  .select one ee related by bridge->S_EE[R19]
  .invoke eecn = get_ee_class_name( ee )
  .invoke params = gen_parameter_list(root_pkg_name, parameters, false )
${eecn.body}.$cr{bridge.Name}(${params.body})\
.end function
.//
.function gen_transform_values
  .param string root_pkg_name
  .select many values from instances of V_TRV
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .select one tfm related by value->O_TFR[R829]
    .select many parameters related by value->V_PAR[R811]
    .invoke result = gen_transform_value(root_pkg_name, value, tfm, parameters)
    .assign val.buffer = "${result.body}"
  .end for
.end function
.//
.function gen_transform_value
  .param string root_pkg_name
  .param inst_ref value
  .param inst_ref oper
  .param inst_ref_set parameters
  .assign root_arg_included = false
  .select any tfmobj related by oper->O_OBJ[R115]
  .if (oper.Instance_Based != 1)
    .assign root_arg_included = true
    .invoke cn = get_class_name( tfmobj )
${cn.body}.$cr{oper.Name}(modelRoot\
  .else
    .select one var related by value->V_VAR[R830]
    .if (not_empty var)
      .if ("$l{var.Name}" != "self")
v_${var.Name}.\
      .end if
    .end if
$cr{oper.Name}(\
  .end if
  .invoke params = gen_parameter_list(root_pkg_name, parameters, root_arg_included )
${params.body})\
.end function
.//
.function gen_synch_service_values
  .param string root_pkg_name
  .select many values from instances of V_FNV
  .for each value in values
    .select one val related by value->V_VAL[R801]
    .select one func related by value->S_SYNC[R827]
    .select many parameters related by value->V_PAR[R817]
    .invoke result = gen_synch_service_value(root_pkg_name, val,func,parameters)
    .assign val.buffer = "${result.body}"
  .end for
.end function
.//
.function gen_synch_service_value
  .param string root_pkg_name
  .param inst_ref value
  .param inst_ref func
  .param inst_ref_set parameters
  .//
  .select any root_pkg from instances of EP_PKG where (selected.Name == root_pkg_name)
  .invoke rpn_result = get_root_pkg_name( root_pkg )
  .invoke params = gen_parameter_list(root_pkg_name, parameters, true )
${rpn_result.body}.$cr{func.Name}(modelRoot${params.body})\
.end function
.//
