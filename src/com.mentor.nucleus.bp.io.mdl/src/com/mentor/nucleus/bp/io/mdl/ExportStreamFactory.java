//========================================================================
//
//File:      $RCSfile: ExportStreamFactory.java,v $
//Version:   $Revision: 1.12.36.1 $
//Modified:  $Date: 2013/07/26 10:13:37 $
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

import java.io.OutputStream;

import org.eclipse.jface.operation.IRunnableWithProgress;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.AbstractStreamExportFactory;

public class ExportStreamFactory extends AbstractStreamExportFactory {

	@Override
	public IRunnableWithProgress create(OutputStream outStream,
			NonRootModelElement[] elements, boolean exportGraphics, 
			boolean fileExportInProgress, boolean exportUsingCachedIds) {
		ExportModelStream stream = new ExportModelStream(Ooaofooa.getInstance(elements[0].getModelRoot()
				.getId()), outStream, exportGraphics, elements, fileExportInProgress);
		stream.outputCachedIDs = exportUsingCachedIds;
		return stream;
	}

	@Override
	public IRunnableWithProgress create(OutputStream outStream,
			NonRootModelElement[] elements, boolean exportGraphics,
			boolean fileExportInProgress) {
		return create(outStream, elements, exportGraphics, fileExportInProgress, false);
	}
}
