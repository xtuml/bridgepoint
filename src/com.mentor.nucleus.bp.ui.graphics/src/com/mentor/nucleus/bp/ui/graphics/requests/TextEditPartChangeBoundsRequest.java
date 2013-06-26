package com.mentor.nucleus.bp.ui.graphics.requests;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.requests.ChangeBoundsRequest;

public class TextEditPartChangeBoundsRequest extends ChangeBoundsRequest {

	private GraphicalEditPart target;

	public TextEditPartChangeBoundsRequest(String type, GraphicalEditPart target) {
		super(type);
		this.target = target;
	}

	public GraphicalEditPart getTarget() {
		return target;
	}
}
