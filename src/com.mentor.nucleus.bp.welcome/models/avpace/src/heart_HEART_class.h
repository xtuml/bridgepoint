/*----------------------------------------------------------------------------
 * File:  heart_HEART_class.h
 *
 * Class:       heart  (HEART)
 * Component:   heart
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef HEART_HEART_CLASS_H
#define HEART_HEART_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   heart  (HEART)
 */
struct heart_HEART {
  Escher_StateNumber_t current_state;
  /* application analysis class attributes */

  /* relationship storage */
  /* Note:  No storage needed for HEART->SINUS_NODE[R1] */
};



#define heart_HEART_MAX_EXTENT_SIZE 10
extern Escher_Extent_t pG_heart_HEART_extent;

/*
 * class-based event:  out_to_body::diastolic_pace:'diastolic_pace'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} heart_HEART_CBevent1;
extern const Escher_xtUMLEventConstant_t heart_HEART_CBevent1c;

/*
 * class-based event:  out_to_body::systolic_pace:'systolic_pace'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} heart_HEART_CBevent2;
extern const Escher_xtUMLEventConstant_t heart_HEART_CBevent2c;

/*
 * union of events targeted towards 'HEART' state machine
 */
typedef union {
  heart_HEART_CBevent1 heart11;  
  heart_HEART_CBevent2 heart22;  
} heart_HEART_CB_Events_u;

/*
 * enumeration of state model states for class
 */
#define heart_HEART_CB_STATE_1 1  /* state [1]:  (receiving paces) */

/*
 * enumeration of state model event numbers
 */
#define HEART_HEART_CBEVENT2NUM 0  /* out_to_body::systolic_pace:'systolic_pace' */
#define HEART_HEART_CBEVENT1NUM 1  /* out_to_body::diastolic_pace:'diastolic_pace' */

extern void heart_HEART_CBDispatch( Escher_xtUMLEvent_t * );


/*
 * instance event:  HEART1:'done'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} heart_HEARTevent1;
extern const Escher_xtUMLEventConstant_t heart_HEARTevent1c;

/*
 * instance event:  HEART2:'diastolic_pace'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} heart_HEARTevent2;
extern const Escher_xtUMLEventConstant_t heart_HEARTevent2c;

/*
 * instance event:  HEART3:'systolic_pace'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} heart_HEARTevent3;
extern const Escher_xtUMLEventConstant_t heart_HEARTevent3c;

/*
 * instance event:  HEART4:'diastolic_pulse'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} heart_HEARTevent4;
extern const Escher_xtUMLEventConstant_t heart_HEARTevent4c;

/*
 * instance event:  HEART5:'systolic_pulse'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} heart_HEARTevent5;
extern const Escher_xtUMLEventConstant_t heart_HEARTevent5c;

/*
 * union of events targeted towards 'HEART' state machine
 */
typedef union {
  heart_HEARTevent1 heart11;  
  heart_HEARTevent2 heart22;  
  heart_HEARTevent3 heart33;  
  heart_HEARTevent4 heart44;  
  heart_HEARTevent5 heart55;  
} heart_HEART_Events_u;

/*
 * enumeration of state model states for class
 */
#define heart_HEART_STATE_1 1  /* state [1]:  (born) */
#define heart_HEART_STATE_2 2  /* state [2]:  (ventricular systole) */
#define heart_HEART_STATE_3 3  /* state [3]:  (atrial diastole) */
#define heart_HEART_STATE_4 4  /* state [4]:  (ventricular diastole) */
#define heart_HEART_STATE_5 5  /* state [5]:  (atrial systole) */
/*
 * enumeration of state model event numbers
 */
#define HEART_HEARTEVENT1NUM 0  /* HEART1:'done' */
#define HEART_HEARTEVENT2NUM 1  /* HEART2:'diastolic_pace' */
#define HEART_HEARTEVENT3NUM 2  /* HEART3:'systolic_pace' */
#define HEART_HEARTEVENT4NUM 3  /* HEART4:'diastolic_pulse' */
#define HEART_HEARTEVENT5NUM 4  /* HEART5:'systolic_pulse' */
extern void heart_HEART_Dispatch( Escher_xtUMLEvent_t * );

#ifdef	__cplusplus
}
#endif

#endif  /* HEART_HEART_CLASS_H */


