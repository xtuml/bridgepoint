/*----------------------------------------------------------------------------
 * File:  MicrowaveOven_sys_types.h
 *
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *
 *
 * System Name:  
 * System ID:    1
 * Model Compiler Product Information:
 * Product:  
 * Version:  
 * S/N:      
 * System default/colored values:
 * MaxStringLen:  32
 * MaxObjExtent:  0
 * MaxRelExtent:  0
 * MaxSelectExtent:  0
 * MaxSelfEvents:  0
 * MaxNonSelfEvents:  0
 * MaxTimers:  0
 * MaxInterleavedBridges:  0
 * MaxInterleavedBridgeDataSize:  0
 * CollectionsFlavor:  0
 * ForcePriorityEvents:  FALSE
 * PEIClassCount:  0
 * PersistentClassCount:  0
 * PersistInstanceCacheDepth:  128
 * PersistLinkCacheDepth:  128
 *
 * Component Name:  MicrowaveOven
 * MaxObjExtent:  70
 * MaxRelExtent:  0
 * MaxSelectExtent:  0
 * MaxSelfEvents:  9
 * MaxNonSelfEvents:  4
 * MaxPriorityEvents:  0
 * MaxTimers:  20
 * InterleavedBridges:  0
 * PEIClassCount:  0
 * PersistentClassCount:  0
 * InterleavedDataSize:  8
 * CollectionsFlavor:  0
 *--------------------------------------------------------------------------*/

#ifndef MICROWAVEOVEN_SYS_TYPES_H
#define MICROWAVEOVEN_SYS_TYPES_H
#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Shore up C by declaring our own version of bool.
 */
#ifndef	__cplusplus
typedef unsigned char bool;

#ifdef false
#undef false
#endif

#ifdef true
#undef true
#endif

#define false ( (bool) 0 )
#define true  ( (bool) (!false) )
#endif

#ifdef FALSE
#undef FALSE
#endif

#ifdef TRUE
#undef TRUE
#endif

#define FALSE ( (bool) 0 )
#define TRUE  ( (bool) (!FALSE) )


#define ESCHER_SYS_MAX_STRING_LEN 32
#define ESCHER_PERSIST_INST_CACHE_DEPTH 128
#define ESCHER_PERSIST_LINK_CACHE_DEPTH 128
#define ESCHER_SYS_MAX_ASSOCIATION_EXTENT 0
#define ESCHER_SYS_MAX_TRANSIENT_EXTENT 0
#define SYS_MAX_CONTAINERS ( ESCHER_SYS_MAX_ASSOCIATION_EXTENT + ESCHER_SYS_MAX_TRANSIENT_EXTENT )
#define ESCHER_SYS_MAX_SELF_EVENTS 10
#define ESCHER_SYS_MAX_NONSELF_EVENTS 5
#define ESCHER_SYS_MAX_XTUML_EVENTS ( ESCHER_SYS_MAX_SELF_EVENTS + ESCHER_SYS_MAX_NONSELF_EVENTS )
#define ESCHER_SYS_MAX_XTUML_TIMERS 21
#define ESCHER_SYS_MAX_INTERLEAVED_BRIDGES 0
#define ESCHER_SYS_MAX_INTERLEAVED_BRIDGE_DATA 8


/*
 * Core types with byte widths defined for MISRA-C compliance.
 */
typedef          char    c_t;
typedef unsigned char   uc_t;
typedef          int     i_t;
typedef unsigned int    ui_t;
typedef          long    l_t;
typedef unsigned long   ul_t;
typedef   signed char   s1_t;
typedef unsigned char   u1_t;
typedef   signed short  s2_t;
typedef unsigned short  u2_t;
typedef   signed long   s4_t;
typedef unsigned long   u4_t;
typedef          float  r4_t;
typedef          double r8_t;

/*
 * These are some of the fundamental types used universally.
 */
typedef u1_t Escher_DomainNumber_t;
typedef u2_t Escher_ClassNumber_t;
typedef u2_t Escher_ClassSize_t;
typedef u2_t Escher_InstanceIndex_t;
typedef u1_t Escher_StateNumber_t;
typedef u1_t Escher_EventNumber_t;
typedef u1_t Escher_EventFlags_t;
typedef u1_t Escher_EventPriority_t;
typedef u1_t Escher_SEMcell_t;
typedef struct {
  Escher_DomainNumber_t domainnum;
  Escher_ClassNumber_t classnum;
  Escher_InstanceIndex_t index;
} InstanceIdentifier_t;

typedef struct {
  Escher_StateNumber_t current_state;
} Escher_InstanceBase_t;
typedef Escher_InstanceBase_t * Escher_iHandle_t;
typedef Escher_iHandle_t Escher_UniqueID_t;

