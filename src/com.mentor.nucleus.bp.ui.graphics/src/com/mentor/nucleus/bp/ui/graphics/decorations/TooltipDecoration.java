//========================================================================
//
//File:      $RCSfile: TooltipDecoration.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:05:56 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.decorations;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.geometry.PointList;

/**
 * A TooltipDecoration is one that shows nothing, same as having no decoration.
 * The exception is that it will allow for tool tip usage.
 * 
 * @author tlondon
 *
 */
public class TooltipDecoration extends PolygonDecoration {

	public TooltipDecoration(String tooltip) {
		super();
		if(!tooltip.equals("")) {
			Label label = new Label(tooltip);
			label.setBorder(new MarginBorder(3, 3, 3, 3));
			setToolTip(label);
		}
		setScale(1, 1);
		PointList points = new PointList();
		ConnectorPolygonDecoration.createFilledSquare(points);
		setTemplate(points);
	}

	@Override
	public void setRotation(double angle) {
		// do not adjust for rotation
	}

	@Override
	protected void outlineShape(Graphics g) {
		// we don't actually want this one
		// to be drawn
	}

	@Override
	protected void fillShape(Graphics g) {
		// we don't actually want this one
		// to be filled
	}

}
