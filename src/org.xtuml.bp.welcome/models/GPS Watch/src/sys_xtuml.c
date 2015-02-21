/*--------------------------------------------------------------------------
 * File:  sys_xtuml.c
 *
 * Description:
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "GPSWatch_sys_types.h"
#include "HeartRateMonitor_classes.h"
#include "Location_classes.h"
#include "Tracking_classes.h"
#include "UI_classes.h"


/*
 * Allocate the storage for the pool of container nodes.
 */
static Escher_ObjectSet_s node1_FreeList;
static Escher_SetElement_s node1s[ SYS_MAX_CONTAINERS ];

/*
 * Initialize the node1 instances by linking them into a collection.
 * These containoids will be collected into a null-terminated,
 * singly linked list (slist).
 * This needs to be called during architecture initialization.
 */
void
Escher_SetFactoryInit( const i_t n1_size )
{
  u2_t i;
  node1_FreeList.head = &node1s[ 0 ];
  /* Build the collection (linked list) of node1 instances.  */
  for ( i = 0; i < ( n1_size - 1 ); i++ ) {
    node1s[ i ].next = &node1s[ i + 1 ];
    node1s[ i ].object = 0;
  }
  node1s[ n1_size - 1 ].next = 0;
}

/*
 * This will copy all of the elements from one set into another
 * set.  If the target set is not empty, it will be cleared
 * before the copy operation occurs freeing any nodes in that set.
 * The new set will use containoids from the free list.
 */
void 
Escher_CopySet( Escher_ObjectSet_s * to_set,
                Escher_ObjectSet_s * const from_set )
{
  const Escher_SetElement_s * slot;

  /* May be copying into an existing set, release target collection nodes.  */
  Escher_ClearSet( to_set );

  for ( slot = from_set->head; ( slot != 0 ); slot = slot->next ) {
    Escher_SetInsertElement( to_set, slot->object ); 
  }
}

/*
 * Release all nodes in the given set back to the free pool.
 */
void
Escher_ClearSet( Escher_ObjectSet_s * set )
{
  if ( set->head != 0 ) {                                    /* empty set  */
    Escher_SetElement_s * slot;
    for ( slot = set->head; ( slot->next != 0 ); slot = slot->next ); /* Find end.  */
    slot->next = node1_FreeList.head;     /* Tie string to free list.      */
    node1_FreeList.head = set->head;      /* Point free list to head.      */
    Escher_InitSet( set );                /* Zero set out.  */
  }
}

/*
 * Insert a single element into the set in no particular order.
 * The element is a data item.  A container node will be allocated
 * to link in the element.
 */
void
Escher_SetInsertElement(
  Escher_ObjectSet_s * set,
  void * const substance
)
{
  Escher_SetElement_s * slot;
  if ( 0 == node1_FreeList.head ) {
    UserNodeListEmptyCallout(); /* Bad news!  No more nodes.         */
  } else {
    slot = node1_FreeList.head; /* Extract node from free list head. */
    node1_FreeList.head = node1_FreeList.head->next;
    slot->object = substance;
    slot->next = set->head;     /* Insert substance at list front.   */
    set->head = slot;
  }
}

/*
 * Insert a block of objects into the given set in sequence.  Link the
 * data into the supplied containers.
 * Return a pointer to the linked set.
 */
Escher_SetElement_s *
Escher_SetInsertBlock( Escher_SetElement_s * container,
                       const u1_t * instance,
                       const u2_t length,
                       u2_t count )
{
  Escher_SetElement_s * head = ( count > 0 ) ? container : 0;
  while ( count > 0 ) {
    count--;
    container->object = (void *) instance;  /* Link in the object data.     */
    instance = instance + length;           /* Bump to next object image.   */
    /* String together or ground containoids.  */
    container->next = ( count > 0 ) ? container + 1 : 0;
    container++;
  }
  return head;
}

/*
 * Remove an instance from an instance collection.
 */
/* Signature:  void Escher_SetRemoveInstance( pextent, instance, slot, container, pool ) */

/*
 * Insert an instance onto an extent.
 */
/* Signature:  void Escher_SetInsertInstance( pextent, node ) */


/*
 * Remove a data item from the given set.  This requires searching
 * the set for the item, unlinking the item (if found) and returning
 * the pointer to the removed node.  SetRemoveNode is used
 * when some knowledge of the linking mechanism is required (as
 * in extent management).  SetRemoveElement is used whenever
 * possible.
 */
