.//====================================================================
.//
.// File:      $RCSfile: verify_selects.arc,v $
.// Version:   $Revision: 1.9.32.1 $
.// Modified:  $Date: 2013/07/19 12:26:00 $
.//
.// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
.//
.//====================================================================
.//
.//    Purpose:    This archetype verifies that each select over a
.//	               conditional association is followed by an if
.//				   not_empty test before being accessed
.//
.//    Functions:  process_entire_model  
.//				   process_functions_only
.//				   check_for_variable_as_rvalue
.//				   populate_elif_if_stmt_id
.//				   populate_else_if_stmt_id
.//				   check_statement
.//                check_block
.// 			   check_block_for_ref
.//				   process_unary_operation
.//				   process_binary_operation
.//				   process_parenthesis_expr
.//
.//============================================================================
.//
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.//
.invoke mc_root_pkg_name = GET_ENV_VAR("PTC_MCC_ROOT")
.assign mc_root_pkg = mc_root_pkg_name.result
.//
.//
.include "${mc_archetypes}/arch_utils.inc"
.//
.// start of functions
.//
.//
.//	
.// process_entire_model
.//
.//	This function calls check_block on
.//	all action found in a model
.//	           
.function process_entire_model
  .param String log_output_prev
  .select many actions from instances of ACT_ACT
  .for each action in actions
    .select many processed_variables from instances of V_VAR where (selected.Variable_Name == "")
    .select one outer_blk related by action->ACT_BLK[R666]
    .invoke result = check_block (outer_blk, log_output_prev, processed_variables)
    .assign log_output_prev = result.log_output
  .end for
  .assign attr_log_output_prev = log_output_prev
.end function
.//
.// process_functions_only
.//
.// This function calls check_block on
.// all action of type S_SYNC
.//
.function process_functions_only
  .param String log_output_prev
  .select many functions from instances of S_SYNC
  .for each function in functions
    .// relate the domain to the functions being processed
  .select one dom related by function->S_DOM[R23]
  .if (empty dom)
    .select one dom related by function->S_FIP[R31]->S_FPK[R31]->S_DOM[R29]
  .end if
    .if(not_empty dom)
      .assign function.Dom_ID = dom.Dom_ID
    .end if
    .select any action from instances of ACT_ACT where (selected.Action_Id == "${function.Sync_ID}")
    .if(not_empty action)
      .select many processed_variables from instances of V_VAR where (selected.Variable_Name == "")
      .select one outer_blk related by action->ACT_BLK[R666]
      .invoke result = check_block (outer_blk, log_output_prev, processed_variables)
      .assign log_output_prev = result.log_output
    .end if
  .end for
  .assign attr_log_output_prev = log_output_prev
.end function
.// process_parenthesis_expr
.//
.//	This function is reponsible for checking whether or
.// not a parenthesized expression contains a unary operation
.// or a binary operation, calling the necessary function for
.// either, on the variable being checked and if so marks the
.// block as ignored from the block check
.//
.// This function will recurse until it finds nothing or either
.// a binary or unary operation
.//
.function process_parenthesis_expr
  .param Inst_Ref pex
  .param String var_name
  .assign attr_ignored = false
  .assign attr_is_safe_empty_test = false
  .select one uop related by pex->V_VAL[R824]->V_UNY[R801]
  .if(not_empty uop)
    .invoke puo = process_unary_operation( uop, var_name )
    .assign attr_ignored = puo.ignored
    .assign attr_is_safe_empty_test = puo.is_safe_empty_test
  .end if
  .select one bop related by pex->V_VAL[R824]->V_BIN[R801]
  .if(not_empty bop)
    .invoke pbo = process_binary_operation( bop, var_name )
    .assign attr_ignored = pbo.ignored
    .assign attr_is_safe_empty_test = pbo.is_safe_empty_test
  .end if
  .select one nested_pex related by pex->V_VAL[R824]->V_PEX[R801]
  .if(not_empty nested_pex)
    .invoke ppex = process_parenthesis_expr( nested_pex, var_name )
    .assign attr_ignored = ppex.ignored
    .assign attr_is_safe_empty_test = ppex.is_safe_empty_test
  .end if
