//=====================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2008-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================
package com.mentor.nucleus.bp.debug.ui.test.execute;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.ProvidedExecutableProperty_c;
import com.mentor.nucleus.bp.core.ProvidedSignal_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.RequiredExecutableProperty_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.RequiredSignal_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.perspective.BridgePointPerspective;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.debug.ui.launch.BPDebugUtils;
import com.mentor.nucleus.bp.debug.ui.test.DebugUITestUtilities;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.session.views.SessionExplorerView;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;

public class VerifierExecuteActionTests extends BaseTest {

	private static String projectName = "VerifierLaunchConfigurationTests";

	private boolean initialized = false;

	public VerifierExecuteActionTests(String testName) throws Exception {
		super(null, testName);
	}

	@Override
	protected void setUp() throws Exception {
		if (!initialized)
		  delayGlobalUpgrade = true;
		super.setUp();

		if (!initialized) {
			BaseTest.dispatchEvents(0);
			CorePlugin.disableParseAllOnResourceChange();

			// set perspective switch dialog on launch
			DebugUIPlugin.getDefault().getPluginPreferences().setValue(
					IDebugUIConstants.PLUGIN_ID + ".switch_to_perspective",
					"always");

			// initialize test model
			final IProject project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(projectName);

			loadProject(projectName);

			DebugUITestUtilities.processDebugEvents();

			m_sys = SystemModel_c.SystemModelInstance(Ooaofooa
					.getDefaultInstance(), new ClassQueryInterface_c() {

				public boolean evaluate(Object candidate) {
					return ((SystemModel_c) candidate).getName().equals(
							project.getName());
				}

			});

			PersistableModelComponent sys_comp = m_sys
					.getPersistableComponent();
			sys_comp.loadComponentAndChildren(new NullProgressMonitor());

			CorePlugin.enableParseAllOnResourceChange();

			TestingUtilities.allowJobCompletion();
			while (!ResourcesPlugin.getWorkspace().getRoot().isSynchronized(
					IProject.DEPTH_INFINITE)) {
				ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
						IProject.DEPTH_INFINITE, new NullProgressMonitor());
				while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
					;
			}

			Ooaofooa.setPersistEnabled(true);
            delayGlobalUpgrade = false;
			initialized = true;
		}
	}

	public void tearDown() throws Exception {
		DebugUITestUtilities.stopSession(m_sys, projectName);
	}

	public void testExecuteClassBasedOperationOAL() {
		Package_c domain = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("domain");
			}
		});
		Package_c container = Package_c.getOneEP_PKGOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8003(Component_c
						.getManyC_CsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(domain))),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals("ss");
					}
				});
		domain = container;
		Operation_c operation = Operation_c.getOneO_TFROnR115(ModelClass_c
				.getManyO_OBJsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(domain)), new ClassQueryInterface_c() {
				
					public boolean evaluate(Object candidate) {
						return ((Operation_c)candidate).getName().equals("op");
					}
				
				});
		assertNotNull(operation);

		// launch the domain
		DebugUITestUtilities.launchElement(Component_c
						.getOneC_COnR8003(PackageableElement_c
								.getOnePE_PEOnR8001(container)), m_bp_tree.getControl()
				.getMenu());
		DebugUITestUtilities.waitForElementsToStart(1);

        openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
        
        BPDebugUtils.setSelectionInSETree(new StructuredSelection(operation));
        Menu menu = DebugUITestUtilities.getMenuInSETree(operation);
        assertTrue(
                "The execute menu item was not available for a class operation.",
                UITestingUtilities.checkItemStatusInContextMenu(menu,
                        "Execute", "", false));

		ActivityEditor editor = DebugUITestUtilities
				.openActivityEditorForSelectedElement();

		DebugUITestUtilities.setBreakpointAtLine(editor, 2);
		
		BPDebugUtils.openSessionExplorerView(true);

		UITestingUtilities.activateMenuItem(menu, "Execute");

		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c
				.getOneI_EXEOnR2955(Component_c
						.getOneC_COnR8003(PackageableElement_c
								.getOnePE_PEOnR8001(container)));
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);
		
		IProcess process = DebugUITestUtilities.getProcessForEngine(engine);
		assertNotNull(process);
		
		DebugUITestUtilities.processDebugEvents();
		
		IDebugTarget target = process.getLaunch().getDebugTarget();
		assertTrue(
				"Process was not suspended by breakpoint in class based operation.",
				target.isSuspended());

		String stackTrace = DebugUITestUtilities.getStackTrace(process, engine);
		assertTrue("Unexpected suspend state, expected stack trace not found.",
				stackTrace.equals("class::op line: 2"));		
	}

	public void testExecuteClassBasedOperationNoOAL() {
		Package_c domain = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("domain");
			}
		});
		Package_c container = Package_c.getOneEP_PKGOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8003(Component_c
						.getManyC_CsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(domain))),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals("ss");
					}
				});
		domain = container;		
		assertNotNull(domain);

		Operation_c operation = Operation_c.getOneO_TFROnR115(ModelClass_c
				.getManyO_OBJsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(domain)), new ClassQueryInterface_c() {
				
					public boolean evaluate(Object candidate) {
						return ((Operation_c)candidate).getName().equals("opNoOAL");
					}
				
				});
		assertNotNull(operation);

		// launch the domain
		DebugUITestUtilities.launchElement(Component_c
						.getOneC_COnR8003(PackageableElement_c
								.getOnePE_PEOnR8001(container)), m_bp_tree.getControl()
				.getMenu());
		DebugUITestUtilities.waitForElementsToStart(1);

		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
        BPDebugUtils.setSelectionInSETree(new StructuredSelection(operation));

		Menu menu = DebugUITestUtilities.getMenuInSETree(operation);

		assertTrue(
				"The execute menu item was not available for a class operation.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Execute", "", false));

		UITestingUtilities.activateMenuItem(menu, "Execute");

		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c
				.getOneI_EXEOnR2955(Component_c
						.getOneC_COnR8003(PackageableElement_c
								.getOnePE_PEOnR8001(container)));
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		TestingUtilities.processDisplayEvents();
		String consoleText = DebugUITestUtilities.getConsoleText();
		final String expectedText = "User invoked operation: opNoOAL failed, no statements to execute. Check for OAL problems.";
		boolean textFound = (consoleText.indexOf(expectedText) != -1);
				
		for (int i = 0; i < 10 && !textFound; i++) {
			// wait for the execution to complete
			DebugUITestUtilities.waitForBPThreads(m_sys);

			TestingUtilities.processDisplayEvents();
			consoleText = DebugUITestUtilities.getConsoleText();
			textFound = (consoleText.indexOf(expectedText) != -1);
		}
		assertTrue(
				"Expected console warning not found when executing an operation with no OAL.",
				textFound);
	}
	
	public void testExecuteClassBasedOperationWithParameters() {
		Package_c domain = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("domain");
			}
		});
		Package_c container = Package_c.getOneEP_PKGOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8003(Component_c
						.getManyC_CsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(domain))),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals("ss");
					}
				});
		domain = container;
		assertNotNull(domain);

		Operation_c operation = Operation_c.getOneO_TFROnR115(ModelClass_c
				.getManyO_OBJsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(domain)), new ClassQueryInterface_c() {
				
					public boolean evaluate(Object candidate) {
						return ((Operation_c)candidate).getName().equals("opWithParams");
					}
				
				});
		assertNotNull(operation);

		// launch the domain
		DebugUITestUtilities.launchElement(Component_c
						.getOneC_COnR8003(PackageableElement_c
								.getOnePE_PEOnR8001(container)), m_bp_tree.getControl()
				.getMenu());
		DebugUITestUtilities.waitForElementsToStart(1);

        openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
        		
        BPDebugUtils.setSelectionInSETree(new StructuredSelection(operation));

        Menu menu = DebugUITestUtilities.getMenuInSETree(operation);

		ActivityEditor editor = DebugUITestUtilities
			.openActivityEditorForSelectedElement();

		DebugUITestUtilities.setBreakpointAtLine(editor, 1);
		
		assertTrue(
				"The execute menu item was not available for a class operation.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Execute", "", false));

		BPDebugUtils.openSessionExplorerView(true);

		UITestingUtilities.activateMenuItem(menu, "Execute");

		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c
				.getOneI_EXEOnR2955(Component_c
						.getOneC_COnR8003(PackageableElement_c
								.getOnePE_PEOnR8001(container)));
		assertNotNull(engine);
		
		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);
		
		IProcess process = DebugUITestUtilities.getProcessForEngine(engine);
		assertNotNull(process);

		DebugUITestUtilities.processDebugEvents();
		
		IDebugTarget target = process.getLaunch().getDebugTarget();
		assertTrue(
				"Process was not suspended by breakpoint in class based operation.",
				target.isSuspended());

		String stackTrace = DebugUITestUtilities.getStackTrace(process, engine);
		assertTrue("Unexpected suspend state, expected stack trace not found.",
				stackTrace.equals("class::opWithParams line: 2"));

		// Commenting out known failure tests.  See dts0100656068
