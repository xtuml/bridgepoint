//=====================================================================
//
//File:      $RCSfile: ModelContentMergeViewerCreator.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/10 22:49:59 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================
package com.mentor.nucleus.bp.model.compare.contentmergeviewer;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

public class ModelContentMergeViewerCreator implements IViewerCreator {

	public Viewer createViewer(Composite parent, CompareConfiguration config) {
		return new ModelContentMergeViewer(parent, config);
	}	

}
