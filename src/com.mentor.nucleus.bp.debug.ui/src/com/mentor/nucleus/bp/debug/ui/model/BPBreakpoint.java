//========================================================================
//
//File:      $RCSfile: BPBreakpoint.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/05/12 00:16:37 $
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.Breakpoint;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.mentor.nucleus.bp.core.Breakpoint_c;
import com.mentor.nucleus.bp.core.Condition_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.Instance_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.ProvidedSignal_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.RequiredSignal_c;
import com.mentor.nucleus.bp.core.StateBreakpoint_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.debug.ui.IBPDebugUIPluginConstants;
import com.mentor.nucleus.bp.debug.ui.ModelElementLocation;

public abstract class BPBreakpoint extends Breakpoint implements IBPBreakpoint, IWorkbenchAdapter {

	/**
	 * List of active instance filters for this breakpoint
	 * (list of <code>Instance_c</code>).
	 */
	protected List fInstanceFilters = null;
	
	/**
	 * Empty instance filters array.
	 */
	protected static final Instance_c[] fgEmptyInstanceFilters = new Instance_c[0];
	
	/**
	 * Default constructor is required for the breakpoint manager
	 * to re-create persisted breakpoints. After instantiating a breakpoint,
	 * the <code>setMarker(...)</code> method is called to restore
	 * this breakpoint's attributes.
	 */
	public BPBreakpoint() {
	}
	
	/**
	 * @param resource file on which to set the breakpoint
	 * @throws CoreException if unable to create the breakpoint
	 */
	public BPBreakpoint(final String markerType, NonRootModelElement nrme, final int flags_all)
			throws CoreException {
		IResource resource = (IResource)nrme.getModelRoot().getPersistenceFile();
		init(resource, markerType, nrme, flags_all, "", 0); //$NON-NLS-1$
		setHitCount(0);
	}

