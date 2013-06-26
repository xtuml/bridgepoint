//========================================================================
//
//File:      $RCSfile: GraphicsCreationToolEntry.java,v $
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
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.jface.resource.ImageDescriptor;

import com.mentor.nucleus.bp.ui.graphics.tools.GraphicsCreationTool;

public class GraphicsCreationToolEntry extends ToolEntry {

	private int type;

	public GraphicsCreationToolEntry(String label, String shortDesc,
			CreationFactory factory, ImageDescriptor iconSmall,
			ImageDescriptor iconLarge, int ooaType) {
		super(label, shortDesc, iconSmall, iconLarge,
				GraphicsCreationTool.class);
		setToolProperty(CreationTool.PROPERTY_CREATION_FACTORY, factory);
		type = ooaType;
	}

	@Override
	public Tool createTool() {
		return new GraphicsCreationTool(
				(CreationFactory) getToolProperty(CreationTool.PROPERTY_CREATION_FACTORY),
				type);
	}

}
