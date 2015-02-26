/*----------------------------------------------------------------------------
 * File:  MicrowaveOven_MO_O_class.c
 *
 * Class:       Oven  (MO_O)
 * Component:   MicrowaveOven
 *
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/

#include "MicrowaveOven_sys_types.h"
#include "TIM_bridge.h"
#include "MicrowaveOven_ARCH_bridge.h"
#include "MicrowaveOven_classes.h"


/*
 * RELATE MO_MT TO MO_O ACROSS R1
 */
void
MicrowaveOven_MO_O_R1_Link( MicrowaveOven_MO_MT * part, MicrowaveOven_MO_O * form )
{
  if ( (part == 0) || (form == 0) ) {
    XTUML_EMPTY_HANDLE_TRACE( "MO_O", "MicrowaveOven_MO_O_R1_Link" );
    return;
  }
  form->TubeID = part->TubeID;
  form->MO_MT_R1 = part;
  /* Note:  MO_MT->MO_O[R1] not navigated */
}

/*
 * RELATE MO_IL TO MO_O ACROSS R2
 */
void
MicrowaveOven_MO_O_R2_Link( MicrowaveOven_MO_IL * part, MicrowaveOven_MO_O * form )
{
  if ( (part == 0) || (form == 0) ) {
    XTUML_EMPTY_HANDLE_TRACE( "MO_O", "MicrowaveOven_MO_O_R2_Link" );
    return;
  }
  form->LightID = part->LightID;
  form->MO_IL_R2 = part;
  /* Note:  MO_IL->MO_O[R2] not navigated */
}

/*
 * RELATE MO_B TO MO_O ACROSS R3
 */
void
MicrowaveOven_MO_O_R3_Link( MicrowaveOven_MO_B * part, MicrowaveOven_MO_O * form )
{
  if ( (part == 0) || (form == 0) ) {
    XTUML_EMPTY_HANDLE_TRACE( "MO_O", "MicrowaveOven_MO_O_R3_Link" );
    return;
  }
  form->BeeperID = part->BeeperID;
  form->MO_B_R3 = part;
  part->MO_O_R3 = form;
}

/*
 * RELATE MO_D TO MO_O ACROSS R4
 */
void
MicrowaveOven_MO_O_R4_Link( MicrowaveOven_MO_D * part, MicrowaveOven_MO_O * form )
{
  if ( (part == 0) || (form == 0) ) {
    XTUML_EMPTY_HANDLE_TRACE( "MO_O", "MicrowaveOven_MO_O_R4_Link" );
    return;
  }
  form->DoorID = part->DoorID;
  form->MO_D_R4 = part;
  part->MO_O_R4 = form;
}

/*
 * RELATE MO_TRN TO MO_O ACROSS R5
 */
void
MicrowaveOven_MO_O_R5_Link( MicrowaveOven_MO_TRN * part, MicrowaveOven_MO_O * form )
{
  if ( (part == 0) || (form == 0) ) {
    XTUML_EMPTY_HANDLE_TRACE( "MO_O", "MicrowaveOven_MO_O_R5_Link" );
    return;
  }
  form->TurntableID = part->TurntableID;
  form->MO_TRN_R5 = part;
  /* Note:  MO_TRN->MO_O[R5] not navigated */
}

/*
 * Statically allocate space for the instance population for this class.
 * Allocate space for the class instance and its attribute values.
 * Depending upon the collection scheme, allocate containoids (collection
 * nodes) for gathering instances into free and active extents.
 */
static Escher_SetElement_s MicrowaveOven_MO_O_container[ MicrowaveOven_MO_O_MAX_EXTENT_SIZE ];
static MicrowaveOven_MO_O MicrowaveOven_MO_O_instances[ MicrowaveOven_MO_O_MAX_EXTENT_SIZE ];
Escher_Extent_t pG_MicrowaveOven_MO_O_extent = {
  {0}, {0}, &MicrowaveOven_MO_O_container[ 0 ],
  (Escher_iHandle_t) &MicrowaveOven_MO_O_instances,
  sizeof( MicrowaveOven_MO_O ), MicrowaveOven_MO_O_STATE_1, MicrowaveOven_MO_O_MAX_EXTENT_SIZE
  };