/* Return code type for dispatch of a polymorphic event (see sys_events.h).  */
typedef u1_t Escher_PolyEventRC_t;

/* Interleaved bridge node base type.  */
typedef struct Escher_ilb_s Escher_ilb_t;
typedef void ( * Escher_ilb_fp_t )( Escher_ilb_t * );
struct Escher_ilb_s { Escher_ilb_fp_t f; };

/*
 * Time and date core types.
 */
typedef l_t  Escher_Date_t;
typedef u4_t Escher_TimeStamp_t;
typedef u4_t Escher_uSec_t;

#define DomainMultiplier 1

/*
 * Allow for compilerisms (such as 8051 Tasking) to use memory model
 * hints to the compiler.
 */
#ifndef _reentrant
#define _reentrant
#endif

/*
 * Note we include stdio.h for printf.  Otherwise, it is not needed.
 */
#include <stdio.h>
#include "sys_xtuml.h"
#include "sys_user_co.h"
/*
 * Data structures and methods associated with the event base class of this
 * model compiler.
 */
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

/* Opaque type definition of a translated state action member function.  */
typedef _reentrant void (*StateAction_t)( Escher_iHandle_t, const Escher_xtUMLEvent_t * const );

/* Opaque type definition of class event dispatcher member function.  */
typedef _reentrant void (*EventTaker_t)( Escher_xtUMLEvent_t * );

/* Opaque type definition of domain level event dispatcher member function.  */
typedef _reentrant void (*DomainDispatcher_t)( Escher_xtUMLEvent_t * );



extern Escher_xtUMLEvent_t * Escher_AllocatextUMLEvent( void );
extern Escher_xtUMLEvent_t * Escher_NewxtUMLEvent( const void * const, const Escher_xtUMLEventConstant_t * const );
extern Escher_xtUMLEvent_t * Escher_ModifyxtUMLEvent( Escher_xtUMLEvent_t *, const Escher_xtUMLEventConstant_t * const );
extern void Escher_DeletextUMLEvent( Escher_xtUMLEvent_t * );
extern void InitializeOoaEventPool( void );
extern void Escher_xtUML_run( void );
extern void * Escher_GetSelf( void );
extern void Escher_SendEvent( Escher_xtUMLEvent_t * );
extern void Escher_SendSelfEvent( Escher_xtUMLEvent_t * );

/*
 * Delcarations for multi-tasking/threading services.
 */


#define NUM_OF_XTUML_CLASS_THREADS 1
#define NUM_OF_TOTAL_THREADS NUM_OF_XTUML_CLASS_THREADS
#define SEMAPHORE_FLAVOR_IQUEUE    0
#define SEMAPHORE_FLAVOR_SQUEUE    1
#define SEMAPHORE_FLAVOR_FREELIST  2
#define SEMAPHORE_FLAVOR_NONBUSY   3
#define SEMAPHORE_FLAVOR_INSTANCE  4
#define SEMAPHORE_FLAVOR_TIMER     5
#define SEMAPHORE_FLAVOR_ILB       6
#define SEMAPHORE_FLAVOR_MAX       7

extern void Escher_InitializeThreading( void );
extern void Escher_thread_create( void *(f)(void *), const u1_t );
extern void Escher_mutex_lock( const u1_t );
extern void Escher_mutex_unlock( const u1_t );
extern void Escher_nonbusy_wait( const u1_t );
extern void Escher_nonbusy_wake( const u1_t );
extern void Escher_thread_shutdown( void );

#ifdef NOMUTEX_DEBUG
#define pthread_mutex_lock( X ) 0
#define pthread_mutex_unlock( X ) 0
#endif
#include "TIM_bridge.h"




#define SYSTEM_DOMAIN_COUNT 1
/* xtUML domain identification numbers */
#define MicrowaveOven_DOMAIN_ID 0
#include "MicrowaveOven_classes.h"

/*----------------------------------------------------------------------------
 *
 * Run time instrumentation and tracing declarations are defined here.
 *
 * Note:
 *   Multi-line macros use the do {...} while (0) construct recommended
 *   by cert.org (PRE10-C, PRE10-CPP).
 *
 *   Users may copy this file to the /gen folder and modify these macros
 *   as desired to change the way tracing works.
 *
 *--------------------------------------------------------------------------*/

#define XTUML_TRACE_FLUSH( i ) fflush( i )

/*
 * State transition start tracing:
 */
/* To suppress source identification in tracing, uncomment the following macro */
/* #define XTUML_SOURCE_PROLOGUE */

#ifndef XTUML_SOURCE_PROLOGUE
#define XTUML_SOURCE_PROLOGUE printf( "%s #%5u: ", __FILE__, __LINE__ ); XTUML_TRACE_FLUSH( 0 )
#endif

