/*----------------------------------------------------------------------------
 * File:  pacemaker_PACER_class.c
 *
 * Class:       pacer  (PACER)
 * Component:   pacemaker
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "avpace_sys_types.h"
#include "pacemaker_LOG_bridge.h"
#include "TIM_bridge.h"
#include "pacemaker_classes.h"


/*
 * Statically allocate space for the instance population for this class.
 * Allocate space for the class instance and its attribute values.
 * Depending upon the collection scheme, allocate containoids (collection
 * nodes) for gathering instances into free and active extents.
 */
static Escher_SetElement_s pacemaker_PACER_container[ pacemaker_PACER_MAX_EXTENT_SIZE ];
static pacemaker_PACER pacemaker_PACER_instances[ pacemaker_PACER_MAX_EXTENT_SIZE ];
Escher_Extent_t pG_pacemaker_PACER_extent = {
  {0}, {0}, &pacemaker_PACER_container[ 0 ],
  (Escher_iHandle_t) &pacemaker_PACER_instances,
  sizeof( pacemaker_PACER ), pacemaker_PACER_STATE_1, pacemaker_PACER_MAX_EXTENT_SIZE
  };
/*----------------------------------------------------------------------------
 * State and transition action implementations for the following class:
 *
 * Class:      pacer  (PACER)
 * Component:  pacemaker
 *--------------------------------------------------------------------------*/

/*
 * State 1:  [syncing]
 */
static void pacemaker_PACER_act1( pacemaker_PACER *, const Escher_xtUMLEvent_t * const );
static void
pacemaker_PACER_act1( pacemaker_PACER * self, const Escher_xtUMLEvent_t * const event )
{
}

/*
 * State 2:  [listening for systolic pulse]
 */
static void pacemaker_PACER_act2( pacemaker_PACER *, const Escher_xtUMLEvent_t * const );
static void
pacemaker_PACER_act2( pacemaker_PACER * self, const Escher_xtUMLEvent_t * const event )
{
  bool existed;Escher_xtUMLEvent_t * timeout;
  /* ASSIGN existed = TIM::timer_cancel(timer_inst_ref:self.timeout_timer) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN existed = TIM::timer_cancel(timer_inst_ref:self.timeout_timer)" );
  existed = TIM_timer_cancel( self->timeout_timer );
  /* CREATE EVENT INSTANCE timeout(  ) TO self */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE timeout(  ) TO self" );
  timeout = Escher_NewxtUMLEvent( (void *) self, &pacemaker_PACERevent3c );
  /* ASSIGN self.timeout_timer = TIM::timer_start(event_inst:timeout, microseconds:self.systolic_timeout) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.timeout_timer = TIM::timer_start(event_inst:timeout, microseconds:self.systolic_timeout)" );
  self->timeout_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)timeout, self->systolic_timeout );
  /* ASSIGN self.cycle_count = ( self.cycle_count + 1 ) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.cycle_count = ( self.cycle_count + 1 )" );
  self->cycle_count = ( self->cycle_count + 1 );
  /* LOG::LogInfo( message:cycle count ) */
  XTUML_OAL_STMT_TRACE( 1, "LOG::LogInfo( message:cycle count )" );
  pacemaker_LOG_LogInfo( "cycle count" );
  /* LOG::LogInteger( message:self.cycle_count ) */
  XTUML_OAL_STMT_TRACE( 1, "LOG::LogInteger( message:self.cycle_count )" );
  pacemaker_LOG_LogInteger( self->cycle_count );
}

/*
 * State 3:  [listening for diastolic pulse]
 */
static void pacemaker_PACER_act3( pacemaker_PACER *, const Escher_xtUMLEvent_t * const );
static void
pacemaker_PACER_act3( pacemaker_PACER * self, const Escher_xtUMLEvent_t * const event )
{
  bool existed;Escher_xtUMLEvent_t * timeout;
  /* ASSIGN existed = TIM::timer_cancel(timer_inst_ref:self.timeout_timer) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN existed = TIM::timer_cancel(timer_inst_ref:self.timeout_timer)" );
  existed = TIM_timer_cancel( self->timeout_timer );
  /* CREATE EVENT INSTANCE timeout(  ) TO self */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE timeout(  ) TO self" );
  timeout = Escher_NewxtUMLEvent( (void *) self, &pacemaker_PACERevent3c );
  /* ASSIGN self.timeout_timer = TIM::timer_start(event_inst:timeout, microseconds:self.diastolic_timeout) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.timeout_timer = TIM::timer_start(event_inst:timeout, microseconds:self.diastolic_timeout)" );
  self->timeout_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)timeout, self->diastolic_timeout );
}

/*
 * State 4:  [Increasing rate]
 */
static void pacemaker_PACER_act4( pacemaker_PACER *, const Escher_xtUMLEvent_t * const );
static void
pacemaker_PACER_act4( pacemaker_PACER * self, const Escher_xtUMLEvent_t * const event )
{
  /* ASSIGN self.systolic_timeout = ( self.systolic_timeout + 1 ) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.systolic_timeout = ( self.systolic_timeout + 1 )" );
  self->systolic_timeout = ( self->systolic_timeout + 1 );
  /* ASSIGN self.diastolic_timeout = ( self.diastolic_timeout + 1 ) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.diastolic_timeout = ( self.diastolic_timeout + 1 )" );
  self->diastolic_timeout = ( self->diastolic_timeout + 1 );
  /* GENERATE PACER5:rate_increased() TO self */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE PACER5:rate_increased() TO self" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( self, &pacemaker_PACERevent5c );
    Escher_SendSelfEvent( e );
  }
}

