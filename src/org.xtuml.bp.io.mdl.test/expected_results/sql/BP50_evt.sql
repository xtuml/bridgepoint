-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (2470000,
	'BP50_evt',
	'This test deals with the new features of BP5.0 concering events.

Generates a pre-created event stored in an attribute from :
( All events are generated from instance.attribute unless otherwise indicated )
    - Function
    - Bridge ( wired to Functions in BP50_evt2 )
    - Class Operation
    - Instance Operation
    - State Action ( using self.attribute )
    - State Action
    - MDA
',
	0,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	2470000,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	2470000,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	2470000,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	2470000,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	2470000,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	2470000,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	2470000,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	2470000,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	2470000,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	2470000,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	2470000,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	2470000,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	2470000,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	2470000,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	2470000,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	2470000,
	'inst_ref<Timer>',
	'');
INSERT INTO S_UDT
	VALUES (524305,
	524294,
	0);
INSERT INTO S_DT
	VALUES (524305,
	2470000,
	'arbitrary_id',
	'Arbitrary ID with core data type of unique_id.');
INSERT INTO S_SYNC
	VALUES (524289,
	2470000,
	'Func_gen_evt',
	'',
	'x = param.func_param;
select any e_obj from instances of E_OBJ;
select any o_obj from instances of O_OBJ;
create event instance o of O_OBJ2(count:x+1) to o_obj; 
timer = TIM::timer_start(microseconds:5000000, event_inst:o);
e_obj.my_timer = timer;

create event instance e2 of E_OBJ2:''Event''(count:x+1) to e_obj;
e_obj.my_event = e2;
generate e_obj.my_event; 
',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524313,
	524289,
	'func_param',
	524291,
	1);
INSERT INTO S_EE
	VALUES (524289,
	'Logger',
	'',
	'LOG',
	2470000);
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
	VALUES (524289,
	524290,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524291,
	524289,
	'LogSuccess',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524290,
	524291,
	'message',
	524293,
	0);
INSERT INTO S_EE
	VALUES (524290,
	'Architecture',
	'',
	'ARCH',
	2470000);
