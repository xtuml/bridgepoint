//========================================================================
//
//File:      $RCSfile: ActionComparable.java,v $
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

import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.NewStateTransition_c;
import com.mentor.nucleus.bp.core.StateEventMatrixEntry_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.TransitionActionHome_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.model.compare.ComparableTreeObject;
import com.mentor.nucleus.bp.model.compare.providers.ComparableProvider;
import com.mentor.nucleus.bp.model.compare.providers.NonRootModelElementComparable;

public class ActionComparable extends NonRootModelElementComparable {

	public ActionComparable(NonRootModelElement realElement) {
		super(realElement);
	}

	@Override
	public boolean treeItemEquals(Object other) {
		if (!super.treeItemEquals(other)) {
			if (other instanceof ActionComparable) {
				ActionComparable ahc = (ActionComparable) other;
				Action_c otherAction = (Action_c) ahc.getRealElement();
				Transition_c transition = Transition_c
						.getOneSM_TXNOnR530(TransitionActionHome_c
								.getOneSM_TAHOnR513(ActionHome_c
										.getOneSM_AHOnR514(otherAction)));
				if (transition != null) {
					Action_c thisAction = (Action_c) getRealElement();
					Transition_c thisTransition = Transition_c
							.getOneSM_TXNOnR530(TransitionActionHome_c
									.getOneSM_TAHOnR513(ActionHome_c
											.getOneSM_AHOnR514(thisAction)));
					ComparableTreeObject cto = ComparableProvider
							.getComparableTreeObject(transition);
					ComparableTreeObject thisCto = ComparableProvider
							.getComparableTreeObject(thisTransition);
					if (cto.treeItemEquals(thisCto)) {
						return true;
					} else {
						return false;
					}
				}
			}
		}
		return super.treeItemEquals(other);
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
		StateMachineState_c state = StateMachineState_c
				.getOneSM_STATEOnR503(
						StateEventMatrixEntry_c
								.getOneSM_SEMEOnR504(NewStateTransition_c
										.getOneSM_NSTXNOnR507(Transition_c
												.getOneSM_TXNOnR530(TransitionActionHome_c
														.getOneSM_TAHOnR513(ActionHome_c
																.getOneSM_AHOnR514((Action_c) getRealElement()))))));
		if(state != null) {
			return state.getSmstt_id().hashCode();
		} else {
			return super.hashCode();
		}
	}

}
