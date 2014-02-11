.//====================================================================
.//
.// File:      $RCSfile: wfl_block.inc,v $
.// Version:   $Revision: 1.14 $
.// Modified:  $Date: 2013/01/10 22:54:05 $
.//
.// (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.// This function creates a workflow.
.//
.//====================================================================
.function create_wfl
  .param string name
  .param string sync_id
  .param inst_ref cme
  .//
  .assign attr_name = name
INSERT INTO W_WOF VALUES ( '${name}', ${sync_id}, '${cme.Key_Lett}', \
'${cme.Specialism}', '${cme.Label}');\
.end function
.//====================================================================
.//
.// This function creates a workflow step.
.//
.//====================================================================
.function create_step
  .param frag_ref wfl
  .param inst_ref blk
  .param string parent_id
  .param string prev_id
  .//
  .assign attr_wfl_name = wfl.name
  .assign attr_wfl = wfl
  .assign attr_id = "${info.unique_num}"
INSERT INTO W_STEP VALUES ('${attr_id}', '${wfl.name}', '${blk.Block_Id}', \
'${parent_id}', '${prev_id}', '');\
.end function
.//====================================================================
.//
.// This function translates a block by iterating over its statements.
.// When it encounters a 'from instances of where' or 'related by where'
.// statement that makes a USER bridge call in the where clause, this
.// function adds either a Context Item (W_CTI) instance or a Field
.// (W_FLD) instance to the current workflow step. Context Items are
.// created for 'from instances of' statements and Fields are added
.// for 'related by' statements.
.//
.// This data is used later in function_body.inc and wizard.inc to pre-
.// set values before entering the code generated from the rest of the
.// action language. It is also used in ui_processing.inc to suppress
.// emission of code implementing the selection statments, because the
.// selections have already been supplied, either by the user from the
.// context of the action (for example, the currently selected item in
.// a tree) or by the user making a selection or entering a value in a
.// widget such as a text field.
.//
.// This function is recursive, and each time it recurses, a new step
.// is added to the workflow. This allows conditionals to be computed
.// and the appropriate fields to be enabled/disabled or shown/hidden.
.//
.//====================================================================
.function wfl_pop_blck_xlate
  .param frag_ref step
  .param inst_ref blck
  .//
  .assign wfl = step.wfl
  .select any statement related by blck->ACT_SMT[R602] where (selected.Previous_Statement_Id == "0")
  .assign prev_step_id = "0"
  .assign srw_stmt_num = 0
  .while (not_empty statement)
    .select any fiw_stmt related by statement->ACT_FIW[R603]
    .if (not_empty fiw_stmt) 
      .// NOTE: UNLIKE Tiny Java Core . . . . . 
      .// We DON'T want to emit code for 'from instances of where' statements, 
      .// because these items will already have been supplied by the context 
      .// and passed as parameters. So skip this statement . . .
      .select one fiw_val related by fiw_stmt->V_VAL[R610]
      .invoke hubi = has_user_bridge_invocation( fiw_val )
      .if (hubi.result)
        .select one inst_ref related by fiw_stmt->V_VAR[R665]->V_INT[R814]
        .select one inst_set related by fiw_stmt->V_VAR[R665]->V_INS[R814]
        .if (not_empty inst_ref)
INSERT INTO W_CTI VALUES ('${inst_ref.Variable_Name}', '${wfl.name}', '${statement.Statement_Id}', '${inst_ref.Key_Lett}', '${fiw_stmt.cardinality}');
        .elif (not_empty inst_set)
INSERT INTO W_CTI VALUES ('${inst_set.Variable_Name}', '${wfl.name}', '${statement.Statement_Id}', '${inst_set.Key_Lett}', '${fiw_stmt.cardinality}');
        .else
          .print "ERROR: From instances where statement does not specify instance reference or set . . . . internal generation error"
        .end if
      .end if
    .end if
    .select any srw_stmt related by statement->ACT_SEL[R603]->ACT_SRW[R664]
    .if (not_empty srw_stmt)
      .// NOTE: AGAIN, UNLIKE Tiny Java Core . . . . . 
      .// We DON'T want to emit standard architecture code for 'related by where'
      .// statements, because these items are the subject of GUI navigations by
      .// the user. So instead of normal processing, we create a dialog field
      .// here.
      .select one srw_val related by srw_stmt->V_VAL[R611]
      .invoke hubi = has_user_bridge_invocation( srw_val )
      .if (hubi.result)
        .if(hubi.Method_Name == "selectExisting")
          .// here we need to provide the existing associated element
          .// to the element selector
          .select one result related by srw_stmt->ACT_SEL[R664]->V_VAR[R638]
          .select any last_link related by srw_stmt->ACT_SEL[R664]->ACT_LNK[R637] where (selected.Next_Link_Id == "0")
          .select one next_link related by last_link->ACT_LNK[R604.'precedes']
          .while (not_empty next_link)
            .assign last_link = next_link
            .select one next_link related by last_link->ACT_LNK[R604.'precedes']
          .end while
