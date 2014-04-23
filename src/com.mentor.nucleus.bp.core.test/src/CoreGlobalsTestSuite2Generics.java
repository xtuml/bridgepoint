
//=====================================================================
//
//File:      $RCSfile: CoreGlobalsTestSuite2Generics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 04:31:11 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================


import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.test.ActivityTestsGenerics;
import com.mentor.nucleus.bp.core.test.CanRenameCanDeleteTestGenerics;
import com.mentor.nucleus.bp.core.test.ClassKeyLetters;
import com.mentor.nucleus.bp.core.test.CombineSplitReferentialsTestGenerics;
import com.mentor.nucleus.bp.core.test.CommunicationLinkTestsGenerics;
import com.mentor.nucleus.bp.core.test.CommunicationMessageTestsGenerics;
import com.mentor.nucleus.bp.core.test.CommunicationTestsGenerics;
import com.mentor.nucleus.bp.core.test.CoreGlobalsTestSuiteIIGenerics;
import com.mentor.nucleus.bp.core.test.CreationTransitionEventReassignmentTestGenerics;
import com.mentor.nucleus.bp.core.test.DeleteDatatypesTestGenerics;
import com.mentor.nucleus.bp.core.test.I810_SlowDeletionTestGenerics;
import com.mentor.nucleus.bp.core.test.IntegrityIssueTests;
import com.mentor.nucleus.bp.core.test.ModelChangeListenersBatchingTestGenerics;
import com.mentor.nucleus.bp.core.test.ModelIntegrityTests;
import com.mentor.nucleus.bp.core.test.ModelTransactionTestGenerics;
import com.mentor.nucleus.bp.core.test.ModificationValidationTestsGenerics;
import com.mentor.nucleus.bp.core.test.ModifyNonFullyLoadedModelTestsGenerics;
import com.mentor.nucleus.bp.core.test.MultipleSelectionAssignmentTests;
import com.mentor.nucleus.bp.core.test.PolymorphicEventAssignmentTestGenerics;
import com.mentor.nucleus.bp.core.test.RefreshTestCoreGenerics;
import com.mentor.nucleus.bp.core.test.RenameInvolvingResourceTestGenerics;
import com.mentor.nucleus.bp.core.test.SequenceTestsGenerics;
import com.mentor.nucleus.bp.core.test.TestVisibilityInElementChooser;
import com.mentor.nucleus.bp.core.test.TransitionActionTestGenerics;
import com.mentor.nucleus.bp.core.test.TwoModelsSelectionTestGenerics;
import com.mentor.nucleus.bp.core.test.UndoRedoTestGenerics;
import com.mentor.nucleus.bp.core.test.UniqueNameTestGenerics;
import com.mentor.nucleus.bp.core.test.UseCaseTestsGenerics;
import com.mentor.nucleus.bp.core.test.ui.WritableContextMenuTestGenerics;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;

/**
* Test all areas of the core
*/
public class CoreGlobalsTestSuite2Generics extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 * @throws CoreException
	 */
	public static Test suite() throws CoreException {
		return new CoreGlobalsTestSuite2Generics();
	}

	/**
	 * Construct the test suite.
	 */
	public CoreGlobalsTestSuite2Generics() throws CoreException {

		// turn off autobuild to stop MC-3020 builders from running
		WorkspaceUtil.setAutobuilding(false);   // throws CoreException
		CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION, true);
        addTest(new TestSuite(IntegrityIssueTests.class));
		addTest(new TestSuite(CoreGlobalsTestSuiteIIGenerics.class));
		addTest(new TestSuite(MultipleSelectionAssignmentTests.class));
        addTest(new TestSuite(SequenceTestsGenerics.class));
	    addTest(new TestSuite(CommunicationMessageTestsGenerics.class));/*3*/
	    addTest(new TestSuite(CommunicationTestsGenerics.class));
	    addTest(new TestSuite(CommunicationLinkTestsGenerics.class));
        addTest(new TestSuite(ModelTransactionTestGenerics.class));/*2*/
        addTest(new TestSuite(DeleteDatatypesTestGenerics.class));
		addTest(new TestSuite(CanRenameCanDeleteTestGenerics.class));
  		addTest(new TestSuite(CombineSplitReferentialsTestGenerics.class));
     	addTest(new TestSuite(RenameInvolvingResourceTestGenerics.class));
     	addTest(new TestSuite(TwoModelsSelectionTestGenerics.class));/*1*/
    	addTest(new TestSuite(ModelChangeListenersBatchingTestGenerics.class));
		addTest(new TestSuite(WritableContextMenuTestGenerics.class));
		addTest(new TestSuite(CreationTransitionEventReassignmentTestGenerics.class));
		addTest(new TestSuite(PolymorphicEventAssignmentTestGenerics.class));
        addTest(new TestSuite(UndoRedoTestGenerics.class));
  	    addTest(new TestSuite(UseCaseTestsGenerics.class));
        addTest(new TestSuite(RefreshTestCoreGenerics.class));
 	    addTest(new TestSuite(ModificationValidationTestsGenerics.class));
        addTest(new TestSuite(UniqueNameTestGenerics.class));
        addTest(new TestSuite(ModifyNonFullyLoadedModelTestsGenerics.class));
        addTest(new TestSuite(I810_SlowDeletionTestGenerics.class));
        addTest(new TestSuite(TransitionActionTestGenerics.class));
        addTest(new TestSuite(ActivityTestsGenerics.class));
        addTest(new TestSuite(TestVisibilityInElementChooser.class));
        addTest(new TestSuite(ModelIntegrityTests.class));
        addTest(new TestSuite(ClassKeyLetters.class));
	}
}