/*----------------------------------------------------------------------------
 * File:  UI_UI_class.h
 *
 * Class:       UI  (UI)
 * Component:   UI
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef UI_UI_CLASS_H
#define UI_UI_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   UI  (UI)
 */
struct UI_UI {

  /* application analysis class attributes */

};
void UI_UI_op_connect( void );



#define UI_UI_MAX_EXTENT_SIZE 200
extern Escher_Extent_t pG_UI_UI_extent;

/*
 * class-based event:  UI_A3:'setTargetPressed'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} UI_UI_CBevent3;
extern const Escher_xtUMLEventConstant_t UI_UI_CBevent3c;

/*
 * class-based event:  UI_A4:'startStopPressed'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} UI_UI_CBevent4;
extern const Escher_xtUMLEventConstant_t UI_UI_CBevent4c;

/*
 * class-based event:  UI_A5:'lapResetPressed'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} UI_UI_CBevent5;
extern const Escher_xtUMLEventConstant_t UI_UI_CBevent5c;

/*
 * class-based event:  UI_A6:'lightPressed'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} UI_UI_CBevent6;
extern const Escher_xtUMLEventConstant_t UI_UI_CBevent6c;

/*
 * class-based event:  UI_A7:'modePressed'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} UI_UI_CBevent7;
extern const Escher_xtUMLEventConstant_t UI_UI_CBevent7c;

/*
 * union of events targeted towards 'UI' state machine
 */
typedef union {
  UI_UI_CBevent3 ui31;  
  UI_UI_CBevent4 ui42;  
  UI_UI_CBevent5 ui53;  
  UI_UI_CBevent6 ui64;  
  UI_UI_CBevent7 ui75;  
} UI_UI_CB_Events_u;

/*
 * enumeration of state model states for class
 */
#define UI_UI_CB_STATE_1 1  /* state [1]:  (running) */

/*
 * enumeration of state model event numbers
 */
#define UI_UI_CBEVENT3NUM 0  /* UI_A3:'setTargetPressed' */
#define UI_UI_CBEVENT4NUM 1  /* UI_A4:'startStopPressed' */
#define UI_UI_CBEVENT5NUM 2  /* UI_A5:'lapResetPressed' */
#define UI_UI_CBEVENT6NUM 3  /* UI_A6:'lightPressed' */
#define UI_UI_CBEVENT7NUM 4  /* UI_A7:'modePressed' */

extern void UI_UI_CBDispatch( Escher_xtUMLEvent_t * );


#ifdef	__cplusplus
}
#endif

#endif  /* UI_UI_CLASS_H */


