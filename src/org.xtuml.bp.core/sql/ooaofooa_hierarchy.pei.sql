--======================================================================
--
-- File:      $RCSfile: ooaofooa_hierarchy.pei.sql,v $
-- Version:   $Revision: 1.67.16.2 $
-- Modified:  $Date: 2013/07/23 15:06:41 $
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
INSERT INTO T_TNS VALUES (1,  'System.gif',                     'S_SYS',   '', 'Name', '',                 'System',						-1, '');
INSERT INTO T_TNS VALUES (6,  'Class.gif',                      'O_OBJ',   '', 'Name', '',                 'Class', 						-1, '');
INSERT INTO T_TNS VALUES (3,  'Function.gif',                   'S_SYNC',  '', 'Name', '',                 'Function', 					-1, '');
INSERT INTO T_TNS VALUES (4,  'FunctionParameter.gif',          'S_SPARM', '', 'Name', '',                 'Function Parameter',        54, 'succeeds');
INSERT INTO T_TNS VALUES (7,  'InstanceBasedOperation.gif',                  'O_TFR',   '', 'Name', '',                 'Operation',                 125, 'succeeds');
INSERT INTO T_TNS VALUES (8,  'OperationParameter.gif',         'O_TPARM', '', 'Name', '',                 'Operation Parameter',       124, 'succeeds');
INSERT INTO T_TNS VALUES (9,  'InstanceBasedOperation.gif', 'I_IBO', '', 'Name', '->O_TFR[R2979]', 'Instance Bound Operations', -1, '');
INSERT INTO T_TNS VALUES (10, 'InstanceStateChart.gif',         'SM_ISM',  'Instance State Chart', '', '', 'Instance State Chart', 		-1, '');
INSERT INTO T_TNS VALUES (15, 'ClassStateChart.gif',            'SM_ASM',  'Class State Chart', '', '',    'Class State Chart', 			-1, '');
INSERT INTO T_TNS VALUES (11, 'State.gif',                      'SM_STATE','', 'Name', '',                 'State', 						-1, '');
INSERT INTO T_TNS VALUES (12, 'Event.gif',                      'SM_EVT',  '', 'Mning','',                 'Event', 						-1, '');
INSERT INTO T_TNS VALUES (14, 'EventData.gif',                  'SM_EVTDI','', 'Name', '',                 'Event Data',                533, 'succeeds');
INSERT INTO T_TNS VALUES (16, 'Attribute.gif',                  'O_ATTR',  '', 'Name', '',                 'Attribute', 				103, 'succeeds');
INSERT INTO T_TNS VALUES (17, 'DataType.gif',                       'S_DT',    '', '', '',                 'Data Type', 				-1, '');
INSERT INTO T_TNS VALUES (19, 'Enumerator.gif',                 'S_ENUM',  '', 'Name', '',                 'Enumerator', 				   56, 'succeeds');
INSERT INTO T_TNS VALUES (27, 'ExternalEntity.gif',             'S_EE',    '', 'Label', '',                 'External Entity', 			-1, '');
INSERT INTO T_TNS VALUES (28, 'Bridge.gif',                     'S_BRG',   '', 'Name', '',                 'Bridge', 						-1, '');
INSERT INTO T_TNS VALUES (29, 'BridgeParameter.gif',            'S_BPARM', '', 'Name', '',                 'Bridge Parameter',        55, 'succeeds');
INSERT INTO T_TNS VALUES (30, 'Association.gif',                'R_REL',   '', '', '',                     'Association', 					-1, '');
INSERT INTO T_TNS VALUES (31, 'ImportedClass.gif',              'O_IOBJ',  '', '', '',                     'Imported Class', 				-1, '');
INSERT INTO T_TNS VALUES (32, 'Identifier.gif',                 'O_OIDA',    '', '', '',                     'Identifier', 					-1, '');
INSERT INTO T_TNS VALUES (33, 'DerivedAttribute.gif',           'O_DBATTR','', '', '',                     'Derived Attribute', 			-1, '');
INSERT INTO T_TNS VALUES (34, 'Reference.gif',                  'O_REF',   '', '', '',                     'Reference', 					112, 'succeeds');
INSERT INTO T_TNS VALUES (37, 'AssociationParticipantEnd.gif', 'R_PART',  '', '', '',                     'Association Participant End', 	-1, '');
INSERT INTO T_TNS VALUES (38, 'AssociationFormalizerEnd.gif',  'R_FORM',  '', '', '',                     'Association Formalizer End', 	-1, '');
INSERT INTO T_TNS VALUES (39, 'AssociativeOneEnd.gif',         'R_AONE',  '', '', '',                     'Associative One End', 			-1, '');
INSERT INTO T_TNS VALUES (40, 'AssociativeOtherEnd.gif',       'R_AOTH',  '', '', '',                     'Associative Other End', 		-1, '');
INSERT INTO T_TNS VALUES (41, 'Associative.gif',                'R_ASSR',  '', '', '',                     'Associative', 					-1, '');
INSERT INTO T_TNS VALUES (42, 'Supertype.gif',                  'R_SUPER', '', '', '',                     'Supertype', 					-1, '');
INSERT INTO T_TNS VALUES (43, 'Subtype.gif',                    'R_SUB',   '', '', '',                     'Subtype', 						-1, '');
INSERT INTO T_TNS VALUES (44, 'DerivedOneEnd.gif',             'R_CONE',  '', '', '',                     'Derived One End', 				-1, '');
INSERT INTO T_TNS VALUES (45, 'DerivedOtherEnd.gif',           'R_COTH',  '', '', '',                     'Derived Other End', 			-1, '');
INSERT INTO T_TNS VALUES (46, 'StateAction.gif',                'SM_ACT',  '', '', '',                     'Action', 				-1, '');
INSERT INTO T_TNS VALUES (48, 'EventIgnored.gif',               'SM_EIGN', '', '', '',                     'Event Ignored', 				-1, '');
INSERT INTO T_TNS VALUES (49, 'EventCantHappen.gif',            'SM_CH',   '', '', '',                     'Event Cant Happen', 			-1, '');
INSERT INTO T_TNS VALUES (70, 'StateEventMatrixEntry.gif',      'SM_SEME', '', '', '',                     'State Event Matrix Entry', 	-1, '');
INSERT INTO T_TNS VALUES (74, 'UserDefinedDataType.gif',       'S_UDT',   '', 'Name', '->S_DT[R17]',      'User Defined Data Type', 		-1, '');
INSERT INTO T_TNS VALUES (75, 'CoreDataType.gif',               'S_CDT',   '', 'Name', '->S_DT[R17]',      'Core Data Type', 				-1, '');
INSERT INTO T_TNS VALUES (77, 'Enumeration.gif',                'S_EDT',   '', 'Name', '->S_DT[R17]',      'Enumeration', 					-1, '');
INSERT INTO T_TNS VALUES (79, 'DerivedAssociation.gif',         'R_COMP',  '', '', '',                     'Derived Association', 			-1, '');
INSERT INTO T_TNS VALUES (80, 'ReferentialAttribute.gif',       'O_RATTR',  '', '', '',                    'Referential Attribute', 		-1, '');
INSERT INTO T_TNS VALUES (81, 'NonLocalEvent.gif',              'SM_NLEVT',  '', 'Name', '',               'Inherited Event', 				-1, '');
INSERT INTO T_TNS VALUES (83, 'ExternalEntityEventData.gif',    'S_EEEDT',  '', '', '',                    'External Entity Event Data', 	-1, '');
INSERT INTO T_TNS VALUES (86, 'Instance.gif', 'SQ_CIP',  '', 'Name', '', 'Instances', -1, '');
INSERT INTO T_TNS VALUES (87, 'Attribute.gif', 'SQ_AV',  '', 'Label', '', 'Formal Instance Attribute Values', -1, '', 'InformalName');
INSERT INTO T_TNS VALUES (88, 'ImportedExternalEntity.gif', 'SQ_EEP',  '', 'Label', '', 'Imported External Entities', -1, '', 'InformalName');
INSERT INTO T_TNS VALUES (304,'Package.gif', 'SQ_PP',  '', 'Label', '', 'Imported Packages', -1, '', 'InformalName');
INSERT INTO T_TNS VALUES (90, 'ImportedClass.gif', 'SQ_CP',  '', 'Label', '', 'Imported Classes', -1, '', 'InformalName');
INSERT INTO T_TNS VALUES (91, 'Lifespan.gif', 'SQ_LS',  '', '', '', 'Lifespans', -1, '');
INSERT INTO T_TNS VALUES (92, 'Attribute.gif', 'SQ_CPA',  '', 'Name', '', 'Informal Attributes', -1, '');
INSERT INTO T_TNS VALUES (93, 'TimingMark.gif', 'SQ_TM',  '', 'Name', '', 'Timing Marks', -1, '');
INSERT INTO T_TNS VALUES (94, 'TimeSpan.gif', 'SQ_TS',  '', 'Name', '', 'Time Spans', -1, '');
INSERT INTO T_TNS VALUES (95, 'SynchronousMessage.gif', 'MSG_SM',  '', 'Label', '', 'Synchronous Messages', -1, '', 'InformalName');
INSERT INTO T_TNS VALUES (96, 'AsynchronousMessage.gif', 'MSG_AM',  '', 'Label', '', 'Asynchronous Messages', -1, '', 'InformalName');
INSERT INTO T_TNS VALUES (99, 'OperationParameter.gif', 'MSG_A',  '', 'Name', '', 'Informal Message Arguments', -1, '', 'InformalName');
INSERT INTO T_TNS VALUES (97, 'OperationParameter.gif', 'MSG_A',  '', 'Label', '', 'Message Arguments', -1, '', 'InformalName');
INSERT INTO T_TNS VALUES (98, 'Attribute.gif', 'SQ_AV',  '', 'Label', '', 'Informal Instance Attribute Values', -1, '', 'InformalName');
INSERT INTO T_TNS VALUES (101, 'Actor.gif', 'SQ_AP',  '', 'Name', '', 'Actors', -1, '');
INSERT INTO T_TNS VALUES (102, 'ReturnMessage.gif', 'MSG_R',  '', 'Name', '', 'Return Messages', -1, '');
INSERT INTO T_TNS VALUES (104, 'Association.gif', 'COMM_LNK',  '', '', '', 'Communication Links', -1, '');
INSERT INTO T_TNS VALUES (106, 'UseCase.gif', 'IA_UCP',  '', 'Name', '', 'Use Cases', -1, '');
INSERT INTO T_TNS VALUES (107, 'Association.gif', 'UC_BA',  '', '', '', 'Use Case Associations', -1, '');
INSERT INTO T_TNS VALUES (108, 'Generalization.gif', 'UC_G',  '', '', '', 'Use Case Generalizations', -1, '');
INSERT INTO T_TNS VALUES (109, 'Include.gif', 'UC_I',  '', '', '', 'Use Case Includes', -1, '');
INSERT INTO T_TNS VALUES (110, 'Extend.gif', 'UC_E',  '', '', '', 'Use Case Extends', -1, '');
INSERT INTO T_TNS VALUES (112, 'ForkJoinNode.gif', 'A_FJ', '', 'GuardCondition', '',   'ForkJoins', 			-1, '');
INSERT INTO T_TNS VALUES (113, 'InitialNode.gif', 'A_INI', '', '', '', 'InitialNodes', -1, '');
INSERT INTO T_TNS VALUES (114, 'ActivityEdge.gif', 'A_E', '', 'Guard', '', 'ActivityEdges', -1, '');
INSERT INTO T_TNS VALUES (115, 'ActivityFinalNode.gif', 'A_AF', '', '', '', 'ActivityFinalNodes', -1, '');
INSERT INTO T_TNS VALUES (116, 'FlowFinalNode.gif', 'A_FF', '', '', '', 'FlowFinalNodes', -1, '');
INSERT INTO T_TNS VALUES (117, 'GenericAction.gif', 'A_GA', '', 'Name', '', 'ActivityDiagramActions', -1, '');
INSERT INTO T_TNS VALUES (118, 'DecisionMergeNode.gif', 'A_DM', '', '', '', 'DecisionMergeNodes', -1, '');
INSERT INTO T_TNS VALUES (119, 'ObjectNode.gif', 'A_OBJ', '', 'Name', '', 'ObjectNodes', -1, '');
INSERT INTO T_TNS VALUES (120, 'AcceptEvent.gif',            'A_AEA',   '', 'Name', '', 'AcceptEventActions', -1, '');
INSERT INTO T_TNS VALUES (121, 'SendSignal.gif', 'A_SS', '', 'Name', '', 'SendSignalActions', -1, '');
INSERT INTO T_TNS VALUES (122, 'ActivityPartition.gif', 'A_AP', '', 'Name', '', 'ActivityPartitions', -1, '');
INSERT INTO T_TNS VALUES (123, 'AcceptTimeEvent.gif', 'A_ATE', '', 'Name', '', 'AcceptTimeEventActions', -1, '');
INSERT INTO T_TNS VALUES (125, 'Component.gif', 'C_C', '', 'Name', '', 'Components', -1, '');
INSERT INTO T_TNS VALUES (126, 'ImportedComponent.gif', 'CL_IC', '', '', '', 'Component References', -1, '');
INSERT INTO T_TNS VALUES (127, 'Provision.gif', 'C_P', '', 'Name', '', 'Provided Interfaces', -1, '');
INSERT INTO T_TNS VALUES (128, 'Requirement.gif', 'C_R', '', 'Name', '', 'Required Interfaces', -1, '');
INSERT INTO T_TNS VALUES (130, 'Interface.gif', 'C_I', '', 'Name', '', 'Interfaces', -1, '');
INSERT INTO T_TNS VALUES (131, 'InterfaceSignal.gif', 'C_AS', '', 'Name', '', 'Signals',        4020, 'succeeds');
INSERT INTO T_TNS VALUES (132, 'InterfaceOperation.gif', 'C_IO', '', 'Name', '', 'Operations',        4019, 'succeeds');
INSERT INTO T_TNS VALUES (133, 'PropertyParameter.gif', 'C_PP', '', 'Name', '', 'Parameters',        4021, 'succeeds');
INSERT INTO T_TNS VALUES (134, 'RequiredOperation.gif', 'SPR_RO', '', '', '', 'Required Interface Operations', -1, '');
INSERT INTO T_TNS VALUES (135, 'RequiredSignal.gif', 'SPR_RS', '', '', '', 'Required Interface Signals', -1, '');
INSERT INTO T_TNS VALUES (136, 'ProvidedOperation.gif', 'SPR_PO', '', '', '', 'Provided Interface Operations', -1, '');
INSERT INTO T_TNS VALUES (137, 'ProvidedSignal.gif', 'SPR_PS', '', '', '', 'Provided Interface Signals', -1, '');
INSERT INTO T_TNS VALUES (285, 'InstanceOfClass.gif',                  'I_INS',   '', 'Name', '',                 'Instances',                     -1, '');
INSERT INTO T_TNS VALUES (286, 'Attribute.gif',                  'I_AVL',   '', '', '',                 'Values',                        -1, '');
INSERT INTO T_TNS VALUES (287, 'Event.gif',                      'I_EVI',   '', '', '',                 'Pending Events',                -1, '');
INSERT INTO T_TNS VALUES (288, 'StructuredDataType.gif',         'S_SDT',   '', 'Name', '->S_DT[R17]',  'Structured Data Type',                -1, '');
INSERT INTO T_TNS VALUES (289, 'StructuredDataTypeMember.gif',   'S_MBR',   '', 'Name', '',                 'Structure Member',                46, 'succeeds');
INSERT INTO T_TNS VALUES (290, 'Attribute.gif',                  'RV_RVL',  '', 'Label', '',                 'RuntimeValues',                        -1, '');
INSERT INTO T_TNS VALUES (291, 'Port.gif',                  'C_PO',  '', 'Name', '',                 'Port',                        -1, '');
INSERT INTO T_TNS VALUES (292, 'Provision.gif',                  'CL_IP',  '', 'Name', '',                 'Imported Provided Interfaces',                        -1, '');
INSERT INTO T_TNS VALUES (293, 'Requirement.gif',                  'CL_IR',  '', 'Name', '',                 'Imported Required Interfaces',                        -1, '');
INSERT INTO T_TNS VALUES (294, 'Transition.gif',                  'SM_TXN',  '', 'Name', '',                 'Transitions',                        -1, '');
INSERT INTO T_TNS VALUES (295, 'InstanceOfComponent.gif',                  'I_EXE',   '', 'Label', '',                 'Component Instances',                     -1, '');
INSERT INTO T_TNS VALUES (296, 'Component.gif', 'SQ_COP',  '', 'Label', '', 'Component Participants', -1, '');
INSERT INTO T_TNS VALUES (297, 'Package.gif', 'EP_PKG',  '', 'Label', '', 'Packages', -1, '');
INSERT INTO T_TNS VALUES (298, 'Association.gif',                'I_LIP',   '', 'Label', '',                     'Association', 					-1, '');
INSERT INTO T_TNS VALUES (299, 'Class.gif',                  'CSME_CIE',   '', 'Label', '',                 'Classes Under Execution',                     -1, '');
INSERT INTO T_TNS VALUES (300, 'ConstantSpecification.gif',  'CNST_CSP',  '', 'InformalGroupName', '',      'Constant Specification',  -1, '');
INSERT INTO T_TNS VALUES (301, 'Enumerator.gif',             'CNST_LSC',  '', 'Name', '->CNST_LFSC[R1503]->CNST_SYC[R1502]', 'Literal Symbolic Constant', 1505, 'succeeds');
INSERT INTO T_TNS VALUES (302, 'Enumerator.gif',             'CNST_SYC',  '', '', '',                 'Symbolic Constant', 				-1, '');
INSERT INTO T_TNS VALUES (303, 'Satisfaction.gif',           'C_SF',  '', 'Label', '',                 'Satisfactions', 				-1, '');
INSERT INTO T_TNS VALUES (305, 'Attribute.gif',           'O_NBATTR','', '', '',                     'Non Derived Attribute', 			-1, '');
INSERT INTO T_TNS VALUES (306, 'Event.gif',           'SM_PEVT','', '', '',                     'Polymorphic Event', 			-1, '');
INSERT INTO T_TNS VALUES (307, 'ReferentialAttribute.gif',           'O_RTIDA','', '', '',                     'Referenced Identifier', 			-1, '');
INSERT INTO T_TNS VALUES (308, 'Delegation.gif',           'C_DG','', '', '',                     'Delegated Interfaces', 			-1, '');
INSERT INTO T_TNS VALUES (309, 'Event.gif',                      'SM_SEVT',  '', '','',                 'Event', 						-1, '');
INSERT INTO T_TNS VALUES (310, 'Attribute.gif',                      'MSG_ISM',  '', '','',                 'Informal Message', 						-1, '');
INSERT INTO T_TNS VALUES (311, 'Attribute.gif',                      'MSG_IAM',  '', '','',                 'Informal Message', 						-1, '');
INSERT INTO T_TNS VALUES (312, 'Function.gif',                      'MSG_F',  '', '','',                 'Formal Function', 						-1, '');
INSERT INTO T_TNS VALUES (313, 'Bridge.gif',                         'MSG_B',  '', '','',                 'Formal Bridge Operation', 						-1, '');
INSERT INTO T_TNS VALUES (314, 'InstanceBasedOperation.gif',                    'MSG_O',  '', '','',                 'Formal Operation', 						-1, '');
INSERT INTO T_TNS VALUES (315, 'InterfaceOperation.gif',       'MSG_IOP',  '', '','',                 'Formal Interface Operation', 						-1, '');
INSERT INTO T_TNS VALUES (316, 'Event.gif',                          'MSG_E',  '', '','',                 'Formal Event', 						-1, '');
INSERT INTO T_TNS VALUES (317, 'InterfaceSignal.gif',            'MSG_SIG',  '', '','',                 'Formal Signal', 						-1, '');
INSERT INTO T_TNS VALUES (318, 'InstanceBasedOperation.gif',            'MSG_IA',  '', '','',                 'Informal Argument', 						-1, '');
INSERT INTO T_TNS VALUES (319, 'Transition.gif',            'SM_CRTXN',  '', '','',                 'Creation Transitions', 						-1, '');
INSERT INTO T_TNS VALUES (382, 'Port.gif',                  'CL_POR',  '', 'Name', '',                 'Port Reference',                        -1, '');
INSERT INTO T_TNS VALUES (383, 'Exception.gif', 'S_EXP', '', 'Name', '', 'Exception', -1, '');
INSERT INTO T_TNS VALUES (384, 'Range.gif', 'S_RANGE', '', '', '', 'Range', -1, '');
INSERT INTO T_TNS VALUES (385, 'Deployment.gif', 'D_DEPL', '', 'Name', '', 'Deployment', -1, '');
INSERT INTO T_TNS VALUES (386, 'Terminator.gif', 'D_TERM', '', 'Name', '', 'Terminator', -1, '');
INSERT INTO T_TNS VALUES (387, 'TerminatorService.gif', 'D_TSVC', '', 'Name', '', 'Terminator Service', -1, ''); -- Disabled until infrastructe can handle 1661, 'succeeds'
INSERT INTO T_TNS VALUES (388, 'TerminatorServiceParameter.gif', 'D_TSPARM', '', 'Name', '', 'Terminator Service Parameter', 1654, 'succeeds');
INSERT INTO T_TNS VALUES (389, 'InstanceBasedOperation.gif', 'O_DEF', '', '', '', 'Deferred Operation', -1, '');

