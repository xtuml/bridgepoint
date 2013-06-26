-- root-types-contained: SystemModel_c
-- BP 7.1 content: StreamData syschar: 3 persistence-version: 7.1.5

INSERT INTO S_SYS
	VALUES (1,
	'avpace',
	0);
INSERT INTO SLD_SDP
	VALUES (1,
	2);
INSERT INTO S_DPK
	VALUES (2,
	'Datatypes',
	0,
	0);
INSERT INTO S_DIP
	VALUES (2,
	3);
INSERT INTO S_DT
	VALUES (3,
	0,
	'void',
	'',
	'');
INSERT INTO S_CDT
	VALUES (3,
	0);
INSERT INTO S_DIP
	VALUES (2,
	4);
INSERT INTO S_DT
	VALUES (4,
	0,
	'boolean',
	'',
	'');
INSERT INTO S_CDT
	VALUES (4,
	1);
INSERT INTO S_DIP
	VALUES (2,
	5);
INSERT INTO S_DT
	VALUES (5,
	0,
	'integer',
	'',
	'');
INSERT INTO S_CDT
	VALUES (5,
	2);
INSERT INTO S_DIP
	VALUES (2,
	6);
INSERT INTO S_DT
	VALUES (6,
	0,
	'real',
	'',
	'');
INSERT INTO S_CDT
	VALUES (6,
	3);
INSERT INTO S_DIP
	VALUES (2,
	7);
INSERT INTO S_DT
	VALUES (7,
	0,
	'string',
	'',
	'');
INSERT INTO S_CDT
	VALUES (7,
	4);
INSERT INTO S_DIP
	VALUES (2,
	8);
INSERT INTO S_DT
	VALUES (8,
	0,
	'unique_id',
	'',
	'');
INSERT INTO S_CDT
	VALUES (8,
	5);
INSERT INTO S_DIP
	VALUES (2,
	9);
INSERT INTO S_DT
	VALUES (9,
	0,
	'state<State_Model>',
	'',
	'');
INSERT INTO S_CDT
	VALUES (9,
	6);
INSERT INTO S_DIP
	VALUES (2,
	10);
INSERT INTO S_DT
	VALUES (10,
	0,
	'same_as<Base_Attribute>',
	'',
	'');
INSERT INTO S_CDT
	VALUES (10,
	7);
INSERT INTO S_DIP
	VALUES (2,
	11);
INSERT INTO S_DT
	VALUES (11,
	0,
	'inst_ref<Object>',
	'',
	'');
INSERT INTO S_CDT
	VALUES (11,
	8);
INSERT INTO S_DIP
	VALUES (2,
	12);
INSERT INTO S_DT
	VALUES (12,
	0,
	'inst_ref_set<Object>',
	'',
	'');
INSERT INTO S_CDT
	VALUES (12,
	9);
INSERT INTO S_DIP
	VALUES (2,
	13);
INSERT INTO S_DT
	VALUES (13,
	0,
	'inst<Event>',
	'',
	'');
INSERT INTO S_CDT
	VALUES (13,
	10);
INSERT INTO S_DIP
	VALUES (2,
	14);
INSERT INTO S_DT
	VALUES (14,
	0,
	'inst<Mapping>',
	'',
	'');
INSERT INTO S_CDT
	VALUES (14,
	11);
INSERT INTO S_DIP
	VALUES (2,
	15);
INSERT INTO S_DT
	VALUES (15,
	0,
	'inst_ref<Mapping>',
	'',
	'');
INSERT INTO S_CDT
	VALUES (15,
	12);
INSERT INTO S_DIP
	VALUES (2,
	16);
INSERT INTO S_DT
	VALUES (16,
	0,
	'component_ref',
	'',
	'');
INSERT INTO S_CDT
	VALUES (16,
	13);
INSERT INTO S_DIP
	VALUES (2,
	17);
INSERT INTO S_DT
	VALUES (17,
	0,
	'date',
	'',
	'');
INSERT INTO S_UDT
	VALUES (17,
	14,
	1);
INSERT INTO S_DIP
	VALUES (2,
	18);
INSERT INTO S_DT
	VALUES (18,
	0,
	'inst_ref<Timer>',
	'',
	'');
INSERT INTO S_UDT
	VALUES (18,
	15,
	3);
INSERT INTO S_DIP
	VALUES (2,
	19);
INSERT INTO S_DT
	VALUES (19,
	0,
	'timestamp',
	'',
	'');
INSERT INTO S_UDT
	VALUES (19,
	14,
	2);
INSERT INTO SLD_SDINP
	VALUES (2,
	3,
	1);
INSERT INTO SLD_SDINP
	VALUES (2,
	4,
	1);
INSERT INTO SLD_SDINP
	VALUES (2,
	5,
	1);
INSERT INTO SLD_SDINP
	VALUES (2,
	6,
	1);
INSERT INTO SLD_SDINP
	VALUES (2,
	7,
	1);
INSERT INTO SLD_SDINP
	VALUES (2,
	8,
	1);
INSERT INTO SLD_SDINP
	VALUES (2,
	9,
	1);
INSERT INTO SLD_SDINP
	VALUES (2,
	10,
	1);
INSERT INTO SLD_SDINP
	VALUES (2,
	11,
	1);
INSERT INTO SLD_SDINP
	VALUES (2,
	12,
	1);
INSERT INTO SLD_SDINP
	VALUES (2,
	13,
	1);
INSERT INTO SLD_SDINP
	VALUES (2,
	14,
	1);
INSERT INTO SLD_SDINP
	VALUES (2,
	15,
	1);
INSERT INTO SLD_SDINP
	VALUES (2,
	16,
	1);
INSERT INTO SLD_SDINP
	VALUES (2,
	17,
	1);
INSERT INTO SLD_SDINP
	VALUES (2,
	18,
	1);
INSERT INTO SLD_SDINP
	VALUES (2,
	19,
	1);
INSERT INTO EP_SPKG
	VALUES (2,
	0);
INSERT INTO EP_PKG
	VALUES (20,
	1,
	1,
	'analysis',
	'',
	0);
INSERT INTO PE_PE
	VALUES (21,
	1,
	20,
	0,
	7);
INSERT INTO EP_PKG
	VALUES (21,
	0,
	1,
	'Increased Activity «comm»',
	'',
	0);
INSERT INTO PE_PE
	VALUES (22,
	1,
	21,
	0,
	14);
INSERT INTO SQ_P
	VALUES (22,
	0);
INSERT INTO SQ_CIP
	VALUES (22,
	23,
	'Pacer1',
	'Pacer',
	'Pacer1 : pacer',
	'The pacer instance has an increased amount of signals delivered during Increased
Activity.  This allows the Pacer to deliver the appropriate number of beats to
accomodate the heart under the increased activity.',
	1);
INSERT INTO SQ_AV
	VALUES (24,
	0,
	0,
	'Informal Attribute',
	'',
	'Informal Attribute',
	22,
	0,
	'',
	0);
INSERT INTO SQ_AV
	VALUES (25,
	23,
	26,
	'systolic_tolerance',
	'',
	'',
	0,
	22,
	'',
	1);
INSERT INTO SQ_FAV
	VALUES (25);
INSERT INTO SQ_AV
	VALUES (27,
	23,
	28,
	'diastolic_tolerance',
	'',
	'',
	0,
	22,
	'',
	1);
INSERT INTO SQ_FAV
	VALUES (27);
INSERT INTO SQ_AV
	VALUES (29,
	23,
	30,
	'current_state',
	'',
	'',
	0,
	22,
	'',
	1);
INSERT INTO SQ_FAV
	VALUES (29);
INSERT INTO SQ_AV
	VALUES (31,
	23,
	32,
	'cycle_count',
	'',
	'',
	0,
	22,
	'',
	1);
INSERT INTO SQ_FAV
	VALUES (31);
INSERT INTO SQ_AV
	VALUES (33,
	23,
	34,
	'timeout_timer',
	'',
	'',
	0,
	22,
	'',
	1);
INSERT INTO SQ_FAV
	VALUES (33);
INSERT INTO SQ_AV
	VALUES (35,
	23,
	36,
	'systolic_timeout',
	'',
	'',
	0,
	22,
	'',
	1);
INSERT INTO SQ_FAV
	VALUES (35);
INSERT INTO SQ_AV
	VALUES (37,
	23,
	38,
	'diastolic_timeout',
	'',
	'',
	0,
	22,
	'',
	1);
INSERT INTO SQ_FAV
	VALUES (37);
INSERT INTO COMM_LNK
	VALUES (39,
	0,
	'',
	'The R1 link allows for a Host Monitor to help control a Pacer when certain
environmental values have changed.',
	'notifies of increased activity',
	'',
	0,
	0,
	0,
	22,
	40);
INSERT INTO PE_PE
	VALUES (41,
	1,
	21,
	0,
	14);
INSERT INTO SQ_P
	VALUES (41,
	0);
INSERT INTO SQ_CIP
	VALUES (41,
	42,
	'TemperatureMonitor1',
	'TemperatureMonitor',
	'TemperatureMonitor1 : Temperature Monitor',
	'The TemperatureMonitor monitors the current temperature of the host''s blood.',
	1);
INSERT INTO SQ_AV
	VALUES (43,
	0,
	0,
	'current_temp = 37.84',
	'37.84',
	'current_temp',
	41,
	0,
	'',
	0);
INSERT INTO COMM_LNK
	VALUES (44,
	45,
	'2',
	'The R2 Link allows for the HostMonitor to communicate with a temperature
monitor.  Allowing for readings of the hots''s current temperature.',
	'',
	'provides temperature monitoring',
	1,
	0,
	0,
	41,
	40);
INSERT INTO PE_PE
	VALUES (46,
	1,
	21,
	0,
	14);
INSERT INTO SQ_P
	VALUES (46,
	0);
INSERT INTO SQ_CIP
	VALUES (46,
	47,
	'RespiratoryMonitor1',
	'RespiratoryMonitor',
	'RespiratoryMonitor1 : Respiratory Monitor',
	'The RespiratoryMonitor continually measures the host''s breaths per minute.',
	1);
INSERT INTO SQ_AV
	VALUES (48,
	0,
	0,
	'current_rate = 35 bpms',
	'35 bpms',
	'current_rate',
	46,
	0,
	'',
	0);
INSERT INTO COMM_LNK
	VALUES (49,
	50,
	'3',
	'The R3 Link allows for a HostMonitor to have access to a continually measured
respiratory value.  The Respiratory Monitor measures the value in breaths per
minute.',
	'',
	'provides respiratory monitoring',
	1,
	0,
	0,
	46,
	40);
INSERT INTO PE_PE
	VALUES (40,
	1,
	21,
	0,
	14);
INSERT INTO SQ_P
	VALUES (40,
	0);
INSERT INTO SQ_CIP
	VALUES (40,
	51,
	'HostMonitor1',
	'HostMonitor',
	'HostMonitor1 : Host Monitor',
	'The HostMonitor instance watches lower level monitors for values that exceed
normal activity levels.',
	1);
INSERT INTO SQ_AV
	VALUES (52,
	0,
	0,
	'lastTemp = 37.84',
	'37.84',
	'lastTemp',
	40,
	0,
	'',
	0);
INSERT INTO SQ_AV
	VALUES (53,
	0,
	0,
	'lastRate = 35',
	'35',
	'lastRate',
	40,
	0,
	'',
	0);
INSERT INTO SQ_AV
	VALUES (54,
	51,
	55,
	'lastTemp',
	'',
	'',
	0,
	40,
	'',
	1);
INSERT INTO SQ_FAV
	VALUES (54);
INSERT INTO SQ_AV
	VALUES (56,
	51,
	57,
	'lastRate',
	'',
	'',
	0,
	40,
	'',
	1);
INSERT INTO SQ_FAV
	VALUES (56);
INSERT INTO SQ_AV
	VALUES (58,
	51,
	59,
	'current_state',
	'',
	'',
	0,
	40,
	'',
	1);
INSERT INTO SQ_FAV
	VALUES (58);
INSERT INTO PE_PE
	VALUES (60,
	1,
	21,
	0,
	17);
INSERT INTO MSG_M
	VALUES (60,
	0,
	0,
	1);
INSERT INTO MSG_AM
	VALUES (60,
	'increasedTemperature',
	'The increased_activity message asynchronously notifies the Pacer of an event
where the Host''s temperature and respiratory rate have exceeded normal activity
levels.',
	'blood_temp > 37.5 or resipiratory_rate > 18',
	'',
	'',
	0,
	'increasedTemperature',
	'3');
INSERT INTO MSG_IAM
	VALUES (60);
INSERT INTO MSG_A
	VALUES (61,
	60,
	0,
	'current_temp = 37.84',
	'37.84',
	'current_temp',
	'',
	0);
INSERT INTO MSG_IA
	VALUES (61);
INSERT INTO PE_PE
	VALUES (62,
	1,
	21,
	0,
	17);
INSERT INTO MSG_M
	VALUES (62,
	0,
	0,
	1);
INSERT INTO MSG_SM
	VALUES (62,
	'getCurrentRate',
	'The getCurrentRate operation is used to retrieve the current respiratory rate in
breaths per minute.',
	'',
	'lastRate',
	'35',
	0,
	'getCurrentRate',
	'2');
INSERT INTO MSG_ISM
	VALUES (62);
INSERT INTO PE_PE
	VALUES (63,
	1,
	21,
	0,
	17);
INSERT INTO MSG_M
	VALUES (63,
	0,
	0,
	1);
INSERT INTO MSG_SM
	VALUES (63,
	'getCurrentTemp',
	'The getCurrentTemp operation is used to retrieve the latest temperature reading
in celcius.',
	'',
	'lastTemp',
	'37.84',
	0,
	'getCurrentTemp',
	'1');
INSERT INTO MSG_ISM
	VALUES (63);
INSERT INTO PE_PE
	VALUES (64,
	1,
	20,
	0,
	7);
INSERT INTO EP_PKG
	VALUES (64,
	0,
	1,
	'Increased Activity «sd»',
	'',
	0);
INSERT INTO PE_PE
	VALUES (65,
	1,
	64,
	0,
	14);
INSERT INTO SQ_P
	VALUES (65,
	0);
INSERT INTO SQ_CIP
	VALUES (65,
	23,
	'Pacer1',
	'Informal Class',
	'Pacer1 : pacer',
	'',
	1);
INSERT INTO SQ_AV
	VALUES (66,
	23,
	26,
	'systolic_tolerance',
	'',
	'',
	0,
	65,
	'',
	1);
INSERT INTO SQ_FAV
	VALUES (66);
INSERT INTO SQ_AV
	VALUES (67,
	23,
	28,
	'diastolic_tolerance',
	'',
	'',
	0,
	65,
	'',
	1);
INSERT INTO SQ_FAV
	VALUES (67);
INSERT INTO SQ_AV
	VALUES (68,
	23,
	30,
	'current_state',
	'',
	'',
	0,
	65,
	'',
	1);
INSERT INTO SQ_FAV
	VALUES (68);
INSERT INTO SQ_AV
	VALUES (69,
	23,
	32,
	'cycle_count',
	'',
	'',
	0,
	65,
	'',
	1);
INSERT INTO SQ_FAV
	VALUES (69);
INSERT INTO SQ_AV
	VALUES (70,
	23,
	34,
	'timeout_timer',
	'',
	'',
	0,
	65,
	'',
	1);
INSERT INTO SQ_FAV
	VALUES (70);
INSERT INTO SQ_AV
	VALUES (71,
	23,
	36,
	'systolic_timeout',
	'',
	'',
	0,
	65,
	'',
	1);
INSERT INTO SQ_FAV
	VALUES (71);
INSERT INTO SQ_AV
	VALUES (72,
	23,
	38,
	'diastolic_timeout',
	'',
	'',
	0,
	65,
	'',
	1);
INSERT INTO SQ_FAV
	VALUES (72);
INSERT INTO PE_PE
	VALUES (73,
	1,
	64,
	0,
	14);
INSERT INTO SQ_P
	VALUES (73,
	0);
INSERT INTO MSG_M
	VALUES (74,
	75,
	73,
	0);
INSERT INTO MSG_AM
	VALUES (74,
	'Informal Message',
	'',
	'blood_temp > 37.5 or respiratory_rate > 18',
	'',
	'',
	0,
	'Informal Message',
	'');
INSERT INTO MSG_IAM
	VALUES (74);
INSERT INTO SQ_LS
	VALUES (73,
	65,
	'',
	0);
INSERT INTO PE_PE
	VALUES (76,
	1,
	64,
	0,
	14);
INSERT INTO SQ_P
	VALUES (76,
	0);
INSERT INTO SQ_CIP
	VALUES (76,
	0,
	'HostMonitor1',
	'Informal Class',
	'HostMonitor1 : Informal Class',
	'',
	0);
INSERT INTO PE_PE
	VALUES (75,
	1,
	64,
	0,
	14);
INSERT INTO SQ_P
	VALUES (75,
	0);
INSERT INTO MSG_M
	VALUES (77,
	75,
	75,
	0);
INSERT INTO MSG_AM
	VALUES (77,
	'Informal Message',
	'',
	'',
	'',
	'',
	0,
	'Informal Message',
	'');
INSERT INTO MSG_IAM
	VALUES (77);
INSERT INTO MSG_M
	VALUES (78,
	75,
	75,
	0);
INSERT INTO MSG_AM
	VALUES (78,
	'Informal Message',
	'',
	'',
	'',
	'',
	0,
	'Informal Message',
	'');
INSERT INTO MSG_IAM
	VALUES (78);
INSERT INTO SQ_LS
	VALUES (75,
	76,
	'',
	0);
INSERT INTO SQ_TM
	VALUES (79,
	'increased activity detection',
	75,
	'');
INSERT INTO SQ_TS
	VALUES (80,
	81,
	79,
	'< 10ms',
	'');
INSERT INTO SQ_TM
	VALUES (81,
	'notification sent',
	75,
	'');
INSERT INTO PE_PE
	VALUES (82,
	1,
	64,
	0,
	14);
INSERT INTO SQ_P
	VALUES (82,
	0);
INSERT INTO SQ_CIP
	VALUES (82,
	0,
	'TemperatureMonitor1',
	'Informal Class',
	'TemperatureMonitor1 : Informal Class',
	'',
	0);
INSERT INTO PE_PE
	VALUES (83,
	1,
	64,
	0,
	14);
INSERT INTO SQ_P
	VALUES (83,
	0);
INSERT INTO SQ_CIP
	VALUES (83,
	0,
	'Respiratory Monitor',
	'Informal Class',
	'Respiratory Monitor : Informal Class',
	'',
	0);
