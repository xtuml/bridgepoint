package com.mentor.nucleus.bp.debug.ui.model;

//====================================================================
//
// File:      $RCSfile: BPThread.java,v $
// Version:   $Revision: 1.41 $
// Modified:  $Date: 2013/05/12 00:16:37 $
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import lib.LOG;
import lib.TIM;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.BlockInStackFrame_c;
import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.EventQueueEntry_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PendingEvent_c;
import com.mentor.nucleus.bp.core.Runstatetype_c;
import com.mentor.nucleus.bp.core.SelfQueueEntry_c;
import com.mentor.nucleus.bp.core.StackFrame_c;
import com.mentor.nucleus.bp.core.Stack_c;
import com.mentor.nucleus.bp.core.Statement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.Timer_c;
import com.mentor.nucleus.bp.core.Vm_c;
import com.mentor.nucleus.bp.core.common.IModelChangeListener;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.debug.java.access.VerifierInvocationHandler;
import com.mentor.nucleus.bp.ui.explorer.MonitorView;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.session.views.SessionExplorerView;

public class BPThread extends BPDebugElement implements IThread {
	private ComponentInstance_c engine = null;
	private BPStackFrame frame = null;
	private static IModelChangeListener changeListener = null;
	private static ArrayList<BPThread> instances = new ArrayList<BPThread>();
	private static volatile Map<StructuredViewer, Boolean> refreshIsPending =
		                               new HashMap<StructuredViewer, Boolean>();
	private static volatile boolean canvasRefreshPending = false;
	
	public BPThread(BPDebugTarget debugTarget, ILaunch launch,
			ComponentInstance_c engine) {
		super(debugTarget, launch);
		this.engine = engine;
		instances.add(this);
	}

	Thread runner = null;

	public Thread getRunner() {
		return runner;
	}