.end function
.//					
.// process_binary_operation
.//
.//	This function is reponsible for checking whether or
.// not a binary operation contains a unary operation
.// or a parenthesized expression, calling the necessary function
.// for either, on the variable being checked and if so marks the
.// block as ignored from the block check
.//
.// This function will recurse until it finds nothing or either
.// a parenthesized expression or a unary operation
.//
.function process_binary_operation
  .param Inst_Ref bin_op
  .param String var_name
  .assign attr_ignored = false
  .assign attr_is_safe_empty_test = false
  .select one left_side_uop related by bin_op->V_VAL[R802]->V_UNY[R801]
  .select one left_side_bop related by bin_op->V_VAL[R802]->V_BIN[R801]
  .select one left_side_pex related by bin_op->V_VAL[R802]->V_PEX[R801]
  .if(not_empty left_side_uop)
    .invoke puo = process_unary_operation( left_side_uop, var_name )
    .assign attr_ignored = puo.ignored
    .assign attr_is_safe_empty_test = puo.is_safe_empty_test
  .elif(not_empty left_side_bop)
    .invoke pbo = process_binary_operation ( left_side_bop, var_name )
    .assign attr_ignored = pbo.ignored
    .assign attr_is_safe_empty_test = pbo.is_safe_empty_test
  .elif(not_empty left_side_pex)
    .invoke ppex = process_parenthesis_expr( left_side_pex, var_name )
	.assign attr_ignored = ppex.ignored
	.assign attr_is_safe_empty_test = ppex.is_safe_empty_test
  .end if
  .if(attr_ignored != true) OR (attr_is_safe_empty_test != true)
    .select one right_side_uop related by bin_op->V_VAL[R803]->V_UNY[R801]
    .select one right_side_bop related by bin_op->V_VAL[R803]->V_BIN[R801]
    .select one right_side_pex related by bin_op->V_VAL[R803]->V_PEX[R801]
    .if(not_empty right_side_uop)
      .invoke puo = process_unary_operation( right_side_uop, var_name )
      .assign attr_ignored = puo.ignored
      .assign attr_is_safe_empty_test = puo.is_safe_empty_test
    .elif(not_empty right_side_bop)
      .invoke pbo = process_binary_operation ( right_side_bop, var_name )
      .assign attr_ignored = pbo.ignored
      .assign attr_is_safe_empty_test = pbo.is_safe_empty_test
    .elif(not_empty right_side_pex)
	  .invoke ppex = process_parenthesis_expr( right_side_pex, var_name )
	  .assign attr_ignored = ppex.ignored
	  .assign attr_is_safe_empty_test = ppex.is_safe_empty_test
	.end if
  .end if  
.end function
.//					
.// process_unary_operation
.//
.//	This function is reponsible for checking whether or
.// not a unary operation contains a not_empty on the variable
.// being checked and if so marks the block as ignored from the
.// block check
.//
.function process_unary_operation
  .param Inst_Ref uop
  .param String var_name
  .assign attr_ignored = false
  .assign attr_is_safe_empty_test = false
  .if(uop.Operator == "NOT_EMPTY")
    .select one instance_ref related by uop->V_VAL[R804]->V_IRF[R801]
    .if(not_empty instance_ref)
      .select one inst_ref_var related by instance_ref->V_VAR[R808]
      .if(not_empty inst_ref_var)
        .if(inst_ref_var.Variable_Name == var_name)
          .assign attr_ignored = true
        .end if
      .end if
    .end if
  .elif(uop.Operator == "NOT")
	.select one nested_uop related by uop->V_VAL[R804]->V_UNY[R801]
	.if(not_empty nested_uop) AND (nested_uop.Operator == "empty")
      .select one instance_ref related by nested_uop->V_VAL[R804]->V_IRF[R801]
      .if(not_empty instance_ref)
        .select one inst_ref_var related by instance_ref->V_VAR[R808]
        .if(not_empty inst_ref_var)
          .if(inst_ref_var.Variable_Name == var_name)
            .assign attr_ignored = true
          .end if
        .end if
      .end if
	.end if
  .elif(uop.Operator == "EMPTY")
	.select one instance_ref related by uop->V_VAL[R804]->V_IRF[R801]
	.if(not_empty instance_ref)
	  .select one inst_ref_var related by instance_ref->V_VAR[R808]
	  .if(not_empty inst_ref_var)
	    .if(inst_ref_var.Variable_Name == var_name)
	      .assign attr_is_safe_empty_test = true
	    .end if
	  .end if
	.end if
  .end if
