-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (2787908,
	'G_EVT_PE_G_EVT_NLE_nle_ignored',
	'This test is designed to use a primary identifying attribute, which is of an enumerated type, in various "select...where..." statements.  The enumeration is used in the "where..." section.  The test covers selecting one or more instances, where the enumeration is some value, in functions, bridges, class operations, instance operations, MDAs, and state action language by doing the following:
	- "select any...where..." comparing to a literal enumeration.
	- "select any...where..." comparing to a local enumeration.
	- "select any...where..." comparing to its base attribute.

	- "select many...where..." comparing to a literal enumeration.
	- "select many...where..." comparing to a local enumeration.
	- "select many...where..." comparing to its base attribute.

	- "select one related by...where..." comparing to a literal enumeration.
	- "select one related by...where..." comparing to a local enumeration.
	- "select one related by...where..." comparing to its base attribute.

	- "select any related by...where..." comparing to a literal enumeration.
	- "select any related by...where..." comparing to a local enumeration.
	- "select any related by...where..." comparing to its base attribute.

	- "select many related by...where..." comparing to a literal enumeration.
	- "select many related by...where..." comparing to a local enumeration.
	- "select many related by...where..." comparing to its base attribute.

  
',
	0,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	2787908,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	2787908,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	2787908,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	2787908,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	2787908,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	2787908,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	2787908,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	2787908,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	2787908,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	2787908,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	2787908,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	2787908,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	2787908,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	2787908,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	2787908,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	2787908,
	'inst_ref<Timer>',
	'');
INSERT INTO S_UDT
	VALUES (524305,
	524294,
	0);
INSERT INTO S_DT
	VALUES (524305,
	2787908,
	'arbitrary_id',
	'Arbitrary ID with core data type of unique_id.');
INSERT INTO S_EE
	VALUES (524289,
	'Time',
	'',
	'TIM',
	2787908);
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
	2787908);
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
	2787908);
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
	2787908,
	1,
	1,
	0,
	1,
	1,
	0,
	12,
	1823,
	4174,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (524296,
	524289,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524296,
	2144,
	1344,
	2512,
	1488);
INSERT INTO GD_GE
	VALUES (524324,
	524289,
	524289,
	12);
INSERT INTO GD_SHP
	VALUES (524324,
	1936,
	1536,
	2096,
	1632);
INSERT INTO GD_GE
	VALUES (524325,
	524289,
	524290,
	12);
INSERT INTO GD_SHP
	VALUES (524325,
	2144,
	1536,
	2304,
	1632);
INSERT INTO GD_GE
	VALUES (524326,
	524289,
	524291,
	12);
INSERT INTO GD_SHP
	VALUES (524326,
	2352,
	1536,
	2512,
	1632);
INSERT INTO S_SS
	VALUES (1048578,
	'G_EVT_PE_G_EVT_NLE_nle_ignored',
	'This subsystem contains the classes for initialization, run, and data for the test.',
	'NLE_IGN',
	1,
	2787908,
	1048578);
