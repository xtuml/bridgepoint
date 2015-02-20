/*----------------------------------------------------------------------------
 * File:  MicrowaveOven_MO_O_class.h
 *
 * Class:       Oven  (MO_O)
 * Component:   MicrowaveOven
 *
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/

#ifndef MICROWAVEOVEN_MO_O_CLASS_H
#define MICROWAVEOVEN_MO_O_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   Oven  (MO_O)
 */
struct MicrowaveOven_MO_O {
  Escher_StateNumber_t current_state;
  /* application analysis class attributes */
  Escher_UniqueID_t OvenID;  /* * OvenID */
  Escher_UniqueID_t TubeID;  /* - TubeID (R1) */
  Escher_UniqueID_t LightID;  /* - LightID (R2) */
  Escher_UniqueID_t BeeperID;  /* - BeeperID (R3) */
  Escher_UniqueID_t DoorID;  /* - DoorID (R4) */
  Escher_UniqueID_t TurntableID;  /* - TurntableID (R5) */
  Escher_Timer_t * oven_timer;  /* - oven_timer */
  i_t remaining_cooking_time;  /* - remaining_cooking_time */

  /* relationship storage */
  MicrowaveOven_MO_MT * MO_MT_R1;
  MicrowaveOven_MO_IL * MO_IL_R2;
  MicrowaveOven_MO_B * MO_B_R3;
  MicrowaveOven_MO_D * MO_D_R4;
  MicrowaveOven_MO_TRN * MO_TRN_R5;
};

extern void MicrowaveOven_MO_O_R1_Link( MicrowaveOven_MO_MT *, MicrowaveOven_MO_O * );
/* Note:  MO_MT<-R1->MO_O unrelate accessor not needed */
extern void MicrowaveOven_MO_O_R2_Link( MicrowaveOven_MO_IL *, MicrowaveOven_MO_O * );
/* Note:  MO_IL<-R2->MO_O unrelate accessor not needed */
extern void MicrowaveOven_MO_O_R3_Link( MicrowaveOven_MO_B *, MicrowaveOven_MO_O * );
/* Note:  MO_B<-R3->MO_O unrelate accessor not needed */
extern void MicrowaveOven_MO_O_R4_Link( MicrowaveOven_MO_D *, MicrowaveOven_MO_O * );
/* Note:  MO_D<-R4->MO_O unrelate accessor not needed */
extern void MicrowaveOven_MO_O_R5_Link( MicrowaveOven_MO_TRN *, MicrowaveOven_MO_O * );
/* Note:  MO_TRN<-R5->MO_O unrelate accessor not needed */


#define MicrowaveOven_MO_O_MAX_EXTENT_SIZE 10
extern Escher_Extent_t pG_MicrowaveOven_MO_O_extent;

/*
 * instance event:  MO_O1:'initialise'
 * warning:  Event is not used in application - no code generated.
 */

/*
 * instance event:  MO_O3:'start_cooking'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_Oevent3;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Oevent3c;

/*
 * instance event:  MO_O4:'cancel_cooking'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_Oevent4;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Oevent4c;

/*
 * instance event:  MO_O2:'oven_initialised'
 * warning:  Event is not used in application - no code generated.
 */

/*
 * instance event:  MO_O5:'cooking_period_over'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_Oevent5;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Oevent5c;

/*
 * instance event:  MO_O6:'beeping_over'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_Oevent6;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Oevent6c;

/*
 * instance event:  MO_O7:'oven_safe'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} MicrowaveOven_MO_Oevent7;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Oevent7c;

/*
 * instance event:  MO_O8:'cooking_period'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  i_t p_period; /* period */
} MicrowaveOven_MO_Oevent8;
extern const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Oevent8c;

/*
 * instance event:  MO_O9:'Unassigned Parameter Placeholder'
 * warning:  Event is not used in application - no code generated.
 */

/*
 * union of events targeted towards 'MO_O' state machine
 */
typedef union {
  MicrowaveOven_MO_Oevent3 mo_o31;  
  MicrowaveOven_MO_Oevent4 mo_o42;  
  MicrowaveOven_MO_Oevent5 mo_o53;  
  MicrowaveOven_MO_Oevent6 mo_o64;  
  MicrowaveOven_MO_Oevent7 mo_o75;  
  MicrowaveOven_MO_Oevent8 mo_o86;  
} MicrowaveOven_MO_O_Events_u;

/*
 * enumeration of state model states for class
 */
#define MicrowaveOven_MO_O_STATE_1 1  /* state [1]:  (Awaiting Cooking Request) */
#define MicrowaveOven_MO_O_STATE_2 2  /* state [2]:  (Ensuring Safe to Cook) */
#define MicrowaveOven_MO_O_STATE_3 3  /* state [3]:  (Cooking) */
#define MicrowaveOven_MO_O_STATE_4 4  /* state [4]:  (Cooking Suspended) */
#define MicrowaveOven_MO_O_STATE_5 5  /* state [5]:  (Signalling Cooking Period Over) */
#define MicrowaveOven_MO_O_STATE_6 6  /* state [6]:  (Assigning Cooking Period) */
/*
 * enumeration of state model event numbers
 */
#define MICROWAVEOVEN_MO_OEVENT3NUM 0  /* MO_O3:'start_cooking' */
#define MICROWAVEOVEN_MO_OEVENT4NUM 1  /* MO_O4:'cancel_cooking' */
#define MICROWAVEOVEN_MO_OEVENT5NUM 2  /* MO_O5:'cooking_period_over' */
#define MICROWAVEOVEN_MO_OEVENT6NUM 3  /* MO_O6:'beeping_over' */
#define MICROWAVEOVEN_MO_OEVENT7NUM 4  /* MO_O7:'oven_safe' */
#define MICROWAVEOVEN_MO_OEVENT8NUM 5  /* MO_O8:'cooking_period' */
extern void MicrowaveOven_MO_O_Dispatch( Escher_xtUMLEvent_t * );

#ifdef	__cplusplus
}
#endif

#endif  /* MICROWAVEOVEN_MO_O_CLASS_H */