	public void startModel() {
		if (!engine.getRunning()) {
			final ComponentInstance_c fee = engine;
			final Domain_c domain = Domain_c.getOneS_DOMOnR2948(engine);
			Component_c tgtComponent = Component_c.getOneC_COnR2955(engine);
			if (tgtComponent == null) {
				tgtComponent = Component_c.getOneC_COnR4201(ComponentReference_c
						.getOneCL_ICOnR2963(engine));
			}
			String name = "";
			if (domain != null)
				name = domain.getName();
			if (tgtComponent != null)
				name = tgtComponent.getName();
			runner = new Thread(new Runnable() {
				public void run() {
					fee.setRunning(true);
					try {
						ModelRoot.disableChangeNotification();
						while (fee != null && fee.getRunning()) {
						  Stack_c stack = Stack_c.getOneI_STACKOnR2930(fee);
						  if (fee.getRealizedby() != null) { // realized execution
					    		VerifierInvocationHandler.executeRealizedCode(stack);
								if (stack.getRunstate() == Runstatetype_c.Terminated) {
									try {
										BPThread thread = BPThread
														.getThreadExecuting(fee);
										if (thread != null) {
											thread.terminate();
										}
									} catch (DebugException de) {
										// do nothing
									}
									break;
								}
						  }
						  else {  // Modeled execution
							int lastRunState = Runstatetype_c.OOA_UNINITIALIZED_ENUM;
							boolean modelStateChanged = false;
							try {
								Ooaofooa.beginVerifierExecution(fee
											.getExecution_engine_id());
								stack = Stack_c.getOneI_STACKOnR2930(fee);
								if (stack != null) {
									lastRunState = stack.getRunstate();
									modelStateChanged = stack.Run();
									if (stack.getRunstate() == Runstatetype_c.Terminated) {
										try {
											BPThread thread = BPThread
														.getThreadExecuting(fee);
											if (thread != null) {
												thread.terminate();
											}
										} catch (DebugException de) {
											// do nothing
										}
									}
								}
							} catch (Exception e) {
									DebugUIPlugin.log(e);
							} finally {
									Ooaofooa.endVerifierExecution(fee
											.getExecution_engine_id());
							}
							stack = Stack_c.getOneI_STACKOnR2930(fee);
							if (stack != null
									&& stack.getRunstate() == Runstatetype_c.Suspended
									&& lastRunState != Runstatetype_c.Suspended) {
								fireSuspendEvent(DebugEvent.BREAKPOINT);
								try {
								  notifyTargetOfSuspension();
								}
								catch (DebugException de) {
									DebugUIPlugin.logErrorMessage("Debug exception encountered servicing breakpoint: " + de);
								}
							}
							if (modelStateChanged) {
								ModelChangedEvent mce = new ModelChangedEvent(
										Ooaofooa.getDefaultInstance(),
										Modeleventnotification_c.MODEL_EXECUTION_STOPPED,
										fee);
								Ooaofooa.getDefaultInstance().fireModelEvent(
										mce);
							}
						  }
							synchronized (fee) {
								fee.Lockqueue("I_EQE");
								EventQueueEntry_c eqe = EventQueueEntry_c
										.getOneI_EQEOnR2944(fee);
								fee.Unlockqueue("I_EQE");
								fee.Lockqueue("I_SQE");
								SelfQueueEntry_c sqe = SelfQueueEntry_c
										.getOneI_SQEOnR2946(fee);
								fee.Unlockqueue("I_SQE");
								StackFrame_c blockedStf = StackFrame_c.getOneI_STFOnR2965BlockedBy(StackFrame_c.getOneI_STFOnR2929(stack));
								boolean qdFramesReady = qdFramesReady(stack);
								boolean qdResultsReady = qdResultsReady(stack);
								BPThread.refreshVerifierViews();
								BPThread.refreshCanvases();
								if (stack != null
										&& (stack.getRunstate() == Runstatetype_c.Suspended || (eqe == null
												&& sqe == null)) || (blockedStf != null)) {
									try {
										ModelRoot.enableChangeNotification();
										if ((!qdFramesReady && !qdResultsReady) || stack.getRunstate() == Runstatetype_c.Suspended) {
											if (TIM.isSIM_TIME()) {
												TIM.idleNotification(fee);
											}
										  if (stack.getRunstate() == Runstatetype_c.Suspended) {
										    TIM.suspendTime();
										  }
											
											fee.wait();
										}
									} catch (InterruptedException ie) {
											// TODO log error, should not
											// happen
									}
									finally {
											ModelRoot.disableChangeNotification();
									}
									ModelChangedEvent mce = new ModelChangedEvent(
											fee.getModelRoot(),
											Modeleventnotification_c.MODEL_EXECUTION_STOPPED,
											fee);
									Ooaofooa.getDefaultInstance()
											.fireModelEvent(mce);
								}
							}
							BPThread.refreshVerifierViews();
							BPThread.refreshCanvases();
						}
					} finally {
						ModelRoot.enableChangeNotification();
					}
					int haltedCardinality = 0;
					Stack_c[] stacks = Stack_c.getManyI_STACKsOnR2930(engine);
					for (int i = 0; i < stacks.length; i++) {
						if (stacks[i].getRunstate() == Runstatetype_c.Suspended) {
							haltedCardinality++;
						}
					}
					if (haltedCardinality > 1) {
						DebugUIPlugin.logErrorMessage("Multiple ("
								+ haltedCardinality + ") halted stacks.");
					}

				};
			}, "Verifier (" + name + ")");
			runner.setPriority(Thread.MIN_PRIORITY);
			runner.start();
			Thread.State state = runner.getState();
			while (state != Thread.State.WAITING) {
				// Give the new thread a change to start
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				state = runner.getState();
			}

			if (changeListener != null) {
				Ooaofooa.getDefaultInstance().removeModelChangeListener(
						changeListener);
			}
			changeListener = new VerifierModelChangeListener(this);
			// Install change listener
			// Add is ignored if listener is already installed
			Ooaofooa.getDefaultInstance()
					.addModelChangeListener(changeListener);
		}
	}

