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
  .invoke gen_literal_boolean_values()
  .invoke gen_literal_integer_values()
  .invoke gen_literal_real_values()
  .invoke gen_literal_string_values()
  .invoke gen_literal_enumerations()
  .invoke gen_attribute_values()
  .invoke gen_transient_values()
  .invoke gen_instance_reference_values()
  .invoke gen_selection_test_values()
  .invoke gen_inst_ref_set_values()
  .invoke gen_event_values()
  .invoke gen_parameter_values()
  .invoke gen_binary_op_values()
  .invoke gen_unary_op_values()
  .invoke gen_bridge_values()
  .invoke gen_transform_values()
  .invoke gen_synch_service_values()
  .invoke gen_paren_expr_values()
.end function
.//
.//
.function translate_control_statements
  .invoke gen_if_statements()
  .invoke gen_else_statements()
  .invoke gen_for_statements()
  .invoke gen_while_statements()
  .invoke gen_elif_statements()
.end function
.//
.function translate_other_statements
  .param string main_class_name
  .param boolean output_change_messages
  .invoke gen_asgn_trans_statements()
  .invoke gen_asgn_attr_statements()
  .invoke gen_create_instance_statements(main_class_name, output_change_messages)
  .invoke gen_delete_instance_statements(main_class_name, output_change_messages)
  .invoke gen_create_event_instance_statements()
  .invoke gen_relate_statements()
  .invoke gen_unrelate_statements()
  .invoke gen_select_statements()
  .invoke gen_select_inst_where_statements()
  .invoke gen_select_inst_rel_by_statements()
  .invoke gen_select_rel_where_statements()
  .invoke gen_relate_using_statements()
  .invoke gen_unrelate_using_statements()
  .invoke gen_event_gen_statements()
  .invoke gen_event_gen_ass_statements()
  .invoke gen_transform_statements()
  .invoke gen_bridge_statements()
  .invoke gen_function_statements()
  .invoke gen_return_statements()
  .invoke gen_control_statements(main_class_name)
  .invoke gen_break_statements()
  .invoke gen_continue_statements()
.end function
.//
.// 
.function translate_all_oal
  .param string main_class_name
  .param boolean output_change_messages
  .//
  .invoke translate_values()
  .invoke translate_control_statements()
  .invoke translate_other_statements(main_class_name, output_change_messages)
.end function
.//