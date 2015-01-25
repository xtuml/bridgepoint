/*----------------------------------------------------------------------------
 * File:  MicrowaveOven_MO_D_class.c
 *
 * Class:       Door  (MO_D)
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
static Escher_SetElement_s MicrowaveOven_MO_D_container[ MicrowaveOven_MO_D_MAX_EXTENT_SIZE ];
static MicrowaveOven_MO_D MicrowaveOven_MO_D_instances[ MicrowaveOven_MO_D_MAX_EXTENT_SIZE ];
Escher_Extent_t pG_MicrowaveOven_MO_D_extent = {
  {0}, {0}, &MicrowaveOven_MO_D_container[ 0 ],
  (Escher_iHandle_t) &MicrowaveOven_MO_D_instances,
  sizeof( MicrowaveOven_MO_D ), MicrowaveOven_MO_D_STATE_1, MicrowaveOven_MO_D_MAX_EXTENT_SIZE
  };
/*----------------------------------------------------------------------------
 * State and transition action implementations for the following class:
 *
 * Class:      Door  (MO_D)
 * Component:  MicrowaveOven
 *--------------------------------------------------------------------------*/

/*
 * State 1:  [Open]
 */
static void MicrowaveOven_MO_D_act1( MicrowaveOven_MO_D *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_D_act1( MicrowaveOven_MO_D * self, const Escher_xtUMLEvent_t * const event )
{
  MicrowaveOven_MO_O * oven = 0; /* oven (MO_O) */
 
  /* ASSIGN self.is_secure = FALSE */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.is_secure = FALSE" );
  self->is_secure = FALSE;
  /* SELECT one oven RELATED BY self->MO_O[R4] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one oven RELATED BY self->MO_O[R4]" );
  oven = self->MO_O_R4;
  /* GENERATE MO_O4:cancel_cooking() TO oven */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_O4:cancel_cooking() TO oven" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( oven, &MicrowaveOven_MO_Oevent4c );
    Escher_SendEvent( e );
  }
}

/*
 * State 2:  [Closed]
 */
static void MicrowaveOven_MO_D_act2( MicrowaveOven_MO_D *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_D_act2( MicrowaveOven_MO_D * self, const Escher_xtUMLEvent_t * const event )
{
  /* ASSIGN self.is_secure = TRUE */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.is_secure = TRUE" );
  self->is_secure = TRUE;
}

const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Devent1c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_D_CLASS_NUMBER, MICROWAVEOVEN_MO_DEVENT1NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Devent2c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_D_CLASS_NUMBER, MICROWAVEOVEN_MO_DEVENT2NUM,
  ESCHER_IS_INSTANCE_EVENT };



/*
 * State-Event Matrix (SEM)
 * Row index is object (MC enumerated) current state.
 * Row zero is the uninitialized state (e.g., for creation event transitions).
 * Column index is (MC enumerated) state machine events.
 */
static const Escher_SEMcell_t MicrowaveOven_MO_D_StateEventMatrix[ 2 + 1 ][ 2 ] = {
  /* row 0:  uninitialized state (for creation events) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN },
  /* row 1:  MicrowaveOven_MO_D_STATE_1 (Open) */
  { EVENT_IS_IGNORED, MicrowaveOven_MO_D_STATE_2 },
  /* row 2:  MicrowaveOven_MO_D_STATE_2 (Closed) */
  { MicrowaveOven_MO_D_STATE_1, EVENT_IS_IGNORED }
};

  /*
   * Array of pointers to the class state action procedures.
   * Index is the (MC enumerated) number of the state action to execute.
   */
  static const StateAction_t MicrowaveOven_MO_D_acts[ 3 ] = {
    (StateAction_t) 0,
    (StateAction_t) MicrowaveOven_MO_D_act1,  /* Open */
    (StateAction_t) MicrowaveOven_MO_D_act2  /* Closed */
  };

  /*
   * Array of string names of the state machine names.
   * Index is the (MC enumerated) number of the state.
   */
  static const c_t * const state_name_strings[ 3 ] = {
    "",
    "Open",
    "Closed"
  };

/*
 * instance state machine event dispatching
 */
void
MicrowaveOven_MO_D_Dispatch( Escher_xtUMLEvent_t * event )
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
      next_state = MicrowaveOven_MO_D_StateEventMatrix[ current_state ][ event_number ];
      if ( next_state <= 2 ) {
        STATE_TXN_START_TRACE( "MO_D", current_state, state_name_strings[ current_state ] );
        /* Execute the state action and update the current state.  */
        ( *MicrowaveOven_MO_D_acts[ next_state ] )( instance, event );
        STATE_TXN_END_TRACE( "MO_D", next_state, state_name_strings[ next_state ] );
        instance->current_state = next_state;
      } else if ( next_state == EVENT_IS_IGNORED ) {
          /* event ignored */
          STATE_TXN_IG_TRACE( "MO_D", current_state );
      } else {
        /* empty else */
      }
    }
  }
}


