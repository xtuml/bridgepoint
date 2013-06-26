-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (1346846,
	'sync',
	'This domain tests functions (synchronous services).

Functions are tested in the context of integers, booleans, reals, strings, enumerations (BP5.0) dates and timestamps, unique ids, inst<event> and inst_ref<timer>.',
	0,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	1346846,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	1346846,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	1346846,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	1346846,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	1346846,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	1346846,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	1346846,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	1346846,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	1346846,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	1346846,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	1346846,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	1346846,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	1346846,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	1346846,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	1346846,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	1346846,
	'inst_ref<Timer>',
	'');
INSERT INTO S_UDT
	VALUES (524305,
	524290,
	0);
INSERT INTO S_DT
	VALUES (524305,
	1346846,
	'uBoolean',
	'');
INSERT INTO S_UDT
	VALUES (524306,
	524291,
	0);
INSERT INTO S_DT
	VALUES (524306,
	1346846,
	'uInteger',
	'');
INSERT INTO S_UDT
	VALUES (524307,
	524292,
	0);
INSERT INTO S_DT
	VALUES (524307,
	1346846,
	'uReal',
	'');
INSERT INTO S_UDT
	VALUES (524308,
	524293,
	0);
INSERT INTO S_DT
	VALUES (524308,
	1346846,
	'uString',
	'');
INSERT INTO S_UDT
	VALUES (524309,
	524294,
	0);
INSERT INTO S_DT
	VALUES (524309,
	1346846,
	'uUnique',
	'');
INSERT INTO S_EDT
	VALUES (524312);
INSERT INTO S_ENUM
	VALUES (524289,
	'red',
	'',
	524312);
INSERT INTO S_ENUM
	VALUES (524290,
	'orange',
	'',
	524312);
INSERT INTO S_ENUM
	VALUES (524291,
	'yellow',
	'',
	524312);
INSERT INTO S_ENUM
	VALUES (524292,
	'green',
	'',
	524312);
INSERT INTO S_ENUM
	VALUES (524293,
	'blue',
	'',
	524312);
INSERT INTO S_ENUM
	VALUES (524294,
	'purple',
	'',
	524312);
INSERT INTO S_DT
	VALUES (524312,
	1346846,
	'color',
	'');
INSERT INTO S_EDT
	VALUES (524313);
INSERT INTO S_ENUM
	VALUES (524295,
	'one',
	'',
	524313);
INSERT INTO S_ENUM
	VALUES (524296,
	'two',
	'',
	524313);
INSERT INTO S_ENUM
	VALUES (524297,
	'three',
	'',
	524313);
INSERT INTO S_ENUM
	VALUES (524298,
	'four',
	'',
	524313);
INSERT INTO S_ENUM
	VALUES (524299,
	'five',
	'',
	524313);
INSERT INTO S_DT
	VALUES (524313,
	1346846,
	'number',
	'');
INSERT INTO S_SYNC
	VALUES (524289,
	1346846,
	'int',
	'',
	'temp = param.ref;
param.ref = param.val;
return temp;',
	524291,
	1);
INSERT INTO S_SPARM
	VALUES (524289,
	524289,
	'val',
	524291,
	0);
INSERT INTO S_SPARM
	VALUES (524290,
	524289,
	'ref',
	524291,
	1);
INSERT INTO S_SYNC
	VALUES (524290,
	1346846,
	'real',
	'',
	'temp = param.ref;
param.ref = param.val;
return temp;',
	524292,
	1);
INSERT INTO S_SPARM
	VALUES (524291,
	524290,
	'ref',
	524292,
	1);
INSERT INTO S_SPARM
	VALUES (524292,
	524290,
	'val',
	524292,
	0);
INSERT INTO S_SYNC
	VALUES (524291,
	1346846,
	'string',
	'',
	'temp = param.ref;
param.ref = param.val;
return temp;',
	524293,
	1);
INSERT INTO S_SPARM
	VALUES (524293,
	524291,
	'ref',
	524293,
	1);
INSERT INTO S_SPARM
	VALUES (524294,
	524291,
	'val',
	524293,
	0);
INSERT INTO S_SYNC
	VALUES (524292,
	1346846,
	'bool',
	'',
	'LOG::LogSuccess(message:"Entered action language for function ''bool''");

temp = param.ref;
param.ref = param.val;
return temp;',
	524290,
	1);
INSERT INTO S_SPARM
	VALUES (524295,
	524292,
	'ref',
	524290,
	1);
INSERT INTO S_SPARM
	VALUES (524296,
	524292,
	'val',
	524290,
	0);
INSERT INTO S_SYNC
	VALUES (524293,
	1346846,
	'date',
	'',
	'temp = param.ref;
param.ref = param.val;
return temp;',
	524302,
	1);
INSERT INTO S_SPARM
	VALUES (524297,
	524293,
	'ref',
	524302,
	1);
INSERT INTO S_SPARM
	VALUES (524298,
	524293,
	'val',
	524302,
	0);
INSERT INTO S_SYNC
	VALUES (524294,
	1346846,
	'time',
	'',
	'temp = param.ref;
param.ref = param.val;
return temp;',
	524303,
	1);
INSERT INTO S_SPARM
	VALUES (524299,
	524294,
	'ref',
	524303,
	1);
INSERT INTO S_SPARM
	VALUES (524300,
	524294,
	'val',
	524303,
	0);
INSERT INTO S_SYNC
	VALUES (524295,
	1346846,
	'color',
	'',
	'temp = param.ref;
param.ref = param.val;
return temp;',
	524312,
	1);
INSERT INTO S_SPARM
	VALUES (524301,
	524295,
	'ref',
	524312,
	1);
INSERT INTO S_SPARM
	VALUES (524302,
	524295,
	'val',
	524312,
	0);
INSERT INTO S_SYNC
	VALUES (524296,
	1346846,
	'inst',
	'',
	'temp = param.ref;
param.ref = param.val;
return temp;',
	524299,
	1);
INSERT INTO S_SPARM
	VALUES (524303,
	524296,
	'ref',
	524299,
	1);
INSERT INTO S_SPARM
	VALUES (524304,
	524296,
	'val',
	524299,
	0);
INSERT INTO S_SYNC
	VALUES (524297,
	1346846,
	'inst_ref',
	'',
	'temp = param.ref;
param.ref = param.val;
return temp;',
	524304,
	1);
INSERT INTO S_SPARM
	VALUES (524305,
	524297,
	'ref',
	524304,
	1);
INSERT INTO S_SPARM
	VALUES (524306,
	524297,
	'val',
	524304,
	0);
INSERT INTO S_SYNC
	VALUES (524298,
	1346846,
	'number',
	'',
	'temp = param.ref;
param.ref = param.val;
return temp;',
	524313,
	1);
INSERT INTO S_SPARM
	VALUES (524307,
	524298,
	'ref',
	524313,
	1);
INSERT INTO S_SPARM
	VALUES (524308,
	524298,
	'val',
	524313,
	0);
INSERT INTO S_SYNC
	VALUES (524299,
	1346846,
	'uBool',
	'',
	'temp = param.ref;
param.ref = param.val;
return temp;',
	524305,
	1);
INSERT INTO S_SPARM
	VALUES (524309,
	524299,
	'val',
	524305,
	0);
INSERT INTO S_SPARM
	VALUES (524310,
	524299,
	'ref',
	524305,
	1);
INSERT INTO S_SYNC
	VALUES (524302,
	1346846,
	'uInt',
	'',
	'temp = param.ref;
param.ref = param.val;
return temp;',
	524306,
	1);
INSERT INTO S_SPARM
	VALUES (524315,
	524302,
	'ref',
	524306,
	1);
INSERT INTO S_SPARM
	VALUES (524316,
	524302,
	'val',
	524306,
	0);
INSERT INTO S_SYNC
	VALUES (524303,
	1346846,
	'uUnique',
	'',
	'temp = param.ref;
param.ref = param.val;
return temp;',
	524294,
	1);
