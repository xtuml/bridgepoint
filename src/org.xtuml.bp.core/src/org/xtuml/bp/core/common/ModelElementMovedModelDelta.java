
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

import java.util.HashSet;

import org.xtuml.bp.core.Modeleventnotification_c;

public class ModelElementMovedModelDelta extends BaseModelDelta {

	// We store off the RGOs now before we start changing this in memory
	// because in order to find RGOs we take advantage of ExternalLinkEvaluator.java
	// and it uses PersistenceHierarchyMetaData.java which together use the
	// folder structure in combination with loaded instance to determine RGOs
	// Once we start moving things around, we can't determine RGOs again until
	// the change is complete both in memory and on disk. So, we get the RGOs now so
	// we can persist them later when persistence is done in 
	// ComponentTransactionListener.java::endTransaction.
	private HashSet<PersistableModelComponent> rgosAffectedByMove;
	private NonRootModelElement destination;
	
	/**
	 * @param eventType - 
	 * @param modelElement - the model element being moved
	 * @param oldValue - the original (source) PMC of the element being moved
	 * @param newValue - the destination PMC of the element being moved
	 */
	public ModelElementMovedModelDelta(ModelElement modelElement, NonRootModelElement destination, HashSet<PersistableModelComponent> rgos) {
		super(Modeleventnotification_c.DELTA_MODEL_ELEMENT_MOVE, modelElement);
		this.destination = destination;
		this.rgosAffectedByMove = rgos;
	}

	public NonRootModelElement getDestination() {
		return destination;
	}

	public void setDestination(NonRootModelElement newValue) {
		this.destination = newValue;
	}

	public HashSet<PersistableModelComponent> getRGOsAffectedByMove() {
		return rgosAffectedByMove;
	}
}
