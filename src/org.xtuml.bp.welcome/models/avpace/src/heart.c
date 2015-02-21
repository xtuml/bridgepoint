/*----------------------------------------------------------------------------
 * File:  heart.c
 *
 * UML Component Port Messages
 * Component/Module Name:  heart
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "avpace_sys_types.h"
#include "heart.h"
#include "heart_LOG_bridge.h"
#include "TIM_bridge.h"
#include "pacer.h"
#include "heart_classes.h"

/*
 * Interface:  synchronization
 * Required Port:  out_to_body
 * From Provider Message:  diastolic_pace
 */
void
heart_out_to_body_diastolic_pace()
{
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( (void *) 0, &heart_HEART_CBevent1c );
    Escher_SendEvent( e );
  }

}

/*
 * Interface:  synchronization
 * Required Port:  out_to_body
 * To Provider Message:  diastolic_pulse
 */
void
heart_out_to_body_diastolic_pulse()
{
  pacer_to_heart_diastolic_pulse();
}

/*
 * Interface:  synchronization
 * Required Port:  out_to_body
 * To Provider Message:  init
 */
void
heart_out_to_body_init( const i_t p_diastolic_period, const i_t p_systolic_period )
{
  pacer_to_heart_init(  p_diastolic_period, p_systolic_period );
}

/*
 * Interface:  synchronization
 * Required Port:  out_to_body
 * From Provider Message:  systolic_pace
 */
void
heart_out_to_body_systolic_pace()
{
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( (void *) 0, &heart_HEART_CBevent2c );
    Escher_SendEvent( e );
  }

}

/*
 * Interface:  synchronization
 * Required Port:  out_to_body
 * To Provider Message:  systolic_pulse
 */
void
heart_out_to_body_systolic_pulse()
{
  pacer_to_heart_systolic_pulse();
}

/*
 * Interface:  host
 * Required Port:  host
 * To Provider Message:  breath_taken
 */
void
heart_host_breath_taken()
{
}

/*
 * Interface:  host
 * Required Port:  host
 * To Provider Message:  current_temp
 */
void
heart_host_current_temp()
{
}

/*
 * UML Domain Functions (Synchronous Services)
 */

/*
 * Domain Function:  init
 */
void
heart_init()
{
  Escher_Timer_t * t;heart_SINUS_NODE * snode;heart_HEART * heart;Escher_xtUMLEvent_t * e;
  /* CREATE OBJECT INSTANCE heart OF HEART */
  XTUML_OAL_STMT_TRACE( 1, "CREATE OBJECT INSTANCE heart OF HEART" );
  heart = (heart_HEART *) Escher_CreateInstance( heart_DOMAIN_ID, heart_HEART_CLASS_NUMBER );
  /* CREATE OBJECT INSTANCE snode OF SINUS_NODE */
  XTUML_OAL_STMT_TRACE( 1, "CREATE OBJECT INSTANCE snode OF SINUS_NODE" );
  snode = (heart_SINUS_NODE *) Escher_CreateInstance( heart_DOMAIN_ID, heart_SINUS_NODE_CLASS_NUMBER );
  /* RELATE heart TO snode ACROSS R1 */
  XTUML_OAL_STMT_TRACE( 1, "RELATE heart TO snode ACROSS R1" );
  heart_SINUS_NODE_R1_Link_is_triggered_by( heart, snode );
  /* ASSIGN snode.systolic_period = 500000 */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN snode.systolic_period = 500000" );
  snode->systolic_period = 500000;
  /* ASSIGN snode.diastolic_period = 500000 */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN snode.diastolic_period = 500000" );
  snode->diastolic_period = 500000;
  /* ASSIGN snode.decrement = 0 */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN snode.decrement = 0" );
  snode->decrement = 0;
  /* out_to_body::init(diastolic_period:snode.diastolic_period, systolic_period:snode.systolic_period) */
  XTUML_OAL_STMT_TRACE( 1, "out_to_body::init(diastolic_period:snode.diastolic_period, systolic_period:snode.systolic_period)" );
  heart_out_to_body_init( snode->diastolic_period, snode->systolic_period );
  /* CREATE EVENT INSTANCE e(  ) TO snode */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE e(  ) TO snode" );
  e = Escher_NewxtUMLEvent( (void *) snode, &heart_SINUS_NODEevent2c );
  /* ASSIGN t = TIM::timer_start(event_inst:e, microseconds:( snode.systolic_period + snode.diastolic_period )) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN t = TIM::timer_start(event_inst:e, microseconds:( snode.systolic_period + snode.diastolic_period ))" );
  t = TIM_timer_start( (Escher_xtUMLEvent_t *)e, ( snode->systolic_period + snode->diastolic_period ) );

}

/* xtUML class info (collections, sizes, etc.) */
Escher_Extent_t * const heart_class_info[ heart_MAX_CLASS_NUMBERS ] = {
  &pG_heart_HEART_extent,
  &pG_heart_SINUS_NODE_extent,
  0
};

/*
 * Array of pointers to the class event dispatcher method.
 * Index is the (model compiler enumerated) number of the state model.
 */
const EventTaker_t heart_EventDispatcher[ heart_STATE_MODELS ] = {
  heart_class_dispatchers
};

void heart_execute_initialization()
{
  /*
   * Initialization Function:  init
   * Component:  heart
   */
  heart_init();

}
