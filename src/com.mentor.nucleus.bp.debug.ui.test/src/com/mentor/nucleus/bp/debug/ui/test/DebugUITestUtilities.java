//=====================================================================
//
//File:      $RCSfile: DebugUITestUtilities.java,v $
//Version:   $Revision: 1.28 $
//Modified:  $Date: 2013/05/10 04:28:45 $
//
//(c) Copyright 2008-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.debug.ui.test;

import java.lang.Thread.State;
import java.util.ArrayList;

import junit.framework.Assert;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.internal.ui.viewers.model.provisional.TreeModelViewer;
import org.eclipse.debug.internal.ui.views.variables.VariablesView;
import org.eclipse.debug.ui.AbstractDebugView;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.debug.ui.IDebugView;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.TextConsole;
import org.eclipse.ui.part.ViewPart;

import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.ComponentPackage_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.IntercomponentQueueEntry_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.PendingEvent_c;
import com.mentor.nucleus.bp.core.Runstatetype_c;
import com.mentor.nucleus.bp.core.StackFrame_c;
import com.mentor.nucleus.bp.core.Stack_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionException;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.TransactionUtil;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.debug.ui.actions.ExecuteAction;
import com.mentor.nucleus.bp.debug.ui.launch.BPDebugUtils;
import com.mentor.nucleus.bp.debug.ui.launch.VerifierLaunchConfiguration;
import com.mentor.nucleus.bp.debug.ui.model.BPDebugTarget;
import com.mentor.nucleus.bp.debug.ui.model.BPProcess;
import com.mentor.nucleus.bp.debug.ui.model.BPThread;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;

public class DebugUITestUtilities {

