/*----------------------------------------------------------------------------
 * File:  sys_events.h
 *
 * Description:
 *  Data stuctures an methods associated with the even base class of this
 * model compiler.
 *
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/

#ifndef ESCHER_SYS_EVENTS_H
#define ESCHER_SYS_EVENTS_H
#ifdef  __cplusplus
extern "C" {
#endif

/* Escher_EventFlags_t bit masks */
#define ESCHER_IS_INSTANCE_EVENT     0x01
#define ESCHER_IS_CREATION_EVENT     0x02
#define ESCHER_IS_ASSIGNER_EVENT     0x04
#define ESCHER_IS_DELAYED_EVENT      0x08
#define ESCHER_NO_FSM_RELEASE_EVENT  0x10
#define ESCHER_IS_POLYMORPHIC_EVENT  0x20
#define ESCHER_IS_TRUE_EVENT         0x40
#define ESCHER_IS_POLY_IN_FLIGHT     0x80

#define UNINITIALIZED_STATE 0
#define EVENT_CANT_HAPPEN ((Escher_StateNumber_t) -1)
#define EVENT_IS_IGNORED  ((Escher_StateNumber_t) -2)

/* This represents the constant part of the event structure.  */
typedef struct {
  Escher_DomainNumber_t destination_domain_number;
  Escher_ClassNumber_t destination_object_number;
  Escher_EventNumber_t event_number;
  Escher_EventFlags_t event_flags;
} Escher_xtUMLEventConstant_t;

/* Structure:  Escher_xtUMLEvent_t
 * Base class of application analysis xtUML events.  */
#define EVENT_BASE_ATTRIBUTE_LIST \
  struct Escher_xtUMLevent_s * next;\
  Escher_iHandle_t object_instance;\
  Escher_DomainNumber_t destination_domain_number;\
  Escher_ClassNumber_t destination_object_number;\
  Escher_EventNumber_t event_number;\
  Escher_EventFlags_t event_flags;\

struct Escher_xtUMLevent_s {
  EVENT_BASE_ATTRIBUTE_LIST
};
typedef struct Escher_xtUMLevent_s Escher_xtUMLEvent_t;

typedef unsigned long ETimer_time_t;

typedef void Escher_Timer_t;

/*
 * Event Macros
 */

#define GetOoaEventFlags(X) (((X))->event_flags)
#define SetOoaEventFlags(X, Y) (((X))->event_flags = Y)
#define ClearOoaEventFlags(X) (((X))->event_flags = 0)

#define GetIsInstanceEvent(X) (GetOoaEventFlags(X) & ESCHER_IS_INSTANCE_EVENT)
#define SetIsInstanceEvent(X) (GetOoaEventFlags(X) |= ESCHER_IS_INSTANCE_EVENT)

#define GetIsCreationEvent(X) (GetOoaEventFlags(X) & ESCHER_IS_CREATION_EVENT)
#define SetIsCreationEvent(X) (GetOoaEventFlags(X) |= ESCHER_IS_CREATION_EVENT)

#define GetIsAssignerEvent(X) (GetOoaEventFlags(X) & ESCHER_IS_ASSIGNER_EVENT)
#define SetIsAssignerEvent(X) (GetOoaEventFlags(X) |= ESCHER_IS_ASSIGNER_EVENT)

#define GetIsDelayedEvent(X)  (GetOoaEventFlags(X) & ESCHER_IS_DELAYED_EVENT)
#define SetIsDelayedEvent(X)  (GetOoaEventFlags(X) |= ESCHER_IS_DELAYED_EVENT)

#define GetIsPolymorphicEvent(X) (GetOoaEventFlags(X) & ESCHER_IS_POLYMORPHIC_EVENT)
#define SetIsPolymorphicEvent(X) (GetOoaEventFlags(X) |= ESCHER_IS_POLYMORPHIC_EVENT)
#define ClearIsPolymorphicEvent(X)  (GetOoaEventFlags(X) &= ~(Escher_EventFlags_t)ESCHER_IS_POLYMORPHIC_EVENT)

