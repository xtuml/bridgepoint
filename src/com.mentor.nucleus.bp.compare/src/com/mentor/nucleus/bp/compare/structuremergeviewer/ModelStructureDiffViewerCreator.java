//=====================================================================
//
//File:      $RCSfile: ModelStructureDiffViewerCreator.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 22:49:52 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================
package com.mentor.nucleus.bp.compare.structuremergeviewer;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

public class ModelStructureDiffViewerCreator implements IViewerCreator {

	public Viewer createViewer(Composite parent, CompareConfiguration config) {
		ModelStructureDiffViewer viewer = ModelStructureDiffViewer.getViewer(config);
		if(viewer != null) {
			// we only require one viewer per configuration
			return null;
		}
		//Creating the diff viewer
		return new ModelStructureDiffViewer(parent, config);
	}
}
