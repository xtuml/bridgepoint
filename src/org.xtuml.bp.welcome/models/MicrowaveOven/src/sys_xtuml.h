/*----------------------------------------------------------------------------
 * File:  sys_xtuml.h
 *
 * Description:
 * Here we have the system-level instance create and delete declaration.
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef SYS_XTUML_H
#define SYS_XTUML_H
#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * This is the most simple and basic container class and
 * represents elements in the set (nodes in the list).
 */
typedef struct Escher_SetElement_s Escher_SetElement_s;
struct Escher_SetElement_s {
  Escher_SetElement_s * next;
  void * object;
};

/*
 * This is the set class and may contain attributes that apply
 * to the set as a whole.
 */
typedef struct {
  Escher_SetElement_s * head;
} Escher_ObjectSet_s;

/*
 * Notice the symmetry between the object containoid, the
 * head of set structure and the set node containoid.
 */
typedef struct {
  Escher_SetElement_s * next;
} Escher_Object_s;

/*
 * Here is the shape of the general class extent.  This array contains list
 * heads for both active (animate) and inactive (inanimate) instances.
 */
typedef struct {
  Escher_ObjectSet_s active, inactive;
  Escher_SetElement_s* container;
  Escher_iHandle_t pool;
  Escher_ClassSize_t size;
  Escher_StateNumber_t initial_state;
  Escher_InstanceIndex_t population;
} Escher_Extent_t;

Escher_UniqueID_t Escher_ID_factory( void );
void Escher_SetFactoryInit( const i_t );
void Escher_CopySet( Escher_ObjectSet_s *,
                const Escher_ObjectSet_s * const );
void Escher_ClearSet( Escher_ObjectSet_s * );
#define ESCHER_SET_LHS_IS_INSTANCE 0x01
#define ESCHER_SET_RHS_IS_INSTANCE 0x02
Escher_ObjectSet_s *
Escher_SetUnion( Escher_ObjectSet_s * const, void * const, void * const, int );
Escher_ObjectSet_s *
Escher_SetIntersection( Escher_ObjectSet_s * const, void * const, void * const, int );
Escher_ObjectSet_s *
Escher_SetDifference( Escher_ObjectSet_s * const, void * const, void * const, int );
Escher_ObjectSet_s *
Escher_SetSymmetricDifference( Escher_ObjectSet_s * const, void * const, void * const, int );
void Escher_SetInsertElement( Escher_ObjectSet_s *,
                         void * const );
Escher_SetElement_s *
Escher_SetInsertBlock( Escher_SetElement_s *,
                       const u1_t *,
                       const Escher_size_t,
                       Escher_size_t );
#define Escher_SetRemoveInstance( pextent, instance, slot, container, pool ) \
  slot = Escher_SetRemoveNode( pextent, instance )
#define Escher_SetInsertInstance( pextent, node ) \
  node->next = (pextent)->head; \
  (pextent)->head = node


Escher_SetElement_s *
Escher_SetRemoveNode( Escher_ObjectSet_s *,
                      const void * const );
void Escher_SetRemoveElement( Escher_ObjectSet_s *,
                         const void * const );
const void * Escher_SetContains( const Escher_ObjectSet_s * const,
                    const void * const );
Escher_size_t Escher_SetCardinality( const Escher_ObjectSet_s * const );
bool Escher_SetEquality( Escher_ObjectSet_s * const,
                    Escher_ObjectSet_s * const );
#define Escher_InitSet( S ) (S)->head = 0
#define Escher_SetGetAny( S ) ( ((S)->head != 0) ? (S)->head->object : 0 )
#define Escher_SetIsEmpty( S ) ( (S)->head == 0 )

/*
 * Collection Iteration
 * The iterator uses a cursor external to the set (or extent).
 * This cursor maintains a flavor of current element.
 */
typedef struct Escher_Iterator_s Escher_Iterator_s;
struct Escher_Iterator_s {
  Escher_SetElement_s * cursor;
};
#define Escher_IteratorReset( I, S ) ( ((I)->cursor = (S)->head) )
void * Escher_IteratorNext( Escher_Iterator_s * const );

/* We could easily replace this function declaration with a macro
   that invoked the compiler (C library) supplied strlen.  */
Escher_size_t Escher_strlen( const c_t * );

/* We could easily replace this function declaration with a macro
   that invoked the compiler (C library) supplied memset.  */
void Escher_memset( void * const, const u1_t, Escher_size_t );

/* We could easily replace this function declaration with a macro
   that invoked the compiler (C library) supplied memmove.  */
void Escher_memmove( void * const, const void * const, Escher_size_t );
c_t * Escher_strcpy( c_t *, const c_t * );
c_t * Escher_stradd( const c_t *, const c_t * );
/* We could easily replace this function declaration with a macro
   that invoked the compiler (C library) supplied strcmp.  */
c_t Escher_strcmp( const c_t *, const c_t * );
c_t * Escher_strget( void );

Escher_iHandle_t Escher_CreateInstance( const Escher_DomainNumber_t, const Escher_ClassNumber_t );
void Escher_DeleteInstance( Escher_iHandle_t, const Escher_DomainNumber_t, const Escher_ClassNumber_t );
/*
 * Initialize object factory services.
 * Initialize class instance storage free pool (inanimate list)
 * by linking the empty instances into a collection.
 */
void Escher_ClassFactoryInit( const Escher_DomainNumber_t, const Escher_ClassNumber_t );

#ifdef	__cplusplus
}
#endif
#endif   /* SYS_XTUML_H */
