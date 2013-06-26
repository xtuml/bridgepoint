//========================================================================
//
//File:      $RCSfile: FixedTextLocator.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:06:00 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.layout;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Locator;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

import com.mentor.nucleus.bp.ui.canvas.Direction_c;
import com.mentor.nucleus.bp.ui.canvas.Gr_c;

public class FixedTextLocator implements Locator {

	private boolean fIsEnd;
	private Connection fConnection;

	public FixedTextLocator(Connection connection, boolean isEnd) {
		fConnection = connection;
		fIsEnd = isEnd;
	}

	@Override
	public void relocate(IFigure target) {
		float angle = 0f;
		int text_x = 0;
		int text_y = 0;
		if (fIsEnd) {
			text_x = fConnection.getPoints().getLastPoint().x;
			text_y = fConnection.getPoints().getLastPoint().y;
			angle = Gr_c.Getangle(fConnection.getPoints().getLastPoint().x,
					fConnection.getPoints().getPoint(
							fConnection.getPoints().size() - 2).x, fConnection
							.getPoints().getLastPoint().y, fConnection
							.getPoints().getPoint(
									fConnection.getPoints().size() - 2).y);
		} else {
			text_x = fConnection.getPoints().getFirstPoint().x;
			text_y = fConnection.getPoints().getFirstPoint().y;
			angle = Gr_c.Getangle(fConnection.getPoints().getFirstPoint().x,
					fConnection.getPoints().getPoint(1).x, fConnection
							.getPoints().getFirstPoint().y, fConnection
							.getPoints().getPoint(1).y);
		}
		int direction = Gr_c.Getdirection(angle);
		int spacing = Gr_c.Getgraphicspacing() + 3;
		Label label = (Label) target;
		Dimension stringExtents = FigureUtilities.getStringExtents(label
				.getText(), label.getFont());
		switch (direction) {
		case Direction_c.East:
			text_x = text_x + spacing;
			text_y = text_y - spacing - stringExtents.height;
			break;
		case Direction_c.South:
			text_x = text_x - spacing - stringExtents.width;
			text_y = text_y + spacing;
			break;
		case Direction_c.North:
			text_x = text_x - spacing - stringExtents.width;
			text_y = text_y - spacing - stringExtents.height;
			break;
		case Direction_c.West:
			text_x = text_x - spacing - stringExtents.width;
			text_y = text_y - spacing - stringExtents.height;
			break;
		default:
			break;
		}
		target.setSize(stringExtents);
		target.setLocation(new Point(text_x, text_y));
	}

}
