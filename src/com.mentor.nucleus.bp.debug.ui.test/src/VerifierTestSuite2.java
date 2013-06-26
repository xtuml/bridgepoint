
//=====================================================================
//
//File:      $RCSfile: VerifierTestSuite2.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/05/10 04:28:44 $
//
//(c) Copyright 2006-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================


import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.debug.engine.CreateAndGenerateEventTest;
import com.mentor.nucleus.bp.debug.engine.ExternalEntityTest;
import com.mentor.nucleus.bp.debug.engine.MDATest;
import com.mentor.nucleus.bp.debug.engine.SendMessageOverDelegationTest;
import com.mentor.nucleus.bp.debug.engine.SignalParameterPassingTest;
import com.mentor.nucleus.bp.debug.engine.TimerTest;
import com.mentor.nucleus.bp.debug.engine.VerifierAuditTest;
import com.mentor.nucleus.bp.debug.engine.VerifierCreationTransitionDebugTest;
import com.mentor.nucleus.bp.debug.engine.VerifierMessageTest;
import com.mentor.nucleus.bp.debug.engine.VerifierMessageTestGlobals;
import com.mentor.nucleus.bp.debug.engine.VerifierTransitionActionTests;
import com.mentor.nucleus.bp.debug.test.RealizedClassTest;
import com.mentor.nucleus.bp.debug.test.VIECTest;
import com.mentor.nucleus.bp.debug.test.VerifierTestSuiteI;
import com.mentor.nucleus.bp.debug.test.VerifierUDTAsUDTInitializationTests;
import com.mentor.nucleus.bp.debug.test.breakpoint.BreakpointTest;
import com.mentor.nucleus.bp.debug.ui.launch.VerifierLaunchTestSuite;
import com.mentor.nucleus.bp.debug.ui.session.tree.VerifierSessionExplorerTests;
import com.mentor.nucleus.bp.debug.ui.test.execute.VerifierExecuteActionTests;
import com.mentor.nucleus.bp.debug.ui.test.execute.VerifierExecuteFragmentTest;
import com.mentor.nucleus.bp.debug.ui.test.execute.VerifierInterfaceExecutionTests;
import com.mentor.nucleus.bp.debug.ui.test.realizedClasses.VerifierBindingAuditTest;
import com.mentor.nucleus.bp.debug.ui.test.realizedClasses.VerifierRealizedUDTTest;
import com.mentor.nucleus.bp.debug.ui.test.realizedClasses.VerifierStaticVariablesInRealizedClassesTest;
import com.mentor.nucleus.bp.debug.ui.test.runtimemsg.TestVerifierRunTimeErrorMsgs_0;
import com.mentor.nucleus.bp.debug.ui.test.runtimemsg.TestVerifierRunTimeErrorMsgs_1;

/**
* Test all areas of the core
*/
public class VerifierTestSuite2 extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 * @throws CoreException
	 */
	public static Test suite() throws CoreException {
		return new VerifierTestSuite2();
	}
	
	/**
	 * Construct the test suite.
	 */
	public VerifierTestSuite2() throws CoreException {

		// turn off autobuild to stop MC-3020 builders from running
		WorkspaceUtil.setAutobuilding(false);   // throws CoreException
		CorePlugin.getDefault().getPreferenceStore().
        setValue(BridgePointPreferencesStore.
  		              USE_DEFAULT_NAME_FOR_CREATION, true);

		addTest(new TestSuite(VerifierMessageTestGlobals.class));
	}

}