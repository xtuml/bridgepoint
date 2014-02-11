//========================================================================
//
//File:      $RCSfile: BPLineBreakpoint.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/01/10 23:18:02 $
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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.ILineBreakpoint;

import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.Body_c;
import com.mentor.nucleus.bp.core.Breakpoint_c;
import com.mentor.nucleus.bp.core.Breakpoint_type_c;
import com.mentor.nucleus.bp.core.BridgeBody_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.CreationTransition_c;
import com.mentor.nucleus.bp.core.DerivedAttributeBody_c;
import com.mentor.nucleus.bp.core.DerivedBaseAttribute_c;
import com.mentor.nucleus.bp.core.FunctionBody_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.MooreActionHome_c;
import com.mentor.nucleus.bp.core.OalBreakpoint_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.OperationBody_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.ProvidedOperationBody_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.ProvidedSignalBody_c;
import com.mentor.nucleus.bp.core.ProvidedSignal_c;
import com.mentor.nucleus.bp.core.RequiredOperationBody_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.RequiredSignalBody_c;
import com.mentor.nucleus.bp.core.RequiredSignal_c;
import com.mentor.nucleus.bp.core.StateActionBody_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.Statement_c;
import com.mentor.nucleus.bp.core.TransitionActionBody_c;
import com.mentor.nucleus.bp.core.TransitionActionHome_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;
import com.mentor.nucleus.bp.ui.text.placeholder.PlaceHolderEntry;

/**
 * OAL line breakpoint
 */
public class BPLineBreakpoint extends BPBreakpoint implements ILineBreakpoint {

	public static final int FUNCTION = 1;
	public static final int BRIDGE = 2;
	public static final int STATE = 3;
	public static final int MDA = 4;
	public static final int OPERATION = 5;
	public static final int REQ_OPERATION = 6;
	public static final int PROV_OPERATION = 7;
	public static final int REQ_SIGNAL = 8;
	public static final int PROV_SIGNAL = 9;
	public static final int TRANSITION = 10;
	public static final int CREATION_TRANSITION=11;
	
	/**
	 * Default constructor is required for the breakpoint manager
	 * to re-create persisted breakpoints. After instantiating a breakpoint,
	 * the <code>setMarker(...)</code> method is called to restore
	 * this breakpoint's attributes.
	 */
	public BPLineBreakpoint() {
	}
	
	/**
	 * Constructs a line breakpoint on the given resource at the given
	 * line number. The line number is 1-based (i.e. the first line of a
	 * file is line number 1). 
	 * 
	 * @param resource file on which to set the breakpoint
	 * @param lineNumber valid 1-based line number of the breakpoint
	 * @throws CoreException if unable to create the breakpoint
	 */
	public BPLineBreakpoint(final IResource resource, final int lineNumber)
			throws CoreException {
		if ( resource instanceof PlaceHolderEntry.PlaceHolderFileProxy ) {
			PlaceHolderEntry.PlaceHolderFileProxy proxy = (PlaceHolderEntry.PlaceHolderFileProxy)resource;
			NonRootModelElement nrme = proxy.getModelElement();
			super.init(resource, LINE_BREAKPOINT_ID, nrme, getActivityType(nrme), 
					IMarker.LINE_NUMBER, lineNumber);
		} else {
			Exception e = new Exception("Invalid resource for OAL Line Breakpoint");
			e.fillInStackTrace();
			throw new CoreException(new Status(IStatus.ERROR,
	                DebugPlugin.getUniqueIdentifier(),
	                DebugException.REQUEST_FAILED, 
	                "Invalid resource for OAL Line Breakpoint", e));
		}
	}
	
	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.debug.ui.model.IBPBreakpoint#getTypeName()
	 */
	public String getTypeName() throws CoreException {
		return "Type:";
	}

	public boolean supportsInstanceFilters() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.debug.ui.model.IBPBreakpoint#setHitCount(int)
	 */
	public void setHitCount(int hitCount) throws CoreException {
		if (hitCount != getHitCount()) {
			setAttribute(HIT_COUNT, hitCount);
			if ( hitCount > 0 ) {
				String message = getLocation() + 
				   " [line: " + getLineNumber() + "]" +
				   " [hit count: " + hitCount + "]";
				getMarker().setAttribute(IMarker.MESSAGE, message);
			}
			else {
				String message = getLocation() + 
				   " [line: " + getLineNumber() + "]";
				getMarker().setAttribute(IMarker.MESSAGE, message);
			}
		}
	}

