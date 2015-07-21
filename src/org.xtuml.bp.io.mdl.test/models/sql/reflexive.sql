-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (4300000,
	'reflexive',
	'This domain tests reflexive relationships - all of them.  To wit (where the middle digit is an associative object):
 - 1:1
 - 1:m
 - 1:1:1
 - 1:M:1
 - 1:1:M
 - 1:M:M
 - M:M:M
 - M:1:M

Each of these arrangements is subjected to various relates, unrelates and finally selects, where the selected instance is verified to be the correct one.',
	1,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	4300000,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	4300000,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	4300000,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	4300000,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	4300000,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	4300000,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	4300000,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	4300000,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	4300000,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	4300000,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	4300000,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	4300000,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	4300000,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	4300000,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	4300000,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	4300000,
	'inst_ref<Timer>',
	'');
INSERT INTO S_EE
	VALUES (524290,
	'Time',
	'',
	'TIM',
	4300000);
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
	'Logging',
	'',
	'LOG',
	4300000);
INSERT INTO S_BRG
	VALUES (524319,
	524291,
	'LogInfo',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524333,
	524319,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524340,
	524291,
	'LogFailure',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524357,
	524340,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524341,
	524291,
	'LogSuccess',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524358,
	524341,
	'message',
	524293,
	0);
INSERT INTO S_EE
	VALUES (524292,
	'Architecture',
	'',
	'ARCH',
	4300000);
