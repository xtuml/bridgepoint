-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (248972094,
	'G_ALL_select_where_enum',
	'This test is designed to use an enumerated type, in various "select...where..."
statements.  The test covers selecting one, any, and many instances in functions,
bridges, class operations, instance operations, MDAs, and state action language.

The values that are compared are the class'' identifying attribute as well as the
class'' referential attribute, both of type enumeration, in the following ways:

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
	248972094,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	248972094,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	248972094,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	248972094,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	248972094,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	248972094,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	248972094,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	248972094,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	248972094,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	248972094,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	248972094,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	248972094,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	248972094,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	248972094,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	248972094,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	248972094,
	'inst_ref<Timer>',
	'');
INSERT INTO S_UDT
	VALUES (524305,
	524294,
	0);
INSERT INTO S_DT
	VALUES (524305,
	248972094,
	'arbitrary_id',
	'Arbitrary ID with core data type of unique_id.');
INSERT INTO S_EDT
	VALUES (524306);
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
	'yellow',
	'',
	524306);
INSERT INTO S_ENUM
	VALUES (524292,
	'green',
	'',
	524306);
INSERT INTO S_ENUM
	VALUES (524293,
	'white',
	'',
	524306);
INSERT INTO S_ENUM
	VALUES (524294,
	'black',
	'',
	524306);
INSERT INTO S_ENUM
	VALUES (524295,
	'purple',
	'',
	524306);
INSERT INTO S_DT
	VALUES (524306,
	248972094,
	'colors',
	'');
INSERT INTO S_SYNC
	VALUES (524289,
	248972094,
	'compare_enums',
	'',
	'LOG::LogInfo(message:"In function compare_enums.");

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select any.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select any sel from instances of SEL where selected.sel2_ID == colors::green;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using literal.");
end if;

a_color = colors::green;
select any sel from instances of SEL where selected.sel2_ID == a_color;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using local.");
end if;

select any sel2 from instances of SEL2;
select any sel from instances of SEL where selected.sel2_ID == sel2.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using base attribute.");
end if;

select any sel from instances of SEL where selected.sel_ID == colors::yellow;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using identifier and literal.");
end if;

a_color = colors::yellow;
select any sel from instances of SEL where selected.sel_ID == a_color;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select any sel from instances of SEL where selected.sel_ID == sel1.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select many.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select many sels from instances of SEL where colors::green == selected.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instances of ''Select'' using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instances of ''Select'' using literal.");
end if;

a_color = colors::green;
select many sels from instances of SEL where a_color == selected.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instances of ''Select'' using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using local.");
end if;

select many sels from instances of SEL where sel2.sel2_ID == selected.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instances of ''Select'' using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instances of ''Select'' using base attribute.");
end if;

select many sels from instances of SEL where selected.sel_ID == colors::yellow;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using identifier and literal.");
end if;

a_color = colors::yellow;
select many sels from instances of SEL where selected.sel_ID == a_color;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select many sels from instances of SEL where selected.sel_ID == sel1.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select one related by.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select any sel_one2 from instances of SEL_ONE2;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one2_ID == colors::blue;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one instance of ''Select One'' using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select one instance of ''Select One'' using literal.");
end if;

a_color = colors::blue;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one2_ID == a_color;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one instance of ''Select One'' using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select one instance of ''Select One'' using local.");
end if;

select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one2_ID == sel_one2.sel_one2_ID;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one instance of ''Select One'' using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select one instance of ''Select One'' using base attribute.");
end if;

select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one_ID == colors::purple;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one related by ''Select One Two'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select one relate dby ''Select One Two'' using identifier and literal.");
end if;

a_color = colors::purple;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one_ID == a_color;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one related by ''Select One Two'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select one related by  ''Select One Two'' using identifier and local.");
end if;

select any sel_one1 from instances of SEL_ONE;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one_ID == sel_one1.sel_one_ID;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one related by ''Select One Two'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select one related by ''Select One Two'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select any related by.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select any sel related by sel2->SEL[R1] where colors::green == selected.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' related by using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' related by using literal.");
end if;

a_color = colors::green;
select any sel related by sel2->SEL[R1] where a_color == selected.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' related by using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' related by using local.");
end if;

select any sel related by sel2->SEL[R1] where sel2.sel2_ID == selected.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' related by using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' related by using base attribute.");
end if;

select any sel related by sel2->SEL[R1] where colors::yellow == selected.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any related by ''Select Two'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any related by ''Select Two'' using identifier and literal.");
end if;

a_color = colors::yellow;
select any sel related by sel2->SEL[R1] where a_color == selected.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any related by ''Select Two'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any related by ''Select Two'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select any sel related by sel2->SEL[R1] where sel1.sel_ID == selected.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any related by ''Select Two'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any related by ''Select Two'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select many related by.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select many sels related by sel2->SEL[R1] where selected.sel2_ID == colors::green;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' related by using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' related by using literal.");
end if;

a_color = colors::green;
select many sels related by sel2->SEL[R1] where selected.sel2_ID == a_color;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' related by using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' related by using local.");
end if;

select many sels related by sel2->SEL[R1] where selected.sel2_ID == sel2.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected an instance of ''Select'' related by using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select an instance of ''Select'' related by using base attribute.");
end if;

select many sels related by sel2->SEL[R1] where colors::yellow == selected.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many related by ''Select Two'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many related by ''Select Two'' using identifier and literal.");
end if;

a_color = colors::yellow;
select many sels related by sel2->SEL[R1] where a_color == selected.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many related by ''Select Two'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many related by ''Select Two'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select many sels related by sel2->SEL[R1] where sel1.sel_ID == selected.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many related by ''Select Two'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select many related by ''Select Two'' using identifier and base attribute.");
end if;
',
	524289,
	1);
INSERT INTO S_EE
	VALUES (524289,
	'Time',
	'',
	'TIM',
	248972094);
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
	248972094);
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
	248972094);
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
INSERT INTO S_EE
	VALUES (524292,
	'Bridge',
	'',
	'B',
	248972094);
INSERT INTO S_BRG
	VALUES (524316,
	524292,
	'compare_enums',
	'',
	0,
	524289,
	'LOG::LogInfo(message:"In bridge compare_enums.");

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select any.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select any sel from instances of SEL where selected.sel2_ID == colors::green;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using literal.");
end if;

a_color = colors::green;
select any sel from instances of SEL where selected.sel2_ID == a_color;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using local.");
end if;

select any sel2 from instances of SEL2;
select any sel from instances of SEL where selected.sel2_ID == sel2.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using base attribute.");
end if;

select any sel from instances of SEL where selected.sel_ID == colors::yellow;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using identifier and literal.");
end if;

a_color = colors::yellow;
select any sel from instances of SEL where selected.sel_ID == a_color;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select any sel from instances of SEL where selected.sel_ID == sel1.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select many.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select many sels from instances of SEL where colors::green == selected.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instances of ''Select'' using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instances of ''Select'' using literal.");
end if;

a_color = colors::green;
select many sels from instances of SEL where a_color == selected.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instances of ''Select'' using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using local.");
end if;

select many sels from instances of SEL where sel2.sel2_ID == selected.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instances of ''Select'' using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instances of ''Select'' using base attribute.");
end if;

select many sels from instances of SEL where selected.sel_ID == colors::yellow;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using identifier and literal.");
end if;

a_color = colors::yellow;
select many sels from instances of SEL where selected.sel_ID == a_color;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select many sels from instances of SEL where selected.sel_ID == sel1.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select one related by.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select any sel_one2 from instances of SEL_ONE2;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one2_ID == colors::blue;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one instance of ''Select One'' using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select one instance of ''Select One'' using literal.");
end if;

a_color = colors::blue;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one2_ID == a_color;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one instance of ''Select One'' using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select one instance of ''Select One'' using local.");
end if;

select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one2_ID == sel_one2.sel_one2_ID;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one instance of ''Select One'' using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select one instance of ''Select One'' using base attribute.");
end if;

select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one_ID == colors::purple;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one related by ''Select One Two'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select one relate dby ''Select One Two'' using identifier and literal.");
end if;

a_color = colors::purple;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one_ID == a_color;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one related by ''Select One Two'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select one related by  ''Select One Two'' using identifier and local.");
end if;

select any sel_one1 from instances of SEL_ONE;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one_ID == sel_one1.sel_one_ID;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one related by ''Select One Two'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select one related by ''Select One Two'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select any related by.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select any sel related by sel2->SEL[R1] where colors::green == selected.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' related by using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' related by using literal.");
end if;

a_color = colors::green;
select any sel related by sel2->SEL[R1] where a_color == selected.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' related by using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' related by using local.");
end if;

select any sel related by sel2->SEL[R1] where sel2.sel2_ID == selected.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' related by using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' related by using base attribute.");
end if;

select any sel related by sel2->SEL[R1] where colors::yellow == selected.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any related by ''Select Two'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any related by ''Select Two'' using identifier and literal.");
end if;

a_color = colors::yellow;
select any sel related by sel2->SEL[R1] where a_color == selected.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any related by ''Select Two'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any related by ''Select Two'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select any sel related by sel2->SEL[R1] where sel1.sel_ID == selected.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any related by ''Select Two'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any related by ''Select Two'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select many related by.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select many sels related by sel2->SEL[R1] where selected.sel2_ID == colors::green;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' related by using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' related by using literal.");
end if;

a_color = colors::green;
select many sels related by sel2->SEL[R1] where selected.sel2_ID == a_color;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' related by using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' related by using local.");
end if;

select many sels related by sel2->SEL[R1] where selected.sel2_ID == sel2.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected an instance of ''Select'' related by using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select an instance of ''Select'' related by using base attribute.");
end if;

select many sels related by sel2->SEL[R1] where colors::yellow == selected.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many related by ''Select Two'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many related by ''Select Two'' using identifier and literal.");
end if;

a_color = colors::yellow;
select many sels related by sel2->SEL[R1] where a_color == selected.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many related by ''Select Two'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many related by ''Select Two'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select many sels related by sel2->SEL[R1] where sel1.sel_ID == selected.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many related by ''Select Two'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select many related by ''Select Two'' using identifier and base attribute.");
end if;
',
	1);
