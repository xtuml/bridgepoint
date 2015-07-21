/*----------------------------------------------------------------------------
 * File:  Tracking_WorkoutTimer_class.c
 *
 * Class:       WorkoutTimer  (WorkoutTimer)
 * Component:   Tracking
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "GPSWatch_sys_types.h"
#include "LOG_bridge.h"
#include "TIM_bridge.h"
#include "Tracking_classes.h"

/*
 * instance operation:  activate
 */
void
Tracking_WorkoutTimer_op_activate( Tracking_WorkoutTimer * self)
{
  Escher_xtUMLEvent_t * evt;  /* evt */ 
  /* CREATE EVENT INSTANCE evt(  ) TO self */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE evt(  ) TO self" );
  evt = Escher_NewxtUMLEvent( (void *) self, &Tracking_WorkoutTimerevent3c );
  /* ASSIGN self.timer = TIM::timer_start_recurring(event_inst:evt, microseconds:1000000) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.timer = TIM::timer_start_recurring(event_inst:evt, microseconds:1000000)" );
  self->timer = TIM_timer_start_recurring( (Escher_xtUMLEvent_t *)evt, 1000000 );
  /*  SEND LOC::registerListener() */
  XTUML_OAL_STMT_TRACE( 1, " SEND LOC::registerListener()" );
  Tracking_LOC_registerListener();
  /*  SEND HR::registerListener() */
  XTUML_OAL_STMT_TRACE( 1, " SEND HR::registerListener()" );
  Tracking_HR_registerListener();

}

/*
 * instance operation:  deactivate
 */
void
Tracking_WorkoutTimer_op_deactivate( Tracking_WorkoutTimer * self)
{
  bool res; 
  /* ASSIGN res = TIM::timer_cancel(timer_inst_ref:self.timer) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN res = TIM::timer_cancel(timer_inst_ref:self.timer)" );
  res = TIM_timer_cancel( self->timer );
  /*  SEND LOC::unregisterListener() */
  XTUML_OAL_STMT_TRACE( 1, " SEND LOC::unregisterListener()" );
  Tracking_LOC_unregisterListener();
  /*  SEND HR::unregisterListener() */
  XTUML_OAL_STMT_TRACE( 1, " SEND HR::unregisterListener()" );
  Tracking_HR_unregisterListener();

}


/*
 * RELATE TrackLog TO WorkoutTimer ACROSS R4
 */
void
Tracking_WorkoutTimer_R4_Link( Tracking_TrackLog * part, Tracking_WorkoutTimer * form )
{
  if ( (part == 0) || (form == 0) ) {
    XTUML_EMPTY_HANDLE_TRACE( "WorkoutTimer", "Tracking_WorkoutTimer_R4_Link" );
    return;
  }
  form->TrackLog_R4 = part;
  part->WorkoutTimer_R4 = form;
}

/*
 * UNRELATE TrackLog FROM WorkoutTimer ACROSS R4
 */
void
Tracking_WorkoutTimer_R4_Unlink( Tracking_TrackLog * part, Tracking_WorkoutTimer * form )
{
  if ( (part == 0) || (form == 0) ) {
    XTUML_EMPTY_HANDLE_TRACE( "WorkoutTimer", "Tracking_WorkoutTimer_R4_Unlink" );
    return;
  }
  form->TrackLog_R4 = 0;
  part->WorkoutTimer_R4 = 0;
}


/*----------------------------------------------------------------------------
 * Operation action methods implementation for the following class:
 *
 * Class:      WorkoutTimer  (WorkoutTimer)
 * Component:  Tracking
 *--------------------------------------------------------------------------*/
/*
 * Statically allocate space for the instance population for this class.
 * Allocate space for the class instance and its attribute values.
 * Depending upon the collection scheme, allocate containoids (collection
 * nodes) for gathering instances into free and active extents.
 */
static Escher_SetElement_s Tracking_WorkoutTimer_container[ Tracking_WorkoutTimer_MAX_EXTENT_SIZE ];
static Tracking_WorkoutTimer Tracking_WorkoutTimer_instances[ Tracking_WorkoutTimer_MAX_EXTENT_SIZE ];
Escher_Extent_t pG_Tracking_WorkoutTimer_extent = {
  {0}, {0}, &Tracking_WorkoutTimer_container[ 0 ],
  (Escher_iHandle_t) &Tracking_WorkoutTimer_instances,
  sizeof( Tracking_WorkoutTimer ), Tracking_WorkoutTimer_STATE_1, Tracking_WorkoutTimer_MAX_EXTENT_SIZE
  };
