//========================================================================
//
//File:      $RCSfile: ImportModelFactory.java,v $
//Version:   $Revision: 1.13 $
//Modified:  $Date: 2013/05/10 16:17:58 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.io.sql;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.ui.AbstractModelImportFactory;
import com.mentor.nucleus.bp.core.ui.IModelImport;

public class ImportModelFactory extends AbstractModelImportFactory {

	public IModelImport create(
		String fileName,
		Ooaofooa aModelRoot,
		SystemModel_c system,
		boolean parseAll,
		boolean clearDatabase,
		boolean isTemplate) throws FileNotFoundException {
		return new ImportBPSql(
			fileName,
			aModelRoot,
			system,
			parseAll,
			clearDatabase,
			isTemplate);
	}

	public IModelImport create(
			InputStream inStream,
			Ooaofooa aModelRoot,
			SystemModel_c system,
			boolean parseAll,
			boolean clearDatabase,
			boolean parseGraphics,
			boolean isTemplate) throws IOException {
		// not supported by this package
		return null;
	}
	
	public IModelImport create(
			IFile file,
			Ooaofooa aModelRoot,
			PersistableModelComponent component,
			boolean parseAll,
			boolean clearDatabase,
			boolean parseGraphics,
			boolean isTemplate) throws IOException{
	    //not support by this package
	    return null;
	}
	
    /* (non-Javadoc)
     * @see com.mentor.nucleus.bp.core.ui.AbstractModelImportFactory#create(org.eclipse.core.runtime.IPath)
     */
    public IModelImport create(IPath filePath) throws IOException {
        //not support by this package
        return null;
    }

}

