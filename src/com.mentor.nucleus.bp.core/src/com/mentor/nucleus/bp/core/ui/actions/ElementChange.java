//========================================================================
//
//File:      $RCSfile: ElementChange.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2012/03/23 18:47:39 $
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
package com.mentor.nucleus.bp.core.ui.actions;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;

public class ElementChange {
	private boolean isRemoval = false;
	private String changeLabel = "";
	private Object elementChanged = null;

	public ElementChange(Object elementChanged,
			boolean isDeletion) {
		this(elementChanged, isDeletion, "");
	}
	public ElementChange(Object elementChanged,
			boolean isRemoval, String changeLabel) {
		this.isRemoval = isRemoval;
		this.elementChanged = elementChanged;
		this.changeLabel = changeLabel;
	}
	
	public NonRootModelElement getElementChanged() {
		return (NonRootModelElement) elementChanged;
	}
	
	public boolean isRemoval() {
		return isRemoval;
	}
	
	public String getChangeLabel() {
		return changeLabel;
	}
}
