/*----------------------------------------------------------------------------
 * File:  Tracking_TrackLog_class.c
 *
 * Class:       TrackLog  (TrackLog)
 * Component:   Tracking
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "GPSWatch_sys_types.h"
#include "LOG_bridge.h"
#include "TIM_bridge.h"
#include "Tracking_classes.h"

/*
 * instance operation:  addTrackPoint
 */
void
Tracking_TrackLog_op_addTrackPoint( Tracking_TrackLog * self, GPSWatch_sdt_Location p_location)
{
  Tracking_WorkoutTimer * workoutTimer = 0; /* workoutTimer (WorkoutTimer) */
 Tracking_TrackPoint * trackPoint; Tracking_TrackPoint * firstPoint = 0; /* firstPoint (TrackPoint) */
 Tracking_TrackPoint * lastPoint = 0; /* lastPoint (TrackPoint) */
 r_t distance; 
  /* SELECT one workoutTimer RELATED BY self->WorkoutTimer[R4] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one workoutTimer RELATED BY self->WorkoutTimer[R4]" );
  workoutTimer = ( 0 != self ) ? self->WorkoutTimer_R4 : 0;
  /* CREATE OBJECT INSTANCE trackPoint OF TrackPoint */
  XTUML_OAL_STMT_TRACE( 1, "CREATE OBJECT INSTANCE trackPoint OF TrackPoint" );
  trackPoint = (Tracking_TrackPoint *) Escher_CreateInstance( Tracking_DOMAIN_ID, Tracking_TrackPoint_CLASS_NUMBER );
  /* ASSIGN trackPoint.time = workoutTimer.time */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN trackPoint.time = workoutTimer.time" );
  trackPoint->time = workoutTimer->time;
  /* ASSIGN trackPoint.longitude = PARAM.location.longitude */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN trackPoint.longitude = PARAM.location.longitude" );
  trackPoint->longitude = p_location.longitude;
  /* ASSIGN trackPoint.latitude = PARAM.location.latitude */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN trackPoint.latitude = PARAM.location.latitude" );
  trackPoint->latitude = p_location.latitude;
  /* ASSIGN trackPoint.speed = PARAM.location.speed */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN trackPoint.speed = PARAM.location.speed" );
  trackPoint->speed = p_location.speed;
  /* SELECT one firstPoint RELATED BY self->TrackPoint[R1] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one firstPoint RELATED BY self->TrackPoint[R1]" );
  firstPoint = ( 0 != self ) ? self->TrackPoint_R1 : 0;
  /* SELECT one lastPoint RELATED BY self->TrackPoint[R3] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one lastPoint RELATED BY self->TrackPoint[R3]" );
  lastPoint = ( 0 != self ) ? self->TrackPoint_R3 : 0;
  /* IF ( empty firstPoint ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( empty firstPoint )" );
  if ( ( 0 == firstPoint ) ) {
    /* RELATE self TO trackPoint ACROSS R1 */
    XTUML_OAL_STMT_TRACE( 2, "RELATE self TO trackPoint ACROSS R1" );
    Tracking_TrackLog_R1_Link( trackPoint, self );
    /* RELATE self TO trackPoint ACROSS R3 */
    XTUML_OAL_STMT_TRACE( 2, "RELATE self TO trackPoint ACROSS R3" );
    Tracking_TrackPoint_R3_Link( self, trackPoint );
  }
  else {
    /* UNRELATE self FROM lastPoint ACROSS R3 */
    XTUML_OAL_STMT_TRACE( 2, "UNRELATE self FROM lastPoint ACROSS R3" );
    Tracking_TrackPoint_R3_Unlink( self, lastPoint );
    /* RELATE self TO trackPoint ACROSS R3 */
    XTUML_OAL_STMT_TRACE( 2, "RELATE self TO trackPoint ACROSS R3" );
    Tracking_TrackPoint_R3_Link( self, trackPoint );
    /* RELATE lastPoint TO trackPoint ACROSS R2 */
    XTUML_OAL_STMT_TRACE( 2, "RELATE lastPoint TO trackPoint ACROSS R2" );
    Tracking_TrackPoint_R2_Link_follows( lastPoint, trackPoint );
  }
  /* ASSIGN distance = 0.0 */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN distance = 0.0" );
  distance = 0.0;
  /* IF ( self.hasLocation ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( self.hasLocation )" );
  if ( self->hasLocation ) {
    /* ASSIGN distance = UTIL::getDistance(fromLocation:self.lastKnownLocation, toLocation:PARAM.location) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN distance = UTIL::getDistance(fromLocation:self.lastKnownLocation, toLocation:PARAM.location)" );
    distance = Tracking_UTIL_getDistance( self->lastKnownLocation, p_location );
  }
  /* ASSIGN self.hasLocation = TRUE */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.hasLocation = TRUE" );
  self->hasLocation = TRUE;
  /* ASSIGN self.lastKnownLocation = PARAM.location */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.lastKnownLocation = PARAM.location" );
  self->lastKnownLocation = p_location;
  /* ASSIGN self.distance = ( self.distance + distance ) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.distance = ( self.distance + distance )" );
  self->distance = ( self->distance + distance );
  /* ASSIGN self.currentSpeed = PARAM.location.speed */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.currentSpeed = PARAM.location.speed" );
  self->currentSpeed = p_location.speed;
  /* GENERATE Display_A2:refresh() TO Display CLASS */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE Display_A2:refresh() TO Display CLASS" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( (void *) 0, &Tracking_Display_CBevent2c );
    Escher_SendEvent( e );
  }


}

