-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (3824712,
	'cl',
	'This test deals with if, elif, else, while, for, break, and continue.
It nests if, elif, and while.
It executes a break and a for from within a while loop and a for loop.',
	0,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	3824712,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	3824712,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	3824712,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	3824712,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	3824712,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	3824712,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	3824712,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	3824712,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	3824712,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	3824712,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	3824712,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	3824712,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	3824712,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	3824712,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	3824712,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	3824712,
	'inst_ref<Timer>',
	'');
INSERT INTO S_EE
	VALUES (524290,
	'Logging ',
	'',
	'LOG',
	3824712);
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
	3824712);
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
	VALUES (524293,
	'Architecture',
	'',
	'ARCH',
	3824712);
INSERT INTO S_BRG
	VALUES (524325,
	524293,
	'shutdown',
	'',
	0,
	524289,
	'control stop;',
	1);
INSERT INTO GD_MD
	VALUES (524289,
	1,
	3824712,
	1,
	1,
	0,
	1,
	1,
	0,
	12,
	1599,
	4076,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (524301,
	524289,
	524290,
	12);
INSERT INTO GD_SHP
	VALUES (524301,
	2128,
	1632,
	2288,
	1728);
INSERT INTO GD_GE
	VALUES (524313,
	524289,
	2621445,
	11);
INSERT INTO GD_SHP
	VALUES (524313,
	1920,
	1488,
	2080,
	1584);
INSERT INTO GD_GE
	VALUES (524314,
	524289,
	524291,
	12);
INSERT INTO GD_SHP
	VALUES (524314,
	1712,
	1632,
	1872,
	1728);
INSERT INTO GD_GE
	VALUES (524316,
	524289,
	524293,
	12);
INSERT INTO GD_SHP
	VALUES (524316,
	1920,
	1632,
	2080,
	1728);
INSERT INTO GD_MD
	VALUES (524290,
	2,
	3824712,
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
	VALUES (524317,
	524290,
	2621445,
	11);
INSERT INTO GD_SHP
	VALUES (524317,
	1920,
	1344,
	2080,
	1440);
INSERT INTO GD_MD
	VALUES (524291,
	3,
	3824712,
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
	VALUES (524318,
	524291,
	2621445,
	11);
INSERT INTO GD_SHP
	VALUES (524318,
	1920,
	1344,
	2080,
	1440);
INSERT INTO GD_MD
	VALUES (524292,
	4,
	3824712,
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
	VALUES (524319,
	524292,
	2621445,
	11);
INSERT INTO GD_SHP
	VALUES (524319,
	1920,
	1344,
	2080,
	1440);
INSERT INTO S_SS
	VALUES (2621445,
	'cl',
	'',
	'',
	1,
	3824712,
	2621445);
INSERT INTO O_OBJ
	VALUES (2621441,
	'Driver',
	1,
	'DR',
	'',
	2621445);
INSERT INTO O_NBATTR
	VALUES (2621441,
	2621441);
INSERT INTO O_BATTR
	VALUES (2621441,
	2621441);
INSERT INTO O_ATTR
	VALUES (2621441,
	2621441,
	0,
	'driver_id',
	'',
	'',
	'driver_id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (2621443,
	2621441);
INSERT INTO O_BATTR
	VALUES (2621443,
	2621441);
INSERT INTO O_ATTR
	VALUES (2621443,
	2621441,
	2621441,
	'testNumber',
	'',
	'',
	'testNumber',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (2621442,
	2621441);
INSERT INTO O_BATTR
	VALUES (2621442,
	2621441);
INSERT INTO O_ATTR
	VALUES (2621442,
	2621441,
	2621443,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	2621441);
INSERT INTO O_OIDA
	VALUES (2621441,
	2621441,
	0);
INSERT INTO SM_ISM
	VALUES (1048578,
	2621441);
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
	'ELIF Nesting',
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
	'DR1',
	'');
INSERT INTO SM_SEME
	VALUES (1048577,
	1048577,
	1048578,
	1048577);
INSERT INTO SM_LEVT
	VALUES (1048578,
	1048578,
	1048577);
INSERT INTO SM_SEVT
	VALUES (1048578,
	1048578,
	1048577);
INSERT INTO SM_EVT
	VALUES (1048578,
	1048578,
	1048577,
	2,
	'next test',
	0,
	'',
	'DR2',
	'');
INSERT INTO SM_SEME
	VALUES (1048577,
	1048578,
	1048578,
	1048577);
INSERT INTO SM_STATE
	VALUES (1048578,
	1048578,
	1048577,
	'ELIF Length',
	2,
	0);
INSERT INTO SM_EIGN
	VALUES (1048578,
	1048577,
	1048578,
	1048577,
	'');
INSERT INTO SM_SEME
	VALUES (1048578,
	1048577,
	1048578,
	1048577);
INSERT INTO SM_SEME
	VALUES (1048578,
	1048578,
	1048578,
	1048577);
INSERT INTO SM_STATE
	VALUES (1048579,
	1048578,
	1048577,
	'WHILE Looping',
	3,
	0);
INSERT INTO SM_EIGN
	VALUES (1048579,
	1048577,
	1048578,
	1048577,
	'');
INSERT INTO SM_SEME
	VALUES (1048579,
	1048577,
	1048578,
	1048577);
INSERT INTO SM_SEME
	VALUES (1048579,
	1048578,
	1048578,
	1048577);
INSERT INTO SM_STATE
	VALUES (1048580,
	1048578,
	1048577,
	'WHILE Nesting',
	4,
	0);
INSERT INTO SM_EIGN
	VALUES (1048580,
	1048577,
	1048578,
	1048577,
	'');
INSERT INTO SM_SEME
	VALUES (1048580,
	1048577,
	1048578,
	1048577);
INSERT INTO SM_SEME
	VALUES (1048580,
	1048578,
	1048578,
	1048577);
INSERT INTO SM_STATE
	VALUES (1048581,
	1048578,
	1048577,
	'WHILE With No Statements',
	5,
	0);
INSERT INTO SM_EIGN
	VALUES (1048581,
	1048577,
	1048578,
	1048577,
	'');
INSERT INTO SM_SEME
	VALUES (1048581,
	1048577,
	1048578,
	1048577);
INSERT INTO SM_SEME
	VALUES (1048581,
	1048578,
	1048578,
	1048577);
INSERT INTO SM_STATE
	VALUES (1048582,
	1048578,
	1048577,
	'Simple BREAK',
	6,
	0);
INSERT INTO SM_EIGN
	VALUES (1048582,
	1048577,
	1048578,
	1048577,
	'');
INSERT INTO SM_SEME
	VALUES (1048582,
	1048577,
	1048578,
	1048577);
INSERT INTO SM_SEME
	VALUES (1048582,
	1048578,
	1048578,
	1048577);
INSERT INTO SM_STATE
	VALUES (1048583,
	1048578,
	1048577,
	'BREAK Out Of WHILE',
	7,
	0);
INSERT INTO SM_EIGN
	VALUES (1048583,
	1048577,
	1048578,
	1048577,
	'');
INSERT INTO SM_SEME
	VALUES (1048583,
	1048577,
	1048578,
	1048577);
INSERT INTO SM_SEME
	VALUES (1048583,
	1048578,
	1048578,
	1048577);
INSERT INTO SM_STATE
	VALUES (1048584,
	1048578,
	1048577,
	'BREAK Out Of FOREACH',
	8,
	0);
INSERT INTO SM_EIGN
	VALUES (1048584,
	1048577,
	1048578,
	1048577,
	'');
INSERT INTO SM_SEME
	VALUES (1048584,
	1048577,
	1048578,
	1048577);
INSERT INTO SM_SEME
	VALUES (1048584,
	1048578,
	1048578,
	1048577);
INSERT INTO SM_STATE
	VALUES (1048585,
	1048578,
	1048577,
	'CONTINUE In WHILE',
	9,
	0);
INSERT INTO SM_EIGN
	VALUES (1048585,
	1048577,
	1048578,
	1048577,
	'');
INSERT INTO SM_SEME
	VALUES (1048585,
	1048577,
	1048578,
	1048577);
INSERT INTO SM_SEME
	VALUES (1048585,
	1048578,
	1048578,
	1048577);
INSERT INTO SM_STATE
	VALUES (1048586,
	1048578,
	1048577,
	'CONTINUE In FOREACH',
	10,
	0);
INSERT INTO SM_EIGN
	VALUES (1048586,
	1048577,
	1048578,
	1048577,
	'');
INSERT INTO SM_SEME
	VALUES (1048586,
	1048577,
	1048578,
	1048577);
INSERT INTO SM_SEME
	VALUES (1048586,
	1048578,
	1048578,
	1048577);
INSERT INTO SM_STATE
	VALUES (1048587,
	1048578,
	1048577,
	'Test Complete',
	11,
	1);
INSERT INTO SM_EIGN
	VALUES (1048587,
	1048577,
	1048578,
	1048577,
	'');
INSERT INTO SM_SEME
	VALUES (1048587,
	1048577,
	1048578,
	1048577);
INSERT INTO SM_EIGN
	VALUES (1048587,
	1048578,
	1048578,
	1048577,
	'');
INSERT INTO SM_SEME
	VALUES (1048587,
	1048578,
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
INSERT INTO SM_NSTXN
	VALUES (1048578,
	1048578,
	1048577,
	1048578,
	1048577);
INSERT INTO SM_TXN
	VALUES (1048578,
	1048578,
	1048578,
	1048577);
INSERT INTO SM_NSTXN
	VALUES (1048579,
	1048578,
	1048578,
	1048578,
	1048577);
INSERT INTO SM_TXN
	VALUES (1048579,
	1048578,
	1048579,
	1048577);
INSERT INTO SM_NSTXN
	VALUES (1048580,
	1048578,
	1048579,
	1048578,
	1048577);
INSERT INTO SM_TXN
	VALUES (1048580,
	1048578,
	1048580,
	1048577);
INSERT INTO SM_NSTXN
	VALUES (1048581,
	1048578,
	1048580,
	1048578,
	1048577);
INSERT INTO SM_TXN
	VALUES (1048581,
	1048578,
	1048581,
	1048577);
INSERT INTO SM_NSTXN
	VALUES (1048582,
	1048578,
	1048581,
	1048578,
	1048577);
INSERT INTO SM_TXN
	VALUES (1048582,
	1048578,
	1048582,
	1048577);
INSERT INTO SM_NSTXN
	VALUES (1048583,
	1048578,
	1048582,
	1048578,
	1048577);
INSERT INTO SM_TXN
	VALUES (1048583,
	1048578,
	1048583,
	1048577);
INSERT INTO SM_NSTXN
	VALUES (1048584,
	1048578,
	1048583,
	1048578,
	1048577);
INSERT INTO SM_TXN
	VALUES (1048584,
	1048578,
	1048584,
	1048577);
INSERT INTO SM_NSTXN
	VALUES (1048585,
	1048578,
	1048584,
	1048578,
	1048577);
INSERT INTO SM_TXN
	VALUES (1048585,
	1048578,
	1048585,
	1048577);
INSERT INTO SM_NSTXN
	VALUES (1048586,
	1048578,
	1048585,
	1048578,
	1048577);
INSERT INTO SM_TXN
	VALUES (1048586,
	1048578,
	1048586,
	1048577);
INSERT INTO SM_NSTXN
	VALUES (1048587,
	1048578,
	1048586,
	1048578,
	1048577);
INSERT INTO SM_TXN
	VALUES (1048587,
	1048578,
	1048587,
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
	'// ELIF Nesting:
if (FALSE)
   // comment
elif (TRUE)
   if (FALSE)
      // comment
   elif (TRUE)
      if (FALSE)
         // comment
      elif (TRUE)
         if (FALSE)
            // comment
         elif (TRUE)
            if (FALSE)
               // comment
            elif (TRUE)
               if (FALSE)
                  // comment
               elif (TRUE)
                  if (FALSE)
                     // comment
                  elif (TRUE)
                     LOG::LogSuccess(message:"ELIF Nesting");
                  else
                     LOG::LogFailure(message:"ELIF Nesting");
                  end if;
               else
                  LOG::LogFailure(message:"ELIF Nesting");
               end if;
            else
               LOG::LogFailure(message:"ELIF Nesting");
            end if;
         else
            LOG::LogFailure(message:"ELIF Nesting");
         end if;
      else
         LOG::LogFailure(message:"ELIF Nesting");
      end if;
   else
      LOG::LogFailure(message:"ELIF Nesting");
   end if;
end if;


//============================================================
// Test complete
//============================================================

generate DR2:''next test'' () to self;',
	'');
INSERT INTO SM_MOAH
	VALUES (1048578,
	1048578,
	1048578);
INSERT INTO SM_AH
	VALUES (1048578,
	1048578);
INSERT INTO SM_ACT
	VALUES (1048578,
	1048578,
	1,
	'// Increment test counter
self.testNumber = self.testNumber + 1;


// ELIF length:
  if (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (FALSE)
     LOG::LogFailure(message:"elif length");
  elif (TRUE)
    LOG::LogSuccess(message:"elif length");
  else
     LOG::LogFailure(message:"elif length");
  end if;


//============================================================
// Test complete
//============================================================

generate DR2:''next test'' () to self;
',
	'');
INSERT INTO SM_MOAH
	VALUES (1048579,
	1048578,
	1048579);
INSERT INTO SM_AH
	VALUES (1048579,
	1048578);
INSERT INTO SM_ACT
	VALUES (1048579,
	1048578,
	1,
	'  i = 1;
  while (i <= 20)
     i = i + 1;
  end while;

  if (i == 21)
    LOG::LogSuccess(message:"While looping");
  else
    LOG::LogFailure(message:"While looping");
  end if;


//============================================================
// Test complete
//============================================================

generate DR2:''next test'' () to self;
',
	'');
INSERT INTO SM_MOAH
	VALUES (1048580,
	1048578,
	1048580);
INSERT INTO SM_AH
	VALUES (1048580,
	1048578);
INSERT INTO SM_ACT
	VALUES (1048580,
	1048578,
	1,
	'// Increment test counter
self.testNumber = self.testNumber + 1;


// while nesting depth test
  j = 1;
  while ( j <= 10 )
     while ( j <= 10 )
        while ( j <= 10 )
           while ( j <= 10 )
              while ( j <= 10 )
                 while ( j <= 10 )
                    j = j + 1;
                 end while;
              end while;
           end while;
        end while;
     end while;
  end while;

  if (j == 11)
    LOG::LogSuccess(message:"While Nesting");
  else
    LOG::LogFailure(message:"While Nesting");
  end if;


//============================================================
// Test complete
//============================================================

generate DR2:''next test'' () to self;
',
	'');
INSERT INTO SM_MOAH
	VALUES (1048581,
	1048578,
	1048581);
INSERT INTO SM_AH
	VALUES (1048581,
	1048578);
INSERT INTO SM_ACT
	VALUES (1048581,
	1048578,
	1,
	'// while construct with no statements--it should successfully parse
// and report success.  If it fails, it will hang.
   while ( FALSE )
   end while;

    LOG::LogSuccess(message:"While with no statements");


//============================================================
// Test complete
//============================================================

generate DR2:''next test'' () to self;
',
	'');
INSERT INTO SM_MOAH
	VALUES (1048582,
	1048578,
	1048582);
INSERT INTO SM_AH
	VALUES (1048582,
	1048578);
INSERT INTO SM_ACT
	VALUES (1048582,
	1048578,
	1,
	'  while ( TRUE )
     break;
  end while;
  LOG::LogSuccess(message:"Simple Break");



//============================================================
// Test complete
//============================================================

generate DR2:''next test'' () to self;
',
	'');
INSERT INTO SM_MOAH
	VALUES (1048583,
	1048578,
	1048583);
INSERT INTO SM_AH
	VALUES (1048583,
	1048578);
INSERT INTO SM_ACT
	VALUES (1048583,
	1048578,
	1,
	'  x = 0;
  while ( x < 20 )
     x = x + 1;
     if (x == 10)
        break;
     end if;
  end while;

  if (x == 10)
    LOG::LogSuccess(message:"break out of while");  
  else
    LOG::LogFailure(message:"break out of while");  
  end if;


//============================================================
// Test complete
//============================================================

generate DR2:''next test'' () to self;
',
	'');
INSERT INTO SM_MOAH
	VALUES (1048584,
	1048578,
	1048584);
INSERT INTO SM_AH
	VALUES (1048584,
	1048578);
INSERT INTO SM_ACT
	VALUES (1048584,
	1048578,
	1,
	'// Added testNumber attribute to DR object

// Added initialization of x for while loop
x = 0;

  while ( x <= 20 )
     x = x + 1;
     create object instance a of A;
     a.a_id = x;
     a.val = x;
  end while;

  // Implicitly declare a for this scope
  select any a from instances of A;

  select many a_set from instances of A;
  for each a in a_set
     if (a.val == 10)
        break;
     end if;
  end for;

  if (a.val == 10)
    LOG::LogSuccess(message:"break out of for each");  
  else
    LOG::LogFailure(message:"break out of for each");  
  end if;

  for each a in a_set
     delete object instance a;
  end for;

//============================================================
// Test complete
//============================================================

generate DR2:''next test'' () to self;
',
	'');
INSERT INTO SM_MOAH
	VALUES (1048585,
	1048578,
	1048585);
INSERT INTO SM_AH
	VALUES (1048585,
	1048578);
INSERT INTO SM_ACT
	VALUES (1048585,
	1048578,
	1,
	'// continue in a while loop
  x = 0;
  y = 0;
  while (x < 20)
     x = x + 1;
     if ((x == 10) or (x == 15))
        continue;
     end if;
     y = y + 1;
  end while;

  if ((x == 20) and (y == 18))
       LOG::LogSuccess(message:"continue in while");  
  else
       LOG::LogFailure(message:"continue in while");  
  end if;


//============================================================
// Test complete
//============================================================

generate DR2:''next test'' () to self;
',
	'');
INSERT INTO SM_MOAH
	VALUES (1048586,
	1048578,
	1048586);
INSERT INTO SM_AH
	VALUES (1048586,
	1048578);
INSERT INTO SM_ACT
	VALUES (1048586,
	1048578,
	1,
	'// Initialize loop counter
x = 0;

// continue in a for each loop
  while ( x < 20 )
     x = x + 1;
     create object instance a of A;
     a.a_id = x;
     a.val = x;
  end while;

  select many a_set from instances of A;
  for each a in a_set
     if (a.val == 10)
       continue;
     end if;
     a.val = 999;
  end for;

  select many a_set from instances of A where (selected.val == 10);
  if (cardinality a_set == 1)
     select many a_set from instances of A where (selected.val == 999);
     if (cardinality a_set == 19)
       LOG::LogSuccess(message:"continue in for each");  
     else
       LOG::LogFailure(message:"continue in for each");  
     end if;
  else 
       LOG::LogFailure(message:"continue in for each");  
  end if;

  select many a_set from instances of A;
  for each a in a_set
     delete object instance a;
  end for;

//============================================================
// Test complete
//============================================================
generate DR2:''next test''() to self;',
	'');
INSERT INTO SM_MOAH
	VALUES (1048587,
	1048578,
	1048587);
INSERT INTO SM_AH
	VALUES (1048587,
	1048578);
INSERT INTO SM_ACT
	VALUES (1048587,
	1048578,
	1,
	'LOG::LogInfo(message:"test complete");
bridge ARCH::shutdown();',
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
	1712,
	1264,
	1920,
	1360);
INSERT INTO GD_GE
	VALUES (1048579,
	1048577,
	1048578,
	41);
INSERT INTO GD_SHP
	VALUES (1048579,
	1712,
	1408,
	1920,
	1504);
INSERT INTO GD_GE
	VALUES (1048580,
	1048577,
	1048579,
	41);
INSERT INTO GD_SHP
	VALUES (1048580,
	1712,
	1552,
	1920,
	1648);
INSERT INTO GD_GE
	VALUES (1048581,
	1048577,
	1048580,
	41);
INSERT INTO GD_SHP
	VALUES (1048581,
	1712,
	1696,
	1920,
	1792);
INSERT INTO GD_GE
	VALUES (1048582,
	1048577,
	1048581,
	41);
INSERT INTO GD_SHP
	VALUES (1048582,
	1984,
	1264,
	2192,
	1360);
INSERT INTO GD_GE
	VALUES (1048583,
	1048577,
	1048582,
	41);
INSERT INTO GD_SHP
	VALUES (1048583,
	1984,
	1408,
	2192,
	1504);
INSERT INTO GD_GE
	VALUES (1048584,
	1048577,
	1048583,
	41);
INSERT INTO GD_SHP
	VALUES (1048584,
	1984,
	1552,
	2192,
	1648);
INSERT INTO GD_GE
	VALUES (1048585,
	1048577,
	1048584,
	41);
INSERT INTO GD_SHP
	VALUES (1048585,
	1984,
	1696,
	2192,
	1792);
INSERT INTO GD_GE
	VALUES (1048586,
	1048577,
	1048585,
	41);
INSERT INTO GD_SHP
	VALUES (1048586,
	2256,
	1264,
	2448,
	1360);
INSERT INTO GD_GE
	VALUES (1048587,
	1048577,
	1048586,
	41);
INSERT INTO GD_SHP
	VALUES (1048587,
	2256,
	1408,
	2448,
	1504);
INSERT INTO GD_GE
	VALUES (1048588,
	1048577,
	1048587,
	41);
INSERT INTO GD_SHP
	VALUES (1048588,
	2256,
	1552,
	2448,
	1648);
INSERT INTO GD_GE
	VALUES (1048589,
	1048577,
	1048577,
	42);
INSERT INTO GD_CON
	VALUES (1048589,
	1048578,
	1048578,
	0);
INSERT INTO GD_CTXT
	VALUES (1048589,
	0,
	0,
	0,
	0,
	0,
	0,
	1643,
	1198,
	1772,
	1243,
	-12,
	-19,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048590,
	1048589,
	1712,
	1296,
	1680,
	1296,
	0);
INSERT INTO GD_LS
	VALUES (1048591,
	1048589,
	1680,
	1296,
	1680,
	1232,
	1048590);
INSERT INTO GD_LS
	VALUES (1048592,
	1048589,
	1680,
	1232,
	1744,
	1232,
	1048591);
INSERT INTO GD_LS
	VALUES (1048593,
	1048589,
	1744,
	1232,
	1744,
	1264,
	1048592);
INSERT INTO GD_GE
	VALUES (1048594,
	1048577,
	1048578,
	42);
INSERT INTO GD_CON
	VALUES (1048594,
	1048578,
	1048579,
	0);
INSERT INTO GD_CTXT
	VALUES (1048594,
	0,
	0,
	0,
	0,
	0,
	0,
	1792,
	1376,
	1842,
	1406,
	0,
	7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048595,
	1048594,
	1808,
	1360,
	1808,
	1408,
	0);
INSERT INTO GD_GE
	VALUES (1048596,
	1048577,
	1048579,
	42);
INSERT INTO GD_CON
	VALUES (1048596,
	1048579,
	1048580,
	0);
INSERT INTO GD_CTXT
	VALUES (1048596,
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
	VALUES (1048597,
	1048596,
	1808,
	1504,
	1808,
	1552,
	0);
INSERT INTO GD_GE
	VALUES (1048598,
	1048577,
	1048580,
	42);
INSERT INTO GD_CON
	VALUES (1048598,
	1048580,
	1048581,
	0);
INSERT INTO GD_CTXT
	VALUES (1048598,
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
	VALUES (1048599,
	1048598,
	1808,
	1648,
	1808,
	1696,
	0);
INSERT INTO GD_GE
	VALUES (1048600,
	1048577,
	1048581,
	42);
INSERT INTO GD_CON
	VALUES (1048600,
	1048581,
	1048582,
	0);
INSERT INTO GD_CTXT
	VALUES (1048600,
	0,
	0,
	0,
	0,
	0,
	0,
	1936,
	1506,
	1986,
	1536,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048601,
	1048600,
	1920,
	1744,
	1952,
	1744,
	0);
INSERT INTO GD_LS
	VALUES (1048602,
	1048600,
	1952,
	1744,
	1952,
	1312,
	1048601);
INSERT INTO GD_LS
	VALUES (1048603,
	1048600,
	1952,
	1312,
	1984,
	1312,
	1048602);
INSERT INTO GD_GE
	VALUES (1048604,
	1048577,
	1048582,
	42);
INSERT INTO GD_CON
	VALUES (1048604,
	1048582,
	1048583,
	0);
INSERT INTO GD_CTXT
	VALUES (1048604,
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
	VALUES (1048605,
	1048604,
	2096,
	1360,
	2096,
	1408,
	0);
INSERT INTO GD_GE
	VALUES (1048606,
	1048577,
	1048583,
	42);
INSERT INTO GD_CON
	VALUES (1048606,
	1048583,
	1048584,
	0);
INSERT INTO GD_CTXT
	VALUES (1048606,
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
	VALUES (1048607,
	1048606,
	2096,
	1504,
	2096,
	1552,
	0);
INSERT INTO GD_GE
	VALUES (1048608,
	1048577,
	1048584,
	42);
INSERT INTO GD_CON
	VALUES (1048608,
	1048584,
	1048585,
	0);
INSERT INTO GD_CTXT
	VALUES (1048608,
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
	VALUES (1048609,
	1048608,
	2096,
	1648,
	2096,
	1696,
	0);
INSERT INTO GD_GE
	VALUES (1048610,
	1048577,
	1048585,
	42);
INSERT INTO GD_CON
	VALUES (1048610,
	1048585,
	1048586,
	0);
INSERT INTO GD_CTXT
	VALUES (1048610,
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
	VALUES (1048611,
	1048610,
	2192,
	1744,
	2224,
	1744,
	0);
INSERT INTO GD_LS
	VALUES (1048612,
	1048610,
	2224,
	1744,
	2224,
	1312,
	1048611);
INSERT INTO GD_LS
	VALUES (1048613,
	1048610,
	2224,
	1312,
	2256,
	1312,
	1048612);
INSERT INTO GD_GE
	VALUES (1048614,
	1048577,
	1048586,
	42);
INSERT INTO GD_CON
	VALUES (1048614,
	1048586,
	1048587,
	0);
INSERT INTO GD_CTXT
	VALUES (1048614,
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
	VALUES (1048615,
	1048614,
	2352,
	1360,
	2352,
	1408,
	0);
INSERT INTO GD_GE
	VALUES (1048616,
	1048577,
	1048587,
	42);
INSERT INTO GD_CON
	VALUES (1048616,
	1048587,
	1048588,
	0);
INSERT INTO GD_CTXT
	VALUES (1048616,
	0,
	0,
	0,
	0,
	0,
	0,
	2336,
	1516,
	2493,
	1550,
	0,
	3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048617,
	1048616,
	2352,
	1504,
	2352,
	1552,
	0);
INSERT INTO O_OBJ
	VALUES (2621442,
	'cl init',
	2,
	'INIT',
	'',
	2621445);
INSERT INTO O_NBATTR
	VALUES (2621444,
	2621442);
INSERT INTO O_BATTR
	VALUES (2621444,
	2621442);
INSERT INTO O_ATTR
	VALUES (2621444,
	2621442,
	0,
	'id',
	'',
	'',
	'id',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (2621445,
	2621442);
INSERT INTO O_BATTR
	VALUES (2621445,
	2621442);
INSERT INTO O_ATTR
	VALUES (2621445,
	2621442,
	2621444,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	2621442);
INSERT INTO O_OIDA
	VALUES (2621444,
	2621442,
	0);
INSERT INTO SM_ISM
	VALUES (1572867,
	2621442);
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
	'Initialize System',
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
	'Init',
	0,
	'',
	'INIT1',
	'');
INSERT INTO SM_SEME
	VALUES (1572865,
	1572865,
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
	'create object instance dr of DR;
generate DR1:''start test'' to dr;
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
	1824,
	1360,
	2080,
	1600);
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
	2160,
	1318,
	2222,
	1340,
	121,
	-33,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572868,
	1572867,
	2080,
	1424,
	2144,
	1424,
	0);
INSERT INTO GD_LS
	VALUES (1572869,
	1572867,
	2144,
	1424,
	2144,
	1296,
	1572868);
INSERT INTO GD_LS
	VALUES (1572870,
	1572867,
	2144,
	1296,
	2016,
	1296,
	1572869);
INSERT INTO GD_LS
	VALUES (1572871,
	1572867,
	2016,
	1296,
	2016,
	1360,
	1572870);
INSERT INTO O_OBJ
	VALUES (2621443,
	'A',
	3,
	'A',
	'',
	2621445);
INSERT INTO O_NBATTR
	VALUES (2621446,
	2621443);
INSERT INTO O_BATTR
	VALUES (2621446,
	2621443);
INSERT INTO O_ATTR
	VALUES (2621446,
	2621443,
	0,
	'a_id',
	'',
	'',
	'a_id',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (2621447,
	2621443);
INSERT INTO O_BATTR
	VALUES (2621447,
	2621443);
INSERT INTO O_ATTR
	VALUES (2621447,
	2621443,
	2621446,
	'val',
	'',
	'',
	'val',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	2621443);
INSERT INTO O_OIDA
	VALUES (2621446,
	2621443,
	0);
INSERT INTO GD_MD
	VALUES (2621454,
	5,
	2621445,
	11,
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
	VALUES (2621457,
	2621454,
	2621441,
	21);
INSERT INTO GD_SHP
	VALUES (2621457,
	1696,
	1280,
	1952,
	1424);
INSERT INTO GD_GE
	VALUES (2621458,
	2621454,
	2621442,
	21);
INSERT INTO GD_SHP
	VALUES (2621458,
	1984,
	1280,
	2240,
	1424);
INSERT INTO GD_GE
	VALUES (2621459,
	2621454,
	2621443,
	21);
INSERT INTO GD_SHP
	VALUES (2621459,
	1696,
	1456,
	1952,
	1600);
INSERT INTO GD_MD
	VALUES (2621455,
	6,
	2621445,
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
	VALUES (2621460,
	2621455,
	2621441,
	21);
INSERT INTO GD_SHP
	VALUES (2621460,
	1760,
	1280,
	1952,
	1344);
INSERT INTO GD_GE
	VALUES (2621461,
	2621455,
	2621442,
	21);
INSERT INTO GD_SHP
	VALUES (2621461,
	2000,
	1280,
	2192,
	1344);
INSERT INTO GD_MD
	VALUES (2621456,
	7,
	2621445,
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
	VALUES (2621462,
	2621456,
	2621441,
	21);
INSERT INTO GD_SHP
	VALUES (2621462,
	1760,
	1280,
	1952,
	1344);
INSERT INTO GD_GE
	VALUES (2621463,
	2621456,
	2621442,
	21);
INSERT INTO GD_SHP
	VALUES (2621463,
	2000,
	1280,
	2192,
	1344);
INSERT INTO GD_GE
	VALUES (2621464,
	2621456,
	2621443,
	21);
INSERT INTO GD_SHP
	VALUES (2621464,
	1760,
	1472,
	1952,
	1536);