/*----------------------------------------------------------------------------
 * State and transition action implementations for the following class:
 *
 * Class:      Oven  (MO_O)
 * Component:  MicrowaveOven
 *--------------------------------------------------------------------------*/

/*
 * State 1:  [Awaiting Cooking Request]
 */
static void MicrowaveOven_MO_O_act1( MicrowaveOven_MO_O *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_O_act1( MicrowaveOven_MO_O * self, const Escher_xtUMLEvent_t * const event )
{
  MicrowaveOven_MO_B * beeper = 0; /* beeper (MO_B) */
 
  /* ASSIGN self.remaining_cooking_time = 0 */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.remaining_cooking_time = 0" );
  self->remaining_cooking_time = 0;
  /* SELECT one beeper RELATED BY self->MO_B[R3] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one beeper RELATED BY self->MO_B[R3]" );
  beeper = self->MO_B_R3;
  /* GENERATE MO_B4:stop_beeping() TO beeper */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_B4:stop_beeping() TO beeper" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( beeper, &MicrowaveOven_MO_Bevent4c );
    Escher_SendEvent( e );
  }
}

/*
 * State 2:  [Ensuring Safe to Cook]
 */
static void MicrowaveOven_MO_O_act2( MicrowaveOven_MO_O *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_O_act2( MicrowaveOven_MO_O * self, const Escher_xtUMLEvent_t * const event )
{
  /* IF ( ( self.remaining_cooking_time != 0 ) ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( ( self.remaining_cooking_time != 0 ) )" );
  if ( ( self->remaining_cooking_time != 0 ) ) {
    MicrowaveOven_MO_D * door = 0; /* door (MO_D) */
 
    /* SELECT one door RELATED BY self->MO_D[R4] */
    XTUML_OAL_STMT_TRACE( 2, "SELECT one door RELATED BY self->MO_D[R4]" );
    door = self->MO_D_R4;
    /* IF ( ( door.is_secure == TRUE ) ) */
    XTUML_OAL_STMT_TRACE( 2, "IF ( ( door.is_secure == TRUE ) )" );
    if ( ( door->is_secure == TRUE ) ) {
      /* GENERATE MO_O7:oven_safe() TO self */
      XTUML_OAL_STMT_TRACE( 3, "GENERATE MO_O7:oven_safe() TO self" );
      { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( self, &MicrowaveOven_MO_Oevent7c );
        Escher_SendSelfEvent( e );
      }
    }
  }
}

/*
 * State 3:  [Cooking]
 */
static void MicrowaveOven_MO_O_act3( MicrowaveOven_MO_O *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_O_act3( MicrowaveOven_MO_O * self, const Escher_xtUMLEvent_t * const event )
{
  Escher_xtUMLEvent_t * cooking_over;  /* cooking_over */ MicrowaveOven_MO_IL * light = 0; /* light (MO_IL) */
 MicrowaveOven_MO_TRN * turntable = 0; /* turntable (MO_TRN) */
 MicrowaveOven_MO_MT * tube = 0; /* tube (MO_MT) */
 
  /* CREATE EVENT INSTANCE cooking_over(  ) TO self */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE cooking_over(  ) TO self" );
  cooking_over = Escher_NewxtUMLEvent( (void *) self, &MicrowaveOven_MO_Oevent5c );
  /* ASSIGN self.oven_timer = TIM::timer_start(event_inst:cooking_over, microseconds:self.remaining_cooking_time) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.oven_timer = TIM::timer_start(event_inst:cooking_over, microseconds:self.remaining_cooking_time)" );
  self->oven_timer = TIM_timer_start( (Escher_xtUMLEvent_t *)cooking_over, self->remaining_cooking_time );
  /* SELECT one light RELATED BY self->MO_IL[R2] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one light RELATED BY self->MO_IL[R2]" );
  light = self->MO_IL_R2;
  /* GENERATE MO_IL1:switch_on() TO light */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_IL1:switch_on() TO light" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( light, &MicrowaveOven_MO_ILevent1c );
    Escher_SendEvent( e );
  }
  /* SELECT one turntable RELATED BY self->MO_TRN[R5] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one turntable RELATED BY self->MO_TRN[R5]" );
  turntable = self->MO_TRN_R5;
  /* GENERATE MO_TRN1:spin() TO turntable */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_TRN1:spin() TO turntable" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( turntable, &MicrowaveOven_MO_TRNevent1c );
    Escher_SendEvent( e );
  }
  /* SELECT one tube RELATED BY self->MO_MT[R1] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one tube RELATED BY self->MO_MT[R1]" );
  tube = self->MO_MT_R1;
  /* GENERATE MO_MT3:power_on() TO tube */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_MT3:power_on() TO tube" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( tube, &MicrowaveOven_MO_MTevent3c );
    Escher_SendEvent( e );
  }
}

