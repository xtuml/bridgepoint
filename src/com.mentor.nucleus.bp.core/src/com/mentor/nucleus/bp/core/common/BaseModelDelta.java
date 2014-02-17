//========================================================================
//
//File:      $RCSfile: BaseModelDelta.java,v $
//Version:   $Revision: 1.14 $
//Modified:  $Date: 2013/01/10 22:54:09 $
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

package com.mentor.nucleus.bp.core.common;



public class BaseModelDelta implements IModelDelta {

	private int kind;
	private ModelElement modelElement;
	
	boolean isIgnored = false;
	
	public BaseModelDelta(int deltaKind, ModelElement srcModelElement){
		kind = deltaKind;
		modelElement = srcModelElement;
	}
	
	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.core.common.IModelDelta#getKind()
	 */
	public int getKind() {
		return kind;
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.core.common.IModelDelta#getModelElement()
	 */
	public ModelElement getModelElement() {
		return modelElement;
	}
	
	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.core.common.IModelDelta#setModelElement()
	 */
	public void setModelElement(ModelElement element) {
		modelElement = element;
	}

}
