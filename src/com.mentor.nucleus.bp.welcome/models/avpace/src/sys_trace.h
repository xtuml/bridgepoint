/*----------------------------------------------------------------------------
 * File:  sys_trace.h
 *
 * Description:
 *  Run time instrumentation and tracing declarations
 *
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/

#ifndef SYS_TRACE_H
#define SYS_TRACE_H
#ifdef  __cplusplus
extern "C" {
#endif

#define ROX_TRACE_FLUSH( i ) fflush( i );

/*
 * State transition start tracing:
 */
/* To suppress transition source identification, uncomment the following macro */
/* #define ROX_TXN_START_PROLOGUE */
#define ROX_TXN_START_PROLOGUE

#ifndef ROX_TXN_START_PROLOGUE
#define ROX_TXN_START_PROLOGUE printf( "%s #%5u: ", __FILE__, __LINE__ ); ROX_TRACE_FLUSH( 0 )
#endif

/* To suppress state transition start tracing, uncomment the following macro */
/* #define STATE_TXN_START_TRACE( obj_kl, state_num, state_name ) */

#ifndef STATE_TXN_START_TRACE
#define STATE_TXN_START_TRACE( obj_kl, state_num, state_name ) {   ROX_TXN_START_PROLOGUE   printf( "Transition started:  %s State [%u] %s\n", obj_kl, state_num, state_name ); }
#endif

/*
 * State transition complete tracing:
 */
/* To suppress state transition complete tracing, uncomment the following macro */
/* #define STATE_TXN_END_TRACE( obj_kl, state_num, state_name ) */

#ifndef STATE_TXN_END_TRACE
#define STATE_TXN_END_TRACE( obj_kl, state_num, state_name ) {   ROX_TXN_START_PROLOGUE   printf( "Transition complete:  %s State [%u] %s\n", obj_kl, state_num, state_name );   ROX_TRACE_FLUSH( 0 ) }
#endif

/*
 * Event ignored tracing:
 */
/* To unsuppress event ignored tracing, comment out the following macro.  */
#define STATE_TXN_IG_TRACE( obj_kl, state_num )

#ifndef STATE_TXN_IG_TRACE
#define STATE_TXN_IG_TRACE( obj_kl, state_num ) {   ROX_TXN_START_PROLOGUE   printf( "Event ignored:  %s current_state = %u\n", obj_kl, state_num );   ROX_TRACE_FLUSH( 0 ) }
#endif

/*
 * Event can't happen tracing:
 */
/* To suppress can't happen tracing, uncomment the following macro */
/* #define STATE_TXN_CH_TRACE( obj_kl, state_num ) */

#ifndef STATE_TXN_CH_TRACE
#define STATE_TXN_CH_TRACE( obj_kl, state_num ) {   ROX_TXN_START_PROLOGUE   printf( "Event cannot happen:  %s current_state = %u\n", obj_kl, state_num );   ROX_TRACE_FLUSH( 0 ) }
#endif

/*
 * Transformer invocation start tracing:
 */
/* To suppress transformer start tracing, uncomment the following macro */
/* #define ROX_TRANSFORMER_START_TRACE( obj_kl, tfr_name ) */

#ifndef ROX_TRANSFORMER_START_TRACE
#define ROX_TRANSFORMER_START_TRACE( obj_kl, tfr_name ) {   ROX_TXN_START_PROLOGUE   printf( "Invocation started: '%s' Transformer '%s'\n", obj_kl, tfr_name );   ROX_TRACE_FLUSH( 0 ) }
#endif

/*
 * Transformer invocation complete tracing:
 */
/* To suppress transformer end tracing, uncomment the following macro */
/* #define ROX_TRANSFORMER_END_TRACE( obj_kl, tfr_name ) */

#ifndef ROX_TRANSFORMER_END_TRACE
#define ROX_TRANSFORMER_END_TRACE( obj_kl, tfr_name ) {   ROX_TXN_START_PROLOGUE   printf( "Invocation complete: '%s' Transformer '%s'\n", obj_kl, tfr_name );   ROX_TRACE_FLUSH( 0 ) }
#endif

/*
 * Object Action Language (OAL) statement level tracing:
 *
 * ROX_BPAL_STMT_PROLOGUE :
 * ROX_BPAL_STMT_INDENT :
 * ROX_BPAL_STMT_TRACE :
 */

/* To suppress statement source identification, uncomment the following macro */
/* #define ROX_BPAL_STMT_PROLOGUE */

#ifndef ROX_BPAL_STMT_PROLOGUE
#define ROX_BPAL_STMT_PROLOGUE printf( "%s #%5u: ", __FILE__, __LINE__ ); ROX_TRACE_FLUSH( 0 )
#endif

/* To suppress statement level indentation, uncomment the following macro */
/* #define ROX_BPAL_STMT_INDENT( blck_level ) */

#ifndef ROX_BPAL_STMT_INDENT
#define ROX_BPAL_STMT_INDENT( blck_level ) {  s1_t i; for ( i = 0; i < blck_level; i++ ) printf( "  " ); }
#endif

/* To suppress statement source identification, uncomment the following macro */
/* #define ROX_BPAL_STMT_TRACE( blck_level, stmt_action ) */

#ifndef ROX_BPAL_STMT_TRACE
#define ROX_BPAL_STMT_TRACE( blck_level, stmt_action ) {   ROX_BPAL_STMT_PROLOGUE   ROX_BPAL_STMT_INDENT( blck_level )   printf( "%s\n", stmt_action );   ROX_TRACE_FLUSH( 0 ) }
#endif

/* To suppress empty handle detection, modify the following macro.  */

#ifndef ROX_EMPTY_HANDLE_TRACE
#define ROX_EMPTY_HANDLE_TRACE( object_keyletters, s ) { UserEmptyHandleDetectedCallout( object_keyletters, s ) }
#endif

/*
 * Declare state information structure.
 */
typedef struct Escher_StateInfo_s Escher_StateInfo_s;
struct Escher_StateInfo_s {
  Escher_StateNumber_t state_number;  /* 'real' state number */
  c_t * state_name;
};

#ifdef    __cplusplus
}
#endif
#endif  /* ROX_TRACE_H */
