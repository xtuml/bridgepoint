--======================================================================
--
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

INSERT INTO EO VALUES ( '6',	'13',	  '',     'System Model',0 , '', 'first', true );
INSERT INTO EO VALUES ( '13',	'',	  '24',     'SystemModelPackage',0 , '', 'first', false );
INSERT INTO EO VALUES ( '24', '24.1', '', 'Package', 1401, 'many', 'first', true );

INSERT INTO EO VALUES ( '24.1',       '',          '24.10', 'PackageDiagram', 0, '', 'first', false );
INSERT INTO EO VALUES ( '24.10',    '24.10.1', '24.11', 'Packageable Element', 8000, 'many', 'first', false );
INSERT INTO EO VALUES ( '24.10.1',  '1.5.3.1.1', '24.10.2', 'Data Type', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '24.10.2',  '6.2.1', '24.10.3',     'Interaction Participant', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '24.10.3',  '1.14.2.1', '24.10.4',       'Activity Node', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '24.10.4',  '2.1.1', '24.10.5',          'Model Class', 8001, 'one', 'first', true );
INSERT INTO EO VALUES ( '24.10.5',  '12.4.1', '24.10.6',       'Component', 8001, 'one', 'first', true );
INSERT INTO EO VALUES ( '24.10.6',  '12.4.8.1', '24.10.7',       'Component Reference', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '24.10.7',  '14.1.3.1', '24.10.8',       'Interface', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '24.10.8',  '', '24.10.9',                  'Package', 8001, 'one', 'none', true );
INSERT INTO EO VALUES ( '24.10.9',  '1.5.6.1.1', '24.10.10',  'Constant Specification', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '24.10.10',  '', '24.10.11',  'Activity Partition', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '24.10.11',  '', '24.10.12',  'Activity Edge', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '24.10.12',  '7.1', '24.10.13',  'Message', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '24.10.13',  '2.4.1', '24.10.14',  'Association', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '24.10.14',  '', '24.10.15',  'Imported Class', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '24.10.15',  '1.6.4.1.1', '24.10.16',  'External Entity', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '24.10.16',  '1.7.4.1.1', '24.10.17',  'Function', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '24.10.17',  '6.2.8.1', '24.10.18',  'Use Case Association', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '24.10.18',  '',        '24.10.19',  'Satisfaction', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '24.10.19',  '12.4.9.1.1', '24.10.20',       'Delegation', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '24.10.20',  '', '24.10.21',               'Exception', 8001, 'one', 'first', true );
INSERT INTO EO VALUES ( '24.10.21',  '24.10.21.1', '',                       'Deployment', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '24.11',  '', '',  'Package Reference', 1402, 'one', 'first', false, false, 'refers to', false, false, ''  );

INSERT INTO EO VALUES ( '24.10.21.1', '24.10.21.1.1', '', 'Terminator', 1650, 'many', 'first', false );
INSERT INTO EO VALUES ( '24.10.21.1.1', '24.10.21.1.1.1', '', 'Terminator Service', 1651, 'many', 'first', false );
INSERT INTO EO VALUES ( '24.10.21.1.1.1', '24.10.21.1.1.1.1', '24.10.21.1.1.2', 'Terminator Service Parameter', 1652, 'many', 'first', false );
INSERT INTO EO VALUES ( '24.10.21.1.1.1.1', '', '', 'Dimensions', 1655, 'many', 'first' );
INSERT INTO EO VALUES ( '24.10.21.1.1.2', '', '', 'Dimensions', 1657, 'many', 'first' );

