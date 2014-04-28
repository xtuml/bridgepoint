/*----------------------------------------------------------------------------
 * File:  pacer.h
 *
 * UML Component (Module) Declaration (Operations and Signals)
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef PACER_H
#define PACER_H
#ifdef	__cplusplus
extern	"C"	{
#endif
/*
The pacer component listens to the host's heartbeat and sends signals to
increase the rate depending on the host's current rate.  It consists of two
internal components, one that handles generating a beat and the other that
listens for increased activity.
*/

#include "avpace_sys_types.h"
void pacer_to_heart_diastolic_pace( void );
void pacer_to_heart_diastolic_pulse( void );
void pacer_to_heart_init( const i_t, const i_t );
void pacer_to_heart_systolic_pace( void );
void pacer_to_heart_systolic_pulse( void );
void pacer_host_breath_taken( void );
void pacer_host_current_temp( void );


#ifdef	__cplusplus
}
#endif
#endif  /* PACER_H */