.end function
.//
.// check_for_variable_as_rvalue
.//				   
.//	This function looks for any access to a variable
.//	as an rvalue.
.//
.function check_for_variable_as_rvalue
  .param Inst_Ref variable
  .param Inst_Ref block
  .assign attr_dangerous_access = false
  .assign attr_dangerous_output = ""
  .assign error_msg = ""
  .select any op_inv_stmt related by variable->ACT_TFM[R667]->ACT_SMT[R603] where (selected.Block_ID == block.Block_ID)
  .if(not_empty op_inv_stmt)
    .assign error_msg = "Warning: Found dangerous access to ${variable.Name} in an operation invocation contained in block: ${block.Block_ID}\n"
    .assign attr_dangerous_output = attr_dangerous_output + error_msg 
    .assign attr_dangerous_access = true
  .end if
  .select any del_stmt related by variable->ACT_DEL[R634]->ACT_SMT[R603] where (selected.Block_ID == block.Block_ID)
  .if(not_empty del_stmt)
    .assign error_msg = "Warning: Found dangerous access to ${variable.Name} in a delete contained in block: ${block.Block_ID}\n"
    .assign attr_dangerous_output = attr_dangerous_output + error_msg
    .assign attr_dangerous_access = true
  .end if
  .select any ata_stmt related by variable->ACT_AI[R629]->ACT_SMT[R603] where (selected.Block_ID == block.Block_ID)
  .if(not_empty ata_stmt)
    .assign error_msg = "Warning: Found dangerous access to ${variable.Name} in an Assign to Attribute contained in block: ${block.Block_ID}\n"
    .assign attr_dangerous_output = attr_dangerous_output + error_msg
    .assign attr_dangerous_access = true
  .end if
  .select any sel_stmt related by variable->ACT_SEL[R613]->ACT_SMT[R603] where (selected.Block_ID == block.Block_ID)
  .if(not_empty sel_stmt)
    .assign error_msg = "Warning: Found dangerous access to ${variable.Name} in a select contained in block: ${block.Block_ID}\n"
    .assign attr_dangerous_output = attr_dangerous_output + error_msg
    .assign attr_dangerous_access = true
  .end if
  .select any cei_stmt related by variable->E_CEI[R725]->ACT_SMT[R700] where (selected.Block_ID == block.Block_ID)
  .if(not_empty cei_stmt)
    .assign error_msg = "Warning: Found dangerous access to ${variable.Name} in a create event instance contained in block: ${block.Block_ID}\n"
    .assign attr_dangerous_output = attr_dangerous_output + error_msg
    .assign attr_dangerous_access = true
  .end if
  .select any gpr_stmt related by variable->E_GPR[R709]->ACT_SMT[R700] where (selected.Block_ID == block.Block_ID)
  .if(not_empty gpr_stmt)
    .assign error_msg = "Warning: Found dangerous access to ${variable.Name} in a generate pre-existing event contained in block: ${block.Block_ID}\n"
    .assign attr_dangerous_output = attr_dangerous_output + error_msg
    .assign attr_dangerous_access = true
  .end if
  .select any gen_stmt related by variable->E_GEN[R711]->ACT_SMT[R700] where (selected.Block_ID == block.Block_ID)
  .if(not_empty gen_stmt)
    .assign error_msg = "Warning: Found dangerous access to ${variable.Name} in a generate event contained in block: ${block.Block_ID}\n"
    .assign attr_dangerous_output = attr_dangerous_output + error_msg
    .assign attr_dangerous_access = true
  .end if
  .select any rel_stmt related by variable->ACT_REL[R615]->ACT_SMT[R603] where (selected.Block_ID == block.Block_ID)
  .select any rel_oth_stmt related by variable->ACT_REL[R616]->ACT_SMT[R603] where (selected.Block_ID == block.Block_ID)
  .if(not_empty rel_stmt)
    .assign error_msg = "Warning: Found dangerous access to ${variable.Name} in a relate contained in block: ${block.Block_ID}\n"
    .assign attr_dangerous_output = attr_dangerous_output + error_msg
    .assign attr_dangerous_access = true
  .end if
  .if(not_empty rel_oth_stmt)
    .assign error_msg = "Warning: Found dangerous access to ${variable.Name} in a relate contained in block: ${block.Block_ID}\n"
    .assign attr_dangerous_output = attr_dangerous_output + error_msg
    .assign attr_dangerous_access = true
  .end if
  .select any rel_using_one_stmt related by variable->ACT_RU[R617]->ACT_SMT[R603] where (selected.Block_ID == block.Block_ID)
  .select any rel_using_oth_stmt related by variable->ACT_RU[R618]->ACT_SMT[R603] where (selected.Block_ID == block.Block_ID)
  .if(not_empty rel_using_one_stmt)
    .assign error_msg = "Warning: Found dangerous access to ${variable.Name} in a relate using contained in block: ${block.Block_ID}\n"
    .assign attr_dangerous_output = attr_dangerous_output + error_msg
    .assign attr_dangerous_access = true
  .end if
  .if(not_empty rel_using_oth_stmt)
    .assign error_msg = "Warning: Found dangerous access to ${variable.Name} in a relate using contained in block: ${block.Block_ID}\n"
    .assign attr_dangerous_output = attr_dangerous_output + error_msg
    .assign attr_dangerous_access = true
  .end if
  .select any rel_using_stmt related by variable->ACT_RU[R619]->ACT_SMT[R603] where (selected.Block_ID == block.Block_ID)
  .if(not_empty rel_using_stmt)
    .assign error_msg = "Warning: Found dangerous access to ${variable.Name} in a relate using as link contained in block: ${block.Block_ID}\n"
    .assign attr_dangerous_output = attr_dangerous_output + error_msg
    .assign attr_dangerous_access = true
  .end if
  .select any unrel_one_stmt related by variable->ACT_UNR[R620]->ACT_SMT[R603] where (selected.Block_ID == block.Block_ID)
  .select any unrel_oth_stmt related by variable->ACT_UNR[R621]->ACT_SMT[R603] where (selected.Block_ID == block.Block_ID)
  .if(not_empty unrel_one_stmt)
    .assign error_msg = "Warning: Found dangerous access to ${variable.Name} in an unrelate contained in block: ${block.Block_ID}\n"
    .assign attr_dangerous_output = attr_dangerous_output + error_msg
    .assign attr_dangerous_access = true
  .end if
  .if(not_empty unrel_oth_stmt)
    .assign error_msg = "Warning: Found dangerous access to ${variable.Name} in an unrelate contained in block: ${block.Block_ID}\n"
    .assign attr_dangerous_output = attr_dangerous_output + error_msg
    .assign attr_dangerous_access = true
  .end if
  .select any unrel_using_one_stmt related by variable->ACT_URU[R622]->ACT_SMT[R603] where (selected.Block_ID == block.Block_ID)
  .select any unrel_using_oth_stmt related by variable->ACT_URU[R623]->ACT_SMT[R603] where (selected.Block_ID == block.Block_ID) 
  .if(not_empty unrel_using_one_stmt)
    .assign error_msg = "Warning: Found dangerous access to ${variable.Name} in an unrelate using contained in block: ${block.Block_ID}\n"
    .assign attr_dangerous_output = attr_dangerous_output + error_msg
    .assign attr_dangerous_access = true
  .end if
  .if(not_empty unrel_using_oth_stmt)
    .assign error_msg = "Warning: Found dangerous access to ${variable.Name} in an unrelate using contained in block: ${block.Block_ID}\n"
    .assign attr_dangerous_output = attr_dangerous_output + error_msg
    .assign attr_dangerous_access = true
  .end if
  .select any unrel_using_stmt related by variable->ACT_URU[R624]->ACT_SMT[R603] where (selected.Block_ID == block.Block_ID)
  .if(not_empty unrel_using_stmt)
    .assign error_msg = "Warning: Found dangerous access to ${variable.Name} in an unrelate using as link contained in block: ${block.Block_ID}\n"
    .assign attr_dangerous_output = attr_dangerous_output + error_msg
    .assign attr_dangerous_access = true
  .end if
