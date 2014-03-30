//========================================================================
//
//File:      $RCSfile: TransitionComparable.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/05/10 13:25:58 $
//
//Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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
package com.mentor.nucleus.bp.model.compare.providers.custom;

import com.mentor.nucleus.bp.core.NewStateTransition_c;
import com.mentor.nucleus.bp.core.NoEventTransition_c;
import com.mentor.nucleus.bp.core.SemEvent_c;
import com.mentor.nucleus.bp.core.StateEventMatrixEntry_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.model.compare.providers.NonRootModelElementComparable;

public class TransitionComparable extends NonRootModelElementComparable {

	public TransitionComparable(NonRootModelElement realElement) {
		super(realElement);
	}

	@Override
	public boolean treeItemEquals(Object other) {
		if (!super.treeItemEquals(other)) {
			// if the other element is a different transition
			// return true if the transition shares the same
			// origin and the same assigned event
			if (other instanceof TransitionComparable) {
				TransitionComparable transitionComp = (TransitionComparable) other;
				Transition_c transition = (Transition_c) transitionComp.getRealElement();
				Transition_c ourTransition = (Transition_c) getRealElement();
				StateMachineState_c state = StateMachineState_c
						.getOneSM_STATEOnR503(StateEventMatrixEntry_c
								.getOneSM_SEMEOnR504(NewStateTransition_c
										.getOneSM_NSTXNOnR507(transition)));
				StateMachineState_c ourState = StateMachineState_c
						.getOneSM_STATEOnR503(StateEventMatrixEntry_c
								.getOneSM_SEMEOnR504(NewStateTransition_c
										.getOneSM_NSTXNOnR507(ourTransition)));
				if(state == null || ourState == null) {
					return false;
				}
				if(state.getSmstt_id().equals(ourState.getSmstt_id())) {
					StateMachineEvent_c event = StateMachineEvent_c
							.getOneSM_EVTOnR525(SemEvent_c
									.getOneSM_SEVTOnR503(StateEventMatrixEntry_c
											.getOneSM_SEMEOnR504(NewStateTransition_c
													.getOneSM_NSTXNOnR507(transition))));
					StateMachineEvent_c ourEvent = StateMachineEvent_c
					.getOneSM_EVTOnR525(SemEvent_c
							.getOneSM_SEVTOnR503(StateEventMatrixEntry_c
									.getOneSM_SEMEOnR504(NewStateTransition_c
											.getOneSM_NSTXNOnR507(ourTransition))));
					if(event.cachedIdentityEquals(ourEvent)) {
						return true;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean treeItemNameMatches(Object other) {
		return true;
	}

	@Override
	public boolean treeItemTypeEquals(Object other) {
		return true;
	}

	@Override
	public boolean treeItemValueEquals(Object other) {
		return true;
	}

	@Override
	public int hashCode() {
		Transition_c ourTransition = (Transition_c) getRealElement();
		StateMachineState_c ourState = StateMachineState_c
				.getOneSM_STATEOnR503(StateEventMatrixEntry_c
						.getOneSM_SEMEOnR504(NewStateTransition_c
								.getOneSM_NSTXNOnR507(ourTransition)));
		if(ourState == null) {
			// see if this is a no event transition
			ourState = StateMachineState_c
					.getOneSM_STATEOnR508(NoEventTransition_c
							.getOneSM_NETXNOnR507(ourTransition));
		}
		if(ourState != null)
			return ourState.getSmstt_id().hashCode();
		else
			return ourTransition.getTrans_id().hashCode();
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.model.compare.ComparableTreeObject#ignoreOrdering()
	 */
	@Override
	public boolean ignoreOrdering() {
		return true;
	}

}
