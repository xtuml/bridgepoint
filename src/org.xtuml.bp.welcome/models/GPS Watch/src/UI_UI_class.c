/*----------------------------------------------------------------------------
 * File:  UI_UI_class.c
 *
 * Class:       UI  (UI)
 * Component:   UI
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "GPSWatch_sys_types.h"
#include "UI_GuiBridge_bridge.h"
#include "LOG_bridge.h"
#include "TIM_bridge.h"
#include "UI_classes.h"

/*
 * class operation:  connect
 */
void
UI_UI_op_connect()
{
  Escher_xtUMLEvent_t * evt;  /* evt */ 
  /* GuiBridge::connect(  ) */
  XTUML_OAL_STMT_TRACE( 1, "GuiBridge::connect(  )" );
  UI_GuiBridge_connect();
  /* CREATE EVENT INSTANCE evt(  ) TO CLASS */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE evt(  ) TO CLASS" );
  evt = Escher_NewxtUMLEvent( (void *) 0, &UI_UI_CBevent3c );
  /* GuiBridge::feedSetTargetPressedEvent( evt:evt ) */
  XTUML_OAL_STMT_TRACE( 1, "GuiBridge::feedSetTargetPressedEvent( evt:evt )" );
  UI_GuiBridge_feedSetTargetPressedEvent( (Escher_xtUMLEvent_t *)evt );
  /* CREATE EVENT INSTANCE evt(  ) TO CLASS */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE evt(  ) TO CLASS" );
  evt = Escher_NewxtUMLEvent( (void *) 0, &UI_UI_CBevent4c );
  /* GuiBridge::feedStartStopPressedEvent( evt:evt ) */
  XTUML_OAL_STMT_TRACE( 1, "GuiBridge::feedStartStopPressedEvent( evt:evt )" );
  UI_GuiBridge_feedStartStopPressedEvent( (Escher_xtUMLEvent_t *)evt );
  /* CREATE EVENT INSTANCE evt(  ) TO CLASS */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE evt(  ) TO CLASS" );
  evt = Escher_NewxtUMLEvent( (void *) 0, &UI_UI_CBevent5c );
  /* GuiBridge::feedLapResetPressedEvent( evt:evt ) */
  XTUML_OAL_STMT_TRACE( 1, "GuiBridge::feedLapResetPressedEvent( evt:evt )" );
  UI_GuiBridge_feedLapResetPressedEvent( (Escher_xtUMLEvent_t *)evt );
  /* CREATE EVENT INSTANCE evt(  ) TO CLASS */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE evt(  ) TO CLASS" );
  evt = Escher_NewxtUMLEvent( (void *) 0, &UI_UI_CBevent6c );
  /* GuiBridge::feedLightPressedEvent( evt:evt ) */
  XTUML_OAL_STMT_TRACE( 1, "GuiBridge::feedLightPressedEvent( evt:evt )" );
  UI_GuiBridge_feedLightPressedEvent( (Escher_xtUMLEvent_t *)evt );
  /* CREATE EVENT INSTANCE evt(  ) TO CLASS */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE evt(  ) TO CLASS" );
  evt = Escher_NewxtUMLEvent( (void *) 0, &UI_UI_CBevent7c );
  /* GuiBridge::feedModePressedEvent( evt:evt ) */
  XTUML_OAL_STMT_TRACE( 1, "GuiBridge::feedModePressedEvent( evt:evt )" );
  UI_GuiBridge_feedModePressedEvent( (Escher_xtUMLEvent_t *)evt );

}



/*----------------------------------------------------------------------------
 * Operation action methods implementation for the following class:
 *
 * Class:      UI  (UI)
 * Component:  UI
 *--------------------------------------------------------------------------*/
/*
 * Statically allocate space for the instance population for this class.
 * Allocate space for the class instance and its attribute values.
 * Depending upon the collection scheme, allocate containoids (collection
 * nodes) for gathering instances into free and active extents.
 */
static Escher_SetElement_s UI_UI_container[ UI_UI_MAX_EXTENT_SIZE ];
static UI_UI UI_UI_instances[ UI_UI_MAX_EXTENT_SIZE ];
Escher_Extent_t pG_UI_UI_extent = {
  {0}, {0}, &UI_UI_container[ 0 ],
  (Escher_iHandle_t) &UI_UI_instances,
  sizeof( UI_UI ), 0, UI_UI_MAX_EXTENT_SIZE
  };
/*----------------------------------------------------------------------------
 * State and transition action implementations for the following class:
 *
 * Class:      UI  (UI)
 * Component:  UI
 *--------------------------------------------------------------------------*/

/*
 * State 1:  [running]
 */
static void UI_UI_CB_act1( UI_UI *, const Escher_xtUMLEvent_t * const );
static void
UI_UI_CB_act1( UI_UI * self, const Escher_xtUMLEvent_t * const event )
{
}

/*
 */
