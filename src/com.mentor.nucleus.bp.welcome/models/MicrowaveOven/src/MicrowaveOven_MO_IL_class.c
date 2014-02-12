/*----------------------------------------------------------------------------
 * File:  MicrowaveOven_MO_IL_class.c
 *
 * Class:       Internal Light  (MO_IL)
 * Component:   MicrowaveOven
 *
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/

#include "MicrowaveOven_sys_types.h"
#include "TIM_bridge.h"
#include "MicrowaveOven_ARCH_bridge.h"
#include "MicrowaveOven_classes.h"


/*
 * Statically allocate space for the instance population for this class.
 * Allocate space for the class instance and its attribute values.
 * Depending upon the collection scheme, allocate containoids (collection
 * nodes) for gathering instances into free and active extents.
 */
static Escher_SetElement_s MicrowaveOven_MO_IL_container[ MicrowaveOven_MO_IL_MAX_EXTENT_SIZE ];
static MicrowaveOven_MO_IL MicrowaveOven_MO_IL_instances[ MicrowaveOven_MO_IL_MAX_EXTENT_SIZE ];
Escher_Extent_t pG_MicrowaveOven_MO_IL_extent = {
  {0}, {0}, &MicrowaveOven_MO_IL_container[ 0 ],
  (Escher_iHandle_t) &MicrowaveOven_MO_IL_instances,
  sizeof( MicrowaveOven_MO_IL ), MicrowaveOven_MO_IL_STATE_1, MicrowaveOven_MO_IL_MAX_EXTENT_SIZE
  };
/*----------------------------------------------------------------------------
 * State and transition action implementations for the following class:
 *
 * Class:      Internal Light  (MO_IL)
 * Component:  MicrowaveOven
 *--------------------------------------------------------------------------*/

/*
 * State 1:  [Off]
 */
static void MicrowaveOven_MO_IL_act1( MicrowaveOven_MO_IL *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_IL_act1( MicrowaveOven_MO_IL * self, const Escher_xtUMLEvent_t * const event )
{
}

/*
 * State 2:  [On]
 */
static void MicrowaveOven_MO_IL_act2( MicrowaveOven_MO_IL *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_IL_act2( MicrowaveOven_MO_IL * self, const Escher_xtUMLEvent_t * const event )
{
}

const Escher_xtUMLEventConstant_t MicrowaveOven_MO_ILevent1c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_IL_CLASS_NUMBER, MICROWAVEOVEN_MO_ILEVENT1NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t MicrowaveOven_MO_ILevent2c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_IL_CLASS_NUMBER, MICROWAVEOVEN_MO_ILEVENT2NUM,
  ESCHER_IS_INSTANCE_EVENT };



/*
 * State-Event Matrix (SEM)
 * Row index is object (MC enumerated) current state.
 * Row zero is the uninitialized state (e.g., for creation event transitions).
 * Column index is (MC enumerated) state machine events.
 */
static const Escher_SEMcell_t MicrowaveOven_MO_IL_StateEventMatrix[ 2 + 1 ][ 2 ] = {
  /* row 0:  uninitialized state (for creation events) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN },
  /* row 1:  MicrowaveOven_MO_IL_STATE_1 (Off) */
  { MicrowaveOven_MO_IL_STATE_2, EVENT_IS_IGNORED },
  /* row 2:  MicrowaveOven_MO_IL_STATE_2 (On) */
  { EVENT_IS_IGNORED, MicrowaveOven_MO_IL_STATE_1 }
};

  /*
   * Array of pointers to the class state action procedures.
   * Index is the (MC enumerated) number of the state action to execute.
   */
  static const StateAction_t MicrowaveOven_MO_IL_acts[ 3 ] = {
    (StateAction_t) 0,
    (StateAction_t) MicrowaveOven_MO_IL_act1,  /* Off */
    (StateAction_t) MicrowaveOven_MO_IL_act2  /* On */
  };

  /*
   * Array of string names of the state machine names.
   * Index is the (MC enumerated) number of the state.
   */
  static const c_t * const state_name_strings[ 3 ] = {
    "",
    "Off",
    "On"
  };

/*
 * instance state machine event dispatching
 */
void
MicrowaveOven_MO_IL_Dispatch( Escher_xtUMLEvent_t * event )
{
  Escher_iHandle_t instance = GetEventTargetInstance( event );
  Escher_EventNumber_t event_number = GetOoaEventNumber( event );
  Escher_StateNumber_t current_state;
  Escher_StateNumber_t next_state;
  
  if ( 0 != instance ) {
    current_state = instance->current_state;
    if ( current_state > 2 ) {
      /* instance validation failure (object deleted while event in flight) */
      UserEventNoInstanceCallout( event_number );
    } else {
      next_state = MicrowaveOven_MO_IL_StateEventMatrix[ current_state ][ event_number ];
      if ( next_state <= 2 ) {
        STATE_TXN_START_TRACE( "MO_IL", current_state, state_name_strings[ current_state ] );
        /* Execute the state action and update the current state.  */
        ( *MicrowaveOven_MO_IL_acts[ next_state ] )( instance, event );
        STATE_TXN_END_TRACE( "MO_IL", next_state, state_name_strings[ next_state ] );
        instance->current_state = next_state;
      } else if ( next_state == EVENT_IS_IGNORED ) {
          /* event ignored */
          STATE_TXN_IG_TRACE( "MO_IL", current_state );
      } else {
        /* empty else */
      }
    }
  }
}