Escher_SetElement_s *
Escher_SetRemoveNode(
  Escher_ObjectSet_s * set,
  const void * const d
)
{
  Escher_SetElement_s * t = set->head; /* Start with first node.           */
  /* Find node containing data and unlink from list.                 */
  if ( t->object == d ) {        /* Element found at head.           */
    set->head = t->next;         /* Unlink it from the list.         */
  } else {
    Escher_SetElement_s * t_old;
    do {                         /* Search for data element.         */
      t_old = t;
      t = t->next;
      if ( t == 0 ) { return 0; } /* absent       */
    } while ( t->object != d );
    t_old->next = t->next;      /* Unlink element from the list.     */
  }
  return t;
}

/*
 * This interface is used more often to remove an element.  This is
 * used when maximum anonymity is required.  Escher_SetRemoveNode is
 * used when some knowledge of the linking mechanism is required (as
 * in extent management).
 */
void
Escher_SetRemoveElement(
  Escher_ObjectSet_s * set,
  const void * const d
)
{
  Escher_SetElement_s * t;
  if ( set->head != 0 ) {                     /* empty set */
    t = Escher_SetRemoveNode( set, d );
    /* Return node to architecture collection (free list).             */
    if ( t != 0 ) {
      t->next = node1_FreeList.head;
      node1_FreeList.head = t;
    }
  }
}

/*
 * Return a pointer to the found element when the set contains the 
 * given data element.
 */
const void *
Escher_SetContains(
  const Escher_ObjectSet_s * const set,
  const void * const element
)
{
  const Escher_SetElement_s * node = set->head;
  while ( node != 0 ) {
    if ( node->object == element ) { return node; }  /* found  */
    node = node->next;
  }
  return 0;                                      /* absent */
}

/*
 * Count the elements in the set.  Return that count.
 * This routine counts nodes.
 */
u2_t 
Escher_SetCardinality( const Escher_ObjectSet_s * const set )
{
  u2_t result = 0;
  const Escher_SetElement_s * node = set->head;
  while ( node != 0 ) {
    result++;
    node = node->next;
  }
  return result;
}

/*
 * Return true when the left and right set are equivalent.
 * Note:  This currently is not implemented.
 */
bool
Escher_SetEquality( Escher_ObjectSet_s * const left_set,
                    Escher_ObjectSet_s * const right_set )
{
  bool rc = false;
  if ( (left_set->head == 0) && (right_set->head == 0) ) {
    rc = true;
  } else if ( ( (left_set->head != 0) && (right_set->head != 0) ) &&
    (Escher_SetCardinality( left_set ) == Escher_SetCardinality( right_set )) ) {
    rc = true;
  } else { /* nop */ }
  return rc;
}

/*
 * Initialize a set variable.
 */
/* Signature:  void Escher_InitSet( Escher_ObjectSet_s * set ) */

/*
 * Get any element (1st) from a collection.
 */
/* Signature:  void * Escher_SetGetAny( Escher_ObjectSet_s * const set ) */

/*
 * Return true if set is empty.
 */
/* Signature:  bool Escher_SetIsEmpty( const Escher_ObjectSet_s * const set ) */

/*
 * Use this method to reset the cursor.
 */
/* Signature:  void Escher_IteratorReset( Escher_Iterator_s * const iterator,
 *                                        Escher_ObjectSet_s * const set )
 */

/*
 * Interate to the next element and return it.
 */
void *
Escher_IteratorNext( Escher_Iterator_s * const iter )
{
  void * element = 0;
  if ( iter->cursor != 0 ) {
    element = iter->cursor->object;
    iter->cursor = iter->cursor->next;
  }
  return element;
}

/*
 * Set memory bytes to value at destination.
 */
void
Escher_memset( void * const dst, const u1_t val, u2_t len )
{
  u1_t * d = (u1_t *) dst;
  while ( len > 0 ) {
    len--;
    *d++ = val;
  }
}

/*
 * Move memory bytes from source to destination.
 */
void
Escher_memmove( void * const dst, const void * const src, u2_t len )
{
  u1_t * s = (u1_t *) src;
  u1_t * d = (u1_t *) dst;
  while ( len > 0 ) {
    len--;
    *d++ = *s++;
  }
}

/*
 * Copy characters and be paranoid about null delimiter.
 */
