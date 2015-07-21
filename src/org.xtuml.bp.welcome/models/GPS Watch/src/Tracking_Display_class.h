/*----------------------------------------------------------------------------
 * File:  Tracking_Display_class.h
 *
 * Class:       Display  (Display)
 * Component:   Tracking
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef TRACKING_DISPLAY_CLASS_H
#define TRACKING_DISPLAY_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   Display  (Display)
 */
struct Tracking_Display {

  /* application analysis class attributes */

};



#define Tracking_Display_MAX_EXTENT_SIZE 200
extern Escher_Extent_t pG_Tracking_Display_extent;

/*
 * class-based event:  UI::modePressed:'modePressed'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} Tracking_Display_CBevent1;
extern const Escher_xtUMLEventConstant_t Tracking_Display_CBevent1c;

/*
 * class-based event:  Display_A2:'refresh'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} Tracking_Display_CBevent2;
extern const Escher_xtUMLEventConstant_t Tracking_Display_CBevent2c;

/*
 * union of events targeted towards 'Display' state machine
 */
typedef union {
  Tracking_Display_CBevent1 display11;  
  Tracking_Display_CBevent2 display22;  
} Tracking_Display_CB_Events_u;

/*
 * enumeration of state model states for class
 */
#define Tracking_Display_CB_STATE_1 1  /* state [1]:  (displayDistance) */
#define Tracking_Display_CB_STATE_2 2  /* state [2]:  (displaySpeed) */
#define Tracking_Display_CB_STATE_3 3  /* state [3]:  (displayPace) */
#define Tracking_Display_CB_STATE_4 4  /* state [4]:  (displayHeartRate) */
#define Tracking_Display_CB_STATE_5 5  /* state [5]:  (displayLapCount) */

/*
 * enumeration of state model event numbers
 */
#define TRACKING_DISPLAY_CBEVENT2NUM 0  /* Display_A2:'refresh' */
#define TRACKING_DISPLAY_CBEVENT1NUM 1  /* UI::modePressed:'modePressed' */

extern void Tracking_Display_CBDispatch( Escher_xtUMLEvent_t * );


#ifdef	__cplusplus
}
#endif

#endif  /* TRACKING_DISPLAY_CLASS_H */


