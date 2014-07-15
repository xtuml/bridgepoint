package com.mentor.nucleus.bp.debug.ui.model;

//====================================================================
//
// File:      $RCSfile: BPDebugTarget.java,v $
// Version:   $Revision: 1.37 $
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
import java.lang.Thread.State;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import lib.TIM;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ComponentInstanceContainer_c;
import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Delegation_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.EventQueueEntry_c;
import com.mentor.nucleus.bp.core.ImportedProvisionInSatisfaction_c;
import com.mentor.nucleus.bp.core.ImportedProvision_c;
import com.mentor.nucleus.bp.core.ImportedReference_c;
import com.mentor.nucleus.bp.core.ImportedRequirement_c;
import com.mentor.nucleus.bp.core.InterfaceReferenceInDelegation_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.PendingEvent_c;
import com.mentor.nucleus.bp.core.PortReference_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.Runstatetype_c;
import com.mentor.nucleus.bp.core.RuntimeChannel_c;
import com.mentor.nucleus.bp.core.Satisfaction_c;
import com.mentor.nucleus.bp.core.SelfQueueEntry_c;
import com.mentor.nucleus.bp.core.StackFrame_c;
import com.mentor.nucleus.bp.core.Stack_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.Timer_c;
import com.mentor.nucleus.bp.core.User_c;
import com.mentor.nucleus.bp.core.Vm_c;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.util.BPClassLoader;
import com.mentor.nucleus.bp.core.util.OoaofooaUtil;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.debug.java.access.VerifierInvocationHandler;
import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;
import com.mentor.nucleus.bp.debug.ui.IBPDebugUIPluginConstants;
import com.mentor.nucleus.bp.debug.ui.actions.ExecuteAction;
import com.mentor.nucleus.bp.debug.ui.launch.BPDebugUtils;
import com.mentor.nucleus.bp.debug.ui.launch.VerifierLaunchConfiguration;
import com.mentor.nucleus.bp.debug.ui.launch.VerifierLaunchContentProvider;
import com.mentor.nucleus.bp.ui.text.activity.AllActivityModifier;

public class BPDebugTarget extends BPDebugElement implements IDebugTarget {

	private BPProcess process = null;
	private String projectName = null;

	private SystemModel_c system = null;

	private ArrayList<BPThread> threads = new ArrayList<BPThread>();
	private boolean isDeterministic = true;
	private int executionTimeout = 0;
	private BPExecutionTimer executionTimer;
	
	private static ArrayList<BPDebugTarget> targets = 
		                                         new ArrayList<BPDebugTarget>();

	public BPDebugTarget(ILaunch launch, ILaunchConfiguration config,
			String name, IProgressMonitor monitor) throws CoreException {
		this.debugTarget = this;
		this.launch = launch;
		this.name = name;
		init(config, monitor);
		targets.add(this);
	}

	public BPDebugTarget(ILaunch launch, String projectName,
			IProgressMonitor monitor) throws CoreException {
		this.debugTarget = this;
		this.launch = launch;
		this.projectName = projectName;
		init(launch.getLaunchConfiguration(), monitor);
		targets.add(this);
	}

	public boolean isDeterministic() {
		return isDeterministic;
	}