/*		String xValue = DebugUITestUtilities.getValueForVariable("x");
		assertEquals("Default parameter value was not as expected for variable x.", "0", xValue);
		String yValue = DebugUITestUtilities.getValueForVariable("y");
		assertEquals("Default parameter value was not as expected for variable y.", "0", yValue);
*/	}
	
	public void testExecuteSignalAssignedToTransition() {
		Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(m_sys)), new ClassQueryInterface_c() {
		
			public boolean evaluate(Object candidate) {
				return ((Component_c) candidate).getName().equals("fc1");
			}
		
		});
		assertNotNull(component);
		Ooaofooa root = Ooaofooa
				.getInstance("/VerifierLaunchConfigurationTests/models/VerifierLaunchConfigurationTests/cp/cp.xtuml");
		StateMachineState_c state = StateMachineState_c.StateMachineStateInstance(root, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((StateMachineState_c) candidate).getName().equals("State 2");
			}
		});
		assertNotNull(state);

		// launch the domain
		DebugUITestUtilities.launchElement(component, m_bp_tree.getControl()
				.getMenu());
		DebugUITestUtilities.waitForElementsToStart(1);
		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective", BridgePointPerspective.ID_MGC_BP_EXPLORER);

        BPDebugUtils.setSelectionInSETree(new StructuredSelection(state));

		ProvidedSignal_c proSignal = ProvidedSignal_c
			.getOneSPR_PSOnR4503(ProvidedExecutableProperty_c
					.getManySPR_PEPsOnR4501(Provision_c
							.getManyC_PsOnR4009(InterfaceReference_c
									.getManyC_IRsOnR4016(Port_c
											.getManyC_POsOnR4010(component, new ClassQueryInterface_c() {
									
												public boolean evaluate(Object candidate) {
													return ((Port_c)candidate).getName().equals("Port1");
												}
									
											})))), new ClassQueryInterface_c() {
									
											public boolean evaluate(Object candidate) {
												return ((ProvidedSignal_c)candidate).getName().equals("clientserversig");
											}
									
										});
		assertNotNull(proSignal);
		
		ActivityEditor editor = DebugUITestUtilities
			.openActivityEditorForSelectedElement();

		DebugUITestUtilities.setBreakpointAtLine(editor, 2);

        BPDebugUtils.setSelectionInSETree(new StructuredSelection(proSignal));

		Menu menu = DebugUITestUtilities.getMenuInSETree(proSignal);
		assertTrue(
			"The execute menu item was not available for a provided signal assigned to a transition.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Execute", "", false));

        BPDebugUtils.setSelectionInSETree(new StructuredSelection(proSignal));
		
		UITestingUtilities.activateMenuItem(menu, "Execute");

		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(component);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);
		
		IProcess process = DebugUITestUtilities.getProcessForEngine(engine);
		assertNotNull(process);

		DebugUITestUtilities.processDebugEvents();
		
		IDebugTarget target = process.getLaunch().getDebugTarget();
		assertTrue(
				"Process was not suspended by breakpoint in state.",
				target.isSuspended());

		String stackTrace = DebugUITestUtilities.getStackTrace(process, engine);
		assertTrue("Unexpected suspend state, expected stack trace not found.",
				stackTrace.equals("class::State 2 line: 2" + System.getProperty("line.separator") + "Port1::clientserversig"));
	}
	
	public void testExecuteSignalAssignedToTransitionNoOAL() {
		// same exact test as the last, just remove the OAL in
		// the signal
		Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(m_sys)), new ClassQueryInterface_c() {
			
			public boolean evaluate(Object candidate) {
				return ((Component_c) candidate).getName().equals("fc1");
			}
		
		});
		assertNotNull(component);
		
		ProvidedSignal_c proSignal = ProvidedSignal_c
			.getOneSPR_PSOnR4503(ProvidedExecutableProperty_c
				.getManySPR_PEPsOnR4501(Provision_c
						.getManyC_PsOnR4009(InterfaceReference_c
								.getManyC_IRsOnR4016(Port_c
										.getManyC_POsOnR4010(component, new ClassQueryInterface_c() {
										
											public boolean evaluate(Object candidate) {
												return ((Port_c)candidate).getName().equals("Port1");
											}
										
										})))), new ClassQueryInterface_c() {
										
											public boolean evaluate(Object candidate) {
												return ((ProvidedSignal_c)candidate).getName().equals("clientserversig");
											}
										
										});
		assertNotNull(proSignal);
		
        BPDebugUtils.setSelectionInSETree(new StructuredSelection(proSignal));
		
		ActivityEditor editor = DebugUITestUtilities
			.openActivityEditorForSelectedElement();
		editor.getDocumentProvider().getDocument(editor.getEditorInput()).set("");
		editor.doSave(new NullProgressMonitor());
		
		assertTrue("OAL was not removed from signal.", proSignal.getAction_semantics().equals(""));
		testExecuteSignalAssignedToTransition();
	}
	
	public void testExecuteSignalAssignedToTransitionWithParameters() {
		Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(m_sys)), new ClassQueryInterface_c() {
			
			public boolean evaluate(Object candidate) {
				return ((Component_c) candidate).getName().equals("fc1");
			}
		
		});
		assertNotNull(component);
		
		Ooaofooa root = Ooaofooa
				.getInstance("/VerifierLaunchConfigurationTests/models/VerifierLaunchConfigurationTests/cp/cp.xtuml");
		StateMachineState_c state = StateMachineState_c.StateMachineStateInstance(root, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((StateMachineState_c) candidate).getName().equals("State 3");
			}
		});

		assertNotNull(state);

		// launch the domain
		DebugUITestUtilities.launchElement(component, m_bp_tree.getControl()
				.getMenu());
		DebugUITestUtilities.waitForElementsToStart(1);
		ProvidedSignal_c proSignal = ProvidedSignal_c
				.getOneSPR_PSOnR4503(ProvidedExecutableProperty_c
						.getManySPR_PEPsOnR4501(Provision_c
								.getManyC_PsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(component, new ClassQueryInterface_c() {
												
													public boolean evaluate(Object candidate) {
														return ((Port_c)candidate).getName().equals("Port1");
													}
												
												})))), new ClassQueryInterface_c() {
												
													public boolean evaluate(Object candidate) {
														return ((ProvidedSignal_c)candidate).getName().equals("clientserversigparams");
													}
												
												});
		assertNotNull(proSignal);
		
		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
        BPDebugUtils.setSelectionInSETree(new StructuredSelection(state));
		
		ActivityEditor editor = DebugUITestUtilities
			.openActivityEditorForSelectedElement();

		DebugUITestUtilities.setBreakpointAtLine(editor, 2);
		
        BPDebugUtils.setSelectionInSETree(new StructuredSelection(proSignal));

		Menu menu = DebugUITestUtilities.getMenuInSETree(proSignal);

		assertTrue(
			"The execute menu item was not available for a provided signal assigned to a transition.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Execute", "", false));

        BPDebugUtils.setSelectionInSETree(new StructuredSelection(proSignal));
		
		UITestingUtilities.activateMenuItem(menu, "Execute");

		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(component);
		assertNotNull(engine);
		
		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);
		
		IProcess process = DebugUITestUtilities.getProcessForEngine(engine);
		assertNotNull(process);
		
		IDebugTarget target = process.getLaunch().getDebugTarget();
		assertTrue(
				"Process was not suspended by breakpoint in state.",
				target.isSuspended());
		
		DebugUITestUtilities.waitForBPThreads(m_sys);
		DebugUITestUtilities.waitForExecution();
		
		String stackTrace = DebugUITestUtilities.getStackTrace(process, engine);
		assertTrue("Unexpected suspend state, expected stack trace not found.", stackTrace.equals("class::State 3 line: 2"
				+ System.getProperty("line.separator")
				+ "Port1::clientserversigparams" + " (x:integer, y:integer)"));
		
		// Commenting out known failure tests.  See dts0100656068
