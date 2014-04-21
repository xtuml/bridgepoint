//========================================================================
//
// File: StateMachineComparable.java
//
// Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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

package com.mentor.nucleus.bp.model.compare.providers.custom;

import java.util.UUID;

import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.model.compare.providers.NonRootModelElementComparable;

public class StateMachineComparable extends NonRootModelElementComparable {

	/**
	 * @param realElement
	 */
	public StateMachineComparable(NonRootModelElement realElement) {
		super(realElement);
	}

	@Override
	public boolean treeItemEquals(Object other) {
		if(other instanceof StateMachineComparable) {
			Object otherElement = ((StateMachineComparable) other).getRealElement();
			Object thisElement = getRealElement();
			if (otherElement instanceof InstanceStateMachine_c
					&& thisElement instanceof InstanceStateMachine_c) {
				InstanceStateMachine_c otherISM = (InstanceStateMachine_c) otherElement;
				InstanceStateMachine_c thisISM = (InstanceStateMachine_c) thisElement;
				ModelClass_c otherClass = ModelClass_c.getOneO_OBJOnR518(otherISM);
				ModelClass_c thisClass = ModelClass_c.getOneO_OBJOnR518(thisISM);
				if(otherClass != null && thisClass != null) {
					if(otherClass.identityEquals(thisClass)) {
						return true;
					}
				}
			}
			if (otherElement instanceof ClassStateMachine_c
					&& thisElement instanceof ClassStateMachine_c) {
				ClassStateMachine_c otherASM = (ClassStateMachine_c) otherElement;
				ClassStateMachine_c thisASM = (ClassStateMachine_c) thisElement;
				ModelClass_c otherClass = ModelClass_c.getOneO_OBJOnR519(otherASM);
				ModelClass_c thisClass = ModelClass_c.getOneO_OBJOnR519(thisASM);
				if(otherClass != null && thisClass != null) {
					if(otherClass.identityEquals(thisClass)) {
						return true;
					}
				}
			}			
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (getRealElement() instanceof InstanceStateMachine_c) {
			InstanceStateMachine_c thisISM = (InstanceStateMachine_c) getRealElement();
			ModelClass_c thisClass = ModelClass_c.getOneO_OBJOnR518(thisISM);
			if(thisClass != null) {
				return thisClass.getObj_id().hashCode();
			}
		}
		if (getRealElement() instanceof ClassStateMachine_c) {
			ClassStateMachine_c thisASM = (ClassStateMachine_c) getRealElement();
			ModelClass_c thisClass = ModelClass_c.getOneO_OBJOnR519(thisASM);
			if(thisClass != null) {
				return thisClass.getObj_id().hashCode();
			}
		}
		return getClass().getName().hashCode();
	}

	public UUID getSmID() {
		if (getRealElement() instanceof InstanceStateMachine_c) {
			InstanceStateMachine_c thisISM = (InstanceStateMachine_c) getRealElement();
			return thisISM.getSm_id();
		}
		if (getRealElement() instanceof ClassStateMachine_c) {
			ClassStateMachine_c thisASM = (ClassStateMachine_c) getRealElement();
			return thisASM.getSm_id();
		}
		return Gd_c.Null_unique_id();
	}
}
