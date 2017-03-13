-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (48319,
	'G_STE_G_COP_compare_date',
	'This test checks that the Date comparison operators are 
implemented correctly.',
	0,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	48319,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	48319,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	48319,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	48319,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	48319,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	48319,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	48319,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	48319,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	48319,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	48319,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	48319,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	48319,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	48319,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	48319,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	48319,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	48319,
	'inst_ref<Timer>',
	'');
INSERT INTO S_UDT
	VALUES (524305,
	524294,
	0);
INSERT INTO S_DT
	VALUES (524305,
	48319,
	'arbitrary_id',
	'Arbitrary ID with core data type of unique_id.');
INSERT INTO S_EE
	VALUES (524289,
	'Time',
	'',
	'TIM',
	48319);
INSERT INTO S_BRG
	VALUES (524289,
	524289,
	'current_date',
	'Returns the current date.',
	1,
	524302,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524290,
	524289,
	'create_date',
	'Creates a date based on input parameters.

Returns a date.',
	1,
	524302,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524289,
	524290,
	'second',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524290,
	524290,
	'minute',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524291,
	524290,
	'hour',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524292,
	524290,
	'day',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524293,
	524290,
	'month',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524294,
	524290,
	'year',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524291,
	524289,
	'get_second',
	'Returns the second contained in <date>.',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524295,
	524291,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524292,
	524289,
	'get_minute',
	'Returns the minute contained in <date>.',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524296,
	524292,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524293,
	524289,
	'get_hour',
	'Returns the hour contained in <date>.',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524297,
	524293,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524294,
	524289,
	'get_day',
	'Returns the day contained in <date>.',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524298,
	524294,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524295,
	524289,
	'get_month',
	'Returns the month contained in <date>.',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524299,
	524295,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524296,
	524289,
	'get_year',
	'Returns the year contained in <date>.',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524300,
	524296,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524297,
	524289,
	'current_clock',
	'Returns the current time.',
	1,
	524303,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524298,
	524289,
	'timer_start',
	'Starts a (one shot) timer to expire in <microseconds>, sending <event_inst> upon expiration.  Note that upon expiration the timer will be deleted.

Returns the instance handle of the non-recurring timer.',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524301,
	524298,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524302,
	524298,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524299,
	524289,
	'timer_start_recurring',
	'Starts a (recurring) timer to expire in <microseconds>, sending <event_inst> upon expiration.  Upon expiration, the timer will be restarted and fire again in <microseconds> and (re)generate the event <event_inst>.

Returns the instance handle of the recurring timer.',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524303,
	524299,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524304,
	524299,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524300,
	524289,
	'timer_remaining_time',
	'Returns the time remaining (in microseconds) before <timer_inst_ref> expires. If <timer_inst_ref> no longer exists, a zero value will be returned. 

It is important to note that even if it no longer exists, an event may still be in transit (e.g., was generated) from a previous timer expiration.  Respectable OOA must account for this "ships passing" possibility.

Returns the time remaining.
',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524305,
	524300,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BRG
	VALUES (524301,
	524289,
	'timer_reset_time',
	'This method attempts to set <timer_inst_ref> to expire <microseconds>.

It is important to note that an event may still be in transit (e.g., was already generated) from a previous expiration prior to this method invocation.  Respectable OOA must account for this "ships passing" possibility.

Returns TRUE if the timer exists, otherwise FALSE.',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524306,
	524301,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524307,
	524301,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524302,
	524289,
	'timer_add_time',
	'This method attempts to add <microseconds> to the <timer_inst_ref>. 

It is important to note that an event may still be in transit (e.g., was already generated) from a previous expiration prior to this method invocation.  Respectable OOA must account for this "ships passing" possibility.

Returns TRUE if the timer exists, otherwise FALSE.',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524308,
	524302,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524309,
	524302,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524303,
	524289,
	'timer_cancel',
	'This method attempts to cancel and delete <timer_inst_ref>.  

It is important to note that an event may still be in transit (e.g., was already generated) from a previous expiration prior to this method invocation.  Respectable OOA must account for this "ships passing" possibility.

Returns TRUE if the timer exists, otherwise FALSE.',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524310,
	524303,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_EE
	VALUES (524290,
	'Architecture',
	'This EE provides an interface between the domain and the run time implementation that supports the execution of the domain.',
	'ARCH',
	48319);
INSERT INTO S_BRG
	VALUES (524304,
	524290,
	'shutdown',
	'This bridge sends a request to the run time implementation to shut down.',
	0,
	524289,
	'control stop;',
	1);
INSERT INTO S_EE
	VALUES (524291,
	'Logging',
	'This EE is responsible for logging test data.

The output will be written to a file that may be used for test verification.',
	'LOG',
	48319);
INSERT INTO S_BRG
	VALUES (524305,
	524291,
	'LogFailure',
	'Log a test failure.

Output fomat:

"Log Failure - <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524311,
	524305,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524306,
	524291,
	'LogInfo',
	'Log some information.

Output fomat:

"Log Info - <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524312,
	524306,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524307,
	524291,
	'LogSuccess',
	'Log a test success.

Output fomat:

"Log Success - <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524313,
	524307,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524308,
	524291,
	'LogReal',
	'Log a real value with an optional message.

Output fomat:

"Log Real - <real>: <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524314,
	524308,
	'message',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524315,
	524308,
	'real',
	524292,
	0);
INSERT INTO S_BRG
	VALUES (524309,
	524291,
	'LogDate',
	'Log a date value with an optional message.

Output fomat:

"Log Date - <date>: <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524316,
	524309,
	'message',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524317,
	524309,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524310,
	524291,
	'LogTime',
	'Log a timestamp value with an optional message.

Output fomat:

"Log Time - <time>: <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524318,
	524310,
	'message',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524319,
	524310,
	'time',
	524303,
	0);
INSERT INTO S_BRG
	VALUES (524311,
	524291,
	'LogInt',
	'Log an integer value with an optional message.

Output fomat:

"Log Int - <int>: <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524320,
	524311,
	'message',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524321,
	524311,
	'int',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524312,
	524291,
	'LogString',
	'Log a string value with an optional message.

Output fomat:

"Log String - <str>: <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524322,
	524312,
	'message',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524323,
	524312,
	'str',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524313,
	524291,
	'LogBoolean',
	'Log a boolean value with an optional message.

Output fomat:

"Log Boolean - <bool>: <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524324,
	524313,
	'message',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524325,
	524313,
	'bool',
	524290,
	0);
INSERT INTO S_BRG
	VALUES (524314,
	524291,
	'LogUniqueId',
	'Log a unique_id value with an optional message.

Output fomat:

"Log Unique ID - <uid>: <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524326,
	524314,
	'message',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524327,
	524314,
	'uid',
	524294,
	0);
INSERT INTO S_BRG
	VALUES (524315,
	524291,
	'LogArbitraryId',
	'Log an arbitrary_id value with an optional message.

Output fomat:

"Log Arbitrary ID - <aid>: <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524328,
	524315,
	'message',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524329,
	524315,
	'aid',
	524305,
	0);
INSERT INTO GD_MD
	VALUES (524289,
	1,
	48319,
	1,
	1,
	0,
	1,
	1,
	0,
	12,
	1824,
	4173,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (524293,
	524289,
	524289,
	12);
INSERT INTO GD_SHP
	VALUES (524293,
	1936,
	1536,
	2096,
	1632);
INSERT INTO GD_GE
	VALUES (524294,
	524289,
	524290,
	12);
INSERT INTO GD_SHP
	VALUES (524294,
	2144,
	1536,
	2304,
	1632);
INSERT INTO GD_GE
	VALUES (524295,
	524289,
	524291,
	12);
INSERT INTO GD_SHP
	VALUES (524295,
	2352,
	1536,
	2512,
	1632);
INSERT INTO GD_GE
	VALUES (524296,
	524289,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524296,
	2144,
	1392,
	2304,
	1488);
INSERT INTO GD_MD
	VALUES (524290,
	2,
	48319,
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
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524297,
	2160,
	1248,
	2320,
	1344);
INSERT INTO GD_MD
	VALUES (524291,
	3,
	48319,
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
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524298,
	2160,
	1248,
	2320,
	1344);
INSERT INTO GD_MD
	VALUES (524292,
	4,
	48319,
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
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524299,
	2160,
	1248,
	2320,
	1344);
INSERT INTO S_SS
	VALUES (1048578,
	'Date Test',
	'Rename this subsystem to something appropriate for your purposes.',
	'',
	1,
	48319,
	1048578);
INSERT INTO O_OBJ
	VALUES (1048577,
	'Initialization',
	1,
	'INIT',
	'This class is used to create any pre-existing instances necessary for the execution of the domain.  It also generates the creation event to the driver to begin the test(s).  

This class is not translated the same as other classes by the architecture.  Events other than the creation event inside of the initialization class are not allowed.  Any instances created or events generated from its state chart are processed before the dispatcher is started.',
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
	'Init_ID',
	'',
	'',
	'Init_ID',
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
INSERT INTO SM_SUPDT
	VALUES (1572865,
	1572867,
	0);
INSERT INTO SM_STATE
	VALUES (1572865,
	1572867,
	1572865,
	'Initialize',
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
	'Init',
	0,
	'',
	'INIT1',
	'');
INSERT INTO SM_EIGN
	VALUES (1572865,
	1572865,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572865,
	1572865,
	1572867,
	1572865);
INSERT INTO SM_CRTXN
	VALUES (1572865,
	1572867,
	1572865,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572865,
	1572867,
	1572865,
	1572865);
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
	'// Insert initialization action language here.

generate DRV1 to DRV creator;',
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
	1615,
	4278,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (1572866,
	1572865,
	1572865,
	41);
INSERT INTO GD_SHP
	VALUES (1572866,
	1856,
	1376,
	2176,
	1520);
INSERT INTO GD_GE
	VALUES (1572867,
	1572865,
	1572865,
	42);
INSERT INTO GD_CON
	VALUES (1572867,
	1572866,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (1572867,
	0,
	0,
	0,
	0,
	0,
	0,
	1915,
	1321,
	1978,
	1345,
	-32,
	-5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572868,
	1572867,
	2016,
	1376,
	2016,
	1296,
	0);
INSERT INTO O_OBJ
	VALUES (1048578,
	'Driver',
	2,
	'DRV',
	'The Driver class is what runs the test(s).',
	1048578);
INSERT INTO O_TFR
	VALUES (1048577,
	1048578,
	'date_compare',
	'',
	524289,
	0,
	'// d1 should be less than d2

if ( param.d2 > param.d1 )
  LOG::LogSuccess( message:"date > comparison OK");
else
  LOG::LogFailure( message:"date > comparison failed");
end if;

if ( param.d2 != param.d1 )
  LOG::LogSuccess( message:"date != comparison OK");
else
  LOG::LogFailure( message:"date != comparison failed");
end if;

if ( param.d2 >= param.d1 )
  LOG::LogSuccess( message:"date >= comparison OK");
else
  LOG::LogFailure( message:"date >= comparison failed");
end if;

if ( param.d2 < param.d1 )
  LOG::LogFailure( message:"date < comparison failed");
else
  LOG::LogSuccess( message:"date < comparison OK");
end if;

if ( param.d2 == param.d1 )
  LOG::LogFailure( message:"date == comparison failed");
else
  LOG::LogSuccess( message:"date == comparison OK");
end if;

if ( param.d2 <= param.d1 )
  LOG::LogFailure( message:"date <= comparison failed");
else
  LOG::LogSuccess( message:"date <= comparison OK");
end if;

',
	1);
INSERT INTO O_TPARM
	VALUES (1048577,
	1048577,
	'd1',
	524302,
	0);
INSERT INTO O_TPARM
	VALUES (1048578,
	1048577,
	'd2',
	524302,
	0);
INSERT INTO O_TFR
	VALUES (1048578,
	1048578,
	'date_compare_ref',
	'',
	524289,
	0,
	'// d1 should be less than d2

if ( param.d2 > param.d1 )
  LOG::LogSuccess( message:"date > comparison OK");
else
  LOG::LogFailure( message:"date > comparison failed");
end if;

if ( param.d2 != param.d1 )
  LOG::LogSuccess( message:"date != comparison OK");
else
  LOG::LogFailure( message:"date != comparison failed");
end if;

if ( param.d2 >= param.d1 )
  LOG::LogSuccess( message:"date >= comparison OK");
else
  LOG::LogFailure( message:"date >= comparison failed");
end if;

if ( param.d2 < param.d1 )
  LOG::LogFailure( message:"date < comparison failed");
else
   LOG::LogSuccess( message:"date < comparison OK");
end if;

if ( param.d2 == param.d1 )
  LOG::LogFailure( message:"date == comparison failed");
else
  LOG::LogSuccess( message:"date == comparison OK");
end if;

if ( param.d2 <= param.d1 )
  LOG::LogFailure( message:"date <= comparison failed");
else
  LOG::LogSuccess( message:"date <= comparison OK");
end if;

',
	1);
INSERT INTO O_TPARM
	VALUES (1048579,
	1048578,
	'd1',
	524302,
	1);
INSERT INTO O_TPARM
	VALUES (1048580,
	1048578,
	'd2',
	524302,
	1);
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
	'Driver_ID',
	'',
	'',
	'Driver_ID',
	0,
	524291);
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
INSERT INTO SM_SUPDT
	VALUES (2097153,
	2097156,
	0);
INSERT INTO SM_STATE
	VALUES (2097153,
	2097156,
	2097153,
	'Begin Test',
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
	'begin',
	0,
	'',
	'DRV1',
	'');
INSERT INTO SM_EIGN
	VALUES (2097153,
	2097153,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_LEVT
	VALUES (2097154,
	2097156,
	2097153);
INSERT INTO SM_SEVT
	VALUES (2097154,
	2097156,
	2097153);
INSERT INTO SM_EVT
	VALUES (2097154,
	2097156,
	2097153,
	2,
	'end',
	0,
	'',
	'DRV2',
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_STATE
	VALUES (2097154,
	2097156,
	2097153,
	'End Test',
	2,
	1);
INSERT INTO SM_EIGN
	VALUES (2097154,
	2097153,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097154,
	2097154,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_CRTXN
	VALUES (2097153,
	2097156,
	2097153,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097153,
	2097156,
	2097153,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097154,
	2097156,
	2097153,
	2097154,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097154,
	2097156,
	2097154,
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
	'd1 = TIM::create_date( year: 2001, month:5, day: 1, hour: 12, minute:1, second:1 );
d2 = TIM::create_date( year: 2002, month:1, day: 1, hour: 12, minute:1, second:1 );

LOG::LogInfo( message:"Compare different days" );
DRV::date_compare( d1:d1, d2:d2 );

d1 = TIM::create_date( year: 2002, month:5, day: 1, hour: 11, minute:59, second:1 );
d2 = TIM::create_date( year: 2002, month:5, day: 1, hour: 12, minute:1, second:1 );

LOG::LogInfo( message:"Compare same day, different times" );
DRV::date_compare_ref( d1:d1, d2:d2 );

d1 = TIM::create_date( year: 2002, month:4, day: 2, hour: 12, minute:1, second:1 );
d2 = TIM::create_date( year: 2002, month:5, day: 1, hour: 12, minute:1, second:1 );

LOG::LogInfo( message:"Compare different days with same day sum" );
DRV::date_compare( d1:d1, d2:d2 );

d1 = TIM::create_date( year: 2002, month:5, day: 1, hour: 11, minute:2, second:1 );
d2 = TIM::create_date( year: 2002, month:5, day: 1, hour: 12, minute:1, second:1 );

LOG::LogInfo( message:"Compare same day, with same time sum" );
DRV::date_compare_ref( d1:d1, d2:d2 );

d3 = TIM::create_date( year: 1969, month:1, day: 1, hour: 0, minute:0, second:1 );
d4 = TIM::current_date();

LOG::LogInfo( message:"Compare day to old initial value for day" );
DRV::date_compare( d1:d3, d2:d4 );

generate DRV2 to self;',
	'');
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
	'// End the test.

ARCH::shutdown();',
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
	1490,
	4211,
	0.900000,
	0);
INSERT INTO GD_GE
	VALUES (2097154,
	2097153,
	2097153,
	41);
INSERT INTO GD_SHP
	VALUES (2097154,
	1632,
	1296,
	2208,
	1456);
INSERT INTO GD_GE
	VALUES (2097155,
	2097153,
	2097154,
	41);
INSERT INTO GD_SHP
	VALUES (2097155,
	1632,
	1536,
	2208,
	1696);
INSERT INTO GD_GE
	VALUES (2097156,
	2097153,
	2097153,
	42);
INSERT INTO GD_CON
	VALUES (2097156,
	2097154,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (2097156,
	0,
	0,
	0,
	0,
	0,
	0,
	1844,
	1270,
	1920,
	1294,
	-10,
	16,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097157,
	2097156,
	1936,
	1296,
	1936,
	1232,
	0);
INSERT INTO GD_GE
	VALUES (2097158,
	2097153,
	2097154,
	42);
INSERT INTO GD_CON
	VALUES (2097158,
	2097154,
	2097155,
	0);
INSERT INTO GD_CTXT
	VALUES (2097158,
	0,
	0,
	0,
	0,
	0,
	0,
	1846,
	1477,
	1912,
	1501,
	-18,
	-9,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097159,
	2097158,
	1936,
	1456,
	1936,
	1536,
	0);
INSERT INTO GD_MD
	VALUES (1048577,
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
	VALUES (1048580,
	1048577,
	1048577,
	21);
INSERT INTO GD_SHP
	VALUES (1048580,
	1664,
	1248,
	1840,
	1360);
INSERT INTO GD_GE
	VALUES (1048581,
	1048577,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048581,
	1872,
	1248,
	2160,
	1440);
INSERT INTO GD_MD
	VALUES (1048578,
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
	VALUES (1048582,
	1048578,
	1048577,
	21);
INSERT INTO GD_SHP
	VALUES (1048582,
	1664,
	1248,
	1856,
	1312);
INSERT INTO GD_GE
	VALUES (1048583,
	1048578,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048583,
	1920,
	1248,
	2112,
	1312);
INSERT INTO GD_MD
	VALUES (1048579,
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
	VALUES (1048584,
	1048579,
	1048577,
	21);
INSERT INTO GD_SHP
	VALUES (1048584,
	1664,
	1248,
	1856,
	1312);
INSERT INTO GD_GE
	VALUES (1048585,
	1048579,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048585,
	1952,
	1264,
	2144,
	1328);