	public int getLineNumber() throws CoreException {
		IMarker m = getMarker();
		if (m != null) {
			return m.getAttribute(IMarker.LINE_NUMBER, -1);
		}
		return -1;
	}

	public int getCharStart() throws CoreException {
		IMarker m = getMarker();
		if (m != null) {
			return m.getAttribute(IMarker.CHAR_START, -1);
		}
		return -1;
	}

	public int getCharEnd() throws CoreException {
		IMarker m = getMarker();
		if (m != null) {
			return m.getAttribute(IMarker.CHAR_END, -1);
		}
		return -1;
	}
	public String getLabel(Object o) {
		return "OAL Breakpoint";
	}

	public void createTargetBreakpoint() {
		IMarker m = getMarker();
		Ooaofooa modelRoot = Ooaofooa.getInstance(m.getAttribute(MODELROOT_ID, ""), true);
		final UUID ooa_id = UUID.fromString(m.getAttribute(MODELELEMENT_ID, ""));
		try {
			int activityType = ((Integer)m.getAttribute(FLAGS)).intValue(); 
			Statement_c stmt = findStatementOnLine(modelRoot, ooa_id, activityType, getLineNumber());
			if (stmt != null ) {
				Breakpoint_c x = new Breakpoint_c(modelRoot);
				x.Createbreakpoint(stmt.getStatement_id(), Breakpoint_type_c.OAL, isEnabled(),
						false, getHitCount());
			}
		} catch (CoreException e) {
			BPDebugUIPlugin.logError("An exception occurred while creating a breakpoint in the target.", e);
		}
		
	}

	public void deleteTargetBreakpoint() {
		IMarker m = getMarker();
		Ooaofooa modelRoot = Ooaofooa.getInstance(m.getAttribute(MODELROOT_ID, ""), true);
		final UUID ooa_id = UUID.fromString(m.getAttribute(MODELELEMENT_ID, ""));
		try {
			int activityType = ((Integer)m.getAttribute(FLAGS)).intValue(); 
			OalBreakpoint_c lbp = findBPForStatementOnLine(modelRoot, ooa_id, activityType, getLineNumber());
			if ( lbp != null ) {
				Breakpoint_c bp = Breakpoint_c.getOneBP_BPOnR3102(lbp);
				bp.Dispose();
			}		
		} catch (CoreException e) {
			BPDebugUIPlugin.logError("An exception occurred while deleting a breakpoint in the target.", e);
		}
	}

	public void modifyTargetBreakpoint(IMarkerDelta delta) {
		IMarker m = getMarker();
		Ooaofooa modelRoot = Ooaofooa.getInstance(m.getAttribute(MODELROOT_ID, ""), true);
		final UUID ooa_id = UUID.fromString(m.getAttribute(MODELELEMENT_ID, ""));
		try {
			int activityType = ((Integer)m.getAttribute(FLAGS)).intValue(); 
			OalBreakpoint_c lbp = findBPForStatementOnLine(modelRoot, ooa_id, activityType, getLineNumber());
			if ( lbp != null ) {
				Map oldAttrs = delta.getAttributes();
				Object [] oldKeys = oldAttrs.keySet().toArray();
				for ( int i = 0; i < oldKeys.length; ++i ) {
					Object key = oldKeys[i];
					Object oldAttr = oldAttrs.get(key);
						Object newAttr = m.getAttribute((String)key);
						if (!oldAttr.equals(newAttr)) {
							Breakpoint_c bp = Breakpoint_c.getOneBP_BPOnR3102(lbp);
							super.modifyTargetBreakpoint(bp, (String)key, newAttr);
						}
				}
			}
			else {
				createTargetBreakpoint();
			}
		} catch (CoreException e) {
			BPDebugUIPlugin.logError("An exception occurred while modifying a breakpoint in the target.", e);
		}
	}

	private static int getActivityType(NonRootModelElement nrme) {
		if ( nrme instanceof Function_c ) {
			return FUNCTION;
		}
		if ( nrme instanceof Bridge_c ) {
			return BRIDGE;
		}
		if ( nrme instanceof StateMachineState_c ) {
			return STATE;
		}
		if ( nrme instanceof Operation_c ) {
			return OPERATION;
		}
		if ( nrme instanceof Attribute_c ) {
			return MDA;
		}
		if(nrme instanceof RequiredOperation_c) {
			return REQ_OPERATION;
		}
		if(nrme instanceof ProvidedOperation_c) {
			return PROV_OPERATION;
		}
		if(nrme instanceof RequiredSignal_c) {
			return REQ_SIGNAL;
		}
		if(nrme instanceof ProvidedSignal_c) {
			return PROV_SIGNAL;
		}
		if(nrme instanceof Transition_c) {
			return TRANSITION;
		}
		if(nrme instanceof CreationTransition_c){
			return CREATION_TRANSITION;
		}
		return -1;
	}

