/*----------------------------------------------------------------------------
 * File:  hostmonitor_TM_class.h
 *
 * Class:       Temperature Monitor  (TM)
 * Component:   hostmonitor
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef HOSTMONITOR_TM_CLASS_H
#define HOSTMONITOR_TM_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   Temperature Monitor  (TM)
 */
struct hostmonitor_TM {

  /* application analysis class attributes */

  /* relationship storage */
  /* Note:  No storage needed for TM->HM[R2] */
};
i_t hostmonitor_TM_op_getCurrentTemp( hostmonitor_TM * );

/* xtUML WARNING:  HM<-R2->TM never related!  */
/* Note:  HM<-R2->TM unrelate accessor not needed */


#define hostmonitor_TM_MAX_EXTENT_SIZE 10
extern Escher_Extent_t pG_hostmonitor_TM_extent;

#ifdef	__cplusplus
}
#endif

#endif  /* HOSTMONITOR_TM_CLASS_H */