INSERT INTO W_CTI VALUES ('${result.Variable_Name}', '${wfl.name}', '${statement.Statement_Id}', '${last_link.Target_Obj_KL}', 'one');
        .else
          .assign srw_stmt_num = srw_stmt_num + 1
          .select one result related by srw_stmt->ACT_SEL[R664]->V_VAR[R638]
          .select any last_link related by srw_stmt->ACT_SEL[R664]->ACT_LNK[R637] where (selected.Next_Link_Id == "0")
          .select one next_link related by last_link->ACT_LNK[R604.'precedes']
          .while (not_empty next_link)
            .assign last_link = next_link
            .select one next_link related by last_link->ACT_LNK[R604.'precedes']
          .end while
          .select one sel_stmt related by srw_stmt->ACT_SEL[R664]
INSERT INTO W_FLD VALUES ('USER_${result.Variable_Name}', '${result.Variable_Name}', \
'${statement.Statement_Id}', '${last_link.Target_Obj_KL}', '${step.id}', \
'${sel_stmt.cardinality}', \
          .if (hubi.Method_Name == "selectOne")
'Combo', '');
          .elif (hubi.Method_Name == "selectOneWithInt")
'Combo', '');
          .elif (hubi.Method_Name == "selectMany")
'List', '');
          .else
'${hubi.Method_Name}', '');
          .end if
          .select any function from instances of S_SYNC where ("${selected.Sync_ID}" == blck.Action_Id)
          .// only support two custom labels
          .assign label = ""
          .if ( srw_stmt_num == 1 )
            .assign label = "${function.Descrip:Label1}"
          .elif ( srw_stmt_num == 2 )
            .assign label = "${function.Descrip:Label2}"
          .end if
          .if ( label != "" )
INSERT INTO W_CFL VALUES ('USER_${result.Variable_Name}', '${step.id}', '${label}');
          .end if
        .end if
      .else
        .print "${statement.buffer}"
      .end if .// if hubi.result
    .end if
    .select one at_stmt related by statement->ACT_AT[R603]
    .if ( not_empty at_stmt )
      .select one at_val related by at_stmt->V_VAL[R635]
      .invoke hubi = has_user_bridge_invocation( at_val )
      .if (hubi.result)
        .// We DON'T want to emit standard architecture code for 'USER::bridge call'
        .// statements, because these items are the subject of GUI navigations by
        .// the user. So instead of normal processing, we create a dialog field
        .// here.
        .select one transient_asgn related by statement->ACT_AT[R603]
INSERT INTO W_FLD VALUES ('USER_${transient_asgn.Target_Variable_Name}', '${transient_asgn.Target_Variable_Name}', \
'${transient_asgn.Statement_Id}', '', '${step.id}', \
        .if ((hubi.Method_Name == "getString") OR (hubi.Method_Name == "getStringDef"))
'', 'Text', '');
        .elif (hubi.Method_Name == "getMLString")
'', 'MLText', '');
        .else
'', '${hubi.Method_Name}', '');
        .end if
      .end if
    .end if
    .select any for_blk related by statement->ACT_FOR[R603]->ACT_BLK[R605]
    .if (not_empty for_blk)
      .invoke new_step = create_step(step.wfl, for_blk, step.id, prev_step_id)
      .invoke inner_blck = wfl_pop_blck_xlate(new_step, for_blk)
      .if ("${inner_blck.body}" != "")
${new_step.body}\
        .assign prev_step_id = new_step.id
${inner_blck.body}\
      .end if
    .end if
    .select any else_blk related by statement->ACT_E[R603]->ACT_BLK[R606]
    .if (not_empty else_blk)
      .invoke new_step = create_step(step.wfl, else_blk, step.id, prev_step_id)
      .invoke inner_blck = wfl_pop_blck_xlate(new_step, else_blk)
      .if ("${inner_blck.body}" != "")
${new_step.body}\
        .assign prev_step_id = new_step.id
${inner_blck.body}\
      .end if
    .end if
    .select any if_blk related by statement->ACT_IF[R603]->ACT_BLK[R607]
    .if (not_empty if_blk)
      .invoke new_step = create_step(step.wfl, if_blk, step.id, prev_step_id)
      .invoke inner_blck = wfl_pop_blck_xlate(new_step, if_blk)
      .if ("${inner_blck.body}" != "")
${new_step.body}\
        .assign prev_step_id = new_step.id
${inner_blck.body}\
      .end if
    .end if
    .select any whl_blk related by statement->ACT_WHL[R603]->ACT_BLK[R608]
    .if (not_empty whl_blk)
      .invoke new_step = create_step(step.wfl, whl_blk, step.id, prev_step_id)
      .invoke inner_blck = wfl_pop_blck_xlate(new_step, whl_blk)
      .if ("${inner_blck.body}" != "")
${new_step.body}\
        .assign prev_step_id = new_step.id
${inner_blck.body}\
      .end if
    .end if
    .select any eli_blk related by statement->ACT_EL[R603]->ACT_BLK[R658]
    .if (not_empty eli_blk)
      .invoke new_step = create_step(step.wfl, eli_blk, step.id, prev_step_id)
      .invoke inner_blck = wfl_pop_blck_xlate(new_step, eli_blk)
      .if ("${inner_blck.body}" != "")
${new_step.body}\
        .assign prev_step_id = new_step.id
${inner_blck.body}\
      .end if
	.end if
    .select one next_statement related by statement->ACT_SMT[R661.'precedes']
    .assign statement = next_statement
  .end while
.end function
.//
