--======================================================================
--
-- File:      $RCSfile: UITree.pei.sql,v $
-- Version:   $Revision: 1.47 $
-- Modified:  $Date: 2013/01/10 23:15:49 $
--
-- (c) Copyright 2003-2014 Mentor Graphics Corporation All rights reserved.
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
INSERT INTO T_TNS VALUES (1,  '', 'S_SYS',   '', 'Name', '', 'System', false);
INSERT INTO T_TNS VALUES (2,  '',       'S_DOM',   '', 'Name', '', 'Domains', false);
INSERT INTO T_TNS VALUES (5,  '',        'S_SS',    '', 'Name', '', 'Subsystems', false);
INSERT INTO T_TNS VALUES (6,  '',         'O_OBJ',   '', 'Name', '', 'Classes', false);
INSERT INTO T_TNS VALUES (7,  '',         'O_ATTR',  '', 'Name', '', 'Attributes', true);
INSERT INTO T_TNS VALUES (10, '',       'O_TFR',   '', 'Name', '', 'Operations', true);
INSERT INTO T_TNS VALUES (8,  '',  'O_TPARM', '', 'Name', '', 'Operation Parameters', true);
INSERT INTO T_TNS VALUES (25, '',        'SM_ISM',  'Instance State Machine', '', '', 'Instance State Machines', false);
INSERT INTO T_TNS VALUES (11, '',       'SM_STATE','', 'Name', '', 'States', false);
INSERT INTO T_TNS VALUES (12, '',             'SM_EVT',  '', 'Mning','', 'Events', false);
INSERT INTO T_TNS VALUES (13, '',             'SM_NLEVT',  '', 'Name','', 'Inherited Events', false);
INSERT INTO T_TNS VALUES (27, '',             'SM_EVT',  '', 'Mning','', 'Delivered Events', false);
INSERT INTO T_TNS VALUES (14, '',  'SM_EVTDI','', 'Name', '', 'State Machine Event Data Items', true);
INSERT INTO T_TNS VALUES (15, '',        'SM_ASM',  'Class State Machine', '', '', 'Class State Machines', false);
INSERT INTO T_TNS VALUES (16, '',        'S_FPK',   '', 'Name', '', 'Function Packages', false);
INSERT INTO T_TNS VALUES (3,  '',       'S_SYNC',  '', 'Name', '', 'Functions', false);
INSERT INTO T_TNS VALUES (4,  '', 'S_SPARM', '', 'Name', '', 'Function Parameters', true);
INSERT INTO T_TNS VALUES (17, '',        'S_DPK',   '', 'Name', '', 'Data Type Packages', false);
INSERT INTO T_TNS VALUES (18, '',        'S_EEPK',  '', 'Name', '', 'External Entity Packages', false);
INSERT INTO T_TNS VALUES (19, '',          'S_UDT',   '', 'Name', '->S_DT[R17]', 'User Defined Data Types', false);
INSERT INTO T_TNS VALUES (20, '',      'S_EE',    '', 'Label', '', 'External Entities', false);
INSERT INTO T_TNS VALUES (21, '',       'S_BRG',   '', 'Name', '', 'Bridge Operations', false);
INSERT INTO T_TNS VALUES (22, '', 'S_BPARM', '', 'Name', '', 'Bridge Parameters', true);
INSERT INTO T_TNS VALUES (23, '',       'S_EDT',   '', 'Name', '->S_DT[R17]', 'Enumerations', false);
INSERT INTO T_TNS VALUES (24, '',        'S_ENUM',  '', 'Name', '', 'Enumerators', true);
INSERT INTO T_TNS VALUES (28, '',      'S_CDT',   '', 'Name', '->S_DT[R17]', 'Core Data Types', false);
INSERT INTO T_TNS VALUES (29, '',  'SM_SDI',   '', 'Name', '->SM_EVTDI[R522]', 'Event Data Items', false);
INSERT INTO T_TNS VALUES (30, '', 'SQ_S', '', 'Name', '', 'Sequences', false);
INSERT INTO T_TNS VALUES (31, '', 'SQ_CIP', '', 'Label', '', 'Instances', false);
INSERT INTO T_TNS VALUES (32, '', 'SQ_AV', '', 'Label', '', 'Informal Instance Attributes', false);
INSERT INTO T_TNS VALUES (34, '', 'MSG_A', '', 'Label', '', 'Informal Message Arguments', false);
INSERT INTO T_TNS VALUES (35, '', 'MSG_A', '', 'Label', '', 'Event Message Arguments', false);
INSERT INTO T_TNS VALUES (36, '', 'MSG_A', '', 'Label', '', 'Function Message Arguments', false);
INSERT INTO T_TNS VALUES (40, '', 'MSG_A', '', 'Label', '', 'Class Message Arguments', false);
INSERT INTO T_TNS VALUES (41, '', 'MSG_A', '', 'Label', '', 'Bridge Message Arguments', false);
INSERT INTO T_TNS VALUES (37, '', 'SQ_CP', '', 'Label', '', 'Imported Classes', false);
INSERT INTO T_TNS VALUES (38, '', 'SQ_EEP', '', 'Label', '', 'Imported External Entities', false);
INSERT INTO T_TNS VALUES (39, '', 'SQ_FPP', '', 'Label', '', 'Imported Function Packages', false);
INSERT INTO T_TNS VALUES (84, '', 'SQ_PP', '', 'Label', '',  'Imported Packages', false);
INSERT INTO T_TNS VALUES (46, '', 'SQ_CPA', '', 'Name', '', 'Informal Class Attributes', false);
INSERT INTO T_TNS VALUES (47, '', 'SQ_AV', '', 'Label', '', 'Formal Instance Attributes', false);
INSERT INTO T_TNS VALUES (50, '', 'SQ_AP', '', 'Name', '', 'Actors', false);
INSERT INTO T_TNS VALUES (33, '', 'MSG_SM', '', 'Label', '', 'Incoming Synchronous Messages', false);
INSERT INTO T_TNS VALUES (51, '', 'MSG_AM', '', 'Label', '', 'Incoming Asynchronous Messages', false);
INSERT INTO T_TNS VALUES (48, '', 'MSG_R', '', 'Name', '', 'Incoming Return Messages', false);
INSERT INTO T_TNS VALUES (52, '', 'COMM_COMM', '', 'Name', '', 'Communications', false);
INSERT INTO T_TNS VALUES (53, '', 'UC_UCC', '', 'Name', '', 'Use Case Diagrams', false);
INSERT INTO T_TNS VALUES (54, '', 'IA_UCP', '', 'Name', '', 'Use Cases', false);
INSERT INTO T_TNS VALUES (55, '', 'A_A',  '', 'Name', '', 'Activities', false);
INSERT INTO T_TNS VALUES (56, '', 'A_GA',  '', 'Name', '', 'Actions', false);
INSERT INTO T_TNS VALUES (57, '', 'A_OBJ',  '', 'Name', '', 'ObjectNodes', false);
INSERT INTO T_TNS VALUES (58, '', 'A_AEA',  '', 'Name', '', 'Accept Event Actions', false);
INSERT INTO T_TNS VALUES (59, '', 'A_SS',  '', 'Name', '', 'Send Signal Actions', false);
INSERT INTO T_TNS VALUES (60, '', 'A_ATE',  '', 'Name', '', 'Accept Time Event Actions', false);
INSERT INTO T_TNS VALUES (61, '', 'IP_IP',  '', 'Name', '', 'Interface Packages', false);
INSERT INTO T_TNS VALUES (62, '', 'C_I',  '', 'Name', '', 'Interfaces', false);
INSERT INTO T_TNS VALUES (63, '', 'C_AS',  '', 'Name', '', 'Signals', true);
INSERT INTO T_TNS VALUES (64, '', 'C_PP',  '', 'Name', '', 'Parameters', true, true);
INSERT INTO T_TNS VALUES (65, '', 'CP_CP',  '', 'Name', '', 'Component Packages', false);
INSERT INTO T_TNS VALUES (66, '', 'C_C',  '', 'Label', '', 'Components', false);
INSERT INTO T_TNS VALUES (67, '', 'C_P',  '', 'Name', '', 'Provided Interfaces', false);
INSERT INTO T_TNS VALUES (68, '', 'C_R',  '', 'Name', '', 'Required Interfaces', false);
INSERT INTO T_TNS VALUES (69, '', 'SPR_RO',  '', 'Name', '', 'Required Interface Operations', false);
INSERT INTO T_TNS VALUES (70, '', 'SPR_PO',  '', 'Name', '', 'Provided Interface Operations', false);
INSERT INTO T_TNS VALUES (71, '', 'SPR_RS',  '', 'Name', '', 'Required Interface Signals', false);
INSERT INTO T_TNS VALUES (72, '', 'SPR_PS',  '', 'Name', '', 'Provided Interface Signals', false);
INSERT INTO T_TNS VALUES (73, '', 'C_IO',  '', 'Name', '', 'Interface Operations', true);
INSERT INTO T_TNS VALUES (74, '', 'CL_IC',  '', 'Name', '', 'Component References', false);
INSERT INTO T_TNS VALUES (75, '', 'S_SDT',   '', 'Name', '->S_DT[R17]', 'Structured Data Types', false);
INSERT INTO T_TNS VALUES (76, '', 'S_MBR',  '', 'Name', '', 'Members', true);
INSERT INTO T_TNS VALUES (77, '', 'C_PO',  '', 'Name', '', 'Ports', false);
INSERT INTO T_TNS VALUES (78, '', 'CL_IP',  '', 'Name', '', 'Imported Provided Interfaces', false);
INSERT INTO T_TNS VALUES (79, '', 'CL_IR',  '', 'Name', '', 'Imported Required Interfaces', false);
INSERT INTO T_TNS VALUES (80, '', 'SQ_COP', '', 'Label', '', 'Component Participants', false);
INSERT INTO T_TNS VALUES (81, '', 'EP_PKG', '', 'Name', '', 'Packages', false);
INSERT INTO T_TNS VALUES (82, '', 'CNST_CSP', '', 'Name', '', 'Constant Specifications', false);
INSERT INTO T_TNS VALUES (83, '', 'CNST_LSC', '', 'Name', '->CNST_LFSC[R1503]->CNST_SYC[R1502]', 'Literal Symbolic Constants', false);
INSERT INTO T_TNS VALUES (85, '', 'CL_POR',  '', 'Name', '', 'Port Reference', false);