.end function
.//
.//	populate_elif_if_stmt_id
.//
.//	This function is reposinsible for hooking up the
.//	associations between an if statement and its elif
.//	statements
.//
.function populate_elif_if_stmt_id
  .param Inst_ref elif_stmt
  .select one next_stmt related by elif_stmt->ACT_SMT[R603]
  .while (not_empty next_stmt)
    .select one next_stmt related by next_stmt->ACT_SMT[R661.'succeeds']
    .select one if_stmt related by next_stmt->ACT_IF[R603]
    .if(not_empty if_stmt)
      .assign elif_stmt.If_Statement_Id = if_stmt.Statement_ID
      .break while
    .end if
  .end while
  .if(elif_stmt.If_Statement_ID == "")
    .print "Error: elif statement found with no preceeding if statement"
  .end if
.end function
.//
.// populate_else_if_stmt_id
.//
.//	This function is reposinsible for hooking up the
.//	associations between an if statement and its else
.//	statements
.//
.function populate_else_if_stmt_id
  .param Inst_ref else_stmt
  .select one next_stmt related by else_stmt->ACT_SMT[R603]
  .while (not_empty next_stmt)
    .select one next_stmt related by next_stmt->ACT_SMT[R661.'succeeds']
    .select one if_stmt related by next_stmt->ACT_IF[R603]
    .if(not_empty if_stmt)
      .assign else_stmt.If_Statement_Id = if_stmt.Statement_ID
      .break while
    .end if
  .end while
  .if(else_stmt.If_Statement_ID == "")
    .print "Error: else statement found with no preceeding if statement"
  .end if
