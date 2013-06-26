//========================================================================
//
//File:      $RCSfile: ImportModelFactory.java,v $
//Version:   $Revision: 1.13 $
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;

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
		// not supported by this package

		return new ImportModel(
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
			return new ImportModel(
				inStream,
				aModelRoot,
				system,
				parseAll,
				clearDatabase,
				parseGraphics,
				isTemplate);
		}
	
	public IModelImport create(
			IFile file,
			Ooaofooa aModelRoot,
			PersistableModelComponent component,
			boolean parseAll,
			boolean clearDatabase,
			boolean parseGraphics,
			boolean isTemplate) throws IOException {
			return new ImportModelComponent(
				file,
				aModelRoot,
				component,
				parseAll,
				clearDatabase,
				parseGraphics,
				isTemplate);
			
			
	}
}

