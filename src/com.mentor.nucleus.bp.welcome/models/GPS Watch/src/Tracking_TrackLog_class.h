/*----------------------------------------------------------------------------
 * File:  Tracking_TrackLog_class.h
 *
 * Class:       TrackLog  (TrackLog)
 * Component:   Tracking
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef TRACKING_TRACKLOG_CLASS_H
#define TRACKING_TRACKLOG_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   TrackLog  (TrackLog)
 */
struct Tracking_TrackLog {

  /* application analysis class attributes */
  r_t distance;  /* - distance */
  r_t currentSpeed;  /* - currentSpeed */
  r_t currentPace;  /* - currentPace (M) */
  r_t currentHeartRate;  /* - currentHeartRate */
  Escher_TimeStamp_t startTime;  /* - startTime */
  GPSWatch_sdt_Location lastKnownLocation;  /* - lastKnownLocation */
  bool hasLocation;  /* - hasLocation */

  /* relationship storage */
  Tracking_TrackPoint * TrackPoint_R1;
  Tracking_TrackPoint * TrackPoint_R3;
  Tracking_WorkoutTimer * WorkoutTimer_R4;
  Escher_ObjectSet_s LapMarker_R5;
  Escher_ObjectSet_s HeartRateSample_R6;
};
void Tracking_TrackLog_op_addTrackPoint( Tracking_TrackLog *, GPSWatch_sdt_Location );
void Tracking_TrackLog_op_clearTrackPoints( Tracking_TrackLog * );
void Tracking_TrackLog_op_addLapMarker( Tracking_TrackLog * );
void Tracking_TrackLog_op_clearLapMarkers( Tracking_TrackLog * );
void Tracking_TrackLog_op_addHeartRateSample( Tracking_TrackLog *, const r_t );
void Tracking_TrackLog_op_clearHeartRateSamples( Tracking_TrackLog * );
void Tracking_TrackLog_op_init( Tracking_TrackLog * );

void Tracking_TrackLog_R1_Link( Tracking_TrackPoint *, Tracking_TrackLog * );
void Tracking_TrackLog_R1_Unlink( Tracking_TrackPoint *, Tracking_TrackLog * );
#define Tracking_LapMarker_R5_From_TrackLog( TrackLog ) ( &((TrackLog)->LapMarker_R5) )
#define Tracking_HeartRateSample_R6_From_TrackLog( TrackLog ) ( &((TrackLog)->HeartRateSample_R6) )


#define Tracking_TrackLog_MAX_EXTENT_SIZE 200
extern Escher_Extent_t pG_Tracking_TrackLog_extent;
r_t Tracking_TrackLog_MDA_currentPace( Tracking_TrackLog * );

#ifdef	__cplusplus
}
#endif

#endif  /* TRACKING_TRACKLOG_CLASS_H */


