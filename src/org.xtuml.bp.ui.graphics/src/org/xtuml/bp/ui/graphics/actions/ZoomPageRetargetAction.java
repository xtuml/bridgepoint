package org.xtuml.bp.ui.graphics.actions;

import org.eclipse.ui.actions.RetargetAction;

import org.xtuml.bp.core.CorePlugin;

public class ZoomPageRetargetAction extends RetargetAction {

	public ZoomPageRetargetAction() {
		super(null, null);
		setText("Zoom Page");
		setId(GraphicalActionConstants.ZOOM_PAGE);
		setToolTipText("Click to zoom the entire contents");
		setImageDescriptor(CorePlugin.getImageDescriptor("zoomAll.gif")); // $NON-NLS-1$
	}

}
