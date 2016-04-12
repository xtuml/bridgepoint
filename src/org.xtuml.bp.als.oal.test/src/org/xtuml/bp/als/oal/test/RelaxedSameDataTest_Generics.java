//========================================================================
//
//File:      $RCSfile: RelaxedSameDataTest_Generics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 04:52:48 $
//
//(c) Copyright 2008-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//========================================================================
//
package org.xtuml.bp.als.oal.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.ActionHome_c;
import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.MooreActionHome_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;

import antlr.RecognitionException;
import antlr.TokenStreamException;

@RunWith(OrderedRunner.class)
public class RelaxedSameDataTest_Generics extends BaseTest {
  public static boolean configured = false;
  public RelaxedSameDataTest_Generics() {
	  super("org.xtuml.bp.als.oal.test", "Relaxed Same Data");
  }
  final String NO_PARAMETER_ACCESS = "foo = 1; bar = foo;";
  final String EXISTING_PARAMETER_ACCESS = "foo = rcvd_evt.a; bar = foo;";
  final String NON_SUBSET_PARAMETER_ACCESS = "foo = rcvd_evt.d; bar = foo;";
  final String NON_EXISTENT_PARAMETER_ACCESS = "foo = rcvd_evt.e; bar = foo;";
  
  // Relaxed Same Data test model state numbers (minus 1 for zero offset array)
  final int NO_TRANSITIONS = 0;
  final int NO_EVENT_PARAMETERS = 1;
  final int NON_MATCHING_EVENT_PARAMETERS_WITH_SUBSET = 2;
  final int NON_MATCHING_EVENT_PARAMETERS_NO_SUBSET = 3;
  final int MATCHING_EVENT_PARAMETERS = 4;
  final int ONE_NO_EVENT_TRANSITION = 5;
  final int ALL_NO_EVENT_TRANSITION = 6;
  
  @Before
	public void setUp() throws Exception {
	  if (configured) {
		  return;
	  }
      configured = true;
	  super.setUp();
      TestingUtilities.importTestingProjectIntoWorkspace("Relaxed Same Data");
	  OalParserTest_Generics.modelRoot = Ooaofooa.getInstance(Ooaofooa
				.createModelRootId(getProjectHandle("Relaxed Same Data"),
						"Relaxed Same Data", true));
	  modelRoot = OalParserTest_Generics.modelRoot;
	  populateStateActionInstances();
  }