c_t *
Escher_strcpy( c_t * dst, const c_t * src )
{
  c_t * s = dst;
  s2_t i = ESCHER_SYS_MAX_STRING_LEN - 1;
  while ( ( i > 0 ) && ( *src != '\0' ) ) {
    --i;
    *dst++ = *src++;
  }
  *dst = '\0';  /* Ensure delimiter.  */
  return s;
}

/*
 * Add two strings.  Allocate a temporary memory variable to return the value.
 */
c_t *
Escher_stradd( const c_t * left, const c_t * right )
{
  s2_t i = ESCHER_SYS_MAX_STRING_LEN - 1;
  c_t * s = Escher_strget();
  c_t * dst = s;
  while ( ( i > 0 ) && ( *left != '\0' ) ) {
    --i;
    *dst++ = *left++;
  }
  while ( ( i > 0 ) && ( *right != '\0' ) ) {
    --i;
    *dst++ = *right++;
  }
  *dst = '\0';  /* Ensure delimiter.  */
  return s;
}

/*
 * Compare two strings.
 * Return negative number if s1 < s2.
 * Return zero if s1 == s2.
 * Return postive number if s1 > s2.
 */
c_t
Escher_strcmp( const c_t *p1, const c_t *p2 )
{
  const c_t *s1 = p1;
  const c_t *s2 = p2;
  c_t c1, c2;
  s2_t i = ESCHER_SYS_MAX_STRING_LEN;
  do {
    c1 = *s1++;
    c2 = *s2++;
    if ( c1 == 0 ) { break; }
    --i;
  } while ( ( c1 == c2 ) && ( i >= 0 ) );
  return ( c1 - c2 );
}

/*
 * Return a string buffer.  Rotate through a pool.
 */
c_t *
Escher_strget( void )
{
  static u1_t i = 0;
  static c_t s[ 4 ][ ESCHER_SYS_MAX_STRING_LEN ];
  i = ( i + 1 ) % 4;
  return ( &s[ i ][ 0 ] );
}


/* xtUML class info for all of the components (collections, sizes, etc.) */
Escher_Extent_t * const * const domain_class_info[ SYSTEM_DOMAIN_COUNT ] = {
  &HeartRateMonitor_class_info[0],
  &Location_class_info[0],
  &Tracking_class_info[0],
  &UI_class_info[0]
};

/*
 * Create an instance of the class numbered on the input.
 * Return the handle of the created instance.
 */
Escher_iHandle_t
Escher_CreateInstance(
  const Escher_DomainNumber_t domain_num,
  const Escher_ClassNumber_t class_num
)
{
  Escher_SetElement_s * node;
  Escher_iHandle_t instance;
  Escher_Extent_t * dci = *(domain_class_info[ domain_num ] + class_num);
  node = dci->inactive.head;

  if ( 0 == node ) {
    UserObjectPoolEmptyCallout( domain_num, class_num );
  }

  dci->inactive.head = dci->inactive.head->next;
  instance = (Escher_iHandle_t) node->object;
  instance->current_state = dci->initial_state;
  Escher_SetInsertInstance( &dci->active, node );
  return instance;
}

/*
 * Delete an instance of the class passed and numbered on the input.
 */
void
Escher_DeleteInstance(
  Escher_iHandle_t instance,
  const Escher_DomainNumber_t domain_num,
  const Escher_ClassNumber_t class_num
)
{
  Escher_SetElement_s * node;
  Escher_Extent_t * dci = *(domain_class_info[ domain_num ] + class_num);
  node = Escher_SetRemoveNode( &dci->active, instance );
  node->next = dci->inactive.head;
  dci->inactive.head = node;
  /* Initialize storage to zero.  */
  Escher_memset( instance, 0, dci->size );
}

/*
 * Initialize object factory services.
 * Initialize class instance storage free pool (inanimate list)
 * by linking the empty instances into a collection.
 */
void
Escher_ClassFactoryInit(
  const Escher_DomainNumber_t domain_num,
  const Escher_ClassNumber_t class_num )
{
  Escher_Extent_t * dci = *(domain_class_info[ domain_num ] + class_num);
  if ( 0 != dci ) {
  dci->active.head = 0;
  dci->inactive.head = Escher_SetInsertBlock( 
    dci->container,
    (const u1_t *) dci->pool,
    dci->size,
    dci->population );
  }
}
/*
 * Following provides the dispatcher loops for the xtUML event queues.
 */


bool Escher_run_flag = true; /* Turn this off to exit dispatch loop(s).  */

