package com.mentor.nucleus.bp.debug.ui.model;
//====================================================================
//
// File:      $RCSfile: BPStackFrame.java,v $
// Version:   $Revision: 1.19 $
// Modified:  $Date: 2013/01/10 23:18:08 $
//
// (c) Copyright 2007-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
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
import java.util.ArrayList;

import lib.TIM;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;

import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BaseAttribute_c;
import com.mentor.nucleus.bp.core.BlockInStackFrame_c;
import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.Body_c;
import com.mentor.nucleus.bp.core.BridgeBody_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.DataItemValue_c;
import com.mentor.nucleus.bp.core.DerivedAttributeBody_c;
import com.mentor.nucleus.bp.core.DerivedBaseAttribute_c;
import com.mentor.nucleus.bp.core.EventQueueEntry_c;
import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.FunctionBody_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.LocalReference_c;
import com.mentor.nucleus.bp.core.LocalValue_c;
import com.mentor.nucleus.bp.core.Local_c;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.MooreActionHome_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.OperationBody_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.PendingEvent_c;
import com.mentor.nucleus.bp.core.ProvidedOperationBody_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.ProvidedSignalBody_c;
import com.mentor.nucleus.bp.core.ProvidedSignal_c;
import com.mentor.nucleus.bp.core.RequiredOperationBody_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.RequiredSignalBody_c;
import com.mentor.nucleus.bp.core.RequiredSignal_c;
import com.mentor.nucleus.bp.core.SelfQueueEntry_c;
import com.mentor.nucleus.bp.core.StackFrame_c;
import com.mentor.nucleus.bp.core.Stack_c;
import com.mentor.nucleus.bp.core.StateActionBody_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.Statement_c;
import com.mentor.nucleus.bp.core.Timer_c;
import com.mentor.nucleus.bp.core.TransitionActionBody_c;
import com.mentor.nucleus.bp.core.TransitionActionHome_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.ModelRoot;

public class BPStackFrame extends BPDebugElement implements IStackFrame {

	private StackFrame_c stackFrame = null;
	private PendingEvent_c event = null;
	private BPThread thread = null;

	public BPStackFrame(BPDebugTarget debugTarget, ILaunch launch,
			StackFrame_c stackFrame, BPThread thread) {
		super(debugTarget, launch);
		this.stackFrame = stackFrame;
		this.thread = thread;
	}

	public BPStackFrame(BPDebugTarget debugTarget, ILaunch launch,
			PendingEvent_c event, BPThread thread) {
		super(debugTarget, launch);
		this.event = event;
		this.thread = thread;
	}

	public IThread getThread() {
		return thread;
	}
	
	public StackFrame_c getStackFrame() {
		return stackFrame;
	}