.end function
.//
.// check_statement
.//
.//	This function checks an if, elif, or a while
.//	statement to see if it should be ignored for
.//	variable access checking				   				   
.//										
.function check_statement
  .param Inst_Ref statement
  .param String var_name
  .param String stmt_type
  .assign attr_ignored = false
  .assign attr_is_safe_empty_test = false
  .select any value from instances of V_VAL where ("${selected.Value_ID}" == "Impossible")
  .if(stmt_type == "if")
    .select one value related by statement->V_VAL[R625]
  .elif(stmt_type == "while")
    .select one value related by statement->V_VAL[R626]
  .elif(stmt_type == "elif")
    .select one value related by statement->V_VAL[R659]  
  .end if
  .if(not_empty value)
    .select one uop related by value->V_UNY[R801]
	.if(not_empty uop)
	  .invoke puo = process_unary_operation( uop, var_name )
	  .assign attr_ignored = puo.ignored
	  .assign attr_is_safe_empty_test = puo.is_safe_empty_test
    .end if
    .select one bin_op related by value->V_BIN[R801]
    .if(not_empty bin_op)
      .invoke pbo = process_binary_operation ( bin_op, var_name )
      .assign attr_ignored = pbo.ignored
      .assign attr_is_safe_empty_test = pbo.is_safe_empty_test
    .end if
    .select one pex related by value->V_PEX[R801]
    .if(not_empty pex)
      .invoke ppex = process_parenthesis_expr( pex, var_name )
      .assign attr_ignored = ppex.ignored
      .assign attr_is_safe_empty_test = ppex.is_safe_empty_test
    .end if
  .end if  
