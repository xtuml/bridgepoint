/*----------------------------------------------------------------------------
 * File:  Tracking_Display_class.c
 *
 * Class:       Display  (Display)
 * Component:   Tracking
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "GPSWatch_sys_types.h"
#include "LOG_bridge.h"
#include "TIM_bridge.h"
#include "Tracking_classes.h"


/*
 * Statically allocate space for the instance population for this class.
 * Allocate space for the class instance and its attribute values.
 * Depending upon the collection scheme, allocate containoids (collection
 * nodes) for gathering instances into free and active extents.
 */
static Escher_SetElement_s Tracking_Display_container[ Tracking_Display_MAX_EXTENT_SIZE ];
static Tracking_Display Tracking_Display_instances[ Tracking_Display_MAX_EXTENT_SIZE ];
Escher_Extent_t pG_Tracking_Display_extent = {
  {0}, {0}, &Tracking_Display_container[ 0 ],
  (Escher_iHandle_t) &Tracking_Display_instances,
  sizeof( Tracking_Display ), 0, Tracking_Display_MAX_EXTENT_SIZE
  };
/*----------------------------------------------------------------------------
 * State and transition action implementations for the following class:
 *
 * Class:      Display  (Display)
 * Component:  Tracking
 *--------------------------------------------------------------------------*/

/*
 * State 1:  [displayDistance]
 */
static void Tracking_Display_CB_act1( Tracking_Display *, const Escher_xtUMLEvent_t * const );
static void
Tracking_Display_CB_act1( Tracking_Display * self, const Escher_xtUMLEvent_t * const event )
{
  r_t distance; Tracking_TrackLog * trackLog=0; 
  /* ASSIGN distance = 0.0 */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN distance = 0.0" );
  distance = 0.0;
  /* SELECT any trackLog FROM INSTANCES OF TrackLog */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any trackLog FROM INSTANCES OF TrackLog" );
  trackLog = (Tracking_TrackLog *) Escher_SetGetAny( &pG_Tracking_TrackLog_extent.active );
  /* IF ( not empty trackLog ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not empty trackLog )" );
  if ( !( 0 == trackLog ) ) {
    /* ASSIGN distance = trackLog.distance */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN distance = trackLog.distance" );
    distance = trackLog->distance;
  }
  /* IF ( ( distance > 1000.0 ) ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( ( distance > 1000.0 ) )" );
  if ( ( distance > 1000.0 ) ) {
    /* UI::setData(unit:km, value:( distance / 1000.0 )) */
    XTUML_OAL_STMT_TRACE( 2, "UI::setData(unit:km, value:( distance / 1000.0 ))" );
    Tracking_UI_setData( GPSWatch_Unit_km_e, ( distance / 1000.0 ) );
  }
  else {
    /* UI::setData(unit:meters, value:distance) */
    XTUML_OAL_STMT_TRACE( 2, "UI::setData(unit:meters, value:distance)" );
    Tracking_UI_setData( GPSWatch_Unit_meters_e, distance );
  }
}

/*
 * State 2:  [displaySpeed]
 */
static void Tracking_Display_CB_act2( Tracking_Display *, const Escher_xtUMLEvent_t * const );
static void
Tracking_Display_CB_act2( Tracking_Display * self, const Escher_xtUMLEvent_t * const event )
{
  r_t speed; Tracking_TrackLog * trackLog=0; 
  /* ASSIGN speed = 0.0 */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN speed = 0.0" );
  speed = 0.0;
  /* SELECT any trackLog FROM INSTANCES OF TrackLog */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any trackLog FROM INSTANCES OF TrackLog" );
  trackLog = (Tracking_TrackLog *) Escher_SetGetAny( &pG_Tracking_TrackLog_extent.active );
  /* IF ( not empty trackLog ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not empty trackLog )" );
  if ( !( 0 == trackLog ) ) {
    /* ASSIGN speed = trackLog.currentSpeed */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN speed = trackLog.currentSpeed" );
    speed = trackLog->currentSpeed;
  }
  /* UI::setData(unit:kmPerHour, value:speed) */
  XTUML_OAL_STMT_TRACE( 1, "UI::setData(unit:kmPerHour, value:speed)" );
  Tracking_UI_setData( GPSWatch_Unit_kmPerHour_e, speed );
}

