//========================================================================
//
//File:      $RCSfile: ImportModelFactory.java,v $
//Version:   $Revision: 1.13 $
//Modified:  $Date: 2013/01/10 23:35:07 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
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

