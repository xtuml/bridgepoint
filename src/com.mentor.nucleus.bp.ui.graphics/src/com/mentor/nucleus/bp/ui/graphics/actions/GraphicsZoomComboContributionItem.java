//========================================================================
//
//File:      $RCSfile: GraphicsZoomComboContributionItem.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:05:58 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.actions;

import org.eclipse.gef.ui.actions.ZoomComboContributionItem;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPartService;

public class GraphicsZoomComboContributionItem extends
		ZoomComboContributionItem {

	public GraphicsZoomComboContributionItem(IPartService partService, String[] zoomStrings) {
		super(partService, zoomStrings);
	}

	@Override
	protected Control createControl(Composite parent) {
		Combo combo = (Combo) super.createControl(parent);
		combo.setVisibleItemCount(20);
		return combo;
	}

	
}
