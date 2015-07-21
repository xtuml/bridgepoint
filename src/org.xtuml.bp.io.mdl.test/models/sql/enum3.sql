-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (3640000,
	'enum3',
	'This test deals with "New Style" enumerations in the following places:
    - local
    - attribute
    - received event
    - Class Operations      ( realized and translated )
    - Instance Operations ( realized and translated )
    - Bridges                     ( wired to functions in enum4 )
    - Functions
    - MDAs

Passes the enumeration by value and by reference in Functions, Bridges, and Operations and uses the enumeration as a parameter as well as a return value.',
	0,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	3640000,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	3640000,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	3640000,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	3640000,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	3640000,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	3640000,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	3640000,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	3640000,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	3640000,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	3640000,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	3640000,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	3640000,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	3640000,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	3640000,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	3640000,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	3640000,
	'inst_ref<Timer>',
	'');
INSERT INTO S_EDT
	VALUES (524305);
INSERT INTO S_DT
	VALUES (524305,
	3640000,
	'color',
	'');
INSERT INTO S_ENUM
	VALUES (524289,
	'red',
	'',
	524305);
INSERT INTO S_ENUM
	VALUES (524290,
	'blue',
	'',
	524305);
INSERT INTO S_ENUM
	VALUES (524291,
	'green',
	'This value should be overridden by the coloring file value of 200

VALUE: 999',
	524305);
INSERT INTO S_ENUM
	VALUES (524292,
	'orange',
	'',
	524305);
INSERT INTO S_ENUM
	VALUES (524293,
	'purple',
	'',
	524305);
INSERT INTO S_ENUM
	VALUES (524294,
	'black',
	'',
	524305);
INSERT INTO S_ENUM
	VALUES (524295,
	'violet',
	'',
	524305);
INSERT INTO S_ENUM
	VALUES (524296,
	'rouge',
	'',
	524305);
INSERT INTO S_EDT
	VALUES (524306);
INSERT INTO S_DT
	VALUES (524306,
	3640000,
	'temperature',
	'');
INSERT INTO S_ENUM
	VALUES (524297,
	'high',
	'Description line 1
Description line 2
Description line 3
Line 4
Line 5

VALUE: 1000',
	524306);
INSERT INTO S_ENUM
	VALUES (524298,
	'med',
	'Description line 1
Description line 2
Description line 3
Line 4
Line 5

Value: 600',
	524306);
INSERT INTO S_ENUM
	VALUES (524299,
	'low',
	'Description line 1
Description line 2
Description line 3
Line 4
Line 5

value: 0x1',
	524306);
INSERT INTO S_SYNC
	VALUES (524289,
	3640000,
	'function1',
	'',
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
	524305,
	1);
INSERT INTO S_SPARM
	VALUES (524294,
	524289,
	'color_one',
	524305,
	0);
INSERT INTO S_SPARM
	VALUES (524295,
	524289,
	'color_two',
	524305,
	0);
INSERT INTO S_SPARM
	VALUES (524296,
	524289,
	'count',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524292,
	3640000,
	'function2',
	'',
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
	524305,
	1);
INSERT INTO S_SPARM
	VALUES (524312,
	524292,
	'color_one',
	524305,
	0);
INSERT INTO S_SPARM
	VALUES (524313,
	524292,
	'color_two',
	524305,
	1);
