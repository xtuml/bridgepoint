--======================================================================
--
-- File:      $RCSfile$
-- Version:   $Revision$
-- Modified:  $Date$
--
-- (c) Copyright 2006-2014 Mentor Graphics Corp. All rights reserved.
--
--======================================================================
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
--======================================================================
-- Note that the order of the TNS nodes here defines the order in which
-- the nodes appear in the tree.
--
INSERT INTO T_TNS VALUES (5,  'class_obj.gif',         'CSME_CIE',   '', 'Label', '', 'MonitorClasses', false);
INSERT INTO T_TNS VALUES (6,  'state_obj.gif',         'SM_STATE','', 'Name', '', 'MonitorClassState', false);
INSERT INTO T_TNS VALUES (7,  'field_default_obj.gif', 'I_INS',   '', 'Label', '', 'MonitorInstances', false);
INSERT INTO T_TNS VALUES (8,  'Attribute.gif',         'I_AVL',   '', 'Label', '', 'MonitorValues', false);
INSERT INTO T_TNS VALUES (9,  'event.gif',             'I_EVI',   '', 'Label', '', 'MonitorPendingEvents', false);
INSERT INTO T_TNS VALUES (10, 'state_obj.gif',         'SM_STATE','', 'Name', '', 'MonitorStates', false);
INSERT INTO T_TNS VALUES (11, 'Attribute.gif',         'RV_RVL',  '', 'Label', '', 'MonitorRuntimeValues', false);

INSERT INTO T_TPS VALUES (5, 6, '->CSME_CIS[R2932]->SM_STATE[R2932]');
INSERT INTO T_TPS VALUES (7, 10, '->SM_STATE[R2915]');
INSERT INTO T_TPS VALUES (7, 8, '->I_AVL[R2909]');
INSERT INTO T_TPS VALUES (7, 9, '->I_EVI[R2935]');
INSERT INTO T_TPS VALUES (8, 11, '->RV_RVL[R3304]->RV_SVL[R3300]->RV_VIS[R3301]->RV_RVL[R3301]');
INSERT INTO T_TPS VALUES (11, 11, '->RV_SVL[R3300]->RV_VIS[R3301]->RV_RVL[R3301]');
INSERT INTO T_TPS VALUES (8, 11, '->RV_RVL[R3304]->RV_AVL[R3300]->RV_VIA[R3302]->RV_RVL[R3302]');
INSERT INTO T_TPS VALUES (11, 11, '->RV_AVL[R3300]->RV_VIA[R3302]->RV_RVL[R3302]');
--