	private void init(ILaunchConfiguration config, IProgressMonitor monitor) {
		try {
			if (checkProject(projectName)) {
				setSystem();
				if (checkModels(config)) {
					isDeterministic = config.getAttribute(VerifierLaunchConfiguration.ATTR_ENABLEDETERMINISM, false);
					boolean useSimTime = config.getAttribute(VerifierLaunchConfiguration.ATTR_ENABLESIMTIME, false);
					TIM.setSIM_TIME(useSimTime || isDeterministic);
					setupSelectedModels(config);
					if (getLaunch().getProcesses() == null
							|| getLaunch().getProcesses().length == 0) {
						process = new BPProcess(launch, this, null);
						launch.addProcess(process);
					} else {
						process = (BPProcess) getLaunch().getProcesses()[0];
					}
					CorePlugin.loggingEnabled = config.getAttribute(
							VerifierLaunchConfiguration.ATTR_LOGACTIVITY, false);
					executionTimeout = config.getAttribute(VerifierLaunchConfiguration.ATTR_EXECUTIONTIMEOUT, 0);
					
					if (executionTimeout > 0) {
						// Start the execution timer	
						executionTimer = new BPExecutionTimer(this, executionTimeout*1000);
					}
					
					/**
					 * We only start the separate timer thread when
					 * we are not running in deterministic mode.
					 */
					if (!isDeterministic()) {
						TIM.init(useSimTime);
					}
					for (BPThread thread: threads.toArray(new BPThread[0])) {
						ComponentInstance_c target = thread.getEngine();
						Component_c comp = Component_c.getOneC_COnR2955(target);
						if (comp == null) {
							comp = Component_c.getOneC_COnR4201(
							   ComponentReference_c.getOneCL_ICOnR2963(target));
						}
						if (comp != null) {
					        SystemModel_c system = (SystemModel_c)Ooaofooa.
					           getDefaultInstance().getInstanceList(SystemModel_c.class).
					                                              getGlobal(comp.Getsystemid());
							String[] paths = comp.getRealized_class_path().split(";");
							for(String path: paths) {
							  if (!path.equals("")) {
								Vm_c.Adduserclasspath(system, path);
							  }
							}
						}
					}
					
					User_c.Loginfo("Simulation started.");
					// If an initializer was selected, now is the time to execute it
					Vector<NonRootModelElement> initializers = VerifierLaunchConfiguration.getInitializers(config);
					Iterator<NonRootModelElement> iter = initializers.iterator();
					while (iter.hasNext()) {
						final NonRootModelElement initializer = iter.next();
						// do this on the UI Thread
						PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
							
							@Override
							public void run() {
								User_c.Loginfo("Executing the specified initializer.");
								waitForExecutionToStart();
								
								// Execute this initializer
								ExecuteAction action = new ExecuteAction();
								action.setOALElement(initializer);
								action.run(null);					
							}
						});
					}
				}
			}

		} catch (CoreException e) {
			BPDebugUIPlugin
					.logError(
							"Launch Error: Unable to get launch attribute value.",
							e);
		}
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(
				this);
	}
	
	private void waitForExecutionToStart() {
		boolean allRunning = false;
		while (!allRunning) {
			allRunning = true;
			for (BPThread thread: threads.toArray(new BPThread[0])) {
				ComponentInstance_c engine = thread.getEngine();
				Stack_c stack = Stack_c.getOneI_STACKOnR2930(engine);
				if (stack.getRunstate() != Runstatetype_c.Running) {
					allRunning = false;
					break;
				}
			}
			while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())  { };				
		}
	}
	
	
	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		if (breakpoint.getModelIdentifier().equals(
				IBPDebugUIPluginConstants.PLUGIN_ID)) {
			IMarker marker = breakpoint.getMarker();
			if (marker != null) {
				return marker.getResource().getFullPath().segment(0).equals(
						projectName);
			}
		}
		return false;
	}

	private boolean checkProject(String projectName) throws CoreException {
		// assert that all projects are opened
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				projectName);
		if (!project.isOpen()) {
			Shell shell = (PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow() == null) ? null
					: PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getShell();

			UIUtil
					.showMessageDialoginLaunch(
							shell,
							"Verifier Launcher",
							"Launch error: The configured project is closed",
							UIUtil.BPMessageTypes.INFORMATION);			
			return false;
		}
		return true;
	}

	private boolean checkModels(ILaunchConfiguration config) {
		boolean result = true;
		List<NonRootModelElement> elementsBeingVerified = new ArrayList<NonRootModelElement>();
		IProcess[] processes = DebugPlugin.getDefault().getLaunchManager()
				.getProcesses();
		for (int i = 0; i < processes.length; i++) {
			if (processes[i] instanceof BPProcess) {
				if (processes[i].isTerminated())
					continue;
				if (processes[i].getLaunch() == getLaunch())
					continue;
				BPProcess process = (BPProcess) processes[i];
				ILaunchConfiguration configuration = process.getLaunch()
						.getLaunchConfiguration();
				try {
					NonRootModelElement[] runningElements = VerifierLaunchConfiguration
							.getElementsSelectedInConfiguration(configuration);
					for (int j = 0; j < runningElements.length; j++) {
						elementsBeingVerified.add(runningElements[j]);
					}
				} catch (CoreException e) {
					// no need to log an error, no
					// need to return false. For
					// some reason we could not find
					// the selected elements for this
					// configuration (one that is being
					// executed)
				}
			}
		}

		// for every element that is being verified, at least
		// through selection (includes imported components),
		// check to see if the current configuration will try
		// to verify
		String alreadyRunningModels = "";
		NonRootModelElement[] selectedElements = VerifierLaunchConfiguration
				.getElementsSelectedInConfigurationForProject(config,
						projectName);
		for (int i = 0; i < selectedElements.length; i++) {
			if (elementsBeingVerified.contains(selectedElements[i])) {
				if (result)
					alreadyRunningModels = selectedElements[i].getName();
				else
					alreadyRunningModels = alreadyRunningModels + ", "
							+ selectedElements[i].getName();
				result = false;
			}
		}

		if (!result) {
			IWorkbenchWindow win = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();

			UIUtil.showMessageDialoginLaunch((win != null) ? win
					.getShell() : null, "Verifier Launcher",
					"Launch error: The configured model(s) are already running: "
							+ alreadyRunningModels, UIUtil.BPMessageTypes.INFORMATION);
		}

		return result;
	}

	private void setSystem() {
		SystemModel_c[] systems = SystemModel_c.SystemModelInstances(Ooaofooa
				.getDefaultInstance());
		for (int i = 0; i < systems.length; i++) {
			if (systems[i].getName().equals(projectName)) {
				system = systems[i];
				break;
			}
		}
	}

	private void setupSelectedModels(ILaunchConfiguration configuration)
			throws CoreException {
		try {
			NonRootModelElement[] elements = VerifierLaunchConfiguration
					.getElementsSelectedInConfigurationForProject(
							configuration, projectName);
			if (elements != null && elements.length != 0) {
				// Optimization to clear instance data prior to the parse.  
				// The parse is much faster when there is no instance 
				// data to have to search through.  We do this here instead of in the
				// loop below because there may be a shared model root in the 
				// selected elements that we parsed individually below.
				for (int k = 0; k < elements.length; k++) {
					AllActivityModifier.disposeAllBodies(elements[k].getModelRoot());
				}

				for (int k = 0; k < elements.length; k++) {
					ComponentInstance_c exEng = null;
					AllActivityModifier aam = null;
					ModelRoot modelRoot = null;
					NonRootModelElement element = null;
					int multiplicity = VerifierLaunchConfiguration
							.getElementMultiplicityFromConfiguration(
									configuration, elements[k]);
					if (elements[k] instanceof Domain_c) {
						Domain_c dom = (Domain_c) elements[k];
						ComponentInstance_c[] exEngs = ComponentInstance_c
								.ComponentInstanceInstances(dom.getModelRoot());
						for (int i = 0; i < exEngs.length; i++) {
							exEngs[i].Dispose();
						}
						exEng = ComponentInstance_c.getOneI_EXEOnR2948(dom);
						if (exEng == null) {
							exEng = new ComponentInstance_c(dom.getModelRoot());
							// No need to handle realized here, domains not supported
							exEng.relateAcrossR2948To(dom);
						}
						aam = new AllActivityModifier(dom, null);
						modelRoot = dom.getModelRoot();
						element = dom;
					} else if (elements[k] instanceof Component_c) {
            Object parent = VerifierLaunchContentProvider.instance().
                                                         getParent(elements[k]);
            if (! (parent instanceof Component_c) &&
                                  ! (parent instanceof ComponentReference_c) ) {
						  for (int i = 0; i < multiplicity; i++) {
							  Component_c comp = (Component_c) elements[k];
							  exEng = new ComponentInstance_c(comp.getModelRoot());
							  if (comp.getIsrealized()) {
								  setUpForRealizedExecution(comp, exEng);
							  }
							  comp.relateAcrossR2955To(exEng);
							  aam = new AllActivityModifier(comp, null);
							  modelRoot = comp.getModelRoot();
							  element = comp;
							  launchElement(aam, modelRoot, exEng, null, element, elements, false, isDeterministic());
							}
						}
						// all elements processed
						continue;
					} else if (elements[k] instanceof ComponentReference_c) {
            Object parent = VerifierLaunchContentProvider.instance().
                                                         getParent(elements[k]);
            if (! (parent instanceof Component_c) &&
                                  ! (parent instanceof ComponentReference_c) ) {
						  for (int i = 0; i < multiplicity; i++) {
							  launchImportedComponent((ComponentReference_c) elements[k], null, elements, false, isDeterministic());
						  }
            }
						// all elements already launched
						continue;
					}
					launchElement(aam, modelRoot, exEng, null, element, elements, false, isDeterministic());
				}
				for (int k = 0; k < elements.length; k++) {
				  wireChannels(elements[k], null);
				}
				optimized.clear();
				for (int k = 0; k < elements.length; k++) {
					optimizeChannels(elements[k]);
				}
			}
		} finally {
			if (isDeterministic()) {
				launchSynchronousExecutionThread();
			}
			installDeferredBreakpoints();
			if (!((Ooaofooa) system.getModelRoot()).loadedOk()) {
				throw new CoreException(new Status(IStatus.OK, BPDebugUIPlugin
						.getDefault().getBundle().getSymbolicName(), 0,
						"Can not get element for verification.", null));
			}
		}
	}
	
	private void setUpForRealizedExecution(Component_c comp,
			ComponentInstance_c exEng) {
		SystemModel_c sys = OoaofooaUtil.getSystemForElement(PackageableElement_c.getOnePE_PEOnR8001(comp));
		if (sys != null) {
			BPClassLoader cl = Vm_c.getVmCl(sys.Get_ooa_id());
			if (cl != null) {
				String[] paths = comp.getRealized_class_path().split(";");
				for(String path: paths) {
				  if (!path.equals("")) {
					Vm_c.Adduserclasspath(sys, path);
				  }
				}
				try {
					String className = getClassNameForComponent(comp);
					Class<?> realizedTarget = cl.loadClass(className);
					Port_c[] ports = Port_c.getManyC_POsOnR4010(comp);
					Class<?>[] ctorArgs = new Class[ports.length];
					for (int i = 0; i < ports.length; i++) {
						String result = getClassNameForPort(ports[i]);
						ctorArgs[i] = cl.loadClass(result);
					}
					Constructor<?> ctor = realizedTarget
							.getConstructor(ctorArgs);
					Object[] ctorArgVals = new Object[ports.length];
					for (int i = 0; i < ctorArgVals.length; i++) {
						ctorArgVals[i] = Proxy.newProxyInstance(cl,
								new Class[] { ctorArgs[i] },
								new VerifierInvocationHandler(exEng, ports[i]));
					}
					try {
						exEng.setRealizedby(ctor.newInstance(ctorArgVals));
					} catch (IllegalArgumentException e) {
						CorePlugin.logError("Captured IllegalArguementException setting up realized execution for component " + comp.getName() + ".", e);
					} catch (InstantiationException e) {
                        CorePlugin.logError("Captured InstantiationException setting up realized execution for component " + comp.getName() + ".", e);
					} catch (IllegalAccessException e) {
                        CorePlugin.logError("Captured IllegalAccessException setting up realized execution for component " + comp.getName() + ".", e);
					} catch (InvocationTargetException e) {
						e.printStackTrace();
	                    CorePlugin.logError("Captured InvocationTargetException setting up realized execution for component " + comp.getName() + ".", e);
					}
				} catch (ClassNotFoundException cnf) {
				    CorePlugin.err.println("An error occurred while setting up for realized execution. Please run Audit Realized Bindings.  The internal error message is: " + cnf.getMessage());
				} catch (NoSuchMethodException nsme) {
                    CorePlugin.err.println("An error occurred while setting up for realized execution. Please run Audit Realized Bindings.  The internal error message is: " + nsme.getMessage());
				}
			} else {
			    Throwable e = new Throwable();
			    e.fillInStackTrace();
			    CorePlugin.logError("Could not find the expected classloader for " + sys.getName() + ".", e);
			}
		} else {
            Throwable e = new Throwable();
            e.fillInStackTrace();
            CorePlugin.logError("Could not find the expected system for the component " + comp.getName() + ". There appears to be a problem with the model data.", e);
		}
	}

	public static String getClassNameForPort(Port_c port) {
		Interface_c iface = Interface_c
				.getOneC_IOnR4012(InterfaceReference_c
						.getOneC_IROnR4016(port));
		Requirement_c req = Requirement_c
				.getOneC_ROnR4009(InterfaceReference_c
						.getOneC_IROnR4016(port));
		String ifaceName = "I"
				+ iface.getName().replaceAll(" ", "");
		if (req != null) {
			ifaceName += "ToProvider";
		} else {
			ifaceName += "FromProvider";
		}
		String ifacePath = getClassPathForInterface(iface);
		String result = ifacePath + "." +ifaceName;
		return result;
	}

	public static String getClassPathForInterface(Interface_c iface) {
		String path = iface.Getpath(false, "");
		String[] segments = path.split("::");
		String ifacePath = "";
		for (int j = 1; j < segments.length; j++) {
			if (j != 1) {
				ifacePath += ".";
			}
			ifacePath += segments[j].replaceAll(" ", "")
					.toLowerCase();
		}
		return ifacePath;
	}

	public static String getClassNameForComponent(Component_c comp) {
		String name = comp.Getpath(true, "");
		String [] segments = name.split("::");
		String className = "";
		if (segments.length > 1) {
		  for(int i=1; i < segments.length; i++) {
		    if (i != 1) {
		    	className += ".";
		    }
		    if (i < segments.length - 1) { // test class must retain capitalization
		    	segments[i] = segments[i].toLowerCase();
		    }
			className += segments[i].replaceAll(" ", "");
		  }
		}
		return className;
	}

	private Thread VerifierThread = null;
    
	private void launchSynchronousExecutionThread() {
		final ArrayList<BPThread> syncThreads = threads;
		VerifierThread = new Thread(new Runnable() {
			public void run() {
			  Iterator<BPThread> it = syncThreads.iterator();
			  ModelRoot root = Ooaofooa.getDefaultInstance();
			  while (it.hasNext()) {
                BPThread current = it.next();
                ComponentInstance_c fee = current.getEngine();
				fee.setRunning(true);
			  }
			  synchronized (system){
			  try {
			    system.wait();
			  }
			  catch (InterruptedException ie) {
				  // Do nothing
			  }
			}
			  int startingSize = syncThreads.size();
			  boolean systemTerminated = false;
			  while (!systemTerminated && system != null) {
				systemTerminated = true;
				boolean systemActive = false;
			    Iterator<BPThread> executionIterator = syncThreads.iterator();
			    synchronized (system) {
			    while (executionIterator.hasNext() && syncThreads.size() == startingSize) {
				try {
	                BPThread current = executionIterator.next();
	                ComponentInstance_c fee = current.getEngine();
					ModelRoot.disableChangeNotification();
					if (fee != null) {
						int lastRunState = Runstatetype_c.OOA_UNINITIALIZED_ENUM;
						boolean modelStateChanged = false;
						Stack_c stack = Stack_c.getOneI_STACKOnR2930(fee);
						try {
							Ooaofooa.beginVerifierExecution(fee
										.getExecution_engine_id());
							stack = Stack_c.getOneI_STACKOnR2930(fee);
							if (stack != null) {
								lastRunState = stack.getRunstate();
								modelStateChanged = stack.Run();
								if (stack.getRunstate() != Runstatetype_c.Terminated) {
								  systemTerminated = false;
								  // Are events pending?
								  PendingEvent_c [] selfEvts = PendingEvent_c.getManyI_EVIsOnR2946(SelfQueueEntry_c.getManyI_SQEsOnR2946(fee));
								  PendingEvent_c [] queuedEvts = PendingEvent_c.getManyI_EVIsOnR2944(EventQueueEntry_c.getManyI_EQEsOnR2944(fee));
								  boolean eventsPending = selfEvts.length != 0 | queuedEvts.length != 0;
								  if (stack.getRunstate() == Runstatetype_c.Running && (modelStateChanged || eventsPending)) {
									  systemActive = true;
								  }
								}
								else {
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
						} catch (Throwable e) {
								DebugUIPlugin.log(e);
						} finally {
								Ooaofooa.endVerifierExecution(fee
										.getExecution_engine_id());
						}
						stack = Stack_c.getOneI_STACKOnR2930(fee);
						if (stack != null
								&& stack.getRunstate() == Runstatetype_c.Suspended
								&& lastRunState != Runstatetype_c.Suspended) {
							BPThread thread = BPThread.getThreadExecuting(fee);
							thread.fireSuspendEvent(DebugEvent.BREAKPOINT);
							try {
							  threadIsSuspending(current);
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
							fee.Lockqueue("I_EQE");
							EventQueueEntry_c eqe = EventQueueEntry_c
									.getOneI_EQEOnR2944(fee);
							fee.Unlockqueue("I_EQE");
							fee.Lockqueue("I_SQE");
							SelfQueueEntry_c sqe = SelfQueueEntry_c
									.getOneI_SQEOnR2946(fee);
							fee.Unlockqueue("I_SQE");
							StackFrame_c blockedStf = StackFrame_c.getOneI_STFOnR2965BlockedBy(StackFrame_c.getOneI_STFOnR2929(stack));
							boolean qdFramesReady = false;
							StackFrame_c [] qdFrames = StackFrame_c.getManyI_STFsOnR2966(stack);
							for (int i=0; i < qdFrames.length; i++) {
								if (qdFrames[i].getReadyforinterrupt()== true) {
									qdFramesReady = true;
									break;
								}
							}
							boolean qdResultsReady = false;
							StackFrame_c [] qdResults = StackFrame_c.getManyI_STFsOnR2967(stack);
							for (int i=0; i < qdResults.length; i++) {
								if (qdResults[i].getReadyforinterrupt()== true) {
									qdResultsReady = true;
									break;
								}
							}
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
										
									}
								} catch (Throwable t) {
									DebugUIPlugin.log(t);
								}
								finally {
										ModelRoot.disableChangeNotification();
								}
							}
						if (modelStateChanged) {
						  BPThread.refreshVerifierViews();
						  BPThread.refreshCanvases();
						}
					} // fee.isRunning()
				} catch (Throwable t) {
					DebugUIPlugin.log(t);
				} finally {
					ModelRoot.enableChangeNotification();
				}
				Thread.yield();
			    } // end engine iterator
			    if (!systemActive && !systemTerminated) {
			      boolean eventsDelivered = TIM.performSimulatedTime();
			      if(!eventsDelivered) {
			        try {
			    	  system.wait();
			        }
			        catch (InterruptedException ie) {
			    	  // Do Nothing
			        }
			      }
			    }
			    } // end synchronized section
			  }// end while system not terminated
			  try {
                terminate();
			  }
			  catch (DebugException de) {
				  CorePlugin.logError("Exception terminating deterministic session", de);
			  }
			  fireTerminateEvent();
			  VerifierThread = null;
			  
			  // Perform one final UI update
			  BPThread.refreshVerifierViews();
			  BPThread.refreshCanvases();
			};
		}, "Verifier (" + projectName + ")");
		VerifierThread.setPriority(Thread.MIN_PRIORITY);
		VerifierThread.start();
		Thread.State state = VerifierThread.getState();
		while (state != Thread.State.WAITING) {
			// Give the new thread a change to start
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			state = VerifierThread.getState();
		}
		Notify();
		
	}

	private void wireChannels(NonRootModelElement element, ComponentInstance_c parentExEng) {
    ComponentInstance_c[] cis = null;
    if (element instanceof Component_c) {
      cis = ComponentInstance_c
          .getManyI_EXEsOnR2955((Component_c) element);
      for (int i = 0; i < cis.length; i++) {
        wireChannels(cis[i], parentExEng);
      }
    } else if (element instanceof ComponentReference_c) {
      cis = ComponentInstance_c
          .getManyI_EXEsOnR2963((ComponentReference_c) element);
      for (int i = 0; i < cis.length; i++) {
        wireChannels(cis[i], parentExEng);
      }
    }
  }

  private static ArrayList<Component_c> optimized = new ArrayList<Component_c>();

	private void optimizeChannels(NonRootModelElement element) {
		if (element instanceof Component_c) {
      if (!optimized.contains(element)) {
        optimized.add((Component_c) element);
        // optimize all component instances
        ComponentInstance_c[] cis = ComponentInstance_c
            .getManyI_EXEsOnR2955((Component_c) element);
        for (int i = 0; i < cis.length; i++) {
          optimizeChannels(cis[i]);
        }
        // optimize all references to this component
        cis = ComponentInstance_c.getManyI_EXEsOnR2963(ComponentReference_c
            .getManyCL_ICsOnR4201((Component_c) element));
        for (int i = 0; i < cis.length; i++) {
          optimizeChannels(cis[i]);
        }
        // now optimize all the children
        NonRootModelElement[] componentChildren = BPDebugUtils
            .getComponentChildren((Component_c) element);
        for (int i = 0; i < componentChildren.length; i++) {
          optimizeChannels(componentChildren[i]);
        }
      }
    }
    if (element instanceof ComponentReference_c) {
      optimizeChannels(Component_c
          .getOneC_COnR4201((ComponentReference_c) element));
    }
    // Now do the real optimization . . .
    if (element instanceof ComponentInstance_c) {
      RuntimeChannel_c[] channels = RuntimeChannel_c
          .getManyI_RCHsOnR2968IsInterfaceProviderTo((ComponentInstance_c) element);
      for (int i = 0; i < channels.length; i++) {
        //optimizeChannel(channels[i]);
      }
    }
	}

	private void optimizeChannel(RuntimeChannel_c runtimeChannel) {
	  // Find the last runtime channel in the chain . . . 
    RuntimeChannel_c next =
      RuntimeChannel_c.getOneI_RCHOnR2973Requirer(runtimeChannel);
	  RuntimeChannel_c cursor = next;
	  RuntimeChannel_c terminal = null;
	  while (cursor != null) {
	    terminal = cursor;
	    cursor = RuntimeChannel_c.getOneI_RCHOnR2973Requirer(cursor);
	  }
	  // . . . and relate directly to it.
	  if (terminal != next) {
	    runtimeChannel.unrelateAcrossR2973FromRequirer(next);
	    runtimeChannel.relateAcrossR2973ToRequirer(terminal);
	  }
	  else {
	    if (next == null) {
	      // there is no shortcut, just relate to self.
	      runtimeChannel.relateAcrossR2973ToRequirer(runtimeChannel);
	    }
	  }
	}

	private InterfaceReference_c getRequiredRefFromChannel(
			RuntimeChannel_c runtimeChannel) {
		// Satisfaction imported
		InterfaceReference_c prevRef = InterfaceReference_c
				.getOneC_IROnR4701(ImportedReference_c
						.getOneCL_IIROnR4703(ImportedRequirement_c
								.getOneCL_IROnR4706(Satisfaction_c
										.getManyC_SFsOnR2969(runtimeChannel))));
		if (prevRef == null) {
			// Satisfaction nested
			prevRef = InterfaceReference_c.getOneC_IROnR4009(Requirement_c
					.getOneC_ROnR4002(Satisfaction_c
							.getOneC_SFOnR2969(runtimeChannel)));
		}
		if (prevRef == null) {
			// Delegation imported
			prevRef = InterfaceReference_c
					.getOneC_IROnR4701(ImportedReference_c
							.getOneCL_IIROnR4704(Delegation_c
									.getManyC_DGsOnR2972(runtimeChannel)));
		}
		if (prevRef == null) {
			// Delegation nested
			prevRef = InterfaceReference_c
					.getOneC_IROnR4013(InterfaceReferenceInDelegation_c
							.getOneC_RIDOnR4013(Delegation_c
									.getOneC_DGOnR2972(runtimeChannel)));
		}
		Requirement_c req = Requirement_c.getOneC_ROnR4009(prevRef);
		if (req != null) {
		  return prevRef;
		}
		return null;
	}

	private InterfaceReference_c getProvidedRefFromChannel(
			RuntimeChannel_c runtimeChannel) {
		// Satisfaction imported
		InterfaceReference_c prevRef = InterfaceReference_c
				.getOneC_IROnR4701(ImportedReference_c
						.getOneCL_IIROnR4703(ImportedProvision_c
								.getOneCL_IPOnR4705(ImportedProvisionInSatisfaction_c
										.getManyCL_IPINSsOnR4705(Satisfaction_c
												.getManyC_SFsOnR2969(runtimeChannel)))));
		if (prevRef == null) {
			// Satisfaction nested
			prevRef = InterfaceReference_c.getOneC_IROnR4009(Provision_c
					.getOneC_POnR4002(Satisfaction_c
							.getOneC_SFOnR2969(runtimeChannel)));
		}
		if (prevRef == null) {
			// Delegation nested
			prevRef = InterfaceReference_c
					.getOneC_IROnR4013(InterfaceReferenceInDelegation_c
							.getOneC_RIDOnR4013(Delegation_c
									.getOneC_DGOnR2972(runtimeChannel)));
		}
		if (prevRef == null) {
			// Delegation imported
			prevRef = InterfaceReference_c
					.getOneC_IROnR4701(ImportedReference_c
							.getOneCL_IIROnR4704(Delegation_c
									.getManyC_DGsOnR2972(runtimeChannel)));
		}
		Provision_c prov = Provision_c.getOneC_POnR4009(prevRef);
		if (prov != null) {
		  return prevRef;
		}
		return null;
	}

  private boolean elementIsBeingLaunched(NonRootModelElement element, NonRootModelElement[] elementsBeingLaunched) {
    for (int i=0; i < elementsBeingLaunched.length; i++) {
      if (elementsBeingLaunched[i] == element) {
        return true;
      }
    }
    return false;
  }
	private void launchImportedComponent(ComponentReference_c icomponent,
			ComponentInstance_c parentExEng, NonRootModelElement[] elementsBeingLaunched, boolean parentIsComponentReference, boolean deterministic) {
	  if (icomponent.Isassigned() && (parentIsComponentReference ||
                   elementIsBeingLaunched(icomponent, elementsBeingLaunched))) {
		  // launch the component
		  Component_c component = Component_c.getOneC_COnR4201(icomponent);
		  ComponentInstance_c exEng = new ComponentInstance_c(component
				.getModelRoot());
		  if (component.getIsrealized()) {
			  setUpForRealizedExecution(component, exEng);
		  }
		  icomponent.relateAcrossR2963To(exEng);
      addComponentInstanceToContainmentHierarchy(parentExEng, exEng);
		  AllActivityModifier aam = new AllActivityModifier(component, null);
		  ModelRoot modelRoot = component.getModelRoot();
		  launchElement(aam, modelRoot, exEng, parentExEng, icomponent, elementsBeingLaunched, true, deterministic);
	  }
	}

	private void launchElement(AllActivityModifier aam, ModelRoot modelRoot,
      ComponentInstance_c exEng, ComponentInstance_c parentExEng,
      NonRootModelElement element, NonRootModelElement[] elementsBeingLaunched, boolean parentIsComponentReference, boolean deterministic) {
    ModelRoot.disableChangeNotification();
    try {
      exEng.Initializeclasses();
      try {
        aam.processAllActivities(AllActivityModifier.PARSE, false, true);
      } catch (Exception e) {
        CorePlugin.logError("Exception encountered during Verifier launch", e);
      }
      Component_c comp = Component_c.getOneC_COnR2955(exEng);
      ComponentReference_c icomp = ComponentReference_c
          .getOneCL_ICOnR2963(exEng);
      if (icomp != null) {
        comp = Component_c.getOneC_COnR4201(icomp);
      }
      AllActivityModifier.initializeBodies(modelRoot, comp);
      AllActivityModifier.initializePotentialSharedBodies(comp);
      
      // launch the model
      exEng.Reset();
      if (TIM.isSIM_TIME()) {
        TIM.addExecEngines(exEng);
      }
      BPThread thr = new BPThread(this, getLaunch(), exEng);
      if (!deterministic) {
        thr.startModel();
        while (thr.isTerminated()) {
        }
      }
      synchronized (threads) {
        threads.add(thr);
      }
      thr.fireCreationEvent();

      // now launch all the children
      Object[] componentChildren = VerifierLaunchContentProvider.instance()
          .getChildren(comp);
      if (componentChildren != null) {
        for (int i = 0; i < componentChildren.length; i++) {
          if (componentChildren[i] instanceof NonRootModelElement) {
            NonRootModelElement nrme = (NonRootModelElement) componentChildren[i];
            if (parentIsComponentReference
                || elementIsBeingLaunched(nrme, elementsBeingLaunched)) {
              if (nrme instanceof ComponentReference_c) {
                launchImportedComponent((ComponentReference_c) nrme, exEng,
                    elementsBeingLaunched, parentIsComponentReference, deterministic);
              } else if (nrme instanceof Component_c) {
                ComponentInstance_c childEng = new ComponentInstance_c(
                    ((Component_c) nrme).getModelRoot());
				  if (((Component_c)nrme).getIsrealized()) {
					  setUpForRealizedExecution(comp, childEng);
				  }
                // Establish containment hierarchy
                addComponentInstanceToContainmentHierarchy(exEng, childEng);
                ((Component_c) nrme).relateAcrossR2955To(childEng);
                aam = new AllActivityModifier((Component_c) nrme, null);
                modelRoot = ((Component_c) nrme).getModelRoot();
                launchElement(aam, modelRoot, childEng, exEng,
                    (Component_c) nrme, elementsBeingLaunched,
                    parentIsComponentReference, deterministic);
              }
            }
          }
        }
      }
    } finally {
      ModelRoot.enableChangeNotification();
    }
    fireChangeEvent(DebugEvent.CONTENT);
    ModelChangedEvent mce = new ModelChangedEvent(modelRoot,
        Modeleventnotification_c.MODEL_EXECUTION_STOPPED, element);
    Ooaofooa.getDefaultInstance().fireModelEvent(mce);
  }

	private void wireChannels(ComponentInstance_c exEng,
      ComponentInstance_c parentExEng) {
    Component_c comp = Component_c.getOneC_COnR2955(exEng);
    if (comp != null) {
      ComponentReference_c icomp = ComponentReference_c
          .getOneCL_ICOnR2963(exEng);
      Assert
          .isTrue(
              icomp == null,
              "Component Instance is associated with both a Component and an Component Reference");
      final boolean isReq = true;
      Satisfaction_c[] reqSats = Satisfaction_c
          .getManyC_SFsOnR4002(Requirement_c
              .getManyC_RsOnR4009(InterfaceReference_c
                  .getManyC_IRsOnR4016(Port_c.getManyC_POsOnR4010(comp))));
      for (int i = 0; i < reqSats.length; i++) {
        wireChannel(exEng, reqSats[i], isReq);
      }
      Satisfaction_c[] provSats = Satisfaction_c
          .getManyC_SFsOnR4002(Provision_c
              .getManyC_PsOnR4009(InterfaceReference_c
                  .getManyC_IRsOnR4016(Port_c.getManyC_POsOnR4010(comp))));
      for (int i = 0; i < provSats.length; i++) {
        wireChannel(exEng, provSats[i], !isReq);
      }
      if (parentExEng != null) {
        // wire delegations.
        Component_c parentComp = Component_c.getOneC_COnR2955(parentExEng);
        if (parentComp == null) {
          parentComp = Component_c.getOneC_COnR4201(ComponentReference_c
              .getOneCL_ICOnR2963(parentExEng));
        }
        Delegation_c[] dlgs = Delegation_c
            .getManyC_DGsOnR4014(InterfaceReference_c
                .getManyC_IRsOnR4016(Port_c.getManyC_POsOnR4010(comp)));
        for (int i = 0; i < dlgs.length; i++) {
          RuntimeChannel_c parentRt = getParentRuntimeChannelFor(dlgs[i],
              exEng, parentExEng);
          Provision_c prov = Provision_c.getOneC_POnR4009(InterfaceReference_c
              .getOneC_IROnR4013(InterfaceReferenceInDelegation_c
                  .getOneC_RIDOnR4013(dlgs[i])));
          wireChannel(exEng, parentExEng, dlgs[i], prov == null, parentRt);
        } // end for each delegation in dlgs
        // Now we need to worry about imported child components.
        // This seems very very complex. :-(
        RuntimeChannel_c[] rtcs = RuntimeChannel_c
            .getManyI_RCHsOnR2968IsInterfaceProviderTo(parentExEng);
        for (int i = 0; i < rtcs.length; i++) {
          if (exEng == ComponentInstance_c
              .getOneI_EXEOnR2968IsInterfaceRequirerOf(rtcs[i])) {
            // We may need to handle delegation for this
          }
        }
        InterfaceReference_c[] iRefs = InterfaceReference_c
            .getManyC_IRsOnR4009(Requirement_c
                .getManyC_RsOnR4009(InterfaceReference_c
                    .getManyC_IRsOnR4016(Port_c.getManyC_POsOnR4010(comp))));
        dlgs = Delegation_c
            .getManyC_DGsOnR4013(InterfaceReferenceInDelegation_c
                .getManyC_RIDsOnR4013(iRefs));
        for (int i = 0; i < dlgs.length; i++) {
          wireChannel(exEng, parentExEng, dlgs[i], isReq, null);
        }
        iRefs = InterfaceReference_c.getManyC_IRsOnR4009(Provision_c
            .getManyC_PsOnR4009(InterfaceReference_c.getManyC_IRsOnR4016(Port_c
                .getManyC_POsOnR4010(comp))));
        dlgs = Delegation_c
            .getManyC_DGsOnR4013(InterfaceReferenceInDelegation_c
                .getManyC_RIDsOnR4013(iRefs));
        for (int i = 0; i < dlgs.length; i++) {
          wireChannel(exEng, parentExEng, dlgs[i], !isReq, null);
        }
      }
    } else {
      ComponentReference_c icomp = ComponentReference_c
          .getOneCL_ICOnR2963(exEng);
      if (icomp != null) {
        ImportedReference_c[] iRefs = ImportedReference_c
                .getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(icomp)); 
        for (int i = 0; i < iRefs.length; i++) {
          wireChannels(exEng, parentExEng, iRefs[i]);
        }
      }
    }
    // Now call this wiring policy on all children of this component instance
    ComponentInstance_c[] cis = ComponentInstance_c
        .getManyI_EXEsOnR2975(ComponentInstanceContainer_c
            .getOneI_CINOnR2974(exEng));
    for (int i = 0; i < cis.length; i++) {
      wireChannels(cis[i], exEng);
    }
  }
	
  private void addComponentInstanceToContainmentHierarchy(
                   ComponentInstance_c parentExEng, ComponentInstance_c exEng) {
    if (parentExEng != null) {
      ComponentInstanceContainer_c cic = ComponentInstanceContainer_c
        .getOneI_CINOnR2974(parentExEng);
      if (cic == null) {
        cic = new ComponentInstanceContainer_c(parentExEng.getModelRoot());
        cic.relateAcrossR2974To(parentExEng);
      }
      boolean alreadyInHierarchy = false;
      ComponentInstance_c[] cis = ComponentInstance_c.getManyI_EXEsOnR2975(cic);
      for (int i = 0; i < cis.length; i++) {
        if (cis[i] == exEng)
          alreadyInHierarchy = true;
      }
      if (!alreadyInHierarchy) {
        cic.relateAcrossR2975To(exEng);
      }
    }
  }

  private RuntimeChannel_c getParentRuntimeChannelFor(Delegation_c dlg,
      ComponentInstance_c exEng, ComponentInstance_c parentExEng) {
    InterfaceReference_c parentIr = InterfaceReference_c
        .getOneC_IROnR4013(InterfaceReferenceInDelegation_c
            .getOneC_RIDOnR4013(dlg));
    Provision_c prov = Provision_c.getOneC_POnR4009(parentIr);
    boolean isReq = prov == null;
    Component_c parentComp = Component_c.getOneC_COnR2955(parentExEng);
    if (parentComp == null) {
      parentComp = Component_c.getOneC_COnR4201(ComponentReference_c
          .getManyCL_ICsOnR2963(parentExEng));
    }
    Component_c candParent = Component_c.getOneC_COnR4010(Port_c
        .getOneC_POOnR4016(parentIr));
    // and candidateParent equals parentComponent
    if (candParent == parentComp) {
      // Is the parent satisfied?
      Satisfaction_c[] parentSats = null;
      if (isReq) {
        parentSats = Satisfaction_c
        .getManyC_SFsOnR4002(Requirement_c.getManyC_RsOnR4009(parentIr));
      }
      else {
        parentSats = Satisfaction_c.getManyC_SFsOnR4002(prov);
      }
      for (int j = 0; j < parentSats.length; j++) {
        RuntimeChannel_c[] rtchs = RuntimeChannel_c
            .getManyI_RCHsOnR2969(parentSats[j]);
        for (int k = 0; k < rtchs.length; k++) {
          ComponentInstance_c ci = null;
          if (isReq) {
            ci = ComponentInstance_c
            .getOneI_EXEOnR2968IsInterfaceProviderTo(rtchs[k]);
          } else {
            ci = ComponentInstance_c
            .getOneI_EXEOnR2968IsInterfaceRequirerOf(rtchs[k]);
          }
          if (ci == parentExEng) {
            return rtchs[k];
          }
        } // end for each runtime channel in rchs
      } // end for each satisfaction in parentSats
      Delegation_c[] parentDlgs = Delegation_c.getManyC_DGsOnR4014(parentIr);
      for (int j = 0; j < parentDlgs.length; j++) {
        RuntimeChannel_c[] rtchs = RuntimeChannel_c
            .getManyI_RCHsOnR2972(parentDlgs[j]);
        for (int k = 0; k < rtchs.length; k++) {
          ComponentInstance_c ci = null;
          if (isReq) {
            ci = ComponentInstance_c
                .getOneI_EXEOnR2968IsInterfaceProviderTo(rtchs[k]);
          } else {
            ci = ComponentInstance_c
                .getOneI_EXEOnR2968IsInterfaceRequirerOf(rtchs[k]);
          }
          if (ci == parentExEng) {
            return rtchs[k];
          }
        } // end for each runtime channel in rchs
      } // end for each satisfaction in parentSats
    }
    // Should never reach here . . . 
    Port_c port = Port_c.getOneC_POOnR4016(parentIr);
    Component_c comp = Component_c.getOneC_COnR2955(exEng);
    if (comp == null) {
      comp = Component_c.getOneC_COnR4201(
          ComponentReference_c.getOneCL_ICOnR2963(exEng));
    }
    String irName = parentIr != null ? parentIr.getName() : "Unknown";
    String message = "Parent runtime channel not found. "
        + parentComp.getName() + " -> " + comp.getName() + " " + irName;
    Throwable e = new Throwable();
    e.fillInStackTrace();
    CorePlugin.logError(message, e);
    return null;
  }
	private void wireChannels(ComponentInstance_c exEng,
			ComponentInstance_c parentExEng, ImportedReference_c iRef) {
		ComponentInstance_c target = null;
		ImportedRequirement_c iReq = ImportedRequirement_c
				.getOneCL_IROnR4703(iRef);
		if (iReq != null) {
			Satisfaction_c reqSat = Satisfaction_c.getOneC_SFOnR4706(iReq);
			if (!alreadyWired(exEng, reqSat)) {
				// We need to traverse the provided side to get the target
				target = getProviderTargetFor(reqSat, parentExEng);
				if (target != null) {
					wireChannel(reqSat, true, exEng, target, null);
				}
			}
			Delegation_c dlg = Delegation_c.getOneC_DGOnR4704(iRef);
			if (dlg != null && parentExEng != null) {
        RuntimeChannel_c parentRt = getParentRuntimeChannelFor(dlg, exEng, parentExEng);
				wireChannel(exEng, parentExEng, dlg, true, parentRt);

			}
		} else {
			Satisfaction_c[] provSats = Satisfaction_c
					.getManyC_SFsOnR4705(ImportedProvisionInSatisfaction_c
							.getManyCL_IPINSsOnR4705(ImportedProvision_c
									.getManyCL_IPsOnR4703(iRef)));
			for (int i = 0; i < provSats.length; i++) {
				if (!alreadyWired(exEng, provSats[i])) {
					// We need to traverse the required side to get the target
					target = getRequirerTargetFor(provSats[i], parentExEng);
					if (target != null) {
						wireChannel(provSats[i], false, exEng, target, null);
					}
				}
			}
			ImportedProvision_c ip = ImportedProvision_c
					.getOneCL_IPOnR4703(iRef);
				Delegation_c dlg = Delegation_c.getOneC_DGOnR4704(iRef);
				if (dlg != null && parentExEng != null) {
				  RuntimeChannel_c parentRt = getParentRuntimeChannelFor(dlg, exEng, parentExEng);
					wireChannel(exEng, parentExEng, dlg, false, parentRt);
				}
		}
	}

	private ComponentInstance_c getProviderTargetFor(NonRootModelElement element, ComponentInstance_c parent) {
		ComponentInstance_c result = null;
		ImportedReference_c iref = null;
		if (element instanceof Satisfaction_c) {
			iref = ImportedReference_c.getOneCL_IIROnR4703(ImportedProvision_c
					.getOneCL_IPOnR4705(ImportedProvisionInSatisfaction_c
							.getOneCL_IPINSOnR4705((Satisfaction_c) element)));
		} else {
			iref = ImportedReference_c
					.getOneCL_IIROnR4704((Delegation_c) element);
		}
		if (iref != null) {
			ComponentInstance_c[] candidates = ComponentInstance_c
					.getManyI_EXEsOnR2963(ComponentReference_c
							.getOneCL_ICOnR4707(PortReference_c.getOneCL_POROnR4708(iref)));
			if (parent != null) {
			  ComponentInstance_c[] children = ComponentInstance_c.
					getManyI_EXEsOnR2975(ComponentInstanceContainer_c.getOneI_CINOnR2974(parent));
			  for (ComponentInstance_c candidate: candidates) {
				for(ComponentInstance_c child: children) {
					if (candidate == child) {
						result = candidate;  // TODO need to check not already wired?
						break;
					}
				}
			  }
			}
			else {
				if (candidates.length != 1) {
				  DebugPlugin.log(new Throwable("Internal warning getting Provider Target. Ambiguous instances found."));
				}
				if (candidates.length == 0) {
				  DebugPlugin.log(new Throwable("Internal warning getting Provider Target. No instances found."));
				}
				else {
				  result = candidates[0];
				}
			}
			if (result == null) {
			  DebugPlugin.log(new Throwable("Internal error getting Provider Target. No suitable instance found."));
			}
		} else {
			Provision_c prov = null;
			if (element instanceof Satisfaction_c) {
				prov = Provision_c.getOneC_POnR4002((Satisfaction_c) element);
			} else {
				prov = Provision_c.getOneC_POnR4009(InterfaceReference_c
						.getManyC_IRsOnR4013(InterfaceReferenceInDelegation_c
								.getManyC_RIDsOnR4013((Delegation_c) element)));
			}
			if (prov != null) {
				result = ComponentInstance_c.getOneI_EXEOnR2955(Component_c
						.getOneC_COnR4010(Port_c
								.getOneC_POOnR4016(InterfaceReference_c
										.getOneC_IROnR4009(prov))));

			}
		}
		return result;
	}

	private ComponentInstance_c getRequirerTargetFor(NonRootModelElement element, ComponentInstance_c parent) {
		ComponentInstance_c result = null;
		ImportedReference_c iref = null;
		if (element instanceof Satisfaction_c) {
			iref = ImportedReference_c
					.getOneCL_IIROnR4703(ImportedRequirement_c
							.getOneCL_IROnR4706((Satisfaction_c) element));
		} else {
			iref = ImportedReference_c
					.getOneCL_IIROnR4704((Delegation_c) element);
		}
		if (iref != null) {
			ComponentInstance_c[] candidates = ComponentInstance_c
					.getManyI_EXEsOnR2963(ComponentReference_c
							.getOneCL_ICOnR4707(PortReference_c.getOneCL_POROnR4708(iref)));
			if (parent != null) {
			  ComponentInstance_c[] children = ComponentInstance_c.
					getManyI_EXEsOnR2975(ComponentInstanceContainer_c.getOneI_CINOnR2974(parent));
			  for (ComponentInstance_c candidate: candidates) {
				for(ComponentInstance_c child: children) {
					if (candidate == child) {
						result = candidate;  // TODO need to check not already wired?
						break;
					}
				}
			  }
			}
			else {
				if (candidates.length != 1) {
					  DebugPlugin.log(new Throwable("Internal warning getting Required Target. Ambiguous instances found."));
				}
				if (candidates.length == 0) {
					  DebugPlugin.log(new Throwable("Internal warning getting Required Target. No instances found."));
				}
				else {
				  result = candidates[0];
				}
			}
			if (result == null) {
			  DebugPlugin.log(new Throwable("Internal error getting Required Target. No suitable instance found."));
			}
		} else {
			Requirement_c req = null;
			if (element instanceof Satisfaction_c) {
				req = Requirement_c.getOneC_ROnR4002((Satisfaction_c) element);
			} else {
				req = Requirement_c.getOneC_ROnR4009(InterfaceReference_c
						.getManyC_IRsOnR4013(InterfaceReferenceInDelegation_c
								.getManyC_RIDsOnR4013((Delegation_c) element)));
			}
			if (req != null) {
				result = ComponentInstance_c.getOneI_EXEOnR2955(Component_c
						.getOneC_COnR4010(Port_c
								.getOneC_POOnR4016(InterfaceReference_c
										.getOneC_IROnR4009(req))));
			}
		}
		return result;
	}

	private void wireChannel(ComponentInstance_c exEng,
			NonRootModelElement element, boolean isRequired) {
		wireChannel(exEng, null, element, isRequired, null);
	}

	private void wireChannel(ComponentInstance_c exEng,
			ComponentInstance_c parentExEng, NonRootModelElement element,
			boolean isRequired, RuntimeChannel_c parentRt) {
		if (alreadyWired(exEng, element))
			return;
		ComponentInstance_c source = null;
    ComponentInstance_c target = null;
		if (parentExEng != null) {
		  source = parentExEng;
			target = exEng;
		} else {
      source = exEng;
			if (isRequired) {
				target = getProviderTargetFor(element, parentExEng);
			} else {
				target = getRequirerTargetFor(element, parentExEng);
			}
		}
		wireChannel(element, isRequired, source, target, parentRt);
	}

	private void wireChannel(NonRootModelElement element, boolean isRequired,
			ComponentInstance_c src, ComponentInstance_c target, RuntimeChannel_c parentRt) {
		if (src != null && target != null) {
			RuntimeChannel_c channel = new RuntimeChannel_c(element
					.getModelRoot());
			boolean wireForwards = isRequired;
			if (element instanceof Satisfaction_c) {
				channel.relateAcrossR2969To((Satisfaction_c) element);
			} else {
				channel.relateAcrossR2972To((Delegation_c) element);
				wireForwards = !isRequired;
			}
			if (wireForwards) {
				channel.relateAcrossR2968ToIsInterfaceProviderTo(src);
				channel.relateAcrossR2968ToIsInterfaceRequirerOf(target);
			} else {
				// If this is a provision, we relate the source and target the
				// other way around. This provides consistency across R2968,
				// which is important across all reflexive associations.
				channel.relateAcrossR2968ToIsInterfaceProviderTo(target);
				channel.relateAcrossR2968ToIsInterfaceRequirerOf(src);
			}
			if (parentRt != null) {
				if (isRequired) {
					channel.relateAcrossR2973ToRequirer(parentRt);
        }
        else {
					channel.relateAcrossR2973ToProvider(parentRt);
				}
			}

		}

	}

	private boolean alreadyWired(ComponentInstance_c exEng,
			NonRootModelElement element) {
		RuntimeChannel_c[] existingChannels = RuntimeChannel_c
				.getManyI_RCHsOnR2968IsInterfaceProviderTo(exEng);
		for (int i = 0; i < existingChannels.length; i++) {
			Satisfaction_c oneSat = Satisfaction_c
					.getOneC_SFOnR2969(existingChannels[i]);
			if (oneSat != null && oneSat.equals(element)) {
				// Already wired
				return true;
			}
			Delegation_c oneDel = Delegation_c
					.getOneC_DGOnR2972(existingChannels[i]);
			if (oneDel != null && oneDel.equals(element)) {
				// Alreadywired
				return true;
			}
		}
		existingChannels = RuntimeChannel_c
				.getManyI_RCHsOnR2968IsInterfaceRequirerOf(exEng);
		for (int i = 0; i < existingChannels.length; i++) {
			Satisfaction_c othSat = Satisfaction_c
					.getOneC_SFOnR2969(existingChannels[i]);
			if (othSat != null && othSat.equals(element)) {
				// Already wired
				return true;
			}
			Delegation_c othDel = Delegation_c
					.getOneC_DGOnR2972(existingChannels[i]);
			if (othDel != null && othDel.equals(element)) {
				// Alreadywired
				return true;
			}
		}
		return false;
	}

	public void disconnect() throws DebugException {
	}

	public boolean canDisconnect() {
		return false;
	}

	public boolean isDisconnected() {
		return false;
	}

	public boolean supportsStorageRetrieval() {
		return false;
	}

	public IMemoryBlock getMemoryBlock(long startAddress, long length)
			throws DebugException {
		return null;
	}

	public IThread[] getThreads() throws DebugException {
		synchronized (threads) {
			IThread[] threadsResult = new IThread[threads.size()];
			int i = 0;
			Iterator it = threads.iterator();
			while (it.hasNext()) {
				threadsResult[i++] = (IThread) it.next();
			}
			return threadsResult;
		}
	}

	public boolean hasThreads() throws DebugException {
		return threads.size() > 0;
	}

	public boolean canResume() {
		boolean result = false;
		for (int i = 0; i < threads.size(); i++) {
			if (((BPThread) threads.get(i)).canResume()) {
				result = true;
			}
		}
		return result;
	}

	public boolean canSuspend() {
		boolean result = false;
		for (int i = 0; i < threads.size(); i++) {
			if (((BPThread) threads.get(i)).canSuspend()) {
				result = true;
			}
		}
		return result;
	}

	public boolean isSuspended() {
		return !canSuspend();
	}

	public void resume() throws DebugException {
		for (int i = 0; i < threads.size(); i++) {
			BPThread thread = (BPThread) threads.get(i);
			if (thread.canResume()) {
				thread.resume();
			}
		}
		Notify();
		TIM.resumeTime();
	}

	public boolean canTerminate() {
		return !isTerminated();
	}

	public boolean isTerminated() {
		boolean terminated = true;
		synchronized (threads) {
			Iterator it = threads.iterator();
			while (it.hasNext()) {
				IThread thr = (IThread) it.next();
				if (!thr.isTerminated()) {
					terminated = false;
				}
			}
		}
		return terminated;
	}

	// ========================================================================
	// remove timer traces when terminate
	// ========================================================================

	private void removeTimersTraces(ModelRoot modelRoot) {
		if (modelRoot != null) {
			ComponentInstance_c ee = ComponentInstance_c
					.ComponentInstanceInstance(modelRoot);
			if (ee != null) {
				try {
					ModelRoot.disableChangeNotification();
					Timer_c[] timers = Timer_c.TimerInstances(modelRoot);
					for (int i = 0; i < timers.length; i++) {
						if (timers[i] != null) { // can go null during shutdown
							timers[i].Dispose();
						}
					}
				} catch (Exception e) {
					CorePlugin
							.logError(
									"Exception found during verifier timer termination",
									e);// $NON-NLS$
				} finally {
					ModelRoot.enableChangeNotification();
				}
			}
		}
	}// End of removeTimersTraces

	public void terminate() throws DebugException {
		ModelRoot modelRoot = null;
		ModelRoot prev_modelRoot = null;
		synchronized (threads) {
			Iterator it = threads.iterator();
			while (it.hasNext()) {
				BPThread thr = (BPThread) it.next();
				if (thr.getEngine() != null) {
					modelRoot = thr.getEngine().getModelRoot();
					if (prev_modelRoot != modelRoot) {
						TIM.terminate(thr.getEngine());
					}
					prev_modelRoot = modelRoot;
					thr.resetClassLoader();
					Vm_c.removeStack(thr.getRunner());
				}
				if (thr.canTerminate()) {
					thr.stop();
				}
				removeTimersTraces(modelRoot);
			}
			threads.clear();
		}
		fireTerminateEvent();
		
		DebugPlugin.getDefault().getBreakpointManager()
				.removeBreakpointListener(this);
		Notify();
		targets.remove(this);
		if (targets.isEmpty()) {
			TIM.stopTimers();
		}
		BPClassLoader.resetTheDefinitionsCache();
		// Cancel the timer. 
		if (executionTimer != null) {
			executionTimer.cancel();
		}
		optimized.clear();
		process = null;
		// Request garbage collection. This is merely a hint to the JVM
		// that we need unwanted instances flushed. It can take several
		// seconds for it to respond.
		System.gc();
	}



	public IProcess getProcess() {
		if ((launch.getProcesses() != null)
				&& (launch.getProcesses().length > 0)) {
			return launch.getProcesses()[0];
		} else {
			return null;
		}
	}

	public void suspend() throws DebugException {
		for (int i = 0; i < threads.size(); i++) {
			BPThread thread = (BPThread) threads.get(i);
			if (thread.canSuspend()) {
				thread.suspend();
			}
		}
		TIM.suspendTime();
	}

	public void breakpointAdded(IBreakpoint breakpoint) {
		if (breakpoint instanceof IBPBreakpoint) {
			((IBPBreakpoint) breakpoint).createTargetBreakpoint();
		} else if (breakpoint instanceof VerifierExceptionBreakpoint) {
			((VerifierExceptionBreakpoint) breakpoint).createTargetBreakpoint();
		}
	}

	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		if (breakpoint instanceof IBPBreakpoint) {
			((IBPBreakpoint) breakpoint).deleteTargetBreakpoint();
		} else if (breakpoint instanceof VerifierExceptionBreakpoint) {
			((VerifierExceptionBreakpoint) breakpoint).deleteTargetBreakpoint();
		}
	}

	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		if (breakpoint instanceof IBPBreakpoint) {
			((IBPBreakpoint) breakpoint).modifyTargetBreakpoint(delta);
		} else if (breakpoint instanceof VerifierExceptionBreakpoint) {
			((VerifierExceptionBreakpoint) breakpoint)
					.modifyTargetBreakpoint(delta);
		}
	}

	public String getName() {
		if (system != null) {
			return "Verifier [" + system.getName() + "]";
		} else {
		  if (!isTerminated()) {
			return "Error: No system to execute.";
		  }
		  else {
			return "";
		  }
		}
	}

	public void remove(BPThread thr) {
		synchronized(system) {
		synchronized (threads) {
			threads.remove(thr);
			fireChangeEvent(DebugEvent.CONTENT);
			if (threads.isEmpty()) {
				fireTerminateEvent();
			}
		    Notify();
		}
		}
	}

	/**
	 * Install breakpoints that are already registered with the breakpoint
	 * manager.
	 */
	private void installDeferredBreakpoints() {
		IBreakpoint[] breakpoints = DebugPlugin.getDefault()
				.getBreakpointManager().getBreakpoints(
						IBPDebugUIPluginConstants.PLUGIN_ID);
		for (int i = 0; i < breakpoints.length; i++) {
			getDebugTarget().breakpointAdded(breakpoints[i]);
		}
	}

	public void finalize() {
		DebugPlugin.getDefault().getBreakpointManager()
				.removeBreakpointListener(this);
	}

	protected void threadIsSuspending(BPThread suspendingThread)
			throws DebugException {
		for (int i = 0; i < threads.size(); i++) {
			BPThread thread = (BPThread) threads.get(i);
			if (thread.canSuspend() && suspendingThread != thread) {
				// We don't want to try and suspend the thread that is already
				// suspending.
				thread.internalSuspend();
			}
		}
		Notify();
	}

	protected void threadIsResuming(BPThread resumingThread)
			throws DebugException {
		for (int i = 0; i < threads.size(); i++) {
			BPThread thread = (BPThread) threads.get(i);
			if (thread.canResume() && resumingThread != thread) {
				// We don't want to try and suspend the thread that is already
				// resuming.
				thread.internalResume(false);
			}
		}
		Notify();
	}

  protected boolean threadIsWaitingToResume(BPThread resumingThread)
       throws DebugException {
    boolean allStarted = false;
    long startTime = System.currentTimeMillis();
    while (allStarted == false && System.currentTimeMillis() < startTime + getDebugTimeout()) {
      allStarted = true;
      for (int i = 0; i < threads.size(); i++) {
        BPThread thread = (BPThread) threads.get(i);
        if (thread.canResume() && resumingThread != thread) {
          // We don't want to return and start the resuming
          // thread until all the others have resumed.
          ComponentInstance_c cr = thread.getEngine();
          Stack_c stack = Stack_c.getOneI_STACKOnR2930(cr);
          if (stack.getRunstate() != Runstatetype_c.Running) {
            allStarted = false;
          }
        }
      } // end for
    } // end while
    return allStarted;
  }
  

	public void Notify() {
		if (isDeterministic() && system != null) {
			synchronized (system) {
				system.notify();
			}
		}
	}

	public boolean deterministicExecutionInProgress() {
		boolean isInProgress = false;
		Thread currentRunningThread = VerifierThread;
		isInProgress = VerifierThread != null
				&& currentRunningThread.isAlive()
				&& currentRunningThread.getState() != State.WAITING
				&& currentRunningThread.getState() != State.TERMINATED;
		return isInProgress;
	}

	public static ArrayList<BPDebugTarget> getTargets() {
		return targets;
	}

}