/*
 * instance operation:  clearTrackPoints
 */
void
Tracking_TrackLog_op_clearTrackPoints( Tracking_TrackLog * self)
{
  Tracking_TrackPoint * nextPoint = 0; /* nextPoint (TrackPoint) */
 Tracking_TrackPoint * lastPoint = 0; /* lastPoint (TrackPoint) */
 
  /* SELECT one nextPoint RELATED BY self->TrackPoint[R1] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one nextPoint RELATED BY self->TrackPoint[R1]" );
  nextPoint = ( 0 != self ) ? self->TrackPoint_R1 : 0;
  /* SELECT one lastPoint RELATED BY self->TrackPoint[R3] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one lastPoint RELATED BY self->TrackPoint[R3]" );
  lastPoint = ( 0 != self ) ? self->TrackPoint_R3 : 0;
  /* IF ( not empty lastPoint ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not empty lastPoint )" );
  if ( !( 0 == lastPoint ) ) {
    /* UNRELATE self FROM lastPoint ACROSS R3 */
    XTUML_OAL_STMT_TRACE( 2, "UNRELATE self FROM lastPoint ACROSS R3" );
    Tracking_TrackPoint_R3_Unlink( self, lastPoint );
  }
  /* IF ( not empty nextPoint ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not empty nextPoint )" );
  if ( !( 0 == nextPoint ) ) {
    /* UNRELATE self FROM nextPoint ACROSS R1 */
    XTUML_OAL_STMT_TRACE( 2, "UNRELATE self FROM nextPoint ACROSS R1" );
    Tracking_TrackLog_R1_Unlink( nextPoint, self );
  }
  /* WHILE ( not empty nextPoint ) */
  XTUML_OAL_STMT_TRACE( 1, "WHILE ( not empty nextPoint )" );
  while ( !( 0 == nextPoint ) ) {
    Tracking_TrackPoint * prevPoint; 
    /* ASSIGN prevPoint = nextPoint */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN prevPoint = nextPoint" );
    prevPoint = nextPoint;
    /* SELECT one nextPoint RELATED BY nextPoint->TrackPoint[R2.follows] */
    XTUML_OAL_STMT_TRACE( 2, "SELECT one nextPoint RELATED BY nextPoint->TrackPoint[R2.follows]" );
    nextPoint = ( 0 != nextPoint ) ? nextPoint->TrackPoint_R2_follows : 0;
    /* IF ( not_empty nextPoint ) */
    XTUML_OAL_STMT_TRACE( 2, "IF ( not_empty nextPoint )" );
    if ( ( 0 != nextPoint ) ) {
      /* UNRELATE prevPoint FROM nextPoint ACROSS R2 */
      XTUML_OAL_STMT_TRACE( 3, "UNRELATE prevPoint FROM nextPoint ACROSS R2" );
      Tracking_TrackPoint_R2_Unlink_follows( prevPoint, nextPoint );
    }
    /* DELETE OBJECT INSTANCE prevPoint */
    XTUML_OAL_STMT_TRACE( 2, "DELETE OBJECT INSTANCE prevPoint" );
    if ( 0 == prevPoint ) {
      XTUML_EMPTY_HANDLE_TRACE( "TrackPoint", "Escher_DeleteInstance" );
    }
    Escher_DeleteInstance( (Escher_iHandle_t) prevPoint, Tracking_DOMAIN_ID, Tracking_TrackPoint_CLASS_NUMBER );
  }

}