	/**
	 * Refresh SE viewer.  Note that there is a similar routine in UIUtils, however,
	 * the difference is that we must make sure here that there is only 1 
	 * outstanding refresh pending at a time.  If we do not do this, 
	 * refresh request stack up which causes resources to be held in
	 * queue pending a refresh.  This causes performance problems and
	 * eventually chews-up all resources in the environment.
	 * 
	 * @param viewer
	 */
    private static void refreshViewer(final StructuredViewer viewer) 
    {
    	if (viewer == null) {
    		return;
    	}
    	if (!refreshIsPending.containsKey(viewer)) {
    		refreshIsPending.put(viewer, new Boolean(false));
    	}
   		if (refreshIsPending.get(viewer)) {
   			return;
   		} else {
   			refreshIsPending.put(viewer, new Boolean(true));
   		}
    	
		Control control = viewer.getControl();
		if (!control.isDisposed()) {
            Display display = control.getDisplay();
            // ask the viewer's UI thread to perform the refresh
            display.asyncExec(
               new Runnable() {
                   public void run() {
                	   try {
                	   viewer.refresh();
                	   }
                	   finally {
                	     refreshIsPending.put(viewer, new Boolean(false));
                       }
                   }
            });
		}
    }
    
    public static void refreshCanvases() {
    	if (canvasRefreshPending) {
    		return;
    	}
    	else {
    		canvasRefreshPending = true;
    		Display disp = DebugUIPlugin.getStandardDisplay();
    		disp.asyncExec(new Runnable() {
    			public void run() {
    				GraphicalEditor.redrawAll();
    				canvasRefreshPending =  false;
    			}
    		});
    	}
    }
    
    public static void refreshVerifierViews() {
		SessionExplorerView instance = SessionExplorerView.instance();
		BPThread.refreshViewer(instance.getTreeViewer());
		MonitorView spotlight = MonitorView.instance();
		BPThread.refreshViewer(spotlight.getTreeViewer());
    }
	public IStackFrame[] getStackFrames() throws DebugException {
		if (!isSuspended()) {
			frame = null;
			return new IStackFrame[0];
		}
		ArrayList<BPStackFrame> result = new ArrayList<BPStackFrame>();
		// Don't show stack frames for realized components
		if(engine.getRealizedby() == null) {
		  StackFrame_c[] sfs = StackFrame_c.getManyI_STFsOnR2943(Stack_c
				.getManyI_STACKsOnR2930(engine));
		  for (int i = sfs.length - 1; i >= 0; i--) {
			BlockInStackFrame_c[] bisfs = BlockInStackFrame_c
					.getManyI_BSFsOnR2923(sfs[i]);
			for (int j = 0; j < bisfs.length; j++) {
				if (bisfs[j].getIsexecuting()) {
					result.add(new BPStackFrame(
							(BPDebugTarget) getDebugTarget(), getLaunch(),
							sfs[i], this));
				}
			}
		  }
		}
		// TODO stop navigation at queue entry classes and delegate calls
		// TODO Figure out why event queues are not shown
		// Self events
		engine.Lockqueue("I_SQE");
		PendingEvent_c[] spes = PendingEvent_c.getManyI_EVIsOnR2946(engine);
		for (int i = 0; i < spes.length; i++) {
			result.add(new BPStackFrame((BPDebugTarget) getDebugTarget(),
					getLaunch(), spes[i], this));
		}
		engine.Unlockqueue("I_SQE");

		// Regular events
		engine.Lockqueue("I_EQE");
		PendingEvent_c[] pes = PendingEvent_c.getManyI_EVIsOnR2944(engine);
		for (int i = 0; i < pes.length; i++) {
			result.add(new BPStackFrame((BPDebugTarget) getDebugTarget(),
					getLaunch(), pes[i], this));
		}
		engine.Unlockqueue("I_EQE");

		// Timer events
		Timer_c[] timers = Timer_c.getManyI_TIMsOnR2940(PendingEvent_c
				.getManyI_EVIsOnR2964(engine));
		PendingEvent_c[] tes = PendingEvent_c.getManyI_EVIsOnR2940(timers);
		for (int i = 0; i < tes.length; i++) {
			result.add(new BPStackFrame((BPDebugTarget) getDebugTarget(),
					getLaunch(), tes[i], this));
		}
		BPStackFrame[] bpsfs = new BPStackFrame[result.size()];
		int i = 0;
		Iterator it = result.iterator();
		while (it.hasNext()) {
			bpsfs[i++] = (BPStackFrame) it.next();
		}
		// Always return the same instance for the top stack frame,
		// else focus is lost in the debug tree.
		if (bpsfs.length > 0) {
			if (frame != null && frame.equals(bpsfs[0])) {
				frame.setStackFrame(bpsfs[0].getStackFrame());
				bpsfs[0] = frame;
			} else {
				frame = bpsfs[0];
			}
		} else {
			frame = null;
		}
		return bpsfs;
	}

