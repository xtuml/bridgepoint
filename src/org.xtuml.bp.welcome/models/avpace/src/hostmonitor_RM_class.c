/*----------------------------------------------------------------------------
 * File:  hostmonitor_RM_class.c
 *
 * Class:       Respiratory Monitor  (RM)
 * Component:   hostmonitor
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "avpace_sys_types.h"
#include "hostmonitor_classes.h"

/*
 * instance operation:  getCurrentRate
 */
i_t
hostmonitor_RM_op_getCurrentRate( hostmonitor_RM * self)
{
  /* RETURN 0 */
  XTUML_OAL_STMT_TRACE( 1, "RETURN 0" );
  return 0;
}



/*----------------------------------------------------------------------------
 * Operation action methods implementation for the following class:
 *
 * Class:      Respiratory Monitor  (RM)
 * Component:  hostmonitor
 *--------------------------------------------------------------------------*/
/*
 * Statically allocate space for the instance population for this class.
 * Allocate space for the class instance and its attribute values.
 * Depending upon the collection scheme, allocate containoids (collection
 * nodes) for gathering instances into free and active extents.
 */
static Escher_SetElement_s hostmonitor_RM_container[ hostmonitor_RM_MAX_EXTENT_SIZE ];
static hostmonitor_RM hostmonitor_RM_instances[ hostmonitor_RM_MAX_EXTENT_SIZE ];
Escher_Extent_t pG_hostmonitor_RM_extent = {
  {0}, {0}, &hostmonitor_RM_container[ 0 ],
  (Escher_iHandle_t) &hostmonitor_RM_instances,
  sizeof( hostmonitor_RM ), 0, hostmonitor_RM_MAX_EXTENT_SIZE
  };


