//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.debug.ui.model;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.Breakpoint;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;
import com.mentor.nucleus.bp.debug.ui.IBPDebugUIPluginConstants;


public class VerifierExceptionBreakpoint extends Breakpoint implements IWorkbenchAdapter {
	static public int CANT_HAPPEN = 0; 
	static public int EMPTY_INST_ACCESS_RELATE = 1; 
	static public int EMPTY_INST_ACCESS_UNRELATE = 2; 
	static public int EMPTY_INST_ACCESS_GENERATE = 3; 
	static public int NUM_BREAKPOINTS = 4; 

	static private String [] labels = {
		"Break on \"can't happen\"",
		"Break on uninitialized instance relate",
		"Break on uninitialized instance unrelate",
		"Break on uninitialized instance generate" };
		
	static private VerifierExceptionBreakpoint fDummyInstance = new VerifierExceptionBreakpoint();

	/**
	 * Default constructor is required for the breakpoint manager
	 * to re-create persisted breakpoints. After instantiating a breakpoint,
	 * the <code>setMarker(...)</code> method is called to restore
	 * this breakpoint's attributes.
	 */
	public VerifierExceptionBreakpoint() {
	}

	public VerifierExceptionBreakpoint(final int type) throws DebugException {
		final IResource resource = getResource();
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				IMarker marker = resource.createMarker(IBPBreakpoint.VERIFIER_BREAKPOINT_ID);
				setMarker(marker);
				marker.setAttribute(IBreakpoint.ENABLED, true);
				marker.setAttribute(IBreakpoint.ID, getModelIdentifier());
				marker.setAttribute(IBPBreakpoint.VERIFIER_BREAKPOINT_TYPE_ID, getTypeLabel(type));
			}

		};
		run(getMarkerRule(resource), runnable);
	}

	public String getModelIdentifier() {
		return IBPDebugUIPluginConstants.PLUGIN_ID;
	}

	static public int getBPTypeId(String name) throws DebugException {
		for (int i = 0; i < NUM_BREAKPOINTS; ++i ) {
			if ( name.equals(labels[i]) ) {
				return i;
			}
		}
		return -1;
	}

	static public String getTypeLabel(int i) {
		return labels[i]; 
	}

	static public IResource getResource() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}
	
	// return the breakpoints that currently do not exist
	static public int [] getAvailableTypes() {
		IResource r = getResource();
		boolean [] avail = new boolean[NUM_BREAKPOINTS];
		for (int i = 0; i < NUM_BREAKPOINTS; ++i ) {
			avail[i] = true;
		}
		int numAvailable = NUM_BREAKPOINTS;
		try {
			IMarker [] m_set = r.findMarkers(IBPBreakpoint.VERIFIER_BREAKPOINT_ID, false, IResource.DEPTH_ZERO);
			for ( int i = 0; i < m_set.length; ++i ) {
				String name = m_set[i].getAttribute(IBPBreakpoint.VERIFIER_BREAKPOINT_TYPE_ID, "");
				avail[getBPTypeId(name)] = false;
				numAvailable -= 1;
			}
		} catch (CoreException e) {
			// do nothing
		}
		int[] result = new int[numAvailable];
		int next = 0;
		for (int i = 0; i < NUM_BREAKPOINTS; ++i ) {
			if ( avail[i] ) {
				result[next++] = i;
			}
		}
		return result;
	}
	
	public Object[] getChildren(Object o) {
		return null;
	}

	public ImageDescriptor getImageDescriptor(Object object) {
		return null;
	}

	public String getLabel(Object o) {
		if ( o instanceof VerifierExceptionBreakpoint ) {
			IMarker m = getMarker();
			if ( m != null ) {
				return m.getAttribute(IBPBreakpoint.VERIFIER_BREAKPOINT_TYPE_ID, "");
			}
		}
		return "Verifier Exception Breakpoint";
	}

	public Object getParent(Object o) {
		return null;
	}

	// for the dummy instance
	public String toString() {
		if ( getMarker() == null ) {
			return this.getClass().getName();
		}
		return super.toString();
	}

	// for the dummy instance
	public boolean equals(Object item) {
		if ( item instanceof IBreakpoint ) {
			if ( getMarker() == null || ((IBreakpoint)item).getMarker() == null ) {
				return this == item;
			}
		}
		boolean result = super.equals(item);
		return result;
	}

	public static VerifierExceptionBreakpoint getDummyInstance() {
		return fDummyInstance;
	}

	public void createTargetBreakpoint() {
		BPDebugUIPlugin.logError("VerifierExceptionBreakpoint.createTargetBreakpoint() not implemented", null);
	}

	public void deleteTargetBreakpoint() {
		BPDebugUIPlugin.logError("VerifierExceptionBreakpoint.deleteTargetBreakpoint() not implemented", null);
	}

	public void modifyTargetBreakpoint(IMarkerDelta delta) {
		BPDebugUIPlugin.logError("VerifierExceptionBreakpoint.modifyTargetBreakpoint() not implemented", null);
	}

	
}
