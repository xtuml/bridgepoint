/*----------------------------------------------------------------------------
 * File:  Location_GPS_class.h
 *
 * Class:       GPS  (GPS)
 * Component:   Location
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef LOCATION_GPS_CLASS_H
#define LOCATION_GPS_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   GPS  (GPS)
 */
struct Location_GPS {

  /* application analysis class attributes */
  GPSWatch_sdt_Location currentLocation;  /* - currentLocation */
  Escher_Timer_t * timer;  /* - timer */

};



#define Location_GPS_MAX_EXTENT_SIZE 200
extern Escher_Extent_t pG_Location_GPS_extent;

/*
 * class-based event:  GPS_A1:'timeout'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} Location_GPS_CBevent1;
extern const Escher_xtUMLEventConstant_t Location_GPS_CBevent1c;

/*
 * class-based event:  LOC::registerListener:'registerListener'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} Location_GPS_CBevent2;
extern const Escher_xtUMLEventConstant_t Location_GPS_CBevent2c;

/*
 * class-based event:  LOC::unregisterListener:'unregisterListener'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} Location_GPS_CBevent3;
extern const Escher_xtUMLEventConstant_t Location_GPS_CBevent3c;

/*
 * union of events targeted towards 'GPS' state machine
 */
typedef union {
  Location_GPS_CBevent1 gps11;  
  Location_GPS_CBevent2 gps22;  
  Location_GPS_CBevent3 gps33;  
} Location_GPS_CB_Events_u;

/*
 * enumeration of state model states for class
 */
#define Location_GPS_CB_STATE_1 1  /* state [1]:  (idle) */
#define Location_GPS_CB_STATE_2 2  /* state [2]:  (locating) */

/*
 * enumeration of state model event numbers
 */
#define LOCATION_GPS_CBEVENT1NUM 0  /* GPS_A1:'timeout' */
#define LOCATION_GPS_CBEVENT2NUM 1  /* LOC::registerListener:'registerListener' */
#define LOCATION_GPS_CBEVENT3NUM 2  /* LOC::unregisterListener:'unregisterListener' */

extern void Location_GPS_CBDispatch( Escher_xtUMLEvent_t * );


#ifdef	__cplusplus
}
#endif

#endif  /* LOCATION_GPS_CLASS_H */


