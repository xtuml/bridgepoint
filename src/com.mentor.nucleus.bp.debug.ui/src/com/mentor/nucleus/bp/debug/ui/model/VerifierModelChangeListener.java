package com.mentor.nucleus.bp.debug.ui.model;

import org.eclipse.debug.core.DebugEvent;

import com.mentor.nucleus.bp.core.Body_c;
import com.mentor.nucleus.bp.core.StackFrame_c;
import com.mentor.nucleus.bp.core.Stack_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.AttributeChangeModelDelta;
import com.mentor.nucleus.bp.core.common.IModelChangeListener;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.OALPersistenceUtil;

public class VerifierModelChangeListener implements IModelChangeListener {
	
	BPThread m_client = null;
	
	public VerifierModelChangeListener(BPThread client) {
	  m_client = client;	
	}

	// We only care about action semantics changes.
	// Ignore every other change event.
	public void modelChanged(ModelChangedEvent event) {
	}

	public void modelElementDeleted(ModelChangedEvent event, IModelDelta delta) {
	}

	public void modelElementRecreated(ModelChangedEvent event, IModelDelta delta) {
	}

	public void modelElementAttributeChanged(ModelChangedEvent event,
			IModelDelta delta) {
      AttributeChangeModelDelta attrDelta = (AttributeChangeModelDelta)event.
                                                                getModelDelta();
      if (attrDelta.getAttributeName().equals("Action_semantics_internal")) {
      	ModelElement me = attrDelta.getModelElement();
      	Body_c bdy = OALPersistenceUtil.getOALModelElement(me);
      	if (bdy != null) {
            StackFrame_c sf = StackFrame_c.getOneI_STFOnR2929(
            		      Stack_c.getManyI_STACKsOnR2930(m_client.getEngine()));
            if (sf != null) {
              sf.Resetifrequired(bdy.getAction_id());
      		  m_client.fireChangeEvent(DebugEvent.CONTENT);
            }
        }
        else {
          // TODO Log error
        }
      }
      else {
      	// TODO Log error
      }
	}

	public void modelElementCreated(ModelChangedEvent event, IModelDelta delta) {
	}

	public void modelElementRelationChanged(ModelChangedEvent event,
			IModelDelta delta) {
	}

	public void modelElementLoaded(ModelChangedEvent event) {
	}

	public void modelElementUnloaded(ModelChangedEvent event) {
	}

	public void modelElementReloaded(ModelChangedEvent event) {
	}

	public void modelElementAboutToBeReloaded(ModelChangedEvent event) {
	}

	public void modelElementAboutToBeDeleted(ModelChangedEvent event) {
	}

	public void modelEventReceived(ModelChangedEvent event) {
	}
	
	public void systemAboutToBeDisabled(SystemModel_c system) {
	}

	public boolean isBatchedNotificationEnabled() {
		return false;
	}

	public void modelRootChanged(ModelChangedEvent event) {
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
