/*---------------------------------------------------------------------
 * File:  TIM_bridge.c
 *
 * External Entity:  Time (TIM)
 *                            
 * Description:
 * This file provides an implementation of the time functionally
 * similar to the standard Shlaer-Mellor timer functionality.
 * Usage is conformed to the bridge interface
 * as described in the BridgePoint Action Language Users Guide.
 *
 * Modify this file to match project design requirements.  Simply add
 * or remove code.  Special comments draw attention to where
 * modifications can most easily be made.
 *
 * Only a subset of the TIM interfaces are provided in this simple
 * prototype implementation.
 * Long integers are used to store time values thus limiting the
 * duration of timers and the system ticker to about 71 minutes.
 * The sample implementation uses the localtime, mktime, ftime
 * and time library routines.
 *
 * For this example implementation to work, TIM_init() must be
 * invoked at start-up (perhaps from UserInitializationCallout).
 * Also, TIM_tick() must be invoked periodically and as often
 * as practical (perhaps from UserBackgroundProcessingCallout).
 * The resolution of the timers is driven largely by the frequency
 * of invocation of TIM_tick().  Note that TIM_tick could be invoked
 * asynchronously based upon the duration of the next most pending
 * timer.
 *
 * your copyright statement can go here (from te_copyright.body)
 *-------------------------------------------------------------------*/

#include "GPSWatch_sys_types.h"
#include "TIM_bridge.h"
#include <sys/timeb.h>
#include <time.h>

#define USEC_CONVERT 1000UL

/*---------------------------------------------------------------------
 * Timer "Object" Structure Declaration
 *    [next] is the mechanism used to collect and sequence timers.
 *    Timer instances are strung together in an active (animate)
 *    list and an inactive (inanimate) list.  The next pointer
 *    provides the "hole for the beads".
 *    [expiration] is the system clock time at which this
 *    timer will pop.
 *    [recurrence] is the repeating expiration duration
 *    [event] is the handle of the event that the timer will
 *    generate upon expiration.
 *-------------------------------------------------------------------*/
typedef struct ETimer_s ETimer_t;
struct ETimer_s {
  ETimer_t * next;
  ETimer_time_t expiration;
  ETimer_time_t recurrence;
  Escher_xtUMLEvent_t * event;
  u4_t accesskey;
};

#ifdef USED_TO_ALLOW_PAUSING
static ETimer_time_t start_of_pause = 0;
static bool paused = false;
#endif
static ETimer_time_t tinit = 0;
static struct timeb systyme;
#if ESCHER_SYS_MAX_XTUML_TIMERS > 0
static ETimer_t swtimers[ ESCHER_SYS_MAX_XTUML_TIMERS ];  /* system.clr color */
static ETimer_t * animate = 0, * inanimate = 0;

static void timer_insert_sorted( ETimer_t * );
static void timer_fire( ETimer_t * const );
static ETimer_t *timer_start( const ETimer_time_t, Escher_xtUMLEvent_t * const );
static bool timer_cancel( ETimer_t * const );
static bool timer_find_and_delete( ETimer_t * const );
#endif   /* if ESCHER_SYS_MAX_XTUML_TIMERS > 0 */
static ETimer_time_t ETimer_msec_time( void );


#if ESCHER_SYS_MAX_XTUML_TIMERS > 0
/*=====================================================================
 * BridgePoint Primitive:
 * <timer_inst_ref_var> = TIM::timer_start(
 *   microseconds:<integer_var>,
 *   event_inst:<event_inst_var> )
 * This bridge starts up an instance of a one-shot xtUML timer.
 *===================================================================*/
Escher_Timer_t *
TIM_timer_start(
  Escher_xtUMLEvent_t * ee_event_inst,
  const Escher_uSec_t ee_microseconds )
{
  /* Insert implementation specific code here.  */
  ETimer_t * t = timer_start( ee_microseconds/USEC_CONVERT, ee_event_inst );
  t->recurrence = 0;
  return (Escher_Timer_t *) t;
}

