/*----------------------------------------------------------------------------
 * File:  HeartRateMonitor_classes.h
 *
 * This file defines the object type identification numbers for all classes
 * in the component:  HeartRateMonitor
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef HEARTRATEMONITOR_CLASSES_H
#define HEARTRATEMONITOR_CLASSES_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Initialization services for component:  HeartRateMonitor
 */
extern Escher_Extent_t * const HeartRateMonitor_class_info[];
extern const EventTaker_t HeartRateMonitor_EventDispatcher[];
extern void HeartRateMonitor_execute_initialization( void );

#define HeartRateMonitor_STATE_MODELS 2


/* Define constants to map to class numbers.  */
#define HeartRateMonitor_HeartRateMonitor_CLASS_NUMBER 0
#define HeartRateMonitor_HeartRateMonitor_CLASS_NUMBER_CB 1
#define HeartRateMonitor_MAX_CLASS_NUMBERS 2

/* Provide a map of classes to task numbers.  */
#define HeartRateMonitor_TASK_NUMBERS  0, 0

#define HeartRateMonitor_class_dispatchers\
  0,\
  HeartRateMonitor_HeartRateMonitor_CBDispatch

/* Provide definitions of the shapes of the class structures.  */

typedef struct HeartRateMonitor_HeartRateMonitor HeartRateMonitor_HeartRateMonitor;
typedef struct HeartRateMonitor_HeartRateMonitor_CB HeartRateMonitor_HeartRateMonitor_CB;

/* union of class declarations so we may derive largest class size */
#define HeartRateMonitor_CLASS_U \
  char HeartRateMonitor_dummy;\



#include "LOG_bridge.h"
#include "TIM_bridge.h"
#include "HeartRateMonitor.h"
#include "HeartRateMonitor_HeartRateMonitor_class.h"


/*
 * roll-up of all events (with their parameters) for domain HeartRateMonitor
 */
typedef union {
  HeartRateMonitor_HeartRateMonitor_CB_Events_u HeartRateMonitor_HeartRateMonitor_CB_Events_u_namespace;
} HeartRateMonitor_DomainEvents_u;
#ifdef	__cplusplus
}
#endif

#endif  /* HEARTRATEMONITOR_CLASSES_H */

