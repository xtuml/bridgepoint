//========================================================================
//
//File:      $RCSfile: ShapeTextLayout.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:05:59 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.layout;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;

public class ShapeTextLayout extends XYLayout {

	@Override
	public Point getOrigin(IFigure parent) {
		// we do not want an adjustment for
		// text
		return new Point();
	}

}
