/*----------------------------------------------------------------------------
 * File:  Tracking_LapMarker_class.c
 *
 * Class:       LapMarker  (LapMarker)
 * Component:   Tracking
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "GPSWatch_sys_types.h"
#include "LOG_bridge.h"
#include "TIM_bridge.h"
#include "Tracking_classes.h"


/*
 * RELATE TrackLog TO LapMarker ACROSS R5
 */
void
Tracking_LapMarker_R5_Link( Tracking_TrackLog * part, Tracking_LapMarker * form )
{
  if ( (part == 0) || (form == 0) ) {
    XTUML_EMPTY_HANDLE_TRACE( "LapMarker", "Tracking_LapMarker_R5_Link" );
    return;
  }
  /* Note:  LapMarker->TrackLog[R5] not navigated */
  Escher_SetInsertElement( &part->LapMarker_R5, (Escher_ObjectSet_s *) form );
}

/*
 * UNRELATE TrackLog FROM LapMarker ACROSS R5
 */
void
Tracking_LapMarker_R5_Unlink( Tracking_TrackLog * part, Tracking_LapMarker * form )
{
  if ( (part == 0) || (form == 0) ) {
    XTUML_EMPTY_HANDLE_TRACE( "LapMarker", "Tracking_LapMarker_R5_Unlink" );
    return;
  }
  /* Note:  LapMarker->TrackLog[R5] not navigated */
  Escher_SetRemoveElement( &part->LapMarker_R5, (Escher_ObjectSet_s *) form );
}

/*
 * Statically allocate space for the instance population for this class.
 * Allocate space for the class instance and its attribute values.
 * Depending upon the collection scheme, allocate containoids (collection
 * nodes) for gathering instances into free and active extents.
 */
static Escher_SetElement_s Tracking_LapMarker_container[ Tracking_LapMarker_MAX_EXTENT_SIZE ];
static Tracking_LapMarker Tracking_LapMarker_instances[ Tracking_LapMarker_MAX_EXTENT_SIZE ];
Escher_Extent_t pG_Tracking_LapMarker_extent = {
  {0}, {0}, &Tracking_LapMarker_container[ 0 ],
  (Escher_iHandle_t) &Tracking_LapMarker_instances,
  sizeof( Tracking_LapMarker ), 0, Tracking_LapMarker_MAX_EXTENT_SIZE
  };


