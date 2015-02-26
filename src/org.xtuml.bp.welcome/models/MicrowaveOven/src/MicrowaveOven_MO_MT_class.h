/*----------------------------------------------------------------------------
 * File:  MicrowaveOven_MO_MT_class.h
 *
 * Class:       Magnetron Tube  (MO_MT)
 * Component:   MicrowaveOven
 *
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/

#ifndef MICROWAVEOVEN_MO_MT_CLASS_H
#define MICROWAVEOVEN_MO_MT_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   Magnetron Tube  (MO_MT)
 */
struct MicrowaveOven_MO_MT {
  Escher_StateNumber_t current_state;
  /* application analysis class attributes */
  Escher_UniqueID_t TubeID;  /* * TubeID */
  s2_t current_power_output;  /* - current_power_output */

  /* relationship storage */
  /* Note:  No storage needed for MO_MT->MO_O[R1] */
};



#define MicrowaveOven_MO_MT_MAX_EXTENT_SIZE 10
extern Escher_Extent_t pG_MicrowaveOven_MO_MT_extent;

/*
 * instance event:  MO_MT1:'increase_power'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_MTevent1;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_MTevent1c;

/*
 * instance event:  MO_MT2:'decrease_power'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_MTevent2;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_MTevent2c;

/*
 * instance event:  MO_MT3:'power_on'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_MTevent3;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_MTevent3c;

/*
 * instance event:  MO_MT4:'power_off'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_MTevent4;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_MTevent4c;

/*
 * union of events targeted towards 'MO_MT' state machine
 */
typedef union {
  MicrowaveOven_MO_MTevent1 mo_mt11;  
  MicrowaveOven_MO_MTevent2 mo_mt22;  
  MicrowaveOven_MO_MTevent3 mo_mt33;  
  MicrowaveOven_MO_MTevent4 mo_mt44;  
} MicrowaveOven_MO_MT_Events_u;

/*
 * enumeration of state model states for class
 */
#define MicrowaveOven_MO_MT_STATE_1 1  /* state [1]:  (Output Power Stable and OFF) */
#define MicrowaveOven_MO_MT_STATE_2 2  /* state [2]:  (Reducing Output Power) */
#define MicrowaveOven_MO_MT_STATE_3 3  /* state [3]:  (Raising Output Power) */
#define MicrowaveOven_MO_MT_STATE_4 4  /* state [4]:  (Output Power Stable and ON) */
/*
 * enumeration of state model event numbers
 */
#define MICROWAVEOVEN_MO_MTEVENT1NUM 0  /* MO_MT1:'increase_power' */
#define MICROWAVEOVEN_MO_MTEVENT2NUM 1  /* MO_MT2:'decrease_power' */
#define MICROWAVEOVEN_MO_MTEVENT3NUM 2  /* MO_MT3:'power_on' */
#define MICROWAVEOVEN_MO_MTEVENT4NUM 3  /* MO_MT4:'power_off' */
extern void MicrowaveOven_MO_MT_Dispatch( Escher_xtUMLEvent_t * );

#ifdef	__cplusplus
}
#endif

#endif  /* MICROWAVEOVEN_MO_MT_CLASS_H */


