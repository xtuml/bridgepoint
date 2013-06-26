/*----------------------------------------------------------------------------
 * File:  sys_user_co.c
 *
 * Description:
 * Function definitions for user supplied (non-translated) call out entry
 * points in the generated application.
 *
 *
 * Notice:
 *   (C) Copyright 1998-2008 Mentor Graphics Corporation
 *   All rights reserved.
 *
 *--------------------------------------------------------------------------*/

#include "sys_user_co.h"
#include "TIM_bridge.h"

#ifdef SYS_USER_CO_PRINTF_ON
#include <stdio.h>
#define SYS_USER_CO_PRINTF( s ) printf( s );
#else
#define SYS_USER_CO_PRINTF( s )
#endif

/*
 * UserInitializationCallout
 *
 * This function is invoked at the immediate beginning of application
 * initialization. It is the very first function to be executed at system
 * startup.
 * User supplied implementation of this function should be restricted to
 * things like memory initialization, early hardware duties, etc.
 */
void
UserInitializationCalloutf( void )
{
/* Activate this invocation to initialize the example simple TIM.  */
  #if SYS_MAX_OOA_TIMERS > 0
  TIM_init();
  #endif
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserInitializationCallout\n" )
}

/*
 * UserPreOoaInitializationCallout
 *
 * This function is invoked immediately prior to executing any xtUML
 * initialization functions.
 */
void
UserPreOoaInitializationCalloutf( void )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserPreOoaInitializationCalloutf\n" )
}

/*
 * UserPostOoaInitializationCallout
 *
 * This function is invoked immediately after executing any xtUML
 * initialization functions.
 * When this callout function returns, the system dispatcher will allow the
 * xtUML application analysis state models to start consuming events.
 */
void
UserPostOoaInitializationCalloutf( void )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserPostOoaInitializationCalloutf\n" )
}

/*
 * UserBackgroundProcessingCallout
 *
 * This function is invoked once during each loop execution of the system
 * dispather.
 * It is invoked at the 'top' of the system dispatcher loop, immediately
 * prior to dispatching any xtUML application analysis events.
 */
void
UserBackgroundProcessingCalloutf( void )
{
/* Activate this invocation to periodically tick the example simple TIM.  */
  #if SYS_MAX_OOA_TIMERS > 0
  TIM_tick();
  #endif
  /* Insert implementation specific code here.  */
}

/*
 * UserPreShutdownCallout
 *
 * This function is invoked at termination of the system dispatcher, but
 * prior to performing any xtUML application analysis shutdown sequencing.
 */
void
UserPreShutdownCalloutf( void )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserPreShutdownCalloutf\n" )
}

/*
 * UserPostShutdownCallout
 *
 * This function is invoked immediately before application exit.
 */
void
UserPostShutdownCalloutf( void )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserPostShutdownCalloutf\n" )
}

/*
 * UserEventCantHappenCallout
 *
 * This function is invoked any time that an event is received that
 * results in a "cant happen" transition.
 */
void
UserEventCantHappenCalloutf(
  const Escher_StateNumber_t current_state,
  const Escher_StateNumber_t next_state,
  const Escher_EventNumber_t event_number )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserEventCantHappenCalloutf\n" )
}

/*
 * UserEventNoInstanceCallout
 *
 * This function is invoked when we failed to validate the instance
 * to which an event was directed.  This can happen in normal operation
 * when the instance was deleted while the event was in flight (analysis
 * error).
 */
void
UserEventNoInstanceCalloutf(
  const Escher_EventNumber_t event_number )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserEventNoInstanceCalloutf\n" )
}

/*
 * UserEventFreeListEmptyCallout
 *
 * This function is invoked when an attempt is made to allocate an
 * event, but there are no more left.
 */
void
UserEventFreeListEmptyCalloutf( void )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserEventFreeListEmptyCalloutf\n" )
}

/*
 * UserEmptyHandleDetectedCallout
 *
 * This function is invoked when an attempt is made to use an instance
 * reference variable (handle) that is null (empty).
 */
void
UserEmptyHandleDetectedCalloutf( c_t * object_keyletters, c_t * s )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserEmptyHandleDetectedCalloutf\n" )
}

/*
 * UserObjectPoolEmptyCallout
 *
 * This function is invoked when an attempt is made to create an
 * instance of an object, but there are no instances available.
 */
void
UserObjectPoolEmptyCalloutf( c_t * domain, c_t * object_name )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserObjectPoolEmptyCalloutf\n" )
}

/*
 * UserNodeListEmptyCallout
 *
 * This function is invoked when an attempt is made to allocate a
 * node, but there are no more left.
 */
void
UserNodeListEmptyCalloutf( void )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserNodeListEmptyCalloutf\n" )
}

/*
 * UserInterleavedBridgeOverflowCallout
 *
 * This function is invoked when an attempt is made to post too many
 * interleaved bridges.  The depth of this list is defined by
 * SYS_MAX_INTERLEAVED_BRIDGES (unless changed in the archetype).
 */
void
UserInterleavedBridgeOverflowCalloutf( void )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserInterleavedBridgeOverflowCalloutf\n" )
}

/*
 * UserSelfEventQueueEmptyCallout
 *
 * This function is invoked when the events to self queue is
 * interrogated and found to be empty.
 */
void
UserSelfEventQueueEmptyCalloutf( void )
{
  /* Insert implementation specific code here.  */
}

/*
 * UserNonSelfEventQueueEmptyCallout
 *
 * This function is invoked when the events to instance queue is
 * interrogated and found to be empty.
 */
void
UserNonSelfEventQueueEmptyCalloutf( void )
{
  /* Insert implementation specific code here.  */
}

/*
 * UserPersistenceErrorCallout
 *
 * This function is invoked when the events to instance queue is
 * interrogated and found to be empty.
 */
void
UserPersistenceErrorCalloutf( i_t error_code )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserPersistenceErrorCalloutf\n" )
}
