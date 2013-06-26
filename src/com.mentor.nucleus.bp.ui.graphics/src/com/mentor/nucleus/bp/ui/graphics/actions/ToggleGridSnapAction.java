//========================================================================
//
//File:      $RCSfile: ToggleGridSnapAction.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:05:58 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.actions;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.jface.action.Action;

public class ToggleGridSnapAction extends Action {

	public static final String TOGGLE_GRID_SNAP = "com.mentor.nucleus.bp.ui.graphics.actions.ToggleGridSnap"; //$NON-NLS-1$
	private GraphicalViewer diagramViewer;

	public ToggleGridSnapAction(GraphicalViewer diagramViewer) {
		super("Enable Grid Snapping", AS_CHECK_BOX);
		this.diagramViewer = diagramViewer;
		setToolTipText("Check to enable grid snapping");
		setId(TOGGLE_GRID_SNAP);
		setActionDefinitionId(TOGGLE_GRID_SNAP);
		setChecked(isChecked());
	}

	@Override
	public void run() {
		boolean val = !isChecked();
		diagramViewer.setProperty(SnapToGrid.PROPERTY_GRID_ENABLED, new Boolean(val));
	}

	@Override
	public boolean isChecked() {
		Boolean val = (Boolean)diagramViewer.getProperty(SnapToGrid.PROPERTY_GRID_ENABLED);
		if (val != null)
			return val.booleanValue();
		return false;
	}

}
