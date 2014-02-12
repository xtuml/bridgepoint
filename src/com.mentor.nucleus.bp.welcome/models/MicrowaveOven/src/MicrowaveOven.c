/*----------------------------------------------------------------------------
 * File:  MicrowaveOven.c
 *
 * UML Component Port Messages
 * Component/Module Name:  MicrowaveOven
 *
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/
/*
 

Domain Mission: Microwave Application Domain

To capture the retained data and event sequencing associated with the use of a domestic microwave oven comprising :

* a magnetron tube (with variable power output settings)
* a turntable
* an internal light
* a beeper to signal when cooking is complete
* an oven door

Bridges to other Domains:

* To Control Panel (Application User Interface Domain)
* To PIO domain (not shown)
* To Timer domain



 */

#include "MicrowaveOven_sys_types.h"
#include "TIM_bridge.h"
#include "MicrowaveOven_ARCH_bridge.h"
#include "MicrowaveOven_classes.h"

/*
 * UML Domain Functions (Synchronous Services)
 */


/*
 * Domain Function:  StartCooking
 */
void
MicrowaveOven_StartCooking()
{
  MicrowaveOven_MO_O * oven; 
  /* SELECT any oven FROM INSTANCES OF MO_O */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any oven FROM INSTANCES OF MO_O" );
  oven = (MicrowaveOven_MO_O *) Escher_SetGetAny( &pG_MicrowaveOven_MO_O_extent.active );
  /* GENERATE MO_O3:start_cooking() TO oven */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_O3:start_cooking() TO oven" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( oven, &MicrowaveOven_MO_Oevent3c );
    Escher_SendEvent( e );
  }

}


/*
 * Domain Function:  CancelCooking
 */
void
MicrowaveOven_CancelCooking()
{
  MicrowaveOven_MO_O * oven; 
  /* SELECT any oven FROM INSTANCES OF MO_O */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any oven FROM INSTANCES OF MO_O" );
  oven = (MicrowaveOven_MO_O *) Escher_SetGetAny( &pG_MicrowaveOven_MO_O_extent.active );
  /* GENERATE MO_O4:cancel_cooking() TO oven */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_O4:cancel_cooking() TO oven" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( oven, &MicrowaveOven_MO_Oevent4c );
    Escher_SendEvent( e );
  }

}


/*
 * Domain Function:  SpecifyCookingPeriod
 */
void
MicrowaveOven_SpecifyCookingPeriod( i_t p_cookingPeriod)
{
  i_t timePeriod; MicrowaveOven_MO_O * oven; 
  /* ASSIGN timePeriod = ( 1000000 * PARAM.cookingPeriod ) */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN timePeriod = ( 1000000 * PARAM.cookingPeriod )" );
  timePeriod = ( 1000000 * p_cookingPeriod );
  /* SELECT any oven FROM INSTANCES OF MO_O */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any oven FROM INSTANCES OF MO_O" );
  oven = (MicrowaveOven_MO_O *) Escher_SetGetAny( &pG_MicrowaveOven_MO_O_extent.active );
  /* GENERATE MO_O8:cooking_period(period:timePeriod) TO oven */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_O8:cooking_period(period:timePeriod) TO oven" );
  { MicrowaveOven_MO_Oevent8 * e = (MicrowaveOven_MO_Oevent8 *) Escher_NewxtUMLEvent( oven, &MicrowaveOven_MO_Oevent8c );
    e->p_period = timePeriod;
    Escher_SendEvent( (Escher_xtUMLEvent_t *) e );
  }

}


/*
 * Domain Function:  IncreasePower
 */
void
MicrowaveOven_IncreasePower()
{
  MicrowaveOven_MO_MT * tube; 
  /* SELECT any tube FROM INSTANCES OF MO_MT */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any tube FROM INSTANCES OF MO_MT" );
  tube = (MicrowaveOven_MO_MT *) Escher_SetGetAny( &pG_MicrowaveOven_MO_MT_extent.active );
  /* GENERATE MO_MT1:increase_power() TO tube */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_MT1:increase_power() TO tube" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( tube, &MicrowaveOven_MO_MTevent1c );
    Escher_SendEvent( e );
  }

}


