//========================================================================
//
//File:      $RCSfile: IModelDelta.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/01/10 22:54:11 $
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


/**
 * This interface defines the ModelDelta sent as part of ModelChangeEvent. Prior to this
 * issue the constants defined here were part of ooaofooa.mdl but now are made part of
 * this.
 */
public interface IModelDelta {

	/**
	 * Returns the kind of the ModelDelta, which one of the constants defined in the 
     * Modeleventnotification_c class.
	 * @return the kind of this model delta      
	 */
	public int getKind();
	
	/**
	 * 
	 * @return the source Model element of the model change event. 
	 *         This value must not be null.           
	 */
	public ModelElement getModelElement();
	
	/**
	 * Allow setting the model element associated with this delta
	 */
	public void setModelElement(ModelElement element);
}
