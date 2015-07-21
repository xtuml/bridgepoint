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
${statement.buffer}
         .select any for_blk related by statement->ACT_FOR[R603]->ACT_BLK[R605]
      .if (not_empty for_blk)
        .invoke inner_blck = blck_xlate(for_blk)
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
${statement.buffer2}
    .select many eli_stmts related by statement->ACT_IF[R603]->ACT_EL[R682]->ACT_SMT[R603]
    .for each eli_stmt in eli_stmts
${eli_stmt.buffer}
      .select one eli_blk related by eli_stmt->ACT_EL[R603]->ACT_BLK[R658]
        .invoke inner_blck = blck_xlate(eli_blk)
${inner_blck.body}
${eli_stmt.buffer2}
    .end for
    .select any else_stmt related by statement->ACT_IF[R603]->ACT_E[R683]->ACT_SMT[R603]
    .if (not_empty else_stmt)
${else_stmt.buffer}
      .select one else_blk related by else_stmt->ACT_E[R603]->ACT_BLK[R606]
      .invoke inner_blck = blck_xlate(else_blk)
${inner_blck.body}
${else_stmt.buffer2}
	  .end if
    .select one next_statement related by statement->ACT_SMT[R661.'precedes']
    .assign statement = next_statement
  .end while
.end function
.//