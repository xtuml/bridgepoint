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
INSERT INTO T_TNS VALUES (3,  '',       'S_SYNC',  '', 'Name', '', 'Functions', false);
INSERT INTO T_TNS VALUES (4,  '', 'S_SPARM', '', 'Name', '', 'Function Parameters', true);
INSERT INTO T_TNS VALUES (19, '',          'S_UDT',   '', 'Name', '->S_DT[R17]', 'User Defined Data Types', false);
INSERT INTO T_TNS VALUES (20, '',      'S_EE',    '', 'Label', '', 'External Entities', false);
INSERT INTO T_TNS VALUES (21, '',       'S_BRG',   '', 'Name', '', 'Bridge Operations', false);
INSERT INTO T_TNS VALUES (22, '', 'S_BPARM', '', 'Name', '', 'Bridge Parameters', true);
INSERT INTO T_TNS VALUES (23, '',       'S_EDT',   '', 'Name', '->S_DT[R17]', 'Enumerations', false);
INSERT INTO T_TNS VALUES (24, '',        'S_ENUM',  '', 'Name', '', 'Enumerators', true);
INSERT INTO T_TNS VALUES (28, '',      'S_CDT',   '', 'Name', '->S_DT[R17]', 'Core Data Types', false);
INSERT INTO T_TNS VALUES (29, '',  'SM_SDI',   '', 'Name', '->SM_EVTDI[R522]', 'Event Data Items', false);
INSERT INTO T_TNS VALUES (31, '', 'SQ_CIP', '', 'Label', '', 'Instances', false);
INSERT INTO T_TNS VALUES (32, '', 'SQ_AV', '', 'Label', '', 'Informal Instance Attributes', false);
INSERT INTO T_TNS VALUES (34, '', 'MSG_A', '', 'Label', '', 'Informal Message Arguments', false);
INSERT INTO T_TNS VALUES (35, '', 'MSG_A', '', 'Label', '', 'Event Message Arguments', false);
INSERT INTO T_TNS VALUES (36, '', 'MSG_A', '', 'Label', '', 'Function Message Arguments', false);
INSERT INTO T_TNS VALUES (40, '', 'MSG_A', '', 'Label', '', 'Class Message Arguments', false);
INSERT INTO T_TNS VALUES (41, '', 'MSG_A', '', 'Label', '', 'Bridge Message Arguments', false);
INSERT INTO T_TNS VALUES (37, '', 'SQ_CP', '', 'Label', '', 'Imported Classes', false);
INSERT INTO T_TNS VALUES (38, '', 'SQ_EEP', '', 'Label', '', 'Imported External Entities', false);
INSERT INTO T_TNS VALUES (84, '', 'SQ_PP', '', 'Label', '',  'Imported Packages', false);
INSERT INTO T_TNS VALUES (46, '', 'SQ_CPA', '', 'Name', '', 'Informal Class Attributes', false);
INSERT INTO T_TNS VALUES (47, '', 'SQ_AV', '', 'Label', '', 'Formal Instance Attributes', false);
INSERT INTO T_TNS VALUES (50, '', 'SQ_AP', '', 'Name', '', 'Actors', false);
INSERT INTO T_TNS VALUES (33, '', 'MSG_SM', '', 'Label', '', 'Incoming Synchronous Messages', false);
INSERT INTO T_TNS VALUES (51, '', 'MSG_AM', '', 'Label', '', 'Incoming Asynchronous Messages', false);
INSERT INTO T_TNS VALUES (48, '', 'MSG_R', '', 'Name', '', 'Incoming Return Messages', false);
INSERT INTO T_TNS VALUES (54, '', 'IA_UCP', '', 'Name', '', 'Use Cases', false);
INSERT INTO T_TNS VALUES (56, '', 'A_GA',  '', 'Name', '', 'Actions', false);
INSERT INTO T_TNS VALUES (57, '', 'A_OBJ',  '', 'Name', '', 'ObjectNodes', false);
INSERT INTO T_TNS VALUES (58, '', 'A_AEA',  '', 'Name', '', 'Accept Event Actions', false);
INSERT INTO T_TNS VALUES (59, '', 'A_SS',  '', 'Name', '', 'Send Signal Actions', false);
INSERT INTO T_TNS VALUES (60, '', 'A_ATE',  '', 'Name', '', 'Accept Time Event Actions', false);
INSERT INTO T_TNS VALUES (62, '', 'C_I',  '', 'Name', '', 'Interfaces', false);
INSERT INTO T_TNS VALUES (63, '', 'C_AS',  '', 'Name', '', 'Signals', true);
INSERT INTO T_TNS VALUES (64, '', 'C_PP',  '', 'Name', '', 'Parameters', true, true);
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
INSERT INTO T_TNS VALUES (81, '', 'EP_PKG', '', 'Label', '', 'Packages', false);
INSERT INTO T_TNS VALUES (82, '', 'CNST_CSP', '', 'Name', '', 'Constant Specifications', false);
INSERT INTO T_TNS VALUES (83, '', 'CNST_LSC', '', 'Name', '->CNST_LFSC[R1503]->CNST_SYC[R1502]', 'Literal Symbolic Constants', true);
INSERT INTO T_TNS VALUES (85, '', 'CL_POR',  '', 'Name', '', 'Port Reference', false);
INSERT INTO T_TNS VALUES (86, '', 'S_EXP', '', 'Name', '', 'Exceptions', false);
INSERT INTO T_TNS VALUES (87, '', 'D_DEPL', '', 'Name', '', 'Deployments', false);
INSERT INTO T_TNS VALUES (88, '', 'D_TERM', '', 'Name', '', 'Terminators', true);
INSERT INTO T_TNS VALUES (89, '', 'D_TSVC', '', 'Name', '', 'Terminator Services', true);
INSERT INTO T_TNS VALUES (90, '', 'D_TSPARM', '', 'Name', '', 'Terminator Service Parameters', false);

