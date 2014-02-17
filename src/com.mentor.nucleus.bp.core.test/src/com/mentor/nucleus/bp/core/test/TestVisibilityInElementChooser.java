//========================================================================
//
//File:      $RCSfile: TestVisibilityInElementChooser.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/05/10 04:30:26 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.core.test;

import java.util.ArrayList;
import java.util.List;

import com.mentor.nucleus.bp.core.InteractionParticipant_c;
import com.mentor.nucleus.bp.core.PackageParticipant_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.actions.FormalizeOnSQ_PPAction;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.FailableRunnable;

public class TestVisibilityInElementChooser extends BaseTest {

	private static final String TEST_PROJECT_NAME = "testVisibilityFilterInElementChooser";

	@Override
	protected void initialSetup() throws Exception {
		// turn off auto build
		WorkspaceUtil.setAutobuilding(false);
		// load the test model
		loadProject(TEST_PROJECT_NAME);
		m_sys = getSystemModel(TEST_PROJECT_NAME);
	}
	
	public void testVisibilityFilterInElementChooser() throws Exception {
		// locate the test instance
		Package_c container = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Another Nested Package");
			}
		});
		assertNotNull(container);
		PackageParticipant_c testInstance = PackageParticipant_c
				.getOneSQ_PPOnR930(InteractionParticipant_c
						.getOneSQ_POnR8001(PackageableElement_c
								.getOnePE_PEOnR8000(container)));
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(testInstance);
		List<FailableRunnable> runnables = new ArrayList<FailableRunnable>();
		FailableRunnable runnable = TestUtil.chooseItemInDialog(0, "Another Nested Package", true);
		runnables.add(runnable);
		runnable = TestUtil.chooseItemInDialog(runnable, "Nested Package", true);
		runnables.add(runnable);
		runnable = TestUtil.chooseItemInDialog(runnable, "Root", true);
		runnables.add(runnable);
		runnable = TestUtil.toggleButtonInElementSelectionDialog(0, runnable, "Enable visibility filter");
		runnables.add(runnable);
		runnable = TestUtil.chooseItemInDialog(runnable, "Another Nested Package", true);
		runnables.add(runnable);
		runnable = TestUtil.chooseItemInDialog(0, runnable, "Nested Package", true, true);
		runnables.add(runnable);
		runnable = TestUtil.chooseItemInDialog(runnable, "Root", true);
		runnables.add(runnable);
		runnable = TestUtil.toggleButtonInElementSelectionDialog(0, runnable, "Enable visibility filter");
		runnables.add(runnable);
		runnable = TestUtil.chooseItemInDialog(0, "Another Nested Package", true);
		runnables.add(runnable);
		runnable = TestUtil.chooseItemInDialog(runnable, "Nested Package", true);
		runnables.add(runnable);
		runnable = TestUtil.chooseItemInDialog(runnable, "Root", true);
		runnables.add(runnable);
		TestUtil.cancelElementSelectionDialog(0, runnable);
		// get the action and execute it
		FormalizeOnSQ_PPAction action = new FormalizeOnSQ_PPAction();
		action.run(null);
		for(FailableRunnable failable : runnables) {
			if(!failable.getFailure().equals("")) {
				fail(failable.getFailure());
			}
		}
	}
	
}
