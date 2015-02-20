/*----------------------------------------------------------------------------
 * File:  heart_HEART_class.c
 *
 * Class:       heart  (HEART)
 * Component:   heart
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "avpace_sys_types.h"
#include "heart_LOG_bridge.h"
#include "TIM_bridge.h"
#include "heart_classes.h"


/*
 * Statically allocate space for the instance population for this class.
 * Allocate space for the class instance and its attribute values.
 * Depending upon the collection scheme, allocate containoids (collection
 * nodes) for gathering instances into free and active extents.
 */
static Escher_SetElement_s heart_HEART_container[ heart_HEART_MAX_EXTENT_SIZE ];
static heart_HEART heart_HEART_instances[ heart_HEART_MAX_EXTENT_SIZE ];
Escher_Extent_t pG_heart_HEART_extent = {
  {0}, {0}, &heart_HEART_container[ 0 ],
  (Escher_iHandle_t) &heart_HEART_instances,
  sizeof( heart_HEART ), heart_HEART_STATE_1, heart_HEART_MAX_EXTENT_SIZE
  };
/*----------------------------------------------------------------------------
 * State and transition action implementations for the following class:
 *
 * Class:      heart  (HEART)
 * Component:  heart
 *--------------------------------------------------------------------------*/

/*
 * State 5:  [atrial systole]
 */
static void heart_HEART_act5( heart_HEART *, const Escher_xtUMLEvent_t * const );
static void
heart_HEART_act5( heart_HEART * self, const Escher_xtUMLEvent_t * const event )
{
  /* GENERATE HEART1:done() TO self */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE HEART1:done() TO self" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( self, &heart_HEARTevent1c );
    Escher_SendSelfEvent( e );
  }
}

/*
 * State 2:  [ventricular systole]
 */
static void heart_HEART_act2( heart_HEART *, const Escher_xtUMLEvent_t * const );
static void
heart_HEART_act2( heart_HEART * self, const Escher_xtUMLEvent_t * const event )
{
}

/*
 * State 3:  [atrial diastole]
 */
static void heart_HEART_act3( heart_HEART *, const Escher_xtUMLEvent_t * const );
static void
heart_HEART_act3( heart_HEART * self, const Escher_xtUMLEvent_t * const event )
{
  /* GENERATE HEART1:done() TO self */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE HEART1:done() TO self" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( self, &heart_HEARTevent1c );
    Escher_SendSelfEvent( e );
  }
}

/*
 * State 4:  [ventricular diastole]
 */
static void heart_HEART_act4( heart_HEART *, const Escher_xtUMLEvent_t * const );
static void
heart_HEART_act4( heart_HEART * self, const Escher_xtUMLEvent_t * const event )
{
}

/*
 * State 1:  [born]
 */
static void heart_HEART_act1( heart_HEART *, const Escher_xtUMLEvent_t * const );
static void
heart_HEART_act1( heart_HEART * self, const Escher_xtUMLEvent_t * const event )
{
}

/*
 * State 1:  [receiving paces]
 */
static void heart_HEART_CB_act1( heart_HEART *, const Escher_xtUMLEvent_t * const );
static void
heart_HEART_CB_act1( heart_HEART * self, const Escher_xtUMLEvent_t * const event )
{
}

/*
 */
static void heart_HEART_CB_xact1( heart_HEART *, const Escher_xtUMLEvent_t * const );
static void
heart_HEART_CB_xact1( heart_HEART * self, const Escher_xtUMLEvent_t * const event )
{
  heart_HEART * heart=0;
  /* SELECT any heart FROM INSTANCES OF HEART */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any heart FROM INSTANCES OF HEART" );
  heart = (heart_HEART *) Escher_SetGetAny( &pG_heart_HEART_extent.active );
  /* GENERATE HEART2:diastolic_pace() TO heart */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE HEART2:diastolic_pace() TO heart" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( heart, &heart_HEARTevent2c );
    Escher_SendEvent( e );
  }
}

/*
 */
static void heart_HEART_CB_xact2( heart_HEART *, const Escher_xtUMLEvent_t * const );
static void
heart_HEART_CB_xact2( heart_HEART * self, const Escher_xtUMLEvent_t * const event )
{
  heart_HEART * heart=0;
  /* SELECT any heart FROM INSTANCES OF HEART */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any heart FROM INSTANCES OF HEART" );
  heart = (heart_HEART *) Escher_SetGetAny( &pG_heart_HEART_extent.active );
  /* GENERATE HEART3:systolic_pace() TO heart */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE HEART3:systolic_pace() TO heart" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( heart, &heart_HEARTevent3c );
    Escher_SendEvent( e );
  }
}

