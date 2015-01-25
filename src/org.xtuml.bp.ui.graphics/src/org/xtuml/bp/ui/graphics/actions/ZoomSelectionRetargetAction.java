package org.xtuml.bp.ui.graphics.actions;

import org.eclipse.ui.actions.RetargetAction;

import org.xtuml.bp.core.CorePlugin;

public class ZoomSelectionRetargetAction extends RetargetAction {

	public ZoomSelectionRetargetAction() {
		super(null, null);
		setText("Zoom Selection");
		setId(GraphicalActionConstants.ZOOM_SEL);
		setToolTipText("Click to zoom the current selection");
		setImageDescriptor(CorePlugin.getImageDescriptor("zoomSel.gif")); // $NON-NLS-1$
	}
	
}
