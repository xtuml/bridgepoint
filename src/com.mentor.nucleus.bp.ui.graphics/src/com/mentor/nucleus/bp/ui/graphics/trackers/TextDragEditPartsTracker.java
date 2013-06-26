//========================================================================
//
//File:      $RCSfile: TextDragEditPartsTracker.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:05:53 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.trackers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.DragEditPartsTracker;
import org.eclipse.gef.tools.ToolUtilities;

import com.mentor.nucleus.bp.ui.graphics.parts.TextEditPart;
import com.mentor.nucleus.bp.ui.graphics.requests.TextEditPartChangeBoundsRequest;

public class TextDragEditPartsTracker extends DragEditPartsTracker {

	public TextDragEditPartsTracker(EditPart sourceEditPart) {
		super(sourceEditPart);
	}

	@Override
	protected boolean isMove() {
		EditPart part = getSourceEditPart();
		while (part != getTargetEditPart() && part != null) {
			if (part.getSelected() != EditPart.SELECTED_NONE)
				return true;
			part = part.getParent();
		}
		return false;
	}

	@Override
	protected void snapPoint(ChangeBoundsRequest request) {
		// do not snap text
	}

	@Override
	protected List<Object> createOperationSet() {
		if (getCurrentViewer() != null) {
			List<?> list = ToolUtilities
					.getSelectionWithoutDependants(getCurrentViewer());
			// add any text edit parts if existing
			// they are always selected along with
			// the parent, but as a secondary selection
			// according to GEF
			List<Object> additionalSelection = new ArrayList<Object>();
			additionalSelection.addAll(list);
			for (Object selected : list) {
				EditPart part = (EditPart) selected;
				List<?> children = part.getChildren();
				for (Object child : children) {
					if (child instanceof TextEditPart) {
						additionalSelection.add(child);
					}
				}
			}
			ToolUtilities.filterEditPartsUnderstanding(additionalSelection,
					getTargetRequest());
			return additionalSelection;
		}

		return new ArrayList<Object>();
	}

	@Override
	protected Request createTargetRequest() {
		return new TextEditPartChangeBoundsRequest(REQ_MOVE,
				(GraphicalEditPart) getSourceEditPart());
	}

}