/*
 * State 4:  [Cooking Suspended]
 */
static void MicrowaveOven_MO_O_act4( MicrowaveOven_MO_O *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_O_act4( MicrowaveOven_MO_O * self, const Escher_xtUMLEvent_t * const event )
{
  bool cancelled; MicrowaveOven_MO_IL * light = 0; /* light (MO_IL) */
 MicrowaveOven_MO_TRN * turntable = 0; /* turntable (MO_TRN) */
 MicrowaveOven_MO_MT * tube = 0; /* tube (MO_MT) */
 
  /* ASSIGN self.remaining_cooking_time = TIM::timer_remaining_time(timer_inst_ref:self.oven_timer) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.remaining_cooking_time = TIM::timer_remaining_time(timer_inst_ref:self.oven_timer)" );
  self->remaining_cooking_time = TIM_timer_remaining_time( self->oven_timer );
  /* ASSIGN cancelled = TIM::timer_cancel(timer_inst_ref:self.oven_timer) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN cancelled = TIM::timer_cancel(timer_inst_ref:self.oven_timer)" );
  cancelled = TIM_timer_cancel( self->oven_timer );
  /* SELECT one light RELATED BY self->MO_IL[R2] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one light RELATED BY self->MO_IL[R2]" );
  light = self->MO_IL_R2;
  /* GENERATE MO_IL2:switch_off() TO light */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_IL2:switch_off() TO light" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( light, &MicrowaveOven_MO_ILevent2c );
    Escher_SendEvent( e );
  }
  /* SELECT one turntable RELATED BY self->MO_TRN[R5] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one turntable RELATED BY self->MO_TRN[R5]" );
  turntable = self->MO_TRN_R5;
  /* GENERATE MO_TRN2:stop() TO turntable */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_TRN2:stop() TO turntable" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( turntable, &MicrowaveOven_MO_TRNevent2c );
    Escher_SendEvent( e );
  }
  /* SELECT one tube RELATED BY self->MO_MT[R1] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one tube RELATED BY self->MO_MT[R1]" );
  tube = self->MO_MT_R1;
  /* GENERATE MO_MT4:power_off() TO tube */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_MT4:power_off() TO tube" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( tube, &MicrowaveOven_MO_MTevent4c );
    Escher_SendEvent( e );
  }
}

/*
 * State 5:  [Signalling Cooking Period Over]
 */
