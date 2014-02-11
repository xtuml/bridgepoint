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
import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.Instance_c;
import com.mentor.nucleus.bp.core.ModelClass_c;

public interface IBPBreakpoint {

	public static final String BREAKPOINT_ID = "com.mentor.nucleus.bp.debug.ui.bpBreakpoint.marker"; //$NON-NLS-1$
	public static final String ASSOC_BREAKPOINT_ID = BREAKPOINT_ID + ".assoc"; //$NON-NLS-1$
	public static final String ATTRIBUTE_BREAKPOINT_ID = BREAKPOINT_ID + ".attribute"; //$NON-NLS-1$
	public static final String CLASS_BREAKPOINT_ID = BREAKPOINT_ID + ".class"; //$NON-NLS-1$
	public static final String STATE_BREAKPOINT_ID = BREAKPOINT_ID + ".state"; //$NON-NLS-1$
	public static final String EVENT_BREAKPOINT_ID = BREAKPOINT_ID + ".event"; //$NON-NLS-1$
	public static final String PENDING_EVENT_BREAKPOINT_ID = BREAKPOINT_ID + ".pendingEvent"; //$NON-NLS-1$
	public static final String LINE_BREAKPOINT_ID = "com.mentor.nucleus.bp.debug.ui.lineBreakpoint.marker"; //$NON-NLS-1$
	public static final String MODELROOT_ID = "com.mentor.nucleus.bp.debug.core.modelRootId"; //$NON-NLS-1$
	public static final String MODELELEMENT_ID = "com.mentor.nucleus.bp.debug.core.modelElementId"; //$NON-NLS-1$
	public static final String LOCATION = "com.mentor.nucleus.bp.debug.core.location"; //$NON-NLS-1$
	public static final String HIT_COUNT = "com.mentor.nucleus.bp.debug.core.hitCount"; //$NON-NLS-1$
	public static final String CONDITION = "com.mentor.nucleus.bp.debug.core.condition"; //$NON-NLS-1$
	public static final String CONDITION_ENABLED = "com.mentor.nucleus.bp.debug.core.conditionEnabled"; //$NON-NLS-1$
	public static final String BP_TYPE = "com.mentor.nucleus.bp.debug.core.bpType"; //$NON-NLS-1$
	public static final String FLAGS = "com.mentor.nucleus.bp.debug.core.flags"; //$NON-NLS-1$

	public static final String VERIFIER_BREAKPOINT_ID = "com.mentor.nucleus.bp.debug.ui.verifierException.marker"; //$NON-NLS-1$
	public static final String VERIFIER_BREAKPOINT_TYPE_ID = "com.mentor.nucleus.bp.debug.core.exceptionName"; //$NON-NLS-1$

	public void setLocation(String location) throws CoreException;
	public String getLocation() throws CoreException;

	public void setHitCount(int hitCount) throws CoreException;
	public int getHitCount() throws CoreException;

	public void setCondition(String condition) throws CoreException;
	public String getCondition() throws CoreException;

	public boolean isConditionEnabled() throws CoreException;
	public void setConditionEnabled(boolean enableCondition) throws CoreException;
	
	public IMarker getMarker();
	public void setEnabled(boolean enabled) throws CoreException;
	public String getTypeName() throws CoreException;
	
	public boolean supportsHitCount();
	public boolean supportsInstanceFilters();
	public ModelClass_c[] getAllClasses();
	public Instance_c[] getInstanceFilters();
	public void clearInstanceFilters();
	public void addInstanceFilter(Instance_c instance_c);
	
	public void createTargetBreakpoint();
	public void modifyTargetBreakpoint(IMarkerDelta delta);
	public void deleteTargetBreakpoint();
	
}