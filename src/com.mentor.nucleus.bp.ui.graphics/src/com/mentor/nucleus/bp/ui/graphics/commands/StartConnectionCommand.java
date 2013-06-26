//========================================================================
//
//File:      $RCSfile: StartConnectionCommand.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:05:45 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

public class StartConnectionCommand extends Command {

	private Point fPoint;

	public StartConnectionCommand(Point startPoint) {
		super();
		fPoint = startPoint;
	}
	
	public Point getStartPoint() {
		return fPoint;
	}

}