static void MicrowaveOven_MO_O_act5( MicrowaveOven_MO_O *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_O_act5( MicrowaveOven_MO_O * self, const Escher_xtUMLEvent_t * const event )
{
  MicrowaveOven_MO_B * beeper = 0; /* beeper (MO_B) */
 MicrowaveOven_MO_IL * light = 0; /* light (MO_IL) */
 MicrowaveOven_MO_TRN * turntable = 0; /* turntable (MO_TRN) */
 MicrowaveOven_MO_MT * tube = 0; /* tube (MO_MT) */
 
  /* SELECT one beeper RELATED BY self->MO_B[R3] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one beeper RELATED BY self->MO_B[R3]" );
  beeper = self->MO_B_R3;
  /* GENERATE MO_B1:start_beeping() TO beeper */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_B1:start_beeping() TO beeper" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( beeper, &MicrowaveOven_MO_Bevent1c );
    Escher_SendEvent( e );
  }
  /* SELECT one light RELATED BY self->MO_IL[R2] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one light RELATED BY self->MO_IL[R2]" );
  light = self->MO_IL_R2;
  /* GENERATE MO_IL2:switch_off() TO light */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_IL2:switch_off() TO light" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( light, &MicrowaveOven_MO_ILevent2c );
    Escher_SendEvent( e );
  }
  /* SELECT one turntable RELATED BY self->MO_TRN[R5] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one turntable RELATED BY self->MO_TRN[R5]" );
  turntable = self->MO_TRN_R5;
  /* GENERATE MO_TRN2:stop() TO turntable */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_TRN2:stop() TO turntable" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( turntable, &MicrowaveOven_MO_TRNevent2c );
    Escher_SendEvent( e );
  }
  /* SELECT one tube RELATED BY self->MO_MT[R1] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one tube RELATED BY self->MO_MT[R1]" );
  tube = self->MO_MT_R1;
  /* GENERATE MO_MT4:power_off() TO tube */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_MT4:power_off() TO tube" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( tube, &MicrowaveOven_MO_MTevent4c );
    Escher_SendEvent( e );
  }
}

/*
 * State 6:  [Assigning Cooking Period]
 */
static void MicrowaveOven_MO_O_act6( MicrowaveOven_MO_O *, const Escher_xtUMLEvent_t * const );
static void
MicrowaveOven_MO_O_act6( MicrowaveOven_MO_O * self, const Escher_xtUMLEvent_t * const event )
{
  MicrowaveOven_MO_Oevent8 * rcvd_evt = (MicrowaveOven_MO_Oevent8 *) event;
  /* ASSIGN self.remaining_cooking_time = PARAM.period */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN self.remaining_cooking_time = PARAM.period" );
  self->remaining_cooking_time = rcvd_evt->p_period;
}


const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Oevent3c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_O_CLASS_NUMBER, MICROWAVEOVEN_MO_OEVENT3NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Oevent4c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_O_CLASS_NUMBER, MICROWAVEOVEN_MO_OEVENT4NUM,
  ESCHER_IS_INSTANCE_EVENT };


const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Oevent5c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_O_CLASS_NUMBER, MICROWAVEOVEN_MO_OEVENT5NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Oevent6c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_O_CLASS_NUMBER, MICROWAVEOVEN_MO_OEVENT6NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Oevent7c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_O_CLASS_NUMBER, MICROWAVEOVEN_MO_OEVENT7NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t MicrowaveOven_MO_Oevent8c = {
  MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_O_CLASS_NUMBER, MICROWAVEOVEN_MO_OEVENT8NUM,
  ESCHER_IS_INSTANCE_EVENT };




/*
 * State-Event Matrix (SEM)
 * Row index is object (MC enumerated) current state.
 * Row zero is the uninitialized state (e.g., for creation event transitions).
 * Column index is (MC enumerated) state machine events.
 */