	public static int getValidLine(final IResource resource, final int lineNumber) {
		if ( resource instanceof PlaceHolderEntry.PlaceHolderFileProxy ) {
			PlaceHolderEntry.PlaceHolderFileProxy proxy = (PlaceHolderEntry.PlaceHolderFileProxy)resource;
			NonRootModelElement nrme = proxy.getModelElement();
			UUID ooa_id = nrme.Get_ooa_id();
			int activityType = getActivityType(nrme);
			int ret_val = lineNumber;
			Statement_c stmt = findStatementOnLine((Ooaofooa)nrme.getModelRoot(), ooa_id, activityType, ret_val);
			int numLines = getNumLines(nrme.getModelRoot(), ooa_id, activityType);
			while ( stmt == null && ret_val <= numLines ) {
				ret_val += 1;
				stmt = findStatementOnLine((Ooaofooa)nrme.getModelRoot(), ooa_id, activityType, ret_val);
			}
			if ( stmt != null ) {
				return ret_val;
			}
		}
		return lineNumber;
	}
	
	private static int getNumLines(ModelRoot ooaofooa, final UUID activity_id, int activityType) {
		String activity = "";
		if ( activityType == FUNCTION) {
			Function_c f = Function_c.FunctionInstance(ooaofooa, new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate) {
					Function_c selected = (Function_c)candidate;
					return selected.getSync_id().equals(activity_id);
				}
			});
			if (f != null) {
			  activity = f.getAction_semantics();
			}
		}
		else if ( activityType == BRIDGE ) {
			Bridge_c brg = Bridge_c.BridgeInstance(ooaofooa, new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate) {
					Bridge_c selected = (Bridge_c)candidate;
					return selected.getBrg_id().equals(activity_id);
				}
			});
			if (brg != null) {
			  activity = brg.getAction_semantics();
			}
		}
		else if ( activityType == STATE ) {
			StateMachineState_c state = StateMachineState_c.StateMachineStateInstance(ooaofooa, new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate) {
					StateMachineState_c selected = (StateMachineState_c)candidate;
					return selected.getSmstt_id().equals(activity_id);
				}
			});
			Action_c act = Action_c.getOneSM_ACTOnR514(ActionHome_c.getOneSM_AHOnR513(
					MooreActionHome_c.getOneSM_MOAHOnR511(state)));
			if (act != null) {
			  activity = act.getAction_semantics();
			}
		}
		else if ( activityType == TRANSITION ) {
			Transition_c trans = Transition_c.TransitionInstance(ooaofooa, new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate) {
					Transition_c selected = (Transition_c)candidate;
					return selected.getTrans_id().equals(activity_id);
				}
			});
			Action_c act = Action_c.getOneSM_ACTOnR514(ActionHome_c.getOneSM_AHOnR513(
					TransitionActionHome_c.getOneSM_TAHOnR530(trans)));
			if (act != null) {
			  activity = act.getAction_semantics();
			}
		}
		else if(activityType == CREATION_TRANSITION){
			CreationTransition_c ctrans=CreationTransition_c.CreationTransitionInstance(ooaofooa, new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate){
					CreationTransition_c selected=((CreationTransition_c) candidate);
					return selected.getTrans_id().equals(activity_id);
				}
			});
			Transition_c trans=Transition_c.getOneSM_TXNOnR507(ctrans);
			Action_c act = Action_c.getOneSM_ACTOnR514(ActionHome_c.getOneSM_AHOnR513(
					TransitionActionHome_c.getOneSM_TAHOnR530(trans)));
			if (act != null) {
			  activity = act.getAction_semantics();
			}
			
		}
		else if ( activityType == OPERATION ) {
			Operation_c op = Operation_c.OperationInstance(ooaofooa, new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate) {
					Operation_c selected = (Operation_c)candidate;
					return selected.getTfr_id().equals(activity_id);
				}
			});
			if (op != null) {
			  activity = op.getAction_semantics();
			}
		}
		else if ( activityType == MDA ) {
			DerivedBaseAttribute_c dba = DerivedBaseAttribute_c.DerivedBaseAttributeInstance(ooaofooa, new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate) {
					DerivedBaseAttribute_c selected = (DerivedBaseAttribute_c)candidate;
					return selected.getAttr_id().equals(activity_id);
				}
			});
			if (dba != null) {
			  activity = dba.getAction_semantics();
			}
		}
		else if(activityType == REQ_OPERATION) {
			RequiredOperation_c reqOp = RequiredOperation_c
					.RequiredOperationInstance(ooaofooa,
							new ClassQueryInterface_c() {
								public boolean evaluate(Object candidate) {
									return ((RequiredOperation_c) candidate)
											.getId().equals(activity_id);
								}
							});
			if(reqOp != null) {
				activity = reqOp.getAction_semantics();
			}
		}
		else if(activityType == REQ_SIGNAL) {
			RequiredSignal_c reqSig = RequiredSignal_c
					.RequiredSignalInstance(ooaofooa,
							new ClassQueryInterface_c() {
								public boolean evaluate(Object candidate) {
									return ((RequiredSignal_c) candidate)
											.getId().equals(activity_id);
								}
							});
			if(reqSig != null) {
				activity = reqSig.getAction_semantics();
			}
		}
		else if(activityType == PROV_OPERATION) {
			ProvidedOperation_c proOp = ProvidedOperation_c
					.ProvidedOperationInstance(ooaofooa,
							new ClassQueryInterface_c() {
								public boolean evaluate(Object candidate) {
									return ((ProvidedOperation_c) candidate)
											.getId().equals(activity_id);
								}
							});
			if(proOp != null) {
				activity = proOp.getAction_semantics();
			}
		}
		else if(activityType == PROV_SIGNAL) {
			ProvidedSignal_c proSig = ProvidedSignal_c
					.ProvidedSignalInstance(ooaofooa,
							new ClassQueryInterface_c() {
								public boolean evaluate(Object candidate) {
									return ((ProvidedSignal_c) candidate)
											.getId().equals(activity_id);
								}
							});
			if(proSig != null) {
				activity = proSig.getAction_semantics();
			}
		}
		if ( activity.equals("")) {
			return -1;
		}
		else {
		    String [] lines = activity.split("\n");
		    return lines.length;
		}
	}

	private static Statement_c findStatementOnLine(Ooaofooa modelRoot, final UUID activity_id, final int activityType, final int lineNumber) {
		Body_c body = null;
		if ( activityType == FUNCTION) {
			Function_c f = Function_c.FunctionInstance(modelRoot, new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate) {
					Function_c selected = (Function_c)candidate;
					return selected.getSync_id().equals(activity_id);
				}
			});
			body = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(f));
		}
		else if ( activityType == BRIDGE ) {
			Bridge_c brg = Bridge_c.BridgeInstance(modelRoot, new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate) {
					Bridge_c selected = (Bridge_c)candidate;
					return selected.getBrg_id().equals(activity_id);
				}
			});
			body = Body_c.getOneACT_ACTOnR698(BridgeBody_c.getOneACT_BRBOnR697(brg));
		}
		else if ( activityType == STATE ) {
			StateMachineState_c state = StateMachineState_c.StateMachineStateInstance(modelRoot, new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate) {
					StateMachineState_c selected = (StateMachineState_c)candidate;
					return selected.getSmstt_id().equals(activity_id);
				}
			});
			Action_c act = Action_c.getOneSM_ACTOnR514(ActionHome_c.getOneSM_AHOnR513(
					MooreActionHome_c.getOneSM_MOAHOnR511(state)));
			body = Body_c.getOneACT_ACTOnR698(StateActionBody_c.getOneACT_SABOnR691(act));
		}
		else if ( activityType == TRANSITION ) {
			Transition_c trans = Transition_c.TransitionInstance(modelRoot, new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate) {
					Transition_c selected = (Transition_c)candidate;
					return selected.getTrans_id().equals(activity_id);
				}
			});
			Action_c act = Action_c.getOneSM_ACTOnR514(ActionHome_c.getOneSM_AHOnR513(
					TransitionActionHome_c.getOneSM_TAHOnR530(trans)));
			body = Body_c.getOneACT_ACTOnR698(TransitionActionBody_c.getOneACT_TABOnR688(act));
		}
		else if( activityType == CREATION_TRANSITION){
			CreationTransition_c ctrans=CreationTransition_c.CreationTransitionInstance(modelRoot, new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate){
					CreationTransition_c selected=((CreationTransition_c) candidate);
					return selected.getTrans_id().equals(activity_id);
				}
			});
			Transition_c trans=Transition_c.getOneSM_TXNOnR507(ctrans);
			Action_c act = Action_c.getOneSM_ACTOnR514(ActionHome_c.getOneSM_AHOnR513(
					TransitionActionHome_c.getOneSM_TAHOnR530(trans)));
			body = Body_c.getOneACT_ACTOnR698(TransitionActionBody_c.getOneACT_TABOnR688(act));
		}
		else if ( activityType == OPERATION ) {
			Operation_c op = Operation_c.OperationInstance(modelRoot, new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate) {
					Operation_c selected = (Operation_c)candidate;
					return selected.getTfr_id().equals(activity_id);
				}
			});
			body = Body_c.getOneACT_ACTOnR698(OperationBody_c.getOneACT_OPBOnR696(op));
		}
		else if ( activityType == MDA ) {
			DerivedBaseAttribute_c dba = DerivedBaseAttribute_c.DerivedBaseAttributeInstance(modelRoot, new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate) {
					DerivedBaseAttribute_c selected = (DerivedBaseAttribute_c)candidate;
					return selected.getAttr_id().equals(activity_id);
				}
			});
			body = Body_c.getOneACT_ACTOnR698(DerivedAttributeBody_c.getOneACT_DABOnR693(dba));
		}
		else if(activityType == REQ_OPERATION) {
			RequiredOperation_c reqOp = RequiredOperation_c
					.RequiredOperationInstance(modelRoot,
							new ClassQueryInterface_c() {
								public boolean evaluate(Object candidate) {
									return ((RequiredOperation_c) candidate)
											.getId().equals(activity_id);
								}
							});
			body = Body_c.getOneACT_ACTOnR698(RequiredOperationBody_c
					.getOneACT_ROBOnR685(reqOp));
		}
		else if(activityType == REQ_SIGNAL) {
			RequiredSignal_c reqSig = RequiredSignal_c
					.RequiredSignalInstance(modelRoot,
							new ClassQueryInterface_c() {
								public boolean evaluate(Object candidate) {
									return ((RequiredSignal_c) candidate)
											.getId().equals(activity_id);
								}
							});
			body = Body_c.getOneACT_ACTOnR698(RequiredSignalBody_c
					.getOneACT_RSBOnR684(reqSig));
		}
		else if(activityType == PROV_OPERATION) {
			ProvidedOperation_c proOp = ProvidedOperation_c
					.ProvidedOperationInstance(modelRoot,
							new ClassQueryInterface_c() {
								public boolean evaluate(Object candidate) {
									return ((ProvidedOperation_c) candidate)
											.getId().equals(activity_id);
								}
							});
			body = Body_c.getOneACT_ACTOnR698(ProvidedOperationBody_c
					.getOneACT_POBOnR687(proOp));
		}
		else if(activityType == PROV_SIGNAL) {
			ProvidedSignal_c proSig = ProvidedSignal_c
					.ProvidedSignalInstance(modelRoot,
							new ClassQueryInterface_c() {
								public boolean evaluate(Object candidate) {
									return ((ProvidedSignal_c) candidate)
											.getId().equals(activity_id);
								}
							});
			body = Body_c.getOneACT_ACTOnR698(ProvidedSignalBody_c
					.getOneACT_PSBOnR686(proSig));
		}
		
		Statement_c ret_val = null;
		if ( body != null ) {
			Block_c [] blk = Block_c.getManyACT_BLKsOnR601(body);
			Statement_c [] stmts = Statement_c.getManyACT_SMTsOnR602(blk, new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate) {
					Statement_c selected = (Statement_c)candidate;
					return selected.getLinenumber() == lineNumber;
				}
			});
			int first_col = Integer.MAX_VALUE;
			for ( int i = 0; i < stmts.length; ++i ) {
				if ( stmts[i].getLinenumber() < first_col ) {
					first_col = stmts[i].getLinenumber();
					ret_val = stmts[i];
				}
			}
		}
		return ret_val;
	}

	private OalBreakpoint_c findBPForStatementOnLine(Ooaofooa modelRoot, final UUID ooa_id, final int activityType, int lineNumber) {
		final Statement_c stmt = findStatementOnLine(modelRoot, ooa_id, activityType, lineNumber);
		if (stmt != null ) {
			return OalBreakpoint_c.OalBreakpointInstance(modelRoot, new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate) {
					OalBreakpoint_c selected = (OalBreakpoint_c)candidate;
					return selected.getStatement_id() == stmt.getStatement_id();
				}
			});
		}
		return null;
	}
}
