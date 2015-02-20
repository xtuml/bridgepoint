--======================================================================
--
-- File:      $RCSfile: file_io.pei.sql,v $
-- Version:   $Revision: 1.23 $
-- Modified:  $Date: 2013/05/10 16:17:47 $
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

INSERT INTO EO VALUES ( '1',	'1.1',	'', 	'Domain', 0, 'none', 'first' );
INSERT INTO EO VALUES ( '1.1',	'1.1.1','1.2',	'Data Type', 14, 'many', 'last' );
INSERT INTO EO VALUES ( '1.2', 	'1.2.1','1.3',	'Synchronous Service', 23, 'many', 'first' );
INSERT INTO EO VALUES ( '1.3', 	'1.3.1','1.4',	'External Entity', 8, 'many', 'first' );
INSERT INTO EO VALUES ( '1.4', '',		'1.5',	'DD', 0, '', 'first' );
INSERT INTO EO VALUES ( '1.5', '',		'1.6',	'SRM', 0, '', 'first' );
INSERT INTO EO VALUES ( '1.6', '',		'1.7',	'SCM', 0, '', 'first' );
INSERT INTO EO VALUES ( '1.7', '',		'2',	'SAM', 0, '', 'first' );

INSERT INTO EO VALUES ( '1.1.1',	'', 		'1.1.2','Core Data Type', 17, 'one', 'first' );
INSERT INTO EO VALUES ( '1.1.2', 	'', 		'1.1.3','User Data Type', 17, 'one', 'first' );
INSERT INTO EO VALUES ( '1.1.3', 	'1.1.3.1', 	'', 	'Enumeration Data Type', 17, 'one', 'first' );
INSERT INTO EO VALUES ( '1.1.3.1', 	'', 		'', 	'Enumerator', 27, 'many', 'first' );

INSERT INTO EO VALUES ( '1.2.1', 	'', 		'', 	'Synchronous Service Parameter', 24, 'many', 'first' );

INSERT INTO EO VALUES ( '1.3.1', 	'1.3.1.1', 	'1.3.2','Bridge', 19, 'many', 'first' );
INSERT INTO EO VALUES ( '1.3.1.1', 	'',			'', 	'Bridge Parameter', 21, 'many', 'first' );
INSERT INTO EO VALUES ( '1.3.2', 	'', 		'1.3.3','External Entity Event Data Item', 12, 'many', 'first' );
INSERT INTO EO VALUES ( '1.3.3', 	'1.3.3.1',	'',		'External Entity Event', 10, 'many', 'first' );
INSERT INTO EO VALUES ( '1.3.3.1', 	'', 		'',		'External Entity Event Data', 13, 'many', 'first' );


