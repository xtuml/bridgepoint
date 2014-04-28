/*----------------------------------------------------------------------------
 * File:  heart.h
 *
 * UML Component (Module) Declaration (Operations and Signals)
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef HEART_H
#define HEART_H
#ifdef	__cplusplus
extern	"C"	{
#endif

#include "avpace_sys_types.h"
void heart_out_to_body_diastolic_pace( void );
void heart_out_to_body_diastolic_pulse( void );
void heart_out_to_body_init( const i_t, const i_t );
void heart_out_to_body_systolic_pace( void );
void heart_out_to_body_systolic_pulse( void );
void heart_host_breath_taken( void );
void heart_host_current_temp( void );


#ifdef	__cplusplus
}
#endif
#endif  /* HEART_H */