INSERT INTO GD_MD
	VALUES (524289,
	1,
	248972094,
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
	VALUES (524306,
	524289,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524306,
	2144,
	1392,
	2304,
	1488);
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
	524292,
	12);
INSERT INTO GD_SHP
	VALUES (524296,
	1936,
	1392,
	2096,
	1488);
INSERT INTO S_SS
	VALUES (1048578,
	'G_STE_select_where_enum',
	'This subsystem contains the classes for initialization, run, and data for the test.',
	'',
	1,
	248972094,
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
	'Initialize',
	1,
	0);
INSERT INTO SM_EIGN
	VALUES (1572865,
	1572865,
	1572867,
	0,
	'');
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
	'// Insert initialization action language here.

// Instance of Select Two
create object instance sel2 of SEL2;
sel2.sel2_ID = colors::green;

// Instances of Select
create object instance sel of SEL;
sel.sel_ID = colors::yellow;
relate sel to sel2 across R1;

// Instances of Select One
create object instance sel_one of SEL_ONE;
sel_one.sel_one_ID = colors::purple;

// Instances of Select One Two
create object instance sel_one2 of SEL_ONE2;
sel_one2.sel_one2_ID = colors::blue;

relate sel_one to sel_one2 across R2;

// Instance of CL.
create object instance class of CL;
class.cl_ID = 1;

generate DRV1 to DRV creator;',
	'');
INSERT INTO SM_CRTXN
	VALUES (1572865,
	1572867,
	1572865,
	1572865);
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
	VALUES (1048609,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048609,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048609,
	1048578,
	1048579,
	'count',
	'',
	'',
	'count',
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
	1048609,
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
INSERT INTO SM_EVTDI
	VALUES (2097155,
	2097156,
	'num',
	'',
	524291);
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
	'begin',
	0,
	'',
	'DRV1',
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
	2,
	'end',
	0,
	'',
	'DRV2',
	'');
INSERT INTO SM_LEVT
	VALUES (2097155,
	2097156,
	0);
INSERT INTO SM_SEVT
	VALUES (2097155,
	2097156,
	0);
INSERT INTO SM_EVT
	VALUES (2097155,
	2097156,
	0,
	3,
	'next',
	0,
	'',
	'DRV3',
	'');
INSERT INTO SM_LEVT
	VALUES (2097156,
	2097156,
	0);
INSERT INTO SM_SEVT
	VALUES (2097156,
	2097156,
	0);
INSERT INTO SM_EVT
	VALUES (2097156,
	2097156,
	0,
	4,
	'count',
	0,
	'',
	'DRV4',
	'');
INSERT INTO SM_LEVT
	VALUES (2097159,
	2097156,
	0);
INSERT INTO SM_SEVT
	VALUES (2097159,
	2097156,
	0);
INSERT INTO SM_EVT
	VALUES (2097159,
	2097156,
	0,
	7,
	'wait',
	0,
	'',
	'DRV7',
	'');
INSERT INTO SM_LEVT
	VALUES (2097160,
	2097156,
	0);
INSERT INTO SM_SEVT
	VALUES (2097160,
	2097156,
	0);
INSERT INTO SM_EVT
	VALUES (2097160,
	2097156,
	0,
	5,
	'Continue1',
	0,
	'',
	'DRV5',
	'');
INSERT INTO SM_LEVT
	VALUES (2097161,
	2097156,
	0);
INSERT INTO SM_SEVT
	VALUES (2097161,
	2097156,
	0);
INSERT INTO SM_EVT
	VALUES (2097161,
	2097156,
	0,
	6,
	'Continue2',
	0,
	'',
	'DRV6',
	'');
