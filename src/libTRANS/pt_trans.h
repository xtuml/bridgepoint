/*========================================================================
//
// File:      $RCSfile: pt_trans.h,v $
// Version:   $Revision: 1.6 $
// Modified:  $Date: 2013/01/10 23:35:13 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//========================================================================
*/

#ifndef PT_TRANS_HH
#define PT_TRANS_HH

#ifdef _cplusplus
extern "C" 
{
#endif

/* Function declarations */

void *pt_TranslateInit();

const char *pt_TranslateString( void *p_parm,
                                const char *p_switch,
                                const char *p_string);

void pt_TranslateCleanup(void *p_parm);

#ifdef _cplusplus
}
#endif

#endif