/*
 * instance operation:  addLapMarker
 */
void
Tracking_TrackLog_op_addLapMarker( Tracking_TrackLog * self)
{
  Tracking_WorkoutTimer * timer = 0; /* timer (WorkoutTimer) */
 Tracking_LapMarker * lapMarker; 
  /* SELECT one timer RELATED BY self->WorkoutTimer[R4] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one timer RELATED BY self->WorkoutTimer[R4]" );
  timer = ( 0 != self ) ? self->WorkoutTimer_R4 : 0;
  /* CREATE OBJECT INSTANCE lapMarker OF LapMarker */
  XTUML_OAL_STMT_TRACE( 1, "CREATE OBJECT INSTANCE lapMarker OF LapMarker" );
  lapMarker = (Tracking_LapMarker *) Escher_CreateInstance( Tracking_DOMAIN_ID, Tracking_LapMarker_CLASS_NUMBER );
  /* ASSIGN lapMarker.lapTime = timer.time */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN lapMarker.lapTime = timer.time" );
  lapMarker->lapTime = timer->time;
  /* RELATE self TO lapMarker ACROSS R5 */
  XTUML_OAL_STMT_TRACE( 1, "RELATE self TO lapMarker ACROSS R5" );
  Tracking_LapMarker_R5_Link( self, lapMarker );
  /* GENERATE Display_A2:refresh() TO Display CLASS */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE Display_A2:refresh() TO Display CLASS" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( (void *) 0, &Tracking_Display_CBevent2c );
    Escher_SendEvent( e );
  }


}

/*
 * instance operation:  clearLapMarkers
 */
void
Tracking_TrackLog_op_clearLapMarkers( Tracking_TrackLog * self)
{
  Escher_ObjectSet_s lapMarkers_space={0}; Escher_ObjectSet_s * lapMarkers = &lapMarkers_space; /* lapMarkers (LapMarker) */
 Tracking_LapMarker * lapMarker=0; 
  /* SELECT many lapMarkers RELATED BY self->LapMarker[R5] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT many lapMarkers RELATED BY self->LapMarker[R5]" );
  Escher_ClearSet( lapMarkers );
  if ( 0 != self ) {
    Escher_CopySet( lapMarkers, &self->LapMarker_R5 );
  }
  /* FOR EACH lapMarker IN lapMarkers */
  XTUML_OAL_STMT_TRACE( 1, "FOR EACH lapMarker IN lapMarkers" );
  { Escher_Iterator_s iterlapMarker;
  Tracking_LapMarker * iilapMarker;
  Escher_IteratorReset( &iterlapMarker, lapMarkers );
  while ( (iilapMarker = (Tracking_LapMarker *)Escher_IteratorNext( &iterlapMarker )) != 0 ) {
    lapMarker = iilapMarker; {
    /* UNRELATE self FROM lapMarker ACROSS R5 */
    XTUML_OAL_STMT_TRACE( 2, "UNRELATE self FROM lapMarker ACROSS R5" );
    Tracking_LapMarker_R5_Unlink( self, lapMarker );
    /* DELETE OBJECT INSTANCE lapMarker */
    XTUML_OAL_STMT_TRACE( 2, "DELETE OBJECT INSTANCE lapMarker" );
    if ( 0 == lapMarker ) {
      XTUML_EMPTY_HANDLE_TRACE( "LapMarker", "Escher_DeleteInstance" );
    }
    Escher_DeleteInstance( (Escher_iHandle_t) lapMarker, Tracking_DOMAIN_ID, Tracking_LapMarker_CLASS_NUMBER );
  }}}
    Escher_ClearSet( lapMarkers ); /* lapMarkers (LapMarker) */

}