.end function
.// check_block
.//
.//	This function checks a block for conditional
.//	selects, calling check_block_for_ref if any are
.//	found, and then calls itself on all while,
.//	if, elif, else, and for blocks contained in
.//	the current block
.//	
.function check_block
  .param Inst_Ref block
  .param String log_output_prev
  .param Inst_Ref_Set processed_variables
  .assign isConditional = false
  .select many selections related by block->ACT_SMT[R602]->ACT_SEL[R603] where (selected.cardinality != "MANY")
  .select many sfiws related by block->ACT_SMT[R602]->ACT_FIW[R603] where (selected.cardinality != "MANY")
  .select many sfis related by block->ACT_SMT[R602]->ACT_FIO[R603] where (selected.cardinality != "MANY")
  .for each sfiw in sfiws
    .assign process_variable = true
    .select one selection_variable related by sfiw->V_VAR[R665]
    .if(not_empty selection_variable)
      .for each processed_variable in processed_variables
        .if(processed_variable.Variable_Name == selection_variable.Variable_Name)
          .assign process_variable = false
        .end if
      .end for
      .if(process_variable != false)
        .invoke result = check_block_for_ref (selection_variable, block, log_output_prev)
        .assign log_output_prev = result.log_output
        .assign processed_variables = processed_variables | selection_variable
      .end if
    .end if
  .end for
  .for each sfi in sfis
    .assign process_variable = true
    .select one selection_variable related by sfi->V_VAR[R639]
    .if(not_empty selection_variable)
      .for each processed_variable in processed_variables
        .if(processed_variable.Variable_Name == selection_variable.Variable_Name)
          .assign process_variable = false
        .end if
      .end for
      .if(process_variable != false)
        .invoke result = check_block_for_ref (selection_variable, block, log_output_prev)
        .assign log_output_prev = result.log_output
        .assign processed_variables = processed_variables | selection_variable
      .end if
    .end if
  .end for 
  .for each selection in selections
    .assign process_variable = true
    .select one srw related by selection->ACT_SRW[R664]
    .if(not_empty srw)
      .assign isConditional = true
    .else
      .invoke conditionality = determine_chain_conditionality( selection )
      .assign isConditional = conditionality.isConditional
    .end if
    .if(isConditional == true)
      .select one selection_variable related by selection->V_VAR[R638]
      .if(not_empty selection_variable)
        .for each processed_variable in processed_variables
          .if(processed_variable.Variable_Name == selection_variable.Variable_Name)
            .assign process_variable = false
          .end if
        .end for
        .if(process_variable != false)
          .invoke result = check_block_for_ref (selection_variable, block, log_output_prev)
          .assign log_output_prev = result.log_output
          .assign processed_variables = processed_variables | selection_variable
        .end if
      .end if
    .end if
  .end for
  .select many while_blks related by block->ACT_SMT[R602]->ACT_WHL[R603]->ACT_BLK[R608]
  .select many if_blks related by block->ACT_SMT[R602]->ACT_IF[R603]->ACT_BLK[R607]
  .select many elseif_blks related by block->ACT_SMT[R602]->ACT_EL[R603]->ACT_BLK[R658]
  .select many else_blks related by block->ACT_SMT[R602]->ACT_E[R603]->ACT_BLK[R606]
  .select many for_blks related by block->ACT_SMT[R602]->ACT_FOR[R603]->ACT_BLK[R605]
  .assign blocks = while_blks | if_blks
  .assign blocks = blocks | elseif_blks
  .assign blocks = blocks | else_blks
  .assign blocks = blocks | for_blks
  .for each inner_block in blocks  
    .invoke result = check_block(inner_block, log_output_prev, processed_variables)
    .assign log_output_prev = result.log_output
  .end for
  .assign attr_log_output = log_output_prev