INSERT INTO S_BRG
	VALUES (524289,
	524290,
	'shutdown',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_EE
	VALUES (524291,
	'Time',
	'',
	'TIM',
	2470000);
INSERT INTO S_BRG
	VALUES (524292,
	524291,
	'current_date',
	'',
	1,
	524302,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524293,
	524291,
	'create_date',
	'',
	1,
	524302,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524291,
	524293,
	'second',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524292,
	524293,
	'minute',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524293,
	524293,
	'hour',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524294,
	524293,
	'day',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524295,
	524293,
	'month',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524296,
	524293,
	'year',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524294,
	524291,
	'get_second',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524297,
	524294,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524295,
	524291,
	'get_minute',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524298,
	524295,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524296,
	524291,
	'get_hour',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524299,
	524296,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524297,
	524291,
	'get_day',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524300,
	524297,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524298,
	524291,
	'get_month',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524301,
	524298,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524299,
	524291,
	'get_year',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524302,
	524299,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524300,
	524291,
	'current_clock',
	'',
	1,
	524303,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524301,
	524291,
	'timer_start',
	'',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524303,
	524301,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524304,
	524301,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524302,
	524291,
	'timer_start_recurring',
	'',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524305,
	524302,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524306,
	524302,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524303,
	524291,
	'timer_remaining_time',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524307,
	524303,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BRG
	VALUES (524304,
	524291,
	'timer_reset_time',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524308,
	524304,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524309,
	524304,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524305,
	524291,
	'timer_add_time',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524310,
	524305,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524311,
	524305,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524306,
	524291,
	'timer_cancel',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524312,
	524306,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_EE
	VALUES (524292,
	'Extern',
	'',
	'EE',
	2470000);
INSERT INTO S_BRG
	VALUES (524307,
	524292,
	'brdg_gen_evt',
	'x = param.brdg_param;
select any e_obj from instances of E_OBJ;
select any o_obj from instances of O_OBJ;
create event instance e of O_OBJ2:''TimeOut'' to o_obj; 
timer = TIM::timer_start(microseconds:1000000, event_inst:e);
create event instance e2 of E_OBJ2:''Event''(count:x+1, timer:timer) to e_obj;
e_obj.my_event = e2;
generate e_obj.my_event; ',
	0,
	524289,
	'x = param.brdg_param;
select any e_obj from instances of E_OBJ;
select any o_obj from instances of O_OBJ;
create event instance o of O_OBJ2(count:x+2) to o_obj; 
timer = TIM::timer_start(microseconds:5000000, event_inst:o);
e_obj.my_timer = timer;

create event instance e2 of E_OBJ2:''Event''(count:x+2) to e_obj;
e_obj.my_event = e2;
generate e_obj.my_event; ',
	1);
INSERT INTO S_BPARM
	VALUES (524314,
	524307,
	'brdg_param',
	524291,
	1);
INSERT INTO GD_MD
	VALUES (524289,
	1,
	2470000,
	1,
	1,
	0,
	1,
	1,
	0,
	12,
	1647,
	4154,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (524296,
	524289,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524296,
	1680,
	1264,
	1920,
	1392);
INSERT INTO GD_GE
	VALUES (524309,
	524289,
	524289,
	12);
INSERT INTO GD_SHP
	VALUES (524309,
	1680,
	1408,
	1920,
	1536);
INSERT INTO GD_GE
	VALUES (524310,
	524289,
	524290,
	12);
INSERT INTO GD_SHP
	VALUES (524310,
	1968,
	1408,
	2208,
	1536);
INSERT INTO GD_GE
	VALUES (524311,
	524289,
	524291,
	12);
INSERT INTO GD_SHP
	VALUES (524311,
	1680,
	1552,
	1920,
	1680);
INSERT INTO GD_GE
	VALUES (524319,
	524289,
	524292,
	12);
INSERT INTO GD_SHP
	VALUES (524319,
	1968,
	1264,
	2208,
	1392);
INSERT INTO S_SS
	VALUES (1048578,
	'Event_generation',
	'',
	'EVT',
	1,
	2470000,
	1048578);
INSERT INTO O_OBJ
	VALUES (1048577,
	'Initialization Object',
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
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
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
	'create object instance e_obj of E_OBJ;
create object instance o_obj of O_OBJ;
generate E_OBJ1:''Start'' to e_obj;',
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
	4142,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (1572866,
	1572865,
	1572865,
	41);
INSERT INTO GD_SHP
	VALUES (1572866,
	1776,
	1360,
	2016,
	1568);
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
	VALUES (1572868,
	1572867,
	1808,
	1360,
	1808,
	1328,
	0);
INSERT INTO GD_LS
	VALUES (1572869,
	1572867,
	1808,
	1328,
	1984,
	1328,
	1572868);
INSERT INTO GD_LS
	VALUES (1572870,
	1572867,
	1984,
	1328,
	1984,
	1360,
	1572869);
INSERT INTO O_OBJ
	VALUES (1048578,
	'Event Object',
	2,
	'E_OBJ',
	'',
	1048578);
INSERT INTO O_TFR
	VALUES (1048577,
	1048578,
	'op_gen_evt_c',
	'x = param.op_param;
select any e_obj from instances of E_OBJ;
select any o_obj from instances of O_OBJ;
create event instance e of O_OBJ2:''TimeOut'' to o_obj; 
timer = TIM::timer_start(microseconds:1000000, event_inst:e);
create event instance e2 of E_OBJ2:''Event''(count:x+1, timer:timer) to e_obj;
e_obj.my_event = e2;
generate e_obj.my_event;
',
	524289,
	0,
	'x = param.op_param;
select any e_obj from instances of E_OBJ;
select any o_obj from instances of O_OBJ;
create event instance o of O_OBJ2(count:x+1) to o_obj; 
timer = TIM::timer_start(microseconds:5000000, event_inst:o);
e_obj.my_timer = timer;

create event instance e2 of E_OBJ2:''Event''(count:x+1) to e_obj;
e_obj.my_event = e2;
generate e_obj.my_event;',
	1);
INSERT INTO O_TPARM
	VALUES (1048577,
	1048577,
	'op_param',
	524291,
	1);
INSERT INTO O_TFR
	VALUES (1048578,
	1048578,
	'op_gen_evt_i',
	'x = param.op_param;
select any e_obj from instances of E_OBJ;
select any o_obj from instances of O_OBJ;
create event instance e of O_OBJ2:''TimeOut'' to o_obj; 
timer = TIM::timer_start(microseconds:1000000, event_inst:e);
create event instance e2 of E_OBJ2:''Event''(count:x+1, timer:timer) to e_obj;
e_obj.my_event = e2;
generate e_obj.my_event;',
	524289,
	1,
	'x = param.op_param;
select any e_obj from instances of E_OBJ;
select any o_obj from instances of O_OBJ;
create event instance o of O_OBJ2(count:x+1) to o_obj; 
timer = TIM::timer_start(microseconds:5000000, event_inst:o);
e_obj.my_timer = timer;

create event instance e2 of E_OBJ2:''Event''(count:x+1) to e_obj;
e_obj.my_event = e2;
generate e_obj.my_event;',
	1);
INSERT INTO O_TPARM
	VALUES (1048578,
	1048578,
	'op_param',
	524291,
	1);
INSERT INTO O_NBATTR
	VALUES (1048578,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048578,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048578,
	1048578,
	0,
	'my_event',
	'',
	'',
	'my_event',
	0,
	524299);
INSERT INTO O_NBATTR
	VALUES (1048580,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048580,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048580,
	1048578,
	1048578,
	'e_obj_id',
	'',
	'',
	'e_obj_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048579,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048579,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048579,
	1048578,
	1048580,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_DBATTR
	VALUES (1048583,
	1048578,
	'self.mda_gen_evt = 7;
select any e_obj from instances of E_OBJ;
select any o_obj from instances of O_OBJ;
create event instance o of O_OBJ2(count:8) to o_obj; 
timer = TIM::timer_start(microseconds:5000000, event_inst:o);
self.my_timer = timer;

create event instance e2 of E_OBJ2:''Event''(count:8) to e_obj;
e_obj.my_event = e2;
generate e_obj.my_event;
',
	1);
INSERT INTO O_BATTR
	VALUES (1048583,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048583,
	1048578,
	1048579,
	'mda_gen_evt',
	'self.mda_gen_evt = 7;
select any e_obj from instances of E_OBJ;
select any o_obj from instances of O_OBJ;
create event instance e of O_OBJ2:''TimeOut'' to o_obj; 
timer = TIM::timer_start(microseconds:1000000, event_inst:e);
create event instance e2 of E_OBJ2:''Event''(count:8, timer:timer) to e_obj;
e_obj.my_event = e2;
generate e_obj.my_event;',
	'',
	'mda_gen_evt',
	0,
	524291);
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
	'my_timer',
	'',
	'',
	'my_timer',
	0,
	524304);
INSERT INTO O_ID
	VALUES (0,
	1048578);
INSERT INTO O_OIDA
	VALUES (1048580,
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
	VALUES (2097154,
	2097156,
	'count',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (69945632,
	2097156,
	'count',
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
	'Start',
	0,
	'',
	'E_OBJ1',
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
	'Event',
	0,
	'',
	'E_OBJ2',
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
	'TimeOut',
	0,
	'',
	'E_OBJ3',
	'');
INSERT INTO SM_STATE
	VALUES (2097153,
	2097156,
	0,
	'Event_State_1',
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
INSERT INTO SM_SEME
	VALUES (2097153,
	2097155,
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
	'create event instance e2 of E_OBJ3(count:0) to self;
timer = TIM::timer_start(microseconds:5000000, event_inst:e2);
self.my_timer = timer;

create event instance e of E_OBJ2:''Event''(count:0) to self;
self.my_event = e;
generate self.my_event;
',
	'');
INSERT INTO SM_STATE
	VALUES (2097154,
	2097156,
	0,
	'Event_State_2',
	2,
	0);
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
	'bool = TIM::timer_cancel(timer_inst_ref:self.my_timer);
if ( bool == TRUE )
    LOG::LogSuccess(message:"Self-Attribute Event Received in State Action.");
else
    LOG::LogFailure(message:"Self-Attribute Event NOT Received in State Action.");
end if;

count = rcvd_evt.count;

if(count <= 7)
	select any o_obj from instances of O_OBJ;
	generate O_OBJ1:''Start''(count:count) to o_obj;
else
	ARCH::shutdown();
	control stop;
end if;',
	'');
INSERT INTO SM_STATE
	VALUES (2097155,
	2097156,
	0,
	'Timed_Out',
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
INSERT INTO SM_SEME
	VALUES (2097155,
	2097154,
	2097156,
	0);
INSERT INTO SM_EIGN
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
	'generate E_OBJ2(count:rcvd_evt.count) to self;
',
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
INSERT INTO SM_NSTXN
	VALUES (2097154,
	2097156,
	2097153,
	2097154,
	0);
INSERT INTO SM_TXN
	VALUES (2097154,
	2097156,
	2097154,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097155,
	2097156,
	2097154,
	2097154,
	0);
INSERT INTO SM_TXN
	VALUES (2097155,
	2097156,
	2097154,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097156,
	2097156,
	2097153,
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097156,
	2097156,
	2097155,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097158,
	2097156,
	2097155,
	2097154,
	0);
INSERT INTO SM_TXN
	VALUES (2097158,
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
	1654,
	3995,
	0.671540,
	0);
INSERT INTO GD_GE
	VALUES (2097154,
	2097153,
	2097153,
	41);
INSERT INTO GD_SHP
	VALUES (2097154,
	1648,
	1296,
	2176,
	1472);
INSERT INTO GD_GE
	VALUES (2097177,
	2097153,
	2097154,
	41);
INSERT INTO GD_SHP
	VALUES (2097177,
	1648,
	1552,
	2176,
	1840);
INSERT INTO GD_GE
	VALUES (2097184,
	2097153,
	2097155,
	41);
INSERT INTO GD_SHP
	VALUES (2097184,
	2352,
	1328,
	2800,
	1408);
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
	1818,
	1229,
	1919,
	1251,
	-6,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097373,
	2097155,
	1776,
	1296,
	1776,
	1264,
	0);
INSERT INTO GD_LS
	VALUES (2097374,
	2097155,
	1776,
	1264,
	1968,
	1264,
	2097373);
INSERT INTO GD_LS
	VALUES (2097375,
	2097155,
	1968,
	1264,
	1968,
	1296,
	2097374);
INSERT INTO GD_GE
	VALUES (2097178,
	2097153,
	2097154,
	42);
INSERT INTO GD_CON
	VALUES (2097178,
	2097154,
	2097177,
	0);
INSERT INTO GD_CTXT
	VALUES (2097178,
	0,
	0,
	0,
	0,
	0,
	0,
	1904,
	1498,
	2107,
	1520,
	225,
	-5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097504,
	2097178,
	1888,
	1472,
	1888,
	1552,
	0);
INSERT INTO GD_GE
	VALUES (2097180,
	2097153,
	2097155,
	42);
INSERT INTO GD_CON
	VALUES (2097180,
	2097177,
	2097177,
	0);
INSERT INTO GD_CTXT
	VALUES (2097180,
	0,
	0,
	0,
	0,
	0,
	0,
	2239,
	1688,
	2442,
	1710,
	224,
	1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097547,
	2097180,
	2176,
	1808,
	2224,
	1808,
	0);
INSERT INTO GD_LS
	VALUES (2097548,
	2097180,
	2224,
	1808,
	2224,
	1584,
	2097547);
INSERT INTO GD_LS
	VALUES (2097549,
	2097180,
	2224,
	1584,
	2176,
	1584,
	2097548);
INSERT INTO GD_GE
	VALUES (2097185,
	2097153,
	2097156,
	42);
INSERT INTO GD_CON
	VALUES (2097185,
	2097154,
	2097184,
	0);
INSERT INTO GD_CTXT
	VALUES (2097185,
	0,
	0,
	0,
	0,
	0,
	0,
	2202,
	1328,
	2329,
	1350,
	-1,
	-20,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097553,
	2097185,
	2176,
	1376,
	2352,
	1376,
	0);
INSERT INTO GD_GE
	VALUES (2097554,
	2097153,
	2097158,
	42);
INSERT INTO GD_CON
	VALUES (2097554,
	2097184,
	2097177,
	0);
INSERT INTO GD_CTXT
	VALUES (2097554,
	0,
	0,
	0,
	0,
	0,
	0,
	2272,
	1477,
	2409,
	1499,
	183,
	15,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097555,
	2097554,
	2384,
	1408,
	2080,
	1552,
	0);
INSERT INTO O_OBJ
	VALUES (1048579,
	'Other Object',
	3,
	'O_OBJ',
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
	'o_obj_id',
	'',
	'',
	'o_obj_id',
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
	VALUES (2621445,
	1048579);
INSERT INTO SM_SM
	VALUES (2621445,
	'',
	5);
INSERT INTO SM_MOORE
	VALUES (2621445);
INSERT INTO SM_EVTDI
	VALUES (2621441,
	2621445,
	'count',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (86892354,
	2621445,
	'count',
	'',
	524291);
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
	'Start',
	0,
	'',
	'O_OBJ1',
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
	'TimeOut',
	0,
	'',
	'O_OBJ2',
	'');
INSERT INTO SM_STATE
	VALUES (2621441,
	2621445,
	0,
	'Other_State',
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
	'x = rcvd_evt.count;
select any e_obj from instances of E_OBJ;
if(x <= 1)
	create event instance o of O_OBJ2(count:x+1) to self; 
	timer = TIM::timer_start(microseconds:5000000, event_inst:o);
                e_obj.my_timer = timer;
	create event instance e2 of E_OBJ2:''Event''(count:rcvd_evt.count+1) to e_obj;
	e_obj.my_event = e2;
	generate e_obj.my_event;
elif (x == 2)
	EE::brdg_gen_evt(brdg_param:x);
elif (x == 4)
	e_obj.op_gen_evt_i(op_param:x);
elif (x == 5)
	E_OBJ::op_gen_evt_c(op_param:x);
else
	if( e_obj.mda_gen_evt == 7)
		LOG::LogSuccess(message:"Accessing MDA");
	end if;
end if;
',
	'');
INSERT INTO SM_STATE
	VALUES (2621442,
	2621445,
	0,
	'Timed_Out',
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
	'select any e_obj from instances of E_OBJ;
generate E_OBJ2(count:rcvd_evt.count) to e_obj;',
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
INSERT INTO SM_NSTXN
	VALUES (2621443,
	2621445,
	2621442,
	2621442,
	0);
INSERT INTO SM_TXN
	VALUES (2621443,
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
	1642,
	4015,
	0.801897,
	0);
INSERT INTO GD_GE
	VALUES (2621442,
	2621441,
	2621441,
	41);
INSERT INTO GD_SHP
	VALUES (2621442,
	1664,
	1296,
	2384,
	1744);
INSERT INTO GD_GE
	VALUES (2621453,
	2621441,
	2621442,
	41);
INSERT INTO GD_SHP
	VALUES (2621453,
	1664,
	1840,
	2384,
	1952);
INSERT INTO GD_GE
	VALUES (2621443,
	2621441,
	2621441,
	42);
INSERT INTO GD_CON
	VALUES (2621443,
	2621442,
	2621442,
	0);
INSERT INTO GD_CTXT
	VALUES (2621443,
	0,
	0,
	0,
	0,
	0,
	0,
	1947,
	1227,
	2102,
	1249,
	-2,
	-9,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621593,
	2621443,
	1696,
	1296,
	1696,
	1264,
	0);
INSERT INTO GD_LS
	VALUES (2621594,
	2621443,
	1696,
	1264,
	2352,
	1264,
	2621593);
INSERT INTO GD_LS
	VALUES (2621595,
	2621443,
	2352,
	1264,
	2352,
	1296,
	2621594);
INSERT INTO GD_GE
	VALUES (2621454,
	2621441,
	2621442,
	42);
INSERT INTO GD_CON
	VALUES (2621454,
	2621442,
	2621453,
	0);
INSERT INTO GD_CTXT
	VALUES (2621454,
	0,
	0,
	0,
	0,
	0,
	0,
	1962,
	1779,
	2143,
	1801,
	213,
	-4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621605,
	2621454,
	1936,
	1744,
	1936,
	1840,
	0);
INSERT INTO GD_GE
	VALUES (2621606,
	2621441,
	2621443,
	42);
INSERT INTO GD_CON
	VALUES (2621606,
	2621453,
	2621453,
	0);
INSERT INTO GD_CTXT
	VALUES (2621606,
	0,
	0,
	0,
	0,
	0,
	0,
	2441,
	1884,
	2622,
	1906,
	196,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621607,
	2621606,
	2384,
	1936,
	2432,
	1936,
	0);
INSERT INTO GD_LS
	VALUES (2621608,
	2621606,
	2432,
	1936,
	2432,
	1856,
	2621607);
INSERT INTO GD_LS
	VALUES (2621609,
	2621606,
	2432,
	1856,
	2384,
	1856,
	2621608);
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
	1600,
	4142,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (1048588,
	1048581,
	1048577,
	21);
INSERT INTO GD_SHP
	VALUES (1048588,
	1712,
	1280,
	1984,
	1456);
INSERT INTO GD_GE
	VALUES (1048591,
	1048581,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048591,
	1712,
	1504,
	1984,
	1712);
INSERT INTO GD_GE
	VALUES (1048594,
	1048581,
	1048579,
	21);
INSERT INTO GD_SHP
	VALUES (1048594,
	2032,
	1280,
	2272,
	1456);