	public static void joinLaunchJob() {
		Job[] jobs = Job.getJobManager().find(null);
		for (int i = 0; i < jobs.length; i++) {
			if (jobs[i].getName().contains("Launching")) {
				try {
					jobs[i].join();
					TestingUtilities.processDisplayEvents();
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public static String getDebugTextTree() {
		// now compare the expected results with the actual results
		IProcess[] processes = DebugPlugin.getDefault().getLaunchManager()
				.getProcesses();
		String actual_results = "";
		for (int i = 0; i < processes.length; i++) {
			if (processes[i] instanceof BPProcess) {
				BPProcess process = (BPProcess) processes[i];
				IDebugTarget debugTarget = process.getDebugTarget();
				if (debugTarget instanceof BPDebugTarget) {
					BPDebugTarget target = (BPDebugTarget) debugTarget;
					try {
						IThread[] threads = target.getThreads();
						for (int j = 0; j < threads.length; j++) {
							if (threads[j] instanceof BPThread) {
								String type = "";
								BPThread thread = (BPThread) threads[j];
								ComponentInstance_c engine = thread.getEngine();
								Component_c component = Component_c
										.getOneC_COnR2955(engine);
								ComponentReference_c icomponent = ComponentReference_c
										.getOneCL_ICOnR2963(engine);
								if (component != null) {
									type = component.getClass().getSimpleName();
								} else if (icomponent != null) {
									type = icomponent.getClass()
											.getSimpleName();
								} else {
									Domain_c domain = Domain_c
											.getOneS_DOMOnR2948(engine);
									if (domain != null) {
										type = domain.getClass()
												.getSimpleName();
									}
								}
								if (j == threads.length - 1) {
									actual_results = actual_results
											+ thread.getName() + " : " + type;
								} else {
									actual_results = actual_results
											+ thread.getName()
											+ " : "
											+ type
											+ System
													.getProperty("line.separator");
								}
							}
						}
					} catch (DebugException e) {
						Assert.fail(e.toString());
					}
					break;
				}

			}
		}
		return actual_results;
	}

	public static MenuItem getLaunchVerifierItem(Menu menu) {
		MenuItem[] items = menu.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i].getText().equals("Launch Verifier")) {
				return items[i];
			}
		}
		return null;
	}

	public static void terminateAllProcesses(SystemModel_c system) {
		TestingUtilities.processDisplayEvents();
		IProcess[] processes = DebugPlugin.getDefault().getLaunchManager()
				.getProcesses();
		for (int i = 0; i < processes.length; i++) {
			try {
				processes[i].terminate();
				TestingUtilities.processDisplayEvents();
			} catch (DebugException e) {
			}
		}
	}

	public static void clearDebugView() {
		IProcess[] processes = DebugPlugin.getDefault().getLaunchManager()
		                                                       .getProcesses();
		for (int i = 0; i < processes.length; i++) {
			processes[i].getLaunch().removeProcess(processes[i]);
		}

	}
	public static ActivityEditor openActivityEditorForSelectedElement() {
		IEditorPart activeEditor = ExplorerUtil.openEditor();
		Assert.assertTrue(
				"Unable to open activity editor for selected element.",
				activeEditor instanceof ActivityEditor);
		return (ActivityEditor) activeEditor;
	}

	public static void setBreakpointAtLine(ActivityEditor editor, int i) {
		Composite column = getAnnotationRulerColumn(editor);
		int x = column.getBounds().x + 5;
		int fontHeight = editor.getTextWidget().getFont().getFontData()[0]
				.getHeight();
		int y = fontHeight * i;
		Event event = new Event();
		event.x = x;
		event.y = y;
		event.button = 1;
		column.notifyListeners(SWT.MouseDoubleClick, event);
	}

	private static Composite getAnnotationRulerColumn(ActivityEditor editor) {
		Composite control = (Composite) editor.getTextViewer().getControl();
		Control[] children = control.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof Composite) {
				Composite column = getAnnotationRulerColumn((Composite) children[i]);
				if (column != null) {
					return column;
				}
			}
		}
		return null;
	}

	private static Composite getAnnotationRulerColumn(Composite composite) {
		Control[] children = composite.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof Canvas) {
				return (Composite) children[i];
			}
			if (children[i] instanceof Composite) {
				Composite column = getAnnotationRulerColumn((Composite) children[i]);
				if (column != null) {
					return column;
				}
			}
		}
		return null;
	}

	public static IProcess getProcessForEngine(ComponentInstance_c engine) {
		TestingUtilities.processDisplayEvents();
		IProcess[] processes = DebugPlugin.getDefault().getLaunchManager()
				.getProcesses();
		for (int i = 0; i < processes.length; i++) {
			IDebugTarget target = processes[i].getLaunch().getDebugTarget();
			if (target instanceof BPDebugTarget) {
				BPDebugTarget bpTarget = (BPDebugTarget) target;
				try {
					IThread[] threads = bpTarget.getThreads();
					for (int j = 0; j < threads.length; j++) {
						if (threads[j] instanceof BPThread) {
							if (((BPThread) threads[j]).getEngine() == engine) {
								return processes[i];
							}
						}
					}
				} catch (DebugException e) {
				}
			}
		}
		return null;
	}

	public static BPDebugTarget getTargetForEngine(ComponentInstance_c engine) {
		TestingUtilities.processDisplayEvents();
		IProcess[] processes = DebugPlugin.getDefault().getLaunchManager()
				.getProcesses();
		for (int i = 0; i < processes.length; i++) {
			IDebugTarget target = processes[i].getLaunch().getDebugTarget();
			if (target instanceof BPDebugTarget) {
				BPDebugTarget bpTarget = (BPDebugTarget) target;
				try {
					IThread[] threads = bpTarget.getThreads();
					if (threads.length > 0 && threads[0] instanceof BPThread) {
						if (((BPThread) threads[0]).getEngine() == engine) {
							return bpTarget;
						}
					}
				} catch (DebugException e) {
				}
			}
		}
		return null;
	}

	public static void launchElement(NonRootModelElement element, Menu menu) {
		launchElement(element, menu, false);
	}

	public static void launchElement(NonRootModelElement element, Menu menu,
			boolean dismiss) {
		TestingUtilities.processDisplayEvents();
		Selection.getInstance().setSelection(new StructuredSelection(element));
		MenuItem item = UITestingUtilities.getMenuItem(menu, "Launch Verifier");
		TestingUtilities.processPlatformJobs();
		if (dismiss) {
			TestUtil.dismissDialog(200);
		} else {
			TestUtil.debugToDialog(200);
		}
		UITestingUtilities.activateMenuItem(item);
		joinLaunchJob();
	}

	private static SystemModel_c getSystemForElement(NonRootModelElement element) {
		if (element instanceof Component_c) {
			return SystemModel_c.getOneS_SYSOnR4606(ComponentPackage_c
					.getOneCP_CPOnR4608((Component_c) element));
		} else if (element instanceof ComponentPackage_c) {
			return SystemModel_c
					.getOneS_SYSOnR4606((ComponentPackage_c) element);
		} else if (element instanceof ComponentReference_c) {
			return SystemModel_c.getOneS_SYSOnR4606(ComponentPackage_c
					.getOneCP_CPOnR4608(Component_c
							.getOneC_COnR4201((ComponentReference_c) element)));
		} else if (element instanceof Domain_c) {
			return SystemModel_c.getOneS_SYSOnR28((Domain_c) element);
		} else if (element instanceof ComponentInstance_c) {
			Component_c component = Component_c
					.getOneC_COnR2955((ComponentInstance_c) element);
			if (component != null) {
				return getSystemForElement(component);
			} else {
				Domain_c domain = Domain_c
						.getOneS_DOMOnR2948((ComponentInstance_c) element);
				if (domain != null) {
					return getSystemForElement(domain);
				}
			}
		}
		return null;
	}

	public static void setLogActivityAndLaunchForElement(
			NonRootModelElement element, Menu menu, String systemName) {
		setLogActivityAndLaunchForElement(element, menu, systemName, false);
	}
	
	public static void setLogActivityAndLaunchForElement(
			NonRootModelElement element, Menu menu, String systemName, boolean disableDeterminism) {
		ILaunchConfiguration configuration = getLaunchConfigurationForElement(
				element, menu, systemName);
		try {
			ILaunchConfigurationWorkingCopy copy = configuration
					.getWorkingCopy();
			copy.setAttribute(VerifierLaunchConfiguration.ATTR_LOGACTIVITY, true);
			if (disableDeterminism) {
				copy.setAttribute(VerifierLaunchConfiguration.ATTR_ENABLEDETERMINISM, false);
			}
			ILaunchConfiguration newConfig = copy.doSave();
			DebugUITools.launch(newConfig, ILaunchManager.DEBUG_MODE);
			joinLaunchJob();
		} catch (CoreException e) {
		}
	}

	public static ILaunchConfiguration getLaunchConfigurationForElement(
			NonRootModelElement element, Menu menu, String systemName) {
		try {
			ILaunchConfiguration[] configurations = DebugPlugin.getDefault()
					.getLaunchManager().getLaunchConfigurations();
			for (int i = 0; i < configurations.length; i++) {
				if (VerifierLaunchConfiguration.configurationHasExactElementsSelected(
						new NonRootModelElement[] { element },
						configurations[i])) {
					return configurations[i];
				}
			}
			// create a configuration
			launchElement(element, menu, true);
			// find the configuration now that it's been created
			configurations = DebugPlugin.getDefault().getLaunchManager()
					.getLaunchConfigurations();
			NonRootModelElement[] elements = null;
			if (element instanceof ComponentPackage_c) {
				elements = BPDebugUtils.getComponentPackageChildren((ComponentPackage_c) element);
			}
			else if (element instanceof Package_c) {
				elements = BPDebugUtils.getPackageChildren((Package_c) element);
			}
			else {
			  elements = new NonRootModelElement[] { element }; 	
			}
			for (int i = 0; i < configurations.length; i++) {
				if (VerifierLaunchConfiguration.configurationHasExactElementsSelected(
					    elements,
						configurations[i])) {
					return configurations[i];
				}
			}
		} catch (CoreException e) {
		}
		return null;
	}

	public static void removeAllConfigurations() {
		try {
			ILaunchConfiguration[] configurations = DebugPlugin.getDefault()
					.getLaunchManager().getLaunchConfigurations();
			for (int i = 0; i < configurations.length; i++) {
				configurations[i].delete();
			}
		} catch (CoreException e) {
		} catch (ClassCastException e) {
		}
	}

	public static void waitForBPThreads(SystemModel_c system) {
		ArrayList<ComponentInstance_c> list = new ArrayList<ComponentInstance_c>();
		ComponentInstance_c[] engines = ComponentInstance_c
				.getManyI_EXEsOnR2948(Domain_c.getManyS_DOMsOnR28(system));
		for (int i = 0; i < engines.length; i++) {
			list.add(engines[i]);
		}
		engines = ComponentInstance_c.getManyI_EXEsOnR2955(Component_c
				.getManyC_CsOnR4608(ComponentPackage_c
						.getManyCP_CPsOnR4606(system)));
		for (int i = 0; i < engines.length; i++) {
			list.add(engines[i]);
		}
		engines = ComponentInstance_c.getManyI_EXEsOnR2955(Component_c
				.getManyC_CsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000((Package_c
						.getManyEP_PKGsOnR1405(system)))));
		for (int i = 0; i < engines.length; i++) {
			list.add(engines[i]);
		}
		engines = ComponentInstance_c.getManyI_EXEsOnR2963(ComponentReference_c
				.getManyCL_ICsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000((Package_c
						.getManyEP_PKGsOnR1405(system)))));
		for (int i = 0; i < engines.length; i++) {
			list.add(engines[i]);
		}
		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch()) {
			for (int i = 0; i < list.size(); i++) {
				ComponentInstance_c engine = list.get(i);
				processDebugEvents();
				TestingUtilities.processPlatformJobs();
				StackFrame_c[] frames = null;
				PendingEvent_c[] events = null;
				StackFrame_c[] queue = null;
				StackFrame_c[] results = null;
				Stack_c stack = null;
				stack = Stack_c.getOneI_STACKOnR2930(engine);
				frames = StackFrame_c.getManyI_STFsOnR2929(stack);
				queue = StackFrame_c.getManyI_STFsOnR2966(IntercomponentQueueEntry_c.getManyI_ICQEsOnR2966(stack));
				results = StackFrame_c.getManyI_STFsOnR2967(stack);
				events = PendingEvent_c.getManyI_EVIsOnR2964(engine);
				boolean running = frames.length != 0 || events.length != 0 || queue.length != 0 || results.length != 0;
				if (!running) {
					// see if a stack is in waiting
					running = stack != null;
				}
				if (running) {
					// see if execution is suspended
					running = !(stack.getRunstate() == Runstatetype_c.Suspended);
				}
				BPDebugTarget debugTarget = getTargetForEngine(engine);
				while (debugTarget!= null && running) {
					PlatformUI.getWorkbench().getDisplay().readAndDispatch();
					processDebugEvents();
					TestingUtilities.processPlatformJobs();
					events = PendingEvent_c.getManyI_EVIsOnR2964(engine);
					frames = StackFrame_c.getManyI_STFsOnR2929(Stack_c
								.getManyI_STACKsOnR2930(engine));
					queue = StackFrame_c.getManyI_STFsOnR2966(IntercomponentQueueEntry_c.getManyI_ICQEsOnR2966(stack));
					results = StackFrame_c.getManyI_STFsOnR2967(stack);
					running = frames.length != 0 || events.length != 0 || queue.length != 0 || results.length != 0;
					if (running) {
						// see if execution is suspended
						running = !(stack.getRunstate() == Runstatetype_c.Suspended);
					}
					if (!running) {
						if (!debugTarget.isDeterministic()) {
							BPThread threadExecuting = BPThread
									.getThreadExecuting(engine);
							while (threadExecuting.getRunner().getState() != State.WAITING
									&& threadExecuting.getRunner().getState() != State.TERMINATED) {
								processDebugEvents();
								TestingUtilities.processDisplayEvents();
							}
						} else {
							while (debugTarget.deterministicExecutionInProgress()) {
								processDebugEvents();
								TestingUtilities.processDisplayEvents();
							}
						}
					}
					processDebugEvents();
				}
			}
		}
		try {
		Thread.sleep(50);
		}
		catch (InterruptedException ie) {
			
		}
	}

	public static String getStackTrace(IThread thread) {
		String trace = "";
		try {
			IStackFrame[] frames = thread.getStackFrames();
			for (int i = 0; i < frames.length; i++) {
				if (i == frames.length - 1)
					trace = trace + frames[i].getName();
				else
					trace = trace + frames[i].getName()
							+ System.getProperty("line.separator");
			}
		} catch (DebugException e) {
		}
		return trace;
	}

	public static IThread getThread(IProcess process, ComponentInstance_c engine) {
		try {
			IThread[] threads = process.getLaunch().getDebugTarget()
					.getThreads();
			for (int i = 0; i < threads.length; i++) {
				if (threads[i] instanceof BPThread) {
					BPThread thread = (BPThread) threads[i];
					if (thread.getEngine() == engine)
						return thread;
				}
			}
		} catch (DebugException e) {
		}
		return null;
	}

	public static String getStackTrace(IProcess process,
			ComponentInstance_c engine) {
		IThread thread = getThread(process, engine);
		return getStackTrace(thread);
	}

	public static String getConsoleText() {
		return getConsoleText("");
	}

	public static String getConsoleText(String expected) {
		// wait for all outstanding events to complete
		TestingUtilities.processDisplayEvents();
		IConsole[] consoles = ConsolePlugin.getDefault().getConsoleManager()
				.getConsoles();
		for (int i = 0; i < consoles.length; i++) {
			if (consoles[i] instanceof TextConsole) {
				TextConsole console = (TextConsole) consoles[i];
				if (console.getType().equals(
						"org.eclipse.debug.ui.ProcessConsoleType")) {
					waitForConsoleUpdate(console, expected);
					String consoleText = console.getDocument().get();
					return consoleText;
				}
			}
		}
		return "";
	}

	static long maxWaitTime = 2000;

	private static void waitForConsoleUpdate(TextConsole console,
			String expected) {
		ConsolePlugin.getDefault().getConsoleManager().refresh(console);
		String consoleText = console.getDocument().get();
		long start = System.currentTimeMillis();
		if (expected.equals("")) {
			while (!consoleText.equals(expected)) {
				TestingUtilities.processDisplayEvents();
				processDebugEvents();
				consoleText = console.getDocument().get();
				long current = System.currentTimeMillis();
				if ((current - start) > maxWaitTime)
					break;
			}
		} else {
			waitForExpectedText(console, expected, start);
		}
	}

	private static void waitForExpectedText(TextConsole console,
			String expected, long start) {
		String consoleText = console.getDocument().get();
		while (!consoleText.equals(expected)) {
			TestingUtilities.processDisplayEvents();
			processDebugEvents();
			consoleText = console.getDocument().get();
			long current = System.currentTimeMillis();
			if ((current - start) > maxWaitTime)
				break;
		}
	}

	private static Job getJob(String name) {
		Job[] jobs = Platform.getJobManager().find(null);
		for (int i = 0; i < jobs.length; i++) {
			if (jobs[i].getName().equals(name)) {
				return jobs[i];
			}
		}
		return null;
	}

	public static void clearConsoleOutput() {
		IConsole[] consoles = ConsolePlugin.getDefault().getConsoleManager()
				.getConsoles();
		for (int i = 0; i < consoles.length; i++) {
			if (consoles[i] instanceof TextConsole) {
				TextConsole console = (TextConsole) consoles[i];
				if (console.getType().equals(
						"org.eclipse.debug.ui.ProcessConsoleType")) {
					waitForConsoleUpdate(console, "");
					console.getDocument().set("");
				}
			}
		}
	}

	public static void openVariablesView() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().showView(
							IDebugUIConstants.ID_VARIABLE_VIEW);
		} catch (PartInitException e) {
		}
	}
	
	public static void maximizeVariablesView(){
		IViewReference[] viewReferences = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getViewReferences();
		for (int i = 0; i < viewReferences.length; i++) {
			if (viewReferences[i].getId().equals( IDebugUIConstants.ID_VARIABLE_VIEW)){
				if (!viewReferences[i].getPage().isPageZoomed())
					viewReferences[i].getPage().toggleZoom(viewReferences[i]);
				break;
			}
		}	
	}
	
	public static void minimizeVariablesView(){
		IViewReference[] viewReferences = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getViewReferences();
		for (int i = 0; i < viewReferences.length; i++) {
			if (viewReferences[i].getId().equals( IDebugUIConstants.ID_VARIABLE_VIEW)){
				if (viewReferences[i].getPage().isPageZoomed())
					viewReferences[i].getPage().zoomOut();
				break;
			}
		}	
	}

	public static String getValueForVariable(String ParentVariableName) {
		return getValueForVariable(ParentVariableName, null);
	}
	
	/**
	 * Find a variable value in Variable view by knowing its name, and its
	 * Parent variable name ( make sure that parent variable tree is expanded
	 * before querying for child variable value )
	 * 
	 * @param ParentVariableName the parent variable name 
	 * @param ChildValueName the child variable name
	 * @return the variable string value, empty string if not found
	 */
	
	public static String getValueForVariable(String ParentVariableName, String ChildValueName) {
		openVariablesView();
		IViewReference[] viewReferences = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getViewReferences();
		for (int i = 0; i < viewReferences.length; i++) {
			if (viewReferences[i].getId().equals(
					IDebugUIConstants.ID_VARIABLE_VIEW)) {
				for (int trial = 0; trial< 25 ; trial++) {
					while ( org.eclipse.ui.PlatformUI.getWorkbench().getDisplay().readAndDispatch());
					Tree tree = getTreeInView(viewReferences[i]);
					TreeItem[] items = tree.getItems();
					for (int j = 0; j < items.length; j++) {
						if (items[j].getText().indexOf(ParentVariableName) != -1) {
							TreeItem[] itemChildren = items[j].getItems();
							if (ChildValueName == null)
								return !items[j].getText(1).equalsIgnoreCase("\"\"") ? items[j].getText(1) : "" ;
								else{
									for (int k = 0; k < itemChildren.length; k++) {
										if (itemChildren[k].getText().indexOf(ChildValueName) != -1) {
											return !itemChildren[k].getText(1).equalsIgnoreCase("\"\"") ? itemChildren[k].getText(1) : "" ;
										}
									}
								}
						}
					}
				}
			}
		}
		return "";
	}
	
	public static String getValueForVariable(TreeItem[] Items, String VariableName){
		for (int i = 0; i < Items.length; i++) {
			if (Items[i].getText().indexOf(VariableName) != -1) {
				return !Items[i].getText(1).equalsIgnoreCase("\"\"")? Items[i].getText(1) : "" ;
			}
		}
		return "";
	}
	
	/**
	 * Use this method to expand a variable tree in Variables View
	 * 
	 * @param VariableName is the variable name
	 * @return Variable children tree items ( if found )
	 */
	
	@SuppressWarnings("restriction")
	public static TreeItem[] expandValueinVariablesView(String VariableName) {
		try{
			boolean clicked = false;
			openVariablesView();
			maximizeVariablesView();
			for (int trial = 0; trial< 1000; trial++) {
				while ( org.eclipse.ui.PlatformUI.getWorkbench().getDisplay().readAndDispatch());
				IViewReference[] viewReferences = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage().getViewReferences();
				for (int i = 0; i < viewReferences.length; i++) {
					if (viewReferences[i].getId().equals( 
							IDebugUIConstants.ID_VARIABLE_VIEW)) {
						VariablesView variablesView = (VariablesView) viewReferences[i].getView(true);
						TreeModelViewer viewer = (TreeModelViewer) variablesView.getViewer();
						Tree tree = getTreeInView(viewReferences[i]);
						TreeItem[] items = tree.getItems();
						for (int j = 0; j < items.length; j++) {
							if (items[j].getText().indexOf(VariableName) != -1) {
								tree.setSelection(items[j]);
								if ( !clicked){
									variablesView.doubleClick(new DoubleClickEvent(viewer, new TreeSelection()));
									clicked = true;
								}
								for (; trial < 2500; trial++) {
									while ( org.eclipse.ui.PlatformUI.getWorkbench().getDisplay().readAndDispatch());
									TreeItem[] treeItems = items[j].getItems();
									int len = treeItems.length;
									if ( len  != 0){
										String text = treeItems[len-1].getText();
										if ( text != "" ){
											System.out.println("Trails are "+ trial);
											return treeItems;
										}
									}
								}

							}
						}
					}
				}
			}
			return new TreeItem[0];
		}finally{
			minimizeVariablesView();
		}
	}

	private static Tree getTreeInView(IViewReference reference) {
		ViewPart view = (ViewPart) reference.getView(false);
		if (view instanceof AbstractDebugView) {
			AbstractDebugView varView = (AbstractDebugView) view;
			Control control = varView.getViewer().getControl();
			return getTreeInControl(control);
		}
		return null;
	}

	private static Tree getTreeInControl(Control control) {
		if (control instanceof Composite) {
			if (control instanceof Tree) {
				return (Tree) control;
			}
			Control[] children = ((Composite) control).getChildren();
			for (int i = 0; i < children.length; i++) {
				if (children[i] instanceof Tree) {
					return (Tree) children[i];
				} else if (children[i] instanceof Composite) {
					Tree tree = getTreeInControl(children[i]);
					if (tree != null) {
						return tree;
					}
				}
			}
		}
		return null;
	}

	public static void waitForExecution() {
		TestingUtilities.processPlatformJobs();
		// join the execute thread
	  Thread thread = ExecuteAction.getRunner();
		if (thread != null) {
		  while (thread.isAlive()) {
		    Display.getCurrent().readAndDispatch();
		}
		}
		processDebugEvents();
	}

	private static boolean displayEventProcessed = false;
	public static void processDebugEvents() {
		displayEventProcessed = false;
		// insert display event to make sure display is 
		// actually done
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				displayEventProcessed = true;
			}
		});
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		Job job = getJob("Debug Event Dispatch");
		if (job != null && job.getState() == Job.RUNNING) {
			try {
				job.join();
			} catch (InterruptedException e) {
			}
		}
		while(!displayEventProcessed) {
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		}
	}

	public static void removeAllBreakpoints() {
		IBreakpoint[] breakpoints = DebugPlugin.getDefault()
				.getBreakpointManager().getBreakpoints();
		for (int i = 0; i < breakpoints.length; i++) {
			try {
				breakpoints[i].delete();
			} catch (CoreException e) {
			}
		}
	}

	public static void debugTest() {
		int i = 0;
		while (i == 0) {
			while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
				;
		}
	}

	public static void stepOver(ComponentInstance_c engine, int stepCount) {
		showDebugView();
		IProcess processForEngine = getProcessForEngine(engine);
		for (int i = 0; i < stepCount; i++) {
			try {
				processForEngine.getLaunch().getDebugTarget().getThreads()[0]
						.stepOver();
			} catch (DebugException e) {
			}
		}
		waitForBPThreads(getSystemForElement(engine));
	}

	public static void resume(ComponentInstance_c engine) {
		showDebugView();
		IProcess processForEngine = getProcessForEngine(engine);
		try {
			processForEngine.getLaunch().getDebugTarget().getThreads()[0]
						.resume();
		} catch (DebugException e) {
		}
		waitForBPThreads(getSystemForElement(engine));
	}

	private static void showDebugView() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().showView("org.eclipse.debug.ui.DebugView");
		} catch (PartInitException e) {
		}
	}

	public static String getTopFrameText(ComponentInstance_c engine) {
		IProcess processForEngine = getProcessForEngine(engine);
		try {
			return processForEngine.getLaunch().getDebugTarget().getThreads()[0]
					.getStackFrames()[0].getName();
		} catch (DebugException e) {
		}
		return "";
	}

	public static void stepInto(ComponentInstance_c engine, int stepCount) {
		showDebugView();
		IProcess process = getProcessForEngine(engine);
		for (int i = 0; i < stepCount; i++) {
			try {
				process.getLaunch().getDebugTarget().getThreads()[0].stepInto();
			} catch (DebugException e) {
			}
		}
	}

	public static String getTreeTextRepresentation(Tree tree) {
		String result = "";
		TreeItem[] items = getAllTreeItems(tree);
		for (int i = 0; i < items.length; i++) {
			if(items[i].getData() == null || items[i].getText().equals("")) {
				continue;
			}
			String indent = getNestingLevel(items[i]);
			String simpleName = "";
			if (items[i].getData() != null) {
				simpleName = items[i].getData().getClass().getSimpleName();
			}

			if (i == items.length - 1) {
				result = result + indent + items[i].getText() + " : "
						+ simpleName;
			} else {
				result = result + indent + items[i].getText() + " : "
						+ simpleName + System.getProperty("line.separator");
			}
		}
		return result;
	}

	public static TreeItem[] getAllTreeItems(Tree tree) {
		ArrayList<TreeItem> list = new ArrayList<TreeItem>();
		TreeItem parent = tree.getItem(0);
		list.add(parent);
		TreeItem[] allTreeItems = getAllTreeItems(parent);
		for (int i = 0; i < allTreeItems.length; i++) {
			list.add(allTreeItems[i]);
		}
		return list.toArray(new TreeItem[list.size()]);
	}

	public static TreeItem[] getAllTreeItems(TreeItem parent) {
		ArrayList<TreeItem> list = new ArrayList<TreeItem>();
		TreeItem[] items = parent.getItems();
		for (int i = 0; i < items.length; i++) {
			list.add(items[i]);
			TreeItem[] childItems = getAllTreeItems(items[i]);
			for (int j = 0; j < childItems.length; j++) {
				list.add(childItems[j]);
			}
		}
		return list.toArray(new TreeItem[list.size()]);
	}

	public static String getNestingLevel(TreeItem item) {
		String space = "";
		TreeItem parent = item.getParentItem();
		while (parent != null) {
			space = space + "  ";
			parent = parent.getParentItem();
		}
		return space;
	}
	
	public static void setSelectionInDebugTree(IProcess process) {
		IDebugView dv = BPDebugUtils.openDebugView(true);
		StructuredSelection sel = new StructuredSelection(process);
		Viewer viewer = dv.getViewer();
		viewer.setSelection(sel);
		if (viewer instanceof TreeViewer) {
		  ((TreeViewer)viewer).expandAll();
		}
		UIUtil.dispatchAll();
	}
	
	public static Menu getMenuForSelectionInDebugView() {
		IDebugView dv = BPDebugUtils.openDebugView(true);
		Viewer viewer = dv.getViewer();
		if (viewer instanceof TreeViewer) {
			return ((TreeViewer)viewer).getTree().getMenu();
		}
		else {
			return null;
		}
	}
	
	/**
	 * 
     * Terminates a verifier session and waits for the verifier thread to 
     * exit before returning.  This functionality is present in the 
     * tearDown() routine of almost every verifier test suite.  
     * This removes the duplication and provides consistency for 
     * this behavior.
     * 
	 * @param sys
	 * @param projectName
	 */
	public static void stopSession(SystemModel_c sys, String projectName) {
		// terminate all launches
		DebugUITestUtilities.terminateAllProcesses(sys);
		// remove all launch configurations
		DebugUITestUtilities.removeAllConfigurations();
		// clear the any console output
		DebugUITestUtilities.clearConsoleOutput();
		DebugUITestUtilities.clearDebugView();
		// remove all breakpoints
		DebugUITestUtilities.removeAllBreakpoints();
		// wait for display events to complete
		TestingUtilities.processDisplayEvents();
		
		TestingUtilities.waitForThread("Verifier (" + projectName + ")");	
	}
	
	public static void makeUpgradedDomainsExecutable(String [] domNames, SystemModel_c sys) {
    if (domNames.length > 0) {
      TransactionManager tm = TransactionManager.getSingleton();
      Transaction tr = null;
      Package_c domContainer = Package_c.getOneEP_PKGOnR1401(sys,
          new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate) {
              return ((Package_c) candidate).getName().equals("Domains");
            }
          });
      if (domContainer == null) {
        try {
          tr = tm.startTransaction("Make package for executable domains ",
              new ModelElement[] { sys });
          sys.Newpackage();
          domContainer = Package_c.getOneEP_PKGOnR1401(sys,
              new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                  return ((Package_c) candidate).getName().equals(
                      "Unnamed Package");
                }
              });
          domContainer.setName("Domains");
          tm.endTransaction(tr);
          TestingUtilities.allowJobCompletion();
          TestingUtilities.processPlatformJobs();
          Assert.assertNotNull(
            "Domain container package not found and cannot be created.",
            domContainer);
          for (final String domName : domNames) {
            Package_c domPkg = Package_c.getOneEP_PKGOnR1401(sys,
              new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                  return ((Package_c) candidate).getName().equals(domName);
                }
              });
            Assert.assertNotNull(domPkg);
            ExplorerView ev = BaseTest.getExplorerView();
            Selection.getInstance().setSelection(new StructuredSelection(domPkg));
            Assert.assertNotNull("Error opening Explorer View.", ev);
            Menu mnu = ev.getTreeViewer().getTree().getMenu();
            Assert.assertNotNull(
              "Error opening Menu in Explorer View on domain package.", mnu);
            MenuItem itm = UITestingUtilities.getMenuItem(mnu, "Cut");
            Assert.assertNotNull(
              "Cut not available in Explorer View on domain package.", itm);
            UITestingUtilities.activateMenuItem(itm); // Cut the domain
            TestingUtilities.allowJobCompletion();
            TestingUtilities.processPlatformJobs();
            TransactionUtil.TransactionGroup tg = TransactionUtil
            .startTransactionsOnSelectedModelRoots("New Component");
            domContainer.Newcomponent();
            Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c
              .getManyPE_PEsOnR8000(domContainer), new ClassQueryInterface_c() {
              public boolean evaluate(Object candidate) {
                return ((Component_c) candidate).getName().equals(
                  "Unnamed Component");
              }
            });
            Assert.assertNotNull("Component cant be created for domain", comp);
            TransactionUtil.endTransactions(tg);
            TestingUtilities.allowJobCompletion();
            TestingUtilities.processPlatformJobs();
            tg = TransactionUtil
            .startTransactionsOnSelectedModelRoots("Rename Component");
            comp.setName(domName);
            TransactionUtil.endTransactions(tg);
            TestingUtilities.allowJobCompletion();
            TestingUtilities.processPlatformJobs();
            Selection.getInstance().setSelection(new StructuredSelection(comp));
            mnu = ev.getTreeViewer().getTree().getMenu();
            Assert.assertNotNull(
                  "Error opening Menu in Explorer View on component created for domain.",
                  mnu);
            itm = UITestingUtilities.getMenuItem(mnu, "Paste");
            Assert.assertNotNull(
                  "Paste not available in Explorer View on component created for domain.",
                  itm);
            UITestingUtilities.activateMenuItem(itm); // Paste the domain
            TestingUtilities.allowJobCompletion();
            TestingUtilities.processPlatformJobs();
          }
        }
        catch (TransactionException te) {
          Assert.fail("Transaction Exception making domains executable" + te);
        }
      }
    }
	}
	
	public static void waitForElementsToStart(int expectedNumber) {
		while(getTotalThreads(BPDebugTarget.getTargets()) != expectedNumber) {
			waitForExecution();
		}
	}
	
	public static int getTotalThreads(ArrayList<BPDebugTarget> targets) {
		int result = 0;
		try {
		  for(BPDebugTarget target:targets) {
			result += target.getThreads().length;
		  }
		}
		catch (DebugException de){
			Assert.fail("Unexpected exception waiting for threads to start " + de.toString());
		}
		return result;
	}

}
