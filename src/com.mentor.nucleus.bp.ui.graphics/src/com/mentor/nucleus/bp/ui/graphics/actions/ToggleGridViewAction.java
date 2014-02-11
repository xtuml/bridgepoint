//========================================================================
//
//File:      $RCSfile: ToggleGridViewAction.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:05:57 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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
