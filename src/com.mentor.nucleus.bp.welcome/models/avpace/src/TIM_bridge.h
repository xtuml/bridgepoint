/*----------------------------------------------------------------------------
 * File:  TIM_bridge.h
 *
 * Description:
 * This file provides a insulation layer between the BridgePoint built-in
 * External Enity 'Time' (Key Letters: TIM) and the underlying implementation.
 *
 * xtUML Timer methods:
 * Note that in the software architecture there will be a (finite but
 * indeterminate) delay between the expiration of a timer and the delivery
 * of the associated event to the receiving state machine.
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef TIM_BRIDGE_H
#define TIM_BRIDGE_H
#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Bridge method to create an analysis specific date instance.
 */
Escher_Date_t TIM_create_date(
  const i_t, const i_t, const i_t, const i_t, const i_t, const i_t );

/*
 * Bridge method to get the current date.
 */
Escher_Date_t TIM_current_date( void );

/*
 * Bridge methods to get time piece components of a given date.
 */
i_t TIM_get_year   ( const Escher_Date_t );
i_t TIM_get_month  ( const Escher_Date_t );
i_t TIM_get_day    ( const Escher_Date_t );
i_t TIM_get_hour   ( const Escher_Date_t );
i_t TIM_get_minute ( const Escher_Date_t );
i_t TIM_get_second ( const Escher_Date_t );

/*
 * Bridge method to get the current clock.
 */
Escher_TimeStamp_t TIM_current_clock( void );

/*
 * Starts a (one shot) timer to expire in <expression> microseconds,
 * sending the event instance <event> upon expiration.
 * Note that upon expiration the timer will be (automatically) deleted.
 * Returns the instance handle of the non-recurring timer.
 */
Escher_Timer_t * TIM_timer_start( Escher_xtUMLEvent_t *, const Escher_uSec_t );

/*
 * Starts a (recurring) timer to expire in <expression> microseconds,
 * sending the event instance <event> upon expiration.
 * Upon expiration, the timer will be restarted and fire again in
 * <expression> microseconds and (re)generate the event <event>.
 * Returns the instance handle of the recurring timer.
 */
Escher_Timer_t * TIM_timer_start_recurring( Escher_xtUMLEvent_t *, const Escher_uSec_t );

/*
 * Returns the time remaining (in microseconds) before the timer
 * specified by <timer> will expire.  If <timer> no longer exists,
 * a zero value will be returned.  It is important to note that even
 * if <timer> no longer exists, a <event> may still be in
 * transit (e.g., was generated) from a previous <timer> expiration.
 * Respectable analysis must account for this "ships passing" possibility.
 */
Escher_uSec_t TIM_timer_remaining_time( const Escher_Timer_t * const );

/*
 * This method attempts to set an existing timer handle <timer> to expire
 * in <expression> microseconds.  If the timer exists (e.g, it has not
 * already expired), a 'true' value is returned.  If the timer no longer
 * exists, a 'false' value is returned.  In either case, it is important
 * to note that an event may still be in transit (e.g., was already
 * generated) from a previous expiration prior to this method invocation.
 * Respectable analysis must account for this "ships passing" possibility.
 */
bool TIM_timer_reset_time( const Escher_uSec_t,
                                  Escher_Timer_t * const );

/*
 * This method attempts to add <expression> microseconds to the
 * timer handle <timer>.  If the timer exists (e.g, it has not
 * already expired), a 'true' value is returned.  If the timer no longer
 * exists, a 'false' value is returned.  In either case, it is important
 * to note that an event may still be in transit (e.g., was already
 * generated) from a previous expiration prior to this method invocation.
 * Respectable analysis must account for this "ships passing" possibility.
 */
bool TIM_timer_add_time( const Escher_uSec_t,
                                Escher_Timer_t * const );

/*
 * This method attempts to cancel and delete the timer handle specified
 * by <timer>.  If the timer still exists (e.g, it has not already
 * expired), a 'true' value is returned.  If the timer no longer exists,
 * 'false' value is returned.  In either case, it is important
 * to note that an event may still be in transit (e.g., was already
 * generated) from a previous expiration prior to this method invocation.
 * Respectable analysis must account for this "ships passing" possibility.
 */
bool TIM_timer_cancel( Escher_Timer_t * const );

/*
 * This provides a periodic tick to give the timer subsystem the
 * opportunity to check for expired timers and fire them and their
 * respective delayed events.  Calling this routine as often as possible
 * increases the accuracy and resolution of the delayed event timers.  One
 * appropriate place to make this call is from UserBackgroundProcessingCallout
 * found among the system callout routines.
 */
void TIM_tick( void );

/*
 * This routine is used by some tasking implementation to get a value
 * to load into a timed wait.
 */
void * TIM_duration_until_next_timer_pop( void * );

/*
 * This method initializes the timer subsystem.  It must be called
 * during system bring-up.  An appropriate place to make this call
 * is from UserPreOoaInitializationCallout.
 */
void TIM_init( void );

/*
 * Pause all ticking timers.  This is useful during debugging.  It
 * allows timers to freeze during stepping and debug interfacing.
 */
void TIM_pause( void );

/*
 * Resume all ticking timers.  This is useful during debugging.  It
 * allows timers to freeze during stepping and debug interfacing.
 * Ticking timers will have their expirations adjusted, and ticker
 * will continue ticking.
 */
void TIM_resume( void );

#ifdef	__cplusplus
}
#endif
#endif   /* TIM_BRIDGE_H */
