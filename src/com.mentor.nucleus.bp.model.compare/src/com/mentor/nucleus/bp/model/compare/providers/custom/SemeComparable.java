//========================================================================
//
// File: SemeComparable.java
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

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.model.compare.providers.NonRootModelElementComparable;

public class SemeComparable extends NonRootModelElementComparable {

	/**
	 * @param realElement
	 */
	public SemeComparable(NonRootModelElement realElement) {
		super(realElement);
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.model.compare.ComparableTreeObject#ignoreOrdering()
	 */
	@Override
	public boolean ignoreOrdering() {
		return true;
	}
	
}
