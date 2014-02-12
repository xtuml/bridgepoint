/*----------------------------------------------------------------------------
 * File:  MicrowaveOven_MO_TS_class.c
 *
 * Class:       Test Sequences  (MO_TS)
 * Component:   MicrowaveOven
 *
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/

#include "MicrowaveOven_sys_types.h"
#include "TIM_bridge.h"
#include "MicrowaveOven_ARCH_bridge.h"
#include "MicrowaveOven_classes.h"


/*
 * Statically allocate space for the instance population for this class.
 * Allocate space for the class instance and its attribute values.
 * Depending upon the collection scheme, allocate containoids (collection
 * nodes) for gathering instances into free and active extents.
 */
static Escher_SetElement_s MicrowaveOven_MO_TS_container[ MicrowaveOven_MO_TS_MAX_EXTENT_SIZE ];
static MicrowaveOven_MO_TS MicrowaveOven_MO_TS_instances[ MicrowaveOven_MO_TS_MAX_EXTENT_SIZE ];
Escher_Extent_t pG_MicrowaveOven_MO_TS_extent = {
  {0}, {0}, &MicrowaveOven_MO_TS_container[ 0 ],
  (Escher_iHandle_t) &MicrowaveOven_MO_TS_instances,
  sizeof( MicrowaveOven_MO_TS ), MicrowaveOven_MO_TS_STATE_1, MicrowaveOven_MO_TS_MAX_EXTENT_SIZE
  };
/*----------------------------------------------------------------------------
 * State and transition action implementations for the following class:
 *
 * Class:      Test Sequences  (MO_TS)
 * Component:  MicrowaveOven
 *--------------------------------------------------------------------------*/

/*
 * State 1:  [Awaiting Test Sequence Initiation]
 */
static void MicrowaveOven_MO_TS_act1( MicrowaveOven_MO_TS *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_TS_act1( MicrowaveOven_MO_TS * self, const Escher_xtUMLEvent_t * const event )
{
  /* GENERATE MO_TS2:perform_test_seq_1() TO self */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_TS2:perform_test_seq_1() TO self" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( self, &MicrowaveOven_MO_TSevent2c );
    Escher_SendSelfEvent( e );
  }
}

/*
 * State 2:  [Performing Test Sequence 1]
 */