INSERT INTO S_SPARM
	VALUES (524317,
	524303,
	'ref',
	524309,
	1);
INSERT INTO S_SPARM
	VALUES (524318,
	524303,
	'val',
	524309,
	0);
INSERT INTO S_SYNC
	VALUES (524304,
	1346846,
	'unique',
	'',
	'temp = param.ref;
param.ref = param.val;
return temp;',
	524294,
	1);
INSERT INTO S_SPARM
	VALUES (524319,
	524304,
	'val',
	524294,
	0);
INSERT INTO S_SPARM
	VALUES (524320,
	524304,
	'ref',
	524294,
	1);
INSERT INTO S_SYNC
	VALUES (524305,
	1346846,
	'uReal',
	'',
	'temp = param.ref;
param.ref = param.val;
return temp;',
	524307,
	1);
INSERT INTO S_SPARM
	VALUES (524321,
	524305,
	'ref',
	524307,
	1);
INSERT INTO S_SPARM
	VALUES (524322,
	524305,
	'val',
	524307,
	0);
INSERT INTO S_SYNC
	VALUES (524306,
	1346846,
	'uString',
	'',
	'temp = param.ref;
param.ref = param.val;
return temp;',
	524308,
	1);
INSERT INTO S_SPARM
	VALUES (524323,
	524306,
	'ref',
	524308,
	1);
INSERT INTO S_SPARM
	VALUES (524324,
	524306,
	'val',
	524308,
	0);
INSERT INTO S_SYNC
	VALUES (524307,
	1346846,
	'void',
	'',
	'LOG::LogSuccess(message:"Entered action language for function ''void''");',
	524289,
	1);
INSERT INTO S_EE
	VALUES (524289,
	'Time',
	'',
	'TIM',
	1346846);
