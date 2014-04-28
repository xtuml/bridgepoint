/*----------------------------------------------------------------------------
 * File:  heart_SINUS_NODE_class.h
 *
 * Class:       sinus node  (SINUS_NODE)
 * Component:   heart
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef HEART_SINUS_NODE_CLASS_H
#define HEART_SINUS_NODE_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   sinus node  (SINUS_NODE)
 */
struct heart_SINUS_NODE {
  Escher_StateNumber_t current_state;
  /* application analysis class attributes */
  i_t systolic_period;  /* - systolic_period */
  i_t diastolic_period;  /* - diastolic_period */
  i_t decrement;  /* - decrement */

  /* relationship storage */
  heart_HEART * HEART_R1_triggers;
};
void heart_SINUS_NODE_op_log_and_adjust( heart_SINUS_NODE * );

void heart_SINUS_NODE_R1_Link_is_triggered_by( heart_HEART *, heart_SINUS_NODE * );
/* Note:  HEART<-R1->SINUS_NODE unrelate accessor not needed */


#define heart_SINUS_NODE_MAX_EXTENT_SIZE 10
extern Escher_Extent_t pG_heart_SINUS_NODE_extent;

/*
 * instance event:  SINUS_NODE1:'pop'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} heart_SINUS_NODEevent1;
extern const Escher_xtUMLEventConstant_t heart_SINUS_NODEevent1c;

/*
 * instance event:  SINUS_NODE2:'period'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} heart_SINUS_NODEevent2;
extern const Escher_xtUMLEventConstant_t heart_SINUS_NODEevent2c;

/*
 * union of events targeted towards 'SINUS_NODE' state machine
 */
typedef union {
  heart_SINUS_NODEevent1 sinus_node11;  
  heart_SINUS_NODEevent2 sinus_node22;  
} heart_SINUS_NODE_Events_u;

/*
 * enumeration of state model states for class
 */
#define heart_SINUS_NODE_STATE_1 1  /* state [1]:  (pulsing diastolic) */
#define heart_SINUS_NODE_STATE_3 2  /* state [3]:  (pulsing systolic) */
/*
 * enumeration of state model event numbers
 */
#define HEART_SINUS_NODEEVENT1NUM 0  /* SINUS_NODE1:'pop' */
#define HEART_SINUS_NODEEVENT2NUM 1  /* SINUS_NODE2:'period' */
extern void heart_SINUS_NODE_Dispatch( Escher_xtUMLEvent_t * );

#ifdef	__cplusplus
}
#endif

#endif  /* HEART_SINUS_NODE_CLASS_H */