static void MicrowaveOven_MO_TS_act2( MicrowaveOven_MO_TS *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_TS_act2( MicrowaveOven_MO_TS * self, const Escher_xtUMLEvent_t * const event )
{
  MicrowaveOven_MO_D * door; MicrowaveOven_MO_MT * tube; MicrowaveOven_MO_O * oven; Escher_xtUMLEvent_t * finished;  /* finished */ Escher_Timer_t * terminate_timer; 
  /* SELECT any door FROM INSTANCES OF MO_D */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any door FROM INSTANCES OF MO_D" );
  door = (MicrowaveOven_MO_D *) Escher_SetGetAny( &pG_MicrowaveOven_MO_D_extent.active );
  /* IF ( not_empty door ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not_empty door )" );
  if ( ( 0 != door ) ) {
    Escher_xtUMLEvent_t * release_door;  /* release_door */ Escher_Timer_t * step1_timer; 
    /* CREATE EVENT INSTANCE release_door(  ) TO door */
    XTUML_OAL_STMT_TRACE( 2, "CREATE EVENT INSTANCE release_door(  ) TO door" );
    release_door = Escher_NewxtUMLEvent( (void *) door, &MicrowaveOven_MO_Devent1c );
    /* ASSIGN step1_timer = TIM::timer_start(event_inst:release_door, microseconds:2000000) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN step1_timer = TIM::timer_start(event_inst:release_door, microseconds:2000000)" );
    step1_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)release_door, 2000000 );
  }
  /* SELECT any tube FROM INSTANCES OF MO_MT */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any tube FROM INSTANCES OF MO_MT" );
  tube = (MicrowaveOven_MO_MT *) Escher_SetGetAny( &pG_MicrowaveOven_MO_MT_extent.active );
  /* IF ( not_empty tube ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not_empty tube )" );
  if ( ( 0 != tube ) ) {
    Escher_xtUMLEvent_t * lower_power;  /* lower_power */ Escher_Timer_t * step2_timer; 
    /* CREATE EVENT INSTANCE lower_power(  ) TO tube */
    XTUML_OAL_STMT_TRACE( 2, "CREATE EVENT INSTANCE lower_power(  ) TO tube" );
    lower_power = Escher_NewxtUMLEvent( (void *) tube, &MicrowaveOven_MO_MTevent2c );
    /* ASSIGN step2_timer = TIM::timer_start(event_inst:lower_power, microseconds:3000000) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN step2_timer = TIM::timer_start(event_inst:lower_power, microseconds:3000000)" );
    step2_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)lower_power, 3000000 );
  }
  /* SELECT any door FROM INSTANCES OF MO_D */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any door FROM INSTANCES OF MO_D" );
  door = (MicrowaveOven_MO_D *) Escher_SetGetAny( &pG_MicrowaveOven_MO_D_extent.active );
  /* IF ( not_empty door ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not_empty door )" );
  if ( ( 0 != door ) ) {
    Escher_xtUMLEvent_t * close_door;  /* close_door */ Escher_Timer_t * step3_timer; 
    /* CREATE EVENT INSTANCE close_door(  ) TO door */
    XTUML_OAL_STMT_TRACE( 2, "CREATE EVENT INSTANCE close_door(  ) TO door" );
    close_door = Escher_NewxtUMLEvent( (void *) door, &MicrowaveOven_MO_Devent2c );
    /* ASSIGN step3_timer = TIM::timer_start(event_inst:close_door, microseconds:4000000) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN step3_timer = TIM::timer_start(event_inst:close_door, microseconds:4000000)" );
    step3_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)close_door, 4000000 );
  }
  /* SELECT any oven FROM INSTANCES OF MO_O */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any oven FROM INSTANCES OF MO_O" );
  oven = (MicrowaveOven_MO_O *) Escher_SetGetAny( &pG_MicrowaveOven_MO_O_extent.active );
  /* IF ( not_empty oven ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not_empty oven )" );
  if ( ( 0 != oven ) ) {
    MicrowaveOven_MO_Oevent8 * cooking_time;  /* cooking_time */ Escher_Timer_t * step4a_timer; Escher_xtUMLEvent_t * start;  /* start */ Escher_Timer_t * step4b_timer; 
    /* CREATE EVENT INSTANCE cooking_time( period:10000000 ) TO oven */
    XTUML_OAL_STMT_TRACE( 2, "CREATE EVENT INSTANCE cooking_time( period:10000000 ) TO oven" );
    cooking_time = (MicrowaveOven_MO_Oevent8 *) Escher_NewxtUMLEvent( (void *) oven, &MicrowaveOven_MO_Oevent8c );
          cooking_time->p_period = 10000000;    /* ASSIGN step4a_timer = TIM::timer_start(event_inst:cooking_time, microseconds:5000000) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN step4a_timer = TIM::timer_start(event_inst:cooking_time, microseconds:5000000)" );
    step4a_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)cooking_time, 5000000 );
    /* CREATE EVENT INSTANCE start(  ) TO oven */
    XTUML_OAL_STMT_TRACE( 2, "CREATE EVENT INSTANCE start(  ) TO oven" );
    start = Escher_NewxtUMLEvent( (void *) oven, &MicrowaveOven_MO_Oevent3c );
    /* ASSIGN step4b_timer = TIM::timer_start(event_inst:start, microseconds:5000001) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN step4b_timer = TIM::timer_start(event_inst:start, microseconds:5000001)" );
    step4b_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)start, 5000001 );
  }
  /* SELECT any door FROM INSTANCES OF MO_D */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any door FROM INSTANCES OF MO_D" );
  door = (MicrowaveOven_MO_D *) Escher_SetGetAny( &pG_MicrowaveOven_MO_D_extent.active );
  /* IF ( not_empty door ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not_empty door )" );
  if ( ( 0 != door ) ) {
    Escher_xtUMLEvent_t * release_door;  /* release_door */ Escher_Timer_t * step5_timer; 
    /* CREATE EVENT INSTANCE release_door(  ) TO door */
    XTUML_OAL_STMT_TRACE( 2, "CREATE EVENT INSTANCE release_door(  ) TO door" );
    release_door = Escher_NewxtUMLEvent( (void *) door, &MicrowaveOven_MO_Devent1c );
    /* ASSIGN step5_timer = TIM::timer_start(event_inst:release_door, microseconds:15000000) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN step5_timer = TIM::timer_start(event_inst:release_door, microseconds:15000000)" );
    step5_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)release_door, 15000000 );
  }
  /* CREATE EVENT INSTANCE finished(  ) TO self */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE finished(  ) TO self" );
  finished = Escher_NewxtUMLEvent( (void *) self, &MicrowaveOven_MO_TSevent4c );
  /* ASSIGN terminate_timer = TIM::timer_start(event_inst:finished, microseconds:30000000) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN terminate_timer = TIM::timer_start(event_inst:finished, microseconds:30000000)" );
  terminate_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)finished, 30000000 );
}

/*
 * State 3:  [Performing Test Sequence 2]
 */
static void MicrowaveOven_MO_TS_act3( MicrowaveOven_MO_TS *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_TS_act3( MicrowaveOven_MO_TS * self, const Escher_xtUMLEvent_t * const event )
{
  MicrowaveOven_MO_D * door; MicrowaveOven_MO_O * oven; MicrowaveOven_MO_MT * tube; Escher_xtUMLEvent_t * finished;  /* finished */ Escher_Timer_t * terminate_timer; 
  /* SELECT any door FROM INSTANCES OF MO_D */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any door FROM INSTANCES OF MO_D" );
  door = (MicrowaveOven_MO_D *) Escher_SetGetAny( &pG_MicrowaveOven_MO_D_extent.active );
  /* IF ( not_empty door ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not_empty door )" );
  if ( ( 0 != door ) ) {
    Escher_xtUMLEvent_t * release_door;  /* release_door */ Escher_Timer_t * step1_timer; 
    /* CREATE EVENT INSTANCE release_door(  ) TO door */
    XTUML_OAL_STMT_TRACE( 2, "CREATE EVENT INSTANCE release_door(  ) TO door" );
    release_door = Escher_NewxtUMLEvent( (void *) door, &MicrowaveOven_MO_Devent1c );
    /* ASSIGN step1_timer = TIM::timer_start(event_inst:release_door, microseconds:2000000) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN step1_timer = TIM::timer_start(event_inst:release_door, microseconds:2000000)" );
    step1_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)release_door, 2000000 );
  }
  /* SELECT any door FROM INSTANCES OF MO_D */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any door FROM INSTANCES OF MO_D" );
  door = (MicrowaveOven_MO_D *) Escher_SetGetAny( &pG_MicrowaveOven_MO_D_extent.active );
  /* IF ( not_empty door ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not_empty door )" );
  if ( ( 0 != door ) ) {
    Escher_xtUMLEvent_t * close_door;  /* close_door */ Escher_Timer_t * step2_timer; 
    /* CREATE EVENT INSTANCE close_door(  ) TO door */
    XTUML_OAL_STMT_TRACE( 2, "CREATE EVENT INSTANCE close_door(  ) TO door" );
    close_door = Escher_NewxtUMLEvent( (void *) door, &MicrowaveOven_MO_Devent2c );
    /* ASSIGN step2_timer = TIM::timer_start(event_inst:close_door, microseconds:4000000) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN step2_timer = TIM::timer_start(event_inst:close_door, microseconds:4000000)" );
    step2_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)close_door, 4000000 );
  }
  /* SELECT any oven FROM INSTANCES OF MO_O */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any oven FROM INSTANCES OF MO_O" );
  oven = (MicrowaveOven_MO_O *) Escher_SetGetAny( &pG_MicrowaveOven_MO_O_extent.active );
  /* IF ( not_empty oven ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not_empty oven )" );
  if ( ( 0 != oven ) ) {
    MicrowaveOven_MO_Oevent8 * cooking_time;  /* cooking_time */ Escher_Timer_t * step3a_timer; Escher_xtUMLEvent_t * start;  /* start */ Escher_Timer_t * step3b_timer; 
    /* CREATE EVENT INSTANCE cooking_time( period:15000000 ) TO oven */
    XTUML_OAL_STMT_TRACE( 2, "CREATE EVENT INSTANCE cooking_time( period:15000000 ) TO oven" );
    cooking_time = (MicrowaveOven_MO_Oevent8 *) Escher_NewxtUMLEvent( (void *) oven, &MicrowaveOven_MO_Oevent8c );
          cooking_time->p_period = 15000000;    /* ASSIGN step3a_timer = TIM::timer_start(event_inst:cooking_time, microseconds:5000000) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN step3a_timer = TIM::timer_start(event_inst:cooking_time, microseconds:5000000)" );
    step3a_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)cooking_time, 5000000 );
    /* CREATE EVENT INSTANCE start(  ) TO oven */
    XTUML_OAL_STMT_TRACE( 2, "CREATE EVENT INSTANCE start(  ) TO oven" );
    start = Escher_NewxtUMLEvent( (void *) oven, &MicrowaveOven_MO_Oevent3c );
    /* ASSIGN step3b_timer = TIM::timer_start(event_inst:start, microseconds:5000001) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN step3b_timer = TIM::timer_start(event_inst:start, microseconds:5000001)" );
    step3b_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)start, 5000001 );
  }
  /* SELECT any door FROM INSTANCES OF MO_D */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any door FROM INSTANCES OF MO_D" );
  door = (MicrowaveOven_MO_D *) Escher_SetGetAny( &pG_MicrowaveOven_MO_D_extent.active );
  /* IF ( not_empty door ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not_empty door )" );
  if ( ( 0 != door ) ) {
    Escher_xtUMLEvent_t * release_door;  /* release_door */ Escher_Timer_t * step4_timer; 
    /* CREATE EVENT INSTANCE release_door(  ) TO door */
    XTUML_OAL_STMT_TRACE( 2, "CREATE EVENT INSTANCE release_door(  ) TO door" );
    release_door = Escher_NewxtUMLEvent( (void *) door, &MicrowaveOven_MO_Devent1c );
    /* ASSIGN step4_timer = TIM::timer_start(event_inst:release_door, microseconds:6000000) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN step4_timer = TIM::timer_start(event_inst:release_door, microseconds:6000000)" );
    step4_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)release_door, 6000000 );
  }
  /* SELECT any tube FROM INSTANCES OF MO_MT */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any tube FROM INSTANCES OF MO_MT" );
  tube = (MicrowaveOven_MO_MT *) Escher_SetGetAny( &pG_MicrowaveOven_MO_MT_extent.active );
  /* IF ( not_empty tube ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not_empty tube )" );
  if ( ( 0 != tube ) ) {
    Escher_xtUMLEvent_t * lower_power;  /* lower_power */ Escher_Timer_t * step5a_timer; Escher_Timer_t * step5b_timer; 
    /* CREATE EVENT INSTANCE lower_power(  ) TO tube */
    XTUML_OAL_STMT_TRACE( 2, "CREATE EVENT INSTANCE lower_power(  ) TO tube" );
    lower_power = Escher_NewxtUMLEvent( (void *) tube, &MicrowaveOven_MO_MTevent2c );
    /* ASSIGN step5a_timer = TIM::timer_start(event_inst:lower_power, microseconds:3000000) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN step5a_timer = TIM::timer_start(event_inst:lower_power, microseconds:3000000)" );
    step5a_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)lower_power, 3000000 );
    /* ASSIGN step5b_timer = TIM::timer_start(event_inst:lower_power, microseconds:3000001) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN step5b_timer = TIM::timer_start(event_inst:lower_power, microseconds:3000001)" );
    step5b_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)lower_power, 3000001 );
  }
  /* SELECT any door FROM INSTANCES OF MO_D */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any door FROM INSTANCES OF MO_D" );
  door = (MicrowaveOven_MO_D *) Escher_SetGetAny( &pG_MicrowaveOven_MO_D_extent.active );
  /* IF ( not_empty door ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not_empty door )" );
  if ( ( 0 != door ) ) {
    Escher_xtUMLEvent_t * close_door;  /* close_door */ Escher_Timer_t * step6_timer; 
    /* CREATE EVENT INSTANCE close_door(  ) TO door */
    XTUML_OAL_STMT_TRACE( 2, "CREATE EVENT INSTANCE close_door(  ) TO door" );
    close_door = Escher_NewxtUMLEvent( (void *) door, &MicrowaveOven_MO_Devent2c );
    /* ASSIGN step6_timer = TIM::timer_start(event_inst:close_door, microseconds:9000000) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN step6_timer = TIM::timer_start(event_inst:close_door, microseconds:9000000)" );
    step6_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)close_door, 9000000 );
  }
  /* SELECT any oven FROM INSTANCES OF MO_O */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any oven FROM INSTANCES OF MO_O" );
  oven = (MicrowaveOven_MO_O *) Escher_SetGetAny( &pG_MicrowaveOven_MO_O_extent.active );
  /* IF ( not_empty oven ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not_empty oven )" );
  if ( ( 0 != oven ) ) {
    Escher_xtUMLEvent_t * restart;  /* restart */ Escher_Timer_t * step7_timer; 
    /* CREATE EVENT INSTANCE restart(  ) TO oven */
    XTUML_OAL_STMT_TRACE( 2, "CREATE EVENT INSTANCE restart(  ) TO oven" );
    restart = Escher_NewxtUMLEvent( (void *) oven, &MicrowaveOven_MO_Oevent3c );
    /* ASSIGN step7_timer = TIM::timer_start(event_inst:restart, microseconds:10000000) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN step7_timer = TIM::timer_start(event_inst:restart, microseconds:10000000)" );
    step7_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)restart, 10000000 );
  }
  /* CREATE EVENT INSTANCE finished(  ) TO self */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE finished(  ) TO self" );
  finished = Escher_NewxtUMLEvent( (void *) self, &MicrowaveOven_MO_TSevent4c );
  /* ASSIGN terminate_timer = TIM::timer_start(event_inst:finished, microseconds:30000000) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN terminate_timer = TIM::timer_start(event_inst:finished, microseconds:30000000)" );
  terminate_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)finished, 30000000 );
}

/*
 * State 4:  [Cooking Complete]
 */
static void MicrowaveOven_MO_TS_act4( MicrowaveOven_MO_TS *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_TS_act4( MicrowaveOven_MO_TS * self, const Escher_xtUMLEvent_t * const event )
{
  /* ARCH::shutdown(  ) */
  XTUML_OAL_STMT_TRACE( 1, "ARCH::shutdown(  )" );
  MicrowaveOven_ARCH_shutdown();
}

const Escher_xtUMLEventConstant_t MicrowaveOven_MO_TSevent2c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_TS_CLASS_NUMBER, MICROWAVEOVEN_MO_TSEVENT2NUM,
  ESCHER_IS_INSTANCE_EVENT };



const Escher_xtUMLEventConstant_t MicrowaveOven_MO_TSevent4c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_TS_CLASS_NUMBER, MICROWAVEOVEN_MO_TSEVENT4NUM,
  ESCHER_IS_INSTANCE_EVENT };



/*
 * State-Event Matrix (SEM)
 * Row index is object (MC enumerated) current state.
 * Row zero is the uninitialized state (e.g., for creation event transitions).
 * Column index is (MC enumerated) state machine events.
 */
static const Escher_SEMcell_t MicrowaveOven_MO_TS_StateEventMatrix[ 4 + 1 ][ 2 ] = {
  /* row 0:  uninitialized state (for creation events) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN },
  /* row 1:  MicrowaveOven_MO_TS_STATE_1 (Awaiting Test Sequence Initiation) */
  { MicrowaveOven_MO_TS_STATE_2, EVENT_IS_IGNORED },
  /* row 2:  MicrowaveOven_MO_TS_STATE_2 (Performing Test Sequence 1) */
  { EVENT_IS_IGNORED, MicrowaveOven_MO_TS_STATE_4 },
  /* row 3:  MicrowaveOven_MO_TS_STATE_3 (Performing Test Sequence 2) */
  { EVENT_IS_IGNORED, MicrowaveOven_MO_TS_STATE_4 },
  /* row 4:  MicrowaveOven_MO_TS_STATE_4 (Cooking Complete) */
  { EVENT_IS_IGNORED, EVENT_IS_IGNORED }
};

  /*
   * Array of pointers to the class state action procedures.
   * Index is the (MC enumerated) number of the state action to execute.
   */
  static const StateAction_t MicrowaveOven_MO_TS_acts[ 5 ] = {
    (StateAction_t) 0,
    (StateAction_t) MicrowaveOven_MO_TS_act1,  /* Awaiting Test Sequence Initiation */
    (StateAction_t) MicrowaveOven_MO_TS_act2,  /* Performing Test Sequence 1 */
    (StateAction_t) MicrowaveOven_MO_TS_act3,  /* Performing Test Sequence 2 */
    (StateAction_t) MicrowaveOven_MO_TS_act4  /* Cooking Complete */
  };

  /*
   * Array of string names of the state machine names.
   * Index is the (MC enumerated) number of the state.
   */
  static const c_t * const state_name_strings[ 5 ] = {
    "",
    "Awaiting Test Sequence Initiation",
    "Performing Test Sequence 1",
    "Performing Test Sequence 2",
    "Cooking Complete"
  };

/*
 * instance state machine event dispatching
 */
void
MicrowaveOven_MO_TS_Dispatch( Escher_xtUMLEvent_t * event )
{
  Escher_iHandle_t instance = GetEventTargetInstance( event );
  Escher_EventNumber_t event_number = GetOoaEventNumber( event );
  Escher_StateNumber_t current_state;
  Escher_StateNumber_t next_state;
  
  if ( 0 != instance ) {
    current_state = instance->current_state;
    if ( current_state > 4 ) {
      /* instance validation failure (object deleted while event in flight) */
      UserEventNoInstanceCallout( event_number );
    } else {
      next_state = MicrowaveOven_MO_TS_StateEventMatrix[ current_state ][ event_number ];
      if ( next_state <= 4 ) {
        STATE_TXN_START_TRACE( "MO_TS", current_state, state_name_strings[ current_state ] );
        /* Execute the state action and update the current state.  */
        ( *MicrowaveOven_MO_TS_acts[ next_state ] )( instance, event );
        STATE_TXN_END_TRACE( "MO_TS", next_state, state_name_strings[ next_state ] );
        instance->current_state = next_state;
      } else if ( next_state == EVENT_IS_IGNORED ) {
          /* event ignored */
          STATE_TXN_IG_TRACE( "MO_TS", current_state );
      } else {
        /* empty else */
      }
    }
  }
}


