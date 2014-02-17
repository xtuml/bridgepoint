/*----------------------------------------------------------------------------
 * File:  MicrowaveOven_MO_TRN_class.h
 *
 * Class:       Turntable  (MO_TRN)
 * Component:   MicrowaveOven
 *
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/

#ifndef MICROWAVEOVEN_MO_TRN_CLASS_H
#define MICROWAVEOVEN_MO_TRN_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   Turntable  (MO_TRN)
 */
struct MicrowaveOven_MO_TRN {
  Escher_StateNumber_t current_state;
  /* application analysis class attributes */
  Escher_UniqueID_t TurntableID;  /* * TurntableID */

  /* relationship storage */
  /* Note:  No storage needed for MO_TRN->MO_O[R5] */
};



#define MicrowaveOven_MO_TRN_MAX_EXTENT_SIZE 10
extern Escher_Extent_t pG_MicrowaveOven_MO_TRN_extent;

/*
 * instance event:  MO_TRN1:'spin'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_TRNevent1;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_TRNevent1c;

/*
 * instance event:  MO_TRN2:'stop'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_TRNevent2;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_TRNevent2c;

/*
 * union of events targeted towards 'MO_TRN' state machine
 */
typedef union {
  MicrowaveOven_MO_TRNevent1 mo_trn11;  
  MicrowaveOven_MO_TRNevent2 mo_trn22;  
} MicrowaveOven_MO_TRN_Events_u;

/*
 * enumeration of state model states for class
 */
#define MicrowaveOven_MO_TRN_STATE_1 1  /* state [1]:  (Stationary) */
#define MicrowaveOven_MO_TRN_STATE_2 2  /* state [2]:  (Rotating) */
/*
 * enumeration of state model event numbers
 */
#define MICROWAVEOVEN_MO_TRNEVENT1NUM 0  /* MO_TRN1:'spin' */
#define MICROWAVEOVEN_MO_TRNEVENT2NUM 1  /* MO_TRN2:'stop' */
extern void MicrowaveOven_MO_TRN_Dispatch( Escher_xtUMLEvent_t * );

#ifdef	__cplusplus
}
#endif

#endif  /* MICROWAVEOVEN_MO_TRN_CLASS_H */


