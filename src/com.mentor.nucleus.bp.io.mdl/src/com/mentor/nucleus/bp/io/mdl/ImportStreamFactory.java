//========================================================================
//
//File:      $RCSfile: ImportStreamFactory.java,v $
//Version:   $Revision: 1.11 $
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
