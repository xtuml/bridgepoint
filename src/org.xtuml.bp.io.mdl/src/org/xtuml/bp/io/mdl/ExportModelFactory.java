//========================================================================
//
//File:      $RCSfile: ExportModelFactory.java,v $
//Version:   $Revision: 1.13 $
//Modified:  $Date: 2013/05/10 13:26:12 $
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
package org.xtuml.bp.io.mdl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.operation.IRunnableWithProgress;

import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.ui.AbstractModelExportFactory;

public class ExportModelFactory extends AbstractModelExportFactory {

	public IRunnableWithProgress create(
		NonRootModelElement me,
		String fileName,
		String actionFileName,
		boolean exportGraphics) throws FileNotFoundException {
		return new ExportModelComponent((Ooaofooa)me.getModelRoot(), fileName, actionFileName, exportGraphics, me);
	}
	
	public IRunnableWithProgress create(
		Ooaofooa aModelRoot,
		String fileName,
		String actionFileName,
		boolean exportGraphics) throws FileNotFoundException {
		return new ExportModel(aModelRoot, fileName, exportGraphics);
	}
	

	public IRunnableWithProgress create(String file, String actionFile, NonRootModelElement element)
			throws FileNotFoundException {
		ExportModelComponent emc = new ExportModelComponent(file, actionFile, element);
		emc.outputCachedIDs = true;
		return emc;
	}

	public IRunnableWithProgress create(Ooaofooa modelRoot, ByteArrayOutputStream baos, ByteArrayOutputStream actionbaos, NonRootModelElement element) {
		ExportModelComponent emc = new ExportModelComponent(modelRoot, baos, actionbaos, element);
		emc.outputCachedIDs = true;
		return emc;
	}

	@Override
	public IRunnableWithProgress create(Ooaofooa aModelRoot, SystemModel_c sys,
			boolean exportGraphics) throws FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
}

