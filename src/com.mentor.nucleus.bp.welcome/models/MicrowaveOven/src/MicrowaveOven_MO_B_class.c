/*----------------------------------------------------------------------------
 * File:  MicrowaveOven_MO_B_class.c
 *
 * Class:       Beeper  (MO_B)
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
static Escher_SetElement_s MicrowaveOven_MO_B_container[ MicrowaveOven_MO_B_MAX_EXTENT_SIZE ];
static MicrowaveOven_MO_B MicrowaveOven_MO_B_instances[ MicrowaveOven_MO_B_MAX_EXTENT_SIZE ];
Escher_Extent_t pG_MicrowaveOven_MO_B_extent = {
  {0}, {0}, &MicrowaveOven_MO_B_container[ 0 ],
  (Escher_iHandle_t) &MicrowaveOven_MO_B_instances,
  sizeof( MicrowaveOven_MO_B ), MicrowaveOven_MO_B_STATE_1, MicrowaveOven_MO_B_MAX_EXTENT_SIZE
  };
/*----------------------------------------------------------------------------
 * State and transition action implementations for the following class:
 *
 * Class:      Beeper  (MO_B)
 * Component:  MicrowaveOven
 *--------------------------------------------------------------------------*/

/*
 * State 1:  [Awaiting Beeper Request]
 */
