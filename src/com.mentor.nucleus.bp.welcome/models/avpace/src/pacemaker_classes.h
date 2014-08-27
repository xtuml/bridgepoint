/*----------------------------------------------------------------------------
 * File:  pacemaker_classes.h
 *
 * This file defines the object type identification numbers for all classes
 * in the component:  pacemaker
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef PACEMAKER_CLASSES_H
#define PACEMAKER_CLASSES_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Initialization services for component:  pacemaker
 */
extern Escher_Extent_t * const pacemaker_class_info[];
extern const EventTaker_t pacemaker_EventDispatcher[];
void pacemaker_execute_initialization( void );

#define pacemaker_STATE_MODELS 2
/* Define constants to map to class numbers.  */
#define pacemaker_PACER_CLASS_NUMBER 0
#define pacemaker_PACER_CLASS_NUMBER_CB 1
#define pacemaker_MAX_CLASS_NUMBERS 2

/* Provide a map of classes to task numbers.  */
#define pacemaker_TASK_NUMBERS  0, 0

#define pacemaker_class_dispatchers\
  pacemaker_PACER_Dispatch,\
  pacemaker_PACER_CBDispatch

/* Provide definitions of the shapes of the class structures.  */

typedef struct pacemaker_PACER pacemaker_PACER;
typedef struct pacemaker_PACER_CB pacemaker_PACER_CB;

/* union of class declarations so we may derive largest class size */
#define pacemaker_CLASS_U \
  char pacemaker_dummy;\


#include "TIM_bridge.h"
#include "pacemaker_LOG_bridge.h"
#include "pacemaker.h"
#include "pacemaker_PACER_class.h"
/*
 * roll-up of all events (with their parameters) for component pacemaker
 */
typedef union {
  pacemaker_PACER_Events_u pacemaker_PACER_Events_u_namespace;
  pacemaker_PACER_CB_Events_u pacemaker_PACER_CB_Events_u_namespace;
} pacemaker_DomainEvents_u;

#ifdef	__cplusplus
}
#endif
#endif  /* PACEMAKER_CLASSES_H */

