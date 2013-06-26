//========================================================================
//
//File:      $RCSfile: GraphicsFreeformLayeredPane.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:06:17 $
//
//Copyright (c) 2005-2013 Mentor Graphics Corporation.  All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
package com.mentor.nucleus.bp.ui.graphics.figures;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FreeformLayeredPane;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;

public class GraphicsFreeformLayeredPane extends
		FreeformLayeredPane {

	@Override
	protected void paintChildren(Graphics graphics) {
		IFigure child;

		Rectangle clip = Rectangle.SINGLETON;
		
		List<IFigure> connectionLayers = new ArrayList<IFigure>();
		for (int i = 0; i < getChildren().size(); i++) {
			child = (IFigure)getChildren().get(i);
			// skip all user defined connection layers, and draw after
			if(child instanceof ConnectionLayer) {
				connectionLayers.add(child);
				continue;
			}
			if (child.isVisible() && child.intersects(graphics.getClip(clip))) {
				graphics.clipRect(child.getBounds());
				child.paint(graphics);
				graphics.restoreState();
			}
		}
		for(IFigure conLayer : connectionLayers) {
			if(conLayer.isVisible() && conLayer.intersects(graphics.getClip(clip))) {
				graphics.clipRect(conLayer.getBounds());
				conLayer.paint(graphics);
				graphics.restoreState();
			}
		}
	}

}
