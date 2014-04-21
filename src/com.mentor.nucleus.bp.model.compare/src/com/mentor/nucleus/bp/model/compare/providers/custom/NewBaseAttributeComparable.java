package com.mentor.nucleus.bp.model.compare.providers.custom;
//=====================================================================
//
//File:      $RCSfile: NewBaseAttributeComparable.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:35:46 $
//
//(c) Copyright 2013-2014 by Mentor Graphics Corp. All rights reserved.
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

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BaseAttribute_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.DerivedBaseAttribute_c;
import com.mentor.nucleus.bp.core.NewBaseAttribute_c;
import com.mentor.nucleus.bp.model.compare.providers.NonRootModelElementComparable;

public class NewBaseAttributeComparable extends NonRootModelElementComparable {

	public NewBaseAttributeComparable(NewBaseAttribute_c realElement) {
		super(realElement);
	}

	@Override
	public boolean treeItemEquals(Object other) {
		if (!super.treeItemEquals(other)) {
			// if the other element is a polymorphic event
			// return true if our state machine event supertypes
			// are equal
			if (other instanceof DerivedBaseAttributeComparable) {
				DerivedBaseAttributeComparable dbaComp = (DerivedBaseAttributeComparable) other;
				DerivedBaseAttribute_c dba = (DerivedBaseAttribute_c) dbaComp.getRealElement();
				NewBaseAttribute_c nba = (NewBaseAttribute_c) getRealElement();
				if(dba.getAttr_idCachedValue().equals(nba.getAttr_idCachedValue())) {
					return true;
				} else {
					return false;
				}
			} else {
				if(other instanceof NewBaseAttributeComparable) {
					// special-case for state<State_Model> attribute
					Attribute_c attribute = Attribute_c
							.getOneO_ATTROnR106(BaseAttribute_c
									.getOneO_BATTROnR107((NewBaseAttribute_c) getRealElement()));
					DataType_c dt = DataType_c.getOneS_DTOnR114(attribute);
					Attribute_c otherAttribute = Attribute_c
							.getOneO_ATTROnR106(BaseAttribute_c
									.getOneO_BATTROnR107((NewBaseAttribute_c) ((NonRootModelElementComparable) other)
											.getRealElement()));
					DataType_c otherDt = DataType_c
							.getOneS_DTOnR114(otherAttribute);
					if (dt.getName().equals("state<State_Model>")
							&& otherDt.getName().equals("state<State_Model>")) {
						return true;
					}
				}
				return false;
			}
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
		if(treeItemEquals(other) && !(other instanceof NewBaseAttributeComparable)) {
			return false;
		}
		return true;
	}

	@Override
	public String getText() {
		return "New Base Attribute";
	}
}
