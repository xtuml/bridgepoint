//========================================================================
//
//File:      $RCSfile: GraphicsConnectionCreationToolEntry.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:05:49 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.palette;

import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.jface.resource.ImageDescriptor;

import com.mentor.nucleus.bp.ui.graphics.tools.GraphicsConnectionCreationTool;

public class GraphicsConnectionCreationToolEntry extends
		ConnectionCreationToolEntry {

	private int type;

	public GraphicsConnectionCreationToolEntry(String label, String shortDesc,
			CreationFactory factory, ImageDescriptor iconSmall,
			ImageDescriptor iconLarge, int ooaType) {
		super(label, shortDesc, factory, iconSmall, iconLarge);
		type = ooaType;
	}

	@Override
	public Tool createTool() {
		return new GraphicsConnectionCreationTool(
				(CreationFactory) getToolProperty(CreationTool.PROPERTY_CREATION_FACTORY),
				type);
	}
}
