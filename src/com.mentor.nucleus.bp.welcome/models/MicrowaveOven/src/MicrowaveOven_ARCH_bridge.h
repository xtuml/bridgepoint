/*----------------------------------------------------------------------------
 * File:  MicrowaveOven_ARCH_bridge.h
 *
 * Description:
 * Methods for bridging to an external entity.
 *
 * External Entity:  Architecture (ARCH)
 * The Architecture external entity is used to model a shutdown bridge operation. This operation is called when the system has finished the test scenario.  

When the microwave oven models are translated into code by the model compiler, the call to this bridge operation is mapped to an architecture method used to shut down the system.
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/

#ifndef MICROWAVEOVEN_ARCH_BRIDGE_H
#define MICROWAVEOVEN_ARCH_BRIDGE_H
#ifdef	__cplusplus
extern	"C"	{
#endif


extern void MicrowaveOven_ARCH_shutdown( void );

#ifdef	__cplusplus
}
#endif
#endif   /* MICROWAVEOVEN_ARCH_BRIDGE_H */
