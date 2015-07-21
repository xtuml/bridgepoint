-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (490915,
	'enum4',
	'This test is a helper domain for enum3.  See enum3 for a complete description.',
	0,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	490915,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	490915,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	490915,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	490915,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	490915,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	490915,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	490915,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	490915,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	490915,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	490915,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	490915,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	490915,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	490915,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	490915,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	490915,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	490915,
	'inst_ref<Timer>',
	'');
INSERT INTO S_UDT
	VALUES (524305,
	524294,
	0);
INSERT INTO S_DT
	VALUES (524305,
	490915,
	'arbitrary_id',
	'Arbitrary ID with core data type of unique_id.');
INSERT INTO S_EDT
	VALUES (524306);
INSERT INTO S_DT
	VALUES (524306,
	490915,
	'color',
	'OWNER:enum3');
INSERT INTO S_ENUM
	VALUES (524289,
	'red',
	'',
	524306);
INSERT INTO S_ENUM
	VALUES (524290,
	'blue',
	'',
	524306);
INSERT INTO S_ENUM
	VALUES (524291,
	'green',
	'',
	524306);
INSERT INTO S_ENUM
	VALUES (524292,
	'orange',
	'',
	524306);
INSERT INTO S_ENUM
	VALUES (524293,
	'purple',
	'',
	524306);
INSERT INTO S_ENUM
	VALUES (524294,
	'black',
	'',
	524306);
INSERT INTO S_ENUM
	VALUES (524295,
	'violet',
	'',
	524306);
INSERT INTO S_ENUM
	VALUES (524296,
	'rouge',
	'',
	524306);
INSERT INTO S_SYNC
	VALUES (524289,
	490915,
	'bridge1',
	'Description line 1
Description line 2
Description line 3
Line 4
Line 5
',
	'if(param.color_one != color::purple)
	LOG::LogFailure(message:"bridge1 - param.color_one != color::purple");
else
	LOG::LogSuccess(message:"bridge1 - param.color_one == color::purple");
end if;

if(param.color_two != color::blue)
	LOG::LogFailure(message:"bridge1 - param.color_two != color::blue");
else
	LOG::LogSuccess(message:"bridge1 - param.color_two == color::blue");
end if;

if(param.count == 1)
	return param.color_one;
elif(param.count == 2)
	return color::purple;
else
	x = color::purple;
	return x;
end if;',
	524306,
	1);
INSERT INTO S_SPARM
	VALUES (524289,
	524289,
	'color_one',
	524306,
	0);
INSERT INTO S_SPARM
	VALUES (524290,
	524289,
	'color_two',
	524306,
	0);
INSERT INTO S_SPARM
	VALUES (524291,
	524289,
	'count',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524290,
	490915,
	'bridge2',
	'Description line 1
Description line 2
Description line 3
Line 4
Line 5
',
	'if(param.color_one != color::purple)
	LOG::LogFailure(message:"bridge1 - param.color_one != color::purple");
else
	LOG::LogSuccess(message:"bridge1 - param.color_one == color::purple");
end if;

if(param.color_two != color::blue)
	LOG::LogFailure(message:"bridge1 - param.color_two != color::blue");
else
	LOG::LogSuccess(message:"bridge1 - param.color_two == color::blue");
end if;

if(param.count == 1)
	param.color_two = param.color_one;
	return param.color_one;
elif(param.count == 2)
	param.color_two = color::purple;
	return color::purple;
else
	x = color::purple;
	param.color_two = x;
	return x;
end if;',
	524306,
	1);
INSERT INTO S_SPARM
	VALUES (524292,
	524290,
	'color_one',
	524306,
	0);
INSERT INTO S_SPARM
	VALUES (524293,
	524290,
	'color_two',
	524306,
	1);
INSERT INTO S_SPARM
	VALUES (524294,
	524290,
	'count',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524291,
	490915,
	'bridge3',
	'Description line 1
Description line 2
Description line 3
Line 4
Line 5
',
	'if(param.color_one != color::purple)
	LOG::LogFailure(message:"bridge1 - param.color_one != color::purple");
else
	LOG::LogSuccess(message:"bridge1 - param.color_one == color::purple");
end if;

if(param.color_two != color::blue)
	LOG::LogFailure(message:"bridge1 - param.color_two != color::blue");
else
	LOG::LogSuccess(message:"bridge1 - param.color_two == color::blue");
end if;

x = param.color_one;
param.color_one = param.color_two;
param.color_two = x;',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524295,
	524291,
	'color_one',
	524306,
	1);