const Escher_xtUMLEventConstant_t heart_HEART_CBevent1c = {
  heart_DOMAIN_ID, heart_HEART_CLASS_NUMBER_CB, HEART_HEART_CBEVENT1NUM,
  ESCHER_IS_ASSIGNER_EVENT };

const Escher_xtUMLEventConstant_t heart_HEART_CBevent2c = {
  heart_DOMAIN_ID, heart_HEART_CLASS_NUMBER_CB, HEART_HEART_CBEVENT2NUM,
  ESCHER_IS_ASSIGNER_EVENT };



/*
 * State-Event Matrix (SEM)
 * Row index is object (MC enumerated) current state.
 * Row zero is the uninitialized state (e.g., for creation event transitions).
 * Column index is (MC enumerated) state machine events.
 */
static const Escher_SEMcell_t heart_HEART_CB_StateEventMatrix[ 1 + 1 ][ 2 ] = {
  /* row 0:  uninitialized state (for creation events) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN },
  /* row 1:  heart_HEART_CB_STATE_1 (receiving paces) */
  { (2<<8) + heart_HEART_CB_STATE_1, (1<<8) + heart_HEART_CB_STATE_1 }
};

  /*
   * Array of pointers to the class state action procedures.
   * Index is the (MC enumerated) number of the state action to execute.
   */
  static const StateAction_t heart_HEART_CB_acts[ 2 ] = {
    (StateAction_t) 0,
    (StateAction_t) heart_HEART_CB_act1  /* receiving paces */
  };

  /*
   * Array of string names of the state machine names.
   * Index is the (MC enumerated) number of the state.
   */
  static const c_t * const state_name_strings_CB[ 2 ] = {
    "",
    "receiving paces"
  };

  /*
   * Array of pointers to the class transition action procedures.
   * Index is the (MC enumerated) number of the transition action to execute.
   */
  static const StateAction_t heart_HEART_CB_xacts[ 2 ] = {
    (StateAction_t) heart_HEART_CB_xact1,
    (StateAction_t) heart_HEART_CB_xact2
  };

/*
 * class-based state machine event dispatching
 */
void
heart_HEART_CBDispatch( Escher_xtUMLEvent_t * event )
{
  static Escher_InstanceBase_t class_based_singleton = { heart_HEART_CB_STATE_1 };
  Escher_EventNumber_t event_number = GetOoaEventNumber( event );
  Escher_StateNumber_t current_state = class_based_singleton.current_state;
  Escher_SEMcell_t next_state = heart_HEART_CB_StateEventMatrix[ current_state ][ event_number ];

  if ( next_state <= 1 ) {
    STATE_TXN_START_TRACE( "HEART", current_state, state_name_strings_CB[ current_state ] );
    /* Execute the state action and update the current state.  */
    ( *heart_HEART_CB_acts[ next_state ] )( &class_based_singleton, event );
    class_based_singleton.current_state = next_state;
    STATE_TXN_END_TRACE( "HEART", next_state, state_name_strings_CB[ next_state ] );
  } else {
    if ( EVENT_CANT_HAPPEN == next_state ) {
      /* Event cannot happen.  */
      UserEventCantHappenCallout( current_state, next_state, event_number );
      STATE_TXN_CH_TRACE( "HEART", current_state );
    } else if ( EVENT_IS_IGNORED == next_state ) {
      /* Event ignored */
      STATE_TXN_IG_TRACE( "HEART", current_state );
    } else {
      STATE_TXN_START_TRACE( "HEART", current_state, state_name_strings_CB[ current_state ] );
      ( *heart_HEART_CB_xacts[ (next_state>>8)-1 ] )( &class_based_singleton, event );
      next_state = next_state & 0x00ff;
      class_based_singleton.current_state = next_state;
      ( *heart_HEART_CB_acts[ next_state ] )( &class_based_singleton, event );
      STATE_TXN_END_TRACE( "HEART", next_state, state_name_strings_CB[ next_state ] );
    }
  }
}