	public boolean hasStackFrames() throws DebugException {
		return isSuspended();
	}

	public int getPriority() throws DebugException {
		// TODO Auto-generated method stub
		return 0;
	}

	public IStackFrame getTopStackFrame() throws DebugException {
		// Refresh the stack frame cache
		getStackFrames();
		return frame;
	}

	public IBreakpoint[] getBreakpoints() {
		// TODO Auto-generated method stub
		return new IBreakpoint[0];
	}

	public boolean canResume() {
		return !isTerminated() && isSuspended();
	}

	public boolean canSuspend() {
		return !isTerminated() && !isSuspended();
	}

	public boolean isSuspended() {
		Stack_c stack = Stack_c.getOneI_STACKOnR2930(engine);
		if (stack != null) {
			return stack.getRunstate() == Runstatetype_c.Suspended;
		} else {
			return false;
		}
	}

	public boolean isBlocked() {
		StackFrame_c frame = StackFrame_c.getOneI_STFOnR2929(Stack_c.getOneI_STACKOnR2930(engine));
		if (frame != null) {
			return StackFrame_c.getOneI_STFOnR2965BlockedBy(frame) != null;
		}
		return false;
	}
	public boolean isWaiting() {
		PendingEvent_c pEvt = PendingEvent_c.getOneI_EVIOnR2964(engine);
		StackFrame_c frame = StackFrame_c.getOneI_STFOnR2929(Stack_c.getOneI_STACKOnR2930(engine));
		return frame == null && pEvt == null;
	}
	
	public void resume() throws DebugException {
		notifyTargetOfResumption();
		boolean success = waitForTargetsToStart();
		if (success) {
		internalResume(true);
		TIM.resumeTime();
	}
		else {
		  DebugUIPlugin.logErrorMessage("Resume Failure. Targets did not start");
		}
	}

