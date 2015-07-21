.//====================================================================
.//
.// File:      $RCSfile: ui_processing.inc,v $
.// Version:   $Revision: 1.16.80.1 $
.// Modified:  $Date: 2013/07/19 12:25:14 $
.//
.// (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.//  Purpose: This function produces the code for UI action given the outer
.//           block.  It traverses each statement in the order in which 
.//           it was inserted in the generation database.  This order is 
.//           the order of the statements in the action.  
.//
.//           If the statement is a control statement, then there are two
.//           buffers of generated code, otherwise there is only one.
.//
.//           This is closely based on TinyJava processing.arc. In general,
.//           these files should be maintained together.
.//=======================================================================
.//
.function ui_blck_xlate
  .param inst_ref blck
  .param boolean usesTransactions
  .//
  .select many statements related by blck->ACT_SMT[R602]
  .select many else_statements  related by blck->ACT_SMT[R602]->ACT_E[R603]->ACT_SMT[R603]
  .select many elif_statements  related by blck->ACT_SMT[R602]->ACT_EL[R603]->ACT_SMT[R603]
  .assign statements = statements - else_statements
  .assign statements = statements - elif_statements
  .for each last_statement in statements
    .if (last_statement.Previous_Statement_Id == 0)
      .break for
    .end if
  .end for
  .assign statement = last_statement
  .select one prev_stmt related by last_statement->ACT_SMT[R661.'succeeds']
  .while (not_empty prev_stmt)
    .assign statement = prev_stmt
    .select one prev_stmt related by prev_stmt->ACT_SMT[R661.'succeeds']
  .end while
  .while (not_empty statement)
    .// NOTE: UNLIKE Tiny Java Core . . . . . 
    .// We DON'T want to emit code for statements that have fields or context
    .// items, defined for them because these items will already have been 
    .// supplied either by cached UI field data or by UI context information
    .// respectively.
    .select any context_item from instances of W_CTI where (selected.Statement_ID == "${statement.Statement_Id}")
    .select any field from instances of W_FLD where (selected.Statement_ID == "${statement.Statement_Id}")
    .if ((empty context_item) AND (empty field))
${statement.buffer}
      .if (usesTransactions)
	    .// if the current statement is a bridge
	    .select one brg_stmt related by statement->ACT_BRG[R603]
	    .if(not_empty brg_stmt)
	      .select one bridge related by brg_stmt->S_BRG[R674]
	      .select one ee related by bridge->S_EE[R19]
	      .// and if that bridge statement is the warning bridge
	      .if((ee.Key_Lett == "USER") and (bridge.Name == "warning"))
	            .// we cancel the transaction that was started for this action
		TransactionUtil.cancelTransactions(transactionGroup);
	      .end if
	    .end if
	  .end if
      .select any for_blk related by statement->ACT_FOR[R603]->ACT_BLK[R605]
      .if (not_empty for_blk)
        .invoke inner_blck = ui_blck_xlate(for_blk, usesTransactions)
${inner_blck.body}
	  .end if
      .select any if_blk related by statement->ACT_IF[R603]->ACT_BLK[R607]
      .if (not_empty if_blk)
        .invoke inner_blck = ui_blck_xlate(if_blk, usesTransactions)
${inner_blck.body}
      .end if
      .select any whl_blk related by statement->ACT_WHL[R603]->ACT_BLK[R608]
      .if (not_empty whl_blk)
        .invoke inner_blck = ui_blck_xlate(whl_blk, usesTransactions)
${inner_blck.body}
	  .end if
${statement.buffer2}
      .select many eli_stmts related by statement->ACT_IF[R603]->ACT_EL[R682]->ACT_SMT[R603]
      .for each eli_stmt in eli_stmts
${eli_stmt.buffer}
        .select one eli_blk related by eli_stmt->ACT_EL[R603]->ACT_BLK[R658]
        .invoke inner_blck = ui_blck_xlate(eli_blk, usesTransactions)
${inner_blck.body}
${eli_stmt.buffer2}
      .end for
      .select any else_stmt related by statement->ACT_IF[R603]->ACT_E[R683]->ACT_SMT[R603]
      .if (not_empty else_stmt)
${else_stmt.buffer}
        .select one else_blk related by else_stmt->ACT_E[R603]->ACT_BLK[R606]
        .invoke inner_blck = ui_blck_xlate(else_blk, usesTransactions)
${inner_blck.body}
${else_stmt.buffer2}
      .end if
    .end if
    .select one next_statement related by statement->ACT_SMT[R661.'precedes']
    .assign statement = next_statement
  .end while
.end function
