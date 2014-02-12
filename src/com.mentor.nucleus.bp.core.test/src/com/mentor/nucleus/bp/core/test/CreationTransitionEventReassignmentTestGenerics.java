//=====================================================================
//
//File:      $RCSfile: CreationTransitionEventReassignmentTestGenerics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 04:30:27 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.core.test;

import com.mentor.nucleus.bp.core.CreationTransition_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.test.util.EventSelectionUtil;
import com.mentor.nucleus.bp.core.util.OoaofooaUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;

/**
 * Tests that an event may be assigned to a creation transition after
 * another event with a different data item set has been removed.
 */
public class CreationTransitionEventReassignmentTestGenerics extends BaseTest {
	public CreationTransitionEventReassignmentTestGenerics(String arg0) {
		super(null, arg0);
	}

	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		loadProject("CreationTransitionEventReassignmentTest");
	}
	/**
	 * See parent class description.
	 */
	public void testCreationTransitionEventReassignment() {
		// in the following, T is the creation transition in A

		// remove E1 from its assignment on T
		StateMachine_c aMachine = OoaofooaUtil.getStateMachine(modelRoot, "A");
		Transition_c tTransition = Transition_c.getOneSM_TXNOnR505(aMachine);
		CreationTransition_c creation = CreationTransition_c
				.getOneSM_CRTXNOnR507(tTransition);
		tTransition.Removeevent();

		// if the above removal worked properly, then E2 should now
		// be assignable to T
		EventSelectionUtil
				.checkForPresenceOfEventInAssignListForCreationTransitionGenerics(
						"KEY2: E2", creation);
	}
}
