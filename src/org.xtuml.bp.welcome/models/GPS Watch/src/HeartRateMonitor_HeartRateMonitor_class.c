/*----------------------------------------------------------------------------
 * File:  HeartRateMonitor_HeartRateMonitor_class.c
 *
 * Class:       HeartRateMonitor  (HeartRateMonitor)
 * Component:   HeartRateMonitor
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "GPSWatch_sys_types.h"
#include "LOG_bridge.h"
#include "TIM_bridge.h"
#include "HeartRateMonitor_classes.h"


/*
 * Statically allocate space for the instance population for this class.
 * Allocate space for the class instance and its attribute values.
 * Depending upon the collection scheme, allocate containoids (collection
 * nodes) for gathering instances into free and active extents.
 */
static Escher_SetElement_s HeartRateMonitor_HeartRateMonitor_container[ HeartRateMonitor_HeartRateMonitor_MAX_EXTENT_SIZE ];
static HeartRateMonitor_HeartRateMonitor HeartRateMonitor_HeartRateMonitor_instances[ HeartRateMonitor_HeartRateMonitor_MAX_EXTENT_SIZE ];
Escher_Extent_t pG_HeartRateMonitor_HeartRateMonitor_extent = {
  {0}, {0}, &HeartRateMonitor_HeartRateMonitor_container[ 0 ],
  (Escher_iHandle_t) &HeartRateMonitor_HeartRateMonitor_instances,
  sizeof( HeartRateMonitor_HeartRateMonitor ), 0, HeartRateMonitor_HeartRateMonitor_MAX_EXTENT_SIZE
  };
/*----------------------------------------------------------------------------
 * State and transition action implementations for the following class:
 *
 * Class:      HeartRateMonitor  (HeartRateMonitor)
 * Component:  HeartRateMonitor
 *--------------------------------------------------------------------------*/

/*
 * State 1:  [idle]
 */
static void HeartRateMonitor_HeartRateMonitor_CB_act1( HeartRateMonitor_HeartRateMonitor *, const Escher_xtUMLEvent_t * const );
static void
HeartRateMonitor_HeartRateMonitor_CB_act1( HeartRateMonitor_HeartRateMonitor * self, const Escher_xtUMLEvent_t * const event )
{
}

/*
 * State 2:  [monitoring]
 */
