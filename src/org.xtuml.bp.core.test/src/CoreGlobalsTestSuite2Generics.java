
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


import org.eclipse.core.runtime.CoreException;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.test.ActivityTestsGenerics;
import org.xtuml.bp.core.test.CanRenameCanDeleteTestGenerics;
import org.xtuml.bp.core.test.ClassKeyLetters;
import org.xtuml.bp.core.test.CombineSplitReferentialsTestGenerics;
import org.xtuml.bp.core.test.CommunicationLinkTestsGenerics;
import org.xtuml.bp.core.test.CommunicationMessageTestsGenerics;
import org.xtuml.bp.core.test.CommunicationTestsGenerics;
import org.xtuml.bp.core.test.CoreGlobalsTestSuiteIIGenerics;
import org.xtuml.bp.core.test.CreationTransitionEventReassignmentTestGenerics;
import org.xtuml.bp.core.test.DeleteDatatypesTestGenerics;
import org.xtuml.bp.core.test.FormalizeUnformalizeWithPrefixTestGenerics;
import org.xtuml.bp.core.test.I810_SlowDeletionTestGenerics;
import org.xtuml.bp.core.test.IntegrityIssueTests;
import org.xtuml.bp.core.test.ModelChangeListenersBatchingTestGenerics;
import org.xtuml.bp.core.test.ModelIntegrityTests;
import org.xtuml.bp.core.test.ModelTransactionTestGenerics;
import org.xtuml.bp.core.test.ModificationValidationTestsGenerics;
import org.xtuml.bp.core.test.ModifyNonFullyLoadedModelTestsGenerics;
import org.xtuml.bp.core.test.MultipleSelectionAssignmentTests;
import org.xtuml.bp.core.test.PolymorphicEventAssignmentTestGenerics;
import org.xtuml.bp.core.test.RefreshTestCoreGenerics;
import org.xtuml.bp.core.test.RenameInvolvingResourceTestGenerics;
import org.xtuml.bp.core.test.SequenceTestsGenerics;
import org.xtuml.bp.core.test.TransitionActionTestGenerics;
import org.xtuml.bp.core.test.TwoModelsSelectionTestGenerics;
import org.xtuml.bp.core.test.UndoRedoTestGenerics;
import org.xtuml.bp.core.test.UniqueNameTestGenerics;
import org.xtuml.bp.core.test.UseCaseTestsGenerics;
import org.xtuml.bp.core.test.ui.WritableContextMenuTestGenerics;
import org.xtuml.bp.core.util.WorkspaceUtil;

import junit.framework.Test;
import junit.framework.TestSuite;

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
		addTest(new TestSuite(CoreGlobalsTestSuiteIIGenerics.class));
    	addTest(new TestSuite(ModelChangeListenersBatchingTestGenerics.class));
        addTest(new TestSuite(ModifyNonFullyLoadedModelTestsGenerics.class));
        addTest(new TestSuite(DeleteDatatypesTestGenerics.class));
        addTest(new TestSuite(TransitionActionTestGenerics.class));
        addTest(new TestSuite(ModelTransactionTestGenerics.class));
		addTest(new TestSuite(MultipleSelectionAssignmentTests.class));
		addTest(new TestSuite(FormalizeUnformalizeWithPrefixTestGenerics.class));
        addTest(new TestSuite(IntegrityIssueTests.class));
	    addTest(new TestSuite(CommunicationMessageTestsGenerics.class));/*3*/
	    addTest(new TestSuite(CommunicationTestsGenerics.class));
	    addTest(new TestSuite(CommunicationLinkTestsGenerics.class));
		addTest(new TestSuite(CanRenameCanDeleteTestGenerics.class));
  		addTest(new TestSuite(CombineSplitReferentialsTestGenerics.class));
     	addTest(new TestSuite(RenameInvolvingResourceTestGenerics.class));
     	addTest(new TestSuite(TwoModelsSelectionTestGenerics.class));/*1*/
		addTest(new TestSuite(WritableContextMenuTestGenerics.class));
		addTest(new TestSuite(CreationTransitionEventReassignmentTestGenerics.class));
		addTest(new TestSuite(PolymorphicEventAssignmentTestGenerics.class));
        addTest(new TestSuite(UndoRedoTestGenerics.class));
  	    addTest(new TestSuite(UseCaseTestsGenerics.class));
        addTest(new TestSuite(RefreshTestCoreGenerics.class));
 	    addTest(new TestSuite(ModificationValidationTestsGenerics.class));
        addTest(new TestSuite(UniqueNameTestGenerics.class));
        addTest(new TestSuite(I810_SlowDeletionTestGenerics.class));
        addTest(new TestSuite(ActivityTestsGenerics.class));
        addTest(new TestSuite(ModelIntegrityTests.class));
        addTest(new TestSuite(ClassKeyLetters.class));
        addTest(new TestSuite(SequenceTestsGenerics.class));
	}
}