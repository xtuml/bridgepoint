-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (385357,
	'select',
	'This domain tests the select statement:
 - Simple expressions, Complex expressions (with and without parenthesis), and complex expressions using multiple ''selected'' statements.
   - with and without ''where''.
   - using strings, bridge return values, attributes, local variables, etc. with the where clause.',
	0,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	385357,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	385357,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	385357,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	385357,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	385357,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	385357,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	385357,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	385357,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	385357,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	385357,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	385357,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	385357,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	385357,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	385357,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	385357,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	385357,
	'inst_ref<Timer>',
	'');
INSERT INTO S_EE
	VALUES (524290,
	'Logging',
	'',
	'LOG',
	385357);
INSERT INTO S_BRG
	VALUES (524304,
	524290,
	'LogSuccess',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524311,
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
	VALUES (524312,
	524305,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524306,
	524290,
	'LogInfo',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524313,
	524306,
	'message',
	524293,
	0);
INSERT INTO S_EE
	VALUES (524291,
	'Time',
	'',
	'TIM',
	385357);
INSERT INTO S_BRG
	VALUES (524307,
	524291,
	'current_date',
	'',
	1,
	524302,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524308,
	524291,
	'create_date',
	'',
	1,
	524302,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524314,
	524308,
	'second',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524315,
	524308,
	'minute',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524316,
	524308,
	'hour',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524317,
	524308,
	'day',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524318,
	524308,
	'month',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524319,
	524308,
	'year',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524309,
	524291,
	'get_second',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524320,
	524309,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524310,
	524291,
	'get_minute',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524321,
	524310,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524311,
	524291,
	'get_hour',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524322,
	524311,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524312,
	524291,
	'get_day',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524323,
	524312,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524313,
	524291,
	'get_month',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524324,
	524313,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524314,
	524291,
	'get_year',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524325,
	524314,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524315,
	524291,
	'current_clock',
	'',
	1,
	524303,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524316,
	524291,
	'timer_start',
	'',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524326,
	524316,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524327,
	524316,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524317,
	524291,
	'timer_start_recurring',
	'',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524328,
	524317,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524329,
	524317,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524318,
	524291,
	'timer_remaining_time',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524330,
	524318,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BRG
	VALUES (524319,
	524291,
	'timer_reset_time',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524331,
	524319,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524332,
	524319,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524320,
	524291,
	'timer_add_time',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524333,
	524320,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524334,
	524320,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524321,
	524291,
	'timer_cancel',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524335,
	524321,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_EE
	VALUES (524293,
	'Architecture',
	'',
	'ARCH',
	385357);
INSERT INTO S_BRG
	VALUES (524322,
	524293,
	'shutdown',
	'',
	0,
	524289,
	'control stop;',
	1);
INSERT INTO S_EE
	VALUES (524294,
	'Test',
	'',
	'T',
	385357);
INSERT INTO S_BRG
	VALUES (524323,
	524294,
	'two',
	'',
	0,
	524291,
	'// ACTION_SPECIFICATION: TRUE

return 2;
',
	1);
INSERT INTO S_BRG
	VALUES (524324,
	524294,
	'georgette',
	'',
	0,
	524293,
	'// ACTION_SPECIFICATION: TRUE

return "georgette";
',
	1);
INSERT INTO GD_MD
	VALUES (524289,
	1,
	385357,
	1,
	1,
	0,
	1,
	1,
	0,
	12,
	1592,
	4085,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (524319,
	524289,
	3670023,
	11);
INSERT INTO GD_SHP
	VALUES (524319,
	2128,
	1472,
	2288,
	1584);
INSERT INTO GD_GE
	VALUES (524301,
	524289,
	524290,
	12);
INSERT INTO GD_SHP
	VALUES (524301,
	1696,
	1472,
	2080,
	1584);
INSERT INTO GD_GE
	VALUES (524320,
	524289,
	524291,
	12);
INSERT INTO GD_SHP
	VALUES (524320,
	1904,
	1616,
	2080,
	1728);
INSERT INTO GD_GE
	VALUES (524322,
	524289,
	524293,
	12);
INSERT INTO GD_SHP
	VALUES (524322,
	1696,
	1616,
	1856,
	1728);
INSERT INTO GD_GE
	VALUES (524323,
	524289,
	524294,
	12);
INSERT INTO GD_SHP
	VALUES (524323,
	2128,
	1616,
	2288,
	1728);
INSERT INTO S_SS
	VALUES (3670023,
	'select',
	'',
	'',
	1,
	385357,
	3670023);
INSERT INTO O_OBJ
	VALUES (3670017,
	'Object A',
	2,
	'OA',
	'',
	3670023);
INSERT INTO O_NBATTR
	VALUES (3670017,
	3670017);
INSERT INTO O_BATTR
	VALUES (3670017,
	3670017);
INSERT INTO O_ATTR
	VALUES (3670017,
	3670017,
	0,
	'oa_id',
	'',
	'',
	'oa_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (3670018,
	3670017);
INSERT INTO O_BATTR
	VALUES (3670018,
	3670017);
INSERT INTO O_ATTR
	VALUES (3670018,
	3670017,
	3670017,
	'name',
	'MAX_LENGTH: 20',
	'',
	'name',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (3670019,
	3670017);
INSERT INTO O_BATTR
	VALUES (3670019,
	3670017);
INSERT INTO O_ATTR
	VALUES (3670019,
	3670017,
	3670018,
	'age',
	'',
	'',
	'age',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	3670017);
INSERT INTO O_OIDA
	VALUES (3670017,
	3670017,
	0);
INSERT INTO O_OBJ
	VALUES (3670018,
	'Object B',
	3,
	'OB',
	'',
	3670023);
INSERT INTO O_NBATTR
	VALUES (3670020,
	3670018);
INSERT INTO O_BATTR
	VALUES (3670020,
	3670018);
INSERT INTO O_ATTR
	VALUES (3670020,
	3670018,
	0,
	'ob_id',
	'',
	'',
	'ob_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (3670021,
	3670018);
INSERT INTO O_BATTR
	VALUES (3670021,
	3670018);
INSERT INTO O_ATTR
	VALUES (3670021,
	3670018,
	3670020,
	'x',
	'',
	'',
	'x',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3670022,
	3670018);
INSERT INTO O_BATTR
	VALUES (3670022,
	3670018);
INSERT INTO O_ATTR
	VALUES (3670022,
	3670018,
	3670021,
	'y',
	'',
	'',
	'y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3670023,
	3670018);
INSERT INTO O_BATTR
	VALUES (3670023,
	3670018);
INSERT INTO O_ATTR
	VALUES (3670023,
	3670018,
	3670022,
	'z',
	'',
	'',
	'z',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3670024,
	3670018);
INSERT INTO O_BATTR
	VALUES (3670024,
	3670018);
INSERT INTO O_ATTR
	VALUES (3670024,
	3670018,
	3670023,
	'bool_t',
	'',
	'',
	'bool_t',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (3670025,
	3670018);
INSERT INTO O_BATTR
	VALUES (3670025,
	3670018);
INSERT INTO O_ATTR
	VALUES (3670025,
	3670018,
	3670024,
	'bool_f',
	'',
	'',
	'bool_f',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (3670026,
	3670018);
INSERT INTO O_BATTR
	VALUES (3670026,
	3670018);
INSERT INTO O_ATTR
	VALUES (3670026,
	3670018,
	3670025,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_NBATTR
	VALUES (3670027,
	3670018);
INSERT INTO O_BATTR
	VALUES (3670027,
	3670018);
INSERT INTO O_ATTR
	VALUES (3670027,
	3670018,
	3670026,
	'date',
	'',
	'',
	'date',
	0,
	524302);
INSERT INTO O_NBATTR
	VALUES (3670028,
	3670018);
INSERT INTO O_BATTR
	VALUES (3670028,
	3670018);
INSERT INTO O_ATTR
	VALUES (3670028,
	3670018,
	3670027,
	'timest',
	'',
	'',
	'timest',
	0,
	524303);
INSERT INTO O_NBATTR
	VALUES (3670029,
	3670018);
INSERT INTO O_BATTR
	VALUES (3670029,
	3670018);
INSERT INTO O_ATTR
	VALUES (3670029,
	3670018,
	3670028,
	'real',
	'',
	'',
	'real',
	0,
	524292);
INSERT INTO O_ID
	VALUES (0,
	3670018);
INSERT INTO O_OIDA
	VALUES (3670020,
	3670018,
	0);
INSERT INTO SM_ISM
	VALUES (1048578,
	3670018);
INSERT INTO SM_SM
	VALUES (1048578,
	'',
	2);
INSERT INTO SM_MOORE
	VALUES (1048578);
INSERT INTO SM_LEVT
	VALUES (1048577,
	1048578,
	0);
INSERT INTO SM_SEVT
	VALUES (1048577,
	1048578,
	0);
INSERT INTO SM_EVT
	VALUES (1048577,
	1048578,
	0,
	1,
	'dummy event',
	0,
	'',
	'OB1',
	'');
INSERT INTO SM_STATE
	VALUES (1048577,
	1048578,
	0,
	'Exists',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (1048577,
	1048577,
	1048578,
	0);
INSERT INTO SM_MOAH
	VALUES (1048577,
	1048578,
	1048577);
INSERT INTO SM_AH
	VALUES (1048577,
	1048578);
INSERT INTO SM_ACT
	VALUES (1048577,
	1048578,
	1,
	'',
	'');
INSERT INTO SM_NSTXN
	VALUES (1048577,
	1048578,
	1048577,
	1048577,
	0);
INSERT INTO SM_TXN
	VALUES (1048577,
	1048578,
	1048577,
	0);
INSERT INTO GD_MD
	VALUES (1048577,
	8,
	1048578,
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
	VALUES (1048578,
	1048577,
	1048577,
	41);
INSERT INTO GD_SHP
	VALUES (1048578,
	1808,
	1360,
	1952,
	1424);
INSERT INTO GD_GE
	VALUES (1048579,
	1048577,
	1048577,
	42);
INSERT INTO GD_CON
	VALUES (1048579,
	1048578,
	1048578,
	0);
INSERT INTO GD_CTXT
	VALUES (1048579,
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
	VALUES (1048580,
	1048579,
	1952,
	1408,
	2016,
	1408,
	0);
INSERT INTO GD_LS
	VALUES (1048581,
	1048579,
	2016,
	1408,
	2016,
	1264,
	1048580);
INSERT INTO GD_LS
	VALUES (1048582,
	1048579,
	2016,
	1264,
	1904,
	1264,
	1048581);
INSERT INTO GD_LS
	VALUES (1048583,
	1048579,
	1904,
	1264,
	1904,
	1360,
	1048582);
INSERT INTO O_OBJ
	VALUES (3670019,
	'Driver',
	1,
	'DR',
	'',
	3670023);
INSERT INTO O_TFR
	VALUES (3670017,
	3670019,
	'one',
	'',
	524291,
	0,
	'// ACTION_SPECIFICATION: TRUE

return 1;
',
	1);
INSERT INTO O_TFR
	VALUES (3670018,
	3670019,
	'bob',
	'',
	524293,
	0,
	'// ACTION_SPECIFICATION: TRUE

return "bob";
',
	1);
INSERT INTO O_NBATTR
	VALUES (3670030,
	3670019);
INSERT INTO O_BATTR
	VALUES (3670030,
	3670019);
INSERT INTO O_ATTR
	VALUES (3670030,
	3670019,
	0,
	'driver_id',
	'',
	'',
	'driver_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (3670031,
	3670019);
INSERT INTO O_BATTR
	VALUES (3670031,
	3670019);
INSERT INTO O_ATTR
	VALUES (3670031,
	3670019,
	3670030,
	'testNumber',
	'',
	'',
	'testNumber',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3670033,
	3670019);
INSERT INTO O_BATTR
	VALUES (3670033,
	3670019);
INSERT INTO O_ATTR
	VALUES (3670033,
	3670019,
	3670031,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	3670019);
INSERT INTO O_OIDA
	VALUES (3670030,
	3670019,
	0);
INSERT INTO SM_ISM
	VALUES (1572867,
	3670019);
INSERT INTO SM_SM
	VALUES (1572867,
	'',
	3);
INSERT INTO SM_MOORE
	VALUES (1572867);
INSERT INTO SM_LEVT
	VALUES (1572865,
	1572867,
	0);
INSERT INTO SM_SEVT
	VALUES (1572865,
	1572867,
	0);
INSERT INTO SM_EVT
	VALUES (1572865,
	1572867,
	0,
	2,
	'next test',
	0,
	'',
	'DR2',
	'');
INSERT INTO SM_LEVT
	VALUES (1572866,
	1572867,
	0);
INSERT INTO SM_SEVT
	VALUES (1572866,
	1572867,
	0);
INSERT INTO SM_EVT
	VALUES (1572866,
	1572867,
	0,
	1,
	'start test',
	0,
	'',
	'DR1',
	'');
INSERT INTO SM_STATE
	VALUES (1572865,
	1572867,
	0,
	'Simple Expression',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (1572865,
	1572865,
	1572867,
	0);
INSERT INTO SM_EIGN
	VALUES (1572865,
	1572866,
	1572867,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (1572865,
	1572866,
	1572867,
	0);
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
	'//============================================================
// Instance creation and configuration
//============================================================

create object instance a of OA;
a.name = "bob";
a.age = 3;
create object instance a of OA;
a.name = "bobby";
a.age = 4;
create object instance a of OA;
a.name = "bo";
a.age = 5;
create object instance a of OA;
a.name = "fred";
assign a.age = 6;
create object instance a of OA;
a.name = "bob";
a.age=7;

// Create many instances of a with a range of ages
x = 1;
while (x < 30)
   create object instance a of OA;
   a.age = x;
   x = x + 1;
end while;

// Create some redundant ages
create object instance a of OA;
a.age = 12;
create object instance a of OA;
a.age = 14;
create object instance a of OA;
a.age = 18;

//============================================================
// Begin test
//============================================================

// verify select without where works...
select any a from instances of OA;
if ( not_empty a )
  LOG::LogSuccess(message:"Simple Expression 1");
else
  LOG::LogFailure(message:"Simple Expression 1");
end if;

// Use of selected in a simple expression:
select any a from instances of OA where selected.name == "bob";
if (a.name == "bob")
  LOG::LogSuccess(message:"Simple Expression 2 ");
else
  LOG::LogFailure(message:"Simple Expression 2");
end if;

select any a from instances of OA where selected.name != "bob";
if ( a.name != "bob" )
  LOG::LogSuccess(message:"Simple Expression 3");
else
  LOG::LogFailure(message:"Simple Expression 3");
end if;

local_str = "bob";
select any a from instances of OA where selected.name != local_str;
if ( a.name != local_str )
  LOG::LogSuccess(message:"Simple Expression 4");
else
  LOG::LogFailure(message:"Simple Expression 4");
end if;

select any a from instances of OA where selected.name != DR::bob();
if ( a.name != DR::bob() )
  LOG::LogSuccess(message:"Simple Expression 5");
else
  LOG::LogFailure(message:"Simple Expression 5");
end if;

// Use of selected in a simple expression:
select any a from instances of OA where selected.name == "georgette";
if (cardinality a == 0)
  LOG::LogSuccess(message:"Simple Expression 6");
else
  LOG::LogFailure(message:"Simple Expression 6");
end if;


select any a from instances of OA where selected.name == T::georgette();
if (cardinality a == 0)
  LOG::LogSuccess(message:"Simple Expression 7");
else
  LOG::LogFailure(message:"Simple Expression 7");
end if;

select any a from instances of OA where "bob" == selected.name;
if (a.name == "bob")
  LOG::LogSuccess(message:"Simple Expression 8");
else
  LOG::LogFailure(message:"Simple Expression 8");
end if;

select any a from instances of OA where "bob" != selected.name;
if ( a.name != "bob" )
  LOG::LogSuccess(message:"Simple Expression 9");
else
  LOG::LogFailure(message:"Simple Expression 9");
end if;
 
select any a from instances of OA where "bobbie" == selected.name;
if (cardinality a == 0)
  LOG::LogSuccess(message:"Simple Expression 10");
else
  LOG::LogFailure(message:"Simple Expression 10");
end if;

select many as from instances of OA where selected.name == "bob";
pass = TRUE;
for each a in as
  if (a.name != "bob")
    pass = FALSE;
  end if;
end for;

if (pass)
  LOG::LogSuccess(message:"Simple Expression 11");
else
  LOG::LogFailure(message:"Simple Expression 11");
end if;

select many as from instances of OA where selected.name != "bob";
pass = TRUE;
for each a in as
  if (a.name == "bob")
    pass = FALSE;
  end if;
end for;
 
if (pass)
  LOG::LogSuccess(message:"Simple Expression 12");
else
  LOG::LogFailure(message:"Simple Expression 12");
end if;
 
select many as from instances of OA where selected.name == "boob";
if (cardinality as < 1)
  LOG::LogSuccess(message:"Simple Expression 13");
else
  LOG::LogFailure(message:"Simple Expression 13");
end if;

select many as from instances of OA where selected.age < 18;
pass = TRUE;
for each a in as
  if (a.age >= 18)
    pass = FALSE;
  end if;
end for;

if (pass)
  LOG::LogSuccess(message:"Simple Expression 14");
else
  LOG::LogFailure(message:"Simple Expression 14");
end if;

select many as from instances of OA where selected.age < 1;
if (cardinality as <= 0)
  LOG::LogSuccess(message:"Simple Expression 15");
else
  LOG::LogFailure(message:"Simple Expression 15");
end if;


//============================================================
// Test complete
//============================================================

//============================================================
//  Deleting all created instances
//============================================================

select many as from instances of OA;
for each a in as
  delete object instance a;
end for;

generate DR2:''next test'' () to self;
',
	'');
INSERT INTO SM_STATE
	VALUES (1572866,
	1572867,
	0,
	'Parenthesis',
	2,
	0);
INSERT INTO SM_SEME
	VALUES (1572866,
	1572865,
	1572867,
	0);
INSERT INTO SM_EIGN
	VALUES (1572866,
	1572866,
	1572867,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (1572866,
	1572866,
	1572867,
	0);
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
	'//============================================================
// Instance creation and configuration
//============================================================

create object instance a of OA;
a.name = "bob";
a.age = 21;
create object instance a of OA;
a.name = "bobby";
a.age = 21;
create object instance a of OA;
a.name = "bo";
a.age = 21;
create object instance a of OA;
a.name = "fred";
a.age = 21;
create object instance a of OA;
a.name = "bob";
a.age = 21;

// Create many instances of a with a range of ages
x = 1;
while (x < 30)
   create object instance a of OA;
   a.age = x;
   x = x + 1;
end while;

// Create some redundant ages
create object instance a of OA;
a.age = 12;
create object instance a of OA;
a.age = 14;
create object instance a of OA;
a.age = 18;


//============================================================
// Begin test
//============================================================

select any a from instances of OA where selected.name == "bob";
if (a.name == "bob")
  LOG::LogSuccess(message:"Parenthesis 1");
else
  LOG::LogFailure(message:"Parenthesis 1");
end if;


select any a from instances of OA where selected.name == "delores";
if (cardinality a == 0)
  LOG::LogSuccess(message:"Parenthesis 2");
else
  LOG::LogFailure(message:"Parenthesis 2");
end if;

select any a from instances of OA where selected.name == "bob" and
                                                        selected.age == 21;
if ((a.name == "bob") and (a.age == 21))
  LOG::LogSuccess(message:"Parenthesis 3");
else
  LOG::LogFailure(message:"Parenthesis 3");
end if;

select any a from instances of OA where selected.name == "delores" and
                                                        selected.age == 19;
if (cardinality a <= 1)
  LOG::LogSuccess(message:"Parenthesis 4");
else
  LOG::LogFailure(message:"Parenthesis 4");
end if;

select any a from instances of OA where (selected.name == "bob");
if (a.name == "bob")
  LOG::LogSuccess(message:"Parenthesis 5");
else
  LOG::LogFailure(message:"Parenthesis 5");
end if;

select any a from instances of OA where (selected.name == "freddy");
if (cardinality a < 1)
  LOG::LogSuccess(message:"Parenthesis 6");
else
  LOG::LogFailure(message:"Parenthesis 6");
end if;


select any a from instances of OA where (selected.name == "bob") and
                                                        (selected.age == 21);
if ((a.name == "bob") and (a.age == 21))
  LOG::LogSuccess(message:"Parenthesis 7");
else
  LOG::LogFailure(message:"Parenthesis 7");
end if;

select any a from instances of OA where (selected.name == "bob") and
                                                        (selected.age == 89);
if (cardinality a == 0)
  LOG::LogSuccess(message:"Parenthesis 8");
else
  LOG::LogFailure(message:"Parenthesis 8");
end if;


//============================================================
// Test complete
//============================================================

//============================================================
//  Deleting all created instances
//============================================================

select many as from instances of OA;
for each a in as
  delete object instance a;
end for;

generate DR2:''next test'' () to self;
',
	'');
INSERT INTO SM_STATE
	VALUES (1572867,
	1572867,
	0,
	'Compound Expression',
	3,
	0);
INSERT INTO SM_SEME
	VALUES (1572867,
	1572865,
	1572867,
	0);
INSERT INTO SM_EIGN
	VALUES (1572867,
	1572866,
	1572867,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (1572867,
	1572866,
	1572867,
	0);
INSERT INTO SM_MOAH
	VALUES (1572867,
	1572867,
	1572867);
INSERT INTO SM_AH
	VALUES (1572867,
	1572867);
INSERT INTO SM_ACT
	VALUES (1572867,
	1572867,
	1,
	'
//============================================================
// Instance creation and configuration
//============================================================

// Create extra objects for tests using A
create object instance a of OA;
a.name = "bobby";
a.age = 22;
create object instance a of OA;
a.name = "bo";
a.age = 23;
create object instance a of OA;
a.name = "fred";
a.age = 24;
create object instance a of OA;
a.name = "bob";
a.age = 25;

// Create many instances of a with a range of ages
x = 1;
while (x < 30)
   create object instance a of OA;
   a.age = x;
   x = x + 1;
end while;

// Create some redundant ages
create object instance a of OA;
a.age = 12;
create object instance a of OA;
a.age = 14;
create object instance a of OA;
a.age = 18;

// Instances of B
x = 1;
while (x < 30)
   create object instance b of OB;
   b.x = x;
   b.y = 2;
   b.z = x;
   b.bool_t = true;
   b.bool_f = false;
   x = x + 1;
end while;



//============================================================
// Begin test
//============================================================


// Use of selected in a compound expression:

// Create object for this test
create object instance a of OA;
a.name = "bob";
a.age = 21;

select any a from instances of OA where (selected.name == "bob") and
                                                        (selected.age >= 18);
if ((a.name == "bob") and (a.age >= 18))
  LOG::LogSuccess(message:"Compound Expression 1");
else
  LOG::LogFailure(message:"Compound Expression 1");
end if;

select any a from instances of OA where (selected.name == DR::bob()) and
                                                        (selected.age >= 18);
if ((a.name == DR::bob()) and (a.age >= 18))
  LOG::LogSuccess(message:"Compound Expression 2");
else
  LOG::LogFailure(message:"Compound Expression 2");
end if;

// Use of selected in a compound expression - nothing should be found

select any a from instances of OA where (selected.name == "noName") and
				        (selected.age >= 55);
// nothing should be selected
if (cardinality a == 0)
  LOG::LogSuccess(message:"Compound Expression 3");
else
  LOG::LogFailure(message:"Compound Expression 3");
end if;

select many as from instances of OA where (selected.age > 12) and
                                                            (selected.age < 20);
for each a in as
  if ((a.age > 12) and (a.age < 20))
  LOG::LogSuccess(message:"Compound Expression 4");
  else
  LOG::LogFailure(message:"Compound Expression 4");
  end if;
end for;

select many as from instances of OA where (selected.age > 12) and
                                                            (selected.age < 12);
// Nothing should be selected 
if (cardinality as == 0)
  LOG::LogSuccess(message:"Compound Expression 5");
else
  LOG::LogFailure(message:"Compound Expression 5");
end if;

// Create object for this test
create object instance b of OB;
b.x = 1;
b.y = 1;

select any b from instances of OB where (selected.x == 1) and
                                                        (selected.y != T::two());
if ((b.x == 1) and (b.y != 2))
  LOG::LogSuccess(message:"Compound Expression 6");
else
  LOG::LogFailure(message:"Compound Expression 6");
end if;

select any b from instances of OB where (selected.x != DR::one()) and
                                                        (selected.y != 2);
// Nothing should be selected
if (cardinality b == 0)
  LOG::LogSuccess(message:"Compound Expression 7");
else
  LOG::LogFailure(message:"Compound Expression 7");
end if;


create object instance b of OB;
b.x = 2;
b.y = 1;

select any b from instances of OB where ((selected.x + selected.y) < 5);
if ((b.x + b.y) < 5)
  LOG::LogSuccess(message:"Compound Expression 8");
else
  LOG::LogFailure(message:"Compound Expression 8");
end if;

select any b from instances of OB where ((selected.x + selected.y) < 2);
// Nothing should be selected
if (cardinality b == 0)
  LOG::LogSuccess(message:"Compound Expression 9");
else
  LOG::LogFailure(message:"Compound Expression 9");
end if;


create object instance b of OB;
b.x = 2;
b.y = 4;
b.z = 1;

select any b from instances of OB where (selected.x < 5 and
                                          selected.y > 3 or
                                          selected.z == 1);
if ((b.x < 5) and (b.y > 3) or (b.z == 1))
  LOG::LogSuccess(message:"Compound Expression 10");
else
  LOG::LogFailure(message:"Compound Expression 10");
end if;

create object instance b of OB;
b.x = 200;
b.y = 40;
b.z = 123;

select any b from instances of OB where (selected.x > 199 and
                                          selected.y < 39 or
                                          selected.z == 122);
// Nothing should be selected
if (cardinality b == 0)
  LOG::LogSuccess(message:"Compound Expression 11");
else
  LOG::LogFailure(message:"Compound Expression 11");
end if;

create object instance b of OB;
b.x = 2;
b.y = 3;
b.z = 4;

select any b from instances of OB where (selected.x + selected.y +
                                          selected.z) == 9;
if ((b.x + b.y + b.z) == 9)
  LOG::LogSuccess(message:"Compound Expression 12");
else
  LOG::LogFailure(message:"Compound Expression 12");
end if;

//============================================================
// Test complete
//============================================================

//============================================================
//  Deleting all created instances
//============================================================

select many as from instances of OA;
for each a in as
  delete object instance a;
end for;

select many bs from instances of OB;
for each b in bs
  delete object instance b;
end for;

generate DR2:''next test'' () to self;
',
	'');
INSERT INTO SM_STATE
	VALUES (1572868,
	1572867,
	0,
	'Multiple Use of Selected',
	6,
	0);
INSERT INTO SM_SEME
	VALUES (1572868,
	1572865,
	1572867,
	0);
INSERT INTO SM_EIGN
	VALUES (1572868,
	1572866,
	1572867,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (1572868,
	1572866,
	1572867,
	0);
INSERT INTO SM_MOAH
	VALUES (1572868,
	1572867,
	1572868);
INSERT INTO SM_AH
	VALUES (1572868,
	1572867);
INSERT INTO SM_ACT
	VALUES (1572868,
	1572867,
	1,
	'
//============================================================
// Instance creation and configuration
//============================================================

// Create many instances of b with b.x == b.y
x = 1;
while (x <= 10)
   create object instance b of OB;
   b.x = x;
   b.y = x;
   x = x + 1;
end while;

// Create many instances of b with b.x != b.y
x = 1;
while (x <= 10)
   create object instance b of OB;
   b.x = x;
   b.y = x + 1;
   x = x + 1;
end while;


//============================================================
// Begin test
//============================================================

// Using selected multiple times in where expression

select many bs from instances of OB where (selected.x != selected.y);

pass = TRUE;
for each b in bs
  if (b.x == b.y)
    pass = FALSE;
  end if;
end for;

if (pass)
  LOG::LogSuccess(message:"Multiple Use of Selected 1");
else
  LOG::LogFailure(message:"Multiple Use of Selected 1");
end if;

if (cardinality bs != 10)
  pass = FALSE;
end if;

if (pass)
  LOG::LogSuccess(message:"Multiple Use of Selected 2");
else
  LOG::LogFailure(message:"Multiple Use of Selected 2");
end if;


// Using selected multiple times in where expression, no elements 
// should pass the where clause

select many bs from instances of OB where (selected.x > selected.y);

if (cardinality bs == 0)
  LOG::LogSuccess(message:"Multiple Use of Selected 3");
else
  LOG::LogFailure(message:"Multiple Use of Selected 3");
end if;


//============================================================
// Test complete
//============================================================

//============================================================
//  Deleting all created instances
//============================================================

select many bs from instances of OB;
for each b in bs
  delete object instance b;
end for;

generate DR2:''next test'' () to self;

',
	'');
INSERT INTO SM_STATE
	VALUES (1572869,
	1572867,
	0,
	'Selected Alone',
	7,
	1);
INSERT INTO SM_EIGN
	VALUES (1572869,
	1572865,
	1572867,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (1572869,
	1572865,
	1572867,
	0);
INSERT INTO SM_EIGN
	VALUES (1572869,
	1572866,
	1572867,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (1572869,
	1572866,
	1572867,
	0);
INSERT INTO SM_MOAH
	VALUES (1572869,
	1572867,
	1572869);
INSERT INTO SM_AH
	VALUES (1572869,
	1572867);
INSERT INTO SM_ACT
	VALUES (1572869,
	1572867,
	1,
	'
//============================================================
// Instance creation and configuration
//============================================================

x = 1;
while (x <= 10)
   create object instance b of OB;
   b.bool_t = TRUE;
   b.bool_f = FALSE;
   x = x + 1;
end while;


//============================================================
// Begin test
//============================================================


select any b from instances of OB where selected.bool_t;
if (b.bool_t == TRUE)
  LOG::LogSuccess(message:"Selected Alone 1");
else
  LOG::LogFailure(message:"Selected Alone 1");
end if;

select many bs from instances of OB where selected.bool_t;
pass = TRUE;
for each b in bs
  if (b.bool_t != TRUE)
    pass = FALSE;
  end if;
end for;

if (pass)
  LOG::LogSuccess(message:"Selected Alone 2");
else
  LOG::LogFailure(message:"Selected Alone 2");
end if;

select many bs from instances of OB where selected.bool_f;
if (cardinality bs == 0)
  LOG::LogSuccess(message:"Selected Alone 3");
else
  LOG::LogFailure(message:"Selected Alone 3");
end if;


//============================================================
// Test complete
//============================================================

//============================================================
//  Deleting all created instances
//============================================================

select many bs from instances of OB;
for each b in bs
  delete object instance b;
end for;

LOG::LogInfo(message:"test complete");
bridge ARCH::shutdown();',
	'');
INSERT INTO SM_NSTXN
	VALUES (1572865,
	1572867,
	1572865,
	1572865,
	0);
INSERT INTO SM_TXN
	VALUES (1572865,
	1572867,
	1572866,
	0);
INSERT INTO SM_NSTXN
	VALUES (1572866,
	1572867,
	1572868,
	1572865,
	0);
INSERT INTO SM_TXN
	VALUES (1572866,
	1572867,
	1572869,
	0);
INSERT INTO SM_NSTXN
	VALUES (1572867,
	1572867,
	1572867,
	1572865,
	0);
INSERT INTO SM_TXN
	VALUES (1572867,
	1572867,
	1572868,
	0);
INSERT INTO SM_CRTXN
	VALUES (1572868,
	1572867,
	1572866,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572868,
	1572867,
	1572865,
	0);
INSERT INTO SM_NSTXN
	VALUES (1572871,
	1572867,
	1572866,
	1572865,
	0);
INSERT INTO SM_TXN
	VALUES (1572871,
	1572867,
	1572867,
	0);
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
	1482,
	4199,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (1572866,
	1572865,
	1572865,
	41);
INSERT INTO GD_SHP
	VALUES (1572866,
	1728,
	1264,
	1920,
	1344);
INSERT INTO GD_GE
	VALUES (1572867,
	1572865,
	1572866,
	41);
INSERT INTO GD_SHP
	VALUES (1572867,
	1728,
	1392,
	1920,
	1472);
INSERT INTO GD_GE
	VALUES (1572868,
	1572865,
	1572867,
	41);
INSERT INTO GD_SHP
	VALUES (1572868,
	1728,
	1696,
	1920,
	1776);
INSERT INTO GD_GE
	VALUES (1572869,
	1572865,
	1572868,
	41);
INSERT INTO GD_SHP
	VALUES (1572869,
	2032,
	1392,
	2224,
	1472);
INSERT INTO GD_GE
	VALUES (1572870,
	1572865,
	1572869,
	41);
INSERT INTO GD_SHP
	VALUES (1572870,
	2032,
	1520,
	2224,
	1600);
INSERT INTO GD_GE
	VALUES (1572872,
	1572865,
	1572865,
	42);
INSERT INTO GD_CON
	VALUES (1572872,
	1572866,
	1572867,
	0);
INSERT INTO GD_CTXT
	VALUES (1572872,
	0,
	0,
	0,
	0,
	0,
	0,
	1828,
	1356,
	1948,
	1390,
	20,
	3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572873,
	1572872,
	1824,
	1344,
	1824,
	1392,
	0);
INSERT INTO GD_GE
	VALUES (1572874,
	1572865,
	1572866,
	42);
INSERT INTO GD_CON
	VALUES (1572874,
	1572869,
	1572870,
	0);
INSERT INTO GD_CTXT
	VALUES (1572874,
	0,
	0,
	0,
	0,
	0,
	0,
	2112,
	1477,
	2301,
	1518,
	0,
	-4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572875,
	1572874,
	2128,
	1472,
	2128,
	1520,
	0);
INSERT INTO GD_GE
	VALUES (1572876,
	1572865,
	1572867,
	42);
INSERT INTO GD_CON
	VALUES (1572876,
	1572868,
	1572869,
	0);
INSERT INTO GD_CTXT
	VALUES (1572876,
	0,
	0,
	0,
	0,
	0,
	0,
	2016,
	1297,
	2142,
	1330,
	48,
	-152,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572877,
	1572876,
	1920,
	1728,
	1984,
	1728,
	0);
INSERT INTO GD_LS
	VALUES (1572878,
	1572876,
	1984,
	1728,
	1984,
	1328,
	1572877);
INSERT INTO GD_LS
	VALUES (1572879,
	1572876,
	1984,
	1328,
	2112,
	1328,
	1572878);
INSERT INTO GD_LS
	VALUES (1572880,
	1572876,
	2112,
	1328,
	2112,
	1392,
	1572879);
INSERT INTO GD_GE
	VALUES (1572881,
	1572865,
	1572868,
	42);
INSERT INTO GD_CON
	VALUES (1572881,
	1572866,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (1572881,
	0,
	0,
	0,
	0,
	0,
	0,
	1796,
	1214,
	1973,
	1252,
	-12,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572882,
	1572881,
	1824,
	1264,
	1824,
	1200,
	0);
INSERT INTO GD_GE
	VALUES (1572886,
	1572865,
	1572871,
	42);
INSERT INTO GD_CON
	VALUES (1572886,
	1572867,
	1572868,
	0);
INSERT INTO GD_CTXT
	VALUES (1572886,
	0,
	0,
	0,
	0,
	0,
	0,
	1831,
	1564,
	1935,
	1596,
	23,
	-5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572887,
	1572886,
	1824,
	1472,
	1824,
	1696,
	0);
INSERT INTO O_OBJ
	VALUES (3670020,
	'select init',
	4,
	'INIT',
	'',
	3670023);
INSERT INTO O_NBATTR
	VALUES (3670034,
	3670020);
INSERT INTO O_BATTR
	VALUES (3670034,
	3670020);
INSERT INTO O_ATTR
	VALUES (3670034,
	3670020,
	0,
	'init_id',
	'',
	'',
	'init_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (3670035,
	3670020);
INSERT INTO O_BATTR
	VALUES (3670035,
	3670020);
INSERT INTO O_ATTR
	VALUES (3670035,
	3670020,
	3670034,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	3670020);
INSERT INTO O_OIDA
	VALUES (3670034,
	3670020,
	0);
INSERT INTO SM_ISM
	VALUES (2097156,
	3670020);
INSERT INTO SM_SM
	VALUES (2097156,
	'',
	4);
INSERT INTO SM_MOORE
	VALUES (2097156);
INSERT INTO SM_LEVT
	VALUES (2097153,
	2097156,
	0);
INSERT INTO SM_SEVT
	VALUES (2097153,
	2097156,
	0);
INSERT INTO SM_EVT
	VALUES (2097153,
	2097156,
	0,
	1,
	'init',
	0,
	'',
	'INIT1',
	'');
INSERT INTO SM_STATE
	VALUES (2097153,
	2097156,
	0,
	'Initialize System',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (2097153,
	2097153,
	2097156,
	0);
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
	'generate DR1:''start test'' to DR creator;',
	'');
INSERT INTO SM_NSTXN
	VALUES (2097153,
	2097156,
	2097153,
	2097153,
	0);
INSERT INTO SM_TXN
	VALUES (2097153,
	2097156,
	2097153,
	0);
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
	1856,
	1344,
	2064,
	1456);
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
	2080,
	1264,
	2202,
	1300,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097156,
	2097155,
	2064,
	1392,
	2112,
	1392,
	0);
INSERT INTO GD_LS
	VALUES (2097157,
	2097155,
	2112,
	1392,
	2112,
	1296,
	2097156);
INSERT INTO GD_LS
	VALUES (2097158,
	2097155,
	2112,
	1296,
	2000,
	1296,
	2097157);
INSERT INTO GD_LS
	VALUES (2097159,
	2097155,
	2000,
	1296,
	2000,
	1344,
	2097158);
INSERT INTO GD_MD
	VALUES (3670030,
	5,
	3670023,
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
	VALUES (3670033,
	3670030,
	3670017,
	21);
INSERT INTO GD_SHP
	VALUES (3670033,
	1664,
	1424,
	1856,
	1536);
INSERT INTO GD_GE
	VALUES (3670034,
	3670030,
	3670018,
	21);
INSERT INTO GD_SHP
	VALUES (3670034,
	1904,
	1424,
	2128,
	1632);
INSERT INTO GD_GE
	VALUES (3670035,
	3670030,
	3670019,
	21);
INSERT INTO GD_SHP
	VALUES (3670035,
	1904,
	1264,
	2128,
	1392);
INSERT INTO GD_GE
	VALUES (3670036,
	3670030,
	3670020,
	21);
INSERT INTO GD_SHP
	VALUES (3670036,
	1664,
	1264,
	1872,
	1392);