/*
 * Domain Function:  DecreasePower
 */
void
MicrowaveOven_DecreasePower()
{
  MicrowaveOven_MO_MT * tube; 
  /* SELECT any tube FROM INSTANCES OF MO_MT */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any tube FROM INSTANCES OF MO_MT" );
  tube = (MicrowaveOven_MO_MT *) Escher_SetGetAny( &pG_MicrowaveOven_MO_MT_extent.active );
  /* GENERATE MO_MT2:decrease_power() TO tube */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_MT2:decrease_power() TO tube" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( tube, &MicrowaveOven_MO_MTevent2c );
    Escher_SendEvent( e );
  }

}


/*
 * Domain Function:  OpenDoor
 */
void
MicrowaveOven_OpenDoor()
{
  MicrowaveOven_MO_D * door; 
  /* SELECT any door FROM INSTANCES OF MO_D */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any door FROM INSTANCES OF MO_D" );
  door = (MicrowaveOven_MO_D *) Escher_SetGetAny( &pG_MicrowaveOven_MO_D_extent.active );
  /* GENERATE MO_D1:release() TO door */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_D1:release() TO door" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( door, &MicrowaveOven_MO_Devent1c );
    Escher_SendEvent( e );
  }

}


/*
 * Domain Function:  CloseDoor
 */
void
MicrowaveOven_CloseDoor()
{
  MicrowaveOven_MO_D * door; 
  /* SELECT any door FROM INSTANCES OF MO_D */
  XTUML_OAL_STMT_TRACE( 1, "SELECT any door FROM INSTANCES OF MO_D" );
  door = (MicrowaveOven_MO_D *) Escher_SetGetAny( &pG_MicrowaveOven_MO_D_extent.active );
  /* GENERATE MO_D2:close() TO door */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_D2:close() TO door" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( door, &MicrowaveOven_MO_Devent2c );
    Escher_SendEvent( e );
  }

}


/*
 * Domain Function:  TestSequence1
 */
void
MicrowaveOven_TestSequence1()
{
  MicrowaveOven_MO_TS * testSequence; 
  /* CREATE OBJECT INSTANCE testSequence OF MO_TS */
  XTUML_OAL_STMT_TRACE( 1, "CREATE OBJECT INSTANCE testSequence OF MO_TS" );
  testSequence = (MicrowaveOven_MO_TS *) Escher_CreateInstance( MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_TS_CLASS_NUMBER );
  testSequence->TestSeqID = (Escher_UniqueID_t) testSequence;
  /* GENERATE MO_TS2:perform_test_seq_1() TO testSequence */
  XTUML_OAL_STMT_TRACE( 1, "GENERATE MO_TS2:perform_test_seq_1() TO testSequence" );
  { Escher_xtUMLEvent_t * e = Escher_NewxtUMLEvent( testSequence, &MicrowaveOven_MO_TSevent2c );
    Escher_SendEvent( e );
  }

}


/*
 * Domain Function:  DefineOven
 */