.end function
.//	
.// check_block_for_ref
.//
.//	This function checks a block for references to
.//	a variable that is selected over a conditional
.//	association within the current block               
.//					
.function check_block_for_ref
  .param Inst_Ref var
  .param Inst_Ref block
  .param String log_output_prev
  .assign dangerous_access = false
  .assign container = ""
  .assign parent_container = ""
  .assign action_type = ""
  .assign attr_log_output = log_output_prev
  .select any sm_act from instances of SM_ACT where ("${selected.Act_Id}" == "Impossible")
  .select any dbattr from instances of O_DBATTR where ("${selected.Attr_Id}" == "Impossible")
  .select any op from instances of O_TFR where ("${selected.Tfr_Id}" == "Impossible")
  .select any func from instances of S_SYNC where ("${selected.Sync_Id}" == "Impossible")
  .select any bridge from instances of S_BRG where ("${selected.Brg_ID}" == "Impossible")
  .select one action related by block->ACT_ACT[R601]
  .if(action.Type == "SM")
    .select any sm_act from instances of SM_ACT where ("${selected.Act_ID}" == action.Action_ID)
  .elif(action.Type == "AT")  
    .select any dbattr from instances of O_DBATTR where ("${selected.Attr_ID}" == action.Action_ID)
  .elif(action.Type == "CO")
    .select any op from instances of O_TFR where ("${selected.Tfr_ID}" == action.Action_ID)
  .elif(action.Type == "IO")
    .select any op from instances of O_TFR where ("${selected.Tfr_ID}" == action.Action_ID)
  .elif(action.Type == "FN")
    .select any func from instances of S_SYNC where ("${selected.Sync_ID}" == action.Action_ID)
  .elif(action.Type == "BR")
    .select any bridge from instances of S_BRG where ("${selected.Brg_ID}" == action.Action_ID)
  .end if
  .if(not_empty sm_act)
  	.select one moore_home related by sm_act->SM_AH[R514]->SM_MOAH[R513]
	.select one mealy_home related by sm_act->SM_AH[R514]->SM_MEAH[R513]
	.if(not_empty moore_home)
	  .select one state related by moore_home->SM_STATE[R511]
	  .if(not_empty state)
	  	.select one class related by state->SM_SM[R501]->SM_ISM[R517]->O_OBJ[R518]
		.if(not_empty class)
	      .assign parent_container = class.Name
	      .assign container = state.Name
	      .assign action_type = "State Action"
		.end if
	  .end if
	.elif(not_empty mealy_home)
	  .select one state related by moore_home->SM_STATE[R511]
	  .if(not_empty state)
	    .select one class related by state->SM_SM[R501]->SM_ISM[R517]->O_OBJ[R518]
        .if(not_empty class)
		  .assign parent_container = class.Name
		  .assign container = state.Name
		  .assign action_type = "State Action"
		.end if
	  .end if
	.end if
  .elif(not_empty dbattr)
    .select one class related by dbattr->O_BATTR[R107]->O_ATTR[R106]->O_OBJ[R102]
    .if(not_empty class)
 	  .select one ss related by class->S_SS[R2];
      .if(not_empty ss)
	    .assign parent_container = ss.Name
        .assign container = class.Name
        .assign action_type = "Derived Base Attribute"    
	  .end if
    .end if
  .elif(not_empty op)
    .select one class related by op->O_OBJ[R115]
    .if(not_empty class)
   	  .select one ss related by class->S_SS[R2]
      .if(not_empty ss)
	    .assign parent_container = ss.Name
        .assign container = class.Name + "."
        .assign container = container + op.Name
        .if(action.Type == "CO")
          .assign action_type = "Class Based Operation"
        .elif(action.Type == "IO")
          .assign action_type = "Instance Based Operation"
        .end if
	  .end if
	.end if
  .elif(not_empty func)
    .select one dom related by func->S_DOM[R23]
    .if(not_empty dom)
      .assign parent_container = dom.Name
      .assign container = func.Name
      .assign action_type = "Function"
    .end if
  .elif(not_empty bridge)
    .select one ee related by bridge->S_EE[R19]
    .if(not_empty ee)
      .assign parent_container = ee.Name
      .assign container = bridge.Name
      .assign action_type = "Bridge Operation"
    .end if
  .end if
  .assign original_log_output = attr_log_output
  .assign attr_log_output = attr_log_output + "\nProcessed block with the following data for dangerous access to variable: ${var.Name}\n\n"
  .assign attr_log_output = attr_log_output + "  Block: ${block.Block_ID}\n"
  .assign attr_log_output = attr_log_output + "  Owner: ${container}\n"
  .assign attr_log_output = attr_log_output + "  Type: ${action_type}\n"
  .assign attr_log_output = attr_log_output + "  Contained in: ${parent_container}\n\n"
  .select many irf_values related by var->V_IRF[R808]->V_VAL[R801] where (selected.Block_ID == block.Block_ID)
  .select many tval_values related by var->V_TVL[R805]->V_VAL[R801] where (selected.Block_ID == block.Block_ID)
  .select many aval_values related by var->V_AVL[R807]->V_VAL[R801] where (selected.Block_ID == block.Block_ID)
  .assign values = irf_values | tval_values
  .assign values = values | aval_values
  .for each value in values
	.select one uop related by value->V_UNY[R804]
    .if(empty uop)
      .assign warning_msg = "Warning: unsafe usage of variable ${var.Name} as L-Value in block: ${block.Block_ID}\n"
      .assign dangerous_access = true
      .assign attr_log_output = attr_log_output + warning_msg
      .print "${warning_msg}"
    .end if
  .end for
  .invoke cfvalv = check_for_variable_as_rvalue ( var, block )
  .if(cfvalv.dangerous_access == true)
    .assign attr_log_output = attr_log_output + cfvalv.dangerous_output
    .print "Warning: unsafe usage of variable ${var.Name} as R-Value in block: ${block.Block_ID}\n"
  .elif(cfvalv.dangerous_access == false)
    .if(dangerous_access == false)
      .assign attr_log_output = original_log_output
    .end if
  .end if
  .assign log_output_prev = attr_log_output  
  .select many while_stmts related by block->ACT_SMT[R602]->ACT_WHL[R603]
  .select many if_stmts related by block->ACT_SMT[R602]->ACT_IF[R603]
  .select many for_stmts related by block->ACT_SMT[R602]->ACT_FOR[R603]
  .for each while_stmt in while_stmts
    .invoke stmt_result = check_statement(while_stmt, var.Variable_Name, "while")
    .if(stmt_result.ignored == false)
      .select one while_blk related by while_stmt->ACT_BLK[R608]
      .if(not_empty while_blk)
        .invoke blk_chk_result = check_block_for_ref(var, while_blk, log_output_prev)
        .assign log_output_prev = blk_chk_result.log_output
      .end if
	.end if
  .end for
  .for each if_stmt in if_stmts
    .invoke stmt_result = check_statement(if_stmt, var.Variable_Name, "if")
    .if(stmt_result.ignored == false)
      .select one if_blk related by if_stmt->ACT_BLK[R607]
      .if(not_empty if_blk)
        .invoke blk_chk_result = check_block_for_ref(var, if_blk, log_output_prev)
        .assign log_output_prev = blk_chk_result.log_output
      .end if
	.end if
    .select many elif_stmts related by if_stmt->ACT_EL[R682]
    .for each elif_stmt in elif_stmts
      .invoke elif_stmt_chk = check_statement(elif_stmt, var.Variable_Name, "elif")
      .if(elif_stmt_chk.ignored == false)
        .select one elif_blk related by elif_stmt->ACT_BLK[R658]
        .if(not_empty elif_blk)
          .invoke elif_blk_chk = check_block_for_ref(var, elif_blk, log_output_prev)
          .assign log_output_prev = elif_blk_chk.log_output
        .end if
      .end if
    .end for
    .if(stmt_result.is_safe_empty_test == false) OR (elif_stmt_chk.is_safe_empty_test == false)
      .select one else_stmt related by if_stmt->ACT_E[R683]
      .select one else_blk related by else_stmt->ACT_BLK[R606]
      .if(not_empty else_blk)
        .invoke else_blk_chk = check_block_for_ref(var, else_blk, log_output_prev)
        .assign log_output_prev = else_blk_chk.log_output
      .end if
	.end if    
  .end for
  .for each for_stmt in for_stmts
    .select one for_blk related by for_stmt->ACT_BLK[R605]
    .if(not_empty for_blk)
      .invoke blk_chk_result = check_block_for_ref(var, for_blk, log_output_prev)
      .assign log_output_prev = blk_chk_result.log_output
    .end if
  .end for
  .assign attr_log_output = log_output_prev
