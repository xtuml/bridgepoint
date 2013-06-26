//========================================================================
//
//File:      $RCSfile: ExportStreamFactory.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/01/17 03:34:42 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.io.mdl;

import java.io.ByteArrayOutputStream;

import org.eclipse.jface.operation.IRunnableWithProgress;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.AbstractStreamExportFactory;

public class ExportStreamFactory extends AbstractStreamExportFactory {

	@Override
	public IRunnableWithProgress create(ByteArrayOutputStream outStream,
			NonRootModelElement[] elements, boolean exportGraphics, 
			boolean fileExportInProgress, boolean exportUsingCachedIds) {
		ExportModelStream stream = new ExportModelStream(Ooaofooa.getInstance(elements[0].getModelRoot()
				.getId()), outStream, exportGraphics, elements, fileExportInProgress);
		stream.outputCachedIDs = exportUsingCachedIds;
		return stream;
	}

	@Override
	public IRunnableWithProgress create(ByteArrayOutputStream outStream,
			NonRootModelElement[] elements, boolean exportGraphics,
			boolean fileExportInProgress) {
		return create(outStream, elements, exportGraphics, fileExportInProgress, false);
	}
}
