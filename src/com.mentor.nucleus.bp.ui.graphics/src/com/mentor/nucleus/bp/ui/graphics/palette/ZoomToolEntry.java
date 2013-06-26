//========================================================================
//
//File:      $RCSfile: ZoomToolEntry.java,v $
//Version:   $Revision: 1.5 $
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
import org.eclipse.jface.resource.ImageDescriptor;

import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.tools.GraphicalZoomTool;

public class ZoomToolEntry extends ToolEntry {

	private GraphicalEditor fEditor;

	public ZoomToolEntry(String label, String shortDesc,
			ImageDescriptor iconSmall, ImageDescriptor iconLarge, GraphicalEditor editor) {
		super(label, shortDesc, iconSmall, iconLarge);
		fEditor = editor;
	}

	@Override
	public Tool createTool() {
		return new GraphicalZoomTool(fEditor);
	}

}
