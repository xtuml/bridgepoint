package com.mentor.nucleus.bp.core.ui.marker;
//========================================================================
//
//File:      $RCSfile: ProblemModelChangeListener.java,v $
//Version:   $Revision: 1.14 $
//Modified:  $Date: 2013/05/10 13:26:25 $
//
//(c) Copyright 2005-2012 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.IModelChangeListener;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;

public class ProblemModelChangeListener implements IModelChangeListener {

    public void modelChanged(ModelChangedEvent event) {
    }

    public void modelElementDeleted(ModelChangedEvent event, IModelDelta delta) {
        NonRootModelElement me = (NonRootModelElement) delta.getModelElement();
        if (me != null)
            UmlProblem.elementDeleted(me);
    }

    public void modelElementAttributeChanged(ModelChangedEvent event,
            IModelDelta delta) {
    }

    public void modelElementCreated(ModelChangedEvent event, IModelDelta delta) {
    }

    public void modelElementRecreated(ModelChangedEvent event, IModelDelta delta) {
    }

    public void modelElementRelationChanged(ModelChangedEvent event,
            IModelDelta delta) {
    }

    public void modelElementLoaded(ModelChangedEvent event) {
    }

    public void modelElementUnloaded(ModelChangedEvent event) {
    }

    public void modelElementReloaded(ModelChangedEvent event) {
        UmlProblem.endCollectingProxies();
    }

    public void modelElementAboutToBeReloaded(ModelChangedEvent event) {
        UmlProblem.startCollectingProxies();
    }

    public void modelElementAboutToBeDeleted(ModelChangedEvent event) {
    }

    public void modelEventReceived(ModelChangedEvent event) {
    }

    public boolean isBatchedNotificationEnabled() {
        return false;
    }

	public void modelRootChanged(ModelChangedEvent event) {
		// do nothing
	}

	public void systemAboutToBeDisabled(SystemModel_c system) {
		// do nothing
	}
	
  @Override
  public boolean isSynchronous() {
    return false;
  }

  @Override
  public boolean isMaskable() {
    return true;
  }

}
