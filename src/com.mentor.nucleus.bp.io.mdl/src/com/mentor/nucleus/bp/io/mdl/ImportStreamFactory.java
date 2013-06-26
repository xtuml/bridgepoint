//========================================================================
//
//File:      $RCSfile: ImportStreamFactory.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 23:35:07 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.io.mdl;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.IPath;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.ui.AbstractStreamImportFactory;
import com.mentor.nucleus.bp.core.ui.IModelImport;

public class ImportStreamFactory extends AbstractStreamImportFactory {

	public IModelImport create(InputStream inStream,
			Ooaofooa aModelRoot, boolean clearDatabase, IPath projectRelativePath) throws IOException {
		return new ImportModelStream(inStream, aModelRoot, clearDatabase, projectRelativePath);
	}
}
