-- ========================================================================
--
-- File:          $RCSfile: bp_gd.sql,v $
-- Version:   $Revision: 1.15 $
-- Modified:  $Date: 2013/05/10 16:17:47 $
-- 
-- (c) Copyright 2003-2014 Mentor Graphics Corporation All rights reserved.
-- 
-- ========================================================================
-- Licensed under the Apache License, Version 2.0 (the "License"); you may not 
-- use this file except in compliance with the License.  You may obtain a copy 
-- of the License at
--
--   http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software 
-- distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
-- WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
-- License for the specific language governing permissions and limitations under
-- the License.
-- ========================================================================
-- 
-- This file is created by exporting the Graphics Domain subsystem from
-- ooaofooa (without graphics), and then adding an S_DOM insert.
-- The attribute LineSegment.Next_GE_ID was renamed to Previous_GE_ID

INSERT INTO S_DOM
	VALUES (63030, 
	'ooaofgraphics',
	'', 
	0,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	63030,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	63030,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	63030,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	63030,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	63030,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	63030,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	63030,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	63030,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	63030,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	63030,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	63030,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	63030,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	63030,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	63030,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	63030,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	63030,
	'inst_ref<Timer>',
	'');

INSERT INTO S_SS
	VALUES (4194312,
	'Graphics Domain',
	'Import:org.eclipse.swt.graphics.*',
	'GD',
	1000,
	63030,
	4194312);
INSERT INTO O_OBJ
	VALUES (4194305,
	'Model',
	1000,
	'GD_MD',
	'A model is a drawing for one particular view of the analysis. There is an instance of model for each glyph on the index window, and each entry in the model element list on the index window. For example, every domain will have four models (DPD, PDD(R), PDD(A), PDD(S)).  Every subsystem will have three models (CD, OCD(A), OCD(S)).  Every state chart has one model (SC).  The STT is not a graphical model.',
	4194312);
INSERT INTO O_NBATTR
	VALUES (4194305,
	4194305);
INSERT INTO O_BATTR
	VALUES (4194305,
	4194305);