/*		String xValue = DebugUITestUtilities.getValueForVariable("x");
		assertEquals("Default parameter value was not as expected for variable x.", "0", xValue);
		String yValue = DebugUITestUtilities.getValueForVariable("y");
		assertEquals("Default parameter value was not as expected for variable x.", "0", yValue);
*/	}
	
	public void testExecuteSignalNotAssignedToTransition() {
		Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(m_sys)), new ClassQueryInterface_c() {
			
			public boolean evaluate(Object candidate) {
				return ((Component_c) candidate).getName().equals("fc1");
			}
		
		});
		assertNotNull(component);

		// launch the domain
		DebugUITestUtilities.launchElement(component, m_bp_tree.getControl()
				.getMenu());
		DebugUITestUtilities.waitForElementsToStart(1);

		RequiredSignal_c reqSignal = RequiredSignal_c
				.getOneSPR_RSOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(Requirement_c
								.getManyC_RsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(component, new ClassQueryInterface_c() {
												
													public boolean evaluate(Object candidate) {
														return ((Port_c)candidate).getName().equals("Port2");
													}
												
												})))), new ClassQueryInterface_c() {
												
													public boolean evaluate(Object candidate) {
														return ((RequiredSignal_c)candidate).getName().equals("clientserversig");
													}
												
												});
		assertNotNull(reqSignal);
		
		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
        BPDebugUtils.setSelectionInSETree(new StructuredSelection(reqSignal));
		
		ActivityEditor editor = DebugUITestUtilities
			.openActivityEditorForSelectedElement();

		DebugUITestUtilities.setBreakpointAtLine(editor, 2);

		Menu menu = DebugUITestUtilities.getMenuInSETree(reqSignal);

		assertTrue(
			"The execute menu item was not available for a required signal not assigned to a transition.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Execute", "", false));

        BPDebugUtils.setSelectionInSETree(new StructuredSelection(reqSignal));
		
		UITestingUtilities.activateMenuItem(menu, "Execute");

		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(component);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);
		
		IProcess process = DebugUITestUtilities.getProcessForEngine(engine);
		assertNotNull(process);

		DebugUITestUtilities.processDebugEvents();
		
		IDebugTarget target = process.getLaunch().getDebugTarget();
		assertTrue(
				"Process was not suspended by breakpoint in signal.",
				target.isSuspended());
		
		String stackTrace = DebugUITestUtilities.getStackTrace(process, engine);
		assertTrue("Unexpected suspend state, expected stack trace not found.",
				stackTrace.equals("Port2::Interface::clientserversig line: 2"));
	}
	
	public void testExecuteSignalNotAssignedToTransitionNoOAL() {
		Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(m_sys)), new ClassQueryInterface_c() {
			
			public boolean evaluate(Object candidate) {
				return ((Component_c) candidate).getName().equals("fc1");
			}
		
		});
		assertNotNull(component);

		// launch the component
		DebugUITestUtilities.launchElement(component, m_bp_tree.getControl()
				.getMenu());
		DebugUITestUtilities.waitForElementsToStart(1);

		RequiredSignal_c reqSignal = RequiredSignal_c
				.getOneSPR_RSOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(Requirement_c
								.getManyC_RsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(component, new ClassQueryInterface_c() {
												
													public boolean evaluate(Object candidate) {
														return ((Port_c)candidate).getName().equals("Port2");
													}
												
												})))), new ClassQueryInterface_c() {
												
													public boolean evaluate(Object candidate) {
														return ((RequiredSignal_c)candidate).getName().equals("clientserversig");
													}
												
												});
		assertNotNull(reqSignal);
		
		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
        BPDebugUtils.setSelectionInSETree(new StructuredSelection(reqSignal));
		
		ActivityEditor editor = DebugUITestUtilities
			.openActivityEditorForSelectedElement();
		editor.getDocumentProvider().getDocument(editor.getEditorInput()).set("");
		editor.doSave(new NullProgressMonitor());
	
		assertTrue("OAL was not removed from signal.", reqSignal.getAction_semantics().equals(""));
		
		Menu menu = DebugUITestUtilities.getMenuInSETree(reqSignal);
		
		while (Display.getCurrent().readAndDispatch());
		
		assertTrue(
			"The execute menu item was not available for a required signal not assigned to a transition.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Execute", "", false));

		UITestingUtilities.activateMenuItem(menu, "Execute");

		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(component);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		
		TestingUtilities.processDisplayEvents();
		String consoleText = DebugUITestUtilities.getConsoleText();
		final String expectedText = "User invoked interface message: clientserversig failed, no statements to execute. Check for OAL problems.";
		boolean textFound = (consoleText.indexOf(expectedText) != -1);
				
		for (int i = 0; i < 10 && !textFound; i++) {
			// wait for the execution to complete
			DebugUITestUtilities.waitForBPThreads(m_sys);

			TestingUtilities.processDisplayEvents();
			consoleText = DebugUITestUtilities.getConsoleText();
			textFound = (consoleText.indexOf(expectedText) != -1);
		}
		assertTrue(
				"Expected console warning not found when executing a signal with no OAL.",
				textFound);		
	}

	public void testExecuteSignalNotAssignedToTransitionWithParameters() {
		Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(m_sys)), new ClassQueryInterface_c() {
			
			public boolean evaluate(Object candidate) {
				return ((Component_c) candidate).getName().equals("fc1");
			}
		
		});
		assertNotNull(component);

		// launch the domain
		DebugUITestUtilities.launchElement(component, m_bp_tree.getControl()
				.getMenu());
		DebugUITestUtilities.waitForElementsToStart(1);

		RequiredSignal_c reqSignal = RequiredSignal_c
				.getOneSPR_RSOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(Requirement_c
								.getManyC_RsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(component, new ClassQueryInterface_c() {
												
													public boolean evaluate(Object candidate) {
														return ((Port_c)candidate).getName().equals("Port2");
													}
												
												})))), new ClassQueryInterface_c() {
												
													public boolean evaluate(Object candidate) {
														return ((RequiredSignal_c)candidate).getName().equals("clientserversigparams");
													}
												
												});
		assertNotNull(reqSignal);
		
		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
        BPDebugUtils.setSelectionInSETree(new StructuredSelection(reqSignal));
		
		ActivityEditor editor = DebugUITestUtilities
			.openActivityEditorForSelectedElement();

		DebugUITestUtilities.setBreakpointAtLine(editor, 2);

		Menu menu = DebugUITestUtilities.getMenuInSETree(reqSignal);

		assertTrue(
			"The execute menu item was not available for a required signal not assigned to a transition.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Execute", "", false));

		UITestingUtilities.activateMenuItem(menu, "Execute");

		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(component);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);
		
		IProcess process = DebugUITestUtilities.getProcessForEngine(engine);
		assertNotNull(process);

		DebugUITestUtilities.processDebugEvents();
		
		IDebugTarget target = process.getLaunch().getDebugTarget();
		assertTrue(
				"Process was not suspended by breakpoint in signal.",
				target.isSuspended());
		
		String stackTrace = DebugUITestUtilities.getStackTrace(process, engine);
		assertTrue("Unexpected suspend state, expected stack trace not found.",
				stackTrace.equals("Port2::Interface::clientserversigparams line: 2"));
		
		// Commenting out known failure tests.  See dts0100656068
