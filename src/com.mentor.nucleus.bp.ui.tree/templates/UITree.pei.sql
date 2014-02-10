--======================================================================
--
-- File:      $RCSfile$
-- Version:   $Revision$
-- Modified:  $Date$
--
--(c) Copyright 2006-2013 by Mentor Graphics Corp.  All rights reserved.
--
--========================================================================
-- Licensed under the Apache License, Version 2.0 (the "License"); you may not
-- use this file except in compliance with the License.  You may obtain a copy
-- of the License at
--
--      http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
-- WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
-- License for the specific language governing permissions and limitations under
-- the License.
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
INSERT INTO T_TNS VALUES (1,  '', 'S_SYS',   '', 'Name', '', 'System', false);
INSERT INTO T_TNS VALUES (2,  '', 'S_DOM',   '', 'Name', '', 'Domains', false);
INSERT INTO T_TNS VALUES (5,  '', 'S_SS',    '', 'Name', '', 'Subsystems', false);
INSERT INTO T_TNS VALUES (6,  '', 'O_OBJ',   '', 'Name', '', 'Classes', false);
INSERT INTO T_TNS VALUES (7,  '', 'O_ATTR',  '', 'Name', '', 'Attributes', true);
INSERT INTO T_TNS VALUES (10, '', 'O_TFR',   '', 'Name', '', 'Operations', false);
INSERT INTO T_TNS VALUES (8,  '', 'O_TPARM', '', 'Name', '', 'Operation Parameters', false);
INSERT INTO T_TNS VALUES (25, '', 'SM_ISM',  'Instance State Machine', '', '', 'Instance State Machines', false);
INSERT INTO T_TNS VALUES (11, '', 'SM_STATE','', 'Name', '', 'States', false);
INSERT INTO T_TNS VALUES (12, '', 'SM_EVT',  '', 'Mning','', 'Events', false);
INSERT INTO T_TNS VALUES (14, '', 'SM_EVTDI','', 'Name', '', 'State Machine Event Data Items', false);
INSERT INTO T_TNS VALUES (15, '', 'SM_ASM',  'Class State Machine', '', '', 'Class State Machines', false);
INSERT INTO T_TNS VALUES (16, '', 'S_FPK',   '', 'Name', '', 'Function Packages', false);
INSERT INTO T_TNS VALUES (3,  '', 'S_SYNC',  '', 'Name', '', 'Functions', false);
INSERT INTO T_TNS VALUES (4,  '', 'S_SPARM', '', 'Name', '', 'Function Parameters', false);
INSERT INTO T_TNS VALUES (17, '', 'S_DPK',   '', 'Name', '', 'Data Type Packages', false);
INSERT INTO T_TNS VALUES (18, '', 'S_EEPK',  '', 'Name', '', 'External Entity Packages', false);
INSERT INTO T_TNS VALUES (19, '', 'S_UDT',   '', 'Name', '->S_DT[R17]', 'User Defined Data Types', false);
INSERT INTO T_TNS VALUES (20, '', 'S_EE',    '', 'Name', '', 'External Entities', false);
INSERT INTO T_TNS VALUES (21, '', 'S_BRG',   '', 'Name', '', 'Bridge Operations', false);
INSERT INTO T_TNS VALUES (22, '', 'S_BPARM', '', 'Name', '', 'Bridge Parameters', false);
INSERT INTO T_TNS VALUES (23, '', 'S_EDT',   '', 'Name', '->S_DT[R17]', 'Enumerations', false);
INSERT INTO T_TNS VALUES (24, '', 'S_ENUM',  '', 'Name', '', 'Enumerators', false);
INSERT INTO T_TNS VALUES (26, '', 'SM_SM',   'Event Data Items', '', '', 'Event Data Item Packages', false);
INSERT INTO T_TNS VALUES (28, '', 'S_CDT',   '', 'Name', '->S_DT[R17]', 'Core Data Types', false);
INSERT INTO T_TNS VALUES (29, '', 'SM_SDI',  '', 'Name', '->SM_EVTDI[R522]', 'Event Data Items', false);