INSERT INTO SM_STATE
	VALUES (2097153,
	2097156,
	0,
	'Begin Test',
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
INSERT INTO SM_EIGN
	VALUES (2097153,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097154,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097153,
	2097155,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097153,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097153,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097153,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097153,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097161,
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
	'// Start the test.
generate DRV3 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097154,
	2097156,
	0,
	'End Test',
	2,
	1);
INSERT INTO SM_EIGN
	VALUES (2097154,
	2097153,
	2097156,
	0,
	'');
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
INSERT INTO SM_EIGN
	VALUES (2097154,
	2097155,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097155,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097154,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097154,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097154,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097154,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097161,
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
	'// End the test.
::compare_enums();
B::compare_enums();
CL::class_compare_enums();
select any class from instances of CL;
class.instance_compare_enums();
x = class.compare_enums;

ARCH::shutdown();',
	'');
INSERT INTO SM_STATE
	VALUES (2097156,
	2097156,
	0,
	'Select One Related By',
	3,
	0);
INSERT INTO SM_EIGN
	VALUES (2097156,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097156,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097154,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097156,
	2097155,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097156,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097156,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097156,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097156,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097161,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097156,
	2097156,
	2097156);
INSERT INTO SM_AH
	VALUES (2097156,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097156,
	2097156,
	1,
	'// Preparation
a_color=colors::blue;
b_color=colors::purple;
select any sel_1 from instances of SEL_ONE;
select any sel_2 from instances of SEL_ONE2;

// Do select one related by - referential.
select one sel1 related by sel_2->SEL_ONE[R2] where selected.sel_one2_ID == colors::blue;
select one sel2 related by sel_2->SEL_ONE[R2] where selected.sel_one2_ID == a_color;
select one sel3 related by sel_2->SEL_ONE[R2] where selected.sel_one2_ID == sel_2.sel_one2_ID;

// Do select one related by - identifier.
select one sel4 related by sel_2->SEL_ONE[R2] where selected.sel_one_ID == colors::purple;
select one sel5 related by sel_2->SEL_ONE[R2] where selected.sel_one_ID == b_color;
select one sel6 related by sel_2->SEL_ONE[R2] where selected.sel_one_ID == sel_1.sel_one_ID;

if( empty sel1 )
  LOG::LogFailure( message:"Unable to select one related by ''Select One Two'' using referential and literal.");
end if;

if( empty sel2 )
  LOG::LogFailure( message:"Unable to select one related by ''Select One Two'' using referential and local.");
end if;

if( empty sel3 )
  LOG::LogFailure( message:"Unable to select one related by ''Select One Two'' using referential and attribute.");
end if;

if( empty sel4 )
  LOG::LogFailure( message:"Unable to select one related by ''Select One Two'' using identifier and literal.");
end if;

if( empty sel5 )
  LOG::LogFailure( message:"Unable to select one related by ''Select One Two'' using identifier and local.");
end if;

if( empty sel6 )
  LOG::LogFailure( message:"Unable to select one related by ''Select One Two'' using identifier and attribute.");
end if;

generate SEL_ONE1 to sel_1;',
	'');
INSERT INTO SM_STATE
	VALUES (2097157,
	2097156,
	0,
	'Select Any Related By',
	4,
	0);
INSERT INTO SM_EIGN
	VALUES (2097157,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097157,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097154,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097157,
	2097155,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097157,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097157,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097157,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097157,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097161,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097157,
	2097156,
	2097157);
INSERT INTO SM_AH
	VALUES (2097157,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097157,
	2097156,
	1,
	'// Preparation
a_color=colors::green;
b_color=colors::yellow;
select any sel_1 from instances of SEL;
select any sel_2 from instances of SEL2;

// Do select any related by - referential.
select any sel1 related by sel_2->SEL[R1] where selected.sel2_ID == colors::green;
select any sel2 related by sel_2->SEL[R1] where selected.sel2_ID == a_color;
select any sel3 related by sel_2->SEL[R1] where selected.sel2_ID == sel_2.sel2_ID;

// Do select any related by - identifier.
select any sel4 related by sel_2->SEL[R1] where selected.sel_ID == colors::yellow;
select any sel5 related by sel_2->SEL[R1] where selected.sel_ID == b_color;
select any sel6 related by sel_2->SEL[R1] where selected.sel_ID == sel_1.sel_ID;

if( empty sel1 )
  LOG::LogFailure( message:"Unable to select any related by ''Select Two'' using referential and literal.");
end if;

if( empty sel2 )
  LOG::LogFailure( message:"Unable to select any related by ''Select Two'' using referential and local.");
end if;

if( empty sel3 )
  LOG::LogFailure( message:"Unable to select any related by ''Select Two'' using referential and attribute.");
end if;

if( empty sel4 )
  LOG::LogFailure( message:"Unable to select any related by ''Select Two'' using identifier and literal.");
end if;

if( empty sel5 )
  LOG::LogFailure( message:"Unable to select any related by ''Select Two'' using identifier and local.");
end if;

if( empty sel6 )
  LOG::LogFailure( message:"Unable to select any related by ''Select Two'' using identifier and attribute.");
end if;

generate SEL3 to sel_1;',
	'');
INSERT INTO SM_STATE
	VALUES (2097158,
	2097156,
	0,
	'Select Many Related By',
	5,
	0);
INSERT INTO SM_EIGN
	VALUES (2097158,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097158,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097154,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097158,
	2097155,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097155,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097158,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097158,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097158,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097158,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097161,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097158,
	2097156,
	2097158);
INSERT INTO SM_AH
	VALUES (2097158,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097158,
	2097156,
	1,
	'// Preparation
a_color=colors::green;
b_color=colors::yellow;
select any sel_1 from instances of SEL;
select any sel_2 from instances of SEL2;

// Do select many related by - referential.
select many sel1 related by sel_2->SEL[R1] where selected.sel2_ID == colors::green;
select many sel2 related by sel_2->SEL[R1] where selected.sel2_ID == a_color;
select many sel3 related by sel_2->SEL[R1] where selected.sel2_ID == sel_2.sel2_ID;

// Do select many related by - identifier.
select many sel4 related by sel_2->SEL[R1] where selected.sel_ID == colors::yellow;
select many sel5 related by sel_2->SEL[R1] where selected.sel_ID == b_color;
select many sel6 related by sel_2->SEL[R1] where selected.sel_ID == sel_1.sel_ID;

if( empty sel1 )
  LOG::LogFailure( message:"Unable to select many related by ''Select Two'' using referential and literal.");
end if;

if( empty sel2 )
  LOG::LogFailure( message:"Unable to select many related by ''Select Two'' using referential and local.");
end if;

if( empty sel3 )
  LOG::LogFailure( message:"Unable to select many related by ''Select Two'' using referential and attribute.");
end if;

if( empty sel4 )
  LOG::LogFailure( message:"Unable to select many related by ''Select Two'' using identifier and literal.");
end if;

if( empty sel5 )
  LOG::LogFailure( message:"Unable to select many related by ''Select Two'' using identifier and local.");
end if;

if( empty sel6 )
  LOG::LogFailure( message:"Unable to select many related by ''Select Two'' using identifier and attribute.");
end if;

for each sel in sel1
  generate SEL2 to sel;
end for;

generate DRV4(num:1) to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097159,
	2097156,
	0,
	'Waiting',
	6,
	0);
INSERT INTO SM_EIGN
	VALUES (2097159,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097159,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097154,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097159,
	2097155,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097155,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097159,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097159,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097159,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097159,
	2097160,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097159,
	2097161,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097159,
	2097156,
	2097159);
INSERT INTO SM_AH
	VALUES (2097159,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097159,
	2097156,
	1,
	'if ( self.count >= 1 )
  self.count = 0;
  if ( rcvd_evt.num == 0 )
    generate DRV6 to self;
  else
    generate DRV5 to self;
  end if;
else
  self.count = self.count+1;
end if;

',
	'');
INSERT INTO SM_STATE
	VALUES (2097160,
	2097156,
	0,
	'Select Any',
	7,
	0);
INSERT INTO SM_EIGN
	VALUES (2097160,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097160,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097154,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097160,
	2097155,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097160,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097160,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097160,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097160,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097161,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097160,
	2097156,
	2097160);
INSERT INTO SM_AH
	VALUES (2097160,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097160,
	2097156,
	1,
	'// Preparation
a_color=colors::green;
b_color=colors::yellow;
select any sel_1 from instances of SEL;
select any sel_2 from instances of SEL2;

// Do select any - referential.
select any sel1 from instances of SEL where selected.sel2_ID == colors::green;
select any sel2 from instances of SEL where selected.sel2_ID == a_color;
select any sel3 from instances of SEL where selected.sel2_ID == sel_2.sel2_ID;

// Do select any - identifier.
select any sel4 from instances of SEL where selected.sel_ID == colors::yellow;
select any sel5 from instances of SEL where selected.sel_ID == b_color;
select any sel6 from instances of SEL where selected.sel_ID == sel_1.sel_ID;

if( empty sel1 )
  LOG::LogFailure( message:"Unable to select any instance of ''Select'' using referential and literal.");
end if;

if( empty sel2 )
  LOG::LogFailure( message:"Unable to select any instance of ''Select'' using referential and local.");
end if;

if( empty sel3 )
  LOG::LogFailure( message:"Unable to select any instance of ''Select'' using referential and attribute.");
end if;

if( empty sel4 )
  LOG::LogFailure( message:"Unable to select any instance of ''Select'' using identifier and literal.");
end if;

if( empty sel5 )
  LOG::LogFailure( message:"Unable to select any instance of ''Select'' using identifier and local.");
end if;

if( empty sel6 )
  LOG::LogFailure( message:"Unable to select any instance of ''Select'' using identifier and attribute.");
end if;

generate SEL1 to sel_1;',
	'');
INSERT INTO SM_STATE
	VALUES (2097161,
	2097156,
	0,
	'Select Many',
	8,
	0);
INSERT INTO SM_EIGN
	VALUES (2097161,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097161,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097154,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097161,
	2097155,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097155,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097161,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097161,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097161,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097161,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097161,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097161,
	2097156,
	2097161);
INSERT INTO SM_AH
	VALUES (2097161,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097161,
	2097156,
	1,
	'// Preparation
a_color=colors::green;
b_color=colors::yellow;
select any sel_1 from instances of SEL;
select any sel_2 from instances of SEL2;

// Do select any - referential.
select many sel1 from instances of SEL where selected.sel2_ID == colors::green;
select many sel2 from instances of SEL where selected.sel2_ID == a_color;
select many sel3 from instances of SEL where selected.sel2_ID == sel_2.sel2_ID;

// Do select any - identifier.
select many sel4 from instances of SEL where selected.sel_ID == colors::yellow;
select many sel5 from instances of SEL where selected.sel_ID == b_color;
select many sel6 from instances of SEL where selected.sel_ID == sel_1.sel_ID;

if( empty sel1 )
  LOG::LogFailure( message:"Unable to select many instance of ''Select'' using referential and literal.");
end if;

if( empty sel2 )
  LOG::LogFailure( message:"Unable to select many instance of ''Select'' using referential and local.");
end if;

if( empty sel3 )
  LOG::LogFailure( message:"Unable to select many instance of ''Select'' using referential and attribute.");
end if;

if( empty sel4 )
  LOG::LogFailure( message:"Unable to select many instance of ''Select'' using identifier and literal.");
end if;

if( empty sel5 )
  LOG::LogFailure( message:"Unable to select many instance of ''Select'' using identifier and local.");
end if;

if( empty sel6 )
  LOG::LogFailure( message:"Unable to select many instance of ''Select'' using identifier and attribute.");
end if;

for each sel in sel1
  generate SEL2 to sel;
end for;

generate DRV4(num:0) to self;',
	'');
INSERT INTO SM_CRTXN
	VALUES (2097153,
	2097156,
	2097153,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097153,
	2097156,
	2097153,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097158,
	2097156,
	2097157,
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097158,
	2097156,
	2097158,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097157,
	2097156,
	2097156,
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097157,
	2097156,
	2097157,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097164,
	2097156,
	2097158,
	2097156,
	0);
INSERT INTO SM_TXN
	VALUES (2097164,
	2097156,
	2097159,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097163,
	2097156,
	2097159,
	2097156,
	0);
INSERT INTO SM_TXN
	VALUES (2097163,
	2097156,
	2097159,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097168,
	2097156,
	2097159,
	2097160,
	0);
INSERT INTO SM_TXN
	VALUES (2097168,
	2097156,
	2097154,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097170,
	2097156,
	2097153,
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097170,
	2097156,
	2097160,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097171,
	2097156,
	2097160,
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097171,
	2097156,
	2097161,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097174,
	2097156,
	2097161,
	2097156,
	0);
INSERT INTO SM_TXN
	VALUES (2097174,
	2097156,
	2097159,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097175,
	2097156,
	2097159,
	2097161,
	0);
INSERT INTO SM_TXN
	VALUES (2097175,
	2097156,
	2097156,
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
	700,
	3571,
	0.372684,
	0);
INSERT INTO GD_GE
	VALUES (2097154,
	2097153,
	2097153,
	41);
INSERT INTO GD_SHP
	VALUES (2097154,
	720,
	784,
	1296,
	960);
INSERT INTO GD_GE
	VALUES (2097155,
	2097153,
	2097154,
	41);
INSERT INTO GD_SHP
	VALUES (2097155,
	2256,
	1552,
	2832,
	1712);
INSERT INTO GD_GE
	VALUES (2097165,
	2097153,
	2097156,
	41);
INSERT INTO GD_SHP
	VALUES (2097165,
	2256,
	784,
	2832,
	960);
INSERT INTO GD_GE
	VALUES (2097168,
	2097153,
	2097157,
	41);
INSERT INTO GD_SHP
	VALUES (2097168,
	2256,
	1056,
	2832,
	1216);
INSERT INTO GD_GE
	VALUES (2097169,
	2097153,
	2097158,
	41);
INSERT INTO GD_SHP
	VALUES (2097169,
	2256,
	1312,
	2832,
	1472);
INSERT INTO GD_GE
	VALUES (2097311,
	2097153,
	2097159,
	41);
INSERT INTO GD_SHP
	VALUES (2097311,
	1504,
	1312,
	2080,
	1472);
INSERT INTO GD_GE
	VALUES (2097659,
	2097153,
	2097160,
	41);
INSERT INTO GD_SHP
	VALUES (2097659,
	720,
	1056,
	1296,
	1232);
INSERT INTO GD_GE
	VALUES (2097662,
	2097153,
	2097161,
	41);
INSERT INTO GD_SHP
	VALUES (2097662,
	720,
	1312,
	1296,
	1488);
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
	881,
	741,
	975,
	763,
	-43,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097691,
	2097156,
	1024,
	784,
	1024,
	720,
	0);