static void UI_UI_CB_xact1( UI_UI *, const Escher_xtUMLEvent_t * const );
static void
UI_UI_CB_xact1( UI_UI * self, const Escher_xtUMLEvent_t * const event )
{
  Escher_xtUMLEvent_t * evt;  /* evt */ 
  /* CREATE EVENT INSTANCE evt(  ) TO CLASS */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE evt(  ) TO CLASS" );
  evt = Escher_NewxtUMLEvent( (void *) 0, &UI_UI_CBevent3c );
  /* GuiBridge::feedSetTargetPressedEvent( evt:evt ) */
  XTUML_OAL_STMT_TRACE( 1, "GuiBridge::feedSetTargetPressedEvent( evt:evt )" );
  UI_GuiBridge_feedSetTargetPressedEvent( (Escher_xtUMLEvent_t *)evt );
  /* GuiBridge::sendTargetPressed(  ) */
  XTUML_OAL_STMT_TRACE( 1, "GuiBridge::sendTargetPressed(  )" );
  UI_GuiBridge_sendTargetPressed();
}

/*
 */
static void UI_UI_CB_xact2( UI_UI *, const Escher_xtUMLEvent_t * const );
static void
UI_UI_CB_xact2( UI_UI * self, const Escher_xtUMLEvent_t * const event )
{
  Escher_xtUMLEvent_t * evt;  /* evt */ 
  /* CREATE EVENT INSTANCE evt(  ) TO CLASS */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE evt(  ) TO CLASS" );
  evt = Escher_NewxtUMLEvent( (void *) 0, &UI_UI_CBevent4c );
  /* GuiBridge::feedStartStopPressedEvent( evt:evt ) */
  XTUML_OAL_STMT_TRACE( 1, "GuiBridge::feedStartStopPressedEvent( evt:evt )" );
  UI_GuiBridge_feedStartStopPressedEvent( (Escher_xtUMLEvent_t *)evt );
  /* GuiBridge::sendStartStopPressed(  ) */
  XTUML_OAL_STMT_TRACE( 1, "GuiBridge::sendStartStopPressed(  )" );
  UI_GuiBridge_sendStartStopPressed();
}

/*
 */
static void UI_UI_CB_xact3( UI_UI *, const Escher_xtUMLEvent_t * const );
static void
UI_UI_CB_xact3( UI_UI * self, const Escher_xtUMLEvent_t * const event )
{
  Escher_xtUMLEvent_t * evt;  /* evt */ 
  /* CREATE EVENT INSTANCE evt(  ) TO CLASS */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE evt(  ) TO CLASS" );
  evt = Escher_NewxtUMLEvent( (void *) 0, &UI_UI_CBevent5c );
  /* GuiBridge::feedLapResetPressedEvent( evt:evt ) */
  XTUML_OAL_STMT_TRACE( 1, "GuiBridge::feedLapResetPressedEvent( evt:evt )" );
  UI_GuiBridge_feedLapResetPressedEvent( (Escher_xtUMLEvent_t *)evt );
  /* GuiBridge::sendLapResetPressed(  ) */
  XTUML_OAL_STMT_TRACE( 1, "GuiBridge::sendLapResetPressed(  )" );
  UI_GuiBridge_sendLapResetPressed();
}

/*
 */
static void UI_UI_CB_xact4( UI_UI *, const Escher_xtUMLEvent_t * const );
static void
UI_UI_CB_xact4( UI_UI * self, const Escher_xtUMLEvent_t * const event )
{
  Escher_xtUMLEvent_t * evt;  /* evt */ 
  /* CREATE EVENT INSTANCE evt(  ) TO CLASS */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE evt(  ) TO CLASS" );
  evt = Escher_NewxtUMLEvent( (void *) 0, &UI_UI_CBevent6c );
  /* GuiBridge::feedLightPressedEvent( evt:evt ) */
  XTUML_OAL_STMT_TRACE( 1, "GuiBridge::feedLightPressedEvent( evt:evt )" );
  UI_GuiBridge_feedLightPressedEvent( (Escher_xtUMLEvent_t *)evt );
  /* GuiBridge::sendLightPressed(  ) */
  XTUML_OAL_STMT_TRACE( 1, "GuiBridge::sendLightPressed(  )" );
  UI_GuiBridge_sendLightPressed();
}

/*
 */
static void UI_UI_CB_xact5( UI_UI *, const Escher_xtUMLEvent_t * const );
static void
UI_UI_CB_xact5( UI_UI * self, const Escher_xtUMLEvent_t * const event )
{
  Escher_xtUMLEvent_t * evt;  /* evt */ 
  /* CREATE EVENT INSTANCE evt(  ) TO CLASS */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE evt(  ) TO CLASS" );
  evt = Escher_NewxtUMLEvent( (void *) 0, &UI_UI_CBevent7c );
  /* GuiBridge::feedModePressedEvent( evt:evt ) */
  XTUML_OAL_STMT_TRACE( 1, "GuiBridge::feedModePressedEvent( evt:evt )" );
  UI_GuiBridge_feedModePressedEvent( (Escher_xtUMLEvent_t *)evt );
  /* GuiBridge::sendModePressed(  ) */
  XTUML_OAL_STMT_TRACE( 1, "GuiBridge::sendModePressed(  )" );
  UI_GuiBridge_sendModePressed();
}