/*
 */
static void pacemaker_PACER_xact1( pacemaker_PACER *, const Escher_xtUMLEvent_t * const );
static void
pacemaker_PACER_xact1( pacemaker_PACER * self, const Escher_xtUMLEvent_t * const event )
{
  /* SEND to_heart::diastolic_pace() */
  XTUML_OAL_STMT_TRACE( 1, "SEND to_heart::diastolic_pace()" );
  pacemaker_to_heart_diastolic_pace();
}

/*
 */
static void pacemaker_PACER_xact2( pacemaker_PACER *, const Escher_xtUMLEvent_t * const );
static void
pacemaker_PACER_xact2( pacemaker_PACER * self, const Escher_xtUMLEvent_t * const event )
{
  /* SEND to_heart::systolic_pace() */
  XTUML_OAL_STMT_TRACE( 1, "SEND to_heart::systolic_pace()" );
  pacemaker_to_heart_systolic_pace();
}

/*
 * State 1:  [monitoring host]
 */
static void pacemaker_PACER_CB_act1( pacemaker_PACER *, const Escher_xtUMLEvent_t * const );
static void
pacemaker_PACER_CB_act1( pacemaker_PACER * self, const Escher_xtUMLEvent_t * const event )
{
}

/*
 */
static void pacemaker_PACER_CB_xact1( pacemaker_PACER *, const Escher_xtUMLEvent_t * const );
static void
pacemaker_PACER_CB_xact1( pacemaker_PACER * self, const Escher_xtUMLEvent_t * const event )
{
  pacemaker_PACER * pacer=0;
  /* SELECT any pacer FROM INSTANCES OF PACER */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any pacer FROM INSTANCES OF PACER" );
  pacer = (pacemaker_PACER *) Escher_SetGetAny( &pG_pacemaker_PACER_extent.active );
  /* GENERATE PACER2:diastolic_pulse() TO pacer */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE PACER2:diastolic_pulse() TO pacer" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( pacer, &pacemaker_PACERevent2c );
    Escher_SendEvent( e );
  }
}

/*
 */
static void pacemaker_PACER_CB_xact2( pacemaker_PACER *, const Escher_xtUMLEvent_t * const );
static void
pacemaker_PACER_CB_xact2( pacemaker_PACER * self, const Escher_xtUMLEvent_t * const event )
{
  pacemaker_PACER * pacer=0;
  /* SELECT any pacer FROM INSTANCES OF PACER */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any pacer FROM INSTANCES OF PACER" );
  pacer = (pacemaker_PACER *) Escher_SetGetAny( &pG_pacemaker_PACER_extent.active );
  /* GENERATE PACER1:systolic_pulse() TO pacer */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE PACER1:systolic_pulse() TO pacer" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( pacer, &pacemaker_PACERevent1c );
    Escher_SendEvent( e );
  }
}

/*
 */
static void pacemaker_PACER_CB_xact3( pacemaker_PACER *, const Escher_xtUMLEvent_t * const );
static void
pacemaker_PACER_CB_xact3( pacemaker_PACER * self, const Escher_xtUMLEvent_t * const event )
{
  pacemaker_PACER_CBevent5 * rcvd_evt = (pacemaker_PACER_CBevent5 *) event;
  pacemaker_PACER * pacer=0;
  /* SELECT any pacer FROM INSTANCES OF PACER */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any pacer FROM INSTANCES OF PACER" );
  pacer = (pacemaker_PACER *) Escher_SetGetAny( &pG_pacemaker_PACER_extent.active );
  /* GENERATE PACER5:rate_increased() TO pacer */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE PACER5:rate_increased() TO pacer" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( pacer, &pacemaker_PACERevent5c );
    Escher_SendEvent( e );
  }
}

const Escher_xtUMLEventConstant_t pacemaker_PACER_CBevent3c = {
  pacemaker_DOMAIN_ID, pacemaker_PACER_CLASS_NUMBER_CB, PACEMAKER_PACER_CBEVENT3NUM,
  ESCHER_IS_ASSIGNER_EVENT };

