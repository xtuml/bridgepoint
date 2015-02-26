/*----------------------------------------------------------------------------
 * File:  hostmonitor_HM_class.c
 *
 * Class:       Host Monitor  (HM)
 * Component:   hostmonitor
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "avpace_sys_types.h"
#include "hostmonitor_classes.h"


/*
 * Statically allocate space for the instance population for this class.
 * Allocate space for the class instance and its attribute values.
 * Depending upon the collection scheme, allocate containoids (collection
 * nodes) for gathering instances into free and active extents.
 */
static Escher_SetElement_s hostmonitor_HM_container[ hostmonitor_HM_MAX_EXTENT_SIZE ];
static hostmonitor_HM hostmonitor_HM_instances[ hostmonitor_HM_MAX_EXTENT_SIZE ];
Escher_Extent_t pG_hostmonitor_HM_extent = {
  {0}, {0}, &hostmonitor_HM_container[ 0 ],
  (Escher_iHandle_t) &hostmonitor_HM_instances,
  sizeof( hostmonitor_HM ), hostmonitor_HM_STATE_1, hostmonitor_HM_MAX_EXTENT_SIZE
  };
/*----------------------------------------------------------------------------
 * State and transition action implementations for the following class:
 *
 * Class:      Host Monitor  (HM)
 * Component:  hostmonitor
 *--------------------------------------------------------------------------*/

/*
 * State 1:  [Polling monitors]
 */
static void hostmonitor_HM_act1( hostmonitor_HM *, const Escher_xtUMLEvent_t * const );
static void
hostmonitor_HM_act1( hostmonitor_HM * self, const Escher_xtUMLEvent_t * const event )
{
  hostmonitor_RM * rm=0;hostmonitor_TM * tm=0;
  /* SELECT one tm RELATED BY self->TM[R2] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one tm RELATED BY self->TM[R2]" );
  tm = ( 0 != self ) ? self->TM_R2 : 0;
  /* ASSIGN self.lastTemp = tm.getCurrentTemp() */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.lastTemp = tm.getCurrentTemp()" );
  self->lastTemp = hostmonitor_TM_op_getCurrentTemp(tm);
  /* SELECT one rm RELATED BY self->RM[R3] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one rm RELATED BY self->RM[R3]" );
  rm = ( 0 != self ) ? self->RM_R3 : 0;
  /* ASSIGN self.lastRate = rm.getCurrentRate() */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.lastRate = rm.getCurrentRate()" );
  self->lastRate = hostmonitor_RM_op_getCurrentRate(rm);
  /* IF ( ( ( self.lastTemp > 37.5 ) or ( self.lastRate > 18 ) ) ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( ( ( self.lastTemp > 37.5 ) or ( self.lastRate > 18 ) ) )" );
  if ( ( ( self->lastTemp > 37.5 ) || ( self->lastRate > 18 ) ) ) {
    /* GENERATE HM2:increasedActivity() TO self */
    XTUML_OAL_STMT_TRACE( 2, "GENERATE HM2:increasedActivity() TO self" );
    { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( self, &hostmonitor_HMevent2c );
      Escher_SendSelfEvent( e );
    }
  }
  /* GENERATE HM1:poll() TO self */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE HM1:poll() TO self" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( self, &hostmonitor_HMevent1c );
    Escher_SendSelfEvent( e );
  }
}

/*
 * State 2:  [Notifying of increased activity]
 */
static void hostmonitor_HM_act2( hostmonitor_HM *, const Escher_xtUMLEvent_t * const );
static void
hostmonitor_HM_act2( hostmonitor_HM * self, const Escher_xtUMLEvent_t * const event )
{
  /* SEND monitor::increased_activty(current_rate:self.lastRate, current_temp:self.lastTemp) */
  XTUML_OAL_STMT_TRACE( 1, "SEND monitor::increased_activty(current_rate:self.lastRate, current_temp:self.lastTemp)" );
  hostmonitor_monitor_increased_activty( self->lastRate, self->lastTemp );
  /* GENERATE HM1:poll() TO self */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE HM1:poll() TO self" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( self, &hostmonitor_HMevent1c );
    Escher_SendSelfEvent( e );
  }
}

/*
 * State 1:  [listening to host events]
 */
static void hostmonitor_HM_CB_act1( hostmonitor_HM *, const Escher_xtUMLEvent_t * const );
static void
hostmonitor_HM_CB_act1( hostmonitor_HM * self, const Escher_xtUMLEvent_t * const event )
{
}

const Escher_xtUMLEventConstant_t hostmonitor_HM_CBevent1c = {
  hostmonitor_DOMAIN_ID, hostmonitor_HM_CLASS_NUMBER_CB, HOSTMONITOR_HM_CBEVENT1NUM,
  ESCHER_IS_ASSIGNER_EVENT };

const Escher_xtUMLEventConstant_t hostmonitor_HM_CBevent2c = {
  hostmonitor_DOMAIN_ID, hostmonitor_HM_CLASS_NUMBER_CB, HOSTMONITOR_HM_CBEVENT2NUM,
  ESCHER_IS_ASSIGNER_EVENT };



/*
 * State-Event Matrix (SEM)
 * Row index is object (MC enumerated) current state.
 * Row zero is the uninitialized state (e.g., for creation event transitions).
 * Column index is (MC enumerated) state machine events.
 */
