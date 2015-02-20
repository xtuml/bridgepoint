/*----------------------------------------------------------------------------
 * File:  hostmonitor_HM_class.h
 *
 * Class:       Host Monitor  (HM)
 * Component:   hostmonitor
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef HOSTMONITOR_HM_CLASS_H
#define HOSTMONITOR_HM_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   Host Monitor  (HM)
 */
struct hostmonitor_HM {
  Escher_StateNumber_t current_state;
  /* application analysis class attributes */
  i_t lastTemp;  /* - lastTemp */
  i_t lastRate;  /* - lastRate */

  /* relationship storage */
  hostmonitor_TM * TM_R2;
  hostmonitor_RM * RM_R3;
};



#define hostmonitor_HM_MAX_EXTENT_SIZE 10
extern Escher_Extent_t pG_hostmonitor_HM_extent;

/*
 * class-based event:  host::breath_taken:'breath_taken'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} hostmonitor_HM_CBevent1;
extern const Escher_xtUMLEventConstant_t hostmonitor_HM_CBevent1c;

/*
 * class-based event:  host::current_temp:'current_temp'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} hostmonitor_HM_CBevent2;
extern const Escher_xtUMLEventConstant_t hostmonitor_HM_CBevent2c;

/*
 * union of events targeted towards 'HM' state machine
 */
typedef union {
  hostmonitor_HM_CBevent1 hm11;  
  hostmonitor_HM_CBevent2 hm22;  
} hostmonitor_HM_CB_Events_u;

/*
 * enumeration of state model states for class
 */
#define hostmonitor_HM_CB_STATE_1 1  /* state [1]:  (listening to host events) */

/*
 * enumeration of state model event numbers
 */
#define HOSTMONITOR_HM_CBEVENT2NUM 0  /* host::current_temp:'current_temp' */
#define HOSTMONITOR_HM_CBEVENT1NUM 1  /* host::breath_taken:'breath_taken' */

extern void hostmonitor_HM_CBDispatch( Escher_xtUMLEvent_t * );


/*
 * instance event:  HM1:'poll'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} hostmonitor_HMevent1;
extern const Escher_xtUMLEventConstant_t hostmonitor_HMevent1c;

/*
 * instance event:  HM2:'increasedActivity'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} hostmonitor_HMevent2;
extern const Escher_xtUMLEventConstant_t hostmonitor_HMevent2c;

/*
 * union of events targeted towards 'HM' state machine
 */
typedef union {
  hostmonitor_HMevent1 hm11;  
  hostmonitor_HMevent2 hm22;  
} hostmonitor_HM_Events_u;

/*
 * enumeration of state model states for class
 */
#define hostmonitor_HM_STATE_1 1  /* state [1]:  (Polling monitors) */
#define hostmonitor_HM_STATE_2 2  /* state [2]:  (Notifying of increased activity) */
/*
 * enumeration of state model event numbers
 */
#define HOSTMONITOR_HMEVENT1NUM 0  /* HM1:'poll' */
#define HOSTMONITOR_HMEVENT2NUM 1  /* HM2:'increasedActivity' */
extern void hostmonitor_HM_Dispatch( Escher_xtUMLEvent_t * );

#ifdef	__cplusplus
}
#endif

#endif  /* HOSTMONITOR_HM_CLASS_H */


