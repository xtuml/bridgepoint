/*----------------------------------------------------------------------------
 * File:  MicrowaveOven_MO_MT_class.c
 *
 * Class:       Magnetron Tube  (MO_MT)
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
static Escher_SetElement_s MicrowaveOven_MO_MT_container[ MicrowaveOven_MO_MT_MAX_EXTENT_SIZE ];
static MicrowaveOven_MO_MT MicrowaveOven_MO_MT_instances[ MicrowaveOven_MO_MT_MAX_EXTENT_SIZE ];
Escher_Extent_t pG_MicrowaveOven_MO_MT_extent = {
  {0}, {0}, &MicrowaveOven_MO_MT_container[ 0 ],
  (Escher_iHandle_t) &MicrowaveOven_MO_MT_instances,
  sizeof( MicrowaveOven_MO_MT ), MicrowaveOven_MO_MT_STATE_1, MicrowaveOven_MO_MT_MAX_EXTENT_SIZE
  };
/*----------------------------------------------------------------------------
 * State and transition action implementations for the following class:
 *
 * Class:      Magnetron Tube  (MO_MT)
 * Component:  MicrowaveOven
 *--------------------------------------------------------------------------*/

/*
 * State 1:  [Output Power Stable and OFF]
 */
static void MicrowaveOven_MO_MT_act1( MicrowaveOven_MO_MT *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_MT_act1( MicrowaveOven_MO_MT * self, const Escher_xtUMLEvent_t * const event )
{
}

/*
 * State 2:  [Reducing Output Power]
 */
static void MicrowaveOven_MO_MT_act2( MicrowaveOven_MO_MT *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_MT_act2( MicrowaveOven_MO_MT * self, const Escher_xtUMLEvent_t * const event )
{
  /* IF ( ( self.current_power_output == med_low ) ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( ( self.current_power_output == med_low ) )" );
  if ( ( self->current_power_output == MicrowaveOven_tube_wattage_med_low_e ) ) {
    /* ASSIGN self.current_power_output = low */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN self.current_power_output = low" );
    self->current_power_output = MicrowaveOven_tube_wattage_low_e;
  }
  else if ( ( self->current_power_output == MicrowaveOven_tube_wattage_medium_e ) ) {
    /* ASSIGN self.current_power_output = med_low */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN self.current_power_output = med_low" );
    self->current_power_output = MicrowaveOven_tube_wattage_med_low_e;
  }
  else if ( ( self->current_power_output == MicrowaveOven_tube_wattage_med_high_e ) ) {
    /* ASSIGN self.current_power_output = medium */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN self.current_power_output = medium" );
    self->current_power_output = MicrowaveOven_tube_wattage_medium_e;
  }
  else if ( ( self->current_power_output == MicrowaveOven_tube_wattage_high_e ) ) {
    /* ASSIGN self.current_power_output = med_high */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN self.current_power_output = med_high" );
    self->current_power_output = MicrowaveOven_tube_wattage_med_high_e;
  }
}

/*
 * State 3:  [Raising Output Power]
 */
static void MicrowaveOven_MO_MT_act3( MicrowaveOven_MO_MT *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_MT_act3( MicrowaveOven_MO_MT * self, const Escher_xtUMLEvent_t * const event )
{
  /* IF ( ( self.current_power_output == low ) ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( ( self.current_power_output == low ) )" );
  if ( ( self->current_power_output == MicrowaveOven_tube_wattage_low_e ) ) {
    /* ASSIGN self.current_power_output = med_low */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN self.current_power_output = med_low" );
    self->current_power_output = MicrowaveOven_tube_wattage_med_low_e;
  }
  else if ( ( self->current_power_output == MicrowaveOven_tube_wattage_med_low_e ) ) {
    /* ASSIGN self.current_power_output = medium */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN self.current_power_output = medium" );
    self->current_power_output = MicrowaveOven_tube_wattage_medium_e;
  }
  else if ( ( self->current_power_output == MicrowaveOven_tube_wattage_medium_e ) ) {
    /* ASSIGN self.current_power_output = med_high */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN self.current_power_output = med_high" );
    self->current_power_output = MicrowaveOven_tube_wattage_med_high_e;
  }
  else if ( ( self->current_power_output == MicrowaveOven_tube_wattage_med_high_e ) ) {
    /* ASSIGN self.current_power_output = high */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN self.current_power_output = high" );
    self->current_power_output = MicrowaveOven_tube_wattage_high_e;
  }
}

/*
 * State 4:  [Output Power Stable and ON]
 */
static void MicrowaveOven_MO_MT_act4( MicrowaveOven_MO_MT *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_MT_act4( MicrowaveOven_MO_MT * self, const Escher_xtUMLEvent_t * const event )
{
}

const Escher_xtUMLEventConstant_t MicrowaveOven_MO_MTevent1c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_MT_CLASS_NUMBER, MICROWAVEOVEN_MO_MTEVENT1NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t MicrowaveOven_MO_MTevent2c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_MT_CLASS_NUMBER, MICROWAVEOVEN_MO_MTEVENT2NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t MicrowaveOven_MO_MTevent3c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_MT_CLASS_NUMBER, MICROWAVEOVEN_MO_MTEVENT3NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t MicrowaveOven_MO_MTevent4c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_MT_CLASS_NUMBER, MICROWAVEOVEN_MO_MTEVENT4NUM,
  ESCHER_IS_INSTANCE_EVENT };



/*
 * State-Event Matrix (SEM)
 * Row index is object (MC enumerated) current state.
 * Row zero is the uninitialized state (e.g., for creation event transitions).
 * Column index is (MC enumerated) state machine events.
 */
static const Escher_SEMcell_t MicrowaveOven_MO_MT_StateEventMatrix[ 4 + 1 ][ 4 ] = {
  /* row 0:  uninitialized state (for creation events) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN },
  /* row 1:  MicrowaveOven_MO_MT_STATE_1 (Output Power Stable and OFF) */
  { MicrowaveOven_MO_MT_STATE_3, MicrowaveOven_MO_MT_STATE_2, MicrowaveOven_MO_MT_STATE_4, EVENT_IS_IGNORED },
  /* row 2:  MicrowaveOven_MO_MT_STATE_2 (Reducing Output Power) */
  { MicrowaveOven_MO_MT_STATE_3, MicrowaveOven_MO_MT_STATE_2, MicrowaveOven_MO_MT_STATE_4, MicrowaveOven_MO_MT_STATE_1 },
  /* row 3:  MicrowaveOven_MO_MT_STATE_3 (Raising Output Power) */
  { MicrowaveOven_MO_MT_STATE_3, MicrowaveOven_MO_MT_STATE_2, MicrowaveOven_MO_MT_STATE_4, MicrowaveOven_MO_MT_STATE_1 },
  /* row 4:  MicrowaveOven_MO_MT_STATE_4 (Output Power Stable and ON) */
  { MicrowaveOven_MO_MT_STATE_3, MicrowaveOven_MO_MT_STATE_2, EVENT_IS_IGNORED, MicrowaveOven_MO_MT_STATE_1 }
};

  /*
   * Array of pointers to the class state action procedures.
   * Index is the (MC enumerated) number of the state action to execute.
   */
  static const StateAction_t MicrowaveOven_MO_MT_acts[ 5 ] = {
    (StateAction_t) 0,
    (StateAction_t) MicrowaveOven_MO_MT_act1,  /* Output Power Stable and OFF */
    (StateAction_t) MicrowaveOven_MO_MT_act2,  /* Reducing Output Power */
    (StateAction_t) MicrowaveOven_MO_MT_act3,  /* Raising Output Power */
    (StateAction_t) MicrowaveOven_MO_MT_act4  /* Output Power Stable and ON */
  };

  /*
   * Array of string names of the state machine names.
   * Index is the (MC enumerated) number of the state.
   */
  static const c_t * const state_name_strings[ 5 ] = {
    "",
    "Output Power Stable and OFF",
    "Reducing Output Power",
    "Raising Output Power",
    "Output Power Stable and ON"
  };

/*
 * instance state machine event dispatching
 */
void
MicrowaveOven_MO_MT_Dispatch( Escher_xtUMLEvent_t * event )
{
  Escher_iHandle_t instance = GetEventTargetInstance( event );
  Escher_EventNumber_t event_number = GetOoaEventNumber( event );
  Escher_StateNumber_t current_state;
  Escher_StateNumber_t next_state;
  
  if ( 0 != instance ) {
    current_state = instance->current_state;
    if ( current_state > 4 ) {
      /* instance validation failure (object deleted while event in flight) */
      UserEventNoInstanceCallout( event_number );
    } else {
      next_state = MicrowaveOven_MO_MT_StateEventMatrix[ current_state ][ event_number ];
      if ( next_state <= 4 ) {
        STATE_TXN_START_TRACE( "MO_MT", current_state, state_name_strings[ current_state ] );
        /* Execute the state action and update the current state.  */
        ( *MicrowaveOven_MO_MT_acts[ next_state ] )( instance, event );
        STATE_TXN_END_TRACE( "MO_MT", next_state, state_name_strings[ next_state ] );
        instance->current_state = next_state;
      } else if ( next_state == EVENT_IS_IGNORED ) {
          /* event ignored */
          STATE_TXN_IG_TRACE( "MO_MT", current_state );
      } else {
        /* empty else */
      }
    }
  }
}


