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
INSERT INTO T_TPS VALUES (5,   24, '->I_LIP[R2958]->I_LNK[R2901]->I_LIP[R2902]');
INSERT INTO T_TPS VALUES (5,   24, '->I_LIP[R2958]->I_LNK[R2902]->I_LIP[R2901]');
INSERT INTO T_TPS VALUES (5,   24, '->I_LIP[R2958]->I_LNK[R2903]->I_LIP[R2901]');
INSERT INTO T_TPS VALUES (5,   24, '->I_LIP[R2958]->I_LNK[R2903]->I_LIP[R2902]');
INSERT INTO T_TPS VALUES (24,  5, '->I_LNK[R2901]->I_LIP[R2903]->I_INS[R2958]');
INSERT INTO T_TPS VALUES (24,  5, '->I_LNK[R2902]->I_LIP[R2903]->I_INS[R2958]');
INSERT INTO T_TPS VALUES (24,  5, '->I_INS[R2958]'); 
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