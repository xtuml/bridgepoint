--======================================================================
--
-- File:      $RCSfile$
-- Version:   $Revision$
-- Modified:  $Date$
--
--(c) Copyright 2006-2014 by Mentor Graphics Corp.  All rights reserved.
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
--      org.xtuml.core/sql/ooaofooa_hierarchy.pei.sql
-- for the same key letters (parameter 3) will be used. That file is the common
-- file used by all trees to specify common node data so that all trees will
-- display the same icon for the same class.
--
INSERT INTO T_TNS VALUES (1,  '', 'S_SYS',   '', 'Name', '', 'System', false);
INSERT INTO T_TNS VALUES (6,  '', 'O_OBJ',   '', 'Name', '', 'Classes', false);
INSERT INTO T_TNS VALUES (7,  '', 'O_ATTR',  '', 'Name', '', 'Attributes', true);
INSERT INTO T_TNS VALUES (10, '', 'O_TFR',   '', 'Name', '', 'Operations', false);
INSERT INTO T_TNS VALUES (8,  '', 'O_TPARM', '', 'Name', '', 'Operation Parameters', false);
INSERT INTO T_TNS VALUES (25, '', 'SM_ISM',  'Instance State Machine', '', '', 'Instance State Machines', false);
INSERT INTO T_TNS VALUES (11, '', 'SM_STATE','', 'Name', '', 'States', false);
INSERT INTO T_TNS VALUES (12, '', 'SM_EVT',  '', 'Mning','', 'Events', false);
INSERT INTO T_TNS VALUES (14, '', 'SM_EVTDI','', 'Name', '', 'State Machine Event Data Items', false);
INSERT INTO T_TNS VALUES (15, '', 'SM_ASM',  'Class State Machine', '', '', 'Class State Machines', false);
INSERT INTO T_TNS VALUES (3,  '', 'S_SYNC',  '', 'Name', '', 'Functions', false);
INSERT INTO T_TNS VALUES (4,  '', 'S_SPARM', '', 'Name', '', 'Function Parameters', false);
INSERT INTO T_TNS VALUES (19, '', 'S_UDT',   '', 'Name', '->S_DT[R17]', 'User Defined Data Types', false);
INSERT INTO T_TNS VALUES (20, '', 'S_EE',    '', 'Name', '', 'External Entities', false);
INSERT INTO T_TNS VALUES (21, '', 'S_BRG',   '', 'Name', '', 'Bridge Operations', false);
INSERT INTO T_TNS VALUES (22, '', 'S_BPARM', '', 'Name', '', 'Bridge Parameters', false);
INSERT INTO T_TNS VALUES (23, '', 'S_EDT',   '', 'Name', '->S_DT[R17]', 'Enumerations', false);
INSERT INTO T_TNS VALUES (24, '', 'S_ENUM',  '', 'Name', '', 'Enumerators', false);
INSERT INTO T_TNS VALUES (26, '', 'SM_SM',   'Event Data Items', '', '', 'Event Data Item Packages', false);
INSERT INTO T_TNS VALUES (28, '', 'S_CDT',   '', 'Name', '->S_DT[R17]', 'Core Data Types', false);
INSERT INTO T_TNS VALUES (29, '', 'SM_SDI',  '', 'Name', '->SM_EVTDI[R522]', 'Event Data Items', false);

INSERT INTO T_TPS VALUES (3, 4,   '->S_SPARM[R24]');
INSERT INTO T_TPS VALUES (20,21,  '->S_BRG[R19]');
INSERT INTO T_TPS VALUES (21,22,  '->S_BPARM[R21]');
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