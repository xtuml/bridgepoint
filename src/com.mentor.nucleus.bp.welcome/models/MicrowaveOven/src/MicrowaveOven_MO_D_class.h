/*----------------------------------------------------------------------------
 * File:  MicrowaveOven_MO_D_class.h
 *
 * Class:       Door  (MO_D)
 * Component:   MicrowaveOven
 *
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/

#ifndef MICROWAVEOVEN_MO_D_CLASS_H
#define MICROWAVEOVEN_MO_D_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   Door  (MO_D)
 */
struct MicrowaveOven_MO_D {
  Escher_StateNumber_t current_state;
  /* application analysis class attributes */
  Escher_UniqueID_t DoorID;  /* * DoorID */
  bool is_secure;  /* - is_secure */

  /* relationship storage */
  MicrowaveOven_MO_O * MO_O_R4;
};



#define MicrowaveOven_MO_D_MAX_EXTENT_SIZE 10
extern Escher_Extent_t pG_MicrowaveOven_MO_D_extent;

/*
 * instance event:  MO_D1:'release'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_Devent1;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Devent1c;

/*
 * instance event:  MO_D2:'close'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_Devent2;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Devent2c;

/*
 * union of events targeted towards 'MO_D' state machine
 */
typedef union {
  MicrowaveOven_MO_Devent1 mo_d11;  
  MicrowaveOven_MO_Devent2 mo_d22;  
} MicrowaveOven_MO_D_Events_u;

/*
 * enumeration of state model states for class
 */
#define MicrowaveOven_MO_D_STATE_1 1  /* state [1]:  (Open) */
#define MicrowaveOven_MO_D_STATE_2 2  /* state [2]:  (Closed) */
/*
 * enumeration of state model event numbers
 */
#define MICROWAVEOVEN_MO_DEVENT1NUM 0  /* MO_D1:'release' */
#define MICROWAVEOVEN_MO_DEVENT2NUM 1  /* MO_D2:'close' */
extern void MicrowaveOven_MO_D_Dispatch( Escher_xtUMLEvent_t * );

#ifdef	__cplusplus
}
#endif

#endif  /* MICROWAVEOVEN_MO_D_CLASS_H */


