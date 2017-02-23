-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (23473,
	'ex1',
	'',
	0,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	23473,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	23473,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	23473,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	23473,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	23473,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	23473,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	23473,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	23473,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	23473,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	23473,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	23473,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	23473,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	23473,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	23473,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	23473,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	23473,
	'inst_ref<Timer>',
	'');
INSERT INTO S_UDT
	VALUES (524305,
	524290,
	0);
INSERT INTO S_DT
	VALUES (524305,
	23473,
	'my_b',
	'');
INSERT INTO S_UDT
	VALUES (524306,
	524291,
	0);
INSERT INTO S_DT
	VALUES (524306,
	23473,
	'my_i',
	'');
INSERT INTO S_UDT
	VALUES (524307,
	524292,
	0);
INSERT INTO S_DT
	VALUES (524307,
	23473,
	'my_r',
	'');
INSERT INTO S_UDT
	VALUES (524308,
	524293,
	0);
INSERT INTO S_DT
	VALUES (524308,
	23473,
	'my_s',
	'');
INSERT INTO S_UDT
	VALUES (524309,
	524294,
	0);
INSERT INTO S_DT
	VALUES (524309,
	23473,
	'my_u',
	'');
INSERT INTO S_EE
	VALUES (524289,
	'Time',
	'',
	'TIM',
	23473);
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
	'',
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
	'',
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
	'',
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
	'',
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
	'',
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
	'',
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
	'',
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
	'timer_restart',
	'',
	1,
	524289,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524305,
	524300,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524306,
	524300,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524307,
	524300,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524301,
	524289,
	'timer_remaining_time',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524308,
	524301,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BRG
	VALUES (524302,
	524289,
	'timer_reset_time',
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
	'timer_add_time',
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
INSERT INTO S_BPARM
	VALUES (524312,
	524303,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524304,
	524289,
	'timer_cancel',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524313,
	524304,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_EE
	VALUES (524290,
	'Architecture',
	'',
	'ARCH',
	23473);
INSERT INTO S_BRG
	VALUES (524305,
	524290,
	'shutdown',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_EE
	VALUES (524291,
	'Logging',
	'',
	'LOG',
	23473);
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
	VALUES (524314,
	524306,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524307,
	524291,
	'LogFailure',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524315,
	524307,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524308,
	524291,
	'LogInfo',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524316,
	524308,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524309,
	524291,
	'LogDate',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524317,
	524309,
	'd',
	524302,
	0);
INSERT INTO S_BPARM
	VALUES (524318,
	524309,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524310,
	524291,
	'LogTime',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524319,
	524310,
	't',
	524303,
	0);
INSERT INTO S_BPARM
	VALUES (524320,
	524310,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524311,
	524291,
	'LogReal',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524321,
	524311,
	'r',
	524292,
	0);
INSERT INTO S_BPARM
	VALUES (524322,
	524311,
	'message',
	524293,
	0);
INSERT INTO GD_MD
	VALUES (524289,
	1,
	23473,
	1,
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
	VALUES (524331,
	524289,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524331,
	1920,
	1344,
	2080,
	1440);
INSERT INTO GD_GE
	VALUES (524332,
	524289,
	524289,
	12);
INSERT INTO GD_SHP
	VALUES (524332,
	2064,
	1568,
	2224,
	1664);
INSERT INTO GD_GE
	VALUES (524333,
	524289,
	524290,
	12);
INSERT INTO GD_SHP
	VALUES (524333,
	1792,
	1568,
	1936,
	1664);
INSERT INTO GD_GE
	VALUES (524334,
	524289,
	524291,
	12);
INSERT INTO GD_SHP
	VALUES (524334,
	1840,
	1472,
	2016,
	1568);
INSERT INTO S_SS
	VALUES (1048578,
	'ex',
	'',
	'',
	1,
	23473,
	1048578);
INSERT INTO O_OBJ
	VALUES (1048577,
	'ex init',
	1,
	'USERINIT',
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
	'init',
	0,
	'',
	'USERINIT1',
	'');
INSERT INTO SM_STATE
	VALUES (1572865,
	1572867,
	0,
	'ex init',
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
	'LOG::LogInfo(message:"a1test_exconfig: a1test_exconfig init") ;

// create preexisting instances
create object instance ev of EV;
assign ev.shutdown_count = 3;
create object instance et of ET;
assign et.btrue = TRUE;
assign et.et_id = 1;
create object instance one_inst of OI;
relate et to one_inst across R3;
create object instance mi1 of MI;
relate et to mi1 across R4;
create object instance mi2 of MI;
relate et to mi2 across R4;

create object instance etob1 of ETOB;
assign etob1.etob_id = 2;
assign etob1.r = 2.1718;
assign etob1.btrue = TRUE;
assign etob1.bfalse = FALSE;
assign etob1.s = "etob1 string";
bridge etob1.d = TIM::create_date(second:17,minute:7,hour:13,day:9,month:8,year:1991);
select any event_instance from instances of EV;
create event instance ev_temp of EV1:''Event Instance''(message:"etob1 event instance") to event_instance;
assign etob1.ev = ev_temp;
bridge etob1.tim = TIM::timer_start ( microseconds:2000000, event_inst:ev_temp);
relate et to etob1 across R1;
relate et to etob1 across R8;
create object instance etob2 of ETOB;
assign etob2.etob_id = 3;
assign etob2.r = 1.616;
assign etob2.btrue = TRUE;
assign etob2.bfalse = FALSE;
assign etob2.s = "etob2 string";
relate et to etob2 across R8;

create object instance etoc of ETOC;
assign etoc.etoc_id = 4;
assign etoc.r = 11.311;
assign etoc.btrue = TRUE;
assign etoc.bfalse = FALSE;
assign etoc.s = "etoc string";
bridge etoc.d = TIM::create_date(second:10,minute:0,hour:0,day:13,month:12,year:1997);
create event instance ev_temp2 of EV1:''Event Instance''(message:"etoc event instance") to event_instance;
assign etoc.ev = ev_temp2;
bridge etoc.tim = TIM::timer_start ( microseconds:2000000, event_inst:ev_temp2);

relate etob1 to etoc across R6;
create object instance etoc2 of ETOC;
assign etoc2.etoc_id = 5;
assign etoc2.r = 17.311;
assign etoc2.btrue = TRUE;
assign etoc2.bfalse = FALSE;
assign etoc2.s = "etoc2 string";
relate etob2 to etoc2 across R6;

create object instance bet of BET;
assign bet.btrue = TRUE;
assign bet.bfalse = FALSE;
create object instance obet1 of OBET;
assign obet1.btrue = TRUE;
assign obet1.bfalse = FALSE;
relate bet to obet1 across R9;
relate bet to obet1 across R10;
create object instance obet2 of OBET;
assign obet2.btrue = TRUE;
assign obet2.bfalse = FALSE;
relate bet to obet2 across R10;
create object instance tbet1 of TBET;
assign tbet1.btrue = TRUE;
assign tbet1.bfalse = FALSE;
relate tbet1 to obet1 across R11;
create object instance tbet2 of TBET;
assign tbet2.btrue = TRUE;
assign tbet2.bfalse = FALSE;
relate tbet2 to obet2 across R11;

create object instance ubt of UBT;
assign ubt.btrue = TRUE;
assign ubt.bfalse = FALSE;

create object instance uobt1 of UOBT;
assign uobt1.btrue = TRUE;
assign uobt1.bfalse = FALSE;
relate ubt to uobt1 across R12;
relate ubt to uobt1 across R13;

create object instance uobt2 of UOBT;
assign uobt2.btrue = TRUE;
assign uobt2.bfalse = FALSE;
relate ubt to uobt2 across R13;

create object instance utbt1 of UTBT;
assign utbt1.btrue = TRUE;
assign utbt1.bfalse = FALSE;
relate utbt1 to uobt1 across R14;

create object instance utbt2 of UTBT;
assign utbt2.btrue = TRUE;
assign utbt2.bfalse = FALSE;
relate utbt2 to uobt2 across R14;


// no instances of NOI should be created

// start the expression test
generate ET1:''Start Expression Test''() to et;

generate BET1:''Start and test''( ttrue: true, tfalse: false) to bet;
generate UBT1:''Start and test''( ttrue: true, tfalse: false) to ubt;
',
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
	1744,
	1248,
	2240,
	1728);
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
	2178,
	1171,
	2323,
	1204,
	-109,
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
	2240,
	1296,
	2320,
	1296,
	0);
INSERT INTO GD_LS
	VALUES (1572869,
	1572867,
	2320,
	1296,
	2320,
	1200,
	1572868);
INSERT INTO GD_LS
	VALUES (1572870,
	1572867,
	2320,
	1200,
	2176,
	1200,
	1572869);
INSERT INTO GD_LS
	VALUES (1572871,
	1572867,
	2176,
	1200,
	2176,
	1248,
	1572870);
INSERT INTO O_OBJ
	VALUES (1048578,
	'Expression Test',
	3,
	'ET',
	'',
	1048578);
INSERT INTO O_TFR
	VALUES (1048577,
	1048578,
	'no_parm_ret_void',
	'',
	524289,
	0,
	'LOG::LogFailure(message:"Operation ET::no_parm_ret_void should not have been translated.");
',
	1);
INSERT INTO O_TFR
	VALUES (1048578,
	1048578,
	'i_parm_ret_i',
	'',
	524291,
	0,
	'LOG::LogFailure(message:"Operation ET::i_parm_ret_i should not have been translated.");
return param.i;',
	1);
INSERT INTO O_TPARM
	VALUES (1048577,
	1048578,
	'i',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (1048579,
	1048578,
	'r_parm_ret_r',
	'',
	524292,
	0,
	'LOG::LogFailure(message:"Operation ET::r_parm_ret_r should not have been translated.");
return param.r;',
	1);
INSERT INTO O_TPARM
	VALUES (1048578,
	1048579,
	'r',
	524292,
	0);
INSERT INTO O_TFR
	VALUES (1048580,
	1048578,
	'b_parm_ret_b',
	'',
	524290,
	0,
	'LOG::LogFailure(message:"Operation ET::b_parm_ret_b should not have been translated.");
return param.b;',
	1);
INSERT INTO O_TPARM
	VALUES (1048579,
	1048580,
	'b',
	524290,
	0);
INSERT INTO O_TFR
	VALUES (1048581,
	1048578,
	's_parm_ret_s',
	'',
	524293,
	0,
	'LOG::LogFailure(message:"Operation ET::s_parm_ret_s should not have been translated.");
return param.s;',
	1);
INSERT INTO O_TPARM
	VALUES (1048580,
	1048581,
	's',
	524293,
	0);
INSERT INTO O_TFR
	VALUES (1048582,
	1048578,
	'u_parm_ret_u',
	'',
	524294,
	0,
	'LOG::LogFailure(message:"Operation ET::u_parm_ret_u should not have been translated.");
return param.u;',
	1);
INSERT INTO O_TPARM
	VALUES (1048581,
	1048582,
	'u',
	524294,
	0);
INSERT INTO O_TFR
	VALUES (1048583,
	1048578,
	'd_parm_ret_d',
	'',
	524302,
	0,
	'LOG::LogFailure(message:"Operation ET::d_parm_ret_d should not have been translated.");
return param.d;',
	1);
INSERT INTO O_TPARM
	VALUES (1048582,
	1048583,
	'd',
	524302,
	0);
INSERT INTO O_TFR
	VALUES (1048584,
	1048578,
	't_parm_ret_t',
	'',
	524303,
	0,
	'LOG::LogFailure(message:"Operation ET::t_parm_ret_t should not have been translated.");
return param.t;',
	1);
INSERT INTO O_TPARM
	VALUES (1048583,
	1048584,
	't',
	524303,
	0);
INSERT INTO O_TFR
	VALUES (1048585,
	1048578,
	'i_parm_ret_void',
	'',
	524289,
	0,
	'LOG::LogFailure(message:"Operation ET::i_parm_ret_void should not have been translated.");',
	1);
INSERT INTO O_TPARM
	VALUES (1048584,
	1048585,
	'i',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (1048586,
	1048578,
	'waitOneSec',
	'',
	524289,
	0,
	'',
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
	'et_id',
	'',
	'',
	'et_id',
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
	'bfalse',
	'',
	'',
	'bfalse',
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
	'btrue',
	'',
	'',
	'btrue',
	0,
	524290);
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
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048583,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048583,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048583,
	1048578,
	1048582,
	'r',
	'',
	'',
	'r',
	0,
	524292);
INSERT INTO O_NBATTR
	VALUES (1048584,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048584,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048584,
	1048578,
	1048583,
	'd',
	'',
	'',
	'd',
	0,
	524302);
INSERT INTO O_NBATTR
	VALUES (1048585,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048585,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048585,
	1048578,
	1048584,
	'u',
	'',
	'',
	'u',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048586,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048586,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048586,
	1048578,
	1048585,
	's',
	'',
	'',
	's',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048587,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048587,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048587,
	1048578,
	1048586,
	't',
	'',
	'',
	't',
	0,
	524303);
INSERT INTO O_NBATTR
	VALUES (1048588,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048588,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048588,
	1048578,
	1048587,
	'tim',
	'',
	'',
	'tim',
	0,
	524304);
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
	'ev',
	'',
	'',
	'ev',
	0,
	524299);
INSERT INTO O_NBATTR
	VALUES (1048590,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048590,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048590,
	1048578,
	1048589,
	'log_id',
	'',
	'',
	'log_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048591,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048591,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048591,
	1048578,
	1048590,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_NBATTR
	VALUES (1048592,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048592,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048592,
	1048578,
	1048591,
	'my_b',
	'',
	'',
	'my_b',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (1048593,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048593,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048593,
	1048578,
	1048592,
	'my_i',
	'',
	'',
	'my_i',
	0,
	524306);
INSERT INTO O_NBATTR
	VALUES (1048594,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048594,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048594,
	1048578,
	1048593,
	'my_r',
	'',
	'',
	'my_r',
	0,
	524307);
INSERT INTO O_NBATTR
	VALUES (1048595,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048595,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048595,
	1048578,
	1048594,
	'my_s',
	'',
	'',
	'my_s',
	0,
	524308);
INSERT INTO O_NBATTR
	VALUES (1048596,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048596,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048596,
	1048578,
	1048595,
	'my_u',
	'',
	'',
	'my_u',
	0,
	524309);
INSERT INTO O_REF
	VALUES (1048578,
	1048583,
	0,
	1048620,
	1048577,
	1048578,
	1048577,
	1048597,
	1048577,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048597,
	1048578,
	1048620,
	1048583,
	1);
INSERT INTO O_ATTR
	VALUES (1048597,
	1048578,
	1048596,
	'etob_id',
	'',
	'',
	'etob_id',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1048578,
	1048583,
	0,
	1048621,
	1048577,
	1048578,
	1048577,
	1048598,
	1048578,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048598,
	1048578,
	1048621,
	1048583,
	1);
INSERT INTO O_ATTR
	VALUES (1048598,
	1048578,
	1048597,
	'r1btrue',
	'',
	'r1',
	'btrue',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048578,
	1048583,
	0,
	1048622,
	1048577,
	1048578,
	1048577,
	1048599,
	1048579,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048599,
	1048578,
	1048622,
	1048583,
	1);
INSERT INTO O_ATTR
	VALUES (1048599,
	1048578,
	1048598,
	'r1bfalse',
	'',
	'r1',
	'bfalse',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048578,
	1048583,
	0,
	1048623,
	1048577,
	1048578,
	1048577,
	1048600,
	1048580,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048600,
	1048578,
	1048623,
	1048583,
	1);
INSERT INTO O_ATTR
	VALUES (1048600,
	1048578,
	1048599,
	'r1r',
	'',
	'r1',
	'r',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048578,
	1048583,
	0,
	1048624,
	1048577,
	1048578,
	1048577,
	1048601,
	1048581,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048601,
	1048578,
	1048624,
	1048583,
	1);
INSERT INTO O_ATTR
	VALUES (1048601,
	1048578,
	1048600,
	'r1s',
	'',
	'r1',
	's',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048578,
	1048583,
	0,
	1048625,
	1048577,
	1048578,
	1048577,
	1048602,
	1048582,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048602,
	1048578,
	1048625,
	1048583,
	1);
INSERT INTO O_ATTR
	VALUES (1048602,
	1048578,
	1048601,
	'r1u',
	'',
	'r1',
	'u',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048578,
	1048583,
	0,
	1048626,
	1048577,
	1048578,
	1048577,
	1048603,
	1048583,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048603,
	1048578,
	1048626,
	1048583,
	1);
INSERT INTO O_ATTR
	VALUES (1048603,
	1048578,
	1048602,
	'r1d',
	'',
	'r1',
	'd',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048578,
	1048583,
	0,
	1048627,
	1048577,
	1048578,
	1048577,
	1048604,
	1048584,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048604,
	1048578,
	1048627,
	1048583,
	1);
INSERT INTO O_ATTR
	VALUES (1048604,
	1048578,
	1048603,
	'r1t',
	'',
	'r1',
	't',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048578,
	1048583,
	0,
	1048628,
	1048577,
	1048578,
	1048577,
	1048605,
	1048585,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048605,
	1048578,
	1048628,
	1048583,
	1);
INSERT INTO O_ATTR
	VALUES (1048605,
	1048578,
	1048604,
	'r1tim',
	'',
	'r1',
	'tim',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048578,
	1048583,
	0,
	1048629,
	1048577,
	1048578,
	1048577,
	1048606,
	1048586,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048606,
	1048578,
	1048629,
	1048583,
	1);
INSERT INTO O_ATTR
	VALUES (1048606,
	1048578,
	1048605,
	'r1ev',
	'',
	'r1',
	'ev',
	1,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048578);
INSERT INTO O_OIDA
	VALUES (1048579,
	1048578,
	0);
INSERT INTO O_RTIDA
	VALUES (1048579,
	1048578,
	0,
	1048578,
	1048579);
INSERT INTO O_RTIDA
	VALUES (1048579,
	1048578,
	0,
	1048579,
	1048581);
INSERT INTO O_RTIDA
	VALUES (1048579,
	1048578,
	0,
	1048580,
	1048583);
INSERT INTO O_RTIDA
	VALUES (1048579,
	1048578,
	0,
	1048581,
	1048585);
INSERT INTO O_RTIDA
	VALUES (1048579,
	1048578,
	0,
	1048583,
	1048589);
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
	VALUES (2097153,
	2097156,
	'i',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (2097154,
	2097156,
	'b',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (2097155,
	2097156,
	'r',
	'',
	524292);
INSERT INTO SM_EVTDI
	VALUES (2097156,
	2097156,
	's',
	'',
	524293);
INSERT INTO SM_EVTDI
	VALUES (2097157,
	2097156,
	'u',
	'',
	524294);
INSERT INTO SM_EVTDI
	VALUES (2097158,
	2097156,
	'my_b',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (2097159,
	2097156,
	'my_i',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (2097160,
	2097156,
	'my_r',
	'',
	524292);
INSERT INTO SM_EVTDI
	VALUES (2097161,
	2097156,
	'my_s',
	'',
	524293);
INSERT INTO SM_EVTDI
	VALUES (2097162,
	2097156,
	'my_u',
	'',
	524294);
INSERT INTO SM_EVTDI
	VALUES (2097163,
	2097156,
	'tfalse',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (2097164,
	2097156,
	'ttrue',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (2097165,
	2097156,
	'tint1',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (2097166,
	2097156,
	'tint2',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (2097167,
	2097156,
	'treal1',
	'',
	524292);
INSERT INTO SM_EVTDI
	VALUES (2097168,
	2097156,
	'treal2',
	'',
	524292);
INSERT INTO SM_EVTDI
	VALUES (2097169,
	2097156,
	'd',
	'',
	524302);
INSERT INTO SM_EVTDI
	VALUES (2097170,
	2097156,
	'ts',
	'',
	524303);
INSERT INTO SM_EVTDI
	VALUES (2097171,
	2097156,
	'tim',
	'',
	524304);
INSERT INTO SM_EVTDI
	VALUES (2097172,
	2097156,
	'ev',
	'',
	524299);
INSERT INTO SM_EVTDI
	VALUES (21988601,
	2097156,
	'i',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (169906934,
	2097156,
	'b',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (92653333,
	2097156,
	'r',
	'',
	524292);
INSERT INTO SM_EVTDI
	VALUES (171122880,
	2097156,
	's',
	'',
	524293);
INSERT INTO SM_EVTDI
	VALUES (28050817,
	2097156,
	'u',
	'',
	524294);
INSERT INTO SM_EVTDI
	VALUES (231119511,
	2097156,
	'tfalse',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (21411756,
	2097156,
	'tfalse',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (163222509,
	2097156,
	'tfalse',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (103198742,
	2097156,
	'ttrue',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (109306853,
	2097156,
	'ttrue',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (159206193,
	2097156,
	'ttrue',
	'',
	524290);
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
	'Start Expression Test',
	0,
	'',
	'ET1',
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
	'Run F3 Binary Ops',
	0,
	'',
	'ET2',
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
	'Run F15 Values',
	0,
	'',
	'ET3',
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
	'Run F16 Transformers',
	0,
	'',
	'ET4',
	'');
INSERT INTO SM_LEVT
	VALUES (2097157,
	2097156,
	0);
INSERT INTO SM_SEVT
	VALUES (2097157,
	2097156,
	0);
INSERT INTO SM_EVT
	VALUES (2097157,
	2097156,
	0,
	5,
	'Run F17 Timestamp and F18 Date Operators',
	0,
	'',
	'ET5',
	'');
INSERT INTO SM_LEVT
	VALUES (2097158,
	2097156,
	0);
INSERT INTO SM_SEVT
	VALUES (2097158,
	2097156,
	0);
INSERT INTO SM_EVT
	VALUES (2097158,
	2097156,
	0,
	6,
	'Run F1 and F2 for User Defined Types',
	0,
	'',
	'ET6',
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
	'Run F3 through F14 for User Defined Types',
	0,
	'',
	'ET7',
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
	8,
	'Run F15 for User Defined Types',
	0,
	'',
	'ET8',
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
	9,
	'Shut Down',
	0,
	'',
	'ET9',
	'');
INSERT INTO SM_LEVT
	VALUES (2097162,
	2097156,
	0);
INSERT INTO SM_SEVT
	VALUES (2097162,
	2097156,
	0);
INSERT INTO SM_EVT
	VALUES (2097162,
	2097156,
	0,
	10,
	'Start not_empty test',
	0,
	'',
	'ET10',
	'');
INSERT INTO SM_LEVT
	VALUES (2097163,
	2097156,
	0);
INSERT INTO SM_SEVT
	VALUES (2097163,
	2097156,
	0);
INSERT INTO SM_EVT
	VALUES (2097163,
	2097156,
	0,
	11,
	'Start cardinality test',
	0,
	'',
	'ET11',
	'');
INSERT INTO SM_LEVT
	VALUES (2097164,
	2097156,
	0);
INSERT INTO SM_SEVT
	VALUES (2097164,
	2097156,
	0);
INSERT INTO SM_EVT
	VALUES (2097164,
	2097156,
	0,
	12,
	'Start empty test',
	0,
	'',
	'ET12',
	'');
INSERT INTO SM_LEVT
	VALUES (2097165,
	2097156,
	0);
INSERT INTO SM_SEVT
	VALUES (2097165,
	2097156,
	0);
INSERT INTO SM_EVT
	VALUES (2097165,
	2097156,
	0,
	13,
	'Start not test',
	0,
	'',
	'ET13',
	'');
INSERT INTO SM_LEVT
	VALUES (2097166,
	2097156,
	0);
INSERT INTO SM_SEVT
	VALUES (2097166,
	2097156,
	0);
INSERT INTO SM_EVT
	VALUES (2097166,
	2097156,
	0,
	14,
	'Start subtract test',
	0,
	'',
	'ET14',
	'');
INSERT INTO SM_LEVT
	VALUES (2097167,
	2097156,
	0);
INSERT INTO SM_SEVT
	VALUES (2097167,
	2097156,
	0);
INSERT INTO SM_EVT
	VALUES (2097167,
	2097156,
	0,
	15,
	'Start assign test',
	0,
	'',
	'ET15',
	'');
INSERT INTO SM_LEVT
	VALUES (2097168,
	2097156,
	0);
INSERT INTO SM_SEVT
	VALUES (2097168,
	2097156,
	0);
INSERT INTO SM_EVT
	VALUES (2097168,
	2097156,
	0,
	16,
	'Start and test',
	0,
	'',
	'ET16',
	'');
INSERT INTO SM_LEVT
	VALUES (2097169,
	2097156,
	0);
INSERT INTO SM_SEVT
	VALUES (2097169,
	2097156,
	0);
INSERT INTO SM_EVT
	VALUES (2097169,
	2097156,
	0,
	17,
	'Start or test',
	0,
	'',
	'ET17',
	'');
INSERT INTO SM_STATE
	VALUES (2097153,
	2097156,
	0,
	'Starting Expression Test F1 and F2',
	1,
	0);
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
INSERT INTO SM_CH
	VALUES (2097153,
	2097155,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097155,
	2097156,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (2097153,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097157,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097153,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097158,
	2097156,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (2097153,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097162,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097153,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097163,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097153,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097164,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097153,
	2097165,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097153,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097166,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097153,
	2097167,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097167,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097153,
	2097168,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097168,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097153,
	2097169,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097169,
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
	'LOG::LogInfo(message:"Starting Expression Test F1 and F2") ;

//BEGIN EX.F.1
//check defaults

if (self.bfalse != FALSE)
  LOG::LogFailure(message:"EX.F.1: b default") ;
else
  LOG::LogSuccess(message:"EX.F.1: b default") ;
end if;

if (self.i != 0)
  LOG::LogFailure(message:"EX.F.1: i default") ;
else 
   LOG::LogSuccess(message:"EX.F.1: i default") ; 
end if;

//generate LOGREAL1:''Log Real''(r:self.r,message:"EX.F.1: r default") real;

if (self.s != "")
  LOG::LogFailure(message:"EX.F.1: s default") ;
else
  LOG::LogSuccess(message:"EX.F.1: s default") ;
end if;

//END EX.F.1

//BEGIN EX.F.2
//check integer assign

assign self.i = 10;
if (self.i == 10)
  LOG::LogSuccess(message:"EX.F.2: self.i assign") ;  
else
  LOG::LogFailure(message:"EX.F.2: self.i assign") ;  
end if;

//check real assign
assign self.r = 3.14;
//generate LOGREAL1:''Log Real''(r:3.14,message:"EX.F.2: r assigned to") real;
//generate LOGREAL1:''Log Real''(r:self.r,message:"EX.F.2: r assigned is") real;

//check bool assign
assign self.bfalse = TRUE;
if (self.bfalse == TRUE)
  LOG::LogSuccess(message:"EX.F.2: self.b assign") ;  
else
  LOG::LogFailure(message:"EX.F.2: self.b assign") ;  
end if;
assign self.bfalse = FALSE;
if (self.bfalse == FALSE)
  LOG::LogSuccess(message:"EX.F.2: self.b assign") ;  
else
  LOG::LogFailure(message:"EX.F.2: self.b assign") ;  
end if;

//check string assign
assign self.s = "String";
if (self.s == "String")
  LOG::LogSuccess(message:"EX.F.2: self.s assign") ;  
else
  LOG::LogFailure(message:"EX.F.2: self.s assign") ;  
end if;

LOG::LogInfo(message:"Finished Expression Test F1 and F2") ;

//generate event to commence Operations test
generate ET13:''Start not test''(tfalse:FALSE, ttrue:TRUE) to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097154,
	2097156,
	0,
	'Run F3 through F14 Binary Operations',
	2,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_SEME
	VALUES (2097154,
	2097155,
	2097156,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (2097154,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097157,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097154,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097158,
	2097156,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (2097154,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097162,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097154,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097163,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097154,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097164,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097154,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097165,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097154,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097166,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097154,
	2097167,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097167,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097154,
	2097168,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097168,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097154,
	2097169,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097169,
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
	'
LOG::LogInfo(message:"Running F3 through F14 Binary Operations") ;

//BEGIN EX.F.3
// boolean and, or, ==, and !=

//and
assign b1 = TRUE;
assign b2 = FALSE;
if (b1 and b2)
  LOG::LogFailure(message:"EX.F.3 boolean and") ;
else
  LOG::LogSuccess(message:"EX.F.3 boolean and") ;
end if;

// or
if (b1 or b2)
  LOG::LogSuccess(message:"EX.F.3 boolean or") ;
else
  LOG::LogFailure(message:"EX.F.3 boolean or") ;
end if;

//==
if (b1 == b2)
  LOG::LogFailure(message:"EX.F.3 boolean ==") ;
else
  LOG::LogSuccess(message:"EX.F.3 boolean ==") ;
end if;

//!=
if (b1 != b2)
  LOG::LogSuccess(message:"EX.F.3 boolean ==") ;
else
  LOG::LogFailure(message:"EX.F.3 boolean ==") ;
end if;
//END EX.F.3

//BEGIN EX.F.4
// integer + - * / %

// +
assign i1 = 5;
assign i2 = -6;
assign i3 = i1 + i2;
if (i3 == -1)
  LOG::LogSuccess(message:"EX.F.4 integer +") ;
else
  LOG::LogFailure(message:"EX.F.4 integer +") ;
end if;
//- 
assign i4 = i1 - i2;
if (i4 == 11)
  LOG::LogSuccess(message:"EX.F.4 integer -") ;
else
  LOG::LogFailure(message:"EX.F.4 integer -") ;
end if;
// *
assign i5 = i1*i2;
if (i5 == -30)
  LOG::LogSuccess(message:"EX.F.4 integer *") ;
else
  LOG::LogFailure(message:"EX.F.4 integer *") ;
end if;
// /
assign i6 = 10;
assign i7 = i6/i1;
if (i7 == 2)
  LOG::LogSuccess(message:"EX.F.4 integer /") ;
else
  LOG::LogFailure(message:"EX.F.4 integer /") ;
end if;
// %
assign i8 = 13;
assign i9 = i8%i1;
if (i9 == 3)
  LOG::LogSuccess(message:"EX.F.4 integer %") ;
else
  LOG::LogFailure(message:"EX.F.4 integer %") ;
end if;
//END EX.F.4

//BEGIN EX.F.5
// integer  < <= == != >= >

assign j1 = 12;
assign j2 = 6;
assign j3 = 6;
// <
if (j2 < j1)
  LOG::LogSuccess(message:"EX.F.5 integer <") ;
else
  LOG::LogFailure(message:"EX.F.5 integer <") ;
end if;
// <=
if (j2 <= j3)
  LOG::LogSuccess(message:"EX.F.5 integer <=") ;
else
  LOG::LogFailure(message:"EX.F.5 integer <=") ;
end if;
//==
if (j2 == j3)
  LOG::LogSuccess(message:"EX.F.5 integer ==") ;
else
  LOG::LogFailure(message:"EX.F.5 integer ==") ;
end if;
//!=
if (j2 != j1)
  LOG::LogSuccess(message:"EX.F.5 integer !=") ;
else
  LOG::LogFailure(message:"EX.F.5 integer !=") ;
end if;
//>=
if (j1 >= j2)
  LOG::LogSuccess(message:"EX.F.5 integer >=") ;
else
  LOG::LogFailure(message:"EX.F.5 integer >=") ;
end if;
//>
if (j1 > j2)
  LOG::LogSuccess(message:"EX.F.5 integer >") ;
else
  LOG::LogFailure(message:"EX.F.5 integer >") ;
end if;
//END EX.F.5

//BEGIN EX.F.6
//  + - * /  with int as left operand, real as right operand, and real as result
// +
assign i1 = 5;
assign r1 = -6.0;
assign r3 = i1 + r1;
LOG::LogReal(r:r3,message:"EX.F.6 int real + : r should be -1.0") ;

//- 
assign r4 = i1 - r1;
LOG::LogReal(r:r4,message:"EX.F.6 int real - : r should be 11.0") ;

// *
assign r5 = i1*r1;
LOG::LogReal(r:r5,message:"EX.F.6 int real * : r should be -30.0") ;

// /
assign i10 = 4;
assign r2 = 2.0;
assign r6 = i10/r2;
LOG::LogReal(r:r6,message:"EX.F.6 int real / : r should be 2.0") ;

//END EX.F.6

//BEGIN EX.F.7
//  < <= == != >= > int as left operand, real as right, and bool result

assign k1 = 12;
assign s1 = 6.0;
assign s2 = 12.0;
assign k2 = 6;

// <
if (k2 < s2)
  LOG::LogSuccess(message:"EX.F.7 int real <") ;
else
  LOG::LogFailure(message:"EX.F.7 int real <") ;
end if;

// <=
if (k2 <= s1)
  LOG::LogSuccess(message:"EX.F.7 int real <=") ;
else
  LOG::LogFailure(message:"EX.F.7 int real <=") ;
end if;

//==
if (k2 == s1)
  LOG::LogInfo(message:"EX.F.7 int real ==") ;
else
  LOG::LogInfo(message:"EX.F.7 int real ==") ;
end if;

//!=
if (k1 != s1)
  LOG::LogInfo(message:"EX.F.7 int real !=") ;
else
  LOG::LogInfo(message:"EX.F.7 int real !=") ;
end if;

//>=
if (k1 >= s1)
  LOG::LogSuccess(message:"EX.F.7 int real >=") ;
else
  LOG::LogFailure(message:"EX.F.7 int real >=") ;
end if;
//>
if (k1 > s1)
  LOG::LogSuccess(message:"EX.F.7 int real >") ;
else
  LOG::LogFailure(message:"EX.F.7 int real >") ;
end if;
//END EX.F.7

//BEGIN EX.F.8
//  + - * /  with real as left operand, int as right operand, and real as result

// +
assign i1 = 5;
assign r1 = -6.0;
assign r3 = r1 + i1;
LOG::LogReal(r:r3,message:"EX.F.8 real int  + : r should be -1.0") ;

//- 
assign r4 = r1 - i1;
LOG::LogReal(r:r4,message:"EX.F.8 real int - : r should be -11.0") ;

// *
assign r5 = r1*i1;
LOG::LogReal(r:r5,message:"EX.F.8 real int * : r should be -30.0") ;

// /
assign i10 = 4;
assign r2 = 2.0;
assign r6 = r2/i10;
LOG::LogReal(r:r6,message:"EX.F.8 real int  / : r should be 0.5") ;

//END EX.F.8

//BEGIN EX.F.9
//  < <= == != >= > real as left operand, int as right, and bool result

assign s1 = 12.0;
assign k1 = 6;
assign k2 = 12;
assign s2 = 6.0;

// <
if (s2 < k2)
  LOG::LogSuccess(message:"EX.F.9 real int <") ;
else
  LOG::LogFailure(message:"EX.F.9 real int <") ;
end if;

// <=
if (s1 <= k2)
  LOG::LogInfo(message:"EX.F.9 real int <=") ;
else
  LOG::LogInfo(message:"EX.F.9 real int <=") ;
end if;

//==
if (s2 == k1)
  LOG::LogInfo(message:"EX.F.9 real int ==") ;
else
  LOG::LogInfo(message:"EX.F.9 real int ==") ;
end if;

//!=
if (s1 != k1)
  LOG::LogSuccess(message:"EX.F.9 real int !=") ;
else
  LOG::LogFailure(message:"EX.F.9 real int !=") ;
end if;

//>=
if (s1 >= k1)
  LOG::LogSuccess(message:"EX.F.9 real int >=") ;
else
  LOG::LogFailure(message:"EX.F.9 real int >=") ;
end if;

//>
if (s1 > k1)
  LOG::LogSuccess(message:"EX.F.9 real int >") ;
else
  LOG::LogFailure(message:"EX.F.9 real int >") ;
end if;

//END EX.F.9

//BEGIN EX.F.10
// real + - * / 

// +
assign r1 = 5.0;
assign r2 = -6.0;
assign r3 = r1 + r2;
LOG::LogReal(r:r3,message:"EX.F.10 real real + : r should be -1.0") ;

//- 
assign r4 = r1 - r2;
LOG::LogReal(r:r4,message:"EX.F.10 real real - : r should be 11.0") ;

// *
assign r5 = r1*r2;
LOG::LogReal(r:r5,message:"EX.F.10 real real * : r should be -30.0") ;

// /
assign r6 = 10.0;
assign r7 = r6/r1;
LOG::LogReal(r:r7,message:"EX.F.10 real real / : r should be 2.0") ;

//END EX.F.10


//BEGIN EX.F.11
// real  < <= == != >= >

assign r1 = 12.0;
assign r2 = 6.0;
assign r3 = 6.0;
// <
if (r2 < r1)
  LOG::LogSuccess(message:"EX.F.11 real <") ;
else
  LOG::LogFailure(message:"EX.F.11 real <") ;
end if;
// <=
if (r2 <= r1)
  LOG::LogSuccess(message:"EX.F.11 real <=") ;
else
  LOG::LogFailure(message:"EX.F.11 real <=") ;
end if;
//==
if (r2 == r3)
  LOG::LogInfo(message:"EX.F.11 real ==") ;
else
  LOG::LogInfo(message:"EX.F.11 real ==") ;
end if;
//!=
if (r2 != r1)
  LOG::LogSuccess(message:"EX.F.11 real !=") ;
else
  LOG::LogFailure(message:"EX.F.11 real !=") ;
end if;
//>=
if (r1 >= r2)
  LOG::LogSuccess(message:"EX.F.11 real >=") ;
else
  LOG::LogFailure(message:"EX.F.11 real >=") ;
end if;
//>
if (r1 > r2)
  LOG::LogSuccess(message:"EX.F.11 real >") ;
else
  LOG::LogFailure(message:"EX.F.11 real >") ;
end if;
//END EX.F.11

//BEGIN EX.F.12
// string string +
assign str1 = "Hello";
assign str2 = "World";
assign str3 = str1+str2;
if (str3 == "HelloWorld")
  LOG::LogSuccess(message:"EX.F.12 string +") ;
else
  LOG::LogFailure(message:"EX.F.12 string +") ;
end if;
//END EX.F.12

//BEGIN EX.F.13
//string < <= == != >= >
assign str1 = "a";
assign str2 = "b";
assign str3 = "b";
// <
if (str1 < str2)
  LOG::LogSuccess(message:"EX.F.13 string <") ;
else
  LOG::LogFailure(message:"EX.F.13 string <") ;
end if;
// <=
if (str2 <= str3)
  LOG::LogSuccess(message:"EX.F.13 string <=") ;
else
  LOG::LogFailure(message:"EX.F.13 string <=") ;
end if;
// ==
if (str3 == str2)
  LOG::LogSuccess(message:"EX.F.13 string ==") ;
else
  LOG::LogFailure(message:"EX.F.13 string ==") ;
end if;
// !=
if (str3 != str1)
  LOG::LogSuccess(message:"EX.F.13 string !=") ;
else
  LOG::LogFailure(message:"EX.F.13 string !=") ;
end if;
// >=
if (str3 >= str1)
  LOG::LogSuccess(message:"EX.F.13 string >=") ;
else
  LOG::LogFailure(message:"EX.F.13 string >=") ;
end if;
// >
if (str3 > str1)
  LOG::LogSuccess(message:"EX.F.13 string >") ;
else
  LOG::LogFailure(message:"EX.F.13 string >") ;
end if;
if (str3 < "c")
  LOG::LogSuccess(message:"EX.F.13 string < quoted") ;
else
  LOG::LogFailure(message:"EX.F.13 string < quoted") ;
end if;
//END EX.F.13

//BEGIN EX.F.14
// unique_id == !=
assign u = self.et_id;
if (u == self.et_id)
  LOG::LogSuccess(message:"EX.F.14 unique ==") ;
else
  LOG::LogFailure(message:"EX.F.14 unique ==") ;
end if;

select any oi from instances of OI;
if (self.u != oi.oi_id)
  LOG::LogSuccess(message:"EX.F.14 unique !=") ;
else
  LOG::LogFailure(message:"EX.F.14 unique !=") ;
end if;
//END EX.F.14

LOG::LogInfo(message:"Finished F3 through F14 Binary Operations") ;

generate ET3:''Run F15 Values''(b:TRUE) to self;
',
	'');
INSERT INTO SM_STATE
	VALUES (2097155,
	2097156,
	0,
	'Run F15 values',
	3,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (2097155,
	2097155,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097155,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097155,
	2097156,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097155,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097157,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097155,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097158,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097155,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097159,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097155,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097160,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097155,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097161,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097155,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097162,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097155,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097163,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097155,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097164,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097155,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097165,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097155,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097166,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097155,
	2097167,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097167,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097155,
	2097168,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097168,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097155,
	2097169,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097169,
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
	'// Test all of the data items.
// Data items can be literals, object attributes, received event data items, 
// and transient variables.

LOG::LogInfo(message:"Running F15 values") ;

// Literals and Transient

assign str1 = "Hello";
if (str1 == "Hello")
  LOG::LogSuccess(message:"EX.F.15 left transient, right literal") ;
else 
  LOG::LogFailure(message:"EX.F.15 left transient, right literal") ;
end if;

if ("Hello" == str1)
  LOG::LogSuccess(message:"EX.F.15 left literal, right transient") ;
else 
  LOG::LogFailure(message:"EX.F.15 left literal, right transient") ;
end if;

// Object Attributes

assign self.i = 23;
if (self.i  == 23)
  LOG::LogSuccess(message:"EX.F.15 left self.attr, right literal") ;
else 
  LOG::LogFailure(message:"EX.F.15 left self.attr, right literal") ;
end if;

if (23== self.i)
  LOG::LogSuccess(message:"EX.F.15 left literal, right self.attr") ;
else 
  LOG::LogFailure(message:"EX.F.15 left literal, right self.attr") ;
end if;

create object instance ao of AO;
assign ao.b = FALSE;
if (ao.b == FALSE)
  LOG::LogSuccess(message:"EX.F.15 left other.attr, right literal") ;
else 
  LOG::LogFailure(message:"EX.F.15 left other.attr, right literal") ;
end if;
if (FALSE == ao.b)
  LOG::LogSuccess(message:"EX.F.15 left literal, right other.attr") ;
else 
  LOG::LogFailure(message:"EX.F.15 left literal, right other.attr") ;
end if;

// Received Event 
if (rcvd_evt.b == TRUE)
  LOG::LogSuccess(message:"EX.F.15 left rcvd_evt.attr, right literal") ;
else 
  LOG::LogFailure(message:"EX.F.15 left other.attr, right literal") ;
end if;

if (TRUE == rcvd_evt.b )
  LOG::LogSuccess(message:"EX.F.15  left literal, right rcvd_evt.attr") ;
else 
 LOG::LogFailure(message:"EX.F.15  left literal, right rcvd_evt.attr") ; 
end if;

// Use id attr as an rval
assign this_id = self.et_id;

// use ref attr as an rval
create object instance my_etob of ETOB;
assign my_etob.etob_id = 2;
relate self to my_etob across R1;
assign my_etob_id = self.etob_id * 100;

// use transient from select as an rval
create object instance of ETOB;
select many etobs from instances of ETOB;
for each e_etob in etobs
  assign my_etob = e_etob;
end for;


LOG::LogInfo(message:"Finished F15 values") ;

assign self.i=42;
assign self.r=3.14;
assign self.bfalse=TRUE;
assign self.s="Hello";
generate ET4:''Run F16 Transformers''(i:self.i, r:self.r, b:self.bfalse,s:self.s,u:self.u) to self;

',
	'');
INSERT INTO SM_STATE
	VALUES (2097156,
	2097156,
	0,
	'Run F16 Transformers',
	4,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (2097156,
	2097155,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097155,
	2097156,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_SEME
	VALUES (2097156,
	2097157,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097156,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097158,
	2097156,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (2097156,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097162,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097156,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097163,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097156,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097164,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097156,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097165,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097156,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097166,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097156,
	2097167,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097167,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097156,
	2097168,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097168,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097156,
	2097169,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097169,
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
	'
LOG::LogInfo(message:"Running F16 Transformers") ;

// assign predetermined return values.  These will be the values that the coded 
// transforms use as return vals.

assign i_parm_ret_i_val = 42;
assign self.i = i_parm_ret_i_val;
assign r_parm_ret_r_val = 3.14;
assign b_parm_ret_b_val = TRUE;
assign s_parm_ret_s_val = "Hello";
assign u_parm_ret_u_val = rcvd_evt.u; //self.et_id
 
create object instance ao of AO;
assign ao.b = TRUE;

// no_parm_ret_void 
transform ET::no_parm_ret_void();

// i_parm_ret_void
transform ET::i_parm_ret_void(i:self.i);

// i_parm_ret_i
// use sefl.attr as parameter
transform i1 =  ET::i_parm_ret_i(i:self.i);
if (i1 == i_parm_ret_i_val )
  LOG::LogSuccess(message:"EX.F.16 i_parm_ret_i") ;
else 
  LOG::LogFailure(message:"EX.F.16 i_parm_ret_i") ;
end if;

// r_parm_ret_r
// use transient as parameter
assign r1 = 3.14;
transform r2 =  ET::r_parm_ret_r(r:r1);
LOG::LogReal(r:r2,message:"EX.F.16: r_parm_ret_r - r should be 3.14");

// b_parm_ret_b
// use attr of other object as parameter
transform b1 =  ET::b_parm_ret_b(b:ao.b);
if (b1 == b_parm_ret_b_val )
  LOG::LogSuccess(message:"EX.F.16 b_parm_ret_b") ;
else 
  LOG::LogFailure(message:"EX.F.16 b_parm_ret_b") ;
end if;

// s_parm_ret_s
// use literal as parm
transform s1 =  ET::s_parm_ret_s(s:"Hello");
if (s1 == s_parm_ret_s_val )
  LOG::LogSuccess(message:"EX.F.16 s_parm_ret_s") ;
else 
  LOG::LogFailure(message:"EX.F.16 s_parm_ret_s") ;
end if;

// u_parm_ret_u
// use received event as parm
transform u1 =  ET::u_parm_ret_u(u:rcvd_evt.u);
if (u1 == u_parm_ret_u_val )
  LOG::LogSuccess(message:"EX.F.16 u_parm_ret_u") ;
else 
  LOG::LogFailure(message:"EX.F.16 u_parm_ret_u") ;
end if;

// d_parm_ret_d

bridge d1 = TIM::create_date(second:30,minute:56,hour:19,day:3,month:1,year:1966);
LOG::LogDate(d:d1,message:"EX.F.16 d_parm_ret_d - d initial value");

bridge t1 = TIM::current_clock();
LOG::LogTime(t:t1,message:"EX.F.16 t_parm_ret_t - t initial value");


transform d2 = ET::d_parm_ret_d(d:d1);
LOG::LogDate(d:d2,message:"EX.F.16 d_parm_ret_d - d ret value");

// t_parm_ret_t

transform t2 = ET::t_parm_ret_t(t:t1);
LOG::LogTime(t:t2,message:"EX.F.16 t_parm_ret_t - t ret value");

// run a simple transform of another object
transform i3 = AO::i_parm_ret_i(i:24);

if (i3 == 24 )
  LOG::LogSuccess(message:"EX.F.16 other obj i_parm_ret_i") ;
else 
  LOG::LogFailure(message:"EX.F.16 other obj i_parm_ret_i") ;
end if;

LOG::LogInfo(message:"Finished F16 Transformers") ;

generate ET5:''Run F17 Timestamp and F18 Date Operators''() to self;
',
	'');
INSERT INTO SM_STATE
	VALUES (2097157,
	2097156,
	0,
	'Shutting Down',
	5,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (2097157,
	2097155,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097155,
	2097156,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (2097157,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097157,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097157,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097158,
	2097156,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (2097157,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097162,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097157,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097163,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097157,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097164,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097157,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097165,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097157,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097166,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097157,
	2097167,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097167,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097157,
	2097168,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097168,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097157,
	2097169,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097169,
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
	'LOG::LogInfo(message:"a1test_exconfig Expression Test Complete") ;

select any ev from instances of EV;
generate EV2:''shutdown'' to ev;
',
	'');
INSERT INTO SM_STATE
	VALUES (2097158,
	2097156,
	0,
	'Runnning F17 Timestamp and F18 Date Operators',
	6,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (2097158,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097156,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097158,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097157,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097158,
	2097158,
	2097156,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (2097158,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097162,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097158,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097163,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097158,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097164,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097158,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097165,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097158,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097166,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097158,
	2097167,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097167,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097158,
	2097168,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097168,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097158,
	2097169,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097169,
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
	'
// BEGIN EX.F.17

bridge t1 = TIM::current_clock();

LOG::LogInfo(message:"Starting EX.F.17 and EX.F.18 Test") ;

assign t3 = t1;

transform ET::waitOneSec();
bridge t2 = TIM::current_clock();

if (t1== t3)
  LOG::LogSuccess(message:"EX.F.17: timestamp == timestamp") ;
else
  LOG::LogFailure(message:"EX.F.17: timestamp == timestamp") ;
end if;

if (t1!= t2)
  LOG::LogSuccess(message:"EX.F.17: timestamp != timestamp") ;
else
  LOG::LogFailure(message:"EX.F.17: timestamp != timestamp") ;
end if;

if (t1<  t2)
  LOG::LogSuccess(message:"EX.F.17: timestamp < timestamp") ;
else
  LOG::LogFailure(message:"EX.F.17: timestamp < timestamp") ;
end if;

if (t1<= t3)
  LOG::LogSuccess(message:"EX.F.17: timestamp <= timestamp") ;
else
  LOG::LogFailure(message:"EX.F.17: timestamp <= timestamp") ;
end if;

if (t2 > t3)
  LOG::LogSuccess(message:"EX.F.17: timestamp > timestamp") ;
else
  LOG::LogFailure(message:"EX.F.17: timestamp > timestamp") ;
end if;

if (t1>= t3)
  LOG::LogSuccess(message:"EX.F.17: timestamp >= timestamp") ;
else
  LOG::LogFailure(message:"EX.F.17: timestamp >= timestamp") ;
end if;

// END EX.F.17

// BEGIN EX.F.18

LOG::LogInfo(message:"Starting EX.F.18 Testing Date operators") ;


bridge d1 = TIM::current_date();
assign d4 = d1;
bridge d2 = TIM::create_date(second:10,minute:10,hour:10,day:10,month:10,year:1910);
bridge d3 = TIM::create_date(second:10,minute:10,hour:10,day:10,month:10,year:1911);

if (d1== d4)
  LOG::LogSuccess(message:"EX.F.18: date == date") ;
else
  LOG::LogFailure(message:"EX.F.18: date == date") ;
end if;

if (d2 != d3)
  LOG::LogSuccess(message:"EX.F.18: date != date") ;
else
  LOG::LogFailure(message:"EX.F.18: date != date") ;
end if;

if (d3<  d1)
  LOG::LogSuccess(message:"EX.F.18: date < date") ;
else
  LOG::LogFailure(message:"EX.F.18: date < date") ;
end if;

if (d1<= d4)
  LOG::LogSuccess(message:"EX.F.18: date <= date") ;
else
  LOG::LogFailure(message:"EX.F.18: date <= date") ;
end if;

if (d1> d2)
  LOG::LogSuccess(message:"EX.F.18: date > date") ;
else
  LOG::LogFailure(message:"EX.F.18: date > date") ;
end if;

if (d1>= d4)
  LOG::LogSuccess(message:"EX.F.18: date >= date") ;
else
  LOG::LogFailure(message:"EX.F.18: date >= date") ;
end if;

LOG::LogInfo(message:"Test EX.F.17 and EX.F.18 complete") ;
generate ET6:''Run F1 and F2 for User Defined Types''() to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097159,
	2097156,
	0,
	'Running F1 and F2 for User Defined Types',
	7,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (2097159,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097156,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097159,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097157,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097159,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097158,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097159,
	2097159,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097159,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097160,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097159,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097161,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097159,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097162,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097159,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097163,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097159,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097164,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097159,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097165,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097159,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097166,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097159,
	2097167,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097167,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097159,
	2097168,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097168,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097159,
	2097169,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097159,
	2097169,
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
	'
LOG::LogInfo(message:"Expression Test F1 and F2 for User Defined Types") ;

//BEGIN EX.F.1_UDT
//check defaults

if (self.my_b != FALSE)
  LOG::LogFailure(message:"EX.F.1_UDT: my_b default") ;
else
  LOG::LogSuccess(message:"EX.F.1_UDT: my_b default") ;
end if;

if (self.my_i != 0)
  LOG::LogFailure(message:"EX.F.1_UDT: i default") ;
else 
   LOG::LogSuccess(message:"EX.F.1_UDT: i default") ; 
end if;
LOG::LogReal(r:self.r,message:"EX.F.1_UDT: r default");

if (self.my_s != "")
  LOG::LogFailure(message:"EX.F.1_UDT: s default") ;
else
  LOG::LogSuccess(message:"EX.F.1_UDT: s default") ;
end if;

//END EX.F.1_UDT

//BEGIN EX.F.2_UDT
//check integer assign

assign self.my_i = 10;
if (self.my_i == 10)
  LOG::LogSuccess(message:"EX.F.2_UDT: self.my_i assign") ;  
else
  LOG::LogFailure(message:"EX.F.2_UDT: self.my_i assign") ;  
end if;

//check real assign
assign self.my_r = 3.14;
LOG::LogReal(r:3.14,message:"EX.F.2_UDT: my_r assigned to") ;
LOG::LogReal(r:self.my_r,message:"EX.F.2_UDT: my_r assigned is");

//check bool assign
assign self.my_b = TRUE;
if (self.my_b == TRUE)
  LOG::LogSuccess(message:"EX.F.2_UDT: self.my_b assign") ;  
else
  LOG::LogFailure(message:"EX.F.2_UDT: self.my_b assign") ;  
end if;

//check string assign
assign self.my_s = "String";
if (self.my_s == "String")
  LOG::LogSuccess(message:"EX.F.2_UDT: self.my_s assign") ;  
else
  LOG::LogFailure(message:"EX.F.2_UDT: self.my_s assign") ;  
end if;

// check unary not
assign self.my_b = FALSE;
if (not self.my_b)
  LOG::LogSuccess(message:"EX.F.2_UDT: unary not") ;  
else
  LOG::LogFailure(message:"EX.F.2_UDT: unary not") ;  
end if;  

LOG::LogInfo(message:"Finished F1 and F2 for User Defined Types") ;


//generate event to commence Binary Operations test
generate ET7:''Run F3 through F14 for User Defined Types''() to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097160,
	2097156,
	0,
	'Running F3 through F14 for User Defined Types',
	8,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (2097160,
	2097155,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097155,
	2097156,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (2097160,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097157,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097160,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097158,
	2097156,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_SEME
	VALUES (2097160,
	2097160,
	2097156,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (2097160,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097162,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097160,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097163,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097160,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097164,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097160,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097165,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097160,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097166,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097160,
	2097167,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097167,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097160,
	2097168,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097168,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097160,
	2097169,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097169,
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
	'LOG::LogInfo(message:"Running F3 through F14 for User Defined Types") ;

//BEGIN EX.F.3_UDT
// boolean and, or, ==, and !=

//and
assign self.my_b = TRUE;
assign b2 = FALSE;
if (self.my_b and b2)
  LOG::LogFailure(message:"EX.F.3_UDT boolean and") ;
else
  LOG::LogSuccess(message:"EX.F.3_UDT boolean and") ;
end if;

// or
if (self.my_b or b2)
  LOG::LogSuccess(message:"EX.F.3_UDT boolean or") ;
else
  LOG::LogFailure(message:"EX.F.3_UDT boolean or") ;
end if;

//==
if (self.my_b == b2)
  LOG::LogFailure(message:"EX.F.3_UDT boolean ==") ;
else
  LOG::LogSuccess(message:"EX.F.3_UDT boolean ==") ;
end if;

//!=
if (self.my_b != b2)
  LOG::LogSuccess(message:"EX.F.3_UDT boolean ==") ;
else
  LOG::LogFailure(message:"EX.F.3_UDT boolean ==") ;
end if;
//END EX.F.3_UDT

//BEGIN EX.F.4_UDT
// integer + - * / %

// +
assign self.my_i = 5;
assign i2 = -6;
assign i3 = self.my_i + i2;
if (i3 == -1)
  LOG::LogSuccess(message:"EX.F.4_UDT integer +") ;
else
  LOG::LogFailure(message:"EX.F.4_UDT integer +") ;
end if;
//- 
assign i4 = self.my_i - i2;
if (i4 == 11)
  LOG::LogSuccess(message:"EX.F.4_UDT integer -") ;
else
  LOG::LogFailure(message:"EX.F.4_UDT integer -") ;
end if;
// *
assign i5 = self.my_i*i2;
if (i5 == -30)
  LOG::LogSuccess(message:"EX.F.4_UDT integer *") ;
else
  LOG::LogFailure(message:"EX.F.4_UDT integer *") ;
end if;
// /
assign i6 = 10;
assign i7 = i6/self.my_i;
if (i7 == 2)
  LOG::LogSuccess(message:"EX.F.4_UDT integer /") ;
else
  LOG::LogFailure(message:"EX.F.4_UDT integer /") ;
end if;
// %
assign i8 = 13;
assign i9 = i8%self.my_i;
if (i9 == 3)
  LOG::LogSuccess(message:"EX.F.4_UDT integer %") ;
else
  LOG::LogFailure(message:"EX.F.4_UDT integer %") ;
end if;
//END EX.F.4_UDT

//BEGIN EX.F.5_UDT
// integer  < <= == != >= >

assign self.my_i = 12;
assign j2 = 6;
assign j3 = 12;
// <
if (j2 < self.my_i)
  LOG::LogSuccess(message:"EX.F.5_UDT integer <") ;
else
  LOG::LogFailure(message:"EX.F.5_UDT integer <") ;
end if;
// <=
if (j2 <= self.my_i)
  LOG::LogSuccess(message:"EX.F.5_UDT integer <=") ;
else
  LOG::LogFailure(message:"EX.F.5_UDT integer <=") ;
end if;
//==
if (self.my_i == j3)
  LOG::LogSuccess(message:"EX.F.5_UDT integer ==") ;
else
  LOG::LogFailure(message:"EX.F.5_UDT integer ==") ;
end if;
//!=
if (j2 != self.my_i)
  LOG::LogSuccess(message:"EX.F.5_UDT integer !=") ;
else
  LOG::LogFailure(message:"EX.F.5_UDT integer !=") ;
end if;
//>=
if (self.my_i >= j2)
  LOG::LogSuccess(message:"EX.F.5 integer >=") ;
else
  LOG::LogFailure(message:"EX.F.5_UDT integer >=") ;
end if;
//>
if (self.my_i > j2)
  LOG::LogSuccess(message:"EX.F.5_UDT integer >") ;
else
  LOG::LogFailure(message:"EX.F.5_UDT integer >") ;
end if;
//END EX.F.5_UDT

//BEGIN EX.F.6_UDT
//  + - * /  with int as left operand, real as right operand, and real as result
// +
assign self.my_i = 5;
assign r1 = -6.0;
assign r3 = self.my_i + r1;
LOG::LogReal(r:r3,message:"EX.F.6_UDT int real + : r should be -1.0") ;

//- 
assign r4 = self.my_i - r1;
LOG::LogReal(r:r4,message:"EX.F.6_UDT int real - : r should be 11.0") ;

// *
assign r5 = self.my_i*r1;
LOG::LogReal(r:r5,message:"EX.F.6_UDT int real * : r should be -30.0") ;

// /
assign self.my_i = 4;
assign r2 = 2.0;
assign r6 = self.my_i/r2;
LOG::LogReal(r:r6,message:"EX.F.6_UDT int real / : r should be 2.0") ;

//END EX.F.6_UDT

//BEGIN EX.F.7_UDT
//  < <= == != >= > int as left operand, real as right, and bool result

assign s1 = 6.0;
assign s2 = 12.0;
assign self.my_i = 6;

// <
if (self.my_i < s2)
  LOG::LogSuccess(message:"EX.F.7_UDT int real <") ;
else
  LOG::LogFailure(message:"EX.F.7_UDT int real <") ;
end if;

// <=
if (self.my_i <= s1)
  LOG::LogSuccess(message:"EX.F.7_UDT int real <=") ;
else
  LOG::LogFailure(message:"EX.F.7_UDT int real <=") ;
end if;

//==
if (self.my_i == s1)
  LOG::LogInfo(message:"EX.F.7_UDT int real ==") ;
else
  LOG::LogInfo(message:"EX.F.7_UDT int real ==") ;
end if;

assign self.my_i=12;
//!=
if (self.my_i != s1)
  LOG::LogInfo(message:"EX.F.7_UDT int real !=") ;
else
  LOG::LogInfo(message:"EX.F.7_UDT int real !=") ;
end if;

//>=
if (self.my_i >= s1)
  LOG::LogSuccess(message:"EX.F.7_UDT int real >=") ;
else
  LOG::LogFailure(message:"EX.F.7 int real >=") ;
end if;
//>
if (self.my_i > s1)
  LOG::LogSuccess(message:"EX.F.7_UDT int real >") ;
else
  LOG::LogFailure(message:"EX.F.7_UDT int real >") ;
end if;
//END EX.F.7_UDT

//BEGIN EX.F.8_UDT
//  + - * /  with real as left operand, int as right operand, and real as result

// +
assign self.my_i = 5;
assign r1 = -6.0;
assign r3 = r1 + self.my_i;
LOG::LogReal(r:r3,message:"EX.F.8_UDT real int  + : r should be -1.0") ;

//- 
assign r4 = r1 - self.my_i;
LOG::LogReal(r:r4,message:"EX.F.8_UDT real int - : r should be -11.0") ;

// *
assign r5 = r1*self.my_i;
LOG::LogReal(r:r5,message:"EX.F.8_UDT real int * : r should be -30.0") ;

// /
assign self.my_i = 4;
assign r2 = 2.0;
assign r6 = r2/self.my_i;
LOG::LogReal(r:r6,message:"EX.F.8 real int  / : r should be 0.5") ;

//END EX.F.8_UDT

//BEGIN EX.F.9_UDT
//  < <= == != >= > real as left operand, int as right, and bool result

assign s1 = 12.0;
assign self.my_i = 12;
assign s2 = 6.0;

// <
if (s2 < self.my_i)
  LOG::LogSuccess(message:"EX.F.9_UDT real int <") ;
else
  LOG::LogFailure(message:"EX.F.9_UDT real int <") ;
end if;

// <=
if (s1 <= self.my_i)
  LOG::LogInfo(message:"EX.F.9_UDT real int <=") ;
else
  LOG::LogInfo(message:"EX.F.9_UDT real int <=") ;
end if;

assign self.my_i = 6;

//==
if (s2 == self.my_i)
  LOG::LogInfo(message:"EX.F.9_UDT real int ==") ;
else
  LOG::LogInfo(message:"EX.F.9_UDT real int ==") ;
end if;

//!=
if (s1 != self.my_i)
  LOG::LogSuccess(message:"EX.F.9_UDT real int !=") ;
else
  LOG::LogFailure(message:"EX.F.9_UDT real int !=") ;
end if;

//>=
if (s1 >= self.my_i)
  LOG::LogSuccess(message:"EX.F.9_UDT real int >=") ;
else
  LOG::LogFailure(message:"EX.F.9_UDT real int >=") ;
end if;

//>
if (s1 > self.my_i)
  LOG::LogSuccess(message:"EX.F.9_UDT real int >") ;
else
  LOG::LogFailure(message:"EX.F.9_UDT real int >") ;
end if;

//END EX.F.9_UDT

//BEGIN EX.F.10_UDT
// real + - * / 

// +
assign self.my_r = 5.0;
assign r2 = -6.0;
assign r3 = self.my_r + r2;
LOG::LogReal(r:r3,message:"EX.F.10_UDT real real + : r should be -1.0") ;

//- 
assign r4 = self.my_r - r2;
LOG::LogReal(r:r4,message:"EX.F.10_UDT real real - : r should be 11.0") ;

// *
assign r5 = self.my_r*r2;
LOG::LogReal(r:r5,message:"EX.F.10_UDT real real * : r should be -30.0") ;

// /
assign r6 = 10.0;
assign r7 = r6/self.my_r;
LOG::LogReal(r:r7,message:"EX.F.10_UDT real real / : r should be 2.0") ;

//END EX.F.10_UDT


//BEGIN EX.F.11_UDT
// real  < <= == != >= >

assign self.my_r = 12.0;
assign r2 = 6.0;
assign r3 = 6.0;
assign r4=12.0;
// <
if (r2 < self.my_r)
  LOG::LogSuccess(message:"EX.F.11 real <") ;
else
  LOG::LogFailure(message:"EX.F.11_UDT real <") ;
end if;
// <=
if (r2 <= self.my_r)
  LOG::LogSuccess(message:"EX.F.11_UDT real <=") ;
else
  LOG::LogFailure(message:"EX.F.11_UDT real <=") ;
end if;
//==
if (self.my_r == r4)
  LOG::LogInfo(message:"EX.F.11_UDT real ==") ;
else
  LOG::LogInfo(message:"EX.F.11_UDT real ==") ;
end if;
//!=
if (r2 != self.my_r)
  LOG::LogSuccess(message:"EX.F.11_UDT real !=") ;
else
  LOG::LogFailure(message:"EX.F.11_UDT real !=") ;
end if;
//>=
if (self.my_r >= r2)
  LOG::LogSuccess(message:"EX.F.11_UDT real >=") ;
else
  LOG::LogFailure(message:"EX.F.11_UDT real >=") ;
end if;
//>
if (self.my_r > r2)
  LOG::LogSuccess(message:"EX.F.11_UDT real >") ;
else
  LOG::LogFailure(message:"EX.F.11_UDT real >") ;
end if;
//END EX.F.11_UDT

//BEGIN EX.F.12_UDT
// string string +
assign self.my_s = "Hello";
assign str2 = "World";
assign str3 = self.my_s+str2;
if (str3 == "HelloWorld")
  LOG::LogSuccess(message:"EX.F.12_UDT string +") ;
else
  LOG::LogFailure(message:"EX.F.12_UDT string +") ;
end if;
//END EX.F.12_UDT

//BEGIN EX.F.13_UDT
//string < <= == != >= >
assign self.my_s = "a";
assign str2 = "b";
assign str3 = "a";
// <
if (self.my_s < str2)
  LOG::LogSuccess(message:"EX.F.13_UDT string <") ;
else
  LOG::LogFailure(message:"EX.F.13 string <") ;
end if;
// <=
if (self.my_s <= str3)
  LOG::LogSuccess(message:"EX.F.13_UDT string <=") ;
else
  LOG::LogFailure(message:"EX.F.13_UDT string <=") ;
end if;
// ==
if (str3 == self.my_s)
  LOG::LogSuccess(message:"EX.F.13_UDT string ==") ;
else
  LOG::LogFailure(message:"EX.F.13_UDT string ==") ;
end if;
// !=
if (str2 != self.my_s)
  LOG::LogSuccess(message:"EX.F.13 string !=") ;
else
  LOG::LogFailure(message:"EX.F.13_UDT string !=") ;
end if;
// >=
if (str2 >= self.my_s)
  LOG::LogSuccess(message:"EX.F.13_UDT string >=") ;
else
  LOG::LogFailure(message:"EX.F.13 string >=") ;
end if;
// >
if (str2 > self.my_s)
  LOG::LogSuccess(message:"EX.F.13_UDT string >") ;
else
  LOG::LogFailure(message:"EX.F.13_UDT string >") ;
end if;
if (self.my_s < "c")
  LOG::LogSuccess(message:"EX.F.13 string < quoted") ;
else
  LOG::LogFailure(message:"EX.F.13_UDT string < quoted") ;
end if;
//END EX.F.13_UDT

//BEGIN EX.F.14_UDT
// unique_id == !=
assign self.my_u = self.u;
if (self.my_u == self.u)
  LOG::LogSuccess(message:"EX.F.14_UDT unique ==") ;
else
  LOG::LogFailure(message:"EX.F.14_UDT unique ==") ;
end if;

select any oi from instances of OI;
if (self.my_u != oi.oi_id)
  LOG::LogSuccess(message:"EX.F.14_UDT unique !=") ;
else
  LOG::LogFailure(message:"EX.F.14_UDT unique !=") ;
end if;
//END EX.F.14_UDT

LOG::LogInfo(message:"Finished F3 through F14 for User Defined Types") ;

generate ET8:''Run F15 for User Defined Types''(my_b:TRUE,my_i:42,my_r:3.14,my_s:"Hello",my_u:self.u) to self;
',
	'');
INSERT INTO SM_STATE
	VALUES (2097161,
	2097156,
	0,
	'Running F15 for User Defined Types',
	9,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (2097161,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097156,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097161,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097157,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097161,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097158,
	2097156,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_SEME
	VALUES (2097161,
	2097161,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097161,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097162,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097161,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097163,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097161,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097164,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097161,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097165,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097161,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097166,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097161,
	2097167,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097167,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097161,
	2097168,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097168,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097161,
	2097169,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097169,
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
	'LOG::LogInfo(message:"Running F15 for User Defined Types") ;

// Received Event 
if (rcvd_evt.my_b == TRUE)
  LOG::LogSuccess(message:"EX.F.15_UDT  rcvd_evt.attr my_b") ;
else 
  LOG::LogFailure(message:"EX.F.15_UDT rcvd_evt.attr my_b") ;
end if;

if (rcvd_evt.my_i == 42)
  LOG::LogSuccess(message:"EX.F.15_UDT  rcvd_evt.attr my_i") ;
else 
  LOG::LogFailure(message:"EX.F.15_UDT rcvd_evt.attr my_i") ;
end if;

LOG::LogReal(message:"EX.F.15_UDT rcvd_evt.attr r",r:rcvd_evt.my_r) ; 

if (rcvd_evt.my_s == "Hello")
  LOG::LogSuccess(message:"EX.F.15_UDT  rcvd_evt.attr my_s") ;
else 
  LOG::LogFailure(message:"EX.F.15_UDT rcvd_evt.attr my_s") ;
end if;

if (rcvd_evt.my_u == self.u)
  LOG::LogSuccess(message:"EX.F.15_UDT  rcvd_evt.attr my_u") ;
else 
  LOG::LogFailure(message:"EX.F.15_UDT rcvd_evt.attr my_u") ;
end if;

generate ET9:''Shut Down''() to self;

',
	'');
INSERT INTO SM_STATE
	VALUES (2097162,
	2097156,
	0,
	'Testing empty operator',
	10,
	0);
INSERT INTO SM_CH
	VALUES (2097162,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097153,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097162,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097154,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097162,
	2097155,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097155,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097162,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097156,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097162,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097157,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097162,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097158,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097162,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097159,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097162,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097160,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097162,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097161,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097162,
	2097162,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097162,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097163,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097162,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097164,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097162,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097165,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097162,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097166,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097162,
	2097167,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097167,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097162,
	2097168,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097168,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097162,
	2097169,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097169,
	2097156,
	0);
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
	'LOG::LogInfo(message:"Starting Expression Test empty operator") ;

//empty operation
  // local existing (saf)
    // inst_ref(Object)
select any saf0 from instances of NOI;
assign t1 = empty saf0;
if (t1 == TRUE)
  LOG::LogSuccess(message:"EX.F.2: empty any from (true)") ;  
else
  LOG::LogFailure(message:"EX.F.2: empty any from (true)") ;  
end if;

select any saf1 from instances of OI;
assign t2 = empty saf1;
if (t2 == FALSE)
  LOG::LogSuccess(message:"EX.F.2: empty any from (false)") ;  
else
  LOG::LogFailure(message:"EX.F.2: empty any from (false)") ;  
end if;

  // local existing (smf)
    // inst_ref_set(Object)
select many smf0 from instances of NOI;
assign t3 = empty smf0;
if (t3 == TRUE)
  LOG::LogSuccess(message:"EX.F.2: empty many from (true)") ;  
else
  LOG::LogFailure(message:"EX.F.2: empty many from (true)") ;  
end if;

select many smf1 from instances of MI;
assign t4 = empty smf1;
if ( t4 == FALSE )
  LOG::LogSuccess(message:"EX.F.2: empty many from (false)") ;  
else
  LOG::LogFailure(message:"EX.F.2: empty many from (false)") ;  
end if;


  // local existing (sar)
    // inst_ref(Object)
select any sar0 related by self->NOI[R2];
assign t5 = empty sar0;
if (t5 == TRUE)
  LOG::LogSuccess(message:"EX.F.2: empty any related (true)") ;  
else
  LOG::LogFailure(message:"EX.F.2: empty any related (true)") ;  
end if;

select any sar1 related by self->MI[R4];
assign t6 = empty sar1;
if (t6 == FALSE)
  LOG::LogSuccess(message:"EX.F.2: empty any related (false)") ;  
else
  LOG::LogFailure(message:"EX.F.2: empty any related (false)") ;  
end if;

  // local existing (sor)
    // inst_ref(Object)
select one sor0 related by self->NOI[R5];
assign t7 = empty sor0;
if (t7 == TRUE)
  LOG::LogSuccess(message:"EX.F.2: empty one related (true)") ;  
else
  LOG::LogFailure(message:"EX.F.2: empty one related (true)") ;  
end if;

select one sor1 related by self->OI[R3];
assign t8 = empty sor1;
if (t8 == FALSE)
  LOG::LogSuccess(message:"EX.F.2: empty one related (false)") ;  
else
  LOG::LogFailure(message:"EX.F.2: empty one related (false)") ;  
end if;

  // local existing (smr)
    // inst_ref_set(Object)
select many smr0 related by self->NOI[R2];
assign t9 = empty smr0;
if (t9 == TRUE)
  LOG::LogSuccess(message:"EX.F.2: empty one related (true)") ;  
else
  LOG::LogFailure(message:"EX.F.2: empty one related (true)") ;  
end if;

select many smr1 related by self->MI[R4];
assign t10 = empty smr1;
if (t10 == FALSE)
  LOG::LogSuccess(message:"EX.F.2: empty one related (false)") ;  
else
  LOG::LogFailure(message:"EX.F.2: empty one related (false)") ;  
end if;

//generate event to commence next test
generate ET10:''Start not_empty test''() to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097163,
	2097156,
	0,
	'Testing not_empty operator',
	11,
	0);
INSERT INTO SM_CH
	VALUES (2097163,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097153,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097163,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097154,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097163,
	2097155,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097155,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097163,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097156,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097163,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097157,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097163,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097158,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097163,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097159,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097163,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097160,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097163,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097161,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097163,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097162,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097163,
	2097163,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097163,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097164,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097163,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097165,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097163,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097166,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097163,
	2097167,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097167,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097163,
	2097168,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097168,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097163,
	2097169,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097169,
	2097156,
	0);
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
	'LOG::LogInfo(message:"Starting Expression Test not_empty operator") ;

//not_empty operation
  // local existing (saf)
    // inst_ref(Object)
select any saf0 from instances of NOI;
assign t1 = not_empty saf0;
if (t1 == FALSE)
  LOG::LogSuccess(message:"EX.F.2: not_empty any from (false)") ;  
else
  LOG::LogFailure(message:"EX.F.2: not_empty any from (false)") ;  
end if;

select any saf1 from instances of OI;
assign t2 = not_empty saf1;
if (t2 == TRUE)
  LOG::LogSuccess(message:"EX.F.2: not_empty any from (true)") ;  
else
  LOG::LogFailure(message:"EX.F.2: not_empty any from (true)") ;  
end if;

  // local existing (smf)
    // inst_ref_set(Object)
select many smf0 from instances of NOI;
assign t3 = not_empty smf0;
if (t3 == FALSE)
  LOG::LogSuccess(message:"EX.F.2: not_empty many from (false)") ;  
else
  LOG::LogFailure(message:"EX.F.2: not_empty many from (false)") ;  
end if;

select many smf1 from instances of MI;
assign t4 = not_empty smf1;
if ( t4 == TRUE )
  LOG::LogSuccess(message:"EX.F.2: not_empty many from (true)") ;  
else
  LOG::LogFailure(message:"EX.F.2: not_empty many from (true)") ;  
end if;


  // local existing (sar)
    // inst_ref(Object)
select any sar0 related by self->NOI[R2];
assign t5 = not_empty sar0;
if (t5 == FALSE)
  LOG::LogSuccess(message:"EX.F.2: not_empty any related (false)") ;  
else
  LOG::LogFailure(message:"EX.F.2: not_empty any related (false)") ;  
end if;

select any sar1 related by self->MI[R4];
assign t6 = not_empty sar1;
if (t6 == TRUE)
  LOG::LogSuccess(message:"EX.F.2: not_empty any related (true)") ;  
else
  LOG::LogFailure(message:"EX.F.2: not_empty any related (true)") ;  
end if;

  // local existing (sor)
    // inst_ref(Object)
select one sor0 related by self->NOI[R5];
assign t7 = not_empty sor0;
if (t7 == FALSE)
  LOG::LogSuccess(message:"EX.F.2: not_empty one related (false)") ;  
else
  LOG::LogFailure(message:"EX.F.2: not_empty one related (false)") ;  
end if;

select one sor1 related by self->OI[R3];
assign t8 = not_empty sor1;
if (t8 == TRUE)
  LOG::LogSuccess(message:"EX.F.2: not_empty one related (true)") ;  
else
  LOG::LogFailure(message:"EX.F.2: not_empty one related (true)") ;  
end if;

  // local existing (smr)
    // inst_ref_set(Object)
select many smr0 related by self->NOI[R2];
assign t9 = not_empty smr0;
if (t9 == FALSE)
  LOG::LogSuccess(message:"EX.F.2: not_empty one related (false)") ;  
else
  LOG::LogFailure(message:"EX.F.2: not_empty one related (false)") ;  
end if;

select many smr1 related by self->MI[R4];
assign t10 = not_empty smr1;
if (t10 == TRUE)
  LOG::LogSuccess(message:"EX.F.2: not_empty one related (true)") ;  
else
  LOG::LogFailure(message:"EX.F.2: not_empty one related (true)") ;  
end if;

//generate event to commence next test
generate ET11:''Start cardinality test''() to self;
',
	'');
INSERT INTO SM_STATE
	VALUES (2097164,
	2097156,
	0,
	'Testing cardinality operator',
	12,
	0);
INSERT INTO SM_CH
	VALUES (2097164,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097164,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097154,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097164,
	2097155,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097155,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097164,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097156,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097164,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097157,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097164,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097158,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097164,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097159,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097164,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097160,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097164,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097161,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097164,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097162,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097164,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097163,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097164,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097164,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097164,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097165,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097164,
	2097166,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097164,
	2097167,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097167,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097164,
	2097168,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097168,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097164,
	2097169,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097169,
	2097156,
	0);
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
	'LOG::LogInfo(message:"Starting Expression Test cardinality operator") ;

// cardinality operation
  // local existing (saf)
    // inst_ref(Object)
select any saf0 from instances of NOI;
assign t1 = cardinality saf0;
if (t1 == 0)
  LOG::LogSuccess(message:"EX.F.2: cardinality any from (0)") ;  
else
  LOG::LogFailure(message:"EX.F.2: cardinality any from (0)") ;  
end if;

select any saf1 from instances of OI;
assign t2 = cardinality saf1;
if (t2 == 1)
  LOG::LogSuccess(message:"EX.F.2: cardinality any from (1)") ;  
else
  LOG::LogFailure(message:"EX.F.2: cardinality any from (1)") ;  
end if;

  // local existing (smf)
    // inst_ref_set(Object)
select many smf0 from instances of NOI;
assign t3 = cardinality smf0;
if (t3 == 0)
  LOG::LogSuccess(message:"EX.F.2: cardinality many from (0)") ;  
else
  LOG::LogFailure(message:"EX.F.2: cardinality many from (0)") ;  
end if;

select many smf1 from instances of MI;
assign t4 = cardinality smf1;
if ( t4 == 2)
  LOG::LogSuccess(message:"EX.F.2: cardinality many from (2)") ;  
else
  LOG::LogFailure(message:"EX.F.2: cardinality many from (2)") ;  
end if;


  // local existing (sar)
    // inst_ref(Object)
select any sar0 related by self->NOI[R2];
assign t5 = cardinality sar0;
if (t5 == 0)
  LOG::LogSuccess(message:"EX.F.2: cardinality any related (0)") ;  
else
  LOG::LogFailure(message:"EX.F.2: cardinality any related (0)") ;  
end if;

select any sar1 related by self->MI[R4];
assign t6 = cardinality sar1;
if (t6 == 1)
  LOG::LogSuccess(message:"EX.F.2: cardinality any related (1)") ;  
else
  LOG::LogFailure(message:"EX.F.2: cardinality any related (1)") ;  
end if;

  // local existing (sor)
    // inst_ref(Object)
select one sor0 related by self->NOI[R5];
assign t7 = cardinality sor0;
if (t7 == 0)
  LOG::LogSuccess(message:"EX.F.2: cardinality one related (0)") ;  
else
  LOG::LogFailure(message:"EX.F.2: cardinality one related (0)") ;  
end if;

select one sor1 related by self->OI[R3];
assign t8 = cardinality sor1;
if (t8 == 1)
  LOG::LogSuccess(message:"EX.F.2: cardinality one related (1)") ;  
else
  LOG::LogFailure(message:"EX.F.2: cardinality one related (1)") ;  
end if;

  // local existing (smr)
    // inst_ref_set(Object)
select many smr0 related by self->NOI[R2];
assign t9 = cardinality smr0;
if (t9 == 0)
  LOG::LogSuccess(message:"EX.F.2: cardinality one related (0)") ;  
else
  LOG::LogFailure(message:"EX.F.2: cardinality one related (0)") ;  
end if;

select many smr1 related by self->MI[R4];
assign t10 = cardinality smr1;
if (t10 == 2)
  LOG::LogSuccess(message:"EX.F.2: cardinality one related (2)") ;  
else
  LOG::LogFailure(message:"EX.F.2: cardinality one related (2)") ;  
end if;

// generate event to commence next test
generate ET14:''Start subtract test''( tint1:17, tint2:13, treal1:2.781828, treal2:11.2) to self;

',
	'');
INSERT INTO SM_STATE
	VALUES (2097165,
	2097156,
	0,
	'Testing not operator',
	13,
	0);
INSERT INTO SM_CH
	VALUES (2097165,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097153,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097165,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097154,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097165,
	2097155,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097155,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097165,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097156,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097165,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097157,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097165,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097158,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097165,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097159,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097165,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097160,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097165,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097161,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097165,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097162,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097165,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097163,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097165,
	2097164,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097165,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097165,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097165,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097166,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097165,
	2097167,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097167,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097165,
	2097168,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097168,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097165,
	2097169,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097169,
	2097156,
	0);
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
	'LOG::LogInfo(message:"Starting Expression Test not operator") ;

//not operation
  // local existing 
assign tfalse = false;
assign ttrue = true;
assign t1 = not tfalse;
if (t1 == TRUE)
  LOG::LogSuccess(message:"not local existing (true)") ;  
else
  LOG::LogFailure(message:"not local existing (true)") ;  
end if;

assign t2 = not ttrue;
if (t2 == FALSE)
  LOG::LogSuccess(message:"not local existing (false)") ;
else
  LOG::LogFailure(message:"not local existing (false)") ;
end if;

  // rcvd_evt
assign t3 = not rcvd_evt.tfalse;
if (t3 == TRUE)
  LOG::LogSuccess(message:"not rcvd_evt (true)") ;
else
  LOG::LogFailure(message:"not rcvd_evt (true)") ;
end if;  
 
assign t4 = not rcvd_evt.ttrue;
if (t4 == FALSE)
  LOG::LogSuccess(message:"not rcvd_evt (false)") ;
else
  LOG::LogFailure(message:"not rcvd_evt (false)") ;
end if;
 
  // constant 
assign t5 = not FALSE;
if (t5 == TRUE)
  LOG::LogSuccess(message:"not constant (true)") ;
else
  LOG::LogFailure(message:"not constant (true)") ;
end if;  
 
assign t6 = not TRUE;
if (t6 == FALSE)
  LOG::LogSuccess(message:"not constant (false)") ;
else
  LOG::LogFailure(message:"not constant (false)") ;
end if;
 
  // self.attribute
assign t7 = not self.bfalse;
if (t7 == TRUE)
  LOG::LogSuccess(message:"not self.attribute (true)") ;
else 
  LOG::LogFailure(message:"not self.attribute (true)") ;
end if; 
 
assign t8 = not self.btrue;
if (t8 == FALSE)
  LOG::LogSuccess(message:"not self.attribute (false)") ;
else 
  LOG::LogFailure(message:"not self.attribute (false)") ;
end if;
 
  // self.referential attribute
assign t9 = not self.r1bfalse;
if (t9 == TRUE)
  LOG::LogSuccess(message:"not self.referential attribute (true)") ;
else 
  LOG::LogFailure(message:"not self.referential attribute (true)") ;
end if; 
 
assign t10 = not self.r1btrue;
if (t10 == FALSE)
  LOG::LogSuccess(message:"not self.referential attribute (false)") ;
else 
  LOG::LogFailure(message:"not self.referential attribute (false)") ;
end if;

  // other.attribute (saf)
select any saf1 from instances of ETOB;
assign t11 = not saf1.bfalse;
if (t11 == TRUE)
  LOG::LogSuccess(message:"not other.attribute (saf) (true)") ;
else
  LOG::LogFailure(message:"not other.attribute (saf) (true)") ;
end if;
 
assign t12 = not saf1.btrue;
if (t12 == FALSE)
  LOG::LogSuccess(message:"not other.attribute (saf) (false)") ;
else
  LOG::LogFailure(message:"not other.attribute (saf) (false)") ;
end if;
 
  // other.attribute (smf)
select many smf1 from instances of ETOB;
for each smf in smf1
  assign t13 = not smf.bfalse;
  if (t13 == TRUE)
    LOG::LogSuccess(message:"not other.attribute (smf) (true)") ;
  else
    LOG::LogFailure(message:"not other.attribute (smf) (true)") ;
  end if;
 
  assign t14 = not smf.btrue;
  if (t14 == FALSE)
    LOG::LogSuccess(message:"not other.attribute (smf) (false)") ;
  else
    LOG::LogFailure(message:"not other.attribute (smf) (false)") ;
  end if;
end for;
 
  // other.attribute (sar)
select any sar1 related by self->ETOB[R8];
assign t15 = not sar1.bfalse;
if (t15 == TRUE)
  LOG::LogSuccess(message:"not other.attribute (sar) (true)") ;
else
  LOG::LogFailure(message:"not other.attribute (sar) (true)") ;
end if;
 
assign t16 = not sar1.btrue;
if (t16 == FALSE)
  LOG::LogSuccess(message:"not other.attribute (sar) (false)") ;
else
  LOG::LogFailure(message:"not other.attribute (sar) (false)") ;
end if;

  // other.attribute (sor)
select one sor1 related by self->ETOB[R1];
assign t17 = not sor1.bfalse;
if (t17 == TRUE)
  LOG::LogSuccess(message:"not other.attribute (sor) (true)") ;
else
  LOG::LogFailure(message:"not other.attribute (sor) (true)") ;
end if;
 
assign t18 = not sor1.btrue;
if (t18 == FALSE)
  LOG::LogSuccess(message:"not other.attribute (sor) (false)") ;
else
  LOG::LogFailure(message:"not other.attribute (sor) (false)") ;
end if;


  // other.attribute (smr)
select many smr1 from instances of ETOB;
for each smr in smr1
  assign t19 = not smr.bfalse;
  if (t19 == TRUE)
    LOG::LogSuccess(message:"not other.attribute (smr) (true)") ;
  else
    LOG::LogFailure(message:"not other.attribute (smr) (true)") ;
  end if;
 
  assign t20 = not smr.btrue;
  if (t20 == FALSE)
    LOG::LogSuccess(message:"not other.attribute (smr) (false)") ;
  else
    LOG::LogFailure(message:"not other.attribute (smr) (false)") ;
  end if;
end for;

  // other.referential attribute (saf)
assign t21 = not saf1.r6bfalse;
if (t21 == TRUE)
  LOG::LogSuccess(message:"not other.referential attribute (saf) (true)") ;
else
  LOG::LogFailure(message:"not other.referential attribute (saf) (true)") ;
end if;
 
assign t22 = not saf1.r6btrue;
if (t22 == FALSE)
  LOG::LogSuccess(message:"not other.referential attribute (saf) (false)") ;
else
  LOG::LogFailure(message:"not other.referential attribute (saf) (false)") ;
end if;
 
  // other.referential attribute (smf)
for each smf in smf1
  assign t23 = not smf.r6bfalse;
  if (t23 == TRUE)
    LOG::LogSuccess(message:"not other.referential attribute (smf) (true)") ;
  else
    LOG::LogFailure(message:"not other.referential attribute (smf) (true)") ;
  end if;
 
  assign t24 = not smf.r6btrue;
  if (t24 == FALSE)
    LOG::LogSuccess(message:"not other.referential attribute (smf) (false)") ;
  else
    LOG::LogFailure(message:"not other.referential attribute (smf) (false)") ;
  end if;
end for;

  // other.referential attribute (sar)
assign t25 = not sar1.r6bfalse;
if (t25 == TRUE)
  LOG::LogSuccess(message:"not other.referential attribute (sar) (true)") ;
else
  LOG::LogFailure(message:"not other.referential attribute (sar) (true)") ;
end if;
 
assign t26 = not sar1.r6btrue;
if (t26 == FALSE)
  LOG::LogSuccess(message:"not other.referential attribute (sar) (false)") ;
else
  LOG::LogFailure(message:"not other.referential attribute (sar) (false)") ;
end if;

  // other.referential attribute (sor)
assign t27 = not sor1.r6bfalse;
if (t27 == TRUE)
  LOG::LogSuccess(message:"not other.referential attribute (sor) (true)") ;
else
  LOG::LogFailure(message:"not other.referential attribute (sor) (true)") ;
end if;
 
assign t28 = not sor1.r6btrue;
if (t28 == FALSE)
  LOG::LogSuccess(message:"not other.referential attribute (sor) (false)") ;
else
  LOG::LogFailure(message:"not other.referential attribute (sor) (false)") ;
end if;

  // other.referential attribute (smr)
for each smr in smr1
  assign t29 = not smr.r6bfalse;
  if (t29 == TRUE)
    LOG::LogSuccess(message:"not other.referential attribute (smr) (true)") ;
  else
    LOG::LogFailure(message:"not other.referential attribute (smr) (true)") ;
  end if;
 
  assign t30 = not smr.r6btrue;
  if (t30 == FALSE)
    LOG::LogSuccess(message:"not other.referential attribute (smr) (false)") ;
  else
    LOG::LogFailure(message:"not other.referential attribute (smr) (false)") ;
  end if;
end for;

 
LOG::LogInfo(message:"Finished Expression Test not operator") ;

//generate event to commence next test
generate ET12:''Start empty test''() to self;
',
	'');
INSERT INTO SM_STATE
	VALUES (2097166,
	2097156,
	0,
	'Testing subtraction operator',
	14,
	0);
INSERT INTO SM_CH
	VALUES (2097166,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097166,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097166,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097166,
	2097154,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097166,
	2097155,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097166,
	2097155,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097166,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097166,
	2097156,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097166,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097166,
	2097157,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097166,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097166,
	2097158,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097166,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097166,
	2097159,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097166,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097166,
	2097160,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097166,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097166,
	2097161,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097166,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097166,
	2097162,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097166,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097166,
	2097163,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097166,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097166,
	2097164,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097166,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097166,
	2097165,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097166,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097166,
	2097166,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097166,
	2097167,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097166,
	2097168,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097166,
	2097168,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097166,
	2097169,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097166,
	2097169,
	2097156,
	0);
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
	'LOG::LogInfo(message:"Starting subtract test") ;
select any event_instance from instances of EV;
//subtraction (-) operation
// perform each test combination with the following types:
     // integer integer
     // integer real
     // real integer
     // real real

  // local existing    local existing
assign temp1 = 13;
assign temp2 = 7;
assign temp3 = 2.4;
assign temp4 = 23.7;
assign t1 = temp1 - temp2 ;
if ( t1 == 6 )
  LOG::LogSuccess(message:" subtract local_existing local_existing ( 6 )") ;
else
  LOG::LogFailure(message:" subtract local_existing local_existing ( 6 )") ;
end if;
 
assign t2 = temp1 - temp3 ;
LOG::LogReal(r: t2 , message:" subtract local_existing local_existing ( 10.6 )") ;
 
assign t3 = temp3 - temp2 ;
LOG::LogReal(r: t3 , message:" subtract local_existing local_existing ( -4.6 )") ;
 
assign t4 = temp4 - temp3 ;
LOG::LogReal(r: t4 , message:" subtract local_existing local_existing ( 21.3 )") ;
 
  // local existing    rcvd_evt
// assume
// rcvd_evt.tint1 = 17
// rcvd_evt.treal1 = 2.781828
assign t5 = temp1 - rcvd_evt.tint1 ;
if ( t5 == -4 )
  LOG::LogSuccess(message:" subtract local_existing rcvd_evt ( -4 )") ;
else
  LOG::LogFailure(message:" subtract local_existing rcvd_evt ( -4 )") ;
end if;
 
assign t6 = temp1 - rcvd_evt.treal1 ;
LOG::LogReal(r: t6 , message:" subtract local_existing rcvd_evt ( 10.218172 )") ;
 
assign t7 = temp3 - rcvd_evt.tint1 ;
LOG::LogReal(r: t7 , message:" subtract local_existing rcvd_evt ( -14.6 )") ;
 
assign t8 = temp4 - rcvd_evt.treal1 ;
LOG::LogReal(r: t8 , message:" subtract local_existing rcvd_evt ( 20.918172 )") ;
 
  // local existing    constant
assign t9 = temp1 - 3 ;
if ( t9 == 10 )
  LOG::LogSuccess(message:" subtract local_existing constant ( 10 )") ;
else
  LOG::LogFailure(message:" subtract local_existing constant ( 10 )") ;
end if;
 
assign t10 = temp1 - 3.14 ;
LOG::LogReal(r: t10 , message:" subtract local_existing constant ( 9.86 )") ;
 
assign t11 = temp3 - 5.6 ;
LOG::LogReal(r: t11 , message:" subtract local_existing constant ( -3.2 )") ;
 
assign t12 = temp4 - 16.9 ;
LOG::LogReal(r: t12 , message:" subtract local_existing constant ( 6.8 )") ;
 
  // local existing    self.attribute
// assume self.i = 10
// assume self.r = 3.14
assign t13 = temp1 - self.i ;
if ( t13 == 3 )
  LOG::LogSuccess(message:" subtract local_existing self.attribute ( 3 )") ;
else
  LOG::LogFailure(message:" subtract local_existing self.attribute ( 3 )") ;
end if;
 
assign t14 = temp1 - self.r ;
LOG::LogReal(r: t14 , message:" subtract local_existing self.attribute ( 9.86 )") ;
 
assign t15 = temp3 - self.i ;
LOG::LogReal(r: t15 , message:" subtract local_existing self.attribute ( -7.6 )") ;
 
assign t16 = temp4 - self.r ;
LOG::LogReal(r: t16 , message:" subtract local_existing self.attribute ( 20.56 )") ;
 
  // local existing    self.referential attribute
assign t17 = temp1 - self.etob_id ;
if ( t17 == 11 )
  LOG::LogSuccess(message:" subtract local_existing self.referential_attribute ( 11 )") ;
else
  LOG::LogFailure(message:" subtract local_existing self.referential_attribute ( 11 )") ;
end if;
 
assign t18 = temp1 - self.r1r ;
LOG::LogReal(r: t18 , message:" subtract local_existing self.referential_attribute ( 10.8282 )") ;
 
assign t19 = temp3 - self.etob_id ;
LOG::LogReal(r: t19 , message:" subtract local_existing self.referential_attribute ( 0.4 )") ;
 
assign t20 = temp4 - self.r1r ;
LOG::LogReal(r: t20 , message:" subtract local_existing self.referential_attribute ( 21.5282 )") ;
 
  // local existing    other.attribute (saf)
select any saf1 from instances of ETOB;
assign t21 = temp1 - saf1.etob_id ;
if ( t21 == 11 )
  LOG::LogSuccess(message:" subtract local_existing other.attribute(saf) ( 11 )") ;
else
  LOG::LogFailure(message:" subtract local_existing other.attribute(saf) ( 11 )") ;
end if;
 
assign t22 = temp1 - saf1.r ;
LOG::LogReal(r: t22 , message:" subtract local_existing other.attribute(saf) ( 10.8282 )") ;
 
assign t23 = temp3 - saf1.etob_id ;
LOG::LogReal(r: t23 , message:" subtract local_existing other.attribute(saf) ( 0.4 )") ;
 
assign t24 = temp4 - saf1.r ;
LOG::LogReal(r: t24 , message:" subtract local_existing other.attribute(saf) ( 21.5282 )") ;
 
  // local existing    other.attribute (smf)
select many smf1 from instances of ETOB;
for each smf in smf1
  if ( smf.etob_id == 2 )
assign t25 = temp1 - smf.etob_id ;
if ( t25 == 11 )
  LOG::LogSuccess(message:" subtract local_existing other.attribute(smf) ( 11 )") ;
else
  LOG::LogFailure(message:" subtract local_existing other.attribute(smf) ( 11 )") ;
end if;
 
assign t26 = temp1 - smf.r ;
LOG::LogReal(r: t26 , message:" subtract local_existing other.attribute(smf) ( 10.8282 )") ;
 
assign t27 = temp3 - smf.etob_id ;
LOG::LogReal(r: t27 , message:" subtract local_existing other.attribute(smf) ( 0.4 )") ;
 
assign t28 = temp4 - smf.r ;
LOG::LogReal(r: t28 , message:" subtract local_existing other.attribute(smf) ( 21.5282 )") ;
 
  // only do this once
  end if;
end for;
  // local existing    other.attribute (sor)
select one sor1 related by self->ETOB[R1];
assign t29 = temp1 - sor1.etob_id ;
if ( t29 == 11 )
  LOG::LogSuccess(message:" subtract local_existing other.attribute(sor) ( 11 )") ;
else
  LOG::LogFailure(message:" subtract local_existing other.attribute(sor) ( 11 )") ;
end if;
 
assign t30 = temp1 - sor1.r ;
LOG::LogReal(r: t30 , message:" subtract local_existing other.attribute(sor) ( 10.8282 )") ;
 
assign t31 = temp3 - sor1.etob_id ;
LOG::LogReal(r: t31 , message:" subtract local_existing other.attribute(sor) ( 0.4 )") ;
 
assign t32 = temp4 - sor1.r ;
LOG::LogReal(r: t32 , message:" subtract local_existing other.attribute(sor) ( 21.5282 )") ;
 
  // local existing    other.attribute (sar)
select any sar1 related by self->ETOB[R8];
assign t33 = temp1 - sar1.etob_id ;
if ( t33 == 11 )
  LOG::LogSuccess(message:" subtract local_existing other.attribute(sar) ( 11 )") ;
else
  LOG::LogFailure(message:" subtract local_existing other.attribute(sar) ( 11 )") ;
end if;
 
assign t34 = temp1 - sar1.r ;
LOG::LogReal(r: t34 , message:" subtract local_existing other.attribute(sar) ( 10.8282 )") ;
 
assign t35 = temp3 - sar1.etob_id ;
LOG::LogReal(r: t35 , message:" subtract local_existing other.attribute(sar) ( 0.4 )") ;
 
assign t36 = temp4 - sar1.r ;
LOG::LogReal(r: t36 , message:" subtract local_existing other.attribute(sar) ( 21.5282 )") ;
 
  // local existing    other.attribute (smr)
select many smr1 related by self->ETOB[R8];
for each smr in smr1
  if ( smr.etob_id == 2 )
assign t37 = temp1 - smr.etob_id ;
if ( t37 == 11 )
  LOG::LogSuccess(message:" subtract local_existing other.attribute(smr) ( 11 )") ;
else
  LOG::LogFailure(message:" subtract local_existing other.attribute(smr) ( 11 )") ;
end if;
 
assign t38 = temp1 - smr.r ;
LOG::LogReal(r: t38 , message:" subtract local_existing other.attribute(smr) ( 10.8282 )") ;
 
assign t39 = temp3 - smr.etob_id ;
LOG::LogReal(r: t39 , message:" subtract local_existing other.attribute(smr) ( 0.4 )") ;
 
assign t40 = temp4 - smr.r ;
LOG::LogReal(r: t40 , message:" subtract local_existing other.attribute(smr) ( 21.5282 )") ;
 
  end if;
end for;
  // local existing    other.referential attribute (saf)
assign t41 = temp1 - saf1.etoc_id ;
if ( t41 == 9 )
  LOG::LogSuccess(message:" subtract local_existing other.referential_attribute(saf) ( 9 )") ;
else
  LOG::LogFailure(message:" subtract local_existing other.referential_attribute(saf) ( 9 )") ;
end if;
 
assign t42 = temp1 - saf1.r6r ;
LOG::LogReal(r: t42 , message:" subtract local_existing other.referential_attribute(saf) ( 1.689 )") ;
 
assign t43 = temp3 - saf1.etoc_id ;
LOG::LogReal(r: t43 , message:" subtract local_existing other.referential_attribute(saf) ( -1.6 )") ;
 
assign t44 = temp4 - saf1.r6r ;
LOG::LogReal(r: t44 , message:" subtract local_existing other.referential_attribute(saf) ( 12.389 )") ;
 
  // local existing    other.referential attribute (smf)
for each smf in smf1
  if ( smf.etob_id == 2 )
assign t45 = temp1 - smf.etoc_id ;
if ( t45 == 9 )
  LOG::LogSuccess(message:" subtract local_existing other.referential_attribute(smf) ( 9 )") ;
else
  LOG::LogFailure(message:" subtract local_existing other.referential_attribute(smf) ( 9 )") ;
end if;
 
assign t46 = temp1 - smf.r6r ;
LOG::LogReal(r: t46 , message:" subtract local_existing other.referential_attribute(smf) ( 1.689 )") ;
 
assign t47 = temp3 - smf.etoc_id ;
LOG::LogReal(r: t47 , message:" subtract local_existing other.referential_attribute(smf) ( -1.6 )") ;
 
assign t48 = temp4 - smf.r6r ;
LOG::LogReal(r: t48 , message:" subtract local_existing other.referential_attribute(smf) ( 12.389 )") ;
 
  end if;
end for;
  // local existing    other.referential attribute (sor)
assign t49 = temp1 - sor1.etoc_id ;
if ( t49 == 9 )
  LOG::LogSuccess(message:" subtract local_existing other.referential_attribute(sor) ( 9 )") ;
else
  LOG::LogFailure(message:" subtract local_existing other.referential_attribute(sor) ( 9 )") ;
end if;
 
assign t50 = temp1 - sor1.r6r ;
LOG::LogReal(r: t50 , message:" subtract local_existing other.referential_attribute(sor) ( 1.689 )") ;
 
assign t51 = temp3 - sor1.etoc_id ;
LOG::LogReal(r: t51 , message:" subtract local_existing other.referential_attribute(sor) ( -1.6 )") ;
 
assign t52 = temp4 - sor1.r6r ;
LOG::LogReal(r: t52 , message:" subtract local_existing other.referential_attribute(sor) ( 12.389 )") ;
 
  // local existing    other.referential attribute (sar)
assign t53 = temp1 - sar1.etoc_id ;
if ( t53 == 9 )
  LOG::LogSuccess(message:" subtract local_existing other.referential_attribute(sar) ( 9 )") ;
else
  LOG::LogFailure(message:" subtract local_existing other.referential_attribute(sar) ( 9 )") ;
end if;
 
assign t54 = temp1 - sar1.r6r ;
LOG::LogReal(r: t54 , message:" subtract local_existing other.referential_attribute(sar) ( 1.689 )") ;
 
assign t55 = temp3 - sar1.etoc_id ;
LOG::LogReal(r: t55 , message:" subtract local_existing other.referential_attribute(sar) ( -1.6 )") ;
 
assign t56 = temp4 - sar1.r6r ;
LOG::LogReal(r: t56 , message:" subtract local_existing other.referential_attribute(sar) ( 12.389 )") ;
 
  // local existing    other.referential attribute (smr)
for each smr in smr1
  if ( smr.etob_id == 2 )
assign t57 = temp1 - smr.etoc_id ;
if ( t57 == 9 )
  LOG::LogSuccess(message:" subtract local_existing other.referential_attribute(smr) ( 9 )") ;
else
  LOG::LogFailure(message:" subtract local_existing other.referential_attribute(smr) ( 9 )") ;
end if;
 
assign t58 = temp1 - smr.r6r ;
LOG::LogReal(r: t58 , message:" subtract local_existing other.referential_attribute(smr) ( 1.689 )") ;
 
assign t59 = temp3 - smr.etoc_id ;
LOG::LogReal(r: t59 , message:" subtract local_existing other.referential_attribute(smr) ( -1.6 )") ;
 
assign t60 = temp4 - smr.r6r ;
LOG::LogReal(r: t60 , message:" subtract local_existing other.referential_attribute(smr) ( 12.389 )") ;
 
  end if;
end for;
  // rcvd_evt    local existing
assign t61 = rcvd_evt.tint1 - temp1 ;
if ( t61 == 4 )
  LOG::LogSuccess(message:" subtract rcvd_evt local_existing ( 4 )") ;
else
  LOG::LogFailure(message:" subtract rcvd_evt local_existing ( 4 )") ;
end if;
 
assign t62 = rcvd_evt.tint1 - temp3 ;
LOG::LogReal(r: t62 , message:" subtract rcvd_evt local_existing ( 14.6 )") ;
 
assign t63 = rcvd_evt.treal1 - temp1 ;
LOG::LogReal(r: t63 , message:" subtract rcvd_evt local_existing ( -10.218172 )") ;
 
assign t64 = rcvd_evt.treal1 - temp3 ;
LOG::LogReal(r: t64 , message:" subtract rcvd_evt local_existing ( 0.381828 )") ;
 
  // rcvd_evt    rcvd_evt
assign t65 = rcvd_evt.tint1 - rcvd_evt.tint2 ;
if ( t65 == 4 )
  LOG::LogSuccess(message:" subtract rcvd_evt rcvd_evt ( 4 )") ;
else
  LOG::LogFailure(message:" subtract rcvd_evt rcvd_evt ( 4 )") ;
end if;
 
assign t66 = rcvd_evt.tint1 - rcvd_evt.treal2 ;
LOG::LogReal(r: t66 , message:" subtract rcvd_evt rcvd_evt ( 5.8 )") ;
 
assign t67 = rcvd_evt.treal1 - rcvd_evt.tint2 ;
LOG::LogReal(r: t67 , message:" subtract rcvd_evt rcvd_evt ( -10.218172 )") ;
 
assign t68 = rcvd_evt.treal1 - rcvd_evt.treal2 ;
LOG::LogReal(r: t68 , message:" subtract rcvd_evt rcvd_evt ( -8.418172 )") ;
 
  // rcvd_evt    constant
assign t69 = rcvd_evt.tint1 - 1 ;
if ( t69 == 16 )
  LOG::LogSuccess(message:" subtract rcvd_evt constant ( 16 )") ;
else
  LOG::LogFailure(message:" subtract rcvd_evt constant ( 16 )") ;
end if;
 
assign t70 = rcvd_evt.tint1 - 1.0 ;
LOG::LogReal(r: t70 , message:" subtract rcvd_evt constant ( 16.0 )") ;
 
assign t71 = rcvd_evt.treal1 - 1 ;
LOG::LogReal(r: t71 , message:" subtract rcvd_evt constant ( 1.781828 )") ;
 
assign t72 = rcvd_evt.treal1 - 0.5 ;
LOG::LogReal(r: t72 , message:" subtract rcvd_evt constant ( 2.281828 )") ;
 
  // rcvd_evt    self.attribute
assign t73 = rcvd_evt.tint1 - self.i ;
if ( t73 == 7 )
  LOG::LogSuccess(message:" subtract rcvd_evt self.attribute ( 7 )") ;
else
  LOG::LogFailure(message:" subtract rcvd_evt self.attribute ( 7 )") ;
end if;
 
assign t74 = rcvd_evt.tint1 - self.r ;
LOG::LogReal(r: t74 , message:" subtract rcvd_evt self.attribute ( 13.86 )") ;
 
assign t75 = rcvd_evt.treal1 - self.i ;
LOG::LogReal(r: t75 , message:" subtract rcvd_evt self.attribute ( -7.218172 )") ;
 
assign t76 = rcvd_evt.treal1 - self.r ;
LOG::LogReal(r: t76 , message:" subtract rcvd_evt self.attribute ( -0.358172 )") ;
 
  // rcvd_evt    self.referential attribute
assign t77 = rcvd_evt.tint1 - self.etob_id ;
if ( t77 == 15 )
  LOG::LogSuccess(message:" subtract rcvd_evt self.referential_attribute ( 15 )") ;
else
  LOG::LogFailure(message:" subtract rcvd_evt self.referential_attribute ( 15 )") ;
end if;
 
assign t78 = rcvd_evt.tint1 - self.r1r ;
LOG::LogReal(r: t78 , message:" subtract rcvd_evt self.referential_attribute ( 14.8282 )") ;
 
assign t79 = rcvd_evt.treal1 - self.etob_id ;
LOG::LogReal(r: t79 , message:" subtract rcvd_evt self.referential_attribute ( 0.781828 )") ;
 
assign t80 = rcvd_evt.treal1 - self.r1r ;
LOG::LogReal(r: t80 , message:" subtract rcvd_evt self.referential_attribute ( 0.610028 )") ;
 
  // rcvd_evt    other.attribute (saf)
assign t81 = rcvd_evt.tint1 - saf1.etob_id ;
if ( t81 == 15 )
  LOG::LogSuccess(message:" subtract rcvd_evt other.attribute(saf) ( 15 )") ;
else
  LOG::LogFailure(message:" subtract rcvd_evt other.attribute(saf) ( 15 )") ;
end if;
 
assign t82 = rcvd_evt.tint1 - saf1.r ;
LOG::LogReal(r: t82 , message:" subtract rcvd_evt other.attribute(saf) ( 14.8282 )") ;
 
assign t83 = rcvd_evt.treal1 - saf1.etob_id ;
LOG::LogReal(r: t83 , message:" subtract rcvd_evt other.attribute(saf) ( 0.781828 )") ;
 
assign t84 = rcvd_evt.treal1 - saf1.r ;
LOG::LogReal(r: t84 , message:" subtract rcvd_evt other.attribute(saf) ( 0.610028 )") ;
 
  // rcvd_evt    other.attribute (smf)
  // rcvd_evt    other.attribute (sor)
  // rcvd_evt    other.attribute (sar)
  // rcvd_evt    other.attribute (smr)
  // rcvd_evt    other.referential attribute (saf)
assign t101 = rcvd_evt.tint1 - saf1.etoc_id ;
if ( t101 == 13 )
  LOG::LogSuccess(message:" subtract rcvd_evt other.referential_attribute(saf) ( 13 )") ;
else
  LOG::LogFailure(message:" subtract rcvd_evt other.referential_attribute(saf) ( 13 )") ;
end if;
 
  // rcvd_evt    other.referential attribute (smf)
  // rcvd_evt    other.referential attribute (sor)
  // rcvd_evt    other.referential attribute (sar)
  // rcvd_evt    other.referential attribute (smr)
  // constant    local existing
assign t121 = 27 - temp1 ;
if ( t121 == 14 )
  LOG::LogSuccess(message:" subtract constant local_existing ( 14 )") ;
else
  LOG::LogFailure(message:" subtract constant local_existing ( 14 )") ;
end if;
 
  // constant    rcvd_evt
assign t126 = 27 - rcvd_evt.treal2 ;
LOG::LogReal(r: t126 , message:" subtract constant rcvd_evt ( 15.8 )") ;
 
  // constant    constant
assign t131 = 14.3 - 5 ;
LOG::LogReal(r: t131 , message:" subtract constant constant ( 9.3 )") ;
 
  // constant    self.attribute
assign t136 = 14.3 - self.r ;
LOG::LogReal(r: t136 , message:" subtract constant self.attribute ( 11.16 )") ;
 
  // constant    self.referential attribute
assign t137 = 27 - self.etob_id ;
if ( t137 == 25 )
  LOG::LogSuccess(message:" subtract constant self.referential_attribute ( 25 )") ;
else
  LOG::LogFailure(message:" subtract constant self.referential_attribute ( 25 )") ;
end if;
 
  // constant    other.attribute (saf)
assign t141 = 27 - saf1.etob_id ;
if ( t141 == 25 )
  LOG::LogSuccess(message:" subtract constant other.attribute(saf) ( 25 )") ;
else
  LOG::LogFailure(message:" subtract constant other.attribute(saf) ( 25 )") ;
end if;
 
  // constant    other.attribute (smf)
  // constant    other.attribute (sor)
  // constant    other.attribute (sar)
  // constant    other.attribute (smr)
  // constant    other.referential attribute (saf)
assign t161 = 27 - saf1.etoc_id ;
if ( t161 == 23 )
  LOG::LogSuccess(message:" subtract constant other.referential_attribute(saf) ( 23 )") ;
else
  LOG::LogFailure(message:" subtract constant other.referential_attribute(saf) ( 23 )") ;
end if;
 
  // constant    other.referential attribute (smf)
  // constant    other.referential attribute (sor)
  // constant    other.referential attribute (sar)
  // constant    other.referential attribute (smr)
  // self.attribute    local existing
assign t181 = self.i - temp1 ;
if ( t181 == -3 )
  LOG::LogSuccess(message:" subtract self.attribute local_existing ( -3 )") ;
else
  LOG::LogFailure(message:" subtract self.attribute local_existing ( -3 )") ;
end if;
 
  // self.attribute    rcvd_evt
assign t186 = self.i - rcvd_evt.treal2 ;
LOG::LogReal(r: t186 , message:" subtract self.attribute rcvd_evt ( -1.2 )") ;
 
  // self.attribute    constant
assign t191 = self.r - 2 ;
LOG::LogReal(r: t191 , message:" subtract self.attribute constant ( 1.14 )") ;
 
  // self.attribute    self.attribute
assign t196 = self.i - self.r ;
LOG::LogReal(r: t196 , message:" subtract self.attribute self.attribute ( 6.86 )") ;
 
  // self.attribute    self.referential attribute
assign t197 = self.i - self.etob_id ;
if ( t197 == 8 )
  LOG::LogSuccess(message:" subtract self.attribute self.referential_attribute ( 8 )") ;
else
  LOG::LogFailure(message:" subtract self.attribute self.referential_attribute ( 8 )") ;
end if;
 
  // self.attribute    other.attribute (saf)
assign t201 = self.i - saf1.etob_id ;
if ( t201 == 8 )
  LOG::LogSuccess(message:" subtract self.attribute other.attribute(saf) ( 8 )") ;
else
  LOG::LogFailure(message:" subtract self.attribute other.attribute(saf) ( 8 )") ;
end if;
 
  // self.attribute    other.attribute (smf)
  // self.attribute    other.attribute (sor)
  // self.attribute    other.attribute (sar)
  // self.attribute    other.attribute (smr)
  // self.attribute    other.referential attribute (saf)
assign t221 = self.i - saf1.etoc_id ;
if ( t221 == 6 )
  LOG::LogSuccess(message:" subtract self.attribute other.referential_attribute(saf) ( 6 )") ;
else
  LOG::LogFailure(message:" subtract self.attribute other.referential_attribute(saf) ( 6 )") ;
end if;
 
  // self.attribute    other.referential attribute (smf)
  // self.attribute    other.referential attribute (sor)
  // self.attribute    other.referential attribute (sar)
  // self.attribute    other.referential attribute (smr)
  // self.referential attribute    local existing
assign t241 = self.etob_id - temp1 ;
if ( t241 == -11 )
  LOG::LogSuccess(message:" subtract self.referential_attribute local_existing ( -11 )") ;
else
  LOG::LogFailure(message:" subtract self.referential_attribute local_existing ( -11 )") ;
end if;
 
  // self.referential attribute    rcvd_evt
assign t246 = self.etob_id - rcvd_evt.treal2 ;
LOG::LogReal(r: t246 , message:" subtract self.referential_attribute rcvd_evt ( -9.2 )") ;
 
  // self.referential attribute    constant
assign t251 = self.r1r - 2 ;
LOG::LogReal(r: t251 , message:" subtract self.referential_attribute constant ( 0.1718 )") ;
 
  // self.referential attribute    self.attribute
assign t256 = self.r1r - self.r ;
LOG::LogReal(r: t256 , message:" subtract self.referential_attribute self.attribute ( -0.9682 )") ;
 
  // self.referential attribute    self.referential attribute
assign t258 = self.etob_id - self.r1r ;
LOG::LogReal(r: t258 , message:" subtract self.referential_attribute self.referential_attribute ( -0.1718 )") ;
 
  // self.referential attribute    other.attribute (saf)
assign t261 = self.etob_id - saf1.etob_id ;
if ( t261 == 0 )
  LOG::LogSuccess(message:" subtract self.referential_attribute other.attribute(saf) ( 0 )") ;
else
  LOG::LogFailure(message:" subtract self.referential_attribute other.attribute(saf) ( 0 )") ;
end if;
 
  // self.referential attribute    other.attribute (smf)
  // self.referential attribute    other.attribute (sor)
  // self.referential attribute    other.attribute (sar)
  // self.referential attribute    other.attribute (smr)
  // self.referential attribute    other.referential attribute (saf)
assign t281 = self.etob_id - saf1.etoc_id ;
if ( t281 == -2 )
  LOG::LogSuccess(message:" subtract self.referential_attribute other.referential_attribute(saf) ( -2 )") ;
else
  LOG::LogFailure(message:" subtract self.referential_attribute other.referential_attribute(saf) ( -2 )") ;
end if;
 
  // self.referential attribute    other.referential attribute (smf)
  // self.referential attribute    other.referential attribute (sor)
  // self.referential attribute    other.referential attribute (sar)
  // self.referential attribute    other.referential attribute (smr)
  // other.attribute (saf)    local existing
  // other.attribute (saf)    rcvd_evt
  // other.attribute (saf)    constant
  // other.attribute (saf)    self.attribute
  // other.attribute (saf)    self.referential attribute
  // other.attribute (saf)    other.attribute (saf)
  // other.attribute (saf)    other.attribute (smf)
  // other.attribute (saf)    other.attribute (sor)
  // other.attribute (saf)    other.attribute (sar)
  // other.attribute (saf)    other.attribute (smr)
  // other.attribute (saf)    other.referential attribute (saf)
  // other.attribute (saf)    other.referential attribute (smf)
  // other.attribute (saf)    other.referential attribute (sor)
  // other.attribute (saf)    other.referential attribute (sar)
  // other.attribute (saf)    other.referential attribute (smr)
  // other.attribute (smf)    local existing
  // other.attribute (smf)    rcvd_evt
  // other.attribute (smf)    constant
  // other.attribute (smf)    self.attribute
  // other.attribute (smf)    self.referential attribute
  // other.attribute (smf)    other.attribute (saf)
  // other.attribute (smf)    other.attribute (smf)
  // other.attribute (smf)    other.attribute (sor)
  // other.attribute (smf)    other.attribute (sar)
  // other.attribute (smf)    other.attribute (smr)
  // other.attribute (smf)    other.referential attribute (saf)
  // other.attribute (smf)    other.referential attribute (smf)
  // other.attribute (smf)    other.referential attribute (sor)
  // other.attribute (smf)    other.referential attribute (sar)
  // other.attribute (smf)    other.referential attribute (smr)
  // other.attribute (sor)    local existing
  // other.attribute (sor)    rcvd_evt
  // other.attribute (sor)    constant
  // other.attribute (sor)    self.attribute
  // other.attribute (sor)    self.referential attribute
  // other.attribute (sor)    other.attribute (saf)
  // other.attribute (sor)    other.attribute (smf)
  // other.attribute (sor)    other.attribute (sor)
  // other.attribute (sor)    other.attribute (sar)
  // other.attribute (sor)    other.attribute (smr)
  // other.attribute (sor)    other.referential attribute (saf)
  // other.attribute (sor)    other.referential attribute (smf)
  // other.attribute (sor)    other.referential attribute (sor)
  // other.attribute (sor)    other.referential attribute (sar)
  // other.attribute (sor)    other.referential attribute (smr)
  // other.attribute (sar)    local existing
  // other.attribute (sar)    rcvd_evt
  // other.attribute (sar)    constant
  // other.attribute (sar)    self.attribute
  // other.attribute (sar)    self.referential attribute
  // other.attribute (sar)    other.attribute (saf)
  // other.attribute (sar)    other.attribute (smf)
  // other.attribute (sar)    other.attribute (sor)
  // other.attribute (sar)    other.attribute (sar)
  // other.attribute (sar)    other.attribute (smr)
  // other.attribute (sar)    other.referential attribute (saf)
  // other.attribute (sar)    other.referential attribute (smf)
  // other.attribute (sar)    other.referential attribute (sor)
  // other.attribute (sar)    other.referential attribute (sar)
  // other.attribute (sar)    other.referential attribute (smr)
  // other.attribute (smr)    local existing
  // other.attribute (smr)    rcvd_evt
  // other.attribute (smr)    constant
  // other.attribute (smr)    self.attribute
  // other.attribute (smr)    self.referential attribute
  // other.attribute (smr)    other.attribute (saf)
  // other.attribute (smr)    other.attribute (smf)
  // other.attribute (smr)    other.attribute (sor)
  // other.attribute (smr)    other.attribute (sar)
  // other.attribute (smr)    other.attribute (smr)
  // other.attribute (smr)    other.referential attribute (saf)
  // other.attribute (smr)    other.referential attribute (smf)
  // other.attribute (smr)    other.referential attribute (sor)
  // other.attribute (smr)    other.referential attribute (sar)
  // other.attribute (smr)    other.referential attribute (smr)
  // other.referential attribute (saf)    local existing
  // other.referential attribute (saf)    rcvd_evt
  // other.referential attribute (saf)    constant
  // other.referential attribute (saf)    self.attribute
  // other.referential attribute (saf)    self.referential attribute
  // other.referential attribute (saf)    other.attribute (saf)
  // other.referential attribute (saf)    other.attribute (smf)
  // other.referential attribute (saf)    other.attribute (sor)
  // other.referential attribute (saf)    other.attribute (sar)
  // other.referential attribute (saf)    other.attribute (smr)
  // other.referential attribute (saf)    other.referential attribute (saf)
  // other.referential attribute (saf)    other.referential attribute (smf)
  // other.referential attribute (saf)    other.referential attribute (sor)
  // other.referential attribute (saf)    other.referential attribute (sar)
  // other.referential attribute (saf)    other.referential attribute (smr)
  // other.referential attribute (smf)    local existing
  // other.referential attribute (smf)    rcvd_evt
  // other.referential attribute (smf)    constant
  // other.referential attribute (smf)    self.attribute
  // other.referential attribute (smf)    self.referential attribute
  // other.referential attribute (smf)    other.attribute (saf)
  // other.referential attribute (smf)    other.attribute (smf)
  // other.referential attribute (smf)    other.attribute (sor)
  // other.referential attribute (smf)    other.attribute (sar)
  // other.referential attribute (smf)    other.attribute (smr)
  // other.referential attribute (smf)    other.referential attribute (saf)
  // other.referential attribute (smf)    other.referential attribute (smf)
  // other.referential attribute (smf)    other.referential attribute (sor)
  // other.referential attribute (smf)    other.referential attribute (sar)
  // other.referential attribute (smf)    other.referential attribute (smr)
  // other.referential attribute (sor)    local existing
  // other.referential attribute (sor)    rcvd_evt
  // other.referential attribute (sor)    constant
  // other.referential attribute (sor)    self.attribute
  // other.referential attribute (sor)    self.referential attribute
  // other.referential attribute (sor)    other.attribute (saf)
  // other.referential attribute (sor)    other.attribute (smf)
  // other.referential attribute (sor)    other.attribute (sor)
  // other.referential attribute (sor)    other.attribute (sar)
  // other.referential attribute (sor)    other.attribute (smr)
  // other.referential attribute (sor)    other.referential attribute (saf)
  // other.referential attribute (sor)    other.referential attribute (smf)
  // other.referential attribute (sor)    other.referential attribute (sor)
  // other.referential attribute (sor)    other.referential attribute (sar)
  // other.referential attribute (sor)    other.referential attribute (smr)
  // other.referential attribute (sar)    local existing
  // other.referential attribute (sar)    rcvd_evt
  // other.referential attribute (sar)    constant
  // other.referential attribute (sar)    self.attribute
  // other.referential attribute (sar)    self.referential attribute
  // other.referential attribute (sar)    other.attribute (saf)
  // other.referential attribute (sar)    other.attribute (smf)
  // other.referential attribute (sar)    other.attribute (sor)
  // other.referential attribute (sar)    other.attribute (sar)
  // other.referential attribute (sar)    other.attribute (smr)
  // other.referential attribute (sar)    other.referential attribute (saf)
  // other.referential attribute (sar)    other.referential attribute (smf)
  // other.referential attribute (sar)    other.referential attribute (sor)
  // other.referential attribute (sar)    other.referential attribute (sar)
  // other.referential attribute (sar)    other.referential attribute (smr)
  // other.referential attribute (sar)    local existing
  // other.referential attribute (smr)    rcvd_evt
  // other.referential attribute (smr)    constant
  // other.referential attribute (smr)    self.attribute
  // other.referential attribute (smr)    self.referential attribute
  // other.referential attribute (smr)    other.attribute (saf)
  // other.referential attribute (smr)    other.attribute (smf)
  // other.referential attribute (smr)    other.attribute (sor)
  // other.referential attribute (smr)    other.attribute (sar)
  // other.referential attribute (smr)    other.attribute (smr)
  // other.referential attribute (smr)    other.referential attribute (saf)
  // other.referential attribute (smr)    other.referential attribute (smf)
  // other.referential attribute (smr)    other.referential attribute (sor)
  // other.referential attribute (smr)    other.referential attribute (sar)
  // other.referential attribute (smr)    other.referential attribute (smr)
 
LOG::LogInfo(message:"Completed subtract test") ;

//generate event to commence next test
bridge d = TIM::current_date();
bridge ts = TIM::current_clock();
create event instance evinst of EV1:''Event Instance''(message:"timer event 1.2")  to event_instance;
bridge tim = TIM::timer_start ( microseconds:1000000, event_inst:evinst);
generate ET15:''Start assign test''(ttrue:true, tfalse:false, i:11, r:12.34, s:"fit",
   u:self.u, d: d, ts: ts, tim:tim, ev:evinst) to self;

',
	'');
INSERT INTO SM_STATE
	VALUES (2097167,
	2097156,
	0,
	'Testing assign',
	15,
	0);
INSERT INTO SM_CH
	VALUES (2097167,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097167,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097167,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097167,
	2097154,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097167,
	2097155,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097167,
	2097155,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097167,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097167,
	2097156,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097167,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097167,
	2097157,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097167,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097167,
	2097158,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097167,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097167,
	2097159,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097167,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097167,
	2097160,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097167,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097167,
	2097161,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097167,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097167,
	2097162,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097167,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097167,
	2097163,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097167,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097167,
	2097164,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097167,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097167,
	2097165,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097167,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097167,
	2097166,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097167,
	2097167,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097167,
	2097168,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097167,
	2097168,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097167,
	2097169,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097167,
	2097169,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097167,
	2097156,
	2097167);
INSERT INTO SM_AH
	VALUES (2097167,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097167,
	2097156,
	1,
	'LOG::LogInfo(message:"Starting assign test") ;
select any event_instance from instances of EV;
// Assign operation

  // boolean
assign t1 = true;
if ( t1 == true )
  LOG::LogSuccess(message:"assign local_initial constant ( true )") ;
else
  LOG::LogFailure(message:"assign local_initial constant ( true )") ;
end if;
 
assign t2 = false;
if ( t2 == false )
  LOG::LogSuccess(message:"assign local_initial constant ( false )") ;
else
  LOG::LogFailure(message:"assign local_initial constant ( false )") ;
end if;
 
  // integer
assign t3 = 5;
if ( t3 == 5 )
  LOG::LogSuccess(message:"assign local_initial constant ( 5 )") ;
else
  LOG::LogFailure(message:"assign local_initial constant ( 5 )") ;
end if;
 
  // real
assign t4 = 16.98;
LOG::LogInfo( message:"assign local_initial constant ( 16.98 )") ;
 
  // string
assign t5 = "hissy";
if ( t5 == "hissy" )
  LOG::LogSuccess(message:"assign local_initial constant ( hissy )") ;
else
  LOG::LogFailure(message:"assign local_initial constant ( hissy )") ;
end if;

  // boolean
assign t6 = t1;
if ( t6 == true )
  LOG::LogSuccess(message:"assign local_initial local_existing ( true )") ;
else
  LOG::LogFailure(message:"assign local_initial local_existing ( true )") ;
end if;
 
assign t7 = t2;
if ( t7 == false )
  LOG::LogSuccess(message:"assign local_initial local_existing ( false )") ;
else
  LOG::LogFailure(message:"assign local_initial local_existing ( false )") ;
end if;
 
  // integer
assign t8 = t3;
if ( t8 == 5 )
  LOG::LogSuccess(message:"assign local_initial local_existing ( 5 )") ;
else
  LOG::LogFailure(message:"assign local_initial local_existing ( 5 )") ;
end if;
 
  // real
assign t9 = t4;
LOG::LogInfo( message:"assign local_initial local_existing ( 16.98 )") ;
 
  // string
assign t10 = t5;
if ( t10 == "hissy" )
  LOG::LogSuccess(message:"assign local_initial local_existing ( hissy )") ;
else
  LOG::LogFailure(message:"assign local_initial local_existing ( hissy )") ;
end if;

// unique_id
assign temp_unique_id = self.u;
assign t11 = temp_unique_id;
if ( t11 == self.u )
  LOG::LogSuccess(message:"assign local_initial local_existing ( self.u )") ;
else
  LOG::LogFailure(message:"assign local_initial local_existing ( self.u )") ;
end if;
 
  // date
bridge temp_date = TIM::create_date(second:30, minute:56, hour:19, day:3, month:1, year:1966);
assign t12 = temp_date;
LOG::LogDate(d:t12, message:"assign local_initial local_existing ( 3/1/1996 19:56:30 )");

  // timestamp
bridge temp_timestamp = TIM::current_clock();
assign t13 = temp_timestamp;
LOG::LogTime(t:t13, message:"assign local_initial local_existing ( )");

  // inst_ref<Timer>
create event instance ev_inst1 of EV1:''Event Instance''(message:"timer event")to event_instance ;
bridge temp_timer = TIM::timer_start ( microseconds:1000000, event_inst:ev_inst1 );
assign t14 = temp_timer;
if ( t14 == temp_timer )
  LOG::LogSuccess(message:"assign local_initial local_existing ( temp_timer )") ;
else
  LOG::LogFailure(message:"assign local_initial local_existing ( temp_timer )") ;
end if;
 
  // inst<Event>
assign t15 = ev_inst1;
LOG::LogInfo( message:"assign local_initial local_existing ( LOG1(timer event) )") ;

  // inst_ref<Object>
select any none from instances of NOI;
assign t16 = none;
if ( t16 == none )
  LOG::LogSuccess(message:"assign local_initial local_existing ( none )") ;
else
  LOG::LogFailure(message:"assign local_initial local_existing ( none )") ;
end if;
 
select any inst from instances of OI;
assign t17 = inst;
if ( t17 == inst )
  LOG::LogSuccess(message:"assign local_initial local_existing ( inst )") ;
else
  LOG::LogFailure(message:"assign local_initial local_existing ( inst )") ;
end if;

// This is a special test case to verify that usage of 
// local variables of type inst_ref<Object> generate code
// without any error messages.  See the bp issue 659. 
assign t17b = self;
relate t17 to t17b across R3;
unrelate t17b from t17 across R3;

  // boolean
assign t19 = rcvd_evt.ttrue;
if ( t19 == true )
  LOG::LogSuccess(message:"assign local_initial rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:"assign local_initial rcvd_evt ( true )") ;
end if;
 
assign t20 = rcvd_evt.tfalse;
if ( t20 == false )
  LOG::LogSuccess(message:"assign local_initial rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:"assign local_initial rcvd_evt ( false )") ;
end if;
 
  // integer
assign t21 = rcvd_evt.i;
if ( t21 == 11 )
  LOG::LogSuccess(message:"assign local_initial rcvd_evt ( 11 )") ;
else
  LOG::LogFailure(message:"assign local_initial rcvd_evt ( 11 )") ;
end if;
 
  // real
assign t22 = rcvd_evt.r;
LOG::LogInfo( message:"assign local_initial rcvd_evt ( 12.34 )") ;
 
  // string
assign t23 = rcvd_evt.s;
if ( t23 == "fit" )
  LOG::LogSuccess(message:"assign local_initial rcvd_evt ( fit )") ;
else
  LOG::LogFailure(message:"assign local_initial rcvd_evt ( fit )") ;
end if;

  // unique_id
assign t24 = rcvd_evt.u;
if ( t24 == rcvd_evt.u )
  LOG::LogSuccess(message:"assign local_initial rcvd_evt ( rcvd_evt.u )") ;
else
  LOG::LogFailure(message:"assign local_initial rcvd_evt ( rcvd_evt.u )") ;
end if;
 
  // date
assign t25 = rcvd_evt.d;
if ( t25 == rcvd_evt.d )
  LOG::LogSuccess(message:"assign local_initial rcvd_evt ( rcvd_evt.d )") ;
else
  LOG::LogFailure(message:"assign local_initial rcvd_evt ( rcvd_evt.d )") ;
end if;
 
  // timestamp
assign t26 = rcvd_evt.ts;
if ( t26 == rcvd_evt.ts )
  LOG::LogSuccess(message:"assign local_initial rcvd_evt ( rcvd_evt.ts )") ;
else
  LOG::LogFailure(message:"assign local_initial rcvd_evt ( rcvd_evt.ts )") ;
end if;
 
  // inst<Event>
assign t28 = rcvd_evt.ev;
LOG::LogInfo( message:"assign local_initial rcvd_evt ( LOG1(timer event) )") ;

  // boolean
assign t29 = self.bfalse;
if ( t29 == false )
  LOG::LogSuccess(message:"assign local_initial self.attribute ( false )") ;
else
  LOG::LogFailure(message:"assign local_initial self.attribute ( false )") ;
end if;
 
assign t30 = self.btrue;
if ( t30 == true )
  LOG::LogSuccess(message:"assign local_initial self.attribute ( true )") ;
else
  LOG::LogFailure(message:"assign local_initial self.attribute ( true )") ;
end if;
 
  // integer
assign t31 = self.i;
if ( t31 == 10 )
  LOG::LogSuccess(message:"assign local_initial self.attribute ( 10 )") ;
else
  LOG::LogFailure(message:"assign local_initial self.attribute ( 10 )") ;
end if;
 
  // real
assign t32 = self.r;
LOG::LogInfo( message:"assign local_initial self.attribute ( 3.14 )") ;
 
  // string
assign t33 = self.s;
if ( t33 == "String" )
  LOG::LogSuccess(message:"assign local_initial self.attribute ( String )") ;
else
  LOG::LogFailure(message:"assign local_initial self.attribute ( String )") ;
end if;

  // unique_id
assign t34 = self.u;
if ( t34 == self.u )
  LOG::LogSuccess(message:"assign local_initial self.attribute ( self.u )") ;
else
  LOG::LogFailure(message:"assign local_initial self.attribute ( self.u )") ;
end if;
 
  // date
bridge self.d = TIM::create_date(second:31, minute:51, hour:18, day:4, month:4, year:1970);
assign t35 = self.d;
LOG::LogDate(d:t35, message:"assign local_initial self.attribute ( 4/4/1970 18:51:31 )");

  // timestamp
assign t36a = self.t;
LOG::LogTime(t:t36a, message:"assign local_initial self.attribute ( 0 )");
bridge self.t = TIM::current_clock();
assign t36b = self.t;
LOG::LogTime(t:t36b, message:"assign local_initial self.attribute ( current_time )") ;

  // inst_ref<Timer>
create event instance temp_ev of EV1:''Event Instance''(message:"timer event") to event_instance;
assign self.ev = temp_ev;
bridge self.tim = TIM::timer_start ( microseconds:1000000, event_inst:ev_inst1 );
assign t37 = self.tim;
if ( t37 == self.tim )
  LOG::LogSuccess(message:"assign local_initial self.attribute ( self.tim )") ;
else
  LOG::LogFailure(message:"assign local_initial self.attribute ( self.tim )") ;
end if;
 
  // inst<Event>
assign t38 = self.ev;
LOG::LogInfo( message:"assign local_initial self.attribute ( LOG1(timer event) )") ;

  // boolean
assign t39 = self.r1btrue;
if ( t39 == true )
  LOG::LogSuccess(message:"assign local_initial self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:"assign local_initial self.referential_attribute ( true )") ;
end if;
 
assign t40 = self.r1bfalse;
if ( t40 == false )
  LOG::LogSuccess(message:"assign local_initial self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:"assign local_initial self.referential_attribute ( false )") ;
end if;
 
  // integer
assign t41 = self.etob_id;
if ( t41 == 2 )
  LOG::LogSuccess(message:"assign local_initial self.referential_attribute ( 2 )") ;
else
  LOG::LogFailure(message:"assign local_initial self.referential_attribute ( 2 )") ;
end if;
 
  // real
assign t42 = self.r1r;
LOG::LogInfo( message:"assign local_initial self.referential_attribute ( 2.1718 )") ;
 
  // string
assign t43 = self.r1s;
if ( t43 == "etob1 string" )
  LOG::LogSuccess(message:"assign local_initial self.referential_attribute ( etob1 string )") ;
else
  LOG::LogFailure(message:"assign local_initial self.referential_attribute ( etob1 string )") ;
end if;
  // unique_id
assign t44 = self.r1u;
if ( t44 == self.r1u )
  LOG::LogSuccess(message:"assign local_initial self.referential_attribute ( self.r1u )") ;
else
  LOG::LogFailure(message:"assign local_initial self.referential_attribute ( self.r1u )") ;
end if;
 
  // date
assign t45 = self.r1d;
LOG::LogDate(d:t45, message:"assign local_initial self.referential_attribute ( 8/9/1991 13:07:17 )");

  // timestamp
assign t46 = self.r1t;
LOG::LogTime(t:t46, message:"assign local_initial self.referential_attribute ( 0 )");

  // inst_ref<Timer>
assign t47 = self.r1tim;
if ( t47 == self.r1tim )
  LOG::LogSuccess(message:"assign local_initial self.referential_attribute ( self.r1tim )") ;
else
  LOG::LogFailure(message:"assign local_initial self.referential_attribute ( self.r1tim )") ;
end if;
 
  // inst<Event>
assign t48 = self.r1ev;
LOG::LogInfo( message:"assign local_initial self.referential_attribute ( LOG1(timer event) )") ;

select any saf from instances of ETOB;
  // boolean
assign t49 = saf.btrue;
if ( t49 == true )
  LOG::LogSuccess(message:"assign local_initial other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(saf) ( true )") ;
end if;
 
assign t50 = saf.bfalse;
if ( t50 == false )
  LOG::LogSuccess(message:"assign local_initial other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(saf) ( false )") ;
end if;
 
  // integer
assign t51 = saf.etob_id;
if ( t51 == 2 )
  LOG::LogSuccess(message:"assign local_initial other.attribute(saf) ( 2 )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(saf) ( 2 )") ;
end if;
 
  // real
assign t52 = saf.r;
LOG::LogInfo( message:"assign local_initial other.attribute(saf) ( 2.1718 )") ;
 
  // string
assign t53 = saf.s;
if ( t53 == "etob1 string" )
  LOG::LogSuccess(message:"assign local_initial other.attribute(saf) ( etob1 string )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(saf) ( etob1 string )") ;
end if;
  // unique_id
assign t54 = saf.u;
if ( t54 == saf.u )
  LOG::LogSuccess(message:"assign local_initial other.attribute(saf) ( saf.u )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(saf) ( saf.u )") ;
end if;
 
  // date
assign t55 = saf.d;
LOG::LogDate(d:t55, message:"assign local_initial other.attribute(saf) ( 8/9/1991 13:07:17 )") ;
 
  // timestamp
assign t56 = saf.t;
LOG::LogTime(t:t56, message:"assign local_initial other.attribute(saf) ( 0 )") ;
 
  // inst_ref<Timer>
assign t57 = saf.tim;
if ( t57 == saf.tim )
  LOG::LogSuccess(message:"assign local_initial other.attribute(saf) ( saf.tim )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(saf) ( saf.tim )") ;
end if;
 
  // inst<Event>
assign t58 = saf.ev;
LOG::LogInfo( message:"assign local_initial other.attribute(saf) ( LOG1(timer event) )") ;

select many smfs from instances of ETOB;
for each smf in smfs
  if ( smf.etob_id == 2 )
  // boolean
assign t59 = smf.btrue;
if ( t59 == true )
  LOG::LogSuccess(message:"assign local_initial other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(smf) ( true )") ;
end if;
 
assign t60 = smf.bfalse;
if ( t60 == false )
  LOG::LogSuccess(message:"assign local_initial other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(smf) ( false )") ;
end if;
 
  // integer
assign t61 = smf.etob_id;
if ( t61 == 2 )
  LOG::LogSuccess(message:"assign local_initial other.attribute(smf) ( 2 )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(smf) ( 2 )") ;
end if;
 
  // real
assign t62 = smf.r;
LOG::LogInfo( message:"assign local_initial other.attribute(smf) ( 2.1718 )") ;
 
  // string
assign t63 = smf.s;
if ( t63 == "etob1 string" )
  LOG::LogSuccess(message:"assign local_initial other.attribute(smf) ( etob1 string )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(smf) ( etob1 string )") ;
end if;
  // unique_id
assign t64 = smf.u;
if ( t64 == smf.u )
  LOG::LogSuccess(message:"assign local_initial other.attribute(smf) ( smf.u )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(smf) ( smf.u )") ;
end if;
 
  // date
assign t65 = smf.d;
LOG::LogDate(d:t65, message:"assign local_initial other.attribute(smf) ( 8/9/1991 13:07:17 )") ;
 
  // timestamp
assign t66 = smf.t;
LOG::LogTime(t:t66, message:"assign local_initial other.attribute(smf) ( 0 )") ;
 
  // inst_ref<Timer>
assign t67 = smf.tim;
if ( t67 == smf.tim )
  LOG::LogSuccess(message:"assign local_initial other.attribute(smf) ( smf.tim )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(smf) ( smf.tim )") ;
end if;
 
  // inst<Event>
assign t68 = smf.ev;
LOG::LogInfo( message:"assign local_initial other.attribute(smf) ( LOG1(timer event) )") ;

  end if;
end for;

select one sor related by self->ETOB[R1];
  // boolean
assign t69 = sor.btrue;
if ( t69 == true )
  LOG::LogSuccess(message:"assign local_initial other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(sor) ( true )") ;
end if;
 
assign t70 = sor.bfalse;
if ( t70 == false )
  LOG::LogSuccess(message:"assign local_initial other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(sor) ( false )") ;
end if;
 
  // integer
assign t71 = sor.etob_id;
if ( t71 == 2 )
  LOG::LogSuccess(message:"assign local_initial other.attribute(sor) ( 2 )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(sor) ( 2 )") ;
end if;
 
  // real
assign t72 = sor.r;
LOG::LogInfo( message:"assign local_initial other.attribute(sor) ( 2.1718 )") ;
 
  // string
assign t73 = sor.s;
if ( t73 == "etob1 string" )
  LOG::LogSuccess(message:"assign local_initial other.attribute(sor) ( etob1 string )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(sor) ( etob1 string )") ;
end if;
  // unique_id
assign t74 = sor.u;
if ( t74 == sor.u )
  LOG::LogSuccess(message:"assign local_initial other.attribute(sor) ( sor.u )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(sor) ( sor.u )") ;
end if;
 
  // date
assign t75 = sor.d;
LOG::LogDate(d:t75, message:"assign local_initial other.attribute(sor) ( 8/9/1991 13:07:17 )") ;
 
  // timestamp
assign t76 = sor.t;
LOG::LogTime(t:t76, message:"assign local_initial other.attribute(sor) ( 0 )") ;
 
  // inst_ref<Timer>
assign t77 = sor.tim;
if ( t77 == sor.tim )
  LOG::LogSuccess(message:"assign local_initial other.attribute(sor) ( sor.tim )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(sor) ( sor.tim )") ;
end if;
 
  // inst<Event>
assign t78 = sor.ev;
LOG::LogInfo( message:"assign local_initial other.attribute(sor) ( LOG1(timer event) )") ;

select any sar related by self->ETOB[R8];
  // boolean
assign t79 = sar.btrue;
if ( t79 == true )
  LOG::LogSuccess(message:"assign local_initial other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(sar) ( true )") ;
end if;
 
assign t80 = sar.bfalse;
if ( t80 == false )
  LOG::LogSuccess(message:"assign local_initial other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(sar) ( false )") ;
end if;
 
  // integer
assign t81 = sar.etob_id;
if ( t81 == 2 )
  LOG::LogSuccess(message:"assign local_initial other.attribute(sar) ( 2 )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(sar) ( 2 )") ;
end if;
 
  // real
assign t82 = sar.r;
LOG::LogInfo( message:"assign local_initial other.attribute(sar) ( 2.1718 )") ;
 
  // string
assign t83 = sar.s;
if ( t83 == "etob1 string" )
  LOG::LogSuccess(message:"assign local_initial other.attribute(sar) ( etob1 string )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(sar) ( etob1 string )") ;
end if;
  // unique_id
assign t84 = sar.u;
if ( t84 == sar.u )
  LOG::LogSuccess(message:"assign local_initial other.attribute(sar) ( sar.u )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(sar) ( sar.u )") ;
end if;
 
  // date
assign t85 = sar.d;
LOG::LogDate(d:t85, message:"assign local_initial other.attribute(sar) ( 8/9/1991 13:07:17 )") ;
 
  // timestamp
assign t86 = sar.t;
LOG::LogTime(t:t86, message:"assign local_initial other.attribute(sar) ( 0 )") ;
 
  // inst_ref<Timer>
assign t87 = sar.tim;
if ( t87 == sar.tim )
  LOG::LogSuccess(message:"assign local_initial other.attribute(sar) ( sar.tim )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(sar) ( sar.tim )") ;
end if;
 
  // inst<Event>
assign t88 = sar.ev;
LOG::LogInfo( message:"assign local_initial other.attribute(sar) ( LOG1(timer event) )") ;

select many smrs related by self->ETOB[R8];
for each smr in smrs
  if ( smr.etob_id == 2 )
  // boolean
assign t89 = smr.btrue;
if ( t89 == true )
  LOG::LogSuccess(message:"assign local_initial other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(smr) ( true )") ;
end if;
 
assign t90 = smr.bfalse;
if ( t90 == false )
  LOG::LogSuccess(message:"assign local_initial other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(smr) ( false )") ;
end if;
 
  // integer
assign t91 = smr.etob_id;
if ( t91 == 2 )
  LOG::LogSuccess(message:"assign local_initial other.attribute(smr) ( 2 )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(smr) ( 2 )") ;
end if;
 
  // real
assign t92 = smr.r;
LOG::LogInfo( message:"assign local_initial other.attribute(smr) ( 2.1718 )") ;
 
  // string
assign t93 = smr.s;
if ( t93 == "etob1 string" )
  LOG::LogSuccess(message:"assign local_initial other.attribute(smr) ( etob1 string )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(smr) ( etob1 string )") ;
end if;
  // unique_id
assign t94 = smr.u;
if ( t94 == smr.u )
  LOG::LogSuccess(message:"assign local_initial other.attribute(smr) ( smr.u )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(smr) ( smr.u )") ;
end if;
 
  // date
assign t95 = smr.d;
LOG::LogDate(d:t95, message:"assign local_initial other.attribute(smr) ( 8/9/1991 13:07:17 )") ;
 
  // timestamp
assign t96 = smr.t;
LOG::LogTime(t:t96, message:"assign local_initial other.attribute(smr) ( 0 )") ;
 
  // inst_ref<Timer>
assign t97 = smr.tim;
if ( t97 == smr.tim )
  LOG::LogSuccess(message:"assign local_initial other.attribute(smr) ( smr.tim )") ;
else
  LOG::LogFailure(message:"assign local_initial other.attribute(smr) ( smr.tim )") ;
end if;
 
  // inst<Event>
assign t98 = smr.ev;
LOG::LogInfo( message:"assign local_initial other.attribute(smr) ( LOG1(timer event) )") ;
  end if;
end for;

  // boolean
assign t99 = saf.r6btrue;
if ( t99 == true )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(saf) ( true )") ;
end if;
 
assign t100 = saf.r6bfalse;
if ( t100 == false )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(saf) ( false )") ;
end if;
 
  // integer
assign t101 = saf.etoc_id;
if ( t101 == 4 )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(saf) ( 4 )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(saf) ( 4 )") ;
end if;
 
  // real
assign t102 = saf.r6r;
LOG::LogInfo( message:"assign local_initial other.referential_attribute(saf) ( 11.311 )") ;
 
  // string
assign t103 = saf.r6s;
if ( t103 == "etoc string" )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(saf) ( etoc string )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(saf) ( etoc string )") ;
end if;
  // unique_id
assign t104 = saf.r6u;
if ( t104 == saf.r6u )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(saf) ( saf.r6u )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(saf) ( saf.r6u )") ;
end if;
 
  // date
assign t105 = saf.r6d;
LOG::LogDate(d:t105, message:"assign local_initial other.referential_attribute(saf) ( 12/13/1997 00:00:10 )") ;
 
  // timestamp
assign t106 = saf.r6t;
LOG::LogTime(t:t106, message:"assign local_initial other.referential_attribute(saf) ( 0 )") ;
 
  // inst_ref<Timer>
assign t107 = saf.r6tim;
if ( t107 == saf.r6tim )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(saf) ( saf.r6tim )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(saf) ( saf.r6tim )") ;
end if;
 
  // inst<Event>
assign t108 = saf.r6ev;
LOG::LogInfo( message:"assign local_initial other.referential_attribute(saf) ( LOG1(timer event) )") ;

for each smf in smfs
  if (smf.etob_id == 2)
  // boolean
assign t119 = smf.r6btrue;
if ( t119 == true )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute ( true )") ;
end if;
 
assign t110 = smf.r6bfalse;
if ( t110 == false )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute ( false )") ;
end if;
 
  // integer
assign t111 = smf.etoc_id;
if ( t111 == 4 )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute ( 4 )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute ( 4 )") ;
end if;
 
  // real
assign t112 = smf.r6r;
LOG::LogInfo( message:"assign local_initial other.referential_attribute ( 11.311 )") ;
 
  // string
assign t113 = smf.r6s;
if ( t113 == "etoc string" )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(smf) ( etoc string )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(smf) ( etoc string )") ;
end if;
  // unique_id
assign t114 = smf.r6u;
if ( t114 == smf.r6u )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute ( smf.r6u )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute ( smf.r6u )") ;
end if;
 
  // date
assign t115 = smf.r6d;
LOG::LogDate(d:t115, message:"assign local_initial other.referential_attribute(smf) ( 12/13/1997 00:00:10 )") ;
 
  // timestamp
assign t116 = smf.r6t;
LOG::LogTime(t:t116, message:"assign local_initial other.referential_attribute(smf) ( 0 )") ;
 
  // inst_ref<Timer>
assign t117 = smf.r6tim;
if ( t117 == smf.r6tim )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute ( smf.r6tim )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute ( smf.r6tim )") ;
end if;
 
  // inst<Event>
assign t118 = smf.r6ev;
LOG::LogInfo( message:"assign local_initial other.referential_attribute(smf) ( LOG1(timer event) )") ;
  end if;
end for;

  // boolean
assign t119 = sor.r6btrue;
if ( t119 == true )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(sor) ( true )") ;
end if;
 
assign t120 = sor.r6bfalse;
if ( t120 == false )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(sor) ( false )") ;
end if;
 
  // integer
assign t121 = sor.etoc_id;
if ( t121 == 4 )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(sor) ( 4 )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(sor) ( 4 )") ;
end if;
 
  // real
assign t122 = sor.r6r;
LOG::LogInfo( message:"assign local_initial other.referential_attribute(sor) ( 11.311 )") ;
 
  // string
assign t123 = sor.r6s;
if ( t123 == "etoc string" )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(sor) ( etoc string )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(sor) ( etoc string )") ;
end if;
  // unique_id
assign t124 = sor.r6u;
if ( t124 == sor.r6u )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(sor) ( sor.r6u )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(sor) ( sor.r6u )") ;
end if;
 
  // date
assign t125 = sor.r6d;
LOG::LogDate(d:t125, message:"assign local_initial other.referential_attribute(sor) ( 12/13/1997 00:00:10 )") ;
 
  // timestamp
assign t126 = sor.r6t;
LOG::LogTime(t:t126, message:"assign local_initial other.referential_attribute(sor) ( 0 )") ;
 
  // inst_ref<Timer>
assign t127 = sor.r6tim;
if ( t127 == sor.r6tim )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(sor) ( sor.r6tim )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(sor) ( sor.r6tim )") ;
end if;
 
  // inst<Event>
assign t128 = sor.r6ev;
LOG::LogInfo( message:"assign local_initial other.referential_attribute(sor) ( LOG1(timer event) )") ;

  // boolean
assign t129 = sar.r6btrue;
if ( t129 == true )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(sar) ( true )") ;
end if;
 
assign t130 = sar.r6bfalse;
if ( t130 == false )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(sar) ( false )") ;
end if;
 
  // integer
assign t131 = sar.etoc_id;
if ( t131 == 4 )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(sar) ( 4 )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(sar) ( 4 )") ;
end if;
 
  // real
assign t132 = sar.r6r;
LOG::LogInfo( message:"assign local_initial other.referential_attribute(sar) ( 11.311 )") ;
 
  // string
assign t133 = sar.r6s;
if ( t133 == "etoc string" )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(sar) ( etoc string )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(sar) ( etoc string )") ;
end if;
  // unique_id
assign t134 = sar.r6u;
if ( t134 == sar.r6u )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(sar) ( sar.r6u )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(sar) ( sar.r6u )") ;
end if;
 
  // date
assign t135 = sar.r6d;
LOG::LogDate(d:t135, message:"assign local_initial other.referential_attribute(sar) ( 12/13/1997 00:00:10 )") ;
 
  // timestamp
assign t136 = sar.r6t;
LOG::LogTime(t:t136, message:"assign local_initial other.referential_attribute(sar) ( 0 )") ;
 
  // inst_ref<Timer>
assign t137 = sar.r6tim;
if ( t137 == sar.r6tim )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(sar) ( sar.r6tim )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(sar) ( sar.r6tim )") ;
end if;
 
  // inst<Event>
assign t138 = sar.r6ev;
LOG::LogInfo( message:"assign local_initial other.referential_attribute(sar) ( LOG1(timer event) )") ;

for each smr in smrs
  if ( smr.etob_id == 2 )
  // boolean
assign t139 = smr.r6btrue;
if ( t139 == true )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(smr) ( true )") ;
end if;
 
assign t140 = smr.r6bfalse;
if ( t140 == false )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(smr) ( false )") ;
end if;
 
  // integer
assign t141 = smr.etoc_id;
if ( t141 == 4 )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(smr) ( 4 )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(smr) ( 4 )") ;
end if;
 
  // real
assign t142 = smr.r6r;
LOG::LogInfo( message:"assign local_initial other.referential_attribute(smr) ( 11.311 )") ;
 
  // string
assign t143 = smr.r6s;
if ( t143 == "etoc string" )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(smr) ( etoc string )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(smr) ( etoc string )") ;
end if;
  // unique_id
assign t144 = smr.r6u;
if ( t144 == smr.r6u )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(smr) ( smr.r6u )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(smr) ( smr.r6u )") ;
end if;
 
  // date
assign t145 = smr.r6d;
LOG::LogDate(d:t145, message:"assign local_initial other.referential_attribute(smr) ( 12/13/1997 00:00:10 )") ;
 
  // timestamp
assign t146 = smr.r6t;
LOG::LogTime(t:t146, message:"assign local_initial other.referential_attribute(smr) ( 0 )") ;
 
  // inst_ref<Timer>
assign t147 = smr.r6tim;
if ( t147 == smr.r6tim )
  LOG::LogSuccess(message:"assign local_initial other.referential_attribute(smr) ( smr.r6tim )") ;
else
  LOG::LogFailure(message:"assign local_initial other.referential_attribute(smr) ( smr.r6tim )") ;
end if;
 
  // inst<Event>
assign t148 = smr.r6ev;
LOG::LogInfo( message:"assign local_initial other.referential_attribute(smr) ( LOG1(timer event) )") ;
  end if;
end for;


  // boolean
assign t1 = false;
if ( t1 == false )
  LOG::LogSuccess(message:"assign local_existing constant ( false )") ;
else
  LOG::LogFailure(message:"assign local_existing constant ( false )") ;
end if;
 
assign t2 = true;
if ( t2 == true )
  LOG::LogSuccess(message:"assign local_existing constant ( true )") ;
else
  LOG::LogFailure(message:"assign local_existing constant ( true )") ;
end if;
 
  // integer
assign t3 = 7;
if ( t3 == 7 )
  LOG::LogSuccess(message:"assign local_existing constant ( 7 )") ;
else
  LOG::LogFailure(message:"assign local_existing constant ( 7 )") ;
end if;
 
  // real
assign t4 = 96.98;
LOG::LogInfo( message:"assign local_existing constant ( 96.98 )") ;
 
  // string
assign t5 = "fussy";
if ( t5 == "fussy" )
  LOG::LogSuccess(message:"assign local_existing constant ( fussy )") ;
else
  LOG::LogFailure(message:"assign local_existing constant ( fussy )") ;
end if;

  // boolean
assign t2 = t7;
if ( t2 == false )
  LOG::LogSuccess(message:"assign local_existing local_existing ( false )") ;
else
  LOG::LogFailure(message:"assign local_existing local_existing ( false )") ;
end if;
 
assign t1 = t6;
if ( t1 == true )
  LOG::LogSuccess(message:"assign local_existing local_existing ( true )") ;
else
  LOG::LogFailure(message:"assign local_existing local_existing ( true )") ;
end if;
 
  // integer
assign t8 = t3;
if ( t8 == 7 )
  LOG::LogSuccess(message:"assign local_existing local_existing ( 7 )") ;
else
  LOG::LogFailure(message:"assign local_existing local_existing ( 7 )") ;
end if;
 
  // real
assign t9 = t4;
LOG::LogInfo( message:"assign local_existing local_existing ( 96.98 )") ;
 
  // string
assign t10 = t5;
if ( t10 == "fussy" )
  LOG::LogSuccess(message:"assign local_existing local_existing ( fussy )") ;
else
  LOG::LogFailure(message:"assign local_existing local_existing ( fussy )") ;
end if;

  // unique_id
assign t24 = t11;
if ( t24 == self.u )
  LOG::LogSuccess(message:"assign local_existing local_existing ( self.u )") ;
else
  LOG::LogFailure(message:"assign local_existing local_existing ( self.u )") ;
end if;
 
  // date
assign t25 = t12;
if ( t25 == temp_date )
  LOG::LogSuccess(message:"assign local_existing local_existing ( temp_date )") ;
else
  LOG::LogFailure(message:"assign local_existing local_existing ( temp_date )") ;
end if;
 
  // timestamp
assign t26 = t13;
if ( t26 == temp_timestamp )
  LOG::LogSuccess(message:"assign local_existing local_existing ( temp_timestamp )") ;
else
  LOG::LogFailure(message:"assign local_existing local_existing ( temp_timestamp )") ;
end if;
 
  // inst_ref<Timer>
assign t27 = t14;
if ( t27 == temp_timer )
  LOG::LogSuccess(message:"assign local_existing local_existing ( temp_timer )") ;
else
  LOG::LogFailure(message:"assign local_existing local_existing ( temp_timer )") ;
end if;
 
  // inst<Event>
assign t28 = t15;
LOG::LogInfo( message:"assign local_existing rcvd_evt ( LOG1(timer event) )");

  // inst_ref<Object>
assign t17 = inst;
if ( t17 == inst )
  LOG::LogSuccess(message:"assign local_existing local_existing ( inst )") ;
else
  LOG::LogFailure(message:"assign local_existing local_existing ( inst )") ;
end if;
 

  // boolean
assign t19 = rcvd_evt.tfalse;
if ( t19 == false )
  LOG::LogSuccess(message:"assign local_existing rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:"assign local_existing rcvd_evt ( false )") ;
end if;
 
assign t20 = rcvd_evt.ttrue;
if ( t20 == true )
  LOG::LogSuccess(message:"assign local_existing rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:"assign local_existing rcvd_evt ( true )") ;
end if;
 
  // integer
assign t21 = 0;
assign t21 = rcvd_evt.i;
if ( t21 == 11 )
  LOG::LogSuccess(message:"assign local_existing rcvd_evt ( 11 )") ;
else
  LOG::LogFailure(message:"assign local_existing rcvd_evt ( 11 )") ;
end if;
 
  // real
assign t22 = 0.0;
assign t22 = rcvd_evt.r;
LOG::LogInfo( message:"assign local_existing rcvd_evt ( 12.34 )") ;
 
  // string
assign t23 = "";
assign t23 = rcvd_evt.s;
if ( t23 == "fit" )
  LOG::LogSuccess(message:"assign local_existing rcvd_evt ( fit )") ;
else
  LOG::LogFailure(message:"assign local_existing rcvd_evt ( fit )") ;
end if;
 
  // unique_id
assign t24 = rcvd_evt.u;
if ( t24 == rcvd_evt.u )
  LOG::LogSuccess(message:"assign local_existing rcvd_evt ( rcvd_evt.u )") ;
else
  LOG::LogFailure(message:"assign local_existing rcvd_evt ( rcvd_evt.u )") ;
end if;
 
  // date
assign t25 = rcvd_evt.d;
if ( t25 == rcvd_evt.d )
  LOG::LogSuccess(message:"assign local_existing rcvd_evt ( rcvd_evt.d )") ;
else
  LOG::LogFailure(message:"assign local_existing rcvd_evt ( rcvd_evt.d )") ;
end if;
 
  // timestamp
assign t26 = rcvd_evt.ts;
if ( t26 == rcvd_evt.ts )
  LOG::LogSuccess(message:"assign local_existing rcvd_evt ( rcvd_evt.ts )") ;
else
  LOG::LogFailure(message:"assign local_existing rcvd_evt ( rcvd_evt.ts )") ;
end if;
 
  // boolean
assign t19 = self.btrue;
if ( t19 == true )
  LOG::LogSuccess(message:"assign local_existing self.attribute ( true )") ;
else
  LOG::LogFailure(message:"assign local_existing self.attribute ( true )") ;
end if;
 
assign t20 = self.bfalse;
if ( t20 == false )
  LOG::LogSuccess(message:"assign local_existing self.attribute ( false )") ;
else
  LOG::LogFailure(message:"assign local_existing self.attribute ( false )") ;
end if;
 
  // integer
assign t21 = self.i;
if ( t21 == 10 )
  LOG::LogSuccess(message:"assign local_existing self.attribute ( 10 )") ;
else
  LOG::LogFailure(message:"assign local_existing self.attribute ( 10 )") ;
end if;
 
  // real
assign t22 = self.r;
LOG::LogInfo( message:"assign local_existing self.attribute ( 3.14 )") ;
 
  // string
assign t23 = self.s;
if ( t23 == "String" )
  LOG::LogSuccess(message:"assign local_existing self.attribute ( String )") ;
else
  LOG::LogFailure(message:"assign local_existing self.attribute ( String )") ;
end if;
  // unique_id
assign t24 = self.u;
if ( t24 == self.u )
  LOG::LogSuccess(message:"assign local_existing self.attribute ( self.u )") ;
else
  LOG::LogFailure(message:"assign local_existing self.attribute ( self.u )") ;
end if;
 
  // date
assign t25 = self.d;
if ( t25 == self.d )
  LOG::LogSuccess(message:"assign local_existing self.attribute ( self.d )") ;
else
  LOG::LogFailure(message:"assign local_existing self.attribute ( self.d )") ;
end if;
 
  // timestamp
assign t26 = self.t;
if ( t26 == self.t )
  LOG::LogSuccess(message:"assign local_existing self.attribute ( self.t )") ;
else
  LOG::LogFailure(message:"assign local_existing self.attribute ( self.t )") ;
end if;
 
  // inst_ref<Timer>
assign t27 = self.tim;
if ( t27 == self.tim )
  LOG::LogSuccess(message:"assign local_existing self.attribute ( self.tim )") ;
else
  LOG::LogFailure(message:"assign local_existing self.attribute ( self.tim )") ;
end if;
 
  // inst<Event>
assign t28 = self.ev;
LOG::LogInfo( message:"assign local_existing self.attribute ( LOG1(timer event) )") ;

  // boolean
assign t19 = self.r1bfalse;
if ( t19 == false )
  LOG::LogSuccess(message:"assign local_existing self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:"assign local_existing self.referential_attribute ( false )") ;
end if;
 
assign t20 = self.r1btrue;
if ( t20 == true )
  LOG::LogSuccess(message:"assign local_existing self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:"assign local_existing self.referential_attribute ( true )") ;
end if;
 
  // integer
assign t21 = self.etob_id;
if ( t21 == 2 )
  LOG::LogSuccess(message:"assign local_existing self.referential_attribute ( 2 )") ;
else
  LOG::LogFailure(message:"assign local_existing self.referential_attribute ( 2 )") ;
end if;
 
  // real
assign t22 = self.r1r;
LOG::LogInfo( message:"assign local_existing self.referential_attribute ( 2.1718 )") ;
 
  // string
assign t23 = self.r1s;
if ( t23 == "etob1 string" )
  LOG::LogSuccess(message:"assign local_existing self.referential_attribute ( etob1 string )") ;
else
  LOG::LogFailure(message:"assign local_existing self.referential_attribute ( etob1 string )") ;
end if;
  // unique_id
assign t24 = self.r1u;
if ( t24 == self.r1u )
  LOG::LogSuccess(message:"assign local_existing self.referential_attribute ( self.r1u )") ;
else
  LOG::LogFailure(message:"assign local_existing self.referential_attribute ( self.r1u )") ;
end if;
 
  // date
assign t25 = self.r1d;
if ( t25 == self.r1d )
  LOG::LogSuccess(message:"assign local_existing self.referential_attribute ( self.r1d )") ;
else
  LOG::LogFailure(message:"assign local_existing self.referential_attribute ( self.r1d )") ;
end if;
 
  // timestamp
assign t26 = self.r1t;
if ( t26 == self.r1t )
  LOG::LogSuccess(message:"assign local_existing self.referential_attribute ( self.r1t )") ;
else
  LOG::LogFailure(message:"assign local_existing self.referential_attribute ( self.r1t )") ;
end if;
 
  // inst_ref<Timer>
assign t27 = self.r1tim;
if ( t27 == self.r1tim )
  LOG::LogSuccess(message:"assign local_existing self.referential_attribute ( self.r1tim )") ;
else
  LOG::LogFailure(message:"assign local_existing self.referential_attribute ( self.r1tim )") ;
end if;
 
  // inst<Event>
assign t28 = self.r1ev;
LOG::LogInfo( message:"assign local_existing self.referential_attribute ( LOG1(timer event) )") ;

// lval = local existing rval = other.attribute (saf)
  // boolean
assign t19 = saf.bfalse;
if ( t19 == false )
  LOG::LogSuccess(message:"assign local_existing other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:"assign local_existing other.attribute(saf) ( false )") ;
end if;
 
assign t20 = saf.btrue;
if ( t20 == true )
  LOG::LogSuccess(message:"assign local_existing other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:"assign local_existing other.attribute(saf) ( true )") ;
end if;
 
  // integer
assign t21 = saf.etob_id;
if ( t21 == 2 )
  LOG::LogSuccess(message:"assign local_existing other.attribute(saf) ( 2 )") ;
else
  LOG::LogFailure(message:"assign local_existing other.attribute(saf) ( 2 )") ;
end if;
 
  // real
assign t22 = saf.r;
LOG::LogInfo( message:"assign local_existing other.attribute(saf) ( 2.1718 )") ;
 
  // string
assign t23 = saf.s;
if ( t23 == "etob1 string" )
  LOG::LogSuccess(message:"assign local_existing other.attribute(saf) ( etob1 string )") ;
else
  LOG::LogFailure(message:"assign local_existing other.attribute(saf) ( etob1 string )") ;
end if;
  // unique_id
assign t24 = saf.u;
if ( t24 == saf.u )
  LOG::LogSuccess(message:"assign local_existing other.attribute(saf) ( saf.u )") ;
else
  LOG::LogFailure(message:"assign local_existing other.attribute(saf) ( saf.u )") ;
end if;
 
  // date
assign t25 = saf.d;
if ( t25 == saf.d )
  LOG::LogSuccess(message:"assign local_existing other.attribute(saf) ( saf.d )") ;
else
  LOG::LogFailure(message:"assign local_existing other.attribute(saf) ( saf.d )") ;
end if;
 
  // timestamp
assign t26 = saf.t;
if ( t26 == saf.t )
  LOG::LogSuccess(message:"assign local_existing other.attribute(saf) ( saf.t )") ;
else
  LOG::LogFailure(message:"assign local_existing other.attribute(saf) ( saf.t )") ;
end if;
 
  // inst_ref<Timer>
assign t27 = saf.tim;
if ( t27 == saf.tim )
  LOG::LogSuccess(message:"assign local_existing other.attribute(saf) ( saf.tim )") ;
else
  LOG::LogFailure(message:"assign local_existing other.attribute(saf) ( saf.tim )") ;
end if;
 
  // inst<Event>
assign t28 = saf.ev;
LOG::LogInfo( message:"assign local_existing other.attribute(saf) ( LOG1(timer event) )") ;

for each smf in smfs
  if ( smf.etob_id == 2 )
  // boolean
assign t19 = smf.btrue;
if ( t19 == true )
  LOG::LogSuccess(message:"assign local_existing other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:"assign local_existing other.attribute(smf) ( true )") ;
end if;
 
assign t20 = smf.bfalse;
if ( t20 == false )
  LOG::LogSuccess(message:"assign local_existing other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:"assign local_existing other.attribute(smf) ( false )") ;
end if;
 
  // integer
assign t21 = smf.etob_id;
if ( t21 == 2 )
  LOG::LogSuccess(message:"assign local_existing other.attribute(smf) ( 2 )") ;
else
  LOG::LogFailure(message:"assign local_existing other.attribute(smf) ( 2 )") ;
end if;
 
  // real
assign t22 = smf.r;
LOG::LogInfo( message:"assign local_existing other.attribute(smf) ( 2.1718 )") ;
 
  // string
assign t23 = smf.s;
if ( t23 == "etob1 string" )
  LOG::LogSuccess(message:"assign local_existing other.attribute(smf) ( etob1 string )") ;
else
  LOG::LogFailure(message:"assign local_existing other.attribute(smf) ( etob1 string )") ;
end if;
  // unique_id
assign t24 = smf.u;
if ( t24 == smf.u )
  LOG::LogSuccess(message:"assign local_existing other.attribute(smf) ( smf.u )") ;
else
  LOG::LogFailure(message:"assign local_existing other.attribute(smf) ( smf.u )") ;
end if;
 
  // date
assign t25 = smf.d;
if ( t25 == smf.d )
  LOG::LogSuccess(message:"assign local_existing other.attribute(smf) ( smf.d )") ;
else
  LOG::LogFailure(message:"assign local_existing other.attribute(smf) ( smf.d )") ;
end if;
 
  // timestamp
assign t26 = smf.t;
if ( t26 == smf.t )
  LOG::LogSuccess(message:"assign local_existing other.attribute(smf) ( smf.t )") ;
else
  LOG::LogFailure(message:"assign local_existing other.attribute(smf) ( smf.t )") ;
end if;
 
  // inst_ref<Timer>
assign t27 = smf.tim;
if ( t27 == smf.tim )
  LOG::LogSuccess(message:"assign local_existing other.attribute(smf) ( smf.tim )") ;
else
  LOG::LogFailure(message:"assign local_existing other.attribute(smf) ( smf.tim )") ;
end if;
 
  end if;
end for;

//generate event to continue test
generate ET15:''Start assign test''(ttrue:true, tfalse:false, i:11, r:12.34, s:"fit",
   u:self.u, d: rcvd_evt.d, ts: rcvd_evt.ts, tim:rcvd_evt.tim, ev:rcvd_evt.ev) to self;

',
	'');
INSERT INTO SM_STATE
	VALUES (2097168,
	2097156,
	0,
	'Additional assign tests',
	17,
	0);
INSERT INTO SM_CH
	VALUES (2097168,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097168,
	2097153,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097168,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097168,
	2097154,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097168,
	2097155,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097168,
	2097155,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097168,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097168,
	2097156,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097168,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097168,
	2097157,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097168,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097168,
	2097158,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097168,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097168,
	2097159,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097168,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097168,
	2097160,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097168,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097168,
	2097161,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097168,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097168,
	2097162,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097168,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097168,
	2097163,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097168,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097168,
	2097164,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097168,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097168,
	2097165,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097168,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097168,
	2097166,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097168,
	2097167,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097168,
	2097167,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097168,
	2097168,
	2097156,
	0);
INSERT INTO SM_CH
	VALUES (2097168,
	2097169,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097168,
	2097169,
	2097156,
	0);
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
	'select any event_instance from instances of EV;
  // boolean
assign self.btrue = false;
if ( self.btrue == false )
  LOG::LogSuccess(message:"assign self.attribute constant ( false )") ;
else
  LOG::LogFailure(message:"assign self.attribute constant ( false )") ;
end if;
 
assign self.btrue = true;
if ( self.btrue == true )
  LOG::LogSuccess(message:"assign self.attribute constant ( true )") ;
else
  LOG::LogFailure(message:"assign self.attribute constant ( true )") ;
end if;
 
  // integer
assign self.i = -17;
if ( self.i == -17 )
  LOG::LogSuccess(message:"assign self.attribute constant ( -17 )") ;
else
  LOG::LogFailure(message:"assign self.attribute constant ( -17 )") ;
end if;
 
assign self.i = 15;
if ( self.i == 15 )
  LOG::LogSuccess(message:"assign self.attribute constant ( 15 )") ;
else
  LOG::LogFailure(message:"assign self.attribute constant ( 15 )") ;
end if;

  // real
assign self.r = 387.32;
if ( ((self.r - 387.32 ) < .1) OR ((387.32 - self.r) < .1) )
  LOG::LogSuccess(message:"assign self.attribute constant ( 387.21 )") ;
else
  LOG::LogFailure(message:"assign self.attribute constant (387.21 )") ;
end if;

  // string
assign self.s = "test 1029";
if ( self.s == "test 1029" )
  LOG::LogSuccess(message:"assign self.attribute constant ( test 1029 )") ;
else
  LOG::LogFailure(message:"assign self.attribute constant ( test 1029 )") ;
end if;

  // boolean
assign t20 = false;
assign self.btrue = t20;
if ( self.btrue == false )
  LOG::LogSuccess(message:"assign self.attribute local_existing ( false )") ;
else
  LOG::LogFailure(message:"assign self.attribute local_existing ( false )") ;
end if;

assign t19 = true; 
assign self.btrue = t19;
if ( self.btrue == true )
  LOG::LogSuccess(message:"assign self.attribute local_existing ( true )") ;
else
  LOG::LogFailure(message:"assign self.attribute local_existing ( true )") ;
end if;
 
  // integer
assign t21 = 2;
assign self.i = t21;
if ( self.i == 2 )
  LOG::LogSuccess(message:"assign self.attribute local_existing ( 2 )") ;
else
  LOG::LogFailure(message:"assign self.attribute local_existing ( 2 )") ;
end if;
 
  // real
assign t22 = 2.1718;
assign self.r = t22;
if ( ((self.r - 2.1718) < .1) OR ((2.1718 - self.r) < .1) )
  LOG::LogSuccess(message:"assign self.attribute local_existing ( etob1 real )") ;
else
  LOG::LogFailure(message:"assign self.attribute local_existing ( etob1 real )") ;
end if;

  // string
assign t23 = "etob1 string";
assign self.s = t23;
if ( self.s == "etob1 string" )
  LOG::LogSuccess(message:"assign self.attribute local_existing ( etob1 string )") ;
else
  LOG::LogFailure(message:"assign self.attribute local_existing ( etob1 string )") ;
end if;

  // unique_id
assign save_u = self.u;
assign t24 = rcvd_evt.u;
assign self.u = t24;
if ( self.u == rcvd_evt.u )
  LOG::LogSuccess(message:"assign self.attribute local_existing ( rcvd_evt.u )") ;
else
  LOG::LogFailure(message:"assign self.attribute local_existing ( rcvd_evt.u )") ;
end if;
 
  // date
assign t25 = rcvd_evt.d;
assign self.d = t25;
if ( self.d == rcvd_evt.d )
  LOG::LogSuccess(message:"assign self.attribute local_existing ( rcvd_evt.d )") ;
else
  LOG::LogFailure(message:"assign self.attribute local_existing ( rcvd_evt.d )") ;
end if;
 
  // timestamp
assign t26 = rcvd_evt.ts;
assign self.t = t26;
if ( self.t == rcvd_evt.ts )
  LOG::LogSuccess(message:"assign self.attribute local_existing ( rcvd_evt.ts )") ;
else
  LOG::LogFailure(message:"assign self.attribute local_existing ( rcvd_evt.ts )") ;
end if;
 
  // inst_ref<Timer>
create event instance ev_inst1 of EV1:''Event Instance''(message:"timer event") to event_instance ;
bridge temp_timer = TIM::timer_start ( microseconds:1000000, event_inst:ev_inst1 );
assign self.tim = temp_timer;
if ( self.tim == temp_timer )
  LOG::LogSuccess(message:"assign self.attribute local_existing ( temp_timer )") ;
else
  LOG::LogFailure(message:"assign self.attribute local_existing ( temp_timer )") ;
end if;
 
  // inst<Event>
assign self.ev = ev_inst1;
LOG::LogInfo(message:"assign self.attribute local_existing ( LOG1(timer event) )");

  // boolean
assign self.btrue = rcvd_evt.tfalse;
if ( self.btrue == false )
  LOG::LogSuccess(message:"assign self.attribute rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:"assign self.attribute rcvd_evt ( false )") ;
end if;
 
assign self.btrue = rcvd_evt.ttrue;
if ( self.btrue == true )
  LOG::LogSuccess(message:"assign self.attribute rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:"assign self.attribute rcvd_evt ( true )") ;
end if;
 
  // integer
assign self.i = rcvd_evt.i;
if ( self.i == 11 )
  LOG::LogSuccess(message:"assign self.attribute rcvd_evt ( 11 )") ;
else
  LOG::LogFailure(message:"assign self.attribute rcvd_evt ( 11 )") ;
end if;
 
  // real
assign self.r = rcvd_evt.r;
if ( ((self.r - 11.0) < .1) OR ((11.0 - self.r) < .1) )
  LOG::LogSuccess(message:"assign self.attribute rcvd_evt ( 11.0 )") ;
else
  LOG::LogFailure(message:"assign self.attribute rcvd_evt ( 11.0 )") ;
end if;
 
assign self.r = rcvd_evt.i;
if ( self.r == 11.0 )
  LOG::LogSuccess(message:"assign self.attribute rcvd_evt ( 11.0 )") ;
else
  LOG::LogFailure(message:"assign self.attribute rcvd_evt ( 11.0 )") ;
end if;
 
  // string
assign self.s = rcvd_evt.s; 
if ( self.s == "fit" ) 
  LOG::LogSuccess(message:"assign self.attribute rcvd_evt ( fit )") ;
else 
  LOG::LogFailure(message:"assign self.attribute rcvd_evt ( fit )") ; 
end if; 
 
  // unique_id   
assign self.u = rcvd_evt.u;
if ( self.u == save_u )
  LOG::LogSuccess(message:"assign self.attribute rcvd_evt ( save_u )") ;
else
  LOG::LogFailure(message:"assign self.attribute rcvd_evt ( save_u )") ;
end if;
 
  // date
assign self.d = rcvd_evt.d;
if ( self.d == rcvd_evt.d )
  LOG::LogSuccess(message:"assign self.attribute rcvd_evt ( rcvd_evt.d )") ;
else
  LOG::LogFailure(message:"assign self.attribute rcvd_evt ( rcvd_evt.d )") ;
end if;
 
  // timestamp 
assign self.t = rcvd_evt.ts;
if ( self.t == rcvd_evt.ts )
  LOG::LogSuccess(message:"assign self.attribute rcvd_evt ( rcvd_evt.ts )") ;
else
  LOG::LogFailure(message:"assign self.attribute rcvd_evt ( rcvd_evt.ts )") ;
end if;
 
  // inst_ref<Timer>
assign self.tim = rcvd_evt.tim;
if ( self.tim == rcvd_evt.tim )
  LOG::LogSuccess(message:"assign self.attribute rcvd_evt ( rcvd_evt.tim )") ;
else
  LOG::LogFailure(message:"assign self.attribute rcvd_evt ( rcvd_evt.tim )") ;
end if;
 
  // inst<Event>
assign self.ev = rcvd_evt.ev;
LOG::LogInfo(message:"assign self.attribute rcvd_evt ( LOG1(timer event) )") ; 
 

  // boolean
assign self.btrue = self.bfalse;
if ( self.btrue == false )
  LOG::LogSuccess(message:"assign self.attribute self.attribute ( false )") ;
else
  LOG::LogFailure(message:"assign self.attribute self.attribute ( false )") ;
end if;
 
assign self.btrue = true;
assign self.bfalse = self.btrue;
if ( self.bfalse == true )
  LOG::LogSuccess(message:"assign self.attribute self.attribute ( true )") ;
else
  LOG::LogFailure(message:"assign self.attribute self.attribute ( true )") ;
end if;
 
assign self.bfalse = false;

  // integer
assign self.i = self.i;
if ( self.i == 11 )
  LOG::LogSuccess(message:"assign self.attribute self.attribute ( 11 )") ;
else
  LOG::LogFailure(message:"assign self.attribute self.attribute ( 11 )") ;
end if;
 
  // real
assign self.r = self.r;
if (  ((self.r - 11.0) < .1) OR ((11.0 - self.r) < .1) ) 
  LOG::LogSuccess(message:"assign self.attribute self.attribute ( r )") ;
else 
  LOG::LogFailure(message:"assign self.attribute self.attribute ( r )") ; 
end if; 
 
assign self.r = self.i;
if (  ((self.r - 11.0) < .1) OR ((11.0 - self.r) < .1) ) 
  LOG::LogSuccess(message:"assign self.attribute self.attribute ( r i)") ;
else 
  LOG::LogFailure(message:"assign self.attribute self.attribute ( r i)") ; 
end if; 
 
  // string
assign self.s = self.s; 
if ( self.s == "fit" ) 
  LOG::LogSuccess(message:"assign self.attribute self.attribute ( fit )") ;
else 
  LOG::LogFailure(message:"assign self.attribute self.attribute ( fit )") ; 
end if; 
 
  // unique_id
assign self.u = self.u;
if ( self.u == save_u )
  LOG::LogSuccess(message:"assign self.attribute self.attribute ( save_u )") ;
else
  LOG::LogFailure(message:"assign self.attribute self.attribute ( save_u )") ;
end if;
 
  // date
assign self.d = self.d;
if ( self.d == self.d )
  LOG::LogSuccess(message:"assign self.attribute self.attribute ( self.d )") ;
else
  LOG::LogFailure(message:"assign self.attribute self.attribute ( self.d )") ;
end if;
 
  // timestamp
assign self.t = self.t;
if ( self.t == self.t )
  LOG::LogSuccess(message:"assign self.attribute self.attribute ( self.t )") ;
else
  LOG::LogFailure(message:"assign self.attribute self.attribute ( self.t )") ;
end if;
 
  // inst_ref<Timer>
assign self.tim = self.tim;
if ( self.tim == self.tim )
  LOG::LogSuccess(message:"assign self.attribute self.attribute ( self.tim )") ;
else
  LOG::LogFailure(message:"assign self.attribute self.attribute ( self.tim )") ;
end if;
 
  // inst<Event>
assign self.ev = self.ev;
LOG::LogInfo( message:"assign self.attribute self.attribute ( LOG1(timer event) )") ; 

  // boolean
assign self.btrue = self.r1bfalse;
if ( self.btrue == false )
  LOG::LogSuccess(message:"assign self.attribute self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:"assign self.attribute self.referential_attribute ( false )") ;
end if;
 
assign self.btrue = self.r1btrue;
if ( self.btrue == true )
  LOG::LogSuccess(message:"assign self.attribute self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:"assign self.attribute self.referential_attribute ( true )") ;
end if;
 
assign self.bfalse = self.r1btrue;
if ( self.bfalse == true )
  LOG::LogSuccess(message:"assign self.attribute self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:"assign self.attribute self.referential_attribute ( true )") ;
end if;
 
assign self.bfalse = self.r1bfalse;
if ( self.bfalse == false )
  LOG::LogSuccess(message:"assign self.attribute self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:"assign self.attribute self.referential_attribute ( false )") ;
end if;
 
  // integer
assign self.i = self.etob_id;
if ( self.i == 2 )
  LOG::LogSuccess(message:"assign self.attribute self.referential_attribute ( 2 )") ;
else
  LOG::LogFailure(message:"assign self.attribute self.referential_attribute ( 2 )") ;
end if;
 
  // real
assign self.r = self.r1r;
if ( (( self.r - 2.1718) < .1 ) OR ((2.1718 - self.r) < .1) )
  LOG::LogSuccess(message:"assign self.attribute self.referential_attribute ( assign self.r = self.r1r)") ;
else
  LOG::LogFailure(message:"assign self.attribute self.referential_attribute ( assign self.r = self.r1r )") ;
end if;
 
assign self.r = self.etob_id;
if ( (( self.r - 2.0) < .1 ) OR ((2.0 - self.r) < .1) )
  LOG::LogSuccess(message:"assign self.attribute self.referential_attribute ( assign self.r = self.r1r)") ;
else
  LOG::LogFailure(message:"assign self.attribute self.referential_attribute ( assign self.r = self.r1r )") ;
end if;
 
  // string
assign self.s = self.r1s; 
if ( self.s == "etob1 string" ) 
  LOG::LogSuccess(message:"assign self.attribute self.referential attribute ( etob1 string)") ;
else 
  LOG::LogFailure(message:"assign self.attribute self.referential attribute ( etob1 string)") ; 
end if; 
 
  // unique_id
assign self.u = self.r1u;
if ( self.u == self.r1u )
  LOG::LogSuccess(message:"assign self.attribute self.referential_attribute ( self.r1u )") ;
else
  LOG::LogFailure(message:"assign self.attribute self.referential_attribute ( self.r1u )") ;
end if;
 
  // date
assign self.d = self.r1d;
if ( self.d == self.r1d )
  LOG::LogSuccess(message:"assign self.attribute self.referential_attribute ( self.r1d )") ;
else
  LOG::LogFailure(message:"assign self.attribute self.referential_attribute ( self.r1d )") ;
end if;
 
  // timestamp
assign self.t = self.r1t;
if ( self.t == self.r1t )
  LOG::LogSuccess(message:"assign self.attribute self.referential_attribute ( self.r1t )") ;
else
  LOG::LogFailure(message:"assign self.attribute self.referential_attribute ( self.r1t )") ;
end if;
 
  // inst_ref<Timer>
assign self.tim = self.r1tim;
if ( self.tim == self.r1tim )
  LOG::LogSuccess(message:"assign self.attribute self.referential_attribute ( self.r1tim )") ;
else
  LOG::LogFailure(message:"assign self.attribute self.referential_attribute ( self.r1tim )") ;
end if;
 
  // inst<Event>
assign self.ev = self.r1ev;
LOG::LogInfo(message:"assign self.attribute self.referential attribute ( LOG1(timer event) )") ; 


  // boolean
select any saf from instances of ETOB;
assign self.btrue = saf.bfalse;
if ( self.btrue == false )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(saf) ( false )") ;
end if;
 
assign self.btrue = saf.btrue;
if ( self.btrue == true )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(saf) ( true )") ;
end if;
 
assign self.bfalse = saf.btrue;
if ( self.bfalse == true )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(saf) ( true )") ;
end if;
 
assign self.bfalse = saf.bfalse;
if ( self.bfalse == false )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(saf) ( false )") ;
end if;
 
  // integer
assign self.i = saf.etob_id;
if ( self.i == 2 )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(saf) ( 2 )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(saf) ( 2 )") ;
end if;
 
  // real
assign self.r = saf.r;
if (  ((self.r -2.1718) < .1) OR (( 2.1718 - self.i ) < .1) )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(saf) ( assign self.r = saf.r )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(saf) ( assign self.r = saf.r )") ;
end if;
 
assign self.r = saf.etob_id;
if (  ((self.r -2.0) < .1) OR (( 2.0 - self.i ) < .1) )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(saf) ( assign self.r = saf.etob_id; )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(saf) (assign self.r = saf.etob_id; )") ;
end if;
 
  // string
assign self.s = saf.s; 
if ( self.s == "etob1 string" ) 
  LOG::LogSuccess(message:"assign self.attribute saf.attribute ( etob1 string)") ;
else 
  LOG::LogFailure(message:"assign self.attribute saf.attribute ( etob1 string)") ; 
end if; 
 
  // unique_id
assign self.u = saf.u;
if ( self.u == saf.u )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(saf) ( saf.u )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(saf) ( saf.u )") ;
end if;
 
  // date
assign self.d = saf.d;
if ( self.d == saf.d )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(saf) ( saf.d )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(saf) ( saf.d )") ;
end if;
 
  // timestamp
assign self.t = saf.t;
if ( self.t == saf.t )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(saf) ( saf.t )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(saf) ( saf.t )") ;
end if;
 
  // inst_ref<Timer>
assign self.tim = saf.tim;
if ( self.tim == saf.tim )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(saf) ( saf.tim )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(saf) ( saf.tim )") ;
end if;
 
  // inst<Event>
assign self.ev = saf.ev;
LOG::LogInfo(message:"assign self.attribute saf.attribute ( LOG1(timer event) )") ; 

  // boolean
select many smfs from instances of ETOB;
for each smf in smfs
assign self.btrue = smf.bfalse;
if ( self.btrue == false )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(smf) ( false )") ;
end if;
 
assign self.btrue = smf.btrue;
if ( self.btrue == true )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(smf) ( true )") ;
end if;
 
assign self.bfalse = smf.btrue;
if ( self.bfalse == true )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(smf) ( true )") ;
end if;
 
assign self.bfalse = smf.bfalse;
if ( self.bfalse == false )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(smf) ( false )") ;
end if;
 
  // integer
assign self.i = smf.etob_id;
if ( self.i == smf.etob_id )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(smf) ( smf.etob_id )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(smf) ( smf.etob_id )") ;
end if;
 
  // real
assign self.r = smf.r;
if ( ((self.r - smf.r ) < .1) OR ((smf.r - self.r) < .1) )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(smf) ( assign self.r = smf.r )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(smf) ( assign self.r = smf.r )") ;
end if;
 
assign self.r = smf.etob_id;
if ( ((self.r - smf.etob_id ) < .1) OR ((smf.etob_id - self.r) < .1) )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(smf) ( assign self.r = smf.etob_id )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(smf) (assign self.r = smf.etob_id )") ;
end if;
 
  // string
assign self.s = smf.s; 
if ( self.s == smf.s ) 
  LOG::LogSuccess(message:"assign self.attribute smf.attribute  etob1 string") ;
else 
  LOG::LogFailure(message:"assign self.attribute smf.attribute ( etob1 string)") ; 
end if; 
  // unique_id
assign self.u = smf.u;
if ( self.u == smf.u )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(smf) ( smf.u )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(smf) ( smf.u )") ;
end if;
 
  // date
assign self.d = smf.d;
if ( self.d == smf.d )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(smf) ( smf.d )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(smf) ( smf.d )") ;
end if;
 
  // timestamp
assign self.t = smf.t;
if ( self.t == smf.t )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(smf) ( smf.t )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(smf) ( smf.t )") ;
end if;
 
  // inst_ref<Timer>
assign self.tim = smf.tim;
if ( self.tim == smf.tim )
  LOG::LogSuccess(message:"assign self.attribute other.attribute(smf) ( smf.tim )") ;
else
  LOG::LogFailure(message:"assign self.attribute other.attribute(smf) ( smf.tim )") ;
end if;
 
  // inst<Event>
assign self.ev = smf.ev;
generate EV1:''Event Instance''( message:"assign self.attribute smf.attribute ( LOG1(timer event) )") to event_instance; 
end for;

  // boolean
assign saf.btrue = false;
if ( saf.btrue == false )
  LOG::LogSuccess(message:"assign other.attribute(saf) constant ( false )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) constant ( false )") ;
end if;
 
assign saf.btrue = true;
if ( saf.btrue == true )
  LOG::LogSuccess(message:"assign other.attribute(saf) constant ( true )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) constant ( true )") ;
end if;
 
assign saf.bfalse = true;
if ( saf.bfalse == true )
  LOG::LogSuccess(message:"assign other.attribute(saf) constant ( true )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) constant ( true )") ;
end if;
 
assign saf.bfalse = false;
if ( saf.bfalse == false )
  LOG::LogSuccess(message:"assign other.attribute(saf) constant ( false )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) constant ( false )") ;
end if;
 
  // integer
assign saf.etob_id = 435;
if ( saf.etob_id == 435 )
  LOG::LogSuccess(message:"assign other.attribute(saf) constant ( 435 )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) constant ( 435 )") ;
end if;
 
  // real
assign saf.r = 12;
if ( saf.r == 12 )
  LOG::LogSuccess(message:"assign other.attribute(saf) constant ( 12 )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) constant ( 12 )") ;
end if;
 
assign saf.r = 17.6;
LOG::LogInfo(message:"assign other.attribute(saf) constant ( 17.6 )");
 
  // string
assign saf.s = "test 222";
if ( saf.s == "test 222" )
  LOG::LogSuccess(message:"assign other.attribute constant ( test 222 )") ;
else 
  LOG::LogFailure(message:"assign other.attribute constant ( test 222 )") ; 
end if;

  // boolean
assign temp10 = true;
assign temp11 = false;
assign saf.btrue = temp11;
if ( saf.btrue == false )
  LOG::LogSuccess(message:"assign other.attribute(saf) local_existing ( false )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) local_existing ( false )") ;
end if;
 
assign saf.btrue = temp10;
if ( saf.btrue == true )
  LOG::LogSuccess(message:"assign other.attribute(saf) local_existing ( true )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) local_existing ( true )") ;
end if;
 
assign saf.bfalse = temp10;
if ( saf.bfalse == true )
  LOG::LogSuccess(message:"assign other.attribute(saf) local_existing ( true )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) local_existing ( true )") ;
end if;
 
assign saf.bfalse = temp11;
if ( saf.bfalse == false )
  LOG::LogSuccess(message:"assign other.attribute(saf) local_existing ( false )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) local_existing ( false )") ;
end if;
 
  // integer
assign temp12 = 31;
assign saf.etob_id = temp12;
if ( saf.etob_id == 31 )
  LOG::LogSuccess(message:"assign other.attribute(saf) local_existing ( 31 )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) local_existing ( 31 )") ;
end if;
 
  // real
assign temp13 = 9.874;
assign saf.r = temp12;
LOG::LogInfo( message:"assign other.attribute(saf) local_existing ( 31.0 )") ;
 
assign saf.r = temp13;
LOG::LogInfo( message:"assign other.attribute(saf) local_existing ( 9.874 )") ;
 
  // string
assign temp14 = "test 333";
assign saf.s = temp14;
if ( saf.s == "test 333" )
  LOG::LogSuccess(message:"assign other.attribute local_existing ( test 333 )") ;
else 
  LOG::LogFailure(message:"assign other.attribute local_existing ( test 333 )") ; 
end if;

  // date
assign saf.d = t25;
if ( saf.d == rcvd_evt.d )
  LOG::LogSuccess(message:"assign other.attribute(saf) local_existing ( rcvd_evt.d )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) local_existing ( rcvd_evt.d )") ;
end if;
 
  // timestamp
assign saf.t = t26;
if ( saf.t == rcvd_evt.ts )
  LOG::LogSuccess(message:"assign other.attribute(saf) local_existing ( rcvd_evt.ts )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) local_existing ( rcvd_evt.ts )") ;
end if;
 
  // inst_ref<Timer>
assign saf.tim = temp_timer;
if ( saf.tim == temp_timer )
  LOG::LogSuccess(message:"assign other.attribute(saf) local_existing ( temp_timer )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) local_existing ( temp_timer )") ;
end if;
 
  // inst<Event>
assign saf.ev = ev_inst1;
generate EV1:''Event Instance''( message:"assign other.attribute local_existing ( LOG1(timer event) )") to event_instance; 
  // boolean
assign saf.btrue = rcvd_evt.tfalse;
if ( saf.btrue == false )
  LOG::LogSuccess(message:"assign other.attribute(saf) rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) rcvd_evt ( false )") ;
end if;
 
assign saf.btrue = rcvd_evt.ttrue;
if ( saf.btrue == true )
  LOG::LogSuccess(message:"assign other.attribute(saf) rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) rcvd_evt ( true )") ;
end if;
 
  // integer
assign saf.etob_id = rcvd_evt.i;
if ( saf.etob_id == 11 )
  LOG::LogSuccess(message:"assign other.attribute(saf) rcvd_evt ( 11 )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) rcvd_evt ( 11 )") ;
end if;
 
  // real
assign saf.r = rcvd_evt.r;
LOG::LogInfo( message:"assign other.attribute(saf) rcvd_evt ( 12.34 )") ;
 
assign saf.r = rcvd_evt.i;
if ( saf.r == 11.0 )
  LOG::LogSuccess(message:"assign other.attribute(saf) rcvd_evt ( 11.0 )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) rcvd_evt ( 11.0 )") ;
end if;
 
  // string
assign saf.s = rcvd_evt.s;
if ( saf.s == "fit" )
  LOG::LogSuccess(message:"assign saf.attribute rcvd_evt ( fit )") ;
else
  LOG::LogFailure(message:"assign saf.attribute rcvd_evt ( fit )") ;
end if;
 
  // date
assign saf.d = rcvd_evt.d;
if ( saf.d == rcvd_evt.d )
  LOG::LogSuccess(message:"assign other.attribute(saf) rcvd_evt ( rcvd_evt.d )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) rcvd_evt ( rcvd_evt.d )") ;
end if;
 
  // timestamp
assign saf.t = rcvd_evt.ts;
if ( saf.t == rcvd_evt.ts )
  LOG::LogSuccess(message:"assign other.attribute(saf) rcvd_evt ( rcvd_evt.ts )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) rcvd_evt ( rcvd_evt.ts )") ;
end if;
 
  // inst_ref<Timer>
assign saf.tim = rcvd_evt.tim;
if ( saf.tim == rcvd_evt.tim )
  LOG::LogSuccess(message:"assign other.attribute(saf) rcvd_evt ( rcvd_evt.tim )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) rcvd_evt ( rcvd_evt.tim )") ;
end if;
 
  // inst<Event>
assign saf.ev = rcvd_evt.ev;
generate EV1:''Event Instance''( message:"assign saf.attribute rcvd_evt ( LOG1(timer event) )") to event_instance; 
 
  // boolean
assign saf.btrue = self.bfalse;
if ( saf.btrue == false )
  LOG::LogSuccess(message:"assign other.attribute(saf) self.attribute ( false )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) self.attribute ( false )") ;
end if;
 
assign saf.btrue = true;
assign saf.bfalse = self.btrue;
if ( saf.bfalse == true )
  LOG::LogSuccess(message:"assign other.attribute(saf) self.attribute ( true )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) self.attribute ( true )") ;
end if;
 
assign saf.bfalse = false;

  // integer
assign saf.etob_id = self.i;
if ( saf.etob_id == 3 )
  LOG::LogSuccess(message:"assign other.attribute(saf) self.attribute ( 3 )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) self.attribute ( 3 )") ;
end if;
 
  // real
assign saf.r = self.r;
LOG::LogInfo(message:"assign other.attribute(saf) self.attribute ( 3.0 )") ;
 
assign saf.r = self.i;
LOG::LogInfo( message:"assign other.attribute(saf) self.attribute ( 3.0 )") ;
 
  // string
assign saf.s = self.s;
if ( saf.s == "etob2 string" )
  LOG::LogSuccess(message:"assign saf.attribute self.attribute ( etob2 string )") ;
else
  LOG::LogFailure(message:"assign saf.attribute self.attribute ( etob2 string )") ;
end if;

  // date
assign saf.d = self.d;
if ( saf.d == self.d )
  LOG::LogSuccess(message:"assign other.attribute(saf) self.attribute ( self.d )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) self.attribute ( self.d )") ;
end if;
 
  // timestamp
assign saf.t = self.t;
if ( saf.t == self.t )
  LOG::LogSuccess(message:"assign other.attribute(saf) self.attribute ( self.t )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) self.attribute ( self.t )") ;
end if;
 
  // inst_ref<Timer>
assign saf.tim = self.tim;
if ( saf.tim == self.tim )
  LOG::LogSuccess(message:"assign other.attribute(saf) self.attribute ( self.tim )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) self.attribute ( self.tim )") ;
end if;
 
  // inst<Event>
assign saf.ev = self.ev;
generate EV1:''Event Instance''( message:"assign saf.attribute self.attribute ( LOG1(timer event) )") to event_instance; 

  // boolean
assign saf.btrue = self.r1bfalse;
if ( saf.btrue == false )
  LOG::LogSuccess(message:"assign other.attribute(saf) self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) self.referential_attribute ( false )") ;
end if;
 
assign saf.btrue = self.r1btrue;
if ( saf.btrue == true )
  LOG::LogSuccess(message:"assign other.attribute(saf) self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) self.referential_attribute ( true )") ;
end if;
 
assign saf.bfalse = self.r1btrue;
if ( saf.bfalse == true )
  LOG::LogSuccess(message:"assign other.attribute(saf) self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) self.referential_attribute ( true )") ;
end if;
 
assign saf.bfalse = self.r1bfalse;
if ( saf.bfalse == false )
  LOG::LogSuccess(message:"assign other.attribute(saf) self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) self.referential_attribute ( false )") ;
end if;
 
  // integer
assign saf.etob_id = self.etob_id;
if ( saf.etob_id == 2 )
  LOG::LogSuccess(message:"assign other.attribute(saf) self.referential_attribute ( 2 )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) self.referential_attribute ( 2 )") ;
end if;
 
  // real
assign saf.r = self.r1r;
LOG::LogInfo(message:"assign other.attribute(saf) self.referential_attribute ( 2.1718 )") ;
 
assign saf.r = self.etob_id;
LOG::LogInfo( message:"assign other.attribute(saf) self.referential_attribute ( 2.0 )") ;
 
  // string
assign saf.s = self.r1s;
if ( saf.s == "etob1 string" )
  LOG::LogSuccess(message:"assign saf.attribute self.referential attribute ( etob1 string)") ;
else
  LOG::LogFailure(message:"assign saf.attribute self.referential attribute ( etob1 string)") ;
end if;

  // date
assign saf.d = self.r1d;
if ( saf.d == self.r1d )
  LOG::LogSuccess(message:"assign other.attribute(saf) self.referential_attribute ( self.r1d )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) self.referential_attribute ( self.r1d )") ;
end if;
 
  // timestamp
assign saf.t = self.r1t;
if ( saf.t == self.r1t )
  LOG::LogSuccess(message:"assign other.attribute(saf) self.referential_attribute ( self.r1t )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) self.referential_attribute ( self.r1t )") ;
end if;
 
  // inst_ref<Timer>
assign saf.tim = self.r1tim;
if ( saf.tim == self.r1tim )
  LOG::LogSuccess(message:"assign other.attribute(saf) self.referential_attribute ( self.r1tim )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) self.referential_attribute ( self.r1tim )") ;
end if;
 
  // inst<Event>
assign saf.ev = self.r1ev;
generate EV1:''Event Instance''( message:"assign saf.attribute self.referential attribute ( LOG1(timer event) )") to event_instance; 

  // boolean
assign saf.btrue = saf.bfalse;
if ( saf.btrue == false )
  LOG::LogSuccess(message:"assign other.attribute(saf) other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) other.attribute(saf) ( false )") ;
end if;

assign saf.btrue = true; 
assign saf.bfalse = saf.btrue;
if ( saf.bfalse == true )
  LOG::LogSuccess(message:"assign other.attribute(saf) other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) other.attribute(saf) ( true )") ;
end if;
assign saf.bfalse = false; 
 
  // integer
assign saf.etob_id = saf.etob_id;
if ( saf.etob_id == 2 )
  LOG::LogSuccess(message:"assign other.attribute(saf) other.attribute(saf) ( 2 )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) other.attribute(saf) ( 2 )") ;
end if;
 
  // real
assign saf.r = saf.r;
LOG::LogInfo( message:"assign other.attribute(saf) other.attribute(saf) ( 2.0 )") ;
 
assign saf.r = saf.etob_id;
LOG::LogInfo( message:"assign other.attribute(saf) other.attribute(saf) ( 2.0 )") ;
 
  // string
assign saf.s = saf.s;
if ( saf.s == "etob1 string" )
  LOG::LogSuccess(message:"assign saf.attribute saf.attribute ( etob1 string)") ;
else
  LOG::LogFailure(message:"assign saf.attribute saf.attribute ( etob1 string)") ;
end if;

  // date
assign saf.d = saf.d;
if ( saf.d == saf.d )
  LOG::LogSuccess(message:"assign other.attribute(saf) other.attribute(saf) ( saf.d )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) other.attribute(saf) ( saf.d )") ;
end if;
 
  // timestamp
assign saf.t = saf.t;
if ( saf.t == saf.t )
  LOG::LogSuccess(message:"assign other.attribute(saf) other.attribute(saf) ( saf.t )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) other.attribute(saf) ( saf.t )") ;
end if;
 
  // inst_ref<Timer>
assign saf.tim = saf.tim;
if ( saf.tim == saf.tim )
  LOG::LogSuccess(message:"assign other.attribute(saf) other.attribute(saf) ( saf.tim )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) other.attribute(saf) ( saf.tim )") ;
end if;
 
  // inst<Event>
assign saf.ev = saf.ev;
generate EV1:''Event Instance''( message:"assign saf.attribute saf.attribute ( LOG1(timer event)") to event_instance; 

  // boolean
for each smf in smfs
assign saf.btrue = smf.bfalse;
if ( saf.btrue == false )
  LOG::LogSuccess(message:"assign other.attribute(saf) other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) other.attribute(smf) ( false )") ;
end if;
 
assign saf.btrue = true;
assign saf.bfalse = smf.btrue;
if ( saf.bfalse == true )
  LOG::LogSuccess(message:"assign other.attribute(saf) other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) other.attribute(smf) ( true )") ;
end if;
 
assign saf.bfalse = false;
 
  // integer
assign saf.etob_id = smf.etob_id;
if ( saf.etob_id == smf.etob_id )
  LOG::LogSuccess(message:"assign other.attribute(saf) other.attribute(smf) ( smf.etob_id )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) other.attribute(smf) ( smf.etob_id )") ;
end if;
 
  // real
assign saf.r = smf.r;
LOG::LogInfo(message:"assign other.attribute(saf) other.attribute(smf) ( smf.r )") ;
 
assign saf.r = smf.etob_id;
LOG::LogInfo( message:"assign other.attribute(saf) other.attribute(smf) ( smf.etob_id )") ;
 
  // string
assign saf.s = smf.s;
if ( saf.s == smf.s )
  LOG::LogSuccess(message:"assign saf.attribute smf.attribute ( etob1 string)") ;
else
  LOG::LogFailure(message:"assign saf.attribute smf.attribute ( etob1 string)") ;
end if;

  // date
assign saf.d = smf.d;
if ( saf.d == smf.d )
  LOG::LogSuccess(message:"assign other.attribute(saf) other.attribute(smf) ( smf.d )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) other.attribute(smf) ( smf.d )") ;
end if;
 
  // timestamp
assign saf.t = smf.t;
if ( saf.t == smf.t )
  LOG::LogSuccess(message:"assign other.attribute(saf) other.attribute(smf) ( smf.t )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) other.attribute(smf) ( smf.t )") ;
end if;
 
  // inst_ref<Timer>
assign saf.tim = smf.tim;
if ( saf.tim == smf.tim )
  LOG::LogSuccess(message:"assign other.attribute(saf) other.attribute(smf) ( smf.tim )") ;
else
  LOG::LogFailure(message:"assign other.attribute(saf) other.attribute(smf) ( smf.tim )") ;
end if;
 
  // inst<Event>
assign saf.ev = smf.ev;
generate EV1:''Event Instance''( message:"assign saf.attribute smf.attribute ( LOG1(timer event) )") to event_instance; 
end for;


for each smf in smfs
  // boolean
assign smf.btrue = false;
if ( smf.btrue == false )
  LOG::LogSuccess(message:"assign other.attribute(smf) constant ( false )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) constant ( false )") ;
end if;
 
assign smf.btrue = true;
if ( smf.btrue == true )
  LOG::LogSuccess(message:"assign other.attribute(smf) constant ( true )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) constant ( true )") ;
end if;
 
assign smf.bfalse = true;
if ( smf.bfalse == true )
  LOG::LogSuccess(message:"assign other.attribute(smf) constant ( true )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) constant ( true )") ;
end if;
 
assign smf.bfalse = false;
if ( smf.bfalse == false )
  LOG::LogSuccess(message:"assign other.attribute(smf) constant ( false )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) constant ( false )") ;
end if;
 
  // integer
assign smf.etob_id = 29;
if ( smf.etob_id == 29 )
  LOG::LogSuccess(message:"assign other.attribute(smf) constant ( 29 )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) constant ( 29 )") ;
end if;
 
  // real
assign smf.r = 12;
if ( smf.r == 12.0 )
  LOG::LogSuccess(message:"assign other.attribute(smf) constant ( 12.0 )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) constant ( 12.0 )") ;
end if;
 
assign smf.r = 17.5;
if ( smf.r == 17.5 )
  LOG::LogSuccess(message:"assign other.attribute(smf) constant ( 17.5 )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) constant ( 17.5 )") ;
end if;
 
  // string
assign smf.s = "test 444";
if ( smf.s == "test 444" )
  LOG::LogSuccess(message:"assign other.attribute(smf) constant ( test 444 )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) constant ( test 444 )") ;
end if;


  // boolean
assign smf.btrue = temp11;
if ( smf.btrue == false )
  LOG::LogSuccess(message:"assign other.attribute(smf) local_existing ( false )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) local_existing ( false )") ;
end if;
 
assign smf.btrue = temp10;
if ( smf.btrue == true )
  LOG::LogSuccess(message:"assign other.attribute(smf) local_existing ( true )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) local_existing ( true )") ;
end if;
 
assign smf.bfalse = temp10;
if ( smf.bfalse == true )
  LOG::LogSuccess(message:"assign other.attribute(smf) local_existing ( true )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) local_existing ( true )") ;
end if;
 
assign smf.bfalse = temp11;
if ( smf.bfalse == false )
  LOG::LogSuccess(message:"assign other.attribute(smf) local_existing ( false )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) local_existing ( false )") ;
end if;
 
  // integer
assign smf.etob_id = temp12;
if ( smf.etob_id == 31 )
  LOG::LogSuccess(message:"assign other.attribute(smf) local_existing ( 31 )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) local_existing ( 31 )") ;
end if;
 
  // real
assign smf.r = temp12;
LOG::LogInfo( message:"assign other.attribute(smf) local_existing ( 31.0 )") ;
 
assign smf.r = temp13;
LOG::LogInfo( message:"assign other.attribute(smf) local_existing ( 9.874 )") ;
 
  // string
assign smf.s = temp14;
if ( smf.s == "test 333" )
  LOG::LogSuccess(message:"assign other.attribute(smf) constant ( test 333 )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) constant ( test 333 )") ;
end if;

  // date
assign smf.d = t25;
if ( rcvd_evt.d == smf.d )
  LOG::LogSuccess(message:"assign other.attribute(smf) local_existing ( rcvd_evt.d )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) local_existing ( rcvd_evt.d )") ;
end if;
 
  // timestamp
assign smf.t = t26;
if ( rcvd_evt.ts == smf.t )
  LOG::LogSuccess(message:"assign other.attribute(smf) local_existing ( rcvd_evt.ts )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) local_existing ( rcvd_evt.ts )") ;
end if;
 
  // inst_ref<Timer>
assign smf.tim = temp_timer;
if ( smf.tim == temp_timer )
  LOG::LogSuccess(message:"assign other.attribute(smf) local_existing ( temp_timer )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) local_existing ( temp_timer )") ;
end if;
 
  // inst<Event>
assign smf.ev = ev_inst1;
generate EV1:''Event Instance''( message:"assign other.attribute(smf) local_existing ( LOG1(timer event) )") to event_instance; 

  // boolean
assign smf.btrue = rcvd_evt.tfalse;
if ( smf.btrue == false )
  LOG::LogSuccess(message:"assign other.attribute(smf) rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) rcvd_evt ( false )") ;
end if;
 
assign smf.btrue = rcvd_evt.ttrue;
if ( smf.btrue == true )
  LOG::LogSuccess(message:"assign other.attribute(smf) rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) rcvd_evt ( true )") ;
end if;
 
  // integer
assign smf.etob_id = rcvd_evt.i;
if ( smf.etob_id == 11 )
  LOG::LogSuccess(message:"assign other.attribute(smf) rcvd_evt ( 11 )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) rcvd_evt ( 11 )") ;
end if;
 
  // real
assign smf.r = rcvd_evt.i;
LOG::LogInfo(message:"assign other.attribute(smf) rcvd_evt ( 11.0 )") ;
 
assign smf.r = rcvd_evt.r;
LOG::LogInfo( message:"assign other.attribute(smf) rcvd_evt ( 12.34 )") ;
 
  // string
assign smf.s = rcvd_evt.s;
if ( smf.s == "fit" )
  LOG::LogSuccess(message:"assign smf.attribute rcvd_evt ( fit )") ;
else
  LOG::LogFailure(message:"assign smf.attribute rcvd_evt ( fit )") ;
end if;

  // date
assign smf.d = rcvd_evt.d;
if ( smf.d == rcvd_evt.d )
  LOG::LogSuccess(message:"assign other.attribute(smf) rcvd_evt ( rcvd_evt.d )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) rcvd_evt ( rcvd_evt.d )") ;
end if;
 
  // timestamp
assign smf.t = rcvd_evt.ts;
if ( smf.t == rcvd_evt.ts )
  LOG::LogSuccess(message:"assign other.attribute(smf) rcvd_evt ( rcvd_evt.ts )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) rcvd_evt ( rcvd_evt.ts )") ;
end if;
 
  // inst_ref<Timer>
assign smf.tim = rcvd_evt.tim;
if ( smf.tim == rcvd_evt.tim )
  LOG::LogSuccess(message:"assign other.attribute(smf) rcvd_evt ( rcvd_evt.tim )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) rcvd_evt ( rcvd_evt.tim )") ;
end if;
 
  // inst<Event>
assign smf.ev = rcvd_evt.ev;
generate EV1:''Event Instance''( message:"assign smf.attribute rcvd_evt ( LOG1(timer event) )") to event_instance; 

  // boolean
assign smf.btrue = self.bfalse;
if ( smf.btrue == false )
  LOG::LogSuccess(message:"assign other.attribute(smf) self.attribute ( false )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) self.attribute ( false )") ;
end if;
 
assign smf.btrue = self.btrue;
if ( smf.btrue == true )
  LOG::LogSuccess(message:"assign other.attribute(smf) self.attribute ( true )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) self.attribute ( true )") ;
end if;
 
  // integer
assign smf.etob_id = self.i;
if ( smf.etob_id == 3 )
  LOG::LogSuccess(message:"assign other.attribute(smf) self.attribute ( 3 )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) self.attribute ( 3 )") ;
end if;
 
  // real
assign smf.r = self.i;
LOG::LogInfo( message:"assign other.attribute(smf) self.attribute ( 3.0 )") ;
 
assign smf.r = self.r;
LOG::LogInfo( message:"assign other.attribute(smf) self.attribute ( 3.0 )") ;
 
  // string
assign smf.s = self.s;
if ( smf.s == "etob2 string" )
  LOG::LogSuccess(message:"assign smf.attribute self.attribute ( etob2 string )") ;
else
  LOG::LogFailure(message:"assign smf.attribute self.attribute ( etob2 string )") ;
end if;
 
  // date
assign smf.d = self.d;
if ( smf.d == self.d )
  LOG::LogSuccess(message:"assign other.attribute(smf) self.attribute ( self.d )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) self.attribute ( self.d )") ;
end if;
 
  // timestamp
assign smf.t = self.t;
if ( smf.t == self.t )
  LOG::LogSuccess(message:"assign other.attribute(smf) self.attribute ( self.t )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) self.attribute ( self.t )") ;
end if;
 
  // inst_ref<Timer>
assign smf.tim = self.tim;
if ( smf.tim == self.tim )
  LOG::LogSuccess(message:"assign other.attribute(smf) self.attribute ( self.tim )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) self.attribute ( self.tim )") ;
end if;
 
  // inst<Event>
assign smf.ev = self.ev;
generate EV1:''Event Instance''( message:"assign smf.attribute self.attribute ( LOG1(timer event) )") to event_instance; 
 
  // boolean
assign smf.btrue = self.r1bfalse;
if ( smf.btrue == false )
  LOG::LogSuccess(message:"assign other.attribute(smf) self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) self.referential_attribute ( false )") ;
end if;
 
assign smf.btrue = self.r1btrue;
if ( smf.btrue == true )
  LOG::LogSuccess(message:"assign other.attribute(smf) self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) self.referential_attribute ( true )") ;
end if;
 
  // integer
assign smf.etob_id = self.etob_id;
if ( smf.etob_id == 2 )
  LOG::LogSuccess(message:"assign other.attribute(smf) self.referential_attribute ( 2 )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) self.referential_attribute ( 2 )") ;
end if;
 
  // real
assign smf.r = self.etob_id;
LOG::LogInfo(message:"assign other.attribute(smf) self.referential_attribute ( 2.0 )") ;
 
assign smf.r = self.r1r;
LOG::LogInfo( message:"assign other.attribute(smf) self.referential_attribute ( 2.1718 )") ;
 
  // string
assign smf.s = self.r1s;
if ( smf.s == "etob1 string" )
  LOG::LogSuccess(message:"assign smf.attribute self.referential attribute ( etob1 string )") ;
else
  LOG::LogFailure(message:"assign smf.attribute self.referential attribute ( etob1 string )") ;
end if;
 
  // date
assign smf.d = self.r1d;
if ( smf.d == self.r1d )
  LOG::LogSuccess(message:"assign other.attribute(smf) self.referential_attribute ( self.r1d )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) self.referential_attribute ( self.r1d )") ;
end if;
 
  // timestamp
assign smf.t = self.r1t;
if ( smf.t == self.r1t )
  LOG::LogSuccess(message:"assign other.attribute(smf) self.referential_attribute ( self.r1t )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) self.referential_attribute ( self.r1t )") ;
end if;
 
  // inst_ref<Timer>
assign smf.tim = self.r1tim;
if ( smf.tim == self.r1tim )
  LOG::LogSuccess(message:"assign other.attribute(smf) self.referential_attribute ( self.r1tim )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) self.referential_attribute ( self.r1tim )") ;
end if;
 
  // inst<Event>
assign smf.ev = self.r1ev;
generate EV1:''Event Instance''( message:"assign smf.attribute self.referentialattribute ( LOG1(timer event) )") to event_instance; 

  // boolean
assign smf.btrue = saf.bfalse;
if ( smf.btrue == false )
  LOG::LogSuccess(message:"assign other.attribute(smf) other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) other.attribute(saf) ( false )") ;
end if;

assign saf.btrue = true; 
assign smf.btrue = saf.btrue;
if ( smf.btrue == true )
  LOG::LogSuccess(message:"assign other.attribute(smf) other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) other.attribute(saf) ( true )") ;
end if;
 
  // integer
assign smf.etob_id = saf.etob_id;
if ( smf.etob_id == 2 )
  LOG::LogSuccess(message:"assign other.attribute(smf) other.attribute(saf) ( 2 )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) other.attribute(saf) ( 2 )") ;
end if;
 
  // real
assign smf.r = saf.etob_id;
LOG::LogInfo(message:"assign other.attribute(smf) other.attribute(saf) ( 2.0 )") ;
 
assign smf.r = saf.r;
LOG::LogInfo( message:"assign other.attribute(smf) other.attribute(saf) ( 2.0 )") ;
 
  // string
assign saf.s = "test 444";
assign smf.s = saf.s;
if ( smf.s == "test 444" )
  LOG::LogSuccess(message:"assign smf.attribute saf.attribute ( test 444 )") ;
else
  LOG::LogFailure(message:"assign smf.attribute saf.attribute ( test 444 )") ;
end if;
 
  // date
assign smf.d = saf.d;
if ( smf.d == saf.d )
  LOG::LogSuccess(message:"assign other.attribute(smf) other.attribute(saf) ( saf.d )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) other.attribute(saf) ( saf.d )") ;
end if;
 
  // timestamp
assign smf.t = saf.t;
if ( smf.t == saf.t )
  LOG::LogSuccess(message:"assign other.attribute(smf) other.attribute(saf) ( saf.t )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) other.attribute(saf) ( saf.t )") ;
end if;
 
  // inst_ref<Timer>
assign smf.tim = saf.tim;
if ( smf.tim == saf.tim )
  LOG::LogSuccess(message:"assign other.attribute(smf) other.attribute(saf) ( saf.tim )") ;
else
  LOG::LogFailure(message:"assign other.attribute(smf) other.attribute(saf) ( saf.tim )") ;
end if;
 
  // inst<Event>
assign smf.ev = saf.ev;
generate EV1:''Event Instance''( message:"assign smf.attribute saf.attribute ( LOG1(timer event) )") to event_instance; 

end for;

LOG::LogInfo(message:"Completed assign test") ;
 
//generate event to commence next test
generate ET16:''Start and test''(tfalse:false, ttrue: true) to self;

',
	'');
INSERT INTO SM_NSTXN
	VALUES (2097153,
	2097156,
	2097154,
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097153,
	2097156,
	2097155,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097154,
	2097156,
	2097155,
	2097156,
	0);
INSERT INTO SM_TXN
	VALUES (2097154,
	2097156,
	2097156,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097155,
	2097156,
	2097156,
	2097157,
	0);
INSERT INTO SM_TXN
	VALUES (2097155,
	2097156,
	2097158,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097156,
	2097156,
	2097158,
	2097158,
	0);
INSERT INTO SM_TXN
	VALUES (2097156,
	2097156,
	2097159,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097157,
	2097156,
	2097159,
	2097159,
	0);
INSERT INTO SM_TXN
	VALUES (2097157,
	2097156,
	2097160,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097158,
	2097156,
	2097160,
	2097160,
	0);
INSERT INTO SM_TXN
	VALUES (2097158,
	2097156,
	2097161,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097159,
	2097156,
	2097161,
	2097161,
	0);
INSERT INTO SM_TXN
	VALUES (2097159,
	2097156,
	2097157,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097160,
	2097156,
	2097162,
	2097162,
	0);
INSERT INTO SM_TXN
	VALUES (2097160,
	2097156,
	2097163,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097161,
	2097156,
	2097163,
	2097163,
	0);
INSERT INTO SM_TXN
	VALUES (2097161,
	2097156,
	2097164,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097162,
	2097156,
	2097153,
	2097153,
	0);
INSERT INTO SM_TXN
	VALUES (2097162,
	2097156,
	2097153,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097163,
	2097156,
	2097165,
	2097164,
	0);
INSERT INTO SM_TXN
	VALUES (2097163,
	2097156,
	2097162,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097164,
	2097156,
	2097153,
	2097165,
	0);
INSERT INTO SM_TXN
	VALUES (2097164,
	2097156,
	2097165,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097165,
	2097156,
	2097164,
	2097166,
	0);
INSERT INTO SM_TXN
	VALUES (2097165,
	2097156,
	2097166,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097166,
	2097156,
	2097166,
	2097167,
	0);
INSERT INTO SM_TXN
	VALUES (2097166,
	2097156,
	2097167,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097167,
	2097156,
	2097167,
	2097167,
	0);
INSERT INTO SM_TXN
	VALUES (2097167,
	2097156,
	2097168,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097168,
	2097156,
	2097168,
	2097168,
	0);
INSERT INTO SM_TXN
	VALUES (2097168,
	2097156,
	2097154,
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
	752,
	1360,
	1072,
	1456);
INSERT INTO GD_GE
	VALUES (2097155,
	2097153,
	2097154,
	41);
INSERT INTO GD_SHP
	VALUES (2097155,
	1712,
	1520,
	2128,
	1632);
INSERT INTO GD_GE
	VALUES (2097156,
	2097153,
	2097155,
	41);
INSERT INTO GD_SHP
	VALUES (2097156,
	1712,
	1696,
	2128,
	1808);
INSERT INTO GD_GE
	VALUES (2097157,
	2097153,
	2097156,
	41);
INSERT INTO GD_SHP
	VALUES (2097157,
	1712,
	1872,
	2128,
	1968);
INSERT INTO GD_GE
	VALUES (2097158,
	2097153,
	2097157,
	41);
INSERT INTO GD_SHP
	VALUES (2097158,
	2400,
	1536,
	2816,
	1632);
INSERT INTO GD_GE
	VALUES (2097159,
	2097153,
	2097158,
	41);
INSERT INTO GD_SHP
	VALUES (2097159,
	1712,
	2016,
	2128,
	2112);
INSERT INTO GD_GE
	VALUES (2097160,
	2097153,
	2097159,
	41);
INSERT INTO GD_SHP
	VALUES (2097160,
	2400,
	2016,
	2816,
	2112);
INSERT INTO GD_GE
	VALUES (2097161,
	2097153,
	2097160,
	41);
INSERT INTO GD_SHP
	VALUES (2097161,
	2400,
	1872,
	2816,
	1968);
INSERT INTO GD_GE
	VALUES (2097162,
	2097153,
	2097161,
	41);
INSERT INTO GD_SHP
	VALUES (2097162,
	2400,
	1712,
	2816,
	1808);
INSERT INTO GD_GE
	VALUES (2097163,
	2097153,
	2097162,
	41);
INSERT INTO GD_SHP
	VALUES (2097163,
	752,
	1680,
	1072,
	1776);
INSERT INTO GD_GE
	VALUES (2097164,
	2097153,
	2097163,
	41);
INSERT INTO GD_SHP
	VALUES (2097164,
	752,
	1840,
	1072,
	1936);
INSERT INTO GD_GE
	VALUES (2097165,
	2097153,
	2097164,
	41);
INSERT INTO GD_SHP
	VALUES (2097165,
	752,
	2016,
	1072,
	2112);
INSERT INTO GD_GE
	VALUES (2097166,
	2097153,
	2097165,
	41);
INSERT INTO GD_SHP
	VALUES (2097166,
	752,
	1536,
	1072,
	1616);
INSERT INTO GD_GE
	VALUES (2097167,
	2097153,
	2097166,
	41);
INSERT INTO GD_SHP
	VALUES (2097167,
	1280,
	1360,
	1600,
	1456);
INSERT INTO GD_GE
	VALUES (2097168,
	2097153,
	2097167,
	41);
INSERT INTO GD_SHP
	VALUES (2097168,
	1280,
	1520,
	1600,
	1616);
INSERT INTO GD_GE
	VALUES (2097169,
	2097153,
	2097168,
	41);
INSERT INTO GD_SHP
	VALUES (2097169,
	1280,
	1680,
	1600,
	1776);
INSERT INTO GD_GE
	VALUES (2097170,
	2097153,
	2097153,
	42);
INSERT INTO GD_CON
	VALUES (2097170,
	2097155,
	2097156,
	0);
INSERT INTO GD_CTXT
	VALUES (2097170,
	0,
	0,
	0,
	0,
	0,
	0,
	2176,
	1675,
	2380,
	1719,
	16,
	2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097171,
	2097170,
	2128,
	1616,
	2176,
	1616,
	0);
INSERT INTO GD_LS
	VALUES (2097172,
	2097170,
	2176,
	1616,
	2176,
	1760,
	2097171);
INSERT INTO GD_LS
	VALUES (2097173,
	2097170,
	2176,
	1760,
	2128,
	1760,
	2097172);
INSERT INTO GD_GE
	VALUES (2097174,
	2097153,
	2097154,
	42);
INSERT INTO GD_CON
	VALUES (2097174,
	2097156,
	2097157,
	0);
INSERT INTO GD_CTXT
	VALUES (2097174,
	0,
	0,
	0,
	0,
	0,
	0,
	2160,
	1840,
	2387,
	1893,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097175,
	2097174,
	2128,
	1792,
	2176,
	1792,
	0);
INSERT INTO GD_LS
	VALUES (2097176,
	2097174,
	2176,
	1792,
	2176,
	1920,
	2097175);
INSERT INTO GD_LS
	VALUES (2097177,
	2097174,
	2176,
	1920,
	2128,
	1920,
	2097176);
INSERT INTO GD_GE
	VALUES (2097178,
	2097153,
	2097155,
	42);
INSERT INTO GD_CON
	VALUES (2097178,
	2097157,
	2097159,
	0);
INSERT INTO GD_CTXT
	VALUES (2097178,
	0,
	0,
	0,
	0,
	0,
	0,
	2182,
	1984,
	2394,
	2044,
	22,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097179,
	2097178,
	2128,
	1952,
	2176,
	1952,
	0);
INSERT INTO GD_LS
	VALUES (2097180,
	2097178,
	2176,
	1952,
	2176,
	2048,
	2097179);
INSERT INTO GD_LS
	VALUES (2097181,
	2097178,
	2176,
	2048,
	2128,
	2048,
	2097180);
INSERT INTO GD_GE
	VALUES (2097182,
	2097153,
	2097156,
	42);
INSERT INTO GD_CON
	VALUES (2097182,
	2097159,
	2097160,
	0);
INSERT INTO GD_CTXT
	VALUES (2097182,
	0,
	0,
	0,
	0,
	0,
	0,
	2177,
	2077,
	2363,
	2141,
	-62,
	22,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097183,
	2097182,
	2128,
	2080,
	2400,
	2080,
	0);
INSERT INTO GD_GE
	VALUES (2097184,
	2097153,
	2097157,
	42);
INSERT INTO GD_CON
	VALUES (2097184,
	2097160,
	2097161,
	0);
INSERT INTO GD_CTXT
	VALUES (2097184,
	0,
	0,
	0,
	0,
	0,
	0,
	2632,
	1975,
	2879,
	2024,
	40,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097185,
	2097184,
	2608,
	2016,
	2608,
	1968,
	0);
INSERT INTO GD_GE
	VALUES (2097186,
	2097153,
	2097158,
	42);
INSERT INTO GD_CON
	VALUES (2097186,
	2097161,
	2097162,
	0);
INSERT INTO GD_CTXT
	VALUES (2097186,
	0,
	0,
	0,
	0,
	0,
	0,
	2626,
	1820,
	2854,
	1865,
	34,
	-5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097187,
	2097186,
	2608,
	1872,
	2608,
	1808,
	0);
INSERT INTO GD_GE
	VALUES (2097188,
	2097153,
	2097159,
	42);
INSERT INTO GD_CON
	VALUES (2097188,
	2097162,
	2097158,
	0);
INSERT INTO GD_CTXT
	VALUES (2097188,
	0,
	0,
	0,
	0,
	0,
	0,
	2571,
	1649,
	2845,
	1701,
	-5,
	-8,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097189,
	2097188,
	2592,
	1712,
	2592,
	1632,
	0);
INSERT INTO GD_GE
	VALUES (2097190,
	2097153,
	2097160,
	42);
INSERT INTO GD_CON
	VALUES (2097190,
	2097163,
	2097164,
	0);
INSERT INTO GD_CTXT
	VALUES (2097190,
	0,
	0,
	0,
	0,
	0,
	0,
	742,
	1794,
	941,
	1831,
	-170,
	1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097191,
	2097190,
	928,
	1776,
	928,
	1840,
	0);
INSERT INTO GD_GE
	VALUES (2097192,
	2097153,
	2097161,
	42);
INSERT INTO GD_CON
	VALUES (2097192,
	2097164,
	2097165,
	0);
INSERT INTO GD_CTXT
	VALUES (2097192,
	0,
	0,
	0,
	0,
	0,
	0,
	749,
	1961,
	918,
	1997,
	-163,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097193,
	2097192,
	928,
	1936,
	928,
	2016,
	0);
INSERT INTO GD_GE
	VALUES (2097194,
	2097153,
	2097162,
	42);
INSERT INTO GD_CON
	VALUES (2097194,
	2097154,
	2097154,
	0);
INSERT INTO GD_CTXT
	VALUES (2097194,
	0,
	0,
	0,
	0,
	0,
	0,
	681,
	1279,
	842,
	1314,
	-38,
	-24,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097195,
	2097194,
	752,
	1408,
	624,
	1408,
	0);
INSERT INTO GD_LS
	VALUES (2097196,
	2097194,
	624,
	1408,
	624,
	1328,
	2097195);
INSERT INTO GD_LS
	VALUES (2097197,
	2097194,
	624,
	1328,
	784,
	1328,
	2097196);
INSERT INTO GD_LS
	VALUES (2097198,
	2097194,
	784,
	1328,
	784,
	1360,
	2097197);
INSERT INTO GD_GE
	VALUES (2097199,
	2097153,
	2097163,
	42);
INSERT INTO GD_CON
	VALUES (2097199,
	2097166,
	2097163,
	0);
INSERT INTO GD_CTXT
	VALUES (2097199,
	0,
	0,
	0,
	0,
	0,
	0,
	753,
	1632,
	942,
	1668,
	-159,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097200,
	2097199,
	928,
	1616,
	928,
	1680,
	0);
INSERT INTO GD_GE
	VALUES (2097201,
	2097153,
	2097164,
	42);
INSERT INTO GD_CON
	VALUES (2097201,
	2097154,
	2097166,
	0);
INSERT INTO GD_CTXT
	VALUES (2097201,
	0,
	0,
	0,
	0,
	0,
	0,
	692,
	1475,
	918,
	1515,
	-220,
	-6,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097202,
	2097201,
	928,
	1456,
	928,
	1536,
	0);
INSERT INTO GD_GE
	VALUES (2097203,
	2097153,
	2097165,
	42);
INSERT INTO GD_CON
	VALUES (2097203,
	2097165,
	2097167,
	0);
INSERT INTO GD_CTXT
	VALUES (2097203,
	0,
	0,
	0,
	0,
	0,
	0,
	1098,
	1333,
	1331,
	1372,
	-54,
	-372,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097204,
	2097203,
	1072,
	2080,
	1168,
	2080,
	0);
INSERT INTO GD_LS
	VALUES (2097205,
	2097203,
	1168,
	2080,
	1168,
	1376,
	2097204);
INSERT INTO GD_LS
	VALUES (2097206,
	2097203,
	1168,
	1376,
	1280,
	1376,
	2097205);
INSERT INTO GD_GE
	VALUES (2097207,
	2097153,
	2097166,
	42);
INSERT INTO GD_CON
	VALUES (2097207,
	2097167,
	2097168,
	0);
INSERT INTO GD_CTXT
	VALUES (2097207,
	0,
	0,
	0,
	0,
	0,
	0,
	1198,
	1469,
	1418,
	1503,
	-210,
	-4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097208,
	2097207,
	1424,
	1456,
	1424,
	1520,
	0);
INSERT INTO GD_GE
	VALUES (2097209,
	2097153,
	2097167,
	42);
INSERT INTO GD_CON
	VALUES (2097209,
	2097168,
	2097169,
	0);
INSERT INTO GD_CTXT
	VALUES (2097209,
	0,
	0,
	0,
	0,
	0,
	0,
	1194,
	1630,
	1457,
	1664,
	-246,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097210,
	2097209,
	1456,
	1616,
	1456,
	1680,
	0);
INSERT INTO GD_GE
	VALUES (2097211,
	2097153,
	2097168,
	42);
INSERT INTO GD_CON
	VALUES (2097211,
	2097169,
	2097155,
	0);
INSERT INTO GD_CTXT
	VALUES (2097211,
	0,
	0,
	0,
	0,
	0,
	0,
	1654,
	1641,
	1841,
	1676,
	22,
	16,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097212,
	2097211,
	1600,
	1728,
	1648,
	1728,
	0);
INSERT INTO GD_LS
	VALUES (2097213,
	2097211,
	1648,
	1728,
	1648,
	1568,
	2097212);
INSERT INTO GD_LS
	VALUES (2097214,
	2097211,
	1648,
	1568,
	1712,
	1568,
	2097213);
INSERT INTO O_OBJ
	VALUES (1048579,
	'No Instances',
	5,
	'NOI',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048607,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048607,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048607,
	1048579,
	0,
	'noi_id',
	'',
	'',
	'noi_id',
	0,
	524294);
INSERT INTO O_REF
	VALUES (1048579,
	1048578,
	0,
	1048579,
	1048578,
	1048580,
	1048579,
	1048608,
	1048587,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048608,
	1048579,
	1048579,
	1048578,
	1);
INSERT INTO O_ATTR
	VALUES (1048608,
	1048579,
	1048607,
	'r2et_id',
	'',
	'r2',
	'et_id',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048579,
	1048578,
	0,
	1048579,
	1048581,
	1048586,
	1048585,
	1048609,
	1048588,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048609,
	1048579,
	1048579,
	1048578,
	1);
INSERT INTO O_ATTR
	VALUES (1048609,
	1048579,
	1048608,
	'r5et_id',
	'',
	'r5',
	'et_id',
	1,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048579);
INSERT INTO O_OIDA
	VALUES (1048607,
	1048579,
	0);
INSERT INTO O_OBJ
	VALUES (1048580,
	'One Instance',
	6,
	'OI',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048610,
	1048580);
INSERT INTO O_BATTR
	VALUES (1048610,
	1048580);
INSERT INTO O_ATTR
	VALUES (1048610,
	1048580,
	0,
	'oi_id',
	'',
	'',
	'oi_id',
	0,
	524294);
INSERT INTO O_REF
	VALUES (1048580,
	1048578,
	0,
	1048579,
	1048579,
	1048582,
	1048581,
	1048611,
	1048589,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048611,
	1048580,
	1048579,
	1048578,
	1);
INSERT INTO O_ATTR
	VALUES (1048611,
	1048580,
	1048610,
	'et_id',
	'',
	'',
	'et_id',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048580);
INSERT INTO O_OIDA
	VALUES (1048610,
	1048580,
	0);
INSERT INTO O_OBJ
	VALUES (1048581,
	'Many Instances',
	7,
	'MI',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048612,
	1048581);
INSERT INTO O_BATTR
	VALUES (1048612,
	1048581);
INSERT INTO O_ATTR
	VALUES (1048612,
	1048581,
	0,
	'mi_id',
	'',
	'',
	'mi_id',
	0,
	524294);
INSERT INTO O_REF
	VALUES (1048581,
	1048578,
	0,
	1048579,
	1048580,
	1048584,
	1048583,
	1048613,
	1048590,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048613,
	1048581,
	1048579,
	1048578,
	1);
INSERT INTO O_ATTR
	VALUES (1048613,
	1048581,
	1048612,
	'et_id',
	'',
	'',
	'et_id',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048581);
INSERT INTO O_OIDA
	VALUES (1048612,
	1048581,
	0);
INSERT INTO O_OBJ
	VALUES (1048582,
	'Any Object',
	8,
	'AO',
	'',
	1048578);
INSERT INTO O_TFR
	VALUES (1048587,
	1048582,
	'i_parm_ret_i',
	'',
	524291,
	0,
	'LOG::LogFailure(message:"Operation AO::i_parm_ret_i should not have been translated.");
return param.i;',
	1);
INSERT INTO O_TPARM
	VALUES (1048585,
	1048587,
	'i',
	524291,
	0);
INSERT INTO O_NBATTR
	VALUES (1048614,
	1048582);
INSERT INTO O_BATTR
	VALUES (1048614,
	1048582);
INSERT INTO O_ATTR
	VALUES (1048614,
	1048582,
	0,
	'ao_id',
	'',
	'',
	'ao_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048615,
	1048582);
INSERT INTO O_BATTR
	VALUES (1048615,
	1048582);
INSERT INTO O_ATTR
	VALUES (1048615,
	1048582,
	1048614,
	'b',
	'',
	'',
	'b',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (1048616,
	1048582);
INSERT INTO O_BATTR
	VALUES (1048616,
	1048582);
INSERT INTO O_ATTR
	VALUES (1048616,
	1048582,
	1048615,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048617,
	1048582);
INSERT INTO O_BATTR
	VALUES (1048617,
	1048582);
INSERT INTO O_ATTR
	VALUES (1048617,
	1048582,
	1048616,
	'r',
	'',
	'',
	'r',
	0,
	524292);
INSERT INTO O_NBATTR
	VALUES (1048618,
	1048582);
INSERT INTO O_BATTR
	VALUES (1048618,
	1048582);
INSERT INTO O_ATTR
	VALUES (1048618,
	1048582,
	1048617,
	's',
	'',
	'',
	's',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048619,
	1048582);
INSERT INTO O_BATTR
	VALUES (1048619,
	1048582);
INSERT INTO O_ATTR
	VALUES (1048619,
	1048582,
	1048618,
	'u',
	'',
	'',
	'u',
	0,
	524294);
INSERT INTO O_ID
	VALUES (0,
	1048582);
INSERT INTO O_OIDA
	VALUES (1048614,
	1048582,
	0);
INSERT INTO O_OBJ
	VALUES (1048583,
	'Expression Test Object B',
	12,
	'ETOB',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048620,
	1048583);
INSERT INTO O_BATTR
	VALUES (1048620,
	1048583);
INSERT INTO O_ATTR
	VALUES (1048620,
	1048583,
	0,
	'etob_id',
	'',
	'',
	'etob_id',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048621,
	1048583);
INSERT INTO O_BATTR
	VALUES (1048621,
	1048583);
INSERT INTO O_ATTR
	VALUES (1048621,
	1048583,
	1048620,
	'btrue',
	'',
	'',
	'btrue',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (1048622,
	1048583);
INSERT INTO O_BATTR
	VALUES (1048622,
	1048583);
INSERT INTO O_ATTR
	VALUES (1048622,
	1048583,
	1048621,
	'bfalse',
	'',
	'',
	'bfalse',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (1048623,
	1048583);
INSERT INTO O_BATTR
	VALUES (1048623,
	1048583);
INSERT INTO O_ATTR
	VALUES (1048623,
	1048583,
	1048622,
	'r',
	'',
	'',
	'r',
	0,
	524292);
INSERT INTO O_NBATTR
	VALUES (1048624,
	1048583);
INSERT INTO O_BATTR
	VALUES (1048624,
	1048583);
INSERT INTO O_ATTR
	VALUES (1048624,
	1048583,
	1048623,
	's',
	'',
	'',
	's',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048625,
	1048583);
INSERT INTO O_BATTR
	VALUES (1048625,
	1048583);
INSERT INTO O_ATTR
	VALUES (1048625,
	1048583,
	1048624,
	'u',
	'',
	'',
	'u',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048626,
	1048583);
INSERT INTO O_BATTR
	VALUES (1048626,
	1048583);
INSERT INTO O_ATTR
	VALUES (1048626,
	1048583,
	1048625,
	'd',
	'',
	'',
	'd',
	0,
	524302);
INSERT INTO O_NBATTR
	VALUES (1048627,
	1048583);
INSERT INTO O_BATTR
	VALUES (1048627,
	1048583);
INSERT INTO O_ATTR
	VALUES (1048627,
	1048583,
	1048626,
	't',
	'',
	'',
	't',
	0,
	524303);
INSERT INTO O_NBATTR
	VALUES (1048628,
	1048583);
INSERT INTO O_BATTR
	VALUES (1048628,
	1048583);
INSERT INTO O_ATTR
	VALUES (1048628,
	1048583,
	1048627,
	'tim',
	'',
	'',
	'tim',
	0,
	524304);
INSERT INTO O_NBATTR
	VALUES (1048629,
	1048583);
INSERT INTO O_BATTR
	VALUES (1048629,
	1048583);
INSERT INTO O_ATTR
	VALUES (1048629,
	1048583,
	1048628,
	'ev',
	'',
	'',
	'ev',
	0,
	524299);
INSERT INTO O_REF
	VALUES (1048583,
	1048578,
	0,
	1048579,
	1048583,
	1048590,
	1048589,
	1048630,
	1048591,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048630,
	1048583,
	1048579,
	1048578,
	1);
INSERT INTO O_ATTR
	VALUES (1048630,
	1048583,
	1048629,
	'et_id',
	'',
	'',
	'et_id',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1048583,
	1048584,
	0,
	1048641,
	1048582,
	1048587,
	1048588,
	1048631,
	1048592,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048631,
	1048583,
	1048641,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048631,
	1048583,
	1048630,
	'etoc_id',
	'',
	'',
	'etoc_id',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1048583,
	1048584,
	0,
	1048642,
	1048582,
	1048587,
	1048588,
	1048632,
	1048593,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048632,
	1048583,
	1048642,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048632,
	1048583,
	1048631,
	'r6btrue',
	'',
	'r6',
	'btrue',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048583,
	1048584,
	0,
	1048643,
	1048582,
	1048587,
	1048588,
	1048633,
	1048594,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048633,
	1048583,
	1048643,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048633,
	1048583,
	1048632,
	'r6bfalse',
	'',
	'r6',
	'bfalse',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048583,
	1048584,
	0,
	1048644,
	1048582,
	1048587,
	1048588,
	1048634,
	1048595,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048634,
	1048583,
	1048644,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048634,
	1048583,
	1048633,
	'r6r',
	'',
	'r6',
	'r',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048583,
	1048584,
	0,
	1048645,
	1048582,
	1048587,
	1048588,
	1048635,
	1048596,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048635,
	1048583,
	1048645,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048635,
	1048583,
	1048634,
	'r6s',
	'',
	'r6',
	's',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048583,
	1048584,
	0,
	1048646,
	1048582,
	1048587,
	1048588,
	1048636,
	1048597,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048636,
	1048583,
	1048646,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048636,
	1048583,
	1048635,
	'r6u',
	'',
	'r6',
	'u',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048583,
	1048584,
	0,
	1048647,
	1048582,
	1048587,
	1048588,
	1048637,
	1048598,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048637,
	1048583,
	1048647,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048637,
	1048583,
	1048636,
	'r6d',
	'',
	'r6',
	'd',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048583,
	1048584,
	0,
	1048648,
	1048582,
	1048587,
	1048588,
	1048638,
	1048599,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048638,
	1048583,
	1048648,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048638,
	1048583,
	1048637,
	'r6t',
	'',
	'r6',
	't',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048583,
	1048584,
	0,
	1048649,
	1048582,
	1048587,
	1048588,
	1048639,
	1048600,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048639,
	1048583,
	1048649,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048639,
	1048583,
	1048638,
	'r6tim',
	'',
	'r6',
	'tim',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048583,
	1048584,
	0,
	1048650,
	1048582,
	1048587,
	1048588,
	1048640,
	1048601,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048640,
	1048583,
	1048650,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048640,
	1048583,
	1048639,
	'r6ev',
	'',
	'r6',
	'ev',
	1,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048583);
INSERT INTO O_OIDA
	VALUES (1048629,
	1048583,
	0);
INSERT INTO O_RTIDA
	VALUES (1048629,
	1048583,
	0,
	1048577,
	1048577);
INSERT INTO O_OIDA
	VALUES (1048625,
	1048583,
	0);
INSERT INTO O_RTIDA
	VALUES (1048625,
	1048583,
	0,
	1048577,
	1048577);
INSERT INTO O_OIDA
	VALUES (1048621,
	1048583,
	0);
INSERT INTO O_RTIDA
	VALUES (1048621,
	1048583,
	0,
	1048577,
	1048577);
INSERT INTO O_OIDA
	VALUES (1048626,
	1048583,
	0);
INSERT INTO O_RTIDA
	VALUES (1048626,
	1048583,
	0,
	1048577,
	1048577);
INSERT INTO O_OIDA
	VALUES (1048628,
	1048583,
	0);
INSERT INTO O_RTIDA
	VALUES (1048628,
	1048583,
	0,
	1048577,
	1048577);
INSERT INTO O_OIDA
	VALUES (1048620,
	1048583,
	0);
INSERT INTO O_RTIDA
	VALUES (1048620,
	1048583,
	0,
	1048577,
	1048577);
INSERT INTO O_OIDA
	VALUES (1048627,
	1048583,
	0);
INSERT INTO O_RTIDA
	VALUES (1048627,
	1048583,
	0,
	1048577,
	1048577);
INSERT INTO O_OIDA
	VALUES (1048623,
	1048583,
	0);
INSERT INTO O_RTIDA
	VALUES (1048623,
	1048583,
	0,
	1048577,
	1048577);
INSERT INTO O_OIDA
	VALUES (1048622,
	1048583,
	0);
INSERT INTO O_RTIDA
	VALUES (1048622,
	1048583,
	0,
	1048577,
	1048577);
INSERT INTO O_OIDA
	VALUES (1048624,
	1048583,
	0);
INSERT INTO O_RTIDA
	VALUES (1048624,
	1048583,
	0,
	1048577,
	1048577);
INSERT INTO O_OBJ
	VALUES (1048584,
	'Expression Test Object C',
	13,
	'ETOC',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048641,
	1048584);
INSERT INTO O_BATTR
	VALUES (1048641,
	1048584);
INSERT INTO O_ATTR
	VALUES (1048641,
	1048584,
	0,
	'etoc_id',
	'',
	'',
	'etoc_id',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048642,
	1048584);
INSERT INTO O_BATTR
	VALUES (1048642,
	1048584);
INSERT INTO O_ATTR
	VALUES (1048642,
	1048584,
	1048641,
	'btrue',
	'',
	'',
	'btrue',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (1048643,
	1048584);
INSERT INTO O_BATTR
	VALUES (1048643,
	1048584);
INSERT INTO O_ATTR
	VALUES (1048643,
	1048584,
	1048642,
	'bfalse',
	'',
	'',
	'bfalse',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (1048644,
	1048584);
INSERT INTO O_BATTR
	VALUES (1048644,
	1048584);
INSERT INTO O_ATTR
	VALUES (1048644,
	1048584,
	1048643,
	'r',
	'',
	'',
	'r',
	0,
	524292);
INSERT INTO O_NBATTR
	VALUES (1048645,
	1048584);
INSERT INTO O_BATTR
	VALUES (1048645,
	1048584);
INSERT INTO O_ATTR
	VALUES (1048645,
	1048584,
	1048644,
	's',
	'',
	'',
	's',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048646,
	1048584);
INSERT INTO O_BATTR
	VALUES (1048646,
	1048584);
INSERT INTO O_ATTR
	VALUES (1048646,
	1048584,
	1048645,
	'u',
	'',
	'',
	'u',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048647,
	1048584);
INSERT INTO O_BATTR
	VALUES (1048647,
	1048584);
INSERT INTO O_ATTR
	VALUES (1048647,
	1048584,
	1048646,
	'd',
	'',
	'',
	'd',
	0,
	524302);
INSERT INTO O_NBATTR
	VALUES (1048648,
	1048584);
INSERT INTO O_BATTR
	VALUES (1048648,
	1048584);
INSERT INTO O_ATTR
	VALUES (1048648,
	1048584,
	1048647,
	't',
	'',
	'',
	't',
	0,
	524303);
INSERT INTO O_NBATTR
	VALUES (1048649,
	1048584);
INSERT INTO O_BATTR
	VALUES (1048649,
	1048584);
INSERT INTO O_ATTR
	VALUES (1048649,
	1048584,
	1048648,
	'tim',
	'',
	'',
	'tim',
	0,
	524304);
INSERT INTO O_NBATTR
	VALUES (1048650,
	1048584);
INSERT INTO O_BATTR
	VALUES (1048650,
	1048584);
INSERT INTO O_ATTR
	VALUES (1048650,
	1048584,
	1048649,
	'ev',
	'',
	'',
	'ev',
	0,
	524299);
INSERT INTO O_ID
	VALUES (0,
	1048584);
INSERT INTO O_OIDA
	VALUES (1048645,
	1048584,
	0);
INSERT INTO O_RTIDA
	VALUES (1048645,
	1048584,
	0,
	1048582,
	1048588);
INSERT INTO O_OIDA
	VALUES (1048650,
	1048584,
	0);
INSERT INTO O_RTIDA
	VALUES (1048650,
	1048584,
	0,
	1048582,
	1048588);
INSERT INTO O_OIDA
	VALUES (1048647,
	1048584,
	0);
INSERT INTO O_RTIDA
	VALUES (1048647,
	1048584,
	0,
	1048582,
	1048588);
INSERT INTO O_OIDA
	VALUES (1048646,
	1048584,
	0);
INSERT INTO O_RTIDA
	VALUES (1048646,
	1048584,
	0,
	1048582,
	1048588);
INSERT INTO O_OIDA
	VALUES (1048648,
	1048584,
	0);
INSERT INTO O_RTIDA
	VALUES (1048648,
	1048584,
	0,
	1048582,
	1048588);
INSERT INTO O_OIDA
	VALUES (1048644,
	1048584,
	0);
INSERT INTO O_RTIDA
	VALUES (1048644,
	1048584,
	0,
	1048582,
	1048588);
INSERT INTO O_OIDA
	VALUES (1048642,
	1048584,
	0);
INSERT INTO O_RTIDA
	VALUES (1048642,
	1048584,
	0,
	1048582,
	1048588);
INSERT INTO O_OIDA
	VALUES (1048649,
	1048584,
	0);
INSERT INTO O_RTIDA
	VALUES (1048649,
	1048584,
	0,
	1048582,
	1048588);
INSERT INTO O_OIDA
	VALUES (1048643,
	1048584,
	0);
INSERT INTO O_RTIDA
	VALUES (1048643,
	1048584,
	0,
	1048582,
	1048588);
INSERT INTO O_OIDA
	VALUES (1048641,
	1048584,
	0);
INSERT INTO O_RTIDA
	VALUES (1048641,
	1048584,
	0,
	1048582,
	1048588);
INSERT INTO O_OBJ
	VALUES (1048585,
	'Boolean Expression Test',
	15,
	'BET',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048651,
	1048585);
INSERT INTO O_BATTR
	VALUES (1048651,
	1048585);
INSERT INTO O_ATTR
	VALUES (1048651,
	1048585,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048652,
	1048585);
INSERT INTO O_BATTR
	VALUES (1048652,
	1048585);
INSERT INTO O_ATTR
	VALUES (1048652,
	1048585,
	1048651,
	'btrue',
	'',
	'',
	'btrue',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (1048653,
	1048585);
INSERT INTO O_BATTR
	VALUES (1048653,
	1048585);
INSERT INTO O_ATTR
	VALUES (1048653,
	1048585,
	1048652,
	'bfalse',
	'',
	'',
	'bfalse',
	0,
	524290);
INSERT INTO O_REF
	VALUES (1048585,
	1048586,
	0,
	1048657,
	1048584,
	1048591,
	1048592,
	1048654,
	1048602,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048654,
	1048585,
	1048657,
	1048586,
	1);
INSERT INTO O_ATTR
	VALUES (1048654,
	1048585,
	1048653,
	'r9bfalse',
	'',
	'r9',
	'bfalse',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048585,
	1048586,
	0,
	1048658,
	1048584,
	1048591,
	1048592,
	1048655,
	1048603,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048655,
	1048585,
	1048658,
	1048586,
	1);
INSERT INTO O_ATTR
	VALUES (1048655,
	1048585,
	1048654,
	'r9btrue',
	'',
	'r9',
	'btrue',
	1,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048656,
	1048585);
INSERT INTO O_BATTR
	VALUES (1048656,
	1048585);
INSERT INTO O_ATTR
	VALUES (1048656,
	1048585,
	1048655,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048585);
INSERT INTO O_OIDA
	VALUES (1048651,
	1048585,
	0);
INSERT INTO O_RTIDA
	VALUES (1048651,
	1048585,
	0,
	1048585,
	1048593);
INSERT INTO SM_ISM
	VALUES (2621445,
	1048585);
INSERT INTO SM_SM
	VALUES (2621445,
	'',
	5);
INSERT INTO SM_MOORE
	VALUES (2621445);
INSERT INTO SM_EVTDI
	VALUES (2621441,
	2621445,
	'tfalse',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (2621442,
	2621445,
	'ttrue',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (19755229,
	2621445,
	'tfalse',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (208107962,
	2621445,
	'tfalse',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (33768249,
	2621445,
	'tfalse',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (3932103,
	2621445,
	'ttrue',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (8652784,
	2621445,
	'ttrue',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (247215845,
	2621445,
	'ttrue',
	'',
	524290);
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
	'Start and test',
	0,
	'',
	'BET1',
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
	3,
	'Start or test',
	0,
	'',
	'BET3',
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
	'Continue and test',
	0,
	'',
	'BET2',
	'');
INSERT INTO SM_LEVT
	VALUES (2621444,
	2621445,
	0);
INSERT INTO SM_SEVT
	VALUES (2621444,
	2621445,
	0);
INSERT INTO SM_EVT
	VALUES (2621444,
	2621445,
	0,
	4,
	'Continue or test',
	0,
	'',
	'BET4',
	'');
INSERT INTO SM_STATE
	VALUES (2621441,
	2621445,
	0,
	'And test 1a',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (2621441,
	2621441,
	2621445,
	0);
INSERT INTO SM_CH
	VALUES (2621441,
	2621442,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621442,
	2621445,
	0);
INSERT INTO SM_SEME
	VALUES (2621441,
	2621443,
	2621445,
	0);
INSERT INTO SM_CH
	VALUES (2621441,
	2621444,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621444,
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
	'LOG::LogInfo(message:"Starting and test") ;

//and operation  (only boolean type)

assign temp1 = false;
assign temp2 = true;

  // local existing    local existing
assign t1 = temp1 and temp1 ;
if ( t1 == false )
  LOG::LogSuccess(message:" and local_existing local_existing ( false )") ;
else
  LOG::LogFailure(message:" and local_existing local_existing ( false )") ;
end if;
 
assign t2 = temp1 and temp2 ;
if ( t2 == false )
  LOG::LogSuccess(message:" and local_existing local_existing ( false )") ;
else
  LOG::LogFailure(message:" and local_existing local_existing ( false )") ;
end if;
 
assign t3 = temp2 and temp1 ;
if ( t3 == false )
  LOG::LogSuccess(message:" and local_existing local_existing ( false )") ;
else
  LOG::LogFailure(message:" and local_existing local_existing ( false )") ;
end if;
 
assign t4 = temp2 and temp2 ;
if ( t4 == true )
  LOG::LogSuccess(message:" and local_existing local_existing ( true )") ;
else
  LOG::LogFailure(message:" and local_existing local_existing ( true )") ;
end if;
 
  // local existing    rcvd_evt
assign t5 = temp1 and rcvd_evt.tfalse ;
if ( t5 == false )
  LOG::LogSuccess(message:" and local_existing rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and local_existing rcvd_evt ( false )") ;
end if;
 
assign t6 = temp1 and rcvd_evt.ttrue ;
if ( t6 == false )
  LOG::LogSuccess(message:" and local_existing rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and local_existing rcvd_evt ( false )") ;
end if;
 
assign t7 = temp2 and rcvd_evt.tfalse ;
if ( t7 == false )
  LOG::LogSuccess(message:" and local_existing rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and local_existing rcvd_evt ( false )") ;
end if;
 
assign t8 = temp2 and rcvd_evt.ttrue ;
if ( t8 == true )
  LOG::LogSuccess(message:" and local_existing rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" and local_existing rcvd_evt ( true )") ;
end if;
 
  // local existing   constant 
assign t9 = temp1 and false ;
if ( t9 == false )
  LOG::LogSuccess(message:" and local_existing constant ( false )") ;
else
  LOG::LogFailure(message:" and local_existing constant ( false )") ;
end if;
 
assign t10 = temp1 and true ;
if ( t10 == false )
  LOG::LogSuccess(message:" and local_existing constant ( false )") ;
else
  LOG::LogFailure(message:" and local_existing constant ( false )") ;
end if;
 
assign t11 = temp2 and false ;
if ( t11 == false )
  LOG::LogSuccess(message:" and local_existing constant ( false )") ;
else
  LOG::LogFailure(message:" and local_existing constant ( false )") ;
end if;
 
assign t12 = temp2 and true ;
if ( t12 == true )
  LOG::LogSuccess(message:" and local_existing constant ( true )") ;
else
  LOG::LogFailure(message:" and local_existing constant ( true )") ;
end if;
 
  // local existing   self.attribute 
assign t13 = temp1 and self.bfalse ;
if ( t13 == false )
  LOG::LogSuccess(message:" and local_existing self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and local_existing self.attribute ( false )") ;
end if;
 
assign t14 = temp1 and self.btrue ;
if ( t14 == false )
  LOG::LogSuccess(message:" and local_existing self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and local_existing self.attribute ( false )") ;
end if;
 
assign t15 = temp2 and self.bfalse ;
if ( t15 == false )
  LOG::LogSuccess(message:" and local_existing self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and local_existing self.attribute ( false )") ;
end if;
 
assign t16 = temp2 and self.btrue ;
if ( t16 == true )
  LOG::LogSuccess(message:" and local_existing self.attribute ( true )") ;
else
  LOG::LogFailure(message:" and local_existing self.attribute ( true )") ;
end if;
 

  // local existing    self.referential attribute
assign t17 = temp1 and self.r9bfalse ;
if ( t17 == false )
  LOG::LogSuccess(message:" and local_existing self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and local_existing self.referential_attribute ( false )") ;
end if;
 
assign t18 = temp1 and self.r9btrue ;
if ( t18 == false )
  LOG::LogSuccess(message:" and local_existing self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and local_existing self.referential_attribute ( false )") ;
end if;
 
assign t19 = temp2 and self.r9bfalse ;
if ( t19 == false )
  LOG::LogSuccess(message:" and local_existing self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and local_existing self.referential_attribute ( false )") ;
end if;
 
assign t20 = temp2 and self.r9btrue ;
if ( t20 == true )
  LOG::LogSuccess(message:" and local_existing self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" and local_existing self.referential_attribute ( true )") ;
end if;
 
  // local existing    other.attribute (saf)
select any saf from instances of OBET;
assign t21 = temp1 and saf.bfalse ;
if ( t21 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(saf) ( false )") ;
end if;
 
assign t22 = temp1 and saf.btrue ;
if ( t22 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(saf) ( false )") ;
end if;
 
assign t23 = temp2 and saf.bfalse ;
if ( t23 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(saf) ( false )") ;
end if;
 
assign t24 = temp2 and saf.btrue ;
if ( t24 == true )
  LOG::LogSuccess(message:" and local_existing other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(saf) ( true )") ;
end if;
 
  // local existing    other.attribute (smf)
select many smfs from instances of OBET;
for each smf in smfs
assign t25 = temp1 and smf.bfalse ;
if ( t25 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(smf) ( false )") ;
end if;
 
assign t26 = temp1 and smf.btrue ;
if ( t26 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(smf) ( false )") ;
end if;
 
assign t27 = temp2 and smf.bfalse ;
if ( t27 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(smf) ( false )") ;
end if;
 
assign t28 = temp2 and smf.btrue ;
if ( t28 == true )
  LOG::LogSuccess(message:" and local_existing other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(smf) ( true )") ;
end if;
 
end for;
  // local existing    other.attribute (sor)
select one sor related by self->OBET[R9];
assign t29 = temp1 and sor.bfalse ;
if ( t29 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(sor) ( false )") ;
end if;
 
assign t30 = temp1 and sor.btrue ;
if ( t30 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(sor) ( false )") ;
end if;
 
assign t31 = temp2 and sor.bfalse ;
if ( t31 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(sor) ( false )") ;
end if;
 
assign t32 = temp2 and sor.btrue ;
if ( t32 == true )
  LOG::LogSuccess(message:" and local_existing other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(sor) ( true )") ;
end if;
 
  // local existing    other.attribute (sar)
select any sar related by self->OBET[R10];
assign t33 = temp1 and sar.bfalse ;
if ( t33 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(sar) ( false )") ;
end if;
 
assign t34 = temp1 and sar.btrue ;
if ( t34 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(sar) ( false )") ;
end if;
 
assign t35 = temp2 and sar.bfalse ;
if ( t35 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(sar) ( false )") ;
end if;
 
assign t36 = temp2 and sar.btrue ;
if ( t36 == true )
  LOG::LogSuccess(message:" and local_existing other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(sar) ( true )") ;
end if;
 
  // local existing    other.attribute (smr)
select many smrs related by self->OBET[R10];
for each smr in smrs
assign t37 = temp1 and smr.bfalse ;
if ( t37 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(smr) ( false )") ;
end if;
 
assign t38 = temp1 and smr.btrue ;
if ( t38 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(smr) ( false )") ;
end if;
 
assign t39 = temp2 and smr.bfalse ;
if ( t39 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(smr) ( false )") ;
end if;
 
assign t40 = temp2 and smr.btrue ;
if ( t40 == true )
  LOG::LogSuccess(message:" and local_existing other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(smr) ( true )") ;
end if;
 
end for;
  // local existing    other.referential attribute (saf)
assign t41 = temp1 and saf.r11bfalse ;
if ( t41 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(saf) ( false )") ;
end if;
 
assign t42 = temp1 and saf.r11btrue ;
if ( t42 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(saf) ( false )") ;
end if;
 
assign t43 = temp2 and saf.r11bfalse ;
if ( t43 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(saf) ( false )") ;
end if;
 
assign t44 = temp2 and saf.r11btrue ;
if ( t44 == true )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(saf) ( true )") ;
end if;
 
  // local existing    other.referential attribute (smf)
for each smf in smfs
assign t45 = temp1 and smf.r11bfalse ;
if ( t45 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(smf) ( false )") ;
end if;
 
assign t46 = temp1 and smf.r11btrue ;
if ( t46 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(smf) ( false )") ;
end if;
 
assign t47 = temp2 and smf.r11bfalse ;
if ( t47 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(smf) ( false )") ;
end if;
 
assign t48 = temp2 and smf.r11btrue ;
if ( t48 == true )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // local existing    other.referential attribute (sor)
assign t49 = temp1 and sor.r11bfalse ;
if ( t49 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(sor) ( false )") ;
end if;
 
assign t50 = temp1 and sor.r11btrue ;
if ( t50 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(sor) ( false )") ;
end if;
 
assign t51 = temp2 and sor.r11bfalse ;
if ( t51 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(sor) ( false )") ;
end if;
 
assign t52 = temp2 and sor.r11btrue ;
if ( t52 == true )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(sor) ( true )") ;
end if;
 
  // local existing    other.referential attribute (sar)
assign t53 = temp1 and sar.r11bfalse ;
if ( t53 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(sar) ( false )") ;
end if;
 
assign t54 = temp1 and sar.r11btrue ;
if ( t54 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(sar) ( false )") ;
end if;
 
assign t55 = temp2 and sar.r11bfalse ;
if ( t55 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(sar) ( false )") ;
end if;
 
assign t56 = temp2 and sar.r11btrue ;
if ( t56 == true )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(sar) ( true )") ;
end if;
 
  // local existing    other.referential attribute (smr)
for each smr in smrs
assign t57 = temp1 and smr.r11bfalse ;
if ( t57 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(smr) ( false )") ;
end if;
 
assign t58 = temp1 and smr.r11btrue ;
if ( t58 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(smr) ( false )") ;
end if;
 
assign t59 = temp2 and smr.r11bfalse ;
if ( t59 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(smr) ( false )") ;
end if;
 
assign t60 = temp2 and smr.r11btrue ;
if ( t60 == true )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(smr) ( true )") ;
end if;
 
end for;
  // rcvd_evt    local existing
assign t61 = rcvd_evt.tfalse and temp1 ;
if ( t61 == false )
  LOG::LogSuccess(message:" and rcvd_evt local_existing ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt local_existing ( false )") ;
end if;
 
assign t62 = rcvd_evt.ttrue and temp1 ;
if ( t62 == false )
  LOG::LogSuccess(message:" and rcvd_evt local_existing ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt local_existing ( false )") ;
end if;
 
assign t63 = rcvd_evt.tfalse and temp2 ;
if ( t63 == false )
  LOG::LogSuccess(message:" and rcvd_evt local_existing ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt local_existing ( false )") ;
end if;
 
assign t64 = rcvd_evt.ttrue and temp2 ;
if ( t64 == true )
  LOG::LogSuccess(message:" and rcvd_evt local_existing ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt local_existing ( true )") ;
end if;
 
  // rcvd_evt    rcvd_evt
assign t65 = rcvd_evt.tfalse and rcvd_evt.tfalse ;
if ( t65 == false )
  LOG::LogSuccess(message:" and rcvd_evt rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt rcvd_evt ( false )") ;
end if;
 
assign t66 = rcvd_evt.ttrue and rcvd_evt.tfalse ;
if ( t66 == false )
  LOG::LogSuccess(message:" and rcvd_evt rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt rcvd_evt ( false )") ;
end if;
 
assign t67 = rcvd_evt.tfalse and rcvd_evt.ttrue ;
if ( t67 == false )
  LOG::LogSuccess(message:" and rcvd_evt rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt rcvd_evt ( false )") ;
end if;
 
assign t68 = rcvd_evt.ttrue and rcvd_evt.ttrue ;
if ( t68 == true )
  LOG::LogSuccess(message:" and rcvd_evt rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt rcvd_evt ( true )") ;
end if;
 
  // rcvd_evt    constant
assign t69 = rcvd_evt.tfalse and false ;
if ( t69 == false )
  LOG::LogSuccess(message:" and rcvd_evt constant ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt constant ( false )") ;
end if;
 
assign t70 = rcvd_evt.ttrue and false ;
if ( t70 == false )
  LOG::LogSuccess(message:" and rcvd_evt constant ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt constant ( false )") ;
end if;
 
assign t71 = rcvd_evt.tfalse and true ;
if ( t71 == false )
  LOG::LogSuccess(message:" and rcvd_evt constant ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt constant ( false )") ;
end if;
 
assign t72 = rcvd_evt.ttrue and true ;
if ( t72 == true )
  LOG::LogSuccess(message:" and rcvd_evt constant ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt constant ( true )") ;
end if;
 
  // rcvd_evt    self.attribute
assign t73 = rcvd_evt.tfalse and self.bfalse ;
if ( t73 == false )
  LOG::LogSuccess(message:" and rcvd_evt self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt self.attribute ( false )") ;
end if;
 
assign t74 = rcvd_evt.ttrue and self.bfalse ;
if ( t74 == false )
  LOG::LogSuccess(message:" and rcvd_evt self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt self.attribute ( false )") ;
end if;
 
assign t75 = rcvd_evt.tfalse and self.btrue ;
if ( t75 == false )
  LOG::LogSuccess(message:" and rcvd_evt self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt self.attribute ( false )") ;
end if;
 
assign t76 = rcvd_evt.ttrue and self.btrue ;
if ( t76 == true )
  LOG::LogSuccess(message:" and rcvd_evt self.attribute ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt self.attribute ( true )") ;
end if;
 
  // rcvd_evt    self.referential attribute
assign t77 = rcvd_evt.tfalse and self.r9bfalse ;
if ( t77 == false )
  LOG::LogSuccess(message:" and rcvd_evt self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt self.referential_attribute ( false )") ;
end if;
 
assign t78 = rcvd_evt.ttrue and self.r9bfalse ;
if ( t78 == false )
  LOG::LogSuccess(message:" and rcvd_evt self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt self.referential_attribute ( false )") ;
end if;
 
assign t79 = rcvd_evt.tfalse and self.r9btrue ;
if ( t79 == false )
  LOG::LogSuccess(message:" and rcvd_evt self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt self.referential_attribute ( false )") ;
end if;
 
assign t80 = rcvd_evt.ttrue and self.r9btrue ;
if ( t80 == true )
  LOG::LogSuccess(message:" and rcvd_evt self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt self.referential_attribute ( true )") ;
end if;
 
  // rcvd_evt    other.attribute (saf)
assign t81 = rcvd_evt.tfalse and saf.bfalse ;
if ( t81 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(saf) ( false )") ;
end if;
 
assign t82 = rcvd_evt.tfalse and saf.btrue ;
if ( t82 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(saf) ( false )") ;
end if;
 
assign t83 = rcvd_evt.ttrue and saf.bfalse ;
if ( t83 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(saf) ( false )") ;
end if;
 
assign t84 = rcvd_evt.ttrue and saf.btrue ;
if ( t84 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(saf) ( true )") ;
end if;
 
  // rcvd_evt    other.attribute (smf)
for each smf in smfs
assign t85 = rcvd_evt.tfalse and smf.bfalse ;
if ( t85 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(smf) ( false )") ;
end if;
 
assign t86 = rcvd_evt.tfalse and smf.btrue ;
if ( t86 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(smf) ( false )") ;
end if;
 
assign t87 = rcvd_evt.ttrue and smf.bfalse ;
if ( t87 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(smf) ( false )") ;
end if;
 
assign t88 = rcvd_evt.ttrue and smf.btrue ;
if ( t88 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(smf) ( true )") ;
end if;
 
end for;
  // rcvd_evt    other.attribute (sor)
assign t89 = rcvd_evt.tfalse and sor.bfalse ;
if ( t89 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(sor) ( false )") ;
end if;
 
assign t90 = rcvd_evt.tfalse and sor.btrue ;
if ( t90 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(sor) ( false )") ;
end if;
 
assign t91 = rcvd_evt.ttrue and sor.bfalse ;
if ( t91 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(sor) ( false )") ;
end if;
 
assign t92 = rcvd_evt.ttrue and sor.btrue ;
if ( t92 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(sor) ( true )") ;
end if;
 
  // rcvd_evt    other.attribute (sar)
assign t93 = rcvd_evt.tfalse and sar.bfalse ;
if ( t93 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(sar) ( false )") ;
end if;
 
assign t94 = rcvd_evt.tfalse and sar.btrue ;
if ( t94 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(sar) ( false )") ;
end if;
 
assign t95 = rcvd_evt.ttrue and sar.bfalse ;
if ( t95 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(sar) ( false )") ;
end if;
 
assign t96 = rcvd_evt.ttrue and sar.btrue ;
if ( t96 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(sar) ( true )") ;
end if;
 
  // rcvd_evt    other.attribute (smr)
for each smr in smrs
assign t97 = rcvd_evt.tfalse and smr.bfalse ;
if ( t97 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(smr) ( false )") ;
end if;
 
assign t98 = rcvd_evt.tfalse and smr.btrue ;
if ( t98 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(smr) ( false )") ;
end if;
 
assign t99 = rcvd_evt.ttrue and smr.bfalse ;
if ( t99 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(smr) ( false )") ;
end if;
 
assign t100 = rcvd_evt.ttrue and smr.btrue ;
if ( t100 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(smr) ( true )") ;
end if;
 
end for;
  // rcvd_evt    other.referential attribute (saf)
assign t101 = rcvd_evt.tfalse and saf.r11bfalse ;
if ( t101 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(saf) ( false )") ;
end if;
 
assign t102 = rcvd_evt.tfalse and saf.r11btrue ;
if ( t102 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(saf) ( false )") ;
end if;
 
assign t103 = rcvd_evt.ttrue and saf.r11bfalse ;
if ( t103 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(saf) ( false )") ;
end if;
 
assign t104 = rcvd_evt.ttrue and saf.r11btrue ;
if ( t104 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(saf) ( true )") ;
end if;
 
  // rcvd_evt    other.referential attribute (smf)
for each smf in smfs
assign t105 = rcvd_evt.tfalse and smf.r11bfalse ;
if ( t105 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(smf) ( false )") ;
end if;
 
assign t106 = rcvd_evt.tfalse and smf.r11btrue ;
if ( t106 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(smf) ( false )") ;
end if;
 
assign t107 = rcvd_evt.ttrue and smf.r11bfalse ;
if ( t107 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(smf) ( false )") ;
end if;
 
assign t108 = rcvd_evt.ttrue and smf.r11btrue ;
if ( t108 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // rcvd_evt    other.referential attribute (sor)
assign t109 = rcvd_evt.tfalse and sor.r11bfalse ;
if ( t109 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(sor) ( false )") ;
end if;
 
assign t110 = rcvd_evt.tfalse and sor.r11btrue ;
if ( t110 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(sor) ( false )") ;
end if;
 
assign t111 = rcvd_evt.ttrue and sor.r11bfalse ;
if ( t111 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(sor) ( false )") ;
end if;
 
assign t112 = rcvd_evt.ttrue and sor.r11btrue ;
if ( t112 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(sor) ( true )") ;
end if;
 
  // rcvd_evt    other.referential attribute (sar)
assign t113 = rcvd_evt.tfalse and sar.r11bfalse ;
if ( t113 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(sar) ( false )") ;
end if;
 
assign t114 = rcvd_evt.tfalse and sar.r11btrue ;
if ( t114 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(sar) ( false )") ;
end if;
 
assign t115 = rcvd_evt.ttrue and sar.r11bfalse ;
if ( t115 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(sar) ( false )") ;
end if;
 
assign t116 = rcvd_evt.ttrue and sar.r11btrue ;
if ( t116 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(sar) ( true )") ;
end if;
 
  // rcvd_evt    other.referential attribute (smr)
for each smr in smrs
assign t117 = rcvd_evt.tfalse and smr.r11bfalse ;
if ( t117 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(smr) ( false )") ;
end if;
 
assign t118 = rcvd_evt.tfalse and smr.r11btrue ;
if ( t118 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(smr) ( false )") ;
end if;
 
assign t119 = rcvd_evt.ttrue and smr.r11bfalse ;
if ( t119 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(smr) ( false )") ;
end if;
 
assign t120 = rcvd_evt.ttrue and smr.r11btrue ;
if ( t120 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(smr) ( true )") ;
end if;
 
end for;

 
//generate event to continue test
Generate BET2:''Continue and test''( ttrue: true, tfalse: false ) to self; 

',
	'');
INSERT INTO SM_STATE
	VALUES (2621442,
	2621445,
	0,
	'And test 2',
	3,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_SEME
	VALUES (2621442,
	2621442,
	2621445,
	0);
INSERT INTO SM_CH
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
	2621444,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621442,
	2621444,
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
	'//and operation  (only boolean type)

assign temp1 = false;
assign temp2 = true;

select any saf from instances of OBET;
select many smfs from instances of OBET;
select one sor related by self->OBET[R9];
select any sar related by self->OBET[R10];
select many smrs related by self->OBET[R10];

  // other.attribute (saf)    local existing
assign t301 = saf.bfalse and temp1 ;
if ( t301 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) local_existing ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) local_existing ( false )") ;
end if;
 
assign t302 = saf.btrue and temp1 ;
if ( t302 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) local_existing ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) local_existing ( false )") ;
end if;
 
assign t303 = saf.bfalse and temp2 ;
if ( t303 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) local_existing ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) local_existing ( false )") ;
end if;
 
assign t304 = saf.btrue and temp2 ;
if ( t304 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) local_existing ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) local_existing ( true )") ;
end if;
 
  // other.attribute(saf)   rcvd_evt 
assign t305 = saf.bfalse and rcvd_evt.tfalse ;
if ( t305 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) rcvd_evt ( false )") ;
end if;
 
assign t306 = saf.btrue and rcvd_evt.tfalse ;
if ( t306 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) rcvd_evt ( false )") ;
end if;
 
assign t307 = saf.bfalse and rcvd_evt.ttrue ;
if ( t307 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) rcvd_evt ( false )") ;
end if;
 
assign t308 = saf.btrue and rcvd_evt.ttrue ;
if ( t308 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) rcvd_evt ( true )") ;
end if;
 
  // other.attribute(saf)    constant
assign t309 = saf.bfalse and false ;
if ( t309 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) constant ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) constant ( false )") ;
end if;
 
assign t310 = saf.btrue and false ;
if ( t310 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) constant ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) constant ( false )") ;
end if;
 
assign t311 = saf.bfalse and true ;
if ( t311 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) constant ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) constant ( false )") ;
end if;
 
assign t312 = saf.btrue and true ;
if ( t312 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) constant ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) constant ( true )") ;
end if;
 
  // other.attribute(saf)    self.attribute
assign t313 = saf.bfalse and self.bfalse ;
if ( t313 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) self.attribute ( false )") ;
end if;
 
assign t314 = saf.btrue and self.bfalse ;
if ( t314 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) self.attribute ( false )") ;
end if;
 
assign t315 = saf.bfalse and self.btrue ;
if ( t315 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) self.attribute ( false )") ;
end if;
 
assign t316 = saf.btrue and self.btrue ;
if ( t316 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) self.attribute ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) self.attribute ( true )") ;
end if;
 
  // other.attribute(saf)    self.referential attribute
assign t317 = saf.bfalse and self.r9bfalse ;
if ( t317 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) self.referential_attribute ( false )") ;
end if;
 
assign t318 = saf.btrue and self.r9bfalse ;
if ( t318 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) self.referential_attribute ( false )") ;
end if;
 
assign t319 = saf.bfalse and self.r9btrue ;
if ( t319 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) self.referential_attribute ( false )") ;
end if;
 
assign t320 = saf.btrue and self.r9btrue ;
if ( t320 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) self.referential_attribute ( true )") ;
end if;
 
  // other.attribute(saf)    other.attribute (saf)
assign t321 = saf.bfalse and saf.bfalse ;
if ( t321 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(saf) ( false )") ;
end if;
 
assign t322 = saf.bfalse and saf.btrue ;
if ( t322 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(saf) ( false )") ;
end if;
 
assign t323 = saf.btrue and saf.bfalse ;
if ( t323 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(saf) ( false )") ;
end if;
 
assign t324 = saf.btrue and saf.btrue ;
if ( t324 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(saf) ( true )") ;
end if;
 
  // other.attribute(saf)    other.attribute (smf)
for each smf in smfs
assign t325 = saf.bfalse and smf.bfalse ;
if ( t325 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(smf) ( false )") ;
end if;
 
assign t326 = saf.bfalse and smf.btrue ;
if ( t326 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(smf) ( false )") ;
end if;
 
assign t327 = saf.btrue and smf.bfalse ;
if ( t327 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(smf) ( false )") ;
end if;
 
assign t328 = saf.btrue and smf.btrue ;
if ( t328 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(smf) ( true )") ;
end if;
 
end for;
  // other.attribute(saf)    other.attribute (sor)
assign t329 = saf.bfalse and sor.bfalse ;
if ( t329 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(sor) ( false )") ;
end if;
 
assign t330 = saf.bfalse and sor.btrue ;
if ( t330 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(sor) ( false )") ;
end if;
 
assign t331 = saf.btrue and sor.bfalse ;
if ( t331 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(sor) ( false )") ;
end if;
 
assign t332 = saf.btrue and sor.btrue ;
if ( t332 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(sor) ( true )") ;
end if;
 
  // other.attribute(saf)    other.attribute (sar)
assign t333 = saf.bfalse and sar.bfalse ;
if ( t333 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(sar) ( false )") ;
end if;
 
assign t334 = saf.bfalse and sar.btrue ;
if ( t334 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(sar) ( false )") ;
end if;
 
assign t335 = saf.btrue and sar.bfalse ;
if ( t335 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(sar) ( false )") ;
end if;
 
assign t336 = saf.btrue and sar.btrue ;
if ( t336 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(sar) ( true )") ;
end if;
 
  // other.attribute(saf)    other.attribute (smr)
for each smr in smrs
assign t337 = saf.bfalse and smr.bfalse ;
if ( t337 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(smr) ( false )") ;
end if;
 
assign t338 = saf.bfalse and smr.btrue ;
if ( t338 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(smr) ( false )") ;
end if;
 
assign t339 = saf.btrue and smr.bfalse ;
if ( t339 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(smr) ( false )") ;
end if;
 
assign t340 = saf.btrue and smr.btrue ;
if ( t340 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(smr) ( true )") ;
end if;
 
end for;
  // other.attribute(saf)    other.referential attribute (saf)
assign t341 = saf.bfalse and saf.r11bfalse ;
if ( t341 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(saf) ( false )") ;
end if;
 
assign t342 = saf.bfalse and saf.r11btrue ;
if ( t342 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(saf) ( false )") ;
end if;
 
assign t343 = saf.btrue and saf.r11bfalse ;
if ( t343 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(saf) ( false )") ;
end if;
 
assign t344 = saf.btrue and saf.r11btrue ;
if ( t344 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(saf) ( true )") ;
end if;
 
  // other.attribute(saf)    other.referential attribute (smf)
for each smf in smfs
assign t345 = saf.bfalse and smf.r11bfalse ;
if ( t345 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(smf) ( false )") ;
end if;
 
assign t346 = saf.bfalse and smf.r11btrue ;
if ( t346 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(smf) ( false )") ;
end if;
 
assign t347 = saf.btrue and smf.r11bfalse ;
if ( t347 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(smf) ( false )") ;
end if;
 
assign t348 = saf.btrue and smf.r11btrue ;
if ( t348 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // other.attribute(saf)    other.referential attribute (sor)
assign t349 = saf.bfalse and sor.r11bfalse ;
if ( t349 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(sor) ( false )") ;
end if;
 
assign t350 = saf.bfalse and sor.r11btrue ;
if ( t350 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(sor) ( false )") ;
end if;
 
assign t351 = saf.btrue and sor.r11bfalse ;
if ( t351 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(sor) ( false )") ;
end if;
 
assign t352 = saf.btrue and sor.r11btrue ;
if ( t352 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(sor) ( true )") ;
end if;
 
  // other.attribute(saf)    other.referential attribute (sar)
assign t353 = saf.bfalse and sar.r11bfalse ;
if ( t353 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(sar) ( false )") ;
end if;
 
assign t354 = saf.bfalse and sar.r11btrue ;
if ( t354 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(sar) ( false )") ;
end if;
 
assign t355 = saf.btrue and sar.r11bfalse ;
if ( t355 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(sar) ( false )") ;
end if;
 
assign t356 = saf.btrue and sar.r11btrue ;
if ( t356 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(sar) ( true )") ;
end if;
 
  // other.attribute(saf)    other.referential attribute (smr)
for each smr in smrs
assign t357 = saf.bfalse and smr.r11bfalse ;
if ( t357 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(smr) ( false )") ;
end if;
 
assign t358 = saf.bfalse and smr.r11btrue ;
if ( t358 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(smr) ( false )") ;
end if;
 
assign t359 = saf.btrue and smr.r11bfalse ;
if ( t359 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(smr) ( false )") ;
end if;
 
assign t360 = saf.btrue and smr.r11btrue ;
if ( t360 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(smr) ( true )") ;
end if;
 
end for;
  // other.attribute (smf)    local existing
  // other.attribute (smf)    constant
  // other.attribute (smf)    constant
  // other.attribute (smf)    self.attribute
  // other.attribute (smf)    self.referential attribute
  // other.attribute (smf)    other.attribute (saf)
  // other.attribute (smf)    other.attribute (smf)
  // other.attribute (smf)    other.attribute (sor)
  // other.attribute (smf)    other.attribute (sar)
  // other.attribute (smf)    other.attribute (smr)
  // other.attribute (smf)    other.referential attribute (saf)
  // other.attribute (smf)    other.referential attribute (smf)
  // other.attribute (smf)    other.referential attribute (sor)
  // other.attribute (smf)    other.referential attribute (sar)
  // other.attribute (smf)    other.referential attribute (smr)
  // other.attribute (sor)    local existing
  // other.attribute (sor)    constant
  // other.attribute (sor)    constant
  // other.attribute (sor)    self.attribute
  // other.attribute (sor)    self.referential attribute
  // other.attribute (sor)    other.attribute (saf)
  // other.attribute (sor)    other.attribute (smf)
  // other.attribute (sor)    other.attribute (sor)
  // other.attribute (sor)    other.attribute (sar)
  // other.attribute (sor)    other.attribute (smr)
  // other.attribute (sor)    other.referential attribute (saf)
  // other.attribute (sor)    other.referential attribute (smf)
  // other.attribute (sor)    other.referential attribute (sor)
  // other.attribute (sor)    other.referential attribute (sar)
  // other.attribute (sor)    other.referential attribute (smr)
  // other.attribute (sar)    local existing
  // other.attribute (sar)    constant
  // other.attribute (sar)    constant
  // other.attribute (sar)    self.attribute
  // other.attribute (sar)    self.referential attribute
  // other.attribute (sar)    other.attribute (saf)
  // other.attribute (sar)    other.attribute (smf)
  // other.attribute (sar)    other.attribute (sor)
  // other.attribute (sar)    other.attribute (sar)
  // other.attribute (sar)    other.attribute (smr)
  // other.attribute (sar)    other.referential attribute (saf)
  // other.attribute (sar)    other.referential attribute (smf)
  // other.attribute (sar)    other.referential attribute (sor)
  // other.attribute (sar)    other.referential attribute (sar)
  // other.attribute (sar)    other.referential attribute (smr)
  // other.attribute (smr)    local existing
  // other.attribute (smr)    constant
  // other.attribute (smr)    constant
  // other.attribute (smr)    self.attribute
  // other.attribute (smr)    self.referential attribute
  // other.attribute (smr)    other.attribute (saf)
  // other.attribute (smr)    other.attribute (smf)
  // other.attribute (smr)    other.attribute (sor)
  // other.attribute (smr)    other.attribute (sar)
  // other.attribute (smr)    other.attribute (smr)
  // other.attribute (smr)    other.referential attribute (saf)
  // other.attribute (smr)    other.referential attribute (smf)
  // other.attribute (smr)    other.referential attribute (sor)
  // other.attribute (smr)    other.referential attribute (sar)
  // other.attribute (smr)    other.referential attribute (smr)
  // other.referential attribute (saf)    local existing
assign t601 = saf.r11bfalse and temp1 ;
if ( t601 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) local_existing ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) local_existing ( false )") ;
end if;
 
assign t602 = saf.r11btrue and temp1 ;
if ( t602 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) local_existing ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) local_existing ( false )") ;
end if;
 
assign t603 = saf.r11bfalse and temp2 ;
if ( t603 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) local_existing ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) local_existing ( false )") ;
end if;
 
assign t604 = saf.r11btrue and temp2 ;
if ( t604 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) local_existing ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) local_existing ( true )") ;
end if;
 
  // other.referential_attribute(saf)   rcvd_evt 
assign t605 = saf.r11bfalse and rcvd_evt.tfalse ;
if ( t605 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) rcvd_evt ( false )") ;
end if;
 
assign t606 = saf.r11btrue and rcvd_evt.tfalse ;
if ( t606 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) rcvd_evt ( false )") ;
end if;
 
assign t607 = saf.r11bfalse and rcvd_evt.ttrue ;
if ( t607 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) rcvd_evt ( false )") ;
end if;
 
assign t608 = saf.r11btrue and rcvd_evt.ttrue ;
if ( t608 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) rcvd_evt ( true )") ;
end if;
 
  // other.referential_attribute(saf)    constant
assign t609 = saf.r11bfalse and false ;
if ( t609 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) constant ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) constant ( false )") ;
end if;
 
assign t610 = saf.r11btrue and false ;
if ( t610 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) constant ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) constant ( false )") ;
end if;
 
assign t611 = saf.r11bfalse and true ;
if ( t611 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) constant ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) constant ( false )") ;
end if;
 
assign t612 = saf.r11btrue and true ;
if ( t612 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) constant ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) constant ( true )") ;
end if;
 
  // other.referential_attribute(saf)    self.attribute
assign t613 = saf.r11bfalse and self.bfalse ;
if ( t613 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) self.attribute ( false )") ;
end if;
 
assign t614 = saf.r11btrue and self.bfalse ;
if ( t614 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) self.attribute ( false )") ;
end if;
 
assign t615 = saf.r11bfalse and self.btrue ;
if ( t615 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) self.attribute ( false )") ;
end if;
 
assign t616 = saf.r11btrue and self.btrue ;
if ( t616 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) self.attribute ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) self.attribute ( true )") ;
end if;
 
  // other.referential_attribute(saf)    self.referential attribute
assign t617 = saf.r11bfalse and self.r9bfalse ;
if ( t617 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) self.referential_attribute ( false )") ;
end if;
 
assign t618 = saf.r11btrue and self.r9bfalse ;
if ( t618 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) self.referential_attribute ( false )") ;
end if;
 
assign t619 = saf.r11bfalse and self.r9btrue ;
if ( t619 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) self.referential_attribute ( false )") ;
end if;
 
assign t620 = saf.r11btrue and self.r9btrue ;
if ( t620 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) self.referential_attribute ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.attribute (saf)
assign t621 = saf.r11bfalse and saf.bfalse ;
if ( t621 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(saf) ( false )") ;
end if;
 
assign t622 = saf.r11bfalse and saf.btrue ;
if ( t622 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(saf) ( false )") ;
end if;
 
assign t623 = saf.r11btrue and saf.bfalse ;
if ( t623 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(saf) ( false )") ;
end if;
 
assign t624 = saf.r11btrue and saf.btrue ;
if ( t624 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(saf) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.attribute (smf)
for each smf in smfs
assign t625 = saf.r11bfalse and smf.bfalse ;
if ( t625 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(smf) ( false )") ;
end if;
 
assign t626 = saf.r11bfalse and smf.btrue ;
if ( t626 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(smf) ( false )") ;
end if;
 
assign t627 = saf.r11btrue and smf.bfalse ;
if ( t627 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(smf) ( false )") ;
end if;
 
assign t628 = saf.r11btrue and smf.btrue ;
if ( t628 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(smf) ( true )") ;
end if;
 
end for;
  // other.referential_attribute(saf)    other.attribute (sor)
assign t629 = saf.r11bfalse and sor.bfalse ;
if ( t629 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(sor) ( false )") ;
end if;
 
assign t630 = saf.r11bfalse and sor.btrue ;
if ( t630 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(sor) ( false )") ;
end if;
 
assign t631 = saf.r11btrue and sor.bfalse ;
if ( t631 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(sor) ( false )") ;
end if;
 
assign t632 = saf.r11btrue and sor.btrue ;
if ( t632 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(sor) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.attribute (sar)
assign t633 = saf.r11bfalse and sar.bfalse ;
if ( t633 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(sar) ( false )") ;
end if;
 
assign t634 = saf.r11bfalse and sar.btrue ;
if ( t634 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(sar) ( false )") ;
end if;
 
assign t635 = saf.r11btrue and sar.bfalse ;
if ( t635 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(sar) ( false )") ;
end if;
 
assign t636 = saf.r11btrue and sar.btrue ;
if ( t636 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(sar) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.attribute (smr)
for each smr in smrs
assign t637 = saf.r11bfalse and smr.bfalse ;
if ( t637 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(smr) ( false )") ;
end if;
 
assign t638 = saf.r11bfalse and smr.btrue ;
if ( t638 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(smr) ( false )") ;
end if;
 
assign t639 = saf.r11btrue and smr.bfalse ;
if ( t639 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(smr) ( false )") ;
end if;
 
assign t640 = saf.r11btrue and smr.btrue ;
if ( t640 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(smr) ( true )") ;
end if;
 
end for;
  // other.referential_attribute(saf)    other.referential attribute (saf)
assign t641 = saf.r11bfalse and saf.r11bfalse ;
if ( t641 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(saf) ( false )") ;
end if;
 
assign t642 = saf.r11bfalse and saf.r11btrue ;
if ( t642 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(saf) ( false )") ;
end if;
 
assign t643 = saf.r11btrue and saf.r11bfalse ;
if ( t643 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(saf) ( false )") ;
end if;
 
assign t644 = saf.r11btrue and saf.r11btrue ;
if ( t644 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(saf) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.referential attribute (smf)
for each smf in smfs
assign t645 = saf.r11bfalse and smf.r11bfalse ;
if ( t645 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(smf) ( false )") ;
end if;
 
assign t646 = saf.r11bfalse and smf.r11btrue ;
if ( t646 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(smf) ( false )") ;
end if;
 
assign t647 = saf.r11btrue and smf.r11bfalse ;
if ( t647 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(smf) ( false )") ;
end if;
 
assign t648 = saf.r11btrue and smf.r11btrue ;
if ( t648 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // other.referential_attribute(saf)    other.referential attribute (sor)
assign t649 = saf.r11bfalse and sor.r11bfalse ;
if ( t649 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(sor) ( false )") ;
end if;
 
assign t650 = saf.r11bfalse and sor.r11btrue ;
if ( t650 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(sor) ( false )") ;
end if;
 
assign t651 = saf.r11btrue and sor.r11bfalse ;
if ( t651 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(sor) ( false )") ;
end if;
 
assign t652 = saf.r11btrue and sor.r11btrue ;
if ( t652 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(sor) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.referential attribute (sar)
assign t653 = saf.r11bfalse and sar.r11bfalse ;
if ( t653 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(sar) ( false )") ;
end if;
 
assign t654 = saf.r11bfalse and sar.r11btrue ;
if ( t654 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(sar) ( false )") ;
end if;
 
assign t655 = saf.r11btrue and sar.r11bfalse ;
if ( t655 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(sar) ( false )") ;
end if;
 
assign t656 = saf.r11btrue and sar.r11btrue ;
if ( t656 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(sar) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.referential attribute (smr)
for each smr in smrs
assign t657 = saf.r11bfalse and smr.r11bfalse ;
if ( t657 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(smr) ( false )") ;
end if;
 
assign t658 = saf.r11bfalse and smr.r11btrue ;
if ( t658 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(smr) ( false )") ;
end if;
 
assign t659 = saf.r11btrue and smr.r11bfalse ;
if ( t659 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(smr) ( false )") ;
end if;
 
assign t660 = saf.r11btrue and smr.r11btrue ;
if ( t660 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(smr) ( true )") ;
end if;
 
end for;
  // other.referential attribute (saf)    constant
  // other.referential attribute (saf)    constant
  // other.referential attribute (saf)    self.attribute
  // other.referential attribute (saf)    self.referential attribute
  // other.referential attribute (saf)    other.attribute (saf)
  // other.referential attribute (saf)    other.attribute (smf)
  // other.referential attribute (saf)    other.attribute (sor)
  // other.referential attribute (saf)    other.attribute (sar)
  // other.referential attribute (saf)    other.attribute (smr)
  // other.referential attribute (saf)    other.referential attribute (saf)
  // other.referential attribute (saf)    other.referential attribute (smf)
  // other.referential attribute (saf)    other.referential attribute (sor)
  // other.referential attribute (saf)    other.referential attribute (sar)
  // other.referential attribute (saf)    other.referential attribute (smr)
  // other.referential attribute (smf)    local existing
  // other.referential attribute (smf)    constant
  // other.referential attribute (smf)    constant
  // other.referential attribute (smf)    self.attribute
  // other.referential attribute (smf)    self.referential attribute
  // other.referential attribute (smf)    other.attribute (saf)
  // other.referential attribute (smf)    other.attribute (smf)
  // other.referential attribute (smf)    other.attribute (sor)
  // other.referential attribute (smf)    other.attribute (sar)
  // other.referential attribute (smf)    other.attribute (smr)
  // other.referential attribute (smf)    other.referential attribute (saf)
  // other.referential attribute (smf)    other.referential attribute (smf)
  // other.referential attribute (smf)    other.referential attribute (sor)
  // other.referential attribute (smf)    other.referential attribute (sar)
  // other.referential attribute (smf)    other.referential attribute (smr)
  // other.referential attribute (sor)    local existing
  // other.referential attribute (sor)    constant
  // other.referential attribute (sor)    constant
  // other.referential attribute (sor)    self.attribute
  // other.referential attribute (sor)    self.referential attribute
  // other.referential attribute (sor)    other.attribute (saf)
  // other.referential attribute (sor)    other.attribute (smf)
  // other.referential attribute (sor)    other.attribute (sor)
  // other.referential attribute (sor)    other.attribute (sar)
  // other.referential attribute (sor)    other.attribute (smr)
  // other.referential attribute (sor)    other.referential attribute (saf)
  // other.referential attribute (sor)    other.referential attribute (smf)
  // other.referential attribute (sor)    other.referential attribute (sor)
  // other.referential attribute (sor)    other.referential attribute (sar)
  // other.referential attribute (sor)    other.referential attribute (smr)
  // other.referential attribute (sar)    local existing
  // other.referential attribute (sar)    constant
  // other.referential attribute (sar)    constant
  // other.referential attribute (sar)    self.attribute
  // other.referential attribute (sar)    self.referential attribute
  // other.referential attribute (sar)    other.attribute (saf)
  // other.referential attribute (sar)    other.attribute (smf)
  // other.referential attribute (sar)    other.attribute (sor)
  // other.referential attribute (sar)    other.attribute (sar)
  // other.referential attribute (sar)    other.attribute (smr)
  // other.referential attribute (sar)    other.referential attribute (saf)
  // other.referential attribute (sar)    other.referential attribute (smf)
  // other.referential attribute (sar)    other.referential attribute (sor)
  // other.referential attribute (sar)    other.referential attribute (sar)
  // other.referential attribute (sar)    other.referential attribute (smr)
  // other.referential attribute (sar)    local existing
  // other.referential attribute (smr)    constant
  // other.referential attribute (smr)    constant
  // other.referential attribute (smr)    self.attribute
  // other.referential attribute (smr)    self.referential attribute
  // other.referential attribute (smr)    other.attribute (saf)
  // other.referential attribute (smr)    other.attribute (smf)
  // other.referential attribute (smr)    other.attribute (sor)
  // other.referential attribute (smr)    other.attribute (sar)
  // other.referential attribute (smr)    other.attribute (smr)
  // other.referential attribute (smr)    other.referential attribute (saf)
  // other.referential attribute (smr)    other.referential attribute (smf)
  // other.referential attribute (smr)    other.referential attribute (sor)
  // other.referential attribute (smr)    other.referential attribute (sar)
  // other.referential attribute (smr)    other.referential attribute (smr)
 
LOG::LogInfo(message:"Completed and test") ;

//generate event to commence next test
generate BET3:''Start or test''(tfalse:false, ttrue:true) to self;
 

',
	'');
INSERT INTO SM_STATE
	VALUES (2621443,
	2621445,
	0,
	'Or test 1a',
	4,
	0);
INSERT INTO SM_CH
	VALUES (2621443,
	2621441,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621443,
	2621441,
	2621445,
	0);
INSERT INTO SM_CH
	VALUES (2621443,
	2621442,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621443,
	2621442,
	2621445,
	0);
INSERT INTO SM_CH
	VALUES (2621443,
	2621443,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621443,
	2621443,
	2621445,
	0);
INSERT INTO SM_SEME
	VALUES (2621443,
	2621444,
	2621445,
	0);
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
	'LOG::LogInfo(message:"Starting or test") ;

//or operation  (only boolean type)

assign temp1 = false;
assign temp2 = true;

  // local existing    local existing
assign t1 = temp1 or temp1 ;
if ( t1 == false )
  LOG::LogSuccess(message:" or local_existing local_existing ( false )") ;
else
  LOG::LogFailure(message:" or local_existing local_existing ( false )") ;
end if;
 
assign t2 = temp1 or temp2 ;
if ( t2 == true )
  LOG::LogSuccess(message:" or local_existing local_existing ( true )") ;
else
  LOG::LogFailure(message:" or local_existing local_existing ( true )") ;
end if;
 
assign t3 = temp2 or temp1 ;
if ( t3 == true )
  LOG::LogSuccess(message:" or local_existing local_existing ( true )") ;
else
  LOG::LogFailure(message:" or local_existing local_existing ( true )") ;
end if;
 
assign t4 = temp2 or temp2 ;
if ( t4 == true )
  LOG::LogSuccess(message:" or local_existing local_existing ( true )") ;
else
  LOG::LogFailure(message:" or local_existing local_existing ( true )") ;
end if;
 
  // local existing    rcvd_evt
assign t5 = temp1 or rcvd_evt.tfalse ;
if ( t5 == false )
  LOG::LogSuccess(message:" or local_existing rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" or local_existing rcvd_evt ( false )") ;
end if;
 
assign t6 = temp1 or rcvd_evt.ttrue ;
if ( t6 == true )
  LOG::LogSuccess(message:" or local_existing rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or local_existing rcvd_evt ( true )") ;
end if;
 
assign t7 = temp2 or rcvd_evt.tfalse ;
if ( t7 == true )
  LOG::LogSuccess(message:" or local_existing rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or local_existing rcvd_evt ( true )") ;
end if;
 
assign t8 = temp2 or rcvd_evt.ttrue ;
if ( t8 == true )
  LOG::LogSuccess(message:" or local_existing rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or local_existing rcvd_evt ( true )") ;
end if;
 
  // local existing   constant 
assign t9 = temp1 or false ;
if ( t9 == false )
  LOG::LogSuccess(message:" or local_existing constant ( false )") ;
else
  LOG::LogFailure(message:" or local_existing constant ( false )") ;
end if;
 
assign t10 = temp1 or true ;
if ( t10 == true )
  LOG::LogSuccess(message:" or local_existing constant ( true )") ;
else
  LOG::LogFailure(message:" or local_existing constant ( true )") ;
end if;
 
assign t11 = temp2 or false ;
if ( t11 == true )
  LOG::LogSuccess(message:" or local_existing constant ( true )") ;
else
  LOG::LogFailure(message:" or local_existing constant ( true )") ;
end if;
 
assign t12 = temp2 or true ;
if ( t12 == true )
  LOG::LogSuccess(message:" or local_existing constant ( true )") ;
else
  LOG::LogFailure(message:" or local_existing constant ( true )") ;
end if;
 
  // local existing   self.attribute 
assign t13 = temp1 or self.bfalse ;
if ( t13 == false )
  LOG::LogSuccess(message:" or local_existing self.attribute ( false )") ;
else
  LOG::LogFailure(message:" or local_existing self.attribute ( false )") ;
end if;
 
assign t14 = temp1 or self.btrue ;
if ( t14 == true )
  LOG::LogSuccess(message:" or local_existing self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or local_existing self.attribute ( true )") ;
end if;
 
assign t15 = temp2 or self.bfalse ;
if ( t15 == true )
  LOG::LogSuccess(message:" or local_existing self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or local_existing self.attribute ( true )") ;
end if;
 
assign t16 = temp2 or self.btrue ;
if ( t16 == true )
  LOG::LogSuccess(message:" or local_existing self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or local_existing self.attribute ( true )") ;
end if;
 

  // local existing    self.referential attribute
assign t17 = temp1 or self.r9bfalse ;
if ( t17 == false )
  LOG::LogSuccess(message:" or local_existing self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" or local_existing self.referential_attribute ( false )") ;
end if;
 
assign t18 = temp1 or self.r9btrue ;
if ( t18 == true )
  LOG::LogSuccess(message:" or local_existing self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or local_existing self.referential_attribute ( true )") ;
end if;
 
assign t19 = temp2 or self.r9bfalse ;
if ( t19 == true )
  LOG::LogSuccess(message:" or local_existing self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or local_existing self.referential_attribute ( true )") ;
end if;
 
assign t20 = temp2 or self.r9btrue ;
if ( t20 == true )
  LOG::LogSuccess(message:" or local_existing self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or local_existing self.referential_attribute ( true )") ;
end if;
 
  // local existing    other.attribute (saf)
select any saf from instances of OBET;
assign t21 = temp1 or saf.bfalse ;
if ( t21 == false )
  LOG::LogSuccess(message:" or local_existing other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(saf) ( false )") ;
end if;
 
assign t22 = temp1 or saf.btrue ;
if ( t22 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(saf) ( true )") ;
end if;
 
assign t23 = temp2 or saf.bfalse ;
if ( t23 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(saf) ( true )") ;
end if;
 
assign t24 = temp2 or saf.btrue ;
if ( t24 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(saf) ( true )") ;
end if;
 
  // local existing    other.attribute (smf)
select many smfs from instances of OBET;
for each smf in smfs
assign t25 = temp1 or smf.bfalse ;
if ( t25 == false )
  LOG::LogSuccess(message:" or local_existing other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(smf) ( false )") ;
end if;
 
assign t26 = temp1 or smf.btrue ;
if ( t26 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(smf) ( true )") ;
end if;
 
assign t27 = temp2 or smf.bfalse ;
if ( t27 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(smf) ( true )") ;
end if;
 
assign t28 = temp2 or smf.btrue ;
if ( t28 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(smf) ( true )") ;
end if;
 
end for;
  // local existing    other.attribute (sor)
select one sor related by self->OBET[R9];
assign t29 = temp1 or sor.bfalse ;
if ( t29 == false )
  LOG::LogSuccess(message:" or local_existing other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(sor) ( false )") ;
end if;
 
assign t30 = temp1 or sor.btrue ;
if ( t30 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(sor) ( true )") ;
end if;
 
assign t31 = temp2 or sor.bfalse ;
if ( t31 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(sor) ( true )") ;
end if;
 
assign t32 = temp2 or sor.btrue ;
if ( t32 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(sor) ( true )") ;
end if;
 
  // local existing    other.attribute (sar)
select any sar related by self->OBET[R10];
assign t33 = temp1 or sar.bfalse ;
if ( t33 == false )
  LOG::LogSuccess(message:" or local_existing other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(sar) ( false )") ;
end if;
 
assign t34 = temp1 or sar.btrue ;
if ( t34 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(sar) ( true )") ;
end if;
 
assign t35 = temp2 or sar.bfalse ;
if ( t35 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(sar) ( true )") ;
end if;
 
assign t36 = temp2 or sar.btrue ;
if ( t36 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(sar) ( true )") ;
end if;
 
  // local existing    other.attribute (smr)
select many smrs related by self->OBET[R10];
for each smr in smrs
assign t37 = temp1 or smr.bfalse ;
if ( t37 == false )
  LOG::LogSuccess(message:" or local_existing other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(smr) ( false )") ;
end if;
 
assign t38 = temp1 or smr.btrue ;
if ( t38 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(smr) ( true )") ;
end if;
 
assign t39 = temp2 or smr.bfalse ;
if ( t39 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(smr) ( true )") ;
end if;
 
assign t40 = temp2 or smr.btrue ;
if ( t40 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(smr) ( true )") ;
end if;
 
end for;
  // local existing    other.referential attribute (saf)
assign t41 = temp1 or saf.r11bfalse ;
if ( t41 == false )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(saf) ( false )") ;
end if;
 
assign t42 = temp1 or saf.r11btrue ;
if ( t42 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(saf) ( true )") ;
end if;
 
assign t43 = temp2 or saf.r11bfalse ;
if ( t43 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(saf) ( true )") ;
end if;
 
assign t44 = temp2 or saf.r11btrue ;
if ( t44 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(saf) ( true )") ;
end if;
 
  // local existing    other.referential attribute (smf)
for each smf in smfs
assign t45 = temp1 or smf.r11bfalse ;
if ( t45 == false )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(smf) ( false )") ;
end if;
 
assign t46 = temp1 or smf.r11btrue ;
if ( t46 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(smf) ( true )") ;
end if;
 
assign t47 = temp2 or smf.r11bfalse ;
if ( t47 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(smf) ( true )") ;
end if;
 
assign t48 = temp2 or smf.r11btrue ;
if ( t48 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // local existing    other.referential attribute (sor)
assign t49 = temp1 or sor.r11bfalse ;
if ( t49 == false )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(sor) ( false )") ;
end if;
 
assign t50 = temp1 or sor.r11btrue ;
if ( t50 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(sor) ( true )") ;
end if;
 
assign t51 = temp2 or sor.r11bfalse ;
if ( t51 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(sor) ( true )") ;
end if;
 
assign t52 = temp2 or sor.r11btrue ;
if ( t52 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(sor) ( true )") ;
end if;
 
  // local existing    other.referential attribute (sar)
assign t53 = temp1 or sar.r11bfalse ;
if ( t53 == false )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(sar) ( false )") ;
end if;
 
assign t54 = temp1 or sar.r11btrue ;
if ( t54 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(sar) ( true )") ;
end if;
 
assign t55 = temp2 or sar.r11bfalse ;
if ( t55 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(sar) ( true )") ;
end if;
 
assign t56 = temp2 or sar.r11btrue ;
if ( t56 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(sar) ( true )") ;
end if;
 
  // local existing    other.referential attribute (smr)
for each smr in smrs
assign t57 = temp1 or smr.r11bfalse ;
if ( t57 == false )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(smr) ( false )") ;
end if;
 
assign t58 = temp1 or smr.r11btrue ;
if ( t58 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(smr) ( true )") ;
end if;
 
assign t59 = temp2 or smr.r11bfalse ;
if ( t59 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(smr) ( true )") ;
end if;
 
assign t60 = temp2 or smr.r11btrue ;
if ( t60 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(smr) ( true )") ;
end if;
 
end for;
  // rcvd_evt    local existing
assign t61 = rcvd_evt.tfalse or temp1 ;
if ( t61 == false )
  LOG::LogSuccess(message:" or rcvd_evt local_existing ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt local_existing ( false )") ;
end if;
 
assign t62 = rcvd_evt.ttrue or temp1 ;
if ( t62 == true )
  LOG::LogSuccess(message:" or rcvd_evt local_existing ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt local_existing ( true )") ;
end if;
 
assign t63 = rcvd_evt.tfalse or temp2 ;
if ( t63 == true )
  LOG::LogSuccess(message:" or rcvd_evt local_existing ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt local_existing ( true )") ;
end if;
 
assign t64 = rcvd_evt.ttrue or temp2 ;
if ( t64 == true )
  LOG::LogSuccess(message:" or rcvd_evt local_existing ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt local_existing ( true )") ;
end if;
 
  // rcvd_evt    rcvd_evt
assign t65 = rcvd_evt.tfalse or rcvd_evt.tfalse ;
if ( t65 == false )
  LOG::LogSuccess(message:" or rcvd_evt rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt rcvd_evt ( false )") ;
end if;
 
assign t66 = rcvd_evt.ttrue or rcvd_evt.tfalse ;
if ( t66 == true )
  LOG::LogSuccess(message:" or rcvd_evt rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt rcvd_evt ( true )") ;
end if;
 
assign t67 = rcvd_evt.tfalse or rcvd_evt.ttrue ;
if ( t67 == true )
  LOG::LogSuccess(message:" or rcvd_evt rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt rcvd_evt ( true )") ;
end if;
 
assign t68 = rcvd_evt.ttrue or rcvd_evt.ttrue ;
if ( t68 == true )
  LOG::LogSuccess(message:" or rcvd_evt rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt rcvd_evt ( true )") ;
end if;
 
  // rcvd_evt    constant
assign t69 = rcvd_evt.tfalse or false ;
if ( t69 == false )
  LOG::LogSuccess(message:" or rcvd_evt constant ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt constant ( false )") ;
end if;
 
assign t70 = rcvd_evt.ttrue or false ;
if ( t70 == true )
  LOG::LogSuccess(message:" or rcvd_evt constant ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt constant ( true )") ;
end if;
 
assign t71 = rcvd_evt.tfalse or true ;
if ( t71 == true )
  LOG::LogSuccess(message:" or rcvd_evt constant ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt constant ( true )") ;
end if;
 
assign t72 = rcvd_evt.ttrue or true ;
if ( t72 == true )
  LOG::LogSuccess(message:" or rcvd_evt constant ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt constant ( true )") ;
end if;
 
  // rcvd_evt    self.attribute
assign t73 = rcvd_evt.tfalse or self.bfalse ;
if ( t73 == false )
  LOG::LogSuccess(message:" or rcvd_evt self.attribute ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt self.attribute ( false )") ;
end if;
 
assign t74 = rcvd_evt.ttrue or self.bfalse ;
if ( t74 == true )
  LOG::LogSuccess(message:" or rcvd_evt self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt self.attribute ( true )") ;
end if;
 
assign t75 = rcvd_evt.tfalse or self.btrue ;
if ( t75 == true )
  LOG::LogSuccess(message:" or rcvd_evt self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt self.attribute ( true )") ;
end if;
 
assign t76 = rcvd_evt.ttrue or self.btrue ;
if ( t76 == true )
  LOG::LogSuccess(message:" or rcvd_evt self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt self.attribute ( true )") ;
end if;
 
  // rcvd_evt    self.referential attribute
assign t77 = rcvd_evt.tfalse or self.r9bfalse ;
if ( t77 == false )
  LOG::LogSuccess(message:" or rcvd_evt self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt self.referential_attribute ( false )") ;
end if;
 
assign t78 = rcvd_evt.ttrue or self.r9bfalse ;
if ( t78 == true )
  LOG::LogSuccess(message:" or rcvd_evt self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt self.referential_attribute ( true )") ;
end if;
 
assign t79 = rcvd_evt.tfalse or self.r9btrue ;
if ( t79 == true )
  LOG::LogSuccess(message:" or rcvd_evt self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt self.referential_attribute ( true )") ;
end if;
 
assign t80 = rcvd_evt.ttrue or self.r9btrue ;
if ( t80 == true )
  LOG::LogSuccess(message:" or rcvd_evt self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt self.referential_attribute ( true )") ;
end if;
 
  // rcvd_evt    other.attribute (saf)
assign t81 = rcvd_evt.tfalse or saf.bfalse ;
if ( t81 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(saf) ( false )") ;
end if;
 
assign t82 = rcvd_evt.tfalse or saf.btrue ;
if ( t82 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(saf) ( true )") ;
end if;
 
assign t83 = rcvd_evt.ttrue or saf.bfalse ;
if ( t83 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(saf) ( true )") ;
end if;
 
assign t84 = rcvd_evt.ttrue or saf.btrue ;
if ( t84 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(saf) ( true )") ;
end if;
 
  // rcvd_evt    other.attribute (smf)
for each smf in smfs
assign t85 = rcvd_evt.tfalse or smf.bfalse ;
if ( t85 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(smf) ( false )") ;
end if;
 
assign t86 = rcvd_evt.tfalse or smf.btrue ;
if ( t86 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(smf) ( true )") ;
end if;
 
assign t87 = rcvd_evt.ttrue or smf.bfalse ;
if ( t87 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(smf) ( true )") ;
end if;
 
assign t88 = rcvd_evt.ttrue or smf.btrue ;
if ( t88 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(smf) ( true )") ;
end if;
 
end for;
  // rcvd_evt    other.attribute (sor)
assign t89 = rcvd_evt.tfalse or sor.bfalse ;
if ( t89 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(sor) ( false )") ;
end if;
 
assign t90 = rcvd_evt.tfalse or sor.btrue ;
if ( t90 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(sor) ( true )") ;
end if;
 
assign t91 = rcvd_evt.ttrue or sor.bfalse ;
if ( t91 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(sor) ( true )") ;
end if;
 
assign t92 = rcvd_evt.ttrue or sor.btrue ;
if ( t92 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(sor) ( true )") ;
end if;
 
  // rcvd_evt    other.attribute (sar)
assign t93 = rcvd_evt.tfalse or sar.bfalse ;
if ( t93 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(sar) ( false )") ;
end if;
 
assign t94 = rcvd_evt.tfalse or sar.btrue ;
if ( t94 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(sar) ( true )") ;
end if;
 
assign t95 = rcvd_evt.ttrue or sar.bfalse ;
if ( t95 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(sar) ( true )") ;
end if;
 
assign t96 = rcvd_evt.ttrue or sar.btrue ;
if ( t96 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(sar) ( true )") ;
end if;
 
  // rcvd_evt    other.attribute (smr)
for each smr in smrs
assign t97 = rcvd_evt.tfalse or smr.bfalse ;
if ( t97 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(smr) ( false )") ;
end if;
 
assign t98 = rcvd_evt.tfalse or smr.btrue ;
if ( t98 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(smr) ( true )") ;
end if;
 
assign t99 = rcvd_evt.ttrue or smr.bfalse ;
if ( t99 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(smr) ( true )") ;
end if;
 
assign t100 = rcvd_evt.ttrue or smr.btrue ;
if ( t100 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(smr) ( true )") ;
end if;
 
end for;
  // rcvd_evt    other.referential attribute (saf)
assign t101 = rcvd_evt.tfalse or saf.r11bfalse ;
if ( t101 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(saf) ( false )") ;
end if;
 
assign t102 = rcvd_evt.tfalse or saf.r11btrue ;
if ( t102 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(saf) ( true )") ;
end if;
 
assign t103 = rcvd_evt.ttrue or saf.r11bfalse ;
if ( t103 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(saf) ( true )") ;
end if;
 
assign t104 = rcvd_evt.ttrue or saf.r11btrue ;
if ( t104 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(saf) ( true )") ;
end if;
 
  // rcvd_evt    other.referential attribute (smf)
for each smf in smfs
assign t105 = rcvd_evt.tfalse or smf.r11bfalse ;
if ( t105 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(smf) ( false )") ;
end if;
 
assign t106 = rcvd_evt.tfalse or smf.r11btrue ;
if ( t106 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(smf) ( true )") ;
end if;
 
assign t107 = rcvd_evt.ttrue or smf.r11bfalse ;
if ( t107 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(smf) ( true )") ;
end if;
 
assign t108 = rcvd_evt.ttrue or smf.r11btrue ;
if ( t108 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // rcvd_evt    other.referential attribute (sor)
assign t109 = rcvd_evt.tfalse or sor.r11bfalse ;
if ( t109 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(sor) ( false )") ;
end if;
 
assign t110 = rcvd_evt.tfalse or sor.r11btrue ;
if ( t110 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(sor) ( true )") ;
end if;
 
assign t111 = rcvd_evt.ttrue or sor.r11bfalse ;
if ( t111 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(sor) ( true )") ;
end if;
 
assign t112 = rcvd_evt.ttrue or sor.r11btrue ;
if ( t112 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(sor) ( true )") ;
end if;
 
  // rcvd_evt    other.referential attribute (sar)
assign t113 = rcvd_evt.tfalse or sar.r11bfalse ;
if ( t113 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(sar) ( false )") ;
end if;
 
assign t114 = rcvd_evt.tfalse or sar.r11btrue ;
if ( t114 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(sar) ( true )") ;
end if;
 
assign t115 = rcvd_evt.ttrue or sar.r11bfalse ;
if ( t115 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(sar) ( true )") ;
end if;
 
assign t116 = rcvd_evt.ttrue or sar.r11btrue ;
if ( t116 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(sar) ( true )") ;
end if;
 
  // rcvd_evt    other.referential attribute (smr)
for each smr in smrs
assign t117 = rcvd_evt.tfalse or smr.r11bfalse ;
if ( t117 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(smr) ( false )") ;
end if;
 
assign t118 = rcvd_evt.tfalse or smr.r11btrue ;
if ( t118 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(smr) ( true )") ;
end if;
 
assign t119 = rcvd_evt.ttrue or smr.r11bfalse ;
if ( t119 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(smr) ( true )") ;
end if;
 
assign t120 = rcvd_evt.ttrue or smr.r11btrue ;
if ( t120 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(smr) ( true )") ;
end if;
 
end for;

 
//generate event to continue test
Generate BET4:''Continue or test''( ttrue: true, tfalse: false ) to self; 

',
	'');
INSERT INTO SM_STATE
	VALUES (2621444,
	2621445,
	0,
	'Or test 2',
	6,
	0);
INSERT INTO SM_CH
	VALUES (2621444,
	2621441,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621444,
	2621441,
	2621445,
	0);
INSERT INTO SM_CH
	VALUES (2621444,
	2621442,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621444,
	2621442,
	2621445,
	0);
INSERT INTO SM_CH
	VALUES (2621444,
	2621443,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621444,
	2621443,
	2621445,
	0);
INSERT INTO SM_CH
	VALUES (2621444,
	2621444,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621444,
	2621444,
	2621445,
	0);
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
	'
//or operation  (only boolean type)

assign temp1 = false;
assign temp2 = true;

select any saf from instances of OBET;
select many smfs from instances of OBET;
select one sor related by self->OBET[R9];
select any sar related by self->OBET[R10];
select many smrs related by self->OBET[R10];

  // other.attribute (saf)    local existing
assign t301 = saf.bfalse or temp1 ;
if ( t301 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) local_existing ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) local_existing ( false )") ;
end if;
 
assign t302 = saf.btrue or temp1 ;
if ( t302 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) local_existing ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) local_existing ( true )") ;
end if;
 
assign t303 = saf.bfalse or temp2 ;
if ( t303 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) local_existing ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) local_existing ( true )") ;
end if;
 
assign t304 = saf.btrue or temp2 ;
if ( t304 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) local_existing ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) local_existing ( true )") ;
end if;
 
  // other.attribute(saf)   rcvd_evt 
assign t305 = saf.bfalse or rcvd_evt.tfalse ;
if ( t305 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) rcvd_evt ( false )") ;
end if;
 
assign t306 = saf.btrue or rcvd_evt.tfalse ;
if ( t306 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) rcvd_evt ( true )") ;
end if;
 
assign t307 = saf.bfalse or rcvd_evt.ttrue ;
if ( t307 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) rcvd_evt ( true )") ;
end if;
 
assign t308 = saf.btrue or rcvd_evt.ttrue ;
if ( t308 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) rcvd_evt ( true )") ;
end if;
 
  // other.attribute(saf)    constant
assign t309 = saf.bfalse or false ;
if ( t309 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) constant ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) constant ( false )") ;
end if;
 
assign t310 = saf.btrue or false ;
if ( t310 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) constant ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) constant ( true )") ;
end if;
 
assign t311 = saf.bfalse or true ;
if ( t311 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) constant ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) constant ( true )") ;
end if;
 
assign t312 = saf.btrue or true ;
if ( t312 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) constant ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) constant ( true )") ;
end if;
 
  // other.attribute(saf)    self.attribute
assign t313 = saf.bfalse or self.bfalse ;
if ( t313 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) self.attribute ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) self.attribute ( false )") ;
end if;
 
assign t314 = saf.btrue or self.bfalse ;
if ( t314 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) self.attribute ( true )") ;
end if;
 
assign t315 = saf.bfalse or self.btrue ;
if ( t315 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) self.attribute ( true )") ;
end if;
 
assign t316 = saf.btrue or self.btrue ;
if ( t316 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) self.attribute ( true )") ;
end if;
 
  // other.attribute(saf)    self.referential attribute
assign t317 = saf.bfalse or self.r9bfalse ;
if ( t317 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) self.referential_attribute ( false )") ;
end if;
 
assign t318 = saf.btrue or self.r9bfalse ;
if ( t318 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) self.referential_attribute ( true )") ;
end if;
 
assign t319 = saf.bfalse or self.r9btrue ;
if ( t319 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) self.referential_attribute ( true )") ;
end if;
 
assign t320 = saf.btrue or self.r9btrue ;
if ( t320 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) self.referential_attribute ( true )") ;
end if;
 
  // other.attribute(saf)    other.attribute (saf)
assign t321 = saf.bfalse or saf.bfalse ;
if ( t321 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(saf) ( false )") ;
end if;
 
assign t322 = saf.bfalse or saf.btrue ;
if ( t322 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(saf) ( true )") ;
end if;
 
assign t323 = saf.btrue or saf.bfalse ;
if ( t323 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(saf) ( true )") ;
end if;
 
assign t324 = saf.btrue or saf.btrue ;
if ( t324 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(saf) ( true )") ;
end if;
 
  // other.attribute(saf)    other.attribute (smf)
for each smf in smfs
assign t325 = saf.bfalse or smf.bfalse ;
if ( t325 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(smf) ( false )") ;
end if;
 
assign t326 = saf.bfalse or smf.btrue ;
if ( t326 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(smf) ( true )") ;
end if;
 
assign t327 = saf.btrue or smf.bfalse ;
if ( t327 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(smf) ( true )") ;
end if;
 
assign t328 = saf.btrue or smf.btrue ;
if ( t328 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(smf) ( true )") ;
end if;
 
end for;
  // other.attribute(saf)    other.attribute (sor)
assign t329 = saf.bfalse or sor.bfalse ;
if ( t329 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(sor) ( false )") ;
end if;
 
assign t330 = saf.bfalse or sor.btrue ;
if ( t330 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(sor) ( true )") ;
end if;
 
assign t331 = saf.btrue or sor.bfalse ;
if ( t331 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(sor) ( true )") ;
end if;
 
assign t332 = saf.btrue or sor.btrue ;
if ( t332 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(sor) ( true )") ;
end if;
 
  // other.attribute(saf)    other.attribute (sar)
assign t333 = saf.bfalse or sar.bfalse ;
if ( t333 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(sar) ( false )") ;
end if;
 
assign t334 = saf.bfalse or sar.btrue ;
if ( t334 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(sar) ( true )") ;
end if;
 
assign t335 = saf.btrue or sar.bfalse ;
if ( t335 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(sar) ( true )") ;
end if;
 
assign t336 = saf.btrue or sar.btrue ;
if ( t336 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(sar) ( true )") ;
end if;
 
  // other.attribute(saf)    other.attribute (smr)
for each smr in smrs
assign t337 = saf.bfalse or smr.bfalse ;
if ( t337 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(smr) ( false )") ;
end if;
 
assign t338 = saf.bfalse or smr.btrue ;
if ( t338 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(smr) ( true )") ;
end if;
 
assign t339 = saf.btrue or smr.bfalse ;
if ( t339 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(smr) ( true )") ;
end if;
 
assign t340 = saf.btrue or smr.btrue ;
if ( t340 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(smr) ( true )") ;
end if;
 
end for;
  // other.attribute(saf)    other.referential attribute (saf)
assign t341 = saf.bfalse or saf.r11bfalse ;
if ( t341 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(saf) ( false )") ;
end if;
 
assign t342 = saf.bfalse or saf.r11btrue ;
if ( t342 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(saf) ( true )") ;
end if;
 
assign t343 = saf.btrue or saf.r11bfalse ;
if ( t343 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(saf) ( true )") ;
end if;
 
assign t344 = saf.btrue or saf.r11btrue ;
if ( t344 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(saf) ( true )") ;
end if;
 
  // other.attribute(saf)    other.referential attribute (smf)
for each smf in smfs
assign t345 = saf.bfalse or smf.r11bfalse ;
if ( t345 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(smf) ( false )") ;
end if;
 
assign t346 = saf.bfalse or smf.r11btrue ;
if ( t346 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(smf) ( true )") ;
end if;
 
assign t347 = saf.btrue or smf.r11bfalse ;
if ( t347 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(smf) ( true )") ;
end if;
 
assign t348 = saf.btrue or smf.r11btrue ;
if ( t348 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // other.attribute(saf)    other.referential attribute (sor)
assign t349 = saf.bfalse or sor.r11bfalse ;
if ( t349 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(sor) ( false )") ;
end if;
 
assign t350 = saf.bfalse or sor.r11btrue ;
if ( t350 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(sor) ( true )") ;
end if;
 
assign t351 = saf.btrue or sor.r11bfalse ;
if ( t351 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(sor) ( true )") ;
end if;
 
assign t352 = saf.btrue or sor.r11btrue ;
if ( t352 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(sor) ( true )") ;
end if;
 
  // other.attribute(saf)    other.referential attribute (sar)
assign t353 = saf.bfalse or sar.r11bfalse ;
if ( t353 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(sar) ( false )") ;
end if;
 
assign t354 = saf.bfalse or sar.r11btrue ;
if ( t354 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(sar) ( true )") ;
end if;
 
assign t355 = saf.btrue or sar.r11bfalse ;
if ( t355 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(sar) ( true )") ;
end if;
 
assign t356 = saf.btrue or sar.r11btrue ;
if ( t356 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(sar) ( true )") ;
end if;
 
  // other.attribute(saf)    other.referential attribute (smr)
for each smr in smrs
assign t357 = saf.bfalse or smr.r11bfalse ;
if ( t357 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(smr) ( false )") ;
end if;
 
assign t358 = saf.bfalse or smr.r11btrue ;
if ( t358 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(smr) ( true )") ;
end if;
 
assign t359 = saf.btrue or smr.r11bfalse ;
if ( t359 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(smr) ( true )") ;
end if;
 
assign t360 = saf.btrue or smr.r11btrue ;
if ( t360 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(smr) ( true )") ;
end if;
 
end for;
  // other.attribute (smf)    local existing
  // other.attribute (smf)    constant
  // other.attribute (smf)    constant
  // other.attribute (smf)    self.attribute
  // other.attribute (smf)    self.referential attribute
  // other.attribute (smf)    other.attribute (saf)
  // other.attribute (smf)    other.attribute (smf)
  // other.attribute (smf)    other.attribute (sor)
  // other.attribute (smf)    other.attribute (sar)
  // other.attribute (smf)    other.attribute (smr)
  // other.attribute (smf)    other.referential attribute (saf)
  // other.attribute (smf)    other.referential attribute (smf)
  // other.attribute (smf)    other.referential attribute (sor)
  // other.attribute (smf)    other.referential attribute (sar)
  // other.attribute (smf)    other.referential attribute (smr)
  // other.attribute (sor)    local existing
  // other.attribute (sor)    constant
  // other.attribute (sor)    constant
  // other.attribute (sor)    self.attribute
  // other.attribute (sor)    self.referential attribute
  // other.attribute (sor)    other.attribute (saf)
  // other.attribute (sor)    other.attribute (smf)
  // other.attribute (sor)    other.attribute (sor)
  // other.attribute (sor)    other.attribute (sar)
  // other.attribute (sor)    other.attribute (smr)
  // other.attribute (sor)    other.referential attribute (saf)
  // other.attribute (sor)    other.referential attribute (smf)
  // other.attribute (sor)    other.referential attribute (sor)
  // other.attribute (sor)    other.referential attribute (sar)
  // other.attribute (sor)    other.referential attribute (smr)
  // other.attribute (sar)    local existing
  // other.attribute (sar)    constant
  // other.attribute (sar)    constant
  // other.attribute (sar)    self.attribute
  // other.attribute (sar)    self.referential attribute
  // other.attribute (sar)    other.attribute (saf)
  // other.attribute (sar)    other.attribute (smf)
  // other.attribute (sar)    other.attribute (sor)
  // other.attribute (sar)    other.attribute (sar)
  // other.attribute (sar)    other.attribute (smr)
  // other.attribute (sar)    other.referential attribute (saf)
  // other.attribute (sar)    other.referential attribute (smf)
  // other.attribute (sar)    other.referential attribute (sor)
  // other.attribute (sar)    other.referential attribute (sar)
  // other.attribute (sar)    other.referential attribute (smr)
  // other.attribute (smr)    local existing
  // other.attribute (smr)    constant
  // other.attribute (smr)    constant
  // other.attribute (smr)    self.attribute
  // other.attribute (smr)    self.referential attribute
  // other.attribute (smr)    other.attribute (saf)
  // other.attribute (smr)    other.attribute (smf)
  // other.attribute (smr)    other.attribute (sor)
  // other.attribute (smr)    other.attribute (sar)
  // other.attribute (smr)    other.attribute (smr)
  // other.attribute (smr)    other.referential attribute (saf)
  // other.attribute (smr)    other.referential attribute (smf)
  // other.attribute (smr)    other.referential attribute (sor)
  // other.attribute (smr)    other.referential attribute (sar)
  // other.attribute (smr)    other.referential attribute (smr)
  // other.referential attribute (saf)    local existing
assign t601 = saf.r11bfalse or temp1 ;
if ( t601 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) local_existing ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) local_existing ( false )") ;
end if;
 
assign t602 = saf.r11btrue or temp1 ;
if ( t602 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) local_existing ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) local_existing ( true )") ;
end if;
 
assign t603 = saf.r11bfalse or temp2 ;
if ( t603 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) local_existing ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) local_existing ( true )") ;
end if;
 
assign t604 = saf.r11btrue or temp2 ;
if ( t604 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) local_existing ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) local_existing ( true )") ;
end if;
 
  // other.referential_attribute(saf)   rcvd_evt 
assign t605 = saf.r11bfalse or rcvd_evt.tfalse ;
if ( t605 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) rcvd_evt ( false )") ;
end if;
 
assign t606 = saf.r11btrue or rcvd_evt.tfalse ;
if ( t606 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) rcvd_evt ( true )") ;
end if;
 
assign t607 = saf.r11bfalse or rcvd_evt.ttrue ;
if ( t607 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) rcvd_evt ( true )") ;
end if;
 
assign t608 = saf.r11btrue or rcvd_evt.ttrue ;
if ( t608 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) rcvd_evt ( true )") ;
end if;
 
  // other.referential_attribute(saf)    constant
assign t609 = saf.r11bfalse or false ;
if ( t609 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) constant ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) constant ( false )") ;
end if;
 
assign t610 = saf.r11btrue or false ;
if ( t610 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) constant ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) constant ( true )") ;
end if;
 
assign t611 = saf.r11bfalse or true ;
if ( t611 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) constant ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) constant ( true )") ;
end if;
 
assign t612 = saf.r11btrue or true ;
if ( t612 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) constant ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) constant ( true )") ;
end if;
 
  // other.referential_attribute(saf)    self.attribute
assign t613 = saf.r11bfalse or self.bfalse ;
if ( t613 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) self.attribute ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) self.attribute ( false )") ;
end if;
 
assign t614 = saf.r11btrue or self.bfalse ;
if ( t614 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) self.attribute ( true )") ;
end if;
 
assign t615 = saf.r11bfalse or self.btrue ;
if ( t615 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) self.attribute ( true )") ;
end if;
 
assign t616 = saf.r11btrue or self.btrue ;
if ( t616 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) self.attribute ( true )") ;
end if;
 
  // other.referential_attribute(saf)    self.referential attribute
assign t617 = saf.r11bfalse or self.r9bfalse ;
if ( t617 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) self.referential_attribute ( false )") ;
end if;
 
assign t618 = saf.r11btrue or self.r9bfalse ;
if ( t618 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) self.referential_attribute ( true )") ;
end if;
 
assign t619 = saf.r11bfalse or self.r9btrue ;
if ( t619 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) self.referential_attribute ( true )") ;
end if;
 
assign t620 = saf.r11btrue or self.r9btrue ;
if ( t620 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) self.referential_attribute ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.attribute (saf)
assign t621 = saf.r11bfalse or saf.bfalse ;
if ( t621 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(saf) ( false )") ;
end if;
 
assign t622 = saf.r11bfalse or saf.btrue ;
if ( t622 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(saf) ( true )") ;
end if;
 
assign t623 = saf.r11btrue or saf.bfalse ;
if ( t623 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(saf) ( true )") ;
end if;
 
assign t624 = saf.r11btrue or saf.btrue ;
if ( t624 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(saf) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.attribute (smf)
for each smf in smfs
assign t625 = saf.r11bfalse or smf.bfalse ;
if ( t625 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(smf) ( false )") ;
end if;
 
assign t626 = saf.r11bfalse or smf.btrue ;
if ( t626 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(smf) ( true )") ;
end if;
 
assign t627 = saf.r11btrue or smf.bfalse ;
if ( t627 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(smf) ( true )") ;
end if;
 
assign t628 = saf.r11btrue or smf.btrue ;
if ( t628 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(smf) ( true )") ;
end if;
 
end for;
  // other.referential_attribute(saf)    other.attribute (sor)
assign t629 = saf.r11bfalse or sor.bfalse ;
if ( t629 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(sor) ( false )") ;
end if;
 
assign t630 = saf.r11bfalse or sor.btrue ;
if ( t630 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(sor) ( true )") ;
end if;
 
assign t631 = saf.r11btrue or sor.bfalse ;
if ( t631 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(sor) ( true )") ;
end if;
 
assign t632 = saf.r11btrue or sor.btrue ;
if ( t632 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(sor) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.attribute (sar)
assign t633 = saf.r11bfalse or sar.bfalse ;
if ( t633 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(sar) ( false )") ;
end if;
 
assign t634 = saf.r11bfalse or sar.btrue ;
if ( t634 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(sar) ( true )") ;
end if;
 
assign t635 = saf.r11btrue or sar.bfalse ;
if ( t635 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(sar) ( true )") ;
end if;
 
assign t636 = saf.r11btrue or sar.btrue ;
if ( t636 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(sar) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.attribute (smr)
for each smr in smrs
assign t637 = saf.r11bfalse or smr.bfalse ;
if ( t637 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(smr) ( false )") ;
end if;
 
assign t638 = saf.r11bfalse or smr.btrue ;
if ( t638 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(smr) ( true )") ;
end if;
 
assign t639 = saf.r11btrue or smr.bfalse ;
if ( t639 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(smr) ( true )") ;
end if;
 
assign t640 = saf.r11btrue or smr.btrue ;
if ( t640 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(smr) ( true )") ;
end if;
 
end for;
  // other.referential_attribute(saf)    other.referential attribute (saf)
assign t641 = saf.r11bfalse or saf.r11bfalse ;
if ( t641 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(saf) ( false )") ;
end if;
 
assign t642 = saf.r11bfalse or saf.r11btrue ;
if ( t642 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(saf) ( true )") ;
end if;
 
assign t643 = saf.r11btrue or saf.r11bfalse ;
if ( t643 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(saf) ( true )") ;
end if;
 
assign t644 = saf.r11btrue or saf.r11btrue ;
if ( t644 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(saf) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.referential attribute (smf)
for each smf in smfs
assign t645 = saf.r11bfalse or smf.r11bfalse ;
if ( t645 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(smf) ( false )") ;
end if;
 
assign t646 = saf.r11bfalse or smf.r11btrue ;
if ( t646 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(smf) ( true )") ;
end if;
 
assign t647 = saf.r11btrue or smf.r11bfalse ;
if ( t647 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(smf) ( true )") ;
end if;
 
assign t648 = saf.r11btrue or smf.r11btrue ;
if ( t648 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // other.referential_attribute(saf)    other.referential attribute (sor)
assign t649 = saf.r11bfalse or sor.r11bfalse ;
if ( t649 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(sor) ( false )") ;
end if;
 
assign t650 = saf.r11bfalse or sor.r11btrue ;
if ( t650 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(sor) ( true )") ;
end if;
 
assign t651 = saf.r11btrue or sor.r11bfalse ;
if ( t651 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(sor) ( true )") ;
end if;
 
assign t652 = saf.r11btrue or sor.r11btrue ;
if ( t652 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(sor) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.referential attribute (sar)
assign t653 = saf.r11bfalse or sar.r11bfalse ;
if ( t653 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(sar) ( false )") ;
end if;
 
assign t654 = saf.r11bfalse or sar.r11btrue ;
if ( t654 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(sar) ( true )") ;
end if;
 
assign t655 = saf.r11btrue or sar.r11bfalse ;
if ( t655 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(sar) ( true )") ;
end if;
 
assign t656 = saf.r11btrue or sar.r11btrue ;
if ( t656 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(sar) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.referential attribute (smr)
for each smr in smrs
assign t657 = saf.r11bfalse or smr.r11bfalse ;
if ( t657 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(smr) ( false )") ;
end if;
 
assign t658 = saf.r11bfalse or smr.r11btrue ;
if ( t658 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(smr) ( true )") ;
end if;
 
assign t659 = saf.r11btrue or smr.r11bfalse ;
if ( t659 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(smr) ( true )") ;
end if;
 
assign t660 = saf.r11btrue or smr.r11btrue ;
if ( t660 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(smr) ( true )") ;
end if;
 
end for;
  // other.referential attribute (saf)    constant
  // other.referential attribute (saf)    constant
  // other.referential attribute (saf)    self.attribute
  // other.referential attribute (saf)    self.referential attribute
  // other.referential attribute (saf)    other.attribute (saf)
  // other.referential attribute (saf)    other.attribute (smf)
  // other.referential attribute (saf)    other.attribute (sor)
  // other.referential attribute (saf)    other.attribute (sar)
  // other.referential attribute (saf)    other.attribute (smr)
  // other.referential attribute (saf)    other.referential attribute (saf)
  // other.referential attribute (saf)    other.referential attribute (smf)
  // other.referential attribute (saf)    other.referential attribute (sor)
  // other.referential attribute (saf)    other.referential attribute (sar)
  // other.referential attribute (saf)    other.referential attribute (smr)
  // other.referential attribute (smf)    local existing
  // other.referential attribute (smf)    constant
  // other.referential attribute (smf)    constant
  // other.referential attribute (smf)    self.attribute
  // other.referential attribute (smf)    self.referential attribute
  // other.referential attribute (smf)    other.attribute (saf)
  // other.referential attribute (smf)    other.attribute (smf)
  // other.referential attribute (smf)    other.attribute (sor)
  // other.referential attribute (smf)    other.attribute (sar)
  // other.referential attribute (smf)    other.attribute (smr)
  // other.referential attribute (smf)    other.referential attribute (saf)
  // other.referential attribute (smf)    other.referential attribute (smf)
  // other.referential attribute (smf)    other.referential attribute (sor)
  // other.referential attribute (smf)    other.referential attribute (sar)
  // other.referential attribute (smf)    other.referential attribute (smr)
  // other.referential attribute (sor)    local existing
  // other.referential attribute (sor)    constant
  // other.referential attribute (sor)    constant
  // other.referential attribute (sor)    self.attribute
  // other.referential attribute (sor)    self.referential attribute
  // other.referential attribute (sor)    other.attribute (saf)
  // other.referential attribute (sor)    other.attribute (smf)
  // other.referential attribute (sor)    other.attribute (sor)
  // other.referential attribute (sor)    other.attribute (sar)
  // other.referential attribute (sor)    other.attribute (smr)
  // other.referential attribute (sor)    other.referential attribute (saf)
  // other.referential attribute (sor)    other.referential attribute (smf)
  // other.referential attribute (sor)    other.referential attribute (sor)
  // other.referential attribute (sor)    other.referential attribute (sar)
  // other.referential attribute (sor)    other.referential attribute (smr)
  // other.referential attribute (sar)    local existing
  // other.referential attribute (sar)    constant
  // other.referential attribute (sar)    constant
  // other.referential attribute (sar)    self.attribute
  // other.referential attribute (sar)    self.referential attribute
  // other.referential attribute (sar)    other.attribute (saf)
  // other.referential attribute (sar)    other.attribute (smf)
  // other.referential attribute (sar)    other.attribute (sor)
  // other.referential attribute (sar)    other.attribute (sar)
  // other.referential attribute (sar)    other.attribute (smr)
  // other.referential attribute (sar)    other.referential attribute (saf)
  // other.referential attribute (sar)    other.referential attribute (smf)
  // other.referential attribute (sar)    other.referential attribute (sor)
  // other.referential attribute (sar)    other.referential attribute (sar)
  // other.referential attribute (sar)    other.referential attribute (smr)
  // other.referential attribute (sar)    local existing
  // other.referential attribute (smr)    constant
  // other.referential attribute (smr)    constant
  // other.referential attribute (smr)    self.attribute
  // other.referential attribute (smr)    self.referential attribute
  // other.referential attribute (smr)    other.attribute (saf)
  // other.referential attribute (smr)    other.attribute (smf)
  // other.referential attribute (smr)    other.attribute (sor)
  // other.referential attribute (smr)    other.attribute (sar)
  // other.referential attribute (smr)    other.attribute (smr)
  // other.referential attribute (smr)    other.referential attribute (saf)
  // other.referential attribute (smr)    other.referential attribute (smf)
  // other.referential attribute (smr)    other.referential attribute (sor)
  // other.referential attribute (smr)    other.referential attribute (sar)
  // other.referential attribute (smr)    other.referential attribute (smr)
 
LOG::LogInfo(message:"Completed or test") ;

select any ev from instances of EV;
generate EV2:''shutdown'' to ev;
 

',
	'');
INSERT INTO SM_STATE
	VALUES (2621445,
	2621445,
	0,
	'And test 1b',
	2,
	0);
INSERT INTO SM_CH
	VALUES (2621445,
	2621441,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621445,
	2621441,
	2621445,
	0);
INSERT INTO SM_CH
	VALUES (2621445,
	2621442,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621445,
	2621442,
	2621445,
	0);
INSERT INTO SM_SEME
	VALUES (2621445,
	2621443,
	2621445,
	0);
INSERT INTO SM_CH
	VALUES (2621445,
	2621444,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621445,
	2621444,
	2621445,
	0);
INSERT INTO SM_MOAH
	VALUES (2621445,
	2621445,
	2621445);
INSERT INTO SM_AH
	VALUES (2621445,
	2621445);
INSERT INTO SM_ACT
	VALUES (2621445,
	2621445,
	1,
	'assign temp1 = false;
assign temp2 = true;

select any saf from instances of OBET;
select many smfs from instances of OBET;
select one sor related by self->OBET[R9];
select any sar related by self->OBET[R10];
select many smrs related by self->OBET[R10];

// BridgePoint 3.2 can''t parse expressions for booleans of the type:
//  assign x = true and temp1;
// so tests 121 - 180 aren''t implemented

  // self.attribute    local existing
assign t181 = self.bfalse and temp1 ;
if ( t181 == false )
  LOG::LogSuccess(message:" and self.attribute local_existing ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute local_existing ( false )") ;
end if;
 
assign t182 = self.btrue and temp1 ;
if ( t182 == false )
  LOG::LogSuccess(message:" and self.attribute local_existing ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute local_existing ( false )") ;
end if;
 
assign t183 = self.bfalse and temp2 ;
if ( t183 == false )
  LOG::LogSuccess(message:" and self.attribute local_existing ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute local_existing ( false )") ;
end if;
 
assign t184 = self.btrue and temp2 ;
if ( t184 == true )
  LOG::LogSuccess(message:" and self.attribute local_existing ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute local_existing ( true )") ;
end if;
 
  // self.attribute   rcvd_evt 
assign t185 = self.bfalse and rcvd_evt.tfalse ;
if ( t185 == false )
  LOG::LogSuccess(message:" and self.attribute rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute rcvd_evt ( false )") ;
end if;
 
assign t186 = self.btrue and rcvd_evt.tfalse ;
if ( t186 == false )
  LOG::LogSuccess(message:" and self.attribute rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute rcvd_evt ( false )") ;
end if;
 
assign t187 = self.bfalse and rcvd_evt.ttrue ;
if ( t187 == false )
  LOG::LogSuccess(message:" and self.attribute rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute rcvd_evt ( false )") ;
end if;
 
assign t188 = self.btrue and rcvd_evt.ttrue ;
if ( t188 == true )
  LOG::LogSuccess(message:" and self.attribute rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute rcvd_evt ( true )") ;
end if;
 
  // self.attribute    constant
assign t189 = self.bfalse and false ;
if ( t189 == false )
  LOG::LogSuccess(message:" and self.attribute constant ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute constant ( false )") ;
end if;
 
assign t190 = self.btrue and false ;
if ( t190 == false )
  LOG::LogSuccess(message:" and self.attribute constant ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute constant ( false )") ;
end if;
 
assign t191 = self.bfalse and true ;
if ( t191 == false )
  LOG::LogSuccess(message:" and self.attribute constant ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute constant ( false )") ;
end if;
 
assign t192 = self.btrue and true ;
if ( t192 == true )
  LOG::LogSuccess(message:" and self.attribute constant ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute constant ( true )") ;
end if;
 
  // self.attribute    self.attribute
assign t193 = self.bfalse and self.bfalse ;
if ( t193 == false )
  LOG::LogSuccess(message:" and self.attribute self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute self.attribute ( false )") ;
end if;
 
assign t194 = self.btrue and self.bfalse ;
if ( t194 == false )
  LOG::LogSuccess(message:" and self.attribute self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute self.attribute ( false )") ;
end if;
 
assign t195 = self.bfalse and self.btrue ;
if ( t195 == false )
  LOG::LogSuccess(message:" and self.attribute self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute self.attribute ( false )") ;
end if;
 
assign t196 = self.btrue and self.btrue ;
if ( t196 == true )
  LOG::LogSuccess(message:" and self.attribute self.attribute ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute self.attribute ( true )") ;
end if;
 
  // self.attribute    self.referential attribute
assign t197 = self.bfalse and self.r9bfalse ;
if ( t197 == false )
  LOG::LogSuccess(message:" and self.attribute self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute self.referential_attribute ( false )") ;
end if;
 
assign t198 = self.btrue and self.r9bfalse ;
if ( t198 == false )
  LOG::LogSuccess(message:" and self.attribute self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute self.referential_attribute ( false )") ;
end if;
 
assign t199 = self.bfalse and self.r9btrue ;
if ( t199 == false )
  LOG::LogSuccess(message:" and self.attribute self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute self.referential_attribute ( false )") ;
end if;
 
assign t200 = self.btrue and self.r9btrue ;
if ( t200 == true )
  LOG::LogSuccess(message:" and self.attribute self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute self.referential_attribute ( true )") ;
end if;
 
  // self.attribute    other.attribute (saf)
assign t201 = self.bfalse and saf.bfalse ;
if ( t201 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(saf) ( false )") ;
end if;
 
assign t202 = self.bfalse and saf.btrue ;
if ( t202 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(saf) ( false )") ;
end if;
 
assign t203 = self.btrue and saf.bfalse ;
if ( t203 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(saf) ( false )") ;
end if;
 
assign t204 = self.btrue and saf.btrue ;
if ( t204 == true )
  LOG::LogSuccess(message:" and self.attribute other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(saf) ( true )") ;
end if;
 
  // self.attribute    other.attribute (smf)
for each smf in smfs
assign t205 = self.bfalse and smf.bfalse ;
if ( t205 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(smf) ( false )") ;
end if;
 
assign t206 = self.bfalse and smf.btrue ;
if ( t206 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(smf) ( false )") ;
end if;
 
assign t207 = self.btrue and smf.bfalse ;
if ( t207 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(smf) ( false )") ;
end if;
 
assign t208 = self.btrue and smf.btrue ;
if ( t208 == true )
  LOG::LogSuccess(message:" and self.attribute other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(smf) ( true )") ;
end if;
 
end for;
  // self.attribute    other.attribute (sor)
assign t209 = self.bfalse and sor.bfalse ;
if ( t209 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(sor) ( false )") ;
end if;
 
assign t210 = self.bfalse and sor.btrue ;
if ( t210 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(sor) ( false )") ;
end if;
 
assign t211 = self.btrue and sor.bfalse ;
if ( t211 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(sor) ( false )") ;
end if;
 
assign t212 = self.btrue and sor.btrue ;
if ( t212 == true )
  LOG::LogSuccess(message:" and self.attribute other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(sor) ( true )") ;
end if;
 
  // self.attribute    other.attribute (sar)
assign t213 = self.bfalse and sar.bfalse ;
if ( t213 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(sar) ( false )") ;
end if;
 
assign t214 = self.bfalse and sar.btrue ;
if ( t214 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(sar) ( false )") ;
end if;
 
assign t215 = self.btrue and sar.bfalse ;
if ( t215 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(sar) ( false )") ;
end if;
 
assign t216 = self.btrue and sar.btrue ;
if ( t216 == true )
  LOG::LogSuccess(message:" and self.attribute other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(sar) ( true )") ;
end if;
 
  // self.attribute    other.attribute (smr)
for each smr in smrs
assign t217 = self.bfalse and smr.bfalse ;
if ( t217 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(smr) ( false )") ;
end if;
 
assign t218 = self.bfalse and smr.btrue ;
if ( t218 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(smr) ( false )") ;
end if;
 
assign t219 = self.btrue and smr.bfalse ;
if ( t219 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(smr) ( false )") ;
end if;
 
assign t220 = self.btrue and smr.btrue ;
if ( t220 == true )
  LOG::LogSuccess(message:" and self.attribute other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(smr) ( true )") ;
end if;
 
end for;
  // self.attribute    other.referential attribute (saf)
assign t221 = self.bfalse and saf.r11bfalse ;
if ( t221 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(saf) ( false )") ;
end if;
 
assign t222 = self.bfalse and saf.r11btrue ;
if ( t222 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(saf) ( false )") ;
end if;
 
assign t223 = self.btrue and saf.r11bfalse ;
if ( t223 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(saf) ( false )") ;
end if;
 
assign t224 = self.btrue and saf.r11btrue ;
if ( t224 == true )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(saf) ( true )") ;
end if;
 
  // self.attribute    other.referential attribute (smf)
for each smf in smfs
assign t225 = self.bfalse and smf.r11bfalse ;
if ( t225 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(smf) ( false )") ;
end if;
 
assign t226 = self.bfalse and smf.r11btrue ;
if ( t226 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(smf) ( false )") ;
end if;
 
assign t227 = self.btrue and smf.r11bfalse ;
if ( t227 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(smf) ( false )") ;
end if;
 
assign t228 = self.btrue and smf.r11btrue ;
if ( t228 == true )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // self.attribute    other.referential attribute (sor)
assign t229 = self.bfalse and sor.r11bfalse ;
if ( t229 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(sor) ( false )") ;
end if;
 
assign t230 = self.bfalse and sor.r11btrue ;
if ( t230 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(sor) ( false )") ;
end if;
 
assign t231 = self.btrue and sor.r11bfalse ;
if ( t231 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(sor) ( false )") ;
end if;
 
assign t232 = self.btrue and sor.r11btrue ;
if ( t232 == true )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(sor) ( true )") ;
end if;
 
  // self.attribute    other.referential attribute (sar)
assign t233 = self.bfalse and sar.r11bfalse ;
if ( t233 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(sar) ( false )") ;
end if;
 
assign t234 = self.bfalse and sar.r11btrue ;
if ( t234 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(sar) ( false )") ;
end if;
 
assign t235 = self.btrue and sar.r11bfalse ;
if ( t235 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(sar) ( false )") ;
end if;
 
assign t236 = self.btrue and sar.r11btrue ;
if ( t236 == true )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(sar) ( true )") ;
end if;
 
  // self.attribute    other.referential attribute (smr)
for each smr in smrs
assign t237 = self.bfalse and smr.r11bfalse ;
if ( t237 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(smr) ( false )") ;
end if;
 
assign t238 = self.bfalse and smr.r11btrue ;
if ( t238 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(smr) ( false )") ;
end if;
 
assign t239 = self.btrue and smr.r11bfalse ;
if ( t239 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(smr) ( false )") ;
end if;
 
assign t240 = self.btrue and smr.r11btrue ;
if ( t240 == true )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(smr) ( true )") ;
end if;
 
end for;


  // self.referential attribute    local existing
assign t241 = self.r9bfalse and temp1 ;
if ( t241 == false )
  LOG::LogSuccess(message:" and self.referential_attribute local_existing ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute local_existing ( false )") ;
end if;
 
assign t242 = self.r9btrue and temp1 ;
if ( t242 == false )
  LOG::LogSuccess(message:" and self.referential_attribute local_existing ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute local_existing ( false )") ;
end if;
 
assign t243 = self.r9bfalse and temp2 ;
if ( t243 == false )
  LOG::LogSuccess(message:" and self.referential_attribute local_existing ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute local_existing ( false )") ;
end if;
 
assign t244 = self.r9btrue and temp2 ;
if ( t244 == true )
  LOG::LogSuccess(message:" and self.referential_attribute local_existing ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute local_existing ( true )") ;
end if;
 
  // self.referential_attribute   rcvd_evt 
assign t245 = self.r9bfalse and rcvd_evt.tfalse ;
if ( t245 == false )
  LOG::LogSuccess(message:" and self.referential_attribute rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute rcvd_evt ( false )") ;
end if;
 
assign t246 = self.r9btrue and rcvd_evt.tfalse ;
if ( t246 == false )
  LOG::LogSuccess(message:" and self.referential_attribute rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute rcvd_evt ( false )") ;
end if;
 
assign t247 = self.r9bfalse and rcvd_evt.ttrue ;
if ( t247 == false )
  LOG::LogSuccess(message:" and self.referential_attribute rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute rcvd_evt ( false )") ;
end if;
 
assign t248 = self.r9btrue and rcvd_evt.ttrue ;
if ( t248 == true )
  LOG::LogSuccess(message:" and self.referential_attribute rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute rcvd_evt ( true )") ;
end if;
 
  // self.referential_attribute    constant
assign t249 = self.r9bfalse and false ;
if ( t249 == false )
  LOG::LogSuccess(message:" and self.referential_attribute constant ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute constant ( false )") ;
end if;
 
assign t250 = self.r9btrue and false ;
if ( t250 == false )
  LOG::LogSuccess(message:" and self.referential_attribute constant ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute constant ( false )") ;
end if;
 
assign t251 = self.r9bfalse and true ;
if ( t251 == false )
  LOG::LogSuccess(message:" and self.referential_attribute constant ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute constant ( false )") ;
end if;
 
assign t252 = self.r9btrue and true ;
if ( t252 == true )
  LOG::LogSuccess(message:" and self.referential_attribute constant ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute constant ( true )") ;
end if;
 
  // self.referential_attribute    self.attribute
assign t253 = self.r9bfalse and self.bfalse ;
if ( t253 == false )
  LOG::LogSuccess(message:" and self.referential_attribute self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute self.attribute ( false )") ;
end if;
 
assign t254 = self.r9btrue and self.bfalse ;
if ( t254 == false )
  LOG::LogSuccess(message:" and self.referential_attribute self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute self.attribute ( false )") ;
end if;
 
assign t255 = self.r9bfalse and self.btrue ;
if ( t255 == false )
  LOG::LogSuccess(message:" and self.referential_attribute self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute self.attribute ( false )") ;
end if;
 
assign t256 = self.r9btrue and self.btrue ;
if ( t256 == true )
  LOG::LogSuccess(message:" and self.referential_attribute self.attribute ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute self.attribute ( true )") ;
end if;
 
  // self.referential_attribute    self.referential attribute
assign t257 = self.r9bfalse and self.r9bfalse ;
if ( t257 == false )
  LOG::LogSuccess(message:" and self.referential_attribute self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute self.referential_attribute ( false )") ;
end if;
 
assign t258 = self.r9btrue and self.r9bfalse ;
if ( t258 == false )
  LOG::LogSuccess(message:" and self.referential_attribute self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute self.referential_attribute ( false )") ;
end if;
 
assign t259 = self.r9bfalse and self.r9btrue ;
if ( t259 == false )
  LOG::LogSuccess(message:" and self.referential_attribute self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute self.referential_attribute ( false )") ;
end if;
 
assign t260 = self.r9btrue and self.r9btrue ;
if ( t260 == true )
  LOG::LogSuccess(message:" and self.referential_attribute self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute self.referential_attribute ( true )") ;
end if;
 
  // self.referential_attribute    other.attribute (saf)
assign t261 = self.r9bfalse and saf.bfalse ;
if ( t261 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(saf) ( false )") ;
end if;
 
assign t262 = self.r9bfalse and saf.btrue ;
if ( t262 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(saf) ( false )") ;
end if;
 
assign t263 = self.r9btrue and saf.bfalse ;
if ( t263 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(saf) ( false )") ;
end if;
 
assign t264 = self.r9btrue and saf.btrue ;
if ( t264 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(saf) ( true )") ;
end if;
 
  // self.referential_attribute    other.attribute (smf)
for each smf in smfs
assign t265 = self.r9bfalse and smf.bfalse ;
if ( t265 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(smf) ( false )") ;
end if;
 
assign t266 = self.r9bfalse and smf.btrue ;
if ( t266 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(smf) ( false )") ;
end if;
 
assign t267 = self.r9btrue and smf.bfalse ;
if ( t267 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(smf) ( false )") ;
end if;
 
assign t268 = self.r9btrue and smf.btrue ;
if ( t268 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(smf) ( true )") ;
end if;
 
end for;
  // self.referential_attribute    other.attribute (sor)
assign t269 = self.r9bfalse and sor.bfalse ;
if ( t269 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(sor) ( false )") ;
end if;
 
assign t270 = self.r9bfalse and sor.btrue ;
if ( t270 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(sor) ( false )") ;
end if;
 
assign t271 = self.r9btrue and sor.bfalse ;
if ( t271 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(sor) ( false )") ;
end if;
 
assign t272 = self.r9btrue and sor.btrue ;
if ( t272 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(sor) ( true )") ;
end if;
 
  // self.referential_attribute    other.attribute (sar)
assign t273 = self.r9bfalse and sar.bfalse ;
if ( t273 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(sar) ( false )") ;
end if;
 
assign t274 = self.r9bfalse and sar.btrue ;
if ( t274 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(sar) ( false )") ;
end if;
 
assign t275 = self.r9btrue and sar.bfalse ;
if ( t275 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(sar) ( false )") ;
end if;
 
assign t276 = self.r9btrue and sar.btrue ;
if ( t276 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(sar) ( true )") ;
end if;
 
  // self.referential_attribute    other.attribute (smr)
for each smr in smrs
assign t277 = self.r9bfalse and smr.bfalse ;
if ( t277 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(smr) ( false )") ;
end if;
 
assign t278 = self.r9bfalse and smr.btrue ;
if ( t278 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(smr) ( false )") ;
end if;
 
assign t279 = self.r9btrue and smr.bfalse ;
if ( t279 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(smr) ( false )") ;
end if;
 
assign t280 = self.r9btrue and smr.btrue ;
if ( t280 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(smr) ( true )") ;
end if;
 
end for;
  // self.referential_attribute    other.referential attribute (saf)
assign t281 = self.r9bfalse and saf.r11bfalse ;
if ( t281 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(saf) ( false )") ;
end if;
 
assign t282 = self.r9bfalse and saf.r11btrue ;
if ( t282 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(saf) ( false )") ;
end if;
 
assign t283 = self.r9btrue and saf.r11bfalse ;
if ( t283 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(saf) ( false )") ;
end if;
 
assign t284 = self.r9btrue and saf.r11btrue ;
if ( t284 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(saf) ( true )") ;
end if;
 
  // self.referential_attribute    other.referential attribute (smf)
for each smf in smfs
assign t285 = self.r9bfalse and smf.r11bfalse ;
if ( t285 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(smf) ( false )") ;
end if;
 
assign t286 = self.r9bfalse and smf.r11btrue ;
if ( t286 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(smf) ( false )") ;
end if;
 
assign t287 = self.r9btrue and smf.r11bfalse ;
if ( t287 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(smf) ( false )") ;
end if;
 
assign t288 = self.r9btrue and smf.r11btrue ;
if ( t288 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // self.referential_attribute    other.referential attribute (sor)
assign t289 = self.r9bfalse and sor.r11bfalse ;
if ( t289 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(sor) ( false )") ;
end if;
 
assign t290 = self.r9bfalse and sor.r11btrue ;
if ( t290 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(sor) ( false )") ;
end if;
 
assign t291 = self.r9btrue and sor.r11bfalse ;
if ( t291 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(sor) ( false )") ;
end if;
 
assign t292 = self.r9btrue and sor.r11btrue ;
if ( t292 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(sor) ( true )") ;
end if;
 
  // self.referential_attribute    other.referential attribute (sar)
assign t293 = self.r9bfalse and sar.r11bfalse ;
if ( t293 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(sar) ( false )") ;
end if;
 
assign t294 = self.r9bfalse and sar.r11btrue ;
if ( t294 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(sar) ( false )") ;
end if;
 
assign t295 = self.r9btrue and sar.r11bfalse ;
if ( t295 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(sar) ( false )") ;
end if;
 
assign t296 = self.r9btrue and sar.r11btrue ;
if ( t296 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(sar) ( true )") ;
end if;
 
  // self.referential_attribute    other.referential attribute (smr)
for each smr in smrs
assign t297 = self.r9bfalse and smr.r11bfalse ;
if ( t297 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(smr) ( false )") ;
end if;
 
assign t298 = self.r9bfalse and smr.r11btrue ;
if ( t298 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(smr) ( false )") ;
end if;
 
assign t299 = self.r9btrue and smr.r11bfalse ;
if ( t299 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(smr) ( false )") ;
end if;
 
assign t300 = self.r9btrue and smr.r11btrue ;
if ( t300 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(smr) ( true )") ;
end if;
 
end for;

 
//generate event to continue test
Generate BET2:''Continue and test''( ttrue: true, tfalse: false ) to self; 

',
	'');
INSERT INTO SM_STATE
	VALUES (2621446,
	2621445,
	0,
	'Or test 1b',
	5,
	0);
INSERT INTO SM_CH
	VALUES (2621446,
	2621441,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621446,
	2621441,
	2621445,
	0);
INSERT INTO SM_CH
	VALUES (2621446,
	2621442,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621446,
	2621442,
	2621445,
	0);
INSERT INTO SM_CH
	VALUES (2621446,
	2621443,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621446,
	2621443,
	2621445,
	0);
INSERT INTO SM_SEME
	VALUES (2621446,
	2621444,
	2621445,
	0);
INSERT INTO SM_MOAH
	VALUES (2621446,
	2621445,
	2621446);
INSERT INTO SM_AH
	VALUES (2621446,
	2621445);
INSERT INTO SM_ACT
	VALUES (2621446,
	2621445,
	1,
	'// 1.3
// set my logging instance

assign temp1 = false;
assign temp2 = true;

select any saf from instances of OBET;
select many smfs from instances of OBET;
select one sor related by self->OBET[R9];
select any sar related by self->OBET[R10];
select many smrs related by self->OBET[R10];

// BridgePoint 3.2 can''t parse expressions for booleans of the type:
//  assign x = true or temp1;
// so tests 121 - 180 aren''t implemented

  // self.attribute    local existing
assign t181 = self.bfalse or temp1 ;
if ( t181 == false )
  LOG::LogSuccess(message:" or self.attribute local_existing ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute local_existing ( false )") ;
end if;
 
assign t182 = self.btrue or temp1 ;
if ( t182 == true )
  LOG::LogSuccess(message:" or self.attribute local_existing ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute local_existing ( true )") ;
end if;
 
assign t183 = self.bfalse or temp2 ;
if ( t183 == true )
  LOG::LogSuccess(message:" or self.attribute local_existing ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute local_existing ( true )") ;
end if;
 
assign t184 = self.btrue or temp2 ;
if ( t184 == true )
  LOG::LogSuccess(message:" or self.attribute local_existing ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute local_existing ( true )") ;
end if;
 
  // self.attribute   rcvd_evt 
assign t185 = self.bfalse or rcvd_evt.tfalse ;
if ( t185 == false )
  LOG::LogSuccess(message:" or self.attribute rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute rcvd_evt ( false )") ;
end if;
 
assign t186 = self.btrue or rcvd_evt.tfalse ;
if ( t186 == true )
  LOG::LogSuccess(message:" or self.attribute rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute rcvd_evt ( true )") ;
end if;
 
assign t187 = self.bfalse or rcvd_evt.ttrue ;
if ( t187 == true )
  LOG::LogSuccess(message:" or self.attribute rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute rcvd_evt ( true )") ;
end if;
 
assign t188 = self.btrue or rcvd_evt.ttrue ;
if ( t188 == true )
  LOG::LogSuccess(message:" or self.attribute rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute rcvd_evt ( true )") ;
end if;
 
  // self.attribute    constant
assign t189 = self.bfalse or false ;
if ( t189 == false )
  LOG::LogSuccess(message:" or self.attribute constant ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute constant ( false )") ;
end if;
 
assign t190 = self.btrue or false ;
if ( t190 == true )
  LOG::LogSuccess(message:" or self.attribute constant ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute constant ( true )") ;
end if;
 
assign t191 = self.bfalse or true ;
if ( t191 == true )
  LOG::LogSuccess(message:" or self.attribute constant ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute constant ( true )") ;
end if;
 
assign t192 = self.btrue or true ;
if ( t192 == true )
  LOG::LogSuccess(message:" or self.attribute constant ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute constant ( true )") ;
end if;
 
  // self.attribute    self.attribute
assign t193 = self.bfalse or self.bfalse ;
if ( t193 == false )
  LOG::LogSuccess(message:" or self.attribute self.attribute ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute self.attribute ( false )") ;
end if;
 
assign t194 = self.btrue or self.bfalse ;
if ( t194 == true )
  LOG::LogSuccess(message:" or self.attribute self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute self.attribute ( true )") ;
end if;
 
assign t195 = self.bfalse or self.btrue ;
if ( t195 == true )
  LOG::LogSuccess(message:" or self.attribute self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute self.attribute ( true )") ;
end if;
 
assign t196 = self.btrue or self.btrue ;
if ( t196 == true )
  LOG::LogSuccess(message:" or self.attribute self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute self.attribute ( true )") ;
end if;
 
  // self.attribute    self.referential attribute
assign t197 = self.bfalse or self.r9bfalse ;
if ( t197 == false )
  LOG::LogSuccess(message:" or self.attribute self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute self.referential_attribute ( false )") ;
end if;
 
assign t198 = self.btrue or self.r9bfalse ;
if ( t198 == true )
  LOG::LogSuccess(message:" or self.attribute self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute self.referential_attribute ( true )") ;
end if;
 
assign t199 = self.bfalse or self.r9btrue ;
if ( t199 == true )
  LOG::LogSuccess(message:" or self.attribute self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute self.referential_attribute ( true )") ;
end if;
 
assign t200 = self.btrue or self.r9btrue ;
if ( t200 == true )
  LOG::LogSuccess(message:" or self.attribute self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute self.referential_attribute ( true )") ;
end if;
 
  // self.attribute    other.attribute (saf)
assign t201 = self.bfalse or saf.bfalse ;
if ( t201 == false )
  LOG::LogSuccess(message:" or self.attribute other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(saf) ( false )") ;
end if;
 
assign t202 = self.bfalse or saf.btrue ;
if ( t202 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(saf) ( true )") ;
end if;
 
assign t203 = self.btrue or saf.bfalse ;
if ( t203 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(saf) ( true )") ;
end if;
 
assign t204 = self.btrue or saf.btrue ;
if ( t204 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(saf) ( true )") ;
end if;
 
  // self.attribute    other.attribute (smf)
for each smf in smfs
assign t205 = self.bfalse or smf.bfalse ;
if ( t205 == false )
  LOG::LogSuccess(message:" or self.attribute other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(smf) ( false )") ;
end if;
 
assign t206 = self.bfalse or smf.btrue ;
if ( t206 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(smf) ( true )") ;
end if;
 
assign t207 = self.btrue or smf.bfalse ;
if ( t207 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(smf) ( true )") ;
end if;
 
assign t208 = self.btrue or smf.btrue ;
if ( t208 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(smf) ( true )") ;
end if;
 
end for;
  // self.attribute    other.attribute (sor)
assign t209 = self.bfalse or sor.bfalse ;
if ( t209 == false )
  LOG::LogSuccess(message:" or self.attribute other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(sor) ( false )") ;
end if;
 
assign t210 = self.bfalse or sor.btrue ;
if ( t210 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(sor) ( true )") ;
end if;
 
assign t211 = self.btrue or sor.bfalse ;
if ( t211 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(sor) ( true )") ;
end if;
 
assign t212 = self.btrue or sor.btrue ;
if ( t212 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(sor) ( true )") ;
end if;
 
  // self.attribute    other.attribute (sar)
assign t213 = self.bfalse or sar.bfalse ;
if ( t213 == false )
  LOG::LogSuccess(message:" or self.attribute other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(sar) ( false )") ;
end if;
 
assign t214 = self.bfalse or sar.btrue ;
if ( t214 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(sar) ( true )") ;
end if;
 
assign t215 = self.btrue or sar.bfalse ;
if ( t215 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(sar) ( true )") ;
end if;
 
assign t216 = self.btrue or sar.btrue ;
if ( t216 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(sar) ( true )") ;
end if;
 
  // self.attribute    other.attribute (smr)
for each smr in smrs
assign t217 = self.bfalse or smr.bfalse ;
if ( t217 == false )
  LOG::LogSuccess(message:" or self.attribute other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(smr) ( false )") ;
end if;
 
assign t218 = self.bfalse or smr.btrue ;
if ( t218 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(smr) ( true )") ;
end if;
 
assign t219 = self.btrue or smr.bfalse ;
if ( t219 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(smr) ( true )") ;
end if;
 
assign t220 = self.btrue or smr.btrue ;
if ( t220 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(smr) ( true )") ;
end if;
 
end for;
  // self.attribute    other.referential attribute (saf)
assign t221 = self.bfalse or saf.r11bfalse ;
if ( t221 == false )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(saf) ( false )") ;
end if;
 
assign t222 = self.bfalse or saf.r11btrue ;
if ( t222 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(saf) ( true )") ;
end if;
 
assign t223 = self.btrue or saf.r11bfalse ;
if ( t223 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(saf) ( true )") ;
end if;
 
assign t224 = self.btrue or saf.r11btrue ;
if ( t224 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(saf) ( true )") ;
end if;
 
  // self.attribute    other.referential attribute (smf)
for each smf in smfs
assign t225 = self.bfalse or smf.r11bfalse ;
if ( t225 == false )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(smf) ( false )") ;
end if;
 
assign t226 = self.bfalse or smf.r11btrue ;
if ( t226 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(smf) ( true )") ;
end if;
 
assign t227 = self.btrue or smf.r11bfalse ;
if ( t227 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(smf) ( true )") ;
end if;
 
assign t228 = self.btrue or smf.r11btrue ;
if ( t228 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // self.attribute    other.referential attribute (sor)
assign t229 = self.bfalse or sor.r11bfalse ;
if ( t229 == false )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(sor) ( false )") ;
end if;
 
assign t230 = self.bfalse or sor.r11btrue ;
if ( t230 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(sor) ( true )") ;
end if;
 
assign t231 = self.btrue or sor.r11bfalse ;
if ( t231 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(sor) ( true )") ;
end if;
 
assign t232 = self.btrue or sor.r11btrue ;
if ( t232 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(sor) ( true )") ;
end if;
 
  // self.attribute    other.referential attribute (sar)
assign t233 = self.bfalse or sar.r11bfalse ;
if ( t233 == false )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(sar) ( false )") ;
end if;
 
assign t234 = self.bfalse or sar.r11btrue ;
if ( t234 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(sar) ( true )") ;
end if;
 
assign t235 = self.btrue or sar.r11bfalse ;
if ( t235 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(sar) ( true )") ;
end if;
 
assign t236 = self.btrue or sar.r11btrue ;
if ( t236 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(sar) ( true )") ;
end if;
 
  // self.attribute    other.referential attribute (smr)
for each smr in smrs
assign t237 = self.bfalse or smr.r11bfalse ;
if ( t237 == false )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(smr) ( false )") ;
end if;
 
assign t238 = self.bfalse or smr.r11btrue ;
if ( t238 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(smr) ( true )") ;
end if;
 
assign t239 = self.btrue or smr.r11bfalse ;
if ( t239 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(smr) ( true )") ;
end if;
 
assign t240 = self.btrue or smr.r11btrue ;
if ( t240 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(smr) ( true )") ;
end if;
 
end for;


  // self.referential attribute    local existing
assign t241 = self.r9bfalse or temp1 ;
if ( t241 == false )
  LOG::LogSuccess(message:" or self.referential_attribute local_existing ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute local_existing ( false )") ;
end if;
 
assign t242 = self.r9btrue or temp1 ;
if ( t242 == true )
  LOG::LogSuccess(message:" or self.referential_attribute local_existing ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute local_existing ( true )") ;
end if;
 
assign t243 = self.r9bfalse or temp2 ;
if ( t243 == true )
  LOG::LogSuccess(message:" or self.referential_attribute local_existing ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute local_existing ( true )") ;
end if;
 
assign t244 = self.r9btrue or temp2 ;
if ( t244 == true )
  LOG::LogSuccess(message:" or self.referential_attribute local_existing ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute local_existing ( true )") ;
end if;
 
  // self.referential_attribute   rcvd_evt 
assign t245 = self.r9bfalse or rcvd_evt.tfalse ;
if ( t245 == false )
  LOG::LogSuccess(message:" or self.referential_attribute rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute rcvd_evt ( false )") ;
end if;
 
assign t246 = self.r9btrue or rcvd_evt.tfalse ;
if ( t246 == true )
  LOG::LogSuccess(message:" or self.referential_attribute rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute rcvd_evt ( true )") ;
end if;
 
assign t247 = self.r9bfalse or rcvd_evt.ttrue ;
if ( t247 == true )
  LOG::LogSuccess(message:" or self.referential_attribute rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute rcvd_evt ( true )") ;
end if;
 
assign t248 = self.r9btrue or rcvd_evt.ttrue ;
if ( t248 == true )
  LOG::LogSuccess(message:" or self.referential_attribute rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute rcvd_evt ( true )") ;
end if;
 
  // self.referential_attribute    constant
assign t249 = self.r9bfalse or false ;
if ( t249 == false )
  LOG::LogSuccess(message:" or self.referential_attribute constant ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute constant ( false )") ;
end if;
 
assign t250 = self.r9btrue or false ;
if ( t250 == true )
  LOG::LogSuccess(message:" or self.referential_attribute constant ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute constant ( true )") ;
end if;
 
assign t251 = self.r9bfalse or true ;
if ( t251 == true )
  LOG::LogSuccess(message:" or self.referential_attribute constant ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute constant ( true )") ;
end if;
 
assign t252 = self.r9btrue or true ;
if ( t252 == true )
  LOG::LogSuccess(message:" or self.referential_attribute constant ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute constant ( true )") ;
end if;
 
  // self.referential_attribute    self.attribute
assign t253 = self.r9bfalse or self.bfalse ;
if ( t253 == false )
  LOG::LogSuccess(message:" or self.referential_attribute self.attribute ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute self.attribute ( false )") ;
end if;
 
assign t254 = self.r9btrue or self.bfalse ;
if ( t254 == true )
  LOG::LogSuccess(message:" or self.referential_attribute self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute self.attribute ( true )") ;
end if;
 
assign t255 = self.r9bfalse or self.btrue ;
if ( t255 == true )
  LOG::LogSuccess(message:" or self.referential_attribute self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute self.attribute ( true )") ;
end if;
 
assign t256 = self.r9btrue or self.btrue ;
if ( t256 == true )
  LOG::LogSuccess(message:" or self.referential_attribute self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute self.attribute ( true )") ;
end if;
 
  // self.referential_attribute    self.referential attribute
assign t257 = self.r9bfalse or self.r9bfalse ;
if ( t257 == false )
  LOG::LogSuccess(message:" or self.referential_attribute self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute self.referential_attribute ( false )") ;
end if;
 
assign t258 = self.r9btrue or self.r9bfalse ;
if ( t258 == true )
  LOG::LogSuccess(message:" or self.referential_attribute self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute self.referential_attribute ( true )") ;
end if;
 
assign t259 = self.r9bfalse or self.r9btrue ;
if ( t259 == true )
  LOG::LogSuccess(message:" or self.referential_attribute self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute self.referential_attribute ( true )") ;
end if;
 
assign t260 = self.r9btrue or self.r9btrue ;
if ( t260 == true )
  LOG::LogSuccess(message:" or self.referential_attribute self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute self.referential_attribute ( true )") ;
end if;
 
  // self.referential_attribute    other.attribute (saf)
assign t261 = self.r9bfalse or saf.bfalse ;
if ( t261 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(saf) ( false )") ;
end if;
 
assign t262 = self.r9bfalse or saf.btrue ;
if ( t262 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(saf) ( true )") ;
end if;
 
assign t263 = self.r9btrue or saf.bfalse ;
if ( t263 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(saf) ( true )") ;
end if;
 
assign t264 = self.r9btrue or saf.btrue ;
if ( t264 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(saf) ( true )") ;
end if;
 
  // self.referential_attribute    other.attribute (smf)
for each smf in smfs
assign t265 = self.r9bfalse or smf.bfalse ;
if ( t265 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(smf) ( false )") ;
end if;
 
assign t266 = self.r9bfalse or smf.btrue ;
if ( t266 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(smf) ( true )") ;
end if;
 
assign t267 = self.r9btrue or smf.bfalse ;
if ( t267 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(smf) ( true )") ;
end if;
 
assign t268 = self.r9btrue or smf.btrue ;
if ( t268 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(smf) ( true )") ;
end if;
 
end for;
  // self.referential_attribute    other.attribute (sor)
assign t269 = self.r9bfalse or sor.bfalse ;
if ( t269 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(sor) ( false )") ;
end if;
 
assign t270 = self.r9bfalse or sor.btrue ;
if ( t270 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(sor) ( true )") ;
end if;
 
assign t271 = self.r9btrue or sor.bfalse ;
if ( t271 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(sor) ( true )") ;
end if;
 
assign t272 = self.r9btrue or sor.btrue ;
if ( t272 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(sor) ( true )") ;
end if;
 
  // self.referential_attribute    other.attribute (sar)
assign t273 = self.r9bfalse or sar.bfalse ;
if ( t273 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(sar) ( false )") ;
end if;
 
assign t274 = self.r9bfalse or sar.btrue ;
if ( t274 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(sar) ( true )") ;
end if;
 
assign t275 = self.r9btrue or sar.bfalse ;
if ( t275 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(sar) ( true )") ;
end if;
 
assign t276 = self.r9btrue or sar.btrue ;
if ( t276 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(sar) ( true )") ;
end if;
 
  // self.referential_attribute    other.attribute (smr)
for each smr in smrs
assign t277 = self.r9bfalse or smr.bfalse ;
if ( t277 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(smr) ( false )") ;
end if;
 
assign t278 = self.r9bfalse or smr.btrue ;
if ( t278 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(smr) ( true )") ;
end if;
 
assign t279 = self.r9btrue or smr.bfalse ;
if ( t279 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(smr) ( true )") ;
end if;
 
assign t280 = self.r9btrue or smr.btrue ;
if ( t280 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(smr) ( true )") ;
end if;
 
end for;
  // self.referential_attribute    other.referential attribute (saf)
assign t281 = self.r9bfalse or saf.r11bfalse ;
if ( t281 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(saf) ( false )") ;
end if;
 
assign t282 = self.r9bfalse or saf.r11btrue ;
if ( t282 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(saf) ( true )") ;
end if;
 
assign t283 = self.r9btrue or saf.r11bfalse ;
if ( t283 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(saf) ( true )") ;
end if;
 
assign t284 = self.r9btrue or saf.r11btrue ;
if ( t284 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(saf) ( true )") ;
end if;
 
  // self.referential_attribute    other.referential attribute (smf)
for each smf in smfs
assign t285 = self.r9bfalse or smf.r11bfalse ;
if ( t285 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(smf) ( false )") ;
end if;
 
assign t286 = self.r9bfalse or smf.r11btrue ;
if ( t286 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(smf) ( true )") ;
end if;
 
assign t287 = self.r9btrue or smf.r11bfalse ;
if ( t287 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(smf) ( true )") ;
end if;
 
assign t288 = self.r9btrue or smf.r11btrue ;
if ( t288 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // self.referential_attribute    other.referential attribute (sor)
assign t289 = self.r9bfalse or sor.r11bfalse ;
if ( t289 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(sor) ( false )") ;
end if;
 
assign t290 = self.r9bfalse or sor.r11btrue ;
if ( t290 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(sor) ( true )") ;
end if;
 
assign t291 = self.r9btrue or sor.r11bfalse ;
if ( t291 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(sor) ( true )") ;
end if;
 
assign t292 = self.r9btrue or sor.r11btrue ;
if ( t292 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(sor) ( true )") ;
end if;
 
  // self.referential_attribute    other.referential attribute (sar)
assign t293 = self.r9bfalse or sar.r11bfalse ;
if ( t293 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(sar) ( false )") ;
end if;
 
assign t294 = self.r9bfalse or sar.r11btrue ;
if ( t294 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(sar) ( true )") ;
end if;
 
assign t295 = self.r9btrue or sar.r11bfalse ;
if ( t295 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(sar) ( true )") ;
end if;
 
assign t296 = self.r9btrue or sar.r11btrue ;
if ( t296 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(sar) ( true )") ;
end if;
 
  // self.referential_attribute    other.referential attribute (smr)
for each smr in smrs
assign t297 = self.r9bfalse or smr.r11bfalse ;
if ( t297 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(smr) ( false )") ;
end if;
 
assign t298 = self.r9bfalse or smr.r11btrue ;
if ( t298 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(smr) ( true )") ;
end if;
 
assign t299 = self.r9btrue or smr.r11bfalse ;
if ( t299 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(smr) ( true )") ;
end if;
 
assign t300 = self.r9btrue or smr.r11btrue ;
if ( t300 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(smr) ( true )") ;
end if;
 
end for;

 
//generate event to continue test
Generate BET4:''Continue or test''( ttrue: true, tfalse: false ) to self;


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
	2621442,
	2621442,
	0);
INSERT INTO SM_TXN
	VALUES (2621442,
	2621445,
	2621443,
	0);
INSERT INTO SM_NSTXN
	VALUES (2621443,
	2621445,
	2621445,
	2621443,
	0);
INSERT INTO SM_TXN
	VALUES (2621443,
	2621445,
	2621442,
	0);
INSERT INTO SM_NSTXN
	VALUES (2621444,
	2621445,
	2621441,
	2621443,
	0);
INSERT INTO SM_TXN
	VALUES (2621444,
	2621445,
	2621445,
	0);
INSERT INTO SM_NSTXN
	VALUES (2621445,
	2621445,
	2621446,
	2621444,
	0);
INSERT INTO SM_TXN
	VALUES (2621445,
	2621445,
	2621444,
	0);
INSERT INTO SM_NSTXN
	VALUES (2621446,
	2621445,
	2621443,
	2621444,
	0);
INSERT INTO SM_TXN
	VALUES (2621446,
	2621445,
	2621446,
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
	4200,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (2621442,
	2621441,
	2621441,
	41);
INSERT INTO GD_SHP
	VALUES (2621442,
	1872,
	1280,
	2064,
	1376);
INSERT INTO GD_GE
	VALUES (2621443,
	2621441,
	2621442,
	41);
INSERT INTO GD_SHP
	VALUES (2621443,
	1872,
	1680,
	2064,
	1776);
INSERT INTO GD_GE
	VALUES (2621444,
	2621441,
	2621443,
	41);
INSERT INTO GD_SHP
	VALUES (2621444,
	2224,
	1280,
	2416,
	1376);
INSERT INTO GD_GE
	VALUES (2621445,
	2621441,
	2621444,
	41);
INSERT INTO GD_SHP
	VALUES (2621445,
	2224,
	1680,
	2416,
	1776);
INSERT INTO GD_GE
	VALUES (2621446,
	2621441,
	2621445,
	41);
INSERT INTO GD_SHP
	VALUES (2621446,
	1872,
	1472,
	2064,
	1568);
INSERT INTO GD_GE
	VALUES (2621447,
	2621441,
	2621446,
	41);
INSERT INTO GD_SHP
	VALUES (2621447,
	2224,
	1472,
	2416,
	1568);
INSERT INTO GD_GE
	VALUES (2621448,
	2621441,
	2621441,
	42);
INSERT INTO GD_CON
	VALUES (2621448,
	2621442,
	2621442,
	0);
INSERT INTO GD_CTXT
	VALUES (2621448,
	0,
	0,
	0,
	0,
	0,
	0,
	1994,
	1189,
	2229,
	1223,
	-77,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621449,
	2621448,
	2064,
	1312,
	2128,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (2621450,
	2621448,
	2128,
	1312,
	2128,
	1216,
	2621449);
INSERT INTO GD_LS
	VALUES (2621451,
	2621448,
	2128,
	1216,
	1968,
	1216,
	2621450);
INSERT INTO GD_LS
	VALUES (2621452,
	2621448,
	1968,
	1216,
	1968,
	1280,
	2621451);
INSERT INTO GD_GE
	VALUES (2621453,
	2621441,
	2621442,
	42);
INSERT INTO GD_CON
	VALUES (2621453,
	2621443,
	2621444,
	0);
INSERT INTO GD_CTXT
	VALUES (2621453,
	0,
	0,
	0,
	0,
	0,
	0,
	2331,
	1231,
	2536,
	1272,
	155,
	-202,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621454,
	2621453,
	2064,
	1712,
	2192,
	1712,
	0);
INSERT INTO GD_LS
	VALUES (2621455,
	2621453,
	2192,
	1712,
	2192,
	1232,
	2621454);
INSERT INTO GD_LS
	VALUES (2621456,
	2621453,
	2192,
	1232,
	2320,
	1232,
	2621455);
INSERT INTO GD_LS
	VALUES (2621457,
	2621453,
	2320,
	1232,
	2320,
	1280,
	2621456);
INSERT INTO GD_GE
	VALUES (2621458,
	2621441,
	2621444,
	42);
INSERT INTO GD_CON
	VALUES (2621458,
	2621442,
	2621446,
	0);
INSERT INTO GD_CTXT
	VALUES (2621458,
	0,
	0,
	0,
	0,
	0,
	0,
	1705,
	1407,
	1975,
	1447,
	-247,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621459,
	2621458,
	1968,
	1376,
	1968,
	1472,
	0);
INSERT INTO GD_GE
	VALUES (2621460,
	2621441,
	2621443,
	42);
INSERT INTO GD_CON
	VALUES (2621460,
	2621446,
	2621443,
	0);
INSERT INTO GD_CTXT
	VALUES (2621460,
	0,
	0,
	0,
	0,
	0,
	0,
	1695,
	1610,
	1974,
	1654,
	-257,
	1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621461,
	2621460,
	1968,
	1568,
	1968,
	1680,
	0);
INSERT INTO GD_GE
	VALUES (2621462,
	2621441,
	2621446,
	42);
INSERT INTO GD_CON
	VALUES (2621462,
	2621444,
	2621447,
	0);
INSERT INTO GD_CTXT
	VALUES (2621462,
	0,
	0,
	0,
	0,
	0,
	0,
	2304,
	1408,
	2556,
	1457,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621463,
	2621462,
	2320,
	1376,
	2320,
	1472,
	0);
INSERT INTO GD_GE
	VALUES (2621464,
	2621441,
	2621445,
	42);
INSERT INTO GD_CON
	VALUES (2621464,
	2621447,
	2621445,
	0);
INSERT INTO GD_CTXT
	VALUES (2621464,
	0,
	0,
	0,
	0,
	0,
	0,
	2313,
	1601,
	2554,
	1649,
	9,
	-8,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621465,
	2621464,
	2320,
	1568,
	2320,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (1048586,
	'Other Boolean Expression Test',
	16,
	'OBET',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048657,
	1048586);
INSERT INTO O_BATTR
	VALUES (1048657,
	1048586);
INSERT INTO O_ATTR
	VALUES (1048657,
	1048586,
	0,
	'bfalse',
	'',
	'',
	'bfalse',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (1048658,
	1048586);
INSERT INTO O_BATTR
	VALUES (1048658,
	1048586);
INSERT INTO O_ATTR
	VALUES (1048658,
	1048586,
	1048657,
	'btrue',
	'',
	'',
	'btrue',
	0,
	524290);
INSERT INTO O_REF
	VALUES (1048586,
	1048585,
	0,
	1048651,
	1048585,
	1048594,
	1048593,
	1048659,
	1048604,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048659,
	1048586,
	1048651,
	1048585,
	1);
INSERT INTO O_ATTR
	VALUES (1048659,
	1048586,
	1048658,
	'id',
	'',
	'',
	'id',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1048586,
	1048587,
	0,
	1048662,
	1048586,
	1048595,
	1048596,
	1048660,
	1048605,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048660,
	1048586,
	1048662,
	1048587,
	1);
INSERT INTO O_ATTR
	VALUES (1048660,
	1048586,
	1048659,
	'r11btrue',
	'',
	'r11',
	'btrue',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048586,
	1048587,
	0,
	1048663,
	1048586,
	1048595,
	1048596,
	1048661,
	1048606,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048661,
	1048586,
	1048663,
	1048587,
	1);
INSERT INTO O_ATTR
	VALUES (1048661,
	1048586,
	1048660,
	'r11bfalse',
	'',
	'r11',
	'bfalse',
	1,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048586);
INSERT INTO O_OIDA
	VALUES (1048657,
	1048586,
	0);
INSERT INTO O_RTIDA
	VALUES (1048657,
	1048586,
	0,
	1048584,
	1048592);
INSERT INTO O_OIDA
	VALUES (1048658,
	1048586,
	0);
INSERT INTO O_RTIDA
	VALUES (1048658,
	1048586,
	0,
	1048584,
	1048592);
INSERT INTO O_OBJ
	VALUES (1048587,
	'Third Boolean Expression Test',
	17,
	'TBET',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048662,
	1048587);
INSERT INTO O_BATTR
	VALUES (1048662,
	1048587);
INSERT INTO O_ATTR
	VALUES (1048662,
	1048587,
	0,
	'btrue',
	'',
	'',
	'btrue',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (1048663,
	1048587);
INSERT INTO O_BATTR
	VALUES (1048663,
	1048587);
INSERT INTO O_ATTR
	VALUES (1048663,
	1048587,
	1048662,
	'bfalse',
	'',
	'',
	'bfalse',
	0,
	524290);
INSERT INTO O_ID
	VALUES (0,
	1048587);
INSERT INTO O_OIDA
	VALUES (1048663,
	1048587,
	0);
INSERT INTO O_RTIDA
	VALUES (1048663,
	1048587,
	0,
	1048586,
	1048596);
INSERT INTO O_OIDA
	VALUES (1048662,
	1048587,
	0);
INSERT INTO O_RTIDA
	VALUES (1048662,
	1048587,
	0,
	1048586,
	1048596);
INSERT INTO O_OBJ
	VALUES (1048588,
	'User Boolean Test',
	18,
	'UBT',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048664,
	1048588);
INSERT INTO O_BATTR
	VALUES (1048664,
	1048588);
INSERT INTO O_ATTR
	VALUES (1048664,
	1048588,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048665,
	1048588);
INSERT INTO O_BATTR
	VALUES (1048665,
	1048588);
INSERT INTO O_ATTR
	VALUES (1048665,
	1048588,
	1048664,
	'btrue',
	'',
	'',
	'btrue',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (1048666,
	1048588);
INSERT INTO O_BATTR
	VALUES (1048666,
	1048588);
INSERT INTO O_ATTR
	VALUES (1048666,
	1048588,
	1048665,
	'bfalse',
	'',
	'',
	'bfalse',
	0,
	524305);
INSERT INTO O_REF
	VALUES (1048588,
	1048589,
	0,
	1048670,
	1048587,
	1048597,
	1048598,
	1048667,
	1048607,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048667,
	1048588,
	1048670,
	1048589,
	1);
INSERT INTO O_ATTR
	VALUES (1048667,
	1048588,
	1048666,
	'r9bfalse',
	'',
	'r9',
	'bfalse',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048588,
	1048589,
	0,
	1048671,
	1048587,
	1048597,
	1048598,
	1048668,
	1048608,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048668,
	1048588,
	1048671,
	1048589,
	1);
INSERT INTO O_ATTR
	VALUES (1048668,
	1048588,
	1048667,
	'r9btrue',
	'',
	'r9',
	'btrue',
	1,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048669,
	1048588);
INSERT INTO O_BATTR
	VALUES (1048669,
	1048588);
INSERT INTO O_ATTR
	VALUES (1048669,
	1048588,
	1048668,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048588);
INSERT INTO O_OIDA
	VALUES (1048664,
	1048588,
	0);
INSERT INTO O_RTIDA
	VALUES (1048664,
	1048588,
	0,
	1048588,
	1048599);
INSERT INTO SM_ISM
	VALUES (3145734,
	1048588);
INSERT INTO SM_SM
	VALUES (3145734,
	'',
	6);
INSERT INTO SM_MOORE
	VALUES (3145734);
INSERT INTO SM_EVTDI
	VALUES (3145729,
	3145734,
	'tfalse',
	'',
	524305);
INSERT INTO SM_EVTDI
	VALUES (3145730,
	3145734,
	'ttrue',
	'',
	524305);
INSERT INTO SM_EVTDI
	VALUES (110549718,
	3145734,
	'tfalse',
	'',
	524305);
INSERT INTO SM_EVTDI
	VALUES (93962273,
	3145734,
	'tfalse',
	'',
	524305);
INSERT INTO SM_EVTDI
	VALUES (108801103,
	3145734,
	'tfalse',
	'',
	524305);
INSERT INTO SM_EVTDI
	VALUES (3777263,
	3145734,
	'ttrue',
	'',
	524305);
INSERT INTO SM_EVTDI
	VALUES (51343190,
	3145734,
	'ttrue',
	'',
	524305);
INSERT INTO SM_EVTDI
	VALUES (246142497,
	3145734,
	'ttrue',
	'',
	524305);
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
	'Start and test',
	0,
	'',
	'UBT1',
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
	2,
	'Continue and test',
	0,
	'',
	'UBT2',
	'');
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
	3,
	'Start or test',
	0,
	'',
	'UBT3',
	'');
INSERT INTO SM_LEVT
	VALUES (3145732,
	3145734,
	0);
INSERT INTO SM_SEVT
	VALUES (3145732,
	3145734,
	0);
INSERT INTO SM_EVT
	VALUES (3145732,
	3145734,
	0,
	4,
	'Continue or test',
	0,
	'',
	'UBT4',
	'');
INSERT INTO SM_STATE
	VALUES (3145729,
	3145734,
	0,
	'And test 1a',
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
INSERT INTO SM_CH
	VALUES (3145729,
	3145731,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145731,
	3145734,
	0);
INSERT INTO SM_CH
	VALUES (3145729,
	3145732,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145732,
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
	'LOG::LogInfo(message:"Starting and (user-defined type) test") ;

//and operation  (only boolean type)

assign temp1 = false;
assign temp2 = true;

  // local existing    rcvd_evt
assign t5 = temp1 and rcvd_evt.tfalse ;
if ( t5 == false )
  LOG::LogSuccess(message:" and local_existing rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and local_existing rcvd_evt ( false )") ;
end if;
 
assign t6 = temp1 and rcvd_evt.ttrue ;
if ( t6 == false )
  LOG::LogSuccess(message:" and local_existing rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and local_existing rcvd_evt ( false )") ;
end if;
 
assign t7 = temp2 and rcvd_evt.tfalse ;
if ( t7 == false )
  LOG::LogSuccess(message:" and local_existing rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and local_existing rcvd_evt ( false )") ;
end if;
 
assign t8 = temp2 and rcvd_evt.ttrue ;
if ( t8 == true )
  LOG::LogSuccess(message:" and local_existing rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" and local_existing rcvd_evt ( true )") ;
end if;
 
  // local existing   self.attribute 
assign t13 = temp1 and self.bfalse ;
if ( t13 == false )
  LOG::LogSuccess(message:" and local_existing self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and local_existing self.attribute ( false )") ;
end if;
 
assign t14 = temp1 and self.btrue ;
if ( t14 == false )
  LOG::LogSuccess(message:" and local_existing self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and local_existing self.attribute ( false )") ;
end if;
 
assign t15 = temp2 and self.bfalse ;
if ( t15 == false )
  LOG::LogSuccess(message:" and local_existing self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and local_existing self.attribute ( false )") ;
end if;
 
assign t16 = temp2 and self.btrue ;
if ( t16 == true )
  LOG::LogSuccess(message:" and local_existing self.attribute ( true )") ;
else
  LOG::LogFailure(message:" and local_existing self.attribute ( true )") ;
end if;
 

  // local existing    self.referential attribute
assign t17 = temp1 and self.r9bfalse ;
if ( t17 == false )
  LOG::LogSuccess(message:" and local_existing self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and local_existing self.referential_attribute ( false )") ;
end if;
 
assign t18 = temp1 and self.r9btrue ;
if ( t18 == false )
  LOG::LogSuccess(message:" and local_existing self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and local_existing self.referential_attribute ( false )") ;
end if;
 
assign t19 = temp2 and self.r9bfalse ;
if ( t19 == false )
  LOG::LogSuccess(message:" and local_existing self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and local_existing self.referential_attribute ( false )") ;
end if;
 
assign t20 = temp2 and self.r9btrue ;
if ( t20 == true )
  LOG::LogSuccess(message:" and local_existing self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" and local_existing self.referential_attribute ( true )") ;
end if;
 
  // local existing    other.attribute (saf)
select any saf from instances of UOBT;
assign t21 = temp1 and saf.bfalse ;
if ( t21 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(saf) ( false )") ;
end if;
 
assign t22 = temp1 and saf.btrue ;
if ( t22 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(saf) ( false )") ;
end if;
 
assign t23 = temp2 and saf.bfalse ;
if ( t23 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(saf) ( false )") ;
end if;
 
assign t24 = temp2 and saf.btrue ;
if ( t24 == true )
  LOG::LogSuccess(message:" and local_existing other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(saf) ( true )") ;
end if;
 
  // local existing    other.attribute (smf)
select many smfs from instances of UOBT;
for each smf in smfs
assign t25 = temp1 and smf.bfalse ;
if ( t25 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(smf) ( false )") ;
end if;
 
assign t26 = temp1 and smf.btrue ;
if ( t26 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(smf) ( false )") ;
end if;
 
assign t27 = temp2 and smf.bfalse ;
if ( t27 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(smf) ( false )") ;
end if;
 
assign t28 = temp2 and smf.btrue ;
if ( t28 == true )
  LOG::LogSuccess(message:" and local_existing other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(smf) ( true )") ;
end if;
 
end for;
  // local existing    other.attribute (sor)
select one sor related by self->UOBT[R12];
assign t29 = temp1 and sor.bfalse ;
if ( t29 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(sor) ( false )") ;
end if;
 
assign t30 = temp1 and sor.btrue ;
if ( t30 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(sor) ( false )") ;
end if;
 
assign t31 = temp2 and sor.bfalse ;
if ( t31 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(sor) ( false )") ;
end if;
 
assign t32 = temp2 and sor.btrue ;
if ( t32 == true )
  LOG::LogSuccess(message:" and local_existing other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(sor) ( true )") ;
end if;
 
  // local existing    other.attribute (sar)
select any sar related by self->UOBT[R13];
assign t33 = temp1 and sar.bfalse ;
if ( t33 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(sar) ( false )") ;
end if;
 
assign t34 = temp1 and sar.btrue ;
if ( t34 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(sar) ( false )") ;
end if;
 
assign t35 = temp2 and sar.bfalse ;
if ( t35 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(sar) ( false )") ;
end if;
 
assign t36 = temp2 and sar.btrue ;
if ( t36 == true )
  LOG::LogSuccess(message:" and local_existing other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(sar) ( true )") ;
end if;
 
  // local existing    other.attribute (smr)
select many smrs related by self->UOBT[R13];
for each smr in smrs
assign t37 = temp1 and smr.bfalse ;
if ( t37 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(smr) ( false )") ;
end if;
 
assign t38 = temp1 and smr.btrue ;
if ( t38 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(smr) ( false )") ;
end if;
 
assign t39 = temp2 and smr.bfalse ;
if ( t39 == false )
  LOG::LogSuccess(message:" and local_existing other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(smr) ( false )") ;
end if;
 
assign t40 = temp2 and smr.btrue ;
if ( t40 == true )
  LOG::LogSuccess(message:" and local_existing other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.attribute(smr) ( true )") ;
end if;
 
end for;
  // local existing    other.referential attribute (saf)
assign t41 = temp1 and saf.r11bfalse ;
if ( t41 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(saf) ( false )") ;
end if;
 
assign t42 = temp1 and saf.r11btrue ;
if ( t42 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(saf) ( false )") ;
end if;
 
assign t43 = temp2 and saf.r11bfalse ;
if ( t43 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(saf) ( false )") ;
end if;
 
assign t44 = temp2 and saf.r11btrue ;
if ( t44 == true )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(saf) ( true )") ;
end if;
 
  // local existing    other.referential attribute (smf)
for each smf in smfs
assign t45 = temp1 and smf.r11bfalse ;
if ( t45 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(smf) ( false )") ;
end if;
 
assign t46 = temp1 and smf.r11btrue ;
if ( t46 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(smf) ( false )") ;
end if;
 
assign t47 = temp2 and smf.r11bfalse ;
if ( t47 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(smf) ( false )") ;
end if;
 
assign t48 = temp2 and smf.r11btrue ;
if ( t48 == true )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // local existing    other.referential attribute (sor)
assign t49 = temp1 and sor.r11bfalse ;
if ( t49 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(sor) ( false )") ;
end if;
 
assign t50 = temp1 and sor.r11btrue ;
if ( t50 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(sor) ( false )") ;
end if;
 
assign t51 = temp2 and sor.r11bfalse ;
if ( t51 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(sor) ( false )") ;
end if;
 
assign t52 = temp2 and sor.r11btrue ;
if ( t52 == true )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(sor) ( true )") ;
end if;
 
  // local existing    other.referential attribute (sar)
assign t53 = temp1 and sar.r11bfalse ;
if ( t53 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(sar) ( false )") ;
end if;
 
assign t54 = temp1 and sar.r11btrue ;
if ( t54 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(sar) ( false )") ;
end if;
 
assign t55 = temp2 and sar.r11bfalse ;
if ( t55 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(sar) ( false )") ;
end if;
 
assign t56 = temp2 and sar.r11btrue ;
if ( t56 == true )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(sar) ( true )") ;
end if;
 
  // local existing    other.referential attribute (smr)
for each smr in smrs
assign t57 = temp1 and smr.r11bfalse ;
if ( t57 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(smr) ( false )") ;
end if;
 
assign t58 = temp1 and smr.r11btrue ;
if ( t58 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(smr) ( false )") ;
end if;
 
assign t59 = temp2 and smr.r11bfalse ;
if ( t59 == false )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(smr) ( false )") ;
end if;
 
assign t60 = temp2 and smr.r11btrue ;
if ( t60 == true )
  LOG::LogSuccess(message:" and local_existing other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and local_existing other.referential_attribute(smr) ( true )") ;
end if;
 
end for;
  // rcvd_evt    local existing
assign t61 = rcvd_evt.tfalse and temp1 ;
if ( t61 == false )
  LOG::LogSuccess(message:" and rcvd_evt local_existing ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt local_existing ( false )") ;
end if;
 
assign t62 = rcvd_evt.ttrue and temp1 ;
if ( t62 == false )
  LOG::LogSuccess(message:" and rcvd_evt local_existing ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt local_existing ( false )") ;
end if;
 
assign t63 = rcvd_evt.tfalse and temp2 ;
if ( t63 == false )
  LOG::LogSuccess(message:" and rcvd_evt local_existing ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt local_existing ( false )") ;
end if;
 
assign t64 = rcvd_evt.ttrue and temp2 ;
if ( t64 == true )
  LOG::LogSuccess(message:" and rcvd_evt local_existing ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt local_existing ( true )") ;
end if;
 
  // rcvd_evt    rcvd_evt
assign t65 = rcvd_evt.tfalse and rcvd_evt.tfalse ;
if ( t65 == false )
  LOG::LogSuccess(message:" and rcvd_evt rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt rcvd_evt ( false )") ;
end if;
 
assign t66 = rcvd_evt.ttrue and rcvd_evt.tfalse ;
if ( t66 == false )
  LOG::LogSuccess(message:" and rcvd_evt rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt rcvd_evt ( false )") ;
end if;
 
assign t67 = rcvd_evt.tfalse and rcvd_evt.ttrue ;
if ( t67 == false )
  LOG::LogSuccess(message:" and rcvd_evt rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt rcvd_evt ( false )") ;
end if;
 
assign t68 = rcvd_evt.ttrue and rcvd_evt.ttrue ;
if ( t68 == true )
  LOG::LogSuccess(message:" and rcvd_evt rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt rcvd_evt ( true )") ;
end if;
 
  // rcvd_evt    constant
assign t69 = rcvd_evt.tfalse and false ;
if ( t69 == false )
  LOG::LogSuccess(message:" and rcvd_evt constant ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt constant ( false )") ;
end if;
 
assign t70 = rcvd_evt.ttrue and false ;
if ( t70 == false )
  LOG::LogSuccess(message:" and rcvd_evt constant ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt constant ( false )") ;
end if;
 
assign t71 = rcvd_evt.tfalse and true ;
if ( t71 == false )
  LOG::LogSuccess(message:" and rcvd_evt constant ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt constant ( false )") ;
end if;
 
assign t72 = rcvd_evt.ttrue and true ;
if ( t72 == true )
  LOG::LogSuccess(message:" and rcvd_evt constant ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt constant ( true )") ;
end if;
 
  // rcvd_evt    self.attribute
assign t73 = rcvd_evt.tfalse and self.bfalse ;
if ( t73 == false )
  LOG::LogSuccess(message:" and rcvd_evt self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt self.attribute ( false )") ;
end if;
 
assign t74 = rcvd_evt.ttrue and self.bfalse ;
if ( t74 == false )
  LOG::LogSuccess(message:" and rcvd_evt self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt self.attribute ( false )") ;
end if;
 
assign t75 = rcvd_evt.tfalse and self.btrue ;
if ( t75 == false )
  LOG::LogSuccess(message:" and rcvd_evt self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt self.attribute ( false )") ;
end if;
 
assign t76 = rcvd_evt.ttrue and self.btrue ;
if ( t76 == true )
  LOG::LogSuccess(message:" and rcvd_evt self.attribute ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt self.attribute ( true )") ;
end if;
 
  // rcvd_evt    self.referential attribute
assign t77 = rcvd_evt.tfalse and self.r9bfalse ;
if ( t77 == false )
  LOG::LogSuccess(message:" and rcvd_evt self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt self.referential_attribute ( false )") ;
end if;
 
assign t78 = rcvd_evt.ttrue and self.r9bfalse ;
if ( t78 == false )
  LOG::LogSuccess(message:" and rcvd_evt self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt self.referential_attribute ( false )") ;
end if;
 
assign t79 = rcvd_evt.tfalse and self.r9btrue ;
if ( t79 == false )
  LOG::LogSuccess(message:" and rcvd_evt self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt self.referential_attribute ( false )") ;
end if;
 
assign t80 = rcvd_evt.ttrue and self.r9btrue ;
if ( t80 == true )
  LOG::LogSuccess(message:" and rcvd_evt self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt self.referential_attribute ( true )") ;
end if;
 
  // rcvd_evt    other.attribute (saf)
assign t81 = rcvd_evt.tfalse and saf.bfalse ;
if ( t81 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(saf) ( false )") ;
end if;
 
assign t82 = rcvd_evt.tfalse and saf.btrue ;
if ( t82 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(saf) ( false )") ;
end if;
 
assign t83 = rcvd_evt.ttrue and saf.bfalse ;
if ( t83 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(saf) ( false )") ;
end if;
 
assign t84 = rcvd_evt.ttrue and saf.btrue ;
if ( t84 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(saf) ( true )") ;
end if;
 
  // rcvd_evt    other.attribute (smf)
for each smf in smfs
assign t85 = rcvd_evt.tfalse and smf.bfalse ;
if ( t85 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(smf) ( false )") ;
end if;
 
assign t86 = rcvd_evt.tfalse and smf.btrue ;
if ( t86 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(smf) ( false )") ;
end if;
 
assign t87 = rcvd_evt.ttrue and smf.bfalse ;
if ( t87 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(smf) ( false )") ;
end if;
 
assign t88 = rcvd_evt.ttrue and smf.btrue ;
if ( t88 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(smf) ( true )") ;
end if;
 
end for;
  // rcvd_evt    other.attribute (sor)
assign t89 = rcvd_evt.tfalse and sor.bfalse ;
if ( t89 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(sor) ( false )") ;
end if;
 
assign t90 = rcvd_evt.tfalse and sor.btrue ;
if ( t90 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(sor) ( false )") ;
end if;
 
assign t91 = rcvd_evt.ttrue and sor.bfalse ;
if ( t91 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(sor) ( false )") ;
end if;
 
assign t92 = rcvd_evt.ttrue and sor.btrue ;
if ( t92 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(sor) ( true )") ;
end if;
 
  // rcvd_evt    other.attribute (sar)
assign t93 = rcvd_evt.tfalse and sar.bfalse ;
if ( t93 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(sar) ( false )") ;
end if;
 
assign t94 = rcvd_evt.tfalse and sar.btrue ;
if ( t94 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(sar) ( false )") ;
end if;
 
assign t95 = rcvd_evt.ttrue and sar.bfalse ;
if ( t95 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(sar) ( false )") ;
end if;
 
assign t96 = rcvd_evt.ttrue and sar.btrue ;
if ( t96 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(sar) ( true )") ;
end if;
 
  // rcvd_evt    other.attribute (smr)
for each smr in smrs
assign t97 = rcvd_evt.tfalse and smr.bfalse ;
if ( t97 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(smr) ( false )") ;
end if;
 
assign t98 = rcvd_evt.tfalse and smr.btrue ;
if ( t98 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(smr) ( false )") ;
end if;
 
assign t99 = rcvd_evt.ttrue and smr.bfalse ;
if ( t99 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(smr) ( false )") ;
end if;
 
assign t100 = rcvd_evt.ttrue and smr.btrue ;
if ( t100 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.attribute(smr) ( true )") ;
end if;
 
end for;
  // rcvd_evt    other.referential attribute (saf)
assign t101 = rcvd_evt.tfalse and saf.r11bfalse ;
if ( t101 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(saf) ( false )") ;
end if;
 
assign t102 = rcvd_evt.tfalse and saf.r11btrue ;
if ( t102 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(saf) ( false )") ;
end if;
 
assign t103 = rcvd_evt.ttrue and saf.r11bfalse ;
if ( t103 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(saf) ( false )") ;
end if;
 
assign t104 = rcvd_evt.ttrue and saf.r11btrue ;
if ( t104 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(saf) ( true )") ;
end if;
 
  // rcvd_evt    other.referential attribute (smf)
for each smf in smfs
assign t105 = rcvd_evt.tfalse and smf.r11bfalse ;
if ( t105 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(smf) ( false )") ;
end if;
 
assign t106 = rcvd_evt.tfalse and smf.r11btrue ;
if ( t106 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(smf) ( false )") ;
end if;
 
assign t107 = rcvd_evt.ttrue and smf.r11bfalse ;
if ( t107 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(smf) ( false )") ;
end if;
 
assign t108 = rcvd_evt.ttrue and smf.r11btrue ;
if ( t108 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // rcvd_evt    other.referential attribute (sor)
assign t109 = rcvd_evt.tfalse and sor.r11bfalse ;
if ( t109 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(sor) ( false )") ;
end if;
 
assign t110 = rcvd_evt.tfalse and sor.r11btrue ;
if ( t110 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(sor) ( false )") ;
end if;
 
assign t111 = rcvd_evt.ttrue and sor.r11bfalse ;
if ( t111 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(sor) ( false )") ;
end if;
 
assign t112 = rcvd_evt.ttrue and sor.r11btrue ;
if ( t112 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(sor) ( true )") ;
end if;
 
  // rcvd_evt    other.referential attribute (sar)
assign t113 = rcvd_evt.tfalse and sar.r11bfalse ;
if ( t113 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(sar) ( false )") ;
end if;
 
assign t114 = rcvd_evt.tfalse and sar.r11btrue ;
if ( t114 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(sar) ( false )") ;
end if;
 
assign t115 = rcvd_evt.ttrue and sar.r11bfalse ;
if ( t115 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(sar) ( false )") ;
end if;
 
assign t116 = rcvd_evt.ttrue and sar.r11btrue ;
if ( t116 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(sar) ( true )") ;
end if;
 
  // rcvd_evt    other.referential attribute (smr)
for each smr in smrs
assign t117 = rcvd_evt.tfalse and smr.r11bfalse ;
if ( t117 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(smr) ( false )") ;
end if;
 
assign t118 = rcvd_evt.tfalse and smr.r11btrue ;
if ( t118 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(smr) ( false )") ;
end if;
 
assign t119 = rcvd_evt.ttrue and smr.r11bfalse ;
if ( t119 == false )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(smr) ( false )") ;
end if;
 
assign t120 = rcvd_evt.ttrue and smr.r11btrue ;
if ( t120 == true )
  LOG::LogSuccess(message:" and rcvd_evt other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and rcvd_evt other.referential_attribute(smr) ( true )") ;
end if;
 
end for;

 
//generate event to continue test
Generate UBT2:''Continue and test''( ttrue: true, tfalse: false ) to self; 

',
	'');
INSERT INTO SM_STATE
	VALUES (3145730,
	3145734,
	0,
	'And test 1b',
	2,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_SEME
	VALUES (3145730,
	3145730,
	3145734,
	0);
INSERT INTO SM_CH
	VALUES (3145730,
	3145731,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145731,
	3145734,
	0);
INSERT INTO SM_CH
	VALUES (3145730,
	3145732,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145732,
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
	'assign temp1 = false;
assign temp2 = true;

select any saf from instances of UOBT;
select many smfs from instances of UOBT;
select one sor related by self->UOBT[R12];
select any sar related by self->UOBT[R13];
select many smrs related by self->UOBT[R13];

// BridgePoint 3.2 can''t parse expressions for booleans of the type:
//  assign x = true and temp1;
// so tests 121 - 180 aren''t implemented

  // self.attribute    local existing
assign t181 = self.bfalse and temp1 ;
if ( t181 == false )
  LOG::LogSuccess(message:" and self.attribute local_existing ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute local_existing ( false )") ;
end if;
 
assign t182 = self.btrue and temp1 ;
if ( t182 == false )
  LOG::LogSuccess(message:" and self.attribute local_existing ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute local_existing ( false )") ;
end if;
 
assign t183 = self.bfalse and temp2 ;
if ( t183 == false )
  LOG::LogSuccess(message:" and self.attribute local_existing ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute local_existing ( false )") ;
end if;
 
assign t184 = self.btrue and temp2 ;
if ( t184 == true )
  LOG::LogSuccess(message:" and self.attribute local_existing ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute local_existing ( true )") ;
end if;
 
  // self.attribute   rcvd_evt 
assign t185 = self.bfalse and rcvd_evt.tfalse ;
if ( t185 == false )
  LOG::LogSuccess(message:" and self.attribute rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute rcvd_evt ( false )") ;
end if;
 
assign t186 = self.btrue and rcvd_evt.tfalse ;
if ( t186 == false )
  LOG::LogSuccess(message:" and self.attribute rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute rcvd_evt ( false )") ;
end if;
 
assign t187 = self.bfalse and rcvd_evt.ttrue ;
if ( t187 == false )
  LOG::LogSuccess(message:" and self.attribute rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute rcvd_evt ( false )") ;
end if;
 
assign t188 = self.btrue and rcvd_evt.ttrue ;
if ( t188 == true )
  LOG::LogSuccess(message:" and self.attribute rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute rcvd_evt ( true )") ;
end if;
 
  // self.attribute    constant
assign t189 = self.bfalse and false ;
if ( t189 == false )
  LOG::LogSuccess(message:" and self.attribute constant ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute constant ( false )") ;
end if;
 
assign t190 = self.btrue and false ;
if ( t190 == false )
  LOG::LogSuccess(message:" and self.attribute constant ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute constant ( false )") ;
end if;
 
assign t191 = self.bfalse and true ;
if ( t191 == false )
  LOG::LogSuccess(message:" and self.attribute constant ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute constant ( false )") ;
end if;
 
assign t192 = self.btrue and true ;
if ( t192 == true )
  LOG::LogSuccess(message:" and self.attribute constant ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute constant ( true )") ;
end if;
 
  // self.attribute    self.attribute
assign t193 = self.bfalse and self.bfalse ;
if ( t193 == false )
  LOG::LogSuccess(message:" and self.attribute self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute self.attribute ( false )") ;
end if;
 
assign t194 = self.btrue and self.bfalse ;
if ( t194 == false )
  LOG::LogSuccess(message:" and self.attribute self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute self.attribute ( false )") ;
end if;
 
assign t195 = self.bfalse and self.btrue ;
if ( t195 == false )
  LOG::LogSuccess(message:" and self.attribute self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute self.attribute ( false )") ;
end if;
 
assign t196 = self.btrue and self.btrue ;
if ( t196 == true )
  LOG::LogSuccess(message:" and self.attribute self.attribute ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute self.attribute ( true )") ;
end if;
 
  // self.attribute    self.referential attribute
assign t197 = self.bfalse and self.r9bfalse ;
if ( t197 == false )
  LOG::LogSuccess(message:" and self.attribute self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute self.referential_attribute ( false )") ;
end if;
 
assign t198 = self.btrue and self.r9bfalse ;
if ( t198 == false )
  LOG::LogSuccess(message:" and self.attribute self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute self.referential_attribute ( false )") ;
end if;
 
assign t199 = self.bfalse and self.r9btrue ;
if ( t199 == false )
  LOG::LogSuccess(message:" and self.attribute self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute self.referential_attribute ( false )") ;
end if;
 
assign t200 = self.btrue and self.r9btrue ;
if ( t200 == true )
  LOG::LogSuccess(message:" and self.attribute self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute self.referential_attribute ( true )") ;
end if;
 
  // self.attribute    other.attribute (saf)
assign t201 = self.bfalse and saf.bfalse ;
if ( t201 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(saf) ( false )") ;
end if;
 
assign t202 = self.bfalse and saf.btrue ;
if ( t202 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(saf) ( false )") ;
end if;
 
assign t203 = self.btrue and saf.bfalse ;
if ( t203 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(saf) ( false )") ;
end if;
 
assign t204 = self.btrue and saf.btrue ;
if ( t204 == true )
  LOG::LogSuccess(message:" and self.attribute other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(saf) ( true )") ;
end if;
 
  // self.attribute    other.attribute (smf)
for each smf in smfs
assign t205 = self.bfalse and smf.bfalse ;
if ( t205 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(smf) ( false )") ;
end if;
 
assign t206 = self.bfalse and smf.btrue ;
if ( t206 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(smf) ( false )") ;
end if;
 
assign t207 = self.btrue and smf.bfalse ;
if ( t207 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(smf) ( false )") ;
end if;
 
assign t208 = self.btrue and smf.btrue ;
if ( t208 == true )
  LOG::LogSuccess(message:" and self.attribute other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(smf) ( true )") ;
end if;
 
end for;
  // self.attribute    other.attribute (sor)
assign t209 = self.bfalse and sor.bfalse ;
if ( t209 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(sor) ( false )") ;
end if;
 
assign t210 = self.bfalse and sor.btrue ;
if ( t210 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(sor) ( false )") ;
end if;
 
assign t211 = self.btrue and sor.bfalse ;
if ( t211 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(sor) ( false )") ;
end if;
 
assign t212 = self.btrue and sor.btrue ;
if ( t212 == true )
  LOG::LogSuccess(message:" and self.attribute other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(sor) ( true )") ;
end if;
 
  // self.attribute    other.attribute (sar)
assign t213 = self.bfalse and sar.bfalse ;
if ( t213 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(sar) ( false )") ;
end if;
 
assign t214 = self.bfalse and sar.btrue ;
if ( t214 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(sar) ( false )") ;
end if;
 
assign t215 = self.btrue and sar.bfalse ;
if ( t215 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(sar) ( false )") ;
end if;
 
assign t216 = self.btrue and sar.btrue ;
if ( t216 == true )
  LOG::LogSuccess(message:" and self.attribute other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(sar) ( true )") ;
end if;
 
  // self.attribute    other.attribute (smr)
for each smr in smrs
assign t217 = self.bfalse and smr.bfalse ;
if ( t217 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(smr) ( false )") ;
end if;
 
assign t218 = self.bfalse and smr.btrue ;
if ( t218 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(smr) ( false )") ;
end if;
 
assign t219 = self.btrue and smr.bfalse ;
if ( t219 == false )
  LOG::LogSuccess(message:" and self.attribute other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(smr) ( false )") ;
end if;
 
assign t220 = self.btrue and smr.btrue ;
if ( t220 == true )
  LOG::LogSuccess(message:" and self.attribute other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.attribute(smr) ( true )") ;
end if;
 
end for;
  // self.attribute    other.referential attribute (saf)
assign t221 = self.bfalse and saf.r11bfalse ;
if ( t221 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(saf) ( false )") ;
end if;
 
assign t222 = self.bfalse and saf.r11btrue ;
if ( t222 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(saf) ( false )") ;
end if;
 
assign t223 = self.btrue and saf.r11bfalse ;
if ( t223 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(saf) ( false )") ;
end if;
 
assign t224 = self.btrue and saf.r11btrue ;
if ( t224 == true )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(saf) ( true )") ;
end if;
 
  // self.attribute    other.referential attribute (smf)
for each smf in smfs
assign t225 = self.bfalse and smf.r11bfalse ;
if ( t225 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(smf) ( false )") ;
end if;
 
assign t226 = self.bfalse and smf.r11btrue ;
if ( t226 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(smf) ( false )") ;
end if;
 
assign t227 = self.btrue and smf.r11bfalse ;
if ( t227 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(smf) ( false )") ;
end if;
 
assign t228 = self.btrue and smf.r11btrue ;
if ( t228 == true )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // self.attribute    other.referential attribute (sor)
assign t229 = self.bfalse and sor.r11bfalse ;
if ( t229 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(sor) ( false )") ;
end if;
 
assign t230 = self.bfalse and sor.r11btrue ;
if ( t230 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(sor) ( false )") ;
end if;
 
assign t231 = self.btrue and sor.r11bfalse ;
if ( t231 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(sor) ( false )") ;
end if;
 
assign t232 = self.btrue and sor.r11btrue ;
if ( t232 == true )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(sor) ( true )") ;
end if;
 
  // self.attribute    other.referential attribute (sar)
assign t233 = self.bfalse and sar.r11bfalse ;
if ( t233 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(sar) ( false )") ;
end if;
 
assign t234 = self.bfalse and sar.r11btrue ;
if ( t234 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(sar) ( false )") ;
end if;
 
assign t235 = self.btrue and sar.r11bfalse ;
if ( t235 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(sar) ( false )") ;
end if;
 
assign t236 = self.btrue and sar.r11btrue ;
if ( t236 == true )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(sar) ( true )") ;
end if;
 
  // self.attribute    other.referential attribute (smr)
for each smr in smrs
assign t237 = self.bfalse and smr.r11bfalse ;
if ( t237 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(smr) ( false )") ;
end if;
 
assign t238 = self.bfalse and smr.r11btrue ;
if ( t238 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(smr) ( false )") ;
end if;
 
assign t239 = self.btrue and smr.r11bfalse ;
if ( t239 == false )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(smr) ( false )") ;
end if;
 
assign t240 = self.btrue and smr.r11btrue ;
if ( t240 == true )
  LOG::LogSuccess(message:" and self.attribute other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and self.attribute other.referential_attribute(smr) ( true )") ;
end if;
 
end for;


  // self.referential attribute    local existing
assign t241 = self.r9bfalse and temp1 ;
if ( t241 == false )
  LOG::LogSuccess(message:" and self.referential_attribute local_existing ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute local_existing ( false )") ;
end if;
 
assign t242 = self.r9btrue and temp1 ;
if ( t242 == false )
  LOG::LogSuccess(message:" and self.referential_attribute local_existing ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute local_existing ( false )") ;
end if;
 
assign t243 = self.r9bfalse and temp2 ;
if ( t243 == false )
  LOG::LogSuccess(message:" and self.referential_attribute local_existing ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute local_existing ( false )") ;
end if;
 
assign t244 = self.r9btrue and temp2 ;
if ( t244 == true )
  LOG::LogSuccess(message:" and self.referential_attribute local_existing ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute local_existing ( true )") ;
end if;
 
  // self.referential_attribute   rcvd_evt 
assign t245 = self.r9bfalse and rcvd_evt.tfalse ;
if ( t245 == false )
  LOG::LogSuccess(message:" and self.referential_attribute rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute rcvd_evt ( false )") ;
end if;
 
assign t246 = self.r9btrue and rcvd_evt.tfalse ;
if ( t246 == false )
  LOG::LogSuccess(message:" and self.referential_attribute rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute rcvd_evt ( false )") ;
end if;
 
assign t247 = self.r9bfalse and rcvd_evt.ttrue ;
if ( t247 == false )
  LOG::LogSuccess(message:" and self.referential_attribute rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute rcvd_evt ( false )") ;
end if;
 
assign t248 = self.r9btrue and rcvd_evt.ttrue ;
if ( t248 == true )
  LOG::LogSuccess(message:" and self.referential_attribute rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute rcvd_evt ( true )") ;
end if;
 
  // self.referential_attribute    constant
assign t249 = self.r9bfalse and false ;
if ( t249 == false )
  LOG::LogSuccess(message:" and self.referential_attribute constant ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute constant ( false )") ;
end if;
 
assign t250 = self.r9btrue and false ;
if ( t250 == false )
  LOG::LogSuccess(message:" and self.referential_attribute constant ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute constant ( false )") ;
end if;
 
assign t251 = self.r9bfalse and true ;
if ( t251 == false )
  LOG::LogSuccess(message:" and self.referential_attribute constant ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute constant ( false )") ;
end if;
 
assign t252 = self.r9btrue and true ;
if ( t252 == true )
  LOG::LogSuccess(message:" and self.referential_attribute constant ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute constant ( true )") ;
end if;
 
  // self.referential_attribute    self.attribute
assign t253 = self.r9bfalse and self.bfalse ;
if ( t253 == false )
  LOG::LogSuccess(message:" and self.referential_attribute self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute self.attribute ( false )") ;
end if;
 
assign t254 = self.r9btrue and self.bfalse ;
if ( t254 == false )
  LOG::LogSuccess(message:" and self.referential_attribute self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute self.attribute ( false )") ;
end if;
 
assign t255 = self.r9bfalse and self.btrue ;
if ( t255 == false )
  LOG::LogSuccess(message:" and self.referential_attribute self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute self.attribute ( false )") ;
end if;
 
assign t256 = self.r9btrue and self.btrue ;
if ( t256 == true )
  LOG::LogSuccess(message:" and self.referential_attribute self.attribute ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute self.attribute ( true )") ;
end if;
 
  // self.referential_attribute    self.referential attribute
assign t257 = self.r9bfalse and self.r9bfalse ;
if ( t257 == false )
  LOG::LogSuccess(message:" and self.referential_attribute self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute self.referential_attribute ( false )") ;
end if;
 
assign t258 = self.r9btrue and self.r9bfalse ;
if ( t258 == false )
  LOG::LogSuccess(message:" and self.referential_attribute self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute self.referential_attribute ( false )") ;
end if;
 
assign t259 = self.r9bfalse and self.r9btrue ;
if ( t259 == false )
  LOG::LogSuccess(message:" and self.referential_attribute self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute self.referential_attribute ( false )") ;
end if;
 
assign t260 = self.r9btrue and self.r9btrue ;
if ( t260 == true )
  LOG::LogSuccess(message:" and self.referential_attribute self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute self.referential_attribute ( true )") ;
end if;
 
  // self.referential_attribute    other.attribute (saf)
assign t261 = self.r9bfalse and saf.bfalse ;
if ( t261 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(saf) ( false )") ;
end if;
 
assign t262 = self.r9bfalse and saf.btrue ;
if ( t262 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(saf) ( false )") ;
end if;
 
assign t263 = self.r9btrue and saf.bfalse ;
if ( t263 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(saf) ( false )") ;
end if;
 
assign t264 = self.r9btrue and saf.btrue ;
if ( t264 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(saf) ( true )") ;
end if;
 
  // self.referential_attribute    other.attribute (smf)
for each smf in smfs
assign t265 = self.r9bfalse and smf.bfalse ;
if ( t265 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(smf) ( false )") ;
end if;
 
assign t266 = self.r9bfalse and smf.btrue ;
if ( t266 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(smf) ( false )") ;
end if;
 
assign t267 = self.r9btrue and smf.bfalse ;
if ( t267 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(smf) ( false )") ;
end if;
 
assign t268 = self.r9btrue and smf.btrue ;
if ( t268 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(smf) ( true )") ;
end if;
 
end for;
  // self.referential_attribute    other.attribute (sor)
assign t269 = self.r9bfalse and sor.bfalse ;
if ( t269 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(sor) ( false )") ;
end if;
 
assign t270 = self.r9bfalse and sor.btrue ;
if ( t270 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(sor) ( false )") ;
end if;
 
assign t271 = self.r9btrue and sor.bfalse ;
if ( t271 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(sor) ( false )") ;
end if;
 
assign t272 = self.r9btrue and sor.btrue ;
if ( t272 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(sor) ( true )") ;
end if;
 
  // self.referential_attribute    other.attribute (sar)
assign t273 = self.r9bfalse and sar.bfalse ;
if ( t273 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(sar) ( false )") ;
end if;
 
assign t274 = self.r9bfalse and sar.btrue ;
if ( t274 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(sar) ( false )") ;
end if;
 
assign t275 = self.r9btrue and sar.bfalse ;
if ( t275 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(sar) ( false )") ;
end if;
 
assign t276 = self.r9btrue and sar.btrue ;
if ( t276 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(sar) ( true )") ;
end if;
 
  // self.referential_attribute    other.attribute (smr)
for each smr in smrs
assign t277 = self.r9bfalse and smr.bfalse ;
if ( t277 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(smr) ( false )") ;
end if;
 
assign t278 = self.r9bfalse and smr.btrue ;
if ( t278 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(smr) ( false )") ;
end if;
 
assign t279 = self.r9btrue and smr.bfalse ;
if ( t279 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(smr) ( false )") ;
end if;
 
assign t280 = self.r9btrue and smr.btrue ;
if ( t280 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.attribute(smr) ( true )") ;
end if;
 
end for;
  // self.referential_attribute    other.referential attribute (saf)
assign t281 = self.r9bfalse and saf.r11bfalse ;
if ( t281 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(saf) ( false )") ;
end if;
 
assign t282 = self.r9bfalse and saf.r11btrue ;
if ( t282 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(saf) ( false )") ;
end if;
 
assign t283 = self.r9btrue and saf.r11bfalse ;
if ( t283 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(saf) ( false )") ;
end if;
 
assign t284 = self.r9btrue and saf.r11btrue ;
if ( t284 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(saf) ( true )") ;
end if;
 
  // self.referential_attribute    other.referential attribute (smf)
for each smf in smfs
assign t285 = self.r9bfalse and smf.r11bfalse ;
if ( t285 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(smf) ( false )") ;
end if;
 
assign t286 = self.r9bfalse and smf.r11btrue ;
if ( t286 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(smf) ( false )") ;
end if;
 
assign t287 = self.r9btrue and smf.r11bfalse ;
if ( t287 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(smf) ( false )") ;
end if;
 
assign t288 = self.r9btrue and smf.r11btrue ;
if ( t288 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // self.referential_attribute    other.referential attribute (sor)
assign t289 = self.r9bfalse and sor.r11bfalse ;
if ( t289 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(sor) ( false )") ;
end if;
 
assign t290 = self.r9bfalse and sor.r11btrue ;
if ( t290 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(sor) ( false )") ;
end if;
 
assign t291 = self.r9btrue and sor.r11bfalse ;
if ( t291 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(sor) ( false )") ;
end if;
 
assign t292 = self.r9btrue and sor.r11btrue ;
if ( t292 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(sor) ( true )") ;
end if;
 
  // self.referential_attribute    other.referential attribute (sar)
assign t293 = self.r9bfalse and sar.r11bfalse ;
if ( t293 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(sar) ( false )") ;
end if;
 
assign t294 = self.r9bfalse and sar.r11btrue ;
if ( t294 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(sar) ( false )") ;
end if;
 
assign t295 = self.r9btrue and sar.r11bfalse ;
if ( t295 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(sar) ( false )") ;
end if;
 
assign t296 = self.r9btrue and sar.r11btrue ;
if ( t296 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(sar) ( true )") ;
end if;
 
  // self.referential_attribute    other.referential attribute (smr)
for each smr in smrs
assign t297 = self.r9bfalse and smr.r11bfalse ;
if ( t297 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(smr) ( false )") ;
end if;
 
assign t298 = self.r9bfalse and smr.r11btrue ;
if ( t298 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(smr) ( false )") ;
end if;
 
assign t299 = self.r9btrue and smr.r11bfalse ;
if ( t299 == false )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(smr) ( false )") ;
end if;
 
assign t300 = self.r9btrue and smr.r11btrue ;
if ( t300 == true )
  LOG::LogSuccess(message:" and self.referential_attribute other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and self.referential_attribute other.referential_attribute(smr) ( true )") ;
end if;
 
end for;

 
//generate event to continue test
Generate UBT2:''Continue and test''( ttrue: true, tfalse: false ) to self; 

',
	'');
INSERT INTO SM_STATE
	VALUES (3145731,
	3145734,
	0,
	'And test 2',
	3,
	0);
INSERT INTO SM_CH
	VALUES (3145731,
	3145729,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145729,
	3145734,
	0);
INSERT INTO SM_CH
	VALUES (3145731,
	3145730,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145730,
	3145734,
	0);
INSERT INTO SM_SEME
	VALUES (3145731,
	3145731,
	3145734,
	0);
INSERT INTO SM_CH
	VALUES (3145731,
	3145732,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145732,
	3145734,
	0);
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
	'//and operation  (only boolean type)

assign temp1 = false;
assign temp2 = true;

select any saf from instances of UOBT;
select many smfs from instances of UOBT;
select one sor related by self->UOBT[R12];
select any sar related by self->UOBT[R13];
select many smrs related by self->UOBT[R13];

  // other.attribute (saf)    local existing
assign t301 = saf.bfalse and temp1 ;
if ( t301 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) local_existing ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) local_existing ( false )") ;
end if;
 
assign t302 = saf.btrue and temp1 ;
if ( t302 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) local_existing ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) local_existing ( false )") ;
end if;
 
assign t303 = saf.bfalse and temp2 ;
if ( t303 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) local_existing ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) local_existing ( false )") ;
end if;
 
assign t304 = saf.btrue and temp2 ;
if ( t304 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) local_existing ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) local_existing ( true )") ;
end if;
 
  // other.attribute(saf)   rcvd_evt 
assign t305 = saf.bfalse and rcvd_evt.tfalse ;
if ( t305 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) rcvd_evt ( false )") ;
end if;
 
assign t306 = saf.btrue and rcvd_evt.tfalse ;
if ( t306 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) rcvd_evt ( false )") ;
end if;
 
assign t307 = saf.bfalse and rcvd_evt.ttrue ;
if ( t307 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) rcvd_evt ( false )") ;
end if;
 
assign t308 = saf.btrue and rcvd_evt.ttrue ;
if ( t308 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) rcvd_evt ( true )") ;
end if;
 
  // other.attribute(saf)    constant
assign t309 = saf.bfalse and false ;
if ( t309 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) constant ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) constant ( false )") ;
end if;
 
assign t310 = saf.btrue and false ;
if ( t310 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) constant ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) constant ( false )") ;
end if;
 
assign t311 = saf.bfalse and true ;
if ( t311 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) constant ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) constant ( false )") ;
end if;
 
assign t312 = saf.btrue and true ;
if ( t312 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) constant ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) constant ( true )") ;
end if;
 
  // other.attribute(saf)    self.attribute
assign t313 = saf.bfalse and self.bfalse ;
if ( t313 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) self.attribute ( false )") ;
end if;
 
assign t314 = saf.btrue and self.bfalse ;
if ( t314 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) self.attribute ( false )") ;
end if;
 
assign t315 = saf.bfalse and self.btrue ;
if ( t315 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) self.attribute ( false )") ;
end if;
 
assign t316 = saf.btrue and self.btrue ;
if ( t316 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) self.attribute ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) self.attribute ( true )") ;
end if;
 
  // other.attribute(saf)    self.referential attribute
assign t317 = saf.bfalse and self.r9bfalse ;
if ( t317 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) self.referential_attribute ( false )") ;
end if;
 
assign t318 = saf.btrue and self.r9bfalse ;
if ( t318 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) self.referential_attribute ( false )") ;
end if;
 
assign t319 = saf.bfalse and self.r9btrue ;
if ( t319 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) self.referential_attribute ( false )") ;
end if;
 
assign t320 = saf.btrue and self.r9btrue ;
if ( t320 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) self.referential_attribute ( true )") ;
end if;
 
  // other.attribute(saf)    other.attribute (saf)
assign t321 = saf.bfalse and saf.bfalse ;
if ( t321 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(saf) ( false )") ;
end if;
 
assign t322 = saf.bfalse and saf.btrue ;
if ( t322 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(saf) ( false )") ;
end if;
 
assign t323 = saf.btrue and saf.bfalse ;
if ( t323 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(saf) ( false )") ;
end if;
 
assign t324 = saf.btrue and saf.btrue ;
if ( t324 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(saf) ( true )") ;
end if;
 
  // other.attribute(saf)    other.attribute (smf)
for each smf in smfs
assign t325 = saf.bfalse and smf.bfalse ;
if ( t325 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(smf) ( false )") ;
end if;
 
assign t326 = saf.bfalse and smf.btrue ;
if ( t326 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(smf) ( false )") ;
end if;
 
assign t327 = saf.btrue and smf.bfalse ;
if ( t327 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(smf) ( false )") ;
end if;
 
assign t328 = saf.btrue and smf.btrue ;
if ( t328 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(smf) ( true )") ;
end if;
 
end for;
  // other.attribute(saf)    other.attribute (sor)
assign t329 = saf.bfalse and sor.bfalse ;
if ( t329 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(sor) ( false )") ;
end if;
 
assign t330 = saf.bfalse and sor.btrue ;
if ( t330 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(sor) ( false )") ;
end if;
 
assign t331 = saf.btrue and sor.bfalse ;
if ( t331 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(sor) ( false )") ;
end if;
 
assign t332 = saf.btrue and sor.btrue ;
if ( t332 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(sor) ( true )") ;
end if;
 
  // other.attribute(saf)    other.attribute (sar)
assign t333 = saf.bfalse and sar.bfalse ;
if ( t333 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(sar) ( false )") ;
end if;
 
assign t334 = saf.bfalse and sar.btrue ;
if ( t334 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(sar) ( false )") ;
end if;
 
assign t335 = saf.btrue and sar.bfalse ;
if ( t335 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(sar) ( false )") ;
end if;
 
assign t336 = saf.btrue and sar.btrue ;
if ( t336 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(sar) ( true )") ;
end if;
 
  // other.attribute(saf)    other.attribute (smr)
for each smr in smrs
assign t337 = saf.bfalse and smr.bfalse ;
if ( t337 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(smr) ( false )") ;
end if;
 
assign t338 = saf.bfalse and smr.btrue ;
if ( t338 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(smr) ( false )") ;
end if;
 
assign t339 = saf.btrue and smr.bfalse ;
if ( t339 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(smr) ( false )") ;
end if;
 
assign t340 = saf.btrue and smr.btrue ;
if ( t340 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.attribute(smr) ( true )") ;
end if;
 
end for;
  // other.attribute(saf)    other.referential attribute (saf)
assign t341 = saf.bfalse and saf.r11bfalse ;
if ( t341 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(saf) ( false )") ;
end if;
 
assign t342 = saf.bfalse and saf.r11btrue ;
if ( t342 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(saf) ( false )") ;
end if;
 
assign t343 = saf.btrue and saf.r11bfalse ;
if ( t343 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(saf) ( false )") ;
end if;
 
assign t344 = saf.btrue and saf.r11btrue ;
if ( t344 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(saf) ( true )") ;
end if;
 
  // other.attribute(saf)    other.referential attribute (smf)
for each smf in smfs
assign t345 = saf.bfalse and smf.r11bfalse ;
if ( t345 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(smf) ( false )") ;
end if;
 
assign t346 = saf.bfalse and smf.r11btrue ;
if ( t346 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(smf) ( false )") ;
end if;
 
assign t347 = saf.btrue and smf.r11bfalse ;
if ( t347 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(smf) ( false )") ;
end if;
 
assign t348 = saf.btrue and smf.r11btrue ;
if ( t348 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // other.attribute(saf)    other.referential attribute (sor)
assign t349 = saf.bfalse and sor.r11bfalse ;
if ( t349 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(sor) ( false )") ;
end if;
 
assign t350 = saf.bfalse and sor.r11btrue ;
if ( t350 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(sor) ( false )") ;
end if;
 
assign t351 = saf.btrue and sor.r11bfalse ;
if ( t351 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(sor) ( false )") ;
end if;
 
assign t352 = saf.btrue and sor.r11btrue ;
if ( t352 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(sor) ( true )") ;
end if;
 
  // other.attribute(saf)    other.referential attribute (sar)
assign t353 = saf.bfalse and sar.r11bfalse ;
if ( t353 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(sar) ( false )") ;
end if;
 
assign t354 = saf.bfalse and sar.r11btrue ;
if ( t354 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(sar) ( false )") ;
end if;
 
assign t355 = saf.btrue and sar.r11bfalse ;
if ( t355 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(sar) ( false )") ;
end if;
 
assign t356 = saf.btrue and sar.r11btrue ;
if ( t356 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(sar) ( true )") ;
end if;
 
  // other.attribute(saf)    other.referential attribute (smr)
for each smr in smrs
assign t357 = saf.bfalse and smr.r11bfalse ;
if ( t357 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(smr) ( false )") ;
end if;
 
assign t358 = saf.bfalse and smr.r11btrue ;
if ( t358 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(smr) ( false )") ;
end if;
 
assign t359 = saf.btrue and smr.r11bfalse ;
if ( t359 == false )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(smr) ( false )") ;
end if;
 
assign t360 = saf.btrue and smr.r11btrue ;
if ( t360 == true )
  LOG::LogSuccess(message:" and other.attribute(saf) other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and other.attribute(saf) other.referential_attribute(smr) ( true )") ;
end if;
 
end for;
  // other.attribute (smf)    local existing
  // other.attribute (smf)    constant
  // other.attribute (smf)    constant
  // other.attribute (smf)    self.attribute
  // other.attribute (smf)    self.referential attribute
  // other.attribute (smf)    other.attribute (saf)
  // other.attribute (smf)    other.attribute (smf)
  // other.attribute (smf)    other.attribute (sor)
  // other.attribute (smf)    other.attribute (sar)
  // other.attribute (smf)    other.attribute (smr)
  // other.attribute (smf)    other.referential attribute (saf)
  // other.attribute (smf)    other.referential attribute (smf)
  // other.attribute (smf)    other.referential attribute (sor)
  // other.attribute (smf)    other.referential attribute (sar)
  // other.attribute (smf)    other.referential attribute (smr)
  // other.attribute (sor)    local existing
  // other.attribute (sor)    constant
  // other.attribute (sor)    constant
  // other.attribute (sor)    self.attribute
  // other.attribute (sor)    self.referential attribute
  // other.attribute (sor)    other.attribute (saf)
  // other.attribute (sor)    other.attribute (smf)
  // other.attribute (sor)    other.attribute (sor)
  // other.attribute (sor)    other.attribute (sar)
  // other.attribute (sor)    other.attribute (smr)
  // other.attribute (sor)    other.referential attribute (saf)
  // other.attribute (sor)    other.referential attribute (smf)
  // other.attribute (sor)    other.referential attribute (sor)
  // other.attribute (sor)    other.referential attribute (sar)
  // other.attribute (sor)    other.referential attribute (smr)
  // other.attribute (sar)    local existing
  // other.attribute (sar)    constant
  // other.attribute (sar)    constant
  // other.attribute (sar)    self.attribute
  // other.attribute (sar)    self.referential attribute
  // other.attribute (sar)    other.attribute (saf)
  // other.attribute (sar)    other.attribute (smf)
  // other.attribute (sar)    other.attribute (sor)
  // other.attribute (sar)    other.attribute (sar)
  // other.attribute (sar)    other.attribute (smr)
  // other.attribute (sar)    other.referential attribute (saf)
  // other.attribute (sar)    other.referential attribute (smf)
  // other.attribute (sar)    other.referential attribute (sor)
  // other.attribute (sar)    other.referential attribute (sar)
  // other.attribute (sar)    other.referential attribute (smr)
  // other.attribute (smr)    local existing
  // other.attribute (smr)    constant
  // other.attribute (smr)    constant
  // other.attribute (smr)    self.attribute
  // other.attribute (smr)    self.referential attribute
  // other.attribute (smr)    other.attribute (saf)
  // other.attribute (smr)    other.attribute (smf)
  // other.attribute (smr)    other.attribute (sor)
  // other.attribute (smr)    other.attribute (sar)
  // other.attribute (smr)    other.attribute (smr)
  // other.attribute (smr)    other.referential attribute (saf)
  // other.attribute (smr)    other.referential attribute (smf)
  // other.attribute (smr)    other.referential attribute (sor)
  // other.attribute (smr)    other.referential attribute (sar)
  // other.attribute (smr)    other.referential attribute (smr)
  // other.referential attribute (saf)    local existing
assign t601 = saf.r11bfalse and temp1 ;
if ( t601 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) local_existing ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) local_existing ( false )") ;
end if;
 
assign t602 = saf.r11btrue and temp1 ;
if ( t602 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) local_existing ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) local_existing ( false )") ;
end if;
 
assign t603 = saf.r11bfalse and temp2 ;
if ( t603 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) local_existing ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) local_existing ( false )") ;
end if;
 
assign t604 = saf.r11btrue and temp2 ;
if ( t604 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) local_existing ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) local_existing ( true )") ;
end if;
 
  // other.referential_attribute(saf)   rcvd_evt 
assign t605 = saf.r11bfalse and rcvd_evt.tfalse ;
if ( t605 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) rcvd_evt ( false )") ;
end if;
 
assign t606 = saf.r11btrue and rcvd_evt.tfalse ;
if ( t606 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) rcvd_evt ( false )") ;
end if;
 
assign t607 = saf.r11bfalse and rcvd_evt.ttrue ;
if ( t607 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) rcvd_evt ( false )") ;
end if;
 
assign t608 = saf.r11btrue and rcvd_evt.ttrue ;
if ( t608 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) rcvd_evt ( true )") ;
end if;
 
  // other.referential_attribute(saf)    constant
assign t609 = saf.r11bfalse and false ;
if ( t609 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) constant ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) constant ( false )") ;
end if;
 
assign t610 = saf.r11btrue and false ;
if ( t610 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) constant ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) constant ( false )") ;
end if;
 
assign t611 = saf.r11bfalse and true ;
if ( t611 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) constant ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) constant ( false )") ;
end if;
 
assign t612 = saf.r11btrue and true ;
if ( t612 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) constant ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) constant ( true )") ;
end if;
 
  // other.referential_attribute(saf)    self.attribute
assign t613 = saf.r11bfalse and self.bfalse ;
if ( t613 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) self.attribute ( false )") ;
end if;
 
assign t614 = saf.r11btrue and self.bfalse ;
if ( t614 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) self.attribute ( false )") ;
end if;
 
assign t615 = saf.r11bfalse and self.btrue ;
if ( t615 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) self.attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) self.attribute ( false )") ;
end if;
 
assign t616 = saf.r11btrue and self.btrue ;
if ( t616 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) self.attribute ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) self.attribute ( true )") ;
end if;
 
  // other.referential_attribute(saf)    self.referential attribute
assign t617 = saf.r11bfalse and self.r9bfalse ;
if ( t617 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) self.referential_attribute ( false )") ;
end if;
 
assign t618 = saf.r11btrue and self.r9bfalse ;
if ( t618 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) self.referential_attribute ( false )") ;
end if;
 
assign t619 = saf.r11bfalse and self.r9btrue ;
if ( t619 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) self.referential_attribute ( false )") ;
end if;
 
assign t620 = saf.r11btrue and self.r9btrue ;
if ( t620 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) self.referential_attribute ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.attribute (saf)
assign t621 = saf.r11bfalse and saf.bfalse ;
if ( t621 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(saf) ( false )") ;
end if;
 
assign t622 = saf.r11bfalse and saf.btrue ;
if ( t622 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(saf) ( false )") ;
end if;
 
assign t623 = saf.r11btrue and saf.bfalse ;
if ( t623 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(saf) ( false )") ;
end if;
 
assign t624 = saf.r11btrue and saf.btrue ;
if ( t624 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(saf) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.attribute (smf)
for each smf in smfs
assign t625 = saf.r11bfalse and smf.bfalse ;
if ( t625 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(smf) ( false )") ;
end if;
 
assign t626 = saf.r11bfalse and smf.btrue ;
if ( t626 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(smf) ( false )") ;
end if;
 
assign t627 = saf.r11btrue and smf.bfalse ;
if ( t627 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(smf) ( false )") ;
end if;
 
assign t628 = saf.r11btrue and smf.btrue ;
if ( t628 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(smf) ( true )") ;
end if;
 
end for;
  // other.referential_attribute(saf)    other.attribute (sor)
assign t629 = saf.r11bfalse and sor.bfalse ;
if ( t629 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(sor) ( false )") ;
end if;
 
assign t630 = saf.r11bfalse and sor.btrue ;
if ( t630 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(sor) ( false )") ;
end if;
 
assign t631 = saf.r11btrue and sor.bfalse ;
if ( t631 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(sor) ( false )") ;
end if;
 
assign t632 = saf.r11btrue and sor.btrue ;
if ( t632 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(sor) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.attribute (sar)
assign t633 = saf.r11bfalse and sar.bfalse ;
if ( t633 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(sar) ( false )") ;
end if;
 
assign t634 = saf.r11bfalse and sar.btrue ;
if ( t634 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(sar) ( false )") ;
end if;
 
assign t635 = saf.r11btrue and sar.bfalse ;
if ( t635 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(sar) ( false )") ;
end if;
 
assign t636 = saf.r11btrue and sar.btrue ;
if ( t636 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(sar) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.attribute (smr)
for each smr in smrs
assign t637 = saf.r11bfalse and smr.bfalse ;
if ( t637 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(smr) ( false )") ;
end if;
 
assign t638 = saf.r11bfalse and smr.btrue ;
if ( t638 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(smr) ( false )") ;
end if;
 
assign t639 = saf.r11btrue and smr.bfalse ;
if ( t639 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(smr) ( false )") ;
end if;
 
assign t640 = saf.r11btrue and smr.btrue ;
if ( t640 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.attribute(smr) ( true )") ;
end if;
 
end for;
  // other.referential_attribute(saf)    other.referential attribute (saf)
assign t641 = saf.r11bfalse and saf.r11bfalse ;
if ( t641 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(saf) ( false )") ;
end if;
 
assign t642 = saf.r11bfalse and saf.r11btrue ;
if ( t642 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(saf) ( false )") ;
end if;
 
assign t643 = saf.r11btrue and saf.r11bfalse ;
if ( t643 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(saf) ( false )") ;
end if;
 
assign t644 = saf.r11btrue and saf.r11btrue ;
if ( t644 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(saf) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.referential attribute (smf)
for each smf in smfs
assign t645 = saf.r11bfalse and smf.r11bfalse ;
if ( t645 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(smf) ( false )") ;
end if;
 
assign t646 = saf.r11bfalse and smf.r11btrue ;
if ( t646 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(smf) ( false )") ;
end if;
 
assign t647 = saf.r11btrue and smf.r11bfalse ;
if ( t647 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(smf) ( false )") ;
end if;
 
assign t648 = saf.r11btrue and smf.r11btrue ;
if ( t648 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // other.referential_attribute(saf)    other.referential attribute (sor)
assign t649 = saf.r11bfalse and sor.r11bfalse ;
if ( t649 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(sor) ( false )") ;
end if;
 
assign t650 = saf.r11bfalse and sor.r11btrue ;
if ( t650 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(sor) ( false )") ;
end if;
 
assign t651 = saf.r11btrue and sor.r11bfalse ;
if ( t651 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(sor) ( false )") ;
end if;
 
assign t652 = saf.r11btrue and sor.r11btrue ;
if ( t652 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(sor) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.referential attribute (sar)
assign t653 = saf.r11bfalse and sar.r11bfalse ;
if ( t653 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(sar) ( false )") ;
end if;
 
assign t654 = saf.r11bfalse and sar.r11btrue ;
if ( t654 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(sar) ( false )") ;
end if;
 
assign t655 = saf.r11btrue and sar.r11bfalse ;
if ( t655 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(sar) ( false )") ;
end if;
 
assign t656 = saf.r11btrue and sar.r11btrue ;
if ( t656 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(sar) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.referential attribute (smr)
for each smr in smrs
assign t657 = saf.r11bfalse and smr.r11bfalse ;
if ( t657 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(smr) ( false )") ;
end if;
 
assign t658 = saf.r11bfalse and smr.r11btrue ;
if ( t658 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(smr) ( false )") ;
end if;
 
assign t659 = saf.r11btrue and smr.r11bfalse ;
if ( t659 == false )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(smr) ( false )") ;
end if;
 
assign t660 = saf.r11btrue and smr.r11btrue ;
if ( t660 == true )
  LOG::LogSuccess(message:" and other.referential_attribute(saf) other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" and other.referential_attribute(saf) other.referential_attribute(smr) ( true )") ;
end if;
 
end for;
  // other.referential attribute (saf)    constant
  // other.referential attribute (saf)    constant
  // other.referential attribute (saf)    self.attribute
  // other.referential attribute (saf)    self.referential attribute
  // other.referential attribute (saf)    other.attribute (saf)
  // other.referential attribute (saf)    other.attribute (smf)
  // other.referential attribute (saf)    other.attribute (sor)
  // other.referential attribute (saf)    other.attribute (sar)
  // other.referential attribute (saf)    other.attribute (smr)
  // other.referential attribute (saf)    other.referential attribute (saf)
  // other.referential attribute (saf)    other.referential attribute (smf)
  // other.referential attribute (saf)    other.referential attribute (sor)
  // other.referential attribute (saf)    other.referential attribute (sar)
  // other.referential attribute (saf)    other.referential attribute (smr)
  // other.referential attribute (smf)    local existing
  // other.referential attribute (smf)    constant
  // other.referential attribute (smf)    constant
  // other.referential attribute (smf)    self.attribute
  // other.referential attribute (smf)    self.referential attribute
  // other.referential attribute (smf)    other.attribute (saf)
  // other.referential attribute (smf)    other.attribute (smf)
  // other.referential attribute (smf)    other.attribute (sor)
  // other.referential attribute (smf)    other.attribute (sar)
  // other.referential attribute (smf)    other.attribute (smr)
  // other.referential attribute (smf)    other.referential attribute (saf)
  // other.referential attribute (smf)    other.referential attribute (smf)
  // other.referential attribute (smf)    other.referential attribute (sor)
  // other.referential attribute (smf)    other.referential attribute (sar)
  // other.referential attribute (smf)    other.referential attribute (smr)
  // other.referential attribute (sor)    local existing
  // other.referential attribute (sor)    constant
  // other.referential attribute (sor)    constant
  // other.referential attribute (sor)    self.attribute
  // other.referential attribute (sor)    self.referential attribute
  // other.referential attribute (sor)    other.attribute (saf)
  // other.referential attribute (sor)    other.attribute (smf)
  // other.referential attribute (sor)    other.attribute (sor)
  // other.referential attribute (sor)    other.attribute (sar)
  // other.referential attribute (sor)    other.attribute (smr)
  // other.referential attribute (sor)    other.referential attribute (saf)
  // other.referential attribute (sor)    other.referential attribute (smf)
  // other.referential attribute (sor)    other.referential attribute (sor)
  // other.referential attribute (sor)    other.referential attribute (sar)
  // other.referential attribute (sor)    other.referential attribute (smr)
  // other.referential attribute (sar)    local existing
  // other.referential attribute (sar)    constant
  // other.referential attribute (sar)    constant
  // other.referential attribute (sar)    self.attribute
  // other.referential attribute (sar)    self.referential attribute
  // other.referential attribute (sar)    other.attribute (saf)
  // other.referential attribute (sar)    other.attribute (smf)
  // other.referential attribute (sar)    other.attribute (sor)
  // other.referential attribute (sar)    other.attribute (sar)
  // other.referential attribute (sar)    other.attribute (smr)
  // other.referential attribute (sar)    other.referential attribute (saf)
  // other.referential attribute (sar)    other.referential attribute (smf)
  // other.referential attribute (sar)    other.referential attribute (sor)
  // other.referential attribute (sar)    other.referential attribute (sar)
  // other.referential attribute (sar)    other.referential attribute (smr)
  // other.referential attribute (sar)    local existing
  // other.referential attribute (smr)    constant
  // other.referential attribute (smr)    constant
  // other.referential attribute (smr)    self.attribute
  // other.referential attribute (smr)    self.referential attribute
  // other.referential attribute (smr)    other.attribute (saf)
  // other.referential attribute (smr)    other.attribute (smf)
  // other.referential attribute (smr)    other.attribute (sor)
  // other.referential attribute (smr)    other.attribute (sar)
  // other.referential attribute (smr)    other.attribute (smr)
  // other.referential attribute (smr)    other.referential attribute (saf)
  // other.referential attribute (smr)    other.referential attribute (smf)
  // other.referential attribute (smr)    other.referential attribute (sor)
  // other.referential attribute (smr)    other.referential attribute (sar)
  // other.referential attribute (smr)    other.referential attribute (smr)
 
LOG::LogInfo(message:"Completed and (user-defined type) test") ;

//generate event to commence next test
generate UBT3:''Start or test''(tfalse:false, ttrue:true) to self;
 

',
	'');
INSERT INTO SM_STATE
	VALUES (3145732,
	3145734,
	0,
	'Or test 1a',
	4,
	0);
INSERT INTO SM_CH
	VALUES (3145732,
	3145729,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145729,
	3145734,
	0);
INSERT INTO SM_CH
	VALUES (3145732,
	3145730,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145730,
	3145734,
	0);
INSERT INTO SM_CH
	VALUES (3145732,
	3145731,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145731,
	3145734,
	0);
INSERT INTO SM_SEME
	VALUES (3145732,
	3145732,
	3145734,
	0);
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
	'LOG::LogInfo(message:"Starting or (user-defined type) test") ;

//or operation  (only boolean type)

assign temp1 = false;
assign temp2 = true;

  // local existing    rcvd_evt
assign t5 = temp1 or rcvd_evt.tfalse ;
if ( t5 == false )
  LOG::LogSuccess(message:" or local_existing rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" or local_existing rcvd_evt ( false )") ;
end if;
 
assign t6 = temp1 or rcvd_evt.ttrue ;
if ( t6 == true )
  LOG::LogSuccess(message:" or local_existing rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or local_existing rcvd_evt ( true )") ;
end if;
 
assign t7 = temp2 or rcvd_evt.tfalse ;
if ( t7 == true )
  LOG::LogSuccess(message:" or local_existing rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or local_existing rcvd_evt ( true )") ;
end if;
 
assign t8 = temp2 or rcvd_evt.ttrue ;
if ( t8 == true )
  LOG::LogSuccess(message:" or local_existing rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or local_existing rcvd_evt ( true )") ;
end if;
 
  // local existing   constant 
assign t9 = temp1 or false ;
if ( t9 == false )
  LOG::LogSuccess(message:" or local_existing constant ( false )") ;
else
  LOG::LogFailure(message:" or local_existing constant ( false )") ;
end if;
 
assign t10 = temp1 or true ;
if ( t10 == true )
  LOG::LogSuccess(message:" or local_existing constant ( true )") ;
else
  LOG::LogFailure(message:" or local_existing constant ( true )") ;
end if;
 
assign t11 = temp2 or false ;
if ( t11 == true )
  LOG::LogSuccess(message:" or local_existing constant ( true )") ;
else
  LOG::LogFailure(message:" or local_existing constant ( true )") ;
end if;
 
assign t12 = temp2 or true ;
if ( t12 == true )
  LOG::LogSuccess(message:" or local_existing constant ( true )") ;
else
  LOG::LogFailure(message:" or local_existing constant ( true )") ;
end if;
 
  // local existing   self.attribute 
assign t13 = temp1 or self.bfalse ;
if ( t13 == false )
  LOG::LogSuccess(message:" or local_existing self.attribute ( false )") ;
else
  LOG::LogFailure(message:" or local_existing self.attribute ( false )") ;
end if;
 
assign t14 = temp1 or self.btrue ;
if ( t14 == true )
  LOG::LogSuccess(message:" or local_existing self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or local_existing self.attribute ( true )") ;
end if;
 
assign t15 = temp2 or self.bfalse ;
if ( t15 == true )
  LOG::LogSuccess(message:" or local_existing self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or local_existing self.attribute ( true )") ;
end if;
 
assign t16 = temp2 or self.btrue ;
if ( t16 == true )
  LOG::LogSuccess(message:" or local_existing self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or local_existing self.attribute ( true )") ;
end if;
 

  // local existing    self.referential attribute
assign t17 = temp1 or self.r9bfalse ;
if ( t17 == false )
  LOG::LogSuccess(message:" or local_existing self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" or local_existing self.referential_attribute ( false )") ;
end if;
 
assign t18 = temp1 or self.r9btrue ;
if ( t18 == true )
  LOG::LogSuccess(message:" or local_existing self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or local_existing self.referential_attribute ( true )") ;
end if;
 
assign t19 = temp2 or self.r9bfalse ;
if ( t19 == true )
  LOG::LogSuccess(message:" or local_existing self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or local_existing self.referential_attribute ( true )") ;
end if;
 
assign t20 = temp2 or self.r9btrue ;
if ( t20 == true )
  LOG::LogSuccess(message:" or local_existing self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or local_existing self.referential_attribute ( true )") ;
end if;
 
  // local existing    other.attribute (saf)
select any saf from instances of UOBT;
assign t21 = temp1 or saf.bfalse ;
if ( t21 == false )
  LOG::LogSuccess(message:" or local_existing other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(saf) ( false )") ;
end if;
 
assign t22 = temp1 or saf.btrue ;
if ( t22 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(saf) ( true )") ;
end if;
 
assign t23 = temp2 or saf.bfalse ;
if ( t23 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(saf) ( true )") ;
end if;
 
assign t24 = temp2 or saf.btrue ;
if ( t24 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(saf) ( true )") ;
end if;
 
  // local existing    other.attribute (smf)
select many smfs from instances of UOBT;
for each smf in smfs
assign t25 = temp1 or smf.bfalse ;
if ( t25 == false )
  LOG::LogSuccess(message:" or local_existing other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(smf) ( false )") ;
end if;
 
assign t26 = temp1 or smf.btrue ;
if ( t26 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(smf) ( true )") ;
end if;
 
assign t27 = temp2 or smf.bfalse ;
if ( t27 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(smf) ( true )") ;
end if;
 
assign t28 = temp2 or smf.btrue ;
if ( t28 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(smf) ( true )") ;
end if;
 
end for;
  // local existing    other.attribute (sor)
select one sor related by self->UOBT[R12];
assign t29 = temp1 or sor.bfalse ;
if ( t29 == false )
  LOG::LogSuccess(message:" or local_existing other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(sor) ( false )") ;
end if;
 
assign t30 = temp1 or sor.btrue ;
if ( t30 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(sor) ( true )") ;
end if;
 
assign t31 = temp2 or sor.bfalse ;
if ( t31 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(sor) ( true )") ;
end if;
 
assign t32 = temp2 or sor.btrue ;
if ( t32 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(sor) ( true )") ;
end if;
 
  // local existing    other.attribute (sar)
select any sar related by self->UOBT[R13];
assign t33 = temp1 or sar.bfalse ;
if ( t33 == false )
  LOG::LogSuccess(message:" or local_existing other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(sar) ( false )") ;
end if;
 
assign t34 = temp1 or sar.btrue ;
if ( t34 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(sar) ( true )") ;
end if;
 
assign t35 = temp2 or sar.bfalse ;
if ( t35 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(sar) ( true )") ;
end if;
 
assign t36 = temp2 or sar.btrue ;
if ( t36 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(sar) ( true )") ;
end if;
 
  // local existing    other.attribute (smr)
select many smrs related by self->UOBT[R13];
for each smr in smrs
assign t37 = temp1 or smr.bfalse ;
if ( t37 == false )
  LOG::LogSuccess(message:" or local_existing other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(smr) ( false )") ;
end if;
 
assign t38 = temp1 or smr.btrue ;
if ( t38 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(smr) ( true )") ;
end if;
 
assign t39 = temp2 or smr.bfalse ;
if ( t39 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(smr) ( true )") ;
end if;
 
assign t40 = temp2 or smr.btrue ;
if ( t40 == true )
  LOG::LogSuccess(message:" or local_existing other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.attribute(smr) ( true )") ;
end if;
 
end for;
  // local existing    other.referential attribute (saf)
assign t41 = temp1 or saf.r11bfalse ;
if ( t41 == false )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(saf) ( false )") ;
end if;
 
assign t42 = temp1 or saf.r11btrue ;
if ( t42 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(saf) ( true )") ;
end if;
 
assign t43 = temp2 or saf.r11bfalse ;
if ( t43 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(saf) ( true )") ;
end if;
 
assign t44 = temp2 or saf.r11btrue ;
if ( t44 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(saf) ( true )") ;
end if;
 
  // local existing    other.referential attribute (smf)
for each smf in smfs
assign t45 = temp1 or smf.r11bfalse ;
if ( t45 == false )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(smf) ( false )") ;
end if;
 
assign t46 = temp1 or smf.r11btrue ;
if ( t46 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(smf) ( true )") ;
end if;
 
assign t47 = temp2 or smf.r11bfalse ;
if ( t47 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(smf) ( true )") ;
end if;
 
assign t48 = temp2 or smf.r11btrue ;
if ( t48 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // local existing    other.referential attribute (sor)
assign t49 = temp1 or sor.r11bfalse ;
if ( t49 == false )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(sor) ( false )") ;
end if;
 
assign t50 = temp1 or sor.r11btrue ;
if ( t50 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(sor) ( true )") ;
end if;
 
assign t51 = temp2 or sor.r11bfalse ;
if ( t51 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(sor) ( true )") ;
end if;
 
assign t52 = temp2 or sor.r11btrue ;
if ( t52 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(sor) ( true )") ;
end if;
 
  // local existing    other.referential attribute (sar)
assign t53 = temp1 or sar.r11bfalse ;
if ( t53 == false )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(sar) ( false )") ;
end if;
 
assign t54 = temp1 or sar.r11btrue ;
if ( t54 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(sar) ( true )") ;
end if;
 
assign t55 = temp2 or sar.r11bfalse ;
if ( t55 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(sar) ( true )") ;
end if;
 
assign t56 = temp2 or sar.r11btrue ;
if ( t56 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(sar) ( true )") ;
end if;
 
  // local existing    other.referential attribute (smr)
for each smr in smrs
assign t57 = temp1 or smr.r11bfalse ;
if ( t57 == false )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(smr) ( false )") ;
end if;
 
assign t58 = temp1 or smr.r11btrue ;
if ( t58 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(smr) ( true )") ;
end if;
 
assign t59 = temp2 or smr.r11bfalse ;
if ( t59 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(smr) ( true )") ;
end if;
 
assign t60 = temp2 or smr.r11btrue ;
if ( t60 == true )
  LOG::LogSuccess(message:" or local_existing other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or local_existing other.referential_attribute(smr) ( true )") ;
end if;
 
end for;
  // rcvd_evt    local existing
assign t61 = rcvd_evt.tfalse or temp1 ;
if ( t61 == false )
  LOG::LogSuccess(message:" or rcvd_evt local_existing ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt local_existing ( false )") ;
end if;
 
assign t62 = rcvd_evt.ttrue or temp1 ;
if ( t62 == true )
  LOG::LogSuccess(message:" or rcvd_evt local_existing ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt local_existing ( true )") ;
end if;
 
assign t63 = rcvd_evt.tfalse or temp2 ;
if ( t63 == true )
  LOG::LogSuccess(message:" or rcvd_evt local_existing ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt local_existing ( true )") ;
end if;
 
assign t64 = rcvd_evt.ttrue or temp2 ;
if ( t64 == true )
  LOG::LogSuccess(message:" or rcvd_evt local_existing ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt local_existing ( true )") ;
end if;
 
  // rcvd_evt    rcvd_evt
assign t65 = rcvd_evt.tfalse or rcvd_evt.tfalse ;
if ( t65 == false )
  LOG::LogSuccess(message:" or rcvd_evt rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt rcvd_evt ( false )") ;
end if;
 
assign t66 = rcvd_evt.ttrue or rcvd_evt.tfalse ;
if ( t66 == true )
  LOG::LogSuccess(message:" or rcvd_evt rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt rcvd_evt ( true )") ;
end if;
 
assign t67 = rcvd_evt.tfalse or rcvd_evt.ttrue ;
if ( t67 == true )
  LOG::LogSuccess(message:" or rcvd_evt rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt rcvd_evt ( true )") ;
end if;
 
assign t68 = rcvd_evt.ttrue or rcvd_evt.ttrue ;
if ( t68 == true )
  LOG::LogSuccess(message:" or rcvd_evt rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt rcvd_evt ( true )") ;
end if;
 
  // rcvd_evt    constant
assign t69 = rcvd_evt.tfalse or false ;
if ( t69 == false )
  LOG::LogSuccess(message:" or rcvd_evt constant ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt constant ( false )") ;
end if;
 
assign t70 = rcvd_evt.ttrue or false ;
if ( t70 == true )
  LOG::LogSuccess(message:" or rcvd_evt constant ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt constant ( true )") ;
end if;
 
assign t71 = rcvd_evt.tfalse or true ;
if ( t71 == true )
  LOG::LogSuccess(message:" or rcvd_evt constant ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt constant ( true )") ;
end if;
 
assign t72 = rcvd_evt.ttrue or true ;
if ( t72 == true )
  LOG::LogSuccess(message:" or rcvd_evt constant ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt constant ( true )") ;
end if;
 
  // rcvd_evt    self.attribute
assign t73 = rcvd_evt.tfalse or self.bfalse ;
if ( t73 == false )
  LOG::LogSuccess(message:" or rcvd_evt self.attribute ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt self.attribute ( false )") ;
end if;
 
assign t74 = rcvd_evt.ttrue or self.bfalse ;
if ( t74 == true )
  LOG::LogSuccess(message:" or rcvd_evt self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt self.attribute ( true )") ;
end if;
 
assign t75 = rcvd_evt.tfalse or self.btrue ;
if ( t75 == true )
  LOG::LogSuccess(message:" or rcvd_evt self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt self.attribute ( true )") ;
end if;
 
assign t76 = rcvd_evt.ttrue or self.btrue ;
if ( t76 == true )
  LOG::LogSuccess(message:" or rcvd_evt self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt self.attribute ( true )") ;
end if;
 
  // rcvd_evt    self.referential attribute
assign t77 = rcvd_evt.tfalse or self.r9bfalse ;
if ( t77 == false )
  LOG::LogSuccess(message:" or rcvd_evt self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt self.referential_attribute ( false )") ;
end if;
 
assign t78 = rcvd_evt.ttrue or self.r9bfalse ;
if ( t78 == true )
  LOG::LogSuccess(message:" or rcvd_evt self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt self.referential_attribute ( true )") ;
end if;
 
assign t79 = rcvd_evt.tfalse or self.r9btrue ;
if ( t79 == true )
  LOG::LogSuccess(message:" or rcvd_evt self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt self.referential_attribute ( true )") ;
end if;
 
assign t80 = rcvd_evt.ttrue or self.r9btrue ;
if ( t80 == true )
  LOG::LogSuccess(message:" or rcvd_evt self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt self.referential_attribute ( true )") ;
end if;
 
  // rcvd_evt    other.attribute (saf)
assign t81 = rcvd_evt.tfalse or saf.bfalse ;
if ( t81 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(saf) ( false )") ;
end if;
 
assign t82 = rcvd_evt.tfalse or saf.btrue ;
if ( t82 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(saf) ( true )") ;
end if;
 
assign t83 = rcvd_evt.ttrue or saf.bfalse ;
if ( t83 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(saf) ( true )") ;
end if;
 
assign t84 = rcvd_evt.ttrue or saf.btrue ;
if ( t84 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(saf) ( true )") ;
end if;
 
  // rcvd_evt    other.attribute (smf)
for each smf in smfs
assign t85 = rcvd_evt.tfalse or smf.bfalse ;
if ( t85 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(smf) ( false )") ;
end if;
 
assign t86 = rcvd_evt.tfalse or smf.btrue ;
if ( t86 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(smf) ( true )") ;
end if;
 
assign t87 = rcvd_evt.ttrue or smf.bfalse ;
if ( t87 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(smf) ( true )") ;
end if;
 
assign t88 = rcvd_evt.ttrue or smf.btrue ;
if ( t88 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(smf) ( true )") ;
end if;
 
end for;
  // rcvd_evt    other.attribute (sor)
assign t89 = rcvd_evt.tfalse or sor.bfalse ;
if ( t89 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(sor) ( false )") ;
end if;
 
assign t90 = rcvd_evt.tfalse or sor.btrue ;
if ( t90 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(sor) ( true )") ;
end if;
 
assign t91 = rcvd_evt.ttrue or sor.bfalse ;
if ( t91 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(sor) ( true )") ;
end if;
 
assign t92 = rcvd_evt.ttrue or sor.btrue ;
if ( t92 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(sor) ( true )") ;
end if;
 
  // rcvd_evt    other.attribute (sar)
assign t93 = rcvd_evt.tfalse or sar.bfalse ;
if ( t93 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(sar) ( false )") ;
end if;
 
assign t94 = rcvd_evt.tfalse or sar.btrue ;
if ( t94 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(sar) ( true )") ;
end if;
 
assign t95 = rcvd_evt.ttrue or sar.bfalse ;
if ( t95 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(sar) ( true )") ;
end if;
 
assign t96 = rcvd_evt.ttrue or sar.btrue ;
if ( t96 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(sar) ( true )") ;
end if;
 
  // rcvd_evt    other.attribute (smr)
for each smr in smrs
assign t97 = rcvd_evt.tfalse or smr.bfalse ;
if ( t97 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(smr) ( false )") ;
end if;
 
assign t98 = rcvd_evt.tfalse or smr.btrue ;
if ( t98 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(smr) ( true )") ;
end if;
 
assign t99 = rcvd_evt.ttrue or smr.bfalse ;
if ( t99 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(smr) ( true )") ;
end if;
 
assign t100 = rcvd_evt.ttrue or smr.btrue ;
if ( t100 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.attribute(smr) ( true )") ;
end if;
 
end for;
  // rcvd_evt    other.referential attribute (saf)
assign t101 = rcvd_evt.tfalse or saf.r11bfalse ;
if ( t101 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(saf) ( false )") ;
end if;
 
assign t102 = rcvd_evt.tfalse or saf.r11btrue ;
if ( t102 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(saf) ( true )") ;
end if;
 
assign t103 = rcvd_evt.ttrue or saf.r11bfalse ;
if ( t103 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(saf) ( true )") ;
end if;
 
assign t104 = rcvd_evt.ttrue or saf.r11btrue ;
if ( t104 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(saf) ( true )") ;
end if;
 
  // rcvd_evt    other.referential attribute (smf)
for each smf in smfs
assign t105 = rcvd_evt.tfalse or smf.r11bfalse ;
if ( t105 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(smf) ( false )") ;
end if;
 
assign t106 = rcvd_evt.tfalse or smf.r11btrue ;
if ( t106 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(smf) ( true )") ;
end if;
 
assign t107 = rcvd_evt.ttrue or smf.r11bfalse ;
if ( t107 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(smf) ( true )") ;
end if;
 
assign t108 = rcvd_evt.ttrue or smf.r11btrue ;
if ( t108 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // rcvd_evt    other.referential attribute (sor)
assign t109 = rcvd_evt.tfalse or sor.r11bfalse ;
if ( t109 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(sor) ( false )") ;
end if;
 
assign t110 = rcvd_evt.tfalse or sor.r11btrue ;
if ( t110 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(sor) ( true )") ;
end if;
 
assign t111 = rcvd_evt.ttrue or sor.r11bfalse ;
if ( t111 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(sor) ( true )") ;
end if;
 
assign t112 = rcvd_evt.ttrue or sor.r11btrue ;
if ( t112 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(sor) ( true )") ;
end if;
 
  // rcvd_evt    other.referential attribute (sar)
assign t113 = rcvd_evt.tfalse or sar.r11bfalse ;
if ( t113 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(sar) ( false )") ;
end if;
 
assign t114 = rcvd_evt.tfalse or sar.r11btrue ;
if ( t114 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(sar) ( true )") ;
end if;
 
assign t115 = rcvd_evt.ttrue or sar.r11bfalse ;
if ( t115 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(sar) ( true )") ;
end if;
 
assign t116 = rcvd_evt.ttrue or sar.r11btrue ;
if ( t116 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(sar) ( true )") ;
end if;
 
  // rcvd_evt    other.referential attribute (smr)
for each smr in smrs
assign t117 = rcvd_evt.tfalse or smr.r11bfalse ;
if ( t117 == false )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(smr) ( false )") ;
end if;
 
assign t118 = rcvd_evt.tfalse or smr.r11btrue ;
if ( t118 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(smr) ( true )") ;
end if;
 
assign t119 = rcvd_evt.ttrue or smr.r11bfalse ;
if ( t119 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(smr) ( true )") ;
end if;
 
assign t120 = rcvd_evt.ttrue or smr.r11btrue ;
if ( t120 == true )
  LOG::LogSuccess(message:" or rcvd_evt other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or rcvd_evt other.referential_attribute(smr) ( true )") ;
end if;
 
end for;

 
//generate event to continue test
Generate UBT4:''Continue or test''( ttrue: true, tfalse: false ) to self; 

',
	'');
INSERT INTO SM_STATE
	VALUES (3145733,
	3145734,
	0,
	'Or test 1b',
	5,
	0);
INSERT INTO SM_CH
	VALUES (3145733,
	3145729,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145729,
	3145734,
	0);
INSERT INTO SM_CH
	VALUES (3145733,
	3145730,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145730,
	3145734,
	0);
INSERT INTO SM_CH
	VALUES (3145733,
	3145731,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145731,
	3145734,
	0);
INSERT INTO SM_SEME
	VALUES (3145733,
	3145732,
	3145734,
	0);
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
	'
assign temp1 = false;
assign temp2 = true;

select any saf from instances of UOBT;
select many smfs from instances of UOBT;
select one sor related by self->UOBT[R12];
select any sar related by self->UOBT[R13];
select many smrs related by self->UOBT[R13];

// BridgePoint 3.2 can''t parse expressions for booleans of the type:
//  assign x = true or temp1;
// so tests 121 - 180 aren''t implemented

  // self.attribute    local existing
assign t181 = self.bfalse or temp1 ;
if ( t181 == false )
  LOG::LogSuccess(message:" or self.attribute local_existing ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute local_existing ( false )") ;
end if;
 
assign t182 = self.btrue or temp1 ;
if ( t182 == true )
  LOG::LogSuccess(message:" or self.attribute local_existing ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute local_existing ( true )") ;
end if;
 
assign t183 = self.bfalse or temp2 ;
if ( t183 == true )
  LOG::LogSuccess(message:" or self.attribute local_existing ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute local_existing ( true )") ;
end if;
 
assign t184 = self.btrue or temp2 ;
if ( t184 == true )
  LOG::LogSuccess(message:" or self.attribute local_existing ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute local_existing ( true )") ;
end if;
 
  // self.attribute   rcvd_evt 
assign t185 = self.bfalse or rcvd_evt.tfalse ;
if ( t185 == false )
  LOG::LogSuccess(message:" or self.attribute rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute rcvd_evt ( false )") ;
end if;
 
assign t186 = self.btrue or rcvd_evt.tfalse ;
if ( t186 == true )
  LOG::LogSuccess(message:" or self.attribute rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute rcvd_evt ( true )") ;
end if;
 
assign t187 = self.bfalse or rcvd_evt.ttrue ;
if ( t187 == true )
  LOG::LogSuccess(message:" or self.attribute rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute rcvd_evt ( true )") ;
end if;
 
assign t188 = self.btrue or rcvd_evt.ttrue ;
if ( t188 == true )
  LOG::LogSuccess(message:" or self.attribute rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute rcvd_evt ( true )") ;
end if;
 
  // self.attribute    constant
assign t189 = self.bfalse or false ;
if ( t189 == false )
  LOG::LogSuccess(message:" or self.attribute constant ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute constant ( false )") ;
end if;
 
assign t190 = self.btrue or false ;
if ( t190 == true )
  LOG::LogSuccess(message:" or self.attribute constant ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute constant ( true )") ;
end if;
 
assign t191 = self.bfalse or true ;
if ( t191 == true )
  LOG::LogSuccess(message:" or self.attribute constant ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute constant ( true )") ;
end if;
 
assign t192 = self.btrue or true ;
if ( t192 == true )
  LOG::LogSuccess(message:" or self.attribute constant ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute constant ( true )") ;
end if;
 
  // self.attribute    self.attribute
assign t193 = self.bfalse or self.bfalse ;
if ( t193 == false )
  LOG::LogSuccess(message:" or self.attribute self.attribute ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute self.attribute ( false )") ;
end if;
 
assign t194 = self.btrue or self.bfalse ;
if ( t194 == true )
  LOG::LogSuccess(message:" or self.attribute self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute self.attribute ( true )") ;
end if;
 
assign t195 = self.bfalse or self.btrue ;
if ( t195 == true )
  LOG::LogSuccess(message:" or self.attribute self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute self.attribute ( true )") ;
end if;
 
assign t196 = self.btrue or self.btrue ;
if ( t196 == true )
  LOG::LogSuccess(message:" or self.attribute self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute self.attribute ( true )") ;
end if;
 
  // self.attribute    self.referential attribute
assign t197 = self.bfalse or self.r9bfalse ;
if ( t197 == false )
  LOG::LogSuccess(message:" or self.attribute self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute self.referential_attribute ( false )") ;
end if;
 
assign t198 = self.btrue or self.r9bfalse ;
if ( t198 == true )
  LOG::LogSuccess(message:" or self.attribute self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute self.referential_attribute ( true )") ;
end if;
 
assign t199 = self.bfalse or self.r9btrue ;
if ( t199 == true )
  LOG::LogSuccess(message:" or self.attribute self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute self.referential_attribute ( true )") ;
end if;
 
assign t200 = self.btrue or self.r9btrue ;
if ( t200 == true )
  LOG::LogSuccess(message:" or self.attribute self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute self.referential_attribute ( true )") ;
end if;
 
  // self.attribute    other.attribute (saf)
assign t201 = self.bfalse or saf.bfalse ;
if ( t201 == false )
  LOG::LogSuccess(message:" or self.attribute other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(saf) ( false )") ;
end if;
 
assign t202 = self.bfalse or saf.btrue ;
if ( t202 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(saf) ( true )") ;
end if;
 
assign t203 = self.btrue or saf.bfalse ;
if ( t203 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(saf) ( true )") ;
end if;
 
assign t204 = self.btrue or saf.btrue ;
if ( t204 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(saf) ( true )") ;
end if;
 
  // self.attribute    other.attribute (smf)
for each smf in smfs
assign t205 = self.bfalse or smf.bfalse ;
if ( t205 == false )
  LOG::LogSuccess(message:" or self.attribute other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(smf) ( false )") ;
end if;
 
assign t206 = self.bfalse or smf.btrue ;
if ( t206 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(smf) ( true )") ;
end if;
 
assign t207 = self.btrue or smf.bfalse ;
if ( t207 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(smf) ( true )") ;
end if;
 
assign t208 = self.btrue or smf.btrue ;
if ( t208 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(smf) ( true )") ;
end if;
 
end for;
  // self.attribute    other.attribute (sor)
assign t209 = self.bfalse or sor.bfalse ;
if ( t209 == false )
  LOG::LogSuccess(message:" or self.attribute other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(sor) ( false )") ;
end if;
 
assign t210 = self.bfalse or sor.btrue ;
if ( t210 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(sor) ( true )") ;
end if;
 
assign t211 = self.btrue or sor.bfalse ;
if ( t211 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(sor) ( true )") ;
end if;
 
assign t212 = self.btrue or sor.btrue ;
if ( t212 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(sor) ( true )") ;
end if;
 
  // self.attribute    other.attribute (sar)
assign t213 = self.bfalse or sar.bfalse ;
if ( t213 == false )
  LOG::LogSuccess(message:" or self.attribute other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(sar) ( false )") ;
end if;
 
assign t214 = self.bfalse or sar.btrue ;
if ( t214 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(sar) ( true )") ;
end if;
 
assign t215 = self.btrue or sar.bfalse ;
if ( t215 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(sar) ( true )") ;
end if;
 
assign t216 = self.btrue or sar.btrue ;
if ( t216 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(sar) ( true )") ;
end if;
 
  // self.attribute    other.attribute (smr)
for each smr in smrs
assign t217 = self.bfalse or smr.bfalse ;
if ( t217 == false )
  LOG::LogSuccess(message:" or self.attribute other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(smr) ( false )") ;
end if;
 
assign t218 = self.bfalse or smr.btrue ;
if ( t218 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(smr) ( true )") ;
end if;
 
assign t219 = self.btrue or smr.bfalse ;
if ( t219 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(smr) ( true )") ;
end if;
 
assign t220 = self.btrue or smr.btrue ;
if ( t220 == true )
  LOG::LogSuccess(message:" or self.attribute other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.attribute(smr) ( true )") ;
end if;
 
end for;
  // self.attribute    other.referential attribute (saf)
assign t221 = self.bfalse or saf.r11bfalse ;
if ( t221 == false )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(saf) ( false )") ;
end if;
 
assign t222 = self.bfalse or saf.r11btrue ;
if ( t222 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(saf) ( true )") ;
end if;
 
assign t223 = self.btrue or saf.r11bfalse ;
if ( t223 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(saf) ( true )") ;
end if;
 
assign t224 = self.btrue or saf.r11btrue ;
if ( t224 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(saf) ( true )") ;
end if;
 
  // self.attribute    other.referential attribute (smf)
for each smf in smfs
assign t225 = self.bfalse or smf.r11bfalse ;
if ( t225 == false )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(smf) ( false )") ;
end if;
 
assign t226 = self.bfalse or smf.r11btrue ;
if ( t226 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(smf) ( true )") ;
end if;
 
assign t227 = self.btrue or smf.r11bfalse ;
if ( t227 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(smf) ( true )") ;
end if;
 
assign t228 = self.btrue or smf.r11btrue ;
if ( t228 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // self.attribute    other.referential attribute (sor)
assign t229 = self.bfalse or sor.r11bfalse ;
if ( t229 == false )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(sor) ( false )") ;
end if;
 
assign t230 = self.bfalse or sor.r11btrue ;
if ( t230 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(sor) ( true )") ;
end if;
 
assign t231 = self.btrue or sor.r11bfalse ;
if ( t231 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(sor) ( true )") ;
end if;
 
assign t232 = self.btrue or sor.r11btrue ;
if ( t232 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(sor) ( true )") ;
end if;
 
  // self.attribute    other.referential attribute (sar)
assign t233 = self.bfalse or sar.r11bfalse ;
if ( t233 == false )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(sar) ( false )") ;
end if;
 
assign t234 = self.bfalse or sar.r11btrue ;
if ( t234 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(sar) ( true )") ;
end if;
 
assign t235 = self.btrue or sar.r11bfalse ;
if ( t235 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(sar) ( true )") ;
end if;
 
assign t236 = self.btrue or sar.r11btrue ;
if ( t236 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(sar) ( true )") ;
end if;
 
  // self.attribute    other.referential attribute (smr)
for each smr in smrs
assign t237 = self.bfalse or smr.r11bfalse ;
if ( t237 == false )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(smr) ( false )") ;
end if;
 
assign t238 = self.bfalse or smr.r11btrue ;
if ( t238 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(smr) ( true )") ;
end if;
 
assign t239 = self.btrue or smr.r11bfalse ;
if ( t239 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(smr) ( true )") ;
end if;
 
assign t240 = self.btrue or smr.r11btrue ;
if ( t240 == true )
  LOG::LogSuccess(message:" or self.attribute other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.attribute other.referential_attribute(smr) ( true )") ;
end if;
 
end for;


  // self.referential attribute    local existing
assign t241 = self.r9bfalse or temp1 ;
if ( t241 == false )
  LOG::LogSuccess(message:" or self.referential_attribute local_existing ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute local_existing ( false )") ;
end if;
 
assign t242 = self.r9btrue or temp1 ;
if ( t242 == true )
  LOG::LogSuccess(message:" or self.referential_attribute local_existing ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute local_existing ( true )") ;
end if;
 
assign t243 = self.r9bfalse or temp2 ;
if ( t243 == true )
  LOG::LogSuccess(message:" or self.referential_attribute local_existing ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute local_existing ( true )") ;
end if;
 
assign t244 = self.r9btrue or temp2 ;
if ( t244 == true )
  LOG::LogSuccess(message:" or self.referential_attribute local_existing ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute local_existing ( true )") ;
end if;
 
  // self.referential_attribute   rcvd_evt 
assign t245 = self.r9bfalse or rcvd_evt.tfalse ;
if ( t245 == false )
  LOG::LogSuccess(message:" or self.referential_attribute rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute rcvd_evt ( false )") ;
end if;
 
assign t246 = self.r9btrue or rcvd_evt.tfalse ;
if ( t246 == true )
  LOG::LogSuccess(message:" or self.referential_attribute rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute rcvd_evt ( true )") ;
end if;
 
assign t247 = self.r9bfalse or rcvd_evt.ttrue ;
if ( t247 == true )
  LOG::LogSuccess(message:" or self.referential_attribute rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute rcvd_evt ( true )") ;
end if;
 
assign t248 = self.r9btrue or rcvd_evt.ttrue ;
if ( t248 == true )
  LOG::LogSuccess(message:" or self.referential_attribute rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute rcvd_evt ( true )") ;
end if;
 
  // self.referential_attribute    constant
assign t249 = self.r9bfalse or false ;
if ( t249 == false )
  LOG::LogSuccess(message:" or self.referential_attribute constant ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute constant ( false )") ;
end if;
 
assign t250 = self.r9btrue or false ;
if ( t250 == true )
  LOG::LogSuccess(message:" or self.referential_attribute constant ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute constant ( true )") ;
end if;
 
assign t251 = self.r9bfalse or true ;
if ( t251 == true )
  LOG::LogSuccess(message:" or self.referential_attribute constant ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute constant ( true )") ;
end if;
 
assign t252 = self.r9btrue or true ;
if ( t252 == true )
  LOG::LogSuccess(message:" or self.referential_attribute constant ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute constant ( true )") ;
end if;
 
  // self.referential_attribute    self.attribute
assign t253 = self.r9bfalse or self.bfalse ;
if ( t253 == false )
  LOG::LogSuccess(message:" or self.referential_attribute self.attribute ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute self.attribute ( false )") ;
end if;
 
assign t254 = self.r9btrue or self.bfalse ;
if ( t254 == true )
  LOG::LogSuccess(message:" or self.referential_attribute self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute self.attribute ( true )") ;
end if;
 
assign t255 = self.r9bfalse or self.btrue ;
if ( t255 == true )
  LOG::LogSuccess(message:" or self.referential_attribute self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute self.attribute ( true )") ;
end if;
 
assign t256 = self.r9btrue or self.btrue ;
if ( t256 == true )
  LOG::LogSuccess(message:" or self.referential_attribute self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute self.attribute ( true )") ;
end if;
 
  // self.referential_attribute    self.referential attribute
assign t257 = self.r9bfalse or self.r9bfalse ;
if ( t257 == false )
  LOG::LogSuccess(message:" or self.referential_attribute self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute self.referential_attribute ( false )") ;
end if;
 
assign t258 = self.r9btrue or self.r9bfalse ;
if ( t258 == true )
  LOG::LogSuccess(message:" or self.referential_attribute self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute self.referential_attribute ( true )") ;
end if;
 
assign t259 = self.r9bfalse or self.r9btrue ;
if ( t259 == true )
  LOG::LogSuccess(message:" or self.referential_attribute self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute self.referential_attribute ( true )") ;
end if;
 
assign t260 = self.r9btrue or self.r9btrue ;
if ( t260 == true )
  LOG::LogSuccess(message:" or self.referential_attribute self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute self.referential_attribute ( true )") ;
end if;
 
  // self.referential_attribute    other.attribute (saf)
assign t261 = self.r9bfalse or saf.bfalse ;
if ( t261 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(saf) ( false )") ;
end if;
 
assign t262 = self.r9bfalse or saf.btrue ;
if ( t262 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(saf) ( true )") ;
end if;
 
assign t263 = self.r9btrue or saf.bfalse ;
if ( t263 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(saf) ( true )") ;
end if;
 
assign t264 = self.r9btrue or saf.btrue ;
if ( t264 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(saf) ( true )") ;
end if;
 
  // self.referential_attribute    other.attribute (smf)
for each smf in smfs
assign t265 = self.r9bfalse or smf.bfalse ;
if ( t265 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(smf) ( false )") ;
end if;
 
assign t266 = self.r9bfalse or smf.btrue ;
if ( t266 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(smf) ( true )") ;
end if;
 
assign t267 = self.r9btrue or smf.bfalse ;
if ( t267 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(smf) ( true )") ;
end if;
 
assign t268 = self.r9btrue or smf.btrue ;
if ( t268 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(smf) ( true )") ;
end if;
 
end for;
  // self.referential_attribute    other.attribute (sor)
assign t269 = self.r9bfalse or sor.bfalse ;
if ( t269 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(sor) ( false )") ;
end if;
 
assign t270 = self.r9bfalse or sor.btrue ;
if ( t270 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(sor) ( true )") ;
end if;
 
assign t271 = self.r9btrue or sor.bfalse ;
if ( t271 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(sor) ( true )") ;
end if;
 
assign t272 = self.r9btrue or sor.btrue ;
if ( t272 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(sor) ( true )") ;
end if;
 
  // self.referential_attribute    other.attribute (sar)
assign t273 = self.r9bfalse or sar.bfalse ;
if ( t273 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(sar) ( false )") ;
end if;
 
assign t274 = self.r9bfalse or sar.btrue ;
if ( t274 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(sar) ( true )") ;
end if;
 
assign t275 = self.r9btrue or sar.bfalse ;
if ( t275 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(sar) ( true )") ;
end if;
 
assign t276 = self.r9btrue or sar.btrue ;
if ( t276 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(sar) ( true )") ;
end if;
 
  // self.referential_attribute    other.attribute (smr)
for each smr in smrs
assign t277 = self.r9bfalse or smr.bfalse ;
if ( t277 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(smr) ( false )") ;
end if;
 
assign t278 = self.r9bfalse or smr.btrue ;
if ( t278 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(smr) ( true )") ;
end if;
 
assign t279 = self.r9btrue or smr.bfalse ;
if ( t279 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(smr) ( true )") ;
end if;
 
assign t280 = self.r9btrue or smr.btrue ;
if ( t280 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.attribute(smr) ( true )") ;
end if;
 
end for;
  // self.referential_attribute    other.referential attribute (saf)
assign t281 = self.r9bfalse or saf.r11bfalse ;
if ( t281 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(saf) ( false )") ;
end if;
 
assign t282 = self.r9bfalse or saf.r11btrue ;
if ( t282 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(saf) ( true )") ;
end if;
 
assign t283 = self.r9btrue or saf.r11bfalse ;
if ( t283 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(saf) ( true )") ;
end if;
 
assign t284 = self.r9btrue or saf.r11btrue ;
if ( t284 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(saf) ( true )") ;
end if;
 
  // self.referential_attribute    other.referential attribute (smf)
for each smf in smfs
assign t285 = self.r9bfalse or smf.r11bfalse ;
if ( t285 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(smf) ( false )") ;
end if;
 
assign t286 = self.r9bfalse or smf.r11btrue ;
if ( t286 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(smf) ( true )") ;
end if;
 
assign t287 = self.r9btrue or smf.r11bfalse ;
if ( t287 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(smf) ( true )") ;
end if;
 
assign t288 = self.r9btrue or smf.r11btrue ;
if ( t288 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // self.referential_attribute    other.referential attribute (sor)
assign t289 = self.r9bfalse or sor.r11bfalse ;
if ( t289 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(sor) ( false )") ;
end if;
 
assign t290 = self.r9bfalse or sor.r11btrue ;
if ( t290 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(sor) ( true )") ;
end if;
 
assign t291 = self.r9btrue or sor.r11bfalse ;
if ( t291 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(sor) ( true )") ;
end if;
 
assign t292 = self.r9btrue or sor.r11btrue ;
if ( t292 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(sor) ( true )") ;
end if;
 
  // self.referential_attribute    other.referential attribute (sar)
assign t293 = self.r9bfalse or sar.r11bfalse ;
if ( t293 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(sar) ( false )") ;
end if;
 
assign t294 = self.r9bfalse or sar.r11btrue ;
if ( t294 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(sar) ( true )") ;
end if;
 
assign t295 = self.r9btrue or sar.r11bfalse ;
if ( t295 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(sar) ( true )") ;
end if;
 
assign t296 = self.r9btrue or sar.r11btrue ;
if ( t296 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(sar) ( true )") ;
end if;
 
  // self.referential_attribute    other.referential attribute (smr)
for each smr in smrs
assign t297 = self.r9bfalse or smr.r11bfalse ;
if ( t297 == false )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(smr) ( false )") ;
end if;
 
assign t298 = self.r9bfalse or smr.r11btrue ;
if ( t298 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(smr) ( true )") ;
end if;
 
assign t299 = self.r9btrue or smr.r11bfalse ;
if ( t299 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(smr) ( true )") ;
end if;
 
assign t300 = self.r9btrue or smr.r11btrue ;
if ( t300 == true )
  LOG::LogSuccess(message:" or self.referential_attribute other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or self.referential_attribute other.referential_attribute(smr) ( true )") ;
end if;
 
end for;

 
//generate event to continue test
Generate UBT4:''Continue or test''( ttrue: true, tfalse: false ) to self;


',
	'');
INSERT INTO SM_STATE
	VALUES (3145734,
	3145734,
	0,
	'Or test 2',
	6,
	0);
INSERT INTO SM_CH
	VALUES (3145734,
	3145729,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145729,
	3145734,
	0);
INSERT INTO SM_CH
	VALUES (3145734,
	3145730,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145730,
	3145734,
	0);
INSERT INTO SM_CH
	VALUES (3145734,
	3145731,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145731,
	3145734,
	0);
INSERT INTO SM_CH
	VALUES (3145734,
	3145732,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145732,
	3145734,
	0);
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
	'//or operation  (only boolean type)

assign temp1 = false;
assign temp2 = true;

select any saf from instances of UOBT;
select many smfs from instances of UOBT;
select one sor related by self->UOBT[R12];
select any sar related by self->UOBT[R13];
select many smrs related by self->UOBT[R13];

  // other.attribute (saf)    local existing
assign t301 = saf.bfalse or temp1 ;
if ( t301 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) local_existing ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) local_existing ( false )") ;
end if;
 
assign t302 = saf.btrue or temp1 ;
if ( t302 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) local_existing ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) local_existing ( true )") ;
end if;
 
assign t303 = saf.bfalse or temp2 ;
if ( t303 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) local_existing ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) local_existing ( true )") ;
end if;
 
assign t304 = saf.btrue or temp2 ;
if ( t304 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) local_existing ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) local_existing ( true )") ;
end if;
 
  // other.attribute(saf)   rcvd_evt 
assign t305 = saf.bfalse or rcvd_evt.tfalse ;
if ( t305 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) rcvd_evt ( false )") ;
end if;
 
assign t306 = saf.btrue or rcvd_evt.tfalse ;
if ( t306 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) rcvd_evt ( true )") ;
end if;
 
assign t307 = saf.bfalse or rcvd_evt.ttrue ;
if ( t307 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) rcvd_evt ( true )") ;
end if;
 
assign t308 = saf.btrue or rcvd_evt.ttrue ;
if ( t308 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) rcvd_evt ( true )") ;
end if;
 
  // other.attribute(saf)    constant
assign t309 = saf.bfalse or false ;
if ( t309 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) constant ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) constant ( false )") ;
end if;
 
assign t310 = saf.btrue or false ;
if ( t310 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) constant ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) constant ( true )") ;
end if;
 
assign t311 = saf.bfalse or true ;
if ( t311 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) constant ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) constant ( true )") ;
end if;
 
assign t312 = saf.btrue or true ;
if ( t312 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) constant ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) constant ( true )") ;
end if;
 
  // other.attribute(saf)    self.attribute
assign t313 = saf.bfalse or self.bfalse ;
if ( t313 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) self.attribute ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) self.attribute ( false )") ;
end if;
 
assign t314 = saf.btrue or self.bfalse ;
if ( t314 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) self.attribute ( true )") ;
end if;
 
assign t315 = saf.bfalse or self.btrue ;
if ( t315 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) self.attribute ( true )") ;
end if;
 
assign t316 = saf.btrue or self.btrue ;
if ( t316 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) self.attribute ( true )") ;
end if;
 
  // other.attribute(saf)    self.referential attribute
assign t317 = saf.bfalse or self.r9bfalse ;
if ( t317 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) self.referential_attribute ( false )") ;
end if;
 
assign t318 = saf.btrue or self.r9bfalse ;
if ( t318 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) self.referential_attribute ( true )") ;
end if;
 
assign t319 = saf.bfalse or self.r9btrue ;
if ( t319 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) self.referential_attribute ( true )") ;
end if;
 
assign t320 = saf.btrue or self.r9btrue ;
if ( t320 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) self.referential_attribute ( true )") ;
end if;
 
  // other.attribute(saf)    other.attribute (saf)
assign t321 = saf.bfalse or saf.bfalse ;
if ( t321 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(saf) ( false )") ;
end if;
 
assign t322 = saf.bfalse or saf.btrue ;
if ( t322 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(saf) ( true )") ;
end if;
 
assign t323 = saf.btrue or saf.bfalse ;
if ( t323 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(saf) ( true )") ;
end if;
 
assign t324 = saf.btrue or saf.btrue ;
if ( t324 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(saf) ( true )") ;
end if;
 
  // other.attribute(saf)    other.attribute (smf)
for each smf in smfs
assign t325 = saf.bfalse or smf.bfalse ;
if ( t325 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(smf) ( false )") ;
end if;
 
assign t326 = saf.bfalse or smf.btrue ;
if ( t326 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(smf) ( true )") ;
end if;
 
assign t327 = saf.btrue or smf.bfalse ;
if ( t327 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(smf) ( true )") ;
end if;
 
assign t328 = saf.btrue or smf.btrue ;
if ( t328 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(smf) ( true )") ;
end if;
 
end for;
  // other.attribute(saf)    other.attribute (sor)
assign t329 = saf.bfalse or sor.bfalse ;
if ( t329 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(sor) ( false )") ;
end if;
 
assign t330 = saf.bfalse or sor.btrue ;
if ( t330 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(sor) ( true )") ;
end if;
 
assign t331 = saf.btrue or sor.bfalse ;
if ( t331 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(sor) ( true )") ;
end if;
 
assign t332 = saf.btrue or sor.btrue ;
if ( t332 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(sor) ( true )") ;
end if;
 
  // other.attribute(saf)    other.attribute (sar)
assign t333 = saf.bfalse or sar.bfalse ;
if ( t333 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(sar) ( false )") ;
end if;
 
assign t334 = saf.bfalse or sar.btrue ;
if ( t334 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(sar) ( true )") ;
end if;
 
assign t335 = saf.btrue or sar.bfalse ;
if ( t335 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(sar) ( true )") ;
end if;
 
assign t336 = saf.btrue or sar.btrue ;
if ( t336 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(sar) ( true )") ;
end if;
 
  // other.attribute(saf)    other.attribute (smr)
for each smr in smrs
assign t337 = saf.bfalse or smr.bfalse ;
if ( t337 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(smr) ( false )") ;
end if;
 
assign t338 = saf.bfalse or smr.btrue ;
if ( t338 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(smr) ( true )") ;
end if;
 
assign t339 = saf.btrue or smr.bfalse ;
if ( t339 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(smr) ( true )") ;
end if;
 
assign t340 = saf.btrue or smr.btrue ;
if ( t340 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.attribute(smr) ( true )") ;
end if;
 
end for;
  // other.attribute(saf)    other.referential attribute (saf)
assign t341 = saf.bfalse or saf.r11bfalse ;
if ( t341 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(saf) ( false )") ;
end if;
 
assign t342 = saf.bfalse or saf.r11btrue ;
if ( t342 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(saf) ( true )") ;
end if;
 
assign t343 = saf.btrue or saf.r11bfalse ;
if ( t343 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(saf) ( true )") ;
end if;
 
assign t344 = saf.btrue or saf.r11btrue ;
if ( t344 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(saf) ( true )") ;
end if;
 
  // other.attribute(saf)    other.referential attribute (smf)
for each smf in smfs
assign t345 = saf.bfalse or smf.r11bfalse ;
if ( t345 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(smf) ( false )") ;
end if;
 
assign t346 = saf.bfalse or smf.r11btrue ;
if ( t346 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(smf) ( true )") ;
end if;
 
assign t347 = saf.btrue or smf.r11bfalse ;
if ( t347 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(smf) ( true )") ;
end if;
 
assign t348 = saf.btrue or smf.r11btrue ;
if ( t348 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // other.attribute(saf)    other.referential attribute (sor)
assign t349 = saf.bfalse or sor.r11bfalse ;
if ( t349 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(sor) ( false )") ;
end if;
 
assign t350 = saf.bfalse or sor.r11btrue ;
if ( t350 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(sor) ( true )") ;
end if;
 
assign t351 = saf.btrue or sor.r11bfalse ;
if ( t351 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(sor) ( true )") ;
end if;
 
assign t352 = saf.btrue or sor.r11btrue ;
if ( t352 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(sor) ( true )") ;
end if;
 
  // other.attribute(saf)    other.referential attribute (sar)
assign t353 = saf.bfalse or sar.r11bfalse ;
if ( t353 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(sar) ( false )") ;
end if;
 
assign t354 = saf.bfalse or sar.r11btrue ;
if ( t354 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(sar) ( true )") ;
end if;
 
assign t355 = saf.btrue or sar.r11bfalse ;
if ( t355 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(sar) ( true )") ;
end if;
 
assign t356 = saf.btrue or sar.r11btrue ;
if ( t356 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(sar) ( true )") ;
end if;
 
  // other.attribute(saf)    other.referential attribute (smr)
for each smr in smrs
assign t357 = saf.bfalse or smr.r11bfalse ;
if ( t357 == false )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(smr) ( false )") ;
end if;
 
assign t358 = saf.bfalse or smr.r11btrue ;
if ( t358 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(smr) ( true )") ;
end if;
 
assign t359 = saf.btrue or smr.r11bfalse ;
if ( t359 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(smr) ( true )") ;
end if;
 
assign t360 = saf.btrue or smr.r11btrue ;
if ( t360 == true )
  LOG::LogSuccess(message:" or other.attribute(saf) other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.attribute(saf) other.referential_attribute(smr) ( true )") ;
end if;
 
end for;
  // other.attribute (smf)    local existing
  // other.attribute (smf)    constant
  // other.attribute (smf)    constant
  // other.attribute (smf)    self.attribute
  // other.attribute (smf)    self.referential attribute
  // other.attribute (smf)    other.attribute (saf)
  // other.attribute (smf)    other.attribute (smf)
  // other.attribute (smf)    other.attribute (sor)
  // other.attribute (smf)    other.attribute (sar)
  // other.attribute (smf)    other.attribute (smr)
  // other.attribute (smf)    other.referential attribute (saf)
  // other.attribute (smf)    other.referential attribute (smf)
  // other.attribute (smf)    other.referential attribute (sor)
  // other.attribute (smf)    other.referential attribute (sar)
  // other.attribute (smf)    other.referential attribute (smr)
  // other.attribute (sor)    local existing
  // other.attribute (sor)    constant
  // other.attribute (sor)    constant
  // other.attribute (sor)    self.attribute
  // other.attribute (sor)    self.referential attribute
  // other.attribute (sor)    other.attribute (saf)
  // other.attribute (sor)    other.attribute (smf)
  // other.attribute (sor)    other.attribute (sor)
  // other.attribute (sor)    other.attribute (sar)
  // other.attribute (sor)    other.attribute (smr)
  // other.attribute (sor)    other.referential attribute (saf)
  // other.attribute (sor)    other.referential attribute (smf)
  // other.attribute (sor)    other.referential attribute (sor)
  // other.attribute (sor)    other.referential attribute (sar)
  // other.attribute (sor)    other.referential attribute (smr)
  // other.attribute (sar)    local existing
  // other.attribute (sar)    constant
  // other.attribute (sar)    constant
  // other.attribute (sar)    self.attribute
  // other.attribute (sar)    self.referential attribute
  // other.attribute (sar)    other.attribute (saf)
  // other.attribute (sar)    other.attribute (smf)
  // other.attribute (sar)    other.attribute (sor)
  // other.attribute (sar)    other.attribute (sar)
  // other.attribute (sar)    other.attribute (smr)
  // other.attribute (sar)    other.referential attribute (saf)
  // other.attribute (sar)    other.referential attribute (smf)
  // other.attribute (sar)    other.referential attribute (sor)
  // other.attribute (sar)    other.referential attribute (sar)
  // other.attribute (sar)    other.referential attribute (smr)
  // other.attribute (smr)    local existing
  // other.attribute (smr)    constant
  // other.attribute (smr)    constant
  // other.attribute (smr)    self.attribute
  // other.attribute (smr)    self.referential attribute
  // other.attribute (smr)    other.attribute (saf)
  // other.attribute (smr)    other.attribute (smf)
  // other.attribute (smr)    other.attribute (sor)
  // other.attribute (smr)    other.attribute (sar)
  // other.attribute (smr)    other.attribute (smr)
  // other.attribute (smr)    other.referential attribute (saf)
  // other.attribute (smr)    other.referential attribute (smf)
  // other.attribute (smr)    other.referential attribute (sor)
  // other.attribute (smr)    other.referential attribute (sar)
  // other.attribute (smr)    other.referential attribute (smr)
  // other.referential attribute (saf)    local existing
assign t601 = saf.r11bfalse or temp1 ;
if ( t601 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) local_existing ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) local_existing ( false )") ;
end if;
 
assign t602 = saf.r11btrue or temp1 ;
if ( t602 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) local_existing ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) local_existing ( true )") ;
end if;
 
assign t603 = saf.r11bfalse or temp2 ;
if ( t603 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) local_existing ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) local_existing ( true )") ;
end if;
 
assign t604 = saf.r11btrue or temp2 ;
if ( t604 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) local_existing ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) local_existing ( true )") ;
end if;
 
  // other.referential_attribute(saf)   rcvd_evt 
assign t605 = saf.r11bfalse or rcvd_evt.tfalse ;
if ( t605 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) rcvd_evt ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) rcvd_evt ( false )") ;
end if;
 
assign t606 = saf.r11btrue or rcvd_evt.tfalse ;
if ( t606 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) rcvd_evt ( true )") ;
end if;
 
assign t607 = saf.r11bfalse or rcvd_evt.ttrue ;
if ( t607 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) rcvd_evt ( true )") ;
end if;
 
assign t608 = saf.r11btrue or rcvd_evt.ttrue ;
if ( t608 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) rcvd_evt ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) rcvd_evt ( true )") ;
end if;
 
  // other.referential_attribute(saf)    constant
assign t609 = saf.r11bfalse or false ;
if ( t609 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) constant ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) constant ( false )") ;
end if;
 
assign t610 = saf.r11btrue or false ;
if ( t610 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) constant ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) constant ( true )") ;
end if;
 
assign t611 = saf.r11bfalse or true ;
if ( t611 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) constant ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) constant ( true )") ;
end if;
 
assign t612 = saf.r11btrue or true ;
if ( t612 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) constant ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) constant ( true )") ;
end if;
 
  // other.referential_attribute(saf)    self.attribute
assign t613 = saf.r11bfalse or self.bfalse ;
if ( t613 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) self.attribute ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) self.attribute ( false )") ;
end if;
 
assign t614 = saf.r11btrue or self.bfalse ;
if ( t614 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) self.attribute ( true )") ;
end if;
 
assign t615 = saf.r11bfalse or self.btrue ;
if ( t615 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) self.attribute ( true )") ;
end if;
 
assign t616 = saf.r11btrue or self.btrue ;
if ( t616 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) self.attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) self.attribute ( true )") ;
end if;
 
  // other.referential_attribute(saf)    self.referential attribute
assign t617 = saf.r11bfalse or self.r9bfalse ;
if ( t617 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) self.referential_attribute ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) self.referential_attribute ( false )") ;
end if;
 
assign t618 = saf.r11btrue or self.r9bfalse ;
if ( t618 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) self.referential_attribute ( true )") ;
end if;
 
assign t619 = saf.r11bfalse or self.r9btrue ;
if ( t619 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) self.referential_attribute ( true )") ;
end if;
 
assign t620 = saf.r11btrue or self.r9btrue ;
if ( t620 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) self.referential_attribute ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) self.referential_attribute ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.attribute (saf)
assign t621 = saf.r11bfalse or saf.bfalse ;
if ( t621 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(saf) ( false )") ;
end if;
 
assign t622 = saf.r11bfalse or saf.btrue ;
if ( t622 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(saf) ( true )") ;
end if;
 
assign t623 = saf.r11btrue or saf.bfalse ;
if ( t623 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(saf) ( true )") ;
end if;
 
assign t624 = saf.r11btrue or saf.btrue ;
if ( t624 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(saf) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.attribute (smf)
for each smf in smfs
assign t625 = saf.r11bfalse or smf.bfalse ;
if ( t625 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(smf) ( false )") ;
end if;
 
assign t626 = saf.r11bfalse or smf.btrue ;
if ( t626 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(smf) ( true )") ;
end if;
 
assign t627 = saf.r11btrue or smf.bfalse ;
if ( t627 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(smf) ( true )") ;
end if;
 
assign t628 = saf.r11btrue or smf.btrue ;
if ( t628 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(smf) ( true )") ;
end if;
 
end for;
  // other.referential_attribute(saf)    other.attribute (sor)
assign t629 = saf.r11bfalse or sor.bfalse ;
if ( t629 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(sor) ( false )") ;
end if;
 
assign t630 = saf.r11bfalse or sor.btrue ;
if ( t630 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(sor) ( true )") ;
end if;
 
assign t631 = saf.r11btrue or sor.bfalse ;
if ( t631 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(sor) ( true )") ;
end if;
 
assign t632 = saf.r11btrue or sor.btrue ;
if ( t632 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(sor) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.attribute (sar)
assign t633 = saf.r11bfalse or sar.bfalse ;
if ( t633 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(sar) ( false )") ;
end if;
 
assign t634 = saf.r11bfalse or sar.btrue ;
if ( t634 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(sar) ( true )") ;
end if;
 
assign t635 = saf.r11btrue or sar.bfalse ;
if ( t635 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(sar) ( true )") ;
end if;
 
assign t636 = saf.r11btrue or sar.btrue ;
if ( t636 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(sar) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.attribute (smr)
for each smr in smrs
assign t637 = saf.r11bfalse or smr.bfalse ;
if ( t637 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(smr) ( false )") ;
end if;
 
assign t638 = saf.r11bfalse or smr.btrue ;
if ( t638 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(smr) ( true )") ;
end if;
 
assign t639 = saf.r11btrue or smr.bfalse ;
if ( t639 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(smr) ( true )") ;
end if;
 
assign t640 = saf.r11btrue or smr.btrue ;
if ( t640 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.attribute(smr) ( true )") ;
end if;
 
end for;
  // other.referential_attribute(saf)    other.referential attribute (saf)
assign t641 = saf.r11bfalse or saf.r11bfalse ;
if ( t641 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(saf) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(saf) ( false )") ;
end if;
 
assign t642 = saf.r11bfalse or saf.r11btrue ;
if ( t642 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(saf) ( true )") ;
end if;
 
assign t643 = saf.r11btrue or saf.r11bfalse ;
if ( t643 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(saf) ( true )") ;
end if;
 
assign t644 = saf.r11btrue or saf.r11btrue ;
if ( t644 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(saf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(saf) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.referential attribute (smf)
for each smf in smfs
assign t645 = saf.r11bfalse or smf.r11bfalse ;
if ( t645 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(smf) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(smf) ( false )") ;
end if;
 
assign t646 = saf.r11bfalse or smf.r11btrue ;
if ( t646 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(smf) ( true )") ;
end if;
 
assign t647 = saf.r11btrue or smf.r11bfalse ;
if ( t647 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(smf) ( true )") ;
end if;
 
assign t648 = saf.r11btrue or smf.r11btrue ;
if ( t648 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(smf) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(smf) ( true )") ;
end if;
 
end for;
  // other.referential_attribute(saf)    other.referential attribute (sor)
assign t649 = saf.r11bfalse or sor.r11bfalse ;
if ( t649 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(sor) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(sor) ( false )") ;
end if;
 
assign t650 = saf.r11bfalse or sor.r11btrue ;
if ( t650 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(sor) ( true )") ;
end if;
 
assign t651 = saf.r11btrue or sor.r11bfalse ;
if ( t651 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(sor) ( true )") ;
end if;
 
assign t652 = saf.r11btrue or sor.r11btrue ;
if ( t652 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(sor) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(sor) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.referential attribute (sar)
assign t653 = saf.r11bfalse or sar.r11bfalse ;
if ( t653 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(sar) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(sar) ( false )") ;
end if;
 
assign t654 = saf.r11bfalse or sar.r11btrue ;
if ( t654 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(sar) ( true )") ;
end if;
 
assign t655 = saf.r11btrue or sar.r11bfalse ;
if ( t655 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(sar) ( true )") ;
end if;
 
assign t656 = saf.r11btrue or sar.r11btrue ;
if ( t656 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(sar) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(sar) ( true )") ;
end if;
 
  // other.referential_attribute(saf)    other.referential attribute (smr)
for each smr in smrs
assign t657 = saf.r11bfalse or smr.r11bfalse ;
if ( t657 == false )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(smr) ( false )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(smr) ( false )") ;
end if;
 
assign t658 = saf.r11bfalse or smr.r11btrue ;
if ( t658 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(smr) ( true )") ;
end if;
 
assign t659 = saf.r11btrue or smr.r11bfalse ;
if ( t659 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(smr) ( true )") ;
end if;
 
assign t660 = saf.r11btrue or smr.r11btrue ;
if ( t660 == true )
  LOG::LogSuccess(message:" or other.referential_attribute(saf) other.referential_attribute(smr) ( true )") ;
else
  LOG::LogFailure(message:" or other.referential_attribute(saf) other.referential_attribute(smr) ( true )") ;
end if;
 
end for;
  // other.referential attribute (saf)    constant
  // other.referential attribute (saf)    constant
  // other.referential attribute (saf)    self.attribute
  // other.referential attribute (saf)    self.referential attribute
  // other.referential attribute (saf)    other.attribute (saf)
  // other.referential attribute (saf)    other.attribute (smf)
  // other.referential attribute (saf)    other.attribute (sor)
  // other.referential attribute (saf)    other.attribute (sar)
  // other.referential attribute (saf)    other.attribute (smr)
  // other.referential attribute (saf)    other.referential attribute (saf)
  // other.referential attribute (saf)    other.referential attribute (smf)
  // other.referential attribute (saf)    other.referential attribute (sor)
  // other.referential attribute (saf)    other.referential attribute (sar)
  // other.referential attribute (saf)    other.referential attribute (smr)
  // other.referential attribute (smf)    local existing
  // other.referential attribute (smf)    constant
  // other.referential attribute (smf)    constant
  // other.referential attribute (smf)    self.attribute
  // other.referential attribute (smf)    self.referential attribute
  // other.referential attribute (smf)    other.attribute (saf)
  // other.referential attribute (smf)    other.attribute (smf)
  // other.referential attribute (smf)    other.attribute (sor)
  // other.referential attribute (smf)    other.attribute (sar)
  // other.referential attribute (smf)    other.attribute (smr)
  // other.referential attribute (smf)    other.referential attribute (saf)
  // other.referential attribute (smf)    other.referential attribute (smf)
  // other.referential attribute (smf)    other.referential attribute (sor)
  // other.referential attribute (smf)    other.referential attribute (sar)
  // other.referential attribute (smf)    other.referential attribute (smr)
  // other.referential attribute (sor)    local existing
  // other.referential attribute (sor)    constant
  // other.referential attribute (sor)    constant
  // other.referential attribute (sor)    self.attribute
  // other.referential attribute (sor)    self.referential attribute
  // other.referential attribute (sor)    other.attribute (saf)
  // other.referential attribute (sor)    other.attribute (smf)
  // other.referential attribute (sor)    other.attribute (sor)
  // other.referential attribute (sor)    other.attribute (sar)
  // other.referential attribute (sor)    other.attribute (smr)
  // other.referential attribute (sor)    other.referential attribute (saf)
  // other.referential attribute (sor)    other.referential attribute (smf)
  // other.referential attribute (sor)    other.referential attribute (sor)
  // other.referential attribute (sor)    other.referential attribute (sar)
  // other.referential attribute (sor)    other.referential attribute (smr)
  // other.referential attribute (sar)    local existing
  // other.referential attribute (sar)    constant
  // other.referential attribute (sar)    constant
  // other.referential attribute (sar)    self.attribute
  // other.referential attribute (sar)    self.referential attribute
  // other.referential attribute (sar)    other.attribute (saf)
  // other.referential attribute (sar)    other.attribute (smf)
  // other.referential attribute (sar)    other.attribute (sor)
  // other.referential attribute (sar)    other.attribute (sar)
  // other.referential attribute (sar)    other.attribute (smr)
  // other.referential attribute (sar)    other.referential attribute (saf)
  // other.referential attribute (sar)    other.referential attribute (smf)
  // other.referential attribute (sar)    other.referential attribute (sor)
  // other.referential attribute (sar)    other.referential attribute (sar)
  // other.referential attribute (sar)    other.referential attribute (smr)
  // other.referential attribute (sar)    local existing
  // other.referential attribute (smr)    constant
  // other.referential attribute (smr)    constant
  // other.referential attribute (smr)    self.attribute
  // other.referential attribute (smr)    self.referential attribute
  // other.referential attribute (smr)    other.attribute (saf)
  // other.referential attribute (smr)    other.attribute (smf)
  // other.referential attribute (smr)    other.attribute (sor)
  // other.referential attribute (smr)    other.attribute (sar)
  // other.referential attribute (smr)    other.attribute (smr)
  // other.referential attribute (smr)    other.referential attribute (saf)
  // other.referential attribute (smr)    other.referential attribute (smf)
  // other.referential attribute (smr)    other.referential attribute (sor)
  // other.referential attribute (smr)    other.referential attribute (sar)
  // other.referential attribute (smr)    other.referential attribute (smr)
 
LOG::LogInfo(message:"Completed or (user-defined type) test") ;

select any ev from instances of EV;
generate EV2:''shutdown'' to ev;
 
 

',
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
	3145729,
	0);
INSERT INTO SM_NSTXN
	VALUES (3145730,
	3145734,
	3145731,
	3145731,
	0);
INSERT INTO SM_TXN
	VALUES (3145730,
	3145734,
	3145732,
	0);
INSERT INTO SM_NSTXN
	VALUES (3145731,
	3145734,
	3145729,
	3145730,
	0);
INSERT INTO SM_TXN
	VALUES (3145731,
	3145734,
	3145730,
	0);
INSERT INTO SM_NSTXN
	VALUES (3145732,
	3145734,
	3145730,
	3145730,
	0);
INSERT INTO SM_TXN
	VALUES (3145732,
	3145734,
	3145731,
	0);
INSERT INTO SM_NSTXN
	VALUES (3145733,
	3145734,
	3145732,
	3145732,
	0);
INSERT INTO SM_TXN
	VALUES (3145733,
	3145734,
	3145733,
	0);
INSERT INTO SM_NSTXN
	VALUES (3145734,
	3145734,
	3145733,
	3145732,
	0);
INSERT INTO SM_TXN
	VALUES (3145734,
	3145734,
	3145734,
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
	4200,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (3145730,
	3145729,
	3145729,
	41);
INSERT INTO GD_SHP
	VALUES (3145730,
	1728,
	1264,
	1872,
	1344);
INSERT INTO GD_GE
	VALUES (3145731,
	3145729,
	3145730,
	41);
INSERT INTO GD_SHP
	VALUES (3145731,
	1728,
	1456,
	1872,
	1536);
INSERT INTO GD_GE
	VALUES (3145732,
	3145729,
	3145731,
	41);
INSERT INTO GD_SHP
	VALUES (3145732,
	1728,
	1648,
	1872,
	1728);
INSERT INTO GD_GE
	VALUES (3145733,
	3145729,
	3145732,
	41);
INSERT INTO GD_SHP
	VALUES (3145733,
	2032,
	1296,
	2160,
	1360);
INSERT INTO GD_GE
	VALUES (3145734,
	3145729,
	3145733,
	41);
INSERT INTO GD_SHP
	VALUES (3145734,
	2032,
	1456,
	2160,
	1536);
INSERT INTO GD_GE
	VALUES (3145735,
	3145729,
	3145734,
	41);
INSERT INTO GD_SHP
	VALUES (3145735,
	2032,
	1648,
	2160,
	1728);
INSERT INTO GD_GE
	VALUES (3145736,
	3145729,
	3145729,
	42);
INSERT INTO GD_CON
	VALUES (3145736,
	3145730,
	3145730,
	0);
INSERT INTO GD_CTXT
	VALUES (3145736,
	0,
	0,
	0,
	0,
	0,
	0,
	1821,
	1186,
	2073,
	1227,
	-66,
	-5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145737,
	3145736,
	1872,
	1296,
	1920,
	1296,
	0);
INSERT INTO GD_LS
	VALUES (3145738,
	3145736,
	1920,
	1296,
	1920,
	1216,
	3145737);
INSERT INTO GD_LS
	VALUES (3145739,
	3145736,
	1920,
	1216,
	1824,
	1216,
	3145738);
INSERT INTO GD_LS
	VALUES (3145740,
	3145736,
	1824,
	1216,
	1824,
	1264,
	3145739);
INSERT INTO GD_GE
	VALUES (3145741,
	3145729,
	3145731,
	42);
INSERT INTO GD_CON
	VALUES (3145741,
	3145730,
	3145731,
	0);
INSERT INTO GD_CTXT
	VALUES (3145741,
	0,
	0,
	0,
	0,
	0,
	0,
	1545,
	1389,
	1793,
	1426,
	-231,
	4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145742,
	3145741,
	1792,
	1344,
	1792,
	1456,
	0);
INSERT INTO GD_GE
	VALUES (3145743,
	3145729,
	3145732,
	42);
INSERT INTO GD_CON
	VALUES (3145743,
	3145731,
	3145732,
	0);
INSERT INTO GD_CTXT
	VALUES (3145743,
	0,
	0,
	0,
	0,
	0,
	0,
	1564,
	1575,
	1790,
	1619,
	-212,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145744,
	3145743,
	1792,
	1536,
	1792,
	1648,
	0);
INSERT INTO GD_GE
	VALUES (3145745,
	3145729,
	3145733,
	42);
INSERT INTO GD_CON
	VALUES (3145745,
	3145733,
	3145734,
	0);
INSERT INTO GD_CTXT
	VALUES (3145745,
	0,
	0,
	0,
	0,
	0,
	0,
	2067,
	1394,
	2341,
	1432,
	-13,
	1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145746,
	3145745,
	2096,
	1360,
	2096,
	1456,
	0);
INSERT INTO GD_GE
	VALUES (3145747,
	3145729,
	3145734,
	42);
INSERT INTO GD_CON
	VALUES (3145747,
	3145734,
	3145735,
	0);
INSERT INTO GD_CTXT
	VALUES (3145747,
	0,
	0,
	0,
	0,
	0,
	0,
	2065,
	1584,
	2337,
	1625,
	-15,
	7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145748,
	3145747,
	2096,
	1536,
	2096,
	1648,
	0);
INSERT INTO GD_GE
	VALUES (3145749,
	3145729,
	3145730,
	42);
INSERT INTO GD_CON
	VALUES (3145749,
	3145732,
	3145733,
	0);
INSERT INTO GD_CTXT
	VALUES (3145749,
	0,
	0,
	0,
	0,
	0,
	0,
	2057,
	1221,
	2292,
	1266,
	105,
	-196,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145750,
	3145749,
	1872,
	1696,
	1968,
	1696,
	0);
INSERT INTO GD_LS
	VALUES (3145751,
	3145749,
	1968,
	1696,
	1968,
	1248,
	3145750);
INSERT INTO GD_LS
	VALUES (3145752,
	3145749,
	1968,
	1248,
	2096,
	1248,
	3145751);
INSERT INTO GD_LS
	VALUES (3145753,
	3145749,
	2096,
	1248,
	2096,
	1296,
	3145752);
INSERT INTO O_OBJ
	VALUES (1048589,
	'User Other Boolean Test',
	19,
	'UOBT',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048670,
	1048589);
INSERT INTO O_BATTR
	VALUES (1048670,
	1048589);
INSERT INTO O_ATTR
	VALUES (1048670,
	1048589,
	0,
	'bfalse',
	'',
	'',
	'bfalse',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (1048671,
	1048589);
INSERT INTO O_BATTR
	VALUES (1048671,
	1048589);
INSERT INTO O_ATTR
	VALUES (1048671,
	1048589,
	1048670,
	'btrue',
	'',
	'',
	'btrue',
	0,
	524305);
INSERT INTO O_REF
	VALUES (1048589,
	1048588,
	0,
	1048664,
	1048588,
	1048600,
	1048599,
	1048672,
	1048609,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048672,
	1048589,
	1048664,
	1048588,
	1);
INSERT INTO O_ATTR
	VALUES (1048672,
	1048589,
	1048671,
	'id',
	'',
	'',
	'id',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1048589,
	1048590,
	0,
	1048675,
	1048589,
	1048601,
	1048602,
	1048673,
	1048610,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048673,
	1048589,
	1048675,
	1048590,
	1);
INSERT INTO O_ATTR
	VALUES (1048673,
	1048589,
	1048672,
	'r11btrue',
	'',
	'r11',
	'btrue',
	1,
	524296);
INSERT INTO O_REF
	VALUES (1048589,
	1048590,
	0,
	1048676,
	1048589,
	1048601,
	1048602,
	1048674,
	1048611,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048674,
	1048589,
	1048676,
	1048590,
	1);
INSERT INTO O_ATTR
	VALUES (1048674,
	1048589,
	1048673,
	'r11bfalse',
	'',
	'r11',
	'bfalse',
	1,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048589);
INSERT INTO O_OIDA
	VALUES (1048671,
	1048589,
	0);
INSERT INTO O_RTIDA
	VALUES (1048671,
	1048589,
	0,
	1048587,
	1048598);
INSERT INTO O_OIDA
	VALUES (1048670,
	1048589,
	0);
INSERT INTO O_RTIDA
	VALUES (1048670,
	1048589,
	0,
	1048587,
	1048598);
INSERT INTO O_OBJ
	VALUES (1048590,
	'User Third Boolean Test',
	20,
	'UTBT',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048675,
	1048590);
INSERT INTO O_BATTR
	VALUES (1048675,
	1048590);
INSERT INTO O_ATTR
	VALUES (1048675,
	1048590,
	0,
	'btrue',
	'',
	'',
	'btrue',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (1048676,
	1048590);
INSERT INTO O_BATTR
	VALUES (1048676,
	1048590);
INSERT INTO O_ATTR
	VALUES (1048676,
	1048590,
	1048675,
	'bfalse',
	'',
	'',
	'bfalse',
	0,
	524305);
INSERT INTO O_ID
	VALUES (0,
	1048590);
INSERT INTO O_OIDA
	VALUES (1048675,
	1048590,
	0);
INSERT INTO O_RTIDA
	VALUES (1048675,
	1048590,
	0,
	1048589,
	1048602);
INSERT INTO O_OIDA
	VALUES (1048676,
	1048590,
	0);
INSERT INTO O_RTIDA
	VALUES (1048676,
	1048590,
	0,
	1048589,
	1048602);
INSERT INTO O_OBJ
	VALUES (1048591,
	'Event Instance',
	21,
	'EV',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048677,
	1048591);
INSERT INTO O_BATTR
	VALUES (1048677,
	1048591);
INSERT INTO O_ATTR
	VALUES (1048677,
	1048591,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048678,
	1048591);
INSERT INTO O_BATTR
	VALUES (1048678,
	1048591);
INSERT INTO O_ATTR
	VALUES (1048678,
	1048591,
	1048677,
	'shutdown_count',
	'',
	'',
	'shutdown_count',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048679,
	1048591);
INSERT INTO O_BATTR
	VALUES (1048679,
	1048591);
INSERT INTO O_ATTR
	VALUES (1048679,
	1048591,
	1048678,
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
	VALUES (1048677,
	1048591,
	0);
INSERT INTO SM_ISM
	VALUES (3670023,
	1048591);
INSERT INTO SM_SM
	VALUES (3670023,
	'',
	7);
INSERT INTO SM_MOORE
	VALUES (3670023);
INSERT INTO SM_EVTDI
	VALUES (3670017,
	3670023,
	'message',
	'',
	524293);
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
	1,
	'Event Instance',
	0,
	'',
	'EV1',
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
	2,
	'shutdown',
	0,
	'',
	'EV2',
	'');
INSERT INTO SM_STATE
	VALUES (3670017,
	3670023,
	0,
	'dummy',
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
	'',
	'');
INSERT INTO SM_STATE
	VALUES (3670018,
	3670023,
	0,
	'shutdown',
	2,
	0);
INSERT INTO SM_SEME
	VALUES (3670018,
	3670017,
	3670023,
	0);
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
	'self.shutdown_count = self.shutdown_count - 1;

if ( self.shutdown_count == 0 )
  bridge ARCH::shutdown();
end if;',
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
	3670017,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670018,
	3670023,
	3670018,
	3670018,
	0);
INSERT INTO SM_TXN
	VALUES (3670018,
	3670023,
	3670018,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670019,
	3670023,
	3670018,
	3670017,
	0);
INSERT INTO SM_TXN
	VALUES (3670019,
	3670023,
	3670017,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670020,
	3670023,
	3670017,
	3670018,
	0);
INSERT INTO SM_TXN
	VALUES (3670020,
	3670023,
	3670018,
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
	4200,
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
	1328,
	2144,
	1456);
INSERT INTO GD_GE
	VALUES (3670019,
	3670017,
	3670018,
	41);
INSERT INTO GD_SHP
	VALUES (3670019,
	1856,
	1056,
	2144,
	1184);
INSERT INTO GD_GE
	VALUES (3670020,
	3670017,
	3670017,
	42);
INSERT INTO GD_CON
	VALUES (3670020,
	3670018,
	3670018,
	0);
INSERT INTO GD_CTXT
	VALUES (3670020,
	0,
	0,
	0,
	0,
	0,
	0,
	1920,
	1243,
	2204,
	1302,
	-7,
	-28,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670021,
	3670020,
	1888,
	1328,
	1888,
	1296,
	0);
INSERT INTO GD_LS
	VALUES (3670022,
	3670020,
	1888,
	1296,
	2016,
	1296,
	3670021);
INSERT INTO GD_LS
	VALUES (3670023,
	3670020,
	2016,
	1296,
	2016,
	1328,
	3670022);
INSERT INTO GD_GE
	VALUES (3670024,
	3670017,
	3670018,
	42);
INSERT INTO GD_CON
	VALUES (3670024,
	3670019,
	3670019,
	0);
INSERT INTO GD_CTXT
	VALUES (3670024,
	0,
	0,
	0,
	0,
	0,
	0,
	1983,
	954,
	2127,
	998,
	0,
	-29,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670025,
	3670024,
	1920,
	1056,
	1920,
	1008,
	0);
INSERT INTO GD_LS
	VALUES (3670026,
	3670024,
	1920,
	1008,
	2096,
	1008,
	3670025);
INSERT INTO GD_LS
	VALUES (3670027,
	3670024,
	2096,
	1008,
	2096,
	1056,
	3670026);
INSERT INTO GD_GE
	VALUES (3670028,
	3670017,
	3670020,
	42);
INSERT INTO GD_CON
	VALUES (3670028,
	3670018,
	3670019,
	0);
INSERT INTO GD_CTXT
	VALUES (3670028,
	0,
	0,
	0,
	0,
	0,
	0,
	2247,
	1247,
	2381,
	1289,
	23,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670029,
	3670028,
	2144,
	1408,
	2240,
	1408,
	0);
INSERT INTO GD_LS
	VALUES (3670030,
	3670028,
	2240,
	1408,
	2240,
	1120,
	3670029);
INSERT INTO GD_LS
	VALUES (3670031,
	3670028,
	2240,
	1120,
	2144,
	1120,
	3670030);
INSERT INTO GD_GE
	VALUES (3670032,
	3670017,
	3670019,
	42);
INSERT INTO GD_CON
	VALUES (3670032,
	3670019,
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
	1778,
	1237,
	1914,
	1281,
	18,
	-4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670033,
	3670032,
	1856,
	1120,
	1776,
	1120,
	0);
INSERT INTO GD_LS
	VALUES (3670034,
	3670032,
	1776,
	1120,
	1776,
	1392,
	3670033);
INSERT INTO GD_LS
	VALUES (3670035,
	3670032,
	1776,
	1392,
	1856,
	1392,
	3670034);
INSERT INTO R_SIMP
	VALUES (1048577);
INSERT INTO R_REL
	VALUES (1048577,
	1,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048583,
	1048577,
	1048577,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (1048583,
	1048577,
	1048577,
	0);
INSERT INTO R_OIR
	VALUES (1048583,
	1048577,
	1048577,
	0);
INSERT INTO R_FORM
	VALUES (1048578,
	1048577,
	1048578,
	0,
	1,
	'');
INSERT INTO R_RGO
	VALUES (1048578,
	1048577,
	1048578);
INSERT INTO R_OIR
	VALUES (1048578,
	1048577,
	1048578,
	0);
INSERT INTO R_SIMP
	VALUES (1048578);
INSERT INTO R_REL
	VALUES (1048578,
	2,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048578,
	1048578,
	1048579,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (1048578,
	1048578,
	1048579,
	0);
INSERT INTO R_OIR
	VALUES (1048578,
	1048578,
	1048579,
	0);
INSERT INTO R_FORM
	VALUES (1048579,
	1048578,
	1048580,
	1,
	1,
	'');
INSERT INTO R_RGO
	VALUES (1048579,
	1048578,
	1048580);
INSERT INTO R_OIR
	VALUES (1048579,
	1048578,
	1048580,
	0);
INSERT INTO R_SIMP
	VALUES (1048579);
INSERT INTO R_REL
	VALUES (1048579,
	3,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048578,
	1048579,
	1048581,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (1048578,
	1048579,
	1048581,
	0);
INSERT INTO R_OIR
	VALUES (1048578,
	1048579,
	1048581,
	0);
INSERT INTO R_FORM
	VALUES (1048580,
	1048579,
	1048582,
	0,
	0,
	'');
INSERT INTO R_RGO
	VALUES (1048580,
	1048579,
	1048582);
INSERT INTO R_OIR
	VALUES (1048580,
	1048579,
	1048582,
	0);
INSERT INTO R_SIMP
	VALUES (1048580);
INSERT INTO R_REL
	VALUES (1048580,
	4,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048578,
	1048580,
	1048583,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (1048578,
	1048580,
	1048583,
	0);
INSERT INTO R_OIR
	VALUES (1048578,
	1048580,
	1048583,
	0);
INSERT INTO R_FORM
	VALUES (1048581,
	1048580,
	1048584,
	1,
	0,
	'');
INSERT INTO R_RGO
	VALUES (1048581,
	1048580,
	1048584);
INSERT INTO R_OIR
	VALUES (1048581,
	1048580,
	1048584,
	0);
INSERT INTO R_SIMP
	VALUES (1048581);
INSERT INTO R_REL
	VALUES (1048581,
	5,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048578,
	1048581,
	1048585,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (1048578,
	1048581,
	1048585,
	0);
INSERT INTO R_OIR
	VALUES (1048578,
	1048581,
	1048585,
	0);
INSERT INTO R_FORM
	VALUES (1048579,
	1048581,
	1048586,
	0,
	1,
	'');
INSERT INTO R_RGO
	VALUES (1048579,
	1048581,
	1048586);
INSERT INTO R_OIR
	VALUES (1048579,
	1048581,
	1048586,
	0);
INSERT INTO R_SIMP
	VALUES (1048582);
INSERT INTO R_REL
	VALUES (1048582,
	6,
	'',
	1048578);
INSERT INTO R_FORM
	VALUES (1048583,
	1048582,
	1048587,
	0,
	0,
	'');
INSERT INTO R_RGO
	VALUES (1048583,
	1048582,
	1048587);
INSERT INTO R_OIR
	VALUES (1048583,
	1048582,
	1048587,
	0);
INSERT INTO R_PART
	VALUES (1048584,
	1048582,
	1048588,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (1048584,
	1048582,
	1048588,
	0);
INSERT INTO R_OIR
	VALUES (1048584,
	1048582,
	1048588,
	0);
INSERT INTO R_SIMP
	VALUES (1048583);
INSERT INTO R_REL
	VALUES (1048583,
	8,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048578,
	1048583,
	1048589,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (1048578,
	1048583,
	1048589,
	0);
INSERT INTO R_OIR
	VALUES (1048578,
	1048583,
	1048589,
	0);
INSERT INTO R_FORM
	VALUES (1048583,
	1048583,
	1048590,
	1,
	0,
	'');
INSERT INTO R_RGO
	VALUES (1048583,
	1048583,
	1048590);
INSERT INTO R_OIR
	VALUES (1048583,
	1048583,
	1048590,
	0);
INSERT INTO R_SIMP
	VALUES (1048584);
INSERT INTO R_REL
	VALUES (1048584,
	9,
	'',
	1048578);
INSERT INTO R_FORM
	VALUES (1048585,
	1048584,
	1048591,
	0,
	1,
	'');
INSERT INTO R_RGO
	VALUES (1048585,
	1048584,
	1048591);
INSERT INTO R_OIR
	VALUES (1048585,
	1048584,
	1048591,
	0);
INSERT INTO R_PART
	VALUES (1048586,
	1048584,
	1048592,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (1048586,
	1048584,
	1048592,
	0);
INSERT INTO R_OIR
	VALUES (1048586,
	1048584,
	1048592,
	0);
INSERT INTO R_SIMP
	VALUES (1048585);
INSERT INTO R_REL
	VALUES (1048585,
	10,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048585,
	1048585,
	1048593,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (1048585,
	1048585,
	1048593,
	0);
INSERT INTO R_OIR
	VALUES (1048585,
	1048585,
	1048593,
	0);
INSERT INTO R_FORM
	VALUES (1048586,
	1048585,
	1048594,
	1,
	0,
	'');
INSERT INTO R_RGO
	VALUES (1048586,
	1048585,
	1048594);
INSERT INTO R_OIR
	VALUES (1048586,
	1048585,
	1048594,
	0);
INSERT INTO R_SIMP
	VALUES (1048586);
INSERT INTO R_REL
	VALUES (1048586,
	11,
	'',
	1048578);
INSERT INTO R_FORM
	VALUES (1048586,
	1048586,
	1048595,
	0,
	0,
	'');
INSERT INTO R_RGO
	VALUES (1048586,
	1048586,
	1048595);
INSERT INTO R_OIR
	VALUES (1048586,
	1048586,
	1048595,
	0);
INSERT INTO R_PART
	VALUES (1048587,
	1048586,
	1048596,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (1048587,
	1048586,
	1048596,
	0);
INSERT INTO R_OIR
	VALUES (1048587,
	1048586,
	1048596,
	0);
INSERT INTO R_SIMP
	VALUES (1048587);
INSERT INTO R_REL
	VALUES (1048587,
	12,
	'',
	1048578);
INSERT INTO R_FORM
	VALUES (1048588,
	1048587,
	1048597,
	0,
	1,
	'');
INSERT INTO R_RGO
	VALUES (1048588,
	1048587,
	1048597);
INSERT INTO R_OIR
	VALUES (1048588,
	1048587,
	1048597,
	0);
INSERT INTO R_PART
	VALUES (1048589,
	1048587,
	1048598,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (1048589,
	1048587,
	1048598,
	0);
INSERT INTO R_OIR
	VALUES (1048589,
	1048587,
	1048598,
	0);
INSERT INTO R_SIMP
	VALUES (1048588);
INSERT INTO R_REL
	VALUES (1048588,
	13,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048588,
	1048588,
	1048599,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (1048588,
	1048588,
	1048599,
	0);
INSERT INTO R_OIR
	VALUES (1048588,
	1048588,
	1048599,
	0);
INSERT INTO R_FORM
	VALUES (1048589,
	1048588,
	1048600,
	1,
	0,
	'');
INSERT INTO R_RGO
	VALUES (1048589,
	1048588,
	1048600);
INSERT INTO R_OIR
	VALUES (1048589,
	1048588,
	1048600,
	0);
INSERT INTO R_SIMP
	VALUES (1048589);
INSERT INTO R_REL
	VALUES (1048589,
	14,
	'',
	1048578);
INSERT INTO R_FORM
	VALUES (1048589,
	1048589,
	1048601,
	0,
	0,
	'');
INSERT INTO R_RGO
	VALUES (1048589,
	1048589,
	1048601);
INSERT INTO R_OIR
	VALUES (1048589,
	1048589,
	1048601,
	0);
INSERT INTO R_PART
	VALUES (1048590,
	1048589,
	1048602,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (1048590,
	1048589,
	1048602,
	0);
INSERT INTO R_OIR
	VALUES (1048590,
	1048589,
	1048602,
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
	707,
	3353,
	0.433884,
	0);
INSERT INTO GD_GE
	VALUES (1048584,
	1048581,
	1048577,
	21);
INSERT INTO GD_SHP
	VALUES (1048584,
	1648,
	1216,
	1872,
	1360);
INSERT INTO GD_GE
	VALUES (1048585,
	1048581,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048585,
	1648,
	1392,
	1888,
	1856);
INSERT INTO GD_GE
	VALUES (1048586,
	1048581,
	1048579,
	21);
INSERT INTO GD_SHP
	VALUES (1048586,
	2112,
	1392,
	2304,
	1504);
INSERT INTO GD_GE
	VALUES (1048587,
	1048581,
	1048580,
	21);
INSERT INTO GD_SHP
	VALUES (1048587,
	2112,
	1520,
	2304,
	1632);
INSERT INTO GD_GE
	VALUES (1048588,
	1048581,
	1048581,
	21);
INSERT INTO GD_SHP
	VALUES (1048588,
	2112,
	1664,
	2304,
	1776);
INSERT INTO GD_GE
	VALUES (1048589,
	1048581,
	1048582,
	21);
INSERT INTO GD_SHP
	VALUES (1048589,
	1920,
	1216,
	2112,
	1376);
INSERT INTO GD_GE
	VALUES (1048590,
	1048581,
	1048583,
	21);
INSERT INTO GD_SHP
	VALUES (1048590,
	1648,
	1952,
	1920,
	2320);
INSERT INTO GD_GE
	VALUES (1048591,
	1048581,
	1048584,
	21);
INSERT INTO GD_SHP
	VALUES (1048591,
	1648,
	2432,
	1904,
	2624);
INSERT INTO GD_GE
	VALUES (1048592,
	1048581,
	1048585,
	21);
INSERT INTO GD_SHP
	VALUES (1048592,
	1344,
	1536,
	1616,
	1696);
INSERT INTO GD_GE
	VALUES (1048593,
	1048581,
	1048586,
	21);
INSERT INTO GD_SHP
	VALUES (1048593,
	1344,
	1856,
	1600,
	2048);
INSERT INTO GD_GE
	VALUES (1048594,
	1048581,
	1048587,
	21);
INSERT INTO GD_SHP
	VALUES (1048594,
	1360,
	2160,
	1600,
	2320);
INSERT INTO GD_GE
	VALUES (1048595,
	1048581,
	1048588,
	21);
INSERT INTO GD_SHP
	VALUES (1048595,
	960,
	1536,
	1264,
	1696);
INSERT INTO GD_GE
	VALUES (1048596,
	1048581,
	1048589,
	21);
INSERT INTO GD_SHP
	VALUES (1048596,
	960,
	1856,
	1280,
	2064);
INSERT INTO GD_GE
	VALUES (1048597,
	1048581,
	1048590,
	21);
INSERT INTO GD_SHP
	VALUES (1048597,
	976,
	2160,
	1264,
	2336);
INSERT INTO GD_GE
	VALUES (1048598,
	1048581,
	1048591,
	21);
INSERT INTO GD_SHP
	VALUES (1048598,
	960,
	1312,
	1216,
	1472);
INSERT INTO GD_GE
	VALUES (1048626,
	1048581,
	1048577,
	24);
INSERT INTO GD_CON
	VALUES (1048626,
	1048590,
	1048585,
	0);
INSERT INTO GD_CTXT
	VALUES (1048626,
	0,
	0,
	0,
	0,
	0,
	0,
	1744,
	1888,
	1794,
	1918,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048627,
	1048626,
	1760,
	1952,
	1760,
	1856,
	0);
INSERT INTO GD_GE
	VALUES (1048628,
	1048581,
	1048578,
	24);
INSERT INTO GD_CON
	VALUES (1048628,
	1048585,
	1048586,
	0);
INSERT INTO GD_CTXT
	VALUES (1048628,
	0,
	0,
	0,
	0,
	0,
	0,
	1968,
	1408,
	2018,
	1438,
	-7,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048629,
	1048628,
	1888,
	1440,
	2112,
	1440,
	0);
INSERT INTO GD_GE
	VALUES (1048630,
	1048581,
	1048579,
	24);
INSERT INTO GD_CON
	VALUES (1048630,
	1048585,
	1048587,
	0);
INSERT INTO GD_CTXT
	VALUES (1048630,
	0,
	0,
	0,
	0,
	0,
	0,
	1968,
	1552,
	2018,
	1582,
	-7,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048631,
	1048630,
	1888,
	1584,
	2112,
	1584,
	0);
INSERT INTO GD_GE
	VALUES (1048632,
	1048581,
	1048580,
	24);
INSERT INTO GD_CON
	VALUES (1048632,
	1048585,
	1048588,
	0);
INSERT INTO GD_CTXT
	VALUES (1048632,
	0,
	0,
	0,
	0,
	0,
	0,
	1968,
	1696,
	2018,
	1726,
	-7,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048633,
	1048632,
	1888,
	1728,
	2112,
	1728,
	0);
INSERT INTO GD_GE
	VALUES (1048634,
	1048581,
	1048581,
	24);
INSERT INTO GD_CON
	VALUES (1048634,
	1048585,
	1048586,
	0);
INSERT INTO GD_CTXT
	VALUES (1048634,
	0,
	0,
	0,
	0,
	0,
	0,
	1968,
	1440,
	2018,
	1470,
	-7,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048635,
	1048634,
	1888,
	1472,
	2112,
	1472,
	0);
INSERT INTO GD_GE
	VALUES (1048636,
	1048581,
	1048582,
	24);
INSERT INTO GD_CON
	VALUES (1048636,
	1048590,
	1048591,
	0);
INSERT INTO GD_CTXT
	VALUES (1048636,
	0,
	0,
	0,
	0,
	0,
	0,
	1696,
	2344,
	1746,
	2374,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048637,
	1048636,
	1712,
	2320,
	1712,
	2432,
	0);
INSERT INTO GD_GE
	VALUES (1048638,
	1048581,
	1048583,
	24);
INSERT INTO GD_CON
	VALUES (1048638,
	1048585,
	1048590,
	0);
INSERT INTO GD_CTXT
	VALUES (1048638,
	0,
	0,
	0,
	0,
	0,
	0,
	1840,
	1888,
	1890,
	1918,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048639,
	1048638,
	1856,
	1856,
	1856,
	1952,
	0);
INSERT INTO GD_GE
	VALUES (1048640,
	1048581,
	1048584,
	24);
INSERT INTO GD_CON
	VALUES (1048640,
	1048592,
	1048593,
	0);
INSERT INTO GD_CTXT
	VALUES (1048640,
	0,
	0,
	0,
	0,
	0,
	0,
	1390,
	1749,
	1440,
	1779,
	14,
	-4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048641,
	1048640,
	1392,
	1696,
	1392,
	1856,
	0);
INSERT INTO GD_GE
	VALUES (1048642,
	1048581,
	1048585,
	24);
INSERT INTO GD_CON
	VALUES (1048642,
	1048592,
	1048593,
	0);
INSERT INTO GD_CTXT
	VALUES (1048642,
	0,
	0,
	0,
	0,
	0,
	0,
	1552,
	1756,
	1602,
	1786,
	16,
	3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048643,
	1048642,
	1552,
	1696,
	1552,
	1856,
	0);
INSERT INTO GD_GE
	VALUES (1048644,
	1048581,
	1048586,
	24);
INSERT INTO GD_CON
	VALUES (1048644,
	1048593,
	1048594,
	0);
INSERT INTO GD_CTXT
	VALUES (1048644,
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
	VALUES (1048645,
	1048644,
	1456,
	2048,
	1456,
	2160,
	0);
INSERT INTO GD_GE
	VALUES (1048646,
	1048581,
	1048587,
	24);
INSERT INTO GD_CON
	VALUES (1048646,
	1048595,
	1048596,
	0);
INSERT INTO GD_CTXT
	VALUES (1048646,
	0,
	0,
	0,
	0,
	0,
	0,
	1026,
	1761,
	1076,
	1791,
	18,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048647,
	1048646,
	1024,
	1696,
	1024,
	1856,
	0);
INSERT INTO GD_GE
	VALUES (1048648,
	1048581,
	1048588,
	24);
INSERT INTO GD_CON
	VALUES (1048648,
	1048595,
	1048596,
	0);
INSERT INTO GD_CTXT
	VALUES (1048648,
	0,
	0,
	0,
	0,
	0,
	0,
	1194,
	1760,
	1244,
	1790,
	10,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048649,
	1048648,
	1200,
	1696,
	1200,
	1856,
	0);
INSERT INTO GD_GE
	VALUES (1048650,
	1048581,
	1048589,
	24);
INSERT INTO GD_CON
	VALUES (1048650,
	1048596,
	1048597,
	0);
INSERT INTO GD_CTXT
	VALUES (1048650,
	0,
	0,
	0,
	0,
	0,
	0,
	1109,
	2098,
	1159,
	2128,
	21,
	1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048651,
	1048650,
	1104,
	2064,
	1104,
	2160,
	0);
