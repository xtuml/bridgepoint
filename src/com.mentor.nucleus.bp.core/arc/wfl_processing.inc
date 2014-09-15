.//====================================================================
.//
.// File:      $RCSfile: wfl_processing.inc,v $
.// Version:   $Revision: 1.11.80.1 $
.// Modified:  $Date: 2013/07/19 12:25:14 $
.//
.// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
.//
.//=======================================================================
.// Licensed under the Apache License, Version 2.0 (the "License"); you may not
.// use this file except in compliance with the License.  You may obtain a copy
.// of the License at
.//
.//      http://www.apache.org/licenses/LICENSE-2.0
.//
.// Unless required by applicable law or agreed to in writing, software
.// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
.// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
.// License for the specific language governing permissions and limitations under
.// the License.
.//====================================================================
.//
.//  Purpose: This function produces the code for the workflow given the
.//           outer block.  It traverses each statement in the order in 
.//           which it was inserted in the generation database.  This 
.//           order is the order of the statements in the action.  
.//
.//           If the statement is a control statement, then there are two
.//           buffers of generated code, otherwise there is only one.
.//
.//           This is closely based on TinyJava processing.arc. In general,
.//           these files should be maintained together.
.//=======================================================================
.//
.function wfl_blck_xlate
.param inst_ref blck
  .select any step from instances of W_STEP where (selected.Block_Id == "${blck.Block_Id}")
  .if (not_empty step)
${step.buffer}
  .end if
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
    .// For the workflow, we are only interested in executing the passive statements
    .// That is, statements that have no effect on the state of the metamodel. Note
    .// that we currently don't check rvals for this property, we really should. For
    .// now we assume that if we're doing a select, assigning to a transient or
    .// executing a control block we're OK. we need to extend the checks to assure 
    .// that the rvals don't themselves resolve to state changing code. This is a bit
    .// like 'const' in C++ or 'final' in java.
    .assign passive = false
    .select any fio_stmt related by statement->ACT_FIO[R603]
    .if (not_empty fio_stmt)
      .assign passive = true
    .end if
    .select any fiw_stmt related by statement->ACT_FIW[R603]
    .if (not_empty fiw_stmt)
      .assign passive = true
    .end if
    .select any sel_stmt related by statement->ACT_SEL[R603]
    .if (not_empty sel_stmt)
      .assign passive = true
    .end if
    .select any asgn_stmt related by statement->ACT_AI[R603]
    .if (not_empty asgn_stmt)
      .assign passive = true
    .end if
    .select any for_blk related by statement->ACT_FOR[R603]->ACT_BLK[R605]
    .if (not_empty for_blk)
      .assign passive = true
    .end if
    .select any else_blk related by statement->ACT_E[R603]->ACT_BLK[R606]
    .if (not_empty else_blk)
      .assign passive = true
    .end if
    .select any if_blk related by statement->ACT_IF[R603]->ACT_BLK[R607]
    .if (not_empty if_blk)
      .assign passive = true
    .end if
    .select any whl_blk related by statement->ACT_WHL[R603]->ACT_BLK[R608]
    .if (not_empty whl_blk)
      .assign passive = true
    .end if
    .select any eli_blk related by statement->ACT_EL[R603]->ACT_BLK[R658]
    .if (not_empty eli_blk)
      .assign passive = true
    .end if
    .if (passive == true)
        .select any field from instances of W_FLD where (selected.Statement_Id == "${statement.Statement_Id}")
        .select any context_item from instances of W_CTI where (selected.Statement_Id == "${statement.Statement_Id}")
        .if (not_empty field)
${field.buffer}
        .elif (not_empty context_item)
          .// nothing required, already set up from context
        .else
${statement.buffer}
        .end if
      .select any for_blk related by statement->ACT_FOR[R603]->ACT_BLK[R605]
      .if (not_empty for_blk)
        .invoke inner_blck = wfl_blck_xlate(for_blk)
${inner_blck.body}
      .end if
      .select any if_blk related by statement->ACT_IF[R603]->ACT_BLK[R607]
      .if (not_empty if_blk)
        .invoke inner_blck = wfl_blck_xlate(if_blk)
${inner_blck.body}
      .end if
      .select any whl_blk related by statement->ACT_WHL[R603]->ACT_BLK[R608]
      .if (not_empty whl_blk)
        .invoke inner_blck = wfl_blck_xlate(whl_blk)
${inner_blck.body}
      .end if
${statement.buffer2}
      .select many eli_stmts related by statement->ACT_IF[R603]->ACT_EL[R682]->ACT_SMT[R603]
      .for each eli_stmt in eli_stmts
${eli_stmt.buffer}
        .select one eli_blk related by eli_stmt->ACT_EL[R603]->ACT_BLK[R658]
        .invoke inner_blck = wfl_blck_xlate(eli_blk)
${inner_blck.body}
${eli_stmt.buffer2}
      .end for
      .select any else_stmt related by statement->ACT_IF[R603]->ACT_E[R683]->ACT_SMT[R603]
      .if (not_empty else_stmt)
${else_stmt.buffer}
        .select one else_blk related by else_stmt->ACT_E[R603]->ACT_BLK[R606]
        .invoke inner_blck = wfl_blck_xlate(else_blk)
${inner_blck.body}
${else_stmt.buffer2}
      .end if
    .end if
    .select one next_statement related by statement->ACT_SMT[R661.'precedes']
    .assign statement = next_statement
  .end while
.end function