/*=====================================================================
 * BridgePoint Primitive:
 * <timer_inst_ref_var> = TIM::timer_start_recurring(
 *   microseconds:<integer_var>,
 *   event_inst:<event_inst_var> )
 * This bridge starts up an instance of a recurring xtUML timer.
 *===================================================================*/
Escher_Timer_t *
TIM_timer_start_recurring(
  Escher_xtUMLEvent_t * ee_event_inst,
  const Escher_uSec_t ee_microseconds )
{
  /* Insert implementation specific code here.  */
  ETimer_t * t = timer_start( ee_microseconds/USEC_CONVERT, ee_event_inst );
  t->recurrence = ee_microseconds/USEC_CONVERT;
  return (Escher_Timer_t *) t;
}

/*=====================================================================
 * BridgePoint Primitive:
 * <integer_var> = TIM::timer_remaining_time(
 *   timer_inst_ref:<timer_inst_ref_var> )
 * Return the remaining time of the specified timer.
 *===================================================================*/
Escher_uSec_t
TIM_timer_remaining_time(
  const Escher_Timer_t * const ee_timer_inst_ref )
{
  /* Insert implementation specific code here.  */
  Escher_uSec_t t = 0UL;
  if ( ee_timer_inst_ref != 0 ) {
    t = ETimer_msec_time();
    t = ( ((ETimer_t *) ee_timer_inst_ref)->expiration > t ) ?
      USEC_CONVERT * ( ((ETimer_t *) ee_timer_inst_ref)->expiration - t ) :
      0UL;
  }
  return ( t );  
}

/*=====================================================================
 * BridgePoint Primitive:
 * <was_running_flag> = TIM::timer_reset_time(
 *   microseconds:<integer_var>,
 *   timer_inst_ref:<timer_inst_ref_var> )
 * Try to change expiration of an existing timer.
 * If successful, return true.
 *===================================================================*/
bool
TIM_timer_reset_time(
  const Escher_uSec_t ee_microseconds,
  Escher_Timer_t * const ee_timer_inst_ref )
{
  /* Insert implementation specific code here.  */
  ETimer_t * t = (ETimer_t *) ee_timer_inst_ref;
  bool rc = false;
  if ( ( t != 0 ) && ( t->expiration > 0UL ) ) {
    t->expiration = ETimer_msec_time() + ee_microseconds/USEC_CONVERT + 1UL;
    rc = true;
  }
  return ( rc );
}

/*=====================================================================
 * BridgePoint Primitive:
 * <was_running_flag> = TIM::timer_add_time(
 *   microseconds:<integer_var>,
 *   timer_inst_ref:<timer_inst_ref_var> )
 * Try to add time to an existing timer.
 * If successful, return true.
 *===================================================================*/
bool
TIM_timer_add_time(
  const Escher_uSec_t ee_microseconds,
  Escher_Timer_t * const ee_timer_inst_ref )
{
  /* Insert implementation specific code here.  */
  ETimer_t * t = (ETimer_t *) ee_timer_inst_ref;
  bool rc = false;
  if ( ( t != 0 ) && ( t->expiration > 0UL ) ) {
    t->expiration += ee_microseconds/USEC_CONVERT;
    rc = true;
  }
  return ( rc );
}

/*=====================================================================
 * BridgePoint Primitive:
 * <was_running_flag> = TIM::timer_cancel(
 *   timer_inst_ref:<timer_inst_ref_var> )
 * This attempts to cancel the specified timer.
 * Return true if we actually cancelled the timer.
 *===================================================================*/
bool
TIM_timer_cancel(
  Escher_Timer_t * const ee_timer_inst_ref )
{
  /* Insert implementation specific code here.  */
  return timer_cancel( (ETimer_t * const) ee_timer_inst_ref );
}
#endif   /* if ESCHER_SYS_MAX_XTUML_TIMERS > 0 */


/*=====================================================================
 * BridgePoint Primitive:
 * <date_var> = TIM::current_date()
 * Return a variable representing the current calendar date.
 *===================================================================*/
Escher_Date_t
TIM_current_date( void )
{
  /* Insert implementation specific code here.  */
  return ( (Escher_Date_t) time( 0 ) );
}

