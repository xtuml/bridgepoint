/*----------------------------------------------------------------------------
 * File:  sys_factory.h
 *
 * Description:
 * Here we have the system-level instance create and delete declaration.
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/

#ifndef SYS_FACTORY_H
#define SYS_FACTORY_H
#ifdef  __cplusplus
extern "C" {
#endif


#define Escher_CreateInstance( d, c ) Escher_CreateInstancef( c )
extern Escher_iHandle_t Escher_CreateInstancef(
  const Escher_ClassNumber_t );
#define Escher_DeleteInstance( i, d, c ) Escher_DeleteInstancef( i, c )
extern void Escher_DeleteInstancef(
  Escher_iHandle_t,
  const Escher_ClassNumber_t );
/*
 * Initialize object factory services.
 * Initialize class instance storage free pool (inanimate list)
 * by linking the empty instances into a collection.
 */
extern void Escher_ClassFactoryInit( 
  const Escher_DomainNumber_t,
  const Escher_ClassNumber_t );

#ifdef    __cplusplus
}
#endif
#endif   /* SYS_FACTORY_H */