INSERT INTO T_TPS VALUES (1, 81, '->EP_PKG[R1401]');
INSERT INTO T_TPS VALUES (81, 81, '->EP_PIP[R1403]->EP_PKG[R1404]');
INSERT INTO T_TPS VALUES (81, 55, '->EP_SPKG[R1400]->A_A[R1402]');
INSERT INTO T_TPS VALUES (81, 52, '->EP_SPKG[R1400]->COMM_COMM[R1402]');
INSERT INTO T_TPS VALUES (81, 65, '->EP_SPKG[R1400]->CP_CP[R1402]');
INSERT INTO T_TPS VALUES (81, 17, '->EP_SPKG[R1400]->S_DPK[R1402]');
INSERT INTO T_TPS VALUES (81, 61, '->EP_SPKG[R1400]->IP_IP[R1402]');
INSERT INTO T_TPS VALUES (81, 30, '->EP_SPKG[R1400]->SQ_S[R1402]');
INSERT INTO T_TPS VALUES (81, 53, '->EP_SPKG[R1400]->UC_UCC[R1402]');
INSERT INTO T_TPS VALUES (81,28,    '->PE_PE[R8000]->S_DT[R8001]->S_CDT[R17]');
INSERT INTO T_TPS VALUES (81,19,    '->PE_PE[R8000]->S_DT[R8001]->S_UDT[R17]');
INSERT INTO T_TPS VALUES (81,23,    '->PE_PE[R8000]->S_DT[R8001]->S_EDT[R17]');
INSERT INTO T_TPS VALUES (81,75,    '->PE_PE[R8000]->S_DT[R8001]->S_SDT[R17]');
INSERT INTO T_TPS VALUES (81,50,    '->PE_PE[R8000]->SQ_P[R8001]->SQ_AP[R930]');
INSERT INTO T_TPS VALUES (81,54,    '->PE_PE[R8000]->SQ_P[R8001]->IA_UCP[R930]');
INSERT INTO T_TPS VALUES (81,59,    '->PE_PE[R8000]->A_N[R8001]->A_ACT[R1105]->A_SS[R1107]');
INSERT INTO T_TPS VALUES (81,56,    '->PE_PE[R8000]->A_N[R8001]->A_ACT[R1105]->A_GA[R1107]');
INSERT INTO T_TPS VALUES (81,58,    '->PE_PE[R8000]->A_N[R8001]->A_ACT[R1105]->A_AE[R1107]->A_AEA[R1112]');
INSERT INTO T_TPS VALUES (81,60,    '->PE_PE[R8000]->A_N[R8001]->A_ACT[R1105]->A_AE[R1107]->A_ATE[R1112]');
INSERT INTO T_TPS VALUES (81,6,     '->PE_PE[R8000]->O_OBJ[R8001]');
INSERT INTO T_TPS VALUES (81,66,    '->PE_PE[R8000]->C_C[R8001]');
INSERT INTO T_TPS VALUES (81,74,    '->PE_PE[R8000]->CL_IC[R8001]');
INSERT INTO T_TPS VALUES (81,62,    '->PE_PE[R8000]->C_I[R8001]');
INSERT INTO T_TPS VALUES (81,81,    '->PE_PE[R8000]->EP_PKG[R8001]');
INSERT INTO T_TPS VALUES (81,82,    '->PE_PE[R8000]->CNST_CSP[R8001]');
INSERT INTO T_TPS VALUES (81,57,    '->PE_PE[R8000]->A_N[R8001]->A_OBJ[R1105]');
INSERT INTO T_TPS VALUES (81,31,    '->PE_PE[R8000]->SQ_P[R8001]->SQ_CIP[R930]');
INSERT INTO T_TPS VALUES (81,38,    '->PE_PE[R8000]->SQ_P[R8001]->SQ_EEP[R930]');
INSERT INTO T_TPS VALUES (81,37,    '->PE_PE[R8000]->SQ_P[R8001]->SQ_CP[R930]');
INSERT INTO T_TPS VALUES (81,80,    '->PE_PE[R8000]->SQ_P[R8001]->SQ_COP[R930]');
INSERT INTO T_TPS VALUES (81,84,    '->PE_PE[R8000]->SQ_P[R8001]->SQ_PP[R930]');
INSERT INTO T_TPS VALUES (81,3,    '->PE_PE[R8000]->S_SYNC[R8001]');
INSERT INTO T_TPS VALUES (81,20,    '->PE_PE[R8000]->S_EE[R8001]');

