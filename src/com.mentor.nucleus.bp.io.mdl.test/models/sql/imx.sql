-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (3679133,
	'imx',
	'This test deals with creating, relating, and selecting instances using navigation of the form:

select any(many, one) <instance> related by <instance ref>-><relationship 1>->...-><relationship n>

It also checks deletion and unrelate work correctly.  It should not be possible to select an instance from the set if it has been deleted.  It should not be possible to select an instance across a relationship that has been unrelated.

The test performs these verifications with each data type.',
	0,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	3679133,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	3679133,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	3679133,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	3679133,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	3679133,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	3679133,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	3679133,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	3679133,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	3679133,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	3679133,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	3679133,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	3679133,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	3679133,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	3679133,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	3679133,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	3679133,
	'inst_ref<Timer>',
	'');
INSERT INTO S_UDT
	VALUES (524305,
	524293,
	0);
INSERT INTO S_DT
	VALUES (524305,
	3679133,
	'color',
	'Enumeration: TRUE
Enumerator0: Red
Enumerator1: Blue
Enumerator2: Green
Enumerator3: Black
Enumerator4: Gray
Enumerator5: Teal
');
INSERT INTO S_UDT
	VALUES (524306,
	524293,
	0);
INSERT INTO S_DT
	VALUES (524306,
	3679133,
	'my_string',
	'');
INSERT INTO S_EE
	VALUES (524290,
	'Logging ',
	'',
	'LOG',
	3679133);
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
	3679133);
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
	VALUES (524292,
	'Architecture',
	'',
	'ARCH',
	3679133);
INSERT INTO S_BRG
	VALUES (524322,
	524292,
	'shutdown',
	'',
	0,
	524289,
	'control stop;',
	1);
