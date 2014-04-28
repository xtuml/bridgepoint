/*----------------------------------------------------------------------------
 * File:  hostmonitor_TM_class.c
 *
 * Class:       Temperature Monitor  (TM)
 * Component:   hostmonitor
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "avpace_sys_types.h"
#include "hostmonitor_classes.h"

/*
 * instance operation:  getCurrentTemp
 */
i_t
hostmonitor_TM_op_getCurrentTemp( hostmonitor_TM * self)
{
  /* RETURN 0 */
  XTUML_OAL_STMT_TRACE( 1, "RETURN 0" );
  return 0;
}



/*----------------------------------------------------------------------------
 * Operation action methods implementation for the following class:
 *
 * Class:      Temperature Monitor  (TM)
 * Component:  hostmonitor
 *--------------------------------------------------------------------------*/
/*
 * Statically allocate space for the instance population for this class.
 * Allocate space for the class instance and its attribute values.
 * Depending upon the collection scheme, allocate containoids (collection
 * nodes) for gathering instances into free and active extents.
 */
static Escher_SetElement_s hostmonitor_TM_container[ hostmonitor_TM_MAX_EXTENT_SIZE ];
static hostmonitor_TM hostmonitor_TM_instances[ hostmonitor_TM_MAX_EXTENT_SIZE ];
Escher_Extent_t pG_hostmonitor_TM_extent = {
  {0}, {0}, &hostmonitor_TM_container[ 0 ],
  (Escher_iHandle_t) &hostmonitor_TM_instances,
  sizeof( hostmonitor_TM ), 0, hostmonitor_TM_MAX_EXTENT_SIZE
  };


