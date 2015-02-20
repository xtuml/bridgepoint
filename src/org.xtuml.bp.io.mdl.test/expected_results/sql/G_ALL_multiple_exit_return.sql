-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (539756,
	'G_ALL_multiple_exit_return',
	'This domain tests the return statement, as used in bridges, class based operations, instance based operations and functions.  For each of the four contexts there are two functions, one which returns an enum and another that returns void, but uses a by reference parameter.  Either the return value or the by reference parameter is checked for validity after each call.  Each of the contexts is called multiple times.

This test also tests return from within an MDAs action.  We set the MDA and then issue a return and check that the value of the MDA was set correctly.',
	0,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	539756,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	539756,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	539756,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	539756,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	539756,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	539756,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	539756,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	539756,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	539756,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	539756,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	539756,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	539756,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	539756,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	539756,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	539756,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	539756,
	'inst_ref<Timer>',
	'');
INSERT INTO S_UDT
	VALUES (524305,
	524294,
	0);
INSERT INTO S_DT
	VALUES (524305,
	539756,
	'arbitrary_id',
	'Arbitrary ID with core data type of unique_id.');
INSERT INTO S_EDT
	VALUES (524306);
INSERT INTO S_ENUM
	VALUES (524289,
	'FAIL',
	'',
	524306);
INSERT INTO S_ENUM
	VALUES (524290,
	'SUCC0',
	'',
	524306);
INSERT INTO S_ENUM
	VALUES (524291,
	'SUCC1',
	'',
	524306);
INSERT INTO S_ENUM
	VALUES (524292,
	'SUCC2',
	'',
	524306);
INSERT INTO S_DT
	VALUES (524306,
	539756,
	'succ_fail',
	'');
INSERT INTO S_SYNC
	VALUES (524289,
	539756,
	'multiplereturn',
	'',
	'if(param.count == 0)
  param.count = param.count+1;
  return succ_fail::SUCC0;
elif(param.count == 1)
  param.count = param.count+1;
  return succ_fail::SUCC1;
elif(param.count == 2)
  param.count = param.count+1;
  return succ_fail::SUCC2;
end if;
param.count = param.count+1;
return succ_fail::FAIL;

',
	524306,
	1);
INSERT INTO S_SPARM
	VALUES (524289,
	524289,
	'count',
	524291,
	1);
INSERT INTO S_SYNC
	VALUES (524290,
	539756,
	'returnvoid',
	'',
	'if(param.count == 0)
  param.count = param.count+1;
  param.sorf = succ_fail::SUCC0;
  return;
elif(param.count == 1)
  param.count = param.count+1;
  param.sorf = succ_fail::SUCC1;
  return;
elif(param.count == 2)
  param.count = param.count+1;
  param.sorf = succ_fail::SUCC2;
  return;
end if;
param.count = param.count+1;
return;',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524290,
	524290,
	'sorf',
	524306,
	1);
INSERT INTO S_SPARM
	VALUES (524291,
	524290,
	'count',
	524291,
	1);
