
INSERT INTO EO VALUES ( '6',	'23',	  '',     'System Model',0 , '', 'first', true );
INSERT INTO EO VALUES ( '23', '23.10', '', 'Package', 1401, 'many', 'first', true );

INSERT INTO EO VALUES ( '23.10',  '1.5.3.1.1', '23.11', 'Data Type', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->S_DT[R8001]'  );
INSERT INTO EO VALUES ( '23.11',  '6.2.1', '23.12',   'Interaction Participant', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->SQ_P[R8001]'  );
INSERT INTO EO VALUES ( '23.12',  '1.14.2.1', '23.13',       'Activity Node', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->A_N[R8001]'  );
INSERT INTO EO VALUES ( '23.13',  '2.1.1', '23.14',          'Model Class', -1, 'many', 'none', true, false, '', false, false, '->PE_PE[R8000]->O_OBJ[R8001]' );
INSERT INTO EO VALUES ( '23.14',  '12.4.1', '23.15',       'Component', -1, 'many', 'none', true, false, '', false, false, '->PE_PE[R8000]->C_C[R8001]'  );
INSERT INTO EO VALUES ( '23.15',  '12.4.8.1', '23.16',       'Component Reference', -1, 'many', 'first', false, false, '', false, false, '->PE_PE[R8000]->CL_IC[R8001]'  );
INSERT INTO EO VALUES ( '23.16',  '14.1.3.1', '23.17',       'Interface', -1, 'many', 'none', true, false, '', false, false, '->PE_PE[R8000]->C_I[R8001]'  );
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
INSERT INTO EO VALUES ( '23.19.1',    '', '', 'Packageable Element', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '23.20.1',    '', '', 'Packageable Element', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '23.23.1',    '', '', 'Packageable Element', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '23.27.1',    '', '', 'Packageable Element', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '23.29',    '', '', 'Packageable Element', 8001, 'one', 'first', false );

INSERT INTO EO VALUES ( '1.5.3.1.1',	'',             '1.5.3.1.2', 'Core Data Type', 17, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.2', 	'',             '1.5.3.1.3', 'User Data Type', 17, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.3', 	'1.5.3.1.3.1', 	'1.5.3.1.4', 'Enumeration Data Type', 17, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.3.1', 	'',             '',          'Enumerator', 27, 'many', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.4', 	'1.5.3.1.4.1',  '1.5.3.1.5', 'Structured Data Type', 17, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.4.1', 	'1.5.3.1.4.1.1','',          'Structure Member', 44, 'many', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.4.1.1','',             '',          'Dimensions', 53, 'many', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.5', 	'',             '1.5.3.1.6',          'Instance Reference Data Type', 17, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.4', 	    '',             '1.5.5',     'System Datatype in Package', 4401, 'many', 'first' );
INSERT INTO EO VALUES ( '1.5.5',        '',             '1.5.6',     'System Datatype Package', 4400, 'many', 'first', false );
INSERT INTO EO VALUES ( '1.5.6',        '',             '1.5.7',     'Specification Package', 1402, 'one', 'first', false );
INSERT INTO EO VALUES ( '1.5.7',        '1.5.7.1',      '1.5.8',     'Constant in Package', 1506, 'many', 'first' );
INSERT INTO EO VALUES ( '1.5.7.1',      '1.5.7.1.1',    '',          'Constant Specification', 1506, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.7.1.1',    '1.5.7.1.1.1',  '1.5.7.1.2',          'Symbolic Constant', 1504, 'many', 'first' );
INSERT INTO EO VALUES ( '1.5.7.1.1.1',  '1.5.7.1.1.1.1','',          'Leaf Symbolic Constant', 1502, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.7.1.1.1.1','',             '',          'Literal Symbolic Constant', 1503, 'one', 'first' );
INSERT INTO EO VALUES ( '1.5.8', 	    '',             '',          'System Constant in Package', 4403, 'many', 'first' );
INSERT INTO EO VALUES ( '1.5.3.1.6',    '', '', 'Packageable Element', 8001, 'one', 'first', false );
INSERT INTO EO VALUES ( '1.5.7.1.2',    '', '', 'Packageable Element', 8001, 'one', 'first', false );


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

INSERT INTO EO VALUES ( '2.5.1',	'2.5.1.1',	'2.5.2',	'EE to SM Comm Path', 401, 'one', 'first' );
INSERT INTO EO VALUES ( '2.5.1.1',	'',			'',			'EE to SM Event Comm', 404, 'many', 'first' );
INSERT INTO EO VALUES ( '2.5.2',	'2.5.2.1',	'2.5.3',	'SM to SM Comm Path', 401, 'one', 'first' );
INSERT INTO EO VALUES ( '2.5.2.1',	'',			'',			'SM to SM Event Comm', 408, 'many', 'first' );
INSERT INTO EO VALUES ( '2.5.3',	'2.5.3.1',	'',			'SM to EE Comm Path', 401, 'one', 'first' );
INSERT INTO EO VALUES ( '2.5.3.1',	'',			'',			'SM to EE Event Comm', 412, 'many', 'first' );

INSERT INTO EO VALUES ( '2.6.1',	'2.6.1.1',	'2.6.2',	'SM to OBJ Access Path', 415, 'one', 'first' );
INSERT INTO EO VALUES ( '2.6.1.1',	'',			'',			'SM to OBJ Attribute Access', 418, 'many', 'first' );
INSERT INTO EO VALUES ( '2.6.2',	'2.6.2.1',	'',			'SM to EE Access Path', 415, 'one', 'first' );
INSERT INTO EO VALUES ( '2.6.2.1',	'',			'',			'SM to EE Data Item Access', 422, 'many', 'first' );


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

INSERT INTO EO VALUES ( '6.2.1',	'',	   '6.2.2',	  'External Entity Participant', 930, 'one', 'first' );
INSERT INTO EO VALUES ( '6.2.2',	'',	   '6.2.3',	  'Function Package Participant', 930, 'one', 'first' );
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

INSERT INTO EI VALUES ( 'ISM' );
INSERT INTO GD VALUES ( 'ISM', 'InstanceStateChartDiagram', 'State Machine', 'SM_ID' );
INSERT INTO EI VALUES ( 'ASM' );
INSERT INTO GD VALUES ( 'ASM', 'ClassStateChartDiagram', 'State Machine', 'SM_ID' );
