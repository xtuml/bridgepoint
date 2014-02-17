.//====================================================================
.//
.// File:      block.inc
.//
.// Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.//  Purpose: This function produces the code for an action given the outer
.//           block.  It traverses each statement in the order in which 
.//           it was inserted in the generation database.  This order is 
.//           the order of the statements in the action.  
.//
.//           If the statement is a control statement, then there are two
.//           buffers of generated code, otherwise there is only one.
.//=======================================================================
.//
.function blck_xlate
.param inst_ref blck
  .select any statement related by blck->ACT_SMT[R602] where (selected.Previous_Statement_Id == "0")
  .while (not_empty statement)
${statement.buffer}
         .select any for_blk related by statement->ACT_FOR[R603]->ACT_BLK[R605]
      .if (not_empty for_blk)
        .invoke inner_blck = blck_xlate(for_blk)
${inner_blck.body}
	  .end if
      .select any else_blk related by statement->ACT_E[R603]->ACT_BLK[R606]
      .if (not_empty else_blk)
        .invoke inner_blck = blck_xlate(else_blk)
${inner_blck.body}
	  .end if
      .select any if_blk related by statement->ACT_IF[R603]->ACT_BLK[R607]
      .if (not_empty if_blk)
        .invoke inner_blck = blck_xlate(if_blk)
${inner_blck.body}
	  .end if
      .select any whl_blk related by statement->ACT_WHL[R603]->ACT_BLK[R608]
      .if (not_empty whl_blk)
        .invoke inner_blck = blck_xlate(whl_blk)
${inner_blck.body}
	  .end if
      .select any eli_blk related by statement->ACT_EL[R603]->ACT_BLK[R658]
      .if (not_empty eli_blk)
        .invoke inner_blck = blck_xlate(eli_blk)
${inner_blck.body}
	  .end if
${statement.buffer2}
    .select one next_statement related by statement->ACT_SMT[R661.'precedes']
    .assign statement = next_statement
  .end while
.end function
.//