	public PendingEvent_c getEvent() {
		return event;
	}
	public IVariable[] getVariables() throws DebugException {
	  if (stackFrame != null) {
		PendingEvent_c execEvt = null;
		StateActionBody_c currentStateAction = StateActionBody_c
				       .getOneACT_SABOnR698(Body_c
					  	       .getOneACT_ACTOnR601(Block_c
								       .getOneACT_BLKOnR2923(BlockInStackFrame_c
										       .getOneI_BSFOnR2923(stackFrame))));
		TransitionActionBody_c currentTransitionAction = TransitionActionBody_c
	       					.getOneACT_TABOnR698(Body_c
	       							.getOneACT_ACTOnR601(Block_c
	       									.getOneACT_BLKOnR2923(BlockInStackFrame_c
	       											.getOneI_BSFOnR2923(stackFrame))));
		if (currentStateAction != null || currentTransitionAction != null){
			PendingEvent_c[] evts = PendingEvent_c
						.getManyI_EVIsOnR2944(EventQueueEntry_c
								.getManyI_EQEsOnR2944(ComponentInstance_c
										.getManyI_EXEsOnR2930(Stack_c
												.getManyI_STACKsOnR2929(stackFrame))));
			
			if(evts.length == 0) {
				evts = PendingEvent_c.getManyI_EVIsOnR2946(SelfQueueEntry_c
							.getManyI_SQEsOnR2946(ComponentInstance_c
									.getManyI_EXEsOnR2930(Stack_c
											.getManyI_STACKsOnR2929(stackFrame))));
			}
			for (int i=0; i < evts.length; i++) {
				if (evts[i].getIsexecuting() == true) {
					execEvt = evts[i];
					break;
				}
			}
		}
		DataItemValue_c [] evtdis = DataItemValue_c.getManyI_DIVsOnR2933(execEvt);
		LocalValue_c [] localVals = LocalValue_c.getManyL_LVLsOnR3001(Local_c.getManyL_LCLsOnR3000(
		          BlockInStackFrame_c.getManyI_BSFsOnR2923(stackFrame)));
		LocalReference_c [] localRefs = LocalReference_c.getManyL_LCRsOnR3001(Local_c.getManyL_LCLsOnR3000(
		          BlockInStackFrame_c.getManyI_BSFsOnR2923(stackFrame)));
		IVariable [] result = new IVariable[evtdis.length + localVals.length + localRefs.length];
		for(int i=0; i < evtdis.length; i++) {
			result[i] = new BPVariable(getDebugTarget(), getLaunch(), evtdis[i]);
		}
		for (int i=0; i < localVals.length; i++) {
          result[i + evtdis.length] = new BPVariable(getDebugTarget(), getLaunch(), localVals[i]);
		}
		for (int i=0; i < localRefs.length; i++) {
	      result[i + evtdis.length + localVals.length] = new BPVariable(getDebugTarget(), getLaunch(), localRefs[i]);
		}
		return result;
	  }
	  else if (event != null){
        DataItemValue_c [] divs = DataItemValue_c.getManyI_DIVsOnR2933(event);
		IVariable [] result = new IVariable[divs.length];
		for(int i=0; i < divs.length; i++) {
			result[i] = new BPVariable(getDebugTarget(), getLaunch(), divs[i]);
		}
		return result;
	  }
	  return new IVariable[0];
	}

	public boolean hasVariables() throws DebugException {
		return getVariables().length > 0;
	}

	public int getLineNumber() throws DebugException {
		if (stackFrame != null) {
			BlockInStackFrame_c [] bisfs = BlockInStackFrame_c.getManyI_BSFsOnR2923(stackFrame);
			for (int i=0; i < bisfs.length; i++) {
				if (bisfs[i].getIsexecuting()){
					Statement_c stmt = Statement_c.getOneACT_SMTOnR2941(bisfs[i]);
				    if (stmt != null)
				      return stmt.getLinenumber();
				}
			}
		}
		return 0;
	}

	public int getCharStart() throws DebugException {
		if (stackFrame != null) {
			String activityText = "";
			Object activity = getActivityContainerElement();
			if (activity instanceof StateMachineState_c) {
				Action_c action = Action_c.getOneSM_ACTOnR514(
						                  ActionHome_c.getOneSM_AHOnR513(
						                  MooreActionHome_c.getOneSM_MOAHOnR511(
						                       (StateMachineState_c)activity)));
				activityText = action.getAction_semantics();
			}
			if (activity instanceof Transition_c) {
                Action_c action = Action_c.getOneSM_ACTOnR514(
		                  ActionHome_c.getOneSM_AHOnR513(
		                		  TransitionActionHome_c.getOneSM_TAHOnR530(
		                				              (Transition_c)activity)));
                activityText = action.getAction_semantics();
			}
			if (activity instanceof Function_c) {
				activityText = ((Function_c)activity).getAction_semantics();
			}
			if (activity instanceof Bridge_c) {
				activityText = ((Bridge_c)activity).getAction_semantics();
			}
			if (activity instanceof Operation_c) {
				activityText = ((Operation_c)activity).getAction_semantics();
			}
			if (activity instanceof Attribute_c) {
				activityText = DerivedBaseAttribute_c.getOneO_DBATTROnR107(
						        BaseAttribute_c.getOneO_BATTROnR106(
								  (Attribute_c)activity)).getAction_semantics();
			} 
			if(activity instanceof ProvidedSignal_c) {
				activityText = ((ProvidedSignal_c) activity).getAction_semantics();
			}
			if(activity instanceof ProvidedOperation_c) {
				activityText = ((ProvidedOperation_c) activity).getAction_semantics();
			}
			if(activity instanceof RequiredSignal_c) {
				activityText = ((RequiredSignal_c) activity).getAction_semantics();
			}
			if(activity instanceof RequiredOperation_c) {
				activityText = ((RequiredOperation_c) activity).getAction_semantics();
			}
			BlockInStackFrame_c bisf = null;
			BlockInStackFrame_c [] bisfs = BlockInStackFrame_c.getManyI_BSFsOnR2923(stackFrame);
			for (int i=0; i < bisfs.length; i++) {
				if (bisfs[i].getIsexecuting()){
					bisf = bisfs[i];
					break;
				}
			}
			Statement_c stmt = Statement_c.getOneACT_SMTOnR2941(bisf);
			if (stmt != null && stmt.getLinenumber() != -1 && !activityText.equals("")) {
			  // work through until statement is reached adding up char offsets
			  String endl = "\n";
			  int endLen = endl.length();
			  String [] lines = activityText.split("\n");
			  int charStart = 0;
			  int i = 0;
			  for (; i < lines.length; i++) {
				if (i == stmt.getLinenumber() - 1) {
					  break;
				}
				charStart = charStart + lines[i].length() + endLen;
			  }
              int stmtEndPos = lines[i].indexOf(';', stmt.getStartposition());
              if (stmtEndPos != -1) {
                charEnd = charStart + stmtEndPos + 1;
              }
              else {
            	// TODO This needs work. Currently, if we don't find a semi-
            	// colon, we assume the end of the line is good. This won't
            	// work for all coding styles, since it is possible that a
            	// statement may be split across multiple lines. Similarly, we
            	// can't assume above that a semicolon will necessarily be
            	// on the same line as the statement start. An experiment was
            	// made that works backwards from the beginning of the next
            	// statement, but this doesn't work with optional reserved words
            	// (this is fixable in the parser) or comments (needs a bunch of
            	// hacked code to work back through the comments, or a
            	// significant parser investment to make comments part of the
            	// OAL syntax.
                charEnd = charStart + lines[i].length() + 1;
              }
			  return charStart + stmt.getStartposition() - 1;
		      
			}
		}
		return 0;
	}
	
