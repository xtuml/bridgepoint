/*----------------------------------------------------------------------------
 * File:  hostmonitor_RM_class.h
 *
 * Class:       Respiratory Monitor  (RM)
 * Component:   hostmonitor
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef HOSTMONITOR_RM_CLASS_H
#define HOSTMONITOR_RM_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   Respiratory Monitor  (RM)
 */
struct hostmonitor_RM {

  /* application analysis class attributes */

  /* relationship storage */
  /* Note:  No storage needed for RM->HM[R3] */
};
i_t hostmonitor_RM_op_getCurrentRate( hostmonitor_RM * );

/* xtUML WARNING:  HM<-R3->RM never related!  */
/* Note:  HM<-R3->RM unrelate accessor not needed */


#define hostmonitor_RM_MAX_EXTENT_SIZE 10
extern Escher_Extent_t pG_hostmonitor_RM_extent;

#ifdef	__cplusplus
}
#endif

#endif  /* HOSTMONITOR_RM_CLASS_H */


