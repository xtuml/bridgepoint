//========================================================================
//
//File:      $RCSfile: IModelImport.java,v $
//Version:   $Revision: 1.12 $
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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;

public interface IModelImport extends IRunnableWithProgress {

	public static final int PPS_OK = 0;
	public static final int PPS_ERROR = -1;
	public static final int PPS_ASK = -2;

	public IFileHeader getHeader();
	public int countAndValidateInsertStatements();
	public String  getErrorMessage();
	public boolean getSuccessful();
	
	public NonRootModelElement getRootModelElement();
	public void finishLoad(IProgressMonitor pm);
    public void finishComponentLoad(IProgressMonitor pm, boolean searchAllRootsForBatchRelate);
    public NonRootModelElement[] getLoadedInstances();
    public NonRootModelElement[] getLoadedGraphicalInstances();

	public interface IFileHeader{
		public String getProductVersion();
		public String getFileFormatVersion();
		public String getModelComponentType();
		public SystemModel_c readSystemModel(InputStream in) throws IOException;
	}
}