/*		String xValue = DebugUITestUtilities.getValueForVariable("x");
		assertEquals("Default parameter value was not as expected for variable x.", "0", xValue);
		String yValue = DebugUITestUtilities.getValueForVariable("y");
		assertEquals("Default parameter value was not as expected for variable x.", "0", yValue);
*/	}
	
    public void testExecuteInterfaceOperation() {
		Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(m_sys)), new ClassQueryInterface_c() {
			
			public boolean evaluate(Object candidate) {
				return ((Component_c) candidate).getName().equals("fc1");
			}
		
		});
		assertNotNull(component);

		// launch the domain
		DebugUITestUtilities.launchElement(component, m_bp_tree.getControl()
				.getMenu());
		DebugUITestUtilities.waitForElementsToStart(1);

		RequiredOperation_c reqOp = RequiredOperation_c
				.getOneSPR_ROOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(Requirement_c
								.getManyC_RsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(component, new ClassQueryInterface_c() {
												
													public boolean evaluate(Object candidate) {
														return ((Port_c)candidate).getName().equals("Port2");
													}
												
												})))), new ClassQueryInterface_c() {
												
													public boolean evaluate(Object candidate) {
														return ((RequiredOperation_c)candidate).getName().equals("clientserverop");
													}
												
												});
		assertNotNull(reqOp);
		
		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
        BPDebugUtils.setSelectionInSETree(new StructuredSelection(reqOp));
		
		ActivityEditor editor = DebugUITestUtilities
			.openActivityEditorForSelectedElement();

		DebugUITestUtilities.setBreakpointAtLine(editor, 2);

		Menu menu = DebugUITestUtilities.getMenuInSETree(reqOp);

		assertTrue(
			"The execute menu item was not available for a required operation.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Execute", "", false));

		UITestingUtilities.activateMenuItem(menu, "Execute");

		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(component);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);
		
		IProcess process = DebugUITestUtilities.getProcessForEngine(engine);
		assertNotNull(process);

		DebugUITestUtilities.processDebugEvents();
		
		IDebugTarget target = process.getLaunch().getDebugTarget();
		assertTrue(
				"Process was not suspended by breakpoint in interface operation.",
				target.isSuspended());
		
		String stackTrace = DebugUITestUtilities.getStackTrace(process, engine);
		assertTrue("Unexpected suspend state, expected stack trace not found.",
				stackTrace.equals("Port2::Interface::clientserverop line: 2"));
    }

    public void testExecuteInterfaceOperationNoOAL() {
		Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(m_sys)), new ClassQueryInterface_c() {
			
			public boolean evaluate(Object candidate) {
				return ((Component_c) candidate).getName().equals("fc1");
			}
		
		});
		assertNotNull(component);

		// launch the domain
		DebugUITestUtilities.launchElement(component, m_bp_tree.getControl()
				.getMenu());
		DebugUITestUtilities.waitForElementsToStart(1);

		RequiredOperation_c reqOp = RequiredOperation_c
				.getOneSPR_ROOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(Requirement_c
								.getManyC_RsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(component, new ClassQueryInterface_c() {
												
													public boolean evaluate(Object candidate) {
														return ((Port_c)candidate).getName().equals("Port2");
													}
												
												})))), new ClassQueryInterface_c() {
												
													public boolean evaluate(Object candidate) {
														return ((RequiredOperation_c)candidate).getName().equals("clientserverop");
													}
												
												});
		assertNotNull(reqOp);
		
		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
        BPDebugUtils.setSelectionInSETree(new StructuredSelection(reqOp));
		
		ActivityEditor editor = DebugUITestUtilities
			.openActivityEditorForSelectedElement();
		editor.getDocumentProvider().getDocument(editor.getEditorInput()).set("");
		editor.doSave(new NullProgressMonitor());
		
		Menu menu = DebugUITestUtilities.getMenuInSETree(reqOp);

		assertTrue(
			"The execute menu item was not available for a required operation.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Execute", "", false));

        BPDebugUtils.setSelectionInSETree(new StructuredSelection(reqOp));
		
		UITestingUtilities.activateMenuItem(menu, "Execute");

		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(component);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		
		
		
		TestingUtilities.processDisplayEvents();
		String consoleText = DebugUITestUtilities.getConsoleText();
		final String expectedText = "User invoked interface message: clientserverop failed, no statements to execute. Check for OAL problems.";
		boolean textFound = (consoleText.indexOf(expectedText) != -1);
				
		for (int i = 0; i < 10 && !textFound; i++) {
			// wait for the execution to complete
			DebugUITestUtilities.waitForBPThreads(m_sys);

			TestingUtilities.processDisplayEvents();
			consoleText = DebugUITestUtilities.getConsoleText();
			textFound = (consoleText.indexOf(expectedText) != -1);
		}
		assertTrue(
				"Expected console warning not found when executing a signal with no OAL.",
				textFound);		
    }

    public void testExecuteInterfaceOperationWithParameters() {
		Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(m_sys)), new ClassQueryInterface_c() {
			
			public boolean evaluate(Object candidate) {
				return ((Component_c) candidate).getName().equals("fc1");
			}
		
		});
		assertNotNull(component);

		// launch the domain
		DebugUITestUtilities.launchElement(component, m_bp_tree.getControl()
				.getMenu());
		DebugUITestUtilities.waitForElementsToStart(1);

		RequiredOperation_c reqOp = RequiredOperation_c
				.getOneSPR_ROOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(Requirement_c
								.getManyC_RsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(component, new ClassQueryInterface_c() {
												
													public boolean evaluate(Object candidate) {
														return ((Port_c)candidate).getName().equals("Port2");
													}
												
												})))), new ClassQueryInterface_c() {
												
													public boolean evaluate(Object candidate) {
														return ((RequiredOperation_c)candidate).getName().equals("clientserveropparams");
													}
												
												});
		assertNotNull(reqOp);
		
		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
        BPDebugUtils.setSelectionInSETree(new StructuredSelection(reqOp));
		
		ActivityEditor editor = DebugUITestUtilities
			.openActivityEditorForSelectedElement();

		DebugUITestUtilities.setBreakpointAtLine(editor, 2);

		Menu menu = DebugUITestUtilities.getMenuInSETree(reqOp);

		assertTrue(
			"The execute menu item was not available for a required operation.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Execute", "", false));

		UITestingUtilities.activateMenuItem(menu, "Execute");

		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(component);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);
		
		IProcess process = DebugUITestUtilities.getProcessForEngine(engine);
		assertNotNull(process);

		DebugUITestUtilities.processDebugEvents();
		
		IDebugTarget target = process.getLaunch().getDebugTarget();
		assertTrue(
				"Process was not suspended by breakpoint in interface operation.",
				target.isSuspended());
		
		String stackTrace = DebugUITestUtilities.getStackTrace(process, engine);
		assertTrue("Unexpected suspend state, expected stack trace not found.",
				stackTrace.equals("Port2::Interface::clientserveropparams line: 2"));
		
		// Commenting out known failure tests.  See dts0100656068