/*----------------------------------------------------------------------------
 * State and transition action implementations for the following class:
 *
 * Class:      WorkoutTimer  (WorkoutTimer)
 * Component:  Tracking
 *--------------------------------------------------------------------------*/

/*
 * State 1:  [stopped]
 */
static void Tracking_WorkoutTimer_act1( Tracking_WorkoutTimer *, const Escher_xtUMLEvent_t * const );
static void
Tracking_WorkoutTimer_act1( Tracking_WorkoutTimer * self, const Escher_xtUMLEvent_t * const event )
{
  Tracking_TrackLog * trackLog = 0; /* trackLog (TrackLog) */
 
  /* ASSIGN self.time = 0 */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.time = 0" );
  self->time = 0;
  /* UI::setTime(time:self.time) */
  XTUML_OAL_STMT_TRACE( 1, "UI::setTime(time:self.time)" );
  Tracking_UI_setTime( self->time );
  /* SELECT one trackLog RELATED BY self->TrackLog[R4] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one trackLog RELATED BY self->TrackLog[R4]" );
  trackLog = ( 0 != self ) ? self->TrackLog_R4 : 0;
  /* trackLog.clearTrackPoints() */
  XTUML_OAL_STMT_TRACE( 1, "trackLog.clearTrackPoints()" );
  Tracking_TrackLog_op_clearTrackPoints( trackLog );
  /* trackLog.clearLapMarkers() */
  XTUML_OAL_STMT_TRACE( 1, "trackLog.clearLapMarkers()" );
  Tracking_TrackLog_op_clearLapMarkers( trackLog );
  /* trackLog.clearHeartRateSamples() */
  XTUML_OAL_STMT_TRACE( 1, "trackLog.clearHeartRateSamples()" );
  Tracking_TrackLog_op_clearHeartRateSamples( trackLog );
  /* GENERATE Display_A2:refresh() TO Display CLASS */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE Display_A2:refresh() TO Display CLASS" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( (void *) 0, &Tracking_Display_CBevent2c );
    Escher_SendEvent( e );
  }

  /* UNRELATE self FROM trackLog ACROSS R4 */
  XTUML_OAL_STMT_TRACE( 1, "UNRELATE self FROM trackLog ACROSS R4" );
  Tracking_WorkoutTimer_R4_Unlink( trackLog, self );
  /* DELETE OBJECT INSTANCE trackLog */
  XTUML_OAL_STMT_TRACE( 1, "DELETE OBJECT INSTANCE trackLog" );
  if ( 0 == trackLog ) {
    XTUML_EMPTY_HANDLE_TRACE( "TrackLog", "Escher_DeleteInstance" );
  }
  Escher_DeleteInstance( (Escher_iHandle_t) trackLog, Tracking_DOMAIN_ID, Tracking_TrackLog_CLASS_NUMBER );
}

/*
 * State 2:  [running]
 */
static void Tracking_WorkoutTimer_act2( Tracking_WorkoutTimer *, const Escher_xtUMLEvent_t * const );
static void
Tracking_WorkoutTimer_act2( Tracking_WorkoutTimer * self, const Escher_xtUMLEvent_t * const event )
{
}

/*
 * State 3:  [paused]
 */
static void Tracking_WorkoutTimer_act3( Tracking_WorkoutTimer *, const Escher_xtUMLEvent_t * const );
static void
Tracking_WorkoutTimer_act3( Tracking_WorkoutTimer * self, const Escher_xtUMLEvent_t * const event )
{
}

/*
 */