INSERT INTO GD_MD
	VALUES (524289,
	1,
	3679133,
	1,
	1,
	0,
	1,
	1,
	0,
	12,
	1599,
	4061,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (524301,
	524289,
	524290,
	12);
INSERT INTO GD_SHP
	VALUES (524301,
	2096,
	1632,
	2256,
	1728);
INSERT INTO GD_GE
	VALUES (524359,
	524289,
	10485780,
	11);
INSERT INTO GD_SHP
	VALUES (524359,
	1920,
	1520,
	2080,
	1616);
INSERT INTO GD_GE
	VALUES (524360,
	524289,
	524291,
	12);
INSERT INTO GD_SHP
	VALUES (524360,
	1920,
	1632,
	2080,
	1728);
INSERT INTO GD_GE
	VALUES (524362,
	524289,
	524292,
	12);
INSERT INTO GD_SHP
	VALUES (524362,
	1744,
	1632,
	1904,
	1728);
INSERT INTO GD_MD
	VALUES (524290,
	2,
	3679133,
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
	VALUES (524363,
	524290,
	10485780,
	11);
INSERT INTO GD_SHP
	VALUES (524363,
	1920,
	1344,
	2080,
	1440);
INSERT INTO GD_MD
	VALUES (524291,
	3,
	3679133,
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
	VALUES (524365,
	524291,
	10485780,
	11);
INSERT INTO GD_SHP
	VALUES (524365,
	1920,
	1344,
	2080,
	1440);
INSERT INTO GD_MD
	VALUES (524292,
	4,
	3679133,
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
	VALUES (524367,
	524292,
	10485780,
	11);
INSERT INTO GD_SHP
	VALUES (524367,
	1920,
	1344,
	2080,
	1440);
INSERT INTO S_SS
	VALUES (10485780,
	'imx',
	'',
	'',
	1,
	3679133,
	10485780);
INSERT INTO O_OBJ
	VALUES (10485761,
	'init',
	1,
	'INIT',
	'',
	10485780);
INSERT INTO O_NBATTR
	VALUES (10485761,
	10485761);
INSERT INTO O_BATTR
	VALUES (10485761,
	10485761);
INSERT INTO O_ATTR
	VALUES (10485761,
	10485761,
	0,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_NBATTR
	VALUES (10485762,
	10485761);
INSERT INTO O_BATTR
	VALUES (10485762,
	10485761);
INSERT INTO O_ATTR
	VALUES (10485762,
	10485761,
	10485761,
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_ID
	VALUES (0,
	10485761);
INSERT INTO O_OIDA
	VALUES (10485762,
	10485761,
	0);
INSERT INTO SM_ISM
	VALUES (1048578,
	10485761);
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
	'IMX Init',
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
	'generate IMX_DRV1:''Start IXC2 Test''() to IMX_DRV creator;
',
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
	1296,
	2240,
	1488);
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
	1711,
	1221,
	1847,
	1258,
	8,
	-12,
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
	1344,
	1728,
	1344,
	0);
INSERT INTO GD_LS
	VALUES (1048581,
	1048579,
	1728,
	1344,
	1728,
	1248,
	1048580);
INSERT INTO GD_LS
	VALUES (1048582,
	1048579,
	1728,
	1248,
	1824,
	1248,
	1048581);
INSERT INTO GD_LS
	VALUES (1048583,
	1048579,
	1824,
	1248,
	1824,
	1296,
	1048582);
INSERT INTO O_OBJ
	VALUES (10485762,
	'Object with Int Id Attr L Side',
	4,
	'OL_INT',
	'',
	10485780);
INSERT INTO O_NBATTR
	VALUES (10485763,
	10485762);
INSERT INTO O_BATTR
	VALUES (10485763,
	10485762);
INSERT INTO O_ATTR
	VALUES (10485763,
	10485762,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (10485764,
	10485762);
INSERT INTO O_BATTR
	VALUES (10485764,
	10485762);
INSERT INTO O_ATTR
	VALUES (10485764,
	10485762,
	10485763,
	'id1',
	'',
	'',
	'id1',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (10485765,
	10485762);
INSERT INTO O_BATTR
	VALUES (10485765,
	10485762);
INSERT INTO O_ATTR
	VALUES (10485765,
	10485762,
	10485764,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_NBATTR
	VALUES (10485766,
	10485762);
INSERT INTO O_BATTR
	VALUES (10485766,
	10485762);
INSERT INTO O_ATTR
	VALUES (10485766,
	10485762,
	10485765,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	10485762);
INSERT INTO O_OIDA
	VALUES (10485764,
	10485762,
	0);
INSERT INTO O_RTIDA
	VALUES (10485764,
	10485762,
	0,
	10485761,
	10485761);
INSERT INTO O_OIDA
	VALUES (10485763,
	10485762,
	0);
INSERT INTO O_RTIDA
	VALUES (10485763,
	10485762,
	0,
	10485761,
	10485761);
INSERT INTO SM_ISM
	VALUES (1572867,
	10485762);
INSERT INTO SM_SM
	VALUES (1572867,
	'',
	3);
INSERT INTO SM_MOORE
	VALUES (1572867);
INSERT INTO SM_EVTDI
	VALUES (1572865,
	1572867,
	'id',
	'',
	524291);
INSERT INTO SM_SUPDT
	VALUES (1572865,
	1572867,
	0);
INSERT INTO SM_SUPDT
	VALUES (1572866,
	1572867,
	0);
INSERT INTO SM_SDI
	VALUES (1572865,
	1572866,
	1572867);
INSERT INTO SM_STATE
	VALUES (1572865,
	1572867,
	1572865,
	'Starting IX2 Test',
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
	'Start IXC2 Test',
	0,
	'',
	'OL_INT1',
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
	'Verify Rel with Instance',
	0,
	'',
	'OL_INT2',
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
	'Finish IX2 Test',
	0,
	'',
	'OL_INT3',
	'');
INSERT INTO SM_SEME
	VALUES (1572865,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_STATE
	VALUES (1572866,
	1572867,
	1572866,
	'Verifying Rel with Instance',
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
	VALUES (1572866,
	1572867,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572866,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_STATE
	VALUES (1572867,
	1572867,
	1572865,
	'Finishing IX2 Test',
	3,
	0);
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_CH
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
INSERT INTO SM_NSTXN
	VALUES (1572865,
	1572867,
	1572865,
	1572867,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572865,
	1572867,
	1572867,
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
	1572865,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572867,
	1572867,
	1572865,
	1572866,
	1572866);
INSERT INTO SM_TXN
	VALUES (1572867,
	1572867,
	1572866,
	1572866);
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
	'create object instance inst1 of OR_INT;
assign self.id = 1;
assign self.id1=10;
assign inst1.r_id = 2;
relate self to inst1 across R2;
assign inst1.i = 42;
assign self.i = 42;

// select one one_inst related by self-><rel_obj>[REL]
select one oner related by self->OR_INT[R2];
if (oner.i == 42)
  LOG::LogSuccess(message:"IX2: OL_INT - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_INT - select one inst related by self-><rel_obj>[REL]") ;
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select one ol related by self->OR_INT[R2]->OL_INT[R2];
if (ol.i == self.i)
  LOG::LogSuccess(message:"IX2: OL_INT - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_INT - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OR_INT2:''Verify Rel with Instance''(id:self.id) to oner;',
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
	'select one oner related by self->OR_INT[R2];
if (oner.r_id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OL_INT - Verifying Rel with Instance") ;
  generate OR_INT3:''Finish IX2 Test''() to oner;
else 
  LOG::LogFailure(message:"IX2: OL_INT - Verifying Rel with Instance") ;
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
	'// check delete
select one oner related by self->OR_INT[R2];
unrelate oner from self across R2;
delete object instance oner;
select one oner related by self->OR_INT[R2];
if (empty oner)
  LOG::LogSuccess(message:"IX2: OL_INT - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_INT - delete object instance <rel_inst>") ;
end if;

//check unrelate
create object instance or1 of OR_INT;
assign or1.r_id = 200;
relate self to or1 across R2;
unrelate self from or1 across R2;
select one or2 related by self->OR_INT[R2];
if (empty or2)
  LOG::LogSuccess(message:"IX2: OL_INT - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_INT - unrelate object instance <rel_inst>") ;
end if;
delete object instance or1;

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
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (1572867,
	1572865,
	1572866,
	41);
INSERT INTO GD_SHP
	VALUES (1572867,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (1572868,
	1572865,
	1572867,
	41);
INSERT INTO GD_SHP
	VALUES (1572868,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (1572869,
	1572865,
	1572866,
	42);
INSERT INTO GD_CON
	VALUES (1572869,
	1572866,
	1572866,
	0);
INSERT INTO GD_CTXT
	VALUES (1572869,
	0,
	0,
	0,
	0,
	0,
	0,
	1680,
	1200,
	1894,
	1229,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572870,
	1572869,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (1572871,
	1572869,
	1664,
	1312,
	1664,
	1232,
	1572870);
INSERT INTO GD_LS
	VALUES (1572872,
	1572869,
	1664,
	1232,
	1792,
	1232,
	1572871);
INSERT INTO GD_LS
	VALUES (1572873,
	1572869,
	1792,
	1232,
	1792,
	1280,
	1572872);
INSERT INTO GD_GE
	VALUES (1572874,
	1572865,
	1572867,
	42);
INSERT INTO GD_CON
	VALUES (1572874,
	1572866,
	1572867,
	0);
INSERT INTO GD_CTXT
	VALUES (1572874,
	0,
	0,
	0,
	0,
	0,
	0,
	2048,
	1216,
	2252,
	1254,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572875,
	1572874,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (1572876,
	1572874,
	2016,
	1248,
	2128,
	1248,
	1572875);
INSERT INTO GD_LS
	VALUES (1572877,
	1572874,
	2128,
	1248,
	2128,
	1280,
	1572876);
INSERT INTO GD_GE
	VALUES (1572878,
	1572865,
	1572865,
	42);
INSERT INTO GD_CON
	VALUES (1572878,
	1572866,
	1572868,
	0);
INSERT INTO GD_CTXT
	VALUES (1572878,
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
	VALUES (1572879,
	1572878,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (10485763,
	'Object with Int Id Attr R Side',
	5,
	'OR_INT',
	'',
	10485780);
INSERT INTO O_NBATTR
	VALUES (10485767,
	10485763);
INSERT INTO O_BATTR
	VALUES (10485767,
	10485763);
INSERT INTO O_ATTR
	VALUES (10485767,
	10485763,
	0,
	'r_id',
	'',
	'',
	'r_id',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (10485768,
	10485763);
INSERT INTO O_BATTR
	VALUES (10485768,
	10485763);
INSERT INTO O_ATTR
	VALUES (10485768,
	10485763,
	10485767,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_REF
	VALUES (10485763,
	10485762,
	0,
	10485763,
	10485761,
	10485762,
	10485761,
	10485769,
	10485761,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (10485769,
	10485763,
	10485763,
	10485762,
	1);
INSERT INTO O_ATTR
	VALUES (10485769,
	10485763,
	10485768,
	'id',
	'',
	'',
	'id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (10485770,
	10485763);
INSERT INTO O_BATTR
	VALUES (10485770,
	10485763);
INSERT INTO O_ATTR
	VALUES (10485770,
	10485763,
	10485769,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_REF
	VALUES (10485763,
	10485762,
	0,
	10485764,
	10485761,
	10485762,
	10485761,
	10485771,
	10485762,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (10485771,
	10485763,
	10485764,
	10485762,
	1);
INSERT INTO O_ATTR
	VALUES (10485771,
	10485763,
	10485770,
	'id1',
	'',
	'',
	'id1',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	10485763);
INSERT INTO O_OIDA
	VALUES (10485767,
	10485763,
	0);
INSERT INTO SM_ISM
	VALUES (2097156,
	10485763);
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
	524291);
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
	'Starting IX2 Test',
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
	'Start IXC2 Test',
	0,
	'',
	'OR_INT1',
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
	'OR_INT2',
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
	'Finish IX2 Test',
	0,
	'',
	'OR_INT3',
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097155,
	2097156,
	2097153);
INSERT INTO SM_STATE
	VALUES (2097154,
	2097156,
	2097154,
	'Verifying Rel with Instance',
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
INSERT INTO SM_STATE
	VALUES (2097155,
	2097156,
	2097153,
	'Finishing IX2 Test',
	3,
	0);
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
	2097153,
	2097153,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097154,
	2097156,
	2097153,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097155,
	2097156,
	2097153,
	2097154,
	2097154);
INSERT INTO SM_TXN
	VALUES (2097155,
	2097156,
	2097154,
	2097154);
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
	'create object instance inst1 of OL_INT;
assign self.r_id = 3;
assign inst1.id = 4;
assign inst1.id1=44;
relate self to inst1 across R2;
assign inst1.i = 32;
assign self.i = 32;

// select one one_inst related by self-><rel_obj>[REL]
select one ol related by self->OL_INT[R2];
if (ol.i == 32)
  LOG::LogSuccess(message:"IX2: OR_INT - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_INT - select one inst related by   self-><rel_obj>[REL]") ;
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select one oner related by self->OL_INT[R2]->OR_INT[R2];
if (oner.i == self.i)
  LOG::LogSuccess(message:"IX2: OR_INT - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_INT - select one inst related by   self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OL_INT2:''Verify Rel with Instance''(id:self.r_id) to ol;',
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
	'select one ol related by self->OL_INT[R2];
if (ol.id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OR_INT - Verifying Rel with Instance") ;
  generate OL_INT3:''Finish IX2 Test''() to ol;
else 
  LOG::LogFailure(message:"IX2: OR_INT - Verifying Rel with Instance") ;
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
	'// check delete
select one ol related by self->OL_INT[R2];
unrelate ol from self across R2;
delete object instance ol;
select one ol related by self->OL_INT[R2];
if (empty ol)
  LOG::LogSuccess(message:"IX2: OR_INT - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_INT - delete object instance <rel_inst>") ;
end if;

//check unrelate
create object instance ol1 of OL_INT;
assign ol1.id = 100;
relate self to ol1 across R2;
unrelate self from ol1 across R2;
select one ol2 related by self->OL_INT[R2];
if (empty ol2)
  LOG::LogSuccess(message:"IX2: OR_INT - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_INT - unrelate object instance <rel_inst>") ;
end if;
delete object instance ol1;

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
	1600);
INSERT INTO GD_GE
	VALUES (2097155,
	2097153,
	2097154,
	41);
INSERT INTO GD_SHP
	VALUES (2097155,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (2097156,
	2097153,
	2097155,
	41);
INSERT INTO GD_SHP
	VALUES (2097156,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (2097157,
	2097153,
	2097154,
	42);
INSERT INTO GD_CON
	VALUES (2097157,
	2097154,
	2097154,
	0);
INSERT INTO GD_CTXT
	VALUES (2097157,
	0,
	0,
	0,
	0,
	0,
	0,
	1680,
	1200,
	1894,
	1229,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097158,
	2097157,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (2097159,
	2097157,
	1664,
	1312,
	1664,
	1232,
	2097158);
INSERT INTO GD_LS
	VALUES (2097160,
	2097157,
	1664,
	1232,
	1792,
	1232,
	2097159);
INSERT INTO GD_LS
	VALUES (2097161,
	2097157,
	1792,
	1232,
	1792,
	1280,
	2097160);
INSERT INTO GD_GE
	VALUES (2097162,
	2097153,
	2097155,
	42);
INSERT INTO GD_CON
	VALUES (2097162,
	2097154,
	2097155,
	0);
INSERT INTO GD_CTXT
	VALUES (2097162,
	0,
	0,
	0,
	0,
	0,
	0,
	2048,
	1216,
	2252,
	1254,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097163,
	2097162,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (2097164,
	2097162,
	2016,
	1248,
	2128,
	1248,
	2097163);
INSERT INTO GD_LS
	VALUES (2097165,
	2097162,
	2128,
	1248,
	2128,
	1280,
	2097164);
INSERT INTO GD_GE
	VALUES (2097166,
	2097153,
	2097153,
	42);
INSERT INTO GD_CON
	VALUES (2097166,
	2097154,
	2097156,
	0);
INSERT INTO GD_CTXT
	VALUES (2097166,
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
	VALUES (2097167,
	2097166,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (10485764,
	'Object with String Id Attr L Side',
	6,
	'OL_STR',
	'',
	10485780);
INSERT INTO O_NBATTR
	VALUES (10485772,
	10485764);
INSERT INTO O_BATTR
	VALUES (10485772,
	10485764);
INSERT INTO O_ATTR
	VALUES (10485772,
	10485764,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (10485773,
	10485764);
INSERT INTO O_BATTR
	VALUES (10485773,
	10485764);
INSERT INTO O_ATTR
	VALUES (10485773,
	10485764,
	10485772,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_NBATTR
	VALUES (10485774,
	10485764);
INSERT INTO O_BATTR
	VALUES (10485774,
	10485764);
INSERT INTO O_ATTR
	VALUES (10485774,
	10485764,
	10485773,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	10485764);
INSERT INTO O_OIDA
	VALUES (10485772,
	10485764,
	0);
INSERT INTO O_RTIDA
	VALUES (10485772,
	10485764,
	0,
	10485762,
	10485763);
INSERT INTO SM_ISM
	VALUES (2621445,
	10485764);
INSERT INTO SM_SM
	VALUES (2621445,
	'',
	5);
INSERT INTO SM_MOORE
	VALUES (2621445);
INSERT INTO SM_EVTDI
	VALUES (2621441,
	2621445,
	'id',
	'',
	524293);
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
	2621441,
	'Starting IX2 Test',
	1,
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
	'Start IXC2 Test',
	0,
	'',
	'OL_STR1',
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621441,
	2621445,
	2621441);
INSERT INTO SM_LEVT
	VALUES (2621442,
	2621445,
	2621442);
INSERT INTO SM_SEVT
	VALUES (2621442,
	2621445,
	2621442);
INSERT INTO SM_EVT
	VALUES (2621442,
	2621445,
	2621442,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OL_STR2',
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621442,
	2621445,
	2621442);
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
	'Finish IX2 Test',
	0,
	'',
	'OL_STR3',
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621443,
	2621445,
	2621441);
INSERT INTO SM_STATE
	VALUES (2621442,
	2621445,
	2621442,
	'Verifying Rel with Instance',
	2,
	0);
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
	2621442,
	'');
INSERT INTO SM_SEME
	VALUES (2621442,
	2621442,
	2621445,
	2621442);
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
	'Finishing IX2 Test',
	3,
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
	2621442,
	'');
INSERT INTO SM_SEME
	VALUES (2621443,
	2621442,
	2621445,
	2621442);
INSERT INTO SM_CH
	VALUES (2621443,
	2621443,
	2621445,
	2621441,
	'');
INSERT INTO SM_SEME
	VALUES (2621443,
	2621443,
	2621445,
	2621441);
INSERT INTO SM_NSTXN
	VALUES (2621441,
	2621445,
	2621441,
	2621443,
	2621441);
INSERT INTO SM_TXN
	VALUES (2621441,
	2621445,
	2621443,
	2621441);
INSERT INTO SM_NSTXN
	VALUES (2621442,
	2621445,
	2621441,
	2621441,
	2621441);
INSERT INTO SM_TXN
	VALUES (2621442,
	2621445,
	2621441,
	2621441);
INSERT INTO SM_NSTXN
	VALUES (2621443,
	2621445,
	2621441,
	2621442,
	2621442);
INSERT INTO SM_TXN
	VALUES (2621443,
	2621445,
	2621442,
	2621442);
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
	'create object instance inst1 of OR_STR;
assign self.id = "id1";
assign inst1.r_id = "id2";
relate self to inst1 across R3;
assign inst1.i = 42;
assign self.i = 42;

// select one one_inst related by self-><rel_obj>[REL]
select one oner related by self->OR_STR[R3];
if (oner.i == 42)
  LOG::LogSuccess(message:"IX2: OL_STR - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_STR - select one inst related by self-><rel_obj>[REL]") ;
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select one ol related by self->OR_STR[R3]->OL_STR[R3];
if (ol.i == self.i)
  LOG::LogSuccess(message:"IX2: OL_STR - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_STR - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OR_STR2:''Verify Rel with Instance''(id:self.id) to oner;',
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
	'select one oner related by self->OR_STR[R3];
if (oner.r_id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OL_STR - Verifying Rel with Instance") ;
  generate OR_STR3:''Finish IX2 Test''() to oner;
else 
  LOG::LogFailure(message:"IX2: OL_STR - Verifying Rel with Instance") ;
end if;',
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
	'// check delete
select one oner related by self->OR_STR[R3];
unrelate oner from self across R3;
delete object instance oner;
select one oner related by self->OR_STR[R3];
if (empty oner)
  LOG::LogSuccess(message:"IX2: OL_STR - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_STR - delete object instance <rel_inst>") ;
end if;

//check unrelate
create object instance or1 of OR_STR;
assign or1.r_id = "id200";
relate self to or1 across R3;
unrelate self from or1 across R3;
select one or2 related by self->OR_STR[R3];
if (empty or2)
  LOG::LogSuccess(message:"IX2: OL_STR - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_STR - unrelate object instance <rel_inst>") ;
end if;
delete object instance or1;

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
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (2621443,
	2621441,
	2621442,
	41);
INSERT INTO GD_SHP
	VALUES (2621443,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (2621444,
	2621441,
	2621443,
	41);
INSERT INTO GD_SHP
	VALUES (2621444,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (2621445,
	2621441,
	2621442,
	42);
INSERT INTO GD_CON
	VALUES (2621445,
	2621442,
	2621442,
	0);
INSERT INTO GD_CTXT
	VALUES (2621445,
	0,
	0,
	0,
	0,
	0,
	0,
	1680,
	1200,
	1894,
	1229,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621446,
	2621445,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (2621447,
	2621445,
	1664,
	1312,
	1664,
	1232,
	2621446);
INSERT INTO GD_LS
	VALUES (2621448,
	2621445,
	1664,
	1232,
	1792,
	1232,
	2621447);
INSERT INTO GD_LS
	VALUES (2621449,
	2621445,
	1792,
	1232,
	1792,
	1280,
	2621448);
INSERT INTO GD_GE
	VALUES (2621450,
	2621441,
	2621443,
	42);
INSERT INTO GD_CON
	VALUES (2621450,
	2621442,
	2621443,
	0);
INSERT INTO GD_CTXT
	VALUES (2621450,
	0,
	0,
	0,
	0,
	0,
	0,
	2048,
	1216,
	2252,
	1254,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621451,
	2621450,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (2621452,
	2621450,
	2016,
	1248,
	2128,
	1248,
	2621451);
INSERT INTO GD_LS
	VALUES (2621453,
	2621450,
	2128,
	1248,
	2128,
	1280,
	2621452);
INSERT INTO GD_GE
	VALUES (2621454,
	2621441,
	2621441,
	42);
INSERT INTO GD_CON
	VALUES (2621454,
	2621442,
	2621444,
	0);
INSERT INTO GD_CTXT
	VALUES (2621454,
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
	VALUES (2621455,
	2621454,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (10485765,
	'Object with String Id Attr R Side',
	7,
	'OR_STR',
	'',
	10485780);
INSERT INTO O_NBATTR
	VALUES (10485775,
	10485765);
INSERT INTO O_BATTR
	VALUES (10485775,
	10485765);
INSERT INTO O_ATTR
	VALUES (10485775,
	10485765,
	0,
	'r_id',
	'',
	'',
	'r_id',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (10485776,
	10485765);
INSERT INTO O_BATTR
	VALUES (10485776,
	10485765);
INSERT INTO O_ATTR
	VALUES (10485776,
	10485765,
	10485775,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_REF
	VALUES (10485765,
	10485764,
	0,
	10485772,
	10485762,
	10485764,
	10485763,
	10485777,
	10485763,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (10485777,
	10485765,
	10485772,
	10485764,
	1);
INSERT INTO O_ATTR
	VALUES (10485777,
	10485765,
	10485776,
	'id',
	'',
	'',
	'id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (10485778,
	10485765);
INSERT INTO O_BATTR
	VALUES (10485778,
	10485765);
INSERT INTO O_ATTR
	VALUES (10485778,
	10485765,
	10485777,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	10485765);
INSERT INTO O_OIDA
	VALUES (10485775,
	10485765,
	0);
INSERT INTO SM_ISM
	VALUES (3145734,
	10485765);
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
	524293);
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
	'Starting IX2 Test',
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
	'Start IXC2 Test',
	0,
	'',
	'OR_STR1',
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
	'OR_STR2',
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
	'Finish IX2 Test',
	0,
	'',
	'OR_STR3',
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145730,
	3145734,
	3145730,
	'Verifying Rel with Instance',
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
INSERT INTO SM_STATE
	VALUES (3145731,
	3145734,
	3145729,
	'Finishing IX2 Test',
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
	3145729,
	3145729,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145730,
	3145734,
	3145729,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145731,
	3145734,
	3145729,
	3145730,
	3145730);
INSERT INTO SM_TXN
	VALUES (3145731,
	3145734,
	3145730,
	3145730);
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
	'create object instance inst1 of OL_STR;
assign self.r_id = "id3";
assign inst1.id = "id4";
relate self to inst1 across R3;
assign inst1.i = 32;
assign self.i = 32;

// select one one_inst related by self-><rel_obj>[REL]
select one ol related by self->OL_STR[R3];
if (ol.i == 32)
  LOG::LogSuccess(message:"IX2: OR_STR - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_STR - select one inst related by   self-><rel_obj>[REL]") ;
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select one oner related by self->OL_STR[R3]->OR_STR[R3];
if (oner.i == self.i)
  LOG::LogSuccess(message:"IX2: OR_STR - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_STR - select one inst related by   self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OL_STR2:''Verify Rel with Instance''(id:self.r_id) to ol;',
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
	'select one ol related by self->OL_STR[R3];
if (ol.id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OR_STR - Verifying Rel with Instance") ;
  generate OL_STR3:''Finish IX2 Test''() to ol;
else 
  LOG::LogFailure(message:"IX2: OR_STR - Verifying Rel with Instance") ;
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
	'// check delete
select one ol related by self->OL_STR[R3];
unrelate ol from self across R3;
delete object instance ol;
select one ol related by self->OL_STR[R3];
if (empty ol)
  LOG::LogSuccess(message:"IX2: OR_STR - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_STR - delete object instance <rel_inst>") ;
end if;

//check unrelate
create object instance ol1 of OL_STR;
assign ol1.id = "id101";
relate self to ol1 across R3;
unrelate self from ol1 across R3;
select one ol2 related by self->OL_STR[R3];
if (empty ol2)
  LOG::LogSuccess(message:"IX2: OR_STR - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_STR - unrelate object instance <rel_inst>") ;
end if;
delete object instance ol1;

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
	3145730,
	42);
INSERT INTO GD_CON
	VALUES (3145733,
	3145730,
	3145730,
	0);
INSERT INTO GD_CTXT
	VALUES (3145733,
	0,
	0,
	0,
	0,
	0,
	0,
	1680,
	1200,
	1894,
	1229,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145734,
	3145733,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (3145735,
	3145733,
	1664,
	1312,
	1664,
	1232,
	3145734);
INSERT INTO GD_LS
	VALUES (3145736,
	3145733,
	1664,
	1232,
	1792,
	1232,
	3145735);
INSERT INTO GD_LS
	VALUES (3145737,
	3145733,
	1792,
	1232,
	1792,
	1280,
	3145736);
INSERT INTO GD_GE
	VALUES (3145738,
	3145729,
	3145731,
	42);
INSERT INTO GD_CON
	VALUES (3145738,
	3145730,
	3145731,
	0);
INSERT INTO GD_CTXT
	VALUES (3145738,
	0,
	0,
	0,
	0,
	0,
	0,
	2048,
	1216,
	2252,
	1254,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145739,
	3145738,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (3145740,
	3145738,
	2016,
	1248,
	2128,
	1248,
	3145739);
INSERT INTO GD_LS
	VALUES (3145741,
	3145738,
	2128,
	1248,
	2128,
	1280,
	3145740);
INSERT INTO GD_GE
	VALUES (3145742,
	3145729,
	3145729,
	42);
INSERT INTO GD_CON
	VALUES (3145742,
	3145730,
	3145732,
	0);
INSERT INTO GD_CTXT
	VALUES (3145742,
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
	VALUES (3145743,
	3145742,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (10485766,
	'Object with Bool Id Attr L Side',
	8,
	'OL_BOOL',
	'',
	10485780);
INSERT INTO O_NBATTR
	VALUES (10485779,
	10485766);
INSERT INTO O_BATTR
	VALUES (10485779,
	10485766);
INSERT INTO O_ATTR
	VALUES (10485779,
	10485766,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (10485780,
	10485766);
INSERT INTO O_BATTR
	VALUES (10485780,
	10485766);
INSERT INTO O_ATTR
	VALUES (10485780,
	10485766,
	10485779,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (10485781,
	10485766);
INSERT INTO O_BATTR
	VALUES (10485781,
	10485766);
INSERT INTO O_ATTR
	VALUES (10485781,
	10485766,
	10485780,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	10485766);
INSERT INTO O_OIDA
	VALUES (10485779,
	10485766,
	0);
INSERT INTO O_RTIDA
	VALUES (10485779,
	10485766,
	0,
	10485763,
	10485765);
INSERT INTO SM_ISM
	VALUES (3670023,
	10485766);
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
	524290);
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
	'Starting IX2 Test',
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
	'Start IXC2 Test',
	0,
	'',
	'OL_BOOL1',
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
	'OL_BOOL2',
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
	'Finish IX2 Test',
	0,
	'',
	'OL_BOOL3',
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670019,
	3670023,
	3670017);
INSERT INTO SM_STATE
	VALUES (3670018,
	3670023,
	3670018,
	'Verifying Rel with Instance',
	2,
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
INSERT INTO SM_STATE
	VALUES (3670019,
	3670023,
	3670017,
	'Finishing IX2 Test',
	3,
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
INSERT INTO SM_CH
	VALUES (3670019,
	3670019,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670019,
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
	3670017,
	3670017,
	3670017);
INSERT INTO SM_TXN
	VALUES (3670018,
	3670023,
	3670017,
	3670017);
INSERT INTO SM_NSTXN
	VALUES (3670019,
	3670023,
	3670017,
	3670018,
	3670018);
INSERT INTO SM_TXN
	VALUES (3670019,
	3670023,
	3670018,
	3670018);
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
	'assign self.id = TRUE;
create object instance inst1 of OR_BOOL;
assign inst1.r_id=FALSE;
relate self to inst1 across R4;
assign inst1.i = 42;
assign self.i = 42;

// select one one_inst related by self-><rel_obj>[REL]
select one oner related by self->OR_BOOL[R4];
if (oner.i == 42)
  LOG::LogSuccess(message:"IX2: OL_BOOL - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_BOOL - select one inst related by self-><rel_obj>[REL]") ;
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select one ol related by self->OR_BOOL[R4]->OL_BOOL[R4];
if (ol.i == self.i)
  LOG::LogSuccess(message:"IX2: OL_BOOL - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_BOOL - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OR_BOOL2:''Verify Rel with Instance''(id:self.id) to oner;',
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
	'select one oner related by self->OR_BOOL[R4];
if (oner.r_id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OL_BOOL - Verifying Rel with Instance") ;
  generate OR_BOOL3:''Finish IX2 Test''() to oner;
else 
  LOG::LogFailure(message:"IX2: OL_BOOL - Verifying Rel with Instance") ;
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
	'// check delete
select one oner related by self->OR_BOOL[R4];
unrelate self from oner across R4;
delete object instance oner;
select one oner related by self->OR_BOOL[R4];
if (empty oner)
  LOG::LogSuccess(message:"IX2: OL_BOOL - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_BOOL - delete object instance <rel_inst>") ;
end if;

//check unrelate
create object instance or1 of OR_BOOL;
assign or1.r_id = FALSE;
relate self to or1 across R4;
unrelate self from or1 across R4;
select one or2 related by self->OR_BOOL[R4];
if (empty or2)
  LOG::LogSuccess(message:"IX2: OL_BOOL - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_BOOL - unrelate object instance <rel_inst>") ;
end if;
delete object instance or1;

',
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
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (3670019,
	3670017,
	3670018,
	41);
INSERT INTO GD_SHP
	VALUES (3670019,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (3670020,
	3670017,
	3670019,
	41);
INSERT INTO GD_SHP
	VALUES (3670020,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (3670021,
	3670017,
	3670018,
	42);
INSERT INTO GD_CON
	VALUES (3670021,
	3670018,
	3670018,
	0);
INSERT INTO GD_CTXT
	VALUES (3670021,
	0,
	0,
	0,
	0,
	0,
	0,
	1680,
	1200,
	1894,
	1229,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670022,
	3670021,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (3670023,
	3670021,
	1664,
	1312,
	1664,
	1232,
	3670022);
INSERT INTO GD_LS
	VALUES (3670024,
	3670021,
	1664,
	1232,
	1792,
	1232,
	3670023);
INSERT INTO GD_LS
	VALUES (3670025,
	3670021,
	1792,
	1232,
	1792,
	1280,
	3670024);
INSERT INTO GD_GE
	VALUES (3670026,
	3670017,
	3670019,
	42);
INSERT INTO GD_CON
	VALUES (3670026,
	3670018,
	3670019,
	0);
INSERT INTO GD_CTXT
	VALUES (3670026,
	0,
	0,
	0,
	0,
	0,
	0,
	2048,
	1216,
	2252,
	1254,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670027,
	3670026,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (3670028,
	3670026,
	2016,
	1248,
	2128,
	1248,
	3670027);
INSERT INTO GD_LS
	VALUES (3670029,
	3670026,
	2128,
	1248,
	2128,
	1280,
	3670028);
INSERT INTO GD_GE
	VALUES (3670030,
	3670017,
	3670017,
	42);
INSERT INTO GD_CON
	VALUES (3670030,
	3670018,
	3670020,
	0);
INSERT INTO GD_CTXT
	VALUES (3670030,
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
	VALUES (3670031,
	3670030,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (10485767,
	'Object with Bool Id Attr R Side',
	9,
	'OR_BOOL',
	'',
	10485780);
INSERT INTO O_NBATTR
	VALUES (10485782,
	10485767);
INSERT INTO O_BATTR
	VALUES (10485782,
	10485767);
INSERT INTO O_ATTR
	VALUES (10485782,
	10485767,
	0,
	'r_id',
	'',
	'',
	'r_id',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (10485783,
	10485767);
INSERT INTO O_BATTR
	VALUES (10485783,
	10485767);
INSERT INTO O_ATTR
	VALUES (10485783,
	10485767,
	10485782,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_REF
	VALUES (10485767,
	10485766,
	0,
	10485779,
	10485763,
	10485766,
	10485765,
	10485784,
	10485764,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (10485784,
	10485767,
	10485779,
	10485766,
	1);
INSERT INTO O_ATTR
	VALUES (10485784,
	10485767,
	10485783,
	'id',
	'',
	'',
	'id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (10485785,
	10485767);
INSERT INTO O_BATTR
	VALUES (10485785,
	10485767);
INSERT INTO O_ATTR
	VALUES (10485785,
	10485767,
	10485784,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	10485767);
INSERT INTO O_OIDA
	VALUES (10485782,
	10485767,
	0);
INSERT INTO SM_ISM
	VALUES (4194312,
	10485767);
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
	524290);
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
	'Starting IX2 Test',
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
	'Start IXC2 Test',
	0,
	'',
	'OR_BOOL1',
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
	'OR_BOOL2',
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
	'Finish IX2 Test',
	0,
	'',
	'OR_BOOL3',
	'');
INSERT INTO SM_SEME
	VALUES (4194305,
	4194307,
	4194312,
	4194305);
INSERT INTO SM_STATE
	VALUES (4194306,
	4194312,
	4194306,
	'Verifying Rel with Instance',
	2,
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
INSERT INTO SM_STATE
	VALUES (4194307,
	4194312,
	4194305,
	'Finishing IX2 Test',
	3,
	0);
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
	4194305,
	4194305,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194306,
	4194312,
	4194305,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194307,
	4194312,
	4194305,
	4194306,
	4194306);
INSERT INTO SM_TXN
	VALUES (4194307,
	4194312,
	4194306,
	4194306);
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
	'assign self.r_id = TRUE;
create object instance inst1 of OL_BOOL;
assign inst1.id = FALSE;
relate self to inst1 across R4;
assign inst1.i = 32;
assign self.i = 32;

// select one one_inst related by self-><rel_obj>[REL]
select one ol related by self->OL_BOOL[R4];
if (ol.i == 32)
  LOG::LogSuccess(message:"IX2: OR_BOOL - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_BOOL - select one inst related by   self-><rel_obj>[REL]") ;
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select one oner related by self->OL_BOOL[R4]->OR_BOOL[R4];
if (oner.i == self.i)
  LOG::LogSuccess(message:"IX2: OR_BOOL - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_BOOL - select one inst related by   self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OL_BOOL2:''Verify Rel with Instance''(id:self.r_id) to ol;',
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
	'select one ol related by self->OL_BOOL[R4];
if (ol.id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OR_BOOL - Verifying Rel with Instance") ;
  generate OL_BOOL3:''Finish IX2 Test''() to ol;
else 
  LOG::LogFailure(message:"IX2: OR_BOOL - Verifying Rel with Instance") ;
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
	'// check delete
select one ol related by self->OL_BOOL[R4];
unrelate ol from self across R4;
delete object instance ol;
select one ol related by self->OL_BOOL[R4];
if (empty ol)
  LOG::LogSuccess(message:"IX2: OR_BOOL - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_BOOL - delete object instance <rel_inst>") ;
end if;

//check unrelate
create object instance ol1 of OL_BOOL;
relate self to ol1 across R4;
unrelate self from ol1 across R4;
select one ol2 related by self->OL_BOOL[R4];
if (empty ol2)
  LOG::LogSuccess(message:"IX2: OR_BOOL - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_BOOL - unrelate object instance <rel_inst>") ;
end if;
delete object instance ol1;

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
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (4194307,
	4194305,
	4194306,
	41);
INSERT INTO GD_SHP
	VALUES (4194307,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (4194308,
	4194305,
	4194307,
	41);
INSERT INTO GD_SHP
	VALUES (4194308,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (4194309,
	4194305,
	4194306,
	42);
INSERT INTO GD_CON
	VALUES (4194309,
	4194306,
	4194306,
	0);
INSERT INTO GD_CTXT
	VALUES (4194309,
	0,
	0,
	0,
	0,
	0,
	0,
	1680,
	1200,
	1894,
	1229,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194310,
	4194309,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (4194311,
	4194309,
	1664,
	1312,
	1664,
	1232,
	4194310);
INSERT INTO GD_LS
	VALUES (4194312,
	4194309,
	1664,
	1232,
	1792,
	1232,
	4194311);
INSERT INTO GD_LS
	VALUES (4194313,
	4194309,
	1792,
	1232,
	1792,
	1280,
	4194312);
INSERT INTO GD_GE
	VALUES (4194314,
	4194305,
	4194307,
	42);
INSERT INTO GD_CON
	VALUES (4194314,
	4194306,
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
	2048,
	1216,
	2252,
	1254,
	1,
	-7,
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
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (4194316,
	4194314,
	2016,
	1248,
	2128,
	1248,
	4194315);
INSERT INTO GD_LS
	VALUES (4194317,
	4194314,
	2128,
	1248,
	2128,
	1280,
	4194316);
INSERT INTO GD_GE
	VALUES (4194318,
	4194305,
	4194305,
	42);
INSERT INTO GD_CON
	VALUES (4194318,
	4194306,
	4194308,
	0);
INSERT INTO GD_CTXT
	VALUES (4194318,
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
	VALUES (4194319,
	4194318,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (10485768,
	'Object with Date Id Attr L Side',
	10,
	'OL_DATE',
	'',
	10485780);
INSERT INTO O_NBATTR
	VALUES (10485786,
	10485768);
INSERT INTO O_BATTR
	VALUES (10485786,
	10485768);
INSERT INTO O_ATTR
	VALUES (10485786,
	10485768,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524302);
INSERT INTO O_NBATTR
	VALUES (10485787,
	10485768);
INSERT INTO O_BATTR
	VALUES (10485787,
	10485768);
INSERT INTO O_ATTR
	VALUES (10485787,
	10485768,
	10485786,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_NBATTR
	VALUES (10485788,
	10485768);
INSERT INTO O_BATTR
	VALUES (10485788,
	10485768);
INSERT INTO O_ATTR
	VALUES (10485788,
	10485768,
	10485787,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	10485768);
INSERT INTO O_OIDA
	VALUES (10485786,
	10485768,
	0);
INSERT INTO O_RTIDA
	VALUES (10485786,
	10485768,
	0,
	10485764,
	10485767);
INSERT INTO SM_ISM
	VALUES (4718601,
	10485768);
INSERT INTO SM_SM
	VALUES (4718601,
	'',
	9);
INSERT INTO SM_MOORE
	VALUES (4718601);
INSERT INTO SM_EVTDI
	VALUES (4718593,
	4718601,
	'id',
	'',
	524302);
INSERT INTO SM_SUPDT
	VALUES (4718593,
	4718601,
	0);
INSERT INTO SM_SUPDT
	VALUES (4718594,
	4718601,
	0);
INSERT INTO SM_SDI
	VALUES (4718593,
	4718594,
	4718601);
INSERT INTO SM_STATE
	VALUES (4718593,
	4718601,
	4718593,
	'Starting IX2 Test',
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
	'Start IXC2 Test',
	0,
	'',
	'OL_DATE1',
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
	'Verify Rel with Instance',
	0,
	'',
	'OL_DATE2',
	'');
INSERT INTO SM_SEME
	VALUES (4718593,
	4718594,
	4718601,
	4718594);
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
	'Finish IX2 Test',
	0,
	'',
	'OL_DATE3',
	'');
INSERT INTO SM_SEME
	VALUES (4718593,
	4718595,
	4718601,
	4718593);
INSERT INTO SM_STATE
	VALUES (4718594,
	4718601,
	4718594,
	'Verifying Rel with Instance',
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
	4718594,
	'');
INSERT INTO SM_SEME
	VALUES (4718594,
	4718594,
	4718601,
	4718594);
INSERT INTO SM_CH
	VALUES (4718594,
	4718595,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718594,
	4718595,
	4718601,
	4718593);
INSERT INTO SM_STATE
	VALUES (4718595,
	4718601,
	4718593,
	'Finishing IX2 Test',
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
	4718594,
	'');
INSERT INTO SM_SEME
	VALUES (4718595,
	4718594,
	4718601,
	4718594);
INSERT INTO SM_CH
	VALUES (4718595,
	4718595,
	4718601,
	4718593,
	'');
INSERT INTO SM_SEME
	VALUES (4718595,
	4718595,
	4718601,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718593,
	4718601,
	4718593,
	4718595,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718593,
	4718601,
	4718595,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718594,
	4718601,
	4718593,
	4718593,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718594,
	4718601,
	4718593,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718595,
	4718601,
	4718593,
	4718594,
	4718594);
INSERT INTO SM_TXN
	VALUES (4718595,
	4718601,
	4718594,
	4718594);
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
	'bridge date1 = TIM::create_date(second:0,minute:0,hour:0,day:1,month:1,year:1990);
bridge date2 = TIM::create_date(second:0,minute:1,hour:0,day:1,month:1,year:1990);
assign self.id = date1;
create object instance inst1 of OR_DATE;
assign inst1.r_id = date2;
relate self to inst1 across R5;
assign inst1.i = 42;
assign self.i = 42;

// select one one_inst related by self-><rel_obj>[REL]
select one oner related by self->OR_DATE[R5];
if (oner.i == 42)
  LOG::LogSuccess(message:"IX2: OL_DATE - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_DATE - select one inst related by self-><rel_obj>[REL]") ;
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select one ol related by self->OR_DATE[R5]->OL_DATE[R5];
if (ol.i == self.i)
  LOG::LogSuccess(message:"IX2: OL_DATE - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_DATE - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OR_DATE2:''Verify Rel with Instance''(id:self.id) to oner;',
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
	'select one oner related by self->OR_DATE[R5];
if (oner.r_id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OL_DATE - Verifying Rel with Instance") ;
  generate OR_DATE3:''Finish IX2 Test''() to oner;
else 
  LOG::LogFailure(message:"IX2: OL_DATE - Verifying Rel with Instance");
end if;',
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
	'// check delete
select one oner related by self->OR_DATE[R5];
unrelate oner from self across R5;
delete object instance oner;
select one oner related by self->OR_DATE[R5];
if (empty oner)
  LOG::LogSuccess(message:"IX2: OL_DATE - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_DATE - delete object instance <rel_inst>") ;
end if;

//check unrelate
create object instance or1 of OR_DATE;
bridge date1 = TIM::create_date(second:3,minute:3,hour:3,day:3,month:3,year:704);
assign or1.r_id = date1;
relate self to or1 across R5;
unrelate self from or1 across R5;
select one or2 related by self->OR_DATE[R5];
if (empty or2)
  LOG::LogSuccess(message:"IX2: OL_DATE - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_DATE - unrelate object instance <rel_inst>") ;
end if;
delete object instance or1;

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
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (4718595,
	4718593,
	4718594,
	41);
INSERT INTO GD_SHP
	VALUES (4718595,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (4718596,
	4718593,
	4718595,
	41);
INSERT INTO GD_SHP
	VALUES (4718596,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (4718597,
	4718593,
	4718594,
	42);
INSERT INTO GD_CON
	VALUES (4718597,
	4718594,
	4718594,
	0);
INSERT INTO GD_CTXT
	VALUES (4718597,
	0,
	0,
	0,
	0,
	0,
	0,
	1680,
	1200,
	1894,
	1229,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718598,
	4718597,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (4718599,
	4718597,
	1664,
	1312,
	1664,
	1232,
	4718598);
INSERT INTO GD_LS
	VALUES (4718600,
	4718597,
	1664,
	1232,
	1792,
	1232,
	4718599);
INSERT INTO GD_LS
	VALUES (4718601,
	4718597,
	1792,
	1232,
	1792,
	1280,
	4718600);
INSERT INTO GD_GE
	VALUES (4718602,
	4718593,
	4718595,
	42);
INSERT INTO GD_CON
	VALUES (4718602,
	4718594,
	4718595,
	0);
INSERT INTO GD_CTXT
	VALUES (4718602,
	0,
	0,
	0,
	0,
	0,
	0,
	2048,
	1216,
	2252,
	1254,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718603,
	4718602,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (4718604,
	4718602,
	2016,
	1248,
	2128,
	1248,
	4718603);
INSERT INTO GD_LS
	VALUES (4718605,
	4718602,
	2128,
	1248,
	2128,
	1280,
	4718604);
INSERT INTO GD_GE
	VALUES (4718606,
	4718593,
	4718593,
	42);
INSERT INTO GD_CON
	VALUES (4718606,
	4718594,
	4718596,
	0);
INSERT INTO GD_CTXT
	VALUES (4718606,
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
	VALUES (4718607,
	4718606,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (10485769,
	'Object with Date Id Attr R Side',
	11,
	'OR_DATE',
	'',
	10485780);
INSERT INTO O_NBATTR
	VALUES (10485789,
	10485769);
INSERT INTO O_BATTR
	VALUES (10485789,
	10485769);
INSERT INTO O_ATTR
	VALUES (10485789,
	10485769,
	0,
	'r_id',
	'',
	'',
	'r_id',
	0,
	524302);
INSERT INTO O_NBATTR
	VALUES (10485790,
	10485769);
INSERT INTO O_BATTR
	VALUES (10485790,
	10485769);
INSERT INTO O_ATTR
	VALUES (10485790,
	10485769,
	10485789,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_REF
	VALUES (10485769,
	10485768,
	0,
	10485786,
	10485764,
	10485768,
	10485767,
	10485791,
	10485765,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (10485791,
	10485769,
	10485786,
	10485768,
	1);
INSERT INTO O_ATTR
	VALUES (10485791,
	10485769,
	10485790,
	'id',
	'',
	'',
	'id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (10485792,
	10485769);
INSERT INTO O_BATTR
	VALUES (10485792,
	10485769);
INSERT INTO O_ATTR
	VALUES (10485792,
	10485769,
	10485791,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	10485769);
INSERT INTO O_OIDA
	VALUES (10485789,
	10485769,
	0);
INSERT INTO SM_ISM
	VALUES (5242890,
	10485769);
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
	524302);
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
	'Starting IX2 Test',
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
	'Start IXC2 Test',
	0,
	'',
	'OR_DATE1',
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
	'OR_DATE2',
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
	'Finish IX2 Test',
	0,
	'',
	'OR_DATE3',
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
	'Verifying Rel with Instance',
	2,
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
INSERT INTO SM_STATE
	VALUES (5242883,
	5242890,
	5242881,
	'Finishing IX2 Test',
	3,
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
INSERT INTO SM_CH
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
	5242881,
	5242881,
	5242881);
INSERT INTO SM_TXN
	VALUES (5242882,
	5242890,
	5242881,
	5242881);
INSERT INTO SM_NSTXN
	VALUES (5242883,
	5242890,
	5242881,
	5242882,
	5242882);
INSERT INTO SM_TXN
	VALUES (5242883,
	5242890,
	5242882,
	5242882);
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
	'bridge date1 = TIM::create_date(second:1,minute:0,hour:1,day:1,month:10,year:1980);
bridge date2 = TIM::create_date(second:1,minute:0,hour:1,day:1,month:11,year:1980);
assign self.r_id = date1;
create object instance inst1 of OL_DATE;
assign inst1.id = date2;
relate self to inst1 across R5;
assign inst1.i = 32;
assign self.i = 32;

// select one one_inst related by self-><rel_obj>[REL]
select one ol related by self->OL_DATE[R5];
if (ol.i == 32)
  LOG::LogSuccess(message:"IX2: OR_DATE - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_DATE - select one inst related by   self-><rel_obj>[REL]") ;
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select one oner related by self->OL_DATE[R5]->OR_DATE[R5];
if (oner.i == self.i)
  LOG::LogSuccess(message:"IX2: OR_DATE - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_DATE - select one inst related by   self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OL_DATE2:''Verify Rel with Instance''(id:self.r_id) to ol;',
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
	'select one ol related by self->OL_DATE[R5];
if (ol.id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OR_DATE - Verifying Rel with Instance") ;
  generate OL_DATE3:''Finish IX2 Test''() to ol;
else 
  LOG::LogFailure(message:"IX2: OR_DATE - Verifying Rel with Instance") ;
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
	'// check delete
select one ol related by self->OL_DATE[R5];
unrelate ol from self across R5;
delete object instance ol;
select one ol related by self->OL_DATE[R5];
if (empty ol)
  LOG::LogSuccess(message:"IX2: OR_DATE - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_DATE - delete object instance <rel_inst>") ;
end if;

//check unrelate
create object instance ol1 of OL_DATE;
assign y = 1905;
bridge date1 = TIM::create_date(second:5,minute:5,hour:5,day:5,month:5,year:y);
assign ol1.id = date1;
relate self to ol1 across R5;
unrelate self from ol1 across R5;
select one ol2 related by self->OL_DATE[R5];
if (empty ol2)
  LOG::LogSuccess(message:"IX2: OR_DATE - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_DATE - unrelate object instance <rel_inst>") ;
end if;
delete object instance ol1;

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
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (5242883,
	5242881,
	5242882,
	41);
INSERT INTO GD_SHP
	VALUES (5242883,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (5242884,
	5242881,
	5242883,
	41);
INSERT INTO GD_SHP
	VALUES (5242884,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (5242885,
	5242881,
	5242882,
	42);
INSERT INTO GD_CON
	VALUES (5242885,
	5242882,
	5242882,
	0);
INSERT INTO GD_CTXT
	VALUES (5242885,
	0,
	0,
	0,
	0,
	0,
	0,
	1680,
	1200,
	1894,
	1229,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5242886,
	5242885,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (5242887,
	5242885,
	1664,
	1312,
	1664,
	1232,
	5242886);
INSERT INTO GD_LS
	VALUES (5242888,
	5242885,
	1664,
	1232,
	1792,
	1232,
	5242887);
INSERT INTO GD_LS
	VALUES (5242889,
	5242885,
	1792,
	1232,
	1792,
	1280,
	5242888);
INSERT INTO GD_GE
	VALUES (5242890,
	5242881,
	5242883,
	42);
INSERT INTO GD_CON
	VALUES (5242890,
	5242882,
	5242883,
	0);
INSERT INTO GD_CTXT
	VALUES (5242890,
	0,
	0,
	0,
	0,
	0,
	0,
	2048,
	1216,
	2252,
	1254,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5242891,
	5242890,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (5242892,
	5242890,
	2016,
	1248,
	2128,
	1248,
	5242891);
INSERT INTO GD_LS
	VALUES (5242893,
	5242890,
	2128,
	1248,
	2128,
	1280,
	5242892);
INSERT INTO GD_GE
	VALUES (5242894,
	5242881,
	5242881,
	42);
INSERT INTO GD_CON
	VALUES (5242894,
	5242882,
	5242884,
	0);
INSERT INTO GD_CTXT
	VALUES (5242894,
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
	VALUES (5242895,
	5242894,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (10485770,
	'Object with Time Id Attr L Side',
	12,
	'OL_TIME',
	'',
	10485780);
INSERT INTO O_NBATTR
	VALUES (10485793,
	10485770);
INSERT INTO O_BATTR
	VALUES (10485793,
	10485770);
INSERT INTO O_ATTR
	VALUES (10485793,
	10485770,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524303);
INSERT INTO O_NBATTR
	VALUES (10485794,
	10485770);
INSERT INTO O_BATTR
	VALUES (10485794,
	10485770);
INSERT INTO O_ATTR
	VALUES (10485794,
	10485770,
	10485793,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_NBATTR
	VALUES (10485795,
	10485770);
INSERT INTO O_BATTR
	VALUES (10485795,
	10485770);
INSERT INTO O_ATTR
	VALUES (10485795,
	10485770,
	10485794,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	10485770);
INSERT INTO O_OIDA
	VALUES (10485793,
	10485770,
	0);
INSERT INTO O_RTIDA
	VALUES (10485793,
	10485770,
	0,
	10485765,
	10485769);
INSERT INTO SM_ISM
	VALUES (5767179,
	10485770);
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
	524303);
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
	'Starting IX2 Test',
	1,
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
	'Start IXC2 Test',
	0,
	'',
	'OL_TIME1',
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
	'OL_TIME2',
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
	3,
	'Finish IX2 Test',
	0,
	'',
	'OL_TIME3',
	'');
INSERT INTO SM_SEME
	VALUES (5767169,
	5767171,
	5767179,
	5767169);
INSERT INTO SM_STATE
	VALUES (5767170,
	5767179,
	5767170,
	'Verifying Rel with Instance',
	2,
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
INSERT INTO SM_STATE
	VALUES (5767171,
	5767179,
	5767169,
	'Finishing IX2 Test',
	3,
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
INSERT INTO SM_CH
	VALUES (5767171,
	5767171,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767171,
	5767171,
	5767179,
	5767169);
INSERT INTO SM_NSTXN
	VALUES (5767169,
	5767179,
	5767169,
	5767171,
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
	5767169,
	5767169);
INSERT INTO SM_TXN
	VALUES (5767170,
	5767179,
	5767169,
	5767169);
INSERT INTO SM_NSTXN
	VALUES (5767171,
	5767179,
	5767169,
	5767170,
	5767170);
INSERT INTO SM_TXN
	VALUES (5767171,
	5767179,
	5767170,
	5767170);
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
	'create object instance inst1 of OR_TIME;
bridge time1 = TIM::current_clock();
LOG::LogInfo(message:"IX2: OL_TIME - getting current clock()") ;
transform OR_TIME::waitOneSec();
bridge time2 = TIM::current_clock();
assign self.id = time1;
assign inst1.r_id = time2;
relate self to inst1 across R6;
assign inst1.i = 42;
assign self.i = 42;

// select one one_inst related by self-><rel_obj>[REL]
select one oner related by self->OR_TIME[R6];
if (oner.i == 42)
  LOG::LogSuccess(message:"IX2: OL_TIME - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_TIME - select one inst related by self-><rel_obj>[REL]") ;
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select one ol related by self->OR_TIME[R6]->OL_TIME[R6];
if (ol.i == self.i)
  LOG::LogSuccess(message:"IX2: OL_TIME - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_TIME - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OR_TIME2:''Verify Rel with Instance''(id:self.id) to oner;',
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
	'select one oner related by self->OR_TIME[R6];
if (oner.r_id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OL_TIME - Verifying Rel with Instance") ;
  generate OR_TIME3:''Finish IX2 Test''() to oner;
else 
  LOG::LogFailure(message:"IX2: OL_TIME - Verifying Rel with Instance") ;
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
	'// check delete
select one oner related by self->OR_TIME[R6];
unrelate oner from self across R6;
delete object instance oner;
select one oner related by self->OR_TIME[R6];
if (empty oner)
  LOG::LogSuccess(message:"IX2: OL_TIME - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_TIME - delete object instance <rel_inst>") ;
end if;

//check unrelate
create object instance or1 of OR_TIME;
bridge time1 = TIM::current_clock();
assign or1.r_id = time1;
relate self to or1 across R6;
unrelate self from or1 across R6;
select one or2 related by self->OR_TIME[R6];
if (empty or2)
  LOG::LogSuccess(message:"IX2: OL_TIME - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_TIME - unrelate object instance <rel_inst>") ;
end if;
delete object instance or1;

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
	1680,
	1280,
	2032,
	1600);
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
	1600);
INSERT INTO GD_GE
	VALUES (5767172,
	5767169,
	5767171,
	41);
INSERT INTO GD_SHP
	VALUES (5767172,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (5767173,
	5767169,
	5767170,
	42);
INSERT INTO GD_CON
	VALUES (5767173,
	5767170,
	5767170,
	0);
INSERT INTO GD_CTXT
	VALUES (5767173,
	0,
	0,
	0,
	0,
	0,
	0,
	1680,
	1200,
	1894,
	1229,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5767174,
	5767173,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (5767175,
	5767173,
	1664,
	1312,
	1664,
	1232,
	5767174);
INSERT INTO GD_LS
	VALUES (5767176,
	5767173,
	1664,
	1232,
	1792,
	1232,
	5767175);
INSERT INTO GD_LS
	VALUES (5767177,
	5767173,
	1792,
	1232,
	1792,
	1280,
	5767176);
INSERT INTO GD_GE
	VALUES (5767178,
	5767169,
	5767171,
	42);
INSERT INTO GD_CON
	VALUES (5767178,
	5767170,
	5767171,
	0);
INSERT INTO GD_CTXT
	VALUES (5767178,
	0,
	0,
	0,
	0,
	0,
	0,
	2048,
	1216,
	2252,
	1254,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5767179,
	5767178,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (5767180,
	5767178,
	2016,
	1248,
	2128,
	1248,
	5767179);
INSERT INTO GD_LS
	VALUES (5767181,
	5767178,
	2128,
	1248,
	2128,
	1280,
	5767180);
INSERT INTO GD_GE
	VALUES (5767182,
	5767169,
	5767169,
	42);
INSERT INTO GD_CON
	VALUES (5767182,
	5767170,
	5767172,
	0);
INSERT INTO GD_CTXT
	VALUES (5767182,
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
	VALUES (5767183,
	5767182,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (10485771,
	'Object with Time Id Attr R Side',
	13,
	'OR_TIME',
	'',
	10485780);
INSERT INTO O_TFR
	VALUES (10485761,
	10485771,
	'waitOneSec',
	'',
	524289,
	0,
	'',
	1);
INSERT INTO O_NBATTR
	VALUES (10485796,
	10485771);
INSERT INTO O_BATTR
	VALUES (10485796,
	10485771);
INSERT INTO O_ATTR
	VALUES (10485796,
	10485771,
	0,
	'r_id',
	'',
	'',
	'r_id',
	0,
	524303);
INSERT INTO O_NBATTR
	VALUES (10485797,
	10485771);
INSERT INTO O_BATTR
	VALUES (10485797,
	10485771);
INSERT INTO O_ATTR
	VALUES (10485797,
	10485771,
	10485796,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_REF
	VALUES (10485771,
	10485770,
	0,
	10485793,
	10485765,
	10485770,
	10485769,
	10485798,
	10485766,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (10485798,
	10485771,
	10485793,
	10485770,
	1);
INSERT INTO O_ATTR
	VALUES (10485798,
	10485771,
	10485797,
	'id',
	'',
	'',
	'id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (10485799,
	10485771);
INSERT INTO O_BATTR
	VALUES (10485799,
	10485771);
INSERT INTO O_ATTR
	VALUES (10485799,
	10485771,
	10485798,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	10485771);
INSERT INTO O_OIDA
	VALUES (10485796,
	10485771,
	0);
INSERT INTO SM_ISM
	VALUES (6291468,
	10485771);
INSERT INTO SM_SM
	VALUES (6291468,
	'',
	12);
INSERT INTO SM_MOORE
	VALUES (6291468);
INSERT INTO SM_EVTDI
	VALUES (6291457,
	6291468,
	'id',
	'',
	524303);
INSERT INTO SM_SUPDT
	VALUES (6291457,
	6291468,
	0);
INSERT INTO SM_SUPDT
	VALUES (6291458,
	6291468,
	0);
INSERT INTO SM_SDI
	VALUES (6291457,
	6291458,
	6291468);
INSERT INTO SM_STATE
	VALUES (6291457,
	6291468,
	6291457,
	'Starting IX2 Test',
	1,
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
	'Start IXC2 Test',
	0,
	'',
	'OR_TIME1',
	'');
INSERT INTO SM_SEME
	VALUES (6291457,
	6291457,
	6291468,
	6291457);
INSERT INTO SM_LEVT
	VALUES (6291458,
	6291468,
	6291458);
INSERT INTO SM_SEVT
	VALUES (6291458,
	6291468,
	6291458);
INSERT INTO SM_EVT
	VALUES (6291458,
	6291468,
	6291458,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OR_TIME2',
	'');
INSERT INTO SM_SEME
	VALUES (6291457,
	6291458,
	6291468,
	6291458);
INSERT INTO SM_LEVT
	VALUES (6291459,
	6291468,
	6291457);
INSERT INTO SM_SEVT
	VALUES (6291459,
	6291468,
	6291457);
INSERT INTO SM_EVT
	VALUES (6291459,
	6291468,
	6291457,
	3,
	'Finish IX2 Test',
	0,
	'',
	'OR_TIME3',
	'');
INSERT INTO SM_SEME
	VALUES (6291457,
	6291459,
	6291468,
	6291457);
INSERT INTO SM_STATE
	VALUES (6291458,
	6291468,
	6291458,
	'Verifying Rel with Instance',
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
	6291458,
	'');
INSERT INTO SM_SEME
	VALUES (6291458,
	6291458,
	6291468,
	6291458);
INSERT INTO SM_CH
	VALUES (6291458,
	6291459,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291458,
	6291459,
	6291468,
	6291457);
INSERT INTO SM_STATE
	VALUES (6291459,
	6291468,
	6291457,
	'Finishing IX2 Test',
	3,
	0);
INSERT INTO SM_CH
	VALUES (6291459,
	6291457,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291459,
	6291457,
	6291468,
	6291457);
INSERT INTO SM_CH
	VALUES (6291459,
	6291458,
	6291468,
	6291458,
	'');
INSERT INTO SM_SEME
	VALUES (6291459,
	6291458,
	6291468,
	6291458);
INSERT INTO SM_CH
	VALUES (6291459,
	6291459,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291459,
	6291459,
	6291468,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291457,
	6291468,
	6291457,
	6291459,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291457,
	6291468,
	6291459,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291458,
	6291468,
	6291457,
	6291457,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291458,
	6291468,
	6291457,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291459,
	6291468,
	6291457,
	6291458,
	6291458);
INSERT INTO SM_TXN
	VALUES (6291459,
	6291468,
	6291458,
	6291458);
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
	'bridge time1 = TIM::current_clock();
LOG::LogInfo(message:"IX2: OR_TIME - getting current clock()") ;
transform OR_TIME::waitOneSec();
bridge time2 = TIM::current_clock();
create object instance inst1 of OL_TIME;
assign self.r_id = time1;
assign inst1.id = time2;
relate self to inst1 across R6;
assign inst1.i = 32;
assign self.i = 32;

// select one one_inst related by self-><rel_obj>[REL]
select one ol related by self->OL_TIME[R6];
if (ol.i == 32)
  LOG::LogSuccess(message:"IX2: OR_TIME - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_TIME - select one inst related by   self-><rel_obj>[REL]") ;
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select one oner related by self->OL_TIME[R6]->OR_TIME[R6];
if (oner.i == self.i)
  LOG::LogSuccess(message:"IX2: OR_TIME - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_TIME - select one inst related by   self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OL_TIME2:''Verify Rel with Instance''(id:self.r_id) to ol;',
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
	'select one ol related by self->OL_TIME[R6];
if (ol.id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OR_TIME - Verifying Rel with Instance") ;
  generate OL_TIME3:''Finish IX2 Test''() to ol;
else 
  LOG::LogFailure(message:"IX2: OR_TIME - Verifying Rel with Instance") ;
end if;',
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
	'// check delete
select one ol related by self->OL_TIME[R6];
unrelate ol from self across R6;
delete object instance ol;
select one ol related by self->OL_TIME[R6];
if (empty ol)
  LOG::LogSuccess(message:"IX2: OR_TIME - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_TIME - delete object instance <rel_inst>") ;
end if;

//check unrelate
create object instance ol1 of OL_TIME;
bridge time1 = TIM::current_clock();
assign ol1.id = time1;
relate self to ol1 across R6;
unrelate self from ol1 across R6;
select one ol2 related by self->OL_TIME[R6];
if (empty ol2)
  LOG::LogSuccess(message:"IX2: OR_TIME - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_TIME - unrelate object instance <rel_inst>") ;
end if;
delete object instance ol1;

',
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
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (6291459,
	6291457,
	6291458,
	41);
INSERT INTO GD_SHP
	VALUES (6291459,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (6291460,
	6291457,
	6291459,
	41);
INSERT INTO GD_SHP
	VALUES (6291460,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (6291461,
	6291457,
	6291458,
	42);
INSERT INTO GD_CON
	VALUES (6291461,
	6291458,
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
	1680,
	1200,
	1894,
	1229,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291462,
	6291461,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (6291463,
	6291461,
	1664,
	1312,
	1664,
	1232,
	6291462);
INSERT INTO GD_LS
	VALUES (6291464,
	6291461,
	1664,
	1232,
	1792,
	1232,
	6291463);
INSERT INTO GD_LS
	VALUES (6291465,
	6291461,
	1792,
	1232,
	1792,
	1280,
	6291464);
INSERT INTO GD_GE
	VALUES (6291466,
	6291457,
	6291459,
	42);
INSERT INTO GD_CON
	VALUES (6291466,
	6291458,
	6291459,
	0);
INSERT INTO GD_CTXT
	VALUES (6291466,
	0,
	0,
	0,
	0,
	0,
	0,
	2048,
	1216,
	2252,
	1254,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291467,
	6291466,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (6291468,
	6291466,
	2016,
	1248,
	2128,
	1248,
	6291467);
INSERT INTO GD_LS
	VALUES (6291469,
	6291466,
	2128,
	1248,
	2128,
	1280,
	6291468);
INSERT INTO GD_GE
	VALUES (6291470,
	6291457,
	6291457,
	42);
INSERT INTO GD_CON
	VALUES (6291470,
	6291458,
	6291460,
	0);
INSERT INTO GD_CTXT
	VALUES (6291470,
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
	VALUES (6291471,
	6291470,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (10485772,
	'Object with Enum Id Attr L Side',
	14,
	'OL_ENUM',
	'',
	10485780);
INSERT INTO O_NBATTR
	VALUES (10485800,
	10485772);
INSERT INTO O_BATTR
	VALUES (10485800,
	10485772);
INSERT INTO O_ATTR
	VALUES (10485800,
	10485772,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (10485801,
	10485772);
INSERT INTO O_BATTR
	VALUES (10485801,
	10485772);
INSERT INTO O_ATTR
	VALUES (10485801,
	10485772,
	10485800,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_NBATTR
	VALUES (10485802,
	10485772);
INSERT INTO O_BATTR
	VALUES (10485802,
	10485772);
INSERT INTO O_ATTR
	VALUES (10485802,
	10485772,
	10485801,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	10485772);
INSERT INTO O_OIDA
	VALUES (10485800,
	10485772,
	0);
INSERT INTO O_RTIDA
	VALUES (10485800,
	10485772,
	0,
	10485766,
	10485771);
INSERT INTO SM_ISM
	VALUES (6815757,
	10485772);
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
	524305);
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
	'Starting IX2 Test',
	1,
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
	'Start IXC2 Test',
	0,
	'',
	'OL_ENUM1',
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
	'OL_ENUM2',
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
	'Finish IX2 Test',
	0,
	'',
	'OL_ENUM3',
	'');
INSERT INTO SM_SEME
	VALUES (6815745,
	6815747,
	6815757,
	6815745);
INSERT INTO SM_STATE
	VALUES (6815746,
	6815757,
	6815746,
	'Verifying Rel with Instance',
	2,
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
INSERT INTO SM_STATE
	VALUES (6815747,
	6815757,
	6815745,
	'Finishing IX2 Test',
	3,
	0);
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
	6815745,
	6815745,
	6815745);
INSERT INTO SM_TXN
	VALUES (6815746,
	6815757,
	6815745,
	6815745);
INSERT INTO SM_NSTXN
	VALUES (6815747,
	6815757,
	6815745,
	6815746,
	6815746);
INSERT INTO SM_TXN
	VALUES (6815747,
	6815757,
	6815746,
	6815746);
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
	'assign self.id = "Red";
create object instance inst1 of OR_ENUM;
assign inst1.r_id = "Blue";
relate self to inst1 across R7;
assign inst1.i = 42;
assign self.i = 42;

// select one one_inst related by self-><rel_obj>[REL]
select one oner related by self->OR_ENUM[R7];
if (oner.i == 42)
  LOG::LogSuccess(message:"IX2: OL_ENUM - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_ENUM - select one inst related by self-><rel_obj>[REL]") ;
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select one ol related by self->OR_ENUM[R7]->OL_ENUM[R7];
if (ol.i == self.i)
  LOG::LogSuccess(message:"IX2: OL_ENUM - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_ENUM - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OR_ENUM2:''Verify Rel with Instance''(id:self.id) to oner;',
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
	'select one oner related by self->OR_ENUM[R7];
if (oner.r_id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OL_ENUM - Verifying Rel with Instance") ;
  generate OR_ENUM3:''Finish IX2 Test''() to oner;
else 
  LOG::LogFailure(message:"IX2: OL_ENUM - Verifying Rel with Instance") ;
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
	'// check delete
select one oner related by self->OR_ENUM[R7];
unrelate oner from self across R7;
delete object instance oner;
select one oner related by self->OR_ENUM[R7];
if (empty oner)
  LOG::LogSuccess(message:"IX2: OL_ENUM - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_ENUM - delete object instance <rel_inst>") ;
end if;

//check unrelate
create object instance or1 of OR_ENUM;
assign or1.r_id = "Teal";
relate self to or1 across R7;
unrelate self from or1 across R7;
select one or2 related by self->OR_ENUM[R7];
if (empty or2)
  LOG::LogSuccess(message:"IX2: OL_ENUM - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_ENUM - unrelate object instance <rel_inst>") ;
end if;
delete object instance or1;

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
	1600);
INSERT INTO GD_GE
	VALUES (6815747,
	6815745,
	6815746,
	41);
INSERT INTO GD_SHP
	VALUES (6815747,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (6815748,
	6815745,
	6815747,
	41);
INSERT INTO GD_SHP
	VALUES (6815748,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (6815749,
	6815745,
	6815746,
	42);
INSERT INTO GD_CON
	VALUES (6815749,
	6815746,
	6815746,
	0);
INSERT INTO GD_CTXT
	VALUES (6815749,
	0,
	0,
	0,
	0,
	0,
	0,
	1680,
	1200,
	1894,
	1229,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815750,
	6815749,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (6815751,
	6815749,
	1664,
	1312,
	1664,
	1232,
	6815750);
INSERT INTO GD_LS
	VALUES (6815752,
	6815749,
	1664,
	1232,
	1792,
	1232,
	6815751);
INSERT INTO GD_LS
	VALUES (6815753,
	6815749,
	1792,
	1232,
	1792,
	1280,
	6815752);
INSERT INTO GD_GE
	VALUES (6815754,
	6815745,
	6815747,
	42);
INSERT INTO GD_CON
	VALUES (6815754,
	6815746,
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
	2048,
	1216,
	2252,
	1254,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815755,
	6815754,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (6815756,
	6815754,
	2016,
	1248,
	2128,
	1248,
	6815755);
INSERT INTO GD_LS
	VALUES (6815757,
	6815754,
	2128,
	1248,
	2128,
	1280,
	6815756);
INSERT INTO GD_GE
	VALUES (6815758,
	6815745,
	6815745,
	42);
INSERT INTO GD_CON
	VALUES (6815758,
	6815746,
	6815748,
	0);
INSERT INTO GD_CTXT
	VALUES (6815758,
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
	VALUES (6815759,
	6815758,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (10485773,
	'Object with Enum Id Attr R Side',
	15,
	'OR_ENUM',
	'',
	10485780);
INSERT INTO O_NBATTR
	VALUES (10485803,
	10485773);
INSERT INTO O_BATTR
	VALUES (10485803,
	10485773);
INSERT INTO O_ATTR
	VALUES (10485803,
	10485773,
	0,
	'r_id',
	'',
	'',
	'r_id',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (10485804,
	10485773);
INSERT INTO O_BATTR
	VALUES (10485804,
	10485773);
INSERT INTO O_ATTR
	VALUES (10485804,
	10485773,
	10485803,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_REF
	VALUES (10485773,
	10485772,
	0,
	10485800,
	10485766,
	10485772,
	10485771,
	10485805,
	10485767,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (10485805,
	10485773,
	10485800,
	10485772,
	1);
INSERT INTO O_ATTR
	VALUES (10485805,
	10485773,
	10485804,
	'id',
	'',
	'',
	'id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (10485806,
	10485773);
INSERT INTO O_BATTR
	VALUES (10485806,
	10485773);
INSERT INTO O_ATTR
	VALUES (10485806,
	10485773,
	10485805,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	10485773);
INSERT INTO O_OIDA
	VALUES (10485803,
	10485773,
	0);
INSERT INTO SM_ISM
	VALUES (7340046,
	10485773);
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
	524305);
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
	'Starting IX2 Test',
	1,
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
	'Start IXC2 Test',
	0,
	'',
	'OR_ENUM1',
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
	'OR_ENUM2',
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
	'Finish IX2 Test',
	0,
	'',
	'OR_ENUM3',
	'');
INSERT INTO SM_SEME
	VALUES (7340033,
	7340035,
	7340046,
	7340033);
INSERT INTO SM_STATE
	VALUES (7340034,
	7340046,
	7340034,
	'Verifying Rel with Instance',
	2,
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
INSERT INTO SM_STATE
	VALUES (7340035,
	7340046,
	7340033,
	'Finishing IX2 Test',
	3,
	0);
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
	7340033,
	7340033,
	7340033);
INSERT INTO SM_TXN
	VALUES (7340034,
	7340046,
	7340033,
	7340033);
INSERT INTO SM_NSTXN
	VALUES (7340035,
	7340046,
	7340033,
	7340034,
	7340034);
INSERT INTO SM_TXN
	VALUES (7340035,
	7340046,
	7340034,
	7340034);
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
	'create object instance inst1 of OL_ENUM;
assign self.r_id = "Green";
assign inst1.id = "Black";
relate self to inst1 across R7;
assign inst1.i = 32;
assign self.i = 32;

// select one one_inst related by self-><rel_obj>[REL]
select one ol related by self->OL_ENUM[R7];
if (ol.i == 32)
  LOG::LogSuccess(message:"IX2: OR_ENUM - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_ENUM - select one inst related by   self-><rel_obj>[REL]") ;
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select one oner related by self->OL_ENUM[R7]->OR_ENUM[R7];
if (oner.i == self.i)
  LOG::LogSuccess(message:"IX2: OR_ENUM - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_ENUM - select one inst related by   self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OL_ENUM2:''Verify Rel with Instance''(id:self.r_id) to ol;',
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
	'select one ol related by self->OL_ENUM[R7];
if (ol.id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OR_ENUM - Verifying Rel with Instance") ;
  generate OL_ENUM3:''Finish IX2 Test''() to ol;
else 
  LOG::LogFailure(message:"IX2: OR_ENUM - Verifying Rel with Instance") ;
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
	'// check delete
select one ol related by self->OL_ENUM[R7];
unrelate ol from self across R7;
delete object instance ol;
select one ol related by self->OL_ENUM[R7];
if (empty ol)
  LOG::LogSuccess(message:"IX2: OR_ENUM - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_ENUM - delete object instance <rel_inst>") ;
end if;

//check unrelate
create object instance ol1 of OL_ENUM;
assign ol1.id = "Gray";
relate self to ol1 across R7;
unrelate self from ol1 across R7;
select one ol2 related by self->OL_ENUM[R7];
if (empty ol2)
  LOG::LogSuccess(message:"IX2: OR_ENUM - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_ENUM - unrelate object instance <rel_inst>") ;
end if;
delete object instance ol1;

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
	7340034,
	42);
INSERT INTO GD_CON
	VALUES (7340037,
	7340034,
	7340034,
	0);
INSERT INTO GD_CTXT
	VALUES (7340037,
	0,
	0,
	0,
	0,
	0,
	0,
	1680,
	1200,
	1894,
	1229,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (7340038,
	7340037,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (7340039,
	7340037,
	1664,
	1312,
	1664,
	1232,
	7340038);
INSERT INTO GD_LS
	VALUES (7340040,
	7340037,
	1664,
	1232,
	1792,
	1232,
	7340039);
INSERT INTO GD_LS
	VALUES (7340041,
	7340037,
	1792,
	1232,
	1792,
	1280,
	7340040);
INSERT INTO GD_GE
	VALUES (7340042,
	7340033,
	7340035,
	42);
INSERT INTO GD_CON
	VALUES (7340042,
	7340034,
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
	2048,
	1216,
	2252,
	1254,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (7340043,
	7340042,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (7340044,
	7340042,
	2016,
	1248,
	2128,
	1248,
	7340043);
INSERT INTO GD_LS
	VALUES (7340045,
	7340042,
	2128,
	1248,
	2128,
	1280,
	7340044);
INSERT INTO GD_GE
	VALUES (7340046,
	7340033,
	7340033,
	42);
INSERT INTO GD_CON
	VALUES (7340046,
	7340034,
	7340036,
	0);
INSERT INTO GD_CTXT
	VALUES (7340046,
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
	VALUES (7340047,
	7340046,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (10485774,
	'Object with User Id Attr L Side',
	16,
	'OL_USER',
	'',
	10485780);
INSERT INTO O_NBATTR
	VALUES (10485807,
	10485774);
INSERT INTO O_BATTR
	VALUES (10485807,
	10485774);
INSERT INTO O_ATTR
	VALUES (10485807,
	10485774,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524306);
INSERT INTO O_NBATTR
	VALUES (10485808,
	10485774);
INSERT INTO O_BATTR
	VALUES (10485808,
	10485774);
INSERT INTO O_ATTR
	VALUES (10485808,
	10485774,
	10485807,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_NBATTR
	VALUES (10485809,
	10485774);
INSERT INTO O_BATTR
	VALUES (10485809,
	10485774);
INSERT INTO O_ATTR
	VALUES (10485809,
	10485774,
	10485808,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	10485774);
INSERT INTO O_OIDA
	VALUES (10485807,
	10485774,
	0);
INSERT INTO O_RTIDA
	VALUES (10485807,
	10485774,
	0,
	10485767,
	10485773);
INSERT INTO SM_ISM
	VALUES (7864335,
	10485774);
INSERT INTO SM_SM
	VALUES (7864335,
	'',
	15);
INSERT INTO SM_MOORE
	VALUES (7864335);
INSERT INTO SM_EVTDI
	VALUES (7864321,
	7864335,
	'id',
	'',
	524306);
INSERT INTO SM_SUPDT
	VALUES (7864321,
	7864335,
	0);
INSERT INTO SM_SUPDT
	VALUES (7864322,
	7864335,
	0);
INSERT INTO SM_SDI
	VALUES (7864321,
	7864322,
	7864335);
INSERT INTO SM_STATE
	VALUES (7864321,
	7864335,
	7864321,
	'Starting IX2 Test',
	1,
	0);
INSERT INTO SM_LEVT
	VALUES (7864321,
	7864335,
	7864321);
INSERT INTO SM_SEVT
	VALUES (7864321,
	7864335,
	7864321);
INSERT INTO SM_EVT
	VALUES (7864321,
	7864335,
	7864321,
	1,
	'Start IXC2 Test',
	0,
	'',
	'OL_USER1',
	'');
INSERT INTO SM_SEME
	VALUES (7864321,
	7864321,
	7864335,
	7864321);
INSERT INTO SM_LEVT
	VALUES (7864322,
	7864335,
	7864322);
INSERT INTO SM_SEVT
	VALUES (7864322,
	7864335,
	7864322);
INSERT INTO SM_EVT
	VALUES (7864322,
	7864335,
	7864322,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OL_USER2',
	'');
INSERT INTO SM_SEME
	VALUES (7864321,
	7864322,
	7864335,
	7864322);
INSERT INTO SM_LEVT
	VALUES (7864323,
	7864335,
	7864321);
INSERT INTO SM_SEVT
	VALUES (7864323,
	7864335,
	7864321);
INSERT INTO SM_EVT
	VALUES (7864323,
	7864335,
	7864321,
	3,
	'Finish IX2 Test',
	0,
	'',
	'OL_USER3',
	'');
INSERT INTO SM_SEME
	VALUES (7864321,
	7864323,
	7864335,
	7864321);
INSERT INTO SM_STATE
	VALUES (7864322,
	7864335,
	7864322,
	'Verifying Rel with Instance',
	2,
	0);
INSERT INTO SM_CH
	VALUES (7864322,
	7864321,
	7864335,
	7864321,
	'');
INSERT INTO SM_SEME
	VALUES (7864322,
	7864321,
	7864335,
	7864321);
INSERT INTO SM_CH
	VALUES (7864322,
	7864322,
	7864335,
	7864322,
	'');
INSERT INTO SM_SEME
	VALUES (7864322,
	7864322,
	7864335,
	7864322);
INSERT INTO SM_CH
	VALUES (7864322,
	7864323,
	7864335,
	7864321,
	'');
INSERT INTO SM_SEME
	VALUES (7864322,
	7864323,
	7864335,
	7864321);
INSERT INTO SM_STATE
	VALUES (7864323,
	7864335,
	7864321,
	'Finishing IX2 Test',
	3,
	0);
INSERT INTO SM_CH
	VALUES (7864323,
	7864321,
	7864335,
	7864321,
	'');
INSERT INTO SM_SEME
	VALUES (7864323,
	7864321,
	7864335,
	7864321);
INSERT INTO SM_CH
	VALUES (7864323,
	7864322,
	7864335,
	7864322,
	'');
INSERT INTO SM_SEME
	VALUES (7864323,
	7864322,
	7864335,
	7864322);
INSERT INTO SM_CH
	VALUES (7864323,
	7864323,
	7864335,
	7864321,
	'');
INSERT INTO SM_SEME
	VALUES (7864323,
	7864323,
	7864335,
	7864321);
INSERT INTO SM_NSTXN
	VALUES (7864321,
	7864335,
	7864321,
	7864323,
	7864321);
INSERT INTO SM_TXN
	VALUES (7864321,
	7864335,
	7864323,
	7864321);
INSERT INTO SM_NSTXN
	VALUES (7864322,
	7864335,
	7864321,
	7864321,
	7864321);
INSERT INTO SM_TXN
	VALUES (7864322,
	7864335,
	7864321,
	7864321);
INSERT INTO SM_NSTXN
	VALUES (7864323,
	7864335,
	7864321,
	7864322,
	7864322);
INSERT INTO SM_TXN
	VALUES (7864323,
	7864335,
	7864322,
	7864322);
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
	'create object instance inst1 of OR_USER;
assign self.id = "id1";
assign inst1.r_id="id2";
relate self to inst1 across R8;
assign inst1.i = 42;
assign self.i = 42;

// select one one_inst related by self-><rel_obj>[REL]
select one oner related by self->OR_USER[R8];
if (oner.i == 42)
  LOG::LogSuccess(message:"IX2: OL_USER - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_USER - select one inst related by self-><rel_obj>[REL]") ;
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select one ol related by self->OR_USER[R8]->OL_USER[R8];
if (ol.i == self.i)
  LOG::LogSuccess(message:"IX2: OL_USER - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_USER - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OR_USER2:''Verify Rel with Instance''(id:self.id) to oner;',
	'');
INSERT INTO SM_MOAH
	VALUES (7864322,
	7864335,
	7864322);
INSERT INTO SM_AH
	VALUES (7864322,
	7864335);
INSERT INTO SM_ACT
	VALUES (7864322,
	7864335,
	1,
	'select one oner related by self->OR_USER[R8];
if (oner.r_id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OL_USER - Verifying Rel with Instance") ;
  generate OR_USER3:''Finish IX2 Test''() to oner;
else 
  LOG::LogFailure(message:"IX2: OL_USER - Verifying Rel with Instance") ;
end if;',
	'');
INSERT INTO SM_MOAH
	VALUES (7864323,
	7864335,
	7864323);
INSERT INTO SM_AH
	VALUES (7864323,
	7864335);
INSERT INTO SM_ACT
	VALUES (7864323,
	7864335,
	1,
	'// check delete
select one oner related by self->OR_USER[R8];
unrelate oner from self across R8;
delete object instance oner;
select one oner related by self->OR_USER[R8];
if (empty oner)
  LOG::LogSuccess(message:"IX2: OL_USER - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_USER - delete object instance <rel_inst>") ;
end if;

//check unrelate
create object instance or1 of OR_USER;
assign or1.r_id = "id24";
relate self to or1 across R8;
unrelate self from or1 across R8;
select one or2 related by self->OR_USER[R8];
if (empty or2)
  LOG::LogSuccess(message:"IX2: OL_USER - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_USER - unrelate object instance <rel_inst>") ;
end if;
delete object instance or1;

',
	'');
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
	1600,
	4200,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (7864322,
	7864321,
	7864321,
	41);
INSERT INTO GD_SHP
	VALUES (7864322,
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (7864323,
	7864321,
	7864322,
	41);
INSERT INTO GD_SHP
	VALUES (7864323,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (7864324,
	7864321,
	7864323,
	41);
INSERT INTO GD_SHP
	VALUES (7864324,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (7864325,
	7864321,
	7864322,
	42);
INSERT INTO GD_CON
	VALUES (7864325,
	7864322,
	7864322,
	0);
INSERT INTO GD_CTXT
	VALUES (7864325,
	0,
	0,
	0,
	0,
	0,
	0,
	1680,
	1200,
	1894,
	1229,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (7864326,
	7864325,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (7864327,
	7864325,
	1664,
	1312,
	1664,
	1232,
	7864326);
INSERT INTO GD_LS
	VALUES (7864328,
	7864325,
	1664,
	1232,
	1792,
	1232,
	7864327);
INSERT INTO GD_LS
	VALUES (7864329,
	7864325,
	1792,
	1232,
	1792,
	1280,
	7864328);
INSERT INTO GD_GE
	VALUES (7864330,
	7864321,
	7864323,
	42);
INSERT INTO GD_CON
	VALUES (7864330,
	7864322,
	7864323,
	0);
INSERT INTO GD_CTXT
	VALUES (7864330,
	0,
	0,
	0,
	0,
	0,
	0,
	2048,
	1216,
	2252,
	1254,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (7864331,
	7864330,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (7864332,
	7864330,
	2016,
	1248,
	2128,
	1248,
	7864331);
INSERT INTO GD_LS
	VALUES (7864333,
	7864330,
	2128,
	1248,
	2128,
	1280,
	7864332);
INSERT INTO GD_GE
	VALUES (7864334,
	7864321,
	7864321,
	42);
INSERT INTO GD_CON
	VALUES (7864334,
	7864322,
	7864324,
	0);
INSERT INTO GD_CTXT
	VALUES (7864334,
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
	VALUES (7864335,
	7864334,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (10485775,
	'Object with User Id Attr R Side',
	17,
	'OR_USER',
	'',
	10485780);
INSERT INTO O_NBATTR
	VALUES (10485810,
	10485775);
INSERT INTO O_BATTR
	VALUES (10485810,
	10485775);
INSERT INTO O_ATTR
	VALUES (10485810,
	10485775,
	0,
	'r_id',
	'',
	'',
	'r_id',
	0,
	524306);
INSERT INTO O_NBATTR
	VALUES (10485811,
	10485775);
INSERT INTO O_BATTR
	VALUES (10485811,
	10485775);
INSERT INTO O_ATTR
	VALUES (10485811,
	10485775,
	10485810,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_REF
	VALUES (10485775,
	10485774,
	0,
	10485807,
	10485767,
	10485774,
	10485773,
	10485812,
	10485768,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (10485812,
	10485775,
	10485807,
	10485774,
	1);
INSERT INTO O_ATTR
	VALUES (10485812,
	10485775,
	10485811,
	'id',
	'',
	'',
	'id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (10485813,
	10485775);
INSERT INTO O_BATTR
	VALUES (10485813,
	10485775);
INSERT INTO O_ATTR
	VALUES (10485813,
	10485775,
	10485812,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	10485775);
INSERT INTO O_OIDA
	VALUES (10485810,
	10485775,
	0);
INSERT INTO SM_ISM
	VALUES (8388624,
	10485775);
INSERT INTO SM_SM
	VALUES (8388624,
	'',
	16);
INSERT INTO SM_MOORE
	VALUES (8388624);
INSERT INTO SM_EVTDI
	VALUES (8388609,
	8388624,
	'id',
	'',
	524306);
INSERT INTO SM_SUPDT
	VALUES (8388609,
	8388624,
	0);
INSERT INTO SM_SUPDT
	VALUES (8388610,
	8388624,
	0);
INSERT INTO SM_SDI
	VALUES (8388609,
	8388610,
	8388624);
INSERT INTO SM_STATE
	VALUES (8388609,
	8388624,
	8388609,
	'Starting IX2 Test',
	1,
	0);
INSERT INTO SM_LEVT
	VALUES (8388609,
	8388624,
	8388609);
INSERT INTO SM_SEVT
	VALUES (8388609,
	8388624,
	8388609);
INSERT INTO SM_EVT
	VALUES (8388609,
	8388624,
	8388609,
	1,
	'Start IXC2 Test',
	0,
	'',
	'OR_USER1',
	'');
INSERT INTO SM_SEME
	VALUES (8388609,
	8388609,
	8388624,
	8388609);
INSERT INTO SM_LEVT
	VALUES (8388610,
	8388624,
	8388610);
INSERT INTO SM_SEVT
	VALUES (8388610,
	8388624,
	8388610);
INSERT INTO SM_EVT
	VALUES (8388610,
	8388624,
	8388610,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OR_USER2',
	'');
INSERT INTO SM_SEME
	VALUES (8388609,
	8388610,
	8388624,
	8388610);
INSERT INTO SM_LEVT
	VALUES (8388611,
	8388624,
	8388609);
INSERT INTO SM_SEVT
	VALUES (8388611,
	8388624,
	8388609);
INSERT INTO SM_EVT
	VALUES (8388611,
	8388624,
	8388609,
	3,
	'Finish IX2 Test',
	0,
	'',
	'OR_USER3',
	'');
INSERT INTO SM_SEME
	VALUES (8388609,
	8388611,
	8388624,
	8388609);
INSERT INTO SM_STATE
	VALUES (8388610,
	8388624,
	8388610,
	'Verifying Rel with Instance',
	2,
	0);
INSERT INTO SM_CH
	VALUES (8388610,
	8388609,
	8388624,
	8388609,
	'');
INSERT INTO SM_SEME
	VALUES (8388610,
	8388609,
	8388624,
	8388609);
INSERT INTO SM_CH
	VALUES (8388610,
	8388610,
	8388624,
	8388610,
	'');
INSERT INTO SM_SEME
	VALUES (8388610,
	8388610,
	8388624,
	8388610);
INSERT INTO SM_CH
	VALUES (8388610,
	8388611,
	8388624,
	8388609,
	'');
INSERT INTO SM_SEME
	VALUES (8388610,
	8388611,
	8388624,
	8388609);
INSERT INTO SM_STATE
	VALUES (8388611,
	8388624,
	8388609,
	'Finishing IX2 Test',
	3,
	0);
INSERT INTO SM_CH
	VALUES (8388611,
	8388609,
	8388624,
	8388609,
	'');
INSERT INTO SM_SEME
	VALUES (8388611,
	8388609,
	8388624,
	8388609);
INSERT INTO SM_CH
	VALUES (8388611,
	8388610,
	8388624,
	8388610,
	'');
INSERT INTO SM_SEME
	VALUES (8388611,
	8388610,
	8388624,
	8388610);
INSERT INTO SM_CH
	VALUES (8388611,
	8388611,
	8388624,
	8388609,
	'');
INSERT INTO SM_SEME
	VALUES (8388611,
	8388611,
	8388624,
	8388609);
INSERT INTO SM_NSTXN
	VALUES (8388609,
	8388624,
	8388609,
	8388611,
	8388609);
INSERT INTO SM_TXN
	VALUES (8388609,
	8388624,
	8388611,
	8388609);
INSERT INTO SM_NSTXN
	VALUES (8388610,
	8388624,
	8388609,
	8388609,
	8388609);
INSERT INTO SM_TXN
	VALUES (8388610,
	8388624,
	8388609,
	8388609);
INSERT INTO SM_NSTXN
	VALUES (8388611,
	8388624,
	8388609,
	8388610,
	8388610);
INSERT INTO SM_TXN
	VALUES (8388611,
	8388624,
	8388610,
	8388610);
INSERT INTO SM_MOAH
	VALUES (8388609,
	8388624,
	8388609);
INSERT INTO SM_AH
	VALUES (8388609,
	8388624);
INSERT INTO SM_ACT
	VALUES (8388609,
	8388624,
	1,
	'create object instance inst1 of OL_USER;
assign self.r_id = "id3";
assign inst1.id = "id4";
relate self to inst1 across R8;
assign inst1.i = 32;
assign self.i = 32;

// select one one_inst related by self-><rel_obj>[REL]
select one ol related by self->OL_USER[R8];
if (ol.i == 32)
  LOG::LogSuccess(message:"IX2: OR_USER - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_USER - select one inst related by   self-><rel_obj>[REL]") ;
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select one oner related by self->OL_USER[R8]->OR_USER[R8];
if (oner.i == self.i)
  LOG::LogSuccess(message:"IX2: OR_USER - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_USER - select one inst related by   self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OL_USER2:''Verify Rel with Instance''(id:self.r_id) to ol;',
	'');
INSERT INTO SM_MOAH
	VALUES (8388610,
	8388624,
	8388610);
INSERT INTO SM_AH
	VALUES (8388610,
	8388624);
INSERT INTO SM_ACT
	VALUES (8388610,
	8388624,
	1,
	'select one ol related by self->OL_USER[R8];
if (ol.id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OR_USER - Verifying Rel with Instance") ;
  generate OL_USER3:''Finish IX2 Test''() to ol;
else 
  LOG::LogFailure(message:"IX2: OR_USER - Verifying Rel with Instance") ;
end if;',
	'');
INSERT INTO SM_MOAH
	VALUES (8388611,
	8388624,
	8388611);
INSERT INTO SM_AH
	VALUES (8388611,
	8388624);
INSERT INTO SM_ACT
	VALUES (8388611,
	8388624,
	1,
	'// check delete
select one ol related by self->OL_USER[R8];
unrelate ol from self across R8;
delete object instance ol;
select one ol related by self->OL_USER[R8];
if (empty ol)
  LOG::LogSuccess(message:"IX2: OR_USER - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_USER - delete object instance <rel_inst>") ;
end if;

//check unrelate
create object instance ol1 of OL_USER;
assign ol1.id = "id33";
relate self to ol1 across R8;
unrelate self from ol1 across R8;
select one ol2 related by self->OL_USER[R8];
if (empty ol2)
  LOG::LogSuccess(message:"IX2: OR_USER - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_USER - unrelate object instance <rel_inst>") ;
end if;
delete object instance ol1;

',
	'');
INSERT INTO GD_MD
	VALUES (8388609,
	8,
	8388624,
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
	VALUES (8388610,
	8388609,
	8388609,
	41);
INSERT INTO GD_SHP
	VALUES (8388610,
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (8388611,
	8388609,
	8388610,
	41);
INSERT INTO GD_SHP
	VALUES (8388611,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (8388612,
	8388609,
	8388611,
	41);
INSERT INTO GD_SHP
	VALUES (8388612,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (8388613,
	8388609,
	8388610,
	42);
INSERT INTO GD_CON
	VALUES (8388613,
	8388610,
	8388610,
	0);
INSERT INTO GD_CTXT
	VALUES (8388613,
	0,
	0,
	0,
	0,
	0,
	0,
	1680,
	1200,
	1894,
	1229,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (8388614,
	8388613,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (8388615,
	8388613,
	1664,
	1312,
	1664,
	1232,
	8388614);
INSERT INTO GD_LS
	VALUES (8388616,
	8388613,
	1664,
	1232,
	1792,
	1232,
	8388615);
INSERT INTO GD_LS
	VALUES (8388617,
	8388613,
	1792,
	1232,
	1792,
	1280,
	8388616);
INSERT INTO GD_GE
	VALUES (8388618,
	8388609,
	8388611,
	42);
INSERT INTO GD_CON
	VALUES (8388618,
	8388610,
	8388611,
	0);
INSERT INTO GD_CTXT
	VALUES (8388618,
	0,
	0,
	0,
	0,
	0,
	0,
	2048,
	1216,
	2252,
	1254,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (8388619,
	8388618,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (8388620,
	8388618,
	2016,
	1248,
	2128,
	1248,
	8388619);
INSERT INTO GD_LS
	VALUES (8388621,
	8388618,
	2128,
	1248,
	2128,
	1280,
	8388620);
INSERT INTO GD_GE
	VALUES (8388622,
	8388609,
	8388609,
	42);
INSERT INTO GD_CON
	VALUES (8388622,
	8388610,
	8388612,
	0);
INSERT INTO GD_CTXT
	VALUES (8388622,
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
	VALUES (8388623,
	8388622,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (10485776,
	'imx driver',
	18,
	'IMX_DRV',
	'',
	10485780);
INSERT INTO O_NBATTR
	VALUES (10485814,
	10485776);
INSERT INTO O_BATTR
	VALUES (10485814,
	10485776);
INSERT INTO O_ATTR
	VALUES (10485814,
	10485776,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (10485815,
	10485776);
INSERT INTO O_BATTR
	VALUES (10485815,
	10485776);
INSERT INTO O_ATTR
	VALUES (10485815,
	10485776,
	10485814,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	10485776);
INSERT INTO O_OIDA
	VALUES (10485814,
	10485776,
	0);
INSERT INTO SM_ISM
	VALUES (8912913,
	10485776);
INSERT INTO SM_SM
	VALUES (8912913,
	'',
	17);
INSERT INTO SM_MOORE
	VALUES (8912913);
INSERT INTO SM_SUPDT
	VALUES (8912897,
	8912913,
	0);
INSERT INTO SM_STATE
	VALUES (8912897,
	8912913,
	8912897,
	'Initializing Timers for all Runs',
	1,
	0);
INSERT INTO SM_LEVT
	VALUES (8912897,
	8912913,
	8912897);
INSERT INTO SM_SEVT
	VALUES (8912897,
	8912913,
	8912897);
INSERT INTO SM_EVT
	VALUES (8912897,
	8912913,
	8912897,
	1,
	'Start IXC2 Test',
	0,
	'',
	'IMX_DRV1',
	'');
INSERT INTO SM_CH
	VALUES (8912897,
	8912897,
	8912913,
	8912897,
	'');
INSERT INTO SM_SEME
	VALUES (8912897,
	8912897,
	8912913,
	8912897);
INSERT INTO SM_LEVT
	VALUES (8912898,
	8912913,
	8912897);
INSERT INTO SM_SEVT
	VALUES (8912898,
	8912913,
	8912897);
INSERT INTO SM_EVT
	VALUES (8912898,
	8912913,
	8912897,
	3,
	'Start Int',
	0,
	'',
	'IMX_DRV3',
	'');
INSERT INTO SM_EIGN
	VALUES (8912897,
	8912898,
	8912913,
	8912897,
	'');
INSERT INTO SM_SEME
	VALUES (8912897,
	8912898,
	8912913,
	8912897);
INSERT INTO SM_LEVT
	VALUES (8912899,
	8912913,
	8912897);
INSERT INTO SM_SEVT
	VALUES (8912899,
	8912913,
	8912897);
INSERT INTO SM_EVT
	VALUES (8912899,
	8912913,
	8912897,
	4,
	'Start Str',
	0,
	'',
	'IMX_DRV4',
	'');
INSERT INTO SM_CH
	VALUES (8912897,
	8912899,
	8912913,
	8912897,
	'');
INSERT INTO SM_SEME
	VALUES (8912897,
	8912899,
	8912913,
	8912897);
INSERT INTO SM_LEVT
	VALUES (8912900,
	8912913,
	8912897);
INSERT INTO SM_SEVT
	VALUES (8912900,
	8912913,
	8912897);
INSERT INTO SM_EVT
	VALUES (8912900,
	8912913,
	8912897,
	5,
	'Start Enum',
	0,
	'',
	'IMX_DRV5',
	'');
INSERT INTO SM_CH
	VALUES (8912897,
	8912900,
	8912913,
	8912897,
	'');
INSERT INTO SM_SEME
	VALUES (8912897,
	8912900,
	8912913,
	8912897);
INSERT INTO SM_LEVT
	VALUES (8912901,
	8912913,
	8912897);
INSERT INTO SM_SEVT
	VALUES (8912901,
	8912913,
	8912897);
INSERT INTO SM_EVT
	VALUES (8912901,
	8912913,
	8912897,
	6,
	'Start Date',
	0,
	'',
	'IMX_DRV6',
	'');
INSERT INTO SM_CH
	VALUES (8912897,
	8912901,
	8912913,
	8912897,
	'');
INSERT INTO SM_SEME
	VALUES (8912897,
	8912901,
	8912913,
	8912897);
INSERT INTO SM_LEVT
	VALUES (8912902,
	8912913,
	8912897);
INSERT INTO SM_SEVT
	VALUES (8912902,
	8912913,
	8912897);
INSERT INTO SM_EVT
	VALUES (8912902,
	8912913,
	8912897,
	7,
	'Start Time',
	0,
	'',
	'IMX_DRV7',
	'');
INSERT INTO SM_CH
	VALUES (8912897,
	8912902,
	8912913,
	8912897,
	'');
INSERT INTO SM_SEME
	VALUES (8912897,
	8912902,
	8912913,
	8912897);
INSERT INTO SM_LEVT
	VALUES (8912903,
	8912913,
	8912897);
INSERT INTO SM_SEVT
	VALUES (8912903,
	8912913,
	8912897);
INSERT INTO SM_EVT
	VALUES (8912903,
	8912913,
	8912897,
	8,
	'Start Bool',
	0,
	'',
	'IMX_DRV8',
	'');
INSERT INTO SM_CH
	VALUES (8912897,
	8912903,
	8912913,
	8912897,
	'');
INSERT INTO SM_SEME
	VALUES (8912897,
	8912903,
	8912913,
	8912897);
INSERT INTO SM_LEVT
	VALUES (8912904,
	8912913,
	8912897);
INSERT INTO SM_SEVT
	VALUES (8912904,
	8912913,
	8912897);
INSERT INTO SM_EVT
	VALUES (8912904,
	8912913,
	8912897,
	9,
	'Shut Down',
	0,
	'',
	'IMX_DRV9',
	'');
INSERT INTO SM_SEME
	VALUES (8912897,
	8912904,
	8912913,
	8912897);
INSERT INTO SM_STATE
	VALUES (8912898,
	8912913,
	8912897,
	'Shutting Down',
	8,
	0);
INSERT INTO SM_CH
	VALUES (8912898,
	8912897,
	8912913,
	8912897,
	'');
INSERT INTO SM_SEME
	VALUES (8912898,
	8912897,
	8912913,
	8912897);
INSERT INTO SM_CH
	VALUES (8912898,
	8912898,
	8912913,
	8912897,
	'');
INSERT INTO SM_SEME
	VALUES (8912898,
	8912898,
	8912913,
	8912897);
INSERT INTO SM_CH
	VALUES (8912898,
	8912899,
	8912913,
	8912897,
	'');
INSERT INTO SM_SEME
	VALUES (8912898,
	8912899,
	8912913,
	8912897);
INSERT INTO SM_CH
	VALUES (8912898,
	8912900,
	8912913,
	8912897,
	'');
INSERT INTO SM_SEME
	VALUES (8912898,
	8912900,
	8912913,
	8912897);
INSERT INTO SM_CH
	VALUES (8912898,
	8912901,
	8912913,
	8912897,
	'');
INSERT INTO SM_SEME
	VALUES (8912898,
	8912901,
	8912913,
	8912897);
INSERT INTO SM_CH
	VALUES (8912898,
	8912902,
	8912913,
	8912897,
	'');
INSERT INTO SM_SEME
	VALUES (8912898,
	8912902,
	8912913,
	8912897);
INSERT INTO SM_CH
	VALUES (8912898,
	8912903,
	8912913,
	8912897,
	'');
INSERT INTO SM_SEME
	VALUES (8912898,
	8912903,
	8912913,
	8912897);
INSERT INTO SM_CH
	VALUES (8912898,
	8912904,
	8912913,
	8912897,
	'');
INSERT INTO SM_SEME
	VALUES (8912898,
	8912904,
	8912913,
	8912897);
INSERT INTO SM_CRTXN
	VALUES (8912897,
	8912913,
	8912897,
	8912897);
INSERT INTO SM_TXN
	VALUES (8912897,
	8912913,
	8912897,
	8912897);
INSERT INTO SM_NSTXN
	VALUES (8912898,
	8912913,
	8912897,
	8912904,
	8912897);
INSERT INTO SM_TXN
	VALUES (8912898,
	8912913,
	8912898,
	8912897);
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
	'assign self.id = "IX2C Driver";
LOG::LogInfo(message:"IXC2 Driver - Staring IXC2 Test - 7 Tests at 2 second intervals");

// create all test instances
create object instance li of OL_INT;
create object instance ri of OR_INT;
create object instance ls of OL_STR;
create object instance rs of OR_STR;
create object instance le of OL_ENUM;
create object instance re of OR_ENUM;
create object instance lb of OL_BOOL;
// only 2 bools, not enough id attr''s
create object instance ld of OL_DATE;
create object instance rd of OR_DATE;
create object instance lt of OL_TIME;
create object instance rt of OR_TIME;
create object instance lu of OL_USER;
create object instance ru of OR_USER; 

create event instance elu of OL_USER1:''Start IXC2 Test''() to lu;
create event instance eru of OR_USER1:''Start IXC2 Test''() to ru;
create event instance eli of OL_INT1:''Start IXC2 Test''() to li;
create event instance eri of OR_INT1:''Start IXC2 Test''() to ri;
create event instance els of OL_STR1:''Start IXC2 Test''() to ls;
create event instance ers of OR_STR1:''Start IXC2 Test''() to rs;
create event instance ele of OL_ENUM1:''Start IXC2 Test''() to le;
create event instance ere of OR_ENUM1:''Start IXC2 Test''() to re;
create event instance eld of OL_DATE1:''Start IXC2 Test''() to ld;
create event instance erd of OR_DATE1:''Start IXC2 Test''() to rd;
create event instance elt of OL_TIME1:''Start IXC2 Test''() to lt;
create event instance ert of OR_TIME1:''Start IXC2 Test''() to rt;
create event instance elb of OL_BOOL1:''Start IXC2 Test''() to lb;

//set timers for all of these event pairs, 2 seconds apart.
bridge timer_ref = TIM::timer_start(microseconds:0,event_inst:elu);
bridge timer_ref = TIM::timer_start(microseconds:0,event_inst:eru);
bridge timer_ref = TIM::timer_start(microseconds:2000000,event_inst:eli);
bridge timer_ref = TIM::timer_start(microseconds:2000000,event_inst:eri);
bridge timer_ref = TIM::timer_start(microseconds:4000000,event_inst:els);
bridge timer_ref = TIM::timer_start(microseconds:4000000,event_inst:ers);
bridge timer_ref = TIM::timer_start(microseconds:6000000,event_inst:ele);
bridge timer_ref = TIM::timer_start(microseconds:6000000,event_inst:ere);
bridge timer_ref = TIM::timer_start(microseconds:8000000,event_inst:eld);
bridge timer_ref = TIM::timer_start(microseconds:8000000,event_inst:erd);
bridge timer_ref = TIM::timer_start(microseconds:10000000,event_inst:elt);
bridge timer_ref = TIM::timer_start(microseconds:10000000,event_inst:ert);
bridge timer_ref = TIM::timer_start(microseconds:12000000,event_inst:elb);

select any driver from instances of IMX_DRV;
create event instance to_self of IMX_DRV9:''Shut Down''() to driver;
bridge timer_ref = TIM::timer_start(microseconds:14000000,event_inst:to_self);

',
	'');
INSERT INTO SM_MOAH
	VALUES (8912898,
	8912913,
	8912898);
INSERT INTO SM_AH
	VALUES (8912898,
	8912913);
INSERT INTO SM_ACT
	VALUES (8912898,
	8912913,
	1,
	'LOG::LogInfo(message:"ICX_DRV: ICX2 Test Complete, Shutting Down") ;
bridge ARCH::shutdown();',
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
	4200,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (8912898,
	8912897,
	8912897,
	41);
INSERT INTO GD_SHP
	VALUES (8912898,
	1664,
	1264,
	1952,
	1344);
INSERT INTO GD_GE
	VALUES (8912899,
	8912897,
	8912898,
	41);
INSERT INTO GD_SHP
	VALUES (8912899,
	2048,
	1264,
	2320,
	1344);
INSERT INTO GD_GE
	VALUES (8912900,
	8912897,
	8912897,
	42);
INSERT INTO GD_CON
	VALUES (8912900,
	8912898,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (8912900,
	0,
	0,
	0,
	0,
	0,
	0,
	1830,
	1214,
	2086,
	1252,
	38,
	-11,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (8912901,
	8912900,
	1808,
	1264,
	1808,
	1216,
	0);
INSERT INTO GD_GE
	VALUES (8912902,
	8912897,
	8912898,
	42);
INSERT INTO GD_CON
	VALUES (8912902,
	8912898,
	8912899,
	0);
INSERT INTO GD_CTXT
	VALUES (8912902,
	0,
	0,
	0,
	0,
	0,
	0,
	1887,
	1339,
	2114,
	1378,
	-88,
	68,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (8912903,
	8912902,
	1952,
	1296,
	2048,
	1296,
	0);
INSERT INTO R_SIMP
	VALUES (10485761);
INSERT INTO R_REL
	VALUES (10485761,
	2,
	'',
	10485780);
INSERT INTO R_PART
	VALUES (10485762,
	10485761,
	10485761,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (10485762,
	10485761,
	10485761,
	0);
INSERT INTO R_OIR
	VALUES (10485762,
	10485761,
	10485761,
	0);
INSERT INTO R_FORM
	VALUES (10485763,
	10485761,
	10485762,
	0,
	0,
	'');
INSERT INTO R_RGO
	VALUES (10485763,
	10485761,
	10485762);
INSERT INTO R_OIR
	VALUES (10485763,
	10485761,
	10485762,
	0);
INSERT INTO R_SIMP
	VALUES (10485762);
INSERT INTO R_REL
	VALUES (10485762,
	3,
	'',
	10485780);
INSERT INTO R_PART
	VALUES (10485764,
	10485762,
	10485763,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (10485764,
	10485762,
	10485763,
	0);
INSERT INTO R_OIR
	VALUES (10485764,
	10485762,
	10485763,
	0);
INSERT INTO R_FORM
	VALUES (10485765,
	10485762,
	10485764,
	0,
	0,
	'');
INSERT INTO R_RGO
	VALUES (10485765,
	10485762,
	10485764);
INSERT INTO R_OIR
	VALUES (10485765,
	10485762,
	10485764,
	0);
INSERT INTO R_SIMP
	VALUES (10485763);
INSERT INTO R_REL
	VALUES (10485763,
	4,
	'',
	10485780);
INSERT INTO R_PART
	VALUES (10485766,
	10485763,
	10485765,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (10485766,
	10485763,
	10485765,
	0);
INSERT INTO R_OIR
	VALUES (10485766,
	10485763,
	10485765,
	0);
INSERT INTO R_FORM
	VALUES (10485767,
	10485763,
	10485766,
	0,
	0,
	'');
INSERT INTO R_RGO
	VALUES (10485767,
	10485763,
	10485766);
INSERT INTO R_OIR
	VALUES (10485767,
	10485763,
	10485766,
	0);
INSERT INTO R_SIMP
	VALUES (10485764);
INSERT INTO R_REL
	VALUES (10485764,
	5,
	'',
	10485780);
INSERT INTO R_PART
	VALUES (10485768,
	10485764,
	10485767,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (10485768,
	10485764,
	10485767,
	0);
INSERT INTO R_OIR
	VALUES (10485768,
	10485764,
	10485767,
	0);
INSERT INTO R_FORM
	VALUES (10485769,
	10485764,
	10485768,
	0,
	0,
	'');
INSERT INTO R_RGO
	VALUES (10485769,
	10485764,
	10485768);
INSERT INTO R_OIR
	VALUES (10485769,
	10485764,
	10485768,
	0);
INSERT INTO R_SIMP
	VALUES (10485765);
INSERT INTO R_REL
	VALUES (10485765,
	6,
	'',
	10485780);
INSERT INTO R_PART
	VALUES (10485770,
	10485765,
	10485769,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (10485770,
	10485765,
	10485769,
	0);
INSERT INTO R_OIR
	VALUES (10485770,
	10485765,
	10485769,
	0);
INSERT INTO R_FORM
	VALUES (10485771,
	10485765,
	10485770,
	0,
	0,
	'');
INSERT INTO R_RGO
	VALUES (10485771,
	10485765,
	10485770);
INSERT INTO R_OIR
	VALUES (10485771,
	10485765,
	10485770,
	0);
INSERT INTO R_SIMP
	VALUES (10485766);
INSERT INTO R_REL
	VALUES (10485766,
	7,
	'',
	10485780);
INSERT INTO R_PART
	VALUES (10485772,
	10485766,
	10485771,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (10485772,
	10485766,
	10485771,
	0);
INSERT INTO R_OIR
	VALUES (10485772,
	10485766,
	10485771,
	0);
INSERT INTO R_FORM
	VALUES (10485773,
	10485766,
	10485772,
	0,
	0,
	'');
INSERT INTO R_RGO
	VALUES (10485773,
	10485766,
	10485772);
INSERT INTO R_OIR
	VALUES (10485773,
	10485766,
	10485772,
	0);
INSERT INTO R_SIMP
	VALUES (10485767);
INSERT INTO R_REL
	VALUES (10485767,
	8,
	'',
	10485780);
INSERT INTO R_PART
	VALUES (10485774,
	10485767,
	10485773,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (10485774,
	10485767,
	10485773,
	0);
INSERT INTO R_OIR
	VALUES (10485774,
	10485767,
	10485773,
	0);
INSERT INTO R_FORM
	VALUES (10485775,
	10485767,
	10485774,
	0,
	0,
	'');
INSERT INTO R_RGO
	VALUES (10485775,
	10485767,
	10485774);
INSERT INTO R_OIR
	VALUES (10485775,
	10485767,
	10485774,
	0);
INSERT INTO GD_MD
	VALUES (10485774,
	5,
	10485780,
	11,
	1,
	0,
	1,
	1,
	0,
	12,
	1630,
	3962,
	0.746445,
	0);
INSERT INTO GD_GE
	VALUES (10485777,
	10485774,
	10485761,
	21);
INSERT INTO GD_SHP
	VALUES (10485777,
	1664,
	1216,
	1840,
	1328);
INSERT INTO GD_GE
	VALUES (10485778,
	10485774,
	10485762,
	21);
INSERT INTO GD_SHP
	VALUES (10485778,
	1712,
	1552,
	1872,
	1664);
INSERT INTO GD_GE
	VALUES (10485779,
	10485774,
	10485763,
	21);
INSERT INTO GD_SHP
	VALUES (10485779,
	2016,
	1552,
	2176,
	1696);
INSERT INTO GD_GE
	VALUES (10485780,
	10485774,
	10485764,
	21);
INSERT INTO GD_SHP
	VALUES (10485780,
	1712,
	1712,
	1872,
	1840);
INSERT INTO GD_GE
	VALUES (10485781,
	10485774,
	10485765,
	21);
INSERT INTO GD_SHP
	VALUES (10485781,
	2016,
	1712,
	2176,
	1840);
INSERT INTO GD_GE
	VALUES (10485782,
	10485774,
	10485766,
	21);
INSERT INTO GD_SHP
	VALUES (10485782,
	2240,
	1376,
	2384,
	1504);
INSERT INTO GD_GE
	VALUES (10485783,
	10485774,
	10485767,
	21);
INSERT INTO GD_SHP
	VALUES (10485783,
	2528,
	1376,
	2672,
	1504);
INSERT INTO GD_GE
	VALUES (10485784,
	10485774,
	10485768,
	21);
INSERT INTO GD_SHP
	VALUES (10485784,
	2240,
	1552,
	2384,
	1680);
INSERT INTO GD_GE
	VALUES (10485785,
	10485774,
	10485769,
	21);
INSERT INTO GD_SHP
	VALUES (10485785,
	2528,
	1552,
	2672,
	1680);
INSERT INTO GD_GE
	VALUES (10485786,
	10485774,
	10485770,
	21);
INSERT INTO GD_SHP
	VALUES (10485786,
	2240,
	1712,
	2384,
	1840);
INSERT INTO GD_GE
	VALUES (10485787,
	10485774,
	10485771,
	21);
INSERT INTO GD_SHP
	VALUES (10485787,
	2528,
	1712,
	2672,
	1856);
INSERT INTO GD_GE
	VALUES (10485788,
	10485774,
	10485772,
	21);
INSERT INTO GD_SHP
	VALUES (10485788,
	1712,
	1872,
	1872,
	2000);
INSERT INTO GD_GE
	VALUES (10485789,
	10485774,
	10485773,
	21);
INSERT INTO GD_SHP
	VALUES (10485789,
	2016,
	1872,
	2176,
	2000);
INSERT INTO GD_GE
	VALUES (10485790,
	10485774,
	10485774,
	21);
INSERT INTO GD_SHP
	VALUES (10485790,
	2240,
	1872,
	2384,
	2016);
INSERT INTO GD_GE
	VALUES (10485791,
	10485774,
	10485775,
	21);
INSERT INTO GD_SHP
	VALUES (10485791,
	2528,
	1872,
	2672,
	2016);
INSERT INTO GD_GE
	VALUES (10485792,
	10485774,
	10485776,
	21);
INSERT INTO GD_SHP
	VALUES (10485792,
	1888,
	1216,
	2064,
	1328);
INSERT INTO GD_GE
	VALUES (10485841,
	10485774,
	10485761,
	24);
INSERT INTO GD_CON
	VALUES (10485841,
	10485778,
	10485779,
	0);
INSERT INTO GD_CTXT
	VALUES (10485841,
	0,
	0,
	0,
	0,
	0,
	0,
	1920,
	1568,
	1970,
	1598,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (10485842,
	10485841,
	1872,
	1600,
	2016,
	1600,
	0);
INSERT INTO GD_GE
	VALUES (10485843,
	10485774,
	10485762,
	24);
INSERT INTO GD_CON
	VALUES (10485843,
	10485780,
	10485781,
	0);
INSERT INTO GD_CTXT
	VALUES (10485843,
	0,
	0,
	0,
	0,
	0,
	0,
	1920,
	1728,
	1970,
	1758,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (10485844,
	10485843,
	1872,
	1760,
	2016,
	1760,
	0);
INSERT INTO GD_GE
	VALUES (10485845,
	10485774,
	10485763,
	24);
INSERT INTO GD_CON
	VALUES (10485845,
	10485782,
	10485783,
	0);
INSERT INTO GD_CTXT
	VALUES (10485845,
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
	VALUES (10485846,
	10485845,
	2384,
	1440,
	2528,
	1440,
	0);
INSERT INTO GD_GE
	VALUES (10485847,
	10485774,
	10485764,
	24);
INSERT INTO GD_CON
	VALUES (10485847,
	10485784,
	10485785,
	0);
INSERT INTO GD_CTXT
	VALUES (10485847,
	0,
	0,
	0,
	0,
	0,
	0,
	2432,
	1568,
	2482,
	1598,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (10485848,
	10485847,
	2384,
	1600,
	2528,
	1600,
	0);
INSERT INTO GD_GE
	VALUES (10485849,
	10485774,
	10485765,
	24);
INSERT INTO GD_CON
	VALUES (10485849,
	10485786,
	10485787,
	0);
INSERT INTO GD_CTXT
	VALUES (10485849,
	0,
	0,
	0,
	0,
	0,
	0,
	2432,
	1744,
	2482,
	1774,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (10485850,
	10485849,
	2384,
	1776,
	2528,
	1776,
	0);
INSERT INTO GD_GE
	VALUES (10485851,
	10485774,
	10485766,
	24);
INSERT INTO GD_CON
	VALUES (10485851,
	10485788,
	10485789,
	0);
INSERT INTO GD_CTXT
	VALUES (10485851,
	0,
	0,
	0,
	0,
	0,
	0,
	1920,
	1888,
	1970,
	1918,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (10485852,
	10485851,
	1872,
	1920,
	2016,
	1920,
	0);
INSERT INTO GD_GE
	VALUES (10485853,
	10485774,
	10485767,
	24);
INSERT INTO GD_CON
	VALUES (10485853,
	10485790,
	10485791,
	0);
INSERT INTO GD_CTXT
	VALUES (10485853,
	0,
	0,
	0,
	0,
	0,
	0,
	2432,
	1904,
	2482,
	1934,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (10485854,
	10485853,
	2384,
	1936,
	2528,
	1936,
	0);
INSERT INTO GD_MD
	VALUES (10485775,
	6,
	10485780,
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
	VALUES (10485793,
	10485775,
	10485761,
	21);
INSERT INTO GD_SHP
	VALUES (10485793,
	1712,
	1248,
	1904,
	1312);
INSERT INTO GD_GE
	VALUES (10485794,
	10485775,
	10485762,
	21);
INSERT INTO GD_SHP
	VALUES (10485794,
	1712,
	1552,
	1904,
	1616);
INSERT INTO GD_GE
	VALUES (10485795,
	10485775,
	10485763,
	21);
INSERT INTO GD_SHP
	VALUES (10485795,
	2016,
	1552,
	2208,
	1616);
INSERT INTO GD_GE
	VALUES (10485796,
	10485775,
	10485764,
	21);
INSERT INTO GD_SHP
	VALUES (10485796,
	1712,
	1712,
	1904,
	1776);
INSERT INTO GD_GE
	VALUES (10485797,
	10485775,
	10485765,
	21);
INSERT INTO GD_SHP
	VALUES (10485797,
	2016,
	1712,
	2208,
	1776);
INSERT INTO GD_GE
	VALUES (10485798,
	10485775,
	10485767,
	21);
INSERT INTO GD_SHP
	VALUES (10485798,
	2528,
	1376,
	2720,
	1440);
INSERT INTO GD_GE
	VALUES (10485799,
	10485775,
	10485768,
	21);
INSERT INTO GD_SHP
	VALUES (10485799,
	2240,
	1552,
	2432,
	1616);
INSERT INTO GD_GE
	VALUES (10485800,
	10485775,
	10485769,
	21);
INSERT INTO GD_SHP
	VALUES (10485800,
	2528,
	1552,
	2720,
	1616);
INSERT INTO GD_GE
	VALUES (10485801,
	10485775,
	10485770,
	21);
INSERT INTO GD_SHP
	VALUES (10485801,
	2240,
	1712,
	2432,
	1776);
INSERT INTO GD_GE
	VALUES (10485802,
	10485775,
	10485771,
	21);
INSERT INTO GD_SHP
	VALUES (10485802,
	2528,
	1712,
	2720,
	1776);
INSERT INTO GD_GE
	VALUES (10485803,
	10485775,
	10485772,
	21);
INSERT INTO GD_SHP
	VALUES (10485803,
	1712,
	1888,
	1904,
	1952);
INSERT INTO GD_GE
	VALUES (10485804,
	10485775,
	10485773,
	21);
INSERT INTO GD_SHP
	VALUES (10485804,
	2016,
	1888,
	2208,
	1952);
INSERT INTO GD_GE
	VALUES (10485805,
	10485775,
	10485774,
	21);
INSERT INTO GD_SHP
	VALUES (10485805,
	2240,
	1872,
	2432,
	1936);
INSERT INTO GD_GE
	VALUES (10485806,
	10485775,
	10485775,
	21);
INSERT INTO GD_SHP
	VALUES (10485806,
	2528,
	1872,
	2720,
	1936);
INSERT INTO GD_GE
	VALUES (10485807,
	10485775,
	10485766,
	21);
INSERT INTO GD_SHP
	VALUES (10485807,
	2240,
	1376,
	2432,
	1440);
INSERT INTO GD_GE
	VALUES (10485808,
	10485775,
	10485776,
	21);
INSERT INTO GD_SHP
	VALUES (10485808,
	1888,
	1216,
	2080,
	1280);
INSERT INTO GD_MD
	VALUES (10485776,
	7,
	10485780,
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
	VALUES (10485809,
	10485776,
	10485761,
	21);
INSERT INTO GD_SHP
	VALUES (10485809,
	1712,
	1248,
	1904,
	1312);
INSERT INTO GD_GE
	VALUES (10485810,
	10485776,
	10485762,
	21);
INSERT INTO GD_SHP
	VALUES (10485810,
	1712,
	1552,
	1904,
	1616);
INSERT INTO GD_GE
	VALUES (10485811,
	10485776,
	10485763,
	21);
INSERT INTO GD_SHP
	VALUES (10485811,
	2016,
	1552,
	2208,
	1616);
INSERT INTO GD_GE
	VALUES (10485812,
	10485776,
	10485764,
	21);
INSERT INTO GD_SHP
	VALUES (10485812,
	1712,
	1712,
	1904,
	1776);
INSERT INTO GD_GE
	VALUES (10485813,
	10485776,
	10485765,
	21);
INSERT INTO GD_SHP
	VALUES (10485813,
	2016,
	1712,
	2208,
	1776);
INSERT INTO GD_GE
	VALUES (10485814,
	10485776,
	10485766,
	21);
INSERT INTO GD_SHP
	VALUES (10485814,
	2240,
	1376,
	2432,
	1440);
INSERT INTO GD_GE
	VALUES (10485815,
	10485776,
	10485767,
	21);
INSERT INTO GD_SHP
	VALUES (10485815,
	2528,
	1376,
	2720,
	1440);
INSERT INTO GD_GE
	VALUES (10485816,
	10485776,
	10485768,
	21);
INSERT INTO GD_SHP
	VALUES (10485816,
	2240,
	1552,
	2432,
	1616);
INSERT INTO GD_GE
	VALUES (10485817,
	10485776,
	10485769,
	21);
INSERT INTO GD_SHP
	VALUES (10485817,
	2528,
	1552,
	2720,
	1616);
INSERT INTO GD_GE
	VALUES (10485818,
	10485776,
	10485770,
	21);
INSERT INTO GD_SHP
	VALUES (10485818,
	2240,
	1712,
	2432,
	1776);
INSERT INTO GD_GE
	VALUES (10485819,
	10485776,
	10485771,
	21);
INSERT INTO GD_SHP
	VALUES (10485819,
	2528,
	1712,
	2720,
	1776);
INSERT INTO GD_GE
	VALUES (10485820,
	10485776,
	10485772,
	21);
INSERT INTO GD_SHP
	VALUES (10485820,
	1712,
	1888,
	1904,
	1952);
INSERT INTO GD_GE
	VALUES (10485821,
	10485776,
	10485773,
	21);
INSERT INTO GD_SHP
	VALUES (10485821,
	2016,
	1888,
	2208,
	1952);
INSERT INTO GD_GE
	VALUES (10485822,
	10485776,
	10485774,
	21);
INSERT INTO GD_SHP
	VALUES (10485822,
	2240,
	1872,
	2432,
	1936);
INSERT INTO GD_GE
	VALUES (10485823,
	10485776,
	10485775,
	21);
INSERT INTO GD_SHP
	VALUES (10485823,
	2528,
	1872,
	2720,
	1936);
INSERT INTO GD_GE
	VALUES (10485824,
	10485776,
	10485776,
	21);
INSERT INTO GD_SHP
	VALUES (10485824,
	1888,
	1216,
	2080,
	1280);
