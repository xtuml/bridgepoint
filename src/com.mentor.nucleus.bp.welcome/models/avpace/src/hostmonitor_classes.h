/*----------------------------------------------------------------------------
 * File:  hostmonitor_classes.h
 *
 * This file defines the object type identification numbers for all classes
 * in the component:  hostmonitor
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef HOSTMONITOR_CLASSES_H
#define HOSTMONITOR_CLASSES_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Initialization services for component:  hostmonitor
 */
extern Escher_Extent_t * const hostmonitor_class_info[];
extern const EventTaker_t hostmonitor_EventDispatcher[];
void hostmonitor_execute_initialization( void );

#define hostmonitor_STATE_MODELS 2
/* Define constants to map to class numbers.  */
#define hostmonitor_HM_CLASS_NUMBER 0
#define hostmonitor_HM_CLASS_NUMBER_CB 1
#define hostmonitor_TM_CLASS_NUMBER 2
#define hostmonitor_RM_CLASS_NUMBER 3
#define hostmonitor_MAX_CLASS_NUMBERS 4

/* Provide a map of classes to task numbers.  */
#define hostmonitor_TASK_NUMBERS  0, 0

#define hostmonitor_class_dispatchers\
  hostmonitor_HM_Dispatch,\
  hostmonitor_HM_CBDispatch

/* Provide definitions of the shapes of the class structures.  */

typedef struct hostmonitor_HM hostmonitor_HM;
typedef struct hostmonitor_HM_CB hostmonitor_HM_CB;
typedef struct hostmonitor_TM hostmonitor_TM;
typedef struct hostmonitor_RM hostmonitor_RM;

/* union of class declarations so we may derive largest class size */
#define hostmonitor_CLASS_U \
  char hostmonitor_dummy;\


#include "hostmonitor.h"
#include "hostmonitor_HM_class.h"
#include "hostmonitor_TM_class.h"
#include "hostmonitor_RM_class.h"
/*
 * roll-up of all events (with their parameters) for component hostmonitor
 */
typedef union {
  hostmonitor_HM_Events_u hostmonitor_HM_Events_u_namespace;
  hostmonitor_HM_CB_Events_u hostmonitor_HM_CB_Events_u_namespace;
} hostmonitor_DomainEvents_u;

#ifdef	__cplusplus
}
#endif
#endif  /* HOSTMONITOR_CLASSES_H */

