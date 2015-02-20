/*--------------------------------------------------------------------------
 * File:  GPSWatch_sys_main.c
 *
 * Description:  main, system initialization (and idle loop)
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "GPSWatch_sys_types.h"
#include "HeartRateMonitor_classes.h"
#include "Location_classes.h"
#include "Tracking_classes.h"
#include "UI_classes.h"

/*
 * Run application level initialization by initializing the
 * collection of instances for each class and calling domain
 * specific initialization.
 */
static void ApplicationLevelInitialization( void );
static void ApplicationLevelInitialization( void )
{
  Escher_DomainNumber_t d;
  Escher_ClassNumber_t c;

  static const Escher_ClassNumber_t domain_class_count[ SYSTEM_DOMAIN_COUNT ] = {
    HeartRateMonitor_MAX_CLASS_NUMBERS,
    Location_MAX_CLASS_NUMBERS,
    Tracking_MAX_CLASS_NUMBERS,
    UI_MAX_CLASS_NUMBERS
  };
  for ( d = 0; d < SYSTEM_DOMAIN_COUNT; d++ ) {
    for ( c = 0; c < domain_class_count[ d ]; c++ ) {
      Escher_ClassFactoryInit( d, c );
    }
  }
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
main( int argc, char ** argv )
{
  UserInitializationCallout();
  Escher_SetFactoryInit( SYS_MAX_CONTAINERS );
  InitializeOoaEventPool();
  ApplicationLevelInitialization();
  UserPreOoaInitializationCallout();
  HeartRateMonitor_execute_initialization();
  Location_execute_initialization();
  Tracking_execute_initialization();
  UI_execute_initialization();
  UserPostOoaInitializationCallout();
  Escher_xtUML_run(); /* This is the primary event dispatch loop.  */
  UserPreShutdownCallout();
  UserPostShutdownCallout();
  return 0;
}
