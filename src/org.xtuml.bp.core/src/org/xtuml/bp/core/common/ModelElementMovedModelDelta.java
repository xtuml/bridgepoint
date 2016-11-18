
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

package org.xtuml.bp.core.common;

import org.xtuml.bp.core.Modeleventnotification_c;

public class ModelElementMovedModelDelta extends BaseModelDelta {

	private NonRootModelElement destination;
	// store the source from which the element was removed from
	private NonRootModelElement elementSource;
	
	/**
	 * @param eventType - 
	 * @param modelElement - the model element being moved
	 * @param oldValue - the original (source) PMC of the element being moved
	 * @param newValue - the destination PMC of the element being moved
	 */
	public ModelElementMovedModelDelta(ModelElement modelElement, NonRootModelElement destination, NonRootModelElement source) {
		super(Modeleventnotification_c.DELTA_MODEL_ELEMENT_MOVE, modelElement);
		this.destination = destination;
		this.elementSource = source;
	}

	public NonRootModelElement getDestination() {
		return destination;
	}

	public void setDestination(NonRootModelElement newValue) {
		this.destination = newValue;
	}

	public void setSource(NonRootModelElement newValue) {
		this.elementSource = newValue;
	}
	
	public NonRootModelElement getSource() {
		return elementSource;
	}
}