INSERT INTO T_TPS VALUES (66,81,    '->PE_PE[R8003]->EP_PKG[R8001]');
INSERT INTO T_TPS VALUES (66,62,    '->PE_PE[R8003]->C_I[R8001]');
INSERT INTO T_TPS VALUES (66,66,    '->PE_PE[R8003]->C_C[R8001]');
INSERT INTO T_TPS VALUES (66,74,    '->PE_PE[R8003]->CL_IC[R8001]');
INSERT INTO T_TPS VALUES (66,19,    '->PE_PE[R8003]->S_DT[R8001]->S_UDT[R17]');
INSERT INTO T_TPS VALUES (66,6,     '->PE_PE[R8003]->O_OBJ[R8001]');

INSERT INTO T_TPS VALUES (1, 2, '->S_DOM[R28]');
INSERT INTO T_TPS VALUES (1, 30, '->SQ_S[R950]');
INSERT INTO T_TPS VALUES (1, 53, '->UC_UCC[R1211]');
INSERT INTO T_TPS VALUES (1, 52, '->COMM_COMM[R1136]');
INSERT INTO T_TPS VALUES (1, 55, '->A_A[R1113]');
INSERT INTO T_TPS VALUES (2, 17,   '->S_DPK[R40]');
INSERT INTO T_TPS VALUES (17,17,    '->S_DPIP[R37]->S_DPK[R38]');
INSERT INTO T_TPS VALUES (17,28,	'->S_DT[R39]->S_CDT[R17]');
INSERT INTO T_TPS VALUES (17,19,	'->S_DT[R39]->S_UDT[R17]');
INSERT INTO T_TPS VALUES (17,23,    '->S_DT[R39]->S_EDT[R17]');
INSERT INTO T_TPS VALUES (17,75,    '->S_DT[R39]->S_SDT[R17]');
INSERT INTO T_TPS VALUES (75,76,    '->S_MBR[R44]');
INSERT INTO T_TPS VALUES (17,82,    '->CNST_CIP[R1506]->CNST_CSP[R1506]');
INSERT INTO T_TPS VALUES (82,83,    '->CNST_SYC[R1504]->CNST_LFSC[R1502]->CNST_LSC[R1503]');
INSERT INTO T_TPS VALUES (2, 16,   '->S_FPK[R29]');
INSERT INTO T_TPS VALUES (16, 16,   '->S_FPIP[R30]->S_FPK[R32]');
INSERT INTO T_TPS VALUES (16, 3,   '->S_SYNC[R31]');
INSERT INTO T_TPS VALUES (3, 4, '->S_SPARM[R24]');
INSERT INTO T_TPS VALUES (2, 18,	'->S_EEPK[R36]');
INSERT INTO T_TPS VALUES (18,18,    '->S_EEPIP[R34]->S_EEPK[R35]');
INSERT INTO T_TPS VALUES (18,20,	'->S_EE[R33]');
INSERT INTO T_TPS VALUES (20,21,	'->S_BRG[R19]');
INSERT INTO T_TPS VALUES (21,22,	'->S_BPARM[R21]');
INSERT INTO T_TPS VALUES (2, 5, '->S_SS[R43]');
INSERT INTO T_TPS VALUES (2,55, '->A_A[R1100]');
INSERT INTO T_TPS VALUES ( 5, 5,    '->S_SIS[R41]->S_SS[R42]');

