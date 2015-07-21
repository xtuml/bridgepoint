/*----------------------------------------------------------------------------
 * File:  HeartRateMonitor.c
 *
 * UML Component Port Messages
 * Component/Module Name:  HeartRateMonitor
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/
/*
 Simulates a pulse monitor hardware/firmware. This component is only behavioral and included for testing purposes.
 */

#include "GPSWatch_sys_types.h"
#include "HeartRateMonitor.h"
#include "LOG_bridge.h"
#include "TIM_bridge.h"
#include "HeartRateMonitor_classes.h"

/*
 * Interface:  HeartRateProvider
 * Provided Port:  HR
 * From Provider Message:  heartRateChanged
 */
void
HeartRateMonitor_HR_heartRateChanged( const r_t p_heartRate)
{
  Tracking_HR_heartRateChanged(  p_heartRate );
}

/*
 * Interface:  HeartRateProvider
 * Provided Port:  HR
 * To Provider Message:  registerListener
 */
void
HeartRateMonitor_HR_registerListener()
{
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( (void *) 0, &HeartRateMonitor_HeartRateMonitor_CBevent1c );
    Escher_SendEvent( e );
  }

}

/*
 * Interface:  HeartRateProvider
 * Provided Port:  HR
 * To Provider Message:  unregisterListener
 */
void
HeartRateMonitor_HR_unregisterListener()
{
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( (void *) 0, &HeartRateMonitor_HeartRateMonitor_CBevent2c );
    Escher_SendEvent( e );
  }

}

/*
 * UML Domain Functions (Synchronous Services)
 */

#if HeartRateMonitor_MAX_CLASS_NUMBERS > 0
/* xtUML class info (collections, sizes, etc.) */
Escher_Extent_t * const HeartRateMonitor_class_info[ HeartRateMonitor_MAX_CLASS_NUMBERS ] = {
  &pG_HeartRateMonitor_HeartRateMonitor_extent,
  0
};
#endif

/*
 * Array of pointers to the class event dispatcher method.
 * Index is the (model compiler enumerated) number of the state model.
 */
const EventTaker_t HeartRateMonitor_EventDispatcher[ HeartRateMonitor_STATE_MODELS ] = {
  HeartRateMonitor_class_dispatchers
};

void HeartRateMonitor_execute_initialization()
{
}
