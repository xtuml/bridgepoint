/*----------------------------------------------------------------------------
 * File:  UI.h
 *
 * UML Component (Module) Declaration (Operations and Signals)
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef UI_H
#define UI_H
#ifdef	__cplusplus
extern	"C"	{
#endif
/*
Simulates the user interface and has the ability to connect an external GUI.

It is using the BridgePoint Java API to connect to the GUI in Verifier mode.
There are also handwritten C code that implments parts of this component to 
allow generated code to connect to the exact same GUI.
*/

#include "GPSWatch_sys_types.h"
void UI_UI_lapResetPressed( void );
void UI_UI_lightPressed( void );
void UI_UI_modePressed( void );
void UI_UI_setData( const GPSWatch_Unit_t, const r_t );
void UI_UI_setTargetPressed( void );
void UI_UI_setTime( const i_t );
void UI_UI_startStopPressed( void );
void UI_UI_startTest( void );


#ifdef	__cplusplus
}
#endif
#endif  /* UI_H */
