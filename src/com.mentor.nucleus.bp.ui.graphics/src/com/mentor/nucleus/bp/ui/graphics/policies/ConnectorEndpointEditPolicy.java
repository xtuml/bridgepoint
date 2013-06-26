package com.mentor.nucleus.bp.ui.graphics.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.handles.ConnectionEndpointHandle;
import org.eclipse.gef.requests.ReconnectRequest;

import com.mentor.nucleus.bp.ui.graphics.anchors.WSAnchor;
import com.mentor.nucleus.bp.ui.graphics.handles.SnappingConnectionEndPointHandle;

public class ConnectorEndpointEditPolicy extends ConnectionEndpointEditPolicy {

	private int fPreviousLineWidth = 0;

	public ConnectorEndpointEditPolicy(int configuredLineWidth) {
		fPreviousLineWidth = configuredLineWidth;
	}

	@Override
	protected List<?> createSelectionHandles() {
		fPreviousLineWidth = ((PolylineConnection) getHostFigure())
				.getLineWidth();
		if (fPreviousLineWidth == 0) {
			((PolylineConnection) getConnection()).setLineWidth(2);
		} else {
			((PolylineConnection) getConnection())
					.setLineWidth(fPreviousLineWidth * 2);
		}
		List<ConnectionEndpointHandle> list = new ArrayList<ConnectionEndpointHandle>();
		list.add(new SnappingConnectionEndPointHandle(
				(ConnectionEditPart) getHost(), ConnectionLocator.SOURCE));
		list.add(new SnappingConnectionEndPointHandle(
				(ConnectionEditPart) getHost(), ConnectionLocator.TARGET));
		return list;
	}

	@Override
	protected void removeSelectionHandles() {
		super.removeSelectionHandles();
		((PolylineConnection) getConnection()).setLineWidth(fPreviousLineWidth);
	}

	@Override
	protected void showConnectionMoveFeedback(ReconnectRequest request) {
		super.showConnectionMoveFeedback(request);
		if (request.getTarget() == null) {
			// never use the host figure as that will cause
			// an infinite loop
			WSAnchor anchor = WSAnchor.getWSAnchorFor(request, getHostFigure()
					.getParent());
			getFeedbackHelper(request).update(anchor, null);
		}
	}

}