	protected void init(final IResource resource, final String markerType, 
			NonRootModelElement nrme, final int flags_all,
			final String optionalAttribute, final int optionalAttrValue) throws DebugException {
		final String mr_id = nrme.getModelRoot().getId();
		final String location = ModelElementLocation.getModelElementLocation(nrme);
		String ooa_id = String.valueOf(nrme.Get_ooa_id());
		if(ooa_id.equals(Gd_c.Null_unique_id().toString())) {
			if(nrme instanceof RequiredOperation_c) {
				ooa_id = ((RequiredOperation_c) nrme).getId().toString();
			} else if (nrme instanceof RequiredSignal_c) {
				ooa_id = ((RequiredSignal_c) nrme).getId().toString();
			} else if (nrme instanceof ProvidedOperation_c) {
				ooa_id = ((ProvidedOperation_c) nrme).getId().toString();
			} else if (nrme instanceof ProvidedSignal_c) {
				ooa_id = ((ProvidedSignal_c) nrme).getId().toString();
			}
		}
		final String final_id = ooa_id;
		//final ModelElementID modelElementID = ModelAdapter.getModelElementAdapter(nrme).createModelElementID(nrme);
			IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					IMarker marker = resource.createMarker(markerType);
					setMarker(marker);
					marker.setAttribute(IBreakpoint.ENABLED, true);
					marker.setAttribute(IBreakpoint.ID, getModelIdentifier()+"/" + location);
					marker.setAttribute(MODELROOT_ID, mr_id);
					marker.setAttribute(MODELELEMENT_ID, final_id);
					marker.setAttribute(LOCATION, location);
					marker.setAttribute(CONDITION, "");
					marker.setAttribute(CONDITION_ENABLED, false);
					marker.setAttribute(FLAGS, flags_all);
					if ( !optionalAttribute.equals("") ) { //$NON-NLS-1$
						marker.setAttribute(optionalAttribute, optionalAttrValue);
					}
					setHitCount(0);
				}
			};
			run(getMarkerRule(resource), runnable);
	}

	public String getModelIdentifier() {
		return IBPDebugUIPluginConstants.PLUGIN_ID;
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.debug.ui.model.IBPBreakpoint#getTypeName()
	 */
	public String getTypeName() throws CoreException {
		return "Class:";
	}

	public void setLocation(String location) throws CoreException {
		if (!location.equals(getCondition())) {
			setAttribute(LOCATION, location);
		}
	}

	public String getLocation() throws CoreException {
		return ensureMarker().getAttribute(LOCATION, "");
	}
	
	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.debug.ui.model.IBPBreakpoint#setHitCount(int)
	 */
	public void setHitCount(int hitCount) throws CoreException {
		if (hitCount != getHitCount()) {
			setAttribute(HIT_COUNT, hitCount);
			if ( hitCount > 0 ) {
				String message = getLocation() + 
				   " [hit count: " + hitCount + "]";
				ensureMarker().setAttribute(IMarker.MESSAGE, message);
			}
			else {
				String message = getLocation(); 
				ensureMarker().setAttribute(IMarker.MESSAGE, message);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.debug.ui.model.IBPBreakpoint#getHitCount()
	 */
	public int getHitCount() throws CoreException {
		return ensureMarker().getAttribute(HIT_COUNT, -1);
	}

	public void setCondition(String condition) throws CoreException {
		if (!condition.equals(getCondition())) {
			setAttribute(CONDITION, condition);
		}
	}

	public String getCondition() throws CoreException {
		return ensureMarker().getAttribute(CONDITION, "");
	}

	public boolean isConditionEnabled() throws CoreException {
		return ensureMarker().getAttribute(CONDITION_ENABLED, false);
	}

	public void setConditionEnabled(boolean enableCondition) throws CoreException {
		if ( isConditionEnabled() != enableCondition ) {
			setAttribute(CONDITION_ENABLED, enableCondition);
		}
	}

	public boolean supportsCondition() {
		return false;
	}

	public boolean supportsHitCount() {
		return true;
	}

	public boolean getFlag(int flag, int all_flags, boolean defaultValue) {
		IMarker m = getMarker();
		if (m != null) {
			int flags = m.getAttribute(FLAGS, all_flags);
			return (flags & flag) != 0;
		}
		return defaultValue;		
	}
	
	public void setFlag(boolean condition, int flag, int all_flags) throws CoreException {
		IMarker m = getMarker();
		if (m != null) {
			int flags = m.getAttribute(FLAGS, all_flags);
			if ( condition ) {
				flags = flags | flag;
			}
			else {
				flags = flags & ~flag;
			}
			m.setAttribute(FLAGS, flags);
		}
	}

	public String getText() {
		IMarker m = getMarker();
		if (m != null) {
			return getTextDetail() + " " + m.getAttribute(IMarker.MESSAGE, "");
		}
		return "";
	}

	public boolean supportsInstanceFilters() {
		return false;
	}

	public Instance_c[] getInstanceFilters() {
		if (fInstanceFilters == null || fInstanceFilters.isEmpty()) {
			return fgEmptyInstanceFilters;
		}
		return (Instance_c[])fInstanceFilters.toArray(new Instance_c[fInstanceFilters.size()]);
	}

	public void clearInstanceFilters() {
		if (fInstanceFilters != null) {
			fInstanceFilters.clear();
			fInstanceFilters = null;
			fireChanged();
		}
	}

	public void addInstanceFilter(Instance_c object) {
		if (fInstanceFilters == null) {
			fInstanceFilters = new ArrayList();
		}
		fInstanceFilters.add(object);
		fireChanged();
	}

	/**
	 * Change notification when there are no marker changes. If the marker
	 * does not exist, do not fire a change notification (the marker may not
	 * exist if the associated project was closed).
	 */
	protected void fireChanged() {
		if (markerExists()) {	
			DebugPlugin.getDefault().getBreakpointManager().fireBreakpointChanged(this);
		}					
	}

	
	public ModelClass_c[] getAllClasses() {
		IMarker m = getMarker();
		if (m != null) {
			String mr_id = m.getAttribute(MODELROOT_ID, "");  //$NON_NLS-1$
			Ooaofooa x = Ooaofooa.getInstance(mr_id);
			ModelClass_c [] ret_val = ModelClass_c.ModelClassInstances(x);
			return ret_val;
		}
		return new ModelClass_c[0];
	}

	public Object[] getChildren(Object o) {
		return null;
	}

	public ImageDescriptor getImageDescriptor(Object object) {
		return null;
	}

	public String getLabel(Object o) {
		// more specific labels will be supplied by overrides in subtypes
		return "Breakpoint";
	}

	public Object getParent(Object o) {
		return null;
	}

	protected boolean managerEnabled() {
	  return DebugPlugin.getDefault().getBreakpointManager().isEnabled();
	}
	public void modifyTargetBreakpoint(Breakpoint_c bp, String string, Object newAttr) {
		if ( string.equals(ENABLED) ) {
			boolean newValue = ((Boolean)newAttr).booleanValue();
			if (!managerEnabled()) {
				newValue = false;
			}
			bp.setEnabled(newValue);
		}
		else if ( string.equals(HIT_COUNT) ) {
			int newValue = ((Integer)newAttr).intValue();
			bp.setTarget_hit_count(newValue);
		}
		else if ( string.equals(CONDITION_ENABLED) ) {
			boolean newValue = ((Boolean)newAttr).booleanValue();
			bp.setCondition_enabled(newValue);
		}
		else if ( string.equals(CONDITION) ) {
			String newValue = (String)newAttr;
			Condition_c cond = Condition_c.getOneBP_CONOnR3100(bp);
			if ( cond == null ) {
				cond = new Condition_c(bp.getModelRoot());
				cond.relateAcrossR3100To(bp);
			}
			cond.setExpression(newValue);
		}
	}

	protected String getTextDetail() {
		// Subtypes should override this to provide more
		// detail than just the data stored in the Marker
		return "";
	}
	
	/**
	 * Deletes this breakpoint's underlying marker, and removes
	 * this breakpoint from the breakpoint manager.
	 *
	 * @override
	 * @exception CoreException if unable to delete this breakpoint's
	 *  underlying marker
	 */
	public void delete() throws CoreException {
		deleteTargetBreakpoint();
		super.delete();
	}
}