INSERT INTO T_TPS VALUES (7, 3,  17, '->S_DT[R25]', 'Return Type', true);
INSERT INTO T_TPS VALUES (8, 3,  4,  '->S_SPARM[R24]', '', false);
INSERT INTO T_TPS VALUES (9, 4,  17, '->S_DT[R26]', 'Type', true);
INSERT INTO T_TPS VALUES (14, 6,  16, '->O_ATTR[R102]', '', false);
INSERT INTO T_TPS VALUES (15, 6,  7,  '->O_TFR[R115]', '', false);
INSERT INTO T_TPS VALUES (16, 6,  10, '->SM_ISM[R518]', '', false);
INSERT INTO T_TPS VALUES (17, 6,  15, '->SM_ASM[R519]', '', false);
INSERT INTO T_TPS VALUES (18, 7,  17, '->S_DT[R116]', 'Return Type', true);
INSERT INTO T_TPS VALUES (19, 7,  8,  '->O_TPARM[R117]', '', false);
INSERT INTO T_TPS VALUES (20, 8,  17, '->S_DT[R118]', 'Type', true);
INSERT INTO T_TPS VALUES (22, 10, 11, '->SM_SM[R517]->SM_STATE[R501]', '', false);
INSERT INTO T_TPS VALUES (410, 10, 319, '->SM_SM[R517]->SM_TXN[R505]->SM_CRTXN[R507]', '', false);
INSERT INTO T_TPS VALUES (411, 319, 12, '->SM_LEVT[R509]->SM_SEVT[R526]->SM_EVT[R525]', 'Creation Event', false); 
INSERT INTO T_TPS VALUES (23, 10, 12, '->SM_SM[R517]->SM_EVT[R502]->SM_SEVT[R525]->SM_LEVT[R526]->SM_SEVT[R526]->SM_EVT[R525]', 'Local Event', false, false, '', '', true);
INSERT INTO T_TPS VALUES (234, 10, 12, '->SM_SM[R517]->SM_EVT[R502]->SM_PEVT[R525]->SM_EVT[R525]', 'Declared Event', false, false, '', '', true);
INSERT INTO T_TPS VALUES (28, 81, 306, '->SM_PEVT[R527]', 'Declared Event', false);
INSERT INTO T_TPS VALUES (25, 11, 70, '->SM_SEME[R503]->SM_EIGN[R504]->SM_SEME[R504]', 'Event Ignored', false, false, '', '', true);
INSERT INTO T_TPS VALUES (409, 11, 70, '->SM_SEME[R503]->SM_CH[R504]->SM_SEME[R504]', 'Cant Happen', false, false, '', '', true);
INSERT INTO T_TPS VALUES (27, 11, 46, '->SM_MOAH[R511]->SM_AH[R513]->SM_ACT[R514]', '', false);
INSERT INTO T_TPS VALUES (89, 11, 294, '->SM_SEME[R503]->SM_NSTXN[R504]->SM_TXN[R507]', 'New State Transitions', false, false, '', '', true);
INSERT INTO T_TPS VALUES (89, 11, 294, '->SM_NETXN[R508]->SM_TXN[R507]', 'No Event Transitions', false, false, '', '', true);
INSERT INTO T_TPS VALUES (29, 10, 81, '->SM_SM[R517]->SM_EVT[R502]->SM_SEVT[R525]->SM_NLEVT[R526]', 'Inherited Event', false, false, '', '', true);
INSERT INTO T_TPS VALUES (31, 14, 17, '->S_DT[R524]', 'Type', true);
INSERT INTO T_TPS VALUES (408, 15, 11, '->SM_SM[R517]->SM_STATE[R501]', '', false);
INSERT INTO T_TPS VALUES (34, 15, 12, '->SM_SM[R517]->SM_EVT[R502]', '', false);
INSERT INTO T_TPS VALUES (37, 12, 14, '->SM_EVTDI[R532]', '', false);
INSERT INTO T_TPS VALUES (388, 12, 306, '->SM_PEVT[R525]', '', false, false, '', '', false, 0, '', true);
INSERT INTO T_TPS VALUES (389, 12, 309, '->SM_SEVT[R525]', '', false, false, '', '', false, 0, '', true);
INSERT INTO T_TPS VALUES (38, 16, 17, '->S_DT[R114]', 'Type', true);
INSERT INTO T_TPS VALUES (39, 16, 80, '->O_RATTR[R106]', '', false);
INSERT INTO T_TPS VALUES (40, 16, 32, '->O_OIDA[R105]', '', false);
INSERT INTO T_TPS VALUES (41, 16, 33, '->O_BATTR[R106]->O_DBATTR[R107]', '', false);
INSERT INTO T_TPS VALUES (41, 16, 305, '->O_BATTR[R106]->O_NBATTR[R107]', '', false);
INSERT INTO T_TPS VALUES (42, 16, 34, '->O_RATTR[R106]->O_REF[R108]', '', false);
INSERT INTO T_TPS VALUES (43, 75, 17, '->S_DT[R17]', '', false);
INSERT INTO T_TPS VALUES (44, 74, 17, '->S_DT[R17]', '', false);
INSERT INTO T_TPS VALUES (402,74, 384, '->S_RANGE[R57]', '', false);
INSERT INTO T_TPS VALUES (45, 77, 17, '->S_DT[R17]', '', false);
INSERT INTO T_TPS VALUES (46, 27, 28, '->S_BRG[R19]', '', false);
INSERT INTO T_TPS VALUES (50, 28, 17, '->S_DT[R20]', 'Return Type', true);
INSERT INTO T_TPS VALUES (60, 28, 29, '->S_BPARM[R21]', '', false);
INSERT INTO T_TPS VALUES (61, 29, 17, '->S_DT[R22]', 'Type', true);
INSERT INTO T_TPS VALUES (62, 30, 37, '->R_OIR[R201]->R_RTO[R203]->R_PART[R204]', '', false);
INSERT INTO T_TPS VALUES (63, 30, 38, '->R_OIR[R201]->R_RGO[R203]->R_FORM[R205]', '', false);
INSERT INTO T_TPS VALUES (64, 30, 39, '->R_OIR[R201]->R_RTO[R203]->R_AONE[R204]', '', false);
INSERT INTO T_TPS VALUES (65, 30, 40, '->R_OIR[R201]->R_RTO[R203]->R_AOTH[R204]', '', false);
INSERT INTO T_TPS VALUES (66, 30, 41, '->R_OIR[R201]->R_RGO[R203]->R_ASSR[R205]', '', false);
INSERT INTO T_TPS VALUES (67, 30, 42, '->R_OIR[R201]->R_RTO[R203]->R_SUPER[R204]', '', false);
INSERT INTO T_TPS VALUES (68, 30, 43, '->R_OIR[R201]->R_RGO[R203]->R_SUB[R205]', '', false);
INSERT INTO T_TPS VALUES (69, 30, 79, '->R_COMP[R206]', '', false);
INSERT INTO T_TPS VALUES (70, 34, 6,  '->O_RTIDA[R111]->R_RTO[R110]->R_OIR[R203]->O_OBJ[R201]', 'Referred To Class', false);
INSERT INTO T_TPS VALUES (71, 34, 16,  '->O_RTIDA[R111]->O_OIDA[R110]->O_ATTR[R105]', 'Referred To Attribute', false);
INSERT INTO T_TPS VALUES (72, 34, 30, '->O_RTIDA[R111]->R_RTO[R110]->R_OIR[R203]->R_REL[R201]', 'Referenced Over', false);
INSERT INTO T_TPS VALUES (73, 37, 6,  '->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]', 'Related', false);
INSERT INTO T_TPS VALUES (74, 38, 6,  '->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]', 'Related', false);
INSERT INTO T_TPS VALUES (75, 39, 6,  '->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]', 'Related', false);
INSERT INTO T_TPS VALUES (76, 40, 6,  '->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]', 'Related', false);
INSERT INTO T_TPS VALUES (77, 41, 6,  '->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]', 'Related', false);
INSERT INTO T_TPS VALUES (78, 42, 6,  '->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]', 'Related', false);
INSERT INTO T_TPS VALUES (79, 43, 6,  '->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]', 'Related', false);
INSERT INTO T_TPS VALUES (80, 44, 6,  '->R_OIR[R203]->O_OBJ[R201]', 'Related', false);
INSERT INTO T_TPS VALUES (81, 45, 6,  '->R_OIR[R203]->O_OBJ[R201]', 'Related', false);
INSERT INTO T_TPS VALUES (365, 294, 12, '->SM_NSTXN[R507]->SM_SEME[R504]->SM_SEVT[R503]->SM_EVT[R525]', 'Assigned Local Event', false, false, '', '', false, 0, '(LocalEvent_c.getOneSM_LEVTOnR526(SemEvent_c.getOneSM_SEVTOnR503(StateEventMatrixEntry_c.getOneSM_SEMEOnR504(NewStateTransition_c	.getOneSM_NSTXNOnR507((Transition_c) arg)))) == null && !(NoEventTransition_c.getOneSM_NETXNOnR507((Transition_c) arg) != null))');
INSERT INTO T_TPS VALUES (386, 294, 81, '->SM_NSTXN[R507]->SM_SEME[R504]->SM_SEVT[R503]->SM_NLEVT[R526]', 'Assigned Polymorphic Event', false, false, '', '', false, 0, '(NonLocalEvent_c.getOneSM_NLEVTOnR526(SemEvent_c.getOneSM_SEVTOnR503(StateEventMatrixEntry_c.getOneSM_SEMEOnR504(NewStateTransition_c.getOneSM_NSTXNOnR507((Transition_c) arg)))) == null)');
INSERT INTO T_TPS VALUES (387, 294, 12, '->SM_NSTXN[R507]->SM_SEME[R504]->SM_SEVT[R503]->SM_SGEVT[R526]->SM_SEVT[R526]->SM_EVT[R525]', 'Assigned Signal', false, false, '', '', false, 0, '(SignalEvent_c.getOneSM_SGEVTOnR526(SemEvent_c.getOneSM_SEVTOnR503(StateEventMatrixEntry_c.getOneSM_SEMEOnR504(NewStateTransition_c.getOneSM_NSTXNOnR507((Transition_c) arg)))) == null)');
INSERT INTO T_TPS VALUES (366, 294, 12, '->SM_CRTXN[R507]->SM_LEVT[R509]->SM_SEVT[R526]->SM_EVT[R525]', 'Creation Event', false, false, '', '', false, 0, '(CreationTransition_c.getOneSM_CRTXNOnR507((Transition_c) arg) == null)');
INSERT INTO T_TPS VALUES (83, 294, 11, '->SM_STATE[R506]', 'To', false);
INSERT INTO T_TPS VALUES (90, 70, 48, '->SM_EIGN[R504]', '', false);
INSERT INTO T_TPS VALUES (91, 70, 49, '->SM_CH[R504]', '', false);
INSERT INTO T_TPS VALUES (97, 77, 19, '->S_ENUM[R27]', '', false);
INSERT INTO T_TPS VALUES (99, 79, 44, '->R_CONE[R214]', '', false);
INSERT INTO T_TPS VALUES (100, 79, 45, '->R_COTH[R215]', '', false);
INSERT INTO T_TPS VALUES (102, 74, 17, '->S_DT[R18]', 'Core Type', true, false);
INSERT INTO T_TPS VALUES (112, 88, 91, '->SQ_P[R930]->SQ_LS[R940]', '', false);
INSERT INTO T_TPS VALUES (404, 90, 91, '->SQ_P[R930]->SQ_LS[R940]', '', false);
INSERT INTO T_TPS VALUES (405, 86, 91, '->SQ_P[R930]->SQ_LS[R940]', '', false);
INSERT INTO T_TPS VALUES (406, 296, 91, '->SQ_P[R930]->SQ_LS[R940]', '', false);
INSERT INTO T_TPS VALUES (407, 101, 91, '->SQ_P[R930]->SQ_LS[R940]', '', false);
INSERT INTO T_TPS VALUES (115, 90, 92, '->SQ_CPA[R935]->SQ_IA[R947]->SQ_CPA[R947]', '', false);
INSERT INTO T_TPS VALUES (116, 86, 98, '->SQ_AV[R936]->SQ_IAV[R948]->SQ_AV[R948]', '', false);
INSERT INTO T_TPS VALUES (117, 86, 87, '->SQ_AV[R937]->SQ_FAV[R948]->SQ_AV[R948]', '', false);
INSERT INTO T_TPS VALUES (118, 91, 93, '->SQ_TM[R931]', '', false);
INSERT INTO T_TPS VALUES (119, 93, 94, '->SQ_TS[R941]', '', false);
INSERT INTO T_TPS VALUES (120, 95, 99, '->MSG_M[R1018]->MSG_A[R1000]->MSG_IA[R1013]->MSG_A[R1013]', '', false);
INSERT INTO T_TPS VALUES (121, 95, 97, '->MSG_M[R1018]->MSG_A[R1001]', '', false);
INSERT INTO T_TPS VALUES (122, 96, 99, '->MSG_M[R1018]->MSG_A[R1000]->MSG_IA[R1013]->MSG_A[R1013]', '', false);
INSERT INTO T_TPS VALUES (123, 96, 97, '->MSG_M[R1018]->MSG_A[R1001]', '', false);
INSERT INTO T_TPS VALUES (124, 102, 99, '->MSG_M[R1018]->MSG_A[R1000]->MSG_IA[R1013]->MSG_A[R1013]', '', false);
INSERT INTO T_TPS VALUES (125, 102, 97, '->MSG_M[R1018]->MSG_A[R1001]', '', false);
INSERT INTO T_TPS VALUES (127, 86, 95, '->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]', '', false);
INSERT INTO T_TPS VALUES (128, 86, 96, '->SQ_P[R930]->MSG_M[R1007]->MSG_AM[R1018]', '', false);
INSERT INTO T_TPS VALUES (129, 86, 102, '->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]', '', false);
INSERT INTO T_TPS VALUES (130, 90, 95, '->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]', '', false);
INSERT INTO T_TPS VALUES (131, 90, 96, '->SQ_P[R930]->MSG_M[R1007]->MSG_AM[R1018]', '', false);
INSERT INTO T_TPS VALUES (132, 90, 102, '->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]', '', false);
INSERT INTO T_TPS VALUES (135, 88, 95, '->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]', '', false);
INSERT INTO T_TPS VALUES (136, 88, 102, '->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]', '', false);
INSERT INTO T_TPS VALUES (137, 91, 95, '->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]', '', false);
INSERT INTO T_TPS VALUES (138, 91, 96, '->SQ_P[R930]->MSG_M[R1007]->MSG_AM[R1018]', '', false);
INSERT INTO T_TPS VALUES (139, 91, 102, '->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]', '', false);
INSERT INTO T_TPS VALUES (339, 296, 95, '->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]', '', false);
INSERT INTO T_TPS VALUES (340, 296, 96, '->SQ_P[R930]->MSG_M[R1007]->MSG_AM[R1018]', '', false);
INSERT INTO T_TPS VALUES (341, 296, 102, '->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]', '', false);
INSERT INTO T_TPS VALUES (347, 304, 95, '->SQ_P[R930]->MSG_M[R1007]->MSG_SM[R1018]', '', false);
INSERT INTO T_TPS VALUES (348, 304, 96, '->SQ_P[R930]->MSG_M[R1007]->MSG_AM[R1018]', '', false);
INSERT INTO T_TPS VALUES (349, 304, 102, '->SQ_P[R930]->MSG_M[R1007]->MSG_R[R1018]', '', false);
INSERT INTO T_TPS VALUES (149, 104, 86, '->SQ_P[R1133]->SQ_CIP[R930]', 'Link Start', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (150, 104, 86, '->SQ_P[R1134]->SQ_CIP[R930]', 'Link End', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (151, 104, 90, '->SQ_P[R1133]->SQ_CP[R930]', 'Link Start', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (152, 104, 90, '->SQ_P[R1134]->SQ_CP[R930]', 'Link End', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (153, 104, 88, '->SQ_P[R1133]->SQ_EEP[R930]', 'Link Start', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (154, 104, 88, '->SQ_P[R1134]->SQ_EEP[R930]', 'Link End', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (157, 104, 101, '->SQ_P[R1133]->SQ_AP[R930]', 'Link Start', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (158, 104, 101, '->SQ_P[R1134]->SQ_AP[R930]', 'Link End', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (343, 104, 296, '->SQ_P[R1133]->SQ_COP[R930]', 'Link Start', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (344, 104, 296, '->SQ_P[R1134]->SQ_COP[R930]', 'Link End', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (167, 107,  101,  '->UC_UCA[R1210]->SQ_P[R1206]->SQ_AP[R930]', 'Association One Side', false);
INSERT INTO T_TPS VALUES (168, 107,  106,  '->UC_UCA[R1210]->SQ_P[R1207]->IA_UCP[R930]', 'Association Other Side', false);
INSERT INTO T_TPS VALUES (171, 108,  106,  '->UC_UCA[R1210]->SQ_P[R1206]->IA_UCP[R930]', 'Association One Side', false);
INSERT INTO T_TPS VALUES (172, 108,  106,  '->UC_UCA[R1210]->SQ_P[R1207]->IA_UCP[R930]', 'Association Other Side', false);
INSERT INTO T_TPS VALUES (173, 109,  106,  '->UC_UCA[R1210]->SQ_P[R1206]->IA_UCP[R930]', 'Association One Side', false);
INSERT INTO T_TPS VALUES (174, 109,  106,  '->UC_UCA[R1210]->SQ_P[R1207]->IA_UCP[R930]', 'Association Other Side', false);
INSERT INTO T_TPS VALUES (175, 110,  106,  '->UC_UCA[R1210]->SQ_P[R1206]->IA_UCP[R930]', 'Association One Side', false);
INSERT INTO T_TPS VALUES (176, 110,  106,  '->UC_UCA[R1210]->SQ_P[R1207]->IA_UCP[R930]', 'Association Other Side', false);
 
INSERT INTO T_TPS VALUES (190, 88, 27, '->S_EE[R933]', 'Formal External Entity', false);
INSERT INTO T_TPS VALUES (192, 90, 6,  '->O_OBJ[R939]', 'Formal Class', false);
INSERT INTO T_TPS VALUES (193, 86, 6,  '->O_OBJ[R934]', 'Formal Instance', false);
INSERT INTO T_TPS VALUES (194, 87, 16, '->O_ATTR[R938]', 'Formal Attribute', false);
INSERT INTO T_TPS VALUES (334, 304, 297,'->EP_PKG[R956]', 'Formal Package', false);
INSERT INTO T_TPS VALUES (345, 296, 125,'->C_C[R955]', 'Formal Component', false);

INSERT INTO T_TPS VALUES (390, 95, 310, '->MSG_ISM[R1020]', 'Informal Message', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (391, 96, 311, '->MSG_IAM[R1019]', 'Informal Message', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (195, 95, 313, '->MSG_B[R1020]', 'Formal Bridge Operation', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (196, 95, 314,  '->MSG_O[R1020]', 'Formal Operation', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (197, 95, 312,  '->MSG_F[R1020]', 'Formal Function', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (208, 95, 315, '->MSG_IOP[R1020]', 'Formal Interface Operation', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (198, 96, 316, '->MSG_E[R1019]', 'Formal Event', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (209, 96, 317, '->MSG_SIG[R1019]', 'Formal Signal Event', false, false, '', '', false, 0, '(referential == null)');

INSERT INTO T_TPS VALUES (199, 97, 29, '->MSG_BA[R1013]->S_BPARM[R1014]', 'Formal Bridge Operation Parameter', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (200, 97, 8,  '->MSG_OA[R1013]->O_TPARM[R1015]', 'Formal Class Operation Parameter', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (201, 97, 4,  '->MSG_FA[R1013]->S_SPARM[R1016]', 'Formal Function Parameter', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (202, 97, 14, '->MSG_EA[R1013]->SM_EVTDI[R1017]', 'Formal Event Data Item', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (210, 97, 133, '->MSG_EPA[R1013]->C_PP[R1023]', 'Executable Property Parameter', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (395, 99, 29, '->MSG_BA[R1013]->S_BPARM[R1014]', 'Formal Bridge Operation Parameter', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (396, 99, 8,  '->MSG_OA[R1013]->O_TPARM[R1015]', 'Formal Class Operation Parameter', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (397, 99, 4,  '->MSG_FA[R1013]->S_SPARM[R1016]', 'Formal Function Parameter', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (398, 99, 14, '->MSG_EA[R1013]->SM_EVTDI[R1017]', 'Formal Event Data Item', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (399, 99, 133, '->MSG_EPA[R1013]->C_PP[R1023]', 'Executable Property Parameter', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (400, 97, 318, '->MSG_IA[R1013]', 'Informal Argument', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (401, 99, 318, '->MSG_IA[R1013]', 'Informal Argument', false, false, '', '', false, 0, '(referential == null)');

INSERT INTO T_TPS VALUES (207, 125, 291, '->C_PO[R4010]', '', false);
INSERT INTO T_TPS VALUES (385 , 382, 291, '->C_PO[R4709]', 'Port Reference', false);
INSERT INTO T_TPS VALUES (365, 126, 125, '->C_C[R4201]', 'Referenced Component', false);
INSERT INTO T_TPS VALUES (384, 126, 382, '->CL_POR[R4707]', '', false);
INSERT INTO T_TPS VALUES (211, 382, 292, '->CL_IIR[R4708]->CL_IP[R4703]', '', false);
INSERT INTO T_TPS VALUES (212, 382, 293, '->CL_IIR[R4708]->CL_IR[R4703]', '', false);
INSERT INTO T_TPS VALUES (213, 127, 130, '->C_IR[R4009]->C_I[R4012]', 'Referenced Interface', false);
INSERT INTO T_TPS VALUES (214, 128, 130, '->C_IR[R4009]->C_I[R4012]', 'Referenced Interface', false);
INSERT INTO T_TPS VALUES (219, 130, 132, '->C_EP[R4003]->C_IO[R4004]', '', false);
INSERT INTO T_TPS VALUES (218, 130, 131, '->C_EP[R4003]->C_AS[R4004]', '', false);
INSERT INTO T_TPS VALUES (220, 131, 133, '->C_EP[R4004]->C_PP[R4006]', '', false);
INSERT INTO T_TPS VALUES (221, 132, 133, '->C_EP[R4004]->C_PP[R4006]', '', false);
INSERT INTO T_TPS VALUES (224, 132,  17, '->S_DT[R4008]', 'Return Type', true, true, 'DataTypePackage', 'Domain, ComponentPackage');
INSERT INTO T_TPS VALUES (225, 133,  17, '->S_DT[R4007]', 'Type', true, true, 'DataTypePackage', 'Domain, ComponentPackage');
INSERT INTO T_TPS VALUES (231, 288, 289, '->S_MBR[R44]', '', false);
INSERT INTO T_TPS VALUES (232, 289, 17, '->S_DT[R45]', 'Type', true);
INSERT INTO T_TPS VALUES (233, 288, 17, '->S_DT[R17]', '', false);
INSERT INTO T_TPS VALUES (383, 127,136, '->SPR_PEP[R4501]->SPR_PO[R4503]', '', false);
INSERT INTO T_TPS VALUES (235, 127,137, '->SPR_PEP[R4501]->SPR_PS[R4503]', '', false);
INSERT INTO T_TPS VALUES (236, 128,134, '->SPR_REP[R4500]->SPR_RO[R4502]', '', false);
INSERT INTO T_TPS VALUES (237, 128,135, '->SPR_REP[R4500]->SPR_RS[R4502]', '', false);
INSERT INTO T_TPS VALUES (238, 136,133, '->SPR_PEP[R4503]->C_EP[R4501]->C_PP[R4006]', '', false);
INSERT INTO T_TPS VALUES (239, 137,133, '->SPR_PEP[R4503]->C_EP[R4501]->C_PP[R4006]', '', false);
INSERT INTO T_TPS VALUES (240, 134,133, '->SPR_REP[R4502]->C_EP[R4500]->C_PP[R4006]', '', false);
INSERT INTO T_TPS VALUES (241, 135,133, '->SPR_REP[R4502]->C_EP[R4500]->C_PP[R4006]', '', false);
INSERT INTO T_TPS VALUES (245, 291,127, '->C_IR[R4016]->C_P[R4009]', '', false);
INSERT INTO T_TPS VALUES (246, 291,128, '->C_IR[R4016]->C_R[R4009]', '', false);
INSERT INTO T_TPS VALUES (247, 292,136, '->CL_IIR[R4703]->C_IR[R4701]->C_P[R4009]->SPR_PEP[R4501]->SPR_PO[R4503]', '', false);
INSERT INTO T_TPS VALUES (248, 292,137, '->CL_IIR[R4703]->C_IR[R4701]->C_P[R4009]->SPR_PEP[R4501]->SPR_PS[R4503]', '', false);
INSERT INTO T_TPS VALUES (249, 293,134, '->CL_IIR[R4703]->C_IR[R4701]->C_R[R4009]->SPR_REP[R4500]->SPR_RO[R4502]', '', false);
INSERT INTO T_TPS VALUES (250, 293,135, '->CL_IIR[R4703]->C_IR[R4701]->C_R[R4009]->SPR_REP[R4500]->SPR_RS[R4502]', '', false);

INSERT INTO T_TPS VALUES (262, 294, 46, '->SM_TAH[R530]->SM_AH[R513]->SM_ACT[R514]', '', false);

INSERT INTO T_TPS VALUES (276, 1, 297, '->EP_PKG[R1401]', '', false);

INSERT INTO T_TPS VALUES (285, 300, 301, '->CNST_SYC[R1504]->CNST_LFSC[R1502]->CNST_LSC[R1503]', '', false);
INSERT INTO T_TPS VALUES (287, 301, 302, '->CNST_LFSC[R1503]->CNST_SYC[R1502]', '', false);
INSERT INTO T_TPS VALUES (288, 301,  17, '->CNST_LFSC[R1503]->CNST_SYC[R1502]->S_DT[R1500]', 'Type', true);

INSERT INTO T_TPS VALUES (289, 297,  74, '->PE_PE[R8000]->S_DT[R8001]->S_UDT[R17]', '', false);
INSERT INTO T_TPS VALUES (290, 297,  75, '->PE_PE[R8000]->S_DT[R8001]->S_CDT[R17]', '', false);
INSERT INTO T_TPS VALUES (291, 297,  77, '->PE_PE[R8000]->S_DT[R8001]->S_EDT[R17]', '', false);
INSERT INTO T_TPS VALUES (292, 297, 288, '->PE_PE[R8000]->S_DT[R8001]->S_SDT[R17]', '', false);
INSERT INTO T_TPS VALUES (293, 297, 300, '->PE_PE[R8000]->CNST_CSP[R8001]', '', false);
INSERT INTO T_TPS VALUES (294, 297,  86, '->PE_PE[R8000]->SQ_P[R8001]->SQ_CIP[R930]', '', false);
INSERT INTO T_TPS VALUES (295, 297,  88, '->PE_PE[R8000]->SQ_P[R8001]->SQ_EEP[R930]', '', false);
INSERT INTO T_TPS VALUES (333, 297, 304, '->PE_PE[R8000]->SQ_P[R8001]->SQ_PP[R930]', '', false);
INSERT INTO T_TPS VALUES (296, 297,  90, '->PE_PE[R8000]->SQ_P[R8001]->SQ_CP[R930]', '', false);
INSERT INTO T_TPS VALUES (337, 297,  296, '->PE_PE[R8000]->SQ_P[R8001]->SQ_COP[R930]', '', false);
INSERT INTO T_TPS VALUES (395, 297, 101, '->PE_PE[R8000]->SQ_P[R8001]->SQ_AP[R930]', '', false);
INSERT INTO T_TPS VALUES (301, 297, 104, '->PE_PE[R8000]->SQ_P[R8001]->COMM_LNK[R1133]', '', false);
INSERT INTO T_TPS VALUES (302, 297, 106, '->PE_PE[R8000]->SQ_P[R8001]->IA_UCP[R930]', '', false);
INSERT INTO T_TPS VALUES (304, 297,  30, '->PE_PE[R8000]->R_REL[R8001]', '', false);
INSERT INTO T_TPS VALUES (305, 297, 107, '->PE_PE[R8000]->UC_UCA[R8001]->UC_BA[R1210]', '', false);
INSERT INTO T_TPS VALUES (306, 297, 108, '->PE_PE[R8000]->UC_UCA[R8001]->UC_G[R1210]', '', false);
INSERT INTO T_TPS VALUES (307, 297, 109, '->PE_PE[R8000]->UC_UCA[R8001]->UC_I[R1210]', '', false);
INSERT INTO T_TPS VALUES (308, 297, 110, '->PE_PE[R8000]->UC_UCA[R8001]->UC_E[R1210]', '', false);
INSERT INTO T_TPS VALUES (309, 297, 112, '->PE_PE[R8000]->A_N[R8001]->A_CTL[R1105]->A_FJ[R1106]', '', false);
INSERT INTO T_TPS VALUES (310, 297, 113, '->PE_PE[R8000]->A_N[R8001]->A_CTL[R1105]->A_INI[R1106]', '', false);
INSERT INTO T_TPS VALUES (311, 297, 114, '->PE_PE[R8000]->A_E[R8001]', '', false);
INSERT INTO T_TPS VALUES (312, 297, 115, '->PE_PE[R8000]->A_N[R8001]->A_CTL[R1105]->A_AF[R1106]', '', false);
INSERT INTO T_TPS VALUES (313, 297, 116, '->PE_PE[R8000]->A_N[R8001]->A_CTL[R1105]->A_FF[R1106]', '', false);
INSERT INTO T_TPS VALUES (314, 297, 117, '->PE_PE[R8000]->A_N[R8001]->A_ACT[R1105]->A_GA[R1107]', '', false);
INSERT INTO T_TPS VALUES (315, 297, 118, '->PE_PE[R8000]->A_N[R8001]->A_CTL[R1105]->A_DM[R1106]', '', false);
INSERT INTO T_TPS VALUES (316, 297, 119, '->PE_PE[R8000]->A_N[R8001]->A_OBJ[R1105]', '', false);
INSERT INTO T_TPS VALUES (317, 297, 120, '->PE_PE[R8000]->A_N[R8001]->A_ACT[R1105]->A_AE[R1107]->A_AEA[R1112]', '', false);
INSERT INTO T_TPS VALUES (318, 297, 121, '->PE_PE[R8000]->A_N[R8001]->A_ACT[R1105]->A_SS[R1107]', '', false);
INSERT INTO T_TPS VALUES (319, 297, 123, '->PE_PE[R8000]->A_N[R8001]->A_ACT[R1105]->A_AE[R1107]->A_ATE[R1112]', '', false);
INSERT INTO T_TPS VALUES (320, 297,   6, '->PE_PE[R8000]->O_OBJ[R8001]', '', false);
INSERT INTO T_TPS VALUES (321, 297,  31, '->PE_PE[R8000]->O_IOBJ[R8001]', '', false);
INSERT INTO T_TPS VALUES (322, 297, 125, '->PE_PE[R8000]->C_C[R8001]', '', false);
INSERT INTO T_TPS VALUES (323, 297, 126, '->PE_PE[R8000]->CL_IC[R8001]', '', false);
INSERT INTO T_TPS VALUES (326, 297, 130, '->PE_PE[R8000]->C_I[R8001]', '', false);
INSERT INTO T_TPS VALUES (327, 297,  27, '->PE_PE[R8000]->S_EE[R8001]', '', false);
INSERT INTO T_TPS VALUES (328, 297,   3, '->PE_PE[R8000]->S_SYNC[R8001]', '', false);
INSERT INTO T_TPS VALUES (329, 297, 122, '->PE_PE[R8000]->A_AP[R8001]', '', false);
INSERT INTO T_TPS VALUES (330, 297,  95, '->PE_PE[R8000]->MSG_M[R8001]->MSG_SM[R1018]', '', false, false, '', '', false, 0, '(InteractionParticipant_c.getOneSQ_POnR1007(Message_c.getOneMSG_MOnR1018(v_synchronousmessages[i])) != null)');
INSERT INTO T_TPS VALUES (331, 297,  96, '->PE_PE[R8000]->MSG_M[R8001]->MSG_AM[R1018]', '', false, false, '', '', false, 0, '(InteractionParticipant_c.getOneSQ_POnR1007(Message_c.getOneMSG_MOnR1018(v_asynchronousmessages[i])) != null)');
INSERT INTO T_TPS VALUES (332, 297, 102, '->PE_PE[R8000]->MSG_M[R8001]->MSG_R[R1018]', '', false, false, '', '', false, 0, '(InteractionParticipant_c.getOneSQ_POnR1007(Message_c.getOneMSG_MOnR1018(v_returnmessages[i])) != null)');
INSERT INTO T_TPS VALUES (346, 297, 297, '->PE_PE[R8000]->EP_PKG[R8001]', '', false);
INSERT INTO T_TPS VALUES (350, 297, 303, '->PE_PE[R8000]->C_SF[R8001]', '', false);
INSERT INTO T_TPS VALUES (351, 303, 127, '->C_P[R4002]', '', false);
INSERT INTO T_TPS VALUES (352, 303, 128, '->C_R[R4002]', '', false);

INSERT INTO T_TPS VALUES (335, 104, 304, '->SQ_P[R1133]->SQ_PP[R930]', 'Link Start', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (336, 104, 304, '->SQ_P[R1134]->SQ_PP[R930]', 'Link End', false, false, '', '', false, 0, '(referential == null)');
INSERT INTO T_TPS VALUES (337, 31, 6, '->O_OBJ[R101]', 'Class Reference', false);
INSERT INTO T_TPS VALUES (368, 37, 307, '->R_RTO[R204]->O_RTIDA[R110]', '', false);
INSERT INTO T_TPS VALUES (370, 39, 307, '->R_RTO[R204]->O_RTIDA[R110]', '', false);
INSERT INTO T_TPS VALUES (371, 40, 307, '->R_RTO[R204]->O_RTIDA[R110]', '', false);
INSERT INTO T_TPS VALUES (369, 42, 307, '->R_RTO[R204]->O_RTIDA[R110]', '', false);
INSERT INTO T_TPS VALUES (372, 127, 308, '->C_IR[R4009]->C_RID[R4013]->C_DG[R4013]', 'Delegated Provided Interfaces', false, false, '', '', true);
INSERT INTO T_TPS VALUES (373, 128, 308, '->C_IR[R4009]->C_RID[R4013]->C_DG[R4013]', 'Delegated Required Interfaces', false, false, '', '', true);

INSERT INTO T_TPS VALUES (376, 125, 297, '->PE_PE[R8003]->EP_PKG[R8001]', '', false);
INSERT INTO T_TPS VALUES (377, 125, 125, '->PE_PE[R8003]->C_C[R8001]', 'Component', false, false, '', '', true);
INSERT INTO T_TPS VALUES (378, 125, 126, '->PE_PE[R8003]->CL_IC[R8001]', '', false);
INSERT INTO T_TPS VALUES (379, 125, 130, '->PE_PE[R8003]->C_I[R8001]', '', false);
INSERT INTO T_TPS VALUES (380, 125, 74,  '->PE_PE[R8003]->S_DT[R8001]->S_UDT[R17]', '', false);
INSERT INTO T_TPS VALUES (381, 297, 383, '->PE_PE[R8000]->S_EXP[R8001]', '', false);
INSERT INTO T_TPS VALUES (382, 297, 385, '->PE_PE[R8000]->D_DEPL[R8001]', '', false);
INSERT INTO T_TPS VALUES (601, 385, 386, '->D_TERM[R1650]', '', false);
INSERT INTO T_TPS VALUES (602, 386, 387, '->D_TSVC[R1651]', '', false);
INSERT INTO T_TPS VALUES (603, 387,  17, '->S_DT[R1656]', 'Type', false);
INSERT INTO T_TPS VALUES (604, 387, 388, '->D_TSPARM[R1652]', '', false);
INSERT INTO T_TPS VALUES (605, 388,  17, '->S_DT[R1653]', 'Type', false);
INSERT INTO T_TPS VALUES (606, 7,  389, '->O_DEF[R126]', 'Deferral', false);
INSERT INTO T_TPS VALUES (607, 389,  30, '->R_REL[R126]', 'Relationship', false);

-- Next available T_TPS ID 412
-- The entries below specify the data association between tree elements, it captures
-- the underlying data's parent/child association as the tree entries above may
-- include multiple elements to reach a tree child (for display purposes)
--Below is the corresponding T_TPS entry 
--INSERT INTO T_DAS VALUES (36, 'SM_SM', 'SM_TXN', '->SM_TXN[R505]', 'self');


-- The following allows for external (to the T_TNS class) data to display under
-- the associated T_TNS
INSERT INTO T_EDL VALUES (1, 74, 'PE_PE', '->S_DT[R17]->PE_PE[R8001]');
INSERT INTO T_EA VALUES (1, 'Visibility');
INSERT INTO T_EDL VALUES (2, 77, 'PE_PE', '->S_DT[R17]->PE_PE[R8001]');
INSERT INTO T_EA VALUES (2, 'Visibility');
INSERT INTO T_EDL VALUES (3, 288, 'PE_PE', '->S_DT[R17]->PE_PE[R8001]');
INSERT INTO T_EA VALUES (3, 'Visibility');
INSERT INTO T_EDL VALUES (4, 300, 'PE_PE', '->PE_PE[R8001]');
INSERT INTO T_EA VALUES (4, 'Visibility');
INSERT INTO T_EDL VALUES (5, 125, 'PE_PE', '->PE_PE[R8001]');
INSERT INTO T_EA VALUES (5, 'Visibility');
INSERT INTO T_EDL VALUES (6, 297, 'PE_PE', '->PE_PE[R8001]');
INSERT INTO T_EA VALUES (6, 'Visibility');
INSERT INTO T_EDL VALUES (7, 6, 'PE_PE', '->PE_PE[R8001]');
INSERT INTO T_EA VALUES (7, 'Visibility');