/*		String xValue = DebugUITestUtilities.getValueForVariable("x");
		assertEquals("Default parameter value was not as expected for variable x.", "0", xValue);
		String yValue = DebugUITestUtilities.getValueForVariable("y");
		assertEquals("Default parameter value was not as expected for variable x.", "0", yValue);
*/
    }
    
    public void testTerminateAndRelaunch() {
    	Package_c dom = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Test Functions");
			}
		});
		assertNotNull(dom);

		// launch the domain
		DebugUITestUtilities.launchElement(Component_c
				.getOneC_COnR8003(PackageableElement_c
						.getOnePE_PEOnR8001(dom)), m_bp_tree.getControl()
							.getMenu());

		Function_c testCase = Function_c.getOneS_SYNCOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(dom), new ClassQueryInterface_c() {

			public boolean evaluate(
					Object candidate) {
				return ((Function_c) candidate).getName().equals("testFunction");
			}

		});
		assertNotNull(testCase);

		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
		BPDebugUtils.setSelectionInSETree(new StructuredSelection(testCase));

		ActivityEditor editor = DebugUITestUtilities
		.openActivityEditorForSelectedElement();

	    DebugUITestUtilities.setBreakpointAtLine(editor, 2);

      for (int i=0; i < 10; i++){
    	  performTerminateAndRelaunchTest(testCase);
      }
    }
    
    private void performTerminateAndRelaunchTest(Function_c testCase) {
    	Package_c dom = Package_c.getOneEP_PKGOnR8000(PackageableElement_c.getOnePE_PEOnR8001(testCase));
		Menu menu = null;
		boolean readyToExecute = false;
    	while (!readyToExecute) {
        	Selection.getInstance().clear();
        	SessionExplorerView sev = BPDebugUtils.openSessionExplorerView(true);
        	Tree sevTree = sev.getTreeViewer().getTree();
        	sevTree.setSelection(new TreeItem[0]);
        	sev.getTreeViewer().refresh();
        	sev.getTreeViewer().reveal(testCase);
        	UIUtil.dispatchAll();
        	BPDebugUtils.setSelectionInSETree(new StructuredSelection(testCase));
        	
    		menu = DebugUITestUtilities.getMenuInSETree(testCase);
    		readyToExecute = UITestingUtilities.checkItemStatusInContextMenu(menu,
    				"Execute", "", false);
    	}
    	
		assertTrue(
				"The execute menu item was not available for a required function.",
				readyToExecute);

		UITestingUtilities.activateMenuItem(menu, "Execute");

		DebugUITestUtilities.waitForExecution();
		ComponentInstance_c engine = ComponentInstance_c
				.getOneI_EXEOnR2955(Component_c
						.getOneC_COnR8003(PackageableElement_c
								.getOnePE_PEOnR8001(dom)));
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
		assertTrue("Unexpected suspend state, expected stack trace not found.",
				stackTrace.equals("testFunction line: 2"));
		
		IThread thread = DebugUITestUtilities.getThread(process, engine);
		
		// Reproduce the failure case exactly (not really needed for
        // reproducing the issue, but performing due diligence)
		try {
		  for (int i=0; i < 4; i++) {
		    thread.stepOver();
			DebugUITestUtilities.processDebugEvents();
			DebugUITestUtilities.waitForExecution();
			DebugUITestUtilities.waitForBPThreads(m_sys);
		  }
		}
		catch (Exception e) {
			String message = "Exception while stepping in Terminate and Relaunch test: " + e.toString();
			fail(message);
		}
		DebugUITestUtilities.setSelectionInDebugTree(process);
		menu = DebugUITestUtilities.getMenuForSelectionInDebugView();
		if (menu == null) {
		  fail("Failed to get tree viewer in Debug View.");
		}
		UITestingUtilities.activateMenuItem(menu, "Terminate and Relaunch");
		DebugUITestUtilities.processDebugEvents();
		TestingUtilities.processPlatformJobs();
		DebugUITestUtilities.waitForExecution();
		DebugUITestUtilities.waitForBPThreads(m_sys);
    }
}