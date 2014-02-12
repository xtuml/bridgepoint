/*----------------------------------------------------------------------------
 * File:  MicrowaveOven_MO_TS_class.h
 *
 * Class:       Test Sequences  (MO_TS)
 * Component:   MicrowaveOven
 *
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/

#ifndef MICROWAVEOVEN_MO_TS_CLASS_H
#define MICROWAVEOVEN_MO_TS_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   Test Sequences  (MO_TS)
 */
struct MicrowaveOven_MO_TS {
  Escher_StateNumber_t current_state;
  /* application analysis class attributes */
  Escher_UniqueID_t TestSeqID;  /* * TestSeqID */

};



#define MicrowaveOven_MO_TS_MAX_EXTENT_SIZE 10
extern Escher_Extent_t pG_MicrowaveOven_MO_TS_extent;

/*
 * instance event:  MO_TS2:'perform_test_seq_1'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_TSevent2;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_TSevent2c;

/*
 * instance event:  MO_TS3:'perform_test_seq_2'
 * warning:  Event is not used in application - no code generated.
 */

/*
 * instance event:  MO_TS1:'initialize'
 * warning:  Event is not used in application - no code generated.
 */

/*
 * instance event:  MO_TS4:'test_seq_complete'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_TSevent4;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_TSevent4c;

/*
 * union of events targeted towards 'MO_TS' state machine
 */
typedef union {
  MicrowaveOven_MO_TSevent2 mo_ts21;  
  MicrowaveOven_MO_TSevent4 mo_ts42;  
} MicrowaveOven_MO_TS_Events_u;

/*
 * enumeration of state model states for class
 */
#define MicrowaveOven_MO_TS_STATE_1 1  /* state [1]:  (Awaiting Test Sequence Initiation) */
#define MicrowaveOven_MO_TS_STATE_2 2  /* state [2]:  (Performing Test Sequence 1) */
#define MicrowaveOven_MO_TS_STATE_3 3  /* state [3]:  (Performing Test Sequence 2) */
#define MicrowaveOven_MO_TS_STATE_4 4  /* state [4]:  (Cooking Complete) */
/*
 * enumeration of state model event numbers
 */
#define MICROWAVEOVEN_MO_TSEVENT2NUM 0  /* MO_TS2:'perform_test_seq_1' */
#define MICROWAVEOVEN_MO_TSEVENT4NUM 1  /* MO_TS4:'test_seq_complete' */
extern void MicrowaveOven_MO_TS_Dispatch( Escher_xtUMLEvent_t * );

#ifdef	__cplusplus
}
#endif

#endif  /* MICROWAVEOVEN_MO_TS_CLASS_H */