	private int charEnd = 0;

	public int getCharEnd() throws DebugException {
		return charEnd;
	}

	public IRegisterGroup[] getRegisterGroups() throws DebugException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasRegisterGroups() throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isTopEvent() {
		if (event != null) {
			SelfQueueEntry_c sqe = SelfQueueEntry_c.getOneI_SQEOnR2946(event);
			EventQueueEntry_c eqe = EventQueueEntry_c.getOneI_EQEOnR2944(event);
			// Don't test the regular event queue if self events exist.
			if (sqe != null) {
				return SelfQueueEntry_c.getOneI_SQEOnR2947Precedes(sqe) == null;
			}
			sqe = SelfQueueEntry_c.getOneI_SQEOnR2946(thread.getEngine());
			// Don't test the regular event queue if any self events exist.
			if (eqe != null && sqe == null) {
				return EventQueueEntry_c.getOneI_EQEOnR2945Precedes(eqe) == null;
			}
		}
		return false;
	}
	public boolean canStepInto() {
		if (event != null) {
			if (isTopEvent()) {
				StackFrame_c tsf = StackFrame_c.getOneI_STFOnR2929(
						      Stack_c.getOneI_STACKOnR2930(thread.getEngine()));
				// We can step into an event only if it is the top one and there
				// is no top stack frame (i.e. we cannot step into it twice).
				return tsf == null;
			}
		}
		else {
		  if (stackFrame != null) {
		    boolean susp = thread.isSuspended();
		    StackFrame_c tsf = StackFrame_c.getOneI_STFOnR2929(Stack_c.getOneI_STACKOnR2943(stackFrame));
		    if (susp && (tsf == null || tsf == stackFrame) && !thread.isBlocked()) {
			  return true;
		    }
		  }
		}
		return false;
	}

	public boolean canStepOver() {
		if (event != null) {
			if (isTopEvent()) {
				StackFrame_c tsf = StackFrame_c.getOneI_STFOnR2929(
						      Stack_c.getOneI_STACKOnR2930(thread.getEngine()));
				// We can step over an event only if it is the top one and there
				// is no top stack frame.
				if (tsf == null) {
				  return thread.canStepOver();
				}
			}
		}
		else {
		  if (stackFrame != null) {
		    return thread.canStepOver();
		  }
		}
		return false;
	}

	public boolean canStepReturn() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isStepping() {
		// TODO Auto-generated method stub
		return false;
	}

