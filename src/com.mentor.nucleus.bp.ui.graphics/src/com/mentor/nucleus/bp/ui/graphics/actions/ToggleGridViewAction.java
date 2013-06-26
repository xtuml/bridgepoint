//========================================================================
//
//File:      $RCSfile: ToggleGridViewAction.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:05:57 $
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

public class ToggleGridViewAction extends Action {

	public static final String TOGGLE_GRID = "com.mentor.nucleus.bp.ui.graphics.actions.ToggleGrid"; //$NON-NLS-1$
	private GraphicalViewer diagramViewer;

	public ToggleGridViewAction(GraphicalViewer diagramViewer) {
		super("Show Grid", AS_CHECK_BOX);
		this.diagramViewer = diagramViewer;
		setToolTipText("Check to enable the grid view");
		setId(TOGGLE_GRID);
		setActionDefinitionId(TOGGLE_GRID);
		setChecked(isChecked());
	}

	@Override
	public void run() {
		boolean val = !isChecked();
		diagramViewer.setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE, new Boolean(val));
	}

	@Override
	public boolean isChecked() {
		Boolean val = (Boolean)diagramViewer.getProperty(SnapToGrid.PROPERTY_GRID_VISIBLE);
		if (val != null)
			return val.booleanValue();
		return false;
	}

}