static void HeartRateMonitor_HeartRateMonitor_CB_act2( HeartRateMonitor_HeartRateMonitor *, const Escher_xtUMLEvent_t * const );
static void
HeartRateMonitor_HeartRateMonitor_CB_act2( HeartRateMonitor_HeartRateMonitor * self, const Escher_xtUMLEvent_t * const event )
{
  HeartRateMonitor_HeartRateMonitor * monitor=0; 
  /* SELECT any monitor FROM INSTANCES OF HeartRateMonitor */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any monitor FROM INSTANCES OF HeartRateMonitor" );
  monitor = (HeartRateMonitor_HeartRateMonitor *) Escher_SetGetAny( &pG_HeartRateMonitor_HeartRateMonitor_extent.active );
  /*  SEND HR::heartRateChanged(heartRate:monitor.recentHeartRate) */
  XTUML_OAL_STMT_TRACE( 1, " SEND HR::heartRateChanged(heartRate:monitor.recentHeartRate)" );
  HeartRateMonitor_HR_heartRateChanged( monitor->recentHeartRate );
  /* ASSIGN monitor.recentHeartRate = ( monitor.recentHeartRate + 1.0 ) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN monitor.recentHeartRate = ( monitor.recentHeartRate + 1.0 )" );
  monitor->recentHeartRate = ( monitor->recentHeartRate + 1.0 );
}

/*
 */
static void HeartRateMonitor_HeartRateMonitor_CB_xact1( HeartRateMonitor_HeartRateMonitor *, const Escher_xtUMLEvent_t * const );
static void
HeartRateMonitor_HeartRateMonitor_CB_xact1( HeartRateMonitor_HeartRateMonitor * self, const Escher_xtUMLEvent_t * const event )
{
  HeartRateMonitor_HeartRateMonitor * monitor=0; Escher_xtUMLEvent_t * timeout;  /* timeout */ 
  /* SELECT any monitor FROM INSTANCES OF HeartRateMonitor */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any monitor FROM INSTANCES OF HeartRateMonitor" );
  monitor = (HeartRateMonitor_HeartRateMonitor *) Escher_SetGetAny( &pG_HeartRateMonitor_HeartRateMonitor_extent.active );
  /* IF ( empty monitor ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( empty monitor )" );
  if ( ( 0 == monitor ) ) {
    /* CREATE OBJECT INSTANCE monitor OF HeartRateMonitor */
    XTUML_OAL_STMT_TRACE( 2, "CREATE OBJECT INSTANCE monitor OF HeartRateMonitor" );
    monitor = (HeartRateMonitor_HeartRateMonitor *) Escher_CreateInstance( HeartRateMonitor_DOMAIN_ID, HeartRateMonitor_HeartRateMonitor_CLASS_NUMBER );
  }
  /* ASSIGN monitor.recentHeartRate = 50.0 */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN monitor.recentHeartRate = 50.0" );
  monitor->recentHeartRate = 50.0;
  /* LOG::LogInfo( message:listener registered with interval: 3 sec ) */
  XTUML_OAL_STMT_TRACE( 1, "LOG::LogInfo( message:listener registered with interval: 3 sec )" );
  LOG_LogInfo( "listener registered with interval: 3 sec" );
  /* CREATE EVENT INSTANCE timeout(  ) TO CLASS */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE timeout(  ) TO CLASS" );
  timeout = Escher_NewxtUMLEvent( (void *) 0, &HeartRateMonitor_HeartRateMonitor_CBevent3c );
  /* ASSIGN monitor.timer = TIM::timer_start_recurring(event_inst:timeout, microseconds:3000000) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN monitor.timer = TIM::timer_start_recurring(event_inst:timeout, microseconds:3000000)" );
  monitor->timer = TIM_timer_start_recurring( (Escher_xtUMLEvent_t *)timeout, 3000000 );
}

/*
 */
static void HeartRateMonitor_HeartRateMonitor_CB_xact2( HeartRateMonitor_HeartRateMonitor *, const Escher_xtUMLEvent_t * const );
static void
HeartRateMonitor_HeartRateMonitor_CB_xact2( HeartRateMonitor_HeartRateMonitor * self, const Escher_xtUMLEvent_t * const event )
{
  HeartRateMonitor_HeartRateMonitor * monitor=0; bool res; 
  /* SELECT any monitor FROM INSTANCES OF HeartRateMonitor */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any monitor FROM INSTANCES OF HeartRateMonitor" );
  monitor = (HeartRateMonitor_HeartRateMonitor *) Escher_SetGetAny( &pG_HeartRateMonitor_HeartRateMonitor_extent.active );
  /* ASSIGN res = TIM::timer_cancel(timer_inst_ref:monitor.timer) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN res = TIM::timer_cancel(timer_inst_ref:monitor.timer)" );
  res = TIM_timer_cancel( monitor->timer );
}

const Escher_xtUMLEventConstant_t HeartRateMonitor_HeartRateMonitor_CBevent1c = {
  HeartRateMonitor_DOMAIN_ID, HeartRateMonitor_HeartRateMonitor_CLASS_NUMBER_CB, HEARTRATEMONITOR_HEARTRATEMONITOR_CBEVENT1NUM,
  ESCHER_IS_ASSIGNER_EVENT };

const Escher_xtUMLEventConstant_t HeartRateMonitor_HeartRateMonitor_CBevent2c = {
  HeartRateMonitor_DOMAIN_ID, HeartRateMonitor_HeartRateMonitor_CLASS_NUMBER_CB, HEARTRATEMONITOR_HEARTRATEMONITOR_CBEVENT2NUM,
  ESCHER_IS_ASSIGNER_EVENT };

const Escher_xtUMLEventConstant_t HeartRateMonitor_HeartRateMonitor_CBevent3c = {
  HeartRateMonitor_DOMAIN_ID, HeartRateMonitor_HeartRateMonitor_CLASS_NUMBER_CB, HEARTRATEMONITOR_HEARTRATEMONITOR_CBEVENT3NUM,
  ESCHER_IS_ASSIGNER_EVENT };



/*
 * State-Event Matrix (SEM)
 * Row index is object (MC enumerated) current state.
 * Row zero is the uninitialized state (e.g., for creation event transitions).
 * Column index is (MC enumerated) state machine events.
 */
static const Escher_SEMcell_t HeartRateMonitor_HeartRateMonitor_CB_StateEventMatrix[ 2 + 1 ][ 3 ] = {
  /* row 0:  uninitialized state (for creation events) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN },
  /* row 1:  HeartRateMonitor_HeartRateMonitor_CB_STATE_1 (idle) */
  { EVENT_IS_IGNORED, (1<<8) + HeartRateMonitor_HeartRateMonitor_CB_STATE_2, EVENT_CANT_HAPPEN },
  /* row 2:  HeartRateMonitor_HeartRateMonitor_CB_STATE_2 (monitoring) */
  { HeartRateMonitor_HeartRateMonitor_CB_STATE_2, EVENT_CANT_HAPPEN, (2<<8) + HeartRateMonitor_HeartRateMonitor_CB_STATE_1 }
};

  /*
   * Array of pointers to the class state action procedures.
   * Index is the (MC enumerated) number of the state action to execute.
   */
  static const StateAction_t HeartRateMonitor_HeartRateMonitor_CB_acts[ 3 ] = {
    (StateAction_t) 0,
    (StateAction_t) HeartRateMonitor_HeartRateMonitor_CB_act1,  /* idle */
    (StateAction_t) HeartRateMonitor_HeartRateMonitor_CB_act2  /* monitoring */
  };

  /*
   * Array of string names of the state machine names.
   * Index is the (MC enumerated) number of the state.
   */
  static const c_t * const state_name_strings_CB[ 3 ] = {
    "",
    "idle",
    "monitoring"
  };

  /*
   * Array of pointers to the class transition action procedures.
   * Index is the (MC enumerated) number of the transition action to execute.
   */
  static const StateAction_t HeartRateMonitor_HeartRateMonitor_CB_xacts[ 2 ] = {
    (StateAction_t) HeartRateMonitor_HeartRateMonitor_CB_xact1,
    (StateAction_t) HeartRateMonitor_HeartRateMonitor_CB_xact2
  };

/*
 * class-based state machine event dispatching
 */
void
HeartRateMonitor_HeartRateMonitor_CBDispatch( Escher_xtUMLEvent_t * event )
{
  static Escher_InstanceBase_t class_based_singleton = { HeartRateMonitor_HeartRateMonitor_CB_STATE_1 };
  Escher_EventNumber_t event_number = GetOoaEventNumber( event );
  Escher_StateNumber_t current_state = class_based_singleton.current_state;
  Escher_SEMcell_t next_state = HeartRateMonitor_HeartRateMonitor_CB_StateEventMatrix[ current_state ][ event_number ];

  if ( next_state <= 2 ) {
    STATE_TXN_START_TRACE( "HeartRateMonitor", current_state, state_name_strings_CB[ current_state ] );
    /* Execute the state action and update the current state.  */
    ( *HeartRateMonitor_HeartRateMonitor_CB_acts[ next_state ] )( &class_based_singleton, event );
    class_based_singleton.current_state = next_state;
    STATE_TXN_END_TRACE( "HeartRateMonitor", next_state, state_name_strings_CB[ next_state ] );
  } else {
    if ( EVENT_CANT_HAPPEN == next_state ) {
      /* Event cannot happen.  */
      UserEventCantHappenCallout( current_state, next_state, event_number );
      STATE_TXN_CH_TRACE( "HeartRateMonitor", current_state );
    } else if ( EVENT_IS_IGNORED == next_state ) {
      /* Event ignored */
      STATE_TXN_IG_TRACE( "HeartRateMonitor", current_state );
    } else {
      STATE_TXN_START_TRACE( "HeartRateMonitor", current_state, state_name_strings_CB[ current_state ] );
      ( *HeartRateMonitor_HeartRateMonitor_CB_xacts[ (next_state>>8)-1 ] )( &class_based_singleton, event );
      next_state = next_state & 0x00ff;
      class_based_singleton.current_state = next_state;
      ( *HeartRateMonitor_HeartRateMonitor_CB_acts[ next_state ] )( &class_based_singleton, event );
      STATE_TXN_END_TRACE( "HeartRateMonitor", next_state, state_name_strings_CB[ next_state ] );
    }
  }
}