  // The tests themselves
  @Test
	public void testNoTransitionsNoParameterAccess() {
	String x = OalParserTest_Generics.parseAction(NO_PARAMETER_ACCESS,
			   OalParserTest_Generics.ACTIVITY_TYPE_STATE, NO_TRANSITIONS); //$NON-NLS-1$
	assertTrue("Unexpected error encountered: " + x, x.length() == 0);
  }
  @Test
	public void testNoTransitionsNonExistentParameterAccess() {
	String x = OalParserTest_Generics.parseAction(NON_EXISTENT_PARAMETER_ACCESS,
               OalParserTest_Generics.ACTIVITY_TYPE_STATE, NO_TRANSITIONS); //$NON-NLS-1$
	String [] lines = x.split("\\n");
	assertEquals(3, lines.length);
	assertEquals(":1:16-16: Attempted to access parameter ->e<- when there are no incoming transitions.", lines[0]);//$NON-NLS-1$
	assertEquals("line 1:19: expecting Semicolon, found 'bar'",lines[1]);//$NON-NLS-1$
	assertEquals("line 1:25: unexpected token: foo", lines[2]);//$NON-NLS-1$
  }
  @Test
	public void testNoEventParametersNoParameterAccess() {
    String x = OalParserTest_Generics.parseAction(NO_PARAMETER_ACCESS,
          OalParserTest_Generics.ACTIVITY_TYPE_STATE, NO_EVENT_PARAMETERS); //$NON-NLS-1$
    assertTrue("Unexpected error encountered: " + x, x.length() == 0);
  }
  @Test
	public void testNoEventParametersNonExistentParameterAccess() {
	String x = OalParserTest_Generics.parseAction(NON_EXISTENT_PARAMETER_ACCESS,
               OalParserTest_Generics.ACTIVITY_TYPE_STATE, NO_EVENT_PARAMETERS); //$NON-NLS-1$
	String [] lines = x.split("\\n");
	assertEquals(3, lines.length);
	assertEquals(":1:16-16: The following incoming messages do not carry required parameter ->e<- No Parms1, No Parms2", lines[0]);//$NON-NLS-1$
	assertEquals("line 1:19: expecting Semicolon, found 'bar'",lines[1]);//$NON-NLS-1$
	assertEquals("line 1:25: unexpected token: foo", lines[2]);//$NON-NLS-1$
  }
  @Test
	public void testMatchingEventParametersNoParameterAccess() {
    String x = OalParserTest_Generics.parseAction(NO_PARAMETER_ACCESS,
                               OalParserTest_Generics.ACTIVITY_TYPE_STATE,
                                       MATCHING_EVENT_PARAMETERS); //$NON-NLS-1$
    assertTrue("Unexpected error encountered: " + x, x.length() == 0);
  }
  @Test
	public void testMatchingEventParametersExistingParameterAccess() {
    String x = OalParserTest_Generics.parseAction(EXISTING_PARAMETER_ACCESS,
                                   OalParserTest_Generics.ACTIVITY_TYPE_STATE,
                                       MATCHING_EVENT_PARAMETERS); //$NON-NLS-1$
    assertTrue("Unexpected error encountered: " + x, x.length() == 0);
  }
  @Test
	public void testMatchingEventParametersNonExistentParameterAccess() {
    String x = OalParserTest_Generics.parseAction(NON_EXISTENT_PARAMETER_ACCESS,
                                       OalParserTest_Generics.ACTIVITY_TYPE_STATE,
                                       MATCHING_EVENT_PARAMETERS); //$NON-NLS-1$
    String [] lines = x.split("\\n");
    assertEquals(3, lines.length);
    assertEquals(":1:16-16: The following incoming messages do not carry required parameter ->e<- Common Parms1, Common Parms2", lines[0]);//$NON-NLS-1$
    assertEquals("line 1:19: expecting Semicolon, found 'bar'",lines[1]);//$NON-NLS-1$
    assertEquals("line 1:25: unexpected token: foo", lines[2]);//$NON-NLS-1$
  }
  @Test
	public void testNonMatchingEventParametersWithSubsetNoParameterAccess() {
    String x = OalParserTest_Generics.parseAction(NO_PARAMETER_ACCESS,
                             OalParserTest_Generics.ACTIVITY_TYPE_STATE,
                       NON_MATCHING_EVENT_PARAMETERS_WITH_SUBSET); //$NON-NLS-1$
    assertTrue("Unexpected error encountered: " + x, x.length() == 0);
  }
  @Test
	public void testNonMatchingEventParametersWithSubsetSubsetParameterAccess() {
    String x = OalParserTest_Generics.parseAction(EXISTING_PARAMETER_ACCESS,
                                   OalParserTest_Generics.ACTIVITY_TYPE_STATE,
                       NON_MATCHING_EVENT_PARAMETERS_WITH_SUBSET); //$NON-NLS-1$
    assertTrue("Unexpected error encountered: " + x, x.length() == 0);
  }
  @Test
	public void testNonMatchingEventParametersWithSubsetNonSubsetParameterAccess() {
    String x = OalParserTest_Generics.parseAction(NON_SUBSET_PARAMETER_ACCESS,
                                       OalParserTest_Generics.ACTIVITY_TYPE_STATE,
                       NON_MATCHING_EVENT_PARAMETERS_WITH_SUBSET); //$NON-NLS-1$
    String [] lines = x.split("\\n");
    assertEquals(3, lines.length);
    assertEquals(":1:16-16: The following incoming messages do not carry required parameter ->d<- Two Parms1, Two Parms2", lines[0]);//$NON-NLS-1$
    assertEquals("line 1:19: expecting Semicolon, found 'bar'",lines[1]);//$NON-NLS-1$
    assertEquals("line 1:25: unexpected token: foo", lines[2]);//$NON-NLS-1$
  }
  @Test
	public void testNonMatchingEventParametersWithSubsetNonExistentParameterAccess() {
    String x = OalParserTest_Generics.parseAction(NON_EXISTENT_PARAMETER_ACCESS,
                                       OalParserTest_Generics.ACTIVITY_TYPE_STATE,
                       NON_MATCHING_EVENT_PARAMETERS_WITH_SUBSET); //$NON-NLS-1$
    String [] lines = x.split("\\n");
    assertEquals(3, lines.length);
    assertEquals(":1:16-16: The following incoming messages do not carry required parameter ->e<- Two Parms1, Two Parms2", lines[0]);//$NON-NLS-1$
    assertEquals("line 1:19: expecting Semicolon, found 'bar'",lines[1]);//$NON-NLS-1$
    assertEquals("line 1:25: unexpected token: foo", lines[2]);//$NON-NLS-1$
  }
  @Test
	public void testNonMatchingEventParametersNoSubsetNoParameterAccess() {
    String x = OalParserTest_Generics.parseAction(NO_PARAMETER_ACCESS,
                             OalParserTest_Generics.ACTIVITY_TYPE_STATE,
                         NON_MATCHING_EVENT_PARAMETERS_NO_SUBSET); //$NON-NLS-1$
    assertTrue("Unexpected error encountered: " + x, x.length() == 0);
  }
  @Test
	public void testNonMatchingEventParametersNoSubsetExistingParameterAccess() {
    String x = OalParserTest_Generics.parseAction(EXISTING_PARAMETER_ACCESS,
                                   OalParserTest_Generics.ACTIVITY_TYPE_STATE,
                         NON_MATCHING_EVENT_PARAMETERS_NO_SUBSET); //$NON-NLS-1$
    String [] lines = x.split("\\n");
    assertEquals(3, lines.length);
    assertEquals(":1:16-16: The following incoming messages do not carry required parameter ->a<- No Subset2", lines[0]);//$NON-NLS-1$
    assertEquals("line 1:19: expecting Semicolon, found 'bar'",lines[1]);//$NON-NLS-1$
    assertEquals("line 1:25: unexpected token: foo", lines[2]);//$NON-NLS-1$
  }
  @Test
	public void testNonMatchingEventParametersNoSubsetNonExistentParameterAccess() {
    String x = OalParserTest_Generics.parseAction(NON_EXISTENT_PARAMETER_ACCESS,
                                       OalParserTest_Generics.ACTIVITY_TYPE_STATE,
                         NON_MATCHING_EVENT_PARAMETERS_NO_SUBSET); //$NON-NLS-1$
    String [] lines = x.split("\\n");
    assertEquals(3, lines.length);
    assertEquals(":1:16-16: The following incoming messages do not carry required parameter ->e<- No Subset1, No Subset2", lines[0]);//$NON-NLS-1$
    assertEquals("line 1:19: expecting Semicolon, found 'bar'",lines[1]);//$NON-NLS-1$
    assertEquals("line 1:25: unexpected token: foo", lines[2]);//$NON-NLS-1$
  }
  @Test
	public void testOneNoEventTransitionNoParameterAccess() {
    String x = OalParserTest_Generics.parseAction(NO_PARAMETER_ACCESS,
                             OalParserTest_Generics.ACTIVITY_TYPE_STATE,
                                         ONE_NO_EVENT_TRANSITION); //$NON-NLS-1$
    assertTrue("Unexpected error encountered: " + x, x.length() == 0);
  }
  @Test
	public void testOneNoEventTransitionExistingParameterAccess() {
    String x = OalParserTest_Generics.parseAction(EXISTING_PARAMETER_ACCESS,
                                   OalParserTest_Generics.ACTIVITY_TYPE_STATE,
                                         ONE_NO_EVENT_TRANSITION); //$NON-NLS-1$
    String [] lines = x.split("\\n");
    assertEquals(3, lines.length);
    assertEquals(":1:16-16: Attempted to access parameter ->a<- when one or more incoming transitions do not have events assigned.", lines[0]);//$NON-NLS-1$
    assertEquals("line 1:19: expecting Semicolon, found 'bar'",lines[1]);//$NON-NLS-1$
    assertEquals("line 1:25: unexpected token: foo", lines[2]);//$NON-NLS-1$
  }
  @Test
	public void testOneNoEventTransitionNonExistentParameterAccess() {
    String x = OalParserTest_Generics.parseAction(NON_EXISTENT_PARAMETER_ACCESS,
                                       OalParserTest_Generics.ACTIVITY_TYPE_STATE,
                                         ONE_NO_EVENT_TRANSITION); //$NON-NLS-1$
    String [] lines = x.split("\\n");
    assertEquals(3, lines.length);
    assertEquals(":1:16-16: Attempted to access parameter ->e<- when one or more incoming transitions do not have events assigned.", lines[0]);//$NON-NLS-1$
    assertEquals("line 1:19: expecting Semicolon, found 'bar'",lines[1]);//$NON-NLS-1$
    assertEquals("line 1:25: unexpected token: foo", lines[2]);//$NON-NLS-1$
  }
  @Test
	public void testAllNoEventTransitionNoParameterAccess() {
    String x = OalParserTest_Generics.parseAction(NO_PARAMETER_ACCESS,
                             OalParserTest_Generics.ACTIVITY_TYPE_STATE,
                                         ALL_NO_EVENT_TRANSITION); //$NON-NLS-1$
    assertTrue("Unexpected error encountered: " + x, x.length() == 0);
  }
  @Test
	public void testAllNoEventTransitionNonExistentParameterAccess() {
    String x = OalParserTest_Generics.parseAction(NON_EXISTENT_PARAMETER_ACCESS,
                                       OalParserTest_Generics.ACTIVITY_TYPE_STATE,
                                         ALL_NO_EVENT_TRANSITION); //$NON-NLS-1$
    String [] lines = x.split("\\n");
    assertEquals(3, lines.length);
    assertEquals(":1:16-16: Attempted to access parameter ->e<- when one or more incoming transitions do not have events assigned.", lines[0]);//$NON-NLS-1$
    assertEquals("line 1:19: expecting Semicolon, found 'bar'",lines[1]);//$NON-NLS-1$
    assertEquals("line 1:25: unexpected token: foo", lines[2]);//$NON-NLS-1$
  }
  // Test utilities
  private void populateStateActionInstances() {
	StateMachine_c sm = StateMachine_c.
	                             StateMachineInstance(modelRoot);
	assertNotNull("State Machine not found.", sm);
	  
    StateMachineState_c [] states =
                                 StateMachineState_c.getManySM_STATEsOnR501(sm);
    assertTrue("No test states found", states.length != 0);
    Action_c i_acts[] = Action_c.getManySM_ACTsOnR514(
				                ActionHome_c.getManySM_AHsOnR513(
						      MooreActionHome_c.getManySM_MOAHsOnR511(states)));
    assertTrue("No test actions found", i_acts.length != 0);
	for ( int i = 0; i < i_acts.length; ++i )
	{
      ActionHome_c ah = ActionHome_c.getOneSM_AHOnR514(i_acts[i]);
      MooreActionHome_c moah = MooreActionHome_c.getOneSM_MOAHOnR513(ah);
      StateMachineState_c st = StateMachineState_c.getOneSM_STATEOnR511(moah);
      assertNotNull("State not found.", st);
      OalParserTest_Generics.m_testAction[st.getNumb() - 1] = i_acts[i]; 
	}
  }
  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  @After
	public void tearDown() throws Exception {
    try {
      super.tearDown();
      OalParserTest_Generics.tearDownActionData();
    } catch (RecognitionException re) {
      // do nothing
    } catch (TokenStreamException te) {
      // do nothing
    }
  }
}