INSERT INTO PE_PE
	VALUES (84,
	1,
	64,
	0,
	14);
INSERT INTO SQ_P
	VALUES (84,
	0);
INSERT INTO MSG_M
	VALUES (85,
	75,
	84,
	0);
INSERT INTO MSG_SM
	VALUES (85,
	'Informal Message',
	'',
	'',
	'lastTemp',
	'37.84',
	0,
	'Informal Message',
	'');
INSERT INTO MSG_ISM
	VALUES (85);
INSERT INTO SQ_LS
	VALUES (84,
	82,
	'',
	0);
INSERT INTO PE_PE
	VALUES (86,
	1,
	64,
	0,
	14);
INSERT INTO SQ_P
	VALUES (86,
	0);
INSERT INTO MSG_M
	VALUES (87,
	75,
	86,
	0);
INSERT INTO MSG_SM
	VALUES (87,
	'Informal Message',
	'',
	'',
	'lastRate',
	'35',
	0,
	'Informal Message',
	'');
INSERT INTO MSG_ISM
	VALUES (87);
INSERT INTO SQ_LS
	VALUES (86,
	83,
	'',
	0);
INSERT INTO PE_PE
	VALUES (85,
	1,
	64,
	0,
	17);
INSERT INTO PE_PE
	VALUES (87,
	1,
	64,
	0,
	17);
INSERT INTO PE_PE
	VALUES (74,
	1,
	64,
	0,
	17);
INSERT INTO PE_PE
	VALUES (77,
	1,
	64,
	0,
	17);
INSERT INTO PE_PE
	VALUES (78,
	1,
	64,
	0,
	17);
INSERT INTO PE_PE
	VALUES (88,
	1,
	20,
	0,
	7);
INSERT INTO EP_PKG
	VALUES (88,
	0,
	1,
	'Increased Activity «activity»',
	'',
	0);
INSERT INTO PE_PE
	VALUES (89,
	1,
	88,
	0,
	11);
INSERT INTO A_AP
	VALUES (89,
	0,
	'',
	'');
INSERT INTO PE_PE
	VALUES (90,
	1,
	88,
	0,
	11);
INSERT INTO A_AP
	VALUES (90,
	0,
	'',
	'');
INSERT INTO PE_PE
	VALUES (91,
	1,
	88,
	0,
	11);
INSERT INTO A_AP
	VALUES (91,
	0,
	'',
	'');
INSERT INTO PE_PE
	VALUES (92,
	1,
	88,
	0,
	11);
INSERT INTO A_AP
	VALUES (92,
	0,
	'',
	'');
