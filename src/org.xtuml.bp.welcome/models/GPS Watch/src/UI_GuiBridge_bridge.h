/*----------------------------------------------------------------------------
 * File:  UI_GuiBridge_bridge.h
 *
 * Description:
 * Methods for bridging to an external entity.
 *
 * External Entity:  Graphical User Interface (GuiBridge)
 * 
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef UI_GUIBRIDGE_BRIDGE_H
#define UI_GUIBRIDGE_BRIDGE_H
#ifdef	__cplusplus
extern	"C"	{
#endif

#include "GPSWatch_sys_types.h"

void UI_GuiBridge_feedSetTargetPressedEvent( Escher_xtUMLEvent_t * );
void UI_GuiBridge_feedModePressedEvent( Escher_xtUMLEvent_t * );
void UI_GuiBridge_feedLightPressedEvent( Escher_xtUMLEvent_t * );
void UI_GuiBridge_feedLapResetPressedEvent( Escher_xtUMLEvent_t * );
void UI_GuiBridge_feedStartStopPressedEvent( Escher_xtUMLEvent_t * );
void UI_GuiBridge_setData( const i_t, const r_t );
void UI_GuiBridge_setTime( const i_t );
void UI_GuiBridge_connect( void );
void UI_GuiBridge_sendModePressed( void );
void UI_GuiBridge_sendLightPressed( void );
void UI_GuiBridge_sendLapResetPressed( void );
void UI_GuiBridge_sendStartStopPressed( void );
void UI_GuiBridge_sendTargetPressed( void );

#ifdef	__cplusplus
}
#endif
#endif   /* UI_GUIBRIDGE_BRIDGE_H */