/*
 * State 3:  [displayPace]
 */
static void Tracking_Display_CB_act3( Tracking_Display *, const Escher_xtUMLEvent_t * const );
static void
Tracking_Display_CB_act3( Tracking_Display * self, const Escher_xtUMLEvent_t * const event )
{
  r_t pace; Tracking_TrackLog * trackLog=0; 
  /* ASSIGN pace = 0.0 */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN pace = 0.0" );
  pace = 0.0;
  /* SELECT any trackLog FROM INSTANCES OF TrackLog */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any trackLog FROM INSTANCES OF TrackLog" );
  trackLog = (Tracking_TrackLog *) Escher_SetGetAny( &pG_Tracking_TrackLog_extent.active );
  /* IF ( not empty trackLog ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not empty trackLog )" );
  if ( !( 0 == trackLog ) ) {
    /* ASSIGN pace = trackLog.currentPace */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN pace = trackLog.currentPace" );
    pace = Tracking_TrackLog_MDA_currentPace( trackLog );
  }
  /* UI::setData(unit:minPerKm, value:pace) */
  XTUML_OAL_STMT_TRACE( 1, "UI::setData(unit:minPerKm, value:pace)" );
  Tracking_UI_setData( GPSWatch_Unit_minPerKm_e, pace );
}

/*
 * State 4:  [displayHeartRate]
 */
static void Tracking_Display_CB_act4( Tracking_Display *, const Escher_xtUMLEvent_t * const );
static void
Tracking_Display_CB_act4( Tracking_Display * self, const Escher_xtUMLEvent_t * const event )
{
  r_t heartRate; Tracking_TrackLog * trackLog=0; 
  /* ASSIGN heartRate = 0.0 */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN heartRate = 0.0" );
  heartRate = 0.0;
  /* SELECT any trackLog FROM INSTANCES OF TrackLog */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any trackLog FROM INSTANCES OF TrackLog" );
  trackLog = (Tracking_TrackLog *) Escher_SetGetAny( &pG_Tracking_TrackLog_extent.active );
  /* IF ( not empty trackLog ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not empty trackLog )" );
  if ( !( 0 == trackLog ) ) {
    /* ASSIGN heartRate = trackLog.currentHeartRate */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN heartRate = trackLog.currentHeartRate" );
    heartRate = trackLog->currentHeartRate;
  }
  /* UI::setData(unit:bpm, value:heartRate) */
  XTUML_OAL_STMT_TRACE( 1, "UI::setData(unit:bpm, value:heartRate)" );
  Tracking_UI_setData( GPSWatch_Unit_bpm_e, heartRate );
}

/*
 * State 5:  [displayLapCount]
 */
static void Tracking_Display_CB_act5( Tracking_Display *, const Escher_xtUMLEvent_t * const );
static void
Tracking_Display_CB_act5( Tracking_Display * self, const Escher_xtUMLEvent_t * const event )
{
  Escher_ObjectSet_s lapMarkers_space={0}; Escher_ObjectSet_s * lapMarkers = &lapMarkers_space; /* lapMarkers (LapMarker) */ 
  /* SELECT many lapMarkers FROM INSTANCES OF LapMarker */
  XTUML_OAL_STMT_TRACE( 1, "SELECT many lapMarkers FROM INSTANCES OF LapMarker" );
  Escher_CopySet( lapMarkers, &pG_Tracking_LapMarker_extent.active );
  /* UI::setData(unit:laps, value:cardinality lapMarkers) */
  XTUML_OAL_STMT_TRACE( 1, "UI::setData(unit:laps, value:cardinality lapMarkers)" );
  Tracking_UI_setData( GPSWatch_Unit_laps_e, Escher_SetCardinality( lapMarkers ) );
  Escher_ClearSet( lapMarkers );
}

const Escher_xtUMLEventConstant_t Tracking_Display_CBevent1c = {
  Tracking_DOMAIN_ID, Tracking_Display_CLASS_NUMBER_CB, TRACKING_DISPLAY_CBEVENT1NUM,
  ESCHER_IS_ASSIGNER_EVENT };

