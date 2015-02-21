/*----------------------------------------------------------------------------
 * File:  sys_user_co.h
 *
 * Description:
 * Function declarations for user supplied (non-translated) call out entry
 * points in the generated application.
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef SYS_USER_CO_H
#define SYS_USER_CO_H
#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * UserInitializationCallout
 *
 * This function is invoked at the immediate beginning of application
 * initialization. It is the very first function to be executed at system
 * startup.
 * User supplied implementation of this function should be restricted to
 * things like memory initialization, early hardware duties, etc.
 *
 */
void UserInitializationCalloutf( void );
/* The following empty definition renders the callout hook invisible.
   Delete or comment out the following define to activate the in line
   hook for this callout.
   (activated by default...  Uncomment to deactivate.)
#define UserInitializationCallout()
 */

#ifndef UserInitializationCallout
#define UserInitializationCallout() UserInitializationCalloutf()
#endif

/*
 * UserPreOoaInitializationCallout
 *
 * This function is invoked immediately prior to executing any xtUML
 * initialization functions.
 */
void UserPreOoaInitializationCalloutf( void );
/* The following empty definition renders the callout hook invisible.
   Delete or comment out the following define to activate the in line
   hook for this callout.  */
#define UserPreOoaInitializationCallout()

#ifndef UserPreOoaInitializationCallout
#define UserPreOoaInitializationCallout() UserPreOoaInitializationCalloutf()
#endif

/*
 * UserPostOoaInitializationCallout
 *
 * This function is invoked immediately after executing any xtUML
 * initialization functions.
 * When this callout function returns, the system dispatcher will allow the
 * xtUML application analysis state models to start consuming events.
 */
void UserPostOoaInitializationCalloutf( void );
/* The following empty definition renders the callout hook invisible.
   Delete or comment out the following define to activate the in line
   hook for this callout.  */
#define UserPostOoaInitializationCallout()

#ifndef UserPostOoaInitializationCallout
#define UserPostOoaInitializationCallout() UserPostOoaInitializationCalloutf()
#endif

/*
 * UserBackgroundProcessingCallout
 *
 * This function is invoked once during each loop execution of the system
 * dispather.
 * It is invoked at the 'top' of the system dispatcher loop, immediately
 * prior to dispatching any xtUML application analysis events.
 */
void UserBackgroundProcessingCalloutf( void );
/* The following empty definition renders the callout hook invisible.
   Delete or comment out the following define to activate the in line
   hook for this callout.
   (activated by default...  Uncomment to deactivate.)
#define UserBackgroundProcessingCallout()
 */

#ifndef UserBackgroundProcessingCallout
#define UserBackgroundProcessingCallout() UserBackgroundProcessingCalloutf()
#endif

/*
 * UserPreShutdownCallout
 *
 * This function is invoked at termination of the system dispatcher, but
 * prior to performing any xtUML application analysis shutdown sequencing.
 */
void UserPreShutdownCalloutf( void );
/* The following empty definition renders the callout hook invisible.
   Delete or comment out the following define to activate the in line
   hook for this callout.  */
#define UserPreShutdownCallout()

#ifndef UserPreShutdownCallout
#define UserPreShutdownCallout() UserPreShutdownCalloutf()
#endif

/*
 * UserPostShutdownCallout
 *
 * This function is invoked immediately before application exit.
 */
void UserPostShutdownCalloutf( void );
/* The following empty definition renders the callout hook invisible.
   Delete or comment out the following define to activate the in line
   hook for this callout.  */
#define UserPostShutdownCallout()

#ifndef UserPostShutdownCallout
#define UserPostShutdownCallout() UserPostShutdownCalloutf()
#endif

/*
 * UserEventCantHappenCallout
 *
 * This function is invoked any time that an event is received that
 * results in a "cant happen" transition.
 */
void UserEventCantHappenCalloutf( const Escher_StateNumber_t,
                                         const Escher_StateNumber_t,
                                         const Escher_EventNumber_t );
/* The following empty definition renders the callout hook invisible.
   Delete or comment out the following define to activate the in line
   hook for this callout.  */
#define UserEventCantHappenCallout( s1, s2, e )

#ifndef UserEventCantHappenCallout
#define UserEventCantHappenCallout( s1, s2, e ) UserEventCantHappenCalloutf( s1, s2, e )
#endif

/*
 * UserEventNoInstanceCallout
 *
 * This function is invoked when we failed to validate the instance
 * to which an event was directed.  This can happen in normal operation
 * when the instance was deleted while the event was in flight (analysis
 * error).
 */
void UserEventNoInstanceCalloutf( const Escher_EventNumber_t );
/* The following empty definition renders the callout hook invisible.
   Delete or comment out the following define to activate the in line
   hook for this callout.  */
#define UserEventNoInstanceCallout( e )

#ifndef UserEventNoInstanceCallout
#define UserEventNoInstanceCallout( e ) UserEventNoInstanceCalloutf( e )
#endif

