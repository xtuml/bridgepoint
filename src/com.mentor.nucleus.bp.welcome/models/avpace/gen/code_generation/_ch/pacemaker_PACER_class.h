/*----------------------------------------------------------------------------
 * File:  pacemaker_PACER_class.h
 *
 * Class:       pacer  (PACER)
 * Component:   pacemaker
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef PACEMAKER_PACER_CLASS_H
#define PACEMAKER_PACER_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   pacer  (PACER)
 */
struct pacemaker_PACER {
  Escher_StateNumber_t current_state;
  /* application analysis class attributes */
  i_t systolic_tolerance;  /* - systolic_tolerance */
  i_t diastolic_tolerance;  /* - diastolic_tolerance */
  i_t cycle_count;  /* - cycle_count */
  Escher_Timer_t * timeout_timer;  /* - timeout_timer */
  i_t systolic_timeout;  /* - systolic_timeout */
  i_t diastolic_timeout;  /* - diastolic_timeout */

};



#define pacemaker_PACER_MAX_EXTENT_SIZE 10
extern Escher_Extent_t pG_pacemaker_PACER_extent;

/*
 * class-based event:  to_heart::diastolic_pulse:'diastolic_pulse'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} pacemaker_PACER_CBevent3;
extern const Escher_xtUMLEventConstant_t pacemaker_PACER_CBevent3c;

/*
 * class-based event:  to_heart::systolic_pulse:'systolic_pulse'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} pacemaker_PACER_CBevent4;
extern const Escher_xtUMLEventConstant_t pacemaker_PACER_CBevent4c;

/*
 * class-based event:  monitor::increased_activty:'increased_activty'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  i_t p_current_rate; /* current_rate */
  i_t p_current_temp; /* current_temp */
} pacemaker_PACER_CBevent5;
extern const Escher_xtUMLEventConstant_t pacemaker_PACER_CBevent5c;

/*
 * union of events targeted towards 'PACER' state machine
 */
typedef union {
  pacemaker_PACER_CBevent3 pacer31;  
  pacemaker_PACER_CBevent4 pacer42;  
  pacemaker_PACER_CBevent5 pacer53;  
} pacemaker_PACER_CB_Events_u;

/*
 * enumeration of state model states for class
 */
#define pacemaker_PACER_CB_STATE_1 1  /* state [1]:  (monitoring host) */

/*
 * enumeration of state model event numbers
 */
#define PACEMAKER_PACER_CBEVENT5NUM 0  /* monitor::increased_activty:'increased_activty' */
#define PACEMAKER_PACER_CBEVENT4NUM 1  /* to_heart::systolic_pulse:'systolic_pulse' */
#define PACEMAKER_PACER_CBEVENT3NUM 2  /* to_heart::diastolic_pulse:'diastolic_pulse' */

extern void pacemaker_PACER_CBDispatch( Escher_xtUMLEvent_t * );


/*
 * instance event:  PACER1:'systolic_pulse'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} pacemaker_PACERevent1;
extern const Escher_xtUMLEventConstant_t pacemaker_PACERevent1c;

/*
 * instance event:  PACER2:'diastolic_pulse'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} pacemaker_PACERevent2;
extern const Escher_xtUMLEventConstant_t pacemaker_PACERevent2c;

/*
 * instance event:  PACER3:'timeout'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} pacemaker_PACERevent3;
extern const Escher_xtUMLEventConstant_t pacemaker_PACERevent3c;

/*
 * instance event:  PACER4:'increased_activity'
 * warning:  Event is not used in application - no code generated.
 */

/*
 * instance event:  PACER5:'rate_increased'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} pacemaker_PACERevent5;
extern const Escher_xtUMLEventConstant_t pacemaker_PACERevent5c;

/*
 * union of events targeted towards 'PACER' state machine
 */
typedef union {
  pacemaker_PACERevent1 pacer11;  
  pacemaker_PACERevent2 pacer22;  
  pacemaker_PACERevent3 pacer33;  
  pacemaker_PACERevent5 pacer54;  
} pacemaker_PACER_Events_u;

/*
 * enumeration of state model states for class
 */
#define pacemaker_PACER_STATE_1 1  /* state [1]:  (syncing) */
#define pacemaker_PACER_STATE_2 2  /* state [2]:  (listening for systolic pulse) */
#define pacemaker_PACER_STATE_3 3  /* state [3]:  (listening for diastolic pulse) */
#define pacemaker_PACER_STATE_4 4  /* state [4]:  (Increasing rate) */
/*
 * enumeration of state model event numbers
 */
#define PACEMAKER_PACEREVENT1NUM 0  /* PACER1:'systolic_pulse' */
#define PACEMAKER_PACEREVENT2NUM 1  /* PACER2:'diastolic_pulse' */
#define PACEMAKER_PACEREVENT3NUM 2  /* PACER3:'timeout' */
#define PACEMAKER_PACEREVENT5NUM 3  /* PACER5:'rate_increased' */
extern void pacemaker_PACER_Dispatch( Escher_xtUMLEvent_t * );

#ifdef	__cplusplus
}
#endif

#endif  /* PACEMAKER_PACER_CLASS_H */


