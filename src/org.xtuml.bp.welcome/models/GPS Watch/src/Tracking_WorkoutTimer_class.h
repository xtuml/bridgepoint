/*----------------------------------------------------------------------------
 * File:  Tracking_WorkoutTimer_class.h
 *
 * Class:       WorkoutTimer  (WorkoutTimer)
 * Component:   Tracking
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef TRACKING_WORKOUTTIMER_CLASS_H
#define TRACKING_WORKOUTTIMER_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   WorkoutTimer  (WorkoutTimer)
 */
struct Tracking_WorkoutTimer {
  Escher_StateNumber_t current_state;
  /* application analysis class attributes */
  i_t time;  /* - time */
  Escher_Timer_t * timer;  /* - timer */

  /* relationship storage */
  Tracking_TrackLog * TrackLog_R4;
};
void Tracking_WorkoutTimer_op_activate( Tracking_WorkoutTimer * );
void Tracking_WorkoutTimer_op_deactivate( Tracking_WorkoutTimer * );

void Tracking_WorkoutTimer_R4_Link( Tracking_TrackLog *, Tracking_WorkoutTimer * );
void Tracking_WorkoutTimer_R4_Unlink( Tracking_TrackLog *, Tracking_WorkoutTimer * );


#define Tracking_WorkoutTimer_MAX_EXTENT_SIZE 200
extern Escher_Extent_t pG_Tracking_WorkoutTimer_extent;

/*
 * instance event:  WorkoutTimer1:'startStopPressed'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} Tracking_WorkoutTimerevent1;
extern const Escher_xtUMLEventConstant_t Tracking_WorkoutTimerevent1c;

/*
 * instance event:  WorkoutTimer2:'lapResetPressed'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} Tracking_WorkoutTimerevent2;
extern const Escher_xtUMLEventConstant_t Tracking_WorkoutTimerevent2c;

/*
 * instance event:  WorkoutTimer3:'tick'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} Tracking_WorkoutTimerevent3;
extern const Escher_xtUMLEventConstant_t Tracking_WorkoutTimerevent3c;

/*
 * union of events targeted towards 'WorkoutTimer' state machine
 */
typedef union {
  Tracking_WorkoutTimerevent1 workouttimer11;  
  Tracking_WorkoutTimerevent2 workouttimer22;  
  Tracking_WorkoutTimerevent3 workouttimer33;  
} Tracking_WorkoutTimer_Events_u;

/*
 * enumeration of state model states for class
 */
#define Tracking_WorkoutTimer_STATE_1 1  /* state [1]:  (stopped) */
#define Tracking_WorkoutTimer_STATE_2 2  /* state [2]:  (running) */
#define Tracking_WorkoutTimer_STATE_3 3  /* state [3]:  (paused) */
/*
 * enumeration of state model event numbers
 */
#define TRACKING_WORKOUTTIMEREVENT1NUM 0  /* WorkoutTimer1:'startStopPressed' */
#define TRACKING_WORKOUTTIMEREVENT2NUM 1  /* WorkoutTimer2:'lapResetPressed' */
#define TRACKING_WORKOUTTIMEREVENT3NUM 2  /* WorkoutTimer3:'tick' */
extern void Tracking_WorkoutTimer_Dispatch( Escher_xtUMLEvent_t * );

#ifdef	__cplusplus
}
#endif

#endif  /* TRACKING_WORKOUTTIMER_CLASS_H */


