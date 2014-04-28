/*----------------------------------------------------------------------------
 * File:  pacemaker_LOG_bridge.c
 *
 * Description:
 * Methods for bridging to an external entity.
 *
 * External Entity:  Logging (LOG)
 * 
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "avpace_sys_types.h"
#include "pacemaker_LOG_bridge.h"
#include "TIM_bridge.h"
#include "pacemaker_classes.h"
#include "pacemaker_LOG_bridge.h"
#include "avpace_sys_types.h"

/*
 * Bridge:  LogInteger
 */
void
pacemaker_LOG_LogInteger( const i_t p_message )
{
  /* Replace/Insert your implementation code here... */

  /* WARNING!  Skipping unsuccessful or unparsed action.  */
}


/*
 * Bridge:  LogReal
 */
void
pacemaker_LOG_LogReal( c_t p_message[ESCHER_SYS_MAX_STRING_LEN], const r_t p_r )
{
  /* Replace/Insert your implementation code here... */
}


/*
 * Bridge:  LogTime
 */
void
pacemaker_LOG_LogTime( c_t p_message[ESCHER_SYS_MAX_STRING_LEN], Escher_TimeStamp_t p_t )
{
  /* Replace/Insert your implementation code here... */
}


/*
 * Bridge:  LogDate
 */
void
pacemaker_LOG_LogDate( Escher_Date_t p_d, c_t p_message[ESCHER_SYS_MAX_STRING_LEN] )
{
  /* Replace/Insert your implementation code here... */
}


/*
 * Bridge:  LogInfo
 */
void
pacemaker_LOG_LogInfo( c_t p_message[ESCHER_SYS_MAX_STRING_LEN] )
{
  /* Replace/Insert your implementation code here... */
}


/*
 * Bridge:  LogFailure
 */
void
pacemaker_LOG_LogFailure( c_t p_message[ESCHER_SYS_MAX_STRING_LEN] )
{
  /* Replace/Insert your implementation code here... */
}


/*
 * Bridge:  LogSuccess
 */
void
pacemaker_LOG_LogSuccess( c_t p_message[ESCHER_SYS_MAX_STRING_LEN] )
{
  /* Replace/Insert your implementation code here... */
}


