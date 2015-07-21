-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (13105528,
	'ex3',
	'This test deals with expressions using boolean, integer, real, and string types.  It tests the assignment operator (=) and checks that default values are assigned correctly.  It also tests >, <, >=, <=, ==, and != using integers, reals, and strings.  It does these tests with both an attribute of these types and a user defined type attribute.',
	0,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	13105528,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	13105528,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	13105528,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	13105528,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	13105528,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	13105528,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	13105528,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	13105528,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	13105528,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	13105528,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	13105528,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	13105528,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	13105528,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	13105528,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	13105528,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	13105528,
	'inst_ref<Timer>',
	'');
INSERT INTO S_UDT
	VALUES (524305,
	524294,
	0);
INSERT INTO S_DT
	VALUES (524305,
	13105528,
	'arbitrary_id',
	'Arbitrary ID with core data type of unique_id.');
INSERT INTO S_UDT
	VALUES (524306,
	524291,
	0);
INSERT INTO S_DT
	VALUES (524306,
	13105528,
	'my_i',
	'');
INSERT INTO S_UDT
	VALUES (524307,
	524290,
	0);
INSERT INTO S_DT
	VALUES (524307,
	13105528,
	'my_b',
	'');
INSERT INTO S_UDT
	VALUES (524308,
	524292,
	0);
INSERT INTO S_DT
	VALUES (524308,
	13105528,
	'my_r',
	'');
INSERT INTO S_UDT
	VALUES (524309,
	524293,
	0);
INSERT INTO S_DT
	VALUES (524309,
	13105528,
	'my_s',
	'');
INSERT INTO S_SYNC
	VALUES (524289,
	13105528,
	'LogDateAndTime',
	'',
	'//output the current date
cur_date = TIM::current_date();
LOG::LogDate(date:cur_date,message:param.message) ;

//output the current time
cur_time = TIM::current_clock();
LOG::LogTime(timestamp:cur_time,message:param.message);',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524289,
	524289,
	'message',
	524293,
	0);
