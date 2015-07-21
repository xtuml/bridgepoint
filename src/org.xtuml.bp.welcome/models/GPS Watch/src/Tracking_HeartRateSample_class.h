/*----------------------------------------------------------------------------
 * File:  Tracking_HeartRateSample_class.h
 *
 * Class:       HeartRateSample  (HeartRateSample)
 * Component:   Tracking
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef TRACKING_HEARTRATESAMPLE_CLASS_H
#define TRACKING_HEARTRATESAMPLE_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   HeartRateSample  (HeartRateSample)
 */
struct Tracking_HeartRateSample {

  /* application analysis class attributes */
  r_t heartRate;  /* - heartRate */

  /* relationship storage */
  /* Note:  No storage needed for HeartRateSample->TrackLog[R6] */
};

void Tracking_HeartRateSample_R6_Link( Tracking_TrackLog *, Tracking_HeartRateSample * );
void Tracking_HeartRateSample_R6_Unlink( Tracking_TrackLog *, Tracking_HeartRateSample * );


#define Tracking_HeartRateSample_MAX_EXTENT_SIZE 200
extern Escher_Extent_t pG_Tracking_HeartRateSample_extent;

#ifdef	__cplusplus
}
#endif

#endif  /* TRACKING_HEARTRATESAMPLE_CLASS_H */