static void Tracking_WorkoutTimer_xact1( Tracking_WorkoutTimer *, const Escher_xtUMLEvent_t * const );
static void
Tracking_WorkoutTimer_xact1( Tracking_WorkoutTimer * self, const Escher_xtUMLEvent_t * const event )
{
  Tracking_TrackLog * trackLog; 
  /* CREATE OBJECT INSTANCE trackLog OF TrackLog */
  XTUML_OAL_STMT_TRACE( 1, "CREATE OBJECT INSTANCE trackLog OF TrackLog" );
  trackLog = (Tracking_TrackLog *) Escher_CreateInstance( Tracking_DOMAIN_ID, Tracking_TrackLog_CLASS_NUMBER );
  /* RELATE self TO trackLog ACROSS R4 */
  XTUML_OAL_STMT_TRACE( 1, "RELATE self TO trackLog ACROSS R4" );
  Tracking_WorkoutTimer_R4_Link( trackLog, self );
  /* trackLog.init() */
  XTUML_OAL_STMT_TRACE( 1, "trackLog.init()" );
  Tracking_TrackLog_op_init( trackLog );
  /* self.activate() */
  XTUML_OAL_STMT_TRACE( 1, "self.activate()" );
  Tracking_WorkoutTimer_op_activate( self );
}

/*
 */
static void Tracking_WorkoutTimer_xact2( Tracking_WorkoutTimer *, const Escher_xtUMLEvent_t * const );
static void
Tracking_WorkoutTimer_xact2( Tracking_WorkoutTimer * self, const Escher_xtUMLEvent_t * const event )
{
  Tracking_TrackLog * trackLog=0; 
  /* SELECT any trackLog FROM INSTANCES OF TrackLog */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any trackLog FROM INSTANCES OF TrackLog" );
  trackLog = (Tracking_TrackLog *) Escher_SetGetAny( &pG_Tracking_TrackLog_extent.active );
  /* IF ( not empty trackLog ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not empty trackLog )" );
  if ( !( 0 == trackLog ) ) {
    /* trackLog.addLapMarker() */
    XTUML_OAL_STMT_TRACE( 2, "trackLog.addLapMarker()" );
    Tracking_TrackLog_op_addLapMarker( trackLog );
  }
}

/*
 */
static void Tracking_WorkoutTimer_xact3( Tracking_WorkoutTimer *, const Escher_xtUMLEvent_t * const );
static void
Tracking_WorkoutTimer_xact3( Tracking_WorkoutTimer * self, const Escher_xtUMLEvent_t * const event )
{
  /* self.activate() */
  XTUML_OAL_STMT_TRACE( 1, "self.activate()" );
  Tracking_WorkoutTimer_op_activate( self );
}

/*
 */
static void Tracking_WorkoutTimer_xact4( Tracking_WorkoutTimer *, const Escher_xtUMLEvent_t * const );
static void
Tracking_WorkoutTimer_xact4( Tracking_WorkoutTimer * self, const Escher_xtUMLEvent_t * const event )
{
  /* self.deactivate() */
  XTUML_OAL_STMT_TRACE( 1, "self.deactivate()" );
  Tracking_WorkoutTimer_op_deactivate( self );
}

/*
 */
static void Tracking_WorkoutTimer_xact5( Tracking_WorkoutTimer *, const Escher_xtUMLEvent_t * const );
static void
Tracking_WorkoutTimer_xact5( Tracking_WorkoutTimer * self, const Escher_xtUMLEvent_t * const event )
{
  /* ASSIGN self.time = ( self.time + 1 ) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.time = ( self.time + 1 )" );
  self->time = ( self->time + 1 );
  /* UI::setTime(time:self.time) */
  XTUML_OAL_STMT_TRACE( 1, "UI::setTime(time:self.time)" );
  Tracking_UI_setTime( self->time );
}