	public void stepInto() throws DebugException {
		((BPThread)getThread()).resetFrame();
        Stack_c stack = Stack_c.getOneI_STACKOnR2930(((BPThread)getThread()).getEngine());
  		if (stack != null) {
	          ModelRoot.disableChangeNotification();
	      	  try {
	      		((BPThread)getThread()).fireResumeEvent(DebugEvent.STEP_OVER);
	      		if (stackFrame != null)
	    		  stack.Stepin();
	      		else {
	      		  if (event != null) {
	      			// If the event is waiting on a timeout, force it to expire immediately
	      		    Timer_c tmr = Timer_c.getOneI_TIMOnR2940(event);
	      		    if (tmr != null) {
                      tmr.Fire();
	      		    }
	      		  }
	      		  stack.Stepin();	
	      		}
	      	  }
	      	  finally {
                ((BPThread)getThread()).fireSuspendEvent(DebugEvent.STEP_END);
	            ModelRoot.enableChangeNotification();
	            ComponentInstance_c fee = ((BPThread)getThread()).getEngine();
		        ModelChangedEvent mce = new ModelChangedEvent(fee.getModelRoot(), Modeleventnotification_c.MODEL_EXECUTION_STOPPED, fee);
		        Ooaofooa.getDefaultInstance().fireModelEvent(mce);
		        BPThread.refreshVerifierViews();
		        BPThread.refreshCanvases();
		        ArrayList<BPDebugTarget> targets = BPDebugTarget.getTargets();
		        for (BPDebugTarget target : targets) {
					target.Notify();
				}
	      	  }
  		}
	}

	public void stepOver() throws DebugException {
		((BPThread)getThread()).resetFrame();
        Stack_c stack = Stack_c.getOneI_STACKOnR2930(((BPThread)getThread()).getEngine());
  		if (stack != null) {
	          ModelRoot.disableChangeNotification();
	      	  try {
	      		((BPThread)getThread()).fireResumeEvent(DebugEvent.STEP_OVER);
	      		if (stackFrame != null)
	    		  stack.Stepover(stackFrame.getStack_frame_id());
	      		else {
	      		  if (event != null) {
	      			// If the event is waiting on a timeout, force it to expire immediately
	      		    Timer_c tmr = Timer_c.getOneI_TIMOnR2940(event);
	      		    if (tmr != null) {
                      tmr.Fire();
	      		    }
	      		  }
	      		  stack.Stepover(Gd_c.Null_unique_id());	
	      		}
	      	  }
	      	  finally {
                ((BPThread)getThread()).fireSuspendEvent(DebugEvent.STEP_END);
	            ModelRoot.enableChangeNotification();
	            ComponentInstance_c fee = ((BPThread)getThread()).getEngine();
		        ModelChangedEvent mce = new ModelChangedEvent(fee.getModelRoot(), Modeleventnotification_c.MODEL_EXECUTION_STOPPED, fee);
		        Ooaofooa.getDefaultInstance().fireModelEvent(mce);
		        BPThread.refreshVerifierViews();
		        BPThread.refreshCanvases();
		        ArrayList<BPDebugTarget> targets = BPDebugTarget.getTargets();
		        for (BPDebugTarget target : targets) {
					target.Notify();
				}
	      	  }
  		}
	}

	public void stepReturn() throws DebugException {
		// TODO Auto-generated method stub

	}

	public boolean canResume() {
		return thread.canResume();
	}

	public boolean canSuspend() {
		return thread.canSuspend();
	}

	public boolean isSuspended() {
	  if (isEvent()) {
		  return false;
	  }
	  return thread.isSuspended();
	}

	public void resume() throws DebugException {
		thread.resume();		
		TIM.resumeTime();
	}

	public void suspend() throws DebugException {
		thread.suspend();
		TIM.suspendTime();
	}

	public boolean canTerminate() {
		return thread.canTerminate();
	}

	public boolean isTerminated() {
		return thread.isTerminated();
	}

