//========================================================================
//
//File:      $RCSfile: XMIExportBuilder.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 22:44:48 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//======================================================================== 
package org.xtuml.bp.mc.xmiexport;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class XMIExportBuilder extends IncrementalProjectBuilder {

	class XMIExportDeltaVisitor implements IResourceDeltaVisitor {
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
		 */
		public boolean visit(IResourceDelta delta) throws CoreException {
			//return true to continue visiting children.
			return true;
		}
	}

	class XMIExportResourceVisitor implements IResourceVisitor {
		public boolean visit(IResource resource) {
			//return true to continue visiting children.
			return true;
		}
	}

	public static final String BUILDER_ID = "org.xtuml.bp.mc.xmiexport.XMIExportBuilder";  //$NON-NLS-1$


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.internal.events.InternalBuilder#build(int,
	 *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		return null;
	}

}
