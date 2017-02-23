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
package org.xtuml.bp.core.test;

import junit.framework.TestCase;

public class CoreTestResultCreator extends TestCase {

	public void testCreateTestResults() throws Exception {

		try {

			DeleteTestGenerics dt = new DeleteTestGenerics();
			dt.setUp();
			dt.setGenerateResults();

			AssignClassTestGenerics act = new AssignClassTestGenerics();
			act.setUp();
			act.setGenerateResults();

			AssignRemoveEventsGenerics are = new AssignRemoveEventsGenerics();
			are.setUp();
			are.setGenerateResults();

			AttributeMenuItemTestGenerics ami = new AttributeMenuItemTestGenerics();
			ami.setUp();
			ami.setGenerateResults();

			CombineSplitReferentialsTestGenerics csr = new CombineSplitReferentialsTestGenerics();
			csr.setUp();
			csr.setGenerateResults();

			ModelTransactionTestGenerics mtt = new ModelTransactionTestGenerics();
			mtt.setUp();

			SequenceTestsGenerics st = new SequenceTestsGenerics();
			st.setGenerateResults();

			CommunicationLinkTestsGenerics clt = new CommunicationLinkTestsGenerics();
			clt.setGenerateResults();

			CommunicationMessageTestsGenerics cmt = new CommunicationMessageTestsGenerics();
			cmt.setGenerateResults();

			CommunicationTestsGenerics cts = new CommunicationTestsGenerics();
			cts.setGenerateResults();

            ModifyNonFullyLoadedModelTestsGenerics mnflm = new ModifyNonFullyLoadedModelTestsGenerics();
            mnflm.setGenerateResults();
            
			System.out
					.println("CoreTestResultCreator: Test results created OK");
		} catch (Exception e) {
			System.err.println("Exception encountered by test result creator: "
					+ e);
			throw e;
		}

	}
}
