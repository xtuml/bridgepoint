-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (3108914,
	'wimx',
	'This domain tests the abliity of a Model Compiler to have classes with identifying attributes of the folllowing types:
 - Integer
 - String
 - Enum (old style)
 - Boolean
 - Date
 - Timestamp
 - User Defined Type (string)

For each of the above types two classes are specified with a 1:M relationship between them.  Instances of each class are created and events are generated to each instance.

The state charts of each instance then create, select and delete classes across their common relationship.
',
	0,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	3108914,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	3108914,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	3108914,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	3108914,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	3108914,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	3108914,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	3108914,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	3108914,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	3108914,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	3108914,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	3108914,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	3108914,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	3108914,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	3108914,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	3108914,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	3108914,
	'inst_ref<Timer>',
	'');
INSERT INTO S_UDT
	VALUES (524305,
	524293,
	0);
INSERT INTO S_DT
	VALUES (524305,
	3108914,
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
	3108914,
	'my_string',
	'');
INSERT INTO S_EE
	VALUES (524293,
	'Logging',
	'',
	'LOG',
	3108914);
INSERT INTO S_BRG
	VALUES (524320,
	524293,
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
	524293,
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
	524293,
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
INSERT INTO S_EE
	VALUES (524294,
	'Time',
	'',
	'TIM',
	3108914);
INSERT INTO S_BRG
	VALUES (524323,
	524294,
	'current_date',
	'',
	1,
	524302,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524324,
	524294,
	'create_date',
	'',
	1,
	524302,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524336,
	524324,
	'second',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524337,
	524324,
	'minute',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524338,
	524324,
	'hour',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524339,
	524324,
	'day',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524340,
	524324,
	'month',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524341,
	524324,
	'year',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524325,
	524294,
	'get_second',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524342,
	524325,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524326,
	524294,
	'get_minute',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524343,
	524326,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524327,
	524294,
	'get_hour',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524344,
	524327,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524328,
	524294,
	'get_day',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524345,
	524328,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524329,
	524294,
	'get_month',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524346,
	524329,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524330,
	524294,
	'get_year',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524347,
	524330,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524331,
	524294,
	'current_clock',
	'',
	1,
	524303,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524332,
	524294,
	'timer_start',
	'',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524348,
	524332,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524349,
	524332,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524333,
	524294,
	'timer_start_recurring',
	'',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524350,
	524333,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524351,
	524333,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524334,
	524294,
	'timer_remaining_time',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524352,
	524334,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BRG
	VALUES (524335,
	524294,
	'timer_reset_time',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524353,
	524335,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524354,
	524335,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524336,
	524294,
	'timer_add_time',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524355,
	524336,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524356,
	524336,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524337,
	524294,
	'timer_cancel',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524357,
	524337,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_EE
	VALUES (524295,
	'Architecture',
	'',
	'ARCH',
	3108914);
INSERT INTO S_BRG
	VALUES (524338,
	524295,
	'shutdown',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO GD_MD
	VALUES (524289,
	1,
	3108914,
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
	VALUES (524465,
	524289,
	15728670,
	11);
INSERT INTO GD_SHP
	VALUES (524465,
	1920,
	1344,
	2080,
	1440);
INSERT INTO GD_GE
	VALUES (524413,
	524289,
	524293,
	12);
INSERT INTO GD_SHP
	VALUES (524413,
	2128,
	1520,
	2288,
	1616);
INSERT INTO GD_GE
	VALUES (524466,
	524289,
	524294,
	12);
INSERT INTO GD_SHP
	VALUES (524466,
	1920,
	1520,
	2080,
	1616);
INSERT INTO GD_GE
	VALUES (524467,
	524289,
	524295,
	12);
INSERT INTO GD_SHP
	VALUES (524467,
	1712,
	1520,
	1872,
	1616);
INSERT INTO S_SS
	VALUES (15728670,
	'wimx',
	'',
	'',
	1,
	3108914,
	15728670);
INSERT INTO O_OBJ
	VALUES (15728655,
	'init',
	1,
	'INIT',
	'',
	15728670);
INSERT INTO O_NBATTR
	VALUES (15728684,
	15728655);
INSERT INTO O_BATTR
	VALUES (15728684,
	15728655);
INSERT INTO O_ATTR
	VALUES (15728684,
	15728655,
	0,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_NBATTR
	VALUES (15728685,
	15728655);
INSERT INTO O_BATTR
	VALUES (15728685,
	15728655);
INSERT INTO O_ATTR
	VALUES (15728685,
	15728655,
	15728684,
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_ID
	VALUES (0,
	15728655);
INSERT INTO O_OIDA
	VALUES (15728685,
	15728655,
	0);
INSERT INTO SM_ISM
	VALUES (6815757,
	15728655);
INSERT INTO SM_SM
	VALUES (6815757,
	'',
	13);
INSERT INTO SM_MOORE
	VALUES (6815757);
INSERT INTO SM_LEVT
	VALUES (6815745,
	6815757,
	0);
INSERT INTO SM_SEVT
	VALUES (6815745,
	6815757,
	0);
INSERT INTO SM_EVT
	VALUES (6815745,
	6815757,
	0,
	1,
	'initialize',
	0,
	'',
	'INIT1',
	'');
INSERT INTO SM_STATE
	VALUES (6815745,
	6815757,
	0,
	'IMX Init',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (6815745,
	6815745,
	6815757,
	0);
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
	'generate WIMX_DRV1:''Start IXC2 Test''() to WIMX_DRV creator;',
	'');
INSERT INTO SM_NSTXN
	VALUES (6815745,
	6815757,
	6815745,
	6815745,
	0);
INSERT INTO SM_TXN
	VALUES (6815745,
	6815757,
	6815745,
	0);
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
	1728,
	1440,
	2192,
	1632);
INSERT INTO GD_GE
	VALUES (6815747,
	6815745,
	6815745,
	42);
INSERT INTO GD_CON
	VALUES (6815747,
	6815746,
	6815746,
	0);
INSERT INTO GD_CTXT
	VALUES (6815747,
	0,
	0,
	0,
	0,
	0,
	0,
	2252,
	1407,
	2399,
	1441,
	12,
	38,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815748,
	6815747,
	2192,
	1520,
	2256,
	1520,
	0);
INSERT INTO GD_LS
	VALUES (6815749,
	6815747,
	2256,
	1520,
	2256,
	1376,
	6815748);
INSERT INTO GD_LS
	VALUES (6815750,
	6815747,
	2256,
	1376,
	2128,
	1376,
	6815749);
INSERT INTO GD_LS
	VALUES (6815751,
	6815747,
	2128,
	1376,
	2128,
	1440,
	6815750);
INSERT INTO O_OBJ
	VALUES (15728656,
	'Object with Int Id Attr L Side',
	4,
	'OL_INT',
	'',
	15728670);
INSERT INTO O_NBATTR
	VALUES (15728686,
	15728656);
INSERT INTO O_BATTR
	VALUES (15728686,
	15728656);
INSERT INTO O_ATTR
	VALUES (15728686,
	15728656,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (15728687,
	15728656);
INSERT INTO O_BATTR
	VALUES (15728687,
	15728656);
INSERT INTO O_ATTR
	VALUES (15728687,
	15728656,
	15728686,
	'id1',
	'',
	'',
	'id1',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (15728688,
	15728656);
INSERT INTO O_BATTR
	VALUES (15728688,
	15728656);
INSERT INTO O_ATTR
	VALUES (15728688,
	15728656,
	15728687,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_NBATTR
	VALUES (15728689,
	15728656);
INSERT INTO O_BATTR
	VALUES (15728689,
	15728656);
INSERT INTO O_ATTR
	VALUES (15728689,
	15728656,
	15728688,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	15728656);
INSERT INTO O_OIDA
	VALUES (15728686,
	15728656,
	0);
INSERT INTO O_RTIDA
	VALUES (15728686,
	15728656,
	0,
	15728646,
	15728656);
INSERT INTO O_OIDA
	VALUES (15728687,
	15728656,
	0);
INSERT INTO O_RTIDA
	VALUES (15728687,
	15728656,
	0,
	15728646,
	15728656);
INSERT INTO SM_ISM
	VALUES (7340046,
	15728656);
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
	524291);
INSERT INTO SM_LEVT
	VALUES (7340033,
	7340046,
	0);
INSERT INTO SM_SEVT
	VALUES (7340033,
	7340046,
	0);
INSERT INTO SM_EVT
	VALUES (7340033,
	7340046,
	0,
	1,
	'Start IXC2 Test',
	0,
	'',
	'OL_INT1',
	'');
INSERT INTO SM_LEVT
	VALUES (7340034,
	7340046,
	0);
INSERT INTO SM_SEVT
	VALUES (7340034,
	7340046,
	0);
INSERT INTO SM_EVT
	VALUES (7340034,
	7340046,
	0,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OL_INT2',
	'');
INSERT INTO SM_LEVT
	VALUES (7340035,
	7340046,
	0);
INSERT INTO SM_SEVT
	VALUES (7340035,
	7340046,
	0);
INSERT INTO SM_EVT
	VALUES (7340035,
	7340046,
	0,
	3,
	'Finish IX2 Test',
	0,
	'',
	'OL_INT3',
	'');
INSERT INTO SM_STATE
	VALUES (7340033,
	7340046,
	0,
	'Starting IX2 Test',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (7340033,
	7340033,
	7340046,
	0);
INSERT INTO SM_SEME
	VALUES (7340033,
	7340034,
	7340046,
	0);
INSERT INTO SM_SEME
	VALUES (7340033,
	7340035,
	7340046,
	0);
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
	'assign self.id = 11;
assign self.id1=10;
assign self.i = 92;

create object instance inst1 of OR_INT;
assign inst1.r_id = 1;
assign inst1.i = 1;
relate self to inst1 across R2;

create object instance inst2 of OR_INT;
assign inst2.r_id = 2;
assign inst2.i = 1;
relate self to inst2 across R2;

create object instance inst3 of OR_INT;
assign inst3.r_id = 3;
assign inst3.i = 2;
relate self to inst3 across R2;

create object instance inst4 of OR_INT;
assign inst4.r_id = 4;
assign inst4.i = 2;
relate self to inst4 across R2;

select any oner related by self->OR_INT[R2] where selected.i == 1;
if (oner.i == 1)
  LOG::LogSuccess(message:"IX2: OL_INT - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_INT - select one inst related by self-><rel_obj>[REL]") ;
end if;

select many oners related by self->OR_INT[R2] where selected.i == 2;
assign c = cardinality oners;
if (c == 2)
  LOG::LogSuccess(message:"IX2: OL_INT - select many inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_INT - select many inst related by self-><rel_obj>[REL]") ;
end if;

select any ol related by self->OR_INT[R2]->OL_INT[R2] where selected.i == 92;
if (ol.i == self.i)
  LOG::LogSuccess(message:"IX2: OL_INT - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_INT - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OR_INT2:''Verify Rel with Instance''(id:self.id) to oner;',
	'');
INSERT INTO SM_STATE
	VALUES (7340034,
	7340046,
	0,
	'Verifying Rel with Instance',
	2,
	0);
INSERT INTO SM_CH
	VALUES (7340034,
	7340033,
	7340046,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (7340034,
	7340033,
	7340046,
	0);
INSERT INTO SM_CH
	VALUES (7340034,
	7340034,
	7340046,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (7340034,
	7340034,
	7340046,
	0);
INSERT INTO SM_CH
	VALUES (7340034,
	7340035,
	7340046,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (7340034,
	7340035,
	7340046,
	0);
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
	'select any oner related by self->OR_INT[R2] where selected.r_id == rcvd_evt.id;
if (oner.r_id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OL_INT - Verifying Rel with Instance") ;
  generate OR_INT3:''Finish IX2 Test''() to oner;
else 
  LOG::LogFailure(message:"IX2: OL_INT - Verifying Rel with Instance") ;
end if;',
	'');
INSERT INTO SM_STATE
	VALUES (7340035,
	7340046,
	0,
	'Finishing IX2 Test',
	3,
	0);
INSERT INTO SM_CH
	VALUES (7340035,
	7340033,
	7340046,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (7340035,
	7340033,
	7340046,
	0);
INSERT INTO SM_CH
	VALUES (7340035,
	7340034,
	7340046,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (7340035,
	7340034,
	7340046,
	0);
INSERT INTO SM_CH
	VALUES (7340035,
	7340035,
	7340046,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (7340035,
	7340035,
	7340046,
	0);
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
select any oner related by self->OR_INT[R2] where selected.i == 1;
found_id = oner.r_id;
unrelate oner from self across R2;
delete object instance oner;
select any oner related by self->OR_INT[R2] where selected.r_id == found_id;
if (empty oner)
  LOG::LogSuccess(message:"IX2: OL_INT - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_INT - delete object instance <rel_inst>") ;
end if;

//check unrelate
select any or1 related by self->OR_INT[R2] where selected.i == 1;
unrelate self from or1 across R2;
select any or2 related by self->OR_INT[R2] where selected.i == 1;
if (empty or2)
  LOG::LogSuccess(message:"IX2: OL_INT - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_INT - unrelate object instance <rel_inst>") ;
end if;
delete object instance or1;
',
	'');
INSERT INTO SM_NSTXN
	VALUES (7340033,
	7340046,
	7340033,
	7340035,
	0);
INSERT INTO SM_TXN
	VALUES (7340033,
	7340046,
	7340035,
	0);
INSERT INTO SM_NSTXN
	VALUES (7340034,
	7340046,
	7340033,
	7340033,
	0);
INSERT INTO SM_TXN
	VALUES (7340034,
	7340046,
	7340033,
	0);
INSERT INTO SM_NSTXN
	VALUES (7340035,
	7340046,
	7340033,
	7340034,
	0);
INSERT INTO SM_TXN
	VALUES (7340035,
	7340046,
	7340034,
	0);
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
	VALUES (15728657,
	'Object with Int Id Attr R Side',
	5,
	'OR_INT',
	'',
	15728670);
INSERT INTO O_NBATTR
	VALUES (15728690,
	15728657);
INSERT INTO O_BATTR
	VALUES (15728690,
	15728657);
INSERT INTO O_ATTR
	VALUES (15728690,
	15728657,
	0,
	'r_id',
	'',
	'',
	'r_id',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (15728691,
	15728657);
INSERT INTO O_BATTR
	VALUES (15728691,
	15728657);
INSERT INTO O_ATTR
	VALUES (15728691,
	15728657,
	15728690,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_REF
	VALUES (15728657,
	15728656,
	0,
	15728686,
	15728646,
	15728657,
	15728656,
	15728692,
	15728651,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (15728692,
	15728657,
	15728686,
	15728656,
	1);
INSERT INTO O_ATTR
	VALUES (15728692,
	15728657,
	15728691,
	'id',
	'',
	'',
	'id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (15728693,
	15728657);
INSERT INTO O_BATTR
	VALUES (15728693,
	15728657);
INSERT INTO O_ATTR
	VALUES (15728693,
	15728657,
	15728692,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_REF
	VALUES (15728657,
	15728656,
	0,
	15728687,
	15728646,
	15728657,
	15728656,
	15728694,
	15728652,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (15728694,
	15728657,
	15728687,
	15728656,
	1);
INSERT INTO O_ATTR
	VALUES (15728694,
	15728657,
	15728693,
	'id1',
	'',
	'',
	'id1',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	15728657);
INSERT INTO O_OIDA
	VALUES (15728690,
	15728657,
	0);
INSERT INTO SM_ISM
	VALUES (7864335,
	15728657);
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
	524291);
INSERT INTO SM_LEVT
	VALUES (7864321,
	7864335,
	0);
INSERT INTO SM_SEVT
	VALUES (7864321,
	7864335,
	0);
INSERT INTO SM_EVT
	VALUES (7864321,
	7864335,
	0,
	1,
	'Start IXC2 Test',
	0,
	'',
	'OR_INT1',
	'');
INSERT INTO SM_LEVT
	VALUES (7864322,
	7864335,
	0);
INSERT INTO SM_SEVT
	VALUES (7864322,
	7864335,
	0);
INSERT INTO SM_EVT
	VALUES (7864322,
	7864335,
	0,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OR_INT2',
	'');
INSERT INTO SM_LEVT
	VALUES (7864323,
	7864335,
	0);
INSERT INTO SM_SEVT
	VALUES (7864323,
	7864335,
	0);
INSERT INTO SM_EVT
	VALUES (7864323,
	7864335,
	0,
	3,
	'Finish IX2 Test',
	0,
	'',
	'OR_INT3',
	'');
INSERT INTO SM_STATE
	VALUES (7864321,
	7864335,
	0,
	'Starting IX2 Test',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (7864321,
	7864321,
	7864335,
	0);
INSERT INTO SM_SEME
	VALUES (7864321,
	7864322,
	7864335,
	0);
INSERT INTO SM_SEME
	VALUES (7864321,
	7864323,
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
	'assign self.r_id = 3;
assign self.i = 72;

create object instance inst1 of OL_INT;
assign inst1.id = 1;
assign inst1.id1=11;
assign inst1.i = 34;

relate self to inst1 across R2;

// select one one_inst related by self-><rel_obj>[REL]
select one ol related by self->OL_INT[R2] where selected.i == 34;
if (ol.i == 34)
  LOG::LogSuccess(message:"IX2: OR_INT - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_INT - select one inst related by   self-><rel_obj>[REL]") ;
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select any oner related by self->OL_INT[R2]->OR_INT[R2] where selected.i == self.i;
if (oner.i == self.i)
  LOG::LogSuccess(message:"IX2: OR_INT - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_INT - select one inst related by   self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OL_INT2:''Verify Rel with Instance''(id:self.r_id) to ol;',
	'');
INSERT INTO SM_STATE
	VALUES (7864322,
	7864335,
	0,
	'Verifying Rel with Instance',
	2,
	0);
INSERT INTO SM_CH
	VALUES (7864322,
	7864321,
	7864335,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (7864322,
	7864321,
	7864335,
	0);
INSERT INTO SM_CH
	VALUES (7864322,
	7864322,
	7864335,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (7864322,
	7864322,
	7864335,
	0);
INSERT INTO SM_CH
	VALUES (7864322,
	7864323,
	7864335,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (7864322,
	7864323,
	7864335,
	0);
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
	'select one ol related by self->OL_INT[R2] where selected.id == rcvd_evt.id;
if (ol.id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OR_INT - Verifying Rel with Instance") ;
  generate OL_INT3:''Finish IX2 Test''() to ol;
else 
  LOG::LogFailure(message:"IX2: OR_INT - Verifying Rel with Instance") ;
end if;',
	'');
INSERT INTO SM_STATE
	VALUES (7864323,
	7864335,
	0,
	'Finishing IX2 Test',
	3,
	0);
INSERT INTO SM_CH
	VALUES (7864323,
	7864321,
	7864335,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (7864323,
	7864321,
	7864335,
	0);
INSERT INTO SM_CH
	VALUES (7864323,
	7864322,
	7864335,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (7864323,
	7864322,
	7864335,
	0);
INSERT INTO SM_CH
	VALUES (7864323,
	7864323,
	7864335,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (7864323,
	7864323,
	7864335,
	0);
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
select one ol related by self->OL_INT[R2] where selected.i == 34;
found_id = ol.id;
unrelate ol from self across R2;
delete object instance ol;
select one ol related by self->OL_INT[R2] where selected.id == found_id;
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
select one ol2 related by self->OL_INT[R2] where selected.id == 100;
if (empty ol2)
  LOG::LogSuccess(message:"IX2: OR_INT - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_INT - unrelate object instance <rel_inst>") ;
end if;
delete object instance ol1;

',
	'');
INSERT INTO SM_NSTXN
	VALUES (7864321,
	7864335,
	7864321,
	7864323,
	0);
INSERT INTO SM_TXN
	VALUES (7864321,
	7864335,
	7864323,
	0);
INSERT INTO SM_NSTXN
	VALUES (7864322,
	7864335,
	7864321,
	7864321,
	0);
INSERT INTO SM_TXN
	VALUES (7864322,
	7864335,
	7864321,
	0);
INSERT INTO SM_NSTXN
	VALUES (7864323,
	7864335,
	7864321,
	7864322,
	0);
INSERT INTO SM_TXN
	VALUES (7864323,
	7864335,
	7864322,
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
	VALUES (15728658,
	'Object with String Id Attr L Side',
	6,
	'OL_STR',
	'',
	15728670);
INSERT INTO O_NBATTR
	VALUES (15728695,
	15728658);
INSERT INTO O_BATTR
	VALUES (15728695,
	15728658);
INSERT INTO O_ATTR
	VALUES (15728695,
	15728658,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (15728696,
	15728658);
INSERT INTO O_BATTR
	VALUES (15728696,
	15728658);
INSERT INTO O_ATTR
	VALUES (15728696,
	15728658,
	15728695,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_NBATTR
	VALUES (15728697,
	15728658);
INSERT INTO O_BATTR
	VALUES (15728697,
	15728658);
INSERT INTO O_ATTR
	VALUES (15728697,
	15728658,
	15728696,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	15728658);
INSERT INTO O_OIDA
	VALUES (15728695,
	15728658,
	0);
INSERT INTO O_RTIDA
	VALUES (15728695,
	15728658,
	0,
	15728647,
	15728658);
INSERT INTO SM_ISM
	VALUES (8388624,
	15728658);
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
	524293);
INSERT INTO SM_LEVT
	VALUES (8388609,
	8388624,
	0);
INSERT INTO SM_SEVT
	VALUES (8388609,
	8388624,
	0);
INSERT INTO SM_EVT
	VALUES (8388609,
	8388624,
	0,
	1,
	'Start IXC2 Test',
	0,
	'',
	'OL_STR1',
	'');
INSERT INTO SM_LEVT
	VALUES (8388610,
	8388624,
	0);
INSERT INTO SM_SEVT
	VALUES (8388610,
	8388624,
	0);
INSERT INTO SM_EVT
	VALUES (8388610,
	8388624,
	0,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OL_STR2',
	'');
INSERT INTO SM_LEVT
	VALUES (8388611,
	8388624,
	0);
INSERT INTO SM_SEVT
	VALUES (8388611,
	8388624,
	0);
INSERT INTO SM_EVT
	VALUES (8388611,
	8388624,
	0,
	3,
	'Finish IX2 Test',
	0,
	'',
	'OL_STR3',
	'');
INSERT INTO SM_STATE
	VALUES (8388609,
	8388624,
	0,
	'Starting IX2 Test',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (8388609,
	8388609,
	8388624,
	0);
INSERT INTO SM_SEME
	VALUES (8388609,
	8388610,
	8388624,
	0);
INSERT INTO SM_SEME
	VALUES (8388609,
	8388611,
	8388624,
	0);
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
	'assign self.id = "id";
assign self.i = 42;

create object instance inst1 of OR_STR;
assign inst1.r_id = "id1";
assign inst1.i = 1;
relate self to inst1 across R3;

create object instance inst2 of OR_STR;
assign inst2.r_id = "id2";
assign inst2.i = 1;
relate self to inst2 across R3;

create object instance inst3 of OR_STR;
assign inst3.r_id = "id3";
assign inst3.i = 2;
relate self to inst3 across R3;

create object instance inst4 of OR_STR;
assign inst4.r_id = "id4";
assign inst4.i = 2;
relate self to inst4 across R3;

select any oner related by self->OR_STR[R3] where selected.i == 1;
if (oner.i == 1)
  LOG::LogSuccess(message:"IX2: OL_STR - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_STR - select one inst related by self-><rel_obj>[REL]") ;
end if;

select many oners related by self->OR_STR[R3] where selected.i == 2;
assign c = cardinality oners;
if (c == 2)
  LOG::LogSuccess(message:"IX2: OL_STR - select many inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_STR - select many inst related by self-><rel_obj>[REL]") ;
end if;

select any ol related by self->OR_STR[R3]->OL_STR[R3] where selected.i == self.i;
if (ol.i == self.i)
  LOG::LogSuccess(message:"IX2: OL_STR - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_STR - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OR_STR2:''Verify Rel with Instance''(id:self.id) to oner;',
	'');
INSERT INTO SM_STATE
	VALUES (8388610,
	8388624,
	0,
	'Verifying Rel with Instance',
	2,
	0);
INSERT INTO SM_CH
	VALUES (8388610,
	8388609,
	8388624,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (8388610,
	8388609,
	8388624,
	0);
INSERT INTO SM_CH
	VALUES (8388610,
	8388610,
	8388624,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (8388610,
	8388610,
	8388624,
	0);
INSERT INTO SM_CH
	VALUES (8388610,
	8388611,
	8388624,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (8388610,
	8388611,
	8388624,
	0);
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
	'select any oner related by self->OR_STR[R3] where selected.r_id == rcvd_evt.id;
if (oner.r_id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OL_STR - Verifying Rel with Instance") ;
  generate OR_STR3:''Finish IX2 Test''() to oner;
else 
  LOG::LogFailure(message:"IX2: OL_STR - Verifying Rel with Instance") ;
end if;',
	'');
INSERT INTO SM_STATE
	VALUES (8388611,
	8388624,
	0,
	'Finishing IX2 Test',
	3,
	0);
INSERT INTO SM_CH
	VALUES (8388611,
	8388609,
	8388624,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (8388611,
	8388609,
	8388624,
	0);
INSERT INTO SM_CH
	VALUES (8388611,
	8388610,
	8388624,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (8388611,
	8388610,
	8388624,
	0);
INSERT INTO SM_CH
	VALUES (8388611,
	8388611,
	8388624,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (8388611,
	8388611,
	8388624,
	0);
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
select any oner related by self->OR_STR[R3] where selected.i == 1;
found_id = oner.r_id;
unrelate oner from self across R3;
delete object instance oner;
select any oner related by self->OR_STR[R3] where selected.r_id == found_id;
if (empty oner)
  LOG::LogSuccess(message:"IX2: OL_STR - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_STR - delete object instance <rel_inst>") ;
end if;

//check unrelate
select any or1 related by self->OR_STR[R3] where selected.i == 1;
unrelate self from or1 across R3;
select any or2 related by self->OR_STR[R3] where selected.i == 1;
if (empty or2)
  LOG::LogSuccess(message:"IX2: OL_STR - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_STR - unrelate object instance <rel_inst>") ;
end if;
delete object instance or1;

',
	'');
INSERT INTO SM_NSTXN
	VALUES (8388609,
	8388624,
	8388609,
	8388611,
	0);
INSERT INTO SM_TXN
	VALUES (8388609,
	8388624,
	8388611,
	0);
INSERT INTO SM_NSTXN
	VALUES (8388610,
	8388624,
	8388609,
	8388609,
	0);
INSERT INTO SM_TXN
	VALUES (8388610,
	8388624,
	8388609,
	0);
INSERT INTO SM_NSTXN
	VALUES (8388611,
	8388624,
	8388609,
	8388610,
	0);
INSERT INTO SM_TXN
	VALUES (8388611,
	8388624,
	8388610,
	0);
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
	VALUES (15728659,
	'Object with String Id Attr R Side',
	7,
	'OR_STR',
	'',
	15728670);
INSERT INTO O_NBATTR
	VALUES (15728698,
	15728659);
INSERT INTO O_BATTR
	VALUES (15728698,
	15728659);
INSERT INTO O_ATTR
	VALUES (15728698,
	15728659,
	0,
	'r_id',
	'',
	'',
	'r_id',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (15728699,
	15728659);
INSERT INTO O_BATTR
	VALUES (15728699,
	15728659);
INSERT INTO O_ATTR
	VALUES (15728699,
	15728659,
	15728698,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_REF
	VALUES (15728659,
	15728658,
	0,
	15728695,
	15728647,
	15728659,
	15728658,
	15728700,
	15728653,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (15728700,
	15728659,
	15728695,
	15728658,
	1);
INSERT INTO O_ATTR
	VALUES (15728700,
	15728659,
	15728699,
	'id',
	'',
	'',
	'id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (15728701,
	15728659);
INSERT INTO O_BATTR
	VALUES (15728701,
	15728659);
INSERT INTO O_ATTR
	VALUES (15728701,
	15728659,
	15728700,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	15728659);
INSERT INTO O_OIDA
	VALUES (15728698,
	15728659,
	0);
INSERT INTO SM_ISM
	VALUES (8912913,
	15728659);
INSERT INTO SM_SM
	VALUES (8912913,
	'',
	17);
INSERT INTO SM_MOORE
	VALUES (8912913);
INSERT INTO SM_EVTDI
	VALUES (8912897,
	8912913,
	'id',
	'',
	524293);
INSERT INTO SM_LEVT
	VALUES (8912897,
	8912913,
	0);
INSERT INTO SM_SEVT
	VALUES (8912897,
	8912913,
	0);
INSERT INTO SM_EVT
	VALUES (8912897,
	8912913,
	0,
	1,
	'Start IXC2 Test',
	0,
	'',
	'OR_STR1',
	'');
INSERT INTO SM_LEVT
	VALUES (8912898,
	8912913,
	0);
INSERT INTO SM_SEVT
	VALUES (8912898,
	8912913,
	0);
INSERT INTO SM_EVT
	VALUES (8912898,
	8912913,
	0,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OR_STR2',
	'');
INSERT INTO SM_LEVT
	VALUES (8912899,
	8912913,
	0);
INSERT INTO SM_SEVT
	VALUES (8912899,
	8912913,
	0);
INSERT INTO SM_EVT
	VALUES (8912899,
	8912913,
	0,
	3,
	'Finish IX2 Test',
	0,
	'',
	'OR_STR3',
	'');
INSERT INTO SM_STATE
	VALUES (8912897,
	8912913,
	0,
	'Starting IX2 Test',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (8912897,
	8912897,
	8912913,
	0);
INSERT INTO SM_SEME
	VALUES (8912897,
	8912898,
	8912913,
	0);
INSERT INTO SM_SEME
	VALUES (8912897,
	8912899,
	8912913,
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
	'assign self.r_id = "id3";
assign self.i = 32;

create object instance inst1 of OL_STR;
assign inst1.id = "id123";
relate self to inst1 across R3;
assign inst1.i = 59;

// select one one_inst related by self-><rel_obj>[REL]
select one ol related by self->OL_STR[R3] where selected.i == 59;
if (ol.i == 59)
  LOG::LogSuccess(message:"IX2: OR_STR - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_STR - select one inst related by   self-><rel_obj>[REL]") ;
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select any oner related by self->OL_STR[R3]->OR_STR[R3] where selected.i == self.i;
if (oner.i == self.i)
  LOG::LogSuccess(message:"IX2: OR_STR - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_STR - select one inst related by   self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OL_STR2:''Verify Rel with Instance''(id:self.r_id) to ol;',
	'');
INSERT INTO SM_STATE
	VALUES (8912898,
	8912913,
	0,
	'Verifying Rel with Instance',
	2,
	0);
INSERT INTO SM_CH
	VALUES (8912898,
	8912897,
	8912913,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (8912898,
	8912897,
	8912913,
	0);
INSERT INTO SM_CH
	VALUES (8912898,
	8912898,
	8912913,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (8912898,
	8912898,
	8912913,
	0);
INSERT INTO SM_CH
	VALUES (8912898,
	8912899,
	8912913,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (8912898,
	8912899,
	8912913,
	0);
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
	'select one ol related by self->OL_STR[R3] where selected.id == rcvd_evt.id;
if (ol.id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OR_STR - Verifying Rel with Instance") ;
  generate OL_STR3:''Finish IX2 Test''() to ol;
else 
  LOG::LogFailure(message:"IX2: OR_STR - Verifying Rel with Instance") ;
end if;',
	'');
INSERT INTO SM_STATE
	VALUES (8912899,
	8912913,
	0,
	'Finishing IX2 Test',
	3,
	0);
INSERT INTO SM_CH
	VALUES (8912899,
	8912897,
	8912913,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (8912899,
	8912897,
	8912913,
	0);
INSERT INTO SM_CH
	VALUES (8912899,
	8912898,
	8912913,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (8912899,
	8912898,
	8912913,
	0);
INSERT INTO SM_CH
	VALUES (8912899,
	8912899,
	8912913,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (8912899,
	8912899,
	8912913,
	0);
INSERT INTO SM_MOAH
	VALUES (8912899,
	8912913,
	8912899);
INSERT INTO SM_AH
	VALUES (8912899,
	8912913);
INSERT INTO SM_ACT
	VALUES (8912899,
	8912913,
	1,
	'// check delete
select one ol related by self->OL_STR[R3] where selected.i == 59;
unrelate ol from self across R3;
delete object instance ol;
select one ol related by self->OL_STR[R3] where selected.i == 59;
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
select one ol2 related by self->OL_STR[R3] where selected.id == "id101";
if (empty ol2)
  LOG::LogSuccess(message:"IX2: OR_STR - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_STR - unrelate object instance <rel_inst>") ;
end if;
delete object instance ol1;

',
	'');
INSERT INTO SM_NSTXN
	VALUES (8912897,
	8912913,
	8912897,
	8912899,
	0);
INSERT INTO SM_TXN
	VALUES (8912897,
	8912913,
	8912899,
	0);
INSERT INTO SM_NSTXN
	VALUES (8912898,
	8912913,
	8912897,
	8912897,
	0);
INSERT INTO SM_TXN
	VALUES (8912898,
	8912913,
	8912897,
	0);
INSERT INTO SM_NSTXN
	VALUES (8912899,
	8912913,
	8912897,
	8912898,
	0);
INSERT INTO SM_TXN
	VALUES (8912899,
	8912913,
	8912898,
	0);
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
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (8912899,
	8912897,
	8912898,
	41);
INSERT INTO GD_SHP
	VALUES (8912899,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (8912900,
	8912897,
	8912899,
	41);
INSERT INTO GD_SHP
	VALUES (8912900,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (8912901,
	8912897,
	8912898,
	42);
INSERT INTO GD_CON
	VALUES (8912901,
	8912898,
	8912898,
	0);
INSERT INTO GD_CTXT
	VALUES (8912901,
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
	VALUES (8912902,
	8912901,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (8912903,
	8912901,
	1664,
	1312,
	1664,
	1232,
	8912902);
INSERT INTO GD_LS
	VALUES (8912904,
	8912901,
	1664,
	1232,
	1792,
	1232,
	8912903);
INSERT INTO GD_LS
	VALUES (8912905,
	8912901,
	1792,
	1232,
	1792,
	1280,
	8912904);
INSERT INTO GD_GE
	VALUES (8912906,
	8912897,
	8912899,
	42);
INSERT INTO GD_CON
	VALUES (8912906,
	8912898,
	8912899,
	0);
INSERT INTO GD_CTXT
	VALUES (8912906,
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
	VALUES (8912907,
	8912906,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (8912908,
	8912906,
	2016,
	1248,
	2128,
	1248,
	8912907);
INSERT INTO GD_LS
	VALUES (8912909,
	8912906,
	2128,
	1248,
	2128,
	1280,
	8912908);
INSERT INTO GD_GE
	VALUES (8912910,
	8912897,
	8912897,
	42);
INSERT INTO GD_CON
	VALUES (8912910,
	8912898,
	8912900,
	0);
INSERT INTO GD_CTXT
	VALUES (8912910,
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
	VALUES (8912911,
	8912910,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (15728660,
	'Object with Bool Id Attr L Side',
	8,
	'OL_BOOL',
	'',
	15728670);
INSERT INTO O_NBATTR
	VALUES (15728702,
	15728660);
INSERT INTO O_BATTR
	VALUES (15728702,
	15728660);
INSERT INTO O_ATTR
	VALUES (15728702,
	15728660,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (15728703,
	15728660);
INSERT INTO O_BATTR
	VALUES (15728703,
	15728660);
INSERT INTO O_ATTR
	VALUES (15728703,
	15728660,
	15728702,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (15728704,
	15728660);
INSERT INTO O_BATTR
	VALUES (15728704,
	15728660);
INSERT INTO O_ATTR
	VALUES (15728704,
	15728660,
	15728703,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	15728660);
INSERT INTO O_OIDA
	VALUES (15728702,
	15728660,
	0);
INSERT INTO O_RTIDA
	VALUES (15728702,
	15728660,
	0,
	15728648,
	15728660);
INSERT INTO SM_ISM
	VALUES (9437202,
	15728660);
INSERT INTO SM_SM
	VALUES (9437202,
	'',
	18);
INSERT INTO SM_MOORE
	VALUES (9437202);
INSERT INTO SM_EVTDI
	VALUES (9437185,
	9437202,
	'id',
	'',
	524290);
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
	'Start IXC2 Test',
	0,
	'',
	'OL_BOOL1',
	'');
INSERT INTO SM_LEVT
	VALUES (9437186,
	9437202,
	0);
INSERT INTO SM_SEVT
	VALUES (9437186,
	9437202,
	0);
INSERT INTO SM_EVT
	VALUES (9437186,
	9437202,
	0,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OL_BOOL2',
	'');
INSERT INTO SM_LEVT
	VALUES (9437187,
	9437202,
	0);
INSERT INTO SM_SEVT
	VALUES (9437187,
	9437202,
	0);
INSERT INTO SM_EVT
	VALUES (9437187,
	9437202,
	0,
	3,
	'Finish IX2 Test',
	0,
	'',
	'OL_BOOL3',
	'');
INSERT INTO SM_STATE
	VALUES (9437185,
	9437202,
	0,
	'Starting IX2 Test',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (9437185,
	9437185,
	9437202,
	0);
INSERT INTO SM_SEME
	VALUES (9437185,
	9437186,
	9437202,
	0);
INSERT INTO SM_SEME
	VALUES (9437185,
	9437187,
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
	'assign self.id = TRUE;
assign self.i = 42;

create object instance inst1 of OR_BOOL;
assign inst1.r_id = FALSE;
relate self to inst1 across R4;
assign inst1.i = 0;

create object instance inst2 of OR_BOOL;
assign inst2.r_id = TRUE;
relate self to inst2 across R4;
assign inst2.i = 1;

select any oner related by self->OR_BOOL[R4] where selected.r_id == FALSE;
if (oner.i == 0)
  LOG::LogSuccess(message:"IX2: OL_BOOL - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_BOOL - select one inst related by self-><rel_obj>[REL]") ;
end if;

select any oner2 related by self->OR_BOOL[R4] where selected.r_id == TRUE;
if (oner2.i == 1)
  LOG::LogSuccess(message:"IX2: OL_BOOL - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_BOOL - select one inst related by self-><rel_obj>[REL]") ;
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select any ol related by self->OR_BOOL[R4]->OL_BOOL[R4];
if (ol.i == self.i)
  LOG::LogSuccess(message:"IX2: OL_BOOL - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_BOOL - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OR_BOOL2:''Verify Rel with Instance''(id:self.id) to oner;',
	'');
INSERT INTO SM_STATE
	VALUES (9437186,
	9437202,
	0,
	'Verifying Rel with Instance',
	2,
	0);
INSERT INTO SM_CH
	VALUES (9437186,
	9437185,
	9437202,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (9437186,
	9437185,
	9437202,
	0);
INSERT INTO SM_CH
	VALUES (9437186,
	9437186,
	9437202,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (9437186,
	9437186,
	9437202,
	0);
INSERT INTO SM_CH
	VALUES (9437186,
	9437187,
	9437202,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (9437186,
	9437187,
	9437202,
	0);
INSERT INTO SM_MOAH
	VALUES (9437186,
	9437202,
	9437186);
INSERT INTO SM_AH
	VALUES (9437186,
	9437202);
INSERT INTO SM_ACT
	VALUES (9437186,
	9437202,
	1,
	'select any oner related by self->OR_BOOL[R4] where selected.r_id == rcvd_evt.id;
if (oner.r_id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OL_BOOL - Verifying Rel with Instance") ;
  generate OR_BOOL3:''Finish IX2 Test''() to oner;
else 
  LOG::LogFailure(message:"IX2: OL_BOOL - Verifying Rel with Instance") ;
end if;',
	'');
INSERT INTO SM_STATE
	VALUES (9437187,
	9437202,
	0,
	'Finishing IX2 Test',
	3,
	0);
INSERT INTO SM_CH
	VALUES (9437187,
	9437185,
	9437202,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (9437187,
	9437185,
	9437202,
	0);
INSERT INTO SM_CH
	VALUES (9437187,
	9437186,
	9437202,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (9437187,
	9437186,
	9437202,
	0);
INSERT INTO SM_CH
	VALUES (9437187,
	9437187,
	9437202,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (9437187,
	9437187,
	9437202,
	0);
INSERT INTO SM_MOAH
	VALUES (9437187,
	9437202,
	9437187);
INSERT INTO SM_AH
	VALUES (9437187,
	9437202);
INSERT INTO SM_ACT
	VALUES (9437187,
	9437202,
	1,
	'// check delete
select any oner related by self->OR_BOOL[R4] where selected.r_id == FALSE;
unrelate self from oner across R4;
delete object instance oner;
select any oner related by self->OR_BOOL[R4] where selected.r_id == FALSE;
if (empty oner)
  LOG::LogSuccess(message:"IX2: OL_BOOL - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_BOOL - delete object instance <rel_inst>") ;
end if;

//check unrelate
select any or1 related by self->OR_BOOL[R4] where selected.r_id == TRUE;
unrelate self from or1 across R4;

select any or2 related by self->OR_BOOL[R4] where selected.r_id == TRUE;
if (empty or2)
  LOG::LogSuccess(message:"IX2: OL_BOOL - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_BOOL - unrelate object instance <rel_inst>") ;
end if;

delete object instance or1;

',
	'');
INSERT INTO SM_NSTXN
	VALUES (9437185,
	9437202,
	9437185,
	9437187,
	0);
INSERT INTO SM_TXN
	VALUES (9437185,
	9437202,
	9437187,
	0);
INSERT INTO SM_NSTXN
	VALUES (9437186,
	9437202,
	9437185,
	9437185,
	0);
INSERT INTO SM_TXN
	VALUES (9437186,
	9437202,
	9437185,
	0);
INSERT INTO SM_NSTXN
	VALUES (9437187,
	9437202,
	9437185,
	9437186,
	0);
INSERT INTO SM_TXN
	VALUES (9437187,
	9437202,
	9437186,
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
	1600,
	4200,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (9437186,
	9437185,
	9437185,
	41);
INSERT INTO GD_SHP
	VALUES (9437186,
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (9437187,
	9437185,
	9437186,
	41);
INSERT INTO GD_SHP
	VALUES (9437187,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (9437188,
	9437185,
	9437187,
	41);
INSERT INTO GD_SHP
	VALUES (9437188,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (9437189,
	9437185,
	9437186,
	42);
INSERT INTO GD_CON
	VALUES (9437189,
	9437186,
	9437186,
	0);
INSERT INTO GD_CTXT
	VALUES (9437189,
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
	VALUES (9437190,
	9437189,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (9437191,
	9437189,
	1664,
	1312,
	1664,
	1232,
	9437190);
INSERT INTO GD_LS
	VALUES (9437192,
	9437189,
	1664,
	1232,
	1792,
	1232,
	9437191);
INSERT INTO GD_LS
	VALUES (9437193,
	9437189,
	1792,
	1232,
	1792,
	1280,
	9437192);
INSERT INTO GD_GE
	VALUES (9437194,
	9437185,
	9437187,
	42);
INSERT INTO GD_CON
	VALUES (9437194,
	9437186,
	9437187,
	0);
INSERT INTO GD_CTXT
	VALUES (9437194,
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
	VALUES (9437195,
	9437194,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (9437196,
	9437194,
	2016,
	1248,
	2128,
	1248,
	9437195);
INSERT INTO GD_LS
	VALUES (9437197,
	9437194,
	2128,
	1248,
	2128,
	1280,
	9437196);
INSERT INTO GD_GE
	VALUES (9437198,
	9437185,
	9437185,
	42);
INSERT INTO GD_CON
	VALUES (9437198,
	9437186,
	9437188,
	0);
INSERT INTO GD_CTXT
	VALUES (9437198,
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
	VALUES (9437199,
	9437198,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (15728661,
	'Object with Bool Id Attr R Side',
	9,
	'OR_BOOL',
	'',
	15728670);
INSERT INTO O_NBATTR
	VALUES (15728705,
	15728661);
INSERT INTO O_BATTR
	VALUES (15728705,
	15728661);
INSERT INTO O_ATTR
	VALUES (15728705,
	15728661,
	0,
	'r_id',
	'',
	'',
	'r_id',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (15728706,
	15728661);
INSERT INTO O_BATTR
	VALUES (15728706,
	15728661);
INSERT INTO O_ATTR
	VALUES (15728706,
	15728661,
	15728705,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_REF
	VALUES (15728661,
	15728660,
	0,
	15728702,
	15728648,
	15728661,
	15728660,
	15728707,
	15728654,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (15728707,
	15728661,
	15728702,
	15728660,
	1);
INSERT INTO O_ATTR
	VALUES (15728707,
	15728661,
	15728706,
	'id',
	'',
	'',
	'id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (15728708,
	15728661);
INSERT INTO O_BATTR
	VALUES (15728708,
	15728661);
INSERT INTO O_ATTR
	VALUES (15728708,
	15728661,
	15728707,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	15728661);
INSERT INTO O_OIDA
	VALUES (15728705,
	15728661,
	0);
INSERT INTO SM_ISM
	VALUES (9961491,
	15728661);
INSERT INTO SM_SM
	VALUES (9961491,
	'',
	19);
INSERT INTO SM_MOORE
	VALUES (9961491);
INSERT INTO SM_EVTDI
	VALUES (9961473,
	9961491,
	'id',
	'',
	524290);
INSERT INTO SM_LEVT
	VALUES (9961473,
	9961491,
	0);
INSERT INTO SM_SEVT
	VALUES (9961473,
	9961491,
	0);
INSERT INTO SM_EVT
	VALUES (9961473,
	9961491,
	0,
	1,
	'Start IXC2 Test',
	0,
	'',
	'OR_BOOL1',
	'');
INSERT INTO SM_LEVT
	VALUES (9961474,
	9961491,
	0);
INSERT INTO SM_SEVT
	VALUES (9961474,
	9961491,
	0);
INSERT INTO SM_EVT
	VALUES (9961474,
	9961491,
	0,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OR_BOOL2',
	'');
INSERT INTO SM_LEVT
	VALUES (9961475,
	9961491,
	0);
INSERT INTO SM_SEVT
	VALUES (9961475,
	9961491,
	0);
INSERT INTO SM_EVT
	VALUES (9961475,
	9961491,
	0,
	3,
	'Finish IX2 Test',
	0,
	'',
	'OR_BOOL3',
	'');
INSERT INTO SM_STATE
	VALUES (9961473,
	9961491,
	0,
	'Starting IX2 Test',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (9961473,
	9961473,
	9961491,
	0);
INSERT INTO SM_SEME
	VALUES (9961473,
	9961474,
	9961491,
	0);
INSERT INTO SM_SEME
	VALUES (9961473,
	9961475,
	9961491,
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
	'assign self.r_id = TRUE;
assign self.i = 32;

create object instance inst1 of OL_BOOL;
assign inst1.id = FALSE;
relate self to inst1 across R4;
assign inst1.i = 0;

select one ol related by self->OL_BOOL[R4] where selected.id == FALSE;
if (ol.i == 0)
  LOG::LogSuccess(message:"IX2: OR_BOOL - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_BOOL - select one inst related by   self-><rel_obj>[REL]") ;
end if;

select any oner related by self->OL_BOOL[R4]->OR_BOOL[R4] where selected.i == self.i;
if (oner.i == self.i)
  LOG::LogSuccess(message:"IX2: OR_BOOL - select any inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_BOOL - select any inst related by   self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OL_BOOL2:''Verify Rel with Instance''(id:self.r_id) to ol;',
	'');
INSERT INTO SM_STATE
	VALUES (9961474,
	9961491,
	0,
	'Verifying Rel with Instance',
	2,
	0);
INSERT INTO SM_CH
	VALUES (9961474,
	9961473,
	9961491,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (9961474,
	9961473,
	9961491,
	0);
INSERT INTO SM_CH
	VALUES (9961474,
	9961474,
	9961491,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (9961474,
	9961474,
	9961491,
	0);
INSERT INTO SM_CH
	VALUES (9961474,
	9961475,
	9961491,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (9961474,
	9961475,
	9961491,
	0);
INSERT INTO SM_MOAH
	VALUES (9961474,
	9961491,
	9961474);
INSERT INTO SM_AH
	VALUES (9961474,
	9961491);
INSERT INTO SM_ACT
	VALUES (9961474,
	9961491,
	1,
	'select one ol related by self->OL_BOOL[R4];
if (ol.id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OR_BOOL - Verifying Rel with Instance") ;
  generate OL_BOOL3:''Finish IX2 Test''() to ol;
else 
  LOG::LogFailure(message:"IX2: OR_BOOL - Verifying Rel with Instance") ;
end if;',
	'');
INSERT INTO SM_STATE
	VALUES (9961475,
	9961491,
	0,
	'Finishing IX2 Test',
	3,
	0);
INSERT INTO SM_CH
	VALUES (9961475,
	9961473,
	9961491,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (9961475,
	9961473,
	9961491,
	0);
INSERT INTO SM_CH
	VALUES (9961475,
	9961474,
	9961491,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (9961475,
	9961474,
	9961491,
	0);
INSERT INTO SM_CH
	VALUES (9961475,
	9961475,
	9961491,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (9961475,
	9961475,
	9961491,
	0);
INSERT INTO SM_MOAH
	VALUES (9961475,
	9961491,
	9961475);
INSERT INTO SM_AH
	VALUES (9961475,
	9961491);
INSERT INTO SM_ACT
	VALUES (9961475,
	9961491,
	1,
	'// check delete
select one ol related by self->OL_BOOL[R4] where selected.id == FALSE;
unrelate ol from self across R4;
delete object instance ol;
select one ol related by self->OL_BOOL[R4] where selected.id == FALSE;
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
INSERT INTO SM_NSTXN
	VALUES (9961473,
	9961491,
	9961473,
	9961475,
	0);
INSERT INTO SM_TXN
	VALUES (9961473,
	9961491,
	9961475,
	0);
INSERT INTO SM_NSTXN
	VALUES (9961474,
	9961491,
	9961473,
	9961473,
	0);
INSERT INTO SM_TXN
	VALUES (9961474,
	9961491,
	9961473,
	0);
INSERT INTO SM_NSTXN
	VALUES (9961475,
	9961491,
	9961473,
	9961474,
	0);
INSERT INTO SM_TXN
	VALUES (9961475,
	9961491,
	9961474,
	0);
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
	1600,
	4200,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (9961474,
	9961473,
	9961473,
	41);
INSERT INTO GD_SHP
	VALUES (9961474,
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (9961475,
	9961473,
	9961474,
	41);
INSERT INTO GD_SHP
	VALUES (9961475,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (9961476,
	9961473,
	9961475,
	41);
INSERT INTO GD_SHP
	VALUES (9961476,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (9961477,
	9961473,
	9961474,
	42);
INSERT INTO GD_CON
	VALUES (9961477,
	9961474,
	9961474,
	0);
INSERT INTO GD_CTXT
	VALUES (9961477,
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
	VALUES (9961478,
	9961477,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (9961479,
	9961477,
	1664,
	1312,
	1664,
	1232,
	9961478);
INSERT INTO GD_LS
	VALUES (9961480,
	9961477,
	1664,
	1232,
	1792,
	1232,
	9961479);
INSERT INTO GD_LS
	VALUES (9961481,
	9961477,
	1792,
	1232,
	1792,
	1280,
	9961480);
INSERT INTO GD_GE
	VALUES (9961482,
	9961473,
	9961475,
	42);
INSERT INTO GD_CON
	VALUES (9961482,
	9961474,
	9961475,
	0);
INSERT INTO GD_CTXT
	VALUES (9961482,
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
	VALUES (9961483,
	9961482,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (9961484,
	9961482,
	2016,
	1248,
	2128,
	1248,
	9961483);
INSERT INTO GD_LS
	VALUES (9961485,
	9961482,
	2128,
	1248,
	2128,
	1280,
	9961484);
INSERT INTO GD_GE
	VALUES (9961486,
	9961473,
	9961473,
	42);
INSERT INTO GD_CON
	VALUES (9961486,
	9961474,
	9961476,
	0);
INSERT INTO GD_CTXT
	VALUES (9961486,
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
	VALUES (9961487,
	9961486,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (15728662,
	'Object with Date Id Attr L Side',
	10,
	'OL_DATE',
	'',
	15728670);
INSERT INTO O_NBATTR
	VALUES (15728709,
	15728662);
INSERT INTO O_BATTR
	VALUES (15728709,
	15728662);
INSERT INTO O_ATTR
	VALUES (15728709,
	15728662,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524302);
INSERT INTO O_NBATTR
	VALUES (15728710,
	15728662);
INSERT INTO O_BATTR
	VALUES (15728710,
	15728662);
INSERT INTO O_ATTR
	VALUES (15728710,
	15728662,
	15728709,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_NBATTR
	VALUES (15728711,
	15728662);
INSERT INTO O_BATTR
	VALUES (15728711,
	15728662);
INSERT INTO O_ATTR
	VALUES (15728711,
	15728662,
	15728710,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	15728662);
INSERT INTO O_OIDA
	VALUES (15728709,
	15728662,
	0);
INSERT INTO O_RTIDA
	VALUES (15728709,
	15728662,
	0,
	15728649,
	15728662);
INSERT INTO SM_ISM
	VALUES (10485780,
	15728662);
INSERT INTO SM_SM
	VALUES (10485780,
	'',
	20);
INSERT INTO SM_MOORE
	VALUES (10485780);
INSERT INTO SM_EVTDI
	VALUES (10485761,
	10485780,
	'id',
	'',
	524302);
INSERT INTO SM_LEVT
	VALUES (10485761,
	10485780,
	0);
INSERT INTO SM_SEVT
	VALUES (10485761,
	10485780,
	0);
INSERT INTO SM_EVT
	VALUES (10485761,
	10485780,
	0,
	1,
	'Start IXC2 Test',
	0,
	'',
	'OL_DATE1',
	'');
INSERT INTO SM_LEVT
	VALUES (10485762,
	10485780,
	0);
INSERT INTO SM_SEVT
	VALUES (10485762,
	10485780,
	0);
INSERT INTO SM_EVT
	VALUES (10485762,
	10485780,
	0,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OL_DATE2',
	'');
INSERT INTO SM_LEVT
	VALUES (10485763,
	10485780,
	0);
INSERT INTO SM_SEVT
	VALUES (10485763,
	10485780,
	0);
INSERT INTO SM_EVT
	VALUES (10485763,
	10485780,
	0,
	3,
	'Finish IX2 Test',
	0,
	'',
	'OL_DATE3',
	'');
INSERT INTO SM_STATE
	VALUES (10485761,
	10485780,
	0,
	'Starting IX2 Test',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (10485761,
	10485761,
	10485780,
	0);
INSERT INTO SM_SEME
	VALUES (10485761,
	10485762,
	10485780,
	0);
INSERT INTO SM_SEME
	VALUES (10485761,
	10485763,
	10485780,
	0);
INSERT INTO SM_MOAH
	VALUES (10485761,
	10485780,
	10485761);
INSERT INTO SM_AH
	VALUES (10485761,
	10485780);
INSERT INTO SM_ACT
	VALUES (10485761,
	10485780,
	1,
	'bridge self_date = TIM::create_date(second:0,minute:0,hour:0,day:1,month:1,year:1990);
bridge date1 = TIM::create_date(second:0,minute:1,hour:0,day:1,month:1,year:1990);
bridge date2 = TIM::create_date(second:0,minute:2,hour:0,day:1,month:1,year:1990);
bridge date3 = TIM::create_date(second:0,minute:3,hour:0,day:1,month:1,year:1990);
bridge date4 = TIM::create_date(second:0,minute:4,hour:0,day:1,month:1,year:1990);
bridge date5 = TIM::create_date(second:0,minute:5,hour:0,day:1,month:1,year:1990);

assign self.id = self_date;
assign self.i = 22;

create object instance inst1 of OR_DATE;
assign inst1.r_id = date1;
assign inst1.i = 1;
relate self to inst1 across R5;

create object instance inst2 of OR_DATE;
assign inst2.r_id = date2;
assign inst2.i = 1;
relate self to inst2 across R5;

create object instance inst3 of OR_DATE;
assign inst3.r_id = date3;
assign inst3.i = 2;
relate self to inst3 across R5;

create object instance inst4 of OR_DATE;
assign inst4.r_id = date4;
assign inst4.i = 2;
relate self to inst4 across R5;

select any oner related by self->OR_DATE[R5] where selected.r_id == inst1.r_id;
if (oner.i == inst1.i)
 LOG::LogSuccess(message:"IX2: OL_DATE - select one inst related by self-><rel_obj>[REL]") ;
else 
 LOG::LogFailure(message:"IX2: OL_DATE - select one inst related by self-><rel_obj>[REL]") ;
end if;

select many oners related by self->OR_DATE[R5] where selected.i == 2;
assign c = cardinality oners;
if (c == 2)
 LOG::LogSuccess(message:"IX2: OL_DATE - select one inst related by self-><rel_obj>[REL]") ;
else 
 LOG::LogFailure(message:"IX2: OL_DATE - select one inst related by self-><rel_obj>[REL]") ;
end if;

select any ol related by self->OR_DATE[R5]->OL_DATE[R5];
if (ol.i == self.i)
 LOG::LogSuccess(message:"IX2: OL_DATE - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
 LOG::LogFailure(message:"IX2: OL_DATE - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OR_DATE2:''Verify Rel with Instance''(id:self.id) to oner;',
	'');
INSERT INTO SM_STATE
	VALUES (10485762,
	10485780,
	0,
	'Verifying Rel with Instance',
	2,
	0);
INSERT INTO SM_CH
	VALUES (10485762,
	10485761,
	10485780,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (10485762,
	10485761,
	10485780,
	0);
INSERT INTO SM_CH
	VALUES (10485762,
	10485762,
	10485780,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (10485762,
	10485762,
	10485780,
	0);
INSERT INTO SM_CH
	VALUES (10485762,
	10485763,
	10485780,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (10485762,
	10485763,
	10485780,
	0);
INSERT INTO SM_MOAH
	VALUES (10485762,
	10485780,
	10485762);
INSERT INTO SM_AH
	VALUES (10485762,
	10485780);
INSERT INTO SM_ACT
	VALUES (10485762,
	10485780,
	1,
	'select any oner related by self->OR_DATE[R5] where selected.r_id == rcvd_evt.id;
if (oner.r_id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OL_DATE - Verifying Rel with Instance") ;
  generate OR_DATE3:''Finish IX2 Test''() to oner;
else 
  LOG::LogFailure(message:"IX2: OL_DATE - Verifying Rel with Instance") ;
end if;',
	'');
INSERT INTO SM_STATE
	VALUES (10485763,
	10485780,
	0,
	'Finishing IX2 Test',
	3,
	0);
INSERT INTO SM_CH
	VALUES (10485763,
	10485761,
	10485780,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (10485763,
	10485761,
	10485780,
	0);
INSERT INTO SM_CH
	VALUES (10485763,
	10485762,
	10485780,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (10485763,
	10485762,
	10485780,
	0);
INSERT INTO SM_CH
	VALUES (10485763,
	10485763,
	10485780,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (10485763,
	10485763,
	10485780,
	0);
INSERT INTO SM_MOAH
	VALUES (10485763,
	10485780,
	10485763);
INSERT INTO SM_AH
	VALUES (10485763,
	10485780);
INSERT INTO SM_ACT
	VALUES (10485763,
	10485780,
	1,
	'// check delete
select any oner related by self->OR_DATE[R5] where selected.i == 2;
date1 = oner.r_id;
unrelate oner from self across R5;
delete object instance oner;
select any oner related by self->OR_DATE[R5] where selected.r_id == date1;
if (empty oner)
  LOG::LogSuccess(message:"IX2: OL_DATE - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_DATE - delete object instance <rel_inst>") ;
end if;

//check unrelate
select any or1 related by self->OR_DATE[R5] where selected.i == 1;
unrelate self from or1 across R5;
date2 = or1.r_id;
select any or2 related by self->OR_DATE[R5] where selected.r_id == date2;
if (empty or2)
  LOG::LogSuccess(message:"IX2: OL_DATE - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_DATE - unrelate object instance <rel_inst>") ;
end if;
delete object instance or1;

',
	'');
INSERT INTO SM_NSTXN
	VALUES (10485761,
	10485780,
	10485761,
	10485763,
	0);
INSERT INTO SM_TXN
	VALUES (10485761,
	10485780,
	10485763,
	0);
INSERT INTO SM_NSTXN
	VALUES (10485762,
	10485780,
	10485761,
	10485761,
	0);
INSERT INTO SM_TXN
	VALUES (10485762,
	10485780,
	10485761,
	0);
INSERT INTO SM_NSTXN
	VALUES (10485763,
	10485780,
	10485761,
	10485762,
	0);
INSERT INTO SM_TXN
	VALUES (10485763,
	10485780,
	10485762,
	0);
INSERT INTO GD_MD
	VALUES (10485761,
	8,
	10485780,
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
	VALUES (10485762,
	10485761,
	10485761,
	41);
INSERT INTO GD_SHP
	VALUES (10485762,
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (10485763,
	10485761,
	10485762,
	41);
INSERT INTO GD_SHP
	VALUES (10485763,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (10485764,
	10485761,
	10485763,
	41);
INSERT INTO GD_SHP
	VALUES (10485764,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (10485765,
	10485761,
	10485762,
	42);
INSERT INTO GD_CON
	VALUES (10485765,
	10485762,
	10485762,
	0);
INSERT INTO GD_CTXT
	VALUES (10485765,
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
	VALUES (10485766,
	10485765,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (10485767,
	10485765,
	1664,
	1312,
	1664,
	1232,
	10485766);
INSERT INTO GD_LS
	VALUES (10485768,
	10485765,
	1664,
	1232,
	1792,
	1232,
	10485767);
INSERT INTO GD_LS
	VALUES (10485769,
	10485765,
	1792,
	1232,
	1792,
	1280,
	10485768);
INSERT INTO GD_GE
	VALUES (10485770,
	10485761,
	10485763,
	42);
INSERT INTO GD_CON
	VALUES (10485770,
	10485762,
	10485763,
	0);
INSERT INTO GD_CTXT
	VALUES (10485770,
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
	VALUES (10485771,
	10485770,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (10485772,
	10485770,
	2016,
	1248,
	2128,
	1248,
	10485771);
INSERT INTO GD_LS
	VALUES (10485773,
	10485770,
	2128,
	1248,
	2128,
	1280,
	10485772);
INSERT INTO GD_GE
	VALUES (10485774,
	10485761,
	10485761,
	42);
INSERT INTO GD_CON
	VALUES (10485774,
	10485762,
	10485764,
	0);
INSERT INTO GD_CTXT
	VALUES (10485774,
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
	VALUES (10485775,
	10485774,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (15728663,
	'Object with Date Id Attr R Side',
	11,
	'OR_DATE',
	'',
	15728670);
INSERT INTO O_NBATTR
	VALUES (15728712,
	15728663);
INSERT INTO O_BATTR
	VALUES (15728712,
	15728663);
INSERT INTO O_ATTR
	VALUES (15728712,
	15728663,
	0,
	'r_id',
	'',
	'',
	'r_id',
	0,
	524302);
INSERT INTO O_NBATTR
	VALUES (15728713,
	15728663);
INSERT INTO O_BATTR
	VALUES (15728713,
	15728663);
INSERT INTO O_ATTR
	VALUES (15728713,
	15728663,
	15728712,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_REF
	VALUES (15728663,
	15728662,
	0,
	15728709,
	15728649,
	15728663,
	15728662,
	15728714,
	15728655,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (15728714,
	15728663,
	15728709,
	15728662,
	1);
INSERT INTO O_ATTR
	VALUES (15728714,
	15728663,
	15728713,
	'id',
	'',
	'',
	'id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (15728715,
	15728663);
INSERT INTO O_BATTR
	VALUES (15728715,
	15728663);
INSERT INTO O_ATTR
	VALUES (15728715,
	15728663,
	15728714,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	15728663);
INSERT INTO O_OIDA
	VALUES (15728712,
	15728663,
	0);
INSERT INTO SM_ISM
	VALUES (11010069,
	15728663);
INSERT INTO SM_SM
	VALUES (11010069,
	'',
	21);
INSERT INTO SM_MOORE
	VALUES (11010069);
INSERT INTO SM_EVTDI
	VALUES (11010049,
	11010069,
	'id',
	'',
	524302);
INSERT INTO SM_LEVT
	VALUES (11010049,
	11010069,
	0);
INSERT INTO SM_SEVT
	VALUES (11010049,
	11010069,
	0);
INSERT INTO SM_EVT
	VALUES (11010049,
	11010069,
	0,
	1,
	'Start IXC2 Test',
	0,
	'',
	'OR_DATE1',
	'');
INSERT INTO SM_LEVT
	VALUES (11010050,
	11010069,
	0);
INSERT INTO SM_SEVT
	VALUES (11010050,
	11010069,
	0);
INSERT INTO SM_EVT
	VALUES (11010050,
	11010069,
	0,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OR_DATE2',
	'');
INSERT INTO SM_LEVT
	VALUES (11010051,
	11010069,
	0);
INSERT INTO SM_SEVT
	VALUES (11010051,
	11010069,
	0);
INSERT INTO SM_EVT
	VALUES (11010051,
	11010069,
	0,
	3,
	'Finish IX2 Test',
	0,
	'',
	'OR_DATE3',
	'');
INSERT INTO SM_STATE
	VALUES (11010049,
	11010069,
	0,
	'Starting IX2 Test',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (11010049,
	11010049,
	11010069,
	0);
INSERT INTO SM_SEME
	VALUES (11010049,
	11010050,
	11010069,
	0);
INSERT INTO SM_SEME
	VALUES (11010049,
	11010051,
	11010069,
	0);
INSERT INTO SM_MOAH
	VALUES (11010049,
	11010069,
	11010049);
INSERT INTO SM_AH
	VALUES (11010049,
	11010069);
INSERT INTO SM_ACT
	VALUES (11010049,
	11010069,
	1,
	'bridge self_date = TIM::create_date(second:0,minute:0,hour:0,day:1,month:1,year:1990);
bridge date1 = TIM::create_date(second:0,minute:1,hour:0,day:1,month:1,year:1990);

assign self.r_id = self_date;
assign self.i = 12;

create object instance inst1 of OL_DATE;
assign inst1.id = date1;
assign inst1.i = 1;
relate self to inst1 across R5;

select one ol related by self->OL_DATE[R5] where selected.i == 1;
if (ol.i == 1)
  LOG::LogSuccess(message:"IX2: OR_DATE - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_DATE - select one inst related by   self-><rel_obj>[REL]") ;
end if;

select any oner related by self->OL_DATE[R5]->OR_DATE[R5] where selected.r_id == self_date;
if (oner.i == self.i)
  LOG::LogSuccess(message:"IX2: OR_DATE - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_DATE - select one inst related by   self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OL_DATE2:''Verify Rel with Instance''(id:self.r_id) to ol;',
	'');
INSERT INTO SM_STATE
	VALUES (11010050,
	11010069,
	0,
	'Verifying Rel with Instance',
	2,
	0);
INSERT INTO SM_CH
	VALUES (11010050,
	11010049,
	11010069,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (11010050,
	11010049,
	11010069,
	0);
INSERT INTO SM_CH
	VALUES (11010050,
	11010050,
	11010069,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (11010050,
	11010050,
	11010069,
	0);
INSERT INTO SM_CH
	VALUES (11010050,
	11010051,
	11010069,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (11010050,
	11010051,
	11010069,
	0);
INSERT INTO SM_MOAH
	VALUES (11010050,
	11010069,
	11010050);
INSERT INTO SM_AH
	VALUES (11010050,
	11010069);
INSERT INTO SM_ACT
	VALUES (11010050,
	11010069,
	1,
	'select one ol related by self->OL_DATE[R5] where selected.id == rcvd_evt.id;
if (ol.id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OR_DATE - Verifying Rel with Instance") ;
  generate OL_DATE3:''Finish IX2 Test''() to ol;
else 
  LOG::LogFailure(message:"IX2: OR_DATE - Verifying Rel with Instance") ;
end if;',
	'');
INSERT INTO SM_STATE
	VALUES (11010051,
	11010069,
	0,
	'Finishing IX2 Test',
	3,
	0);
INSERT INTO SM_CH
	VALUES (11010051,
	11010049,
	11010069,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (11010051,
	11010049,
	11010069,
	0);
INSERT INTO SM_CH
	VALUES (11010051,
	11010050,
	11010069,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (11010051,
	11010050,
	11010069,
	0);
INSERT INTO SM_CH
	VALUES (11010051,
	11010051,
	11010069,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (11010051,
	11010051,
	11010069,
	0);
INSERT INTO SM_MOAH
	VALUES (11010051,
	11010069,
	11010051);
INSERT INTO SM_AH
	VALUES (11010051,
	11010069);
INSERT INTO SM_ACT
	VALUES (11010051,
	11010069,
	1,
	'// check delete
select one ol related by self->OL_DATE[R5] where selected.i == 1;
unrelate ol from self across R5;
delete object instance ol;
select one ol related by self->OL_DATE[R5] where selected.i == 1;
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
select one ol2 related by self->OL_DATE[R5] where selected.id == date1;
if (empty ol2)
  LOG::LogSuccess(message:"IX2: OR_DATE - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_DATE - unrelate object instance <rel_inst>") ;
end if;
delete object instance ol1;

',
	'');
INSERT INTO SM_NSTXN
	VALUES (11010049,
	11010069,
	11010049,
	11010051,
	0);
INSERT INTO SM_TXN
	VALUES (11010049,
	11010069,
	11010051,
	0);
INSERT INTO SM_NSTXN
	VALUES (11010050,
	11010069,
	11010049,
	11010049,
	0);
INSERT INTO SM_TXN
	VALUES (11010050,
	11010069,
	11010049,
	0);
INSERT INTO SM_NSTXN
	VALUES (11010051,
	11010069,
	11010049,
	11010050,
	0);
INSERT INTO SM_TXN
	VALUES (11010051,
	11010069,
	11010050,
	0);
INSERT INTO GD_MD
	VALUES (11010049,
	8,
	11010069,
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
	VALUES (11010050,
	11010049,
	11010049,
	41);
INSERT INTO GD_SHP
	VALUES (11010050,
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (11010051,
	11010049,
	11010050,
	41);
INSERT INTO GD_SHP
	VALUES (11010051,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (11010052,
	11010049,
	11010051,
	41);
INSERT INTO GD_SHP
	VALUES (11010052,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (11010053,
	11010049,
	11010050,
	42);
INSERT INTO GD_CON
	VALUES (11010053,
	11010050,
	11010050,
	0);
INSERT INTO GD_CTXT
	VALUES (11010053,
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
	VALUES (11010054,
	11010053,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (11010055,
	11010053,
	1664,
	1312,
	1664,
	1232,
	11010054);
INSERT INTO GD_LS
	VALUES (11010056,
	11010053,
	1664,
	1232,
	1792,
	1232,
	11010055);
INSERT INTO GD_LS
	VALUES (11010057,
	11010053,
	1792,
	1232,
	1792,
	1280,
	11010056);
INSERT INTO GD_GE
	VALUES (11010058,
	11010049,
	11010051,
	42);
INSERT INTO GD_CON
	VALUES (11010058,
	11010050,
	11010051,
	0);
INSERT INTO GD_CTXT
	VALUES (11010058,
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
	VALUES (11010059,
	11010058,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (11010060,
	11010058,
	2016,
	1248,
	2128,
	1248,
	11010059);
INSERT INTO GD_LS
	VALUES (11010061,
	11010058,
	2128,
	1248,
	2128,
	1280,
	11010060);
INSERT INTO GD_GE
	VALUES (11010062,
	11010049,
	11010049,
	42);
INSERT INTO GD_CON
	VALUES (11010062,
	11010050,
	11010052,
	0);
INSERT INTO GD_CTXT
	VALUES (11010062,
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
	VALUES (11010063,
	11010062,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (15728664,
	'Object with Time Id Attr L Side',
	12,
	'OL_TIME',
	'',
	15728670);
INSERT INTO O_NBATTR
	VALUES (15728716,
	15728664);
INSERT INTO O_BATTR
	VALUES (15728716,
	15728664);
INSERT INTO O_ATTR
	VALUES (15728716,
	15728664,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524303);
INSERT INTO O_NBATTR
	VALUES (15728717,
	15728664);
INSERT INTO O_BATTR
	VALUES (15728717,
	15728664);
INSERT INTO O_ATTR
	VALUES (15728717,
	15728664,
	15728716,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_NBATTR
	VALUES (15728718,
	15728664);
INSERT INTO O_BATTR
	VALUES (15728718,
	15728664);
INSERT INTO O_ATTR
	VALUES (15728718,
	15728664,
	15728717,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	15728664);
INSERT INTO O_OIDA
	VALUES (15728716,
	15728664,
	0);
INSERT INTO O_RTIDA
	VALUES (15728716,
	15728664,
	0,
	15728650,
	15728664);
INSERT INTO SM_ISM
	VALUES (11534358,
	15728664);
INSERT INTO SM_SM
	VALUES (11534358,
	'',
	22);
INSERT INTO SM_MOORE
	VALUES (11534358);
INSERT INTO SM_EVTDI
	VALUES (11534337,
	11534358,
	'id',
	'',
	524303);
INSERT INTO SM_LEVT
	VALUES (11534337,
	11534358,
	0);
INSERT INTO SM_SEVT
	VALUES (11534337,
	11534358,
	0);
INSERT INTO SM_EVT
	VALUES (11534337,
	11534358,
	0,
	1,
	'Start IXC2 Test',
	0,
	'',
	'OL_TIME1',
	'');
INSERT INTO SM_LEVT
	VALUES (11534338,
	11534358,
	0);
INSERT INTO SM_SEVT
	VALUES (11534338,
	11534358,
	0);
INSERT INTO SM_EVT
	VALUES (11534338,
	11534358,
	0,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OL_TIME2',
	'');
INSERT INTO SM_LEVT
	VALUES (11534339,
	11534358,
	0);
INSERT INTO SM_SEVT
	VALUES (11534339,
	11534358,
	0);
INSERT INTO SM_EVT
	VALUES (11534339,
	11534358,
	0,
	3,
	'Finish IX2 Test',
	0,
	'',
	'OL_TIME3',
	'');
INSERT INTO SM_STATE
	VALUES (11534337,
	11534358,
	0,
	'Starting IX2 Test',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (11534337,
	11534337,
	11534358,
	0);
INSERT INTO SM_SEME
	VALUES (11534337,
	11534338,
	11534358,
	0);
INSERT INTO SM_SEME
	VALUES (11534337,
	11534339,
	11534358,
	0);
INSERT INTO SM_MOAH
	VALUES (11534337,
	11534358,
	11534337);
INSERT INTO SM_AH
	VALUES (11534337,
	11534358);
INSERT INTO SM_ACT
	VALUES (11534337,
	11534358,
	1,
	'LOG::LogInfo(message:"IX2: OL_TIME - getting current clock()");
bridge self_time = TIM::current_clock();
transform OR_TIME::waitOneSec();
bridge time1 = TIM::current_clock();
transform OR_TIME::waitOneSec();
bridge time2 = TIM::current_clock();
transform OR_TIME::waitOneSec();
bridge time3 = TIM::current_clock();
transform OR_TIME::waitOneSec();
bridge time4 = TIM::current_clock();

assign self.id = self_time;
assign self.i = 69;

create object instance inst1 of OR_TIME;
assign inst1.r_id = time1;
assign inst1.i = 1;
relate self to inst1 across R6;

create object instance inst2 of OR_TIME;
assign inst2.r_id = time2;
assign inst2.i = 1;
relate self to inst2 across R6;

create object instance inst3 of OR_TIME;
assign inst3.r_id = time3;
assign inst3.i = 2;
relate self to inst3 across R6;

create object instance inst4 of OR_TIME;
assign inst4.r_id = time4;
assign inst4.i = 2;
relate self to inst4 across R6;

select any oner related by self->OR_TIME[R6] where selected.i == 1;
if (oner.i == 1)
  LOG::LogSuccess(message:"IX2: OL_TIME - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_TIME - select one inst related by self-><rel_obj>[REL]") ;
end if;

select many oners related by self->OR_TIME[R6] where selected.i == 2;
assign c = cardinality oners;
if (c == 2)
  LOG::LogSuccess(message:"IX2: OL_TIME - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_TIME - select one inst related by self-><rel_obj>[REL]") ;
end if;

select any ol related by self->OR_TIME[R6]->OL_TIME[R6] where selected.i == self.i;
if (ol.i == self.i)
  LOG::LogSuccess(message:"IX2: OL_TIME - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_TIME - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OR_TIME2:''Verify Rel with Instance''(id:self.id) to oner;',
	'');
INSERT INTO SM_STATE
	VALUES (11534338,
	11534358,
	0,
	'Verifying Rel with Instance',
	2,
	0);
INSERT INTO SM_CH
	VALUES (11534338,
	11534337,
	11534358,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (11534338,
	11534337,
	11534358,
	0);
INSERT INTO SM_CH
	VALUES (11534338,
	11534338,
	11534358,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (11534338,
	11534338,
	11534358,
	0);
INSERT INTO SM_CH
	VALUES (11534338,
	11534339,
	11534358,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (11534338,
	11534339,
	11534358,
	0);
INSERT INTO SM_MOAH
	VALUES (11534338,
	11534358,
	11534338);
INSERT INTO SM_AH
	VALUES (11534338,
	11534358);
INSERT INTO SM_ACT
	VALUES (11534338,
	11534358,
	1,
	'select any oner related by self->OR_TIME[R6] where selected.r_id == rcvd_evt.id;
if (oner.r_id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OL_TIME - Verifying Rel with Instance") ;
  generate OR_TIME3:''Finish IX2 Test''() to oner;
else 
  LOG::LogFailure(message:"IX2: OL_TIME - Verifying Rel with Instance") ;
end if;',
	'');
INSERT INTO SM_STATE
	VALUES (11534339,
	11534358,
	0,
	'Finishing IX2 Test',
	3,
	0);
INSERT INTO SM_CH
	VALUES (11534339,
	11534337,
	11534358,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (11534339,
	11534337,
	11534358,
	0);
INSERT INTO SM_CH
	VALUES (11534339,
	11534338,
	11534358,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (11534339,
	11534338,
	11534358,
	0);
INSERT INTO SM_CH
	VALUES (11534339,
	11534339,
	11534358,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (11534339,
	11534339,
	11534358,
	0);
INSERT INTO SM_MOAH
	VALUES (11534339,
	11534358,
	11534339);
INSERT INTO SM_AH
	VALUES (11534339,
	11534358);
INSERT INTO SM_ACT
	VALUES (11534339,
	11534358,
	1,
	'// check delete
select any oner related by self->OR_TIME[R6] where selected.i == 1;
found_id = oner.r_id;
unrelate oner from self across R6;
delete object instance oner;
select any oner related by self->OR_TIME[R6] where selected.r_id == found_id;
if (empty oner)
  LOG::LogSuccess(message:"IX2: OL_TIME - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_TIME - delete object instance <rel_inst>") ;
end if;

//check unrelate
select any or1 related by self->OR_TIME[R6] where selected.i == 1;
unrelate self from or1 across R6;
select any or2 related by self->OR_TIME[R6] where selected.i == 1;
if (empty or2)
  LOG::LogSuccess(message:"IX2: OL_TIME - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_TIME - unrelate object instance <rel_inst>") ;
end if;
delete object instance or1;

',
	'');
INSERT INTO SM_NSTXN
	VALUES (11534337,
	11534358,
	11534337,
	11534339,
	0);
INSERT INTO SM_TXN
	VALUES (11534337,
	11534358,
	11534339,
	0);
INSERT INTO SM_NSTXN
	VALUES (11534338,
	11534358,
	11534337,
	11534337,
	0);
INSERT INTO SM_TXN
	VALUES (11534338,
	11534358,
	11534337,
	0);
INSERT INTO SM_NSTXN
	VALUES (11534339,
	11534358,
	11534337,
	11534338,
	0);
INSERT INTO SM_TXN
	VALUES (11534339,
	11534358,
	11534338,
	0);
INSERT INTO GD_MD
	VALUES (11534337,
	8,
	11534358,
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
	VALUES (11534338,
	11534337,
	11534337,
	41);
INSERT INTO GD_SHP
	VALUES (11534338,
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (11534339,
	11534337,
	11534338,
	41);
INSERT INTO GD_SHP
	VALUES (11534339,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (11534340,
	11534337,
	11534339,
	41);
INSERT INTO GD_SHP
	VALUES (11534340,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (11534341,
	11534337,
	11534338,
	42);
INSERT INTO GD_CON
	VALUES (11534341,
	11534338,
	11534338,
	0);
INSERT INTO GD_CTXT
	VALUES (11534341,
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
	VALUES (11534342,
	11534341,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (11534343,
	11534341,
	1664,
	1312,
	1664,
	1232,
	11534342);
INSERT INTO GD_LS
	VALUES (11534344,
	11534341,
	1664,
	1232,
	1792,
	1232,
	11534343);
INSERT INTO GD_LS
	VALUES (11534345,
	11534341,
	1792,
	1232,
	1792,
	1280,
	11534344);
INSERT INTO GD_GE
	VALUES (11534346,
	11534337,
	11534339,
	42);
INSERT INTO GD_CON
	VALUES (11534346,
	11534338,
	11534339,
	0);
INSERT INTO GD_CTXT
	VALUES (11534346,
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
	VALUES (11534347,
	11534346,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (11534348,
	11534346,
	2016,
	1248,
	2128,
	1248,
	11534347);
INSERT INTO GD_LS
	VALUES (11534349,
	11534346,
	2128,
	1248,
	2128,
	1280,
	11534348);
INSERT INTO GD_GE
	VALUES (11534350,
	11534337,
	11534337,
	42);
INSERT INTO GD_CON
	VALUES (11534350,
	11534338,
	11534340,
	0);
INSERT INTO GD_CTXT
	VALUES (11534350,
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
	VALUES (11534351,
	11534350,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (15728665,
	'Object with Time Id Attr R Side',
	13,
	'OR_TIME',
	'',
	15728670);
INSERT INTO O_TFR
	VALUES (15728641,
	15728665,
	'waitOneSec',
	'',
	524289,
	0,
	'',
	1);
INSERT INTO O_NBATTR
	VALUES (15728719,
	15728665);
INSERT INTO O_BATTR
	VALUES (15728719,
	15728665);
INSERT INTO O_ATTR
	VALUES (15728719,
	15728665,
	0,
	'r_id',
	'',
	'',
	'r_id',
	0,
	524303);
INSERT INTO O_NBATTR
	VALUES (15728720,
	15728665);
INSERT INTO O_BATTR
	VALUES (15728720,
	15728665);
INSERT INTO O_ATTR
	VALUES (15728720,
	15728665,
	15728719,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_REF
	VALUES (15728665,
	15728664,
	0,
	15728716,
	15728650,
	15728665,
	15728664,
	15728721,
	15728656,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (15728721,
	15728665,
	15728716,
	15728664,
	1);
INSERT INTO O_ATTR
	VALUES (15728721,
	15728665,
	15728720,
	'id',
	'',
	'',
	'id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (15728722,
	15728665);
INSERT INTO O_BATTR
	VALUES (15728722,
	15728665);
INSERT INTO O_ATTR
	VALUES (15728722,
	15728665,
	15728721,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	15728665);
INSERT INTO O_OIDA
	VALUES (15728719,
	15728665,
	0);
INSERT INTO SM_ISM
	VALUES (12058647,
	15728665);
INSERT INTO SM_SM
	VALUES (12058647,
	'',
	23);
INSERT INTO SM_MOORE
	VALUES (12058647);
INSERT INTO SM_EVTDI
	VALUES (12058625,
	12058647,
	'id',
	'',
	524303);
INSERT INTO SM_LEVT
	VALUES (12058625,
	12058647,
	0);
INSERT INTO SM_SEVT
	VALUES (12058625,
	12058647,
	0);
INSERT INTO SM_EVT
	VALUES (12058625,
	12058647,
	0,
	1,
	'Start IXC2 Test',
	0,
	'',
	'OR_TIME1',
	'');
INSERT INTO SM_LEVT
	VALUES (12058626,
	12058647,
	0);
INSERT INTO SM_SEVT
	VALUES (12058626,
	12058647,
	0);
INSERT INTO SM_EVT
	VALUES (12058626,
	12058647,
	0,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OR_TIME2',
	'');
INSERT INTO SM_LEVT
	VALUES (12058627,
	12058647,
	0);
INSERT INTO SM_SEVT
	VALUES (12058627,
	12058647,
	0);
INSERT INTO SM_EVT
	VALUES (12058627,
	12058647,
	0,
	3,
	'Finish IX2 Test',
	0,
	'',
	'OR_TIME3',
	'');
INSERT INTO SM_STATE
	VALUES (12058625,
	12058647,
	0,
	'Starting IX2 Test',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (12058625,
	12058625,
	12058647,
	0);
INSERT INTO SM_SEME
	VALUES (12058625,
	12058626,
	12058647,
	0);
INSERT INTO SM_SEME
	VALUES (12058625,
	12058627,
	12058647,
	0);
INSERT INTO SM_MOAH
	VALUES (12058625,
	12058647,
	12058625);
INSERT INTO SM_AH
	VALUES (12058625,
	12058647);
INSERT INTO SM_ACT
	VALUES (12058625,
	12058647,
	1,
	'LOG::LogInfo(message:"IX2: OR_TIME - getting current clock()") ;
bridge time1 = TIM::current_clock();
transform OR_TIME::waitOneSec();
bridge time2 = TIM::current_clock();

assign self.r_id = time1;
assign self.i = 32;

create object instance inst1 of OL_TIME;
assign inst1.id = time2;
assign inst1.i = 1;
relate self to inst1 across R6;

// select one one_inst related by self-><rel_obj>[REL]
select one ol related by self->OL_TIME[R6] where selected.i == 1;
if (ol.i == 1)
  LOG::LogSuccess(message:"IX2: OR_TIME - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_TIME - select one inst related by   self-><rel_obj>[REL]") ;
end if;

// select one one_inst related by self-><rel_obj>[REL]-><self_obj>[REL]
select any oner related by self->OL_TIME[R6]->OR_TIME[R6] where selected.i == self.i;
if (oner.i == self.i)
  LOG::LogSuccess(message:"IX2: OR_TIME - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_TIME - select one inst related by   self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OL_TIME2:''Verify Rel with Instance''(id:self.r_id) to ol;',
	'');
INSERT INTO SM_STATE
	VALUES (12058626,
	12058647,
	0,
	'Verifying Rel with Instance',
	2,
	0);
INSERT INTO SM_CH
	VALUES (12058626,
	12058625,
	12058647,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (12058626,
	12058625,
	12058647,
	0);
INSERT INTO SM_CH
	VALUES (12058626,
	12058626,
	12058647,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (12058626,
	12058626,
	12058647,
	0);
INSERT INTO SM_CH
	VALUES (12058626,
	12058627,
	12058647,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (12058626,
	12058627,
	12058647,
	0);
INSERT INTO SM_MOAH
	VALUES (12058626,
	12058647,
	12058626);
INSERT INTO SM_AH
	VALUES (12058626,
	12058647);
INSERT INTO SM_ACT
	VALUES (12058626,
	12058647,
	1,
	'select one ol related by self->OL_TIME[R6] where selected.id == rcvd_evt.id;
if (ol.id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OR_TIME - Verifying Rel with Instance") ;
  generate OL_TIME3:''Finish IX2 Test''() to ol;
else 
  LOG::LogFailure(message:"IX2: OR_TIME - Verifying Rel with Instance") ;
end if;',
	'');
INSERT INTO SM_STATE
	VALUES (12058627,
	12058647,
	0,
	'Finishing IX2 Test',
	3,
	0);
INSERT INTO SM_CH
	VALUES (12058627,
	12058625,
	12058647,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (12058627,
	12058625,
	12058647,
	0);
INSERT INTO SM_CH
	VALUES (12058627,
	12058626,
	12058647,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (12058627,
	12058626,
	12058647,
	0);
INSERT INTO SM_CH
	VALUES (12058627,
	12058627,
	12058647,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (12058627,
	12058627,
	12058647,
	0);
INSERT INTO SM_MOAH
	VALUES (12058627,
	12058647,
	12058627);
INSERT INTO SM_AH
	VALUES (12058627,
	12058647);
INSERT INTO SM_ACT
	VALUES (12058627,
	12058647,
	1,
	'// check delete
select one ol related by self->OL_TIME[R6] where selected.i == 1;
unrelate ol from self across R6;
delete object instance ol;
select one ol related by self->OL_TIME[R6] where selected.i == 1;
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
select one ol2 related by self->OL_TIME[R6] where selected.id == ol1.id;
if (empty ol2)
  LOG::LogSuccess(message:"IX2: OR_TIME - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_TIME - unrelate object instance <rel_inst>") ;
end if;
delete object instance ol1;

',
	'');
INSERT INTO SM_NSTXN
	VALUES (12058625,
	12058647,
	12058625,
	12058627,
	0);
INSERT INTO SM_TXN
	VALUES (12058625,
	12058647,
	12058627,
	0);
INSERT INTO SM_NSTXN
	VALUES (12058626,
	12058647,
	12058625,
	12058625,
	0);
INSERT INTO SM_TXN
	VALUES (12058626,
	12058647,
	12058625,
	0);
INSERT INTO SM_NSTXN
	VALUES (12058627,
	12058647,
	12058625,
	12058626,
	0);
INSERT INTO SM_TXN
	VALUES (12058627,
	12058647,
	12058626,
	0);
INSERT INTO GD_MD
	VALUES (12058625,
	8,
	12058647,
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
	VALUES (12058626,
	12058625,
	12058625,
	41);
INSERT INTO GD_SHP
	VALUES (12058626,
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (12058627,
	12058625,
	12058626,
	41);
INSERT INTO GD_SHP
	VALUES (12058627,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (12058628,
	12058625,
	12058627,
	41);
INSERT INTO GD_SHP
	VALUES (12058628,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (12058629,
	12058625,
	12058626,
	42);
INSERT INTO GD_CON
	VALUES (12058629,
	12058626,
	12058626,
	0);
INSERT INTO GD_CTXT
	VALUES (12058629,
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
	VALUES (12058630,
	12058629,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (12058631,
	12058629,
	1664,
	1312,
	1664,
	1232,
	12058630);
INSERT INTO GD_LS
	VALUES (12058632,
	12058629,
	1664,
	1232,
	1792,
	1232,
	12058631);
INSERT INTO GD_LS
	VALUES (12058633,
	12058629,
	1792,
	1232,
	1792,
	1280,
	12058632);
INSERT INTO GD_GE
	VALUES (12058634,
	12058625,
	12058627,
	42);
INSERT INTO GD_CON
	VALUES (12058634,
	12058626,
	12058627,
	0);
INSERT INTO GD_CTXT
	VALUES (12058634,
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
	VALUES (12058635,
	12058634,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (12058636,
	12058634,
	2016,
	1248,
	2128,
	1248,
	12058635);
INSERT INTO GD_LS
	VALUES (12058637,
	12058634,
	2128,
	1248,
	2128,
	1280,
	12058636);
INSERT INTO GD_GE
	VALUES (12058638,
	12058625,
	12058625,
	42);
INSERT INTO GD_CON
	VALUES (12058638,
	12058626,
	12058628,
	0);
INSERT INTO GD_CTXT
	VALUES (12058638,
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
	VALUES (12058639,
	12058638,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (15728666,
	'Object with Enum Id Attr L Side',
	14,
	'OL_ENUM',
	'',
	15728670);
INSERT INTO O_NBATTR
	VALUES (15728723,
	15728666);
INSERT INTO O_BATTR
	VALUES (15728723,
	15728666);
INSERT INTO O_ATTR
	VALUES (15728723,
	15728666,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (15728724,
	15728666);
INSERT INTO O_BATTR
	VALUES (15728724,
	15728666);
INSERT INTO O_ATTR
	VALUES (15728724,
	15728666,
	15728723,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_NBATTR
	VALUES (15728725,
	15728666);
INSERT INTO O_BATTR
	VALUES (15728725,
	15728666);
INSERT INTO O_ATTR
	VALUES (15728725,
	15728666,
	15728724,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	15728666);
INSERT INTO O_OIDA
	VALUES (15728723,
	15728666,
	0);
INSERT INTO O_RTIDA
	VALUES (15728723,
	15728666,
	0,
	15728651,
	15728666);
INSERT INTO SM_ISM
	VALUES (12582936,
	15728666);
INSERT INTO SM_SM
	VALUES (12582936,
	'',
	24);
INSERT INTO SM_MOORE
	VALUES (12582936);
INSERT INTO SM_EVTDI
	VALUES (12582913,
	12582936,
	'id',
	'',
	524305);
INSERT INTO SM_LEVT
	VALUES (12582913,
	12582936,
	0);
INSERT INTO SM_SEVT
	VALUES (12582913,
	12582936,
	0);
INSERT INTO SM_EVT
	VALUES (12582913,
	12582936,
	0,
	1,
	'Start IXC2 Test',
	0,
	'',
	'OL_ENUM1',
	'');
INSERT INTO SM_LEVT
	VALUES (12582914,
	12582936,
	0);
INSERT INTO SM_SEVT
	VALUES (12582914,
	12582936,
	0);
INSERT INTO SM_EVT
	VALUES (12582914,
	12582936,
	0,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OL_ENUM2',
	'');
INSERT INTO SM_LEVT
	VALUES (12582915,
	12582936,
	0);
INSERT INTO SM_SEVT
	VALUES (12582915,
	12582936,
	0);
INSERT INTO SM_EVT
	VALUES (12582915,
	12582936,
	0,
	3,
	'Finish IX2 Test',
	0,
	'',
	'OL_ENUM3',
	'');
INSERT INTO SM_STATE
	VALUES (12582913,
	12582936,
	0,
	'Starting IX2 Test',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (12582913,
	12582913,
	12582936,
	0);
INSERT INTO SM_SEME
	VALUES (12582913,
	12582914,
	12582936,
	0);
INSERT INTO SM_SEME
	VALUES (12582913,
	12582915,
	12582936,
	0);
INSERT INTO SM_MOAH
	VALUES (12582913,
	12582936,
	12582913);
INSERT INTO SM_AH
	VALUES (12582913,
	12582936);
INSERT INTO SM_ACT
	VALUES (12582913,
	12582936,
	1,
	'assign self.id = "Red";
assign self.i = 52;

create object instance inst1 of OR_ENUM;
assign inst1.r_id = "Blue";
relate self to inst1 across R7;
assign inst1.i = 1;

create object instance inst2 of OR_ENUM;
assign inst2.r_id = "Green";
relate self to inst2 across R7;
assign inst2.i = 2;

create object instance inst3 of OR_ENUM;
assign inst3.r_id = "Black";
relate self to inst3 across R7;
assign inst3.i = 1;

create object instance inst4 of OR_ENUM;
assign inst4.r_id = "Teal";
relate self to inst4 across R7;
assign inst4.i = 2;

select any oner related by self->OR_ENUM[R7] where selected.r_id == "Teal";
if (oner.r_id == "Teal")
  LOG::LogSuccess(message:"IX2: OL_ENUM - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_ENUM - select one inst related by self-><rel_obj>[REL]") ;
end if;

select many oners related by self->OR_ENUM[R7] where selected.i == 2;
assign c = cardinality oners;
if (c == 2)
  LOG::LogSuccess(message:"IX2: OL_ENUM - select many inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_ENUM - select many inst related by self-><rel_obj>[REL]") ;
end if;

select any ol related by self->OR_ENUM[R7]->OL_ENUM[R7] where selected.i == 52;
if (ol.i == self.i)
  LOG::LogSuccess(message:"IX2: OL_ENUM - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_ENUM - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OR_ENUM2:''Verify Rel with Instance''(id:self.id) to oner;',
	'');
INSERT INTO SM_STATE
	VALUES (12582914,
	12582936,
	0,
	'Verifying Rel with Instance',
	2,
	0);
INSERT INTO SM_CH
	VALUES (12582914,
	12582913,
	12582936,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (12582914,
	12582913,
	12582936,
	0);
INSERT INTO SM_CH
	VALUES (12582914,
	12582914,
	12582936,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (12582914,
	12582914,
	12582936,
	0);
INSERT INTO SM_CH
	VALUES (12582914,
	12582915,
	12582936,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (12582914,
	12582915,
	12582936,
	0);
INSERT INTO SM_MOAH
	VALUES (12582914,
	12582936,
	12582914);
INSERT INTO SM_AH
	VALUES (12582914,
	12582936);
INSERT INTO SM_ACT
	VALUES (12582914,
	12582936,
	1,
	'select any oner related by self->OR_ENUM[R7] where selected.r_id == rcvd_evt.id;
if (oner.r_id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OL_ENUM - Verifying Rel with Instance") ;
  generate OR_ENUM3:''Finish IX2 Test''() to oner;
else 
  LOG::LogFailure(message:"IX2: OL_ENUM - Verifying Rel with Instance") ;
end if;',
	'');
INSERT INTO SM_STATE
	VALUES (12582915,
	12582936,
	0,
	'Finishing IX2 Test',
	3,
	0);
INSERT INTO SM_CH
	VALUES (12582915,
	12582913,
	12582936,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (12582915,
	12582913,
	12582936,
	0);
INSERT INTO SM_CH
	VALUES (12582915,
	12582914,
	12582936,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (12582915,
	12582914,
	12582936,
	0);
INSERT INTO SM_CH
	VALUES (12582915,
	12582915,
	12582936,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (12582915,
	12582915,
	12582936,
	0);
INSERT INTO SM_MOAH
	VALUES (12582915,
	12582936,
	12582915);
INSERT INTO SM_AH
	VALUES (12582915,
	12582936);
INSERT INTO SM_ACT
	VALUES (12582915,
	12582936,
	1,
	'// check delete
select any oner related by self->OR_ENUM[R7] where selected.r_id == "Teal";
unrelate oner from self across R7;
delete object instance oner;
select any oner related by self->OR_ENUM[R7] where selected.r_id == "Teal";
if (empty oner)
  LOG::LogSuccess(message:"IX2: OL_ENUM - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_ENUM - delete object instance <rel_inst>") ;
end if;

//check unrelate
select any or1 related by self->OR_ENUM[R7] where selected.r_id == "Black";
unrelate self from or1 across R7;
select any or2 related by self->OR_ENUM[R7] where selected.r_id == "Black";
if (empty or2)
  LOG::LogSuccess(message:"IX2: OL_ENUM - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_ENUM - unrelate object instance <rel_inst>") ;
end if;
delete object instance or1;

',
	'');
INSERT INTO SM_NSTXN
	VALUES (12582913,
	12582936,
	12582913,
	12582915,
	0);
INSERT INTO SM_TXN
	VALUES (12582913,
	12582936,
	12582915,
	0);
INSERT INTO SM_NSTXN
	VALUES (12582914,
	12582936,
	12582913,
	12582913,
	0);
INSERT INTO SM_TXN
	VALUES (12582914,
	12582936,
	12582913,
	0);
INSERT INTO SM_NSTXN
	VALUES (12582915,
	12582936,
	12582913,
	12582914,
	0);
INSERT INTO SM_TXN
	VALUES (12582915,
	12582936,
	12582914,
	0);
INSERT INTO GD_MD
	VALUES (12582913,
	8,
	12582936,
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
	VALUES (12582914,
	12582913,
	12582913,
	41);
INSERT INTO GD_SHP
	VALUES (12582914,
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (12582915,
	12582913,
	12582914,
	41);
INSERT INTO GD_SHP
	VALUES (12582915,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (12582916,
	12582913,
	12582915,
	41);
INSERT INTO GD_SHP
	VALUES (12582916,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (12582917,
	12582913,
	12582914,
	42);
INSERT INTO GD_CON
	VALUES (12582917,
	12582914,
	12582914,
	0);
INSERT INTO GD_CTXT
	VALUES (12582917,
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
	VALUES (12582918,
	12582917,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (12582919,
	12582917,
	1664,
	1312,
	1664,
	1232,
	12582918);
INSERT INTO GD_LS
	VALUES (12582920,
	12582917,
	1664,
	1232,
	1792,
	1232,
	12582919);
INSERT INTO GD_LS
	VALUES (12582921,
	12582917,
	1792,
	1232,
	1792,
	1280,
	12582920);
INSERT INTO GD_GE
	VALUES (12582922,
	12582913,
	12582915,
	42);
INSERT INTO GD_CON
	VALUES (12582922,
	12582914,
	12582915,
	0);
INSERT INTO GD_CTXT
	VALUES (12582922,
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
	VALUES (12582923,
	12582922,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (12582924,
	12582922,
	2016,
	1248,
	2128,
	1248,
	12582923);
INSERT INTO GD_LS
	VALUES (12582925,
	12582922,
	2128,
	1248,
	2128,
	1280,
	12582924);
INSERT INTO GD_GE
	VALUES (12582926,
	12582913,
	12582913,
	42);
INSERT INTO GD_CON
	VALUES (12582926,
	12582914,
	12582916,
	0);
INSERT INTO GD_CTXT
	VALUES (12582926,
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
	VALUES (12582927,
	12582926,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (15728667,
	'Object with Enum Id Attr R Side',
	15,
	'OR_ENUM',
	'',
	15728670);
INSERT INTO O_NBATTR
	VALUES (15728726,
	15728667);
INSERT INTO O_BATTR
	VALUES (15728726,
	15728667);
INSERT INTO O_ATTR
	VALUES (15728726,
	15728667,
	0,
	'r_id',
	'',
	'',
	'r_id',
	0,
	524305);
INSERT INTO O_NBATTR
	VALUES (15728727,
	15728667);
INSERT INTO O_BATTR
	VALUES (15728727,
	15728667);
INSERT INTO O_ATTR
	VALUES (15728727,
	15728667,
	15728726,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_REF
	VALUES (15728667,
	15728666,
	0,
	15728723,
	15728651,
	15728667,
	15728666,
	15728728,
	15728657,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (15728728,
	15728667,
	15728723,
	15728666,
	1);
INSERT INTO O_ATTR
	VALUES (15728728,
	15728667,
	15728727,
	'id',
	'',
	'',
	'id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (15728729,
	15728667);
INSERT INTO O_BATTR
	VALUES (15728729,
	15728667);
INSERT INTO O_ATTR
	VALUES (15728729,
	15728667,
	15728728,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	15728667);
INSERT INTO O_OIDA
	VALUES (15728726,
	15728667,
	0);
INSERT INTO SM_ISM
	VALUES (13107225,
	15728667);
INSERT INTO SM_SM
	VALUES (13107225,
	'',
	25);
INSERT INTO SM_MOORE
	VALUES (13107225);
INSERT INTO SM_EVTDI
	VALUES (13107201,
	13107225,
	'id',
	'',
	524305);
INSERT INTO SM_LEVT
	VALUES (13107201,
	13107225,
	0);
INSERT INTO SM_SEVT
	VALUES (13107201,
	13107225,
	0);
INSERT INTO SM_EVT
	VALUES (13107201,
	13107225,
	0,
	1,
	'Start IXC2 Test',
	0,
	'',
	'OR_ENUM1',
	'');
INSERT INTO SM_LEVT
	VALUES (13107202,
	13107225,
	0);
INSERT INTO SM_SEVT
	VALUES (13107202,
	13107225,
	0);
INSERT INTO SM_EVT
	VALUES (13107202,
	13107225,
	0,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OR_ENUM2',
	'');
INSERT INTO SM_LEVT
	VALUES (13107203,
	13107225,
	0);
INSERT INTO SM_SEVT
	VALUES (13107203,
	13107225,
	0);
INSERT INTO SM_EVT
	VALUES (13107203,
	13107225,
	0,
	3,
	'Finish IX2 Test',
	0,
	'',
	'OR_ENUM3',
	'');
INSERT INTO SM_STATE
	VALUES (13107201,
	13107225,
	0,
	'Starting IX2 Test',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (13107201,
	13107201,
	13107225,
	0);
INSERT INTO SM_SEME
	VALUES (13107201,
	13107202,
	13107225,
	0);
INSERT INTO SM_SEME
	VALUES (13107201,
	13107203,
	13107225,
	0);
INSERT INTO SM_MOAH
	VALUES (13107201,
	13107225,
	13107201);
INSERT INTO SM_AH
	VALUES (13107201,
	13107225);
INSERT INTO SM_ACT
	VALUES (13107201,
	13107225,
	1,
	'assign self.r_id = "Green";
assign self.i = 32;

create object instance inst1 of OL_ENUM;
assign inst1.id = "Black";
assign inst1.i = 1;
relate self to inst1 across R7;

select one ol related by self->OL_ENUM[R7] where selected.id == "Black";
if (ol.i == 1)
  LOG::LogSuccess(message:"IX2: OR_ENUM - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_ENUM - select one inst related by   self-><rel_obj>[REL]") ;
end if;

select any oner related by self->OL_ENUM[R7]->OR_ENUM[R7] where selected.r_id == "Green";
if (oner.i == self.i)
  LOG::LogSuccess(message:"IX2: OR_ENUM - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_ENUM - select one inst related by   self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OL_ENUM2:''Verify Rel with Instance''(id:self.r_id) to ol;',
	'');
INSERT INTO SM_STATE
	VALUES (13107202,
	13107225,
	0,
	'Verifying Rel with Instance',
	2,
	0);
INSERT INTO SM_CH
	VALUES (13107202,
	13107201,
	13107225,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (13107202,
	13107201,
	13107225,
	0);
INSERT INTO SM_CH
	VALUES (13107202,
	13107202,
	13107225,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (13107202,
	13107202,
	13107225,
	0);
INSERT INTO SM_CH
	VALUES (13107202,
	13107203,
	13107225,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (13107202,
	13107203,
	13107225,
	0);
INSERT INTO SM_MOAH
	VALUES (13107202,
	13107225,
	13107202);
INSERT INTO SM_AH
	VALUES (13107202,
	13107225);
INSERT INTO SM_ACT
	VALUES (13107202,
	13107225,
	1,
	'select one ol related by self->OL_ENUM[R7] where selected.id == rcvd_evt.id;
if (ol.id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OR_ENUM - Verifying Rel with Instance") ;
  generate OL_ENUM3:''Finish IX2 Test''() to ol;
else 
  LOG::LogFailure(message:"IX2: OR_ENUM - Verifying Rel with Instance") ;
end if;',
	'');
INSERT INTO SM_STATE
	VALUES (13107203,
	13107225,
	0,
	'Finishing IX2 Test',
	3,
	0);
INSERT INTO SM_CH
	VALUES (13107203,
	13107201,
	13107225,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (13107203,
	13107201,
	13107225,
	0);
INSERT INTO SM_CH
	VALUES (13107203,
	13107202,
	13107225,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (13107203,
	13107202,
	13107225,
	0);
INSERT INTO SM_CH
	VALUES (13107203,
	13107203,
	13107225,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (13107203,
	13107203,
	13107225,
	0);
INSERT INTO SM_MOAH
	VALUES (13107203,
	13107225,
	13107203);
INSERT INTO SM_AH
	VALUES (13107203,
	13107225);
INSERT INTO SM_ACT
	VALUES (13107203,
	13107225,
	1,
	'// check delete
select one ol related by self->OL_ENUM[R7] where selected.i == 1;
found_id = ol.id;
unrelate ol from self across R7;
delete object instance ol;
select one ol related by self->OL_ENUM[R7] where selected.id == found_id;
if (empty ol)
  LOG::LogSuccess(message:"IX2: OR_ENUM - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_ENUM - delete object instance <rel_inst>") ;
end if;

create object instance ol1 of OL_ENUM;
assign ol1.id = found_id;
relate self to ol1 across R7;
unrelate self from ol1 across R7;
select one ol2 related by self->OL_ENUM[R7] where selected.id == found_id;
if (empty ol2)
  LOG::LogSuccess(message:"IX2: OR_ENUM - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_ENUM - unrelate object instance <rel_inst>") ;
end if;
delete object instance ol1;

',
	'');
INSERT INTO SM_NSTXN
	VALUES (13107201,
	13107225,
	13107201,
	13107203,
	0);
INSERT INTO SM_TXN
	VALUES (13107201,
	13107225,
	13107203,
	0);
INSERT INTO SM_NSTXN
	VALUES (13107202,
	13107225,
	13107201,
	13107201,
	0);
INSERT INTO SM_TXN
	VALUES (13107202,
	13107225,
	13107201,
	0);
INSERT INTO SM_NSTXN
	VALUES (13107203,
	13107225,
	13107201,
	13107202,
	0);
INSERT INTO SM_TXN
	VALUES (13107203,
	13107225,
	13107202,
	0);
INSERT INTO GD_MD
	VALUES (13107201,
	8,
	13107225,
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
	VALUES (13107202,
	13107201,
	13107201,
	41);
INSERT INTO GD_SHP
	VALUES (13107202,
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (13107203,
	13107201,
	13107202,
	41);
INSERT INTO GD_SHP
	VALUES (13107203,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (13107204,
	13107201,
	13107203,
	41);
INSERT INTO GD_SHP
	VALUES (13107204,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (13107205,
	13107201,
	13107202,
	42);
INSERT INTO GD_CON
	VALUES (13107205,
	13107202,
	13107202,
	0);
INSERT INTO GD_CTXT
	VALUES (13107205,
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
	VALUES (13107206,
	13107205,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (13107207,
	13107205,
	1664,
	1312,
	1664,
	1232,
	13107206);
INSERT INTO GD_LS
	VALUES (13107208,
	13107205,
	1664,
	1232,
	1792,
	1232,
	13107207);
INSERT INTO GD_LS
	VALUES (13107209,
	13107205,
	1792,
	1232,
	1792,
	1280,
	13107208);
INSERT INTO GD_GE
	VALUES (13107210,
	13107201,
	13107203,
	42);
INSERT INTO GD_CON
	VALUES (13107210,
	13107202,
	13107203,
	0);
INSERT INTO GD_CTXT
	VALUES (13107210,
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
	VALUES (13107211,
	13107210,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (13107212,
	13107210,
	2016,
	1248,
	2128,
	1248,
	13107211);
INSERT INTO GD_LS
	VALUES (13107213,
	13107210,
	2128,
	1248,
	2128,
	1280,
	13107212);
INSERT INTO GD_GE
	VALUES (13107214,
	13107201,
	13107201,
	42);
INSERT INTO GD_CON
	VALUES (13107214,
	13107202,
	13107204,
	0);
INSERT INTO GD_CTXT
	VALUES (13107214,
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
	VALUES (13107215,
	13107214,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (15728668,
	'Object with User Id Attr L Side',
	16,
	'OL_USER',
	'',
	15728670);
INSERT INTO O_NBATTR
	VALUES (15728730,
	15728668);
INSERT INTO O_BATTR
	VALUES (15728730,
	15728668);
INSERT INTO O_ATTR
	VALUES (15728730,
	15728668,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524306);
INSERT INTO O_NBATTR
	VALUES (15728731,
	15728668);
INSERT INTO O_BATTR
	VALUES (15728731,
	15728668);
INSERT INTO O_ATTR
	VALUES (15728731,
	15728668,
	15728730,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_NBATTR
	VALUES (15728732,
	15728668);
INSERT INTO O_BATTR
	VALUES (15728732,
	15728668);
INSERT INTO O_ATTR
	VALUES (15728732,
	15728668,
	15728731,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	15728668);
INSERT INTO O_OIDA
	VALUES (15728730,
	15728668,
	0);
INSERT INTO O_RTIDA
	VALUES (15728730,
	15728668,
	0,
	15728652,
	15728668);
INSERT INTO SM_ISM
	VALUES (13631514,
	15728668);
INSERT INTO SM_SM
	VALUES (13631514,
	'',
	26);
INSERT INTO SM_MOORE
	VALUES (13631514);
INSERT INTO SM_EVTDI
	VALUES (13631489,
	13631514,
	'id',
	'',
	524306);
INSERT INTO SM_LEVT
	VALUES (13631489,
	13631514,
	0);
INSERT INTO SM_SEVT
	VALUES (13631489,
	13631514,
	0);
INSERT INTO SM_EVT
	VALUES (13631489,
	13631514,
	0,
	1,
	'Start IXC2 Test',
	0,
	'',
	'OL_USER1',
	'');
INSERT INTO SM_LEVT
	VALUES (13631490,
	13631514,
	0);
INSERT INTO SM_SEVT
	VALUES (13631490,
	13631514,
	0);
INSERT INTO SM_EVT
	VALUES (13631490,
	13631514,
	0,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OL_USER2',
	'');
INSERT INTO SM_LEVT
	VALUES (13631491,
	13631514,
	0);
INSERT INTO SM_SEVT
	VALUES (13631491,
	13631514,
	0);
INSERT INTO SM_EVT
	VALUES (13631491,
	13631514,
	0,
	3,
	'Finish IX2 Test',
	0,
	'',
	'OL_USER3',
	'');
INSERT INTO SM_STATE
	VALUES (13631489,
	13631514,
	0,
	'Starting IX2 Test',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (13631489,
	13631489,
	13631514,
	0);
INSERT INTO SM_SEME
	VALUES (13631489,
	13631490,
	13631514,
	0);
INSERT INTO SM_SEME
	VALUES (13631489,
	13631491,
	13631514,
	0);
INSERT INTO SM_MOAH
	VALUES (13631489,
	13631514,
	13631489);
INSERT INTO SM_AH
	VALUES (13631489,
	13631514);
INSERT INTO SM_ACT
	VALUES (13631489,
	13631514,
	1,
	'assign self.id = "id";
assign self.i = 42;

create object instance inst1 of OR_USER;
assign inst1.r_id="id1";
assign inst1.i = 1;
relate self to inst1 across R8;

create object instance inst2 of OR_USER;
assign inst2.r_id="id2";
assign inst2.i = 1;
relate self to inst2 across R8;

create object instance inst3 of OR_USER;
assign inst3.r_id="id3";
assign inst3.i = 2;
relate self to inst3 across R8;

create object instance inst4 of OR_USER;
assign inst4.r_id="id4";
assign inst4.i = 2;
relate self to inst4 across R8;

select any oner related by self->OR_USER[R8] where selected.i == 1;
if (oner.i == 1)
  LOG::LogSuccess(message:"IX2: OL_USER - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_USER - select one inst related by self-><rel_obj>[REL]") ;
end if;

select many oners related by self->OR_USER[R8] where selected.i == 2;
assign c = cardinality oners;
if (c == 2)
  LOG::LogSuccess(message:"IX2: OL_USER - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_USER - select one inst related by self-><rel_obj>[REL]") ;
end if;

select any ol related by self->OR_USER[R8]->OL_USER[R8] where selected.i == self.i; 
if (ol.i == self.i)
  LOG::LogSuccess(message:"IX2: OL_USER - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OL_USER - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OR_USER2:''Verify Rel with Instance''(id:self.id) to oner;',
	'');
INSERT INTO SM_STATE
	VALUES (13631490,
	13631514,
	0,
	'Verifying Rel with Instance',
	2,
	0);
INSERT INTO SM_CH
	VALUES (13631490,
	13631489,
	13631514,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (13631490,
	13631489,
	13631514,
	0);
INSERT INTO SM_CH
	VALUES (13631490,
	13631490,
	13631514,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (13631490,
	13631490,
	13631514,
	0);
INSERT INTO SM_CH
	VALUES (13631490,
	13631491,
	13631514,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (13631490,
	13631491,
	13631514,
	0);
INSERT INTO SM_MOAH
	VALUES (13631490,
	13631514,
	13631490);
INSERT INTO SM_AH
	VALUES (13631490,
	13631514);
INSERT INTO SM_ACT
	VALUES (13631490,
	13631514,
	1,
	'select any oner related by self->OR_USER[R8] where selected.r_id == rcvd_evt.id;
if (oner.r_id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OL_USER - Verifying Rel with Instance") ;
  generate OR_USER3:''Finish IX2 Test''() to oner;
else 
  LOG::LogFailure(message:"IX2: OL_USER - Verifying Rel with Instance") ;
end if;',
	'');
INSERT INTO SM_STATE
	VALUES (13631491,
	13631514,
	0,
	'Finishing IX2 Test',
	3,
	0);
INSERT INTO SM_CH
	VALUES (13631491,
	13631489,
	13631514,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (13631491,
	13631489,
	13631514,
	0);
INSERT INTO SM_CH
	VALUES (13631491,
	13631490,
	13631514,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (13631491,
	13631490,
	13631514,
	0);
INSERT INTO SM_CH
	VALUES (13631491,
	13631491,
	13631514,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (13631491,
	13631491,
	13631514,
	0);
INSERT INTO SM_MOAH
	VALUES (13631491,
	13631514,
	13631491);
INSERT INTO SM_AH
	VALUES (13631491,
	13631514);
INSERT INTO SM_ACT
	VALUES (13631491,
	13631514,
	1,
	'// check delete
select any oner related by self->OR_USER[R8] where selected.i == 1;
unrelate oner from self across R8;
delete object instance oner;
select many oners related by self->OR_USER[R8] where selected.i == 1;
assign c = cardinality oners;
if (c == 1)
  LOG::LogSuccess(message:"IX2: OL_USER - delete object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_USER - delete object instance <rel_inst>") ;
end if;

//check unrelate
select any or1 related by self->OR_USER[R8] where selected.i == 1;
unrelate self from or1 across R8;
select any or2 related by self->OR_USER[R8] where selected.i == 1;
if (empty or2)
  LOG::LogSuccess(message:"IX2: OL_USER - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OL_USER - unrelate object instance <rel_inst>") ;
end if;
delete object instance or1;

',
	'');
INSERT INTO SM_NSTXN
	VALUES (13631489,
	13631514,
	13631489,
	13631491,
	0);
INSERT INTO SM_TXN
	VALUES (13631489,
	13631514,
	13631491,
	0);
INSERT INTO SM_NSTXN
	VALUES (13631490,
	13631514,
	13631489,
	13631489,
	0);
INSERT INTO SM_TXN
	VALUES (13631490,
	13631514,
	13631489,
	0);
INSERT INTO SM_NSTXN
	VALUES (13631491,
	13631514,
	13631489,
	13631490,
	0);
INSERT INTO SM_TXN
	VALUES (13631491,
	13631514,
	13631490,
	0);
INSERT INTO GD_MD
	VALUES (13631489,
	8,
	13631514,
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
	VALUES (13631490,
	13631489,
	13631489,
	41);
INSERT INTO GD_SHP
	VALUES (13631490,
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (13631491,
	13631489,
	13631490,
	41);
INSERT INTO GD_SHP
	VALUES (13631491,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (13631492,
	13631489,
	13631491,
	41);
INSERT INTO GD_SHP
	VALUES (13631492,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (13631493,
	13631489,
	13631490,
	42);
INSERT INTO GD_CON
	VALUES (13631493,
	13631490,
	13631490,
	0);
INSERT INTO GD_CTXT
	VALUES (13631493,
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
	VALUES (13631494,
	13631493,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (13631495,
	13631493,
	1664,
	1312,
	1664,
	1232,
	13631494);
INSERT INTO GD_LS
	VALUES (13631496,
	13631493,
	1664,
	1232,
	1792,
	1232,
	13631495);
INSERT INTO GD_LS
	VALUES (13631497,
	13631493,
	1792,
	1232,
	1792,
	1280,
	13631496);
INSERT INTO GD_GE
	VALUES (13631498,
	13631489,
	13631491,
	42);
INSERT INTO GD_CON
	VALUES (13631498,
	13631490,
	13631491,
	0);
INSERT INTO GD_CTXT
	VALUES (13631498,
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
	VALUES (13631499,
	13631498,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (13631500,
	13631498,
	2016,
	1248,
	2128,
	1248,
	13631499);
INSERT INTO GD_LS
	VALUES (13631501,
	13631498,
	2128,
	1248,
	2128,
	1280,
	13631500);
INSERT INTO GD_GE
	VALUES (13631502,
	13631489,
	13631489,
	42);
INSERT INTO GD_CON
	VALUES (13631502,
	13631490,
	13631492,
	0);
INSERT INTO GD_CTXT
	VALUES (13631502,
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
	VALUES (13631503,
	13631502,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (15728669,
	'Object with User Id Attr R Side',
	17,
	'OR_USER',
	'',
	15728670);
INSERT INTO O_NBATTR
	VALUES (15728733,
	15728669);
INSERT INTO O_BATTR
	VALUES (15728733,
	15728669);
INSERT INTO O_ATTR
	VALUES (15728733,
	15728669,
	0,
	'r_id',
	'',
	'',
	'r_id',
	0,
	524306);
INSERT INTO O_NBATTR
	VALUES (15728734,
	15728669);
INSERT INTO O_BATTR
	VALUES (15728734,
	15728669);
INSERT INTO O_ATTR
	VALUES (15728734,
	15728669,
	15728733,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_REF
	VALUES (15728669,
	15728668,
	0,
	15728730,
	15728652,
	15728669,
	15728668,
	15728735,
	15728658,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (15728735,
	15728669,
	15728730,
	15728668,
	1);
INSERT INTO O_ATTR
	VALUES (15728735,
	15728669,
	15728734,
	'id',
	'',
	'',
	'id',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (15728736,
	15728669);
INSERT INTO O_BATTR
	VALUES (15728736,
	15728669);
INSERT INTO O_ATTR
	VALUES (15728736,
	15728669,
	15728735,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	15728669);
INSERT INTO O_OIDA
	VALUES (15728733,
	15728669,
	0);
INSERT INTO SM_ISM
	VALUES (14155803,
	15728669);
INSERT INTO SM_SM
	VALUES (14155803,
	'',
	27);
INSERT INTO SM_MOORE
	VALUES (14155803);
INSERT INTO SM_EVTDI
	VALUES (14155777,
	14155803,
	'id',
	'',
	524306);
INSERT INTO SM_LEVT
	VALUES (14155777,
	14155803,
	0);
INSERT INTO SM_SEVT
	VALUES (14155777,
	14155803,
	0);
INSERT INTO SM_EVT
	VALUES (14155777,
	14155803,
	0,
	1,
	'Start IXC2 Test',
	0,
	'',
	'OR_USER1',
	'');
INSERT INTO SM_LEVT
	VALUES (14155778,
	14155803,
	0);
INSERT INTO SM_SEVT
	VALUES (14155778,
	14155803,
	0);
INSERT INTO SM_EVT
	VALUES (14155778,
	14155803,
	0,
	2,
	'Verify Rel with Instance',
	0,
	'',
	'OR_USER2',
	'');
INSERT INTO SM_LEVT
	VALUES (14155779,
	14155803,
	0);
INSERT INTO SM_SEVT
	VALUES (14155779,
	14155803,
	0);
INSERT INTO SM_EVT
	VALUES (14155779,
	14155803,
	0,
	3,
	'Finish IX2 Test',
	0,
	'',
	'OR_USER3',
	'');
INSERT INTO SM_STATE
	VALUES (14155777,
	14155803,
	0,
	'Starting IX2 Test',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (14155777,
	14155777,
	14155803,
	0);
INSERT INTO SM_SEME
	VALUES (14155777,
	14155778,
	14155803,
	0);
INSERT INTO SM_SEME
	VALUES (14155777,
	14155779,
	14155803,
	0);
INSERT INTO SM_MOAH
	VALUES (14155777,
	14155803,
	14155777);
INSERT INTO SM_AH
	VALUES (14155777,
	14155803);
INSERT INTO SM_ACT
	VALUES (14155777,
	14155803,
	1,
	'assign self.r_id = "id3";
assign self.i = 32;

create object instance inst1 of OL_USER;
assign inst1.id = "id4";
assign inst1.i = 32;
relate self to inst1 across R8;

select one ol related by self->OL_USER[R8] where selected.i == 32;
if (ol.i == 32)
  LOG::LogSuccess(message:"IX2: OR_USER - select one inst related by self-><rel_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_USER - select one inst related by   self-><rel_obj>[REL]") ;
end if;

select any oner related by self->OL_USER[R8]->OR_USER[R8] where selected.i == self.i;
if (oner.i == self.i)
  LOG::LogSuccess(message:"IX2: OR_USER - select one inst related by self-><rel_obj>[REL]-><self_obj>[REL]") ;
else 
  LOG::LogFailure(message:"IX2: OR_USER - select one inst related by   self-><rel_obj>[REL]-><self_obj>[REL]") ;
end if;

// have related instance check if it can see the relationship that this 
// instance created
generate OL_USER2:''Verify Rel with Instance''(id:self.r_id) to ol;',
	'');
INSERT INTO SM_STATE
	VALUES (14155778,
	14155803,
	0,
	'Verifying Rel with Instance',
	2,
	0);
INSERT INTO SM_CH
	VALUES (14155778,
	14155777,
	14155803,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14155778,
	14155777,
	14155803,
	0);
INSERT INTO SM_CH
	VALUES (14155778,
	14155778,
	14155803,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14155778,
	14155778,
	14155803,
	0);
INSERT INTO SM_CH
	VALUES (14155778,
	14155779,
	14155803,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14155778,
	14155779,
	14155803,
	0);
INSERT INTO SM_MOAH
	VALUES (14155778,
	14155803,
	14155778);
INSERT INTO SM_AH
	VALUES (14155778,
	14155803);
INSERT INTO SM_ACT
	VALUES (14155778,
	14155803,
	1,
	'select one ol related by self->OL_USER[R8] where selected.id == rcvd_evt.id;
if (ol.id == rcvd_evt.id)
  LOG::LogSuccess(message:"IX2: OR_USER - Verifying Rel with Instance") ;
  generate OL_USER3:''Finish IX2 Test''() to ol;
else 
  LOG::LogFailure(message:"IX2: OR_USER - Verifying Rel with Instance") ;
end if;',
	'');
INSERT INTO SM_STATE
	VALUES (14155779,
	14155803,
	0,
	'Finishing IX2 Test',
	3,
	0);
INSERT INTO SM_CH
	VALUES (14155779,
	14155777,
	14155803,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14155779,
	14155777,
	14155803,
	0);
INSERT INTO SM_CH
	VALUES (14155779,
	14155778,
	14155803,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14155779,
	14155778,
	14155803,
	0);
INSERT INTO SM_CH
	VALUES (14155779,
	14155779,
	14155803,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14155779,
	14155779,
	14155803,
	0);
INSERT INTO SM_MOAH
	VALUES (14155779,
	14155803,
	14155779);
INSERT INTO SM_AH
	VALUES (14155779,
	14155803);
INSERT INTO SM_ACT
	VALUES (14155779,
	14155803,
	1,
	'// check delete
select one ol related by self->OL_USER[R8] where selected.i == 32;
unrelate ol from self across R8;
delete object instance ol;
select one ol related by self->OL_USER[R8] where selected.i == 32;
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
select one ol2 related by self->OL_USER[R8] where selected.id == ol1.id;
if (empty ol2)
  LOG::LogSuccess(message:"IX2: OR_USER - unrelate object instance <rel_inst>") ;
else
  LOG::LogFailure(message:"IX2: OR_USER - unrelate object instance <rel_inst>") ;
end if;
delete object instance ol1;

',
	'');
INSERT INTO SM_NSTXN
	VALUES (14155777,
	14155803,
	14155777,
	14155779,
	0);
INSERT INTO SM_TXN
	VALUES (14155777,
	14155803,
	14155779,
	0);
INSERT INTO SM_NSTXN
	VALUES (14155778,
	14155803,
	14155777,
	14155777,
	0);
INSERT INTO SM_TXN
	VALUES (14155778,
	14155803,
	14155777,
	0);
INSERT INTO SM_NSTXN
	VALUES (14155779,
	14155803,
	14155777,
	14155778,
	0);
INSERT INTO SM_TXN
	VALUES (14155779,
	14155803,
	14155778,
	0);
INSERT INTO GD_MD
	VALUES (14155777,
	8,
	14155803,
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
	VALUES (14155778,
	14155777,
	14155777,
	41);
INSERT INTO GD_SHP
	VALUES (14155778,
	1680,
	1280,
	2032,
	1600);
INSERT INTO GD_GE
	VALUES (14155779,
	14155777,
	14155778,
	41);
INSERT INTO GD_SHP
	VALUES (14155779,
	2048,
	1280,
	2368,
	1600);
INSERT INTO GD_GE
	VALUES (14155780,
	14155777,
	14155779,
	41);
INSERT INTO GD_SHP
	VALUES (14155780,
	1680,
	1680,
	2032,
	1808);
INSERT INTO GD_GE
	VALUES (14155781,
	14155777,
	14155778,
	42);
INSERT INTO GD_CON
	VALUES (14155781,
	14155778,
	14155778,
	0);
INSERT INTO GD_CTXT
	VALUES (14155781,
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
	VALUES (14155782,
	14155781,
	1680,
	1312,
	1664,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (14155783,
	14155781,
	1664,
	1312,
	1664,
	1232,
	14155782);
INSERT INTO GD_LS
	VALUES (14155784,
	14155781,
	1664,
	1232,
	1792,
	1232,
	14155783);
INSERT INTO GD_LS
	VALUES (14155785,
	14155781,
	1792,
	1232,
	1792,
	1280,
	14155784);
INSERT INTO GD_GE
	VALUES (14155786,
	14155777,
	14155779,
	42);
INSERT INTO GD_CON
	VALUES (14155786,
	14155778,
	14155779,
	0);
INSERT INTO GD_CTXT
	VALUES (14155786,
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
	VALUES (14155787,
	14155786,
	2016,
	1280,
	2016,
	1248,
	0);
INSERT INTO GD_LS
	VALUES (14155788,
	14155786,
	2016,
	1248,
	2128,
	1248,
	14155787);
INSERT INTO GD_LS
	VALUES (14155789,
	14155786,
	2128,
	1248,
	2128,
	1280,
	14155788);
INSERT INTO GD_GE
	VALUES (14155790,
	14155777,
	14155777,
	42);
INSERT INTO GD_CON
	VALUES (14155790,
	14155778,
	14155780,
	0);
INSERT INTO GD_CTXT
	VALUES (14155790,
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
	VALUES (14155791,
	14155790,
	1840,
	1600,
	1840,
	1680,
	0);
INSERT INTO O_OBJ
	VALUES (15728670,
	'driver',
	18,
	'WIMX_DRV',
	'',
	15728670);
INSERT INTO O_NBATTR
	VALUES (15728737,
	15728670);
INSERT INTO O_BATTR
	VALUES (15728737,
	15728670);
INSERT INTO O_ATTR
	VALUES (15728737,
	15728670,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (15728738,
	15728670);
INSERT INTO O_BATTR
	VALUES (15728738,
	15728670);
INSERT INTO O_ATTR
	VALUES (15728738,
	15728670,
	15728737,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	15728670);
INSERT INTO O_OIDA
	VALUES (15728737,
	15728670,
	0);
INSERT INTO SM_ISM
	VALUES (14680092,
	15728670);
INSERT INTO SM_SM
	VALUES (14680092,
	'',
	28);
INSERT INTO SM_MOORE
	VALUES (14680092);
INSERT INTO SM_LEVT
	VALUES (14680065,
	14680092,
	0);
INSERT INTO SM_SEVT
	VALUES (14680065,
	14680092,
	0);
INSERT INTO SM_EVT
	VALUES (14680065,
	14680092,
	0,
	1,
	'Start IXC2 Test',
	0,
	'',
	'WIMX_DRV1',
	'');
INSERT INTO SM_LEVT
	VALUES (14680066,
	14680092,
	0);
INSERT INTO SM_SEVT
	VALUES (14680066,
	14680092,
	0);
INSERT INTO SM_EVT
	VALUES (14680066,
	14680092,
	0,
	3,
	'Start Int',
	0,
	'',
	'WIMX_DRV3',
	'');
INSERT INTO SM_LEVT
	VALUES (14680067,
	14680092,
	0);
INSERT INTO SM_SEVT
	VALUES (14680067,
	14680092,
	0);
INSERT INTO SM_EVT
	VALUES (14680067,
	14680092,
	0,
	4,
	'Start Str',
	0,
	'',
	'WIMX_DRV4',
	'');
INSERT INTO SM_LEVT
	VALUES (14680068,
	14680092,
	0);
INSERT INTO SM_SEVT
	VALUES (14680068,
	14680092,
	0);
INSERT INTO SM_EVT
	VALUES (14680068,
	14680092,
	0,
	5,
	'Start Enum',
	0,
	'',
	'WIMX_DRV5',
	'');
INSERT INTO SM_LEVT
	VALUES (14680069,
	14680092,
	0);
INSERT INTO SM_SEVT
	VALUES (14680069,
	14680092,
	0);
INSERT INTO SM_EVT
	VALUES (14680069,
	14680092,
	0,
	6,
	'Start Date',
	0,
	'',
	'WIMX_DRV6',
	'');
INSERT INTO SM_LEVT
	VALUES (14680070,
	14680092,
	0);
INSERT INTO SM_SEVT
	VALUES (14680070,
	14680092,
	0);
INSERT INTO SM_EVT
	VALUES (14680070,
	14680092,
	0,
	7,
	'Start Time',
	0,
	'',
	'WIMX_DRV7',
	'');
INSERT INTO SM_LEVT
	VALUES (14680071,
	14680092,
	0);
INSERT INTO SM_SEVT
	VALUES (14680071,
	14680092,
	0);
INSERT INTO SM_EVT
	VALUES (14680071,
	14680092,
	0,
	8,
	'Start Bool',
	0,
	'',
	'WIMX_DRV8',
	'');
INSERT INTO SM_LEVT
	VALUES (14680072,
	14680092,
	0);
INSERT INTO SM_SEVT
	VALUES (14680072,
	14680092,
	0);
INSERT INTO SM_EVT
	VALUES (14680072,
	14680092,
	0,
	9,
	'Shut Down',
	0,
	'',
	'WIMX_DRV9',
	'');
INSERT INTO SM_STATE
	VALUES (14680065,
	14680092,
	0,
	'Initializing Timers for all Runs',
	1,
	0);
INSERT INTO SM_CH
	VALUES (14680065,
	14680065,
	14680092,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14680065,
	14680065,
	14680092,
	0);
INSERT INTO SM_EIGN
	VALUES (14680065,
	14680066,
	14680092,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14680065,
	14680066,
	14680092,
	0);
INSERT INTO SM_CH
	VALUES (14680065,
	14680067,
	14680092,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14680065,
	14680067,
	14680092,
	0);
INSERT INTO SM_CH
	VALUES (14680065,
	14680068,
	14680092,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14680065,
	14680068,
	14680092,
	0);
INSERT INTO SM_CH
	VALUES (14680065,
	14680069,
	14680092,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14680065,
	14680069,
	14680092,
	0);
INSERT INTO SM_CH
	VALUES (14680065,
	14680070,
	14680092,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14680065,
	14680070,
	14680092,
	0);
INSERT INTO SM_CH
	VALUES (14680065,
	14680071,
	14680092,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14680065,
	14680071,
	14680092,
	0);
INSERT INTO SM_SEME
	VALUES (14680065,
	14680072,
	14680092,
	0);
INSERT INTO SM_MOAH
	VALUES (14680065,
	14680092,
	14680065);
INSERT INTO SM_AH
	VALUES (14680065,
	14680092);
INSERT INTO SM_ACT
	VALUES (14680065,
	14680092,
	1,
	'assign self.id = "IX2C Driver";
LOG::LogInfo(message:"IXC2 Driver - Staring IXC2 Test - 7 Tests at 2 second intervals") ;

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

select any driver from instances of WIMX_DRV;
create event instance to_self of WIMX_DRV9:''Shut Down''() to driver;
bridge timer_ref = TIM::timer_start(microseconds:14000000,event_inst:to_self);

',
	'');
INSERT INTO SM_STATE
	VALUES (14680066,
	14680092,
	0,
	'Shutting Down',
	8,
	0);
INSERT INTO SM_CH
	VALUES (14680066,
	14680065,
	14680092,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14680066,
	14680065,
	14680092,
	0);
INSERT INTO SM_CH
	VALUES (14680066,
	14680066,
	14680092,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14680066,
	14680066,
	14680092,
	0);
INSERT INTO SM_CH
	VALUES (14680066,
	14680067,
	14680092,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14680066,
	14680067,
	14680092,
	0);
INSERT INTO SM_CH
	VALUES (14680066,
	14680068,
	14680092,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14680066,
	14680068,
	14680092,
	0);
INSERT INTO SM_CH
	VALUES (14680066,
	14680069,
	14680092,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14680066,
	14680069,
	14680092,
	0);
INSERT INTO SM_CH
	VALUES (14680066,
	14680070,
	14680092,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14680066,
	14680070,
	14680092,
	0);
INSERT INTO SM_CH
	VALUES (14680066,
	14680071,
	14680092,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14680066,
	14680071,
	14680092,
	0);
INSERT INTO SM_CH
	VALUES (14680066,
	14680072,
	14680092,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (14680066,
	14680072,
	14680092,
	0);
INSERT INTO SM_MOAH
	VALUES (14680066,
	14680092,
	14680066);
INSERT INTO SM_AH
	VALUES (14680066,
	14680092);
INSERT INTO SM_ACT
	VALUES (14680066,
	14680092,
	1,
	'LOG::LogInfo(message:"ICX_DRV: ICX2 Test Complete, Shutting Down");

bridge ARCH::shutdown();
control stop;',
	'');
INSERT INTO SM_CRTXN
	VALUES (14680065,
	14680092,
	14680065,
	14680065);
INSERT INTO SM_TXN
	VALUES (14680065,
	14680092,
	14680065,
	0);
INSERT INTO SM_NSTXN
	VALUES (14680066,
	14680092,
	14680065,
	14680072,
	0);
INSERT INTO SM_TXN
	VALUES (14680066,
	14680092,
	14680066,
	0);
INSERT INTO GD_MD
	VALUES (14680065,
	8,
	14680092,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1636,
	4443,
	1.100000,
	0);
INSERT INTO GD_GE
	VALUES (14680066,
	14680065,
	14680065,
	41);
INSERT INTO GD_SHP
	VALUES (14680066,
	1664,
	1264,
	1952,
	1344);
INSERT INTO GD_GE
	VALUES (14680067,
	14680065,
	14680066,
	41);
INSERT INTO GD_SHP
	VALUES (14680067,
	2048,
	1264,
	2320,
	1344);
INSERT INTO GD_GE
	VALUES (14680068,
	14680065,
	14680065,
	42);
INSERT INTO GD_CON
	VALUES (14680068,
	14680066,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (14680068,
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
	VALUES (14680069,
	14680068,
	1808,
	1264,
	1808,
	1216,
	0);
INSERT INTO GD_GE
	VALUES (14680070,
	14680065,
	14680066,
	42);
INSERT INTO GD_CON
	VALUES (14680070,
	14680066,
	14680067,
	0);
INSERT INTO GD_CTXT
	VALUES (14680070,
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
	VALUES (14680071,
	14680070,
	1952,
	1296,
	2048,
	1296,
	0);
INSERT INTO R_SIMP
	VALUES (15728646);
INSERT INTO R_REL
	VALUES (15728646,
	2,
	'',
	15728670);
INSERT INTO R_PART
	VALUES (15728656,
	15728646,
	15728656,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (15728656,
	15728646,
	15728656,
	0);
INSERT INTO R_OIR
	VALUES (15728656,
	15728646,
	15728656,
	0);
INSERT INTO R_FORM
	VALUES (15728657,
	15728646,
	15728657,
	1,
	0,
	'');
INSERT INTO R_RGO
	VALUES (15728657,
	15728646,
	15728657);
INSERT INTO R_OIR
	VALUES (15728657,
	15728646,
	15728657,
	0);
INSERT INTO R_SIMP
	VALUES (15728647);
INSERT INTO R_REL
	VALUES (15728647,
	3,
	'',
	15728670);
INSERT INTO R_PART
	VALUES (15728658,
	15728647,
	15728658,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (15728658,
	15728647,
	15728658,
	0);
INSERT INTO R_OIR
	VALUES (15728658,
	15728647,
	15728658,
	0);
INSERT INTO R_FORM
	VALUES (15728659,
	15728647,
	15728659,
	1,
	0,
	'');
INSERT INTO R_RGO
	VALUES (15728659,
	15728647,
	15728659);
INSERT INTO R_OIR
	VALUES (15728659,
	15728647,
	15728659,
	0);
INSERT INTO R_SIMP
	VALUES (15728648);
INSERT INTO R_REL
	VALUES (15728648,
	4,
	'',
	15728670);
INSERT INTO R_PART
	VALUES (15728660,
	15728648,
	15728660,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (15728660,
	15728648,
	15728660,
	0);
INSERT INTO R_OIR
	VALUES (15728660,
	15728648,
	15728660,
	0);
INSERT INTO R_FORM
	VALUES (15728661,
	15728648,
	15728661,
	1,
	0,
	'');
INSERT INTO R_RGO
	VALUES (15728661,
	15728648,
	15728661);
INSERT INTO R_OIR
	VALUES (15728661,
	15728648,
	15728661,
	0);
INSERT INTO R_SIMP
	VALUES (15728649);
INSERT INTO R_REL
	VALUES (15728649,
	5,
	'',
	15728670);
INSERT INTO R_PART
	VALUES (15728662,
	15728649,
	15728662,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (15728662,
	15728649,
	15728662,
	0);
INSERT INTO R_OIR
	VALUES (15728662,
	15728649,
	15728662,
	0);
INSERT INTO R_FORM
	VALUES (15728663,
	15728649,
	15728663,
	1,
	0,
	'');
INSERT INTO R_RGO
	VALUES (15728663,
	15728649,
	15728663);
INSERT INTO R_OIR
	VALUES (15728663,
	15728649,
	15728663,
	0);
INSERT INTO R_SIMP
	VALUES (15728650);
INSERT INTO R_REL
	VALUES (15728650,
	6,
	'',
	15728670);
INSERT INTO R_PART
	VALUES (15728664,
	15728650,
	15728664,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (15728664,
	15728650,
	15728664,
	0);
INSERT INTO R_OIR
	VALUES (15728664,
	15728650,
	15728664,
	0);
INSERT INTO R_FORM
	VALUES (15728665,
	15728650,
	15728665,
	1,
	0,
	'');
INSERT INTO R_RGO
	VALUES (15728665,
	15728650,
	15728665);
INSERT INTO R_OIR
	VALUES (15728665,
	15728650,
	15728665,
	0);
INSERT INTO R_SIMP
	VALUES (15728651);
INSERT INTO R_REL
	VALUES (15728651,
	7,
	'',
	15728670);
INSERT INTO R_PART
	VALUES (15728666,
	15728651,
	15728666,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (15728666,
	15728651,
	15728666,
	0);
INSERT INTO R_OIR
	VALUES (15728666,
	15728651,
	15728666,
	0);
INSERT INTO R_FORM
	VALUES (15728667,
	15728651,
	15728667,
	1,
	0,
	'');
INSERT INTO R_RGO
	VALUES (15728667,
	15728651,
	15728667);
INSERT INTO R_OIR
	VALUES (15728667,
	15728651,
	15728667,
	0);
INSERT INTO R_SIMP
	VALUES (15728652);
INSERT INTO R_REL
	VALUES (15728652,
	8,
	'',
	15728670);
INSERT INTO R_PART
	VALUES (15728668,
	15728652,
	15728668,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (15728668,
	15728652,
	15728668,
	0);
INSERT INTO R_OIR
	VALUES (15728668,
	15728652,
	15728668,
	0);
INSERT INTO R_FORM
	VALUES (15728669,
	15728652,
	15728669,
	1,
	0,
	'');
INSERT INTO R_RGO
	VALUES (15728669,
	15728652,
	15728669);
INSERT INTO R_OIR
	VALUES (15728669,
	15728652,
	15728669,
	0);
INSERT INTO GD_MD
	VALUES (15728766,
	5,
	15728670,
	11,
	1,
	0,
	1,
	1,
	0,
	12,
	1648,
	4267,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (15728769,
	15728766,
	15728655,
	21);
INSERT INTO GD_SHP
	VALUES (15728769,
	1696,
	1296,
	1872,
	1408);
INSERT INTO GD_GE
	VALUES (15728770,
	15728766,
	15728656,
	21);
INSERT INTO GD_SHP
	VALUES (15728770,
	1712,
	1552,
	1872,
	1664);
INSERT INTO GD_GE
	VALUES (15728771,
	15728766,
	15728657,
	21);
INSERT INTO GD_SHP
	VALUES (15728771,
	2016,
	1552,
	2176,
	1696);
INSERT INTO GD_GE
	VALUES (15728772,
	15728766,
	15728658,
	21);
INSERT INTO GD_SHP
	VALUES (15728772,
	1712,
	1712,
	1872,
	1840);
INSERT INTO GD_GE
	VALUES (15728773,
	15728766,
	15728659,
	21);
INSERT INTO GD_SHP
	VALUES (15728773,
	2016,
	1712,
	2176,
	1840);
INSERT INTO GD_GE
	VALUES (15728774,
	15728766,
	15728660,
	21);
INSERT INTO GD_SHP
	VALUES (15728774,
	2240,
	1376,
	2384,
	1504);
INSERT INTO GD_GE
	VALUES (15728775,
	15728766,
	15728661,
	21);
INSERT INTO GD_SHP
	VALUES (15728775,
	2528,
	1376,
	2672,
	1504);
INSERT INTO GD_GE
	VALUES (15728776,
	15728766,
	15728662,
	21);
INSERT INTO GD_SHP
	VALUES (15728776,
	2240,
	1552,
	2384,
	1680);
INSERT INTO GD_GE
	VALUES (15728777,
	15728766,
	15728663,
	21);
INSERT INTO GD_SHP
	VALUES (15728777,
	2528,
	1552,
	2672,
	1680);
INSERT INTO GD_GE
	VALUES (15728778,
	15728766,
	15728664,
	21);
INSERT INTO GD_SHP
	VALUES (15728778,
	2240,
	1712,
	2384,
	1840);
INSERT INTO GD_GE
	VALUES (15728779,
	15728766,
	15728665,
	21);
INSERT INTO GD_SHP
	VALUES (15728779,
	2528,
	1712,
	2672,
	1856);
INSERT INTO GD_GE
	VALUES (15728780,
	15728766,
	15728666,
	21);
INSERT INTO GD_SHP
	VALUES (15728780,
	1712,
	1872,
	1872,
	2000);
INSERT INTO GD_GE
	VALUES (15728781,
	15728766,
	15728667,
	21);
INSERT INTO GD_SHP
	VALUES (15728781,
	2016,
	1872,
	2176,
	2000);
INSERT INTO GD_GE
	VALUES (15728782,
	15728766,
	15728668,
	21);
INSERT INTO GD_SHP
	VALUES (15728782,
	2240,
	1872,
	2384,
	2016);
INSERT INTO GD_GE
	VALUES (15728783,
	15728766,
	15728669,
	21);
INSERT INTO GD_SHP
	VALUES (15728783,
	2528,
	1872,
	2672,
	2016);
INSERT INTO GD_GE
	VALUES (15728784,
	15728766,
	15728670,
	21);
INSERT INTO GD_SHP
	VALUES (15728784,
	1920,
	1296,
	2096,
	1408);
INSERT INTO GD_GE
	VALUES (15728824,
	15728766,
	15728646,
	24);
INSERT INTO GD_CON
	VALUES (15728824,
	15728770,
	15728771,
	0);
INSERT INTO GD_CTXT
	VALUES (15728824,
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
	VALUES (15728825,
	15728824,
	1872,
	1600,
	2016,
	1600,
	0);
INSERT INTO GD_GE
	VALUES (15728826,
	15728766,
	15728647,
	24);
INSERT INTO GD_CON
	VALUES (15728826,
	15728772,
	15728773,
	0);
INSERT INTO GD_CTXT
	VALUES (15728826,
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
	VALUES (15728827,
	15728826,
	1872,
	1760,
	2016,
	1760,
	0);
INSERT INTO GD_GE
	VALUES (15728828,
	15728766,
	15728648,
	24);
INSERT INTO GD_CON
	VALUES (15728828,
	15728774,
	15728775,
	0);
INSERT INTO GD_CTXT
	VALUES (15728828,
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
	VALUES (15728829,
	15728828,
	2384,
	1440,
	2528,
	1440,
	0);
INSERT INTO GD_GE
	VALUES (15728830,
	15728766,
	15728649,
	24);
INSERT INTO GD_CON
	VALUES (15728830,
	15728776,
	15728777,
	0);
INSERT INTO GD_CTXT
	VALUES (15728830,
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
	VALUES (15728831,
	15728830,
	2384,
	1600,
	2528,
	1600,
	0);
INSERT INTO GD_GE
	VALUES (15728832,
	15728766,
	15728650,
	24);
INSERT INTO GD_CON
	VALUES (15728832,
	15728778,
	15728779,
	0);
INSERT INTO GD_CTXT
	VALUES (15728832,
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
	VALUES (15728833,
	15728832,
	2384,
	1776,
	2528,
	1776,
	0);
INSERT INTO GD_GE
	VALUES (15728834,
	15728766,
	15728651,
	24);
INSERT INTO GD_CON
	VALUES (15728834,
	15728780,
	15728781,
	0);
INSERT INTO GD_CTXT
	VALUES (15728834,
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
	VALUES (15728835,
	15728834,
	1872,
	1920,
	2016,
	1920,
	0);
INSERT INTO GD_GE
	VALUES (15728836,
	15728766,
	15728652,
	24);
INSERT INTO GD_CON
	VALUES (15728836,
	15728782,
	15728783,
	0);
INSERT INTO GD_CTXT
	VALUES (15728836,
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
	VALUES (15728837,
	15728836,
	2384,
	1936,
	2528,
	1936,
	0);