INSERT INTO T_TPS VALUES (1, 81, '->EP_PKG[R1401]');
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

INSERT INTO T_TPS VALUES (75,76,    '->S_MBR[R44]');
INSERT INTO T_TPS VALUES (82,83,    '->CNST_SYC[R1504]->CNST_LFSC[R1502]->CNST_LSC[R1503]');
INSERT INTO T_TPS VALUES (3, 4, '->S_SPARM[R24]');
INSERT INTO T_TPS VALUES (20,21,	'->S_BRG[R19]');
INSERT INTO T_TPS VALUES (21,22,	'->S_BPARM[R21]');

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

INSERT INTO T_TPS VALUES (84, 33, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]');
INSERT INTO T_TPS VALUES (84, 48, '->SQ_P[R930]->SQ_LS[R940]->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]');
INSERT INTO T_TPS VALUES (84, 33, '->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]');
INSERT INTO T_TPS VALUES (84, 48, '->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]');

INSERT INTO T_TPS VALUES (33, 34, '->MSG_ISM[R1020]->MSG_SM[R1020]->MSG_M[R1018]->MSG_A[R1000]');
INSERT INTO T_TPS VALUES (51, 34, '->MSG_IAM[R1019]->MSG_AM[R1019]->MSG_M[R1018]->MSG_A[R1000]');
INSERT INTO T_TPS VALUES (48, 34, '->MSG_M[R1018]->MSG_A[R1000]');
INSERT INTO T_TPS VALUES (33, 40, '->MSG_O[R1020]->MSG_SM[R1020]->MSG_M[R1018]->MSG_A[R1001]');
INSERT INTO T_TPS VALUES (33, 36, '->MSG_F[R1020]->MSG_SM[R1020]->MSG_M[R1018]->MSG_A[R1001]');
INSERT INTO T_TPS VALUES (33, 41, '->MSG_B[R1020]->MSG_SM[R1020]->MSG_M[R1018]->MSG_A[R1001]');
INSERT INTO T_TPS VALUES (51, 35, '->MSG_E[R1019]->MSG_AM[R1019]->MSG_M[R1018]->MSG_A[R1001]');
INSERT INTO T_TPS VALUES (31, 32, '->SQ_AV[R936]->SQ_IAV[R948]->SQ_AV[R948]');
INSERT INTO T_TPS VALUES (31, 47, '->SQ_AV[R937]->SQ_FAV[R948]->SQ_AV[R948]');
INSERT INTO T_TPS VALUES (37, 46, '->SQ_CPA[R935]->SQ_IA[R947]->SQ_CPA[R947]');
INSERT INTO T_TPS VALUES (6, 7,    '->O_ATTR[R102]');
INSERT INTO T_TPS VALUES (6, 10, '->O_TFR[R115]');
INSERT INTO T_TPS VALUES (10, 8, '->O_TPARM[R117]');
INSERT INTO T_TPS VALUES (6, 25,    '->SM_ISM[R518]');
INSERT INTO T_TPS VALUES (25, 11,   '->SM_SM[R517]->SM_STATE[R501]');
INSERT INTO T_TPS VALUES (25, 12,   '->SM_SM[R517]->SM_EVT[R502]->SM_SEVT[R525]->SM_LEVT[R526]->SM_SEVT[R526]->SM_EVT[R525]');
INSERT INTO T_TPS VALUES (25, 27,   '->SM_SM[R517]->SM_EVT[R502]->SM_PEVT[R525]->SM_EVT[R525]');
INSERT INTO T_TPS VALUES (25, 13,   '->SM_SM[R517]->SM_EVT[R502]->SM_SEVT[R525]->SM_NLEVT[R526]', '', 'Hasnonlocalpoly()');
INSERT INTO T_TPS VALUES (12, 29,   '->SM_SUPDT[R520]->SM_SDI[R522]');
INSERT INTO T_TPS VALUES (6, 15,    '->SM_ASM[R519]');
INSERT INTO T_TPS VALUES (15, 11,   '->SM_SM[R517]->SM_STATE[R501]');
INSERT INTO T_TPS VALUES (15, 12,   '->SM_SM[R517]->SM_EVT[R502]');
INSERT INTO T_TPS VALUES (23,24,    '->S_ENUM[R27]');
INSERT INTO T_TPS VALUES (12,14,    '->SM_EVTDI[R532]');
INSERT INTO T_TPS VALUES (62,73, '->C_EP[R4003]->C_IO[R4004]');
INSERT INTO T_TPS VALUES (62,63, '->C_EP[R4003]->C_AS[R4004]');
INSERT INTO T_TPS VALUES (63,64, '->C_EP[R4004]->C_PP[R4006]');
INSERT INTO T_TPS VALUES (73,64, '->C_EP[R4004]->C_PP[R4006]');
INSERT INTO T_TPS VALUES (66,77, '->C_PO[R4010]');
INSERT INTO T_TPS VALUES (67,70, '->SPR_PEP[R4501]->SPR_PO[R4503]');
INSERT INTO T_TPS VALUES (67,72, '->SPR_PEP[R4501]->SPR_PS[R4503]');
INSERT INTO T_TPS VALUES (68,69, '->SPR_REP[R4500]->SPR_RO[R4502]');
INSERT INTO T_TPS VALUES (68,71, '->SPR_REP[R4500]->SPR_RS[R4502]');
INSERT INTO T_TPS VALUES (70,64, '->SPR_PEP[R4503]->C_EP[R4501]->C_PP[R4006]');
INSERT INTO T_TPS VALUES (72,64, '->SPR_PEP[R4503]->C_EP[R4501]->C_PP[R4006]');
INSERT INTO T_TPS VALUES (69,64, '->SPR_REP[R4502]->C_EP[R4500]->C_PP[R4006]');
INSERT INTO T_TPS VALUES (71,64, '->SPR_REP[R4502]->C_EP[R4500]->C_PP[R4006]');
INSERT INTO T_TPS VALUES (77,67, '->C_IR[R4016]->C_P[R4009]');
INSERT INTO T_TPS VALUES (77,68, '->C_IR[R4016]->C_R[R4009]');
INSERT INTO T_TPS VALUES (74,85, '->CL_POR[R4707]');
INSERT INTO T_TPS VALUES (85,78, '->CL_IIR[R4708]->CL_IP[R4703]');
INSERT INTO T_TPS VALUES (85,79, '->CL_IIR[R4708]->CL_IR[R4703]');
INSERT INTO T_TPS VALUES (78,70, '->CL_IIR[R4703]->C_IR[R4701]->C_P[R4009]->SPR_PEP[R4501]->SPR_PO[R4503]');
INSERT INTO T_TPS VALUES (78,72, '->CL_IIR[R4703]->C_IR[R4701]->C_P[R4009]->SPR_PEP[R4501]->SPR_PS[R4503]');
INSERT INTO T_TPS VALUES (79,69, '->CL_IIR[R4703]->C_IR[R4701]->C_R[R4009]->SPR_REP[R4500]->SPR_RO[R4502]');
INSERT INTO T_TPS VALUES (79,71, '->CL_IIR[R4703]->C_IR[R4701]->C_R[R4009]->SPR_REP[R4500]->SPR_RS[R4502]');
INSERT INTO T_TPS VALUES (81,86, '->PE_PE[R8000]->S_EXP[R8001]');
INSERT INTO T_TPS VALUES (81,87, '->PE_PE[R8000]->D_DEPL[R8001]');
INSERT INTO T_TPS VALUES (87,88, '->D_TERM[R1650]');
INSERT INTO T_TPS VALUES (88,89, '->D_TSVC[R1651]');
INSERT INTO T_TPS VALUES (89,90, '->D_TSPARM[R1652]');
