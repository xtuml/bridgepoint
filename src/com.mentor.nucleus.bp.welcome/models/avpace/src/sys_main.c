//============================================================================
/*--------------------------------------------------------------------------
 * File:  sys_main.c
 *
 * Description:  main and system inititialization
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/

#include "sys_types.h"
#include "sys_thread.h"

/*
 * Run application level initialization by initializing the
 * collection of instances for each class and calling domain
 * specific initialization.
 */
static void ApplicationLevelInitialization( void );
static void ApplicationLevelInitialization( void )
{
}

/*
 *
 * main (although perhaps under a different name)
 *
 * Bring the system up and start the event dispatch loops.
 * Make invocations to the user hooks during the different phases
 * of bringup, run and shutdown.
 *
 */
int
main ( int argc, char ** argv )
{
  UserInitializationCallout()
  Escher_SetFactoryInit();
  Escher_InitializeThreading();
  ApplicationLevelInitialization();
  UserPreOoaInitializationCallout()
  UserPostOoaInitializationCallout()
  UserPreShutdownCallout()
  UserPostShutdownCallout()
  Escher_thread_shutdown();
  return 0;
}
