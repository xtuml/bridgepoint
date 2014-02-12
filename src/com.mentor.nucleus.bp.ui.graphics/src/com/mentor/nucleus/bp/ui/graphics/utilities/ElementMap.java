//========================================================================
//
//File:      $RCSfile: ElementMap.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/17 03:29:32 $
//
//Copyright (c) 2005-2014 Mentor Graphics Corporation.  All rights reserved.
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
package com.mentor.nucleus.bp.ui.graphics.utilities;

import java.util.HashMap;
import java.util.UUID;

import com.mentor.nucleus.bp.core.common.AttributeChangeModelDelta;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.ModelChangeAdapter;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class ElementMap extends ModelChangeAdapter {
	
	private HashMap<UUID, GraphicalElement_c> elementMap = new HashMap<UUID, GraphicalElement_c>();

	public ElementMap() {
		// register self
		Ooaofgraphics.getDefaultInstance().addModelChangeListener(this);
	}
	
	@Override
	public boolean isBatchedNotificationEnabled() {
		return false;
	}

	public void dispose() {
		Ooaofgraphics.getDefaultInstance().removeModelChangeListener(this);
	}

	@Override
	public void modelElementAttributeChanged(ModelChangedEvent event,
			IModelDelta delta) {
		// listen for setRepresents() changes
		AttributeChangeModelDelta aDelta = (AttributeChangeModelDelta) delta;
		if (aDelta.getAttributeName().equals("Represents") && aDelta.getModelElement() instanceof GraphicalElement_c) { //$NON-NLS-1$
			if(aDelta.getNewValue() == null) {
				elementMap.remove(((NonRootModelElement) aDelta.getOldValue())
						.Get_ooa_id());
				return;
			}
			if(aDelta.getNewValue() instanceof String) {
				return;
			}
			// add the element to the map
			elementMap.put(Cl_c.Getooa_idfrominstance(aDelta.getNewValue()),
					(GraphicalElement_c) aDelta.getModelElement());
		}
	}

	public GraphicalElement_c getElement(UUID representsId) {
		return elementMap.get(representsId);
	}

	@Override
	public boolean isMaskable() {
		return false;
	}

	@Override
	public boolean isSynchronous() {
		return true;
	}

	public void addElement(GraphicalElement_c element, UUID elementId) {
		elementMap.put(elementId, element);
	}
	
}
