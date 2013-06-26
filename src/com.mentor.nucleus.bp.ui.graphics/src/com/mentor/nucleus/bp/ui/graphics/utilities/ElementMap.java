//========================================================================
//
//File:      $RCSfile: ElementMap.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/17 03:29:32 $
//
//Copyright (c) 2005-2013 Mentor Graphics Corporation.  All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
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