INSERT INTO S_BRG
	VALUES (524289,
	524289,
	'current_date',
	'',
	1,
	524302,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524290,
	524289,
	'create_date',
	'',
	1,
	524302,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524325,
	524290,
	'second',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524326,
	524290,
	'minute',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524327,
	524290,
	'hour',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524328,
	524290,
	'day',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524329,
	524290,
	'month',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524330,
	524290,
	'year',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524291,
	524289,
	'get_second',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524331,
	524291,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524292,
	524289,
	'get_minute',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524332,
	524292,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524293,
	524289,
	'get_hour',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524333,
	524293,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524294,
	524289,
	'get_day',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524334,
	524294,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524295,
	524289,
	'get_month',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524335,
	524295,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524296,
	524289,
	'get_year',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524336,
	524296,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524297,
	524289,
	'current_clock',
	'',
	1,
	524303,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524298,
	524289,
	'timer_start',
	'',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524337,
	524298,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524338,
	524298,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524299,
	524289,
	'timer_start_recurring',
	'',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524339,
	524299,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524340,
	524299,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524300,
	524289,
	'timer_remaining_time',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524341,
	524300,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BRG
	VALUES (524301,
	524289,
	'timer_reset_time',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524342,
	524301,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524343,
	524301,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524302,
	524289,
	'timer_add_time',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524344,
	524302,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524345,
	524302,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524303,
	524289,
	'timer_cancel',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524346,
	524303,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_EE
	VALUES (524290,
	'Logging',
	'',
	'LOG',
	1346846);
INSERT INTO S_BRG
	VALUES (524304,
	524290,
	'LogInfo',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524347,
	524304,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524305,
	524290,
	'LogFailure',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524348,
	524305,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524306,
	524290,
	'LogSuccess',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524349,
	524306,
	'message',
	524293,
	0);
INSERT INTO S_EE
	VALUES (524292,
	'Architecture',
	'',
	'ARCH',
	1346846);
INSERT INTO S_BRG
	VALUES (524326,
	524292,
	'shutdown',
	'',
	0,
	524289,
	'control stop;',
	1);
INSERT INTO GD_MD
	VALUES (524289,
	1,
	1346846,
	1,
	1,
	0,
	1,
	1,
	0,
	12,
	1719,
	4285,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (524319,
	524289,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524319,
	1920,
	1344,
	2080,
	1440);
INSERT INTO GD_GE
	VALUES (524320,
	524289,
	2097156,
	11);
INSERT INTO GD_SHP
	VALUES (524320,
	1920,
	1216,
	2080,
	1312);
INSERT INTO GD_GE
	VALUES (524293,
	524289,
	524289,
	12);
INSERT INTO GD_SHP
	VALUES (524293,
	2160,
	1488,
	2320,
	1584);
INSERT INTO GD_GE
	VALUES (524294,
	524289,
	524290,
	12);
INSERT INTO GD_SHP
	VALUES (524294,
	1920,
	1488,
	2080,
	1584);
INSERT INTO GD_GE
	VALUES (524341,
	524289,
	524292,
	12);
INSERT INTO GD_SHP
	VALUES (524341,
	2160,
	1344,
	2320,
	1440);
INSERT INTO S_SS
	VALUES (1048578,
	'Objects',
	'',
	'',
	1,
	1346846,
	1048578);
INSERT INTO O_OBJ
	VALUES (1048577,
	'Integer',
	1,
	'INT',
	'',
	1048578);
INSERT INTO O_TFR
	VALUES (1048577,
	1048577,
	'uInt',
	'',
	524306,
	0,
	'param.ref = 2 * param.ref;
return param.val;',
	1);
INSERT INTO O_TPARM
	VALUES (1048577,
	1048577,
	'val',
	524306,
	0);
INSERT INTO O_TPARM
	VALUES (1048578,
	1048577,
	'ref',
	524306,
	1);
INSERT INTO O_TFR
	VALUES (1048578,
	1048577,
	'int',
	'',
	524291,
	0,
	'param.ref = 2 * param.ref;
return param.val;',
	1);
INSERT INTO O_TPARM
	VALUES (1048579,
	1048578,
	'val',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (1048580,
	1048578,
	'ref',
	524291,
	1);
INSERT INTO O_TFR
	VALUES (1048579,
	1048577,
	'int_i',
	'',
	524291,
	1,
	'param.ref = 2 * param.ref;
return param.val + self.width;',
	1);
INSERT INTO O_TPARM
	VALUES (1048581,
	1048579,
	'ref',
	524291,
	1);
INSERT INTO O_TPARM
	VALUES (1048582,
	1048579,
	'val',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (1048580,
	1048577,
	'uInt_i',
	'',
	524306,
	1,
	'param.ref = 2 * param.ref;
return param.val + self.width;',
	1);
INSERT INTO O_TPARM
	VALUES (1048583,
	1048580,
	'ref',
	524306,
	1);
INSERT INTO O_TPARM
	VALUES (1048584,
	1048580,
	'val',
	524306,
	0);
INSERT INTO O_NBATTR
	VALUES (1048577,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048577,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048577,
	1048577,
	0,
	'width',
	'',
	'',
	'width',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048578,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048578,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048578,
	1048577,
	1048577,
	'length',
	'',
	'',
	'length',
	0,
	524306);
INSERT INTO O_OBJ
	VALUES (1048578,
	'Real',
	2,
	'REAL',
	'',
	1048578);
INSERT INTO O_TFR
	VALUES (1048581,
	1048578,
	'uReal',
	'',
	524307,
	0,
	'param.ref = 2 * param.ref;
return param.val;',
	1);
INSERT INTO O_TPARM
	VALUES (1048585,
	1048581,
	'ref',
	524307,
	1);
INSERT INTO O_TPARM
	VALUES (1048586,
	1048581,
	'val',
	524307,
	0);
INSERT INTO O_TFR
	VALUES (1048582,
	1048578,
	'real',
	'',
	524292,
	0,
	'param.ref = 2 * param.ref;
return param.val;',
	1);
INSERT INTO O_TPARM
	VALUES (1048587,
	1048582,
	'ref',
	524292,
	1);
INSERT INTO O_TPARM
	VALUES (1048588,
	1048582,
	'val',
	524292,
	0);
INSERT INTO O_TFR
	VALUES (1048583,
	1048578,
	'real_i',
	'',
	524292,
	1,
	'param.ref = 2 * param.ref;
return param.val + self.width;',
	1);
INSERT INTO O_TPARM
	VALUES (1048589,
	1048583,
	'ref',
	524292,
	1);
INSERT INTO O_TPARM
	VALUES (1048590,
	1048583,
	'val',
	524292,
	0);
INSERT INTO O_TFR
	VALUES (1048584,
	1048578,
	'uReal_i',
	'',
	524307,
	1,
	'param.ref = 2 * param.ref;
return param.val + self.width;',
	1);
INSERT INTO O_TPARM
	VALUES (1048591,
	1048584,
	'ref',
	524307,
	1);
INSERT INTO O_TPARM
	VALUES (1048592,
	1048584,
	'val',
	524307,
	0);
INSERT INTO O_NBATTR
	VALUES (1048581,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048581,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048581,
	1048578,
	0,
	'width',
	'',
	'',
	'width',
	0,
	524292);
INSERT INTO O_NBATTR
	VALUES (1048582,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048582,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048582,
	1048578,
	1048581,
	'length',
	'',
	'',
	'length',
	0,
	524307);
INSERT INTO O_OBJ
	VALUES (1048579,
	'Boolean',
	3,
	'BOOL',
	'',
	1048578);
INSERT INTO O_TFR
	VALUES (1048585,
	1048579,
	'bool',
	'',
	524305,
	0,
	'if param.ref == true
	param.ref = false;
else
	param.ref = true;
end if;

return param.val;',
	1);
INSERT INTO O_TPARM
	VALUES (1048593,
	1048585,
	'ref',
	524290,
	1);
INSERT INTO O_TPARM
	VALUES (1048594,
	1048585,
	'val',
	524290,
	0);
INSERT INTO O_TFR
	VALUES (1048586,
	1048579,
	'uBool',
	'',
	524305,
	0,
	'if param.ref == true
	param.ref = false;
else
	param.ref = true;
end if;

return param.val;',
	1);
INSERT INTO O_TPARM
	VALUES (1048595,
	1048586,
	'ref',
	524305,
	1);
INSERT INTO O_TPARM
	VALUES (1048596,
	1048586,
	'val',
	524305,
	0);
INSERT INTO O_TFR
	VALUES (1048587,
	1048579,
	'bool_i',
	'',
	524290,
	1,
	'if param.ref == true
	param.ref = self.q;
else
	param.ref = self.p;
end if;

return param.val;',
	1);
INSERT INTO O_TPARM
	VALUES (1048597,
	1048587,
	'ref',
	524290,
	1);
INSERT INTO O_TPARM
	VALUES (1048598,
	1048587,
	'val',
	524290,
	0);
INSERT INTO O_TFR
	VALUES (1048588,
	1048579,
	'uBool_i',
	'',
	524305,
	1,
	'if param.ref == true
	param.ref = self.q;
else
	param.ref = self.p;
end if;

return param.val;',
	1);
INSERT INTO O_TPARM
	VALUES (1048599,
	1048588,
	'ref',
	524305,
	1);
INSERT INTO O_TPARM
	VALUES (1048600,
	1048588,
	'val',
	524305,
	0);
INSERT INTO O_NBATTR
	VALUES (1048585,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048585,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048585,
	1048579,
	0,
	'p',
	'',
	'',
	'p',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (1048586,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048586,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048586,
	1048579,
	1048585,
	'q',
	'',
	'',
	'q',
	0,
	524305);
INSERT INTO O_OBJ
	VALUES (1048580,
	'String',
	4,
	'STR',
	'',
	1048578);
INSERT INTO O_TFR
	VALUES (1048589,
	1048580,
	'string',
	'',
	524293,
	0,
	'param.ref = "New string";
return param.val;',
	1);
INSERT INTO O_TPARM
	VALUES (1048601,
	1048589,
	'ref',
	524293,
	1);
INSERT INTO O_TPARM
	VALUES (1048602,
	1048589,
	'val',
	524293,
	0);
INSERT INTO O_TFR
	VALUES (1048590,
	1048580,
	'uString',
	'',
	524308,
	0,
	'param.ref = "New string";
return param.val;',
	1);
INSERT INTO O_TPARM
	VALUES (1048603,
	1048590,
	'ref',
	524308,
	1);
INSERT INTO O_TPARM
	VALUES (1048604,
	1048590,
	'val',
	524308,
	0);
INSERT INTO O_TFR
	VALUES (1048591,
	1048580,
	'string_i',
	'',
	524293,
	1,
	'param.ref = "New string" + self.first;
return param.val;',
	1);
INSERT INTO O_TPARM
	VALUES (1048605,
	1048591,
	'ref',
	524293,
	1);
INSERT INTO O_TPARM
	VALUES (1048606,
	1048591,
	'val',
	524293,
	0);
INSERT INTO O_TFR
	VALUES (1048592,
	1048580,
	'uString_i',
	'',
	524308,
	1,
	'param.ref = "New string" + self.first;
return param.val;',
	1);
INSERT INTO O_TPARM
	VALUES (1048607,
	1048592,
	'ref',
	524308,
	1);
INSERT INTO O_TPARM
	VALUES (1048608,
	1048592,
	'val',
	524308,
	0);
INSERT INTO O_NBATTR
	VALUES (1048589,
	1048580);
INSERT INTO O_BATTR
	VALUES (1048589,
	1048580);
INSERT INTO O_ATTR
	VALUES (1048589,
	1048580,
	0,
	'first',
	'',
	'',
	'first',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048590,
	1048580);
INSERT INTO O_BATTR
	VALUES (1048590,
	1048580);
INSERT INTO O_ATTR
	VALUES (1048590,
	1048580,
	1048589,
	'last',
	'',
	'',
	'last',
	0,
	524308);
INSERT INTO O_OBJ
	VALUES (1048583,
	'Date and Time',
	7,
	'DT',
	'',
	1048578);
INSERT INTO O_TFR
	VALUES (1048601,
	1048583,
	'date',
	'',
	524302,
	0,
	'day = TIM::get_day(date:param.val);
month = TIM::get_month(date:param.val);
year = TIM::get_year(date:param.val);
second = TIM::get_second(date:param.val);
minute = TIM::get_minute(date:param.val);
hour = TIM::get_hour(date:param.val);

param.ref = TIM::create_date(day:day, month:month, year:year+1, second:second, minute:minute, hour:hour);

return param.val;',
	1);
INSERT INTO O_TPARM
	VALUES (1048625,
	1048601,
	'ref',
	524302,
	1);
INSERT INTO O_TPARM
	VALUES (1048626,
	1048601,
	'val',
	524302,
	0);
INSERT INTO O_TFR
	VALUES (1048602,
	1048583,
	'time',
	'',
	524303,
	0,
	'param.ref = param.val;

return param.val;',
	1);
INSERT INTO O_TPARM
	VALUES (1048627,
	1048602,
	'ref',
	524303,
	1);
INSERT INTO O_TPARM
	VALUES (1048628,
	1048602,
	'val',
	524303,
	0);
INSERT INTO O_TFR
	VALUES (1048603,
	1048583,
	'date_i',
	'',
	524302,
	1,
	'day = TIM::get_day(date:param.val);
month = TIM::get_month(date:param.val);
year = TIM::get_year(date:param.val);
second = TIM::get_second(date:self.date);
minute = TIM::get_minute(date:self.date);
hour = TIM::get_hour(date:self.date);

param.ref = TIM::create_date(day:day, month:month, year:year+1, second:second, minute:minute, hour:hour);

return param.val;',
	1);
INSERT INTO O_TPARM
	VALUES (1048629,
	1048603,
	'ref',
	524302,
	1);
INSERT INTO O_TPARM
	VALUES (1048630,
	1048603,
	'val',
	524302,
	0);
INSERT INTO O_TFR
	VALUES (1048604,
	1048583,
	'time_i',
	'',
	524303,
	1,
	'param.ref = param.val;
return self.time;',
	1);
INSERT INTO O_TPARM
	VALUES (1048631,
	1048604,
	'ref',
	524303,
	1);
INSERT INTO O_TPARM
	VALUES (1048632,
	1048604,
	'val',
	524303,
	0);
INSERT INTO O_NBATTR
	VALUES (1048605,
	1048583);
INSERT INTO O_BATTR
	VALUES (1048605,
	1048583);
INSERT INTO O_ATTR
	VALUES (1048605,
	1048583,
	0,
	'date',
	'',
	'',
	'date',
	0,
	524302);
INSERT INTO O_NBATTR
	VALUES (1048606,
	1048583);
INSERT INTO O_BATTR
	VALUES (1048606,
	1048583);
INSERT INTO O_ATTR
	VALUES (1048606,
	1048583,
	1048605,
	'time',
	'',
	'',
	'time',
	0,
	524303);
INSERT INTO O_OBJ
	VALUES (1048584,
	'Unique',
	8,
	'UNI',
	'',
	1048578);
INSERT INTO O_TFR
	VALUES (1048605,
	1048584,
	'unique',
	'',
	524294,
	0,
	'temp = param.ref;
param.ref = param.val;
return temp;',
	1);
INSERT INTO O_TPARM
	VALUES (1048633,
	1048605,
	'ref',
	524294,
	1);
INSERT INTO O_TPARM
	VALUES (1048634,
	1048605,
	'val',
	524294,
	0);
INSERT INTO O_TFR
	VALUES (1048606,
	1048584,
	'uUnique',
	'',
	524309,
	0,
	'temp = param.ref;
param.ref = param.val;
return temp;',
	1);
INSERT INTO O_TPARM
	VALUES (1048635,
	1048606,
	'ref',
	524309,
	1);
INSERT INTO O_TPARM
	VALUES (1048636,
	1048606,
	'val',
	524309,
	0);
INSERT INTO O_TFR
	VALUES (1048607,
	1048584,
	'unique_i',
	'',
	524294,
	1,
	'param.ref = param.val;
return self.one;',
	1);
INSERT INTO O_TPARM
	VALUES (1048637,
	1048607,
	'ref',
	524294,
	1);
INSERT INTO O_TPARM
	VALUES (1048638,
	1048607,
	'val',
	524294,
	0);
INSERT INTO O_TFR
	VALUES (1048608,
	1048584,
	'uUnique_i',
	'',
	524309,
	1,
	'param.ref = param.val;
return self.two;',
	1);
INSERT INTO O_TPARM
	VALUES (1048639,
	1048608,
	'val',
	524309,
	0);
INSERT INTO O_TPARM
	VALUES (1048640,
	1048608,
	'ref',
	524309,
	1);
INSERT INTO O_NBATTR
	VALUES (1048609,
	1048584);
INSERT INTO O_BATTR
	VALUES (1048609,
	1048584);
INSERT INTO O_ATTR
	VALUES (1048609,
	1048584,
	0,
	'one',
	'',
	'',
	'one',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048610,
	1048584);
INSERT INTO O_BATTR
	VALUES (1048610,
	1048584);
INSERT INTO O_ATTR
	VALUES (1048610,
	1048584,
	1048609,
	'two',
	'',
	'',
	'two',
	0,
	524309);
INSERT INTO O_OBJ
	VALUES (1048585,
	'Enumeration',
	9,
	'ENUM',
	'',
	1048578);
INSERT INTO O_TFR
	VALUES (1048609,
	1048585,
	'size3',
	'',
	524313,
	0,
	'if (param.val == number::one)
	param.ref = number::two;
elif (param.val == number::two)
	param.ref = number::three;
elif (param.val == number::three)
	param.ref = number::four;
elif (param.val == number::four)
	param.ref = number::five;
else
	param.ref = number::one; 
end if;

return param.val;',
	1);
INSERT INTO O_TPARM
	VALUES (1048641,
	1048609,
	'ref',
	524313,
	1);
INSERT INTO O_TPARM
	VALUES (1048642,
	1048609,
	'val',
	524313,
	0);
INSERT INTO O_TFR
	VALUES (1048610,
	1048585,
	'color3',
	'',
	524312,
	0,
	'temp = param.ref;
param.ref = param.val;
return temp;
',
	1);
INSERT INTO O_TPARM
	VALUES (1048643,
	1048610,
	'ref',
	524312,
	1);
INSERT INTO O_TPARM
	VALUES (1048644,
	1048610,
	'val',
	524312,
	0);
INSERT INTO O_TFR
	VALUES (1048611,
	1048585,
	'color_i',
	'',
	524312,
	1,
	'param.ref = param.val;
return self.color;',
	1);
INSERT INTO O_TPARM
	VALUES (1048645,
	1048611,
	'ref',
	524312,
	1);
INSERT INTO O_TPARM
	VALUES (1048646,
	1048611,
	'val',
	524312,
	0);
INSERT INTO O_TFR
	VALUES (1048612,
	1048585,
	'size_i',
	'',
	524313,
	1,
	'param.ref = param.val;
return self.size;',
	1);
INSERT INTO O_TPARM
	VALUES (1048647,
	1048612,
	'ref',
	524313,
	1);
INSERT INTO O_TPARM
	VALUES (1048648,
	1048612,
	'val',
	524313,
	0);
INSERT INTO O_NBATTR
	VALUES (1048611,
	1048585);
INSERT INTO O_BATTR
	VALUES (1048611,
	1048585);
INSERT INTO O_ATTR
	VALUES (1048611,
	1048585,
	0,
	'color',
	'',
	'',
	'color',
	0,
	524312);
INSERT INTO O_NBATTR
	VALUES (1048612,
	1048585);
INSERT INTO O_BATTR
	VALUES (1048612,
	1048585);
INSERT INTO O_ATTR
	VALUES (1048612,
	1048585,
	1048611,
	'size',
	'',
	'',
	'size',
	0,
	524313);
INSERT INTO O_OBJ
	VALUES (1048586,
	'Instance',
	10,
	'INST',
	'',
	1048578);
INSERT INTO O_TFR
	VALUES (1048613,
	1048586,
	'inst',
	'',
	524299,
	0,
	'temp = param.ref;
param.ref = param.val;
return temp;',
	1);
INSERT INTO O_TPARM
	VALUES (1048649,
	1048613,
	'ref',
	524299,
	1);
INSERT INTO O_TPARM
	VALUES (1048650,
	1048613,
	'val',
	524299,
	0);
INSERT INTO O_TFR
	VALUES (1048615,
	1048586,
	'inst_i',
	'',
	524299,
	1,
	'param.ref = param.val;
return self.one;',
	1);
INSERT INTO O_TPARM
	VALUES (1048653,
	1048615,
	'ref',
	524299,
	1);
INSERT INTO O_TPARM
	VALUES (1048654,
	1048615,
	'val',
	524299,
	0);
INSERT INTO O_NBATTR
	VALUES (1048615,
	1048586);
INSERT INTO O_BATTR
	VALUES (1048615,
	1048586);
INSERT INTO O_ATTR
	VALUES (1048615,
	1048586,
	0,
	'one',
	'',
	'',
	'one',
	0,
	524299);
INSERT INTO O_NBATTR
	VALUES (1048616,
	1048586);
INSERT INTO O_BATTR
	VALUES (1048616,
	1048586);
INSERT INTO O_ATTR
	VALUES (1048616,
	1048586,
	1048615,
	'two',
	'',
	'',
	'two',
	0,
	524299);
INSERT INTO O_DBATTR
	VALUES (1048617,
	1048586,
	'self.three = self.two;',
	1);
INSERT INTO O_BATTR
	VALUES (1048617,
	1048586);
INSERT INTO O_ATTR
	VALUES (1048617,
	1048586,
	1048616,
	'three',
	'',
	'',
	'three',
	0,
	524299);
INSERT INTO O_NBATTR
	VALUES (1048621,
	1048586);
INSERT INTO O_BATTR
	VALUES (1048621,
	1048586);
INSERT INTO O_ATTR
	VALUES (1048621,
	1048586,
	1048617,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO SM_ISM
	VALUES (3670023,
	1048586);
INSERT INTO SM_SM
	VALUES (3670023,
	'',
	7);
INSERT INTO SM_MOORE
	VALUES (3670023);
INSERT INTO SM_EVTDI
	VALUES (3670018,
	3670023,
	'int',
	'',
	524291);
INSERT INTO SM_LEVT
	VALUES (3670019,
	3670023,
	0);
INSERT INTO SM_SEVT
	VALUES (3670019,
	3670023,
	0);
INSERT INTO SM_EVT
	VALUES (3670019,
	3670023,
	0,
	1,
	'One',
	0,
	'',
	'INST1',
	'');
INSERT INTO SM_LEVT
	VALUES (3670020,
	3670023,
	0);
INSERT INTO SM_SEVT
	VALUES (3670020,
	3670023,
	0);
INSERT INTO SM_EVT
	VALUES (3670020,
	3670023,
	0,
	2,
	'Two',
	0,
	'',
	'INST2',
	'');
INSERT INTO SM_STATE
	VALUES (3670018,
	3670023,
	0,
	'one',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (3670018,
	3670019,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670018,
	3670020,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670018,
	3670023,
	3670018);
INSERT INTO SM_AH
	VALUES (3670018,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670018,
	3670023,
	1,
	'',
	'');
INSERT INTO SM_STATE
	VALUES (3670019,
	3670023,
	0,
	'two',
	2,
	0);
INSERT INTO SM_EIGN
	VALUES (3670019,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670019,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670019,
	3670020,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670020,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670019,
	3670023,
	3670019);
INSERT INTO SM_AH
	VALUES (3670019,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670019,
	3670023,
	1,
	'',
	'');
INSERT INTO SM_NSTXN
	VALUES (3670017,
	3670023,
	3670018,
	3670019,
	0);
INSERT INTO SM_TXN
	VALUES (3670017,
	3670023,
	3670018,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670019,
	3670023,
	3670018,
	3670020,
	0);
INSERT INTO SM_TXN
	VALUES (3670019,
	3670023,
	3670019,
	0);
INSERT INTO GD_MD
	VALUES (3670017,
	8,
	3670023,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	4199,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (3670019,
	3670017,
	3670018,
	41);
INSERT INTO GD_SHP
	VALUES (3670019,
	1936,
	1360,
	2096,
	1472);
INSERT INTO GD_GE
	VALUES (3670030,
	3670017,
	3670019,
	41);
INSERT INTO GD_SHP
	VALUES (3670030,
	1936,
	1568,
	2096,
	1680);
INSERT INTO GD_GE
	VALUES (3670020,
	3670017,
	3670017,
	42);
INSERT INTO GD_CON
	VALUES (3670020,
	3670019,
	3670019,
	0);
INSERT INTO GD_CTXT
	VALUES (3670020,
	0,
	0,
	0,
	0,
	0,
	0,
	1941,
	1303,
	2009,
	1327,
	-129,
	-47,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670021,
	3670020,
	2096,
	1408,
	2144,
	1408,
	0);
INSERT INTO GD_LS
	VALUES (3670022,
	3670020,
	2144,
	1408,
	2144,
	1312,
	3670021);
INSERT INTO GD_LS
	VALUES (3670023,
	3670020,
	2144,
	1312,
	2064,
	1312,
	3670022);
INSERT INTO GD_LS
	VALUES (3670024,
	3670020,
	2064,
	1312,
	2064,
	1360,
	3670023);
INSERT INTO GD_GE
	VALUES (3670031,
	3670017,
	3670019,
	42);
INSERT INTO GD_CON
	VALUES (3670031,
	3670019,
	3670030,
	0);
INSERT INTO GD_CTXT
	VALUES (3670031,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670032,
	3670031,
	2000,
	1472,
	2000,
	1568,
	0);
INSERT INTO O_OBJ
	VALUES (1048587,
	'Instance Ref',
	11,
	'IREF',
	'',
	1048578);
INSERT INTO O_TFR
	VALUES (1048617,
	1048587,
	'inst_ref',
	'',
	524304,
	0,
	'temp = param.ref;
param.ref = param.val;
return temp;',
	1);
INSERT INTO O_TPARM
	VALUES (1048657,
	1048617,
	'ref',
	524304,
	1);
INSERT INTO O_TPARM
	VALUES (1048658,
	1048617,
	'val',
	524304,
	0);
INSERT INTO O_TFR
	VALUES (1048619,
	1048587,
	'inst_ref_i',
	'',
	524304,
	1,
	'param.ref = param.val;
return self.one;',
	1);
INSERT INTO O_TPARM
	VALUES (1048661,
	1048619,
	'ref',
	524304,
	1);
INSERT INTO O_TPARM
	VALUES (1048662,
	1048619,
	'val',
	524304,
	0);
INSERT INTO O_NBATTR
	VALUES (1048622,
	1048587);
INSERT INTO O_BATTR
	VALUES (1048622,
	1048587);
INSERT INTO O_ATTR
	VALUES (1048622,
	1048587,
	0,
	'one',
	'',
	'',
	'one',
	0,
	524304);
INSERT INTO O_DBATTR
	VALUES (1048625,
	1048587,
	'self.three = self.one;',
	1);
INSERT INTO O_BATTR
	VALUES (1048625,
	1048587);
INSERT INTO O_ATTR
	VALUES (1048625,
	1048587,
	1048622,
	'three',
	'',
	'',
	'three',
	0,
	524304);
INSERT INTO GD_MD
	VALUES (1048584,
	5,
	1048578,
	11,
	1,
	0,
	1,
	1,
	0,
	12,
	1262,
	4197,
	0.736663,
	0);
INSERT INTO GD_GE
	VALUES (1048587,
	1048584,
	1048577,
	21);
INSERT INTO GD_SHP
	VALUES (1048587,
	1284,
	1007,
	1556,
	1215);
INSERT INTO GD_GE
	VALUES (1048588,
	1048584,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048588,
	1284,
	1247,
	1556,
	1455);
INSERT INTO GD_GE
	VALUES (1048589,
	1048584,
	1048579,
	21);
INSERT INTO GD_SHP
	VALUES (1048589,
	1588,
	1007,
	1860,
	1215);
INSERT INTO GD_GE
	VALUES (1048590,
	1048584,
	1048580,
	21);
INSERT INTO GD_SHP
	VALUES (1048590,
	1284,
	1487,
	1556,
	1695);
INSERT INTO GD_GE
	VALUES (1048593,
	1048584,
	1048583,
	21);
INSERT INTO GD_SHP
	VALUES (1048593,
	1588,
	1487,
	1860,
	1695);
INSERT INTO GD_GE
	VALUES (1048594,
	1048584,
	1048584,
	21);
INSERT INTO GD_SHP
	VALUES (1048594,
	1891,
	1007,
	2195,
	1215);
INSERT INTO GD_GE
	VALUES (1048595,
	1048584,
	1048585,
	21);
INSERT INTO GD_SHP
	VALUES (1048595,
	1587,
	1247,
	1859,
	1455);
INSERT INTO GD_GE
	VALUES (1048630,
	1048584,
	1048586,
	21);
INSERT INTO GD_SHP
	VALUES (1048630,
	1888,
	1248,
	2192,
	1456);
INSERT INTO GD_GE
	VALUES (1048633,
	1048584,
	1048587,
	21);
INSERT INTO GD_SHP
	VALUES (1048633,
	1888,
	1488,
	2192,
	1696);
INSERT INTO S_SS
	VALUES (2097156,
	'Drivers',
	'',
	'',
	100,
	1346846,
	2097156);
INSERT INTO O_OBJ
	VALUES (2097153,
	'Driver',
	101,
	'D',
	'',
	2097156);
INSERT INTO O_NBATTR
	VALUES (2097153,
	2097153);
INSERT INTO O_BATTR
	VALUES (2097153,
	2097153);
INSERT INTO O_ATTR
	VALUES (2097153,
	2097153,
	0,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO SM_ISM
	VALUES (2621445,
	2097153);
INSERT INTO SM_SM
	VALUES (2621445,
	'',
	5);
INSERT INTO SM_MOORE
	VALUES (2621445);
INSERT INTO SM_EVTDI
	VALUES (2621441,
	2621445,
	'int',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (2621442,
	2621445,
	'time',
	'',
	524303);
INSERT INTO SM_LEVT
	VALUES (2621441,
	2621445,
	0);
INSERT INTO SM_SEVT
	VALUES (2621441,
	2621445,
	0);
INSERT INTO SM_EVT
	VALUES (2621441,
	2621445,
	0,
	0,
	'Start',
	0,
	'',
	'D0',
	'');
INSERT INTO SM_LEVT
	VALUES (2621442,
	2621445,
	0);
INSERT INTO SM_SEVT
	VALUES (2621442,
	2621445,
	0);
INSERT INTO SM_EVT
	VALUES (2621442,
	2621445,
	0,
	1,
	'Next',
	0,
	'',
	'D1',
	'');
INSERT INTO SM_LEVT
	VALUES (2621443,
	2621445,
	0);
INSERT INTO SM_SEVT
	VALUES (2621443,
	2621445,
	0);
INSERT INTO SM_EVT
	VALUES (2621443,
	2621445,
	0,
	2,
	'Wait',
	0,
	'',
	'D2',
	'');
INSERT INTO SM_LEVT
	VALUES (203664951,
	2621445,
	0);
INSERT INTO SM_SEVT
	VALUES (203664951,
	2621445,
	0);
INSERT INTO SM_EVT
	VALUES (203664951,
	2621445,
	0,
	3,
	'Unassigned Parameter Placeholder',
	0,
	'',
	'D3',
	'');
INSERT INTO SM_STATE
	VALUES (2621441,
	2621445,
	0,
	'Function',
	2,
	0);
INSERT INTO SM_EIGN
	VALUES (2621441,
	2621441,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621441,
	2621445,
	0);
INSERT INTO SM_SEME
	VALUES (2621441,
	2621442,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621441,
	2621443,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621443,
	2621445,
	0);
INSERT INTO SM_CH
	VALUES (2621441,
	203664951,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	203664951,
	2621445,
	0);
INSERT INTO SM_MOAH
	VALUES (2621441,
	2621445,
	2621441);
INSERT INTO SM_AH
	VALUES (2621441,
	2621445);
INSERT INTO SM_ACT
	VALUES (2621441,
	2621445,
	1,
	'LOG::LogInfo(message:"Starting function tests");
// All of these tests take two variables, one by reference and one by value.  The ref variable is 
// set to the value of the val variable, and the original value of the ref variable is returned.

bool_ref = true;
bool_val = false;
bool_returned = ::bool(ref:bool_ref, val:bool_val);
if (bool_returned == true) LOG::LogSuccess(message:"Function bool returned correct value");
else LOG::LogFailure(message:"Function bool returned incorrect value");
end if;
if (bool_ref == false)	LOG::LogSuccess(message:"Function bool correctly modified ref variable");
else LOG::LogFailure(message:"Function bool did not correctly modify ref variable");
end if;
if (bool_val == false) LOG::LogSuccess(message:"Function bool did not modify val variable");
else LOG::LogFailure(message:"Function bool modified value of val variable");
end if; 
bool_ref = true;
bool_val = false;
bool_returned = ::uBool(ref:bool_ref, val:bool_val);
if (bool_returned == true) LOG::LogSuccess(message:"Function uBool returned correct value");
else LOG::LogFailure(message:"Function uBool returned incorrect value");
end if;
if (bool_ref == false)	LOG::LogSuccess(message:"Function uBool correctly modified ref variable");
else LOG::LogFailure(message:"Function uBool did not correctly modify ref variable");
end if;
if (bool_val == false) LOG::LogSuccess(message:"Function uBool did not modify val variable");
else LOG::LogFailure(message:"Function uBool modified value of val variable");
end if; 

color_ref = color::red;
color_val = color::blue;
color_returned = ::color(ref:color_ref, val:color_val);
if (color_returned == color::red) LOG::LogSuccess(message:"Function color returned correct value");
else LOG::LogFailure(message:"Function color returned incorrect value");
end if;
if (color_ref == color::blue) LOG::LogSuccess(message:"Function color correctly modified ref variable");
else LOG::LogFailure(message:"Function color did not correctly modify ref variable");
end if;
if (color_val == color::blue) LOG::LogSuccess(message:"Function color did not modify val variable");
else LOG::LogFailure(message:"Function color modified value of val variable");
end if; 

date1 = TIM::create_date(day:1, month:1, year:2001, second:0, minute:0, hour:12);
date2 = TIM::create_date(day:14, month:8, year:1977, second:1, minute:1, hour:1); 
date_ref = date1;
date_val = date2;
date_returned = ::date(ref:date_ref, val:date_val);
if (date_returned == date1) LOG::LogSuccess(message:"Function date returned correct value");
else LOG::LogFailure(message:"Function date returned incorrect value");
end if;
if (date_ref == date2) LOG::LogSuccess(message:"Function date correctly modified ref variable");
else LOG::LogFailure(message:"Function date did not correctly modify ref variable");
end if;
if (date_val == date2) LOG::LogSuccess(message:"Function date did not modify val variable");
else LOG::LogFailure(message:"Function date modified value of val variable");
end if; 

select any inst from instances of INST;
create event instance e of INST1 to inst;
create event instance e2 of INST2(int:83) to inst;
inst_ref = e;
inst_val = e2;
if (e == e)
	inst_returned = ::inst(ref:inst_ref, val:inst_val);
	if (inst_returned == e) LOG::LogSuccess(message:"Function inst returned correct value");
	else LOG::LogFailure(message:"Function inst returned incorrect value");
	end if;
	if (inst_ref == e2) LOG::LogSuccess(message:"Function inst correctly modified ref variable");
	else LOG::LogFailure(message:"Function inst did not correctly modify ref variable");
	end if;
	if (inst_val == e2) LOG::LogSuccess(message:"Function inst did not modify val variable");
	else LOG::LogFailure(message:"Function inst modified value of val variable");
	end if; 
else
	LOG::LogFailure(message:"Cannot compare event to itself");
end if;

select any iref from instances of IREF;
timer1 = TIM::timer_start(microseconds:10000, event_inst:e);
timer2 = TIM::timer_start(microseconds:100000000, event_inst:e2);
t = TIM::timer_cancel(timer_inst_ref:iref.one);
iref.one = timer1;
timer_val = timer2;
timer_returned = ::inst_ref(ref:iref.one, val:timer_val);
if (timer_returned == timer1) LOG::LogSuccess(message:"Function inst_ref returned correct value - class attributes");
else LOG::LogFailure(message:"Function inst_ref returned incorrect value - class attributes");
end if;
if (iref.one == timer2) LOG::LogSuccess(message:"Function inst_ref correctly modified ref variable - class attributes");
else LOG::LogFailure(message:"Function inst_ref did not correctly modify ref variable - class attributes");
end if;
if (timer_val == timer2) LOG::LogSuccess(message:"Function inst_ref did not modify val variable - class attributes");
else LOG::LogFailure(message:"Function inst_ref modified value of val variable - class attributes");
end if; 
t = TIM::timer_cancel(timer_inst_ref:timer1);
t = TIM::timer_cancel(timer_inst_ref:timer2);

int_ref = 27;
int_val = 94;
int_returned = ::int(ref:int_ref, val:int_val);
if (int_returned == 27) LOG::LogSuccess(message:"Function int returned correct value");
else LOG::LogFailure(message:"Function int returned incorrect value");
end if;
if (int_ref == 94) LOG::LogSuccess(message:"Function int correctly modified ref variable");
else LOG::LogFailure(message:"Function int did not correctly modify ref variable");
end if;
if (int_val == 94) LOG::LogSuccess(message:"Function int did not modify val variable");
else LOG::LogFailure(message:"Function int modified value of val variable");
end if; 
int_ref = 27;
int_val = 94;
int_returned = ::uInt(ref:int_ref, val:int_val);
if (int_returned == 27) LOG::LogSuccess(message:"Function uInt returned correct value");
else LOG::LogFailure(message:"Function uInt returned incorrect value");
end if;
if (int_ref == 94) LOG::LogSuccess(message:"Function uInt correctly modified ref variable");
else LOG::LogFailure(message:"Function uInt did not correctly modify ref variable");
end if;
if (int_val == 94) LOG::LogSuccess(message:"Function uInt did not modify val variable");
else LOG::LogFailure(message:"Function uInt modified value of val variable");
end if; 

number_ref = number::two;
number_val = number::three;
number_returned = ::number(ref:number_ref, val:number_val);
if (number_returned == number::two) LOG::LogSuccess(message:"Function number returned correct value");
else LOG::LogFailure(message:"Function number returned incorrect value");
end if;
if (number_ref == number::three) LOG::LogSuccess(message:"Function number correctly modified ref variable");
else LOG::LogFailure(message:"Function number did not correctly modify ref variable");
end if;
if (number_val == number::three) LOG::LogSuccess(message:"Function number did not modify val variable");
else LOG::LogFailure(message:"Function number modified value of val variable");
end if; 

real_ref = 27.5;
real_val = 94.0;
real_returned = ::real(ref:real_ref, val:real_val);
if (real_returned == 27.5) LOG::LogSuccess(message:"Function real returned correct value");
else LOG::LogFailure(message:"Function real returned incorrect value");
end if;
if (real_ref == 94.0) LOG::LogSuccess(message:"Function real correctly modified ref variable");
else LOG::LogFailure(message:"Function real did not correctly modify ref variable");
end if;
if (real_val == 94.0) LOG::LogSuccess(message:"Function real did not modify val variable");
else LOG::LogFailure(message:"Function real modified value of val variable");
end if; 
real_ref = 27.5;
real_val = 94.0;
real_returned = ::uReal(ref:real_ref, val:real_val);
if (real_returned == 27.5) LOG::LogSuccess(message:"Function uReal returned correct value");
else LOG::LogFailure(message:"Function uReal returned incorrect value");
end if;
if (real_ref == 94.0) LOG::LogSuccess(message:"Function uReal correctly modified ref variable");
else LOG::LogFailure(message:"Function uReal did not correctly modify ref variable");
end if;
if (real_val == 94.0) LOG::LogSuccess(message:"Function uReal did not modify val variable");
else LOG::LogFailure(message:"Function uReal modified value of val variable");
end if;

string_ref = "27";
string_val = "a string";
string_returned = ::string(ref:string_ref, val:string_val);
if (string_returned == "27") LOG::LogSuccess(message:"Function string returned correct value");
else LOG::LogFailure(message:"Function string returned incorrect value");
end if;
if (string_ref == "a string") LOG::LogSuccess(message:"Function string correctly modified ref variable");
else LOG::LogFailure(message:"Function string did not correctly modify ref variable");
end if;
if (string_val == "a string") LOG::LogSuccess(message:"Function string did not modify val variable");
else LOG::LogFailure(message:"Function string modified value of val variable");
end if; 
string_ref = "27";
string_val = "a string";
string_returned = ::uString(ref:string_ref, val:string_val);
if (string_returned == "27") LOG::LogSuccess(message:"Function uString returned correct value");
else LOG::LogFailure(message:"Function uString returned incorrect value");
end if;
if (string_ref == "a string") LOG::LogSuccess(message:"Function uString correctly modified ref variable");
else LOG::LogFailure(message:"Function uString did not correctly modify ref variable");
end if;
if (string_val == "a string") LOG::LogSuccess(message:"Function uString did not modify val variable");
else LOG::LogFailure(message:"Function uString modified value of val variable");
end if;

time1 = rcvd_evt.time;
time2 = TIM::current_clock();
time_ref = time1;
time_val = time2;
if (time_ref == time_val)
	LOG::LogFailure(message:"Unable to create different timestamps"); 
else
	time_returned = ::time(ref:time_ref, val:time_val);
	if (time_returned == time1) LOG::LogSuccess(message:"Function time returned correct value");
	else LOG::LogFailure(message:"Function time returned incorrect value");
	end if;
	if (time_ref == time2) LOG::LogSuccess(message:"Function time correctly modified ref variable");
	else LOG::LogFailure(message:"Function time did not correctly modify ref variable");
	end if;
	if (time_val == time2) LOG::LogSuccess(message:"Function time did not modify val variable");
	else LOG::LogFailure(message:"Function time modified value of val variable");
	end if; 
end if;

select any uni from instances of UNI;
uni_ref = uni.one;
uni_val = uni.two;
uni_returned = ::unique(ref:uni_ref, val:uni_val);
if (uni_returned == uni.one) LOG::LogSuccess(message:"Function unique returned correct value");
else LOG::LogFailure(message:"Function unique returned incorrect value");
end if;
if (uni_ref == uni.two) LOG::LogSuccess(message:"Function unique correctly modified ref variable");
else LOG::LogFailure(message:"Function unique did not correctly modify ref variable");
end if;
if (uni_val == uni.two) LOG::LogSuccess(message:"Function unique did not modify val variable");
else LOG::LogFailure(message:"Function unique modified value of val variable");
end if; 
uni_ref = uni.two;
uni_val = uni.one;
uni_returned = ::uUnique(ref:uni_ref, val:uni_val);
if (uni_returned == uni.two) LOG::LogSuccess(message:"Function uUnique returned correct value");
else LOG::LogFailure(message:"Function uUnique returned incorrect value");
end if;
if (uni_ref == uni.one) LOG::LogSuccess(message:"Function uUnique correctly modified ref variable");
else LOG::LogFailure(message:"Function uUnique did not correctly modify ref variable");
end if;
if (uni_val == uni.one) LOG::LogSuccess(message:"Function uUnique did not modify val variable");
else LOG::LogFailure(message:"Function uUnique modified value of val variable");
end if; 

::void();
LOG::LogInfo(message:"Function tests complete");
generate D1 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2621442,
	2621445,
	0,
	'Test Complete',
	3,
	1);
INSERT INTO SM_EIGN
	VALUES (2621442,
	2621441,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621442,
	2621441,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621442,
	2621442,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621442,
	2621442,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621442,
	2621443,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621442,
	2621443,
	2621445,
	0);
INSERT INTO SM_CH
	VALUES (2621442,
	203664951,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621442,
	203664951,
	2621445,
	0);
INSERT INTO SM_MOAH
	VALUES (2621442,
	2621445,
	2621442);
INSERT INTO SM_AH
	VALUES (2621442,
	2621445);
INSERT INTO SM_ACT
	VALUES (2621442,
	2621445,
	1,
	'LOG::LogInfo(message:"Test complete");
ARCH::shutdown();',
	'');
INSERT INTO SM_STATE
	VALUES (2621447,
	2621445,
	0,
	'Waiting',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (2621447,
	2621441,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621447,
	2621442,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621447,
	2621442,
	2621445,
	0);
INSERT INTO SM_SEME
	VALUES (2621447,
	2621443,
	2621445,
	0);
INSERT INTO SM_CH
	VALUES (2621447,
	203664951,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621447,
	203664951,
	2621445,
	0);
INSERT INTO SM_MOAH
	VALUES (2621447,
	2621445,
	2621447);
INSERT INTO SM_AH
	VALUES (2621447,
	2621445);
INSERT INTO SM_ACT
	VALUES (2621447,
	2621445,
	1,
	'select any dt from instances of DT;
create event instance e of D0:''Start''(time:dt.time) to self;
timer1 =TIM::timer_start(microseconds:5000000, event_inst:e);
',
	'');
INSERT INTO SM_NSTXN
	VALUES (2621447,
	2621445,
	2621441,
	2621442,
	0);
INSERT INTO SM_TXN
	VALUES (2621447,
	2621445,
	2621442,
	0);
INSERT INTO SM_NSTXN
	VALUES (2621449,
	2621445,
	2621447,
	2621441,
	0);
INSERT INTO SM_TXN
	VALUES (2621449,
	2621445,
	2621441,
	0);
INSERT INTO SM_NSTXN
	VALUES (2621448,
	2621445,
	2621447,
	2621443,
	0);
INSERT INTO SM_TXN
	VALUES (2621448,
	2621445,
	2621447,
	0);
INSERT INTO GD_MD
	VALUES (2621441,
	8,
	2621445,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1690,
	4128,
	0.750000,
	0);
INSERT INTO GD_GE
	VALUES (2621442,
	2621441,
	2621441,
	41);
INSERT INTO GD_SHP
	VALUES (2621442,
	1792,
	1296,
	2304,
	1744);
INSERT INTO GD_GE
	VALUES (2621443,
	2621441,
	2621442,
	41);
INSERT INTO GD_SHP
	VALUES (2621443,
	1792,
	1840,
	2304,
	1920);
INSERT INTO GD_GE
	VALUES (2621464,
	2621441,
	2621447,
	41);
INSERT INTO GD_SHP
	VALUES (2621464,
	2416,
	1296,
	2928,
	1456);
INSERT INTO GD_GE
	VALUES (2621461,
	2621441,
	2621447,
	42);
INSERT INTO GD_CON
	VALUES (2621461,
	2621442,
	2621443,
	0);
INSERT INTO GD_CTXT
	VALUES (2621461,
	0,
	0,
	0,
	0,
	0,
	0,
	1942,
	1806,
	1994,
	1830,
	0,
	24,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621463,
	2621461,
	2000,
	1744,
	2000,
	1840,
	0);
INSERT INTO GD_GE
	VALUES (2621465,
	2621441,
	2621448,
	42);
INSERT INTO GD_CON
	VALUES (2621465,
	2621464,
	2621464,
	0);
INSERT INTO GD_CTXT
	VALUES (2621465,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621466,
	2621465,
	2848,
	1456,
	2832,
	1504,
	0);
INSERT INTO GD_LS
	VALUES (2621467,
	2621465,
	2832,
	1504,
	2480,
	1504,
	2621466);
INSERT INTO GD_LS
	VALUES (2621468,
	2621465,
	2480,
	1504,
	2464,
	1456,
	2621467);
INSERT INTO GD_GE
	VALUES (2621469,
	2621441,
	2621449,
	42);
INSERT INTO GD_CON
	VALUES (2621469,
	2621464,
	2621442,
	0);
INSERT INTO GD_CTXT
	VALUES (2621469,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621470,
	2621469,
	2416,
	1344,
	2304,
	1344,
	0);
INSERT INTO O_OBJ
	VALUES (2097154,
	'Init',
	102,
	'INIT',
	'',
	2097156);
INSERT INTO O_NBATTR
	VALUES (2097154,
	2097154);
INSERT INTO O_BATTR
	VALUES (2097154,
	2097154);
INSERT INTO O_ATTR
	VALUES (2097154,
	2097154,
	0,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO SM_ISM
	VALUES (3145734,
	2097154);
INSERT INTO SM_SM
	VALUES (3145734,
	'',
	6);
INSERT INTO SM_MOORE
	VALUES (3145734);
INSERT INTO SM_LEVT
	VALUES (3145729,
	3145734,
	0);
INSERT INTO SM_SEVT
	VALUES (3145729,
	3145734,
	0);
INSERT INTO SM_EVT
	VALUES (3145729,
	3145734,
	0,
	1,
	'Init',
	0,
	'',
	'INIT1',
	'');
INSERT INTO SM_STATE
	VALUES (3145729,
	3145734,
	0,
	'Initialize',
	1,
	0);
INSERT INTO SM_EIGN
	VALUES (3145729,
	3145729,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145729,
	3145734,
	0);
INSERT INTO SM_MOAH
	VALUES (3145729,
	3145734,
	3145729);
INSERT INTO SM_AH
	VALUES (3145729,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145729,
	3145734,
	1,
	'create object instance int of INT;
int.width = 5;
int.length = 6;
//int.area should be 30
//int.perimeter should be 22

create object instance real of REAL;
real.width = 1.3;
real.length = 2.4;
//real.area should be 3.12
//real.perimeter should be 7.4

create object instance string of STR;
string.first = "First";
string.last = "Last";
//string.FL should be "First Last"
//string.LF should be "Last, First"

create object instance bool of BOOL;
bool.p = true;
bool.q = false;
//bool.b_and should be false
//bool.b_or should be true

create object instance date_time of DT;
date_time.date = TIM::create_date(day:1, month:1, year:2000, second:0, minute:0, hour:12);
date_time.time = TIM::current_clock();
//date_time.next_date should be 2001.1.1 12:00:00
//date_time.next_time should be date_time.time

create object instance enum of ENUM;
enum.color = color::purple;
enum.size = number::three;
//enum.color2 should be color::yellow
//enum.size2 should be number::four

create object instance inst of INST;
create event instance e of INST1 to inst;
inst.one = e;
create event instance e2 of INST2(int:83) to inst;
inst.two = e2;
//inst.three should be inst.two
//inst.four is set by Verifier
//inst.five is set by Verifier
//inst.six should be inst.four

create object instance iref of IREF;
iref.one = TIM::timer_start(microseconds:100000000, event_inst:e);
//iref.two is set by Verifier
//iref.three should be iref.one
//iref.four should be iref.two

create object instance uni of UNI;
//uni.one is set by Verifier
//uni.two is set by Verifier

create object instance d of D;
generate D2:''Wait'' to d;',
	'');
INSERT INTO SM_CRTXN
	VALUES (3145729,
	3145734,
	3145729,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145729,
	3145734,
	3145729,
	0);
INSERT INTO GD_MD
	VALUES (3145729,
	8,
	3145734,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1611,
	4139,
	0.884422,
	0);
INSERT INTO GD_GE
	VALUES (3145730,
	3145729,
	3145729,
	41);
INSERT INTO GD_SHP
	VALUES (3145730,
	1712,
	1328,
	1936,
	1616);
INSERT INTO GD_GE
	VALUES (3145731,
	3145729,
	3145729,
	42);
INSERT INTO GD_CON
	VALUES (3145731,
	3145730,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (3145731,
	0,
	0,
	0,
	0,
	0,
	0,
	1848,
	1274,
	1965,
	1292,
	8,
	-22,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145732,
	3145731,
	1840,
	1328,
	1840,
	1280,
	0);
INSERT INTO GD_LS
	VALUES (3145733,
	3145731,
	1840,
	1280,
	1840,
	1264,
	3145732);
INSERT INTO GD_MD
	VALUES (2097174,
	5,
	2097156,
	11,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	4178,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (2097177,
	2097174,
	2097153,
	21);
INSERT INTO GD_SHP
	VALUES (2097177,
	1908,
	1295,
	2084,
	1439);
INSERT INTO GD_GE
	VALUES (2097178,
	2097174,
	2097154,
	21);
INSERT INTO GD_SHP
	VALUES (2097178,
	1700,
	1295,
	1876,
	1439);
