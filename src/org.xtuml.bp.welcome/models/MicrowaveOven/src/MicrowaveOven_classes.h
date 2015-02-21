/*----------------------------------------------------------------------------
 * File:  MicrowaveOven_classes.h
 *
 * This file defines the object type identification numbers for all classes
 * in the component:  MicrowaveOven
 *
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/

#ifndef MICROWAVEOVEN_CLASSES_H
#define MICROWAVEOVEN_CLASSES_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Initialization services for component:  MicrowaveOven
 */
extern Escher_Extent_t * const MicrowaveOven_class_info[];
extern const EventTaker_t MicrowaveOven_EventDispatcher[];
extern void MicrowaveOven_execute_initialization( void );

#define MicrowaveOven_STATE_MODELS 7


/* Define constants to map to class numbers.  */
#define MicrowaveOven_MO_B_CLASS_NUMBER 0
#define MicrowaveOven_MO_D_CLASS_NUMBER 1
#define MicrowaveOven_MO_IL_CLASS_NUMBER 2
#define MicrowaveOven_MO_MT_CLASS_NUMBER 3
#define MicrowaveOven_MO_O_CLASS_NUMBER 4
#define MicrowaveOven_MO_TRN_CLASS_NUMBER 5
#define MicrowaveOven_MO_TS_CLASS_NUMBER 6
#define MicrowaveOven_MAX_CLASS_NUMBERS 7

/* Provide a map of classes to task numbers.  */
#define MicrowaveOven_TASK_NUMBERS  0, 0, 0, 0, 0, 0, 0

#define MicrowaveOven_CLASS_INFO_INIT\
  &pG_MicrowaveOven_MO_B_extent,\
  &pG_MicrowaveOven_MO_D_extent,\
  &pG_MicrowaveOven_MO_IL_extent,\
  &pG_MicrowaveOven_MO_MT_extent,\
  &pG_MicrowaveOven_MO_O_extent,\
  &pG_MicrowaveOven_MO_TRN_extent,\
  &pG_MicrowaveOven_MO_TS_extent

#define MicrowaveOven_class_dispatchers\
  MicrowaveOven_MO_B_Dispatch,\
  MicrowaveOven_MO_D_Dispatch,\
  MicrowaveOven_MO_IL_Dispatch,\
  MicrowaveOven_MO_MT_Dispatch,\
  MicrowaveOven_MO_O_Dispatch,\
  MicrowaveOven_MO_TRN_Dispatch,\
  MicrowaveOven_MO_TS_Dispatch

/* Provide definitions of the shapes of the class structures.  */

typedef struct MicrowaveOven_MO_B MicrowaveOven_MO_B;
typedef struct MicrowaveOven_MO_D MicrowaveOven_MO_D;
typedef struct MicrowaveOven_MO_IL MicrowaveOven_MO_IL;
typedef struct MicrowaveOven_MO_MT MicrowaveOven_MO_MT;
typedef struct MicrowaveOven_MO_O MicrowaveOven_MO_O;
typedef struct MicrowaveOven_MO_TRN MicrowaveOven_MO_TRN;
typedef struct MicrowaveOven_MO_TS MicrowaveOven_MO_TS;

/* union of class declarations so we may derive largest class size */
#define MicrowaveOven_CLASS_U \
  char MicrowaveOven_dummy;\

/*
 * Internal enumerated and structured data types for component:  MicrowaveOven
 */

/*
 * Enumerated Data Type:  tube_wattage
 */
#define MicrowaveOven_tube_wattage__UNINITIALIZED__e -1
#define MicrowaveOven_tube_wattage_high_e 0
#define MicrowaveOven_tube_wattage_low_e 1
#define MicrowaveOven_tube_wattage_med_high_e 2
#define MicrowaveOven_tube_wattage_med_low_e 3
#define MicrowaveOven_tube_wattage_medium_e 4


/*
 * UML Domain Functions (Synchronous Services)
 */
extern void MicrowaveOven_StartCooking( void );
extern void MicrowaveOven_CancelCooking( void );
extern void MicrowaveOven_SpecifyCookingPeriod( i_t );
extern void MicrowaveOven_IncreasePower( void );
extern void MicrowaveOven_DecreasePower( void );
extern void MicrowaveOven_OpenDoor( void );
extern void MicrowaveOven_CloseDoor( void );
extern void MicrowaveOven_TestSequence1( void );
extern void MicrowaveOven_DefineOven( void );



#include "TIM_bridge.h"
#include "MicrowaveOven_ARCH_bridge.h"
#include "MicrowaveOven.h"
#include "MicrowaveOven_MO_B_class.h"
#include "MicrowaveOven_MO_D_class.h"
#include "MicrowaveOven_MO_IL_class.h"
#include "MicrowaveOven_MO_MT_class.h"
#include "MicrowaveOven_MO_O_class.h"
#include "MicrowaveOven_MO_TRN_class.h"
#include "MicrowaveOven_MO_TS_class.h"


/*
 * roll-up of all events (with their parameters) for domain MicrowaveOven
 */
typedef union {
  MicrowaveOven_MO_B_Events_u namespace_dummy1;
  MicrowaveOven_MO_D_Events_u namespace_dummy2;
  MicrowaveOven_MO_IL_Events_u namespace_dummy3;
  MicrowaveOven_MO_MT_Events_u namespace_dummy4;
  MicrowaveOven_MO_O_Events_u namespace_dummy5;
  MicrowaveOven_MO_TRN_Events_u namespace_dummy6;
  MicrowaveOven_MO_TS_Events_u namespace_dummy7;
} MicrowaveOven_DomainEvents_u;
#ifdef	__cplusplus
}
#endif

#endif  /* MICROWAVEOVEN_CLASSES_H */