INSERT INTO EO VALUES ( '2',	'2.1',	'',		'Subsystem', 1, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1',	'2.1.1','2.2',	'Object', 2, 'many', 'first' );
INSERT INTO EO VALUES ( '2.2',	'',		'2.3',	'Imported Object', 3, 'many', 'first' );
INSERT INTO EO VALUES ( '2.3',	'',		'2.4',	'External Entity in Model', 7, 'many', 'first' );
INSERT INTO EO VALUES ( '2.4',	'2.4.1','2.5',	'Relationship', 4, 'many', 'Composition Relationship' );
INSERT INTO EO VALUES ( '2.5',	'2.5.1','2.6',	'Communication Path', 5, 'many', 'first' );
INSERT INTO EO VALUES ( '2.6',	'2.6.1','2.7',	'Access Path', 6, 'many', 'first' );
INSERT INTO EO VALUES ( '2.7',	'',		'2.8',	'OIM', 0, '', 'first' );
INSERT INTO EO VALUES ( '2.8',	'',		'2.9',	'OCM', 0, '', 'first' );
INSERT INTO EO VALUES ( '2.9',	'',		'', 	'OAM', 0, '', 'first' );

INSERT INTO EO VALUES ( '2.1.1',	'2.1.1.1',	'2.1.2',	'Transformer', 115, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.1.1',	'',	  		'', 		'Transformer Parameter', 117, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.2',	'2.1.2.1',	'2.1.3',	'Attribute', 102, 'many', 'last' );
INSERT INTO EO VALUES ( '2.1.2.1',	'2.1.2.1.1','2.1.2.2',	'Base Attribute', 106, 'one', 'last' );
INSERT INTO EO VALUES ( '2.1.2.1.1','',			'2.1.2.1.2','Derived Based Attribute', 107, 'one', 'first' );
INSERT INTO EO VALUES ( '2.1.2.1.2','',			'',			'New Base Attribute', 107, 'one', 'first' );
INSERT INTO EO VALUES ( '2.1.2.2',	'2.1.2.2.1','',			'Referential Attribute', 106, 'one', 'last' );
INSERT INTO EO VALUES ( '2.1.2.2.1','',			'',			'Attribute Reference in Object', 108, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.3',	'2.1.3.1',	'2.1.4',	'Object Identifier', 104, 'many', 'first if child not empty' );
INSERT INTO EO VALUES ( '2.1.3.1',	'2.1.3.1.1','',			'Object Identifier Attribute', 105, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.3.1.1','',			'',			'Referred To Identifier Attribute', 110, 'many', 'first' );
INSERT INTO EO VALUES ( '2.1.4',	'3',		'2.1.5',	'Instance State Model', 518, 'one', 'first' );
INSERT INTO EO VALUES ( '2.1.5',	'3',		'',			'Assigner State Model', 519, 'one', 'first' );

INSERT INTO EO VALUES ( '2.4.1',	'',			'2.4.2',	'Simple Relationship', 206, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.2',	'',			'2.4.3',	'Associative Relationship', 206, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.3',	'',			'2.4.4',	'Subtype Supertype Relationship', 206, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.4',	'',			'2.4.5',	'Composition Relationship', 206, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.5',	'2.4.5.1',	'',			'Object In Relationship', 201, 'many', 'last' );

INSERT INTO EO VALUES ( '2.4.5.1',	'2.4.5.1.1','2.4.5.2',	'Referred To Object in Rel', 203, 'one', 'last' );
INSERT INTO EO VALUES ( '2.4.5.1.1','',			'2.4.5.1.2','Object As Simple Participant', 204, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.5.1.2','',			'2.4.5.1.3','Object As Associated One Side', 204, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.5.1.3','',			'2.4.5.1.4','Object As Associated Other Side', 204, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.5.1.4','',			'',         'Object As Supertype', 204, 'one', 'first' );

INSERT INTO EO VALUES ( '2.4.5.2',	'2.4.5.2.1','2.4.5.3',	'Referring Object In Rel', 203, 'one', 'last' );
INSERT INTO EO VALUES ( '2.4.5.2.1','',			'2.4.5.2.2','Object As Simple Formalizer', 205, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.5.2.2','',			'2.4.5.2.3','Object As Associator', 205, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.5.2.3','',			'',         'Object As Subtype', 205, 'one', 'first' );

INSERT INTO EO VALUES ( '2.4.5.3',	'',			'2.4.5.4',	'Object As Composition One Side', 203, 'one', 'first' );
INSERT INTO EO VALUES ( '2.4.5.4',	'',			'',			'Object As Composition Other Side', 203, 'one', 'first' );

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


INSERT INTO EO VALUES ( '3',	'3.1',	'', 'State Model', 517, 'one', 'first' );
INSERT INTO EO VALUES ( '3.1',	'',		'3.2', 'Moore State Model', 510, 'one', 'first' );
INSERT INTO EO VALUES ( '3.2',	'',		'3.3', 'Mealy State Model', 510, 'one', 'first' );
INSERT INTO EO VALUES ( '3.3',	'',		'3.4', 'State Model Event Data Item', 516, 'many', 'first' );
INSERT INTO EO VALUES ( '3.4',	'3.4.1','3.5', 'Event Supplemental Data', 523, 'many', 'first' );
INSERT INTO EO VALUES ( '3.5',	'3.5.1','3.6', 'State Model Event', 502, 'many', 'last' );
INSERT INTO EO VALUES ( '3.6',	'3.6.1','3.7', 'State Model State', 501, 'many', 'first' );
INSERT INTO EO VALUES ( '3.7',	'3.7.1','3.8', 'Transition', 505, 'many', 'last' );
INSERT INTO EO VALUES ( '3.8',	'',		'3.9', 'ISM', 0, '', 'first' );
INSERT INTO EO VALUES ( '3.9',	'',		'',    'ASM', 0, '', 'first' );

INSERT INTO EO VALUES ( '3.4.1','',		'', 'Supplemental Data Items', 522, 'many', 'first' );

INSERT INTO EO VALUES ( '3.5.1','',		'3.5.2',	'Polymorphic Event', 525, 'one', 'last' );
INSERT INTO EO VALUES ( '3.5.2','3.5.2.1','',		'SEM Event', 525, 'one', 'last' );
INSERT INTO EO VALUES ( '3.5.2.1','',	'3.5.2.2',	'Local Event', 526, 'one', 'first' );
INSERT INTO EO VALUES ( '3.5.2.2','',	'',			'Non Local Event', 526, 'one', 'first' );

INSERT INTO EO VALUES ( '3.6.1','3.6.1.1',	'3.6.2','State Event Matrix Entry', 503, 'many', 'last' );
INSERT INTO EO VALUES ( '3.6.1.1','',	'3.6.1.2',	'Event Ignored', 504, 'one', 'first' );
INSERT INTO EO VALUES ( '3.6.1.2','',	'', 		'Cant Happen', 504, 'one', 'first' );
INSERT INTO EO VALUES ( '3.6.2', '3.6.2.1',        '', 'Moore Action Home', 511, 'one', 'first' );
INSERT INTO EO VALUES ( '3.6.2.1', '3.6.2.1.1',    '', 'Action Home', 513, 'one', 'first' );
INSERT INTO EO VALUES ( '3.6.2.1.1', '',           '', 'Action', 514, 'one', 'first' );

INSERT INTO EO VALUES ( '3.7.1','',		'3.7.2', 	'No Event Transition', 507, 'one', 'first' );
INSERT INTO EO VALUES ( '3.7.2','',		'3.7.3', 	'New State Transition', 507, 'one', 'first' );
INSERT INTO EO VALUES ( '3.7.3','',		'', 		'Creation Transition', 507, 'one', 'first' );

INSERT INTO EO VALUES ( '4', 		'4.1',		'',		'Model', 0, '', 'first' );
INSERT INTO EO VALUES ( '4.1',		'4.1.1',	'',		'Graphical_Element', 1, 'many', 'first' );
INSERT INTO EO VALUES ( '4.1.1',	'', 		'4.1.2','Shape', 2, 'one', 'first' );
INSERT INTO EO VALUES ( '4.1.2',	'4.1.2.1',	'',		'Connector', 2, 'one', 'first' );
INSERT INTO EO VALUES ( '4.1.2.1',	'', 		'4.1.2.2','Floating Text', 8, 'one', 'first' );
INSERT INTO EO VALUES ( '4.1.2.2', 	'', 		'', 	'Line_Segment', 6, 'many', 'first' );

INSERT INTO EI VALUES ( 'DD' );
INSERT INTO GD VALUES ( 'DD', 'DomainPackageDiagram', 'Domain', 'Dom_ID' );
INSERT INTO EI VALUES ( 'SRM' );
INSERT INTO GD VALUES ( 'SRM', 'PDDR', 'Domain', 'Dom_ID' );
INSERT INTO EI VALUES ( 'SCM' );
INSERT INTO GD VALUES ( 'SCM', 'PDDA', 'Domain', 'Dom_ID' );
INSERT INTO EI VALUES ( 'SAM' );
INSERT INTO GD VALUES ( 'SAM', 'PDDS', 'Domain', 'Dom_ID' );
INSERT INTO EI VALUES ( 'OIM' );
INSERT INTO GD VALUES ( 'OIM', 'ClassDiagram', 'Subsystem', 'SS_ID' );
INSERT INTO EI VALUES ( 'OCM' );
INSERT INTO GD VALUES ( 'OCM', 'OCDA', 'Subsystem', 'SS_ID' );
INSERT INTO EI VALUES ( 'OAM' );
INSERT INTO GD VALUES ( 'OAM', 'OCDS', 'Subsystem', 'SS_ID' );
INSERT INTO EI VALUES ( 'ISM' );
INSERT INTO GD VALUES ( 'ISM', 'InstanceStateChartDiagram', 'State Model', 'SM_ID' );
INSERT INTO EI VALUES ( 'ASM' );
INSERT INTO GD VALUES ( 'ASM', 'ClassStateChartDiagram', 'State Model', 'SM_ID' );

INSERT INTO EC VALUES ( 'Domain', 'Config_ID', 'Sys_ID', 'IdAssigner.NULL_UUID' );
INSERT INTO EC VALUES ( 'Referential Attribute', 'Ref_Mode', 'BaseAttrName', '""' );
INSERT INTO EC VALUES ( 'Attribute Reference in Object', 'Descrip', 'RObj_Name', '""');
INSERT INTO EC VALUES ( 'Attribute Reference in Object', 'Descrip', 'RAttr_Name', '""');
INSERT INTO EC VALUES ( 'Attribute Reference in Object', 'Descrip', 'Rel_Name', '""');

INSERT INTO EC VALUES ( 'Model', 'GridOn', 'SelRectX', '0' );
INSERT INTO EC VALUES ( 'Model', 'GridOn', 'SelRectY', '0' );
INSERT INTO EC VALUES ( 'Model', 'GridOn', 'SelRectW', '0' );
INSERT INTO EC VALUES ( 'Model', 'GridOn', 'SelRectH', '0' );
INSERT INTO EC VALUES ( 'Model', 'GridOn', 'represents', 'null' );
INSERT INTO EC VALUES ( 'Graphical_Element', 'OOA_Type', 'represents', 'null' );