const Escher_xtUMLEventConstant_t Tracking_Display_CBevent2c = {
  Tracking_DOMAIN_ID, Tracking_Display_CLASS_NUMBER_CB, TRACKING_DISPLAY_CBEVENT2NUM,
  ESCHER_IS_ASSIGNER_EVENT };



/*
 * State-Event Matrix (SEM)
 * Row index is object (MC enumerated) current state.
 * Row zero is the uninitialized state (e.g., for creation event transitions).
 * Column index is (MC enumerated) state machine events.
 */
static const Escher_SEMcell_t Tracking_Display_CB_StateEventMatrix[ 5 + 1 ][ 2 ] = {
  /* row 0:  uninitialized state (for creation events) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN },
  /* row 1:  Tracking_Display_CB_STATE_1 (displayDistance) */
  { Tracking_Display_CB_STATE_1, Tracking_Display_CB_STATE_2 },
  /* row 2:  Tracking_Display_CB_STATE_2 (displaySpeed) */
  { Tracking_Display_CB_STATE_2, Tracking_Display_CB_STATE_3 },
  /* row 3:  Tracking_Display_CB_STATE_3 (displayPace) */
  { Tracking_Display_CB_STATE_3, Tracking_Display_CB_STATE_4 },
  /* row 4:  Tracking_Display_CB_STATE_4 (displayHeartRate) */
  { Tracking_Display_CB_STATE_4, Tracking_Display_CB_STATE_5 },
  /* row 5:  Tracking_Display_CB_STATE_5 (displayLapCount) */
  { Tracking_Display_CB_STATE_5, Tracking_Display_CB_STATE_1 }
};

  /*
   * Array of pointers to the class state action procedures.
   * Index is the (MC enumerated) number of the state action to execute.
   */
  static const StateAction_t Tracking_Display_CB_acts[ 6 ] = {
    (StateAction_t) 0,
    (StateAction_t) Tracking_Display_CB_act1,  /* displayDistance */
    (StateAction_t) Tracking_Display_CB_act2,  /* displaySpeed */
    (StateAction_t) Tracking_Display_CB_act3,  /* displayPace */
    (StateAction_t) Tracking_Display_CB_act4,  /* displayHeartRate */
    (StateAction_t) Tracking_Display_CB_act5  /* displayLapCount */
  };

  /*
   * Array of string names of the state machine names.
   * Index is the (MC enumerated) number of the state.
   */
  static const c_t * const state_name_strings_CB[ 6 ] = {
    "",
    "displayDistance",
    "displaySpeed",
    "displayPace",
    "displayHeartRate",
    "displayLapCount"
  };

/*
 * class-based state machine event dispatching
 */
void
Tracking_Display_CBDispatch( Escher_xtUMLEvent_t * event )
{
  static Escher_InstanceBase_t class_based_singleton = { Tracking_Display_CB_STATE_1 };
  Escher_EventNumber_t event_number = GetOoaEventNumber( event );
  Escher_StateNumber_t current_state = class_based_singleton.current_state;
  Escher_StateNumber_t next_state = Tracking_Display_CB_StateEventMatrix[ current_state ][ event_number ];

  if ( next_state <= 5 ) {
    STATE_TXN_START_TRACE( "Display", current_state, state_name_strings_CB[ current_state ] );
    /* Execute the state action and update the current state.  */
    ( *Tracking_Display_CB_acts[ next_state ] )( &class_based_singleton, event );
    class_based_singleton.current_state = next_state;
    STATE_TXN_END_TRACE( "Display", next_state, state_name_strings_CB[ next_state ] );
  } else {
    if ( EVENT_CANT_HAPPEN == next_state ) {
      /* Event cannot happen.  */
      UserEventCantHappenCallout( current_state, next_state, event_number );
      STATE_TXN_CH_TRACE( "Display", current_state );
    } else if ( EVENT_IS_IGNORED == next_state ) {
      /* Event ignored */
      STATE_TXN_IG_TRACE( "Display", current_state );
    } else {
      /* Translation/memory/stack error, etc - TBD */
    }
  }
}