/*=====================================================================
 * BridgePoint Primitive:
 * <date_var> = TIM::create_date(
 *   day:<integer_var>,
 *   hour:<integer_var>,
 *   minute:<integer_var>,
 *   month:<integer_var>,
 *   second:<integer_var>,
 *   year:<integer_var> )
 * Return a variable representing the calendar date as specified
 * by the input components.
 *===================================================================*/
Escher_Date_t
TIM_create_date(
  const i_t ee_day,
  const i_t ee_hour,
  const i_t ee_minute,
  const i_t ee_month,
  const i_t ee_second,
  const i_t ee_year )
{
  /* Insert implementation specific code here.  */
  Escher_Date_t r = 0;
  struct tm t;
  t.tm_isdst = -1;
  t.tm_mday = ee_day;
  t.tm_hour = ee_hour;
  t.tm_min = ee_minute;
  t.tm_mon = ee_month;
  t.tm_sec = ee_second;
  t.tm_year = ee_year - 1900;      /* not enough space for 100 years */
  r = (Escher_Date_t) mktime( &t );
  return ( r );
}

/*=====================================================================
 * BridgePoint Primitive:
 * <integer_var> = TIM::get_second(
 *   date:<integer_var> )
 * Return the second field of the date variable.
 *===================================================================*/
i_t
TIM_get_second(
  const Escher_Date_t ee_date
)
{
  /* Insert implementation specific code here.  */
  struct tm * tp;
  tp = localtime( &ee_date );
  return ( tp ) ? tp->tm_sec : 0;
}

/*=====================================================================
 * BridgePoint Primitive:
 * <integer_var> = TIM::get_minute(
 *   date:<integer_var> )
 * Return the minute field of the date variable.
 *===================================================================*/
i_t
TIM_get_minute(
  const Escher_Date_t ee_date
)
{
  /* Insert implementation specific code here.  */
  struct tm * tp;
  tp = localtime( &ee_date );
  return ( tp ) ? tp->tm_min : 0;
}

/*=====================================================================
 * BridgePoint Primitive:
 * <integer_var> = TIM::get_hour(
 *   date:<integer_var> )
 * Return the hour field of the date variable.
 *===================================================================*/
i_t
TIM_get_hour(
  const Escher_Date_t ee_date
)
{
  /* Insert implementation specific code here.  */
  struct tm * tp;
  tp = localtime( &ee_date );
  return ( tp ) ? tp->tm_hour : 0;
}

/*=====================================================================
 * BridgePoint Primitive:
 * <integer_var> = TIM::get_day(
 *   date:<integer_var> )
 * Return the day field of the date variable.
 *===================================================================*/
i_t
TIM_get_day(
  const Escher_Date_t ee_date
)
{
  /* Insert implementation specific code here.  */
  struct tm * tp;
  tp = localtime( &ee_date );
  return ( tp ) ? tp->tm_mday : 0;
}

/*=====================================================================
 * BridgePoint Primitive:
 * <integer_var> = TIM::get_month(
 *   date:<integer_var> )
 * Return the month field of the date variable.
 *===================================================================*/
i_t
TIM_get_month(
  const Escher_Date_t ee_date
)
{
  /* Insert implementation specific code here.  */
  struct tm * tp;
  tp = localtime( &ee_date );
  return ( tp ) ? tp->tm_mon : 0;
}

/*=====================================================================
 * BridgePoint Primitive:
 * <integer_var> = TIM::get_year(
 *   date:<integer_var> )
 * Return the year field of the date variable.
 *===================================================================*/
i_t
TIM_get_year(
  const Escher_Date_t ee_date
)
{
  /* Insert implementation specific code here.  */
  struct tm * tp;
  tp = localtime( &ee_date );
  return ( tp ) ? tp->tm_year + 1900 : 0;
}

/*=====================================================================
 * BridgePoint Primitive:
 * <timestamp_var> = TIM::current_clock()
 * This bridge returns a system dependent time value.
 *===================================================================*/
