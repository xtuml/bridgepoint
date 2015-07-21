/*----------------------------------------------------------------------------
 * File:  Location.h
 *
 * UML Component (Module) Declaration (Operations and Signals)
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef LOCATION_H
#define LOCATION_H
#ifdef	__cplusplus
extern	"C"	{
#endif
/*
Simulates a the GPS hardware/firmware. This component is only behavioral and included for testing purposes.
*/

#include "GPSWatch_sys_types.h"
void Location_LOC_locationUpdate( GPSWatch_sdt_Location );
void Location_LOC_registerListener( void );
void Location_LOC_unregisterListener( void );
r_t Location_UTIL_getDistance( GPSWatch_sdt_Location, GPSWatch_sdt_Location );


#ifdef	__cplusplus
}
#endif
#endif  /* LOCATION_H */
