/*----------------------------------------------------------------------------
 * File:  MicrowaveOven_MO_IL_class.h
 *
 * Class:       Internal Light  (MO_IL)
 * Component:   MicrowaveOven
 *
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/

#ifndef MICROWAVEOVEN_MO_IL_CLASS_H
#define MICROWAVEOVEN_MO_IL_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   Internal Light  (MO_IL)
 */
struct MicrowaveOven_MO_IL {
  Escher_StateNumber_t current_state;
  /* application analysis class attributes */
  Escher_UniqueID_t LightID;  /* * LightID */

  /* relationship storage */
  /* Note:  No storage needed for MO_IL->MO_O[R2] */
};



#define MicrowaveOven_MO_IL_MAX_EXTENT_SIZE 10
extern Escher_Extent_t pG_MicrowaveOven_MO_IL_extent;

/*
 * instance event:  MO_IL1:'switch_on'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_ILevent1;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_ILevent1c;

/*
 * instance event:  MO_IL2:'switch_off'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_ILevent2;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_ILevent2c;

/*
 * union of events targeted towards 'MO_IL' state machine
 */
typedef union {
  MicrowaveOven_MO_ILevent1 mo_il11;  
  MicrowaveOven_MO_ILevent2 mo_il22;  
} MicrowaveOven_MO_IL_Events_u;

/*
 * enumeration of state model states for class
 */
#define MicrowaveOven_MO_IL_STATE_1 1  /* state [1]:  (Off) */
#define MicrowaveOven_MO_IL_STATE_2 2  /* state [2]:  (On) */
/*
 * enumeration of state model event numbers
 */
#define MICROWAVEOVEN_MO_ILEVENT1NUM 0  /* MO_IL1:'switch_on' */
#define MICROWAVEOVEN_MO_ILEVENT2NUM 1  /* MO_IL2:'switch_off' */
extern void MicrowaveOven_MO_IL_Dispatch( Escher_xtUMLEvent_t * );

#ifdef	__cplusplus
}
#endif

#endif  /* MICROWAVEOVEN_MO_IL_CLASS_H */


