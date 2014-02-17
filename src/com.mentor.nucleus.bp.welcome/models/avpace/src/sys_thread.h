/*----------------------------------------------------------------------------
 * File:   sys_thread.h
 *
 * Description:
 * This is the header file for multi-tasking/threading services.
 *
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/

#ifndef SYS_THREAD_H
#define SYS_THREAD_H
#ifdef  __cplusplus
extern "C" {
#endif


#define ESCHER_TASKING_SystemC 1
#define NUM_OF_XTUML_CLASS_THREADS 4
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

#ifdef    __cplusplus
}
#endif
#endif   /* SYS_THREAD_H */
