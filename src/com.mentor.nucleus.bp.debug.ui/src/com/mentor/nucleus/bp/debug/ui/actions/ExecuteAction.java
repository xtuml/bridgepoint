package com.mentor.nucleus.bp.debug.ui.actions;

//=====================================================================
//
// File:      $RCSfile: ExecuteAction.java,v $
// Version:   $Revision: 1.23 $
// Modified:  $Date: 2013/05/10 04:25:30 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================
import java.util.UUID;

import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.Body_c;
import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ExecutableProperty_c;
import com.mentor.nucleus.bp.core.FunctionBody_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.Ifdirectiontype_c;
import com.mentor.nucleus.bp.core.InterfaceOperation_c;
import com.mentor.nucleus.bp.core.InterfaceSignal_c;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.OperationBody_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.PendingEvent_c;
import com.mentor.nucleus.bp.core.ProvidedExecutableProperty_c;
import com.mentor.nucleus.bp.core.ProvidedOperationBody_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.ProvidedSignalBody_c;
import com.mentor.nucleus.bp.core.ProvidedSignal_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.RequiredExecutableProperty_c;
import com.mentor.nucleus.bp.core.RequiredOperationBody_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.RequiredSignalBody_c;
import com.mentor.nucleus.bp.core.RequiredSignal_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.RuntimeChannel_c;
import com.mentor.nucleus.bp.core.Satisfaction_c;
import com.mentor.nucleus.bp.core.SemEvent_c;
import com.mentor.nucleus.bp.core.SignalEvent_c;
import com.mentor.nucleus.bp.core.Stack_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.Statement_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.debug.ui.launch.BPDebugUtils;
import com.mentor.nucleus.bp.debug.ui.model.BPDebugTarget;
import com.mentor.nucleus.bp.ui.session.SessionExplorerContentProvider;
import com.mentor.nucleus.bp.ui.session.views.SessionExplorerView;

public class ExecuteAction implements IViewActionDelegate {

	/**
	 * Constructor for MoveDownOnO_ATTRAction.
	 */
	public ExecuteAction() {
		super();
	}

	private NonRootModelElement selectedElement = null;
	private NonRootModelElement elementToExecute = null;
	private ComponentInstance_c engine = null;
	private static Thread runner = null;

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// The part is mainly needed to locate the selection provider, and
		// we cache our selection in core so no action is needed here.
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {

		Runnable r = new Runnable() {
			public void run() {
				execute();
			}
		};
		runner = new Thread(r, "Verifier user invocation");
		runner.setPriority(Thread.MIN_PRIORITY);
		runner.start();
	}