/* To suppress state transition start tracing, uncomment the following macro */
/* #define STATE_TXN_START_TRACE( obj_kl, state_num, state_name ) */

#ifndef STATE_TXN_START_TRACE
#define STATE_TXN_START_TRACE( obj_kl, state_num, state_name ) do {   XTUML_SOURCE_PROLOGUE;   printf( "Transition started:  %s State [%u] %s\n", obj_kl, state_num, state_name ); } while (0)
#endif

/*
 * State transition complete tracing:
 */
/* To suppress state transition complete tracing, uncomment the following macro */
/* #define STATE_TXN_END_TRACE( obj_kl, state_num, state_name ) */

#ifndef STATE_TXN_END_TRACE
#define STATE_TXN_END_TRACE( obj_kl, state_num, state_name ) do {   XTUML_SOURCE_PROLOGUE;   printf( "Transition complete:  %s State [%u] %s\n", obj_kl, state_num, state_name );   XTUML_TRACE_FLUSH( 0 ); } while (0)
#endif

/*
 * Event ignored tracing:
 */
/* To unsuppress event ignored tracing, comment out the following macro.  */
#define STATE_TXN_IG_TRACE( obj_kl, state_num )

#ifndef STATE_TXN_IG_TRACE
#define STATE_TXN_IG_TRACE( obj_kl, state_num ) do {   XTUML_SOURCE_PROLOGUE;   printf( "Event ignored:  %s current_state = %u\n", obj_kl, state_num );   XTUML_TRACE_FLUSH( 0 ); } while (0)
#endif

/*
 * Event can't happen tracing:
 */
/* To suppress can't happen tracing, uncomment the following macro */
/* #define STATE_TXN_CH_TRACE( obj_kl, state_num ) */

#ifndef STATE_TXN_CH_TRACE
#define STATE_TXN_CH_TRACE( obj_kl, state_num ) do {   XTUML_SOURCE_PROLOGUE;   printf( "Event cannot happen:  %s current_state = %u\n", obj_kl, state_num );   XTUML_TRACE_FLUSH( 0 ); } while (0)
#endif

/*
 * Transformer invocation start tracing:
 */
/* To suppress transformer start tracing, uncomment the following macro */
/* #define XTUML_TRANSFORMER_START_TRACE( obj_kl, tfr_name ) */

#ifndef XTUML_TRANSFORMER_START_TRACE
#define XTUML_TRANSFORMER_START_TRACE( obj_kl, tfr_name ) do {   XTUML_SOURCE_PROLOGUE;   printf( "Invocation started: '%s' Transformer '%s'\n", obj_kl, tfr_name );   XTUML_TRACE_FLUSH( 0 ); } while (0)
#endif

/*
 * Transformer invocation complete tracing:
 */
/* To suppress transformer end tracing, uncomment the following macro */
/* #define XTUML_TRANSFORMER_END_TRACE( obj_kl, tfr_name ) */

#ifndef XTUML_TRANSFORMER_END_TRACE
#define XTUML_TRANSFORMER_END_TRACE( obj_kl, tfr_name ) do {   XTUML_SOURCE_PROLOGUE;   printf( "Invocation complete: '%s' Transformer '%s'\n", obj_kl, tfr_name );   XTUML_TRACE_FLUSH( 0 ); } while (0)
#endif

/*
 * Object Action Language (OAL) statement level tracing:
 */

/* To suppress statement source identification, uncomment the following macro */
/* #define XTUML_OAL_STMT_TRACE( blck_level, stmt_action ) */

#ifndef XTUML_OAL_STMT_TRACE
#define XTUML_OAL_STMT_TRACE( blck_level, stmt_action ) do {   XTUML_SOURCE_PROLOGUE;   { /* indenting */ s1_t i; for ( i = 0; i < blck_level; i++ ) printf( "  " ); }   printf( "%s\n", stmt_action );   XTUML_TRACE_FLUSH( 0 ); } while (0)
#endif

/* To suppress empty handle detection, modify the following macro.  */

#ifndef XTUML_EMPTY_HANDLE_TRACE
#define XTUML_EMPTY_HANDLE_TRACE( object_keyletters, s ) do { UserEmptyHandleDetectedCallout( object_keyletters, s ); } while (0)
#endif

/*
 * Declare state information structure.
 */
typedef struct Escher_StateInfo_s Escher_StateInfo_s;
struct Escher_StateInfo_s {
  Escher_StateNumber_t state_number;  /* 'real' state number */
  c_t * state_name;
};

#ifdef	__cplusplus
}
#endif
#endif  /* MICROWAVEOVEN_SYS_TYPES_H */