static const Escher_SEMcell_t MicrowaveOven_MO_O_StateEventMatrix[ 6 + 1 ][ 6 ] = {
  /* row 0:  uninitialized state (for creation events) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN },
  /* row 1:  MicrowaveOven_MO_O_STATE_1 (Awaiting Cooking Request) */
  { EVENT_IS_IGNORED, EVENT_IS_IGNORED, EVENT_IS_IGNORED, EVENT_IS_IGNORED, EVENT_IS_IGNORED, MicrowaveOven_MO_O_STATE_6 },
  /* row 2:  MicrowaveOven_MO_O_STATE_2 (Ensuring Safe to Cook) */
  { MicrowaveOven_MO_O_STATE_2, MicrowaveOven_MO_O_STATE_1, EVENT_IS_IGNORED, EVENT_IS_IGNORED, MicrowaveOven_MO_O_STATE_3, EVENT_IS_IGNORED },
  /* row 3:  MicrowaveOven_MO_O_STATE_3 (Cooking) */
  { EVENT_IS_IGNORED, MicrowaveOven_MO_O_STATE_4, MicrowaveOven_MO_O_STATE_5, EVENT_IS_IGNORED, EVENT_IS_IGNORED, EVENT_IS_IGNORED },
  /* row 4:  MicrowaveOven_MO_O_STATE_4 (Cooking Suspended) */
  { MicrowaveOven_MO_O_STATE_2, MicrowaveOven_MO_O_STATE_1, EVENT_IS_IGNORED, EVENT_IS_IGNORED, EVENT_IS_IGNORED, EVENT_IS_IGNORED },
  /* row 5:  MicrowaveOven_MO_O_STATE_5 (Signalling Cooking Period Over) */
  { EVENT_IS_IGNORED, MicrowaveOven_MO_O_STATE_1, EVENT_IS_IGNORED, MicrowaveOven_MO_O_STATE_1, EVENT_IS_IGNORED, EVENT_IS_IGNORED },
  /* row 6:  MicrowaveOven_MO_O_STATE_6 (Assigning Cooking Period) */
  { MicrowaveOven_MO_O_STATE_2, EVENT_IS_IGNORED, EVENT_IS_IGNORED, EVENT_IS_IGNORED, EVENT_IS_IGNORED, EVENT_IS_IGNORED }
};

  /*
   * Array of pointers to the class state action procedures.
   * Index is the (MC enumerated) number of the state action to execute.
   */
  static const StateAction_t MicrowaveOven_MO_O_acts[ 7 ] = {
    (StateAction_t) 0,
    (StateAction_t) MicrowaveOven_MO_O_act1,  /* Awaiting Cooking Request */
    (StateAction_t) MicrowaveOven_MO_O_act2,  /* Ensuring Safe to Cook */
    (StateAction_t) MicrowaveOven_MO_O_act3,  /* Cooking */
    (StateAction_t) MicrowaveOven_MO_O_act4,  /* Cooking Suspended */
    (StateAction_t) MicrowaveOven_MO_O_act5,  /* Signalling Cooking Period Over */
    (StateAction_t) MicrowaveOven_MO_O_act6  /* Assigning Cooking Period */
  };

  /*
   * Array of string names of the state machine names.
   * Index is the (MC enumerated) number of the state.
   */
  static const c_t * const state_name_strings[ 7 ] = {
    "",
    "Awaiting Cooking Request",
    "Ensuring Safe to Cook",
    "Cooking",
    "Cooking Suspended",
    "Signalling Cooking Period Over",
    "Assigning Cooking Period"
  };

/*
 * instance state machine event dispatching
 */
void
MicrowaveOven_MO_O_Dispatch( Escher_xtUMLEvent_t * event )
{
  Escher_iHandle_t instance = GetEventTargetInstance( event );
  Escher_EventNumber_t event_number = GetOoaEventNumber( event );
  Escher_StateNumber_t current_state;
  Escher_StateNumber_t next_state;
  
  if ( 0 != instance ) {
    current_state = instance->current_state;
    if ( current_state > 6 ) {
      /* instance validation failure (object deleted while event in flight) */
      UserEventNoInstanceCallout( event_number );
    } else {
      next_state = MicrowaveOven_MO_O_StateEventMatrix[ current_state ][ event_number ];
      if ( next_state <= 6 ) {
        STATE_TXN_START_TRACE( "MO_O", current_state, state_name_strings[ current_state ] );
        /* Execute the state action and update the current state.  */
        ( *MicrowaveOven_MO_O_acts[ next_state ] )( instance, event );
        STATE_TXN_END_TRACE( "MO_O", next_state, state_name_strings[ next_state ] );
        instance->current_state = next_state;
      } else if ( next_state == EVENT_CANT_HAPPEN ) {
          /* event cant happen */
          UserEventCantHappenCallout( current_state, next_state, event_number );
          STATE_TXN_CH_TRACE( "MO_O", current_state );
      } else if ( next_state == EVENT_IS_IGNORED ) {
          /* event ignored */
          STATE_TXN_IG_TRACE( "MO_O", current_state );
      } else {
        /* empty else */
      }
    }
  }
}


