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
package com.mentor.nucleus.bp.debug.ui.actions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.ClassInEngine_c;
import com.mentor.nucleus.bp.core.Instance_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.PendingEvent_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;
import com.mentor.nucleus.bp.debug.ui.ModelElementLocation;
import com.mentor.nucleus.bp.debug.ui.model.BPAssocCreateDeleteBreakpoint;
import com.mentor.nucleus.bp.debug.ui.model.BPAttributeBreakpoint;
import com.mentor.nucleus.bp.debug.ui.model.BPClassCreateDeleteBreakpoint;
import com.mentor.nucleus.bp.debug.ui.model.BPEventBreakpoint;
import com.mentor.nucleus.bp.debug.ui.model.BPPendingEventBreakpoint;
import com.mentor.nucleus.bp.debug.ui.model.BPStateBreakpoint;
import com.mentor.nucleus.bp.debug.ui.model.IBPBreakpoint;


/**
 * Presents the standard properties dialog to configure
 * the attibutes of a Breakpoint from the popup menu of a breakpoint instance 
 */
public class CreateBPBreakpointAction implements IObjectActionDelegate {

	private IWorkbenchPart fPart;
	private NonRootModelElement fNrme;

	// TODO PLCM:  the method of finding the resource for the marker will probably change
	// once PLCM is implemented
	
	public void run(IAction action) {
        IBreakpoint existing_bp = determineBreakpoint();
        if ( existing_bp != null ) {
			try {
				DebugPlugin.getDefault().getBreakpointManager().removeBreakpoint(existing_bp, true);
			} catch (CoreException e) {
                BPDebugUIPlugin.logError("Unable to delete breakpoint", e);
			}
        	
        	return;
        }

		if ( fNrme instanceof Association_c ) {
			IResource resource = (IResource) ((Association_c)fNrme).getModelRoot().getPersistenceFile();
			if (resource != null) {
				BPAssocCreateDeleteBreakpoint bp;
				try {
					bp = new BPAssocCreateDeleteBreakpoint((Association_c)fNrme);
					DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(bp);
				} catch (CoreException e) {
	                BPDebugUIPlugin.logError("Unable to create Association breakpoint", e);
				}
			}
		}
		else if ( fNrme instanceof Attribute_c ) {
			IResource resource = (IResource) ((Attribute_c)fNrme).getModelRoot().getPersistenceFile();
			if (resource != null) {
				BPAttributeBreakpoint bp;
				try {
					bp = new BPAttributeBreakpoint((Attribute_c)fNrme);
					DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(bp);
				} catch (CoreException e) {
	                BPDebugUIPlugin.logError("Unable to create Attribute breakpoint", e);
				}
			}
		}
		else if ( fNrme instanceof ModelClass_c ) {
			IResource resource = (IResource) ((ModelClass_c)fNrme).getModelRoot().getPersistenceFile();
			if (resource != null) {
				BPClassCreateDeleteBreakpoint bp;
				try {
					bp = new BPClassCreateDeleteBreakpoint((ModelClass_c)fNrme);
					DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(bp);
				} catch (CoreException e) {
	                BPDebugUIPlugin.logError("Unable to create Model Class breakpoint", e);
				}
			}
		}
		else if ( fNrme instanceof StateMachineEvent_c ) {
			IResource resource = (IResource) ((StateMachineEvent_c)fNrme).getModelRoot().getPersistenceFile();
			if (resource != null) {
				BPEventBreakpoint bp;
				try {
					bp = new BPEventBreakpoint((StateMachineEvent_c)fNrme);
					bp.setCantHappen(false);
					bp.setEventIgnored(false);
					bp.setQueued(false);
					DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(bp);
				} catch (CoreException e) {
	                BPDebugUIPlugin.logError("Unable to create State Machine Event breakpoint", e);
				}
			}
		}
		else if ( fNrme instanceof StateMachineState_c ) {
			IResource resource = (IResource) ((StateMachineState_c)fNrme).getModelRoot().getPersistenceFile();
			if (resource != null) {
				BPStateBreakpoint bp;
				try {
					bp = new BPStateBreakpoint((StateMachineState_c)fNrme);
					bp.setOutof(false);
					DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(bp);
				} catch (CoreException e) {
	                BPDebugUIPlugin.logError("Unable to create State Machine State breakpoint", e);
				}
			}
		}
		else if ( fNrme instanceof Instance_c ) {
			IResource resource = (IResource) ((Instance_c)fNrme).getModelRoot().getPersistenceFile();
			if (resource != null) {
				BPClassCreateDeleteBreakpoint bp;
				ModelClass_c mc = ModelClass_c.getOneO_OBJOnR2961(ClassInEngine_c.getOneCSME_CIEOnR2962((Instance_c)fNrme));
				try {
					bp = new BPClassCreateDeleteBreakpoint(mc);
					bp.setCreate(false);
					DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(bp);
					bp.addInstanceFilter((Instance_c)fNrme);
				} catch (CoreException e) {
	                BPDebugUIPlugin.logError("Unable to create Model Class breakpoint", e);
				}
			}
		}
		else if ( fNrme instanceof PendingEvent_c ) {
			IResource resource = (IResource) ((PendingEvent_c)fNrme).getModelRoot().getPersistenceFile();
			if (resource != null) {
				BPPendingEventBreakpoint bp;
				try {
					bp = new BPPendingEventBreakpoint((PendingEvent_c)fNrme);
					DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(bp);
				} catch (CoreException e) {
	                BPDebugUIPlugin.logError("Unable to create Pending Event breakpoint", e);
				}
			}
		}
		
	}
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		fPart = targetPart;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss= (IStructuredSelection)selection;
			if (ss.isEmpty() || ss.size() > 1) {
				return;
			}
			Object element= ss.getFirstElement();
			if (element instanceof NonRootModelElement) {
				setElement((NonRootModelElement)element);
			}
	        if (determineBreakpoint() == null) {
	            action.setText("Set Breakpoint");
	        }
	        else {
	        	action.setText("Remove Breakpoint");
	        }
		}
	}

	private void setElement(NonRootModelElement e) {
		fNrme = e;
	}

	private IBreakpoint determineBreakpoint() {
		IBreakpoint[] breakpoints= DebugPlugin.getDefault().getBreakpointManager().getBreakpoints(BPDebugUIPlugin.getUniqueIdentifier());
		final String location = ModelElementLocation.getModelElementLocation(fNrme);
		for (int i= 0; i < breakpoints.length; i++) {
			IBreakpoint breakpoint= breakpoints[i];
			IMarker m = breakpoint.getMarker();
			if ( m != null ) {
				String markerLoc = m.getAttribute(IBPBreakpoint.LOCATION, "");
				String rootId = m.getAttribute(IBPBreakpoint.MODELROOT_ID, "");
				if ( rootId.equals(fNrme.getModelRoot().getId()) && markerLoc.equals(location) ) {
					// line breakpoints have their own creation action
					try {
						if ( ! breakpoint.getMarker().getType().equals(IBPBreakpoint.LINE_BREAKPOINT_ID) ) {
							return breakpoint;
						}
					} catch (CoreException e) {
						// do nothing
					}
				}
			}
		}
		return null;
	}
}