INSERT INTO GD_GE
	VALUES (2097180,
	2097153,
	2097157,
	42);
INSERT INTO GD_CON
	VALUES (2097180,
	2097165,
	2097168,
	0);
INSERT INTO GD_CTXT
	VALUES (2097180,
	0,
	0,
	0,
	0,
	0,
	0,
	2400,
	995,
	2479,
	1017,
	-59,
	-4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097709,
	2097180,
	2544,
	960,
	2544,
	1056,
	0);
INSERT INTO GD_GE
	VALUES (2097182,
	2097153,
	2097158,
	42);
INSERT INTO GD_CON
	VALUES (2097182,
	2097168,
	2097169,
	0);
INSERT INTO GD_CTXT
	VALUES (2097182,
	0,
	0,
	0,
	0,
	0,
	0,
	2401,
	1255,
	2480,
	1277,
	-58,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097711,
	2097182,
	2544,
	1216,
	2544,
	1312,
	0);
INSERT INTO GD_GE
	VALUES (2097353,
	2097153,
	2097163,
	42);
INSERT INTO GD_CON
	VALUES (2097353,
	2097311,
	2097311,
	0);
INSERT INTO GD_CTXT
	VALUES (2097353,
	0,
	0,
	0,
	0,
	0,
	0,
	1661,
	1206,
	1882,
	1228,
	-7,
	-14,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097452,
	2097353,
	1568,
	1312,
	1568,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (2097453,
	2097353,
	1568,
	1248,
	1984,
	1248,
	2097452);
INSERT INTO GD_LS
	VALUES (2097454,
	2097353,
	1984,
	1248,
	1984,
	1312,
	2097453);
INSERT INTO GD_GE
	VALUES (2097357,
	2097153,
	2097164,
	42);
INSERT INTO GD_CON
	VALUES (2097357,
	2097169,
	2097311,
	0);
INSERT INTO GD_CTXT
	VALUES (2097357,
	0,
	0,
	0,
	0,
	0,
	0,
	2098,
	1336,
	2231,
	1358,
	-6,
	-12,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097710,
	2097357,
	2256,
	1376,
	2080,
	1376,
	0);
INSERT INTO GD_GE
	VALUES (2097365,
	2097153,
	2097168,
	42);
INSERT INTO GD_CON
	VALUES (2097365,
	2097311,
	2097155,
	0);
INSERT INTO GD_CTXT
	VALUES (2097365,
	0,
	0,
	0,
	0,
	0,
	0,
	2075,
	1509,
	2163,
	1549,
	-4,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097705,
	2097365,
	2075,
	1467,
	2272,
	1552,
	0);
INSERT INTO GD_GE
	VALUES (2097660,
	2097153,
	2097170,
	42);
INSERT INTO GD_CON
	VALUES (2097660,
	2097154,
	2097659,
	0);
INSERT INTO GD_CTXT
	VALUES (2097660,
	0,
	0,
	0,
	0,
	0,
	0,
	939,
	999,
	1018,
	1021,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097719,
	2097660,
	1024,
	960,
	1024,
	1056,
	0);
INSERT INTO GD_GE
	VALUES (2097664,
	2097153,
	2097171,
	42);
INSERT INTO GD_CON
	VALUES (2097664,
	2097659,
	2097662,
	0);
INSERT INTO GD_CTXT
	VALUES (2097664,
	0,
	0,
	0,
	0,
	0,
	0,
	881,
	1263,
	960,
	1285,
	-58,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097718,
	2097664,
	1024,
	1232,
	1024,
	1312,
	0);
INSERT INTO GD_GE
	VALUES (2097676,
	2097153,
	2097174,
	42);
INSERT INTO GD_CON
	VALUES (2097676,
	2097662,
	2097311,
	0);
INSERT INTO GD_CTXT
	VALUES (2097676,
	0,
	0,
	0,
	0,
	0,
	0,
	1336,
	1364,
	1469,
	1386,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097694,
	2097676,
	1296,
	1392,
	1504,
	1392,
	0);
INSERT INTO GD_GE
	VALUES (2097678,
	2097153,
	2097175,
	42);
INSERT INTO GD_CON
	VALUES (2097678,
	2097311,
	2097165,
	0);
INSERT INTO GD_CTXT
	VALUES (2097678,
	0,
	0,
	0,
	0,
	0,
	0,
	2037,
	1097,
	2159,
	1119,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097706,
	2097678,
	2075,
	1317,
	2256,
	912,
	0);
INSERT INTO O_OBJ
	VALUES (1048583,
	'Select',
	7,
	'SEL',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048590,
	1048583);
INSERT INTO O_BATTR
	VALUES (1048590,
	1048583);
INSERT INTO O_ATTR
	VALUES (1048590,
	1048583,
	0,
	'sel_ID',
	'',
	'',
	'sel_ID',
	0,
	524306);
INSERT INTO O_NBATTR
	VALUES (1048591,
	1048583);
INSERT INTO O_BATTR
	VALUES (1048591,
	1048583);
INSERT INTO O_ATTR
	VALUES (1048591,
	1048583,
	1048590,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_REF
	VALUES (1048583,
	1048584,
	0,
	1048592,
	1048578,
	1048581,
	1048582,
	1048616,
	1048584,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048616,
	1048583,
	1048592,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048616,
	1048583,
	1048591,
	'sel2_ID',
	'',
	'',
	'sel2_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048583);
INSERT INTO O_OIDA
	VALUES (1048590,
	1048583,
	0);
INSERT INTO SM_ISM
	VALUES (4718601,
	1048583);
INSERT INTO SM_SM
	VALUES (4718601,
	'',
	9);
INSERT INTO SM_MOORE
	VALUES (4718601);
INSERT INTO SM_LEVT
	VALUES (4718593,
	4718601,
	0);
INSERT INTO SM_SEVT
	VALUES (4718593,
	4718601,
	0);
INSERT INTO SM_EVT
	VALUES (4718593,
	4718601,
	0,
	2,
	'Next',
	0,
	'',
	'SEL2',
	'');
INSERT INTO SM_LEVT
	VALUES (4718594,
	4718601,
	0);
INSERT INTO SM_SEVT
	VALUES (4718594,
	4718601,
	0);
INSERT INTO SM_EVT
	VALUES (4718594,
	4718601,
	0,
	1,
	'Start',
	0,
	'',
	'SEL1',
	'');
INSERT INTO SM_LEVT
	VALUES (4718595,
	4718601,
	0);
INSERT INTO SM_SEVT
	VALUES (4718595,
	4718601,
	0);
INSERT INTO SM_EVT
	VALUES (4718595,
	4718601,
	0,
	3,
	'Continue',
	0,
	'',
	'SEL3',
	'');
INSERT INTO SM_STATE
	VALUES (4718596,
	4718601,
	0,
	'Select Any',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (4718596,
	4718593,
	4718601,
	0);
INSERT INTO SM_SEME
	VALUES (4718596,
	4718594,
	4718601,
	0);
INSERT INTO SM_EIGN
	VALUES (4718596,
	4718595,
	4718601,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (4718596,
	4718595,
	4718601,
	0);
INSERT INTO SM_MOAH
	VALUES (4718596,
	4718601,
	4718596);
INSERT INTO SM_AH
	VALUES (4718596,
	4718601);
INSERT INTO SM_ACT
	VALUES (4718596,
	4718601,
	1,
	'if ( self.sel2_ID == colors::green )
  LOG::LogSuccess(message:"Successfully selected any where sel2_ID equal to colors::green.");
else
  LOG::LogFailure(message:"sel2_ID not equal to colors::green, but an event was generated to this instance.");
end if;

select any drv from instances of DRV;
generate DRV3 to drv;',
	'');
INSERT INTO SM_STATE
	VALUES (4718597,
	4718601,
	0,
	'Select Many',
	2,
	0);
INSERT INTO SM_SEME
	VALUES (4718597,
	4718593,
	4718601,
	0);
INSERT INTO SM_EIGN
	VALUES (4718597,
	4718594,
	4718601,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (4718597,
	4718594,
	4718601,
	0);
INSERT INTO SM_SEME
	VALUES (4718597,
	4718595,
	4718601,
	0);
INSERT INTO SM_MOAH
	VALUES (4718597,
	4718601,
	4718597);
INSERT INTO SM_AH
	VALUES (4718597,
	4718601);
INSERT INTO SM_ACT
	VALUES (4718597,
	4718601,
	1,
	'if ( self.sel2_ID == colors::green )
  LOG::LogSuccess(message:"Successfully selected many where sel2_ID equal to colors::green.");
else
  LOG::LogFailure(message:"sel2_ID not equal to colors::green, but an event was generated to this instance.");
end if;

select any drv from instances of DRV;
generate DRV4(num:0) to drv;',
	'');
INSERT INTO SM_STATE
	VALUES (4718598,
	4718601,
	0,
	'Select Any Related By',
	3,
	0);
INSERT INTO SM_SEME
	VALUES (4718598,
	4718593,
	4718601,
	0);
INSERT INTO SM_EIGN
	VALUES (4718598,
	4718594,
	4718601,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (4718598,
	4718594,
	4718601,
	0);
INSERT INTO SM_EIGN
	VALUES (4718598,
	4718595,
	4718601,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (4718598,
	4718595,
	4718601,
	0);
INSERT INTO SM_MOAH
	VALUES (4718598,
	4718601,
	4718598);
INSERT INTO SM_AH
	VALUES (4718598,
	4718601);
INSERT INTO SM_ACT
	VALUES (4718598,
	4718601,
	1,
	'if ( self.sel2_ID == colors::green )
  LOG::LogSuccess(message:"Successfully selected any related by where sel2_ID equal to colors::green.");
else
  LOG::LogFailure(message:"sel2_ID not equal to colors::green, but an event was generated to this instance.");
end if;

select any drv from instances of DRV;
generate DRV3 to drv;',
	'');
INSERT INTO SM_STATE
	VALUES (4718599,
	4718601,
	0,
	'Select Many Related By',
	4,
	0);
INSERT INTO SM_EIGN
	VALUES (4718599,
	4718593,
	4718601,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (4718599,
	4718593,
	4718601,
	0);
INSERT INTO SM_EIGN
	VALUES (4718599,
	4718594,
	4718601,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (4718599,
	4718594,
	4718601,
	0);
INSERT INTO SM_SEME
	VALUES (4718599,
	4718595,
	4718601,
	0);
INSERT INTO SM_MOAH
	VALUES (4718599,
	4718601,
	4718599);
INSERT INTO SM_AH
	VALUES (4718599,
	4718601);
INSERT INTO SM_ACT
	VALUES (4718599,
	4718601,
	1,
	'if ( self.sel2_ID == colors::green )
  LOG::LogSuccess(message:"Successfully selected many related by where sel2_ID equal to colors::green.");
else
  LOG::LogFailure(message:"sel2_ID not equal to colors::green, but an event was generated to this instance.");
end if;

select any drv from instances of DRV;
generate DRV4(num:1) to drv;',
	'');
INSERT INTO SM_NSTXN
	VALUES (4718595,
	4718601,
	4718598,
	4718593,
	0);
INSERT INTO SM_TXN
	VALUES (4718595,
	4718601,
	4718599,
	0);
INSERT INTO SM_NSTXN
	VALUES (4718597,
	4718601,
	4718596,
	4718593,
	0);
INSERT INTO SM_TXN
	VALUES (4718597,
	4718601,
	4718597,
	0);
INSERT INTO SM_NSTXN
	VALUES (4718598,
	4718601,
	4718596,
	4718594,
	0);
INSERT INTO SM_TXN
	VALUES (4718598,
	4718601,
	4718596,
	0);
INSERT INTO SM_NSTXN
	VALUES (4718599,
	4718601,
	4718597,
	4718593,
	0);
INSERT INTO SM_TXN
	VALUES (4718599,
	4718601,
	4718597,
	0);
INSERT INTO SM_NSTXN
	VALUES (4718596,
	4718601,
	4718597,
	4718595,
	0);
INSERT INTO SM_TXN
	VALUES (4718596,
	4718601,
	4718598,
	0);
INSERT INTO SM_NSTXN
	VALUES (4718600,
	4718601,
	4718599,
	4718595,
	0);
INSERT INTO SM_TXN
	VALUES (4718600,
	4718601,
	4718599,
	0);
INSERT INTO GD_MD
	VALUES (4718593,
	8,
	4718601,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1528,
	3991,
	0.741318,
	0);
INSERT INTO GD_GE
	VALUES (4718603,
	4718593,
	4718596,
	41);
INSERT INTO GD_SHP
	VALUES (4718603,
	1552,
	1312,
	2000,
	1568);
INSERT INTO GD_GE
	VALUES (4718604,
	4718593,
	4718597,
	41);
INSERT INTO GD_SHP
	VALUES (4718604,
	1552,
	1664,
	2000,
	1920);
INSERT INTO GD_GE
	VALUES (4718605,
	4718593,
	4718598,
	41);
INSERT INTO GD_SHP
	VALUES (4718605,
	2144,
	1312,
	2592,
	1568);
INSERT INTO GD_GE
	VALUES (4718606,
	4718593,
	4718599,
	41);
INSERT INTO GD_SHP
	VALUES (4718606,
	2144,
	1664,
	2592,
	1920);
INSERT INTO GD_GE
	VALUES (4718607,
	4718593,
	4718595,
	42);
INSERT INTO GD_CON
	VALUES (4718607,
	4718605,
	4718606,
	0);
INSERT INTO GD_CTXT
	VALUES (4718607,
	0,
	0,
	0,
	0,
	0,
	0,
	2225,
	1607,
	2313,
	1629,
	-49,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718608,
	4718607,
	2368,
	1568,
	2368,
	1664,
	0);
INSERT INTO GD_GE
	VALUES (4718609,
	4718593,
	4718596,
	42);
INSERT INTO GD_CON
	VALUES (4718609,
	4718604,
	4718605,
	0);
INSERT INTO GD_CTXT
	VALUES (4718609,
	0,
	0,
	0,
	0,
	0,
	0,
	1909,
	1607,
	2019,
	1629,
	-38,
	16,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718610,
	4718609,
	1984,
	1664,
	2144,
	1552,
	0);
INSERT INTO GD_GE
	VALUES (4718611,
	4718593,
	4718597,
	42);
INSERT INTO GD_CON
	VALUES (4718611,
	4718603,
	4718604,
	0);
INSERT INTO GD_CTXT
	VALUES (4718611,
	0,
	0,
	0,
	0,
	0,
	0,
	1682,
	1607,
	1770,
	1629,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718612,
	4718611,
	1776,
	1568,
	1776,
	1664,
	0);
INSERT INTO GD_GE
	VALUES (4718613,
	4718593,
	4718598,
	42);
INSERT INTO GD_CON
	VALUES (4718613,
	4718603,
	4718603,
	0);
INSERT INTO GD_CTXT
	VALUES (4718613,
	0,
	0,
	0,
	0,
	0,
	0,
	1706,
	1246,
	1796,
	1268,
	-27,
	-6,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718614,
	4718613,
	1568,
	1312,
	1568,
	1280,
	0);
INSERT INTO GD_LS
	VALUES (4718615,
	4718613,
	1568,
	1280,
	1984,
	1280,
	4718614);
INSERT INTO GD_LS
	VALUES (4718616,
	4718613,
	1984,
	1280,
	1984,
	1312,
	4718615);
INSERT INTO GD_GE
	VALUES (4718617,
	4718593,
	4718599,
	42);
INSERT INTO GD_CON
	VALUES (4718617,
	4718604,
	4718604,
	0);
INSERT INTO GD_CTXT
	VALUES (4718617,
	0,
	0,
	0,
	0,
	0,
	0,
	1732,
	1967,
	1811,
	1989,
	1,
	43,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718618,
	4718617,
	1568,
	1920,
	1568,
	1952,
	0);
INSERT INTO GD_LS
	VALUES (4718619,
	4718617,
	1568,
	1952,
	1968,
	1952,
	4718618);
INSERT INTO GD_LS
	VALUES (4718620,
	4718617,
	1968,
	1952,
	1968,
	1920,
	4718619);
INSERT INTO GD_GE
	VALUES (4718621,
	4718593,
	4718600,
	42);
INSERT INTO GD_CON
	VALUES (4718621,
	4718606,
	4718606,
	0);
INSERT INTO GD_CTXT
	VALUES (4718621,
	0,
	0,
	0,
	0,
	0,
	0,
	2339,
	1971,
	2449,
	1993,
	16,
	47,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718631,
	4718621,
	2176,
	1920,
	2176,
	1952,
	0);
INSERT INTO GD_LS
	VALUES (4718632,
	4718621,
	2176,
	1952,
	2576,
	1952,
	4718631);
INSERT INTO GD_LS
	VALUES (4718633,
	4718621,
	2576,
	1952,
	2576,
	1920,
	4718632);
INSERT INTO O_OBJ
	VALUES (1048584,
	'Select Two',
	8,
	'SEL2',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048592,
	1048584);
INSERT INTO O_BATTR
	VALUES (1048592,
	1048584);
INSERT INTO O_ATTR
	VALUES (1048592,
	1048584,
	0,
	'sel2_ID',
	'',
	'',
	'sel2_ID',
	0,
	524306);
INSERT INTO O_NBATTR
	VALUES (1048593,
	1048584);
INSERT INTO O_BATTR
	VALUES (1048593,
	1048584);
INSERT INTO O_ATTR
	VALUES (1048593,
	1048584,
	1048592,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048584);
INSERT INTO O_OIDA
	VALUES (1048592,
	1048584,
	0);
INSERT INTO O_RTIDA
	VALUES (1048592,
	1048584,
	0,
	1048578,
	1048582);
INSERT INTO SM_ISM
	VALUES (5242890,
	1048584);
INSERT INTO SM_SM
	VALUES (5242890,
	'',
	10);
INSERT INTO SM_MOORE
	VALUES (5242890);
INSERT INTO SM_STATE
	VALUES (5242887,
	5242890,
	0,
	'State One',
	1,
	0);
INSERT INTO SM_MOAH
	VALUES (5242887,
	5242890,
	5242887);
INSERT INTO SM_AH
	VALUES (5242887,
	5242890);
INSERT INTO SM_ACT
	VALUES (5242887,
	5242890,
	1,
	'',
	'');
INSERT INTO GD_MD
	VALUES (5242881,
	8,
	5242890,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1479,
	4304,
	0.653776,
	0);
INSERT INTO GD_GE
	VALUES (5243045,
	5242881,
	5242887,
	41);
INSERT INTO GD_SHP
	VALUES (5243045,
	1712,
	896,
	2336,
	1344);
INSERT INTO O_OBJ
	VALUES (1048591,
	'Class',
	13,
	'CL',
	'',
	1048578);
INSERT INTO O_TFR
	VALUES (1048577,
	1048591,
	'class_compare_enums',
	'',
	524289,
	0,
	'LOG::LogInfo(message:"In class based operation class_compare_enums.");

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select any.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select any sel from instances of SEL where selected.sel2_ID == colors::green;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using literal.");
end if;

a_color = colors::green;
select any sel from instances of SEL where selected.sel2_ID == a_color;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using local.");
end if;

select any sel2 from instances of SEL2;
select any sel from instances of SEL where selected.sel2_ID == sel2.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using base attribute.");
end if;

select any sel from instances of SEL where selected.sel_ID == colors::yellow;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using identifier and literal.");
end if;

a_color = colors::yellow;
select any sel from instances of SEL where selected.sel_ID == a_color;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select any sel from instances of SEL where selected.sel_ID == sel1.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select many.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select many sels from instances of SEL where colors::green == selected.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instances of ''Select'' using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instances of ''Select'' using literal.");
end if;

a_color = colors::green;
select many sels from instances of SEL where a_color == selected.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instances of ''Select'' using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using local.");
end if;

select many sels from instances of SEL where sel2.sel2_ID == selected.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instances of ''Select'' using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instances of ''Select'' using base attribute.");
end if;

select many sels from instances of SEL where selected.sel_ID == colors::yellow;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using identifier and literal.");
end if;

a_color = colors::yellow;
select many sels from instances of SEL where selected.sel_ID == a_color;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select many sels from instances of SEL where selected.sel_ID == sel1.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select one related by.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select any sel_one2 from instances of SEL_ONE2;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one2_ID == colors::blue;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one instance of ''Select One'' using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select one instance of ''Select One'' using literal.");
end if;

a_color = colors::blue;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one2_ID == a_color;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one instance of ''Select One'' using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select one instance of ''Select One'' using local.");
end if;

select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one2_ID == sel_one2.sel_one2_ID;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one instance of ''Select One'' using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select one instance of ''Select One'' using base attribute.");
end if;

select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one_ID == colors::purple;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one related by ''Select One Two'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select one relate dby ''Select One Two'' using identifier and literal.");
end if;

a_color = colors::purple;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one_ID == a_color;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one related by ''Select One Two'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select one related by  ''Select One Two'' using identifier and local.");
end if;

select any sel_one1 from instances of SEL_ONE;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one_ID == sel_one1.sel_one_ID;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one related by ''Select One Two'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select one related by ''Select One Two'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select any related by.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select any sel related by sel2->SEL[R1] where colors::green == selected.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' related by using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' related by using literal.");
end if;

a_color = colors::green;
select any sel related by sel2->SEL[R1] where a_color == selected.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' related by using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' related by using local.");
end if;

select any sel related by sel2->SEL[R1] where sel2.sel2_ID == selected.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' related by using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' related by using base attribute.");
end if;

select any sel related by sel2->SEL[R1] where colors::yellow == selected.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any related by ''Select Two'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any related by ''Select Two'' using identifier and literal.");
end if;

a_color = colors::yellow;
select any sel related by sel2->SEL[R1] where a_color == selected.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any related by ''Select Two'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any related by ''Select Two'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select any sel related by sel2->SEL[R1] where sel1.sel_ID == selected.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any related by ''Select Two'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any related by ''Select Two'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select many related by.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select many sels related by sel2->SEL[R1] where selected.sel2_ID == colors::green;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' related by using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' related by using literal.");
end if;

a_color = colors::green;
select many sels related by sel2->SEL[R1] where selected.sel2_ID == a_color;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' related by using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' related by using local.");
end if;

select many sels related by sel2->SEL[R1] where selected.sel2_ID == sel2.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected an instance of ''Select'' related by using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select an instance of ''Select'' related by using base attribute.");
end if;

select many sels related by sel2->SEL[R1] where colors::yellow == selected.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many related by ''Select Two'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many related by ''Select Two'' using identifier and literal.");
end if;

a_color = colors::yellow;
select many sels related by sel2->SEL[R1] where a_color == selected.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many related by ''Select Two'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many related by ''Select Two'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select many sels related by sel2->SEL[R1] where sel1.sel_ID == selected.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many related by ''Select Two'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select many related by ''Select Two'' using identifier and base attribute.");
end if;
',
	1);
INSERT INTO O_TFR
	VALUES (1048578,
	1048591,
	'instance_compare_enums',
	'',
	524289,
	1,
	'LOG::LogInfo(message:"In instance based operation instance_compare_enums.");

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select any.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select any sel from instances of SEL where selected.sel2_ID == colors::green;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using literal.");
end if;

a_color = colors::green;
select any sel from instances of SEL where selected.sel2_ID == a_color;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using local.");
end if;

select any sel2 from instances of SEL2;
select any sel from instances of SEL where selected.sel2_ID == sel2.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using base attribute.");
end if;

select any sel from instances of SEL where selected.sel_ID == colors::yellow;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using identifier and literal.");
end if;

a_color = colors::yellow;
select any sel from instances of SEL where selected.sel_ID == a_color;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select any sel from instances of SEL where selected.sel_ID == sel1.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select many.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select many sels from instances of SEL where colors::green == selected.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instances of ''Select'' using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instances of ''Select'' using literal.");
end if;

a_color = colors::green;
select many sels from instances of SEL where a_color == selected.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instances of ''Select'' using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using local.");
end if;

select many sels from instances of SEL where sel2.sel2_ID == selected.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instances of ''Select'' using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instances of ''Select'' using base attribute.");
end if;

select many sels from instances of SEL where selected.sel_ID == colors::yellow;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using identifier and literal.");
end if;

a_color = colors::yellow;
select many sels from instances of SEL where selected.sel_ID == a_color;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select many sels from instances of SEL where selected.sel_ID == sel1.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select one related by.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select any sel_one2 from instances of SEL_ONE2;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one2_ID == colors::blue;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one instance of ''Select One'' using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select one instance of ''Select One'' using literal.");
end if;

a_color = colors::blue;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one2_ID == a_color;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one instance of ''Select One'' using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select one instance of ''Select One'' using local.");
end if;

select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one2_ID == sel_one2.sel_one2_ID;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one instance of ''Select One'' using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select one instance of ''Select One'' using base attribute.");
end if;

select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one_ID == colors::purple;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one related by ''Select One Two'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select one relate dby ''Select One Two'' using identifier and literal.");
end if;

a_color = colors::purple;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one_ID == a_color;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one related by ''Select One Two'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select one related by  ''Select One Two'' using identifier and local.");
end if;

select any sel_one1 from instances of SEL_ONE;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one_ID == sel_one1.sel_one_ID;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one related by ''Select One Two'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select one related by ''Select One Two'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select any related by.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select any sel related by sel2->SEL[R1] where colors::green == selected.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' related by using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' related by using literal.");
end if;

a_color = colors::green;
select any sel related by sel2->SEL[R1] where a_color == selected.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' related by using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' related by using local.");
end if;

select any sel related by sel2->SEL[R1] where sel2.sel2_ID == selected.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' related by using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' related by using base attribute.");
end if;

select any sel related by sel2->SEL[R1] where colors::yellow == selected.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any related by ''Select Two'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any related by ''Select Two'' using identifier and literal.");
end if;

a_color = colors::yellow;
select any sel related by sel2->SEL[R1] where a_color == selected.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any related by ''Select Two'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any related by ''Select Two'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select any sel related by sel2->SEL[R1] where sel1.sel_ID == selected.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any related by ''Select Two'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any related by ''Select Two'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select many related by.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select many sels related by sel2->SEL[R1] where selected.sel2_ID == colors::green;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' related by using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' related by using literal.");
end if;

a_color = colors::green;
select many sels related by sel2->SEL[R1] where selected.sel2_ID == a_color;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' related by using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' related by using local.");
end if;

select many sels related by sel2->SEL[R1] where selected.sel2_ID == sel2.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected an instance of ''Select'' related by using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select an instance of ''Select'' related by using base attribute.");
end if;

select many sels related by sel2->SEL[R1] where colors::yellow == selected.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many related by ''Select Two'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many related by ''Select Two'' using identifier and literal.");
end if;

a_color = colors::yellow;
select many sels related by sel2->SEL[R1] where a_color == selected.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many related by ''Select Two'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many related by ''Select Two'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select many sels related by sel2->SEL[R1] where sel1.sel_ID == selected.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many related by ''Select Two'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select many related by ''Select Two'' using identifier and base attribute.");
end if;
',
	1);
INSERT INTO O_NBATTR
	VALUES (1048611,
	1048591);
INSERT INTO O_BATTR
	VALUES (1048611,
	1048591);
INSERT INTO O_ATTR
	VALUES (1048611,
	1048591,
	0,
	'cl_ID',
	'',
	'',
	'cl_ID',
	0,
	524291);
INSERT INTO O_DBATTR
	VALUES (1048612,
	1048591,
	'LOG::LogInfo(message:"In MDA compare_enums.");

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select any.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select any sel from instances of SEL where selected.sel2_ID == colors::green;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using literal.");
end if;

a_color = colors::green;
select any sel from instances of SEL where selected.sel2_ID == a_color;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using local.");
end if;

select any sel2 from instances of SEL2;
select any sel from instances of SEL where selected.sel2_ID == sel2.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using base attribute.");
end if;

select any sel from instances of SEL where selected.sel_ID == colors::yellow;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using identifier and literal.");
end if;

a_color = colors::yellow;
select any sel from instances of SEL where selected.sel_ID == a_color;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select any sel from instances of SEL where selected.sel_ID == sel1.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select many.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select many sels from instances of SEL where colors::green == selected.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instances of ''Select'' using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instances of ''Select'' using literal.");
end if;

a_color = colors::green;
select many sels from instances of SEL where a_color == selected.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instances of ''Select'' using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using local.");
end if;

select many sels from instances of SEL where sel2.sel2_ID == selected.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instances of ''Select'' using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instances of ''Select'' using base attribute.");
end if;

select many sels from instances of SEL where selected.sel_ID == colors::yellow;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using identifier and literal.");
end if;

a_color = colors::yellow;
select many sels from instances of SEL where selected.sel_ID == a_color;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select many sels from instances of SEL where selected.sel_ID == sel1.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select one related by.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select any sel_one2 from instances of SEL_ONE2;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one2_ID == colors::blue;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one instance of ''Select One'' using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select one instance of ''Select One'' using literal.");
end if;

a_color = colors::blue;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one2_ID == a_color;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one instance of ''Select One'' using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select one instance of ''Select One'' using local.");
end if;

select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one2_ID == sel_one2.sel_one2_ID;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one instance of ''Select One'' using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select one instance of ''Select One'' using base attribute.");
end if;

select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one_ID == colors::purple;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one related by ''Select One Two'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select one relate dby ''Select One Two'' using identifier and literal.");
end if;

a_color = colors::purple;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one_ID == a_color;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one related by ''Select One Two'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select one related by  ''Select One Two'' using identifier and local.");
end if;

select any sel_one1 from instances of SEL_ONE;
select one sel_one related by sel_one2->SEL_ONE[R2] where selected.sel_one_ID == sel_one1.sel_one_ID;
if ( not_empty sel_one )
  LOG::LogSuccess(message:"Successfully selected one related by ''Select One Two'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select one related by ''Select One Two'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select any related by.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select any sel related by sel2->SEL[R1] where colors::green == selected.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' related by using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' related by using literal.");
end if;

a_color = colors::green;
select any sel related by sel2->SEL[R1] where a_color == selected.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' related by using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' related by using local.");
end if;

select any sel related by sel2->SEL[R1] where sel2.sel2_ID == selected.sel2_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any instance of ''Select'' related by using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any instance of ''Select'' related by using base attribute.");
end if;

select any sel related by sel2->SEL[R1] where colors::yellow == selected.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any related by ''Select Two'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select any related by ''Select Two'' using identifier and literal.");
end if;

a_color = colors::yellow;
select any sel related by sel2->SEL[R1] where a_color == selected.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any related by ''Select Two'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select any related by ''Select Two'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select any sel related by sel2->SEL[R1] where sel1.sel_ID == selected.sel_ID;
if ( not_empty sel )
  LOG::LogSuccess(message:"Successfully selected any related by ''Select Two'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select any related by ''Select Two'' using identifier and base attribute.");
end if;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Select many related by.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

select many sels related by sel2->SEL[R1] where selected.sel2_ID == colors::green;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' related by using literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' related by using literal.");
end if;

a_color = colors::green;
select many sels related by sel2->SEL[R1] where selected.sel2_ID == a_color;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many instance of ''Select'' related by using local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many instance of ''Select'' related by using local.");
end if;

select many sels related by sel2->SEL[R1] where selected.sel2_ID == sel2.sel2_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected an instance of ''Select'' related by using base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select an instance of ''Select'' related by using base attribute.");
end if;

select many sels related by sel2->SEL[R1] where colors::yellow == selected.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many related by ''Select Two'' using identifier and literal.");
else
  LOG::LogFailure(message:"Did NOT successfully select many related by ''Select Two'' using identifier and literal.");
end if;

a_color = colors::yellow;
select many sels related by sel2->SEL[R1] where a_color == selected.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many related by ''Select Two'' using identifier and local.");
else
  LOG::LogFailure(message:"Did NOT successfully select many related by ''Select Two'' using identifier and local.");
end if;

select any sel1 from instances of SEL;
select many sels related by sel2->SEL[R1] where sel1.sel_ID == selected.sel_ID;
if ( not_empty sels )
  LOG::LogSuccess(message:"Successfully selected many related by ''Select Two'' using identifier and base attribute.");
else
  LOG::LogFailure(message:"Did NOT successfully select many related by ''Select Two'' using identifier and base attribute.");
end if;

self.compare_enums = 1;',
	1);
INSERT INTO O_BATTR
	VALUES (1048612,
	1048591);
INSERT INTO O_ATTR
	VALUES (1048612,
	1048591,
	1048611,
	'compare_enums',
	'',
	'',
	'compare_enums',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048613,
	1048591);
INSERT INTO O_BATTR
	VALUES (1048613,
	1048591);
INSERT INTO O_ATTR
	VALUES (1048613,
	1048591,
	1048612,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048591);
INSERT INTO O_OIDA
	VALUES (1048611,
	1048591,
	0);
INSERT INTO SM_ISM
	VALUES (8912913,
	1048591);
INSERT INTO SM_SM
	VALUES (8912913,
	'',
	17);
INSERT INTO SM_MOORE
	VALUES (8912913);
INSERT INTO SM_STATE
	VALUES (8912897,
	8912913,
	0,
	'State One',
	1,
	0);
INSERT INTO SM_MOAH
	VALUES (8912897,
	8912913,
	8912897);
INSERT INTO SM_AH
	VALUES (8912897,
	8912913);
INSERT INTO SM_ACT
	VALUES (8912897,
	8912913,
	1,
	'',
	'');
INSERT INTO GD_MD
	VALUES (8912897,
	8,
	8912913,
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
	VALUES (8912898,
	8912897,
	8912897,
	41);
INSERT INTO GD_SHP
	VALUES (8912898,
	1760,
	1264,
	2128,
	1536);
INSERT INTO O_OBJ
	VALUES (1048593,
	'Select One',
	14,
	'SEL_ONE',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048617,
	1048593);
INSERT INTO O_BATTR
	VALUES (1048617,
	1048593);
INSERT INTO O_ATTR
	VALUES (1048617,
	1048593,
	0,
	'sel_one_ID',
	'',
	'',
	'sel_one_ID',
	0,
	524306);
INSERT INTO O_REF
	VALUES (1048593,
	1048594,
	0,
	1048619,
	1048581,
	1048595,
	1048596,
	1048621,
	1048585,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048621,
	1048593,
	1048619,
	1048594,
	1);
INSERT INTO O_ATTR
	VALUES (1048621,
	1048593,
	1048617,
	'sel_one2_ID',
	'',
	'',
	'sel_one2_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048618,
	1048593);
INSERT INTO O_BATTR
	VALUES (1048618,
	1048593);
INSERT INTO O_ATTR
	VALUES (1048618,
	1048593,
	1048621,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048593);
INSERT INTO O_OIDA
	VALUES (1048617,
	1048593,
	0);
INSERT INTO SM_ISM
	VALUES (9437202,
	1048593);
INSERT INTO SM_SM
	VALUES (9437202,
	'',
	18);
INSERT INTO SM_MOORE
	VALUES (9437202);
INSERT INTO SM_LEVT
	VALUES (9437185,
	9437202,
	0);
INSERT INTO SM_SEVT
	VALUES (9437185,
	9437202,
	0);
INSERT INTO SM_EVT
	VALUES (9437185,
	9437202,
	0,
	1,
	'Start',
	0,
	'',
	'SEL_ONE1',
	'');
INSERT INTO SM_STATE
	VALUES (9437185,
	9437202,
	0,
	'Select One',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (9437185,
	9437185,
	9437202,
	0);
INSERT INTO SM_MOAH
	VALUES (9437185,
	9437202,
	9437185);
INSERT INTO SM_AH
	VALUES (9437185,
	9437202);
INSERT INTO SM_ACT
	VALUES (9437185,
	9437202,
	1,
	'if ( self.sel_one2_ID == colors::blue )
  LOG::LogSuccess(message:"Successfully selected one where sel_one2_ID equal to colors::blue.");
else
  LOG::LogFailure(message:"sel_one2_ID not equal to colors::blue, but an event was generated to this instance.");
end if;

select any drv from instances of DRV;
generate DRV3 to drv;',
	'');
INSERT INTO SM_NSTXN
	VALUES (9437185,
	9437202,
	9437185,
	9437185,
	0);
INSERT INTO SM_TXN
	VALUES (9437185,
	9437202,
	9437185,
	0);
INSERT INTO GD_MD
	VALUES (9437185,
	8,
	9437202,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1762,
	4374,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (9437186,
	9437185,
	9437185,
	41);
INSERT INTO GD_SHP
	VALUES (9437186,
	1968,
	1184,
	2336,
	1520);
INSERT INTO GD_GE
	VALUES (9437187,
	9437185,
	9437185,
	42);
INSERT INTO GD_CON
	VALUES (9437187,
	9437186,
	9437186,
	0);
INSERT INTO GD_CTXT
	VALUES (9437187,
	0,
	0,
	0,
	0,
	0,
	0,
	2078,
	1124,
	2202,
	1146,
	-6,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (9437197,
	9437187,
	1984,
	1184,
	1984,
	1152,
	0);
INSERT INTO GD_LS
	VALUES (9437198,
	9437187,
	1984,
	1152,
	2304,
	1152,
	9437197);
INSERT INTO GD_LS
	VALUES (9437199,
	9437187,
	2304,
	1152,
	2304,
	1184,
	9437198);
INSERT INTO O_OBJ
	VALUES (1048594,
	'Select One Two',
	15,
	'SEL_ONE2',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048619,
	1048594);
INSERT INTO O_BATTR
	VALUES (1048619,
	1048594);
INSERT INTO O_ATTR
	VALUES (1048619,
	1048594,
	0,
	'sel_one2_ID',
	'',
	'',
	'sel_one2_ID',
	0,
	524306);
INSERT INTO O_NBATTR
	VALUES (1048620,
	1048594);
INSERT INTO O_BATTR
	VALUES (1048620,
	1048594);
INSERT INTO O_ATTR
	VALUES (1048620,
	1048594,
	1048619,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048594);
INSERT INTO O_OIDA
	VALUES (1048619,
	1048594,
	0);
INSERT INTO O_RTIDA
	VALUES (1048619,
	1048594,
	0,
	1048581,
	1048596);
INSERT INTO SM_ISM
	VALUES (9961491,
	1048594);
INSERT INTO SM_SM
	VALUES (9961491,
	'',
	19);
INSERT INTO SM_MOORE
	VALUES (9961491);
INSERT INTO SM_STATE
	VALUES (9961473,
	9961491,
	0,
	'State One',
	1,
	0);
INSERT INTO SM_MOAH
	VALUES (9961473,
	9961491,
	9961473);
INSERT INTO SM_AH
	VALUES (9961473,
	9961491);
INSERT INTO SM_ACT
	VALUES (9961473,
	9961491,
	1,
	'',
	'');
INSERT INTO GD_MD
	VALUES (9961473,
	8,
	9961491,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1796,
	4346,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (9961474,
	9961473,
	9961473,
	41);
INSERT INTO GD_SHP
	VALUES (9961474,
	2000,
	1216,
	2448,
	1488);
INSERT INTO R_SIMP
	VALUES (1048578);
INSERT INTO R_REL
	VALUES (1048578,
	1,
	'',
	1048578);
INSERT INTO R_FORM
	VALUES (1048583,
	1048578,
	1048581,
	1,
	1,
	'');
INSERT INTO R_RGO
	VALUES (1048583,
	1048578,
	1048581);
INSERT INTO R_OIR
	VALUES (1048583,
	1048578,
	1048581,
	0);
INSERT INTO R_PART
	VALUES (1048584,
	1048578,
	1048582,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (1048584,
	1048578,
	1048582,
	0);
INSERT INTO R_OIR
	VALUES (1048584,
	1048578,
	1048582,
	0);
INSERT INTO R_SIMP
	VALUES (1048581);
INSERT INTO R_REL
	VALUES (1048581,
	2,
	'',
	1048578);
INSERT INTO R_FORM
	VALUES (1048593,
	1048581,
	1048595,
	0,
	0,
	'');
INSERT INTO R_RGO
	VALUES (1048593,
	1048581,
	1048595);
INSERT INTO R_OIR
	VALUES (1048593,
	1048581,
	1048595,
	0);
INSERT INTO R_PART
	VALUES (1048594,
	1048581,
	1048596,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (1048594,
	1048581,
	1048596,
	0);
INSERT INTO R_OIR
	VALUES (1048594,
	1048581,
	1048596,
	0);
INSERT INTO GD_MD
	VALUES (1048585,
	5,
	1048578,
	11,
	1,
	0,
	1,
	1,
	0,
	12,
	1530,
	4764,
	1.372330,
	0);
INSERT INTO GD_GE
	VALUES (1048588,
	1048585,
	1048577,
	21);
INSERT INTO GD_SHP
	VALUES (1048588,
	1552,
	1088,
	1728,
	1200);
INSERT INTO GD_GE
	VALUES (1048589,
	1048585,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048589,
	1760,
	1088,
	1936,
	1200);
INSERT INTO GD_GE
	VALUES (1048612,
	1048585,
	1048583,
	21);
INSERT INTO GD_SHP
	VALUES (1048612,
	1600,
	928,
	1776,
	1040);
INSERT INTO GD_GE
	VALUES (1048614,
	1048585,
	1048584,
	21);
INSERT INTO GD_SHP
	VALUES (1048614,
	1920,
	928,
	2096,
	1040);
INSERT INTO GD_GE
	VALUES (1048693,
	1048585,
	1048591,
	21);
INSERT INTO GD_SHP
	VALUES (1048693,
	1968,
	1088,
	2144,
	1200);
INSERT INTO GD_GE
	VALUES (1048714,
	1048585,
	1048593,
	21);
INSERT INTO GD_SHP
	VALUES (1048714,
	1600,
	768,
	1776,
	880);
INSERT INTO GD_GE
	VALUES (1048716,
	1048585,
	1048594,
	21);
INSERT INTO GD_SHP
	VALUES (1048716,
	1920,
	768,
	2096,
	880);
INSERT INTO GD_GE
	VALUES (1048616,
	1048585,
	1048578,
	24);
INSERT INTO GD_CON
	VALUES (1048616,
	1048612,
	1048614,
	1048723);
INSERT INTO GD_CTXT
	VALUES (1048616,
	0,
	0,
	0,
	0,
	0,
	0,
	1838,
	948,
	1862,
	970,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048723,
	1048616,
	1776,
	976,
	1920,
	976,
	0);
INSERT INTO GD_GE
	VALUES (1048720,
	1048585,
	1048581,
	24);
INSERT INTO GD_CON
	VALUES (1048720,
	1048714,
	1048716,
	0);
INSERT INTO GD_CTXT
	VALUES (1048720,
	0,
	0,
	0,
	0,
	0,
	0,
	1838,
	804,
	1862,
	826,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048725,
	1048720,
	1776,
	832,
	1920,
	832,
	0);