INSERT INTO EO VALUES ( '12.4.1',       '',    '12.4.3',     'ComponentDiagram', 0, '', 'first', false );
INSERT INTO EO VALUES ( '12.4.3',     '12.4.3.1',  '12.4.9',      'Port', 4010, 'many', 'first', false );             
INSERT INTO EO VALUES ( '12.4.3.1',     '12.4.3.1.1',  '',      'Interface Reference', 4016, 'many', 'first', false );             
INSERT INTO EO VALUES ( '12.4.3.1.1',   '12.4.3.1.1.1',          '12.4.3.1.2', 'Requirement', 4009, 'one', 'first', false );
INSERT INTO EO VALUES ( '12.4.3.1.1.1', '12.4.3.1.1.1.1', '',     'Required Executable Property', 4500, 'many', 'first', false );
INSERT INTO EO VALUES ( '12.4.3.1.1.1.1', '12.4.3.1.1.1.1.1', '12.4.3.1.1.1.2',     'Required Signal', 4502, 'one', 'first', false );
INSERT INTO EO VALUES ( '12.4.3.1.1.1.1.1', '15',          '',          'Required Signal Body', 684, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '12.4.3.1.1.1.2', '12.4.3.1.1.1.2.1', '',     'Required Operation', 4502, 'one', 'first', false );
INSERT INTO EO VALUES ( '12.4.3.1.1.1.2.1', '15',          '',          'Required Operation Body', 685, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '12.4.3.1.2',   '12.4.3.1.2.1','', 'Provision', 4009, 'one', 'first', false );
INSERT INTO EO VALUES ( '12.4.3.1.2.1', '12.4.3.1.2.1.1', '',     'Provided Executable Property', 4501, 'many', 'first', false );
INSERT INTO EO VALUES ( '12.4.3.1.2.1.1', '12.4.3.1.2.1.1.1', '12.4.3.1.2.1.2',     'Provided Signal', 4503, 'one', 'first', false );
INSERT INTO EO VALUES ( '12.4.3.1.2.1.1.1', '15',          '',          'Provided Signal Body', 686, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '12.4.3.1.2.1.2', '12.4.3.1.2.1.2.1', '',     'Provided Operation', 4503, 'one', 'first', false );
INSERT INTO EO VALUES ( '12.4.3.1.2.1.2.1', '15',          '',          'Provided Operation Body', 687, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '12.4.8.1',       '12.4.8.1.1',    '', 'Port Reference', 4707, 'many', 'first', false );
INSERT INTO EO VALUES ( '12.4.8.1.1',       '12.4.8.1.1.1',    '', 'Imported Reference', 4708, 'many', 'first', false );
INSERT INTO EO VALUES ( '12.4.8.1.1.1',       '12.4.8.1.1.1.1',    '12.4.8.1.1.2', 'Imported Provision', 4703, 'one', 'first', false );
INSERT INTO EO VALUES ( '12.4.8.1.1.1.1',       '',    '', 'Imported Provision In Satisfaction', 4705, 'many', 'first', false );
INSERT INTO EO VALUES ( '12.4.8.1.1.2',       '',    '', 'Imported Requirement', 4703, 'one', 'first', false );
INSERT INTO EO VALUES ( '12.4.9',   '12.4.9.1', '12.4.10',     'Delegation In Component', 9002, 'many', 'first', false );
INSERT INTO EO VALUES ( '12.4.9.1',   '12.4.9.1.1', '',     'Delegation', 9002, 'many', 'first', false );
INSERT INTO EO VALUES ( '12.4.9.1.1',   '', '',     'Interface Reference In Delegation', 4013, 'many', 'first', false );
INSERT INTO EO VALUES ( '12.4.10', '12.4.10.1',          '',  'Satisfaction In Component', 9000, 'many', 'first', false );
INSERT INTO EO VALUES ( '12.4.10.1', '',          '',  'Satisfaction', 9000, 'many', 'none', false );

INSERT INTO EO VALUES ( '14.1.3.1', '14.1.3.1.1', '',     'Executable Property', 4003, 'many', 'first', false );
INSERT INTO EO VALUES ( '14.1.3.1.1', '', '14.1.3.1.2',     'Interface Signal', 4004, 'one', 'first', false );
INSERT INTO EO VALUES ( '14.1.3.1.2', '14.1.3.1.2.1', '14.1.3.1.3',     'Interface Operation', 4004, 'one', 'first', false );
INSERT INTO EO VALUES ( '14.1.3.1.2.1', '', '',     'Dimensions', 4018, 'many', 'first' );
INSERT INTO EO VALUES ( '14.1.3.1.3', '14.1.3.1.3.1', '',    'Property Parameter', 4006, 'many', 'first', false );
INSERT INTO EO VALUES ( '14.1.3.1.3.1', '', '',     'Dimensions', 4017, 'many', 'first' );

