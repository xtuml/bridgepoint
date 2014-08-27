/*----------------------------------------------------------------------------
 * File:  pacemaker.c
 *
 * UML Component Port Messages
 * Component/Module Name:  pacemaker
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "avpace_sys_types.h"
#include "pacemaker.h"
#include "TIM_bridge.h"
#include "pacemaker_LOG_bridge.h"
#include "hostmonitor.h"
#include "pacemaker_classes.h"

/*
 * Interface:  synchronization
 * Provided Port:  to_heart
 * From Provider Message:  diastolic_pace
 */
void
pacemaker_to_heart_diastolic_pace()
{
}

/*
 * Interface:  synchronization
 * Provided Port:  to_heart
 * To Provider Message:  diastolic_pulse
 */
void
pacemaker_to_heart_diastolic_pulse()
{
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( (void *) 0, &pacemaker_PACER_CBevent3c );
    Escher_SendEvent( e );
  }

}

/*
 * Interface:  synchronization
 * Provided Port:  to_heart
 * To Provider Message:  init
 */
void
pacemaker_to_heart_init( const i_t p_diastolic_period, const i_t p_systolic_period )
{
  pacemaker_PACER * pacer;
  /* CREATE OBJECT INSTANCE pacer OF PACER */
  XTUML_OAL_STMT_TRACE( 1, "CREATE OBJECT INSTANCE pacer OF PACER" );
  pacer = (pacemaker_PACER *) Escher_CreateInstance( pacemaker_DOMAIN_ID, pacemaker_PACER_CLASS_NUMBER );
  /* ASSIGN pacer.systolic_tolerance = 25 */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN pacer.systolic_tolerance = 25" );
  pacer->systolic_tolerance = 25;
  /* ASSIGN pacer.diastolic_tolerance = 25 */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN pacer.diastolic_tolerance = 25" );
  pacer->diastolic_tolerance = 25;
  /* ASSIGN pacer.cycle_count = 0 */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN pacer.cycle_count = 0" );
  pacer->cycle_count = 0;
  /* ASSIGN pacer.systolic_timeout = ( PARAM.systolic_period + ( ( PARAM.systolic_period * pacer.systolic_tolerance ) / 100 ) ) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN pacer.systolic_timeout = ( PARAM.systolic_period + ( ( PARAM.systolic_period * pacer.systolic_tolerance ) / 100 ) )" );
  pacer->systolic_timeout = ( p_systolic_period + ( ( p_systolic_period * pacer->systolic_tolerance ) / 100 ) );
  /* ASSIGN pacer.diastolic_timeout = ( PARAM.diastolic_period + ( ( PARAM.diastolic_period * pacer.diastolic_tolerance ) / 100 ) ) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN pacer.diastolic_timeout = ( PARAM.diastolic_period + ( ( PARAM.diastolic_period * pacer.diastolic_tolerance ) / 100 ) )" );
  pacer->diastolic_timeout = ( p_diastolic_period + ( ( p_diastolic_period * pacer->diastolic_tolerance ) / 100 ) );
}

/*
 * Interface:  synchronization
 * Provided Port:  to_heart
 * From Provider Message:  systolic_pace
 */
void
pacemaker_to_heart_systolic_pace()
{
}

/*
 * Interface:  synchronization
 * Provided Port:  to_heart
 * To Provider Message:  systolic_pulse
 */
void
pacemaker_to_heart_systolic_pulse()
{
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( (void *) 0, &pacemaker_PACER_CBevent4c );
    Escher_SendEvent( e );
  }

}

/*
 * Interface:  monitor
 * Provided Port:  monitor
 * To Provider Message:  increased_activty
 */
void
pacemaker_monitor_increased_activty( const i_t p_current_rate, const i_t p_current_temp )
{
  { pacemaker_PACER_CBevent5 * e = (pacemaker_PACER_CBevent5 *) Escher_NewxtUMLEvent( (void *) 0, &pacemaker_PACER_CBevent5c );
    e->p_current_rate = p_current_rate;    e->p_current_temp = p_current_temp;
    Escher_SendEvent( (Escher_xtUMLEvent_t *) e );
  }

}

/*
 * UML Domain Functions (Synchronous Services)
 */

/* xtUML class info (collections, sizes, etc.) */
Escher_Extent_t * const pacemaker_class_info[ pacemaker_MAX_CLASS_NUMBERS ] = {
  &pG_pacemaker_PACER_extent,
  0
};

/*
 * Array of pointers to the class event dispatcher method.
 * Index is the (model compiler enumerated) number of the state model.
 */
const EventTaker_t pacemaker_EventDispatcher[ pacemaker_STATE_MODELS ] = {
  pacemaker_class_dispatchers
};

void pacemaker_execute_initialization()
{
}
