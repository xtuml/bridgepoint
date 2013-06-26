.//====================================================================
.//
.// File:      $RCSfile: block.inc,v $
.// Version:   $Revision: 1.9 $
.// Modified:  $Date: 2013/01/10 23:16:35 $
.//
.// (c) Copyright 2003-2013 Mentor Graphics Corporation  All rights reserved.
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
.// $Log: block.inc,v $
.// Revision 1.9  2013/01/10 23:16:35  rmulvey
.// job:dts0100939343
.// Updated the copyright to 2013
.//
.// Revision 1.8  2012/01/23 21:24:37  kbrown
.// job:dts0100848212
.// Batch commit of copyright updated files.
.//
.// Revision 1.7  2011/05/30 20:22:54  kbrown
.// job:dts0100742889
.// Updated copy right.
.//
.// Revision 1.6  2010/01/05 03:17:01  kbrown
.// job:dts0100644853
.// Batch commit of copyright change from 2009 to 2010 for BP CVS projects.
.//
.// Revision 1.5  2009/01/01 23:17:04  rmulvey
.// Job:4060
.// Batch promotion of copyright changes from Review-i4060 and Review-i4060-01.
.//
.// Revision 1.4.50.1  2008/12/31 16:22:51  rmulvey
.// Job:4060
.// This is a batch commit of 2009 copyright changes to branch Review-i4060.  This includes the
.// report that outlines all changes made (before/after for each line changed).  This report is found here: <cvs>/Documentation/internal/test_results/R2_1_2/i4060/update-copyright-results.txt.
.//
.// Revision 1.4  2008/01/08 20:24:49  rmulvey
.// Job:3349
.// Promoted from Review-i3349.  The copyright has been updated to 2008 and the version has been bumped to 1.5.4.
.//
.// Revision 1.3.20.1  2007/12/21 17:55:00  rmulvey
.// Job:3339
.// Checking-in the copyriight changes in branch Review-i3349.  A detailed list of these changes is found
.// in the report produced by the utility used to changesthese messages.  This report is here:
.// Documentation/internal/test_results/R1_5_4/i3349/update-copyright-results.txt
.//
.// Revision 1.3  2007/09/05 02:43:10  kbrown
.// Job:2673
.// Promoting copyright changes for projects:
.// MC-Java
.// MC-Java.test
.// pt_antlr
.//
.// Revision 1.2.718.1  2007/09/01 01:36:23  rmulvey
.// Job:2673
.// Updated copyright messages and version numbers in Review-i2673-01.
.//
.// Revision 1.2  2004/03/19 20:52:39  greg
.// Job: 143
.// Changed from binary to ASCII -kkv
.//
.// Revision 1.1  2003/08/04 18:51:17  greg
.// Job: 44
.// Renamed from processing.arc
.//
.// Revision 1.4  2003/02/15 19:00:05  campbell
.// Fixed statement ordering
.//
.// Revision 1.3  2003/02/11 18:24:03  campbell
.// BP 6.1 ODMS running
.//
.// Revision 1.2  2003/01/23 00:12:19  campbell
.// Delete test for 'controls block', caused compile error.
.// May have to reinstate when usage becomes clear
.//
.// Revision 1.1  2003/01/22 20:18:17  campbell
.// Finalized RCS Header
.//