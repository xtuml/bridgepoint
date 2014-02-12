/*----------------------------------------------------------------------------
 * File:  sys_events.c
 *
 * Description:
 * This file provides the dispatcher loops for the xtUML event queues.
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/

#include "sys_types.h"
#include "sys_thread.h"
/* Include header(s) of component level events union and component dispatchers. */

bool Escher_run_flag = true; /* Turn this off to exit dispatch loop(s).  */