	public void terminate() throws DebugException {
		thread.terminate();
	}
	public String getName() {
		if (event != null) {
			return event.getLabel();
		}
		else if (stackFrame != null) {
			BlockInStackFrame_c bisf = null;
			BlockInStackFrame_c [] bisfs = BlockInStackFrame_c.getManyI_BSFsOnR2923(stackFrame);
			for (int i=0; i < bisfs.length; i++) {
				if (bisfs[i].getIsexecuting()){
					bisf = bisfs[i];
					break;
				}
			}
		  Statement_c stmt = Statement_c.getOneACT_SMTOnR2941(bisf);
		  if (stmt != null) {
		    return stmt.getLabel();
		  }
          return "Error, undefined statement.";
		}
		return "Error, undefined stack frame.";
	}

	public boolean isEvent() {
		return event != null;
	}
	
	public void setStackFrame(StackFrame_c sf) {
		stackFrame = sf;
	}
	
	public Object getActivityContainerElement() {
		BlockInStackFrame_c bisf = null;
		BlockInStackFrame_c [] bisfs = BlockInStackFrame_c.getManyI_BSFsOnR2923(this.getStackFrame());
		for (int i=0; i < bisfs.length; i++) {
			if (bisfs[i].getIsexecuting()){
				bisf = bisfs[i];
				break;
			}
		}
		Body_c bdy = Body_c.getOneACT_ACTOnR601(Block_c.getOneACT_BLKOnR602(
						               Statement_c.getOneACT_SMTOnR2941(bisf))); 
		StateMachineState_c state = StateMachineState_c.getOneSM_STATEOnR511(
				           MooreActionHome_c.getOneSM_MOAHOnR513(
				             ActionHome_c.getOneSM_AHOnR514(
				               Action_c.getOneSM_ACTOnR691(
				                 StateActionBody_c.getOneACT_SABOnR698(bdy)))));
		if (state != null) {
		  return state;
		}
		Transition_c trans = Transition_c.getOneSM_TXNOnR530(
		           TransitionActionHome_c.getOneSM_TAHOnR513(
		             ActionHome_c.getOneSM_AHOnR514(
		               Action_c.getOneSM_ACTOnR688(
		                 TransitionActionBody_c.getOneACT_TABOnR698(bdy)))));
		if (trans != null) {
			return trans;
		}
		Function_c fn = Function_c.getOneS_SYNCOnR695(
        		                       FunctionBody_c.getOneACT_FNBOnR698(bdy));
        if (fn != null) {
		  return fn;
		}
        Bridge_c br = Bridge_c.getOneS_BRGOnR697(
                                         BridgeBody_c.getOneACT_BRBOnR698(bdy));
		if (br != null) {
		  return br;
		}
		Operation_c op = Operation_c.getOneO_TFROnR696(
				                      OperationBody_c.getOneACT_OPBOnR698(bdy));
		if (op != null) {
			return op;
	    }
		Attribute_c dba = Attribute_c.getOneO_ATTROnR106(
				         BaseAttribute_c.getManyO_BATTRsOnR107(
			               DerivedBaseAttribute_c.getOneO_DBATTROnR693(
				             DerivedAttributeBody_c.getOneACT_DABOnR698(bdy))));
		if (dba != null) {
			return dba;
		}
		RequiredOperation_c reqOp = RequiredOperation_c
				.getOneSPR_ROOnR685(RequiredOperationBody_c
						.getOneACT_ROBOnR698(bdy));
		if(reqOp != null) {
			return reqOp;
		}
		RequiredSignal_c reqSig = RequiredSignal_c
				.getOneSPR_RSOnR684(RequiredSignalBody_c
						.getOneACT_RSBOnR698(bdy));
		if(reqSig != null) {
			return reqSig;
		}
		ProvidedOperation_c proOp = ProvidedOperation_c
				.getOneSPR_POOnR687(ProvidedOperationBody_c
						.getOneACT_POBOnR698(bdy));
		if(proOp != null) {
			return proOp;
		}
		ProvidedSignal_c proSig = ProvidedSignal_c
				.getOneSPR_PSOnR686(ProvidedSignalBody_c
						.getOneACT_PSBOnR698(bdy));
		if(proSig != null) {
			return proSig;
		}
		return null;
	}
	public void fireDBEvt() {
		  fireChangeEvent(DebugEvent.CONTENT);
	}
	public boolean equals(Object o) {
		if (o instanceof BPStackFrame) {
		  BPStackFrame bpsf = (BPStackFrame)o;
		  return bpsf.getStackFrame() == getStackFrame() && bpsf.getEvent() == getEvent();
		}
		return false;
	}
}