static const Escher_SEMcell_t hostmonitor_HM_CB_StateEventMatrix[ 1 + 1 ][ 2 ] = {
  /* row 0:  uninitialized state (for creation events) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN },
  /* row 1:  hostmonitor_HM_CB_STATE_1 (listening to host events) */
  { hostmonitor_HM_CB_STATE_1, hostmonitor_HM_CB_STATE_1 }
};

  /*
   * Array of pointers to the class state action procedures.
   * Index is the (MC enumerated) number of the state action to execute.
   */
  static const StateAction_t hostmonitor_HM_CB_acts[ 2 ] = {
    (StateAction_t) 0,
    (StateAction_t) hostmonitor_HM_CB_act1  /* listening to host events */
  };

  /*
   * Array of string names of the state machine names.
   * Index is the (MC enumerated) number of the state.
   */
  static const c_t * const state_name_strings_CB[ 2 ] = {
    "",
    "listening to host events"
  };

/*
 * class-based state machine event dispatching
 */
void
hostmonitor_HM_CBDispatch( Escher_xtUMLEvent_t * event )
{
  static Escher_InstanceBase_t class_based_singleton = { hostmonitor_HM_CB_STATE_1 };
  Escher_EventNumber_t event_number = GetOoaEventNumber( event );
  Escher_StateNumber_t current_state = class_based_singleton.current_state;
  Escher_StateNumber_t next_state = hostmonitor_HM_CB_StateEventMatrix[ current_state ][ event_number ];

  if ( next_state <= 1 ) {
    STATE_TXN_START_TRACE( "HM", current_state, state_name_strings_CB[ current_state ] );
    /* Execute the state action and update the current state.  */
    ( *hostmonitor_HM_CB_acts[ next_state ] )( &class_based_singleton, event );
    class_based_singleton.current_state = next_state;
    STATE_TXN_END_TRACE( "HM", next_state, state_name_strings_CB[ next_state ] );
  } else {
    if ( EVENT_CANT_HAPPEN == next_state ) {
      /* Event cannot happen.  */
      UserEventCantHappenCallout( current_state, next_state, event_number );
      STATE_TXN_CH_TRACE( "HM", current_state );
    } else if ( EVENT_IS_IGNORED == next_state ) {
      /* Event ignored */
      STATE_TXN_IG_TRACE( "HM", current_state );
    } else {
      /* Translation/memory/stack error, etc - TBD */
    }
  }
}

const Escher_xtUMLEventConstant_t hostmonitor_HMevent1c = {
  hostmonitor_DOMAIN_ID, hostmonitor_HM_CLASS_NUMBER, HOSTMONITOR_HMEVENT1NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t hostmonitor_HMevent2c = {
  hostmonitor_DOMAIN_ID, hostmonitor_HM_CLASS_NUMBER, HOSTMONITOR_HMEVENT2NUM,
  ESCHER_IS_INSTANCE_EVENT };



/*
 * State-Event Matrix (SEM)
 * Row index is object (MC enumerated) current state.
 * Row zero is the uninitialized state (e.g., for creation event transitions).
 * Column index is (MC enumerated) state machine events.
 */
static const Escher_SEMcell_t hostmonitor_HM_StateEventMatrix[ 2 + 1 ][ 2 ] = {
  /* row 0:  uninitialized state (for creation events) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN },
  /* row 1:  hostmonitor_HM_STATE_1 (Polling monitors) */
  { hostmonitor_HM_STATE_1, hostmonitor_HM_STATE_2 },
  /* row 2:  hostmonitor_HM_STATE_2 (Notifying of increased activity) */
  { hostmonitor_HM_STATE_1, EVENT_CANT_HAPPEN }
};

  /*
   * Array of pointers to the class state action procedures.
   * Index is the (MC enumerated) number of the state action to execute.
   */
  static const StateAction_t hostmonitor_HM_acts[ 3 ] = {
    (StateAction_t) 0,
    (StateAction_t) hostmonitor_HM_act1,  /* Polling monitors */
    (StateAction_t) hostmonitor_HM_act2  /* Notifying of increased activity */
  };

  /*
   * Array of string names of the state machine names.
   * Index is the (MC enumerated) number of the state.
   */
  static const c_t * const state_name_strings[ 3 ] = {
    "",
    "Polling monitors",
    "Notifying of increased activity"
  };

/*
 * instance state machine event dispatching
 */
void
hostmonitor_HM_Dispatch( Escher_xtUMLEvent_t * event )
{
  Escher_iHandle_t instance = GetEventTargetInstance( event );
  Escher_EventNumber_t event_number = GetOoaEventNumber( event );
  Escher_StateNumber_t current_state;
  Escher_StateNumber_t next_state;
  
  if ( 0 != instance ) {
    current_state = instance->current_state;
    if ( current_state > 2 ) {
      /* instance validation failure (object deleted while event in flight) */
      UserEventNoInstanceCallout( event_number );
    } else {
      next_state = hostmonitor_HM_StateEventMatrix[ current_state ][ event_number ];
      if ( next_state <= 2 ) {
        STATE_TXN_START_TRACE( "HM", current_state, state_name_strings[ current_state ] );
        /* Execute the state action and update the current state.  */
        ( *hostmonitor_HM_acts[ next_state ] )( instance, event );
        STATE_TXN_END_TRACE( "HM", next_state, state_name_strings[ next_state ] );
        instance->current_state = next_state;
      } else if ( next_state == EVENT_CANT_HAPPEN ) {
          /* event cant happen */
          UserEventCantHappenCallout( current_state, next_state, event_number );
          STATE_TXN_CH_TRACE( "HM", current_state );
      } else {
        /* empty else */
      }
    }
  }
}