INSERT INTO O_ATTR
	VALUES (4194305,
	4194305,
	0,
	'Model_ID',
	'Description: This is an arbitrary unique identifier for the graphical model.
Full Name: Model Identifier
',
	'',
	'Model_ID',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (4194306,
	4194305);
INSERT INTO O_BATTR
	VALUES (4194306,
	4194305);
INSERT INTO O_ATTR
	VALUES (4194306,
	4194305,
	4194305,
	'Model_Type',
	'Description: An enumeration of the values that identify the type of a model.
Full Name: Graphical Model Type
Data Domain:  None= 0, DPD= 1, PDD(R)=2, PDD(A)=3, PDD(S)=4, CD=5, OCD(A)=6, OCD(S)=7, SC(Instance)=8, SC(Assigner)=10',
	'',
	'Model_Type',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194307,
	4194305);
INSERT INTO O_BATTR
	VALUES (4194307,
	4194305);
INSERT INTO O_ATTR
	VALUES (4194307,
	4194305,
	4194306,
	'OOA_ID',
	'Description: The identifier of the corresponding OOA model for this model (GD_MD).
Full Name: OOA Model Identifier',
	'',
	'OOA_ID',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (4194308,
	4194305);
INSERT INTO O_BATTR
	VALUES (4194308,
	4194305);
INSERT INTO O_ATTR
	VALUES (4194308,
	4194305,
	4194307,
	'OOA_Type',
	'Description: The type of the OOA model that the OOA_ID identifies.  
Full Name: OOA Model Type
Data Domain: Domain = 1, Subsystem = 11, Statechart = 40
',
	'',
	'OOA_Type',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194309,
	4194305);
INSERT INTO O_BATTR
	VALUES (4194309,
	4194305);
INSERT INTO O_ATTR
	VALUES (4194309,
	4194305,
	4194308,
	'UseGlobalPrint',
	'Description: Defines the print rules for the model.
Full Name: Global Print Indicator
Data Domain: If True, use the globally defined rules for printing this model.  If False, use the print rules defined by this model (PrintMode, PrintRows, PrintCols, IsLandscape, ZoomFontSize).',
	'',
	'UseGlobalPrint',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (4194310,
	4194305);
INSERT INTO O_BATTR
	VALUES (4194310,
	4194305);
INSERT INTO O_ATTR
	VALUES (4194310,
	4194305,
	4194309,
	'PrintMode',
	'Description: Determines how a model is printed.
Full Name: Print Mode Indicator
Data Domain: If False, then use the PrintCols and PrintRows values to determine the size of the output.  If True, then use the ZoomFontSize to determine the size of the output.
',
	'',
	'PrintMode',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (4194311,
	4194305);
INSERT INTO O_BATTR
	VALUES (4194311,
	4194305);
INSERT INTO O_ATTR
	VALUES (4194311,
	4194305,
	4194310,
	'PrintRows',
	'Description: When printing the model in mosaic mode, this is the number of rows that are printed.
Full Name: Number of Print Rows',
	'',
	'PrintRows',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194312,
	4194305);
INSERT INTO O_BATTR
	VALUES (4194312,
	4194305);
INSERT INTO O_ATTR
	VALUES (4194312,
	4194305,
	4194311,
	'PrintCols',
	'Description: When printing the model in mosaic mode, this is the number of columns that are printed.
Full Name: Number of Print Columns
',
	'',
	'PrintCols',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194313,
	4194305);
INSERT INTO O_BATTR
	VALUES (4194313,
	4194305);
INSERT INTO O_ATTR
	VALUES (4194313,
	4194305,
	4194312,
	'IsLandscape',
	'Description: Determines if printing in landscape or portrait mode.
Full Name: Landscape Indicator
Data Domain: If true, print the model using the landscape (long axis horizontal) orientation. If false, print the model using the portrait orientation.
',
	'',
	'IsLandscape',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (4194314,
	4194305);
INSERT INTO O_BATTR
	VALUES (4194314,
	4194305);
INSERT INTO O_ATTR
	VALUES (4194314,
	4194305,
	4194313,
	'ZoomFontSize',
	'Description: When sizing the diagram, if the user requests Zoom to Font Size, use this font size to determine the zoom factor.
Full Name: Zoom Font Size
',
	'',
	'ZoomFontSize',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194315,
	4194305);
INSERT INTO O_BATTR
	VALUES (4194315,
	4194305);
INSERT INTO O_ATTR
	VALUES (4194315,
	4194305,
	4194314,
	'ScrollXPos',
	'Description: This is the X position the model was last scrolled to.
Full Name: Last Scroll X Position',
	'',
	'ScrollXPos',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194316,
	4194305);
INSERT INTO O_BATTR
	VALUES (4194316,
	4194305);
INSERT INTO O_ATTR
	VALUES (4194316,
	4194305,
	4194315,
	'ScrollYPos',
	'Description: This is the Y position the model was last scrolled to.
Full Name: Last Scroll Y Position',
	'',
	'ScrollYPos',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194317,
	4194305);
INSERT INTO O_BATTR
	VALUES (4194317,
	4194305);
INSERT INTO O_ATTR
	VALUES (4194317,
	4194305,
	4194316,
	'ZoomFactor',
	'Description: This is the scaling percentage used for the model.  
Full Name: Zoom Factor
Data Domain: 20 - 300 inclusive.',
	'',
	'ZoomFactor',
	0,
	524292);
INSERT INTO O_NBATTR
	VALUES (4194318,
	4194305);
INSERT INTO O_BATTR
	VALUES (4194318,
	4194305);
INSERT INTO O_ATTR
	VALUES (4194318,
	4194305,
	4194317,
	'GridOn',
	'Description: Indicates if the grid should be shown.
Full Name: Grid On Indicator
Data Domain: If true, the drawing grid is shown on the model. If false, it is not.
',
	'',
	'GridOn',
	0,
	524290);
INSERT INTO O_ID
	VALUES (0,
	4194305);
INSERT INTO O_OIDA
	VALUES (4194305,
	4194305,
	0);
INSERT INTO O_RTIDA
	VALUES (4194305,
	4194305,
	0,
	4194312,
	4194321);
INSERT INTO O_OBJ
	VALUES (4194306,
	'Graphical_Element',
	1001,
	'GD_GE',
	'Graphical element abstracts the different shapes and lines that make up the graphical representation of a model on the canvas.',
	4194312);
INSERT INTO O_NBATTR
	VALUES (4194325,
	4194306);
INSERT INTO O_BATTR
	VALUES (4194325,
	4194306);
INSERT INTO O_ATTR
	VALUES (4194325,
	4194306,
	0,
	'GE_ID',
	'Description: This is an arbitrary identifier for the graphical element.
Full Name: Graphical Element Identifier
',
	'',
	'GE_ID',
	0,
	524291);
INSERT INTO O_REF
	VALUES (4194306,
	4194305,
	0,
	4194305,
	4194312,
	4194322,
	4194321,
	4194326,
	4194310,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (4194326,
	4194306,
	4194305,
	4194305,
	1);
INSERT INTO O_ATTR
	VALUES (4194326,
	4194306,
	4194325,
	'Model_ID',
	'Description: This is the model that this graphical element is part of.
',
	'',
	'Model_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (4194327,
	4194306);
INSERT INTO O_BATTR
	VALUES (4194327,
	4194306);
INSERT INTO O_ATTR
	VALUES (4194327,
	4194306,
	4194326,
	'OOA_ID',
	'Description: The identifier of the OOA model element that this graphical element  is graphical information for.   Note that there may be multiple graphical elements needed to capture all the graphical data for one OOA model element.  E.g., relationships.
Full Name: OOA Identifier

',
	'',
	'OOA_ID',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (4194328,
	4194306);
INSERT INTO O_BATTR
	VALUES (4194328,
	4194306);
INSERT INTO O_ATTR
	VALUES (4194328,
	4194306,
	4194327,
	'OOA_Type',
	'Description: This is the type of the OOA model element that OOA_ID identifies.
Full Name: OOA Type
Data Domain: Subsystem=11, EE=12, Class=21, Imported Class=23, Relationship=24, Communication Path=28, Access Path=29, State=41, Transition=42, Derived Communication Path=47, Derived Access Path=48
',
	'',
	'OOA_Type',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	4194306);
INSERT INTO O_OIDA
	VALUES (4194325,
	4194306,
	0);
INSERT INTO O_RTIDA
	VALUES (4194325,
	4194306,
	0,
	4194311,
	4194317);
INSERT INTO O_RTIDA
	VALUES (4194325,
	4194306,
	0,
	4194305,
	4194306);
INSERT INTO O_RTIDA
	VALUES (4194325,
	4194306,
	0,
	4194306,
	4194308);
INSERT INTO O_OBJ
	VALUES (4194307,
	'Shape',
	1002,
	'GD_SHP',
	'This represents the rectangular space taken by a shape on the model.  The actual shape drawn may be an oval, partitioned rectangle, etc.  Note: sometimes BridgePoint reverses the NW and SE coordinates of the shape when populating the graphical subsystem model.',
	4194312);
INSERT INTO O_REF
	VALUES (4194307,
	4194306,
	0,
	4194325,
	4194311,
	4194320,
	4194317,
	4194330,
	4194305,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (4194330,
	4194307,
	4194325,
	4194306,
	1);
INSERT INTO O_ATTR
	VALUES (4194330,
	4194307,
	0,
	'GE_ID',
	'Description: This is an arbitrary identifier for the shape.
Full Name: Graphical Element Identifier',
	'',
	'GE_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (4194331,
	4194307);
INSERT INTO O_BATTR
	VALUES (4194331,
	4194307);
INSERT INTO O_ATTR
	VALUES (4194331,
	4194307,
	4194330,
	'NW_X',
	'Description: The X coordinate of the upper left corner of the shape.
Full Name: Northwest X coordinate',
	'',
	'NW_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194332,
	4194307);
INSERT INTO O_BATTR
	VALUES (4194332,
	4194307);
INSERT INTO O_ATTR
	VALUES (4194332,
	4194307,
	4194331,
	'NW_Y',
	'Description: The Y coordinate of the upper left corner of the shape.
Full Name: Northwest Y coordinate',
	'',
	'NW_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194333,
	4194307);
INSERT INTO O_BATTR
	VALUES (4194333,
	4194307);
INSERT INTO O_ATTR
	VALUES (4194333,
	4194307,
	4194332,
	'SE_X',
	'Description: The X coordinate of the lower right corner of the shape.
Full Name: Southeast X coordinate',
	'',
	'SE_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194334,
	4194307);
INSERT INTO O_BATTR
	VALUES (4194334,
	4194307);
INSERT INTO O_ATTR
	VALUES (4194334,
	4194307,
	4194333,
	'SE_Y',
	'Description: The Y coordinate of the lower right corner of the shape.
Full Name: Southeast Y coordinate',
	'',
	'SE_Y',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	4194307);
INSERT INTO O_OIDA
	VALUES (4194330,
	4194307,
	0);
INSERT INTO O_OBJ
	VALUES (4194308,
	'Connector',
	1003,
	'GD_CON',
	'A connector represents a connection between two graphical elements.  The connection is represented by a set of lines.',
	4194312);
INSERT INTO O_REF
	VALUES (4194308,
	4194306,
	0,
	4194325,
	4194311,
	4194319,
	4194317,
	4194335,
	4194306,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (4194335,
	4194308,
	4194325,
	4194306,
	1);
INSERT INTO O_ATTR
	VALUES (4194335,
	4194308,
	0,
	'GE_ID',
	'This is an arbitrary identifier for the connector.',
	'',
	'GE_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (4194308,
	4194306,
	0,
	4194325,
	4194306,
	4194307,
	4194308,
	4194336,
	4194307,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (4194336,
	4194308,
	4194325,
	4194306,
	1);
INSERT INTO O_ATTR
	VALUES (4194336,
	4194308,
	4194335,
	'Starting_GE_ID',
	'Description: This is the ID of the graphical element that the connector starts at.
',
	'Starting_',
	'GE_ID',
	1,
	524296);
INSERT INTO O_REF
	VALUES (4194308,
	4194306,
	0,
	4194325,
	4194305,
	4194305,
	4194306,
	4194337,
	4194308,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (4194337,
	4194308,
	4194325,
	4194306,
	1);
INSERT INTO O_ATTR
	VALUES (4194337,
	4194308,
	4194336,
	'Ending_GE_ID',
	'Description: This is the ID of the graphical element that the connector ends at. The ID may be non-participating if the connector represents a creation transition, or the supertype part of a supertype/subtype relationship.
',
	'Ending_',
	'GE_ID',
	1,
	524296);
INSERT INTO O_REF
	VALUES (4194308,
	4194309,
	0,
	4194339,
	4194310,
	4194316,
	4194315,
	4194338,
	4194311,
	0,
	1,
	'The line segment referenced must belong to this connector.
In other words, this assertion must always be true:

Line_Segment[self.assoc_GE_ID].conn_GE_ID == self.GE_ID');
INSERT INTO O_RATTR
	VALUES (4194338,
	4194308,
	4194325,
	4194306,
	1);
INSERT INTO O_ATTR
	VALUES (4194338,
	4194308,
	4194337,
	'Assoc_GE_ID',
	'Description: When the connector represents an associative relationship, this is the ID of the line segment that the associative connector terminates at.',
	'Assoc_',
	'GE_ID',
	1,
	524296);
INSERT INTO O_ID
	VALUES (0,
	4194308);
INSERT INTO O_OIDA
	VALUES (4194335,
	4194308,
	0);
INSERT INTO O_RTIDA
	VALUES (4194335,
	4194308,
	0,
	4194309,
	4194313);
INSERT INTO O_RTIDA
	VALUES (4194335,
	4194308,
	0,
	4194307,
	4194309);
INSERT INTO O_OBJ
	VALUES (4194309,
	'Line_Segment',
	1004,
	'GD_LS',
	'The line segment is a single line drawn on the model. If the line segment is the first or last line segment, there may be adornments (cardinality, multiplicity) drawn near where the line segment attaches to the GE it starts/ends at.  Note: BridgePoint is very arbitrary about how it populates line segments in terms of begins and ends.  Line segments do not always run from start element to end element - sometimes they are reversed. ',
	4194312);
INSERT INTO O_REF
	VALUES (4194309,
	4194306,
	0,
	4194325,
	4194311,
	4194318,
	4194317,
	4194339,
	4194309,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (4194339,
	4194309,
	4194325,
	4194306,
	1);
INSERT INTO O_ATTR
	VALUES (4194339,
	4194309,
	0,
	'GE_ID',
	'Description: This is an arbitrary identifier for the line segment.
Full Name: Graphical Element Identifier',
	'',
	'GE_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (4194309,
	4194308,
	0,
	4194335,
	4194309,
	4194314,
	4194313,
	4194340,
	4194312,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (4194340,
	4194309,
	4194325,
	4194306,
	1);
INSERT INTO O_ATTR
	VALUES (4194340,
	4194309,
	4194339,
	'conn_GE_ID',
	'Description: This is the connector that the line segment is a part of.
Full Name: Connector Graphical Element Identifier
',
	'conn_',
	'GE_ID',
	1,
	524296);
INSERT INTO O_NBATTR
	VALUES (4194341,
	4194309);
INSERT INTO O_BATTR
	VALUES (4194341,
	4194309);
INSERT INTO O_ATTR
	VALUES (4194341,
	4194309,
	4194340,
	'Start_X',
	'Description: This is the X position of the starting point for the line.
Full Name: Starting X Position
',
	'',
	'Start_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194342,
	4194309);
INSERT INTO O_BATTR
	VALUES (4194342,
	4194309);
INSERT INTO O_ATTR
	VALUES (4194342,
	4194309,
	4194341,
	'Start_Y',
	'Description: This is the Y position of the starting point for the line.
Full Name: Starting Y Position',
	'',
	'Start_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194343,
	4194309);
INSERT INTO O_BATTR
	VALUES (4194343,
	4194309);
INSERT INTO O_ATTR
	VALUES (4194343,
	4194309,
	4194342,
	'End_X',
	'Description: This is the X position of the ending point for the line.
Full Name: Ending X Position',
	'',
	'End_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194344,
	4194309);
INSERT INTO O_BATTR
	VALUES (4194344,
	4194309);
INSERT INTO O_ATTR
	VALUES (4194344,
	4194309,
	4194343,
	'End_Y',
	'Description: This is the Y position of the ending point for the line.
Full Name: Ending Y Position',
	'',
	'End_Y',
	0,
	524291);
INSERT INTO O_REF
	VALUES (4194309,
	4194309,
	0,
	4194339,
	4194308,
	4194311,
	4194312,
	4194345,
	4194313,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (4194345,
	4194309,
	4194325,
	4194306,
	1);
INSERT INTO O_ATTR
	VALUES (4194345,
	4194309,
	4194344,
	'Previous_GE_ID',
	'Description: This is the identifier of the previous line segment belonging to the connector.
Full Name: Previous Graphical Element',
	'Previous_',
	'GE_ID',
	1,
	524296);
INSERT INTO O_ID
	VALUES (0,
	4194309);
INSERT INTO O_OIDA
	VALUES (4194339,
	4194309,
	0);
INSERT INTO O_RTIDA
	VALUES (4194339,
	4194309,
	0,
	4194308,
	4194312);
INSERT INTO O_RTIDA
	VALUES (4194339,
	4194309,
	0,
	4194310,
	4194315);
INSERT INTO O_OBJ
	VALUES (4194310,
	'Floating Text',
	1005,
	'GD_CTXT',
	'This class contains the positions for the text at the start, middle and end of the connector.  The positions have a upper left and lower right coordinate, which specifies the size of the box containing the text. The delta coordinate specifies how much to move the box from the default position.  Note: For non supertype/subtype relationships, Start and End are the two relationship phrases and Middle is the relationship number.  For supertype/subtype relationships, Start is the hard-coded phrase, End is the relationship number, and Middle is nothing.  Also note: sometimes BridgePoint reverses the NW and SE coordinates for the connector text when populating the graphical subsystem model.',
	4194312);
INSERT INTO O_REF
	VALUES (4194310,
	4194308,
	0,
	4194335,
	4194307,
	4194310,
	4194309,
	4194346,
	4194314,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (4194346,
	4194310,
	4194325,
	4194306,
	1);
INSERT INTO O_ATTR
	VALUES (4194346,
	4194310,
	0,
	'GE_ID',
	'Description: This is an arbitrary identifier for the shape.
Full Name: Graphical Element ID',
	'',
	'GE_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (4194347,
	4194310);
INSERT INTO O_BATTR
	VALUES (4194347,
	4194310);
INSERT INTO O_ATTR
	VALUES (4194347,
	4194310,
	4194346,
	'Start_NW_X',
	'Description: This is the northwest x coordinate of the starting connector text.  For regular relationships, this is the starting text phrase.  For supertype/subtype relationships, this is the hard-coded phrase disjoint, complete.
Full Name: Starting connector text northwest X coordinate.',
	'',
	'Start_NW_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194348,
	4194310);
INSERT INTO O_BATTR
	VALUES (4194348,
	4194310);
INSERT INTO O_ATTR
	VALUES (4194348,
	4194310,
	4194347,
	'Start_NW_Y',
	'Description: This is the northwest Y coordinate of the starting connector text.  For regular relationships, this is the starting text phrase.  For supertype/subtype relationships, this is the hard-coded phrase disjoint, complete.
Full Name: Starting connector text northwest Y coordinate.',
	'',
	'Start_NW_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194349,
	4194310);
INSERT INTO O_BATTR
	VALUES (4194349,
	4194310);
INSERT INTO O_ATTR
	VALUES (4194349,
	4194310,
	4194348,
	'Start_SE_X',
	'Description: This is the southeast X coordinate of the starting connector text.  For regular relationships, this is the starting text phrase.  For supertype/subtype relationships, this is the hard-coded phrase disjoint, complete.
Full Name: Starting connector text southeast X coordinate.',
	'',
	'Start_SE_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194350,
	4194310);
INSERT INTO O_BATTR
	VALUES (4194350,
	4194310);
INSERT INTO O_ATTR
	VALUES (4194350,
	4194310,
	4194349,
	'Start_SE_Y',
	'Description: This is the southeast Y coordinate of the starting connector text.  For regular relationships, this is the starting text phrase.  For supertype/subtype relationships, this is the hard-coded phrase disjoint, complete.
Full Name: Starting connector text southeast Y coordinate.',
	'',
	'Start_SE_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194351,
	4194310);
INSERT INTO O_BATTR
	VALUES (4194351,
	4194310);
INSERT INTO O_ATTR
	VALUES (4194351,
	4194310,
	4194350,
	'Start_delta_X',
	'Description: The X offset of the starting connector text from the default position.
Full Name: Start Connector Text Delta X Value',
	'',
	'Start_delta_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194352,
	4194310);
INSERT INTO O_BATTR
	VALUES (4194352,
	4194310);
INSERT INTO O_ATTR
	VALUES (4194352,
	4194310,
	4194351,
	'Start_delta_Y',
	'Description: The Y offset of the starting connector text from the default position.
Full Name: Start Connector Text Delta Y Value',
	'',
	'Start_delta_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194353,
	4194310);
INSERT INTO O_BATTR
	VALUES (4194353,
	4194310);
INSERT INTO O_ATTR
	VALUES (4194353,
	4194310,
	4194352,
	'Middle_NW_X',
	'Description: This is the northwest X coordinate of the middle connector text.  For regular relationships, this is the relationship number.  For supertype/subtype relationships this has no value.
Full Name: Middle connector text northwest X coordinate.',
	'',
	'Middle_NW_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194354,
	4194310);
INSERT INTO O_BATTR
	VALUES (4194354,
	4194310);
INSERT INTO O_ATTR
	VALUES (4194354,
	4194310,
	4194353,
	'Middle_NW_Y',
	'Description: This is the northwest Y coordinate of the middle connector text.  For regular relationships, this is the relationship number.  For supertype/subtype relationships this has no value.
Full Name: Middle connector text northwest Y coordinate.',
	'',
	'Middle_NW_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194355,
	4194310);
INSERT INTO O_BATTR
	VALUES (4194355,
	4194310);
INSERT INTO O_ATTR
	VALUES (4194355,
	4194310,
	4194354,
	'Middle_SE_X',
	'Description: This is the southeast X coordinate of the middle connector text.  For regular relationships, this is the relationship number.  For supertype/subtype relationships this has no value.
Full Name: Middle connector text southeast X coordinate.',
	'',
	'Middle_SE_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194356,
	4194310);
INSERT INTO O_BATTR
	VALUES (4194356,
	4194310);
INSERT INTO O_ATTR
	VALUES (4194356,
	4194310,
	4194355,
	'Middle_SE_Y',
	'Description: This is the southeast Y coordinate of the middle connector text.  For regular relationships, this is the relationship number.  For supertype/subtype relationships this has no value.
Full Name: Middle connector text southeast Y coordinate.',
	'',
	'Middle_SE_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194357,
	4194310);
INSERT INTO O_BATTR
	VALUES (4194357,
	4194310);
INSERT INTO O_ATTR
	VALUES (4194357,
	4194310,
	4194356,
	'Middle_delta_X',
	'Description: The X offset of the middle connector text from the default position.
Full Name: Middle Connector Text Delta X Value',
	'',
	'Middle_delta_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194358,
	4194310);
INSERT INTO O_BATTR
	VALUES (4194358,
	4194310);
INSERT INTO O_ATTR
	VALUES (4194358,
	4194310,
	4194357,
	'Middle_delta_Y',
	'Description: The Y offset of the middle connector text from the default position.
Full Name: Middle Connector Text Delta Y Value',
	'',
	'Middle_delta_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194359,
	4194310);
INSERT INTO O_BATTR
	VALUES (4194359,
	4194310);
INSERT INTO O_ATTR
	VALUES (4194359,
	4194310,
	4194358,
	'End_NW_X',
	'Description: This is the northwest X coordinate of the end connector text.  For regular relationships, this is the end relationship phrase.  For supertype/subtype relationships this is the relationship number.
Full Name: End connector text northwest X coordinate.',
	'',
	'End_NW_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194360,
	4194310);
INSERT INTO O_BATTR
	VALUES (4194360,
	4194310);
INSERT INTO O_ATTR
	VALUES (4194360,
	4194310,
	4194359,
	'End_NW_Y',
	'Description: This is the northwest Y coordinate of the end connector text.  For regular relationships, this is the end relationship phrase.  For supertype/subtype relationships this is the relationship number.
Full Name: End connector text northwest Y coordinate.',
	'',
	'End_NW_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194361,
	4194310);
INSERT INTO O_BATTR
	VALUES (4194361,
	4194310);
INSERT INTO O_ATTR
	VALUES (4194361,
	4194310,
	4194360,
	'End_SE_X',
	'Description: This is the southeast X coordinate of the end connector text.  For regular relationships, this is the end relationship phrase.  For supertype/subtype relationships this is the relationship number.
Full Name: End connector text southeast X coordinate.',
	'',
	'End_SE_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194362,
	4194310);
INSERT INTO O_BATTR
	VALUES (4194362,
	4194310);
INSERT INTO O_ATTR
	VALUES (4194362,
	4194310,
	4194361,
	'End_SE_Y',
	'Description: This is the southeast Y coordinate of the end connector text.  For regular relationships, this is the end relationship phrase.  For supertype/subtype relationships this is the relationship number.
Full Name: End connector text southeast Y coordinate.',
	'',
	'End_SE_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194363,
	4194310);
INSERT INTO O_BATTR
	VALUES (4194363,
	4194310);
INSERT INTO O_ATTR
	VALUES (4194363,
	4194310,
	4194362,
	'End_delta_X',
	'Description: The X offset of the ending connector text from the default position.
Full Name: End Connector Text Delta X Value',
	'',
	'End_delta_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (4194364,
	4194310);
INSERT INTO O_BATTR
	VALUES (4194364,
	4194310);
INSERT INTO O_ATTR
	VALUES (4194364,
	4194310,
	4194363,
	'End_delta_Y',
	'Description: The Y offset of the ending connector text from the default position.
Full Name: End Connector Text  Delta Y Value',
	'',
	'End_delta_Y',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	4194310);
INSERT INTO O_OIDA
	VALUES (4194346,
	4194310,
	0);
INSERT INTO R_SIMP
	VALUES (4194305);
INSERT INTO R_REL
	VALUES (4194305,
	1000,
	'A graphical element can be the ending point for zero or more connectors. A connector will not have an ending point shape when it is a creation transition, or the supertype line for a supertype/subtype relationship.',
	4194312);
INSERT INTO R_FORM
	VALUES (4194308,
	4194305,
	4194305,
	0,
	1,
	'is ending point for');
INSERT INTO R_RGO
	VALUES (4194308,
	4194305,
	4194305);
INSERT INTO R_OIR
	VALUES (4194308,
	4194305,
	4194305,
	0);
INSERT INTO R_PART
	VALUES (4194306,
	4194305,
	4194306,
	0,
	1,
	'ends at');
INSERT INTO R_RTO
	VALUES (4194306,
	4194305,
	4194306,
	0);
INSERT INTO R_OIR
	VALUES (4194306,
	4194305,
	4194306,
	0);
INSERT INTO R_SIMP
	VALUES (4194306);
INSERT INTO R_REL
	VALUES (4194306,
	1001,
	'The graphical elements can have zero or more connectors using them for a starting point.',
	4194312);
INSERT INTO R_FORM
	VALUES (4194308,
	4194306,
	4194307,
	1,
	1,
	'is starting point for');
INSERT INTO R_RGO
	VALUES (4194308,
	4194306,
	4194307);
INSERT INTO R_OIR
	VALUES (4194308,
	4194306,
	4194307,
	0);
INSERT INTO R_PART
	VALUES (4194306,
	4194306,
	4194308,
	0,
	0,
	'starts at');
INSERT INTO R_RTO
	VALUES (4194306,
	4194306,
	4194308,
	0);
INSERT INTO R_OIR
	VALUES (4194306,
	4194306,
	4194308,
	0);
INSERT INTO R_SIMP
	VALUES (4194307);
INSERT INTO R_REL
	VALUES (4194307,
	1002,
	'Each connector has text associated with it.  The text is placed at the beginning, middle and end of the connector. ',
	4194312);
INSERT INTO R_PART
	VALUES (4194308,
	4194307,
	4194309,
	0,
	0,
	'is text for');
INSERT INTO R_RTO
	VALUES (4194308,
	4194307,
	4194309,
	0);
INSERT INTO R_OIR
	VALUES (4194308,
	4194307,
	4194309,
	0);
INSERT INTO R_FORM
	VALUES (4194310,
	4194307,
	4194310,
	0,
	0,
	'has text');
INSERT INTO R_RGO
	VALUES (4194310,
	4194307,
	4194310);
INSERT INTO R_OIR
	VALUES (4194310,
	4194307,
	4194310,
	0);
INSERT INTO R_SIMP
	VALUES (4194308);
INSERT INTO R_REL
	VALUES (4194308,
	1003,
	'Line segments are ordered from first to last for each connector.',
	4194312);
INSERT INTO R_FORM
	VALUES (4194309,
	4194308,
	4194311,
	0,
	1,
	'precedes');
INSERT INTO R_RGO
	VALUES (4194309,
	4194308,
	4194311);
INSERT INTO R_OIR
	VALUES (4194309,
	4194308,
	4194311,
	0);
INSERT INTO R_PART
	VALUES (4194309,
	4194308,
	4194312,
	0,
	1,
	'follows');
INSERT INTO R_RTO
	VALUES (4194309,
	4194308,
	4194312,
	0);
INSERT INTO R_OIR
	VALUES (4194309,
	4194308,
	4194312,
	0);
INSERT INTO R_SIMP
	VALUES (4194309);
INSERT INTO R_REL
	VALUES (4194309,
	1004,
	'A connector has one or more line segments.',
	4194312);
INSERT INTO R_PART
	VALUES (4194308,
	4194309,
	4194313,
	0,
	0,
	'is part of');
INSERT INTO R_RTO
	VALUES (4194308,
	4194309,
	4194313,
	0);
INSERT INTO R_OIR
	VALUES (4194308,
	4194309,
	4194313,
	0);
INSERT INTO R_FORM
	VALUES (4194309,
	4194309,
	4194314,
	1,
	0,
	'has');
INSERT INTO R_RGO
	VALUES (4194309,
	4194309,
	4194314);
INSERT INTO R_OIR
	VALUES (4194309,
	4194309,
	4194314,
	0);
INSERT INTO R_SIMP
	VALUES (4194310);
INSERT INTO R_REL
	VALUES (4194310,
	1005,
	'When a connector represents an associative relationship, this relationship captures which line segment in the connector that the association line is connected to.',
	4194312);
INSERT INTO R_PART
	VALUES (4194309,
	4194310,
	4194315,
	0,
	1,
	'ends at');
INSERT INTO R_RTO
	VALUES (4194309,
	4194310,
	4194315,
	0);
INSERT INTO R_OIR
	VALUES (4194309,
	4194310,
	4194315,
	0);
INSERT INTO R_FORM
	VALUES (4194308,
	4194310,
	4194316,
	0,
	1,
	'is the destination for');
INSERT INTO R_RGO
	VALUES (4194308,
	4194310,
	4194316);
INSERT INTO R_OIR
	VALUES (4194308,
	4194310,
	4194316,
	0);
INSERT INTO R_SUBSUP
	VALUES (4194311);
INSERT INTO R_REL
	VALUES (4194311,
	1006,
	'A graphical element can be a shape, connector, or line segment. A connector has no graphical representation, it acts as a collection of line segments that make up the connector.',
	4194312);
INSERT INTO R_SUPER
	VALUES (4194306,
	4194311,
	4194317);
INSERT INTO R_RTO
	VALUES (4194306,
	4194311,
	4194317,
	0);
INSERT INTO R_OIR
	VALUES (4194306,
	4194311,
	4194317,
	0);
INSERT INTO R_SUB
	VALUES (4194309,
	4194311,
	4194318);
INSERT INTO R_RGO
	VALUES (4194309,
	4194311,
	4194318);
INSERT INTO R_OIR
	VALUES (4194309,
	4194311,
	4194318,
	0);
INSERT INTO R_SUB
	VALUES (4194308,
	4194311,
	4194319);
INSERT INTO R_RGO
	VALUES (4194308,
	4194311,
	4194319);
INSERT INTO R_OIR
	VALUES (4194308,
	4194311,
	4194319,
	0);
INSERT INTO R_SUB
	VALUES (4194307,
	4194311,
	4194320);
INSERT INTO R_RGO
	VALUES (4194307,
	4194311,
	4194320);
INSERT INTO R_OIR
	VALUES (4194307,
	4194311,
	4194320,
	0);
INSERT INTO R_SIMP
	VALUES (4194312);
INSERT INTO R_REL
	VALUES (4194312,
	1007,
	'A model contains zero or more (it may be empty) graphical elements.',
	4194312);
INSERT INTO R_PART
	VALUES (4194305,
	4194312,
	4194321,
	0,
	0,
	'is part of');
INSERT INTO R_RTO
	VALUES (4194305,
	4194312,
	4194321,
	0);
INSERT INTO R_OIR
	VALUES (4194305,
	4194312,
	4194321,
	0);
INSERT INTO R_FORM
	VALUES (4194306,
	4194312,
	4194322,
	1,
	1,
	'contains');
INSERT INTO R_RGO
	VALUES (4194306,
	4194312,
	4194322);
INSERT INTO R_OIR
	VALUES (4194306,
	4194312,
	4194322,
	0);
