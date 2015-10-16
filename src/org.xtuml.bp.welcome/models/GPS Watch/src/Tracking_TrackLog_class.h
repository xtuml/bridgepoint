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
  Tracking_TrackPoint * TrackPoint_R1_has_first;
  Tracking_TrackPoint * TrackPoint_R3_has_last;
  Tracking_WorkoutTimer * WorkoutTimer_R4_is_being_timed_by;
  Escher_ObjectSet_s LapMarker_R5_has_laps_defined_by;
  Escher_ObjectSet_s HeartRateSample_R6_logs_heart_rate_changes_as;
};
void Tracking_TrackLog_op_addTrackPoint( Tracking_TrackLog *, GPSWatch_sdt_Location );
void Tracking_TrackLog_op_clearTrackPoints( Tracking_TrackLog * );
void Tracking_TrackLog_op_addLapMarker( Tracking_TrackLog * );
void Tracking_TrackLog_op_clearLapMarkers( Tracking_TrackLog * );
void Tracking_TrackLog_op_addHeartRateSample( Tracking_TrackLog *, const r_t );
void Tracking_TrackLog_op_clearHeartRateSamples( Tracking_TrackLog * );
void Tracking_TrackLog_op_init( Tracking_TrackLog * );

void Tracking_TrackLog_R3_Link_is_last_for( Tracking_TrackPoint *, Tracking_TrackLog * );
void Tracking_TrackLog_R3_Unlink_is_last_for( Tracking_TrackPoint *, Tracking_TrackLog * );
void Tracking_TrackLog_R4_Link_provides_high_resolution_data_for( Tracking_WorkoutTimer *, Tracking_TrackLog * );
void Tracking_TrackLog_R4_Unlink_provides_high_resolution_data_for( Tracking_WorkoutTimer *, Tracking_TrackLog * );
#define Tracking_LapMarker_R5_From_TrackLog_has_laps_defined_by( TrackLog ) ( &((TrackLog)->LapMarker_R5_has_laps_defined_by) )
#define Tracking_HeartRateSample_R6_From_TrackLog_logs_heart_rate_changes_as( TrackLog ) ( &((TrackLog)->HeartRateSample_R6_logs_heart_rate_changes_as) )


#define Tracking_TrackLog_MAX_EXTENT_SIZE 200
extern Escher_Extent_t pG_Tracking_TrackLog_extent;
r_t Tracking_TrackLog_MDA_currentPace( Tracking_TrackLog * );

#ifdef	__cplusplus
}
#endif

#endif  /* TRACKING_TRACKLOG_CLASS_H */


