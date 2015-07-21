/*----------------------------------------------------------------------------
 * File:  Tracking.c
 *
 * UML Component Port Messages
 * Component/Module Name:  Tracking
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/
/*
 The Tracking component encapsulates the entire application software. This is the 
only component in the system from which code will be generated for the final 
product.
 */

#include "GPSWatch_sys_types.h"
#include "Tracking.h"
#include "LOG_bridge.h"
#include "TIM_bridge.h"
#include "Tracking_classes.h"

/*
 * Interface:  LocationProvider
 * Required Port:  LOC
 * From Provider Message:  locationUpdate
 */
void
Tracking_LOC_locationUpdate( GPSWatch_sdt_Location p_location)
{
  Tracking_TrackLog * trackLog=0; 
  /* LOG::LogInfo( message:location updated:  ) */
  XTUML_OAL_STMT_TRACE( 1, "LOG::LogInfo( message:location updated:  )" );
  LOG_LogInfo( "location updated: " );
  /* LOG::LogReal( message:longitude, r:PARAM.location.longitude ) */
  XTUML_OAL_STMT_TRACE( 1, "LOG::LogReal( message:longitude, r:PARAM.location.longitude )" );
  LOG_LogReal( "longitude", p_location.longitude );
  /* LOG::LogReal( message:latitude, r:PARAM.location.latitude ) */
  XTUML_OAL_STMT_TRACE( 1, "LOG::LogReal( message:latitude, r:PARAM.location.latitude )" );
  LOG_LogReal( "latitude", p_location.latitude );
  /* LOG::LogReal( message:speed, r:PARAM.location.speed ) */
  XTUML_OAL_STMT_TRACE( 1, "LOG::LogReal( message:speed, r:PARAM.location.speed )" );
  LOG_LogReal( "speed", p_location.speed );
  /* SELECT any trackLog FROM INSTANCES OF TrackLog */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any trackLog FROM INSTANCES OF TrackLog" );
  trackLog = (Tracking_TrackLog *) Escher_SetGetAny( &pG_Tracking_TrackLog_extent.active );
  /* IF ( not empty trackLog ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not empty trackLog )" );
  if ( !( 0 == trackLog ) ) {
    /* trackLog.addTrackPoint( location:PARAM.location ) */
    XTUML_OAL_STMT_TRACE( 2, "trackLog.addTrackPoint( location:PARAM.location )" );
    Tracking_TrackLog_op_addTrackPoint( trackLog,  p_location );
  }
}

/*
 * Interface:  LocationProvider
 * Required Port:  LOC
 * To Provider Message:  registerListener
 */
void
Tracking_LOC_registerListener()
{
  Location_LOC_registerListener();
}

/*
 * Interface:  LocationProvider
 * Required Port:  LOC
 * To Provider Message:  unregisterListener
 */
void
Tracking_LOC_unregisterListener()
{
  Location_LOC_unregisterListener();
}

/*
 * Interface:  UI
 * Required Port:  UI
 * From Provider Message:  lapResetPressed
 */
void
Tracking_UI_lapResetPressed()
{
  Tracking_WorkoutTimer * workoutTimer=0; 
  /* SELECT any workoutTimer FROM INSTANCES OF WorkoutTimer */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any workoutTimer FROM INSTANCES OF WorkoutTimer" );
  workoutTimer = (Tracking_WorkoutTimer *) Escher_SetGetAny( &pG_Tracking_WorkoutTimer_extent.active );
  /* IF ( not empty workoutTimer ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not empty workoutTimer )" );
  if ( !( 0 == workoutTimer ) ) {
    /* GENERATE WorkoutTimer2:lapResetPressed() TO workoutTimer */
    XTUML_OAL_STMT_TRACE( 2, "GENERATE WorkoutTimer2:lapResetPressed() TO workoutTimer" );
    { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( workoutTimer, &Tracking_WorkoutTimerevent2c );
      Escher_SendEvent( e );
    }
  }
}

/*
 * Interface:  UI
 * Required Port:  UI
 * From Provider Message:  lightPressed
 */
void
Tracking_UI_lightPressed()
{
}

/*
 * Interface:  UI
 * Required Port:  UI
 * From Provider Message:  modePressed
 */
void
Tracking_UI_modePressed()
{
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( (void *) 0, &Tracking_Display_CBevent1c );
    Escher_SendEvent( e );
  }

}

/*
 * Interface:  UI
 * Required Port:  UI
 * To Provider Message:  setData
 */
void
Tracking_UI_setData( const GPSWatch_Unit_t p_unit, const r_t p_value)
{
  UI_UI_setData(  p_unit, p_value );
}

/*
 * Interface:  UI
 * Required Port:  UI
 * From Provider Message:  setTargetPressed
 */