const Escher_xtUMLEventConstant_t Tracking_WorkoutTimerevent1c = {
  Tracking_DOMAIN_ID, Tracking_WorkoutTimer_CLASS_NUMBER, TRACKING_WORKOUTTIMEREVENT1NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t Tracking_WorkoutTimerevent2c = {
  Tracking_DOMAIN_ID, Tracking_WorkoutTimer_CLASS_NUMBER, TRACKING_WORKOUTTIMEREVENT2NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t Tracking_WorkoutTimerevent3c = {
  Tracking_DOMAIN_ID, Tracking_WorkoutTimer_CLASS_NUMBER, TRACKING_WORKOUTTIMEREVENT3NUM,
  ESCHER_IS_INSTANCE_EVENT };



/*
 * State-Event Matrix (SEM)
 * Row index is object (MC enumerated) current state.
 * Row zero is the uninitialized state (e.g., for creation event transitions).
 * Column index is (MC enumerated) state machine events.
 */
static const Escher_SEMcell_t Tracking_WorkoutTimer_StateEventMatrix[ 3 + 1 ][ 3 ] = {
  /* row 0:  uninitialized state (for creation events) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN },
  /* row 1:  Tracking_WorkoutTimer_STATE_1 (stopped) */
  { (1<<8) + Tracking_WorkoutTimer_STATE_2, EVENT_IS_IGNORED, EVENT_IS_IGNORED },
  /* row 2:  Tracking_WorkoutTimer_STATE_2 (running) */
  { (4<<8) + Tracking_WorkoutTimer_STATE_3, (2<<8) + Tracking_WorkoutTimer_STATE_2, (5<<8) + Tracking_WorkoutTimer_STATE_2 },
  /* row 3:  Tracking_WorkoutTimer_STATE_3 (paused) */
  { (3<<8) + Tracking_WorkoutTimer_STATE_2, Tracking_WorkoutTimer_STATE_1, EVENT_IS_IGNORED }
};

  /*
   * Array of pointers to the class state action procedures.
   * Index is the (MC enumerated) number of the state action to execute.
   */
  static const StateAction_t Tracking_WorkoutTimer_acts[ 4 ] = {
    (StateAction_t) 0,
    (StateAction_t) Tracking_WorkoutTimer_act1,  /* stopped */
    (StateAction_t) Tracking_WorkoutTimer_act2,  /* running */
    (StateAction_t) Tracking_WorkoutTimer_act3  /* paused */
  };

  /*
   * Array of string names of the state machine names.
   * Index is the (MC enumerated) number of the state.
   */
  static const c_t * const state_name_strings[ 4 ] = {
    "",
    "stopped",
    "running",
    "paused"
  };

  /*
   * Array of pointers to the class transition action procedures.
   * Index is the (MC enumerated) number of the transition action to execute.
   */
  static const StateAction_t Tracking_WorkoutTimer_xacts[ 5 ] = {
    (StateAction_t) Tracking_WorkoutTimer_xact1,
    (StateAction_t) Tracking_WorkoutTimer_xact2,
    (StateAction_t) Tracking_WorkoutTimer_xact3,
    (StateAction_t) Tracking_WorkoutTimer_xact4,
    (StateAction_t) Tracking_WorkoutTimer_xact5
  };

/*
 * instance state machine event dispatching
 */
void
Tracking_WorkoutTimer_Dispatch( Escher_xtUMLEvent_t * event )
{
  Escher_iHandle_t instance = GetEventTargetInstance( event );
  Escher_EventNumber_t event_number = GetOoaEventNumber( event );
  Escher_StateNumber_t current_state;
  Escher_SEMcell_t next_state;
  
  if ( 0 != instance ) {
    current_state = instance->current_state;
    if ( current_state > 3 ) {
      /* instance validation failure (object deleted while event in flight) */
      UserEventNoInstanceCallout( event_number );
    } else {
      next_state = Tracking_WorkoutTimer_StateEventMatrix[ current_state ][ event_number ];
      if ( next_state <= 3 ) {
        STATE_TXN_START_TRACE( "WorkoutTimer", current_state, state_name_strings[ current_state ] );
        /* Execute the state action and update the current state.  */
        ( *Tracking_WorkoutTimer_acts[ next_state ] )( instance, event );
        STATE_TXN_END_TRACE( "WorkoutTimer", next_state, state_name_strings[ next_state ] );
        instance->current_state = next_state;
      } else if ( next_state == EVENT_IS_IGNORED ) {
          /* event ignored */
          STATE_TXN_IG_TRACE( "WorkoutTimer", current_state );
      } else {
        STATE_TXN_START_TRACE( "WorkoutTimer", current_state, state_name_strings[ current_state ] );
        ( *Tracking_WorkoutTimer_xacts[ (next_state>>8)-1 ] )( instance, event );
        next_state = next_state & 0x00ff;
        instance->current_state = next_state;
        ( *Tracking_WorkoutTimer_acts[ next_state ] )( instance, event );
        STATE_TXN_END_TRACE( "WorkoutTimer", next_state, state_name_strings[ next_state ] );
      }
    }
  }
}


