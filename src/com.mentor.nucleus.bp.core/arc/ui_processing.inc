.//====================================================================
.//
.// File:      $RCSfile: ui_processing.inc,v $
.// Version:   $Revision: 1.16 $
.// Modified:  $Date: 2013/01/17 03:38:58 $
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
  .select any statement related by blck->ACT_SMT[R602] where (selected.Previous_Statement_Id == "0")
  .while (not_empty statement)
    .// NOTE: UNLIKE Tiny Java Core . . . . . 
    .// We DON'T want to emit code for statements that have fields or context
    .// items, defined for them because these items will already have been 
    .// supplied either by cached UI field data or by UI context information
    .// respectively.
    .select one context_item related by statement->W_CTI[R2004]
    .select one field related by statement->W_FLD[R2010]
    .if ((empty context_item) AND (empty field))
${statement.buffer}
		.if (usesTransactions)
	        .// if the current statement is a bridge
	        .select one brg_stmt related by statement->ACT_BRG[R603]
	        .if(not_empty brg_stmt)
	          .// and if that bridge statement is the warning bridge
	          .if((brg_stmt.External_Entity_KL == "USER") and (brg_stmt.Method_Name == "warning"))
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
        .select any else_blk related by statement->ACT_E[R603]->ACT_BLK[R606]
        .if (not_empty else_blk)
          .invoke inner_blck = ui_blck_xlate(else_blk, usesTransactions)
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
        .select any eli_blk related by statement->ACT_EL[R603]->ACT_BLK[R658]
        .if (not_empty eli_blk)
          .invoke inner_blck = ui_blck_xlate(eli_blk, usesTransactions)
${inner_blck.body}
	    .end if
${statement.buffer2}
    .end if
    .select one next_statement related by statement->ACT_SMT[R661.'precedes']
    .assign statement = next_statement
  .end while
.end function
