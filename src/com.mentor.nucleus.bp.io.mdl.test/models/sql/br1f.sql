-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (372605,
	'br1f',
	'This test deals with invoking Bridges using every data type in the following ways:
    - Accessed by Realized code
        - 10 threads are spawned which access the bridges sychronously
    - Accessing Realized code
    - Wired to Functions in br2f

This test also contains the following:
    - Colors myType as pointer
    - Accesses Realized Transformers
    - Enumeration data type Color is "Old Style" enumeration
    - Functions are wired for Realized access and are called from Realized code',
	1,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	372605,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	372605,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	372605,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	372605,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	372605,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	372605,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	372605,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	372605,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	372605,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	372605,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	372605,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	372605,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	372605,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	372605,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	372605,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	372605,
	'inst_ref<Timer>',
	'');
INSERT INTO S_UDT
	VALUES (524305,
	524293,
	0);
INSERT INTO S_DT
	VALUES (524305,
	372605,
	'color',
	'Enumeration:TRUE
Enumerator0:Blue
Enumerator1:Red
Enumerator2:Green');
INSERT INTO S_UDT
	VALUES (524306,
	524300,
	0);
INSERT INTO S_DT
	VALUES (524306,
	372605,
	'user',
	'');
INSERT INTO S_SYNC
	VALUES (524289,
	372605,
	'ds_get_b_val',
	'',
	'return OOADS::get_b_val();
',
	524290,
	1);
INSERT INTO S_SYNC
	VALUES (524290,
	372605,
	'ds_get_d_val',
	'',
	'return OOADS::get_d_val();
',
	524302,
	1);
INSERT INTO S_SYNC
	VALUES (524291,
	372605,
	'ds_get_i_val',
	'',
	'return OOADS::get_i_val();
',
	524291,
	1);
INSERT INTO S_SYNC
	VALUES (524292,
	372605,
	'ds_get_r_val',
	'',
	'return OOADS::get_r_val();
',
	524292,
	1);
INSERT INTO S_SYNC
	VALUES (524293,
	372605,
	'ds_get_s_val',
	'',
	'return OOADS::get_s_val();
',
	524293,
	1);
INSERT INTO S_SYNC
	VALUES (524294,
	372605,
	'ds_get_t_val',
	'',
	'return OOADS::get_t_val();
',
	524303,
	1);
INSERT INTO S_SYNC
	VALUES (524295,
	372605,
	'b1_b_parm_ret_b',
	'',
	'LOG::LogInfo(message:"br1 bridge object one - b_parm_ret_b") ;

t_b = BRONE::send_b_return_b ( b:param.b );

if (t_b == param.b)
  LOG::LogSuccess(message:"br1 bridge object one - b_parm_ret_b") ;
else
  LOG::LogFailure(message:"br1 bridge object one - b_parm_ret_b") ;
end if;


// generate an event to instance
select any ao from instances of AO;
generate AO1:''b''(b:t_b) to ao;

// generate an event to assigner
generate AO_A11:''b''(b:t_b) to AO Assigner;

//send received bool value back as return value
return t_b;

',
	524290,
	1);
INSERT INTO S_SPARM
	VALUES (524289,
	524295,
	'b',
	524290,
	0);
INSERT INTO S_SYNC
	VALUES (524296,
	372605,
	'b1_d_parm_ret_d',
	'',
	'LOG::LogInfo(message:"br1 bridge object one - d_parm_ret_d") ;

t_d = BRONE::send_d_return_d ( d:param.d );

if (t_d == param.d)
  LOG::LogSuccess(message:"br1 bridge object one - d_parm_ret_d") ;
else
  LOG::LogFailure(message:"br1 bridge object one - d_parm_ret_d") ;
end if;


// generate an event to instance
select any ao from instances of AO;
generate AO4:''d''(d:t_d) to ao;

// generate an event to assigner
generate AO_A9:''d''(d:t_d) to AO Assigner;

//send received bool value back as return value
return t_d;

',
	524302,
	1);
INSERT INTO S_SPARM
	VALUES (524290,
	524296,
	'd',
	524302,
	0);
INSERT INTO S_SYNC
	VALUES (524297,
	372605,
	'b1_e_parm_ret_e',
	'',
	'LOG::LogInfo(message:"br1 bridge object one - e_parm_ret_e") ;

t_e = BRONE::send_e_return_e ( e:param.e );

if (t_e == param.e)
  LOG::LogSuccess(message:"br1 bridge object one - e_parm_ret_e") ;
else
  LOG::LogFailure(message:"br1 bridge object one - e_parm_ret_e") ;
end if;


// generate an event to instance
select any ao from instances of AO;
generate AO9:''e''(e:t_e) to ao;

// generate an event to assigner
generate AO_A6:''e''(e:t_e) to AO Assigner;

//send received bool value back as return value
return t_e;

',
	524305,
	1);
INSERT INTO S_SPARM
	VALUES (524291,
	524297,
	'e',
	524305,
	0);
INSERT INTO S_SYNC
	VALUES (524298,
	372605,
	'b1_i_parm_ret_i',
	'',
	'LOG::LogInfo(message:"br1 bridge object one - i_parm_ret_i") ;

t_i = BRONE::send_i_return_i ( i:param.i );

if (t_i == param.i)
  LOG::LogSuccess(message:"br1 bridge object one - i_parm_ret_i") ;
else
  LOG::LogFailure(message:"br1 bridge object one - i_parm_ret_i") ;
end if;

// generate an event to instance
select any ao from instances of AO;
generate AO2:''i''(i:t_i) to ao;

// generate an event to assigner
generate AO_A14:''i''(i:t_i) to AO Assigner;

//send received bool value back as return value
return t_i;

',
	524291,
	1);
INSERT INTO S_SPARM
	VALUES (524292,
	524298,
	'i',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524299,
	372605,
	'b1_no_parm_ret_void',
	'',
	'Transform BRONE::send_void_return_void();
LOG::LogInfo(message:"br1 BRONE::no_parm_ret_void");',
	524289,
	1);
INSERT INTO S_SYNC
	VALUES (524300,
	372605,
	'b1_r_parm_ret_r',
	'',
	'LOG::LogInfo(message:"br1 bridge object one - r_parm_ret_r") ;

t_r = BRONE::send_r_return_r ( r:param.r );

if (t_r == param.r)
  LOG::LogSuccess(message:"br1 bridge object one - r_parm_ret_r") ;
else
  LOG::LogFailure(message:"br1 bridge object one - r_parm_ret_r") ;
end if;

// generate an event to instance
select any ao from instances of AO;
generate AO3:''r''(r:t_r) to ao;

// generate an event to assigner
generate AO_A10:''r''(r:t_r) to AO Assigner;

//send received bool value back as return value
return t_r;

',
	524292,
	1);
INSERT INTO S_SPARM
	VALUES (524293,
	524300,
	'r',
	524292,
	0);
INSERT INTO S_SYNC
	VALUES (524301,
	372605,
	'b1_shutdown',
	'',
	'select any dr from instances of BTD;
generate BTD4 to dr;',
	524289,
	1);
INSERT INTO S_SYNC
	VALUES (524302,
	372605,
	'b1_u_parm_ret_u',
	'',
	'LOG::LogInfo(message:"br1 bridge object one - u_parm_ret_u") ;

t_u = BRONE::send_u_return_u ( u:param.u);

if (t_u == param.u)
  LOG::LogSuccess(message:"br1 bridge object one - u_parm_ret_u") ;
else
  LOG::LogFailure(message:"br1 bridge object one - u_parm_ret_u") ;
end if;

// generate an event to instance
select any ao from instances of AO;
generate AO7:''u''(u:t_u) to ao;

// generate an event to assigner
generate AO_A12:''u''(u:t_u) to AO Assigner;

//send received bool value back as return value
return t_u;

',
	524294,
	1);
INSERT INTO S_SPARM
	VALUES (524294,
	524302,
	'u',
	524294,
	0);
INSERT INTO S_SYNC
	VALUES (524303,
	372605,
	'b1_s_parm_ret_s',
	'',
	'LOG::LogInfo(message:"br1 bridge object one - s_parm_ret_s") ;

t_s = BRONE::send_s_return_s ( s:param.s );

if (t_s == param.s)
  LOG::LogSuccess(message:"br1 bridge object one -s_parm_ret_s") ;
else
  LOG::LogFailure(message:"br1 bridge object one - s_parm_ret_s") ;
end if;

// generate an event to instance
select any ao from instances of AO;
generate AO10:''s''(s:t_s) to ao;

// generate an event to assigner
generate AO_A8:''s''(s:t_s) to AO Assigner;

//send received bool value back as return value
return t_s;

',
	524293,
	1);
INSERT INTO S_SPARM
	VALUES (524295,
	524303,
	's',
	524293,
	0);
INSERT INTO S_SYNC
	VALUES (524304,
	372605,
	'b1_t_parm_ret_t',
	'',
	'LOG::LogInfo(message:"br1 bridge object one - t_parm_ret_t") ;

t_t = BRONE::send_t_return_t ( t:param.t );

if (t_t == param.t)
  LOG::LogSuccess(message:"br1 bridge object one -t_parm_ret_t") ;
else
  LOG::LogFailure(message:"br1 bridge object one - t_parm_ret_t") ;
end if;

// generate an event to instance
select any ao from instances of AO;
generate AO6:''t''(t:t_t) to ao;

// generate an event to assigner
generate AO_A13:''t''(t:t_t) to AO Assigner;

//send received bool value back as return value
return  t_t;

',
	524303,
	1);
INSERT INTO S_SPARM
	VALUES (524296,
	524304,
	't',
	524303,
	0);
INSERT INTO S_SYNC
	VALUES (524305,
	372605,
	'b1_us_parm_ret_us',
	'',
	'LOG::LogInfo(message:"br1 bridge object one - us_parm_ret_us") ;

t_us = BRONE::send_us_return_us ( us:param.us);

if (t_us == param.us)
  LOG::LogSuccess(message:"br1 bridge object one - us_parm_ret_us") ;
else
  LOG::LogFailure(message:"br1 bridge object one - us_parm_ret_us") ;
end if;

// generate an event to instance
select any ao from instances of AO;
generate AO8:''us''(us:t_us) to ao;

// generate an event to assigner
generate AO_A7:''us''(us:t_us) to AO Assigner;

//send received bool value back as return value
return t_us;

',
	524306,
	1);
INSERT INTO S_SPARM
	VALUES (524297,
	524305,
	'us',
	524306,
	0);
INSERT INTO S_SYNC
	VALUES (524306,
	372605,
	'ee_done',
	'',
	'select any driver from instances of BTD;

driver.done_ee = driver.done_ee + 1;

if ( driver.done_ee == driver.num_ee )
  generate BTD4 to driver;
end if;',
	524289,
	1);
INSERT INTO S_EE
	VALUES (524289,
	'Logging ',
	'',
	'LOG',
	372605);
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
	VALUES (524298,
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
	VALUES (524299,
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
	VALUES (524300,
	524291,
	'message',
	524293,
	0);
INSERT INTO S_EE
	VALUES (524290,
	'Time',
	'',
	'TIM',
	372605);
INSERT INTO S_BRG
	VALUES (524292,
	524290,
	'current_date',
	'',
	1,
	524302,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524293,
	524290,
	'create_date',
	'',
	1,
	524302,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524301,
	524293,
	'second',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524302,
	524293,
	'minute',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524303,
	524293,
	'hour',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524304,
	524293,
	'day',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524305,
	524293,
	'month',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524306,
	524293,
	'year',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524294,
	524290,
	'get_second',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524307,
	524294,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524295,
	524290,
	'get_minute',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524308,
	524295,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524296,
	524290,
	'get_hour',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524309,
	524296,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524297,
	524290,
	'get_day',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524310,
	524297,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524298,
	524290,
	'get_month',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524311,
	524298,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524299,
	524290,
	'get_year',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524312,
	524299,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524300,
	524290,
	'current_clock',
	'',
	1,
	524303,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524301,
	524290,
	'timer_start',
	'',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524313,
	524301,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524314,
	524301,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524302,
	524290,
	'timer_start_recurring',
	'',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524315,
	524302,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524316,
	524302,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524303,
	524290,
	'timer_remaining_time',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524317,
	524303,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BRG
	VALUES (524304,
	524290,
	'timer_reset_time',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524318,
	524304,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524319,
	524304,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524305,
	524290,
	'timer_add_time',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524320,
	524305,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524321,
	524305,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524306,
	524290,
	'timer_cancel',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524322,
	524306,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_EE
	VALUES (524291,
	'Realized Domain Accessing OOA',
	'This EE is used to test a non OOA realized domain that accesses an OOA domain.
',
	'RDONE',
	372605);
INSERT INTO S_BRG
	VALUES (524307,
	524291,
	'start_test',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524323,
	524307,
	'num_ee',
	524291,
	0);
INSERT INTO S_EEEDI
	VALUES (524289,
	524291,
	'b',
	'',
	524290);
INSERT INTO S_EEEDI
	VALUES (524290,
	524291,
	'i',
	'',
	524291);
INSERT INTO S_EEEDI
	VALUES (524291,
	524291,
	'r',
	'',
	524292);
INSERT INTO S_EEEDI
	VALUES (524292,
	524291,
	'd',
	'',
	524302);
INSERT INTO S_EEEDI
	VALUES (524293,
	524291,
	's',
	'',
	524293);
INSERT INTO S_EEEDI
	VALUES (524294,
	524291,
	't',
	'',
	524303);
INSERT INTO S_EEEDI
	VALUES (524295,
	524291,
	'u',
	'',
	524294);
INSERT INTO S_EEEDI
	VALUES (524296,
	524291,
	'user',
	'',
	524291);
INSERT INTO S_EEEVT
	VALUES (524289,
	524291,
	1,
	'Event to Non OOA Client Ext Task',
	0,
	'',
	'RDONE1',
	'');
INSERT INTO S_EEEDT
	VALUES (524291,
	524289,
	524289);
INSERT INTO S_EEEDT
	VALUES (524291,
	524289,
	524296);
INSERT INTO S_EEEDT
	VALUES (524291,
	524289,
	524290);
INSERT INTO S_EEEDT
	VALUES (524291,
	524289,
	524295);
INSERT INTO S_EEEDT
	VALUES (524291,
	524289,
	524293);
INSERT INTO S_EEEDT
	VALUES (524291,
	524289,
	524291);
INSERT INTO S_EEEDT
	VALUES (524291,
	524289,
	524294);
INSERT INTO S_EEEDT
	VALUES (524291,
	524289,
	524292);
INSERT INTO S_EE
	VALUES (524292,
	'Realized Domain Accessed by OOA',
	'This EE tests a realized domain that is accessed by an OOA domain.',
	'RDTWO',
	372605);
INSERT INTO S_BRG
	VALUES (524308,
	524292,
	'no_parm_ret_void',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BRG
	VALUES (524309,
	524292,
	'i_parm_ret_i',
	'',
	0,
	524291,
	'return param.i;',
	1);
INSERT INTO S_BPARM
	VALUES (524324,
	524309,
	'i',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524310,
	524292,
	'b_parm_ret_b',
	'',
	0,
	524290,
	'return param.b;',
	1);
INSERT INTO S_BPARM
	VALUES (524325,
	524310,
	'b',
	524290,
	0);
INSERT INTO S_BRG
	VALUES (524311,
	524292,
	'r_parm_ret_r',
	'',
	0,
	524292,
	'return param.r;',
	1);
INSERT INTO S_BPARM
	VALUES (524326,
	524311,
	'r',
	524292,
	0);
INSERT INTO S_BRG
	VALUES (524312,
	524292,
	's_parm_ret_s',
	'',
	0,
	524293,
	'return param.s;',
	1);
INSERT INTO S_BPARM
	VALUES (524327,
	524312,
	's',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524313,
	524292,
	'u_parm_ret_u',
	'',
	0,
	524294,
	'return param.u;',
	1);
INSERT INTO S_BPARM
	VALUES (524328,
	524313,
	'u',
	524294,
	0);
INSERT INTO S_BRG
	VALUES (524314,
	524292,
	'd_parm_ret_d',
	'',
	0,
	524302,
	'return param.d;',
	1);
INSERT INTO S_BPARM
	VALUES (524329,
	524314,
	'd',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524315,
	524292,
	't_parm_ret_t',
	'',
	0,
	524303,
	'return param.t;',
	1);
INSERT INTO S_BPARM
	VALUES (524330,
	524315,
	't',
	524303,
	0);
INSERT INTO S_BRG
	VALUES (524316,
	524292,
	'us_parm_ret_us',
	'',
	0,
	524306,
	'return param.us;',
	1);
INSERT INTO S_BPARM
	VALUES (524331,
	524316,
	'us',
	524306,
	0);
INSERT INTO S_BRG
	VALUES (524317,
	524292,
	'e_parm_ret_e',
	'',
	0,
	524305,
	'return param.e;',
	1);
INSERT INTO S_BPARM
	VALUES (524332,
	524317,
	'e',
	524305,
	0);
INSERT INTO S_EEEDI
	VALUES (524297,
	524292,
	'i',
	'',
	524291);
INSERT INTO S_EEEDI
	VALUES (524298,
	524292,
	'b',
	'',
	524290);
INSERT INTO S_EEEDI
	VALUES (524299,
	524292,
	'r',
	'',
	524292);
INSERT INTO S_EEEDI
	VALUES (524300,
	524292,
	'd',
	'',
	524302);
INSERT INTO S_EEEDI
	VALUES (524301,
	524292,
	's',
	'',
	524293);
INSERT INTO S_EEEDI
	VALUES (524302,
	524292,
	't',
	'',
	524303);
INSERT INTO S_EEEDI
	VALUES (524303,
	524292,
	'u',
	'',
	524294);
INSERT INTO S_EEEVT
	VALUES (524290,
	524292,
	1,
	'Event to Non OOA Server With Data',
	0,
	'',
	'RDTWO1',
	'');
INSERT INTO S_EEEDT
	VALUES (524292,
	524290,
	524301);
INSERT INTO S_EEEDT
	VALUES (524292,
	524290,
	524303);
INSERT INTO S_EEEDT
	VALUES (524292,
	524290,
	524302);
INSERT INTO S_EEEDT
	VALUES (524292,
	524290,
	524299);
INSERT INTO S_EEEDT
	VALUES (524292,
	524290,
	524297);
INSERT INTO S_EEEDT
	VALUES (524292,
	524290,
	524300);
INSERT INTO S_EEEDT
	VALUES (524292,
	524290,
	524298);
INSERT INTO S_EEEVT
	VALUES (524291,
	524292,
	2,
	'Event to Non OOA Server Without Data',
	0,
	'',
	'RDTWO2',
	'');
INSERT INTO S_EE
	VALUES (524293,
	'br2 ooa domain',
	'',
	'br2',
	372605);
INSERT INTO S_BRG
	VALUES (524318,
	524293,
	'no_parm_ret_void',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BRG
	VALUES (524319,
	524293,
	'b_parm_ret_b',
	'',
	0,
	524290,
	'return param.b;',
	1);
INSERT INTO S_BPARM
	VALUES (524333,
	524319,
	'b',
	524290,
	0);
INSERT INTO S_BRG
	VALUES (524320,
	524293,
	'i_parm_ret_i',
	'',
	0,
	524291,
	'return param.i;',
	1);
INSERT INTO S_BPARM
	VALUES (524334,
	524320,
	'i',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524321,
	524293,
	'r_parm_ret_r',
	'',
	0,
	524292,
	'return param.r;',
	1);
INSERT INTO S_BPARM
	VALUES (524335,
	524321,
	'r',
	524292,
	0);
INSERT INTO S_BRG
	VALUES (524322,
	524293,
	's_parm_ret_s',
	'',
	0,
	524293,
	'return param.s;',
	1);
INSERT INTO S_BPARM
	VALUES (524336,
	524322,
	's',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524323,
	524293,
	'u_parm_ret_u',
	'',
	0,
	524294,
	'return param.u;',
	1);
INSERT INTO S_BPARM
	VALUES (524337,
	524323,
	'u',
	524294,
	0);
INSERT INTO S_BRG
	VALUES (524324,
	524293,
	'd_parm_ret_d',
	'',
	0,
	524302,
	'return param.d;',
	1);
INSERT INTO S_BPARM
	VALUES (524338,
	524324,
	'd',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524325,
	524293,
	't_parm_ret_t',
	'',
	0,
	524303,
	'return param.t;',
	1);
INSERT INTO S_BPARM
	VALUES (524339,
	524325,
	't',
	524303,
	0);
INSERT INTO S_BRG
	VALUES (524326,
	524293,
	'e_parm_ret_e',
	'',
	0,
	524305,
	'return param.e;',
	1);
INSERT INTO S_BPARM
	VALUES (524340,
	524326,
	'e',
	524305,
	0);
INSERT INTO S_BRG
	VALUES (524327,
	524293,
	'i_parm_ret_void',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524341,
	524327,
	'i',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524328,
	524293,
	'us_parm_ret_us',
	'',
	0,
	524306,
	'return param.us;',
	1);
INSERT INTO S_BPARM
	VALUES (524342,
	524328,
	'us',
	524306,
	0);
INSERT INTO S_EEEDI
	VALUES (524304,
	524293,
	'i',
	'',
	524291);
INSERT INTO S_EEEDI
	VALUES (524305,
	524293,
	'b',
	'',
	524290);
INSERT INTO S_EEEDI
	VALUES (524306,
	524293,
	'r',
	'',
	524292);
INSERT INTO S_EEEDI
	VALUES (524307,
	524293,
	's',
	'',
	524293);
INSERT INTO S_EEEDI
	VALUES (524308,
	524293,
	't',
	'',
	524303);
INSERT INTO S_EEEDI
	VALUES (524309,
	524293,
	'u',
	'',
	524294);
INSERT INTO S_EEEDI
	VALUES (524310,
	524293,
	'd',
	'',
	524302);
INSERT INTO S_EEEVT
	VALUES (524292,
	524293,
	1,
	'Event to N OOA Server With Data No Reply',
	0,
	'',
	'br21',
	'');
INSERT INTO S_EEEDT
	VALUES (524293,
	524292,
	524304);
INSERT INTO S_EEEDT
	VALUES (524293,
	524292,
	524310);
INSERT INTO S_EEEDT
	VALUES (524293,
	524292,
	524308);
INSERT INTO S_EEEDT
	VALUES (524293,
	524292,
	524309);
INSERT INTO S_EEEDT
	VALUES (524293,
	524292,
	524307);
INSERT INTO S_EEEDT
	VALUES (524293,
	524292,
	524306);
INSERT INTO S_EEEDT
	VALUES (524293,
	524292,
	524305);
INSERT INTO S_EEEVT
	VALUES (524293,
	524293,
	2,
	'Event to N OOA Server Without Data No Reply',
	0,
	'',
	'br22',
	'');
INSERT INTO S_EEEVT
	VALUES (524294,
	524293,
	3,
	'Event to N OOA Server With Data Reply',
	0,
	'',
	'br23',
	'');
INSERT INTO S_EEEDT
	VALUES (524293,
	524294,
	524309);
INSERT INTO S_EEEDT
	VALUES (524293,
	524294,
	524307);
INSERT INTO S_EEEDT
	VALUES (524293,
	524294,
	524308);
INSERT INTO S_EEEDT
	VALUES (524293,
	524294,
	524306);
INSERT INTO S_EEEDT
	VALUES (524293,
	524294,
	524310);
INSERT INTO S_EEEDT
	VALUES (524293,
	524294,
	524304);
INSERT INTO S_EEEDT
	VALUES (524293,
	524294,
	524305);
INSERT INTO S_EEEVT
	VALUES (524295,
	524293,
	4,
	'Event to N OOA Server Without Data Reply',
	0,
	'',
	'br24',
	'');
INSERT INTO S_EE
	VALUES (524294,
	'Architecture',
	'',
	'ARCH',
	372605);
INSERT INTO S_BRG
	VALUES (524329,
	524294,
	'shutdown',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_EE
	VALUES (524295,
	'OOA Data Server',
	'',
	'OOADS',
	372605);
INSERT INTO S_BRG
	VALUES (524330,
	524295,
	'get_i_val',
	'',
	0,
	524291,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524331,
	524295,
	'get_b_val',
	'',
	0,
	524290,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524332,
	524295,
	'get_d_val',
	'',
	0,
	524302,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524333,
	524295,
	'get_r_val',
	'',
	0,
	524292,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524334,
	524295,
	'get_s_val',
	'',
	0,
	524293,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524335,
	524295,
	'get_t_val',
	'',
	0,
	524303,
	'',
	0);
INSERT INTO GD_MD
	VALUES (524289,
	1,
	372605,
	1,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	4148,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (524293,
	524289,
	524289,
	12);
INSERT INTO GD_SHP
	VALUES (524293,
	1680,
	1712,
	1840,
	1808);
INSERT INTO GD_GE
	VALUES (524294,
	524289,
	524290,
	12);
INSERT INTO GD_SHP
	VALUES (524294,
	1856,
	1712,
	2016,
	1808);
INSERT INTO GD_GE
	VALUES (524295,
	524289,
	524291,
	12);
INSERT INTO GD_SHP
	VALUES (524295,
	1696,
	1296,
	1920,
	1424);
INSERT INTO GD_GE
	VALUES (524296,
	524289,
	524292,
	12);
INSERT INTO GD_SHP
	VALUES (524296,
	1712,
	1568,
	1952,
	1680);
INSERT INTO GD_GE
	VALUES (524297,
	524289,
	524293,
	12);
INSERT INTO GD_SHP
	VALUES (524297,
	2048,
	1584,
	2224,
	1680);
INSERT INTO GD_GE
	VALUES (524298,
	524289,
	524294,
	12);
INSERT INTO GD_SHP
	VALUES (524298,
	2048,
	1712,
	2240,
	1808);
INSERT INTO GD_GE
	VALUES (524299,
	524289,
	524295,
	12);
INSERT INTO GD_SHP
	VALUES (524299,
	2016,
	1296,
	2208,
	1408);
INSERT INTO GD_GE
	VALUES (524335,
	524289,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524335,
	1712,
	1456,
	1904,
	1552);
INSERT INTO GD_GE
	VALUES (524336,
	524289,
	3145734,
	11);
INSERT INTO GD_SHP
	VALUES (524336,
	1936,
	1456,
	2112,
	1552);
INSERT INTO GD_MD
	VALUES (524290,
	2,
	372605,
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
	VALUES (524337,
	524290,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524337,
	1744,
	1408,
	1904,
	1504);
INSERT INTO GD_GE
	VALUES (524338,
	524290,
	3145734,
	11);
INSERT INTO GD_SHP
	VALUES (524338,
	1936,
	1456,
	2112,
	1552);
INSERT INTO GD_MD
	VALUES (524291,
	3,
	372605,
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
	VALUES (524339,
	524291,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524339,
	1600,
	1344,
	1760,
	1440);
INSERT INTO GD_GE
	VALUES (524340,
	524291,
	3145734,
	11);
INSERT INTO GD_SHP
	VALUES (524340,
	1936,
	1456,
	2112,
	1552);
INSERT INTO GD_MD
	VALUES (524292,
	4,
	372605,
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
	VALUES (524341,
	524292,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524341,
	1744,
	1392,
	1904,
	1488);
INSERT INTO GD_GE
	VALUES (524342,
	524292,
	3145734,
	11);
INSERT INTO GD_SHP
	VALUES (524342,
	1936,
	1456,
	2112,
	1552);
INSERT INTO S_SS
	VALUES (1048578,
	'br1',
	'This subsystem holds the bridge objects and other objects used to run the test.',
	'',
	1,
	372605,
	1048578);
INSERT INTO O_OBJ
	VALUES (1048577,
	'Bridge Ops on Domains',
	4,
	'BOPNRD',
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
	'test_count',
	'',
	'',
	'test_count',
	0,
	524291);
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
	'b',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (1572866,
	1572867,
	'i',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (1572867,
	1572867,
	'r',
	'',
	524292);
INSERT INTO SM_EVTDI
	VALUES (1572868,
	1572867,
	'd',
	'',
	524302);
INSERT INTO SM_EVTDI
	VALUES (1572869,
	1572867,
	's',
	'',
	524293);
INSERT INTO SM_EVTDI
	VALUES (1572870,
	1572867,
	't',
	'',
	524303);
INSERT INTO SM_EVTDI
	VALUES (1572871,
	1572867,
	'u',
	'',
	524294);
INSERT INTO SM_EVTDI
	VALUES (1572872,
	1572867,
	'us',
	'',
	524306);
INSERT INTO SM_EVTDI
	VALUES (1572873,
	1572867,
	'e',
	'',
	524305);
INSERT INTO SM_SUPDT
	VALUES (1572865,
	1572867,
	0);
INSERT INTO SM_SUPDT
	VALUES (1572866,
	1572867,
	0);
INSERT INTO SM_SDI
	VALUES (1572873,
	1572866,
	1572867);
INSERT INTO SM_SDI
	VALUES (1572869,
	1572866,
	1572867);
INSERT INTO SM_SDI
	VALUES (1572865,
	1572866,
	1572867);
INSERT INTO SM_SDI
	VALUES (1572868,
	1572866,
	1572867);
INSERT INTO SM_SDI
	VALUES (1572867,
	1572866,
	1572867);
INSERT INTO SM_SDI
	VALUES (1572872,
	1572866,
	1572867);
INSERT INTO SM_SDI
	VALUES (1572870,
	1572866,
	1572867);
INSERT INTO SM_SDI
	VALUES (1572871,
	1572866,
	1572867);
INSERT INTO SM_SDI
	VALUES (1572866,
	1572866,
	1572867);
INSERT INTO SM_STATE
	VALUES (1572865,
	1572867,
	1572865,
	'Testing Non OOA Domain',
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
	'Test Non OOA',
	0,
	'',
	'BOPNRD1',
	'');
INSERT INTO SM_CH
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
	'Test OOA',
	0,
	'',
	'BOPNRD2',
	'');
INSERT INTO SM_SEME
	VALUES (1572865,
	1572866,
	1572867,
	1572866);
INSERT INTO SM_LEVT
	VALUES (1572867,
	1572867,
	1572865);
INSERT INTO SM_SEVT
	VALUES (1572867,
	1572867,
	1572865);
INSERT INTO SM_EVT
	VALUES (1572867,
	1572867,
	1572865,
	3,
	'Re-Test',
	0,
	'',
	'BOPNRD3',
	'');
INSERT INTO SM_EIGN
	VALUES (1572865,
	1572867,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572865,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_LEVT
	VALUES (1572868,
	1572867,
	1572865);
INSERT INTO SM_SEVT
	VALUES (1572868,
	1572867,
	1572865);
INSERT INTO SM_EVT
	VALUES (1572868,
	1572867,
	1572865,
	4,
	'Test Complete',
	0,
	'',
	'BOPNRD4',
	'');
INSERT INTO SM_EIGN
	VALUES (1572865,
	1572868,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572865,
	1572868,
	1572867,
	1572865);
INSERT INTO SM_STATE
	VALUES (1572866,
	1572867,
	1572866,
	'Testing OOA Domain',
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_SEME
	VALUES (1572866,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_SEME
	VALUES (1572866,
	1572868,
	1572867,
	1572865);
INSERT INTO SM_STATE
	VALUES (1572867,
	1572867,
	1572865,
	'Done Testing',
	3,
	1);
INSERT INTO SM_EIGN
	VALUES (1572867,
	1572865,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572867,
	1572865,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572867,
	1572866,
	1572867,
	1572866,
	'');
INSERT INTO SM_SEME
	VALUES (1572867,
	1572866,
	1572867,
	1572866);
INSERT INTO SM_EIGN
	VALUES (1572867,
	1572867,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572867,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572867,
	1572868,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572867,
	1572868,
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
INSERT INTO SM_NSTXN
	VALUES (1572867,
	1572867,
	1572866,
	1572867,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572867,
	1572867,
	1572865,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572868,
	1572867,
	1572866,
	1572868,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572868,
	1572867,
	1572867,
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
	'LOG::LogInfo(message:"br1: Starting Test Non OOA Server in Same Task");

// invoke each bridge operation

select any ao from instances of AO;
assign b = ao.b;
assign i = ao.i;
assign r = ao.r;
assign d = ao.d;
assign s = ao.s;
assign t =  ao.t;
assign u = ao.id;
assign e = ao.e;
assign us=ao.us;

// bridge using bridge operations to non OOA server 
bridge RDTWO::no_parm_ret_void();
bridge ret_b = RDTWO::b_parm_ret_b(b:b);
bridge ret_i = RDTWO::i_parm_ret_i(i:i);
bridge ret_r = RDTWO::r_parm_ret_r(r:r);
bridge ret_d = RDTWO::d_parm_ret_d(d:d);
bridge ret_s = RDTWO::s_parm_ret_s(s:s);
bridge ret_t = RDTWO::t_parm_ret_t(t:t);
bridge ret_u = RDTWO::u_parm_ret_u(u:u);
bridge ret_e = RDTWO::e_parm_ret_e(e:e);
bridge ret_us = RDTWO::us_parm_ret_us(us:us);

if ( ret_b != b )
  LOG::LogFailure(message:"br1: Testing Non OOA Sever in Same Task - ret_b");
else
  LOG::LogSuccess(message:"br1: Testing Non OOA Sever in Same Task - ret_b");
end if;

if ( ret_i != i )
  LOG::LogFailure(message:"br1: Testing Non OOA Sever in Same Task - ret_i");
else
  LOG::LogSuccess(message:"br1: Testing Non OOA Sever in Same Task - ret_i");
end if;

x=ret_r-r;
if (x < 0)
  x=x*(-1);
end if;

if ( x > .001 )
  LOG::LogFailure(message:"br1: Testing Non OOA Sever in Same Task - ret_r");
else
  LOG::LogSuccess(message:"br1: Testing Non OOA Sever in Same Task - ret_r");
end if;

if ( ret_d != d )
  LOG::LogFailure(message:"br1: Testing Non OOA Sever in Same Task - ret_d");
else
  LOG::LogSuccess(message:"br1: Testing Non OOA Sever in Same Task - ret_d");
end if;

if ( ret_s != s )
  LOG::LogFailure(message:"br1: Testing Non OOA Sever in Same Task - ret_s");
else
  LOG::LogSuccess(message:"br1: Testing Non OOA Sever in Same Task - ret_s");
end if;

if ( ret_t != t )
  LOG::LogFailure(message:"br1: Testing Non OOA Sever in Same Task - ret_t");
else
  LOG::LogSuccess(message:"br1: Testing Non OOA Sever in Same Task - ret_t");
end if;

if ( ret_u != u )
  LOG::LogFailure(message:"br1: Testing Non OOA Sever in Same Task - ret_u");
else
  LOG::LogSuccess(message:"br1: Testing Non OOA Sever in Same Task - ret_u");
end if;

if ( ret_us != us )
  LOG::LogFailure(message:"br1: Testing Non OOA Sever in Same Task - ret_us");
else
  LOG::LogSuccess(message:"br1: Testing Non OOA Sever in Same Task - ret_us");
end if;

if ( ret_e != e )
  LOG::LogFailure(message:"br1: Testing Non OOA Sever in Same Task - ret_e");
else
  LOG::LogSuccess(message:"br1: Testing Non OOA Sever in Same Task - ret_e");
end if;


LOG::LogInfo(message:"br1: Finished Test Non OOA Server in Same Task");

// generate event to self to kick off next test
generate BOPNRD2:''Test OOA''(b:b,d:d,i:i,r:r,s:s,t:t,u:u,us:us,e:e) to self;',
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
	'LOG::LogInfo(message:"br1: Start Testing OOA Server");

// invoke each bridge operation

select any ao from instances of AO;
assign b = ao.b;
assign i = ao.i;
assign r = ao.r;
assign d = ao.d;
assign s = ao.s;
assign t =  ao.t;
assign u = ao.id;
assign e = ao.e;
assign us=ao.us;

// bridge using synchronous services to non OOA server 
br2::no_parm_ret_void();
ret_b = br2::b_parm_ret_b(b:b);
ret_i = br2::i_parm_ret_i(i:i);
ret_r = br2::r_parm_ret_r(r:r);
ret_d = br2::d_parm_ret_d(d:d);
ret_s = br2::s_parm_ret_s(s:s);
ret_t = br2::t_parm_ret_t(t:t);
ret_u = br2::u_parm_ret_u(u:u);
ret_e = br2::e_parm_ret_e(e:e);
ret_us = br2::us_parm_ret_us(us:us);

if ( ret_b != b )
  LOG::LogFailure(message:"br1: Testing OOA Server - ret_b");
else
  LOG::LogSuccess(message:"br1: Testing OOA Server - ret_b");
end if;

if ( ret_i != i )
  LOG::LogFailure(message:"br1: Testing OOA Server - ret_i");
else
  LOG::LogSuccess(message:"br1: Testing OOA Server - ret_i");
end if;

x=ret_r-r;
if (x < 0)
  x=x*(-1);
end if;

if ( x > .001 )
  LOG::LogFailure(message:"br1: Testing OOA Server - ret_r");
else
  LOG::LogSuccess(message:"br1: Testing OOA Server - ret_r");
end if;

if ( ret_d != d )
  LOG::LogFailure(message:"br1: Testing OOA Server - ret_d");
else
  LOG::LogSuccess(message:"br1: Testing OOA Server - ret_d");
end if;

if ( ret_s != s )
  LOG::LogFailure(message:"br1: Testing OOA Server - ret_s");
else
  LOG::LogSuccess(message:"br1: Testing OOA Server - ret_s");
end if;

if ( ret_t != t )
  LOG::LogFailure(message:"br1: Testing OOA Server - ret_t");
else
  LOG::LogSuccess(message:"br1: Testing OOA Server - ret_t");
end if;

if ( ret_u != u )
  LOG::LogFailure(message:"br1: Testing OOA Server - ret_u");
else
  LOG::LogSuccess(message:"br1: Testing OOA Server - ret_u");
end if;

if ( ret_us != us )
  LOG::LogFailure(message:"br1: Testing OOA Server - ret_us");
else
  LOG::LogSuccess(message:"br1: Testing OOA Server - ret_us");
end if;

if ( ret_e != e )
  LOG::LogFailure(message:"br1: Testing OOA Server - ret_e");
else
  LOG::LogSuccess(message:"br1: Testing OOA Server - ret_e");
end if;


LOG::LogInfo(message:"br1: Finished Testing OOA Server");

assign self.test_count = self.test_count + 1;
if ( self.test_count < 50 )
  generate BOPNRD3:''Re-Test'' to self;
else
  generate BOPNRD4:''Test Complete'' to self;
end if;',
	'');
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
	'select any btd from instances of BTD;
generate BTD4:''Shut Down''() to  btd;',
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
	2250,
	4117,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (1572866,
	1572865,
	1572865,
	41);
INSERT INTO GD_SHP
	VALUES (1572866,
	2272,
	1312,
	2800,
	1440);
INSERT INTO GD_GE
	VALUES (1572867,
	1572865,
	1572866,
	41);
INSERT INTO GD_SHP
	VALUES (1572867,
	2272,
	1504,
	2800,
	1616);
INSERT INTO GD_GE
	VALUES (1572868,
	1572865,
	1572867,
	41);
INSERT INTO GD_SHP
	VALUES (1572868,
	2272,
	1680,
	2800,
	1792);
INSERT INTO GD_GE
	VALUES (1572869,
	1572865,
	1572865,
	42);
INSERT INTO GD_CON
	VALUES (1572869,
	1572866,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (1572869,
	0,
	0,
	0,
	0,
	0,
	0,
	2532,
	1231,
	2935,
	1302,
	36,
	-42,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572870,
	1572869,
	2512,
	1312,
	2512,
	1264,
	0);
INSERT INTO GD_GE
	VALUES (1572871,
	1572865,
	1572866,
	42);
INSERT INTO GD_CON
	VALUES (1572871,
	1572866,
	1572867,
	0);
INSERT INTO GD_CTXT
	VALUES (1572871,
	0,
	0,
	0,
	0,
	0,
	0,
	2528,
	1455,
	2952,
	1490,
	32,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572872,
	1572871,
	2512,
	1440,
	2512,
	1504,
	0);
INSERT INTO GD_GE
	VALUES (1572873,
	1572865,
	1572867,
	42);
INSERT INTO GD_CON
	VALUES (1572873,
	1572867,
	1572866,
	0);
INSERT INTO GD_CTXT
	VALUES (1572873,
	0,
	0,
	0,
	0,
	0,
	0,
	2809,
	1329,
	2948,
	1375,
	-39,
	-128,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572874,
	1572873,
	2800,
	1568,
	2864,
	1568,
	0);
INSERT INTO GD_LS
	VALUES (1572875,
	1572873,
	2864,
	1568,
	2864,
	1376,
	1572874);
INSERT INTO GD_LS
	VALUES (1572876,
	1572873,
	2864,
	1376,
	2800,
	1376,
	1572875);
INSERT INTO GD_GE
	VALUES (1572877,
	1572865,
	1572868,
	42);
INSERT INTO GD_CON
	VALUES (1572877,
	1572867,
	1572868,
	0);
INSERT INTO GD_CTXT
	VALUES (1572877,
	0,
	0,
	0,
	0,
	0,
	0,
	2530,
	1639,
	2701,
	1679,
	34,
	6,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572878,
	1572877,
	2512,
	1616,
	2512,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (1048578,
	'bridge object one',
	5,
	'BRONE',
	'This object is one of two bridge objects whose functions are called by other OOA domains and non-OOA domains.  Both OOA domains an non-OOA domains will call the bridge functions of this bridge object.',
	1048578);
INSERT INTO O_TFR
	VALUES (1048577,
	1048578,
	'send_u_return_u',
	'',
	524294,
	0,
	'return param.u;',
	1);
INSERT INTO O_TPARM
	VALUES (1048577,
	1048577,
	'u',
	524294,
	0);
INSERT INTO O_TFR
	VALUES (1048578,
	1048578,
	'send_t_return_t',
	'',
	524303,
	0,
	'return param.t;',
	1);
INSERT INTO O_TPARM
	VALUES (1048578,
	1048578,
	't',
	524303,
	0);
INSERT INTO O_TFR
	VALUES (1048579,
	1048578,
	'send_s_return_s',
	'',
	524293,
	0,
	'return param.s;',
	1);
INSERT INTO O_TPARM
	VALUES (1048579,
	1048579,
	's',
	524293,
	0);
INSERT INTO O_TFR
	VALUES (1048580,
	1048578,
	'send_r_return_r',
	'',
	524292,
	0,
	'return param.r;',
	1);
INSERT INTO O_TPARM
	VALUES (1048580,
	1048580,
	'r',
	524292,
	0);
INSERT INTO O_TFR
	VALUES (1048581,
	1048578,
	'send_d_return_d',
	'',
	524302,
	0,
	'return param.d;',
	1);
INSERT INTO O_TPARM
	VALUES (1048581,
	1048581,
	'd',
	524302,
	0);
INSERT INTO O_TFR
	VALUES (1048582,
	1048578,
	'send_i_return_i',
	'',
	524291,
	0,
	'return param.i;',
	1);
INSERT INTO O_TPARM
	VALUES (1048582,
	1048582,
	'i',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (1048583,
	1048578,
	'send_b_return_b',
	'',
	524290,
	0,
	'return param.b;
',
	1);
INSERT INTO O_TPARM
	VALUES (1048583,
	1048583,
	'b',
	524290,
	0);
INSERT INTO O_TFR
	VALUES (1048584,
	1048578,
	'send_void_return_void',
	'',
	524289,
	0,
	'',
	1);
INSERT INTO O_TFR
	VALUES (1048585,
	1048578,
	'send_e_return_e',
	'',
	524305,
	0,
	'return param.e;',
	1);
INSERT INTO O_TPARM
	VALUES (1048584,
	1048585,
	'e',
	524305,
	0);
INSERT INTO O_TFR
	VALUES (1048586,
	1048578,
	'send_us_return_us',
	'',
	524306,
	0,
	'return param.us;',
	1);
INSERT INTO O_TPARM
	VALUES (1048585,
	1048586,
	'us',
	524306,
	0);
INSERT INTO O_NBATTR
	VALUES (1048580,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048580,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048580,
	1048578,
	0,
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
	VALUES (1048580,
	1048578,
	0);
INSERT INTO O_OBJ
	VALUES (1048579,
	'br1 init',
	8,
	'INIT',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048581,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048581,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048581,
	1048579,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048582,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048582,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048582,
	1048579,
	1048581,
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
	VALUES (1048581,
	1048579,
	0);
INSERT INTO SM_ISM
	VALUES (2097156,
	1048579);
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
	'init1',
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
	'LOG::LogInfo(message:"br1 init running ....");

// first set up the data
ts=TIM::current_clock();
td=TIM::current_date();

create object instance o1 of O;
o1.name="sup1";

create object instance o2 of O;
o2.name="sup2";

create object instance ao of AO;
ao.name="sub1";
ao.b=TRUE;
ao.i=42;
ao.r=3.14159;
ao.d=td;
ao.s="Hello";
ao.t=ts;
ao.e="Red";

create object instance po of PO;
po.name="sub2";
po.b=TRUE;
po.i=42;
po.r=3.14159;
po.d=td;
po.s="Hello";
po.e="Red";

relate o1 to ao across R101;
relate o2 to po across R101;

relate ao to po across R100;

// start bridge test
generate BTD1:''Run Bridge Tests''(num_ee:10) to BTD creator;
',
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
	1808,
	1136,
	2176,
	1312);
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
	2166,
	1064,
	2352,
	1100,
	-49,
	1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097156,
	2097155,
	2176,
	1168,
	2256,
	1168,
	0);
INSERT INTO GD_LS
	VALUES (2097157,
	2097155,
	2256,
	1168,
	2256,
	1088,
	2097156);
INSERT INTO GD_LS
	VALUES (2097158,
	2097155,
	2256,
	1088,
	2112,
	1088,
	2097157);
INSERT INTO GD_LS
	VALUES (2097159,
	2097155,
	2112,
	1088,
	2112,
	1136,
	2097158);
INSERT INTO O_OBJ
	VALUES (1048580,
	'Bridge Test Driver',
	10,
	'BTD',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048583,
	1048580);
INSERT INTO O_BATTR
	VALUES (1048583,
	1048580);
INSERT INTO O_ATTR
	VALUES (1048583,
	1048580,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048584,
	1048580);
INSERT INTO O_BATTR
	VALUES (1048584,
	1048580);
INSERT INTO O_ATTR
	VALUES (1048584,
	1048580,
	1048583,
	'num_ee',
	'',
	'',
	'num_ee',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048585,
	1048580);
INSERT INTO O_BATTR
	VALUES (1048585,
	1048580);
INSERT INTO O_ATTR
	VALUES (1048585,
	1048580,
	1048584,
	'done_ee',
	'',
	'',
	'done_ee',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048586,
	1048580);
INSERT INTO O_BATTR
	VALUES (1048586,
	1048580);
INSERT INTO O_ATTR
	VALUES (1048586,
	1048580,
	1048585,
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
	VALUES (1048583,
	1048580,
	0);
INSERT INTO SM_ISM
	VALUES (2621445,
	1048580);
INSERT INTO SM_SM
	VALUES (2621445,
	'',
	5);
INSERT INTO SM_MOORE
	VALUES (2621445);
INSERT INTO SM_EVTDI
	VALUES (2621441,
	2621445,
	'num_ee',
	'',
	524291);
INSERT INTO SM_SUPDT
	VALUES (2621441,
	2621445,
	0);
INSERT INTO SM_SUPDT
	VALUES (2621442,
	2621445,
	0);
INSERT INTO SM_SDI
	VALUES (2621441,
	2621442,
	2621445);
INSERT INTO SM_STATE
	VALUES (2621441,
	2621445,
	2621442,
	'Running OOA Client to non OOA Server Same Task',
	1,
	0);
INSERT INTO SM_LEVT
	VALUES (2621441,
	2621445,
	2621442);
INSERT INTO SM_SEVT
	VALUES (2621441,
	2621445,
	2621442);
INSERT INTO SM_EVT
	VALUES (2621441,
	2621445,
	2621442,
	1,
	'Run Bridge Tests',
	0,
	'',
	'BTD1',
	'');
INSERT INTO SM_EIGN
	VALUES (2621441,
	2621441,
	2621445,
	2621442,
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621441,
	2621445,
	2621442);
INSERT INTO SM_LEVT
	VALUES (2621442,
	2621445,
	2621441);
INSERT INTO SM_SEVT
	VALUES (2621442,
	2621445,
	2621441);
INSERT INTO SM_EVT
	VALUES (2621442,
	2621445,
	2621441,
	4,
	'Shut Down',
	0,
	'',
	'BTD4',
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621442,
	2621445,
	2621441);
INSERT INTO SM_STATE
	VALUES (2621442,
	2621445,
	2621441,
	'Shutting Down',
	4,
	1);
INSERT INTO SM_CH
	VALUES (2621442,
	2621441,
	2621445,
	2621442,
	'');
INSERT INTO SM_SEME
	VALUES (2621442,
	2621441,
	2621445,
	2621442);
INSERT INTO SM_CH
	VALUES (2621442,
	2621442,
	2621445,
	2621441,
	'');
INSERT INTO SM_SEME
	VALUES (2621442,
	2621442,
	2621445,
	2621441);
INSERT INTO SM_STATE
	VALUES (2621443,
	2621445,
	2621441,
	'Shutdown Stage One',
	2,
	0);
INSERT INTO SM_EIGN
	VALUES (2621443,
	2621441,
	2621445,
	2621442,
	'');
INSERT INTO SM_SEME
	VALUES (2621443,
	2621441,
	2621445,
	2621442);
INSERT INTO SM_SEME
	VALUES (2621443,
	2621442,
	2621445,
	2621441);
INSERT INTO SM_STATE
	VALUES (2621444,
	2621445,
	2621441,
	'Shutdown Stage Two',
	3,
	0);
INSERT INTO SM_EIGN
	VALUES (2621444,
	2621441,
	2621445,
	2621442,
	'');
INSERT INTO SM_SEME
	VALUES (2621444,
	2621441,
	2621445,
	2621442);
INSERT INTO SM_SEME
	VALUES (2621444,
	2621442,
	2621445,
	2621441);
INSERT INTO SM_NSTXN
	VALUES (2621441,
	2621445,
	2621441,
	2621442,
	2621441);
INSERT INTO SM_TXN
	VALUES (2621441,
	2621445,
	2621443,
	2621441);
INSERT INTO SM_CRTXN
	VALUES (2621442,
	2621445,
	2621441,
	2621442);
INSERT INTO SM_TXN
	VALUES (2621442,
	2621445,
	2621441,
	2621442);
INSERT INTO SM_NSTXN
	VALUES (2621443,
	2621445,
	2621443,
	2621442,
	2621441);
INSERT INTO SM_TXN
	VALUES (2621443,
	2621445,
	2621444,
	2621441);
INSERT INTO SM_NSTXN
	VALUES (2621444,
	2621445,
	2621444,
	2621442,
	2621441);
INSERT INTO SM_TXN
	VALUES (2621444,
	2621445,
	2621442,
	2621441);
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
	'num_ee = rcvd_evt.num_ee;
self.num_ee = num_ee;

self.done_ee = 0;

// Start the realized EE 
bridge RDONE::start_test( num_ee:num_ee );

generate BOPNRD1:''Test Non OOA''() to BOPNRD creator;

',
	'');
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
	'
bridge ARCH::shutdown();',
	'');
INSERT INTO SM_MOAH
	VALUES (2621443,
	2621445,
	2621443);
INSERT INTO SM_AH
	VALUES (2621443,
	2621445);
INSERT INTO SM_ACT
	VALUES (2621443,
	2621445,
	1,
	'// ARCH::shutdown for Verifier
control stop;',
	'');
INSERT INTO SM_MOAH
	VALUES (2621444,
	2621445,
	2621444);
INSERT INTO SM_AH
	VALUES (2621444,
	2621445);
INSERT INTO SM_ACT
	VALUES (2621444,
	2621445,
	1,
	'',
	'');
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
	1395,
	4438,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (2621442,
	2621441,
	2621441,
	41);
INSERT INTO GD_SHP
	VALUES (2621442,
	1488,
	896,
	2080,
	992);
INSERT INTO GD_GE
	VALUES (2621443,
	2621441,
	2621442,
	41);
INSERT INTO GD_SHP
	VALUES (2621443,
	1488,
	1376,
	2080,
	1488);
INSERT INTO GD_GE
	VALUES (2621444,
	2621441,
	2621443,
	41);
INSERT INTO GD_SHP
	VALUES (2621444,
	1488,
	1056,
	2080,
	1152);
INSERT INTO GD_GE
	VALUES (2621445,
	2621441,
	2621444,
	41);
INSERT INTO GD_SHP
	VALUES (2621445,
	1488,
	1216,
	2080,
	1312);
INSERT INTO GD_GE
	VALUES (2621446,
	2621441,
	2621441,
	42);
INSERT INTO GD_CON
	VALUES (2621446,
	2621442,
	2621444,
	0);
INSERT INTO GD_CTXT
	VALUES (2621446,
	0,
	0,
	0,
	0,
	0,
	0,
	1772,
	1004,
	1887,
	1028,
	133,
	-10,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621447,
	2621446,
	1760,
	992,
	1760,
	1056,
	0);
INSERT INTO GD_GE
	VALUES (2621448,
	2621441,
	2621442,
	42);
INSERT INTO GD_CON
	VALUES (2621448,
	2621442,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (2621448,
	0,
	0,
	0,
	0,
	0,
	0,
	1580,
	814,
	1819,
	855,
	-203,
	2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621449,
	2621448,
	1952,
	896,
	1952,
	768,
	0);
INSERT INTO GD_GE
	VALUES (2621450,
	2621441,
	2621443,
	42);
INSERT INTO GD_CON
	VALUES (2621450,
	2621444,
	2621445,
	0);
INSERT INTO GD_CTXT
	VALUES (2621450,
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
	VALUES (2621451,
	2621450,
	1744,
	1152,
	1744,
	1216,
	0);
INSERT INTO GD_GE
	VALUES (2621452,
	2621441,
	2621444,
	42);
INSERT INTO GD_CON
	VALUES (2621452,
	2621445,
	2621443,
	0);
INSERT INTO GD_CTXT
	VALUES (2621452,
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
	VALUES (2621453,
	2621452,
	1888,
	1312,
	1888,
	1376,
	0);
INSERT INTO S_EEM
	VALUES (1048577,
	524291,
	6,
	1048578);
INSERT INTO S_EEM
	VALUES (1048578,
	524292,
	6,
	1048578);
INSERT INTO S_EEM
	VALUES (1048579,
	524293,
	6,
	1048578);
INSERT INTO CA_COMM
	VALUES (1572866,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (1572866,
	1572867,
	2621445,
	0);
INSERT INTO CA_SMSME
	VALUES (1572866,
	2621445,
	2621442);
INSERT INTO CA_ACC
	VALUES (1572865,
	1048578,
	1572867,
	0);
INSERT INTO CA_SMOA
	VALUES (1572865,
	3145730,
	0);
INSERT INTO CA_SMOAA
	VALUES (1572865,
	3145745,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (1572865,
	3145740,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (1572865,
	3145743,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (1572865,
	3145748,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (1572865,
	3145747,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (1572865,
	3145744,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (1572865,
	3145746,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (1572865,
	3145749,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (1572865,
	3145742,
	3145730);
INSERT INTO CA_ACC
	VALUES (1572867,
	1048578,
	1572867,
	0);
INSERT INTO CA_SMOA
	VALUES (1572867,
	1048577,
	0);
INSERT INTO CA_SMOAA
	VALUES (1572867,
	1048578,
	1048577);
INSERT INTO CA_COMM
	VALUES (2097154,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (2097154,
	2097156,
	2621445,
	0);
INSERT INTO CA_SMSME
	VALUES (2097154,
	2621445,
	2621441);
INSERT INTO CA_ACC
	VALUES (2097156,
	1048578,
	2097156,
	0);
INSERT INTO CA_SMOA
	VALUES (2097156,
	3145731,
	0);
INSERT INTO CA_SMOAA
	VALUES (2097156,
	3145752,
	3145731);
INSERT INTO CA_ACC
	VALUES (2097157,
	1048578,
	2097156,
	0);
INSERT INTO CA_SMOA
	VALUES (2097157,
	3145730,
	0);
INSERT INTO CA_SMOAA
	VALUES (2097157,
	3145746,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (2097157,
	3145743,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (2097157,
	3145747,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (2097157,
	3145742,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (2097157,
	3145741,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (2097157,
	3145748,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (2097157,
	3145744,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (2097157,
	3145745,
	3145730);
INSERT INTO CA_ACC
	VALUES (2097158,
	1048578,
	2097156,
	0);
INSERT INTO CA_SMOA
	VALUES (2097158,
	3145729,
	0);
INSERT INTO CA_SMOAA
	VALUES (2097158,
	3145735,
	3145729);
INSERT INTO CA_SMOAA
	VALUES (2097158,
	3145731,
	3145729);
INSERT INTO CA_SMOAA
	VALUES (2097158,
	3145730,
	3145729);
INSERT INTO CA_SMOAA
	VALUES (2097158,
	3145732,
	3145729);
INSERT INTO CA_SMOAA
	VALUES (2097158,
	3145737,
	3145729);
INSERT INTO CA_SMOAA
	VALUES (2097158,
	3145734,
	3145729);
INSERT INTO CA_SMOAA
	VALUES (2097158,
	3145733,
	3145729);
INSERT INTO CA_COMM
	VALUES (2621442,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (2621442,
	2621445,
	1572867,
	0);
INSERT INTO CA_SMSME
	VALUES (2621442,
	1572867,
	1572865);
INSERT INTO CA_ACC
	VALUES (2621442,
	1048578,
	2621445,
	0);
INSERT INTO CA_SMOA
	VALUES (2621442,
	1048580,
	0);
INSERT INTO CA_SMOAA
	VALUES (2621442,
	1048585,
	1048580);
INSERT INTO CA_SMOAA
	VALUES (2621442,
	1048584,
	1048580);
INSERT INTO CA_EESMC
	VALUES (1048579,
	1048578,
	524292,
	1572867);
INSERT INTO CA_COMM
	VALUES (1048579,
	1048578);
INSERT INTO CA_EESME
	VALUES (1048579,
	1572867,
	1572866);
INSERT INTO GD_MD
	VALUES (1048588,
	5,
	1048578,
	11,
	1,
	0,
	1,
	1,
	0,
	12,
	1335,
	3991,
	0.754115,
	0);
INSERT INTO GD_GE
	VALUES (1048591,
	1048588,
	1048577,
	21);
INSERT INTO GD_SHP
	VALUES (1048591,
	1360,
	1248,
	1664,
	1408);
INSERT INTO GD_GE
	VALUES (1048592,
	1048588,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048592,
	1488,
	1456,
	2016,
	1904);
INSERT INTO GD_GE
	VALUES (1048593,
	1048588,
	1048579,
	21);
INSERT INTO GD_SHP
	VALUES (1048593,
	1696,
	1248,
	1984,
	1408);
INSERT INTO GD_GE
	VALUES (1048594,
	1048588,
	1048580,
	21);
INSERT INTO GD_SHP
	VALUES (1048594,
	2048,
	1248,
	2288,
	1408);
INSERT INTO GD_MD
	VALUES (1048589,
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
	VALUES (1048595,
	1048589,
	1048577,
	21);
INSERT INTO GD_SHP
	VALUES (1048595,
	1712,
	1584,
	1904,
	1648);
INSERT INTO GD_GE
	VALUES (1048596,
	1048589,
	1048579,
	21);
INSERT INTO GD_SHP
	VALUES (1048596,
	2144,
	1216,
	2336,
	1280);
INSERT INTO GD_GE
	VALUES (1048597,
	1048589,
	1048580,
	21);
INSERT INTO GD_SHP
	VALUES (1048597,
	2016,
	1424,
	2208,
	1488);
INSERT INTO GD_GE
	VALUES (1048603,
	1048589,
	1048980,
	1005);
INSERT INTO GD_CON
	VALUES (1048603,
	1048596,
	1048597,
	0);
INSERT INTO GD_CTXT
	VALUES (1048603,
	0,
	0,
	0,
	0,
	0,
	0,
	2172,
	1310,
	2415,
	1375,
	28,
	-27,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048604,
	1048603,
	2160,
	1280,
	2160,
	1424,
	0);
INSERT INTO GD_GE
	VALUES (1048605,
	1048589,
	1049143,
	1005);
INSERT INTO GD_CON
	VALUES (1048605,
	1048597,
	1048595,
	0);
INSERT INTO GD_CTXT
	VALUES (1048605,
	0,
	0,
	0,
	0,
	0,
	0,
	2023,
	1657,
	2215,
	1751,
	-8,
	50,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048606,
	1048605,
	2064,
	1488,
	2064,
	1632,
	0);
INSERT INTO GD_LS
	VALUES (1048607,
	1048605,
	2064,
	1632,
	1904,
	1632,
	1048606);
INSERT INTO GD_MD
	VALUES (1048590,
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
	VALUES (1048608,
	1048590,
	1048577,
	21);
INSERT INTO GD_SHP
	VALUES (1048608,
	1664,
	1424,
	1856,
	1488);
INSERT INTO GD_GE
	VALUES (1048609,
	1048590,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048609,
	1664,
	1600,
	1856,
	1664);
INSERT INTO GD_GE
	VALUES (1048610,
	1048590,
	1048579,
	21);
INSERT INTO GD_SHP
	VALUES (1048610,
	2160,
	1424,
	2352,
	1488);
INSERT INTO GD_GE
	VALUES (1048611,
	1048590,
	1048580,
	21);
INSERT INTO GD_SHP
	VALUES (1048611,
	1920,
	1424,
	2112,
	1488);
INSERT INTO S_SS
	VALUES (3145734,
	'objects',
	'This subsystem contains objects used by the bridge objects.  The state actions of the bridge objects create instances of these objects, delete instances, relate, unrelated, generate events to, invoke transformers on, etc.',
	'',
	100,
	372605,
	3145734);
INSERT INTO O_OBJ
	VALUES (3145729,
	'Passive Object',
	101,
	'PO',
	'',
	3145734);
INSERT INTO O_REF
	VALUES (3145729,
	3145731,
	0,
	3145751,
	3145730,
	3145733,
	3145731,
	3145729,
	3145729,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145729,
	3145729,
	3145751,
	3145731,
	1);
INSERT INTO O_ATTR
	VALUES (3145729,
	3145729,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145730,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145730,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145730,
	3145729,
	3145729,
	'name',
	'',
	'',
	'name',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (3145731,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145731,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145731,
	3145729,
	3145730,
	'b',
	'',
	'',
	'b',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (3145732,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145732,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145732,
	3145729,
	3145731,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145733,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145733,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145733,
	3145729,
	3145732,
	'r',
	'',
	'',
	'r',
	0,
	524292);
INSERT INTO O_NBATTR
	VALUES (3145734,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145734,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145734,
	3145729,
	3145733,
	'd',
	'',
	'',
	'd',
	0,
	524302);
INSERT INTO O_NBATTR
	VALUES (3145735,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145735,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145735,
	3145729,
	3145734,
	's',
	'',
	'',
	's',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (3145736,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145736,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145736,
	3145729,
	3145735,
	't',
	'',
	'',
	't',
	0,
	524303);
INSERT INTO O_NBATTR
	VALUES (3145737,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145737,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145737,
	3145729,
	3145736,
	'e',
	'',
	'',
	'e',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (3145738,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145738,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145738,
	3145729,
	3145737,
	'us',
	'',
	'',
	'us',
	0,
	524306);
INSERT INTO O_REF
	VALUES (3145729,
	3145730,
	1,
	3145741,
	3145729,
	3145730,
	3145729,
	3145739,
	3145730,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145739,
	3145729,
	3145741,
	3145730,
	1);
INSERT INTO O_ATTR
	VALUES (3145739,
	3145729,
	3145738,
	'ActiveObjectname',
	'',
	'ActiveObject',
	'name',
	1,
	524296);
INSERT INTO O_ID
	VALUES (0,
	3145729);
INSERT INTO O_OIDA
	VALUES (3145729,
	3145729,
	0);
INSERT INTO O_ID
	VALUES (1,
	3145729);
INSERT INTO O_OIDA
	VALUES (3145730,
	3145729,
	1);
INSERT INTO O_OBJ
	VALUES (3145730,
	'Active Object',
	102,
	'AO',
	'',
	3145734);
INSERT INTO O_REF
	VALUES (3145730,
	3145731,
	0,
	3145751,
	3145730,
	3145732,
	3145731,
	3145740,
	3145731,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145740,
	3145730,
	3145751,
	3145731,
	1);
INSERT INTO O_ATTR
	VALUES (3145740,
	3145730,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145741,
	3145730);
INSERT INTO O_BATTR
	VALUES (3145741,
	3145730);
INSERT INTO O_ATTR
	VALUES (3145741,
	3145730,
	3145740,
	'name',
	'',
	'',
	'name',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (3145742,
	3145730);
INSERT INTO O_BATTR
	VALUES (3145742,
	3145730);
INSERT INTO O_ATTR
	VALUES (3145742,
	3145730,
	3145741,
	'b',
	'',
	'',
	'b',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (3145743,
	3145730);
INSERT INTO O_BATTR
	VALUES (3145743,
	3145730);
INSERT INTO O_ATTR
	VALUES (3145743,
	3145730,
	3145742,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145744,
	3145730);
INSERT INTO O_BATTR
	VALUES (3145744,
	3145730);
INSERT INTO O_ATTR
	VALUES (3145744,
	3145730,
	3145743,
	'r',
	'',
	'',
	'r',
	0,
	524292);
INSERT INTO O_NBATTR
	VALUES (3145745,
	3145730);
INSERT INTO O_BATTR
	VALUES (3145745,
	3145730);
INSERT INTO O_ATTR
	VALUES (3145745,
	3145730,
	3145744,
	'd',
	'',
	'',
	'd',
	0,
	524302);
INSERT INTO O_NBATTR
	VALUES (3145746,
	3145730);
INSERT INTO O_BATTR
	VALUES (3145746,
	3145730);
INSERT INTO O_ATTR
	VALUES (3145746,
	3145730,
	3145745,
	's',
	'',
	'',
	's',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (3145747,
	3145730);
INSERT INTO O_BATTR
	VALUES (3145747,
	3145730);
INSERT INTO O_ATTR
	VALUES (3145747,
	3145730,
	3145746,
	't',
	'',
	'',
	't',
	0,
	524303);
INSERT INTO O_NBATTR
	VALUES (3145748,
	3145730);
INSERT INTO O_BATTR
	VALUES (3145748,
	3145730);
INSERT INTO O_ATTR
	VALUES (3145748,
	3145730,
	3145747,
	'e',
	'',
	'',
	'e',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (3145749,
	3145730);
INSERT INTO O_BATTR
	VALUES (3145749,
	3145730);
INSERT INTO O_ATTR
	VALUES (3145749,
	3145730,
	3145748,
	'us',
	'',
	'',
	'us',
	0,
	524306);
INSERT INTO O_NBATTR
	VALUES (3145750,
	3145730);
INSERT INTO O_BATTR
	VALUES (3145750,
	3145730);
INSERT INTO O_ATTR
	VALUES (3145750,
	3145730,
	3145749,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	3145730);
INSERT INTO O_OIDA
	VALUES (3145740,
	3145730,
	0);
INSERT INTO O_ID
	VALUES (1,
	3145730);
INSERT INTO O_OIDA
	VALUES (3145741,
	3145730,
	1);
INSERT INTO O_RTIDA
	VALUES (3145741,
	3145730,
	1,
	3145729,
	3145729);
INSERT INTO SM_ISM
	VALUES (3670023,
	3145730);
INSERT INTO SM_SM
	VALUES (3670023,
	'',
	7);
INSERT INTO SM_MOORE
	VALUES (3670023);
INSERT INTO SM_EVTDI
	VALUES (3670017,
	3670023,
	'b',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (3670018,
	3670023,
	'i',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (3670019,
	3670023,
	'r',
	'',
	524292);
INSERT INTO SM_EVTDI
	VALUES (3670020,
	3670023,
	'd',
	'',
	524302);
INSERT INTO SM_EVTDI
	VALUES (3670021,
	3670023,
	's',
	'',
	524293);
INSERT INTO SM_EVTDI
	VALUES (3670022,
	3670023,
	't',
	'',
	524303);
INSERT INTO SM_EVTDI
	VALUES (3670023,
	3670023,
	'u',
	'',
	524294);
INSERT INTO SM_EVTDI
	VALUES (3670024,
	3670023,
	'us',
	'',
	524306);
INSERT INTO SM_EVTDI
	VALUES (3670025,
	3670023,
	'e',
	'',
	524305);
INSERT INTO SM_SUPDT
	VALUES (3670017,
	3670023,
	0);
INSERT INTO SM_SDI
	VALUES (3670017,
	3670017,
	3670023);
INSERT INTO SM_SUPDT
	VALUES (3670018,
	3670023,
	0);
INSERT INTO SM_SDI
	VALUES (3670018,
	3670018,
	3670023);
INSERT INTO SM_SUPDT
	VALUES (3670019,
	3670023,
	0);
INSERT INTO SM_SDI
	VALUES (3670019,
	3670019,
	3670023);
INSERT INTO SM_SUPDT
	VALUES (3670020,
	3670023,
	0);
INSERT INTO SM_SDI
	VALUES (3670020,
	3670020,
	3670023);
INSERT INTO SM_SUPDT
	VALUES (3670021,
	3670023,
	0);
INSERT INTO SM_SDI
	VALUES (3670022,
	3670021,
	3670023);
INSERT INTO SM_SUPDT
	VALUES (3670022,
	3670023,
	0);
INSERT INTO SM_SDI
	VALUES (3670023,
	3670022,
	3670023);
INSERT INTO SM_SUPDT
	VALUES (3670023,
	3670023,
	0);
INSERT INTO SM_SDI
	VALUES (3670024,
	3670023,
	3670023);
INSERT INTO SM_SUPDT
	VALUES (3670024,
	3670023,
	0);
INSERT INTO SM_SDI
	VALUES (3670025,
	3670024,
	3670023);
INSERT INTO SM_SUPDT
	VALUES (3670025,
	3670023,
	0);
INSERT INTO SM_SUPDT
	VALUES (3670026,
	3670023,
	0);
INSERT INTO SM_SDI
	VALUES (3670021,
	3670026,
	3670023);
INSERT INTO SM_STATE
	VALUES (3670017,
	3670023,
	3670025,
	'center',
	1,
	0);
INSERT INTO SM_LEVT
	VALUES (3670017,
	3670023,
	3670017);
INSERT INTO SM_SEVT
	VALUES (3670017,
	3670023,
	3670017);
INSERT INTO SM_EVT
	VALUES (3670017,
	3670023,
	3670017,
	1,
	'b',
	0,
	'',
	'AO1',
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670017,
	3670023,
	3670017);
INSERT INTO SM_LEVT
	VALUES (3670018,
	3670023,
	3670018);
INSERT INTO SM_SEVT
	VALUES (3670018,
	3670023,
	3670018);
INSERT INTO SM_EVT
	VALUES (3670018,
	3670023,
	3670018,
	2,
	'i',
	0,
	'',
	'AO2',
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670018,
	3670023,
	3670018);
INSERT INTO SM_LEVT
	VALUES (3670019,
	3670023,
	3670019);
INSERT INTO SM_SEVT
	VALUES (3670019,
	3670023,
	3670019);
INSERT INTO SM_EVT
	VALUES (3670019,
	3670023,
	3670019,
	3,
	'r',
	0,
	'',
	'AO3',
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670019,
	3670023,
	3670019);
INSERT INTO SM_LEVT
	VALUES (3670020,
	3670023,
	3670020);
INSERT INTO SM_SEVT
	VALUES (3670020,
	3670023,
	3670020);
INSERT INTO SM_EVT
	VALUES (3670020,
	3670023,
	3670020,
	4,
	'd',
	0,
	'',
	'AO4',
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670020,
	3670023,
	3670020);
INSERT INTO SM_LEVT
	VALUES (3670021,
	3670023,
	3670025);
INSERT INTO SM_SEVT
	VALUES (3670021,
	3670023,
	3670025);
INSERT INTO SM_EVT
	VALUES (3670021,
	3670023,
	3670025,
	5,
	'back',
	0,
	'',
	'AO5',
	'');
INSERT INTO SM_EIGN
	VALUES (3670017,
	3670021,
	3670023,
	3670025,
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670021,
	3670023,
	3670025);
INSERT INTO SM_LEVT
	VALUES (3670022,
	3670023,
	3670021);
INSERT INTO SM_SEVT
	VALUES (3670022,
	3670023,
	3670021);
INSERT INTO SM_EVT
	VALUES (3670022,
	3670023,
	3670021,
	6,
	't',
	0,
	'',
	'AO6',
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670022,
	3670023,
	3670021);
INSERT INTO SM_LEVT
	VALUES (3670023,
	3670023,
	3670022);
INSERT INTO SM_SEVT
	VALUES (3670023,
	3670023,
	3670022);
INSERT INTO SM_EVT
	VALUES (3670023,
	3670023,
	3670022,
	7,
	'u',
	0,
	'',
	'AO7',
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670023,
	3670023,
	3670022);
INSERT INTO SM_LEVT
	VALUES (3670024,
	3670023,
	3670023);
INSERT INTO SM_SEVT
	VALUES (3670024,
	3670023,
	3670023);
INSERT INTO SM_EVT
	VALUES (3670024,
	3670023,
	3670023,
	8,
	'us',
	0,
	'',
	'AO8',
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670024,
	3670023,
	3670023);
INSERT INTO SM_LEVT
	VALUES (3670025,
	3670023,
	3670024);
INSERT INTO SM_SEVT
	VALUES (3670025,
	3670023,
	3670024);
INSERT INTO SM_EVT
	VALUES (3670025,
	3670023,
	3670024,
	9,
	'e',
	0,
	'',
	'AO9',
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670025,
	3670023,
	3670024);
INSERT INTO SM_LEVT
	VALUES (3670026,
	3670023,
	3670026);
INSERT INTO SM_SEVT
	VALUES (3670026,
	3670023,
	3670026);
INSERT INTO SM_EVT
	VALUES (3670026,
	3670023,
	3670026,
	10,
	's',
	0,
	'',
	'AO10',
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670026,
	3670023,
	3670026);
INSERT INTO SM_STATE
	VALUES (3670018,
	3670023,
	3670017,
	'b',
	2,
	0);
INSERT INTO SM_EIGN
	VALUES (3670018,
	3670017,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670017,
	3670023,
	3670017);
INSERT INTO SM_EIGN
	VALUES (3670018,
	3670018,
	3670023,
	3670018,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670018,
	3670023,
	3670018);
INSERT INTO SM_EIGN
	VALUES (3670018,
	3670019,
	3670023,
	3670019,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670019,
	3670023,
	3670019);
INSERT INTO SM_EIGN
	VALUES (3670018,
	3670020,
	3670023,
	3670020,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670020,
	3670023,
	3670020);
INSERT INTO SM_SEME
	VALUES (3670018,
	3670021,
	3670023,
	3670025);
INSERT INTO SM_CH
	VALUES (3670018,
	3670022,
	3670023,
	3670021,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670022,
	3670023,
	3670021);
INSERT INTO SM_CH
	VALUES (3670018,
	3670023,
	3670023,
	3670022,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670023,
	3670023,
	3670022);
INSERT INTO SM_CH
	VALUES (3670018,
	3670024,
	3670023,
	3670023,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670024,
	3670023,
	3670023);
INSERT INTO SM_CH
	VALUES (3670018,
	3670025,
	3670023,
	3670024,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670025,
	3670023,
	3670024);
INSERT INTO SM_CH
	VALUES (3670018,
	3670026,
	3670023,
	3670026,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670026,
	3670023,
	3670026);
INSERT INTO SM_STATE
	VALUES (3670019,
	3670023,
	3670018,
	'i',
	3,
	0);
INSERT INTO SM_EIGN
	VALUES (3670019,
	3670017,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670017,
	3670023,
	3670017);
INSERT INTO SM_EIGN
	VALUES (3670019,
	3670018,
	3670023,
	3670018,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670018,
	3670023,
	3670018);
INSERT INTO SM_EIGN
	VALUES (3670019,
	3670019,
	3670023,
	3670019,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670019,
	3670023,
	3670019);
INSERT INTO SM_EIGN
	VALUES (3670019,
	3670020,
	3670023,
	3670020,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670020,
	3670023,
	3670020);
INSERT INTO SM_SEME
	VALUES (3670019,
	3670021,
	3670023,
	3670025);
INSERT INTO SM_CH
	VALUES (3670019,
	3670022,
	3670023,
	3670021,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670022,
	3670023,
	3670021);
INSERT INTO SM_CH
	VALUES (3670019,
	3670023,
	3670023,
	3670022,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670023,
	3670023,
	3670022);
INSERT INTO SM_CH
	VALUES (3670019,
	3670024,
	3670023,
	3670023,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670024,
	3670023,
	3670023);
INSERT INTO SM_CH
	VALUES (3670019,
	3670025,
	3670023,
	3670024,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670025,
	3670023,
	3670024);
INSERT INTO SM_CH
	VALUES (3670019,
	3670026,
	3670023,
	3670026,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670026,
	3670023,
	3670026);
INSERT INTO SM_STATE
	VALUES (3670020,
	3670023,
	3670021,
	't',
	4,
	0);
INSERT INTO SM_EIGN
	VALUES (3670020,
	3670017,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670017,
	3670023,
	3670017);
INSERT INTO SM_EIGN
	VALUES (3670020,
	3670018,
	3670023,
	3670018,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670018,
	3670023,
	3670018);
INSERT INTO SM_EIGN
	VALUES (3670020,
	3670019,
	3670023,
	3670019,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670019,
	3670023,
	3670019);
INSERT INTO SM_EIGN
	VALUES (3670020,
	3670020,
	3670023,
	3670020,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670020,
	3670023,
	3670020);
INSERT INTO SM_SEME
	VALUES (3670020,
	3670021,
	3670023,
	3670025);
INSERT INTO SM_CH
	VALUES (3670020,
	3670022,
	3670023,
	3670021,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670022,
	3670023,
	3670021);
INSERT INTO SM_CH
	VALUES (3670020,
	3670023,
	3670023,
	3670022,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670023,
	3670023,
	3670022);
INSERT INTO SM_CH
	VALUES (3670020,
	3670024,
	3670023,
	3670023,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670024,
	3670023,
	3670023);
INSERT INTO SM_CH
	VALUES (3670020,
	3670025,
	3670023,
	3670024,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670025,
	3670023,
	3670024);
INSERT INTO SM_CH
	VALUES (3670020,
	3670026,
	3670023,
	3670026,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670026,
	3670023,
	3670026);
INSERT INTO SM_STATE
	VALUES (3670021,
	3670023,
	3670022,
	'u',
	5,
	0);
INSERT INTO SM_EIGN
	VALUES (3670021,
	3670017,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670017,
	3670023,
	3670017);
INSERT INTO SM_EIGN
	VALUES (3670021,
	3670018,
	3670023,
	3670018,
	'');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670018,
	3670023,
	3670018);
INSERT INTO SM_EIGN
	VALUES (3670021,
	3670019,
	3670023,
	3670019,
	'');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670019,
	3670023,
	3670019);
INSERT INTO SM_EIGN
	VALUES (3670021,
	3670020,
	3670023,
	3670020,
	'');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670020,
	3670023,
	3670020);
INSERT INTO SM_SEME
	VALUES (3670021,
	3670021,
	3670023,
	3670025);
INSERT INTO SM_CH
	VALUES (3670021,
	3670022,
	3670023,
	3670021,
	'');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670022,
	3670023,
	3670021);
INSERT INTO SM_CH
	VALUES (3670021,
	3670023,
	3670023,
	3670022,
	'');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670023,
	3670023,
	3670022);
INSERT INTO SM_CH
	VALUES (3670021,
	3670024,
	3670023,
	3670023,
	'');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670024,
	3670023,
	3670023);
INSERT INTO SM_CH
	VALUES (3670021,
	3670025,
	3670023,
	3670024,
	'');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670025,
	3670023,
	3670024);
INSERT INTO SM_CH
	VALUES (3670021,
	3670026,
	3670023,
	3670026,
	'');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670026,
	3670023,
	3670026);
INSERT INTO SM_STATE
	VALUES (3670022,
	3670023,
	3670019,
	'r',
	6,
	0);
INSERT INTO SM_CH
	VALUES (3670022,
	3670017,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670017,
	3670023,
	3670017);
INSERT INTO SM_CH
	VALUES (3670022,
	3670018,
	3670023,
	3670018,
	'');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670018,
	3670023,
	3670018);
INSERT INTO SM_CH
	VALUES (3670022,
	3670019,
	3670023,
	3670019,
	'');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670019,
	3670023,
	3670019);
INSERT INTO SM_CH
	VALUES (3670022,
	3670020,
	3670023,
	3670020,
	'');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670020,
	3670023,
	3670020);
INSERT INTO SM_SEME
	VALUES (3670022,
	3670021,
	3670023,
	3670025);
INSERT INTO SM_CH
	VALUES (3670022,
	3670022,
	3670023,
	3670021,
	'');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670022,
	3670023,
	3670021);
INSERT INTO SM_CH
	VALUES (3670022,
	3670023,
	3670023,
	3670022,
	'');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670023,
	3670023,
	3670022);
INSERT INTO SM_CH
	VALUES (3670022,
	3670024,
	3670023,
	3670023,
	'');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670024,
	3670023,
	3670023);
INSERT INTO SM_CH
	VALUES (3670022,
	3670025,
	3670023,
	3670024,
	'');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670025,
	3670023,
	3670024);
INSERT INTO SM_CH
	VALUES (3670022,
	3670026,
	3670023,
	3670026,
	'');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670026,
	3670023,
	3670026);
INSERT INTO SM_STATE
	VALUES (3670023,
	3670023,
	3670020,
	'd',
	7,
	0);
INSERT INTO SM_CH
	VALUES (3670023,
	3670017,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670023,
	3670017,
	3670023,
	3670017);
INSERT INTO SM_CH
	VALUES (3670023,
	3670018,
	3670023,
	3670018,
	'');
INSERT INTO SM_SEME
	VALUES (3670023,
	3670018,
	3670023,
	3670018);
INSERT INTO SM_CH
	VALUES (3670023,
	3670019,
	3670023,
	3670019,
	'');
INSERT INTO SM_SEME
	VALUES (3670023,
	3670019,
	3670023,
	3670019);
INSERT INTO SM_CH
	VALUES (3670023,
	3670020,
	3670023,
	3670020,
	'');
INSERT INTO SM_SEME
	VALUES (3670023,
	3670020,
	3670023,
	3670020);
INSERT INTO SM_SEME
	VALUES (3670023,
	3670021,
	3670023,
	3670025);
INSERT INTO SM_CH
	VALUES (3670023,
	3670022,
	3670023,
	3670021,
	'');
INSERT INTO SM_SEME
	VALUES (3670023,
	3670022,
	3670023,
	3670021);
INSERT INTO SM_CH
	VALUES (3670023,
	3670023,
	3670023,
	3670022,
	'');
INSERT INTO SM_SEME
	VALUES (3670023,
	3670023,
	3670023,
	3670022);
INSERT INTO SM_CH
	VALUES (3670023,
	3670024,
	3670023,
	3670023,
	'');
INSERT INTO SM_SEME
	VALUES (3670023,
	3670024,
	3670023,
	3670023);
INSERT INTO SM_CH
	VALUES (3670023,
	3670025,
	3670023,
	3670024,
	'');
INSERT INTO SM_SEME
	VALUES (3670023,
	3670025,
	3670023,
	3670024);
INSERT INTO SM_CH
	VALUES (3670023,
	3670026,
	3670023,
	3670026,
	'');
INSERT INTO SM_SEME
	VALUES (3670023,
	3670026,
	3670023,
	3670026);
INSERT INTO SM_STATE
	VALUES (3670024,
	3670023,
	3670026,
	's',
	8,
	0);
INSERT INTO SM_CH
	VALUES (3670024,
	3670017,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670024,
	3670017,
	3670023,
	3670017);
INSERT INTO SM_CH
	VALUES (3670024,
	3670018,
	3670023,
	3670018,
	'');
INSERT INTO SM_SEME
	VALUES (3670024,
	3670018,
	3670023,
	3670018);
INSERT INTO SM_CH
	VALUES (3670024,
	3670019,
	3670023,
	3670019,
	'');
INSERT INTO SM_SEME
	VALUES (3670024,
	3670019,
	3670023,
	3670019);
INSERT INTO SM_CH
	VALUES (3670024,
	3670020,
	3670023,
	3670020,
	'');
INSERT INTO SM_SEME
	VALUES (3670024,
	3670020,
	3670023,
	3670020);
INSERT INTO SM_SEME
	VALUES (3670024,
	3670021,
	3670023,
	3670025);
INSERT INTO SM_CH
	VALUES (3670024,
	3670022,
	3670023,
	3670021,
	'');
INSERT INTO SM_SEME
	VALUES (3670024,
	3670022,
	3670023,
	3670021);
INSERT INTO SM_CH
	VALUES (3670024,
	3670023,
	3670023,
	3670022,
	'');
INSERT INTO SM_SEME
	VALUES (3670024,
	3670023,
	3670023,
	3670022);
INSERT INTO SM_CH
	VALUES (3670024,
	3670024,
	3670023,
	3670023,
	'');
INSERT INTO SM_SEME
	VALUES (3670024,
	3670024,
	3670023,
	3670023);
INSERT INTO SM_CH
	VALUES (3670024,
	3670025,
	3670023,
	3670024,
	'');
INSERT INTO SM_SEME
	VALUES (3670024,
	3670025,
	3670023,
	3670024);
INSERT INTO SM_CH
	VALUES (3670024,
	3670026,
	3670023,
	3670026,
	'');
INSERT INTO SM_SEME
	VALUES (3670024,
	3670026,
	3670023,
	3670026);
INSERT INTO SM_STATE
	VALUES (3670025,
	3670023,
	3670023,
	'us',
	9,
	0);
INSERT INTO SM_CH
	VALUES (3670025,
	3670017,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670017,
	3670023,
	3670017);
INSERT INTO SM_CH
	VALUES (3670025,
	3670018,
	3670023,
	3670018,
	'');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670018,
	3670023,
	3670018);
INSERT INTO SM_CH
	VALUES (3670025,
	3670019,
	3670023,
	3670019,
	'');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670019,
	3670023,
	3670019);
INSERT INTO SM_CH
	VALUES (3670025,
	3670020,
	3670023,
	3670020,
	'');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670020,
	3670023,
	3670020);
INSERT INTO SM_SEME
	VALUES (3670025,
	3670021,
	3670023,
	3670025);
INSERT INTO SM_CH
	VALUES (3670025,
	3670022,
	3670023,
	3670021,
	'');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670022,
	3670023,
	3670021);
INSERT INTO SM_CH
	VALUES (3670025,
	3670023,
	3670023,
	3670022,
	'');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670023,
	3670023,
	3670022);
INSERT INTO SM_CH
	VALUES (3670025,
	3670024,
	3670023,
	3670023,
	'');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670024,
	3670023,
	3670023);
INSERT INTO SM_CH
	VALUES (3670025,
	3670025,
	3670023,
	3670024,
	'');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670025,
	3670023,
	3670024);
INSERT INTO SM_CH
	VALUES (3670025,
	3670026,
	3670023,
	3670026,
	'');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670026,
	3670023,
	3670026);
INSERT INTO SM_STATE
	VALUES (3670026,
	3670023,
	3670024,
	'e',
	10,
	0);
INSERT INTO SM_CH
	VALUES (3670026,
	3670017,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670026,
	3670017,
	3670023,
	3670017);
INSERT INTO SM_CH
	VALUES (3670026,
	3670018,
	3670023,
	3670018,
	'');
INSERT INTO SM_SEME
	VALUES (3670026,
	3670018,
	3670023,
	3670018);
INSERT INTO SM_CH
	VALUES (3670026,
	3670019,
	3670023,
	3670019,
	'');
INSERT INTO SM_SEME
	VALUES (3670026,
	3670019,
	3670023,
	3670019);
INSERT INTO SM_CH
	VALUES (3670026,
	3670020,
	3670023,
	3670020,
	'');
INSERT INTO SM_SEME
	VALUES (3670026,
	3670020,
	3670023,
	3670020);
INSERT INTO SM_SEME
	VALUES (3670026,
	3670021,
	3670023,
	3670025);
INSERT INTO SM_CH
	VALUES (3670026,
	3670022,
	3670023,
	3670021,
	'');
INSERT INTO SM_SEME
	VALUES (3670026,
	3670022,
	3670023,
	3670021);
INSERT INTO SM_CH
	VALUES (3670026,
	3670023,
	3670023,
	3670022,
	'');
INSERT INTO SM_SEME
	VALUES (3670026,
	3670023,
	3670023,
	3670022);
INSERT INTO SM_CH
	VALUES (3670026,
	3670024,
	3670023,
	3670023,
	'');
INSERT INTO SM_SEME
	VALUES (3670026,
	3670024,
	3670023,
	3670023);
INSERT INTO SM_CH
	VALUES (3670026,
	3670025,
	3670023,
	3670024,
	'');
INSERT INTO SM_SEME
	VALUES (3670026,
	3670025,
	3670023,
	3670024);
INSERT INTO SM_CH
	VALUES (3670026,
	3670026,
	3670023,
	3670026,
	'');
INSERT INTO SM_SEME
	VALUES (3670026,
	3670026,
	3670023,
	3670026);
INSERT INTO SM_NSTXN
	VALUES (3670017,
	3670023,
	3670017,
	3670018,
	3670018);
INSERT INTO SM_TXN
	VALUES (3670017,
	3670023,
	3670019,
	3670018);
INSERT INTO SM_NSTXN
	VALUES (3670018,
	3670023,
	3670019,
	3670021,
	3670025);
INSERT INTO SM_TXN
	VALUES (3670018,
	3670023,
	3670017,
	3670025);
INSERT INTO SM_NSTXN
	VALUES (3670019,
	3670023,
	3670018,
	3670021,
	3670025);
INSERT INTO SM_TXN
	VALUES (3670019,
	3670023,
	3670017,
	3670025);
INSERT INTO SM_NSTXN
	VALUES (3670020,
	3670023,
	3670020,
	3670021,
	3670025);
INSERT INTO SM_TXN
	VALUES (3670020,
	3670023,
	3670017,
	3670025);
INSERT INTO SM_NSTXN
	VALUES (3670021,
	3670023,
	3670021,
	3670021,
	3670025);
INSERT INTO SM_TXN
	VALUES (3670021,
	3670023,
	3670017,
	3670025);
INSERT INTO SM_NSTXN
	VALUES (3670022,
	3670023,
	3670017,
	3670017,
	3670017);
INSERT INTO SM_TXN
	VALUES (3670022,
	3670023,
	3670018,
	3670017);
INSERT INTO SM_NSTXN
	VALUES (3670023,
	3670023,
	3670017,
	3670019,
	3670019);
INSERT INTO SM_TXN
	VALUES (3670023,
	3670023,
	3670022,
	3670019);
INSERT INTO SM_NSTXN
	VALUES (3670024,
	3670023,
	3670017,
	3670020,
	3670020);
INSERT INTO SM_TXN
	VALUES (3670024,
	3670023,
	3670023,
	3670020);
INSERT INTO SM_NSTXN
	VALUES (3670025,
	3670023,
	3670017,
	3670022,
	3670021);
INSERT INTO SM_TXN
	VALUES (3670025,
	3670023,
	3670020,
	3670021);
INSERT INTO SM_NSTXN
	VALUES (3670026,
	3670023,
	3670017,
	3670023,
	3670022);
INSERT INTO SM_TXN
	VALUES (3670026,
	3670023,
	3670021,
	3670022);
INSERT INTO SM_NSTXN
	VALUES (3670027,
	3670023,
	3670017,
	3670024,
	3670023);
INSERT INTO SM_TXN
	VALUES (3670027,
	3670023,
	3670025,
	3670023);
INSERT INTO SM_NSTXN
	VALUES (3670028,
	3670023,
	3670017,
	3670025,
	3670024);
INSERT INTO SM_TXN
	VALUES (3670028,
	3670023,
	3670026,
	3670024);
INSERT INTO SM_NSTXN
	VALUES (3670029,
	3670023,
	3670022,
	3670021,
	3670025);
INSERT INTO SM_TXN
	VALUES (3670029,
	3670023,
	3670017,
	3670025);
INSERT INTO SM_NSTXN
	VALUES (3670030,
	3670023,
	3670023,
	3670021,
	3670025);
INSERT INTO SM_TXN
	VALUES (3670030,
	3670023,
	3670017,
	3670025);
INSERT INTO SM_NSTXN
	VALUES (3670031,
	3670023,
	3670024,
	3670021,
	3670025);
INSERT INTO SM_TXN
	VALUES (3670031,
	3670023,
	3670017,
	3670025);
INSERT INTO SM_NSTXN
	VALUES (3670032,
	3670023,
	3670017,
	3670026,
	3670026);
INSERT INTO SM_TXN
	VALUES (3670032,
	3670023,
	3670024,
	3670026);
INSERT INTO SM_NSTXN
	VALUES (3670033,
	3670023,
	3670026,
	3670021,
	3670025);
INSERT INTO SM_TXN
	VALUES (3670033,
	3670023,
	3670017,
	3670025);
INSERT INTO SM_NSTXN
	VALUES (3670034,
	3670023,
	3670025,
	3670021,
	3670025);
INSERT INTO SM_TXN
	VALUES (3670034,
	3670023,
	3670017,
	3670025);
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
	'// idle',
	'');
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
	'if ( rcvd_evt.b == self.b )
  LOG::LogSuccess(message:" br1: AO - state b");
else
  LOG::LogFailure(message:"br1: AO - state b");
end if;
generate AO5 to self;',
	'');
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
	'if ( rcvd_evt.i == self.i )
  LOG::LogSuccess(message:"br1: AO - state i");
else
  LOG::LogFailure(message:"br1: AO - state i");
end if;
generate AO5 to self;',
	'');
INSERT INTO SM_MOAH
	VALUES (3670020,
	3670023,
	3670020);
INSERT INTO SM_AH
	VALUES (3670020,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670020,
	3670023,
	1,
	'if ( rcvd_evt.t == self.t )
  LOG::LogSuccess(message:"br1: AO - state t");
else
  LOG::LogFailure(message:"br1: AO - state t");
end if;
generate AO5 to self;',
	'');
INSERT INTO SM_MOAH
	VALUES (3670021,
	3670023,
	3670021);
INSERT INTO SM_AH
	VALUES (3670021,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670021,
	3670023,
	1,
	'generate AO5 to self;',
	'');
INSERT INTO SM_MOAH
	VALUES (3670022,
	3670023,
	3670022);
INSERT INTO SM_AH
	VALUES (3670022,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670022,
	3670023,
	1,
	'if ( rcvd_evt.r == self.r )
  LOG::LogSuccess(message:"br1: AO - state r");
else
  LOG::LogFailure(message:"br1: AO - state r");
end if;
generate AO5 to self;',
	'');
INSERT INTO SM_MOAH
	VALUES (3670023,
	3670023,
	3670023);
INSERT INTO SM_AH
	VALUES (3670023,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670023,
	3670023,
	1,
	'if ( rcvd_evt.d == self.d )
  LOG::LogSuccess(message:"br1: AO - state d");
else
  LOG::LogFailure(message:"br1: AO - state d");
end if;
generate AO5 to self;',
	'');
INSERT INTO SM_MOAH
	VALUES (3670024,
	3670023,
	3670024);
INSERT INTO SM_AH
	VALUES (3670024,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670024,
	3670023,
	1,
	'if ( rcvd_evt.s == self.s )
  LOG::LogSuccess(message:"br1: AO - state s");
else
  LOG::LogFailure(message:"br1: AO - state s");
end if;
generate AO5 to self;',
	'');
INSERT INTO SM_MOAH
	VALUES (3670025,
	3670023,
	3670025);
INSERT INTO SM_AH
	VALUES (3670025,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670025,
	3670023,
	1,
	'if ( rcvd_evt.us == self.us )
  LOG::LogSuccess(message:"br1: AO - state us");
else
  LOG::LogFailure(message:"br1: AO - state us");
end if;
generate AO5 to self;',
	'');
INSERT INTO SM_MOAH
	VALUES (3670026,
	3670023,
	3670026);
INSERT INTO SM_AH
	VALUES (3670026,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670026,
	3670023,
	1,
	'if ( rcvd_evt.e == self.e )
  LOG::LogSuccess(message:"br1: AO - state e");
else
  LOG::LogFailure(message:"br1: AO - state e");
end if;
generate AO5 to self;',
	'');
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
	3907,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (3670018,
	3670017,
	3670017,
	41);
INSERT INTO GD_SHP
	VALUES (3670018,
	1856,
	1424,
	2144,
	1568);
INSERT INTO GD_GE
	VALUES (3670019,
	3670017,
	3670018,
	41);
INSERT INTO GD_SHP
	VALUES (3670019,
	1344,
	1088,
	1728,
	1216);
INSERT INTO GD_GE
	VALUES (3670020,
	3670017,
	3670019,
	41);
INSERT INTO GD_SHP
	VALUES (3670020,
	1792,
	1040,
	2160,
	1168);
INSERT INTO GD_GE
	VALUES (3670021,
	3670017,
	3670020,
	41);
INSERT INTO GD_SHP
	VALUES (3670021,
	2048,
	1776,
	2416,
	1904);
INSERT INTO GD_GE
	VALUES (3670022,
	3670017,
	3670021,
	41);
INSERT INTO GD_SHP
	VALUES (3670022,
	1616,
	1776,
	1968,
	1904);
INSERT INTO GD_GE
	VALUES (3670023,
	3670017,
	3670022,
	41);
INSERT INTO GD_SHP
	VALUES (3670023,
	2208,
	1072,
	2592,
	1200);
INSERT INTO GD_GE
	VALUES (3670024,
	3670017,
	3670023,
	41);
INSERT INTO GD_SHP
	VALUES (3670024,
	2272,
	1296,
	2656,
	1440);
INSERT INTO GD_GE
	VALUES (3670025,
	3670017,
	3670024,
	41);
INSERT INTO GD_SHP
	VALUES (3670025,
	2304,
	1488,
	2656,
	1632);
INSERT INTO GD_GE
	VALUES (3670026,
	3670017,
	3670025,
	41);
INSERT INTO GD_SHP
	VALUES (3670026,
	1264,
	1600,
	1600,
	1744);
INSERT INTO GD_GE
	VALUES (3670027,
	3670017,
	3670026,
	41);
INSERT INTO GD_SHP
	VALUES (3670027,
	1264,
	1360,
	1584,
	1504);
INSERT INTO GD_GE
	VALUES (3670028,
	3670017,
	3670017,
	42);
INSERT INTO GD_CON
	VALUES (3670028,
	3670018,
	3670020,
	0);
INSERT INTO GD_CTXT
	VALUES (3670028,
	0,
	0,
	0,
	0,
	0,
	0,
	1786,
	1224,
	1948,
	1257,
	-87,
	-55,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670029,
	3670028,
	1936,
	1424,
	1840,
	1168,
	0);
INSERT INTO GD_GE
	VALUES (3670030,
	3670017,
	3670018,
	42);
INSERT INTO GD_CON
	VALUES (3670030,
	3670020,
	3670018,
	0);
INSERT INTO GD_CTXT
	VALUES (3670030,
	0,
	0,
	0,
	0,
	0,
	0,
	1952,
	1293,
	2116,
	1349,
	40,
	14,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670031,
	3670030,
	1888,
	1168,
	1968,
	1424,
	0);
INSERT INTO GD_GE
	VALUES (3670032,
	3670017,
	3670020,
	42);
INSERT INTO GD_CON
	VALUES (3670032,
	3670021,
	3670018,
	0);
INSERT INTO GD_CTXT
	VALUES (3670032,
	0,
	0,
	0,
	0,
	0,
	0,
	2053,
	1666,
	2194,
	1699,
	-67,
	12,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670033,
	3670032,
	2176,
	1776,
	2096,
	1568,
	0);
INSERT INTO GD_GE
	VALUES (3670034,
	3670017,
	3670025,
	42);
INSERT INTO GD_CON
	VALUES (3670034,
	3670018,
	3670021,
	0);
INSERT INTO GD_CTXT
	VALUES (3670034,
	0,
	0,
	0,
	0,
	0,
	0,
	2190,
	1648,
	2336,
	1692,
	25,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670035,
	3670034,
	2139,
	1563,
	2224,
	1776,
	0);
INSERT INTO GD_GE
	VALUES (3670036,
	3670017,
	3670021,
	42);
INSERT INTO GD_CON
	VALUES (3670036,
	3670022,
	3670018,
	0);
INSERT INTO GD_CTXT
	VALUES (3670036,
	0,
	0,
	0,
	0,
	0,
	0,
	1794,
	1658,
	1953,
	1683,
	-70,
	1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670037,
	3670036,
	1872,
	1776,
	1888,
	1568,
	0);
INSERT INTO GD_GE
	VALUES (3670038,
	3670017,
	3670026,
	42);
INSERT INTO GD_CON
	VALUES (3670038,
	3670018,
	3670022,
	0);
INSERT INTO GD_CTXT
	VALUES (3670038,
	0,
	0,
	0,
	0,
	0,
	0,
	1655,
	1686,
	1805,
	1745,
	-98,
	26,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670039,
	3670038,
	1861,
	1563,
	1680,
	1776,
	0);
INSERT INTO GD_GE
	VALUES (3670040,
	3670017,
	3670022,
	42);
INSERT INTO GD_CON
	VALUES (3670040,
	3670018,
	3670019,
	0);
INSERT INTO GD_CTXT
	VALUES (3670040,
	0,
	0,
	0,
	0,
	0,
	0,
	1544,
	1292,
	1687,
	1350,
	-117,
	-14,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670041,
	3670040,
	1856,
	1440,
	1504,
	1216,
	0);
INSERT INTO GD_GE
	VALUES (3670042,
	3670017,
	3670019,
	42);
INSERT INTO GD_CON
	VALUES (3670042,
	3670019,
	3670018,
	0);
INSERT INTO GD_CTXT
	VALUES (3670042,
	0,
	0,
	0,
	0,
	0,
	0,
	1715,
	1281,
	1878,
	1338,
	7,
	-16,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670043,
	3670042,
	1552,
	1216,
	1904,
	1424,
	0);
INSERT INTO GD_GE
	VALUES (3670044,
	3670017,
	3670023,
	42);
INSERT INTO GD_CON
	VALUES (3670044,
	3670018,
	3670023,
	0);
INSERT INTO GD_CTXT
	VALUES (3670044,
	0,
	0,
	0,
	0,
	0,
	0,
	2053,
	1256,
	2148,
	1299,
	-65,
	-30,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670045,
	3670044,
	2064,
	1424,
	2208,
	1168,
	0);
INSERT INTO GD_GE
	VALUES (3670046,
	3670017,
	3670029,
	42);
INSERT INTO GD_CON
	VALUES (3670046,
	3670023,
	3670018,
	0);
INSERT INTO GD_CTXT
	VALUES (3670046,
	0,
	0,
	0,
	0,
	0,
	0,
	2210,
	1239,
	2320,
	1296,
	49,
	-60,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670047,
	3670046,
	2224,
	1200,
	2128,
	1424,
	0);
INSERT INTO GD_GE
	VALUES (3670048,
	3670017,
	3670024,
	42);
INSERT INTO GD_CON
	VALUES (3670048,
	3670018,
	3670024,
	0);
INSERT INTO GD_CTXT
	VALUES (3670048,
	0,
	0,
	0,
	0,
	0,
	0,
	2179,
	1343,
	2248,
	1386,
	-10,
	-41,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670049,
	3670048,
	2144,
	1440,
	2272,
	1344,
	0);
INSERT INTO GD_GE
	VALUES (3670050,
	3670017,
	3670030,
	42);
INSERT INTO GD_CON
	VALUES (3670050,
	3670024,
	3670018,
	0);
INSERT INTO GD_CTXT
	VALUES (3670050,
	0,
	0,
	0,
	0,
	0,
	0,
	2192,
	1424,
	2304,
	1482,
	4,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670051,
	3670050,
	2272,
	1392,
	2144,
	1472,
	0);
INSERT INTO GD_GE
	VALUES (3670052,
	3670017,
	3670032,
	42);
INSERT INTO GD_CON
	VALUES (3670052,
	3670018,
	3670025,
	0);
INSERT INTO GD_CTXT
	VALUES (3670052,
	0,
	0,
	0,
	0,
	0,
	0,
	2183,
	1495,
	2298,
	1540,
	-16,
	23,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670053,
	3670052,
	2144,
	1488,
	2304,
	1504,
	0);
INSERT INTO GD_GE
	VALUES (3670054,
	3670017,
	3670031,
	42);
INSERT INTO GD_CON
	VALUES (3670054,
	3670025,
	3670018,
	0);
INSERT INTO GD_CTXT
	VALUES (3670054,
	0,
	0,
	0,
	0,
	0,
	0,
	2169,
	1553,
	2264,
	1607,
	-30,
	33,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670055,
	3670054,
	2304,
	1552,
	2144,
	1536,
	0);
INSERT INTO GD_GE
	VALUES (3670056,
	3670017,
	3670034,
	42);
INSERT INTO GD_CON
	VALUES (3670056,
	3670026,
	3670018,
	0);
INSERT INTO GD_CTXT
	VALUES (3670056,
	0,
	0,
	0,
	0,
	0,
	0,
	1617,
	1512,
	1761,
	1574,
	-86,
	-37,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670057,
	3670056,
	1595,
	1605,
	1856,
	1504,
	0);
INSERT INTO GD_GE
	VALUES (3670058,
	3670017,
	3670027,
	42);
INSERT INTO GD_CON
	VALUES (3670058,
	3670018,
	3670026,
	0);
INSERT INTO GD_CTXT
	VALUES (3670058,
	0,
	0,
	0,
	0,
	0,
	0,
	1690,
	1615,
	1780,
	1659,
	-15,
	25,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670059,
	3670058,
	1856,
	1536,
	1616,
	1648,
	0);
INSERT INTO GD_LS
	VALUES (3670060,
	3670058,
	1616,
	1648,
	1600,
	1664,
	3670059);
INSERT INTO GD_GE
	VALUES (3670061,
	3670017,
	3670033,
	42);
INSERT INTO GD_CON
	VALUES (3670061,
	3670027,
	3670018,
	0);
INSERT INTO GD_CTXT
	VALUES (3670061,
	0,
	0,
	0,
	0,
	0,
	0,
	1623,
	1360,
	1746,
	1422,
	-73,
	-32,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670062,
	3670061,
	1584,
	1392,
	1856,
	1440,
	0);
INSERT INTO GD_GE
	VALUES (3670063,
	3670017,
	3670028,
	42);
INSERT INTO GD_CON
	VALUES (3670063,
	3670018,
	3670027,
	0);
INSERT INTO GD_CTXT
	VALUES (3670063,
	0,
	0,
	0,
	0,
	0,
	0,
	1696,
	1424,
	1791,
	1482,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670064,
	3670063,
	1856,
	1472,
	1584,
	1424,
	0);
INSERT INTO SM_ASM
	VALUES (4194312,
	3145730);
INSERT INTO SM_SM
	VALUES (4194312,
	'',
	8);
INSERT INTO SM_MOORE
	VALUES (4194312);
INSERT INTO SM_EVTDI
	VALUES (4194305,
	4194312,
	'e',
	'',
	524305);
INSERT INTO SM_EVTDI
	VALUES (4194306,
	4194312,
	'us',
	'',
	524306);
INSERT INTO SM_EVTDI
	VALUES (4194307,
	4194312,
	's',
	'',
	524293);
INSERT INTO SM_EVTDI
	VALUES (4194308,
	4194312,
	'd',
	'',
	524302);
INSERT INTO SM_EVTDI
	VALUES (4194309,
	4194312,
	'r',
	'',
	524292);
INSERT INTO SM_EVTDI
	VALUES (4194310,
	4194312,
	'b',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (4194311,
	4194312,
	'u',
	'',
	524294);
INSERT INTO SM_EVTDI
	VALUES (4194312,
	4194312,
	't',
	'',
	524303);
INSERT INTO SM_EVTDI
	VALUES (4194313,
	4194312,
	'i',
	'',
	524291);
INSERT INTO SM_SUPDT
	VALUES (4194305,
	4194312,
	0);
INSERT INTO SM_SUPDT
	VALUES (4194306,
	4194312,
	0);
INSERT INTO SM_SDI
	VALUES (4194305,
	4194306,
	4194312);
INSERT INTO SM_SUPDT
	VALUES (4194307,
	4194312,
	0);
INSERT INTO SM_SDI
	VALUES (4194306,
	4194307,
	4194312);
INSERT INTO SM_SUPDT
	VALUES (4194308,
	4194312,
	0);
INSERT INTO SM_SDI
	VALUES (4194307,
	4194308,
	4194312);
INSERT INTO SM_SUPDT
	VALUES (4194309,
	4194312,
	0);
INSERT INTO SM_SDI
	VALUES (4194308,
	4194309,
	4194312);
INSERT INTO SM_SUPDT
	VALUES (4194310,
	4194312,
	0);
INSERT INTO SM_SDI
	VALUES (4194309,
	4194310,
	4194312);
INSERT INTO SM_SUPDT
	VALUES (4194311,
	4194312,
	0);
INSERT INTO SM_SDI
	VALUES (4194310,
	4194311,
	4194312);
INSERT INTO SM_SUPDT
	VALUES (4194312,
	4194312,
	0);
INSERT INTO SM_SDI
	VALUES (4194311,
	4194312,
	4194312);
INSERT INTO SM_SUPDT
	VALUES (4194313,
	4194312,
	0);
INSERT INTO SM_SDI
	VALUES (4194312,
	4194313,
	4194312);
INSERT INTO SM_SUPDT
	VALUES (4194314,
	4194312,
	0);
INSERT INTO SM_SDI
	VALUES (4194313,
	4194314,
	4194312);
INSERT INTO SM_STATE
	VALUES (4194305,
	4194312,
	4194306,
	'e',
	2,
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
	'back',
	0,
	'',
	'AO_A1',
	'');
INSERT INTO SM_SEME
	VALUES (4194305,
	4194305,
	4194312,
	4194305);
INSERT INTO SM_LEVT
	VALUES (4194306,
	4194312,
	4194306);
INSERT INTO SM_SEVT
	VALUES (4194306,
	4194312,
	4194306);
INSERT INTO SM_EVT
	VALUES (4194306,
	4194312,
	4194306,
	6,
	'e',
	0,
	'',
	'AO_A6',
	'');
INSERT INTO SM_CH
	VALUES (4194305,
	4194306,
	4194312,
	4194306,
	'');
INSERT INTO SM_SEME
	VALUES (4194305,
	4194306,
	4194312,
	4194306);
INSERT INTO SM_LEVT
	VALUES (4194307,
	4194312,
	4194307);
INSERT INTO SM_SEVT
	VALUES (4194307,
	4194312,
	4194307);
INSERT INTO SM_EVT
	VALUES (4194307,
	4194312,
	4194307,
	7,
	'us',
	0,
	'',
	'AO_A7',
	'');
INSERT INTO SM_CH
	VALUES (4194305,
	4194307,
	4194312,
	4194307,
	'');
INSERT INTO SM_SEME
	VALUES (4194305,
	4194307,
	4194312,
	4194307);
INSERT INTO SM_LEVT
	VALUES (4194308,
	4194312,
	4194308);
INSERT INTO SM_SEVT
	VALUES (4194308,
	4194312,
	4194308);
INSERT INTO SM_EVT
	VALUES (4194308,
	4194312,
	4194308,
	8,
	's',
	0,
	'',
	'AO_A8',
	'');
INSERT INTO SM_CH
	VALUES (4194305,
	4194308,
	4194312,
	4194308,
	'');
INSERT INTO SM_SEME
	VALUES (4194305,
	4194308,
	4194312,
	4194308);
INSERT INTO SM_LEVT
	VALUES (4194309,
	4194312,
	4194309);
INSERT INTO SM_SEVT
	VALUES (4194309,
	4194312,
	4194309);
INSERT INTO SM_EVT
	VALUES (4194309,
	4194312,
	4194309,
	9,
	'd',
	0,
	'',
	'AO_A9',
	'');
INSERT INTO SM_CH
	VALUES (4194305,
	4194309,
	4194312,
	4194309,
	'');
INSERT INTO SM_SEME
	VALUES (4194305,
	4194309,
	4194312,
	4194309);
INSERT INTO SM_LEVT
	VALUES (4194310,
	4194312,
	4194310);
INSERT INTO SM_SEVT
	VALUES (4194310,
	4194312,
	4194310);
INSERT INTO SM_EVT
	VALUES (4194310,
	4194312,
	4194310,
	10,
	'r',
	0,
	'',
	'AO_A10',
	'');
INSERT INTO SM_CH
	VALUES (4194305,
	4194310,
	4194312,
	4194310,
	'');
INSERT INTO SM_SEME
	VALUES (4194305,
	4194310,
	4194312,
	4194310);
INSERT INTO SM_LEVT
	VALUES (4194311,
	4194312,
	4194311);
INSERT INTO SM_SEVT
	VALUES (4194311,
	4194312,
	4194311);
INSERT INTO SM_EVT
	VALUES (4194311,
	4194312,
	4194311,
	11,
	'b',
	0,
	'',
	'AO_A11',
	'');
INSERT INTO SM_CH
	VALUES (4194305,
	4194311,
	4194312,
	4194311,
	'');
INSERT INTO SM_SEME
	VALUES (4194305,
	4194311,
	4194312,
	4194311);
INSERT INTO SM_LEVT
	VALUES (4194312,
	4194312,
	4194312);
INSERT INTO SM_SEVT
	VALUES (4194312,
	4194312,
	4194312);
INSERT INTO SM_EVT
	VALUES (4194312,
	4194312,
	4194312,
	12,
	'u',
	0,
	'',
	'AO_A12',
	'');
INSERT INTO SM_CH
	VALUES (4194305,
	4194312,
	4194312,
	4194312,
	'');
INSERT INTO SM_SEME
	VALUES (4194305,
	4194312,
	4194312,
	4194312);
INSERT INTO SM_LEVT
	VALUES (4194313,
	4194312,
	4194313);
INSERT INTO SM_SEVT
	VALUES (4194313,
	4194312,
	4194313);
INSERT INTO SM_EVT
	VALUES (4194313,
	4194312,
	4194313,
	13,
	't',
	0,
	'',
	'AO_A13',
	'');
INSERT INTO SM_CH
	VALUES (4194305,
	4194313,
	4194312,
	4194313,
	'');
INSERT INTO SM_SEME
	VALUES (4194305,
	4194313,
	4194312,
	4194313);
INSERT INTO SM_LEVT
	VALUES (4194314,
	4194312,
	4194314);
INSERT INTO SM_SEVT
	VALUES (4194314,
	4194312,
	4194314);
INSERT INTO SM_EVT
	VALUES (4194314,
	4194312,
	4194314,
	14,
	'i',
	0,
	'',
	'AO_A14',
	'');
INSERT INTO SM_CH
	VALUES (4194305,
	4194314,
	4194312,
	4194314,
	'');
INSERT INTO SM_SEME
	VALUES (4194305,
	4194314,
	4194312,
	4194314);
INSERT INTO SM_STATE
	VALUES (4194306,
	4194312,
	4194305,
	'center',
	1,
	0);
INSERT INTO SM_CH
	VALUES (4194306,
	4194305,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194306,
	4194305,
	4194312,
	4194305);
INSERT INTO SM_SEME
	VALUES (4194306,
	4194306,
	4194312,
	4194306);
INSERT INTO SM_SEME
	VALUES (4194306,
	4194307,
	4194312,
	4194307);
INSERT INTO SM_SEME
	VALUES (4194306,
	4194308,
	4194312,
	4194308);
INSERT INTO SM_SEME
	VALUES (4194306,
	4194309,
	4194312,
	4194309);
INSERT INTO SM_SEME
	VALUES (4194306,
	4194310,
	4194312,
	4194310);
INSERT INTO SM_SEME
	VALUES (4194306,
	4194311,
	4194312,
	4194311);
INSERT INTO SM_SEME
	VALUES (4194306,
	4194312,
	4194312,
	4194312);
INSERT INTO SM_SEME
	VALUES (4194306,
	4194313,
	4194312,
	4194313);
INSERT INTO SM_SEME
	VALUES (4194306,
	4194314,
	4194312,
	4194314);
INSERT INTO SM_STATE
	VALUES (4194307,
	4194312,
	4194311,
	'b',
	3,
	0);
INSERT INTO SM_SEME
	VALUES (4194307,
	4194305,
	4194312,
	4194305);
INSERT INTO SM_CH
	VALUES (4194307,
	4194306,
	4194312,
	4194306,
	'');
INSERT INTO SM_SEME
	VALUES (4194307,
	4194306,
	4194312,
	4194306);
INSERT INTO SM_CH
	VALUES (4194307,
	4194307,
	4194312,
	4194307,
	'');
INSERT INTO SM_SEME
	VALUES (4194307,
	4194307,
	4194312,
	4194307);
INSERT INTO SM_CH
	VALUES (4194307,
	4194308,
	4194312,
	4194308,
	'');
INSERT INTO SM_SEME
	VALUES (4194307,
	4194308,
	4194312,
	4194308);
INSERT INTO SM_CH
	VALUES (4194307,
	4194309,
	4194312,
	4194309,
	'');
INSERT INTO SM_SEME
	VALUES (4194307,
	4194309,
	4194312,
	4194309);
INSERT INTO SM_CH
	VALUES (4194307,
	4194310,
	4194312,
	4194310,
	'');
INSERT INTO SM_SEME
	VALUES (4194307,
	4194310,
	4194312,
	4194310);
INSERT INTO SM_CH
	VALUES (4194307,
	4194311,
	4194312,
	4194311,
	'');
INSERT INTO SM_SEME
	VALUES (4194307,
	4194311,
	4194312,
	4194311);
INSERT INTO SM_CH
	VALUES (4194307,
	4194312,
	4194312,
	4194312,
	'');
INSERT INTO SM_SEME
	VALUES (4194307,
	4194312,
	4194312,
	4194312);
INSERT INTO SM_CH
	VALUES (4194307,
	4194313,
	4194312,
	4194313,
	'');
INSERT INTO SM_SEME
	VALUES (4194307,
	4194313,
	4194312,
	4194313);
INSERT INTO SM_CH
	VALUES (4194307,
	4194314,
	4194312,
	4194314,
	'');
INSERT INTO SM_SEME
	VALUES (4194307,
	4194314,
	4194312,
	4194314);
INSERT INTO SM_STATE
	VALUES (4194308,
	4194312,
	4194314,
	'i',
	4,
	0);
INSERT INTO SM_SEME
	VALUES (4194308,
	4194305,
	4194312,
	4194305);
INSERT INTO SM_CH
	VALUES (4194308,
	4194306,
	4194312,
	4194306,
	'');
INSERT INTO SM_SEME
	VALUES (4194308,
	4194306,
	4194312,
	4194306);
INSERT INTO SM_CH
	VALUES (4194308,
	4194307,
	4194312,
	4194307,
	'');
INSERT INTO SM_SEME
	VALUES (4194308,
	4194307,
	4194312,
	4194307);
INSERT INTO SM_CH
	VALUES (4194308,
	4194308,
	4194312,
	4194308,
	'');
INSERT INTO SM_SEME
	VALUES (4194308,
	4194308,
	4194312,
	4194308);
INSERT INTO SM_CH
	VALUES (4194308,
	4194309,
	4194312,
	4194309,
	'');
INSERT INTO SM_SEME
	VALUES (4194308,
	4194309,
	4194312,
	4194309);
INSERT INTO SM_CH
	VALUES (4194308,
	4194310,
	4194312,
	4194310,
	'');
INSERT INTO SM_SEME
	VALUES (4194308,
	4194310,
	4194312,
	4194310);
INSERT INTO SM_CH
	VALUES (4194308,
	4194311,
	4194312,
	4194311,
	'');
INSERT INTO SM_SEME
	VALUES (4194308,
	4194311,
	4194312,
	4194311);
INSERT INTO SM_CH
	VALUES (4194308,
	4194312,
	4194312,
	4194312,
	'');
INSERT INTO SM_SEME
	VALUES (4194308,
	4194312,
	4194312,
	4194312);
INSERT INTO SM_CH
	VALUES (4194308,
	4194313,
	4194312,
	4194313,
	'');
INSERT INTO SM_SEME
	VALUES (4194308,
	4194313,
	4194312,
	4194313);
INSERT INTO SM_CH
	VALUES (4194308,
	4194314,
	4194312,
	4194314,
	'');
INSERT INTO SM_SEME
	VALUES (4194308,
	4194314,
	4194312,
	4194314);
INSERT INTO SM_STATE
	VALUES (4194309,
	4194312,
	4194313,
	't',
	5,
	0);
INSERT INTO SM_SEME
	VALUES (4194309,
	4194305,
	4194312,
	4194305);
INSERT INTO SM_CH
	VALUES (4194309,
	4194306,
	4194312,
	4194306,
	'');
INSERT INTO SM_SEME
	VALUES (4194309,
	4194306,
	4194312,
	4194306);
INSERT INTO SM_CH
	VALUES (4194309,
	4194307,
	4194312,
	4194307,
	'');
INSERT INTO SM_SEME
	VALUES (4194309,
	4194307,
	4194312,
	4194307);
INSERT INTO SM_CH
	VALUES (4194309,
	4194308,
	4194312,
	4194308,
	'');
INSERT INTO SM_SEME
	VALUES (4194309,
	4194308,
	4194312,
	4194308);
INSERT INTO SM_CH
	VALUES (4194309,
	4194309,
	4194312,
	4194309,
	'');
INSERT INTO SM_SEME
	VALUES (4194309,
	4194309,
	4194312,
	4194309);
INSERT INTO SM_CH
	VALUES (4194309,
	4194310,
	4194312,
	4194310,
	'');
INSERT INTO SM_SEME
	VALUES (4194309,
	4194310,
	4194312,
	4194310);
INSERT INTO SM_CH
	VALUES (4194309,
	4194311,
	4194312,
	4194311,
	'');
INSERT INTO SM_SEME
	VALUES (4194309,
	4194311,
	4194312,
	4194311);
INSERT INTO SM_CH
	VALUES (4194309,
	4194312,
	4194312,
	4194312,
	'');
INSERT INTO SM_SEME
	VALUES (4194309,
	4194312,
	4194312,
	4194312);
INSERT INTO SM_CH
	VALUES (4194309,
	4194313,
	4194312,
	4194313,
	'');
INSERT INTO SM_SEME
	VALUES (4194309,
	4194313,
	4194312,
	4194313);
INSERT INTO SM_CH
	VALUES (4194309,
	4194314,
	4194312,
	4194314,
	'');
INSERT INTO SM_SEME
	VALUES (4194309,
	4194314,
	4194312,
	4194314);
INSERT INTO SM_STATE
	VALUES (4194310,
	4194312,
	4194312,
	'u',
	6,
	0);
INSERT INTO SM_SEME
	VALUES (4194310,
	4194305,
	4194312,
	4194305);
INSERT INTO SM_CH
	VALUES (4194310,
	4194306,
	4194312,
	4194306,
	'');
INSERT INTO SM_SEME
	VALUES (4194310,
	4194306,
	4194312,
	4194306);
INSERT INTO SM_CH
	VALUES (4194310,
	4194307,
	4194312,
	4194307,
	'');
INSERT INTO SM_SEME
	VALUES (4194310,
	4194307,
	4194312,
	4194307);
INSERT INTO SM_CH
	VALUES (4194310,
	4194308,
	4194312,
	4194308,
	'');
INSERT INTO SM_SEME
	VALUES (4194310,
	4194308,
	4194312,
	4194308);
INSERT INTO SM_CH
	VALUES (4194310,
	4194309,
	4194312,
	4194309,
	'');
INSERT INTO SM_SEME
	VALUES (4194310,
	4194309,
	4194312,
	4194309);
INSERT INTO SM_CH
	VALUES (4194310,
	4194310,
	4194312,
	4194310,
	'');
INSERT INTO SM_SEME
	VALUES (4194310,
	4194310,
	4194312,
	4194310);
INSERT INTO SM_CH
	VALUES (4194310,
	4194311,
	4194312,
	4194311,
	'');
INSERT INTO SM_SEME
	VALUES (4194310,
	4194311,
	4194312,
	4194311);
INSERT INTO SM_CH
	VALUES (4194310,
	4194312,
	4194312,
	4194312,
	'');
INSERT INTO SM_SEME
	VALUES (4194310,
	4194312,
	4194312,
	4194312);
INSERT INTO SM_CH
	VALUES (4194310,
	4194313,
	4194312,
	4194313,
	'');
INSERT INTO SM_SEME
	VALUES (4194310,
	4194313,
	4194312,
	4194313);
INSERT INTO SM_CH
	VALUES (4194310,
	4194314,
	4194312,
	4194314,
	'');
INSERT INTO SM_SEME
	VALUES (4194310,
	4194314,
	4194312,
	4194314);
INSERT INTO SM_STATE
	VALUES (4194311,
	4194312,
	4194310,
	'r',
	7,
	0);
INSERT INTO SM_SEME
	VALUES (4194311,
	4194305,
	4194312,
	4194305);
INSERT INTO SM_CH
	VALUES (4194311,
	4194306,
	4194312,
	4194306,
	'');
INSERT INTO SM_SEME
	VALUES (4194311,
	4194306,
	4194312,
	4194306);
INSERT INTO SM_CH
	VALUES (4194311,
	4194307,
	4194312,
	4194307,
	'');
INSERT INTO SM_SEME
	VALUES (4194311,
	4194307,
	4194312,
	4194307);
INSERT INTO SM_CH
	VALUES (4194311,
	4194308,
	4194312,
	4194308,
	'');
INSERT INTO SM_SEME
	VALUES (4194311,
	4194308,
	4194312,
	4194308);
INSERT INTO SM_CH
	VALUES (4194311,
	4194309,
	4194312,
	4194309,
	'');
INSERT INTO SM_SEME
	VALUES (4194311,
	4194309,
	4194312,
	4194309);
INSERT INTO SM_CH
	VALUES (4194311,
	4194310,
	4194312,
	4194310,
	'');
INSERT INTO SM_SEME
	VALUES (4194311,
	4194310,
	4194312,
	4194310);
INSERT INTO SM_CH
	VALUES (4194311,
	4194311,
	4194312,
	4194311,
	'');
INSERT INTO SM_SEME
	VALUES (4194311,
	4194311,
	4194312,
	4194311);
INSERT INTO SM_CH
	VALUES (4194311,
	4194312,
	4194312,
	4194312,
	'');
INSERT INTO SM_SEME
	VALUES (4194311,
	4194312,
	4194312,
	4194312);
INSERT INTO SM_CH
	VALUES (4194311,
	4194313,
	4194312,
	4194313,
	'');
INSERT INTO SM_SEME
	VALUES (4194311,
	4194313,
	4194312,
	4194313);
INSERT INTO SM_CH
	VALUES (4194311,
	4194314,
	4194312,
	4194314,
	'');
INSERT INTO SM_SEME
	VALUES (4194311,
	4194314,
	4194312,
	4194314);
INSERT INTO SM_STATE
	VALUES (4194312,
	4194312,
	4194309,
	'd',
	8,
	0);
INSERT INTO SM_SEME
	VALUES (4194312,
	4194305,
	4194312,
	4194305);
INSERT INTO SM_CH
	VALUES (4194312,
	4194306,
	4194312,
	4194306,
	'');
INSERT INTO SM_SEME
	VALUES (4194312,
	4194306,
	4194312,
	4194306);
INSERT INTO SM_CH
	VALUES (4194312,
	4194307,
	4194312,
	4194307,
	'');
INSERT INTO SM_SEME
	VALUES (4194312,
	4194307,
	4194312,
	4194307);
INSERT INTO SM_CH
	VALUES (4194312,
	4194308,
	4194312,
	4194308,
	'');
INSERT INTO SM_SEME
	VALUES (4194312,
	4194308,
	4194312,
	4194308);
INSERT INTO SM_CH
	VALUES (4194312,
	4194309,
	4194312,
	4194309,
	'');
INSERT INTO SM_SEME
	VALUES (4194312,
	4194309,
	4194312,
	4194309);
INSERT INTO SM_CH
	VALUES (4194312,
	4194310,
	4194312,
	4194310,
	'');
INSERT INTO SM_SEME
	VALUES (4194312,
	4194310,
	4194312,
	4194310);
INSERT INTO SM_CH
	VALUES (4194312,
	4194311,
	4194312,
	4194311,
	'');
INSERT INTO SM_SEME
	VALUES (4194312,
	4194311,
	4194312,
	4194311);
INSERT INTO SM_CH
	VALUES (4194312,
	4194312,
	4194312,
	4194312,
	'');
INSERT INTO SM_SEME
	VALUES (4194312,
	4194312,
	4194312,
	4194312);
INSERT INTO SM_CH
	VALUES (4194312,
	4194313,
	4194312,
	4194313,
	'');
INSERT INTO SM_SEME
	VALUES (4194312,
	4194313,
	4194312,
	4194313);
INSERT INTO SM_CH
	VALUES (4194312,
	4194314,
	4194312,
	4194314,
	'');
INSERT INTO SM_SEME
	VALUES (4194312,
	4194314,
	4194312,
	4194314);
INSERT INTO SM_STATE
	VALUES (4194313,
	4194312,
	4194308,
	's',
	9,
	0);
INSERT INTO SM_SEME
	VALUES (4194313,
	4194305,
	4194312,
	4194305);
INSERT INTO SM_CH
	VALUES (4194313,
	4194306,
	4194312,
	4194306,
	'');
INSERT INTO SM_SEME
	VALUES (4194313,
	4194306,
	4194312,
	4194306);
INSERT INTO SM_CH
	VALUES (4194313,
	4194307,
	4194312,
	4194307,
	'');
INSERT INTO SM_SEME
	VALUES (4194313,
	4194307,
	4194312,
	4194307);
INSERT INTO SM_CH
	VALUES (4194313,
	4194308,
	4194312,
	4194308,
	'');
INSERT INTO SM_SEME
	VALUES (4194313,
	4194308,
	4194312,
	4194308);
INSERT INTO SM_CH
	VALUES (4194313,
	4194309,
	4194312,
	4194309,
	'');
INSERT INTO SM_SEME
	VALUES (4194313,
	4194309,
	4194312,
	4194309);
INSERT INTO SM_CH
	VALUES (4194313,
	4194310,
	4194312,
	4194310,
	'');
INSERT INTO SM_SEME
	VALUES (4194313,
	4194310,
	4194312,
	4194310);
INSERT INTO SM_CH
	VALUES (4194313,
	4194311,
	4194312,
	4194311,
	'');
INSERT INTO SM_SEME
	VALUES (4194313,
	4194311,
	4194312,
	4194311);
INSERT INTO SM_CH
	VALUES (4194313,
	4194312,
	4194312,
	4194312,
	'');
INSERT INTO SM_SEME
	VALUES (4194313,
	4194312,
	4194312,
	4194312);
INSERT INTO SM_CH
	VALUES (4194313,
	4194313,
	4194312,
	4194313,
	'');
INSERT INTO SM_SEME
	VALUES (4194313,
	4194313,
	4194312,
	4194313);
INSERT INTO SM_CH
	VALUES (4194313,
	4194314,
	4194312,
	4194314,
	'');
INSERT INTO SM_SEME
	VALUES (4194313,
	4194314,
	4194312,
	4194314);
INSERT INTO SM_STATE
	VALUES (4194314,
	4194312,
	4194307,
	'us',
	10,
	0);
INSERT INTO SM_SEME
	VALUES (4194314,
	4194305,
	4194312,
	4194305);
INSERT INTO SM_CH
	VALUES (4194314,
	4194306,
	4194312,
	4194306,
	'');
INSERT INTO SM_SEME
	VALUES (4194314,
	4194306,
	4194312,
	4194306);
INSERT INTO SM_CH
	VALUES (4194314,
	4194307,
	4194312,
	4194307,
	'');
INSERT INTO SM_SEME
	VALUES (4194314,
	4194307,
	4194312,
	4194307);
INSERT INTO SM_CH
	VALUES (4194314,
	4194308,
	4194312,
	4194308,
	'');
INSERT INTO SM_SEME
	VALUES (4194314,
	4194308,
	4194312,
	4194308);
INSERT INTO SM_CH
	VALUES (4194314,
	4194309,
	4194312,
	4194309,
	'');
INSERT INTO SM_SEME
	VALUES (4194314,
	4194309,
	4194312,
	4194309);
INSERT INTO SM_CH
	VALUES (4194314,
	4194310,
	4194312,
	4194310,
	'');
INSERT INTO SM_SEME
	VALUES (4194314,
	4194310,
	4194312,
	4194310);
INSERT INTO SM_CH
	VALUES (4194314,
	4194311,
	4194312,
	4194311,
	'');
INSERT INTO SM_SEME
	VALUES (4194314,
	4194311,
	4194312,
	4194311);
INSERT INTO SM_CH
	VALUES (4194314,
	4194312,
	4194312,
	4194312,
	'');
INSERT INTO SM_SEME
	VALUES (4194314,
	4194312,
	4194312,
	4194312);
INSERT INTO SM_CH
	VALUES (4194314,
	4194313,
	4194312,
	4194313,
	'');
INSERT INTO SM_SEME
	VALUES (4194314,
	4194313,
	4194312,
	4194313);
INSERT INTO SM_CH
	VALUES (4194314,
	4194314,
	4194312,
	4194314,
	'');
INSERT INTO SM_SEME
	VALUES (4194314,
	4194314,
	4194312,
	4194314);
INSERT INTO SM_NSTXN
	VALUES (4194305,
	4194312,
	4194306,
	4194306,
	4194306);
INSERT INTO SM_TXN
	VALUES (4194305,
	4194312,
	4194305,
	4194306);
INSERT INTO SM_NSTXN
	VALUES (4194306,
	4194312,
	4194305,
	4194305,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194306,
	4194312,
	4194306,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194307,
	4194312,
	4194306,
	4194307,
	4194307);
INSERT INTO SM_TXN
	VALUES (4194307,
	4194312,
	4194314,
	4194307);
INSERT INTO SM_NSTXN
	VALUES (4194308,
	4194312,
	4194314,
	4194305,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194308,
	4194312,
	4194306,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194309,
	4194312,
	4194313,
	4194305,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194309,
	4194312,
	4194306,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194310,
	4194312,
	4194306,
	4194308,
	4194308);
INSERT INTO SM_TXN
	VALUES (4194310,
	4194312,
	4194313,
	4194308);
INSERT INTO SM_NSTXN
	VALUES (4194311,
	4194312,
	4194312,
	4194305,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194311,
	4194312,
	4194306,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194312,
	4194312,
	4194306,
	4194309,
	4194309);
INSERT INTO SM_TXN
	VALUES (4194312,
	4194312,
	4194312,
	4194309);
INSERT INTO SM_NSTXN
	VALUES (4194313,
	4194312,
	4194311,
	4194305,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194313,
	4194312,
	4194306,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194314,
	4194312,
	4194306,
	4194310,
	4194310);
INSERT INTO SM_TXN
	VALUES (4194314,
	4194312,
	4194311,
	4194310);
INSERT INTO SM_NSTXN
	VALUES (4194315,
	4194312,
	4194307,
	4194305,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194315,
	4194312,
	4194306,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194316,
	4194312,
	4194306,
	4194311,
	4194311);
INSERT INTO SM_TXN
	VALUES (4194316,
	4194312,
	4194307,
	4194311);
INSERT INTO SM_NSTXN
	VALUES (4194317,
	4194312,
	4194306,
	4194312,
	4194312);
INSERT INTO SM_TXN
	VALUES (4194317,
	4194312,
	4194310,
	4194312);
INSERT INTO SM_NSTXN
	VALUES (4194318,
	4194312,
	4194310,
	4194305,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194318,
	4194312,
	4194306,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194319,
	4194312,
	4194306,
	4194313,
	4194313);
INSERT INTO SM_TXN
	VALUES (4194319,
	4194312,
	4194309,
	4194313);
INSERT INTO SM_NSTXN
	VALUES (4194320,
	4194312,
	4194309,
	4194305,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194320,
	4194312,
	4194306,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194321,
	4194312,
	4194308,
	4194305,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194321,
	4194312,
	4194306,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194322,
	4194312,
	4194306,
	4194314,
	4194314);
INSERT INTO SM_TXN
	VALUES (4194322,
	4194312,
	4194308,
	4194314);
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
	'select any ao from instances of AO;
e=ao.e;
if ( rcvd_evt.e == e )
  LOG::LogSuccess(message:"br1: AOA - state e");
else
  LOG::LogFailure(message:"br1: AOA - state e");
end if;
generate AO_A1 to AO Assigner;',
	'');
INSERT INTO SM_MOAH
	VALUES (4194306,
	4194312,
	4194306);
INSERT INTO SM_AH
	VALUES (4194306,
	4194312);
INSERT INTO SM_ACT
	VALUES (4194306,
	4194312,
	1,
	'// idle',
	'');
INSERT INTO SM_MOAH
	VALUES (4194307,
	4194312,
	4194307);
INSERT INTO SM_AH
	VALUES (4194307,
	4194312);
INSERT INTO SM_ACT
	VALUES (4194307,
	4194312,
	1,
	'select any ao from instances of AO;
b=ao.b;
if ( rcvd_evt.b == b )
  LOG::LogSuccess(message:"br1: AOA - state b");
else
  LOG::LogFailure(message:"br1: AOA - state b");
end if;
generate AO_A1 to AO Assigner;',
	'');
INSERT INTO SM_MOAH
	VALUES (4194308,
	4194312,
	4194308);
INSERT INTO SM_AH
	VALUES (4194308,
	4194312);
INSERT INTO SM_ACT
	VALUES (4194308,
	4194312,
	1,
	'select any ao from instances of AO;
i=ao.i;
if ( rcvd_evt.i == i )
  LOG::LogSuccess(message:"br1: AOA - state i");
else
  LOG::LogFailure(message:"br1: AOA - state i");
end if;
generate AO_A1 to AO Assigner;',
	'');
INSERT INTO SM_MOAH
	VALUES (4194309,
	4194312,
	4194309);
INSERT INTO SM_AH
	VALUES (4194309,
	4194312);
INSERT INTO SM_ACT
	VALUES (4194309,
	4194312,
	1,
	'select any ao from instances of AO;
t=ao.t;
if ( rcvd_evt.t == t )
  LOG::LogSuccess(message:"br1: AOA - state t");
else
  LOG::LogFailure(message:"br1: AOA - state t");
end if;
generate AO_A1 to AO Assigner;',
	'');
INSERT INTO SM_MOAH
	VALUES (4194310,
	4194312,
	4194310);
INSERT INTO SM_AH
	VALUES (4194310,
	4194312);
INSERT INTO SM_ACT
	VALUES (4194310,
	4194312,
	1,
	'select any ao from instances of AO;
u=ao.id;
generate AO_A1 to AO Assigner;',
	'');
INSERT INTO SM_MOAH
	VALUES (4194311,
	4194312,
	4194311);
INSERT INTO SM_AH
	VALUES (4194311,
	4194312);
INSERT INTO SM_ACT
	VALUES (4194311,
	4194312,
	1,
	'select any ao from instances of AO;
r=ao.r;
if ( rcvd_evt.r == r )
  LOG::LogSuccess(message:"br1: AOA - state r");
else
  LOG::LogFailure(message:"br1: AOA - state r");
end if;
generate AO_A1 to AO Assigner;',
	'');
INSERT INTO SM_MOAH
	VALUES (4194312,
	4194312,
	4194312);
INSERT INTO SM_AH
	VALUES (4194312,
	4194312);
INSERT INTO SM_ACT
	VALUES (4194312,
	4194312,
	1,
	'select any ao from instances of AO;
d=ao.d;
if ( rcvd_evt.d == d )
  LOG::LogSuccess(message:"br1: AOA - state d");
else
  LOG::LogFailure(message:"br1: AOA - state d");
end if;
generate AO_A1 to AO Assigner;',
	'');
INSERT INTO SM_MOAH
	VALUES (4194313,
	4194312,
	4194313);
INSERT INTO SM_AH
	VALUES (4194313,
	4194312);
INSERT INTO SM_ACT
	VALUES (4194313,
	4194312,
	1,
	'select any ao from instances of AO;
s=ao.s;
if ( rcvd_evt.s == s )
  LOG::LogSuccess(message:"br1: AOA - state s");
else
  LOG::LogFailure(message:"br1: AOA - state s");
end if;
generate AO_A1 to AO Assigner;',
	'');
INSERT INTO SM_MOAH
	VALUES (4194314,
	4194312,
	4194314);
INSERT INTO SM_AH
	VALUES (4194314,
	4194312);
INSERT INTO SM_ACT
	VALUES (4194314,
	4194312,
	1,
	'select any ao from instances of AO;
us=ao.us;
if ( rcvd_evt.us == us )
  LOG::LogSuccess(message:"br1: AOA - state us");
else
  LOG::LogFailure(message:"br1: AOA - state us");
end if;
generate AO_A1 to AO Assigner;',
	'');
INSERT INTO GD_MD
	VALUES (4194305,
	10,
	4194312,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	3676,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (4194306,
	4194305,
	4194305,
	41);
INSERT INTO GD_SHP
	VALUES (4194306,
	1693,
	1563,
	2013,
	1707);
INSERT INTO GD_GE
	VALUES (4194307,
	4194305,
	4194306,
	41);
INSERT INTO GD_SHP
	VALUES (4194307,
	2285,
	1627,
	2573,
	1771);
INSERT INTO GD_GE
	VALUES (4194308,
	4194305,
	4194307,
	41);
INSERT INTO GD_SHP
	VALUES (4194308,
	1773,
	1291,
	2157,
	1419);
INSERT INTO GD_GE
	VALUES (4194309,
	4194305,
	4194308,
	41);
INSERT INTO GD_SHP
	VALUES (4194309,
	2221,
	1243,
	2589,
	1371);
INSERT INTO GD_GE
	VALUES (4194310,
	4194305,
	4194309,
	41);
INSERT INTO GD_SHP
	VALUES (4194310,
	2477,
	1979,
	2845,
	2107);
INSERT INTO GD_GE
	VALUES (4194311,
	4194305,
	4194310,
	41);
INSERT INTO GD_SHP
	VALUES (4194311,
	2045,
	1979,
	2397,
	2107);
INSERT INTO GD_GE
	VALUES (4194312,
	4194305,
	4194311,
	41);
INSERT INTO GD_SHP
	VALUES (4194312,
	2637,
	1275,
	3021,
	1403);
INSERT INTO GD_GE
	VALUES (4194313,
	4194305,
	4194312,
	41);
INSERT INTO GD_SHP
	VALUES (4194313,
	2701,
	1499,
	3085,
	1643);
INSERT INTO GD_GE
	VALUES (4194314,
	4194305,
	4194313,
	41);
INSERT INTO GD_SHP
	VALUES (4194314,
	2733,
	1691,
	3085,
	1835);
INSERT INTO GD_GE
	VALUES (4194315,
	4194305,
	4194314,
	41);
INSERT INTO GD_SHP
	VALUES (4194315,
	1693,
	1803,
	2029,
	1947);
INSERT INTO GD_GE
	VALUES (4194316,
	4194305,
	4194305,
	42);
INSERT INTO GD_CON
	VALUES (4194316,
	4194307,
	4194306,
	0);
INSERT INTO GD_CTXT
	VALUES (4194316,
	0,
	0,
	0,
	0,
	0,
	0,
	2125,
	1627,
	2220,
	1685,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194317,
	4194316,
	2285,
	1675,
	2013,
	1627,
	0);
INSERT INTO GD_GE
	VALUES (4194318,
	4194305,
	4194306,
	42);
INSERT INTO GD_CON
	VALUES (4194318,
	4194306,
	4194307,
	0);
INSERT INTO GD_CTXT
	VALUES (4194318,
	0,
	0,
	0,
	0,
	0,
	0,
	2052,
	1563,
	2175,
	1625,
	-73,
	-32,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194319,
	4194318,
	2013,
	1595,
	2285,
	1643,
	0);
INSERT INTO GD_GE
	VALUES (4194320,
	4194305,
	4194307,
	42);
INSERT INTO GD_CON
	VALUES (4194320,
	4194307,
	4194315,
	0);
INSERT INTO GD_CTXT
	VALUES (4194320,
	0,
	0,
	0,
	0,
	0,
	0,
	2129,
	1798,
	2236,
	1848,
	-5,
	5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194321,
	4194320,
	2285,
	1739,
	2045,
	1851,
	0);
INSERT INTO GD_LS
	VALUES (4194322,
	4194320,
	2045,
	1851,
	2029,
	1867,
	4194321);
INSERT INTO GD_GE
	VALUES (4194323,
	4194305,
	4194308,
	42);
INSERT INTO GD_CON
	VALUES (4194323,
	4194315,
	4194307,
	0);
INSERT INTO GD_CTXT
	VALUES (4194323,
	0,
	0,
	0,
	0,
	0,
	0,
	2046,
	1715,
	2190,
	1777,
	-86,
	-37,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194324,
	4194323,
	2024,
	1808,
	2285,
	1707,
	0);
INSERT INTO GD_GE
	VALUES (4194325,
	4194305,
	4194309,
	42);
INSERT INTO GD_CON
	VALUES (4194325,
	4194314,
	4194307,
	0);
INSERT INTO GD_CTXT
	VALUES (4194325,
	0,
	0,
	0,
	0,
	0,
	0,
	2598,
	1756,
	2693,
	1810,
	-30,
	33,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194326,
	4194325,
	2733,
	1755,
	2573,
	1739,
	0);
INSERT INTO GD_GE
	VALUES (4194327,
	4194305,
	4194310,
	42);
INSERT INTO GD_CON
	VALUES (4194327,
	4194307,
	4194314,
	0);
INSERT INTO GD_CTXT
	VALUES (4194327,
	0,
	0,
	0,
	0,
	0,
	0,
	2612,
	1698,
	2727,
	1743,
	-16,
	23,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194328,
	4194327,
	2573,
	1691,
	2733,
	1707,
	0);
INSERT INTO GD_GE
	VALUES (4194329,
	4194305,
	4194311,
	42);
INSERT INTO GD_CON
	VALUES (4194329,
	4194313,
	4194307,
	0);
INSERT INTO GD_CTXT
	VALUES (4194329,
	0,
	0,
	0,
	0,
	0,
	0,
	2621,
	1627,
	2733,
	1685,
	4,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194330,
	4194329,
	2701,
	1595,
	2573,
	1675,
	0);
INSERT INTO GD_GE
	VALUES (4194331,
	4194305,
	4194312,
	42);
INSERT INTO GD_CON
	VALUES (4194331,
	4194307,
	4194313,
	0);
INSERT INTO GD_CTXT
	VALUES (4194331,
	0,
	0,
	0,
	0,
	0,
	0,
	2588,
	1564,
	2674,
	1614,
	-30,
	-23,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194332,
	4194331,
	2573,
	1643,
	2701,
	1547,
	0);
INSERT INTO GD_GE
	VALUES (4194333,
	4194305,
	4194313,
	42);
INSERT INTO GD_CON
	VALUES (4194333,
	4194312,
	4194307,
	0);
INSERT INTO GD_CTXT
	VALUES (4194333,
	0,
	0,
	0,
	0,
	0,
	0,
	2589,
	1472,
	2699,
	1529,
	-1,
	-30,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194334,
	4194333,
	2653,
	1403,
	2557,
	1627,
	0);
INSERT INTO GD_GE
	VALUES (4194335,
	4194305,
	4194314,
	42);
INSERT INTO GD_CON
	VALUES (4194335,
	4194307,
	4194312,
	0);
INSERT INTO GD_CTXT
	VALUES (4194335,
	0,
	0,
	0,
	0,
	0,
	0,
	2494,
	1460,
	2594,
	1518,
	-53,
	-29,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194336,
	4194335,
	2493,
	1627,
	2637,
	1371,
	0);
INSERT INTO GD_GE
	VALUES (4194337,
	4194305,
	4194315,
	42);
INSERT INTO GD_CON
	VALUES (4194337,
	4194308,
	4194307,
	0);
INSERT INTO GD_CTXT
	VALUES (4194337,
	0,
	0,
	0,
	0,
	0,
	0,
	2144,
	1484,
	2307,
	1541,
	7,
	-16,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194338,
	4194337,
	1981,
	1419,
	2333,
	1627,
	0);
INSERT INTO GD_GE
	VALUES (4194339,
	4194305,
	4194316,
	42);
INSERT INTO GD_CON
	VALUES (4194339,
	4194307,
	4194308,
	0);
INSERT INTO GD_CTXT
	VALUES (4194339,
	0,
	0,
	0,
	0,
	0,
	0,
	1982,
	1480,
	2125,
	1538,
	-108,
	-29,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194340,
	4194339,
	2285,
	1643,
	1933,
	1419,
	0);
INSERT INTO GD_GE
	VALUES (4194341,
	4194305,
	4194317,
	42);
INSERT INTO GD_CON
	VALUES (4194341,
	4194307,
	4194311,
	0);
INSERT INTO GD_CTXT
	VALUES (4194341,
	0,
	0,
	0,
	0,
	0,
	0,
	2084,
	1889,
	2234,
	1948,
	-98,
	26,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194342,
	4194341,
	2290,
	1766,
	2109,
	1979,
	0);
INSERT INTO GD_GE
	VALUES (4194343,
	4194305,
	4194318,
	42);
INSERT INTO GD_CON
	VALUES (4194343,
	4194311,
	4194307,
	0);
INSERT INTO GD_CTXT
	VALUES (4194343,
	0,
	0,
	0,
	0,
	0,
	0,
	2223,
	1861,
	2382,
	1886,
	-70,
	1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194344,
	4194343,
	2301,
	1979,
	2317,
	1771,
	0);
INSERT INTO GD_GE
	VALUES (4194345,
	4194305,
	4194319,
	42);
INSERT INTO GD_CON
	VALUES (4194345,
	4194307,
	4194310,
	0);
INSERT INTO GD_CTXT
	VALUES (4194345,
	0,
	0,
	0,
	0,
	0,
	0,
	2619,
	1851,
	2765,
	1895,
	25,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194346,
	4194345,
	2568,
	1766,
	2653,
	1979,
	0);
INSERT INTO GD_GE
	VALUES (4194347,
	4194305,
	4194320,
	42);
INSERT INTO GD_CON
	VALUES (4194347,
	4194310,
	4194307,
	0);
INSERT INTO GD_CTXT
	VALUES (4194347,
	0,
	0,
	0,
	0,
	0,
	0,
	2482,
	1869,
	2623,
	1902,
	-67,
	12,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194348,
	4194347,
	2605,
	1979,
	2525,
	1771,
	0);
INSERT INTO GD_GE
	VALUES (4194349,
	4194305,
	4194321,
	42);
INSERT INTO GD_CON
	VALUES (4194349,
	4194309,
	4194307,
	0);
INSERT INTO GD_CTXT
	VALUES (4194349,
	0,
	0,
	0,
	0,
	0,
	0,
	2381,
	1496,
	2545,
	1552,
	40,
	14,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194350,
	4194349,
	2317,
	1371,
	2397,
	1627,
	0);
INSERT INTO GD_GE
	VALUES (4194351,
	4194305,
	4194322,
	42);
INSERT INTO GD_CON
	VALUES (4194351,
	4194307,
	4194309,
	0);
INSERT INTO GD_CTXT
	VALUES (4194351,
	0,
	0,
	0,
	0,
	0,
	0,
	2203,
	1435,
	2365,
	1468,
	-99,
	-47,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194352,
	4194351,
	2365,
	1627,
	2269,
	1371,
	0);
INSERT INTO O_OBJ
	VALUES (3145731,
	'Object',
	103,
	'O',
	'',
	3145734);
INSERT INTO O_NBATTR
	VALUES (3145751,
	3145731);
INSERT INTO O_BATTR
	VALUES (3145751,
	3145731);
INSERT INTO O_ATTR
	VALUES (3145751,
	3145731,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (3145752,
	3145731);
INSERT INTO O_BATTR
	VALUES (3145752,
	3145731);
INSERT INTO O_ATTR
	VALUES (3145752,
	3145731,
	3145751,
	'name',
	'',
	'',
	'name',
	0,
	524293);
INSERT INTO O_ID
	VALUES (0,
	3145731);
INSERT INTO O_OIDA
	VALUES (3145751,
	3145731,
	0);
INSERT INTO O_RTIDA
	VALUES (3145751,
	3145731,
	0,
	3145730,
	3145731);
INSERT INTO R_SIMP
	VALUES (3145729);
INSERT INTO R_REL
	VALUES (3145729,
	100,
	'',
	3145734);
INSERT INTO R_PART
	VALUES (3145730,
	3145729,
	3145729,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (3145730,
	3145729,
	3145729,
	1);
INSERT INTO R_OIR
	VALUES (3145730,
	3145729,
	3145729,
	0);
INSERT INTO R_FORM
	VALUES (3145729,
	3145729,
	3145730,
	1,
	0,
	'');
INSERT INTO R_RGO
	VALUES (3145729,
	3145729,
	3145730);
INSERT INTO R_OIR
	VALUES (3145729,
	3145729,
	3145730,
	0);
INSERT INTO R_SUBSUP
	VALUES (3145730);
INSERT INTO R_REL
	VALUES (3145730,
	101,
	'',
	3145734);
INSERT INTO R_SUPER
	VALUES (3145731,
	3145730,
	3145731);
INSERT INTO R_RTO
	VALUES (3145731,
	3145730,
	3145731,
	0);
INSERT INTO R_OIR
	VALUES (3145731,
	3145730,
	3145731,
	0);
INSERT INTO R_SUB
	VALUES (3145730,
	3145730,
	3145732);
INSERT INTO R_RGO
	VALUES (3145730,
	3145730,
	3145732);
INSERT INTO R_OIR
	VALUES (3145730,
	3145730,
	3145732,
	0);
INSERT INTO R_SUB
	VALUES (3145729,
	3145730,
	3145733);
INSERT INTO R_RGO
	VALUES (3145729,
	3145730,
	3145733);
INSERT INTO R_OIR
	VALUES (3145729,
	3145730,
	3145733,
	0);
INSERT INTO CA_ACC
	VALUES (3670017,
	3145734,
	3670023,
	0);
INSERT INTO CA_SMOA
	VALUES (3670017,
	3145730,
	0);
INSERT INTO CA_SMOAA
	VALUES (3670017,
	3145743,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (3670017,
	3145742,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (3670017,
	3145748,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (3670017,
	3145747,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (3670017,
	3145746,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (3670017,
	3145749,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (3670017,
	3145744,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (3670017,
	3145745,
	3145730);
INSERT INTO CA_COMM
	VALUES (4194305,
	3145734);
INSERT INTO CA_SMSMC
	VALUES (4194305,
	4194312,
	4194312,
	0);
INSERT INTO CA_SMSME
	VALUES (4194305,
	4194312,
	4194305);
INSERT INTO CA_ACC
	VALUES (4194305,
	3145734,
	4194312,
	0);
INSERT INTO CA_SMOA
	VALUES (4194305,
	3145730,
	0);
INSERT INTO CA_SMOAA
	VALUES (4194305,
	3145745,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (4194305,
	3145747,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (4194305,
	3145748,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (4194305,
	3145742,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (4194305,
	3145740,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (4194305,
	3145746,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (4194305,
	3145749,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (4194305,
	3145744,
	3145730);
INSERT INTO CA_SMOAA
	VALUES (4194305,
	3145743,
	3145730);
INSERT INTO GD_MD
	VALUES (3145764,
	5,
	3145734,
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
	VALUES (3145767,
	3145764,
	3145729,
	21);
INSERT INTO GD_SHP
	VALUES (3145767,
	2064,
	1264,
	2304,
	1552);
INSERT INTO GD_GE
	VALUES (3145768,
	3145764,
	3145730,
	21);
INSERT INTO GD_SHP
	VALUES (3145768,
	1648,
	1264,
	1908,
	1558);
INSERT INTO GD_GE
	VALUES (3145769,
	3145764,
	3145731,
	21);
INSERT INTO GD_SHP
	VALUES (3145769,
	1824,
	992,
	2144,
	1152);
INSERT INTO GD_GE
	VALUES (3145785,
	3145764,
	3145729,
	24);
INSERT INTO GD_CON
	VALUES (3145785,
	3145768,
	3145767,
	0);
INSERT INTO GD_CTXT
	VALUES (3145785,
	0,
	0,
	0,
	0,
	0,
	0,
	1968,
	1360,
	2018,
	1390,
	7,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145786,
	3145785,
	1908,
	1392,
	2064,
	1392,
	0);
INSERT INTO GD_GE
	VALUES (3145787,
	3145764,
	3145730,
	24);
INSERT INTO GD_CON
	VALUES (3145787,
	3145769,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (3145787,
	1802,
	1167,
	1988,
	1193,
	-125,
	10,
	0,
	0,
	0,
	0,
	0,
	0,
	2008,
	1162,
	2108,
	1192,
	81,
	-3);
INSERT INTO GD_LS
	VALUES (3145788,
	3145787,
	1968,
	1152,
	1968,
	1200,
	0);
INSERT INTO GD_GE
	VALUES (3145789,
	3145764,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (3145789,
	3145768,
	3145787,
	0);
INSERT INTO GD_CTXT
	VALUES (3145789,
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
	VALUES (3145790,
	3145789,
	1776,
	1264,
	1776,
	1200,
	0);
INSERT INTO GD_LS
	VALUES (3145791,
	3145789,
	1776,
	1200,
	1968,
	1200,
	3145790);
INSERT INTO GD_GE
	VALUES (3145792,
	3145764,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (3145792,
	3145767,
	3145787,
	0);
INSERT INTO GD_CTXT
	VALUES (3145792,
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
	VALUES (3145793,
	3145792,
	2176,
	1264,
	2176,
	1200,
	0);
INSERT INTO GD_LS
	VALUES (3145794,
	3145792,
	2176,
	1200,
	1968,
	1200,
	3145793);
INSERT INTO GD_MD
	VALUES (3145765,
	6,
	3145734,
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
	VALUES (3145770,
	3145765,
	3145730,
	21);
INSERT INTO GD_SHP
	VALUES (3145770,
	1700,
	1270,
	1892,
	1334);
INSERT INTO GD_GE
	VALUES (3145771,
	3145765,
	4194312,
	40);
INSERT INTO GD_SHP
	VALUES (3145771,
	1860,
	1362,
	2052,
	1426);
INSERT INTO GD_MD
	VALUES (3145766,
	7,
	3145734,
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
	VALUES (3145772,
	3145766,
	3145729,
	21);
INSERT INTO GD_SHP
	VALUES (3145772,
	2032,
	1296,
	2224,
	1360);
INSERT INTO GD_GE
	VALUES (3145773,
	3145766,
	4194312,
	40);
INSERT INTO GD_SHP
	VALUES (3145773,
	1860,
	1362,
	2052,
	1426);
INSERT INTO GD_GE
	VALUES (3145774,
	3145766,
	3145731,
	21);
INSERT INTO GD_SHP
	VALUES (3145774,
	1824,
	1024,
	2016,
	1088);
