//========================================================================
//
//File:      $RCSfile: GraphicsViewport.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 23:06:17 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.figures;

import org.eclipse.draw2d.FreeformFigure;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.geometry.Point;

public class GraphicsViewport extends FreeformViewport {

	private Point fViewLocationAtConfiguration = null;
	
	@Override
	protected void readjustScrollBars() {
		super.readjustScrollBars();
		FreeformFigure ff = (FreeformFigure)getContents();
		if(ff.getBounds().width > 100 && fViewLocationAtConfiguration != null) {
			setViewLocation(fViewLocationAtConfiguration);
			fViewLocationAtConfiguration = null;
		}
	}
	
	public void setViewportLocationOnceConfigured(Point newLocation) {
		fViewLocationAtConfiguration = newLocation;
	}
}
