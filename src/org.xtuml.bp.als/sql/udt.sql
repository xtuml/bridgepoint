-- BP 6.1D content: user_data_type syschar: 3

-- File:      $RCSfile: udt.sql,v $
-- Version:   $Revision: 1.3 $
-- Modified:  $Date: 2003/08/08 19:05:52 $

INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	73245,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	73245,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	73245,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	73245,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	73245,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	73245,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	73245,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	73245,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	73245,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	73245,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	73245,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	73245,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	73245,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	73245,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	73245,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	73245,
	'inst_ref<Timer>',
	'');
INSERT INTO S_UDT
	VALUES (524305,
	524301,
	0);
INSERT INTO S_DT
	VALUES (524305,
	73245,
	'Token',
	'Each node in the syntax tree has a token associated with it.
We define the type here so that we can pass this data to the 
verification functions, which then pass it on to error reporting 
function, if the verification fails.
');
INSERT INTO S_EDT
	VALUES (524308);
INSERT INTO S_DT
	VALUES (524308,
	73245,
	'OalConstants',
	'This datatype supplies the types of actions that can contain OAL.
');
INSERT INTO S_ENUM
	VALUES (524289,
	'FUNCTION_TYPE',
	'',
	524308);
INSERT INTO S_ENUM
	VALUES (524290,
	'BRIDGE_TYPE',
	'',
	524308);
INSERT INTO S_ENUM
	VALUES (524291,
	'OPERATION_TYPE',
	'',
	524308);
INSERT INTO S_ENUM
	VALUES (524292,
	'STATE_TYPE',
	'',
	524308);
INSERT INTO S_ENUM
	VALUES (524293,
	'MDA_TYPE',
	'',
	524308);

