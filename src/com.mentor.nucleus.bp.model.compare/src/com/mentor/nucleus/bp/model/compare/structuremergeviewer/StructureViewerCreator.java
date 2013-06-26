package com.mentor.nucleus.bp.model.compare.structuremergeviewer;
//=====================================================================
//
//File:      $RCSfile: StructureViewerCreator.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:36:02 $
//
//(c) Copyright 2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

public class StructureViewerCreator implements IViewerCreator {

	public StructureViewerCreator() {
	}

	@Override
	public Viewer createViewer(Composite parent, CompareConfiguration config) {
		return new ModelStructureDiffViewer(parent, config);
	}

}
