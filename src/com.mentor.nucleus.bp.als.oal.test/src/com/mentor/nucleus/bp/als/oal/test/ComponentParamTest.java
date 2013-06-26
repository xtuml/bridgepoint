package com.mentor.nucleus.bp.als.oal.test;

import java.io.StringReader;
import java.util.UUID;

import org.eclipse.jface.preference.IPreferenceStore;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.TokenStreamRecognitionException;

import com.mentor.nucleus.bp.als.oal.OalLexer;
import com.mentor.nucleus.bp.als.oal.OalParser;
import com.mentor.nucleus.bp.als.oal.Oal_validate;
import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DomainAsComponent_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.MooreActionHome_c;
import com.mentor.nucleus.bp.core.Oalconstants_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.TransitionActionHome_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.util.DomainUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;

public class ComponentParamTest extends BaseTest {

  private static String m_workspace_path = ""; //$NON-NLS-1$
  private static String m_comp_pkg_name = "ComponentSyntaxTest"; //$NON-NLS-1$
  public ComponentParamTest() {
  	super("Models", null);
  }
	
  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();

    if (m_workspace_path.equals(""))//$NON-NLS-1$
    {
      m_workspace_path = System.getProperty("WORKSPACE_PATH");//$NON-NLS-1$
    }
    assertNotNull( m_workspace_path );
    if (m_logfile_path == null || m_logfile_path.equals(""))
    {
      m_logfile_path = System.getProperty("LOGFILE_PATH");
    }
    assertNotNull( m_logfile_path );

