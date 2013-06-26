package com.mentor.nucleus.bp.model.compare.providers;
//=====================================================================
//
//File:      $RCSfile: OverlayCompositeImageDescriptor.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:35:40 $
//
//(c) Copyright 2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

public class OverlayCompositeImageDescriptor extends CompositeImageDescriptor {

	private ImageData overlay;
	private ImageData base;
	private Point SIZE = new Point(16, 16);

	public OverlayCompositeImageDescriptor(ImageData base, ImageData overlay) {
		this.base = base;
		this.overlay = overlay;
	}
	
	@Override
	protected void drawCompositeImage(int width, int height) {
		super.drawImage(base, 0, 0);
		super.drawImage(overlay, 0, 0);
	}

	@Override
	protected Point getSize() {
		return SIZE ;
	}

}
