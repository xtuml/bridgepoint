//========================================================================
//
//File:      $RCSfile: AbstractModelExportFactory.java,v $
//Version:   $Revision: 1.12.16.2 $
//Modified:  $Date: 2013/07/23 15:06:42 $
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
package com.mentor.nucleus.bp.core.ui;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.osgi.framework.Bundle;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;

public abstract class AbstractModelExportFactory {
	static private AbstractModelExportFactory exportModelFactory = null;

	public abstract IRunnableWithProgress create(
		Ooaofooa modelRoot,
		String outfileName,
		boolean export_graphics) throws FileNotFoundException;

	public abstract IRunnableWithProgress create(
			Ooaofooa aModelRoot,
			SystemModel_c sys,
			boolean exportGraphics) throws FileNotFoundException;
	
	public abstract IRunnableWithProgress create(NonRootModelElement element, String outfileName,
			boolean exportGraphics) throws FileNotFoundException;
	
	public abstract IRunnableWithProgress create(String outfileName,
			NonRootModelElement me) throws FileNotFoundException;

	public abstract IRunnableWithProgress create(Ooaofooa modelRoot, ByteArrayOutputStream baos,
			NonRootModelElement me) throws FileNotFoundException;

static public AbstractModelExportFactory getInstance()
   {
   	if ( exportModelFactory == null )
   	{
		try {
			Bundle io_mdl = Platform.getBundle("com.mentor.nucleus.bp.io.mdl");//$NON-NLS-1$
			Class factoryClass = io_mdl
				.loadClass("com.mentor.nucleus.bp.io.mdl.ExportModelFactory"); //$NON-NLS-1$
			exportModelFactory = (AbstractModelExportFactory) factoryClass
				.newInstance();
		} catch (ClassNotFoundException cnf) {
			CorePlugin.logError("Problem loading domains ", cnf); //$NON-NLS-1$
		} catch (IllegalAccessException i) {
			CorePlugin.logError("Problem loading domains ", i); //$NON-NLS-1$
		} catch (InstantiationException i) {
			CorePlugin.logError("Problem loading domains ", i); //$NON-NLS-1$
		}
   	}
	return exportModelFactory;
   }   
}