INSERT INTO S_EE
	VALUES (524289,
	'Time',
	'',
	'TIM',
	13105528);
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
	VALUES (524290,
	524290,
	'second',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524291,
	524290,
	'minute',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524292,
	524290,
	'hour',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524293,
	524290,
	'day',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524294,
	524290,
	'month',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524295,
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
	VALUES (524296,
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
	VALUES (524297,
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
	VALUES (524298,
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
	VALUES (524299,
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
	VALUES (524300,
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
	VALUES (524301,
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
	VALUES (524302,
	524298,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524303,
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
	VALUES (524304,
	524299,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524305,
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
	VALUES (524306,
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
	VALUES (524307,
	524301,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524308,
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
	VALUES (524309,
	524302,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524310,
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
	VALUES (524311,
	524303,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_EE
	VALUES (524290,
	'Architecture',
	'',
	'ARCH',
	13105528);
INSERT INTO S_BRG
	VALUES (524304,
	524290,
	'shutdown',
	'',
	0,
	524289,
	'control stop;',
	1);
INSERT INTO S_EE
	VALUES (524291,
	'Logger',
	'',
	'LOG',
	13105528);
INSERT INTO S_BRG
	VALUES (524305,
	524291,
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
	524291,
	'LogSuccess',
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
INSERT INTO S_BRG
	VALUES (524307,
	524291,
	'LogInfo',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524314,
	524307,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524308,
	524291,
	'LogDate',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524315,
	524308,
	'date',
	524302,
	0);
INSERT INTO S_BPARM
	VALUES (524316,
	524308,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524309,
	524291,
	'LogTime',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524317,
	524309,
	'timestamp',
	524303,
	0);
INSERT INTO S_BPARM
	VALUES (524318,
	524309,
	'message',
	524293,
	0);
INSERT INTO GD_MD
	VALUES (524289,
	1,
	13105528,
	1,
	1,
	0,
	1,
	1,
	0,
	12,
	1711,
	4181,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (524314,
	524289,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524314,
	1904,
	1376,
	2080,
	1472);
INSERT INTO GD_GE
	VALUES (524293,
	524289,
	524289,
	12);
INSERT INTO GD_SHP
	VALUES (524293,
	1904,
	1536,
	2080,
	1632);
INSERT INTO GD_GE
	VALUES (524294,
	524289,
	524290,
	12);
INSERT INTO GD_SHP
	VALUES (524294,
	2144,
	1376,
	2320,
	1472);
INSERT INTO GD_GE
	VALUES (524295,
	524289,
	524291,
	12);
INSERT INTO GD_SHP
	VALUES (524295,
	2144,
	1536,
	2320,
	1632);
INSERT INTO S_SS
	VALUES (1048578,
	'ex',
	'',
	'EX',
	1,
	13105528,
	1048578);
INSERT INTO O_OBJ
	VALUES (1048577,
	'Initialization',
	1,
	'INIT',
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
	'id',
	'',
	'',
	'id',
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
	1,
	'Init',
	0,
	'',
	'INIT1',
	'');
INSERT INTO SM_STATE
	VALUES (1572865,
	1572867,
	0,
	'Init',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (1572865,
	1572865,
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
	'// Create the test classes
create object instance bex of BEX;
create object instance iex of IEX;
create object instance rex of REX;
create object instance sex of SEX;

generate BEX1 to bex;',
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
	1572865,
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
	1600,
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
	1840,
	1328,
	2112,
	1520);
INSERT INTO GD_GE
	VALUES (1572867,
	1572865,
	1572865,
	42);
INSERT INTO GD_CON
	VALUES (1572867,
	1572866,
	1572866,
	0);
INSERT INTO GD_CTXT
	VALUES (1572867,
	0,
	0,
	0,
	0,
	0,
	0,
	1945,
	1248,
	2007,
	1270,
	-2,
	-4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572868,
	1572867,
	1856,
	1328,
	1856,
	1280,
	0);
INSERT INTO GD_LS
	VALUES (1572869,
	1572867,
	1856,
	1280,
	2096,
	1280,
	1572868);
INSERT INTO GD_LS
	VALUES (1572870,
	1572867,
	2096,
	1280,
	2096,
	1328,
	1572869);
INSERT INTO O_OBJ
	VALUES (1048578,
	'Boolean Expressions',
	2,
	'BEX',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048579,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048579,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048579,
	1048578,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048580,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048580,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048580,
	1048578,
	1048579,
	'b',
	'',
	'',
	'b',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (1048581,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048581,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048581,
	1048578,
	1048580,
	'my_b',
	'',
	'',
	'my_b',
	0,
	524307);
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
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048578);
INSERT INTO O_OIDA
	VALUES (1048579,
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
	2,
	'Next',
	0,
	'',
	'BEX2',
	'');
INSERT INTO SM_LEVT
	VALUES (2097154,
	2097156,
	0);
INSERT INTO SM_SEVT
	VALUES (2097154,
	2097156,
	0);
INSERT INTO SM_EVT
	VALUES (2097154,
	2097156,
	0,
	1,
	'Begin',
	0,
	'',
	'BEX1',
	'');
INSERT INTO SM_STATE
	VALUES (2097153,
	2097156,
	0,
	'Check Defaults and Assign',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (2097153,
	2097153,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097153,
	2097154,
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
	'::LogDateAndTime(message:"Beginning Boolean Expressions Test");

////////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////////
// Check Defaults
// Boolean type
if ( self.b == FALSE )
  LOG::LogSuccess(message:"boolean - Uniitialized default equals FALSE");
else
  LOG::LogFailure(message:"boolean - Uniitialized default does not equal FALSE");
end if;

// User defined Integer type
if ( self.my_b == FALSE )
  LOG::LogSuccess(message:"udt boolean - Uniitialized default equals FALSE");
else
  LOG::LogFailure(message:"udt boolean - Uniitialized default does not equal FALSE");
end if;

// Assign values
// Boolean type
self.b = TRUE;

// User defined boolean type
self.my_b = TRUE;

generate BEX2 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097154,
	2097156,
	0,
	'Boolean Operators',
	2,
	0);
INSERT INTO SM_SEME
	VALUES (2097154,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097154,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097154,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097154,
	2097156,
	2097154);
INSERT INTO SM_AH
	VALUES (2097154,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097154,
	2097156,
	1,
	'////////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////////
// Boolean type
if ( ( self.b == TRUE )	AND
     ( self.b == self.b )	AND
     ( self.b != FALSE )	AND
     ( self.b == (not FALSE) )	AND
     ( self.b == (not (not TRUE))) AND
     ( self.b == (not (not self.b))) AND
     ( self.b != (not self.b) ) )
  LOG::LogSuccess(message:"Boolean Type: Boolean Operation test successful");
else
  LOG::LogFailure(message:"Boolean Type: Boolean Operation test failed");
end if;

// User Defined Boolean type
if ( ( self.my_b == TRUE )		AND
     ( self.my_b == self.my_b )		AND
     ( self.my_b != FALSE  )		AND
     ( self.my_b == (not FALSE) )	AND
     ( self.my_b == (not (not TRUE))) 	AND
     ( self.my_b == (not (not self.my_b)))	AND
     ( self.my_b != (not self.my_b) ) )
  LOG::LogSuccess(message:"UDT Boolean: Boolean Operation test successful");
else
  LOG::LogFailure(message:"UDT Boolean: Boolean Operation test failed");
end if;

////////////////////////////////////////////////////////////////////
// Locals
////////////////////////////////////////////////////////////////////
// Assign values
// Boolean type
local_b = TRUE;
if ( (local_b == TRUE)		AND
     (local_b == not FALSE)		AND
     (local_b == ( not (not TRUE) ) )	AND
     (local_b != (not TRUE) ) )
  LOG::LogSuccess(message:"local boolean - Assigned equals TRUE");
else
  LOG::LogFailure(message:"local boolean - Assigned does not equal TRUE");
end if;

// User defined boolean type
local_ub = self.my_b;
if ( (local_ub == TRUE )		AND
     (local_ub == not FALSE)		AND
     (local_ub == ( not (not TRUE) ) )	AND
     (local_ub != (not TRUE) ) )
  LOG::LogSuccess(message:"udt local boolean - Assigned equals TRUE");
else
  LOG::LogFailure(message:"udt local boolean - Assigned does not equal TRUE");
end if;

////////////////////////////////////////////////////////////////////
// Literals
////////////////////////////////////////////////////////////////////
// Assign values
// Boolean type
if ( 	(TRUE == TRUE)		AND
	(TRUE != FALSE)		AND
	(FALSE != TRUE)		AND
	(TRUE == not FALSE)	AND
	(FALSE == not TRUE)	AND
	(TRUE == (not(not TRUE)))	AND
	(FALSE == (not(not FALSE)))	AND
	(FALSE == FALSE) )
  LOG::LogSuccess(message:"literal boolean - TRUE vs. FALSE successful");
else
  LOG::LogFailure(message:"literal boolean - TRUE vs. FALSE failed");
end if;

generate BEX2 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097155,
	2097156,
	0,
	'Binary Operations',
	3,
	0);
INSERT INTO SM_EIGN
	VALUES (2097155,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097155,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097154,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097155,
	2097156,
	2097155);
INSERT INTO SM_AH
	VALUES (2097155,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097155,
	2097156,
	1,
	'////////////////////////////////////////////////////////////////////
// Binary Operations - Attributes
////////////////////////////////////////////////////////////////////
// Boolean type
if ( ( self.b AND TRUE )		AND
     ( self.b AND self.b )		AND
     ( self.b OR FALSE ) 		AND
     ( self.b AND (not FALSE) )		AND
     ( self.b AND (not (not TRUE))) 	AND
     ( self.b AND (not (not self.b))) 	AND
     ( self.b OR (not self.b) ) )
  LOG::LogSuccess(message:"Boolean Type: Binary Operation test successful");
else
  LOG::LogFailure(message:"Boolean Type: Binary Operation test failed");
end if;

// User Defined Boolean type
if ( ( self.my_b AND TRUE )			AND
     ( self.my_b AND self.my_b )		AND
     ( self.my_b OR FALSE  )			AND
     ( self.my_b AND (not FALSE) )		AND
     ( self.my_b AND (not (not TRUE))) 		AND
     ( self.my_b AND (not (not self.my_b)))	AND
     ( self.my_b OR (not self.my_b) ) )
  LOG::LogSuccess(message:"UDT Boolean: Boolean Operation test successful");
else
  LOG::LogFailure(message:"UDT Boolean: Boolean Operation test failed");
end if;

////////////////////////////////////////////////////////////////////
// Locals
////////////////////////////////////////////////////////////////////
// Assign values
// Boolean type
local_b = TRUE;
if ( (local_b AND TRUE)		AND
     (local_b AND (not FALSE))	AND
     (local_b AND ( not (not TRUE) ) )	AND
     (local_b OR (not TRUE) ) )
  LOG::LogSuccess(message:"local boolean - Assigned equals TRUE");
else
  LOG::LogFailure(message:"local boolean - Assigned does not equal TRUE");
end if;

// User defined boolean type
local_ub = self.my_b;
if ( (local_ub AND TRUE )		AND
     (local_ub AND not FALSE)		AND
     (local_ub AND ( not (not TRUE) ) )	AND
     (local_ub OR (not TRUE) ) )
  LOG::LogSuccess(message:"udt local boolean - Assigned equals TRUE");
else
  LOG::LogFailure(message:"udt local boolean - Assigned does not equal TRUE");
end if;

////////////////////////////////////////////////////////////////////
// Literals
////////////////////////////////////////////////////////////////////
// Assign values
// Boolean type
if ( 	(TRUE AND TRUE)		AND
	(TRUE OR FALSE)		AND
	(FALSE OR TRUE)		AND
	(TRUE AND not FALSE)	AND
	(TRUE AND (not(not TRUE) ) ) )
  LOG::LogSuccess(message:"literal boolean - TRUE successful");
else
  LOG::LogFailure(message:"literal boolean - TRUE failed");
end if;

if ( 	(FALSE AND FALSE)	AND
	(FALSE AND not TRUE)	AND
	(FALSE AND (not(not FALSE) ) ) )
  LOG::LogFailure(message:"literal boolean - FALSE failed");
else
  LOG::LogSuccess(message:"literal boolean - FALSE successful");
end if;

::LogDateAndTime(message:"Ending Boolean Expressions Test");

select any iex from instances of IEX;
generate IEX1 to iex;',
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
	2097154,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097154,
	2097156,
	2097153,
	2097154,
	0);
INSERT INTO SM_TXN
	VALUES (2097154,
	2097156,
	2097153,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097155,
	2097156,
	2097154,
	2097153,
	0);
INSERT INTO SM_TXN
	VALUES (2097155,
	2097156,
	2097155,
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
	1560,
	4156,
	0.996835,
	0);
INSERT INTO GD_GE
	VALUES (2097154,
	2097153,
	2097153,
	41);
INSERT INTO GD_SHP
	VALUES (2097154,
	1696,
	1296,
	2240,
	1536);
INSERT INTO GD_GE
	VALUES (2097155,
	2097153,
	2097154,
	41);
INSERT INTO GD_SHP
	VALUES (2097155,
	1696,
	1600,
	2240,
	1824);
INSERT INTO GD_GE
	VALUES (2097156,
	2097153,
	2097155,
	41);
INSERT INTO GD_SHP
	VALUES (2097156,
	1696,
	1904,
	2240,
	2144);
INSERT INTO GD_GE
	VALUES (2097157,
	2097153,
	2097153,
	42);
INSERT INTO GD_CON
	VALUES (2097157,
	2097154,
	2097155,
	0);
INSERT INTO GD_CTXT
	VALUES (2097157,
	0,
	0,
	0,
	0,
	0,
	0,
	1825,
	1559,
	1906,
	1581,
	-56,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097158,
	2097157,
	1968,
	1536,
	1968,
	1600,
	0);
INSERT INTO GD_GE
	VALUES (2097159,
	2097153,
	2097154,
	42);
INSERT INTO GD_CON
	VALUES (2097159,
	2097154,
	2097154,
	0);
INSERT INTO GD_CTXT
	VALUES (2097159,
	0,
	0,
	0,
	0,
	0,
	0,
	1902,
	1236,
	1993,
	1258,
	-23,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097160,
	2097159,
	1840,
	1296,
	1840,
	1264,
	0);
INSERT INTO GD_LS
	VALUES (2097161,
	2097159,
	1840,
	1264,
	2096,
	1264,
	2097160);
INSERT INTO GD_LS
	VALUES (2097162,
	2097159,
	2096,
	1264,
	2096,
	1296,
	2097161);
INSERT INTO GD_GE
	VALUES (2097163,
	2097153,
	2097155,
	42);
INSERT INTO GD_CON
	VALUES (2097163,
	2097155,
	2097156,
	0);
INSERT INTO GD_CTXT
	VALUES (2097163,
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
	VALUES (2097164,
	2097163,
	1968,
	1824,
	1968,
	1904,
	0);
INSERT INTO O_OBJ
	VALUES (1048579,
	'Integer Expressions',
	3,
	'IEX',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048583,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048583,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048583,
	1048579,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048584,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048584,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048584,
	1048579,
	1048583,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048585,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048585,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048585,
	1048579,
	1048584,
	'my_i',
	'',
	'',
	'my_i',
	0,
	524306);
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
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048579);
INSERT INTO O_OIDA
	VALUES (1048583,
	1048579,
	0);
INSERT INTO SM_ISM
	VALUES (2621445,
	1048579);
INSERT INTO SM_SM
	VALUES (2621445,
	'',
	5);
INSERT INTO SM_MOORE
	VALUES (2621445);
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
	1,
	'Begin',
	0,
	'',
	'IEX1',
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
	2,
	'Next',
	0,
	'',
	'IEX2',
	'');
INSERT INTO SM_STATE
	VALUES (2621441,
	2621445,
	0,
	'Check Defaults and Assign',
	1,
	0);
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
	'::LogDateAndTime(message:"Beginning Integer Expressions Test");

////////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////////
// Check Defaults
// Integer type
if ( self.i == 0 )
  LOG::LogSuccess(message:"integer - Uniitialized default equals 0");
else
  LOG::LogFailure(message:"integer - Uniitialized default does not equal 0");
end if;

// User defined Integer type
if ( self.my_i == 0 )
  LOG::LogSuccess(message:"udt integer - Uniitialized default equals 0");
else
  LOG::LogFailure(message:"udt integer - Uniitialized default does not equal 0");
end if;

// Assign values
// Integer type
self.i = 7;

// User defined Integer type
self.my_i = 49;

generate IEX2 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2621442,
	2621445,
	0,
	'Boolean Operators',
	2,
	0);
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
	'////////////////////////////////////////////////////////////////////
// Attributes, Locals, and Literals
////////////////////////////////////////////////////////////////////
// Integer type
i_temp = 7;
if ( ( self.i == 7 )  		AND
     ( self.i == self.i )		AND
     ( self.i == i_temp )  	AND
     ( self.i != 0  )  		AND
     ( self.i != (self.i - 1) )	AND
     ( self.i != (i_temp - 1) )  	AND
     ( self.i > 6   )   		AND
     ( self.i > (self.i - 1) )	AND
     ( self.i > (i_temp - 1) )  	AND
     ( self.i >= 6 )		AND
     ( self.i >= self.i )		AND
     ( self.i >= i_temp )  	AND
     ( self.i < 8   )   		AND
     ( self.i < (self.i + 1) )	AND
     ( self.i < (i_temp + 1) )  	AND
     ( self.i <= 8 )  		AND
     ( self.i <= self.i )		AND
     ( self.i <= i_temp )  )
  LOG::LogSuccess(message:"Integer Type: Boolean Operation test successful");
else
  LOG::LogFailure(message:"Integer Type: Boolean Operation test failed");
end if;

// User Defined Integer type
my_i_temp = self.my_i;
if ( ( self.my_i == 49 )  		AND
     ( self.my_i == self.my_i )		AND
     ( self.my_i == my_i_temp )  	AND
     ( self.my_i != 0  )  		AND
     ( self.my_i != (self.my_i - 1) )	AND
     ( self.my_i != (my_i_temp - 1) )	AND
     ( self.my_i > 48   )   		AND
     ( self.my_i > (self.my_i - 1) )	AND
     ( self.my_i > (my_i_temp - 1) ) 	AND
     ( self.my_i >= 48 )		AND
     ( self.my_i >= self.my_i )		AND
     ( self.my_i >= my_i_temp )  	AND
     ( self.my_i < 50   )   		AND
     ( self.my_i < (self.my_i + 1) )	AND
     ( self.my_i < (my_i_temp + 1) ) AND
     ( self.my_i <= 50 )  		AND
     ( self.my_i <= self.my_i )		AND
     ( self.my_i <= my_i_temp )  )
  LOG::LogSuccess(message:"UDT Integer: Boolean Operation test successful");
else
  LOG::LogFailure(message:"UDT Integer: Boolean Operation test failed");
end if;

::LogDateAndTime(message:"Ending Integer Expressions Test");

select any rex from instances of REX;
generate REX1 to rex;
',
	'');
INSERT INTO SM_NSTXN
	VALUES (2621441,
	2621445,
	2621441,
	2621441,
	0);
INSERT INTO SM_TXN
	VALUES (2621441,
	2621445,
	2621441,
	0);
INSERT INTO SM_NSTXN
	VALUES (2621442,
	2621445,
	2621441,
	2621442,
	0);
INSERT INTO SM_TXN
	VALUES (2621442,
	2621445,
	2621442,
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
	1613,
	4101,
	0.996835,
	0);
INSERT INTO GD_GE
	VALUES (2621442,
	2621441,
	2621441,
	41);
INSERT INTO GD_SHP
	VALUES (2621442,
	1744,
	1344,
	2288,
	1584);
INSERT INTO GD_GE
	VALUES (2621443,
	2621441,
	2621442,
	41);
INSERT INTO GD_SHP
	VALUES (2621443,
	1744,
	1648,
	2288,
	1872);
INSERT INTO GD_GE
	VALUES (2621444,
	2621441,
	2621441,
	42);
INSERT INTO GD_CON
	VALUES (2621444,
	2621442,
	2621442,
	0);
INSERT INTO GD_CTXT
	VALUES (2621444,
	0,
	0,
	0,
	0,
	0,
	0,
	1950,
	1284,
	2041,
	1306,
	-23,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621445,
	2621444,
	1888,
	1344,
	1888,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (2621446,
	2621444,
	1888,
	1312,
	2144,
	1312,
	2621445);
INSERT INTO GD_LS
	VALUES (2621447,
	2621444,
	2144,
	1312,
	2144,
	1344,
	2621446);
INSERT INTO GD_GE
	VALUES (2621448,
	2621441,
	2621442,
	42);
INSERT INTO GD_CON
	VALUES (2621448,
	2621442,
	2621443,
	0);
INSERT INTO GD_CTXT
	VALUES (2621448,
	0,
	0,
	0,
	0,
	0,
	0,
	1873,
	1607,
	1954,
	1629,
	-56,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621449,
	2621448,
	2016,
	1584,
	2016,
	1648,
	0);
INSERT INTO O_OBJ
	VALUES (1048580,
	'Real Expressions',
	4,
	'REX',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048587,
	1048580);
INSERT INTO O_BATTR
	VALUES (1048587,
	1048580);
INSERT INTO O_ATTR
	VALUES (1048587,
	1048580,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048588,
	1048580);
INSERT INTO O_BATTR
	VALUES (1048588,
	1048580);
INSERT INTO O_ATTR
	VALUES (1048588,
	1048580,
	1048587,
	'r',
	'',
	'',
	'r',
	0,
	524292);
INSERT INTO O_NBATTR
	VALUES (1048589,
	1048580);
INSERT INTO O_BATTR
	VALUES (1048589,
	1048580);
INSERT INTO O_ATTR
	VALUES (1048589,
	1048580,
	1048588,
	'my_r',
	'',
	'',
	'my_r',
	0,
	524308);
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
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048580);
INSERT INTO O_OIDA
	VALUES (1048587,
	1048580,
	0);
INSERT INTO SM_ISM
	VALUES (3145734,
	1048580);
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
	2,
	'Next',
	0,
	'',
	'REX2',
	'');
INSERT INTO SM_LEVT
	VALUES (3145730,
	3145734,
	0);
INSERT INTO SM_SEVT
	VALUES (3145730,
	3145734,
	0);
INSERT INTO SM_EVT
	VALUES (3145730,
	3145734,
	0,
	1,
	'Begin',
	0,
	'',
	'REX1',
	'');
INSERT INTO SM_STATE
	VALUES (3145729,
	3145734,
	0,
	'Check Defaults and Assign',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (3145729,
	3145729,
	3145734,
	0);
INSERT INTO SM_SEME
	VALUES (3145729,
	3145730,
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
	'::LogDateAndTime(message:"Beginning Real Expressions Test");

////////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////////
// Check Defaults
// Real type
if ( self.r == 0.0 )
  LOG::LogSuccess(message:"real - Uniitialized default equals 0.0");
else
  LOG::LogFailure(message:"real - Uniitialized default does not equal 0.0");
end if;

// User defined Real type
if ( self.my_r == 0.0 )
  LOG::LogSuccess(message:"udt real - Uniitialized default equals 0.0");
else
  LOG::LogFailure(message:"udt real - Uniitialized default does not equal 0.0");
end if;

// Assign values
// Real type
self.r = 7.7;

// User defined Real type
self.my_r = 49.9;

generate REX2 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (3145730,
	3145734,
	0,
	'Boolean Operators',
	2,
	0);
INSERT INTO SM_EIGN
	VALUES (3145730,
	3145729,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145729,
	3145734,
	0);
INSERT INTO SM_EIGN
	VALUES (3145730,
	3145730,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145730,
	3145734,
	0);
INSERT INTO SM_MOAH
	VALUES (3145730,
	3145734,
	3145730);
INSERT INTO SM_AH
	VALUES (3145730,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145730,
	3145734,
	1,
	'////////////////////////////////////////////////////////////////////
// Attributes, Locals, and Literals
////////////////////////////////////////////////////////////////////
// Real type
r_temp = 7.7;
if ( ( self.r == 7.7 )  		AND
     ( self.r == self.r )	AND
     ( self.r == r_temp )  	AND
     ( self.r != 0.0  )  		AND
     ( self.r != (self.r - 1.1) )	AND
     ( self.r != (r_temp - 1.1) )  	AND
     ( self.r > 6.1   )   	AND
     ( self.r > (self.r - 1.1) )	AND
     ( self.r > (r_temp - 1.1) )  	AND
     ( self.r >= 6.1 )		AND
     ( self.r >= self.r )	AND
     ( self.r >= r_temp )  	AND
     ( self.r < 8.1   )   	AND
     ( self.r < (self.r + 1.1) )	AND
     ( self.r < (r_temp + 1.1) )  	AND
     ( self.r <= 8.1 )  		AND
     ( self.r <= self.r )	AND
     ( self.r <= r_temp )  )
  LOG::LogSuccess(message:"Real Type: Boolean Operation test successful");
else
  LOG::LogFailure(message:"Real Type: Boolean Operation test failed");
end if;

// User Defined Real type
my_r_temp = self.my_r;
if ( ( self.my_r == 49.9 )  		AND
     ( self.my_r == self.my_r )		AND
     ( self.my_r == my_r_temp )  	AND
     ( self.my_r != 0.0  )  		AND
     ( self.my_r != (self.my_r - 1.1) )	AND
     ( self.my_r != (my_r_temp - 1.1) )	AND
     ( self.my_r > 48.8  )   		AND
     ( self.my_r > (self.my_r - 1.1) )	AND
     ( self.my_r > (my_r_temp - 1.1) ) 	AND
     ( self.my_r >= 48.8 )		AND
     ( self.my_r >= self.my_r )		AND
     ( self.my_r >= my_r_temp )  	AND
     ( self.my_r < 50.1   )   		AND
     ( self.my_r < (self.my_r + 1.1) )	AND
     ( self.my_r < (my_r_temp + 1.1) )	AND
     ( self.my_r <= 50.1 )  		AND
     ( self.my_r <= self.my_r )		AND
     ( self.my_r <= my_r_temp )  )
  LOG::LogSuccess(message:"UDT Real: Boolean Operation test successful");
else
  LOG::LogFailure(message:"UDT Real: Boolean Operation test failed");
end if;

::LogDateAndTime(message:"Ending Real Expressions Test");

select any sex from instances of SEX;
generate SEX1 to sex;',
	'');
INSERT INTO SM_NSTXN
	VALUES (3145729,
	3145734,
	3145729,
	3145729,
	0);
INSERT INTO SM_TXN
	VALUES (3145729,
	3145734,
	3145730,
	0);
INSERT INTO SM_NSTXN
	VALUES (3145730,
	3145734,
	3145729,
	3145730,
	0);
INSERT INTO SM_TXN
	VALUES (3145730,
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
	1600,
	4199,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (3145730,
	3145729,
	3145729,
	41);
INSERT INTO GD_SHP
	VALUES (3145730,
	1696,
	1296,
	2240,
	1536);
INSERT INTO GD_GE
	VALUES (3145731,
	3145729,
	3145730,
	41);
INSERT INTO GD_SHP
	VALUES (3145731,
	1696,
	1600,
	2240,
	1824);
INSERT INTO GD_GE
	VALUES (3145732,
	3145729,
	3145729,
	42);
INSERT INTO GD_CON
	VALUES (3145732,
	3145730,
	3145731,
	0);
INSERT INTO GD_CTXT
	VALUES (3145732,
	0,
	0,
	0,
	0,
	0,
	0,
	1825,
	1559,
	1906,
	1581,
	-56,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145733,
	3145732,
	1968,
	1536,
	1968,
	1600,
	0);
INSERT INTO GD_GE
	VALUES (3145734,
	3145729,
	3145730,
	42);
INSERT INTO GD_CON
	VALUES (3145734,
	3145730,
	3145730,
	0);
INSERT INTO GD_CTXT
	VALUES (3145734,
	0,
	0,
	0,
	0,
	0,
	0,
	1902,
	1236,
	1993,
	1258,
	-23,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145735,
	3145734,
	1840,
	1296,
	1840,
	1264,
	0);
INSERT INTO GD_LS
	VALUES (3145736,
	3145734,
	1840,
	1264,
	2096,
	1264,
	3145735);
INSERT INTO GD_LS
	VALUES (3145737,
	3145734,
	2096,
	1264,
	2096,
	1296,
	3145736);
INSERT INTO O_OBJ
	VALUES (1048581,
	'String Expressions',
	5,
	'SEX',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048591,
	1048581);
INSERT INTO O_BATTR
	VALUES (1048591,
	1048581);
INSERT INTO O_ATTR
	VALUES (1048591,
	1048581,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048592,
	1048581);
INSERT INTO O_BATTR
	VALUES (1048592,
	1048581);
INSERT INTO O_ATTR
	VALUES (1048592,
	1048581,
	1048591,
	's',
	'',
	'',
	's',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048593,
	1048581);
INSERT INTO O_BATTR
	VALUES (1048593,
	1048581);
INSERT INTO O_ATTR
	VALUES (1048593,
	1048581,
	1048592,
	'my_s',
	'',
	'',
	'my_s',
	0,
	524309);
INSERT INTO O_NBATTR
	VALUES (1048594,
	1048581);
INSERT INTO O_BATTR
	VALUES (1048594,
	1048581);
INSERT INTO O_ATTR
	VALUES (1048594,
	1048581,
	1048593,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048581);
INSERT INTO O_OIDA
	VALUES (1048591,
	1048581,
	0);
INSERT INTO SM_ISM
	VALUES (3670023,
	1048581);
INSERT INTO SM_SM
	VALUES (3670023,
	'',
	7);
INSERT INTO SM_MOORE
	VALUES (3670023);
INSERT INTO SM_LEVT
	VALUES (3670017,
	3670023,
	0);
INSERT INTO SM_SEVT
	VALUES (3670017,
	3670023,
	0);
INSERT INTO SM_EVT
	VALUES (3670017,
	3670023,
	0,
	2,
	'Next',
	0,
	'',
	'SEX2',
	'');
INSERT INTO SM_LEVT
	VALUES (3670018,
	3670023,
	0);
INSERT INTO SM_SEVT
	VALUES (3670018,
	3670023,
	0);
INSERT INTO SM_EVT
	VALUES (3670018,
	3670023,
	0,
	1,
	'Begin',
	0,
	'',
	'SEX1',
	'');
INSERT INTO SM_STATE
	VALUES (3670017,
	3670023,
	0,
	'Check Defaults and Assign',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (3670017,
	3670017,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670017,
	3670018,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670017,
	3670023,
	3670017);
INSERT INTO SM_AH
	VALUES (3670017,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670017,
	3670023,
	1,
	'::LogDateAndTime(message:"Beginning String Expressions Test");

////////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////////
// Check Defaults
// String type
if ( self.s == "" )
  LOG::LogSuccess(message:"string - Uniitialized default equals empty string");
else
  LOG::LogFailure(message:"string - Uniitialized default does not equal empty string");
end if;

// User defined String type
if ( self.my_s == "" )
  LOG::LogSuccess(message:"udt string - Uniitialized default equals empty string");
else
  LOG::LogFailure(message:"udt string - Uniitialized default does not equal empty string");
end if;

// Assign values
// String type
self.s = "Hello";

// User defined String type
self.my_s = " World";

generate SEX2 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (3670018,
	3670023,
	0,
	'Boolean Operators',
	2,
	0);
INSERT INTO SM_EIGN
	VALUES (3670018,
	3670017,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670017,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670018,
	3670018,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670018,
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
	'////////////////////////////////////////////////////////////////////
// Attributes, Locals, and Literals
////////////////////////////////////////////////////////////////////
// String type
s_temp = "Hello";
ls_temp = "Hello World";
gr_temp = "Hell";
if ( ( self.s == "Hello" )  	AND
     ( self.s == self.s )	AND
     ( self.s == s_temp )  	AND
     ( self.s != ""  )  		AND
     ( self.s > "Hell"   )	AND
     ( self.s > gr_temp )	AND
     ( self.s >= "Hello" )	AND
     ( self.s >= gr_temp )	AND
     ( self.s < "Hello World" )	AND
     ( self.s < ls_temp )	AND
     ( self.s <= "Hello" ) 	AND
     ( self.s <= ls_temp ) )
  LOG::LogSuccess(message:"String Type: Boolean Operation test successful");
else
  LOG::LogFailure(message:"String Type: Boolean Operation test failed");
end if;

// User Defined String type
my_s_temp = self.my_s;
ls_temp = " World!!";
gr_temp = " Worl";
if ( ( self.my_s == " World" )  		AND
     ( self.my_s == self.my_s )		AND
     ( self.my_s == my_s_temp )  		AND
     ( self.my_s != ""  )  		AND
     ( self.my_s > " Worl"   )		AND
     ( self.my_s > gr_temp )		AND
     ( self.my_s >= " World" )		AND
     ( self.my_s >= gr_temp )		AND
     ( self.my_s < " World!!" )		AND
     ( self.my_s < ls_temp )		AND
     ( self.my_s <= " World!!" ) 		AND
     ( self.my_s <= ls_temp ) )
  LOG::LogSuccess(message:"UDT String: Boolean Operation test successful");
else
  LOG::LogFailure(message:"UDT String: Boolean Operation test failed");
end if;

::LogDateAndTime(message:"Ending String Expressions Test");

ARCH::shutdown();',
	'');
INSERT INTO SM_NSTXN
	VALUES (3670017,
	3670023,
	3670017,
	3670017,
	0);
INSERT INTO SM_TXN
	VALUES (3670017,
	3670023,
	3670018,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670018,
	3670023,
	3670017,
	3670018,
	0);
INSERT INTO SM_TXN
	VALUES (3670018,
	3670023,
	3670017,
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
	3867,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (3670018,
	3670017,
	3670017,
	41);
INSERT INTO GD_SHP
	VALUES (3670018,
	1696,
	1296,
	2240,
	1536);
INSERT INTO GD_GE
	VALUES (3670019,
	3670017,
	3670018,
	41);
INSERT INTO GD_SHP
	VALUES (3670019,
	1696,
	1600,
	2240,
	1824);
INSERT INTO GD_GE
	VALUES (3670020,
	3670017,
	3670017,
	42);
INSERT INTO GD_CON
	VALUES (3670020,
	3670018,
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
	1825,
	1559,
	1906,
	1581,
	-56,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670021,
	3670020,
	1968,
	1536,
	1968,
	1600,
	0);
INSERT INTO GD_GE
	VALUES (3670022,
	3670017,
	3670018,
	42);
INSERT INTO GD_CON
	VALUES (3670022,
	3670018,
	3670018,
	0);
INSERT INTO GD_CTXT
	VALUES (3670022,
	0,
	0,
	0,
	0,
	0,
	0,
	1902,
	1236,
	1993,
	1258,
	-23,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670023,
	3670022,
	1840,
	1296,
	1840,
	1264,
	0);
INSERT INTO GD_LS
	VALUES (3670024,
	3670022,
	1840,
	1264,
	2096,
	1264,
	3670023);
INSERT INTO GD_LS
	VALUES (3670025,
	3670022,
	2096,
	1264,
	2096,
	1296,
	3670024);
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
	1594,
	4507,
	1.074217,
	0);
INSERT INTO GD_GE
	VALUES (1048587,
	1048584,
	1048577,
	21);
INSERT INTO GD_SHP
	VALUES (1048587,
	1696,
	928,
	1952,
	1088);
INSERT INTO GD_GE
	VALUES (1048588,
	1048584,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048588,
	1984,
	928,
	2240,
	1088);
INSERT INTO GD_GE
	VALUES (1048589,
	1048584,
	1048579,
	21);
INSERT INTO GD_SHP
	VALUES (1048589,
	1696,
	1120,
	1952,
	1280);
INSERT INTO GD_GE
	VALUES (1048590,
	1048584,
	1048580,
	21);
INSERT INTO GD_SHP
	VALUES (1048590,
	1984,
	1120,
	2240,
	1280);
INSERT INTO GD_GE
	VALUES (1048591,
	1048584,
	1048581,
	21);
INSERT INTO GD_SHP
	VALUES (1048591,
	1696,
	1312,
	1952,
	1472);
