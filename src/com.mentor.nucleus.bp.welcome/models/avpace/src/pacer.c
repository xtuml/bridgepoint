/*----------------------------------------------------------------------------
 * File:  pacer.c
 *
 * UML Component Port Messages
 * Component/Module Name:  pacer
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/
/*
 The pacer component listens to the host's heartbeat and sends signals to
increase the rate depending on the host's current rate.  It consists of two
internal components, one that handles generating a beat and the other that
listens for increased activity.
 */

#include "avpace_sys_types.h"
#include "pacer.h"
#include "pacemaker.h"
#include "hostmonitor.h"
#include "heart.h"

/*
 * Interface:  synchronization
 * Provided Port:  to_heart
 * From Provider Message:  diastolic_pace
 */
void
pacer_to_heart_diastolic_pace()
{
  heart_out_to_body_diastolic_pace();
}

/*
 * Interface:  synchronization
 * Provided Port:  to_heart
 * To Provider Message:  diastolic_pulse
 */
void
pacer_to_heart_diastolic_pulse()
{
}

/*
 * Interface:  synchronization
 * Provided Port:  to_heart
 * To Provider Message:  init
 */
void
pacer_to_heart_init( const i_t p_diastolic_period, const i_t p_systolic_period )
{
}

/*
 * Interface:  synchronization
 * Provided Port:  to_heart
 * From Provider Message:  systolic_pace
 */
void
pacer_to_heart_systolic_pace()
{
  heart_out_to_body_systolic_pace();
}

/*
 * Interface:  synchronization
 * Provided Port:  to_heart
 * To Provider Message:  systolic_pulse
 */
void
pacer_to_heart_systolic_pulse()
{
}

/*
 * Interface:  host
 * Provided Port:  host
 * To Provider Message:  breath_taken
 */
void
pacer_host_breath_taken()
{
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( (void *) 0, &hostmonitor_HM_CBevent1c );
    Escher_SendEvent( e );
  }

}

/*
 * Interface:  host
 * Provided Port:  host
 * To Provider Message:  current_temp
 */
void
pacer_host_current_temp()
{
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( (void *) 0, &hostmonitor_HM_CBevent2c );
    Escher_SendEvent( e );
  }

}

