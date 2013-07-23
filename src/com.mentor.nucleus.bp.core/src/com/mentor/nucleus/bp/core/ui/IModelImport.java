//========================================================================
//
//File:      $RCSfile: IModelImport.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2012/06/21 02:38:40 $
//
//(c) Copyright 2005-2012 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
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
