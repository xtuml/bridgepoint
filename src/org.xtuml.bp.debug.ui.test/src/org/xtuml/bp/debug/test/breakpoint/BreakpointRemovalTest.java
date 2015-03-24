package org.xtuml.bp.debug.test.breakpoint;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.actions.RelaunchActionDelegate;
import org.eclipse.debug.internal.ui.viewers.model.provisional.TreeModelViewer;
import org.eclipse.debug.internal.ui.views.breakpoints.BreakpointContainer;
import org.eclipse.debug.internal.ui.views.breakpoints.BreakpointsView;
import org.eclipse.debug.internal.ui.views.breakpoints.BreakpointsViewer;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.debug.ui.IDebugView;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.ComponentInstance_c;
import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.CreationTransition_c;
import org.xtuml.bp.core.ExecutableProperty_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.InterfaceReference_c;
import org.xtuml.bp.core.InterfaceSignal_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.LocalEvent_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.NewStateTransition_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Port_c;
import org.xtuml.bp.core.RequiredExecutableProperty_c;
import org.xtuml.bp.core.RequiredSignal_c;
import org.xtuml.bp.core.SemEvent_c;
import org.xtuml.bp.core.StateEventMatrixEntry_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.perspective.BridgePointPerspective;
import org.xtuml.bp.core.util.UIUtil;
import org.xtuml.bp.debug.ui.BPDebugUIPlugin;
import org.xtuml.bp.debug.ui.IBPDebugUIPluginConstants;
import org.xtuml.bp.debug.ui.launch.BPDebugUtils;
import org.xtuml.bp.debug.ui.model.BPDebugTarget;
import org.xtuml.bp.debug.ui.test.DebugUITestUtilities;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.text.activity.ActivityEditor;

public class BreakpointRemovalTest extends BaseTest {

	private static String projectName = "GPS Watch";
	private boolean initialized = false;

	public BreakpointRemovalTest(String testName) throws Exception {
		super(projectName, testName);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		if (!initialized) {
			CorePlugin.disableParseAllOnResourceChange();

			int length = BPDebugTarget.getTargets().size();
			for (int i = 0; i < length; i++) {
				BPDebugTarget.getTargets().get(i).terminate();
				DebugUITestUtilities.processDebugEvents();

			}

			// set perspective switch dialog on launch
			DebugUIPlugin
			.getDefault()
			.getPluginPreferences()
			.setValue(
					IDebugUIConstants.PLUGIN_ID
					+ ".switch_to_perspective", "always");

			DebugUITestUtilities.processDebugEvents();

			loadProject(projectName);
			PersistableModelComponent sys_comp = m_sys
					.getPersistableComponent();
			sys_comp.loadComponentAndChildren(new NullProgressMonitor());

			CorePlugin.enableParseAllOnResourceChange();

			TestingUtilities.allowJobCompletion();
			while (!ResourcesPlugin.getWorkspace().getRoot()
					.isSynchronized(IProject.DEPTH_INFINITE)) {
				ResourcesPlugin
				.getWorkspace()
				.getRoot()
				.refreshLocal(IProject.DEPTH_INFINITE,
						new NullProgressMonitor());
				while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
					;
			}

			Ooaofooa.setPersistEnabled(true);

			initialized = true;
		}
	}

	public void tearDown() throws Exception {
		DebugUITestUtilities.stopSession(m_sys, projectName);
	}

	public void testBreakpointOnStateMachineStateIsNotHitAfterRemovalFromBreakPointView() {
		ModelClass_c workoutTimerModelClass = ModelClass_c.getOneO_OBJOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(Package_c
						.getManyEP_PKGsOnR1405(m_sys)),
						new ModelClass_by_name_c("WorkoutTimer"));
		assertNotNull(workoutTimerModelClass);
		InstanceStateMachine_c ism = InstanceStateMachine_c.getOneSM_ISMOnR518(workoutTimerModelClass);
				