void
MicrowaveOven_DefineOven()
{
  MicrowaveOven_MO_O * mo; MicrowaveOven_MO_MT * tube; MicrowaveOven_MO_IL * light; MicrowaveOven_MO_B * beeper; MicrowaveOven_MO_D * door; MicrowaveOven_MO_TRN * turntable; 
  /* CREATE OBJECT INSTANCE mo OF MO_O */
  XTUML_OAL_STMT_TRACE( 1, "CREATE OBJECT INSTANCE mo OF MO_O" );
  mo = (MicrowaveOven_MO_O *) Escher_CreateInstance( MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_O_CLASS_NUMBER );
  mo->OvenID = (Escher_UniqueID_t) mo;
  /* ASSIGN mo.remaining_cooking_time = 0 */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN mo.remaining_cooking_time = 0" );
  mo->remaining_cooking_time = 0;
  /* CREATE OBJECT INSTANCE tube OF MO_MT */
  XTUML_OAL_STMT_TRACE( 1, "CREATE OBJECT INSTANCE tube OF MO_MT" );
  tube = (MicrowaveOven_MO_MT *) Escher_CreateInstance( MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_MT_CLASS_NUMBER );
  tube->TubeID = (Escher_UniqueID_t) tube;
  /* RELATE mo TO tube ACROSS R1 */
  XTUML_OAL_STMT_TRACE( 1, "RELATE mo TO tube ACROSS R1" );
  MicrowaveOven_MO_O_R1_Link( tube, mo );
  /* ASSIGN tube.current_power_output = high */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN tube.current_power_output = high" );
  tube->current_power_output = MicrowaveOven_tube_wattage_high_e;
  /* CREATE OBJECT INSTANCE light OF MO_IL */
  XTUML_OAL_STMT_TRACE( 1, "CREATE OBJECT INSTANCE light OF MO_IL" );
  light = (MicrowaveOven_MO_IL *) Escher_CreateInstance( MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_IL_CLASS_NUMBER );
  light->LightID = (Escher_UniqueID_t) light;
  /* RELATE mo TO light ACROSS R2 */
  XTUML_OAL_STMT_TRACE( 1, "RELATE mo TO light ACROSS R2" );
  MicrowaveOven_MO_O_R2_Link( light, mo );
  /* CREATE OBJECT INSTANCE beeper OF MO_B */
  XTUML_OAL_STMT_TRACE( 1, "CREATE OBJECT INSTANCE beeper OF MO_B" );
  beeper = (MicrowaveOven_MO_B *) Escher_CreateInstance( MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_B_CLASS_NUMBER );
  beeper->BeeperID = (Escher_UniqueID_t) beeper;
  /* RELATE mo TO beeper ACROSS R3 */
  XTUML_OAL_STMT_TRACE( 1, "RELATE mo TO beeper ACROSS R3" );
  MicrowaveOven_MO_O_R3_Link( beeper, mo );
  /* CREATE OBJECT INSTANCE door OF MO_D */
  XTUML_OAL_STMT_TRACE( 1, "CREATE OBJECT INSTANCE door OF MO_D" );
  door = (MicrowaveOven_MO_D *) Escher_CreateInstance( MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_D_CLASS_NUMBER );
  door->DoorID = (Escher_UniqueID_t) door;
  /* RELATE mo TO door ACROSS R4 */
  XTUML_OAL_STMT_TRACE( 1, "RELATE mo TO door ACROSS R4" );
  MicrowaveOven_MO_O_R4_Link( door, mo );
  /* ASSIGN door.is_secure = FALSE */
  XTUML_OAL_STMT_TRACE( 1, "ASSIGN door.is_secure = FALSE" );
  door->is_secure = FALSE;
  /* CREATE OBJECT INSTANCE turntable OF MO_TRN */
  XTUML_OAL_STMT_TRACE( 1, "CREATE OBJECT INSTANCE turntable OF MO_TRN" );
  turntable = (MicrowaveOven_MO_TRN *) Escher_CreateInstance( MicrowaveOven_DOMAIN_ID, MicrowaveOven_MO_TRN_CLASS_NUMBER );
  turntable->TurntableID = (Escher_UniqueID_t) turntable;
  /* RELATE mo TO turntable ACROSS R5 */
  XTUML_OAL_STMT_TRACE( 1, "RELATE mo TO turntable ACROSS R5" );
  MicrowaveOven_MO_O_R5_Link( turntable, mo );

}

#if MicrowaveOven_MAX_CLASS_NUMBERS > 0
/* xtUML class info (collections, sizes, etc.) */
Escher_Extent_t * const MicrowaveOven_class_info[ MicrowaveOven_MAX_CLASS_NUMBERS ] = {
  MicrowaveOven_CLASS_INFO_INIT
};
#endif

/*
 * Array of pointers to the class event dispatcher method.
 * Index is the (model compiler enumerated) number of the state model.
 */
const EventTaker_t MicrowaveOven_EventDispatcher[ MicrowaveOven_STATE_MODELS ] = {
  MicrowaveOven_class_dispatchers
};

void MicrowaveOven_execute_initialization()
{
  /*
   * Initialization Function:  DefineOven
   * Component:  MicrowaveOven
   */
  MicrowaveOven_DefineOven();

  /*
   * Initialization Function:  TestSequence1
   * Component:  MicrowaveOven
   */
  MicrowaveOven_TestSequence1();

}