static void MicrowaveOven_MO_B_act1( MicrowaveOven_MO_B *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_B_act1( MicrowaveOven_MO_B * self, const Escher_xtUMLEvent_t * const event )
{
  bool cancelled_timer; 
  /* ASSIGN self.beep_count = 0 */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.beep_count = 0" );
  self->beep_count = 0;
  /* ASSIGN cancelled_timer = TIM::timer_cancel(timer_inst_ref:self.beeper_timer) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN cancelled_timer = TIM::timer_cancel(timer_inst_ref:self.beeper_timer)" );
  cancelled_timer = TIM_timer_cancel( self->beeper_timer );
}

/*
 * State 2:  [Beeping]
 */
static void MicrowaveOven_MO_B_act2( MicrowaveOven_MO_B *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_B_act2( MicrowaveOven_MO_B * self, const Escher_xtUMLEvent_t * const event )
{
  /* IF ( ( self.beep_count == 0 ) ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( ( self.beep_count == 0 ) )" );
  if ( ( self->beep_count == 0 ) ) {
    Escher_xtUMLEvent_t * delay_over;  /* delay_over */ 
    /* CREATE EVENT INSTANCE delay_over(  ) TO self */
    XTUML_OAL_STMT_TRACE( 2, "CREATE EVENT INSTANCE delay_over(  ) TO self" );
    delay_over = Escher_NewxtUMLEvent( (void *) self, &MicrowaveOven_MO_Bevent2c );
    /* ASSIGN self.beeper_delay_over = delay_over */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN self.beeper_delay_over = delay_over" );
    self->beeper_delay_over = delay_over;
    /* ASSIGN self.beeper_timer = TIM::timer_start(event_inst:self.beeper_delay_over, microseconds:100000) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN self.beeper_timer = TIM::timer_start(event_inst:self.beeper_delay_over, microseconds:100000)" );
    self->beeper_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)self->beeper_delay_over, 100000 );
  }
  else if ( ( self->beep_count == 4 ) ) {
    MicrowaveOven_MO_O * oven = 0; /* oven (MO_O) */
 
    /* GENERATE MO_B3:beeping_stopped() TO self */
    XTUML_OAL_STMT_TRACE( 2, "GENERATE MO_B3:beeping_stopped() TO self" );
    { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( self, &MicrowaveOven_MO_Bevent3c );
      Escher_SendSelfEvent( e );
    }
    /* SELECT one oven RELATED BY self->MO_O[R3] */
    XTUML_OAL_STMT_TRACE( 2, "SELECT one oven RELATED BY self->MO_O[R3]" );
    oven = self->MO_O_R3;
    /* GENERATE MO_O6:beeping_over() TO oven */
    XTUML_OAL_STMT_TRACE( 2, "GENERATE MO_O6:beeping_over() TO oven" );
    { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( oven, &MicrowaveOven_MO_Oevent6c );
      Escher_SendEvent( e );
    }
  }
  else {
    /* ASSIGN self.beeper_timer = TIM::timer_start(event_inst:self.beeper_delay_over, microseconds:100000) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN self.beeper_timer = TIM::timer_start(event_inst:self.beeper_delay_over, microseconds:100000)" );
    self->beeper_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)self->beeper_delay_over, 100000 );
  }
  /* ASSIGN self.beep_count = ( self.beep_count + 1 ) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.beep_count = ( self.beep_count + 1 )" );
  self->beep_count = ( self->beep_count + 1 );
}

const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Bevent1c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_B_CLASS_NUMBER, MICROWAVEOVEN_MO_BEVENT1NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Bevent2c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_B_CLASS_NUMBER, MICROWAVEOVEN_MO_BEVENT2NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Bevent3c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_B_CLASS_NUMBER, MICROWAVEOVEN_MO_BEVENT3NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Bevent4c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_B_CLASS_NUMBER, MICROWAVEOVEN_MO_BEVENT4NUM,
  ESCHER_IS_INSTANCE_EVENT };



/*
 * State-Event Matrix (SEM)
 * Row index is object (MC enumerated) current state.
 * Row zero is the uninitialized state (e.g., for creation event transitions).
 * Column index is (MC enumerated) state machine events.
 */
static const Escher_SEMcell_t MicrowaveOven_MO_B_StateEventMatrix[ 2 + 1 ][ 4 ] = {
  /* row 0:  uninitialized state (for creation events) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN },
  /* row 1:  MicrowaveOven_MO_B_STATE_1 (Awaiting Beeper Request) */
  { MicrowaveOven_MO_B_STATE_2, EVENT_IS_IGNORED, EVENT_IS_IGNORED, EVENT_IS_IGNORED },
  /* row 2:  MicrowaveOven_MO_B_STATE_2 (Beeping) */
  { EVENT_IS_IGNORED, MicrowaveOven_MO_B_STATE_2, MicrowaveOven_MO_B_STATE_1, MicrowaveOven_MO_B_STATE_1 }
};

  /*
   * Array of pointers to the class state action procedures.
   * Index is the (MC enumerated) number of the state action to execute.
   */
  static const StateAction_t MicrowaveOven_MO_B_acts[ 3 ] = {
    (StateAction_t) 0,
    (StateAction_t) MicrowaveOven_MO_B_act1,  /* Awaiting Beeper Request */
    (StateAction_t) MicrowaveOven_MO_B_act2  /* Beeping */
  };

  /*
   * Array of string names of the state machine names.
   * Index is the (MC enumerated) number of the state.
   */
  static const c_t * const state_name_strings[ 3 ] = {
    "",
    "Awaiting Beeper Request",
    "Beeping"
  };

/*
 * instance state machine event dispatching
 */
void
MicrowaveOven_MO_B_Dispatch( Escher_xtUMLEvent_t * event )
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
      next_state = MicrowaveOven_MO_B_StateEventMatrix[ current_state ][ event_number ];
      if ( next_state <= 2 ) {
        STATE_TXN_START_TRACE( "MO_B", current_state, state_name_strings[ current_state ] );
        /* Execute the state action and update the current state.  */
        ( *MicrowaveOven_MO_B_acts[ next_state ] )( instance, event );
        STATE_TXN_END_TRACE( "MO_B", next_state, state_name_strings[ next_state ] );
        instance->current_state = next_state;
      } else if ( next_state == EVENT_IS_IGNORED ) {
          /* event ignored */
          STATE_TXN_IG_TRACE( "MO_B", current_state );
      } else {
        /* empty else */
      }
    }
  }
}


