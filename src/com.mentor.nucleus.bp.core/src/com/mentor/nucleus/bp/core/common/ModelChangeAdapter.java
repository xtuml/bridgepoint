//=====================================================================
//
//File:      $RCSfile: ModelChangeAdapter.java,v $
//Version:   $Revision: 1.14 $
//Modified:  $Date: 2013/05/10 13:26:31 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.core.common;

import java.util.Enumeration;

import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.SystemModel_c;

public abstract class ModelChangeAdapter implements IModelChangeListener {

	/**
	 * This method is called for batch enabled model change listeners 
	 */
	public void modelChanged(ModelChangedEvent event) {
		Enumeration deltaEnumeration = event.getModelDeltas();
     
		while(deltaEnumeration.hasMoreElements()){
			IModelDelta delta = (IModelDelta)deltaEnumeration.nextElement();

			switch(delta.getKind()){
			
			case Modeleventnotification_c.DELTA_ATTRIBUTE_CHANGE:
				modelElementAttributeChanged(event, delta);
				break;
			
			case Modeleventnotification_c.DELTA_DELETE:
				modelElementDeleted(event, delta);
				break;
			
			case Modeleventnotification_c.DELTA_ELEMENT_RELATED:
			case Modeleventnotification_c.DELTA_ELEMENT_UNRELATED:
				modelElementRelationChanged(event, delta);
				break;
			
			case Modeleventnotification_c.DELTA_NEW:
				modelElementCreated(event, delta);
				break;
				
			}
		}
	}
	
	public void modelElementDeleted(ModelChangedEvent event, IModelDelta delta){
		performDefault(event, delta);
	}

	public void modelElementAttributeChanged(ModelChangedEvent event, IModelDelta delta) {
		performDefault(event, delta);
	}

	public void modelElementCreated(ModelChangedEvent event, IModelDelta delta) {
		performDefault(event, delta);
	}

	public void modelElementRelationChanged(ModelChangedEvent event, IModelDelta delta) {
		performDefault(event, delta);
	}

	public void modelElementLoaded(ModelChangedEvent event) {
		performDefault(event, null);
	}

	public void modelElementReloaded(ModelChangedEvent event) {
		performDefault(event, null);
	}

	public void modelElementUnloaded(ModelChangedEvent event) {
		performDefault(event, null);
	}

	public void modelElementAboutToBeDeleted(ModelChangedEvent event) {
		performDefault(event, null);
	}

	public void modelElementAboutToBeReloaded(ModelChangedEvent event) {
		performDefault(event, null);
	}
	
	public void modelEventReceived(ModelChangedEvent event){
		performDefault(event, null);
	}
	public void modelElementRecreated(ModelChangedEvent event, IModelDelta delta) {
		performDefault(event, delta);
	}
	
	public void systemAboutToBeDisabled(SystemModel_c system) {
	}
	
	public void modelRootChanged(ModelChangedEvent event) {
		// do nothing by default
	}
	public boolean isBatchedNotificationEnabled() {
		return true;
	}
	protected void performDefault(ModelChangedEvent event, IModelDelta delta){}
	
	public boolean isSynchronous() {
	  return false;
	}
	
	public boolean isMaskable() {
	  return true;
	}
}