const Escher_xtUMLEventConstant_t heart_HEARTevent1c = {
  heart_DOMAIN_ID, heart_HEART_CLASS_NUMBER, HEART_HEARTEVENT1NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t heart_HEARTevent2c = {
  heart_DOMAIN_ID, heart_HEART_CLASS_NUMBER, HEART_HEARTEVENT2NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t heart_HEARTevent3c = {
  heart_DOMAIN_ID, heart_HEART_CLASS_NUMBER, HEART_HEARTEVENT3NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t heart_HEARTevent4c = {
  heart_DOMAIN_ID, heart_HEART_CLASS_NUMBER, HEART_HEARTEVENT4NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t heart_HEARTevent5c = {
  heart_DOMAIN_ID, heart_HEART_CLASS_NUMBER, HEART_HEARTEVENT5NUM,
  ESCHER_IS_INSTANCE_EVENT };



/*
 * State-Event Matrix (SEM)
 * Row index is object (MC enumerated) current state.
 * Row zero is the uninitialized state (e.g., for creation event transitions).
 * Column index is (MC enumerated) state machine events.
 */
static const Escher_SEMcell_t heart_HEART_StateEventMatrix[ 5 + 1 ][ 5 ] = {
  /* row 0:  uninitialized state (for creation events) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN },
  /* row 1:  heart_HEART_STATE_1 (born) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, heart_HEART_STATE_3, heart_HEART_STATE_5 },
  /* row 2:  heart_HEART_STATE_2 (ventricular systole) */
  { EVENT_IS_IGNORED, heart_HEART_STATE_3, EVENT_CANT_HAPPEN, heart_HEART_STATE_3, EVENT_CANT_HAPPEN },
  /* row 3:  heart_HEART_STATE_3 (atrial diastole) */
  { heart_HEART_STATE_4, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN },
  /* row 4:  heart_HEART_STATE_4 (ventricular diastole) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, heart_HEART_STATE_5, EVENT_CANT_HAPPEN, heart_HEART_STATE_5 },
  /* row 5:  heart_HEART_STATE_5 (atrial systole) */
  { heart_HEART_STATE_2, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN }
};

  /*
   * Array of pointers to the class state action procedures.
   * Index is the (MC enumerated) number of the state action to execute.
   */
  static const StateAction_t heart_HEART_acts[ 6 ] = {
    (StateAction_t) 0,
    (StateAction_t) heart_HEART_act1,  /* born */
    (StateAction_t) heart_HEART_act2,  /* ventricular systole */
    (StateAction_t) heart_HEART_act3,  /* atrial diastole */
    (StateAction_t) heart_HEART_act4,  /* ventricular diastole */
    (StateAction_t) heart_HEART_act5  /* atrial systole */
  };

  /*
   * Array of string names of the state machine names.
   * Index is the (MC enumerated) number of the state.
   */
  static const c_t * const state_name_strings[ 6 ] = {
    "",
    "born",
    "ventricular systole",
    "atrial diastole",
    "ventricular diastole",
    "atrial systole"
  };

/*
 * instance state machine event dispatching
 */
void
heart_HEART_Dispatch( Escher_xtUMLEvent_t * event )
{
  Escher_iHandle_t instance = GetEventTargetInstance( event );
  Escher_EventNumber_t event_number = GetOoaEventNumber( event );
  Escher_StateNumber_t current_state;
  Escher_StateNumber_t next_state;
  
  if ( 0 != instance ) {
    current_state = instance->current_state;
    if ( current_state > 5 ) {
      /* instance validation failure (object deleted while event in flight) */
      UserEventNoInstanceCallout( event_number );
    } else {
      next_state = heart_HEART_StateEventMatrix[ current_state ][ event_number ];
      if ( next_state <= 5 ) {
        STATE_TXN_START_TRACE( "HEART", current_state, state_name_strings[ current_state ] );
        /* Execute the state action and update the current state.  */
        ( *heart_HEART_acts[ next_state ] )( instance, event );
        STATE_TXN_END_TRACE( "HEART", next_state, state_name_strings[ next_state ] );
        instance->current_state = next_state;
      } else if ( next_state == EVENT_CANT_HAPPEN ) {
          /* event cant happen */
          UserEventCantHappenCallout( current_state, next_state, event_number );
          STATE_TXN_CH_TRACE( "HEART", current_state );
      } else if ( next_state == EVENT_IS_IGNORED ) {
          /* event ignored */
          STATE_TXN_IG_TRACE( "HEART", current_state );
      } else {
        /* empty else */
      }
    }
  }
}


