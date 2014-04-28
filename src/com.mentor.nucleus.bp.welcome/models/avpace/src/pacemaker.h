/*----------------------------------------------------------------------------
 * File:  pacemaker.h
 *
 * UML Component (Module) Declaration (Operations and Signals)
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef PACEMAKER_H
#define PACEMAKER_H
#ifdef	__cplusplus
extern	"C"	{
#endif

#include "avpace_sys_types.h"
void pacemaker_to_heart_diastolic_pace( void );
void pacemaker_to_heart_diastolic_pulse( void );
void pacemaker_to_heart_init( const i_t, const i_t );
void pacemaker_to_heart_systolic_pace( void );
void pacemaker_to_heart_systolic_pulse( void );
void pacemaker_monitor_increased_activty( const i_t, const i_t );


#ifdef	__cplusplus
}
#endif
#endif  /* PACEMAKER_H */
