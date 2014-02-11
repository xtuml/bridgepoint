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

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.Breakpoint_c;
import com.mentor.nucleus.bp.core.Breakpoint_type_c;
import com.mentor.nucleus.bp.core.EventBreakpoint_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;

public class BPEventBreakpoint extends BPBreakpoint {
	
	public static final int QUEUED=1;
	public static final int DEQUEUED=2;
	public static final int EVENT_IGNORED=4;
	public static final int CANT_HAPPEN=8;
	public static final int ALL=15;
	
	/**
	 * Default constructor is required for the breakpoint manager
	 * to re-create persisted breakpoints. After instantiating a breakpoint,
	 * the <code>setMarker(...)</code> method is called to restore
	 * this breakpoint's attributes.
	 */
	public BPEventBreakpoint() {
	}
	
	/**
	 * @param resource file on which to set the breakpoint
	 * @throws CoreException if unable to create the breakpoint
	 */
	public BPEventBreakpoint(StateMachineEvent_c event)
			throws CoreException {
		super(EVENT_BREAKPOINT_ID, event, ALL);
	}
	
	public boolean isQueued() {
		return getFlag(QUEUED, ALL, true);
	}

	public boolean isDequeued() {
		return getFlag(DEQUEUED, ALL, true);
	}

	public boolean isEventIgnored() {
		return getFlag(EVENT_IGNORED, ALL, true);
	}

	public boolean isCantHappen() {
		return getFlag(CANT_HAPPEN, ALL, true);
	}

	public void setQueued(boolean access) throws CoreException {
		setFlag(access, QUEUED, ALL);
	}

	public void setDequeued(boolean access) throws CoreException {
		setFlag(access, DEQUEUED, ALL);
	}

	public void setEventIgnored(boolean modification) throws CoreException {
		setFlag(modification, EVENT_IGNORED, ALL);
	}

	public void setCantHappen(boolean uninitAccess) throws CoreException {
		setFlag(uninitAccess, CANT_HAPPEN, ALL);
	}
	public String getLabel(Object o) {
		return "Event Breakpoint";
	}

	public void createTargetBreakpoint() {
		IMarker m = getMarker();
		Ooaofooa modelRoot = Ooaofooa.getInstance(m.getAttribute(MODELROOT_ID, ""), true);
		final UUID ooa_id = UUID.fromString(m.getAttribute(MODELELEMENT_ID, ""));
		Breakpoint_c x = new Breakpoint_c(modelRoot);
		try {
          x.Createbreakpoint(ooa_id, Breakpoint_type_c.Event_BP, isEnabled(),
					false, getHitCount());
          EventBreakpoint_c ebp = EventBreakpoint_c.getOneBP_EVOnR3102(x);
          Integer flags = (Integer)getMarker().getAttribute(FLAGS);
          int flagsInt = flags.intValue();
		  ebp.setOnenqueue((flagsInt & QUEUED) != 0);
		  ebp.setOndequeue((flagsInt & DEQUEUED) != 0);
		  ebp.setOneventignored((flagsInt & EVENT_IGNORED) != 0);
		  ebp.setOncanthappen((flagsInt & CANT_HAPPEN) != 0);
		} catch (CoreException e) {
          BPDebugUIPlugin.logError("An exception occurred while creating a breakpoint in the target.", e);
		}
	}

	public void deleteTargetBreakpoint() {
		IMarker m = getMarker();
		Ooaofooa modelRoot = Ooaofooa.getInstance(m.getAttribute(MODELROOT_ID, ""), true);
		final UUID ooa_id = UUID.fromString(m.getAttribute(MODELELEMENT_ID, ""));
		EventBreakpoint_c ebp = EventBreakpoint_c.EventBreakpointInstance(modelRoot, new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				EventBreakpoint_c selected = (EventBreakpoint_c)candidate;
				return selected.getSmevt_id().equals(ooa_id);
			}
		});
		if ( ebp != null ) {
			Breakpoint_c bp = Breakpoint_c.getOneBP_BPOnR3102(ebp);
			bp.Dispose();
		}
	}

	public void modifyTargetBreakpoint(IMarkerDelta delta) {
		IMarker m = getMarker();
		Ooaofooa modelRoot = Ooaofooa.getInstance(m.getAttribute(MODELROOT_ID, ""), true);
		final UUID ooa_id = UUID.fromString(m.getAttribute(MODELELEMENT_ID, ""));
		EventBreakpoint_c ebp = EventBreakpoint_c.EventBreakpointInstance(modelRoot, new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				EventBreakpoint_c selected = (EventBreakpoint_c)candidate;
				return selected.getSmevt_id().equals(ooa_id);
			}
		});
		if ( ebp != null ) {
			Map oldAttrs = delta.getAttributes();
			Object [] oldKeys = oldAttrs.keySet().toArray();
			for ( int i = 0; i < oldKeys.length; ++i ) {
				Object key = oldKeys[i];
				Object oldAttr = oldAttrs.get(key);
				try {
					Object newAttr = m.getAttribute((String)key);
					if ( key.equals(FLAGS) ) {
						int newValue = ((Integer)newAttr).intValue();
						ebp.setOnenqueue((newValue & QUEUED) != 0);
						ebp.setOndequeue((newValue & DEQUEUED) != 0);
						ebp.setOneventignored((newValue & EVENT_IGNORED) != 0);
						ebp.setOncanthappen((newValue & CANT_HAPPEN) != 0);
					}
					else {
						Breakpoint_c bp = Breakpoint_c.getOneBP_BPOnR3102(ebp);
						super.modifyTargetBreakpoint(bp, (String)key, newAttr);
					}
				} catch (CoreException e) {
					BPDebugUIPlugin.logError("An exception occurred while modifying a breakpoint in the target.", e);
				}
			}
		}		
	}

	protected String getTextDetail() {
		String bpState = "";
		if (isCantHappen() || isDequeued() || isEventIgnored() || isQueued()) {
		  bpState = "[";
		  if (isQueued()) {
            bpState = bpState + "enqueued";
		  }
		  if (isDequeued()) {
			if (!bpState.equals("[")) {
			  bpState = bpState + ", ";
			}
			bpState = bpState + "dequeued";
		  }
		  if (isEventIgnored()) {
            if (!bpState.equals("[")) {
              bpState = bpState + ", ";
            }
            bpState = bpState + "ignored";
          }
		  if (isCantHappen()) {
            if (!bpState.equals("[")) {
				bpState = bpState + ", ";
			}
            bpState = bpState + "can't happen";
		  }
		  bpState = bpState + "]";
		}
		
		return "Event " + bpState;
	}

	public void updateTargetBreakpoint() {
		IMarker m = getMarker();
		Ooaofooa modelRoot = Ooaofooa.getInstance(m.getAttribute(MODELROOT_ID, ""), true);
		final UUID ooa_id = UUID.fromString(m.getAttribute(MODELELEMENT_ID, ""));
		EventBreakpoint_c ebp = EventBreakpoint_c.EventBreakpointInstance(modelRoot, new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				EventBreakpoint_c selected = (EventBreakpoint_c)candidate;
				return selected.getSmevt_id().equals(ooa_id);
			}
		});
		if ( ebp != null ) {
			Breakpoint_c bp = Breakpoint_c.getOneBP_BPOnR3102(ebp);
			ebp.setOncanthappen(isCantHappen());
			ebp.setOndequeue(isDequeued());
			ebp.setOnenqueue(isQueued());
			ebp.setOneventignored(isEventIgnored());
		}
		else {
			System.out.println("Breakpoint not found.");
		}
	}
}