/* Structure:  Escher_systemxtUMLevents
 * _Super-union_ of all xtUML events in the system. For translation
 * patterns which can not accept dynamic memory allocation for
 * xtUML events, this union is used to predetermine the maximum
 * size of any xtUML event in the system.  */
typedef union {
  Escher_xtUMLEvent_t mc_event_base;
  HeartRateMonitor_DomainEvents_u mc_events_in_domain_HeartRateMonitor;
  Location_DomainEvents_u mc_events_in_domain_Location;
  Tracking_DomainEvents_u mc_events_in_domain_Tracking;
  UI_DomainEvents_u mc_events_in_domain_UI;
} Escher_systemxtUMLevents_t;

/* anchor declaration for front and back of list of events */
typedef struct {
  Escher_xtUMLEvent_t * head, * tail;
} xtUMLEventQueue_t;

/* Pointer to head of list of available event nodes.  */
static Escher_xtUMLEvent_t * free_event_list = 0;
static xtUMLEventQueue_t non_self_event_queue[ NUM_OF_XTUML_CLASS_THREADS ];
static xtUMLEventQueue_t self_event_queue[ NUM_OF_XTUML_CLASS_THREADS ];

/*
 * Link the event skeleton nodes together on the free list ready
 * for allocation.
 */
void
InitializeOoaEventPool( void )
{
  /* Pre-allocated memory pool for xtUML events.  */
  static Escher_systemxtUMLevents_t Escher_xtUML_event_pool[ ESCHER_SYS_MAX_XTUML_EVENTS ];
  u2_t i;
  Escher_run_flag = true; /* Default running enabled.  */
  non_self_event_queue[ 0 ].head = 0; non_self_event_queue[ 0 ].tail = 0;
  self_event_queue[ 0 ].head = 0; self_event_queue[ 0 ].tail = 0;
  /* String events together into a singly linked list. */
  free_event_list = (Escher_xtUMLEvent_t *) &Escher_xtUML_event_pool[ 0 ];
  for ( i = 0; i < ESCHER_SYS_MAX_XTUML_EVENTS - 1; i++ ) {
    Escher_xtUML_event_pool[ i ].mc_event_base.next =
      (Escher_xtUMLEvent_t *) &(Escher_xtUML_event_pool[ i + 1 ]);
  }
  Escher_xtUML_event_pool[ ESCHER_SYS_MAX_XTUML_EVENTS - 1 ].mc_event_base.next = 0;
}

/*
 * Obtain an empty event from the free list.
 */
Escher_xtUMLEvent_t * Escher_AllocatextUMLEvent( void )
{
  Escher_xtUMLEvent_t * event = 0;
  if ( free_event_list == 0 ) {
    UserEventFreeListEmptyCallout();   /* Bad news!  No more events.  */
  } else {
    event = free_event_list;       /* Grab front of the free list.  */
    free_event_list = event->next; /* Unlink from front of free list.  */
  }
  return event;
}

/*
 * Allocate the event and initialize the base attributes.
 */
Escher_xtUMLEvent_t *
Escher_NewxtUMLEvent( const void * const destination,
                   const Escher_xtUMLEventConstant_t * const event_info )
{
  Escher_xtUMLEvent_t * event = Escher_AllocatextUMLEvent();
  SetEventTargetInstance( event, destination );
  SetEventDestDomainNumber( event, event_info->destination_domain_number );
  SetEventDestObjectNumber( event, event_info->destination_object_number );
  SetOoaEventNumber( event, event_info->event_number );
  SetOoaEventFlags( event, event_info->event_flags );
  return event;
}

/*
 * Update the base attributes of an event for polymorphic processing.
 */
Escher_xtUMLEvent_t *
Escher_ModifyxtUMLEvent( Escher_xtUMLEvent_t * event,
                            const Escher_xtUMLEventConstant_t * const event_info )
{
  SetEventDestDomainNumber( event, event_info->destination_domain_number );
  SetEventDestObjectNumber( event, event_info->destination_object_number );
  SetOoaEventNumber( event, event_info->event_number );
  SetOoaEventFlags( event, event_info->event_flags );
  return event;
}

/*
 * Delete an event by moving it onto the free list.
 */
void
Escher_DeletextUMLEvent( Escher_xtUMLEvent_t * event )
{
  event->next = free_event_list;
  free_event_list = event;
}

