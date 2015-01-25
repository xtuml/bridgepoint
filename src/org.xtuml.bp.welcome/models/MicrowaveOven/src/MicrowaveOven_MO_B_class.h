/*----------------------------------------------------------------------------
 * File:  MicrowaveOven_MO_B_class.h
 *
 * Class:       Beeper  (MO_B)
 * Component:   MicrowaveOven
 *
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/

#ifndef MICROWAVEOVEN_MO_B_CLASS_H
#define MICROWAVEOVEN_MO_B_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   Beeper  (MO_B)
 */
struct MicrowaveOven_MO_B {
  Escher_StateNumber_t current_state;
  /* application analysis class attributes */
  Escher_UniqueID_t BeeperID;  /* * BeeperID */
  Escher_Timer_t * beeper_timer;  /* - beeper_timer */
  i_t beep_count;  /* - beep_count */
  Escher_xtUMLEvent_t * beeper_delay_over;  /* - beeper_delay_over */

  /* relationship storage */
  MicrowaveOven_MO_O * MO_O_R3;
};



#define MicrowaveOven_MO_B_MAX_EXTENT_SIZE 10
extern Escher_Extent_t pG_MicrowaveOven_MO_B_extent;

/*
 * instance event:  MO_B1:'start_beeping'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_Bevent1;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Bevent1c;

/*
 * instance event:  MO_B2:'beep_delay_over'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_Bevent2;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Bevent2c;

/*
 * instance event:  MO_B3:'beeping_stopped'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_Bevent3;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Bevent3c;

/*
 * instance event:  MO_B4:'stop_beeping'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_Bevent4;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Bevent4c;

/*
 * union of events targeted towards 'MO_B' state machine
 */
typedef union {
  MicrowaveOven_MO_Bevent1 mo_b11;  
  MicrowaveOven_MO_Bevent2 mo_b22;  
  MicrowaveOven_MO_Bevent3 mo_b33;  
  MicrowaveOven_MO_Bevent4 mo_b44;  
} MicrowaveOven_MO_B_Events_u;

/*
 * enumeration of state model states for class
 */
#define MicrowaveOven_MO_B_STATE_1 1  /* state [1]:  (Awaiting Beeper Request) */
#define MicrowaveOven_MO_B_STATE_2 2  /* state [2]:  (Beeping) */
/*
 * enumeration of state model event numbers
 */
#define MICROWAVEOVEN_MO_BEVENT1NUM 0  /* MO_B1:'start_beeping' */
#define MICROWAVEOVEN_MO_BEVENT2NUM 1  /* MO_B2:'beep_delay_over' */
#define MICROWAVEOVEN_MO_BEVENT3NUM 2  /* MO_B3:'beeping_stopped' */
#define MICROWAVEOVEN_MO_BEVENT4NUM 3  /* MO_B4:'stop_beeping' */
extern void MicrowaveOven_MO_B_Dispatch( Escher_xtUMLEvent_t * );

#ifdef	__cplusplus
}
#endif

#endif  /* MICROWAVEOVEN_MO_B_CLASS_H */


