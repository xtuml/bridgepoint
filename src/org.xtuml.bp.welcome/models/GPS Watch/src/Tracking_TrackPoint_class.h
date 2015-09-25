/*----------------------------------------------------------------------------
 * File:  Tracking_TrackPoint_class.h
 *
 * Class:       TrackPoint  (TrackPoint)
 * Component:   Tracking
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef TRACKING_TRACKPOINT_CLASS_H
#define TRACKING_TRACKPOINT_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   TrackPoint  (TrackPoint)
 */
struct Tracking_TrackPoint {

  /* application analysis class attributes */
  i_t time;  /* - time */
  r_t longitude;  /* - longitude */
  r_t latitude;  /* - latitude */
  r_t speed;  /* - speed */

  /* relationship storage */
  /* Note:  No storage needed for TrackPoint->TrackLog[R1] */
  Tracking_TrackPoint * TrackPoint_R2_preceeds;
  Tracking_TrackPoint * TrackPoint_R2_follows;
  /* Note:  No storage needed for TrackPoint->TrackLog[R3] */
};

void Tracking_TrackPoint_R1_Link_has_first( Tracking_TrackLog *, Tracking_TrackPoint * );
void Tracking_TrackPoint_R1_Unlink_has_first( Tracking_TrackLog *, Tracking_TrackPoint * );
      
/*
 * R2 is Simple Reflexive:  0..1:0..1
 *  Formalizing TrackPoint preceeds participant
 *  Participant TrackPoint follows formalizer
 */
/* Navigation phrase:  R2.'preceeds' */
void Tracking_TrackPoint_R2_Link_preceeds( Tracking_TrackPoint *, Tracking_TrackPoint * );
void Tracking_TrackPoint_R2_Unlink_preceeds( Tracking_TrackPoint *, Tracking_TrackPoint * );
/* Navigation phrase:  R2.'follows' */
void Tracking_TrackPoint_R2_Link_follows( Tracking_TrackPoint *, Tracking_TrackPoint * );
void Tracking_TrackPoint_R2_Unlink_follows( Tracking_TrackPoint *, Tracking_TrackPoint * );


#define Tracking_TrackPoint_MAX_EXTENT_SIZE 200
extern Escher_Extent_t pG_Tracking_TrackPoint_extent;

#ifdef	__cplusplus
}
#endif

#endif  /* TRACKING_TRACKPOINT_CLASS_H */