	public void internalResume(boolean selfIsStarting) {
		Stack_c stack = Stack_c.getOneI_STACKOnR2930(engine);
		UUID sfID = Gd_c.Null_unique_id();
		if (frame != null) {
			StackFrame_c sf = frame.getStackFrame();
			if (sf != null) {
				sfID = sf.getStack_frame_id();
			}
		}
		StackFrame_c sf = StackFrame_c.getOneI_STFOnR2929(stack);
		StackFrame_c blockedBy = StackFrame_c.getOneI_STFOnR2965BlockedBy(sf);
		Stack_c stackForFrame = Stack_c.getOneI_STACKOnR2943(sf);
		try {
			ModelRoot.disableChangeNotification();
		if (blockedBy == null && stackForFrame != null) {
		  StackFrame_c topStackFrame = StackFrame_c.getOneI_STFOnR2929(stack);
	    if (frame != null) {
	    	if (!selfIsStarting) {
	      // Wait for any running inter-component calls to complete
	      StackFrame_c blockedFrame = StackFrame_c.getOneI_STFOnR2965Blocks(topStackFrame);
	      long startTime = System.currentTimeMillis();
		    while (topStackFrame != null && blockedFrame != null &&
		                    System.currentTimeMillis() < startTime + getDebugTimeout()) {
		      PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
		        public void run() {
		          PlatformUI.getWorkbench().getDisplay().readAndDispatch();
		        }
		      });
		      topStackFrame = StackFrame_c.getOneI_STFOnR2929(stack);
	          blockedFrame = StackFrame_c.getOneI_STFOnR2965Blocks(topStackFrame);
	          ComponentInstance_c blockedComp = ComponentInstance_c.
	             getOneI_EXEOnR2930(Stack_c.getOneI_STACKOnR2929(blockedFrame));
	          if (blockedComp != null && blockedComp.getRunning()) {
        		  // if the component we're blocking is running, we can
        		  // continue.
        		  break;
	          }
		    }
		    if (topStackFrame != null && blockedFrame != null) {
		      ComponentInstance_c blockedComp = ComponentInstance_c.
		         getOneI_EXEOnR2930(Stack_c.getOneI_STACKOnR2929(blockedFrame));
		      if (blockedComp != null && !blockedComp.getRunning()) {
		        // the timeout must have happened, log a resume error.
		        DebugUIPlugin.logErrorMessage("Resume Failure. Component did not become ready. Try adjusting the start up wait time in Window > Preferences > BridgePoint > Verifier");
		      }
		    }
	      }
	    }
	    else {
	    	if (sfID.equals(Gd_c.Null_unique_id()) && sf != null) {
	    		sfID = sf.getStack_frame_id();
	    	}
	    }
	    
		  stack.Stepover(sfID); // Step past breakpoint
		}
		stack.setRunstate(Runstatetype_c.Running);
		}
		finally {
			ModelRoot.enableChangeNotification();
		}
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
					;
			}
		});
		if (blockedBy == null) {
		  if (engine != null) {
			engine.Notify();
		  }
		  IDebugTarget dbgTgt = getDebugTarget();
		  if (dbgTgt instanceof BPDebugTarget) {
			((BPDebugTarget)dbgTgt).Notify();
		  }
		}
		fireResumeEvent(DebugEvent.RESUME);
		fireChangeEvent(DebugEvent.CLIENT_REQUEST);
	}

	public void suspend() throws DebugException {
		notifyTargetOfSuspension();
		internalSuspend();
	}

	public void internalSuspend() {
	  if (engine != null) {
	    synchronized(engine) {
          Stack_c [] stacks = Stack_c.getManyI_STACKsOnR2930(engine);
          for (int j=0; j < stacks.length; j++) {
		    if (stacks[j] != null) {
		      stacks[j].setRunstate(Runstatetype_c.Suspended);
		    }
          }
		  engine.notify();
	    }
	  }
	  IDebugTarget dbgTgt = getDebugTarget();
	  if (dbgTgt instanceof BPDebugTarget) {
		((BPDebugTarget)dbgTgt).Notify();
	  }
	  fireSuspendEvent(DebugEvent.SUSPEND);
	  fireChangeEvent(DebugEvent.CLIENT_REQUEST);
	}
	public boolean canStepInto() {

		return false;
	}

	public boolean canStepOver() {
		return !isTerminated() && isSuspended() && !isBlocked() && !isWaiting();
	}

	public boolean canStepReturn() {
		return false;
	}

	public boolean isStepping() {
		return isSuspended();
	}

	public void stepInto() throws DebugException {
		frame = null;
		Stack_c stack = Stack_c.getOneI_STACKOnR2930(engine);
		if (stack != null) {
			ModelRoot modelRoot = stack.getModelRoot();
			ModelRoot.disableChangeNotification();
			try {
				fireResumeEvent(DebugEvent.STEP_INTO);
				stack.Stepin();
			} finally {
				fireSuspendEvent(DebugEvent.STEP_END);
				ModelRoot.enableChangeNotification();
			}
			ModelChangedEvent mce = new ModelChangedEvent(modelRoot,
					Modeleventnotification_c.MODEL_EXECUTION_STOPPED, engine);
			Ooaofooa.getDefaultInstance().fireModelEvent(mce);
		}
	}

	public void stepOver() throws DebugException {
		IStackFrame sf = getTopStackFrame();
		frame = null;
		Stack_c stack = Stack_c.getOneI_STACKOnR2930(engine);
		if (sf != null && stack != null) {
			ModelRoot modelRoot = stack.getModelRoot();
			ModelRoot.disableChangeNotification();
			try {
				fireResumeEvent(DebugEvent.STEP_OVER);
				sf.stepOver();
			} finally {
				fireSuspendEvent(DebugEvent.STEP_END);
				ModelRoot.enableChangeNotification();
			}
			ModelChangedEvent mce = new ModelChangedEvent(modelRoot,
					Modeleventnotification_c.MODEL_EXECUTION_STOPPED, engine);
			Ooaofooa.getDefaultInstance().fireModelEvent(mce);
		}
	}

	public void stepReturn() throws DebugException {
		// TODO Implement step out
	}

	public boolean canTerminate() {
		return !isTerminated();
	}

	public boolean isTerminated() {
		return engine == null || !engine.getRunning();
	}

	public void terminate() throws DebugException {
      try {
		suspend();
		int retries = 30;
		while (!isSuspended() && retries > 0 && engine != null) {
		  try {
			Thread.sleep(50);
			retries--;
			if (engine != null && Thread.holdsLock(engine)) {
			  engine.notify();
			}
		  }
		  catch (InterruptedException ie) {
			  // Do nothing
		  }
		}
		resetClassLoader();
		stop();
		fireTerminateEvent();
		debugTarget.remove(this);
		IDebugTarget dbgTgt = getDebugTarget();
		if (dbgTgt instanceof BPDebugTarget) {
		  ((BPDebugTarget)dbgTgt).Notify();
		}
		Vm_c.removeStack(runner);
      }
      finally {
		// Create a transaction so that viewers will update one more time
		BPThread.refreshVerifierViews();
      }
	}
	public void resetClassLoader() 
	{
        ComponentInstance_c ci = this.getEngine();  		
		Component_c comp = Component_c.getOneC_COnR2955(ci);
		if (comp == null) {
			comp = Component_c.getOneC_COnR4201(ComponentReference_c
					.getOneCL_ICOnR2963(ci));
		}
		if (comp != null) {
	        SystemModel_c system = (SystemModel_c)Ooaofooa.
	           getDefaultInstance().getInstanceList(SystemModel_c.class).
	                                              getGlobal(comp.Getsystemid());
		    Vm_c.resetClassLoader(system);
		}
	}
	public void stop() {
		ModelRoot root = null;
		ComponentInstance_c engineTemp = null;
		try {
			if (engine != null) {
	    		// Call the user realized class cleanup method
	    		Object realizedBy = engine.getRealizedby();
	    		if (realizedBy != null) {
	    			try {
	    				Method term = realizedBy.getClass().getMethod("terminate",
	    						                                      new Class[0]);
	    				term.invoke(realizedBy, new Object[0]);
	    			}
	    			catch (NoSuchMethodException nsm) {
	    				// Do nothing, the user code doesn't have any cleanup to do
	    			} catch (IllegalArgumentException e) {
	    				LOG.LogInfo("Illegal argument exception during shutown of realized component. " + e.getLocalizedMessage());
						e.printStackTrace();
					} catch (IllegalAccessException e) {
	    				LOG.LogInfo("Illegal access exception during shutown of realized component. " + e.getLocalizedMessage());
						e.printStackTrace();
					} catch (InvocationTargetException e) {
	    				LOG.LogInfo("Invocation target exception during shutown of realized component. " + e.getLocalizedMessage());
						e.printStackTrace();
					}
	    			engine.setRealizedby(null);
	    		}
				engineTemp =engine;
				root = engine.getModelRoot();
				engine.Stop();
				if (engine != null) {
					engine.Dispose();
					if (engine != null) {
						engine.Notify();
					}
				}
			}
			engine = null;
		} finally {
			instances.remove(this);
			if (instances.isEmpty()) {
				TIM.terminate(engineTemp);
			}
		}
	}

	public String getName() {
		String stateLabel = "";
		if (!isTerminated()) {
            if (engine != null && engine.getRealizedby() != null) {
            	stateLabel = " (Realized, ";
            }
            else {
            	stateLabel = " (";
            }
			if (isSuspended()) {
				Stack_c stack = Stack_c.getOneI_STACKOnR2930(engine);
				if (stack.getSuspendreason().equals("")) {
					stateLabel += "Suspended)";
				} else {
					stateLabel += "Suspended " + stack.getSuspendreason()
							+ ")";
				}
			} else {
				stateLabel += "Running)";
			}
		}
		Package_c pkg = Package_c.getOneEP_PKGOnR2970(engine);
		Domain_c domain = Domain_c.getOneS_DOMOnR2948(engine);
		if (pkg != null) {
			return pkg.getName() + stateLabel;
		}
		else if (domain != null) {
			return domain.getName() + stateLabel;
		} else {
			Component_c component = Component_c.getOneC_COnR2955(engine);
			if (component != null) {
				int numEngines = ComponentInstance_c
						.getManyI_EXEsOnR2955(component).length;
				if (numEngines == 1) {
					return component.getLabel() + stateLabel;
				} else {
					return component.getLabel() + " - "
							+ engine.Getenginenumber() + stateLabel;
				}
			} else {
				ComponentReference_c icomponent = ComponentReference_c
						.getOneCL_ICOnR2963(engine);
				if (icomponent != null) {
					int numEngines = ComponentInstance_c
							.getManyI_EXEsOnR2963(icomponent).length;
					int numIComps = ComponentReference_c
							.getManyCL_ICsOnR4201(Component_c
									.getOneC_COnR4201(icomponent)).length;
					String instanceString = "";
					// If there are multiple instances of a CL_IC formalized to
					// the
					// same component differentiate them by appending a number
					// that
					// identifies the particular CL_IC. We do the same for I_EXE
					// instances. However since the I_EXE.getEngineumber is not
					// unique across different CL_IC instances, we add the
					// CL_IC.Getimportedcomponentnumber() as a way to
					// differentiate
					// it in the SE and debug views.
					if (numIComps > 1) {
						instanceString = " - " + engine.Getenginenumber() + "."
								+ icomponent.Getimportedcomponentnumber();
					} else if (numEngines > 1) {
						instanceString = " - " + engine.Getenginenumber();
					}
					return icomponent.getName() + instanceString + stateLabel;
				}
			}
			return "Error: No element to execute.";
		}
	}

	public ComponentInstance_c getEngine() {
		return engine;
	}

	public void resetFrame() {
		frame = null;
	}

	public static BPThread getThreadExecuting(ComponentInstance_c eng) {
		Iterator it = instances.iterator();
		BPThread thread = null;
		while (it.hasNext()) {
			thread = (BPThread) it.next();
			if (thread.getEngine() == eng) {
				break;
			}
		}
		return thread;
	}
	
	private void notifyTargetOfSuspension() throws DebugException {
		IDebugTarget dbgTgt = getDebugTarget();
		if (dbgTgt instanceof BPDebugTarget) {
			((BPDebugTarget)dbgTgt).threadIsSuspending(this);
		}
	}

	private void notifyTargetOfResumption() throws DebugException {
		IDebugTarget dbgTgt = getDebugTarget();
		if (dbgTgt instanceof BPDebugTarget) {
			((BPDebugTarget)dbgTgt).threadIsResuming(this);
		}
	}
	
	 private boolean waitForTargetsToStart()  throws DebugException {
	    IDebugTarget dbgTgt = getDebugTarget();
	    if (dbgTgt instanceof BPDebugTarget) {
	      return ((BPDebugTarget)dbgTgt).threadIsWaitingToResume(this);
	    }
	    else {
	      return false;
	    }
	  }

	/**
	 * @param stack
	 * @return
	 */
	public static boolean qdFramesReady(Stack_c stack) {
		boolean qdFramesReady = false;
		StackFrame_c [] qdFrames = StackFrame_c.getManyI_STFsOnR2966(stack);
		for (int i=0; i < qdFrames.length; i++) {
			if (qdFrames[i].getReadyforinterrupt()== true) {
				qdFramesReady = true;
				break;
			}
		}
		return qdFramesReady;
	}

	/**
	 * @param stack
	 * @return
	 */
	public static boolean qdResultsReady(Stack_c stack) {
		boolean qdResultsReady = false;
		StackFrame_c [] qdResults = StackFrame_c.getManyI_STFsOnR2967(stack);
		StackFrame_c topFrame = StackFrame_c.getOneI_STFOnR2929(stack);
		for (int i=0; i < qdResults.length; i++) {
			if (qdResults[i].getReadyforinterrupt()== true) {
				StackFrame_c blockedFrame = StackFrame_c.getOneI_STFOnR2965Blocks(qdResults[i]);
				if (topFrame == blockedFrame) {
				  qdResultsReady = true;
				  break;
				}
			}
		}
		return qdResultsReady;
	}

}
