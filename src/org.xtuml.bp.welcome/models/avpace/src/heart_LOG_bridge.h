/*----------------------------------------------------------------------------
 * File:  heart_LOG_bridge.h
 *
 * Description:
 * Methods for bridging to an external entity.
 *
 * External Entity:  Logging (LOG)
 * 
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef HEART_LOG_BRIDGE_H
#define HEART_LOG_BRIDGE_H
#ifdef	__cplusplus
extern	"C"	{
#endif

#include "avpace_sys_types.h"

void heart_LOG_LogSuccess( c_t[ESCHER_SYS_MAX_STRING_LEN] );
void heart_LOG_LogFailure( c_t[ESCHER_SYS_MAX_STRING_LEN] );
void heart_LOG_LogInfo( c_t[ESCHER_SYS_MAX_STRING_LEN] );
void heart_LOG_LogDate( Escher_Date_t, c_t[ESCHER_SYS_MAX_STRING_LEN] );
void heart_LOG_LogTime( c_t[ESCHER_SYS_MAX_STRING_LEN], Escher_TimeStamp_t );
void heart_LOG_LogReal( c_t[ESCHER_SYS_MAX_STRING_LEN], const r_t );
void heart_LOG_LogInteger( const i_t );

#ifdef	__cplusplus
}
#endif
#endif   /* HEART_LOG_BRIDGE_H */
