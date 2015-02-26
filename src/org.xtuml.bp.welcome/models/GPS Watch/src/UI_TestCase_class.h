/*----------------------------------------------------------------------------
 * File:  UI_TestCase_class.h
 *
 * Class:       TestCase  (TestCase)
 * Component:   UI
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef UI_TESTCASE_CLASS_H
#define UI_TESTCASE_CLASS_H

#ifdef	__cplusplus
extern	"C"	{
#endif

/*
 * Structural representation of application analysis class:
 *   TestCase  (TestCase)
 */
struct UI_TestCase {
  Escher_StateNumber_t current_state;
  /* application analysis class attributes */
  i_t iterations;  /* - iterations */

};
void UI_TestCase_op_execute( void );



#define UI_TestCase_MAX_EXTENT_SIZE 200
extern Escher_Extent_t pG_UI_TestCase_extent;

/*
 * instance event:  TestCase1:'delay'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} UI_TestCaseevent1;
extern const Escher_xtUMLEventConstant_t UI_TestCaseevent1c;

/*
 * creation event:  TestCase2:'start'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  i_t p_iterations; /* iterations */
} UI_TestCaseevent2;
extern const Escher_xtUMLEventConstant_t UI_TestCaseevent2c;

/*
 * instance event:  TestCase3:'finish'
 */
typedef struct {
  EVENT_BASE_ATTRIBUTE_LIST         /* base attributes of all event classes */
  /* Note:  no supplemental data for this event */
} UI_TestCaseevent3;
extern const Escher_xtUMLEventConstant_t UI_TestCaseevent3c;

/*
 * union of events targeted towards 'TestCase' state machine
 */
typedef union {
  UI_TestCaseevent1 testcase11;  
  UI_TestCaseevent2 testcase22;  
  UI_TestCaseevent3 testcase33;  
} UI_TestCase_Events_u;

/*
 * enumeration of state model states for class
 */
#define UI_TestCase_STATE_2 1  /* state [2]:  (pressStartStop) */
#define UI_TestCase_STATE_3 2  /* state [3]:  (testCaseFinished) */
/*
 * enumeration of state model event numbers
 */
#define UI_TESTCASEEVENT1NUM 0  /* TestCase1:'delay' */
#define UI_TESTCASEEVENT2NUM 1  /* TestCase2:'start' */
#define UI_TESTCASEEVENT3NUM 2  /* TestCase3:'finish' */
extern void UI_TestCase_Dispatch( Escher_xtUMLEvent_t * );

#ifdef	__cplusplus
}
#endif

#endif  /* UI_TESTCASE_CLASS_H */