INSERT INTO S_SPARM
	VALUES (524314,
	524292,
	'count',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524293,
	3640000,
	'function3',
	'',
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
	VALUES (524315,
	524293,
	'color_one',
	524305,
	1);
INSERT INTO S_SPARM
	VALUES (524316,
	524293,
	'color_two',
	524305,
	1);
INSERT INTO S_EE
	VALUES (524289,
	'Logger',
	'',
	'LOG',
	3640000);
INSERT INTO S_BRG
	VALUES (524289,
	524289,
	'LogSuccess',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524289,
	524289,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524290,
	524289,
	'LogFailure',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524290,
	524290,
	'message',
	524293,
	0);
INSERT INTO S_EE
	VALUES (524290,
	'Architecture',
	'',
	'ARCH',
	3640000);
INSERT INTO S_BRG
	VALUES (524291,
	524290,
	'shutdown',
	'',
	0,
	524289,
	'control stop;',
	1);
INSERT INTO S_EE
	VALUES (524291,
	'Enum4',
	'',
	'E4',
	3640000);
INSERT INTO S_BRG
	VALUES (524292,
	524291,
	'bridge1',
	'',
	0,
	524305,
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
	1);
INSERT INTO S_BPARM
	VALUES (524291,
	524292,
	'color_one',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524292,
	524292,
	'color_two',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524293,
	524292,
	'count',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524295,
	524291,
	'bridge2',
	'',
	0,
	524305,
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
	1);
INSERT INTO S_BPARM
	VALUES (524307,
	524295,
	'color_one',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524308,
	524295,
	'color_two',
	524305,
	1);
INSERT INTO S_BPARM
	VALUES (524309,
	524295,
	'count',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524296,
	524291,
	'bridge3',
	'',
	0,
	524289,
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
	1);
INSERT INTO S_BPARM
	VALUES (524310,
	524296,
	'color_one',
	524305,
	1);
INSERT INTO S_BPARM
	VALUES (524311,
	524296,
	'color_two',
	524305,
	1);
INSERT INTO GD_MD
	VALUES (524289,
	1,
	3640000,
	1,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	4142,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (524296,
	524289,
	10485780,
	11);
INSERT INTO GD_SHP
	VALUES (524296,
	1744,
	1296,
	2016,
	1456);
INSERT INTO GD_GE
	VALUES (524306,
	524289,
	524289,
	12);
INSERT INTO GD_SHP
	VALUES (524306,
	1744,
	1504,
	2016,
	1664);
INSERT INTO GD_GE
	VALUES (524307,
	524289,
	524290,
	12);
INSERT INTO GD_SHP
	VALUES (524307,
	2048,
	1504,
	2256,
	1664);
INSERT INTO GD_GE
	VALUES (524314,
	524289,
	524291,
	12);
INSERT INTO GD_SHP
	VALUES (524314,
	2048,
	1296,
	2256,
	1456);
INSERT INTO GD_MD
	VALUES (524290,
	2,
	3640000,
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
	VALUES (524297,
	524290,
	10485780,
	11);
INSERT INTO GD_SHP
	VALUES (524297,
	1744,
	1296,
	2016,
	1456);
INSERT INTO GD_MD
	VALUES (524291,
	3,
	3640000,
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
	VALUES (524298,
	524291,
	10485780,
	11);
INSERT INTO GD_SHP
	VALUES (524298,
	1744,
	1296,
	2016,
	1456);
INSERT INTO GD_MD
	VALUES (524292,
	4,
	3640000,
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
	VALUES (524299,
	524292,
	10485780,
	11);
INSERT INTO GD_SHP
	VALUES (524299,
	1744,
	1296,
	2016,
	1456);
INSERT INTO S_SS
	VALUES (10485780,
	'Enum3',
	'',
	'E3',
	1,
	3640000,
	10485780);
INSERT INTO O_OBJ
	VALUES (10485761,
	'Object A',
	1,
	'OA',
	'',
	10485780);
INSERT INTO O_DBATTR
	VALUES (10485782,
	10485761,
	'if(self.color_two == color::green)
	self.color_changer = color::black;
else
	self.color_changer = color::red;
end if;',
	1);
INSERT INTO O_BATTR
	VALUES (10485782,
	10485761);
INSERT INTO O_ATTR
	VALUES (10485782,
	10485761,
	0,
	'color_changer',
	'',
	'',
	'color_changer',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (10485761,
	10485761);
INSERT INTO O_BATTR
	VALUES (10485761,
	10485761);
INSERT INTO O_ATTR
	VALUES (10485761,
	10485761,
	10485782,
	'oa_id',
	'',
	'',
	'oa_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (10485763,
	10485761);
INSERT INTO O_BATTR
	VALUES (10485763,
	10485761);
INSERT INTO O_ATTR
	VALUES (10485763,
	10485761,
	10485761,
	'color_one',
	'',
	'',
	'color_one',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (10485764,
	10485761);
INSERT INTO O_BATTR
	VALUES (10485764,
	10485761);
INSERT INTO O_ATTR
	VALUES (10485764,
	10485761,
	10485763,
	'color_two',
	'',
	'',
	'color_two',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (10485762,
	10485761);
INSERT INTO O_BATTR
	VALUES (10485762,
	10485761);
INSERT INTO O_ATTR
	VALUES (10485762,
	10485761,
	10485764,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	10485761);
INSERT INTO O_OIDA
	VALUES (10485761,
	10485761,
	0);
INSERT INTO SM_ISM
	VALUES (11010069,
	10485761);
INSERT INTO SM_SM
	VALUES (11010069,
	'',
	21);
INSERT INTO SM_MOORE
	VALUES (11010069);
INSERT INTO SM_EVTDI
	VALUES (11010049,
	11010069,
	'color_one',
	'',
	524305);
INSERT INTO SM_EVTDI
	VALUES (11010050,
	11010069,
	'color_two',
	'',
	524305);
INSERT INTO SM_SUPDT
	VALUES (11010049,
	11010069,
	0);
INSERT INTO SM_SUPDT
	VALUES (11010051,
	11010069,
	0);
INSERT INTO SM_SDI
	VALUES (11010049,
	11010051,
	11010069);
INSERT INTO SM_SDI
	VALUES (11010050,
	11010051,
	11010069);
INSERT INTO SM_STATE
	VALUES (11010049,
	11010069,
	11010049,
	'one',
	1,
	0);
INSERT INTO SM_LEVT
	VALUES (11010049,
	11010069,
	11010049);
INSERT INTO SM_SEVT
	VALUES (11010049,
	11010069,
	11010049);
INSERT INTO SM_EVT
	VALUES (11010049,
	11010069,
	11010049,
	1,
	'one',
	0,
	'',
	'OA1',
	'');
INSERT INTO SM_SEME
	VALUES (11010049,
	11010049,
	11010069,
	11010049);
INSERT INTO SM_LEVT
	VALUES (11010050,
	11010069,
	11010051);
INSERT INTO SM_SEVT
	VALUES (11010050,
	11010069,
	11010051);
INSERT INTO SM_EVT
	VALUES (11010050,
	11010069,
	11010051,
	2,
	'two',
	0,
	'',
	'OA2',
	'');
INSERT INTO SM_SEME
	VALUES (11010049,
	11010050,
	11010069,
	11010051);
INSERT INTO SM_LEVT
	VALUES (11010051,
	11010069,
	11010051);
INSERT INTO SM_SEVT
	VALUES (11010051,
	11010069,
	11010051);
INSERT INTO SM_EVT
	VALUES (11010051,
	11010069,
	11010051,
	3,
	'three',
	0,
	'',
	'OA3',
	'');
INSERT INTO SM_EIGN
	VALUES (11010049,
	11010051,
	11010069,
	11010051,
	'');
INSERT INTO SM_SEME
	VALUES (11010049,
	11010051,
	11010069,
	11010051);
INSERT INTO SM_STATE
	VALUES (11010050,
	11010069,
	11010051,
	'two',
	2,
	0);
INSERT INTO SM_CH
	VALUES (11010050,
	11010049,
	11010069,
	11010049,
	'');
INSERT INTO SM_SEME
	VALUES (11010050,
	11010049,
	11010069,
	11010049);
INSERT INTO SM_CH
	VALUES (11010050,
	11010050,
	11010069,
	11010051,
	'');
INSERT INTO SM_SEME
	VALUES (11010050,
	11010050,
	11010069,
	11010051);
INSERT INTO SM_SEME
	VALUES (11010050,
	11010051,
	11010069,
	11010051);
INSERT INTO SM_STATE
	VALUES (11010051,
	11010069,
	11010051,
	'three',
	3,
	0);
INSERT INTO SM_EIGN
	VALUES (11010051,
	11010049,
	11010069,
	11010049,
	'');
INSERT INTO SM_SEME
	VALUES (11010051,
	11010049,
	11010069,
	11010049);
INSERT INTO SM_EIGN
	VALUES (11010051,
	11010050,
	11010069,
	11010051,
	'');
INSERT INTO SM_SEME
	VALUES (11010051,
	11010050,
	11010069,
	11010051);
INSERT INTO SM_EIGN
	VALUES (11010051,
	11010051,
	11010069,
	11010051,
	'');
INSERT INTO SM_SEME
	VALUES (11010051,
	11010051,
	11010069,
	11010051);
INSERT INTO SM_NSTXN
	VALUES (11010050,
	11010069,
	11010049,
	11010049,
	11010049);
INSERT INTO SM_TXN
	VALUES (11010050,
	11010069,
	11010049,
	11010049);
INSERT INTO SM_NSTXN
	VALUES (11010049,
	11010069,
	11010049,
	11010050,
	11010051);
INSERT INTO SM_TXN
	VALUES (11010049,
	11010069,
	11010050,
	11010051);
INSERT INTO SM_NSTXN
	VALUES (11010051,
	11010069,
	11010050,
	11010051,
	11010051);
INSERT INTO SM_TXN
	VALUES (11010051,
	11010069,
	11010051,
	11010051);
INSERT INTO SM_MOAH
	VALUES (11010049,
	11010069,
	11010049);
INSERT INTO SM_AH
	VALUES (11010049,
	11010069);
INSERT INTO SM_ACT
	VALUES (11010049,
	11010069,
	1,
	'if (self.color_one != color::black)
  LOG::LogFailure(message:"Object A.one - color_one != color::black");
else
  LOG::LogSuccess(message:"Object A.one - color_one == color::black");
end if;
if (self.color_two != color::blue)
  LOG::LogFailure(message:"Object A.one - color_two != color::blue");
else
  LOG::LogSuccess(message:"Object A.one - color_two == color::blue");
end if;

x = color::green;
self.color_one = color::rouge;
self.color_two = x; 
generate OA2:''two''(color_one:self.color_one, color_two:self.color_two) to self;',
	'Receives event from initialization object
Assigns local parameter to enumerated type
Reassigns Object attribute color_one to different enumerator
Reassigns Object attribute color_two to local variable of enumerated type
Generates event, passing object attributes as the event data');
INSERT INTO SM_MOAH
	VALUES (11010050,
	11010069,
	11010050);
INSERT INTO SM_AH
	VALUES (11010050,
	11010069);
INSERT INTO SM_ACT
	VALUES (11010050,
	11010069,
	1,
	'if (rcvd_evt.color_one != color::rouge)
  LOG::LogFailure(message:"Event OA2 - color_one != color::rouge");
else
  LOG::LogSuccess(message:"Event OA2 - color_one == color::rouge");
end if;

if (rcvd_evt.color_two != color::green)
  LOG::LogFailure(message:"Event OA2 - color_two != color::green");
else
  LOG::LogSuccess(message:"Event OA2 - color_two == color::green");
end if;

if (self.color_one != color::rouge)
  LOG::LogFailure(message:"Object A after Event OA2 - self.color_one != color::rouge");
else
  LOG::LogSuccess(message:"Object A after Event OA2 - self.color_one == color::rouge");
end if;

if (self.color_two != color::green)
  LOG::LogFailure(message:"Object A after Event OA2 - self.color_two != color::green");
else
  LOG::LogSuccess(message:"Object A after Event OA2 - self.color_two == color::green");
end if;

generate OA3:''three''(color_one:color::red, color_two:color::violet) to self;',
	'Receives object attribute values as event data
Checks object attributes to ensure that changes made in State one remain
Generates event passing enumerators');
INSERT INTO SM_MOAH
	VALUES (11010051,
	11010069,
	11010051);
INSERT INTO SM_AH
	VALUES (11010051,
	11010069);
INSERT INTO SM_ACT
	VALUES (11010051,
	11010069,
	1,
	'a = rcvd_evt.color_one;
b = rcvd_evt.color_two;

if (a != color::red)
  LOG::LogFailure(message:"Object A.two - color_one != color::red");
else
  LOG::LogSuccess(message:"Object A.two - color_one == color::red");
end if;

if (b != color::violet)
  LOG::LogFailure(message:"Object A.two - color_two != color::violet");
else
  LOG::LogSuccess(message:"Object A.two - color_two == color::violet");
end if;

select any ob from instances of OB;
generate OB1:''Go''(color_one:a, color_two:b) to ob;
',
	'Assigns enumerated event data to local variables
Generates event across objects passing enumerated local variables as event data');
INSERT INTO GD_MD
	VALUES (11010049,
	8,
	11010069,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1224,
	4159,
	0.610164,
	0);
INSERT INTO GD_GE
	VALUES (11010050,
	11010049,
	11010049,
	41);
INSERT INTO GD_SHP
	VALUES (11010050,
	1248,
	944,
	1728,
	1200);
INSERT INTO GD_GE
	VALUES (11010051,
	11010049,
	11010050,
	41);
INSERT INTO GD_SHP
	VALUES (11010051,
	1248,
	1280,
	1728,
	1744);
INSERT INTO GD_GE
	VALUES (11010220,
	11010049,
	11010051,
	41);
INSERT INTO GD_SHP
	VALUES (11010220,
	1936,
	1440,
	2416,
	1744);
INSERT INTO GD_GE
	VALUES (11010052,
	11010049,
	11010049,
	42);
INSERT INTO GD_CON
	VALUES (11010052,
	11010050,
	11010051,
	0);
INSERT INTO GD_CTXT
	VALUES (11010052,
	0,
	0,
	0,
	0,
	0,
	0,
	1523,
	1226,
	1709,
	1244,
	229,
	-5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (11010280,
	11010052,
	1488,
	1200,
	1488,
	1280,
	0);
INSERT INTO GD_GE
	VALUES (11010054,
	11010049,
	11010050,
	42);
INSERT INTO GD_CON
	VALUES (11010054,
	11010050,
	11010050,
	0);
INSERT INTO GD_CTXT
	VALUES (11010054,
	0,
	0,
	0,
	0,
	0,
	0,
	1436,
	858,
	1489,
	876,
	-466,
	-92,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (11010275,
	11010054,
	1360,
	944,
	1360,
	896,
	0);
INSERT INTO GD_LS
	VALUES (11010276,
	11010054,
	1360,
	896,
	1568,
	896,
	11010275);
INSERT INTO GD_LS
	VALUES (11010277,
	11010054,
	1568,
	896,
	1568,
	944,
	11010276);
INSERT INTO GD_GE
	VALUES (11010221,
	11010049,
	11010051,
	42);
INSERT INTO GD_CON
	VALUES (11010221,
	11010051,
	11010220,
	0);
INSERT INTO GD_CTXT
	VALUES (11010221,
	0,
	0,
	0,
	0,
	0,
	0,
	1734,
	1577,
	1927,
	1595,
	-2,
	-13,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (11010285,
	11010221,
	1728,
	1616,
	1936,
	1616,
	0);
INSERT INTO O_OBJ
	VALUES (10485762,
	'Initialization Object',
	2,
	'INIT',
	'',
	10485780);
INSERT INTO O_NBATTR
	VALUES (10485765,
	10485762);
INSERT INTO O_BATTR
	VALUES (10485765,
	10485762);
INSERT INTO O_ATTR
	VALUES (10485765,
	10485762,
	0,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO SM_ISM
	VALUES (11534358,
	10485762);
INSERT INTO SM_SM
	VALUES (11534358,
	'',
	22);
INSERT INTO SM_MOORE
	VALUES (11534358);
INSERT INTO SM_SUPDT
	VALUES (11534337,
	11534358,
	0);
INSERT INTO SM_STATE
	VALUES (11534337,
	11534358,
	11534337,
	'Init',
	1,
	0);
INSERT INTO SM_LEVT
	VALUES (11534337,
	11534358,
	11534337);
INSERT INTO SM_SEVT
	VALUES (11534337,
	11534358,
	11534337);
INSERT INTO SM_EVT
	VALUES (11534337,
	11534358,
	11534337,
	1,
	'Go',
	0,
	'',
	'INIT1',
	'');
INSERT INTO SM_SEME
	VALUES (11534337,
	11534337,
	11534358,
	11534337);
INSERT INTO SM_NSTXN
	VALUES (11534337,
	11534358,
	11534337,
	11534337,
	11534337);
INSERT INTO SM_TXN
	VALUES (11534337,
	11534358,
	11534337,
	11534337);
INSERT INTO SM_MOAH
	VALUES (11534337,
	11534358,
	11534337);
INSERT INTO SM_AH
	VALUES (11534337,
	11534358);
INSERT INTO SM_ACT
	VALUES (11534337,
	11534358,
	1,
	'create object instance oa of OA;
create object instance ob of OB;
create object instance oc of OC;
create object instance od of OD;
oa.color_one=color::black;
oa.color_two=color::blue;
generate OA1 to oa;',
	'');
INSERT INTO GD_MD
	VALUES (11534337,
	8,
	11534358,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	4142,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (11534338,
	11534337,
	11534337,
	41);
INSERT INTO GD_SHP
	VALUES (11534338,
	1792,
	1344,
	2064,
	1504);
INSERT INTO GD_GE
	VALUES (11534339,
	11534337,
	11534337,
	42);
INSERT INTO GD_CON
	VALUES (11534339,
	11534338,
	11534338,
	0);
INSERT INTO GD_CTXT
	VALUES (11534339,
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
	VALUES (11534340,
	11534339,
	1824,
	1344,
	1824,
	1280,
	0);
INSERT INTO GD_LS
	VALUES (11534341,
	11534339,
	1824,
	1280,
	2016,
	1280,
	11534340);
INSERT INTO GD_LS
	VALUES (11534342,
	11534339,
	2016,
	1280,
	2016,
	1344,
	11534341);
INSERT INTO O_OBJ
	VALUES (10485763,
	'Object B',
	3,
	'OB',
	'',
	10485780);
INSERT INTO O_TFR
	VALUES (10485761,
	10485763,
	'op1',
	'',
	524305,
	0,
	'if(param.color_one != color::black)
	LOG::LogFailure(message:"Operation OB::op1 - param.color_one != color::black");
else
	LOG::LogSuccess(message:"Operation OB::op1 - param.color_one == color::black");
end if;

if(param.color_two != color::orange)
	LOG::LogFailure(message:"Operation OB::op1 - param.color_two != color::orange");
else
	LOG::LogSuccess(message:"Operation OB::op1 - param.color_two == color::orange");
end if;

if(param.count == 1)
	return param.color_one;
elif(param.count == 2)
	return color::black;
else
	x = color::black;
	return x;
end if;',
	1);
INSERT INTO O_TPARM
	VALUES (10485761,
	10485761,
	'color_one',
	524305,
	0);
INSERT INTO O_TPARM
	VALUES (10485762,
	10485761,
	'color_two',
	524305,
	0);
INSERT INTO O_TPARM
	VALUES (10485763,
	10485761,
	'count',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (10485762,
	10485763,
	'op2',
	'',
	524305,
	0,
	'if(param.color_one != color::black)
	LOG::LogFailure(message:"bridge1 - param.color_one != color::black");
else
	LOG::LogSuccess(message:"bridge1 - param.color_one == color::black");
end if;

if(param.color_two != color::orange)
	LOG::LogFailure(message:"bridge1 - param.color_two != color::orange");
else
	LOG::LogSuccess(message:"bridge1 - param.color_two == color::orange");
end if;

if(param.count == 1)
	param.color_two = param.color_one;
	return param.color_one;
elif(param.count == 2)
	param.color_two = color::black;
	return color::black;
else
	x = color::black;
	param.color_two = x;
	return x;
end if;',
	1);
INSERT INTO O_TPARM
	VALUES (10485764,
	10485762,
	'color_one',
	524305,
	0);
INSERT INTO O_TPARM
	VALUES (10485765,
	10485762,
	'color_two',
	524305,
	1);
INSERT INTO O_TPARM
	VALUES (10485766,
	10485762,
	'count',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (10485763,
	10485763,
	'op3',
	'',
	524289,
	0,
	'if(param.color_one != color::black)
	LOG::LogFailure(message:"bridge1 - param.color_one != color::black");
else
	LOG::LogSuccess(message:"bridge1 - param.color_one == color::black");
end if;

if(param.color_two != color::orange)
	LOG::LogFailure(message:"bridge1 - param.color_two != color::orange");
else
	LOG::LogSuccess(message:"bridge1 - param.color_two == color::orange");
end if;

x = param.color_one;
param.color_one = param.color_two;
param.color_two = x;
',
	1);
INSERT INTO O_TPARM
	VALUES (10485767,
	10485763,
	'color_one',
	524305,
	1);
INSERT INTO O_TPARM
	VALUES (10485768,
	10485763,
	'color_two',
	524305,
	1);
INSERT INTO O_DBATTR
	VALUES (10485781,
	10485763,
	'if(self.color_two == color::red)
	self.color_changer = color::violet;
else
	self.color_changer = color::black;
end if;',
	1);
INSERT INTO O_BATTR
	VALUES (10485781,
	10485763);
INSERT INTO O_ATTR
	VALUES (10485781,
	10485763,
	0,
	'color_changer',
	'',
	'',
	'color_changer',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (10485766,
	10485763);
INSERT INTO O_BATTR
	VALUES (10485766,
	10485763);
INSERT INTO O_ATTR
	VALUES (10485766,
	10485763,
	10485781,
	'ob_id',
	'',
	'',
	'ob_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (10485767,
	10485763);
INSERT INTO O_BATTR
	VALUES (10485767,
	10485763);
INSERT INTO O_ATTR
	VALUES (10485767,
	10485763,
	10485766,
	'color_one',
	'',
	'',
	'color_one',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (10485768,
	10485763);
INSERT INTO O_BATTR
	VALUES (10485768,
	10485763);
INSERT INTO O_ATTR
	VALUES (10485768,
	10485763,
	10485767,
	'color_two',
	'',
	'',
	'color_two',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (10485769,
	10485763);
INSERT INTO O_BATTR
	VALUES (10485769,
	10485763);
INSERT INTO O_ATTR
	VALUES (10485769,
	10485763,
	10485768,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	10485763);
INSERT INTO O_OIDA
	VALUES (10485766,
	10485763,
	0);
INSERT INTO SM_ISM
	VALUES (12058647,
	10485763);
INSERT INTO SM_SM
	VALUES (12058647,
	'',
	23);
INSERT INTO SM_MOORE
	VALUES (12058647);
INSERT INTO SM_EVTDI
	VALUES (12058625,
	12058647,
	'color_one',
	'',
	524305);
INSERT INTO SM_EVTDI
	VALUES (12058626,
	12058647,
	'color_two',
	'',
	524305);
INSERT INTO SM_SUPDT
	VALUES (12058625,
	12058647,
	0);
INSERT INTO SM_SDI
	VALUES (12058626,
	12058625,
	12058647);
INSERT INTO SM_SDI
	VALUES (12058625,
	12058625,
	12058647);
INSERT INTO SM_STATE
	VALUES (12058625,
	12058647,
	12058625,
	'Class Based Operations',
	1,
	0);
INSERT INTO SM_LEVT
	VALUES (12058625,
	12058647,
	12058625);
INSERT INTO SM_SEVT
	VALUES (12058625,
	12058647,
	12058625);
INSERT INTO SM_EVT
	VALUES (12058625,
	12058647,
	12058625,
	1,
	'Go',
	0,
	'',
	'OB1',
	'');
INSERT INTO SM_SEME
	VALUES (12058625,
	12058625,
	12058647,
	12058625);
INSERT INTO SM_LEVT
	VALUES (12058626,
	12058647,
	12058625);
INSERT INTO SM_SEVT
	VALUES (12058626,
	12058647,
	12058625);
INSERT INTO SM_EVT
	VALUES (12058626,
	12058647,
	12058625,
	2,
	'Next',
	0,
	'',
	'OB2',
	'');
INSERT INTO SM_SEME
	VALUES (12058625,
	12058626,
	12058647,
	12058625);
INSERT INTO SM_STATE
	VALUES (12058626,
	12058647,
	12058625,
	'Instance Based Operations',
	2,
	0);
INSERT INTO SM_EIGN
	VALUES (12058626,
	12058625,
	12058647,
	12058625,
	'');
INSERT INTO SM_SEME
	VALUES (12058626,
	12058625,
	12058647,
	12058625);
INSERT INTO SM_EIGN
	VALUES (12058626,
	12058626,
	12058647,
	12058625,
	'');
INSERT INTO SM_SEME
	VALUES (12058626,
	12058626,
	12058647,
	12058625);
INSERT INTO SM_NSTXN
	VALUES (12058625,
	12058647,
	12058625,
	12058625,
	12058625);
INSERT INTO SM_TXN
	VALUES (12058625,
	12058647,
	12058625,
	12058625);
INSERT INTO SM_NSTXN
	VALUES (12058626,
	12058647,
	12058625,
	12058626,
	12058625);
INSERT INTO SM_TXN
	VALUES (12058626,
	12058647,
	12058626,
	12058625);
INSERT INTO SM_MOAH
	VALUES (12058625,
	12058647,
	12058625);
INSERT INTO SM_AH
	VALUES (12058625,
	12058647);
INSERT INTO SM_ACT
	VALUES (12058625,
	12058647,
	1,
	'self.color_one = rcvd_evt.color_one;
self.color_two = rcvd_evt.color_two;

if(self.color_one != color::red)
	LOG::LogFailure(message:"Event OB1 across objects - color_one != color::red");
else
	LOG::LogSuccess(message:"Event OB1 across objects - color_one == color::red");
end if;
if(self.color_two != color::violet)
	LOG::LogFailure(message:"Event OB1 across objects - color_two != color::violet");
else
	LOG::LogSuccess(message:"Event OB1 across objects - color_two == color::violet");
end if;

self.color_one = color::black;
self.color_two = color::orange;
return_value = OB::op1(color_one:self.color_one, color_two:self.color_two, count:1);
if(return_value != color::black)
	LOG::LogFailure(message:"First Call: Operation OB::op1 - return_value != color::black");
else
	LOG::LogSuccess(message:"First Call: Operation OB::op1 - return_value == color::black");
end if;

return_value = OB::op1(color_one:color::black, color_two:color::orange, count:2);
if(return_value != color::black)
	LOG::LogFailure(message:"Second Call: Operation OB::op1 - return_value != color::black");
else
	LOG::LogSuccess(message:"Second Call: Operation OB::op1 - return_value == color::black");
end if;

black = color::black;
orange = color::orange;
return_value = OB::op1(color_one:black, color_two:orange, count:3);
if(return_value != color::black)
	LOG::LogFailure(message:"Third Call: Operation OB::op1 - return_value != color::black");
else
	LOG::LogSuccess(message:"Third Call: Operation OB::op1 - return_value == color::black");
end if;

self.color_one = color::black;
self.color_two = color::orange;
return_value = OB::op2(color_one:self.color_one, color_two:self.color_two, count:1);
if(return_value != color::black)
	LOG::LogFailure(message:"First Call: Operation OB::op2 - return_value != color::black");
else
	LOG::LogSuccess(message:"First Call: Operation OB::op2 - return_value == color::black");
end if;
if(self.color_two != color::black)
	LOG::LogFailure(message:"First Call: Operation OB::op2 - self.color_two != color::black");
else
	LOG::LogSuccess(message:"First Call: Operation OB::op2 - self.color_two == color::black");
end if;


orange = color::orange;
return_value = OB::op2(color_one:color::black, color_two:orange, count:2);
if(return_value != color::black)
	LOG::LogFailure(message:"Second Call: Operation OB::op2 - return_value != color::black");
else
	LOG::LogSuccess(message:"Second Call: Operation OB::op2 - return_value == color::black");
end if;
if(orange != color::black)
	LOG::LogFailure(message:"Second Call: Operation OB::op2 - orange != color::black");
else
	LOG::LogSuccess(message:"Second Call: Operation OB::op2 - orange == color::black");
end if;

black = color::black;
orange = color::orange;
return_value = OB::op2(color_one:black, color_two:orange, count:3);
if(return_value != color::black)
	LOG::LogFailure(message:"Third Call: Operation OB::op2 - return_value != color::black");
else
	LOG::LogSuccess(message:"Third Call: Operation OB::op2 - return_value == color::black");
end if;
if(orange != color::black)
	LOG::LogFailure(message:"Third Call: Operation OB::op2 - orange != color::black");
else
	LOG::LogSuccess(message:"Third Call: Operation OB::op2 - orange == color::black");
end if;


self.color_one = color::black;
self.color_two = color::orange;
OB::op3(color_one:self.color_one, color_two:self.color_two);
if(self.color_one != color::orange)
	LOG::LogFailure(message:"First Call: Operation OB::op3 - self.color_one != color::orange");
else
	LOG::LogSuccess(message:"First Call: Operation OB::op3 - self.color_one == color::orange");
end if;
if(orange != color::black)
	LOG::LogFailure(message:"First Call: Operation OB::op3 - self.color_two != color::black");
else
	LOG::LogSuccess(message:"First Call: Operation OB::op3 - self.color_two == color::black");
end if;

orange = color::orange;
black = color::black;
OB::op3(color_one:black, color_two:orange);
if(black != color::orange)
	LOG::LogFailure(message:"Second Call: Operation OB::op3 - black != color::orange");
else
	LOG::LogSuccess(message:"Second Call: Operation OB::op3 - black == color::orange");
end if;
if(orange != color::black)
	LOG::LogFailure(message:"Second Call: Operation OB::op3 - orange != color::black");
else
	LOG::LogSuccess(message:"Second Call: Operation OB::op3 - orange == color::black");
end if;

generate OB2:''Next''(color_one:color::red, color_two:color::green) to self;',
	'Receives Event across objects
Calls Operation/Transformer passing enumerations by value using 
object attributes, enumerators, and local variables');
INSERT INTO SM_MOAH
	VALUES (12058626,
	12058647,
	12058626);
INSERT INTO SM_AH
	VALUES (12058626,
	12058647);
INSERT INTO SM_ACT
	VALUES (12058626,
	12058647,
	1,
	'self.color_one = rcvd_evt.color_one;
self.color_two = rcvd_evt.color_two;

if(self.color_one != color::red)
	LOG::LogFailure(message:"Event OB2 within objects - color_one != color::red");
else
	LOG::LogSuccess(message:"Event OB2 within objects - color_one == color::red");
end if;
if(self.color_two != color::green)
	LOG::LogFailure(message:"Event OB2 within objects - color_two != color::green");
else
	LOG::LogSuccess(message:"Event OB2 within objects - color_two == color::greent");
end if;

select any inst_obj from instances of OC;
self.color_one = color::red;
self.color_two = color::green;
return_value = inst_obj.op4(color_one:self.color_one, color_two:self.color_two, count:1);
if(return_value != color::red)
	LOG::LogFailure(message:"First Call: Operation inst_obj.op4 - return_value != color::red");
else
	LOG::LogSuccess(message:"First Call: Operation inst_obj.op4 - return_value == color::red");
end if;

return_value = inst_obj.op4(color_one:color::red, color_two:color::green, count:2);
if(return_value != color::red)
	LOG::LogFailure(message:"Second Call: Operation inst_obj.op4 - return_value != color::red");
else
	LOG::LogSuccess(message:"Second Call: Operation inst_obj.op4 - return_value == color::red");
end if;

red = color::red;
green = color::green;
return_value = inst_obj.op4(color_one:red, color_two:green, count:3);
if(return_value != color::red)
	LOG::LogFailure(message:"Third Call: Operation inst_obj.op4 - return_value != color::red");
else
	LOG::LogSuccess(message:"Third Call: Operation inst_obj.op4 - return_value == color::red");
end if;

self.color_one = color::red;
self.color_two = color::green;
return_value = inst_obj.op5(color_one:self.color_one, color_two:self.color_two, count:1);
if(return_value != color::red)
	LOG::LogFailure(message:"First Call: Operation inst_obj.op5 - return_value != color::red");
else
	LOG::LogSuccess(message:"First Call: Operation inst_obj.op5 - return_value == color::red");
end if;
if(self.color_two != color::red)
	LOG::LogFailure(message:"First Call: Operation inst_obj.op5 - self.color_two != color::red");
else
	LOG::LogSuccess(message:"First Call: Operation inst_obj.op5 - self.color_two == color::red");
end if;


green = color::green;
return_value = inst_obj.op5(color_one:color::red, color_two:green, count:2);
if(return_value != color::red)
	LOG::LogFailure(message:"Second Call: Operation inst_obj.op5 - return_value != color::red");
else
	LOG::LogSuccess(message:"Second Call: Operation inst_obj.op5 - return_value == color::red");
end if;
if(green != color::red)
	LOG::LogFailure(message:"Second Call: Operation inst_obj.op5 - green != color::red");
else
	LOG::LogSuccess(message:"Second Call: Operation inst_obj.op5 - green == color::red");
end if;

red = color::red;
green = color::green;
return_value = inst_obj.op5(color_one:red, color_two:green, count:3);
if(return_value != color::red)
	LOG::LogFailure(message:"Third Call: Operation inst_obj.op5 - return_value != color::red");
else
	LOG::LogSuccess(message:"Third Call: Operation inst_obj.op5 - return_value == color::red");
end if;
if(green != color::red)
	LOG::LogFailure(message:"Third Call: Operation inst_obj.op5 - green != color::red");
else
	LOG::LogSuccess(message:"Third Call: Operation inst_obj.op5 - green == color::red");
end if;


self.color_one = color::red;
self.color_two = color::green;
inst_obj.op6(color_one:self.color_one, color_two:self.color_two);
if(self.color_one != color::green)
	LOG::LogFailure(message:"First Call: Operation inst_obj.op6 - self.color_one != color::green");
else
	LOG::LogSuccess(message:"First Call: Operation inst_obj.op6 - self.color_one == color::green");
end if;
if(green != color::red)
	LOG::LogFailure(message:"First Call: Operation inst_obj.op6 - self.color_two != color::red");
else
	LOG::LogSuccess(message:"First Call: Operation inst_obj.op6 - self.color_two == color::red");
end if;

green = color::green;
red = color::red;
inst_obj.op6(color_one:red, color_two:green);
if(red != color::green)
	LOG::LogFailure(message:"Second Call: Operation inst_obj.op6 - red != color::green");
else
	LOG::LogSuccess(message:"Second Call: Operation inst_obj.op6 - red == color::green");
end if;
if(green != color::red)
	LOG::LogFailure(message:"Second Call: Operation inst_obj.op6 - green != color::red");
else
	LOG::LogSuccess(message:"Second Call: Operation inst_obj.op6 - green == color::red");
end if;

select any oc from instances of OC;
generate OC1:''Go''() to oc;',
	'');
INSERT INTO GD_MD
	VALUES (12058625,
	8,
	12058647,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1740,
	3718,
	0.870284,
	0);
INSERT INTO GD_GE
	VALUES (12058626,
	12058625,
	12058625,
	41);
INSERT INTO GD_SHP
	VALUES (12058626,
	1760,
	1312,
	2496,
	1680);
INSERT INTO GD_GE
	VALUES (12058739,
	12058625,
	12058626,
	41);
INSERT INTO GD_SHP
	VALUES (12058739,
	1760,
	1744,
	2496,
	2080);
INSERT INTO GD_GE
	VALUES (12058627,
	12058625,
	12058625,
	42);
INSERT INTO GD_CON
	VALUES (12058627,
	12058626,
	12058626,
	0);
INSERT INTO GD_CTXT
	VALUES (12058627,
	0,
	0,
	0,
	0,
	0,
	0,
	1888,
	1223,
	2038,
	1263,
	-7,
	5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (12058736,
	12058627,
	1808,
	1312,
	1808,
	1264,
	0);
INSERT INTO GD_LS
	VALUES (12058737,
	12058627,
	1808,
	1264,
	2128,
	1264,
	12058736);
INSERT INTO GD_LS
	VALUES (12058738,
	12058627,
	2128,
	1264,
	2128,
	1312,
	12058737);
INSERT INTO GD_GE
	VALUES (12058740,
	12058625,
	12058626,
	42);
INSERT INTO GD_CON
	VALUES (12058740,
	12058626,
	12058739,
	0);
INSERT INTO GD_CTXT
	VALUES (12058740,
	0,
	0,
	0,
	0,
	0,
	0,
	2112,
	1698,
	2343,
	1720,
	253,
	-5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (12058741,
	12058740,
	2096,
	1680,
	2096,
	1744,
	0);
INSERT INTO O_OBJ
	VALUES (10485764,
	'Object C',
	4,
	'OC',
	'',
	10485780);
INSERT INTO O_TFR
	VALUES (10485764,
	10485764,
	'op4',
	'',
	524305,
	1,
	'if(param.color_one != color::red)
	LOG::LogFailure(message:"Operation OB::op1 - param.color_one != color::red");
else
	LOG::LogSuccess(message:"Operation OB::op1 - param.color_one == color::red");
end if;

if(param.color_two != color::green)
	LOG::LogFailure(message:"Operation OB::op1 - param.color_two != color::green");
else
	LOG::LogSuccess(message:"Operation OB::op1 - param.color_two == color::green");
end if;

if(param.count == 1)
	return param.color_one;
elif(param.count == 2)
	return color::red;
else
	x = color::red;
	return x;
end if;',
	1);
INSERT INTO O_TPARM
	VALUES (10485769,
	10485764,
	'color_one',
	524305,
	0);
INSERT INTO O_TPARM
	VALUES (10485770,
	10485764,
	'color_two',
	524305,
	0);
INSERT INTO O_TPARM
	VALUES (10485771,
	10485764,
	'count',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (10485765,
	10485764,
	'op5',
	'',
	524305,
	1,
	'if(param.color_one != color::red)
	LOG::LogFailure(message:"bridge1 - param.color_one != color::red");
else
	LOG::LogSuccess(message:"bridge1 - param.color_one == color::red");
end if;

if(param.color_two != color::green)
	LOG::LogFailure(message:"bridge1 - param.color_two != color::green");
else
	LOG::LogSuccess(message:"bridge1 - param.color_two == color::green");
end if;

if(param.count == 1)
	param.color_two = param.color_one;
	return param.color_one;
elif(param.count == 2)
	param.color_two = color::red;
	return color::red;
else
	x = color::red;
	param.color_two = x;
	return x;
end if;',
	1);
INSERT INTO O_TPARM
	VALUES (10485772,
	10485765,
	'color_one',
	524305,
	0);
INSERT INTO O_TPARM
	VALUES (10485773,
	10485765,
	'color_two',
	524305,
	1);
INSERT INTO O_TPARM
	VALUES (10485774,
	10485765,
	'count',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (10485766,
	10485764,
	'op6',
	'',
	524289,
	1,
	'if(param.color_one != color::red)
	LOG::LogFailure(message:"bridge1 - param.color_one != color::red");
else
	LOG::LogSuccess(message:"bridge1 - param.color_one == color::red");
end if;

if(param.color_two != color::green)
	LOG::LogFailure(message:"bridge1 - param.color_two != color::green");
else
	LOG::LogSuccess(message:"bridge1 - param.color_two == color::green");
end if;

x = param.color_one;
param.color_one = param.color_two;
param.color_two = x;',
	1);
INSERT INTO O_TPARM
	VALUES (10485775,
	10485766,
	'color_one',
	524305,
	1);
INSERT INTO O_TPARM
	VALUES (10485776,
	10485766,
	'color_two',
	524305,
	1);
INSERT INTO O_DBATTR
	VALUES (10485780,
	10485764,
	'if(self.color_two == color::purple)
	self.color_changer = color::green;
else
	self.color_changer = color::red;
end if;',
	1);
INSERT INTO O_BATTR
	VALUES (10485780,
	10485764);
INSERT INTO O_ATTR
	VALUES (10485780,
	10485764,
	0,
	'color_changer',
	'',
	'',
	'color_changer',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (10485771,
	10485764);
INSERT INTO O_BATTR
	VALUES (10485771,
	10485764);
INSERT INTO O_ATTR
	VALUES (10485771,
	10485764,
	10485780,
	'oc_id',
	'',
	'',
	'oc_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (10485772,
	10485764);
INSERT INTO O_BATTR
	VALUES (10485772,
	10485764);
INSERT INTO O_ATTR
	VALUES (10485772,
	10485764,
	10485771,
	'color_one',
	'',
	'',
	'color_one',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (10485773,
	10485764);
INSERT INTO O_BATTR
	VALUES (10485773,
	10485764);
INSERT INTO O_ATTR
	VALUES (10485773,
	10485764,
	10485772,
	'color_two',
	'',
	'',
	'color_two',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (10485770,
	10485764);
INSERT INTO O_BATTR
	VALUES (10485770,
	10485764);
INSERT INTO O_ATTR
	VALUES (10485770,
	10485764,
	10485773,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	10485764);
INSERT INTO O_OIDA
	VALUES (10485771,
	10485764,
	0);
INSERT INTO SM_ISM
	VALUES (12582936,
	10485764);
INSERT INTO SM_SM
	VALUES (12582936,
	'',
	24);
INSERT INTO SM_MOORE
	VALUES (12582936);
INSERT INTO SM_SUPDT
	VALUES (12582913,
	12582936,
	0);
INSERT INTO SM_STATE
	VALUES (12582913,
	12582936,
	12582913,
	'Bridges',
	1,
	0);
INSERT INTO SM_LEVT
	VALUES (12582913,
	12582936,
	12582913);
INSERT INTO SM_SEVT
	VALUES (12582913,
	12582936,
	12582913);
INSERT INTO SM_EVT
	VALUES (12582913,
	12582936,
	12582913,
	1,
	'Go',
	0,
	'',
	'OC1',
	'');
INSERT INTO SM_SEME
	VALUES (12582913,
	12582913,
	12582936,
	12582913);
INSERT INTO SM_NSTXN
	VALUES (12582913,
	12582936,
	12582913,
	12582913,
	12582913);
INSERT INTO SM_TXN
	VALUES (12582913,
	12582936,
	12582913,
	12582913);
INSERT INTO SM_MOAH
	VALUES (12582913,
	12582936,
	12582913);
INSERT INTO SM_AH
	VALUES (12582913,
	12582936);
INSERT INTO SM_ACT
	VALUES (12582913,
	12582936,
	1,
	'self.color_one = color::purple;
self.color_two = color::blue;

return_value = E4::bridge1(color_one:self.color_one, color_two:self.color_two, count:1);
if(return_value != color::purple)
	LOG::LogFailure(message:"First Call: bridge1 - return_value != color::purple");
else
	LOG::LogSuccess(message:"First Call: bridge1 - return_value == color::purple");
end if;

return_value = E4::bridge1(color_one:color::purple, color_two:color::blue, count:2);
if(return_value != color::purple)
	LOG::LogFailure(message:"Second Call: bridge1 - return_value != color::purple");
else
	LOG::LogSuccess(message:"Second Call: bridge1 - return_value == color::purple");
end if;

a = color::purple;
b = color::blue;
return_value = E4::bridge1(color_one:a, color_two:b, count:3);
if(return_value != color::purple)
	LOG::LogFailure(message:"Third Call: bridge1 - return_value != color::purple");
else
	LOG::LogSuccess(message:"Third Call: bridge1 - return_value == color::purple");
end if;

return_value = E4::bridge2(color_one:self.color_one, color_two:self.color_two, count:1);
if(return_value != color::purple)
	LOG::LogFailure(message:"First Call: bridge2 - return_value != color::purple");
else
	LOG::LogSuccess(message:"First Call: bridge2 - return_value == color::purple");
end if;
if(self.color_two != color::purple)
	LOG::LogFailure(message:"First Call: bridge2 - self.color_two != color::purple");
else
	LOG::LogSuccess(message:"First Call: bridge2 - self.color_two == color::purple");
end if;

b = color::blue;
return_value = E4::bridge2(color_one:color::purple, color_two:b, count:2);
if(return_value != color::purple)
	LOG::LogFailure(message:"Second Call: bridge2 - return_value != color::purple");
else
	LOG::LogSuccess(message:"Second Call: bridge2 - return_value == color::purple");
end if;
if(b != color::purple)
	LOG::LogFailure(message:"Second Call: bridge2 - b != color::purple");
else
	LOG::LogSuccess(message:"Second Call: bridge2 - b == color::purple");
end if;

a = color::purple;
b = color::blue;
return_value = E4::bridge2(color_one:a, color_two:b, count:3);
if(return_value != color::purple)
	LOG::LogFailure(message:"Third Call: bridge2 - return_value != color::purple");
else
	LOG::LogSuccess(message:"Third Call: bridge2 - return_value == color::purple");
end if;
if(b != color::purple)
	LOG::LogFailure(message:"Third Call: bridge2 - b != color::purple");
else
	LOG::LogSuccess(message:"Third Call: bridge2 - b == color::purple");
end if;

self.color_one = color::purple;
self.color_two = color::blue;
E4::bridge3(color_one:self.color_one, color_two:self.color_two);
if(self.color_one != color::blue)
	LOG::LogFailure(message:"First Call: bridge3 - self.color_one != color::blue");
else
	LOG::LogSuccess(message:"First Call: bridge3 - self.color_one == color::blue");
end if;
if(self.color_two != color::purple)
	LOG::LogFailure(message:"First Call: bridge3 - self.color_two != color::purple");
else
	LOG::LogSuccess(message:"First Call: bridge3 - self.color_two == color::purple");
end if;


a = color::purple;
b = color::blue;
E4::bridge3(color_one:a, color_two:b);
if(a != color::blue)
	LOG::LogFailure(message:"Second Call: bridge3 - a != color::blue");
else
	LOG::LogSuccess(message:"Second Call: bridge3 - a == color::blue");
end if;
if(b != color::purple)
	LOG::LogFailure(message:"Second Call: bridge3 - b != color::purple");
else
	LOG::LogSuccess(message:"Second Call: bridge3 - b == color::purple");
end if;

select any od from instances of OD;
generate OD1:''Go'' to od;
',
	'Makes calls to bridge1 sending enumerated types
from object attributes, enumerators, and local variables
Checks the return_value
shuts down');
INSERT INTO GD_MD
	VALUES (12582913,
	8,
	12582936,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	4257,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (12582914,
	12582913,
	12582913,
	41);
INSERT INTO GD_SHP
	VALUES (12582914,
	1664,
	1280,
	2304,
	1552);
INSERT INTO GD_GE
	VALUES (12582915,
	12582913,
	12582913,
	42);
INSERT INTO GD_CON
	VALUES (12582915,
	12582914,
	12582914,
	0);
INSERT INTO GD_CTXT
	VALUES (12582915,
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
	VALUES (12582916,
	12582915,
	1760,
	1280,
	1760,
	1232,
	0);
INSERT INTO GD_LS
	VALUES (12582917,
	12582915,
	1760,
	1232,
	2160,
	1232,
	12582916);
INSERT INTO GD_LS
	VALUES (12582918,
	12582915,
	2160,
	1232,
	2160,
	1280,
	12582917);
INSERT INTO O_OBJ
	VALUES (10485765,
	'Object D',
	5,
	'OD',
	'',
	10485780);
INSERT INTO O_DBATTR
	VALUES (10485778,
	10485765,
	'if(self.color_two == color::purple)
	self.color_changer = color::rouge;
else
	self.color_changer = color::red;
end if;',
	1);
INSERT INTO O_BATTR
	VALUES (10485778,
	10485765);
INSERT INTO O_ATTR
	VALUES (10485778,
	10485765,
	0,
	'color_changer',
	'',
	'',
	'color_changer',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (10485774,
	10485765);
INSERT INTO O_BATTR
	VALUES (10485774,
	10485765);
INSERT INTO O_ATTR
	VALUES (10485774,
	10485765,
	10485778,
	'od_id',
	'',
	'',
	'od_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (10485775,
	10485765);
INSERT INTO O_BATTR
	VALUES (10485775,
	10485765);
INSERT INTO O_ATTR
	VALUES (10485775,
	10485765,
	10485774,
	'color_one',
	'',
	'',
	'color_one',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (10485776,
	10485765);
INSERT INTO O_BATTR
	VALUES (10485776,
	10485765);
INSERT INTO O_ATTR
	VALUES (10485776,
	10485765,
	10485775,
	'color_two',
	'',
	'',
	'color_two',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (10485777,
	10485765);
INSERT INTO O_BATTR
	VALUES (10485777,
	10485765);
INSERT INTO O_ATTR
	VALUES (10485777,
	10485765,
	10485776,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	10485765);
INSERT INTO O_OIDA
	VALUES (10485775,
	10485765,
	0);
INSERT INTO SM_ISM
	VALUES (13107225,
	10485765);
INSERT INTO SM_SM
	VALUES (13107225,
	'',
	25);
INSERT INTO SM_MOORE
	VALUES (13107225);
INSERT INTO SM_EVTDI
	VALUES (13107201,
	13107225,
	'color_one',
	'',
	524305);
INSERT INTO SM_EVTDI
	VALUES (13107202,
	13107225,
	'color_two',
	'',
	524305);
INSERT INTO SM_SUPDT
	VALUES (13107202,
	13107225,
	0);
INSERT INTO SM_SUPDT
	VALUES (13107204,
	13107225,
	0);
INSERT INTO SM_SDI
	VALUES (13107202,
	13107204,
	13107225);
INSERT INTO SM_SDI
	VALUES (13107201,
	13107204,
	13107225);
INSERT INTO SM_STATE
	VALUES (13107202,
	13107225,
	13107202,
	'Functions',
	1,
	0);
INSERT INTO SM_LEVT
	VALUES (13107202,
	13107225,
	13107202);
INSERT INTO SM_SEVT
	VALUES (13107202,
	13107225,
	13107202);
INSERT INTO SM_EVT
	VALUES (13107202,
	13107225,
	13107202,
	1,
	'Go',
	0,
	'',
	'OD1',
	'');
INSERT INTO SM_SEME
	VALUES (13107202,
	13107202,
	13107225,
	13107202);
INSERT INTO SM_LEVT
	VALUES (13107203,
	13107225,
	13107204);
INSERT INTO SM_SEVT
	VALUES (13107203,
	13107225,
	13107204);
INSERT INTO SM_EVT
	VALUES (13107203,
	13107225,
	13107204,
	2,
	'Next',
	0,
	'',
	'OD2',
	'');
INSERT INTO SM_SEME
	VALUES (13107202,
	13107203,
	13107225,
	13107204);
INSERT INTO SM_STATE
	VALUES (13107203,
	13107225,
	13107204,
	'MDA',
	2,
	0);
INSERT INTO SM_EIGN
	VALUES (13107203,
	13107202,
	13107225,
	13107202,
	'');
INSERT INTO SM_SEME
	VALUES (13107203,
	13107202,
	13107225,
	13107202);
INSERT INTO SM_EIGN
	VALUES (13107203,
	13107203,
	13107225,
	13107204,
	'');
INSERT INTO SM_SEME
	VALUES (13107203,
	13107203,
	13107225,
	13107204);
INSERT INTO SM_NSTXN
	VALUES (13107202,
	13107225,
	13107202,
	13107202,
	13107202);
INSERT INTO SM_TXN
	VALUES (13107202,
	13107225,
	13107202,
	13107202);
INSERT INTO SM_NSTXN
	VALUES (13107203,
	13107225,
	13107202,
	13107203,
	13107204);
INSERT INTO SM_TXN
	VALUES (13107203,
	13107225,
	13107203,
	13107204);
INSERT INTO SM_MOAH
	VALUES (13107202,
	13107225,
	13107202);
INSERT INTO SM_AH
	VALUES (13107202,
	13107225);
INSERT INTO SM_ACT
	VALUES (13107202,
	13107225,
	1,
	'self.color_one = color::purple;
self.color_two = color::blue;

return_value = ::function1(color_one:self.color_one, color_two:self.color_two, count:1);
if(return_value != color::purple)
	LOG::LogFailure(message:"First Call: function1 - return_value != color::purple");
else
	LOG::LogSuccess(message:"First Call: function1 - return_value == color::purple");
end if;

return_value = ::function1(color_one:color::purple, color_two:color::blue, count:2);
if(return_value != color::purple)
	LOG::LogFailure(message:"Second Call: function1 - return_value != color::purple");
else
	LOG::LogSuccess(message:"Second Call: function1 - return_value == color::purple");
end if;

a = color::purple;
b = color::blue;
return_value = ::function1(color_one:a, color_two:b, count:3);
if(return_value != color::purple)
	LOG::LogFailure(message:"Third Call: function1 - return_value != color::purple");
else
	LOG::LogSuccess(message:"Third Call: function1 - return_value == color::purple");
end if;

return_value = ::function2(color_one:self.color_one, color_two:self.color_two, count:1);
if(return_value != color::purple)
	LOG::LogFailure(message:"First Call: function2 - return_value != color::purple");
else
	LOG::LogSuccess(message:"First Call: function2 - return_value == color::purple");
end if;
if(self.color_two != color::purple)
	LOG::LogFailure(message:"First Call: function2 - self.color_two != color::purple");
else
	LOG::LogSuccess(message:"First Call: function2 - self.color_two == color::purple");
end if;

return_value = ::function2(color_one:color::purple, color_two:b, count:2);
if(return_value != color::purple)
	LOG::LogFailure(message:"Second Call: function2 - return_value != color::purple");
else
	LOG::LogSuccess(message:"Second Call: function2 - return_value == color::purple");
end if;
if(b != color::purple)
	LOG::LogFailure(message:"Second Call: function2 - b != color::purple");
else
	LOG::LogSuccess(message:"Second Call: function2 - b == color::purple");
end if;

a = color::purple;
b = color::blue;
return_value = ::function2(color_one:a, color_two:b, count:3);
if(return_value != color::purple)
	LOG::LogFailure(message:"Third Call: function2 - return_value != color::purple");
else
	LOG::LogSuccess(message:"Third Call: function2 - return_value == color::purple");
end if;
if(b != color::purple)
	LOG::LogFailure(message:"Third Call: function2 - b != color::purple");
else
	LOG::LogSuccess(message:"Third Call: function2 - b == color::purple");
end if;

self.color_one = color::purple;
self.color_two = color::blue;
::function3(color_one:self.color_one, color_two:self.color_two);
if(self.color_one != color::blue)
	LOG::LogFailure(message:"First Call: function3 - self.color_one != color::blue");
else
	LOG::LogSuccess(message:"First Call: function3 - self.color_one == color::blue");
end if;
if(self.color_two != color::purple)
	LOG::LogFailure(message:"First Call: function3 - self.color_two != color::purple");
else
	LOG::LogSuccess(message:"First Call: function3 - self.color_two == color::purple");
end if;


a = color::purple;
b = color::blue;
::function3(color_one:a, color_two:b);
if(a != color::blue)
	LOG::LogFailure(message:"Second Call: function3 - a != color::blue");
else
	LOG::LogSuccess(message:"Second Call: function3 - a == color::blue");
end if;
if(b != color::purple)
	LOG::LogFailure(message:"Second Call: function3 - b != color::purple");
else
	LOG::LogSuccess(message:"Second Call: function3 - b == color::purple");
end if;

generate OD2:''Next''(color_one:a, color_two:b) to self;',
	'Makes calls to bridge1 sending enumerated types
from object attributes, enumerators, and local variables
Checks the return_value
shuts down');
INSERT INTO SM_MOAH
	VALUES (13107203,
	13107225,
	13107203);
INSERT INTO SM_AH
	VALUES (13107203,
	13107225);
INSERT INTO SM_ACT
	VALUES (13107203,
	13107225,
	1,
	'if(rcvd_evt.color_one != color::blue)
	LOG::LogFailure(message:"MDA - event item - param.color_one != color::blue");
else
	LOG::LogSuccess(message:"MDA - event item - param.color_one == color::blue");
end if;
if(rcvd_evt.color_two != color::purple)
	LOG::LogFailure(message:"MDA - event item - param.color_two != color::purple");
else
	LOG::LogSuccess(message:"MDA - event item - param.color_two == color::purple");
end if;

select any oa from instances of OA;
if(oa.color_changer != color::black)
	LOG::LogFailure(message:"MDA - Object A - color_changer != color::black");
else
	LOG::LogSuccess(message:"MDA - Object A - color_changer == color::black");
end if;

select any ob from instances of OB;
if(ob.color_changer != color::violet)
	LOG::LogFailure(message:"MDA - Object B - color_changer != color::violet");
else
	LOG::LogSuccess(message:"MDA - Object B - color_changer == color::violet");
end if;

select any oc from instances of OC;
if(oc.color_changer != color::green)
	LOG::LogFailure(message:"MDA - Object C - color_changer != color::green");
else
	LOG::LogSuccess(message:"MDA - Object C - color_changer == color::green");
end if;

select any od from instances of OD;
if(od.color_changer != color::rouge)
	LOG::LogFailure(message:"MDA - Object D - color_changer != color::rouge");
else
	LOG::LogSuccess(message:"MDA - Object D - color_changer == color::rouge");
end if;

ARCH::shutdown();',
	'');
INSERT INTO GD_MD
	VALUES (13107201,
	8,
	13107225,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	4062,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (13107207,
	13107201,
	13107202,
	41);
INSERT INTO GD_SHP
	VALUES (13107207,
	1664,
	1280,
	2304,
	1552);
INSERT INTO GD_GE
	VALUES (13107212,
	13107201,
	13107203,
	41);
INSERT INTO GD_SHP
	VALUES (13107212,
	1664,
	1648,
	2304,
	1888);
INSERT INTO GD_GE
	VALUES (13107208,
	13107201,
	13107202,
	42);
INSERT INTO GD_CON
	VALUES (13107208,
	13107207,
	13107207,
	0);
INSERT INTO GD_CTXT
	VALUES (13107208,
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
	VALUES (13107209,
	13107208,
	1760,
	1280,
	1760,
	1232,
	0);
INSERT INTO GD_LS
	VALUES (13107210,
	13107208,
	1760,
	1232,
	2160,
	1232,
	13107209);
INSERT INTO GD_LS
	VALUES (13107211,
	13107208,
	2160,
	1232,
	2160,
	1280,
	13107210);
INSERT INTO GD_GE
	VALUES (13107213,
	13107201,
	13107203,
	42);
INSERT INTO GD_CON
	VALUES (13107213,
	13107207,
	13107212,
	0);
INSERT INTO GD_CTXT
	VALUES (13107213,
	0,
	0,
	0,
	0,
	0,
	0,
	1922,
	1586,
	2115,
	1604,
	89,
	16,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (13107215,
	13107213,
	1904,
	1552,
	1904,
	1648,
	0);
INSERT INTO GD_MD
	VALUES (10485765,
	5,
	10485780,
	11,
	1,
	0,
	1,
	1,
	0,
	12,
	1281,
	4250,
	0.824869,
	0);
INSERT INTO GD_GE
	VALUES (10485772,
	10485765,
	10485761,
	21);
INSERT INTO GD_SHP
	VALUES (10485772,
	1328,
	1264,
	1744,
	1488);
INSERT INTO GD_GE
	VALUES (10485775,
	10485765,
	10485762,
	21);
INSERT INTO GD_SHP
	VALUES (10485775,
	1632,
	1008,
	1904,
	1200);
INSERT INTO GD_GE
	VALUES (10485780,
	10485765,
	10485763,
	21);
INSERT INTO GD_SHP
	VALUES (10485780,
	1328,
	1504,
	1744,
	1728);
INSERT INTO GD_GE
	VALUES (10485783,
	10485765,
	10485764,
	21);
INSERT INTO GD_SHP
	VALUES (10485783,
	1792,
	1264,
	2208,
	1488);
INSERT INTO GD_GE
	VALUES (10485817,
	10485765,
	10485765,
	21);
INSERT INTO GD_SHP
	VALUES (10485817,
	1792,
	1504,
	2208,
	1728);
INSERT INTO GD_MD
	VALUES (10485766,
	6,
	10485780,
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
	VALUES (10485774,
	10485766,
	10485761,
	21);
INSERT INTO GD_SHP
	VALUES (10485774,
	1616,
	1264,
	1808,
	1328);
INSERT INTO GD_GE
	VALUES (10485777,
	10485766,
	10485762,
	21);
INSERT INTO GD_SHP
	VALUES (10485777,
	1936,
	1264,
	2128,
	1328);
INSERT INTO GD_GE
	VALUES (10485782,
	10485766,
	10485763,
	21);
INSERT INTO GD_SHP
	VALUES (10485782,
	1616,
	1504,
	1808,
	1568);
INSERT INTO GD_GE
	VALUES (10485785,
	10485766,
	10485764,
	21);
INSERT INTO GD_SHP
	VALUES (10485785,
	1936,
	1504,
	2128,
	1568);
INSERT INTO GD_GE
	VALUES (10485818,
	10485766,
	10485765,
	21);
INSERT INTO GD_SHP
	VALUES (10485818,
	2016,
	1712,
	2208,
	1776);
INSERT INTO GD_MD
	VALUES (10485767,
	7,
	10485780,
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
	VALUES (10485773,
	10485767,
	10485761,
	21);
INSERT INTO GD_SHP
	VALUES (10485773,
	1616,
	1264,
	1808,
	1328);
INSERT INTO GD_GE
	VALUES (10485776,
	10485767,
	10485762,
	21);
INSERT INTO GD_SHP
	VALUES (10485776,
	1936,
	1264,
	2128,
	1328);
INSERT INTO GD_GE
	VALUES (10485781,
	10485767,
	10485763,
	21);
INSERT INTO GD_SHP
	VALUES (10485781,
	1616,
	1504,
	1808,
	1568);
INSERT INTO GD_GE
	VALUES (10485784,
	10485767,
	10485764,
	21);
INSERT INTO GD_SHP
	VALUES (10485784,
	1936,
	1504,
	2128,
	1568);
INSERT INTO GD_GE
	VALUES (10485819,
	10485767,
	10485765,
	21);
INSERT INTO GD_SHP
	VALUES (10485819,
	2016,
	1712,
	2208,
	1776);
