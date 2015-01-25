/*----------------------------------------------------------------------------
 * File:  LOG_bridge.h
 *
 * Description:
 * Methods for bridging to an external entity.
 *
 * External Entity:  Logging (LOG)
 * 
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef LOG_BRIDGE_H
#define LOG_BRIDGE_H
#ifdef	__cplusplus
extern	"C"	{
#endif

#include "GPSWatch_sys_types.h"

void LOG_LogSuccess( c_t[ESCHER_SYS_MAX_STRING_LEN] );
void LOG_LogFailure( c_t[ESCHER_SYS_MAX_STRING_LEN] );
void LOG_LogInfo( c_t[ESCHER_SYS_MAX_STRING_LEN] );
void LOG_LogDate( Escher_Date_t, c_t[ESCHER_SYS_MAX_STRING_LEN] );
void LOG_LogTime( c_t[ESCHER_SYS_MAX_STRING_LEN], Escher_TimeStamp_t );
void LOG_LogReal( c_t[ESCHER_SYS_MAX_STRING_LEN], const r_t );
void LOG_LogInteger( const i_t );

#ifdef	__cplusplus
}
#endif
#endif   /* LOG_BRIDGE_H */
