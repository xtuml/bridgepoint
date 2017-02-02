-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (219826,
	'G_ALL_R_BRG_tim',
	'The purpose of this domain is to test the Model Compiler implementation of the TIM bridges.

Note that there are ERRORs in the Model Verifier output from this domain.  This is because we are waiting for a timer to expire, and Model Verifier errors if timer_remaining_time, timer_reset_time, timer_add_time or timer_cancel is called after a timer has expired.

As of 09/03/2002, the Model Debugger will have LogFailures because of the way that it orders the parameter invocations. It will also not run to completion for an unknown reason.

As of 02/25/2003, the test does not issue LogFailures because of changes to the timing, nextgen:822.',
	0,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	219826,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	219826,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	219826,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	219826,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	219826,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	219826,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	219826,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	219826,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	219826,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	219826,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	219826,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	219826,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	219826,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	219826,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	219826,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	219826,
	'inst_ref<Timer>',
	'');
INSERT INTO S_SYNC
	VALUES (524289,
	219826,
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
INSERT INTO S_SYNC
	VALUES (524290,
	219826,
	'TestTIM',
	'Test the TIM bridges as parameters to a function.
Test parameter values from TIM bridges passed as parameters.
Test the TIM bridges called from within a function.',
	'//////////////////////////////////////////////
// Test incoming parameters.
//////////////////////////////////////////////
if ( param.create_date == param.current_date )
  LOG::LogSuccess(message:"Success in function TestTIM - param.create_date is equal to param.current_date.");
else
  LOG::LogFailure(message:"Failure in function TestTIM - param.create_date is not equal to param.current_date.");
end if;

if ( ( param.get_second + param.get_minute + param.get_hour + param.get_day + param.get_month + param.get_year ) == 6 )
  LOG::LogSuccess(message:"Success in function TestTIM - Sum of param.get_* is equal to six(6).");
else
  LOG::LogFailure(message:"Failure in function TestTIM - Sum of param.get_* is not equal to six(6).");
end if;

if ( param.timer_time_add )
  LOG::LogSuccess(message:"Success in function TestTIM - param.timer_add_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in function TestTIM - param.timer_add_time is not TRUE.");
end if;

if ( param.timer_reset_time )
  LOG::LogSuccess(message:"Success in function TestTIM - param.timer_reset_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in function TestTIM - param.timer_reset_time is not TRUE.");
end if;

if ( param.timer_cancel )
  LOG::LogSuccess(message:"Success in function TestTIM - param.timer_cancel_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in function TestTIM - param.timer_cancel_time is not TRUE.");
end if;

if ( param.timer_remaining_time >= 500000 )
  LOG::LogSuccess(message:"Success in function TestTIM - param.timer_remaining_time is >= 500000.");
else
  LOG::LogFailure(message:"Failure in function TestTIM - param.timer_remaining_time is < 500000.");
end if;

if ( TIM::timer_cancel( timer_inst_ref: param.timer_start ) == TRUE )
  LOG::LogSuccess(message:"Success in function TestTIM - param.timer_start was cancelled.");
else
  LOG::LogFailure(message:"Failure in function TestTIM - param.timer_start was not cancelled.");
end if;

if ( TIM::timer_cancel( timer_inst_ref: param.timer_start_recurring ) == TRUE )
  LOG::LogSuccess(message:"Success in function TestTIM - param.timer_start_recurring was cancelled.");
else
  LOG::LogFailure(message:"Failure in function TestTIM - param.timer_start_recurring was not cancelled.");
end if;

//////////////////////////////////////////////
// TIM::current_clock
//////////////////////////////////////////////
clock = TIM::current_clock();
if ( param.current_clock <= clock )
  LOG::LogSuccess(message:"Success in function TestTIM - param.current_clock is less than or equal to clock.");
else
  LOG::LogFailure(message:"Failure in function TestTIM - param.current_clock is greater than clock.");
end if;

//////////////////////////////////////////////
// TIM::current_date
//////////////////////////////////////////////
today = TIM::current_date();

//////////////////////////////////////////////
// TIM::get_day
// TIM::get_hour
// TIM::get_minute
// TIM::get_month
// TIM::get_second
// TIM::get_year
// TIM::create_date
//////////////////////////////////////////////
sec = TIM::get_second( date: today );

if ( today == TIM::create_date( second:sec, minute: TIM::get_minute( date: today ), hour: TIM::get_hour( date: today ), day: TIM::get_day( date: today ), month: TIM::get_month( date: today ), year:TIM::get_year( date: today ) ) )
  LOG::LogSuccess(message:"Success in function TestTIM - TIM::create_date using TIM invocations.");
else
  LOG::LogFailure(message:"Failure in function TestTIM - TIM::create_date using TIM invocations.");
end if;

//////////////////////////////////////////////
// TIM::timer_start
// TIM::timer_remaining_time
//////////////////////////////////////////////
create event instance e of BC1(type:1) to BC creator;
timer1 = TIM::timer_start( microseconds: 5000000, event_inst: e );

create object instance bc3 of BC3;
bc3.type = 1;
bc3.timer = timer1;

// Log the remaining time.
time_remain = TIM::timer_remaining_time( timer_inst_ref: timer1 );
LOG::LogInt( int: time_remain, message: "Function TestTIM - Time remaining on timer1." );

//////////////////////////////////////////////
// TIM::timer_start_recurring
// TIM::timer_remaining_time
//////////////////////////////////////////////
create object instance bc of BC;
bc.num_recur = 0;
num_recur = bc.num_recur;

create event instance e2 of BC1(type:0) to bc;
timer2 = TIM::timer_start_recurring( microseconds: 1, event_inst: e2 );

//////////////////////////////////////////////
// TIM::timer_add_time - live timer
// TIM::timer_reset_time - live timer
// TIM::timer_cancel - live timer
//////////////////////////////////////////////
// Add time to the live timer
if ( TIM::timer_add_time( timer_inst_ref: timer2, microseconds: 60000000 ) == TRUE )
  LOG::LogSuccess( message: "Function TestTIM - TIM::timer_add_time returned that timer2 has had time added.");
    
  // Check the remaining time to ensure that the add worked
  if ( TIM::timer_remaining_time( timer_inst_ref: timer2 ) > 58000000 )
     LOG::LogSuccess(message:"Function TestTIM - timer2 has had time added successfully.");
  else
    LOG::LogFailure( message: "Function TestTIM - timer2 has not had time added.");
  end if;

else
  LOG::LogFailure( message: "Function TestTIM - TIM::timer_add_time returned that timer2 has not had time added.");
end if;

// Reset the live timer
if ( TIM::timer_reset_time( timer_inst_ref: timer2, microseconds: 5000000  ) == TRUE )
  LOG::LogSuccess( message: "Function TestTIM - TIM::timer_reset_time returned that timer2 was reset.");

  // Check if the timer actually was reset
  if ( TIM::timer_remaining_time( timer_inst_ref: timer2 ) < 6000000 )
     LOG::LogSuccess(message:"Function TestTIM - timer2 has had time reset successfully.");
  else
    LOG::LogFailure( message: "Function TestTIM - timer2 has not had time reset.");
  end if;
else
  LOG::LogFailure( message: "Function TestTIM - TIM::timer_reset_time returned that timer2 was not reset.");
end if;

// Try to cancel the live timer.
if ( TIM::timer_cancel( timer_inst_ref: timer2 ) == TRUE )
  LOG::LogSuccess(message:"Function TestTIM - TIM::timer_cancel returned that timer2 was cancelled.");
else
  LOG::LogFailure(message:"Function TestTIM - TIM::timer_cancel returned that timer2 was cancelled.");
end if;',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524290,
	524290,
	'create_date',
	524302,
	0);
INSERT INTO S_SPARM
	VALUES (524291,
	524290,
	'current_clock',
	524303,
	0);
INSERT INTO S_SPARM
	VALUES (524292,
	524290,
	'current_date',
	524302,
	0);
INSERT INTO S_SPARM
	VALUES (524293,
	524290,
	'get_day',
	524291,
	0);
INSERT INTO S_SPARM
	VALUES (524294,
	524290,
	'get_hour',
	524291,
	0);
INSERT INTO S_SPARM
	VALUES (524295,
	524290,
	'get_minute',
	524291,
	0);
INSERT INTO S_SPARM
	VALUES (524296,
	524290,
	'get_month',
	524291,
	0);
INSERT INTO S_SPARM
	VALUES (524297,
	524290,
	'get_second',
	524291,
	0);
INSERT INTO S_SPARM
	VALUES (524298,
	524290,
	'get_year',
	524291,
	0);
INSERT INTO S_SPARM
	VALUES (524299,
	524290,
	'timer_time_add',
	524290,
	0);
INSERT INTO S_SPARM
	VALUES (524300,
	524290,
	'timer_cancel',
	524290,
	0);
INSERT INTO S_SPARM
	VALUES (524301,
	524290,
	'timer_remaining_time',
	524291,
	0);
INSERT INTO S_SPARM
	VALUES (524302,
	524290,
	'timer_reset_time',
	524290,
	0);
INSERT INTO S_SPARM
	VALUES (524303,
	524290,
	'timer_start',
	524304,
	0);
INSERT INTO S_SPARM
	VALUES (524304,
	524290,
	'timer_start_recurring',
	524304,
	0);
INSERT INTO S_SYNC
	VALUES (524291,
	219826,
	'TestTIM2',
	'',
	'select any bc3 from instances of BC3 where selected.type == 1;
timer1 = bc3.timer;

//////////////////////////////////////////////
// TIM::timer_add_time - dead timer
// TIM::timer_reset_time - dead timer
// TIM::timer_cancel - dead timer
//////////////////////////////////////////////
// Try to add time to the dead timer.
if ( TIM::timer_add_time( timer_inst_ref: timer1, microseconds: 1000000 ) == FALSE )
  LOG::LogSuccess( message: "Function TestTIM - TIM::timer_add_time returned that timer1 has popped.");
else
  LOG::LogFailure( message: "Function TestTIM - TIM::timer_add_time returned that timer1 has not popped.");
end if;

// Try to reset the time on the dead timer.
if ( TIM::timer_reset_time( timer_inst_ref: timer1, microseconds: 1000000  ) == FALSE )
  LOG::LogSuccess( message: "Function TestTIM - TIM::timer_reset_time returned that timer1 has popped.");
else
  LOG::LogFailure( message: "Function TestTIM - TIM::timer_reset_time returned that timer1 has not popped.");
end if;

// Try to cancel the dead timer.
if ( TIM::timer_cancel( timer_inst_ref: timer1 ) == FALSE )
  LOG::LogSuccess(message:"Function TestTIM - TIM::timer_cancel returned that timer1 has popped.");
else
  LOG::LogFailure(message:"Function TestTIM - TIM::timer_cancel returned that timer1 has not popped.");
end if;
',
	524289,
	1);
INSERT INTO S_EE
	VALUES (524289,
	'Logging ',
	'',
	'LOG',
	219826);
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
	VALUES (524305,
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
	VALUES (524306,
	524290,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524291,
	524289,
	'LogInfo',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524307,
	524291,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524292,
	524289,
	'LogInt',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524308,
	524292,
	'int',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524309,
	524292,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524293,
	524289,
	'LogDate',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524310,
	524293,
	'date',
	524302,
	0);
INSERT INTO S_BPARM
	VALUES (524311,
	524293,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524294,
	524289,
	'LogTime',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524312,
	524294,
	'timestamp',
	524303,
	0);
INSERT INTO S_BPARM
	VALUES (524313,
	524294,
	'message',
	524293,
	0);
INSERT INTO S_EE
	VALUES (524290,
	'Time',
	'',
	'TIM',
	219826);
INSERT INTO S_BRG
	VALUES (524295,
	524290,
	'current_date',
	'',
	1,
	524302,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524296,
	524290,
	'create_date',
	'',
	1,
	524302,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524314,
	524296,
	'second',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524315,
	524296,
	'minute',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524316,
	524296,
	'hour',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524317,
	524296,
	'day',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524318,
	524296,
	'month',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524319,
	524296,
	'year',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524297,
	524290,
	'get_second',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524320,
	524297,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524298,
	524290,
	'get_minute',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524321,
	524298,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524299,
	524290,
	'get_hour',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524322,
	524299,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524300,
	524290,
	'get_day',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524323,
	524300,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524301,
	524290,
	'get_month',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524324,
	524301,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524302,
	524290,
	'get_year',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524325,
	524302,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524303,
	524290,
	'current_clock',
	'',
	1,
	524303,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524304,
	524290,
	'timer_start',
	'',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524326,
	524304,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524327,
	524304,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524305,
	524290,
	'timer_start_recurring',
	'',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524328,
	524305,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524329,
	524305,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524306,
	524290,
	'timer_restart',
	'',
	1,
	524289,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524330,
	524306,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524331,
	524306,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524332,
	524306,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524307,
	524290,
	'timer_remaining_time',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524333,
	524307,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BRG
	VALUES (524308,
	524290,
	'timer_reset_time',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524334,
	524308,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524335,
	524308,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524309,
	524290,
	'timer_add_time',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524336,
	524309,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524337,
	524309,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524310,
	524290,
	'timer_cancel',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524338,
	524310,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_EE
	VALUES (524291,
	'Architecture',
	'',
	'ARCH',
	219826);
INSERT INTO S_BRG
	VALUES (524311,
	524291,
	'shutdown',
	'',
	0,
	524289,
	'control stop;',
	1);
INSERT INTO S_EE
	VALUES (524292,
	'Generated EE',
	'',
	'GEE',
	219826);
INSERT INTO S_BRG
	VALUES (524312,
	524292,
	'TestTIM',
	'Test the TIM bridges as parameters to a generated bridge.
Test parameter values from TIM bridges passed as parameters.
Test the TIM bridges called from within a generated bridge.',
	0,
	524289,
	'//////////////////////////////////////////////
// Test incoming parameters.
//////////////////////////////////////////////
if ( param.create_date == param.current_date )
  LOG::LogSuccess(message:"Success in generated bridge TestTIM - param.create_date is equal to param.current_date.");
else
  LOG::LogFailure(message:"Failure in generated bridge TestTIM - param.create_date is not equal to param.current_date.");
end if;

if ( ( param.get_second + param.get_minute + param.get_hour + param.get_day + param.get_month + param.get_year ) == 6 )
  LOG::LogSuccess(message:"Success in generated bridge TestTIM - Sum of param.get_* is equal to six(6).");
else
  LOG::LogFailure(message:"Failure in generated bridge TestTIM - Sum of param.get_* is not equal to six(6).");
end if;

if ( param.timer_time_add )
  LOG::LogSuccess(message:"Success in generated bridge TestTIM - param.timer_add_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in generated bridge TestTIM - param.timer_add_time is not TRUE.");
end if;

if ( param.timer_reset_time )
  LOG::LogSuccess(message:"Success in generated bridge TestTIM - param.timer_reset_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in generated bridge TestTIM - param.timer_reset_time is not TRUE.");
end if;

if ( param.timer_cancel )
  LOG::LogSuccess(message:"Success in generated bridge TestTIM - param.timer_cancel_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in generated bridge TestTIM - param.timer_cancel_time is not TRUE.");
end if;

if ( param.timer_remaining_time >= 500000 )
  LOG::LogSuccess(message:"Success in generated bridge TestTIM - param.timer_remaining_time is >= 500000.");
else
  LOG::LogFailure(message:"Failure in generated bridge TestTIM - param.timer_remaining_time is < 500000.");
end if;

if ( TIM::timer_cancel( timer_inst_ref: param.timer_start ) == TRUE )
  LOG::LogSuccess(message:"Success in generated bridge TestTIM - param.timer_start was cancelled.");
else
  LOG::LogFailure(message:"Failure in generated bridge TestTIM - param.timer_start was not cancelled.");
end if;

if ( TIM::timer_cancel( timer_inst_ref: param.timer_start_recurring ) == TRUE )
  LOG::LogSuccess(message:"Success in generated bridge TestTIM - param.timer_start_recurring was cancelled.");
else
  LOG::LogFailure(message:"Failure in generated bridge TestTIM - param.timer_start_recurring was not cancelled.");
end if;

//////////////////////////////////////////////
// TIM::current_clock
//////////////////////////////////////////////
clock = TIM::current_clock();
if ( param.current_clock <= clock )
  LOG::LogSuccess(message:"Success in generated bridge TestTIM - param.current_clock is less than or equal to clock.");
else
  LOG::LogFailure(message:"Failure in generated bridge TestTIM - param.current_clock is greater than clock.");
end if;

//////////////////////////////////////////////
// TIM::current_date
//////////////////////////////////////////////
today = TIM::current_date();

//////////////////////////////////////////////
// TIM::get_day
// TIM::get_hour
// TIM::get_minute
// TIM::get_month
// TIM::get_second
// TIM::get_year
// TIM::create_date
//////////////////////////////////////////////
sec = TIM::get_second( date: today );

if ( today == TIM::create_date( second:sec, minute: TIM::get_minute( date: today ), hour: TIM::get_hour( date: today ), day: TIM::get_day( date: today ), month: TIM::get_month( date: today ), year:TIM::get_year( date: today ) ) )
  LOG::LogSuccess(message:"Success in generated bridge TestTIM - TIM::create_date using TIM invocations.");
else
  LOG::LogFailure(message:"Failure in generated bridge TestTIM - TIM::create_date using TIM invocations.");
end if;

//////////////////////////////////////////////
// TIM::timer_start
// TIM::timer_remaining_time
//////////////////////////////////////////////
create event instance e of BC1(type:2) to BC creator;
timer1 = TIM::timer_start( microseconds: 5000000, event_inst: e );

create object instance bc3 of BC3;
bc3.type = 2;
bc3.timer = timer1;

// Log the remaining time.
time_remain = TIM::timer_remaining_time( timer_inst_ref: timer1 );
LOG::LogInt( int: time_remain, message: "Generated Bridge TestTIM - Time remaining on timer1." );

//////////////////////////////////////////////
// TIM::timer_start_recurring
// TIM::timer_remaining_time
//////////////////////////////////////////////
create object instance bc of BC;
bc.num_recur = 0;
num_recur = bc.num_recur;

create event instance e2 of BC1(type:0) to bc;
timer2 = TIM::timer_start_recurring( microseconds: 1, event_inst: e2 );

//////////////////////////////////////////////
// TIM::timer_add_time - live timer
// TIM::timer_reset_time - live timer
// TIM::timer_cancel - live timer
//////////////////////////////////////////////
// Add time to the live timer
if ( TIM::timer_add_time( timer_inst_ref: timer2, microseconds: 60000000 ) == TRUE )
  LOG::LogSuccess( message: "Generated Bridge TestTIM - TIM::timer_add_time returned that timer2 has had time added.");
    
  // Check the remaining time to ensure that the add worked
  if ( TIM::timer_remaining_time( timer_inst_ref: timer2 ) > 58000000 )
     LOG::LogSuccess(message:"Generated Bridge TestTIM - timer2 has had time added successfully.");
  else
    LOG::LogFailure( message: "Generated Bridge TestTIM - timer2 has not had time added.");
  end if;

else
  LOG::LogFailure( message: "Generated Bridge TestTIM - TIM::timer_add_time returned that timer2 has not had time added.");
end if;

// Reset the live timer
if ( TIM::timer_reset_time( timer_inst_ref: timer2, microseconds: 5000000  ) == TRUE )
  LOG::LogSuccess( message: "Generated Bridge TestTIM - TIM::timer_reset_time returned that timer2 was reset.");

  // Check if the timer actually was reset
  if ( TIM::timer_remaining_time( timer_inst_ref: timer2 ) < 6000000 )
     LOG::LogSuccess(message:"Generated Bridge TestTIM - timer2 has had time reset successfully.");
  else
    LOG::LogFailure( message: "Generated Bridge TestTIM - timer2 has not had time reset.");
  end if;
else
  LOG::LogFailure( message: "Generated Bridge TestTIM - TIM::timer_reset_time returned that timer2 was not reset.");
end if;

// Try to cancel the live timer.
if ( TIM::timer_cancel( timer_inst_ref: timer2 ) == TRUE )
  LOG::LogSuccess(message:"Generated Bridge TestTIM - TIM::timer_cancel returned that timer2 was cancelled.");
else
  LOG::LogFailure(message:"Generated Bridge TestTIM - TIM::timer_cancel returned that timer2 was cancelled.");
end if;',
	1);
INSERT INTO S_BPARM
	VALUES (524339,
	524312,
	'create_date',
	524302,
	0);
INSERT INTO S_BPARM
	VALUES (524340,
	524312,
	'current_clock',
	524303,
	0);
INSERT INTO S_BPARM
	VALUES (524341,
	524312,
	'current_date',
	524302,
	0);
INSERT INTO S_BPARM
	VALUES (524342,
	524312,
	'get_day',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524343,
	524312,
	'get_hour',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524344,
	524312,
	'get_minute',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524345,
	524312,
	'get_month',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524346,
	524312,
	'get_second',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524347,
	524312,
	'get_year',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524348,
	524312,
	'timer_time_add',
	524290,
	0);
INSERT INTO S_BPARM
	VALUES (524349,
	524312,
	'timer_cancel',
	524290,
	0);
INSERT INTO S_BPARM
	VALUES (524350,
	524312,
	'timer_remaining_time',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524351,
	524312,
	'timer_reset_time',
	524290,
	0);
INSERT INTO S_BPARM
	VALUES (524352,
	524312,
	'timer_start',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524353,
	524312,
	'timer_start_recurring',
	524304,
	0);
INSERT INTO S_BRG
	VALUES (524315,
	524292,
	'TestTIM2',
	'',
	0,
	524289,
	'select any bc3 from instances of BC3 where selected.type == 2;
timer1 = bc3.timer;

//////////////////////////////////////////////
// TIM::timer_add_time - dead timer
// TIM::timer_reset_time - dead timer
// TIM::timer_cancel - dead timer
//////////////////////////////////////////////
// Try to add time to the dead timer.
if ( TIM::timer_add_time( timer_inst_ref: timer1, microseconds: 1000000 ) == FALSE )
  LOG::LogSuccess( message: "Generated Bridge TestTIM - TIM::timer_add_time returned that timer1 has popped.");
else
  LOG::LogFailure( message: "Generated Bridge TestTIM - TIM::timer_add_time returned that timer1 has not popped.");
end if;

// Try to reset the time on the dead timer.
if ( TIM::timer_reset_time( timer_inst_ref: timer1, microseconds: 1000000  ) == FALSE )
  LOG::LogSuccess( message: "Generated Bridge TestTIM - TIM::timer_reset_time returned that timer1 has popped.");
else
  LOG::LogFailure( message: "Generated Bridge TestTIM - TIM::timer_reset_time returned that timer1 has not popped.");
end if;

// Try to cancel the dead timer.
if ( TIM::timer_cancel( timer_inst_ref: timer1 ) == FALSE )
  LOG::LogSuccess(message:"Generated Bridge TestTIM - TIM::timer_cancel returned that timer1 has popped.");
else
  LOG::LogFailure(message:"Generated Bridge TestTIM - TIM::timer_cancel returned that timer1 has not popped.");
end if;
',
	1);
INSERT INTO S_EE
	VALUES (524293,
	'Realized EE',
	'',
	'REE',
	219826);
INSERT INTO S_BRG
	VALUES (524313,
	524293,
	'TestTIM',
	'Test the TIM bridges as parameters to a realized bridge.
Test parameter values from TIM bridges passed as parameters.
Test the TIM bridges called from within a realized bridge.',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524354,
	524313,
	'create_date',
	524302,
	0);
INSERT INTO S_BPARM
	VALUES (524355,
	524313,
	'current_clock',
	524303,
	0);
INSERT INTO S_BPARM
	VALUES (524356,
	524313,
	'current_date',
	524302,
	0);
INSERT INTO S_BPARM
	VALUES (524357,
	524313,
	'get_day',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524358,
	524313,
	'get_hour',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524359,
	524313,
	'get_minute',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524360,
	524313,
	'get_month',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524361,
	524313,
	'get_second',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524362,
	524313,
	'get_year',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524363,
	524313,
	'timer_time_add',
	524290,
	0);
INSERT INTO S_BPARM
	VALUES (524364,
	524313,
	'timer_cancel',
	524290,
	0);
INSERT INTO S_BPARM
	VALUES (524365,
	524313,
	'timer_remaining_time',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524366,
	524313,
	'timer_reset_time',
	524290,
	0);
INSERT INTO S_BPARM
	VALUES (524367,
	524313,
	'timer_start',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524368,
	524313,
	'timer_start_recurring',
	524304,
	0);
INSERT INTO GD_MD
	VALUES (524289,
	1,
	219826,
	1,
	1,
	0,
	1,
	1,
	0,
	12,
	1455,
	4213,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (524293,
	524289,
	524289,
	12);
INSERT INTO GD_SHP
	VALUES (524293,
	1776,
	1488,
	1936,
	1584);
INSERT INTO GD_GE
	VALUES (524294,
	524289,
	524290,
	12);
INSERT INTO GD_SHP
	VALUES (524294,
	1584,
	1488,
	1744,
	1584);
INSERT INTO GD_GE
	VALUES (524295,
	524289,
	524291,
	12);
INSERT INTO GD_SHP
	VALUES (524295,
	1968,
	1488,
	2128,
	1584);
INSERT INTO GD_GE
	VALUES (524296,
	524289,
	524292,
	12);
INSERT INTO GD_SHP
	VALUES (524296,
	1584,
	1616,
	1744,
	1712);
INSERT INTO GD_GE
	VALUES (524297,
	524289,
	524293,
	12);
INSERT INTO GD_SHP
	VALUES (524297,
	1776,
	1616,
	1936,
	1712);
INSERT INTO GD_GE
	VALUES (524298,
	524289,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524298,
	1776,
	1360,
	1936,
	1456);
INSERT INTO GD_GE
	VALUES (524299,
	524289,
	2621445,
	11);
INSERT INTO GD_SHP
	VALUES (524299,
	1968,
	1360,
	2128,
	1456);
INSERT INTO GD_GE
	VALUES (524300,
	524289,
	3670023,
	11);
INSERT INTO GD_SHP
	VALUES (524300,
	1584,
	1360,
	1744,
	1456);
INSERT INTO GD_MD
	VALUES (524290,
	2,
	219826,
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
	VALUES (524301,
	524290,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524301,
	1920,
	1344,
	2080,
	1440);
INSERT INTO GD_GE
	VALUES (524302,
	524290,
	2621445,
	11);
INSERT INTO GD_SHP
	VALUES (524302,
	2048,
	1360,
	2208,
	1456);
INSERT INTO GD_GE
	VALUES (524303,
	524290,
	3670023,
	11);
INSERT INTO GD_SHP
	VALUES (524303,
	1760,
	1520,
	1920,
	1616);
INSERT INTO GD_MD
	VALUES (524291,
	3,
	219826,
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
	VALUES (524304,
	524291,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524304,
	1920,
	1344,
	2080,
	1440);
INSERT INTO GD_GE
	VALUES (524305,
	524291,
	2621445,
	11);
INSERT INTO GD_SHP
	VALUES (524305,
	2048,
	1360,
	2208,
	1456);
INSERT INTO GD_GE
	VALUES (524306,
	524291,
	3670023,
	11);
INSERT INTO GD_SHP
	VALUES (524306,
	1760,
	1520,
	1920,
	1616);
INSERT INTO GD_MD
	VALUES (524292,
	4,
	219826,
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
	524292,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524307,
	1920,
	1344,
	2080,
	1440);
INSERT INTO GD_GE
	VALUES (524308,
	524292,
	2621445,
	11);
INSERT INTO GD_SHP
	VALUES (524308,
	2048,
	1360,
	2208,
	1456);
INSERT INTO GD_GE
	VALUES (524309,
	524292,
	3670023,
	11);
INSERT INTO GD_SHP
	VALUES (524309,
	1760,
	1520,
	1920,
	1616);
INSERT INTO S_SS
	VALUES (1048578,
	'Time Date Primitives',
	'',
	'',
	201,
	219826,
	1048578);
INSERT INTO O_OBJ
	VALUES (1048577,
	'Time Date Primitive Test',
	1,
	'TDPT',
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
	'date',
	'',
	'',
	'date',
	0,
	524302);
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
	'day',
	'',
	'',
	'day',
	0,
	524291);
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
	'month',
	'',
	'',
	'month',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048581,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048581,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048581,
	1048577,
	1048580,
	'year',
	'',
	'',
	'year',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048582,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048582,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048582,
	1048577,
	1048581,
	'timestamp',
	'',
	'',
	'timestamp',
	0,
	524303);
INSERT INTO O_NBATTR
	VALUES (1048583,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048583,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048583,
	1048577,
	1048582,
	'second',
	'',
	'',
	'second',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048584,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048584,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048584,
	1048577,
	1048583,
	'minute',
	'',
	'',
	'minute',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048585,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048585,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048585,
	1048577,
	1048584,
	'hour',
	'',
	'',
	'hour',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048586,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048586,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048586,
	1048577,
	1048585,
	'int',
	'',
	'',
	'int',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048587,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048587,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048587,
	1048577,
	1048586,
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
	'Running TM1 Test',
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
	2,
	'Finish TM1 Test',
	0,
	'',
	'TDPT2',
	'');
INSERT INTO SM_SEME
	VALUES (1572865,
	1572865,
	1572867,
	1572865);
INSERT INTO SM_LEVT
	VALUES (1572866,
	1572867,
	1572865);
INSERT INTO SM_SEVT
	VALUES (1572866,
	1572867,
	1572865);
INSERT INTO SM_EVT
	VALUES (1572866,
	1572867,
	1572865,
	0,
	'Start TM1 Test',
	0,
	'',
	'TDPT0',
	'');
INSERT INTO SM_SEME
	VALUES (1572865,
	1572866,
	1572867,
	1572865);
INSERT INTO SM_STATE
	VALUES (1572866,
	1572867,
	1572865,
	'Finishing TM1 Test',
	3,
	1);
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
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572866,
	1572866,
	1572867,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572865,
	1572867,
	1572865,
	1572866,
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
	1572865,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572866,
	1572867,
	1572866,
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
	'::LogDateAndTime(message:"TM1: Test Starting - Testing Time Date Primitives") ;

// Create a bogus date
bridge self.date = TIM::create_date (second:2, minute:56, hour:19, day:3, month:1, year:1996);

// Check Day
bridge self.day = TIM::get_day (date:self.date);
if (self.day == 3)
  LOG::LogSuccess(message:"TM1: get_day()") ;
else
  LOG::LogFailure(message:"TM1: get_day()") ;  
end if;

// Check Month
bridge self.month = TIM::get_month (date:self.date);
if (self.month == 1)
  LOG::LogSuccess(message:"TM1: get_month()") ;
else
  LOG::LogFailure(message:"TM1: get_month()") ;  
end if;

// Check Year
bridge self.year = TIM::get_year (date:self.date);
if (self.year == 1996)
  LOG::LogSuccess(message:"TM1: get_year()") ;
else
  LOG::LogFailure(message:"TM1: get_year()") ;  
end if;

// Check Second
bridge self.second = TIM::get_second (date:self.date);
if (self.second == 2)
  LOG::LogSuccess(message:"TM1: get_second()") ;
else
  LOG::LogFailure(message:"TM1: get_second()") ;  
end if;

// Check Minute
bridge self.minute = TIM::get_minute (date:self.date);
if (self.minute == 56)
  LOG::LogSuccess(message:"TM1: get_minute()") ;
else
  LOG::LogFailure(message:"TM1: get_minute()") ;  
end if;

// Check hour
bridge self.hour = TIM::get_hour (date:self.date);
if (self.hour == 19)
  LOG::LogSuccess(message:"TM1: get_hour()") ;
else
  LOG::LogFailure(message:"TM1: get_hour()") ;  
end if;

generate TDPT2:''Finish TM1 Test''() to self;
',
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
	'select any driver from instances of TMD;
generate TMD2:''TM1 Test Complete''() to driver;
',
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
INSERT INTO GD_GE
	VALUES (1572866,
	1572865,
	1572865,
	41);
INSERT INTO GD_SHP
	VALUES (1572866,
	1664,
	1312,
	2032,
	1616);
INSERT INTO GD_GE
	VALUES (1572867,
	1572865,
	1572866,
	41);
INSERT INTO GD_SHP
	VALUES (1572867,
	2080,
	1312,
	2384,
	1440);
INSERT INTO GD_GE
	VALUES (1572868,
	1572865,
	1572865,
	42);
INSERT INTO GD_CON
	VALUES (1572868,
	1572866,
	1572866,
	0);
INSERT INTO GD_CTXT
	VALUES (1572868,
	0,
	0,
	0,
	0,
	0,
	0,
	1586,
	1252,
	1672,
	1307,
	18,
	11,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572869,
	1572868,
	1664,
	1408,
	1584,
	1408,
	0);
INSERT INTO GD_LS
	VALUES (1572870,
	1572868,
	1584,
	1408,
	1584,
	1248,
	1572869);
INSERT INTO GD_LS
	VALUES (1572871,
	1572868,
	1584,
	1248,
	1744,
	1248,
	1572870);
INSERT INTO GD_LS
	VALUES (1572872,
	1572868,
	1744,
	1248,
	1744,
	1312,
	1572871);
INSERT INTO GD_GE
	VALUES (1572873,
	1572865,
	1572866,
	42);
INSERT INTO GD_CON
	VALUES (1572873,
	1572866,
	1572867,
	0);
INSERT INTO GD_CTXT
	VALUES (1572873,
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
	VALUES (1572874,
	1572873,
	1984,
	1312,
	1984,
	1264,
	0);
INSERT INTO GD_LS
	VALUES (1572875,
	1572873,
	1984,
	1264,
	2160,
	1264,
	1572874);
INSERT INTO GD_LS
	VALUES (1572876,
	1572873,
	2160,
	1264,
	2160,
	1312,
	1572875);
INSERT INTO O_OBJ
	VALUES (1048578,
	'TM Test Driver',
	4,
	'TMD',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048588,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048588,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048588,
	1048578,
	0,
	'tmd_id',
	'',
	'',
	'tmd_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048589,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048589,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048589,
	1048578,
	1048588,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_DBATTR
	VALUES (1048590,
	1048578,
	'//////////////////////////////////////////////
// TIM::current_clock
//////////////////////////////////////////////
clock = TIM::current_clock();

//////////////////////////////////////////////
// TIM::current_date
//////////////////////////////////////////////
today = TIM::current_date();

//////////////////////////////////////////////
// TIM::get_day
// TIM::get_hour
// TIM::get_minute
// TIM::get_month
// TIM::get_second
// TIM::get_year
// TIM::create_date
//////////////////////////////////////////////
sec = TIM::get_second( date: today );

if ( today == TIM::create_date( second:sec, minute: TIM::get_minute( date: today ), hour: TIM::get_hour( date: today ), day: TIM::get_day( date: today ), month: TIM::get_month( date: today ), year:TIM::get_year( date: today ) ) )
  LOG::LogSuccess(message:"Success in mathematically derived attribute TestTIM - TIM::create_date using TIM invocations.");
else
  LOG::LogFailure(message:"Failure in mathematically derived attribute TestTIM - TIM::create_date using TIM invocations.");
end if;

//////////////////////////////////////////////
// TIM::timer_start
// TIM::timer_remaining_time
//////////////////////////////////////////////
create event instance e of BC1(type:5) to BC creator;
timer1 = TIM::timer_start( microseconds: 5000000, event_inst: e );

create object instance bc3 of BC3;
bc3.type = 5;
bc3.timer = timer1;

// Log the remaining time.
time_remain = TIM::timer_remaining_time( timer_inst_ref: timer1 );
LOG::LogInt( int: time_remain, message: "Mathematically Derived Attribute TestTIM - Time remaining on timer1." );

//////////////////////////////////////////////
// TIM::timer_start_recurring
// TIM::timer_remaining_time
//////////////////////////////////////////////
create object instance bc of BC;
bc.num_recur = 0;
num_recur = bc.num_recur;

create event instance e2 of BC1(type:0) to bc;
timer2 = TIM::timer_start_recurring( microseconds: 1, event_inst: e2 );

//////////////////////////////////////////////
// TIM::timer_add_time - live timer
// TIM::timer_reset_time - live timer
// TIM::timer_cancel - live timer
//////////////////////////////////////////////
// Add time to the live timer
if ( TIM::timer_add_time( timer_inst_ref: timer2, microseconds: 60000000 ) == TRUE )
  LOG::LogSuccess( message: "Mathematically Derived Attribute TestTIM - TIM::timer_add_time returned that timer2 has had time added.");
    
  // Check the remaining time to ensure that the add worked
  if ( TIM::timer_remaining_time( timer_inst_ref: timer2 ) > 58000000 )
     LOG::LogSuccess(message:"Mathematically Derived Attribute TestTIM - timer2 has had time added successfully.");
  else
    LOG::LogFailure( message: "Mathematically Derived Attribute TestTIM - timer2 has not had time added.");
  end if;

else
  LOG::LogFailure( message: "Mathematically Derived Attribute TestTIM - TIM::timer_add_time returned that timer2 has not had time added.");
end if;

// Reset the live timer
if ( TIM::timer_reset_time( timer_inst_ref: timer2, microseconds: 5000000  ) == TRUE )
  LOG::LogSuccess( message: "Mathematically Derived Attribute TestTIM - TIM::timer_reset_time returned that timer2 was reset.");

  // Check if the timer actually was reset
  if ( TIM::timer_remaining_time( timer_inst_ref: timer2 ) < 6000000 )
     LOG::LogSuccess(message:"Mathematically Derived Attribute TestTIM - timer2 has had time reset successfully.");
  else
    LOG::LogFailure( message: "Mathematically Derived Attribute TestTIM - timer2 has not had time reset.");
  end if;
else
  LOG::LogFailure( message: "Mathematically Derived Attribute TestTIM - TIM::timer_reset_time returned that timer2 was not reset.");
end if;

// Try to cancel the live timer.
if ( TIM::timer_cancel( timer_inst_ref: timer2 ) == TRUE )
  LOG::LogSuccess(message:"Mathematically Derived Attribute TestTIM - TIM::timer_cancel returned that timer2 was cancelled.");
else
  LOG::LogFailure(message:"Mathematically Derived Attribute TestTIM - TIM::timer_cancel returned that timer2 was cancelled.");
end if;

// We need to assign it...
self.mda = -1;',
	1);
INSERT INTO O_BATTR
	VALUES (1048590,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048590,
	1048578,
	1048589,
	'mda',
	'',
	'',
	'mda',
	0,
	524291);
INSERT INTO O_DBATTR
	VALUES (1048591,
	1048578,
	'select any bc3 from instances of BC3 where selected.type == 5;
timer1 = bc3.timer;

//////////////////////////////////////////////
// TIM::timer_add_time - dead timer
// TIM::timer_reset_time - dead timer
// TIM::timer_cancel - dead timer
//////////////////////////////////////////////
// Try to add time to the dead timer.
if ( TIM::timer_add_time( timer_inst_ref: timer1, microseconds: 1000000 ) == FALSE )
  LOG::LogSuccess( message: "Mathematically Derived Attribute TestTIM - TIM::timer_add_time returned that timer1 has popped.");
else
  LOG::LogFailure( message: "Mathematically Derived Attribute TestTIM - TIM::timer_add_time returned that timer1 has not popped.");
end if;

// Try to reset the time on the dead timer.
if ( TIM::timer_reset_time( timer_inst_ref: timer1, microseconds: 1000000  ) == FALSE )
  LOG::LogSuccess( message: "Mathematically Derived Attribute TestTIM - TIM::timer_reset_time returned that timer1 has popped.");
else
  LOG::LogFailure( message: "Mathematically Derived Attribute TestTIM - TIM::timer_reset_time returned that timer1 has not popped.");
end if;

// Try to cancel the dead timer.
if ( TIM::timer_cancel( timer_inst_ref: timer1 ) == FALSE )
  LOG::LogSuccess(message:"Mathematically Derived Attribute TestTIM - TIM::timer_cancel returned that timer1 has popped.");
else
  LOG::LogFailure(message:"Mathematically Derived Attribute TestTIM - TIM::timer_cancel returned that timer1 has not popped.");
end if;

self.mda2 = 1;',
	1);
INSERT INTO O_BATTR
	VALUES (1048591,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048591,
	1048578,
	1048590,
	'mda2',
	'',
	'',
	'mda2',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	1048578);
INSERT INTO O_OIDA
	VALUES (1048588,
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
	'Running TM1 Time Date Primitive Test',
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
	'Start TM Test',
	0,
	'',
	'TMD1',
	'');
INSERT INTO SM_CH
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
	'TM1 Test Complete',
	0,
	'',
	'TMD2',
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_LEVT
	VALUES (2097155,
	2097156,
	2097153);
INSERT INTO SM_SEVT
	VALUES (2097155,
	2097156,
	2097153);
INSERT INTO SM_EVT
	VALUES (2097155,
	2097156,
	2097153,
	3,
	'TM2 Test Complete',
	0,
	'',
	'TMD3',
	'');
INSERT INTO SM_CH
	VALUES (2097153,
	2097155,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097155,
	2097156,
	2097153);
INSERT INTO SM_LEVT
	VALUES (2097156,
	2097156,
	2097153);
INSERT INTO SM_SEVT
	VALUES (2097156,
	2097156,
	2097153);
INSERT INTO SM_EVT
	VALUES (2097156,
	2097156,
	2097153,
	4,
	'Next',
	0,
	'',
	'TMD4',
	'');
INSERT INTO SM_EIGN
	VALUES (2097153,
	2097156,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097156,
	2097156,
	2097153);
INSERT INTO SM_STATE
	VALUES (2097154,
	2097156,
	2097153,
	'Running TM2 Timer Primitive Test',
	2,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_SEME
	VALUES (2097154,
	2097155,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097154,
	2097156,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097156,
	2097156,
	2097153);
INSERT INTO SM_STATE
	VALUES (2097155,
	2097156,
	2097153,
	'Shutting Down',
	3,
	1);
INSERT INTO SM_CH
	VALUES (2097155,
	2097153,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_CH
	VALUES (2097155,
	2097154,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_CH
	VALUES (2097155,
	2097155,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097155,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097155,
	2097156,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097156,
	2097156,
	2097153);
INSERT INTO SM_STATE
	VALUES (2097156,
	2097156,
	2097153,
	'Function',
	4,
	0);
INSERT INTO SM_EIGN
	VALUES (2097156,
	2097153,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097156,
	2097154,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097156,
	2097155,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097155,
	2097156,
	2097153);
INSERT INTO SM_SEME
	VALUES (2097156,
	2097156,
	2097156,
	2097153);
INSERT INTO SM_STATE
	VALUES (2097157,
	2097156,
	2097153,
	'Generated Bridge',
	5,
	0);
INSERT INTO SM_EIGN
	VALUES (2097157,
	2097153,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097157,
	2097154,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097157,
	2097155,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097155,
	2097156,
	2097153);
INSERT INTO SM_SEME
	VALUES (2097157,
	2097156,
	2097156,
	2097153);
INSERT INTO SM_STATE
	VALUES (2097158,
	2097156,
	2097153,
	'Realized Bridge',
	6,
	0);
INSERT INTO SM_EIGN
	VALUES (2097158,
	2097153,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097158,
	2097154,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097158,
	2097155,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097155,
	2097156,
	2097153);
INSERT INTO SM_SEME
	VALUES (2097158,
	2097156,
	2097156,
	2097153);
INSERT INTO SM_STATE
	VALUES (2097159,
	2097156,
	2097153,
	'MDA',
	7,
	0);
INSERT INTO SM_EIGN
	VALUES (2097159,
	2097153,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097159,
	2097154,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097159,
	2097155,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097155,
	2097156,
	2097153);
INSERT INTO SM_SEME
	VALUES (2097159,
	2097156,
	2097156,
	2097153);
INSERT INTO SM_STATE
	VALUES (2097160,
	2097156,
	2097153,
	'Generated Class Operation',
	8,
	0);
INSERT INTO SM_EIGN
	VALUES (2097160,
	2097153,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097160,
	2097154,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097160,
	2097155,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097155,
	2097156,
	2097153);
INSERT INTO SM_SEME
	VALUES (2097160,
	2097156,
	2097156,
	2097153);
INSERT INTO SM_STATE
	VALUES (2097161,
	2097156,
	2097153,
	'Generated Instance Operation',
	9,
	0);
INSERT INTO SM_EIGN
	VALUES (2097161,
	2097153,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097161,
	2097154,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097161,
	2097155,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097155,
	2097156,
	2097153);
INSERT INTO SM_SEME
	VALUES (2097161,
	2097156,
	2097156,
	2097153);
INSERT INTO SM_STATE
	VALUES (2097162,
	2097156,
	2097153,
	'Realized Class Operation',
	10,
	0);
INSERT INTO SM_EIGN
	VALUES (2097162,
	2097153,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097162,
	2097154,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097162,
	2097155,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097155,
	2097156,
	2097153);
INSERT INTO SM_SEME
	VALUES (2097162,
	2097156,
	2097156,
	2097153);
INSERT INTO SM_STATE
	VALUES (2097163,
	2097156,
	2097153,
	'Realized Instance Operation',
	11,
	0);
INSERT INTO SM_EIGN
	VALUES (2097163,
	2097153,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097163,
	2097154,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097163,
	2097155,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097155,
	2097156,
	2097153);
INSERT INTO SM_SEME
	VALUES (2097163,
	2097156,
	2097156,
	2097153);
INSERT INTO SM_STATE
	VALUES (2097164,
	2097156,
	2097153,
	'Event Supplemental Data',
	12,
	0);
INSERT INTO SM_EIGN
	VALUES (2097164,
	2097153,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097164,
	2097154,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097164,
	2097155,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097155,
	2097156,
	2097153);
INSERT INTO SM_SEME
	VALUES (2097164,
	2097156,
	2097156,
	2097153);
INSERT INTO SM_STATE
	VALUES (2097165,
	2097156,
	2097153,
	'Assigner Event Supplemental Data',
	13,
	0);
INSERT INTO SM_EIGN
	VALUES (2097165,
	2097153,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097165,
	2097154,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097165,
	2097155,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097155,
	2097156,
	2097153);
INSERT INTO SM_SEME
	VALUES (2097165,
	2097156,
	2097156,
	2097153);
INSERT INTO SM_STATE
	VALUES (2097166,
	2097156,
	2097153,
	'Creation Event Supplemental Data',
	14,
	0);
INSERT INTO SM_EIGN
	VALUES (2097166,
	2097153,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097166,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097166,
	2097154,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097166,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097166,
	2097155,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097166,
	2097155,
	2097156,
	2097153);
INSERT INTO SM_SEME
	VALUES (2097166,
	2097156,
	2097156,
	2097153);
INSERT INTO SM_STATE
	VALUES (2097168,
	2097156,
	2097153,
	'Cleanup',
	15,
	0);
INSERT INTO SM_EIGN
	VALUES (2097168,
	2097153,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097168,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097168,
	2097154,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097168,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097168,
	2097155,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097168,
	2097155,
	2097156,
	2097153);
INSERT INTO SM_SEME
	VALUES (2097168,
	2097156,
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
INSERT INTO SM_NSTXN
	VALUES (2097155,
	2097156,
	2097154,
	2097155,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097155,
	2097156,
	2097156,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097156,
	2097156,
	2097156,
	2097156,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097156,
	2097156,
	2097157,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097157,
	2097156,
	2097157,
	2097156,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097157,
	2097156,
	2097158,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097158,
	2097156,
	2097158,
	2097156,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097158,
	2097156,
	2097159,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097159,
	2097156,
	2097159,
	2097156,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097159,
	2097156,
	2097160,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097160,
	2097156,
	2097160,
	2097156,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097160,
	2097156,
	2097161,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097161,
	2097156,
	2097161,
	2097156,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097161,
	2097156,
	2097162,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097162,
	2097156,
	2097162,
	2097156,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097162,
	2097156,
	2097163,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097163,
	2097156,
	2097163,
	2097156,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097163,
	2097156,
	2097164,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097165,
	2097156,
	2097166,
	2097156,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097165,
	2097156,
	2097165,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097168,
	2097156,
	2097164,
	2097156,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097168,
	2097156,
	2097166,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097169,
	2097156,
	2097165,
	2097156,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097169,
	2097156,
	2097168,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097170,
	2097156,
	2097168,
	2097156,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097170,
	2097156,
	2097155,
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
	'create object instance tm1 of TDPT;
generate TDPT0:''Start TM1 Test''() to tm1;',
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
	'create object instance ttoa of TTOA;
generate TTOA0:''Start TM2 Test''() to ttoa;',
	'');
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
	'////////////////////////////////////////////////////////////////////////////////////////////////////////////
LOG::LogInfo(message:"TCD: TM Driver shutting down");
bridge ARCH::shutdown();',
	'');
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
	'////////////////////////////////////////////////////////////////////////////////////////////////////////////
LOG::LogInfo(message:"TCD: Testing TIM in function.");
today = TIM::current_date( );
date1 = TIM::create_date( second: 1, minute: 1, hour: 1, day: 1, month: 1, year: 1 );
create event instance e of BC1(type:0) to BC creator;
create event instance e2 of BC1(type:0) to BC creator;
create object instance bc of BC;
create event instance e3 of BC1(type:0) to bc;
timer1 = TIM::timer_start ( microseconds: 60000000, event_inst:e );

::TestTIM( create_date: TIM::create_date( second: TIM::get_second( date: today ), minute: TIM::get_minute( date: today ), hour: TIM::get_hour( date: today ), day: TIM::get_day( date: today ), month: TIM::get_month( date: today ), year: TIM::get_year( date: today ) ), current_clock: TIM::current_clock( ), current_date: today, get_second: TIM::get_second( date: date1 ), get_minute: TIM::get_minute( date: date1 ), get_hour: TIM::get_hour( date: date1 ), get_day: TIM::get_day( date: date1 ), get_month: TIM::get_month( date: date1 ), get_year: TIM::get_year( date: date1 ), timer_time_add: TIM::timer_add_time( microseconds: 30000000,  timer_inst_ref: timer1 ),  timer_remaining_time: TIM::timer_remaining_time( timer_inst_ref: timer1 ), timer_reset_time: TIM::timer_reset_time( microseconds: 30000000, timer_inst_ref: timer1 ),  timer_cancel: TIM::timer_cancel( timer_inst_ref: timer1 ), timer_start: TIM::timer_start( microseconds: 60000000, event_inst: e2 ) , timer_start_recurring: TIM::timer_start_recurring( microseconds: 60000000, event_inst: e3 ) );

generate TMD4 to self;',
	'');
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
	'////////////////////////////////////////////////////////////////////////////////////////////////////////////
LOG::LogInfo(message:"TCD: Testing TIM in generated bridge.");
today = TIM::current_date( );
date1 = TIM::create_date( second: 1, minute: 1, hour: 1, day: 1, month: 1, year: 1 );
create event instance e4 of BC1(type:0) to BC creator;
create event instance e5 of BC1(type:0) to BC creator;
create object instance bc2 of BC;
create event instance e6 of BC1(type:0) to bc2;
timer1 = TIM::timer_start ( microseconds: 60000000, event_inst:e4 );

GEE::TestTIM( create_date: TIM::create_date( second: TIM::get_second( date: today ), minute: TIM::get_minute( date: today ), hour: TIM::get_hour( date: today ), day: TIM::get_day( date: today ), month: TIM::get_month( date: today ), year: TIM::get_year( date: today ) ), current_clock: TIM::current_clock( ), current_date: today, get_second: TIM::get_second( date: date1 ), get_minute: TIM::get_minute( date: date1 ), get_hour: TIM::get_hour( date: date1 ), get_day: TIM::get_day( date: date1 ), get_month: TIM::get_month( date: date1 ), get_year: TIM::get_year( date: date1 ), timer_time_add: TIM::timer_add_time( microseconds: 30000000,  timer_inst_ref: timer1 ),  timer_remaining_time: TIM::timer_remaining_time( timer_inst_ref: timer1 ), timer_reset_time: TIM::timer_reset_time( microseconds: 30000000, timer_inst_ref: timer1 ),  timer_cancel: TIM::timer_cancel( timer_inst_ref: timer1 ), timer_start: TIM::timer_start( microseconds: 60000000, event_inst: e5 ) , timer_start_recurring: TIM::timer_start_recurring( microseconds: 60000000, event_inst: e6 )  );

generate TMD4 to self;',
	'');
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
	'////////////////////////////////////////////////////////////////////////////////////////////////////////////
LOG::LogInfo(message:"TCD: Testing TIM in realized bridge.");
today = TIM::current_date( );
date1 = TIM::create_date( second: 1, minute: 1, hour: 1, day: 1, month: 1, year: 1 );
create event instance e7 of BC1(type:0) to BC creator;
create event instance e8 of BC1(type:0) to BC creator;
create object instance bc3 of BC;
create event instance e9 of BC1(type:0) to bc3;
timer1 = TIM::timer_start ( microseconds: 60000000, event_inst:e7 );

REE::TestTIM( create_date: TIM::create_date( second: TIM::get_second( date: today ), minute: TIM::get_minute( date: today ), hour: TIM::get_hour( date: today ), day: TIM::get_day( date: today ), month: TIM::get_month( date: today ), year: TIM::get_year( date: today ) ), current_clock: TIM::current_clock( ), current_date: today, get_second: TIM::get_second( date: date1 ), get_minute: TIM::get_minute( date: date1 ), get_hour: TIM::get_hour( date: date1 ), get_day: TIM::get_day( date: date1 ), get_month: TIM::get_month( date: date1 ), get_year: TIM::get_year( date: date1 ), timer_time_add: TIM::timer_add_time( microseconds: 30000000,  timer_inst_ref: timer1 ),  timer_remaining_time: TIM::timer_remaining_time( timer_inst_ref: timer1 ), timer_reset_time: TIM::timer_reset_time( microseconds: 30000000, timer_inst_ref: timer1 ),  timer_cancel: TIM::timer_cancel( timer_inst_ref: timer1 ), timer_start: TIM::timer_start( microseconds: 60000000, event_inst: e8 ) , timer_start_recurring: TIM::timer_start_recurring( microseconds: 60000000, event_inst: e9 )  );

generate TMD4 to self;',
	'');
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
	'////////////////////////////////////////////////////////////////////////////////////////////////////////////
LOG::LogInfo(message:"TCD: Testing TIM in MDA");
mda1 = self.mda;

generate TMD4 to self;',
	'');
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
	'////////////////////////////////////////////////////////////////////////////////////////////////////////////
LOG::LogInfo(message:"TCD: Testing TIM in generated class operation.");
today = TIM::current_date( );
date1 = TIM::create_date( second: 1, minute: 1, hour: 1, day: 1, month: 1, year: 1 );
create event instance e10 of BC1(type:0) to BC creator;
create event instance e11 of BC1(type:0) to BC creator;
create object instance bc4 of BC;
create event instance e12 of BC1(type:0) to bc4;
timer1 = TIM::timer_start ( microseconds: 60000000, event_inst:e10 );

BC::TestTIM( create_date: TIM::create_date( second: TIM::get_second( date: today ), minute: TIM::get_minute( date: today ), hour: TIM::get_hour( date: today ), day: TIM::get_day( date: today ), month: TIM::get_month( date: today ), year: TIM::get_year( date: today ) ), current_clock: TIM::current_clock( ), current_date: today, get_second: TIM::get_second( date: date1 ), get_minute: TIM::get_minute( date: date1 ), get_hour: TIM::get_hour( date: date1 ), get_day: TIM::get_day( date: date1 ), get_month: TIM::get_month( date: date1 ), get_year: TIM::get_year( date: date1 ), timer_time_add: TIM::timer_add_time( microseconds: 30000000,  timer_inst_ref: timer1 ),  timer_remaining_time: TIM::timer_remaining_time( timer_inst_ref: timer1 ), timer_reset_time: TIM::timer_reset_time( microseconds: 30000000, timer_inst_ref: timer1 ),  timer_cancel: TIM::timer_cancel( timer_inst_ref: timer1 ), timer_start: TIM::timer_start( microseconds: 60000000, event_inst: e11 ) , timer_start_recurring: TIM::timer_start_recurring( microseconds: 60000000, event_inst: e12 )  );

generate TMD4 to self;',
	'');
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
	'////////////////////////////////////////////////////////////////////////////////////////////////////////////
LOG::LogInfo(message:"TCD: Testing TIM in generated  instance operation.");
create object instance bc of BC;
today = TIM::current_date( );
date1 = TIM::create_date( second: 1, minute: 1, hour: 1, day: 1, month: 1, year: 1 );
create event instance e13 of BC1(type:0) to BC creator;
create event instance e14 of BC1(type:0) to BC creator;
create object instance bc5 of BC;
create event instance e15 of BC1(type:0) to bc5;
timer1 = TIM::timer_start ( microseconds: 60000000, event_inst:e13 );

bc.TestTIM2( create_date: TIM::create_date( second: TIM::get_second( date: today ), minute: TIM::get_minute( date: today ), hour: TIM::get_hour( date: today ), day: TIM::get_day( date: today ), month: TIM::get_month( date: today ), year: TIM::get_year( date: today ) ), current_clock: TIM::current_clock( ), current_date: today, get_second: TIM::get_second( date: date1 ), get_minute: TIM::get_minute( date: date1 ), get_hour: TIM::get_hour( date: date1 ), get_day: TIM::get_day( date: date1 ), get_month: TIM::get_month( date: date1 ), get_year: TIM::get_year( date: date1 ), timer_time_add: TIM::timer_add_time( microseconds: 30000000,  timer_inst_ref: timer1 ),  timer_remaining_time: TIM::timer_remaining_time( timer_inst_ref: timer1 ), timer_reset_time: TIM::timer_reset_time( microseconds: 30000000, timer_inst_ref: timer1 ),  timer_cancel: TIM::timer_cancel( timer_inst_ref: timer1 ), timer_start: TIM::timer_start( microseconds: 60000000, event_inst: e14 ) , timer_start_recurring: TIM::timer_start_recurring( microseconds: 60000000, event_inst: e15 )  );

delete object instance bc;

generate TMD4 to self;',
	'');
INSERT INTO SM_MOAH
	VALUES (2097162,
	2097156,
	2097162);
INSERT INTO SM_AH
	VALUES (2097162,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097162,
	2097156,
	1,
	'////////////////////////////////////////////////////////////////////////////////////////////////////////////
LOG::LogInfo(message:"TCD: Testing TIM in realized class operation.");
today = TIM::current_date( );
date1 = TIM::create_date( second: 1, minute: 1, hour: 1, day: 1, month: 1, year: 1 );
create event instance e16 of BC1(type:0) to BC creator;
create event instance e17 of BC1(type:0) to BC creator;
create object instance bc6 of BC;
create event instance e18 of BC1(type:0) to bc6;
timer1 = TIM::timer_start ( microseconds: 60000000, event_inst:e16 );

BC::TestTIM3( create_date: TIM::create_date( second: TIM::get_second( date: today ), minute: TIM::get_minute( date: today ), hour: TIM::get_hour( date: today ), day: TIM::get_day( date: today ), month: TIM::get_month( date: today ), year: TIM::get_year( date: today ) ), current_clock: TIM::current_clock( ), current_date: today, get_second: TIM::get_second( date: date1 ), get_minute: TIM::get_minute( date: date1 ), get_hour: TIM::get_hour( date: date1 ), get_day: TIM::get_day( date: date1 ), get_month: TIM::get_month( date: date1 ), get_year: TIM::get_year( date: date1 ), timer_time_add: TIM::timer_add_time( microseconds: 30000000,  timer_inst_ref: timer1 ),  timer_remaining_time: TIM::timer_remaining_time( timer_inst_ref: timer1 ), timer_reset_time: TIM::timer_reset_time( microseconds: 30000000, timer_inst_ref: timer1 ),  timer_cancel: TIM::timer_cancel( timer_inst_ref: timer1 ), timer_start: TIM::timer_start( microseconds: 60000000, event_inst: e17 ) , timer_start_recurring: TIM::timer_start_recurring( microseconds: 60000000, event_inst: e18 )  );

generate TMD4 to self;',
	'');
INSERT INTO SM_MOAH
	VALUES (2097163,
	2097156,
	2097163);
INSERT INTO SM_AH
	VALUES (2097163,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097163,
	2097156,
	1,
	'////////////////////////////////////////////////////////////////////////////////////////////////////////////
LOG::LogInfo(message:"TCD: Testing TIM in realized  instance operation.");
today = TIM::current_date( );
date1 = TIM::create_date( second: 1, minute: 1, hour: 1, day: 1, month: 1, year: 1 );
create object instance bc of BC;
create event instance e19 of BC1(type:0) to BC creator;
create event instance e20 of BC1(type:0) to BC creator;
create object instance bc7 of BC;
create event instance e21 of BC1(type:0) to bc7;
timer1 = TIM::timer_start ( microseconds: 60000000, event_inst:e19 );

bc.TestTIM4( create_date: TIM::create_date( second: TIM::get_second( date: today ), minute: TIM::get_minute( date: today ), hour: TIM::get_hour( date: today ), day: TIM::get_day( date: today ), month: TIM::get_month( date: today ), year: TIM::get_year( date: today ) ), current_clock: TIM::current_clock( ), current_date: today, get_second: TIM::get_second( date: date1 ), get_minute: TIM::get_minute( date: date1 ), get_hour: TIM::get_hour( date: date1 ), get_day: TIM::get_day( date: date1 ), get_month: TIM::get_month( date: date1 ), get_year: TIM::get_year( date: date1 ), timer_time_add: TIM::timer_add_time( microseconds: 30000000,  timer_inst_ref: timer1 ),  timer_remaining_time: TIM::timer_remaining_time( timer_inst_ref: timer1 ), timer_reset_time: TIM::timer_reset_time( microseconds: 30000000, timer_inst_ref: timer1 ),  timer_cancel: TIM::timer_cancel( timer_inst_ref: timer1 ), timer_start: TIM::timer_start( microseconds: 60000000, event_inst: e20 ) , timer_start_recurring: TIM::timer_start_recurring( microseconds: 60000000, event_inst: e21 )  );

delete object instance bc;

generate TMD4 to self;
',
	'');
INSERT INTO SM_MOAH
	VALUES (2097164,
	2097156,
	2097164);
INSERT INTO SM_AH
	VALUES (2097164,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097164,
	2097156,
	1,
	'////////////////////////////////////////////////////////////////////////////////////////////////////////////
LOG::LogInfo(message:"TCD: Testing TIM as event supplemental data.");
generate BC1(type:0) to BC creator;
create object instance bc of BC;
today = TIM::current_date( );
date1 = TIM::create_date( second: 1, minute: 1, hour: 1, day: 1, month: 1, year: 1 );
create event instance e22 of BC1(type:0) to BC creator;
create event instance e23 of BC1(type:0) to BC creator;
create object instance bc8 of BC;
create event instance e24 of BC1(type:0) to bc8;
timer1 = TIM::timer_start ( microseconds: 60000000, event_inst:e22 );

generate BC2( create_date: TIM::create_date( second: TIM::get_second( date: today ), minute: TIM::get_minute( date: today ), hour: TIM::get_hour( date: today ), day: TIM::get_day( date: today ), month: TIM::get_month( date: today ), year: TIM::get_year( date: today ) ), current_clock: TIM::current_clock( ), current_date: today, get_second: TIM::get_second( date: date1 ), get_minute: TIM::get_minute( date: date1 ), get_hour: TIM::get_hour( date: date1 ), get_day: TIM::get_day( date: date1 ), get_month: TIM::get_month( date: date1 ), get_year: TIM::get_year( date: date1 ), timer_time_add: TIM::timer_add_time( microseconds: 30000000,  timer_inst_ref: timer1 ),  timer_remaining_time: TIM::timer_remaining_time( timer_inst_ref: timer1 ), timer_reset_time: TIM::timer_reset_time( microseconds: 30000000, timer_inst_ref: timer1 ),  timer_cancel: TIM::timer_cancel( timer_inst_ref: timer1 ), timer_start: TIM::timer_start( microseconds: 60000000, event_inst: e23 ) , timer_start_recurring: TIM::timer_start_recurring( microseconds: 60000000, event_inst: e24 )  ) to bc;',
	'');
INSERT INTO SM_MOAH
	VALUES (2097165,
	2097156,
	2097165);
INSERT INTO SM_AH
	VALUES (2097165,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097165,
	2097156,
	1,
	'////////////////////////////////////////////////////////////////////////////////////////////////////////////
LOG::LogInfo(message:"TCD: Testing TIM as assigner event supplemental data.");
today = TIM::current_date( );
date1 = TIM::create_date( second: 1, minute: 1, hour: 1, day: 1, month: 1, year: 1 );

create event instance e25 of BC_A1 to BC assigner;
create event instance e26 of BC_A1 to BC assigner;
create event instance e27 of BC_A1 to BC assigner;

timer1 = TIM::timer_start ( microseconds: 60000000, event_inst:e27 );

create event instance e28 of BC_A2( create_date: TIM::create_date( second: TIM::get_second( date: today ), minute: TIM::get_minute( date: today ), hour: TIM::get_hour( date: today ), day: TIM::get_day( date: today ), month: TIM::get_month( date: today ), year: TIM::get_year( date: today ) ), current_clock: TIM::current_clock( ), current_date: today, get_second: TIM::get_second( date: date1 ), get_minute: TIM::get_minute( date: date1 ), get_hour: TIM::get_hour( date: date1 ), get_day: TIM::get_day( date: date1 ), get_month: TIM::get_month( date: date1 ), get_year: TIM::get_year( date: date1 ), timer_time_add: TIM::timer_add_time( microseconds: 30000000,  timer_inst_ref: timer1 ),  timer_remaining_time: TIM::timer_remaining_time( timer_inst_ref: timer1 ), timer_reset_time: TIM::timer_reset_time( microseconds: 30000000, timer_inst_ref: timer1 ),  timer_cancel: TIM::timer_cancel( timer_inst_ref: timer1 ), timer_start: TIM::timer_start( microseconds: 10000000, event_inst: e25 ) , timer_start_recurring: TIM::timer_start_recurring( microseconds: 50000000, event_inst: e26 )  ) to BC assigner;

timer2 = TIM::timer_start ( microseconds: 1, event_inst:e28 );

',
	'');
INSERT INTO SM_MOAH
	VALUES (2097166,
	2097156,
	2097166);
INSERT INTO SM_AH
	VALUES (2097166,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097166,
	2097156,
	1,
	'////////////////////////////////////////////////////////////////////////////////////////////////////////////
LOG::LogInfo(message:"TCD: Testing TIM as creation event supplemental data.");
today = TIM::current_date( );
date1 = TIM::create_date( second: 1, minute: 1, hour: 1, day: 1, month: 1, year: 1 );
create event instance e22 of BC1(type:0) to BC creator;
create event instance e23 of BC1(type:0) to BC creator;
create object instance bc8 of BC;
create event instance e24 of BC1(type:0) to bc8;
timer1 = TIM::timer_start ( microseconds: 60000000, event_inst:e22 );

generate BC21( create_date: TIM::create_date( second: TIM::get_second( date: today ), minute: TIM::get_minute( date: today ), hour: TIM::get_hour( date: today ), day: TIM::get_day( date: today ), month: TIM::get_month( date: today ), year: TIM::get_year( date: today ) ), current_clock: TIM::current_clock( ), current_date: today, get_second: TIM::get_second( date: date1 ), get_minute: TIM::get_minute( date: date1 ), get_hour: TIM::get_hour( date: date1 ), get_day: TIM::get_day( date: date1 ), get_month: TIM::get_month( date: date1 ), get_year: TIM::get_year( date: date1 ), timer_time_add: TIM::timer_add_time( microseconds: 30000000,  timer_inst_ref: timer1 ),  timer_remaining_time: TIM::timer_remaining_time( timer_inst_ref: timer1 ), timer_reset_time: TIM::timer_reset_time( microseconds: 30000000, timer_inst_ref: timer1 ),  timer_cancel: TIM::timer_cancel( timer_inst_ref: timer1 ), timer_start: TIM::timer_start( microseconds: 60000000, event_inst: e23 ) , timer_start_recurring: TIM::timer_start_recurring( microseconds: 60000000, event_inst: e24 )  ) to BC2 creator;',
	'');
INSERT INTO SM_MOAH
	VALUES (2097168,
	2097156,
	2097168);
INSERT INTO SM_AH
	VALUES (2097168,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097168,
	2097156,
	1,
	'// Wait 15 seconds to allow all events to be processed, then go to shutdown.
create event instance e of TMD4 to self;
t = TIM::timer_start( microseconds: 15000000, event_inst: e );',
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
	1400,
	3977,
	0.696188,
	0);
INSERT INTO GD_GE
	VALUES (2097154,
	2097153,
	2097153,
	41);
INSERT INTO GD_SHP
	VALUES (2097154,
	1424,
	1280,
	1680,
	1360);
INSERT INTO GD_GE
	VALUES (2097155,
	2097153,
	2097154,
	41);
INSERT INTO GD_SHP
	VALUES (2097155,
	1424,
	1408,
	1680,
	1488);
INSERT INTO GD_GE
	VALUES (2097156,
	2097153,
	2097155,
	41);
INSERT INTO GD_SHP
	VALUES (2097156,
	2144,
	1792,
	2400,
	1872);
INSERT INTO GD_GE
	VALUES (2097157,
	2097153,
	2097156,
	41);
INSERT INTO GD_SHP
	VALUES (2097157,
	1424,
	1536,
	1680,
	1616);
INSERT INTO GD_GE
	VALUES (2097158,
	2097153,
	2097157,
	41);
INSERT INTO GD_SHP
	VALUES (2097158,
	1424,
	1664,
	1680,
	1744);
INSERT INTO GD_GE
	VALUES (2097159,
	2097153,
	2097158,
	41);
INSERT INTO GD_SHP
	VALUES (2097159,
	1424,
	1792,
	1680,
	1872);
INSERT INTO GD_GE
	VALUES (2097160,
	2097153,
	2097159,
	41);
INSERT INTO GD_SHP
	VALUES (2097160,
	1792,
	1280,
	2048,
	1360);
INSERT INTO GD_GE
	VALUES (2097161,
	2097153,
	2097160,
	41);
INSERT INTO GD_SHP
	VALUES (2097161,
	1792,
	1408,
	2048,
	1488);
INSERT INTO GD_GE
	VALUES (2097162,
	2097153,
	2097161,
	41);
INSERT INTO GD_SHP
	VALUES (2097162,
	1792,
	1536,
	2048,
	1616);
INSERT INTO GD_GE
	VALUES (2097163,
	2097153,
	2097162,
	41);
INSERT INTO GD_SHP
	VALUES (2097163,
	1792,
	1664,
	2048,
	1744);
INSERT INTO GD_GE
	VALUES (2097164,
	2097153,
	2097163,
	41);
INSERT INTO GD_SHP
	VALUES (2097164,
	1792,
	1792,
	2048,
	1872);
INSERT INTO GD_GE
	VALUES (2097165,
	2097153,
	2097164,
	41);
INSERT INTO GD_SHP
	VALUES (2097165,
	2144,
	1280,
	2400,
	1360);
INSERT INTO GD_GE
	VALUES (2097166,
	2097153,
	2097165,
	41);
INSERT INTO GD_SHP
	VALUES (2097166,
	2144,
	1536,
	2400,
	1616);
INSERT INTO GD_GE
	VALUES (2097167,
	2097153,
	2097166,
	41);
INSERT INTO GD_SHP
	VALUES (2097167,
	2144,
	1408,
	2400,
	1488);
INSERT INTO GD_GE
	VALUES (2097209,
	2097153,
	2097168,
	41);
INSERT INTO GD_SHP
	VALUES (2097209,
	2144,
	1664,
	2400,
	1744);
INSERT INTO GD_GE
	VALUES (2097169,
	2097153,
	2097153,
	42);
INSERT INTO GD_CON
	VALUES (2097169,
	2097154,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (2097169,
	0,
	0,
	0,
	0,
	0,
	0,
	1553,
	1237,
	1701,
	1259,
	171,
	-10,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097216,
	2097169,
	1536,
	1280,
	1536,
	1232,
	0);
INSERT INTO GD_GE
	VALUES (2097171,
	2097153,
	2097154,
	42);
INSERT INTO GD_CON
	VALUES (2097171,
	2097154,
	2097155,
	0);
INSERT INTO GD_CTXT
	VALUES (2097171,
	0,
	0,
	0,
	0,
	0,
	0,
	1563,
	1365,
	1773,
	1404,
	137,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097172,
	2097171,
	1552,
	1360,
	1552,
	1408,
	0);
INSERT INTO GD_GE
	VALUES (2097173,
	2097153,
	2097155,
	42);
INSERT INTO GD_CON
	VALUES (2097173,
	2097155,
	2097157,
	0);
INSERT INTO GD_CTXT
	VALUES (2097173,
	0,
	0,
	0,
	0,
	0,
	0,
	1566,
	1501,
	1754,
	1523,
	208,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097174,
	2097173,
	1552,
	1488,
	1552,
	1536,
	0);
INSERT INTO GD_GE
	VALUES (2097175,
	2097153,
	2097156,
	42);
INSERT INTO GD_CON
	VALUES (2097175,
	2097157,
	2097158,
	0);
INSERT INTO GD_CTXT
	VALUES (2097175,
	0,
	0,
	0,
	0,
	0,
	0,
	1569,
	1628,
	1651,
	1650,
	105,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097176,
	2097175,
	1552,
	1616,
	1552,
	1664,
	0);
INSERT INTO GD_GE
	VALUES (2097177,
	2097153,
	2097157,
	42);
INSERT INTO GD_CON
	VALUES (2097177,
	2097158,
	2097159,
	0);
INSERT INTO GD_CTXT
	VALUES (2097177,
	0,
	0,
	0,
	0,
	0,
	0,
	1569,
	1758,
	1706,
	1780,
	160,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097178,
	2097177,
	1552,
	1744,
	1552,
	1792,
	0);
INSERT INTO GD_GE
	VALUES (2097179,
	2097153,
	2097158,
	42);
INSERT INTO GD_CON
	VALUES (2097179,
	2097159,
	2097160,
	0);
INSERT INTO GD_CTXT
	VALUES (2097179,
	0,
	0,
	0,
	0,
	0,
	0,
	1707,
	1846,
	1789,
	1868,
	51,
	279,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097180,
	2097179,
	1680,
	1840,
	1744,
	1840,
	0);
INSERT INTO GD_LS
	VALUES (2097181,
	2097179,
	1744,
	1840,
	1744,
	1312,
	2097180);
INSERT INTO GD_LS
	VALUES (2097182,
	2097179,
	1744,
	1312,
	1792,
	1312,
	2097181);
INSERT INTO GD_GE
	VALUES (2097183,
	2097153,
	2097159,
	42);
INSERT INTO GD_CON
	VALUES (2097183,
	2097160,
	2097161,
	0);
INSERT INTO GD_CTXT
	VALUES (2097183,
	0,
	0,
	0,
	0,
	0,
	0,
	1938,
	1370,
	2020,
	1392,
	106,
	-5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097184,
	2097183,
	1920,
	1360,
	1920,
	1408,
	0);
INSERT INTO GD_GE
	VALUES (2097185,
	2097153,
	2097160,
	42);
INSERT INTO GD_CON
	VALUES (2097185,
	2097161,
	2097162,
	0);
INSERT INTO GD_CTXT
	VALUES (2097185,
	0,
	0,
	0,
	0,
	0,
	0,
	1939,
	1500,
	2021,
	1522,
	107,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097186,
	2097185,
	1920,
	1488,
	1920,
	1536,
	0);
INSERT INTO GD_GE
	VALUES (2097187,
	2097153,
	2097161,
	42);
INSERT INTO GD_CON
	VALUES (2097187,
	2097162,
	2097163,
	0);
INSERT INTO GD_CTXT
	VALUES (2097187,
	0,
	0,
	0,
	0,
	0,
	0,
	1937,
	1629,
	2019,
	1651,
	105,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097188,
	2097187,
	1920,
	1616,
	1920,
	1664,
	0);
INSERT INTO GD_GE
	VALUES (2097189,
	2097153,
	2097162,
	42);
INSERT INTO GD_CON
	VALUES (2097189,
	2097163,
	2097164,
	0);
INSERT INTO GD_CTXT
	VALUES (2097189,
	0,
	0,
	0,
	0,
	0,
	0,
	1941,
	1755,
	2023,
	1777,
	164,
	-4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097190,
	2097189,
	1920,
	1744,
	1920,
	1792,
	0);
INSERT INTO GD_GE
	VALUES (2097191,
	2097153,
	2097163,
	42);
INSERT INTO GD_CON
	VALUES (2097191,
	2097164,
	2097165,
	0);
INSERT INTO GD_CTXT
	VALUES (2097191,
	0,
	0,
	0,
	0,
	0,
	0,
	2057,
	1830,
	2139,
	1852,
	49,
	271,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097192,
	2097191,
	2048,
	1824,
	2096,
	1824,
	0);
INSERT INTO GD_LS
	VALUES (2097193,
	2097191,
	2096,
	1824,
	2096,
	1312,
	2097192);
INSERT INTO GD_LS
	VALUES (2097194,
	2097191,
	2096,
	1312,
	2144,
	1312,
	2097193);
INSERT INTO GD_GE
	VALUES (2097197,
	2097153,
	2097165,
	42);
INSERT INTO GD_CON
	VALUES (2097197,
	2097167,
	2097166,
	0);
INSERT INTO GD_CTXT
	VALUES (2097197,
	0,
	0,
	0,
	0,
	0,
	0,
	2288,
	1501,
	2370,
	1523,
	104,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097198,
	2097197,
	2272,
	1488,
	2272,
	1536,
	0);
INSERT INTO GD_GE
	VALUES (2097205,
	2097153,
	2097168,
	42);
INSERT INTO GD_CON
	VALUES (2097205,
	2097165,
	2097167,
	0);
INSERT INTO GD_CTXT
	VALUES (2097205,
	0,
	0,
	0,
	0,
	0,
	0,
	2284,
	1372,
	2366,
	1394,
	100,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097206,
	2097205,
	2272,
	1360,
	2272,
	1408,
	0);
INSERT INTO GD_GE
	VALUES (2097210,
	2097153,
	2097169,
	42);
INSERT INTO GD_CON
	VALUES (2097210,
	2097166,
	2097209,
	0);
INSERT INTO GD_CTXT
	VALUES (2097210,
	0,
	0,
	0,
	0,
	0,
	0,
	2288,
	1628,
	2370,
	1650,
	104,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097211,
	2097210,
	2272,
	1616,
	2272,
	1664,
	0);
INSERT INTO GD_GE
	VALUES (2097212,
	2097153,
	2097170,
	42);
INSERT INTO GD_CON
	VALUES (2097212,
	2097209,
	2097156,
	0);
INSERT INTO GD_CTXT
	VALUES (2097212,
	0,
	0,
	0,
	0,
	0,
	0,
	2290,
	1756,
	2372,
	1778,
	161,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097213,
	2097212,
	2272,
	1744,
	2272,
	1792,
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
	1520,
	1936,
	1808);
INSERT INTO GD_GE
	VALUES (1048581,
	1048577,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048581,
	1664,
	1360,
	1904,
	1504);
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
	1760,
	1344,
	1952,
	1408);
INSERT INTO GD_GE
	VALUES (1048583,
	1048578,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048583,
	2080,
	1392,
	2272,
	1456);
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
	1760,
	1344,
	1952,
	1408);
INSERT INTO GD_GE
	VALUES (1048585,
	1048579,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048585,
	2080,
	1392,
	2272,
	1456);
INSERT INTO S_SS
	VALUES (2621445,
	'Timer Primitives',
	'',
	'',
	100,
	219826,
	2621445);
INSERT INTO O_OBJ
	VALUES (2621441,
	'Timer Test Object A',
	100,
	'TTOA',
	'',
	2621445);
INSERT INTO O_NBATTR
	VALUES (2621441,
	2621441);
INSERT INTO O_BATTR
	VALUES (2621441,
	2621441);
INSERT INTO O_ATTR
	VALUES (2621441,
	2621441,
	0,
	'ttoa_id',
	'',
	'',
	'ttoa_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (2621442,
	2621441);
INSERT INTO O_BATTR
	VALUES (2621442,
	2621441);
INSERT INTO O_ATTR
	VALUES (2621442,
	2621441,
	2621441,
	'ack_count',
	'',
	'',
	'ack_count',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (2621443,
	2621441);
INSERT INTO O_BATTR
	VALUES (2621443,
	2621441);
INSERT INTO O_ATTR
	VALUES (2621443,
	2621441,
	2621442,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (2621444,
	2621441);
INSERT INTO O_BATTR
	VALUES (2621444,
	2621441);
INSERT INTO O_ATTR
	VALUES (2621444,
	2621441,
	2621443,
	'b',
	'',
	'',
	'b',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (2621445,
	2621441);
INSERT INTO O_BATTR
	VALUES (2621445,
	2621441);
INSERT INTO O_ATTR
	VALUES (2621445,
	2621441,
	2621444,
	'r',
	'',
	'',
	'r',
	0,
	524292);
INSERT INTO O_NBATTR
	VALUES (2621446,
	2621441);
INSERT INTO O_BATTR
	VALUES (2621446,
	2621441);
INSERT INTO O_ATTR
	VALUES (2621446,
	2621441,
	2621445,
	's',
	'',
	'',
	's',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (2621447,
	2621441);
INSERT INTO O_BATTR
	VALUES (2621447,
	2621441);
INSERT INTO O_ATTR
	VALUES (2621447,
	2621441,
	2621446,
	'd',
	'',
	'',
	'd',
	0,
	524302);
INSERT INTO O_NBATTR
	VALUES (2621448,
	2621441);
INSERT INTO O_BATTR
	VALUES (2621448,
	2621441);
INSERT INTO O_ATTR
	VALUES (2621448,
	2621441,
	2621447,
	't',
	'',
	'',
	't',
	0,
	524303);
INSERT INTO O_NBATTR
	VALUES (2621449,
	2621441);
INSERT INTO O_BATTR
	VALUES (2621449,
	2621441);
INSERT INTO O_ATTR
	VALUES (2621449,
	2621441,
	2621448,
	'timer_ref',
	'',
	'',
	'timer_ref',
	0,
	524304);
INSERT INTO O_NBATTR
	VALUES (2621450,
	2621441);
INSERT INTO O_BATTR
	VALUES (2621450,
	2621441);
INSERT INTO O_ATTR
	VALUES (2621450,
	2621441,
	2621449,
	'recurring_timer_ref',
	'',
	'',
	'recurring_timer_ref',
	0,
	524304);
INSERT INTO O_NBATTR
	VALUES (2621451,
	2621441);
INSERT INTO O_BATTR
	VALUES (2621451,
	2621441);
INSERT INTO O_ATTR
	VALUES (2621451,
	2621441,
	2621450,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	2621441);
INSERT INTO O_OIDA
	VALUES (2621441,
	2621441,
	0);
INSERT INTO SM_ISM
	VALUES (3145734,
	2621441);
INSERT INTO SM_SM
	VALUES (3145734,
	'',
	6);
INSERT INTO SM_MOORE
	VALUES (3145734);
INSERT INTO SM_EVTDI
	VALUES (3145729,
	3145734,
	'i',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (3145730,
	3145734,
	'r',
	'',
	524292);
INSERT INTO SM_EVTDI
	VALUES (3145731,
	3145734,
	'b',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (3145732,
	3145734,
	's',
	'',
	524293);
INSERT INTO SM_EVTDI
	VALUES (3145733,
	3145734,
	'u',
	'',
	524294);
INSERT INTO SM_EVTDI
	VALUES (3145734,
	3145734,
	'd',
	'',
	524302);
INSERT INTO SM_EVTDI
	VALUES (3145735,
	3145734,
	't',
	'',
	524303);
INSERT INTO SM_SUPDT
	VALUES (3145729,
	3145734,
	0);
INSERT INTO SM_SUPDT
	VALUES (3145730,
	3145734,
	0);
INSERT INTO SM_SDI
	VALUES (3145733,
	3145730,
	3145734);
INSERT INTO SM_SDI
	VALUES (3145730,
	3145730,
	3145734);
INSERT INTO SM_SDI
	VALUES (3145729,
	3145730,
	3145734);
INSERT INTO SM_SDI
	VALUES (3145735,
	3145730,
	3145734);
INSERT INTO SM_SDI
	VALUES (3145731,
	3145730,
	3145734);
INSERT INTO SM_SDI
	VALUES (3145732,
	3145730,
	3145734);
INSERT INTO SM_SDI
	VALUES (3145734,
	3145730,
	3145734);
INSERT INTO SM_STATE
	VALUES (3145729,
	3145734,
	3145729,
	'Running TM2 timer_start Test',
	1,
	0);
INSERT INTO SM_LEVT
	VALUES (3145729,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145729,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145729,
	3145734,
	3145729,
	1,
	'Timer_Start Complete',
	0,
	'',
	'TTOA1',
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_LEVT
	VALUES (3145730,
	3145734,
	3145730);
INSERT INTO SM_SEVT
	VALUES (3145730,
	3145734,
	3145730);
INSERT INTO SM_EVT
	VALUES (3145730,
	3145734,
	3145730,
	0,
	'Check Event Data',
	1,
	'CHKEVDI',
	'CHKEVDI0',
	'');
INSERT INTO SM_CH
	VALUES (3145729,
	3145730,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145730,
	3145734,
	3145730);
INSERT INTO SM_LEVT
	VALUES (3145731,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145731,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145731,
	3145734,
	3145729,
	2,
	'Start Timer_Start_Recurring Test',
	0,
	'',
	'TTOA2',
	'');
INSERT INTO SM_CH
	VALUES (3145729,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_LEVT
	VALUES (3145732,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145732,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145732,
	3145734,
	3145729,
	3,
	'Timer_Start_Recurring Event',
	0,
	'',
	'TTOA3',
	'');
INSERT INTO SM_CH
	VALUES (3145729,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_LEVT
	VALUES (3145733,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145733,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145733,
	3145734,
	3145729,
	0,
	'Start TM2 Test',
	0,
	'',
	'TTOA0',
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_LEVT
	VALUES (3145734,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145734,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145734,
	3145734,
	3145729,
	4,
	'Timer_Start_Recurring Complete',
	0,
	'',
	'TTOA4',
	'');
INSERT INTO SM_CH
	VALUES (3145729,
	3145734,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_LEVT
	VALUES (3145735,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145735,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145735,
	3145734,
	3145729,
	5,
	'Bogus Event',
	1,
	'BOGUSEV',
	'BOGUSEV5',
	'');
INSERT INTO SM_EIGN
	VALUES (3145729,
	3145735,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145735,
	3145734,
	3145729);
INSERT INTO SM_LEVT
	VALUES (3145736,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145736,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145736,
	3145734,
	3145729,
	5,
	'Timer_Remaining_Time Complete',
	0,
	'',
	'TTOA5',
	'');
INSERT INTO SM_CH
	VALUES (3145729,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_LEVT
	VALUES (3145737,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145737,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145737,
	3145734,
	3145729,
	6,
	'Timer_Reset_Time Complete',
	0,
	'',
	'TTOA6',
	'');
INSERT INTO SM_CH
	VALUES (3145729,
	3145737,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145737,
	3145734,
	3145729);
INSERT INTO SM_LEVT
	VALUES (3145738,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145738,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145738,
	3145734,
	3145729,
	7,
	'Timer_Add_Time Complete',
	0,
	'',
	'TTOA7',
	'');
INSERT INTO SM_CH
	VALUES (3145729,
	3145738,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145738,
	3145734,
	3145729);
INSERT INTO SM_LEVT
	VALUES (3145739,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145739,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145739,
	3145734,
	3145729,
	8,
	'TM2 Test Complete',
	0,
	'',
	'TTOA8',
	'');
INSERT INTO SM_CH
	VALUES (3145729,
	3145739,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145739,
	3145734,
	3145729);
INSERT INTO SM_LEVT
	VALUES (3145740,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145740,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145740,
	3145734,
	3145729,
	9,
	'Waiting',
	0,
	'',
	'TTOA9',
	'');
INSERT INTO SM_EIGN
	VALUES (3145729,
	3145740,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145740,
	3145734,
	3145729);
INSERT INTO SM_LEVT
	VALUES (3145741,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145741,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145741,
	3145734,
	3145729,
	10,
	'Continue',
	0,
	'',
	'TTOA10',
	'');
INSERT INTO SM_EIGN
	VALUES (3145729,
	3145741,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145741,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145730,
	3145734,
	3145729,
	'Checking TM2 timer_start Test',
	2,
	0);
INSERT INTO SM_CH
	VALUES (3145730,
	3145729,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145730,
	3145730,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145730,
	3145734,
	3145730);
INSERT INTO SM_SEME
	VALUES (3145730,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145730,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145730,
	3145733,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145730,
	3145734,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145730,
	3145735,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145735,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145730,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145730,
	3145737,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145737,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145730,
	3145738,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145738,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145730,
	3145739,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145739,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145730,
	3145740,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145740,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145730,
	3145741,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145741,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145731,
	3145734,
	3145729,
	'Running TM2 timer_start_recurring Test',
	3,
	0);
INSERT INTO SM_CH
	VALUES (3145731,
	3145729,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145731,
	3145730,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145730,
	3145734,
	3145730);
INSERT INTO SM_CH
	VALUES (3145731,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145731,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145731,
	3145733,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145731,
	3145734,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145731,
	3145735,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145735,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145731,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145731,
	3145737,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145737,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145731,
	3145738,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145738,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145731,
	3145739,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145739,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145731,
	3145740,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145740,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145731,
	3145741,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145741,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145732,
	3145734,
	3145729,
	'Checking TM2 timer_start_recurring Test',
	4,
	0);
INSERT INTO SM_CH
	VALUES (3145732,
	3145729,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145732,
	3145730,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145730,
	3145734,
	3145730);
INSERT INTO SM_CH
	VALUES (3145732,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145732,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145732,
	3145733,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145732,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145732,
	3145735,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145735,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145732,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145732,
	3145737,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145737,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145732,
	3145738,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145738,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145732,
	3145739,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145739,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145732,
	3145740,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145740,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145732,
	3145741,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145741,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145733,
	3145734,
	3145729,
	'Running TM2 timer_remaining_time Test',
	5,
	0);
INSERT INTO SM_CH
	VALUES (3145733,
	3145729,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145733,
	3145730,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145730,
	3145734,
	3145730);
INSERT INTO SM_CH
	VALUES (3145733,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145733,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145733,
	3145733,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145733,
	3145734,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145733,
	3145735,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145735,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145733,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145733,
	3145737,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145737,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145733,
	3145738,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145738,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145733,
	3145739,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145739,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145733,
	3145740,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145740,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145733,
	3145741,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145734,
	3145734,
	3145729,
	'Running TM2 timer_reset_time Test',
	6,
	0);
INSERT INTO SM_CH
	VALUES (3145734,
	3145729,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145734,
	3145730,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145730,
	3145734,
	3145730);
INSERT INTO SM_CH
	VALUES (3145734,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145734,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145734,
	3145733,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145734,
	3145734,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145734,
	3145735,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145735,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145734,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145734,
	3145737,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145737,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145734,
	3145738,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145738,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145734,
	3145739,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145739,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145734,
	3145740,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145734,
	3145741,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145741,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145735,
	3145734,
	3145729,
	'Running TM2 timer_add_time Test',
	7,
	0);
INSERT INTO SM_CH
	VALUES (3145735,
	3145729,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145735,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145735,
	3145730,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145735,
	3145730,
	3145734,
	3145730);
INSERT INTO SM_CH
	VALUES (3145735,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145735,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145735,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145735,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145735,
	3145733,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145735,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145735,
	3145734,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145735,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145735,
	3145735,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145735,
	3145735,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145735,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145735,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145735,
	3145737,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145735,
	3145737,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145735,
	3145738,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145735,
	3145738,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145735,
	3145739,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145735,
	3145739,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145735,
	3145740,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145735,
	3145740,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145735,
	3145741,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145736,
	3145734,
	3145729,
	'Running TM2 timer_cancel Test',
	8,
	0);
INSERT INTO SM_CH
	VALUES (3145736,
	3145729,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145736,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145736,
	3145730,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145736,
	3145730,
	3145734,
	3145730);
INSERT INTO SM_CH
	VALUES (3145736,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145736,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145736,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145736,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145736,
	3145733,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145736,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145736,
	3145734,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145736,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145736,
	3145735,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145736,
	3145735,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145736,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145736,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145736,
	3145737,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145736,
	3145737,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145736,
	3145738,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145736,
	3145738,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145736,
	3145739,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145736,
	3145739,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145736,
	3145740,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145736,
	3145741,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145736,
	3145741,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145737,
	3145734,
	3145730,
	'Checking Event Data Items',
	9,
	0);
INSERT INTO SM_CH
	VALUES (3145737,
	3145729,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145737,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145737,
	3145730,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145737,
	3145730,
	3145734,
	3145730);
INSERT INTO SM_CH
	VALUES (3145737,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145737,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145737,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145737,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145737,
	3145733,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145737,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145737,
	3145734,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145737,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145737,
	3145735,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145737,
	3145735,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145737,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145737,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145737,
	3145737,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145737,
	3145737,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145737,
	3145738,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145737,
	3145738,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145737,
	3145739,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145737,
	3145740,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145737,
	3145740,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145737,
	3145741,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145737,
	3145741,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145738,
	3145734,
	3145729,
	'TM2 Test Suite Complete',
	10,
	1);
INSERT INTO SM_CH
	VALUES (3145738,
	3145729,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145738,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145738,
	3145730,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145738,
	3145730,
	3145734,
	3145730);
INSERT INTO SM_CH
	VALUES (3145738,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145738,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145738,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145738,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145738,
	3145733,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145738,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145738,
	3145734,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145738,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145738,
	3145735,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145738,
	3145735,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145738,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145738,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145738,
	3145737,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145738,
	3145737,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145738,
	3145738,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145738,
	3145738,
	3145734,
	3145729);
INSERT INTO SM_CH
	VALUES (3145738,
	3145739,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145738,
	3145739,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145738,
	3145740,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145738,
	3145740,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145738,
	3145741,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145738,
	3145741,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145739,
	3145734,
	3145729,
	'Waiting',
	11,
	0);
INSERT INTO SM_EIGN
	VALUES (3145739,
	3145729,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145739,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145739,
	3145730,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145739,
	3145730,
	3145734,
	3145730);
INSERT INTO SM_EIGN
	VALUES (3145739,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145739,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145739,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145739,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145739,
	3145733,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145739,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145739,
	3145734,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145739,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145739,
	3145735,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145739,
	3145735,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145739,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145739,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145739,
	3145737,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145739,
	3145737,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145739,
	3145738,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145739,
	3145738,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145739,
	3145739,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145739,
	3145739,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145739,
	3145740,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145739,
	3145740,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145739,
	3145741,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145740,
	3145734,
	3145729,
	'Running TM2 timer_cancel Test Continued',
	12,
	0);
INSERT INTO SM_EIGN
	VALUES (3145740,
	3145729,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145740,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145740,
	3145730,
	3145734,
	3145730);
INSERT INTO SM_EIGN
	VALUES (3145740,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145740,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145740,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145740,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145740,
	3145733,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145740,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145740,
	3145734,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145740,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145740,
	3145735,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145740,
	3145735,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145740,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145740,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145740,
	3145737,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145740,
	3145737,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145740,
	3145738,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145740,
	3145738,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145740,
	3145739,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145740,
	3145739,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145740,
	3145740,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145740,
	3145740,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145740,
	3145741,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145740,
	3145741,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145741,
	3145734,
	3145729,
	'Waiting for Timer',
	13,
	0);
INSERT INTO SM_EIGN
	VALUES (3145741,
	3145729,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145741,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145741,
	3145730,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145741,
	3145730,
	3145734,
	3145730);
INSERT INTO SM_EIGN
	VALUES (3145741,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145741,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145741,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145741,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145741,
	3145733,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145741,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145741,
	3145734,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145741,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145741,
	3145735,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145741,
	3145735,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145741,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145741,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145741,
	3145737,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145741,
	3145737,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145741,
	3145738,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145741,
	3145738,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145741,
	3145739,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145741,
	3145739,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145741,
	3145740,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145741,
	3145740,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145741,
	3145741,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145742,
	3145734,
	3145729,
	'TM2 timer_reset_time continued',
	14,
	0);
INSERT INTO SM_EIGN
	VALUES (3145742,
	3145729,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145742,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145742,
	3145730,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145742,
	3145730,
	3145734,
	3145730);
INSERT INTO SM_EIGN
	VALUES (3145742,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145742,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145742,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145742,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145742,
	3145733,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145742,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145742,
	3145734,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145742,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145742,
	3145735,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145742,
	3145735,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145742,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145742,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145742,
	3145737,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145742,
	3145738,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145742,
	3145738,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145742,
	3145739,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145742,
	3145739,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145742,
	3145740,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145742,
	3145740,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145742,
	3145741,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145742,
	3145741,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145743,
	3145734,
	3145729,
	'Waiting on Timer',
	15,
	0);
INSERT INTO SM_EIGN
	VALUES (3145743,
	3145729,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145743,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145743,
	3145730,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145743,
	3145730,
	3145734,
	3145730);
INSERT INTO SM_EIGN
	VALUES (3145743,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145743,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145743,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145743,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145743,
	3145733,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145743,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145743,
	3145734,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145743,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145743,
	3145735,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145743,
	3145735,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145743,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145743,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145743,
	3145737,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145743,
	3145737,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145743,
	3145738,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145743,
	3145739,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145743,
	3145739,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145743,
	3145740,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145743,
	3145740,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145743,
	3145741,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145743,
	3145741,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145744,
	3145734,
	3145729,
	'Wait until no time remaining',
	16,
	0);
INSERT INTO SM_EIGN
	VALUES (3145744,
	3145729,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145744,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145744,
	3145730,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145744,
	3145730,
	3145734,
	3145730);
INSERT INTO SM_EIGN
	VALUES (3145744,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145744,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145744,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145744,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145744,
	3145733,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145744,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145744,
	3145734,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145744,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145744,
	3145735,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145744,
	3145735,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145744,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145744,
	3145737,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145744,
	3145737,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145744,
	3145738,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145744,
	3145738,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145744,
	3145739,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145744,
	3145739,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145744,
	3145740,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145744,
	3145740,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145744,
	3145741,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145744,
	3145741,
	3145734,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145729,
	3145734,
	3145729,
	3145729,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145729,
	3145734,
	3145730,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145730,
	3145734,
	3145730,
	3145731,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145730,
	3145734,
	3145731,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145731,
	3145734,
	3145729,
	3145733,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145731,
	3145734,
	3145729,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145732,
	3145734,
	3145731,
	3145732,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145732,
	3145734,
	3145732,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145733,
	3145734,
	3145732,
	3145732,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145733,
	3145734,
	3145732,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145734,
	3145734,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145734,
	3145734,
	3145733,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145735,
	3145734,
	3145737,
	3145739,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145735,
	3145734,
	3145738,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145736,
	3145734,
	3145736,
	3145740,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145736,
	3145734,
	3145739,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145737,
	3145734,
	3145740,
	3145730,
	3145730);
INSERT INTO SM_TXN
	VALUES (3145737,
	3145734,
	3145737,
	3145730);
INSERT INTO SM_NSTXN
	VALUES (3145738,
	3145734,
	3145739,
	3145741,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145738,
	3145734,
	3145740,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145739,
	3145734,
	3145741,
	3145741,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145739,
	3145734,
	3145742,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145740,
	3145734,
	3145734,
	3145740,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145740,
	3145734,
	3145741,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145741,
	3145734,
	3145742,
	3145737,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145741,
	3145734,
	3145735,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145742,
	3145734,
	3145743,
	3145738,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145742,
	3145734,
	3145736,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145743,
	3145734,
	3145735,
	3145741,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145743,
	3145734,
	3145743,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145744,
	3145734,
	3145733,
	3145741,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145744,
	3145734,
	3145744,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145745,
	3145734,
	3145744,
	3145736,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145745,
	3145734,
	3145734,
	3145729);
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
	'::LogDateAndTime(message:"TM2: Running timer_start Test");

//test timer_start
LOG::LogInfo(message:"starting timer using timer_start for 5 seconds.") ;
create event instance event_inst of TTOA1:''Timer_Start Complete''() to self;
bridge timer_inst_ref = TIM::timer_start(microseconds:5000000,event_inst:event_inst);
',
	'');
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
	'// If we are here then TTOA1 fired
::LogDateAndTime(message:"TM2: checking timer_start test") ;
LOG::LogSuccess(message:"TTOA1:timer_start_complete fired");
generate TTOA2:''Start Timer_Start_Recurring Test''() to self;',
	'');
INSERT INTO SM_MOAH
	VALUES (3145731,
	3145734,
	3145731);
INSERT INTO SM_AH
	VALUES (3145731,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145731,
	3145734,
	1,
	'::LogDateAndTime(message:"TM2: Running timer_start_recurring Test");

//test timer_start_recurring
assign self.ack_count = 0;
LOG::LogInfo(message:"starting timer using timer_start_recurring for 5 times once every 5 seconds.");
create event instance event_inst of TTOA3:''Timer_Start_Recurring Event''() to self;
self.recurring_timer_ref = TIM::timer_start_recurring(microseconds:5000000,event_inst:event_inst);
',
	'');
INSERT INTO SM_MOAH
	VALUES (3145732,
	3145734,
	3145732);
INSERT INTO SM_AH
	VALUES (3145732,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145732,
	3145734,
	1,
	'::LogDateAndTime(message:"TM2: Checking timer_start_recurring Test") ;

//test timer_start_recurring
assign self.ack_count = self.ack_count + 1;
LOG::LogSuccess(message:"Another iteration of timer_start_recurring test");
LOG::LogInt(int:self.ack_count, message:"Iteration number");
if (self.ack_count == 5)
  was_running = TIM::timer_cancel(timer_inst_ref:self.recurring_timer_ref);
  generate TTOA4:''Timer_Start_Recurring Complete''() to self;
end if;
',
	'');
INSERT INTO SM_MOAH
	VALUES (3145733,
	3145734,
	3145733);
INSERT INTO SM_AH
	VALUES (3145733,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145733,
	3145734,
	1,
	'::LogDateAndTime(message:"TM2: Running timer_remaining_time Test") ;

//test timer_remaining_time for a timer started with timer_start primitive
LOG::LogInfo(message:"TM2: Running timer_remaining_time - starting timer using timer_start for 100 seconds.");
create event instance event_inst of BOGUSEV5:''Bogus Event''() to self;
timer_inst_ref = TIM::timer_start(microseconds:100000000,event_inst:event_inst);
time_remaining = TIM::timer_remaining_time(timer_inst_ref:timer_inst_ref);
if(time_remaining > 0)
  LOG::LogSuccess(message:"TM2: Running timer_remaining_time - time remaining on timer_start") ;
else
  LOG::LogFailure(message:"TM2: Running timer_remaining_time - no time remaining on timer_start");
end if;
was_running = TIM::timer_cancel(timer_inst_ref:timer_inst_ref);

//test timer_remaining for a timer started with timer_start_recurring primitive
LOG::LogInfo(message:"TM2: Running timer_remaining_time - starting timer using timer_start_recurring for 100 seconds.") ;
create event instance event_inst of BOGUSEV5:''Bogus Event''() to self;
timer_inst_ref = TIM::timer_start_recurring(microseconds:100000000,event_inst:event_inst);
time_remaining = TIM::timer_remaining_time(timer_inst_ref:timer_inst_ref);
if(time_remaining > 0)
  LOG::LogSuccess(message:"TM2: Running timer_remaining_time - time remaining on timer_start_recurring") ;
else
  LOG::LogFailure(message:"TM2: Running timer_remaining_time - no time remaining on timer_start_recurring");
end if;
was_running = TIM::timer_cancel(timer_inst_ref:timer_inst_ref);

//test timer_remaining_time for a timer started with timer_start, after it has expired
create event instance event_inst2 of TTOA10 to self;
//set timer for 1 microsecond
LOG::LogInfo(message:"TM2: Running timer_remaining_time - starting timer using timer_start for 1 microsecond.") ;
self.timer_ref = TIM::timer_start(microseconds:1,event_inst:event_inst2);',
	'');
INSERT INTO SM_MOAH
	VALUES (3145734,
	3145734,
	3145734);
INSERT INTO SM_AH
	VALUES (3145734,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145734,
	3145734,
	1,
	'::LogDateAndTime(message:"TM2: Running timer_reset_time Test");

//test timer_reset_time for a timer started with timer_start primitive
create event instance event_inst of BOGUSEV5:''Bogus Event''() to self;
//set timer for 100 seconds
LOG::LogInfo(message:"TM2: Running timer_reset_time - starting timer using timer_start for 100 seconds.") ;
bridge timer_inst_ref = TIM::timer_start(microseconds:100000000,event_inst:event_inst);
//reset timer to 200 seconds
bridge was_running = TIM::timer_reset_time(timer_inst_ref:timer_inst_ref,microseconds:200000000);
bridge time_remaining = TIM::timer_remaining_time(timer_inst_ref:timer_inst_ref);
if( (was_running == TRUE) AND (time_remaining > 100000000) )
  LOG::LogSuccess(message:"TM2: Running timer_reset_time - reset timer using timer_reset for 200 seconds.") ;
else
  LOG::LogFailure(message:"TM2: Running timer_reset_time - did not reset timer using timer_reset for 200 seconds.") ;
end if;
LOG::LogInt(int:time_remaining,message:"Time remaining") ;
bridge was_running = TIM::timer_cancel(timer_inst_ref:timer_inst_ref);

//test timer_reset_time for a timer started with timer_start_recurring primitive
create event instance event_inst of BOGUSEV5:''Bogus Event''() to self;
//set timer for 100 seconds
LOG::LogInfo(message:"TM2: Running timer_reset_time - starting timer using timer_start_recurring for 100 seconds.") ;
bridge timer_inst_ref = TIM::timer_start_recurring(microseconds:100000000,event_inst:event_inst);
//reset timer to 50 seconds
bridge was_running = TIM::timer_reset_time(timer_inst_ref:timer_inst_ref,microseconds:50000000);
bridge time_remaining = TIM::timer_remaining_time(timer_inst_ref:timer_inst_ref);
if( (was_running == TRUE) AND (time_remaining <= 50000000) )
  LOG::LogSuccess(message:"TM2: Running timer_reset_time - reset timer using timer_reset for 50 seconds.") ;
else
  LOG::LogFailure(message:"TM2: Running timer_reset_time - did not reset timer using timer_reset for 50 seconds.") ;
end if;
LOG::LogInt(int:time_remaining,message:"Time remaining") ;
bridge was_running = TIM::timer_cancel(timer_inst_ref:timer_inst_ref);

//test timer_reset_time for a timer started with timer_start that has already expired
create event instance event_inst2 of TTOA9 to self;
//set timer for 1 microsecond
LOG::LogInfo(message:"TM2: Running timer_reset_time - starting timer using timer_start for 1 microsecond.") ;
bridge self.timer_ref = TIM::timer_start(microseconds:1,event_inst:event_inst2);

// In order for the event to actually fire, we must leave this state action
',
	'');
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
	'::LogDateAndTime(message:"TM2: Running timer_add_time Test");

//test timer_add_time for a timer started with timer_start primitive
create event instance event_inst of BOGUSEV5:''Bogus Event''() to self;
//set timer for 100 seconds
LOG::LogInfo(message:"TM2: Running timer_add_time - starting timer using timer_start for 100 seconds.") ;
bridge timer_inst_ref = TIM::timer_start(microseconds:100000000,event_inst:event_inst);
//add 200 seconds to timer
bridge was_running = TIM::timer_add_time(timer_inst_ref:timer_inst_ref,microseconds:200000000);
bridge time_remaining = TIM::timer_remaining_time(timer_inst_ref:timer_inst_ref);
if(was_running == TRUE)
  LOG::LogSuccess(message:"TM2: Running timer_add_time - 200 seconds were added to timer_start");
else
  LOG::LogFailure(message:"TM2: Running timer_add_time - 200 seconds were not added to timer_start");
end if;
LOG::LogInt(int:time_remaining,message:"Time remaining") ;
bridge was_running = TIM::timer_cancel(timer_inst_ref:timer_inst_ref);

//test timer_remaining for a timer started with timer_start_recurring primitive
create event instance event_inst of BOGUSEV5:''Bogus Event''() to self;
//set timer for 300 seconds
LOG::LogInfo(message:"TM2: Running timer_add_time - starting timer using timer_start_recurring for 300 seconds.") ;
bridge timer_inst_ref = TIM::timer_start_recurring(microseconds:300000000,event_inst:event_inst);
//add -100 seconds to timer
bridge was_running = TIM::timer_add_time(timer_inst_ref:timer_inst_ref,microseconds:-100000000);
bridge time_remaining = TIM::timer_remaining_time(timer_inst_ref:timer_inst_ref);
if(was_running == TRUE)
  LOG::LogSuccess(message:"TM2: Running timer_add_time - 100 seconds were subtracted from timer_start_recurring");
else
  LOG::LogFailure(message:"TM2: Running timer_add_time - 100 seconds were not subtracted from timer_start_recurring");
end if;
LOG::LogInt(int:time_remaining,message:"Time remaining") ;
bridge was_running = TIM::timer_cancel(timer_inst_ref:timer_inst_ref);

//test timer_add_time for a timer started with timer_start, after it has expired
create event instance event_inst2 of TTOA10 to self;
//set timer for 1 microsecond
LOG::LogInfo(message:"TM2: Running timer_add_time - starting timer using timer_start for 1 microsecond.") ;
bridge self.timer_ref = TIM::timer_start(microseconds:1,event_inst:event_inst2);',
	'');
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
	'::LogDateAndTime(message:"TM2: Running timer_cancel Test");

//test timer_cancel for a timer started with timer_start primitive
create event instance event_inst of BOGUSEV5:''Bogus Event''() to self;
//set timer for 100 seconds
LOG::LogInfo(message:"TM2: Running timer_cancel - starting timer using timer_start for 100 seconds.") ;
bridge timer_inst_ref = TIM::timer_start(microseconds:100000000,event_inst:event_inst);
bridge was_running = TIM::timer_cancel(timer_inst_ref:timer_inst_ref);
if (was_running == TRUE)
  LOG::LogSuccess(message:"TM2: Running timer_cancel - timer_start timer cancelled was running") ;
else 
  LOG::LogFailure(message:"TM2: Running timer_cancel - timer_start timer cancelled was not running") ;
end if;

//test timer_cancel for a timer started with timer_start_recurring primitive
create event instance event_inst of BOGUSEV5:''Bogus Event''() to self;
//set timer for 100 seconds
LOG::LogInfo(message:"TM2: Running timer_cancel - starting timer using timer_start_recurring for 100 seconds.") ;
bridge timer_inst_ref = TIM::timer_start(microseconds:100000000,event_inst:event_inst);
bridge was_running = TIM::timer_cancel(timer_inst_ref:timer_inst_ref);
if (was_running == TRUE)
  LOG::LogSuccess(message:"TM2: Running timer_cancel - timer_start_recurring timer cancelled was running") ;
else 
  LOG::LogFailure(message:"TM2: Running timer_cancel - timer_start_recurring timer cancelled was not running") ;
end if;

//test timer_cancel for a timer started with timer_start, after it has expired
create event instance event_inst2 of TTOA9 to self;
//set timer for 1 microsecond
LOG::LogInfo(message:"TM2: Running timer_cancel - starting timer using timer_start for 1 microsecond.") ;
self.timer_ref = TIM::timer_start(microseconds:1,event_inst:event_inst2);

// In order for the event to happen we must leave this state action.',
	'');
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
	'::LogDateAndTime(message:"TM2: Running Checking Event Data Items Test");

//check event data items 
if (rcvd_evt.i == self.i)
  LOG::LogSuccess(message:"TM2: Running Checking Event Data Items - i") ;
else
  LOG::LogFailure(message:"TM2: Running Checking Event Data Items - i") ;
end if;

abs_val = rcvd_evt.r - self.r;
if(abs_val < 0)
  abs_val = -abs_val;
end if;
if (abs_val < .001)
  LOG::LogSuccess(message:"TM2: Running Checking Event Data Items - r") ;
else
  LOG::LogFailure(message:"TM2: Running Checking Event Data Items - r") ;
end if;

if (rcvd_evt.b == self.b)
  LOG::LogSuccess(message:"TM2: Running Checking Event Data Items - b") ;
else
  LOG::LogFailure(message:"TM2: Running Checking Event Data Items - b") ;
end if;

if (rcvd_evt.s == self.s)
  LOG::LogSuccess(message:"TM2: Running Checking Event Data Items - s") ;
else
  LOG::LogFailure(message:"TM2: Running Checking Event Data Items - s") ;
end if;

if (rcvd_evt.u == self.ttoa_id)
  LOG::LogSuccess(message:"TM2: Running Checking Event Data Items - u") ;
else
  LOG::LogFailure(message:"TM2: Running Checking Event Data Items - u") ;
end if;

if (rcvd_evt.d == self.d)
  LOG::LogSuccess(message:"TM2: Running Checking Event Data Items - d") ;
else
  LOG::LogFailure(message:"TM2: Running Checking Event Data Items - d") ;
end if;

if (rcvd_evt.t == self.t)
  LOG::LogSuccess(message:"TM2: Running Checking Event Data Items - t") ;
else
  LOG::LogFailure(message:"TM2: Running Checking Event Data Items - t") ;
end if;

::LogDateAndTime(message:"TM2: Running Checking Event Data Items Complete") ;
generate TTOA8:''TM2 Test Complete''() to self;
',
	'');
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
	'LOG::LogInfo(message:"TM2 Test Complete");

select any driver from instances of TMD;
generate TMD3:''TM2 Test Complete''() to driver;',
	'');
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
	'generate TTOA10 to self;',
	'');
INSERT INTO SM_MOAH
	VALUES (3145740,
	3145734,
	3145740);
INSERT INTO SM_AH
	VALUES (3145740,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145740,
	3145734,
	1,
	'bridge was_running = TIM::timer_cancel(timer_inst_ref:self.timer_ref);
if (was_running == FALSE)
  LOG::LogSuccess(message:"TM2: Running timer_cancel - timer_start timer cancelled was not running") ;
else 
  LOG::LogFailure(message:"TM2: Running timer_cancel - timer_start timer cancelled was running") ;
end if;

::LogDateAndTime(message:"TM2: Running timer_cancel Complete") ;

assign self.i = 10;
assign self.r=2.3;
assign self.b= TRUE;
assign self.d = TIM::current_date();
assign self.t = TIM::current_clock();
assign self.s = "Hello";
create event instance event_inst2 of CHKEVDI0:''Check Event Data''(b:self.b,d:self.d,t:self.t,i:self.i,r:self.r,u:self.ttoa_id,s:self.s) to self;

bridge self.timer_ref = TIM::timer_start(microseconds:1000000,event_inst:event_inst2);
',
	'');
INSERT INTO SM_MOAH
	VALUES (3145741,
	3145734,
	3145741);
INSERT INTO SM_AH
	VALUES (3145741,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145741,
	3145734,
	1,
	'generate TTOA10 to self;',
	'');
INSERT INTO SM_MOAH
	VALUES (3145742,
	3145734,
	3145742);
INSERT INTO SM_AH
	VALUES (3145742,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145742,
	3145734,
	1,
	'//reset the timer
bridge was_running = TIM::timer_reset_time(timer_inst_ref:self.timer_ref,microseconds:100000000);
if (was_running == FALSE)
  LOG::LogSuccess(message:"TM2: Running timer_reset_time - timer_start timer was not running") ;
else 
  LOG::LogFailure(message:"TM2: Running timer_reset_time - timer_start timer reset was running") ;
end if;

::LogDateAndTime(message:"TM2: Running timer_reset_time Test Complete") ;
generate TTOA6:''Timer_Reset_Time Complete''() to self;',
	'');
INSERT INTO SM_MOAH
	VALUES (3145743,
	3145734,
	3145743);
INSERT INTO SM_AH
	VALUES (3145743,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145743,
	3145734,
	1,
	'bridge was_running = TIM::timer_add_time(timer_inst_ref:self.timer_ref,microseconds:10000000);
if (was_running == FALSE)
  LOG::LogSuccess(message:"TM2: Running timer_add_time - timer_start timer_add_time was not running") ;
else 
  LOG::LogFailure(message:"TM2: Running timer_add_time - timer_start timer_add_time was running") ;
end if;

::LogDateAndTime(message:"TM2: Running timer_add_time Test Complete");
generate TTOA7:''Timer_Add_Time Complete''() to self;',
	'');
INSERT INTO SM_MOAH
	VALUES (3145744,
	3145734,
	3145744);
INSERT INTO SM_AH
	VALUES (3145744,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145744,
	3145734,
	1,
	'//read remaining time on timer
bridge time_remaining = TIM::timer_remaining_time(timer_inst_ref:self.timer_ref);
if (time_remaining == 0)
  LOG::LogSuccess(message:"TM2: Running timer_remaining_time - time remaining is 0") ;
else 
  LOG::LogFailure(message:"TM2: Running timer_remaining_time - time remaining is not 0") ;
end if;

::LogDateAndTime(message:"TM2: Running timer_remaining_time Test Complete");
generate TTOA5:''Timer_Remaining_Time Complete''() to self;',
	'');
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
	1493,
	3754,
	0.500223,
	0);
INSERT INTO GD_GE
	VALUES (3145730,
	3145729,
	3145729,
	41);
INSERT INTO GD_SHP
	VALUES (3145730,
	1696,
	1200,
	2048,
	1296);
INSERT INTO GD_GE
	VALUES (3145731,
	3145729,
	3145730,
	41);
INSERT INTO GD_SHP
	VALUES (3145731,
	1696,
	1392,
	2048,
	1488);
INSERT INTO GD_GE
	VALUES (3145732,
	3145729,
	3145731,
	41);
INSERT INTO GD_SHP
	VALUES (3145732,
	1696,
	1584,
	2048,
	1680);
INSERT INTO GD_GE
	VALUES (3145733,
	3145729,
	3145732,
	41);
INSERT INTO GD_SHP
	VALUES (3145733,
	1696,
	1760,
	2048,
	1856);
INSERT INTO GD_GE
	VALUES (3145734,
	3145729,
	3145733,
	41);
INSERT INTO GD_SHP
	VALUES (3145734,
	1696,
	1936,
	2048,
	2032);
INSERT INTO GD_GE
	VALUES (3145735,
	3145729,
	3145734,
	41);
INSERT INTO GD_SHP
	VALUES (3145735,
	2240,
	1936,
	2528,
	2032);
INSERT INTO GD_GE
	VALUES (3145736,
	3145729,
	3145735,
	41);
INSERT INTO GD_SHP
	VALUES (3145736,
	2240,
	1760,
	2528,
	1856);
INSERT INTO GD_GE
	VALUES (3145737,
	3145729,
	3145736,
	41);
INSERT INTO GD_SHP
	VALUES (3145737,
	2240,
	1392,
	2528,
	1488);
INSERT INTO GD_GE
	VALUES (3145738,
	3145729,
	3145737,
	41);
INSERT INTO GD_SHP
	VALUES (3145738,
	2240,
	1200,
	2528,
	1296);
INSERT INTO GD_GE
	VALUES (3145739,
	3145729,
	3145738,
	41);
INSERT INTO GD_SHP
	VALUES (3145739,
	2240,
	1008,
	2528,
	1104);
INSERT INTO GD_GE
	VALUES (3145740,
	3145729,
	3145739,
	41);
INSERT INTO GD_SHP
	VALUES (3145740,
	2656,
	1392,
	2864,
	1488);
INSERT INTO GD_GE
	VALUES (3145741,
	3145729,
	3145740,
	41);
INSERT INTO GD_SHP
	VALUES (3145741,
	2656,
	1200,
	2864,
	1296);
INSERT INTO GD_GE
	VALUES (3145742,
	3145729,
	3145741,
	41);
INSERT INTO GD_SHP
	VALUES (3145742,
	2656,
	1936,
	2896,
	2032);
INSERT INTO GD_GE
	VALUES (3145743,
	3145729,
	3145742,
	41);
INSERT INTO GD_SHP
	VALUES (3145743,
	2656,
	1760,
	2896,
	1856);
INSERT INTO GD_GE
	VALUES (3145744,
	3145729,
	3145743,
	41);
INSERT INTO GD_SHP
	VALUES (3145744,
	2240,
	1584,
	2528,
	1680);
INSERT INTO GD_GE
	VALUES (3145745,
	3145729,
	3145744,
	41);
INSERT INTO GD_SHP
	VALUES (3145745,
	1984,
	2112,
	2272,
	2224);
INSERT INTO GD_GE
	VALUES (3145746,
	3145729,
	3145729,
	42);
INSERT INTO GD_CON
	VALUES (3145746,
	3145730,
	3145731,
	0);
INSERT INTO GD_CTXT
	VALUES (3145746,
	0,
	0,
	0,
	0,
	0,
	0,
	1709,
	1314,
	1921,
	1336,
	71,
	-21,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145747,
	3145746,
	1856,
	1296,
	1856,
	1392,
	0);
INSERT INTO GD_GE
	VALUES (3145748,
	3145729,
	3145730,
	42);
INSERT INTO GD_CON
	VALUES (3145748,
	3145731,
	3145732,
	0);
INSERT INTO GD_CTXT
	VALUES (3145748,
	0,
	0,
	0,
	0,
	0,
	0,
	1860,
	1514,
	2112,
	1560,
	20,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145749,
	3145748,
	1856,
	1488,
	1856,
	1584,
	0);
INSERT INTO GD_GE
	VALUES (3145750,
	3145729,
	3145731,
	42);
INSERT INTO GD_CON
	VALUES (3145750,
	3145730,
	3145730,
	0);
INSERT INTO GD_CTXT
	VALUES (3145750,
	0,
	0,
	0,
	0,
	0,
	0,
	1792,
	1120,
	1952,
	1142,
	46,
	-4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145751,
	3145750,
	1712,
	1200,
	1712,
	1152,
	0);
INSERT INTO GD_LS
	VALUES (3145752,
	3145750,
	1712,
	1152,
	1936,
	1152,
	3145751);
INSERT INTO GD_LS
	VALUES (3145753,
	3145750,
	1936,
	1152,
	1936,
	1200,
	3145752);
INSERT INTO GD_GE
	VALUES (3145754,
	3145729,
	3145732,
	42);
INSERT INTO GD_CON
	VALUES (3145754,
	3145732,
	3145733,
	0);
INSERT INTO GD_CTXT
	VALUES (3145754,
	0,
	0,
	0,
	0,
	0,
	0,
	1840,
	1712,
	2082,
	1754,
	0,
	7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145755,
	3145754,
	1856,
	1680,
	1856,
	1760,
	0);
INSERT INTO GD_GE
	VALUES (3145756,
	3145729,
	3145733,
	42);
INSERT INTO GD_CON
	VALUES (3145756,
	3145733,
	3145733,
	0);
INSERT INTO GD_CTXT
	VALUES (3145756,
	0,
	0,
	0,
	0,
	0,
	0,
	2056,
	1781,
	2181,
	1860,
	-24,
	-12,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145757,
	3145756,
	2048,
	1840,
	2096,
	1840,
	0);
INSERT INTO GD_LS
	VALUES (3145758,
	3145756,
	2096,
	1840,
	2096,
	1776,
	3145757);
INSERT INTO GD_LS
	VALUES (3145759,
	3145756,
	2096,
	1776,
	2048,
	1776,
	3145758);
INSERT INTO GD_GE
	VALUES (3145760,
	3145729,
	3145734,
	42);
INSERT INTO GD_CON
	VALUES (3145760,
	3145733,
	3145734,
	0);
INSERT INTO GD_CTXT
	VALUES (3145760,
	0,
	0,
	0,
	0,
	0,
	0,
	1852,
	1878,
	2076,
	1932,
	12,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145761,
	3145760,
	1856,
	1856,
	1856,
	1936,
	0);
INSERT INTO GD_GE
	VALUES (3145762,
	3145729,
	3145735,
	42);
INSERT INTO GD_CON
	VALUES (3145762,
	3145738,
	3145739,
	0);
INSERT INTO GD_CTXT
	VALUES (3145762,
	0,
	0,
	0,
	0,
	0,
	0,
	2048,
	1142,
	2243,
	1164,
	-7,
	15,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145763,
	3145762,
	2256,
	1200,
	2256,
	1168,
	0);
INSERT INTO GD_LS
	VALUES (3145764,
	3145762,
	2256,
	1168,
	2256,
	1104,
	3145763);
INSERT INTO GD_GE
	VALUES (3145765,
	3145729,
	3145736,
	42);
INSERT INTO GD_CON
	VALUES (3145765,
	3145737,
	3145740,
	0);
INSERT INTO GD_CTXT
	VALUES (3145765,
	0,
	0,
	0,
	0,
	0,
	0,
	2536,
	1422,
	2647,
	1444,
	-3,
	-22,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145766,
	3145765,
	2528,
	1472,
	2656,
	1472,
	0);
INSERT INTO GD_GE
	VALUES (3145767,
	3145729,
	3145738,
	42);
INSERT INTO GD_CON
	VALUES (3145767,
	3145740,
	3145741,
	0);
INSERT INTO GD_CTXT
	VALUES (3145767,
	0,
	0,
	0,
	0,
	0,
	0,
	2579,
	1332,
	2708,
	1354,
	-54,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145768,
	3145767,
	2768,
	1392,
	2768,
	1296,
	0);
INSERT INTO GD_GE
	VALUES (3145769,
	3145729,
	3145737,
	42);
INSERT INTO GD_CON
	VALUES (3145769,
	3145741,
	3145738,
	0);
INSERT INTO GD_CTXT
	VALUES (3145769,
	0,
	0,
	0,
	0,
	0,
	0,
	2535,
	1176,
	2860,
	1198,
	103,
	-60,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145770,
	3145769,
	2656,
	1264,
	2528,
	1264,
	0);
INSERT INTO GD_GE
	VALUES (3145771,
	3145729,
	3145739,
	42);
INSERT INTO GD_CON
	VALUES (3145771,
	3145742,
	3145743,
	0);
INSERT INTO GD_CTXT
	VALUES (3145771,
	0,
	0,
	0,
	0,
	0,
	0,
	2625,
	1879,
	2754,
	1901,
	-8,
	-8,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145772,
	3145771,
	2768,
	1936,
	2768,
	1856,
	0);
INSERT INTO GD_GE
	VALUES (3145773,
	3145729,
	3145740,
	42);
INSERT INTO GD_CON
	VALUES (3145773,
	3145735,
	3145742,
	0);
INSERT INTO GD_CTXT
	VALUES (3145773,
	0,
	0,
	0,
	0,
	0,
	0,
	2536,
	1952,
	2647,
	1974,
	-3,
	-4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145774,
	3145773,
	2528,
	1984,
	2656,
	1984,
	0);
INSERT INTO GD_GE
	VALUES (3145775,
	3145729,
	3145741,
	42);
INSERT INTO GD_CON
	VALUES (3145775,
	3145743,
	3145736,
	0);
INSERT INTO GD_CTXT
	VALUES (3145775,
	0,
	0,
	0,
	0,
	0,
	0,
	2525,
	1728,
	2836,
	1775,
	-2,
	-34,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145776,
	3145775,
	2656,
	1808,
	2528,
	1808,
	0);
INSERT INTO GD_GE
	VALUES (3145777,
	3145729,
	3145742,
	42);
INSERT INTO GD_CON
	VALUES (3145777,
	3145744,
	3145737,
	0);
INSERT INTO GD_CTXT
	VALUES (3145777,
	0,
	0,
	0,
	0,
	0,
	0,
	2396,
	1525,
	2554,
	1565,
	176,
	7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145778,
	3145777,
	2384,
	1584,
	2384,
	1488,
	0);
INSERT INTO GD_GE
	VALUES (3145779,
	3145729,
	3145743,
	42);
INSERT INTO GD_CON
	VALUES (3145779,
	3145736,
	3145744,
	0);
INSERT INTO GD_CTXT
	VALUES (3145779,
	0,
	0,
	0,
	0,
	0,
	0,
	2235,
	1700,
	2372,
	1722,
	-6,
	-11,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145780,
	3145779,
	2384,
	1760,
	2384,
	1680,
	0);
INSERT INTO GD_GE
	VALUES (3145781,
	3145729,
	3145744,
	42);
INSERT INTO GD_CON
	VALUES (3145781,
	3145734,
	3145745,
	0);
INSERT INTO GD_CTXT
	VALUES (3145781,
	0,
	0,
	0,
	0,
	0,
	0,
	1813,
	2189,
	1942,
	2211,
	92,
	94,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145782,
	3145781,
	1856,
	2032,
	1856,
	2176,
	0);
INSERT INTO GD_LS
	VALUES (3145783,
	3145781,
	1856,
	2176,
	1984,
	2176,
	3145782);
INSERT INTO GD_GE
	VALUES (3145784,
	3145729,
	3145745,
	42);
INSERT INTO GD_CON
	VALUES (3145784,
	3145745,
	3145735,
	0);
INSERT INTO GD_CTXT
	VALUES (3145784,
	0,
	0,
	0,
	0,
	0,
	0,
	2289,
	2189,
	2587,
	2211,
	193,
	94,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145785,
	3145784,
	2272,
	2176,
	2400,
	2176,
	0);
INSERT INTO GD_LS
	VALUES (3145786,
	3145784,
	2400,
	2176,
	2400,
	2032,
	3145785);
INSERT INTO GD_MD
	VALUES (2621441,
	5,
	2621445,
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
	VALUES (2621444,
	2621441,
	2621441,
	21);
INSERT INTO GD_SHP
	VALUES (2621444,
	1712,
	1296,
	1920,
	1536);
INSERT INTO GD_MD
	VALUES (2621442,
	6,
	2621445,
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
	VALUES (2621445,
	2621442,
	2621441,
	21);
INSERT INTO GD_SHP
	VALUES (2621445,
	1712,
	1296,
	1904,
	1360);
INSERT INTO GD_MD
	VALUES (2621443,
	7,
	2621445,
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
	VALUES (2621446,
	2621443,
	2621441,
	21);
INSERT INTO GD_SHP
	VALUES (2621446,
	1712,
	1296,
	1904,
	1360);
INSERT INTO S_SS
	VALUES (3670023,
	'tm',
	'',
	'',
	200,
	219826,
	3670023);
INSERT INTO O_OBJ
	VALUES (3670017,
	'tm init',
	200,
	'INIT',
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
	'id',
	'',
	'',
	'id',
	0,
	524291);
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
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	3670017);
INSERT INTO O_OIDA
	VALUES (3670017,
	3670017,
	0);
INSERT INTO SM_ISM
	VALUES (4194312,
	3670017);
INSERT INTO SM_SM
	VALUES (4194312,
	'',
	8);
INSERT INTO SM_MOORE
	VALUES (4194312);
INSERT INTO SM_SUPDT
	VALUES (4194305,
	4194312,
	0);
INSERT INTO SM_STATE
	VALUES (4194305,
	4194312,
	4194305,
	'tm init',
	1,
	0);
INSERT INTO SM_LEVT
	VALUES (4194305,
	4194312,
	4194305);
INSERT INTO SM_SEVT
	VALUES (4194305,
	4194312,
	4194305);
INSERT INTO SM_EVT
	VALUES (4194305,
	4194312,
	4194305,
	1,
	'start test',
	0,
	'',
	'INIT1',
	'');
INSERT INTO SM_SEME
	VALUES (4194305,
	4194305,
	4194312,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194305,
	4194312,
	4194305,
	4194305,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194305,
	4194312,
	4194305,
	4194305);
INSERT INTO SM_MOAH
	VALUES (4194305,
	4194312,
	4194305);
INSERT INTO SM_AH
	VALUES (4194305,
	4194312);
INSERT INTO SM_ACT
	VALUES (4194305,
	4194312,
	1,
	'// Just a few TIM bridge invocations from within an initialization state.
today = TIM::current_date( );
now = TIM::current_clock( ) ;
cr_dt = TIM::create_date( second: TIM::get_second( date: today ), minute: TIM::get_minute( date: today ), hour: TIM::get_hour( date: today ), day: TIM::get_day( date: today ), month: TIM::get_month( date: today ), year: TIM::get_year( date: today ) );

generate TMD1:''Start TM Test''() to TMD creator;',
	'');
INSERT INTO GD_MD
	VALUES (4194305,
	8,
	4194312,
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
	VALUES (4194306,
	4194305,
	4194305,
	41);
INSERT INTO GD_SHP
	VALUES (4194306,
	1680,
	1440,
	2224,
	1744);
INSERT INTO GD_GE
	VALUES (4194307,
	4194305,
	4194305,
	42);
INSERT INTO GD_CON
	VALUES (4194307,
	4194306,
	4194306,
	0);
INSERT INTO GD_CTXT
	VALUES (4194307,
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
	VALUES (4194308,
	4194307,
	1680,
	1520,
	1616,
	1520,
	0);
INSERT INTO GD_LS
	VALUES (4194309,
	4194307,
	1616,
	1520,
	1616,
	1376,
	4194308);
INSERT INTO GD_LS
	VALUES (4194310,
	4194307,
	1616,
	1376,
	1760,
	1376,
	4194309);
INSERT INTO GD_LS
	VALUES (4194311,
	4194307,
	1760,
	1376,
	1760,
	1440,
	4194310);
INSERT INTO O_OBJ
	VALUES (3670018,
	'Bogus Class',
	201,
	'BC',
	'',
	3670023);
INSERT INTO O_TFR
	VALUES (3670017,
	3670018,
	'TestTIM',
	'',
	524289,
	0,
	'//////////////////////////////////////////////
// Test incoming parameters.
//////////////////////////////////////////////
if ( param.create_date == param.current_date )
  LOG::LogSuccess(message:"Success in class operation TestTIM - param.create_date is equal to param.current_date.");
else
  LOG::LogFailure(message:"Failure in class operation TestTIM - param.create_date is not equal to param.current_date.");
end if;

if ( ( param.get_second + param.get_minute + param.get_hour + param.get_day + param.get_month + param.get_year ) == 6 )
  LOG::LogSuccess(message:"Success in class operation TestTIM - Sum of param.get_* is equal to six(6).");
else
  LOG::LogFailure(message:"Failure in class operation TestTIM - Sum of param.get_* is not equal to six(6).");
end if;

if ( param.timer_time_add )
  LOG::LogSuccess(message:"Success in class operation TestTIM - param.timer_add_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in class operation TestTIM - param.timer_add_time is not TRUE.");
end if;

if ( param.timer_reset_time )
  LOG::LogSuccess(message:"Success in class operation TestTIM - param.timer_reset_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in class operation TestTIM - param.timer_reset_time is not TRUE.");
end if;

if ( param.timer_cancel )
  LOG::LogSuccess(message:"Success in class operation TestTIM - param.timer_cancel_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in class operation TestTIM - param.timer_cancel_time is not TRUE.");
end if;

if ( param.timer_remaining_time >= 500000 )
  LOG::LogSuccess(message:"Success in class operation TestTIM - param.timer_remaining_time is >= 500000.");
else
  LOG::LogFailure(message:"Failure in class operation TestTIM - param.timer_remaining_time is < 500000.");
end if;

if ( TIM::timer_cancel( timer_inst_ref: param.timer_start ) == TRUE )
  LOG::LogSuccess(message:"Success in class operation TestTIM - param.timer_start was cancelled.");
else
  LOG::LogFailure(message:"Failure in class operation TestTIM - param.timer_start was not cancelled.");
end if;

if ( TIM::timer_cancel( timer_inst_ref: param.timer_start_recurring ) == TRUE )
  LOG::LogSuccess(message:"Success in class operation TestTIM - param.timer_start_recurring was cancelled.");
else
  LOG::LogFailure(message:"Failure in class operation TestTIM - param.timer_start_recurring was not cancelled.");
end if;

//////////////////////////////////////////////
// TIM::current_clock
//////////////////////////////////////////////
clock = TIM::current_clock();
if ( param.current_clock <= clock )
  LOG::LogSuccess(message:"Success in class operation TestTIM - param.current_clock is less than or equal to clock.");
else
  LOG::LogFailure(message:"Failure in class operation TestTIM - param.current_clock is greater than clock.");
end if;

//////////////////////////////////////////////
// TIM::current_date
//////////////////////////////////////////////
today = TIM::current_date();

//////////////////////////////////////////////
// TIM::get_day
// TIM::get_hour
// TIM::get_minute
// TIM::get_month
// TIM::get_second
// TIM::get_year
// TIM::create_date
//////////////////////////////////////////////
sec = TIM::get_second( date: today );

if ( today == TIM::create_date( second:sec, minute: TIM::get_minute( date: today ), hour: TIM::get_hour( date: today ), day: TIM::get_day( date: today ), month: TIM::get_month( date: today ), year:TIM::get_year( date: today ) ) )
  LOG::LogSuccess(message:"Success in class operation TestTIM - TIM::create_date using TIM invocations.");
else
  LOG::LogFailure(message:"Failure in class operation TestTIM - TIM::create_date using TIM invocations.");
end if;

//////////////////////////////////////////////
// TIM::timer_start
// TIM::timer_remaining_time
//////////////////////////////////////////////
create event instance e of BC1(type:3) to BC creator;
timer1 = TIM::timer_start( microseconds: 5000000, event_inst: e );

create object instance bc3 of BC3;
bc3.type = 3;
bc3.timer = timer1;

// Log the remaining time.
time_remain = TIM::timer_remaining_time( timer_inst_ref: timer1 );
LOG::LogInt( int: time_remain, message: "Class Operation TestTIM - Time remaining on timer1." );

//////////////////////////////////////////////
// TIM::timer_start_recurring
// TIM::timer_remaining_time
//////////////////////////////////////////////
create object instance bc of BC;
bc.num_recur = 0;
num_recur = bc.num_recur;

create event instance e2 of BC1(type:0) to bc;
timer2 = TIM::timer_start_recurring( microseconds: 1, event_inst: e2 );

//////////////////////////////////////////////
// TIM::timer_add_time - live timer
// TIM::timer_reset_time - live timer
// TIM::timer_cancel - live timer
//////////////////////////////////////////////
// Add time to the live timer
if ( TIM::timer_add_time( timer_inst_ref: timer2, microseconds: 60000000 ) == TRUE )
  LOG::LogSuccess( message: "Class Operation TestTIM - TIM::timer_add_time returned that timer2 has had time added.");
    
  // Check the remaining time to ensure that the add worked
  if ( TIM::timer_remaining_time( timer_inst_ref: timer2 ) > 58000000 )
     LOG::LogSuccess(message:"Class Operation TestTIM - timer2 has had time added successfully.");
  else
    LOG::LogFailure( message: "Class Operation TestTIM - timer2 has not had time added.");
  end if;

else
  LOG::LogFailure( message: "Class Operation TestTIM - TIM::timer_add_time returned that timer2 has not had time added.");
end if;

// Reset the live timer
if ( TIM::timer_reset_time( timer_inst_ref: timer2, microseconds: 5000000  ) == TRUE )
  LOG::LogSuccess( message: "Class Operation TestTIM - TIM::timer_reset_time returned that timer2 was reset.");

  // Check if the timer actually was reset
  if ( TIM::timer_remaining_time( timer_inst_ref: timer2 ) < 6000000 )
     LOG::LogSuccess(message:"Class Operation TestTIM - timer2 has had time reset successfully.");
  else
    LOG::LogFailure( message: "Class Operation TestTIM - timer2 has not had time reset.");
  end if;
else
  LOG::LogFailure( message: "Class Operation TestTIM - TIM::timer_reset_time returned that timer2 was not reset.");
end if;

// Try to cancel the live timer.
if ( TIM::timer_cancel( timer_inst_ref: timer2 ) == TRUE )
  LOG::LogSuccess(message:"Class Operation TestTIM - TIM::timer_cancel returned that timer2 was cancelled.");
else
  LOG::LogFailure(message:"Class Operation TestTIM - TIM::timer_cancel returned that timer2 was cancelled.");
end if;',
	1);
INSERT INTO O_TPARM
	VALUES (3670017,
	3670017,
	'create_date',
	524302,
	0);
INSERT INTO O_TPARM
	VALUES (3670018,
	3670017,
	'current_clock',
	524303,
	0);
INSERT INTO O_TPARM
	VALUES (3670019,
	3670017,
	'current_date',
	524302,
	0);
INSERT INTO O_TPARM
	VALUES (3670020,
	3670017,
	'get_day',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670021,
	3670017,
	'get_hour',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670022,
	3670017,
	'get_minute',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670023,
	3670017,
	'get_month',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670024,
	3670017,
	'get_second',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670025,
	3670017,
	'get_year',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670026,
	3670017,
	'timer_time_add',
	524290,
	0);
INSERT INTO O_TPARM
	VALUES (3670027,
	3670017,
	'timer_cancel',
	524290,
	0);
INSERT INTO O_TPARM
	VALUES (3670028,
	3670017,
	'timer_remaining_time',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670029,
	3670017,
	'timer_reset_time',
	524290,
	0);
INSERT INTO O_TPARM
	VALUES (3670030,
	3670017,
	'timer_start',
	524304,
	0);
INSERT INTO O_TPARM
	VALUES (3670031,
	3670017,
	'timer_start_recurring',
	524304,
	0);
INSERT INTO O_TFR
	VALUES (3670018,
	3670018,
	'TestTIM2',
	'',
	524289,
	1,
	'//////////////////////////////////////////////
// Test incoming parameters.
//////////////////////////////////////////////
if ( param.create_date == param.current_date )
  LOG::LogSuccess(message:"Success in instance operation TestTIM - param.create_date is equal to param.current_date.");
else
  LOG::LogFailure(message:"Failure in instance operation TestTIM - param.create_date is not equal to param.current_date.");
end if;

if ( ( param.get_second + param.get_minute + param.get_hour + param.get_day + param.get_month + param.get_year ) == 6 )
  LOG::LogSuccess(message:"Success in instance operation TestTIM - Sum of param.get_* is equal to six(6).");
else
  LOG::LogFailure(message:"Failure in instance operation TestTIM - Sum of param.get_* is not equal to six(6).");
end if;

if ( param.timer_time_add )
  LOG::LogSuccess(message:"Success in instance operation TestTIM - param.timer_add_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in instance operation TestTIM - param.timer_add_time is not TRUE.");
end if;

if ( param.timer_reset_time )
  LOG::LogSuccess(message:"Success in instance operation TestTIM - param.timer_reset_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in instance operation TestTIM - param.timer_reset_time is not TRUE.");
end if;

if ( param.timer_cancel )
  LOG::LogSuccess(message:"Success in instance operation TestTIM - param.timer_cancel_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in instance operation TestTIM - param.timer_cancel_time is not TRUE.");
end if;

if ( param.timer_remaining_time >= 500000 )
  LOG::LogSuccess(message:"Success in instance operation TestTIM - param.timer_remaining_time is >= 500000.");
else
  LOG::LogFailure(message:"Failure in instance operation TestTIM - param.timer_remaining_time is < 500000.");
end if;

if ( TIM::timer_cancel( timer_inst_ref: param.timer_start ) == TRUE )
  LOG::LogSuccess(message:"Success in instance operation TestTIM - param.timer_start was cancelled.");
else
  LOG::LogFailure(message:"Failure in instance operation TestTIM - param.timer_start was not cancelled.");
end if;

if ( TIM::timer_cancel( timer_inst_ref: param.timer_start_recurring ) == TRUE )
  LOG::LogSuccess(message:"Success in instance operation TestTIM - param.timer_start_recurring was cancelled.");
else
  LOG::LogFailure(message:"Failure in instance operation TestTIM - param.timer_start_recurring was not cancelled.");
end if;

//////////////////////////////////////////////
// TIM::current_clock
//////////////////////////////////////////////
clock = TIM::current_clock();
if ( param.current_clock <= clock )
  LOG::LogSuccess(message:"Success in instance operation TestTIM - param.current_clock is less than or equal to clock.");
else
  LOG::LogFailure(message:"Failure in instance operation TestTIM - param.current_clock is greater than clock.");
end if;

//////////////////////////////////////////////
// TIM::current_date
//////////////////////////////////////////////
today = TIM::current_date();

//////////////////////////////////////////////
// TIM::get_day
// TIM::get_hour
// TIM::get_minute
// TIM::get_month
// TIM::get_second
// TIM::get_year
// TIM::create_date
//////////////////////////////////////////////
sec = TIM::get_second( date: today );

if ( today == TIM::create_date( second:sec, minute: TIM::get_minute( date: today ), hour: TIM::get_hour( date: today ), day: TIM::get_day( date: today ), month: TIM::get_month( date: today ), year:TIM::get_year( date: today ) ) )
  LOG::LogSuccess(message:"Success in instance operation TestTIM - TIM::create_date using TIM invocations.");
else
  LOG::LogFailure(message:"Failure in instance operation TestTIM - TIM::create_date using TIM invocations.");
end if;

//////////////////////////////////////////////
// TIM::timer_start
// TIM::timer_remaining_time
//////////////////////////////////////////////
create event instance e of BC1(type:4) to BC creator;
timer1 = TIM::timer_start( microseconds: 5000000, event_inst: e );

create object instance bc3 of BC3;
bc3.type = 4;
bc3.timer = timer1;

// Log the remaining time.
time_remain = TIM::timer_remaining_time( timer_inst_ref: timer1 );
LOG::LogInt( int: time_remain, message: "Instance Operation TestTIM - Time remaining on timer1." );

//////////////////////////////////////////////
// TIM::timer_start_recurring
// TIM::timer_remaining_time
//////////////////////////////////////////////
create object instance bc of BC;
bc.num_recur = 0;
num_recur = bc.num_recur;

create event instance e2 of BC1(type:0) to bc;
timer2 = TIM::timer_start_recurring( microseconds: 1, event_inst: e2 );

//////////////////////////////////////////////
// TIM::timer_add_time - live timer
// TIM::timer_reset_time - live timer
// TIM::timer_cancel - live timer
//////////////////////////////////////////////
// Add time to the live timer
if ( TIM::timer_add_time( timer_inst_ref: timer2, microseconds: 60000000 ) == TRUE )
  LOG::LogSuccess( message: "Instance Operation TestTIM - TIM::timer_add_time returned that timer2 has had time added.");
    
  // Check the remaining time to ensure that the add worked
  if ( TIM::timer_remaining_time( timer_inst_ref: timer2 ) > 58000000 )
     LOG::LogSuccess(message:"Instance Operation TestTIM - timer2 has had time added successfully.");
  else
    LOG::LogFailure( message: "Instance Operation TestTIM - timer2 has not had time added.");
  end if;

else
  LOG::LogFailure( message: "Instance Operation TestTIM - TIM::timer_add_time returned that timer2 has not had time added.");
end if;

// Reset the live timer
if ( TIM::timer_reset_time( timer_inst_ref: timer2, microseconds: 5000000  ) == TRUE )
  LOG::LogSuccess( message: "Instance Operation TestTIM - TIM::timer_reset_time returned that timer2 was reset.");

  // Check if the timer actually was reset
  if ( TIM::timer_remaining_time( timer_inst_ref: timer2 ) < 6000000 )
     LOG::LogSuccess(message:"Instance Operation TestTIM - timer2 has had time reset successfully.");
  else
    LOG::LogFailure( message: "Instance Operation TestTIM - timer2 has not had time reset.");
  end if;
else
  LOG::LogFailure( message: "Instance Operation TestTIM - TIM::timer_reset_time returned that timer2 was not reset.");
end if;

// Try to cancel the live timer.
if ( TIM::timer_cancel( timer_inst_ref: timer2 ) == TRUE )
  LOG::LogSuccess(message:"Instance Operation TestTIM - TIM::timer_cancel returned that timer2 was cancelled.");
else
  LOG::LogFailure(message:"Instance Operation TestTIM - TIM::timer_cancel returned that timer2 was cancelled.");
end if;',
	1);
INSERT INTO O_TPARM
	VALUES (3670032,
	3670018,
	'create_date',
	524302,
	0);
INSERT INTO O_TPARM
	VALUES (3670033,
	3670018,
	'current_clock',
	524303,
	0);
INSERT INTO O_TPARM
	VALUES (3670034,
	3670018,
	'current_date',
	524302,
	0);
INSERT INTO O_TPARM
	VALUES (3670035,
	3670018,
	'get_day',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670036,
	3670018,
	'get_hour',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670037,
	3670018,
	'get_minute',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670038,
	3670018,
	'get_month',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670039,
	3670018,
	'get_second',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670040,
	3670018,
	'get_year',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670041,
	3670018,
	'timer_time_add',
	524290,
	0);
INSERT INTO O_TPARM
	VALUES (3670042,
	3670018,
	'timer_cancel',
	524290,
	0);
INSERT INTO O_TPARM
	VALUES (3670043,
	3670018,
	'timer_remaining_time',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670044,
	3670018,
	'timer_reset_time',
	524290,
	0);
INSERT INTO O_TPARM
	VALUES (3670045,
	3670018,
	'timer_start',
	524304,
	0);
INSERT INTO O_TPARM
	VALUES (3670046,
	3670018,
	'timer_start_recurring',
	524304,
	0);
INSERT INTO O_TFR
	VALUES (3670019,
	3670018,
	'TestTIM3',
	'',
	524289,
	0,
	'',
	1);
INSERT INTO O_TPARM
	VALUES (3670047,
	3670019,
	'create_date',
	524302,
	0);
INSERT INTO O_TPARM
	VALUES (3670048,
	3670019,
	'current_clock',
	524303,
	0);
INSERT INTO O_TPARM
	VALUES (3670049,
	3670019,
	'current_date',
	524302,
	0);
INSERT INTO O_TPARM
	VALUES (3670050,
	3670019,
	'get_day',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670051,
	3670019,
	'get_hour',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670052,
	3670019,
	'get_minute',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670053,
	3670019,
	'get_month',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670054,
	3670019,
	'get_second',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670055,
	3670019,
	'get_year',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670056,
	3670019,
	'timer_time_add',
	524290,
	0);
INSERT INTO O_TPARM
	VALUES (3670057,
	3670019,
	'timer_cancel',
	524290,
	0);
INSERT INTO O_TPARM
	VALUES (3670058,
	3670019,
	'timer_remaining_time',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670059,
	3670019,
	'timer_reset_time',
	524290,
	0);
INSERT INTO O_TPARM
	VALUES (3670060,
	3670019,
	'timer_start',
	524304,
	0);
INSERT INTO O_TPARM
	VALUES (3670061,
	3670019,
	'timer_start_recurring',
	524304,
	0);
INSERT INTO O_TFR
	VALUES (3670020,
	3670018,
	'TestTIM4',
	'',
	524289,
	1,
	'',
	1);
INSERT INTO O_TPARM
	VALUES (3670062,
	3670020,
	'create_date',
	524302,
	0);
INSERT INTO O_TPARM
	VALUES (3670063,
	3670020,
	'current_clock',
	524303,
	0);
INSERT INTO O_TPARM
	VALUES (3670064,
	3670020,
	'current_date',
	524302,
	0);
INSERT INTO O_TPARM
	VALUES (3670065,
	3670020,
	'get_day',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670066,
	3670020,
	'get_hour',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670067,
	3670020,
	'get_minute',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670068,
	3670020,
	'get_month',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670069,
	3670020,
	'get_second',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670070,
	3670020,
	'get_year',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670071,
	3670020,
	'timer_time_add',
	524290,
	0);
INSERT INTO O_TPARM
	VALUES (3670072,
	3670020,
	'timer_cancel',
	524290,
	0);
INSERT INTO O_TPARM
	VALUES (3670073,
	3670020,
	'timer_remaining_time',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3670074,
	3670020,
	'timer_reset_time',
	524290,
	0);
INSERT INTO O_TPARM
	VALUES (3670075,
	3670020,
	'timer_start',
	524304,
	0);
INSERT INTO O_TPARM
	VALUES (3670076,
	3670020,
	'timer_start_recurring',
	524304,
	0);
INSERT INTO O_TFR
	VALUES (3670021,
	3670018,
	'TestTIM5',
	'',
	524289,
	0,
	'select any bc3 from instances of BC3 where selected.type == 3;
timer1 = bc3.timer;

//////////////////////////////////////////////
// TIM::timer_add_time - dead timer
// TIM::timer_reset_time - dead timer
// TIM::timer_cancel - dead timer
//////////////////////////////////////////////
// Try to add time to the dead timer.
if ( TIM::timer_add_time( timer_inst_ref: timer1, microseconds: 1000000 ) == FALSE )
  LOG::LogSuccess( message: "Class Operation TestTIM - TIM::timer_add_time returned that timer1 has popped.");
else
  LOG::LogFailure( message: "Class Operation TestTIM - TIM::timer_add_time returned that timer1 has not popped.");
end if;

// Try to reset the time on the dead timer.
if ( TIM::timer_reset_time( timer_inst_ref: timer1, microseconds: 1000000  ) == FALSE )
  LOG::LogSuccess( message: "Class Operation TestTIM - TIM::timer_reset_time returned that timer1 has popped.");
else
  LOG::LogFailure( message: "Class Operation TestTIM - TIM::timer_reset_time returned that timer1 has not popped.");
end if;

// Try to cancel the dead timer.
if ( TIM::timer_cancel( timer_inst_ref: timer1 ) == FALSE )
  LOG::LogSuccess(message:"Class Operation TestTIM - TIM::timer_cancel returned that timer1 has popped.");
else
  LOG::LogFailure(message:"Class Operation TestTIM - TIM::timer_cancel returned that timer1 has not popped.");
end if;
',
	1);
INSERT INTO O_TFR
	VALUES (3670022,
	3670018,
	'TestTIM6',
	'',
	524289,
	1,
	'select any bc3 from instances of BC3 where selected.type == 4;
timer1 = bc3.timer;

//////////////////////////////////////////////
// TIM::timer_add_time - dead timer
// TIM::timer_reset_time - dead timer
// TIM::timer_cancel - dead timer
//////////////////////////////////////////////
// Try to add time to the dead timer.
if ( TIM::timer_add_time( timer_inst_ref: timer1, microseconds: 1000000 ) == FALSE )
  LOG::LogSuccess( message: "Instance Operation TestTIM - TIM::timer_add_time returned that timer1 has popped.");
else
  LOG::LogFailure( message: "Instance Operation TestTIM - TIM::timer_add_time returned that timer1 has not popped.");
end if;

// Try to reset the time on the dead timer.
if ( TIM::timer_reset_time( timer_inst_ref: timer1, microseconds: 1000000  ) == FALSE )
  LOG::LogSuccess( message: "Instance Operation TestTIM - TIM::timer_reset_time returned that timer1 has popped.");
else
  LOG::LogFailure( message: "Instance Operation TestTIM - TIM::timer_reset_time returned that timer1 has not popped.");
end if;

// Try to cancel the dead timer.
if ( TIM::timer_cancel( timer_inst_ref: timer1 ) == FALSE )
  LOG::LogSuccess(message:"Instance Operation TestTIM - TIM::timer_cancel returned that timer1 has popped.");
else
  LOG::LogFailure(message:"Instance Operation TestTIM - TIM::timer_cancel returned that timer1 has not popped.");
end if;
',
	1);
INSERT INTO O_NBATTR
	VALUES (3670019,
	3670018);
INSERT INTO O_BATTR
	VALUES (3670019,
	3670018);
INSERT INTO O_ATTR
	VALUES (3670019,
	3670018,
	0,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_NBATTR
	VALUES (3670020,
	3670018);
INSERT INTO O_BATTR
	VALUES (3670020,
	3670018);
INSERT INTO O_ATTR
	VALUES (3670020,
	3670018,
	3670019,
	'num_recur',
	'',
	'',
	'num_recur',
	0,
	524291);
INSERT INTO SM_ISM
	VALUES (4718601,
	3670018);
INSERT INTO SM_SM
	VALUES (4718601,
	'',
	9);
INSERT INTO SM_MOORE
	VALUES (4718601);
INSERT INTO SM_EVTDI
	VALUES (4718593,
	4718601,
	'create_date',
	'',
	524302);
INSERT INTO SM_EVTDI
	VALUES (4718594,
	4718601,
	'current_clock',
	'',
	524303);
INSERT INTO SM_EVTDI
	VALUES (4718595,
	4718601,
	'current_date',
	'',
	524302);
INSERT INTO SM_EVTDI
	VALUES (4718596,
	4718601,
	'get_day',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (4718597,
	4718601,
	'get_hour',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (4718598,
	4718601,
	'get_minute',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (4718599,
	4718601,
	'get_month',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (4718600,
	4718601,
	'get_second',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (4718601,
	4718601,
	'get_year',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (4718602,
	4718601,
	'timer_time_add',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (4718603,
	4718601,
	'timer_cancel',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (4718604,
	4718601,
	'timer_remaining_time',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (4718605,
	4718601,
	'timer_reset_time',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (4718606,
	4718601,
	'timer_start',
	'',
	524304);
INSERT INTO SM_EVTDI
	VALUES (4718607,
	4718601,
	'timer_start_recurring',
	'',
	524304);
INSERT INTO SM_EVTDI
	VALUES (4718608,
	4718601,
	'type',
	'',
	524291);
INSERT INTO SM_SUPDT
	VALUES (4718593,
	4718601,
	0);
INSERT INTO SM_SDI
	VALUES (4718608,
	4718593,
	4718601);
INSERT INTO SM_SUPDT
	VALUES (4718594,
	4718601,
	0);
INSERT INTO SM_SDI
	VALUES (4718597,
	4718594,
	4718601);
INSERT INTO SM_SDI
	VALUES (4718606,
	4718594,
	4718601);
INSERT INTO SM_SDI
	VALUES (4718596,
	4718594,
	4718601);
INSERT INTO SM_SDI
	VALUES (4718599,
	4718594,
	4718601);
INSERT INTO SM_SDI
	VALUES (4718598,
	4718594,
	4718601);
INSERT INTO SM_SDI
	VALUES (4718607,
	4718594,
	4718601);
INSERT INTO SM_SDI
	VALUES (4718603,
	4718594,
	4718601);
INSERT INTO SM_SDI
	VALUES (4718600,
	4718594,
	4718601);
INSERT INTO SM_SDI
	VALUES (4718602,
	4718594,
	4718601);
INSERT INTO SM_SDI
	VALUES (4718601,
	4718594,
	4718601);
INSERT INTO SM_SDI
	VALUES (4718594,
	4718594,
	4718601);
INSERT INTO SM_SDI
	VALUES (4718605,
	4718594,
	4718601);
INSERT INTO SM_SDI
	VALUES (4718595,
	4718594,
	4718601);
INSERT INTO SM_SDI
	VALUES (4718593,
	4718594,
	4718601);
INSERT INTO SM_SDI
	VALUES (4718604,
	4718594,
	4718601);
INSERT INTO SM_STATE
	VALUES (4718593,
	4718601,
	4718593,
	'Bogus',
	1,
	0);
INSERT INTO SM_LEVT
	VALUES (4718593,
	4718601,
	4718593);
INSERT INTO SM_SEVT
	VALUES (4718593,
	4718601,
	4718593);
INSERT INTO SM_EVT
	VALUES (4718593,
	4718601,
	4718593,
	1,
	'',
	0,
	'',
	'BC1',
	'');
INSERT INTO SM_SEME
	VALUES (4718593,
	4718593,
	4718601,
	4718593);
INSERT INTO SM_LEVT
	VALUES (4718594,
	4718601,
	4718594);
INSERT INTO SM_SEVT
	VALUES (4718594,
	4718601,
	4718594);
INSERT INTO SM_EVT
	VALUES (4718594,
	4718601,
	4718594,
	2,
	'',
	0,
	'',
	'BC2',
	'');
INSERT INTO SM_SEME
	VALUES (4718593,
	4718594,
	4718601,
	4718594);
INSERT INTO SM_STATE
	VALUES (4718594,
	4718601,
	4718594,
	'Bogus Two',
	2,
	0);
INSERT INTO SM_EIGN
	VALUES (4718594,
	4718593,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718594,
	4718593,
	4718601,
	4718593);
INSERT INTO SM_EIGN
	VALUES (4718594,
	4718594,
	4718601,
	4718594,
	'');
INSERT INTO SM_SEME
	VALUES (4718594,
	4718594,
	4718601,
	4718594);
INSERT INTO SM_CRTXN
	VALUES (4718593,
	4718601,
	4718593,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718593,
	4718601,
	4718593,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718594,
	4718601,
	4718593,
	4718594,
	4718594);
INSERT INTO SM_TXN
	VALUES (4718594,
	4718601,
	4718594,
	4718594);
INSERT INTO SM_NSTXN
	VALUES (4718595,
	4718601,
	4718593,
	4718593,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718595,
	4718601,
	4718593,
	4718593);
INSERT INTO SM_MOAH
	VALUES (4718593,
	4718601,
	4718593);
INSERT INTO SM_AH
	VALUES (4718593,
	4718601);
INSERT INTO SM_ACT
	VALUES (4718593,
	4718601,
	1,
	'if ( rcvd_evt.type > 0 )
  x = rcvd_evt.type;
  if ( x == 1 )
    // Function
    ::TestTIM2();
  elif ( x == 2 )
    // Bridge
    GEE::TestTIM2();
  elif ( x == 3 )
    // Class Operation
    BC::TestTIM5();
  elif ( x == 4 )
    // Instance Operation
    self.TestTIM6();
  elif ( x == 5 )
    // MDA
    select any tmd from instances of TMD;
    y = tmd.mda2;
  elif ( x == 6 )
    // Class Action
    generate BC_A3 to BC assigner;
  end if;
else
  LOG::LogSuccess( message: "Recurring timer has fired." );
  LOG::LogInt( int: self.num_recur, message: "Number of times the timer has fired." );

  self.num_recur = self.num_recur + 1;
  if ( self.num_recur >= 5 )
    self.num_recur = 0;
    select any drv from instances of TMD;
    generate TMD4 to drv;
  end if;
end if;',
	'');
INSERT INTO SM_MOAH
	VALUES (4718594,
	4718601,
	4718594);
INSERT INTO SM_AH
	VALUES (4718594,
	4718601);
INSERT INTO SM_ACT
	VALUES (4718594,
	4718601,
	1,
	'//////////////////////////////////////////////
// Test incoming rcvd_evt.
//////////////////////////////////////////////
if ( rcvd_evt.create_date == rcvd_evt.current_date )
  LOG::LogSuccess(message:"Success in event parameter for Bogus Two - rcvd_evt.create_date is equal to rcvd_evt.current_date.");
else
  LOG::LogFailure(message:"Failure in event parameter for Bogus Two - rcvd_evt.create_date is not equal to rcvd_evt.current_date.");
end if;

if ( ( rcvd_evt.get_second + rcvd_evt.get_minute + rcvd_evt.get_hour + rcvd_evt.get_day + rcvd_evt.get_month + rcvd_evt.get_year ) == 6 )
  LOG::LogSuccess(message:"Success in event parameter for Bogus Two - Sum of rcvd_evt.get_* is equal to six(6).");
else
  LOG::LogFailure(message:"Failure in event parameter for Bogus Two - Sum of rcvd_evt.get_* is not equal to six(6).");
end if;

if ( rcvd_evt.timer_time_add )
  LOG::LogSuccess(message:"Success in event parameter TestTIM - rcvd_evt.timer_add_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in event parameter TestTIM - rcvd_evt.timer_add_time is not TRUE.");
end if;

if ( rcvd_evt.timer_reset_time )
  LOG::LogSuccess(message:"Success in event parameter TestTIM - rcvd_evt.timer_reset_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in event parameter TestTIM - rcvd_evt.timer_reset_time is not TRUE.");
end if;

if ( rcvd_evt.timer_cancel )
  LOG::LogSuccess(message:"Success in event parameter TestTIM - rcvd_evt.timer_cancel_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in event parameter TestTIM - rcvd_evt.timer_cancel_time is not TRUE.");
end if;

if ( rcvd_evt.timer_remaining_time >= 500000 )
  LOG::LogSuccess(message:"Success in event parameter TestTIM - rcvd_evt.timer_remaining_time is >= 500000.");
else
  LOG::LogFailure(message:"Failure in event parameter TestTIM - rcvd_evt.timer_remaining_time is < 500000.");
end if;

if ( TIM::timer_cancel( timer_inst_ref: rcvd_evt.timer_start ) == TRUE )
  LOG::LogSuccess(message:"Success in event parameter for Bogus Two - rcvd_evt.timer_start was cancelled.");
else
  LOG::LogFailure(message:"Failure in event parameter for Bogus Two - rcvd_evt.timer_start was not cancelled.");
end if;

if ( TIM::timer_cancel( timer_inst_ref: rcvd_evt.timer_start_recurring ) == TRUE )
  LOG::LogSuccess(message:"Success in event parameter for Bogus Two - rcvd_evt.timer_start_recurring was cancelled.");
else
  LOG::LogFailure(message:"Failure in event parameter for Bogus Two - rcvd_evt.timer_start_recurring was not cancelled.");
end if;

//////////////////////////////////////////////
// TIM::current_clock
//////////////////////////////////////////////
clock = TIM::current_clock();
if ( rcvd_evt.current_clock <= clock )
  LOG::LogSuccess(message:"Success in event parameter for Bogus Two - rcvd_evt.current_clock is less than or equal to clock.");
else
  LOG::LogFailure(message:"Failure in event parameter for Bogus Two - rcvd_evt.current_clock is greater than clock.");
end if;

select any drv from instances of TMD;
generate TMD4 to drv;
',
	'');
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
	1760,
	4009,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (4718594,
	4718593,
	4718593,
	41);
INSERT INTO GD_SHP
	VALUES (4718594,
	1776,
	1328,
	2048,
	1520);
INSERT INTO GD_GE
	VALUES (4718595,
	4718593,
	4718594,
	41);
INSERT INTO GD_SHP
	VALUES (4718595,
	1776,
	1632,
	2048,
	1792);
INSERT INTO GD_GE
	VALUES (4718596,
	4718593,
	4718593,
	42);
INSERT INTO GD_CON
	VALUES (4718596,
	4718594,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (4718596,
	0,
	0,
	0,
	0,
	0,
	0,
	1861,
	1276,
	1901,
	1298,
	-13,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718597,
	4718596,
	1920,
	1328,
	1920,
	1248,
	0);
INSERT INTO GD_GE
	VALUES (4718598,
	4718593,
	4718594,
	42);
INSERT INTO GD_CON
	VALUES (4718598,
	4718594,
	4718595,
	0);
INSERT INTO GD_CTXT
	VALUES (4718598,
	0,
	0,
	0,
	0,
	0,
	0,
	1930,
	1538,
	2329,
	1614,
	431,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718599,
	4718598,
	1904,
	1520,
	1904,
	1632,
	0);
INSERT INTO GD_GE
	VALUES (4718600,
	4718593,
	4718595,
	42);
INSERT INTO GD_CON
	VALUES (4718600,
	4718594,
	4718594,
	0);
INSERT INTO GD_CTXT
	VALUES (4718600,
	0,
	0,
	0,
	0,
	0,
	0,
	2110,
	1400,
	2150,
	1422,
	60,
	1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718601,
	4718600,
	2048,
	1456,
	2096,
	1456,
	0);
INSERT INTO GD_LS
	VALUES (4718602,
	4718600,
	2096,
	1456,
	2096,
	1360,
	4718601);
INSERT INTO GD_LS
	VALUES (4718603,
	4718600,
	2096,
	1360,
	2048,
	1360,
	4718602);
INSERT INTO SM_ASM
	VALUES (5242890,
	3670018);
INSERT INTO SM_SM
	VALUES (5242890,
	'',
	10);
INSERT INTO SM_MOORE
	VALUES (5242890);
INSERT INTO SM_EVTDI
	VALUES (5242881,
	5242890,
	'timer_start_recurring',
	'',
	524304);
INSERT INTO SM_EVTDI
	VALUES (5242882,
	5242890,
	'timer_reset_time',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (5242883,
	5242890,
	'current_clock',
	'',
	524303);
INSERT INTO SM_EVTDI
	VALUES (5242884,
	5242890,
	'timer_time_add',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (5242885,
	5242890,
	'timer_cancel',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (5242886,
	5242890,
	'get_minute',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (5242887,
	5242890,
	'get_month',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (5242888,
	5242890,
	'current_date',
	'',
	524302);
INSERT INTO SM_EVTDI
	VALUES (5242889,
	5242890,
	'timer_start',
	'',
	524304);
INSERT INTO SM_EVTDI
	VALUES (5242890,
	5242890,
	'get_hour',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (5242891,
	5242890,
	'get_second',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (5242892,
	5242890,
	'get_day',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (5242893,
	5242890,
	'create_date',
	'',
	524302);
INSERT INTO SM_EVTDI
	VALUES (5242894,
	5242890,
	'timer_remaining_time',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (5242895,
	5242890,
	'get_year',
	'',
	524291);
INSERT INTO SM_SUPDT
	VALUES (5242881,
	5242890,
	0);
INSERT INTO SM_SUPDT
	VALUES (5242882,
	5242890,
	0);
INSERT INTO SM_SDI
	VALUES (5242882,
	5242882,
	5242890);
INSERT INTO SM_SDI
	VALUES (5242884,
	5242882,
	5242890);
INSERT INTO SM_SDI
	VALUES (5242889,
	5242882,
	5242890);
INSERT INTO SM_SDI
	VALUES (5242892,
	5242882,
	5242890);
INSERT INTO SM_SDI
	VALUES (5242890,
	5242882,
	5242890);
INSERT INTO SM_SDI
	VALUES (5242893,
	5242882,
	5242890);
INSERT INTO SM_SDI
	VALUES (5242881,
	5242882,
	5242890);
INSERT INTO SM_SDI
	VALUES (5242886,
	5242882,
	5242890);
INSERT INTO SM_SDI
	VALUES (5242885,
	5242882,
	5242890);
INSERT INTO SM_SDI
	VALUES (5242883,
	5242882,
	5242890);
INSERT INTO SM_SDI
	VALUES (5242891,
	5242882,
	5242890);
INSERT INTO SM_SDI
	VALUES (5242895,
	5242882,
	5242890);
INSERT INTO SM_SDI
	VALUES (5242888,
	5242882,
	5242890);
INSERT INTO SM_SDI
	VALUES (5242894,
	5242882,
	5242890);
INSERT INTO SM_SDI
	VALUES (5242887,
	5242882,
	5242890);
INSERT INTO SM_STATE
	VALUES (5242881,
	5242890,
	5242881,
	'Bogus',
	1,
	0);
INSERT INTO SM_LEVT
	VALUES (5242881,
	5242890,
	5242881);
INSERT INTO SM_SEVT
	VALUES (5242881,
	5242890,
	5242881);
INSERT INTO SM_EVT
	VALUES (5242881,
	5242890,
	5242881,
	1,
	'',
	0,
	'',
	'BC_A1',
	'');
INSERT INTO SM_SEME
	VALUES (5242881,
	5242881,
	5242890,
	5242881);
INSERT INTO SM_LEVT
	VALUES (5242882,
	5242890,
	5242882);
INSERT INTO SM_SEVT
	VALUES (5242882,
	5242890,
	5242882);
INSERT INTO SM_EVT
	VALUES (5242882,
	5242890,
	5242882,
	2,
	'',
	0,
	'',
	'BC_A2',
	'');
INSERT INTO SM_SEME
	VALUES (5242881,
	5242882,
	5242890,
	5242882);
INSERT INTO SM_LEVT
	VALUES (5242883,
	5242890,
	5242881);
INSERT INTO SM_SEVT
	VALUES (5242883,
	5242890,
	5242881);
INSERT INTO SM_EVT
	VALUES (5242883,
	5242890,
	5242881,
	3,
	'',
	0,
	'',
	'BC_A3',
	'');
INSERT INTO SM_EIGN
	VALUES (5242881,
	5242883,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242881,
	5242883,
	5242890,
	5242881);
INSERT INTO SM_STATE
	VALUES (5242882,
	5242890,
	5242882,
	'Bogus Two',
	2,
	0);
INSERT INTO SM_EIGN
	VALUES (5242882,
	5242881,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242882,
	5242881,
	5242890,
	5242881);
INSERT INTO SM_EIGN
	VALUES (5242882,
	5242882,
	5242890,
	5242882,
	'');
INSERT INTO SM_SEME
	VALUES (5242882,
	5242882,
	5242890,
	5242882);
INSERT INTO SM_SEME
	VALUES (5242882,
	5242883,
	5242890,
	5242881);
INSERT INTO SM_STATE
	VALUES (5242883,
	5242890,
	5242881,
	'Bogus Three',
	3,
	0);
INSERT INTO SM_EIGN
	VALUES (5242883,
	5242881,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242883,
	5242881,
	5242890,
	5242881);
INSERT INTO SM_EIGN
	VALUES (5242883,
	5242882,
	5242890,
	5242882,
	'');
INSERT INTO SM_SEME
	VALUES (5242883,
	5242882,
	5242890,
	5242882);
INSERT INTO SM_EIGN
	VALUES (5242883,
	5242883,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242883,
	5242883,
	5242890,
	5242881);
INSERT INTO SM_CRTXN
	VALUES (5242881,
	5242890,
	5242881,
	5242881);
INSERT INTO SM_TXN
	VALUES (5242881,
	5242890,
	5242881,
	5242881);
INSERT INTO SM_NSTXN
	VALUES (5242882,
	5242890,
	5242881,
	5242882,
	5242882);
INSERT INTO SM_TXN
	VALUES (5242882,
	5242890,
	5242882,
	5242882);
INSERT INTO SM_NSTXN
	VALUES (5242883,
	5242890,
	5242881,
	5242881,
	5242881);
INSERT INTO SM_TXN
	VALUES (5242883,
	5242890,
	5242881,
	5242881);
INSERT INTO SM_NSTXN
	VALUES (5242884,
	5242890,
	5242882,
	5242883,
	5242881);
INSERT INTO SM_TXN
	VALUES (5242884,
	5242890,
	5242883,
	5242881);
INSERT INTO SM_MOAH
	VALUES (5242881,
	5242890,
	5242881);
INSERT INTO SM_AH
	VALUES (5242881,
	5242890);
INSERT INTO SM_ACT
	VALUES (5242881,
	5242890,
	1,
	'LOG::LogSuccess( message: "Assigner - Recurring timer has fired." );',
	'');
INSERT INTO SM_MOAH
	VALUES (5242882,
	5242890,
	5242882);
INSERT INTO SM_AH
	VALUES (5242882,
	5242890);
INSERT INTO SM_ACT
	VALUES (5242882,
	5242890,
	1,
	'//////////////////////////////////////////////
// Test incoming rcvd_evt.
//////////////////////////////////////////////
if ( rcvd_evt.create_date == rcvd_evt.current_date )
  LOG::LogSuccess(message:"Success in assigner event parameter for Bogus Two - rcvd_evt.create_date is equal to rcvd_evt.current_date.");
else
  LOG::LogFailure(message:"Failure in assigner event parameter for Bogus Two - rcvd_evt.create_date is not equal to rcvd_evt.current_date.");
end if;

if ( ( rcvd_evt.get_second + rcvd_evt.get_minute + rcvd_evt.get_hour + rcvd_evt.get_day + rcvd_evt.get_month + rcvd_evt.get_year ) == 6 )
  LOG::LogSuccess(message:"Success in assigner event parameter for Bogus Two - Sum of rcvd_evt.get_* is equal to six(6).");
else
  LOG::LogFailure(message:"Failure in assigner event parameter for Bogus Two - Sum of rcvd_evt.get_* is not equal to six(6).");
end if;

if ( rcvd_evt.timer_time_add )
  LOG::LogSuccess(message:"Success in assigner event parameter TestTIM - rcvd_evt.timer_add_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in assigner event parameter TestTIM - rcvd_evt.timer_add_time is not TRUE.");
end if;

if ( rcvd_evt.timer_reset_time )
  LOG::LogSuccess(message:"Success in assigner event parameter TestTIM - rcvd_evt.timer_reset_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in assigner event parameter TestTIM - rcvd_evt.timer_reset_time is not TRUE.");
end if;

if ( rcvd_evt.timer_cancel )
  LOG::LogSuccess(message:"Success in assigner event parameter TestTIM - rcvd_evt.timer_cancel_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in assigner event parameter TestTIM - rcvd_evt.timer_cancel_time is not TRUE.");
end if;

if ( rcvd_evt.timer_remaining_time >= 500000 )
  LOG::LogSuccess(message:"Success in assigner event parameter TestTIM - rcvd_evt.timer_remaining_time is >= 500000.");
else
  LOG::LogFailure(message:"Failure in assigner event parameter TestTIM - rcvd_evt.timer_remaining_time is < 500000.");
end if;

if ( TIM::timer_cancel( timer_inst_ref: rcvd_evt.timer_start ) == TRUE )
  LOG::LogSuccess(message:"Success in assigner event parameter for Bogus Two - rcvd_evt.timer_start was cancelled.");
else
  LOG::LogFailure(message:"Failure in assigner event parameter for Bogus Two - rcvd_evt.timer_start was not cancelled.");
end if;

if ( TIM::timer_cancel( timer_inst_ref: rcvd_evt.timer_start_recurring ) == TRUE )
  LOG::LogSuccess(message:"Success in assigner event parameter for Bogus Two - rcvd_evt.timer_start_recurring was cancelled.");
else
  LOG::LogFailure(message:"Failure in assigner event parameter for Bogus Two - rcvd_evt.timer_start_recurring was not cancelled.");
end if;

//////////////////////////////////////////////
// TIM::current_clock
//////////////////////////////////////////////
clock = TIM::current_clock();
if ( rcvd_evt.current_clock <= clock )
  LOG::LogSuccess(message:"Success in assigner event parameter for Bogus Two - rcvd_evt.current_clock is less than or equal to clock.");
else
  LOG::LogFailure(message:"Failure in assigner event parameter for Bogus Two - rcvd_evt.current_clock is greater than clock.");
end if;

//////////////////////////////////////////////
// TIM::current_date
//////////////////////////////////////////////
today = TIM::current_date();

//////////////////////////////////////////////
// TIM::get_day
// TIM::get_hour
// TIM::get_minute
// TIM::get_month
// TIM::get_second
// TIM::get_year
// TIM::create_date
//////////////////////////////////////////////
sec = TIM::get_second( date: today );

if ( today == TIM::create_date( second:sec, minute: TIM::get_minute( date: today ), hour: TIM::get_hour( date: today ), day: TIM::get_day( date: today ), month: TIM::get_month( date: today ), year:TIM::get_year( date: today ) ) )
  LOG::LogSuccess(message:"Success in assigner action TestTIM - TIM::create_date using TIM invocations.");
else
  LOG::LogFailure(message:"Failure in assigner action TestTIM - TIM::create_date using TIM invocations.");
end if;

//////////////////////////////////////////////
// TIM::timer_start
// TIM::timer_remaining_time
//////////////////////////////////////////////
create event instance e of BC1(type:6) to BC creator;
timer1 = TIM::timer_start( microseconds: 5000000, event_inst: e );

create object instance bc3 of BC3;
bc3.type = 6;
bc3.timer = timer1;

// Log the remaining time.
time_remain = TIM::timer_remaining_time( timer_inst_ref: timer1 );
LOG::LogInt( int: time_remain, message: "Assigner Action TestTIM - Time remaining on timer1." );

//////////////////////////////////////////////
// TIM::timer_start_recurring
// TIM::timer_remaining_time
//////////////////////////////////////////////
create object instance bc of BC;
bc.num_recur = 0;
num_recur = bc.num_recur;

create event instance e2 of BC1(type:0) to bc;
timer2 = TIM::timer_start_recurring( microseconds: 1, event_inst: e2 );

//////////////////////////////////////////////
// TIM::timer_add_time - live timer
// TIM::timer_reset_time - live timer
// TIM::timer_cancel - live timer
//////////////////////////////////////////////
// Add time to the live timer
if ( TIM::timer_add_time( timer_inst_ref: timer2, microseconds: 60000000 ) == TRUE )
  LOG::LogSuccess( message: "Assigner Action TestTIM - TIM::timer_add_time returned that timer2 has had time added.");
    
  // Check the remaining time to ensure that the add worked
  if ( TIM::timer_remaining_time( timer_inst_ref: timer2 ) > 58000000 )
     LOG::LogSuccess(message:"Assigner Action TestTIM - timer2 has had time added successfully.");
  else
    LOG::LogFailure( message: "Assigner Action TestTIM - timer2 has not had time added.");
  end if;

else
  LOG::LogFailure( message: "Assigner Action TestTIM - TIM::timer_add_time returned that timer2 has not had time added.");
end if;

// Reset the live timer
if ( TIM::timer_reset_time( timer_inst_ref: timer2, microseconds: 5000000  ) == TRUE )
  LOG::LogSuccess( message: "Assigner Action TestTIM - TIM::timer_reset_time returned that timer2 was reset.");

  // Check if the timer actually was reset
  if ( TIM::timer_remaining_time( timer_inst_ref: timer2 ) < 6000000 )
     LOG::LogSuccess(message:"Assigner Action TestTIM - timer2 has had time reset successfully.");
  else
    LOG::LogFailure( message: "Assigner Action TestTIM - timer2 has not had time reset.");
  end if;
else
  LOG::LogFailure( message: "Assigner Action TestTIM - TIM::timer_reset_time returned that timer2 was not reset.");
end if;

// Try to cancel the live timer.
if ( TIM::timer_cancel( timer_inst_ref: timer2 ) == TRUE )
  LOG::LogSuccess(message:"Assigner Action TestTIM - TIM::timer_cancel returned that timer2 was cancelled.");
else
  LOG::LogFailure(message:"Assigner Action TestTIM - TIM::timer_cancel returned that timer2 was cancelled.");
end if;

// End the test
select any drv from instances of TMD;
generate TMD4 to drv;
',
	'');
INSERT INTO SM_MOAH
	VALUES (5242883,
	5242890,
	5242883);
INSERT INTO SM_AH
	VALUES (5242883,
	5242890);
INSERT INTO SM_ACT
	VALUES (5242883,
	5242890,
	1,
	'select any bc3 from instances of BC3 where selected.type == 6;
timer1 = bc3.timer;

//////////////////////////////////////////////
// TIM::timer_add_time - dead timer
// TIM::timer_reset_time - dead timer
// TIM::timer_cancel - dead timer
//////////////////////////////////////////////
// Try to add time to the dead timer.
if ( TIM::timer_add_time( timer_inst_ref: timer1, microseconds: 1000000 ) == FALSE )
  LOG::LogSuccess( message: "Assigner Action TestTIM - TIM::timer_add_time returned that timer1 has popped.");
else
  LOG::LogFailure( message: "Assigner Action TestTIM - TIM::timer_add_time returned that timer1 has not popped.");
end if;

// Try to reset the time on the dead timer.
if ( TIM::timer_reset_time( timer_inst_ref: timer1, microseconds: 1000000  ) == FALSE )
  LOG::LogSuccess( message: "Assigner Action TestTIM - TIM::timer_reset_time returned that timer1 has popped.");
else
  LOG::LogFailure( message: "Assigner Action TestTIM - TIM::timer_reset_time returned that timer1 has not popped.");
end if;

// Try to cancel the dead timer.
if ( TIM::timer_cancel( timer_inst_ref: timer1 ) == FALSE )
  LOG::LogSuccess(message:"Assigner Action TestTIM - TIM::timer_cancel returned that timer1 has popped.");
else
  LOG::LogFailure(message:"Assigner Action TestTIM - TIM::timer_cancel returned that timer1 has not popped.");
end if;
',
	'');
INSERT INTO GD_MD
	VALUES (5242881,
	10,
	5242890,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1748,
	3752,
	1.082474,
	0);
INSERT INTO GD_GE
	VALUES (5242882,
	5242881,
	5242881,
	41);
INSERT INTO GD_SHP
	VALUES (5242882,
	1808,
	1296,
	2080,
	1488);
INSERT INTO GD_GE
	VALUES (5242883,
	5242881,
	5242882,
	41);
INSERT INTO GD_SHP
	VALUES (5242883,
	1808,
	1600,
	2080,
	1760);
INSERT INTO GD_GE
	VALUES (5242892,
	5242881,
	5242883,
	41);
INSERT INTO GD_SHP
	VALUES (5242892,
	1808,
	1856,
	2080,
	2000);
INSERT INTO GD_GE
	VALUES (5242884,
	5242881,
	5242881,
	42);
INSERT INTO GD_CON
	VALUES (5242884,
	5242882,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (5242884,
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
	VALUES (5242885,
	5242884,
	1936,
	1296,
	1936,
	1232,
	0);
INSERT INTO GD_GE
	VALUES (5242886,
	5242881,
	5242882,
	42);
INSERT INTO GD_CON
	VALUES (5242886,
	5242882,
	5242883,
	0);
INSERT INTO GD_CTXT
	VALUES (5242886,
	0,
	0,
	0,
	0,
	0,
	0,
	1955,
	1512,
	2447,
	1588,
	517,
	4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5242887,
	5242886,
	1936,
	1488,
	1936,
	1600,
	0);
INSERT INTO GD_GE
	VALUES (5242888,
	5242881,
	5242883,
	42);
INSERT INTO GD_CON
	VALUES (5242888,
	5242882,
	5242882,
	0);
INSERT INTO GD_CTXT
	VALUES (5242888,
	0,
	0,
	0,
	0,
	0,
	0,
	2142,
	1372,
	2202,
	1394,
	80,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5242889,
	5242888,
	2080,
	1312,
	2128,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (5242890,
	5242888,
	2128,
	1312,
	2128,
	1456,
	5242889);
INSERT INTO GD_LS
	VALUES (5242891,
	5242888,
	2128,
	1456,
	2080,
	1456,
	5242890);
INSERT INTO GD_GE
	VALUES (5242893,
	5242881,
	5242884,
	42);
INSERT INTO GD_CON
	VALUES (5242893,
	5242883,
	5242892,
	0);
INSERT INTO GD_CTXT
	VALUES (5242893,
	0,
	0,
	0,
	0,
	0,
	0,
	1965,
	1799,
	2025,
	1821,
	95,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5242894,
	5242893,
	1936,
	1760,
	1936,
	1856,
	0);
INSERT INTO O_OBJ
	VALUES (3670019,
	'Bogus Class Two',
	202,
	'BC2',
	'',
	3670023);
INSERT INTO O_NBATTR
	VALUES (3670021,
	3670019);
INSERT INTO O_BATTR
	VALUES (3670021,
	3670019);
INSERT INTO O_ATTR
	VALUES (3670021,
	3670019,
	0,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO SM_ISM
	VALUES (5767179,
	3670019);
INSERT INTO SM_SM
	VALUES (5767179,
	'',
	11);
INSERT INTO SM_MOORE
	VALUES (5767179);
INSERT INTO SM_EVTDI
	VALUES (5767169,
	5767179,
	'create_date',
	'',
	524302);
INSERT INTO SM_EVTDI
	VALUES (5767170,
	5767179,
	'current_clock',
	'',
	524303);
INSERT INTO SM_EVTDI
	VALUES (5767171,
	5767179,
	'current_date',
	'',
	524302);
INSERT INTO SM_EVTDI
	VALUES (5767172,
	5767179,
	'get_day',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (5767173,
	5767179,
	'get_hour',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (5767174,
	5767179,
	'get_minute',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (5767175,
	5767179,
	'get_month',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (5767176,
	5767179,
	'get_second',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (5767177,
	5767179,
	'get_year',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (5767178,
	5767179,
	'timer_time_add',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (5767179,
	5767179,
	'timer_cancel',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (5767180,
	5767179,
	'timer_remaining_time',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (5767181,
	5767179,
	'timer_reset_time',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (5767182,
	5767179,
	'timer_start',
	'',
	524304);
INSERT INTO SM_EVTDI
	VALUES (5767183,
	5767179,
	'timer_start_recurring',
	'',
	524304);
INSERT INTO SM_SUPDT
	VALUES (5767169,
	5767179,
	0);
INSERT INTO SM_SDI
	VALUES (5767175,
	5767169,
	5767179);
INSERT INTO SM_SDI
	VALUES (5767182,
	5767169,
	5767179);
INSERT INTO SM_SDI
	VALUES (5767173,
	5767169,
	5767179);
INSERT INTO SM_SDI
	VALUES (5767171,
	5767169,
	5767179);
INSERT INTO SM_SDI
	VALUES (5767181,
	5767169,
	5767179);
INSERT INTO SM_SDI
	VALUES (5767179,
	5767169,
	5767179);
INSERT INTO SM_SDI
	VALUES (5767178,
	5767169,
	5767179);
INSERT INTO SM_SDI
	VALUES (5767172,
	5767169,
	5767179);
INSERT INTO SM_SDI
	VALUES (5767177,
	5767169,
	5767179);
INSERT INTO SM_SDI
	VALUES (5767180,
	5767169,
	5767179);
INSERT INTO SM_SDI
	VALUES (5767169,
	5767169,
	5767179);
INSERT INTO SM_SDI
	VALUES (5767183,
	5767169,
	5767179);
INSERT INTO SM_SDI
	VALUES (5767174,
	5767169,
	5767179);
INSERT INTO SM_SDI
	VALUES (5767176,
	5767169,
	5767179);
INSERT INTO SM_SDI
	VALUES (5767170,
	5767169,
	5767179);
INSERT INTO SM_STATE
	VALUES (5767169,
	5767179,
	5767169,
	'Bogus Two',
	2,
	0);
INSERT INTO SM_LEVT
	VALUES (5767169,
	5767179,
	5767169);
INSERT INTO SM_SEVT
	VALUES (5767169,
	5767179,
	5767169);
INSERT INTO SM_EVT
	VALUES (5767169,
	5767179,
	5767169,
	1,
	'',
	0,
	'',
	'BC21',
	'');
INSERT INTO SM_EIGN
	VALUES (5767169,
	5767169,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767169,
	5767169,
	5767179,
	5767169);
INSERT INTO SM_CRTXN
	VALUES (5767169,
	5767179,
	5767169,
	5767169);
INSERT INTO SM_TXN
	VALUES (5767169,
	5767179,
	5767169,
	5767169);
INSERT INTO SM_MOAH
	VALUES (5767169,
	5767179,
	5767169);
INSERT INTO SM_AH
	VALUES (5767169,
	5767179);
INSERT INTO SM_ACT
	VALUES (5767169,
	5767179,
	1,
	'//////////////////////////////////////////////
// Test incoming rcvd_evt.
//////////////////////////////////////////////
if ( rcvd_evt.create_date == rcvd_evt.current_date )
  LOG::LogSuccess(message:"Success in creation event parameter for Bogus Two - rcvd_evt.create_date is equal to rcvd_evt.current_date.");
else
  LOG::LogFailure(message:"Failure in creation event parameter for Bogus Two - rcvd_evt.create_date is not equal to rcvd_evt.current_date.");
end if;

if ( ( rcvd_evt.get_second + rcvd_evt.get_minute + rcvd_evt.get_hour + rcvd_evt.get_day + rcvd_evt.get_month + rcvd_evt.get_year ) == 6 )
  LOG::LogSuccess(message:"Success in creation event parameter for Bogus Two - Sum of rcvd_evt.get_* is equal to six(6).");
else
  LOG::LogFailure(message:"Failure in creation event parameter for Bogus Two - Sum of rcvd_evt.get_* is not equal to six(6).");
end if;

if ( rcvd_evt.timer_time_add )
  LOG::LogSuccess(message:"Success in creation event parameter TestTIM - rcvd_evt.timer_add_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in creation event parameter TestTIM - rcvd_evt.timer_add_time is not TRUE.");
end if;

if ( rcvd_evt.timer_reset_time )
  LOG::LogSuccess(message:"Success in creation event parameter TestTIM - rcvd_evt.timer_reset_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in creation event parameter TestTIM - rcvd_evt.timer_reset_time is not TRUE.");
end if;

if ( rcvd_evt.timer_cancel )
  LOG::LogSuccess(message:"Success in creation event parameter TestTIM - rcvd_evt.timer_cancel_time is TRUE.");
else
  LOG::LogFailure(message:"Failure in creation event parameter TestTIM - rcvd_evt.timer_cancel_time is not TRUE.");
end if;

if ( rcvd_evt.timer_remaining_time >= 500000 )
  LOG::LogSuccess(message:"Success in creation event parameter TestTIM - rcvd_evt.timer_remaining_time is >= 500000.");
else
  LOG::LogFailure(message:"Failure in creation event parameter TestTIM - rcvd_evt.timer_remaining_time is < 500000.");
end if;

if ( TIM::timer_cancel( timer_inst_ref: rcvd_evt.timer_start ) == TRUE )
  LOG::LogSuccess(message:"Success in creation event parameter for Bogus Two - rcvd_evt.timer_start was cancelled.");
else
  LOG::LogFailure(message:"Failure in creation event parameter for Bogus Two - rcvd_evt.timer_start was not cancelled.");
end if;

if ( TIM::timer_cancel( timer_inst_ref: rcvd_evt.timer_start_recurring ) == TRUE )
  LOG::LogSuccess(message:"Success in creation event parameter for Bogus Two - rcvd_evt.timer_start_recurring was cancelled.");
else
  LOG::LogFailure(message:"Failure in creation event parameter for Bogus Two - rcvd_evt.timer_start_recurring was not cancelled.");
end if;

//////////////////////////////////////////////
// TIM::current_clock
//////////////////////////////////////////////
clock = TIM::current_clock();
if ( rcvd_evt.current_clock <= clock )
  LOG::LogSuccess(message:"Success in creation event parameter for Bogus Two - rcvd_evt.current_clock is less than or equal to clock.");
else
  LOG::LogFailure(message:"Failure in creation event parameter for Bogus Two - rcvd_evt.current_clock is greater than clock.");
end if;

select any drv from instances of TMD;
generate TMD4 to drv;
',
	'');
INSERT INTO GD_MD
	VALUES (5767169,
	8,
	5767179,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1527,
	4059,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (5767170,
	5767169,
	5767169,
	41);
INSERT INTO GD_SHP
	VALUES (5767170,
	1776,
	1632,
	2048,
	1792);
INSERT INTO GD_GE
	VALUES (5767171,
	5767169,
	5767169,
	42);
INSERT INTO GD_CON
	VALUES (5767171,
	5767170,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (5767171,
	0,
	0,
	0,
	0,
	0,
	0,
	1720,
	1459,
	2136,
	1535,
	222,
	-105,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5767172,
	5767171,
	1920,
	1632,
	1920,
	1568,
	0);
INSERT INTO O_OBJ
	VALUES (3670020,
	'Bogus Class Three',
	203,
	'BC3',
	'',
	3670023);
INSERT INTO O_NBATTR
	VALUES (3670022,
	3670020);
INSERT INTO O_BATTR
	VALUES (3670022,
	3670020);
INSERT INTO O_ATTR
	VALUES (3670022,
	3670020,
	0,
	'type',
	'',
	'',
	'type',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3670023,
	3670020);
INSERT INTO O_BATTR
	VALUES (3670023,
	3670020);
INSERT INTO O_ATTR
	VALUES (3670023,
	3670020,
	3670022,
	'timer',
	'',
	'',
	'timer',
	0,
	524304);
INSERT INTO O_ID
	VALUES (0,
	3670020);
INSERT INTO O_OIDA
	VALUES (3670022,
	3670020,
	0);
INSERT INTO GD_MD
	VALUES (3670017,
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
	VALUES (3670020,
	3670017,
	3670017,
	21);
INSERT INTO GD_SHP
	VALUES (3670020,
	1680,
	1248,
	1872,
	1392);
INSERT INTO GD_GE
	VALUES (3670021,
	3670017,
	3670018,
	21);
INSERT INTO GD_SHP
	VALUES (3670021,
	1888,
	1248,
	2400,
	1776);
INSERT INTO GD_GE
	VALUES (3670022,
	3670017,
	3670019,
	21);
INSERT INTO GD_SHP
	VALUES (3670022,
	1680,
	1440,
	1872,
	1584);
INSERT INTO GD_GE
	VALUES (3670031,
	3670017,
	3670020,
	21);
INSERT INTO GD_SHP
	VALUES (3670031,
	1680,
	1632,
	1872,
	1776);
INSERT INTO GD_MD
	VALUES (3670018,
	6,
	3670023,
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
	VALUES (3670023,
	3670018,
	3670017,
	21);
INSERT INTO GD_SHP
	VALUES (3670023,
	1680,
	1248,
	1872,
	1312);
INSERT INTO GD_GE
	VALUES (3670024,
	3670018,
	3670018,
	21);
INSERT INTO GD_SHP
	VALUES (3670024,
	1680,
	1456,
	1872,
	1520);
INSERT INTO GD_GE
	VALUES (3670025,
	3670018,
	5242890,
	40);
INSERT INTO GD_SHP
	VALUES (3670025,
	2048,
	1340,
	2240,
	1404);
INSERT INTO GD_GE
	VALUES (3670026,
	3670018,
	3670019,
	21);
INSERT INTO GD_SHP
	VALUES (3670026,
	1696,
	1264,
	1888,
	1328);
INSERT INTO GD_MD
	VALUES (3670019,
	7,
	3670023,
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
	VALUES (3670027,
	3670019,
	3670017,
	21);
INSERT INTO GD_SHP
	VALUES (3670027,
	1680,
	1248,
	1872,
	1312);
INSERT INTO GD_GE
	VALUES (3670028,
	3670019,
	3670018,
	21);
INSERT INTO GD_SHP
	VALUES (3670028,
	1680,
	1456,
	1872,
	1520);
INSERT INTO GD_GE
	VALUES (3670029,
	3670019,
	5242890,
	40);
INSERT INTO GD_SHP
	VALUES (3670029,
	2048,
	1340,
	2240,
	1404);
INSERT INTO GD_GE
	VALUES (3670030,
	3670019,
	3670019,
	21);
INSERT INTO GD_SHP
	VALUES (3670030,
	1696,
	1264,
	1888,
	1328);
INSERT INTO GD_GE
	VALUES (3670032,
	3670019,
	3670020,
	21);
INSERT INTO GD_SHP
	VALUES (3670032,
	1680,
	1632,
	1872,
	1696);
