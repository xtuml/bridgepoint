/*----------------------------------------------------------------------------
 * File:  HeartRateMonitor.h
 *
 * UML Component (Module) Declaration (Operations and Signals)
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef HEARTRATEMONITOR_H
#define HEARTRATEMONITOR_H
#ifdef	__cplusplus
extern	"C"	{
#endif
/*
Simulates a pulse monitor hardware/firmware. This component is only behavioral and included for testing purposes.
*/

#include "GPSWatch_sys_types.h"
void HeartRateMonitor_HR_heartRateChanged( const r_t );
void HeartRateMonitor_HR_registerListener( void );
void HeartRateMonitor_HR_unregisterListener( void );


#ifdef	__cplusplus
}
#endif
#endif  /* HEARTRATEMONITOR_H */
