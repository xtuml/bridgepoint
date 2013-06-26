package com.mentor.nucleus.bp.ui.graphics.draw;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;

public class PrecisionBendpoint extends PrecisionPoint implements Bendpoint {

	private static final long serialVersionUID = 1;

	public PrecisionBendpoint(Point p) {
		super(p);
	}

	public PrecisionBendpoint(int x, int y) {
		super(x, y);
	}
	
	@Override
	public Point getLocation() {
		return this;
	}

}