Escher_TimeStamp_t
TIM_current_clock( void )
{
  /* Insert implementation specific code here.  */
  return ( ETimer_msec_time() );
}


/* Routines below are implementation specific.  Modify to taste.     */


#if ESCHER_SYS_MAX_XTUML_TIMERS > 0



/*---------------------------------------------------------------------
 * Get a timer instance from the inanimate list, provide the
 * expiration time and insert it into its proper location among
 * the currently ticking timers.
 *-------------------------------------------------------------------*/
static ETimer_t *
timer_start(
  const ETimer_time_t duration,
  Escher_xtUMLEvent_t * const event
)
{
  ETimer_t * t;
  t = inanimate;
  if ( t != 0 ) {
    inanimate = inanimate->next;
    t->event = event;
    /*---------------------------------------------------------------*/
    /* Calculate the timer expiration time.                          */
    /* Note:  Add one to the duration to make sure that delay is     */
    /* at least as long as duration.                                 */
    /*---------------------------------------------------------------*/
    t->expiration = ETimer_msec_time() + duration + 1UL;
    timer_insert_sorted( t );
  }
  return ( t );
}

/*---------------------------------------------------------------------
 * Insert a timer into the list of ticking timers maintaining
 * an order that fires timers chronologically.
 *-------------------------------------------------------------------*/
static void
timer_insert_sorted(
  ETimer_t * t
)
{
  if ( animate == 0 ) {                              /* empty list   */
    t->next = 0;
    animate = t;
  } else {
    ETimer_time_t poptime = t->expiration;
    if ( poptime <= animate->expiration ) {          /* before head  */
      t->next = animate;
      animate = t;         
    } else {                                         /* find bigger  */
      ETimer_t * prev = animate;
      ETimer_t * cursor;
      while ( ( cursor = prev->next ) != 0 ) {
        if ( poptime <= cursor->expiration ) {
          break;
        }
        prev = cursor;
      }
      prev->next = t;                                /* link in      */
      t->next = cursor;
    }
  }
}

/*---------------------------------------------------------------------
 * Try to find a ticking timer and move to the non-ticking list.
 *-------------------------------------------------------------------*/
static bool
timer_find_and_delete( ETimer_t * const t )
{
  bool rc = false;
  if ( ( t != 0 ) && ( animate != 0 ) ) {
    /*---------------------------------------------------------------*/
    /* Check to see if the timer has already been reset.  This       */
    /* check is probabilistic; it could have a hole if multitasked.  */
    /* We need to try to unlink and see if we actually unlinked.     */
    /* Attempt to remove the timer from the list of running timers.  */
    /* If we succeed, then we can cancel/delete the timer.  If the   */
    /* timer is not in the list, then there is no point in           */
    /* attempting to do any more.                                    */
    /*---------------------------------------------------------------*/
    if ( t == animate ) {
      animate = animate->next;
    } else {
      ETimer_t * prev = animate;
      ETimer_t * cursor;
      while ( ( cursor = prev->next ) != t ) {           /* find */
        if ( cursor == 0 ) {
          return ( false );
        }
        prev = cursor;
      }
      prev->next = t->next;                             /* unlink */
    }
    t->expiration = 0; /* in case anyone tries to read the handle */
    t->next = inanimate;
    inanimate = t;
    rc = true;
  }
  return rc;
}

/*---------------------------------------------------------------------
 * Cancel the given timer if possible.
 *-------------------------------------------------------------------*/
static bool
timer_cancel(
  ETimer_t * const t
)
{
  bool rc; Escher_xtUMLEvent_t * e;
  rc = timer_find_and_delete( t );
  e = t->event;
  if ( true == rc ) {
    if ( 0 != e ) {
      Escher_DeletextUMLEvent( e );
    }
  }
  return ( rc );
}


/*---------------------------------------------------------------------
 * Generate delayed event to the application.
 * Deactivate fired timer.
 *-------------------------------------------------------------------*/