void
Tracking_UI_setTargetPressed()
{
}

/*
 * Interface:  UI
 * Required Port:  UI
 * To Provider Message:  setTime
 */
void
Tracking_UI_setTime( const i_t p_time)
{
  UI_UI_setTime(  p_time );
}

/*
 * Interface:  UI
 * Required Port:  UI
 * From Provider Message:  startStopPressed
 */
void
Tracking_UI_startStopPressed()
{
  Tracking_WorkoutTimer * workoutTimer=0; 
  /* SELECT any workoutTimer FROM INSTANCES OF WorkoutTimer */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any workoutTimer FROM INSTANCES OF WorkoutTimer" );
  workoutTimer = (Tracking_WorkoutTimer *) Escher_SetGetAny( &pG_Tracking_WorkoutTimer_extent.active );
  /* IF ( empty workoutTimer ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( empty workoutTimer )" );
  if ( ( 0 == workoutTimer ) ) {
    /* CREATE OBJECT INSTANCE workoutTimer OF WorkoutTimer */
    XTUML_OAL_STMT_TRACE( 2, "CREATE OBJECT INSTANCE workoutTimer OF WorkoutTimer" );
    workoutTimer = (Tracking_WorkoutTimer *) Escher_CreateInstance( Tracking_DOMAIN_ID, Tracking_WorkoutTimer_CLASS_NUMBER );
    /* ASSIGN workoutTimer.time = 0 */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN workoutTimer.time = 0" );
    workoutTimer->time = 0;
  }
  /* GENERATE WorkoutTimer1:startStopPressed() TO workoutTimer */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE WorkoutTimer1:startStopPressed() TO workoutTimer" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( workoutTimer, &Tracking_WorkoutTimerevent1c );
    Escher_SendEvent( e );
  }
}

/*
 * Interface:  UI
 * Required Port:  UI
 * To Provider Message:  startTest
 */
void
Tracking_UI_startTest()
{
  UI_UI_startTest();
}

/*
 * Interface:  HeartRateProvider
 * Required Port:  HR
 * From Provider Message:  heartRateChanged
 */
void
Tracking_HR_heartRateChanged( const r_t p_heartRate)
{
  Tracking_TrackLog * trackLog=0; 
  /* SELECT any trackLog FROM INSTANCES OF TrackLog */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any trackLog FROM INSTANCES OF TrackLog" );
  trackLog = (Tracking_TrackLog *) Escher_SetGetAny( &pG_Tracking_TrackLog_extent.active );
  /* IF ( not empty trackLog ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( not empty trackLog )" );
  if ( !( 0 == trackLog ) ) {
    /* trackLog.addHeartRateSample( heartRate:PARAM.heartRate ) */
    XTUML_OAL_STMT_TRACE( 2, "trackLog.addHeartRateSample( heartRate:PARAM.heartRate )" );
    Tracking_TrackLog_op_addHeartRateSample( trackLog,  p_heartRate );
  }
}

/*
 * Interface:  HeartRateProvider
 * Required Port:  HR
 * To Provider Message:  registerListener
 */
void
Tracking_HR_registerListener()
{
  HeartRateMonitor_HR_registerListener();
}

/*
 * Interface:  HeartRateProvider
 * Required Port:  HR
 * To Provider Message:  unregisterListener
 */
void
Tracking_HR_unregisterListener()
{
  HeartRateMonitor_HR_unregisterListener();
}

/*
 * Interface:  LocationUtil
 * Required Port:  UTIL
 * To Provider Message:  getDistance
 */
r_t
Tracking_UTIL_getDistance( GPSWatch_sdt_Location p_fromLocation, GPSWatch_sdt_Location p_toLocation)
{
return   Location_UTIL_getDistance(  p_fromLocation, p_toLocation );
}

/*
 * UML Domain Functions (Synchronous Services)
 */

#if Tracking_MAX_CLASS_NUMBERS > 0
/* xtUML class info (collections, sizes, etc.) */
Escher_Extent_t * const Tracking_class_info[ Tracking_MAX_CLASS_NUMBERS ] = {
  &pG_Tracking_Display_extent,
  &pG_Tracking_WorkoutTimer_extent,
  0,
  &pG_Tracking_HeartRateSample_extent,
  &pG_Tracking_LapMarker_extent,
  &pG_Tracking_TrackLog_extent,
  &pG_Tracking_TrackPoint_extent
};
#endif

/*
 * Array of pointers to the class event dispatcher method.
 * Index is the (model compiler enumerated) number of the state model.
 */
const EventTaker_t Tracking_EventDispatcher[ Tracking_STATE_MODELS ] = {
  Tracking_class_dispatchers
};

void Tracking_execute_initialization()
{
}
