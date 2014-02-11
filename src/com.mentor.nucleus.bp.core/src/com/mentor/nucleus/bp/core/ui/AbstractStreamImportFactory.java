//========================================================================
//
//File:      $RCSfile: AbstractStreamImportFactory.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2012/06/21 02:38:40 $
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

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;

public abstract class AbstractStreamImportFactory {

	public abstract IModelImport create(InputStream inStream,
			Ooaofooa modelRoot, boolean clearDatabase, IPath projectRelativePath) throws IOException;

	public static AbstractStreamImportFactory getInstance(String bundle,
			String className) {
		try {
			Bundle io_mdl = Platform.getBundle(bundle);
			Class factoryClass = io_mdl.loadClass(bundle + "." + className);
			return (AbstractStreamImportFactory) factoryClass.newInstance();
		} catch (ClassNotFoundException cnf) {
			CorePlugin.logError("Problem creating factory " + className, cnf); //$NON-NLS-1$
			return null;
		} catch (IllegalAccessException i) {
			CorePlugin.logError("Problem creating factory " + className, i); //$NON-NLS-1$
			return null;
		} catch (InstantiationException i) {
			CorePlugin.logError("Problem creating factory " + className, i); //$NON-NLS-1$
			return null;
		}
	}

}