-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (107905,
	'G_ALL_nested_invoke',
	'This test is designed to exercise the use of invocations of functions, bridges, instance operations, and class operations as rvals.  Specifically as parameters, in assignments, as return values, in where statements, in if, elif, for, and while. Also, multiple invocations, i.e. an invocation as a parameter to another invocation, or two invocations added together with one having an invocation as a parameter, etc.

For test coverage see MC-2020_coverage.xls.',
	0,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	107905,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	107905,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	107905,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	107905,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	107905,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	107905,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	107905,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	107905,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	107905,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	107905,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	107905,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	107905,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	107905,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	107905,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	107905,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	107905,
	'inst_ref<Timer>',
	'');
INSERT INTO S_UDT
	VALUES (524305,
	524294,
	0);
INSERT INTO S_DT
	VALUES (524305,
	107905,
	'arbitrary_id',
	'Arbitrary ID with core data type of unique_id.');
INSERT INTO S_UDT
	VALUES (524306,
	524291,
	0);
INSERT INTO S_DT
	VALUES (524306,
	107905,
	'udt',
	'');
INSERT INTO S_EDT
	VALUES (524307);
INSERT INTO S_ENUM
	VALUES (524289,
	'blue',
	'',
	524307);
INSERT INTO S_ENUM
	VALUES (524290,
	'red',
	'',
	524307);
INSERT INTO S_ENUM
	VALUES (524291,
	'yellow',
	'',
	524307);
INSERT INTO S_DT
	VALUES (524307,
	107905,
	'enum',
	'');
INSERT INTO S_SYNC
	VALUES (524289,
	107905,
	'bool1',
	'',
	'select any tc from instances of TC;

if ( param.date1 == tc.d )
  LOG::LogSuccess( message: "Function bool1 - Date parameter is correct." );
else
  LOG::LogFailure( message: "Function bool1 - Date parameter is incorrect." );
end if;

return tc.b;
',
	524290,
	1);
INSERT INTO S_SPARM
	VALUES (524289,
	524289,
	'date1',
	524302,
	0);
INSERT INTO S_SYNC
	VALUES (524290,
	107905,
	'date1',
	'',
	'select any tc from instances of TC;

if ( param.evt1 == tc.e )
  LOG::LogSuccess( message: "Function date1 - Event parameter is correct." );
else
  LOG::LogFailure( message: "Function date1 - Event parameter is incorrect." );
end if;

return tc.d;',
	524302,
	1);
INSERT INTO S_SPARM
	VALUES (524290,
	524290,
	'evt1',
	524299,
	0);
INSERT INTO S_SYNC
	VALUES (524291,
	107905,
	'evt1',
	'',
	'select any tc from instances of TC;

if ( param.timer1 == tc.tr )
  LOG::LogSuccess( message: "Function evt1 - Timer parameter is correct." );
else
  LOG::LogFailure( message: "Function evt1 - Timer parameter is incorrect." );
end if;

return tc.e;',
	524299,
	1);
INSERT INTO S_SPARM
	VALUES (524291,
	524291,
	'timer1',
	524304,
	0);
INSERT INTO S_SYNC
	VALUES (524292,
	107905,
	'timer1',
	'',
	'select any tc from instances of TC;

if ( ( param.int1 == tc.i ) OR ( param.int1 == ( 2 * tc.i ) ) )
  LOG::LogSuccess( message: "Function timer1 - Integer parameter is correct." );
else
  LOG::LogFailure( message: "Function timer1 - Integer parameter is incorrect." );
end if;

return tc.tr;',
	524304,
	1);
INSERT INTO S_SPARM
	VALUES (524292,
	524292,
	'int1',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524293,
	107905,
	'int1',
	'',
	'select any tc from instances of TC;

if ( ( param.real1 == tc.r ) OR ( ( param.real1 == ( 2 * tc.r ) ) OR ( param.real1 == 0  ) ) )
  LOG::LogSuccess( message: "Function int1 - Real parameter is correct." );
else
  LOG::LogFailure( message: "Function int1 - Real parameter is incorrect." );
end if;

return tc.i;',
	524291,
	1);
INSERT INTO S_SPARM
	VALUES (524293,
	524293,
	'real1',
	524292,
	0);
INSERT INTO S_SYNC
	VALUES (524294,
	107905,
	'real1',
	'',
	'select any tc from instances of TC;

if ( ( param.str1 == tc.s ) OR ( param.str1 == ( tc.s + tc.s ) ) )
  LOG::LogSuccess( message: "Function real1 - String parameter is correct." );
else
  LOG::LogFailure( message: "Function real1 - String parameter is incorrect." );
end if;

return tc.r;',
	524292,
	1);
INSERT INTO S_SPARM
	VALUES (524294,
	524294,
	'str1',
	524293,
	0);
INSERT INTO S_SYNC
	VALUES (524295,
	107905,
	'time1',
	'',
	'select any tc from instances of TC;

if ( param.id1 == tc.ID )
  LOG::LogSuccess( message: "Function time1 - Unique ID parameter is correct." );
else
  LOG::LogFailure( message: "Function time1 - Unique ID parameter is incorrect." );
end if;

return tc.t;',
	524303,
	1);
INSERT INTO S_SPARM
	VALUES (524295,
	524295,
	'id1',
	524294,
	0);
INSERT INTO S_SYNC
	VALUES (524296,
	107905,
	'str1',
	'',
	'select any tc from instances of TC;

if ( param.time1 == tc.t )
  LOG::LogSuccess( message: "Function str1 - Timestamp parameter is correct." );
else
  LOG::LogFailure( message: "Function str1 - Timestamp parameter is incorrect." );
end if;

return tc.s;',
	524293,
	1);
INSERT INTO S_SPARM
	VALUES (524296,
	524296,
	'time1',
	524303,
	0);
INSERT INTO S_SYNC
	VALUES (524297,
	107905,
	'id1',
	'',
	'select any tc from instances of TC;

if ( param.enum1 == tc.en )
  LOG::LogSuccess( message: "Function id1 - Enumeration parameter is correct." );
else
  LOG::LogFailure( message: "Function id1 - Enumeration parameter is incorrect." );
end if;

return tc.ID;',
	524294,
	1);
INSERT INTO S_SPARM
	VALUES (524297,
	524297,
	'enum1',
	524307,
	0);
INSERT INTO S_SYNC
	VALUES (524298,
	107905,
	'enum1',
	'',
	'select any tc from instances of TC;

if ( ( param.udt1 == tc.u ) OR ( ( param.udt1 == ( 2 * tc.u ) ) OR ( param.udt1 == 1 ) ) )
  LOG::LogSuccess( message: "Function enum1 - User Data Type parameter is correct." );
else
  LOG::LogFailure( message: "Function enum1 - User Data Type parameter is incorrect." );
end if;

return tc.en;',
	524307,
	1);
INSERT INTO S_SPARM
	VALUES (524298,
	524298,
	'udt1',
	524306,
	0);
INSERT INTO S_SYNC
	VALUES (524299,
	107905,
	'udt1',
	'',
	'select any tc from instances of TC;

if ( param.bool1 == tc.b )
  LOG::LogSuccess( message: "Function udt1 - Boolean parameter is correct." );
else
  LOG::LogFailure( message: "Function udt1 - Boolean parameter is incorrect." );
end if;

return tc.u;',
	524306,
	1);
INSERT INTO S_SPARM
	VALUES (524299,
	524299,
	'bool1',
	524290,
	0);
INSERT INTO S_SYNC
	VALUES (524300,
	107905,
	'single_invoke',
	'',
	'select any tc from instances of TC;

////////////////////////////
// Functions
////////////////////////////
b = ::bool1(date1:tc.d);
d = ::date1(evt1:tc.e);
e = ::evt1(timer1:tc.tr);
tr = ::timer1(int1:tc.i);
i = ::int1(real1:tc.r);
r = ::real1(str1:tc.s);
s = ::str1(time1:tc.t);
t = ::time1(id1:tc.ID);
id = ::id1(enum1:tc.en);
en = ::enum1(udt1:tc.u);
u = ::udt1(bool1:tc.b);

////////////////////////////
// Bridges
////////////////////////////
b2 = EE::bool2(date2:tc.d);
d2 = EE::date2(evt2:tc.e);
e2 =  EE::evt2(timer2:tc.tr);
tr2 =  EE::timer2(int2:tc.i);
i2 =  EE::int2(real2:tc.r);
r2 =  EE::real2(str2:tc.s);
s2 =  EE::str2(time2:tc.t);
t2 =  EE::time2(id2:tc.ID);
id2 =  EE::id2(enum2:tc.en);
en2 = EE::enum2(udt2:tc.u);
u2 = EE::udt2(bool2:tc.b);

////////////////////////////
// Instance Operations
////////////////////////////
b3 = tc.bool3(date3:tc.d);
d3 =  tc.date3(evt3:tc.e);
e3 =  tc.evt3(timer3:tc.tr);
tr3 =  tc.timer3(int3:tc.i);
i3 =  tc.int3(real3:tc.r);
r3 =  tc.real3(str3:tc.s);
s3 =  tc.str3(time3:tc.t);
t3 =  tc.time3(id3:tc.ID);
id3 =  tc.id3(enum3:tc.en);
en3 = tc.enum3(udt3:tc.u);
u3 = tc.udt3(bool3:tc.b);

////////////////////////////
// Class Operations
////////////////////////////
b4 = TC::bool4(date4:tc.d);
d4 = TC::date4(evt4:tc.e);
e4 = TC::evt4(timer4:tc.tr);
tr4 = TC::timer4(int4:tc.i);
i4 = TC::int4(real4:tc.r);
r4 = TC::real4(str4:tc.s);
s4 = TC::str4(time4:tc.t);
t4 = TC::time4(id4:tc.ID);
id4 = TC::id4(enum4:tc.en);
en4 = TC::enum4(udt4:tc.u);
u4 = TC::udt4(bool4:tc.b);

////////////////////////////
// Results
////////////////////////////
if ( b and b2 and b3 and b4 )
  LOG::LogSuccess( message:"Single Invocation - Boolean" );
else
  LOG::LogFailure( message:"Single Invocation - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:b2 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b3 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b4 );
end if;

if ( ( d == d2 ) and ( d3 == d4 ) )
  LOG::LogSuccess( message:"Single Invocation - Date" );
else
  LOG::LogFailure( message:"Single Invocation - Date");
  LOG::LogDate( message:"Function Invocation", date:d );
  LOG::LogDate( message:"Bridge Invocation", date:d2 );
  LOG::LogDate( message:"Instance Operation Invocation", date:d3 );
  LOG::LogDate( message:"Class Operation Invocation", date:d4 );
end if;

if ( ( e == e2 ) and ( e3 == e4 ) )
  LOG::LogSuccess( message:"Single Invocation - Event" );
else
  LOG::LogFailure( message:"Single Invocation - Event");
end if;

if ( ( tr == tr2 ) and ( tr3 == tr4 ) )
  LOG::LogSuccess( message:"Single Invocation - Timer" );
else
  LOG::LogFailure( message:"Single Invocation - Timer");
end if;

if ( ( i == i2 ) and ( i3 == i4 ) )
  LOG::LogSuccess( message:"Single Invocation - Integer" );
else
  LOG::LogFailure( message:"Single Invocation - Integer");
  LOG::LogInt( message:"Function Invocation", int:i );
  LOG::LogInt( message:"Bridge Invocation", int:i2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i3 );
  LOG::LogInt( message:"Class Operation Invocation", int:i4 );
end if;

if ( ( r == r2 ) and ( r3 == r4 ) )
  LOG::LogSuccess( message:"Single Invocation - Real" );
else
  LOG::LogFailure( message:"Single Invocation - Real");
  LOG::LogReal( message:"Function Invocation", real:r );
  LOG::LogReal( message:"Bridge Invocation", real:r2 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r3 );
  LOG::LogReal( message:"Class Operation Invocation", real:r4 );
end if;

if ( ( s == s2 ) and ( s3 == s4 ) )
  LOG::LogSuccess( message:"Single Invocation - String" );
else
  LOG::LogFailure( message:"Single Invocation - String");
  LOG::LogString( message:"Function Invocation", str:s );
  LOG::LogString( message:"Bridge Invocation", str:s2 );
  LOG::LogString( message:"Instance Operation Invocation", str:s3 );
  LOG::LogString( message:"Class Operation Invocation", str:s4 );
end if;

if ( ( t== t2 ) and ( t3 == t4 ) )
  LOG::LogSuccess( message:"Single Invocation - Timestamp" );
else
  LOG::LogFailure( message:"Single Invocation - Timestamp");
  LOG::LogTime( message:"Function Invocation", time:t );
  LOG::LogTime( message:"Bridge Invocation", time:t2 );
  LOG::LogTime( message:"Instance Operation Invocation", time:t3 );
  LOG::LogTime( message:"Class Operation Invocation", time:t4 );
end if;

if ( ( id == id2 ) and ( id3 == id4 ) )
  LOG::LogSuccess( message:"Single Invocation - Unique ID" );
else
  LOG::LogFailure( message:"Single Invocation - Unique ID");
  LOG::LogUniqueId( message:"Function Invocation", uid:id );
  LOG::LogUniqueId( message:"Bridge Invocation", uid:id2 );
  LOG::LogUniqueId( message:"Instance Operation Invocation", uid:id3 );
  LOG::LogUniqueId( message:"Class Operation Invocation", uid:id4 );
end if;

if ( ( en == en2 ) and ( en3 == en4 ) )
  LOG::LogSuccess( message:"Single Invocation - Enumeration" );
else
  LOG::LogFailure( message:"Single Invocation - Enumeration");
end if;

if ( ( u == u2 ) and ( u3 == u4 ) )
  LOG::LogSuccess( message:"Single Invocation - User Data Type" );
else
  LOG::LogFailure( message:"Single Invocation - User Data Type");
  LOG::LogInt( message:"Function Invocation", int:u );
  LOG::LogInt( message:"Bridge Invocation", int:u2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u3 );
  LOG::LogInt( message:"Class Operation Invocation", int:u4 );
end if;',
	524289,
	1);
INSERT INTO S_SYNC
	VALUES (524301,
	107905,
	'multiple_invoke',
	'',
	'select any tc from instances of TC;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Functions
////////////////////////////
b = ::bool1(date1: ::date1(evt1:tc.e));
d = ::date1(evt1:EE::evt2(timer2:tc.tr));
e = ::evt1(timer1: tc.timer3(int3:tc.i));
tr = ::timer1(int1:TC::int4(real4:tc.r));
i = ::int1(real1: ::real1(str1:tc.s));
r = ::real1(str1:  EE::str2(time2:tc.t));
s = ::str1(time1: tc.time3(id3:tc.ID));
t = ::time1(id1: TC::id4(enum4:tc.en));
id = ::id1(enum1: ::enum1(udt1:tc.u));
en = ::enum1(udt1:EE::udt2(bool2:tc.b));
u = ::udt1(bool1: tc.bool3(date3:tc.d));

////////////////////////////
// Bridges
////////////////////////////
b2 = EE::bool2(date2: ::date1(evt1:tc.e));
d2 = EE::date2(evt2:  EE::evt2(timer2:tc.tr));
e2 =  EE::evt2(timer2: tc.timer3(int3:tc.i));
tr2 =  EE::timer2(int2: TC::int4(real4:tc.r));
i2 =  EE::int2(real2: ::real1(str1:tc.s));
r2 =  EE::real2(str2:  EE::str2(time2:tc.t));
s2 =  EE::str2(time2: tc.time3(id3:tc.ID));
t2 =  EE::time2(id2: TC::id4(enum4:tc.en));
id2 =  EE::id2(enum2: ::enum1(udt1:tc.u));
en2 = EE::enum2(udt2: EE::udt2(bool2:tc.b));
u2 = EE::udt2(bool2: tc.bool3(date3:tc.d));

////////////////////////////
// Instance Operations
////////////////////////////
b3 = tc.bool3(date3: ::date1(evt1:tc.e));
d3 =  tc.date3(evt3: EE::evt2(timer2:tc.tr));
e3 =  tc.evt3(timer3:  tc.timer3(int3:tc.i));
tr3 =  tc.timer3(int3:TC::int4(real4:tc.r));
i3 =  tc.int3(real3: ::real1(str1:tc.s));
r3 =  tc.real3(str3: EE::str2(time2:tc.t));
s3 =  tc.str3(time3: tc.time3(id3:tc.ID));
t3 =  tc.time3(id3: TC::id4(enum4:tc.en));
id3 =  tc.id3(enum3: ::enum1(udt1:tc.u));
en3 = tc.enum3(udt3: EE::udt2(bool2:tc.b));
u3 = tc.udt3(bool3: tc.bool3(date3:tc.d));

////////////////////////////
// Class Operations
////////////////////////////
b4 = TC::bool4(date4: ::date1(evt1:tc.e));
d4 = TC::date4(evt4: EE::evt2(timer2:tc.tr));
e4 = TC::evt4(timer4: tc.timer3(int3:tc.i));
tr4 = TC::timer4(int4:TC::int4(real4:tc.r));
i4 = TC::int4(real4: ::real1(str1:tc.s));
r4 = TC::real4(str4: EE::str2(time2:tc.t));
s4 = TC::str4(time4:tc.time3(id3:tc.ID));
t4 = TC::time4(id4:TC::id4(enum4:tc.en));
id4 = TC::id4(enum4: ::enum1(udt1:tc.u));
en4 = TC::enum4(udt4:EE::udt2(bool2:tc.b));
u4 = TC::udt4(bool4: tc.bool3(date3:tc.d));

////////////////////////////
// Results
////////////////////////////
if ( b and b2 and b3 and b4 )
  LOG::LogSuccess( message:"Multiple Invocation - Boolean" );
else
  LOG::LogFailure( message:"Multiple Invocation - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:b2 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b3 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b4 );
end if;

if ( ( d == d2 ) and ( d3 == d4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Date" );
else
  LOG::LogFailure( message:"Multiple Invocation - Date");
  LOG::LogDate( message:"Function Invocation", date:d );
  LOG::LogDate( message:"Bridge Invocation", date:d2 );
  LOG::LogDate( message:"Instance Operation Invocation", date:d3 );
  LOG::LogDate( message:"Class Operation Invocation", date:d4 );
end if;

if ( ( e == e2 ) and ( e3 == e4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Event" );
else
  LOG::LogFailure( message:"Multiple Invocation - Event");
end if;

if ( ( tr == tr2 ) and ( tr3 == tr4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Timer" );
else
  LOG::LogFailure( message:"Multiple Invocation - Timer");
end if;

if ( ( i == i2 ) and ( i3 == i4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Integer" );
else
  LOG::LogFailure( message:"Multiple Invocation - Integer");
  LOG::LogInt( message:"Function Invocation", int:i );
  LOG::LogInt( message:"Bridge Invocation", int:i2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i3 );
  LOG::LogInt( message:"Class Operation Invocation", int:i4 );
end if;

if ( ( r == r2 ) and ( r3 == r4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Real" );
else
  LOG::LogFailure( message:"Multiple Invocation - Real");
  LOG::LogReal( message:"Function Invocation", real:r );
  LOG::LogReal( message:"Bridge Invocation", real:r2 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r3 );
  LOG::LogReal( message:"Class Operation Invocation", real:r4 );
end if;

if ( ( s == s2 ) and ( s3 == s4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - String" );
else
  LOG::LogFailure( message:"Multiple Invocation - String");
  LOG::LogString( message:"Function Invocation", str:s );
  LOG::LogString( message:"Bridge Invocation", str:s2 );
  LOG::LogString( message:"Instance Operation Invocation", str:s3 );
  LOG::LogString( message:"Class Operation Invocation", str:s4 );
end if;

if ( ( t== t2 ) and ( t3 == t4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Timestamp" );
else
  LOG::LogFailure( message:"Multiple Invocation - Timestamp");
  LOG::LogTime( message:"Function Invocation", time:t );
  LOG::LogTime( message:"Bridge Invocation", time:t2 );
  LOG::LogTime( message:"Instance Operation Invocation", time:t3 );
  LOG::LogTime( message:"Class Operation Invocation", time:t4 );
end if;

if ( ( id == id2 ) and ( id3 == id4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Unique ID" );
else
  LOG::LogFailure( message:"Multiple Invocation - Unique ID");
  LOG::LogUniqueId( message:"Function Invocation", uid:id );
  LOG::LogUniqueId( message:"Bridge Invocation", uid:id2 );
  LOG::LogUniqueId( message:"Instance Operation Invocation", uid:id3 );
  LOG::LogUniqueId( message:"Class Operation Invocation", uid:id4 );
end if;

if ( ( en == en2 ) and ( en3 == en4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Enumeration" );
else
  LOG::LogFailure( message:"Multiple Invocation - Enumeration");
end if;

if ( ( u == u2 ) and ( u3 == u4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - User Data Type" );
else
  LOG::LogFailure( message:"Multiple Invocation - User Data Type");
  LOG::LogInt( message:"Function Invocation", int:u );
  LOG::LogInt( message:"Bridge Invocation", int:u2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u3 );
  LOG::LogInt( message:"Class Operation Invocation", int:u4 );
end if;',
	524289,
	1);
INSERT INTO S_SYNC
	VALUES (524302,
	107905,
	'multiple_invoke_w_expressions',
	'',
	'select any tc from instances of TC;

select any tc4 from instances of TC4;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Functions
////////////////////////////
b = ::bool1( date1: ::date1( evt1:tc.e ) ) and REE::bool5(date5: tc.date3( evt3:tc.e ) );
tr = ::timer1( int1:TC::int4( real4:tc.r )      +    ::int1( real1: tc.real3( str3:tc.s ) ) );
i = ::int1(real1: ::real1(str1:tc.s)               -   EE::real2( str2: tc.str3( time3:tc.t ) ) );
r = ::real1(str1: EE::str2(time2:tc.t)          +    tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s = ::str1( time1: EE::time2( id2: tc.ID ) )  +    tc.str3( time3: TC::time4( id4: tc.ID ) );
en = ::enum1(udt1:EE::udt2(bool2:tc.b)   /    tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u = ::udt1(bool1: tc.bool3(date3:tc.d)    and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Bridges
////////////////////////////
b2 = EE::bool2(date2: ::date1(evt1:tc.e))  and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tr2 =  EE::timer2(int2: TC::int4(real4:tc.r)     +   ::int1( real1: tc.real3( str3:tc.s ) ) );
i2 =  EE::int2(real2: ::real1(str1:tc.s)            *  REE::real5( str5: tc.str3( time3:tc.t ) ) );
r2 =  EE::real2(str2:  EE::str2(time2:tc.t)      +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s2 =  EE::str2(time2: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
en2 = EE::enum2(udt2: REE::udt5(bool5:tc.b) /  ::udt1( bool1: TC::bool4( date4:tc.d ) ) );
u2 = EE::udt2(bool2: tc4.bool6(date6:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Instance Operations
////////////////////////////
b3 = tc.bool3(date3: ::date1(evt1:tc.e))   or EE::bool2(date2: tc.date3( evt3:tc.e ) );
tr3 =  tc.timer3(int3:TC4::int7(real7:tc.r)       +   REE::int5( real5: tc.real3( str3:tc.s ) ) );
i3 =  tc.int3(real3: TC::real4(str4:tc.s)            -  REE::real5( str5: tc.str3( time3:tc.t ) ) );
r3 =  tc4.real6(str6: EE::str2(time2:tc.t)        +   tc4.str6( time6: TC::time4( id4:tc.ID ) ) );
s3 =  tc.str3(time3: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
en3 = tc4.enum6(udt6: TC4::udt7(bool7:tc.b) -  tc4.udt6( bool6: TC::bool4( date4:tc.d ) ) );
u3 = tc.udt3(bool3: tc.bool3(date3:tc.d)   or  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Class Operations
////////////////////////////
b4 = TC::bool4(date4: ::date1(evt1:tc.e))   or REE::bool5(date5: tc.date3( evt3:tc.e ) );
tr4 = TC::timer4(int4:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
i4 = TC::int4(real4: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
r4 = TC::real4(str4: REE::str5(time5:tc.t)        +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s4 = TC::str4(time4: ::time1(id1:tc.ID))       +  tc4.str6( time6: TC::time4( id4: tc.ID ) );
en4 = TC::enum4(udt4:EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u4 = TC::udt4(bool4: tc4.bool6(date6:tc.d)   or  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Results
////////////////////////////
if ( b and b2 and b3 and b4 )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Boolean" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:b2 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b3 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b4 );
end if;

if ( ( tr == tr2 ) and ( tr3 == tr4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Timer" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Timer");
end if;

if ( ( i == i2 ) and ( i3 == i4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Integer" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Integer");
  LOG::LogInt( message:"Function Invocation", int:i );
  LOG::LogInt( message:"Bridge Invocation", int:i2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i3 );
  LOG::LogInt( message:"Class Operation Invocation", int:i4 );
end if;

if ( ( r == r2 ) and ( r3 == r4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Real" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Real");
  LOG::LogReal( message:"Function Invocation", real:r );
  LOG::LogReal( message:"Bridge Invocation", real:r2 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r3 );
  LOG::LogReal( message:"Class Operation Invocation", real:r4 );
end if;

if ( ( s == s2 ) and ( s3 == s4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - String" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - String");
  LOG::LogString( message:"Function Invocation", str:s );
  LOG::LogString( message:"Bridge Invocation", str:s2 );
  LOG::LogString( message:"Instance Operation Invocation", str:s3 );
  LOG::LogString( message:"Class Operation Invocation", str:s4 );
end if;

if ( ( en == en2 ) and ( en3 == en4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Enumeration" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Enumeration");
end if;

if ( ( u == u2 ) and ( u3 == u4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - User Data Type" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - User Data Type");
  LOG::LogInt( message:"Function Invocation", int:u );
  LOG::LogInt( message:"Bridge Invocation", int:u2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u3 );
  LOG::LogInt( message:"Class Operation Invocation", int:u4 );
end if;',
	524289,
	1);
INSERT INTO S_SYNC
	VALUES (524303,
	107905,
	'ret1',
	'',
	'select any tc from instances of TC;

return ::bool1( date1: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) );',
	524290,
	1);
INSERT INTO S_SYNC
	VALUES (524304,
	107905,
	'mod_params1',
	'',
	'//////////////////////////////
// Param Assign
//////////////////////////////

select any tc from instances of TC;
select any tc_4 from instances of TC4;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Functions
////////////////////////////
param.b = ::bool1( date1: ::date1( evt1:tc.e ) ) and EE::bool2(date2: tc.date3( evt3:tc.e ) );
param.tr = ::timer1( int1:TC::int4( real4:tc.r )      +    ::int1( real1: tc.real3( str3:tc.s ) ) );
param.i = ::int1(real1: ::real1(str1:tc.s)               +   EE::real2( str2: tc.str3( time3:tc.t ) ) );
param.r = ::real1(str1: EE::str2(time2:tc.t)          +    tc.str3( time3: TC::time4( id4:tc.ID ) ) );
param.s = ::str1( time1: EE::time2( id2: tc.ID ) )  +    tc.str3( time3: TC::time4( id4: tc.ID ) );
param.en = ::enum1(udt1:EE::udt2(bool2:tc.b)   +    tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
param.u = ::udt1(bool1: tc.bool3(date3:tc.d)    and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Bridges
////////////////////////////
param.b2 = EE::bool2(date2: ::date1(evt1:tc.e))  and EE::bool2(date2: tc.date3( evt3:tc.e ) );
param.tr2 =  EE::timer2(int2: TC::int4(real4:tc.r)     +   ::int1( real1: tc.real3( str3:tc.s ) ) );
param.i2 =  REE::int5(real5: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
param.r2 =  EE::real2(str2:  EE::str2(time2:tc.t)      +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
param.s2 =  REE::str5(time5: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
param.en2 = EE::enum2(udt2: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
param.u2 = EE::udt2(bool2: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Instance Operations
////////////////////////////
param.b3 = tc.bool3(date3: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
param.tr3 =  tc.timer3(int3:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
param.i3 =  tc.int3(real3: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
param.r3 =  tc_4.real6(str6: EE::str2(time2:tc.t)        +   tc_4.str6( time6: TC::time4( id4:tc.ID ) ) );
param.s3 =  tc_4.str6(time6: ::time1(id1:tc.ID))       +  tc_4.str6( time6: TC::time4( id4: tc.ID ) );
param.en3 = tc.enum3(udt3: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
param.u3 = tc.udt3(bool3: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Class Operations
////////////////////////////
param.b4 = TC::bool4(date4: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
param.tr4 = TC::timer4(int4:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
param.i4 = TC4::int7(real7: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
param.r4 = TC4::real7(str7: EE::str2(time2:tc.t)        +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
param.s4 = TC::str4(time4:tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
param.en4 = TC::enum4(udt4:EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
param.u4 = TC::udt4(bool4: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Results
////////////////////////////
if ( param.b and param.b2 and param.b3 and param.b4 )
  LOG::LogSuccess( message:"Assign Param - Boolean" );
else
  LOG::LogFailure( message:"Assign Param - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:param.b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:param.b );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:param.b );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:param.b );
end if;

if ( ( param.tr ==  param.tr2 ) and (  param.tr3 ==  param.tr4 ) )
  LOG::LogSuccess( message:"Assign Param - Timer" );
else
  LOG::LogFailure( message:"Assign Param - Timer");
end if;

if ( ( param.i ==  param.i2 ) and (  param.i3 ==  param.i4 ) )
  LOG::LogSuccess( message:"Assign Param - Integer" );
else
  LOG::LogFailure( message:"Assign Param - Integer");
  LOG::LogInt( message:"Function Invocation", int: param.i );
  LOG::LogInt( message:"Bridge Invocation", int: param.i );
  LOG::LogInt( message:"Instance Operation Invocation", int: param.i );
  LOG::LogInt( message:"Class Operation Invocation", int: param.i );
end if;

if ( (  param.r ==  param.r2 ) and (  param.r3 ==  param.r4 ) )
  LOG::LogSuccess( message:"Assign Param - Real" );
else
  LOG::LogFailure( message:"Assign Param - Real");
  LOG::LogReal( message:"Function Invocation", real: param.r );
  LOG::LogReal( message:"Bridge Invocation", real: param.r );
  LOG::LogReal( message:"Instance Operation Invocation", real: param.r );
  LOG::LogReal( message:"Class Operation Invocation", real: param.r );
end if;

if ( (  param.s ==  param.s2 ) and (  param.s3 ==  param.s4 ) )
  LOG::LogSuccess( message:"Assign Param - String" );
else
  LOG::LogFailure( message:"Assign Param - String");
  LOG::LogString( message:"Function Invocation", str: param.s );
  LOG::LogString( message:"Bridge Invocation", str: param.s );
  LOG::LogString( message:"Instance Operation Invocation", str: param.s );
  LOG::LogString( message:"Class Operation Invocation", str: param.s );
end if;

if ( (  param.en ==  param.en2 ) and (  param.en3 ==  param.en4 ) )
  LOG::LogSuccess( message:"Assign Param - Enumeration" );
else
  LOG::LogFailure( message:"Assign Param - Enumeration");
end if;

if ( (  param.u ==  param.u2 ) and (  param.u3 ==  param.u4 ) )
  LOG::LogSuccess( message:"Assign Param - User Data Type" );
else
  LOG::LogFailure( message:"Assign Param - User Data Type");
  LOG::LogInt( message:"Function Invocation", int: param.u );
  LOG::LogInt( message:"Bridge Invocation", int: param.u );
  LOG::LogInt( message:"Instance Operation Invocation", int: param.u );
  LOG::LogInt( message:"Class Operation Invocation", int: param.u );
end if;',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524300,
	524304,
	'b',
	524290,
	1);
INSERT INTO S_SPARM
	VALUES (524301,
	524304,
	'en',
	524307,
	1);
INSERT INTO S_SPARM
	VALUES (524302,
	524304,
	'tr',
	524304,
	1);
INSERT INTO S_SPARM
	VALUES (524303,
	524304,
	'i',
	524291,
	1);
INSERT INTO S_SPARM
	VALUES (524304,
	524304,
	'r',
	524292,
	1);
INSERT INTO S_SPARM
	VALUES (524305,
	524304,
	's',
	524293,
	1);
INSERT INTO S_SPARM
	VALUES (524306,
	524304,
	'u',
	524306,
	1);
INSERT INTO S_SPARM
	VALUES (524307,
	524304,
	'b2',
	524290,
	1);
INSERT INTO S_SPARM
	VALUES (524308,
	524304,
	'tr2',
	524304,
	1);
INSERT INTO S_SPARM
	VALUES (524309,
	524304,
	'i2',
	524291,
	1);
INSERT INTO S_SPARM
	VALUES (524310,
	524304,
	'r2',
	524292,
	1);
INSERT INTO S_SPARM
	VALUES (524311,
	524304,
	's2',
	524293,
	1);
INSERT INTO S_SPARM
	VALUES (524312,
	524304,
	'en2',
	524307,
	1);
INSERT INTO S_SPARM
	VALUES (524313,
	524304,
	'u2',
	524306,
	1);
INSERT INTO S_SPARM
	VALUES (524314,
	524304,
	'b3',
	524290,
	1);
INSERT INTO S_SPARM
	VALUES (524315,
	524304,
	'tr3',
	524304,
	1);
INSERT INTO S_SPARM
	VALUES (524316,
	524304,
	'i3',
	524291,
	1);
INSERT INTO S_SPARM
	VALUES (524317,
	524304,
	'r3',
	524292,
	1);
INSERT INTO S_SPARM
	VALUES (524318,
	524304,
	's3',
	524293,
	1);
INSERT INTO S_SPARM
	VALUES (524319,
	524304,
	'en3',
	524307,
	1);
INSERT INTO S_SPARM
	VALUES (524320,
	524304,
	'u3',
	524306,
	1);
INSERT INTO S_SPARM
	VALUES (524321,
	524304,
	'b4',
	524290,
	1);
INSERT INTO S_SPARM
	VALUES (524322,
	524304,
	'tr4',
	524304,
	1);
INSERT INTO S_SPARM
	VALUES (524323,
	524304,
	'i4',
	524291,
	1);
INSERT INTO S_SPARM
	VALUES (524324,
	524304,
	'r4',
	524292,
	1);
INSERT INTO S_SPARM
	VALUES (524325,
	524304,
	's4',
	524293,
	1);
INSERT INTO S_SPARM
	VALUES (524326,
	524304,
	'en4',
	524307,
	1);
INSERT INTO S_SPARM
	VALUES (524327,
	524304,
	'u4',
	524306,
	1);
INSERT INTO S_SYNC
	VALUES (524305,
	107905,
	'real_invoke1',
	'',
	'select any tc from instances of TC;

select any tc7 from instances of TC4;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Bridges
////////////////////////////
b5 = REE::bool5(date5: ::date1(evt1:tc.e))  and REE::bool5(date5: tc.date3( evt3:tc.e ) );
i5 =  REE::int5(real5: ::real1(str1:tc.s)            +  EE::real2( str2: REE::str5( time5:tc.t ) ) );
r5 =  REE::real5(str5:  REE::str5(time5:tc.t)      +   tc7.str6( time6: TC::time4( id4:tc.ID ) ) );
s5 =  REE::str5(time5: tc.time3(id3:tc.ID))       +  tc7.str6( time6: TC::time4( id4: tc.ID ) );
en5 = REE::enum5(udt5: REE::udt5(bool5:tc.b) +  tc7.udt6( bool6: TC4::bool7( date7:tc.d ) ) );
u5 = REE::udt5(bool5: tc7.bool6(date6:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Instance Operations
////////////////////////////
b6 = tc7.bool6(date6: ::date1(evt1:tc.e))   and REE::bool5(date5: TC::date4( evt4:tc.e ) );
i6 =  tc7.int6(real6: ::real1(str1:tc.s)            +  REE::real5( str5: tc7.str6( time6:tc.t ) ) );
r6 =  tc7.real6(str6: REE::str5(time5:tc.t)        +   tc7.str6( time6: TC::time4( id4:tc.ID ) ) );
s6 =  tc7.str6(time6: TC::time4(id4:tc.ID))       +  tc7.str6( time6: TC::time4( id4: tc.ID ) );
en6 = tc7.enum6(udt6: REE::udt5(bool5:tc.b) +  tc7.udt6( bool6: REE::bool5( date5:tc.d ) ) );
u6 = tc7.udt6(bool6: tc7.bool6(date6:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Class Operations
////////////////////////////
b7 = TC4::bool7(date7: ::date1(evt1:tc.e))   and REE::bool5(date5: tc.date3( evt3:tc.e ) );
i7 = TC4::int7(real7: ::real1(str1:tc.s)            +  REE::real5( str5: tc7.str6( time6:tc.t ) ) );
r7 = TC4::real7(str7: REE::str5(time5:tc.t)        +   tc7.str6( time6: TC::time4( id4:tc.ID ) ) );
s7 = TC4::str7(time7:tc.time3(id3:tc.ID))       +  TC::str4( time4: TC::time4( id4: tc.ID ) );
en7 = TC4::enum7(udt7:REE::udt5(bool5:tc.b) +  tc7.udt6( bool6: TC4::bool7( date7:tc.d ) ) );
u7 = TC4::udt7(bool7: tc7.bool6(date6:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Results
////////////////////////////
if ( b5 and b6 and b7 )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Boolean" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Boolean");
  LOG::LogBoolean( message:"Bridge Invocation", bool:b5 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b6 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b7 );
end if;

if ( ( i5 == i6 ) and ( i6 == i7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Integer" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Integer");
  LOG::LogInt( message:"Bridge Invocation", int:i5 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i6 );
  LOG::LogInt( message:"Class Operation Invocation", int:i7 );
end if;

if ( ( r5 == r6 ) and ( r6 == r7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Real" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Real");
  LOG::LogReal( message:"Bridge Invocation", real:r5 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r6 );
  LOG::LogReal( message:"Class Operation Invocation", real:r7 );
end if;

if ( ( s5 == s6 ) and ( s6 == s7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - String" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - String");
  LOG::LogString( message:"Bridge Invocation", str:s5 );
  LOG::LogString( message:"Instance Operation Invocation", str:s6 );
  LOG::LogString( message:"Class Operation Invocation", str:s7 );
end if;

if ( ( en5 == en6 ) and ( en6 == en7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Enumeration" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Enumeration");
end if;

if ( ( u5 == u6 ) and ( u6 == u7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - User Data Type" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - User Data Type");
  LOG::LogInt( message:"Bridge Invocation", int:u5 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u6 );
  LOG::LogInt( message:"Class Operation Invocation", int:u7 );
end if;',
	524289,
	1);
INSERT INTO S_EE
	VALUES (524289,
	'Time',
	'',
	'TIM',
	107905);
INSERT INTO S_BRG
	VALUES (524289,
	524289,
	'current_date',
	'Returns the current date.',
	1,
	524302,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524290,
	524289,
	'create_date',
	'Creates a date based on input parameters.

Returns a date.',
	1,
	524302,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524328,
	524290,
	'second',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524329,
	524290,
	'minute',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524330,
	524290,
	'hour',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524331,
	524290,
	'day',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524332,
	524290,
	'month',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524333,
	524290,
	'year',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524291,
	524289,
	'get_second',
	'Returns the second contained in <date>.',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524334,
	524291,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524292,
	524289,
	'get_minute',
	'Returns the minute contained in <date>.',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524335,
	524292,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524293,
	524289,
	'get_hour',
	'Returns the hour contained in <date>.',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524336,
	524293,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524294,
	524289,
	'get_day',
	'Returns the day contained in <date>.',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524337,
	524294,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524295,
	524289,
	'get_month',
	'Returns the month contained in <date>.',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524338,
	524295,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524296,
	524289,
	'get_year',
	'Returns the year contained in <date>.',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524339,
	524296,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524297,
	524289,
	'current_clock',
	'Returns the current time.',
	1,
	524303,
	'',
	0);
INSERT INTO S_BRG
	VALUES (524298,
	524289,
	'timer_start',
	'Starts a (one shot) timer to expire in <microseconds>, sending <event_inst> upon expiration.  Note that upon expiration the timer will be deleted.

Returns the instance handle of the non-recurring timer.',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524340,
	524298,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524341,
	524298,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524299,
	524289,
	'timer_start_recurring',
	'Starts a (recurring) timer to expire in <microseconds>, sending <event_inst> upon expiration.  Upon expiration, the timer will be restarted and fire again in <microseconds> and (re)generate the event <event_inst>.

Returns the instance handle of the recurring timer.',
	1,
	524304,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524342,
	524299,
	'microseconds',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524343,
	524299,
	'event_inst',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524300,
	524289,
	'timer_remaining_time',
	'Returns the time remaining (in microseconds) before <timer_inst_ref> expires. If <timer_inst_ref> no longer exists, a zero value will be returned. 

It is important to note that even if it no longer exists, an event may still be in transit (e.g., was generated) from a previous timer expiration.  Respectable OOA must account for this "ships passing" possibility.

Returns the time remaining.
',
	1,
	524291,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524344,
	524300,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BRG
	VALUES (524301,
	524289,
	'timer_reset_time',
	'This method attempts to set <timer_inst_ref> to expire <microseconds>.

It is important to note that an event may still be in transit (e.g., was already generated) from a previous expiration prior to this method invocation.  Respectable OOA must account for this "ships passing" possibility.

Returns TRUE if the timer exists, otherwise FALSE.',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524345,
	524301,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524346,
	524301,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524302,
	524289,
	'timer_add_time',
	'This method attempts to add <microseconds> to the <timer_inst_ref>. 

It is important to note that an event may still be in transit (e.g., was already generated) from a previous expiration prior to this method invocation.  Respectable OOA must account for this "ships passing" possibility.

Returns TRUE if the timer exists, otherwise FALSE.',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524347,
	524302,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_BPARM
	VALUES (524348,
	524302,
	'microseconds',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524303,
	524289,
	'timer_cancel',
	'This method attempts to cancel and delete <timer_inst_ref>.  

It is important to note that an event may still be in transit (e.g., was already generated) from a previous expiration prior to this method invocation.  Respectable OOA must account for this "ships passing" possibility.

Returns TRUE if the timer exists, otherwise FALSE.',
	1,
	524290,
	'',
	0);
INSERT INTO S_BPARM
	VALUES (524349,
	524303,
	'timer_inst_ref',
	524304,
	0);
INSERT INTO S_EE
	VALUES (524290,
	'Architecture',
	'This EE provides an interface between the domain and the run time implementation that supports the execution of the domain.',
	'ARCH',
	107905);
INSERT INTO S_BRG
	VALUES (524304,
	524290,
	'shutdown',
	'This bridge sends a request to the run time implementation to shut down.',
	0,
	524289,
	'control stop;',
	1);
INSERT INTO S_EE
	VALUES (524291,
	'Logging',
	'This EE is responsible for logging test data.

The output will be written to a file that may be used for test verification.',
	'LOG',
	107905);
INSERT INTO S_BRG
	VALUES (524305,
	524291,
	'LogFailure',
	'Log a test failure.

Output fomat:

"Log Failure - <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524350,
	524305,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524306,
	524291,
	'LogInfo',
	'Log some information.

Output fomat:

"Log Info - <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524351,
	524306,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524307,
	524291,
	'LogSuccess',
	'Log a test success.

Output fomat:

"Log Success - <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524352,
	524307,
	'message',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524308,
	524291,
	'LogReal',
	'Log a real value with an optional message.

Output fomat:

"Log Real - <real>: <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524353,
	524308,
	'message',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524354,
	524308,
	'real',
	524292,
	0);
INSERT INTO S_BRG
	VALUES (524309,
	524291,
	'LogDate',
	'Log a date value with an optional message.

Output fomat:

"Log Date - <date>: <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524355,
	524309,
	'message',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524356,
	524309,
	'date',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524310,
	524291,
	'LogTime',
	'Log a timestamp value with an optional message.

Output fomat:

"Log Time - <time>: <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524357,
	524310,
	'message',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524358,
	524310,
	'time',
	524303,
	0);
INSERT INTO S_BRG
	VALUES (524311,
	524291,
	'LogInt',
	'Log an integer value with an optional message.

Output fomat:

"Log Int - <int>: <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524359,
	524311,
	'message',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524360,
	524311,
	'int',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524312,
	524291,
	'LogString',
	'Log a string value with an optional message.

Output fomat:

"Log String - <str>: <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524361,
	524312,
	'message',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524362,
	524312,
	'str',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524313,
	524291,
	'LogBoolean',
	'Log a boolean value with an optional message.

Output fomat:

"Log Boolean - <bool>: <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524363,
	524313,
	'message',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524364,
	524313,
	'bool',
	524290,
	0);
INSERT INTO S_BRG
	VALUES (524314,
	524291,
	'LogUniqueId',
	'Log a unique_id value with an optional message.

Output fomat:

"Log Unique ID - <uid>: <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524365,
	524314,
	'message',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524366,
	524314,
	'uid',
	524294,
	0);
INSERT INTO S_BRG
	VALUES (524315,
	524291,
	'LogArbitraryId',
	'Log an arbitrary_id value with an optional message.

Output fomat:

"Log Arbitrary ID - <aid>: <message>"',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524367,
	524315,
	'message',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524368,
	524315,
	'aid',
	524305,
	0);
INSERT INTO S_EE
	VALUES (524292,
	'EE',
	'',
	'EE',
	107905);
INSERT INTO S_BRG
	VALUES (524316,
	524292,
	'bool2',
	'',
	0,
	524290,
	'select any tc from instances of TC;

if ( param.date2 == tc.d )
  LOG::LogSuccess( message: "EE Bridge bool2 - Date parameter is correct." );
else
  LOG::LogFailure( message: "EE Bridge bool2 - Date parameter is incorrect." );
end if;

return tc.b;',
	1);
INSERT INTO S_BPARM
	VALUES (524369,
	524316,
	'date2',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524317,
	524292,
	'date2',
	'',
	0,
	524302,
	'select any tc from instances of TC;

if ( param.evt2 == tc.e )
  LOG::LogSuccess( message: "EE Bridge date2 - Event parameter is correct." );
else
  LOG::LogFailure( message: "EE Bridge date2 - Event parameter is incorrect." );
end if;

return tc.d;',
	1);
INSERT INTO S_BPARM
	VALUES (524370,
	524317,
	'evt2',
	524299,
	0);
INSERT INTO S_BRG
	VALUES (524318,
	524292,
	'evt2',
	'',
	0,
	524299,
	'select any tc from instances of TC;

if ( param.timer2 == tc.tr )
  LOG::LogSuccess( message: "EE Bridge evt2 - Timer parameter is correct." );
else
  LOG::LogFailure( message: "EE Bridge evt2 - Timer parameter is incorrect." );
end if;

return tc.e;',
	1);
INSERT INTO S_BPARM
	VALUES (524371,
	524318,
	'timer2',
	524304,
	0);
INSERT INTO S_BRG
	VALUES (524319,
	524292,
	'timer2',
	'',
	0,
	524304,
	'select any tc from instances of TC;

if ( ( param.int2 == tc.i ) OR ( param.int2 == ( 2 * tc.i ) ) )
  LOG::LogSuccess( message: "EE Bridge timer2 - Integer parameter is correct." );
else
  LOG::LogFailure( message: "EE Bridge timer2 - Integer parameter is incorrect." );
end if;

return tc.tr;',
	1);
INSERT INTO S_BPARM
	VALUES (524372,
	524319,
	'int2',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524320,
	524292,
	'int2',
	'',
	0,
	524291,
	'select any tc from instances of TC;

if ( ( param.real2 == tc.r ) OR ( ( param.real2 == ( 2 * tc.r ) ) OR ( param.real2 == ( tc.r * tc.r ) ) ) )
  LOG::LogSuccess( message: "EE Bridge int2 - Real parameter is correct." );
else
  LOG::LogFailure( message: "EE Bridge int2 - Real parameter is incorrect." );
end if;

return tc.i;',
	1);
INSERT INTO S_BPARM
	VALUES (524373,
	524320,
	'real2',
	524292,
	0);
INSERT INTO S_BRG
	VALUES (524321,
	524292,
	'real2',
	'',
	0,
	524292,
	'select any tc from instances of TC;

if ( ( param.str2 == tc.s ) OR ( param.str2 == ( tc.s + tc.s ) ) )
  LOG::LogSuccess( message: "EE Bridge real2 - String parameter is correct." );
else
  LOG::LogFailure( message: "EE Bridge real2 - String parameter is incorrect." );
end if;

return tc.r;',
	1);
INSERT INTO S_BPARM
	VALUES (524374,
	524321,
	'str2',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524322,
	524292,
	'str2',
	'',
	0,
	524293,
	'select any tc from instances of TC;

if ( param.time2 == tc.t )
  LOG::LogSuccess( message: "EE Bridge str2 - Timestamp parameter is correct." );
else
  LOG::LogFailure( message: "EE Bridge str2 - Timestamp parameter is incorrect." );
end if;

return tc.s;',
	1);
INSERT INTO S_BPARM
	VALUES (524375,
	524322,
	'time2',
	524303,
	0);
INSERT INTO S_BRG
	VALUES (524323,
	524292,
	'time2',
	'',
	0,
	524303,
	'select any tc from instances of TC;

if ( param.id2 == tc.ID )
  LOG::LogSuccess( message: "EE Bridge time2 - Unique ID parameter is correct." );
else
  LOG::LogFailure( message: "EE Bridge time2 - Unique ID parameter is incorrect." );
end if;

return tc.t;',
	1);
INSERT INTO S_BPARM
	VALUES (524376,
	524323,
	'id2',
	524294,
	0);
INSERT INTO S_BRG
	VALUES (524324,
	524292,
	'id2',
	'',
	0,
	524294,
	'select any tc from instances of TC;

if ( param.enum2 == tc.en )
  LOG::LogSuccess( message: "EE Bridge id2 - Enumeration parameter is correct." );
else
  LOG::LogFailure( message: "EE Bridge id2 - Enumeration parameter is incorrect." );
end if;

return tc.ID;',
	1);
INSERT INTO S_BPARM
	VALUES (524377,
	524324,
	'enum2',
	524307,
	0);
INSERT INTO S_BRG
	VALUES (524325,
	524292,
	'enum2',
	'',
	0,
	524307,
	'select any tc from instances of TC;

if ( ( param.udt2 == tc.u ) OR ( ( param.udt2 == ( 2 * tc.u ) ) OR ( param.udt2 == 1 ) ) )
  LOG::LogSuccess( message: "EE Bridge enum2 - User Data Type parameter is correct." );
else
  LOG::LogFailure( message: "EE Bridge enum2 - User Data Type parameter is incorrect." );
end if;

return tc.en;',
	1);
INSERT INTO S_BPARM
	VALUES (524378,
	524325,
	'udt2',
	524306,
	0);
INSERT INTO S_BRG
	VALUES (524326,
	524292,
	'udt2',
	'',
	0,
	524306,
	'select any tc from instances of TC;

if ( param.bool2 == tc.b )
  LOG::LogSuccess( message: "EE Bridge udt2 - Boolean parameter is correct." );
else
  LOG::LogFailure( message: "EE Bridge udt2 - Boolean parameter is incorrect." );
end if;

return tc.u;',
	1);
INSERT INTO S_BPARM
	VALUES (524379,
	524326,
	'bool2',
	524290,
	0);
INSERT INTO S_BRG
	VALUES (524327,
	524292,
	'single_invoke',
	'',
	0,
	524289,
	'select any tc from instances of TC;

////////////////////////////
// Functions
////////////////////////////
b = ::bool1(date1:tc.d);
d = ::date1(evt1:tc.e);
e = ::evt1(timer1:tc.tr);
tr = ::timer1(int1:tc.i);
i = ::int1(real1:tc.r);
r = ::real1(str1:tc.s);
s = ::str1(time1:tc.t);
t = ::time1(id1:tc.ID);
id = ::id1(enum1:tc.en);
en = ::enum1(udt1:tc.u);
u = ::udt1(bool1:tc.b);

////////////////////////////
// Bridges
////////////////////////////
b2 = EE::bool2(date2:tc.d);
d2 = EE::date2(evt2:tc.e);
e2 =  EE::evt2(timer2:tc.tr);
tr2 =  EE::timer2(int2:tc.i);
i2 =  EE::int2(real2:tc.r);
r2 =  EE::real2(str2:tc.s);
s2 =  EE::str2(time2:tc.t);
t2 =  EE::time2(id2:tc.ID);
id2 =  EE::id2(enum2:tc.en);
en2 = EE::enum2(udt2:tc.u);
u2 = EE::udt2(bool2:tc.b);

////////////////////////////
// Instance Operations
////////////////////////////
b3 = tc.bool3(date3:tc.d);
d3 =  tc.date3(evt3:tc.e);
e3 =  tc.evt3(timer3:tc.tr);
tr3 =  tc.timer3(int3:tc.i);
i3 =  tc.int3(real3:tc.r);
r3 =  tc.real3(str3:tc.s);
s3 =  tc.str3(time3:tc.t);
t3 =  tc.time3(id3:tc.ID);
id3 =  tc.id3(enum3:tc.en);
en3 = tc.enum3(udt3:tc.u);
u3 = tc.udt3(bool3:tc.b);

////////////////////////////
// Class Operations
////////////////////////////
b4 = TC::bool4(date4:tc.d);
d4 = TC::date4(evt4:tc.e);
e4 = TC::evt4(timer4:tc.tr);
tr4 = TC::timer4(int4:tc.i);
i4 = TC::int4(real4:tc.r);
r4 = TC::real4(str4:tc.s);
s4 = TC::str4(time4:tc.t);
t4 = TC::time4(id4:tc.ID);
id4 = TC::id4(enum4:tc.en);
en4 = TC::enum4(udt4:tc.u);
u4 = TC::udt4(bool4:tc.b);

////////////////////////////
// Results
////////////////////////////
if ( b and b2 and b3 and b4 )
  LOG::LogSuccess( message:"Single Invocation - Boolean" );
else
  LOG::LogFailure( message:"Single Invocation - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:b2 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b3 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b4 );
end if;

if ( ( d == d2 ) and ( d3 == d4 ) )
  LOG::LogSuccess( message:"Single Invocation - Date" );
else
  LOG::LogFailure( message:"Single Invocation - Date");
  LOG::LogDate( message:"Function Invocation", date:d );
  LOG::LogDate( message:"Bridge Invocation", date:d2 );
  LOG::LogDate( message:"Instance Operation Invocation", date:d3 );
  LOG::LogDate( message:"Class Operation Invocation", date:d4 );
end if;

if ( ( e == e2 ) and ( e3 == e4 ) )
  LOG::LogSuccess( message:"Single Invocation - Event" );
else
  LOG::LogFailure( message:"Single Invocation - Event");
end if;

if ( ( tr == tr2 ) and ( tr3 == tr4 ) )
  LOG::LogSuccess( message:"Single Invocation - Timer" );
else
  LOG::LogFailure( message:"Single Invocation - Timer");
end if;

if ( ( i == i2 ) and ( i3 == i4 ) )
  LOG::LogSuccess( message:"Single Invocation - Integer" );
else
  LOG::LogFailure( message:"Single Invocation - Integer");
  LOG::LogInt( message:"Function Invocation", int:i );
  LOG::LogInt( message:"Bridge Invocation", int:i2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i3 );
  LOG::LogInt( message:"Class Operation Invocation", int:i4 );
end if;

if ( ( r == r2 ) and ( r3 == r4 ) )
  LOG::LogSuccess( message:"Single Invocation - Real" );
else
  LOG::LogFailure( message:"Single Invocation - Real");
  LOG::LogReal( message:"Function Invocation", real:r );
  LOG::LogReal( message:"Bridge Invocation", real:r2 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r3 );
  LOG::LogReal( message:"Class Operation Invocation", real:r4 );
end if;

if ( ( s == s2 ) and ( s3 == s4 ) )
  LOG::LogSuccess( message:"Single Invocation - String" );
else
  LOG::LogFailure( message:"Single Invocation - String");
  LOG::LogString( message:"Function Invocation", str:s );
  LOG::LogString( message:"Bridge Invocation", str:s2 );
  LOG::LogString( message:"Instance Operation Invocation", str:s3 );
  LOG::LogString( message:"Class Operation Invocation", str:s4 );
end if;

if ( ( t== t2 ) and ( t3 == t4 ) )
  LOG::LogSuccess( message:"Single Invocation - Timestamp" );
else
  LOG::LogFailure( message:"Single Invocation - Timestamp");
  LOG::LogTime( message:"Function Invocation", time:t );
  LOG::LogTime( message:"Bridge Invocation", time:t2 );
  LOG::LogTime( message:"Instance Operation Invocation", time:t3 );
  LOG::LogTime( message:"Class Operation Invocation", time:t4 );
end if;

if ( ( id == id2 ) and ( id3 == id4 ) )
  LOG::LogSuccess( message:"Single Invocation - Unique ID" );
else
  LOG::LogFailure( message:"Single Invocation - Unique ID");
  LOG::LogUniqueId( message:"Function Invocation", uid:id );
  LOG::LogUniqueId( message:"Bridge Invocation", uid:id2 );
  LOG::LogUniqueId( message:"Instance Operation Invocation", uid:id3 );
  LOG::LogUniqueId( message:"Class Operation Invocation", uid:id4 );
end if;

if ( ( en == en2 ) and ( en3 == en4 ) )
  LOG::LogSuccess( message:"Single Invocation - Enumeration" );
else
  LOG::LogFailure( message:"Single Invocation - Enumeration");
end if;

if ( ( u == u2 ) and ( u3 == u4 ) )
  LOG::LogSuccess( message:"Single Invocation - User Data Type" );
else
  LOG::LogFailure( message:"Single Invocation - User Data Type");
  LOG::LogInt( message:"Function Invocation", int:u );
  LOG::LogInt( message:"Bridge Invocation", int:u2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u3 );
  LOG::LogInt( message:"Class Operation Invocation", int:u4 );
end if;',
	1);
INSERT INTO S_BRG
	VALUES (524328,
	524292,
	'multiple_invoke',
	'',
	0,
	524289,
	'select any tc from instances of TC;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Functions
////////////////////////////
b = ::bool1(date1: ::date1(evt1:tc.e));
d = ::date1(evt1:EE::evt2(timer2:tc.tr));
e = ::evt1(timer1: tc.timer3(int3:tc.i));
tr = ::timer1(int1:TC::int4(real4:tc.r));
i = ::int1(real1: ::real1(str1:tc.s));
r = ::real1(str1:  EE::str2(time2:tc.t));
s = ::str1(time1: tc.time3(id3:tc.ID));
t = ::time1(id1: TC::id4(enum4:tc.en));
id = ::id1(enum1: ::enum1(udt1:tc.u));
en = ::enum1(udt1:EE::udt2(bool2:tc.b));
u = ::udt1(bool1: tc.bool3(date3:tc.d));

////////////////////////////
// Bridges
////////////////////////////
b2 = EE::bool2(date2: ::date1(evt1:tc.e));
d2 = EE::date2(evt2:  EE::evt2(timer2:tc.tr));
e2 =  EE::evt2(timer2: tc.timer3(int3:tc.i));
tr2 =  EE::timer2(int2: TC::int4(real4:tc.r));
i2 =  EE::int2(real2: ::real1(str1:tc.s));
r2 =  EE::real2(str2:  EE::str2(time2:tc.t));
s2 =  EE::str2(time2: tc.time3(id3:tc.ID));
t2 =  EE::time2(id2: TC::id4(enum4:tc.en));
id2 =  EE::id2(enum2: ::enum1(udt1:tc.u));
en2 = EE::enum2(udt2: EE::udt2(bool2:tc.b));
u2 = EE::udt2(bool2: tc.bool3(date3:tc.d));

////////////////////////////
// Instance Operations
////////////////////////////
b3 = tc.bool3(date3: ::date1(evt1:tc.e));
d3 =  tc.date3(evt3: EE::evt2(timer2:tc.tr));
e3 =  tc.evt3(timer3:  tc.timer3(int3:tc.i));
tr3 =  tc.timer3(int3:TC::int4(real4:tc.r));
i3 =  tc.int3(real3: ::real1(str1:tc.s));
r3 =  tc.real3(str3: EE::str2(time2:tc.t));
s3 =  tc.str3(time3: tc.time3(id3:tc.ID));
t3 =  tc.time3(id3: TC::id4(enum4:tc.en));
id3 =  tc.id3(enum3: ::enum1(udt1:tc.u));
en3 = tc.enum3(udt3: EE::udt2(bool2:tc.b));
u3 = tc.udt3(bool3: tc.bool3(date3:tc.d));

////////////////////////////
// Class Operations
////////////////////////////
b4 = TC::bool4(date4: ::date1(evt1:tc.e));
d4 = TC::date4(evt4: EE::evt2(timer2:tc.tr));
e4 = TC::evt4(timer4: tc.timer3(int3:tc.i));
tr4 = TC::timer4(int4:TC::int4(real4:tc.r));
i4 = TC::int4(real4: ::real1(str1:tc.s));
r4 = TC::real4(str4: EE::str2(time2:tc.t));
s4 = TC::str4(time4:tc.time3(id3:tc.ID));
t4 = TC::time4(id4:TC::id4(enum4:tc.en));
id4 = TC::id4(enum4: ::enum1(udt1:tc.u));
en4 = TC::enum4(udt4:EE::udt2(bool2:tc.b));
u4 = TC::udt4(bool4: tc.bool3(date3:tc.d));

////////////////////////////
// Results
////////////////////////////
if ( b and b2 and b3 and b4 )
  LOG::LogSuccess( message:"Multiple Invocation - Boolean" );
else
  LOG::LogFailure( message:"Multiple Invocation - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:b2 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b3 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b4 );
end if;

if ( ( d == d2 ) and ( d3 == d4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Date" );
else
  LOG::LogFailure( message:"Multiple Invocation - Date");
  LOG::LogDate( message:"Function Invocation", date:d );
  LOG::LogDate( message:"Bridge Invocation", date:d2 );
  LOG::LogDate( message:"Instance Operation Invocation", date:d3 );
  LOG::LogDate( message:"Class Operation Invocation", date:d4 );
end if;

if ( ( e == e2 ) and ( e3 == e4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Event" );
else
  LOG::LogFailure( message:"Multiple Invocation - Event");
end if;

if ( ( tr == tr2 ) and ( tr3 == tr4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Timer" );
else
  LOG::LogFailure( message:"Multiple Invocation - Timer");
end if;

if ( ( i == i2 ) and ( i3 == i4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Integer" );
else
  LOG::LogFailure( message:"Multiple Invocation - Integer");
  LOG::LogInt( message:"Function Invocation", int:i );
  LOG::LogInt( message:"Bridge Invocation", int:i2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i3 );
  LOG::LogInt( message:"Class Operation Invocation", int:i4 );
end if;

if ( ( r == r2 ) and ( r3 == r4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Real" );
else
  LOG::LogFailure( message:"Multiple Invocation - Real");
  LOG::LogReal( message:"Function Invocation", real:r );
  LOG::LogReal( message:"Bridge Invocation", real:r2 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r3 );
  LOG::LogReal( message:"Class Operation Invocation", real:r4 );
end if;

if ( ( s == s2 ) and ( s3 == s4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - String" );
else
  LOG::LogFailure( message:"Multiple Invocation - String");
  LOG::LogString( message:"Function Invocation", str:s );
  LOG::LogString( message:"Bridge Invocation", str:s2 );
  LOG::LogString( message:"Instance Operation Invocation", str:s3 );
  LOG::LogString( message:"Class Operation Invocation", str:s4 );
end if;

if ( ( t== t2 ) and ( t3 == t4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Timestamp" );
else
  LOG::LogFailure( message:"Multiple Invocation - Timestamp");
  LOG::LogTime( message:"Function Invocation", time:t );
  LOG::LogTime( message:"Bridge Invocation", time:t2 );
  LOG::LogTime( message:"Instance Operation Invocation", time:t3 );
  LOG::LogTime( message:"Class Operation Invocation", time:t4 );
end if;

if ( ( id == id2 ) and ( id3 == id4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Unique ID" );
else
  LOG::LogFailure( message:"Multiple Invocation - Unique ID");
  LOG::LogUniqueId( message:"Function Invocation", uid:id );
  LOG::LogUniqueId( message:"Bridge Invocation", uid:id2 );
  LOG::LogUniqueId( message:"Instance Operation Invocation", uid:id3 );
  LOG::LogUniqueId( message:"Class Operation Invocation", uid:id4 );
end if;

if ( ( en == en2 ) and ( en3 == en4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Enumeration" );
else
  LOG::LogFailure( message:"Multiple Invocation - Enumeration");
end if;

if ( ( u == u2 ) and ( u3 == u4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - User Data Type" );
else
  LOG::LogFailure( message:"Multiple Invocation - User Data Type");
  LOG::LogInt( message:"Function Invocation", int:u );
  LOG::LogInt( message:"Bridge Invocation", int:u2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u3 );
  LOG::LogInt( message:"Class Operation Invocation", int:u4 );
end if;',
	1);
INSERT INTO S_BRG
	VALUES (524329,
	524292,
	'multiple_invoke_w_expressions',
	'',
	0,
	524289,
	'select any tc from instances of TC;


select any tc4 from instances of TC4;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Functions
////////////////////////////
b = ::bool1( date1: ::date1( evt1:tc.e ) ) and REE::bool5(date5: tc.date3( evt3:tc.e ) );
tr = ::timer1( int1:TC::int4( real4:tc.r )      +    ::int1( real1: tc.real3( str3:tc.s ) ) );
i = ::int1(real1: ::real1(str1:tc.s)               +   EE::real2( str2: tc.str3( time3:tc.t ) ) );
r = ::real1(str1: EE::str2(time2:tc.t)          +    tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s = ::str1( time1: EE::time2( id2: tc.ID ) )  +    tc.str3( time3: TC::time4( id4: tc.ID ) );
en = ::enum1(udt1:EE::udt2(bool2:tc.b)   +    tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u = ::udt1(bool1: tc.bool3(date3:tc.d)    and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Bridges
////////////////////////////
b2 = EE::bool2(date2: ::date1(evt1:tc.e))  and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tr2 =  EE::timer2(int2: TC::int4(real4:tc.r)     +   ::int1( real1: tc.real3( str3:tc.s ) ) );
i2 =  EE::int2(real2: ::real1(str1:tc.s)            +  REE::real5( str5: tc.str3( time3:tc.t ) ) );
r2 =  EE::real2(str2:  EE::str2(time2:tc.t)      +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s2 =  EE::str2(time2: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
en2 = EE::enum2(udt2: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u2 = EE::udt2(bool2: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Instance Operations
////////////////////////////
b3 = tc.bool3(date3: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tr3 =  tc.timer3(int3:TC4::int7(real7:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
i3 =  tc.int3(real3: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
r3 =  tc.real3(str3: EE::str2(time2:tc.t)        +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s3 =  tc.str3(time3: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
en3 = tc.enum3(udt3: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u3 = tc.udt3(bool3: tc.bool3(date3:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Class Operations
////////////////////////////
b4 = TC::bool4(date4: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tr4 = TC::timer4(int4:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
i4 = TC::int4(real4: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
r4 = TC::real4(str4: REE::str5(time5:tc.t)        +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s4 = TC::str4(time4:tc.time3(id3:tc.ID))       +  tc4.str6( time6: TC::time4( id4: tc.ID ) );
en4 = TC::enum4(udt4:EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u4 = TC::udt4(bool4: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Results
////////////////////////////
if ( b and b2 and b3 and b4 )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Boolean" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:b2 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b3 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b4 );
end if;

if ( ( tr == tr2 ) and ( tr3 == tr4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Timer" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Timer");
end if;

if ( ( i == i2 ) and ( i3 == i4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Integer" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Integer");
  LOG::LogInt( message:"Function Invocation", int:i );
  LOG::LogInt( message:"Bridge Invocation", int:i2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i3 );
  LOG::LogInt( message:"Class Operation Invocation", int:i4 );
end if;

if ( ( r == r2 ) and ( r3 == r4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Real" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Real");
  LOG::LogReal( message:"Function Invocation", real:r );
  LOG::LogReal( message:"Bridge Invocation", real:r2 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r3 );
  LOG::LogReal( message:"Class Operation Invocation", real:r4 );
end if;

if ( ( s == s2 ) and ( s3 == s4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - String" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - String");
  LOG::LogString( message:"Function Invocation", str:s );
  LOG::LogString( message:"Bridge Invocation", str:s2 );
  LOG::LogString( message:"Instance Operation Invocation", str:s3 );
  LOG::LogString( message:"Class Operation Invocation", str:s4 );
end if;

if ( ( en == en2 ) and ( en3 == en4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Enumeration" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Enumeration");
end if;

if ( ( u == u2 ) and ( u3 == u4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - User Data Type" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - User Data Type");
  LOG::LogInt( message:"Function Invocation", int:u );
  LOG::LogInt( message:"Bridge Invocation", int:u2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u3 );
  LOG::LogInt( message:"Class Operation Invocation", int:u4 );
end if;',
	1);
INSERT INTO S_BRG
	VALUES (524330,
	524292,
	'ret2',
	'',
	0,
	524290,
	'select any tc from instances of TC;

return ::bool1( date1: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) );',
	1);
INSERT INTO S_BRG
	VALUES (524331,
	524292,
	'mod_params2',
	'',
	0,
	524289,
	'//////////////////////////////
// Param Assign
//////////////////////////////

select any tc from instances of TC;
select any tc_4 from instances of TC4;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Functions
////////////////////////////
param.b = ::bool1( date1: ::date1( evt1:tc.e ) ) and EE::bool2(date2: tc.date3( evt3:tc.e ) );
param.tr = ::timer1( int1:TC::int4( real4:tc.r )      +    ::int1( real1: tc.real3( str3:tc.s ) ) );
param.i = ::int1(real1: ::real1(str1:tc.s)               +   EE::real2( str2: tc.str3( time3:tc.t ) ) );
param.r = ::real1(str1: EE::str2(time2:tc.t)          +    tc.str3( time3: TC::time4( id4:tc.ID ) ) );
param.s = ::str1( time1: EE::time2( id2: tc.ID ) )  +    tc.str3( time3: TC::time4( id4: tc.ID ) );
param.en = ::enum1(udt1:EE::udt2(bool2:tc.b)   +    tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
param.u = ::udt1(bool1: tc.bool3(date3:tc.d)    and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Bridges
////////////////////////////
param.b2 = EE::bool2(date2: ::date1(evt1:tc.e))  and EE::bool2(date2: tc.date3( evt3:tc.e ) );
param.tr2 =  EE::timer2(int2: TC::int4(real4:tc.r)     +   ::int1( real1: tc.real3( str3:tc.s ) ) );
param.i2 =  EE::int2(real2: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
param.r2 =  REE::real5(str5:  EE::str2(time2:tc.t)      +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
param.s2 =  REE::str5(time5: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
param.en2 = EE::enum2(udt2: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
param.u2 = EE::udt2(bool2: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Instance Operations
////////////////////////////
param.b3 = tc.bool3(date3: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
param.tr3 =  tc.timer3(int3:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
param.i3 =  tc_4.int6(real6: ::real1(str1:tc.s)            +  EE::real2( str2: tc_4.str6( time6:tc.t ) ) );
param.r3 =  tc_4.real6(str6: EE::str2(time2:tc.t)        +   tc_4.str6( time6: TC::time4( id4:tc.ID ) ) );
param.s3 =  tc.str3(time3: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
param.en3 = tc.enum3(udt3: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
param.u3 = tc.udt3(bool3: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Class Operations
////////////////////////////
param.b4 = TC::bool4(date4: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
param.tr4 = TC::timer4(int4:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
param.i4 = TC4::int7(real7: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
param.r4 = TC::real4(str4: EE::str2(time2:tc.t)        +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
param.s4 = TC4::str7(time7:tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
param.en4 = TC::enum4(udt4:EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
param.u4 = TC::udt4(bool4: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Results
////////////////////////////
if ( param.b and param.b2 and param.b3 and param.b4 )
  LOG::LogSuccess( message:"Assign Param - Boolean" );
else
  LOG::LogFailure( message:"Assign Param - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:param.b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:param.b );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:param.b );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:param.b );
end if;

if ( ( param.tr ==  param.tr2 ) and (  param.tr3 ==  param.tr4 ) )
  LOG::LogSuccess( message:"Assign Param - Timer" );
else
  LOG::LogFailure( message:"Assign Param - Timer");
end if;

if ( ( param.i ==  param.i2 ) and (  param.i3 ==  param.i4 ) )
  LOG::LogSuccess( message:"Assign Param - Integer" );
else
  LOG::LogFailure( message:"Assign Param - Integer");
  LOG::LogInt( message:"Function Invocation", int: param.i );
  LOG::LogInt( message:"Bridge Invocation", int: param.i );
  LOG::LogInt( message:"Instance Operation Invocation", int: param.i );
  LOG::LogInt( message:"Class Operation Invocation", int: param.i );
end if;

if ( (  param.r ==  param.r2 ) and (  param.r3 ==  param.r4 ) )
  LOG::LogSuccess( message:"Assign Param - Real" );
else
  LOG::LogFailure( message:"Assign Param - Real");
  LOG::LogReal( message:"Function Invocation", real: param.r );
  LOG::LogReal( message:"Bridge Invocation", real: param.r );
  LOG::LogReal( message:"Instance Operation Invocation", real: param.r );
  LOG::LogReal( message:"Class Operation Invocation", real: param.r );
end if;

if ( (  param.s ==  param.s2 ) and (  param.s3 ==  param.s4 ) )
  LOG::LogSuccess( message:"Assign Param - String" );
else
  LOG::LogFailure( message:"Assign Param - String");
  LOG::LogString( message:"Function Invocation", str: param.s );
  LOG::LogString( message:"Bridge Invocation", str: param.s );
  LOG::LogString( message:"Instance Operation Invocation", str: param.s );
  LOG::LogString( message:"Class Operation Invocation", str: param.s );
end if;

if ( (  param.en ==  param.en2 ) and (  param.en3 ==  param.en4 ) )
  LOG::LogSuccess( message:"Assign Param - Enumeration" );
else
  LOG::LogFailure( message:"Assign Param - Enumeration");
end if;

if ( (  param.u ==  param.u2 ) and (  param.u3 ==  param.u4 ) )
  LOG::LogSuccess( message:"Assign Param - User Data Type" );
else
  LOG::LogFailure( message:"Assign Param - User Data Type");
  LOG::LogInt( message:"Function Invocation", int: param.u );
  LOG::LogInt( message:"Bridge Invocation", int: param.u );
  LOG::LogInt( message:"Instance Operation Invocation", int: param.u );
  LOG::LogInt( message:"Class Operation Invocation", int: param.u );
end if;',
	1);
INSERT INTO S_BPARM
	VALUES (524380,
	524331,
	'b',
	524290,
	1);
INSERT INTO S_BPARM
	VALUES (524381,
	524331,
	'tr',
	524304,
	1);
INSERT INTO S_BPARM
	VALUES (524382,
	524331,
	'i',
	524291,
	1);
INSERT INTO S_BPARM
	VALUES (524383,
	524331,
	'r',
	524292,
	1);
INSERT INTO S_BPARM
	VALUES (524384,
	524331,
	's',
	524293,
	1);
INSERT INTO S_BPARM
	VALUES (524385,
	524331,
	'en',
	524307,
	1);
INSERT INTO S_BPARM
	VALUES (524386,
	524331,
	'u',
	524306,
	1);
INSERT INTO S_BPARM
	VALUES (524387,
	524331,
	'b2',
	524290,
	1);
INSERT INTO S_BPARM
	VALUES (524388,
	524331,
	'tr2',
	524304,
	1);
INSERT INTO S_BPARM
	VALUES (524389,
	524331,
	'i2',
	524291,
	1);
INSERT INTO S_BPARM
	VALUES (524390,
	524331,
	'r2',
	524292,
	1);
INSERT INTO S_BPARM
	VALUES (524391,
	524331,
	's2',
	524293,
	1);
INSERT INTO S_BPARM
	VALUES (524392,
	524331,
	'en2',
	524307,
	1);
INSERT INTO S_BPARM
	VALUES (524393,
	524331,
	'u2',
	524306,
	1);
INSERT INTO S_BPARM
	VALUES (524394,
	524331,
	'b3',
	524290,
	1);
INSERT INTO S_BPARM
	VALUES (524395,
	524331,
	'tr3',
	524304,
	1);
INSERT INTO S_BPARM
	VALUES (524396,
	524331,
	'i3',
	524291,
	1);
INSERT INTO S_BPARM
	VALUES (524397,
	524331,
	'r3',
	524292,
	1);
INSERT INTO S_BPARM
	VALUES (524398,
	524331,
	's3',
	524293,
	1);
INSERT INTO S_BPARM
	VALUES (524399,
	524331,
	'en3',
	524307,
	1);
INSERT INTO S_BPARM
	VALUES (524400,
	524331,
	'u3',
	524306,
	1);
INSERT INTO S_BPARM
	VALUES (524401,
	524331,
	'b4',
	524290,
	1);
INSERT INTO S_BPARM
	VALUES (524402,
	524331,
	'tr4',
	524304,
	1);
INSERT INTO S_BPARM
	VALUES (524403,
	524331,
	'i4',
	524291,
	1);
INSERT INTO S_BPARM
	VALUES (524404,
	524331,
	'r4',
	524292,
	1);
INSERT INTO S_BPARM
	VALUES (524405,
	524331,
	's4',
	524293,
	1);
INSERT INTO S_BPARM
	VALUES (524406,
	524331,
	'en4',
	524307,
	1);
INSERT INTO S_BPARM
	VALUES (524407,
	524331,
	'u4',
	524306,
	1);
INSERT INTO S_BRG
	VALUES (524332,
	524292,
	'real_invoke2',
	'',
	0,
	524289,
	'select any tc from instances of TC;

select any tc7 from instances of TC4;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Bridges
////////////////////////////
b5 = REE::bool5(date5: ::date1(evt1:tc.e))  and REE::bool5(date5: tc.date3( evt3:tc.e ) );
i5 =  REE::int5(real5: ::real1(str1:tc.s)            +  EE::real2( str2: REE::str5( time5:tc.t ) ) );
r5 =  REE::real5(str5:  REE::str5(time5:tc.t)      +   tc7.str6( time6: TC::time4( id4:tc.ID ) ) );
s5 =  REE::str5(time5: tc.time3(id3:tc.ID))       +  tc7.str6( time6: TC::time4( id4: tc.ID ) );
en5 = REE::enum5(udt5: REE::udt5(bool5:tc.b) +  tc7.udt6( bool6: TC4::bool7( date7:tc.d ) ) );
u5 = REE::udt5(bool5: tc7.bool6(date6:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Instance Operations
////////////////////////////
b6 = tc7.bool6(date6: ::date1(evt1:tc.e))   and REE::bool5(date5: TC::date4( evt4:tc.e ) );
i6 =  tc7.int6(real6: ::real1(str1:tc.s)            +  REE::real5( str5: tc7.str6( time6:tc.t ) ) );
r6 =  tc7.real6(str6: REE::str5(time5:tc.t)        +   tc7.str6( time6: TC::time4( id4:tc.ID ) ) );
s6 =  tc7.str6(time6: TC::time4(id4:tc.ID))       +  tc7.str6( time6: TC::time4( id4: tc.ID ) );
en6 = tc7.enum6(udt6: REE::udt5(bool5:tc.b) +  tc7.udt6( bool6: REE::bool5( date5:tc.d ) ) );
u6 = tc7.udt6(bool6: tc7.bool6(date6:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Class Operations
////////////////////////////
b7 = TC4::bool7(date7: ::date1(evt1:tc.e))   and REE::bool5(date5: tc.date3( evt3:tc.e ) );
i7 = TC4::int7(real7: ::real1(str1:tc.s)            +  REE::real5( str5: tc7.str6( time6:tc.t ) ) );
r7 = TC4::real7(str7: REE::str5(time5:tc.t)        +   tc7.str6( time6: TC::time4( id4:tc.ID ) ) );
s7 = TC4::str7(time7:tc.time3(id3:tc.ID))       +  TC::str4( time4: TC::time4( id4: tc.ID ) );
en7 = TC4::enum7(udt7:REE::udt5(bool5:tc.b) +  tc7.udt6( bool6: TC4::bool7( date7:tc.d ) ) );
u7 = TC4::udt7(bool7: tc7.bool6(date6:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Results
////////////////////////////
if ( b5 and b6 and b7 )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Boolean" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Boolean");
  LOG::LogBoolean( message:"Bridge Invocation", bool:b5 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b6 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b7 );
end if;

if ( ( i5 == i6 ) and ( i6 == i7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Integer" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Integer");
  LOG::LogInt( message:"Bridge Invocation", int:i5 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i6 );
  LOG::LogInt( message:"Class Operation Invocation", int:i7 );
end if;

if ( ( r5 == r6 ) and ( r6 == r7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Real" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Real");
  LOG::LogReal( message:"Bridge Invocation", real:r5 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r6 );
  LOG::LogReal( message:"Class Operation Invocation", real:r7 );
end if;

if ( ( s5 == s6 ) and ( s6 == s7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - String" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - String");
  LOG::LogString( message:"Bridge Invocation", str:s5 );
  LOG::LogString( message:"Instance Operation Invocation", str:s6 );
  LOG::LogString( message:"Class Operation Invocation", str:s7 );
end if;

if ( ( en5 == en6 ) and ( en6 == en7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Enumeration" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Enumeration");
end if;

if ( ( u5 == u6 ) and ( u6 == u7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - User Data Type" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - User Data Type");
  LOG::LogInt( message:"Bridge Invocation", int:u5 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u6 );
  LOG::LogInt( message:"Class Operation Invocation", int:u7 );
end if;',
	1);
INSERT INTO S_BRG
	VALUES (524333,
	524292,
	'ret5',
	'',
	0,
	524290,
	'select any tc from instances of TC;

return REE::bool5( date5: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) );',
	1);
INSERT INTO S_EE
	VALUES (524293,
	'Realized EE',
	'',
	'REE',
	107905);
INSERT INTO S_BRG
	VALUES (524334,
	524293,
	'bool5',
	'',
	0,
	524290,
	'return false;',
	1);
INSERT INTO S_BPARM
	VALUES (524408,
	524334,
	'date5',
	524302,
	0);
INSERT INTO S_BRG
	VALUES (524335,
	524293,
	'int5',
	'',
	0,
	524291,
	'return -1;',
	1);
INSERT INTO S_BPARM
	VALUES (524409,
	524335,
	'real5',
	524292,
	0);
INSERT INTO S_BRG
	VALUES (524336,
	524293,
	'real5',
	'',
	0,
	524292,
	'return -1.0;',
	1);
INSERT INTO S_BPARM
	VALUES (524410,
	524336,
	'str5',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524337,
	524293,
	'str5',
	'',
	0,
	524293,
	'return "Failure";',
	1);
INSERT INTO S_BPARM
	VALUES (524411,
	524337,
	'time5',
	524303,
	0);
INSERT INTO S_BRG
	VALUES (524338,
	524293,
	'udt5',
	'',
	0,
	524306,
	'return -1;',
	1);
INSERT INTO S_BPARM
	VALUES (524412,
	524338,
	'bool5',
	524290,
	0);
INSERT INTO S_BRG
	VALUES (524339,
	524293,
	'enum5',
	'',
	0,
	524307,
	'return enum::blue;',
	1);
INSERT INTO S_BPARM
	VALUES (524413,
	524339,
	'udt5',
	524306,
	0);
INSERT INTO GD_MD
	VALUES (524289,
	1,
	107905,
	1,
	1,
	0,
	1,
	1,
	0,
	12,
	1823,
	4174,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (524298,
	524289,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524298,
	2144,
	1392,
	2304,
	1488);
INSERT INTO GD_GE
	VALUES (524293,
	524289,
	524289,
	12);
INSERT INTO GD_SHP
	VALUES (524293,
	1936,
	1536,
	2096,
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
	2304,
	1632);
INSERT INTO GD_GE
	VALUES (524295,
	524289,
	524291,
	12);
INSERT INTO GD_SHP
	VALUES (524295,
	2352,
	1536,
	2512,
	1632);
INSERT INTO GD_GE
	VALUES (524296,
	524289,
	524292,
	12);
INSERT INTO GD_SHP
	VALUES (524296,
	1936,
	1392,
	2096,
	1488);
INSERT INTO GD_GE
	VALUES (524297,
	524289,
	524293,
	12);
INSERT INTO GD_SHP
	VALUES (524297,
	2352,
	1392,
	2512,
	1488);
INSERT INTO S_SS
	VALUES (1048578,
	'G_ALL_nested_invoke',
	'Rename this subsystem to something appropriate for your purposes.',
	'GANI',
	1,
	107905,
	1048578);
INSERT INTO O_OBJ
	VALUES (1048577,
	'Initialization',
	1,
	'INIT',
	'This class is used to create any pre-existing instances necessary for the execution of the domain.  It also generates the creation event to the driver to begin the test(s).  

This class is not translated the same as other classes by the architecture.  Events other than the creation event inside of the initialization class are not allowed.  Any instances created or events generated from its state chart are processed before the dispatcher is started.',
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
	'Init_ID',
	'',
	'',
	'Init_ID',
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
	'Init',
	0,
	'',
	'INIT1',
	'');
INSERT INTO SM_STATE
	VALUES (1572865,
	1572867,
	0,
	'Initialize',
	1,
	0);
INSERT INTO SM_EIGN
	VALUES (1572865,
	1572865,
	1572867,
	0,
	'');
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
	'// Insert initialization action language here.

create object instance tc of TC;
tc.b = true;
tc.d = TIM::current_date();
tc.en = enum::red;
create event instance t_e of BC1 to BC creator;
tc.tr = TIM::timer_start( event_inst:t_e, microseconds:3600000000 );
tc.i = 24;
tc.r = 4.11;
tc.s = "Hello World!";
tc.t = TIM::current_clock();
tc.u = 42;

create object instance tc2 of TC2;
tc2.times_around = 0;
create object instance tc3 of TC3;
create object instance tc3s of TC3S;

relate tc to tc2 across R1;
relate tc to tc3 across R2;
relate tc3 to tc3s across R3;

create object instance tc5 of TC5;

//////////////////////////////
// Attribute Assign
//////////////////////////////

create object instance tc4 of TC4;
create object instance tc_2 of TC;
create object instance tc_3 of TC;
create object instance tc_4 of TC;
create object instance tc_5 of TC;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Functions
////////////////////////////
tc_2.b = ::bool1( date1: ::date1( evt1:tc.e ) ) and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tc_2.tr = ::timer1( int1:TC::int4( real4:tc.r )      +    ::int1( real1: tc.real3( str3:tc.s ) ) );
tc_2.i = ::int1(real1: ::real1(str1:tc.s)               +   EE::real2( str2: tc.str3( time3:tc.t ) ) );
tc_2.r = ::real1(str1: EE::str2(time2:tc.t)          +    tc.str3( time3: TC::time4( id4:tc.ID ) ) );
tc_2.s = ::str1( time1: EE::time2( id2: tc.ID ) )  +    tc.str3( time3: TC::time4( id4: tc.ID ) );
tc_2.en = ::enum1(udt1:EE::udt2(bool2:tc.b)   +    tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
tc_2.u = ::udt1(bool1: tc.bool3(date3:tc.d)    and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Bridges
////////////////////////////
tc_3.b = EE::bool2(date2: ::date1(evt1:tc.e))  and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tc_3.tr =  EE::timer2(int2: TC::int4(real4:tc.r)     +   ::int1( real1: tc.real3( str3:tc.s ) ) );
tc_3.i =  REE::int5(real5: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
tc_3.r =  REE::real5(str5:  EE::str2(time2:tc.t)      +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
tc_3.s =  EE::str2(time2: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
tc_3.en = EE::enum2(udt2: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
tc_3.u = EE::udt2(bool2: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Instance Operations
////////////////////////////
tc_4.b = tc.bool3(date3: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tc_4.tr =  tc.timer3(int3:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
tc_4.i =  tc4.int6(real6: ::real1(str1:tc.s)            +  EE::real2( str2: tc4.str6( time6:tc.t ) ) );
tc_4.r =  tc4.real6(str6: EE::str2(time2:tc.t)        +   tc4.str6( time6: TC::time4( id4:tc.ID ) ) );
tc_4.s =  tc.str3(time3: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
tc_4.en = tc.enum3(udt3: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
tc_4.u = tc.udt3(bool3: tc5.bool8(date8:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Class Operations
////////////////////////////
tc_5.b = TC::bool4(date4: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tc_5.tr = TC::timer4(int4:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
tc_5.i = TC4::int7(real7: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
tc_5.r = TC4::real7(str7: EE::str2(time2:tc.t)        +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
tc_5.s = TC::str4(time4:tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
tc_5.en = TC::enum4(udt4:EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
tc_5.u = TC::udt4(bool4: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Results
////////////////////////////
if ( tc_2.b and tc_3.b and tc_4.b and tc_5.b )
  LOG::LogSuccess( message:"Init State - Boolean" );
else
  LOG::LogFailure( message:"Init State - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:tc_2.b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:tc_3.b );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:tc_4.b );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:tc_5.b );
end if;

if ( ( tc_2.tr ==  tc_3.tr ) and (  tc_4.tr ==  tc_5.tr ) )
  LOG::LogSuccess( message:"Init State - Timer" );
else
  LOG::LogFailure( message:"Init State - Timer");
end if;

if ( ( tc_2.i ==  tc_3.i ) and (  tc_4.i ==  tc_5.i ) )
  LOG::LogSuccess( message:"Init State - Integer" );
else
  LOG::LogFailure( message:"Init State - Integer");
  LOG::LogInt( message:"Function Invocation", int: tc_2.i );
  LOG::LogInt( message:"Bridge Invocation", int: tc_3.i );
  LOG::LogInt( message:"Instance Operation Invocation", int: tc_4.i );
  LOG::LogInt( message:"Class Operation Invocation", int: tc_5.i );
end if;

if ( (  tc_2.r ==  tc_3.r ) and (  tc_4.r ==  tc_5.r ) )
  LOG::LogSuccess( message:"Init State - Real" );
else
  LOG::LogFailure( message:"Init State - Real");
  LOG::LogReal( message:"Function Invocation", real: tc_2.r );
  LOG::LogReal( message:"Bridge Invocation", real: tc_3.r );
  LOG::LogReal( message:"Instance Operation Invocation", real: tc_4.r );
  LOG::LogReal( message:"Class Operation Invocation", real: tc_5.r );
end if;

if ( (  tc_2.s ==  tc_3.s ) and (  tc_4.s ==  tc_5.s ) )
  LOG::LogSuccess( message:"Init State - String" );
else
  LOG::LogFailure( message:"Init State - String");
  LOG::LogString( message:"Function Invocation", str: tc_2.s );
  LOG::LogString( message:"Bridge Invocation", str: tc_3.s );
  LOG::LogString( message:"Instance Operation Invocation", str: tc_4.s );
  LOG::LogString( message:"Class Operation Invocation", str: tc_5.s );
end if;

if ( (  tc_2.en ==  tc_3.en ) and (  tc_4.en ==  tc_5.en ) )
  LOG::LogSuccess( message:"Init State - Enumeration" );
else
  LOG::LogFailure( message:"Init State - Enumeration");
end if;

if ( (  tc_2.u ==  tc_3.u ) and (  tc_4.u ==  tc_5.u ) )
  LOG::LogSuccess( message:"Init State - User Data Type" );
else
  LOG::LogFailure( message:"Init State - User Data Type");
  LOG::LogInt( message:"Function Invocation", int: tc_2.u );
  LOG::LogInt( message:"Bridge Invocation", int: tc_3.u );
  LOG::LogInt( message:"Instance Operation Invocation", int: tc_4.u );
  LOG::LogInt( message:"Class Operation Invocation", int: tc_5.u );
end if;

delete object instance tc4;
delete object instance tc_2;
delete object instance tc_3;
delete object instance tc_4;
delete object instance tc_5;

generate DRV1 to DRV creator;',
	'');
INSERT INTO SM_CRTXN
	VALUES (1572865,
	1572867,
	1572865,
	1572865);
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
	1615,
	4278,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (1572866,
	1572865,
	1572865,
	41);
INSERT INTO GD_SHP
	VALUES (1572866,
	1856,
	1376,
	2176,
	1520);
INSERT INTO GD_GE
	VALUES (1572867,
	1572865,
	1572865,
	42);
INSERT INTO GD_CON
	VALUES (1572867,
	1572866,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (1572867,
	0,
	0,
	0,
	0,
	0,
	0,
	1915,
	1321,
	1978,
	1345,
	-32,
	-5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572868,
	1572867,
	2016,
	1376,
	2016,
	1296,
	0);
INSERT INTO O_OBJ
	VALUES (1048578,
	'Driver',
	2,
	'DRV',
	'The Driver class is what runs the test(s).',
	1048578);
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
	'Driver_ID',
	'',
	'',
	'Driver_ID',
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
	VALUES (1048579,
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
	VALUES (2097153,
	2097156,
	'bool5',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (2097154,
	2097156,
	'date5',
	'',
	524302);
INSERT INTO SM_EVTDI
	VALUES (2097155,
	2097156,
	'enum5',
	'',
	524307);
INSERT INTO SM_EVTDI
	VALUES (2097156,
	2097156,
	'udt5',
	'',
	524306);
INSERT INTO SM_EVTDI
	VALUES (2097157,
	2097156,
	'int5',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (2097158,
	2097156,
	'real5',
	'',
	524292);
INSERT INTO SM_EVTDI
	VALUES (2097159,
	2097156,
	'str5',
	'',
	524293);
INSERT INTO SM_EVTDI
	VALUES (2097160,
	2097156,
	'evt5',
	'',
	524299);
INSERT INTO SM_EVTDI
	VALUES (2097161,
	2097156,
	'timer5',
	'',
	524304);
INSERT INTO SM_EVTDI
	VALUES (2097162,
	2097156,
	'time5',
	'',
	524303);
INSERT INTO SM_EVTDI
	VALUES (2097163,
	2097156,
	'id5',
	'',
	524294);
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
	'begin',
	0,
	'',
	'DRV1',
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
	'end',
	0,
	'',
	'DRV2',
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
	'next',
	0,
	'',
	'DRV3',
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
	'',
	0,
	'',
	'DRV4',
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
	'',
	0,
	'',
	'DRV5',
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
	'',
	0,
	'',
	'DRV6',
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
	'',
	0,
	'',
	'DRV7',
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
	'',
	0,
	'',
	'DRV8',
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
	'',
	0,
	'',
	'DRV9',
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
	'',
	0,
	'',
	'DRV10',
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
	'',
	0,
	'',
	'DRV11',
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
	'',
	0,
	'',
	'DRV12',
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
	'',
	0,
	'',
	'DRV13',
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
	'',
	0,
	'',
	'DRV14',
	'');
INSERT INTO SM_STATE
	VALUES (2097153,
	2097156,
	0,
	'Begin Test',
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
	VALUES (2097153,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097153,
	2097165,
	2097156,
	0);
INSERT INTO SM_EIGN
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
	'// Start the test.
// Insert additional states between ''Begin Test'' and ''End Test''.

// The following line exists so that this model will execute with no further manipulation.
generate DRV3 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097154,
	2097156,
	0,
	'End Test',
	2,
	1);
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
	'// End the test.

select any tc from instances of TC;
b = TIM::timer_cancel( timer_inst_ref: tc.tr );

ARCH::shutdown();',
	'');
INSERT INTO SM_STATE
	VALUES (2097155,
	2097156,
	0,
	'Single Invocation',
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
INSERT INTO SM_SEME
	VALUES (2097155,
	2097155,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097155,
	2097156,
	2097156,
	0,
	'');
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
	'select any tc from instances of TC;

////////////////////////////
// Functions
////////////////////////////
b = ::bool1(date1:tc.d);
d = ::date1(evt1:tc.e);
e = ::evt1(timer1:tc.tr);
tr = ::timer1(int1:tc.i);
i = ::int1(real1:tc.r);
r = ::real1(str1:tc.s);
s = ::str1(time1:tc.t);
t = ::time1(id1:tc.ID);
id = ::id1(enum1:tc.en);
en = ::enum1(udt1:tc.u);
u = ::udt1(bool1:tc.b);

////////////////////////////
// Bridges
////////////////////////////
b2 = EE::bool2(date2:tc.d);
d2 = EE::date2(evt2:tc.e);
e2 =  EE::evt2(timer2:tc.tr);
tr2 =  EE::timer2(int2:tc.i);
i2 =  EE::int2(real2:tc.r);
r2 =  EE::real2(str2:tc.s);
s2 =  EE::str2(time2:tc.t);
t2 =  EE::time2(id2:tc.ID);
id2 =  EE::id2(enum2:tc.en);
en2 = EE::enum2(udt2:tc.u);
u2 = EE::udt2(bool2:tc.b);

////////////////////////////
// Instance Operations
////////////////////////////
b3 = tc.bool3(date3:tc.d);
d3 =  tc.date3(evt3:tc.e);
e3 =  tc.evt3(timer3:tc.tr);
tr3 =  tc.timer3(int3:tc.i);
i3 =  tc.int3(real3:tc.r);
r3 =  tc.real3(str3:tc.s);
s3 =  tc.str3(time3:tc.t);
t3 =  tc.time3(id3:tc.ID);
id3 =  tc.id3(enum3:tc.en);
en3 = tc.enum3(udt3:tc.u);
u3 = tc.udt3(bool3:tc.b);

////////////////////////////
// Class Operations
////////////////////////////
b4 = TC::bool4(date4:tc.d);
d4 = TC::date4(evt4:tc.e);
e4 = TC::evt4(timer4:tc.tr);
tr4 = TC::timer4(int4:tc.i);
i4 = TC::int4(real4:tc.r);
r4 = TC::real4(str4:tc.s);
s4 = TC::str4(time4:tc.t);
t4 = TC::time4(id4:tc.ID);
id4 = TC::id4(enum4:tc.en);
en4 = TC::enum4(udt4:tc.u);
u4 = TC::udt4(bool4:tc.b);

////////////////////////////
// Results
////////////////////////////
if ( b and b2 and b3 and b4 )
  LOG::LogSuccess( message:"Single Invocation - Boolean" );
else
  LOG::LogFailure( message:"Single Invocation - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:b2 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b3 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b4 );
end if;

if ( ( d == d2 ) and ( d3 == d4 ) )
  LOG::LogSuccess( message:"Single Invocation - Date" );
else
  LOG::LogFailure( message:"Single Invocation - Date");
  LOG::LogDate( message:"Function Invocation", date:d );
  LOG::LogDate( message:"Bridge Invocation", date:d2 );
  LOG::LogDate( message:"Instance Operation Invocation", date:d3 );
  LOG::LogDate( message:"Class Operation Invocation", date:d4 );
end if;

if ( ( e == e2 ) and ( e3 == e4 ) )
  LOG::LogSuccess( message:"Single Invocation - Event" );
else
  LOG::LogFailure( message:"Single Invocation - Event");
end if;

if ( ( tr == tr2 ) and ( tr3 == tr4 ) )
  LOG::LogSuccess( message:"Single Invocation - Timer" );
else
  LOG::LogFailure( message:"Single Invocation - Timer");
end if;

if ( ( i == i2 ) and ( i3 == i4 ) )
  LOG::LogSuccess( message:"Single Invocation - Integer" );
else
  LOG::LogFailure( message:"Single Invocation - Integer");
  LOG::LogInt( message:"Function Invocation", int:i );
  LOG::LogInt( message:"Bridge Invocation", int:i2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i3 );
  LOG::LogInt( message:"Class Operation Invocation", int:i4 );
end if;

if ( ( r == r2 ) and ( r3 == r4 ) )
  LOG::LogSuccess( message:"Single Invocation - Real" );
else
  LOG::LogFailure( message:"Single Invocation - Real");
  LOG::LogReal( message:"Function Invocation", real:r );
  LOG::LogReal( message:"Bridge Invocation", real:r2 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r3 );
  LOG::LogReal( message:"Class Operation Invocation", real:r4 );
end if;

if ( ( s == s2 ) and ( s3 == s4 ) )
  LOG::LogSuccess( message:"Single Invocation - String" );
else
  LOG::LogFailure( message:"Single Invocation - String");
  LOG::LogString( message:"Function Invocation", str:s );
  LOG::LogString( message:"Bridge Invocation", str:s2 );
  LOG::LogString( message:"Instance Operation Invocation", str:s3 );
  LOG::LogString( message:"Class Operation Invocation", str:s4 );
end if;

if ( ( t== t2 ) and ( t3 == t4 ) )
  LOG::LogSuccess( message:"Single Invocation - Timestamp" );
else
  LOG::LogFailure( message:"Single Invocation - Timestamp");
  LOG::LogTime( message:"Function Invocation", time:t );
  LOG::LogTime( message:"Bridge Invocation", time:t2 );
  LOG::LogTime( message:"Instance Operation Invocation", time:t3 );
  LOG::LogTime( message:"Class Operation Invocation", time:t4 );
end if;

if ( ( id == id2 ) and ( id3 == id4 ) )
  LOG::LogSuccess( message:"Single Invocation - Unique ID" );
else
  LOG::LogFailure( message:"Single Invocation - Unique ID");
  LOG::LogUniqueId( message:"Function Invocation", uid:id );
  LOG::LogUniqueId( message:"Bridge Invocation", uid:id2 );
  LOG::LogUniqueId( message:"Instance Operation Invocation", uid:id3 );
  LOG::LogUniqueId( message:"Class Operation Invocation", uid:id4 );
end if;

if ( ( en == en2 ) and ( en3 == en4 ) )
  LOG::LogSuccess( message:"Single Invocation - Enumeration" );
else
  LOG::LogFailure( message:"Single Invocation - Enumeration");
end if;

if ( ( u == u2 ) and ( u3 == u4 ) )
  LOG::LogSuccess( message:"Single Invocation - User Data Type" );
else
  LOG::LogFailure( message:"Single Invocation - User Data Type");
  LOG::LogInt( message:"Function Invocation", int:u );
  LOG::LogInt( message:"Bridge Invocation", int:u2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u3 );
  LOG::LogInt( message:"Class Operation Invocation", int:u4 );
end if;

generate DRV3 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097156,
	2097156,
	0,
	'Multiple Invocation',
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
INSERT INTO SM_EIGN
	VALUES (2097156,
	2097157,
	2097156,
	0,
	'');
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
	'select any tc from instances of TC;

select any tc4 from instances of TC4;
select any tc5 from instances of TC5;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Functions
////////////////////////////
b = ::bool1(date1: ::date1(evt1:tc.e));
d = ::date1(evt1:EE::evt2(timer2:tc.tr));
e = ::evt1(timer1: tc.timer3(int3:tc.i));
tr = ::timer1(int1:TC4::int7(real7:tc.r));
i = ::int1(real1: ::real1(str1:tc.s));
r = ::real1(str1:  EE::str2(time2:tc.t));
s = ::str1(time1: tc.time3(id3:tc.ID));
t = ::time1(id1: TC::id4(enum4:tc.en));
id = ::id1(enum1: ::enum1(udt1:tc.u));
en = ::enum1(udt1:EE::udt2(bool2:tc.b));
u = ::udt1(bool1: tc4.bool6(date6:tc.d));

////////////////////////////
// Bridges
////////////////////////////
b2 = EE::bool2(date2: ::date1(evt1:tc.e));
d2 = EE::date2(evt2:  EE::evt2(timer2:tc.tr));
e2 =  EE::evt2(timer2: tc.timer3(int3:tc.i));
tr2 =  EE::timer2(int2: TC4::int7(real7:tc.r));
i2 =  EE::int2(real2: ::real1(str1:tc.s));
r2 =  EE::real2(str2:  EE::str2(time2:tc.t));
s2 =  EE::str2(time2: tc.time3(id3:tc.ID));
t2 =  EE::time2(id2: TC::id4(enum4:tc.en));
id2 =  EE::id2(enum2: ::enum1(udt1:tc.u));
en2 = EE::enum2(udt2: EE::udt2(bool2:tc.b));
u2 = EE::udt2(bool2: tc4.bool6(date6:tc.d));

////////////////////////////
// Instance Operations
////////////////////////////
b3 = tc.bool3(date3: ::date1(evt1:tc.e));
d3 =  tc.date3(evt3: EE::evt2(timer2:tc.tr));
e3 =  tc.evt3(timer3:  tc.timer3(int3:tc.i));
tr3 =  tc.timer3(int3:TC4::int7(real7:tc.r));
i3 =  tc.int3(real3: ::real1(str1:tc.s));
r3 =  tc.real3(str3: EE::str2(time2:tc.t));
s3 =  tc.str3(time3: tc.time3(id3:tc.ID));
t3 =  tc.time3(id3: TC::id4(enum4:tc.en));
id3 =  tc.id3(enum3: ::enum1(udt1:tc.u));
en3 = tc.enum3(udt3: EE::udt2(bool2:tc.b));
u3 = tc.udt3(bool3: tc.bool3(date3:tc.d));

//bp:2946
b5 = tc5.bool8(date8: tc.date3(evt3:tc.e));
u5 = tc.udt3(bool3: tc5.bool9(date9:tc.d));

////////////////////////////
// Class Operations
////////////////////////////
b4 = TC::bool4(date4: ::date1(evt1:tc.e));
d4 = TC::date4(evt4: EE::evt2(timer2:tc.tr));
e4 = TC::evt4(timer4: tc.timer3(int3:tc.i));
tr4 = TC::timer4(int4:TC4::int7(real7:tc.r));
i4 = TC::int4(real4: ::real1(str1:tc.s));
r4 = TC::real4(str4: EE::str2(time2:tc.t));
s4 = TC::str4(time4:tc.time3(id3:tc.ID));
t4 = TC::time4(id4:TC::id4(enum4:tc.en));
id4 = TC::id4(enum4: ::enum1(udt1:tc.u));
en4 = TC::enum4(udt4:EE::udt2(bool2:tc.b));
u4 = TC::udt4(bool4: tc4.bool6(date6:tc.d));

////////////////////////////
// Results
////////////////////////////
if ( b and b2 and b3 and b4 and b5 )
  LOG::LogSuccess( message:"Multiple Invocation - Boolean" );
else
  LOG::LogFailure( message:"Multiple Invocation - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:b2 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b3 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b4 );
  LOG::LogBoolean( message:"Instance Operation Invocation - bp:2946", bool:b5 );
end if;

if ( ( d == d2 ) and ( d3 == d4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Date" );
else
  LOG::LogFailure( message:"Multiple Invocation - Date");
  LOG::LogDate( message:"Function Invocation", date:d );
  LOG::LogDate( message:"Bridge Invocation", date:d2 );
  LOG::LogDate( message:"Instance Operation Invocation", date:d3 );
  LOG::LogDate( message:"Class Operation Invocation", date:d4 );
end if;

if ( ( e == e2 ) and ( e3 == e4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Event" );
else
  LOG::LogFailure( message:"Multiple Invocation - Event");
end if;

if ( ( tr == tr2 ) and ( tr3 == tr4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Timer" );
else
  LOG::LogFailure( message:"Multiple Invocation - Timer");
end if;

if ( ( i == i2 ) and ( i3 == i4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Integer" );
else
  LOG::LogFailure( message:"Multiple Invocation - Integer");
  LOG::LogInt( message:"Function Invocation", int:i );
  LOG::LogInt( message:"Bridge Invocation", int:i2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i3 );
  LOG::LogInt( message:"Class Operation Invocation", int:i4 );
end if;

if ( ( r == r2 ) and ( r3 == r4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Real" );
else
  LOG::LogFailure( message:"Multiple Invocation - Real");
  LOG::LogReal( message:"Function Invocation", real:r );
  LOG::LogReal( message:"Bridge Invocation", real:r2 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r3 );
  LOG::LogReal( message:"Class Operation Invocation", real:r4 );
end if;

if ( ( s == s2 ) and ( s3 == s4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - String" );
else
  LOG::LogFailure( message:"Multiple Invocation - String");
  LOG::LogString( message:"Function Invocation", str:s );
  LOG::LogString( message:"Bridge Invocation", str:s2 );
  LOG::LogString( message:"Instance Operation Invocation", str:s3 );
  LOG::LogString( message:"Class Operation Invocation", str:s4 );
end if;

if ( ( t== t2 ) and ( t3 == t4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Timestamp" );
else
  LOG::LogFailure( message:"Multiple Invocation - Timestamp");
  LOG::LogTime( message:"Function Invocation", time:t );
  LOG::LogTime( message:"Bridge Invocation", time:t2 );
  LOG::LogTime( message:"Instance Operation Invocation", time:t3 );
  LOG::LogTime( message:"Class Operation Invocation", time:t4 );
end if;

if ( ( id == id2 ) and ( id3 == id4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Unique ID" );
else
  LOG::LogFailure( message:"Multiple Invocation - Unique ID");
  LOG::LogUniqueId( message:"Function Invocation", uid:id );
  LOG::LogUniqueId( message:"Bridge Invocation", uid:id2 );
  LOG::LogUniqueId( message:"Instance Operation Invocation", uid:id3 );
  LOG::LogUniqueId( message:"Class Operation Invocation", uid:id4 );
end if;

if ( ( en == en2 ) and ( en3 == en4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Enumeration" );
else
  LOG::LogFailure( message:"Multiple Invocation - Enumeration");
end if;

if ( ( u == u2 ) and ( u3 == u4 ) and ( u5 == u ) )
  LOG::LogSuccess( message:"Multiple Invocation - User Data Type" );
else
  LOG::LogFailure( message:"Multiple Invocation - User Data Type");
  LOG::LogInt( message:"Function Invocation", int:u );
  LOG::LogInt( message:"Bridge Invocation", int:u2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u3 );
  LOG::LogInt( message:"Class Operation Invocation", int:u4 );
  LOG::LogInt( message:"Instance Operation Invocation - bp:2946", int:u5 );
end if;

generate DRV3 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097157,
	2097156,
	0,
	'Multiple Invocation with Expression',
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
	'select any tc from instances of TC;

select any tc5 from instances of TC5;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Functions
////////////////////////////
b = ::bool1( date1: ::date1( evt1:tc.e ) ) and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tr = ::timer1( int1:TC::int4( real4:tc.r )      +    ::int1( real1: tc.real3( str3:tc.s ) ) );
i = ::int1(real1: ::real1(str1:tc.s)               +   EE::real2( str2: tc.str3( time3:tc.t ) ) );
r = ::real1(str1: EE::str2(time2:tc.t)          +    tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s = ::str1( time1: EE::time2( id2: tc.ID ) )  +    tc.str3( time3: TC::time4( id4: tc.ID ) );
en = ::enum1(udt1:EE::udt2(bool2:tc.b)   +    tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u = ::udt1(bool1: tc.bool3(date3:tc.d)    and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Bridges
////////////////////////////
b2 = EE::bool2(date2: ::date1(evt1:tc.e))  and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tr2 =  EE::timer2(int2: TC::int4(real4:tc.r)     +   ::int1( real1: tc.real3( str3:tc.s ) ) );
i2 =  EE::int2(real2: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
r2 =  EE::real2(str2:  EE::str2(time2:tc.t)      +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s2 =  EE::str2(time2: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
en2 = EE::enum2(udt2: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u2 = EE::udt2(bool2: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Instance Operations
////////////////////////////
b3 = tc.bool3(date3: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tr3 =  tc.timer3(int3:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
i3 =  tc.int3(real3: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
r3 =  tc.real3(str3: EE::str2(time2:tc.t)        +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s3 =  tc.str3(time3: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
en3 = tc.enum3(udt3: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u3 = tc.udt3(bool3: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

// bp:2946
b5 = tc5.bool8(date8: tc.date3(evt3:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
u5 = tc.udt3(bool3: tc5.bool9(date9:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Class Operations
////////////////////////////
b4 = TC::bool4(date4: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tr4 = TC::timer4(int4:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
i4 = TC::int4(real4: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
r4 = TC::real4(str4: EE::str2(time2:tc.t)        +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s4 = TC::str4(time4:tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
en4 = TC::enum4(udt4:EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u4 = TC::udt4(bool4: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Results
////////////////////////////
if ( b and b2 and b3 and b4 and b5 )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Boolean" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:b2 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b3 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b4 );
  LOG::LogBoolean( message:"Instance Operation Invocation - bp:2946", bool:b5 );
end if;

if ( ( tr == tr2 ) and ( tr3 == tr4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Timer" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Timer");
end if;

if ( ( i == i2 ) and ( i3 == i4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Integer" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Integer");
  LOG::LogInt( message:"Function Invocation", int:i );
  LOG::LogInt( message:"Bridge Invocation", int:i2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i3 );
  LOG::LogInt( message:"Class Operation Invocation", int:i4 );
end if;

if ( ( r == r2 ) and ( r3 == r4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Real" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Real");
  LOG::LogReal( message:"Function Invocation", real:r );
  LOG::LogReal( message:"Bridge Invocation", real:r2 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r3 );
  LOG::LogReal( message:"Class Operation Invocation", real:r4 );
end if;

if ( ( s == s2 ) and ( s3 == s4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - String" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - String");
  LOG::LogString( message:"Function Invocation", str:s );
  LOG::LogString( message:"Bridge Invocation", str:s2 );
  LOG::LogString( message:"Instance Operation Invocation", str:s3 );
  LOG::LogString( message:"Class Operation Invocation", str:s4 );
end if;

if ( ( en == en2 ) and ( en3 == en4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Enumeration" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Enumeration");
end if;

if ( ( u == u2 ) and ( u3 == u4 ) and ( u5 == u ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - User Data Type" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - User Data Type");
  LOG::LogInt( message:"Function Invocation", int:u );
  LOG::LogInt( message:"Bridge Invocation", int:u2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u3 );
  LOG::LogInt( message:"Class Operation Invocation", int:u4 );
  LOG::LogInt( message:"Instance Operation Invocation - bp:2946", int:u5 );
end if;

generate DRV3 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097158,
	2097156,
	0,
	'From within',
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
////////////////////////////////////////////////////////
// From within Functions
////////////////////////////////////////////////////////
::single_invoke();
::multiple_invoke();
::multiple_invoke_w_expressions();

////////////////////////////////////////////////////////
// From within Bridges
////////////////////////////////////////////////////////
EE::single_invoke();
EE::multiple_invoke();
EE::multiple_invoke_w_expressions();

////////////////////////////////////////////////////////
// From within Instance Operations
////////////////////////////////////////////////////////
select any tc from instances of TC;
tc.single_invoke();
tc.multiple_invoke();
tc.multiple_invoke_w_expressions();

////////////////////////////////////////////////////////
// From within Class Operations
////////////////////////////////////////////////////////
TC::single_invoke2();
TC::multiple_invoke2();
TC::multiple_invoke_w_expressions2();

generate DRV3 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097159,
	2097156,
	0,
	'Events',
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
	'select any tc from instances of TC;
select any tc3 from instances of TC3;
select any tc3s from instances of TC3S;
select any tc4 from instances of TC4;

////////////////////////////////////////////////
// Event Generation
////////////////////////////////////////////////
// Order is function, bridge, instance operation, class operation

////////////////////////////////////////////////
// Events - Single Invocation; Instance, Creation
////////////////////////////////////////////////

LOG::LogInfo(message: "Single Invocation; Instance, Creation");

generate TC31( bool: ::bool1(date1:tc.d ) ) to TC3 creator;
generate TC31( bool: EE::bool2( date2:tc.d ) ) to TC3 creator;
generate TC31( bool: REE::bool5( date5:tc.d ) ) to TC3 creator;
generate TC31( bool: tc.bool3( date3: tc.d ) ) to TC3 creator;
generate TC31( bool: tc4.bool6( date6: tc.d ) ) to TC3 creator;
generate TC31( bool: TC::bool4( date4: tc.d ) ) to TC3 creator;
generate TC31( bool: TC4::bool7( date7: tc.d ) ) to TC3 creator;

////////////////////////////////////////////////
// Events - Single Invocation; Instance, LE
////////////////////////////////////////////////

LOG::LogInfo(message: "Single Invocation; Instance, LE");

generate DRV4( bool5: ::bool1(date1:tc.d ) ) to self;
generate DRV5( date5: EE::date2( evt2:tc.e ) ) to self;
generate DRV6( enum5: tc.enum3( udt3: tc.u ) ) to self;
generate DRV7( evt5: TC::evt4( timer4: tc.tr ) ) to self;
generate DRV8( id5: ::id1( enum1: tc.en ) ) to self;
generate DRV9( int5: REE::int5( real5: tc.r ) ) to self;
generate DRV10( real5: tc4.real6( str6: tc.s ) ) to self;
generate DRV11( str5: TC4::str7( time7: tc.t ) ) to self;
generate DRV12( time5: ::time1( id1: tc.ID ) ) to self;
generate DRV13( timer5: EE::timer2( int2: tc.i ) ) to self;
generate DRV14( udt5: tc.udt3( bool3: tc.b ) ) to self;

////////////////////////////////////////////////
// Events - Single Invocation; Instance, PE
////////////////////////////////////////////////

LOG::LogInfo(message: "Single Invocation; Instance, PE");

generate TC32*( bool: ::bool1(date1:tc.d ) ) to tc3;
generate TC32*( bool: EE::bool2( date2:tc.d ) ) to tc3;
generate TC32*( bool: REE::bool5( date5:tc.d ) ) to tc3;
generate TC32*( bool: tc.bool3( date3: tc.d ) ) to tc3;
generate TC32*( bool: tc4.bool6( date6: tc.d ) ) to tc3;
generate TC32*( bool: TC::bool4( date4: tc.d ) ) to tc3;
generate TC32*( bool: TC4::bool7( date7: tc.d ) ) to tc3;

////////////////////////////////////////////////
// Events - Single Invocation; Instance, NLE
////////////////////////////////////////////////

LOG::LogInfo(message: "Single Invocation; Instance, NLE");

generate TC32*( bool: ::bool1(date1:tc.d ) ) to tc3s;
generate TC32*( bool: EE::bool2( date2:tc.d ) ) to  tc3s;
generate TC32*( bool: REE::bool5( date5:tc.d ) ) to  tc3s;
generate TC32*( bool: tc.bool3( date3: tc.d ) ) to  tc3s;
generate TC32*( bool: tc4.bool6( date6: tc.d ) ) to  tc3s;
generate TC32*( bool: TC::bool4( date4: tc.d ) ) to  tc3s;
generate TC32*( bool: TC4::bool7( date7: tc.d ) ) to  tc3s;

////////////////////////////////////////////////
// Events - Multiple Invocation; Instance, Creation
////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation; Instance, Creation");

generate TC31( bool: ::bool1(date1:EE::date2( evt2:tc.e ) ) ) to TC3 creator;
generate TC31( bool: EE::bool2( date2: tc.date3( evt3:tc.e ) ) ) to TC3 creator;
generate TC31( bool: REE::bool5( date5:TC::date4( evt4: tc.e ) ) ) to TC3 creator;
generate TC31( bool: tc.bool3( date3: ::date1( evt1:tc.e ) ) ) to TC3 creator;
generate TC31( bool: tc4.bool6( date6: EE::date2( evt2:tc.e ) ) ) to TC3 creator;
generate TC31( bool: TC::bool4( date4: tc.date3( evt3:tc.e ) ) ) to TC3 creator;
generate TC31( bool: TC4::bool7( date7: TC::date4( evt4: tc.e ) ) ) to TC3 creator;

////////////////////////////////////////////////
// Events - Multiple Invocation; Instance, LE
////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation; Instance, LE");

generate DRV4( bool5: ::bool1(date1: EE::date2( evt2:tc.e ) ) ) to self;
generate DRV5( date5: EE::date2( evt2:TC::evt4( timer4: tc.tr ) ) ) to self;
generate DRV6( enum5: tc.enum3( udt3: tc.udt3( bool3: tc.b ) ) ) to self;
generate DRV7( evt5: TC::evt4( timer4: EE::timer2( int2: tc.i ) ) ) to self;
generate DRV8( id5: ::id1( enum1: tc.enum3( udt3:tc.u ) ) ) to self;
generate DRV9( int5: REE::int5( real5:tc.real3( str3: tc.s ) ) ) to self;
generate DRV10( real5: tc4.real6( str6: TC::str4( time4: tc.t ) ) ) to self;
generate DRV11( str5: TC4::str7( time7: ::time1( id1: tc.ID ) ) ) to self;
generate DRV12( time5: ::time1( id1: ::id1( enum1:tc.en ) ) ) to self;
generate DRV13( timer5: EE::timer2( int2: EE::int2( real2:tc.r ) ) ) to self;
generate DRV14( udt5: tc.udt3( bool3: ::bool1( date1:tc.d ) ) ) to self;

////////////////////////////////////////////////
// Events - Multiple Invocation; Instance, PE
////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation; Instance, PE");

generate TC32*( bool: ::bool1(date1:EE::date2( evt2:tc.e ) ) ) to tc3;
generate TC32*( bool: EE::bool2( date2: tc.date3( evt3:tc.e ) ) ) to tc3;
generate TC32*( bool: REE::bool5( date5:TC::date4( evt4: tc.e ) ) ) to  tc3;
generate TC32*( bool: tc.bool3( date3: ::date1( evt1:tc.e ) ) ) to  tc3;
generate TC32*( bool: tc4.bool6( date6: EE::date2( evt2:tc.e ) ) ) to  tc3;
generate TC32*( bool: TC::bool4( date4: tc.date3( evt3:tc.e ) ) ) to  tc3;
generate TC32*( bool: TC4::bool7( date7: TC::date4( evt4: tc.e ) ) ) to  tc3;

////////////////////////////////////////////////
// Events - Multiple Invocation; Instance, NLE
////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation; Instance, NLE");

generate TC32*( bool: ::bool1(date1:EE::date2( evt2:tc.e ) ) ) to tc3s;
generate TC32*( bool: EE::bool2( date2: tc.date3( evt3:tc.e ) ) ) to tc3s;
generate TC32*( bool: REE::bool5( date5:TC::date4( evt4: tc.e ) ) ) to  tc3s;
generate TC32*( bool: tc.bool3( date3: ::date1( evt1:tc.e ) ) ) to  tc3s;
generate TC32*( bool: tc4.bool6( date6: EE::date2( evt2:tc.e ) ) ) to  tc3s;
generate TC32*( bool: TC::bool4( date4: tc.date3( evt3:tc.e ) ) ) to  tc3s;
generate TC32*( bool: TC4::bool7( date7: TC::date4( evt4: tc.e ) ) ) to  tc3s;

////////////////////////////////////////////////
// Events - Multiple Invocation w/ Expressions; Instance, Creation
////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation w/ Expressions; Instance, Creation");

generate TC31( bool: ::bool1(date1:EE::date2( evt2:tc.e ) )  and tc.bool3( date3: tc.d ) ) to TC3 creator;
generate TC31( bool: EE::bool2( date2: tc.date3( evt3:tc.e ) )  or tc.bool3( date3: tc.d ) ) to TC3 creator;
generate TC31( bool: REE::bool5( date5:TC::date4( evt4: tc.e ) )  and tc.bool3( date3: tc.d ) ) to TC3 creator;
generate TC31( bool: tc.bool3( date3: ::date1( evt1:tc.e ) )  or tc.bool3( date3: tc.d ) ) to TC3 creator;
generate TC31( bool: tc4.bool6( date6: EE::date2( evt2:tc.e ) )  and tc.bool3( date3: tc.d ) ) to TC3 creator;
generate TC31( bool: TC::bool4( date4: tc.date3( evt3:tc.e ) )  or tc.bool3( date3: tc.d ) ) to TC3 creator;
generate TC31( bool: TC4::bool7( date7: TC::date4( evt4: tc.e ) )  and tc.bool3( date3: tc.d ) ) to TC3 creator;

////////////////////////////////////////////////////////////////////////////
// Events - Multiple Invocation w/ Expressions; Instance, LE
////////////////////////////////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation w/ Expressions; Instance, LE");

generate DRV4( bool5: ::bool1(date1: EE::date2( evt2:tc.e ) )  and tc.bool3( date3: tc.d ) ) to self;
generate DRV5( date5: EE::date2( evt2:TC::evt4( timer4: tc.tr ) ) ) to self;
generate DRV6( enum5: tc.enum3( udt3: tc.udt3( bool3: tc.b ) + TC::udt4( bool4: tc.b ) ) ) to self;
generate DRV7( evt5: TC::evt4( timer4: EE::timer2( int2: tc.i ) ) ) to self;
generate DRV8( id5: ::id1( enum1: tc.enum3( udt3:tc.u ) ) ) to self;
generate DRV9( int5: REE::int5( real5:tc.real3( str3: tc.s ) )  + tc.int3( real3: tc.r ) ) to self;
generate DRV10( real5: tc4.real6( str6: TC::str4( time4: tc.t ) )  + ::real1( str1: tc.s ) ) to self;
generate DRV11( str5: TC4::str7( time7: ::time1( id1: tc.ID ) ) + EE::str2( time2: tc.t ) ) to self;
generate DRV12( time5: ::time1( id1: ::id1( enum1:tc.en ) ) ) to self;
generate DRV13( timer5: EE::timer2( int2: EE::int2( real2:tc.r )  + tc.int3( real3: tc.r ) ) ) to self;
generate DRV14( udt5: tc.udt3( bool3: ::bool1( date1:tc.d ) and EE::bool2( date2: tc.d ) ) + TC::udt4( bool4: tc.b ) ) to self;

////////////////////////////////////////////////
// Events - Multiple Invocation w/ Expressions; Instance, PE
////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation w/ Expressions; Instance, PE");

generate TC32*( bool: ::bool1(date1:EE::date2( evt2:tc.e ) )  and tc.bool3( date3: tc.d ) ) to tc3;
generate TC32*( bool: EE::bool2( date2: tc.date3( evt3:tc.e ) )  or tc.bool3( date3: tc.d ) ) to tc3;
generate TC32*( bool: REE::bool5( date5:TC::date4( evt4: tc.e ) )  and tc.bool3( date3: tc.d ) ) to tc3;
generate TC32*( bool: tc.bool3( date3: ::date1( evt1:tc.e ) )  or tc.bool3( date3: tc.d ) ) to tc3;
generate TC32*( bool: tc4.bool6( date6: EE::date2( evt2:tc.e ) )  and tc.bool3( date3: tc.d ) ) to tc3;
generate TC32*( bool: TC::bool4( date4: tc.date3( evt3:tc.e ) )  or tc.bool3( date3: tc.d ) ) to tc3;
generate TC32*( bool: TC4::bool7( date7: TC::date4( evt4: tc.e ) )  and tc.bool3( date3: tc.d ) ) to tc3;

////////////////////////////////////////////////
// Events - Multiple Invocation w/ Expressions; Instance, NLE
////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation w/ Expressions; Instance, NLE");

generate TC32*( bool: ::bool1(date1:EE::date2( evt2:tc.e ) )  and tc.bool3( date3: tc.d ) ) to tc3s;
generate TC32*( bool: EE::bool2( date2: tc.date3( evt3:tc.e ) )  or tc.bool3( date3: tc.d ) ) to  tc3s;
generate TC32*( bool: REE::bool5( date5:TC::date4( evt4: tc.e ) )  and tc.bool3( date3: tc.d ) ) to  tc3s;
generate TC32*( bool: tc.bool3( date3: ::date1( evt1:tc.e ) )  or tc.bool3( date3: tc.d ) ) to  tc3s;
generate TC32*( bool: tc4.bool6( date6: EE::date2( evt2:tc.e ) )  and tc.bool3( date3: tc.d ) ) to  tc3s;
generate TC32*( bool: TC::bool4( date4: tc.date3( evt3:tc.e ) )  or tc.bool3( date3: tc.d ) ) to  tc3s;
generate TC32*( bool: TC4::bool7( date7: TC::date4( evt4: tc.e ) )  and tc.bool3( date3: tc.d ) ) to  tc3s;

////////////////////////////////////////////////
// Event Pre-Creation
////////////////////////////////////////////////
// Order is function, bridge, instance operation, class operation

////////////////////////////////////////////////
// Events - Single Invocation; Instance, Creation
////////////////////////////////////////////////

LOG::LogInfo(message: "Single Invocation; Instance, Creation");

create event instance e1 of  TC31( bool: ::bool1(date1:tc.d ) ) to TC3 creator;
create event instance e2 of TC31( bool: EE::bool2( date2:tc.d ) ) to TC3 creator;
create event instance e3 of TC31( bool: REE::bool5( date5:tc.d ) ) to TC3 creator;
create event instance e4 of TC31( bool: tc.bool3( date3: tc.d ) ) to TC3 creator;
create event instance e5 of TC31( bool: tc4.bool6( date6: tc.d ) ) to TC3 creator;
create event instance e6 of TC31( bool: TC::bool4( date4: tc.d ) ) to TC3 creator;
create event instance e7 of TC31( bool: TC4::bool7( date7: tc.d ) ) to TC3 creator;

////////////////////////////////////////////////
// Events - Single Invocation; Instance, LE
////////////////////////////////////////////////

LOG::LogInfo(message: "Single Invocation; Instance, LE");

create event instance e8 of DRV4( bool5: ::bool1(date1:tc.d ) ) to self;
create event instance e9 of DRV5( date5: EE::date2( evt2:tc.e ) ) to self;
create event instance e10 of DRV6( enum5: tc.enum3( udt3: tc.u ) ) to self;
create event instance e11 of DRV7( evt5: TC::evt4( timer4: tc.tr ) ) to self;
create event instance e12 of DRV8( id5: ::id1( enum1: tc.en ) ) to self;
create event instance e13 of DRV9( int5: REE::int5( real5: tc.r ) ) to self;
create event instance e14 of DRV10( real5: tc4.real6( str6: tc.s ) ) to self;
create event instance e15 of DRV11( str5: TC4::str7( time7: tc.t ) ) to self;
create event instance e16 of DRV12( time5: ::time1( id1: tc.ID ) ) to self;
create event instance e17 of DRV13( timer5: EE::timer2( int2: tc.i ) ) to self;
create event instance e18 of DRV14( udt5: tc.udt3( bool3: tc.b ) ) to self;

////////////////////////////////////////////////
// Events - Single Invocation; Instance, PE
////////////////////////////////////////////////

LOG::LogInfo(message: "Single Invocation; Instance, PE");

create event instance e19 of TC32*( bool: ::bool1(date1:tc.d ) ) to tc3;
create event instance e20 of TC32*( bool: EE::bool2( date2:tc.d ) ) to tc3;
create event instance e21 of TC32*( bool: REE::bool5( date5:tc.d ) ) to tc3;
create event instance e22 of TC32*( bool: tc.bool3( date3: tc.d ) ) to tc3;
create event instance e23 of TC32*( bool: tc4.bool6( date6: tc.d ) ) to tc3;
create event instance e24 of TC32*( bool: TC::bool4( date4: tc.d ) ) to tc3;
create event instance e25 of TC32*( bool: TC4::bool7( date7: tc.d ) ) to tc3;

////////////////////////////////////////////////
// Events - Single Invocation; Instance, NLE
////////////////////////////////////////////////

LOG::LogInfo(message: "Single Invocation; Instance, NLE");

create event instance e26 of TC32*( bool: ::bool1(date1:tc.d ) ) to tc3s;
create event instance e27 of TC32*( bool: EE::bool2( date2:tc.d ) ) to  tc3s;
create event instance e28 of TC32*( bool: REE::bool5( date5:tc.d ) ) to  tc3s;
create event instance e29 of TC32*( bool: tc.bool3( date3: tc.d ) ) to  tc3s;
create event instance e30 of TC32*( bool: tc4.bool6( date6: tc.d ) ) to  tc3s;
create event instance e31 of TC32*( bool: TC::bool4( date4: tc.d ) ) to  tc3s;
create event instance e32 of TC32*( bool: TC4::bool7( date7: tc.d ) ) to  tc3s;

////////////////////////////////////////////////
// Events - Multiple Invocation; Instance, Creation
////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation; Instance, Creation");

create event instance e33 of TC31( bool: ::bool1(date1:EE::date2( evt2:tc.e ) ) ) to TC3 creator;
create event instance e34 of TC31( bool: EE::bool2( date2: tc.date3( evt3:tc.e ) ) ) to TC3 creator;
create event instance e35 of TC31( bool: REE::bool5( date5:TC::date4( evt4: tc.e ) ) ) to TC3 creator;
create event instance e36 of TC31( bool: tc.bool3( date3: ::date1( evt1:tc.e ) ) ) to TC3 creator;
create event instance e37 of TC31( bool: tc4.bool6( date6: EE::date2( evt2:tc.e ) ) ) to TC3 creator;
create event instance e38 of TC31( bool: TC::bool4( date4: tc.date3( evt3:tc.e ) ) ) to TC3 creator;
create event instance e39 of TC31( bool: TC4::bool7( date7: TC::date4( evt4: tc.e ) ) ) to TC3 creator;

////////////////////////////////////////////////
// Events - Multiple Invocation; Instance, LE
////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation; Instance, LE");

create event instance e40 of DRV4( bool5: ::bool1(date1: EE::date2( evt2:tc.e ) ) ) to self;
create event instance e41 of DRV5( date5: EE::date2( evt2:TC::evt4( timer4: tc.tr ) ) ) to self;
create event instance e42 of DRV6( enum5: tc.enum3( udt3: tc.udt3( bool3: tc.b ) ) ) to self;
create event instance e43 of DRV7( evt5: TC::evt4( timer4: EE::timer2( int2: tc.i ) ) ) to self;
create event instance e44 of DRV8( id5: ::id1( enum1: tc.enum3( udt3:tc.u ) ) ) to self;
create event instance e45 of DRV9( int5: REE::int5( real5:tc.real3( str3: tc.s ) ) ) to self;
create event instance e46 of DRV10( real5: tc4.real6( str6: TC::str4( time4: tc.t ) ) ) to self;
create event instance e47 of DRV11( str5: TC4::str7( time7: ::time1( id1: tc.ID ) ) ) to self;
create event instance e48 of DRV12( time5: ::time1( id1: ::id1( enum1:tc.en ) ) ) to self;
create event instance e49 of DRV13( timer5: EE::timer2( int2: EE::int2( real2:tc.r ) ) ) to self;
create event instance e50 of DRV14( udt5: tc.udt3( bool3: ::bool1( date1:tc.d ) ) ) to self;

////////////////////////////////////////////////
// Events - Multiple Invocation; Instance, PE
////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation; Instance, PE");

create event instance e51 of TC32*( bool: ::bool1(date1:EE::date2( evt2:tc.e ) ) ) to tc3;
create event instance e52 of TC32*( bool: EE::bool2( date2: tc.date3( evt3:tc.e ) ) ) to tc3;
create event instance e53 of TC32*( bool: REE::bool5( date5:TC::date4( evt4: tc.e ) ) ) to  tc3;
create event instance e54 of TC32*( bool: tc.bool3( date3: ::date1( evt1:tc.e ) ) ) to  tc3;
create event instance e55 of TC32*( bool: tc4.bool6( date6: EE::date2( evt2:tc.e ) ) ) to  tc3;
create event instance e56 of TC32*( bool: TC::bool4( date4: tc.date3( evt3:tc.e ) ) ) to  tc3;
create event instance e57 of TC32*( bool: TC4::bool7( date7: TC::date4( evt4: tc.e ) ) ) to  tc3;

////////////////////////////////////////////////
// Events - Multiple Invocation; Instance, NLE
////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation; Instance, NLE");

create event instance e58 of TC32*( bool: ::bool1(date1:EE::date2( evt2:tc.e ) ) ) to tc3s;
create event instance e59 of TC32*( bool: EE::bool2( date2: tc.date3( evt3:tc.e ) ) ) to tc3s;
create event instance e60 of TC32*( bool: REE::bool5( date5:TC::date4( evt4: tc.e ) ) ) to  tc3s;
create event instance e61 of TC32*( bool: tc.bool3( date3: ::date1( evt1:tc.e ) ) ) to  tc3s;
create event instance e62 of TC32*( bool: tc4.bool6( date6: EE::date2( evt2:tc.e ) ) ) to  tc3s;
create event instance e63 of TC32*( bool: TC::bool4( date4: tc.date3( evt3:tc.e ) ) ) to  tc3s;
create event instance e64 of TC32*( bool: TC4::bool7( date7: TC::date4( evt4: tc.e ) ) ) to  tc3s;

////////////////////////////////////////////////
// Events - Multiple Invocation w/ Expressions; Instance, Creation
////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation w/ Expressions; Instance, Creation");

create event instance e65 of TC31( bool: ::bool1(date1:EE::date2( evt2:tc.e ) )  and tc.bool3( date3: tc.d ) ) to TC3 creator;
create event instance e66 of TC31( bool: EE::bool2( date2: tc.date3( evt3:tc.e ) )  or tc.bool3( date3: tc.d ) ) to TC3 creator;
create event instance e67 of TC31( bool: REE::bool5( date5:TC::date4( evt4: tc.e ) )  and tc.bool3( date3: tc.d ) ) to TC3 creator;
create event instance e68 of TC31( bool: tc.bool3( date3: ::date1( evt1:tc.e ) )  or tc.bool3( date3: tc.d ) ) to TC3 creator;
create event instance e69 of TC31( bool: tc4.bool6( date6: EE::date2( evt2:tc.e ) )  and tc.bool3( date3: tc.d ) ) to TC3 creator;
create event instance e70 of TC31( bool: TC::bool4( date4: tc.date3( evt3:tc.e ) )  or tc.bool3( date3: tc.d ) ) to TC3 creator;
create event instance e71 of TC31( bool: TC4::bool7( date7: TC::date4( evt4: tc.e ) )  and tc.bool3( date3: tc.d ) ) to TC3 creator;

////////////////////////////////////////////////////////////////////////////
// Events - Multiple Invocation w/ Expressions; Instance, LE
////////////////////////////////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation w/ Expressions; Instance, LE");

create event instance e72 of DRV4( bool5: ::bool1(date1: EE::date2( evt2:tc.e ) )  and tc.bool3( date3: tc.d ) ) to self;
create event instance e73 of DRV5( date5: EE::date2( evt2:TC::evt4( timer4: tc.tr ) ) ) to self;
create event instance e74 of DRV6( enum5: tc.enum3( udt3: tc.udt3( bool3: tc.b ) + TC::udt4( bool4: tc.b ) ) ) to self;
create event instance e75 of DRV7( evt5: TC::evt4( timer4: EE::timer2( int2: tc.i ) ) ) to self;
create event instance e76 of DRV8( id5: ::id1( enum1: tc.enum3( udt3:tc.u ) ) ) to self;
create event instance e77 of DRV9( int5: REE::int5( real5:tc.real3( str3: tc.s ) )  + tc.int3( real3: tc.r ) ) to self;
create event instance e78 of DRV10( real5: tc4.real6( str6: TC::str4( time4: tc.t ) )  + ::real1( str1: tc.s ) ) to self;
create event instance e79 of DRV11( str5: TC4::str7( time7: ::time1( id1: tc.ID ) ) + EE::str2( time2: tc.t ) ) to self;
create event instance e80 of DRV12( time5: ::time1( id1: ::id1( enum1:tc.en ) ) ) to self;
create event instance e81 of DRV13( timer5: EE::timer2( int2: EE::int2( real2:tc.r )  + tc.int3( real3: tc.r ) ) ) to self;
create event instance e82 of DRV14( udt5: tc.udt3( bool3: ::bool1( date1:tc.d ) and EE::bool2( date2: tc.d ) ) + TC::udt4( bool4: tc.b ) ) to self;

////////////////////////////////////////////////
// Events - Multiple Invocation w/ Expressions; Instance, PE
////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation w/ Expressions; Instance, PE");

create event instance e83 of TC32*( bool: ::bool1(date1:EE::date2( evt2:tc.e ) )  and tc.bool3( date3: tc.d ) ) to tc3;
create event instance e84 of TC32*( bool: EE::bool2( date2: tc.date3( evt3:tc.e ) )  or tc.bool3( date3: tc.d ) ) to tc3;
create event instance e85 of TC32*( bool: REE::bool5( date5:TC::date4( evt4: tc.e ) )  and tc.bool3( date3: tc.d ) ) to tc3;
create event instance e86 of TC32*( bool: tc.bool3( date3: ::date1( evt1:tc.e ) )  or tc.bool3( date3: tc.d ) ) to tc3;
create event instance e87 of TC32*( bool: tc4.bool6( date6: EE::date2( evt2:tc.e ) )  and tc.bool3( date3: tc.d ) ) to tc3;
create event instance e88 of TC32*( bool: TC::bool4( date4: tc.date3( evt3:tc.e ) )  or tc.bool3( date3: tc.d ) ) to tc3;
create event instance e89 of TC32*( bool: TC4::bool7( date7: TC::date4( evt4: tc.e ) )  and tc.bool3( date3: tc.d ) ) to tc3;

////////////////////////////////////////////////
// Events - Multiple Invocation w/ Expressions; Instance, NLE
////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation w/ Expressions; Instance, NLE");

create event instance e90 of TC32*( bool: ::bool1(date1:EE::date2( evt2:tc.e ) )  and tc.bool3( date3: tc.d ) ) to tc3s;
create event instance e91 of TC32*( bool: EE::bool2( date2: tc.date3( evt3:tc.e ) )  or tc.bool3( date3: tc.d ) ) to  tc3s;
create event instance e92 of TC32*( bool: REE::bool5( date5:TC::date4( evt4: tc.e ) )  and tc.bool3( date3: tc.d ) ) to  tc3s;
create event instance e93 of TC32*( bool: tc.bool3( date3: ::date1( evt1:tc.e ) )  or tc.bool3( date3: tc.d ) ) to  tc3s;
create event instance e94 of TC32*( bool: tc4.bool6( date6: EE::date2( evt2:tc.e ) )  and tc.bool3( date3: tc.d ) ) to  tc3s;
create event instance e95 of TC32*( bool: TC::bool4( date4: tc.date3( evt3:tc.e ) )  or tc.bool3( date3: tc.d ) ) to  tc3s;
create event instance e96 of TC32*( bool: TC4::bool7( date7: TC::date4( evt4: tc.e ) )  and tc.bool3( date3: tc.d ) ) to  tc3s;

generate DRV3 to self;
',
	'');
INSERT INTO SM_STATE
	VALUES (2097160,
	2097156,
	0,
	'Boolean',
	8,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_SEME
	VALUES (2097160,
	2097157,
	2097156,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
	VALUES (2097160,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097160,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
	'LOG::LogSuccess(message:"Event - Boolean");',
	'');
INSERT INTO SM_STATE
	VALUES (2097161,
	2097156,
	0,
	'Date',
	9,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_SEME
	VALUES (2097161,
	2097158,
	2097156,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
	VALUES (2097161,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097161,
	2097161,
	2097156,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
	'LOG::LogSuccess(message:"Event - Date");',
	'');
INSERT INTO SM_STATE
	VALUES (2097162,
	2097156,
	0,
	'Enumeration',
	10,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_SEME
	VALUES (2097162,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
	VALUES (2097162,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097162,
	2097162,
	2097156,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
	'LOG::LogSuccess(message:"Event - Enumeration");',
	'');
INSERT INTO SM_STATE
	VALUES (2097163,
	2097156,
	0,
	'Event',
	11,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_SEME
	VALUES (2097163,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
	VALUES (2097163,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097163,
	2097163,
	2097156,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
	'LOG::LogSuccess(message:"Event - Event");',
	'');
INSERT INTO SM_STATE
	VALUES (2097164,
	2097156,
	0,
	'Unique ID',
	12,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_SEME
	VALUES (2097164,
	2097161,
	2097156,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
	VALUES (2097164,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097164,
	2097166,
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
	'LOG::LogSuccess(message:"Event - Unique ID");',
	'');
INSERT INTO SM_STATE
	VALUES (2097165,
	2097156,
	0,
	'Integer',
	13,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_SEME
	VALUES (2097165,
	2097162,
	2097156,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
	VALUES (2097165,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097165,
	2097164,
	2097156,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
	'LOG::LogSuccess(message:"Event - Integer");',
	'');
INSERT INTO SM_STATE
	VALUES (2097166,
	2097156,
	0,
	'String',
	14,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_SEME
	VALUES (2097166,
	2097164,
	2097156,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
	'LOG::LogSuccess(message:"Event - String");',
	'');
INSERT INTO SM_STATE
	VALUES (2097167,
	2097156,
	0,
	'Real',
	15,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_SEME
	VALUES (2097167,
	2097163,
	2097156,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
	'LOG::LogSuccess(message:"Event - Real");',
	'');
INSERT INTO SM_STATE
	VALUES (2097168,
	2097156,
	0,
	'Timer',
	16,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_SEME
	VALUES (2097168,
	2097166,
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
	'LOG::LogSuccess(message:"Event - Timer");',
	'');
INSERT INTO SM_STATE
	VALUES (2097169,
	2097156,
	0,
	'Timestamp',
	17,
	0);
INSERT INTO SM_EIGN
	VALUES (2097169,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097169,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097169,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097169,
	2097154,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097169,
	2097155,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097169,
	2097155,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097169,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097169,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097169,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097169,
	2097157,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097169,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097169,
	2097158,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097169,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097169,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097169,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097169,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097169,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097169,
	2097161,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097169,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097169,
	2097162,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097169,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097169,
	2097163,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097169,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097169,
	2097164,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097169,
	2097165,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097169,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097169,
	2097166,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097169,
	2097156,
	2097169);
INSERT INTO SM_AH
	VALUES (2097169,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097169,
	2097156,
	1,
	'LOG::LogSuccess(message:"Event - Timestamp");',
	'');
INSERT INTO SM_STATE
	VALUES (2097170,
	2097156,
	0,
	'User Data Type',
	19,
	0);
INSERT INTO SM_EIGN
	VALUES (2097170,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097170,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097170,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097170,
	2097154,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097170,
	2097155,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097170,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097170,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097170,
	2097157,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097170,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097170,
	2097158,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097170,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097170,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097170,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097170,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097170,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097170,
	2097161,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097170,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097170,
	2097162,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097170,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097170,
	2097163,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097170,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097170,
	2097164,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097170,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097170,
	2097165,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097170,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097170,
	2097166,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097170,
	2097156,
	2097170);
INSERT INTO SM_AH
	VALUES (2097170,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097170,
	2097156,
	1,
	'LOG::LogSuccess(message:"Event - User Data Type");',
	'');
INSERT INTO SM_STATE
	VALUES (2097171,
	2097156,
	0,
	'Select from instances of where',
	20,
	0);
INSERT INTO SM_EIGN
	VALUES (2097171,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097171,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097171,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097171,
	2097154,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097171,
	2097155,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097171,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097171,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097171,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097171,
	2097157,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097171,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097171,
	2097158,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097171,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097171,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097171,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097171,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097171,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097171,
	2097161,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097171,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097171,
	2097162,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097171,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097171,
	2097163,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097171,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097171,
	2097164,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097171,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097171,
	2097165,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097171,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097171,
	2097166,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097171,
	2097156,
	2097171);
INSERT INTO SM_AH
	VALUES (2097171,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097171,
	2097156,
	1,
	'//////////////////////////////
// Function
// Select any from instances of where
//////////////////////////////
select any tc from instances of TC;

select any tc2 from instances of TC where ( selected.b == ( ::bool1( date1: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( NOT tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select many from instances of where
//////////////////////////////
select many tc3 from instances of TC where ( selected.b == ( ::bool1( date1: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( NOT EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Bridge
// Select any from instances of where
//////////////////////////////
select any tc4 from instances of TC where ( selected.b == ( EE::bool2( date2: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select many from instances of where
//////////////////////////////
select many tc5 from instances of TC where ( selected.b == ( EE::bool2( date2: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( NOT REE::bool5( date5:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Realized Bridge
// Select any from instances of where
//////////////////////////////
select any tc6 from instances of TC where ( selected.b == ( REE::bool5( date5: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( NOT ::bool1( date1:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select many from instances of where
//////////////////////////////
select many tc7 from instances of TC where ( selected.b == ( REE::bool5( date5: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) or NOT TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Instance Operation
// Select any from instances of where
//////////////////////////////
select any tc8 from instances of TC where ( selected.b == ( tc.bool3( date3: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) or TC4::bool7( date7:tc.d ) ) ) ) );

//////////////////////////////
// Select many from instances of where
//////////////////////////////
select many tc9 from instances of TC where ( selected.b == ( tc.bool3( date3: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Realized Instance Operation
// Select any from instances of where
//////////////////////////////
select any tc_4 from instances of TC4;

select any tc10 from instances of TC where ( selected.b == ( tc_4.bool6( date6: EE::date2( evt2: TC::evt4( timer4: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc_4.int6( real6: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( NOT tc_4.bool6( date6:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select many from instances of where
//////////////////////////////
select many tc11 from instances of TC where ( selected.b == ( tc_4.bool6( date6: EE::date2( evt2: TC::evt4( timer4: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc_4.int6( real6: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc_4.bool6( date6:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Class Operation
// Select any from instances of where
//////////////////////////////
select any tc12 from instances of TC where ( selected.b == ( TC::bool4( date4: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select many from instances of where
//////////////////////////////
select many tc13 from instances of TC where ( selected.b == ( TC::bool4( date4: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Realized Class Operation
// Select any from instances of where
//////////////////////////////
select any tc14 from instances of TC where ( selected.b == ( TC4::bool7( date7: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select many from instances of where
//////////////////////////////
select many tc15 from instances of TC where ( selected.b == ( TC4::bool7( date7: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Verify results
//////////////////////////////
if ( ( tc2 ==  tc4 ) AND ( ( tc6 ==  tc8 ) AND ( ( tc10 == tc12 ) AND (  tc14 == tc2 ) ) ) )
  LOG::LogSuccess( message: "Select any from instances of where was successful." );
else
  LOG::LogFailure( message: "Select any from instances of where failed." );
end if;

if ( ( tc3 ==  tc5 ) AND ( ( tc7 ==  tc9 ) AND ( ( tc11 == tc13 ) AND (  tc15 == tc3 ) ) ) )
  LOG::LogSuccess( message: "Select many from instances of where was successful." );
else
  LOG::LogFailure( message: "Select many from instances of where failed." );
end if;

generate DRV3 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097172,
	2097156,
	0,
	'If Elif',
	21,
	0);
INSERT INTO SM_EIGN
	VALUES (2097172,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097172,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097172,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097172,
	2097154,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097172,
	2097155,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097172,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097172,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097172,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097172,
	2097157,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097172,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097172,
	2097158,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097172,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097172,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097172,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097172,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097172,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097172,
	2097161,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097172,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097172,
	2097162,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097172,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097172,
	2097163,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097172,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097172,
	2097164,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097172,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097172,
	2097165,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097172,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097172,
	2097166,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097172,
	2097156,
	2097172);
INSERT INTO SM_AH
	VALUES (2097172,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097172,
	2097156,
	1,
	'//////////////////////////////
// If
//////////////////////////////

select any tc from instances of TC;
select any tc_4 from instances of TC4;

if ( ( ( ::int1( real1: EE::real2( str2: tc.s ) ) + 999 ) >= EE::int2( real2: EE::real2( str2: tc.s ) ) ) or ( ( ::int1( real1: EE::real2( str2: tc.s ) ) + 999 ) > EE::int2( real2: EE::real2( str2: tc.s ) ) ) or ( ::bool1( date1: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) ) 
  LOG::LogSuccess( message: "If statement was successful." );
else
  LOG::LogFailure( message: "If statement failed." );
end if;

if ( ( ( REE::int5( real5: EE::real2( str2: tc.s ) ) + 999 ) >= ( tc.int3( real3: EE::real2( str2: tc.s ) ) ) )  or ( ( REE::int5( real5: EE::real2( str2: tc.s ) ) + 999 ) > ( tc.int3( real3: EE::real2( str2: tc.s ) ) ) )  or ( EE::bool2( date2: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) ) and ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) and TC::bool4( date4:tc.d ) ) ) )
  LOG::LogSuccess( message: "If statement was successful." );
else
  LOG::LogFailure( message: "If statement failed." );
end if;

if ( ( ( tc_4.int6( real6: EE::real2( str2: tc.s ) ) + 999 ) >= ( TC::int4( real4: EE::real2( str2: tc.s ) ) ) ) or ( ( tc_4.int6( real6: EE::real2( str2: tc.s ) ) + 999 ) > ( TC::int4( real4: EE::real2( str2: tc.s ) ) ) ) or ( REE::bool5( date5: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) ) 
  LOG::LogSuccess( message: "If statement was successful." );
else
  LOG::LogFailure( message: "If statement failed." );
end if;

if ( ( ( TC4::int7( real7: EE::real2( str2: tc.s ) ) + 999 ) >= ( tc.i ) ) or ( ( TC4::int7( real7: EE::real2( str2: tc.s ) ) + 999 ) > ( tc.i ) ) or ( tc.bool3( date3: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) ) 
  LOG::LogSuccess( message: "If statement was successful." );
else
  LOG::LogFailure( message: "If statement failed." );
end if;

if (  ( tc_4.bool6( date6: EE::date2( evt2: TC::evt4( timer4: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc_4.int6( real6: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc_4.bool6( date6:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) ) 
  LOG::LogSuccess( message: "If statement was successful." );
else
  LOG::LogFailure( message: "If statement failed." );
end if;

if (  ( TC::bool4( date4: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) ) 
  LOG::LogSuccess( message: "If statement was successful." );
else
  LOG::LogFailure( message: "If statement failed." );
end if;

if (  ( TC4::bool7( date7: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) ) 
  LOG::LogSuccess( message: "If statement was successful." );
else
  LOG::LogFailure( message: "If statement failed." );
end if;


//////////////////////////////
// Elif
//////////////////////////////

if ( false )
  LOG::LogFailure( message: "If statement failed." );
elif ( ( ( ::int1( real1: EE::real2( str2: tc.s ) ) - 999 ) <= EE::int2( real2: EE::real2( str2: tc.s ) ) ) or ( ( ::int1( real1: EE::real2( str2: tc.s ) ) - 999 ) < EE::int2( real2: EE::real2( str2: tc.s ) ) ) or  ( ::bool1( date1: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) ) 
  LOG::LogSuccess( message: "ElIf statement was successful." );
else
  LOG::LogFailure( message: "ElIf statement failed." );
end if;

if ( false )
  LOG::LogFailure( message: "If statement failed." );
elif (  ( ( REE::int5( real5: EE::real2( str2: tc.s ) ) - 999 ) <= ( tc.int3( real3: EE::real2( str2: tc.s ) ) ) )  or ( ( REE::int5( real5: EE::real2( str2: tc.s ) ) - 999 ) < ( tc.int3( real3: EE::real2( str2: tc.s ) ) ) )  or ( EE::bool2( date2: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) ) 
  LOG::LogSuccess( message: "ElIf statement was successful." );
else
  LOG::LogFailure( message: "ElIf statement failed." );
end if;

if ( false )
  LOG::LogFailure( message: "If statement failed." );
elif (  ( ( tc_4.int6( real6: EE::real2( str2: tc.s ) ) - 999 ) <= ( TC::int4( real4: EE::real2( str2: tc.s ) ) ) ) or ( ( tc_4.int6( real6: EE::real2( str2: tc.s ) ) - 999 ) < ( TC::int4( real4: EE::real2( str2: tc.s ) ) ) ) or ( REE::bool5( date5: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) ) 
  LOG::LogSuccess( message: "ElIf statement was successful." );
else
  LOG::LogFailure( message: "ElIf statement failed." );
end if;

if ( false )
  LOG::LogFailure( message: "If statement failed." );
elif ( ( ( TC4::int7( real7: EE::real2( str2: tc.s ) ) - 999 ) <= ( tc.i ) ) or ( ( TC4::int7( real7: EE::real2( str2: tc.s ) ) - 999 ) < ( tc.i ) ) or ( tc.bool3( date3: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) ) 
  LOG::LogSuccess( message: "ElIf statement was successful." );
else
  LOG::LogFailure( message: "ElIf statement failed." );
end if;

if ( false )
  LOG::LogFailure( message: "If statement failed." );
elif (  ( tc_4.bool6( date6: EE::date2( evt2: TC::evt4( timer4: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc_4.int6( real6: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc_4.bool6( date6:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) ) 
  LOG::LogSuccess( message: "ElIf statement was successful." );
else
  LOG::LogFailure( message: "ElIf statement failed." );
end if;

if ( false )
  LOG::LogFailure( message: "If statement failed." );
elif (  ( TC::bool4( date4: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) ) 
  LOG::LogSuccess( message: "ElIf statement was successful." );
else
  LOG::LogFailure( message: "ElIf statement failed." );
end if;

if ( false )
  LOG::LogFailure( message: "If statement failed." );
elif (  ( TC4::bool7( date7: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) )
  LOG::LogSuccess( message: "ElIf statement was successful." );
else
  LOG::LogFailure( message: "ElIf statement failed." );
end if;

generate DRV3 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097173,
	2097156,
	0,
	'While',
	22,
	0);
INSERT INTO SM_EIGN
	VALUES (2097173,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097173,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097173,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097173,
	2097154,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097173,
	2097155,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097173,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097173,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097173,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097173,
	2097157,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097173,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097173,
	2097158,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097173,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097173,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097173,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097173,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097173,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097173,
	2097161,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097173,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097173,
	2097162,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097173,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097173,
	2097163,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097173,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097173,
	2097164,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097173,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097173,
	2097165,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097173,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097173,
	2097166,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097173,
	2097156,
	2097173);
INSERT INTO SM_AH
	VALUES (2097173,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097173,
	2097156,
	1,
	'//////////////////////////////
// While
//////////////////////////////

select any tc from instances of TC;
select any tc_4 from instances of TC4;

err_code=true;

while ( ( ( ( ::int1( real1: EE::real2( str2: tc.s ) ) % 10000 ) + 999 ) != ( REE::int5( real5: EE::real2( str2: tc.s ) ) % 10000 ) ) or ( ( ( TC::int4( real4: EE::real2( str2: tc.s ) ) % 10000 ) + 999 ) > TC4::int7( real7: EE::real2( str2: tc.s ) ) ) or  ( ::bool1( date1: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) )
  LOG::LogSuccess( message: "While was successful - 1." );
  err_code = false;
  break;
end while;

if ( err_code )
  LOG::LogFailure( message: "While failed - 1." );
end if;
err_code=true;

while ( ( ( ( TC::int4( real4: EE::real2( str2: tc.s ) ) ) /  ( TC4::int7( real7: EE::real2( str2: tc.s ) ) ) ) == ( ( TC::int4( real4: EE::real2( str2: tc.s ) ) ) * ( TC4::int7( real7: EE::real2( str2: tc.s ) ) ) ) ) or ( EE::bool2( date2: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) )
  LOG::LogSuccess( message: "While was successful - 2." );
  err_code = false;
  break;
end while;

if ( err_code )
  LOG::LogFailure( message: "While failed - 2." );
end if;
err_code=true;

while ( ( ( ( tc_4.int6( real6: EE::real2( str2: tc.s ) ) ) /  ( tc_4.int6( real6: EE::real2( str2: tc.s ) ) % 10000 ) ) == ( ( tc_4.int6( real6: EE::real2( str2: tc.s ) ) ) * ( TC4::int7( real7: EE::real2( str2: tc.s ) ) ) ) ) or  ( REE::bool5( date5: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) )
  LOG::LogSuccess( message: "While was successful - 3." );
  err_code = false;
  break;
end while;

if ( err_code )
  LOG::LogFailure( message: "While failed - 3." );
end if;
err_code=true;

while (  ( tc.bool3( date3: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) )
  LOG::LogSuccess( message: "While was successful - 4." );
  err_code = false;
  break;
end while;

if ( err_code )
  LOG::LogFailure( message: "While failed - 4." );
end if;
err_code=true;

while (  ( tc_4.bool6( date6: EE::date2( evt2: TC::evt4( timer4: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc_4.int6( real6: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc_4.bool6( date6:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) )
  LOG::LogSuccess( message: "While was successful - 5." );
  err_code = false;
  break;
end while;

if ( err_code )
  LOG::LogFailure( message: "While failed - 5." );
end if;
err_code=true;

while (  ( TC::bool4( date4: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) )
  LOG::LogSuccess( message: "While was successful - 6." );
  err_code = false;
  break;
end while;

if ( err_code )
  LOG::LogFailure( message: "While failed - 6." );
end if;
err_code=true;

while (  ( TC4::bool7( date7: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) )
  LOG::LogSuccess( message: "While was successful - 7." );
  err_code = false;
  break;
end while;

if ( err_code )
  LOG::LogFailure( message: "While failed - 7." );
end if;

generate DRV3 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097174,
	2097156,
	0,
	'Attribute Assign',
	23,
	0);
INSERT INTO SM_EIGN
	VALUES (2097174,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097174,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097174,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097174,
	2097154,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097174,
	2097155,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097174,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097174,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097174,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097174,
	2097157,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097174,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097174,
	2097158,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097174,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097174,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097174,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097174,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097174,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097174,
	2097161,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097174,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097174,
	2097162,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097174,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097174,
	2097163,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097174,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097174,
	2097164,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097174,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097174,
	2097165,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097174,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097174,
	2097166,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097174,
	2097156,
	2097174);
INSERT INTO SM_AH
	VALUES (2097174,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097174,
	2097156,
	1,
	'//////////////////////////////
// Attribute Assign
//////////////////////////////

select any tc from instances of TC;
select any tc_4 from instances of TC4;
create object instance tc2 of TC;
create object instance tc3 of TC;
create object instance tc4 of TC;
create object instance tc5 of TC;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Functions
////////////////////////////
tc2.b = ::bool1( date1: ::date1( evt1:tc.e ) ) and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tc2.tr = ::timer1( int1:TC::int4( real4:tc.r )      +    ::int1( real1: tc.real3( str3:tc.s ) ) );
tc2.i = ::int1(real1: ::real1(str1:tc.s)               +   EE::real2( str2: tc.str3( time3:tc.t ) ) );
tc2.r = ::real1(str1: EE::str2(time2:tc.t)          +    tc.str3( time3: TC::time4( id4:tc.ID ) ) );
tc2.s = ::str1( time1: EE::time2( id2: tc.ID ) )  +    tc.str3( time3: TC::time4( id4: tc.ID ) );
tc2.en = ::enum1(udt1:EE::udt2(bool2:tc.b)   +    tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
tc2.u = ::udt1(bool1: tc.bool3(date3:tc.d)    and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Bridges
////////////////////////////
tc3.b = EE::bool2(date2: ::date1(evt1:tc.e))  and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tc3.tr =  EE::timer2(int2: TC::int4(real4:tc.r)     +   ::int1( real1: tc.real3( str3:tc.s ) ) );
tc3.i =  REE::int5(real5: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
tc3.r =  REE::real5(str5:  EE::str2(time2:tc.t)      +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
tc3.s =  EE::str2(time2: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
tc3.en = EE::enum2(udt2: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
tc3.u = EE::udt2(bool2: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Instance Operations
////////////////////////////
tc4.b = tc.bool3(date3: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tc4.tr =  tc.timer3(int3:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
tc4.i =  tc_4.int6(real6: ::real1(str1:tc.s)            +  EE::real2( str2: tc_4.str6( time6:tc.t ) ) );
tc4.r =  tc_4.real6(str6: EE::str2(time2:tc.t)        +   tc_4.str6( time6: TC::time4( id4:tc.ID ) ) );
tc4.s =  tc.str3(time3: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
tc4.en = tc.enum3(udt3: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
tc4.u = tc.udt3(bool3: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Class Operations
////////////////////////////
tc5.b = TC::bool4(date4: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tc5.tr = TC::timer4(int4:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
tc5.i = TC4::int7(real7: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
tc5.r = TC4::real7(str7: EE::str2(time2:tc.t)        +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
tc5.s = TC::str4(time4:tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
tc5.en = TC::enum4(udt4:EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
tc5.u = TC::udt4(bool4: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Results
////////////////////////////
if ( tc2.b and tc3.b and tc4.b and tc5.b )
  LOG::LogSuccess( message:"Assign Attribute - Boolean" );
else
  LOG::LogFailure( message:"Assign Attribute - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:tc2.b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:tc3.b );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:tc4.b );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:tc5.b );
end if;

if ( ( tc2.tr ==  tc3.tr ) and (  tc4.tr ==  tc5.tr ) )
  LOG::LogSuccess( message:"Assign Attribute - Timer" );
else
  LOG::LogFailure( message:"Assign Attribute - Timer");
end if;

if ( ( tc2.i ==  tc3.i ) and (  tc4.i ==  tc5.i ) )
  LOG::LogSuccess( message:"Assign Attribute - Integer" );
else
  LOG::LogFailure( message:"Assign Attribute - Integer");
  LOG::LogInt( message:"Function Invocation", int: tc2.i );
  LOG::LogInt( message:"Bridge Invocation", int: tc3.i );
  LOG::LogInt( message:"Instance Operation Invocation", int: tc4.i );
  LOG::LogInt( message:"Class Operation Invocation", int: tc5.i );
end if;

if ( (  tc2.r ==  tc3.r ) and (  tc4.r ==  tc5.r ) )
  LOG::LogSuccess( message:"Assign Attribute - Real" );
else
  LOG::LogFailure( message:"Assign Attribute - Real");
  LOG::LogReal( message:"Function Invocation", real: tc2.r );
  LOG::LogReal( message:"Bridge Invocation", real: tc3.r );
  LOG::LogReal( message:"Instance Operation Invocation", real: tc4.r );
  LOG::LogReal( message:"Class Operation Invocation", real: tc5.r );
end if;

if ( (  tc2.s ==  tc3.s ) and (  tc4.s ==  tc5.s ) )
  LOG::LogSuccess( message:"Assign Attribute - String" );
else
  LOG::LogFailure( message:"Assign Attribute - String");
  LOG::LogString( message:"Function Invocation", str: tc2.s );
  LOG::LogString( message:"Bridge Invocation", str: tc3.s );
  LOG::LogString( message:"Instance Operation Invocation", str: tc4.s );
  LOG::LogString( message:"Class Operation Invocation", str: tc5.s );
end if;

if ( (  tc2.en ==  tc3.en ) and (  tc4.en ==  tc5.en ) )
  LOG::LogSuccess( message:"Assign Attribute - Enumeration" );
else
  LOG::LogFailure( message:"Assign Attribute - Enumeration");
end if;

if ( (  tc2.u ==  tc3.u ) and (  tc4.u ==  tc5.u ) )
  LOG::LogSuccess( message:"Assign Attribute - User Data Type" );
else
  LOG::LogFailure( message:"Assign Attribute - User Data Type");
  LOG::LogInt( message:"Function Invocation", int: tc2.u );
  LOG::LogInt( message:"Bridge Invocation", int: tc3.u );
  LOG::LogInt( message:"Instance Operation Invocation", int: tc4.u );
  LOG::LogInt( message:"Class Operation Invocation", int: tc5.u );
end if;

generate DRV3 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097175,
	2097156,
	0,
	'Assigner Events',
	24,
	0);
INSERT INTO SM_EIGN
	VALUES (2097175,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097175,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097175,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097175,
	2097154,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097175,
	2097155,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097175,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097175,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097175,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097175,
	2097157,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097175,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097175,
	2097158,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097175,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097175,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097175,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097175,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097175,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097175,
	2097161,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097175,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097175,
	2097162,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097175,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097175,
	2097163,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097175,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097175,
	2097164,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097175,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097175,
	2097165,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097175,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097175,
	2097166,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097175,
	2097156,
	2097175);
INSERT INTO SM_AH
	VALUES (2097175,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097175,
	2097156,
	1,
	'//////////////////////////////
// Gerenated Assigner Events
//////////////////////////////

select any tc from instances of TC;
select any tc_4 from instances of TC4;

// Order is function, bridge, instance operation, class operation
////////////////////////////////////////////////
// Events - Single Invocation
////////////////////////////////////////////////

LOG::LogInfo(message: "Single Invocation - Assigner");

generate TC2_A4( bool5: ::bool1(date1:tc.d ) ) to TC2 assigner;
generate TC2_A5( date5: EE::date2( evt2:tc.e ) ) to TC2 assigner;
generate TC2_A6( enum5: tc.enum3( udt3: tc.u ) ) to TC2 assigner;
generate TC2_A7( evt5: TC::evt4( timer4: tc.tr ) ) to TC2 assigner;
generate TC2_A8( id5: ::id1( enum1: tc.en ) ) to TC2 assigner;
generate TC2_A9( int5: REE::int5( real5: tc.r ) ) to TC2 assigner;
generate TC2_A10( real5: tc_4.real6( str6: tc.s ) ) to TC2 assigner;
generate TC2_A11( str5: TC::str4( time4: tc.t ) ) to TC2 assigner;
generate TC2_A12( time5: ::time1( id1: tc.ID ) ) to TC2 assigner;
generate TC2_A13( timer5: EE::timer2( int2: tc.i ) ) to TC2 assigner;
generate TC2_A14( udt5: tc.udt3( bool3: tc.b ) ) to TC2 assigner;

////////////////////////////////////////////////
// Events - Multiple Invocation
////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation - Assigner");

generate TC2_A4( bool5: ::bool1(date1: EE::date2( evt2:tc.e ) ) ) to TC2 assigner;
generate TC2_A5( date5: EE::date2( evt2:TC::evt4( timer4: tc.tr ) ) ) to TC2 assigner;
generate TC2_A6( enum5: tc.enum3( udt3: tc.udt3( bool3: tc.b ) ) ) to TC2 assigner;
generate TC2_A7( evt5: TC::evt4( timer4: EE::timer2( int2: tc.i ) ) ) to TC2 assigner;
generate TC2_A8( id5: ::id1( enum1: tc.enum3( udt3:tc.u ) ) ) to TC2 assigner;
generate TC2_A9( int5: EE::int2( real2:tc.real3( str3: tc.s ) ) ) to TC2 assigner;
generate TC2_A10( real5: tc_4.real6( str6: TC::str4( time4: tc.t ) ) ) to TC2 assigner;
generate TC2_A11( str5: TC4::str7( time7: ::time1( id1: tc.ID ) ) ) to TC2 assigner;
generate TC2_A12( time5: ::time1( id1: ::id1( enum1:tc.en ) ) ) to TC2 assigner;
generate TC2_A13( timer5: EE::timer2( int2: EE::int2( real2:tc.r ) ) ) to TC2 assigner;
generate TC2_A14( udt5: tc.udt3( bool3: ::bool1( date1:tc.d ) ) ) to TC2 assigner;

////////////////////////////////////////////////////////////////////////////
// Events - Multiple Invocation w/ Expressions
////////////////////////////////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation with Expressions - Assigner");

generate TC2_A4( bool5: ::bool1(date1: EE::date2( evt2:tc.e ) )  and tc.bool3( date3: tc.d ) ) to TC2 assigner;
generate TC2_A5( date5: EE::date2( evt2:TC::evt4( timer4: tc.tr ) ) ) to TC2 assigner;
generate TC2_A6( enum5: tc.enum3( udt3: tc.udt3( bool3: tc.b ) + TC::udt4( bool4: tc.b ) ) ) to TC2 assigner;
generate TC2_A7( evt5: TC::evt4( timer4: EE::timer2( int2: tc.i ) ) ) to TC2 assigner;
generate TC2_A8( id5: ::id1( enum1: tc.enum3( udt3:tc.u ) ) ) to TC2 assigner;
generate TC2_A9( int5: REE::int5( real5:tc.real3( str3: tc.s ) )  + tc.int3( real3: tc.r ) ) to TC2 assigner;
generate TC2_A10( real5: tc.real3( str3: TC::str4( time4: tc.t ) )  + ::real1( str1: tc.s ) ) to TC2 assigner;
generate TC2_A11( str5: TC4::str7( time7: ::time1( id1: tc.ID ) ) + EE::str2( time2: tc.t ) ) to TC2 assigner;
generate TC2_A12( time5: ::time1( id1: ::id1( enum1:tc.en ) ) ) to TC2 assigner;
generate TC2_A13( timer5: EE::timer2( int2: EE::int2( real2:tc.r )  + tc.int3( real3: tc.r ) ) ) to TC2 assigner;
generate TC2_A14( udt5: tc.udt3( bool3: ::bool1( date1:tc.d ) and EE::bool2( date2: tc.d ) ) + TC::udt4( bool4: tc.b ) ) to TC2 assigner;

//////////////////////////////
// Pre-created Assigner Events
//////////////////////////////
// Order is function, bridge, instance operation, class operation

////////////////////////////////////////////////
// Events - Single Invocation
////////////////////////////////////////////////

LOG::LogInfo(message: "Single Invocation - Assigner");

create event instance e1 of TC2_A4( bool5: ::bool1(date1:tc.d ) ) to TC2 assigner;
create event instance e2 of TC2_A5( date5: EE::date2( evt2:tc.e ) ) to TC2 assigner;
create event instance e3 of TC2_A6( enum5: tc.enum3( udt3: tc.u ) ) to TC2 assigner;
create event instance e4 of TC2_A7( evt5: TC::evt4( timer4: tc.tr ) ) to TC2 assigner;
create event instance e5 of TC2_A8( id5: ::id1( enum1: tc.en ) ) to TC2 assigner;
create event instance e6 of TC2_A9( int5: REE::int5( real5: tc.r ) ) to TC2 assigner;
create event instance e7 of TC2_A10( real5: tc_4.real6( str6: tc.s ) ) to TC2 assigner;
create event instance e8 of TC2_A11( str5: TC::str4( time4: tc.t ) ) to TC2 assigner;
create event instance e9 of TC2_A12( time5: ::time1( id1: tc.ID ) ) to TC2 assigner;
create event instance e10 of TC2_A13( timer5: EE::timer2( int2: tc.i ) ) to TC2 assigner;
create event instance e11 of TC2_A14( udt5: tc.udt3( bool3: tc.b ) ) to TC2 assigner;

////////////////////////////////////////////////
// Events - Multiple Invocation
////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation - Assigner");

create event instance e12 of TC2_A4( bool5: ::bool1(date1: EE::date2( evt2:tc.e ) ) ) to TC2 assigner;
create event instance e13 of TC2_A5( date5: EE::date2( evt2:TC::evt4( timer4: tc.tr ) ) ) to TC2 assigner;
create event instance e14 of TC2_A6( enum5: tc.enum3( udt3: tc.udt3( bool3: tc.b ) ) ) to TC2 assigner;
create event instance e15 of TC2_A7( evt5: TC::evt4( timer4: EE::timer2( int2: tc.i ) ) ) to TC2 assigner;
create event instance e16 of TC2_A8( id5: ::id1( enum1: tc.enum3( udt3:tc.u ) ) ) to TC2 assigner;
create event instance e17 of TC2_A9( int5: EE::int2( real2:tc.real3( str3: tc.s ) ) ) to TC2 assigner;
create event instance e18 of TC2_A10( real5: tc_4.real6( str6: TC::str4( time4: tc.t ) ) ) to TC2 assigner;
create event instance e19 of TC2_A11( str5: TC4::str7( time7: ::time1( id1: tc.ID ) ) ) to TC2 assigner;
create event instance e20 of TC2_A12( time5: ::time1( id1: ::id1( enum1:tc.en ) ) ) to TC2 assigner;
create event instance e21 of TC2_A13( timer5: EE::timer2( int2: EE::int2( real2:tc.r ) ) ) to TC2 assigner;
create event instance e22 of TC2_A14( udt5: tc.udt3( bool3: ::bool1( date1:tc.d ) ) ) to TC2 assigner;

////////////////////////////////////////////////////////////////////////////
// Events - Multiple Invocation w/ Expressions
////////////////////////////////////////////////////////////////////////////

LOG::LogInfo(message: "Multiple Invocation with Expressions - Assigner");

create event instance e23 of TC2_A4( bool5: ::bool1(date1: EE::date2( evt2:tc.e ) )  and tc.bool3( date3: tc.d ) ) to TC2 assigner;
create event instance e24 of TC2_A5( date5: EE::date2( evt2:TC::evt4( timer4: tc.tr ) ) ) to TC2 assigner;
create event instance e25 of TC2_A6( enum5: tc.enum3( udt3: tc.udt3( bool3: tc.b ) + TC::udt4( bool4: tc.b ) ) ) to TC2 assigner;
create event instance e26 of TC2_A7( evt5: TC::evt4( timer4: EE::timer2( int2: tc.i ) ) ) to TC2 assigner;
create event instance e27 of TC2_A8( id5: ::id1( enum1: tc.enum3( udt3:tc.u ) ) ) to TC2 assigner;
create event instance e28 of TC2_A9( int5: REE::int5( real5:tc.real3( str3: tc.s ) )  + tc.int3( real3: tc.r ) ) to TC2 assigner;
create event instance e29 of TC2_A10( real5: tc.real3( str3: TC::str4( time4: tc.t ) )  + ::real1( str1: tc.s ) ) to TC2 assigner;
create event instance e30 of TC2_A11( str5: TC4::str7( time7: ::time1( id1: tc.ID ) ) + EE::str2( time2: tc.t ) ) to TC2 assigner;
create event instance e31 of TC2_A12( time5: ::time1( id1: ::id1( enum1:tc.en ) ) ) to TC2 assigner;
create event instance e32 of TC2_A13( timer5: EE::timer2( int2: EE::int2( real2:tc.r )  + tc.int3( real3: tc.r ) ) ) to TC2 assigner;
create event instance e33 of TC2_A14( udt5: tc.udt3( bool3: ::bool1( date1:tc.d ) and EE::bool2( date2: tc.d ) ) + TC::udt4( bool4: tc.b ) ) to TC2 assigner;',
	'');
INSERT INTO SM_STATE
	VALUES (2097176,
	2097156,
	0,
	'Realized Invocations - Single',
	25,
	0);
INSERT INTO SM_EIGN
	VALUES (2097176,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097176,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097176,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097176,
	2097154,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097176,
	2097155,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097176,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097176,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097176,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097176,
	2097157,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097176,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097176,
	2097158,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097176,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097176,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097176,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097176,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097176,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097176,
	2097161,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097176,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097176,
	2097162,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097176,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097176,
	2097163,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097176,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097176,
	2097164,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097176,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097176,
	2097165,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097176,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097176,
	2097166,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097176,
	2097156,
	2097176);
INSERT INTO SM_AH
	VALUES (2097176,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097176,
	2097156,
	1,
	'//////////////////////////////
// Realized Invocations
//////////////////////////////

select any tc from instances of TC;

////////////////////////////
// Bridges
////////////////////////////
b5 = REE::bool5(date5:tc.d);
i5 =  REE::int5(real5:tc.r);
r5 =  REE::real5(str5:tc.s);
s5 =  REE::str5(time5:tc.t);
en5 = REE::enum5(udt5:tc.u);
u5 = REE::udt5(bool5:tc.b);

select any tc4 from instances of TC4;

////////////////////////////
// Instance Operations
////////////////////////////
b6 = tc4.bool6(date6:tc.d);
i6 =  tc4.int6(real6:tc.r);
r6 =  tc4.real6(str6:tc.s);
s6 =  tc4.str6(time6:tc.t);
en6 = tc4.enum6(udt6:tc.u);
u6 = tc4.udt6(bool6:tc.b);

////////////////////////////
// Class Operations
////////////////////////////
b7 = TC4::bool7(date7:tc.d);
i7 = TC4::int7(real7:tc.r);
r7 = TC4::real7(str7:tc.s);
s7 = TC4::str7(time7:tc.t);
en7 = TC4::enum7(udt7:tc.u);
u7 = TC4::udt7(bool7:tc.b);

////////////////////////////
// Results
////////////////////////////
if ( b5 and b6 and b7 )
  LOG::LogSuccess( message:"Single Invocation - Boolean" );
else
  LOG::LogFailure( message:"Single Invocation - Boolean");
  LOG::LogBoolean( message:"Realized Bridge Invocation", bool:b5 );
  LOG::LogBoolean( message:"Realized Instance Operation Invocation", bool:b6 );
  LOG::LogBoolean( message:"Realized Class Operation Invocation", bool:b7 );
end if;

if ( ( i5 == i6 ) and ( i6 == i7 ) )
  LOG::LogSuccess( message:"Single Invocation - Integer" );
else
  LOG::LogFailure( message:"Single Invocation - Integer");
  LOG::LogInt( message:"Realized Bridge Invocation", int:i5 );
  LOG::LogInt( message:"Realized Instance Operation Invocation", int:i6 );
  LOG::LogInt( message:"Realized Class Operation Invocation", int:i7 );
end if;

if ( ( r5 == r6 ) and ( r6 == r7 ) )
  LOG::LogSuccess( message:"Single Invocation - Real" );
else
  LOG::LogFailure( message:"Single Invocation - Real");
  LOG::LogReal( message:"Realized Bridge Invocation", real:r5 );
  LOG::LogReal( message:"Realized Instance Operation Invocation", real:r6 );
  LOG::LogReal( message:"Realized Class Operation Invocation", real:r7 );
end if;

if ( ( s5 == s6 ) and ( s6 == s7 ) )
  LOG::LogSuccess( message:"Single Invocation - String" );
else
  LOG::LogFailure( message:"Single Invocation - String");
  LOG::LogString( message:"Realized Bridge Invocation", str:s5 );
  LOG::LogString( message:"Realized Instance Operation Invocation", str:s6 );
  LOG::LogString( message:"Realized Class Operation Invocation", str:s7 );
end if;

if ( ( en5 == en6 ) and ( en6 == en7 ) )
  LOG::LogSuccess( message:"Single Invocation - Enumeration" );
else
  LOG::LogFailure( message:"Single Invocation - Enumeration");
end if;

if ( ( u5 == u6 ) and ( u6 == u7 ) )
  LOG::LogSuccess( message:"Single Invocation - User Data Type" );
else
  LOG::LogFailure( message:"Single Invocation - User Data Type");
  LOG::LogInt( message:"Realized Bridge Invocation", int:u5 );
  LOG::LogInt( message:"Realized Instance Operation Invocation", int:u6 );
  LOG::LogInt( message:"Realized Class Operation Invocation", int:u7 );
end if;

generate DRV3 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097177,
	2097156,
	0,
	'Return',
	26,
	0);
INSERT INTO SM_EIGN
	VALUES (2097177,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097177,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097177,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097177,
	2097154,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097177,
	2097155,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097177,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097177,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097177,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097177,
	2097157,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097177,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097177,
	2097158,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097177,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097177,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097177,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097177,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097177,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097177,
	2097161,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097177,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097177,
	2097162,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097177,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097177,
	2097163,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097177,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097177,
	2097164,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097177,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097177,
	2097165,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097177,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097177,
	2097166,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097177,
	2097156,
	2097177);
INSERT INTO SM_AH
	VALUES (2097177,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097177,
	2097156,
	1,
	'//////////////////////////////
// Return
//////////////////////////////

select any tc from instances of TC;

if ( ::ret1( ) AND EE::ret2( ) AND EE::ret5( ) AND tc.ret3( ) AND tc.ret6( ) AND TC::ret4( ) AND TC::ret7( ) )
  LOG::LogSuccess( message: "Nested invocation return value was successful."  );
else
  LOG::LogFailure( message: "Nested invocation return value failed."  );
end if;

generate DRV3 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097178,
	2097156,
	0,
	'Select related by where',
	27,
	0);
INSERT INTO SM_EIGN
	VALUES (2097178,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097178,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097178,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097178,
	2097154,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097178,
	2097155,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097178,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097178,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097178,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097178,
	2097157,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097178,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097178,
	2097158,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097178,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097178,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097178,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097178,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097178,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097178,
	2097161,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097178,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097178,
	2097162,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097178,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097178,
	2097163,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097178,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097178,
	2097164,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097178,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097178,
	2097165,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097178,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097178,
	2097166,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097178,
	2097156,
	2097178);
INSERT INTO SM_AH
	VALUES (2097178,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097178,
	2097156,
	1,
	'select any tc from instances of TC;
select any tc_2 from instances of TC2;
select any tc_3 from instances of TC3;
select any tc_4 from instances of TC4;

//////////////////////////////
// Function
// Select any related by where
//////////////////////////////
select any tc2 related by tc_2->TC[R1] where ( selected.b == ( ::bool1( date1: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + ( + EE::real2( str2: tc.s ) ) ) + ( + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) == ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select many related by where
//////////////////////////////
select many tc3 related by tc_2->TC[R1] where ( ( NOT selected.b ) != ( ::bool1( date1: EE::date2( evt2: TC::evt4( timer4: TC::timer4( int4: ::int1( real1: tc.r + ( + REE::real5( str5: tc.s ) ) ) + ( + tc_4.int6( real6: TC::real4( str4: tc.s ) ) ) ) ) ) ) and ( ( REE::bool5( date5:tc.d ) == ( tc_4.bool6( date6:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select one related by where
//////////////////////////////
select one tc4 related by tc_3->TC[R2] where ( selected.b == ( ::bool1( date1: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + ( - EE::real2( str2: tc.s ) ) ) + ( - tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) != ( NOT tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Bridge
// Select any related by where
//////////////////////////////
select any tc5 related by tc_2->TC[R1] where ( selected.b == ( EE::bool2( date2: EE::date2( evt2: ::evt1( timer1: TC::timer4( int4: ::int1( real1: tc.r + ( - REE::real5( str5: tc.s ) ) ) + ( - tc_4.int6( real6: TC::real4( str4: tc.s ) ) ) ) ) ) ) and ( ( REE::bool5( date5:tc.d ) != ( NOT tc_4.bool6( date6:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select many related by where
//////////////////////////////
select many tc6 related by tc_2->TC[R1] where ( selected.b == ( EE::bool2( date2: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + ( + TC::real4( str4: tc.s ) ) ) + ( + TC4::int7( real7: TC::real4( str4: tc.s ) ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select one related by where
//////////////////////////////
select one tc7 related by tc_3->TC[R2] where ( selected.b == ( EE::bool2( date2: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + ( - TC::real4( str4: tc.s ) ) ) + ( - TC4::int7( real7: TC::real4( str4: tc.s ) ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Realized Bridge
// Select any related by where
//////////////////////////////
select any tc8 related by tc_2->TC[R1] where ( selected.b == ( REE::bool5( date5: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select many related by where
//////////////////////////////
select many tc9 related by tc_2->TC[R1] where ( selected.b == ( REE::bool5( date5: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select one related by where
//////////////////////////////
select one tc10 related by tc_3->TC[R2] where ( selected.b == ( REE::bool5( date5: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Instance Operation
// Select any related by where
//////////////////////////////
select any tc11 related by tc_2->TC[R1] where ( selected.b == ( tc.bool3( date3: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select many related by where
//////////////////////////////
select many tc12 related by tc_2->TC[R1] where ( selected.b == ( tc.bool3( date3: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select one related by where
//////////////////////////////
select one tc13 related by tc_3->TC[R2] where ( selected.b == ( tc.bool3( date3: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Realized Instance Operation
// Select any related by where
//////////////////////////////
select any tc14 related by tc_2->TC[R1] where ( selected.b == ( tc_4.bool6( date6: EE::date2( evt2: TC::evt4( timer4: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc_4.int6( real6: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc_4.bool6( date6:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select many related by where
//////////////////////////////
select many tc15 related by tc_2->TC[R1] where ( selected.b == ( tc_4.bool6( date6: EE::date2( evt2: TC::evt4( timer4: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc_4.int6( real6: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc_4.bool6( date6:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select one related by where
//////////////////////////////
select one tc16 related by tc_3->TC[R2] where ( selected.b == ( tc_4.bool6( date6: EE::date2( evt2: TC::evt4( timer4: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc_4.int6( real6: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc_4.bool6( date6:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Class Operation
// Select any related by where
//////////////////////////////
select any tc17 related by tc_2->TC[R1] where ( selected.b == ( TC::bool4( date4: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select many related by where
//////////////////////////////
select many tc18 related by tc_2->TC[R1] where ( ( NOT selected.b ) != ( TC::bool4( date4: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select one related by where
//////////////////////////////
select one tc19 related by tc_3->TC[R2] where ( selected.b == ( TC::bool4( date4: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Realized Class Operation
// Select any related by where
//////////////////////////////
select any tc20 related by tc_2->TC[R1] where ( ( NOT selected.b ) != ( TC4::bool7( date7: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select many related by where
//////////////////////////////
select many tc21 related by tc_2->TC[R1] where ( selected.b == ( TC4::bool7( date7: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s )  ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Select one related by where
//////////////////////////////
select one tc22 related by tc_3->TC[R2] where ( selected.b == ( TC4::bool7( date7: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + ( + ::real1( str1: tc.s ) ) ) + ( - ::int1( real1: TC::real4( str4: tc.s ) ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) ) or ( ( ::bool1( date1:tc.d ) ) and TC::bool4( date4:tc.d ) ) ) ) );

//////////////////////////////
// Verify results
//////////////////////////////
if ( ( tc2 ==  tc5 ) AND ( ( tc8 ==  tc11 ) AND ( ( tc14 == tc17 ) AND (  tc20 == tc2 ) ) ) )
  LOG::LogSuccess( message: "Select any related by where was successful." );
else
  LOG::LogFailure( message: "Select any related by where failed." );
end if;

if ( ( tc3 ==  tc6 ) AND ( ( tc9 ==  tc12 ) AND ( ( tc15 == tc18 ) AND (  tc21 == tc3 ) ) ) )
  LOG::LogSuccess( message: "Select many related by where was successful." );
else
  LOG::LogFailure( message: "Select many related by where failed." );
end if;

if ( ( tc4 ==  tc7 ) AND ( ( tc10 ==  tc13 ) AND ( ( tc16 == tc19 ) AND (  tc22 == tc4 ) ) ) )
  LOG::LogSuccess( message: "Select many related by where was successful." );
else
  LOG::LogFailure( message: "Select many related by where failed." );
end if;

generate DRV3 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097179,
	2097156,
	0,
	'Realized Invocations - Multiple with Expression',
	29,
	0);
INSERT INTO SM_EIGN
	VALUES (2097179,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097179,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097179,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097179,
	2097154,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097179,
	2097155,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097179,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097179,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097179,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097179,
	2097157,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097179,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097179,
	2097158,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097179,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097179,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097179,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097179,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097179,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097179,
	2097161,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097179,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097179,
	2097162,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097179,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097179,
	2097163,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097179,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097179,
	2097164,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097179,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097179,
	2097165,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097179,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097179,
	2097166,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097179,
	2097156,
	2097179);
INSERT INTO SM_AH
	VALUES (2097179,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097179,
	2097156,
	1,
	'select any tc from instances of TC;

select any tc7 from instances of TC4;
select any tc5 from instances of TC5;
// Order is Bridge, Instance Operation, Class Operation

////////////////////////////
// Bridges
////////////////////////////
b5 = REE::bool5(date5: ::date1(evt1:tc.e))  and REE::bool5(date5: tc.date3( evt3:tc.e ) );
i5 =  REE::int5(real5: ::real1(str1:tc.s)            +  EE::real2( str2: REE::str5( time5:tc.t ) ) );
r5 =  REE::real5(str5:  REE::str5(time5:tc.t)      +   tc7.str6( time6: TC::time4( id4:tc.ID ) ) );
s5 =  REE::str5(time5: tc.time3(id3:tc.ID))       +  tc7.str6( time6: TC::time4( id4: tc.ID ) );
en5 = REE::enum5(udt5: REE::udt5(bool5:tc.b) +  tc7.udt6( bool6: TC4::bool7( date7:tc.d ) ) );
u5 = REE::udt5(bool5: tc7.bool6(date6:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Instance Operations
////////////////////////////
b6 = tc7.bool6(date6: ::date1(evt1:tc.e))   and REE::bool5(date5: TC::date4( evt4:tc.e ) );
i6 =  tc7.int6(real6: ::real1(str1:tc.s)            +  REE::real5( str5: tc7.str6( time6:tc.t ) ) );
r6 =  tc7.real6(str6: REE::str5(time5:tc.t)        +   tc7.str6( time6: TC::time4( id4:tc.ID ) ) );
s6 =  tc7.str6(time6: TC::time4(id4:tc.ID))       +  tc7.str6( time6: TC::time4( id4: tc.ID ) );
en6 = tc7.enum6(udt6: REE::udt5(bool5:tc.b) +  tc7.udt6( bool6: REE::bool5( date5:tc.d ) ) );
u6 = tc7.udt6(bool6: tc7.bool6(date6:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

// bp:2946
b8 = tc7.bool6(date6: ::date1(evt1:tc.e))   and tc5.bool8(date8: TC::date4( evt4:tc.e ) );
u8 = tc7.udt6(bool6: tc5.bool9(date9:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Class Operations
////////////////////////////
b7 = TC4::bool7(date7: ::date1(evt1:tc.e))   and REE::bool5(date5: tc.date3( evt3:tc.e ) );
i7 = TC4::int7(real7: ::real1(str1:tc.s)            +  REE::real5( str5: tc7.str6( time6:tc.t ) ) );
r7 = TC4::real7(str7: REE::str5(time5:tc.t)        +   tc7.str6( time6: TC::time4( id4:tc.ID ) ) );
s7 = TC4::str7(time7:tc.time3(id3:tc.ID))       +  TC::str4( time4: TC::time4( id4: tc.ID ) );
en7 = TC4::enum7(udt7:REE::udt5(bool5:tc.b) +  tc7.udt6( bool6: TC4::bool7( date7:tc.d ) ) );
u7 = TC4::udt7(bool7: tc7.bool6(date6:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Results
////////////////////////////
if ( b5 and b6 and b7 and b8 )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Boolean" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Boolean");
  LOG::LogBoolean( message:"Bridge Invocation", bool:b5 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b6 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b7 );
  LOG::LogBoolean( message:"Instance Operation Invocation - bp:2946", bool:b8 );
end if;

if ( ( i5 == i6 ) and ( i6 == i7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Integer" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Integer");
  LOG::LogInt( message:"Bridge Invocation", int:i5 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i6 );
  LOG::LogInt( message:"Class Operation Invocation", int:i7 );
end if;

if ( ( r5 == r6 ) and ( r6 == r7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Real" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Real");
  LOG::LogReal( message:"Bridge Invocation", real:r5 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r6 );
  LOG::LogReal( message:"Class Operation Invocation", real:r7 );
end if;

if ( ( s5 == s6 ) and ( s6 == s7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - String" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - String");
  LOG::LogString( message:"Bridge Invocation", str:s5 );
  LOG::LogString( message:"Instance Operation Invocation", str:s6 );
  LOG::LogString( message:"Class Operation Invocation", str:s7 );
end if;

if ( ( en5 == en6 ) and ( en6 == en7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Enumeration" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Enumeration");
end if;

if ( ( u5 == u6 ) and ( u7 == u8 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - User Data Type" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - User Data Type");
  LOG::LogInt( message:"Bridge Invocation", int:u5 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u6 );
  LOG::LogInt( message:"Class Operation Invocation", int:u7 );
  LOG::LogInt( message:"Instance Operation Invocation - bp:2946", int:u8 );
end if;

generate DRV3 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097180,
	2097156,
	0,
	'Realized Invocations - Multiple',
	30,
	0);
INSERT INTO SM_EIGN
	VALUES (2097180,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097180,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097180,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097180,
	2097154,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097180,
	2097155,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097180,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097180,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097180,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097180,
	2097157,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097180,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097180,
	2097158,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097180,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097180,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097180,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097180,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097180,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097180,
	2097161,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097180,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097180,
	2097162,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097180,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097180,
	2097163,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097180,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097180,
	2097164,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097180,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097180,
	2097165,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097180,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097180,
	2097166,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097180,
	2097156,
	2097180);
INSERT INTO SM_AH
	VALUES (2097180,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097180,
	2097156,
	1,
	'select any tc from instances of TC;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Bridges
////////////////////////////
b5 = REE::bool5(date5: ::date1(evt1:tc.e));
i5 =  REE::int5(real5: ::real1(str1:tc.s));
r5 =  REE::real5(str5:  EE::str2(time2:tc.t));
s5 =  REE::str5(time5: tc.time3(id3:tc.ID));
en5 = REE::enum5(udt5: EE::udt2(bool2:tc.b));
u5 = REE::udt5(bool5: tc.bool3(date3:tc.d));

select any tc4 from instances of TC4;
select any tc5 from instances of TC5;

////////////////////////////
// Instance Operations
////////////////////////////
b6 = tc4.bool6(date6: ::date1(evt1:tc.e));
i6 =  tc4.int6(real6: ::real1(str1:tc.s));
r6 =  tc4.real6(str6: EE::str2(time2:tc.t));
en6 = tc4.enum6(udt6: EE::udt2(bool2:tc.b));

// bp:2946
s6 =  tc4.str6(time6: tc.time3(id3:tc.ID));
u6 = tc4.udt6(bool6: tc5.bool9(date9:tc.d));

////////////////////////////
// Class Operations
////////////////////////////
b7 = TC4::bool7(date7: ::date1(evt1:tc.e));
i7 = TC4::int7(real7: ::real1(str1:tc.s));
r7 = TC4::real7(str7: EE::str2(time2:tc.t));
s7 = TC4::str7(time7:tc.time3(id3:tc.ID));
en7 = TC4::enum7(udt7:EE::udt2(bool2:tc.b));
u7 = TC4::udt7(bool7: tc.bool3(date3:tc.d));

////////////////////////////
// Results
////////////////////////////
if ( b5 and b6 and b7 )
  LOG::LogSuccess( message:"Multiple Invocation - Boolean" );
else
  LOG::LogFailure( message:"Multiple Invocation - Boolean");
  LOG::LogBoolean( message:"Realized Bridge Invocation", bool:b5 );
  LOG::LogBoolean( message:"Realized Instance Operation Invocation", bool:b6 );
  LOG::LogBoolean( message:"Realized Class Operation Invocation", bool:b7 );
end if;

if ( ( i5 == i6 ) and ( i6 == i7 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Integer" );
else
  LOG::LogFailure( message:"Multiple Invocation - Integer");
  LOG::LogInt( message:"Realized Bridge Invocation", int:i5 );
  LOG::LogInt( message:"Realized Instance Operation Invocation", int:i6 );
  LOG::LogInt( message:"Realized Class Operation Invocation", int:i7 );
end if;

if ( ( r5 == r6 ) and ( r6 == r7 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Real" );
else
  LOG::LogFailure( message:"Multiple Invocation - Real");
  LOG::LogReal( message:"Realized Bridge Invocation", real:r5 );
  LOG::LogReal( message:"Realized Instance Operation Invocation", real:r6 );
  LOG::LogReal( message:"Realized Class Operation Invocation", real:r7 );
end if;

if ( ( s5 == s6 ) and ( s6 == s7 ) )
  LOG::LogSuccess( message:"Multiple Invocation - String" );
else
  LOG::LogFailure( message:"Multiple Invocation - String");
  LOG::LogString( message:"Realized Bridge Invocation", str:s5 );
  LOG::LogString( message:"Realized Instance Operation Invocation", str:s6 );
  LOG::LogString( message:"Realized Class Operation Invocation", str:s7 );
end if;

if ( ( en5 == en6 ) and ( en6 == en7 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Enumeration" );
else
  LOG::LogFailure( message:"Multiple Invocation - Enumeration");
end if;

if ( ( u5 == u6 ) and ( u6 == u7 ) )
  LOG::LogSuccess( message:"Multiple Invocation - User Data Type" );
else
  LOG::LogFailure( message:"Multiple Invocation - User Data Type");
  LOG::LogInt( message:"Realized Bridge Invocation", int:u5 );
  LOG::LogInt( message:"Realized Instance Operation Invocation", int:u6 );
  LOG::LogInt( message:"Realized Class Operation Invocation", int:u7 );
end if;

generate DRV3 to self;',
	'');
INSERT INTO SM_STATE
	VALUES (2097181,
	2097156,
	0,
	'Assign Params',
	31,
	0);
INSERT INTO SM_EIGN
	VALUES (2097181,
	2097153,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097181,
	2097153,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097181,
	2097154,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097181,
	2097154,
	2097156,
	0);
INSERT INTO SM_SEME
	VALUES (2097181,
	2097155,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097181,
	2097156,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097181,
	2097156,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097181,
	2097157,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097181,
	2097157,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097181,
	2097158,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097181,
	2097158,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097181,
	2097159,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097181,
	2097159,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097181,
	2097160,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097181,
	2097160,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097181,
	2097161,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097181,
	2097161,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097181,
	2097162,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097181,
	2097162,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097181,
	2097163,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097181,
	2097163,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097181,
	2097164,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097181,
	2097164,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097181,
	2097165,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097181,
	2097165,
	2097156,
	0);
INSERT INTO SM_EIGN
	VALUES (2097181,
	2097166,
	2097156,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2097181,
	2097166,
	2097156,
	0);
INSERT INTO SM_MOAH
	VALUES (2097181,
	2097156,
	2097181);
INSERT INTO SM_AH
	VALUES (2097181,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097181,
	2097156,
	1,
	'//////////////////////////////
// Assign Params
//////////////////////////////

select any tc from instances of TC;

create object instance tc2 of TC;
create object instance tc3 of TC;
create object instance tc4 of TC;
create object instance tc5 of TC;

::mod_params1( 	b: tc2.b, tr: tc2.tr, i: tc2.i, r: tc2.r, s: tc2.s, en: tc2.en, u: tc2.u, 
		b2: tc3.b, tr2: tc3.tr, i2: tc3.i, r2: tc3.r, s2: tc3.s, en2: tc3.en, u2: tc3.u, 
		b3: tc4.b, tr3: tc4.tr, i3:tc4.i, r3: tc4.r, s3: tc4.s, en3: tc4.en, u3: tc4.u, 
		b4: tc5.b, tr4: tc5.tr, i4:tc5.i, r4: tc5.r, s4: tc5.s, en4: tc5.en, u4: tc5.u );


////////////////////////////
// Verify Function Results
////////////////////////////
if ( tc2.b and tc3.b and tc4.b and tc5.b )
  LOG::LogSuccess( message:"Assign Parameter - Boolean" );
else
  LOG::LogFailure( message:"Assign Parameter - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:tc2.b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:tc3.b );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:tc4.b );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:tc5.b );
end if;

if ( ( tc2.tr ==  tc3.tr ) and (  tc4.tr ==  tc5.tr ) )
  LOG::LogSuccess( message:"Assign Parameter - Timer" );
else
  LOG::LogFailure( message:"Assign Parameter - Timer");
end if;

if ( ( tc2.i ==  tc3.i ) and (  tc4.i ==  tc5.i ) )
  LOG::LogSuccess( message:"Assign Parameter - Integer" );
else
  LOG::LogFailure( message:"Assign Parameter - Integer");
  LOG::LogInt( message:"Function Invocation", int: tc2.i );
  LOG::LogInt( message:"Bridge Invocation", int: tc3.i );
  LOG::LogInt( message:"Instance Operation Invocation", int: tc4.i );
  LOG::LogInt( message:"Class Operation Invocation", int: tc5.i );
end if;

if ( (  tc2.r ==  tc3.r ) and (  tc4.r ==  tc5.r ) )
  LOG::LogSuccess( message:"Assign Parameter - Real" );
else
  LOG::LogFailure( message:"Assign Parameter - Real");
  LOG::LogReal( message:"Function Invocation", real: tc2.r );
  LOG::LogReal( message:"Bridge Invocation", real: tc3.r );
  LOG::LogReal( message:"Instance Operation Invocation", real: tc4.r );
  LOG::LogReal( message:"Class Operation Invocation", real: tc5.r );
end if;

if ( (  tc2.s ==  tc3.s ) and (  tc4.s ==  tc5.s ) )
  LOG::LogSuccess( message:"Assign Parameter - String" );
else
  LOG::LogFailure( message:"Assign Parameter - String");
  LOG::LogString( message:"Function Invocation", str: tc2.s );
  LOG::LogString( message:"Bridge Invocation", str: tc3.s );
  LOG::LogString( message:"Instance Operation Invocation", str: tc4.s );
  LOG::LogString( message:"Class Operation Invocation", str: tc5.s );
end if;

if ( (  tc2.en ==  tc3.en ) and (  tc4.en ==  tc5.en ) )
  LOG::LogSuccess( message:"Assign Parameter - Enumeration" );
else
  LOG::LogFailure( message:"Assign Parameter - Enumeration");
end if;

if ( (  tc2.u ==  tc3.u ) and (  tc4.u ==  tc5.u ) )
  LOG::LogSuccess( message:"Assign Parameter - User Data Type" );
else
  LOG::LogFailure( message:"Assign Parameter - User Data Type");
  LOG::LogInt( message:"Function Invocation", int: tc2.u );
  LOG::LogInt( message:"Bridge Invocation", int: tc3.u );
  LOG::LogInt( message:"Instance Operation Invocation", int: tc4.u );
  LOG::LogInt( message:"Class Operation Invocation", int: tc5.u );
end if;


EE::mod_params2(	b: tc2.b, tr: tc2.tr, i: tc2.i, r: tc2.r, s: tc2.s, en: tc2.en, u: tc2.u, 
		b2: tc3.b, tr2: tc3.tr, i2: tc3.i, r2: tc3.r, s2: tc3.s, en2: tc3.en, u2: tc3.u, 
		b3: tc4.b, tr3: tc4.tr, i3:tc4.i, r3: tc4.r, s3: tc4.s, en3: tc4.en, u3: tc4.u, 
		b4: tc5.b, tr4: tc5.tr, i4:tc5.i, r4: tc5.r, s4: tc5.s, en4: tc5.en, u4: tc5.u  );


////////////////////////////
// Verify Bridge Results
////////////////////////////
if ( tc2.b and tc3.b and tc4.b and tc5.b )
  LOG::LogSuccess( message:"Assign Parameter - Boolean" );
else
  LOG::LogFailure( message:"Assign Parameter - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:tc2.b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:tc3.b );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:tc4.b );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:tc5.b );
end if;

if ( ( tc2.tr ==  tc3.tr ) and (  tc4.tr ==  tc5.tr ) )
  LOG::LogSuccess( message:"Assign Parameter - Timer" );
else
  LOG::LogFailure( message:"Assign Parameter - Timer");
end if;

if ( ( tc2.i ==  tc3.i ) and (  tc4.i ==  tc5.i ) )
  LOG::LogSuccess( message:"Assign Parameter - Integer" );
else
  LOG::LogFailure( message:"Assign Parameter - Integer");
  LOG::LogInt( message:"Function Invocation", int: tc2.i );
  LOG::LogInt( message:"Bridge Invocation", int: tc3.i );
  LOG::LogInt( message:"Instance Operation Invocation", int: tc4.i );
  LOG::LogInt( message:"Class Operation Invocation", int: tc5.i );
end if;

if ( (  tc2.r ==  tc3.r ) and (  tc4.r ==  tc5.r ) )
  LOG::LogSuccess( message:"Assign Parameter - Real" );
else
  LOG::LogFailure( message:"Assign Parameter - Real");
  LOG::LogReal( message:"Function Invocation", real: tc2.r );
  LOG::LogReal( message:"Bridge Invocation", real: tc3.r );
  LOG::LogReal( message:"Instance Operation Invocation", real: tc4.r );
  LOG::LogReal( message:"Class Operation Invocation", real: tc5.r );
end if;

if ( (  tc2.s ==  tc3.s ) and (  tc4.s ==  tc5.s ) )
  LOG::LogSuccess( message:"Assign Parameter - String" );
else
  LOG::LogFailure( message:"Assign Parameter - String");
  LOG::LogString( message:"Function Invocation", str: tc2.s );
  LOG::LogString( message:"Bridge Invocation", str: tc3.s );
  LOG::LogString( message:"Instance Operation Invocation", str: tc4.s );
  LOG::LogString( message:"Class Operation Invocation", str: tc5.s );
end if;

if ( (  tc2.en ==  tc3.en ) and (  tc4.en ==  tc5.en ) )
  LOG::LogSuccess( message:"Assign Parameter - Enumeration" );
else
  LOG::LogFailure( message:"Assign Parameter - Enumeration");
end if;

if ( (  tc2.u ==  tc3.u ) and (  tc4.u ==  tc5.u ) )
  LOG::LogSuccess( message:"Assign Parameter - User Data Type" );
else
  LOG::LogFailure( message:"Assign Parameter - User Data Type");
  LOG::LogInt( message:"Function Invocation", int: tc2.u );
  LOG::LogInt( message:"Bridge Invocation", int: tc3.u );
  LOG::LogInt( message:"Instance Operation Invocation", int: tc4.u );
  LOG::LogInt( message:"Class Operation Invocation", int: tc5.u );
end if;

tc.mod_params3(	b: tc2.b, tr: tc2.tr, i: tc2.i, r: tc2.r, s: tc2.s, en: tc2.en, u: tc2.u, 
		b2: tc3.b, tr2: tc3.tr, i2: tc3.i, r2: tc3.r, s2: tc3.s, en2: tc3.en, u2: tc3.u, 
		b3: tc4.b, tr3: tc4.tr, i3:tc4.i, r3: tc4.r, s3: tc4.s, en3: tc4.en, u3: tc4.u, 
		b4: tc5.b, tr4: tc5.tr, i4:tc5.i, r4: tc5.r, s4: tc5.s, en4: tc5.en, u4: tc5.u  );


////////////////////////////
// Verify IOP Results
////////////////////////////
if ( tc2.b and tc3.b and tc4.b and tc5.b )
  LOG::LogSuccess( message:"Assign Parameter - Boolean" );
else
  LOG::LogFailure( message:"Assign Parameter - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:tc2.b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:tc3.b );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:tc4.b );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:tc5.b );
end if;

if ( ( tc2.tr ==  tc3.tr ) and (  tc4.tr ==  tc5.tr ) )
  LOG::LogSuccess( message:"Assign Parameter - Timer" );
else
  LOG::LogFailure( message:"Assign Parameter - Timer");
end if;

if ( ( tc2.i ==  tc3.i ) and (  tc4.i ==  tc5.i ) )
  LOG::LogSuccess( message:"Assign Parameter - Integer" );
else
  LOG::LogFailure( message:"Assign Parameter - Integer");
  LOG::LogInt( message:"Function Invocation", int: tc2.i );
  LOG::LogInt( message:"Bridge Invocation", int: tc3.i );
  LOG::LogInt( message:"Instance Operation Invocation", int: tc4.i );
  LOG::LogInt( message:"Class Operation Invocation", int: tc5.i );
end if;

if ( (  tc2.r ==  tc3.r ) and (  tc4.r ==  tc5.r ) )
  LOG::LogSuccess( message:"Assign Parameter - Real" );
else
  LOG::LogFailure( message:"Assign Parameter - Real");
  LOG::LogReal( message:"Function Invocation", real: tc2.r );
  LOG::LogReal( message:"Bridge Invocation", real: tc3.r );
  LOG::LogReal( message:"Instance Operation Invocation", real: tc4.r );
  LOG::LogReal( message:"Class Operation Invocation", real: tc5.r );
end if;

if ( (  tc2.s ==  tc3.s ) and (  tc4.s ==  tc5.s ) )
  LOG::LogSuccess( message:"Assign Parameter - String" );
else
  LOG::LogFailure( message:"Assign Parameter - String");
  LOG::LogString( message:"Function Invocation", str: tc2.s );
  LOG::LogString( message:"Bridge Invocation", str: tc3.s );
  LOG::LogString( message:"Instance Operation Invocation", str: tc4.s );
  LOG::LogString( message:"Class Operation Invocation", str: tc5.s );
end if;

if ( (  tc2.en ==  tc3.en ) and (  tc4.en ==  tc5.en ) )
  LOG::LogSuccess( message:"Assign Parameter - Enumeration" );
else
  LOG::LogFailure( message:"Assign Parameter - Enumeration");
end if;

if ( (  tc2.u ==  tc3.u ) and (  tc4.u ==  tc5.u ) )
  LOG::LogSuccess( message:"Assign Parameter - User Data Type" );
else
  LOG::LogFailure( message:"Assign Parameter - User Data Type");
  LOG::LogInt( message:"Function Invocation", int: tc2.u );
  LOG::LogInt( message:"Bridge Invocation", int: tc3.u );
  LOG::LogInt( message:"Instance Operation Invocation", int: tc4.u );
  LOG::LogInt( message:"Class Operation Invocation", int: tc5.u );
end if;

TC::mod_params4(	b: tc2.b, tr: tc2.tr, i: tc2.i, r: tc2.r, s: tc2.s, en: tc2.en, u: tc2.u, 
		b2: tc3.b, tr2: tc3.tr, i2: tc3.i, r2: tc3.r, s2: tc3.s, en2: tc3.en, u2: tc3.u, 
		b3: tc4.b, tr3: tc4.tr, i3:tc4.i, r3: tc4.r, s3: tc4.s, en3: tc4.en, u3: tc4.u, 
		b4: tc5.b, tr4: tc5.tr, i4:tc5.i, r4: tc5.r, s4: tc5.s, en4: tc5.en, u4: tc5.u  );

////////////////////////////
// Verify COP Results
////////////////////////////
if ( tc2.b and tc3.b and tc4.b and tc5.b )
  LOG::LogSuccess( message:"Assign Parameter - Boolean" );
else
  LOG::LogFailure( message:"Assign Parameter - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:tc2.b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:tc3.b );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:tc4.b );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:tc5.b );
end if;

if ( ( tc2.tr ==  tc3.tr ) and (  tc4.tr ==  tc5.tr ) )
  LOG::LogSuccess( message:"Assign Parameter - Timer" );
else
  LOG::LogFailure( message:"Assign Parameter - Timer");
end if;

if ( ( tc2.i ==  tc3.i ) and (  tc4.i ==  tc5.i ) )
  LOG::LogSuccess( message:"Assign Parameter - Integer" );
else
  LOG::LogFailure( message:"Assign Parameter - Integer");
  LOG::LogInt( message:"Function Invocation", int: tc2.i );
  LOG::LogInt( message:"Bridge Invocation", int: tc3.i );
  LOG::LogInt( message:"Instance Operation Invocation", int: tc4.i );
  LOG::LogInt( message:"Class Operation Invocation", int: tc5.i );
end if;

if ( (  tc2.r ==  tc3.r ) and (  tc4.r ==  tc5.r ) )
  LOG::LogSuccess( message:"Assign Parameter - Real" );
else
  LOG::LogFailure( message:"Assign Parameter - Real");
  LOG::LogReal( message:"Function Invocation", real: tc2.r );
  LOG::LogReal( message:"Bridge Invocation", real: tc3.r );
  LOG::LogReal( message:"Instance Operation Invocation", real: tc4.r );
  LOG::LogReal( message:"Class Operation Invocation", real: tc5.r );
end if;

if ( (  tc2.s ==  tc3.s ) and (  tc4.s ==  tc5.s ) )
  LOG::LogSuccess( message:"Assign Parameter - String" );
else
  LOG::LogFailure( message:"Assign Parameter - String");
  LOG::LogString( message:"Function Invocation", str: tc2.s );
  LOG::LogString( message:"Bridge Invocation", str: tc3.s );
  LOG::LogString( message:"Instance Operation Invocation", str: tc4.s );
  LOG::LogString( message:"Class Operation Invocation", str: tc5.s );
end if;

if ( (  tc2.en ==  tc3.en ) and (  tc4.en ==  tc5.en ) )
  LOG::LogSuccess( message:"Assign Parameter - Enumeration" );
else
  LOG::LogFailure( message:"Assign Parameter - Enumeration");
end if;

if ( (  tc2.u ==  tc3.u ) and (  tc4.u ==  tc5.u ) )
  LOG::LogSuccess( message:"Assign Parameter - User Data Type" );
else
  LOG::LogFailure( message:"Assign Parameter - User Data Type");
  LOG::LogInt( message:"Function Invocation", int: tc2.u );
  LOG::LogInt( message:"Bridge Invocation", int: tc3.u );
  LOG::LogInt( message:"Instance Operation Invocation", int: tc4.u );
  LOG::LogInt( message:"Class Operation Invocation", int: tc5.u );
end if;

generate DRV3 to self;',
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
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097154,
	2097156,
	2097155,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097155,
	2097156,
	2097155,
	2097155,
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
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097156,
	2097156,
	2097157,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097157,
	2097156,
	2097157,
	2097155,
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
	2097155,
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
	2097156,
	0);
INSERT INTO SM_TXN
	VALUES (2097159,
	2097156,
	2097160,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097160,
	2097156,
	2097160,
	2097157,
	0);
INSERT INTO SM_TXN
	VALUES (2097160,
	2097156,
	2097161,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097161,
	2097156,
	2097161,
	2097158,
	0);
INSERT INTO SM_TXN
	VALUES (2097161,
	2097156,
	2097162,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097162,
	2097156,
	2097162,
	2097159,
	0);
INSERT INTO SM_TXN
	VALUES (2097162,
	2097156,
	2097163,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097163,
	2097156,
	2097163,
	2097160,
	0);
INSERT INTO SM_TXN
	VALUES (2097163,
	2097156,
	2097164,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097164,
	2097156,
	2097164,
	2097161,
	0);
INSERT INTO SM_TXN
	VALUES (2097164,
	2097156,
	2097165,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097165,
	2097156,
	2097165,
	2097162,
	0);
INSERT INTO SM_TXN
	VALUES (2097165,
	2097156,
	2097167,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097166,
	2097156,
	2097167,
	2097163,
	0);
INSERT INTO SM_TXN
	VALUES (2097166,
	2097156,
	2097166,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097167,
	2097156,
	2097166,
	2097164,
	0);
INSERT INTO SM_TXN
	VALUES (2097167,
	2097156,
	2097169,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097168,
	2097156,
	2097169,
	2097165,
	0);
INSERT INTO SM_TXN
	VALUES (2097168,
	2097156,
	2097168,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097169,
	2097156,
	2097168,
	2097166,
	0);
INSERT INTO SM_TXN
	VALUES (2097169,
	2097156,
	2097170,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097170,
	2097156,
	2097170,
	2097156,
	0);
INSERT INTO SM_TXN
	VALUES (2097170,
	2097156,
	2097160,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097171,
	2097156,
	2097170,
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097171,
	2097156,
	2097171,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097172,
	2097156,
	2097172,
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097172,
	2097156,
	2097173,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097173,
	2097156,
	2097173,
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097173,
	2097156,
	2097174,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097174,
	2097156,
	2097174,
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097174,
	2097156,
	2097175,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097175,
	2097156,
	2097175,
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097175,
	2097156,
	2097176,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097176,
	2097156,
	2097171,
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097176,
	2097156,
	2097178,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097177,
	2097156,
	2097178,
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097177,
	2097156,
	2097172,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097178,
	2097156,
	2097176,
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097178,
	2097156,
	2097180,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097179,
	2097156,
	2097180,
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097179,
	2097156,
	2097179,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097180,
	2097156,
	2097179,
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097180,
	2097156,
	2097177,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097181,
	2097156,
	2097177,
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097181,
	2097156,
	2097181,
	0);
INSERT INTO SM_NSTXN
	VALUES (2097182,
	2097156,
	2097181,
	2097155,
	0);
INSERT INTO SM_TXN
	VALUES (2097182,
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
	2115,
	4691,
	0.735825,
	0);
INSERT INTO GD_GE
	VALUES (2097154,
	2097153,
	2097153,
	41);
INSERT INTO GD_SHP
	VALUES (2097154,
	1680,
	304,
	2256,
	464);
INSERT INTO GD_GE
	VALUES (2097155,
	2097153,
	2097154,
	41);
INSERT INTO GD_SHP
	VALUES (2097155,
	1680,
	1200,
	2256,
	1360);
INSERT INTO GD_GE
	VALUES (2097156,
	2097153,
	2097155,
	41);
INSERT INTO GD_SHP
	VALUES (2097156,
	2384,
	304,
	2656,
	464);
INSERT INTO GD_GE
	VALUES (2097157,
	2097153,
	2097156,
	41);
INSERT INTO GD_SHP
	VALUES (2097157,
	2384,
	528,
	2656,
	688);
INSERT INTO GD_GE
	VALUES (2097158,
	2097153,
	2097157,
	41);
INSERT INTO GD_SHP
	VALUES (2097158,
	2384,
	752,
	2656,
	912);
INSERT INTO GD_GE
	VALUES (2097159,
	2097153,
	2097158,
	41);
INSERT INTO GD_SHP
	VALUES (2097159,
	2384,
	976,
	2656,
	1136);
INSERT INTO GD_GE
	VALUES (2097160,
	2097153,
	2097159,
	41);
INSERT INTO GD_SHP
	VALUES (2097160,
	2784,
	976,
	3056,
	1136);
INSERT INTO GD_GE
	VALUES (2097161,
	2097153,
	2097160,
	41);
INSERT INTO GD_SHP
	VALUES (2097161,
	2784,
	800,
	2896,
	864);
INSERT INTO GD_GE
	VALUES (2097162,
	2097153,
	2097161,
	41);
INSERT INTO GD_SHP
	VALUES (2097162,
	2784,
	704,
	2896,
	768);
INSERT INTO GD_GE
	VALUES (2097163,
	2097153,
	2097162,
	41);
INSERT INTO GD_SHP
	VALUES (2097163,
	2784,
	576,
	2896,
	640);
INSERT INTO GD_GE
	VALUES (2097164,
	2097153,
	2097163,
	41);
INSERT INTO GD_SHP
	VALUES (2097164,
	2784,
	480,
	2896,
	544);
INSERT INTO GD_GE
	VALUES (2097165,
	2097153,
	2097164,
	41);
INSERT INTO GD_SHP
	VALUES (2097165,
	2784,
	368,
	2896,
	432);
INSERT INTO GD_GE
	VALUES (2097166,
	2097153,
	2097165,
	41);
INSERT INTO GD_SHP
	VALUES (2097166,
	2784,
	272,
	2896,
	336);
INSERT INTO GD_GE
	VALUES (2097167,
	2097153,
	2097166,
	41);
INSERT INTO GD_SHP
	VALUES (2097167,
	3008,
	368,
	3120,
	432);
INSERT INTO GD_GE
	VALUES (2097168,
	2097153,
	2097167,
	41);
INSERT INTO GD_SHP
	VALUES (2097168,
	3008,
	272,
	3120,
	336);
INSERT INTO GD_GE
	VALUES (2097169,
	2097153,
	2097168,
	41);
INSERT INTO GD_SHP
	VALUES (2097169,
	3008,
	576,
	3120,
	640);
INSERT INTO GD_GE
	VALUES (2097170,
	2097153,
	2097169,
	41);
INSERT INTO GD_SHP
	VALUES (2097170,
	3008,
	480,
	3120,
	544);
INSERT INTO GD_GE
	VALUES (2097171,
	2097153,
	2097170,
	41);
INSERT INTO GD_SHP
	VALUES (2097171,
	3008,
	704,
	3120,
	768);
INSERT INTO GD_GE
	VALUES (2097172,
	2097153,
	2097171,
	41);
INSERT INTO GD_SHP
	VALUES (2097172,
	3200,
	304,
	3472,
	464);
INSERT INTO GD_GE
	VALUES (2097173,
	2097153,
	2097172,
	41);
INSERT INTO GD_SHP
	VALUES (2097173,
	3200,
	752,
	3472,
	912);
INSERT INTO GD_GE
	VALUES (2097174,
	2097153,
	2097173,
	41);
INSERT INTO GD_SHP
	VALUES (2097174,
	3200,
	976,
	3472,
	1136);
INSERT INTO GD_GE
	VALUES (2097175,
	2097153,
	2097174,
	41);
INSERT INTO GD_SHP
	VALUES (2097175,
	3584,
	304,
	3856,
	464);
INSERT INTO GD_GE
	VALUES (2097176,
	2097153,
	2097175,
	41);
INSERT INTO GD_SHP
	VALUES (2097176,
	3584,
	528,
	3856,
	688);
INSERT INTO GD_GE
	VALUES (2097177,
	2097153,
	2097176,
	41);
INSERT INTO GD_SHP
	VALUES (2097177,
	3584,
	752,
	3856,
	912);
INSERT INTO GD_GE
	VALUES (2097178,
	2097153,
	2097177,
	41);
INSERT INTO GD_SHP
	VALUES (2097178,
	3200,
	1200,
	3472,
	1360);
INSERT INTO GD_GE
	VALUES (2097179,
	2097153,
	2097178,
	41);
INSERT INTO GD_SHP
	VALUES (2097179,
	3200,
	528,
	3472,
	688);
INSERT INTO GD_GE
	VALUES (2097180,
	2097153,
	2097179,
	41);
INSERT INTO GD_SHP
	VALUES (2097180,
	3584,
	1200,
	3856,
	1360);
INSERT INTO GD_GE
	VALUES (2097181,
	2097153,
	2097180,
	41);
INSERT INTO GD_SHP
	VALUES (2097181,
	3584,
	976,
	3856,
	1136);
INSERT INTO GD_GE
	VALUES (2097182,
	2097153,
	2097181,
	41);
INSERT INTO GD_SHP
	VALUES (2097182,
	2784,
	1200,
	3056,
	1360);
INSERT INTO GD_GE
	VALUES (2097183,
	2097153,
	2097153,
	42);
INSERT INTO GD_CON
	VALUES (2097183,
	2097154,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (2097183,
	0,
	0,
	0,
	0,
	0,
	0,
	1869,
	256,
	1963,
	278,
	-15,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097184,
	2097183,
	1984,
	304,
	1984,
	240,
	0);
INSERT INTO GD_GE
	VALUES (2097185,
	2097153,
	2097154,
	42);
INSERT INTO GD_CON
	VALUES (2097185,
	2097154,
	2097156,
	0);
INSERT INTO GD_CTXT
	VALUES (2097185,
	0,
	0,
	0,
	0,
	0,
	0,
	2281,
	353,
	2360,
	375,
	-2,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097186,
	2097185,
	2256,
	384,
	2384,
	384,
	0);
INSERT INTO GD_GE
	VALUES (2097187,
	2097153,
	2097155,
	42);
INSERT INTO GD_CON
	VALUES (2097187,
	2097156,
	2097157,
	0);
INSERT INTO GD_CTXT
	VALUES (2097187,
	0,
	0,
	0,
	0,
	0,
	0,
	2438,
	481,
	2517,
	503,
	-5,
	-6,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097188,
	2097187,
	2528,
	464,
	2528,
	528,
	0);
INSERT INTO GD_GE
	VALUES (2097189,
	2097153,
	2097156,
	42);
INSERT INTO GD_CON
	VALUES (2097189,
	2097157,
	2097158,
	0);
INSERT INTO GD_CTXT
	VALUES (2097189,
	0,
	0,
	0,
	0,
	0,
	0,
	2427,
	706,
	2506,
	728,
	0,
	-5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097190,
	2097189,
	2512,
	688,
	2512,
	752,
	0);
INSERT INTO GD_GE
	VALUES (2097191,
	2097153,
	2097157,
	42);
INSERT INTO GD_CON
	VALUES (2097191,
	2097158,
	2097159,
	0);
INSERT INTO GD_CTXT
	VALUES (2097191,
	0,
	0,
	0,
	0,
	0,
	0,
	2427,
	935,
	2506,
	957,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097192,
	2097191,
	2512,
	912,
	2512,
	976,
	0);
INSERT INTO GD_GE
	VALUES (2097193,
	2097153,
	2097158,
	42);
INSERT INTO GD_CON
	VALUES (2097193,
	2097159,
	2097160,
	0);
INSERT INTO GD_CTXT
	VALUES (2097193,
	0,
	0,
	0,
	0,
	0,
	0,
	2675,
	1021,
	2754,
	1043,
	-8,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097194,
	2097193,
	2656,
	1056,
	2784,
	1056,
	0);
INSERT INTO GD_GE
	VALUES (2097195,
	2097153,
	2097159,
	42);
INSERT INTO GD_CON
	VALUES (2097195,
	2097160,
	2097161,
	0);
INSERT INTO GD_CTXT
	VALUES (2097195,
	0,
	0,
	0,
	0,
	0,
	0,
	2813,
	940,
	2919,
	962,
	109,
	29,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097196,
	2097195,
	2816,
	976,
	2816,
	864,
	0);
INSERT INTO GD_GE
	VALUES (2097197,
	2097153,
	2097160,
	42);
INSERT INTO GD_CON
	VALUES (2097197,
	2097161,
	2097162,
	0);
INSERT INTO GD_CTXT
	VALUES (2097197,
	0,
	0,
	0,
	0,
	0,
	0,
	2719,
	775,
	2826,
	797,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097198,
	2097197,
	2832,
	800,
	2832,
	768,
	0);
INSERT INTO GD_GE
	VALUES (2097199,
	2097153,
	2097161,
	42);
INSERT INTO GD_CON
	VALUES (2097199,
	2097162,
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
	2712,
	663,
	2826,
	685,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097200,
	2097199,
	2832,
	704,
	2832,
	640,
	0);
INSERT INTO GD_GE
	VALUES (2097201,
	2097153,
	2097162,
	42);
INSERT INTO GD_CON
	VALUES (2097201,
	2097163,
	2097164,
	0);
INSERT INTO GD_CTXT
	VALUES (2097201,
	0,
	0,
	0,
	0,
	0,
	0,
	2730,
	551,
	2826,
	573,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097202,
	2097201,
	2832,
	576,
	2832,
	544,
	0);
INSERT INTO GD_GE
	VALUES (2097203,
	2097153,
	2097163,
	42);
INSERT INTO GD_CON
	VALUES (2097203,
	2097164,
	2097165,
	0);
INSERT INTO GD_CTXT
	VALUES (2097203,
	0,
	0,
	0,
	0,
	0,
	0,
	2737,
	447,
	2826,
	469,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097204,
	2097203,
	2832,
	480,
	2832,
	432,
	0);
INSERT INTO GD_GE
	VALUES (2097205,
	2097153,
	2097164,
	42);
INSERT INTO GD_CON
	VALUES (2097205,
	2097165,
	2097166,
	0);
INSERT INTO GD_CTXT
	VALUES (2097205,
	0,
	0,
	0,
	0,
	0,
	0,
	2734,
	343,
	2826,
	365,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097206,
	2097205,
	2832,
	368,
	2832,
	336,
	0);
INSERT INTO GD_GE
	VALUES (2097207,
	2097153,
	2097165,
	42);
INSERT INTO GD_CON
	VALUES (2097207,
	2097166,
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
	2899,
	276,
	3010,
	298,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097208,
	2097207,
	2896,
	304,
	3008,
	304,
	0);
INSERT INTO GD_GE
	VALUES (2097209,
	2097153,
	2097166,
	42);
INSERT INTO GD_CON
	VALUES (2097209,
	2097168,
	2097167,
	0);
INSERT INTO GD_CTXT
	VALUES (2097209,
	0,
	0,
	0,
	0,
	0,
	0,
	2964,
	343,
	3066,
	365,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097210,
	2097209,
	3072,
	336,
	3072,
	368,
	0);
INSERT INTO GD_GE
	VALUES (2097211,
	2097153,
	2097167,
	42);
INSERT INTO GD_CON
	VALUES (2097211,
	2097167,
	2097170,
	0);
INSERT INTO GD_CTXT
	VALUES (2097211,
	0,
	0,
	0,
	0,
	0,
	0,
	2935,
	447,
	3050,
	469,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097212,
	2097211,
	3056,
	432,
	3056,
	480,
	0);
INSERT INTO GD_GE
	VALUES (2097213,
	2097153,
	2097168,
	42);
INSERT INTO GD_CON
	VALUES (2097213,
	2097170,
	2097169,
	0);
INSERT INTO GD_CTXT
	VALUES (2097213,
	0,
	0,
	0,
	0,
	0,
	0,
	2930,
	551,
	3050,
	573,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097214,
	2097213,
	3056,
	544,
	3056,
	576,
	0);
INSERT INTO GD_GE
	VALUES (2097215,
	2097153,
	2097169,
	42);
INSERT INTO GD_CON
	VALUES (2097215,
	2097169,
	2097171,
	0);
INSERT INTO GD_CTXT
	VALUES (2097215,
	0,
	0,
	0,
	0,
	0,
	0,
	2944,
	663,
	3050,
	685,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097216,
	2097215,
	3056,
	640,
	3056,
	704,
	0);
INSERT INTO GD_GE
	VALUES (2097217,
	2097153,
	2097170,
	42);
INSERT INTO GD_CON
	VALUES (2097217,
	2097171,
	2097161,
	0);
INSERT INTO GD_CTXT
	VALUES (2097217,
	0,
	0,
	0,
	0,
	0,
	0,
	2910,
	804,
	3016,
	826,
	-15,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097218,
	2097217,
	3056,
	768,
	3056,
	832,
	0);
INSERT INTO GD_LS
	VALUES (2097219,
	2097217,
	3056,
	832,
	2896,
	832,
	2097218);
INSERT INTO GD_GE
	VALUES (2097220,
	2097153,
	2097171,
	42);
INSERT INTO GD_CON
	VALUES (2097220,
	2097171,
	2097172,
	0);
INSERT INTO GD_CTXT
	VALUES (2097220,
	0,
	0,
	0,
	0,
	0,
	0,
	3117,
	334,
	3196,
	356,
	50,
	-217,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097221,
	2097220,
	3120,
	736,
	3152,
	736,
	0);
INSERT INTO GD_LS
	VALUES (2097222,
	2097220,
	3152,
	736,
	3152,
	384,
	2097221);
INSERT INTO GD_LS
	VALUES (2097223,
	2097220,
	3152,
	384,
	3200,
	384,
	2097222);
INSERT INTO GD_GE
	VALUES (2097224,
	2097153,
	2097172,
	42);
INSERT INTO GD_CON
	VALUES (2097224,
	2097173,
	2097174,
	0);
INSERT INTO GD_CTXT
	VALUES (2097224,
	0,
	0,
	0,
	0,
	0,
	0,
	3238,
	933,
	3317,
	955,
	-5,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097225,
	2097224,
	3328,
	912,
	3328,
	976,
	0);
INSERT INTO GD_GE
	VALUES (2097226,
	2097153,
	2097173,
	42);
INSERT INTO GD_CON
	VALUES (2097226,
	2097174,
	2097175,
	0);
INSERT INTO GD_CTXT
	VALUES (2097226,
	0,
	0,
	0,
	0,
	0,
	0,
	3498,
	437,
	3577,
	459,
	79,
	-266,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097227,
	2097226,
	3472,
	1056,
	3504,
	1056,
	0);
INSERT INTO GD_LS
	VALUES (2097228,
	2097226,
	3504,
	1056,
	3504,
	368,
	2097227);
INSERT INTO GD_LS
	VALUES (2097229,
	2097226,
	3504,
	368,
	3584,
	368,
	2097228);
INSERT INTO GD_GE
	VALUES (2097230,
	2097153,
	2097174,
	42);
INSERT INTO GD_CON
	VALUES (2097230,
	2097175,
	2097176,
	0);
INSERT INTO GD_CTXT
	VALUES (2097230,
	0,
	0,
	0,
	0,
	0,
	0,
	3622,
	482,
	3701,
	504,
	-5,
	-5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097231,
	2097230,
	3712,
	464,
	3712,
	528,
	0);
INSERT INTO GD_GE
	VALUES (2097232,
	2097153,
	2097175,
	42);
INSERT INTO GD_CON
	VALUES (2097232,
	2097176,
	2097177,
	0);
INSERT INTO GD_CTXT
	VALUES (2097232,
	0,
	0,
	0,
	0,
	0,
	0,
	3620,
	711,
	3699,
	733,
	-7,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097233,
	2097232,
	3712,
	688,
	3712,
	752,
	0);
INSERT INTO GD_GE
	VALUES (2097234,
	2097153,
	2097176,
	42);
INSERT INTO GD_CON
	VALUES (2097234,
	2097172,
	2097179,
	0);
INSERT INTO GD_CTXT
	VALUES (2097234,
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
	VALUES (2097235,
	2097234,
	3328,
	464,
	3328,
	528,
	0);
INSERT INTO GD_GE
	VALUES (2097236,
	2097153,
	2097177,
	42);
INSERT INTO GD_CON
	VALUES (2097236,
	2097179,
	2097173,
	0);
INSERT INTO GD_CTXT
	VALUES (2097236,
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
	VALUES (2097237,
	2097236,
	3328,
	688,
	3328,
	752,
	0);
INSERT INTO GD_GE
	VALUES (2097238,
	2097153,
	2097180,
	42);
INSERT INTO GD_CON
	VALUES (2097238,
	2097180,
	2097178,
	0);
INSERT INTO GD_CTXT
	VALUES (2097238,
	0,
	0,
	0,
	0,
	0,
	0,
	3488,
	1242,
	3567,
	1264,
	-3,
	-10,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097239,
	2097238,
	3584,
	1280,
	3472,
	1280,
	0);
INSERT INTO GD_GE
	VALUES (2097240,
	2097153,
	2097178,
	42);
INSERT INTO GD_CON
	VALUES (2097240,
	2097177,
	2097181,
	0);
INSERT INTO GD_CTXT
	VALUES (2097240,
	0,
	0,
	0,
	0,
	0,
	0,
	3620,
	932,
	3699,
	954,
	-7,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097241,
	2097240,
	3712,
	912,
	3712,
	976,
	0);
INSERT INTO GD_GE
	VALUES (2097242,
	2097153,
	2097179,
	42);
INSERT INTO GD_CON
	VALUES (2097242,
	2097181,
	2097180,
	0);
INSERT INTO GD_CTXT
	VALUES (2097242,
	0,
	0,
	0,
	0,
	0,
	0,
	3625,
	1159,
	3704,
	1181,
	-2,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097243,
	2097242,
	3712,
	1136,
	3712,
	1200,
	0);
INSERT INTO GD_GE
	VALUES (2097244,
	2097153,
	2097181,
	42);
INSERT INTO GD_CON
	VALUES (2097244,
	2097178,
	2097182,
	0);
INSERT INTO GD_CTXT
	VALUES (2097244,
	0,
	0,
	0,
	0,
	0,
	0,
	3087,
	1242,
	3166,
	1264,
	-4,
	-10,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097245,
	2097244,
	3200,
	1280,
	3056,
	1280,
	0);
INSERT INTO GD_GE
	VALUES (2097246,
	2097153,
	2097182,
	42);
INSERT INTO GD_CON
	VALUES (2097246,
	2097182,
	2097155,
	0);
INSERT INTO GD_CTXT
	VALUES (2097246,
	0,
	0,
	0,
	0,
	0,
	0,
	2492,
	1265,
	2571,
	1287,
	9,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097247,
	2097246,
	2784,
	1296,
	2256,
	1296,
	0);
INSERT INTO O_OBJ
	VALUES (1048579,
	'Test Class',
	3,
	'TC',
	'',
	1048578);
INSERT INTO O_TFR
	VALUES (1048577,
	1048579,
	'bool3',
	'',
	524290,
	1,
	'select any tc from instances of TC;

if ( param.date3 == tc.d )
  LOG::LogSuccess( message: "Instance Operation bool3 - Date parameter is correct." );
else
  LOG::LogFailure( message: "Instance Operation bool3 - Date parameter is incorrect." );
end if;

return tc.b;',
	1);
INSERT INTO O_TPARM
	VALUES (1048577,
	1048577,
	'date3',
	524302,
	0);
INSERT INTO O_TFR
	VALUES (1048578,
	1048579,
	'date3',
	'',
	524302,
	1,
	'select any tc from instances of TC;

if ( param.evt3 == tc.e )
  LOG::LogSuccess( message: "Instance Operation date3 - Event parameter is correct." );
else
  LOG::LogFailure( message: "Instance Operation date3 - Event parameter is incorrect." );
end if;

return tc.d;',
	1);
INSERT INTO O_TPARM
	VALUES (1048578,
	1048578,
	'evt3',
	524299,
	0);
INSERT INTO O_TFR
	VALUES (1048579,
	1048579,
	'evt3',
	'',
	524299,
	1,
	'select any tc from instances of TC;

if ( param.timer3 == tc.tr )
  LOG::LogSuccess( message: "Instance Operation evt3 - Timer parameter is correct." );
else
  LOG::LogFailure( message: "Instance Operation evt3 - Timer parameter is incorrect." );
end if;

return tc.e;',
	1);
INSERT INTO O_TPARM
	VALUES (1048579,
	1048579,
	'timer3',
	524304,
	0);
INSERT INTO O_TFR
	VALUES (1048580,
	1048579,
	'timer3',
	'',
	524304,
	1,
	'select any tc from instances of TC;

if ( ( param.int3 == tc.i ) OR ( param.int3 == ( 2 * tc.i ) ) )
  LOG::LogSuccess( message: "Instance Operation timer3 - Integer parameter is correct." );
else
  LOG::LogFailure( message: "Instance Operation timer3 - Integer parameter is incorrect." );
end if;

return tc.tr;',
	1);
INSERT INTO O_TPARM
	VALUES (1048580,
	1048580,
	'int3',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (1048581,
	1048579,
	'int3',
	'',
	524291,
	1,
	'select any tc from instances of TC;

if ( ( param.real3 == tc.r ) OR ( ( param.real3 == ( 2 * tc.r ) ) OR ( param.real3 == 0 ) ) )
  LOG::LogSuccess( message: "Instance Operation int3 - Real parameter is correct." );
else
  LOG::LogFailure( message: "Instance Operation int3 - Real parameter is incorrect." );
end if;

return tc.i;',
	1);
INSERT INTO O_TPARM
	VALUES (1048581,
	1048581,
	'real3',
	524292,
	0);
INSERT INTO O_TFR
	VALUES (1048582,
	1048579,
	'real3',
	'',
	524292,
	1,
	'select any tc from instances of TC;

if ( ( param.str3 == tc.s ) OR ( param.str3 == ( tc.s + tc.s ) ) )
  LOG::LogSuccess( message: "Instance Operation real3 - String parameter is correct." );
else
  LOG::LogFailure( message: "Instance Operation real3 - String parameter is incorrect." );
end if;

return tc.r;',
	1);
INSERT INTO O_TPARM
	VALUES (1048582,
	1048582,
	'str3',
	524293,
	0);
INSERT INTO O_TFR
	VALUES (1048583,
	1048579,
	'str3',
	'',
	524293,
	1,
	'select any tc from instances of TC;

if ( param.time3 == tc.t )
  LOG::LogSuccess( message: "Instance Operation str3 - Timestamp parameter is correct." );
else
  LOG::LogFailure( message: "Instance Operation str3 - Timestamp parameter is incorrect." );
end if;

return tc.s;',
	1);
INSERT INTO O_TPARM
	VALUES (1048583,
	1048583,
	'time3',
	524303,
	0);
INSERT INTO O_TFR
	VALUES (1048584,
	1048579,
	'time3',
	'',
	524303,
	1,
	'select any tc from instances of TC;

if ( param.id3 == tc.ID )
  LOG::LogSuccess( message: "Instance Operation time3 - Unique ID parameter is correct." );
else
  LOG::LogFailure( message: "Instance Operation time3 - Unique ID parameter is incorrect." );
end if;

return tc.t;',
	1);
INSERT INTO O_TPARM
	VALUES (1048584,
	1048584,
	'id3',
	524294,
	0);
INSERT INTO O_TFR
	VALUES (1048585,
	1048579,
	'id3',
	'',
	524294,
	1,
	'select any tc from instances of TC;

if ( param.enum3 == tc.en )
  LOG::LogSuccess( message: "Instance Operation id3 - Enumeration parameter is correct." );
else
  LOG::LogFailure( message: "Instance Operation id3 - Enumeration parameter is incorrect." );
end if;

return tc.ID;',
	1);
INSERT INTO O_TPARM
	VALUES (1048585,
	1048585,
	'enum3',
	524307,
	0);
INSERT INTO O_TFR
	VALUES (1048586,
	1048579,
	'bool4',
	'',
	524290,
	0,
	'select any tc from instances of TC;

if ( param.date4 == tc.d )
  LOG::LogSuccess( message: "Class Operation bool4 - Date parameter is correct." );
else
  LOG::LogFailure( message: "Class Operation bool4 - Date parameter is incorrect." );
end if;

return tc.b;',
	1);
INSERT INTO O_TPARM
	VALUES (1048586,
	1048586,
	'date4',
	524302,
	0);
INSERT INTO O_TFR
	VALUES (1048587,
	1048579,
	'date4',
	'',
	524302,
	0,
	'select any tc from instances of TC;

if ( param.evt4 == tc.e )
  LOG::LogSuccess( message: "Class Operation date4 - Event parameter is correct." );
else
  LOG::LogFailure( message: "Class Operation date4 - Event parameter is incorrect." );
end if;

return tc.d;',
	1);
INSERT INTO O_TPARM
	VALUES (1048587,
	1048587,
	'evt4',
	524299,
	0);
INSERT INTO O_TFR
	VALUES (1048588,
	1048579,
	'evt4',
	'',
	524299,
	0,
	'select any tc from instances of TC;

if ( param.timer4 == tc.tr )
  LOG::LogSuccess( message: "Class Operation evt4 - Timer parameter is correct." );
else
  LOG::LogFailure( message: "Class Operation evt4 - Timer parameter is incorrect." );
end if;

return tc.e;',
	1);
INSERT INTO O_TPARM
	VALUES (1048588,
	1048588,
	'timer4',
	524304,
	0);
INSERT INTO O_TFR
	VALUES (1048589,
	1048579,
	'timer4',
	'',
	524304,
	0,
	'select any tc from instances of TC;

if ( ( param.int4 == tc.i ) OR ( ( param.int4 == ( 2 * tc.i ) ) OR ( param.int4 == 0 ) ) )
  LOG::LogSuccess( message: "Instance Operation timer4 - Integer parameter is correct." );
else
  LOG::LogFailure( message: "Class Operation timer4 - Integer parameter is incorrect." );
end if;

return tc.tr;',
	1);
INSERT INTO O_TPARM
	VALUES (1048589,
	1048589,
	'int4',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (1048590,
	1048579,
	'int4',
	'',
	524291,
	0,
	'select any tc from instances of TC;

if ( ( param.real4 == tc.r ) OR ( param.real4 == ( 2 * tc.r ) ) )
  LOG::LogSuccess( message: "Class Operation int4 - Real parameter is correct." );
else
  LOG::LogFailure( message: "Class Operation int4 - Real parameter is incorrect." );
end if;

return tc.i;',
	1);
INSERT INTO O_TPARM
	VALUES (1048590,
	1048590,
	'real4',
	524292,
	0);
INSERT INTO O_TFR
	VALUES (1048591,
	1048579,
	'real4',
	'',
	524292,
	0,
	'select any tc from instances of TC;

if ( ( param.str4 == tc.s ) OR ( param.str4 == ( tc.s + tc.s ) ) )
  LOG::LogSuccess( message: "Class Operation real4 - String parameter is correct." );
else
  LOG::LogFailure( message: "Class Operation real4 - String parameter is incorrect." );
end if;

return tc.r;',
	1);
INSERT INTO O_TPARM
	VALUES (1048591,
	1048591,
	'str4',
	524293,
	0);
INSERT INTO O_TFR
	VALUES (1048592,
	1048579,
	'str4',
	'',
	524293,
	0,
	'select any tc from instances of TC;

if ( param.time4 == tc.t )
  LOG::LogSuccess( message: "Class Operation str4 - Timestamp parameter is correct." );
else
  LOG::LogFailure( message: "Class Operation str4 - Timestamp parameter is incorrect." );
end if;

return tc.s;',
	1);
INSERT INTO O_TPARM
	VALUES (1048592,
	1048592,
	'time4',
	524303,
	0);
INSERT INTO O_TFR
	VALUES (1048593,
	1048579,
	'time4',
	'',
	524303,
	0,
	'select any tc from instances of TC;

if ( param.id4 == tc.ID )
  LOG::LogSuccess( message: "Class Operation time4 - Unique ID parameter is correct." );
else
  LOG::LogFailure( message: "Class Operation time4 - Unique ID parameter is incorrect." );
end if;

return tc.t;',
	1);
INSERT INTO O_TPARM
	VALUES (1048593,
	1048593,
	'id4',
	524294,
	0);
INSERT INTO O_TFR
	VALUES (1048594,
	1048579,
	'id4',
	'',
	524294,
	0,
	'select any tc from instances of TC;

if ( param.enum4 == tc.en )
  LOG::LogSuccess( message: "Class Operation id4 - Enumeration parameter is correct." );
else
  LOG::LogFailure( message: "Class Operation id4 - Enumeration parameter is incorrect." );
end if;

return tc.ID;',
	1);
INSERT INTO O_TPARM
	VALUES (1048594,
	1048594,
	'enum4',
	524307,
	0);
INSERT INTO O_TFR
	VALUES (1048595,
	1048579,
	'enum3',
	'',
	524307,
	1,
	'select any tc from instances of TC;

if ( ( param.udt3 == tc.u ) OR ( param.udt3 == ( 2 * tc.u ) ) )
  LOG::LogSuccess( message: "Instance Operation enum3 - User Data Type parameter is correct." );
else
  LOG::LogFailure( message: "Instance Operation enum3 - User Data Type parameter is incorrect." );
end if;

return tc.en;',
	1);
INSERT INTO O_TPARM
	VALUES (1048595,
	1048595,
	'udt3',
	524306,
	0);
INSERT INTO O_TFR
	VALUES (1048596,
	1048579,
	'enum4',
	'',
	524307,
	0,
	'select any tc from instances of TC;

if ( ( param.udt4 == tc.u ) OR ( param.udt4 == ( 2 * tc.u ) ) )
  LOG::LogSuccess( message: "Class Operation enum4 - User Data Type parameter is correct." );
else
  LOG::LogFailure( message: "Class Operation enum4 - User Data Type parameter is incorrect." );
end if;

return tc.en;',
	1);
INSERT INTO O_TPARM
	VALUES (1048596,
	1048596,
	'udt4',
	524306,
	0);
INSERT INTO O_TFR
	VALUES (1048597,
	1048579,
	'udt3',
	'',
	524306,
	1,
	'select any tc from instances of TC;

if ( param.bool3 == tc.b )
  LOG::LogSuccess( message: "Instance Operation udt3 - Boolean parameter is correct." );
else
  LOG::LogFailure( message: "Instance Operation udt3 - Boolean parameter is incorrect." );
end if;

return tc.u;',
	1);
INSERT INTO O_TPARM
	VALUES (1048597,
	1048597,
	'bool3',
	524290,
	0);
INSERT INTO O_TFR
	VALUES (1048598,
	1048579,
	'udt4',
	'',
	524306,
	0,
	'select any tc from instances of TC;

if ( param.bool4 == tc.b )
  LOG::LogSuccess( message: "Class Operation udt4 - Boolean parameter is correct." );
else
  LOG::LogFailure( message: "Class Operation udt4 - Boolean parameter is incorrect." );
end if;

return tc.u;',
	1);
INSERT INTO O_TPARM
	VALUES (1048598,
	1048598,
	'bool4',
	524290,
	0);
INSERT INTO O_TFR
	VALUES (1048599,
	1048579,
	'single_invoke',
	'',
	524289,
	1,
	'select any tc from instances of TC;

////////////////////////////
// Functions
////////////////////////////
b = ::bool1(date1:tc.d);
d = ::date1(evt1:tc.e);
e = ::evt1(timer1:tc.tr);
tr = ::timer1(int1:tc.i);
i = ::int1(real1:tc.r);
r = ::real1(str1:tc.s);
s = ::str1(time1:tc.t);
t = ::time1(id1:tc.ID);
id = ::id1(enum1:tc.en);
en = ::enum1(udt1:tc.u);
u = ::udt1(bool1:tc.b);

////////////////////////////
// Bridges
////////////////////////////
b2 = EE::bool2(date2:tc.d);
d2 = EE::date2(evt2:tc.e);
e2 =  EE::evt2(timer2:tc.tr);
tr2 =  EE::timer2(int2:tc.i);
i2 =  EE::int2(real2:tc.r);
r2 =  EE::real2(str2:tc.s);
s2 =  EE::str2(time2:tc.t);
t2 =  EE::time2(id2:tc.ID);
id2 =  EE::id2(enum2:tc.en);
en2 = EE::enum2(udt2:tc.u);
u2 = EE::udt2(bool2:tc.b);

////////////////////////////
// Instance Operations
////////////////////////////
b3 = tc.bool3(date3:tc.d);
d3 =  tc.date3(evt3:tc.e);
e3 =  tc.evt3(timer3:tc.tr);
tr3 =  tc.timer3(int3:tc.i);
i3 =  tc.int3(real3:tc.r);
r3 =  tc.real3(str3:tc.s);
s3 =  tc.str3(time3:tc.t);
t3 =  tc.time3(id3:tc.ID);
id3 =  tc.id3(enum3:tc.en);
en3 = tc.enum3(udt3:tc.u);
u3 = tc.udt3(bool3:tc.b);

////////////////////////////
// Class Operations
////////////////////////////
b4 = TC::bool4(date4:tc.d);
d4 = TC::date4(evt4:tc.e);
e4 = TC::evt4(timer4:tc.tr);
tr4 = TC::timer4(int4:tc.i);
i4 = TC::int4(real4:tc.r);
r4 = TC::real4(str4:tc.s);
s4 = TC::str4(time4:tc.t);
t4 = TC::time4(id4:tc.ID);
id4 = TC::id4(enum4:tc.en);
en4 = TC::enum4(udt4:tc.u);
u4 = TC::udt4(bool4:tc.b);

////////////////////////////
// Results
////////////////////////////
if ( b and b2 and b3 and b4 )
  LOG::LogSuccess( message:"Single Invocation - Boolean" );
else
  LOG::LogFailure( message:"Single Invocation - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:b2 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b3 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b4 );
end if;

if ( ( d == d2 ) and ( d3 == d4 ) )
  LOG::LogSuccess( message:"Single Invocation - Date" );
else
  LOG::LogFailure( message:"Single Invocation - Date");
  LOG::LogDate( message:"Function Invocation", date:d );
  LOG::LogDate( message:"Bridge Invocation", date:d2 );
  LOG::LogDate( message:"Instance Operation Invocation", date:d3 );
  LOG::LogDate( message:"Class Operation Invocation", date:d4 );
end if;

if ( ( e == e2 ) and ( e3 == e4 ) )
  LOG::LogSuccess( message:"Single Invocation - Event" );
else
  LOG::LogFailure( message:"Single Invocation - Event");
end if;

if ( ( tr == tr2 ) and ( tr3 == tr4 ) )
  LOG::LogSuccess( message:"Single Invocation - Timer" );
else
  LOG::LogFailure( message:"Single Invocation - Timer");
end if;

if ( ( i == i2 ) and ( i3 == i4 ) )
  LOG::LogSuccess( message:"Single Invocation - Integer" );
else
  LOG::LogFailure( message:"Single Invocation - Integer");
  LOG::LogInt( message:"Function Invocation", int:i );
  LOG::LogInt( message:"Bridge Invocation", int:i2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i3 );
  LOG::LogInt( message:"Class Operation Invocation", int:i4 );
end if;

if ( ( r == r2 ) and ( r3 == r4 ) )
  LOG::LogSuccess( message:"Single Invocation - Real" );
else
  LOG::LogFailure( message:"Single Invocation - Real");
  LOG::LogReal( message:"Function Invocation", real:r );
  LOG::LogReal( message:"Bridge Invocation", real:r2 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r3 );
  LOG::LogReal( message:"Class Operation Invocation", real:r4 );
end if;

if ( ( s == s2 ) and ( s3 == s4 ) )
  LOG::LogSuccess( message:"Single Invocation - String" );
else
  LOG::LogFailure( message:"Single Invocation - String");
  LOG::LogString( message:"Function Invocation", str:s );
  LOG::LogString( message:"Bridge Invocation", str:s2 );
  LOG::LogString( message:"Instance Operation Invocation", str:s3 );
  LOG::LogString( message:"Class Operation Invocation", str:s4 );
end if;

if ( ( t== t2 ) and ( t3 == t4 ) )
  LOG::LogSuccess( message:"Single Invocation - Timestamp" );
else
  LOG::LogFailure( message:"Single Invocation - Timestamp");
  LOG::LogTime( message:"Function Invocation", time:t );
  LOG::LogTime( message:"Bridge Invocation", time:t2 );
  LOG::LogTime( message:"Instance Operation Invocation", time:t3 );
  LOG::LogTime( message:"Class Operation Invocation", time:t4 );
end if;

if ( ( id == id2 ) and ( id3 == id4 ) )
  LOG::LogSuccess( message:"Single Invocation - Unique ID" );
else
  LOG::LogFailure( message:"Single Invocation - Unique ID");
  LOG::LogUniqueId( message:"Function Invocation", uid:id );
  LOG::LogUniqueId( message:"Bridge Invocation", uid:id2 );
  LOG::LogUniqueId( message:"Instance Operation Invocation", uid:id3 );
  LOG::LogUniqueId( message:"Class Operation Invocation", uid:id4 );
end if;

if ( ( en == en2 ) and ( en3 == en4 ) )
  LOG::LogSuccess( message:"Single Invocation - Enumeration" );
else
  LOG::LogFailure( message:"Single Invocation - Enumeration");
end if;

if ( ( u == u2 ) and ( u3 == u4 ) )
  LOG::LogSuccess( message:"Single Invocation - User Data Type" );
else
  LOG::LogFailure( message:"Single Invocation - User Data Type");
  LOG::LogInt( message:"Function Invocation", int:u );
  LOG::LogInt( message:"Bridge Invocation", int:u2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u3 );
  LOG::LogInt( message:"Class Operation Invocation", int:u4 );
end if;',
	1);
INSERT INTO O_TFR
	VALUES (1048600,
	1048579,
	'single_invoke2',
	'',
	524289,
	0,
	'select any tc from instances of TC;

////////////////////////////
// Functions
////////////////////////////
b = ::bool1(date1:tc.d);
d = ::date1(evt1:tc.e);
e = ::evt1(timer1:tc.tr);
tr = ::timer1(int1:tc.i);
i = ::int1(real1:tc.r);
r = ::real1(str1:tc.s);
s = ::str1(time1:tc.t);
t = ::time1(id1:tc.ID);
id = ::id1(enum1:tc.en);
en = ::enum1(udt1:tc.u);
u = ::udt1(bool1:tc.b);

////////////////////////////
// Bridges
////////////////////////////
b2 = EE::bool2(date2:tc.d);
d2 = EE::date2(evt2:tc.e);
e2 =  EE::evt2(timer2:tc.tr);
tr2 =  EE::timer2(int2:tc.i);
i2 =  EE::int2(real2:tc.r);
r2 =  EE::real2(str2:tc.s);
s2 =  EE::str2(time2:tc.t);
t2 =  EE::time2(id2:tc.ID);
id2 =  EE::id2(enum2:tc.en);
en2 = EE::enum2(udt2:tc.u);
u2 = EE::udt2(bool2:tc.b);

////////////////////////////
// Instance Operations
////////////////////////////
b3 = tc.bool3(date3:tc.d);
d3 =  tc.date3(evt3:tc.e);
e3 =  tc.evt3(timer3:tc.tr);
tr3 =  tc.timer3(int3:tc.i);
i3 =  tc.int3(real3:tc.r);
r3 =  tc.real3(str3:tc.s);
s3 =  tc.str3(time3:tc.t);
t3 =  tc.time3(id3:tc.ID);
id3 =  tc.id3(enum3:tc.en);
en3 = tc.enum3(udt3:tc.u);
u3 = tc.udt3(bool3:tc.b);

////////////////////////////
// Class Operations
////////////////////////////
b4 = TC::bool4(date4:tc.d);
d4 = TC::date4(evt4:tc.e);
e4 = TC::evt4(timer4:tc.tr);
tr4 = TC::timer4(int4:tc.i);
i4 = TC::int4(real4:tc.r);
r4 = TC::real4(str4:tc.s);
s4 = TC::str4(time4:tc.t);
t4 = TC::time4(id4:tc.ID);
id4 = TC::id4(enum4:tc.en);
en4 = TC::enum4(udt4:tc.u);
u4 = TC::udt4(bool4:tc.b);

////////////////////////////
// Results
////////////////////////////
if ( b and b2 and b3 and b4 )
  LOG::LogSuccess( message:"Single Invocation - Boolean" );
else
  LOG::LogFailure( message:"Single Invocation - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:b2 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b3 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b4 );
end if;

if ( ( d == d2 ) and ( d3 == d4 ) )
  LOG::LogSuccess( message:"Single Invocation - Date" );
else
  LOG::LogFailure( message:"Single Invocation - Date");
  LOG::LogDate( message:"Function Invocation", date:d );
  LOG::LogDate( message:"Bridge Invocation", date:d2 );
  LOG::LogDate( message:"Instance Operation Invocation", date:d3 );
  LOG::LogDate( message:"Class Operation Invocation", date:d4 );
end if;

if ( ( e == e2 ) and ( e3 == e4 ) )
  LOG::LogSuccess( message:"Single Invocation - Event" );
else
  LOG::LogFailure( message:"Single Invocation - Event");
end if;

if ( ( tr == tr2 ) and ( tr3 == tr4 ) )
  LOG::LogSuccess( message:"Single Invocation - Timer" );
else
  LOG::LogFailure( message:"Single Invocation - Timer");
end if;

if ( ( i == i2 ) and ( i3 == i4 ) )
  LOG::LogSuccess( message:"Single Invocation - Integer" );
else
  LOG::LogFailure( message:"Single Invocation - Integer");
  LOG::LogInt( message:"Function Invocation", int:i );
  LOG::LogInt( message:"Bridge Invocation", int:i2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i3 );
  LOG::LogInt( message:"Class Operation Invocation", int:i4 );
end if;

if ( ( r == r2 ) and ( r3 == r4 ) )
  LOG::LogSuccess( message:"Single Invocation - Real" );
else
  LOG::LogFailure( message:"Single Invocation - Real");
  LOG::LogReal( message:"Function Invocation", real:r );
  LOG::LogReal( message:"Bridge Invocation", real:r2 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r3 );
  LOG::LogReal( message:"Class Operation Invocation", real:r4 );
end if;

if ( ( s == s2 ) and ( s3 == s4 ) )
  LOG::LogSuccess( message:"Single Invocation - String" );
else
  LOG::LogFailure( message:"Single Invocation - String");
  LOG::LogString( message:"Function Invocation", str:s );
  LOG::LogString( message:"Bridge Invocation", str:s2 );
  LOG::LogString( message:"Instance Operation Invocation", str:s3 );
  LOG::LogString( message:"Class Operation Invocation", str:s4 );
end if;

if ( ( t== t2 ) and ( t3 == t4 ) )
  LOG::LogSuccess( message:"Single Invocation - Timestamp" );
else
  LOG::LogFailure( message:"Single Invocation - Timestamp");
  LOG::LogTime( message:"Function Invocation", time:t );
  LOG::LogTime( message:"Bridge Invocation", time:t2 );
  LOG::LogTime( message:"Instance Operation Invocation", time:t3 );
  LOG::LogTime( message:"Class Operation Invocation", time:t4 );
end if;

if ( ( id == id2 ) and ( id3 == id4 ) )
  LOG::LogSuccess( message:"Single Invocation - Unique ID" );
else
  LOG::LogFailure( message:"Single Invocation - Unique ID");
  LOG::LogUniqueId( message:"Function Invocation", uid:id );
  LOG::LogUniqueId( message:"Bridge Invocation", uid:id2 );
  LOG::LogUniqueId( message:"Instance Operation Invocation", uid:id3 );
  LOG::LogUniqueId( message:"Class Operation Invocation", uid:id4 );
end if;

if ( ( en == en2 ) and ( en3 == en4 ) )
  LOG::LogSuccess( message:"Single Invocation - Enumeration" );
else
  LOG::LogFailure( message:"Single Invocation - Enumeration");
end if;

if ( ( u == u2 ) and ( u3 == u4 ) )
  LOG::LogSuccess( message:"Single Invocation - User Data Type" );
else
  LOG::LogFailure( message:"Single Invocation - User Data Type");
  LOG::LogInt( message:"Function Invocation", int:u );
  LOG::LogInt( message:"Bridge Invocation", int:u2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u3 );
  LOG::LogInt( message:"Class Operation Invocation", int:u4 );
end if;',
	1);
INSERT INTO O_TFR
	VALUES (1048601,
	1048579,
	'multiple_invoke',
	'',
	524289,
	1,
	'select any tc from instances of TC;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Functions
////////////////////////////
b = ::bool1(date1: ::date1(evt1:tc.e));
d = ::date1(evt1:EE::evt2(timer2:tc.tr));
e = ::evt1(timer1: tc.timer3(int3:tc.i));
tr = ::timer1(int1:TC::int4(real4:tc.r));
i = ::int1(real1: ::real1(str1:tc.s));
r = ::real1(str1:  EE::str2(time2:tc.t));
s = ::str1(time1: tc.time3(id3:tc.ID));
t = ::time1(id1: TC::id4(enum4:tc.en));
id = ::id1(enum1: ::enum1(udt1:tc.u));
en = ::enum1(udt1:EE::udt2(bool2:tc.b));
u = ::udt1(bool1: tc.bool3(date3:tc.d));

////////////////////////////
// Bridges
////////////////////////////
b2 = EE::bool2(date2: ::date1(evt1:tc.e));
d2 = EE::date2(evt2:  EE::evt2(timer2:tc.tr));
e2 =  EE::evt2(timer2: tc.timer3(int3:tc.i));
tr2 =  EE::timer2(int2: TC::int4(real4:tc.r));
i2 =  EE::int2(real2: ::real1(str1:tc.s));
r2 =  EE::real2(str2:  EE::str2(time2:tc.t));
s2 =  EE::str2(time2: tc.time3(id3:tc.ID));
t2 =  EE::time2(id2: TC::id4(enum4:tc.en));
id2 =  EE::id2(enum2: ::enum1(udt1:tc.u));
en2 = EE::enum2(udt2: EE::udt2(bool2:tc.b));
u2 = EE::udt2(bool2: tc.bool3(date3:tc.d));

////////////////////////////
// Instance Operations
////////////////////////////
b3 = tc.bool3(date3: ::date1(evt1:tc.e));
d3 =  tc.date3(evt3: EE::evt2(timer2:tc.tr));
e3 =  tc.evt3(timer3:  tc.timer3(int3:tc.i));
tr3 =  tc.timer3(int3:TC::int4(real4:tc.r));
i3 =  tc.int3(real3: ::real1(str1:tc.s));
r3 =  tc.real3(str3: EE::str2(time2:tc.t));
s3 =  tc.str3(time3: tc.time3(id3:tc.ID));
t3 =  tc.time3(id3: TC::id4(enum4:tc.en));
id3 =  tc.id3(enum3: ::enum1(udt1:tc.u));
en3 = tc.enum3(udt3: EE::udt2(bool2:tc.b));
u3 = tc.udt3(bool3: tc.bool3(date3:tc.d));

////////////////////////////
// Class Operations
////////////////////////////
b4 = TC::bool4(date4: ::date1(evt1:tc.e));
d4 = TC::date4(evt4: EE::evt2(timer2:tc.tr));
e4 = TC::evt4(timer4: tc.timer3(int3:tc.i));
tr4 = TC::timer4(int4:TC::int4(real4:tc.r));
i4 = TC::int4(real4: ::real1(str1:tc.s));
r4 = TC::real4(str4: EE::str2(time2:tc.t));
s4 = TC::str4(time4:tc.time3(id3:tc.ID));
t4 = TC::time4(id4:TC::id4(enum4:tc.en));
id4 = TC::id4(enum4: ::enum1(udt1:tc.u));
en4 = TC::enum4(udt4:EE::udt2(bool2:tc.b));
u4 = TC::udt4(bool4: tc.bool3(date3:tc.d));

////////////////////////////
// Results
////////////////////////////
if ( b and b2 and b3 and b4 )
  LOG::LogSuccess( message:"Multiple Invocation - Boolean" );
else
  LOG::LogFailure( message:"Multiple Invocation - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:b2 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b3 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b4 );
end if;

if ( ( d == d2 ) and ( d3 == d4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Date" );
else
  LOG::LogFailure( message:"Multiple Invocation - Date");
  LOG::LogDate( message:"Function Invocation", date:d );
  LOG::LogDate( message:"Bridge Invocation", date:d2 );
  LOG::LogDate( message:"Instance Operation Invocation", date:d3 );
  LOG::LogDate( message:"Class Operation Invocation", date:d4 );
end if;

if ( ( e == e2 ) and ( e3 == e4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Event" );
else
  LOG::LogFailure( message:"Multiple Invocation - Event");
end if;

if ( ( tr == tr2 ) and ( tr3 == tr4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Timer" );
else
  LOG::LogFailure( message:"Multiple Invocation - Timer");
end if;

if ( ( i == i2 ) and ( i3 == i4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Integer" );
else
  LOG::LogFailure( message:"Multiple Invocation - Integer");
  LOG::LogInt( message:"Function Invocation", int:i );
  LOG::LogInt( message:"Bridge Invocation", int:i2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i3 );
  LOG::LogInt( message:"Class Operation Invocation", int:i4 );
end if;

if ( ( r == r2 ) and ( r3 == r4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Real" );
else
  LOG::LogFailure( message:"Multiple Invocation - Real");
  LOG::LogReal( message:"Function Invocation", real:r );
  LOG::LogReal( message:"Bridge Invocation", real:r2 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r3 );
  LOG::LogReal( message:"Class Operation Invocation", real:r4 );
end if;

if ( ( s == s2 ) and ( s3 == s4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - String" );
else
  LOG::LogFailure( message:"Multiple Invocation - String");
  LOG::LogString( message:"Function Invocation", str:s );
  LOG::LogString( message:"Bridge Invocation", str:s2 );
  LOG::LogString( message:"Instance Operation Invocation", str:s3 );
  LOG::LogString( message:"Class Operation Invocation", str:s4 );
end if;

if ( ( t== t2 ) and ( t3 == t4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Timestamp" );
else
  LOG::LogFailure( message:"Multiple Invocation - Timestamp");
  LOG::LogTime( message:"Function Invocation", time:t );
  LOG::LogTime( message:"Bridge Invocation", time:t2 );
  LOG::LogTime( message:"Instance Operation Invocation", time:t3 );
  LOG::LogTime( message:"Class Operation Invocation", time:t4 );
end if;

if ( ( id == id2 ) and ( id3 == id4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Unique ID" );
else
  LOG::LogFailure( message:"Multiple Invocation - Unique ID");
  LOG::LogUniqueId( message:"Function Invocation", uid:id );
  LOG::LogUniqueId( message:"Bridge Invocation", uid:id2 );
  LOG::LogUniqueId( message:"Instance Operation Invocation", uid:id3 );
  LOG::LogUniqueId( message:"Class Operation Invocation", uid:id4 );
end if;

if ( ( en == en2 ) and ( en3 == en4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Enumeration" );
else
  LOG::LogFailure( message:"Multiple Invocation - Enumeration");
end if;

if ( ( u == u2 ) and ( u3 == u4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - User Data Type" );
else
  LOG::LogFailure( message:"Multiple Invocation - User Data Type");
  LOG::LogInt( message:"Function Invocation", int:u );
  LOG::LogInt( message:"Bridge Invocation", int:u2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u3 );
  LOG::LogInt( message:"Class Operation Invocation", int:u4 );
end if;',
	1);
INSERT INTO O_TFR
	VALUES (1048602,
	1048579,
	'multiple_invoke2',
	'',
	524289,
	0,
	'select any tc from instances of TC;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Functions
////////////////////////////
b = ::bool1(date1: ::date1(evt1:tc.e));
d = ::date1(evt1:EE::evt2(timer2:tc.tr));
e = ::evt1(timer1: tc.timer3(int3:tc.i));
tr = ::timer1(int1:TC::int4(real4:tc.r));
i = ::int1(real1: ::real1(str1:tc.s));
r = ::real1(str1:  EE::str2(time2:tc.t));
s = ::str1(time1: tc.time3(id3:tc.ID));
t = ::time1(id1: TC::id4(enum4:tc.en));
id = ::id1(enum1: ::enum1(udt1:tc.u));
en = ::enum1(udt1:EE::udt2(bool2:tc.b));
u = ::udt1(bool1: tc.bool3(date3:tc.d));

////////////////////////////
// Bridges
////////////////////////////
b2 = EE::bool2(date2: ::date1(evt1:tc.e));
d2 = EE::date2(evt2:  EE::evt2(timer2:tc.tr));
e2 =  EE::evt2(timer2: tc.timer3(int3:tc.i));
tr2 =  EE::timer2(int2: TC::int4(real4:tc.r));
i2 =  EE::int2(real2: ::real1(str1:tc.s));
r2 =  EE::real2(str2:  EE::str2(time2:tc.t));
s2 =  EE::str2(time2: tc.time3(id3:tc.ID));
t2 =  EE::time2(id2: TC::id4(enum4:tc.en));
id2 =  EE::id2(enum2: ::enum1(udt1:tc.u));
en2 = EE::enum2(udt2: EE::udt2(bool2:tc.b));
u2 = EE::udt2(bool2: tc.bool3(date3:tc.d));

////////////////////////////
// Instance Operations
////////////////////////////
b3 = tc.bool3(date3: ::date1(evt1:tc.e));
d3 =  tc.date3(evt3: EE::evt2(timer2:tc.tr));
e3 =  tc.evt3(timer3:  tc.timer3(int3:tc.i));
tr3 =  tc.timer3(int3:TC::int4(real4:tc.r));
i3 =  tc.int3(real3: ::real1(str1:tc.s));
r3 =  tc.real3(str3: EE::str2(time2:tc.t));
s3 =  tc.str3(time3: tc.time3(id3:tc.ID));
t3 =  tc.time3(id3: TC::id4(enum4:tc.en));
id3 =  tc.id3(enum3: ::enum1(udt1:tc.u));
en3 = tc.enum3(udt3: EE::udt2(bool2:tc.b));
u3 = tc.udt3(bool3: tc.bool3(date3:tc.d));

////////////////////////////
// Class Operations
////////////////////////////
b4 = TC::bool4(date4: ::date1(evt1:tc.e));
d4 = TC::date4(evt4: EE::evt2(timer2:tc.tr));
e4 = TC::evt4(timer4: tc.timer3(int3:tc.i));
tr4 = TC::timer4(int4:TC::int4(real4:tc.r));
i4 = TC::int4(real4: ::real1(str1:tc.s));
r4 = TC::real4(str4: EE::str2(time2:tc.t));
s4 = TC::str4(time4:tc.time3(id3:tc.ID));
t4 = TC::time4(id4:TC::id4(enum4:tc.en));
id4 = TC::id4(enum4: ::enum1(udt1:tc.u));
en4 = TC::enum4(udt4:EE::udt2(bool2:tc.b));
u4 = TC::udt4(bool4: tc.bool3(date3:tc.d));

////////////////////////////
// Results
////////////////////////////
if ( b and b2 and b3 and b4 )
  LOG::LogSuccess( message:"Multiple Invocation - Boolean" );
else
  LOG::LogFailure( message:"Multiple Invocation - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:b2 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b3 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b4 );
end if;

if ( ( d == d2 ) and ( d3 == d4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Date" );
else
  LOG::LogFailure( message:"Multiple Invocation - Date");
  LOG::LogDate( message:"Function Invocation", date:d );
  LOG::LogDate( message:"Bridge Invocation", date:d2 );
  LOG::LogDate( message:"Instance Operation Invocation", date:d3 );
  LOG::LogDate( message:"Class Operation Invocation", date:d4 );
end if;

if ( ( e == e2 ) and ( e3 == e4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Event" );
else
  LOG::LogFailure( message:"Multiple Invocation - Event");
end if;

if ( ( tr == tr2 ) and ( tr3 == tr4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Timer" );
else
  LOG::LogFailure( message:"Multiple Invocation - Timer");
end if;

if ( ( i == i2 ) and ( i3 == i4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Integer" );
else
  LOG::LogFailure( message:"Multiple Invocation - Integer");
  LOG::LogInt( message:"Function Invocation", int:i );
  LOG::LogInt( message:"Bridge Invocation", int:i2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i3 );
  LOG::LogInt( message:"Class Operation Invocation", int:i4 );
end if;

if ( ( r == r2 ) and ( r3 == r4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Real" );
else
  LOG::LogFailure( message:"Multiple Invocation - Real");
  LOG::LogReal( message:"Function Invocation", real:r );
  LOG::LogReal( message:"Bridge Invocation", real:r2 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r3 );
  LOG::LogReal( message:"Class Operation Invocation", real:r4 );
end if;

if ( ( s == s2 ) and ( s3 == s4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - String" );
else
  LOG::LogFailure( message:"Multiple Invocation - String");
  LOG::LogString( message:"Function Invocation", str:s );
  LOG::LogString( message:"Bridge Invocation", str:s2 );
  LOG::LogString( message:"Instance Operation Invocation", str:s3 );
  LOG::LogString( message:"Class Operation Invocation", str:s4 );
end if;

if ( ( t== t2 ) and ( t3 == t4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Timestamp" );
else
  LOG::LogFailure( message:"Multiple Invocation - Timestamp");
  LOG::LogTime( message:"Function Invocation", time:t );
  LOG::LogTime( message:"Bridge Invocation", time:t2 );
  LOG::LogTime( message:"Instance Operation Invocation", time:t3 );
  LOG::LogTime( message:"Class Operation Invocation", time:t4 );
end if;

if ( ( id == id2 ) and ( id3 == id4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Unique ID" );
else
  LOG::LogFailure( message:"Multiple Invocation - Unique ID");
  LOG::LogUniqueId( message:"Function Invocation", uid:id );
  LOG::LogUniqueId( message:"Bridge Invocation", uid:id2 );
  LOG::LogUniqueId( message:"Instance Operation Invocation", uid:id3 );
  LOG::LogUniqueId( message:"Class Operation Invocation", uid:id4 );
end if;

if ( ( en == en2 ) and ( en3 == en4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - Enumeration" );
else
  LOG::LogFailure( message:"Multiple Invocation - Enumeration");
end if;

if ( ( u == u2 ) and ( u3 == u4 ) )
  LOG::LogSuccess( message:"Multiple Invocation - User Data Type" );
else
  LOG::LogFailure( message:"Multiple Invocation - User Data Type");
  LOG::LogInt( message:"Function Invocation", int:u );
  LOG::LogInt( message:"Bridge Invocation", int:u2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u3 );
  LOG::LogInt( message:"Class Operation Invocation", int:u4 );
end if;',
	1);
INSERT INTO O_TFR
	VALUES (1048603,
	1048579,
	'multiple_invoke_w_expressions',
	'',
	524289,
	1,
	'select any tc from instances of TC;

select any tc4 from instances of TC4;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Functions
////////////////////////////
b = ::bool1( date1: ::date1( evt1:tc.e ) ) and REE::bool5(date5: tc.date3( evt3:tc.e ) );
tr = ::timer1( int1:TC::int4( real4:tc.r )      +    ::int1( real1: self.real3( str3:tc.s ) ) );
i = ::int1(real1: ::real1(str1:tc.s)               +   EE::real2( str2: tc.str3( time3:tc.t ) ) );
r = ::real1(str1: EE::str2(time2:tc.t)          +    self.str3( time3: TC::time4( id4:tc.ID ) ) );
s = ::str1( time1: EE::time2( id2: tc.ID ) )  +    tc.str3( time3: TC::time4( id4: tc.ID ) );
en = ::enum1(udt1:EE::udt2(bool2:tc.b)   +    tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u = ::udt1(bool1: self.bool3(date3:tc.d)    and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Bridges
////////////////////////////
b2 = EE::bool2(date2: ::date1(evt1:tc.e))  and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tr2 =  EE::timer2(int2: TC::int4(real4:tc.r)     +   ::int1( real1: tc.real3( str3:tc.s ) ) );
i2 =  EE::int2(real2: ::real1(str1:tc.s)            +  REE::real5( str5: self.str3( time3:tc.t ) ) );
r2 =  EE::real2(str2:  EE::str2(time2:tc.t)      +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s2 =  EE::str2(time2: tc.time3(id3:tc.ID))       +  self.str3( time3: TC::time4( id4: tc.ID ) );
en2 = EE::enum2(udt2: EE::udt2(bool2:tc.b) +  self.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u2 = EE::udt2(bool2: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Instance Operations
////////////////////////////
b3 = self.bool3(date3: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tr3 =  tc.timer3(int3:TC4::int7(real7:tc.r)       +   ::int1( real1: self.real3( str3:tc.s ) ) );
i3 =  self.int3(real3: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
r3 =  tc.real3(str3: EE::str2(time2:tc.t)        +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s3 =  tc.str3(time3: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
en3 = tc.enum3(udt3: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u3 = self.udt3(bool3: self.bool3(date3:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Class Operations
////////////////////////////
b4 = TC::bool4(date4: ::date1(evt1:tc.e))   and EE::bool2(date2: self.date3( evt3:tc.e ) );
tr4 = TC::timer4(int4:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
i4 = TC::int4(real4: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
r4 = TC::real4(str4: REE::str5(time5:tc.t)        +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s4 = TC::str4(time4:self.time3(id3:tc.ID))       +  tc4.str6( time6: TC::time4( id4: tc.ID ) );
en4 = TC::enum4(udt4:EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u4 = TC::udt4(bool4: self.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Results
////////////////////////////
if ( b and b2 and b3 and b4 )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Boolean" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:b2 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b3 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b4 );
end if;

if ( ( tr == tr2 ) and ( tr3 == tr4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Timer" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Timer");
end if;

if ( ( i == i2 ) and ( i3 == i4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Integer" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Integer");
  LOG::LogInt( message:"Function Invocation", int:i );
  LOG::LogInt( message:"Bridge Invocation", int:i2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i3 );
  LOG::LogInt( message:"Class Operation Invocation", int:i4 );
end if;

if ( ( r == r2 ) and ( r3 == r4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Real" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Real");
  LOG::LogReal( message:"Function Invocation", real:r );
  LOG::LogReal( message:"Bridge Invocation", real:r2 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r3 );
  LOG::LogReal( message:"Class Operation Invocation", real:r4 );
end if;

if ( ( s == s2 ) and ( s3 == s4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - String" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - String");
  LOG::LogString( message:"Function Invocation", str:s );
  LOG::LogString( message:"Bridge Invocation", str:s2 );
  LOG::LogString( message:"Instance Operation Invocation", str:s3 );
  LOG::LogString( message:"Class Operation Invocation", str:s4 );
end if;

if ( ( en == en2 ) and ( en3 == en4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Enumeration" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Enumeration");
end if;

if ( ( u == u2 ) and ( u3 == u4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - User Data Type" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - User Data Type");
  LOG::LogInt( message:"Function Invocation", int:u );
  LOG::LogInt( message:"Bridge Invocation", int:u2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u3 );
  LOG::LogInt( message:"Class Operation Invocation", int:u4 );
end if;',
	1);
INSERT INTO O_TFR
	VALUES (1048604,
	1048579,
	'multiple_invoke_w_expressions2',
	'',
	524289,
	0,
	'select any tc from instances of TC;


select any tc4 from instances of TC4;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Functions
////////////////////////////
b = ::bool1( date1: ::date1( evt1:tc.e ) ) and REE::bool5(date5: tc.date3( evt3:tc.e ) );
tr = ::timer1( int1:TC::int4( real4:tc.r )      +    ::int1( real1: tc.real3( str3:tc.s ) ) );
i = ::int1(real1: ::real1(str1:tc.s)               +   EE::real2( str2: tc.str3( time3:tc.t ) ) );
r = ::real1(str1: EE::str2(time2:tc.t)          +    tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s = ::str1( time1: EE::time2( id2: tc.ID ) )  +    tc.str3( time3: TC::time4( id4: tc.ID ) );
en = ::enum1(udt1:EE::udt2(bool2:tc.b)   +    tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u = ::udt1(bool1: tc.bool3(date3:tc.d)    and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Bridges
////////////////////////////
b2 = EE::bool2(date2: ::date1(evt1:tc.e))  and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tr2 =  EE::timer2(int2: TC::int4(real4:tc.r)     +   ::int1( real1: tc.real3( str3:tc.s ) ) );
i2 =  EE::int2(real2: ::real1(str1:tc.s)            +  REE::real5( str5: tc.str3( time3:tc.t ) ) );
r2 =  EE::real2(str2:  EE::str2(time2:tc.t)      +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s2 =  EE::str2(time2: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
en2 = EE::enum2(udt2: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u2 = EE::udt2(bool2: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Instance Operations
////////////////////////////
b3 = tc.bool3(date3: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tr3 =  tc.timer3(int3:TC4::int7(real7:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
i3 =  tc.int3(real3: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
r3 =  tc.real3(str3: EE::str2(time2:tc.t)        +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s3 =  tc.str3(time3: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
en3 = tc.enum3(udt3: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u3 = tc.udt3(bool3: tc.bool3(date3:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Class Operations
////////////////////////////
b4 = TC::bool4(date4: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tr4 = TC::timer4(int4:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
i4 = TC::int4(real4: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
r4 = TC::real4(str4: REE::str5(time5:tc.t)        +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s4 = TC::str4(time4:tc.time3(id3:tc.ID))       +  tc4.str6( time6: TC::time4( id4: tc.ID ) );
en4 = TC::enum4(udt4:EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u4 = TC::udt4(bool4: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Results
////////////////////////////
if ( b and b2 and b3 and b4 )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Boolean" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:b2 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b3 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b4 );
end if;

if ( ( tr == tr2 ) and ( tr3 == tr4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Timer" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Timer");
end if;

if ( ( i == i2 ) and ( i3 == i4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Integer" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Integer");
  LOG::LogInt( message:"Function Invocation", int:i );
  LOG::LogInt( message:"Bridge Invocation", int:i2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i3 );
  LOG::LogInt( message:"Class Operation Invocation", int:i4 );
end if;

if ( ( r == r2 ) and ( r3 == r4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Real" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Real");
  LOG::LogReal( message:"Function Invocation", real:r );
  LOG::LogReal( message:"Bridge Invocation", real:r2 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r3 );
  LOG::LogReal( message:"Class Operation Invocation", real:r4 );
end if;

if ( ( s == s2 ) and ( s3 == s4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - String" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - String");
  LOG::LogString( message:"Function Invocation", str:s );
  LOG::LogString( message:"Bridge Invocation", str:s2 );
  LOG::LogString( message:"Instance Operation Invocation", str:s3 );
  LOG::LogString( message:"Class Operation Invocation", str:s4 );
end if;

if ( ( en == en2 ) and ( en3 == en4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Enumeration" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Enumeration");
end if;

if ( ( u == u2 ) and ( u3 == u4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - User Data Type" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - User Data Type");
  LOG::LogInt( message:"Function Invocation", int:u );
  LOG::LogInt( message:"Bridge Invocation", int:u2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u3 );
  LOG::LogInt( message:"Class Operation Invocation", int:u4 );
end if;',
	1);
INSERT INTO O_TFR
	VALUES (1048605,
	1048579,
	'ret3',
	'',
	524290,
	1,
	'select any tc from instances of TC;

return tc.bool3( date3: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) );',
	1);
INSERT INTO O_TFR
	VALUES (1048606,
	1048579,
	'ret4',
	'',
	524290,
	0,
	'select any tc from instances of TC;

return TC::bool4( date4: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) );',
	1);
INSERT INTO O_TFR
	VALUES (1048607,
	1048579,
	'mod_params3',
	'',
	524289,
	1,
	'//////////////////////////////
// Param Assign
//////////////////////////////

select any tc from instances of TC;
select any tc_4 from instances of TC4;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Functions
////////////////////////////
param.b = ::bool1( date1: ::date1( evt1:tc.e ) ) and EE::bool2(date2: tc.date3( evt3:tc.e ) );
param.tr = ::timer1( int1:TC::int4( real4:tc.r )      +    ::int1( real1: tc.real3( str3:tc.s ) ) );
param.i = ::int1(real1: ::real1(str1:tc.s)               +   EE::real2( str2: tc.str3( time3:tc.t ) ) );
param.r = ::real1(str1: EE::str2(time2:tc.t)          +    tc.str3( time3: TC::time4( id4:tc.ID ) ) );
param.s = ::str1( time1: EE::time2( id2: tc.ID ) )  +    tc.str3( time3: TC::time4( id4: tc.ID ) );
param.en = ::enum1(udt1:EE::udt2(bool2:tc.b)   +    tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
param.u = ::udt1(bool1: tc.bool3(date3:tc.d)    and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Bridges
////////////////////////////
param.b2 = EE::bool2(date2: ::date1(evt1:tc.e))  and EE::bool2(date2: tc.date3( evt3:tc.e ) );
param.tr2 =  EE::timer2(int2: TC::int4(real4:tc.r)     +   ::int1( real1: tc.real3( str3:tc.s ) ) );
param.i2 =  REE::int5(real5: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
param.r2 =  REE::real5(str5:  EE::str2(time2:tc.t)      +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
param.s2 =  EE::str2(time2: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
param.en2 = EE::enum2(udt2: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
param.u2 = EE::udt2(bool2: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Instance Operations
////////////////////////////
param.b3 = tc.bool3(date3: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
param.tr3 =  tc.timer3(int3:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
param.i3 =  tc.int3(real3: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
param.r3 =  tc_4.real6(str6: EE::str2(time2:tc.t)        +   tc_4.str6( time6: TC::time4( id4:tc.ID ) ) );
param.s3 =  tc_4.str6(time6: TC::time4(id4:tc.ID))       +  tc_4.str6( time6: TC::time4( id4: tc.ID ) );
param.en3 = tc.enum3(udt3: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
param.u3 = tc.udt3(bool3: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Class Operations
////////////////////////////
param.b4 = TC::bool4(date4: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
param.tr4 = TC::timer4(int4:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
param.i4 = TC4::int7(real7: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
param.r4 = TC::real4(str4: EE::str2(time2:tc.t)        +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
param.s4 = TC4::str7(time7:tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
param.en4 = TC::enum4(udt4:EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
param.u4 = TC::udt4(bool4: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Results
////////////////////////////
if ( param.b and param.b2 and param.b3 and param.b4 )
  LOG::LogSuccess( message:"Assign Param - Boolean" );
else
  LOG::LogFailure( message:"Assign Param - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:param.b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:param.b );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:param.b );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:param.b );
end if;

if ( ( param.tr ==  param.tr2 ) and (  param.tr3 ==  param.tr4 ) )
  LOG::LogSuccess( message:"Assign Param - Timer" );
else
  LOG::LogFailure( message:"Assign Param - Timer");
end if;

if ( ( param.i ==  param.i2 ) and (  param.i3 ==  param.i4 ) )
  LOG::LogSuccess( message:"Assign Param - Integer" );
else
  LOG::LogFailure( message:"Assign Param - Integer");
  LOG::LogInt( message:"Function Invocation", int: param.i );
  LOG::LogInt( message:"Bridge Invocation", int: param.i );
  LOG::LogInt( message:"Instance Operation Invocation", int: param.i );
  LOG::LogInt( message:"Class Operation Invocation", int: param.i );
end if;

if ( (  param.r ==  param.r2 ) and (  param.r3 ==  param.r4 ) )
  LOG::LogSuccess( message:"Assign Param - Real" );
else
  LOG::LogFailure( message:"Assign Param - Real");
  LOG::LogReal( message:"Function Invocation", real: param.r );
  LOG::LogReal( message:"Bridge Invocation", real: param.r );
  LOG::LogReal( message:"Instance Operation Invocation", real: param.r );
  LOG::LogReal( message:"Class Operation Invocation", real: param.r );
end if;

if ( (  param.s ==  param.s2 ) and (  param.s3 ==  param.s4 ) )
  LOG::LogSuccess( message:"Assign Param - String" );
else
  LOG::LogFailure( message:"Assign Param - String");
  LOG::LogString( message:"Function Invocation", str: param.s );
  LOG::LogString( message:"Bridge Invocation", str: param.s );
  LOG::LogString( message:"Instance Operation Invocation", str: param.s );
  LOG::LogString( message:"Class Operation Invocation", str: param.s );
end if;

if ( (  param.en ==  param.en2 ) and (  param.en3 ==  param.en4 ) )
  LOG::LogSuccess( message:"Assign Param - Enumeration" );
else
  LOG::LogFailure( message:"Assign Param - Enumeration");
end if;

if ( (  param.u ==  param.u2 ) and (  param.u3 ==  param.u4 ) )
  LOG::LogSuccess( message:"Assign Param - User Data Type" );
else
  LOG::LogFailure( message:"Assign Param - User Data Type");
  LOG::LogInt( message:"Function Invocation", int: param.u );
  LOG::LogInt( message:"Bridge Invocation", int: param.u );
  LOG::LogInt( message:"Instance Operation Invocation", int: param.u );
  LOG::LogInt( message:"Class Operation Invocation", int: param.u );
end if;',
	1);
INSERT INTO O_TPARM
	VALUES (1048599,
	1048607,
	'b',
	524290,
	1);
INSERT INTO O_TPARM
	VALUES (1048600,
	1048607,
	'tr',
	524304,
	1);
INSERT INTO O_TPARM
	VALUES (1048601,
	1048607,
	'i',
	524291,
	1);
INSERT INTO O_TPARM
	VALUES (1048602,
	1048607,
	'r',
	524292,
	1);
INSERT INTO O_TPARM
	VALUES (1048603,
	1048607,
	's',
	524293,
	1);
INSERT INTO O_TPARM
	VALUES (1048604,
	1048607,
	'en',
	524307,
	1);
INSERT INTO O_TPARM
	VALUES (1048605,
	1048607,
	'u',
	524306,
	1);
INSERT INTO O_TPARM
	VALUES (1048606,
	1048607,
	'b2',
	524290,
	1);
INSERT INTO O_TPARM
	VALUES (1048607,
	1048607,
	'tr2',
	524304,
	1);
INSERT INTO O_TPARM
	VALUES (1048608,
	1048607,
	'i2',
	524291,
	1);
INSERT INTO O_TPARM
	VALUES (1048609,
	1048607,
	'r2',
	524292,
	1);
INSERT INTO O_TPARM
	VALUES (1048610,
	1048607,
	's2',
	524293,
	1);
INSERT INTO O_TPARM
	VALUES (1048611,
	1048607,
	'en2',
	524307,
	1);
INSERT INTO O_TPARM
	VALUES (1048612,
	1048607,
	'u2',
	524306,
	1);
INSERT INTO O_TPARM
	VALUES (1048613,
	1048607,
	'b3',
	524290,
	1);
INSERT INTO O_TPARM
	VALUES (1048614,
	1048607,
	'tr3',
	524304,
	1);
INSERT INTO O_TPARM
	VALUES (1048615,
	1048607,
	'i3',
	524291,
	1);
INSERT INTO O_TPARM
	VALUES (1048616,
	1048607,
	'r3',
	524292,
	1);
INSERT INTO O_TPARM
	VALUES (1048617,
	1048607,
	's3',
	524293,
	1);
INSERT INTO O_TPARM
	VALUES (1048618,
	1048607,
	'en3',
	524307,
	1);
INSERT INTO O_TPARM
	VALUES (1048619,
	1048607,
	'u3',
	524306,
	1);
INSERT INTO O_TPARM
	VALUES (1048620,
	1048607,
	'b4',
	524290,
	1);
INSERT INTO O_TPARM
	VALUES (1048621,
	1048607,
	'tr4',
	524304,
	1);
INSERT INTO O_TPARM
	VALUES (1048622,
	1048607,
	'i4',
	524291,
	1);
INSERT INTO O_TPARM
	VALUES (1048623,
	1048607,
	'r4',
	524292,
	1);
INSERT INTO O_TPARM
	VALUES (1048624,
	1048607,
	's4',
	524293,
	1);
INSERT INTO O_TPARM
	VALUES (1048625,
	1048607,
	'en4',
	524307,
	1);
INSERT INTO O_TPARM
	VALUES (1048626,
	1048607,
	'u4',
	524306,
	1);
INSERT INTO O_TFR
	VALUES (1048608,
	1048579,
	'mod_params4',
	'',
	524289,
	0,
	'//////////////////////////////
// Param Assign
//////////////////////////////

select any tc from instances of TC;
select any tc_4 from instances of TC4;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Functions
////////////////////////////
param.b = ::bool1( date1: ::date1( evt1:tc.e ) ) and EE::bool2(date2: tc.date3( evt3:tc.e ) );
param.tr = ::timer1( int1:TC::int4( real4:tc.r )      +    ::int1( real1: tc.real3( str3:tc.s ) ) );
param.i = ::int1(real1: ::real1(str1:tc.s)               +   EE::real2( str2: tc.str3( time3:tc.t ) ) );
param.r = ::real1(str1: EE::str2(time2:tc.t)          +    tc.str3( time3: TC::time4( id4:tc.ID ) ) );
param.s = ::str1( time1: EE::time2( id2: tc.ID ) )  +    tc.str3( time3: TC::time4( id4: tc.ID ) );
param.en = ::enum1(udt1:EE::udt2(bool2:tc.b)   +    tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
param.u = ::udt1(bool1: tc.bool3(date3:tc.d)    and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Bridges
////////////////////////////
param.b2 = EE::bool2(date2: ::date1(evt1:tc.e))  and EE::bool2(date2: tc.date3( evt3:tc.e ) );
param.tr2 =  EE::timer2(int2: TC::int4(real4:tc.r)     +   ::int1( real1: tc.real3( str3:tc.s ) ) );
param.i2 =  REE::int5(real5: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
param.r2 =  REE::real5(str5:  EE::str2(time2:tc.t)      +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
param.s2 =  EE::str2(time2: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
param.en2 = EE::enum2(udt2: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
param.u2 = EE::udt2(bool2: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Instance Operations
////////////////////////////
param.b3 = tc.bool3(date3: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
param.tr3 =  tc.timer3(int3:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
param.i3 =  tc.int3(real3: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
param.r3 =  tc_4.real6(str6: EE::str2(time2:tc.t)        +   tc_4.str6( time6: TC::time4( id4:tc.ID ) ) );
param.s3 =  tc_4.str6(time6: TC::time4(id4:tc.ID))       +  tc_4.str6( time6: TC::time4( id4: tc.ID ) );
param.en3 = tc.enum3(udt3: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
param.u3 = tc.udt3(bool3: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Class Operations
////////////////////////////
param.b4 = TC::bool4(date4: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
param.tr4 = TC::timer4(int4:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
param.i4 = TC4::int7(real7: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
param.r4 = TC4::real7(str7: EE::str2(time2:tc.t)        +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
param.s4 = TC::str4(time4:tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
param.en4 = TC::enum4(udt4:EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
param.u4 = TC::udt4(bool4: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Results
////////////////////////////
if ( param.b and param.b2 and param.b3 and param.b4 )
  LOG::LogSuccess( message:"Assign Param - Boolean" );
else
  LOG::LogFailure( message:"Assign Param - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:param.b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:param.b );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:param.b );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:param.b );
end if;

if ( ( param.tr ==  param.tr2 ) and (  param.tr3 ==  param.tr4 ) )
  LOG::LogSuccess( message:"Assign Param - Timer" );
else
  LOG::LogFailure( message:"Assign Param - Timer");
end if;

if ( ( param.i ==  param.i2 ) and (  param.i3 ==  param.i4 ) )
  LOG::LogSuccess( message:"Assign Param - Integer" );
else
  LOG::LogFailure( message:"Assign Param - Integer");
  LOG::LogInt( message:"Function Invocation", int: param.i );
  LOG::LogInt( message:"Bridge Invocation", int: param.i );
  LOG::LogInt( message:"Instance Operation Invocation", int: param.i );
  LOG::LogInt( message:"Class Operation Invocation", int: param.i );
end if;

if ( (  param.r ==  param.r2 ) and (  param.r3 ==  param.r4 ) )
  LOG::LogSuccess( message:"Assign Param - Real" );
else
  LOG::LogFailure( message:"Assign Param - Real");
  LOG::LogReal( message:"Function Invocation", real: param.r );
  LOG::LogReal( message:"Bridge Invocation", real: param.r );
  LOG::LogReal( message:"Instance Operation Invocation", real: param.r );
  LOG::LogReal( message:"Class Operation Invocation", real: param.r );
end if;

if ( (  param.s ==  param.s2 ) and (  param.s3 ==  param.s4 ) )
  LOG::LogSuccess( message:"Assign Param - String" );
else
  LOG::LogFailure( message:"Assign Param - String");
  LOG::LogString( message:"Function Invocation", str: param.s );
  LOG::LogString( message:"Bridge Invocation", str: param.s );
  LOG::LogString( message:"Instance Operation Invocation", str: param.s );
  LOG::LogString( message:"Class Operation Invocation", str: param.s );
end if;

if ( (  param.en ==  param.en2 ) and (  param.en3 ==  param.en4 ) )
  LOG::LogSuccess( message:"Assign Param - Enumeration" );
else
  LOG::LogFailure( message:"Assign Param - Enumeration");
end if;

if ( (  param.u ==  param.u2 ) and (  param.u3 ==  param.u4 ) )
  LOG::LogSuccess( message:"Assign Param - User Data Type" );
else
  LOG::LogFailure( message:"Assign Param - User Data Type");
  LOG::LogInt( message:"Function Invocation", int: param.u );
  LOG::LogInt( message:"Bridge Invocation", int: param.u );
  LOG::LogInt( message:"Instance Operation Invocation", int: param.u );
  LOG::LogInt( message:"Class Operation Invocation", int: param.u );
end if;',
	1);
INSERT INTO O_TPARM
	VALUES (1048627,
	1048608,
	'b',
	524290,
	1);
INSERT INTO O_TPARM
	VALUES (1048628,
	1048608,
	'tr',
	524304,
	1);
INSERT INTO O_TPARM
	VALUES (1048629,
	1048608,
	'i',
	524291,
	1);
INSERT INTO O_TPARM
	VALUES (1048630,
	1048608,
	'r',
	524292,
	1);
INSERT INTO O_TPARM
	VALUES (1048631,
	1048608,
	's',
	524293,
	1);
INSERT INTO O_TPARM
	VALUES (1048632,
	1048608,
	'en',
	524307,
	1);
INSERT INTO O_TPARM
	VALUES (1048633,
	1048608,
	'u',
	524306,
	1);
INSERT INTO O_TPARM
	VALUES (1048634,
	1048608,
	'b2',
	524290,
	1);
INSERT INTO O_TPARM
	VALUES (1048635,
	1048608,
	'tr2',
	524304,
	1);
INSERT INTO O_TPARM
	VALUES (1048636,
	1048608,
	'i2',
	524291,
	1);
INSERT INTO O_TPARM
	VALUES (1048637,
	1048608,
	'r2',
	524292,
	1);
INSERT INTO O_TPARM
	VALUES (1048638,
	1048608,
	's2',
	524293,
	1);
INSERT INTO O_TPARM
	VALUES (1048639,
	1048608,
	'en2',
	524307,
	1);
INSERT INTO O_TPARM
	VALUES (1048640,
	1048608,
	'u2',
	524306,
	1);
INSERT INTO O_TPARM
	VALUES (1048641,
	1048608,
	'b3',
	524290,
	1);
INSERT INTO O_TPARM
	VALUES (1048642,
	1048608,
	'tr3',
	524304,
	1);
INSERT INTO O_TPARM
	VALUES (1048643,
	1048608,
	'i3',
	524291,
	1);
INSERT INTO O_TPARM
	VALUES (1048644,
	1048608,
	'r3',
	524292,
	1);
INSERT INTO O_TPARM
	VALUES (1048645,
	1048608,
	's3',
	524293,
	1);
INSERT INTO O_TPARM
	VALUES (1048646,
	1048608,
	'en3',
	524307,
	1);
INSERT INTO O_TPARM
	VALUES (1048647,
	1048608,
	'u3',
	524306,
	1);
INSERT INTO O_TPARM
	VALUES (1048648,
	1048608,
	'b4',
	524290,
	1);
INSERT INTO O_TPARM
	VALUES (1048649,
	1048608,
	'tr4',
	524304,
	1);
INSERT INTO O_TPARM
	VALUES (1048650,
	1048608,
	'i4',
	524291,
	1);
INSERT INTO O_TPARM
	VALUES (1048651,
	1048608,
	'r4',
	524292,
	1);
INSERT INTO O_TPARM
	VALUES (1048652,
	1048608,
	's4',
	524293,
	1);
INSERT INTO O_TPARM
	VALUES (1048653,
	1048608,
	'en4',
	524307,
	1);
INSERT INTO O_TPARM
	VALUES (1048654,
	1048608,
	'u4',
	524306,
	1);
INSERT INTO O_TFR
	VALUES (1048609,
	1048579,
	'real_invoke3',
	'',
	524289,
	1,
	'select any tc from instances of TC;

select any tc7 from instances of TC4;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Bridges
////////////////////////////
b5 = REE::bool5(date5: ::date1(evt1:tc.e))  and REE::bool5(date5: tc.date3( evt3:tc.e ) );
i5 =  REE::int5(real5: ::real1(str1:tc.s)            +  EE::real2( str2: REE::str5( time5:tc.t ) ) );
r5 =  REE::real5(str5:  REE::str5(time5:tc.t)      +   tc7.str6( time6: TC::time4( id4:tc.ID ) ) );
s5 =  REE::str5(time5: tc.time3(id3:tc.ID))       +  tc7.str6( time6: TC::time4( id4: tc.ID ) );
en5 = REE::enum5(udt5: REE::udt5(bool5:tc.b) +  tc7.udt6( bool6: TC4::bool7( date7:tc.d ) ) );
u5 = REE::udt5(bool5: tc7.bool6(date6:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Instance Operations
////////////////////////////
b6 = tc7.bool6(date6: ::date1(evt1:tc.e))   and REE::bool5(date5: TC::date4( evt4:tc.e ) );
i6 =  tc7.int6(real6: ::real1(str1:tc.s)            +  REE::real5( str5: tc7.str6( time6:tc.t ) ) );
r6 =  tc7.real6(str6: REE::str5(time5:tc.t)        +   tc7.str6( time6: TC::time4( id4:tc.ID ) ) );
s6 =  tc7.str6(time6: TC::time4(id4:tc.ID))       +  tc7.str6( time6: TC::time4( id4: tc.ID ) );
en6 = tc7.enum6(udt6: REE::udt5(bool5:tc.b) +  tc7.udt6( bool6: REE::bool5( date5:tc.d ) ) );
u6 = tc7.udt6(bool6: tc7.bool6(date6:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Class Operations
////////////////////////////
b7 = TC4::bool7(date7: ::date1(evt1:tc.e))   and REE::bool5(date5: tc.date3( evt3:tc.e ) );
i7 = TC4::int7(real7: ::real1(str1:tc.s)            +  REE::real5( str5: tc7.str6( time6:tc.t ) ) );
r7 = TC4::real7(str7: REE::str5(time5:tc.t)        +   tc7.str6( time6: TC::time4( id4:tc.ID ) ) );
s7 = TC4::str7(time7:tc.time3(id3:tc.ID))       +  TC::str4( time4: TC::time4( id4: tc.ID ) );
en7 = TC4::enum7(udt7:REE::udt5(bool5:tc.b) +  tc7.udt6( bool6: TC4::bool7( date7:tc.d ) ) );
u7 = TC4::udt7(bool7: tc7.bool6(date6:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Results
////////////////////////////
if ( b5 and b6 and b7 )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Boolean" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Boolean");
  LOG::LogBoolean( message:"Bridge Invocation", bool:b5 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b6 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b7 );
end if;

if ( ( i5 == i6 ) and ( i6 == i7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Integer" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Integer");
  LOG::LogInt( message:"Bridge Invocation", int:i5 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i6 );
  LOG::LogInt( message:"Class Operation Invocation", int:i7 );
end if;

if ( ( r5 == r6 ) and ( r6 == r7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Real" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Real");
  LOG::LogReal( message:"Bridge Invocation", real:r5 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r6 );
  LOG::LogReal( message:"Class Operation Invocation", real:r7 );
end if;

if ( ( s5 == s6 ) and ( s6 == s7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - String" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - String");
  LOG::LogString( message:"Bridge Invocation", str:s5 );
  LOG::LogString( message:"Instance Operation Invocation", str:s6 );
  LOG::LogString( message:"Class Operation Invocation", str:s7 );
end if;

if ( ( en5 == en6 ) and ( en6 == en7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Enumeration" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Enumeration");
end if;

if ( ( u5 == u6 ) and ( u6 == u7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - User Data Type" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - User Data Type");
  LOG::LogInt( message:"Bridge Invocation", int:u5 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u6 );
  LOG::LogInt( message:"Class Operation Invocation", int:u7 );
end if;',
	1);
INSERT INTO O_TFR
	VALUES (1048610,
	1048579,
	'real_invoke4',
	'',
	524289,
	0,
	'select any tc from instances of TC;

select any tc7 from instances of TC4;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Bridges
////////////////////////////
b5 = REE::bool5(date5: ::date1(evt1:tc.e))  and REE::bool5(date5: tc.date3( evt3:tc.e ) );
i5 =  REE::int5(real5: ::real1(str1:tc.s)            +  EE::real2( str2: REE::str5( time5:tc.t ) ) );
r5 =  REE::real5(str5:  REE::str5(time5:tc.t)      +   tc7.str6( time6: TC::time4( id4:tc.ID ) ) );
s5 =  REE::str5(time5: tc.time3(id3:tc.ID))       +  tc7.str6( time6: TC::time4( id4: tc.ID ) );
en5 = REE::enum5(udt5: REE::udt5(bool5:tc.b) +  tc7.udt6( bool6: TC4::bool7( date7:tc.d ) ) );
u5 = REE::udt5(bool5: tc7.bool6(date6:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Instance Operations
////////////////////////////
b6 = tc7.bool6(date6: ::date1(evt1:tc.e))   and REE::bool5(date5: TC::date4( evt4:tc.e ) );
i6 =  tc7.int6(real6: ::real1(str1:tc.s)            +  REE::real5( str5: tc7.str6( time6:tc.t ) ) );
r6 =  tc7.real6(str6: REE::str5(time5:tc.t)        +   tc7.str6( time6: TC::time4( id4:tc.ID ) ) );
s6 =  tc7.str6(time6: TC::time4(id4:tc.ID))       +  tc7.str6( time6: TC::time4( id4: tc.ID ) );
en6 = tc7.enum6(udt6: REE::udt5(bool5:tc.b) +  tc7.udt6( bool6: REE::bool5( date5:tc.d ) ) );
u6 = tc7.udt6(bool6: tc7.bool6(date6:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Class Operations
////////////////////////////
b7 = TC4::bool7(date7: ::date1(evt1:tc.e))   and REE::bool5(date5: tc.date3( evt3:tc.e ) );
i7 = TC4::int7(real7: ::real1(str1:tc.s)            +  REE::real5( str5: tc7.str6( time6:tc.t ) ) );
r7 = TC4::real7(str7: REE::str5(time5:tc.t)        +   tc7.str6( time6: TC::time4( id4:tc.ID ) ) );
s7 = TC4::str7(time7:tc.time3(id3:tc.ID))       +  TC::str4( time4: TC::time4( id4: tc.ID ) );
en7 = TC4::enum7(udt7:REE::udt5(bool5:tc.b) +  tc7.udt6( bool6: TC4::bool7( date7:tc.d ) ) );
u7 = TC4::udt7(bool7: tc7.bool6(date6:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Results
////////////////////////////
if ( b5 and b6 and b7 )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Boolean" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Boolean");
  LOG::LogBoolean( message:"Bridge Invocation", bool:b5 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b6 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b7 );
end if;

if ( ( i5 == i6 ) and ( i6 == i7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Integer" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Integer");
  LOG::LogInt( message:"Bridge Invocation", int:i5 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i6 );
  LOG::LogInt( message:"Class Operation Invocation", int:i7 );
end if;

if ( ( r5 == r6 ) and ( r6 == r7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Real" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Real");
  LOG::LogReal( message:"Bridge Invocation", real:r5 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r6 );
  LOG::LogReal( message:"Class Operation Invocation", real:r7 );
end if;

if ( ( s5 == s6 ) and ( s6 == s7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - String" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - String");
  LOG::LogString( message:"Bridge Invocation", str:s5 );
  LOG::LogString( message:"Instance Operation Invocation", str:s6 );
  LOG::LogString( message:"Class Operation Invocation", str:s7 );
end if;

if ( ( en5 == en6 ) and ( en6 == en7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Enumeration" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Enumeration");
end if;

if ( ( u5 == u6 ) and ( u6 == u7 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - User Data Type" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - User Data Type");
  LOG::LogInt( message:"Bridge Invocation", int:u5 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u6 );
  LOG::LogInt( message:"Class Operation Invocation", int:u7 );
end if;',
	1);
INSERT INTO O_TFR
	VALUES (1048611,
	1048579,
	'ret6',
	'',
	524290,
	1,
	'select any tc from instances of TC;
select any tc_4 from instances of TC4;

return tc_4.bool6( date6: EE::date2( evt2: TC::evt4( timer4: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc_4.int6( real6: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc_4.bool6( date6:tc.d ) ) and TC::bool4( date4:tc.d ) ) );',
	1);
INSERT INTO O_TFR
	VALUES (1048612,
	1048579,
	'ret7',
	'',
	524290,
	0,
	'select any tc from instances of TC;

return TC4::bool7( date7: EE::date2( evt2: tc.evt3( timer3: TC::timer4( int4: ::int1( real1: tc.r + EE::real2( str2: tc.s ) ) + tc.int3( real3: TC::real4( str4: tc.s ) ) ) ) ) ) and ( ( EE::bool2( date2:tc.d ) or ( tc.bool3( date3:tc.d ) ) and TC::bool4( date4:tc.d ) ) );',
	1);
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
	'b',
	'',
	'',
	'b',
	0,
	524290);
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
	'd',
	'',
	'',
	'd',
	0,
	524302);
INSERT INTO O_NBATTR
	VALUES (1048583,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048583,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048583,
	1048579,
	1048582,
	'en',
	'',
	'',
	'en',
	0,
	524307);
INSERT INTO O_NBATTR
	VALUES (1048584,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048584,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048584,
	1048579,
	1048583,
	'e',
	'',
	'',
	'e',
	0,
	524299);
INSERT INTO O_NBATTR
	VALUES (1048585,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048585,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048585,
	1048579,
	1048584,
	'tr',
	'',
	'',
	'tr',
	0,
	524304);
INSERT INTO O_NBATTR
	VALUES (1048586,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048586,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048586,
	1048579,
	1048585,
	'i',
	'',
	'',
	'i',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048587,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048587,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048587,
	1048579,
	1048586,
	'r',
	'',
	'',
	'r',
	0,
	524292);
INSERT INTO O_NBATTR
	VALUES (1048588,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048588,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048588,
	1048579,
	1048587,
	's',
	'',
	'',
	's',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048589,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048589,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048589,
	1048579,
	1048588,
	't',
	'',
	'',
	't',
	0,
	524303);
INSERT INTO O_NBATTR
	VALUES (1048590,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048590,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048590,
	1048579,
	1048589,
	'u',
	'',
	'',
	'u',
	0,
	524306);
INSERT INTO O_DBATTR
	VALUES (1048591,
	1048579,
	'select any tc from instances of TC;

// Order is Function, Bridge, Instance Operation, Class Operation

////////////////////////////
// Functions
////////////////////////////
b = ::bool1( date1: ::date1( evt1:tc.e ) ) and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tr = ::timer1( int1:TC::int4( real4:tc.r )      +    ::int1( real1: tc.real3( str3:tc.s ) ) );
i = ::int1(real1: ::real1(str1:tc.s)               +   EE::real2( str2: tc.str3( time3:tc.t ) ) );
r = ::real1(str1: EE::str2(time2:tc.t)          +    tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s = ::str1( time1: EE::time2( id2: tc.ID ) )  +    tc.str3( time3: TC::time4( id4: tc.ID ) );
en = ::enum1(udt1:EE::udt2(bool2:tc.b)   +    tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u = ::udt1(bool1: tc.bool3(date3:tc.d)    and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Bridges
////////////////////////////
b2 = EE::bool2(date2: ::date1(evt1:tc.e))  and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tr2 =  EE::timer2(int2: TC::int4(real4:tc.r)     +   ::int1( real1: tc.real3( str3:tc.s ) ) );
i2 =  EE::int2(real2: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
r2 =  EE::real2(str2:  EE::str2(time2:tc.t)      +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s2 =  EE::str2(time2: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
en2 = EE::enum2(udt2: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u2 = EE::udt2(bool2: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Instance Operations
////////////////////////////
b3 = tc.bool3(date3: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tr3 =  tc.timer3(int3:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
i3 =  tc.int3(real3: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
r3 =  tc.real3(str3: EE::str2(time2:tc.t)        +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s3 =  tc.str3(time3: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
en3 = tc.enum3(udt3: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u3 = tc.udt3(bool3: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Class Operations
////////////////////////////
b4 = TC::bool4(date4: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
tr4 = TC::timer4(int4:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
i4 = TC::int4(real4: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
r4 = TC::real4(str4: EE::str2(time2:tc.t)        +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
s4 = TC::str4(time4:tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
en4 = TC::enum4(udt4:EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
u4 = TC::udt4(bool4: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

////////////////////////////
// Results
////////////////////////////
if ( b and b2 and b3 and b4 )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Boolean" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Boolean");
  LOG::LogBoolean( message:"Function Invocation", bool:b );
  LOG::LogBoolean( message:"Bridge Invocation", bool:b2 );
  LOG::LogBoolean( message:"Instance Operation Invocation", bool:b3 );
  LOG::LogBoolean( message:"Class Operation Invocation", bool:b4 );
end if;

if ( ( tr == tr2 ) and ( tr3 == tr4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Timer" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Timer");
end if;

if ( ( i == i2 ) and ( i3 == i4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Integer" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Integer");
  LOG::LogInt( message:"Function Invocation", int:i );
  LOG::LogInt( message:"Bridge Invocation", int:i2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:i3 );
  LOG::LogInt( message:"Class Operation Invocation", int:i4 );
end if;

if ( ( r == r2 ) and ( r3 == r4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Real" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Real");
  LOG::LogReal( message:"Function Invocation", real:r );
  LOG::LogReal( message:"Bridge Invocation", real:r2 );
  LOG::LogReal( message:"Instance Operation Invocation", real:r3 );
  LOG::LogReal( message:"Class Operation Invocation", real:r4 );
end if;

if ( ( s == s2 ) and ( s3 == s4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - String" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - String");
  LOG::LogString( message:"Function Invocation", str:s );
  LOG::LogString( message:"Bridge Invocation", str:s2 );
  LOG::LogString( message:"Instance Operation Invocation", str:s3 );
  LOG::LogString( message:"Class Operation Invocation", str:s4 );
end if;

if ( ( en == en2 ) and ( en3 == en4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - Enumeration" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - Enumeration");
end if;

if ( ( u == u2 ) and ( u3 == u4 ) )
  LOG::LogSuccess( message:"Multiple Invocation with Expression - User Data Type" );
else
  LOG::LogFailure( message:"Multiple Invocation with Expression - User Data Type");
  LOG::LogInt( message:"Function Invocation", int:u );
  LOG::LogInt( message:"Bridge Invocation", int:u2 );
  LOG::LogInt( message:"Instance Operation Invocation", int:u3 );
  LOG::LogInt( message:"Class Operation Invocation", int:u4 );
end if;

self.mda = true;',
	1);
INSERT INTO O_BATTR
	VALUES (1048591,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048591,
	1048579,
	1048590,
	'mda',
	'',
	'',
	'mda',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (1048592,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048592,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048592,
	1048579,
	1048591,
	'ID',
	'',
	'',
	'ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (1048579,
	1048580,
	0,
	1048594,
	1048577,
	1048577,
	1048578,
	1048593,
	1048579,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048593,
	1048579,
	1048594,
	1048580,
	1);
INSERT INTO O_ATTR
	VALUES (1048593,
	1048579,
	1048592,
	'ID2',
	'',
	'',
	'ID2',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048579);
INSERT INTO O_OIDA
	VALUES (1048592,
	1048579,
	0);
INSERT INTO O_RTIDA
	VALUES (1048592,
	1048579,
	0,
	1048578,
	1048580);
INSERT INTO O_OBJ
	VALUES (1048580,
	'Test Class Two',
	4,
	'TC2',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048594,
	1048580);
INSERT INTO O_BATTR
	VALUES (1048594,
	1048580);
INSERT INTO O_ATTR
	VALUES (1048594,
	1048580,
	0,
	'ID2',
	'',
	'',
	'ID2',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048595,
	1048580);
INSERT INTO O_BATTR
	VALUES (1048595,
	1048580);
INSERT INTO O_ATTR
	VALUES (1048595,
	1048580,
	1048594,
	'times_around',
	'',
	'',
	'times_around',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	1048580);
INSERT INTO O_OIDA
	VALUES (1048594,
	1048580,
	0);
INSERT INTO O_RTIDA
	VALUES (1048594,
	1048580,
	0,
	1048577,
	1048578);
INSERT INTO SM_ASM
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
	'bool5',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (2621442,
	2621445,
	'udt5',
	'',
	524306);
INSERT INTO SM_EVTDI
	VALUES (2621443,
	2621445,
	'timer5',
	'',
	524304);
INSERT INTO SM_EVTDI
	VALUES (2621444,
	2621445,
	'time5',
	'',
	524303);
INSERT INTO SM_EVTDI
	VALUES (2621445,
	2621445,
	'str5',
	'',
	524293);
INSERT INTO SM_EVTDI
	VALUES (2621446,
	2621445,
	'real5',
	'',
	524292);
INSERT INTO SM_EVTDI
	VALUES (2621447,
	2621445,
	'int5',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (2621448,
	2621445,
	'id5',
	'',
	524294);
INSERT INTO SM_EVTDI
	VALUES (2621449,
	2621445,
	'evt5',
	'',
	524299);
INSERT INTO SM_EVTDI
	VALUES (2621450,
	2621445,
	'enum5',
	'',
	524307);
INSERT INTO SM_EVTDI
	VALUES (2621451,
	2621445,
	'date5',
	'',
	524302);
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
	4,
	'',
	0,
	'',
	'TC2_A4',
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
	14,
	'',
	0,
	'',
	'TC2_A14',
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
	13,
	'',
	0,
	'',
	'TC2_A13',
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
	12,
	'',
	0,
	'',
	'TC2_A12',
	'');
INSERT INTO SM_LEVT
	VALUES (2621445,
	2621445,
	0);
INSERT INTO SM_SEVT
	VALUES (2621445,
	2621445,
	0);
INSERT INTO SM_EVT
	VALUES (2621445,
	2621445,
	0,
	11,
	'',
	0,
	'',
	'TC2_A11',
	'');
INSERT INTO SM_LEVT
	VALUES (2621446,
	2621445,
	0);
INSERT INTO SM_SEVT
	VALUES (2621446,
	2621445,
	0);
INSERT INTO SM_EVT
	VALUES (2621446,
	2621445,
	0,
	10,
	'',
	0,
	'',
	'TC2_A10',
	'');
INSERT INTO SM_LEVT
	VALUES (2621447,
	2621445,
	0);
INSERT INTO SM_SEVT
	VALUES (2621447,
	2621445,
	0);
INSERT INTO SM_EVT
	VALUES (2621447,
	2621445,
	0,
	9,
	'',
	0,
	'',
	'TC2_A9',
	'');
INSERT INTO SM_LEVT
	VALUES (2621448,
	2621445,
	0);
INSERT INTO SM_SEVT
	VALUES (2621448,
	2621445,
	0);
INSERT INTO SM_EVT
	VALUES (2621448,
	2621445,
	0,
	8,
	'',
	0,
	'',
	'TC2_A8',
	'');
INSERT INTO SM_LEVT
	VALUES (2621449,
	2621445,
	0);
INSERT INTO SM_SEVT
	VALUES (2621449,
	2621445,
	0);
INSERT INTO SM_EVT
	VALUES (2621449,
	2621445,
	0,
	7,
	'',
	0,
	'',
	'TC2_A7',
	'');
INSERT INTO SM_LEVT
	VALUES (2621450,
	2621445,
	0);
INSERT INTO SM_SEVT
	VALUES (2621450,
	2621445,
	0);
INSERT INTO SM_EVT
	VALUES (2621450,
	2621445,
	0,
	6,
	'',
	0,
	'',
	'TC2_A6',
	'');
INSERT INTO SM_LEVT
	VALUES (2621451,
	2621445,
	0);
INSERT INTO SM_SEVT
	VALUES (2621451,
	2621445,
	0);
INSERT INTO SM_EVT
	VALUES (2621451,
	2621445,
	0,
	5,
	'',
	0,
	'',
	'TC2_A5',
	'');
INSERT INTO SM_STATE
	VALUES (2621441,
	2621445,
	0,
	'Integer',
	6,
	0);
INSERT INTO SM_EIGN
	VALUES (2621441,
	2621441,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621441,
	2621445,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
	VALUES (2621441,
	2621443,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621443,
	2621445,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
	VALUES (2621441,
	2621445,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621445,
	2621445,
	0);
INSERT INTO SM_SEME
	VALUES (2621441,
	2621446,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621441,
	2621447,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621447,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621441,
	2621448,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621448,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621441,
	2621449,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621449,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621441,
	2621450,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621450,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621441,
	2621451,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621441,
	2621451,
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
	'LOG::LogSuccess(message:"Assigner Event - Integer");',
	'');
INSERT INTO SM_STATE
	VALUES (2621442,
	2621445,
	0,
	'Boolean',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (2621442,
	2621441,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621442,
	2621442,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621442,
	2621442,
	2621445,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
	VALUES (2621442,
	2621445,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621442,
	2621445,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621442,
	2621446,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621442,
	2621446,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621442,
	2621447,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621442,
	2621447,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621442,
	2621448,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621442,
	2621448,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621442,
	2621449,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621442,
	2621449,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621442,
	2621450,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621442,
	2621450,
	2621445,
	0);
INSERT INTO SM_SEME
	VALUES (2621442,
	2621451,
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
	'LOG::LogSuccess(message:"Assigner Event - Boolean");',
	'');
INSERT INTO SM_STATE
	VALUES (2621443,
	2621445,
	0,
	'Date',
	2,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
	VALUES (2621443,
	2621444,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621443,
	2621444,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621443,
	2621445,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621443,
	2621445,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621443,
	2621446,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621443,
	2621446,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621443,
	2621447,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621443,
	2621447,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621443,
	2621448,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621443,
	2621448,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621443,
	2621449,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621443,
	2621449,
	2621445,
	0);
INSERT INTO SM_SEME
	VALUES (2621443,
	2621450,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621443,
	2621451,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621443,
	2621451,
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
	'LOG::LogSuccess(message:"Assigner Event - Date");',
	'');
INSERT INTO SM_STATE
	VALUES (2621444,
	2621445,
	0,
	'Enumeration',
	3,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
	VALUES (2621444,
	2621445,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621444,
	2621445,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621444,
	2621446,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621444,
	2621446,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621444,
	2621447,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621444,
	2621447,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621444,
	2621448,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621444,
	2621448,
	2621445,
	0);
INSERT INTO SM_SEME
	VALUES (2621444,
	2621449,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621444,
	2621450,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621444,
	2621450,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621444,
	2621451,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621444,
	2621451,
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
	'LOG::LogSuccess(message:"Assigner Event - Enumeration");',
	'');
INSERT INTO SM_STATE
	VALUES (2621445,
	2621445,
	0,
	'Event',
	4,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
	VALUES (2621445,
	2621443,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621445,
	2621443,
	2621445,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
	VALUES (2621445,
	2621445,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621445,
	2621445,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621445,
	2621446,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621445,
	2621446,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621445,
	2621447,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621445,
	2621447,
	2621445,
	0);
INSERT INTO SM_SEME
	VALUES (2621445,
	2621448,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621445,
	2621449,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621445,
	2621449,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621445,
	2621450,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621445,
	2621450,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621445,
	2621451,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621445,
	2621451,
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
	'LOG::LogSuccess(message:"Assigner Event - Event");',
	'');
INSERT INTO SM_STATE
	VALUES (2621446,
	2621445,
	0,
	'Unique ID',
	5,
	0);
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
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
INSERT INTO SM_EIGN
	VALUES (2621446,
	2621444,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621446,
	2621444,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621446,
	2621445,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621446,
	2621445,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621446,
	2621446,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621446,
	2621446,
	2621445,
	0);
INSERT INTO SM_SEME
	VALUES (2621446,
	2621447,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621446,
	2621448,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621446,
	2621448,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621446,
	2621449,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621446,
	2621449,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621446,
	2621450,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621446,
	2621450,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621446,
	2621451,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621446,
	2621451,
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
	'LOG::LogSuccess(message:"Assigner Event - Unique ID");',
	'');
INSERT INTO SM_STATE
	VALUES (2621447,
	2621445,
	0,
	'String',
	8,
	0);
INSERT INTO SM_EIGN
	VALUES (2621447,
	2621441,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621447,
	2621441,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621447,
	2621442,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621447,
	2621442,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621447,
	2621443,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621447,
	2621443,
	2621445,
	0);
INSERT INTO SM_SEME
	VALUES (2621447,
	2621444,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621447,
	2621445,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621447,
	2621445,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621447,
	2621446,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621447,
	2621446,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621447,
	2621447,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621447,
	2621447,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621447,
	2621448,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621447,
	2621448,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621447,
	2621449,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621447,
	2621449,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621447,
	2621450,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621447,
	2621450,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621447,
	2621451,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621447,
	2621451,
	2621445,
	0);
INSERT INTO SM_MOAH
	VALUES (2621447,
	2621445,
	2621447);
INSERT INTO SM_AH
	VALUES (2621447,
	2621445);
INSERT INTO SM_ACT
	VALUES (2621447,
	2621445,
	1,
	'LOG::LogSuccess(message:"Assigner Event - String");',
	'');
INSERT INTO SM_STATE
	VALUES (2621448,
	2621445,
	0,
	'Real',
	7,
	0);
INSERT INTO SM_EIGN
	VALUES (2621448,
	2621441,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621448,
	2621441,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621448,
	2621442,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621448,
	2621442,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621448,
	2621443,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621448,
	2621443,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621448,
	2621444,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621448,
	2621444,
	2621445,
	0);
INSERT INTO SM_SEME
	VALUES (2621448,
	2621445,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621448,
	2621446,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621448,
	2621446,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621448,
	2621447,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621448,
	2621447,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621448,
	2621448,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621448,
	2621448,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621448,
	2621449,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621448,
	2621449,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621448,
	2621450,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621448,
	2621450,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621448,
	2621451,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621448,
	2621451,
	2621445,
	0);
INSERT INTO SM_MOAH
	VALUES (2621448,
	2621445,
	2621448);
INSERT INTO SM_AH
	VALUES (2621448,
	2621445);
INSERT INTO SM_ACT
	VALUES (2621448,
	2621445,
	1,
	'LOG::LogSuccess(message:"Assigner Event - Real");',
	'');
INSERT INTO SM_STATE
	VALUES (2621449,
	2621445,
	0,
	'Timer',
	10,
	0);
INSERT INTO SM_EIGN
	VALUES (2621449,
	2621441,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621449,
	2621441,
	2621445,
	0);
INSERT INTO SM_SEME
	VALUES (2621449,
	2621442,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621449,
	2621443,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621449,
	2621443,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621449,
	2621444,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621449,
	2621444,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621449,
	2621445,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621449,
	2621445,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621449,
	2621446,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621449,
	2621446,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621449,
	2621447,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621449,
	2621447,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621449,
	2621448,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621449,
	2621448,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621449,
	2621449,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621449,
	2621449,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621449,
	2621450,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621449,
	2621450,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621449,
	2621451,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621449,
	2621451,
	2621445,
	0);
INSERT INTO SM_MOAH
	VALUES (2621449,
	2621445,
	2621449);
INSERT INTO SM_AH
	VALUES (2621449,
	2621445);
INSERT INTO SM_ACT
	VALUES (2621449,
	2621445,
	1,
	'LOG::LogSuccess(message:"Assigner Event - Timer");',
	'');
INSERT INTO SM_STATE
	VALUES (2621450,
	2621445,
	0,
	'Timestamp',
	9,
	0);
INSERT INTO SM_EIGN
	VALUES (2621450,
	2621441,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621450,
	2621441,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621450,
	2621442,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621450,
	2621442,
	2621445,
	0);
INSERT INTO SM_SEME
	VALUES (2621450,
	2621443,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621450,
	2621444,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621450,
	2621444,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621450,
	2621445,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621450,
	2621445,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621450,
	2621446,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621450,
	2621446,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621450,
	2621447,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621450,
	2621447,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621450,
	2621448,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621450,
	2621448,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621450,
	2621449,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621450,
	2621449,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621450,
	2621450,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621450,
	2621450,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621450,
	2621451,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621450,
	2621451,
	2621445,
	0);
INSERT INTO SM_MOAH
	VALUES (2621450,
	2621445,
	2621450);
INSERT INTO SM_AH
	VALUES (2621450,
	2621445);
INSERT INTO SM_ACT
	VALUES (2621450,
	2621445,
	1,
	'LOG::LogSuccess(message:"Assigner Event - Timestamp");',
	'');
INSERT INTO SM_STATE
	VALUES (2621451,
	2621445,
	0,
	'User Data Type',
	11,
	0);
INSERT INTO SM_SEME
	VALUES (2621451,
	2621441,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621451,
	2621442,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621451,
	2621442,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621451,
	2621443,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621451,
	2621443,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621451,
	2621444,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621451,
	2621444,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621451,
	2621445,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621451,
	2621445,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621451,
	2621446,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621451,
	2621446,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621451,
	2621447,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621451,
	2621447,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621451,
	2621448,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621451,
	2621448,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621451,
	2621449,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621451,
	2621449,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621451,
	2621450,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621451,
	2621450,
	2621445,
	0);
INSERT INTO SM_EIGN
	VALUES (2621451,
	2621451,
	2621445,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (2621451,
	2621451,
	2621445,
	0);
INSERT INTO SM_MOAH
	VALUES (2621451,
	2621445,
	2621451);
INSERT INTO SM_AH
	VALUES (2621451,
	2621445);
INSERT INTO SM_ACT
	VALUES (2621451,
	2621445,
	1,
	'LOG::LogSuccess(message:"Assigner Event - User Data Type");

select any tc2 from instances of TC2;
tc2.times_around = tc2.times_around + 1;

if ( tc2.times_around == 3 )
  select any tc from instances of TC;

  // Order is Function, Bridge, Instance Operation, Class Operation

  ////////////////////////////
  // Functions
  ////////////////////////////
  b = ::bool1( date1: ::date1( evt1:tc.e ) ) and EE::bool2(date2: tc.date3( evt3:tc.e ) );
  tr = ::timer1( int1:TC::int4( real4:tc.r )      +    ::int1( real1: tc.real3( str3:tc.s ) ) );
  i = ::int1(real1: ::real1(str1:tc.s)               +   EE::real2( str2: tc.str3( time3:tc.t ) ) );
  r = ::real1(str1: EE::str2(time2:tc.t)          +    tc.str3( time3: TC::time4( id4:tc.ID ) ) );
  s = ::str1( time1: EE::time2( id2: tc.ID ) )  +    tc.str3( time3: TC::time4( id4: tc.ID ) );
  en = ::enum1(udt1:EE::udt2(bool2:tc.b)   +    tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
  u = ::udt1(bool1: tc.bool3(date3:tc.d)    and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

  ////////////////////////////
  // Bridges
  ////////////////////////////
  b2 = EE::bool2(date2: ::date1(evt1:tc.e))  and EE::bool2(date2: tc.date3( evt3:tc.e ) );
  tr2 =  EE::timer2(int2: TC::int4(real4:tc.r)     +   ::int1( real1: tc.real3( str3:tc.s ) ) );
  i2 =  EE::int2(real2: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
  r2 =  EE::real2(str2:  EE::str2(time2:tc.t)      +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
  s2 =  EE::str2(time2: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
  en2 = EE::enum2(udt2: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
  u2 = EE::udt2(bool2: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

  ////////////////////////////
  // Instance Operations
  ////////////////////////////
  b3 = tc.bool3(date3: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
  tr3 =  tc.timer3(int3:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
  i3 =  tc.int3(real3: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
  r3 =  tc.real3(str3: EE::str2(time2:tc.t)        +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
  s3 =  tc.str3(time3: tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
  en3 = tc.enum3(udt3: EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
  u3 = tc.udt3(bool3: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

  ////////////////////////////
  // Class Operations
  ////////////////////////////
  b4 = TC::bool4(date4: ::date1(evt1:tc.e))   and EE::bool2(date2: tc.date3( evt3:tc.e ) );
  tr4 = TC::timer4(int4:TC::int4(real4:tc.r)       +   ::int1( real1: tc.real3( str3:tc.s ) ) );
  i4 = TC::int4(real4: ::real1(str1:tc.s)            +  EE::real2( str2: tc.str3( time3:tc.t ) ) );
  r4 = TC::real4(str4: EE::str2(time2:tc.t)        +   tc.str3( time3: TC::time4( id4:tc.ID ) ) );
  s4 = TC::str4(time4:tc.time3(id3:tc.ID))       +  tc.str3( time3: TC::time4( id4: tc.ID ) );
  en4 = TC::enum4(udt4:EE::udt2(bool2:tc.b) +  tc.udt3( bool3: TC::bool4( date4:tc.d ) ) );
  u4 = TC::udt4(bool4: tc.bool3(date3:tc.d)   and  TC::bool4( date4: ::date1( evt1:tc.e ) ) );

  ////////////////////////////
  // Results
  ////////////////////////////
  if ( b and b2 and b3 and b4 )
    LOG::LogSuccess( message:"Multiple Invocation with Expression - Boolean" );
  else
    LOG::LogFailure( message:"Multiple Invocation with Expression - Boolean");
    LOG::LogBoolean( message:"Function Invocation", bool:b );
    LOG::LogBoolean( message:"Bridge Invocation", bool:b2 );
    LOG::LogBoolean( message:"Instance Operation Invocation", bool:b3 );
    LOG::LogBoolean( message:"Class Operation Invocation", bool:b4 );
  end if;
  
  if ( ( tr == tr2 ) and ( tr3 == tr4 ) )
    LOG::LogSuccess( message:"Multiple Invocation with Expression - Timer" );
  else
    LOG::LogFailure( message:"Multiple Invocation with Expression - Timer");
  end if;
  
  if ( ( i == i2 ) and ( i3 == i4 ) )
    LOG::LogSuccess( message:"Multiple Invocation with Expression - Integer" );
  else
    LOG::LogFailure( message:"Multiple Invocation with Expression - Integer");
    LOG::LogInt( message:"Function Invocation", int:i );
    LOG::LogInt( message:"Bridge Invocation", int:i2 );
    LOG::LogInt( message:"Instance Operation Invocation", int:i3 );
    LOG::LogInt( message:"Class Operation Invocation", int:i4 );
  end if;

  if ( ( r == r2 ) and ( r3 == r4 ) )
    LOG::LogSuccess( message:"Multiple Invocation with Expression - Real" );
  else
    LOG::LogFailure( message:"Multiple Invocation with Expression - Real");
    LOG::LogReal( message:"Function Invocation", real:r );
    LOG::LogReal( message:"Bridge Invocation", real:r2 );
    LOG::LogReal( message:"Instance Operation Invocation", real:r3 );
    LOG::LogReal( message:"Class Operation Invocation", real:r4 );
  end if;
  
  if ( ( s == s2 ) and ( s3 == s4 ) )
    LOG::LogSuccess( message:"Multiple Invocation with Expression - String" );
  else
    LOG::LogFailure( message:"Multiple Invocation with Expression - String");
    LOG::LogString( message:"Function Invocation", str:s );
    LOG::LogString( message:"Bridge Invocation", str:s2 );
    LOG::LogString( message:"Instance Operation Invocation", str:s3 );
    LOG::LogString( message:"Class Operation Invocation", str:s4 );
  end if;

  if ( ( en == en2 ) and ( en3 == en4 ) )
    LOG::LogSuccess( message:"Multiple Invocation with Expression - Enumeration" );
  else
    LOG::LogFailure( message:"Multiple Invocation with Expression - Enumeration");
  end if;

  if ( ( u == u2 ) and ( u3 == u4 ) )
    LOG::LogSuccess( message:"Multiple Invocation with Expression - User Data Type" );
  else
    LOG::LogFailure( message:"Multiple Invocation with Expression - User Data Type");
    LOG::LogInt( message:"Function Invocation", int:u );
    LOG::LogInt( message:"Bridge Invocation", int:u2 );
    LOG::LogInt( message:"Instance Operation Invocation", int:u3 );
    LOG::LogInt( message:"Class Operation Invocation", int:u4 );
  end if;
  
  ////////////////////////////  
  // Realized
  ////////////////////////////
  select any tc7 from instances of TC4;

  // Order is Function, Bridge, Instance Operation, Class Operation

  ////////////////////////////
  // Bridges
  ////////////////////////////
  b5 = REE::bool5(date5: ::date1(evt1:tc.e))  and REE::bool5(date5: tc.date3( evt3:tc.e ) );
  i5 =  REE::int5(real5: ::real1(str1:tc.s)            +  EE::real2( str2: REE::str5( time5:tc.t ) ) );
  r5 =  REE::real5(str5:  REE::str5(time5:tc.t)      +   tc7.str6( time6: TC::time4( id4:tc.ID ) ) );
  s5 =  REE::str5(time5: tc.time3(id3:tc.ID))       +  tc7.str6( time6: TC::time4( id4: tc.ID ) );
  en5 = REE::enum5(udt5: REE::udt5(bool5:tc.b) +  tc7.udt6( bool6: TC4::bool7( date7:tc.d ) ) );
  u5 = REE::udt5(bool5: tc7.bool6(date6:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

  ////////////////////////////
  // Instance Operations
  ////////////////////////////
  b6 = tc7.bool6(date6: ::date1(evt1:tc.e))   and REE::bool5(date5: TC::date4( evt4:tc.e ) );
  i6 =  tc7.int6(real6: ::real1(str1:tc.s)            +  REE::real5( str5: tc7.str6( time6:tc.t ) ) );
  r6 =  tc7.real6(str6: REE::str5(time5:tc.t)        +   tc7.str6( time6: TC::time4( id4:tc.ID ) ) );
  s6 =  tc7.str6(time6: TC::time4(id4:tc.ID))       +  tc7.str6( time6: TC::time4( id4: tc.ID ) );
  en6 = tc7.enum6(udt6: REE::udt5(bool5:tc.b) +  tc7.udt6( bool6: REE::bool5( date5:tc.d ) ) );
  u6 = tc7.udt6(bool6: tc7.bool6(date6:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );
  
  ////////////////////////////
  // Class Operations
  ////////////////////////////
  b7 = TC4::bool7(date7: ::date1(evt1:tc.e))   and REE::bool5(date5: tc.date3( evt3:tc.e ) );
  i7 = TC4::int7(real7: ::real1(str1:tc.s)            +  REE::real5( str5: tc7.str6( time6:tc.t ) ) );
  r7 = TC4::real7(str7: REE::str5(time5:tc.t)        +   tc7.str6( time6: TC::time4( id4:tc.ID ) ) );
  s7 = TC4::str7(time7:tc.time3(id3:tc.ID))       +  TC::str4( time4: TC::time4( id4: tc.ID ) );
  en7 = TC4::enum7(udt7:REE::udt5(bool5:tc.b) +  tc7.udt6( bool6: TC4::bool7( date7:tc.d ) ) );
  u7 = TC4::udt7(bool7: tc7.bool6(date6:tc.d)   and  TC4::bool7( date7: ::date1( evt1:tc.e ) ) );

  ////////////////////////////
  // Results
  ////////////////////////////
  if ( b5 and b6 and b7 )
    LOG::LogSuccess( message:"Multiple Invocation with Expression - Boolean" );
  else
    LOG::LogFailure( message:"Multiple Invocation with Expression - Boolean");
    LOG::LogBoolean( message:"Bridge Invocation", bool:b5 );
    LOG::LogBoolean( message:"Instance Operation Invocation", bool:b6 );
    LOG::LogBoolean( message:"Class Operation Invocation", bool:b7 );
  end if;

  if ( ( i5 == i6 ) and ( i6 == i7 ) )
    LOG::LogSuccess( message:"Multiple Invocation with Expression - Integer" );
  else
    LOG::LogFailure( message:"Multiple Invocation with Expression - Integer");
    LOG::LogInt( message:"Bridge Invocation", int:i5 );
    LOG::LogInt( message:"Instance Operation Invocation", int:i6 );
    LOG::LogInt( message:"Class Operation Invocation", int:i7 );
  end if;

  if ( ( r5 == r6 ) and ( r6 == r7 ) )
    LOG::LogSuccess( message:"Multiple Invocation with Expression - Real" );
  else
    LOG::LogFailure( message:"Multiple Invocation with Expression - Real");
    LOG::LogReal( message:"Bridge Invocation", real:r5 );
    LOG::LogReal( message:"Instance Operation Invocation", real:r6 );
    LOG::LogReal( message:"Class Operation Invocation", real:r7 );
  end if;

  if ( ( s5 == s6 ) and ( s6 == s7 ) )
    LOG::LogSuccess( message:"Multiple Invocation with Expression - String" );
  else
    LOG::LogFailure( message:"Multiple Invocation with Expression - String");
    LOG::LogString( message:"Bridge Invocation", str:s5 );
    LOG::LogString( message:"Instance Operation Invocation", str:s6 );
    LOG::LogString( message:"Class Operation Invocation", str:s7 );
  end if;

  if ( ( en5 == en6 ) and ( en6 == en7 ) )
    LOG::LogSuccess( message:"Multiple Invocation with Expression - Enumeration" );
  else
    LOG::LogFailure( message:"Multiple Invocation with Expression - Enumeration");
  end if;
  
  if ( ( u5 == u6 ) and ( u6 == u7 ) )
    LOG::LogSuccess( message:"Multiple Invocation with Expression - User Data Type" );
  else
    LOG::LogFailure( message:"Multiple Invocation with Expression - User Data Type");
    LOG::LogInt( message:"Bridge Invocation", int:u5 );
    LOG::LogInt( message:"Instance Operation Invocation", int:u6 );
    LOG::LogInt( message:"Class Operation Invocation", int:u7 );
  end if;

  select any drv from instances of DRV;
  generate DRV3 to drv;
end if;',
	'');
INSERT INTO SM_NSTXN
	VALUES (2621441,
	2621445,
	2621451,
	2621441,
	0);
INSERT INTO SM_TXN
	VALUES (2621441,
	2621445,
	2621442,
	0);
INSERT INTO SM_NSTXN
	VALUES (2621442,
	2621445,
	2621449,
	2621442,
	0);
INSERT INTO SM_TXN
	VALUES (2621442,
	2621445,
	2621451,
	0);
INSERT INTO SM_NSTXN
	VALUES (2621443,
	2621445,
	2621450,
	2621443,
	0);
INSERT INTO SM_TXN
	VALUES (2621443,
	2621445,
	2621449,
	0);
INSERT INTO SM_NSTXN
	VALUES (2621444,
	2621445,
	2621447,
	2621444,
	0);
INSERT INTO SM_TXN
	VALUES (2621444,
	2621445,
	2621450,
	0);
INSERT INTO SM_NSTXN
	VALUES (2621445,
	2621445,
	2621448,
	2621445,
	0);
INSERT INTO SM_TXN
	VALUES (2621445,
	2621445,
	2621447,
	0);
INSERT INTO SM_NSTXN
	VALUES (2621446,
	2621445,
	2621441,
	2621446,
	0);
INSERT INTO SM_TXN
	VALUES (2621446,
	2621445,
	2621448,
	0);
INSERT INTO SM_NSTXN
	VALUES (2621447,
	2621445,
	2621446,
	2621447,
	0);
INSERT INTO SM_TXN
	VALUES (2621447,
	2621445,
	2621441,
	0);
INSERT INTO SM_NSTXN
	VALUES (2621448,
	2621445,
	2621445,
	2621448,
	0);
INSERT INTO SM_TXN
	VALUES (2621448,
	2621445,
	2621446,
	0);
INSERT INTO SM_NSTXN
	VALUES (2621449,
	2621445,
	2621444,
	2621449,
	0);
INSERT INTO SM_TXN
	VALUES (2621449,
	2621445,
	2621445,
	0);
INSERT INTO SM_NSTXN
	VALUES (2621450,
	2621445,
	2621442,
	2621451,
	0);
INSERT INTO SM_TXN
	VALUES (2621450,
	2621445,
	2621443,
	0);
INSERT INTO SM_NSTXN
	VALUES (2621451,
	2621445,
	2621443,
	2621450,
	0);
INSERT INTO SM_TXN
	VALUES (2621451,
	2621445,
	2621444,
	0);
INSERT INTO SM_NSTXN
	VALUES (2621452,
	2621445,
	2621442,
	2621441,
	0);
INSERT INTO SM_TXN
	VALUES (2621452,
	2621445,
	2621442,
	0);
INSERT INTO GD_MD
	VALUES (2621441,
	10,
	2621445,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1373,
	4045,
	0.919523,
	0);
INSERT INTO GD_GE
	VALUES (2621442,
	2621441,
	2621441,
	41);
INSERT INTO GD_SHP
	VALUES (2621442,
	1696,
	1264,
	1808,
	1328);
INSERT INTO GD_GE
	VALUES (2621443,
	2621441,
	2621442,
	41);
INSERT INTO GD_SHP
	VALUES (2621443,
	1696,
	1792,
	1808,
	1856);
INSERT INTO GD_GE
	VALUES (2621444,
	2621441,
	2621443,
	41);
INSERT INTO GD_SHP
	VALUES (2621444,
	1696,
	1680,
	1808,
	1744);
INSERT INTO GD_GE
	VALUES (2621445,
	2621441,
	2621444,
	41);
INSERT INTO GD_SHP
	VALUES (2621445,
	1696,
	1568,
	1808,
	1632);
INSERT INTO GD_GE
	VALUES (2621446,
	2621441,
	2621445,
	41);
INSERT INTO GD_SHP
	VALUES (2621446,
	1696,
	1472,
	1808,
	1536);
INSERT INTO GD_GE
	VALUES (2621447,
	2621441,
	2621446,
	41);
INSERT INTO GD_SHP
	VALUES (2621447,
	1696,
	1360,
	1808,
	1424);
INSERT INTO GD_GE
	VALUES (2621448,
	2621441,
	2621447,
	41);
INSERT INTO GD_SHP
	VALUES (2621448,
	1968,
	1360,
	2080,
	1424);
INSERT INTO GD_GE
	VALUES (2621449,
	2621441,
	2621448,
	41);
INSERT INTO GD_SHP
	VALUES (2621449,
	1968,
	1264,
	2080,
	1328);
INSERT INTO GD_GE
	VALUES (2621450,
	2621441,
	2621449,
	41);
INSERT INTO GD_SHP
	VALUES (2621450,
	1968,
	1568,
	2080,
	1632);
INSERT INTO GD_GE
	VALUES (2621451,
	2621441,
	2621450,
	41);
INSERT INTO GD_SHP
	VALUES (2621451,
	1968,
	1472,
	2080,
	1536);
INSERT INTO GD_GE
	VALUES (2621452,
	2621441,
	2621451,
	41);
INSERT INTO GD_SHP
	VALUES (2621452,
	1968,
	1696,
	2080,
	1760);
INSERT INTO GD_GE
	VALUES (2621453,
	2621441,
	2621441,
	42);
INSERT INTO GD_CON
	VALUES (2621453,
	2621452,
	2621443,
	0);
INSERT INTO GD_CTXT
	VALUES (2621453,
	0,
	0,
	0,
	0,
	0,
	0,
	1884,
	1795,
	2006,
	1817,
	31,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621454,
	2621453,
	2016,
	1760,
	2016,
	1824,
	0);
INSERT INTO GD_LS
	VALUES (2621455,
	2621453,
	2016,
	1824,
	1808,
	1824,
	2621454);
INSERT INTO GD_GE
	VALUES (2621456,
	2621441,
	2621442,
	42);
INSERT INTO GD_CON
	VALUES (2621456,
	2621450,
	2621452,
	0);
INSERT INTO GD_CTXT
	VALUES (2621456,
	0,
	0,
	0,
	0,
	0,
	0,
	2023,
	1651,
	2145,
	1673,
	135,
	-4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621457,
	2621456,
	2016,
	1632,
	2016,
	1696,
	0);
INSERT INTO GD_GE
	VALUES (2621458,
	2621441,
	2621443,
	42);
INSERT INTO GD_CON
	VALUES (2621458,
	2621451,
	2621450,
	0);
INSERT INTO GD_CTXT
	VALUES (2621458,
	0,
	0,
	0,
	0,
	0,
	0,
	2030,
	1541,
	2166,
	1563,
	156,
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
	2016,
	1536,
	2016,
	1568,
	0);
INSERT INTO GD_GE
	VALUES (2621460,
	2621441,
	2621444,
	42);
INSERT INTO GD_CON
	VALUES (2621460,
	2621448,
	2621451,
	0);
INSERT INTO GD_CTXT
	VALUES (2621460,
	0,
	0,
	0,
	0,
	0,
	0,
	2027,
	1436,
	2158,
	1458,
	148,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621461,
	2621460,
	2016,
	1424,
	2016,
	1472,
	0);
INSERT INTO GD_GE
	VALUES (2621462,
	2621441,
	2621445,
	42);
INSERT INTO GD_CON
	VALUES (2621462,
	2621449,
	2621448,
	0);
INSERT INTO GD_CTXT
	VALUES (2621462,
	0,
	0,
	0,
	0,
	0,
	0,
	2048,
	1332,
	2166,
	1354,
	140,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621463,
	2621462,
	2032,
	1328,
	2032,
	1360,
	0);
INSERT INTO GD_GE
	VALUES (2621464,
	2621441,
	2621446,
	42);
INSERT INTO GD_CON
	VALUES (2621464,
	2621442,
	2621449,
	0);
INSERT INTO GD_CTXT
	VALUES (2621464,
	0,
	0,
	0,
	0,
	0,
	0,
	1821,
	1266,
	1948,
	1288,
	-6,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621465,
	2621464,
	1808,
	1296,
	1968,
	1296,
	0);
INSERT INTO GD_GE
	VALUES (2621466,
	2621441,
	2621447,
	42);
INSERT INTO GD_CON
	VALUES (2621466,
	2621447,
	2621442,
	0);
INSERT INTO GD_CTXT
	VALUES (2621466,
	0,
	0,
	0,
	0,
	0,
	0,
	1622,
	1334,
	1730,
	1356,
	-8,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621467,
	2621466,
	1744,
	1360,
	1744,
	1328,
	0);
INSERT INTO GD_GE
	VALUES (2621468,
	2621441,
	2621448,
	42);
INSERT INTO GD_CON
	VALUES (2621468,
	2621446,
	2621447,
	0);
INSERT INTO GD_CTXT
	VALUES (2621468,
	0,
	0,
	0,
	0,
	0,
	0,
	1626,
	1439,
	1731,
	1461,
	-7,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621469,
	2621468,
	1744,
	1472,
	1744,
	1424,
	0);
INSERT INTO GD_GE
	VALUES (2621470,
	2621441,
	2621449,
	42);
INSERT INTO GD_CON
	VALUES (2621470,
	2621445,
	2621446,
	0);
INSERT INTO GD_CTXT
	VALUES (2621470,
	0,
	0,
	0,
	0,
	0,
	0,
	1609,
	1542,
	1721,
	1564,
	-17,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621471,
	2621470,
	1744,
	1568,
	1744,
	1536,
	0);
INSERT INTO GD_GE
	VALUES (2621472,
	2621441,
	2621450,
	42);
INSERT INTO GD_CON
	VALUES (2621472,
	2621443,
	2621444,
	0);
INSERT INTO GD_CTXT
	VALUES (2621472,
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
	VALUES (2621473,
	2621472,
	1744,
	1792,
	1744,
	1744,
	0);
INSERT INTO GD_GE
	VALUES (2621474,
	2621441,
	2621451,
	42);
INSERT INTO GD_CON
	VALUES (2621474,
	2621444,
	2621445,
	0);
INSERT INTO GD_CTXT
	VALUES (2621474,
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
	VALUES (2621475,
	2621474,
	1744,
	1680,
	1744,
	1632,
	0);
INSERT INTO GD_GE
	VALUES (2621476,
	2621441,
	2621452,
	42);
INSERT INTO GD_CON
	VALUES (2621476,
	2621443,
	2621443,
	0);
INSERT INTO GD_CTXT
	VALUES (2621476,
	0,
	0,
	0,
	0,
	0,
	0,
	1684,
	1906,
	1806,
	1928,
	-2,
	46,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621477,
	2621476,
	1776,
	1856,
	1776,
	1888,
	0);
INSERT INTO GD_LS
	VALUES (2621478,
	2621476,
	1776,
	1888,
	1728,
	1888,
	2621477);
INSERT INTO GD_LS
	VALUES (2621479,
	2621476,
	1728,
	1888,
	1728,
	1856,
	2621478);
INSERT INTO O_OBJ
	VALUES (1048581,
	'Test Class Three',
	5,
	'TC3',
	'',
	1048578);
INSERT INTO O_REF
	VALUES (1048581,
	1048579,
	0,
	1048592,
	1048578,
	1048579,
	1048580,
	1048596,
	1048577,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048596,
	1048581,
	1048592,
	1048579,
	1);
INSERT INTO O_ATTR
	VALUES (1048596,
	1048581,
	0,
	'ID',
	'',
	'',
	'ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048597,
	1048581);
INSERT INTO O_BATTR
	VALUES (1048597,
	1048581);
INSERT INTO O_ATTR
	VALUES (1048597,
	1048581,
	1048596,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048581);
INSERT INTO O_OIDA
	VALUES (1048596,
	1048581,
	0);
INSERT INTO O_RTIDA
	VALUES (1048596,
	1048581,
	0,
	1048579,
	1048581);
INSERT INTO SM_ISM
	VALUES (3145734,
	1048581);
INSERT INTO SM_SM
	VALUES (3145734,
	'',
	6);
INSERT INTO SM_MOORE
	VALUES (3145734);
INSERT INTO SM_EVTDI
	VALUES (3145729,
	3145734,
	'bool',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (121625013,
	3145734,
	'bool',
	'',
	524290);
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
	'',
	0,
	'',
	'TC31',
	'');
INSERT INTO SM_PEVT
	VALUES (3145730,
	3145734,
	0);
INSERT INTO SM_EVT
	VALUES (3145730,
	3145734,
	0,
	2,
	'PE Event',
	0,
	'',
	'TC32',
	'');
INSERT INTO SM_STATE
	VALUES (3145729,
	3145734,
	0,
	'First and Last',
	1,
	1);
INSERT INTO SM_EIGN
	VALUES (3145729,
	3145729,
	3145734,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145729,
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
	'LOG::LogSuccess( message: "TC3 created successfully." );',
	'');
INSERT INTO SM_CRTXN
	VALUES (3145729,
	3145734,
	3145729,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145729,
	3145734,
	3145729,
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
	4199,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (3145730,
	3145729,
	3145729,
	41);
INSERT INTO GD_SHP
	VALUES (3145730,
	1776,
	1328,
	2064,
	1568);
INSERT INTO GD_GE
	VALUES (3145731,
	3145729,
	3145729,
	42);
INSERT INTO GD_CON
	VALUES (3145731,
	3145730,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (3145731,
	0,
	0,
	0,
	0,
	0,
	0,
	1816,
	1286,
	1913,
	1308,
	-1,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145732,
	3145731,
	1920,
	1328,
	1920,
	1264,
	0);
INSERT INTO O_OBJ
	VALUES (1048582,
	'Test Class Four',
	6,
	'TC4',
	'',
	1048578);
INSERT INTO O_TFR
	VALUES (1048613,
	1048582,
	'bool6',
	'',
	524290,
	1,
	'select any tc from instances of TC;
return tc.b;',
	1);
INSERT INTO O_TPARM
	VALUES (1048655,
	1048613,
	'date6',
	524302,
	0);
INSERT INTO O_TFR
	VALUES (1048614,
	1048582,
	'int6',
	'',
	524291,
	1,
	'select any tc from instances of TC;
return tc.i;',
	1);
INSERT INTO O_TPARM
	VALUES (1048656,
	1048614,
	'real6',
	524292,
	0);
INSERT INTO O_TFR
	VALUES (1048615,
	1048582,
	'real6',
	'',
	524292,
	1,
	'select any tc from instances of TC;
return tc.r;',
	1);
INSERT INTO O_TPARM
	VALUES (1048657,
	1048615,
	'str6',
	524293,
	0);
INSERT INTO O_TFR
	VALUES (1048616,
	1048582,
	'str6',
	'',
	524293,
	1,
	'select any tc from instances of TC;
return tc.s;',
	1);
INSERT INTO O_TPARM
	VALUES (1048658,
	1048616,
	'time6',
	524303,
	0);
INSERT INTO O_TFR
	VALUES (1048617,
	1048582,
	'bool7',
	'',
	524290,
	0,
	'select any tc from instances of TC;
return tc.b;',
	1);
INSERT INTO O_TPARM
	VALUES (1048659,
	1048617,
	'date7',
	524302,
	0);
INSERT INTO O_TFR
	VALUES (1048618,
	1048582,
	'int7',
	'',
	524291,
	0,
	'select any tc from instances of TC;
return tc.i;',
	1);
INSERT INTO O_TPARM
	VALUES (1048660,
	1048618,
	'real7',
	524292,
	0);
INSERT INTO O_TFR
	VALUES (1048619,
	1048582,
	'real7',
	'',
	524292,
	0,
	'select any tc from instances of TC;
return tc.r;',
	1);
INSERT INTO O_TPARM
	VALUES (1048661,
	1048619,
	'str7',
	524293,
	0);
INSERT INTO O_TFR
	VALUES (1048620,
	1048582,
	'str7',
	'',
	524293,
	0,
	'select any tc from instances of TC;
return tc.s;',
	1);
INSERT INTO O_TPARM
	VALUES (1048662,
	1048620,
	'time7',
	524303,
	0);
INSERT INTO O_TFR
	VALUES (1048621,
	1048582,
	'enum6',
	'',
	524307,
	1,
	'select any tc from instances of TC;
return tc.en;',
	1);
INSERT INTO O_TPARM
	VALUES (1048663,
	1048621,
	'udt6',
	524306,
	0);
INSERT INTO O_TFR
	VALUES (1048622,
	1048582,
	'enum7',
	'',
	524307,
	0,
	'select any tc from instances of TC;
return tc.en;',
	1);
INSERT INTO O_TPARM
	VALUES (1048664,
	1048622,
	'udt7',
	524306,
	0);
INSERT INTO O_TFR
	VALUES (1048623,
	1048582,
	'udt6',
	'',
	524306,
	1,
	'select any tc from instances of TC;
return tc.u;',
	1);
INSERT INTO O_TPARM
	VALUES (1048665,
	1048623,
	'bool6',
	524290,
	0);
INSERT INTO O_TFR
	VALUES (1048624,
	1048582,
	'udt7',
	'',
	524306,
	0,
	'select any tc from instances of TC;
return tc.u;',
	1);
INSERT INTO O_TPARM
	VALUES (1048666,
	1048624,
	'bool7',
	524290,
	0);
INSERT INTO O_NBATTR
	VALUES (1048598,
	1048582);
INSERT INTO O_BATTR
	VALUES (1048598,
	1048582);
INSERT INTO O_ATTR
	VALUES (1048598,
	1048582,
	0,
	'ID',
	'',
	'',
	'ID',
	0,
	524294);
INSERT INTO O_ID
	VALUES (0,
	1048582);
INSERT INTO O_OIDA
	VALUES (1048598,
	1048582,
	0);
INSERT INTO O_OBJ
	VALUES (1048583,
	'Test Class Three Subclass',
	7,
	'TC3S',
	'',
	1048578);
INSERT INTO O_REF
	VALUES (1048583,
	1048581,
	0,
	1048596,
	1048579,
	1048582,
	1048581,
	1048599,
	1048578,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048599,
	1048583,
	1048592,
	1048579,
	1);
INSERT INTO O_ATTR
	VALUES (1048599,
	1048583,
	0,
	'ID',
	'',
	'',
	'ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048600,
	1048583);
INSERT INTO O_BATTR
	VALUES (1048600,
	1048583);
INSERT INTO O_ATTR
	VALUES (1048600,
	1048583,
	1048599,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048583);
INSERT INTO O_OIDA
	VALUES (1048599,
	1048583,
	0);
INSERT INTO SM_ISM
	VALUES (3670023,
	1048583);
INSERT INTO SM_SM
	VALUES (3670023,
	'',
	7);
INSERT INTO SM_MOORE
	VALUES (3670023);
INSERT INTO SM_NLEVT
	VALUES (3670017,
	3670023,
	0,
	3145730,
	3145734,
	0,
	'NLE Event');
INSERT INTO SM_SEVT
	VALUES (3670017,
	3670023,
	0);
INSERT INTO SM_EVT
	VALUES (3670017,
	3670023,
	0,
	0,
	'NLE Event',
	0,
	'',
	'TC32*',
	'');
INSERT INTO SM_STATE
	VALUES (3670017,
	3670023,
	0,
	'Accept PE',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (3670017,
	3670017,
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
	'x = rcvd_evt.bool;
LOG::LogSuccess( message: "Received PE/NLE successfully." );',
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
	4199,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (3670018,
	3670017,
	3670017,
	41);
INSERT INTO GD_SHP
	VALUES (3670018,
	1808,
	1328,
	2064,
	1520);
INSERT INTO GD_GE
	VALUES (3670019,
	3670017,
	3670017,
	42);
INSERT INTO GD_CON
	VALUES (3670019,
	3670018,
	3670018,
	0);
INSERT INTO GD_CTXT
	VALUES (3670019,
	0,
	0,
	0,
	0,
	0,
	0,
	1852,
	1266,
	2025,
	1288,
	0,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670020,
	3670019,
	1824,
	1328,
	1824,
	1296,
	0);
INSERT INTO GD_LS
	VALUES (3670021,
	3670019,
	1824,
	1296,
	2048,
	1296,
	3670020);
INSERT INTO GD_LS
	VALUES (3670022,
	3670019,
	2048,
	1296,
	2048,
	1328,
	3670021);
INSERT INTO O_OBJ
	VALUES (1048584,
	'Bogus Class',
	8,
	'BC',
	'',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048602,
	1048584);
INSERT INTO O_BATTR
	VALUES (1048602,
	1048584);
INSERT INTO O_ATTR
	VALUES (1048602,
	1048584,
	0,
	'ID',
	'',
	'',
	'ID',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048601,
	1048584);
INSERT INTO O_BATTR
	VALUES (1048601,
	1048584);
INSERT INTO O_ATTR
	VALUES (1048601,
	1048584,
	1048602,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048584);
INSERT INTO O_OIDA
	VALUES (1048602,
	1048584,
	0);
INSERT INTO SM_ISM
	VALUES (4194312,
	1048584);
INSERT INTO SM_SM
	VALUES (4194312,
	'',
	8);
INSERT INTO SM_MOORE
	VALUES (4194312);
INSERT INTO SM_LEVT
	VALUES (4194305,
	4194312,
	0);
INSERT INTO SM_SEVT
	VALUES (4194305,
	4194312,
	0);
INSERT INTO SM_EVT
	VALUES (4194305,
	4194312,
	0,
	1,
	'',
	0,
	'',
	'BC1',
	'');
INSERT INTO SM_STATE
	VALUES (4194305,
	4194312,
	0,
	'Bogus State',
	1,
	0);
INSERT INTO SM_CH
	VALUES (4194305,
	4194305,
	4194312,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (4194305,
	4194305,
	4194312,
	0);
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
	'',
	'');
INSERT INTO SM_CRTXN
	VALUES (4194305,
	4194312,
	4194305,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194305,
	4194312,
	4194305,
	0);
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
	4199,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (4194306,
	4194305,
	4194305,
	41);
INSERT INTO GD_SHP
	VALUES (4194306,
	1792,
	1296,
	2048,
	1472);
INSERT INTO GD_GE
	VALUES (4194307,
	4194305,
	4194305,
	42);
INSERT INTO GD_CON
	VALUES (4194307,
	4194306,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (4194307,
	0,
	0,
	0,
	0,
	0,
	0,
	1862,
	1250,
	1902,
	1272,
	-12,
	-5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194308,
	4194307,
	1920,
	1296,
	1920,
	1232,
	0);
INSERT INTO O_OBJ
	VALUES (1048585,
	'Test Class Five',
	9,
	'TC5',
	'',
	1048578);
INSERT INTO O_TFR
	VALUES (1048625,
	1048585,
	'bool8',
	'',
	524290,
	1,
	'select any tc from instances of TC;

if ( param.date8 == tc.d )
  LOG::LogSuccess( message: "Instance Operation bool8 - Date parameter is correct." );
else
  LOG::LogFailure( message: "Instance Operation bool8 - Date parameter is incorrect." );
end if;

return tc.b;',
	1);
INSERT INTO O_TPARM
	VALUES (1048667,
	1048625,
	'date8',
	524302,
	0);
INSERT INTO O_TFR
	VALUES (1048626,
	1048585,
	'bool9',
	'',
	524290,
	1,
	'return false;',
	1);
INSERT INTO O_TPARM
	VALUES (1048668,
	1048626,
	'date9',
	524302,
	0);
INSERT INTO O_NBATTR
	VALUES (1048603,
	1048585);
INSERT INTO O_BATTR
	VALUES (1048603,
	1048585);
INSERT INTO O_ATTR
	VALUES (1048603,
	1048585,
	0,
	'ID',
	'',
	'',
	'ID',
	0,
	524294);
INSERT INTO O_ID
	VALUES (0,
	1048585);
INSERT INTO O_OIDA
	VALUES (1048603,
	1048585,
	0);
INSERT INTO R_SIMP
	VALUES (1048577);
INSERT INTO R_REL
	VALUES (1048577,
	1,
	'',
	1048578);
INSERT INTO R_FORM
	VALUES (1048579,
	1048577,
	1048577,
	1,
	1,
	'');
INSERT INTO R_RGO
	VALUES (1048579,
	1048577,
	1048577);
INSERT INTO R_OIR
	VALUES (1048579,
	1048577,
	1048577,
	0);
INSERT INTO R_PART
	VALUES (1048580,
	1048577,
	1048578,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (1048580,
	1048577,
	1048578,
	0);
INSERT INTO R_OIR
	VALUES (1048580,
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
INSERT INTO R_FORM
	VALUES (1048581,
	1048578,
	1048579,
	0,
	0,
	'');
INSERT INTO R_RGO
	VALUES (1048581,
	1048578,
	1048579);
INSERT INTO R_OIR
	VALUES (1048581,
	1048578,
	1048579,
	0);
INSERT INTO R_PART
	VALUES (1048579,
	1048578,
	1048580,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (1048579,
	1048578,
	1048580,
	0);
INSERT INTO R_OIR
	VALUES (1048579,
	1048578,
	1048580,
	0);
INSERT INTO R_SUBSUP
	VALUES (1048579);
INSERT INTO R_REL
	VALUES (1048579,
	3,
	'',
	1048578);
INSERT INTO R_SUPER
	VALUES (1048581,
	1048579,
	1048581);
INSERT INTO R_RTO
	VALUES (1048581,
	1048579,
	1048581,
	0);
INSERT INTO R_OIR
	VALUES (1048581,
	1048579,
	1048581,
	0);
INSERT INTO R_SUB
	VALUES (1048583,
	1048579,
	1048582);
INSERT INTO R_RGO
	VALUES (1048583,
	1048579,
	1048582);
INSERT INTO R_OIR
	VALUES (1048583,
	1048579,
	1048582,
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
	1228,
	3723,
	0.742656,
	0);
INSERT INTO GD_GE
	VALUES (1048580,
	1048577,
	1048577,
	21);
INSERT INTO GD_SHP
	VALUES (1048580,
	1776,
	1248,
	1952,
	1360);
INSERT INTO GD_GE
	VALUES (1048581,
	1048577,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048581,
	1776,
	1408,
	1952,
	1520);
INSERT INTO GD_GE
	VALUES (1048582,
	1048577,
	1048579,
	21);
INSERT INTO GD_SHP
	VALUES (1048582,
	1264,
	1248,
	1648,
	2080);
INSERT INTO GD_GE
	VALUES (1048583,
	1048577,
	1048580,
	21);
INSERT INTO GD_SHP
	VALUES (1048583,
	1776,
	1568,
	1952,
	1680);
INSERT INTO GD_GE
	VALUES (1048584,
	1048577,
	1048581,
	21);
INSERT INTO GD_SHP
	VALUES (1048584,
	1776,
	1744,
	1952,
	1856);
INSERT INTO GD_GE
	VALUES (1048585,
	1048577,
	1048582,
	21);
INSERT INTO GD_SHP
	VALUES (1048585,
	2064,
	1248,
	2448,
	1920);
INSERT INTO GD_GE
	VALUES (1048586,
	1048577,
	1048583,
	21);
INSERT INTO GD_SHP
	VALUES (1048586,
	1776,
	1952,
	1952,
	2064);
INSERT INTO GD_GE
	VALUES (1048607,
	1048577,
	1048584,
	21);
INSERT INTO GD_SHP
	VALUES (1048607,
	2272,
	1952,
	2448,
	2064);
INSERT INTO GD_GE
	VALUES (1048630,
	1048577,
	1048585,
	21);
INSERT INTO GD_SHP
	VALUES (1048630,
	2064,
	1952,
	2240,
	2064);
INSERT INTO GD_GE
	VALUES (1048599,
	1048577,
	1048577,
	24);
INSERT INTO GD_CON
	VALUES (1048599,
	1048582,
	1048583,
	0);
INSERT INTO GD_CTXT
	VALUES (1048599,
	0,
	0,
	0,
	0,
	0,
	0,
	1702,
	1588,
	1726,
	1610,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048600,
	1048599,
	1648,
	1616,
	1776,
	1616,
	0);
INSERT INTO GD_GE
	VALUES (1048601,
	1048577,
	1048578,
	24);
INSERT INTO GD_CON
	VALUES (1048601,
	1048584,
	1048582,
	0);
INSERT INTO GD_CTXT
	VALUES (1048601,
	0,
	0,
	0,
	0,
	0,
	0,
	1702,
	1764,
	1726,
	1786,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048602,
	1048601,
	1776,
	1792,
	1648,
	1792,
	0);
INSERT INTO GD_GE
	VALUES (1048603,
	1048577,
	1048579,
	24);
INSERT INTO GD_CON
	VALUES (1048603,
	1048584,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (1048603,
	1879,
	1862,
	2014,
	1884,
	18,
	-4,
	0,
	0,
	0,
	0,
	0,
	0,
	1776,
	1861,
	1800,
	1883,
	-85,
	-31);
INSERT INTO GD_LS
	VALUES (1048604,
	1048603,
	1856,
	1856,
	1856,
	1920,
	0);
INSERT INTO GD_GE
	VALUES (1048605,
	1048577,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (1048605,
	1048586,
	1048603,
	0);
INSERT INTO GD_CTXT
	VALUES (1048605,
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
	VALUES (1048606,
	1048605,
	1856,
	1952,
	1856,
	1920,
	0);