INSERT INTO T_TPS VALUES (1, 2,   '->S_DOM[R28]');
INSERT INTO T_TPS VALUES (2, 17,  '->S_DPK[R40]');
INSERT INTO T_TPS VALUES (17,17,  '->S_DPIP[R37]->S_DPK[R38]');
INSERT INTO T_TPS VALUES (17,28,  '->S_DT[R39]->S_CDT[R17]');
INSERT INTO T_TPS VALUES (17,19,  '->S_DT[R39]->S_UDT[R17]');
INSERT INTO T_TPS VALUES (17,23,  '->S_DT[R39]->S_EDT[R17]');
INSERT INTO T_TPS VALUES (2, 16,  '->S_FPK[R29]');
INSERT INTO T_TPS VALUES (16,16,  '->S_FPIP[R30]->S_FPK[R32]');
INSERT INTO T_TPS VALUES (16,3,   '->S_SYNC[R31]');
INSERT INTO T_TPS VALUES (3, 4,   '->S_SPARM[R24]');
INSERT INTO T_TPS VALUES (2, 18,  '->S_EEPK[R36]');
INSERT INTO T_TPS VALUES (18,18,  '->S_EEPIP[R34]->S_EEPK[R35]');
INSERT INTO T_TPS VALUES (18,20,  '->S_EE[R33]');
INSERT INTO T_TPS VALUES (20,21,  '->S_BRG[R19]');
INSERT INTO T_TPS VALUES (21,22,  '->S_BPARM[R21]');
INSERT INTO T_TPS VALUES (2, 5,   '->S_SS[R43]');
INSERT INTO T_TPS VALUES (5, 5,   '->S_SIS[R41]->S_SS[R42]');
INSERT INTO T_TPS VALUES (5, 6,   '->O_OBJ[R2]');
INSERT INTO T_TPS VALUES (6, 7,   '->O_ATTR[R102]');
INSERT INTO T_TPS VALUES (6, 10,  '->O_TFR[R115]');
INSERT INTO T_TPS VALUES (10,8,   '->O_TPARM[R117]');
INSERT INTO T_TPS VALUES (6, 25,  '->SM_ISM[R518]');
INSERT INTO T_TPS VALUES (25,11,  '->SM_SM[R517]->SM_STATE[R501]');
INSERT INTO T_TPS VALUES (25,12,  '->SM_SM[R517]->SM_EVT[R502]');
INSERT INTO T_TPS VALUES (12,29,  '->SM_SUPDT[R520]->SM_SDI[R522]');
INSERT INTO T_TPS VALUES (6, 15,  '->SM_ASM[R519]');
INSERT INTO T_TPS VALUES (15,11,  '->SM_SM[R517]->SM_STATE[R501]');
INSERT INTO T_TPS VALUES (15,12,  '->SM_SM[R517]->SM_EVT[R502]');
INSERT INTO T_TPS VALUES (23,24,  '->S_ENUM[R27]');
INSERT INTO T_TPS VALUES (25,26,  '->SM_SM[R517]');
INSERT INTO T_TPS VALUES (15,26,  '->SM_SM[R517]');
INSERT INTO T_TPS VALUES (26,14,  '->SM_EVTDI[R516]');
--
-- $Log$
-- Revision 1.9  2013/01/10 23:19:52  rmulvey
-- job:dts0100939343
-- Updated the copyright to 2013
--
-- Revision 1.8  2012/01/23 21:31:15  kbrown
-- job:dts0100848212
-- Batch commit of copyright updated files.
--
-- Revision 1.7  2011/05/30 20:37:08  kbrown
-- job:dts0100742889
-- Updated copy right.
--
-- Revision 1.6  2010/01/05 04:01:04  kbrown
-- job:dts0100644853
-- Batch commit of copyright change from 2009 to 2010 for BP CVS projects.
--
-- Revision 1.5  2009/01/01 23:28:04  rmulvey
-- Job:4060
-- Batch promotion of copyright changes from Review-i4060 and Review-i4060-01.
--
-- Revision 1.4.18.1  2008/12/31 15:49:15  rmulvey
-- Job:4060
-- This is a batch commit of 2009 copyright changes to branch Review-i4060.  This includes the
-- report that outlines all changes made (before/after for each line changed).  This report is found here: <cvs>/Documentation/internal/test_results/R2_1_2/i4060/update-copyright-results.txt.
--
-- Revision 1.4  2008/01/08 20:24:34  rmulvey
-- Job:3349
-- Promoted from Review-i3349.  The copyright has been updated to 2008 and the version has been bumped to 1.5.4.
--
-- Revision 1.3.6.1  2007/12/21 17:54:12  rmulvey
-- Job:3339
-- Checking-in the copyriight changes in branch Review-i3349.  A detailed list of these changes is found
-- in the report produced by the utility used to changesthese messages.  This report is here:
-- Documentation/internal/test_results/R1_5_4/i3349/update-copyright-results.txt
--
-- Revision 1.3  2007/09/05 02:22:29  kbrown
-- Job:2673
-- Promoting copyright changes for plugins:
-- ui.text
-- ui.text.test
-- ui.tree
-- verifer.pkg
-- verifier.pkg-feature
-- welcome
-- welcome.test
-- help.bp.mc
-- help.bp.mc2020
-- internal.test
--
-- Revision 1.2.24.1  2007/09/01 01:38:09  rmulvey
-- Job:2673
-- Updated copyright messages and version numbers in Review-i2673-01.
--
-- Revision 1.2  2006/12/12 21:57:02  tlondon
-- Job: 2286
--
-- Promotion.
--
-- This is a batch promotion check the resource history for more information.
-- Or see the design note and implementation note named after this issue number.
--
-- Revision 1.1.14.1  2006/12/11 19:01:35  tlondon
-- Job: 2286
--
-- Commit changes to branch to allow others to test against in parallel with promotion.
--
-- Revision 1.1.12.1  2006/10/21 12:49:20  campbell
-- Job:2286
-- Introduced.
--
-- Revision 1.1.10.1  2006/07/19 14:34:50  greg
-- Job: 1850
-- Merge with HEAD 7/19/2007
--
-- Revision 1.1.8.1  2006/07/10 14:59:11  jluebker
-- Job: 2164
-- Introduce
--
-- Revision 1.1.4.2  2006/07/05 14:17:54  jeremyr
-- Job:2164
-- Updated the template to no longer use the 'depricated' keyword in the 2nd parameter.  The parameter is now blank like other unused parameters.
--
-- Updated comments to reflect how the 2nd parameter works
--
-- Revision 1.1.4.1  2006/06/27 23:14:15  jeremyr
-- Job:2164
-- Introduce
--
-- Revision 1.1.2.4  2006/06/27 21:47:12  jeremyr
-- Job:2164
-- Formatted text
--
-- Revision 1.1.2.3  2006/06/27 21:28:53  jeremyr
-- Job:2164
-- Changed all data values for the 2nd parameterof T_TNS entries to be 'depricated' since the values are ignored.
--
-- Added a comment explaining how the icon for the tree entry should be specified in the bp.core/sql/ooaofooa_hierarchy.pei.sql file.
--
-- Revision 1.1.2.2  2006/06/20 14:08:43  jeremyr
-- Job:2164
-- Copied old log entries into the template for historical reference
--
-- Revision 1.1.2.1  2006/06/05 23:12:25  jeremyr
-- Job:2164
-- Introduce
--
-- Revision 1.14  2005/06/28 00:28:19  pablo
-- Job: 367
-- Change 'Datatype' to 'Data Type'
--
-- Revision 1.13.122.1  2005/06/27 15:12:49  greg
-- Job: 367
-- Change 'Datatype' to 'Data Type'
--
-- Revision 1.13.120.1  2005/06/23 22:52:10  greg
-- Job: 367
-- Change 'Datatype' to 'Data Type'
--
-- Revision 1.13  2005/03/16 01:18:13  campbell
-- Job: 702
-- Changed statechart to state machine
--
-- Revision 1.12  2004/12/23 15:33:17  jmather
-- Job: 566
-- Add values for OrderingAssoc and OrderingAttr to T_TNS
-- Change to store forward and reverse association phrases
-- Add values for Sorted attribute
-- Remove values for Ordering* attributes
-- Add T_TNS.Sorted attribute values
--
-- Revision 1.11.72.1  2004/12/16 17:00:22  greg
-- Job: 566
-- Add T_TNS.Sorted attribute values
--
-- Revision 1.11.30.3  2004/12/16 00:46:06  greg
-- Job: 566
-- Add values for Sorted attribute
-- Remove values for Ordering* attributes
--
-- Revision 1.11.30.2  2004/12/06 23:41:29  greg
-- Job: 566
-- Change to store forward and reverse association phrases
--
-- Revision 1.11.30.1  2004/11/22 00:58:49  greg
-- Job: 566
-- Add values for OrderingAssoc and OrderingAttr to T_TNS
--
-- Revision 1.11  2004/09/17 20:15:38  greg
-- Job: 31
-- Change SM_LEVT to SM_EVT
--
-- Revision 1.10  2004/08/05 22:51:04  campbell
-- Job: 352
-- Icons changed.
--
-- Revision 1.9  2004/07/30 00:03:08  campbell
-- Job: 224
-- Fixed incorrect filter category naming.
--
-- Revision 1.8  2004/07/29 18:38:06  pablo
-- Job: 224
-- Added many tree entries
--
-- Revision 1.7.6.2  2004/07/27 17:48:22  pablo
-- Job: 224
-- Adjust Data Items per V1.12 of the .ant
--
-- Revision 1.7.6.1  2004/07/21 22:49:01  pablo
-- Job: 224
-- Initial check in to PRC sandbox
--
-- Revision 1.7  2004/03/10 21:32:26  campbell
-- Job: 100
-- Added Class Statechart to the tree data.
--
-- Revision 1.6  2003/08/07 14:42:59  greg
-- Job: 32
-- Change identifier types from string to unique_id
--
-- Revision 1.5  2003/07/10 20:33:13  campbell
-- Job: 20
-- Cosmetic spacer added.
--
-- Revision 1.4  2003/07/10 20:32:38  campbell
-- Job: 20
-- Added a Log entry.