/*
 * instance operation:  addHeartRateSample
 */
void
Tracking_TrackLog_op_addHeartRateSample( Tracking_TrackLog * self, const r_t p_heartRate)
{
  Tracking_HeartRateSample * sample; 
  /* CREATE OBJECT INSTANCE sample OF HeartRateSample */
  XTUML_OAL_STMT_TRACE( 1, "CREATE OBJECT INSTANCE sample OF HeartRateSample" );
  sample = (Tracking_HeartRateSample *) Escher_CreateInstance( Tracking_DOMAIN_ID, Tracking_HeartRateSample_CLASS_NUMBER );
  /* ASSIGN sample.heartRate = PARAM.heartRate */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN sample.heartRate = PARAM.heartRate" );
  sample->heartRate = p_heartRate;
  /* RELATE self TO sample ACROSS R6 */
  XTUML_OAL_STMT_TRACE( 1, "RELATE self TO sample ACROSS R6" );
  Tracking_HeartRateSample_R6_Link( self, sample );
  /* ASSIGN self.currentHeartRate = PARAM.heartRate */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.currentHeartRate = PARAM.heartRate" );
  self->currentHeartRate = p_heartRate;
  /* GENERATE Display_A2:refresh() TO Display CLASS */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE Display_A2:refresh() TO Display CLASS" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( (void *) 0, &Tracking_Display_CBevent2c );
    Escher_SendEvent( e );
  }


}

/*
 * instance operation:  clearHeartRateSamples
 */
void
Tracking_TrackLog_op_clearHeartRateSamples( Tracking_TrackLog * self)
{
  Escher_ObjectSet_s samples_space={0}; Escher_ObjectSet_s * samples = &samples_space; /* samples (HeartRateSample) */
 Tracking_HeartRateSample * sample=0; 
  /* SELECT many samples RELATED BY self->HeartRateSample[R6] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT many samples RELATED BY self->HeartRateSample[R6]" );
  Escher_ClearSet( samples );
  if ( 0 != self ) {
    Escher_CopySet( samples, &self->HeartRateSample_R6 );
  }
  /* FOR EACH sample IN samples */
  XTUML_OAL_STMT_TRACE( 1, "FOR EACH sample IN samples" );
  { Escher_Iterator_s itersample;
  Tracking_HeartRateSample * iisample;
  Escher_IteratorReset( &itersample, samples );
  while ( (iisample = (Tracking_HeartRateSample *)Escher_IteratorNext( &itersample )) != 0 ) {
    sample = iisample; {
    /* UNRELATE self FROM sample ACROSS R6 */
    XTUML_OAL_STMT_TRACE( 2, "UNRELATE self FROM sample ACROSS R6" );
    Tracking_HeartRateSample_R6_Unlink( self, sample );
    /* DELETE OBJECT INSTANCE sample */
    XTUML_OAL_STMT_TRACE( 2, "DELETE OBJECT INSTANCE sample" );
    if ( 0 == sample ) {
      XTUML_EMPTY_HANDLE_TRACE( "HeartRateSample", "Escher_DeleteInstance" );
    }
    Escher_DeleteInstance( (Escher_iHandle_t) sample, Tracking_DOMAIN_ID, Tracking_HeartRateSample_CLASS_NUMBER );
  }}}
    Escher_ClearSet( samples ); /* samples (HeartRateSample) */

}

