--======================================================================
--
-- Licensed under the Apache License, Version 2.0 (the "License"); you may not 
-- use this file except in compliance with the License.  You may obtain a copy 
-- of the License at
--
--       http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software 
-- distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
-- WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
-- License for the specific language governing permissions and limitations under
-- the License.
--======================================================================

INSERT INTO EO VALUES ( '6',	'13',	  '',     'System Model',0 , '', 'first', true );
INSERT INTO EO VALUES ( '13',	'',	      '23',   'SystemModelPackage',0 , '', 'first', false );
INSERT INTO EO VALUES ( '23', '23.1', '', 'Package', 1401, 'many', 'first', true );

INSERT INTO EO VALUES ( '23.1',       '',          '23.10', 'PackageDiagram', 0, '', 'first', false );
INSERT INTO EO VALUES ( '23.10',  '1.5.3.1.1', '23.11', 'Data Type', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->S_DT[R8001]'  );
INSERT INTO EO VALUES ( '23.11',  '6.2.1', '23.12',   'Interaction Participant', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->SQ_P[R8001]'  );
INSERT INTO EO VALUES ( '23.12',  '1.14.2.1', '23.13',       'Activity Node', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->A_N[R8001]'  );
INSERT INTO EO VALUES ( '23.13',  '2.1.1', '23.14',          'Model Class', -1, 'many', 'first', true, false, '', false, false, '->PE_PE[R8000]->O_OBJ[R8001]' );
INSERT INTO EO VALUES ( '23.14',  '12.4.1', '23.15',       'Component', -1, 'many', 'first', true, false, '', false, false, '->PE_PE[R8000]->C_C[R8001]'  );
INSERT INTO EO VALUES ( '23.15',  '12.4.8.1', '23.16',       'Component Reference', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->CL_IC[R8001]'  );
INSERT INTO EO VALUES ( '23.16',  '14.1.3.1', '23.17',       'Interface', -1, 'many', 'first', true, false, '', false, false, '->PE_PE[R8000]->C_I[R8001]'  );
INSERT INTO EO VALUES ( '23.17',  '', '23.18',                  'Package', -1, 'many', 'none', true, false, '', false, false, '->PE_PE[R8000]->EP_PKG[R8001]'  );
INSERT INTO EO VALUES ( '23.18',  '1.5.7.1.1', '23.19',  'Constant Specification', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->CNST_CSP[R8001]'  );
INSERT INTO EO VALUES ( '23.19',  '23.19.1', '23.20',  'Activity Partition', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->A_AP[R8001]'  );
INSERT INTO EO VALUES ( '23.20',  '23.20.1', '23.21',  'Activity Edge', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->A_E[R8001]'  );
INSERT INTO EO VALUES ( '23.21',  '7.1', '23.22',  'Message', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->MSG_M[R8001]'  );
INSERT INTO EO VALUES ( '23.22',  '2.4.1', '23.23',  'Association', -1, 'many', 'Derived Association', false, false, '', false, false, '->PE_PE[R8000]->R_REL[R8001]'  );
INSERT INTO EO VALUES ( '23.23',  '23.23.1', '23.24',  'Imported Class', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->O_IOBJ[R8001]'  );
INSERT INTO EO VALUES ( '23.24',  '1.6.4.1.1', '23.25',  'External Entity', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->S_EE[R8001]'  );
INSERT INTO EO VALUES ( '23.25',  '1.7.4.1.1', '23.26',  'Function', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->S_SYNC[R8001]'  );
INSERT INTO EO VALUES ( '23.26',  '10.4.1.1', '23.27',  'Use Case Association', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->UC_UCA[R8001]'  );
INSERT INTO EO VALUES ( '23.27',  '23.27.1',        '23.28',  'Satisfaction', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->C_SF[R8001]'  );
INSERT INTO EO VALUES ( '23.28',  '12.4.9.1.1', '23.29',       'Delegation', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->C_DG[R8001]'  );
INSERT INTO EO VALUES ( '23.29',  '23.29.1', '23.30',  'Exception', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->S_EXP[R8001]'  );
INSERT INTO EO VALUES ( '23.30',  '23.30.1', '23.31',  'Proc', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->PR_PROC[R8001]'  );
INSERT INTO EO VALUES ( '23.31',  '', '23.32',  'Package Reference', 1402, 'one', 'first', false, false, 'refers to', false, false, ''  );
INSERT INTO EO VALUES ( '23.32',      '', '', 'Packageable Element', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '23.19.1',    '', '', 'Packageable Element', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '23.20.1',    '', '', 'Packageable Element', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '23.23.1',    '', '', 'Packageable Element', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '23.27.1',    '', '', 'Packageable Element', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '23.29.1',    '', '', 'Packageable Element', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '23.31.1',    '', '', 'Packageable Element', 8001, 'one', 'first', false );

INSERT INTO EO VALUES ( '23.30.1', '23.30.1.1', '23.30.2', 'Terminator', 1650, 'many', 'first', false );
INSERT INTO EO VALUES ( '23.30.1.1', '', '', 'Terminator Service', 1651, 'many', 'first', false );
INSERT INTO EO VALUES ( '23.30.2', '', '', 'Packageable Element', 8001, 'one', 'first', false );

INSERT INTO EO VALUES ( '12.4.1',       '',    '12.4.3',     'ComponentDiagram', 0, '', 'first', false );
INSERT INTO EO VALUES ( '12.4.3',     '12.4.3.1',  '12.4.9',      'Port', 4010, 'many', 'first', false );             
INSERT INTO EO VALUES ( '12.4.3.1',     '12.4.3.1.1',  '',      'Interface Reference', 4016, 'many', 'first', false );             
INSERT INTO EO VALUES ( '12.4.3.1.1',   '12.4.3.1.1.1',          '12.4.3.1.2', 'Requirement', 4009, 'one', 'first', false );
INSERT INTO EO VALUES ( '12.4.3.1.1.1', '12.4.3.1.1.1.1', '',     'Required Executable Property', 4500, 'many', 'first', false );
INSERT INTO EO VALUES ( '12.4.3.1.1.1.1', '', '12.4.3.1.1.1.2',     'Required Signal', 4502, 'one', 'first', false );
INSERT INTO EO VALUES ( '12.4.3.1.1.1.2', '', '',     'Required Operation', 4502, 'one', 'first', false );
INSERT INTO EO VALUES ( '12.4.3.1.2',   '12.4.3.1.2.1','', 'Provision', 4009, 'one', 'first', false );
INSERT INTO EO VALUES ( '12.4.3.1.2.1', '12.4.3.1.2.1.1', '',     'Provided Executable Property', 4501, 'many', 'first', false );
INSERT INTO EO VALUES ( '12.4.3.1.2.1.1', '', '12.4.3.1.2.1.2',     'Provided Signal', 4503, 'one', 'first', false );
INSERT INTO EO VALUES ( '12.4.3.1.2.1.2', '', '',     'Provided Operation', 4503, 'one', 'first', false );
INSERT INTO EO VALUES ( '12.4.8.1',       '12.4.8.1.1',    '12.4.8.2', 'Port Reference', 4707, 'many', 'first', false );
INSERT INTO EO VALUES ( '12.4.8.1.1',      '12.4.8.1.1.1'  ,'', 'Imported Reference', 4708, 'many', 'first', false );
INSERT INTO EO VALUES ( '12.4.8.1.1.1',     '12.4.8.1.1.1.1',    '12.4.8.1.1.2', 'Imported Provision', 4703, 'one', 'first', false );
INSERT INTO EO VALUES ( '12.4.8.1.1.1.1',       '',    '', 'Imported Provision In Satisfaction', 4705, 'many', 'first', false );
INSERT INTO EO VALUES ( '12.4.8.1.1.2',       '',    '', 'Imported Requirement', 4703, 'one', 'first', false );
INSERT INTO EO VALUES ( '12.4.9',   '12.4.9.1', '12.4.10',     'Delegation In Component', 9002, 'many', 'first', false );
INSERT INTO EO VALUES ( '12.4.9.1',   '12.4.9.1.1', '',     'Delegation', 9002, 'many', 'first', false );
INSERT INTO EO VALUES ( '12.4.9.1.1',   '', '12.4.9.1.2',     'Interface Reference In Delegation', 4013, 'many', 'first', false );
INSERT INTO EO VALUES ( '12.4.10', '12.4.10.1',          '12.4.15',  'Satisfaction In Component', 9000, 'many', 'first', false );
INSERT INTO EO VALUES ( '12.4.10.1', '',          '',  'Satisfaction', 9000, 'many', 'first', false );

INSERT INTO EO VALUES ( '12.4.8.2',    '', '', 'Packageable Element', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '12.4.9.1.2',    '', '', 'Packageable Element', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '12.4.15',    '', '12.4.16', 'Packageable Element', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '12.4.16',  '1.5.3.1.1', '12.4.17', 'Data Type', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8003]->S_DT[R8001]'  );
INSERT INTO EO VALUES ( '12.4.17',  '6.2.1', '12.4.18',   'Interaction Participant', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8003]->SQ_P[R8001]'  );
INSERT INTO EO VALUES ( '12.4.18',  '1.14.2.1', '12.4.19',       'Activity Node', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8003]->A_N[R8001]'  );
INSERT INTO EO VALUES ( '12.4.19',  '2.1.1', '12.4.20',          'Model Class', -1, 'many', 'none', true, false, '', false, false, '->PE_PE[R8003]->O_OBJ[R8001]' );
INSERT INTO EO VALUES ( '12.4.20',  '', '12.4.21',       'Component', -1, 'many', 'none', true, false, '', false, false, '->PE_PE[R8003]->C_C[R8001]'  );
INSERT INTO EO VALUES ( '12.4.21',  '12.4.8.1', '12.4.22',       'Component Reference', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8003]->CL_IC[R8001]'  );
INSERT INTO EO VALUES ( '12.4.22',  '14.1.3.1', '12.4.23',       'Interface', -1, 'many', 'none', true, false, '', false, false, '->PE_PE[R8003]->C_I[R8001]'  );
INSERT INTO EO VALUES ( '12.4.23',  '', '12.4.24',                  'Package', -1, 'many', 'none', true, false, '', false, false, '->PE_PE[R8003]->EP_PKG[R8001]'  );
INSERT INTO EO VALUES ( '12.4.24',  '1.5.7.1.1', '12.4.25',  'Constant Specification', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8003]->CNST_CSP[R8001]'  );
INSERT INTO EO VALUES ( '12.4.25',  '23.19.1', '12.4.26',  'Activity Partition', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8003]->A_AP[R8001]'  );
INSERT INTO EO VALUES ( '12.4.26',  '23.20.1', '12.4.27',  'Activity Edge', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8003]->A_E[R8001]'  );
INSERT INTO EO VALUES ( '12.4.27',  '7.1', '12.4.28',  'Message', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8003]->MSG_M[R8001]'  );
INSERT INTO EO VALUES ( '12.4.28',  '2.4.1', '12.4.29',  'Association', -1, 'many', 'Derived Association', false, false, '', false, false, '->PE_PE[R8003]->R_REL[R8001]'  );
INSERT INTO EO VALUES ( '12.4.29',  '23.23.1', '12.4.30',  'Imported Class', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8003]->O_IOBJ[R8001]'  );
INSERT INTO EO VALUES ( '12.4.30',  '1.6.4.1.1', '12.4.31',  'External Entity', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8003]->S_EE[R8001]'  );
INSERT INTO EO VALUES ( '12.4.31',  '1.7.4.1.1', '12.4.32',  'Function', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8003]->S_SYNC[R8001]'  );
INSERT INTO EO VALUES ( '12.4.32',  '10.4.1.1', '12.4.33',  'Use Case Association', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8003]->UC_UCA[R8001]'  );
INSERT INTO EO VALUES ( '12.4.33',  '23.27.1',        '12.4.34',  'Satisfaction', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8003]->C_SF[R8001]'  );
INSERT INTO EO VALUES ( '12.4.34',  '12.4.9.1.1', '12.4.35',       'Delegation', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8003]->C_DG[R8001]'  );
INSERT INTO EO VALUES ( '12.4.35',  '23.29.1', '12.4.36',  'Exception', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8003]->S_EXP[R8001]'  );
INSERT INTO EO VALUES ( '12.4.36',  '23.30.1', '',  'Proc', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8003]->PR_PROC[R8001]'  );

INSERT INTO EO VALUES ( '14.1.3.1',	'14.1.3.1.1',	  '14.1.3.2',     'Executable Property', 4003, 'many', 'first', false );
INSERT INTO EO VALUES ( '14.1.3.1.1', '', '14.1.3.1.2',     'Interface Signal', 4004, 'one', 'first', false );
INSERT INTO EO VALUES ( '14.1.3.1.2', '14.1.3.1.2.1', '14.1.3.1.3',     'Interface Operation', 4004, 'one', 'first', false );
INSERT INTO EO VALUES ( '14.1.3.1.2.1', '', '',     'Dimensions', 4018, 'many', 'first' );
INSERT INTO EO VALUES ( '14.1.3.1.3', '14.1.3.1.3.1', '',    'Property Parameter', 4006, 'many', 'first', false );
INSERT INTO EO VALUES ( '14.1.3.1.3.1', '', '',     'Dimensions', 4017, 'many', 'first' );
INSERT INTO EO VALUES ( '14.1.3.2',    '', '', 'Packageable Element', 8001, 'one', 'first', false );

INSERT INTO EO VALUES ( '1.5.3.1.1',	'',             '1.5.3.1.2', 'Core Data Type', 17, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.2', 	'1.5.3.1.2.1',             '1.5.3.1.3', 'User Data Type', 17, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.2.1', 	'',             '', 'Range', 57, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.3', 	'1.5.3.1.3.1', 	'1.5.3.1.4', 'Enumeration Data Type', 17, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.3.1', 	'',             '',          'Enumerator', 27, 'many', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.4', 	'1.5.3.1.4.1',  '1.5.3.1.5', 'Structured Data Type', 17, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.4.1', 	'1.5.3.1.4.1.1','',          'Structure Member', 44, 'many', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.4.1.1','',             '',          'Dimensions', 53, 'many', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.5', 	'',             '1.5.3.1.6',          'Instance Reference Data Type', 17, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.7.1.1',    '1.5.7.1.1.1',  '1.5.7.1.2',          'Symbolic Constant', 1504, 'many', 'first' );
INSERT INTO EO VALUES ( '1.5.7.1.1.1',  '1.5.7.1.1.1.1','',          'Leaf Symbolic Constant', 1502, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.7.1.1.1.1','',             '',          'Literal Symbolic Constant', 1503, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.6',    '', '', 'Packageable Element', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '1.5.7.1.2',    '', '', 'Packageable Element', 8001, 'one', 'first', false );

INSERT INTO EO VALUES ( '1.6.4.1.1',   '1.6.4.1.1.1', '1.6.4.1.2', 'Bridge', 19, 'many', 'first' );
INSERT INTO EO VALUES ( '1.6.4.1.1.1', '1.6.4.1.1.1.1', '1.6.4.1.1.2', 'Bridge Parameter', 21, 'many', 'first' );
INSERT INTO EO VALUES ( '1.6.4.1.1.1.1', '',          '',          'Dimensions', 49, 'many', 'first' );
INSERT INTO EO VALUES ( '1.6.4.1.1.2', '',            '',          'Dimensions', 50, 'many', 'first' );
INSERT INTO EO VALUES ( '1.6.4.1.2',   '',            '1.6.4.1.3', 'External Entity Event Data Item', 12, 'many', 'first' );
INSERT INTO EO VALUES ( '1.6.4.1.3',   '1.6.4.1.3.1', '1.6.4.1.4', 'External Entity Event', 10, 'many', 'first' );
INSERT INTO EO VALUES ( '1.6.4.1.3.1', '',            '',          'External Entity Event Data', 13, 'many', 'first' );
INSERT INTO EO VALUES ( '1.6.4.1.4',   '',            '1.6.4.1.5',          'External Entity Data Item', 11, 'many', 'first' );
INSERT INTO EO VALUES ( '1.6.4.1.5',    '', '', 'Packageable Element', 8001, 'one', 'first', false );

INSERT INTO EO VALUES ( '1.7.4.1',   '1.7.4.1.1',  '',	      'Function', 31, 'one', 'first' );
INSERT INTO EO VALUES ( '1.7.4.1.1', '1.7.4.1.1.1','1.7.4.1.2', 'Function Parameter', 24, 'many', 'first' );
INSERT INTO EO VALUES ( '1.7.4.1.1.1', '',         '',        'Dimensions', 52, 'many', 'first' );
INSERT INTO EO VALUES ( '1.7.4.1.2', '',           '1.7.4.1.3',        'Dimensions', 51, 'many', 'first' );
INSERT INTO EO VALUES ( '1.7.4.1.3',    '', '', 'Packageable Element', 8001, 'one', 'first', false );

INSERT INTO EO VALUES ( '1.14.2.1',     '1.14.2.1.1',   '1.14.2.2',	    'Action Node', 1105, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.1.1',   '',             '1.14.2.1.2',	'Activity Diagram Action', 1107, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.1.2',   '1.14.2.1.2.1',	'1.14.2.1.3',	'Accept Event', 1107, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.1.2.1', '',             '1.14.2.1.2.2', 'Accept Event Action', 1112, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.1.2.2', '',             '',             'Accept Time Event Action', 1112, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.1.3',   '',             '',             'Send Signal', 1107, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.2',     '',             '1.14.2.3',     'Object Node', 1105, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.3',     '1.14.2.3.1',	'1.14.2.4',             'Control Node', 1105, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.3.1',   '',             '1.14.2.3.2',   'Initial Node', 1106, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.3.2',   '',             '1.14.2.3.3',   'Activity Final Node', 1106, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.3.3',   '',             '1.14.2.3.4',   'Flow Final Node', 1106, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.3.4',   '',             '1.14.2.3.5',   'Decision Merge Node', 1106, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.3.5',   '',             '',             'Fork Join Node', 1106, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.4',    '', '', 'Packageable Element', 8001, 'one', 'first', false );

INSERT INTO EO VALUES ( '2.1.1',	'2.1.1.1',	'2.1.2',	'Operation', 115, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.1.1',	'2.1.1.1.1','2.1.1.2', 	'Operation Parameter', 117, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.1.1.1','',         '',         'Dimensions', 121, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.1.2',  '', '',     'Dimensions', 122, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.2',	'2.1.2.1',	'2.1.3',	'Attribute', 102, 'many', 'last' );
INSERT INTO EO VALUES ( '2.1.2.1',	'2.1.2.1.1','2.1.2.2',	'Base Attribute', 106, 'one', 'last' );
INSERT INTO EO VALUES ( '2.1.2.1.1','',	'2.1.2.1.2','Derived Base Attribute', 107, 'one', 'first' );
INSERT INTO EO VALUES ( '2.1.2.1.2','',			'',			'New Base Attribute', 107, 'one', 'first' );
INSERT INTO EO VALUES ( '2.1.2.2',	'2.1.2.2.1','2.1.2.3',	'Referential Attribute', 106, 'one', 'last' );
INSERT INTO EO VALUES ( '2.1.2.2.1','',			'',			'Attribute Reference in Class', 108, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.2.3',  '',         '',         'Dimensions', 120, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.3',	'2.1.3.1',	'2.1.4',	'Class Identifier', 104, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.3.1',	'',			'',			'Class Identifier Attribute', 105, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.4',	'3',		'2.1.5',	'Instance State Machine', 518, 'one', 'first',true );
INSERT INTO EO VALUES ( '2.1.5',	'3',		'2.1.6',         'Class State Machine', 519, 'one', 'first',true );
INSERT INTO EO VALUES ( '2.1.6',    '', '', 'Packageable Element', 8001, 'one', 'first', false );

INSERT INTO EO VALUES ( '2.4.1',	'',			'2.4.2',	'Simple Association', 206, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.2',	'',			'2.4.3',	'Linked Association', 206, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.3',	'',			'2.4.4',	'Subtype Supertype Association', 206, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.4',	'',			'2.4.5',	'Derived Association', 206, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.5',	'2.4.5.1',	'2.4.6',         'Class In Association', 201, 'many', 'last' );
INSERT INTO EO VALUES ( '2.4.5.1',	'2.4.5.1.1','2.4.5.2',	'Referred To Class in Assoc', 203, 'one', 'last' );
INSERT INTO EO VALUES ( '2.4.5.1.1','',			'2.4.5.1.2','Class As Simple Participant', 204, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.5.1.2','',			'2.4.5.1.3','Class As Associated One Side', 204, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.5.1.3','',			'2.4.5.1.4','Class As Associated Other Side', 204, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.5.1.4','',			'2.4.5.1.5','Class As Supertype', 204, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.5.1.5','',			'',			'Referred To Identifier Attribute', 110, 'many', 'first' );
INSERT INTO EO VALUES ( '2.4.5.2',	'2.4.5.2.1','2.4.5.3',	'Referring Class In Assoc', 203, 'one', 'last' );
INSERT INTO EO VALUES ( '2.4.5.2.1','',			'2.4.5.2.2','Class As Simple Formalizer', 205, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.5.2.2','',			'2.4.5.2.3','Class As Link', 205, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.5.2.3','',			'',         'Class As Subtype', 205, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.5.3',	'',			'2.4.5.4',	'Class As Derived One Side', 203, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.5.4',	'',			'',			'Class As Derived Other Side', 203, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.6',    '', '', 'Packageable Element', 8001, 'one', 'first', false );

INSERT INTO EO VALUES ( '3',	'3.1',	'', 'State Machine', 517, 'one', 'first' );
INSERT INTO EO VALUES ( '3.1',	'',		'3.2', 'Moore State Machine', 510, 'one', 'first' );
INSERT INTO EO VALUES ( '3.2',	'',		'3.3', 'Mealy State Machine', 510, 'one', 'first' );
INSERT INTO EO VALUES ( '3.3',	'3.3.1',		'3.5', 'State Machine Event Data Item', 516, 'many', 'first' );
INSERT INTO EO VALUES ( '3.3.1',''     ,   '',  'Dimensions', 531, 'many', 'first' );
INSERT INTO EO VALUES ( '3.5',	'3.5.1','3.6', 'State Machine Event', 502, 'many', 'last' );
INSERT INTO EO VALUES ( '3.6',	'3.6.1','3.7', 'State Machine State', 501, 'many', 'first' );
INSERT INTO EO VALUES ( '3.7',	'3.7.1','3.8', 'Transition', 505, 'many', 'last' );
INSERT INTO EO VALUES ( '3.8',	'3.8.1','3.9', 'Action', 515, 'many', 'last' );
INSERT INTO EO VALUES ( '3.9',	'',		'3.10', 'ISM', 0, '', 'first' );
INSERT INTO EO VALUES ( '3.10',	'',		'', 	'ASM', 0, '', 'first' );

INSERT INTO EO VALUES ( '3.5.1','',		'3.5.2',	'Polymorphic Event', 525, 'one', 'last' );
INSERT INTO EO VALUES ( '3.5.2','3.5.2.1','',	'SEM Event', 525, 'one', 'last' );
INSERT INTO EO VALUES ( '3.5.2.1','',	'3.5.2.2',	'Local Event', 526, 'one', 'first' );
INSERT INTO EO VALUES ( '3.5.2.2','',	'3.5.2.3',	'Non Local Event', 526, 'one', 'first' );
INSERT INTO EO VALUES ( '3.5.2.3','',	'',	        'Signal Event', 526, 'one', 'first' );

INSERT INTO EO VALUES ( '3.6.1','3.6.1.1',	'',		'State Event Matrix Entry', 503, 'many', 'last' );
INSERT INTO EO VALUES ( '3.6.1.1','',	'3.6.1.2',	'Event Ignored', 504, 'one', 'first' );
INSERT INTO EO VALUES ( '3.6.1.2','',	'', 		'Cant Happen', 504, 'one', 'first' );

INSERT INTO EO VALUES ( '3.7.1','',		'3.7.2', 	'No Event Transition', 507, 'one', 'first' );
INSERT INTO EO VALUES ( '3.7.2','',		'3.7.3', 	'New State Transition', 507, 'one', 'first' );
INSERT INTO EO VALUES ( '3.7.3','',		'', 		'Creation Transition', 507, 'one', 'first' );

INSERT INTO EO VALUES ( '3.8.1',   '3.8.1.1', '',        'Action Home', 514, 'one', 'last' );
INSERT INTO EO VALUES ( '3.8.1.1', '',        '3.8.1.2', 'Moore Action Home', 513, 'one', 'first' );
INSERT INTO EO VALUES ( '3.8.1.2', '',        '3.8.1.3', 'Mealy Action Home', 513, 'one', 'first' );
INSERT INTO EO VALUES ( '3.8.1.3', '',        '',        'Transition Action Home', 513, 'one', 'first' );

-- Graphics
INSERT INTO EO VALUES ( '4', 		 '4.1',       '',        'Model', 0, '', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '4.1',		 '4.1.1',     '4.2',     'Graphical Element', 1, 'many', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '4.1.1',	 '4.1.1.1',   '4.1.2',        'Connector', 2, 'one', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '4.1.1.1',	 '5',         '4.1.2.2', 'Floating Text', 8, 'many', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '4.1.2',	 '4.1.2.1',   '4.1.3',   'Shape', 2, 'one', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '4.1.3',	 	'4.1.3.1',			'',		'ElementStyle', 401, 'many', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '4.1.3.1',	 	'',			'4.1.3.2',		'FillColorStyle', 400, 'one', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '4.1.3.2',	 	'',			'4.1.3.3',		'FontStyle', 400, 'one', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '4.1.3.3',	 	'',			'',		'LineColorStyle', 400, 'one', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '4.1.2.1', 		'',		'4.1.2.3',		'Containing Shape', 28, 'one', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '4.1.2.3', 		'',		'4.1.2.4',		'NonContaining Shape', 28, 'one', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '4.1.2.4',	 '5',         '5',       'Floating Text', 27, 'one', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '4.1.2.2', 	 '4.1.2.2.1', '5.4',     'Line Segment', 6, 'many', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '4.1.2.2.1', '',          '',        'Anchor On Segment', 26, 'many', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '4.2',		 '',          '4.3',     'Diagram', 18, 'one', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '4.3',	     '4.3.1',     '4.4',        'Element In Suppression', 32, 'many', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '4.3.1',	 '',          '',        'Graphical Element', 32, 'many', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '4.4',	     '4.1.3.1',     '4.5',        'ElementStyle', 402, 'many', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '4.5',	     '4.5.1',     '',        'Layer', 34, 'many', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '4.5.1',	 '',     '',        'GraphicalElementInLayer', 35, 'many', 'first', false, false, '', true, false );

INSERT INTO EO VALUES ( '5', 		'5.1',		'5.1.1',		'GraphNode', 19, 'one', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '5.1',		'5.2',		'',	    'GraphElement', 301, 'one', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '5.2',		'',			'5.3',	'DiagramElement', 302, 'one', 'first', false, false, '', true, false );
INSERT INTO EO VALUES ( '5.3',	 	'',			'',		'GraphConnector', 311, 'many', 'first', false, false, '', true, false );

INSERT INTO EO VALUES ( '5.4', 		'5.4.1',	'', 	'GraphEdge', 20, 'one', 'last', false, false, '', true, false );
INSERT INTO EO VALUES ( '5.4.1', 	'',			'5.1',	'Waypoint', 319, 'many', 'first', false, false, '', true, false );

INSERT INTO EO VALUES ( '6.2.1',	'',	   '6.2.3',	  'External Entity Participant', 930, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.3', 	'6.2.3.1',			'6.2.4',	'Class Participant', 930, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.3.1', 	'6.2.3.1.1',			'',	'Class Participant Attribute', 935, 'many', 'first' );
INSERT INTO EO VALUES ( '6.2.3.1.1', 	'',			'6.2.3.1.2',	'Informal Attribute', 947, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.3.1.2', 	'',			'',	'Formal Attribute', 947, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.4', 	'6.2.4.1',			'6.2.5',	'Class Instance Participant', 930, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.4.1', 	'6.2.4.1.1',			'6.2.4.2',	'Instance Attribute Value', 936, 'many', 'first' );
INSERT INTO EO VALUES ( '6.2.4.2', 	'',			'',	'Instance Attribute Value', 937, 'many', 'first' );
INSERT INTO EO VALUES ( '6.2.4.1.1', 	'',			'6.2.4.2.1',	      'Informal Attribute Value', 948, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.4.2.1', 	'',			'',	      'Formal Attribute Value', 948, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.5', 	'',			'6.2.6',	      			  'Actor Participant', 930, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.6',	'',  '6.2.7',	  'Communication Link', 1133, 'many', 'first' );
INSERT INTO EO VALUES ( '6.2.7', '',  '6.2.8',	  'Use Case Participant', 930, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.8', '',  '6.2.9',	  'Component Participant', 930, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.9', 	'6.2.9.1',			'6.2.10',	      'Lifespan', 930, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.9.1', 	'6.2.9.1.1',			'',	      'Timing Mark', 931, 'many', 'first' );
INSERT INTO EO VALUES ( '6.2.9.1.1', 	'',			'',	      'Time Span', 941, 'many', 'first' );
INSERT INTO EO VALUES ( '6.2.10', 	'',			'6.2.11',	      'Package Participant', 930, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.11',    '', '', 'Packageable Element', 8001, 'one', 'first', false );

INSERT INTO EO VALUES ( '7.1', 	'',			'7.2',	      'Return Message', 1018, 'many', 'first' );
INSERT INTO EO VALUES ( '7.2', 	'7.2.1',			'7.3',	      'Synchronous Message', 1018, 'many', 'first' );
INSERT INTO EO VALUES ( '7.2.1', 	'',			'7.2.2',	      'Informal Synchronous Message', 1020, 'one', 'first' );
INSERT INTO EO VALUES ( '7.2.2', 	'',			'7.2.3',	      'Function Message', 1020, 'one', 'first' );
INSERT INTO EO VALUES ( '7.2.3', 	'',			'7.2.4',	      'Operation Message', 1020, 'one', 'first' );
INSERT INTO EO VALUES ( '7.2.4', 	'',			'7.2.5',      'Bridge Message', 1020, 'one', 'first' );
INSERT INTO EO VALUES ( '7.2.5', 	'',			'',	              'Interface Operation Message', 1020, 'one', 'first' );
INSERT INTO EO VALUES ( '7.3', 	'7.3.1',			'7.4',	      'Asynchronous Message', 1018, 'many', 'first' );
INSERT INTO EO VALUES ( '7.3.1', 	'',			'7.3.2',	      'Event Message', 1019, 'one', 'first' );
INSERT INTO EO VALUES ( '7.3.2', 	'',			'7.3.3',	      'Informal Asynchronous Message', 1019, 'one', 'first' );
INSERT INTO EO VALUES ( '7.3.3', 	'',			'',               'Signal Message', 1019, 'one', 'first' );
INSERT INTO EO VALUES ( '7.4', 	'7.4.1',			'7.5', 			  'Message Argument', 1000, 'many', 'first' );
INSERT INTO EO VALUES ( '7.5', 	'7.4.1',			'7.6',	      			  'Message Argument', 1001, 'many', 'first' );
INSERT INTO EO VALUES ( '7.4.1', 	'',			'7.4.2',	      			  'Bridge Argument', 1013, 'one', 'first' );
INSERT INTO EO VALUES ( '7.4.2', 	'',			'7.4.3',	      			  'Operation Argument', 1013, 'one', 'first' );
INSERT INTO EO VALUES ( '7.4.3', 	'',			'7.4.4',	      			  'Function Argument', 1013, 'one', 'first' );
INSERT INTO EO VALUES ( '7.4.4', 	'',			'7.4.5',	      			  'Event Argument', 1013, 'one', 'first' );
INSERT INTO EO VALUES ( '7.4.5', 	'',			'7.4.6',			'Informal Argument', 1013, 'one', 'first' );
INSERT INTO EO VALUES ( '7.4.6', 	'',			'', 				'Executable Property Argument', 1013, 'one', 'first' );
INSERT INTO EO VALUES ( '7.6',    '', '', 'Packageable Element', 8001, 'one', 'first', false );

INSERT INTO EO VALUES ( '8.2', '6.2.1',  '',	  'Interaction Participant', 929, 'many', 'first' );
-- TODO: BOB Fixme - Message in Sequence is not being persisted in GPs
-- INSERT INTO EO VALUES ( '8.4', 	'8.4.1',			'',	      'Message In Sequence', 953, 'many', 'first' );
-- INSERT INTO EO VALUES ( '8.4.1', 	'7.1',			'',	      'Message', 954, 'one', 'first' );

--TODO: BOB Fixme - The following entry was not persisted via GP
--   INSERT INTO EO VALUES ( '10.4', 	'10.4.1',	   '',		'Association In Use Case', 1214, 'many', 'first' );
--INSERT INTO EO VALUES ( '10.4.1', 	'10.4.1.1',	   '',		'Use Case Association', 1215, 'one', 'first' );
INSERT INTO EO VALUES ( '10.4.1.1', 	'',	   '10.4.1.2',		'Binary Association', 1210, 'one', 'first' );
INSERT INTO EO VALUES ( '10.4.1.2', 	'',	   '10.4.1.3',		'Generalization', 1210, 'one', 'first' );
INSERT INTO EO VALUES ( '10.4.1.3', 	'',	   '10.4.1.4',		'Include', 1210, 'one', 'first' );
INSERT INTO EO VALUES ( '10.4.1.4', 	'',	   '10.4.1.5',		'Extend', 1210, 'one', 'first' );
INSERT INTO EO VALUES ( '10.4.1.5',    '', '', 'Packageable Element', 8001, 'one', 'first', false );

INSERT INTO EI VALUES ( 'ISM' );
INSERT INTO GD VALUES ( 'ISM', 'InstanceStateChartDiagram', 'State Machine', 'SM_ID' );
INSERT INTO EI VALUES ( 'ASM' );
INSERT INTO GD VALUES ( 'ASM', 'ClassStateChartDiagram', 'State Machine', 'SM_ID' );
INSERT INTO EI VALUES ( 'SystemModelPackage' );
INSERT INTO GD VALUES ( 'SystemModelPackage', 'SystemModelPackage', 'System Model', 'Sys_ID' );
INSERT INTO EI VALUES ( 'ComponentDiagram' );
INSERT INTO GD VALUES ( 'ComponentDiagram', 'ComponentDiagram', 'Component', 'Id' );
INSERT INTO EI VALUES ( 'PackageDiagram' );
INSERT INTO GD VALUES ( 'PackageDiagram', 'Package', 'Package', 'Package_ID' );
