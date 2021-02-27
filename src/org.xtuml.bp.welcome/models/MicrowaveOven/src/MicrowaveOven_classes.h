/*----------------------------------------------------------------------------
 * File:  MicrowaveOven_classes.h
 *
 * This file defines the object type identification numbers for all classes
 * in the component:  MicrowaveOven
 *
 * your copyright statement can go here (from te_copyright.body)
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
void MicrowaveOven_execute_initialization( void );

#define MicrowaveOven_STATE_MODELS 7
/* Define constants to map to class numbers.  */
#define MicrowaveOven_MO_TS_CLASS_NUMBER 0
#define MicrowaveOven_MO_O_CLASS_NUMBER 1
#define MicrowaveOven_MO_MT_CLASS_NUMBER 2
#define MicrowaveOven_MO_IL_CLASS_NUMBER 3
#define MicrowaveOven_MO_B_CLASS_NUMBER 4
#define MicrowaveOven_MO_D_CLASS_NUMBER 5
#define MicrowaveOven_MO_TRN_CLASS_NUMBER 6
#define MicrowaveOven_MAX_CLASS_NUMBERS 7

/* Provide a map of classes to task numbers.  */
#define MicrowaveOven_TASK_NUMBERS  0, 0, 0, 0, 0, 0, 0

#define MicrowaveOven_class_dispatchers\
  MicrowaveOven_MO_TS_Dispatch,\
  MicrowaveOven_MO_O_Dispatch,\
  MicrowaveOven_MO_MT_Dispatch,\
  MicrowaveOven_MO_IL_Dispatch,\
  MicrowaveOven_MO_B_Dispatch,\
  MicrowaveOven_MO_D_Dispatch,\
  MicrowaveOven_MO_TRN_Dispatch

/* Provide definitions of the shapes of the class structures.  */

typedef struct MicrowaveOven_MO_TS MicrowaveOven_MO_TS;
typedef struct MicrowaveOven_MO_O MicrowaveOven_MO_O;
typedef struct MicrowaveOven_MO_MT MicrowaveOven_MO_MT;
typedef struct MicrowaveOven_MO_IL MicrowaveOven_MO_IL;
typedef struct MicrowaveOven_MO_B MicrowaveOven_MO_B;
typedef struct MicrowaveOven_MO_D MicrowaveOven_MO_D;
typedef struct MicrowaveOven_MO_TRN MicrowaveOven_MO_TRN;

/* union of class declarations so we may derive largest class size */
#define MicrowaveOven_CLASS_U \
  char MicrowaveOven_dummy;\

/*
 * Internal enumerated and structured data types for component:  MicrowaveOven
 */
/*
 * Enumerated Data Type:  tube_wattage
 */
typedef enum {
 MicrowaveOven_tube_wattage__UNINITIALIZED__e = -1,
 MicrowaveOven_tube_wattage_high_e = 0,
 MicrowaveOven_tube_wattage_low_e = 1,
 MicrowaveOven_tube_wattage_med_high_e = 2,
 MicrowaveOven_tube_wattage_med_low_e = 3,
 MicrowaveOven_tube_wattage_medium_e = 4
} MicrowaveOven_tube_wattage_t;

/*
 * UML Domain Functions (Synchronous Services)
 */
void MicrowaveOven_CancelCooking( void );
void MicrowaveOven_CloseDoor( void );
void MicrowaveOven_DecreasePower( void );
void MicrowaveOven_DefineOven( void );
void MicrowaveOven_IncreasePower( void );
void MicrowaveOven_OpenDoor( void );
void MicrowaveOven_SpecifyCookingPeriod( const i_t );
void MicrowaveOven_StartCooking( void );
void MicrowaveOven_TestSequence1( void );

#include "MicrowaveOven_ARCH_bridge.h"
#include "TIM_bridge.h"
#include "MicrowaveOven.h"
#include "MicrowaveOven_MO_TS_class.h"
#include "MicrowaveOven_MO_O_class.h"
#include "MicrowaveOven_MO_MT_class.h"
#include "MicrowaveOven_MO_IL_class.h"
#include "MicrowaveOven_MO_B_class.h"
#include "MicrowaveOven_MO_D_class.h"
#include "MicrowaveOven_MO_TRN_class.h"
/*
 * roll-up of all events (with their parameters) for component MicrowaveOven
 */
typedef union {
  MicrowaveOven_MO_TS_Events_u MicrowaveOven_MO_TS_Events_u_namespace;
  MicrowaveOven_MO_O_Events_u MicrowaveOven_MO_O_Events_u_namespace;
  MicrowaveOven_MO_MT_Events_u MicrowaveOven_MO_MT_Events_u_namespace;
  MicrowaveOven_MO_IL_Events_u MicrowaveOven_MO_IL_Events_u_namespace;
  MicrowaveOven_MO_B_Events_u MicrowaveOven_MO_B_Events_u_namespace;
  MicrowaveOven_MO_D_Events_u MicrowaveOven_MO_D_Events_u_namespace;
  MicrowaveOven_MO_TRN_Events_u MicrowaveOven_MO_TRN_Events_u_namespace;
} MicrowaveOven_DomainEvents_u;

#ifdef	__cplusplus
}
#endif
#endif  /* MICROWAVEOVEN_CLASSES_H */