static void
timer_fire(
  ETimer_t * const t
)
{
  t->expiration = ( t->recurrence == 0 ) ? 0 : t->expiration + t->recurrence;
  Escher_SendEvent( t->event );
  if ( 0 != t->recurrence ) {
    Escher_xtUMLEvent_t * e = Escher_AllocatextUMLEvent();
    Escher_memmove( e, t->event, sizeof( Escher_xtUMLEvent_t ) );
    t->event = e;
    animate = animate->next;      /* Remove from front of list.    */
    timer_insert_sorted( t );
  } else {
  animate = animate->next;        /* Remove from active list.      */
  t->next = inanimate;            /* Connect to inactive list.     */
  inanimate = t;
  }
}
#endif   /* if ESCHER_SYS_MAX_XTUML_TIMERS > 0 */

/*---------------------------------------------------------------------
 * This is the low level mechanism for monotonically increasing
 * at a constant rate.  Substitute code here to read some
 * sort of hardware timer.
 *-------------------------------------------------------------------*/
static ETimer_time_t
ETimer_msec_time( void )
{
  ETimer_time_t t;
  ftime( &systyme );
  t = ( systyme.time * USEC_CONVERT ) + systyme.millitm;
  return ( t - tinit );

}



/*---------------------------------------------------------------------
 * Initialize the tick mechanism and the timer instances.
 *-------------------------------------------------------------------*/
void
TIM_init( void )
{
#if ESCHER_SYS_MAX_XTUML_TIMERS > 0
  u2_t i;
  /*-----------------------------------------------------------------*/
  /* Build the collection (linked list) of timers.                   */
  /*-----------------------------------------------------------------*/
  animate = 0; inanimate = 0;
  for ( i = 0; i < ESCHER_SYS_MAX_XTUML_TIMERS; i++ ) {
    swtimers[ i ].expiration = 0;
    swtimers[ i ].recurrence = 0;
    swtimers[ i ].event = 0;
    swtimers[ i ].next = inanimate;
    inanimate = &swtimers[ i ];
  }
#endif   /* if ESCHER_SYS_MAX_XTUML_TIMERS > 0 */
  ftime( &systyme );            /* Initialize the hardware ticker.   */
  tinit = ( systyme.time * USEC_CONVERT ) + systyme.millitm;
}

/*---------------------------------------------------------------------
 * This is the repetitively invoked timer poller.
 * This routine needs to be run periodically.
 *-------------------------------------------------------------------*/
void
TIM_tick( void )
{
#if ESCHER_SYS_MAX_XTUML_TIMERS > 0
  /*-----------------------------------------------------------------*/
  /* Check to see if there are timers in the ticking timers list.    */
  /*-----------------------------------------------------------------*/
  if ( animate != 0 ) {
    if ( animate->expiration <= ETimer_msec_time() ) {
      timer_fire( animate );
    }
  }
#endif   /* if ESCHER_SYS_MAX_XTUML_TIMERS > 0 */
}

#ifdef USED_TO_ALLOW_PAUSING
/*---------------------------------------------------------------------
 * Pause the tick mechanism such that all timers simply freeze
 * and do not advance toward expiration.  This is used for debug.
 * Record the system time when pause begins.
 *-------------------------------------------------------------------*/
void
TIM_pause( void )
{
  #if ESCHER_SYS_MAX_XTUML_TIMERS > 0
  paused = true;
  start_of_pause = ETimer_msec_time();
  #endif   /* if ESCHER_SYS_MAX_XTUML_TIMERS > 0 */
}

/*---------------------------------------------------------------------
 * Resume the tick mechanism.  Update the expiration time for all
 * ticking timers.
 *-------------------------------------------------------------------*/
void
TIM_resume( void )
{
  #if ESCHER_SYS_MAX_XTUML_TIMERS > 0
  ETimer_t * cursor = animate;
  ETimer_time_t t;      /* difference between now and start of pause */
  t = ETimer_msec_time() - start_of_pause;
  while ( cursor != 0 ) {
    cursor->expiration += t;
    cursor = cursor->next;
  }
  paused = false;
  #endif   /* if ESCHER_SYS_MAX_XTUML_TIMERS > 0 */
}
#endif