INSERT INTO S_EE
	VALUES (524289,
	'Time',
	'',
	'TIM',
	539756);
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
	VALUES (524292,
	524290,
	'second',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524293,
	524290,
	'minute',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524294,
	524290,
	'hour',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524295,
	524290,
	'day',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524296,
	524290,
	'month',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524297,
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
	VALUES (524298,
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
	VALUES (524299,
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
	VALUES (524300,
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
	VALUES (524301,
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
	VALUES (524302,
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
	VALUES (524303,
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
	VALUES (524304,
	524298,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524305,
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
	VALUES (524306,
	524299,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524307,
	524299,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524300,
	524289,
	'timer_remaining_time',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524308,
	524300,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BRG
	VALUES (524301,
	524289,
	'timer_reset_time',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524309,
	524301,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524310,
	524301,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524302,
	524289,
	'timer_add_time',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524311,
	524302,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524312,
	524302,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524303,
	524289,
	'timer_cancel',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524313,
	524303,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_EE
	VALUES (524290,
	'Bridge',
	'',
	'B',
	539756);
INSERT INTO S_BRG
	VALUES (524304,
	524290,
	'multiplereturn',
	'',
	0,
	524306,
	'if(param.count == 0)
  param.count = param.count+1;
  return succ_fail::SUCC0;
elif(param.count == 1)
  param.count = param.count+1;
  return succ_fail::SUCC1;
elif(param.count == 2)
  param.count = param.count+1;
  return succ_fail::SUCC2;
end if;
param.count = param.count+1;
return succ_fail::FAIL;',
	1);
INSERT INTO S_BPARM
	VALUES (524314,
	524304,
	'count',
	524291,
	1);
INSERT INTO S_BRG
	VALUES (524305,
	524290,
	'returnvoid',
	'',
	0,
	524289,
	'if(param.count == 0)
  param.count = param.count+1;
  param.sorf = succ_fail::SUCC0;
  return;
elif(param.count == 1)
  param.count = param.count+1;
  param.sorf = succ_fail::SUCC1;
  return;
elif(param.count == 2)
  param.count = param.count+1;
  param.sorf = succ_fail::SUCC2;
  return;
end if;
param.count = param.count+1;
return;',
	1);
INSERT INTO S_BPARM
	VALUES (524315,
	524305,
	'count',
	524291,
	1);
INSERT INTO S_BPARM
	VALUES (524316,
	524305,
	'sorf',
	524306,
	1);
INSERT INTO S_EE
	VALUES (524291,
	'Logger',
	'',
	'LOG',
	539756);
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
	VALUES (524317,
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
	VALUES (524318,
	524307,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524309,
	524291,
	'LogInfo',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524319,
	524309,
	'message',
	524293,
	0);
INSERT INTO S_EE
	VALUES (524292,
	'Architecture',
	'',
	'ARCH',
	539756);
INSERT INTO S_BRG
	VALUES (524308,
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
	539756,
	1,
	1,
	0,
	1,
	1,
	0,
	12,
	1599,
	4101,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (524297,
	524289,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524297,
	1920,
	1376,
	2080,
	1472);
INSERT INTO GD_GE
	VALUES (524293,
	524289,
	524289,
	12);
INSERT INTO GD_SHP
	VALUES (524293,
	1920,
	1536,
	2080,
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
	2288,
	1632);
INSERT INTO GD_GE
	VALUES (524295,
	524289,
	524291,
	12);
INSERT INTO GD_SHP
	VALUES (524295,
	1712,
	1536,
	1872,
	1632);
INSERT INTO GD_GE
	VALUES (524296,
	524289,
	524292,
	12);
INSERT INTO GD_SHP
	VALUES (524296,
	1920,
	1696,
	2080,
	1792);
INSERT INTO S_SS
	VALUES (1048578,
	'G_ALL_multiple_exit_return',
	'',
	'RET',
	1,
	539756,
	1048578);
INSERT INTO O_OBJ
	VALUES (1048577,
	'Class with op',
	1,
	'CWO',
	'',
	1048578);
INSERT INTO O_TFR
	VALUES (1048577,
	1048577,
	'multiplereturn_c',
	'',
	524306,
	0,
	'if(param.count == 0)
  param.count = param.count+1;
  return succ_fail::SUCC0;
elif(param.count == 1)
  param.count = param.count+1;
  return succ_fail::SUCC1;
elif(param.count == 2)
  param.count = param.count+1;
  return succ_fail::SUCC2;
end if;
param.count = param.count+1;
return succ_fail::FAIL;',
	1);
INSERT INTO O_TPARM
	VALUES (1048577,
	1048577,
	'count',
	524291,
	1);
INSERT INTO O_TFR
	VALUES (1048578,
	1048577,
	'returnvoid_c',
	'',
	524289,
	0,
	'if(param.count == 0)
  param.count = param.count+1;
  param.sorf = succ_fail::SUCC0;
  return;
elif(param.count == 1)
  param.count = param.count+1;
  param.sorf = succ_fail::SUCC1;
  return;
elif(param.count == 2)
  param.count = param.count+1;
  param.sorf = succ_fail::SUCC2;
  return;
end if;
param.count = param.count+1;
return;',
	1);
INSERT INTO O_TPARM
	VALUES (1048578,
	1048578,
	'count',
	524291,
	1);
INSERT INTO O_TPARM
	VALUES (1048579,
	1048578,
	'sorf',
	524306,
	1);
INSERT INTO O_TFR
	VALUES (1048579,
	1048577,
	'multiplereturn_i',
	'',
	524306,
	1,
	'if(param.count == 0)
  param.count = param.count+1;
  return succ_fail::SUCC0;
elif(param.count == 1)
  param.count = param.count+1;
  return succ_fail::SUCC1;
elif(param.count == 2)
  param.count = param.count+1;
  return succ_fail::SUCC2;
end if;
param.count = param.count+1;
return succ_fail::FAIL;',
	1);
INSERT INTO O_TPARM
	VALUES (1048580,
	1048579,
	'count',
	524291,
	1);
INSERT INTO O_TFR
	VALUES (1048580,
	1048577,
	'returnvoid_i',
	'',
	524289,
	1,
	'if(param.count == 0)
  param.count = param.count+1;
  param.sorf = succ_fail::SUCC0;
  return;
elif(param.count == 1)
  param.count = param.count+1;
  param.sorf = succ_fail::SUCC1;
  return;
elif(param.count == 2)
  param.count = param.count+1;
  param.sorf = succ_fail::SUCC2;
  return;
end if;
param.count = param.count+1;
return;',
	1);
INSERT INTO O_TPARM
	VALUES (1048581,
	1048580,
	'count',
	524291,
	1);
INSERT INTO O_TPARM
	VALUES (1048582,
	1048580,
	'sorf',
	524306,
	1);
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
	'cwo_ID',
	'',
	'',
	'cwo_ID',
	0,
	524294);
INSERT INTO O_ID
	VALUES (0,
	1048577);
INSERT INTO O_OIDA
	VALUES (1048577,
	1048577,
	0);
INSERT INTO O_OBJ
	VALUES (1048578,
	'Initialization',
	2,
	'INIT',
	'',
	1048578);
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
	'init_ID',
	'',
	'',
	'init_ID',
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
	1048578,
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
	VALUES (1048578,
	1048578,
	0);
INSERT INTO SM_ISM
	VALUES (1572867,
	1048578);
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
	'Start',
	0,
	'',
	'INIT1',
	'');
INSERT INTO SM_STATE
	VALUES (1572865,
	1572867,
	0,
	'Initialization',
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
	'create object instance d of D;
create object instance cwo of CWO;
create object instance cwm of CWM;
cwm.count = 0;

generate D1 to d;',
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
	1776,
	1232,
	2080,
	1440);
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
	1792,
	1232,
	1792,
	1216,
	0);
INSERT INTO GD_LS
	VALUES (1572869,
	1572867,
	1792,
	1216,
	1792,
	1200,
	1572868);
INSERT INTO GD_LS
	VALUES (1572870,
	1572867,
	1792,
	1200,
	2048,
	1200,
	1572869);
INSERT INTO GD_LS
	VALUES (1572871,
	1572867,
	2048,
	1200,
	2048,
	1232,
	1572870);
INSERT INTO O_OBJ
	VALUES (1048579,
	'Driver',
	3,
	'D',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048580,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048580,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048580,
	1048579,
	0,
	'drv_ID',
	'',
	'',
	'drv_ID',
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
	1048580,
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
	VALUES (1048580,
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
	'Go',
	0,
	'',
	'D1',
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
	'Class Ops',
	0,
	'',
	'D2',
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
	5,
	'Func',
	0,
	'',
	'D5',
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
	6,
	'Brdg',
	0,
	'',
	'D6',
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
	7,
	'Clean Up',
	0,
	'',
	'D7',
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
	3,
	'Instance Ops',
	0,
	'',
	'D3',
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
	4,
	'MDA',
	0,
	'',
	'D4',
	'');
INSERT INTO SM_STATE
	VALUES (2097153,
	2097156,
	0,
	'Begin',
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
INSERT INTO SM_EIGN
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
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097157,
	2097156,
	0);
INSERT INTO SM_EIGN
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
	'generate D2 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097154,
	2097156,
	0,
	'Class Operations',
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
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097157,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097154,
	2097158,
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
	'///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Class Operations
// Return type : succ_fail
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
LOG::LogInfo(message:"CLASS OPERATIONS");
// set keep_count
keep_count = 0;

// set result
result = succ_fail::FAIL;

// First call to multiplereturn
result = CWO::multiplereturn_c(count:keep_count);
if(result == succ_fail::SUCC0)
  LOG::LogSuccess(message:"First call to multiplereturn successful");
else
  LOG::LogFailure(message:"First call to multiplereturn failed");
end if;

// reset result
result = succ_fail::FAIL;

// Second call to multiplereturn
result = CWO::multiplereturn_c(count:keep_count);
if(result == succ_fail::SUCC1)
  LOG::LogSuccess(message:"Second call to multiplereturn successful");
else
  LOG::LogFailure(message:"Second call to multiplereturn failed");
end if;

// reset result
result = succ_fail::FAIL;

// Third call to multiplereturn
result = CWO::multiplereturn_c(count:keep_count);
if(result == succ_fail::SUCC2)
  LOG::LogSuccess(message:"Third call to multiplereturn successful");
else
  LOG::LogFailure(message:"Third call to multiplereturn failed");
end if;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Class Operations
// Return type : void
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// reset keep_count
keep_count = 0;

// reset result
result = succ_fail::FAIL;

// First call to returnvoid
CWO::returnvoid_c(count:keep_count, sorf:result);
if(result == succ_fail::SUCC0)
  LOG::LogSuccess(message:"First call to returnvoid successful");
else
  LOG::LogFailure(message:"First call to returnvoid failed");
end if;

// reset result
result = succ_fail::FAIL;

// Second call to returnvoid
CWO::returnvoid_c(count:keep_count, sorf:result);
if(result == succ_fail::SUCC1)
  LOG::LogSuccess(message:"Second call to returnvoid successful");
else
  LOG::LogFailure(message:"Second call to returnvoid failed");
end if;

// reset result
result = succ_fail::FAIL;

// Third call to returnvoid
CWO::returnvoid_c(count:keep_count, sorf:result);
if(result == succ_fail::SUCC2)
  LOG::LogSuccess(message:"Third call to returnvoid successful");
else
  LOG::LogFailure(message:"Third call to returnvoid failed");
end if;

// Move on to Instance Operations
generate D3 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097155,
	2097156,
	0,
	'Functions',
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_SEME
	VALUES (2097155,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
	'///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functions
// Return type : succ_fail
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
LOG::LogInfo(message:"FUNCTIONS");
// set keep_count
keep_count = 0;

// set result
result = succ_fail::FAIL;

// First call to multiplereturn
result = ::multiplereturn(count:keep_count);
if(result == succ_fail::SUCC0)
  LOG::LogSuccess(message:"First call to multiplereturn successful");
else
  LOG::LogFailure(message:"First call to multiplereturn failed");
end if;

// reset result
result = succ_fail::FAIL;

// Second call to multiplereturn
result = ::multiplereturn(count:keep_count);
if(result == succ_fail::SUCC1)
  LOG::LogSuccess(message:"Second call to multiplereturn successful");
else
  LOG::LogFailure(message:"Second call to multiplereturn failed");
end if;

// reset result
result = succ_fail::FAIL;

// Third call to multiplereturn
result = ::multiplereturn(count:keep_count);
if(result == succ_fail::SUCC2)
  LOG::LogSuccess(message:"Third call to multiplereturn successful");
else
  LOG::LogFailure(message:"Third call to multiplereturn failed");
end if;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functions
// Return type : void
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// reset keep_count
keep_count = 0;

// reset result
result = succ_fail::FAIL;

// First call to returnvoid
::returnvoid(count:keep_count, sorf:result);
if(result == succ_fail::SUCC0)
  LOG::LogSuccess(message:"First call to returnvoid successful");
else
  LOG::LogFailure(message:"First call to returnvoid failed");
end if;

// reset result
result = succ_fail::FAIL;

// Second call to returnvoid
::returnvoid(count:keep_count, sorf:result);
if(result == succ_fail::SUCC1)
  LOG::LogSuccess(message:"Second call to returnvoid successful");
else
  LOG::LogFailure(message:"Second call to returnvoid failed");
end if;

// reset result
result = succ_fail::FAIL;

// Third call to returnvoid
::returnvoid(count:keep_count, sorf:result);
if(result == succ_fail::SUCC2)
  LOG::LogSuccess(message:"Third call to returnvoid successful");
else
  LOG::LogFailure(message:"Third call to returnvoid failed");
end if;

// Move on to Bridges
generate D6 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097156,
	2097156,
	0,
	'Bridges',
	4,
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_SEME
	VALUES (2097156,
	2097157,
	2097156,
	0);
INSERT INTO SM_EIGN
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
	'///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Bridges
// Return type : succ_fail
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
LOG::LogInfo(message:"BRIDGES");
// set keep_count
keep_count = 0;

// set result
result = succ_fail::FAIL;

// First call to multiplereturn
result = B::multiplereturn(count:keep_count);
if(result == succ_fail::SUCC0)
  LOG::LogSuccess(message:"First call to multiplereturn successful");
else
  LOG::LogFailure(message:"First call to multiplereturn failed");
end if;

// reset result
result = succ_fail::FAIL;

// Second call to multiplereturn
result = B::multiplereturn(count:keep_count);
if(result == succ_fail::SUCC1)
  LOG::LogSuccess(message:"Second call to multiplereturn successful");
else
  LOG::LogFailure(message:"Second call to multiplereturn failed");
end if;

// reset result
result = succ_fail::FAIL;

// Third call to multiplereturn
result = B::multiplereturn(count:keep_count);
if(result == succ_fail::SUCC2)
  LOG::LogSuccess(message:"Third call to multiplereturn successful");
else
  LOG::LogFailure(message:"Third call to multiplereturn failed");
end if;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Bridges
// Return type : void
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// reset keep_count
keep_count = 0;

// reset result
result = succ_fail::FAIL;

// First call to returnvoid
B::returnvoid(count:keep_count, sorf:result);
if(result == succ_fail::SUCC0)
  LOG::LogSuccess(message:"First call to returnvoid successful");
else
  LOG::LogFailure(message:"First call to returnvoid failed");
end if;

// reset result
result = succ_fail::FAIL;

// Second call to returnvoid
B::returnvoid(count:keep_count, sorf:result);
if(result == succ_fail::SUCC1)
  LOG::LogSuccess(message:"Second call to returnvoid successful");
else
  LOG::LogFailure(message:"Second call to returnvoid failed");
end if;

// reset result
result = succ_fail::FAIL;

// Third call to returnvoid
B::returnvoid(count:keep_count, sorf:result);
if(result == succ_fail::SUCC2)
  LOG::LogSuccess(message:"Third call to returnvoid successful");
else
  LOG::LogFailure(message:"Third call to returnvoid failed");
end if;

// Clean up
generate D7 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097157,
	2097156,
	0,
	'Clean Up',
	5,
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
INSERT INTO SM_EIGN
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
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097157,
	2097157,
	2097156,
	0);
INSERT INTO SM_EIGN
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
	'select any cwo from instances of CWO;
delete object instance cwo;

select any cwm from instances of CWM;
delete object instance cwm;

delete object instance self;

ARCH::shutdown();
',
	'');
INSERT INTO SM_STATE
	VALUES (2097158,
	2097156,
	0,
	'Instance Operations',
	6,
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
	VALUES (2097158,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097158,
	2097158,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097158,
	2097159,
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
	'///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Instance Operations
// Return type : succ_fail
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
LOG::LogInfo(message:"INSTANCE OPERATIONS");
// set keep_count
keep_count = 0;

// set result
result = succ_fail::FAIL;

// get the instance
select any cwo from instances of CWO;

// First call to multiplereturn_i
result = cwo.multiplereturn_i(count:keep_count);
if(result == succ_fail::SUCC0)
  LOG::LogSuccess(message:"First call to multiplereturn_i successful");
else
  LOG::LogFailure(message:"First call to multiplereturn_i failed");
end if;

// reset result
result = succ_fail::FAIL;

// Second call to multiplereturn_i
result = cwo.multiplereturn_i(count:keep_count);
if(result == succ_fail::SUCC1)
  LOG::LogSuccess(message:"Second call to multiplereturn_i successful");
else
  LOG::LogFailure(message:"Second call to multiplereturn_i failed");
end if;

// reset result
result = succ_fail::FAIL;

// Third call to multiplereturn_i
result = cwo.multiplereturn_i(count:keep_count);
if(result == succ_fail::SUCC2)
  LOG::LogSuccess(message:"Third call to multiplereturn_i successful");
else
  LOG::LogFailure(message:"Third call to multiplereturn_i failed");
end if;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Class Operations
// Return type : void
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// reset keep_count
keep_count = 0;

// reset result
result = succ_fail::FAIL;

// First call to returnvoid_i
cwo.returnvoid_i(count:keep_count, sorf:result);
if(result == succ_fail::SUCC0)
  LOG::LogSuccess(message:"First call to returnvoid_i successful");
else
  LOG::LogFailure(message:"First call to returnvoid_i failed");
end if;

// reset result
result = succ_fail::FAIL;

// Second call to returnvoid_i
cwo.returnvoid_i(count:keep_count, sorf:result);
if(result == succ_fail::SUCC1)
  LOG::LogSuccess(message:"Second call to returnvoid_i successful");
else
  LOG::LogFailure(message:"Second call to returnvoid_i failed");
end if;

// reset result
result = succ_fail::FAIL;

// Third call to returnvoid_i
cwo.returnvoid_i(count:keep_count, sorf:result);
if(result == succ_fail::SUCC2)
  LOG::LogSuccess(message:"Third call to returnvoid_i successful");
else
  LOG::LogFailure(message:"Third call to returnvoid_i failed");
end if;

// Move on to Instance Operations
generate D4 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097159,
	2097156,
	0,
	'MDA',
	7,
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
INSERT INTO SM_SEME
	VALUES (2097159,
	2097155,
	2097156,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
	'///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MDA
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
LOG::LogInfo(message:"MATHEMATICALLY DEPENDENT ATTRIBUTES");
// set keep_count
keep_count = 0;

// set result
result = succ_fail::FAIL;

// get the instance
select any cwm from instances of CWM;

// First access of the MDA
result = cwm.mda;
if(result == succ_fail::SUCC0)
  LOG::LogSuccess(message:"First access of MDA successful");
else
  LOG::LogFailure(message:"First access of MDA failed");
end if;

// reset result
result = succ_fail::FAIL;

// Second call to multiplereturn_i
result = cwm.mda;
if(result == succ_fail::SUCC1)
  LOG::LogSuccess(message:"Second access of MDA successful");
else
  LOG::LogFailure(message:"Second access of MDA failed");
end if;

// reset result
result = succ_fail::FAIL;

// Third call to multiplereturn_i
result = cwm.mda;
if(result == succ_fail::SUCC2)
  LOG::LogSuccess(message:"Third access of MDA successful");
else
  LOG::LogFailure(message:"Third access of MDA failed");
end if;

// Move on to Functions
generate D5 to self;',
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
	2097155,
	2097156,
	0);
INSERT INTO SM_TXN
	VALUES (2097155,
	2097156,
	2097156,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097156,
	2097156,
	2097156,
	2097157,
	0);
INSERT INTO SM_TXN
	VALUES (2097156,
	2097156,
	2097157,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097157,
	2097156,
	2097154,
	2097158,
	0);
INSERT INTO SM_TXN
	VALUES (2097157,
	2097156,
	2097158,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097158,
	2097156,
	2097158,
	2097159,
	0);
INSERT INTO SM_TXN
	VALUES (2097158,
	2097156,
	2097159,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097159,
	2097156,
	2097159,
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097159,
	2097156,
	2097155,
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
	782,
	2542,
	0.278517,
	0);
INSERT INTO GD_GE
	VALUES (2097154,
	2097153,
	2097153,
	41);
INSERT INTO GD_SHP
	VALUES (2097154,
	1616,
	1216,
	2224,
	1328);
INSERT INTO GD_GE
	VALUES (2097155,
	2097153,
	2097154,
	41);
INSERT INTO GD_SHP
	VALUES (2097155,
	1616,
	1424,
	2224,
	2000);
INSERT INTO GD_GE
	VALUES (2097156,
	2097153,
	2097155,
	41);
INSERT INTO GD_SHP
	VALUES (2097156,
	2352,
	2864,
	2960,
	3440);
INSERT INTO GD_GE
	VALUES (2097157,
	2097153,
	2097156,
	41);
INSERT INTO GD_SHP
	VALUES (2097157,
	2352,
	2144,
	2960,
	2720);
INSERT INTO GD_GE
	VALUES (2097158,
	2097153,
	2097157,
	41);
INSERT INTO GD_SHP
	VALUES (2097158,
	2352,
	1888,
	2944,
	2032);
INSERT INTO GD_GE
	VALUES (2097159,
	2097153,
	2097158,
	41);
INSERT INTO GD_SHP
	VALUES (2097159,
	1616,
	2144,
	2224,
	2720);
INSERT INTO GD_GE
	VALUES (2097160,
	2097153,
	2097159,
	41);
INSERT INTO GD_SHP
	VALUES (2097160,
	1616,
	2864,
	2224,
	3440);
INSERT INTO GD_GE
	VALUES (2097161,
	2097153,
	2097153,
	42);
INSERT INTO GD_CON
	VALUES (2097161,
	2097154,
	2097154,
	0);
INSERT INTO GD_CTXT
	VALUES (2097161,
	0,
	0,
	0,
	0,
	0,
	0,
	1486,
	1257,
	1536,
	1279,
	-10,
	-6,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097162,
	2097161,
	1616,
	1232,
	1552,
	1232,
	0);
INSERT INTO GD_LS
	VALUES (2097163,
	2097161,
	1552,
	1232,
	1552,
	1312,
	2097162);
INSERT INTO GD_LS
	VALUES (2097164,
	2097161,
	1552,
	1312,
	1616,
	1312,
	2097163);
INSERT INTO GD_GE
	VALUES (2097165,
	2097153,
	2097154,
	42);
INSERT INTO GD_CON
	VALUES (2097165,
	2097154,
	2097155,
	0);
INSERT INTO GD_CTXT
	VALUES (2097165,
	0,
	0,
	0,
	0,
	0,
	0,
	1926,
	1358,
	2125,
	1401,
	97,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097166,
	2097165,
	1888,
	1328,
	1888,
	1424,
	0);
INSERT INTO GD_GE
	VALUES (2097167,
	2097153,
	2097155,
	42);
INSERT INTO GD_CON
	VALUES (2097167,
	2097156,
	2097157,
	0);
INSERT INTO GD_CTXT
	VALUES (2097167,
	0,
	0,
	0,
	0,
	0,
	0,
	2684,
	2777,
	2747,
	2799,
	97,
	-6,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097168,
	2097167,
	2656,
	2864,
	2656,
	2720,
	0);
INSERT INTO GD_GE
	VALUES (2097169,
	2097153,
	2097156,
	42);
INSERT INTO GD_CON
	VALUES (2097169,
	2097157,
	2097158,
	0);
INSERT INTO GD_CTXT
	VALUES (2097169,
	0,
	0,
	0,
	0,
	0,
	0,
	2505,
	2078,
	2599,
	2100,
	-35,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097170,
	2097169,
	2640,
	2144,
	2640,
	2032,
	0);
INSERT INTO GD_GE
	VALUES (2097171,
	2097153,
	2097157,
	42);
INSERT INTO GD_CON
	VALUES (2097171,
	2097155,
	2097159,
	0);
INSERT INTO GD_CTXT
	VALUES (2097171,
	0,
	0,
	0,
	0,
	0,
	0,
	1763,
	2063,
	1882,
	2085,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097172,
	2097171,
	1888,
	2000,
	1888,
	2144,
	0);
INSERT INTO GD_GE
	VALUES (2097173,
	2097153,
	2097158,
	42);
INSERT INTO GD_CON
	VALUES (2097173,
	2097159,
	2097160,
	0);
INSERT INTO GD_CTXT
	VALUES (2097173,
	0,
	0,
	0,
	0,
	0,
	0,
	1833,
	2783,
	1898,
	2805,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097174,
	2097173,
	1904,
	2720,
	1904,
	2864,
	0);
INSERT INTO GD_GE
	VALUES (2097175,
	2097153,
	2097159,
	42);
INSERT INTO GD_CON
	VALUES (2097175,
	2097160,
	2097156,
	0);
INSERT INTO GD_CTXT
	VALUES (2097175,
	0,
	0,
	0,
	0,
	0,
	0,
	2255,
	3085,
	2318,
	3107,
	-4,
	-23,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097176,
	2097175,
	2224,
	3136,
	2352,
	3136,
	0);
INSERT INTO O_OBJ
	VALUES (1048580,
	'Class with MDA',
	5,
	'CWM',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048582,
	1048580);
INSERT INTO O_BATTR
	VALUES (1048582,
	1048580);
INSERT INTO O_ATTR
	VALUES (1048582,
	1048580,
	0,
	'cwm_ID',
	'',
	'',
	'cwm_ID',
	0,
	524294);
INSERT INTO O_DBATTR
	VALUES (1048583,
	1048580,
	'if(self.count == 0)
  self.count = self.count+1;
  self.mda = succ_fail::SUCC0;
  return;
elif(self.count == 1)
  self.count = self.count+1;
  self.mda = succ_fail::SUCC1;
  return;
elif(self.count == 2)
  self.count = self.count+1;
  self.mda = succ_fail::SUCC2;
  return;
end if;
self.count = self.count+1;
self.mda = succ_fail::FAIL;
return;',
	1);
INSERT INTO O_BATTR
	VALUES (1048583,
	1048580);
INSERT INTO O_ATTR
	VALUES (1048583,
	1048580,
	1048582,
	'mda',
	'',
	'',
	'mda',
	0,
	524306);
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
	'count',
	'',
	'',
	'count',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	1048580);
INSERT INTO O_OIDA
	VALUES (1048582,
	1048580,
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
	1548,
	4088,
	0.921053,
	0);
INSERT INTO GD_GE
	VALUES (1048580,
	1048577,
	1048577,
	21);
INSERT INTO GD_SHP
	VALUES (1048580,
	1648,
	1248,
	1984,
	1472);
INSERT INTO GD_GE
	VALUES (1048581,
	1048577,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048581,
	2048,
	1248,
	2384,
	1472);
INSERT INTO GD_GE
	VALUES (1048582,
	1048577,
	1048579,
	21);
INSERT INTO GD_SHP
	VALUES (1048582,
	1648,
	1520,
	1984,
	1680);
INSERT INTO GD_GE
	VALUES (1048583,
	1048577,
	1048580,
	21);
INSERT INTO GD_SHP
	VALUES (1048583,
	2048,
	1520,
	2384,
	1680);