const Escher_xtUMLEventConstant_t UI_UI_CBevent3c = {
  UI_DOMAIN_ID, UI_UI_CLASS_NUMBER_CB, UI_UI_CBEVENT3NUM,
  ESCHER_IS_ASSIGNER_EVENT };

const Escher_xtUMLEventConstant_t UI_UI_CBevent4c = {
  UI_DOMAIN_ID, UI_UI_CLASS_NUMBER_CB, UI_UI_CBEVENT4NUM,
  ESCHER_IS_ASSIGNER_EVENT };

const Escher_xtUMLEventConstant_t UI_UI_CBevent5c = {
  UI_DOMAIN_ID, UI_UI_CLASS_NUMBER_CB, UI_UI_CBEVENT5NUM,
  ESCHER_IS_ASSIGNER_EVENT };

const Escher_xtUMLEventConstant_t UI_UI_CBevent6c = {
  UI_DOMAIN_ID, UI_UI_CLASS_NUMBER_CB, UI_UI_CBEVENT6NUM,
  ESCHER_IS_ASSIGNER_EVENT };

const Escher_xtUMLEventConstant_t UI_UI_CBevent7c = {
  UI_DOMAIN_ID, UI_UI_CLASS_NUMBER_CB, UI_UI_CBEVENT7NUM,
  ESCHER_IS_ASSIGNER_EVENT };



/*
 * State-Event Matrix (SEM)
 * Row index is object (MC enumerated) current state.
 * Row zero is the uninitialized state (e.g., for creation event transitions).
 * Column index is (MC enumerated) state machine events.
 */
static const Escher_SEMcell_t UI_UI_CB_StateEventMatrix[ 1 + 1 ][ 5 ] = {
  /* row 0:  uninitialized state (for creation events) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN },
  /* row 1:  UI_UI_CB_STATE_1 (running) */
  { (1<<8) + UI_UI_CB_STATE_1, (2<<8) + UI_UI_CB_STATE_1, (3<<8) + UI_UI_CB_STATE_1, (4<<8) + UI_UI_CB_STATE_1, (5<<8) + UI_UI_CB_STATE_1 }
};

  /*
   * Array of pointers to the class state action procedures.
   * Index is the (MC enumerated) number of the state action to execute.
   */
  static const StateAction_t UI_UI_CB_acts[ 2 ] = {
    (StateAction_t) 0,
    (StateAction_t) UI_UI_CB_act1  /* running */
  };

  /*
   * Array of string names of the state machine names.
   * Index is the (MC enumerated) number of the state.
   */
  static const c_t * const state_name_strings_CB[ 2 ] = {
    "",
    "running"
  };

  /*
   * Array of pointers to the class transition action procedures.
   * Index is the (MC enumerated) number of the transition action to execute.
   */
  static const StateAction_t UI_UI_CB_xacts[ 5 ] = {
    (StateAction_t) UI_UI_CB_xact1,
    (StateAction_t) UI_UI_CB_xact2,
    (StateAction_t) UI_UI_CB_xact3,
    (StateAction_t) UI_UI_CB_xact4,
    (StateAction_t) UI_UI_CB_xact5
  };

/*
 * class-based state machine event dispatching
 */
void
UI_UI_CBDispatch( Escher_xtUMLEvent_t * event )
{
  static Escher_InstanceBase_t class_based_singleton = { UI_UI_CB_STATE_1 };
  Escher_EventNumber_t event_number = GetOoaEventNumber( event );
  Escher_StateNumber_t current_state = class_based_singleton.current_state;
  Escher_SEMcell_t next_state = UI_UI_CB_StateEventMatrix[ current_state ][ event_number ];

  if ( next_state <= 1 ) {
    STATE_TXN_START_TRACE( "UI", current_state, state_name_strings_CB[ current_state ] );
    /* Execute the state action and update the current state.  */
    ( *UI_UI_CB_acts[ next_state ] )( &class_based_singleton, event );
    class_based_singleton.current_state = next_state;
    STATE_TXN_END_TRACE( "UI", next_state, state_name_strings_CB[ next_state ] );
  } else {
    if ( EVENT_CANT_HAPPEN == next_state ) {
      /* Event cannot happen.  */
      UserEventCantHappenCallout( current_state, next_state, event_number );
      STATE_TXN_CH_TRACE( "UI", current_state );
    } else if ( EVENT_IS_IGNORED == next_state ) {
      /* Event ignored */
      STATE_TXN_IG_TRACE( "UI", current_state );
    } else {
      STATE_TXN_START_TRACE( "UI", current_state, state_name_strings_CB[ current_state ] );
      ( *UI_UI_CB_xacts[ (next_state>>8)-1 ] )( &class_based_singleton, event );
      next_state = next_state & 0x00ff;
      class_based_singleton.current_state = next_state;
      ( *UI_UI_CB_acts[ next_state ] )( &class_based_singleton, event );
      STATE_TXN_END_TRACE( "UI", next_state, state_name_strings_CB[ next_state ] );
    }
  }
}


