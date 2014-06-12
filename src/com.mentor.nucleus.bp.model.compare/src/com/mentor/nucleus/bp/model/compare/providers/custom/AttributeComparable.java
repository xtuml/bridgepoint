//========================================================================
//
// File: AttributeComparable.java
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

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.model.compare.providers.NonRootModelElementComparable;

public class AttributeComparable extends NonRootModelElementComparable {

	/**
	 * @param realElement
	 */
	public AttributeComparable(NonRootModelElement realElement) {
		super(realElement);
	}

	@Override
	public boolean treeItemEquals(Object other) {
		// consider two current_state attributes the
		// same at all times
		if (other instanceof AttributeComparable) {
			AttributeComparable otherAttributeComp = (AttributeComparable) other;
			Attribute_c otherAttr = (Attribute_c) otherAttributeComp
					.getRealElement();
			Attribute_c thisAttr = (Attribute_c) getRealElement();
			ModelClass_c otherClass = ModelClass_c.getOneO_OBJOnR102(otherAttr);
			ModelClass_c thisClass = ModelClass_c.getOneO_OBJOnR102(thisAttr);
			if(otherClass == null || thisClass == null) {
				return super.treeItemEquals(other);
			}
			if (otherClass.cachedIdentityEquals(thisClass)) {
				DataType_c otherDt = DataType_c.getOneS_DTOnR114(otherAttr);
				DataType_c thisDt = DataType_c.getOneS_DTOnR114(thisAttr);
				if (otherDt.getName().equals("state<State_Model>")
						&& thisDt.getName().equals("state<State_Model>")) {
					return true;
				}
			}
		}
		return super.treeItemEquals(other);
	}

}