INSERT INTO T_TPS VALUES (31, 33, '->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]');
INSERT INTO T_TPS VALUES (31, 51, '->SQ_P[R930]->MSG_M[R1007]->MSG_AM[R1018]');
INSERT INTO T_TPS VALUES (31, 48, '->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]');
INSERT INTO T_TPS VALUES (31, 33, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]');
INSERT INTO T_TPS VALUES (31, 51, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_AM[R1018]');
INSERT INTO T_TPS VALUES (31, 48, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]');

INSERT INTO T_TPS VALUES (80, 33, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]');
INSERT INTO T_TPS VALUES (80, 51, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_AM[R1018]');
INSERT INTO T_TPS VALUES (80, 48, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]');
INSERT INTO T_TPS VALUES (80, 33, '->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]');
INSERT INTO T_TPS VALUES (80, 51, '->SQ_P[R930]->MSG_M[R1007]->MSG_AM[R1018]');
INSERT INTO T_TPS VALUES (80, 48, '->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]');

INSERT INTO T_TPS VALUES (37, 33, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]');
INSERT INTO T_TPS VALUES (37, 51, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_AM[R1018]');
INSERT INTO T_TPS VALUES (37, 48, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]');
INSERT INTO T_TPS VALUES (37, 33, '->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]');
INSERT INTO T_TPS VALUES (37, 51, '->SQ_P[R930]->MSG_M[R1007]->MSG_AM[R1018]');
INSERT INTO T_TPS VALUES (37, 48, '->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]');

INSERT INTO T_TPS VALUES (50, 33, '->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]');
INSERT INTO T_TPS VALUES (50, 51, '->SQ_P[R930]->MSG_M[R1007]->MSG_AM[R1018]');
INSERT INTO T_TPS VALUES (50, 48, '->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]');
INSERT INTO T_TPS VALUES (50, 33, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]');
INSERT INTO T_TPS VALUES (50, 51, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_AM[R1018]');
INSERT INTO T_TPS VALUES (50, 48, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]');

INSERT INTO T_TPS VALUES (38, 33, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]');
INSERT INTO T_TPS VALUES (38, 48, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]');
INSERT INTO T_TPS VALUES (38, 33, '->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]');
INSERT INTO T_TPS VALUES (38, 48, '->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]');

INSERT INTO T_TPS VALUES (39, 33, '->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]');
INSERT INTO T_TPS VALUES (39, 48, '->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]');
INSERT INTO T_TPS VALUES (39, 33, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]');
INSERT INTO T_TPS VALUES (39, 48, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]');

INSERT INTO T_TPS VALUES (84, 33, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]');
INSERT INTO T_TPS VALUES (84, 48, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]');
INSERT INTO T_TPS VALUES (84, 33, '->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]');
INSERT INTO T_TPS VALUES (84, 48, '->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]');

INSERT INTO T_TPS VALUES (2, 30, '->SQ_S[R913]');
INSERT INTO T_TPS VALUES (5, 30, '->SQ_S[R914]');
INSERT INTO T_TPS VALUES (30, 30, '->SQ_SIS[R911]->SQ_S[R928]');
INSERT INTO T_TPS VALUES (30, 31, '->SQ_P[R929]->SQ_CIP[R930]');
INSERT INTO T_TPS VALUES (33, 34, '->MSG_ISM[R1020]->MSG_SM[R1020]->MSG_M[R1018]->MSG_A[R1000]');
INSERT INTO T_TPS VALUES (51, 34, '->MSG_IAM[R1019]->MSG_AM[R1019]->MSG_M[R1018]->MSG_A[R1000]');
INSERT INTO T_TPS VALUES (48, 34, '->MSG_M[R1018]->MSG_A[R1000]');
INSERT INTO T_TPS VALUES (33, 40, '->MSG_O[R1020]->MSG_SM[R1020]->MSG_M[R1018]->MSG_A[R1001]');
INSERT INTO T_TPS VALUES (33, 36, '->MSG_F[R1020]->MSG_SM[R1020]->MSG_M[R1018]->MSG_A[R1001]');
INSERT INTO T_TPS VALUES (33, 41, '->MSG_B[R1020]->MSG_SM[R1020]->MSG_M[R1018]->MSG_A[R1001]');
INSERT INTO T_TPS VALUES (51, 35, '->MSG_E[R1019]->MSG_AM[R1019]->MSG_M[R1018]->MSG_A[R1001]');
INSERT INTO T_TPS VALUES (30, 37, '->SQ_P[R929]->SQ_CP[R930]');
INSERT INTO T_TPS VALUES (30, 80, '->SQ_P[R929]->SQ_COP[R930]');
INSERT INTO T_TPS VALUES (30, 38, '->SQ_P[R929]->SQ_EEP[R930]');
INSERT INTO T_TPS VALUES (30, 39, '->SQ_P[R929]->SQ_FPP[R930]');
INSERT INTO T_TPS VALUES (30, 50, '->SQ_P[R929]->SQ_AP[R930]');
INSERT INTO T_TPS VALUES (31, 32, '->SQ_AV[R936]->SQ_IAV[R948]->SQ_AV[R948]');
INSERT INTO T_TPS VALUES (31, 47, '->SQ_AV[R937]->SQ_FAV[R948]->SQ_AV[R948]');
INSERT INTO T_TPS VALUES (37, 46, '->SQ_CPA[R935]->SQ_IA[R947]->SQ_CPA[R947]');
INSERT INTO T_TPS VALUES (5, 6, '->O_OBJ[R2]');
INSERT INTO T_TPS VALUES (5,55, '->A_A[R1108]');
INSERT INTO T_TPS VALUES (6, 7,    '->O_ATTR[R102]');
INSERT INTO T_TPS VALUES (6, 10, '->O_TFR[R115]');
INSERT INTO T_TPS VALUES (10, 8, '->O_TPARM[R117]');
INSERT INTO T_TPS VALUES (6, 25,    '->SM_ISM[R518]');
INSERT INTO T_TPS VALUES (25, 11,   '->SM_SM[R517]->SM_STATE[R501]');
INSERT INTO T_TPS VALUES (25, 12,   '->SM_SM[R517]->SM_EVT[R502]->SM_SEVT[R525]->SM_LEVT[R526]->SM_SEVT[R526]->SM_EVT[R525]');
INSERT INTO T_TPS VALUES (25, 27,   '->SM_SM[R517]->SM_EVT[R502]->SM_PEVT[R525]->SM_EVT[R525]');
INSERT INTO T_TPS VALUES (25, 13,   '->SM_SM[R517]->SM_EVT[R502]->SM_SEVT[R525]->SM_NLEVT[R526]');
INSERT INTO T_TPS VALUES (12, 29,   '->SM_SUPDT[R520]->SM_SDI[R522]');
INSERT INTO T_TPS VALUES (6, 15,    '->SM_ASM[R519]');
INSERT INTO T_TPS VALUES (15, 11,   '->SM_SM[R517]->SM_STATE[R501]');
INSERT INTO T_TPS VALUES (15, 12,   '->SM_SM[R517]->SM_EVT[R502]');
INSERT INTO T_TPS VALUES (23,24,    '->S_ENUM[R27]');
INSERT INTO T_TPS VALUES (12,14,    '->SM_EVTDI[R532]');
INSERT INTO T_TPS VALUES (2, 52, '->COMM_COMM[R1132]');
INSERT INTO T_TPS VALUES (5, 52, '->COMM_COMM[R1131]');
INSERT INTO T_TPS VALUES (52, 52, '->COMM_CIC[R1130]->COMM_COMM[R1129]');
INSERT INTO T_TPS VALUES (52, 31, '->COMM_PIC[R1126]->SQ_P[R1126]->SQ_CIP[R930]');
INSERT INTO T_TPS VALUES (52, 37, '->COMM_PIC[R1126]->SQ_P[R1126]->SQ_CP[R930]');
INSERT INTO T_TPS VALUES (52, 38, '->COMM_PIC[R1126]->SQ_P[R1126]->SQ_EEP[R930]');
INSERT INTO T_TPS VALUES (52, 39, '->COMM_PIC[R1126]->SQ_P[R1126]->SQ_FPP[R930]');
INSERT INTO T_TPS VALUES (52, 50, '->COMM_PIC[R1126]->SQ_P[R1126]->SQ_AP[R930]');
INSERT INTO T_TPS VALUES (52, 80, '->COMM_PIC[R1126]->SQ_P[R1126]->SQ_COP[R930]');
INSERT INTO T_TPS VALUES (52, 33, '->COMM_MIC[R1135]->MSG_M[R1135]->MSG_SM[R1018]');
INSERT INTO T_TPS VALUES (52, 51, '->COMM_MIC[R1135]->MSG_M[R1135]->MSG_AM[R1018]');
INSERT INTO T_TPS VALUES (52, 48, '->COMM_MIC[R1135]->MSG_M[R1135]->MSG_R[R1018]');
INSERT INTO T_TPS VALUES (2, 53, '->UC_UCC[R1201]');
INSERT INTO T_TPS VALUES (5, 53, '->UC_UCC[R1202]');
INSERT INTO T_TPS VALUES (53, 53, '->UC_UIU[R1208]->UC_UCC[R1209]');
INSERT INTO T_TPS VALUES (53, 50, '->UC_PIUC[R1203]->SQ_P[R1203]->SQ_AP[R930]');
INSERT INTO T_TPS VALUES (53, 54, '->UC_PIUC[R1203]->SQ_P[R1203]->IA_UCP[R930]');
INSERT INTO T_TPS VALUES (55,55,    '->A_AIA[R1109]->A_A[R1110]');
INSERT INTO T_TPS VALUES (55,56, '->A_N[R1101]->A_ACT[R1105]->A_GA[R1107]');
INSERT INTO T_TPS VALUES (55,57, '->A_N[R1101]->A_OBJ[R1105]');
INSERT INTO T_TPS VALUES (55,58, '->A_N[R1101]->A_ACT[R1105]->A_AE[R1107]->A_AEA[R1112]');
INSERT INTO T_TPS VALUES (55,59, '->A_N[R1101]->A_ACT[R1105]->A_SS[R1107]');
INSERT INTO T_TPS VALUES (55,60, '->A_N[R1101]->A_ACT[R1105]->A_AE[R1107]->A_ATE[R1112]');
INSERT INTO T_TPS VALUES (1,61, '->IP_IP[R4302]');
INSERT INTO T_TPS VALUES (61,61, '->IP_IPINIP[R4300]->IP_IP[R4301]');
INSERT INTO T_TPS VALUES (61,62, '->C_I[R4303]');
INSERT INTO T_TPS VALUES (62,73, '->C_EP[R4003]->C_IO[R4004]');
INSERT INTO T_TPS VALUES (62,63, '->C_EP[R4003]->C_AS[R4004]');
INSERT INTO T_TPS VALUES (63,64, '->C_EP[R4004]->C_PP[R4006]');
INSERT INTO T_TPS VALUES (73,64, '->C_EP[R4004]->C_PP[R4006]');
INSERT INTO T_TPS VALUES (1,65, '->CP_CP[R4602]');
INSERT INTO T_TPS VALUES (65,61, '->IP_IP[R4607]');
INSERT INTO T_TPS VALUES (65,65, '->CP_CPINP[R4600]->CP_CP[R4601]');
INSERT INTO T_TPS VALUES (65,66, '->C_C[R4604]');
INSERT INTO T_TPS VALUES (65, 30, '->SQ_S[R951]');
INSERT INTO T_TPS VALUES (65, 53, '->UC_UCC[R1212]');
INSERT INTO T_TPS VALUES (65, 52, '->COMM_COMM[R1137]');
INSERT INTO T_TPS VALUES (65, 55, '->A_A[R1114]');
INSERT INTO T_TPS VALUES (66,66, '->CN_CIC[R4202]->C_C[R4203]');
INSERT INTO T_TPS VALUES (66,61, '->IP_IP[R4206]');
INSERT INTO T_TPS VALUES (66, 30, '->SQ_S[R952]');
INSERT INTO T_TPS VALUES (66, 53, '->UC_UCC[R1213]');
INSERT INTO T_TPS VALUES (66, 52, '->COMM_COMM[R1138]');
INSERT INTO T_TPS VALUES (66, 55, '->A_A[R1115]');
INSERT INTO T_TPS VALUES (65,74, '->CL_IC[R4605]');
INSERT INTO T_TPS VALUES (66,77, '->C_PO[R4010]');
INSERT INTO T_TPS VALUES (67,70, '->SPR_PEP[R4501]->SPR_PO[R4503]');
INSERT INTO T_TPS VALUES (67,72, '->SPR_PEP[R4501]->SPR_PS[R4503]');
INSERT INTO T_TPS VALUES (68,69, '->SPR_REP[R4500]->SPR_RO[R4502]');
INSERT INTO T_TPS VALUES (68,71, '->SPR_REP[R4500]->SPR_RS[R4502]');
INSERT INTO T_TPS VALUES (70,64, '->SPR_PEP[R4503]->C_EP[R4501]->C_PP[R4006]');
INSERT INTO T_TPS VALUES (72,64, '->SPR_PEP[R4503]->C_EP[R4501]->C_PP[R4006]');
INSERT INTO T_TPS VALUES (69,64, '->SPR_REP[R4502]->C_EP[R4500]->C_PP[R4006]');
INSERT INTO T_TPS VALUES (71,64, '->SPR_REP[R4502]->C_EP[R4500]->C_PP[R4006]');
INSERT INTO T_TPS VALUES (66, 17,   '->CN_DC[R4204]->S_DOM[R4204]->S_DPK[R40]', 'first');
INSERT INTO T_TPS VALUES (66, 16,   '->CN_DC[R4204]->S_DOM[R4204]->S_FPK[R29]', 'first');
INSERT INTO T_TPS VALUES (66, 18, 	'->CN_DC[R4204]->S_DOM[R4204]->S_EEPK[R36]', 'first');
INSERT INTO T_TPS VALUES (66, 5,    '->CN_DC[R4204]->S_DOM[R4204]->S_SID[R43]->S_SS[R43]', 'first');
INSERT INTO T_TPS VALUES (66,55,    '->CN_DC[R4204]->S_DOM[R4204]->A_A[R1100]', 'first');
INSERT INTO T_TPS VALUES (66, 30,   '->CN_DC[R4204]->S_DOM[R4204]->SQ_S[R913]', 'first');
INSERT INTO T_TPS VALUES (66, 52,   '->CN_DC[R4204]->S_DOM[R4204]->COMM_COMM[R1132]', 'first');
INSERT INTO T_TPS VALUES (66, 53,   '->CN_DC[R4204]->S_DOM[R4204]->UC_UCC[R1201]', 'first');
INSERT INTO T_TPS VALUES (1,17, '->SLD_SDP[R4400]->S_DPK[R4400]');
INSERT INTO T_TPS VALUES (77,67, '->C_IR[R4016]->C_P[R4009]');
INSERT INTO T_TPS VALUES (77,68, '->C_IR[R4016]->C_R[R4009]');
INSERT INTO T_TPS VALUES (66,74, '->CL_IC[R4205]');
INSERT INTO T_TPS VALUES (74,85, '->CL_POR[R4707]');
INSERT INTO T_TPS VALUES (85,78, '->CL_IIR[R4708]->CL_IP[R4703]');
INSERT INTO T_TPS VALUES (85,79, '->CL_IIR[R4708]->CL_IR[R4703]');
INSERT INTO T_TPS VALUES (78,70, '->CL_IIR[R4703]->C_IR[R4701]->C_P[R4009]->SPR_PEP[R4501]->SPR_PO[R4503]');
INSERT INTO T_TPS VALUES (78,72, '->CL_IIR[R4703]->C_IR[R4701]->C_P[R4009]->SPR_PEP[R4501]->SPR_PS[R4503]');
INSERT INTO T_TPS VALUES (79,69, '->CL_IIR[R4703]->C_IR[R4701]->C_R[R4009]->SPR_REP[R4500]->SPR_RO[R4502]');
INSERT INTO T_TPS VALUES (79,71, '->CL_IIR[R4703]->C_IR[R4701]->C_R[R4009]->SPR_REP[R4500]->SPR_RS[R4502]');