const Escher_xtUMLEventConstant_t pacemaker_PACER_CBevent4c = {
  pacemaker_DOMAIN_ID, pacemaker_PACER_CLASS_NUMBER_CB, PACEMAKER_PACER_CBEVENT4NUM,
  ESCHER_IS_ASSIGNER_EVENT };

const Escher_xtUMLEventConstant_t pacemaker_PACER_CBevent5c = {
  pacemaker_DOMAIN_ID, pacemaker_PACER_CLASS_NUMBER_CB, PACEMAKER_PACER_CBEVENT5NUM,
  ESCHER_IS_ASSIGNER_EVENT };



/*
 * State-Event Matrix (SEM)
 * Row index is object (MC enumerated) current state.
 * Row zero is the uninitialized state (e.g., for creation event transitions).
 * Column index is (MC enumerated) state machine events.
 */
static const Escher_SEMcell_t pacemaker_PACER_CB_StateEventMatrix[ 1 + 1 ][ 3 ] = {
  /* row 0:  uninitialized state (for creation events) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN },
  /* row 1:  pacemaker_PACER_CB_STATE_1 (monitoring host) */
  { (3<<8) + pacemaker_PACER_CB_STATE_1, (2<<8) + pacemaker_PACER_CB_STATE_1, (1<<8) + pacemaker_PACER_CB_STATE_1 }
};

  /*
   * Array of pointers to the class state action procedures.
   * Index is the (MC enumerated) number of the state action to execute.
   */
  static const StateAction_t pacemaker_PACER_CB_acts[ 2 ] = {
    (StateAction_t) 0,
    (StateAction_t) pacemaker_PACER_CB_act1  /* monitoring host */
  };

  /*
   * Array of string names of the state machine names.
   * Index is the (MC enumerated) number of the state.
   */
  static const c_t * const state_name_strings_CB[ 2 ] = {
    "",
    "monitoring host"
  };

  /*
   * Array of pointers to the class transition action procedures.
   * Index is the (MC enumerated) number of the transition action to execute.
   */
  static const StateAction_t pacemaker_PACER_CB_xacts[ 3 ] = {
    (StateAction_t) pacemaker_PACER_CB_xact1,
    (StateAction_t) pacemaker_PACER_CB_xact2,
    (StateAction_t) pacemaker_PACER_CB_xact3
  };

/*
 * class-based state machine event dispatching
 */
void
pacemaker_PACER_CBDispatch( Escher_xtUMLEvent_t * event )
{
  static Escher_InstanceBase_t class_based_singleton = { pacemaker_PACER_CB_STATE_1 };
  Escher_EventNumber_t event_number = GetOoaEventNumber( event );
  Escher_StateNumber_t current_state = class_based_singleton.current_state;
  Escher_SEMcell_t next_state = pacemaker_PACER_CB_StateEventMatrix[ current_state ][ event_number ];

  if ( next_state <= 1 ) {
    STATE_TXN_START_TRACE( "PACER", current_state, state_name_strings_CB[ current_state ] );
    /* Execute the state action and update the current state.  */
    ( *pacemaker_PACER_CB_acts[ next_state ] )( &class_based_singleton, event );
    class_based_singleton.current_state = next_state;
    STATE_TXN_END_TRACE( "PACER", next_state, state_name_strings_CB[ next_state ] );
  } else {
    if ( EVENT_CANT_HAPPEN == next_state ) {
      /* Event cannot happen.  */
      UserEventCantHappenCallout( current_state, next_state, event_number );
      STATE_TXN_CH_TRACE( "PACER", current_state );
    } else if ( EVENT_IS_IGNORED == next_state ) {
      /* Event ignored */
      STATE_TXN_IG_TRACE( "PACER", current_state );
    } else {
      STATE_TXN_START_TRACE( "PACER", current_state, state_name_strings_CB[ current_state ] );
      ( *pacemaker_PACER_CB_xacts[ (next_state>>8)-1 ] )( &class_based_singleton, event );
      next_state = next_state & 0x00ff;
      class_based_singleton.current_state = next_state;
      ( *pacemaker_PACER_CB_acts[ next_state ] )( &class_based_singleton, event );
      STATE_TXN_END_TRACE( "PACER", next_state, state_name_strings_CB[ next_state ] );
    }
  }
}

