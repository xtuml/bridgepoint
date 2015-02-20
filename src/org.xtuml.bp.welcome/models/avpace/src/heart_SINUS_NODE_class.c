/*----------------------------------------------------------------------------
 * File:  heart_SINUS_NODE_class.c
 *
 * Class:       sinus node  (SINUS_NODE)
 * Component:   heart
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "avpace_sys_types.h"
#include "heart_LOG_bridge.h"
#include "TIM_bridge.h"
#include "heart_classes.h"

/*
 * instance operation:  log_and_adjust
 */
void
heart_SINUS_NODE_op_log_and_adjust( heart_SINUS_NODE * self)
{
  /* LOG::LogInfo( message:systolic period, diastolic period ) */
  XTUML_OAL_STMT_TRACE( 1, "LOG::LogInfo( message:systolic period, diastolic period )" );
  heart_LOG_LogInfo( "systolic period, diastolic period" );
  /* LOG::LogInteger( message:self.systolic_period ) */
  XTUML_OAL_STMT_TRACE( 1, "LOG::LogInteger( message:self.systolic_period )" );
  heart_LOG_LogInteger( self->systolic_period );
  /* LOG::LogInteger( message:self.diastolic_period ) */
  XTUML_OAL_STMT_TRACE( 1, "LOG::LogInteger( message:self.diastolic_period )" );
  heart_LOG_LogInteger( self->diastolic_period );
  /* IF ( ( self.decrement < self.systolic_period ) ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( ( self.decrement < self.systolic_period ) )" );
  if ( ( self->decrement < self->systolic_period ) ) {
    /* ASSIGN self.systolic_period = ( self.systolic_period - self.decrement ) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN self.systolic_period = ( self.systolic_period - self.decrement )" );
    self->systolic_period = ( self->systolic_period - self->decrement );
  }
  /* IF ( ( self.decrement < self.diastolic_period ) ) */
  XTUML_OAL_STMT_TRACE( 1, "IF ( ( self.decrement < self.diastolic_period ) )" );
  if ( ( self->decrement < self->diastolic_period ) ) {
    /* ASSIGN self.diastolic_period = ( self.diastolic_period - self.decrement ) */
    XTUML_OAL_STMT_TRACE( 2, "ASSIGN self.diastolic_period = ( self.diastolic_period - self.decrement )" );
    self->diastolic_period = ( self->diastolic_period - self->decrement );
  }

}


/*
 * RELATE HEART TO SINUS_NODE ACROSS R1
 */
void
heart_SINUS_NODE_R1_Link_is_triggered_by( heart_HEART * part, heart_SINUS_NODE * form )
{
  if ( (part == 0) || (form == 0) ) {
    XTUML_EMPTY_HANDLE_TRACE( "SINUS_NODE", "heart_SINUS_NODE_R1_Link_is_triggered_by" );
    return;
  }
  form->HEART_R1_triggers = part;
  /* Note:  HEART->SINUS_NODE[R1] not navigated */
}


/*----------------------------------------------------------------------------
 * Operation action methods implementation for the following class:
 *
 * Class:      sinus node  (SINUS_NODE)
 * Component:  heart
 *--------------------------------------------------------------------------*/
/*
 * Statically allocate space for the instance population for this class.
 * Allocate space for the class instance and its attribute values.
 * Depending upon the collection scheme, allocate containoids (collection
 * nodes) for gathering instances into free and active extents.
 */
static Escher_SetElement_s heart_SINUS_NODE_container[ heart_SINUS_NODE_MAX_EXTENT_SIZE ];
static heart_SINUS_NODE heart_SINUS_NODE_instances[ heart_SINUS_NODE_MAX_EXTENT_SIZE ];
Escher_Extent_t pG_heart_SINUS_NODE_extent = {
  {0}, {0}, &heart_SINUS_NODE_container[ 0 ],
  (Escher_iHandle_t) &heart_SINUS_NODE_instances,
  sizeof( heart_SINUS_NODE ), heart_SINUS_NODE_STATE_1, heart_SINUS_NODE_MAX_EXTENT_SIZE
  };
/*----------------------------------------------------------------------------
 * State and transition action implementations for the following class:
 *
 * Class:      sinus node  (SINUS_NODE)
 * Component:  heart
 *--------------------------------------------------------------------------*/

/*
 * State 3:  [pulsing systolic]
 */
static void heart_SINUS_NODE_act3( heart_SINUS_NODE *, const Escher_xtUMLEvent_t * const );
static void
heart_SINUS_NODE_act3( heart_SINUS_NODE * self, const Escher_xtUMLEvent_t * const event )
{
  Escher_Timer_t * t;Escher_xtUMLEvent_t * e;heart_HEART * heart=0;
  /* SELECT one heart RELATED BY self->HEART[R1] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one heart RELATED BY self->HEART[R1]" );
  heart = ( 0 != self ) ? self->HEART_R1_triggers : 0;
  /* GENERATE HEART5:systolic_pulse() TO heart */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE HEART5:systolic_pulse() TO heart" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( heart, &heart_HEARTevent5c );
    Escher_SendEvent( e );
  }
  /* SEND out_to_body::systolic_pulse() */
  XTUML_OAL_STMT_TRACE( 1, "SEND out_to_body::systolic_pulse()" );
  heart_out_to_body_systolic_pulse();
  /* CREATE EVENT INSTANCE e(  ) TO self */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE e(  ) TO self" );
  e = Escher_NewxtUMLEvent( (void *) self, &heart_SINUS_NODEevent1c );
  /* ASSIGN t = TIM::timer_start(event_inst:e, microseconds:self.diastolic_period) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN t = TIM::timer_start(event_inst:e, microseconds:self.diastolic_period)" );
  t = TIM_timer_start( (Escher_xtUMLEvent_t *)e, self->diastolic_period );
  /* self.log_and_adjust() */
  XTUML_OAL_STMT_TRACE( 1, "self.log_and_adjust()" );
  heart_SINUS_NODE_op_log_and_adjust( self );
}

