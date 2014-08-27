/*----------------------------------------------------------------------------
 * File:  hostmonitor.c
 *
 * UML Component Port Messages
 * Component/Module Name:  hostmonitor
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "avpace_sys_types.h"
#include "hostmonitor.h"
#include "pacemaker.h"
#include "hostmonitor_classes.h"

/*
 * Interface:  monitor
 * Required Port:  monitor
 * To Provider Message:  increased_activty
 */
void
hostmonitor_monitor_increased_activty( const i_t p_current_rate, const i_t p_current_temp )
{
  pacemaker_monitor_increased_activty(  p_current_rate, p_current_temp );
}

/*
 * Interface:  host
 * Provided Port:  host
 * To Provider Message:  breath_taken
 */
void
hostmonitor_host_breath_taken()
{
}

/*
 * Interface:  host
 * Provided Port:  host
 * To Provider Message:  current_temp
 */
void
hostmonitor_host_current_temp()
{
}

/*
 * UML Domain Functions (Synchronous Services)
 */

/* xtUML class info (collections, sizes, etc.) */
Escher_Extent_t * const hostmonitor_class_info[ hostmonitor_MAX_CLASS_NUMBERS ] = {
  &pG_hostmonitor_HM_extent,
  0,
  &pG_hostmonitor_TM_extent,
  &pG_hostmonitor_RM_extent
};

/*
 * Array of pointers to the class event dispatcher method.
 * Index is the (model compiler enumerated) number of the state model.
 */
const EventTaker_t hostmonitor_EventDispatcher[ hostmonitor_STATE_MODELS ] = {
  hostmonitor_class_dispatchers
};

void hostmonitor_execute_initialization()
{
}
