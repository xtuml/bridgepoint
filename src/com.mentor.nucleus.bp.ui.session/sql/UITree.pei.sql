--======================================================================
--
-- File:      $RCSfile$
-- Version:   $Revision$
-- Modified:  $Date$
--
--(c) Copyright 2006-2013 by Mentor Graphics Corp.  All rights reserved.
--
--========================================================================
--This document contains information proprietary and confidential to
--Mentor Graphics Corp., and is not for external distribution.
--======================================================================== 
-- Note that the order of the TNS nodes here defines the order in which
-- the nodes appear in the tree.
--
-- The second parameter of T_TNS is still used to specify the icon to use
-- in the tree, but only if it differs from the standard icon for that class.
-- If parameter 2 is blank, then the icon specified in
--      com.mentor.nucleus.core/sql/ooaofooa_hierarchy.pei.sql
-- for the same key letters (parameter 3) will be used. That file is the common
-- file used by all trees to specify common node data so that all trees will
-- display the same icon for the same class.
--
INSERT INTO T_TNS VALUES (1,  '',                      'S_SYS',   '', 'Name', '', 'System', false);
INSERT INTO T_TNS VALUES (2,  '',                      'S_DOM',   '', 'Name', '', 'Domains', false);
INSERT INTO T_TNS VALUES (3,  '',                      'S_SYNC',   '', 'Name', '', 'Functions', false);
INSERT INTO T_TNS VALUES (4,  'field_default_obj.gif', 'CSME_CIE',   '', 'Label', '', 'Classes', false);
INSERT INTO T_TNS VALUES (5,  'field_default_obj.gif', 'I_INS',   '', 'Label', '', 'Instances', false);
INSERT INTO T_TNS VALUES (6,  'Attribute.gif',         'I_AVL',   '', 'Label', '', 'Values', false);
INSERT INTO T_TNS VALUES (7,  'event.gif',             'I_EVI',   '', 'Label', '', 'PendingEvents', false);
INSERT INTO T_TNS VALUES (8, 'state_obj.gif',         'SM_STATE','', 'Name', '', 'Class States', false);
INSERT INTO T_TNS VALUES (9, 'state_obj.gif',         'SM_STATE','', 'Name', '', 'States', false);
INSERT INTO T_TNS VALUES (10, 'Attribute.gif',         'RV_RVL',  '', 'Label', '', 'RuntimeValues', false);
INSERT INTO T_TNS VALUES (11,  '',                      'C_C',   '', 'Label', '', 'Components', false);
INSERT INTO T_TNS VALUES (12,  'field_default_obj.gif', 'I_EXE',   '', 'Label', '', 'Component Instances', false);
INSERT INTO T_TNS VALUES (13,  '',                      'C_PO',   '', 'Name', '', 'Ports', false);
INSERT INTO T_TNS VALUES (14,  '',                      'C_P',   '', 'Name', '', 'Provisions', false);
INSERT INTO T_TNS VALUES (15,  '',                      'C_R',   '', 'Name', '', 'Requirements', false);
INSERT INTO T_TNS VALUES (16,  '',                      'SPR_PO',   '', 'Name', '', 'Provided Operations', false);
INSERT INTO T_TNS VALUES (17,  '',                      'SPR_PS',   '', 'Name', '', 'Provided Signals', false);
INSERT INTO T_TNS VALUES (18,  '',                      'SPR_RO',   '', 'Name', '', 'Required Operations', false);
INSERT INTO T_TNS VALUES (19,  '',                      'SPR_RS',   '', 'Name', '', 'Required Signals', false);
INSERT INTO T_TNS VALUES (20, '',                       'C_PP',  '', 'Name', '', 'Parameters', false);
INSERT INTO T_TNS VALUES (21, '',                       'O_TFR',  '', 'Name', '', 'Operations', false);
INSERT INTO T_TNS VALUES (22, '',                       'CL_IC',  '', 'Name', '', 'Component References', false);
INSERT INTO T_TNS VALUES (23,  'field_default_obj.gif', 'I_EXE',   '', 'Label', '', 'Component Reference Instances', false);
INSERT INTO T_TNS VALUES (24, '',                       'I_LIP',  '', 'Label', '', 'Associations', false);
INSERT INTO T_TNS VALUES (25, '',        'S_FPK',   '', 'Name', '', 'Function Packages', false);
INSERT INTO T_TNS VALUES (26,  '', 'S_SPARM', '', 'Name', '', 'Function Parameters', true);
INSERT INTO T_TNS VALUES (27,  '', 'EP_PKG', '', 'Name', '', 'Packages', false);