/*
 * State 1:  [pulsing diastolic]
 */
static void heart_SINUS_NODE_act1( heart_SINUS_NODE *, const Escher_xtUMLEvent_t * const );
static void
heart_SINUS_NODE_act1( heart_SINUS_NODE * self, const Escher_xtUMLEvent_t * const event )
{
  Escher_Timer_t * t;Escher_xtUMLEvent_t * e;heart_HEART * heart=0;
  /* SELECT one heart RELATED BY self->HEART[R1] */
  XTUML_OAL_STMT_TRACE( 1, "SELECT one heart RELATED BY self->HEART[R1]" );
  heart = ( 0 != self ) ? self->HEART_R1_triggers : 0;
  /* GENERATE HEART4:diastolic_pulse() TO heart */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE HEART4:diastolic_pulse() TO heart" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( heart, &heart_HEARTevent4c );
    Escher_SendEvent( e );
  }
  /* SEND out_to_body::diastolic_pulse() */
  XTUML_OAL_STMT_TRACE( 1, "SEND out_to_body::diastolic_pulse()" );
  heart_out_to_body_diastolic_pulse();
  /* CREATE EVENT INSTANCE e(  ) TO self */
  XTUML_OAL_STMT_TRACE( 1, "CREATE EVENT INSTANCE e(  ) TO self" );
  e = Escher_NewxtUMLEvent( (void *) self, &heart_SINUS_NODEevent2c );
  /* ASSIGN t = TIM::timer_start(event_inst:e, microseconds:self.systolic_period) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN t = TIM::timer_start(event_inst:e, microseconds:self.systolic_period)" );
  t = TIM_timer_start( (Escher_xtUMLEvent_t *)e, self->systolic_period );
}

const Escher_xtUMLEventConstant_t heart_SINUS_NODEevent1c = {
  heart_DOMAIN_ID, heart_SINUS_NODE_CLASS_NUMBER, HEART_SINUS_NODEEVENT1NUM,
  ESCHER_IS_INSTANCE_EVENT };

const Escher_xtUMLEventConstant_t heart_SINUS_NODEevent2c = {
  heart_DOMAIN_ID, heart_SINUS_NODE_CLASS_NUMBER, HEART_SINUS_NODEEVENT2NUM,
  ESCHER_IS_INSTANCE_EVENT };



/*
 * State-Event Matrix (SEM)
 * Row index is object (MC enumerated) current state.
 * Row zero is the uninitialized state (e.g., for creation event transitions).
 * Column index is (MC enumerated) state machine events.
 */
static const Escher_SEMcell_t heart_SINUS_NODE_StateEventMatrix[ 2 + 1 ][ 2 ] = {
  /* row 0:  uninitialized state (for creation events) */
  { EVENT_CANT_HAPPEN, EVENT_CANT_HAPPEN },
  /* row 1:  heart_SINUS_NODE_STATE_1 (pulsing diastolic) */
  { EVENT_CANT_HAPPEN, heart_SINUS_NODE_STATE_3 },
  /* row 2:  heart_SINUS_NODE_STATE_3 (pulsing systolic) */
  { heart_SINUS_NODE_STATE_1, EVENT_CANT_HAPPEN }
};

  /*
   * Array of pointers to the class state action procedures.
   * Index is the (MC enumerated) number of the state action to execute.
   */
  static const StateAction_t heart_SINUS_NODE_acts[ 3 ] = {
    (StateAction_t) 0,
    (StateAction_t) heart_SINUS_NODE_act1,  /* pulsing diastolic */
    (StateAction_t) heart_SINUS_NODE_act3  /* pulsing systolic */
  };

  /*
   * Array of string names of the state machine names.
   * Index is the (MC enumerated) number of the state.
   */
  static const c_t * const state_name_strings[ 3 ] = {
    "",
    "pulsing diastolic",
    "pulsing systolic"
  };

/*
 * instance state machine event dispatching
 */
void
heart_SINUS_NODE_Dispatch( Escher_xtUMLEvent_t * event )
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
      next_state = heart_SINUS_NODE_StateEventMatrix[ current_state ][ event_number ];
      if ( next_state <= 2 ) {
        STATE_TXN_START_TRACE( "SINUS_NODE", current_state, state_name_strings[ current_state ] );
        /* Execute the state action and update the current state.  */
        ( *heart_SINUS_NODE_acts[ next_state ] )( instance, event );
        STATE_TXN_END_TRACE( "SINUS_NODE", next_state, state_name_strings[ next_state ] );
        instance->current_state = next_state;
      } else if ( next_state == EVENT_CANT_HAPPEN ) {
          /* event cant happen */
          UserEventCantHappenCallout( current_state, next_state, event_number );
          STATE_TXN_CH_TRACE( "SINUS_NODE", current_state );
      } else {
        /* empty else */
      }
    }
  }
}


