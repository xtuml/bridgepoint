-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (308977,
	'im1',
	'This test deals with creating and relating instances of classes.  It also assigns values to attributes, as well as selecting instances and verifying that the assigned values are correct.  It verifies that relate has been implemented properly.',
	0,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	308977,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	308977,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	308977,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	308977,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	308977,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	308977,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	308977,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	308977,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	308977,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	308977,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	308977,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	308977,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	308977,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	308977,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	308977,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	308977,
	'inst_ref<Timer>',
	'');
INSERT INTO S_EE
	VALUES (524290,
	'Time',
	'',
	'TIM',
	308977);
INSERT INTO S_BRG
	VALUES (524304,
	524290,
	'current_date',
	'',
	1,
	524302,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524305,
	524290,
	'create_date',
	'',
	1,
	524302,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524311,
	524305,
	'second',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524312,
	524305,
	'minute',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524313,
	524305,
	'hour',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524314,
	524305,
	'day',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524315,
	524305,
	'month',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524316,
	524305,
	'year',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524306,
	524290,
	'get_second',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524317,
	524306,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524307,
	524290,
	'get_minute',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524318,
	524307,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524308,
	524290,
	'get_hour',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524319,
	524308,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524309,
	524290,
	'get_day',
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
	524290,
	'get_month',
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
	524290,
	'get_year',
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
	524290,
	'current_clock',
	'',
	1,
	524303,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524313,
	524290,
	'timer_start',
	'',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524323,
	524313,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524324,
	524313,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524314,
	524290,
	'timer_start_recurring',
	'',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524325,
	524314,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524326,
	524314,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524315,
	524290,
	'timer_remaining_time',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524327,
	524315,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BRG
	VALUES (524316,
	524290,
	'timer_reset_time',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524328,
	524316,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524329,
	524316,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524317,
	524290,
	'timer_add_time',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524330,
	524317,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524331,
	524317,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524318,
	524290,
	'timer_cancel',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524332,
	524318,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_EE
	VALUES (524291,
	'Architecture',
	'',
	'ARCH',
	308977);
INSERT INTO S_BRG
	VALUES (524319,
	524291,
	'shutdown',
	'',
	0,
	524289,
	'control stop;',
	1);
INSERT INTO S_EE
	VALUES (524292,
	'Logging',
	'',
	'LOG',
	308977);
INSERT INTO S_BRG
	VALUES (524320,
	524292,
	'LogSuccess',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524333,
	524320,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524321,
	524292,
	'LogFailure',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524334,
	524321,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524322,
	524292,
	'LogInfo',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524335,
	524322,
	'message',
	524293,
	0);
INSERT INTO GD_MD
	VALUES (524289,
	1,
	308977,
	1,
	1,
	0,
	1,
	1,
	0,
	12,
	1599,
	4069,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (524357,
	524289,
	8388624,
	11);
INSERT INTO GD_SHP
	VALUES (524357,
	1920,
	1504,
	2080,
	1600);
INSERT INTO GD_GE
	VALUES (524358,
	524289,
	524290,
	12);
INSERT INTO GD_SHP
	VALUES (524358,
	1920,
	1632,
	2080,
	1728);
INSERT INTO GD_GE
	VALUES (524359,
	524289,
	524291,
	12);
INSERT INTO GD_SHP
	VALUES (524359,
	1696,
	1632,
	1888,
	1728);
INSERT INTO GD_GE
	VALUES (524377,
	524289,
	524292,
	12);
INSERT INTO GD_SHP
	VALUES (524377,
	2128,
	1632,
	2304,
	1728);
INSERT INTO GD_MD
	VALUES (524290,
	2,
	308977,
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
	VALUES (524360,
	524290,
	8388624,
	11);
INSERT INTO GD_SHP
	VALUES (524360,
	1920,
	1344,
	2080,
	1440);
INSERT INTO GD_MD
	VALUES (524291,
	3,
	308977,
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
	VALUES (524361,
	524291,
	8388624,
	11);
INSERT INTO GD_SHP
	VALUES (524361,
	1920,
	1344,
	2080,
	1440);
INSERT INTO GD_MD
	VALUES (524292,
	4,
	308977,
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
	VALUES (524362,
	524292,
	8388624,
	11);
INSERT INTO GD_SHP
	VALUES (524362,
	1920,
	1344,
	2080,
	1440);
INSERT INTO S_SS
	VALUES (8388624,
	'im1',
	'',
	'',
	1,
	308977,
	8388624);
INSERT INTO O_OBJ
	VALUES (8388609,
	'im1 init',
	1,
	'INIT',
	'',
	8388624);
INSERT INTO O_NBATTR
	VALUES (8388609,
	8388609);
INSERT INTO O_BATTR
	VALUES (8388609,
	8388609);
INSERT INTO O_ATTR
	VALUES (8388609,
	8388609,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (8388610,
	8388609);
INSERT INTO O_BATTR
	VALUES (8388610,
	8388609);
INSERT INTO O_ATTR
	VALUES (8388610,
	8388609,
	8388609,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	8388609);
INSERT INTO O_OIDA
	VALUES (8388609,
	8388609,
	0);
INSERT INTO SM_ISM
	VALUES (1048578,
	8388609);
INSERT INTO SM_SM
	VALUES (1048578,
	'',
	2);
INSERT INTO SM_MOORE
	VALUES (1048578);
INSERT INTO SM_SUPDT
	VALUES (1048577,
	1048578,
	0);
INSERT INTO SM_STATE
	VALUES (1048577,
	1048578,
	1048577,
	'im1 init',
	1,
	0);
INSERT INTO SM_LEVT
	VALUES (1048577,
	1048578,
	1048577);
INSERT INTO SM_SEVT
	VALUES (1048577,
	1048578,
	1048577);
INSERT INTO SM_EVT
	VALUES (1048577,
	1048578,
	1048577,
	1,
	'start test',
	0,
	'',
	'INIT1',
	'');
INSERT INTO SM_SEME
	VALUES (1048577,
	1048577,
	1048578,
	1048577);
INSERT INTO SM_NSTXN
	VALUES (1048577,
	1048578,
	1048577,
	1048577,
	1048577);
INSERT INTO SM_TXN
	VALUES (1048577,
	1048578,
	1048577,
	1048577);
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
	'generate ICD1:''Start IC Test Suite''() to ICD creator;',
	'');
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
	1776,
	1312,
	2176,
	1536);
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
	1776,
	1376,
	1728,
	1376,
	0);
INSERT INTO GD_LS
	VALUES (1048581,
	1048579,
	1728,
	1376,
	1728,
	1264,
	1048580);
INSERT INTO GD_LS
	VALUES (1048582,
	1048579,
	1728,
	1264,
	1840,
	1264,
	1048581);
INSERT INTO GD_LS
	VALUES (1048583,
	1048579,
	1840,
	1264,
	1840,
	1312,
	1048582);
INSERT INTO O_OBJ
	VALUES (8388611,
	'Object B 1L Side',
	4,
	'OB',
	'',
	8388624);
INSERT INTO O_NBATTR
	VALUES (8388613,
	8388611);
INSERT INTO O_BATTR
	VALUES (8388613,
	8388611);
INSERT INTO O_ATTR
	VALUES (8388613,
	8388611,
	0,
	'ob_id',
	'',
	'',
	'ob_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (8388614,
	8388611);
INSERT INTO O_BATTR
	VALUES (8388614,
	8388611);
INSERT INTO O_ATTR
	VALUES (8388614,
	8388611,
	8388613,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (8388615,
	8388611);
INSERT INTO O_BATTR
	VALUES (8388615,
	8388611);
INSERT INTO O_ATTR
	VALUES (8388615,
	8388611,
	8388614,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	8388611);
INSERT INTO O_OIDA
	VALUES (8388613,
	8388611,
	0);
INSERT INTO O_RTIDA
	VALUES (8388613,
	8388611,
	0,
	8388609,
	8388609);
INSERT INTO SM_ISM
	VALUES (2097156,
	8388611);
INSERT INTO SM_SM
	VALUES (2097156,
	'',
	4);
INSERT INTO SM_MOORE
	VALUES (2097156);
INSERT INTO SM_EVTDI
	VALUES (2097153,
	2097156,
	'id',
	'',
	524294);
INSERT INTO SM_SUPDT
	VALUES (2097153,
	2097156,
	0);
INSERT INTO SM_SUPDT
	VALUES (2097154,
	2097156,
	0);
INSERT INTO SM_SDI
	VALUES (2097153,
	2097154,
	2097156);
INSERT INTO SM_STATE
	VALUES (2097153,
	2097156,
	2097153,
	'Starting IC2 Test',
	2,
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
	'Start IC2 Test',
	0,
	'',
	'OB1',
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
	2097154);
INSERT INTO SM_SEVT
	VALUES (2097154,
	2097156,
	2097154);
INSERT INTO SM_EVT
	VALUES (2097154,
	2097156,
	2097154,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OB2',
	'');
INSERT INTO SM_EIGN
	VALUES (2097153,
	2097154,
	2097156,
	2097154,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097154,
	2097156,
	2097154);
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
	'Finish IC2 Test',
	0,
	'',
	'OB3',
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
	'Continue IC2 Test',
	0,
	'',
	'OB4',
	'');
INSERT INTO SM_CH
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
	2097154,
	'Verifying Rel with Instance',
	3,
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
	2097154,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097154,
	2097156,
	2097154);
INSERT INTO SM_CH
	VALUES (2097154,
	2097155,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097155,
	2097156,
	2097153);
INSERT INTO SM_CH
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
	'Finishing IC2 Test',
	4,
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
	2097154,
	'');
INSERT INTO SM_SEME
	VALUES (2097155,
	2097154,
	2097156,
	2097154);
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
INSERT INTO SM_CH
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
	'Initial State',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (2097156,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_SEME
	VALUES (2097156,
	2097154,
	2097156,
	2097154);
INSERT INTO SM_CH
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
INSERT INTO SM_NSTXN
	VALUES (2097153,
	2097156,
	2097153,
	2097155,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097153,
	2097156,
	2097155,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097154,
	2097156,
	2097156,
	2097154,
	2097154);
INSERT INTO SM_TXN
	VALUES (2097154,
	2097156,
	2097154,
	2097154);
INSERT INTO SM_NSTXN
	VALUES (2097155,
	2097156,
	2097156,
	2097153,
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
	'// select one one_inst related by self-><rel_obj>[REL]
select one oc related by self->OC[R1];
if (oc.i == 42)
  LOG::LogSuccess(message:"IC2: OB - select one inst related by self-><rel_obj>[REL]");
else 
  LOG::LogFailure(message:"IC2: OB - select one inst related by   self-><rel_obj>[REL]");
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select one ob related by self->OC[R1]->OB[R1];
if (ob.i == self.i)
  LOG::LogSuccess(message:"IC2: OB - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]");
else 
  LOG::LogFailure(message:"IC2: OB - select one inst related by   self-><rel_obj>[REL]-><self_obj>[REL]");
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OC2:''Verify Rel with Instance''(id:self.ob_id) to oc;',
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
	'select one oc related by self->OC[R1];
if (oc.oc_id == rcvd_evt.id)
  LOG::LogSuccess(message:"IC2: OB - Verifying Rel with Instance");
  generate OC3:''Finish IC2 Test''() to oc;
else 
  LOG::LogFailure(message:"IC2: OB - Verifying Rel with Instance") ;
end if;',
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
	'//check unrelate
select one oc related by self->OC[R1];
unrelate oc from self across R1;
delete object instance oc;
select one oc related by self->OC[R1];
if (empty oc)
  LOG::LogSuccess(message:"IC2: OB - unrelate object instance <rel_inst>");
else
  LOG::LogFailure(message:"IC2: OB - unrelate object instance <rel_inst>") ;
end if;

select any driver from instances of ICD;
generate ICD3:''IC2 Test B Complete''() to driver;',
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
	'create object instance inst1 of OC;
relate self to inst1 across R1;
assign inst1.i = 42;
assign self.i = 42;

generate OB4:''Continue IC2 Test''() to self;
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
	1680,
	1280,
	2032,
	1568);
INSERT INTO GD_GE
	VALUES (2097155,
	2097153,
	2097154,
	41);
INSERT INTO GD_SHP
	VALUES (2097155,
	2064,
	1280,
	2384,
	1568);
INSERT INTO GD_GE
	VALUES (2097156,
	2097153,
	2097155,
	41);
INSERT INTO GD_SHP
	VALUES (2097156,
	1680,
	1616,
	2064,
	1808);
INSERT INTO GD_GE
	VALUES (2097157,
	2097153,
	2097156,
	41);
INSERT INTO GD_SHP
	VALUES (2097157,
	1680,
	1040,
	2032,
	1168);
INSERT INTO GD_GE
	VALUES (2097158,
	2097153,
	2097153,
	42);
INSERT INTO GD_CON
	VALUES (2097158,
	2097154,
	2097156,
	0);
INSERT INTO GD_CTXT
	VALUES (2097158,
	0,
	0,
	0,
	0,
	0,
	0,
	1824,
	1576,
	1979,
	1613,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097159,
	2097158,
	1840,
	1568,
	1840,
	1616,
	0);
INSERT INTO GD_GE
	VALUES (2097160,
	2097153,
	2097155,
	42);
INSERT INTO GD_CON
	VALUES (2097160,
	2097157,
	2097157,
	0);
INSERT INTO GD_CTXT
	VALUES (2097160,
	0,
	0,
	0,
	0,
	0,
	0,
	1648,
	962,
	1807,
	996,
	32,
	-23,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097161,
	2097160,
	1680,
	1088,
	1632,
	1088,
	0);
INSERT INTO GD_LS
	VALUES (2097162,
	2097160,
	1632,
	1088,
	1632,
	992,
	2097161);
INSERT INTO GD_LS
	VALUES (2097163,
	2097160,
	1632,
	992,
	1712,
	992,
	2097162);
INSERT INTO GD_LS
	VALUES (2097164,
	2097160,
	1712,
	992,
	1712,
	1040,
	2097163);
INSERT INTO GD_GE
	VALUES (2097165,
	2097153,
	2097156,
	42);
INSERT INTO GD_CON
	VALUES (2097165,
	2097157,
	2097154,
	0);
INSERT INTO GD_CTXT
	VALUES (2097165,
	0,
	0,
	0,
	0,
	0,
	0,
	1813,
	1191,
	1971,
	1230,
	21,
	-10,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097166,
	2097165,
	1808,
	1168,
	1808,
	1280,
	0);
INSERT INTO GD_GE
	VALUES (2097167,
	2097153,
	2097154,
	42);
INSERT INTO GD_CON
	VALUES (2097167,
	2097157,
	2097155,
	0);
INSERT INTO GD_CTXT
	VALUES (2097167,
	0,
	0,
	0,
	0,
	0,
	0,
	2182,
	1121,
	2352,
	1165,
	22,
	24,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097168,
	2097167,
	2032,
	1088,
	2176,
	1088,
	0);
INSERT INTO GD_LS
	VALUES (2097169,
	2097167,
	2176,
	1088,
	2176,
	1280,
	2097168);
INSERT INTO O_OBJ
	VALUES (8388612,
	'Object A',
	5,
	'OA',
	'',
	8388624);
INSERT INTO O_NBATTR
	VALUES (8388616,
	8388612);
INSERT INTO O_BATTR
	VALUES (8388616,
	8388612);
INSERT INTO O_ATTR
	VALUES (8388616,
	8388612,
	0,
	'oa_id',
	'',
	'',
	'oa_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (8388617,
	8388612);
INSERT INTO O_BATTR
	VALUES (8388617,
	8388612);
INSERT INTO O_ATTR
	VALUES (8388617,
	8388612,
	8388616,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_NBATTR
	VALUES (8388618,
	8388612);
INSERT INTO O_BATTR
	VALUES (8388618,
	8388612);
INSERT INTO O_ATTR
	VALUES (8388618,
	8388612,
	8388617,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	8388612);
INSERT INTO O_OIDA
	VALUES (8388616,
	8388612,
	0);
INSERT INTO O_RTIDA
	VALUES (8388616,
	8388612,
	0,
	8388614,
	8388620);
INSERT INTO O_RTIDA
	VALUES (8388616,
	8388612,
	0,
	8388613,
	8388619);
INSERT INTO SM_ISM
	VALUES (2621445,
	8388612);
INSERT INTO SM_SM
	VALUES (2621445,
	'',
	5);
INSERT INTO SM_MOORE
	VALUES (2621445);
INSERT INTO SM_SUPDT
	VALUES (2621441,
	2621445,
	0);
INSERT INTO SM_STATE
	VALUES (2621441,
	2621445,
	2621441,
	'IC1 1 Object',
	2,
	0);
INSERT INTO SM_LEVT
	VALUES (2621441,
	2621445,
	2621441);
INSERT INTO SM_SEVT
	VALUES (2621441,
	2621445,
	2621441);
INSERT INTO SM_EVT
	VALUES (2621441,
	2621445,
	2621441,
	1,
	'Start IC1 1 Object No Relationships',
	0,
	'',
	'OA1',
	'');
INSERT INTO SM_CH
	VALUES (2621441,
	2621441,
	2621445,
	2621441,
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621441,
	2621445,
	2621441);
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
	2,
	'IC1 Complete',
	0,
	'',
	'OA2',
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621442,
	2621445,
	2621441);
INSERT INTO SM_LEVT
	VALUES (2621443,
	2621445,
	2621441);
INSERT INTO SM_SEVT
	VALUES (2621443,
	2621445,
	2621441);
INSERT INTO SM_EVT
	VALUES (2621443,
	2621445,
	2621441,
	3,
	'Continue IC1',
	0,
	'',
	'OA3',
	'');
INSERT INTO SM_CH
	VALUES (2621441,
	2621443,
	2621445,
	2621441,
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621443,
	2621445,
	2621441);
INSERT INTO SM_STATE
	VALUES (2621442,
	2621445,
	2621441,
	'IC1 Complete',
	3,
	1);
INSERT INTO SM_CH
	VALUES (2621442,
	2621441,
	2621445,
	2621441,
	'');
INSERT INTO SM_SEME
	VALUES (2621442,
	2621441,
	2621445,
	2621441);
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
INSERT INTO SM_CH
	VALUES (2621442,
	2621443,
	2621445,
	2621441,
	'');
INSERT INTO SM_SEME
	VALUES (2621442,
	2621443,
	2621445,
	2621441);
INSERT INTO SM_STATE
	VALUES (2621443,
	2621445,
	2621441,
	'Initial State',
	1,
	0);
INSERT INTO SM_CH
	VALUES (2621443,
	2621441,
	2621445,
	2621441,
	'');
INSERT INTO SM_SEME
	VALUES (2621443,
	2621441,
	2621445,
	2621441);
INSERT INTO SM_CH
	VALUES (2621443,
	2621442,
	2621445,
	2621441,
	'');
INSERT INTO SM_SEME
	VALUES (2621443,
	2621442,
	2621445,
	2621441);
INSERT INTO SM_SEME
	VALUES (2621443,
	2621443,
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
	2621442,
	2621441);
INSERT INTO SM_CRTXN
	VALUES (2621442,
	2621445,
	2621441,
	2621441);
INSERT INTO SM_TXN
	VALUES (2621442,
	2621445,
	2621443,
	2621441);
INSERT INTO SM_NSTXN
	VALUES (2621443,
	2621445,
	2621443,
	2621443,
	2621441);
INSERT INTO SM_TXN
	VALUES (2621443,
	2621445,
	2621441,
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
	'select any driver from instances of ICD;

select any oa from instances of OA;
if (oa.i == 129)
  LOG::LogSuccess(message:"IC1: select any");
else
 LOG::LogFailure(message:"IC1: select any");
end if;

select many oas from instances of OA;
assign start = cardinality oas;
assign oa1_id = self.oa_id;
if ( true )
  create object instance oa1 of OA;
  assign oa1.i = 129;
  assign oa1_id = oa1.oa_id;
  if (oa1.i == 129)
    LOG::LogSuccess(message:"IC1: assign <self_inst.attr>");
  else
   LOG::LogFailure(message:"IC1: assign <self_inst.attr>");
  end if;

  assign i1 = oa1.i;
  if (i1 == 129)
    LOG::LogSuccess(message:"IC1: assign x = <self_inst.attr>");
  else
   LOG::LogFailure(message:"IC1: assign x = <self_inst.attr>");
  end if;
end if;

if (true)
  create object instance p1 of PO; 
  assign p1.i = 256;
  if (p1.i == 256)
    LOG::LogSuccess(message:"IC1: assign <other_inst.attr>");
  else
   LOG::LogFailure(message:"IC1: assign <other_inst.attr>") ;
  end if;

  assign i1 = p1.i;
  if (i1 == 256)
    LOG::LogSuccess(message:"IC1: assign x = <other_inst.attr>");
  else
   LOG::LogFailure(message:"IC1: assign x = <other_inst.attr>") ;
  end if;

  // start test - selecting passive objects
  create object instance p2 of PO;
  create object instance pb1 of POB; 

  relate self to p1 across R7;
  relate self to p2 across R7;
  relate self to pb1 across R8;

  create object instance p2 of PO;
  relate self to p2 across R7;

end if;

select any po_item from instances of PO;
assign c = cardinality po_item;
if (c == 1)
  LOG::LogSuccess(message:"IC1: select any (passive)");
else
  LOG::LogFailure(message:"IC1: select any (passive)");
end if;

select many po_set from instances of PO;
assign c = cardinality po_set;
if (c > 1)
  LOG::LogSuccess(message:"IC1: select many (passive)");
else
  LOG::LogFailure(message:"IC1: select many (passive)");
end if;

select one pob_item related by self->POB[R8];
assign c = cardinality pob_item;
if (c == 1)
  LOG::LogSuccess(message:"IC1: select one (passive) related by 1:1");
else
  LOG::LogFailure(message:"IC1: select one (passive) related by :1");
end if;

select any po_any related by self->PO[R7];
assign c = cardinality po_any;
if (c == 1)
  LOG::LogSuccess(message:"IC1: select any (passive) related by 1:M");
else
  LOG::LogFailure(message:"IC1: select any (passive) related by 1:M");
end if;

select many po_many related by self->PO[R7];
assign c = cardinality po_many;
if (c > 1)
  LOG::LogSuccess(message:"IC1: select many (passive) related by 1:M") ;
else
  LOG::LogFailure(message:"IC1: select many (passive) related by 1:M") ;
end if;

select many oas from instances of OA;
assign c = cardinality oas;
assign new = start + 1;
if (c == new)
  LOG::LogSuccess(message:"IC1: select many");
else
 LOG::LogFailure(message:"IC1: select many");
end if;

for each oa in oas
  assign i3 = oa.i;
  if (i3 == 129)
    LOG::LogSuccess(message:"IC1: for each");
  else
   LOG::LogFailure(message:"IC1: for each");
  end if;
  if ( oa.oa_id == oa1_id )
    delete object instance oa;
  end if;
end for;

select many oas from instances of OA;
assign c = cardinality oas;
if (c == start)
  LOG::LogSuccess(message:"IC1: delete object instance");
else
 LOG::LogFailure(message:"IC1: delete object instance");
end if;
generate OA2:''IC1 Complete''() to self;
generate ICD2:''IC1 Test Suite Complete''() to driver;
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
	'LOG::LogInfo(message:"IC1 Complete");',
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
	'LOG::LogInfo(message:"OA: Starting IC1 ...");
self.i = 129;
if (self.i == 129)
  LOG::LogSuccess(message:"IC1: assign self.attr");
else
 LOG::LogFailure(message:"IC1: assign self.attr");
end if;

generate OA3:''Continue IC1''() to self;

',
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
	1824,
	1472,
	2160,
	1584);
INSERT INTO GD_GE
	VALUES (2621443,
	2621441,
	2621442,
	41);
INSERT INTO GD_SHP
	VALUES (2621443,
	1824,
	1664,
	2160,
	1776);
INSERT INTO GD_GE
	VALUES (2621444,
	2621441,
	2621443,
	41);
INSERT INTO GD_SHP
	VALUES (2621444,
	1824,
	1296,
	2160,
	1392);
INSERT INTO GD_GE
	VALUES (2621445,
	2621441,
	2621441,
	42);
INSERT INTO GD_CON
	VALUES (2621445,
	2621442,
	2621443,
	0);
INSERT INTO GD_CTXT
	VALUES (2621445,
	0,
	0,
	0,
	0,
	0,
	0,
	1968,
	1608,
	2152,
	1652,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621446,
	2621445,
	1984,
	1584,
	1984,
	1664,
	0);
INSERT INTO GD_GE
	VALUES (2621447,
	2621441,
	2621443,
	42);
INSERT INTO GD_CON
	VALUES (2621447,
	2621444,
	2621442,
	0);
INSERT INTO GD_CTXT
	VALUES (2621447,
	0,
	0,
	0,
	0,
	0,
	0,
	1942,
	1424,
	2161,
	1456,
	-26,
	7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621448,
	2621447,
	1984,
	1392,
	1984,
	1472,
	0);
INSERT INTO GD_GE
	VALUES (2621449,
	2621441,
	2621442,
	42);
INSERT INTO GD_CON
	VALUES (2621449,
	2621444,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (2621449,
	0,
	0,
	0,
	0,
	0,
	0,
	1968,
	1248,
	2256,
	1286,
	0,
	7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621450,
	2621449,
	1984,
	1296,
	1984,
	1216,
	0);
INSERT INTO O_OBJ
	VALUES (8388613,
	'Passive Object ',
	6,
	'PO',
	'',
	8388624);
INSERT INTO O_NBATTR
	VALUES (8388619,
	8388613);
INSERT INTO O_BATTR
	VALUES (8388619,
	8388613);
INSERT INTO O_ATTR
	VALUES (8388619,
	8388613,
	0,
	'po_id',
	'',
	'',
	'po_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (8388620,
	8388613);
INSERT INTO O_BATTR
	VALUES (8388620,
	8388613);
INSERT INTO O_ATTR
	VALUES (8388620,
	8388613,
	8388619,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_REF
	VALUES (8388613,
	8388612,
	0,
	8388616,
	8388613,
	8388618,
	8388619,
	8388621,
	8388609,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (8388621,
	8388613,
	8388616,
	8388612,
	1);
INSERT INTO O_ATTR
	VALUES (8388621,
	8388613,
	8388620,
	'oa_id',
	'',
	'',
	'oa_id',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	8388613);
INSERT INTO O_OIDA
	VALUES (8388619,
	8388613,
	0);
INSERT INTO O_OBJ
	VALUES (8388614,
	'Object C 1R Side',
	7,
	'OC',
	'',
	8388624);
INSERT INTO O_NBATTR
	VALUES (8388622,
	8388614);
INSERT INTO O_BATTR
	VALUES (8388622,
	8388614);
INSERT INTO O_ATTR
	VALUES (8388622,
	8388614,
	0,
	'oc_id',
	'',
	'',
	'oc_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (8388623,
	8388614);
INSERT INTO O_BATTR
	VALUES (8388623,
	8388614);
INSERT INTO O_ATTR
	VALUES (8388623,
	8388614,
	8388622,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_REF
	VALUES (8388614,
	8388611,
	0,
	8388613,
	8388609,
	8388610,
	8388609,
	8388624,
	8388610,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (8388624,
	8388614,
	8388613,
	8388611,
	1);
INSERT INTO O_ATTR
	VALUES (8388624,
	8388614,
	8388623,
	'ob_id',
	'',
	'',
	'ob_id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (8388625,
	8388614);
INSERT INTO O_BATTR
	VALUES (8388625,
	8388614);
INSERT INTO O_ATTR
	VALUES (8388625,
	8388614,
	8388624,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	8388614);
INSERT INTO O_OIDA
	VALUES (8388622,
	8388614,
	0);
INSERT INTO SM_ISM
	VALUES (3145734,
	8388614);
INSERT INTO SM_SM
	VALUES (3145734,
	'',
	6);
INSERT INTO SM_MOORE
	VALUES (3145734);
INSERT INTO SM_EVTDI
	VALUES (3145729,
	3145734,
	'id',
	'',
	524294);
INSERT INTO SM_SUPDT
	VALUES (3145729,
	3145734,
	0);
INSERT INTO SM_SUPDT
	VALUES (3145730,
	3145734,
	0);
INSERT INTO SM_SDI
	VALUES (3145729,
	3145730,
	3145734);
INSERT INTO SM_STATE
	VALUES (3145729,
	3145734,
	3145729,
	'Starting IC2 Test',
	2,
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
	'Start IC2 Test',
	0,
	'',
	'OC1',
	'');
INSERT INTO SM_EIGN
	VALUES (3145729,
	3145729,
	3145734,
	3145729,
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
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OC2',
	'');
INSERT INTO SM_EIGN
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
	3,
	'Finish IC2 Test',
	0,
	'',
	'OC3',
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
	4,
	'Continue IC2 Test',
	0,
	'',
	'OC4',
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
INSERT INTO SM_STATE
	VALUES (3145730,
	3145734,
	3145730,
	'Verifying Rel with Instance',
	3,
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
INSERT INTO SM_CH
	VALUES (3145730,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_CH
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
INSERT INTO SM_STATE
	VALUES (3145731,
	3145734,
	3145729,
	'Finishing IC2 Test',
	4,
	1);
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
INSERT INTO SM_CH
	VALUES (3145731,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145732,
	3145734,
	3145729,
	'Initial State',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (3145732,
	3145729,
	3145734,
	3145729);
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
INSERT INTO SM_NSTXN
	VALUES (3145729,
	3145734,
	3145729,
	3145731,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145729,
	3145734,
	3145731,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145730,
	3145734,
	3145732,
	3145729,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145730,
	3145734,
	3145732,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145731,
	3145734,
	3145732,
	3145730,
	3145730);
INSERT INTO SM_TXN
	VALUES (3145731,
	3145734,
	3145730,
	3145730);
INSERT INTO SM_NSTXN
	VALUES (3145732,
	3145734,
	3145732,
	3145732,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145732,
	3145734,
	3145729,
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
	'// select one one_inst related by self-><rel_obj>[REL]
select one ob related by self->OB[R1];
if (ob.i == 32)
  LOG::LogSuccess(message:"IC2: OC - select one inst related by self-><rel_obj>[REL]");
else 
  LOG::LogFailure(message:"IC2: OC - select one inst related by   self-><rel_obj>[REL]");
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select one oc related by self->OB[R1]->OC[R1];
if (oc.i == self.i)
  LOG::LogSuccess(message:"IC2: OC - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]");
else 
  LOG::LogFailure(message:"IC2: OC - select one inst related by   self-><rel_obj>[REL]-><self_obj>[REL]");
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OB2:''Verify Rel with Instance''(id:self.oc_id) to ob;',
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
	'select one ob related by self->OB[R1];
if (ob.ob_id == rcvd_evt.id)
  LOG::LogSuccess(message:"IC2: OC - Verifying Rel with Instance") ;
  generate OB3:''Finish IC2 Test''() to ob;
else 
  LOG::LogFailure(message:"IC2: OC - Verifying Rel with Instance");
end if;',
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
	'// check unrelate and delete
select one ob related by self->OB[R1];
unrelate ob from self across R1;
delete object instance ob;
select one ob related by self->OB[R1];
if (empty ob)
  LOG::LogSuccess(message:"IC2: OC - unrelate and delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IC2: OC - unrelate and delete object instance <rel_inst>") ;
end if;

//check unrelate
if ( true )
  create object instance ob1 of OB;
  assign ob = ob1;
end if;
relate self to ob across R1;
unrelate self from ob across R1;
select one ob2 related by self->OB[R1];
if (empty ob2)
  LOG::LogSuccess(message:"IC2: OC - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IC2: OC - unrelate object instance <rel_inst>") ;
end if;
delete object instance ob;

// send complete to driver 
select any driver from instances of ICD;
generate ICD4:''IC2 Test C Complete''() to driver;',
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
	'create object instance inst1 of OB;
relate self to inst1 across R1;
assign inst1.i = 32;
assign self.i = 32;

generate OC4:''Continue IC2 Test''() to self;
',
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
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (3145731,
	3145729,
	3145730,
	41);
INSERT INTO GD_SHP
	VALUES (3145731,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (3145732,
	3145729,
	3145731,
	41);
INSERT INTO GD_SHP
	VALUES (3145732,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (3145733,
	3145729,
	3145732,
	41);
INSERT INTO GD_SHP
	VALUES (3145733,
	1680,
	1040,
	2032,
	1168);
INSERT INTO GD_GE
	VALUES (3145734,
	3145729,
	3145729,
	42);
INSERT INTO GD_CON
	VALUES (3145734,
	3145730,
	3145732,
	0);
INSERT INTO GD_CTXT
	VALUES (3145734,
	0,
	0,
	0,
	0,
	0,
	0,
	1824,
	1632,
	2048,
	1663,
	0,
	7,
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
	1600,
	1840,
	1680,
	0);
INSERT INTO GD_GE
	VALUES (3145736,
	3145729,
	3145731,
	42);
INSERT INTO GD_CON
	VALUES (3145736,
	3145733,
	3145731,
	0);
INSERT INTO GD_CTXT
	VALUES (3145736,
	0,
	0,
	0,
	0,
	0,
	0,
	2165,
	1111,
	2368,
	1157,
	21,
	6,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145737,
	3145736,
	2032,
	1088,
	2160,
	1088,
	0);
INSERT INTO GD_LS
	VALUES (3145738,
	3145736,
	2160,
	1088,
	2160,
	1280,
	3145737);
INSERT INTO GD_GE
	VALUES (3145739,
	3145729,
	3145732,
	42);
INSERT INTO GD_CON
	VALUES (3145739,
	3145733,
	3145730,
	0);
INSERT INTO GD_CTXT
	VALUES (3145739,
	0,
	0,
	0,
	0,
	0,
	0,
	1795,
	1215,
	2003,
	1257,
	-13,
	6,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145740,
	3145739,
	1824,
	1168,
	1824,
	1280,
	0);
INSERT INTO GD_GE
	VALUES (3145741,
	3145729,
	3145730,
	42);
INSERT INTO GD_CON
	VALUES (3145741,
	3145733,
	3145733,
	0);
INSERT INTO GD_CTXT
	VALUES (3145741,
	0,
	0,
	0,
	0,
	0,
	0,
	1600,
	944,
	1780,
	977,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145742,
	3145741,
	1680,
	1088,
	1616,
	1088,
	0);
INSERT INTO GD_LS
	VALUES (3145743,
	3145741,
	1616,
	1088,
	1616,
	976,
	3145742);
INSERT INTO GD_LS
	VALUES (3145744,
	3145741,
	1616,
	976,
	1744,
	976,
	3145743);
INSERT INTO GD_LS
	VALUES (3145745,
	3145741,
	1744,
	976,
	1744,
	1040,
	3145744);
INSERT INTO O_OBJ
	VALUES (8388615,
	'Object D 1L Side',
	8,
	'OD',
	'',
	8388624);
INSERT INTO O_NBATTR
	VALUES (8388626,
	8388615);
INSERT INTO O_BATTR
	VALUES (8388626,
	8388615);
INSERT INTO O_ATTR
	VALUES (8388626,
	8388615,
	0,
	'od_id',
	'',
	'',
	'od_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (8388627,
	8388615);
INSERT INTO O_BATTR
	VALUES (8388627,
	8388615);
INSERT INTO O_ATTR
	VALUES (8388627,
	8388615,
	8388626,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (8388628,
	8388615);
INSERT INTO O_BATTR
	VALUES (8388628,
	8388615);
INSERT INTO O_ATTR
	VALUES (8388628,
	8388615,
	8388627,
	'ack_count',
	'',
	'',
	'ack_count',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (8388629,
	8388615);
INSERT INTO O_BATTR
	VALUES (8388629,
	8388615);
INSERT INTO O_ATTR
	VALUES (8388629,
	8388615,
	8388628,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	8388615);
INSERT INTO O_OIDA
	VALUES (8388626,
	8388615,
	0);
INSERT INTO O_RTIDA
	VALUES (8388626,
	8388615,
	0,
	8388610,
	8388611);
INSERT INTO SM_ISM
	VALUES (3670023,
	8388615);
INSERT INTO SM_SM
	VALUES (3670023,
	'',
	7);
INSERT INTO SM_MOORE
	VALUES (3670023);
INSERT INTO SM_EVTDI
	VALUES (3670017,
	3670023,
	'id',
	'',
	524294);
INSERT INTO SM_SUPDT
	VALUES (3670017,
	3670023,
	0);
INSERT INTO SM_SUPDT
	VALUES (3670018,
	3670023,
	0);
INSERT INTO SM_SDI
	VALUES (3670017,
	3670018,
	3670023);
INSERT INTO SM_STATE
	VALUES (3670017,
	3670023,
	3670017,
	'Starting IC3 Test',
	2,
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
	'Start IC3 Test',
	0,
	'',
	'OD1',
	'');
INSERT INTO SM_EIGN
	VALUES (3670017,
	3670017,
	3670023,
	3670017,
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
	'Verify Rel with Instance',
	0,
	'',
	'OD2',
	'');
INSERT INTO SM_EIGN
	VALUES (3670017,
	3670018,
	3670023,
	3670018,
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670018,
	3670023,
	3670018);
INSERT INTO SM_LEVT
	VALUES (3670019,
	3670023,
	3670017);
INSERT INTO SM_SEVT
	VALUES (3670019,
	3670023,
	3670017);
INSERT INTO SM_EVT
	VALUES (3670019,
	3670023,
	3670017,
	3,
	'Ack From Verify Rel',
	0,
	'',
	'OD3',
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670019,
	3670023,
	3670017);
INSERT INTO SM_LEVT
	VALUES (3670020,
	3670023,
	3670017);
INSERT INTO SM_SEVT
	VALUES (3670020,
	3670023,
	3670017);
INSERT INTO SM_EVT
	VALUES (3670020,
	3670023,
	3670017,
	4,
	'Finish IC3 Test',
	0,
	'',
	'OD4',
	'');
INSERT INTO SM_CH
	VALUES (3670017,
	3670020,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670020,
	3670023,
	3670017);
INSERT INTO SM_LEVT
	VALUES (3670021,
	3670023,
	3670017);
INSERT INTO SM_SEVT
	VALUES (3670021,
	3670023,
	3670017);
INSERT INTO SM_EVT
	VALUES (3670021,
	3670023,
	3670017,
	5,
	'Continue IC3 Test',
	0,
	'',
	'OD5',
	'');
INSERT INTO SM_CH
	VALUES (3670017,
	3670021,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670021,
	3670023,
	3670017);
INSERT INTO SM_STATE
	VALUES (3670018,
	3670023,
	3670018,
	'Verifying Rel with Instance',
	3,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (3670018,
	3670019,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670019,
	3670023,
	3670017);
INSERT INTO SM_CH
	VALUES (3670018,
	3670020,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670020,
	3670023,
	3670017);
INSERT INTO SM_CH
	VALUES (3670018,
	3670021,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670021,
	3670023,
	3670017);
INSERT INTO SM_STATE
	VALUES (3670019,
	3670023,
	3670017,
	'Waiting for Acks from Related Instances',
	4,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_SEME
	VALUES (3670019,
	3670019,
	3670023,
	3670017);
INSERT INTO SM_SEME
	VALUES (3670019,
	3670020,
	3670023,
	3670017);
INSERT INTO SM_CH
	VALUES (3670019,
	3670021,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670021,
	3670023,
	3670017);
INSERT INTO SM_STATE
	VALUES (3670020,
	3670023,
	3670017,
	'Finishing IC3 Test',
	5,
	1);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (3670020,
	3670019,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670019,
	3670023,
	3670017);
INSERT INTO SM_CH
	VALUES (3670020,
	3670020,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670020,
	3670023,
	3670017);
INSERT INTO SM_CH
	VALUES (3670020,
	3670021,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670021,
	3670023,
	3670017);
INSERT INTO SM_STATE
	VALUES (3670021,
	3670023,
	3670017,
	'Initial State',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (3670021,
	3670017,
	3670023,
	3670017);
INSERT INTO SM_SEME
	VALUES (3670021,
	3670018,
	3670023,
	3670018);
INSERT INTO SM_CH
	VALUES (3670021,
	3670019,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670019,
	3670023,
	3670017);
INSERT INTO SM_CH
	VALUES (3670021,
	3670020,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670020,
	3670023,
	3670017);
INSERT INTO SM_SEME
	VALUES (3670021,
	3670021,
	3670023,
	3670017);
INSERT INTO SM_NSTXN
	VALUES (3670017,
	3670023,
	3670017,
	3670019,
	3670017);
INSERT INTO SM_TXN
	VALUES (3670017,
	3670023,
	3670019,
	3670017);
INSERT INTO SM_NSTXN
	VALUES (3670018,
	3670023,
	3670019,
	3670019,
	3670017);
INSERT INTO SM_TXN
	VALUES (3670018,
	3670023,
	3670019,
	3670017);
INSERT INTO SM_NSTXN
	VALUES (3670019,
	3670023,
	3670019,
	3670020,
	3670017);
INSERT INTO SM_TXN
	VALUES (3670019,
	3670023,
	3670020,
	3670017);
INSERT INTO SM_NSTXN
	VALUES (3670020,
	3670023,
	3670021,
	3670017,
	3670017);
INSERT INTO SM_TXN
	VALUES (3670020,
	3670023,
	3670021,
	3670017);
INSERT INTO SM_NSTXN
	VALUES (3670021,
	3670023,
	3670021,
	3670018,
	3670018);
INSERT INTO SM_TXN
	VALUES (3670021,
	3670023,
	3670018,
	3670018);
INSERT INTO SM_NSTXN
	VALUES (3670022,
	3670023,
	3670021,
	3670021,
	3670017);
INSERT INTO SM_TXN
	VALUES (3670022,
	3670023,
	3670017,
	3670017);
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
	'// select any rel_inst related by self-><rel_obj>[REL]
select any oe related by self->OE[R2];
if (oe.i == 42)
  LOG::LogSuccess(message:"IC3: OD - select any rel_inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IC3: OD - select any rel_inst related by   self-><rel_obj>[REL]") ;
end if;

// select many rel_insts related by self-><rel_obj>[REL]
select many oes related by self->OE[R2];
assign c = cardinality oes;
if (c == 2) 
  LOG::LogSuccess(message:"IC3: OD - select many rel_insts related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IC3: OD - select many rel_insts related by   self-><rel_obj>[REL]") ; 
end if;

// for each rel_inst 
for each oe in oes
  if (oe.i == 42)
    LOG::LogSuccess(message:"IC3: OD - for each rel_inst");

     // have related instance check if it can see the relationship that this 
     // instance created
     generate OE2:''Verify Rel with Instance''(id:self.od_id) to oe;
     assign self.ack_count = self.ack_count + 1;
  else 
    LOG::LogFailure(message:"IC3: OD - for each rel_inst");
  end if;
end for;

// select any any_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select any od related by self->OE[R2]->OD[R2];
if (od.i == self.i)
  LOG::LogSuccess(message:"IC3: OD - select any any_inst related by self-><rel_obj>[REL]-><self_obj>[REL]");
else 
  LOG::LogFailure(message:"IC3: OD - select any any_inst related by   self-><rel_obj>[REL]-><self_obj>[REL]");
end if;
',
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
	'select many oes related by self->OE[R2];
assign found = FALSE;
for each oe in oes
  if (found == FALSE)
    if (oe.oe_id == rcvd_evt.id) 
      assign found = TRUE;
      generate OE3:''Finish IC3 Test''() to oe;
    end if;
  end if;
end for;
if (found == FALSE)
  LOG::LogFailure(message:"IC3: OD - Verifying Rel with Instance");
end if;',
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
	'assign self.ack_count = self.ack_count - 1;
if (self.ack_count == 0)
  generate OD4:''Finish IC3 Test''() to self;
end if;

',
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
	'//check unrelate and delete
select many oes related by self->OE[R2];
for each oe in oes
  unrelate oe from self across R2;
  delete object instance oe;
end for;
select many oes related by self->OE[R2];
if (empty oes)
  LOG::LogSuccess(message:"IC3: OD - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IC3: OD - delete object instance <rel_inst>") ;
end if;

//check unrelate
if (true)
  create object instance oe1 of OE;
  assign oe = oe1;
end if;
relate self to oe across R2;
unrelate self from oe across R2;
select any oe2 related by self->OE[R2];
if (empty oe2)
  LOG::LogSuccess(message:"IC3: OD - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IC3: OD - unrelate object instance <rel_inst>") ;
end if;
delete object instance oe;

select any driver from instances of ICD;
generate ICD6:''IC3 Test D Complete''() to driver;',
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
	'if (true)
  create object instance rel_inst1 of OE;
  relate self to rel_inst1 across R2;
  assign rel_inst1.i = 42;
end if;

if(true)
  create object instance rel_inst2 of OE;
  relate self to rel_inst2 across R2;
  assign rel_inst2.i = 42;
end if;

assign self.i = 42;
assign self.ack_count = 0;

generate OD5:''Continue IC3 Test''() to self;',
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
	1664,
	1248,
	2032,
	1552);
INSERT INTO GD_GE
	VALUES (3670019,
	3670017,
	3670018,
	41);
INSERT INTO GD_SHP
	VALUES (3670019,
	2064,
	1248,
	2384,
	1552);
INSERT INTO GD_GE
	VALUES (3670020,
	3670017,
	3670019,
	41);
INSERT INTO GD_SHP
	VALUES (3670020,
	1664,
	1632,
	2032,
	1728);
INSERT INTO GD_GE
	VALUES (3670021,
	3670017,
	3670020,
	41);
INSERT INTO GD_SHP
	VALUES (3670021,
	2064,
	1632,
	2368,
	1728);
INSERT INTO GD_GE
	VALUES (3670022,
	3670017,
	3670021,
	41);
INSERT INTO GD_SHP
	VALUES (3670022,
	1664,
	1024,
	2032,
	1136);
INSERT INTO GD_GE
	VALUES (3670023,
	3670017,
	3670017,
	42);
INSERT INTO GD_CON
	VALUES (3670023,
	3670018,
	3670020,
	0);
INSERT INTO GD_CTXT
	VALUES (3670023,
	0,
	0,
	0,
	0,
	0,
	0,
	1776,
	1581,
	1998,
	1627,
	0,
	4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670024,
	3670023,
	1792,
	1552,
	1792,
	1632,
	0);
INSERT INTO GD_GE
	VALUES (3670025,
	3670017,
	3670018,
	42);
INSERT INTO GD_CON
	VALUES (3670025,
	3670020,
	3670020,
	0);
INSERT INTO GD_CTXT
	VALUES (3670025,
	0,
	0,
	0,
	0,
	0,
	0,
	1833,
	1744,
	2021,
	1777,
	106,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670026,
	3670025,
	1680,
	1728,
	1680,
	1776,
	0);
INSERT INTO GD_LS
	VALUES (3670027,
	3670025,
	1680,
	1776,
	1824,
	1776,
	3670026);
INSERT INTO GD_LS
	VALUES (3670028,
	3670025,
	1824,
	1776,
	1824,
	1728,
	3670027);
INSERT INTO GD_GE
	VALUES (3670029,
	3670017,
	3670019,
	42);
INSERT INTO GD_CON
	VALUES (3670029,
	3670020,
	3670021,
	0);
INSERT INTO GD_CTXT
	VALUES (3670029,
	0,
	0,
	0,
	0,
	0,
	0,
	2064,
	1584,
	2221,
	1616,
	-7,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670030,
	3670029,
	2016,
	1632,
	2016,
	1616,
	0);
INSERT INTO GD_LS
	VALUES (3670031,
	3670029,
	2016,
	1616,
	2176,
	1616,
	3670030);
INSERT INTO GD_LS
	VALUES (3670032,
	3670029,
	2176,
	1616,
	2176,
	1632,
	3670031);
INSERT INTO GD_GE
	VALUES (3670033,
	3670017,
	3670020,
	42);
INSERT INTO GD_CON
	VALUES (3670033,
	3670022,
	3670022,
	0);
INSERT INTO GD_CTXT
	VALUES (3670033,
	0,
	0,
	0,
	0,
	0,
	0,
	1584,
	960,
	1779,
	1006,
	-7,
	-17,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670034,
	3670033,
	1664,
	1072,
	1616,
	1072,
	0);
INSERT INTO GD_LS
	VALUES (3670035,
	3670033,
	1616,
	1072,
	1616,
	992,
	3670034);
INSERT INTO GD_LS
	VALUES (3670036,
	3670033,
	1616,
	992,
	1712,
	992,
	3670035);
INSERT INTO GD_LS
	VALUES (3670037,
	3670033,
	1712,
	992,
	1712,
	1024,
	3670036);
INSERT INTO GD_GE
	VALUES (3670038,
	3670017,
	3670022,
	42);
INSERT INTO GD_CON
	VALUES (3670038,
	3670022,
	3670018,
	0);
INSERT INTO GD_CTXT
	VALUES (3670038,
	0,
	0,
	0,
	0,
	0,
	0,
	1784,
	1180,
	1992,
	1219,
	-8,
	3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670039,
	3670038,
	1808,
	1136,
	1808,
	1248,
	0);
INSERT INTO GD_GE
	VALUES (3670040,
	3670017,
	3670021,
	42);
INSERT INTO GD_CON
	VALUES (3670040,
	3670022,
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
	2040,
	1056,
	2270,
	1096,
	-88,
	-41,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670041,
	3670040,
	2032,
	1088,
	2144,
	1088,
	0);
INSERT INTO GD_LS
	VALUES (3670042,
	3670040,
	2144,
	1088,
	2144,
	1248,
	3670041);
INSERT INTO O_OBJ
	VALUES (8388616,
	'Object E MR Side',
	9,
	'OE',
	'',
	8388624);
INSERT INTO O_NBATTR
	VALUES (8388630,
	8388616);
INSERT INTO O_BATTR
	VALUES (8388630,
	8388616);
INSERT INTO O_ATTR
	VALUES (8388630,
	8388616,
	0,
	'oe_id',
	'',
	'',
	'oe_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (8388631,
	8388616);
INSERT INTO O_BATTR
	VALUES (8388631,
	8388616);
INSERT INTO O_ATTR
	VALUES (8388631,
	8388616,
	8388630,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_REF
	VALUES (8388616,
	8388615,
	0,
	8388626,
	8388610,
	8388612,
	8388611,
	8388632,
	8388611,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (8388632,
	8388616,
	8388626,
	8388615,
	1);
INSERT INTO O_ATTR
	VALUES (8388632,
	8388616,
	8388631,
	'od_id',
	'',
	'',
	'od_id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (8388633,
	8388616);
INSERT INTO O_BATTR
	VALUES (8388633,
	8388616);
INSERT INTO O_ATTR
	VALUES (8388633,
	8388616,
	8388632,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	8388616);
INSERT INTO O_OIDA
	VALUES (8388630,
	8388616,
	0);
INSERT INTO SM_ISM
	VALUES (4194312,
	8388616);
INSERT INTO SM_SM
	VALUES (4194312,
	'',
	8);
INSERT INTO SM_MOORE
	VALUES (4194312);
INSERT INTO SM_EVTDI
	VALUES (4194305,
	4194312,
	'id',
	'',
	524294);
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
INSERT INTO SM_STATE
	VALUES (4194305,
	4194312,
	4194305,
	'Starting IC3 Test',
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
	'Start IC3 Test',
	0,
	'',
	'OE1',
	'');
INSERT INTO SM_EIGN
	VALUES (4194305,
	4194305,
	4194312,
	4194305,
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
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OE2',
	'');
INSERT INTO SM_EIGN
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
	4194305);
INSERT INTO SM_SEVT
	VALUES (4194307,
	4194312,
	4194305);
INSERT INTO SM_EVT
	VALUES (4194307,
	4194312,
	4194305,
	3,
	'Finish IC3 Test',
	0,
	'',
	'OE3',
	'');
INSERT INTO SM_SEME
	VALUES (4194305,
	4194307,
	4194312,
	4194305);
INSERT INTO SM_LEVT
	VALUES (4194308,
	4194312,
	4194305);
INSERT INTO SM_SEVT
	VALUES (4194308,
	4194312,
	4194305);
INSERT INTO SM_EVT
	VALUES (4194308,
	4194312,
	4194305,
	4,
	'Continue IC3 Test',
	0,
	'',
	'OE4',
	'');
INSERT INTO SM_CH
	VALUES (4194305,
	4194308,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194305,
	4194308,
	4194312,
	4194305);
INSERT INTO SM_STATE
	VALUES (4194306,
	4194312,
	4194306,
	'Verifying Rel with Instance',
	3,
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
INSERT INTO SM_CH
	VALUES (4194306,
	4194306,
	4194312,
	4194306,
	'');
INSERT INTO SM_SEME
	VALUES (4194306,
	4194306,
	4194312,
	4194306);
INSERT INTO SM_CH
	VALUES (4194306,
	4194307,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194306,
	4194307,
	4194312,
	4194305);
INSERT INTO SM_CH
	VALUES (4194306,
	4194308,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194306,
	4194308,
	4194312,
	4194305);
INSERT INTO SM_STATE
	VALUES (4194307,
	4194312,
	4194305,
	'Finishing IC3 Test',
	4,
	1);
INSERT INTO SM_CH
	VALUES (4194307,
	4194305,
	4194312,
	4194305,
	'');
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
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194307,
	4194307,
	4194312,
	4194305);
INSERT INTO SM_CH
	VALUES (4194307,
	4194308,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194307,
	4194308,
	4194312,
	4194305);
INSERT INTO SM_STATE
	VALUES (4194308,
	4194312,
	4194305,
	'Initial State',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (4194308,
	4194305,
	4194312,
	4194305);
INSERT INTO SM_SEME
	VALUES (4194308,
	4194306,
	4194312,
	4194306);
INSERT INTO SM_CH
	VALUES (4194308,
	4194307,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194308,
	4194307,
	4194312,
	4194305);
INSERT INTO SM_SEME
	VALUES (4194308,
	4194308,
	4194312,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194305,
	4194312,
	4194305,
	4194307,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194305,
	4194312,
	4194307,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194306,
	4194312,
	4194308,
	4194305,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194306,
	4194312,
	4194308,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194307,
	4194312,
	4194308,
	4194306,
	4194306);
INSERT INTO SM_TXN
	VALUES (4194307,
	4194312,
	4194306,
	4194306);
INSERT INTO SM_NSTXN
	VALUES (4194308,
	4194312,
	4194308,
	4194308,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194308,
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
	'// select one one_inst related by self-><rel_obj>[REL]
select one od related by self->OD[R2];
if (od.i == 32)
  LOG::LogSuccess(message:"IC3: OE - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IC3: OE - select one inst related by   self-><rel_obj>[REL]");
end if;

// select any any_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select any oe related by self->OD[R2]->OE[R2];
if (oe.i == self.i)
  LOG::LogSuccess(message:"IC3: OE - select any inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IC3: OE - select any inst related by   self-><rel_obj>[REL]-><self_obj>[REL]");
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OD2:''Verify Rel with Instance''(id:self.oe_id) to od;',
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
	'select one od related by self->OD[R2];
if (od.od_id == rcvd_evt.id)
 LOG::LogSuccess(message:"IC3: OE - Verifying Rel with Instance") ;
  generate OD3:''Ack From Verify Rel''() to od;
else 
  LOG::LogFailure(message:"IC3: OE - Verifying Rel with Instance") ;
end if;',
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
	'//check unrelate and delete
select one od related by self->OD[R2];
unrelate od from self across R2;
delete object instance od;
select one od related by self->OD[R2];
if (empty od)
  LOG::LogSuccess(message:"IC3: OE - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IC3: OE - delete object instance <rel_inst>") ;
end if;

//check unrelate
if (true)
  create object instance od1 of OD;
  assign od = od1;
end if;
relate self to od across R2;
unrelate self from od across R2;
select one od2 related by self->OD[R2];
if (empty od2)
  LOG::LogSuccess(message:"IC3: OE - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IC3: OE - unrelate object instance <rel_inst>") ;
end if;
delete object instance od;

select any driver from instances of ICD;
generate ICD7:''IC3 Test E Complete''() to driver;',
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
	'create object instance inst1 of OD;
relate self to inst1 across R2;
assign inst1.i = 32;
assign self.i = 32;

generate OE4:''Continue IC3 Test''() to self;
',
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
	1600,
	4200,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (4194306,
	4194305,
	4194305,
	41);
INSERT INTO GD_SHP
	VALUES (4194306,
	1696,
	1264,
	2016,
	1552);
INSERT INTO GD_GE
	VALUES (4194307,
	4194305,
	4194306,
	41);
INSERT INTO GD_SHP
	VALUES (4194307,
	2064,
	1264,
	2368,
	1552);
INSERT INTO GD_GE
	VALUES (4194308,
	4194305,
	4194307,
	41);
INSERT INTO GD_SHP
	VALUES (4194308,
	1696,
	1648,
	2016,
	1728);
INSERT INTO GD_GE
	VALUES (4194309,
	4194305,
	4194308,
	41);
INSERT INTO GD_SHP
	VALUES (4194309,
	1696,
	1056,
	2016,
	1152);
INSERT INTO GD_GE
	VALUES (4194310,
	4194305,
	4194305,
	42);
INSERT INTO GD_CON
	VALUES (4194310,
	4194306,
	4194308,
	0);
INSERT INTO GD_CTXT
	VALUES (4194310,
	0,
	0,
	0,
	0,
	0,
	0,
	1712,
	1584,
	1894,
	1621,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194311,
	4194310,
	1728,
	1552,
	1728,
	1648,
	0);
INSERT INTO GD_GE
	VALUES (4194312,
	4194305,
	4194308,
	42);
INSERT INTO GD_CON
	VALUES (4194312,
	4194309,
	4194306,
	0);
INSERT INTO GD_CTXT
	VALUES (4194312,
	0,
	0,
	0,
	0,
	0,
	0,
	1824,
	1200,
	2018,
	1235,
	0,
	7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194313,
	4194312,
	1840,
	1152,
	1840,
	1264,
	0);
INSERT INTO GD_GE
	VALUES (4194314,
	4194305,
	4194307,
	42);
INSERT INTO GD_CON
	VALUES (4194314,
	4194309,
	4194307,
	0);
INSERT INTO GD_CTXT
	VALUES (4194314,
	0,
	0,
	0,
	0,
	0,
	0,
	2128,
	1104,
	2323,
	1137,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194315,
	4194314,
	2016,
	1104,
	2144,
	1104,
	0);
INSERT INTO GD_LS
	VALUES (4194316,
	4194314,
	2144,
	1104,
	2144,
	1264,
	4194315);
INSERT INTO GD_GE
	VALUES (4194317,
	4194305,
	4194306,
	42);
INSERT INTO GD_CON
	VALUES (4194317,
	4194309,
	4194309,
	0);
INSERT INTO GD_CTXT
	VALUES (4194317,
	0,
	0,
	0,
	0,
	0,
	0,
	1617,
	987,
	1790,
	1022,
	-6,
	-22,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194318,
	4194317,
	1696,
	1088,
	1648,
	1088,
	0);
INSERT INTO GD_LS
	VALUES (4194319,
	4194317,
	1648,
	1088,
	1648,
	1024,
	4194318);
INSERT INTO GD_LS
	VALUES (4194320,
	4194317,
	1648,
	1024,
	1728,
	1024,
	4194319);
INSERT INTO GD_LS
	VALUES (4194321,
	4194317,
	1728,
	1024,
	1728,
	1056,
	4194320);
INSERT INTO O_OBJ
	VALUES (8388617,
	'IC Driver',
	10,
	'ICD',
	'',
	8388624);
INSERT INTO O_NBATTR
	VALUES (8388634,
	8388617);
INSERT INTO O_BATTR
	VALUES (8388634,
	8388617);
INSERT INTO O_ATTR
	VALUES (8388634,
	8388617,
	0,
	'icd_id',
	'',
	'',
	'icd_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (8388635,
	8388617);
INSERT INTO O_BATTR
	VALUES (8388635,
	8388617);
INSERT INTO O_ATTR
	VALUES (8388635,
	8388617,
	8388634,
	'ack_count',
	'',
	'',
	'ack_count',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (8388636,
	8388617);
INSERT INTO O_BATTR
	VALUES (8388636,
	8388617);
INSERT INTO O_ATTR
	VALUES (8388636,
	8388617,
	8388635,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	8388617);
INSERT INTO O_OIDA
	VALUES (8388634,
	8388617,
	0);
INSERT INTO SM_ISM
	VALUES (4718601,
	8388617);
INSERT INTO SM_SM
	VALUES (4718601,
	'',
	9);
INSERT INTO SM_MOORE
	VALUES (4718601);
INSERT INTO SM_SUPDT
	VALUES (4718593,
	4718601,
	0);
INSERT INTO SM_STATE
	VALUES (4718593,
	4718601,
	4718593,
	'Running IC1 Test Suite',
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
	'Start IC Test Suite',
	0,
	'',
	'ICD1',
	'');
INSERT INTO SM_SEME
	VALUES (4718593,
	4718593,
	4718601,
	4718593);
INSERT INTO SM_LEVT
	VALUES (4718594,
	4718601,
	4718593);
INSERT INTO SM_SEVT
	VALUES (4718594,
	4718601,
	4718593);
INSERT INTO SM_EVT
	VALUES (4718594,
	4718601,
	4718593,
	2,
	'IC1 Test Suite Complete',
	0,
	'',
	'ICD2',
	'');
INSERT INTO SM_SEME
	VALUES (4718593,
	4718594,
	4718601,
	4718593);
INSERT INTO SM_LEVT
	VALUES (4718595,
	4718601,
	4718593);
INSERT INTO SM_SEVT
	VALUES (4718595,
	4718601,
	4718593);
INSERT INTO SM_EVT
	VALUES (4718595,
	4718601,
	4718593,
	3,
	'IC2 Test B Complete',
	0,
	'',
	'ICD3',
	'');
INSERT INTO SM_CH
	VALUES (4718593,
	4718595,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718593,
	4718595,
	4718601,
	4718593);
INSERT INTO SM_LEVT
	VALUES (4718596,
	4718601,
	4718593);
INSERT INTO SM_SEVT
	VALUES (4718596,
	4718601,
	4718593);
INSERT INTO SM_EVT
	VALUES (4718596,
	4718601,
	4718593,
	4,
	'IC2 Test C Complete',
	0,
	'',
	'ICD4',
	'');
INSERT INTO SM_CH
	VALUES (4718593,
	4718596,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718593,
	4718596,
	4718601,
	4718593);
INSERT INTO SM_LEVT
	VALUES (4718597,
	4718601,
	4718593);
INSERT INTO SM_SEVT
	VALUES (4718597,
	4718601,
	4718593);
INSERT INTO SM_EVT
	VALUES (4718597,
	4718601,
	4718593,
	5,
	'Start IC3 Test Suite',
	0,
	'',
	'ICD5',
	'');
INSERT INTO SM_CH
	VALUES (4718593,
	4718597,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718593,
	4718597,
	4718601,
	4718593);
INSERT INTO SM_LEVT
	VALUES (4718598,
	4718601,
	4718593);
INSERT INTO SM_SEVT
	VALUES (4718598,
	4718601,
	4718593);
INSERT INTO SM_EVT
	VALUES (4718598,
	4718601,
	4718593,
	6,
	'IC3 Test D Complete',
	0,
	'',
	'ICD6',
	'');
INSERT INTO SM_CH
	VALUES (4718593,
	4718598,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718593,
	4718598,
	4718601,
	4718593);
INSERT INTO SM_LEVT
	VALUES (4718599,
	4718601,
	4718593);
INSERT INTO SM_SEVT
	VALUES (4718599,
	4718601,
	4718593);
INSERT INTO SM_EVT
	VALUES (4718599,
	4718601,
	4718593,
	7,
	'IC3 Test E Complete',
	0,
	'',
	'ICD7',
	'');
INSERT INTO SM_CH
	VALUES (4718593,
	4718599,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718593,
	4718599,
	4718601,
	4718593);
INSERT INTO SM_LEVT
	VALUES (4718600,
	4718601,
	4718593);
INSERT INTO SM_SEVT
	VALUES (4718600,
	4718601,
	4718593);
INSERT INTO SM_EVT
	VALUES (4718600,
	4718601,
	4718593,
	8,
	'Start IC4 Test Suite',
	0,
	'',
	'ICD8',
	'');
INSERT INTO SM_CH
	VALUES (4718593,
	4718600,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718593,
	4718600,
	4718601,
	4718593);
INSERT INTO SM_LEVT
	VALUES (4718601,
	4718601,
	4718593);
INSERT INTO SM_SEVT
	VALUES (4718601,
	4718601,
	4718593);
INSERT INTO SM_EVT
	VALUES (4718601,
	4718601,
	4718593,
	9,
	'IC4 Test F Complete',
	0,
	'',
	'ICD9',
	'');
INSERT INTO SM_CH
	VALUES (4718593,
	4718601,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718593,
	4718601,
	4718601,
	4718593);
INSERT INTO SM_LEVT
	VALUES (4718602,
	4718601,
	4718593);
INSERT INTO SM_SEVT
	VALUES (4718602,
	4718601,
	4718593);
INSERT INTO SM_EVT
	VALUES (4718602,
	4718601,
	4718593,
	10,
	'IC4 Test G Complete',
	0,
	'',
	'ICD10',
	'');
INSERT INTO SM_CH
	VALUES (4718593,
	4718602,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718593,
	4718602,
	4718601,
	4718593);
INSERT INTO SM_LEVT
	VALUES (4718603,
	4718601,
	4718593);
INSERT INTO SM_SEVT
	VALUES (4718603,
	4718601,
	4718593);
INSERT INTO SM_EVT
	VALUES (4718603,
	4718601,
	4718593,
	11,
	'Start IC7 Test Suite',
	0,
	'',
	'ICD11',
	'');
INSERT INTO SM_CH
	VALUES (4718593,
	4718603,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718593,
	4718603,
	4718601,
	4718593);
INSERT INTO SM_LEVT
	VALUES (4718604,
	4718601,
	4718593);
INSERT INTO SM_SEVT
	VALUES (4718604,
	4718601,
	4718593);
INSERT INTO SM_EVT
	VALUES (4718604,
	4718601,
	4718593,
	14,
	'IC7 Test L Complete',
	0,
	'',
	'ICD14',
	'');
INSERT INTO SM_CH
	VALUES (4718593,
	4718604,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718593,
	4718604,
	4718601,
	4718593);
INSERT INTO SM_LEVT
	VALUES (4718605,
	4718601,
	4718593);
INSERT INTO SM_SEVT
	VALUES (4718605,
	4718601,
	4718593);
INSERT INTO SM_EVT
	VALUES (4718605,
	4718601,
	4718593,
	15,
	'IC7 Test M Complete',
	0,
	'',
	'ICD15',
	'');
INSERT INTO SM_CH
	VALUES (4718593,
	4718605,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718593,
	4718605,
	4718601,
	4718593);
INSERT INTO SM_LEVT
	VALUES (4718606,
	4718601,
	4718593);
INSERT INTO SM_SEVT
	VALUES (4718606,
	4718601,
	4718593);
INSERT INTO SM_EVT
	VALUES (4718606,
	4718601,
	4718593,
	16,
	'IC7 Test Complete',
	0,
	'',
	'ICD16',
	'');
INSERT INTO SM_CH
	VALUES (4718593,
	4718606,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718593,
	4718606,
	4718601,
	4718593);
INSERT INTO SM_STATE
	VALUES (4718594,
	4718601,
	4718593,
	'Running IC2 Test Suite',
	2,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (4718594,
	4718594,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718594,
	4718594,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718594,
	4718595,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718594,
	4718596,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718594,
	4718597,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718594,
	4718597,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718594,
	4718598,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718594,
	4718598,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718594,
	4718599,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718594,
	4718599,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718594,
	4718600,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718594,
	4718600,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718594,
	4718601,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718594,
	4718601,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718594,
	4718602,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718594,
	4718602,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718594,
	4718603,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718594,
	4718603,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718594,
	4718604,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718594,
	4718604,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718594,
	4718605,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718594,
	4718605,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718594,
	4718606,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718594,
	4718606,
	4718601,
	4718593);
INSERT INTO SM_STATE
	VALUES (4718595,
	4718601,
	4718593,
	'Waiting for IC2 Completions',
	3,
	0);
INSERT INTO SM_CH
	VALUES (4718595,
	4718593,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718595,
	4718593,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718595,
	4718594,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718595,
	4718594,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718595,
	4718595,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718595,
	4718596,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718595,
	4718597,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718595,
	4718598,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718595,
	4718598,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718595,
	4718599,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718595,
	4718599,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718595,
	4718600,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718595,
	4718600,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718595,
	4718601,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718595,
	4718601,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718595,
	4718602,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718595,
	4718602,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718595,
	4718603,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718595,
	4718603,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718595,
	4718604,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718595,
	4718604,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718595,
	4718605,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718595,
	4718605,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718595,
	4718606,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718595,
	4718606,
	4718601,
	4718593);
INSERT INTO SM_STATE
	VALUES (4718596,
	4718601,
	4718593,
	'Running IC3 Test Suite',
	4,
	0);
INSERT INTO SM_CH
	VALUES (4718596,
	4718593,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718596,
	4718593,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718596,
	4718594,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718596,
	4718594,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718596,
	4718595,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718596,
	4718595,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718596,
	4718596,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718596,
	4718596,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718596,
	4718597,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718596,
	4718597,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718596,
	4718598,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718596,
	4718599,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718596,
	4718600,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718596,
	4718600,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718596,
	4718601,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718596,
	4718601,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718596,
	4718602,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718596,
	4718602,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718596,
	4718603,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718596,
	4718603,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718596,
	4718604,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718596,
	4718604,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718596,
	4718605,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718596,
	4718605,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718596,
	4718606,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718596,
	4718606,
	4718601,
	4718593);
INSERT INTO SM_STATE
	VALUES (4718597,
	4718601,
	4718593,
	'Waiting for IC3 Completions',
	5,
	0);
INSERT INTO SM_CH
	VALUES (4718597,
	4718593,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718597,
	4718593,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718597,
	4718594,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718597,
	4718594,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718597,
	4718595,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718597,
	4718595,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718597,
	4718596,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718597,
	4718596,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718597,
	4718597,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718597,
	4718597,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718597,
	4718598,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718597,
	4718599,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718597,
	4718600,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718597,
	4718601,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718597,
	4718601,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718597,
	4718602,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718597,
	4718602,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718597,
	4718603,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718597,
	4718603,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718597,
	4718604,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718597,
	4718604,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718597,
	4718605,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718597,
	4718605,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718597,
	4718606,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718597,
	4718606,
	4718601,
	4718593);
INSERT INTO SM_STATE
	VALUES (4718598,
	4718601,
	4718593,
	'Running IC4 Test Suite',
	6,
	0);
INSERT INTO SM_CH
	VALUES (4718598,
	4718593,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718598,
	4718593,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718598,
	4718594,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718598,
	4718594,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718598,
	4718595,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718598,
	4718595,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718598,
	4718596,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718598,
	4718596,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718598,
	4718597,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718598,
	4718597,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718598,
	4718598,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718598,
	4718598,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718598,
	4718599,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718598,
	4718599,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718598,
	4718600,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718598,
	4718600,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718598,
	4718601,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718598,
	4718602,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718598,
	4718603,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718598,
	4718603,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718598,
	4718604,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718598,
	4718604,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718598,
	4718605,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718598,
	4718605,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718598,
	4718606,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718598,
	4718606,
	4718601,
	4718593);
INSERT INTO SM_STATE
	VALUES (4718599,
	4718601,
	4718593,
	'Waiting for IC4 Completions',
	7,
	0);
INSERT INTO SM_CH
	VALUES (4718599,
	4718593,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718599,
	4718593,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718599,
	4718594,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718599,
	4718594,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718599,
	4718595,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718599,
	4718595,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718599,
	4718596,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718599,
	4718596,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718599,
	4718597,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718599,
	4718597,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718599,
	4718598,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718599,
	4718598,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718599,
	4718599,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718599,
	4718599,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718599,
	4718600,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718599,
	4718600,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718599,
	4718601,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718599,
	4718602,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718599,
	4718603,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718599,
	4718604,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718599,
	4718604,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718599,
	4718605,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718599,
	4718605,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718599,
	4718606,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718599,
	4718606,
	4718601,
	4718593);
INSERT INTO SM_STATE
	VALUES (4718600,
	4718601,
	4718593,
	'Shutting Down',
	20,
	0);
INSERT INTO SM_CH
	VALUES (4718600,
	4718593,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718600,
	4718593,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718600,
	4718594,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718600,
	4718594,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718600,
	4718595,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718600,
	4718595,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718600,
	4718596,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718600,
	4718596,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718600,
	4718597,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718600,
	4718597,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718600,
	4718598,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718600,
	4718598,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718600,
	4718599,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718600,
	4718599,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718600,
	4718600,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718600,
	4718600,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718600,
	4718601,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718600,
	4718601,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718600,
	4718602,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718600,
	4718602,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718600,
	4718603,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718600,
	4718603,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718600,
	4718604,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718600,
	4718604,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718600,
	4718605,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718600,
	4718605,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718600,
	4718606,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718600,
	4718606,
	4718601,
	4718593);
INSERT INTO SM_STATE
	VALUES (4718601,
	4718601,
	4718593,
	'Running IC7 Test Suite',
	8,
	0);
INSERT INTO SM_CH
	VALUES (4718601,
	4718593,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718601,
	4718593,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718601,
	4718594,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718601,
	4718594,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718601,
	4718595,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718601,
	4718595,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718601,
	4718596,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718601,
	4718596,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718601,
	4718597,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718601,
	4718597,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718601,
	4718598,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718601,
	4718598,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718601,
	4718599,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718601,
	4718599,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718601,
	4718600,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718601,
	4718600,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718601,
	4718601,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718601,
	4718601,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718601,
	4718602,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718601,
	4718602,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718601,
	4718603,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718601,
	4718603,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718601,
	4718604,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718601,
	4718605,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718601,
	4718606,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718601,
	4718606,
	4718601,
	4718593);
INSERT INTO SM_STATE
	VALUES (4718602,
	4718601,
	4718593,
	'Waiting for IC7 Completions',
	9,
	0);
INSERT INTO SM_CH
	VALUES (4718602,
	4718593,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718602,
	4718593,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718602,
	4718594,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718602,
	4718594,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718602,
	4718595,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718602,
	4718595,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718602,
	4718596,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718602,
	4718596,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718602,
	4718597,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718602,
	4718597,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718602,
	4718598,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718602,
	4718598,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718602,
	4718599,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718602,
	4718599,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718602,
	4718600,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718602,
	4718600,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718602,
	4718601,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718602,
	4718601,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718602,
	4718602,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718602,
	4718602,
	4718601,
	4718593);
INSERT INTO SM_CH
	VALUES (4718602,
	4718603,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718602,
	4718603,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718602,
	4718604,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718602,
	4718605,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718602,
	4718606,
	4718601,
	4718593);
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
	4718593);
INSERT INTO SM_TXN
	VALUES (4718594,
	4718601,
	4718594,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718595,
	4718601,
	4718594,
	4718595,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718595,
	4718601,
	4718595,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718596,
	4718601,
	4718594,
	4718596,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718596,
	4718601,
	4718595,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718597,
	4718601,
	4718595,
	4718595,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718597,
	4718601,
	4718595,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718598,
	4718601,
	4718595,
	4718596,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718598,
	4718601,
	4718595,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718599,
	4718601,
	4718595,
	4718597,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718599,
	4718601,
	4718596,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718600,
	4718601,
	4718596,
	4718598,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718600,
	4718601,
	4718597,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718601,
	4718601,
	4718596,
	4718599,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718601,
	4718601,
	4718597,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718602,
	4718601,
	4718597,
	4718598,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718602,
	4718601,
	4718597,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718603,
	4718601,
	4718597,
	4718599,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718603,
	4718601,
	4718597,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718604,
	4718601,
	4718597,
	4718600,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718604,
	4718601,
	4718598,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718605,
	4718601,
	4718598,
	4718601,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718605,
	4718601,
	4718599,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718606,
	4718601,
	4718598,
	4718602,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718606,
	4718601,
	4718599,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718607,
	4718601,
	4718599,
	4718601,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718607,
	4718601,
	4718599,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718608,
	4718601,
	4718599,
	4718602,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718608,
	4718601,
	4718599,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718609,
	4718601,
	4718599,
	4718603,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718609,
	4718601,
	4718601,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718610,
	4718601,
	4718601,
	4718604,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718610,
	4718601,
	4718602,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718611,
	4718601,
	4718602,
	4718604,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718611,
	4718601,
	4718602,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718612,
	4718601,
	4718601,
	4718605,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718612,
	4718601,
	4718602,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718613,
	4718601,
	4718602,
	4718605,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718613,
	4718601,
	4718602,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718614,
	4718601,
	4718593,
	4718593,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718614,
	4718601,
	4718593,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718615,
	4718601,
	4718602,
	4718606,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718615,
	4718601,
	4718600,
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
	'LOG::LogInfo(message:"ICD: Running IC1 Test Suite");
generate OA1:''Start IC1 1 Object No Relationships''() to OA creator;',
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
	'LOG::LogInfo(message:"ICD: Running IC2 Test Suite");
create object instance oneleftside of OB;
create object instance onerightside of OC;
generate OB1:''Start IC2 Test''() to oneleftside;
generate OC1:''Start IC2 Test''() to onerightside;
assign self.ack_count = 2;',
	'');
INSERT INTO SM_MOAH
	VALUES (4718595,
	4718601,
	4718595);
INSERT INTO SM_AH
	VALUES (4718595,
	4718601);
INSERT INTO SM_ACT
	VALUES (4718595,
	4718601,
	1,
	'assign self.ack_count = self.ack_count -1;
if (self.ack_count == 0)
  LOG::LogInfo(message:"ICD: IC2 Test Suite Complete") ;
  generate ICD5:''Start IC3 Test Suite''() to self;
end if;
',
	'');
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
	'LOG::LogInfo(message:"ICD: Running IC3 Test Suite");
create object instance oneleftside of OD;
create object instance manyrightside of OE;
generate OD1:''Start IC3 Test''() to oneleftside;
generate OE1:''Start IC3 Test''() to manyrightside;
assign self.ack_count = 2;',
	'');
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
	'assign self.ack_count = self.ack_count -1;
if (self.ack_count == 0)
  LOG::LogInfo(message:"ICD: IC3 Test Suite Complete");
  generate ICD8:''Start IC4 Test Suite''() to self;
end if;
',
	'');
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
	'LOG::LogInfo(message:"ICD: Running IC4 Test Suite");
create object instance oneleftside of OF;
create object instance manyrightside of OG;
generate OF1:''Start IC4 Test''() to oneleftside;
generate OG1:''Start IC4 Test''() to manyrightside;
assign self.ack_count = 2;',
	'');
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
	'assign self.ack_count = self.ack_count -1;
if (self.ack_count == 0)
  LOG::LogInfo(message:"ICD: IC4 Test Suite Complete");
  generate ICD11:''Start IC7 Test Suite''() to self;
end if;
',
	'');
INSERT INTO SM_MOAH
	VALUES (4718600,
	4718601,
	4718600);
INSERT INTO SM_AH
	VALUES (4718600,
	4718601);
INSERT INTO SM_ACT
	VALUES (4718600,
	4718601,
	1,
	'LOG::LogInfo(message:"ICD: Shutting down") ;
bridge ARCH::shutdown();',
	'');
INSERT INTO SM_MOAH
	VALUES (4718601,
	4718601,
	4718601);
INSERT INTO SM_AH
	VALUES (4718601,
	4718601);
INSERT INTO SM_ACT
	VALUES (4718601,
	4718601,
	1,
	'LOG::LogInfo(message:"ICD: Running IC7 Test Suite");
generate OL1:''Start IC7 Test''() to OL creator;
generate OM1:''Start IC7 Test''() to OM creator;
assign self.ack_count = 2;
',
	'');
INSERT INTO SM_MOAH
	VALUES (4718602,
	4718601,
	4718602);
INSERT INTO SM_AH
	VALUES (4718602,
	4718601);
INSERT INTO SM_ACT
	VALUES (4718602,
	4718601,
	1,
	'assign self.ack_count = self.ack_count -1;
if (self.ack_count == 0)
  LOG::LogInfo(message:"ICD: IC7 Test Suite Complete") ;
  generate ICD16:''IC7 Test Complete''() to self;
end if;
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
	1600,
	4200,
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
	1296,
	2128,
	1408);
INSERT INTO GD_GE
	VALUES (4718595,
	4718593,
	4718594,
	41);
INSERT INTO GD_SHP
	VALUES (4718595,
	1776,
	1472,
	2128,
	1584);
INSERT INTO GD_GE
	VALUES (4718596,
	4718593,
	4718595,
	41);
INSERT INTO GD_SHP
	VALUES (4718596,
	1776,
	1664,
	2128,
	1760);
INSERT INTO GD_GE
	VALUES (4718597,
	4718593,
	4718596,
	41);
INSERT INTO GD_SHP
	VALUES (4718597,
	1776,
	1840,
	2128,
	1936);
INSERT INTO GD_GE
	VALUES (4718598,
	4718593,
	4718597,
	41);
INSERT INTO GD_SHP
	VALUES (4718598,
	1776,
	2016,
	2128,
	2112);
INSERT INTO GD_GE
	VALUES (4718599,
	4718593,
	4718598,
	41);
INSERT INTO GD_SHP
	VALUES (4718599,
	1776,
	2176,
	2128,
	2272);
INSERT INTO GD_GE
	VALUES (4718600,
	4718593,
	4718599,
	41);
INSERT INTO GD_SHP
	VALUES (4718600,
	1776,
	2336,
	2128,
	2432);
INSERT INTO GD_GE
	VALUES (4718601,
	4718593,
	4718600,
	41);
INSERT INTO GD_SHP
	VALUES (4718601,
	1792,
	2848,
	2080,
	2928);
INSERT INTO GD_GE
	VALUES (4718602,
	4718593,
	4718601,
	41);
INSERT INTO GD_SHP
	VALUES (4718602,
	1776,
	2512,
	2128,
	2608);
INSERT INTO GD_GE
	VALUES (4718603,
	4718593,
	4718602,
	41);
INSERT INTO GD_SHP
	VALUES (4718603,
	1776,
	2688,
	2144,
	2784);
INSERT INTO GD_GE
	VALUES (4718604,
	4718593,
	4718593,
	42);
INSERT INTO GD_CON
	VALUES (4718604,
	4718594,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (4718604,
	0,
	0,
	0,
	0,
	0,
	0,
	1920,
	1248,
	2134,
	1291,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718605,
	4718604,
	1936,
	1296,
	1936,
	1232,
	0);
INSERT INTO GD_GE
	VALUES (4718606,
	4718593,
	4718594,
	42);
INSERT INTO GD_CON
	VALUES (4718606,
	4718594,
	4718595,
	0);
INSERT INTO GD_CTXT
	VALUES (4718606,
	0,
	0,
	0,
	0,
	0,
	0,
	1943,
	1438,
	2115,
	1481,
	23,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718607,
	4718606,
	1936,
	1408,
	1936,
	1472,
	0);
INSERT INTO GD_GE
	VALUES (4718608,
	4718593,
	4718595,
	42);
INSERT INTO GD_CON
	VALUES (4718608,
	4718595,
	4718596,
	0);
INSERT INTO GD_CTXT
	VALUES (4718608,
	0,
	0,
	0,
	0,
	0,
	0,
	1806,
	1596,
	1952,
	1632,
	-114,
	-13,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718609,
	4718608,
	1936,
	1584,
	1936,
	1664,
	0);
INSERT INTO GD_GE
	VALUES (4718610,
	4718593,
	4718597,
	42);
INSERT INTO GD_CON
	VALUES (4718610,
	4718596,
	4718596,
	0);
INSERT INTO GD_CTXT
	VALUES (4718610,
	0,
	0,
	0,
	0,
	0,
	0,
	1657,
	1646,
	1773,
	1710,
	-55,
	-43,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718611,
	4718610,
	1776,
	1728,
	1728,
	1728,
	0);
INSERT INTO GD_LS
	VALUES (4718612,
	4718610,
	1728,
	1728,
	1728,
	1680,
	4718611);
INSERT INTO GD_LS
	VALUES (4718613,
	4718610,
	1728,
	1680,
	1776,
	1680,
	4718612);
INSERT INTO GD_GE
	VALUES (4718614,
	4718593,
	4718598,
	42);
INSERT INTO GD_CON
	VALUES (4718614,
	4718596,
	4718596,
	0);
INSERT INTO GD_CTXT
	VALUES (4718614,
	0,
	0,
	0,
	0,
	0,
	0,
	2169,
	1676,
	2298,
	1715,
	9,
	-5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718615,
	4718614,
	2128,
	1712,
	2176,
	1712,
	0);
INSERT INTO GD_LS
	VALUES (4718616,
	4718614,
	2176,
	1712,
	2176,
	1680,
	4718615);
INSERT INTO GD_LS
	VALUES (4718617,
	4718614,
	2176,
	1680,
	2144,
	1680,
	4718616);
INSERT INTO GD_LS
	VALUES (4718618,
	4718614,
	2144,
	1680,
	2128,
	1680,
	4718617);
INSERT INTO GD_GE
	VALUES (4718619,
	4718593,
	4718596,
	42);
INSERT INTO GD_CON
	VALUES (4718619,
	4718595,
	4718596,
	0);
INSERT INTO GD_CTXT
	VALUES (4718619,
	0,
	0,
	0,
	0,
	0,
	0,
	2037,
	1605,
	2202,
	1639,
	21,
	-4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718620,
	4718619,
	2032,
	1584,
	2032,
	1664,
	0);
INSERT INTO GD_GE
	VALUES (4718621,
	4718593,
	4718599,
	42);
INSERT INTO GD_CON
	VALUES (4718621,
	4718596,
	4718597,
	0);
INSERT INTO GD_CTXT
	VALUES (4718621,
	0,
	0,
	0,
	0,
	0,
	0,
	1792,
	1792,
	1990,
	1827,
	0,
	7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718622,
	4718621,
	1808,
	1760,
	1808,
	1840,
	0);
INSERT INTO GD_GE
	VALUES (4718623,
	4718593,
	4718600,
	42);
INSERT INTO GD_CON
	VALUES (4718623,
	4718597,
	4718598,
	0);
INSERT INTO GD_CTXT
	VALUES (4718623,
	0,
	0,
	0,
	0,
	0,
	0,
	1838,
	1960,
	1998,
	1996,
	30,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718624,
	4718623,
	1824,
	1936,
	1824,
	2016,
	0);
INSERT INTO GD_GE
	VALUES (4718625,
	4718593,
	4718601,
	42);
INSERT INTO GD_CON
	VALUES (4718625,
	4718597,
	4718598,
	0);
INSERT INTO GD_CTXT
	VALUES (4718625,
	0,
	0,
	0,
	0,
	0,
	0,
	2040,
	1964,
	2195,
	2011,
	24,
	3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718626,
	4718625,
	2032,
	1936,
	2032,
	2016,
	0);
INSERT INTO GD_GE
	VALUES (4718627,
	4718593,
	4718603,
	42);
INSERT INTO GD_CON
	VALUES (4718627,
	4718598,
	4718598,
	0);
INSERT INTO GD_CTXT
	VALUES (4718627,
	0,
	0,
	0,
	0,
	0,
	0,
	2178,
	2044,
	2334,
	2098,
	18,
	3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718628,
	4718627,
	2128,
	2080,
	2176,
	2080,
	0);
INSERT INTO GD_LS
	VALUES (4718629,
	4718627,
	2176,
	2080,
	2176,
	2032,
	4718628);
INSERT INTO GD_LS
	VALUES (4718630,
	4718627,
	2176,
	2032,
	2128,
	2032,
	4718629);
INSERT INTO GD_GE
	VALUES (4718631,
	4718593,
	4718602,
	42);
INSERT INTO GD_CON
	VALUES (4718631,
	4718598,
	4718598,
	0);
INSERT INTO GD_CTXT
	VALUES (4718631,
	0,
	0,
	0,
	0,
	0,
	0,
	1648,
	2086,
	1768,
	2126,
	-64,
	45,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718632,
	4718631,
	1776,
	2032,
	1728,
	2032,
	0);
INSERT INTO GD_LS
	VALUES (4718633,
	4718631,
	1728,
	2032,
	1728,
	2080,
	4718632);
INSERT INTO GD_LS
	VALUES (4718634,
	4718631,
	1728,
	2080,
	1776,
	2080,
	4718633);
INSERT INTO GD_GE
	VALUES (4718635,
	4718593,
	4718604,
	42);
INSERT INTO GD_CON
	VALUES (4718635,
	4718598,
	4718599,
	0);
INSERT INTO GD_CTXT
	VALUES (4718635,
	0,
	0,
	0,
	0,
	0,
	0,
	1824,
	2128,
	2056,
	2162,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718636,
	4718635,
	1840,
	2112,
	1840,
	2176,
	0);
INSERT INTO GD_GE
	VALUES (4718637,
	4718593,
	4718605,
	42);
INSERT INTO GD_CON
	VALUES (4718637,
	4718599,
	4718600,
	0);
INSERT INTO GD_CTXT
	VALUES (4718637,
	0,
	0,
	0,
	0,
	0,
	0,
	1824,
	2288,
	1974,
	2322,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718638,
	4718637,
	1840,
	2272,
	1840,
	2336,
	0);
INSERT INTO GD_GE
	VALUES (4718639,
	4718593,
	4718606,
	42);
INSERT INTO GD_CON
	VALUES (4718639,
	4718599,
	4718600,
	0);
INSERT INTO GD_CTXT
	VALUES (4718639,
	0,
	0,
	0,
	0,
	0,
	0,
	2043,
	2288,
	2222,
	2329,
	-5,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718640,
	4718639,
	2064,
	2272,
	2064,
	2336,
	0);
INSERT INTO GD_GE
	VALUES (4718641,
	4718593,
	4718608,
	42);
INSERT INTO GD_CON
	VALUES (4718641,
	4718600,
	4718600,
	0);
INSERT INTO GD_CTXT
	VALUES (4718641,
	0,
	0,
	0,
	0,
	0,
	0,
	2160,
	2384,
	2301,
	2431,
	0,
	7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718642,
	4718641,
	2128,
	2416,
	2176,
	2416,
	0);
INSERT INTO GD_LS
	VALUES (4718643,
	4718641,
	2176,
	2416,
	2176,
	2368,
	4718642);
INSERT INTO GD_LS
	VALUES (4718644,
	4718641,
	2176,
	2368,
	2128,
	2368,
	4718643);
INSERT INTO GD_GE
	VALUES (4718645,
	4718593,
	4718607,
	42);
INSERT INTO GD_CON
	VALUES (4718645,
	4718600,
	4718600,
	0);
INSERT INTO GD_CTXT
	VALUES (4718645,
	0,
	0,
	0,
	0,
	0,
	0,
	1654,
	2408,
	1776,
	2454,
	-74,
	39,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718646,
	4718645,
	1776,
	2368,
	1744,
	2368,
	0);
INSERT INTO GD_LS
	VALUES (4718647,
	4718645,
	1744,
	2368,
	1744,
	2400,
	4718646);
INSERT INTO GD_LS
	VALUES (4718648,
	4718645,
	1744,
	2400,
	1776,
	2400,
	4718647);
INSERT INTO GD_GE
	VALUES (4718649,
	4718593,
	4718609,
	42);
INSERT INTO GD_CON
	VALUES (4718649,
	4718600,
	4718602,
	0);
INSERT INTO GD_CTXT
	VALUES (4718649,
	0,
	0,
	0,
	0,
	0,
	0,
	1936,
	2464,
	2163,
	2494,
	0,
	7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718650,
	4718649,
	1952,
	2432,
	1952,
	2512,
	0);
INSERT INTO GD_GE
	VALUES (4718651,
	4718593,
	4718610,
	42);
INSERT INTO GD_CON
	VALUES (4718651,
	4718602,
	4718603,
	0);
INSERT INTO GD_CTXT
	VALUES (4718651,
	0,
	0,
	0,
	0,
	0,
	0,
	1861,
	2635,
	2027,
	2669,
	21,
	2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718652,
	4718651,
	1856,
	2608,
	1856,
	2688,
	0);
INSERT INTO GD_GE
	VALUES (4718653,
	4718593,
	4718612,
	42);
INSERT INTO GD_CON
	VALUES (4718653,
	4718602,
	4718603,
	0);
INSERT INTO GD_CTXT
	VALUES (4718653,
	0,
	0,
	0,
	0,
	0,
	0,
	2048,
	2640,
	2252,
	2673,
	0,
	7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718654,
	4718653,
	2064,
	2608,
	2064,
	2688,
	0);
INSERT INTO GD_GE
	VALUES (4718655,
	4718593,
	4718613,
	42);
INSERT INTO GD_CON
	VALUES (4718655,
	4718603,
	4718603,
	0);
INSERT INTO GD_CTXT
	VALUES (4718655,
	0,
	0,
	0,
	0,
	0,
	0,
	2176,
	2720,
	2382,
	2754,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718656,
	4718655,
	2144,
	2768,
	2192,
	2768,
	0);
INSERT INTO GD_LS
	VALUES (4718657,
	4718655,
	2192,
	2768,
	2192,
	2704,
	4718656);
INSERT INTO GD_LS
	VALUES (4718658,
	4718655,
	2192,
	2704,
	2144,
	2704,
	4718657);
INSERT INTO GD_GE
	VALUES (4718659,
	4718593,
	4718611,
	42);
INSERT INTO GD_CON
	VALUES (4718659,
	4718603,
	4718603,
	0);
INSERT INTO GD_CTXT
	VALUES (4718659,
	0,
	0,
	0,
	0,
	0,
	0,
	1620,
	2720,
	1731,
	2784,
	-92,
	-9,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718660,
	4718659,
	1776,
	2768,
	1728,
	2768,
	0);
INSERT INTO GD_LS
	VALUES (4718661,
	4718659,
	1728,
	2768,
	1728,
	2720,
	4718660);
INSERT INTO GD_LS
	VALUES (4718662,
	4718659,
	1728,
	2720,
	1776,
	2720,
	4718661);
INSERT INTO GD_GE
	VALUES (4718663,
	4718593,
	4718615,
	42);
INSERT INTO GD_CON
	VALUES (4718663,
	4718603,
	4718601,
	0);
INSERT INTO GD_CTXT
	VALUES (4718663,
	0,
	0,
	0,
	0,
	0,
	0,
	1865,
	2796,
	2135,
	2836,
	-39,
	-5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718664,
	4718663,
	1920,
	2784,
	1920,
	2848,
	0);
INSERT INTO GD_GE
	VALUES (4718665,
	4718593,
	4718614,
	42);
INSERT INTO GD_CON
	VALUES (4718665,
	4718594,
	4718594,
	0);
INSERT INTO GD_CTXT
	VALUES (4718665,
	0,
	0,
	0,
	0,
	0,
	0,
	2183,
	1338,
	2335,
	1376,
	23,
	1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718666,
	4718665,
	2128,
	1376,
	2176,
	1376,
	0);
INSERT INTO GD_LS
	VALUES (4718667,
	4718665,
	2176,
	1376,
	2176,
	1328,
	4718666);
INSERT INTO GD_LS
	VALUES (4718668,
	4718665,
	2176,
	1328,
	2128,
	1328,
	4718667);
INSERT INTO O_OBJ
	VALUES (8388618,
	'Object F 1L Side',
	11,
	'OF',
	'',
	8388624);
INSERT INTO O_NBATTR
	VALUES (8388637,
	8388618);
INSERT INTO O_BATTR
	VALUES (8388637,
	8388618);
INSERT INTO O_ATTR
	VALUES (8388637,
	8388618,
	0,
	'of_id',
	'',
	'',
	'of_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (8388638,
	8388618);
INSERT INTO O_BATTR
	VALUES (8388638,
	8388618);
INSERT INTO O_ATTR
	VALUES (8388638,
	8388618,
	8388637,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (8388639,
	8388618);
INSERT INTO O_BATTR
	VALUES (8388639,
	8388618);
INSERT INTO O_ATTR
	VALUES (8388639,
	8388618,
	8388638,
	'ack_count',
	'',
	'',
	'ack_count',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (8388640,
	8388618);
INSERT INTO O_BATTR
	VALUES (8388640,
	8388618);
INSERT INTO O_ATTR
	VALUES (8388640,
	8388618,
	8388639,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	8388618);
INSERT INTO O_OIDA
	VALUES (8388637,
	8388618,
	0);
INSERT INTO O_RTIDA
	VALUES (8388637,
	8388618,
	0,
	8388611,
	8388613);
INSERT INTO SM_ISM
	VALUES (5242890,
	8388618);
INSERT INTO SM_SM
	VALUES (5242890,
	'',
	10);
INSERT INTO SM_MOORE
	VALUES (5242890);
INSERT INTO SM_EVTDI
	VALUES (5242881,
	5242890,
	'id',
	'',
	524294);
INSERT INTO SM_SUPDT
	VALUES (5242881,
	5242890,
	0);
INSERT INTO SM_SUPDT
	VALUES (5242882,
	5242890,
	0);
INSERT INTO SM_SDI
	VALUES (5242881,
	5242882,
	5242890);
INSERT INTO SM_STATE
	VALUES (5242881,
	5242890,
	5242881,
	'Starting IC4 Test',
	2,
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
	'Start IC4 Test',
	0,
	'',
	'OF1',
	'');
INSERT INTO SM_EIGN
	VALUES (5242881,
	5242881,
	5242890,
	5242881,
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
	'Verify Rel with Instance',
	0,
	'',
	'OF2',
	'');
INSERT INTO SM_EIGN
	VALUES (5242881,
	5242882,
	5242890,
	5242882,
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
	'Ack From Verify Rel',
	0,
	'',
	'OF3',
	'');
INSERT INTO SM_SEME
	VALUES (5242881,
	5242883,
	5242890,
	5242881);
INSERT INTO SM_LEVT
	VALUES (5242884,
	5242890,
	5242881);
INSERT INTO SM_SEVT
	VALUES (5242884,
	5242890,
	5242881);
INSERT INTO SM_EVT
	VALUES (5242884,
	5242890,
	5242881,
	4,
	'Finish IC4 Test',
	0,
	'',
	'OF4',
	'');
INSERT INTO SM_CH
	VALUES (5242881,
	5242884,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242881,
	5242884,
	5242890,
	5242881);
INSERT INTO SM_LEVT
	VALUES (5242885,
	5242890,
	5242881);
INSERT INTO SM_SEVT
	VALUES (5242885,
	5242890,
	5242881);
INSERT INTO SM_EVT
	VALUES (5242885,
	5242890,
	5242881,
	5,
	'Continue IC4 Test',
	0,
	'',
	'OF5',
	'');
INSERT INTO SM_CH
	VALUES (5242881,
	5242885,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242881,
	5242885,
	5242890,
	5242881);
INSERT INTO SM_STATE
	VALUES (5242882,
	5242890,
	5242882,
	'Verifying Rel with Instance',
	3,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (5242882,
	5242883,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242882,
	5242883,
	5242890,
	5242881);
INSERT INTO SM_CH
	VALUES (5242882,
	5242884,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242882,
	5242884,
	5242890,
	5242881);
INSERT INTO SM_CH
	VALUES (5242882,
	5242885,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242882,
	5242885,
	5242890,
	5242881);
INSERT INTO SM_STATE
	VALUES (5242883,
	5242890,
	5242881,
	'Waiting for Acks from Related Instances',
	4,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_SEME
	VALUES (5242883,
	5242883,
	5242890,
	5242881);
INSERT INTO SM_SEME
	VALUES (5242883,
	5242884,
	5242890,
	5242881);
INSERT INTO SM_CH
	VALUES (5242883,
	5242885,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242883,
	5242885,
	5242890,
	5242881);
INSERT INTO SM_STATE
	VALUES (5242884,
	5242890,
	5242881,
	'Finishing IC4 Test',
	5,
	1);
INSERT INTO SM_CH
	VALUES (5242884,
	5242881,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242884,
	5242881,
	5242890,
	5242881);
INSERT INTO SM_CH
	VALUES (5242884,
	5242882,
	5242890,
	5242882,
	'');
INSERT INTO SM_SEME
	VALUES (5242884,
	5242882,
	5242890,
	5242882);
INSERT INTO SM_CH
	VALUES (5242884,
	5242883,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242884,
	5242883,
	5242890,
	5242881);
INSERT INTO SM_CH
	VALUES (5242884,
	5242884,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242884,
	5242884,
	5242890,
	5242881);
INSERT INTO SM_CH
	VALUES (5242884,
	5242885,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242884,
	5242885,
	5242890,
	5242881);
INSERT INTO SM_STATE
	VALUES (5242885,
	5242890,
	5242881,
	'Initial State',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (5242885,
	5242881,
	5242890,
	5242881);
INSERT INTO SM_SEME
	VALUES (5242885,
	5242882,
	5242890,
	5242882);
INSERT INTO SM_CH
	VALUES (5242885,
	5242883,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242885,
	5242883,
	5242890,
	5242881);
INSERT INTO SM_CH
	VALUES (5242885,
	5242884,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242885,
	5242884,
	5242890,
	5242881);
INSERT INTO SM_SEME
	VALUES (5242885,
	5242885,
	5242890,
	5242881);
INSERT INTO SM_NSTXN
	VALUES (5242881,
	5242890,
	5242881,
	5242883,
	5242881);
INSERT INTO SM_TXN
	VALUES (5242881,
	5242890,
	5242883,
	5242881);
INSERT INTO SM_NSTXN
	VALUES (5242882,
	5242890,
	5242883,
	5242883,
	5242881);
INSERT INTO SM_TXN
	VALUES (5242882,
	5242890,
	5242883,
	5242881);
INSERT INTO SM_NSTXN
	VALUES (5242883,
	5242890,
	5242883,
	5242884,
	5242881);
INSERT INTO SM_TXN
	VALUES (5242883,
	5242890,
	5242884,
	5242881);
INSERT INTO SM_NSTXN
	VALUES (5242884,
	5242890,
	5242885,
	5242882,
	5242882);
INSERT INTO SM_TXN
	VALUES (5242884,
	5242890,
	5242882,
	5242882);
INSERT INTO SM_NSTXN
	VALUES (5242885,
	5242890,
	5242885,
	5242885,
	5242881);
INSERT INTO SM_TXN
	VALUES (5242885,
	5242890,
	5242881,
	5242881);
INSERT INTO SM_NSTXN
	VALUES (5242886,
	5242890,
	5242885,
	5242881,
	5242881);
INSERT INTO SM_TXN
	VALUES (5242886,
	5242890,
	5242885,
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
	'// select any rel_inst related by self-><rel_obj>[REL]
select any og1 related by self->OG[R3];
if (og1.i == 42)
  LOG::LogSuccess(message:"IC4: OF - select any rel_inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IC4: OF - select any rel_inst related by   self-><rel_obj>[REL]") ;
end if;

// select any rel_inst related by self-><assoc_obj>[REL]-><rel_obj>[REL]
select any og2 related by self->OH[R3]->OG[R3];
if (og2.i == 42)
  LOG::LogSuccess(message:"IC4: OF - select any rel_inst related by self-><assoc_obj>[REL]-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IC4: OF - select any rel_inst related by   self-><assoc_obj>[REL]-><rel_obj>[REL]") ;
end if;

// select any assoc_inst related by self-><assoc_obj>[REL]
select any oh related by self->OH[R3]; 
if (oh.i == 42)
  LOG::LogSuccess(message:"IC4: OF - select any assoc_inst related by self-><assoc_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IC4: OF - select any assoc_inst related by self-><assoc_obj>[REL]") ;
end if;

// select any self_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select any af related by self->OG[R3]->OF[R3];
if (af.i == self.i)
  LOG::LogSuccess(message:"IC4: OF - select any self_inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IC4: OF - select any self_inst related by   self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// select any self_inst related by self-><assoc_obj>[REL]-><self_obj>[REL]
select any anyf related by self->OH[R3]->OF[R3];
if (anyf.i == self.i)
  LOG::LogSuccess(message:"IC4: OF - select any self_inst related by self-><assoc_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IC4: OF - select any self_inst related by   self-><assoc_obj>[REL]-><self_obj>[REL]");
end if;

//create second instance
if (true)
  create object instance rel_inst2 of OG;
  create object instance assoc_inst2 of OH;
  relate self to rel_inst2 across R3 using assoc_inst2;
  assign rel_inst2.i = 52;
  assign assoc_inst2.i = 52;
  assign self.i = 52;
end if;

// select many rel_insts related by self-><rel_obj>[REL]
select many ogs related by self->OG[R3];
assign c = cardinality ogs;
if (c == 2) 
  LOG::LogSuccess(message:"IC4: OF - select many rel_insts related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IC4: OF - select many rel_insts related by   self-><rel_obj>[REL]") ;
end if;

// for each rel_insts related by self-><rel_obj>[REL]
for each og in ogs
  if (og.i == 52)
    LOG::LogSuccess(message:"IC4: OF - for each rel_insts related by self-><rel_obj>[REL]");
  else 
    if (og.i == 42)
      LOG::LogSuccess(message:"IC4: OF - for each rel_insts related by self-><rel_obj>[REL]") ;
    else  
      LOG::LogFailure(message:"IC4: OF - for each rel_insts related by self-><rel_obj>[REL]");
    end if;
  end if;
end for;

// select many rel_insts related by self-><assoc_obj>[REL]-><rel_obj>[REL]
select many ogs related by self->OH[R3]->OG[R3];
assign c = cardinality ogs;
if (c == 2) 
  LOG::LogSuccess(message:"IC4: OF - select many rel_insts related by self-><assoc_obj>[REL]-><rel_obj>[REL]");
else 
  LOG::LogFailure(message:"IC4: OF - select many rel_insts related by   self-><assoc_obj>[REL]-><rel_obj>[REL]") ;
end if;

// for each rel_insts related by self-><assoc_obj>[REL]-><rel_obj>[REL]
for each og in ogs
  if (og.i == 52)
    LOG::LogSuccess(message:"IC4: OF - for each rel_insts related by self-><assoc_obj>[REL]-><rel_obj>[REL]");
    generate OG2:''Verify Rel with Instance''(id:self.of_id) to og;
    select one assoc_inst related by og->OH[R3];
    generate OH1:''Verify Rel with Instance Reply to L Side''(l_id:self.of_id,r_id:og.og_id) to assoc_inst;
  else 
    if (og.i == 42)
      LOG::LogSuccess(message:"IC4: OF - for each rel_insts related by self-><assoc_obj>[REL]-><rel_obj>[REL]") ;
      generate OG2:''Verify Rel with Instance''(id:self.of_id) to og;
      select one assoc_inst related by og->OH[R3];
      generate OH1:''Verify Rel with Instance Reply to L Side''(l_id:self.of_id,r_id:og.og_id) to assoc_inst;
    else 
      LOG::LogFailure(message:"IC4: OF - for each rel_insts related by self-><assoc_obj>[REL]-><rel_obj>[REL]");
    end if;
  end if;
end for;

assign self.ack_count = 4;',
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
	'select many ogs related by self->OG[R3];
assign found = FALSE;
for each og in ogs
  if (found == FALSE)
    if (og.og_id == rcvd_evt.id) 
      assign found = TRUE;
      generate OG3:''Ack From Verify Rel''() to og;
    end if;
  end if;
end for;
if (found == FALSE)
  LOG::LogFailure(message:"IC4: OF - Verifying Rel with Instance");
end if;',
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
	'assign self.ack_count = self.ack_count - 1;
if (self.ack_count == 0)
  generate OF4:''Finish IC4 Test''() to self;
end if;

',
	'');
INSERT INTO SM_MOAH
	VALUES (5242884,
	5242890,
	5242884);
INSERT INTO SM_AH
	VALUES (5242884,
	5242890);
INSERT INTO SM_ACT
	VALUES (5242884,
	5242890,
	1,
	'//check unrelate and delete
select many ogs related by self->OG[R3];
for each og in ogs
  select one oneh related by og->OH[R3];
  unrelate og from self across R3 using oneh;
  delete object instance og;
  delete object instance oneh;
end for;
select many ogs related by self->OG[R3];
if (empty ogs)
  LOG::LogSuccess(message:"IC4: OF - delete object instance <rel_inst>");
else
  LOG::LogFailure(message:"IC4: OF - delete object instance <rel_inst>");
end if;

select any driver from instances of ICD;
generate ICD9:''IC4 Test F Complete''() to driver;',
	'');
INSERT INTO SM_MOAH
	VALUES (5242885,
	5242890,
	5242885);
INSERT INTO SM_AH
	VALUES (5242885,
	5242890);
INSERT INTO SM_ACT
	VALUES (5242885,
	5242890,
	1,
	'create object instance rel_inst1 of OG;
create object instance assoc_inst1 of OH;
relate self to rel_inst1 across R3 using assoc_inst1;
assign rel_inst1.i = 42;
assign assoc_inst1.i = 42;
assign self.i = 42;

generate OF5:''Continue IC4 Test''() to self;
',
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
	1600,
	4200,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (5242882,
	5242881,
	5242881,
	41);
INSERT INTO GD_SHP
	VALUES (5242882,
	1680,
	1264,
	2016,
	1568);
INSERT INTO GD_GE
	VALUES (5242883,
	5242881,
	5242882,
	41);
INSERT INTO GD_SHP
	VALUES (5242883,
	2048,
	1264,
	2352,
	1568);
INSERT INTO GD_GE
	VALUES (5242884,
	5242881,
	5242883,
	41);
INSERT INTO GD_SHP
	VALUES (5242884,
	1680,
	1632,
	2016,
	1760);
INSERT INTO GD_GE
	VALUES (5242885,
	5242881,
	5242884,
	41);
INSERT INTO GD_SHP
	VALUES (5242885,
	2064,
	1632,
	2352,
	1760);
INSERT INTO GD_GE
	VALUES (5242886,
	5242881,
	5242885,
	41);
INSERT INTO GD_SHP
	VALUES (5242886,
	1680,
	1072,
	2016,
	1184);
INSERT INTO GD_GE
	VALUES (5242887,
	5242881,
	5242881,
	42);
INSERT INTO GD_CON
	VALUES (5242887,
	5242882,
	5242884,
	0);
INSERT INTO GD_CTXT
	VALUES (5242887,
	0,
	0,
	0,
	0,
	0,
	0,
	1696,
	1584,
	1899,
	1626,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5242888,
	5242887,
	1712,
	1568,
	1712,
	1632,
	0);
INSERT INTO GD_GE
	VALUES (5242889,
	5242881,
	5242883,
	42);
INSERT INTO GD_CON
	VALUES (5242889,
	5242884,
	5242885,
	0);
INSERT INTO GD_CTXT
	VALUES (5242889,
	0,
	0,
	0,
	0,
	0,
	0,
	2163,
	1766,
	2307,
	1802,
	116,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5242890,
	5242889,
	1984,
	1760,
	1984,
	1792,
	0);
INSERT INTO GD_LS
	VALUES (5242891,
	5242889,
	1984,
	1792,
	2160,
	1792,
	5242890);
INSERT INTO GD_LS
	VALUES (5242892,
	5242889,
	2160,
	1792,
	2160,
	1760,
	5242891);
INSERT INTO GD_GE
	VALUES (5242893,
	5242881,
	5242882,
	42);
INSERT INTO GD_CON
	VALUES (5242893,
	5242884,
	5242884,
	0);
INSERT INTO GD_CTXT
	VALUES (5242893,
	0,
	0,
	0,
	0,
	0,
	0,
	1952,
	1568,
	2099,
	1605,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5242894,
	5242893,
	1952,
	1632,
	1952,
	1600,
	0);
INSERT INTO GD_LS
	VALUES (5242895,
	5242893,
	1952,
	1600,
	2000,
	1600,
	5242894);
INSERT INTO GD_LS
	VALUES (5242896,
	5242893,
	2000,
	1600,
	2000,
	1632,
	5242895);
INSERT INTO GD_GE
	VALUES (5242897,
	5242881,
	5242886,
	42);
INSERT INTO GD_CON
	VALUES (5242897,
	5242886,
	5242886,
	0);
INSERT INTO GD_CTXT
	VALUES (5242897,
	0,
	0,
	0,
	0,
	0,
	0,
	1600,
	983,
	1775,
	1038,
	-7,
	-26,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5242898,
	5242897,
	1680,
	1120,
	1632,
	1120,
	0);
INSERT INTO GD_LS
	VALUES (5242899,
	5242897,
	1632,
	1120,
	1632,
	1024,
	5242898);
INSERT INTO GD_LS
	VALUES (5242900,
	5242897,
	1632,
	1024,
	1728,
	1024,
	5242899);
INSERT INTO GD_LS
	VALUES (5242901,
	5242897,
	1728,
	1024,
	1728,
	1072,
	5242900);
INSERT INTO GD_GE
	VALUES (5242902,
	5242881,
	5242885,
	42);
INSERT INTO GD_CON
	VALUES (5242902,
	5242886,
	5242882,
	0);
INSERT INTO GD_CTXT
	VALUES (5242902,
	0,
	0,
	0,
	0,
	0,
	0,
	1847,
	1219,
	2023,
	1251,
	23,
	10,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5242903,
	5242902,
	1840,
	1184,
	1840,
	1264,
	0);
INSERT INTO GD_GE
	VALUES (5242904,
	5242881,
	5242884,
	42);
INSERT INTO GD_CON
	VALUES (5242904,
	5242886,
	5242883,
	0);
INSERT INTO GD_CTXT
	VALUES (5242904,
	0,
	0,
	0,
	0,
	0,
	0,
	2039,
	1080,
	2216,
	1115,
	-88,
	-31,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5242905,
	5242904,
	2016,
	1136,
	2160,
	1136,
	0);
INSERT INTO GD_LS
	VALUES (5242906,
	5242904,
	2160,
	1136,
	2160,
	1264,
	5242905);
INSERT INTO O_OBJ
	VALUES (8388619,
	'Object G MR Side',
	12,
	'OG',
	'',
	8388624);
INSERT INTO O_NBATTR
	VALUES (8388641,
	8388619);
INSERT INTO O_BATTR
	VALUES (8388641,
	8388619);
INSERT INTO O_ATTR
	VALUES (8388641,
	8388619,
	0,
	'og_id',
	'',
	'',
	'og_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (8388642,
	8388619);
INSERT INTO O_BATTR
	VALUES (8388642,
	8388619);
INSERT INTO O_ATTR
	VALUES (8388642,
	8388619,
	8388641,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (8388643,
	8388619);
INSERT INTO O_BATTR
	VALUES (8388643,
	8388619);
INSERT INTO O_ATTR
	VALUES (8388643,
	8388619,
	8388642,
	'ack_count',
	'',
	'',
	'ack_count',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (8388644,
	8388619);
INSERT INTO O_BATTR
	VALUES (8388644,
	8388619);
INSERT INTO O_ATTR
	VALUES (8388644,
	8388619,
	8388643,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	8388619);
INSERT INTO O_OIDA
	VALUES (8388641,
	8388619,
	0);
INSERT INTO O_RTIDA
	VALUES (8388641,
	8388619,
	0,
	8388611,
	8388614);
INSERT INTO SM_ISM
	VALUES (5767179,
	8388619);
INSERT INTO SM_SM
	VALUES (5767179,
	'',
	11);
INSERT INTO SM_MOORE
	VALUES (5767179);
INSERT INTO SM_EVTDI
	VALUES (5767169,
	5767179,
	'id',
	'',
	524294);
INSERT INTO SM_SUPDT
	VALUES (5767169,
	5767179,
	0);
INSERT INTO SM_SUPDT
	VALUES (5767170,
	5767179,
	0);
INSERT INTO SM_SDI
	VALUES (5767169,
	5767170,
	5767179);
INSERT INTO SM_STATE
	VALUES (5767169,
	5767179,
	5767169,
	'Starting IC4 Test',
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
	'Start IC4 Test',
	0,
	'',
	'OG1',
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
INSERT INTO SM_LEVT
	VALUES (5767170,
	5767179,
	5767170);
INSERT INTO SM_SEVT
	VALUES (5767170,
	5767179,
	5767170);
INSERT INTO SM_EVT
	VALUES (5767170,
	5767179,
	5767170,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OG2',
	'');
INSERT INTO SM_EIGN
	VALUES (5767169,
	5767170,
	5767179,
	5767170,
	'');
INSERT INTO SM_SEME
	VALUES (5767169,
	5767170,
	5767179,
	5767170);
INSERT INTO SM_LEVT
	VALUES (5767171,
	5767179,
	5767169);
INSERT INTO SM_SEVT
	VALUES (5767171,
	5767179,
	5767169);
INSERT INTO SM_EVT
	VALUES (5767171,
	5767179,
	5767169,
	4,
	'Finish IC4 Test',
	0,
	'',
	'OG4',
	'');
INSERT INTO SM_EIGN
	VALUES (5767169,
	5767171,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767169,
	5767171,
	5767179,
	5767169);
INSERT INTO SM_LEVT
	VALUES (5767172,
	5767179,
	5767169);
INSERT INTO SM_SEVT
	VALUES (5767172,
	5767179,
	5767169);
INSERT INTO SM_EVT
	VALUES (5767172,
	5767179,
	5767169,
	3,
	'Ack From Verify Rel',
	0,
	'',
	'OG3',
	'');
INSERT INTO SM_SEME
	VALUES (5767169,
	5767172,
	5767179,
	5767169);
INSERT INTO SM_LEVT
	VALUES (5767173,
	5767179,
	5767169);
INSERT INTO SM_SEVT
	VALUES (5767173,
	5767179,
	5767169);
INSERT INTO SM_EVT
	VALUES (5767173,
	5767179,
	5767169,
	5,
	'Continue IC4 Test',
	0,
	'',
	'OG5',
	'');
INSERT INTO SM_CH
	VALUES (5767169,
	5767173,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767169,
	5767173,
	5767179,
	5767169);
INSERT INTO SM_STATE
	VALUES (5767170,
	5767179,
	5767170,
	'Verifying Rel with Instance',
	3,
	0);
INSERT INTO SM_CH
	VALUES (5767170,
	5767169,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767170,
	5767169,
	5767179,
	5767169);
INSERT INTO SM_CH
	VALUES (5767170,
	5767170,
	5767179,
	5767170,
	'');
INSERT INTO SM_SEME
	VALUES (5767170,
	5767170,
	5767179,
	5767170);
INSERT INTO SM_CH
	VALUES (5767170,
	5767171,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767170,
	5767171,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767170,
	5767172,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767170,
	5767172,
	5767179,
	5767169);
INSERT INTO SM_CH
	VALUES (5767170,
	5767173,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767170,
	5767173,
	5767179,
	5767169);
INSERT INTO SM_STATE
	VALUES (5767171,
	5767179,
	5767169,
	'Waiting for Acks from Related Instances',
	4,
	0);
INSERT INTO SM_CH
	VALUES (5767171,
	5767169,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767171,
	5767169,
	5767179,
	5767169);
INSERT INTO SM_CH
	VALUES (5767171,
	5767170,
	5767179,
	5767170,
	'');
INSERT INTO SM_SEME
	VALUES (5767171,
	5767170,
	5767179,
	5767170);
INSERT INTO SM_SEME
	VALUES (5767171,
	5767171,
	5767179,
	5767169);
INSERT INTO SM_SEME
	VALUES (5767171,
	5767172,
	5767179,
	5767169);
INSERT INTO SM_CH
	VALUES (5767171,
	5767173,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767171,
	5767173,
	5767179,
	5767169);
INSERT INTO SM_STATE
	VALUES (5767172,
	5767179,
	5767169,
	'Finishing IC4 Test',
	5,
	1);
INSERT INTO SM_EIGN
	VALUES (5767172,
	5767169,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767172,
	5767169,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767172,
	5767170,
	5767179,
	5767170,
	'');
INSERT INTO SM_SEME
	VALUES (5767172,
	5767170,
	5767179,
	5767170);
INSERT INTO SM_EIGN
	VALUES (5767172,
	5767171,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767172,
	5767171,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767172,
	5767172,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767172,
	5767172,
	5767179,
	5767169);
INSERT INTO SM_CH
	VALUES (5767172,
	5767173,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767172,
	5767173,
	5767179,
	5767169);
INSERT INTO SM_STATE
	VALUES (5767173,
	5767179,
	5767169,
	'Initial State',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (5767173,
	5767169,
	5767179,
	5767169);
INSERT INTO SM_SEME
	VALUES (5767173,
	5767170,
	5767179,
	5767170);
INSERT INTO SM_CH
	VALUES (5767173,
	5767171,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767173,
	5767171,
	5767179,
	5767169);
INSERT INTO SM_CH
	VALUES (5767173,
	5767172,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767173,
	5767172,
	5767179,
	5767169);
INSERT INTO SM_SEME
	VALUES (5767173,
	5767173,
	5767179,
	5767169);
INSERT INTO SM_NSTXN
	VALUES (5767169,
	5767179,
	5767171,
	5767172,
	5767169);
INSERT INTO SM_TXN
	VALUES (5767169,
	5767179,
	5767171,
	5767169);
INSERT INTO SM_NSTXN
	VALUES (5767170,
	5767179,
	5767169,
	5767172,
	5767169);
INSERT INTO SM_TXN
	VALUES (5767170,
	5767179,
	5767171,
	5767169);
INSERT INTO SM_NSTXN
	VALUES (5767171,
	5767179,
	5767171,
	5767171,
	5767169);
INSERT INTO SM_TXN
	VALUES (5767171,
	5767179,
	5767172,
	5767169);
INSERT INTO SM_NSTXN
	VALUES (5767172,
	5767179,
	5767173,
	5767169,
	5767169);
INSERT INTO SM_TXN
	VALUES (5767172,
	5767179,
	5767173,
	5767169);
INSERT INTO SM_NSTXN
	VALUES (5767173,
	5767179,
	5767173,
	5767170,
	5767170);
INSERT INTO SM_TXN
	VALUES (5767173,
	5767179,
	5767170,
	5767170);
INSERT INTO SM_NSTXN
	VALUES (5767174,
	5767179,
	5767173,
	5767173,
	5767169);
INSERT INTO SM_TXN
	VALUES (5767174,
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
	'// select one one_inst related by self-><rel_obj>[REL]
select one onef related by self->OF[R3];
if (onef.i == 32)
  LOG::LogSuccess(message:"IC4: OG - select one inst related by self-><rel_obj>[REL]");
  generate OF2:''Verify Rel with Instance''(id:self.og_id) to onef;
else 
  LOG::LogFailure(message:"IC4: OG - select one inst related by   self-><rel_obj>[REL]");
end if;

// select one assoc_inst related by self-><rel_obj>[REL]
select one oh related by self->OH[R3];
if (oh.i == 32)
  LOG::LogSuccess(message:"IC4: OG - select one assoc_inst related by self-><rel_obj>[REL]") ;
  generate OH2:''Verify Rel with Instance Reply to R Side''(l_id:onef.of_id,r_id:self.og_id) to oh;
else 
  LOG::LogFailure(message:"IC4: OG - select one assoc_inst related by  self-><rel_obj>[REL]");
end if;

//select any any_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select any og related by self->OF[R3]->OG[R3];
if (og.i == self.i)
  LOG::LogSuccess(message:"IC4: OG - select any inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IC4: OG - select any inst related by   self-><rel_obj>[REL]-><self_obj>[REL]");
end if;

assign self.ack_count = 2;',
	'');
INSERT INTO SM_MOAH
	VALUES (5767170,
	5767179,
	5767170);
INSERT INTO SM_AH
	VALUES (5767170,
	5767179);
INSERT INTO SM_ACT
	VALUES (5767170,
	5767179,
	1,
	'select one onef related by self->OF[R3];
if (onef.of_id == rcvd_evt.id)
  LOG::LogSuccess(message:"IC4: OG - Verifying Rel with Instance") ;
  generate OF3:''Ack From Verify Rel''() to onef;
else 
  LOG::LogFailure(message:"IC4: OG - Verifying Rel with Instance") ;
end if;',
	'');
INSERT INTO SM_MOAH
	VALUES (5767171,
	5767179,
	5767171);
INSERT INTO SM_AH
	VALUES (5767171,
	5767179);
INSERT INTO SM_ACT
	VALUES (5767171,
	5767179,
	1,
	'assign self.ack_count = self.ack_count - 1;
if (self.ack_count == 0)
  generate OG4:''Finish IC4 Test''() to self;
end if;',
	'');
INSERT INTO SM_MOAH
	VALUES (5767172,
	5767179,
	5767172);
INSERT INTO SM_AH
	VALUES (5767172,
	5767179);
INSERT INTO SM_ACT
	VALUES (5767172,
	5767179,
	1,
	'// check delete rel_inst
select one onef related by self->OF[R3];
select one oneh related by self->OH[R3];
unrelate onef from self across R3 using oneh;
delete object instance onef;
select one onef related by self->OF[R3];
if (empty onef)
  LOG::LogSuccess(message:"IC4: OG - delete object instance  <rel_inst>");
else
  LOG::LogFailure(message:"IC4: OG - delete object instance <rel_inst>");
end if;

delete object instance oneh;
select any driver from instances of ICD;
generate ICD10:''IC4 Test G Complete''() to driver;',
	'');
INSERT INTO SM_MOAH
	VALUES (5767173,
	5767179,
	5767173);
INSERT INTO SM_AH
	VALUES (5767173,
	5767179);
INSERT INTO SM_ACT
	VALUES (5767173,
	5767179,
	1,
	'create object instance rel_inst of OF;
create object instance assoc_inst of OH;
relate self to rel_inst across R3 using assoc_inst;
assign rel_inst.i = 32;
assign assoc_inst.i = 32;
assign self.i = 32;

generate OG5:''Continue IC4 Test''() to self;
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
	1600,
	4200,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (5767170,
	5767169,
	5767169,
	41);
INSERT INTO GD_SHP
	VALUES (5767170,
	1696,
	1280,
	2016,
	1568);
INSERT INTO GD_GE
	VALUES (5767171,
	5767169,
	5767170,
	41);
INSERT INTO GD_SHP
	VALUES (5767171,
	2048,
	1280,
	2368,
	1568);
INSERT INTO GD_GE
	VALUES (5767172,
	5767169,
	5767171,
	41);
INSERT INTO GD_SHP
	VALUES (5767172,
	1696,
	1680,
	2016,
	1856);
INSERT INTO GD_GE
	VALUES (5767173,
	5767169,
	5767172,
	41);
INSERT INTO GD_SHP
	VALUES (5767173,
	2048,
	1600,
	2368,
	1888);
INSERT INTO GD_GE
	VALUES (5767174,
	5767169,
	5767173,
	41);
INSERT INTO GD_SHP
	VALUES (5767174,
	1696,
	1024,
	2016,
	1168);
INSERT INTO GD_GE
	VALUES (5767175,
	5767169,
	5767170,
	42);
INSERT INTO GD_CON
	VALUES (5767175,
	5767170,
	5767172,
	0);
INSERT INTO GD_CTXT
	VALUES (5767175,
	0,
	0,
	0,
	0,
	0,
	0,
	1696,
	1587,
	1903,
	1650,
	0,
	-22,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5767176,
	5767175,
	1712,
	1568,
	1712,
	1680,
	0);
INSERT INTO GD_GE
	VALUES (5767177,
	5767169,
	5767171,
	42);
INSERT INTO GD_CON
	VALUES (5767177,
	5767172,
	5767173,
	0);
INSERT INTO GD_CTXT
	VALUES (5767177,
	0,
	0,
	0,
	0,
	0,
	0,
	1981,
	1886,
	2133,
	1918,
	-66,
	-9,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5767178,
	5767177,
	1984,
	1856,
	1984,
	1920,
	0);
INSERT INTO GD_LS
	VALUES (5767179,
	5767177,
	1984,
	1920,
	2240,
	1920,
	5767178);
INSERT INTO GD_LS
	VALUES (5767180,
	5767177,
	2240,
	1920,
	2240,
	1888,
	5767179);
INSERT INTO GD_GE
	VALUES (5767181,
	5767169,
	5767169,
	42);
INSERT INTO GD_CON
	VALUES (5767181,
	5767172,
	5767172,
	0);
INSERT INTO GD_CTXT
	VALUES (5767181,
	0,
	0,
	0,
	0,
	0,
	0,
	1936,
	1612,
	2042,
	1654,
	-7,
	-11,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5767182,
	5767181,
	1952,
	1680,
	1952,
	1648,
	0);
INSERT INTO GD_LS
	VALUES (5767183,
	5767181,
	1952,
	1648,
	1984,
	1648,
	5767182);
INSERT INTO GD_LS
	VALUES (5767184,
	5767181,
	1984,
	1648,
	1984,
	1680,
	5767183);
INSERT INTO GD_GE
	VALUES (5767185,
	5767169,
	5767174,
	42);
INSERT INTO GD_CON
	VALUES (5767185,
	5767174,
	5767170,
	0);
INSERT INTO GD_CTXT
	VALUES (5767185,
	0,
	0,
	0,
	0,
	0,
	0,
	1855,
	1215,
	2014,
	1247,
	31,
	6,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5767186,
	5767185,
	1840,
	1168,
	1840,
	1280,
	0);
INSERT INTO GD_GE
	VALUES (5767187,
	5767169,
	5767172,
	42);
INSERT INTO GD_CON
	VALUES (5767187,
	5767174,
	5767174,
	0);
INSERT INTO GD_CTXT
	VALUES (5767187,
	0,
	0,
	0,
	0,
	0,
	0,
	1647,
	943,
	1814,
	982,
	15,
	-26,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5767188,
	5767187,
	1696,
	1088,
	1648,
	1088,
	0);
INSERT INTO GD_LS
	VALUES (5767189,
	5767187,
	1648,
	1088,
	1648,
	976,
	5767188);
INSERT INTO GD_LS
	VALUES (5767190,
	5767187,
	1648,
	976,
	1744,
	976,
	5767189);
INSERT INTO GD_LS
	VALUES (5767191,
	5767187,
	1744,
	976,
	1744,
	1024,
	5767190);
INSERT INTO GD_GE
	VALUES (5767192,
	5767169,
	5767173,
	42);
INSERT INTO GD_CON
	VALUES (5767192,
	5767174,
	5767171,
	0);
INSERT INTO GD_CTXT
	VALUES (5767192,
	0,
	0,
	0,
	0,
	0,
	0,
	2156,
	1112,
	2321,
	1161,
	28,
	7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5767193,
	5767192,
	2016,
	1088,
	2144,
	1088,
	0);
INSERT INTO GD_LS
	VALUES (5767194,
	5767192,
	2144,
	1088,
	2144,
	1280,
	5767193);
INSERT INTO O_OBJ
	VALUES (8388620,
	'Object H 11M Side',
	13,
	'OH',
	'',
	8388624);
INSERT INTO O_REF
	VALUES (8388620,
	8388618,
	0,
	8388637,
	8388611,
	8388615,
	8388613,
	8388645,
	8388612,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (8388645,
	8388620,
	8388637,
	8388618,
	1);
INSERT INTO O_ATTR
	VALUES (8388645,
	8388620,
	0,
	'of_id',
	'',
	'',
	'of_id',
	0,
	524296);
INSERT INTO O_REF
	VALUES (8388620,
	8388619,
	0,
	8388641,
	8388611,
	8388615,
	8388614,
	8388646,
	8388613,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (8388646,
	8388620,
	8388641,
	8388619,
	1);
INSERT INTO O_ATTR
	VALUES (8388646,
	8388620,
	8388645,
	'og_id',
	'',
	'',
	'og_id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (8388647,
	8388620);
INSERT INTO O_BATTR
	VALUES (8388647,
	8388620);
INSERT INTO O_ATTR
	VALUES (8388647,
	8388620,
	8388646,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (8388648,
	8388620);
INSERT INTO O_BATTR
	VALUES (8388648,
	8388620);
INSERT INTO O_ATTR
	VALUES (8388648,
	8388620,
	8388647,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	8388620);
INSERT INTO O_OIDA
	VALUES (8388645,
	8388620,
	0);
INSERT INTO O_OIDA
	VALUES (8388646,
	8388620,
	0);
INSERT INTO SM_ISM
	VALUES (6291468,
	8388620);
INSERT INTO SM_SM
	VALUES (6291468,
	'',
	12);
INSERT INTO SM_MOORE
	VALUES (6291468);
INSERT INTO SM_EVTDI
	VALUES (6291457,
	6291468,
	'l_id',
	'',
	524294);
INSERT INTO SM_EVTDI
	VALUES (6291458,
	6291468,
	'r_id',
	'',
	524294);
INSERT INTO SM_SUPDT
	VALUES (6291457,
	6291468,
	0);
INSERT INTO SM_SDI
	VALUES (6291457,
	6291457,
	6291468);
INSERT INTO SM_SDI
	VALUES (6291458,
	6291457,
	6291468);
INSERT INTO SM_STATE
	VALUES (6291457,
	6291468,
	6291457,
	'Verifying Rel with Instance Reply to L Side',
	3,
	0);
INSERT INTO SM_LEVT
	VALUES (6291457,
	6291468,
	6291457);
INSERT INTO SM_SEVT
	VALUES (6291457,
	6291468,
	6291457);
INSERT INTO SM_EVT
	VALUES (6291457,
	6291468,
	6291457,
	1,
	'Verify Rel with Instance Reply to L Side',
	0,
	'',
	'OH1',
	'');
INSERT INTO SM_CH
	VALUES (6291457,
	6291457,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291457,
	6291457,
	6291468,
	6291457);
INSERT INTO SM_LEVT
	VALUES (6291458,
	6291468,
	6291457);
INSERT INTO SM_SEVT
	VALUES (6291458,
	6291468,
	6291457);
INSERT INTO SM_EVT
	VALUES (6291458,
	6291468,
	6291457,
	2,
	'Verify Rel with Instance Reply to R Side',
	0,
	'',
	'OH2',
	'');
INSERT INTO SM_CH
	VALUES (6291457,
	6291458,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291457,
	6291458,
	6291468,
	6291457);
INSERT INTO SM_STATE
	VALUES (6291458,
	6291468,
	6291457,
	'Verifying Rel with Instance Reply to R Side',
	2,
	0);
INSERT INTO SM_CH
	VALUES (6291458,
	6291457,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291458,
	6291457,
	6291468,
	6291457);
INSERT INTO SM_CH
	VALUES (6291458,
	6291458,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291458,
	6291458,
	6291468,
	6291457);
INSERT INTO SM_STATE
	VALUES (6291459,
	6291468,
	0,
	'Waiting',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (6291459,
	6291457,
	6291468,
	6291457);
INSERT INTO SM_SEME
	VALUES (6291459,
	6291458,
	6291468,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291457,
	6291468,
	6291459,
	6291457,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291457,
	6291468,
	6291457,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291458,
	6291468,
	6291459,
	6291458,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291458,
	6291468,
	6291458,
	6291457);
INSERT INTO SM_MOAH
	VALUES (6291457,
	6291468,
	6291457);
INSERT INTO SM_AH
	VALUES (6291457,
	6291468);
INSERT INTO SM_ACT
	VALUES (6291457,
	6291468,
	1,
	'select one l related by self->OF[R3];
select one r related by self->OG[R3];

assign l_found = FALSE;
assign r_found = FALSE;
if (l.of_id == rcvd_evt.l_id)
  assign l_found = TRUE;
end if;
if (r.og_id == rcvd_evt.r_id)
  assign r_found = TRUE;
end if;
if (l_found AND r_found)
  generate OF3:''Ack From Verify Rel''() to l;
  LOG::LogSuccess(message:"IC4: OH - Verifying Rel with Instance Reply to L Side") ;
else
   LOG::LogFailure(message:"IC4: OH - Verifying Rel with Instance Reply to L Side") ;
end if;
',
	'');
INSERT INTO SM_MOAH
	VALUES (6291458,
	6291468,
	6291458);
INSERT INTO SM_AH
	VALUES (6291458,
	6291468);
INSERT INTO SM_ACT
	VALUES (6291458,
	6291468,
	1,
	'select one l related by self->OF[R3];
select one r related by self->OG[R3];
assign l_found = FALSE;
assign r_found = FALSE;
if (l.of_id == rcvd_evt.l_id)
  assign l_found = TRUE;
end if;
if (r.og_id == rcvd_evt.r_id)
  assign r_found = TRUE;
end if;
if (l_found AND r_found)
  generate OG3:''Ack From Verify Rel''() to r;
  LOG::LogSuccess(message:"IC4: OH - Verifying Rel with Instance Reply to R Side");
else
    LOG::LogFailure(message:"IC4: OH - Verifying Rel with Instance Reply to R Side") ;
end if;
',
	'');
INSERT INTO SM_MOAH
	VALUES (6291459,
	6291468,
	6291459);
INSERT INTO SM_AH
	VALUES (6291459,
	6291468);
INSERT INTO SM_ACT
	VALUES (6291459,
	6291468,
	1,
	'',
	'');
INSERT INTO GD_MD
	VALUES (6291457,
	8,
	6291468,
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
	VALUES (6291458,
	6291457,
	6291457,
	41);
INSERT INTO GD_SHP
	VALUES (6291458,
	1664,
	1472,
	2000,
	1792);
INSERT INTO GD_GE
	VALUES (6291459,
	6291457,
	6291458,
	41);
INSERT INTO GD_SHP
	VALUES (6291459,
	2016,
	1472,
	2352,
	1792);
INSERT INTO GD_GE
	VALUES (6291460,
	6291457,
	6291459,
	41);
INSERT INTO GD_SHP
	VALUES (6291460,
	1680,
	1248,
	2336,
	1360);
INSERT INTO GD_GE
	VALUES (6291461,
	6291457,
	6291457,
	42);
INSERT INTO GD_CON
	VALUES (6291461,
	6291460,
	6291458,
	0);
INSERT INTO GD_CTXT
	VALUES (6291461,
	0,
	0,
	0,
	0,
	0,
	0,
	1672,
	1401,
	1943,
	1444,
	-56,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291462,
	6291461,
	1744,
	1360,
	1744,
	1472,
	0);
INSERT INTO GD_GE
	VALUES (6291463,
	6291457,
	6291458,
	42);
INSERT INTO GD_CON
	VALUES (6291463,
	6291460,
	6291459,
	0);
INSERT INTO GD_CTXT
	VALUES (6291463,
	0,
	0,
	0,
	0,
	0,
	0,
	1986,
	1387,
	2313,
	1422,
	-254,
	-14,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291464,
	6291463,
	2256,
	1360,
	2256,
	1472,
	0);
INSERT INTO O_OBJ
	VALUES (8388621,
	'Object L 1L Side',
	17,
	'OL',
	'',
	8388624);
INSERT INTO O_NBATTR
	VALUES (8388649,
	8388621);
INSERT INTO O_BATTR
	VALUES (8388649,
	8388621);
INSERT INTO O_ATTR
	VALUES (8388649,
	8388621,
	0,
	'ol_id',
	'',
	'',
	'ol_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (8388650,
	8388621);
INSERT INTO O_BATTR
	VALUES (8388650,
	8388621);
INSERT INTO O_ATTR
	VALUES (8388650,
	8388621,
	8388649,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (8388651,
	8388621);
INSERT INTO O_BATTR
	VALUES (8388651,
	8388621);
INSERT INTO O_ATTR
	VALUES (8388651,
	8388621,
	8388650,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	8388621);
INSERT INTO O_OIDA
	VALUES (8388649,
	8388621,
	0);
INSERT INTO O_RTIDA
	VALUES (8388649,
	8388621,
	0,
	8388612,
	8388616);
INSERT INTO SM_ISM
	VALUES (6815757,
	8388621);
INSERT INTO SM_SM
	VALUES (6815757,
	'',
	13);
INSERT INTO SM_MOORE
	VALUES (6815757);
INSERT INTO SM_EVTDI
	VALUES (6815745,
	6815757,
	'id',
	'',
	524294);
INSERT INTO SM_SUPDT
	VALUES (6815745,
	6815757,
	0);
INSERT INTO SM_SUPDT
	VALUES (6815746,
	6815757,
	0);
INSERT INTO SM_SDI
	VALUES (6815745,
	6815746,
	6815757);
INSERT INTO SM_STATE
	VALUES (6815745,
	6815757,
	6815745,
	'Starting IC7 Test',
	2,
	0);
INSERT INTO SM_LEVT
	VALUES (6815745,
	6815757,
	6815745);
INSERT INTO SM_SEVT
	VALUES (6815745,
	6815757,
	6815745);
INSERT INTO SM_EVT
	VALUES (6815745,
	6815757,
	6815745,
	1,
	'Start IC7 Test',
	0,
	'',
	'OL1',
	'');
INSERT INTO SM_EIGN
	VALUES (6815745,
	6815745,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815745,
	6815745,
	6815757,
	6815745);
INSERT INTO SM_LEVT
	VALUES (6815746,
	6815757,
	6815746);
INSERT INTO SM_SEVT
	VALUES (6815746,
	6815757,
	6815746);
INSERT INTO SM_EVT
	VALUES (6815746,
	6815757,
	6815746,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OL2',
	'');
INSERT INTO SM_EIGN
	VALUES (6815745,
	6815746,
	6815757,
	6815746,
	'');
INSERT INTO SM_SEME
	VALUES (6815745,
	6815746,
	6815757,
	6815746);
INSERT INTO SM_LEVT
	VALUES (6815747,
	6815757,
	6815745);
INSERT INTO SM_SEVT
	VALUES (6815747,
	6815757,
	6815745);
INSERT INTO SM_EVT
	VALUES (6815747,
	6815757,
	6815745,
	3,
	'Finish IC7 Test',
	0,
	'',
	'OL3',
	'');
INSERT INTO SM_SEME
	VALUES (6815745,
	6815747,
	6815757,
	6815745);
INSERT INTO SM_LEVT
	VALUES (6815748,
	6815757,
	6815745);
INSERT INTO SM_SEVT
	VALUES (6815748,
	6815757,
	6815745);
INSERT INTO SM_EVT
	VALUES (6815748,
	6815757,
	6815745,
	4,
	'Continue IC7 Test',
	0,
	'',
	'OL4',
	'');
INSERT INTO SM_CH
	VALUES (6815745,
	6815748,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815745,
	6815748,
	6815757,
	6815745);
INSERT INTO SM_STATE
	VALUES (6815746,
	6815757,
	6815746,
	'Verifying Rel with Instance',
	3,
	0);
INSERT INTO SM_CH
	VALUES (6815746,
	6815745,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815746,
	6815745,
	6815757,
	6815745);
INSERT INTO SM_CH
	VALUES (6815746,
	6815746,
	6815757,
	6815746,
	'');
INSERT INTO SM_SEME
	VALUES (6815746,
	6815746,
	6815757,
	6815746);
INSERT INTO SM_CH
	VALUES (6815746,
	6815747,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815746,
	6815747,
	6815757,
	6815745);
INSERT INTO SM_CH
	VALUES (6815746,
	6815748,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815746,
	6815748,
	6815757,
	6815745);
INSERT INTO SM_STATE
	VALUES (6815747,
	6815757,
	6815745,
	'Finishing IC7 Test',
	4,
	1);
INSERT INTO SM_CH
	VALUES (6815747,
	6815745,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815747,
	6815745,
	6815757,
	6815745);
INSERT INTO SM_CH
	VALUES (6815747,
	6815746,
	6815757,
	6815746,
	'');
INSERT INTO SM_SEME
	VALUES (6815747,
	6815746,
	6815757,
	6815746);
INSERT INTO SM_CH
	VALUES (6815747,
	6815747,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815747,
	6815747,
	6815757,
	6815745);
INSERT INTO SM_CH
	VALUES (6815747,
	6815748,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815747,
	6815748,
	6815757,
	6815745);
INSERT INTO SM_STATE
	VALUES (6815748,
	6815757,
	6815745,
	'Initial State',
	1,
	0);
INSERT INTO SM_EIGN
	VALUES (6815748,
	6815745,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815748,
	6815745,
	6815757,
	6815745);
INSERT INTO SM_SEME
	VALUES (6815748,
	6815746,
	6815757,
	6815746);
INSERT INTO SM_CH
	VALUES (6815748,
	6815747,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815748,
	6815747,
	6815757,
	6815745);
INSERT INTO SM_SEME
	VALUES (6815748,
	6815748,
	6815757,
	6815745);
INSERT INTO SM_NSTXN
	VALUES (6815745,
	6815757,
	6815745,
	6815747,
	6815745);
INSERT INTO SM_TXN
	VALUES (6815745,
	6815757,
	6815747,
	6815745);
INSERT INTO SM_NSTXN
	VALUES (6815746,
	6815757,
	6815748,
	6815748,
	6815745);
INSERT INTO SM_TXN
	VALUES (6815746,
	6815757,
	6815745,
	6815745);
INSERT INTO SM_NSTXN
	VALUES (6815747,
	6815757,
	6815748,
	6815746,
	6815746);
INSERT INTO SM_TXN
	VALUES (6815747,
	6815757,
	6815746,
	6815746);
INSERT INTO SM_CRTXN
	VALUES (6815748,
	6815757,
	6815745,
	6815745);
INSERT INTO SM_TXN
	VALUES (6815748,
	6815757,
	6815748,
	6815745);
INSERT INTO SM_MOAH
	VALUES (6815745,
	6815757,
	6815745);
INSERT INTO SM_AH
	VALUES (6815745,
	6815757);
INSERT INTO SM_ACT
	VALUES (6815745,
	6815757,
	1,
	'// select one one_inst related by self-><rel_obj>[REL]
select one om related by self->OM[R6];
if (om.i == 42)
  LOG::LogSuccess(message:"IC7: OL - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IC7: OL - select one inst related by   self-><rel_obj>[REL]");
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select one ol related by self->OM[R6]->OL[R6];
if (ol.i == self.i)
  LOG::LogSuccess(message:"IC7: OL - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]");
else 
  LOG::LogFailure(message:"IC7: OL - select one inst related by   self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OM2:''Verify Rel with Instance''(id:self.ol_id) to om;',
	'');
INSERT INTO SM_MOAH
	VALUES (6815746,
	6815757,
	6815746);
INSERT INTO SM_AH
	VALUES (6815746,
	6815757);
INSERT INTO SM_ACT
	VALUES (6815746,
	6815757,
	1,
	'select one om related by self->OM[R6];
if (om.om_id == rcvd_evt.id)
  LOG::LogSuccess(message:"IC7: OL - Verifying Rel with Instance") ;
  generate OM3:''Finish IC7 Test''() to om;
else 
  LOG::LogFailure(message:"IC7: OL - Verifying Rel with Instance") ;
end if;',
	'');
INSERT INTO SM_MOAH
	VALUES (6815747,
	6815757,
	6815747);
INSERT INTO SM_AH
	VALUES (6815747,
	6815757);
INSERT INTO SM_ACT
	VALUES (6815747,
	6815757,
	1,
	'//check unrelate and delete
select one om related by self->OM[R6];
unrelate self from om across R6;
delete object instance om;
select one om related by self->OM[R6];
if (empty om)
  LOG::LogSuccess(message:"IC7: OL - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IC7: OL - delete object instance <rel_inst>") ;
end if;

//check unrelate
if (true)
  create object instance om1 of OM;
  relate self to om1 across R6;
  assign om = om1;
end if;
unrelate self from om across R6;
select one om2 related by self->OM[R6];
if (empty om2)
  LOG::LogSuccess(message:"IC7: OL - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IC7: OL - unrelate object instance <rel_inst>") ;
end if;
delete object instance om;

select any driver from instances of ICD;
generate ICD14:''IC7 Test L Complete''() to driver;',
	'');
INSERT INTO SM_MOAH
	VALUES (6815748,
	6815757,
	6815748);
INSERT INTO SM_AH
	VALUES (6815748,
	6815757);
INSERT INTO SM_ACT
	VALUES (6815748,
	6815757,
	1,
	'create object instance inst1 of OM;
relate self to inst1 across R6;
assign inst1.i = 42;
assign self.i = 42;

generate OL4:''Continue IC7 Test''() to self;
',
	'');
INSERT INTO GD_MD
	VALUES (6815745,
	8,
	6815757,
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
	VALUES (6815746,
	6815745,
	6815745,
	41);
INSERT INTO GD_SHP
	VALUES (6815746,
	1680,
	1280,
	2032,
	1568);
INSERT INTO GD_GE
	VALUES (6815747,
	6815745,
	6815746,
	41);
INSERT INTO GD_SHP
	VALUES (6815747,
	2064,
	1280,
	2384,
	1568);
INSERT INTO GD_GE
	VALUES (6815748,
	6815745,
	6815747,
	41);
INSERT INTO GD_SHP
	VALUES (6815748,
	1680,
	1616,
	2064,
	1808);
INSERT INTO GD_GE
	VALUES (6815749,
	6815745,
	6815748,
	41);
INSERT INTO GD_SHP
	VALUES (6815749,
	1680,
	1088,
	2032,
	1200);
INSERT INTO GD_GE
	VALUES (6815750,
	6815745,
	6815745,
	42);
INSERT INTO GD_CON
	VALUES (6815750,
	6815746,
	6815748,
	0);
INSERT INTO GD_CTXT
	VALUES (6815750,
	0,
	0,
	0,
	0,
	0,
	0,
	1824,
	1576,
	1979,
	1613,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815751,
	6815750,
	1840,
	1568,
	1840,
	1616,
	0);
INSERT INTO GD_GE
	VALUES (6815752,
	6815745,
	6815746,
	42);
INSERT INTO GD_CON
	VALUES (6815752,
	6815749,
	6815746,
	0);
INSERT INTO GD_CTXT
	VALUES (6815752,
	0,
	0,
	0,
	0,
	0,
	0,
	1808,
	1232,
	1996,
	1265,
	0,
	7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815753,
	6815752,
	1824,
	1200,
	1824,
	1280,
	0);
INSERT INTO GD_GE
	VALUES (6815754,
	6815745,
	6815747,
	42);
INSERT INTO GD_CON
	VALUES (6815754,
	6815749,
	6815747,
	0);
INSERT INTO GD_CTXT
	VALUES (6815754,
	0,
	0,
	0,
	0,
	0,
	0,
	2056,
	1112,
	2242,
	1155,
	-72,
	-33,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815755,
	6815754,
	2032,
	1152,
	2144,
	1152,
	0);
INSERT INTO GD_LS
	VALUES (6815756,
	6815754,
	2144,
	1152,
	2144,
	1280,
	6815755);
INSERT INTO GD_GE
	VALUES (6815757,
	6815745,
	6815748,
	42);
INSERT INTO GD_CON
	VALUES (6815757,
	6815749,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (6815757,
	0,
	0,
	0,
	0,
	0,
	0,
	1853,
	1023,
	2025,
	1057,
	13,
	6,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815758,
	6815757,
	1856,
	1088,
	1856,
	976,
	0);
INSERT INTO O_OBJ
	VALUES (8388622,
	'Object M 1R Side',
	18,
	'OM',
	'',
	8388624);
INSERT INTO O_REF
	VALUES (8388622,
	8388621,
	0,
	8388649,
	8388612,
	8388617,
	8388616,
	8388652,
	8388614,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (8388652,
	8388622,
	8388649,
	8388621,
	0);
INSERT INTO O_ATTR
	VALUES (8388652,
	8388622,
	0,
	'om_id',
	'',
	'',
	'om_id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (8388653,
	8388622);
INSERT INTO O_BATTR
	VALUES (8388653,
	8388622);
INSERT INTO O_ATTR
	VALUES (8388653,
	8388622,
	8388652,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (8388654,
	8388622);
INSERT INTO O_BATTR
	VALUES (8388654,
	8388622);
INSERT INTO O_ATTR
	VALUES (8388654,
	8388622,
	8388653,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	8388622);
INSERT INTO O_OIDA
	VALUES (8388652,
	8388622,
	0);
INSERT INTO SM_ISM
	VALUES (7340046,
	8388622);
INSERT INTO SM_SM
	VALUES (7340046,
	'',
	14);
INSERT INTO SM_MOORE
	VALUES (7340046);
INSERT INTO SM_EVTDI
	VALUES (7340033,
	7340046,
	'id',
	'',
	524294);
INSERT INTO SM_SUPDT
	VALUES (7340033,
	7340046,
	0);
INSERT INTO SM_SUPDT
	VALUES (7340034,
	7340046,
	0);
INSERT INTO SM_SDI
	VALUES (7340033,
	7340034,
	7340046);
INSERT INTO SM_STATE
	VALUES (7340033,
	7340046,
	7340033,
	'Starting IC7 Test',
	2,
	0);
INSERT INTO SM_LEVT
	VALUES (7340033,
	7340046,
	7340033);
INSERT INTO SM_SEVT
	VALUES (7340033,
	7340046,
	7340033);
INSERT INTO SM_EVT
	VALUES (7340033,
	7340046,
	7340033,
	1,
	'Start IC7 Test',
	0,
	'',
	'OM1',
	'');
INSERT INTO SM_EIGN
	VALUES (7340033,
	7340033,
	7340046,
	7340033,
	'');
INSERT INTO SM_SEME
	VALUES (7340033,
	7340033,
	7340046,
	7340033);
INSERT INTO SM_LEVT
	VALUES (7340034,
	7340046,
	7340034);
INSERT INTO SM_SEVT
	VALUES (7340034,
	7340046,
	7340034);
INSERT INTO SM_EVT
	VALUES (7340034,
	7340046,
	7340034,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OM2',
	'');
INSERT INTO SM_EIGN
	VALUES (7340033,
	7340034,
	7340046,
	7340034,
	'');
INSERT INTO SM_SEME
	VALUES (7340033,
	7340034,
	7340046,
	7340034);
INSERT INTO SM_LEVT
	VALUES (7340035,
	7340046,
	7340033);
INSERT INTO SM_SEVT
	VALUES (7340035,
	7340046,
	7340033);
INSERT INTO SM_EVT
	VALUES (7340035,
	7340046,
	7340033,
	3,
	'Finish IC7 Test',
	0,
	'',
	'OM3',
	'');
INSERT INTO SM_SEME
	VALUES (7340033,
	7340035,
	7340046,
	7340033);
INSERT INTO SM_LEVT
	VALUES (7340036,
	7340046,
	7340033);
INSERT INTO SM_SEVT
	VALUES (7340036,
	7340046,
	7340033);
INSERT INTO SM_EVT
	VALUES (7340036,
	7340046,
	7340033,
	4,
	'Continue IC7 Test',
	0,
	'',
	'OM4',
	'');
INSERT INTO SM_CH
	VALUES (7340033,
	7340036,
	7340046,
	7340033,
	'');
INSERT INTO SM_SEME
	VALUES (7340033,
	7340036,
	7340046,
	7340033);
INSERT INTO SM_STATE
	VALUES (7340034,
	7340046,
	7340034,
	'Verifying Rel with Instance',
	3,
	0);
INSERT INTO SM_CH
	VALUES (7340034,
	7340033,
	7340046,
	7340033,
	'');
INSERT INTO SM_SEME
	VALUES (7340034,
	7340033,
	7340046,
	7340033);
INSERT INTO SM_CH
	VALUES (7340034,
	7340034,
	7340046,
	7340034,
	'');
INSERT INTO SM_SEME
	VALUES (7340034,
	7340034,
	7340046,
	7340034);
INSERT INTO SM_CH
	VALUES (7340034,
	7340035,
	7340046,
	7340033,
	'');
INSERT INTO SM_SEME
	VALUES (7340034,
	7340035,
	7340046,
	7340033);
INSERT INTO SM_CH
	VALUES (7340034,
	7340036,
	7340046,
	7340033,
	'');
INSERT INTO SM_SEME
	VALUES (7340034,
	7340036,
	7340046,
	7340033);
INSERT INTO SM_STATE
	VALUES (7340035,
	7340046,
	7340033,
	'Finishing IC7 Test',
	4,
	1);
INSERT INTO SM_CH
	VALUES (7340035,
	7340033,
	7340046,
	7340033,
	'');
INSERT INTO SM_SEME
	VALUES (7340035,
	7340033,
	7340046,
	7340033);
INSERT INTO SM_CH
	VALUES (7340035,
	7340034,
	7340046,
	7340034,
	'');
INSERT INTO SM_SEME
	VALUES (7340035,
	7340034,
	7340046,
	7340034);
INSERT INTO SM_CH
	VALUES (7340035,
	7340035,
	7340046,
	7340033,
	'');
INSERT INTO SM_SEME
	VALUES (7340035,
	7340035,
	7340046,
	7340033);
INSERT INTO SM_CH
	VALUES (7340035,
	7340036,
	7340046,
	7340033,
	'');
INSERT INTO SM_SEME
	VALUES (7340035,
	7340036,
	7340046,
	7340033);
INSERT INTO SM_STATE
	VALUES (7340036,
	7340046,
	7340033,
	'Initial State',
	1,
	0);
INSERT INTO SM_EIGN
	VALUES (7340036,
	7340033,
	7340046,
	7340033,
	'');
INSERT INTO SM_SEME
	VALUES (7340036,
	7340033,
	7340046,
	7340033);
INSERT INTO SM_SEME
	VALUES (7340036,
	7340034,
	7340046,
	7340034);
INSERT INTO SM_CH
	VALUES (7340036,
	7340035,
	7340046,
	7340033,
	'');
INSERT INTO SM_SEME
	VALUES (7340036,
	7340035,
	7340046,
	7340033);
INSERT INTO SM_SEME
	VALUES (7340036,
	7340036,
	7340046,
	7340033);
INSERT INTO SM_NSTXN
	VALUES (7340033,
	7340046,
	7340033,
	7340035,
	7340033);
INSERT INTO SM_TXN
	VALUES (7340033,
	7340046,
	7340035,
	7340033);
INSERT INTO SM_NSTXN
	VALUES (7340034,
	7340046,
	7340036,
	7340034,
	7340034);
INSERT INTO SM_TXN
	VALUES (7340034,
	7340046,
	7340034,
	7340034);
INSERT INTO SM_NSTXN
	VALUES (7340035,
	7340046,
	7340036,
	7340036,
	7340033);
INSERT INTO SM_TXN
	VALUES (7340035,
	7340046,
	7340033,
	7340033);
INSERT INTO SM_CRTXN
	VALUES (7340036,
	7340046,
	7340033,
	7340033);
INSERT INTO SM_TXN
	VALUES (7340036,
	7340046,
	7340036,
	7340033);
INSERT INTO SM_MOAH
	VALUES (7340033,
	7340046,
	7340033);
INSERT INTO SM_AH
	VALUES (7340033,
	7340046);
INSERT INTO SM_ACT
	VALUES (7340033,
	7340046,
	1,
	'// select one one_inst related by self-><rel_obj>[REL]
select one ol related by self->OL[R6];
if (ol.i == 32)
  LOG::LogSuccess(message:"IC7: OM - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IC7: OM - select one inst related by   self-><rel_obj>[REL]");
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select one om related by self->OL[R6]->OM[R6];
if (om.i == self.i)
  LOG::LogSuccess(message:"IC7: OM - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IC7: OM - select one inst related by   self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OL2:''Verify Rel with Instance''(id:self.om_id) to ol;',
	'');
INSERT INTO SM_MOAH
	VALUES (7340034,
	7340046,
	7340034);
INSERT INTO SM_AH
	VALUES (7340034,
	7340046);
INSERT INTO SM_ACT
	VALUES (7340034,
	7340046,
	1,
	'select one ol related by self->OL[R6];
if (ol.ol_id == rcvd_evt.id)
  LOG::LogSuccess(message:"IC7: OM - Verifying Rel with Instance") ;
  generate OL3:''Finish IC7 Test''() to ol;
else 
  LOG::LogFailure(message:"IC7: OM - Verifying Rel with Instance") ;
end if;',
	'');
INSERT INTO SM_MOAH
	VALUES (7340035,
	7340046,
	7340035);
INSERT INTO SM_AH
	VALUES (7340035,
	7340046);
INSERT INTO SM_ACT
	VALUES (7340035,
	7340046,
	1,
	'// check unrelate and delete
select one ol related by self->OL[R6];
unrelate ol from self across R6;
delete object instance ol;
select one ol related by self->OL[R6];
if (empty ol)
  LOG::LogSuccess(message:"IC7: OM - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IC7: OM - delete object instance <rel_inst>") ;
end if;

//check unrelate
create object instance ol1 of OL;
relate self to ol1 across R6;
unrelate self from ol1 across R6;
select one ol2 related by self->OL[R6];
if (empty ol2)
  LOG::LogSuccess(message:"IC7: OM - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IC7: OM - unrelate object instance <rel_inst>") ;
end if;
delete object instance ol1;

// send complete to driver 
select any driver from instances of ICD;
generate ICD15:''IC7 Test M Complete''() to driver;',
	'');
INSERT INTO SM_MOAH
	VALUES (7340036,
	7340046,
	7340036);
INSERT INTO SM_AH
	VALUES (7340036,
	7340046);
INSERT INTO SM_ACT
	VALUES (7340036,
	7340046,
	1,
	'create object instance inst1 of OL;
relate self to inst1 across R6;
assign inst1.i = 32;
assign self.i = 32;

generate OM4:''Continue IC7 Test''() to self;
',
	'');
INSERT INTO GD_MD
	VALUES (7340033,
	8,
	7340046,
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
	VALUES (7340034,
	7340033,
	7340033,
	41);
INSERT INTO GD_SHP
	VALUES (7340034,
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (7340035,
	7340033,
	7340034,
	41);
INSERT INTO GD_SHP
	VALUES (7340035,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (7340036,
	7340033,
	7340035,
	41);
INSERT INTO GD_SHP
	VALUES (7340036,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (7340037,
	7340033,
	7340036,
	41);
INSERT INTO GD_SHP
	VALUES (7340037,
	1680,
	1104,
	2032,
	1184);
INSERT INTO GD_GE
	VALUES (7340038,
	7340033,
	7340033,
	42);
INSERT INTO GD_CON
	VALUES (7340038,
	7340034,
	7340036,
	0);
INSERT INTO GD_CTXT
	VALUES (7340038,
	0,
	0,
	0,
	0,
	0,
	0,
	1824,
	1632,
	2048,
	1663,
	0,
	7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (7340039,
	7340038,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO GD_GE
	VALUES (7340040,
	7340033,
	7340035,
	42);
INSERT INTO GD_CON
	VALUES (7340040,
	7340037,
	7340034,
	0);
INSERT INTO GD_CTXT
	VALUES (7340040,
	0,
	0,
	0,
	0,
	0,
	0,
	1836,
	1222,
	2006,
	1256,
	12,
	5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (7340041,
	7340040,
	1840,
	1184,
	1840,
	1280,
	0);
INSERT INTO GD_GE
	VALUES (7340042,
	7340033,
	7340034,
	42);
INSERT INTO GD_CON
	VALUES (7340042,
	7340037,
	7340035,
	0);
INSERT INTO GD_CTXT
	VALUES (7340042,
	0,
	0,
	0,
	0,
	0,
	0,
	2112,
	1152,
	2308,
	1202,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (7340043,
	7340042,
	2032,
	1152,
	2128,
	1152,
	0);
INSERT INTO GD_LS
	VALUES (7340044,
	7340042,
	2128,
	1152,
	2128,
	1280,
	7340043);
INSERT INTO GD_GE
	VALUES (7340045,
	7340033,
	7340036,
	42);
INSERT INTO GD_CON
	VALUES (7340045,
	7340037,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (7340045,
	0,
	0,
	0,
	0,
	0,
	0,
	1824,
	1040,
	1997,
	1077,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (7340046,
	7340045,
	1840,
	1104,
	1840,
	1008,
	0);
INSERT INTO O_OBJ
	VALUES (8388623,
	'Passive Object B',
	19,
	'POB',
	'',
	8388624);
INSERT INTO O_NBATTR
	VALUES (8388655,
	8388623);
INSERT INTO O_BATTR
	VALUES (8388655,
	8388623);
INSERT INTO O_ATTR
	VALUES (8388655,
	8388623,
	0,
	'pob_id',
	'',
	'',
	'pob_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (8388656,
	8388623);
INSERT INTO O_BATTR
	VALUES (8388656,
	8388623);
INSERT INTO O_ATTR
	VALUES (8388656,
	8388623,
	8388655,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_REF
	VALUES (8388623,
	8388612,
	0,
	8388616,
	8388614,
	8388621,
	8388620,
	8388657,
	8388615,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (8388657,
	8388623,
	8388616,
	8388612,
	1);
INSERT INTO O_ATTR
	VALUES (8388657,
	8388623,
	8388656,
	'oa_id',
	'',
	'',
	'oa_id',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	8388623);
INSERT INTO O_OIDA
	VALUES (8388655,
	8388623,
	0);
INSERT INTO R_SIMP
	VALUES (8388609);
INSERT INTO R_REL
	VALUES (8388609,
	1,
	'',
	8388624);
INSERT INTO R_PART
	VALUES (8388611,
	8388609,
	8388609,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (8388611,
	8388609,
	8388609,
	0);
INSERT INTO R_OIR
	VALUES (8388611,
	8388609,
	8388609,
	0);
INSERT INTO R_FORM
	VALUES (8388614,
	8388609,
	8388610,
	0,
	0,
	'');
INSERT INTO R_RGO
	VALUES (8388614,
	8388609,
	8388610);
INSERT INTO R_OIR
	VALUES (8388614,
	8388609,
	8388610,
	0);
INSERT INTO R_SIMP
	VALUES (8388610);
INSERT INTO R_REL
	VALUES (8388610,
	2,
	'',
	8388624);
INSERT INTO R_PART
	VALUES (8388615,
	8388610,
	8388611,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (8388615,
	8388610,
	8388611,
	0);
INSERT INTO R_OIR
	VALUES (8388615,
	8388610,
	8388611,
	0);
INSERT INTO R_FORM
	VALUES (8388616,
	8388610,
	8388612,
	1,
	0,
	'');
INSERT INTO R_RGO
	VALUES (8388616,
	8388610,
	8388612);
INSERT INTO R_OIR
	VALUES (8388616,
	8388610,
	8388612,
	0);
INSERT INTO R_ASSOC
	VALUES (8388611);
INSERT INTO R_REL
	VALUES (8388611,
	3,
	'',
	8388624);
INSERT INTO R_AONE
	VALUES (8388618,
	8388611,
	8388613,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (8388618,
	8388611,
	8388613,
	0);
INSERT INTO R_OIR
	VALUES (8388618,
	8388611,
	8388613,
	0);
INSERT INTO R_AOTH
	VALUES (8388619,
	8388611,
	8388614,
	1,
	0,
	'');
INSERT INTO R_RTO
	VALUES (8388619,
	8388611,
	8388614,
	0);
INSERT INTO R_OIR
	VALUES (8388619,
	8388611,
	8388614,
	0);
INSERT INTO R_ASSR
	VALUES (8388620,
	8388611,
	8388615,
	0);
INSERT INTO R_RGO
	VALUES (8388620,
	8388611,
	8388615);
INSERT INTO R_OIR
	VALUES (8388620,
	8388611,
	8388615,
	0);
INSERT INTO R_SIMP
	VALUES (8388612);
INSERT INTO R_REL
	VALUES (8388612,
	6,
	'',
	8388624);
INSERT INTO R_PART
	VALUES (8388621,
	8388612,
	8388616,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (8388621,
	8388612,
	8388616,
	0);
INSERT INTO R_OIR
	VALUES (8388621,
	8388612,
	8388616,
	0);
INSERT INTO R_FORM
	VALUES (8388622,
	8388612,
	8388617,
	0,
	0,
	'');
INSERT INTO R_RGO
	VALUES (8388622,
	8388612,
	8388617);
INSERT INTO R_OIR
	VALUES (8388622,
	8388612,
	8388617,
	0);
INSERT INTO R_SIMP
	VALUES (8388613);
INSERT INTO R_REL
	VALUES (8388613,
	7,
	'',
	8388624);
INSERT INTO R_FORM
	VALUES (8388613,
	8388613,
	8388618,
	1,
	0,
	'');
INSERT INTO R_RGO
	VALUES (8388613,
	8388613,
	8388618);
INSERT INTO R_OIR
	VALUES (8388613,
	8388613,
	8388618,
	0);
INSERT INTO R_PART
	VALUES (8388612,
	8388613,
	8388619,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (8388612,
	8388613,
	8388619,
	0);
INSERT INTO R_OIR
	VALUES (8388612,
	8388613,
	8388619,
	0);
INSERT INTO R_SIMP
	VALUES (8388614);
INSERT INTO R_REL
	VALUES (8388614,
	8,
	'',
	8388624);
INSERT INTO R_PART
	VALUES (8388612,
	8388614,
	8388620,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (8388612,
	8388614,
	8388620,
	0);
INSERT INTO R_OIR
	VALUES (8388612,
	8388614,
	8388620,
	0);
INSERT INTO R_FORM
	VALUES (8388623,
	8388614,
	8388621,
	0,
	0,
	'');
INSERT INTO R_RGO
	VALUES (8388623,
	8388614,
	8388621);
INSERT INTO R_OIR
	VALUES (8388623,
	8388614,
	8388621,
	0);
INSERT INTO GD_MD
	VALUES (8388621,
	5,
	8388624,
	11,
	1,
	0,
	1,
	1,
	0,
	12,
	1354,
	3951,
	0.524151,
	0);
INSERT INTO GD_GE
	VALUES (8388624,
	8388621,
	8388609,
	21);
INSERT INTO GD_SHP
	VALUES (8388624,
	1696,
	1104,
	1888,
	1232);
INSERT INTO GD_GE
	VALUES (8388626,
	8388621,
	8388611,
	21);
INSERT INTO GD_SHP
	VALUES (8388626,
	1696,
	1456,
	1888,
	1584);
INSERT INTO GD_GE
	VALUES (8388627,
	8388621,
	8388612,
	21);
INSERT INTO GD_SHP
	VALUES (8388627,
	1696,
	1280,
	1888,
	1408);
INSERT INTO GD_GE
	VALUES (8388628,
	8388621,
	8388613,
	21);
INSERT INTO GD_SHP
	VALUES (8388628,
	1376,
	1280,
	1552,
	1408);
INSERT INTO GD_GE
	VALUES (8388629,
	8388621,
	8388614,
	21);
INSERT INTO GD_SHP
	VALUES (8388629,
	2048,
	1456,
	2240,
	1584);
INSERT INTO GD_GE
	VALUES (8388630,
	8388621,
	8388615,
	21);
INSERT INTO GD_SHP
	VALUES (8388630,
	1696,
	1664,
	1888,
	1792);
INSERT INTO GD_GE
	VALUES (8388631,
	8388621,
	8388616,
	21);
INSERT INTO GD_SHP
	VALUES (8388631,
	2048,
	1664,
	2240,
	1792);
INSERT INTO GD_GE
	VALUES (8388632,
	8388621,
	8388617,
	21);
INSERT INTO GD_SHP
	VALUES (8388632,
	1984,
	1280,
	2176,
	1408);
INSERT INTO GD_GE
	VALUES (8388633,
	8388621,
	8388618,
	21);
INSERT INTO GD_SHP
	VALUES (8388633,
	2304,
	1280,
	2496,
	1392);
INSERT INTO GD_GE
	VALUES (8388634,
	8388621,
	8388619,
	21);
INSERT INTO GD_SHP
	VALUES (8388634,
	2656,
	1280,
	2848,
	1392);
INSERT INTO GD_GE
	VALUES (8388635,
	8388621,
	8388620,
	21);
INSERT INTO GD_SHP
	VALUES (8388635,
	2480,
	1456,
	2672,
	1568);
INSERT INTO GD_GE
	VALUES (8388636,
	8388621,
	8388621,
	21);
INSERT INTO GD_SHP
	VALUES (8388636,
	2320,
	1648,
	2512,
	1792);
INSERT INTO GD_GE
	VALUES (8388637,
	8388621,
	8388622,
	21);
INSERT INTO GD_SHP
	VALUES (8388637,
	2672,
	1648,
	2864,
	1792);
INSERT INTO GD_GE
	VALUES (8388638,
	8388621,
	8388623,
	21);
INSERT INTO GD_SHP
	VALUES (8388638,
	1376,
	1456,
	1552,
	1584);
INSERT INTO GD_GE
	VALUES (8388683,
	8388621,
	8388609,
	24);
INSERT INTO GD_CON
	VALUES (8388683,
	8388626,
	8388629,
	0);
INSERT INTO GD_CTXT
	VALUES (8388683,
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
	VALUES (8388684,
	8388683,
	1888,
	1520,
	2048,
	1520,
	0);
INSERT INTO GD_GE
	VALUES (8388685,
	8388621,
	8388610,
	24);
INSERT INTO GD_CON
	VALUES (8388685,
	8388630,
	8388631,
	0);
INSERT INTO GD_CTXT
	VALUES (8388685,
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
	VALUES (8388686,
	8388685,
	1888,
	1728,
	2048,
	1728,
	0);
INSERT INTO GD_GE
	VALUES (8388687,
	8388621,
	8388611,
	24);
INSERT INTO GD_CON
	VALUES (8388687,
	8388633,
	8388634,
	0);
INSERT INTO GD_CTXT
	VALUES (8388687,
	0,
	0,
	0,
	0,
	0,
	0,
	2544,
	1296,
	2594,
	1326,
	-7,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (8388688,
	8388687,
	2496,
	1328,
	2656,
	1328,
	0);
INSERT INTO GD_GE
	VALUES (8388689,
	8388621,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (8388689,
	8388635,
	8388687,
	0);
INSERT INTO GD_CTXT
	VALUES (8388689,
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
	VALUES (8388690,
	8388689,
	2576,
	1456,
	2576,
	1328,
	0);
INSERT INTO GD_GE
	VALUES (8388691,
	8388621,
	8388612,
	24);
INSERT INTO GD_CON
	VALUES (8388691,
	8388636,
	8388637,
	0);
INSERT INTO GD_CTXT
	VALUES (8388691,
	0,
	0,
	0,
	0,
	0,
	0,
	2560,
	1696,
	2610,
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
	VALUES (8388692,
	8388691,
	2512,
	1728,
	2672,
	1728,
	0);
INSERT INTO GD_GE
	VALUES (8388693,
	8388621,
	8388613,
	24);
INSERT INTO GD_CON
	VALUES (8388693,
	8388628,
	8388627,
	0);
INSERT INTO GD_CTXT
	VALUES (8388693,
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
	VALUES (8388694,
	8388693,
	1552,
	1312,
	1696,
	1312,
	0);
INSERT INTO GD_GE
	VALUES (8388695,
	8388621,
	8388614,
	24);
INSERT INTO GD_CON
	VALUES (8388695,
	8388627,
	8388638,
	0);
INSERT INTO GD_CTXT
	VALUES (8388695,
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
	VALUES (8388696,
	8388695,
	1696,
	1408,
	1552,
	1472,
	0);
INSERT INTO GD_MD
	VALUES (8388622,
	6,
	8388624,
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
	VALUES (8388639,
	8388622,
	8388609,
	21);
INSERT INTO GD_SHP
	VALUES (8388639,
	1696,
	1104,
	1888,
	1168);
INSERT INTO GD_GE
	VALUES (8388641,
	8388622,
	8388612,
	21);
INSERT INTO GD_SHP
	VALUES (8388641,
	1696,
	1280,
	1888,
	1344);
INSERT INTO GD_GE
	VALUES (8388642,
	8388622,
	8388611,
	21);
INSERT INTO GD_SHP
	VALUES (8388642,
	1696,
	1456,
	1888,
	1520);
INSERT INTO GD_GE
	VALUES (8388643,
	8388622,
	8388615,
	21);
INSERT INTO GD_SHP
	VALUES (8388643,
	1696,
	1664,
	1888,
	1728);
INSERT INTO GD_GE
	VALUES (8388644,
	8388622,
	8388616,
	21);
INSERT INTO GD_SHP
	VALUES (8388644,
	2048,
	1664,
	2240,
	1728);
INSERT INTO GD_GE
	VALUES (8388645,
	8388622,
	8388614,
	21);
INSERT INTO GD_SHP
	VALUES (8388645,
	2048,
	1456,
	2240,
	1520);
INSERT INTO GD_GE
	VALUES (8388646,
	8388622,
	8388617,
	21);
INSERT INTO GD_SHP
	VALUES (8388646,
	1984,
	1280,
	2176,
	1344);
INSERT INTO GD_GE
	VALUES (8388647,
	8388622,
	8388618,
	21);
INSERT INTO GD_SHP
	VALUES (8388647,
	1696,
	1856,
	1888,
	1920);
INSERT INTO GD_GE
	VALUES (8388648,
	8388622,
	8388619,
	21);
INSERT INTO GD_SHP
	VALUES (8388648,
	2048,
	1856,
	2240,
	1920);
INSERT INTO GD_GE
	VALUES (8388649,
	8388622,
	8388620,
	21);
INSERT INTO GD_SHP
	VALUES (8388649,
	1872,
	2032,
	2064,
	2096);
INSERT INTO GD_GE
	VALUES (8388650,
	8388622,
	8388621,
	21);
INSERT INTO GD_SHP
	VALUES (8388650,
	2368,
	1936,
	2560,
	2000);
INSERT INTO GD_GE
	VALUES (8388651,
	8388622,
	8388622,
	21);
INSERT INTO GD_SHP
	VALUES (8388651,
	2720,
	1936,
	2912,
	2000);
INSERT INTO GD_MD
	VALUES (8388623,
	7,
	8388624,
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
	VALUES (8388652,
	8388623,
	8388609,
	21);
INSERT INTO GD_SHP
	VALUES (8388652,
	1696,
	1104,
	1888,
	1168);
INSERT INTO GD_GE
	VALUES (8388654,
	8388623,
	8388611,
	21);
INSERT INTO GD_SHP
	VALUES (8388654,
	1680,
	1392,
	1872,
	1456);
INSERT INTO GD_GE
	VALUES (8388655,
	8388623,
	8388612,
	21);
INSERT INTO GD_SHP
	VALUES (8388655,
	1696,
	1296,
	1888,
	1360);
INSERT INTO GD_GE
	VALUES (8388656,
	8388623,
	8388613,
	21);
INSERT INTO GD_SHP
	VALUES (8388656,
	2208,
	1104,
	2400,
	1168);
INSERT INTO GD_GE
	VALUES (8388657,
	8388623,
	8388614,
	21);
INSERT INTO GD_SHP
	VALUES (8388657,
	1984,
	1456,
	2176,
	1520);
INSERT INTO GD_GE
	VALUES (8388658,
	8388623,
	8388615,
	21);
INSERT INTO GD_SHP
	VALUES (8388658,
	1696,
	1664,
	1888,
	1728);
INSERT INTO GD_GE
	VALUES (8388659,
	8388623,
	8388616,
	21);
INSERT INTO GD_SHP
	VALUES (8388659,
	2048,
	1664,
	2240,
	1728);
INSERT INTO GD_GE
	VALUES (8388660,
	8388623,
	8388617,
	21);
INSERT INTO GD_SHP
	VALUES (8388660,
	1984,
	1280,
	2176,
	1344);
INSERT INTO GD_GE
	VALUES (8388661,
	8388623,
	8388618,
	21);
INSERT INTO GD_SHP
	VALUES (8388661,
	1696,
	1856,
	1888,
	1920);
INSERT INTO GD_GE
	VALUES (8388662,
	8388623,
	8388619,
	21);
INSERT INTO GD_SHP
	VALUES (8388662,
	2048,
	1856,
	2240,
	1920);
INSERT INTO GD_GE
	VALUES (8388663,
	8388623,
	8388620,
	21);
INSERT INTO GD_SHP
	VALUES (8388663,
	1872,
	2032,
	2064,
	2096);
INSERT INTO GD_GE
	VALUES (8388664,
	8388623,
	8388621,
	21);
INSERT INTO GD_SHP
	VALUES (8388664,
	2368,
	1936,
	2560,
	2000);
INSERT INTO GD_GE
	VALUES (8388665,
	8388623,
	8388622,
	21);
INSERT INTO GD_SHP
	VALUES (8388665,
	2720,
	1936,
	2912,
	2000);
INSERT INTO GD_GE
	VALUES (8388666,
	8388623,
	8388623,
	21);
INSERT INTO GD_SHP
	VALUES (8388666,
	1376,
	1440,
	1568,
	1504);
INSERT INTO GD_GE
	VALUES (8388667,
	8388623,
	8388762,
	1005);
INSERT INTO GD_CON
	VALUES (8388667,
	8388654,
	8388657,
	0);
INSERT INTO GD_CTXT
	VALUES (8388667,
	0,
	0,
	0,
	0,
	0,
	0,
	1916,
	1406,
	2103,
	1442,
	9,
	-19,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (8388668,
	8388667,
	1872,
	1416,
	1984,
	1480,
	0);
INSERT INTO GD_GE
	VALUES (8388669,
	8388623,
	8388764,
	1005);
INSERT INTO GD_CON
	VALUES (8388669,
	8388657,
	8388654,
	0);
INSERT INTO GD_CTXT
	VALUES (8388669,
	0,
	0,
	0,
	0,
	0,
	0,
	1799,
	1474,
	1932,
	1551,
	-108,
	33,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (8388670,
	8388669,
	1984,
	1496,
	1872,
	1432,
	0);
INSERT INTO GD_GE
	VALUES (8388671,
	8388623,
	8388770,
	1005);
INSERT INTO GD_CON
	VALUES (8388671,
	8388661,
	8388662,
	0);
INSERT INTO GD_CTXT
	VALUES (8388671,
	0,
	0,
	0,
	0,
	0,
	0,
	1904,
	1784,
	2007,
	1866,
	-39,
	-71,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (8388672,
	8388671,
	1888,
	1880,
	2048,
	1880,
	0);
INSERT INTO GD_GE
	VALUES (8388673,
	8388623,
	8388772,
	1005);
INSERT INTO GD_CON
	VALUES (8388673,
	8388662,
	8388661,
	0);
INSERT INTO GD_CTXT
	VALUES (8388673,
	0,
	0,
	0,
	0,
	0,
	0,
	1930,
	1886,
	1990,
	1959,
	-13,
	15,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (8388674,
	8388673,
	2048,
	1896,
	1888,
	1896,
	0);
INSERT INTO GD_GE
	VALUES (8388675,
	8388623,
	8388774,
	1005);
INSERT INTO GD_CON
	VALUES (8388675,
	8388663,
	8388661,
	0);
INSERT INTO GD_CTXT
	VALUES (8388675,
	0,
	0,
	0,
	0,
	0,
	0,
	1864,
	1952,
	1961,
	1994,
	24,
	-5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (8388676,
	8388675,
	1896,
	2032,
	1816,
	1920,
	0);