INSERT INTO T_TPS VALUES (1,  2,  '->S_DOM[R28]->I_EXE[R2948]->S_DOM[R2948]');
INSERT INTO T_TPS VALUES (2,  4,  '->I_EXE[R2948]->CSME_CIE[R2960]');
INSERT INTO T_TPS VALUES (4,  21, '->O_OBJ[R2961]->O_TFR[R115]', '', 'getInstance_based() == 0');
INSERT INTO T_TPS VALUES (5,  9,  '->SM_STATE[R2915]');
INSERT INTO T_TPS VALUES (4,  5,  '->I_INS[R2962]');
INSERT INTO T_TPS VALUES (5,  6,  '->I_AVL[R2909]');
INSERT INTO T_TPS VALUES (5,  7,  '->I_EVI[R2935]');
INSERT INTO T_TPS VALUES (4,  8,  '->CSME_CIS[R2932]->SM_STATE[R2932]');
INSERT INTO T_TPS VALUES (6,  10, '->RV_RVL[R3304]->RV_SVL[R3300]->RV_VIS[R3301]->RV_RVL[R3301]');
INSERT INTO T_TPS VALUES (6,  10, '->RV_RVL[R3304]->RV_AVL[R3300]->RV_VIA[R3302]->RV_RVL[R3302]');
INSERT INTO T_TPS VALUES (10, 10, '->RV_SVL[R3300]->RV_VIS[R3301]->RV_RVL[R3301]');
INSERT INTO T_TPS VALUES (10, 10, '->RV_AVL[R3300]->RV_VIA[R3302]->RV_RVL[R3302]');
INSERT INTO T_TPS VALUES (1,  11, '->CP_CP[R4606]->C_C[R4608]->I_EXE[R2955]->C_C[R2955]', '', 'Isparentexecuting() == false');
INSERT INTO T_TPS VALUES (1,  22, '->CP_CP[R4606]->C_C[R4608]->CL_IC[R4201]->I_EXE[R2963]->CL_IC[R2963]', '', 'Isparentexecuting() == false');
INSERT INTO T_TPS VALUES (22, 23, '->I_EXE[R2963]');
INSERT INTO T_TPS VALUES (11, 12, '->I_EXE[R2955]');
INSERT INTO T_TPS VALUES (12,  4, '->CSME_CIE[R2960]', '', 'Isingenericpackage() == false');
INSERT INTO T_TPS VALUES (12,  13, '->C_C[R2955]->C_PO[R4010]');
INSERT INTO T_TPS VALUES (12,  13, '->CL_IC[R2963]->C_C[R4201]->C_PO[R4010]');
INSERT INTO T_TPS VALUES (12,  27, '->C_C[R2955]->PE_PE[R8003]->EP_PKG[R8001]', '', 'Isexecutingorownsexecutableelements() == true');
INSERT INTO T_TPS VALUES (12,  27, '->CL_IC[R2963]->C_C[R4201]->PE_PE[R8003]->EP_PKG[R8001]', '', 'Isexecutingorownsexecutableelements() == true');
INSERT INTO T_TPS VALUES (13,  14, '->C_IR[R4016]->C_P[R4009]');
INSERT INTO T_TPS VALUES (13,  15, '->C_IR[R4016]->C_R[R4009]');
INSERT INTO T_TPS VALUES (14,  16, '->SPR_PEP[R4501]->SPR_PO[R4503]');
INSERT INTO T_TPS VALUES (14,  17, '->SPR_PEP[R4501]->SPR_PS[R4503]');
INSERT INTO T_TPS VALUES (15,  18, '->SPR_REP[R4500]->SPR_RO[R4502]');
INSERT INTO T_TPS VALUES (15,  19, '->SPR_REP[R4500]->SPR_RS[R4502]');
INSERT INTO T_TPS VALUES (16,  20, '->SPR_PEP[E4503]->C_EP[R4501]->C_PP[R4006]');
INSERT INTO T_TPS VALUES (17,  20, '->SPR_PEP[R4503]->C_EP[R4501]->C_PP[R4006]');
INSERT INTO T_TPS VALUES (18,  20, '->SPR_REP[R4502]->C_EP[R4500]->C_PP[R4006]');
INSERT INTO T_TPS VALUES (19,  20, '->SPR_REP[R4502]->C_EP[R4500]->C_PP[R4006]');
INSERT INTO T_TPS VALUES (5,   24, '->I_LIP[R2958]');
INSERT INTO T_TPS VALUES (24,   5, '->I_LNK[R2901]->I_LIP[R2902]->I_INS[R2958]');
INSERT INTO T_TPS VALUES (24,   5, '->I_LNK[R2901]->I_LIP[R2903]->I_INS[R2958]');
INSERT INTO T_TPS VALUES (24,   5, '->I_LNK[R2902]->I_LIP[R2901]->I_INS[R2958]');
INSERT INTO T_TPS VALUES (24,   5, '->I_LNK[R2902]->I_LIP[R2903]->I_INS[R2958]');
INSERT INTO T_TPS VALUES (24,   5, '->I_LNK[R2903]->I_LIP[R2901]->I_INS[R2958]');
INSERT INTO T_TPS VALUES (24,   5, '->I_LNK[R2903]->I_LIP[R2902]->I_INS[R2958]');
INSERT INTO T_TPS VALUES (12,  25, '->C_C[R2955]->CN_DC[R4204]->S_DOM[R4204]->S_FPK[R29]');
INSERT INTO T_TPS VALUES (12,  25, '->CL_IC[R2963]->C_C[R4201]->CN_DC[R4204]->S_DOM[R4204]->S_FPK[R29]');
INSERT INTO T_TPS VALUES (2,   25, '->I_EXE[R2948]->S_DOM[R2948]->S_FPK[R29]');
INSERT INTO T_TPS VALUES (25,  25, '->S_FPIP[R30]->S_FPK[R32]');
INSERT INTO T_TPS VALUES (25,   3, '->S_SYNC[R31]');
INSERT INTO T_TPS VALUES (3,   26, '->S_SPARM[R24]');
INSERT INTO T_TPS VALUES (1, 27, '->EP_PKG[R1401]', '', 'Isexecutingorischildexecuting() == true');
INSERT INTO T_TPS VALUES (27, 27, '->PE_PE[R8000]->EP_PKG[R8001]', '', 'Isexecutingorownsexecutableelements() == true');
INSERT INTO T_TPS VALUES (27,   3, '->PE_PE[R8000]->S_SYNC[R8001]');
INSERT INTO T_TPS VALUES (27,   4, '->CSME_CIE[R2971]');
INSERT INTO T_TPS VALUES (27,   11, '->PE_PE[R8000]->C_C[R8001]', '', 'Isexecutingorischildexecuting() == true');
INSERT INTO T_TPS VALUES (27,   22, '->PE_PE[R8000]->CL_IC[R8001]', '', 'Isexecuting() == true');
INSERT INTO T_TPS VALUES (12,   12, '->I_CIN[R2974]->I_EXE[R2975]');
--
-- $Log$
-- Revision 1.20  2013/01/10 23:14:07  rmulvey
-- job:dts0100939343
-- Updated the copyright to 2013
--
-- Revision 1.19  2012/01/23 21:26:25  kbrown
-- job:dts0100848212
-- Batch commit of copyright updated files.
--
-- Revision 1.18  2011/05/30 20:22:42  kbrown
-- job:dts0100742889
-- Updated copy right.
--
-- Revision 1.17  2011/05/24 20:25:10  tlondon
-- Job: dts0100739026 dts0100761926 dts0100771722 dts0100770255 dts0100770588 dts0100749315 dts0100732624 dts0100696901 dts0100719568
--
-- Promote changes
--
-- Revision 1.16.26.1  2011/05/22 16:14:05  tlondon
-- Job: dts0100770305
--
-- Commit branch for next promotion to allow others to help
--
-- Revision 1.16.24.1  2011/05/17 13:14:41  campbell
-- Job:dts0100749315
-- Make packages containing executable elements visible in the Session Explorer.
--
-- Revision 1.16  2010/06/07 01:52:53  kbrown
-- job:dts0100631941 dts0100693353
-- Batch commit of promotion files.
--
-- Revision 1.15.6.1  2010/06/04 14:26:06  rmulvey
-- job:dts0100631941
-- Created a final-review branch (dts0100631941-06-finalReview)
--
-- Revision 1.15.4.3  2010/05/31 19:17:42  campbell
-- Job:dts0100631941
-- Fix multiple issues with launching nested components. Fix a couple annoying consistency errors. Posted new expected results for a couple debug.ui tests where the new launch logic starts the components in a slightly different order.
--
-- Revision 1.15.4.2  2010/05/13 13:38:58  campbell
-- Job:dts0100631941
--
-- - Support Delegation execution:
--   - Make Component Instances nested.
--   - Chain Channels together.
--   - Associate Channels with Delegations (in addition to Satisfactions).
--   - Change Signal Invocation.execute(), Interface Operation.execute() and Message Value.getValue() to implement execution across delegations.
-- - Fix serious GDI resource leak.
-- - Allow Delegations in generic packages to be persisted.
-- - Fixed hang in generic package component assignment.
-- - Fixed problems with location of data type instances in generic package parser verification.
--
-- Revision 1.15.4.1.2.1  2010/05/12 10:23:22  campbell
-- Job:dts0100631941
-- Committing work in progress.
--
-- Revision 1.15.4.1  2010/04/15 23:25:50  campbell
-- Job:dts0100631941
-- Support Component based generic package execution.
--
-- Revision 1.15.2.1  2010/04/15 14:26:30  campbell
-- Job:dts0100631941
-- Check in of work in progress.
--
-- Revision 1.15  2010/02/19 23:34:09  kbrown
-- job:dts0100637015
-- Batch commit of promotion files to HEAD.
--
-- Revision 1.14.4.1  2010/02/18 22:24:39  kbrown
-- Job:dts0100637015
-- Batch commit  of files into new review branch Review-dts0100637015-02 for all branched projects.  See INT for file list.
--
-- Revision 1.14.2.1  2010/02/08 21:37:42  nmohamad
-- job:dts0100637015
-- Update project with the new classes names  _Component Reference_ and
-- _Component Instance_
--
-- Revision 1.14  2010/01/05 04:00:43  kbrown
-- job:dts0100644853
-- Batch commit of copyright change from 2009 to 2010 for BP CVS projects.
--
-- Revision 1.13  2009/12/02 20:44:33  rmulvey
-- job:dts0100633339
-- Promoted from Review-dts0100633339-01
--
-- Revision 1.12.28.1  2009/12/02 06:38:59  rmulvey
-- job:dts0100633339
-- Created Review-dts0100633339-01 to capture the changes promoted from dts0100628581.
--
-- Revision 1.12.26.1  2009/12/01 21:41:28  rmulvey
-- job:dts0100633339
-- Created a new, clean branch for this work named Review-dts0100633339 to capture the latest changes in head.
--
-- Revision 1.12.24.1  2009/11/23 15:48:16  campbell
-- Job: dts0100633339
-- Block check in of work in progress.
--
-- Revision 1.12  2009/01/01 23:27:56  rmulvey
-- Job:4060
-- Batch promotion of copyright changes from Review-i4060 and Review-i4060-01.
--
-- Revision 1.11.2.1  2008/12/31 15:53:16  rmulvey
-- Job:4060
-- This is a batch commit of 2009 copyright changes to branch Review-i4060.  This includes the
-- report that outlines all changes made (before/after for each line changed).  This report is found here: <cvs>/Documentation/internal/test_results/R2_1_2/i4060/update-copyright-results.txt.
--
-- Revision 1.11  2008/10/24 06:04:44  kbrown
-- Job:3405 3898 3942 3961 3337 3915
-- Batch commit of Review-i3405-03 to HEAD.
--
-- Revision 1.10.2.7  2008/10/15 18:03:37  tlondon
-- Job: 3405
--
-- Add support for showing array values in session explorer.
--
-- Revision 1.10.2.6  2008/10/07 13:09:27  rmulvey
-- Job:3405
-- Removed a couple of entries that were causes S_SYNC instances to be duplicated in multiple locations.
--
-- Revision 1.10.2.5  2008/10/03 19:25:42  rmulvey
-- Job:3405
-- Updated the PEI data so that domain functions only appear in the package they are associated with.  Before this change
--  they were showing up under the domain (or formal component) AND in the S_FPK.
--
-- Revision 1.10.2.4  2008/10/02 17:29:56  rmulvey
-- Job:3405
-- Added the appropriate PEI data to the session explorer PEI data to cause domain function packages and their associated
-- functions to appear in SE.  Since we removed the execute option from ME this is necessary and it is also being user-friendly
-- (users shouldn't have to switch between views to execute).
--
-- Revision 1.10.2.3  2008/09/30 19:40:26  rmulvey
-- Job:3405
-- Updated I_INS session data to account for the introduction of I_LIP.
--
-- Revision 1.10.2.2  2008/09/30 18:50:02  rmulvey
-- Job:3405
-- I_EXE->CL_IC uses R2963 not R2958.  Updated accordingly.
--
-- Revision 1.10.2.1  2008/09/30 05:12:26  rmulvey
-- Job:3405
-- Batch commit of the i3405 branch merged with the latest changes from head to create a new branch (Review-i3405-03).
--
-- Revision 1.10  2008/09/15 15:10:49  kbrown
-- Job:3038 2765 3621
-- Batch checkin of promotion files to HEAD.
--
-- Revision 1.9.24.1  2008/09/09 21:26:00  rmulvey
-- Job:3038
-- Added PEI data to support viewing and traversing instances from I_LIP.
--
-- Revision 1.9.8.1  2008/07/01 22:20:47  campbell
-- Job: 3038
-- Introduced.
--
-- Revision 1.9.22.1  2008/09/08 15:41:17  rmulvey
-- Job:3405
-- Created a new working branch based on head. The new branch is R2_1_0-RWM-i3405.
-- The old branch was R2_0_4-RWM-i3405-01.
--
-- Revision 1.9.16.1  2008/08/06 18:39:05  rmulvey
-- Job:3405
-- Created a new branch (R2_0_4-RWM-i3405-01) to incorporate the latest changes from head.
--
-- Revision 1.9.14.1  2008/07/14 20:44:01  rmulvey
-- Job:3405
-- This is a batch commit of changes into a new, clean, branch (R2_0_4-RWM-i3405).  This new  branch is based on the work in progress from branch Review-i3405-02 which was shelved several months ago.  The history for this is that initially this issue
-- was going to contain the work for BOTH intra-component verifier and inter-component verifier.   During implemenation the
-- intra-component verfiier portion of the issue was split out into a seperate issue, bug 3099, and completed for BP v2.0.0.
--
-- Revision 1.9.4.2  2008/04/15 20:32:22  tlondon
-- Job: 3405
--
-- Show imported components under parent component when both are executing.
-- Show component instances under imported components.
--
-- Revision 1.9.4.1  2008/03/24 19:36:49  tlondon
-- Job: 3405
--
-- Batch commit of merge with latest from HEAD.
--
-- Revision 1.9.2.1  2008/03/24 17:58:10  tlondon
-- Job: 3405
--
-- First pass at reworking session explorer to only show elements that are being verified.
--
-- Revision 1.9  2008/02/28 21:51:38  rmulvey
-- Job:3099
-- Promoted from branch Review-i3099
--
-- Revision 1.8.4.4  2008/02/08 02:57:36  tlondon
-- Job: 3099
--
-- Add function packages and class based operations to session tree.
--
-- Revision 1.8.4.3  2008/02/06 21:38:04  tlondon
-- Job: 3099
--
-- Show execution engines as instances of a component.
--
-- Revision 1.8.4.2  2008/02/05 17:49:02  tlondon
-- Job: 3099
--
-- Adjust tree according to analysis note.
--
-- Revision 1.8.4.1  2008/01/31 17:12:41  tlondon
-- Job: 3099
--
-- Created new branch from latest HEAD revision.
--
-- Revision 1.8.2.1  2008/01/15 17:35:34  tlondon
-- Job: 3099
--
-- Remove formal component special casing, add component support.
--
-- Revision 1.8  2008/01/08 20:27:41  rmulvey
-- Job:3349
-- Promoted from Review-i3349.  The copyright has been updated to 2008 and the version has been bumped to 1.5.4.
--
-- Revision 1.7.2.1  2007/12/21 18:09:41  rmulvey
-- Job:3339
-- Checking-in the copyriight changes in branch Review-i3349.  A detailed list of these changes is found
-- in the report produced by the utility used to changesthese messages.  This report is here:
-- Documentation/internal/test_results/R1_5_4/i3349/update-copyright-results.txt
--
-- Revision 1.7  2007/12/13 16:12:43  rmulvey
-- Job:3310
-- Promoted from Review-i3310.
--
-- Revision 1.6.2.1  2007/12/12 02:11:01  campbell
-- Job: 3310
-- Show executing nested components.
--
-- Revision 1.6  2007/12/05 20:31:33  kbrown
-- Job:3261
-- Batch promotion of ui.session changes to HEAD.
--
-- Revision 1.5.4.1  2007/12/01 02:52:08  campbell
-- Job: 3261
-- Allow components to be shown in the Session Explorer tree.
--
-- Revision 1.5.2.2  2007/11/29 21:47:57  tlondon
-- Job: 3261
--
-- Show components in session explorer.
--
-- Revision 1.5.2.1  2007/11/29 18:05:40  campbell
-- Job: 2876
-- Allow components to be shown in the Session Explorer tree.
--
-- Revision 1.5  2007/09/05 02:10:33  kbrown
-- Job:2673
-- Promoting copyright changes for plugins:
-- mc.xmiexport
-- pkg
-- pkg-feature
-- test
-- ui.canvas
-- ui.canvas.test
-- ui.explorer
-- ui.explorer.test
-- ui.properties
-- ui.properties.test
-- ui.session
--
-- Revision 1.4.4.1  2007/09/01 01:33:16  rmulvey
-- Job:2673
-- Updated copyright messages and version numbers in Review-i2673-01.
--
-- Revision 1.4  2007/05/11 23:39:53  tlondon
-- Job: 2857
-- Add Runtime values into the display tree.  Add SDTs.
--
-- Revision 1.3.18.1  2007/05/10 16:54:25  campbell
-- Job: 2857
-- Add Runtime values into the display tree.
--
-- Revision 1.3.14.1  2007/04/12 23:45:48  campbell
-- Job: 2857
-- Add SDT's to debug UI.
--
-- Revision 1.3  2006/12/12 21:56:48  tlondon
-- Job: 2286
--
-- Promotion.
--
-- This is a batch promotion check the resource history for more information.
-- Or see the design note and implementation note named after this issue number.
--
-- Revision 1.2.12.1  2006/12/11 19:03:14  tlondon
-- Job: 2286
--
-- Commit changes to branch to allow others to test against in parallel with promotion.
--
-- Revision 1.2.10.1  2006/10/21 12:42:26  campbell
-- Job:2286
-- Introduced.
--
-- Revision 1.2.8.1.4.3  2006/08/30 19:01:07  campbell
-- Job:2286
-- Show current state with its own icon.
--
-- Revision 1.2.8.1.4.2  2006/08/28 23:05:48  campbell
-- Job: 2286
-- Clean up category names.
--
-- Revision 1.2.8.1.4.1  2006/08/10 21:02:13  campbell
-- Job: 2286
-- Fix ugly tree presentation.
--
-- Revision 1.2.8.1  2006/07/19 14:35:28  greg
-- Job: 1850
-- Merge with HEAD 7/19/2007
--
-- Revision 1.2.6.1  2006/07/10 15:10:38  jluebker
-- Job: 2164
-- Introduce
--
-- Revision 1.2.2.3  2006/06/28 21:35:56  jeremyr
-- Job:2164
-- Changed a comment
-- Removed the 'depricated' keyword and replaced with an empty '' to match the other parameters
--
-- Revision 1.2.2.2  2006/06/28 16:35:04  jeremyr
-- Job:2164
-- Removed 'Instance' prefix from filters name
--
-- Revision 1.2.2.1  2006/06/28 03:31:28  jeremyr
-- Job:2164
-- Introduce
--
-- Revision 1.1.4.2  2006/01/05 15:45:36  jluebker
-- Job 272:
-- Attribute.Name is now being displayed in the Instance Explorer View. It is displayed in another tree level under the Attribute Value.
-- Note: that this is not a permanent solution, just a way for us to get more information in that view temporarily.
--
-- Revision 1.1.4.1  2005/10/05 15:53:30  campbell
-- Job: 272
-- Introduced.
--
-- Revision 1.1.2.1  2005/10/03 21:26:52  campbell
-- Job:272
-- Introduced.
--