.end function
.//
.//  end of functions
.//
.//
.//	This is where it all starts
.//
.assign log_output_prev = ""
.assign output_file = ""
.// get model name for output file
.invoke check_env = GET_ENV_VAR( "VERIFY_SELECTS" )
.invoke func_env = GET_ENV_VAR( "CHECK_FUNCTIONS_ONLY" )
.if(check_env.result == "true")
  .select any root_pkg from instances of EP_PKG where (selected.Name == mc_root_pkg)
  .invoke gdn_result = get_root_pkg_name( root_pkg )
  .// Pre-processing to setup association between if statement and elif/else statements
  .select many else_stmts from instances of ACT_E
  .for each else_stmt in else_stmts
    .invoke result = populate_else_if_stmt_id ( else_stmt )
  .end for
  .select many elif_stmts from instances of ACT_EL
  .for each elif_stmt in elif_stmts  
    .invoke result = populate_elif_if_stmt_id ( elif_stmt )
  .end for
  .if(func_env.result == "true")
    .invoke result = process_functions_only ( log_output_prev )
    .assign log_output_prev = result.log_output_prev
    .assign output_file = "functions_only_verify_selects_output.txt"
  .else
    .invoke result = process_entire_model ( log_output_prev )
    .assign log_output_prev = result.log_output_prev
    .assign output_file = "${gdn_result.body}_verify_selects_output.txt"
  .end if
  .if(log_output_prev == "")
    .print "No dangerous variable access found."
  .else
    .assign log_output = log_output_prev
    .print "Safe selection verification complete see ${output_file} for details about dangerous selects."
${log_output}
    .emit to file "${output_file}"
  .end if
.end if