INSERT INTO S_SPARM
	VALUES (524296,
	524291,
	'color_two',
	524306,
	1);
INSERT INTO S_EE
	VALUES (524289,
	'Enum3',
	'',
	'ENUM3',
	490915);
INSERT INTO S_EE
	VALUES (524290,
	'Logger',
	'',
	'LOG',
	490915);
INSERT INTO S_BRG
	VALUES (524289,
	524290,
	'LogFailure',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524297,
	524289,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524290,
	524290,
	'LogSuccess',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524298,
	524290,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524291,
	524290,
	'LogInfo',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524299,
	524291,
	'message',
	524293,
	0);
INSERT INTO S_EE
	VALUES (524291,
	'Architecture',
	'',
	'ARCH',
	490915);
INSERT INTO S_BRG
	VALUES (524292,
	524291,
	'shutdown',
	'',
	0,
	524289,
	'control stop;',
	1);
INSERT INTO GD_MD
	VALUES (524289,
	1,
	490915,
	1,
	1,
	0,
	1,
	1,
	0,
	12,
	1552,
	4221,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (524293,
	524289,
	524289,
	12);
INSERT INTO GD_SHP
	VALUES (524293,
	1744,
	1472,
	1936,
	1584);
INSERT INTO GD_GE
	VALUES (524294,
	524289,
	524290,
	12);
INSERT INTO GD_SHP
	VALUES (524294,
	1968,
	1472,
	2160,
	1584);
INSERT INTO GD_GE
	VALUES (524295,
	524289,
	524291,
	12);
INSERT INTO GD_SHP
	VALUES (524295,
	1968,
	1344,
	2160,
	1456);
INSERT INTO GD_GE
	VALUES (524305,
	524289,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524305,
	1744,
	1344,
	1936,
	1456);
INSERT INTO GD_MD
	VALUES (524290,
	2,
	490915,
	1,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	4200,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (524306,
	524290,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524306,
	1904,
	1376,
	2080,
	1472);
INSERT INTO GD_MD
	VALUES (524291,
	3,
	490915,
	1,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	4200,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (524307,
	524291,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524307,
	1904,
	1376,
	2080,
	1472);
INSERT INTO GD_MD
	VALUES (524292,
	4,
	490915,
	1,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	4200,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (524308,
	524292,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524308,
	1904,
	1376,
	2080,
	1472);
INSERT INTO S_SS
	VALUES (1048578,
	'Enumeration Four',
	'',
	'',
	1,
	490915,
	1048578);
INSERT INTO O_OBJ
	VALUES (1048577,
	'Object A',
	1,
	'OA',
	'',
	1048578);
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
	'oa_id',
	'',
	'',
	'oa_id',
	0,
	524294);
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
	'color_one',
	'',
	'',
	'color_one',
	0,
	524306);
INSERT INTO O_NBATTR
	VALUES (1048579,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048579,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048579,
	1048577,
	1048578,
	'color_two',
	'',
	'',
	'color_two',
	0,
	524306);
INSERT INTO O_NBATTR
	VALUES (1048580,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048580,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048580,
	1048577,
	1048579,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048577);
INSERT INTO O_OIDA
	VALUES (1048577,
	1048577,
	0);
INSERT INTO SM_ISM
	VALUES (1572867,
	1048577);
INSERT INTO SM_SM
	VALUES (1572867,
	'',
	3);
INSERT INTO SM_MOORE
	VALUES (1572867);
INSERT INTO SM_EVTDI
	VALUES (1572865,
	1572867,
	'color_one',
	'',
	524306);
INSERT INTO SM_EVTDI
	VALUES (1572866,
	1572867,
	'color_two',
	'',
	524306);
INSERT INTO SM_SUPDT
	VALUES (1572865,
	1572867,
	0);
INSERT INTO SM_SUPDT
	VALUES (1572866,
	1572867,
	0);
INSERT INTO SM_SDI
	VALUES (1572866,
	1572866,
	1572867);
INSERT INTO SM_SDI
	VALUES (1572865,
	1572866,
	1572867);
INSERT INTO SM_STATE
	VALUES (1572865,
	1572867,
	1572865,
	'one',
	1,
	0);
INSERT INTO SM_LEVT
	VALUES (1572865,
	1572867,
	1572865);
INSERT INTO SM_SEVT
	VALUES (1572865,
	1572867,
	1572865);
INSERT INTO SM_EVT
	VALUES (1572865,
	1572867,
	1572865,
	1,
	'one',
	0,
	'',
	'OA1',
	'');
INSERT INTO SM_SEME
	VALUES (1572865,
	1572865,
	1572867,
	1572865);
INSERT INTO SM_LEVT
	VALUES (1572866,
	1572867,
	1572866);
INSERT INTO SM_SEVT
	VALUES (1572866,
	1572867,
	1572866);
INSERT INTO SM_EVT
	VALUES (1572866,
	1572867,
	1572866,
	2,
	'two',
	0,
	'',
	'OA2',
	'');
INSERT INTO SM_SEME
	VALUES (1572865,
	1572866,
	1572867,
	1572866);
INSERT INTO SM_STATE
	VALUES (1572866,
	1572867,
	1572866,
	'two',
	2,
	0);
INSERT INTO SM_CH
	VALUES (1572866,
	1572865,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572866,
	1572865,
	1572867,
	1572865);
INSERT INTO SM_CH
	VALUES (1572866,
	1572866,
	1572867,
	1572866,
	'');
INSERT INTO SM_SEME
	VALUES (1572866,
	1572866,
	1572867,
	1572866);
INSERT INTO SM_NSTXN
	VALUES (1572865,
	1572867,
	1572865,
	1572865,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572865,
	1572867,
	1572865,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572866,
	1572867,
	1572865,
	1572866,
	1572866);
INSERT INTO SM_TXN
	VALUES (1572866,
	1572867,
	1572866,
	1572866);
INSERT INTO SM_MOAH
	VALUES (1572865,
	1572867,
	1572865);
INSERT INTO SM_AH
	VALUES (1572865,
	1572867);
INSERT INTO SM_ACT
	VALUES (1572865,
	1572867,
	1,
	'if (self.color_one != color::black)
  LOG::LogFailure(message:"Object A.one - color_one != color::black");
else
  LOG::LogSuccess(message:"Object A.one - color_one != color::black");
end if;
if (self.color_two != color::blue)
  LOG::LogFailure(message:"Object A.one - color_two != color::blue");
else
  LOG::LogSuccess(message:"Object A.one - color_two != color::blue");
end if;
generate OA2:''two''(color_one:color::red,color_two:color::violet) to self;',
	'');
INSERT INTO SM_MOAH
	VALUES (1572866,
	1572867,
	1572866);
INSERT INTO SM_AH
	VALUES (1572866,
	1572867);
INSERT INTO SM_ACT
	VALUES (1572866,
	1572867,
	1,
	'if (rcvd_evt.color_one != color::red)
  LOG::LogFailure(message:"Object A.two - color_one != color::red");
else
  LOG::LogSuccess(message:"Object A.two - color_one != color::red");
end if;
if (rcvd_evt.color_two != color::violet)
  LOG::LogFailure(message:"Object A.two - color_two != color::violet");
else
  LOG::LogSuccess(message:"Object A.two - color_two != color::violet");
end if;
ARCH::shutdown();',
	'');
INSERT INTO GD_MD
	VALUES (1572865,
	8,
	1572867,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	4200,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (1572866,
	1572865,
	1572865,
	41);
INSERT INTO GD_SHP
	VALUES (1572866,
	1792,
	1248,
	2096,
	1392);
INSERT INTO GD_GE
	VALUES (1572867,
	1572865,
	1572866,
	41);
INSERT INTO GD_SHP
	VALUES (1572867,
	1792,
	1456,
	2096,
	1616);
INSERT INTO GD_GE
	VALUES (1572868,
	1572865,
	1572866,
	42);
INSERT INTO GD_CON
	VALUES (1572868,
	1572866,
	1572867,
	0);
INSERT INTO GD_CTXT
	VALUES (1572868,
	0,
	0,
	0,
	0,
	0,
	0,
	1818,
	1409,
	1946,
	1441,
	-15,
	-6,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572869,
	1572868,
	1952,
	1392,
	1952,
	1456,
	0);
INSERT INTO GD_GE
	VALUES (1572870,
	1572865,
	1572865,
	42);
INSERT INTO GD_CON
	VALUES (1572870,
	1572866,
	1572866,
	0);
INSERT INTO GD_CTXT
	VALUES (1572870,
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
	VALUES (1572871,
	1572870,
	1824,
	1248,
	1824,
	1216,
	0);
INSERT INTO GD_LS
	VALUES (1572872,
	1572870,
	1824,
	1216,
	2032,
	1216,
	1572871);
INSERT INTO GD_LS
	VALUES (1572873,
	1572870,
	2032,
	1216,
	2032,
	1248,
	1572872);
INSERT INTO O_OBJ
	VALUES (1048578,
	'Initialization Object',
	2,
	'INIT',
	'',
	1048578);
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
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
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
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_ID
	VALUES (0,
	1048578);
INSERT INTO O_OIDA
	VALUES (1048582,
	1048578,
	0);
INSERT INTO SM_ISM
	VALUES (2097156,
	1048578);
INSERT INTO SM_SM
	VALUES (2097156,
	'',
	4);
INSERT INTO SM_MOORE
	VALUES (2097156);
INSERT INTO SM_SUPDT
	VALUES (2097153,
	2097156,
	0);
INSERT INTO SM_STATE
	VALUES (2097153,
	2097156,
	2097153,
	'Init',
	1,
	0);
INSERT INTO SM_LEVT
	VALUES (2097153,
	2097156,
	2097153);
INSERT INTO SM_SEVT
	VALUES (2097153,
	2097156,
	2097153);
INSERT INTO SM_EVT
	VALUES (2097153,
	2097156,
	2097153,
	1,
	'Go',
	0,
	'',
	'INIT1',
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097153,
	2097156,
	2097153,
	2097153,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097153,
	2097156,
	2097153,
	2097153);
INSERT INTO SM_MOAH
	VALUES (2097153,
	2097156,
	2097153);
INSERT INTO SM_AH
	VALUES (2097153,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097153,
	2097156,
	1,
	'create object instance oa of OA;
oa.color_one=color::black;
oa.color_two=color::blue;
generate OA1 to oa;',
	'');
INSERT INTO GD_MD
	VALUES (2097153,
	8,
	2097156,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	4200,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (2097154,
	2097153,
	2097153,
	41);
INSERT INTO GD_SHP
	VALUES (2097154,
	1792,
	1344,
	2064,
	1504);
INSERT INTO GD_GE
	VALUES (2097155,
	2097153,
	2097153,
	42);
INSERT INTO GD_CON
	VALUES (2097155,
	2097154,
	2097154,
	0);
INSERT INTO GD_CTXT
	VALUES (2097155,
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
	VALUES (2097156,
	2097155,
	1824,
	1344,
	1824,
	1280,
	0);
INSERT INTO GD_LS
	VALUES (2097157,
	2097155,
	1824,
	1280,
	2016,
	1280,
	2097156);
INSERT INTO GD_LS
	VALUES (2097158,
	2097155,
	2016,
	1280,
	2016,
	1344,
	2097157);
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
	1600,
	4199,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (1048587,
	1048584,
	1048577,
	21);
INSERT INTO GD_SHP
	VALUES (1048587,
	1696,
	1264,
	1936,
	1456);
INSERT INTO GD_GE
	VALUES (1048588,
	1048584,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048588,
	2016,
	1264,
	2288,
	1456);
INSERT INTO GD_MD
	VALUES (1048585,
	6,
	1048578,
	11,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	4200,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (1048589,
	1048585,
	1048577,
	21);
INSERT INTO GD_SHP
	VALUES (1048589,
	1696,
	1264,
	1888,
	1328);
INSERT INTO GD_GE
	VALUES (1048590,
	1048585,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048590,
	2016,
	1264,
	2208,
	1328);
INSERT INTO GD_MD
	VALUES (1048586,
	7,
	1048578,
	11,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	4200,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (1048591,
	1048586,
	1048577,
	21);
INSERT INTO GD_SHP
	VALUES (1048591,
	1696,
	1264,
	1888,
	1328);
INSERT INTO GD_GE
	VALUES (1048592,
	1048586,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048592,
	2016,
	1264,
	2208,
	1328);