const Escher_xtUMLEventConstant_t pacemaker_PACERevent1c = {
  pacemaker_DOMAIN_ID, pacemaker_PACER_CLASS_NUMBER, PACEMAKER_PACEREVENT1NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t pacemaker_PACERevent2c = {
  pacemaker_DOMAIN_ID, pacemaker_PACER_CLASS_NUMBER, PACEMAKER_PACEREVENT2NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t pacemaker_PACERevent3c = {
  pacemaker_DOMAIN_ID, pacemaker_PACER_CLASS_NUMBER, PACEMAKER_PACEREVENT3NUM,
  ESCHER_IS_INSTANCE_EVENT };


const Escher_xtUMLEventConstant_t pacemaker_PACERevent5c = {
  pacemaker_DOMAIN_ID, pacemaker_PACER_CLASS_NUMBER, PACEMAKER_PACEREVENT5NUM,
  ESCHER_IS_INSTANCE_EVENT };



/*
 * State-Event Matrix (SEM)
 * Row index is object (MC enumerated) current state.
 * Row zero is the uninitialized state (e.g., for creation event transitions).
 * Column index is (MC enumerated) state machine events.
 */
static const Escher_SEMcell_t pacemaker_PACER_StateEventMatrix[ 4 + 1 ][ 4 ] = {
  /* row 0:  uninitialized state (for creation events) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN },
  /* row 1:  pacemaker_PACER_STATE_1 (syncing) */
  { pacemaker_PACER_STATE_3, pacemaker_PACER_STATE_2, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN },
  /* row 2:  pacemaker_PACER_STATE_2 (listening for systolic pulse) */
  { pacemaker_PACER_STATE_3, EVENT_CANT_HAPPEN, (2<<8) + pacemaker_PACER_STATE_1, EVENT_CANT_HAPPEN },
  /* row 3:  pacemaker_PACER_STATE_3 (listening for diastolic pulse) */
  { EVENT_CANT_HAPPEN, pacemaker_PACER_STATE_2, (1<<8) + pacemaker_PACER_STATE_1, EVENT_CANT_HAPPEN },
  /* row 4:  pacemaker_PACER_STATE_4 (Increasing rate) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, pacemaker_PACER_STATE_1 }
};

  /*
   * Array of pointers to the class state action procedures.
   * Index is the (MC enumerated) number of the state action to execute.
   */
  static const StateAction_t pacemaker_PACER_acts[ 5 ] = {
    (StateAction_t) 0,
    (StateAction_t) pacemaker_PACER_act1,  /* syncing */
    (StateAction_t) pacemaker_PACER_act2,  /* listening for systolic pulse */
    (StateAction_t) pacemaker_PACER_act3,  /* listening for diastolic pulse */
    (StateAction_t) pacemaker_PACER_act4  /* Increasing rate */
  };

  /*
   * Array of string names of the state machine names.
   * Index is the (MC enumerated) number of the state.
   */
  static const c_t * const state_name_strings[ 5 ] = {
    "",
    "syncing",
    "listening for systolic pulse",
    "listening for diastolic pulse",
    "Increasing rate"
  };

  /*
   * Array of pointers to the class transition action procedures.
   * Index is the (MC enumerated) number of the transition action to execute.
   */
  static const StateAction_t pacemaker_PACER_xacts[ 2 ] = {
    (StateAction_t) pacemaker_PACER_xact1,
    (StateAction_t) pacemaker_PACER_xact2
  };

/*
 * instance state machine event dispatching
 */
void
pacemaker_PACER_Dispatch( Escher_xtUMLEvent_t * event )
{
  Escher_iHandle_t instance = GetEventTargetInstance( event );
  Escher_EventNumber_t event_number = GetOoaEventNumber( event );
  Escher_StateNumber_t current_state;
  Escher_SEMcell_t next_state;
  
  if ( 0 != instance ) {
    current_state = instance->current_state;
    if ( current_state > 4 ) {
      /* instance validation failure (object deleted while event in flight) */
      UserEventNoInstanceCallout( event_number );
    } else {
      next_state = pacemaker_PACER_StateEventMatrix[ current_state ][ event_number ];
      if ( next_state <= 4 ) {
        STATE_TXN_START_TRACE( "PACER", current_state, state_name_strings[ current_state ] );
        /* Execute the state action and update the current state.  */
        ( *pacemaker_PACER_acts[ next_state ] )( instance, event );
        STATE_TXN_END_TRACE( "PACER", next_state, state_name_strings[ next_state ] );
        instance->current_state = next_state;
      } else if ( next_state == EVENT_CANT_HAPPEN ) {
          /* event cant happen */
          UserEventCantHappenCallout( current_state, next_state, event_number );
          STATE_TXN_CH_TRACE( "PACER", current_state );
      } else {
        STATE_TXN_START_TRACE( "PACER", current_state, state_name_strings[ current_state ] );
        ( *pacemaker_PACER_xacts[ (next_state>>8)-1 ] )( instance, event );
        next_state = next_state & 0x00ff;
        instance->current_state = next_state;
        ( *pacemaker_PACER_acts[ next_state ] )( instance, event );
        STATE_TXN_END_TRACE( "PACER", next_state, state_name_strings[ next_state ] );
      }
    }
  }
}


