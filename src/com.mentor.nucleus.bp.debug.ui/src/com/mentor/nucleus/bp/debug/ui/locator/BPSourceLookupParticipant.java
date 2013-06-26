package com.mentor.nucleus.bp.debug.ui.locator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.debug.ui.model.BPStackFrame;
import com.mentor.nucleus.bp.ui.text.ModelAdapter;
import com.mentor.nucleus.bp.ui.text.ModelElementID;
import com.mentor.nucleus.bp.ui.text.placeholder.PlaceHolderManager;

public class BPSourceLookupParticipant extends AbstractSourceLookupParticipant {

	public BPSourceLookupParticipant() {
		super();
	}

	public Object [] findSourceElements(Object object) throws CoreException {
	  if (object instanceof BPStackFrame) {
	    if (((BPStackFrame)object).getEvent() == null) {
	      return super.findSourceElements(object);
	    }
	    else {
	      Object [] result = new Object[1];
	      result[0] = ((BPStackFrame)object).getEvent(); 
	      return result;
	    }
	  }
	  return EMPTY;
	}
	
	public String getSourceName(Object object) throws CoreException {
		PlaceHolderManager plhMgr = PlaceHolderManager.getDefaultInstance();
		if (object instanceof BPStackFrame) {
            NonRootModelElement activity = (NonRootModelElement)((BPStackFrame)object).getActivityContainerElement();
            if (activity != null) {
              ModelElementID modelElementID = ModelAdapter.getModelElementAdapter(activity).createModelElementID(activity);
              if (modelElementID != null) {
                IFile result = plhMgr.getPlaceholderFile(modelElementID, "oal", this);
			    result.createMarker("com.mentor.nucleus.bp.ui.text.stepmarker");
			    return result.getName();
              }
            }
		}
		return null;
	}

}