INSERT INTO S_BRG
	VALUES (524321,
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
	4300000,
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
	VALUES (524343,
	524289,
	6815757,
	11);
INSERT INTO GD_SHP
	VALUES (524343,
	1920,
	1376,
	2080,
	1472);
INSERT INTO GD_GE
	VALUES (524344,
	524289,
	524290,
	12);
INSERT INTO GD_SHP
	VALUES (524344,
	1920,
	1632,
	2080,
	1728);
INSERT INTO GD_GE
	VALUES (524345,
	524289,
	524291,
	12);
INSERT INTO GD_SHP
	VALUES (524345,
	1696,
	1632,
	1856,
	1728);
INSERT INTO GD_GE
	VALUES (524346,
	524289,
	524292,
	12);
INSERT INTO GD_SHP
	VALUES (524346,
	2128,
	1632,
	2288,
	1728);
INSERT INTO GD_MD
	VALUES (524290,
	2,
	4300000,
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
	VALUES (524347,
	524290,
	6815757,
	11);
INSERT INTO GD_SHP
	VALUES (524347,
	1920,
	1344,
	2080,
	1440);
INSERT INTO GD_MD
	VALUES (524291,
	3,
	4300000,
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
	VALUES (524348,
	524291,
	6815757,
	11);
INSERT INTO GD_SHP
	VALUES (524348,
	1920,
	1344,
	2080,
	1440);
INSERT INTO GD_MD
	VALUES (524292,
	4,
	4300000,
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
	VALUES (524349,
	524292,
	6815757,
	11);
INSERT INTO GD_SHP
	VALUES (524349,
	1920,
	1344,
	2080,
	1440);
INSERT INTO S_SS
	VALUES (6815757,
	'relation',
	'',
	'',
	1,
	4300000,
	6815757);
INSERT INTO O_OBJ
	VALUES (6815745,
	'reflex',
	9,
	'REF',
	'',
	6815757);
INSERT INTO O_NBATTR
	VALUES (6815745,
	6815745);
INSERT INTO O_BATTR
	VALUES (6815745,
	6815745);
INSERT INTO O_ATTR
	VALUES (6815745,
	6815745,
	0,
	'ref_id',
	'',
	'',
	'ref_id',
	0,
	524291);
INSERT INTO O_REF
	VALUES (6815745,
	6815745,
	0,
	6815745,
	6815745,
	6815745,
	6815746,
	6815746,
	6815757,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (6815746,
	6815745,
	6815745,
	6815745,
	0);
INSERT INTO O_ATTR
	VALUES (6815746,
	6815745,
	6815745,
	'parent',
	'',
	'',
	'parent',
	0,
	524296);
INSERT INTO O_REF
	VALUES (6815745,
	6815745,
	0,
	6815745,
	6815748,
	6815754,
	6815753,
	6815747,
	6815758,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (6815747,
	6815745,
	6815745,
	6815745,
	0);
INSERT INTO O_ATTR
	VALUES (6815747,
	6815745,
	6815746,
	'right',
	'',
	'',
	'right',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	6815745);
INSERT INTO O_OIDA
	VALUES (6815745,
	6815745,
	0);
INSERT INTO O_RTIDA
	VALUES (6815745,
	6815745,
	0,
	6815752,
	6815765);
INSERT INTO O_RTIDA
	VALUES (6815745,
	6815745,
	0,
	6815750,
	6815759);
INSERT INTO O_RTIDA
	VALUES (6815745,
	6815745,
	0,
	6815751,
	6815761);
INSERT INTO O_RTIDA
	VALUES (6815745,
	6815745,
	0,
	6815746,
	6815748);
INSERT INTO O_RTIDA
	VALUES (6815745,
	6815745,
	0,
	6815752,
	6815764);
INSERT INTO O_RTIDA
	VALUES (6815745,
	6815745,
	0,
	6815749,
	6815756);
INSERT INTO O_RTIDA
	VALUES (6815745,
	6815745,
	0,
	6815745,
	6815746);
INSERT INTO O_RTIDA
	VALUES (6815745,
	6815745,
	0,
	6815751,
	6815762);
INSERT INTO O_RTIDA
	VALUES (6815745,
	6815745,
	0,
	6815750,
	6815758);
INSERT INTO O_RTIDA
	VALUES (6815745,
	6815745,
	0,
	6815747,
	6815750);
INSERT INTO O_RTIDA
	VALUES (6815745,
	6815745,
	0,
	6815746,
	6815747);
INSERT INTO O_RTIDA
	VALUES (6815745,
	6815745,
	0,
	6815747,
	6815751);
INSERT INTO O_RTIDA
	VALUES (6815745,
	6815745,
	0,
	6815749,
	6815755);
INSERT INTO O_RTIDA
	VALUES (6815745,
	6815745,
	0,
	6815748,
	6815753);
INSERT INTO O_OBJ
	VALUES (6815746,
	'asc2',
	10,
	'ASC2',
	'',
	6815757);
INSERT INTO O_NBATTR
	VALUES (6815748,
	6815746);
INSERT INTO O_BATTR
	VALUES (6815748,
	6815746);
INSERT INTO O_ATTR
	VALUES (6815748,
	6815746,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524291);
INSERT INTO O_REF
	VALUES (6815746,
	6815745,
	0,
	6815745,
	6815747,
	6815752,
	6815750,
	6815749,
	6815745,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (6815749,
	6815746,
	6815745,
	6815745,
	0);
INSERT INTO O_ATTR
	VALUES (6815749,
	6815746,
	6815748,
	'p_id',
	'',
	'',
	'p_id',
	0,
	524296);
INSERT INTO O_REF
	VALUES (6815746,
	6815745,
	0,
	6815745,
	6815747,
	6815752,
	6815751,
	6815750,
	6815746,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (6815750,
	6815746,
	6815745,
	6815745,
	0);
INSERT INTO O_ATTR
	VALUES (6815750,
	6815746,
	6815749,
	'c_id',
	'',
	'',
	'c_id',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	6815746);
INSERT INTO O_OIDA
	VALUES (6815749,
	6815746,
	0);
INSERT INTO O_ID
	VALUES (1,
	6815746);
INSERT INTO O_OIDA
	VALUES (6815748,
	6815746,
	1);
INSERT INTO O_OBJ
	VALUES (6815747,
	'relation init',
	13,
	'INIT',
	'',
	6815757);
INSERT INTO O_NBATTR
	VALUES (6815751,
	6815747);
INSERT INTO O_BATTR
	VALUES (6815751,
	6815747);
INSERT INTO O_ATTR
	VALUES (6815751,
	6815747,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (6815752,
	6815747);
INSERT INTO O_BATTR
	VALUES (6815752,
	6815747);
INSERT INTO O_ATTR
	VALUES (6815752,
	6815747,
	6815751,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	6815747);
INSERT INTO O_OIDA
	VALUES (6815751,
	6815747,
	0);
INSERT INTO SM_ISM
	VALUES (1048578,
	6815747);
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
	'INIT',
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
	'init',
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
	'Create object instance sc of SC;
sc.count = 10;

Create object instance test1 of TS1;
Create object instance test2 of TS2;
Create object instance test3 of TS3;

Generate SC1:START() to sc;',
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
	1648,
	1312,
	2032,
	1616);
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
	1759,
	1234,
	1883,
	1268,
	-40,
	-21,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048580,
	1048579,
	1760,
	1312,
	1760,
	1280,
	0);
INSERT INTO GD_LS
	VALUES (1048581,
	1048579,
	1760,
	1280,
	1888,
	1280,
	1048580);
INSERT INTO GD_LS
	VALUES (1048582,
	1048579,
	1888,
	1280,
	1888,
	1312,
	1048581);
INSERT INTO O_OBJ
	VALUES (6815748,
	'asc1',
	14,
	'ASC1',
	'',
	6815757);
INSERT INTO O_NBATTR
	VALUES (6815753,
	6815748);
INSERT INTO O_BATTR
	VALUES (6815753,
	6815748);
INSERT INTO O_ATTR
	VALUES (6815753,
	6815748,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524291);
INSERT INTO O_REF
	VALUES (6815748,
	6815745,
	0,
	6815745,
	6815749,
	6815757,
	6815755,
	6815754,
	6815747,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (6815754,
	6815748,
	6815745,
	6815745,
	0);
INSERT INTO O_ATTR
	VALUES (6815754,
	6815748,
	6815753,
	'l_id',
	'',
	'',
	'l_id',
	0,
	524296);
INSERT INTO O_REF
	VALUES (6815748,
	6815745,
	0,
	6815745,
	6815749,
	6815757,
	6815756,
	6815755,
	6815748,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (6815755,
	6815748,
	6815745,
	6815745,
	0);
INSERT INTO O_ATTR
	VALUES (6815755,
	6815748,
	6815754,
	'r_id',
	'',
	'',
	'r_id',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	6815748);
INSERT INTO O_OIDA
	VALUES (6815754,
	6815748,
	0);
INSERT INTO O_ID
	VALUES (1,
	6815748);
INSERT INTO O_OIDA
	VALUES (6815753,
	6815748,
	1);
INSERT INTO O_OBJ
	VALUES (6815749,
	'Test1',
	15,
	'TS1',
	'',
	6815757);
INSERT INTO O_NBATTR
	VALUES (6815756,
	6815749);
INSERT INTO O_BATTR
	VALUES (6815756,
	6815749);
INSERT INTO O_ATTR
	VALUES (6815756,
	6815749,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (6815757,
	6815749);
INSERT INTO O_BATTR
	VALUES (6815757,
	6815749);
INSERT INTO O_ATTR
	VALUES (6815757,
	6815749,
	6815756,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	6815749);
INSERT INTO O_OIDA
	VALUES (6815756,
	6815749,
	0);
INSERT INTO SM_ISM
	VALUES (1572867,
	6815749);
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
	'Testing_01_01',
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
	'TEST',
	0,
	'',
	'TS11',
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
	2,
	'NEXT',
	0,
	'',
	'TS12',
	'');
INSERT INTO SM_SEME
	VALUES (1572865,
	1572866,
	1572867,
	1572865);
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
	'test2',
	0,
	'',
	'TS13',
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
	'test3',
	0,
	'',
	'TS14',
	'');
INSERT INTO SM_SEME
	VALUES (1572865,
	1572868,
	1572867,
	1572865);
INSERT INTO SM_STATE
	VALUES (1572866,
	1572867,
	1572865,
	'Cleaning_01_01',
	2,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_SEME
	VALUES (1572866,
	1572866,
	1572867,
	1572865);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
	VALUES (1572866,
	1572868,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572866,
	1572868,
	1572867,
	1572865);
INSERT INTO SM_STATE
	VALUES (1572867,
	1572867,
	1572865,
	'Testing_01_02',
	3,
	0);
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
INSERT INTO SM_SEME
	VALUES (1572867,
	1572866,
	1572867,
	1572865);
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
INSERT INTO SM_STATE
	VALUES (1572868,
	1572867,
	1572865,
	'Cleaning_01_02',
	4,
	0);
INSERT INTO SM_EIGN
	VALUES (1572868,
	1572865,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572868,
	1572865,
	1572867,
	1572865);
INSERT INTO SM_SEME
	VALUES (1572868,
	1572866,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572868,
	1572867,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572868,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572868,
	1572868,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572868,
	1572868,
	1572867,
	1572865);
INSERT INTO SM_STATE
	VALUES (1572869,
	1572867,
	1572865,
	'Testing_01_03',
	5,
	0);
INSERT INTO SM_EIGN
	VALUES (1572869,
	1572865,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572869,
	1572865,
	1572867,
	1572865);
INSERT INTO SM_SEME
	VALUES (1572869,
	1572866,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572869,
	1572867,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572869,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572869,
	1572868,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572869,
	1572868,
	1572867,
	1572865);
INSERT INTO SM_STATE
	VALUES (1572870,
	1572867,
	1572865,
	'Cleaning_01_03',
	6,
	0);
INSERT INTO SM_SEME
	VALUES (1572870,
	1572865,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572870,
	1572866,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572870,
	1572866,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572870,
	1572867,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572870,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572870,
	1572868,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572870,
	1572868,
	1572867,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572865,
	1572867,
	1572865,
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
	1572865);
INSERT INTO SM_TXN
	VALUES (1572866,
	1572867,
	1572866,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572867,
	1572867,
	1572866,
	1572866,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572867,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572868,
	1572867,
	1572867,
	1572866,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572868,
	1572867,
	1572868,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572869,
	1572867,
	1572868,
	1572866,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572869,
	1572867,
	1572869,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572870,
	1572867,
	1572869,
	1572866,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572870,
	1572867,
	1572870,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572871,
	1572867,
	1572870,
	1572865,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572871,
	1572867,
	1572865,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572872,
	1572867,
	1572865,
	1572867,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572872,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572873,
	1572867,
	1572865,
	1572868,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572873,
	1572867,
	1572869,
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
	'// Create instances
Create object instance ref1 of REF;
ref1.ref_id = 1;
Create object instance ref2 of REF;
ref2.ref_id = 2;
Create object instance ref3 of REF;
ref3.ref_id = 3;
Create object instance ref4 of REF;
ref4.ref_id = 4;
Create object instance ref5 of REF;
ref5.ref_id = 5;

// Relate instances
Relate ref1 to ref2 across R1.''is left of'';
Relate ref3 to ref2 across R1.''is right of'';
Relate ref3 to ref4 across R1.''is left of'';
Relate ref5 to ref4 across R1.''is right of'';

ref_left = ref1;
ref_right = ref2;
While (true)
  Select one ref_right related by ref_left->REF[R1.''is left of''];
  If (not_empty ref_right)
    ref_left = ref_right;
  Else
    Break;
  End If;
End While;

If (ref_left.ref_id == 5)
  LOG::LogSuccess(message:"REF-01-01: OK");
Else
  LOG::LogFailure(message:"REF-01-01: ERROR");
End If;

Generate TS12:NEXT() to Self;
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
	'Select any ref1 from instances of REF where (selected.ref_id == 1);
Select any ref2 from instances of REF where (selected.ref_id == 2);
Select any ref3 from instances of REF where (selected.ref_id == 3);
Select any ref4 from instances of REF where (selected.ref_id == 4);
Select any ref5 from instances of REF where (selected.ref_id == 5);

// Unrelate instances
Unrelate ref2 from ref1 across R1.''is right of'';
Unrelate ref2 from ref3 across R1.''is left of'';
Unrelate ref4 from ref3 across R1.''is right of'';
Unrelate ref4 from ref5 across R1.''is left of'';

Delete object instance ref1;
Delete object instance ref2;
Delete object instance ref3;
Delete object instance ref4;
Delete object instance ref5;

Generate TS12:NEXT() to self;
',
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
	'// Create instances
Create object instance ref1 of REF;
ref1.ref_id = 1;
Create object instance ref2 of REF;
ref2.ref_id = 2;
Create object instance ref3 of REF;
ref3.ref_id = 3;
Create object instance ref4 of REF;
ref4.ref_id = 4;
Create object instance ref5 of REF;
ref5.ref_id = 5;

Create object instance asc1 of ASC1;
asc1.id = 1;
Create object instance asc2 of ASC1;
asc2.id = 2;
Create object instance asc3 of ASC1;
asc3.id = 3;
Create object instance asc4 of ASC1;
asc4.id = 4;

// Relate instances
Relate ref1 to ref2 across R3.''is left of'' using asc1;
Relate ref3 to ref2 across R3.''is right of'' using asc2;
Relate ref3 to ref4 across R3.''is left of'' using asc3;
Relate ref5 to ref4 across R3.''is right of'' using asc4;

// REF-01-02-1
ref_left = ref1;
ref_right = ref1;
While (true)
  Select one ref_right related by ref_left->REF[R3.''is left of''];
  If (not_empty ref_right)
    ref_left = ref_right;
  Else
    Break;
  End If;
End While;

If (ref_left.ref_id == 5)
  LOG::LogSuccess(message:"REF-01-02-1: OK");
Else
  LOG::LogFailure(message:"REF-01-02-1: ERROR");
End If;

// REF-01-02-2
ref_left = ref1;
ref_right = ref1;
Select one ref_right related by asc1->REF[R3.''is left of''];
If (ref_right.ref_id == 2)
  LOG::LogSuccess(message:"REF-01-02-2: OK");
Else
  LOG::LogFailure(message:"REF-01-02-2: ERROR");
End If;


Generate TS12:NEXT() to Self;',
	'');
INSERT INTO SM_MOAH
	VALUES (1572868,
	1572867,
	1572868);
INSERT INTO SM_AH
	VALUES (1572868,
	1572867);
INSERT INTO SM_ACT
	VALUES (1572868,
	1572867,
	1,
	'Select any ref1 from instances of REF where (selected.ref_id == 1);
Select any ref2 from instances of REF where (selected.ref_id == 2);
Select any ref3 from instances of REF where (selected.ref_id == 3);
Select any ref4 from instances of REF where (selected.ref_id == 4);
Select any ref5 from instances of REF where (selected.ref_id == 5);

Select any asc1 from instances of ASC1 where (selected.id == 1);
Select any asc2 from instances of ASC1 where (selected.id == 2);
Select any asc3 from instances of ASC1 where (selected.id == 3);
Select any asc4 from instances of ASC1 where (selected.id == 4);

// Unrelate instances
Unrelate ref2 from ref1 across R3.''is right of'' using asc1;
Unrelate ref2 from ref3 across R3.''is left of'' using asc2;
Unrelate ref4 from ref3 across R3.''is right of'' using asc3;
Unrelate ref4 from ref5 across R3.''is left of'' using asc4;

Delete object instance ref1;
Delete object instance ref2;
Delete object instance ref3;
Delete object instance ref4;
Delete object instance ref5;

Delete object instance asc1;
Delete object instance asc2;
Delete object instance asc3;
Delete object instance asc4;

Generate TS12:NEXT() to self;',
	'');
INSERT INTO SM_MOAH
	VALUES (1572869,
	1572867,
	1572869);
INSERT INTO SM_AH
	VALUES (1572869,
	1572867);
INSERT INTO SM_ACT
	VALUES (1572869,
	1572867,
	1,
	'// Create instances
Create object instance ref1 of REF;
ref1.ref_id = 1;
Create object instance ref2 of REF;
ref2.ref_id = 2;
Create object instance ref3 of REF;
ref3.ref_id = 3;
Create object instance ref4 of REF;
ref4.ref_id = 4;
Create object instance ref5 of REF;
ref5.ref_id = 5;

Create object instance asc1 of ASC4;
asc1.id = 1;
Create object instance asc2 of ASC4;
asc2.id = 2;
Create object instance asc3 of ASC4;
asc3.id = 3;
Create object instance asc4 of ASC4;
asc4.id = 4;

// Relate instances
Relate ref1 to ref2 across R6.''is left of'' using asc1;
Relate ref3 to ref2 across R6.''is right of'' using asc2;
Relate ref3 to ref4 across R6.''is left of'' using asc3;
Relate ref5 to ref4 across R6.''is right of'' using asc4;

// REF-01-03-1
ref_left = ref1;
ref_right = ref1;
While (true)
  Select one ref_right related by ref_left->REF[R6.''is left of''];
  If (not_empty ref_right)
    ref_left = ref_right;
  Else
    Break;
  End If;
End While;

If (ref_left.ref_id == 5)
  LOG::LogSuccess(message:"REF-01-03-1: OK");
Else
  LOG::LogFailure(message:"REF-01-03-1: ERROR");
End If;

// REF-01-03-2
ref_left = ref1;
ref_right = ref1;
Select one ref_right related by asc1->REF[R6.''is left of''];
If (ref_right.ref_id == 2)
  LOG::LogSuccess(message:"REF-01-03-2: OK");
Else
  LOG::LogFailure(message:"REF-01-03-2: ERROR");
End If;


Generate TS12:NEXT() to Self;',
	'');
INSERT INTO SM_MOAH
	VALUES (1572870,
	1572867,
	1572870);
INSERT INTO SM_AH
	VALUES (1572870,
	1572867);
INSERT INTO SM_ACT
	VALUES (1572870,
	1572867,
	1,
	'Select any ref1 from instances of REF where (selected.ref_id == 1);
Select any ref2 from instances of REF where (selected.ref_id == 2);
Select any ref3 from instances of REF where (selected.ref_id == 3);
Select any ref4 from instances of REF where (selected.ref_id == 4);
Select any ref5 from instances of REF where (selected.ref_id == 5);

Select any asc1 from instances of ASC4 where (selected.id == 1);
Select any asc2 from instances of ASC4 where (selected.id == 2);
Select any asc3 from instances of ASC4 where (selected.id == 3);
Select any asc4 from instances of ASC4 where (selected.id == 4);

// Unrelate instances
Unrelate ref2 from ref1 across R6.''is right of'' using asc1;
Unrelate ref2 from ref3 across R6.''is left of'' using asc2;
Unrelate ref4 from ref3 across R6.''is right of'' using asc3;
Unrelate ref4 from ref5 across R6.''is left of'' using asc4;

Delete object instance ref1;
Delete object instance ref2;
Delete object instance ref3;
Delete object instance ref4;
Delete object instance ref5;

Delete object instance asc1;
Delete object instance asc2;
Delete object instance asc3;
Delete object instance asc4;

Select any sc from instances of SC;
Generate SC2:DONE() to sc;',
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
	1493,
	3927,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (1572866,
	1572865,
	1572865,
	41);
INSERT INTO GD_SHP
	VALUES (1572866,
	1760,
	1264,
	2160,
	1328);
INSERT INTO GD_GE
	VALUES (1572867,
	1572865,
	1572866,
	41);
INSERT INTO GD_SHP
	VALUES (1572867,
	1760,
	1376,
	2160,
	1440);
INSERT INTO GD_GE
	VALUES (1572868,
	1572865,
	1572867,
	41);
INSERT INTO GD_SHP
	VALUES (1572868,
	1760,
	1488,
	2160,
	1552);
INSERT INTO GD_GE
	VALUES (1572869,
	1572865,
	1572868,
	41);
INSERT INTO GD_SHP
	VALUES (1572869,
	1760,
	1616,
	2160,
	1680);
INSERT INTO GD_GE
	VALUES (1572870,
	1572865,
	1572869,
	41);
INSERT INTO GD_SHP
	VALUES (1572870,
	1760,
	1744,
	2160,
	1808);
INSERT INTO GD_GE
	VALUES (1572871,
	1572865,
	1572870,
	41);
INSERT INTO GD_SHP
	VALUES (1572871,
	1760,
	1856,
	2160,
	1920);
INSERT INTO GD_GE
	VALUES (1572872,
	1572865,
	1572865,
	42);
INSERT INTO GD_CON
	VALUES (1572872,
	1572866,
	1572866,
	0);
INSERT INTO GD_CTXT
	VALUES (1572872,
	0,
	0,
	0,
	0,
	0,
	0,
	1893,
	1201,
	2035,
	1234,
	-42,
	-6,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572873,
	1572872,
	2048,
	1264,
	2048,
	1232,
	0);
INSERT INTO GD_LS
	VALUES (1572874,
	1572872,
	2048,
	1232,
	1872,
	1232,
	1572873);
INSERT INTO GD_LS
	VALUES (1572875,
	1572872,
	1872,
	1232,
	1872,
	1264,
	1572874);
INSERT INTO GD_GE
	VALUES (1572876,
	1572865,
	1572866,
	42);
INSERT INTO GD_CON
	VALUES (1572876,
	1572866,
	1572867,
	0);
INSERT INTO GD_CTXT
	VALUES (1572876,
	0,
	0,
	0,
	0,
	0,
	0,
	1962,
	1337,
	2088,
	1370,
	26,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572877,
	1572876,
	1952,
	1328,
	1952,
	1376,
	0);
INSERT INTO GD_GE
	VALUES (1572878,
	1572865,
	1572867,
	42);
INSERT INTO GD_CON
	VALUES (1572878,
	1572867,
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
	1964,
	1448,
	2101,
	1485,
	28,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572879,
	1572878,
	1952,
	1440,
	1952,
	1488,
	0);
INSERT INTO GD_GE
	VALUES (1572880,
	1572865,
	1572868,
	42);
INSERT INTO GD_CON
	VALUES (1572880,
	1572868,
	1572869,
	0);
INSERT INTO GD_CTXT
	VALUES (1572880,
	0,
	0,
	0,
	0,
	0,
	0,
	1962,
	1565,
	2103,
	1601,
	26,
	-4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572881,
	1572880,
	1952,
	1552,
	1952,
	1616,
	0);
INSERT INTO GD_GE
	VALUES (1572882,
	1572865,
	1572869,
	42);
INSERT INTO GD_CON
	VALUES (1572882,
	1572869,
	1572870,
	0);
INSERT INTO GD_CTXT
	VALUES (1572882,
	0,
	0,
	0,
	0,
	0,
	0,
	1967,
	1699,
	2120,
	1734,
	31,
	2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572883,
	1572882,
	1952,
	1680,
	1952,
	1744,
	0);
INSERT INTO GD_GE
	VALUES (1572884,
	1572865,
	1572870,
	42);
INSERT INTO GD_CON
	VALUES (1572884,
	1572870,
	1572871,
	0);
INSERT INTO GD_CTXT
	VALUES (1572884,
	0,
	0,
	0,
	0,
	0,
	0,
	1970,
	1815,
	2128,
	1849,
	34,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572885,
	1572884,
	1952,
	1808,
	1952,
	1856,
	0);
INSERT INTO GD_GE
	VALUES (1572886,
	1572865,
	1572871,
	42);
INSERT INTO GD_CON
	VALUES (1572886,
	1572871,
	1572866,
	0);
INSERT INTO GD_CTXT
	VALUES (1572886,
	0,
	0,
	0,
	0,
	0,
	0,
	2237,
	1572,
	2372,
	1608,
	13,
	-5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572887,
	1572886,
	2160,
	1888,
	2240,
	1888,
	0);
INSERT INTO GD_LS
	VALUES (1572888,
	1572886,
	2240,
	1888,
	2240,
	1296,
	1572887);
INSERT INTO GD_LS
	VALUES (1572889,
	1572886,
	2240,
	1296,
	2160,
	1296,
	1572888);
INSERT INTO GD_GE
	VALUES (1572890,
	1572865,
	1572872,
	42);
INSERT INTO GD_CON
	VALUES (1572890,
	1572866,
	1572868,
	0);
INSERT INTO GD_CTXT
	VALUES (1572890,
	0,
	0,
	0,
	0,
	0,
	0,
	1633,
	1530,
	1749,
	1564,
	-31,
	129,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572891,
	1572890,
	1760,
	1312,
	1680,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (1572892,
	1572890,
	1680,
	1312,
	1680,
	1520,
	1572891);
INSERT INTO GD_LS
	VALUES (1572893,
	1572890,
	1680,
	1520,
	1760,
	1520,
	1572892);
INSERT INTO GD_GE
	VALUES (1572894,
	1572865,
	1572873,
	42);
INSERT INTO GD_CON
	VALUES (1572894,
	1572866,
	1572870,
	0);
INSERT INTO GD_CTXT
	VALUES (1572894,
	0,
	0,
	0,
	0,
	0,
	0,
	1613,
	1784,
	1737,
	1817,
	45,
	271,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572895,
	1572894,
	1760,
	1280,
	1584,
	1280,
	0);
INSERT INTO GD_LS
	VALUES (1572896,
	1572894,
	1584,
	1280,
	1584,
	1776,
	1572895);
INSERT INTO GD_LS
	VALUES (1572897,
	1572894,
	1584,
	1776,
	1760,
	1776,
	1572896);
INSERT INTO O_OBJ
	VALUES (6815750,
	'asc3',
	16,
	'ASC3',
	'',
	6815757);
INSERT INTO O_NBATTR
	VALUES (6815758,
	6815750);
INSERT INTO O_BATTR
	VALUES (6815758,
	6815750);
INSERT INTO O_ATTR
	VALUES (6815758,
	6815750,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524291);
INSERT INTO O_REF
	VALUES (6815750,
	6815745,
	0,
	6815745,
	6815746,
	6815749,
	6815748,
	6815759,
	6815749,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (6815759,
	6815750,
	6815745,
	6815745,
	0);
INSERT INTO O_ATTR
	VALUES (6815759,
	6815750,
	6815758,
	'l_id',
	'',
	'',
	'l_id',
	0,
	524296);
INSERT INTO O_REF
	VALUES (6815750,
	6815745,
	0,
	6815745,
	6815746,
	6815749,
	6815747,
	6815760,
	6815750,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (6815760,
	6815750,
	6815745,
	6815745,
	0);
INSERT INTO O_ATTR
	VALUES (6815760,
	6815750,
	6815759,
	'r_id',
	'',
	'',
	'r_id',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	6815750);
INSERT INTO O_OIDA
	VALUES (6815760,
	6815750,
	0);
INSERT INTO O_OIDA
	VALUES (6815759,
	6815750,
	0);
INSERT INTO O_ID
	VALUES (1,
	6815750);
INSERT INTO O_OIDA
	VALUES (6815758,
	6815750,
	1);
INSERT INTO O_OBJ
	VALUES (6815751,
	'Test2',
	18,
	'TS2',
	'',
	6815757);
INSERT INTO O_NBATTR
	VALUES (6815761,
	6815751);
INSERT INTO O_BATTR
	VALUES (6815761,
	6815751);
INSERT INTO O_ATTR
	VALUES (6815761,
	6815751,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (6815762,
	6815751);
INSERT INTO O_BATTR
	VALUES (6815762,
	6815751);
INSERT INTO O_ATTR
	VALUES (6815762,
	6815751,
	6815761,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	6815751);
INSERT INTO O_OIDA
	VALUES (6815761,
	6815751,
	0);
INSERT INTO SM_ISM
	VALUES (2097156,
	6815751);
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
	'Testing_02_01',
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
	'TEST',
	0,
	'',
	'TS21',
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
	'NEXT',
	0,
	'',
	'TS22',
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
	'test2',
	0,
	'',
	'TS23',
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
	'test3',
	0,
	'',
	'TS24',
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
	'Cleaning_02_01',
	2,
	0);
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
INSERT INTO SM_SEME
	VALUES (2097154,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_EIGN
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
	'Testing_02_02',
	3,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_SEME
	VALUES (2097155,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_EIGN
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
	'Cleaning_02_02',
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
INSERT INTO SM_EIGN
	VALUES (2097156,
	2097156,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097156,
	2097156,
	2097156,
	2097153);
INSERT INTO SM_STATE
	VALUES (2097157,
	2097156,
	2097153,
	'Testing_02_03',
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
INSERT INTO SM_EIGN
	VALUES (2097157,
	2097156,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097156,
	2097156,
	2097153);
INSERT INTO SM_STATE
	VALUES (2097158,
	2097156,
	2097153,
	'Cleaning_02_03',
	6,
	0);
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
INSERT INTO SM_EIGN
	VALUES (2097158,
	2097156,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097156,
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
	2097158,
	2097153,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097155,
	2097156,
	2097153,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097156,
	2097156,
	2097154,
	2097154,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097156,
	2097156,
	2097155,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097157,
	2097156,
	2097155,
	2097154,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097157,
	2097156,
	2097156,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097158,
	2097156,
	2097156,
	2097154,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097158,
	2097156,
	2097157,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097159,
	2097156,
	2097157,
	2097154,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097159,
	2097156,
	2097158,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097160,
	2097156,
	2097153,
	2097155,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097160,
	2097156,
	2097155,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097161,
	2097156,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097161,
	2097156,
	2097157,
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
	'// Create instances
Create object instance ref1 of REF;
ref1.ref_id = 1;
Create object instance ref2 of REF;
ref2.ref_id = 2;
Create object instance ref3 of REF;
ref3.ref_id = 3;
Create object instance ref4 of REF;
ref4.ref_id = 4;
Create object instance ref5 of REF;
ref5.ref_id = 5;

// Relate instances
Relate ref1 to ref2 across R2.''is parent of'';
Relate ref3 to ref1 across R2.''is child of'';
Relate ref4 to ref5 across R2.''is parent of'';

// REF-03-01
ref_left = ref1;
ref_right = ref1;
found = false;
Select many right_set related by ref_left->REF[R2.''is parent of''];
For each ref_right in right_set
  If((ref_right.ref_id == 2) or (ref_right.ref_id == 3))
    found = true;
  Else
    found = false;
    break;
  End If;
End For;

If (found)
  LOG::LogSuccess(message:"REF-02-01-1: OK");
Else
  LOG::LogFailure(message:"REF-02-01-1: ERROR");
End If;

// REF-03-02
ref_left = ref2;
ref_right = ref2;
found = false;
Select one ref_left related by ref_right->REF[R2.''is child of''];

If (ref_left.ref_id == 1)
  LOG::LogSuccess(message:"REF-02-01-2: OK");
Else
  LOG::LogFailure(message:"REF-02-01-2: ERROR");
End If;

Generate TS22:NEXT() to Self;',
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
	'Select any ref1 from instances of REF where (selected.ref_id == 1);
Select any ref2 from instances of REF where (selected.ref_id == 2);
Select any ref3 from instances of REF where (selected.ref_id == 3);
Select any ref4 from instances of REF where (selected.ref_id == 4);
Select any ref5 from instances of REF where (selected.ref_id == 5);

// Relate instances
Unrelate ref2 from ref1 across R2.''is child of'';
Unrelate ref1 from ref3 across R2.''is parent of'';
Unrelate ref5 from ref4 across R2.''is child of'';

Delete object instance ref1;
Delete object instance ref2;
Delete object instance ref3;
Delete object instance ref4;
Delete object instance ref5;

Generate TS22:NEXT() to Self;',
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
	'// Create instances
Create object instance ref1 of REF;
ref1.ref_id = 1;
Create object instance ref2 of REF;
ref2.ref_id = 2;
Create object instance ref3 of REF;
ref3.ref_id = 3;
Create object instance ref4 of REF;
ref4.ref_id = 4;
Create object instance ref5 of REF;
ref5.ref_id = 5;

Create object instance asc1 of ASC2;
asc1.id = 1;
Create object instance asc2 of ASC2;
asc2.id = 2;
Create object instance asc3 of ASC2;
asc3.id = 3;

// Relate instances
Relate ref1 to ref2 across R4.''is parent of'' using asc1;
Relate ref3 to ref1 across R4.''is child of'' using asc2;
Relate ref4 to ref5 across R4.''is parent of'' using asc3;

// REF-02-02
ref_left = ref1;
ref_right = ref1;
found = false;
Select many right_set related by ref_left->REF[R4.''is parent of''];
For each ref_right in right_set
  If((ref_right.ref_id == 2) or (ref_right.ref_id == 3))
    found = true;
  Else
    found = false;
    break;
  End If;
End For;

If (found)
  LOG::LogSuccess(message:"REF-02-02-1: OK");
Else
  LOG::LogFailure(message:"REF-02-02-1: ERROR");
End If;

// REF-03-02
ref_left = ref2;
ref_right = ref2;
found = false;
Select one ref_left related by ref_right->REF[R4.''is child of''];

If (ref_left.ref_id == 1)
  LOG::LogSuccess(message:"REF-02-02-2: OK");
Else
  LOG::LogFailure(message:"REF-02-02-2: ERROR");
End If;

Generate TS22:NEXT() to Self;',
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
	'Select any ref1 from instances of REF where (selected.ref_id == 1);
Select any ref2 from instances of REF where (selected.ref_id == 2);
Select any ref3 from instances of REF where (selected.ref_id == 3);
Select any ref4 from instances of REF where (selected.ref_id == 4);
Select any ref5 from instances of REF where (selected.ref_id == 5);

Select any asc1 from instances of ASC2 where (selected.id == 1);
Select any asc2 from instances of ASC2 where (selected.id == 2);
Select any asc3 from instances of ASC2 where (selected.id == 3);

// Relate instances
Unrelate ref2 from ref1 across R4.''is child of'' using asc1;
Unrelate ref1 from ref3 across R4.''is parent of'' using asc2;
Unrelate ref5 from ref4 across R4.''is child of'' using asc3;

Delete object instance ref1;
Delete object instance ref2;
Delete object instance ref3;
Delete object instance ref4;
Delete object instance ref5;

Delete object instance asc1;
Delete object instance asc2;
Delete object instance asc3;

Generate TS22:NEXT() to Self;',
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
	'// Create instances
Create object instance ref1 of REF;
ref1.ref_id = 1;
Create object instance ref2 of REF;
ref2.ref_id = 2;
Create object instance ref3 of REF;
ref3.ref_id = 3;
Create object instance ref4 of REF;
ref4.ref_id = 4;
Create object instance ref5 of REF;
ref5.ref_id = 5;

Create object instance asc1 of ASC6;
asc1.id = 1;
Create object instance asc2 of ASC6;
asc2.id = 2;
Create object instance asc3 of ASC6;
asc3.id = 3;

// Relate instances
Relate ref1 to ref2 across R7.''is parent of'' using asc1;
Relate ref3 to ref1 across R7.''is child of'' using asc2;
Relate ref4 to ref5 across R7.''is parent of'' using asc3;

// REF-02-02
ref_left = ref1;
ref_right = ref1;
found = false;
Select many right_set related by ref_left->REF[R7.''is parent of''];
For each ref_right in right_set
  If((ref_right.ref_id == 2) or (ref_right.ref_id == 3))
    found = true;
  Else
    found = false;
    break;
  End If;
End For;

If (found)
  LOG::LogSuccess(message:"REF-02-02-1: OK");
Else
  LOG::LogFailure(message:"REF-02-02-1: ERROR");
End If;

// REF-03-02
ref_left = ref2;
ref_right = ref2;
found = false;
Select one ref_left related by ref_right->REF[R7.''is child of''];

If (ref_left.ref_id == 1)
  LOG::LogSuccess(message:"REF-02-02-2: OK");
Else
  LOG::LogFailure(message:"REF-02-02-2: ERROR");
End If;

Generate TS22:NEXT() to Self;',
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
	'Select any ref1 from instances of REF where (selected.ref_id == 1);
Select any ref2 from instances of REF where (selected.ref_id == 2);
Select any ref3 from instances of REF where (selected.ref_id == 3);
Select any ref4 from instances of REF where (selected.ref_id == 4);
Select any ref5 from instances of REF where (selected.ref_id == 5);

Select any asc1 from instances of ASC6 where (selected.id == 1);
Select any asc2 from instances of ASC6 where (selected.id == 2);
Select any asc3 from instances of ASC6 where (selected.id == 3);

// Relate instances
Unrelate ref2 from ref1 across R7.''is child of'' using asc1;
Unrelate ref1 from ref3 across R7.''is parent of'' using asc2;
Unrelate ref5 from ref4 across R7.''is child of'' using asc3;

Delete object instance ref1;
Delete object instance ref2;
Delete object instance ref3;
Delete object instance ref4;
Delete object instance ref5;

Delete object instance asc1;
Delete object instance asc2;
Delete object instance asc3;

Select any sc from instances of SC;
Generate SC2:DONE() to sc;',
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
	1840,
	1312,
	2208,
	1392);
INSERT INTO GD_GE
	VALUES (2097155,
	2097153,
	2097154,
	41);
INSERT INTO GD_SHP
	VALUES (2097155,
	1840,
	1472,
	2208,
	1552);
INSERT INTO GD_GE
	VALUES (2097156,
	2097153,
	2097155,
	41);
INSERT INTO GD_SHP
	VALUES (2097156,
	1840,
	1616,
	2208,
	1680);
INSERT INTO GD_GE
	VALUES (2097157,
	2097153,
	2097156,
	41);
INSERT INTO GD_SHP
	VALUES (2097157,
	1840,
	1744,
	2208,
	1808);
INSERT INTO GD_GE
	VALUES (2097158,
	2097153,
	2097157,
	41);
INSERT INTO GD_SHP
	VALUES (2097158,
	1840,
	1872,
	2208,
	1936);
INSERT INTO GD_GE
	VALUES (2097159,
	2097153,
	2097158,
	41);
INSERT INTO GD_SHP
	VALUES (2097159,
	1840,
	2000,
	2208,
	2096);
INSERT INTO GD_GE
	VALUES (2097160,
	2097153,
	2097153,
	42);
INSERT INTO GD_CON
	VALUES (2097160,
	2097154,
	2097154,
	0);
INSERT INTO GD_CTXT
	VALUES (2097160,
	0,
	0,
	0,
	0,
	0,
	0,
	1967,
	1230,
	2111,
	1266,
	-32,
	-9,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097161,
	2097160,
	2112,
	1312,
	2112,
	1264,
	0);
INSERT INTO GD_LS
	VALUES (2097162,
	2097160,
	2112,
	1264,
	1936,
	1264,
	2097161);
INSERT INTO GD_LS
	VALUES (2097163,
	2097160,
	1936,
	1264,
	1936,
	1312,
	2097162);
INSERT INTO GD_GE
	VALUES (2097164,
	2097153,
	2097154,
	42);
INSERT INTO GD_CON
	VALUES (2097164,
	2097154,
	2097155,
	0);
INSERT INTO GD_CTXT
	VALUES (2097164,
	0,
	0,
	0,
	0,
	0,
	0,
	1858,
	1423,
	2014,
	1458,
	-142,
	6,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097165,
	2097164,
	2016,
	1392,
	2016,
	1472,
	0);
INSERT INTO GD_GE
	VALUES (2097166,
	2097153,
	2097156,
	42);
INSERT INTO GD_CON
	VALUES (2097166,
	2097155,
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
	1877,
	1566,
	2007,
	1604,
	-123,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097167,
	2097166,
	2016,
	1552,
	2016,
	1616,
	0);
INSERT INTO GD_GE
	VALUES (2097168,
	2097153,
	2097157,
	42);
INSERT INTO GD_CON
	VALUES (2097168,
	2097156,
	2097157,
	0);
INSERT INTO GD_CTXT
	VALUES (2097168,
	0,
	0,
	0,
	0,
	0,
	0,
	1878,
	1694,
	2006,
	1727,
	-122,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097169,
	2097168,
	2016,
	1680,
	2016,
	1744,
	0);
INSERT INTO GD_GE
	VALUES (2097170,
	2097153,
	2097158,
	42);
INSERT INTO GD_CON
	VALUES (2097170,
	2097157,
	2097158,
	0);
INSERT INTO GD_CTXT
	VALUES (2097170,
	0,
	0,
	0,
	0,
	0,
	0,
	1879,
	1823,
	2024,
	1861,
	-121,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097171,
	2097170,
	2016,
	1808,
	2016,
	1872,
	0);
INSERT INTO GD_GE
	VALUES (2097172,
	2097153,
	2097159,
	42);
INSERT INTO GD_CON
	VALUES (2097172,
	2097158,
	2097159,
	0);
INSERT INTO GD_CTXT
	VALUES (2097172,
	0,
	0,
	0,
	0,
	0,
	0,
	1886,
	1950,
	2020,
	1990,
	-114,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097173,
	2097172,
	2016,
	1936,
	2016,
	2000,
	0);
INSERT INTO GD_GE
	VALUES (2097174,
	2097153,
	2097155,
	42);
INSERT INTO GD_CON
	VALUES (2097174,
	2097159,
	2097154,
	0);
INSERT INTO GD_CTXT
	VALUES (2097174,
	0,
	0,
	0,
	0,
	0,
	0,
	2288,
	1688,
	2422,
	1719,
	0,
	7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097175,
	2097174,
	2208,
	2032,
	2304,
	2032,
	0);
INSERT INTO GD_LS
	VALUES (2097176,
	2097174,
	2304,
	2032,
	2304,
	1360,
	2097175);
INSERT INTO GD_LS
	VALUES (2097177,
	2097174,
	2304,
	1360,
	2208,
	1360,
	2097176);
INSERT INTO GD_GE
	VALUES (2097178,
	2097153,
	2097160,
	42);
INSERT INTO GD_CON
	VALUES (2097178,
	2097154,
	2097156,
	0);
INSERT INTO GD_CTXT
	VALUES (2097178,
	0,
	0,
	0,
	0,
	0,
	0,
	1709,
	1659,
	1825,
	1694,
	-51,
	162,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097179,
	2097178,
	1840,
	1376,
	1776,
	1376,
	0);
INSERT INTO GD_LS
	VALUES (2097180,
	2097178,
	1776,
	1376,
	1776,
	1648,
	2097179);
INSERT INTO GD_LS
	VALUES (2097181,
	2097178,
	1776,
	1648,
	1840,
	1648,
	2097180);
INSERT INTO GD_GE
	VALUES (2097182,
	2097153,
	2097161,
	42);
INSERT INTO GD_CON
	VALUES (2097182,
	2097154,
	2097158,
	0);
INSERT INTO GD_CTXT
	VALUES (2097182,
	0,
	0,
	0,
	0,
	0,
	0,
	1660,
	1915,
	1800,
	1948,
	44,
	314,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097183,
	2097182,
	1840,
	1328,
	1632,
	1328,
	0);
INSERT INTO GD_LS
	VALUES (2097184,
	2097182,
	1632,
	1328,
	1632,
	1904,
	2097183);
INSERT INTO GD_LS
	VALUES (2097185,
	2097182,
	1632,
	1904,
	1840,
	1904,
	2097184);
INSERT INTO O_OBJ
	VALUES (6815752,
	'Test3',
	19,
	'TS3',
	'',
	6815757);
INSERT INTO O_NBATTR
	VALUES (6815763,
	6815752);
INSERT INTO O_BATTR
	VALUES (6815763,
	6815752);
INSERT INTO O_ATTR
	VALUES (6815763,
	6815752,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (6815764,
	6815752);
INSERT INTO O_BATTR
	VALUES (6815764,
	6815752);
INSERT INTO O_ATTR
	VALUES (6815764,
	6815752,
	6815763,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	6815752);
INSERT INTO O_OIDA
	VALUES (6815763,
	6815752,
	0);
INSERT INTO SM_ISM
	VALUES (2621445,
	6815752);
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
	'Testing_03_01',
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
	'TEST',
	0,
	'',
	'TS31',
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
	'NEXT',
	0,
	'',
	'TS32',
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
	'test2',
	0,
	'',
	'TS33',
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
	'Cleaning_03_01',
	2,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_SEME
	VALUES (2621442,
	2621442,
	2621445,
	2621441);
INSERT INTO SM_EIGN
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
	'Testing_03_02',
	3,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_SEME
	VALUES (2621443,
	2621442,
	2621445,
	2621441);
INSERT INTO SM_EIGN
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
INSERT INTO SM_STATE
	VALUES (2621444,
	2621445,
	2621441,
	'Cleaning_03_02',
	4,
	0);
INSERT INTO SM_SEME
	VALUES (2621444,
	2621441,
	2621445,
	2621441);
INSERT INTO SM_EIGN
	VALUES (2621444,
	2621442,
	2621445,
	2621441,
	'');
INSERT INTO SM_SEME
	VALUES (2621444,
	2621442,
	2621445,
	2621441);
INSERT INTO SM_EIGN
	VALUES (2621444,
	2621443,
	2621445,
	2621441,
	'');
INSERT INTO SM_SEME
	VALUES (2621444,
	2621443,
	2621445,
	2621441);
INSERT INTO SM_NSTXN
	VALUES (2621441,
	2621445,
	2621441,
	2621441,
	2621441);
INSERT INTO SM_TXN
	VALUES (2621441,
	2621445,
	2621441,
	2621441);
INSERT INTO SM_NSTXN
	VALUES (2621442,
	2621445,
	2621441,
	2621442,
	2621441);
INSERT INTO SM_TXN
	VALUES (2621442,
	2621445,
	2621442,
	2621441);
INSERT INTO SM_NSTXN
	VALUES (2621443,
	2621445,
	2621442,
	2621442,
	2621441);
INSERT INTO SM_TXN
	VALUES (2621443,
	2621445,
	2621443,
	2621441);
INSERT INTO SM_NSTXN
	VALUES (2621444,
	2621445,
	2621443,
	2621442,
	2621441);
INSERT INTO SM_TXN
	VALUES (2621444,
	2621445,
	2621444,
	2621441);
INSERT INTO SM_NSTXN
	VALUES (2621445,
	2621445,
	2621444,
	2621441,
	2621441);
INSERT INTO SM_TXN
	VALUES (2621445,
	2621445,
	2621441,
	2621441);
INSERT INTO SM_NSTXN
	VALUES (2621446,
	2621445,
	2621441,
	2621443,
	2621441);
INSERT INTO SM_TXN
	VALUES (2621446,
	2621445,
	2621443,
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
	'// Create instances
Create object instance ref1 of REF;
ref1.ref_id = 1;
Create object instance ref2 of REF;
ref2.ref_id = 2;
Create object instance ref3 of REF;
ref3.ref_id = 3;
Create object instance ref4 of REF;
ref4.ref_id = 4;

Create object instance asc1 of ASC3;
asc1.id = 1;
Create object instance asc2 of ASC3;
asc2.id = 2;
Create object instance asc3 of ASC3;
asc3.id = 3;
Create object instance asc4 of ASC3;
asc4.id = 4;

// Relate instances
Relate ref1 to ref3 across R5.''is left of'' using asc1;
Relate ref4 to ref1 across R5.''is right of'' using asc2;
Relate ref2 to ref3 across R5.''is left of'' using asc3;
Relate ref4 to ref2 across R5.''is right of'' using asc4;

// REF-03-01
left = ref1;
found = false;
Select many right_set related by left->REF[R5.''is left of''];
For each right in right_set
  If((right.ref_id == 3) or (right.ref_id == 4))
    found = true;
  else
    found = false;
  end if;
End For;

If (found)
  LOG::LogSuccess(message:"REF-03-01: OK");
Else
  LOG::LogFailure(message:"REF-03-01: ERROR");
End If;

Generate TS32:NEXT() to Self;',
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
	'Select any ref1 from instances of REF where (selected.ref_id == 1);
Select any ref2 from instances of REF where (selected.ref_id == 2);
Select any ref3 from instances of REF where (selected.ref_id == 3);
Select any ref4 from instances of REF where (selected.ref_id == 4);

Select any asc1 from instances of ASC3 where (selected.id == 1);
Select any asc2 from instances of ASC3 where (selected.id == 2);
Select any asc3 from instances of ASC3 where (selected.id == 3);
Select any asc4 from instances of ASC3 where (selected.id == 4);

// Relate instances
Unrelate ref1 from ref3 across R5.''is left of'' using asc1;
Unrelate ref4 from ref1 across R5.''is right of'' using asc2;
Unrelate ref2 from ref3 across R5.''is left of'' using asc3;
Unrelate ref4 from ref2 across R5.''is right of'' using asc4;

Delete object instance ref1;
Delete object instance ref2;
Delete object instance ref3;
Delete object instance ref4;

Delete object instance asc1;
Delete object instance asc2;
Delete object instance asc3;
Delete object instance asc4;

Generate TS32:NEXT() to Self;',
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
	'// Create instances
Create object instance ref1 of REF;
ref1.ref_id = 1;
Create object instance ref2 of REF;
ref2.ref_id = 2;
Create object instance ref3 of REF;
ref3.ref_id = 3;
Create object instance ref4 of REF;
ref4.ref_id = 4;

Create object instance asc1 of ASC5;
asc1.id = 1;
Create object instance asc2 of ASC5;
asc2.id = 2;
Create object instance asc3 of ASC5;
asc3.id = 3;
Create object instance asc4 of ASC5;
asc4.id = 4;

// Relate instances
Relate ref1 to ref3 across R8.''is left of'' using asc1;
Relate ref4 to ref1 across R8.''is right of'' using asc2;
Relate ref2 to ref3 across R8.''is left of'' using asc3;
Relate ref4 to ref2 across R8.''is right of'' using asc4;

// REF-03-01
left = ref1;
found = false;
Select many right_set related by left->REF[R8.''is left of''];
For each right in right_set
  If((right.ref_id == 3) or (right.ref_id == 4))
    found = true;
  else
    found = false;
  end if;
End For;

If (found)
  LOG::LogSuccess(message:"REF-03-02: OK");
Else
  LOG::LogFailure(message:"REF-03-02: ERROR");
End If;

Generate TS32:NEXT() to Self;',
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
	'Select any ref1 from instances of REF where (selected.ref_id == 1);
Select any ref2 from instances of REF where (selected.ref_id == 2);
Select any ref3 from instances of REF where (selected.ref_id == 3);
Select any ref4 from instances of REF where (selected.ref_id == 4);

Select any asc1 from instances of ASC5 where (selected.id == 1);
Select any asc2 from instances of ASC5 where (selected.id == 2);
Select any asc3 from instances of ASC5 where (selected.id == 3);
Select any asc4 from instances of ASC5 where (selected.id == 4);

// Relate instances
Unrelate ref1 from ref3 across R8.''is left of'' using asc1;
Unrelate ref4 from ref1 across R8.''is right of'' using asc2;
Unrelate ref2 from ref3 across R8.''is left of'' using asc3;
Unrelate ref4 from ref2 across R8.''is right of'' using asc4;

Delete object instance ref1;
Delete object instance ref2;
Delete object instance ref3;
Delete object instance ref4;

Delete object instance asc1;
Delete object instance asc2;
Delete object instance asc3;
Delete object instance asc4;

Select any sc from instances of SC;
Generate SC2:DONE() to sc;',
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
	1312,
	2176,
	1408);
INSERT INTO GD_GE
	VALUES (2621443,
	2621441,
	2621442,
	41);
INSERT INTO GD_SHP
	VALUES (2621443,
	1824,
	1472,
	2176,
	1568);
INSERT INTO GD_GE
	VALUES (2621444,
	2621441,
	2621443,
	41);
INSERT INTO GD_SHP
	VALUES (2621444,
	1824,
	1648,
	2176,
	1744);
INSERT INTO GD_GE
	VALUES (2621445,
	2621441,
	2621444,
	41);
INSERT INTO GD_SHP
	VALUES (2621445,
	1824,
	1824,
	2176,
	1920);
INSERT INTO GD_GE
	VALUES (2621446,
	2621441,
	2621441,
	42);
INSERT INTO GD_CON
	VALUES (2621446,
	2621442,
	2621442,
	0);
INSERT INTO GD_CTXT
	VALUES (2621446,
	0,
	0,
	0,
	0,
	0,
	0,
	1926,
	1225,
	2047,
	1262,
	-41,
	-14,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621447,
	2621446,
	2080,
	1312,
	2080,
	1264,
	0);
INSERT INTO GD_LS
	VALUES (2621448,
	2621446,
	2080,
	1264,
	1904,
	1264,
	2621447);
INSERT INTO GD_LS
	VALUES (2621449,
	2621446,
	1904,
	1264,
	1904,
	1312,
	2621448);
INSERT INTO GD_GE
	VALUES (2621450,
	2621441,
	2621442,
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
	1869,
	1427,
	1992,
	1461,
	-115,
	2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621451,
	2621450,
	2000,
	1408,
	2000,
	1472,
	0);
INSERT INTO GD_GE
	VALUES (2621452,
	2621441,
	2621445,
	42);
INSERT INTO GD_CON
	VALUES (2621452,
	2621445,
	2621442,
	0);
INSERT INTO GD_CTXT
	VALUES (2621452,
	0,
	0,
	0,
	0,
	0,
	0,
	2272,
	1600,
	2396,
	1630,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621453,
	2621452,
	2176,
	1872,
	2288,
	1872,
	0);
INSERT INTO GD_LS
	VALUES (2621454,
	2621452,
	2288,
	1872,
	2288,
	1360,
	2621453);
INSERT INTO GD_LS
	VALUES (2621455,
	2621452,
	2288,
	1360,
	2176,
	1360,
	2621454);
INSERT INTO GD_GE
	VALUES (2621456,
	2621441,
	2621443,
	42);
INSERT INTO GD_CON
	VALUES (2621456,
	2621443,
	2621444,
	0);
INSERT INTO GD_CTXT
	VALUES (2621456,
	0,
	0,
	0,
	0,
	0,
	0,
	1885,
	1595,
	1992,
	1627,
	-99,
	2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621457,
	2621456,
	2000,
	1568,
	2000,
	1648,
	0);
INSERT INTO GD_GE
	VALUES (2621458,
	2621441,
	2621444,
	42);
INSERT INTO GD_CON
	VALUES (2621458,
	2621444,
	2621445,
	0);
INSERT INTO GD_CTXT
	VALUES (2621458,
	0,
	0,
	0,
	0,
	0,
	0,
	1865,
	1768,
	2001,
	1804,
	-119,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621459,
	2621458,
	2000,
	1744,
	2000,
	1824,
	0);
INSERT INTO GD_GE
	VALUES (2621460,
	2621441,
	2621446,
	42);
INSERT INTO GD_CON
	VALUES (2621460,
	2621442,
	2621444,
	0);
INSERT INTO GD_CTXT
	VALUES (2621460,
	0,
	0,
	0,
	0,
	0,
	0,
	1687,
	1710,
	1802,
	1744,
	-9,
	197,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621461,
	2621460,
	1824,
	1360,
	1712,
	1360,
	0);
INSERT INTO GD_LS
	VALUES (2621462,
	2621460,
	1712,
	1360,
	1712,
	1696,
	2621461);
INSERT INTO GD_LS
	VALUES (2621463,
	2621460,
	1712,
	1696,
	1824,
	1696,
	2621462);
INSERT INTO O_OBJ
	VALUES (6815753,
	'asc4',
	20,
	'ASC4',
	'',
	6815757);
INSERT INTO O_NBATTR
	VALUES (6815765,
	6815753);
INSERT INTO O_BATTR
	VALUES (6815765,
	6815753);
INSERT INTO O_ATTR
	VALUES (6815765,
	6815753,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524291);
INSERT INTO O_REF
	VALUES (6815753,
	6815745,
	0,
	6815745,
	6815750,
	6815760,
	6815759,
	6815766,
	6815751,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (6815766,
	6815753,
	6815745,
	6815745,
	0);
INSERT INTO O_ATTR
	VALUES (6815766,
	6815753,
	6815765,
	'l_id',
	'',
	'',
	'l_id',
	0,
	524296);
INSERT INTO O_REF
	VALUES (6815753,
	6815745,
	0,
	6815745,
	6815750,
	6815760,
	6815758,
	6815767,
	6815752,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (6815767,
	6815753,
	6815745,
	6815745,
	0);
INSERT INTO O_ATTR
	VALUES (6815767,
	6815753,
	6815766,
	'r_id',
	'',
	'',
	'r_id',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	6815753);
INSERT INTO O_OIDA
	VALUES (6815765,
	6815753,
	0);
INSERT INTO O_OIDA
	VALUES (6815766,
	6815753,
	0);
INSERT INTO O_OBJ
	VALUES (6815754,
	'asc6',
	21,
	'ASC6',
	'',
	6815757);
INSERT INTO O_NBATTR
	VALUES (6815768,
	6815754);
INSERT INTO O_BATTR
	VALUES (6815768,
	6815754);
INSERT INTO O_ATTR
	VALUES (6815768,
	6815754,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524291);
INSERT INTO O_REF
	VALUES (6815754,
	6815745,
	0,
	6815745,
	6815751,
	6815763,
	6815761,
	6815769,
	6815753,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (6815769,
	6815754,
	6815745,
	6815745,
	0);
INSERT INTO O_ATTR
	VALUES (6815769,
	6815754,
	6815768,
	'p_id',
	'',
	'',
	'p_id',
	0,
	524296);
INSERT INTO O_REF
	VALUES (6815754,
	6815745,
	0,
	6815745,
	6815751,
	6815763,
	6815762,
	6815770,
	6815754,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (6815770,
	6815754,
	6815745,
	6815745,
	0);
INSERT INTO O_ATTR
	VALUES (6815770,
	6815754,
	6815769,
	'c_id',
	'',
	'',
	'c_id',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	6815754);
INSERT INTO O_OIDA
	VALUES (6815769,
	6815754,
	0);
INSERT INTO O_OIDA
	VALUES (6815768,
	6815754,
	0);
INSERT INTO O_OBJ
	VALUES (6815755,
	'asc5',
	22,
	'ASC5',
	'',
	6815757);
INSERT INTO O_NBATTR
	VALUES (6815771,
	6815755);
INSERT INTO O_BATTR
	VALUES (6815771,
	6815755);
INSERT INTO O_ATTR
	VALUES (6815771,
	6815755,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524291);
INSERT INTO O_REF
	VALUES (6815755,
	6815745,
	0,
	6815745,
	6815752,
	6815766,
	6815765,
	6815772,
	6815755,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (6815772,
	6815755,
	6815745,
	6815745,
	0);
INSERT INTO O_ATTR
	VALUES (6815772,
	6815755,
	6815771,
	'l_id',
	'',
	'',
	'l_id',
	0,
	524296);
INSERT INTO O_REF
	VALUES (6815755,
	6815745,
	0,
	6815745,
	6815752,
	6815766,
	6815764,
	6815773,
	6815756,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (6815773,
	6815755,
	6815745,
	6815745,
	0);
INSERT INTO O_ATTR
	VALUES (6815773,
	6815755,
	6815772,
	'r_id',
	'',
	'',
	'r_id',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	6815755);
INSERT INTO O_OIDA
	VALUES (6815773,
	6815755,
	0);
INSERT INTO O_OIDA
	VALUES (6815771,
	6815755,
	0);
INSERT INTO O_OIDA
	VALUES (6815772,
	6815755,
	0);
INSERT INTO O_OBJ
	VALUES (6815756,
	'Scenario',
	23,
	'SC',
	'',
	6815757);
INSERT INTO O_NBATTR
	VALUES (6815774,
	6815756);
INSERT INTO O_BATTR
	VALUES (6815774,
	6815756);
INSERT INTO O_ATTR
	VALUES (6815774,
	6815756,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (6815775,
	6815756);
INSERT INTO O_BATTR
	VALUES (6815775,
	6815756);
INSERT INTO O_ATTR
	VALUES (6815775,
	6815756,
	6815774,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_NBATTR
	VALUES (6815776,
	6815756);
INSERT INTO O_BATTR
	VALUES (6815776,
	6815756);
INSERT INTO O_ATTR
	VALUES (6815776,
	6815756,
	6815775,
	'count',
	'',
	'',
	'count',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	6815756);
INSERT INTO O_OIDA
	VALUES (6815774,
	6815756,
	0);
INSERT INTO SM_ISM
	VALUES (3145734,
	6815756);
INSERT INTO SM_SM
	VALUES (3145734,
	'',
	6);
INSERT INTO SM_MOORE
	VALUES (3145734);
INSERT INTO SM_SUPDT
	VALUES (3145729,
	3145734,
	0);
INSERT INTO SM_STATE
	VALUES (3145729,
	3145734,
	0,
	'Created',
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
	'START',
	0,
	'',
	'SC1',
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_LEVT
	VALUES (3145730,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145730,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145730,
	3145734,
	3145729,
	2,
	'DONE',
	0,
	'',
	'SC2',
	'');
INSERT INTO SM_EIGN
	VALUES (3145729,
	3145730,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145730,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145730,
	3145734,
	3145729,
	'Testing_01',
	2,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_SEME
	VALUES (3145730,
	3145730,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145731,
	3145734,
	3145729,
	'Testing_02',
	3,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_SEME
	VALUES (3145731,
	3145730,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145732,
	3145734,
	3145729,
	'Testing_03',
	4,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_SEME
	VALUES (3145732,
	3145730,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145733,
	3145734,
	3145729,
	'Loop',
	5,
	0);
INSERT INTO SM_SEME
	VALUES (3145733,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145733,
	3145730,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145730,
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
	3145730,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145730,
	3145734,
	3145731,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145731,
	3145734,
	3145732,
	3145730,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145731,
	3145734,
	3145733,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145732,
	3145734,
	3145731,
	3145730,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145732,
	3145734,
	3145732,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145733,
	3145734,
	3145733,
	3145729,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145733,
	3145734,
	3145730,
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
	'',
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
	'select any test1 from instances of TS1;
Generate TS11:TEST() to test1;',
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
	'select any test2 from instances of TS2;
Generate TS21:TEST() to test2;',
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
	'select any test3 from instances of TS3;
Generate TS31:TEST() to test3;',
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
	'self.count = self.count -1;
If (self.count > 0)
  Generate SC1:START() to Self;
End If;

ARCH::shutdown();',
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
	1744,
	1232,
	2128,
	1280);
INSERT INTO GD_GE
	VALUES (3145731,
	3145729,
	3145730,
	41);
INSERT INTO GD_SHP
	VALUES (3145731,
	1744,
	1360,
	2128,
	1456);
INSERT INTO GD_GE
	VALUES (3145732,
	3145729,
	3145731,
	41);
INSERT INTO GD_SHP
	VALUES (3145732,
	1744,
	1536,
	2128,
	1632);
INSERT INTO GD_GE
	VALUES (3145733,
	3145729,
	3145732,
	41);
INSERT INTO GD_SHP
	VALUES (3145733,
	1744,
	1728,
	2128,
	1824);
INSERT INTO GD_GE
	VALUES (3145734,
	3145729,
	3145733,
	41);
INSERT INTO GD_SHP
	VALUES (3145734,
	1744,
	1904,
	2128,
	2048);
INSERT INTO GD_GE
	VALUES (3145735,
	3145729,
	3145729,
	42);
INSERT INTO GD_CON
	VALUES (3145735,
	3145730,
	3145731,
	0);
INSERT INTO GD_CTXT
	VALUES (3145735,
	0,
	0,
	0,
	0,
	0,
	0,
	1820,
	1307,
	1941,
	1342,
	-116,
	2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145736,
	3145735,
	1952,
	1280,
	1952,
	1360,
	0);
INSERT INTO GD_GE
	VALUES (3145737,
	3145729,
	3145730,
	42);
INSERT INTO GD_CON
	VALUES (3145737,
	3145731,
	3145732,
	0);
INSERT INTO GD_CTXT
	VALUES (3145737,
	0,
	0,
	0,
	0,
	0,
	0,
	1811,
	1478,
	1944,
	1512,
	-125,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145738,
	3145737,
	1952,
	1456,
	1952,
	1536,
	0);
INSERT INTO GD_GE
	VALUES (3145739,
	3145729,
	3145732,
	42);
INSERT INTO GD_CON
	VALUES (3145739,
	3145732,
	3145733,
	0);
INSERT INTO GD_CTXT
	VALUES (3145739,
	0,
	0,
	0,
	0,
	0,
	0,
	1814,
	1662,
	1945,
	1698,
	-122,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145740,
	3145739,
	1952,
	1632,
	1952,
	1728,
	0);
INSERT INTO GD_GE
	VALUES (3145741,
	3145729,
	3145731,
	42);
INSERT INTO GD_CON
	VALUES (3145741,
	3145733,
	3145734,
	0);
INSERT INTO GD_CTXT
	VALUES (3145741,
	0,
	0,
	0,
	0,
	0,
	0,
	1811,
	1849,
	1958,
	1883,
	-125,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145742,
	3145741,
	1952,
	1824,
	1952,
	1904,
	0);
INSERT INTO GD_GE
	VALUES (3145743,
	3145729,
	3145733,
	42);
INSERT INTO GD_CON
	VALUES (3145743,
	3145734,
	3145731,
	0);
INSERT INTO GD_CTXT
	VALUES (3145743,
	0,
	0,
	0,
	0,
	0,
	0,
	2222,
	1683,
	2358,
	1718,
	14,
	2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145744,
	3145743,
	2128,
	1984,
	2224,
	1984,
	0);
INSERT INTO GD_LS
	VALUES (3145745,
	3145743,
	2224,
	1984,
	2224,
	1408,
	3145744);
INSERT INTO GD_LS
	VALUES (3145746,
	3145743,
	2224,
	1408,
	2128,
	1408,
	3145745);
INSERT INTO R_SIMP
	VALUES (6815745);
INSERT INTO R_REL
	VALUES (6815745,
	2,
	'',
	6815757);
INSERT INTO R_FORM
	VALUES (6815745,
	6815745,
	6815745,
	1,
	1,
	'is parent of');
INSERT INTO R_RGO
	VALUES (6815745,
	6815745,
	6815745);
INSERT INTO R_OIR
	VALUES (6815745,
	6815745,
	6815745,
	0);
INSERT INTO R_PART
	VALUES (6815745,
	6815745,
	6815746,
	0,
	1,
	'is child of');
INSERT INTO R_RTO
	VALUES (6815745,
	6815745,
	6815746,
	0);
INSERT INTO R_OIR
	VALUES (6815745,
	6815745,
	6815746,
	0);
INSERT INTO R_ASSOC
	VALUES (6815746);
INSERT INTO R_REL
	VALUES (6815746,
	5,
	'',
	6815757);
INSERT INTO R_AONE
	VALUES (6815745,
	6815746,
	6815747,
	1,
	1,
	'is left of');
INSERT INTO R_RTO
	VALUES (6815745,
	6815746,
	6815747,
	0);
INSERT INTO R_OIR
	VALUES (6815745,
	6815746,
	6815747,
	0);
INSERT INTO R_AOTH
	VALUES (6815745,
	6815746,
	6815748,
	1,
	1,
	'is right of');
INSERT INTO R_RTO
	VALUES (6815745,
	6815746,
	6815748,
	0);
INSERT INTO R_OIR
	VALUES (6815745,
	6815746,
	6815748,
	0);
INSERT INTO R_ASSR
	VALUES (6815750,
	6815746,
	6815749,
	0);
INSERT INTO R_RGO
	VALUES (6815750,
	6815746,
	6815749);
INSERT INTO R_OIR
	VALUES (6815750,
	6815746,
	6815749,
	0);
INSERT INTO R_ASSOC
	VALUES (6815747);
INSERT INTO R_REL
	VALUES (6815747,
	4,
	'',
	6815757);
INSERT INTO R_AONE
	VALUES (6815745,
	6815747,
	6815750,
	0,
	1,
	'is child of');
INSERT INTO R_RTO
	VALUES (6815745,
	6815747,
	6815750,
	0);
INSERT INTO R_OIR
	VALUES (6815745,
	6815747,
	6815750,
	0);
INSERT INTO R_AOTH
	VALUES (6815745,
	6815747,
	6815751,
	1,
	1,
	'is parent of');
INSERT INTO R_RTO
	VALUES (6815745,
	6815747,
	6815751,
	0);
INSERT INTO R_OIR
	VALUES (6815745,
	6815747,
	6815751,
	0);
INSERT INTO R_ASSR
	VALUES (6815746,
	6815747,
	6815752,
	0);
INSERT INTO R_RGO
	VALUES (6815746,
	6815747,
	6815752);
INSERT INTO R_OIR
	VALUES (6815746,
	6815747,
	6815752,
	0);
INSERT INTO R_SIMP
	VALUES (6815748);
INSERT INTO R_REL
	VALUES (6815748,
	1,
	'',
	6815757);
INSERT INTO R_PART
	VALUES (6815745,
	6815748,
	6815753,
	0,
	1,
	'is left of');
INSERT INTO R_RTO
	VALUES (6815745,
	6815748,
	6815753,
	0);
INSERT INTO R_OIR
	VALUES (6815745,
	6815748,
	6815753,
	0);
INSERT INTO R_FORM
	VALUES (6815745,
	6815748,
	6815754,
	0,
	1,
	'is right of');
INSERT INTO R_RGO
	VALUES (6815745,
	6815748,
	6815754);
INSERT INTO R_OIR
	VALUES (6815745,
	6815748,
	6815754,
	0);
INSERT INTO R_ASSOC
	VALUES (6815749);
INSERT INTO R_REL
	VALUES (6815749,
	3,
	'',
	6815757);
INSERT INTO R_AONE
	VALUES (6815745,
	6815749,
	6815755,
	0,
	1,
	'is right of');
INSERT INTO R_RTO
	VALUES (6815745,
	6815749,
	6815755,
	0);
INSERT INTO R_OIR
	VALUES (6815745,
	6815749,
	6815755,
	0);
INSERT INTO R_AOTH
	VALUES (6815745,
	6815749,
	6815756,
	0,
	1,
	'is left of');
INSERT INTO R_RTO
	VALUES (6815745,
	6815749,
	6815756,
	0);
INSERT INTO R_OIR
	VALUES (6815745,
	6815749,
	6815756,
	0);
INSERT INTO R_ASSR
	VALUES (6815748,
	6815749,
	6815757,
	0);
INSERT INTO R_RGO
	VALUES (6815748,
	6815749,
	6815757);
INSERT INTO R_OIR
	VALUES (6815748,
	6815749,
	6815757,
	0);
INSERT INTO R_ASSOC
	VALUES (6815750);
INSERT INTO R_REL
	VALUES (6815750,
	6,
	'',
	6815757);
INSERT INTO R_AONE
	VALUES (6815745,
	6815750,
	6815758,
	0,
	1,
	'is left of');
INSERT INTO R_RTO
	VALUES (6815745,
	6815750,
	6815758,
	0);
INSERT INTO R_OIR
	VALUES (6815745,
	6815750,
	6815758,
	0);
INSERT INTO R_AOTH
	VALUES (6815745,
	6815750,
	6815759,
	0,
	1,
	'is right of');
INSERT INTO R_RTO
	VALUES (6815745,
	6815750,
	6815759,
	0);
INSERT INTO R_OIR
	VALUES (6815745,
	6815750,
	6815759,
	0);
INSERT INTO R_ASSR
	VALUES (6815753,
	6815750,
	6815760,
	1);
INSERT INTO R_RGO
	VALUES (6815753,
	6815750,
	6815760);
INSERT INTO R_OIR
	VALUES (6815753,
	6815750,
	6815760,
	0);
INSERT INTO R_ASSOC
	VALUES (6815751);
INSERT INTO R_REL
	VALUES (6815751,
	7,
	'',
	6815757);
INSERT INTO R_AONE
	VALUES (6815745,
	6815751,
	6815761,
	0,
	1,
	'is child of');
INSERT INTO R_RTO
	VALUES (6815745,
	6815751,
	6815761,
	0);
INSERT INTO R_OIR
	VALUES (6815745,
	6815751,
	6815761,
	0);
INSERT INTO R_AOTH
	VALUES (6815745,
	6815751,
	6815762,
	1,
	1,
	'is parent of');
INSERT INTO R_RTO
	VALUES (6815745,
	6815751,
	6815762,
	0);
INSERT INTO R_OIR
	VALUES (6815745,
	6815751,
	6815762,
	0);
INSERT INTO R_ASSR
	VALUES (6815754,
	6815751,
	6815763,
	1);
INSERT INTO R_RGO
	VALUES (6815754,
	6815751,
	6815763);
INSERT INTO R_OIR
	VALUES (6815754,
	6815751,
	6815763,
	0);
INSERT INTO R_ASSOC
	VALUES (6815752);
INSERT INTO R_REL
	VALUES (6815752,
	8,
	'',
	6815757);
INSERT INTO R_AONE
	VALUES (6815745,
	6815752,
	6815764,
	1,
	1,
	'is left of');
INSERT INTO R_RTO
	VALUES (6815745,
	6815752,
	6815764,
	0);
INSERT INTO R_OIR
	VALUES (6815745,
	6815752,
	6815764,
	0);
INSERT INTO R_AOTH
	VALUES (6815745,
	6815752,
	6815765,
	1,
	1,
	'is right of');
INSERT INTO R_RTO
	VALUES (6815745,
	6815752,
	6815765,
	0);
INSERT INTO R_OIR
	VALUES (6815745,
	6815752,
	6815765,
	0);
INSERT INTO R_ASSR
	VALUES (6815755,
	6815752,
	6815766,
	1);
INSERT INTO R_RGO
	VALUES (6815755,
	6815752,
	6815766);
INSERT INTO R_OIR
	VALUES (6815755,
	6815752,
	6815766,
	0);
INSERT INTO CA_COMM
	VALUES (1048579,
	6815757);
INSERT INTO CA_SMSMC
	VALUES (1048579,
	1048578,
	3145734,
	0);
INSERT INTO CA_SMSME
	VALUES (1048579,
	3145734,
	3145729);
INSERT INTO CA_ACC
	VALUES (1048579,
	6815757,
	1048578,
	0);
INSERT INTO CA_SMOA
	VALUES (1048579,
	6815756,
	0);
INSERT INTO CA_SMOAA
	VALUES (1048579,
	6815776,
	6815756);
INSERT INTO CA_COMM
	VALUES (1572866,
	6815757);
INSERT INTO CA_SMSMC
	VALUES (1572866,
	1572867,
	3145734,
	0);
INSERT INTO CA_SMSME
	VALUES (1572866,
	3145734,
	3145730);
INSERT INTO CA_ACC
	VALUES (1572865,
	6815757,
	1572867,
	0);
INSERT INTO CA_SMOA
	VALUES (1572865,
	6815745,
	0);
INSERT INTO CA_SMOAA
	VALUES (1572865,
	6815745,
	6815745);
INSERT INTO CA_ACC
	VALUES (1572866,
	6815757,
	1572867,
	0);
INSERT INTO CA_SMOA
	VALUES (1572866,
	6815748,
	0);
INSERT INTO CA_SMOAA
	VALUES (1572866,
	6815753,
	6815748);
INSERT INTO CA_ACC
	VALUES (1572867,
	6815757,
	1572867,
	0);
INSERT INTO CA_SMOA
	VALUES (1572867,
	6815753,
	0);
INSERT INTO CA_SMOAA
	VALUES (1572867,
	6815765,
	6815753);
INSERT INTO CA_COMM
	VALUES (2097154,
	6815757);
INSERT INTO CA_SMSMC
	VALUES (2097154,
	2097156,
	3145734,
	0);
INSERT INTO CA_SMSME
	VALUES (2097154,
	3145734,
	3145730);
INSERT INTO CA_ACC
	VALUES (2097153,
	6815757,
	2097156,
	0);
INSERT INTO CA_SMOA
	VALUES (2097153,
	6815745,
	0);
INSERT INTO CA_SMOAA
	VALUES (2097153,
	6815745,
	6815745);
INSERT INTO CA_ACC
	VALUES (2097154,
	6815757,
	2097156,
	0);
INSERT INTO CA_SMOA
	VALUES (2097154,
	6815746,
	0);
INSERT INTO CA_SMOAA
	VALUES (2097154,
	6815748,
	6815746);
INSERT INTO CA_ACC
	VALUES (2097155,
	6815757,
	2097156,
	0);
INSERT INTO CA_SMOA
	VALUES (2097155,
	6815754,
	0);
INSERT INTO CA_SMOAA
	VALUES (2097155,
	6815768,
	6815754);
INSERT INTO CA_COMM
	VALUES (2621442,
	6815757);
INSERT INTO CA_SMSMC
	VALUES (2621442,
	2621445,
	3145734,
	0);
INSERT INTO CA_SMSME
	VALUES (2621442,
	3145734,
	3145730);
INSERT INTO CA_ACC
	VALUES (2621441,
	6815757,
	2621445,
	0);
INSERT INTO CA_SMOA
	VALUES (2621441,
	6815745,
	0);
INSERT INTO CA_SMOAA
	VALUES (2621441,
	6815745,
	6815745);
INSERT INTO CA_ACC
	VALUES (2621442,
	6815757,
	2621445,
	0);
INSERT INTO CA_SMOA
	VALUES (2621442,
	6815750,
	0);
INSERT INTO CA_SMOAA
	VALUES (2621442,
	6815758,
	6815750);
INSERT INTO CA_ACC
	VALUES (2621443,
	6815757,
	2621445,
	0);
INSERT INTO CA_SMOA
	VALUES (2621443,
	6815755,
	0);
INSERT INTO CA_SMOAA
	VALUES (2621443,
	6815771,
	6815755);
INSERT INTO CA_COMM
	VALUES (3145732,
	6815757);
INSERT INTO CA_SMSMC
	VALUES (3145732,
	3145734,
	1572867,
	0);
INSERT INTO CA_SMSME
	VALUES (3145732,
	1572867,
	1572865);
INSERT INTO CA_COMM
	VALUES (3145733,
	6815757);
INSERT INTO CA_SMSMC
	VALUES (3145733,
	3145734,
	2097156,
	0);
INSERT INTO CA_SMSME
	VALUES (3145733,
	2097156,
	2097153);
INSERT INTO CA_COMM
	VALUES (3145734,
	6815757);
INSERT INTO CA_SMSMC
	VALUES (3145734,
	3145734,
	2621445,
	0);
INSERT INTO CA_SMSME
	VALUES (3145734,
	2621445,
	2621441);
INSERT INTO CA_ACC
	VALUES (3145731,
	6815757,
	3145734,
	0);
INSERT INTO CA_SMOA
	VALUES (3145731,
	6815756,
	0);
INSERT INTO CA_SMOAA
	VALUES (3145731,
	6815776,
	6815756);
INSERT INTO GD_MD
	VALUES (6815757,
	5,
	6815757,
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
	VALUES (6815760,
	6815757,
	6815745,
	21);
INSERT INTO GD_SHP
	VALUES (6815760,
	2064,
	1440,
	2240,
	2352);
INSERT INTO GD_GE
	VALUES (6815761,
	6815757,
	6815746,
	21);
INSERT INTO GD_SHP
	VALUES (6815761,
	1664,
	2016,
	1856,
	2144);
INSERT INTO GD_GE
	VALUES (6815762,
	6815757,
	6815747,
	21);
INSERT INTO GD_SHP
	VALUES (6815762,
	2128,
	1296,
	2368,
	1408);
INSERT INTO GD_GE
	VALUES (6815763,
	6815757,
	6815748,
	21);
INSERT INTO GD_SHP
	VALUES (6815763,
	1680,
	1552,
	1856,
	1680);
INSERT INTO GD_GE
	VALUES (6815764,
	6815757,
	6815749,
	21);
INSERT INTO GD_SHP
	VALUES (6815764,
	2448,
	1824,
	2624,
	1936);
INSERT INTO GD_GE
	VALUES (6815765,
	6815757,
	6815750,
	21);
INSERT INTO GD_SHP
	VALUES (6815765,
	2448,
	1456,
	2640,
	1584);
INSERT INTO GD_GE
	VALUES (6815766,
	6815757,
	6815751,
	21);
INSERT INTO GD_SHP
	VALUES (6815766,
	2448,
	1952,
	2624,
	2064);
INSERT INTO GD_GE
	VALUES (6815767,
	6815757,
	6815752,
	21);
INSERT INTO GD_SHP
	VALUES (6815767,
	2448,
	2080,
	2624,
	2192);
INSERT INTO GD_GE
	VALUES (6815768,
	6815757,
	6815753,
	21);
INSERT INTO GD_SHP
	VALUES (6815768,
	1680,
	1712,
	1856,
	1840);
INSERT INTO GD_GE
	VALUES (6815769,
	6815757,
	6815754,
	21);
INSERT INTO GD_SHP
	VALUES (6815769,
	1664,
	2192,
	1856,
	2352);
INSERT INTO GD_GE
	VALUES (6815770,
	6815757,
	6815755,
	21);
INSERT INTO GD_SHP
	VALUES (6815770,
	2448,
	1632,
	2640,
	1776);
INSERT INTO GD_GE
	VALUES (6815771,
	6815757,
	6815756,
	21);
INSERT INTO GD_SHP
	VALUES (6815771,
	2448,
	1296,
	2640,
	1408);
INSERT INTO GD_GE
	VALUES (6815806,
	6815757,
	6815745,
	24);
INSERT INTO GD_CON
	VALUES (6815806,
	6815760,
	6815760,
	0);
INSERT INTO GD_CTXT
	VALUES (6815806,
	1956,
	1968,
	2043,
	2000,
	-38,
	25,
	1931,
	1907,
	1981,
	1937,
	11,
	2,
	1956,
	1874,
	2056,
	1904,
	-38,
	27);
INSERT INTO GD_LS
	VALUES (6815807,
	6815806,
	2064,
	1968,
	1936,
	1968,
	0);
INSERT INTO GD_LS
	VALUES (6815808,
	6815806,
	1936,
	1968,
	1936,
	1872,
	6815807);
INSERT INTO GD_LS
	VALUES (6815809,
	6815806,
	1936,
	1872,
	2064,
	1872,
	6815808);
INSERT INTO GD_GE
	VALUES (6815810,
	6815757,
	6815746,
	24);
INSERT INTO GD_CON
	VALUES (6815810,
	6815760,
	6815760,
	6815812);
INSERT INTO GD_CTXT
	VALUES (6815810,
	2245,
	1473,
	2345,
	1503,
	51,
	26,
	2314,
	1504,
	2364,
	1534,
	-38,
	7,
	2256,
	1554,
	2356,
	1584,
	62,
	27);
INSERT INTO GD_LS
	VALUES (6815811,
	6815810,
	2240,
	1472,
	2368,
	1472,
	0);
INSERT INTO GD_LS
	VALUES (6815812,
	6815810,
	2368,
	1472,
	2368,
	1552,
	6815811);
INSERT INTO GD_LS
	VALUES (6815813,
	6815810,
	2368,
	1552,
	2240,
	1552,
	6815812);
INSERT INTO GD_GE
	VALUES (6815814,
	6815757,
	6815747,
	24);
INSERT INTO GD_CON
	VALUES (6815814,
	6815760,
	6815760,
	6815816);
INSERT INTO GD_CTXT
	VALUES (6815814,
	1953,
	2036,
	2053,
	2066,
	-41,
	29,
	1937,
	2079,
	1987,
	2109,
	17,
	14,
	1947,
	2135,
	2064,
	2170,
	-47,
	32);
INSERT INTO GD_LS
	VALUES (6815815,
	6815814,
	2064,
	2032,
	1936,
	2032,
	0);
INSERT INTO GD_LS
	VALUES (6815816,
	6815814,
	1936,
	2032,
	1936,
	2128,
	6815815);
INSERT INTO GD_LS
	VALUES (6815817,
	6815814,
	1936,
	2128,
	2064,
	2128,
	6815816);
INSERT INTO GD_GE
	VALUES (6815818,
	6815757,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (6815818,
	6815761,
	6815814,
	0);
INSERT INTO GD_CTXT
	VALUES (6815818,
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
	VALUES (6815819,
	6815818,
	1856,
	2080,
	1936,
	2080,
	0);
INSERT INTO GD_GE
	VALUES (6815820,
	6815757,
	6815748,
	24);
INSERT INTO GD_CON
	VALUES (6815820,
	6815760,
	6815760,
	0);
INSERT INTO GD_CTXT
	VALUES (6815820,
	1956,
	1537,
	2056,
	1567,
	-38,
	26,
	1883,
	1490,
	1933,
	1520,
	-37,
	1,
	1961,
	1473,
	2061,
	1503,
	-33,
	26);
INSERT INTO GD_LS
	VALUES (6815821,
	6815820,
	2064,
	1536,
	1936,
	1536,
	0);
INSERT INTO GD_LS
	VALUES (6815822,
	6815820,
	1936,
	1536,
	1936,
	1472,
	6815821);
INSERT INTO GD_LS
	VALUES (6815823,
	6815820,
	1936,
	1472,
	2064,
	1472,
	6815822);
INSERT INTO GD_GE
	VALUES (6815824,
	6815757,
	6815749,
	24);
INSERT INTO GD_CON
	VALUES (6815824,
	6815760,
	6815760,
	6815826);
INSERT INTO GD_CTXT
	VALUES (6815824,
	1954,
	1582,
	2054,
	1612,
	-40,
	23,
	1930,
	1614,
	1980,
	1644,
	10,
	13,
	1950,
	1648,
	2050,
	1678,
	-44,
	25);
INSERT INTO GD_LS
	VALUES (6815825,
	6815824,
	2064,
	1584,
	1936,
	1584,
	0);
INSERT INTO GD_LS
	VALUES (6815826,
	6815824,
	1936,
	1584,
	1936,
	1648,
	6815825);
INSERT INTO GD_LS
	VALUES (6815827,
	6815824,
	1936,
	1648,
	2064,
	1648,
	6815826);
INSERT INTO GD_GE
	VALUES (6815828,
	6815757,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (6815828,
	6815763,
	6815824,
	0);
INSERT INTO GD_CTXT
	VALUES (6815828,
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
	VALUES (6815829,
	6815828,
	1856,
	1616,
	1936,
	1616,
	0);
INSERT INTO GD_GE
	VALUES (6815830,
	6815757,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (6815830,
	6815765,
	6815810,
	0);
INSERT INTO GD_CTXT
	VALUES (6815830,
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
	VALUES (6815831,
	6815830,
	2448,
	1520,
	2368,
	1520,
	0);
INSERT INTO GD_GE
	VALUES (6815832,
	6815757,
	6815750,
	24);
INSERT INTO GD_CON
	VALUES (6815832,
	6815760,
	6815760,
	6815834);
INSERT INTO GD_CTXT
	VALUES (6815832,
	1955,
	1716,
	2055,
	1746,
	-39,
	29,
	1920,
	1744,
	1970,
	1774,
	0,
	-1,
	1957,
	1809,
	2057,
	1839,
	-37,
	26);
INSERT INTO GD_LS
	VALUES (6815833,
	6815832,
	2064,
	1712,
	1936,
	1712,
	0);
INSERT INTO GD_LS
	VALUES (6815834,
	6815832,
	1936,
	1712,
	1936,
	1808,
	6815833);
INSERT INTO GD_LS
	VALUES (6815835,
	6815832,
	1936,
	1808,
	2064,
	1808,
	6815834);
INSERT INTO GD_GE
	VALUES (6815836,
	6815757,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (6815836,
	6815768,
	6815832,
	0);
INSERT INTO GD_CTXT
	VALUES (6815836,
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
	VALUES (6815837,
	6815836,
	1856,
	1760,
	1936,
	1760,
	0);
INSERT INTO GD_GE
	VALUES (6815838,
	6815757,
	6815751,
	24);
INSERT INTO GD_CON
	VALUES (6815838,
	6815760,
	6815760,
	6815840);
INSERT INTO GD_CTXT
	VALUES (6815838,
	1958,
	2212,
	2058,
	2242,
	-36,
	29,
	1920,
	2256,
	1970,
	2286,
	0,
	7,
	1959,
	2317,
	2059,
	2347,
	-35,
	22);
INSERT INTO GD_LS
	VALUES (6815839,
	6815838,
	2064,
	2208,
	1936,
	2208,
	0);
INSERT INTO GD_LS
	VALUES (6815840,
	6815838,
	1936,
	2208,
	1936,
	2320,
	6815839);
INSERT INTO GD_LS
	VALUES (6815841,
	6815838,
	1936,
	2320,
	2064,
	2320,
	6815840);
INSERT INTO GD_GE
	VALUES (6815842,
	6815757,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (6815842,
	6815769,
	6815838,
	0);
INSERT INTO GD_CTXT
	VALUES (6815842,
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
	VALUES (6815843,
	6815842,
	1856,
	2256,
	1936,
	2256,
	0);
INSERT INTO GD_GE
	VALUES (6815844,
	6815757,
	6815752,
	24);
INSERT INTO GD_CON
	VALUES (6815844,
	6815760,
	6815760,
	6815846);
INSERT INTO GD_CTXT
	VALUES (6815844,
	2242,
	1640,
	2342,
	1670,
	48,
	33,
	2310,
	1675,
	2360,
	1705,
	-42,
	10,
	2255,
	1732,
	2355,
	1762,
	61,
	29);
INSERT INTO GD_LS
	VALUES (6815845,
	6815844,
	2240,
	1632,
	2368,
	1632,
	0);
INSERT INTO GD_LS
	VALUES (6815846,
	6815844,
	2368,
	1632,
	2368,
	1728,
	6815845);
INSERT INTO GD_LS
	VALUES (6815847,
	6815844,
	2368,
	1728,
	2240,
	1728,
	6815846);
INSERT INTO GD_GE
	VALUES (6815848,
	6815757,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (6815848,
	6815770,
	6815844,
	0);
INSERT INTO GD_CTXT
	VALUES (6815848,
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
	VALUES (6815849,
	6815848,
	2448,
	1680,
	2368,
	1680,
	0);
INSERT INTO GD_MD
	VALUES (6815758,
	6,
	6815757,
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
	VALUES (6815772,
	6815758,
	6815747,
	21);
INSERT INTO GD_SHP
	VALUES (6815772,
	2640,
	1168,
	2832,
	1232);
INSERT INTO GD_GE
	VALUES (6815773,
	6815758,
	6815749,
	21);
INSERT INTO GD_SHP
	VALUES (6815773,
	2416,
	1552,
	2608,
	1616);
INSERT INTO GD_GE
	VALUES (6815774,
	6815758,
	6815751,
	21);
INSERT INTO GD_SHP
	VALUES (6815774,
	2656,
	1552,
	2848,
	1616);
INSERT INTO GD_GE
	VALUES (6815775,
	6815758,
	6815752,
	21);
INSERT INTO GD_SHP
	VALUES (6815775,
	2880,
	1552,
	3072,
	1616);
INSERT INTO GD_GE
	VALUES (6815776,
	6815758,
	6815756,
	21);
INSERT INTO GD_SHP
	VALUES (6815776,
	2640,
	1312,
	2832,
	1376);
INSERT INTO GD_GE
	VALUES (6815777,
	6815758,
	6815820,
	1005);
INSERT INTO GD_CON
	VALUES (6815777,
	6815772,
	6815773,
	0);
INSERT INTO GD_CTXT
	VALUES (6815777,
	0,
	0,
	0,
	0,
	0,
	0,
	2638,
	1375,
	2795,
	1409,
	-122,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815778,
	6815777,
	2736,
	1232,
	2512,
	1552,
	0);
INSERT INTO GD_GE
	VALUES (6815779,
	6815758,
	6816887,
	1005);
INSERT INTO GD_CON
	VALUES (6815779,
	6815772,
	6815776,
	0);
INSERT INTO GD_CTXT
	VALUES (6815779,
	0,
	0,
	0,
	0,
	0,
	0,
	2724,
	1256,
	2859,
	1291,
	4,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815780,
	6815779,
	2736,
	1232,
	2736,
	1312,
	0);
INSERT INTO GD_GE
	VALUES (6815781,
	6815758,
	6816889,
	1005);
INSERT INTO GD_CON
	VALUES (6815781,
	6815776,
	6815773,
	0);
INSERT INTO GD_CTXT
	VALUES (6815781,
	0,
	0,
	0,
	0,
	0,
	0,
	2482,
	1439,
	2586,
	1474,
	-100,
	-17,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815782,
	6815781,
	2688,
	1376,
	2512,
	1552,
	0);
INSERT INTO GD_GE
	VALUES (6815783,
	6815758,
	6816893,
	1005);
INSERT INTO GD_CON
	VALUES (6815783,
	6815776,
	6815774,
	0);
INSERT INTO GD_CTXT
	VALUES (6815783,
	0,
	0,
	0,
	0,
	0,
	0,
	2622,
	1450,
	2748,
	1483,
	-98,
	1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815784,
	6815783,
	2736,
	1376,
	2736,
	1552,
	0);
INSERT INTO GD_GE
	VALUES (6815785,
	6815758,
	6816897,
	1005);
INSERT INTO GD_CON
	VALUES (6815785,
	6815776,
	6815775,
	0);
INSERT INTO GD_CTXT
	VALUES (6815785,
	0,
	0,
	0,
	0,
	0,
	0,
	2791,
	1471,
	2899,
	1503,
	-70,
	29,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815786,
	6815785,
	2784,
	1376,
	2976,
	1552,
	0);
INSERT INTO GD_MD
	VALUES (6815759,
	7,
	6815757,
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
	VALUES (6815787,
	6815759,
	6815745,
	21);
INSERT INTO GD_SHP
	VALUES (6815787,
	2256,
	1056,
	2448,
	1120);
INSERT INTO GD_GE
	VALUES (6815788,
	6815759,
	6815746,
	21);
INSERT INTO GD_SHP
	VALUES (6815788,
	2512,
	1056,
	2704,
	1120);
INSERT INTO GD_GE
	VALUES (6815789,
	6815759,
	6815747,
	21);
INSERT INTO GD_SHP
	VALUES (6815789,
	2688,
	1184,
	2880,
	1248);
INSERT INTO GD_GE
	VALUES (6815790,
	6815759,
	6815748,
	21);
INSERT INTO GD_SHP
	VALUES (6815790,
	1696,
	1696,
	1888,
	1760);
INSERT INTO GD_GE
	VALUES (6815791,
	6815759,
	6815749,
	21);
INSERT INTO GD_SHP
	VALUES (6815791,
	2480,
	1584,
	2672,
	1648);
INSERT INTO GD_GE
	VALUES (6815792,
	6815759,
	6815750,
	21);
INSERT INTO GD_SHP
	VALUES (6815792,
	2176,
	1088,
	2368,
	1152);
INSERT INTO GD_GE
	VALUES (6815793,
	6815759,
	6815751,
	21);
INSERT INTO GD_SHP
	VALUES (6815793,
	2448,
	1360,
	2640,
	1424);
INSERT INTO GD_GE
	VALUES (6815794,
	6815759,
	6815752,
	21);
INSERT INTO GD_SHP
	VALUES (6815794,
	2448,
	1520,
	2640,
	1584);
INSERT INTO GD_GE
	VALUES (6815795,
	6815759,
	6815753,
	21);
INSERT INTO GD_SHP
	VALUES (6815795,
	1680,
	1712,
	1872,
	1776);
INSERT INTO GD_GE
	VALUES (6815796,
	6815759,
	6815754,
	21);
INSERT INTO GD_SHP
	VALUES (6815796,
	1664,
	2192,
	1856,
	2256);
INSERT INTO GD_GE
	VALUES (6815797,
	6815759,
	6815755,
	21);
INSERT INTO GD_SHP
	VALUES (6815797,
	2448,
	1632,
	2640,
	1696);
INSERT INTO GD_GE
	VALUES (6815798,
	6815759,
	6815756,
	21);
INSERT INTO GD_SHP
	VALUES (6815798,
	2448,
	1296,
	2640,
	1360);
