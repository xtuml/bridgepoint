-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (6676330,
	'asc',
	'This test deals with Associative classes.

The three classes used :  Buyer, Store, and Thing.  A few instances of each are created, populated with data and related to each other.

The test then outputs using LogInfo the Buyer, Store, and a list of Things.  First by using a select where on the store ID. Then by selecting from one side, the Buyer.  Lastly by selecting many.

This test does not have LogSuccess or LogFailure output.',
	0,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	6676330,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	6676330,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	6676330,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	6676330,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	6676330,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	6676330,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	6676330,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	6676330,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	6676330,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	6676330,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	6676330,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	6676330,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	6676330,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	6676330,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	6676330,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	6676330,
	'inst_ref<Timer>',
	'');
INSERT INTO S_EE
	VALUES (524293,
	'Time',
	'',
	'TIM',
	6676330);
INSERT INTO S_BRG
	VALUES (524321,
	524293,
	'current_date',
	'',
	1,
	524302,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524322,
	524293,
	'create_date',
	'',
	1,
	524302,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524335,
	524322,
	'second',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524336,
	524322,
	'minute',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524337,
	524322,
	'hour',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524338,
	524322,
	'day',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524339,
	524322,
	'month',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524340,
	524322,
	'year',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524323,
	524293,
	'get_second',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524341,
	524323,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524324,
	524293,
	'get_minute',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524342,
	524324,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524325,
	524293,
	'get_hour',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524343,
	524325,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524326,
	524293,
	'get_day',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524344,
	524326,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524327,
	524293,
	'get_month',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524345,
	524327,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524328,
	524293,
	'get_year',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524346,
	524328,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524329,
	524293,
	'current_clock',
	'',
	1,
	524303,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524330,
	524293,
	'timer_start',
	'',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524347,
	524330,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524348,
	524330,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524331,
	524293,
	'timer_start_recurring',
	'',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524349,
	524331,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524350,
	524331,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524332,
	524293,
	'timer_remaining_time',
	'',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524351,
	524332,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BRG
	VALUES (524333,
	524293,
	'timer_reset_time',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524352,
	524333,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524353,
	524333,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524334,
	524293,
	'timer_add_time',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524354,
	524334,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524355,
	524334,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524335,
	524293,
	'timer_cancel',
	'',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524356,
	524335,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_EE
	VALUES (524294,
	'Architecture',
	'',
	'ARCH',
	6676330);
INSERT INTO S_BRG
	VALUES (524336,
	524294,
	'shutdown',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_EE
	VALUES (524295,
	'Logger',
	'',
	'LOG',
	6676330);
INSERT INTO S_BRG
	VALUES (524337,
	524295,
	'LogInfo',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524357,
	524337,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524338,
	524295,
	'LogSuccess',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524359,
	524338,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524339,
	524295,
	'LogFailure',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524360,
	524339,
	'message',
	524293,
	0);
INSERT INTO GD_MD
	VALUES (524289,
	1,
	6676330,
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
	VALUES (524336,
	524289,
	3145734,
	11);
INSERT INTO GD_SHP
	VALUES (524336,
	1920,
	1344,
	2080,
	1440);
INSERT INTO GD_GE
	VALUES (524337,
	524289,
	524293,
	12);
INSERT INTO GD_SHP
	VALUES (524337,
	1920,
	1632,
	2080,
	1728);
INSERT INTO GD_GE
	VALUES (524338,
	524289,
	524294,
	12);
INSERT INTO GD_SHP
	VALUES (524338,
	2128,
	1520,
	2272,
	1632);
INSERT INTO GD_GE
	VALUES (524339,
	524289,
	524295,
	12);
INSERT INTO GD_SHP
	VALUES (524339,
	1744,
	1504,
	1888,
	1616);
INSERT INTO S_SS
	VALUES (3145734,
	'Association',
	'',
	'',
	1,
	6676330,
	3145734);
INSERT INTO O_OBJ
	VALUES (3145733,
	'Buyer',
	1,
	'B',
	'',
	3145734);
INSERT INTO O_NBATTR
	VALUES (3145741,
	3145733);
INSERT INTO O_BATTR
	VALUES (3145741,
	3145733);
INSERT INTO O_ATTR
	VALUES (3145741,
	3145733,
	0,
	'Company',
	'',
	'',
	'Company',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145742,
	3145733);
INSERT INTO O_BATTR
	VALUES (3145742,
	3145733);
INSERT INTO O_ATTR
	VALUES (3145742,
	3145733,
	3145741,
	'BuyerName',
	'',
	'',
	'BuyerName',
	0,
	524293);
INSERT INTO O_ID
	VALUES (0,
	3145733);
INSERT INTO O_OIDA
	VALUES (3145741,
	3145733,
	0);
INSERT INTO O_RTIDA
	VALUES (3145741,
	3145733,
	0,
	3145730,
	3145732);
INSERT INTO O_OIDA
	VALUES (3145742,
	3145733,
	0);
INSERT INTO O_RTIDA
	VALUES (3145742,
	3145733,
	0,
	3145730,
	3145732);
INSERT INTO O_OBJ
	VALUES (3145734,
	'Store',
	2,
	'S',
	'',
	3145734);
INSERT INTO O_NBATTR
	VALUES (3145743,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145743,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145743,
	3145734,
	0,
	'StoreName',
	'',
	'',
	'StoreName',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (3145744,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145744,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145744,
	3145734,
	3145743,
	'StoreNo',
	'',
	'',
	'StoreNo',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	3145734);
INSERT INTO O_OIDA
	VALUES (3145744,
	3145734,
	0);
INSERT INTO O_RTIDA
	VALUES (3145744,
	3145734,
	0,
	3145730,
	3145733);
INSERT INTO O_OIDA
	VALUES (3145743,
	3145734,
	0);
INSERT INTO O_RTIDA
	VALUES (3145743,
	3145734,
	0,
	3145730,
	3145733);
INSERT INTO O_OBJ
	VALUES (3145735,
	'GetThings',
	3,
	'G',
	'',
	3145734);
INSERT INTO O_REF
	VALUES (3145735,
	3145733,
	0,
	3145741,
	3145730,
	3145734,
	3145732,
	3145745,
	3145733,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145745,
	3145735,
	3145741,
	3145733,
	1);
INSERT INTO O_ATTR
	VALUES (3145745,
	3145735,
	0,
	'Company',
	'',
	'',
	'Company',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145735,
	3145733,
	0,
	3145742,
	3145730,
	3145734,
	3145732,
	3145746,
	3145734,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145746,
	3145735,
	3145742,
	3145733,
	1);
INSERT INTO O_ATTR
	VALUES (3145746,
	3145735,
	3145745,
	'BuyerName',
	'',
	'',
	'BuyerName',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145735,
	3145734,
	0,
	3145743,
	3145730,
	3145734,
	3145733,
	3145747,
	3145735,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145747,
	3145735,
	3145743,
	3145734,
	1);
INSERT INTO O_ATTR
	VALUES (3145747,
	3145735,
	3145746,
	'StoreName',
	'',
	'',
	'StoreName',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145735,
	3145734,
	0,
	3145744,
	3145730,
	3145734,
	3145733,
	3145748,
	3145736,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145748,
	3145735,
	3145744,
	3145734,
	1);
INSERT INTO O_ATTR
	VALUES (3145748,
	3145735,
	3145747,
	'StoreNo',
	'',
	'',
	'StoreNo',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145749,
	3145735);
INSERT INTO O_BATTR
	VALUES (3145749,
	3145735);
INSERT INTO O_ATTR
	VALUES (3145749,
	3145735,
	3145748,
	'Code',
	'',
	'',
	'Code',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145755,
	3145735);
INSERT INTO O_BATTR
	VALUES (3145755,
	3145735);
INSERT INTO O_ATTR
	VALUES (3145755,
	3145735,
	3145749,
	'Description',
	'',
	'',
	'Description',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (3145750,
	3145735);
INSERT INTO O_BATTR
	VALUES (3145750,
	3145735);
INSERT INTO O_ATTR
	VALUES (3145750,
	3145735,
	3145755,
	'Price',
	'',
	'',
	'Price',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	3145735);
INSERT INTO O_OIDA
	VALUES (3145749,
	3145735,
	0);
INSERT INTO O_OIDA
	VALUES (3145746,
	3145735,
	0);
INSERT INTO O_OIDA
	VALUES (3145747,
	3145735,
	0);
INSERT INTO O_OIDA
	VALUES (3145748,
	3145735,
	0);
INSERT INTO O_OIDA
	VALUES (3145745,
	3145735,
	0);
INSERT INTO O_OBJ
	VALUES (3145736,
	'Association init',
	4,
	'INIT',
	'',
	3145734);
INSERT INTO O_NBATTR
	VALUES (3145751,
	3145736);
INSERT INTO O_BATTR
	VALUES (3145751,
	3145736);
INSERT INTO O_ATTR
	VALUES (3145751,
	3145736,
	0,
	'ID',
	'',
	'',
	'ID',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145752,
	3145736);
INSERT INTO O_BATTR
	VALUES (3145752,
	3145736);
INSERT INTO O_ATTR
	VALUES (3145752,
	3145736,
	3145751,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	3145736);
INSERT INTO O_OIDA
	VALUES (3145751,
	3145736,
	0);
INSERT INTO SM_ISM
	VALUES (1572867,
	3145736);
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
	'INIT',
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
	'//create some instances of buyers
create object instance buyer1 of B;
buyer1.Company = 1;
buyer1.BuyerName = "Fred";
create object instance buyer2 of B;
buyer2.Company = 2;
buyer2.BuyerName = "Joe";

//create some instances of stores
create object instance store1 of S;
store1.StoreName = "Home Depot";
store1.StoreNo = 1;
create object instance store2 of S;
store2.StoreName = "Classic Hobbies";
store2.StoreNo = 2;

//create some instances of things
create object instance thing1 of G;
thing1.Code = 1;
thing1.Description = "Sandpaper";
create object instance thing2 of G;
thing2.Code = 2;
thing2.Description = "D12-5 Rocket Motor";
create object instance thing3 of G;
thing3.Code = 3;
thing3.Description = "16 Pound SledgeHammer";
create object instance thing4 of G;
thing4.Code = 4;
thing4.Description = "Mars Lander Rocket Kit";

//relate things to buyers
relate buyer1 to store1 across R1 using thing1;
relate buyer1 to store2 across R1 using thing2;
relate buyer2 to store1 across R1 using thing3;
relate buyer2 to store2 across R1 using thing4;

generate DR1 to DR creator;
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
	1648,
	1264,
	2176,
	1488);
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
	1840,
	1180,
	1992,
	1224,
	-39,
	-11,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572868,
	1572867,
	1760,
	1264,
	1760,
	1216,
	0);
INSERT INTO GD_LS
	VALUES (1572869,
	1572867,
	1760,
	1216,
	2048,
	1216,
	1572868);
INSERT INTO GD_LS
	VALUES (1572870,
	1572867,
	2048,
	1216,
	2048,
	1264,
	1572869);
INSERT INTO O_OBJ
	VALUES (3145737,
	'Driver',
	5,
	'DR',
	'',
	3145734);
INSERT INTO O_NBATTR
	VALUES (3145754,
	3145737);
INSERT INTO O_BATTR
	VALUES (3145754,
	3145737);
INSERT INTO O_ATTR
	VALUES (3145754,
	3145737,
	0,
	'driver_id',
	'',
	'',
	'driver_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (3145753,
	3145737);
INSERT INTO O_BATTR
	VALUES (3145753,
	3145737);
INSERT INTO O_ATTR
	VALUES (3145753,
	3145737,
	3145754,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	3145737);
INSERT INTO O_OIDA
	VALUES (3145754,
	3145737,
	0);
INSERT INTO SM_ISM
	VALUES (2097156,
	3145737);
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
	'start test',
	0,
	'',
	'DR1',
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
	'NavigateOneSide',
	0,
	'',
	'DR2',
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
	'NavigateManySide',
	0,
	'',
	'DR3',
	'');
INSERT INTO SM_STATE
	VALUES (2097153,
	2097156,
	0,
	'One Side',
	1,
	0);
INSERT INTO SM_EIGN
	VALUES (2097153,
	2097153,
	2097156,
	0,
	'');
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
	'// What things are related to Fred?
msg = "";
select any buyer from instances of B where selected.BuyerName == "Fred";
select many things related by buyer->G[R1];
for each thing in things
    msg = buyer.BuyerName + " is related to " + thing.Description + ", located at " + thing.StoreName + ".";
    if ( thing.Code == 1 AND thing.StoreNo == 1 )       
        LOG::LogSuccess(message:msg);
    elif( thing.Code == 2 AND thing.StoreNo == 2 )
        LOG::LogSuccess(message:msg);
    else
        msg = msg + " It should NOT be!";
        LOG::LogFailure(message:msg);
    end if;
end for;

// What things are related to Joe?
select any buyer from instances of B where selected.BuyerName == "Joe";
select many things related by buyer->G[R1];
for each thing in things
    msg =  buyer.BuyerName + " is related to " + thing.Description + ", located at " + thing.StoreName + ".";
    if ( thing.Code == 3 AND thing.StoreNo == 1 )
        LOG::LogSuccess(message:msg);
    elif( thing.Code == 4 AND thing.StoreNo == 2 )
        LOG::LogSuccess(message:msg);
    else
        msg = msg + " It should NOT be!";
        LOG::LogFailure(message:msg);
    end if;
end for;

generate DR2 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097154,
	2097156,
	0,
	'Select Where',
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
	'// What things are related to Fred?
msg = "";
select any buyer from instances of B where selected.BuyerName == "Fred";
select many stores related by buyer->S[R1];
for each store in stores
    select many things related by buyer->G[R1] where selected.StoreNo == store.StoreNo;
    for each thing in things
        msg = buyer.BuyerName + " is related to " + thing.Description + ", located at " + store.StoreName + ".";
        if ( thing.Code == 1 AND store.StoreNo == 1 )       
            LOG::LogSuccess(message:msg);
        elif( thing.Code == 2 AND thing.StoreNo == 2 )
            LOG::LogSuccess(message:msg);
        else
            msg = msg + " It should NOT be!";
            LOG::LogFailure(message:msg);
        end if;
    end for;
end for;

// What things are related to Joe?
select any buyer from instances of B where selected.BuyerName == "Joe";
select many stores related by buyer->S[R1];
for each store in stores
    select many things related by buyer->G[R1] where selected.StoreNo == store.StoreNo;
    for each thing in things
        msg =  buyer.BuyerName + " is related to " + thing.Description + ", located at " + thing.StoreName + ".";
        if ( thing.Code == 3 AND thing.StoreNo == 1 )
            LOG::LogSuccess(message:msg);
        elif( thing.Code == 4 AND thing.StoreNo == 2 )
            LOG::LogSuccess(message:msg);
        else
            msg = msg + " It should NOT be!";
            LOG::LogFailure(message:msg);
        end if;
    end for;
end for;

generate DR3 to self;
',
	'');
INSERT INTO SM_STATE
	VALUES (2097155,
	2097156,
	0,
	'Many Side',
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
	'// Check navigation from the many side

// What things are related to Fred?
select any buyer from instances of B where selected.BuyerName == "Fred";
select many stores related by buyer->S[R1];
select many store_things related by stores->G[R1] where buyer.BuyerName == selected.BuyerName;
select many buyer_things related by buyer->G[R1];
found_thing = FALSE;
for each store_thing in store_things
    for each buyer_thing in buyer_things
         if( buyer_thing == store_thing )
            found_thing = TRUE;
            break;
        end if;
    end for;  // Buyer
    if( found_thing == TRUE )
        msg = "Fred: " + store_thing.Description + " was in both the buyer and store sets.";
        LOG::LogSuccess(message:msg);
    else
        msg = "Fred: " + store_thing.Description + " was NOT in both the buyer and store sets.";
        LOG::LogFailure(message:msg);
    end if;
end for;  // Store

// What things are related to Joe?
select any buyer from instances of B where selected.BuyerName == "Joe";
select many stores related by buyer->S[R1];
select many store_things related by stores->G[R1] where buyer.BuyerName == selected.BuyerName;
select many buyer_things related by buyer->G[R1];
found_thing = FALSE;
for each store_thing in store_things
    for each buyer_thing in buyer_things
         if( buyer_thing == store_thing )
            found_thing = TRUE;
            break;
        end if;
    end for;  // Buyer
    if( found_thing == TRUE )
        msg = "Joe: " + store_thing.Description + " was in both the buyer and store sets.";
        LOG::LogSuccess(message:msg);
    else
        msg = "Joe: " + store_thing.Description + " was NOT in both the buyer and store sets.";
        LOG::LogFailure(message:msg);
    end if;
end for;  // Store

ARCH::shutdown();
control stop;',
	'');
INSERT INTO SM_CRTXN
	VALUES (2097153,
	2097156,
	2097153,
	2097153);
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
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097155,
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
	1872,
	1248,
	2096,
	1360);
INSERT INTO GD_GE
	VALUES (2097157,
	2097153,
	2097154,
	41);
INSERT INTO GD_SHP
	VALUES (2097157,
	1872,
	1440,
	2096,
	1552);
INSERT INTO GD_GE
	VALUES (2097158,
	2097153,
	2097155,
	41);
INSERT INTO GD_SHP
	VALUES (2097158,
	1872,
	1648,
	2096,
	1760);
INSERT INTO GD_GE
	VALUES (2097155,
	2097153,
	2097153,
	42);
INSERT INTO GD_CON
	VALUES (2097155,
	2097154,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (2097155,
	0,
	0,
	0,
	0,
	0,
	0,
	2011,
	1185,
	2135,
	1228,
	27,
	-16,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097164,
	2097155,
	2000,
	1248,
	2000,
	1184,
	0);
INSERT INTO GD_GE
	VALUES (2097159,
	2097153,
	2097154,
	42);
INSERT INTO GD_CON
	VALUES (2097159,
	2097154,
	2097157,
	0);
INSERT INTO GD_CTXT
	VALUES (2097159,
	0,
	0,
	0,
	0,
	0,
	0,
	2005,
	1380,
	2186,
	1422,
	21,
	-5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097166,
	2097159,
	2000,
	1360,
	2000,
	1440,
	0);
INSERT INTO GD_GE
	VALUES (2097161,
	2097153,
	2097155,
	42);
INSERT INTO GD_CON
	VALUES (2097161,
	2097157,
	2097158,
	0);
INSERT INTO GD_CTXT
	VALUES (2097161,
	0,
	0,
	0,
	0,
	0,
	0,
	2008,
	1570,
	2198,
	1613,
	24,
	-15,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097167,
	2097161,
	2000,
	1552,
	2000,
	1648,
	0);
INSERT INTO R_ASSOC
	VALUES (3145730);
INSERT INTO R_REL
	VALUES (3145730,
	1,
	'',
	3145734);
INSERT INTO R_AONE
	VALUES (3145733,
	3145730,
	3145732,
	1,
	1,
	'');
INSERT INTO R_RTO
	VALUES (3145733,
	3145730,
	3145732,
	0);
INSERT INTO R_OIR
	VALUES (3145733,
	3145730,
	3145732,
	0);
INSERT INTO R_AOTH
	VALUES (3145734,
	3145730,
	3145733,
	1,
	0,
	'');
INSERT INTO R_RTO
	VALUES (3145734,
	3145730,
	3145733,
	0);
INSERT INTO R_OIR
	VALUES (3145734,
	3145730,
	3145733,
	0);
INSERT INTO R_ASSR
	VALUES (3145735,
	3145730,
	3145734,
	1);
INSERT INTO R_RGO
	VALUES (3145735,
	3145730,
	3145734);
INSERT INTO R_OIR
	VALUES (3145735,
	3145730,
	3145734,
	0);
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
	1317,
	4199,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (3145767,
	3145764,
	3145733,
	21);
INSERT INTO GD_SHP
	VALUES (3145767,
	1616,
	1280,
	1776,
	1424);
INSERT INTO GD_GE
	VALUES (3145768,
	3145764,
	3145734,
	21);
INSERT INTO GD_SHP
	VALUES (3145768,
	2048,
	1280,
	2208,
	1424);
INSERT INTO GD_GE
	VALUES (3145769,
	3145764,
	3145735,
	21);
INSERT INTO GD_SHP
	VALUES (3145769,
	1776,
	1536,
	2016,
	1680);
INSERT INTO GD_GE
	VALUES (3145770,
	3145764,
	3145736,
	21);
INSERT INTO GD_SHP
	VALUES (3145770,
	1440,
	1456,
	1648,
	1584);
INSERT INTO GD_GE
	VALUES (3145787,
	3145764,
	3145737,
	21);
INSERT INTO GD_SHP
	VALUES (3145787,
	1440,
	1632,
	1648,
	1776);
INSERT INTO GD_GE
	VALUES (3145783,
	3145764,
	3145730,
	24);
INSERT INTO GD_CON
	VALUES (3145783,
	3145767,
	3145768,
	0);
INSERT INTO GD_CTXT
	VALUES (3145783,
	0,
	0,
	0,
	0,
	0,
	0,
	1888,
	1312,
	1938,
	1342,
	1,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145784,
	3145783,
	1776,
	1344,
	2048,
	1344,
	0);
INSERT INTO GD_GE
	VALUES (3145785,
	3145764,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (3145785,
	3145769,
	3145783,
	0);
INSERT INTO GD_CTXT
	VALUES (3145785,
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
	VALUES (3145786,
	3145785,
	1904,
	1536,
	1904,
	1344,
	0);
