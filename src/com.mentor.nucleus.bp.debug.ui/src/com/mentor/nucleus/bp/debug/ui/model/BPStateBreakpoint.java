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
import java.util.UUID;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.Breakpoint_c;
import com.mentor.nucleus.bp.core.Breakpoint_type_c;
import com.mentor.nucleus.bp.core.EventBreakpoint_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.StateBreakpoint_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;

public class BPStateBreakpoint extends BPBreakpoint {
	
	public static final int INTO=1;
	public static final int OUTOF=2;
	public static final int ALL=3;
	
	/**
	 * Default constructor is required for the breakpoint manager
	 * to re-create persisted breakpoints. After instantiating a breakpoint,
	 * the <code>setMarker(...)</code> method is called to restore
	 * this breakpoint's attributes.
	 */
	public BPStateBreakpoint() {
	}
	
	/**
	 * @param resource file on which to set the breakpoint
	 * @throws CoreException if unable to create the breakpoint
	 */
	public BPStateBreakpoint(StateMachineState_c state)
			throws CoreException {
		super(STATE_BREAKPOINT_ID, state, ALL);
	}
	
	public boolean isInto() {
		return getFlag(INTO, ALL, true);
	}

	public boolean isOutof() {
		return getFlag(OUTOF, ALL, true);
	}

	public void setInto(boolean access) throws CoreException {
		setFlag(access, INTO, ALL);
	}

	public void setOutof(boolean access) throws CoreException {
		setFlag(access, OUTOF, ALL);
	}
	public String getLabel(Object o) {
		return "State Breakpoint";
	}

	public void createTargetBreakpoint() {
		IMarker m = getMarker();
		Ooaofooa modelRoot = Ooaofooa.getInstance(m.getAttribute(MODELROOT_ID, ""), true);
		final UUID ooa_id = UUID.fromString(m.getAttribute(MODELELEMENT_ID, ""));
		Breakpoint_c x = new Breakpoint_c(modelRoot);
		try {
          x.Createbreakpoint(ooa_id, Breakpoint_type_c.State, isEnabled(),
					false, getHitCount());
          StateBreakpoint_c sbp = StateBreakpoint_c.getOneBP_STOnR3102(x);
          Integer flags = (Integer)getMarker().getAttribute(FLAGS);
          int flagsInt = flags.intValue();
		  sbp.setOnentry((flagsInt & INTO) != 0);
		  sbp.setOnexit((flagsInt & OUTOF) != 0);
		} catch (CoreException e) {
          BPDebugUIPlugin.logError("An exception occurred while creating a breakpoint in the target.", e);
		}
	}

	public void deleteTargetBreakpoint() {
		IMarker m = getMarker();
		Ooaofooa modelRoot = Ooaofooa.getInstance(m.getAttribute(MODELROOT_ID, ""), true);
		final UUID ooa_id = UUID.fromString(m.getAttribute(MODELELEMENT_ID, ""));
		StateBreakpoint_c sbp = StateBreakpoint_c.StateBreakpointInstance(modelRoot, new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				StateBreakpoint_c selected = (StateBreakpoint_c)candidate;
				return selected.getSmstt_id().equals(ooa_id);
			}
		});
		if ( sbp != null ) {
			Breakpoint_c bp = Breakpoint_c.getOneBP_BPOnR3102(sbp);
			bp.Dispose();
		}
	}

	public void modifyTargetBreakpoint(IMarkerDelta delta) {
		IMarker m = getMarker();
		Ooaofooa modelRoot = Ooaofooa.getInstance(m.getAttribute(MODELROOT_ID, ""), true);
		final UUID ooa_id = UUID.fromString(m.getAttribute(MODELELEMENT_ID, ""));
		StateBreakpoint_c sbp = StateBreakpoint_c.StateBreakpointInstance(modelRoot, new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				StateBreakpoint_c selected = (StateBreakpoint_c)candidate;
				return selected.getSmstt_id().equals(ooa_id);
			}
		});
		if ( sbp != null ) {
			Map oldAttrs = delta.getAttributes();
			Object [] oldKeys = oldAttrs.keySet().toArray();
			for ( int i = 0; i < oldKeys.length; ++i ) {
				Object key = oldKeys[i];
				Object oldAttr = oldAttrs.get(key);
				try {
					Object newAttr = m.getAttribute((String)key);
					if ( key.equals(FLAGS) ) {
						int newValue = ((Integer)newAttr).intValue();
						sbp.setOnentry((newValue & INTO) != 0);
						sbp.setOnexit((newValue & OUTOF) != 0);
					}
					else {
						Breakpoint_c bp = Breakpoint_c.getOneBP_BPOnR3102(sbp);
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
		if (isInto() || isOutof()) {
		  bpState = "[";
		  if (isInto()) {
		    bpState = bpState + "entry";
		  }
		  if (isOutof()) {
			if (isInto()) {
				bpState = bpState + ", ";
			}
			bpState = bpState + "exit";
		  }
		  bpState = bpState + "]";
		}
		
		return "State " + bpState;
	}

	public void updateTargetBreakpoint() {
		IMarker m = getMarker();
		Ooaofooa modelRoot = Ooaofooa.getInstance(m.getAttribute(MODELROOT_ID, ""), true);
		final UUID ooa_id = UUID.fromString(m.getAttribute(MODELELEMENT_ID, ""));
		StateBreakpoint_c sbp = StateBreakpoint_c.StateBreakpointInstance(modelRoot, new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				StateBreakpoint_c selected = (StateBreakpoint_c)candidate;
				return selected.getSmstt_id().equals(ooa_id);
			}
		});
		if ( sbp != null ) {
			Breakpoint_c bp = Breakpoint_c.getOneBP_BPOnR3102(sbp);
			sbp.setOnentry(isInto());
			sbp.setOnexit(isOutof());
		}
		else {
			System.out.println("Breakpoint not found.");
		}
	}
}