INSERT INTO PE_PE
	VALUES (93,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (93,
	0);
INSERT INTO A_OBJ
	VALUES (93,
	'Monitor',
	'');
INSERT INTO PE_PE
	VALUES (94,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (94,
	0);
INSERT INTO A_OBJ
	VALUES (94,
	'Pacer',
	'');
INSERT INTO PE_PE
	VALUES (95,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (95,
	0);
INSERT INTO A_OBJ
	VALUES (95,
	'Host',
	'');
INSERT INTO PE_PE
	VALUES (96,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (96,
	0);
INSERT INTO A_ACT
	VALUES (96);
INSERT INTO A_GA
	VALUES (96,
	'Check activity levels',
	'This action asks the associated low level monitor elements for the current
readings to determing the current activity level.');
INSERT INTO PE_PE
	VALUES (97,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (97,
	0);
INSERT INTO A_CTL
	VALUES (97);
INSERT INTO A_DM
	VALUES (97,
	'increased activity?',
	'');
INSERT INTO PE_PE
	VALUES (98,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (98,
	0,
	'',
	'',
	97,
	96);
INSERT INTO PE_PE
	VALUES (99,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (99,
	0);
INSERT INTO A_ACT
	VALUES (99);
INSERT INTO A_AE
	VALUES (99);
INSERT INTO A_AEA
	VALUES (99,
	'Accept increased activity',
	'');
INSERT INTO PE_PE
	VALUES (100,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (100,
	0);
INSERT INTO A_ACT
	VALUES (100);
INSERT INTO A_SS
	VALUES (100,
	'Notify of increased activity',
	'');
INSERT INTO PE_PE
	VALUES (101,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (101,
	0,
	'increased activity',
	'',
	100,
	97);
INSERT INTO PE_PE
	VALUES (102,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (102,
	0);
INSERT INTO A_ACT
	VALUES (102);
INSERT INTO A_GA
	VALUES (102,
	'Diastole',
	'');
INSERT INTO PE_PE
	VALUES (103,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (103,
	0);
INSERT INTO A_ACT
	VALUES (103);
INSERT INTO A_GA
	VALUES (103,
	'Systole',
	'');
INSERT INTO PE_PE
	VALUES (104,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (104,
	0);
INSERT INTO A_ACT
	VALUES (104);
INSERT INTO A_GA
	VALUES (104,
	'Synchronize with host',
	'');
INSERT INTO PE_PE
	VALUES (105,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (105,
	0);
INSERT INTO A_CTL
	VALUES (105);
INSERT INTO A_FJ
	VALUES (105,
	'',
	'');
INSERT INTO PE_PE
	VALUES (106,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (106,
	0);
INSERT INTO A_CTL
	VALUES (106);
INSERT INTO A_FJ
	VALUES (106,
	'',
	'');
INSERT INTO PE_PE
	VALUES (107,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (107,
	0,
	'',
	'',
	105,
	102);
INSERT INTO PE_PE
	VALUES (108,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (108,
	0,
	'',
	'',
	106,
	103);
INSERT INTO PE_PE
	VALUES (109,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (109,
	0);
INSERT INTO A_CTL
	VALUES (109);
INSERT INTO A_DM
	VALUES (109,
	'recevied increased activity notification?',
	'');
INSERT INTO PE_PE
	VALUES (110,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (110,
	0);
INSERT INTO A_ACT
	VALUES (110);
INSERT INTO A_GA
	VALUES (110,
	'Increase pulse frequency',
	'');
INSERT INTO PE_PE
	VALUES (111,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (111,
	0,
	'increased activity',
	'',
	110,
	109);
INSERT INTO PE_PE
	VALUES (112,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (112,
	0,
	'',
	'',
	109,
	104);
INSERT INTO PE_PE
	VALUES (113,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (113,
	0);
INSERT INTO A_ACT
	VALUES (113);
INSERT INTO A_GA
	VALUES (113,
	'Generate pulse',
	'');
INSERT INTO PE_PE
	VALUES (114,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (114,
	0);
INSERT INTO A_CTL
	VALUES (114);
INSERT INTO A_DM
	VALUES (114,
	'',
	'');
INSERT INTO PE_PE
	VALUES (115,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (115,
	0,
	'',
	'',
	114,
	110);
INSERT INTO PE_PE
	VALUES (116,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (116,
	0,
	'',
	'',
	113,
	114);
INSERT INTO PE_PE
	VALUES (117,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (117,
	0);
INSERT INTO A_CTL
	VALUES (117);
INSERT INTO A_DM
	VALUES (117,
	'Diastole or Systole?',
	'');
INSERT INTO PE_PE
	VALUES (118,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (118,
	0);
INSERT INTO A_CTL
	VALUES (118);
INSERT INTO A_FJ
	VALUES (118,
	'',
	'');
INSERT INTO PE_PE
	VALUES (119,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (119,
	0,
	'',
	'',
	118,
	100);
INSERT INTO PE_PE
	VALUES (120,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (120,
	0,
	'',
	'',
	99,
	118);
INSERT INTO PE_PE
	VALUES (121,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (121,
	0);
INSERT INTO A_CTL
	VALUES (121);
INSERT INTO A_FJ
	VALUES (121,
	'',
	'');
INSERT INTO PE_PE
	VALUES (122,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (122,
	0,
	'',
	'',
	121,
	113);
INSERT INTO PE_PE
	VALUES (123,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (123,
	0,
	'',
	'',
	117,
	121);
INSERT INTO PE_PE
	VALUES (124,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (124,
	0);
INSERT INTO A_CTL
	VALUES (124);
INSERT INTO A_DM
	VALUES (124,
	'',
	'');
INSERT INTO PE_PE
	VALUES (125,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (125,
	0,
	'',
	'',
	124,
	94);
INSERT INTO PE_PE
	VALUES (126,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (126,
	0,
	'',
	'',
	104,
	124);
INSERT INTO PE_PE
	VALUES (127,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (127,
	0,
	'',
	'',
	124,
	99);
INSERT INTO PE_PE
	VALUES (128,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (128,
	0,
	'',
	'',
	124,
	121);
INSERT INTO PE_PE
	VALUES (129,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (129,
	0,
	'',
	'',
	124,
	106);
INSERT INTO PE_PE
	VALUES (130,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (130,
	0,
	'',
	'',
	124,
	105);
INSERT INTO PE_PE
	VALUES (131,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (131,
	0);
INSERT INTO A_CTL
	VALUES (131);
INSERT INTO A_DM
	VALUES (131,
	'',
	'');
INSERT INTO PE_PE
	VALUES (132,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (132,
	0,
	'',
	'',
	131,
	93);
INSERT INTO PE_PE
	VALUES (133,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (133,
	0,
	'',
	'',
	96,
	131);
INSERT INTO PE_PE
	VALUES (134,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (134,
	0,
	'',
	'',
	131,
	118);
INSERT INTO PE_PE
	VALUES (135,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (135,
	0,
	'normal activity',
	'',
	131,
	97);
INSERT INTO PE_PE
	VALUES (136,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (136,
	0);
INSERT INTO A_CTL
	VALUES (136);
INSERT INTO A_DM
	VALUES (136,
	'',
	'');
INSERT INTO PE_PE
	VALUES (137,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (137,
	0,
	'',
	'',
	136,
	105);
INSERT INTO PE_PE
	VALUES (138,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (138,
	0,
	'',
	'',
	103,
	136);
INSERT INTO PE_PE
	VALUES (139,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (139,
	0,
	'systole',
	'',
	136,
	117);
INSERT INTO PE_PE
	VALUES (140,
	1,
	88,
	0,
	18);
INSERT INTO A_N
	VALUES (140,
	0);
INSERT INTO A_CTL
	VALUES (140);
INSERT INTO A_DM
	VALUES (140,
	'',
	'');
INSERT INTO PE_PE
	VALUES (141,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (141,
	0,
	'',
	'',
	140,
	95);
INSERT INTO PE_PE
	VALUES (142,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (142,
	0,
	'',
	'',
	102,
	140);
INSERT INTO PE_PE
	VALUES (143,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (143,
	0,
	'diastole',
	'',
	140,
	117);
INSERT INTO PE_PE
	VALUES (144,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (144,
	0,
	'',
	'',
	140,
	106);
INSERT INTO PE_PE
	VALUES (145,
	1,
	88,
	0,
	12);
INSERT INTO A_E
	VALUES (145,
	0,
	'normal activity',
	'',
	114,
	109);
INSERT INTO EP_PKG
	VALUES (146,
	1,
	1,
	'interfaces',
	'',
	0);
INSERT INTO PE_PE
	VALUES (147,
	1,
	146,
	0,
	6);
INSERT INTO C_I
	VALUES (147,
	0,
	'synchronization',
	'');
INSERT INTO C_EP
	VALUES (148,
	147,
	-1);
INSERT INTO C_AS
	VALUES (148,
	'systolic_pulse',
	'',
	0,
	0);
INSERT INTO C_EP
	VALUES (149,
	147,
	-1);
INSERT INTO C_AS
	VALUES (149,
	'diastolic_pulse',
	'',
	0,
	148);
INSERT INTO C_EP
	VALUES (150,
	147,
	-1);
INSERT INTO C_AS
	VALUES (150,
	'systolic_pace',
	'',
	1,
	149);
INSERT INTO C_EP
	VALUES (151,
	147,
	-1);
INSERT INTO C_AS
	VALUES (151,
	'diastolic_pace',
	'',
	1,
	150);
INSERT INTO C_EP
	VALUES (152,
	147,
	-1);
INSERT INTO C_IO
	VALUES (152,
	3,
	'init',
	'',
	0,
	'',
	0);
INSERT INTO C_PP
	VALUES (153,
	152,
	5,
	'systolic_period',
	'The systolic period is the duration of time between diastolic and systolic pulses.',
	0,
	'',
	0);
INSERT INTO C_PP
	VALUES (154,
	152,
	5,
	'diastolic_period',
	'The diastolic period is the duration of time between systolic and diastolic pulses.',
	0,
	'',
	153);
INSERT INTO PE_PE
	VALUES (155,
	1,
	146,
	0,
	6);
INSERT INTO C_I
	VALUES (155,
	0,
	'monitor',
	'');
INSERT INTO C_EP
	VALUES (156,
	155,
	-1);
INSERT INTO C_AS
	VALUES (156,
	'increased_activty',
	'',
	0,
	0);
INSERT INTO C_PP
	VALUES (157,
	156,
	5,
	'current_temp',
	'',
	0,
	'',
	0);
INSERT INTO C_PP
	VALUES (158,
	156,
	5,
	'current_rate',
	'',
	0,
	'',
	157);
INSERT INTO PE_PE
	VALUES (159,
	1,
	146,
	0,
	6);
INSERT INTO C_I
	VALUES (159,
	0,
	'host',
	'');
INSERT INTO C_EP
	VALUES (160,
	159,
	-1);
INSERT INTO C_AS
	VALUES (160,
	'breath_taken',
	'',
	0,
	0);
INSERT INTO C_EP
	VALUES (161,
	159,
	-1);
INSERT INTO C_AS
	VALUES (161,
	'current_temp',
	'',
	0,
	160);
INSERT INTO EP_PKG
	VALUES (162,
	1,
	1,
	'library',
	'',
	0);
INSERT INTO PE_PE
	VALUES (163,
	1,
	162,
	0,
	2);
INSERT INTO C_C
	VALUES (163,
	0,
	0,
	'heart',
	'',
	0,
	0);
INSERT INTO C_PO
	VALUES (164,
	163,
	'out_to_body',
	0,
	0);
INSERT INTO C_IR
	VALUES (165,
	147,
	0,
	164);
INSERT INTO C_R
	VALUES (165,
	'synchronization',
	'',
	'Unnamed Interface');
INSERT INTO SPR_REP
	VALUES (166,
	148,
	165);
INSERT INTO SPR_RS
	VALUES (166,
	'systolic_pulse',
	'',
	'',
	1);
INSERT INTO ACT_RSB
	VALUES (167,
	166);
INSERT INTO ACT_ACT
	VALUES (167,
	'signal',
	0,
	0,
	0,
	0,
	'out_to_body::synchronization::systolic_pulse',
	0);
INSERT INTO SPR_REP
	VALUES (168,
	149,
	165);
INSERT INTO SPR_RS
	VALUES (168,
	'diastolic_pulse',
	'',
	'',
	1);
INSERT INTO ACT_RSB
	VALUES (169,
	168);
INSERT INTO ACT_ACT
	VALUES (169,
	'signal',
	0,
	0,
	0,
	0,
	'out_to_body::synchronization::diastolic_pulse',
	0);
INSERT INTO SPR_REP
	VALUES (170,
	150,
	165);
INSERT INTO SPR_RS
	VALUES (170,
	'systolic_pace',
	'',
	'',
	1);
INSERT INTO ACT_RSB
	VALUES (171,
	170);
INSERT INTO ACT_ACT
	VALUES (171,
	'signal',
	0,
	0,
	0,
	0,
	'out_to_body::synchronization::systolic_pace',
	0);
INSERT INTO SPR_REP
	VALUES (172,
	151,
	165);
INSERT INTO SPR_RS
	VALUES (172,
	'diastolic_pace',
	'',
	'',
	1);
INSERT INTO ACT_RSB
	VALUES (173,
	172);
INSERT INTO ACT_ACT
	VALUES (173,
	'signal',
	0,
	0,
	0,
	0,
	'out_to_body::synchronization::diastolic_pace',
	0);
INSERT INTO SPR_REP
	VALUES (174,
	152,
	165);
INSERT INTO SPR_RO
	VALUES (174,
	'init',
	'',
	'',
	1);
INSERT INTO ACT_ROB
	VALUES (175,
	174);
INSERT INTO ACT_ACT
	VALUES (175,
	'interface operation',
	0,
	0,
	0,
	0,
	'out_to_body::synchronization::init',
	0);
INSERT INTO C_PO
	VALUES (176,
	163,
	'host',
	0,
	0);
INSERT INTO C_IR
	VALUES (177,
	159,
	0,
	176);
INSERT INTO C_R
	VALUES (177,
	'host',
	'',
	'Unnamed Interface');
INSERT INTO SPR_REP
	VALUES (178,
	160,
	177);
INSERT INTO SPR_RS
	VALUES (178,
	'breath_taken',
	'',
	'',
	1);
INSERT INTO ACT_RSB
	VALUES (179,
	178);
INSERT INTO ACT_ACT
	VALUES (179,
	'signal',
	0,
	0,
	0,
	0,
	'host::host::breath_taken',
	0);
INSERT INTO SPR_REP
	VALUES (180,
	161,
	177);
INSERT INTO SPR_RS
	VALUES (180,
	'current_temp',
	'',
	'',
	1);
INSERT INTO ACT_RSB
	VALUES (181,
	180);
INSERT INTO ACT_ACT
	VALUES (181,
	'signal',
	0,
	0,
	0,
	0,
	'host::host::current_temp',
	0);
INSERT INTO PE_PE
	VALUES (182,
	1,
	0,
	163,
	7);
INSERT INTO EP_PKG
	VALUES (182,
	0,
	1,
	'heart',
	'',
	0);
INSERT INTO PE_PE
	VALUES (183,
	1,
	182,
	0,
	4);
INSERT INTO O_OBJ
	VALUES (183,
	'heart',
	1,
	'HEART',
	'',
	0);
INSERT INTO O_NBATTR
	VALUES (184,
	183);
INSERT INTO O_BATTR
	VALUES (184,
	183);
INSERT INTO O_ATTR
	VALUES (184,
	183,
	0,
	'current_state',
	'',
	'',
	'current_state',
	0,
	9,
	'',
	'');
INSERT INTO O_ID
	VALUES (0,
	183);
INSERT INTO O_ID
	VALUES (1,
	183);
INSERT INTO O_ID
	VALUES (2,
	183);
INSERT INTO SM_ISM
	VALUES (185,
	183);
INSERT INTO SM_SM
	VALUES (185,
	'',
	0);
INSERT INTO SM_MOORE
	VALUES (185);
INSERT INTO SM_LEVT
	VALUES (186,
	185,
	0);
INSERT INTO SM_SEVT
	VALUES (186,
	185,
	0);
INSERT INTO SM_EVT
	VALUES (186,
	185,
	0,
	1,
	'done',
	0,
	'',
	'HEART1',
	'');
INSERT INTO SM_LEVT
	VALUES (187,
	185,
	0);
INSERT INTO SM_SEVT
	VALUES (187,
	185,
	0);
INSERT INTO SM_EVT
	VALUES (187,
	185,
	0,
	2,
	'diastolic_pace',
	0,
	'',
	'HEART2',
	'');
INSERT INTO SM_LEVT
	VALUES (188,
	185,
	0);
INSERT INTO SM_SEVT
	VALUES (188,
	185,
	0);
INSERT INTO SM_EVT
	VALUES (188,
	185,
	0,
	3,
	'systolic_pace',
	0,
	'',
	'HEART3',
	'');
INSERT INTO SM_LEVT
	VALUES (189,
	185,
	0);
INSERT INTO SM_SEVT
	VALUES (189,
	185,
	0);
INSERT INTO SM_EVT
	VALUES (189,
	185,
	0,
	4,
	'diastolic_pulse',
	0,
	'',
	'HEART4',
	'');
INSERT INTO SM_LEVT
	VALUES (190,
	185,
	0);
INSERT INTO SM_SEVT
	VALUES (190,
	185,
	0);
INSERT INTO SM_EVT
	VALUES (190,
	185,
	0,
	5,
	'systolic_pulse',
	0,
	'',
	'HEART5',
	'');
INSERT INTO SM_STATE
	VALUES (191,
	185,
	0,
	'atrial systole',
	5,
	0);
INSERT INTO SM_SEME
	VALUES (191,
	186,
	185,
	0);
INSERT INTO SM_CH
	VALUES (191,
	187,
	185,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (191,
	187,
	185,
	0);
INSERT INTO SM_CH
	VALUES (191,
	188,
	185,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (191,
	188,
	185,
	0);
INSERT INTO SM_CH
	VALUES (191,
	189,
	185,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (191,
	189,
	185,
	0);
INSERT INTO SM_CH
	VALUES (191,
	190,
	185,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (191,
	190,
	185,
	0);
INSERT INTO SM_MOAH
	VALUES (192,
	185,
	191);
INSERT INTO SM_AH
	VALUES (192,
	185);
INSERT INTO SM_ACT
	VALUES (192,
	185,
	1,
	'generate HEART1:done() to self;',
	'');
INSERT INTO ACT_SAB
	VALUES (193,
	185,
	192);
INSERT INTO ACT_ACT
	VALUES (193,
	'state',
	0,
	0,
	0,
	0,
	'heart::atrial systole',
	0);
INSERT INTO SM_STATE
	VALUES (194,
	185,
	0,
	'ventricular systole',
	2,
	0);
INSERT INTO SM_EIGN
	VALUES (194,
	186,
	185,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (194,
	186,
	185,
	0);
INSERT INTO SM_SEME
	VALUES (194,
	187,
	185,
	0);
INSERT INTO SM_CH
	VALUES (194,
	188,
	185,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (194,
	188,
	185,
	0);
INSERT INTO SM_SEME
	VALUES (194,
	189,
	185,
	0);
INSERT INTO SM_CH
	VALUES (194,
	190,
	185,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (194,
	190,
	185,
	0);
INSERT INTO SM_MOAH
	VALUES (195,
	185,
	194);
INSERT INTO SM_AH
	VALUES (195,
	185);
INSERT INTO SM_ACT
	VALUES (195,
	185,
	1,
	'',
	'');
INSERT INTO ACT_SAB
	VALUES (196,
	185,
	195);
INSERT INTO ACT_ACT
	VALUES (196,
	'state',
	0,
	0,
	0,
	0,
	'heart::ventricular systole',
	0);
INSERT INTO SM_STATE
	VALUES (197,
	185,
	0,
	'atrial diastole',
	3,
	0);
INSERT INTO SM_SEME
	VALUES (197,
	186,
	185,
	0);
INSERT INTO SM_CH
	VALUES (197,
	187,
	185,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (197,
	187,
	185,
	0);
INSERT INTO SM_CH
	VALUES (197,
	188,
	185,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (197,
	188,
	185,
	0);
INSERT INTO SM_CH
	VALUES (197,
	189,
	185,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (197,
	189,
	185,
	0);
INSERT INTO SM_CH
	VALUES (197,
	190,
	185,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (197,
	190,
	185,
	0);
INSERT INTO SM_MOAH
	VALUES (198,
	185,
	197);
INSERT INTO SM_AH
	VALUES (198,
	185);
INSERT INTO SM_ACT
	VALUES (198,
	185,
	1,
	'generate HEART1:done() to self;',
	'');
INSERT INTO ACT_SAB
	VALUES (199,
	185,
	198);
INSERT INTO ACT_ACT
	VALUES (199,
	'state',
	0,
	0,
	0,
	0,
	'heart::atrial diastole',
	0);
INSERT INTO SM_STATE
	VALUES (200,
	185,
	0,
	'ventricular diastole',
	4,
	0);
INSERT INTO SM_CH
	VALUES (200,
	186,
	185,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (200,
	186,
	185,
	0);
INSERT INTO SM_CH
	VALUES (200,
	187,
	185,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (200,
	187,
	185,
	0);
INSERT INTO SM_SEME
	VALUES (200,
	188,
	185,
	0);
INSERT INTO SM_CH
	VALUES (200,
	189,
	185,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (200,
	189,
	185,
	0);
INSERT INTO SM_SEME
	VALUES (200,
	190,
	185,
	0);
INSERT INTO SM_MOAH
	VALUES (201,
	185,
	200);
INSERT INTO SM_AH
	VALUES (201,
	185);
INSERT INTO SM_ACT
	VALUES (201,
	185,
	1,
	'',
	'');
INSERT INTO ACT_SAB
	VALUES (202,
	185,
	201);
INSERT INTO ACT_ACT
	VALUES (202,
	'state',
	0,
	0,
	0,
	0,
	'heart::ventricular diastole',
	0);
INSERT INTO SM_STATE
	VALUES (203,
	185,
	0,
	'born',
	1,
	0);
INSERT INTO SM_CH
	VALUES (203,
	186,
	185,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (203,
	186,
	185,
	0);
INSERT INTO SM_CH
	VALUES (203,
	187,
	185,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (203,
	187,
	185,
	0);
INSERT INTO SM_CH
	VALUES (203,
	188,
	185,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (203,
	188,
	185,
	0);
INSERT INTO SM_SEME
	VALUES (203,
	189,
	185,
	0);
INSERT INTO SM_SEME
	VALUES (203,
	190,
	185,
	0);
INSERT INTO SM_MOAH
	VALUES (204,
	185,
	203);
INSERT INTO SM_AH
	VALUES (204,
	185);
INSERT INTO SM_ACT
	VALUES (204,
	185,
	1,
	'',
	'');
INSERT INTO ACT_SAB
	VALUES (205,
	185,
	204);
INSERT INTO ACT_ACT
	VALUES (205,
	'state',
	0,
	0,
	0,
	0,
	'heart::born',
	0);
INSERT INTO SM_NSTXN
	VALUES (206,
	185,
	191,
	186,
	0);
INSERT INTO SM_TAH
	VALUES (207,
	185,
	206);
INSERT INTO SM_AH
	VALUES (207,
	185);
INSERT INTO SM_ACT
	VALUES (207,
	185,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (208,
	185,
	207);
INSERT INTO ACT_ACT
	VALUES (208,
	'transition',
	0,
	0,
	0,
	0,
	'HEART1: done in atrial systole to ventricular systole',
	0);
INSERT INTO SM_TXN
	VALUES (206,
	185,
	194,
	0);
INSERT INTO SM_NSTXN
	VALUES (209,
	185,
	194,
	189,
	0);
INSERT INTO SM_TAH
	VALUES (210,
	185,
	209);
INSERT INTO SM_AH
	VALUES (210,
	185);
INSERT INTO SM_ACT
	VALUES (210,
	185,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (211,
	185,
	210);
INSERT INTO ACT_ACT
	VALUES (211,
	'transition',
	0,
	0,
	0,
	0,
	'HEART4: diastolic_pulse in ventricular systole to atrial diastole',
	0);
INSERT INTO SM_TXN
	VALUES (209,
	185,
	197,
	0);
INSERT INTO SM_NSTXN
	VALUES (212,
	185,
	197,
	186,
	0);
INSERT INTO SM_TAH
	VALUES (213,
	185,
	212);
INSERT INTO SM_AH
	VALUES (213,
	185);
INSERT INTO SM_ACT
	VALUES (213,
	185,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (214,
	185,
	213);
INSERT INTO ACT_ACT
	VALUES (214,
	'transition',
	0,
	0,
	0,
	0,
	'HEART1: done in atrial diastole to ventricular diastole',
	0);
INSERT INTO SM_TXN
	VALUES (212,
	185,
	200,
	0);
INSERT INTO SM_NSTXN
	VALUES (215,
	185,
	200,
	190,
	0);
INSERT INTO SM_TAH
	VALUES (216,
	185,
	215);
INSERT INTO SM_AH
	VALUES (216,
	185);
INSERT INTO SM_ACT
	VALUES (216,
	185,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (217,
	185,
	216);
INSERT INTO ACT_ACT
	VALUES (217,
	'transition',
	0,
	0,
	0,
	0,
	'HEART5: systolic_pulse in ventricular diastole to atrial systole',
	0);
INSERT INTO SM_TXN
	VALUES (215,
	185,
	191,
	0);
INSERT INTO SM_NSTXN
	VALUES (218,
	185,
	200,
	188,
	0);
INSERT INTO SM_TAH
	VALUES (219,
	185,
	218);
INSERT INTO SM_AH
	VALUES (219,
	185);
INSERT INTO SM_ACT
	VALUES (219,
	185,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (220,
	185,
	219);
INSERT INTO ACT_ACT
	VALUES (220,
	'transition',
	0,
	0,
	0,
	0,
	'HEART3: systolic_pace in ventricular diastole to atrial systole',
	0);
INSERT INTO SM_TXN
	VALUES (218,
	185,
	191,
	0);
INSERT INTO SM_NSTXN
	VALUES (221,
	185,
	194,
	187,
	0);
INSERT INTO SM_TAH
	VALUES (222,
	185,
	221);
INSERT INTO SM_AH
	VALUES (222,
	185);
INSERT INTO SM_ACT
	VALUES (222,
	185,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (223,
	185,
	222);
INSERT INTO ACT_ACT
	VALUES (223,
	'transition',
	0,
	0,
	0,
	0,
	'HEART2: diastolic_pace in ventricular systole to atrial diastole',
	0);
INSERT INTO SM_TXN
	VALUES (221,
	185,
	197,
	0);
INSERT INTO SM_NSTXN
	VALUES (224,
	185,
	203,
	190,
	0);
INSERT INTO SM_TAH
	VALUES (225,
	185,
	224);
INSERT INTO SM_AH
	VALUES (225,
	185);
INSERT INTO SM_ACT
	VALUES (225,
	185,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (226,
	185,
	225);
INSERT INTO ACT_ACT
	VALUES (226,
	'transition',
	0,
	0,
	0,
	0,
	'HEART5: systolic_pulse in born to atrial systole',
	0);
INSERT INTO SM_TXN
	VALUES (224,
	185,
	191,
	0);
INSERT INTO SM_NSTXN
	VALUES (227,
	185,
	203,
	189,
	0);
INSERT INTO SM_TAH
	VALUES (228,
	185,
	227);
INSERT INTO SM_AH
	VALUES (228,
	185);
INSERT INTO SM_ACT
	VALUES (228,
	185,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (229,
	185,
	228);
INSERT INTO ACT_ACT
	VALUES (229,
	'transition',
	0,
	0,
	0,
	0,
	'HEART4: diastolic_pulse in born to atrial diastole',
	0);
INSERT INTO SM_TXN
	VALUES (227,
	185,
	197,
	0);
INSERT INTO SM_ASM
	VALUES (230,
	183);
INSERT INTO SM_SM
	VALUES (230,
	'',
	0);
INSERT INTO SM_MOORE
	VALUES (230);
INSERT INTO SM_SGEVT
	VALUES (231,
	230,
	0,
	0,
	172);
INSERT INTO SM_SEVT
	VALUES (231,
	230,
	0);
INSERT INTO SM_EVT
	VALUES (231,
	230,
	0,
	1,
	'diastolic_pace',
	0,
	'',
	'HEART_A1',
	'');
INSERT INTO SM_SGEVT
	VALUES (232,
	230,
	0,
	0,
	170);
INSERT INTO SM_SEVT
	VALUES (232,
	230,
	0);
INSERT INTO SM_EVT
	VALUES (232,
	230,
	0,
	2,
	'systolic_pace',
	0,
	'',
	'HEART_A2',
	'');
INSERT INTO SM_STATE
	VALUES (233,
	230,
	0,
	'receiving paces',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (233,
	231,
	230,
	0);
INSERT INTO SM_SEME
	VALUES (233,
	232,
	230,
	0);
INSERT INTO SM_MOAH
	VALUES (234,
	230,
	233);
INSERT INTO SM_AH
	VALUES (234,
	230);
INSERT INTO SM_ACT
	VALUES (234,
	230,
	1,
	'',
	'');
INSERT INTO ACT_SAB
	VALUES (235,
	230,
	234);
INSERT INTO ACT_ACT
	VALUES (235,
	'class state',
	0,
	0,
	0,
	0,
	'heart::receiving paces',
	0);
INSERT INTO SM_NSTXN
	VALUES (236,
	230,
	233,
	231,
	0);
INSERT INTO SM_TAH
	VALUES (237,
	230,
	236);
INSERT INTO SM_AH
	VALUES (237,
	230);
INSERT INTO SM_ACT
	VALUES (237,
	230,
	1,
	'select any heart from instances of HEART;
generate HEART2:diastolic_pace() to heart;',
	'');
INSERT INTO ACT_TAB
	VALUES (238,
	230,
	237);
INSERT INTO ACT_ACT
	VALUES (238,
	'class transition',
	0,
	0,
	0,
	0,
	'out_to_body::diastolic_pace in receiving paces to receiving paces',
	0);
INSERT INTO SM_TXN
	VALUES (236,
	230,
	233,
	0);
INSERT INTO SM_NSTXN
	VALUES (239,
	230,
	233,
	232,
	0);
INSERT INTO SM_TAH
	VALUES (240,
	230,
	239);
INSERT INTO SM_AH
	VALUES (240,
	230);
INSERT INTO SM_ACT
	VALUES (240,
	230,
	1,
	'select any heart from instances of HEART;
generate HEART3:systolic_pace() to heart;',
	'');
INSERT INTO ACT_TAB
	VALUES (241,
	230,
	240);
INSERT INTO ACT_ACT
	VALUES (241,
	'class transition',
	0,
	0,
	0,
	0,
	'out_to_body::systolic_pace in receiving paces to receiving paces',
	0);
INSERT INTO SM_TXN
	VALUES (239,
	230,
	233,
	0);
INSERT INTO PE_PE
	VALUES (242,
	1,
	182,
	0,
	4);
INSERT INTO O_OBJ
	VALUES (242,
	'sinus node',
	2,
	'SINUS_NODE',
	'',
	0);
INSERT INTO O_TFR
	VALUES (243,
	242,
	'log_and_adjust',
	'',
	3,
	1,
	'// Now log the periods, decrement them and continue.
LOG::LogInfo( message:"systolic period, diastolic period" );
LOG::LogInteger( message:self.systolic_period );
LOG::LogInteger( message:self.diastolic_period );
if ( self.decrement < self.systolic_period )
  self.systolic_period = self.systolic_period - self.decrement;
end if;
if ( self.decrement < self.diastolic_period )
  self.diastolic_period = self.diastolic_period - self.decrement;
end if;
',
	1,
	'',
	0);
INSERT INTO ACT_OPB
	VALUES (244,
	243);
INSERT INTO ACT_ACT
	VALUES (244,
	'operation',
	0,
	0,
	0,
	0,
	'sinus node::log_and_adjust',
	0);
INSERT INTO O_NBATTR
	VALUES (245,
	242);
INSERT INTO O_BATTR
	VALUES (245,
	242);
INSERT INTO O_ATTR
	VALUES (245,
	242,
	0,
	'systolic_period',
	'The systolic period is the duration of time between diastolic and systolic pulses.',
	'',
	'systolic_period',
	0,
	5,
	'',
	'');
INSERT INTO O_NBATTR
	VALUES (246,
	242);
INSERT INTO O_BATTR
	VALUES (246,
	242);
INSERT INTO O_ATTR
	VALUES (246,
	242,
	245,
	'diastolic_period',
	'The diastolic period is the duration of time between systolic and diastolic pulses.',
	'',
	'diastolic_period',
	0,
	5,
	'',
	'');
INSERT INTO O_NBATTR
	VALUES (247,
	242);
INSERT INTO O_BATTR
	VALUES (247,
	242);
INSERT INTO O_ATTR
	VALUES (247,
	242,
	248,
	'current_state',
	'',
	'',
	'current_state',
	0,
	9,
	'',
	'');
INSERT INTO O_NBATTR
	VALUES (248,
	242);
INSERT INTO O_BATTR
	VALUES (248,
	242);
INSERT INTO O_ATTR
	VALUES (248,
	242,
	246,
	'decrement',
	'This is the value that we reduce the periods each cycle.',
	'',
	'decrement',
	0,
	5,
	'',
	'');
INSERT INTO O_ID
	VALUES (0,
	242);
INSERT INTO O_ID
	VALUES (1,
	242);
INSERT INTO O_ID
	VALUES (2,
	242);
INSERT INTO SM_ISM
	VALUES (249,
	242);
INSERT INTO SM_SM
	VALUES (249,
	'',
	0);
INSERT INTO SM_MOORE
	VALUES (249);
INSERT INTO SM_LEVT
	VALUES (250,
	249,
	0);
INSERT INTO SM_SEVT
	VALUES (250,
	249,
	0);
INSERT INTO SM_EVT
	VALUES (250,
	249,
	0,
	1,
	'pop',
	0,
	'',
	'SINUS_NODE1',
	'');
INSERT INTO SM_LEVT
	VALUES (251,
	249,
	0);
INSERT INTO SM_SEVT
	VALUES (251,
	249,
	0);
INSERT INTO SM_EVT
	VALUES (251,
	249,
	0,
	2,
	'period',
	0,
	'',
	'SINUS_NODE2',
	'');
INSERT INTO SM_STATE
	VALUES (252,
	249,
	0,
	'pulsing systolic',
	3,
	0);
INSERT INTO SM_SEME
	VALUES (252,
	250,
	249,
	0);
INSERT INTO SM_CH
	VALUES (252,
	251,
	249,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (252,
	251,
	249,
	0);
INSERT INTO SM_MOAH
	VALUES (253,
	249,
	252);
INSERT INTO SM_AH
	VALUES (253,
	249);
INSERT INTO SM_ACT
	VALUES (253,
	249,
	1,
	'// Tell the upper heart to pulse.
select one heart related by self->HEART[R1];
generate HEART5:systolic_pulse() to heart;
// Let the rest of the body "hear" the pulse.
send out_to_body::systolic_pulse();
// Set a timer for the systolic period.
create event instance e of SINUS_NODE1:pop() to self;
t = TIM::timer_start( microseconds:self.diastolic_period, event_inst:e );

self.log_and_adjust();
',
	'');
INSERT INTO ACT_SAB
	VALUES (254,
	249,
	253);
INSERT INTO ACT_ACT
	VALUES (254,
	'state',
	0,
	0,
	0,
	0,
	'sinus node::pulsing systolic',
	0);
INSERT INTO SM_STATE
	VALUES (255,
	249,
	0,
	'pulsing diastolic',
	1,
	0);
INSERT INTO SM_CH
	VALUES (255,
	250,
	249,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (255,
	250,
	249,
	0);
INSERT INTO SM_SEME
	VALUES (255,
	251,
	249,
	0);
INSERT INTO SM_MOAH
	VALUES (256,
	249,
	255);
INSERT INTO SM_AH
	VALUES (256,
	249);
INSERT INTO SM_ACT
	VALUES (256,
	249,
	1,
	'// Pulse the heart to relax.
select one heart related by self->HEART[R1];
generate HEART4:diastolic_pulse() to heart;
// Let the rest of the body "hear" the pulse.
send out_to_body::diastolic_pulse();
// Set a timer for the diastolic period.
create event instance e of SINUS_NODE2:period() to self;
t = TIM::timer_start( microseconds:self.systolic_period, event_inst:e );
',
	'');
INSERT INTO ACT_SAB
	VALUES (257,
	249,
	256);
INSERT INTO ACT_ACT
	VALUES (257,
	'state',
	0,
	0,
	0,
	0,
	'sinus node::pulsing diastolic',
	0);
INSERT INTO SM_NSTXN
	VALUES (258,
	249,
	252,
	250,
	0);
INSERT INTO SM_TAH
	VALUES (259,
	249,
	258);
INSERT INTO SM_AH
	VALUES (259,
	249);
INSERT INTO SM_ACT
	VALUES (259,
	249,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (260,
	249,
	259);
INSERT INTO ACT_ACT
	VALUES (260,
	'transition',
	0,
	0,
	0,
	0,
	'SINUS_NODE1: pop in pulsing systolic to pulsing diastolic',
	0);
INSERT INTO SM_TXN
	VALUES (258,
	249,
	255,
	0);
INSERT INTO SM_NSTXN
	VALUES (261,
	249,
	255,
	251,
	0);
INSERT INTO SM_TAH
	VALUES (262,
	249,
	261);
INSERT INTO SM_AH
	VALUES (262,
	249);
INSERT INTO SM_ACT
	VALUES (262,
	249,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (263,
	249,
	262);
INSERT INTO ACT_ACT
	VALUES (263,
	'transition',
	0,
	0,
	0,
	0,
	'SINUS_NODE2: period in pulsing diastolic to pulsing systolic',
	0);
INSERT INTO SM_TXN
	VALUES (261,
	249,
	252,
	0);
INSERT INTO PE_PE
	VALUES (264,
	1,
	182,
	0,
	9);
INSERT INTO R_SIMP
	VALUES (264);
INSERT INTO R_REL
	VALUES (264,
	1,
	'',
	0);
INSERT INTO R_PART
	VALUES (183,
	264,
	265,
	0,
	0,
	'triggers');
INSERT INTO R_RTO
	VALUES (183,
	264,
	265,
	-1);
INSERT INTO R_OIR
	VALUES (183,
	264,
	265,
	0);
INSERT INTO R_PART
	VALUES (242,
	264,
	266,
	0,
	0,
	'is triggered by');
INSERT INTO R_RTO
	VALUES (242,
	264,
	266,
	-1);
INSERT INTO R_OIR
	VALUES (242,
	264,
	266,
	0);
INSERT INTO PE_PE
	VALUES (267,
	1,
	0,
	163,
	7);
INSERT INTO EP_PKG
	VALUES (267,
	0,
	1,
	'functions',
	'',
	0);
INSERT INTO PE_PE
	VALUES (268,
	1,
	267,
	0,
	1);
INSERT INTO S_SYNC
	VALUES (268,
	0,
	'init',
	'',
	'// Create the instances of heart and nervous system.
// Relate them together.
// Initialize the nervous system with (half) periods for the pulsing.
// Send message out to the body (pacer) to initialize.
// Kick off the simulation by sending an event to the nervous system.
create object instance heart of HEART;
create object instance snode of SINUS_NODE;
relate heart to snode across R1;
snode.systolic_period = 500000;
snode.diastolic_period = 500000;
snode.decrement = 0;
send out_to_body::init(systolic_period:snode.systolic_period, diastolic_period:snode.diastolic_period);
// Set a timer for the diastolic period to start things going.
create event instance e of SINUS_NODE2:period() to snode;
//t = TIM::timer_start_recurring( microseconds:snode.systolic_period + snode.diastolic_period, event_inst:e );
t = TIM::timer_start( microseconds:snode.systolic_period + snode.diastolic_period, event_inst:e );

',
	3,
	1,
	'');
INSERT INTO ACT_FNB
	VALUES (269,
	268);
INSERT INTO ACT_ACT
	VALUES (269,
	'function',
	0,
	0,
	0,
	0,
	'init',
	0);
INSERT INTO PE_PE
	VALUES (270,
	1,
	0,
	163,
	7);
INSERT INTO EP_PKG
	VALUES (270,
	0,
	1,
	'External Entities',
	'',
	0);
INSERT INTO PE_PE
	VALUES (271,
	1,
	270,
	0,
	5);
INSERT INTO S_EE
	VALUES (271,
	'Time',
	'The Time external entity provides date, timestamp, and timer related operations.',
	'TIM',
	0);
INSERT INTO S_BRG
	VALUES (272,
	271,
	'current_date',
	'',
	1,
	17,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (273,
	271,
	'create_date',
	'',
	1,
	17,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (274,
	273,
	'second',
	5,
	0,
	'',
	275,
	'');
INSERT INTO S_BPARM
	VALUES (276,
	273,
	'minute',
	5,
	0,
	'',
	277,
	'');
INSERT INTO S_BPARM
	VALUES (277,
	273,
	'hour',
	5,
	0,
	'',
	278,
	'');
INSERT INTO S_BPARM
	VALUES (278,
	273,
	'day',
	5,
	0,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (275,
	273,
	'month',
	5,
	0,
	'',
	276,
	'');
INSERT INTO S_BPARM
	VALUES (279,
	273,
	'year',
	5,
	0,
	'',
	274,
	'');
INSERT INTO S_BRG
	VALUES (280,
	271,
	'get_second',
	'',
	1,
	5,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (281,
	280,
	'date',
	17,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (282,
	271,
	'get_minute',
	'',
	1,
	5,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (283,
	282,
	'date',
	17,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (284,
	271,
	'get_hour',
	'',
	1,
	5,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (285,
	284,
	'date',
	17,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (286,
	271,
	'get_day',
	'',
	1,
	5,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (287,
	286,
	'date',
	17,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (288,
	271,
	'get_month',
	'',
	1,
	5,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (289,
	288,
	'date',
	17,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (290,
	271,
	'get_year',
	'',
	1,
	5,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (291,
	290,
	'date',
	17,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (292,
	271,
	'current_clock',
	'',
	1,
	19,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (293,
	271,
	'timer_start',
	'This bridge operation starts a timer set to expire in the specified number of
microseconds, generating the passed event upon expiration. Returns the instance
handle of the timer.',
	1,
	18,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (294,
	293,
	'microseconds',
	5,
	0,
	'',
	295,
	'');
INSERT INTO S_BPARM
	VALUES (295,
	293,
	'event_inst',
	13,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (296,
	271,
	'timer_start_recurring',
	'This bridge operation starts a timer set to expire in the specified number of
microseconds, generating the passed event upon expiration. Upon expiration, the
timer will be restarted and fire again in the specified number of microseconds
generating the passed event. This bridge operation returns the instance handle
of the timer.',
	1,
	18,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (297,
	296,
	'microseconds',
	5,
	0,
	'',
	298,
	'');
INSERT INTO S_BPARM
	VALUES (298,
	296,
	'event_inst',
	13,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (299,
	271,
	'timer_remaining_time',
	'Returns the time remaining (in microseconds) for the passed timer instance. If
the timer has expired, a zero value is returned.',
	1,
	5,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (300,
	299,
	'timer_inst_ref',
	18,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (301,
	271,
	'timer_reset_time',
	'This bridge operation attempts to set the passed existing timer to expire in
the specified number of microseconds. If the timer exists (that is, it has not
expired), a TRUE value is returned. If the timer no longer exists, a FALSE value
is returned.',
	1,
	4,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (302,
	301,
	'timer_inst_ref',
	18,
	0,
	'',
	303,
	'');
INSERT INTO S_BPARM
	VALUES (303,
	301,
	'microseconds',
	5,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (304,
	271,
	'timer_add_time',
	'This bridge operation attempts to add the specified number of microseconds to a
passed existing timer. If the timer exists (that is, it has not expired), a TRUE
value is returned. If the timer no longer exists, a FALSE value is returned.',
	1,
	4,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (305,
	304,
	'timer_inst_ref',
	18,
	0,
	'',
	306,
	'');
INSERT INTO S_BPARM
	VALUES (306,
	304,
	'microseconds',
	5,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (307,
	271,
	'timer_cancel',
	'This bridge operation cancels and deletes the passed timer instance. If the 
timer exists (that is, it had not expired), a TRUE value is returned. If the
timer no longer exists, a FALSE value is returned.',
	1,
	4,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (308,
	307,
	'timer_inst_ref',
	18,
	0,
	'',
	0,
	'');
INSERT INTO PE_PE
	VALUES (309,
	1,
	270,
	0,
	5);
INSERT INTO S_EE
	VALUES (309,
	'Architecture',
	'',
	'ARCH',
	0);
INSERT INTO S_BRG
	VALUES (310,
	309,
	'shutdown',
	'',
	0,
	3,
	'control stop;',
	1,
	'');
INSERT INTO ACT_BRB
	VALUES (311,
	310);
INSERT INTO ACT_ACT
	VALUES (311,
	'bridge',
	0,
	0,
	0,
	0,
	'Architecture::shutdown',
	0);
INSERT INTO PE_PE
	VALUES (312,
	1,
	270,
	0,
	5);
INSERT INTO S_EE
	VALUES (312,
	'Logging',
	'',
	'LOG',
	0);
INSERT INTO S_BRG
	VALUES (313,
	312,
	'LogSuccess',
	'',
	0,
	3,
	'',
	1,
	'');
INSERT INTO S_BPARM
	VALUES (314,
	313,
	'message',
	7,
	0,
	'',
	0,
	'');
INSERT INTO ACT_BRB
	VALUES (315,
	313);
INSERT INTO ACT_ACT
	VALUES (315,
	'bridge',
	0,
	0,
	0,
	0,
	'Logging::LogSuccess',
	0);
INSERT INTO S_BRG
	VALUES (316,
	312,
	'LogFailure',
	'',
	0,
	3,
	'',
	1,
	'');
INSERT INTO S_BPARM
	VALUES (317,
	316,
	'message',
	7,
	0,
	'',
	0,
	'');
INSERT INTO ACT_BRB
	VALUES (318,
	316);
INSERT INTO ACT_ACT
	VALUES (318,
	'bridge',
	0,
	0,
	0,
	0,
	'Logging::LogFailure',
	0);
INSERT INTO S_BRG
	VALUES (319,
	312,
	'LogInfo',
	'',
	0,
	3,
	'',
	1,
	'');
INSERT INTO S_BPARM
	VALUES (320,
	319,
	'message',
	7,
	0,
	'',
	0,
	'');
INSERT INTO ACT_BRB
	VALUES (321,
	319);
INSERT INTO ACT_ACT
	VALUES (321,
	'bridge',
	0,
	0,
	0,
	0,
	'Logging::LogInfo',
	0);
INSERT INTO S_BRG
	VALUES (322,
	312,
	'LogDate',
	'',
	0,
	3,
	'',
	1,
	'');
INSERT INTO S_BPARM
	VALUES (323,
	322,
	'd',
	17,
	0,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (324,
	322,
	'message',
	7,
	0,
	'',
	323,
	'');
INSERT INTO ACT_BRB
	VALUES (325,
	322);
INSERT INTO ACT_ACT
	VALUES (325,
	'bridge',
	0,
	0,
	0,
	0,
	'Logging::LogDate',
	0);
INSERT INTO S_BRG
	VALUES (326,
	312,
	'LogTime',
	'',
	0,
	3,
	'',
	1,
	'');
INSERT INTO S_BPARM
	VALUES (327,
	326,
	't',
	19,
	0,
	'',
	328,
	'');
INSERT INTO S_BPARM
	VALUES (328,
	326,
	'message',
	7,
	0,
	'',
	0,
	'');
INSERT INTO ACT_BRB
	VALUES (329,
	326);
INSERT INTO ACT_ACT
	VALUES (329,
	'bridge',
	0,
	0,
	0,
	0,
	'Logging::LogTime',
	0);
INSERT INTO S_BRG
	VALUES (330,
	312,
	'LogReal',
	'',
	0,
	3,
	'',
	1,
	'');
INSERT INTO S_BPARM
	VALUES (331,
	330,
	'r',
	6,
	0,
	'',
	332,
	'');
INSERT INTO S_BPARM
	VALUES (332,
	330,
	'message',
	7,
	0,
	'',
	0,
	'');
INSERT INTO ACT_BRB
	VALUES (333,
	330);
INSERT INTO ACT_ACT
	VALUES (333,
	'bridge',
	0,
	0,
	0,
	0,
	'Logging::LogReal',
	0);
INSERT INTO S_BRG
	VALUES (334,
	312,
	'LogInteger',
	'',
	0,
	3,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (335,
	334,
	'message',
	5,
	0,
	'',
	0,
	'');
INSERT INTO PE_PE
	VALUES (336,
	1,
	162,
	0,
	2);
INSERT INTO C_C
	VALUES (336,
	0,
	0,
	'pacer',
	'The pacer component listens to the host''s heartbeat and sends signals to
increase the rate depending on the host''s current rate.  It consists of two
internal components, one that handles generating a beat and the other that
listens for increased activity.',
	0,
	0);
INSERT INTO C_PO
	VALUES (337,
	336,
	'to_heart',
	0,
	0);
INSERT INTO C_IR
	VALUES (338,
	147,
	0,
	337);
INSERT INTO C_P
	VALUES (338,
	'synchronization',
	'Unnamed Interface',
	'');
INSERT INTO SPR_PEP
	VALUES (339,
	148,
	338);
INSERT INTO SPR_PS
	VALUES (339,
	'systolic_pulse',
	'',
	'',
	1);
INSERT INTO ACT_PSB
	VALUES (340,
	339);
INSERT INTO ACT_ACT
	VALUES (340,
	'signal',
	0,
	0,
	0,
	0,
	'to_heart::synchronization::systolic_pulse',
	0);
INSERT INTO SPR_PEP
	VALUES (341,
	149,
	338);
INSERT INTO SPR_PS
	VALUES (341,
	'diastolic_pulse',
	'',
	'',
	1);
INSERT INTO ACT_PSB
	VALUES (342,
	341);
INSERT INTO ACT_ACT
	VALUES (342,
	'signal',
	0,
	0,
	0,
	0,
	'to_heart::synchronization::diastolic_pulse',
	0);
INSERT INTO SPR_PEP
	VALUES (343,
	150,
	338);
INSERT INTO SPR_PS
	VALUES (343,
	'systolic_pace',
	'',
	'',
	1);
INSERT INTO ACT_PSB
	VALUES (344,
	343);
INSERT INTO ACT_ACT
	VALUES (344,
	'signal',
	0,
	0,
	0,
	0,
	'to_heart::synchronization::systolic_pace',
	0);
INSERT INTO SPR_PEP
	VALUES (345,
	151,
	338);
INSERT INTO SPR_PS
	VALUES (345,
	'diastolic_pace',
	'',
	'',
	1);
INSERT INTO ACT_PSB
	VALUES (346,
	345);
INSERT INTO ACT_ACT
	VALUES (346,
	'signal',
	0,
	0,
	0,
	0,
	'to_heart::synchronization::diastolic_pace',
	0);
INSERT INTO SPR_PEP
	VALUES (347,
	152,
	338);
INSERT INTO SPR_PO
	VALUES (347,
	'init',
	'',
	'',
	1);
INSERT INTO ACT_POB
	VALUES (348,
	347);
INSERT INTO ACT_ACT
	VALUES (348,
	'interface operation',
	0,
	0,
	0,
	0,
	'to_heart::synchronization::init',
	0);
INSERT INTO C_PO
	VALUES (349,
	336,
	'host',
	0,
	0);
INSERT INTO C_IR
	VALUES (350,
	159,
	0,
	349);
INSERT INTO C_P
	VALUES (350,
	'host',
	'Unnamed Interface',
	'');
INSERT INTO SPR_PEP
	VALUES (351,
	160,
	350);
INSERT INTO SPR_PS
	VALUES (351,
	'breath_taken',
	'',
	'',
	1);
INSERT INTO ACT_PSB
	VALUES (352,
	351);
INSERT INTO ACT_ACT
	VALUES (352,
	'signal',
	0,
	0,
	0,
	0,
	'host::host::breath_taken',
	0);
INSERT INTO SPR_PEP
	VALUES (353,
	161,
	350);
INSERT INTO SPR_PS
	VALUES (353,
	'current_temp',
	'',
	'',
	1);
INSERT INTO ACT_PSB
	VALUES (354,
	353);
INSERT INTO ACT_ACT
	VALUES (354,
	'signal',
	0,
	0,
	0,
	0,
	'host::host::current_temp',
	0);
INSERT INTO PA_DIC
	VALUES (336,
	355);
INSERT INTO C_DG
	VALUES (355);
INSERT INTO C_RID
	VALUES (338,
	355);
INSERT INTO PA_DIC
	VALUES (336,
	356);
INSERT INTO C_DG
	VALUES (356);
INSERT INTO C_RID
	VALUES (350,
	356);
INSERT INTO PE_PE
	VALUES (357,
	-1,
	0,
	336,
	-1);
INSERT INTO CL_IC
	VALUES (357,
	358,
	0,
	0,
	0,
	'',
	'avpace::library::pacemaker',
	'');
INSERT INTO CL_IIR
	VALUES (359,
	360,
	357,
	355,
	'synchronization',
	'');
INSERT INTO CL_IP
	VALUES (359,
	'synchronization',
	'');
INSERT INTO PE_PE
	VALUES (361,
	1,
	0,
	358,
	7);
INSERT INTO EP_PKG
	VALUES (361,
	0,
	1,
	'pacemaker',
	'',
	0);
INSERT INTO PE_PE
	VALUES (23,
	1,
	361,
	0,
	4);
INSERT INTO O_OBJ
	VALUES (23,
	'pacer',
	1,
	'PACER',
	'',
	0);
INSERT INTO O_NBATTR
	VALUES (26,
	23);
INSERT INTO O_BATTR
	VALUES (26,
	23);
INSERT INTO O_ATTR
	VALUES (26,
	23,
	0,
	'systolic_tolerance',
	'',
	'',
	'systolic_tolerance',
	0,
	5,
	'',
	'');
INSERT INTO O_NBATTR
	VALUES (28,
	23);
INSERT INTO O_BATTR
	VALUES (28,
	23);
INSERT INTO O_ATTR
	VALUES (28,
	23,
	26,
	'diastolic_tolerance',
	'',
	'',
	'diastolic_tolerance',
	0,
	5,
	'',
	'');
INSERT INTO O_NBATTR
	VALUES (30,
	23);
INSERT INTO O_BATTR
	VALUES (30,
	23);
INSERT INTO O_ATTR
	VALUES (30,
	23,
	32,
	'current_state',
	'',
	'',
	'current_state',
	0,
	9,
	'',
	'');
INSERT INTO O_NBATTR
	VALUES (32,
	23);
INSERT INTO O_BATTR
	VALUES (32,
	23);
INSERT INTO O_ATTR
	VALUES (32,
	23,
	28,
	'cycle_count',
	'',
	'',
	'cycle_count',
	0,
	5,
	'',
	'');
INSERT INTO O_NBATTR
	VALUES (34,
	23);
INSERT INTO O_BATTR
	VALUES (34,
	23);
INSERT INTO O_ATTR
	VALUES (34,
	23,
	30,
	'timeout_timer',
	'',
	'',
	'timeout_timer',
	0,
	18,
	'',
	'');
INSERT INTO O_NBATTR
	VALUES (36,
	23);
INSERT INTO O_BATTR
	VALUES (36,
	23);
INSERT INTO O_ATTR
	VALUES (36,
	23,
	34,
	'systolic_timeout',
	'Duration of wait time before we decide that the expected systolic pulse has been missed.',
	'',
	'systolic_timeout',
	0,
	5,
	'',
	'');
INSERT INTO O_NBATTR
	VALUES (38,
	23);
INSERT INTO O_BATTR
	VALUES (38,
	23);
INSERT INTO O_ATTR
	VALUES (38,
	23,
	36,
	'diastolic_timeout',
	'Duration of wait time before we decide that the expected diastolic pulse has been missed.',
	'',
	'diastolic_timeout',
	0,
	5,
	'',
	'');
INSERT INTO O_ID
	VALUES (0,
	23);
INSERT INTO O_ID
	VALUES (1,
	23);
INSERT INTO O_ID
	VALUES (2,
	23);
INSERT INTO SM_ISM
	VALUES (362,
	23);
INSERT INTO SM_SM
	VALUES (362,
	'',
	0);
INSERT INTO SM_MOORE
	VALUES (362);
INSERT INTO SM_EVTDI
	VALUES (363,
	362,
	'blood_temp',
	'',
	5,
	'',
	364,
	0);
INSERT INTO SM_EVTDI
	VALUES (365,
	362,
	'respiratory_rate',
	'',
	5,
	'',
	364,
	363);
INSERT INTO SM_LEVT
	VALUES (366,
	362,
	0);
INSERT INTO SM_SEVT
	VALUES (366,
	362,
	0);
INSERT INTO SM_EVT
	VALUES (366,
	362,
	0,
	1,
	'systolic_pulse',
	0,
	'',
	'PACER1',
	'');
INSERT INTO SM_LEVT
	VALUES (367,
	362,
	0);
INSERT INTO SM_SEVT
	VALUES (367,
	362,
	0);
INSERT INTO SM_EVT
	VALUES (367,
	362,
	0,
	2,
	'diastolic_pulse',
	0,
	'',
	'PACER2',
	'');
INSERT INTO SM_LEVT
	VALUES (368,
	362,
	0);
INSERT INTO SM_SEVT
	VALUES (368,
	362,
	0);
INSERT INTO SM_EVT
	VALUES (368,
	362,
	0,
	3,
	'timeout',
	0,
	'',
	'PACER3',
	'');
INSERT INTO SM_LEVT
	VALUES (364,
	362,
	0);
INSERT INTO SM_SEVT
	VALUES (364,
	362,
	0);
INSERT INTO SM_EVT
	VALUES (364,
	362,
	0,
	4,
	'increased_activity',
	0,
	'',
	'PACER4',
	'');
INSERT INTO SM_LEVT
	VALUES (369,
	362,
	0);
INSERT INTO SM_SEVT
	VALUES (369,
	362,
	0);
INSERT INTO SM_EVT
	VALUES (369,
	362,
	0,
	5,
	'rate_increased',
	0,
	'',
	'PACER5',
	'');
INSERT INTO SM_STATE
	VALUES (370,
	362,
	0,
	'syncing',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (370,
	366,
	362,
	0);
INSERT INTO SM_SEME
	VALUES (370,
	367,
	362,
	0);
INSERT INTO SM_CH
	VALUES (370,
	368,
	362,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (370,
	368,
	362,
	0);
INSERT INTO SM_SEME
	VALUES (370,
	364,
	362,
	0);
INSERT INTO SM_CH
	VALUES (370,
	369,
	362,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (370,
	369,
	362,
	0);
INSERT INTO SM_MOAH
	VALUES (371,
	362,
	370);
INSERT INTO SM_AH
	VALUES (371,
	362);
INSERT INTO SM_ACT
	VALUES (371,
	362,
	1,
	'',
	'');
INSERT INTO ACT_SAB
	VALUES (372,
	362,
	371);
INSERT INTO ACT_ACT
	VALUES (372,
	'state',
	0,
	373,
	0,
	0,
	'pacer::syncing',
	0);
INSERT INTO ACT_BLK
	VALUES (373,
	0,
	0,
	0,
	'',
	'',
	'',
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
	372,
	0);
INSERT INTO SM_STATE
	VALUES (374,
	362,
	0,
	'listening for systolic pulse',
	2,
	0);
INSERT INTO SM_SEME
	VALUES (374,
	366,
	362,
	0);
INSERT INTO SM_CH
	VALUES (374,
	367,
	362,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (374,
	367,
	362,
	0);
INSERT INTO SM_SEME
	VALUES (374,
	368,
	362,
	0);
INSERT INTO SM_CH
	VALUES (374,
	364,
	362,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (374,
	364,
	362,
	0);
INSERT INTO SM_CH
	VALUES (374,
	369,
	362,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (374,
	369,
	362,
	0);
INSERT INTO SM_MOAH
	VALUES (375,
	362,
	374);
INSERT INTO SM_AH
	VALUES (375,
	362);
INSERT INTO SM_ACT
	VALUES (375,
	362,
	1,
	'// Cancel existing timeout timer
existed = TIM::timer_cancel(timer_inst_ref:self.timeout_timer);

// Set up the timeout timer
create event instance timeout of PACER3:''timeout'' to self;
self.timeout_timer = TIM::timer_start(microseconds:self.systolic_timeout,
                                                            event_inst:timeout);

self.cycle_count = self.cycle_count + 1;
LOG::LogInfo( message:"cycle count" );
LOG::LogInteger( message:self.cycle_count );
',
	'');
INSERT INTO ACT_SAB
	VALUES (376,
	362,
	375);
INSERT INTO ACT_ACT
	VALUES (376,
	'state',
	0,
	377,
	0,
	0,
	'pacer::listening for systolic pulse',
	0);
INSERT INTO ACT_BLK
	VALUES (377,
	0,
	0,
	0,
	'LOG',
	'',
	'',
	11,
	1,
	11,
	1,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	376,
	0);
INSERT INTO ACT_SMT
	VALUES (378,
	377,
	379,
	2,
	1,
	'pacer::listening for systolic pulse line: 2');
INSERT INTO ACT_AI
	VALUES (378,
	380,
	381,
	0,
	0);
INSERT INTO ACT_SMT
	VALUES (379,
	377,
	382,
	5,
	1,
	'pacer::listening for systolic pulse line: 5');
INSERT INTO E_ESS
	VALUES (379,
	1,
	0,
	5,
	34,
	5,
	41,
	2,
	11,
	0,
	0,
	0,
	0);
INSERT INTO E_CES
	VALUES (379,
	1,
	383);
INSERT INTO E_CSME
	VALUES (379,
	368,
	362);
INSERT INTO E_CEI
	VALUES (379,
	384);
INSERT INTO ACT_SMT
	VALUES (382,
	377,
	385,
	6,
	1,
	'pacer::listening for systolic pulse line: 6');
INSERT INTO ACT_AI
	VALUES (382,
	386,
	387,
	0,
	0);
INSERT INTO ACT_SMT
	VALUES (385,
	377,
	388,
	9,
	1,
	'pacer::listening for systolic pulse line: 9');
INSERT INTO ACT_AI
	VALUES (385,
	389,
	390,
	0,
	0);
INSERT INTO ACT_SMT
	VALUES (388,
	377,
	391,
	10,
	1,
	'pacer::listening for systolic pulse line: 10');
INSERT INTO ACT_BRG
	VALUES (388,
	319,
	10,
	6,
	10,
	1);
INSERT INTO ACT_SMT
	VALUES (391,
	377,
	0,
	11,
	1,
	'pacer::listening for systolic pulse line: 11');
INSERT INTO ACT_BRG
	VALUES (391,
	334,
	11,
	6,
	11,
	1);
INSERT INTO V_VAL
	VALUES (381,
	1,
	1,
	2,
	1,
	7,
	0,
	0,
	0,
	0,
	4,
	377);
INSERT INTO V_TVL
	VALUES (381,
	392);
INSERT INTO V_VAL
	VALUES (380,
	0,
	0,
	2,
	16,
	-1,
	2,
	29,
	0,
	0,
	4,
	377);
INSERT INTO V_BRV
	VALUES (380,
	307,
	1,
	2,
	11);
INSERT INTO V_VAL
	VALUES (393,
	0,
	0,
	2,
	44,
	47,
	0,
	0,
	0,
	0,
	11,
	377);
INSERT INTO V_IRF
	VALUES (393,
	384);
INSERT INTO V_VAL
	VALUES (394,
	0,
	0,
	2,
	49,
	61,
	0,
	0,
	0,
	0,
	18,
	377);
INSERT INTO V_AVL
	VALUES (394,
	393,
	23,
	34);
INSERT INTO V_PAR
	VALUES (394,
	0,
	380,
	'timer_inst_ref',
	0,
	2,
	29);
INSERT INTO V_VAL
	VALUES (395,
	1,
	0,
	6,
	1,
	4,
	0,
	0,
	0,
	0,
	11,
	377);
INSERT INTO V_IRF
	VALUES (395,
	384);
INSERT INTO V_VAL
	VALUES (387,
	1,
	0,
	6,
	6,
	18,
	0,
	0,
	0,
	0,
	18,
	377);
INSERT INTO V_AVL
	VALUES (387,
	395,
	23,
	34);
INSERT INTO V_VAL
	VALUES (386,
	0,
	0,
	6,
	27,
	-1,
	6,
	39,
	7,
	61,
	18,
	377);
INSERT INTO V_BRV
	VALUES (386,
	293,
	1,
	6,
	22);
INSERT INTO V_VAL
	VALUES (396,
	0,
	0,
	6,
	52,
	55,
	0,
	0,
	0,
	0,
	11,
	377);
INSERT INTO V_IRF
	VALUES (396,
	384);
INSERT INTO V_VAL
	VALUES (397,
	0,
	0,
	6,
	57,
	72,
	0,
	0,
	0,
	0,
	5,
	377);
INSERT INTO V_AVL
	VALUES (397,
	396,
	23,
	36);
INSERT INTO V_PAR
	VALUES (397,
	0,
	386,
	'microseconds',
	398,
	6,
	39);
INSERT INTO V_VAL
	VALUES (398,
	0,
	0,
	7,
	72,
	78,
	0,
	0,
	0,
	0,
	13,
	377);
INSERT INTO V_TVL
	VALUES (398,
	383);
INSERT INTO V_PAR
	VALUES (398,
	0,
	386,
	'event_inst',
	0,
	7,
	61);
INSERT INTO V_VAL
	VALUES (399,
	1,
	0,
	9,
	1,
	4,
	0,
	0,
	0,
	0,
	11,
	377);
INSERT INTO V_IRF
	VALUES (399,
	384);
INSERT INTO V_VAL
	VALUES (390,
	1,
	0,
	9,
	6,
	16,
	0,
	0,
	0,
	0,
	5,
	377);
INSERT INTO V_AVL
	VALUES (390,
	399,
	23,
	32);
INSERT INTO V_VAL
	VALUES (400,
	0,
	0,
	9,
	20,
	23,
	0,
	0,
	0,
	0,
	11,
	377);
INSERT INTO V_IRF
	VALUES (400,
	384);
INSERT INTO V_VAL
	VALUES (401,
	0,
	0,
	9,
	25,
	35,
	0,
	0,
	0,
	0,
	5,
	377);
INSERT INTO V_AVL
	VALUES (401,
	400,
	23,
	32);
INSERT INTO V_VAL
	VALUES (389,
	0,
	0,
	-1,
	-1,
	-1,
	0,
	0,
	0,
	0,
	5,
	377);
INSERT INTO V_BIN
	VALUES (389,
	402,
	401,
	'+');
INSERT INTO V_VAL
	VALUES (402,
	0,
	0,
	9,
	39,
	39,
	0,
	0,
	0,
	0,
	5,
	377);
INSERT INTO V_LIN
	VALUES (402,
	'1');
INSERT INTO V_VAL
	VALUES (403,
	0,
	0,
	10,
	23,
	34,
	0,
	0,
	0,
	0,
	7,
	377);
INSERT INTO V_LST
	VALUES (403,
	'cycle count');
INSERT INTO V_PAR
	VALUES (403,
	388,
	0,
	'message',
	0,
	10,
	15);
INSERT INTO V_VAL
	VALUES (404,
	0,
	0,
	11,
	26,
	29,
	0,
	0,
	0,
	0,
	11,
	377);
INSERT INTO V_IRF
	VALUES (404,
	384);
INSERT INTO V_VAL
	VALUES (405,
	0,
	0,
	11,
	31,
	41,
	0,
	0,
	0,
	0,
	5,
	377);
INSERT INTO V_AVL
	VALUES (405,
	404,
	23,
	32);
INSERT INTO V_PAR
	VALUES (405,
	391,
	0,
	'message',
	0,
	11,
	18);
INSERT INTO V_VAR
	VALUES (392,
	377,
	'existed',
	1,
	4);
INSERT INTO V_TRN
	VALUES (392,
	0,
	'');
INSERT INTO V_LOC
	VALUES (406,
	2,
	1,
	7,
	392);
INSERT INTO V_VAR
	VALUES (384,
	377,
	'self',
	1,
	11);
INSERT INTO V_INT
	VALUES (384,
	0,
	23);
INSERT INTO V_LOC
	VALUES (407,
	2,
	44,
	47,
	384);
INSERT INTO V_LOC
	VALUES (408,
	5,
	54,
	57,
	384);
INSERT INTO V_LOC
	VALUES (409,
	6,
	1,
	4,
	384);
INSERT INTO V_LOC
	VALUES (410,
	6,
	52,
	55,
	384);
INSERT INTO V_LOC
	VALUES (411,
	9,
	1,
	4,
	384);
INSERT INTO V_LOC
	VALUES (412,
	9,
	20,
	23,
	384);
INSERT INTO V_LOC
	VALUES (413,
	11,
	26,
	29,
	384);
INSERT INTO V_VAR
	VALUES (383,
	377,
	'timeout',
	1,
	13);
INSERT INTO V_TRN
	VALUES (383,
	0,
	'');
INSERT INTO V_LOC
	VALUES (414,
	5,
	23,
	29,
	383);
INSERT INTO V_LOC
	VALUES (415,
	7,
	72,
	78,
	383);
INSERT INTO SM_STATE
	VALUES (416,
	362,
	0,
	'listening for diastolic pulse',
	3,
	0);
INSERT INTO SM_CH
	VALUES (416,
	366,
	362,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (416,
	366,
	362,
	0);
INSERT INTO SM_SEME
	VALUES (416,
	367,
	362,
	0);
INSERT INTO SM_SEME
	VALUES (416,
	368,
	362,
	0);
INSERT INTO SM_CH
	VALUES (416,
	364,
	362,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (416,
	364,
	362,
	0);
INSERT INTO SM_CH
	VALUES (416,
	369,
	362,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (416,
	369,
	362,
	0);
INSERT INTO SM_MOAH
	VALUES (417,
	362,
	416);
INSERT INTO SM_AH
	VALUES (417,
	362);
INSERT INTO SM_ACT
	VALUES (417,
	362,
	1,
	'// Cancel existing timeout timer
existed = TIM::timer_cancel(timer_inst_ref:self.timeout_timer);

// Set up the timeout timer
create event instance timeout of PACER3:''timeout'' to self;
self.timeout_timer=TIM::timer_start(microseconds:self.diastolic_timeout,
                                                            event_inst:timeout);
',
	'');
INSERT INTO ACT_SAB
	VALUES (418,
	362,
	417);
INSERT INTO ACT_ACT
	VALUES (418,
	'state',
	0,
	419,
	0,
	0,
	'pacer::listening for diastolic pulse',
	0);
INSERT INTO ACT_BLK
	VALUES (419,
	0,
	0,
	0,
	'TIM',
	'',
	'',
	6,
	1,
	6,
	20,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	418,
	0);
INSERT INTO ACT_SMT
	VALUES (420,
	419,
	421,
	2,
	1,
	'pacer::listening for diastolic pulse line: 2');
INSERT INTO ACT_AI
	VALUES (420,
	422,
	423,
	0,
	0);
INSERT INTO ACT_SMT
	VALUES (421,
	419,
	424,
	5,
	1,
	'pacer::listening for diastolic pulse line: 5');
INSERT INTO E_ESS
	VALUES (421,
	1,
	0,
	5,
	34,
	5,
	41,
	2,
	11,
	0,
	0,
	0,
	0);
INSERT INTO E_CES
	VALUES (421,
	1,
	425);
INSERT INTO E_CSME
	VALUES (421,
	368,
	362);
INSERT INTO E_CEI
	VALUES (421,
	426);
INSERT INTO ACT_SMT
	VALUES (424,
	419,
	0,
	6,
	1,
	'pacer::listening for diastolic pulse line: 6');
INSERT INTO ACT_AI
	VALUES (424,
	427,
	428,
	0,
	0);
INSERT INTO V_VAL
	VALUES (423,
	1,
	1,
	2,
	1,
	7,
	0,
	0,
	0,
	0,
	4,
	419);
INSERT INTO V_TVL
	VALUES (423,
	429);
INSERT INTO V_VAL
	VALUES (422,
	0,
	0,
	2,
	16,
	-1,
	2,
	29,
	0,
	0,
	4,
	419);
INSERT INTO V_BRV
	VALUES (422,
	307,
	1,
	2,
	11);
INSERT INTO V_VAL
	VALUES (430,
	0,
	0,
	2,
	44,
	47,
	0,
	0,
	0,
	0,
	11,
	419);
INSERT INTO V_IRF
	VALUES (430,
	426);
INSERT INTO V_VAL
	VALUES (431,
	0,
	0,
	2,
	49,
	61,
	0,
	0,
	0,
	0,
	18,
	419);
INSERT INTO V_AVL
	VALUES (431,
	430,
	23,
	34);
INSERT INTO V_PAR
	VALUES (431,
	0,
	422,
	'timer_inst_ref',
	0,
	2,
	29);
INSERT INTO V_VAL
	VALUES (432,
	1,
	0,
	6,
	1,
	4,
	0,
	0,
	0,
	0,
	11,
	419);
INSERT INTO V_IRF
	VALUES (432,
	426);
INSERT INTO V_VAL
	VALUES (428,
	1,
	0,
	6,
	6,
	18,
	0,
	0,
	0,
	0,
	18,
	419);
INSERT INTO V_AVL
	VALUES (428,
	432,
	23,
	34);
INSERT INTO V_VAL
	VALUES (427,
	0,
	0,
	6,
	25,
	-1,
	6,
	37,
	7,
	61,
	18,
	419);
INSERT INTO V_BRV
	VALUES (427,
	293,
	1,
	6,
	20);
INSERT INTO V_VAL
	VALUES (433,
	0,
	0,
	6,
	50,
	53,
	0,
	0,
	0,
	0,
	11,
	419);
INSERT INTO V_IRF
	VALUES (433,
	426);
INSERT INTO V_VAL
	VALUES (434,
	0,
	0,
	6,
	55,
	71,
	0,
	0,
	0,
	0,
	5,
	419);
INSERT INTO V_AVL
	VALUES (434,
	433,
	23,
	38);
INSERT INTO V_PAR
	VALUES (434,
	0,
	427,
	'microseconds',
	435,
	6,
	37);
INSERT INTO V_VAL
	VALUES (435,
	0,
	0,
	7,
	72,
	78,
	0,
	0,
	0,
	0,
	13,
	419);
INSERT INTO V_TVL
	VALUES (435,
	425);
INSERT INTO V_PAR
	VALUES (435,
	0,
	427,
	'event_inst',
	0,
	7,
	61);
INSERT INTO V_VAR
	VALUES (429,
	419,
	'existed',
	1,
	4);
INSERT INTO V_TRN
	VALUES (429,
	0,
	'');
INSERT INTO V_LOC
	VALUES (436,
	2,
	1,
	7,
	429);
INSERT INTO V_VAR
	VALUES (426,
	419,
	'self',
	1,
	11);
INSERT INTO V_INT
	VALUES (426,
	0,
	23);
INSERT INTO V_LOC
	VALUES (437,
	2,
	44,
	47,
	426);
INSERT INTO V_LOC
	VALUES (438,
	5,
	54,
	57,
	426);
INSERT INTO V_LOC
	VALUES (439,
	6,
	1,
	4,
	426);
INSERT INTO V_LOC
	VALUES (440,
	6,
	50,
	53,
	426);
INSERT INTO V_VAR
	VALUES (425,
	419,
	'timeout',
	1,
	13);
INSERT INTO V_TRN
	VALUES (425,
	0,
	'');
INSERT INTO V_LOC
	VALUES (441,
	5,
	23,
	29,
	425);
INSERT INTO V_LOC
	VALUES (442,
	7,
	72,
	78,
	425);
INSERT INTO SM_STATE
	VALUES (443,
	362,
	0,
	'Increasing rate',
	4,
	0);
INSERT INTO SM_CH
	VALUES (443,
	366,
	362,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (443,
	366,
	362,
	0);
INSERT INTO SM_CH
	VALUES (443,
	367,
	362,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (443,
	367,
	362,
	0);
INSERT INTO SM_CH
	VALUES (443,
	368,
	362,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (443,
	368,
	362,
	0);
INSERT INTO SM_CH
	VALUES (443,
	364,
	362,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (443,
	364,
	362,
	0);
INSERT INTO SM_SEME
	VALUES (443,
	369,
	362,
	0);
INSERT INTO SM_MOAH
	VALUES (444,
	362,
	443);
INSERT INTO SM_AH
	VALUES (444,
	362);
INSERT INTO SM_ACT
	VALUES (444,
	362,
	1,
	'// calculate an increased rate based on the blood temperature
// and the current respiratory rate
self.systolic_timeout = self.systolic_timeout + 1;
self.diastolic_timeout = self.diastolic_timeout + 1;
generate PACER5 to self;',
	'');
INSERT INTO ACT_SAB
	VALUES (445,
	362,
	444);
INSERT INTO ACT_ACT
	VALUES (445,
	'state',
	0,
	446,
	0,
	0,
	'pacer::Increasing rate',
	0);
INSERT INTO ACT_BLK
	VALUES (446,
	0,
	0,
	0,
	'V_VAR.Var_ID',
	'',
	'',
	5,
	1,
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
	445,
	0);
INSERT INTO ACT_SMT
	VALUES (447,
	446,
	448,
	3,
	1,
	'pacer::Increasing rate line: 3');
INSERT INTO ACT_AI
	VALUES (447,
	449,
	450,
	0,
	0);
INSERT INTO ACT_SMT
	VALUES (448,
	446,
	451,
	4,
	1,
	'pacer::Increasing rate line: 4');
INSERT INTO ACT_AI
	VALUES (448,
	452,
	453,
	0,
	0);
INSERT INTO ACT_SMT
	VALUES (451,
	446,
	0,
	5,
	1,
	'pacer::Increasing rate line: 5');
INSERT INTO E_ESS
	VALUES (451,
	1,
	0,
	5,
	10,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO E_GES
	VALUES (451);
INSERT INTO E_GSME
	VALUES (451,
	369,
	362);
INSERT INTO E_GEN
	VALUES (451,
	454);
INSERT INTO V_VAL
	VALUES (455,
	1,
	0,
	3,
	1,
	4,
	0,
	0,
	0,
	0,
	11,
	446);
INSERT INTO V_IRF
	VALUES (455,
	454);
INSERT INTO V_VAL
	VALUES (450,
	1,
	0,
	3,
	6,
	21,
	0,
	0,
	0,
	0,
	5,
	446);
INSERT INTO V_AVL
	VALUES (450,
	455,
	23,
	36);
INSERT INTO V_VAL
	VALUES (456,
	0,
	0,
	3,
	25,
	28,
	0,
	0,
	0,
	0,
	11,
	446);
INSERT INTO V_IRF
	VALUES (456,
	454);
INSERT INTO V_VAL
	VALUES (457,
	0,
	0,
	3,
	30,
	45,
	0,
	0,
	0,
	0,
	5,
	446);
INSERT INTO V_AVL
	VALUES (457,
	456,
	23,
	36);
INSERT INTO V_VAL
	VALUES (449,
	0,
	0,
	-1,
	-1,
	-1,
	0,
	0,
	0,
	0,
	5,
	446);
INSERT INTO V_BIN
	VALUES (449,
	458,
	457,
	'+');
INSERT INTO V_VAL
	VALUES (458,
	0,
	0,
	3,
	49,
	49,
	0,
	0,
	0,
	0,
	5,
	446);
INSERT INTO V_LIN
	VALUES (458,
	'1');
INSERT INTO V_VAL
	VALUES (459,
	1,
	0,
	4,
	1,
	4,
	0,
	0,
	0,
	0,
	11,
	446);
INSERT INTO V_IRF
	VALUES (459,
	454);
INSERT INTO V_VAL
	VALUES (453,
	1,
	0,
	4,
	6,
	22,
	0,
	0,
	0,
	0,
	5,
	446);
INSERT INTO V_AVL
	VALUES (453,
	459,
	23,
	38);
INSERT INTO V_VAL
	VALUES (460,
	0,
	0,
	4,
	26,
	29,
	0,
	0,
	0,
	0,
	11,
	446);
INSERT INTO V_IRF
	VALUES (460,
	454);
INSERT INTO V_VAL
	VALUES (461,
	0,
	0,
	4,
	31,
	47,
	0,
	0,
	0,
	0,
	5,
	446);
INSERT INTO V_AVL
	VALUES (461,
	460,
	23,
	38);
INSERT INTO V_VAL
	VALUES (452,
	0,
	0,
	-1,
	-1,
	-1,
	0,
	0,
	0,
	0,
	5,
	446);
INSERT INTO V_BIN
	VALUES (452,
	462,
	461,
	'+');
INSERT INTO V_VAL
	VALUES (462,
	0,
	0,
	4,
	51,
	51,
	0,
	0,
	0,
	0,
	5,
	446);
INSERT INTO V_LIN
	VALUES (462,
	'1');
INSERT INTO V_VAR
	VALUES (454,
	446,
	'self',
	1,
	11);
INSERT INTO V_INT
	VALUES (454,
	0,
	23);
INSERT INTO V_LOC
	VALUES (463,
	3,
	1,
	4,
	454);
INSERT INTO V_LOC
	VALUES (464,
	3,
	25,
	28,
	454);
INSERT INTO V_LOC
	VALUES (465,
	4,
	1,
	4,
	454);
INSERT INTO V_LOC
	VALUES (466,
	4,
	26,
	29,
	454);
INSERT INTO V_LOC
	VALUES (467,
	5,
	20,
	23,
	454);
INSERT INTO SM_NSTXN
	VALUES (468,
	362,
	374,
	366,
	0);
INSERT INTO SM_TAH
	VALUES (469,
	362,
	468);
INSERT INTO SM_AH
	VALUES (469,
	362);
INSERT INTO SM_ACT
	VALUES (469,
	362,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (470,
	362,
	469);
INSERT INTO ACT_ACT
	VALUES (470,
	'transition',
	0,
	471,
	0,
	0,
	'PACER1: systolic_pulse in listening for systolic pulse to listening for diastolic pulse',
	0);
INSERT INTO ACT_BLK
	VALUES (471,
	0,
	0,
	0,
	'',
	'',
	'',
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
	470,
	0);
INSERT INTO SM_TXN
	VALUES (468,
	362,
	416,
	0);
INSERT INTO SM_NSTXN
	VALUES (472,
	362,
	416,
	367,
	0);
INSERT INTO SM_TAH
	VALUES (473,
	362,
	472);
INSERT INTO SM_AH
	VALUES (473,
	362);
INSERT INTO SM_ACT
	VALUES (473,
	362,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (474,
	362,
	473);
INSERT INTO ACT_ACT
	VALUES (474,
	'transition',
	0,
	475,
	0,
	0,
	'PACER2: diastolic_pulse in listening for diastolic pulse to listening for systolic pulse',
	0);
INSERT INTO ACT_BLK
	VALUES (475,
	0,
	0,
	0,
	'',
	'',
	'',
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
	474,
	0);
INSERT INTO SM_TXN
	VALUES (472,
	362,
	374,
	0);
INSERT INTO SM_NSTXN
	VALUES (476,
	362,
	370,
	367,
	0);
INSERT INTO SM_TAH
	VALUES (477,
	362,
	476);
INSERT INTO SM_AH
	VALUES (477,
	362);
INSERT INTO SM_ACT
	VALUES (477,
	362,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (478,
	362,
	477);
INSERT INTO ACT_ACT
	VALUES (478,
	'transition',
	0,
	479,
	0,
	0,
	'PACER2: diastolic_pulse in syncing to listening for systolic pulse',
	0);
INSERT INTO ACT_BLK
	VALUES (479,
	0,
	0,
	0,
	'',
	'',
	'',
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
	478,
	0);
INSERT INTO SM_TXN
	VALUES (476,
	362,
	374,
	0);
INSERT INTO SM_NSTXN
	VALUES (480,
	362,
	370,
	366,
	0);
INSERT INTO SM_TAH
	VALUES (481,
	362,
	480);
INSERT INTO SM_AH
	VALUES (481,
	362);
INSERT INTO SM_ACT
	VALUES (481,
	362,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (482,
	362,
	481);
INSERT INTO ACT_ACT
	VALUES (482,
	'transition',
	0,
	483,
	0,
	0,
	'PACER1: systolic_pulse in syncing to listening for diastolic pulse',
	0);
INSERT INTO ACT_BLK
	VALUES (483,
	0,
	0,
	0,
	'',
	'',
	'',
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
	482,
	0);
INSERT INTO SM_TXN
	VALUES (480,
	362,
	416,
	0);
INSERT INTO SM_NSTXN
	VALUES (484,
	362,
	416,
	368,
	0);
INSERT INTO SM_TAH
	VALUES (485,
	362,
	484);
INSERT INTO SM_AH
	VALUES (485,
	362);
INSERT INTO SM_ACT
	VALUES (485,
	362,
	1,
	'send to_heart::diastolic_pace();',
	'');
INSERT INTO ACT_TAB
	VALUES (486,
	362,
	485);
INSERT INTO ACT_ACT
	VALUES (486,
	'transition',
	0,
	487,
	0,
	0,
	'PACER3: timeout in listening for diastolic pulse to syncing',
	0);
INSERT INTO ACT_BLK
	VALUES (487,
	0,
	0,
	0,
	'to_heart',
	'',
	'',
	1,
	1,
	1,
	6,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	486,
	0);
INSERT INTO ACT_SMT
	VALUES (488,
	487,
	0,
	1,
	1,
	'PACER3: timeout in listening for diastolic pulse to syncing line: 1');
INSERT INTO ACT_SGN
	VALUES (488,
	1,
	16,
	1,
	6,
	489,
	0,
	0);
INSERT INTO SM_TXN
	VALUES (484,
	362,
	370,
	0);
INSERT INTO SM_NSTXN
	VALUES (490,
	362,
	374,
	368,
	0);
INSERT INTO SM_TAH
	VALUES (491,
	362,
	490);
INSERT INTO SM_AH
	VALUES (491,
	362);
INSERT INTO SM_ACT
	VALUES (491,
	362,
	1,
	'send to_heart::systolic_pace();',
	'');
INSERT INTO ACT_TAB
	VALUES (492,
	362,
	491);
INSERT INTO ACT_ACT
	VALUES (492,
	'transition',
	0,
	493,
	0,
	0,
	'PACER3: timeout in listening for systolic pulse to syncing',
	0);
INSERT INTO ACT_BLK
	VALUES (493,
	0,
	0,
	0,
	'to_heart',
	'',
	'',
	1,
	1,
	1,
	6,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	492,
	0);
INSERT INTO ACT_SMT
	VALUES (494,
	493,
	0,
	1,
	1,
	'PACER3: timeout in listening for systolic pulse to syncing line: 1');
INSERT INTO ACT_SGN
	VALUES (494,
	1,
	16,
	1,
	6,
	495,
	0,
	0);
INSERT INTO SM_TXN
	VALUES (490,
	362,
	370,
	0);
INSERT INTO SM_NSTXN
	VALUES (496,
	362,
	443,
	369,
	0);
INSERT INTO SM_TAH
	VALUES (497,
	362,
	496);
INSERT INTO SM_AH
	VALUES (497,
	362);
INSERT INTO SM_ACT
	VALUES (497,
	362,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (498,
	362,
	497);
INSERT INTO ACT_ACT
	VALUES (498,
	'transition',
	0,
	499,
	0,
	0,
	'PACER5: rate_increased in Increasing rate to syncing',
	0);
INSERT INTO ACT_BLK
	VALUES (499,
	0,
	0,
	0,
	'',
	'',
	'',
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
	498,
	0);
INSERT INTO SM_TXN
	VALUES (496,
	362,
	370,
	0);
INSERT INTO SM_NSTXN
	VALUES (500,
	362,
	370,
	364,
	0);
INSERT INTO SM_TAH
	VALUES (501,
	362,
	500);
INSERT INTO SM_AH
	VALUES (501,
	362);
INSERT INTO SM_ACT
	VALUES (501,
	362,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (502,
	362,
	501);
INSERT INTO ACT_ACT
	VALUES (502,
	'transition',
	0,
	503,
	0,
	0,
	'PACER4: increased_activity in syncing to Increasing rate',
	0);
INSERT INTO ACT_BLK
	VALUES (503,
	0,
	0,
	0,
	'',
	'',
	'',
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
	502,
	0);
INSERT INTO SM_TXN
	VALUES (500,
	362,
	443,
	0);
INSERT INTO SM_ASM
	VALUES (504,
	23);
INSERT INTO SM_SM
	VALUES (504,
	'',
	0);
INSERT INTO SM_MOORE
	VALUES (504);
INSERT INTO SM_SGEVT
	VALUES (505,
	504,
	0,
	506,
	0);
INSERT INTO SM_SEVT
	VALUES (505,
	504,
	0);
INSERT INTO SM_EVT
	VALUES (505,
	504,
	0,
	3,
	'diastolic_pulse',
	0,
	'',
	'PACER_A3',
	'');
INSERT INTO SM_SGEVT
	VALUES (507,
	504,
	0,
	508,
	0);
INSERT INTO SM_SEVT
	VALUES (507,
	504,
	0);
INSERT INTO SM_EVT
	VALUES (507,
	504,
	0,
	4,
	'systolic_pulse',
	0,
	'',
	'PACER_A4',
	'');
INSERT INTO SM_SGEVT
	VALUES (509,
	504,
	0,
	510,
	0);
INSERT INTO SM_SEVT
	VALUES (509,
	504,
	0);
INSERT INTO SM_EVT
	VALUES (509,
	504,
	0,
	5,
	'increased_activty',
	0,
	'',
	'PACER_A5',
	'');
INSERT INTO SM_STATE
	VALUES (511,
	504,
	0,
	'monitoring host',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (511,
	505,
	504,
	0);
INSERT INTO SM_SEME
	VALUES (511,
	507,
	504,
	0);
INSERT INTO SM_SEME
	VALUES (511,
	509,
	504,
	0);
INSERT INTO SM_MOAH
	VALUES (512,
	504,
	511);
INSERT INTO SM_AH
	VALUES (512,
	504);
INSERT INTO SM_ACT
	VALUES (512,
	504,
	1,
	'',
	'');
INSERT INTO ACT_SAB
	VALUES (513,
	504,
	512);
INSERT INTO ACT_ACT
	VALUES (513,
	'class state',
	0,
	514,
	0,
	0,
	'pacer::monitoring host',
	0);
INSERT INTO ACT_BLK
	VALUES (514,
	0,
	0,
	0,
	'',
	'',
	'',
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
	513,
	0);
INSERT INTO SM_NSTXN
	VALUES (515,
	504,
	511,
	505,
	0);
INSERT INTO SM_TAH
	VALUES (516,
	504,
	515);
INSERT INTO SM_AH
	VALUES (516,
	504);
INSERT INTO SM_ACT
	VALUES (516,
	504,
	1,
	'// Redirect signal from heart to become event to pacer.
select any pacer from instances of PACER;
generate PACER2:diastolic_pulse() to pacer;
',
	'');
INSERT INTO ACT_TAB
	VALUES (517,
	504,
	516);
INSERT INTO ACT_ACT
	VALUES (517,
	'class transition',
	0,
	518,
	0,
	0,
	'to_heart::diastolic_pulse in monitoring host to monitoring host',
	0);
INSERT INTO ACT_BLK
	VALUES (518,
	1,
	0,
	0,
	'V_VAR.Var_ID',
	'',
	'',
	3,
	1,
	2,
	36,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	517,
	0);
INSERT INTO ACT_SMT
	VALUES (519,
	518,
	520,
	2,
	1,
	'to_heart::diastolic_pulse in monitoring host to monitoring host line: 2');
INSERT INTO ACT_FIO
	VALUES (519,
	521,
	1,
	'any',
	23,
	2,
	36);
INSERT INTO ACT_SMT
	VALUES (520,
	518,
	0,
	3,
	1,
	'to_heart::diastolic_pulse in monitoring host to monitoring host line: 3');
INSERT INTO E_ESS
	VALUES (520,
	1,
	0,
	3,
	10,
	3,
	17,
	2,
	36,
	0,
	0,
	0,
	0);
INSERT INTO E_GES
	VALUES (520);
INSERT INTO E_GSME
	VALUES (520,
	367,
	362);
INSERT INTO E_GEN
	VALUES (520,
	521);
INSERT INTO V_VAR
	VALUES (521,
	518,
	'pacer',
	1,
	11);
INSERT INTO V_INT
	VALUES (521,
	0,
	23);
INSERT INTO V_LOC
	VALUES (522,
	2,
	12,
	16,
	521);
INSERT INTO V_LOC
	VALUES (523,
	3,
	38,
	42,
	521);
INSERT INTO SM_TXN
	VALUES (515,
	504,
	511,
	0);
INSERT INTO SM_NSTXN
	VALUES (524,
	504,
	511,
	507,
	0);
INSERT INTO SM_TAH
	VALUES (525,
	504,
	524);
INSERT INTO SM_AH
	VALUES (525,
	504);
INSERT INTO SM_ACT
	VALUES (525,
	504,
	1,
	'// Redirect signal from heart to become event to pacer.
select any pacer from instances of PACER;
generate PACER1:systolic_pulse() to pacer;
',
	'');
INSERT INTO ACT_TAB
	VALUES (526,
	504,
	525);
INSERT INTO ACT_ACT
	VALUES (526,
	'class transition',
	0,
	527,
	0,
	0,
	'to_heart::systolic_pulse in monitoring host to monitoring host',
	0);
INSERT INTO ACT_BLK
	VALUES (527,
	1,
	0,
	0,
	'V_VAR.Var_ID',
	'',
	'',
	3,
	1,
	2,
	36,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	526,
	0);
INSERT INTO ACT_SMT
	VALUES (528,
	527,
	529,
	2,
	1,
	'to_heart::systolic_pulse in monitoring host to monitoring host line: 2');
INSERT INTO ACT_FIO
	VALUES (528,
	530,
	1,
	'any',
	23,
	2,
	36);
INSERT INTO ACT_SMT
	VALUES (529,
	527,
	0,
	3,
	1,
	'to_heart::systolic_pulse in monitoring host to monitoring host line: 3');
INSERT INTO E_ESS
	VALUES (529,
	1,
	0,
	3,
	10,
	3,
	17,
	2,
	36,
	0,
	0,
	0,
	0);
INSERT INTO E_GES
	VALUES (529);
INSERT INTO E_GSME
	VALUES (529,
	366,
	362);
INSERT INTO E_GEN
	VALUES (529,
	530);
INSERT INTO V_VAR
	VALUES (530,
	527,
	'pacer',
	1,
	11);
INSERT INTO V_INT
	VALUES (530,
	0,
	23);
INSERT INTO V_LOC
	VALUES (531,
	2,
	12,
	16,
	530);
INSERT INTO V_LOC
	VALUES (532,
	3,
	37,
	41,
	530);
INSERT INTO SM_TXN
	VALUES (524,
	504,
	511,
	0);
INSERT INTO SM_NSTXN
	VALUES (533,
	504,
	511,
	509,
	0);
INSERT INTO SM_TAH
	VALUES (534,
	504,
	533);
INSERT INTO SM_AH
	VALUES (534,
	504);
INSERT INTO SM_ACT
	VALUES (534,
	504,
	1,
	'// Redirect signal from monitor to become event to pacer.
select any pacer from instances of PACER;
generate PACER5:rate_increased() to pacer;
',
	'');
INSERT INTO ACT_TAB
	VALUES (535,
	504,
	534);
INSERT INTO ACT_ACT
	VALUES (535,
	'class transition',
	0,
	536,
	0,
	0,
	'monitor::increased_activty in monitoring host to monitoring host',
	0);
INSERT INTO ACT_BLK
	VALUES (536,
	1,
	0,
	0,
	'V_VAR.Var_ID',
	'',
	'',
	3,
	1,
	2,
	36,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	535,
	0);
INSERT INTO ACT_SMT
	VALUES (537,
	536,
	538,
	2,
	1,
	'monitor::increased_activty in monitoring host to monitoring host line: 2');
INSERT INTO ACT_FIO
	VALUES (537,
	539,
	1,
	'any',
	23,
	2,
	36);
INSERT INTO ACT_SMT
	VALUES (538,
	536,
	0,
	3,
	1,
	'monitor::increased_activty in monitoring host to monitoring host line: 3');
INSERT INTO E_ESS
	VALUES (538,
	1,
	0,
	3,
	10,
	3,
	17,
	2,
	36,
	0,
	0,
	0,
	0);
INSERT INTO E_GES
	VALUES (538);
INSERT INTO E_GSME
	VALUES (538,
	369,
	362);
INSERT INTO E_GEN
	VALUES (538,
	539);
INSERT INTO V_VAR
	VALUES (539,
	536,
	'pacer',
	1,
	11);
INSERT INTO V_INT
	VALUES (539,
	0,
	23);
INSERT INTO V_LOC
	VALUES (540,
	2,
	12,
	16,
	539);
INSERT INTO V_LOC
	VALUES (541,
	3,
	37,
	41,
	539);
INSERT INTO SM_TXN
	VALUES (533,
	504,
	511,
	0);
INSERT INTO PE_PE
	VALUES (542,
	1,
	0,
	358,
	7);
INSERT INTO EP_PKG
	VALUES (542,
	0,
	1,
	'External Entities',
	'',
	0);
INSERT INTO PE_PE
	VALUES (543,
	1,
	542,
	0,
	5);
INSERT INTO S_EE
	VALUES (543,
	'Logging',
	'',
	'LOG',
	0);
INSERT INTO S_BRG
	VALUES (544,
	543,
	'LogInteger',
	'',
	0,
	3,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (545,
	544,
	'message',
	5,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (546,
	543,
	'LogReal',
	'',
	0,
	3,
	'',
	1,
	'');
INSERT INTO S_BPARM
	VALUES (547,
	546,
	'message',
	7,
	0,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (548,
	546,
	'r',
	6,
	0,
	'',
	547,
	'');
INSERT INTO ACT_BRB
	VALUES (549,
	546);
INSERT INTO ACT_ACT
	VALUES (549,
	'bridge',
	0,
	0,
	0,
	0,
	'Logging::LogReal',
	0);
INSERT INTO S_BRG
	VALUES (550,
	543,
	'LogTime',
	'',
	0,
	3,
	'',
	1,
	'');
INSERT INTO S_BPARM
	VALUES (551,
	550,
	'message',
	7,
	0,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (552,
	550,
	't',
	19,
	0,
	'',
	551,
	'');
INSERT INTO ACT_BRB
	VALUES (553,
	550);
INSERT INTO ACT_ACT
	VALUES (553,
	'bridge',
	0,
	0,
	0,
	0,
	'Logging::LogTime',
	0);
INSERT INTO S_BRG
	VALUES (554,
	543,
	'LogDate',
	'',
	0,
	3,
	'',
	1,
	'');
INSERT INTO S_BPARM
	VALUES (555,
	554,
	'message',
	7,
	0,
	'',
	556,
	'');
INSERT INTO S_BPARM
	VALUES (556,
	554,
	'd',
	17,
	0,
	'',
	0,
	'');
INSERT INTO ACT_BRB
	VALUES (557,
	554);
INSERT INTO ACT_ACT
	VALUES (557,
	'bridge',
	0,
	0,
	0,
	0,
	'Logging::LogDate',
	0);
INSERT INTO S_BRG
	VALUES (558,
	543,
	'LogInfo',
	'',
	0,
	3,
	'',
	1,
	'');
INSERT INTO S_BPARM
	VALUES (559,
	558,
	'message',
	7,
	0,
	'',
	0,
	'');
INSERT INTO ACT_BRB
	VALUES (560,
	558);
INSERT INTO ACT_ACT
	VALUES (560,
	'bridge',
	0,
	0,
	0,
	0,
	'Logging::LogInfo',
	0);
INSERT INTO S_BRG
	VALUES (561,
	543,
	'LogFailure',
	'',
	0,
	3,
	'',
	1,
	'');
INSERT INTO S_BPARM
	VALUES (562,
	561,
	'message',
	7,
	0,
	'',
	0,
	'');
INSERT INTO ACT_BRB
	VALUES (563,
	561);
INSERT INTO ACT_ACT
	VALUES (563,
	'bridge',
	0,
	0,
	0,
	0,
	'Logging::LogFailure',
	0);
INSERT INTO S_BRG
	VALUES (564,
	543,
	'LogSuccess',
	'',
	0,
	3,
	'',
	1,
	'');
INSERT INTO S_BPARM
	VALUES (565,
	564,
	'message',
	7,
	0,
	'',
	0,
	'');
INSERT INTO ACT_BRB
	VALUES (566,
	564);
INSERT INTO ACT_ACT
	VALUES (566,
	'bridge',
	0,
	0,
	0,
	0,
	'Logging::LogSuccess',
	0);
INSERT INTO PE_PE
	VALUES (567,
	1,
	542,
	0,
	5);
INSERT INTO S_EE
	VALUES (567,
	'Architecture',
	'',
	'ARCH',
	0);
INSERT INTO S_BRG
	VALUES (568,
	567,
	'shutdown',
	'',
	0,
	3,
	'control stop;',
	1,
	'');
INSERT INTO ACT_BRB
	VALUES (569,
	568);
INSERT INTO ACT_ACT
	VALUES (569,
	'bridge',
	0,
	0,
	0,
	0,
	'Architecture::shutdown',
	0);
INSERT INTO PE_PE
	VALUES (570,
	1,
	542,
	0,
	5);
INSERT INTO S_EE
	VALUES (570,
	'Time',
	'The Time external entity provides date, timestamp, and timer related operations.',
	'TIM',
	0);
INSERT INTO S_BRG
	VALUES (571,
	570,
	'timer_cancel',
	'This bridge operation cancels and deletes the passed timer instance. If the 
timer exists (that is, it had not expired), a TRUE value is returned. If the
timer no longer exists, a FALSE value is returned.',
	1,
	4,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (572,
	571,
	'timer_inst_ref',
	18,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (573,
	570,
	'timer_add_time',
	'This bridge operation attempts to add the specified number of microseconds to a
passed existing timer. If the timer exists (that is, it has not expired), a TRUE
value is returned. If the timer no longer exists, a FALSE value is returned.',
	1,
	4,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (574,
	573,
	'microseconds',
	5,
	0,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (575,
	573,
	'timer_inst_ref',
	18,
	0,
	'',
	574,
	'');
INSERT INTO S_BRG
	VALUES (576,
	570,
	'timer_reset_time',
	'This bridge operation attempts to set the passed existing timer to expire in
the specified number of microseconds. If the timer exists (that is, it has not
expired), a TRUE value is returned. If the timer no longer exists, a FALSE value
is returned.',
	1,
	4,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (577,
	576,
	'microseconds',
	5,
	0,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (578,
	576,
	'timer_inst_ref',
	18,
	0,
	'',
	577,
	'');
INSERT INTO S_BRG
	VALUES (579,
	570,
	'timer_remaining_time',
	'Returns the time remaining (in microseconds) for the passed timer instance. If
the timer has expired, a zero value is returned.',
	1,
	5,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (580,
	579,
	'timer_inst_ref',
	18,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (581,
	570,
	'timer_start_recurring',
	'This bridge operation starts a timer set to expire in the specified number of
microseconds, generating the passed event upon expiration. Upon expiration, the
timer will be restarted and fire again in the specified number of microseconds
generating the passed event. This bridge operation returns the instance handle
of the timer.',
	1,
	18,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (582,
	581,
	'event_inst',
	13,
	0,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (583,
	581,
	'microseconds',
	5,
	0,
	'',
	582,
	'');
INSERT INTO S_BRG
	VALUES (584,
	570,
	'timer_start',
	'This bridge operation starts a timer set to expire in the specified number of
microseconds, generating the passed event upon expiration. Returns the instance
handle of the timer.',
	1,
	18,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (585,
	584,
	'event_inst',
	13,
	0,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (586,
	584,
	'microseconds',
	5,
	0,
	'',
	585,
	'');
INSERT INTO S_BRG
	VALUES (587,
	570,
	'current_clock',
	'',
	1,
	19,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (588,
	570,
	'get_year',
	'',
	1,
	5,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (589,
	588,
	'date',
	17,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (590,
	570,
	'get_month',
	'',
	1,
	5,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (591,
	590,
	'date',
	17,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (592,
	570,
	'get_day',
	'',
	1,
	5,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (593,
	592,
	'date',
	17,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (594,
	570,
	'get_hour',
	'',
	1,
	5,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (595,
	594,
	'date',
	17,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (596,
	570,
	'get_minute',
	'',
	1,
	5,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (597,
	596,
	'date',
	17,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (598,
	570,
	'get_second',
	'',
	1,
	5,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (599,
	598,
	'date',
	17,
	0,
	'',
	0,
	'');
INSERT INTO S_BRG
	VALUES (600,
	570,
	'create_date',
	'',
	1,
	17,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (601,
	600,
	'year',
	5,
	0,
	'',
	602,
	'');
INSERT INTO S_BPARM
	VALUES (603,
	600,
	'month',
	5,
	0,
	'',
	604,
	'');
INSERT INTO S_BPARM
	VALUES (605,
	600,
	'day',
	5,
	0,
	'',
	0,
	'');
INSERT INTO S_BPARM
	VALUES (606,
	600,
	'hour',
	5,
	0,
	'',
	605,
	'');
INSERT INTO S_BPARM
	VALUES (604,
	600,
	'minute',
	5,
	0,
	'',
	606,
	'');
INSERT INTO S_BPARM
	VALUES (602,
	600,
	'second',
	5,
	0,
	'',
	603,
	'');
INSERT INTO S_BRG
	VALUES (607,
	570,
	'current_date',
	'',
	1,
	17,
	'',
	0,
	'');
INSERT INTO CL_IIR
	VALUES (608,
	609,
	357,
	0,
	'monitor',
	'');
INSERT INTO CL_IP
	VALUES (608,
	'monitor',
	'');
INSERT INTO CL_IPINS
	VALUES (610,
	608);
INSERT INTO PE_PE
	VALUES (611,
	-1,
	0,
	336,
	-1);
INSERT INTO CL_IC
	VALUES (611,
	612,
	0,
	0,
	0,
	'',
	'avpace::library::hostmonitor',
	'');
INSERT INTO CL_IIR
	VALUES (613,
	614,
	611,
	0,
	'monitor',
	'');
INSERT INTO CL_IR
	VALUES (613,
	610,
	'monitor',
	'');
INSERT INTO PE_PE
	VALUES (615,
	1,
	0,
	612,
	7);
INSERT INTO EP_PKG
	VALUES (615,
	0,
	1,
	'hostmonitor',
	'',
	0);
INSERT INTO PE_PE
	VALUES (51,
	1,
	615,
	0,
	4);
INSERT INTO O_OBJ
	VALUES (51,
	'Host Monitor',
	2,
	'HM',
	'',
	0);
INSERT INTO O_NBATTR
	VALUES (55,
	51);
INSERT INTO O_BATTR
	VALUES (55,
	51);
INSERT INTO O_ATTR
	VALUES (55,
	51,
	0,
	'lastTemp',
	'',
	'',
	'lastTemp',
	0,
	5,
	'',
	'');
INSERT INTO O_NBATTR
	VALUES (57,
	51);
INSERT INTO O_BATTR
	VALUES (57,
	51);
INSERT INTO O_ATTR
	VALUES (57,
	51,
	55,
	'lastRate',
	'',
	'',
	'lastRate',
	0,
	5,
	'',
	'');
INSERT INTO O_NBATTR
	VALUES (59,
	51);
INSERT INTO O_BATTR
	VALUES (59,
	51);
INSERT INTO O_ATTR
	VALUES (59,
	51,
	57,
	'current_state',
	'',
	'',
	'current_state',
	0,
	9,
	'',
	'');
INSERT INTO O_ID
	VALUES (0,
	51);
INSERT INTO O_ID
	VALUES (1,
	51);
INSERT INTO O_ID
	VALUES (2,
	51);
INSERT INTO SM_ISM
	VALUES (616,
	51);
INSERT INTO SM_SM
	VALUES (616,
	'',
	0);
INSERT INTO SM_MOORE
	VALUES (616);
INSERT INTO SM_LEVT
	VALUES (617,
	616,
	0);
INSERT INTO SM_SEVT
	VALUES (617,
	616,
	0);
INSERT INTO SM_EVT
	VALUES (617,
	616,
	0,
	1,
	'poll',
	0,
	'',
	'HM1',
	'');
INSERT INTO SM_LEVT
	VALUES (618,
	616,
	0);
INSERT INTO SM_SEVT
	VALUES (618,
	616,
	0);
INSERT INTO SM_EVT
	VALUES (618,
	616,
	0,
	2,
	'increasedActivity',
	0,
	'',
	'HM2',
	'');
INSERT INTO SM_STATE
	VALUES (619,
	616,
	0,
	'Polling monitors',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (619,
	617,
	616,
	0);
INSERT INTO SM_SEME
	VALUES (619,
	618,
	616,
	0);
INSERT INTO SM_MOAH
	VALUES (620,
	616,
	619);
INSERT INTO SM_AH
	VALUES (620,
	616);
INSERT INTO SM_ACT
	VALUES (620,
	616,
	1,
	'select one tm related by self->TM[R2];
self.lastTemp = tm.getCurrentTemp();
select one rm related by self->RM[R3];
self.lastRate = rm.getCurrentRate();
if(self.lastTemp > 37.5 or self.lastRate > 18)
  generate HM2 to self;
end if;
generate HM1 to self;',
	'');
INSERT INTO ACT_SAB
	VALUES (621,
	616,
	620);
INSERT INTO ACT_ACT
	VALUES (621,
	'state',
	0,
	0,
	0,
	0,
	'Host Monitor::Polling monitors',
	0);
INSERT INTO SM_STATE
	VALUES (622,
	616,
	0,
	'Notifying of increased activity',
	2,
	0);
INSERT INTO SM_SEME
	VALUES (622,
	617,
	616,
	0);
INSERT INTO SM_CH
	VALUES (622,
	618,
	616,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (622,
	618,
	616,
	0);
INSERT INTO SM_MOAH
	VALUES (623,
	616,
	622);
INSERT INTO SM_AH
	VALUES (623,
	616);
INSERT INTO SM_ACT
	VALUES (623,
	616,
	1,
	'send monitor::increased_activty(current_temp:self.lastTemp,
                                                    current_rate:self.lastRate);
generate HM1 to self;',
	'');
INSERT INTO ACT_SAB
	VALUES (624,
	616,
	623);
INSERT INTO ACT_ACT
	VALUES (624,
	'state',
	0,
	0,
	0,
	0,
	'Host Monitor::Notifying of increased activity',
	0);
INSERT INTO SM_NSTXN
	VALUES (625,
	616,
	619,
	617,
	0);
INSERT INTO SM_TAH
	VALUES (626,
	616,
	625);
INSERT INTO SM_AH
	VALUES (626,
	616);
INSERT INTO SM_ACT
	VALUES (626,
	616,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (627,
	616,
	626);
INSERT INTO ACT_ACT
	VALUES (627,
	'transition',
	0,
	0,
	0,
	0,
	'HM1: poll in Polling monitors to Polling monitors',
	0);
INSERT INTO SM_TXN
	VALUES (625,
	616,
	619,
	0);
INSERT INTO SM_NSTXN
	VALUES (628,
	616,
	619,
	618,
	0);
INSERT INTO SM_TAH
	VALUES (629,
	616,
	628);
INSERT INTO SM_AH
	VALUES (629,
	616);
INSERT INTO SM_ACT
	VALUES (629,
	616,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (630,
	616,
	629);
INSERT INTO ACT_ACT
	VALUES (630,
	'transition',
	0,
	0,
	0,
	0,
	'HM2: increasedActivity in Polling monitors to Notifying of increased activity',
	0);
INSERT INTO SM_TXN
	VALUES (628,
	616,
	622,
	0);
INSERT INTO SM_NSTXN
	VALUES (631,
	616,
	622,
	617,
	0);
INSERT INTO SM_TAH
	VALUES (632,
	616,
	631);
INSERT INTO SM_AH
	VALUES (632,
	616);
INSERT INTO SM_ACT
	VALUES (632,
	616,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (633,
	616,
	632);
INSERT INTO ACT_ACT
	VALUES (633,
	'transition',
	0,
	0,
	0,
	0,
	'HM1: poll in Notifying of increased activity to Polling monitors',
	0);
INSERT INTO SM_TXN
	VALUES (631,
	616,
	619,
	0);
INSERT INTO SM_ASM
	VALUES (634,
	51);
INSERT INTO SM_SM
	VALUES (634,
	'',
	0);
INSERT INTO SM_MOORE
	VALUES (634);
INSERT INTO SM_SGEVT
	VALUES (635,
	634,
	0,
	351,
	0);
INSERT INTO SM_SEVT
	VALUES (635,
	634,
	0);
INSERT INTO SM_EVT
	VALUES (635,
	634,
	0,
	1,
	'breath_taken',
	0,
	'',
	'HM_A1',
	'');
INSERT INTO SM_SGEVT
	VALUES (636,
	634,
	0,
	353,
	0);
INSERT INTO SM_SEVT
	VALUES (636,
	634,
	0);
INSERT INTO SM_EVT
	VALUES (636,
	634,
	0,
	2,
	'current_temp',
	0,
	'',
	'HM_A2',
	'');
INSERT INTO SM_STATE
	VALUES (637,
	634,
	0,
	'listening to host events',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (637,
	635,
	634,
	0);
INSERT INTO SM_SEME
	VALUES (637,
	636,
	634,
	0);
INSERT INTO SM_MOAH
	VALUES (638,
	634,
	637);
INSERT INTO SM_AH
	VALUES (638,
	634);
INSERT INTO SM_ACT
	VALUES (638,
	634,
	1,
	'',
	'');
INSERT INTO ACT_SAB
	VALUES (639,
	634,
	638);
INSERT INTO ACT_ACT
	VALUES (639,
	'class state',
	0,
	0,
	0,
	0,
	'Host Monitor::listening to host events',
	0);
INSERT INTO SM_NSTXN
	VALUES (640,
	634,
	637,
	635,
	0);
INSERT INTO SM_TAH
	VALUES (641,
	634,
	640);
INSERT INTO SM_AH
	VALUES (641,
	634);
INSERT INTO SM_ACT
	VALUES (641,
	634,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (642,
	634,
	641);
INSERT INTO ACT_ACT
	VALUES (642,
	'class transition',
	0,
	0,
	0,
	0,
	'host::breath_taken in listening to host events to listening to host events',
	0);
INSERT INTO SM_TXN
	VALUES (640,
	634,
	637,
	0);
INSERT INTO SM_NSTXN
	VALUES (643,
	634,
	637,
	636,
	0);
INSERT INTO SM_TAH
	VALUES (644,
	634,
	643);
INSERT INTO SM_AH
	VALUES (644,
	634);
INSERT INTO SM_ACT
	VALUES (644,
	634,
	1,
	'',
	'');
INSERT INTO ACT_TAB
	VALUES (645,
	634,
	644);
INSERT INTO ACT_ACT
	VALUES (645,
	'class transition',
	0,
	0,
	0,
	0,
	'host::current_temp in listening to host events to listening to host events',
	0);
INSERT INTO SM_TXN
	VALUES (643,
	634,
	637,
	0);
INSERT INTO PE_PE
	VALUES (42,
	1,
	615,
	0,
	4);
INSERT INTO O_OBJ
	VALUES (42,
	'Temperature Monitor',
	3,
	'TM',
	'',
	0);
INSERT INTO O_TFR
	VALUES (646,
	42,
	'getCurrentTemp',
	'',
	5,
	1,
	'return 0;',
	1,
	'',
	0);
INSERT INTO ACT_OPB
	VALUES (647,
	646);
INSERT INTO ACT_ACT
	VALUES (647,
	'operation',
	0,
	0,
	0,
	0,
	'Temperature Monitor::getCurrentTemp',
	0);
INSERT INTO O_ID
	VALUES (0,
	42);
INSERT INTO O_ID
	VALUES (1,
	42);
INSERT INTO O_ID
	VALUES (2,
	42);
INSERT INTO PE_PE
	VALUES (47,
	1,
	615,
	0,
	4);
INSERT INTO O_OBJ
	VALUES (47,
	'Respiratory Monitor',
	4,
	'RM',
	'',
	0);
INSERT INTO O_TFR
	VALUES (648,
	47,
	'getCurrentRate',
	'',
	5,
	1,
	'return 0;',
	1,
	'',
	0);
INSERT INTO ACT_OPB
	VALUES (649,
	648);
INSERT INTO ACT_ACT
	VALUES (649,
	'operation',
	0,
	0,
	0,
	0,
	'Respiratory Monitor::getCurrentRate',
	0);
INSERT INTO O_ID
	VALUES (0,
	47);
INSERT INTO O_ID
	VALUES (1,
	47);
INSERT INTO O_ID
	VALUES (2,
	47);
INSERT INTO PE_PE
	VALUES (45,
	1,
	615,
	0,
	9);
INSERT INTO R_SIMP
	VALUES (45);
INSERT INTO R_REL
	VALUES (45,
	2,
	'',
	0);
INSERT INTO R_PART
	VALUES (51,
	45,
	650,
	0,
	0,
	'provides temperature monitoring');
INSERT INTO R_RTO
	VALUES (51,
	45,
	650,
	-1);
INSERT INTO R_OIR
	VALUES (51,
	45,
	650,
	0);
INSERT INTO R_PART
	VALUES (42,
	45,
	651,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (42,
	45,
	651,
	-1);
INSERT INTO R_OIR
	VALUES (42,
	45,
	651,
	0);
INSERT INTO PE_PE
	VALUES (50,
	1,
	615,
	0,
	9);
INSERT INTO R_SIMP
	VALUES (50);
INSERT INTO R_REL
	VALUES (50,
	3,
	'',
	0);
INSERT INTO R_PART
	VALUES (51,
	50,
	652,
	0,
	0,
	'provides respiratory monitoring');
INSERT INTO R_RTO
	VALUES (51,
	50,
	652,
	-1);
INSERT INTO R_OIR
	VALUES (51,
	50,
	652,
	0);
INSERT INTO R_PART
	VALUES (47,
	50,
	653,
	0,
	1,
	'');
INSERT INTO R_RTO
	VALUES (47,
	50,
	653,
	-1);
INSERT INTO R_OIR
	VALUES (47,
	50,
	653,
	0);
INSERT INTO CL_IIR
	VALUES (654,
	655,
	611,
	356,
	'host',
	'');
INSERT INTO CL_IP
	VALUES (654,
	'host',
	'');
INSERT INTO PE_PE
	VALUES (358,
	1,
	162,
	0,
	2);
INSERT INTO C_C
	VALUES (358,
	0,
	0,
	'pacemaker',
	'',
	0,
	0);
INSERT INTO C_PO
	VALUES (656,
	358,
	'to_heart',
	0,
	0);
INSERT INTO C_IR
	VALUES (360,
	147,
	355,
	656);
INSERT INTO C_P
	VALUES (360,
	'synchronization',
	'Unnamed Interface',
	'');
INSERT INTO SPR_PEP
	VALUES (508,
	148,
	360);
INSERT INTO SPR_PS
	VALUES (508,
	'systolic_pulse',
	'',
	'',
	1);
INSERT INTO ACT_PSB
	VALUES (657,
	508);
INSERT INTO ACT_ACT
	VALUES (657,
	'signal',
	0,
	0,
	0,
	0,
	'to_heart::synchronization::systolic_pulse',
	0);
INSERT INTO SPR_PEP
	VALUES (506,
	149,
	360);
INSERT INTO SPR_PS
	VALUES (506,
	'diastolic_pulse',
	'',
	'',
	1);
INSERT INTO ACT_PSB
	VALUES (658,
	506);
INSERT INTO ACT_ACT
	VALUES (658,
	'signal',
	0,
	0,
	0,
	0,
	'to_heart::synchronization::diastolic_pulse',
	0);
INSERT INTO SPR_PEP
	VALUES (495,
	150,
	360);
INSERT INTO SPR_PS
	VALUES (495,
	'systolic_pace',
	'',
	'',
	1);
INSERT INTO ACT_PSB
	VALUES (659,
	495);
INSERT INTO ACT_ACT
	VALUES (659,
	'signal',
	0,
	0,
	0,
	0,
	'to_heart::synchronization::systolic_pace',
	0);
INSERT INTO SPR_PEP
	VALUES (489,
	151,
	360);
INSERT INTO SPR_PS
	VALUES (489,
	'diastolic_pace',
	'',
	'',
	1);
INSERT INTO ACT_PSB
	VALUES (660,
	489);
INSERT INTO ACT_ACT
	VALUES (660,
	'signal',
	0,
	0,
	0,
	0,
	'to_heart::synchronization::diastolic_pace',
	0);
INSERT INTO SPR_PEP
	VALUES (661,
	152,
	360);
INSERT INTO SPR_PO
	VALUES (661,
	'init',
	'',
	'// Initialize by creating the pacer instance and setting its attribute values.
create object instance pacer of PACER;
pacer.systolic_tolerance = 25;   // percent
pacer.diastolic_tolerance = 25;  // percent
pacer.cycle_count = 0;

// Compute the timeouts using the period and tolerance
pacer.systolic_timeout = param.systolic_period + (param.systolic_period * pacer.systolic_tolerance/100);
pacer.diastolic_timeout = param.diastolic_period + (param.diastolic_period * pacer.diastolic_tolerance/100);',
	1);
INSERT INTO ACT_POB
	VALUES (662,
	661);
INSERT INTO ACT_ACT
	VALUES (662,
	'interface operation',
	0,
	0,
	0,
	0,
	'to_heart::synchronization::init',
	0);
INSERT INTO C_PO
	VALUES (663,
	358,
	'monitor',
	0,
	0);
INSERT INTO C_IR
	VALUES (609,
	155,
	0,
	663);
INSERT INTO C_P
	VALUES (609,
	'monitor',
	'Unnamed Interface',
	'');
INSERT INTO SPR_PEP
	VALUES (510,
	156,
	609);
INSERT INTO SPR_PS
	VALUES (510,
	'increased_activty',
	'',
	'',
	1);
INSERT INTO ACT_PSB
	VALUES (664,
	510);
INSERT INTO ACT_ACT
	VALUES (664,
	'signal',
	0,
	0,
	0,
	0,
	'monitor::monitor::increased_activty',
	0);
INSERT INTO PE_PE
	VALUES (612,
	1,
	162,
	0,
	2);
INSERT INTO C_C
	VALUES (612,
	0,
	0,
	'hostmonitor',
	'',
	0,
	0);
INSERT INTO C_PO
	VALUES (665,
	612,
	'monitor',
	0,
	0);
INSERT INTO C_IR
	VALUES (614,
	155,
	0,
	665);
INSERT INTO C_R
	VALUES (614,
	'monitor',
	'',
	'Unnamed Interface');
INSERT INTO SPR_REP
	VALUES (666,
	156,
	614);
INSERT INTO SPR_RS
	VALUES (666,
	'increased_activty',
	'',
	'',
	1);
INSERT INTO ACT_RSB
	VALUES (667,
	666);
INSERT INTO ACT_ACT
	VALUES (667,
	'signal',
	0,
	0,
	0,
	0,
	'monitor::monitor::increased_activty',
	0);
INSERT INTO C_PO
	VALUES (668,
	612,
	'host',
	0,
	0);
INSERT INTO C_IR
	VALUES (655,
	159,
	356,
	668);
INSERT INTO C_P
	VALUES (655,
	'host',
	'Unnamed Interface',
	'');
INSERT INTO SPR_PEP
	VALUES (669,
	160,
	655);
INSERT INTO SPR_PS
	VALUES (669,
	'breath_taken',
	'',
	'',
	1);
INSERT INTO ACT_PSB
	VALUES (670,
	669);
INSERT INTO ACT_ACT
	VALUES (670,
	'signal',
	0,
	0,
	0,
	0,
	'host::host::breath_taken',
	0);
INSERT INTO SPR_PEP
	VALUES (671,
	161,
	655);
INSERT INTO SPR_PS
	VALUES (671,
	'current_temp',
	'',
	'',
	1);
INSERT INTO ACT_PSB
	VALUES (672,
	671);
INSERT INTO ACT_ACT
	VALUES (672,
	'signal',
	0,
	0,
	0,
	0,
	'host::host::current_temp',
	0);
INSERT INTO PE_PE
	VALUES (610,
	1,
	162,
	0,
	22);
INSERT INTO C_SF
	VALUES (610,
	614,
	609,
	'');
INSERT INTO EP_PKG
	VALUES (673,
	1,
	1,
	'system',
	'',
	0);
INSERT INTO PE_PE
	VALUES (674,
	1,
	673,
	0,
	22);
INSERT INTO C_SF
	VALUES (674,
	165,
	338,
	'');
INSERT INTO PE_PE
	VALUES (675,
	1,
	673,
	0,
	21);
INSERT INTO CL_IC
	VALUES (675,
	163,
	0,
	0,
	0,
	'',
	'avpace::library::heart',
	'');
INSERT INTO CL_IIR
	VALUES (676,
	165,
	675,
	0,
	'synchronization',
	'');
INSERT INTO CL_IR
	VALUES (676,
	674,
	'synchronization',
	'');
INSERT INTO CL_IIR
	VALUES (677,
	177,
	675,
	0,
	'host',
	'');
INSERT INTO CL_IR
	VALUES (677,
	0,
	'host',
	'');
INSERT INTO PE_PE
	VALUES (678,
	1,
	673,
	0,
	21);
INSERT INTO CL_IC
	VALUES (678,
	336,
	0,
	0,
	0,
	'',
	'avpace::library::pacer',
	'');
INSERT INTO CL_IIR
	VALUES (679,
	338,
	678,
	0,
	'synchronization',
	'');
INSERT INTO CL_IP
	VALUES (679,
	'synchronization',
	'');
INSERT INTO CL_IPINS
	VALUES (674,
	679);
INSERT INTO CL_IIR
	VALUES (680,
	350,
	678,
	0,
	'host',
	'');
INSERT INTO CL_IP
	VALUES (680,
	'host',
	'');