INSERT INTO O_OBJ
	VALUES (1048577,
	'Supertype with Polymorphic Events',
	1,
	'SUP',
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
INSERT INTO O_RTIDA
	VALUES (1048577,
	1048577,
	0,
	1048577,
	1048577);
INSERT INTO SM_ISM
	VALUES (1572867,
	1048577);
INSERT INTO SM_SM
	VALUES (1572867,
	'',
	3);
INSERT INTO SM_MOORE
	VALUES (1572867);
INSERT INTO SM_PEVT
	VALUES (1572865,
	1572867,
	0);
INSERT INTO SM_EVT
	VALUES (1572865,
	1572867,
	0,
	1,
	'PE',
	0,
	'',
	'SUP1',
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
	4199,
	1.000000,
	0);
INSERT INTO O_OBJ
	VALUES (1048578,
	'Subtype Ignores',
	2,
	'ISB',
	'',
	1048578);
INSERT INTO O_REF
	VALUES (1048578,
	1048577,
	0,
	1048577,
	1048577,
	1048578,
	1048577,
	1048579,
	1048577,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048579,
	1048578,
	1048577,
	1048577,
	1);
INSERT INTO O_ATTR
	VALUES (1048579,
	1048578,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524296);
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
INSERT INTO SM_NLEVT
	VALUES (2097153,
	2097156,
	0,
	1572865,
	1572867,
	0,
	'');
INSERT INTO SM_SEVT
	VALUES (2097153,
	2097156,
	0);
INSERT INTO SM_EVT
	VALUES (2097153,
	2097156,
	0,
	0,
	'PE',
	0,
	'',
	'SUP1*',
	'');
INSERT INTO SM_STATE
	VALUES (2097153,
	2097156,
	0,
	'Exists',
	1,
	0);
INSERT INTO SM_EIGN
	VALUES (2097153,
	2097153,
	2097156,
	0,
	'');
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
	'',
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
	4199,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (2097154,
	2097153,
	2097153,
	41);
INSERT INTO GD_SHP
	VALUES (2097154,
	1840,
	1312,
	2096,
	1472);
INSERT INTO O_OBJ
	VALUES (1048579,
	'Initialization',
	3,
	'INIT',
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
	'Init_ID',
	'',
	'',
	'Init_ID',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048581,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048581,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048581,
	1048579,
	1048583,
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
	'Init',
	0,
	'',
	'INIT1',
	'');
INSERT INTO SM_STATE
	VALUES (2621441,
	2621445,
	0,
	'Initialize',
	1,
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
	'// Insert initialization action language here.

// create Ignoring Subtype
// create Supertype
create object instance sup of SUP;
// create subtype
create object instance isub of ISB;
// relate them
relate sup to isub across R1;

// create Non-Ignoring Subtype
// create Supertype
create object instance sup of SUP;
// create subtype
create object instance nsub of NSB;
// relate them
relate sup to nsub across R1;

// start the test
generate DRV1 to DRV creator;',
	'');
INSERT INTO SM_CRTXN
	VALUES (2621441,
	2621445,
	2621441,
	2621441);
INSERT INTO SM_TXN
	VALUES (2621441,
	2621445,
	2621441,
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
	1600,
	4199,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (2621442,
	2621441,
	2621441,
	41);
INSERT INTO GD_SHP
	VALUES (2621442,
	1696,
	1344,
	2096,
	1712);
INSERT INTO GD_GE
	VALUES (2621443,
	2621441,
	2621441,
	42);
INSERT INTO GD_CON
	VALUES (2621443,
	2621442,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (2621443,
	0,
	0,
	0,
	0,
	0,
	0,
	1755,
	1289,
	1802,
	1329,
	-48,
	3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621451,
	2621443,
	1856,
	1344,
	1856,
	1264,
	0);
INSERT INTO O_OBJ
	VALUES (1048580,
	'Driver',
	4,
	'DRV',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048584,
	1048580);
INSERT INTO O_BATTR
	VALUES (1048584,
	1048580);
INSERT INTO O_ATTR
	VALUES (1048584,
	1048580,
	0,
	'Driver_ID',
	'',
	'',
	'Driver_ID',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048587,
	1048580);
INSERT INTO O_BATTR
	VALUES (1048587,
	1048580);
INSERT INTO O_ATTR
	VALUES (1048587,
	1048580,
	1048584,
	'timer',
	'',
	'',
	'timer',
	0,
	524304);
INSERT INTO O_NBATTR
	VALUES (1048582,
	1048580);
INSERT INTO O_BATTR
	VALUES (1048582,
	1048580);
INSERT INTO O_ATTR
	VALUES (1048582,
	1048580,
	1048587,
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
	VALUES (1048584,
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
INSERT INTO SM_EVTDI
	VALUES (3145729,
	3145734,
	'num',
	'',
	524291);
INSERT INTO SM_LEVT
	VALUES (3145731,
	3145734,
	0);
INSERT INTO SM_SEVT
	VALUES (3145731,
	3145734,
	0);
INSERT INTO SM_EVT
	VALUES (3145731,
	3145734,
	0,
	2,
	'next',
	0,
	'',
	'DRV2',
	'');
INSERT INTO SM_LEVT
	VALUES (3145733,
	3145734,
	0);
INSERT INTO SM_SEVT
	VALUES (3145733,
	3145734,
	0);
INSERT INTO SM_EVT
	VALUES (3145733,
	3145734,
	0,
	1,
	'begin',
	0,
	'',
	'DRV1',
	'');
INSERT INTO SM_LEVT
	VALUES (3145734,
	3145734,
	0);
INSERT INTO SM_SEVT
	VALUES (3145734,
	3145734,
	0);
INSERT INTO SM_EVT
	VALUES (3145734,
	3145734,
	0,
	3,
	'Failed',
	0,
	'',
	'DRV3',
	'');
INSERT INTO SM_LEVT
	VALUES (3145735,
	3145734,
	0);
INSERT INTO SM_SEVT
	VALUES (3145735,
	3145734,
	0);
INSERT INTO SM_EVT
	VALUES (3145735,
	3145734,
	0,
	4,
	'Test Response',
	0,
	'',
	'DRV4',
	'');
INSERT INTO SM_LEVT
	VALUES (145991401,
	3145734,
	0);
INSERT INTO SM_SEVT
	VALUES (145991401,
	3145734,
	0);
INSERT INTO SM_EVT
	VALUES (145991401,
	3145734,
	0,
	5,
	'Unassigned Parameter Placeholder',
	0,
	'',
	'DRV5',
	'');
INSERT INTO SM_STATE
	VALUES (3145729,
	3145734,
	0,
	'Begin Test',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (3145729,
	3145731,
	3145734,
	0);
INSERT INTO SM_EIGN
	VALUES (3145729,
	3145733,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145733,
	3145734,
	0);
INSERT INTO SM_EIGN
	VALUES (3145729,
	3145734,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145734,
	3145734,
	0);
INSERT INTO SM_EIGN
	VALUES (3145729,
	3145735,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145735,
	3145734,
	0);
INSERT INTO SM_CH
	VALUES (3145729,
	145991401,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	145991401,
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
	'// Start the test.

generate DRV2 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (3145735,
	3145734,
	0,
	'Select Ignoring Supertype and Generate Event',
	3,
	0);
INSERT INTO SM_SEME
	VALUES (3145735,
	3145731,
	3145734,
	0);
INSERT INTO SM_EIGN
	VALUES (3145735,
	3145733,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145735,
	3145733,
	3145734,
	0);
INSERT INTO SM_EIGN
	VALUES (3145735,
	3145734,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145735,
	3145734,
	3145734,
	0);
INSERT INTO SM_SEME
	VALUES (3145735,
	3145735,
	3145734,
	0);
INSERT INTO SM_CH
	VALUES (3145735,
	145991401,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145735,
	145991401,
	3145734,
	0);
INSERT INTO SM_MOAH
	VALUES (3145735,
	3145734,
	3145735);
INSERT INTO SM_AH
	VALUES (3145735,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145735,
	3145734,
	1,
	'// Preparation

result = TIM::timer_cancel(timer_inst_ref:self.timer);
select any sub from instances of ISB;
if (not_empty sub)
  select one sup related by sub->SUP[R1];
  if ( not_empty sup )
    create event instance pass_evt of DRV2 to self;
    self.timer = TIM::timer_start (microseconds:1000000, event_inst:pass_evt);
    generate SUP1* to sup;
  // No response is expected since the test is that the subtype ignores the event
  else
    LOG::LogFailure(message:"Unable to select instance of ignoring supertype.");
  end if;
else
  LOG::LogFailure(message:"Unable to select any instance of ignoring subtype.");
end if;',
	'');
INSERT INTO SM_STATE
	VALUES (3145736,
	3145734,
	0,
	'End Test',
	4,
	1);
INSERT INTO SM_EIGN
	VALUES (3145736,
	3145731,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145736,
	3145731,
	3145734,
	0);
INSERT INTO SM_EIGN
	VALUES (3145736,
	3145733,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145736,
	3145733,
	3145734,
	0);
INSERT INTO SM_EIGN
	VALUES (3145736,
	3145734,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145736,
	3145734,
	3145734,
	0);
INSERT INTO SM_EIGN
	VALUES (3145736,
	3145735,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145736,
	3145735,
	3145734,
	0);
INSERT INTO SM_CH
	VALUES (3145736,
	145991401,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145736,
	145991401,
	3145734,
	0);
INSERT INTO SM_MOAH
	VALUES (3145736,
	3145734,
	3145736);
INSERT INTO SM_AH
	VALUES (3145736,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145736,
	3145734,
	1,
	'// End the Test

ARCH::shutdown();',
	'');
INSERT INTO SM_STATE
	VALUES (3145737,
	3145734,
	0,
	'Select Non Ignoring Subtype and Generate Event',
	2,
	0);
INSERT INTO SM_EIGN
	VALUES (3145737,
	3145731,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145737,
	3145731,
	3145734,
	0);
INSERT INTO SM_EIGN
	VALUES (3145737,
	3145733,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145737,
	3145733,
	3145734,
	0);
INSERT INTO SM_SEME
	VALUES (3145737,
	3145734,
	3145734,
	0);
INSERT INTO SM_SEME
	VALUES (3145737,
	3145735,
	3145734,
	0);
INSERT INTO SM_CH
	VALUES (3145737,
	145991401,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145737,
	145991401,
	3145734,
	0);
INSERT INTO SM_MOAH
	VALUES (3145737,
	3145734,
	3145737);
INSERT INTO SM_AH
	VALUES (3145737,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145737,
	3145734,
	1,
	'// Preparation

select any sub from instances of NSB;
if (not_empty sub)
  select one sup related by sub->SUP[R1];
  if ( not_empty sup )
    create event instance fail_evt of DRV3 to self;
    self.timer = TIM::timer_start (microseconds:1000000, event_inst:fail_evt);
    generate SUP1* to sup;
    // Response is expected since this subtype does not ignore the event
  else
    LOG::LogFailure(message:"Unable to select instance of non ignoring supertype.");
  end if;
else
  LOG::LogFailure(message:"Unable to select any instance of non ignoring subtype.");
end if;',
	'');
INSERT INTO SM_STATE
	VALUES (3145738,
	3145734,
	0,
	'No Response Received',
	5,
	0);
INSERT INTO SM_SEME
	VALUES (3145738,
	3145731,
	3145734,
	0);
INSERT INTO SM_EIGN
	VALUES (3145738,
	3145733,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145738,
	3145733,
	3145734,
	0);
INSERT INTO SM_EIGN
	VALUES (3145738,
	3145734,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145738,
	3145734,
	3145734,
	0);
INSERT INTO SM_EIGN
	VALUES (3145738,
	3145735,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145738,
	3145735,
	3145734,
	0);
INSERT INTO SM_CH
	VALUES (3145738,
	145991401,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145738,
	145991401,
	3145734,
	0);
INSERT INTO SM_MOAH
	VALUES (3145738,
	3145734,
	3145738);
INSERT INTO SM_AH
	VALUES (3145738,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145738,
	3145734,
	1,
	'// Event delivery Failed

LOG::LogFailure(message:"Response from Non Ignoring Subtype not received");
generate DRV2 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (3145739,
	3145734,
	0,
	'Unexpected Response Received',
	6,
	0);
INSERT INTO SM_SEME
	VALUES (3145739,
	3145731,
	3145734,
	0);
INSERT INTO SM_EIGN
	VALUES (3145739,
	3145733,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145739,
	3145733,
	3145734,
	0);
INSERT INTO SM_EIGN
	VALUES (3145739,
	3145734,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145739,
	3145734,
	3145734,
	0);
INSERT INTO SM_EIGN
	VALUES (3145739,
	3145735,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145739,
	3145735,
	3145734,
	0);
INSERT INTO SM_CH
	VALUES (3145739,
	145991401,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145739,
	145991401,
	3145734,
	0);
INSERT INTO SM_MOAH
	VALUES (3145739,
	3145734,
	3145739);
INSERT INTO SM_AH
	VALUES (3145739,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145739,
	3145734,
	1,
	'// Unexpected Event delivery

LOG::LogFailure(message:"Unexpected response from  Ignoring Subtype received");
generate DRV2 to self;',
	'');
INSERT INTO SM_NSTXN
	VALUES (3145731,
	3145734,
	3145735,
	3145731,
	0);
INSERT INTO SM_TXN
	VALUES (3145731,
	3145734,
	3145736,
	0);
INSERT INTO SM_CRTXN
	VALUES (3145738,
	3145734,
	3145733,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145738,
	3145734,
	3145729,
	0);
INSERT INTO SM_NSTXN
	VALUES (3145739,
	3145734,
	3145729,
	3145731,
	0);
INSERT INTO SM_TXN
	VALUES (3145739,
	3145734,
	3145737,
	0);
INSERT INTO SM_NSTXN
	VALUES (3145741,
	3145734,
	3145737,
	3145734,
	0);
INSERT INTO SM_TXN
	VALUES (3145741,
	3145734,
	3145738,
	0);
INSERT INTO SM_NSTXN
	VALUES (3145742,
	3145734,
	3145738,
	3145731,
	0);
INSERT INTO SM_TXN
	VALUES (3145742,
	3145734,
	3145736,
	0);
INSERT INTO SM_NSTXN
	VALUES (3145740,
	3145734,
	3145737,
	3145735,
	0);
INSERT INTO SM_TXN
	VALUES (3145740,
	3145734,
	3145735,
	0);
INSERT INTO SM_NSTXN
	VALUES (3145743,
	3145734,
	3145735,
	3145735,
	0);
INSERT INTO SM_TXN
	VALUES (3145743,
	3145734,
	3145739,
	0);
INSERT INTO SM_NSTXN
	VALUES (3145744,
	3145734,
	3145739,
	3145731,
	0);
INSERT INTO SM_TXN
	VALUES (3145744,
	3145734,
	3145736,
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
	874,
	3407,
	0.615137,
	0);
INSERT INTO GD_GE
	VALUES (3145730,
	3145729,
	3145729,
	41);
INSERT INTO GD_SHP
	VALUES (3145730,
	1632,
	1184,
	2048,
	1328);
INSERT INTO GD_GE
	VALUES (3145736,
	3145729,
	3145735,
	41);
INSERT INTO GD_SHP
	VALUES (3145736,
	1568,
	1840,
	2176,
	2224);
INSERT INTO GD_GE
	VALUES (3145737,
	3145729,
	3145736,
	41);
INSERT INTO GD_SHP
	VALUES (3145737,
	1616,
	2320,
	2048,
	2496);
INSERT INTO GD_GE
	VALUES (3145813,
	3145729,
	3145737,
	41);
INSERT INTO GD_SHP
	VALUES (3145813,
	1552,
	1424,
	2176,
	1776);
INSERT INTO GD_GE
	VALUES (3146036,
	3145729,
	3145738,
	41);
INSERT INTO GD_SHP
	VALUES (3146036,
	944,
	1520,
	1392,
	1696);
INSERT INTO GD_GE
	VALUES (3146122,
	3145729,
	3145739,
	41);
INSERT INTO GD_SHP
	VALUES (3146122,
	2288,
	1936,
	2736,
	2112);
INSERT INTO GD_GE
	VALUES (3145742,
	3145729,
	3145731,
	42);
INSERT INTO GD_CON
	VALUES (3145742,
	3145736,
	3145737,
	0);
INSERT INTO GD_CTXT
	VALUES (3145742,
	0,
	0,
	0,
	0,
	0,
	0,
	1738,
	2251,
	1817,
	2273,
	-1,
	-12,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3146115,
	3145742,
	1824,
	2224,
	1824,
	2320,
	0);
INSERT INTO GD_GE
	VALUES (3145758,
	3145729,
	3145738,
	42);
INSERT INTO GD_CON
	VALUES (3145758,
	3145730,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (3145758,
	0,
	0,
	0,
	0,
	0,
	0,
	1722,
	1132,
	1816,
	1154,
	-2,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3146002,
	3145758,
	1824,
	1184,
	1824,
	1104,
	0);
INSERT INTO GD_GE
	VALUES (3145818,
	3145729,
	3145739,
	42);
INSERT INTO GD_CON
	VALUES (3145818,
	3145730,
	3145813,
	0);
INSERT INTO GD_CTXT
	VALUES (3145818,
	0,
	0,
	0,
	0,
	0,
	0,
	1739,
	1358,
	1818,
	1380,
	0,
	-9,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3146081,
	3145818,
	1824,
	1328,
	1824,
	1424,
	0);
INSERT INTO GD_GE
	VALUES (3145820,
	3145729,
	3145740,
	42);
INSERT INTO GD_CON
	VALUES (3145820,
	3145813,
	3145736,
	0);
INSERT INTO GD_CTXT
	VALUES (3145820,
	0,
	0,
	0,
	0,
	0,
	0,
	1831,
	1778,
	1916,
	1818,
	98,
	-12,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3146108,
	3145820,
	1824,
	1776,
	1824,
	1840,
	0);
INSERT INTO GD_GE
	VALUES (3146037,
	3145729,
	3145741,
	42);
INSERT INTO GD_CON
	VALUES (3146037,
	3145813,
	3146036,
	0);
INSERT INTO GD_CTXT
	VALUES (3146037,
	0,
	0,
	0,
	0,
	0,
	0,
	1406,
	1588,
	1501,
	1610,
	-21,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3146079,
	3146037,
	1552,
	1616,
	1392,
	1616,
	0);
INSERT INTO GD_GE
	VALUES (3146039,
	3145729,
	3145742,
	42);
INSERT INTO GD_CON
	VALUES (3146039,
	3146036,
	3145737,
	0);
INSERT INTO GD_CTXT
	VALUES (3146039,
	0,
	0,
	0,
	0,
	0,
	0,
	1131,
	2135,
	1210,
	2157,
	96,
	88,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3146120,
	3146039,
	1120,
	1696,
	1120,
	2416,
	0);
INSERT INTO GD_LS
	VALUES (3146121,
	3146039,
	1120,
	2416,
	1616,
	2416,
	3146120);
INSERT INTO GD_GE
	VALUES (3146123,
	3145729,
	3145743,
	42);
INSERT INTO GD_CON
	VALUES (3146123,
	3145736,
	3146122,
	0);
INSERT INTO GD_CTXT
	VALUES (3146123,
	0,
	0,
	0,
	0,
	0,
	0,
	2187,
	1979,
	2272,
	2019,
	-5,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3146124,
	3146123,
	2176,
	2032,
	2288,
	2032,
	0);
INSERT INTO GD_GE
	VALUES (3146125,
	3145729,
	3145744,
	42);
INSERT INTO GD_CON
	VALUES (3146125,
	3146122,
	3145737,
	0);
INSERT INTO GD_CTXT
	VALUES (3146125,
	0,
	0,
	0,
	0,
	0,
	0,
	2439,
	2295,
	2518,
	2317,
	188,
	-93,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3146132,
	3146125,
	2528,
	2112,
	2528,
	2416,
	0);
INSERT INTO GD_LS
	VALUES (3146133,
	3146125,
	2528,
	2416,
	2048,
	2416,
	3146132);
INSERT INTO O_OBJ
	VALUES (1048581,
	'Subtype Does Not Ignore',
	5,
	'NSB',
	'',
	1048578);
INSERT INTO O_REF
	VALUES (1048581,
	1048577,
	0,
	1048577,
	1048577,
	1048579,
	1048577,
	1048585,
	1048578,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048585,
	1048581,
	1048577,
	1048577,
	1);
INSERT INTO O_ATTR
	VALUES (1048585,
	1048581,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048586,
	1048581);
INSERT INTO O_BATTR
	VALUES (1048586,
	1048581);
INSERT INTO O_ATTR
	VALUES (1048586,
	1048581,
	1048585,
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
	VALUES (1048585,
	1048581,
	0);
INSERT INTO SM_ISM
	VALUES (7864335,
	1048581);
INSERT INTO SM_SM
	VALUES (7864335,
	'',
	15);
INSERT INTO SM_MOORE
	VALUES (7864335);
INSERT INTO SM_NLEVT
	VALUES (7864321,
	7864335,
	0,
	1572865,
	1572867,
	0,
	'');
INSERT INTO SM_SEVT
	VALUES (7864321,
	7864335,
	0);
INSERT INTO SM_EVT
	VALUES (7864321,
	7864335,
	0,
	0,
	'PE',
	0,
	'',
	'SUP1*',
	'');
INSERT INTO SM_STATE
	VALUES (7864321,
	7864335,
	0,
	'Exists',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (7864321,
	7864321,
	7864335,
	0);
INSERT INTO SM_MOAH
	VALUES (7864321,
	7864335,
	7864321);
INSERT INTO SM_AH
	VALUES (7864321,
	7864335);
INSERT INTO SM_ACT
	VALUES (7864321,
	7864335,
	1,
	'// Reply to test event

select any drv from instances of DRV;

if (not_empty drv)
  generate DRV4:''Test Response'' to drv;
else
  LOG::LogFailure(message:"Unable to select driver for response.");
end if;',
	'');
INSERT INTO SM_NSTXN
	VALUES (7864321,
	7864335,
	7864321,
	7864321,
	0);
INSERT INTO SM_TXN
	VALUES (7864321,
	7864335,
	7864321,
	0);
INSERT INTO GD_MD
	VALUES (7864321,
	8,
	7864335,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1470,
	4199,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (7864322,
	7864321,
	7864321,
	41);
INSERT INTO GD_SHP
	VALUES (7864322,
	1616,
	1296,
	2112,
	1536);
INSERT INTO GD_GE
	VALUES (7864323,
	7864321,
	7864321,
	42);
INSERT INTO GD_CON
	VALUES (7864323,
	7864322,
	7864322,
	0);
INSERT INTO GD_CTXT
	VALUES (7864323,
	0,
	0,
	0,
	0,
	0,
	0,
	2042,
	1589,
	2120,
	1611,
	-1,
	1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (7864448,
	7864323,
	2112,
	1456,
	2176,
	1456,
	0);
INSERT INTO GD_LS
	VALUES (7864449,
	7864323,
	2176,
	1456,
	2176,
	1616,
	7864448);
INSERT INTO GD_LS
	VALUES (7864450,
	7864323,
	2176,
	1616,
	1984,
	1616,
	7864449);
INSERT INTO GD_LS
	VALUES (7864451,
	7864323,
	1984,
	1616,
	1984,
	1536,
	7864450);
INSERT INTO R_SUBSUP
	VALUES (1048577);
INSERT INTO R_REL
	VALUES (1048577,
	1,
	'',
	1048578);
INSERT INTO R_SUPER
	VALUES (1048577,
	1048577,
	1048577);
INSERT INTO R_RTO
	VALUES (1048577,
	1048577,
	1048577,
	0);
INSERT INTO R_OIR
	VALUES (1048577,
	1048577,
	1048577,
	0);
INSERT INTO R_SUB
	VALUES (1048578,
	1048577,
	1048578);
INSERT INTO R_RGO
	VALUES (1048578,
	1048577,
	1048578);
INSERT INTO R_OIR
	VALUES (1048578,
	1048577,
	1048578,
	0);
INSERT INTO R_SUB
	VALUES (1048581,
	1048577,
	1048579);
INSERT INTO R_RGO
	VALUES (1048581,
	1048577,
	1048579);
INSERT INTO R_OIR
	VALUES (1048581,
	1048577,
	1048579,
	0);
INSERT INTO GD_MD
	VALUES (1048581,
	5,
	1048578,
	11,
	1,
	0,
	1,
	1,
	0,
	12,
	1824,
	4232,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (1048588,
	1048581,
	1048577,
	21);
INSERT INTO GD_SHP
	VALUES (1048588,
	2048,
	1088,
	2256,
	1232);
INSERT INTO GD_GE
	VALUES (1048590,
	1048581,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048590,
	1888,
	1344,
	2096,
	1504);
INSERT INTO GD_GE
	VALUES (1048606,
	1048581,
	1048579,
	21);
INSERT INTO GD_SHP
	VALUES (1048606,
	1888,
	1552,
	2096,
	1696);
INSERT INTO GD_GE
	VALUES (1048608,
	1048581,
	1048580,
	21);
INSERT INTO GD_SHP
	VALUES (1048608,
	2192,
	1552,
	2400,
	1696);
INSERT INTO GD_GE
	VALUES (1048657,
	1048581,
	1048581,
	21);
INSERT INTO GD_SHP
	VALUES (1048657,
	2192,
	1344,
	2400,
	1504);
INSERT INTO GD_GE
	VALUES (1048592,
	1048581,
	1048577,
	24);
INSERT INTO GD_CON
	VALUES (1048592,
	1048588,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (1048592,
	2058,
	1236,
	2131,
	1276,
	-91,
	-6,
	0,
	0,
	0,
	0,
	0,
	0,
	2164,
	1278,
	2188,
	1300,
	15,
	-6);
INSERT INTO GD_LS
	VALUES (1048654,
	1048592,
	2144,
	1232,
	2144,
	1312,
	0);
INSERT INTO GD_GE
	VALUES (1048594,
	1048581,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (1048594,
	1048590,
	1048592,
	0);
INSERT INTO GD_CTXT
	VALUES (1048594,
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
	VALUES (1048720,
	1048594,
	1984,
	1344,
	1984,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (1048721,
	1048594,
	1984,
	1312,
	2144,
	1312,
	1048720);
INSERT INTO GD_GE
	VALUES (1048659,
	1048581,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (1048659,
	1048657,
	1048592,
	0);
INSERT INTO GD_CTXT
	VALUES (1048659,
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
	VALUES (1048716,
	1048659,
	2304,
	1344,
	2304,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (1048717,
	1048659,
	2304,
	1312,
	2144,
	1312,
	1048716);
