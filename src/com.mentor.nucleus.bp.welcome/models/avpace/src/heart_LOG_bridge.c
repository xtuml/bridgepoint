/*----------------------------------------------------------------------------
 * File:  heart_LOG_bridge.c
 *
 * Description:
 * Methods for bridging to an external entity.
 *
 * External Entity:  Logging (LOG)
 * 
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "avpace_sys_types.h"
#include "heart_LOG_bridge.h"
#include "TIM_bridge.h"
#include "heart_classes.h"
#include "heart_LOG_bridge.h"
#include "avpace_sys_types.h"

/*
 * Bridge:  LogSuccess
 */
void
heart_LOG_LogSuccess( c_t p_message[ESCHER_SYS_MAX_STRING_LEN] )
{
  /* Replace/Insert your implementation code here... */
}


/*
 * Bridge:  LogFailure
 */
void
heart_LOG_LogFailure( c_t p_message[ESCHER_SYS_MAX_STRING_LEN] )
{
  /* Replace/Insert your implementation code here... */
}


/*
 * Bridge:  LogInfo
 */
void
heart_LOG_LogInfo( c_t p_message[ESCHER_SYS_MAX_STRING_LEN] )
{
  /* Replace/Insert your implementation code here... */
}


/*
 * Bridge:  LogDate
 */
void
heart_LOG_LogDate( Escher_Date_t p_d, c_t p_message[ESCHER_SYS_MAX_STRING_LEN] )
{
  /* Replace/Insert your implementation code here... */
}


/*
 * Bridge:  LogTime
 */
void
heart_LOG_LogTime( c_t p_message[ESCHER_SYS_MAX_STRING_LEN], Escher_TimeStamp_t p_t )
{
  /* Replace/Insert your implementation code here... */
}


/*
 * Bridge:  LogReal
 */
void
heart_LOG_LogReal( c_t p_message[ESCHER_SYS_MAX_STRING_LEN], const r_t p_r )
{
  /* Replace/Insert your implementation code here... */
}


/*
 * Bridge:  LogInteger
 */
void
heart_LOG_LogInteger( const i_t p_message )
{
  /* Replace/Insert your implementation code here... */

  /* WARNING!  Skipping unsuccessful or unparsed action.  */
}


