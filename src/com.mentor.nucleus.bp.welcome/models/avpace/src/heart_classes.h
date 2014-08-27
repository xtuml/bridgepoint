/*----------------------------------------------------------------------------
 * File:  heart_classes.h
 *
 * This file defines the object type identification numbers for all classes
 * in the component:  heart
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef HEART_CLASSES_H
#define HEART_CLASSES_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Initialization services for component:  heart
 */
extern Escher_Extent_t * const heart_class_info[];
extern const EventTaker_t heart_EventDispatcher[];
void heart_execute_initialization( void );

#define heart_STATE_MODELS 3
/* Define constants to map to class numbers.  */
#define heart_HEART_CLASS_NUMBER 0
#define heart_SINUS_NODE_CLASS_NUMBER 1
#define heart_HEART_CLASS_NUMBER_CB 2
#define heart_MAX_CLASS_NUMBERS 3

/* Provide a map of classes to task numbers.  */
#define heart_TASK_NUMBERS  1, 1, 1

#define heart_class_dispatchers\
  heart_HEART_Dispatch,\
  heart_SINUS_NODE_Dispatch,\
  heart_HEART_CBDispatch

/* Provide definitions of the shapes of the class structures.  */

typedef struct heart_HEART heart_HEART;
typedef struct heart_SINUS_NODE heart_SINUS_NODE;
typedef struct heart_HEART_CB heart_HEART_CB;

/* union of class declarations so we may derive largest class size */
#define heart_CLASS_U \
  char heart_dummy;\

/*
 * UML Domain Functions (Synchronous Services)
 */
void heart_init( void );


#include "heart_LOG_bridge.h"
#include "TIM_bridge.h"
#include "heart.h"
#include "heart_HEART_class.h"
#include "heart_SINUS_NODE_class.h"
/*
 * roll-up of all events (with their parameters) for component heart
 */
typedef union {
  heart_HEART_Events_u heart_HEART_Events_u_namespace;
  heart_SINUS_NODE_Events_u heart_SINUS_NODE_Events_u_namespace;
  heart_HEART_CB_Events_u heart_HEART_CB_Events_u_namespace;
} heart_DomainEvents_u;

#ifdef	__cplusplus
}
#endif
#endif  /* HEART_CLASSES_H */

