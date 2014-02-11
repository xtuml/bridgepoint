/*----------------------------------------------------------------------------
 * File:  sys_types.h
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
 *--------------------------------------------------------------------------*/

#ifndef SYS_TYPES_H
#define SYS_TYPES_H
#ifdef  __cplusplus
extern "C" {
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
#define ESCHER_SYS_MAX_OBJECT_EXTENT 1
#define ESCHER_SYS_MAX_ASSOCIATION_EXTENT 0
#define ESCHER_SYS_MAX_TRANSIENT_EXTENT 0
#define SYS_MAX_CONTAINERS ( ESCHER_SYS_MAX_ASSOCIATION_EXTENT + ESCHER_SYS_MAX_TRANSIENT_EXTENT )
#define ESCHER_SYS_MAX_SELF_EVENTS 0
#define ESCHER_SYS_MAX_NONSELF_EVENTS 0
#define ESCHER_SYS_MAX_XTUML_EVENTS ( ESCHER_SYS_MAX_SELF_EVENTS + ESCHER_SYS_MAX_NONSELF_EVENTS )
#define ESCHER_SYS_MAX_XTUML_TIMERS 0
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
#include "sys_sets.h"
#include "sys_user_co.h"
#include "sys_trace.h"
#include "sys_factory.h"
#include "sys_events.h"





#define SYSTEM_DOMAIN_COUNT 0
/* xtUML domain identification numbers */


#ifdef    __cplusplus
}
#endif
#endif  /* SYS_TYPES_H */