/*
 * UserEventFreeListEmptyCallout
 *
 * This function is invoked when an attempt is made to allocate an
 * event, but there are no more left.
 */
void UserEventFreeListEmptyCalloutf( void );
/* The following empty definition renders the callout hook invisible.
   Delete or comment out the following define to activate the in line
   hook for this callout.  */
#define UserEventFreeListEmptyCallout()

#ifndef UserEventFreeListEmptyCallout
#define UserEventFreeListEmptyCallout() UserEventFreeListEmptyCalloutf()
#endif

/*
 * UserEmptyHandleDetectedCallout
 *
 * This function is invoked when an attempt is made to use an instance
 * reference variable (handle) that is null (empty).
 */
void UserEmptyHandleDetectedCalloutf( c_t *, c_t * );
/* The following empty definition renders the callout hook invisible.
   Delete or comment out the following define to activate the in line
   hook for this callout.  */
#define UserEmptyHandleDetectedCallout( s1, s2 )

#ifndef UserEmptyHandleDetectedCallout
#define UserEmptyHandleDetectedCallout( s1, s2 ) UserEmptyHandleDetectedCalloutf( (c_t *) s1, (c_t *) s2 )
#endif

/*
 * UserObjectPoolEmptyCallout
 *
 * This function is invoked when an attempt is made to create an
 * instance of an object, but there are no instances available.
 */
void UserObjectPoolEmptyCalloutf( const Escher_DomainNumber_t, const Escher_ClassNumber_t );
/* The following empty definition renders the callout hook invisible.
   Delete or comment out the following define to activate the in line
   hook for this callout.
   (activated by default...  Uncomment to deactivate.)
#define UserObjectPoolEmptyCallout( s1, s2 )
 */

#ifndef UserObjectPoolEmptyCallout
#define UserObjectPoolEmptyCallout( s1, s2 ) UserObjectPoolEmptyCalloutf( s1, s2 )
#endif

/*
 * UserNodeListEmptyCallout
 *
 * This function is invoked when an attempt is made to allocate a
 * node, but there are no more left.
 */
void UserNodeListEmptyCalloutf( void );
/* The following empty definition renders the callout hook invisible.
   Delete or comment out the following define to activate the in line
   hook for this callout.  */
#define UserNodeListEmptyCallout()

#ifndef UserNodeListEmptyCallout
#define UserNodeListEmptyCallout() UserNodeListEmptyCalloutf()
#endif

/*
 * UserInterleavedBridgeOverflowCallout
 *
 * This function is invoked when an attempt is made to post too many
 * interleaved bridges.  The depth of this list is defined by
 * SYS_MAX_INTERLEAVED_BRIDGES (unless changed in the archetype).
 */
void UserInterleavedBridgeOverflowCalloutf( void );
/* The following empty definition renders the callout hook invisible.
   Delete or comment out the following define to activate the in line
   hook for this callout.  */
#define UserInterleavedBridgeOverflowCallout()

#ifndef UserInterleavedBridgeOverflowCallout
#define UserInterleavedBridgeOverflowCallout() UserInterleavedBridgeOverflowCalloutf()
#endif

/*
 * UserSelfEventQueueEmptyCallout
 *
 * This function is invoked when the events to self queue is
 * interrogated and found to be empty.
 */
void UserSelfEventQueueEmptyCalloutf( void );
/* The following empty definition renders the callout hook invisible.
   Delete or comment out the following define to activate the in line
   hook for this callout.  */
#define UserSelfEventQueueEmptyCallout()

#ifndef UserSelfEventQueueEmptyCallout
#define UserSelfEventQueueEmptyCallout() UserSelfEventQueueEmptyCalloutf()
#endif

/*
 * UserNonSelfEventQueueEmptyCallout
 *
 * This function is invoked when the events to instance queue is
 * interrogated and found to be empty.
 */
void UserNonSelfEventQueueEmptyCalloutf( void );
/* The following empty definition renders the callout hook invisible.
   Delete or comment out the following define to activate the in line
   hook for this callout.  */
#define UserNonSelfEventQueueEmptyCallout()

#ifndef UserNonSelfEventQueueEmptyCallout
#define UserNonSelfEventQueueEmptyCallout() UserNonSelfEventQueueEmptyCalloutf()
#endif

/*
 * UserPersistenceErrorCallout
 *
 * This function is invoked when trouble is detected reading from
 * or writing to persistent storage.
 */
void UserPersistenceErrorCalloutf( i_t );
/* The following empty definition renders the callout hook invisible.
   Delete or comment out the following define to activate the in line
   hook for this callout.  */
#define UserPersistenceErrorCallout( i1 )

#ifndef UserPersistenceErrorCallout
#define UserPersistenceErrorCallout( i1 ) UserPersistenceErrorCalloutf( i1 )
#endif

#ifdef	__cplusplus
}
#endif
#endif  /* SYS_USER_CO_H */
