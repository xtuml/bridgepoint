/*----------------------------------------------------------------------------
 * File:  HeartRateMonitor_HeartRateMonitor_class.h
 *
 * Class:       HeartRateMonitor  (HeartRateMonitor)
 * Component:   HeartRateMonitor
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef HEARTRATEMONITOR_HEARTRATEMONITOR_CLASS_H
#define HEARTRATEMONITOR_HEARTRATEMONITOR_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   HeartRateMonitor  (HeartRateMonitor)
 */
struct HeartRateMonitor_HeartRateMonitor {

  /* application analysis class attributes */
  r_t recentHeartRate;  /* - recentHeartRate */
  Escher_Timer_t * timer;  /* - timer */

};



#define HeartRateMonitor_HeartRateMonitor_MAX_EXTENT_SIZE 200
extern Escher_Extent_t pG_HeartRateMonitor_HeartRateMonitor_extent;

/*
 * class-based event:  HR::registerListener:'registerListener'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} HeartRateMonitor_HeartRateMonitor_CBevent1;
extern const Escher_xtUMLEventConstant_t HeartRateMonitor_HeartRateMonitor_CBevent1c;

/*
 * class-based event:  HR::unregisterListener:'unregisterListener'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} HeartRateMonitor_HeartRateMonitor_CBevent2;
extern const Escher_xtUMLEventConstant_t HeartRateMonitor_HeartRateMonitor_CBevent2c;

/*
 * class-based event:  HeartRateMonitor_A3:'timeout'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} HeartRateMonitor_HeartRateMonitor_CBevent3;
extern const Escher_xtUMLEventConstant_t HeartRateMonitor_HeartRateMonitor_CBevent3c;

/*
 * union of events targeted towards 'HeartRateMonitor' state machine
 */
typedef union {
  HeartRateMonitor_HeartRateMonitor_CBevent1 heartratemonitor11;  
  HeartRateMonitor_HeartRateMonitor_CBevent2 heartratemonitor22;  
  HeartRateMonitor_HeartRateMonitor_CBevent3 heartratemonitor33;  
} HeartRateMonitor_HeartRateMonitor_CB_Events_u;

/*
 * enumeration of state model states for class
 */
#define HeartRateMonitor_HeartRateMonitor_CB_STATE_1 1  /* state [1]:  (idle) */
#define HeartRateMonitor_HeartRateMonitor_CB_STATE_2 2  /* state [2]:  (monitoring) */

/*
 * enumeration of state model event numbers
 */
#define HEARTRATEMONITOR_HEARTRATEMONITOR_CBEVENT3NUM 0  /* HeartRateMonitor_A3:'timeout' */
#define HEARTRATEMONITOR_HEARTRATEMONITOR_CBEVENT1NUM 1  /* HR::registerListener:'registerListener' */
#define HEARTRATEMONITOR_HEARTRATEMONITOR_CBEVENT2NUM 2  /* HR::unregisterListener:'unregisterListener' */

extern void HeartRateMonitor_HeartRateMonitor_CBDispatch( Escher_xtUMLEvent_t * );


#ifdef	__cplusplus
}
#endif

#endif  /* HEARTRATEMONITOR_HEARTRATEMONITOR_CLASS_H */