/*
 * Send an event to the instance queue.  Use priority where applicable.
 *
 * The following table summarizes the construction of events
 * as expected in Escher.  For each type of object that
 * may be the source or target of an event, the expected
 * value of the source and destination handles in the event are
 * given.
 *
 *   object type | as event source     | as destination
 *   ------------+---------------------+----------------------
 *   instance    | handle              | handle
 *   class       | manufactured handle | 0
 *   creator     | manufactured handle | 0
 */
void
Escher_SendEvent( Escher_xtUMLEvent_t * event )
{
  xtUMLEventQueue_t * q = &non_self_event_queue[ 0 ];
  event->next = 0;
  /* Append the event to the tail end of the queue.  */
  /* No need to maintain prev pointers when not prioritizing.  */
  if ( q->tail == 0 ) {
    q->head = event;
  } else {
    q->tail->next = event;
  }
  q->tail = event;
}

/*
 * Drag an event from the instance directed queue if there is one.  This
 * routine also serves as the IsQueueEmpty routine.  A null return code 
 * indicates that the queue is empty.  Otherwise the handle to the
 * event will be returned.
 */
static Escher_xtUMLEvent_t * DequeueOoaNonSelfEvent( void );
static Escher_xtUMLEvent_t * DequeueOoaNonSelfEvent( void )
{
  Escher_xtUMLEvent_t * event;
  xtUMLEventQueue_t * q = &non_self_event_queue[ 0 ];
  /* Assign the event from the head of the queue.  */
  event = q->head;
  /* If the list is not empty, bump the head.  */
  if ( event != 0 ) {
    q->head = event->next;
    /* If empty, nullify tail.  Link prev pointers (if needed).  */
    if ( q->head == 0 ) {
      q->tail = 0;
    }
  } else {
    UserNonSelfEventQueueEmptyCallout();
  }
  return event;
}

/*
 * Send an event on the self queue.  No prioritization occurs on
 * this queue.
 */
void
Escher_SendSelfEvent( Escher_xtUMLEvent_t * event )
{
  xtUMLEventQueue_t * q = &self_event_queue[ 0 ];
  event->next = 0;
  /* Append the event to the tail end of the queue.  */
  /* No need to maintain prev pointers for self directed events.  */
  if ( q->tail == 0 ) {
    q->head = event;
  } else {
    q->tail->next = event;
  }
  q->tail = event;
}

/*
 * Drag an event from the self queue if there is one.  This routine
 * also serves as the IsQueueEmpty routine.  A null return code 
 * indicates that the queue is empty.  Otherwise the handle to the
 * event will be returned.
 */
static Escher_xtUMLEvent_t * DequeueOoaSelfEvent( void );
static Escher_xtUMLEvent_t * DequeueOoaSelfEvent( void )
{
  Escher_xtUMLEvent_t * event;
  xtUMLEventQueue_t * q = &self_event_queue[ 0 ];
  /* Assign the event from the head of the queue.  */
  event = q->head;
  /* If the list is not empty, bump the head.  */
  if ( event != 0 ) {
    q->head = event->next;               /* bump */
    /* If empty, nullify tail.  No need to maintain prev pointers
       for the self queue.  They are not used.  */
    if ( q->head == 0 ) {
      q->tail = 0;
    }
  } else {
    UserSelfEventQueueEmptyCallout();
  }
  return event;
}
/*
 * Loop on the event queues dispatching events for this thread.
 */
static void ooa_loop( void );
static void ooa_loop( void )
{
  /* class dispatch table
   */
  static const EventTaker_t * DomainClassDispatcherTable[ 4 ] =
    {
      HeartRateMonitor_EventDispatcher,
      Location_EventDispatcher,
      Tracking_EventDispatcher,
      UI_EventDispatcher,
    };
  Escher_xtUMLEvent_t * event;
  /* Start consuming events and dispatching background processes.  */
  while ( true == Escher_run_flag ) {
    event = DequeueOoaSelfEvent(); /* Self first.  */
    if ( 0 == event ) {
      event = DequeueOoaNonSelfEvent(); /* Instance next.  */
    }
    if ( 0 != event ) {
      ( *( DomainClassDispatcherTable[ GetEventDestDomainNumber( event ) ] )[ GetEventDestObjectNumber( event ) ] )( event );
      Escher_DeletextUMLEvent( event );
    } else {
    }
    UserBackgroundProcessingCallout();
  }
}

/*
 * Load up the threads with event dispatchers and/or specific functionality.
 */
void Escher_xtUML_run( void )
{
  ooa_loop();
}
