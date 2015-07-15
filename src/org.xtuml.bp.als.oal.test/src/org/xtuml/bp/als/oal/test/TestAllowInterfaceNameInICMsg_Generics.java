package org.xtuml.bp.als.oal.test;

//=====================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
//=====================================================================
import org.eclipse.jface.preference.IPreferenceStore;
import org.xtuml.bp.core.ActionHome_c;
import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.MooreActionHome_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.TestingUtilities;

import antlr.RecognitionException;
import antlr.TokenStreamException;

public class TestAllowInterfaceNameInICMsg_Generics extends BaseTest {

	public static boolean configured = false;
	
    public TestAllowInterfaceNameInICMsg_Generics() {
    	super("org.xtuml.bp.als.oal.test", "GPS Watch");
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
		if (configured) {
			return;
		}
		configured = true;
		super.setUp();
		TestingUtilities.importTestingProjectIntoWorkspace("GPS Watch");
		OalParserTest_Generics.modelRoot = Ooaofooa.getInstance(Ooaofooa
				.createModelRootId(getProjectHandle("GPS Watch"),
						"Library", true));
		modelRoot = OalParserTest_Generics.modelRoot;
		populateStateActionInstances();    	
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
      try {
        super.tearDown();
        OalParserTest_Generics.tearDownActionData();
      } catch (RecognitionException re) {
        // do nothing
      } catch (TokenStreamException te) {
        // do nothing
      }
    }
    
    public void testSendUsingInterfaceNameWhenInterfaceNameDisallowed() throws RecognitionException, TokenStreamException {
        IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(BridgePointPreferencesStore.ALLOW_INTERFACE_NAME_IN_IC_MESSAGE, false);

        String act = "select any monitor from instances of HeartRateMonitor; send HeartRateProvider::heartRateChanged(heartRate: monitor.recentHeartRate); "; //$NON-NLS-1$
        String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_STATE, STATE_ASM_IDLE);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:80-95: Interface names are not allowed for sending messages.  Use the port name.", lines[0]); //$NON-NLS-1$
    }

    public void testSendUsingPortNameWhenIntefaceNameDisallowed() throws RecognitionException, TokenStreamException {
        String act = "select any monitor from instances of HeartRateMonitor; send HR::heartRateChanged(heartRate: monitor.recentHeartRate); "; //$NON-NLS-1$
        String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_STATE, STATE_ASM_IDLE);
        assertEquals("", x); //$NON-NLS-1$
    }

    // Now we test the above sends with the preference set to allow the behavior
    // and expect to not get syntax errors reported
    public void testSendUsingInterfaceNameWhenInterfaceNameAllowed() throws RecognitionException, TokenStreamException {
        IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(BridgePointPreferencesStore.ALLOW_INTERFACE_NAME_IN_IC_MESSAGE, true);

        String act = "select any monitor from instances of HeartRateMonitor; send HeartRateProvider::heartRateChanged(heartRate: monitor.recentHeartRate); "; //$NON-NLS-1$
        String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_STATE, STATE_ASM_IDLE);
        assertEquals("", x); //$NON-NLS-1$
    }

    public void testSendUsingPortNameWhenIntefaceNameAllowed() throws RecognitionException, TokenStreamException {
        String act = "select any monitor from instances of HeartRateMonitor; send HR::heartRateChanged(heartRate: monitor.recentHeartRate); "; //$NON-NLS-1$
        String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_STATE, STATE_ASM_IDLE);
        assertEquals("", x); //$NON-NLS-1$
    }

    // GPS Watch HeartRateMonitor state numbers (minus 1 for zero offset array)
    static public final int STATE_ASM_IDLE = 0;
	static public final int STATE_ASM_MONITORING = 1;
	
	public static Action_c[] m_testAction = new Action_c[3];

	private void populateStateActionInstances() {
		StateMachine_c sm = StateMachine_c.StateMachineInstance(modelRoot);
		assertNotNull("State Machine not found.", sm);

		StateMachineState_c[] states = StateMachineState_c
				.getManySM_STATEsOnR501(sm);
		assertTrue("No test states found", states.length != 0);
		Action_c i_acts[] = Action_c.getManySM_ACTsOnR514(ActionHome_c
				.getManySM_AHsOnR513(MooreActionHome_c
						.getManySM_MOAHsOnR511(states)));
		assertTrue("No test actions found", i_acts.length != 0);
		for (int i = 0; i < i_acts.length; ++i) {
			ActionHome_c ah = ActionHome_c.getOneSM_AHOnR514(i_acts[i]);
			MooreActionHome_c moah = MooreActionHome_c.getOneSM_MOAHOnR513(ah);
			StateMachineState_c st = StateMachineState_c
					.getOneSM_STATEOnR511(moah);
			assertNotNull("State not found.", st);
			OalParserTest_Generics.m_testAction[st.getNumb() - 1] = i_acts[i];
		}
	}

}
