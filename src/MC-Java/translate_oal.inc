.//====================================================================
.//
.// File:      translate_oal.inc
.//
.// Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.//    Purpose:    This file contains the functions that generate
.//                action language from the populated OOA of BPAL.
.//
.//    Functions:  translate_values
.//                translate_control_statements
.//                translate_other_statements
.//
.//============================================================================
.//
.//  These functions are part one of a two-step translation of
.//  the action language from the OOA of BPAL.  Each of the invoked
.//  functions below translate a specific part of the BPAL.  For
.//  example the gen_literal_boolean_values() function generates all
.//  of the code for each literal boolean value found in all of the
.//  actions for a domain.  The function gen_if_statements()
.//  generates the code for all of the if statements found in the
.//  actions for a domain.  
.//
.//  Each of the functions places the generated code for its'
.//  element in an attribute of the supertype, called 'buffer'.
.//  In the case of a value, the supertype is V_VAL, for a
.//  statement, the supertype is ACT_SMT.
.//
.//  Once all of the values and statements have been translated,
.//  and all of the code is in the buffers,  the function
.//  blck_xlate is called to order the statements.
.//
.//============================================================================
.//
.function translate_values
  .param string root_pkg_name
  .print "************** generate literal booleans            *****************"
  .invoke gen_literal_boolean_values()
  .print "************** generate literal integers            *****************"
  .invoke gen_literal_integer_values()
  .print "************** generate literal reals               *****************"
  .invoke gen_literal_real_values()
  .print "************** generate literal strings             *****************"
  .invoke gen_literal_string_values()
  .print "************** generate literal enums               *****************"
  .invoke gen_literal_enumerations()
  .print "************** generate attribute values            *****************"
  .invoke gen_attribute_values()
  .print "************** generate transient values            *****************"
  .invoke gen_transient_values()
  .print "************** generate inst_ref values             *****************"
  .invoke gen_instance_reference_values()
  .print "************** generate selection values            *****************"
  .invoke gen_selection_test_values()
  .print "************** generate inst_ref sets               *****************"
  .invoke gen_inst_ref_set_values()
  .print "************** generate event values                *****************"
  .invoke gen_event_values()
  .print "************** generate parameter values            *****************"
  .invoke gen_parameter_values()
  .print "************** generate binary operation values     *****************"
  .invoke gen_binary_op_values(root_pkg_name)
  .print "************** generate unary operation values      *****************"
  .invoke gen_unary_op_values(root_pkg_name)
  .print "************** generate bridge return values        *****************"
  .invoke gen_bridge_values(root_pkg_name)
  .print "************** generate operation return values     *****************"
  .invoke gen_transform_values(root_pkg_name)
  .print "************** generate function return values      *****************"
  .invoke gen_synch_service_values(root_pkg_name)
.end function
.//
.//
.function translate_control_statements
  .print "************** generate if statements               *****************"
  .invoke gen_if_statements()
  .print "************** generate else statements             *****************"
  .invoke gen_else_statements()
  .print "************** generate for statements              *****************"
  .invoke gen_for_statements()
  .print "************** generate while statements            *****************"
  .invoke gen_while_statements()
  .print "************** generate else if statements          *****************"
  .invoke gen_elif_statements()
.end function
.//
.function translate_other_statements
  .param string root_pkg_name
  .param string main_class_name
  .param boolean output_change_messages
  .print "************** generate assignments                 *****************"
  .invoke gen_asgn_statements()
  .print "************** generate creates                     *****************"
  .invoke gen_create_instance_statements(main_class_name, output_change_messages)
  .print "************** generate deletes                     *****************"
  .invoke gen_delete_instance_statements(main_class_name, output_change_messages)
  .print "************* generate event creates               *****************"
  .invoke gen_create_event_instance_statements()
  .print "************* generate relates                     *****************"
  .invoke gen_relate_statements()
  .print "************* generate unrelates                   *****************"
  .invoke gen_unrelate_statements()
  .print "************* generate select from instances       *****************"
  .invoke gen_select_statements()
  .print "************* generate select from instances where *****************"
  .invoke gen_select_inst_where_statements(root_pkg_name)
  .print "************* generate select rel                  *****************"
  .invoke gen_select_inst_rel_by_statements()
  .print "************* generate select rel where            *****************"
  .invoke gen_select_rel_where_statements(root_pkg_name)
  .print "************* generate relate using                *****************"
  .invoke gen_relate_using_statements()
  .print "************* generate unrelate using              *****************"
  .invoke gen_unrelate_using_statements()
  .print "************* generate ism events                  *****************"
  .invoke gen_event_gen_statements(root_pkg_name)
  .print "************* generate csm events                  *****************"
  .invoke gen_event_gen_ass_statements(root_pkg_name)
  .print "************* generate operations                  *****************"
  .invoke gen_transform_statements(root_pkg_name)
  .print "************* generate bridges                     *****************"
  .invoke gen_bridge_statements(root_pkg_name)
  .print "************* generate functions                   *****************"
  .invoke gen_function_statements(root_pkg_name)
  .print "************* generate returns                     *****************"
  .invoke gen_return_statements()
  .print "************* generate arch controls               *****************"
  .invoke gen_control_statements(main_class_name)
  .print "************* generate breaks                      *****************"
  .invoke gen_break_statements()
  .print "************* generate continues                   *****************"
  .invoke gen_continue_statements()
.end function
.//
.//
.function translate_all_oal
  .param string root_pkg_name
  .param string main_class_name
  .param boolean output_change_messages
  .//
  .invoke translate_values(root_pkg_name)
  .invoke translate_control_statements()
  .invoke translate_other_statements(root_pkg_name, main_class_name, output_change_messages)
.end function
.//