		StateMachineState_c state = StateMachineState_c.getOneSM_STATEOnR501(
				StateMachine_c.getOneSM_SMOnR517(ism),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName()
								.equals("running");
					}

				});
		assertNotNull(state);

		setAndConfirmBreakpoint(state);

		Package_c sysPackage = Package_c.getOneEP_PKGOnR1405(m_sys,
				new Package_by_name_c("System"));
		assertNotNull(sysPackage);

		openPerspectiveAndView(
				"org.xtuml.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);
		Selection.getInstance().setSelection(
				new StructuredSelection(sysPackage));

		Menu menu = m_bp_tree.getControl().getMenu();
		assertTrue(
				"The Launch Verifier action was not present for a component.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Launch Verifier", "", false));
		MenuItem launchVerifierItem = DebugUITestUtilities
				.getLaunchVerifierItem(menu);
		assertNotNull(launchVerifierItem);
		TestUtil.debugToDialog(200);
		launchVerifierItem.notifyListeners(SWT.Selection, null);
		TestingUtilities.processDisplayEvents();

		menu = m_bp_tree.getControl().getMenu();
		assertFalse(
				"The Launch Verifier action was present for an unassigned imported component.",
				UITestingUtilities.menuItemExists(menu, "", "Launch Verifier"));

		Component_c trackingComp = Component_c.getOneC_COnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(Package_c
						.getManyEP_PKGsOnR1405(m_sys)),
						new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Component_c) candidate).getName().equals(
								"Tracking");
					}

				});

		Port_c uiPort = Port_c.getOneC_POOnR4010(trackingComp,
				new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((Port_c) candidate).getName().equals("UI");
			}

		});

		RequiredSignal_c reqSig = RequiredSignal_c
				.getOneSPR_RSOnR4502(RequiredExecutableProperty_c.getOneSPR_REPOnR4500(ExecutableProperty_c
						.getOneC_EPOnR4003(Interface_c
								.getManyC_IsOnR4012(InterfaceReference_c
										.getManyC_IRsOnR4016(uiPort)),
										new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								return ((ExecutableProperty_c) candidate)
										.getName().equals(
												"startStopPressed");
							}

						})));

		DebugUITestUtilities.waitForExecution();
		DebugUITestUtilities.waitForBPThreads(m_sys);
		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
			;

		openPerspectiveAndView(
				"org.xtuml.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);

		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(reqSig);
		BPDebugUtils.executeElement(reqSig);

		BPDebugUtils.openSessionExplorerView(true);
		DebugUITestUtilities.waitForExecution();

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);
 
		ILaunch launch = BPDebugTarget.getTargets().get(0).getLaunch();

		PackageableElement_c[] elems = PackageableElement_c
				.getManyPE_PEsOnR8000(sysPackage);
		ComponentReference_c[] compRefs = ComponentReference_c
				.getManyCL_ICsOnR8001(elems);
		ComponentReference_c compRef = compRefs[2];
		ComponentInstance_c engine = ComponentInstance_c
				.getOneI_EXEOnR2963(compRef);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		IProcess process = DebugUITestUtilities.getProcessForEngine(engine);
		assertNotNull(process);

		boolean isSuspended = false;
		while (!isSuspended) {
			PlatformUI.getWorkbench().getDisplay().readAndDispatch();
			DebugUITestUtilities.processDebugEvents();
			DebugUITestUtilities.waitForExecution();
			DebugUITestUtilities.waitForBPThreads(m_sys);
			IDebugTarget target = process.getLaunch().getDebugTarget();
			isSuspended = target.isSuspended();
		}
		assertTrue(
				"Process was not suspended by breakpoint in interface operation.",
				isSuspended);

		String stackTrace = DebugUITestUtilities.getStackTrace(process, engine);
		assertTrue(
				"Unexpected suspend state, expected stack trace not found.",
				stackTrace
				.equals("WorkoutTimer1: startStopPressed line: 1\r\nWorkoutTimer1: startStopPressed"));

		try {
			BPDebugTarget.getTargets().get(0).terminate();
		} catch (DebugException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DebugUITestUtilities.processDebugEvents();
		TestingUtilities.processPlatformJobs();
		DebugUITestUtilities.waitForExecution();
		DebugUITestUtilities.waitForBPThreads(m_sys);

		RelaunchActionDelegate.relaunch(launch.getLaunchConfiguration(),
				launch.getLaunchMode());

		DebugUITestUtilities.processDebugEvents();
		TestingUtilities.processPlatformJobs();
		DebugUITestUtilities.waitForExecution();
		DebugUITestUtilities.waitForBPThreads(m_sys);

		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(reqSig);
		BPDebugUtils.executeElement(reqSig);

		DebugUITestUtilities.waitForExecution();
		DebugUITestUtilities.waitForBPThreads(m_sys);

		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
			;

		IViewPart tempView = null;

		IWorkbenchPage dbgPage;
		try {
			dbgPage = PlatformUI.getWorkbench().showPerspective(
					IBPDebugUIPluginConstants.BPPERSPECTIVE_ID,
					PlatformUI.getWorkbench().getActiveWorkbenchWindow());

			if (dbgPage != null) {

				tempView = dbgPage
						.showView(IBPDebugUIPluginConstants.ID_VIEW_BREAKPOINT);

			}
		} catch (WorkbenchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DebugPlugin.getDefault().getBreakpointManager()
			.getBreakpoints(BPDebugUIPlugin.getUniqueIdentifier())[0]
					.setEnabled(false);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		engine = ComponentInstance_c.getOneI_EXEOnR2963(compRef);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		process = DebugUITestUtilities.getProcessForEngine(engine);
		assertNotNull(process);

		DebugUITestUtilities.resume(engine);
		DebugUITestUtilities.waitForExecution();
	    
		int wait = 5000;
		while (--wait>0)
		{
			PlatformUI.getWorkbench().getDisplay().readAndDispatch();
		}
          
	    
		engine = ComponentInstance_c.getOneI_EXEOnR2963(compRef);
		process = DebugUITestUtilities.getProcessForEngine(engine);
		IDebugTarget target = process.getLaunch().getDebugTarget();

		assertFalse("Process was not suspended by breakpoint in state.",
				target.isSuspended());
		DebugUITestUtilities.waitForExecution();

	}
	public void testBreakpointOnStateMachineEventIsNotHitAfterRemovalFromBreakPointView() {
		ModelClass_c displayModelClass = ModelClass_c.getOneO_OBJOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(Package_c
						.getManyEP_PKGsOnR1405(m_sys)),
						new ModelClass_by_name_c("Display"));
		assertNotNull(displayModelClass);
		ClassStateMachine_c csm = ClassStateMachine_c
				.getOneSM_ASMOnR519(displayModelClass);
		StateMachineEvent_c evt = StateMachineEvent_c.getOneSM_EVTOnR502(
				StateMachine_c.getOneSM_SMOnR517(csm),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineEvent_c) candidate).getMning()
								.equals("refresh");
					}

				});
		assertNotNull(evt);

		setAndConfirmBreakpoint(evt);

		Package_c sysPackage = Package_c.getOneEP_PKGOnR1405(m_sys,
				new Package_by_name_c("System"));
		assertNotNull(sysPackage);

		openPerspectiveAndView(
				"org.xtuml.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);
		Selection.getInstance().setSelection(
				new StructuredSelection(sysPackage));

		Menu menu = m_bp_tree.getControl().getMenu();
		assertTrue(
				"The Launch Verifier action was not present for a component.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Launch Verifier", "", false));
		MenuItem launchVerifierItem = DebugUITestUtilities
				.getLaunchVerifierItem(menu);
		assertNotNull(launchVerifierItem);
		TestUtil.debugToDialog(200);
		launchVerifierItem.notifyListeners(SWT.Selection, null);
		TestingUtilities.processDisplayEvents();

		menu = m_bp_tree.getControl().getMenu();
		assertFalse(
				"The Launch Verifier action was present for an unassigned imported component.",
				UITestingUtilities.menuItemExists(menu, "", "Launch Verifier"));

		Component_c trackingComp = Component_c.getOneC_COnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(Package_c
						.getManyEP_PKGsOnR1405(m_sys)),
						new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Component_c) candidate).getName().equals(
								"Tracking");
					}

				});

		Port_c uiPort = Port_c.getOneC_POOnR4010(trackingComp,
				new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((Port_c) candidate).getName().equals("UI");
			}

		});

		RequiredSignal_c reqSig = RequiredSignal_c
				.getOneSPR_RSOnR4502(RequiredExecutableProperty_c.getOneSPR_REPOnR4500(ExecutableProperty_c
						.getOneC_EPOnR4003(Interface_c
								.getManyC_IsOnR4012(InterfaceReference_c
										.getManyC_IRsOnR4016(uiPort)),
										new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								return ((ExecutableProperty_c) candidate)
										.getName().equals(
												"startStopPressed");
							}

						})));

		DebugUITestUtilities.waitForExecution();
		DebugUITestUtilities.waitForBPThreads(m_sys);
		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
			;

		openPerspectiveAndView(
				"org.xtuml.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);

		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(reqSig);
		BPDebugUtils.executeElement(reqSig);

		BPDebugUtils.openSessionExplorerView(true);
		DebugUITestUtilities.waitForExecution();

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);
 
		ILaunch launch = BPDebugTarget.getTargets().get(0).getLaunch();

		PackageableElement_c[] elems = PackageableElement_c
				.getManyPE_PEsOnR8000(sysPackage);
		ComponentReference_c[] compRefs = ComponentReference_c
				.getManyCL_ICsOnR8001(elems);
		ComponentReference_c compRef = compRefs[2];
		ComponentInstance_c engine = ComponentInstance_c
				.getOneI_EXEOnR2963(compRef);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		IProcess process = DebugUITestUtilities.getProcessForEngine(engine);
		assertNotNull(process);

		boolean isSuspended = false;
		while (!isSuspended) {
			PlatformUI.getWorkbench().getDisplay().readAndDispatch();
			DebugUITestUtilities.processDebugEvents();
			DebugUITestUtilities.waitForExecution();
			DebugUITestUtilities.waitForBPThreads(m_sys);
			IDebugTarget target = process.getLaunch().getDebugTarget();
			isSuspended = target.isSuspended();
		}
		assertTrue(
				"Process was not suspended by breakpoint in interface operation.",
				isSuspended);

		String stackTrace = DebugUITestUtilities.getStackTrace(process, engine);
		assertTrue(
				"Unexpected suspend state, expected stack trace not found.",
				stackTrace
				.equals("Display::displayDistance line: 1\r\nDisplay_A2: refresh\r\nDisplay_A2: refresh\r\nWorkoutTimer3: tick delayed by 1000000 uS"));

		try {
			BPDebugTarget.getTargets().get(0).terminate();
		} catch (DebugException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DebugUITestUtilities.processDebugEvents();
		TestingUtilities.processPlatformJobs();
		DebugUITestUtilities.waitForExecution();
		DebugUITestUtilities.waitForBPThreads(m_sys);

		RelaunchActionDelegate.relaunch(launch.getLaunchConfiguration(),
				launch.getLaunchMode());

		DebugUITestUtilities.processDebugEvents();
		TestingUtilities.processPlatformJobs();
		DebugUITestUtilities.waitForExecution();
		DebugUITestUtilities.waitForBPThreads(m_sys);

		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(reqSig);
		BPDebugUtils.executeElement(reqSig);

		DebugUITestUtilities.waitForExecution();
		DebugUITestUtilities.waitForBPThreads(m_sys);

		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
			;

		IViewPart tempView = null;

		IWorkbenchPage dbgPage;
		try {
			dbgPage = PlatformUI.getWorkbench().showPerspective(
					IBPDebugUIPluginConstants.BPPERSPECTIVE_ID,
					PlatformUI.getWorkbench().getActiveWorkbenchWindow());

			if (dbgPage != null) {

				tempView = dbgPage
						.showView(IBPDebugUIPluginConstants.ID_VIEW_BREAKPOINT);

			}
		} catch (WorkbenchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DebugPlugin.getDefault().getBreakpointManager()
			.getBreakpoints(BPDebugUIPlugin.getUniqueIdentifier())[0]
					.setEnabled(false);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		engine = ComponentInstance_c.getOneI_EXEOnR2963(compRef);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		process = DebugUITestUtilities.getProcessForEngine(engine);
		assertNotNull(process);

		DebugUITestUtilities.resume(engine);
		DebugUITestUtilities.waitForExecution();
	    
        int wait = 5000;   
		while (--wait>0)
		{ 
			PlatformUI.getWorkbench().getDisplay().readAndDispatch();
	    }
		 
          
		DebugUITestUtilities.waitForBPThreads(m_sys);
		engine = ComponentInstance_c.getOneI_EXEOnR2963(compRef);
		process = DebugUITestUtilities.getProcessForEngine(engine);
		IDebugTarget target = process.getLaunch().getDebugTarget();

		assertFalse("Process was not suspended by breakpoint in state.",
				target.isSuspended());
		DebugUITestUtilities.waitForExecution();

	}

	private void setAndConfirmBreakpoint(NonRootModelElement nrme) {
		IBreakpointManager bpm = DebugPlugin.getDefault()
				.getBreakpointManager();
		IBreakpoint[] beforeBps = bpm.getBreakpoints();
		int beforeBpsLength = beforeBps.length;
		Selection.getInstance().clear();
		Selection.getInstance().setSelection(new StructuredSelection(nrme));
		Menu menu = m_bp_tree.getControl().getMenu();
		UITestingUtilities.checkItemStatusInContextMenu(menu, "Set Breakpoint",
				"", false);

		MenuItem setBreakPoint = null;
		MenuItem[] items = menu.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i].getText().equals("Set Breakpoint")) {
				setBreakPoint = items[i];
				break;
			}
		}

		assertNotNull(setBreakPoint);
		setBreakPoint.notifyListeners(SWT.Selection, null);

		IBreakpoint[] bps = bpm.getBreakpoints();
		assertTrue("Breakpoint not set", bps.length == beforeBpsLength + 1);

	}
}