    ensureAvailableAndLoaded("Models", m_comp_pkg_name, false, false,
                                                           "Component Package");
    IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
    store.setValue(
         BridgePointPreferencesStore.ALLOW_IMPLICIT_COMPONENT_ADDRESSING, true);
    // Now set up all the needed state and transition actions for this test
    populateStateMachineActivityInstances();
  }
	
  public void testNoIncomingTransitions() {
    String x = parseAction("testVar = rcvd_evt.a;", STATE_NO_INCOMING_TRANSITIONS);
    assertEquals("Unexpected error:", ":1:20-20: Attempted to access parameter ->a<- when there are no incoming transitions.\nline 1:22: unexpected token: null\nline 1:22: expecting Semicolon, found 'null'\n", x);
    x = parseAction("testVar = param.a;", STATE_NO_INCOMING_TRANSITIONS);
    assertEquals("Unexpected error:", ":1:17-17: Attempted to access parameter ->a<- when there are no incoming transitions.\nline 1:19: unexpected token: null\nline 1:19: expecting Semicolon, found 'null'\n", x);
  }
  public void testNoParms() {
    String x = parseAction("testVar = rcvd_evt.a;", STATE_NO_SIGNAL_PARMS);
    assertEquals("Unexpected error:", ":1:20-20: The following incoming messages do not carry required parameter ->a<- noParms1, noParms2\nline 1:22: unexpected token: null\nline 1:22: expecting Semicolon, found 'null'\n", x);
    x = parseAction("testVar = param.a;", STATE_NO_SIGNAL_PARMS);
    assertEquals("Unexpected error:", ":1:17-17: The following incoming messages do not carry required parameter ->a<- noParms1, noParms2\nline 1:19: unexpected token: null\nline 1:19: expecting Semicolon, found 'null'\n", x);
  }
  public void testMatchingParms() {
    String x = parseAction("testVar1 = rcvd_evt.a; testVar2 = rcvd_evt.b;", STATE_MATCHING_SIGNAL_PARMS);
    assertEquals("Unexpected error:", "", x);
    x = parseAction("testVar1 = param.a; testVar2 = param.b;", STATE_MATCHING_SIGNAL_PARMS);
    assertEquals("Unexpected error:", "", x);
  }
  public void testMatchingParmsTestParmInSubset() {
    String x = parseAction("testVar = rcvd_evt.a;", STATE_NON_MATCHING_WITH_SUBSET_SIGNAL_PARMS);
    assertEquals("Unexpected error:", "", x);
    x = parseAction("testVar = param.a;", STATE_NON_MATCHING_WITH_SUBSET_SIGNAL_PARMS);
    assertEquals("Unexpected error:", "", x);
  }
  public void testMatchingParmsTestParmNotInSubset() {
    String x = parseAction("testVar = rcvd_evt.b;", STATE_NON_MATCHING_WITH_SUBSET_SIGNAL_PARMS);
    assertEquals("Unexpected error:", ":1:20-20: The following incoming messages do not carry required parameter ->b<- TwoParms2\nline 1:22: unexpected token: null\nline 1:22: expecting Semicolon, found 'null'\n", x);
    x = parseAction("testVar = param.b;", STATE_NON_MATCHING_WITH_SUBSET_SIGNAL_PARMS);
    assertEquals("Unexpected error:", ":1:17-17: The following incoming messages do not carry required parameter ->b<- TwoParms2\nline 1:19: unexpected token: null\nline 1:19: expecting Semicolon, found 'null'\n", x);
  }
  public void testOneNoEventTransition() {
    String x = parseAction("testVar = rcvd_evt.a;", STATE_ONE_NO_EVENT_TRANSITION);
    assertEquals("Unexpected error:", ":1:20-20: Attempted to access parameter ->a<- when one or more incoming transitions do not have events assigned.\nline 1:22: unexpected token: null\nline 1:22: expecting Semicolon, found 'null'\n", x);
    x = parseAction("testVar = param.a;", STATE_ONE_NO_EVENT_TRANSITION);
    assertEquals("Unexpected error:", ":1:17-17: Attempted to access parameter ->a<- when one or more incoming transitions do not have events assigned.\nline 1:19: unexpected token: null\nline 1:19: expecting Semicolon, found 'null'\n", x);
  }
  public void testAllNoEventTransitions() {
    String x = parseAction("testVar = rcvd_evt.a;", STATE_ALL_NO_EVENT_TRANSITIONS);
    assertEquals("Unexpected error:", ":1:20-20: Attempted to access parameter ->a<- when one or more incoming transitions do not have events assigned.\nline 1:22: unexpected token: null\nline 1:22: expecting Semicolon, found 'null'\n", x);
    x = parseAction("testVar = param.a;", STATE_ALL_NO_EVENT_TRANSITIONS);
    assertEquals("Unexpected error:", ":1:17-17: Attempted to access parameter ->a<- when one or more incoming transitions do not have events assigned.\nline 1:19: unexpected token: null\nline 1:19: expecting Semicolon, found 'null'\n", x);
  }
  public void testNoEventTransitionAction() {
    String x = parseAction("testVar = rcvd_evt.a; testVar2 = testVar;", TRANS_NO_SIGNAL);
    assertEquals("Unexpected error:", ":1:20-20: Attempted to access parameter ->a<- when associated transition does not have an event assigned.\nline 1:23: expecting Semicolon, found 'testVar2'\nline 1:34: unexpected token: testVar\n", x);
    x = parseAction("testVar = param.a; testVar2 = testVar;", TRANS_NO_SIGNAL);
    assertEquals("Unexpected error:", ":1:17-17: Attempted to access parameter ->a<- when associated transition does not have an event assigned.\nline 1:20: expecting Semicolon, found 'testVar2'\nline 1:31: unexpected token: testVar\n", x);
  }
  public void testNoParmTransitionAction() {
    String x = parseAction("testVar = rcvd_evt.a; testVar2 = testVar;", TRANS_NO_SIGNAL_PARMS);
    assertEquals("Unexpected error:", ":1:20-20: Parameter ->a<- is not carried by signal noParms1\nline 1:23: expecting Semicolon, found 'testVar2'\nline 1:34: unexpected token: testVar\n", x);
    x = parseAction("testVar = param.a; testVar2 = testVar;", TRANS_NO_SIGNAL_PARMS);
    assertEquals("Unexpected error:", ":1:17-17: Parameter ->a<- is not carried by signal noParms1\nline 1:20: expecting Semicolon, found 'testVar2'\nline 1:31: unexpected token: testVar\n", x);
  }
  public void testOneParmTransitionActionParmExists() {
    String x = parseAction("testVar = rcvd_evt.a; testVar2 = testVar;", TRANS_ONE_SIGNAL_PARM);
    assertEquals("Unexpected error:", "", x);
    x = parseAction("testVar = param.a; testVar2 = testVar;", TRANS_ONE_SIGNAL_PARM);
    assertEquals("Unexpected error:", "", x);
  }
  public void testOneParmTransitionActionParmDoesNotExist() {
    String x = parseAction("testVar = rcvd_evt.b; testVar2 = testVar;", TRANS_ONE_SIGNAL_PARM);
    assertEquals("Unexpected error:", ":1:20-20: Parameter ->b<- is not carried by signal OneParm\nline 1:23: expecting Semicolon, found 'testVar2'\nline 1:34: unexpected token: testVar\n", x);
    x = parseAction("testVar = param.b; testVar2 = testVar;", TRANS_ONE_SIGNAL_PARM);
    assertEquals("Unexpected error:", ":1:17-17: Parameter ->b<- is not carried by signal OneParm\nline 1:20: expecting Semicolon, found 'testVar2'\nline 1:31: unexpected token: testVar\n", x);
  }
  public void testTwoParmTransitionActionParmsExist() {
    String x = parseAction("testVar1 = rcvd_evt.a; testVar2 = rcvd_evt.b; testVar3 = testVar1 + testVar2;", TRANS_TWO_SIGNAL_PARMS);
    assertEquals("Unexpected error:", "", x);
    x = parseAction("testVar1 = param.a; testVar2 = param.b; testVar3 = testVar1 + testVar2;", TRANS_TWO_SIGNAL_PARMS);
    assertEquals("Unexpected error:", "", x);
  }
  public void testTwoParmTransitionActionOneParmDoesNotExist() {
    String x = parseAction("testVar1 = rcvd_evt.a; testVar2 = rcvd_evt.c; testVar3 = testVar1 + testVar2;", TRANS_TWO_SIGNAL_PARMS);
    assertEquals("Unexpected error:", ":1:44-44: Parameter ->c<- is not carried by signal TwoParms1\nline 1:47: expecting Semicolon, found 'testVar3'\nline 1:58: unexpected token: testVar1\nline 1:69: unexpected token: testVar2\n", x);
    x = parseAction("testVar1 = param.a; testVar2 = param.c; testVar3 = testVar1 + testVar2;", TRANS_TWO_SIGNAL_PARMS);
    assertEquals("Unexpected error:", ":1:38-38: Parameter ->c<- is not carried by signal TwoParms1\nline 1:41: expecting Semicolon, found 'testVar3'\nline 1:52: unexpected token: testVar1\nline 1:63: unexpected token: testVar2\n", x);
  }
  private String parseAction(String stmts, int actNum) {
    Action_c act = acts[actNum];
    OalLexer lexer = new OalLexer(new StringReader(stmts));
    OalParser parser = new OalParser(modelRoot, lexer);
    parser.m_oal_context = new Oal_validate(DomainUtil.getDomain(act));
    UUID actID = Gd_c.Null_unique_id();
    act.setAction_semantics_internal(stmts);
    actID = act.getAct_id();
    TransitionActionHome_c tah = TransitionActionHome_c.
                        getOneSM_TAHOnR513(ActionHome_c.getOneSM_AHOnR514(act));
    int opType = tah != null ?
                     Oalconstants_c.TRANSITION_TYPE : Oalconstants_c.STATE_TYPE;
    try {
      parser.action(actID, opType);
    }
    catch (TokenStreamException e)
    {
      Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);
      if ( e instanceof TokenStreamRecognitionException ) {
        TokenStreamRecognitionException tsre =
                                             (TokenStreamRecognitionException)e;
        parser.reportError(tsre.recog);
      }
      else {
        fail("Token stream exception in parser");
      }
    }
    catch (RecognitionException e) {
      Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);
      parser.reportError(e);
    }
    catch (InterruptedException ie){
    }
    return parser.m_output;
  }

  Action_c [] acts = null;
  final int STATE_NO_INCOMING_TRANSITIONS = 0;
  final int STATE_NO_SIGNAL_PARMS = 1;
  final int STATE_NON_MATCHING_WITH_SUBSET_SIGNAL_PARMS = 2;
  final int STATE_NON_MATCHING_WITH_NO_SUBSET_SIGNAL_PARMS = 3;
  final int STATE_MATCHING_SIGNAL_PARMS = 4;
  final int STATE_ONE_NO_EVENT_TRANSITION = 5;
  final int STATE_ALL_NO_EVENT_TRANSITIONS = 6;
  final int TRANS_NO_SIGNAL = 7;
  final int TRANS_NO_SIGNAL_PARMS = 8;
  final int TRANS_ONE_SIGNAL_PARM = 9;
  final int TRANS_TWO_SIGNAL_PARMS = 10;

  String [] testStateNames = {      "No Incoming Transitions",
                                    "No Signal Parameters",
                                    "Non Matching with Common Subset",
                                    "Non Matching with no Subset",
                                    "Matching Signal Parameters",
                                    "One No Event Transition",
                                    "All No Event Transitions"};
  String [] testTransitionNames = { "No Event Assigned",
                                    "noParms1",
                                    "oneParm",
                                    "twoParms1"};

  private void populateStateMachineActivityInstances() {
    acts = new Action_c[testStateNames.length + testTransitionNames.length];
    // Get the test class from the model
    class ClassByNameQuery implements ClassQueryInterface_c {
      ClassByNameQuery(String p) {
        m_p = p;
      }
      private String m_p;
      public boolean evaluate(Object inst) {
        ModelClass_c selected = (ModelClass_c)inst;
        return selected.getName().equals(m_p);
      }
    }
    final String testClassName = "Signal Parameter Test";
    ModelClass_c testClass = ModelClass_c.ModelClassInstance(modelRoot,
                                           new ClassByNameQuery(testClassName));
    assertNotNull("Test Class '" + testClassName +
                        "' not found in ComponentSyntaxTest model.", testClass);
    // Get all the test state actions . . . 
    class StateByNameQuery implements ClassQueryInterface_c {
      StateByNameQuery(String p) {
        m_p = p;
      }
      private String m_p;
      public boolean evaluate(Object inst) {
        StateMachineState_c selected = (StateMachineState_c)inst;
        return selected.getName().equals(m_p);
      }
    }
    StateMachine_c sm = StateMachine_c.getOneSM_SMOnR517(
                             ClassStateMachine_c.getOneSM_ASMOnR519(testClass));
    for (int i=0; i < testStateNames.length; i++) {
      StateMachineState_c state = StateMachineState_c.getOneSM_STATEOnR501(sm,
                                       new StateByNameQuery(testStateNames[i]));
      assertNotNull("Could not find test state '" + testStateNames[i] + "'.",
                                                                        state );
      Action_c act = Action_c.getOneSM_ACTOnR514(
                        ActionHome_c.getOneSM_AHOnR513(
                                 MooreActionHome_c.getOneSM_MOAHOnR511(state)));
      assertNotNull("Could not find test action for state '" +
                                                testStateNames[i] + "'.", act );
      acts[i] = act;
    }
    // Get all the test transition actions
    class TransitionByNameQuery implements ClassQueryInterface_c {
      TransitionByNameQuery(String p) {
        m_p = p;
      }
      private String m_p;
      public boolean evaluate(Object inst) {
        Transition_c selected = (Transition_c)inst;
        return selected.getName().contains(m_p);
      }
    }
    for (int i=0; i < testTransitionNames.length; i++) {
      Transition_c trans = Transition_c.getOneSM_TXNOnR505(sm,
                             new TransitionByNameQuery(testTransitionNames[i]));
      assertNotNull("Could not find test transition '" +
                                         testTransitionNames[i] + "'.", trans );
      Action_c act = Action_c.getOneSM_ACTOnR514(
                        ActionHome_c.getOneSM_AHOnR513(
                             TransitionActionHome_c.getOneSM_TAHOnR530(trans)));
      assertNotNull("Could not find test action for transition '" +
                                           testTransitionNames[i] + "'.", act );
      acts[testStateNames.length + i] = act;
    }
  }
}