	/**
	 * Return running thread, used for unit testing
	 */
	public static Thread getRunner() {
		return runner;
	}
	
	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	  try {
      IStructuredSelection structuredSelection = (IStructuredSelection) Selection
          .getInstance().getSelection();
      boolean isEnabled = false;
      if (structuredSelection.getFirstElement() instanceof NonRootModelElement) {
    	  // during testing this can be true, we do not want to respond in
    	  // that case
    	  if(((NonRootModelElement) structuredSelection.getFirstElement()).isProxy()) {
    		  return;
    	  }
        setOALElement((NonRootModelElement) structuredSelection
            .getFirstElement());
        ComponentInstance_c exEng = getExecutionEngine();
        if (exEng != null && exEng.getRunning()) {
          isEnabled = true;
          engine = exEng;
        } else {
          engine = null;
        }
      }
      if(action != null) {
    	  action.setEnabled(isEnabled);
      }
    } catch (Exception e) {
      CorePlugin.logError(
          "Exception encountered changing selection in Execute action.", e);
    }
	}

	private ComponentInstance_c getExecutionEngine() {
      IWorkbenchPart activePart = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getActivePart();
      if(activePart instanceof SessionExplorerView) {
		SessionExplorerView sessionExplorer = (SessionExplorerView) activePart;
		if(sessionExplorer.getTreeViewer().getTree().getSelection().length > 0) {
			ComponentInstance_c engine = sessionExplorer.getTreeViewer()
								             .getExecutionEngineOfSelectedElement();
			if(engine != null) {
				return engine;
			}
		}
	  }
      // if we get here, try the content provider
      // as it could be an initializer being executed
      // which does not require a selection or the tree
      // to even show
	  SessionExplorerContentProvider provider = new SessionExplorerContentProvider();
	  Object parent = provider.getParent(selectedElement);
	  while(parent != null && !(parent instanceof ComponentInstance_c)) {
		parent = provider.getParent(parent);
	  }
	  if(parent instanceof ComponentInstance_c) {
		return (ComponentInstance_c) parent;
	  }
	  return null;
	}

	private void execute() {
		try {
			ModelRoot modelRoot = elementToExecute.getModelRoot();
			boolean launchSuccessful = false;
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				public void run() {
					// execute the runnable
					  engine = getExecutionEngine();
				}
			});
			Ooaofooa.beginVerifierExecution(engine.getExecution_engine_id());
			try {
				Body_c bdy = getBody();
				Statement_c smt = Statement_c.getOneACT_SMTOnR602(Block_c
						.getOneACT_BLKOnR666(bdy));
				if (elementToExecute instanceof SignalEvent_c) {
					// if a signal event, then just add it
					// to the event queue
					PendingEvent_c pendingEvt = new PendingEvent_c(modelRoot);
					pendingEvt.relateAcrossR2964To(engine);

					StateMachineEvent_c evt = StateMachineEvent_c
							.getOneSM_EVTOnR525(SemEvent_c
									.getOneSM_SEVTOnR526((SignalEvent_c) elementToExecute));
					evt.relateAcrossR2906To(pendingEvt, false);
					// no event data, no need to initalize paramters until
					// the event data is supported
					pendingEvt.Fire();

					// wake the Component Instance up
					engine.Notify();
					launchSuccessful = true;
					ModelChangedEvent mce = new ModelChangedEvent(
							engine.getModelRoot(),
							Modeleventnotification_c.MODEL_EXECUTION_STOPPED,
							engine);
					Ooaofooa.getDefaultInstance().fireModelEvent(mce);
				} else {
					String calledElement = "interface message";
					if (elementToExecute instanceof Function_c) {
						calledElement = "function";
					} else if (elementToExecute instanceof Operation_c) {
						calledElement = "operation";
					}

					// log error that Component Instance was not found
					if (engine == null) {
						System.out.println("User invoked " + calledElement
								+ ": " + elementToExecute.getName()
								+ " failed, no Component Instance found.");
					}

					// log that the user invoked something
					CorePlugin.out.println("User invoked " + calledElement
							+ ": " + elementToExecute.getName());

					if (elementToExecute instanceof RequiredSignal_c
							|| elementToExecute instanceof ProvidedSignal_c) {
						// for signals that are not assigned to transitions
						// we need to queue a stack frame at the end of the
						// destination stack
						if (smt != null || engine.getRealizedby() != null) {
							UUID sfid = bdy
									.Createstackframe(true, Gd_c
											.Null_unique_id(), Stack_c
											.getOneI_STACKOnR2930(engine)
											.getStack_id());
							bdy.Startstackframeformessage(sfid);
							launchSuccessful = true;
						} else {
							CorePlugin.out
									.println("User invoked "
											+ calledElement
											+ ": "
											+ selectedElement.getName()
											+ " failed, no statements to execute. Check for OAL problems.");
						}
					} else {
						Stack_c stack = Stack_c.getOneI_STACKOnR2930(engine);
						if (stack != null) {
							if (bdy != null
									&& engine != null
									&& (smt != null || engine.getRealizedby() != null)) {
								UUID sfid = bdy.Createstackframe(true,
										Gd_c.Null_unique_id(),
										stack.getStack_id());
								bdy.Startstackframeformessage(sfid);
								launchSuccessful = true;
							} else {
								CorePlugin.out
										.println("User invoked "
												+ calledElement
												+ ": "
												+ selectedElement.getName()
												+ " failed, no statements to execute. Check for OAL problems.");
							}
						} else {
							CorePlugin.out
									.println("User invoked "
											+ calledElement
											+ ": "
											+ selectedElement.getName()
											+ " failed, internal error. No stack found for execution.");
						}

					}
				}
			} finally {
				Ooaofooa.endVerifierExecution(engine.getExecution_engine_id());
				if (launchSuccessful) {
					IDebugTarget target = BPDebugUtils
							.getTargetForEngine(engine);
					if (target instanceof BPDebugTarget) {
						((BPDebugTarget) target).Notify();
					}
				}
			}
		} catch (Exception e) {
			CorePlugin.logError("Exception encountered during execute action",
					e);
		}
	}

	public void setOALElement(NonRootModelElement element) {
		selectedElement = element;
		elementToExecute = selectedElement;
		ComponentInstance_c exe = getExecutionEngine();
		boolean isWired = false;
		if (selectedElement instanceof RequiredOperation_c) {
			final RequiredOperation_c requiredOp =
				                          (RequiredOperation_c) selectedElement;
			Satisfaction_c[] sats = Satisfaction_c.getManyC_SFsOnR4002(
					                      Requirement_c.getOneC_ROnR4500(
				RequiredExecutableProperty_c.getOneSPR_REPOnR4502(requiredOp)));
			RuntimeChannel_c [] channels = RuntimeChannel_c.getManyI_RCHsOnR2969(sats);
			for (int i=0; i < channels.length; i++) {
				if (ComponentInstance_c.getOneI_EXEOnR2968IsInterfaceProviderTo(channels[i]).equals(exe)) {
					isWired = true;
				}
			}
			if (!isWired) {
				// for an operation, we always just execute
				// its OAL
				return;
			} else {
				// find the provided operation at the other
				// end
				InterfaceOperation_c operation = InterfaceOperation_c
						.getOneC_IOOnR4004(ExecutableProperty_c
								.getOneC_EPOnR4500(RequiredExecutableProperty_c
										.getOneSPR_REPOnR4502(requiredOp)));
				if (operation.getDirection() == Ifdirectiontype_c.ServerClient) {
					// execute the OAL in this required operation
					return;
				} else {
					// otherwise execute the OAL in the provided operation
					Satisfaction_c satisfaction = getSatisfactionForRequiredOperation(
							requiredOp, false);
					Provision_c provision = Provision_c
							.getOneC_POnR4002(satisfaction);
					ProvidedOperation_c providedOperation = ProvidedOperation_c
							.getOneSPR_POOnR4503(ProvidedExecutableProperty_c
									.getManySPR_PEPsOnR4501(provision),
									new ClassQueryInterface_c() {

										public boolean evaluate(Object candidate) {
											return ((ProvidedOperation_c) candidate)
													.getName().equals(
															requiredOp
																	.getName());
										}

									});
					elementToExecute = providedOperation;
					return;
				}
			}
		} else if(selectedElement instanceof RequiredSignal_c) {
			final RequiredSignal_c requiredSig = (RequiredSignal_c) selectedElement;
			Satisfaction_c[] sats = Satisfaction_c.getManyC_SFsOnR4002(
                                          Requirement_c.getOneC_ROnR4500(
               RequiredExecutableProperty_c.getOneSPR_REPOnR4502(requiredSig)));
			RuntimeChannel_c [] channels = RuntimeChannel_c.getManyI_RCHsOnR2969(sats);
            for (int i=0; i < channels.length; i++) {
              if (ComponentInstance_c.getOneI_EXEOnR2968IsInterfaceProviderTo(channels[i]).equals(exe)) {
                isWired = true;
              }
            }
			if(!isWired) {
				// for a required signal we need to execute
				// its OAL if Client to Server is the direction
				InterfaceSignal_c signal = InterfaceSignal_c
						.getOneC_ASOnR4004(ExecutableProperty_c
								.getOneC_EPOnR4500(RequiredExecutableProperty_c
										.getOneSPR_REPOnR4502((RequiredSignal_c) selectedElement)));
				if(signal.getDirection() == Ifdirectiontype_c.ClientServer) {
					return;
				} else {
					// otherwise we need to execute its OAL if
					// the signal is not assigned to a transition
					SignalEvent_c signalEvent = SignalEvent_c
							.getOneSM_SGEVTOnR529((RequiredSignal_c) selectedElement);
					if(signalEvent == null) {
						return;
					} else {
						// if assigned to a transition we need
						// to add the signal as an event to the
						// element's queue
						elementToExecute = signalEvent;
					}
					
				}
			} else {
				InterfaceSignal_c signal = InterfaceSignal_c
					.getOneC_ASOnR4004(ExecutableProperty_c
						.getOneC_EPOnR4500(RequiredExecutableProperty_c
								.getOneSPR_REPOnR4502((RequiredSignal_c) selectedElement)));
				if(signal.getDirection() == Ifdirectiontype_c.ServerClient) {
					// if server to client then execute this required signals
					// body, or add an event if assigned to a transition
					// otherwise we need to execute its OAL if
					// the signal is not assigned to a transition
					SignalEvent_c signalEvent = SignalEvent_c
							.getOneSM_SGEVTOnR529((RequiredSignal_c) selectedElement);
					if(signalEvent == null) {
						return;
					} else {
						// if assigned to a transition we need
						// to add the signal as an event to the
						// element's queue
						elementToExecute = signalEvent;
						return;
					}
				} else {
					// otherwise execute the OAL in the provided signal
					Satisfaction_c satisfaction = getSatisfactionForRequiredSignal(
							requiredSig, false);
					Provision_c provision = Provision_c
							.getOneC_POnR4002(satisfaction);
					ProvidedSignal_c providedSignal = ProvidedSignal_c
							.getOneSPR_PSOnR4503(ProvidedExecutableProperty_c
									.getManySPR_PEPsOnR4501(provision),
									new ClassQueryInterface_c() {

										public boolean evaluate(Object candidate) {
											return ((ProvidedSignal_c) candidate)
													.getName().equals(
															requiredSig
																	.getName());
										}

									});
					SignalEvent_c signalEvent = SignalEvent_c
					          .getOneSM_SGEVTOnR528(providedSignal);
					if(signalEvent == null) {
						elementToExecute = providedSignal;
					} else {
						elementToExecute = signalEvent;
					}
				}
				return;
			}
		} else if(selectedElement instanceof ProvidedOperation_c) {
			final ProvidedOperation_c providedOp = (ProvidedOperation_c) selectedElement;
			Satisfaction_c[] sats = Satisfaction_c.getManyC_SFsOnR4002(
                                            Provision_c.getOneC_POnR4501(
                ProvidedExecutableProperty_c.getOneSPR_PEPOnR4503(providedOp)));
			RuntimeChannel_c [] channels = RuntimeChannel_c.getManyI_RCHsOnR2969(sats);
            for (int i=0; i < channels.length; i++) {
              if (ComponentInstance_c.getOneI_EXEOnR2968IsInterfaceRequirerOf(channels[i]).equals(exe)) {
                isWired = true;
              }
            }
			if(!isWired) {
				// always execute the provided operation OAL
				return;
			} else {
				// if the direction of this operation is Client to Server than
				// execute the provided operation OAL
				InterfaceOperation_c operation = InterfaceOperation_c
					.getOneC_IOOnR4004(ExecutableProperty_c
						.getOneC_EPOnR4501(ProvidedExecutableProperty_c
								.getOneSPR_PEPOnR4503(providedOp)));
				if(operation.getDirection() == Ifdirectiontype_c.ClientServer) {
					return;
				} else {
					// otherwise execute the OAL in the Required operation
					Satisfaction_c satisfaction = getSatisfactionForProvidedOperation(
							providedOp, false);
					Requirement_c requirement = Requirement_c
							.getOneC_ROnR4002(satisfaction);
					RequiredOperation_c requiredOperation = RequiredOperation_c
							.getOneSPR_ROOnR4502(RequiredExecutableProperty_c
									.getManySPR_REPsOnR4500(requirement),
									new ClassQueryInterface_c() {

										public boolean evaluate(Object candidate) {
											return ((RequiredOperation_c) candidate)
													.getName().equals(
															providedOp
																	.getName());
										}

									});
					elementToExecute = requiredOperation;
				}
			}			
		} else if(selectedElement instanceof ProvidedSignal_c) {
			final ProvidedSignal_c providedSig = (ProvidedSignal_c) selectedElement;
			Satisfaction_c[] sats = Satisfaction_c.getManyC_SFsOnR4002(
                                            Provision_c.getOneC_POnR4501(
               ProvidedExecutableProperty_c.getOneSPR_PEPOnR4503(providedSig)));
			RuntimeChannel_c [] channels = RuntimeChannel_c.getManyI_RCHsOnR2969(sats);
            for (int i=0; i < channels.length; i++) {
              if (ComponentInstance_c.getOneI_EXEOnR2968IsInterfaceRequirerOf(channels[i]).equals(exe)) {
                isWired = true;
              }
            }
			if(!isWired) {
				// for a provided signal we need to execute
				// its OAL if Server To Client is the direction
				InterfaceSignal_c signal = InterfaceSignal_c
						.getOneC_ASOnR4004(ExecutableProperty_c
								.getOneC_EPOnR4501(ProvidedExecutableProperty_c
										.getOneSPR_PEPOnR4503((ProvidedSignal_c) selectedElement)));
				if(signal.getDirection() == Ifdirectiontype_c.ServerClient) {
					return;
				} else {
					// otherwise we need to execute its OAL if
					// the signal is not assigned to a transition
					SignalEvent_c signalEvent = SignalEvent_c
							.getOneSM_SGEVTOnR528((ProvidedSignal_c) selectedElement);
					if(signalEvent == null) {
						return;
					} else {
						// if assigned to a transition we need
						// to add the signal as an event to the
						// element's queue
						elementToExecute = signalEvent;
						return;
					}
					
				}
			} else {
				// we need to execute the provided signal OAL if the direction
				// is Client Server, unless the signal is assigned to a
				// transition
				InterfaceSignal_c signal = InterfaceSignal_c
						.getOneC_ASOnR4004(ExecutableProperty_c
								.getOneC_EPOnR4501(ProvidedExecutableProperty_c
										.getOneSPR_PEPOnR4503((ProvidedSignal_c) selectedElement)));
				if(signal.getDirection() == Ifdirectiontype_c.ClientServer) {
					SignalEvent_c signalEvent = SignalEvent_c
						.getOneSM_SGEVTOnR528((ProvidedSignal_c) selectedElement);
					if(signalEvent == null) {
						return;
					} else {
						elementToExecute = signalEvent;
						return;
					}
				} else {
					// otherwise get the required signal satisfied with
					Satisfaction_c satisfaction = getSatisfactionForProvidedSignal(providedSig, false);
					Requirement_c requirement = Requirement_c.getOneC_ROnR4002(satisfaction);
					RequiredSignal_c requiredSignal = RequiredSignal_c
							.getOneSPR_RSOnR4502(RequiredExecutableProperty_c
									.getManySPR_REPsOnR4500(requirement),
									new ClassQueryInterface_c() {

										public boolean evaluate(Object candidate) {
											return ((RequiredSignal_c) candidate)
													.getName().equals(
															providedSig
																	.getName());
										}

									});
					SignalEvent_c signalEvent = SignalEvent_c
							.getOneSM_SGEVTOnR529(requiredSignal);
					if (signalEvent == null) {
						elementToExecute = requiredSignal;
					} else {
						elementToExecute = signalEvent;
					}
				}
			}			
		}
	}

	private Satisfaction_c getSatisfactionForProvidedSignal(
			ProvidedSignal_c providedSig, boolean isImported) {
		Satisfaction_c[] satisfactions = Satisfaction_c
				.getManyC_SFsOnR4002(Provision_c
						.getManyC_PsOnR4501(ProvidedExecutableProperty_c
								.getManySPR_PEPsOnR4503(providedSig)));
		for (int i = 0; i < satisfactions.length; i++) {
			if (!satisfactions[i].Satisfieswithimportedref() && !isImported) {
				return satisfactions[i];
			}
		}
		return null;
	}

	private Satisfaction_c getSatisfactionForProvidedOperation(
			ProvidedOperation_c providedOp, boolean isImported) {
		Satisfaction_c[] satisfactions = Satisfaction_c
				.getManyC_SFsOnR4002(Provision_c
						.getManyC_PsOnR4501(ProvidedExecutableProperty_c
								.getManySPR_PEPsOnR4503(providedOp)));
		for (int i = 0; i < satisfactions.length; i++) {
			if (!satisfactions[i].Satisfieswithimportedref() && !isImported) {
				return satisfactions[i];
			}
		}
		return null;
	}

	private Satisfaction_c getSatisfactionForRequiredSignal(
			RequiredSignal_c requiredSig, boolean isImported) {
		Satisfaction_c[] satisfactions = Satisfaction_c
				.getManyC_SFsOnR4002(Requirement_c
						.getManyC_RsOnR4500(RequiredExecutableProperty_c
								.getManySPR_REPsOnR4502(requiredSig)));
		for (int i = 0; i < satisfactions.length; i++) {
			if (!satisfactions[i].Satisfieswithimportedref() && !isImported) {
				return satisfactions[i];
			}
		}
		return null;
	}

	private Satisfaction_c getSatisfactionForRequiredOperation(
			RequiredOperation_c requiredOp, boolean isImported) {
		Satisfaction_c[] satisfactions = Satisfaction_c
				.getManyC_SFsOnR4002(Requirement_c
						.getManyC_RsOnR4500(RequiredExecutableProperty_c
								.getManySPR_REPsOnR4502(requiredOp)));
		for(int i = 0; i < satisfactions.length; i++) {
			if(!satisfactions[i].Satisfieswithimportedref() && !isImported) {
				return satisfactions[i];
			}
		}
		return null;
	}

	private Body_c getBody() {
		Body_c bdy = null;
		if(selectedElement instanceof Function_c) {
			Function_c fn = (Function_c) selectedElement;
			bdy = Body_c.getOneACT_ACTOnR698(FunctionBody_c
					.getOneACT_FNBOnR695(fn));
		} else if(selectedElement instanceof Operation_c) {
			Operation_c op = (Operation_c) selectedElement;
			bdy = Body_c.getOneACT_ACTOnR698(OperationBody_c.getOneACT_OPBOnR696(op));
		} else if(selectedElement instanceof ProvidedOperation_c) {
			ProvidedOperation_c proOp = (ProvidedOperation_c) selectedElement;
			bdy = Body_c.getOneACT_ACTOnR698(ProvidedOperationBody_c.getOneACT_POBOnR687(proOp));
		} else if(selectedElement instanceof ProvidedSignal_c) {
			ProvidedSignal_c proSig = (ProvidedSignal_c) selectedElement;
			bdy = Body_c.getOneACT_ACTOnR698(ProvidedSignalBody_c.getOneACT_PSBOnR686(proSig));
		} else if(selectedElement instanceof RequiredOperation_c) {
			RequiredOperation_c reqOp = (RequiredOperation_c) selectedElement;
			bdy = Body_c.getOneACT_ACTOnR698(RequiredOperationBody_c.getOneACT_ROBOnR685(reqOp));
		} else if(selectedElement instanceof RequiredSignal_c) {
			RequiredSignal_c reqSig = (RequiredSignal_c) selectedElement;
			bdy = Body_c.getOneACT_ACTOnR698(RequiredSignalBody_c.getOneACT_RSBOnR684(reqSig));
		}
		return bdy;
	}

	/**
	 * @Override
	 */
	public void init(IViewPart view) {
		// nothing to do
	}
}
