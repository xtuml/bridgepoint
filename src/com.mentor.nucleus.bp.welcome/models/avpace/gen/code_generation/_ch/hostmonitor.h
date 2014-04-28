/*----------------------------------------------------------------------------
 * File:  hostmonitor.h
 *
 * UML Component (Module) Declaration (Operations and Signals)
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef HOSTMONITOR_H
#define HOSTMONITOR_H
#ifdef	__cplusplus
extern	"C"	{
#endif

#include "avpace_sys_types.h"
void hostmonitor_monitor_increased_activty( const i_t, const i_t );
void hostmonitor_host_breath_taken( void );
void hostmonitor_host_current_temp( void );


#ifdef	__cplusplus
}
#endif
#endif  /* HOSTMONITOR_H */