/*
 * instance operation:  init
 */
void
Tracking_TrackLog_op_init( Tracking_TrackLog * self)
{
  /* ASSIGN self.startTime = TIM::current_clock() */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.startTime = TIM::current_clock()" );
  self->startTime = TIM_current_clock();
  /* ASSIGN self.distance = 0.0 */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.distance = 0.0" );
  self->distance = 0.0;
  /* ASSIGN self.currentHeartRate = 0.0 */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.currentHeartRate = 0.0" );
  self->currentHeartRate = 0.0;
  /* ASSIGN self.currentSpeed = 0.0 */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.currentSpeed = 0.0" );
  self->currentSpeed = 0.0;
  /* ASSIGN self.hasLocation = FALSE */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.hasLocation = FALSE" );
  self->hasLocation = FALSE;

}


/*
 * RELATE TrackPoint TO TrackLog ACROSS R1
 */
void
Tracking_TrackLog_R1_Link( Tracking_TrackPoint * part, Tracking_TrackLog * form )
{
  if ( (part == 0) || (form == 0) ) {
    XTUML_EMPTY_HANDLE_TRACE( "TrackLog", "Tracking_TrackLog_R1_Link" );
    return;
  }
  form->TrackPoint_R1 = part;
  /* Note:  TrackPoint->TrackLog[R1] not navigated */
}

/*
 * UNRELATE TrackPoint FROM TrackLog ACROSS R1
 */
void
Tracking_TrackLog_R1_Unlink( Tracking_TrackPoint * part, Tracking_TrackLog * form )
{
  if ( (part == 0) || (form == 0) ) {
    XTUML_EMPTY_HANDLE_TRACE( "TrackLog", "Tracking_TrackLog_R1_Unlink" );
    return;
  }
  form->TrackPoint_R1 = 0;
  /* Note:  TrackPoint->TrackLog[R1] not navigated */
}


/*----------------------------------------------------------------------------
 * Operation action methods implementation for the following class:
 *
 * Class:      TrackLog  (TrackLog)
 * Component:  Tracking
 *--------------------------------------------------------------------------*/
/*
 * Statically allocate space for the instance population for this class.
 * Allocate space for the class instance and its attribute values.
 * Depending upon the collection scheme, allocate containoids (collection
 * nodes) for gathering instances into free and active extents.
 */
static Escher_SetElement_s Tracking_TrackLog_container[ Tracking_TrackLog_MAX_EXTENT_SIZE ];
static Tracking_TrackLog Tracking_TrackLog_instances[ Tracking_TrackLog_MAX_EXTENT_SIZE ];
Escher_Extent_t pG_Tracking_TrackLog_extent = {
  {0}, {0}, &Tracking_TrackLog_container[ 0 ],
  (Escher_iHandle_t) &Tracking_TrackLog_instances,
  sizeof( Tracking_TrackLog ), 0, Tracking_TrackLog_MAX_EXTENT_SIZE
  };
/*
 * Mathematically Dependent Attribute:  currentPace
 */
r_t
Tracking_TrackLog_MDA_currentPace( Tracking_TrackLog * self )
{
  /* IF ( ( self.currentSpeed != 0 ) ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( ( self.currentSpeed != 0 ) )" );
  if ( ( self->currentSpeed != 0 ) ) {
    /* ASSIGN self.currentPace = ( 60.0 / self.currentSpeed ) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN self.currentPace = ( 60.0 / self.currentSpeed )" );
    self->currentPace = ( 60.0 / self->currentSpeed );
  }
  else {
    /* ASSIGN self.currentPace = 0.0 */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN self.currentPace = 0.0" );
    self->currentPace = 0.0;
  }

  return self->currentPace;
}


