package com.mentor.nucleus.bp.model.compare.providers.custom;
//=====================================================================
//
//File:      $RCSfile: AssociationComparable.java,v $
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

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.model.compare.ComparableTreeObject;
import com.mentor.nucleus.bp.model.compare.providers.NonRootModelElementComparable;

public class AssociationComparable extends NonRootModelElementComparable {

	public AssociationComparable(NonRootModelElement realElement) {
		super(realElement);
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
		// if the formalization state does not match do not
		// return true
		Association_c thisAssoc = (Association_c) getRealElement();
		Association_c otherAssoc = (Association_c) ((ComparableTreeObject) other).getRealElement();
		if (thisAssoc.Isformalized() && !otherAssoc.Isformalized()
				|| !thisAssoc.Isformalized() && otherAssoc.Isformalized()) {
			return false;
		}
		return true;
	}

}