#define GetFsmReleasesEvent(X) (GetOoaEventFlags(X) & ESCHER_NO_FSM_RELEASE_EVENT)
#define SetFsmReleasesEvent(X) (GetOoaEventFlags(X) |= ESCHER_NO_FSM_RELEASE_EVENT)

#define GetOoaEventNumber(X) (((X))->event_number)
#define SetOoaEventNumber(X, Y) (((X))->event_number = (Y))

#define GetEventTargetInstance(X) (((X))->object_instance)
#define SetEventTargetInstance(X, Y) (((X))->object_instance = (Escher_iHandle_t)(Y))

#define GetEventSendingInstance(X) (((X))->sending_instance)
#define SetEventSendingInstance(X, Y) (((X))->sending_instance = (Escher_iHandle_t)(Y))

#define GetEventDestDomainNumber(X) (((X))->destination_domain_number)
#define SetEventDestDomainNumber(X, Y) (((X))->destination_domain_number = (Y))

#define GetEventDestObjectNumber(X) (((X))->destination_object_number)
#define SetEventDestObjectNumber(X, Y) (((X))->destination_object_number = (Y))

#define GetEventSrcObjectNumber(X) (((X))->source_object_number)
#define SetEventSrcObjectNumber(X, Y) (((X))->source_object_number = (Y))


/*
 * Return codes for dispatching of a polymorphic event.
 *
 * ESCHER_POLY_FORWARDED:  Event delivered to subtype.
 * ESCHER_POLY_IGNORED:  Subtype can not consume or forward event in analysis.
 * ESCHER_POLY_NO_SUBTYPE:  No subtype currently related to supertype.
 *
 * ESCHER_POLY_INTERNAL_ERROR:  Internal logic error.
 * ESCHER_POLY_BAD_OBJ_ID:  Error -> Unknown subtype object type identifier.
 * ESCHER_POLY_BAD_EVT_ID:  Error -> Unknown event number for related subtype.
 */
/* 'Normal' conditions that may be experienced at run time. */
#define ESCHER_POLY_FORWARDED        ((Escher_PolyEventRC_t) 0)
#define ESCHER_POLY_IGNORED          ((Escher_PolyEventRC_t) 1)
#define ESCHER_POLY_NO_SUBTYPE       ((Escher_PolyEventRC_t) 2)

/*
 * Disturbing error contitions.  These may occur when
 * (a) A polymorphic event has been added to a supertype, but the associated
 *     subtype state chart(s) were not updated to pick up the new event.
 * (b) A model compiler error has occurred (should never happen).
 */
#define ESCHER_POLY_INTERNAL_ERROR   ((Escher_PolyEventRC_t) -1)
#define ESCHER_POLY_BAD_OBJ_ID       ((Escher_PolyEventRC_t) -2)
#define ESCHER_POLY_BAD_EVT_ID       ((Escher_PolyEventRC_t) -3)

extern Escher_xtUMLEvent_t * Escher_AllocatextUMLEvent( void );
extern Escher_xtUMLEvent_t * Escher_NewxtUMLEvent( const void * const, const Escher_xtUMLEventConstant_t * const );
extern Escher_xtUMLEvent_t * Escher_ModifyxtUMLEvent( Escher_xtUMLEvent_t *, const Escher_xtUMLEventConstant_t * const );
extern void Escher_DeletextUMLEvent( Escher_xtUMLEvent_t * );
extern void InitializeOoaEventPool( void );
extern void Escher_xtUML_run( void );
extern void * Escher_GetSelf( void );
extern void Escher_SendEvent( Escher_xtUMLEvent_t * );
extern void Escher_SendSelfEvent( Escher_xtUMLEvent_t * );

/* Opaque type definition of a translated state action member function.  */
typedef _reentrant void (*StateAction_t)( Escher_iHandle_t, const Escher_xtUMLEvent_t * const );

/* Opaque type definition of class event dispatcher member function.  */
typedef _reentrant void (*EventTaker_t)( Escher_xtUMLEvent_t * );

/* Opaque type definition of domain level event dispatcher member function.  */
typedef _reentrant void (*DomainDispatcher_t)( Escher_xtUMLEvent_t * );

#include "TIM_bridge.h"

#ifdef    __cplusplus
}
#endif
#endif  /* ESCHER_SYS_EVENTS_H */