INSERT INTO EO VALUES ( '1.5.3.1.1',	'',             '1.5.3.1.2', 'Core Data Type', 17, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.2', 	'1.5.3.1.2.1',             '1.5.3.1.3', 'User Data Type', 17, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.2.1', 	'',             '', 'Range', 57, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.3', 	'1.5.3.1.3.1', 	'1.5.3.1.4',          'Enumeration Data Type', 17, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.3.1', 	'',             '',          'Enumerator', 27, 'many', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.4', 	'1.5.3.1.4.1',  '1.5.3.1.5', 'Structured Data Type', 17, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.4.1', 	'1.5.3.1.4.1.1','',          'Structure Member', 44, 'many', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.4.1.1','',             '',          'Dimensions', 53, 'many', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.5', 	'',             '',          'Instance Reference Data Type', 17, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.6.1.1',    '1.5.6.1.1.1',  '',          'Symbolic Constant', 1504, 'many', 'first' );
INSERT INTO EO VALUES ( '1.5.6.1.1.1',  '1.5.6.1.1.1.1','',          'Leaf Symbolic Constant', 1502, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.6.1.1.1.1','',             '',          'Literal Symbolic Constant', 1503, 'one', 'first' );

INSERT INTO EO VALUES ( '1.6.4.1.1',   '1.6.4.1.1.1', '', 'Bridge', 19, 'many', 'first' );
INSERT INTO EO VALUES ( '1.6.4.1.1.1', '1.6.4.1.1.1.1', '1.6.4.1.1.2', 'Bridge Parameter', 21, 'many', 'first' );
INSERT INTO EO VALUES ( '1.6.4.1.1.1.1', '',          '',          'Dimensions', 49, 'many', 'first' );
INSERT INTO EO VALUES ( '1.6.4.1.1.2', '15',          '1.6.4.1.1.3', 'Bridge Body', 697, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '1.6.4.1.1.3', '',            '',          'Dimensions', 50, 'many', 'first' );

INSERT INTO EO VALUES ( '1.7.4.1',   '1.7.4.1.1',  '',	      'Function', 31, 'one', 'first' );
INSERT INTO EO VALUES ( '1.7.4.1.1', '1.7.4.1.1.1','1.7.4.1.2', 'Function Parameter', 24, 'many', 'first' );
INSERT INTO EO VALUES ( '1.7.4.1.1.1', '',            '',        'Dimensions', 52, 'many', 'first' );
INSERT INTO EO VALUES ( '1.7.4.1.2', '15',         '1.7.4.1.3', 'Function Body', 695, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '1.7.4.1.3', '',            '',        'Dimensions', 51, 'many', 'first' );

INSERT INTO EO VALUES ( '1.14.2.1',     '1.14.2.1.1',   '1.14.2.2',	    'Action Node', 1105, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.1.1',   '',             '1.14.2.1.2',	'Activity Diagram Action', 1107, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.1.2',   '1.14.2.1.2.1',	'1.14.2.1.3',	'Accept Event', 1107, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.1.2.1', '',             '1.14.2.1.2.2', 'Accept Event Action', 1112, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.1.2.2', '',             '',             'Accept Time Event Action', 1112, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.1.3',   '',             '',             'Send Signal', 1107, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.2',     '',             '1.14.2.3',     'Object Node', 1105, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.3',     '1.14.2.3.1',	'',             'Control Node', 1105, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.3.1',   '',             '1.14.2.3.2',   'Initial Node', 1106, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.3.2',   '',             '1.14.2.3.3',   'Activity Final Node', 1106, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.3.3',   '',             '1.14.2.3.4',   'Flow Final Node', 1106, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.3.4',   '',             '1.14.2.3.5',   'Decision Merge Node', 1106, 'one', 'first' );
INSERT INTO EO VALUES ( '1.14.2.3.5',   '',             '',             'Fork Join Node', 1106, 'one', 'first' );

INSERT INTO EO VALUES ( '2.1',	  '2.1.1',  '2.2',	  'Model Class', 2, 'many', 'first', false );
INSERT INTO EO VALUES ( '2.2',	  '',		'2.3',	  'Imported Class', 3, 'many', 'first' );
INSERT INTO EO VALUES ( '2.3',	  '',		'2.4',	  'External Entity in Model', 7, 'many', 'first' );
INSERT INTO EO VALUES ( '2.4',	  '2.4.1',  '',	  'Association', 4, 'many', 'Derived Association' );

INSERT INTO EO VALUES ( '2.1.1',	'2.1.1.1',	'2.1.2',	'Operation', 115, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.1.1',	'2.1.1.1.1','2.1.1.2', 	'Operation Parameter', 117, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.1.1.1','',         '',         'Dimensions', 121, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.1.2',  '15',       '2.1.1.3',  'Operation Body', 696, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '2.1.1.3',  '', '',     'Dimensions', 122, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.2',	'2.1.2.1',	'2.1.3',	'Attribute', 102, 'many', 'last' );
INSERT INTO EO VALUES ( '2.1.2.1',	'2.1.2.1.1','2.1.2.2',	'Base Attribute', 106, 'one', 'last' );
INSERT INTO EO VALUES ( '2.1.2.1.1','2.1.2.1.1.1',	'2.1.2.1.2','Derived Base Attribute', 107, 'one', 'first' );
INSERT INTO EO VALUES ( '2.1.2.1.1.1',       '15',     '',  'Derived Attribute Body', 693, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '2.1.2.1.2','',			'',			'New Base Attribute', 107, 'one', 'first' );
INSERT INTO EO VALUES ( '2.1.2.2',	'2.1.2.2.1','2.1.2.3',	'Referential Attribute', 106, 'one', 'last' );
INSERT INTO EO VALUES ( '2.1.2.2.1','',			'',			'Attribute Reference in Class', 108, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.2.3',  '',         '',         'Dimensions', 120, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.3',	'2.1.3.1',	'2.1.4',	'Class Identifier', 104, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.3.1',	'',			'',			'Class Identifier Attribute', 105, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.4',	'3',		'2.1.5',	'Instance State Machine', 518, 'one', 'first',false );
INSERT INTO EO VALUES ( '2.1.5',	'3',		'2.1.6',    'Class State Machine', 519, 'one', 'first',false );

INSERT INTO EO VALUES ( '2.1.6',	 '2.1.6.1',   '',	      'Class In Engine', 2961, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.6.1',	 '2.1.6.1.1',   '',	      'Instance', 2962, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.6.1.1',	 '',	      '2.1.6.1.2',  'Attribute Value', 2909, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.6.1.2',	 '2.1.6.1.2.1', '',	      'Link Participation', 2958, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.6.1.2.1', '',	      '2.1.6.1.2.2','Link', 2901, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.6.1.2.2', '',	      '2.1.6.1.2.3','Link', 2902, 'many', 'none' );
INSERT INTO EO VALUES ( '2.1.6.1.2.3', '',	      '',	      'Link', 2903, 'many', 'none' );


INSERT INTO EO VALUES ( '2.4.1',	'',			'2.4.2',	'Simple Association', 206, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.2',	'',			'2.4.3',	'Linked Association', 206, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.3',	'',			'2.4.4',	'Subtype Supertype Association', 206, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.4',	'',			'2.4.5',	'Derived Association', 206, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.5',	'2.4.5.1',	'',			'Class In Association', 201, 'many', 'last' );

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

INSERT INTO EO VALUES ( '3',	'3.1',	'', 'State Machine', 517, 'one', 'first' );
INSERT INTO EO VALUES ( '3.1',	'',		'3.2', 'Moore State Machine', 510, 'one', 'first' );
INSERT INTO EO VALUES ( '3.2',	'',		'3.3', 'Mealy State Machine', 510, 'one', 'first' );
INSERT INTO EO VALUES ( '3.3',	'3.3.1',		'3.5', 'State Machine Event Data Item', 516, 'many', 'first' );
INSERT INTO EO VALUES ( '3.3.1',''     ,   '',  'Dimensions', 531, 'many', 'first' );
INSERT INTO EO VALUES ( '3.5',	'3.5.1','3.6', 'State Machine Event', 502, 'many', 'last' );
INSERT INTO EO VALUES ( '3.6',	'3.6.1','3.7', 'State Machine State', 501, 'many', 'first' );
INSERT INTO EO VALUES ( '3.7',	'3.7.1','3.8', 'Transition', 505, 'many', 'last' );
INSERT INTO EO VALUES ( '3.8',	'',     '3.9', 'ISM', 0, '', 'first' );
INSERT INTO EO VALUES ( '3.9',	'',		'', 	'ASM', 0, '', 'first' );


INSERT INTO EO VALUES ( '3.5.1','',		'3.5.2',	'Polymorphic Event', 525, 'one', 'last' );
INSERT INTO EO VALUES ( '3.5.2','3.5.2.1','',		'SEM Event', 525, 'one', 'last' );
INSERT INTO EO VALUES ( '3.5.2.1','',	'3.5.2.2',	'Local Event', 526, 'one', 'first' );
INSERT INTO EO VALUES ( '3.5.2.2','',	'3.5.2.3',			'Non Local Event', 526, 'one', 'first' );
INSERT INTO EO VALUES ( '3.5.2.3','',	'',	        'Signal Event', 526, 'one', 'first' );

INSERT INTO EO VALUES ( '3.6.1','3.6.1.1',	'3.6.2',		'State Event Matrix Entry', 503, 'many', 'last' );
INSERT INTO EO VALUES ( '3.6.1.1','',	'3.6.1.2',	'Event Ignored', 504, 'one', 'first' );
INSERT INTO EO VALUES ( '3.6.1.2','',	'', 		'Cant Happen', 504, 'one', 'first' );
INSERT INTO EO VALUES ( '3.6.2', '3.6.2.1',        '', 'Moore Action Home', 511, 'one', 'first' );
INSERT INTO EO VALUES ( '3.6.2.1', '3.6.2.1.1',        '', 'Action Home', 513, 'one', 'first' );
INSERT INTO EO VALUES ( '3.6.2.1.1', '3.6.2.1.1.1',        '', 'Action', 514, 'one', 'first' );
INSERT INTO EO VALUES ( '3.6.2.1.1.1',   '15', '3.6.2.1.1.2',      'State Action Body', 691, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '3.6.2.1.1.2',   '15',          '', 'Transition Action Body', 688, 'one', 'first', false, false, '', false, true );

INSERT INTO EO VALUES ( '3.7.1','',		'3.7.2', 	'No Event Transition', 507, 'one', 'first' );
INSERT INTO EO VALUES ( '3.7.2','',		'3.7.3', 	'New State Transition', 507, 'one', 'first' );
INSERT INTO EO VALUES ( '3.7.3','',		'3.7.4',	'Creation Transition', 507, 'one', 'first' );
INSERT INTO EO VALUES ( '3.7.4','3.6.2.1',		        '', 'Transition Action Home', 530, 'one', 'first' );

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
INSERT INTO EO VALUES ( '6.2.4.2', 	'',			'6.2.4.3',	'Instance Attribute Value', 937, 'many', 'first' );
INSERT INTO EO VALUES ( '6.2.4.1.1', 	'',			'6.2.4.2.1',	      'Informal Attribute Value', 948, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.4.2.1', 	'',			'',	      'Formal Attribute Value', 948, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.5', 	'',			'6.2.6',	      			  'Actor Participant', 930, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.6',	'',  '6.2.7',	  'Communication Link', 1133, 'many', 'first' );
INSERT INTO EO VALUES ( '6.2.7', 	'7.1',			'6.2.8',	      'Message', 1007, 'many', 'first' );
INSERT INTO EO VALUES ( '6.2.8', 	'6.2.8.1',	   '6.2.9',		'Use Case Association', 1206, 'many', 'first' );
INSERT INTO EO VALUES ( '6.2.8.1', 	'',	   '6.2.8.2',		'Binary Association', 1210, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.8.2', 	'',	   '6.2.8.3',		'Generalization', 1210, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.8.3', 	'',	   '6.2.8.4',		'Include', 1210, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.8.4', 	'',	   '',		'Extend', 1210, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.9', '',  '6.2.10',	  'Use Case Participant', 930, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.10', 	'',			'6.2.11',	'Component Participant', 930, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.11', 	'6.2.11.1',			'6.2.12',	      'Lifespan', 930, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.11.1', 	'6.2.11.1.1',			'',	      'Timing Mark', 931, 'many', 'first' );
INSERT INTO EO VALUES ( '6.2.11.1.1', 	'',			'',	      'Time Span', 941, 'many', 'first' );
INSERT INTO EO VALUES ( '6.2.12', 	'',			'',	      'Package Participant', 930, 'one', 'first' );

INSERT INTO EO VALUES ( '7.1', 	'',			'7.2',	      'Return Message', 1018, 'many', 'first' );
INSERT INTO EO VALUES ( '7.2', 	'7.2.1',			'7.3',	      'Synchronous Message', 1018, 'many', 'first' );
INSERT INTO EO VALUES ( '7.2.1', 	'',			'7.2.2',	      'Informal Synchronous Message', 1020, 'one', 'first' );
INSERT INTO EO VALUES ( '7.2.2', 	'',			'7.2.3',	      'Function Message', 1020, 'one', 'first' );
INSERT INTO EO VALUES ( '7.2.3', 	'',			'7.2.4',	      'Operation Message', 1020, 'one', 'first' );
INSERT INTO EO VALUES ( '7.2.4', 	'',			'7.2.5',          'Bridge Message', 1020, 'one', 'first' );
INSERT INTO EO VALUES ( '7.2.5', 	'',			'',	              'Interface Operation Message', 1020, 'one', 'first' );
INSERT INTO EO VALUES ( '7.3', 	'7.3.1',			'7.4',	      'Asynchronous Message', 1018, 'many', 'first' );
INSERT INTO EO VALUES ( '7.3.1', 	'',			'7.3.2',	      'Event Message', 1019, 'one', 'first' );
INSERT INTO EO VALUES ( '7.3.2', 	'',			'7.3.3',	      'Informal Asynchronous Message', 1019, 'one', 'first' );
INSERT INTO EO VALUES ( '7.3.3', 	'',			'',               'Signal Message', 1019, 'one', 'first' );
INSERT INTO EO VALUES ( '7.4', 	'7.4.1',			'7.5', 			  'Message Argument', 1000, 'many', 'first' );
INSERT INTO EO VALUES ( '7.5', 	'7.4.1',			'',	      			  'Message Argument', 1001, 'many', 'first' );
INSERT INTO EO VALUES ( '7.4.1', 	'',			'7.4.2',	      			  'Bridge Argument', 1013, 'one', 'first' );
INSERT INTO EO VALUES ( '7.4.2', 	'',			'7.4.3',	      			  'Operation Argument', 1013, 'one', 'first' );
INSERT INTO EO VALUES ( '7.4.3', 	'',			'7.4.4',	      			  'Function Argument', 1013, 'one', 'first' );
INSERT INTO EO VALUES ( '7.4.4', 	'',			'7.4.5',	      			  'Event Argument', 1013, 'one', 'first' );
INSERT INTO EO VALUES ( '7.4.5', 	'',			'7.4.6',			'Informal Argument', 1013, 'one', 'first' );
INSERT INTO EO VALUES ( '7.4.6', 	'',			'', 				'Executable Property Argument', 1013, 'one', 'first' );

-- Body Subsystem
INSERT INTO EO VALUES ( '15',          '15.1',       '',          'Body', 698, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1',        '15.1.1',     '',          'Block', 601, 'many', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1',      '15.1.1.1',   '15.1.2',    'Statement', 602, 'many', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.1',    '',           '15.1.1.2',  'Bridge Invocation', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.2',    '',           '15.1.1.3',  'Function Invocation', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.3',    '',           '15.1.1.4',  'Return Stmt', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.4',    '',           '15.1.1.5',  'Operation Invocation', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.5',    '',           '15.1.1.6',  'Interface Operation Invocation', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.6',    '',           '15.1.1.7',  'Signal Invocation', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.7',    '',           '15.1.1.8',  'Create', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.8',    '',           '15.1.1.9',  'Create No Variable', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.9',    '',           '15.1.1.10', 'Delete', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.10',   '',           '15.1.1.11', 'Assign to Member', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.11',   '15.1.1.11.1','15.1.1.12', 'Select', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.11.1', '',           '15.1.1.11.2',   'Select Related Where', 664, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.11.2', '',           '15.1.1.11.3',   'Select Related By', 664, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.11.3', '15.1.1.11.3.1','15.1.1.11.4', 'Chain Link', 637, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.11.3.1', '',          '',         'Chain Link', 604, 'one', 'first', false, false, 'Precedes', false, true );
INSERT INTO EO VALUES ( '15.1.1.12',   '',           '15.1.1.13', 'Select From Instances', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.13',   '',           '15.1.1.14', 'Select From Instances Where', 603, 'one', 'first', false, false, '', false, true );

-- Event Subsystem
INSERT INTO EO VALUES ( '15.1.1.14',   '15.1.1.14.1','15.1.1.15',     'Event Specification Statement', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.14.1', '',           '15.1.1.14.2',   'Actual Parameter', 700, 'many', 'none', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.14.2', '15.1.1.14.2.1', '15.1.1.14.3','Create Event Statement', 701, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.14.2.1',   '15.1.1.14.2.2.1', '15.1.1.14.2.3', 'Create SM Event Statement', 702, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.14.2.2.1',                '', '15.1.1.14.2.2.2', 'Create Event to Instance', 704, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.14.2.2.2',                '', '15.1.1.14.2.2.3', 'Create Event to Class', 704, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.14.2.2.3',                '', '15.1.1.14.2.2.4', 'Create Event to Creator', 704, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.14.2.3',                  '',   '',   'Create Event to External Entity', 702, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.14.3',       '15.1.1.14.3.1',   '',   'Generate Event Statement', 701, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.14.3.1',   '15.1.1.14.3.1.1',   '15.1.1.14.3.2',   'Generate SM Event Statement', 703, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.14.3.1.1',                '',  '15.1.1.14.3.1.2',   'Generate', 705, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.14.3.1.2',                '',  '15.1.1.14.3.1.3',   'Generate to Class', 705, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.14.3.1.3',                '',  '',                  'Generate to Creator', 705, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.14.3.2',                  '',   '',    'Generate to External Entity', 703, 'one', 'first', false, false, '', false, true );

INSERT INTO EO VALUES ( '15.1.1.15',   '',                '15.1.1.16', 'Generate Preexisting Event', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.16',   '',            '15.1.1.17',  'Relate', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.17',   '',            '15.1.1.18',  'Relate Using', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.18',   '',            '15.1.1.19',  'Unrelate', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.19',   '',            '15.1.1.20',  'Unrelate Using', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.20',   '',           '15.1.1.21',   'Break', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.21',   '',           '15.1.1.22',   'Continue', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.22',   '',           '15.1.1.23',   'Control', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.23',   '',           '15.1.1.24',   'While Stmt', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.24',   '',           '15.1.1.25',   'If Stmt', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.25',   '',           '15.1.1.26',   'ElseIf Stmt', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.26',   '',           '15.1.1.27',   'Else Stmt', 603, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.1.27',   '',           '15.1.1.28',   'For Stmt', 603, 'one', 'first', false, false, '', false, true );

-- The Value Subsystem
INSERT INTO EO VALUES ( '15.1.2',      '15.1.2.1',   '15.1.3',      'Value', 826, 'many', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.1',    '',           '15.1.2.2',    'Parameter Value', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.2',    '', '15.1.2.3',              'Function Value', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.3',    '',           '15.1.2.4',    'Selected Reference', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.4',    '',           '15.1.2.5',    'Binary Operation', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.5',    '',           '15.1.2.6',    'Unary Operation', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.6',    '',           '15.1.2.7',    'Literal Boolean', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.7',    '',           '15.1.2.8',    'Literal String', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.8',    '',           '15.1.2.9',    'Literal Real', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.9',    '',           '15.1.2.10',    'Literal Integer', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.10',   '',           '15.1.2.11',   'Transient Value Reference', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.11',   '',           '15.1.2.12',   'Attribute Value Reference', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.12',   '',           '15.1.2.13',   'Instance Set Reference', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.13',   '',           '15.1.2.14',   'Instance Reference', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.14',   '15.1.2.14.1','15.1.2.15',   'Event Datum Value', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.14.1', '',           '',            'Event Parameter Reference', 834, 'many', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.15',   '',           '15.1.2.16',   'Operation Value', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.16',   '',           '15.1.2.17',   'Bridge Value', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.17',   '',           '15.1.2.18',   'Literal Enumerator', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.18',   '',           '15.1.2.19',   'Member Value Reference', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.19',   '',           '15.1.2.20',   'Array Element Reference', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.20',   '',           '15.1.2.21',   'Array Length Value', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.21',   '',           '15.1.2.22',   'Message Value', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.22',   '',           '15.1.2.23',   'Symbolic Constant Value', 801, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.2.23',   '',           '',            'Actual Parameter', 800, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.3',      '15.1.3.1',   '',            'Variable', 823, 'many', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.3.1',    '',           '15.1.3.2',    'Instance Handle', 814, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.3.2',    '',           '15.1.3.3',    'Instance Set', 814, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.3.3',    '15.1.3.3.1', '15.1.3.4',    'Transient Var', 814, 'one', 'first', false, false, '', false, true );
INSERT INTO EO VALUES ( '15.1.3.3.1',  '',           '',            'Dimensions', 844, 'many', 'first');
INSERT INTO EO VALUES ( '15.1.3.4',    '',           '',            'Dimensions', 849, 'many', 'first');

-- Define alternate children

-- export the proxy for the imported references interface
INSERT INTO AEP VALUES ( '12.4.8.1.1', '12.4.3.1',		'->C_IR[R4701]', 'one', true, true, true);
INSERT INTO AEP VALUES ( '12.4.8.1.1', '24.10.7',		'->C_IR[R4701]->C_I[R4012]', 'one', true, true, true);
INSERT INTO AEP VALUES ( '12.4.8.1.1', '24.10.5',		'->C_IR[R4701]->C_PO[R4016]->C_C[R4010]', 'one', false, true);
INSERT INTO AEP VALUES ( '12.4.8.1.1', '24.10',		'->C_IR[R4701]->C_PO[R4016]->C_C[R4010]->PE_PE[R8001]', 'one', false, true);

-- export parameters for interface operations and signals
INSERT INTO AEP VALUES ( '14.1.3.1.1', '14.1.3.1.3',		'->C_EP[R4004]->C_PP[R4006]', 'many', true, false, false);
INSERT INTO AEP VALUES ( '14.1.3.1.2', '14.1.3.1.3',		'->C_EP[R4004]->C_PP[R4006]', 'many', true, false, false);

-- export satisfaction proxy for provisions, requirements, imported provisions, imported requirements
INSERT INTO AEP VALUES ( '12.4.3.1.2', '12.4.10.1',		'->C_SF[R4002]', 'many', false, false, false);
INSERT INTO AEP VALUES ( '12.4.8.1.1.1', '12.4.10.1',		'->CL_IPINS[R4705]->C_SF[R4705]', 'many', false, false, false);
INSERT INTO AEP VALUES ( '12.4.3.1.2', '24.10',		'->C_SF[R4002]->PE_PE[R8001]', 'one', false, false, false);
INSERT INTO AEP VALUES ( '12.4.8.1.1.1', '24.10',		'->CL_IPINS[R4705]->C_SF[R4705]->PE_PE[R8001]', 'one', false, false, false);
-- additionally include the requirement at the other end of the satisfaction
-- and the imported requirement
INSERT INTO AEP VALUES ( '12.4.3.1.2', '12.4.3.1.1',		'->C_SF[R4002]->C_R[R4002]', 'many', false, false, false);
INSERT INTO AEP VALUES ( '12.4.3.1.2', '12.4.8.1.1.2',		'->C_SF[R4002]->CL_IR[R4706]', 'many', false, false, false);
INSERT INTO AEP VALUES ( '12.4.8.1.1.1', '12.4.3.1.1',		'->CL_IPINS[R4705]->C_SF[R4705]->C_R[R4002]', 'many', false, true, false);
INSERT INTO AEP VALUES ( '12.4.8.1.1.1', '12.4.8.1.1.2',		'->CL_IPINS[R4705]->C_SF[R4705]->CL_IR[R4706]', 'many', false, true, false);

-- export any event that is assigned to this transition and it's children
INSERT INTO AEP VALUES ( '3.7.3', '3.5',		'->SM_LEVT[R509]->SM_SEVT[R526]->SM_EVT[R525]', 'one', true, false, false, true);
-- export any data items associated with the supplemental data above
INSERT INTO AEP VALUES ( '3.7.3', '3.3',		'->SM_LEVT[R509]->SM_SEVT[R526]->SM_EVT[R525]->SM_EVTDI[R532]', 'many', true, false, false, true);
-- export the transition action home and its children
INSERT INTO AEP VALUES ( '3.7.3', '3.7.4',		'->SM_TXN[R507]->SM_TAH[R530]', 'one', true, false, false, true);

-- do the same as above for other transitions
-- export any event that is assigned to this transition and it's children
INSERT INTO AEP VALUES ( '3.7', '3.5',		'->SM_NSTXN[R507]->SM_SEME[R504]->SM_SEVT[R503]->SM_EVT[R525]', 'one', true, false, true, true);
-- export any data items associated with the supplemental data above
INSERT INTO AEP VALUES ( '3.7', '3.3',		'->SM_NSTXN[R507]->SM_SEME[R504]->SM_SEVT[R503]->SM_EVT[R525]->SM_EVTDI[R532]', 'many', true, false, false, true);

-- export required poly info
INSERT INTO AEP VALUES ( '3.7', '2.1',		'->SM_NSTXN[R507]->SM_SEME[R504]->SM_SEVT[R503]->SM_NLEVT[R526]->SM_PEVT[R527]->SM_EVT[R525]->SM_SM[R502]->SM_ISM[R517]->O_OBJ[R518]', 'one', true, true, false, false, 'Doesnotcontaintransition(inst.getTrans_id())');
INSERT INTO AEP VALUES ( '2.2', '2.1',		'->O_OBJ[R101]', 'one', true, true, false);
INSERT INTO AEP VALUES ( '2.1', '2.1',    '->SM_ISM[R518]->SM_SM[R517]->SM_EVT[R502]->SM_SEVT[R525]->SM_NLEVT[R526]->SM_PEVT[R527]->SM_EVT[R525]->SM_SM[R502]->SM_ISM[R517]->O_OBJ[R518]', 'many', true, true, false, false, 'getObj_id().equals(inst.getObj_id()) == false');

-- export data items with event
INSERT INTO AEP VALUES ( '3.5', '3.3',		'->SM_EVTDI[R532]', 'many', true, false, false);

-- export Packageable Elements under a Component
INSERT INTO AEP VALUES ( '24.10.5', '24.10',        '->PE_PE[R8003]', 'many', true, false, true);

-- export Signal data for assigned signals
INSERT INTO AEP VALUES ( '3.5.2.3', '12.4.3.1.2.1',        '->SPR_PS[R528]->SPR_PEP[R4503]', 'one', true, true, true); 
INSERT INTO AEP VALUES ( '3.5.2.3', '14.1.3.1',        '->SPR_PS[R528]->SPR_PEP[R4503]->C_EP[R4501]', 'one', true, true, true);
INSERT INTO AEP VALUES ( '3.5.2.3', '12.4.3.1',        '->SPR_PS[R528]->SPR_PEP[R4503]->C_P[R4501]->C_IR[R4009]', 'one', true, true, true);
INSERT INTO AEP VALUES ( '3.5.2.3', '12.4.3',        '->SPR_PS[R528]->SPR_PEP[R4503]->C_P[R4501]->C_IR[R4009]->C_PO[R4016]', 'one', true, true, true);
INSERT INTO AEP VALUES ( '3.5.2.3', '12.4.3.1.1.1',        '->SPR_RS[R529]->SPR_REP[R4502]', 'one', true, true, true); 
INSERT INTO AEP VALUES ( '3.5.2.3', '14.1.3.1',        '->SPR_RS[R529]->SPR_REP[R4502]->C_EP[R4500]', 'one', true, true, true);
INSERT INTO AEP VALUES ( '3.5.2.3', '12.4.3.1',        '->SPR_RS[R529]->SPR_REP[R4502]->C_R[R4500]->C_IR[R4009]', 'one', true, true, true);
INSERT INTO AEP VALUES ( '3.5.2.3', '12.4.3',        '->SPR_RS[R529]->SPR_REP[R4502]->C_R[R4500]->C_IR[R4009]->C_PO[R4016]', 'one', true, true, true);

-- always export proxy for S_DT across R18, as if its in the same PMC it will
-- be excluded which prevents resolution from working at paste time
INSERT INTO AEP VALUES ( '1.5.3.1.2', '24.10.1',        '->S_DT[R18]', 'one', false, true, false);

-- End Define alternate children

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
