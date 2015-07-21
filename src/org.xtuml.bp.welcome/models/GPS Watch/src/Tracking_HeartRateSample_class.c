/*----------------------------------------------------------------------------
 * File:  Tracking_HeartRateSample_class.c
 *
 * Class:       HeartRateSample  (HeartRateSample)
 * Component:   Tracking
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "GPSWatch_sys_types.h"
#include "LOG_bridge.h"
#include "TIM_bridge.h"
#include "Tracking_classes.h"


/*
 * RELATE TrackLog TO HeartRateSample ACROSS R6
 */
void
Tracking_HeartRateSample_R6_Link( Tracking_TrackLog * part, Tracking_HeartRateSample * form )
{
  if ( (part == 0) || (form == 0) ) {
    XTUML_EMPTY_HANDLE_TRACE( "HeartRateSample", "Tracking_HeartRateSample_R6_Link" );
    return;
  }
  /* Note:  HeartRateSample->TrackLog[R6] not navigated */
  Escher_SetInsertElement( &part->HeartRateSample_R6, (Escher_ObjectSet_s *) form );
}

/*
 * UNRELATE TrackLog FROM HeartRateSample ACROSS R6
 */
void
Tracking_HeartRateSample_R6_Unlink( Tracking_TrackLog * part, Tracking_HeartRateSample * form )
{
  if ( (part == 0) || (form == 0) ) {
    XTUML_EMPTY_HANDLE_TRACE( "HeartRateSample", "Tracking_HeartRateSample_R6_Unlink" );
    return;
  }
  /* Note:  HeartRateSample->TrackLog[R6] not navigated */
  Escher_SetRemoveElement( &part->HeartRateSample_R6, (Escher_ObjectSet_s *) form );
}

/*
 * Statically allocate space for the instance population for this class.
 * Allocate space for the class instance and its attribute values.
 * Depending upon the collection scheme, allocate containoids (collection
 * nodes) for gathering instances into free and active extents.
 */
static Escher_SetElement_s Tracking_HeartRateSample_container[ Tracking_HeartRateSample_MAX_EXTENT_SIZE ];
static Tracking_HeartRateSample Tracking_HeartRateSample_instances[ Tracking_HeartRateSample_MAX_EXTENT_SIZE ];
Escher_Extent_t pG_Tracking_HeartRateSample_extent = {
  {0}, {0}, &Tracking_HeartRateSample_container[ 0 ],
  (Escher_iHandle_t) &Tracking_HeartRateSample_instances,
  sizeof( Tracking_HeartRateSample ), 0, Tracking_HeartRateSample_MAX_EXTENT_SIZE
  };


