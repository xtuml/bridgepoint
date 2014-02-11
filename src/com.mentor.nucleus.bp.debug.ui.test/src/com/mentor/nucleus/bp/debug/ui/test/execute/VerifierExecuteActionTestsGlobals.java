//=====================================================================
//
//File:      $RCSfile: VerifierExecuteActionTestsGlobals.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 13:25:45 $
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
package com.mentor.nucleus.bp.debug.ui.test.execute;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Domain_c;
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
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.perspective.BridgePointPerspective;
import com.mentor.nucleus.bp.debug.ui.launch.BPDebugUtils;
import com.mentor.nucleus.bp.debug.ui.test.DebugUITestUtilities;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;

public class VerifierExecuteActionTestsGlobals extends BaseTest {

	private static String projectName = "VerifierLaunchConfigurationTests";

	private static boolean initialized = false;

	public VerifierExecuteActionTestsGlobals(String testName) throws Exception {
		super(projectName, testName);
	}

	@Override
	protected void setUp() throws Exception {
		if (!initialized)
		  delayGlobalUpgrade = true;
		super.setUp();

		if (!initialized) {
			CorePlugin.disableParseAllOnResourceChange();

			// set perspective switch dialog on launch
			DebugUIPlugin.getDefault().getPluginPreferences().setValue(
					IDebugUIConstants.PLUGIN_ID + ".switch_to_perspective",
					"always");

			// initialize test model
			final IProject project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(projectName);

			File sourceProject = new File(m_workspace_path + "../"
					+ projectName);

			TestingUtilities.copyProjectContents(sourceProject, project);

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
			if (testGlobals) {
				Domain_c[] domains = Domain_c.getManyS_DOMsOnR28(m_sys);
				String[] domNames = new String[domains.length];
				for (int i = 0; i < domains.length; i++) {
					domNames[i] = domains[i].getName();
				}
				TestingUtilities.processPlatformJobs();
				DebugUITestUtilities.makeUpgradedDomainsExecutable(domNames,
						m_sys);
				TestingUtilities.allowJobCompletion();
			}
			delayGlobalUpgrade = false;
			initialized = true;
		}
	}

	public void tearDown() throws Exception {
		DebugUITestUtilities.stopSession(m_sys, projectName);
	}

	public void testExecuteClassBasedOperationOAL() {
		Package_c domCtr = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("Domains");
      }
    });
		assertNotNull(domCtr);

		Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(domCtr), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Component_c)candidate).getName().equals("domain");
      }
    });
		assertNotNull(comp);

		Package_c dom = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(comp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("domain");
      }
    });
    
    assertNotNull(dom);

    Package_c ss = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(dom), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("ss");
      }
    });
    assertNotNull(dom);

		Operation_c operation = Operation_c.getOneO_TFROnR115(ModelClass_c
				.getManyO_OBJsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(ss)),
                                                   new ClassQueryInterface_c() {
				
					public boolean evaluate(Object candidate) {
						return ((Operation_c)candidate).getName().equals("op");
					}
				
				});
		assertNotNull(operation);

		// launch the component
		DebugUITestUtilities.launchElement(comp, m_bp_tree.getControl()
				.getMenu());

        openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
        
        Selection.getInstance().clear();
        Selection.getInstance().addToSelection(operation);

		ActivityEditor editor = DebugUITestUtilities
				.openActivityEditorForSelectedElement();

		DebugUITestUtilities.setBreakpointAtLine(editor, 2);

		BPDebugUtils.openSessionExplorerView(true);

		BPDebugUtils.executeElement(operation);

		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(comp);
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
    Package_c cp = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("Domains");
      }
    });
    assertNotNull(cp);

    Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(cp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Component_c)candidate).getName().equals("domain");
      }
    });
    assertNotNull(comp);

    Package_c dom = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(comp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("domain");
      }
    });
    
    assertNotNull(dom);
    
    Package_c ss = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(dom), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("ss");
      }
    });
    assertNotNull(ss);

    Operation_c operation = Operation_c.getOneO_TFROnR115(ModelClass_c
        .getManyO_OBJsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(ss)),
                                                   new ClassQueryInterface_c() {
        
          public boolean evaluate(Object candidate) {
            return ((Operation_c)candidate).getName().equals("opNoOAL");
          }
        
        });
    assertNotNull(operation);

    // launch the component
		DebugUITestUtilities.launchElement(comp, m_bp_tree.getControl()
				.getMenu());

		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
		BPDebugUtils.executeElement(operation);
		
		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(comp);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		String consoleText = DebugUITestUtilities.getConsoleText();
		assertTrue(
				"Expected console warning not found when executing an operation with no OAL.",
				consoleText
						.indexOf("User invoked operation: opNoOAL failed, no statements to execute. Check for OAL problems.") != -1);
	}
	
	public void testExecuteClassBasedOperationWithParameters() {
    Package_c cp = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("Domains");
      }
    });
    assertNotNull(cp);

    Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(cp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Component_c)candidate).getName().equals("domain");
      }
    });
    assertNotNull(comp);
    
    Package_c dom = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(comp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("domain");
      }
    });
    
    assertNotNull(dom);

    Package_c ss = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(dom), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("ss");
      }
    });
    assertNotNull(ss);

    Operation_c operation = Operation_c.getOneO_TFROnR115(ModelClass_c
        .getManyO_OBJsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(ss)),
                                                   new ClassQueryInterface_c() {
        
          public boolean evaluate(Object candidate) {
            return ((Operation_c)candidate).getName().equals("opWithParams");
          }
        
        });
    assertNotNull(operation);

    // launch the component
		DebugUITestUtilities.launchElement(comp, m_bp_tree.getControl()
				.getMenu());

        openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
        		
        Selection.getInstance().clear();
        Selection.getInstance().addToSelection(operation);

		ActivityEditor editor = DebugUITestUtilities
			.openActivityEditorForSelectedElement();

		DebugUITestUtilities.setBreakpointAtLine(editor, 1);
		
		BPDebugUtils.openSessionExplorerView(true);

		BPDebugUtils.executeElement(operation);
		
		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(comp);
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
    Package_c cp = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("cp");
      }
    });
    assertNotNull(cp);

    Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(cp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Component_c)candidate).getName().equals("fc1");
      }
    });
    assertNotNull(comp);
		
    Package_c ss = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(comp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("ss");
      }
    });
    assertNotNull(ss);

		StateMachineState_c state = StateMachineState_c
				.getOneSM_STATEOnR501(StateMachine_c
						.getManySM_SMsOnR517(ClassStateMachine_c
								.getManySM_ASMsOnR519(ModelClass_c
										.getManyO_OBJsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(ss)))), new ClassQueryInterface_c() {
																
																	public boolean evaluate(Object candidate) {
																		return ((StateMachineState_c) candidate).getName().equals("State 2");
																	}
																
																});
		assertNotNull(state);

		// launch the domain
		DebugUITestUtilities.launchElement(comp, m_bp_tree.getControl()
				.getMenu());

		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective", BridgePointPerspective.ID_MGC_BP_EXPLORER);

		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(state);

		ProvidedSignal_c proSignal = ProvidedSignal_c
			.getOneSPR_PSOnR4503(ProvidedExecutableProperty_c
					.getManySPR_PEPsOnR4501(Provision_c
							.getManyC_PsOnR4009(InterfaceReference_c
									.getManyC_IRsOnR4016(Port_c
											.getManyC_POsOnR4010(comp, new ClassQueryInterface_c() {
									
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

		BPDebugUtils.executeElement(proSignal);
		
		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(comp);
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
    Package_c cp = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("cp");
      }
    });
    assertNotNull(cp);

    Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(cp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Component_c)candidate).getName().equals("fc1");
      }
    });
    assertNotNull(comp);
    		
		ProvidedSignal_c proSignal = ProvidedSignal_c
			.getOneSPR_PSOnR4503(ProvidedExecutableProperty_c
				.getManySPR_PEPsOnR4501(Provision_c
						.getManyC_PsOnR4009(InterfaceReference_c
								.getManyC_IRsOnR4016(Port_c
										.getManyC_POsOnR4010(comp, new ClassQueryInterface_c() {
										
											public boolean evaluate(Object candidate) {
												return ((Port_c)candidate).getName().equals("Port1");
											}
										
										})))), new ClassQueryInterface_c() {
										
											public boolean evaluate(Object candidate) {
												return ((ProvidedSignal_c)candidate).getName().equals("clientserversig");
											}
										
										});
		assertNotNull(proSignal);
		
        Selection.getInstance().clear();
        Selection.getInstance().addToSelection(proSignal);
		
		ActivityEditor editor = DebugUITestUtilities
			.openActivityEditorForSelectedElement();
		editor.getDocumentProvider().getDocument(editor.getEditorInput()).set("");
		editor.doSave(new NullProgressMonitor());
		
		assertTrue("OAL was not removed from signal.", proSignal.getAction_semantics().equals(""));
		testExecuteSignalAssignedToTransition();
	}
	
	public void testExecuteSignalAssignedToTransitionWithParameters() {
    Package_c cp = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("cp");
      }
    });
    assertNotNull(cp);

    Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(cp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Component_c)candidate).getName().equals("fc1");
      }
    });
    assertNotNull(comp);
    
    Package_c ss = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(comp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("ss");
      }
    });
    assertNotNull(ss);

    StateMachineState_c state = StateMachineState_c
        .getOneSM_STATEOnR501(StateMachine_c
            .getManySM_SMsOnR517(ClassStateMachine_c
                .getManySM_ASMsOnR519(ModelClass_c
                    .getManyO_OBJsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(ss)))), new ClassQueryInterface_c() {
                                
                                  public boolean evaluate(Object candidate) {
                                    return ((StateMachineState_c) candidate).getName().equals("State 3");
                                  }
                                
                                });
    assertNotNull(state);

		// launch the domain
		DebugUITestUtilities.launchElement(comp, m_bp_tree.getControl()
				.getMenu());

		ProvidedSignal_c proSignal = ProvidedSignal_c
				.getOneSPR_PSOnR4503(ProvidedExecutableProperty_c
						.getManySPR_PEPsOnR4501(Provision_c
								.getManyC_PsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(comp, new ClassQueryInterface_c() {
												
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
		
        Selection.getInstance().clear();
        Selection.getInstance().addToSelection(state);
		
		ActivityEditor editor = DebugUITestUtilities
			.openActivityEditorForSelectedElement();

		DebugUITestUtilities.setBreakpointAtLine(editor, 2);
		
		BPDebugUtils.executeElement(proSignal);
		
		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(comp);
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
    Package_c cp = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("cp");
      }
    });
    assertNotNull(cp);

    Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(cp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Component_c)candidate).getName().equals("fc1");
      }
    });
    assertNotNull(comp);
    
		// launch the domain
		DebugUITestUtilities.launchElement(comp, m_bp_tree.getControl()
				.getMenu());

		RequiredSignal_c reqSignal = RequiredSignal_c
				.getOneSPR_RSOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(Requirement_c
								.getManyC_RsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(comp, new ClassQueryInterface_c() {
												
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
		
        Selection.getInstance().clear();
        Selection.getInstance().addToSelection(reqSignal);
		
		ActivityEditor editor = DebugUITestUtilities
			.openActivityEditorForSelectedElement();

		DebugUITestUtilities.setBreakpointAtLine(editor, 2);

		BPDebugUtils.executeElement(reqSignal);
		
		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(comp);
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
    Package_c cp = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("cp");
      }
    });
    assertNotNull(cp);

    Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(cp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Component_c)candidate).getName().equals("fc1");
      }
    });
    assertNotNull(comp);
    

		// launch the component
		DebugUITestUtilities.launchElement(comp, m_bp_tree.getControl()
				.getMenu());

		RequiredSignal_c reqSignal = RequiredSignal_c
				.getOneSPR_RSOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(Requirement_c
								.getManyC_RsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(comp, new ClassQueryInterface_c() {
												
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
		
        Selection.getInstance().clear();
        Selection.getInstance().addToSelection(reqSignal);
		
		ActivityEditor editor = DebugUITestUtilities
			.openActivityEditorForSelectedElement();
		editor.getDocumentProvider().getDocument(editor.getEditorInput()).set("");
		editor.doSave(new NullProgressMonitor());
	
		assertTrue("OAL was not removed from signal.", reqSignal.getAction_semantics().equals(""));
		
		BPDebugUtils.executeElement(reqSignal);
		
		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(comp);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		String consoleText = DebugUITestUtilities.getConsoleText();
		assertTrue(
				"Expected console warning not found when executing a signal with no OAL.",
				consoleText
						.indexOf("User invoked interface message: clientserversig failed, no statements to execute. Check for OAL problems.") != -1);
	}

	public void testExecuteSignalNotAssignedToTransitionWithParameters() {
    Package_c cp = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("cp");
      }
    });
    assertNotNull(cp);

    Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(cp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Component_c)candidate).getName().equals("fc1");
      }
    });
    assertNotNull(comp);
    

		// launch the domain
		DebugUITestUtilities.launchElement(comp, m_bp_tree.getControl()
				.getMenu());

		RequiredSignal_c reqSignal = RequiredSignal_c
				.getOneSPR_RSOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(Requirement_c
								.getManyC_RsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(comp, new ClassQueryInterface_c() {
												
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
		
        Selection.getInstance().clear();
        Selection.getInstance().addToSelection(reqSignal);
		
		ActivityEditor editor = DebugUITestUtilities
			.openActivityEditorForSelectedElement();

		DebugUITestUtilities.setBreakpointAtLine(editor, 2);

		BPDebugUtils.executeElement(reqSignal);
		
		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(comp);
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
      Package_c cp = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
        
        @Override
        public boolean evaluate(Object candidate) {
          return ((Package_c)candidate).getName().equals("cp");
        }
      });
      assertNotNull(cp);

      Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(cp), new ClassQueryInterface_c() {
        
        @Override
        public boolean evaluate(Object candidate) {
          return ((Component_c)candidate).getName().equals("fc1");
        }
      });
      assertNotNull(comp);
      
		// launch the domain
		DebugUITestUtilities.launchElement(comp, m_bp_tree.getControl()
				.getMenu());

		RequiredOperation_c reqOp = RequiredOperation_c
				.getOneSPR_ROOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(Requirement_c
								.getManyC_RsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(comp, new ClassQueryInterface_c() {
												
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
		
        Selection.getInstance().clear();
        Selection.getInstance().addToSelection(reqOp);
		
		ActivityEditor editor = DebugUITestUtilities
			.openActivityEditorForSelectedElement();

		DebugUITestUtilities.setBreakpointAtLine(editor, 2);

		BPDebugUtils.executeElement(reqOp);
		
		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(comp);
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
      Package_c cp = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
        
        @Override
        public boolean evaluate(Object candidate) {
          return ((Package_c)candidate).getName().equals("cp");
        }
      });
      assertNotNull(cp);

      Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(cp), new ClassQueryInterface_c() {
        
        @Override
        public boolean evaluate(Object candidate) {
          return ((Component_c)candidate).getName().equals("fc1");
        }
      });
      assertNotNull(comp);
      
		// launch the domain
		DebugUITestUtilities.launchElement(comp, m_bp_tree.getControl()
				.getMenu());

		RequiredOperation_c reqOp = RequiredOperation_c
				.getOneSPR_ROOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(Requirement_c
								.getManyC_RsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(comp, new ClassQueryInterface_c() {
												
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
		
        Selection.getInstance().clear();
        Selection.getInstance().addToSelection(reqOp);
		
		ActivityEditor editor = DebugUITestUtilities
			.openActivityEditorForSelectedElement();
		editor.getDocumentProvider().getDocument(editor.getEditorInput()).set("");
		editor.doSave(new NullProgressMonitor());
		
		BPDebugUtils.executeElement(reqOp);

		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(comp);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);
		
		String consoleText = DebugUITestUtilities.getConsoleText();
		assertTrue(
				"Expected console warning not found when executing a signal with no OAL.",
				consoleText
						.indexOf("User invoked interface message: clientserverop failed, no statements to execute. Check for OAL problems.") != -1);
    }

    public void testExecuteInterfaceOperationWithParameters() {
      Package_c cp = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
        
        @Override
        public boolean evaluate(Object candidate) {
          return ((Package_c)candidate).getName().equals("cp");
        }
      });
      assertNotNull(cp);

      Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(cp), new ClassQueryInterface_c() {
        
        @Override
        public boolean evaluate(Object candidate) {
          return ((Component_c)candidate).getName().equals("fc1");
        }
      });
      assertNotNull(comp);
      
		// launch the domain
		DebugUITestUtilities.launchElement(comp, m_bp_tree.getControl()
				.getMenu());

		RequiredOperation_c reqOp = RequiredOperation_c
				.getOneSPR_ROOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(Requirement_c
								.getManyC_RsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(comp, new ClassQueryInterface_c() {
												
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
		
        Selection.getInstance().clear();
        Selection.getInstance().addToSelection(reqOp);
		
		ActivityEditor editor = DebugUITestUtilities
			.openActivityEditorForSelectedElement();

		DebugUITestUtilities.setBreakpointAtLine(editor, 2);

		BPDebugUtils.executeElement(reqOp);
		
		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(comp);
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
      Package_c cp = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
        
        @Override
        public boolean evaluate(Object candidate) {
          return ((Package_c)candidate).getName().equals("Domains");
        }
      });
      assertNotNull(cp);

      Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(cp), new ClassQueryInterface_c() {
        
        @Override
        public boolean evaluate(Object candidate) {
          return ((Component_c)candidate).getName().equals("domain");
        }
      });
      assertNotNull(comp);
      
		// launch the domain
		DebugUITestUtilities.setLogActivityAndLaunchForElement(comp,
				m_bp_tree.getControl().getMenu(), m_sys.getName());

    Package_c dom = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(comp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("domain");
      }
    });
    
    assertNotNull(dom);

    Package_c fp = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(dom), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("Test Functions");
      }
    });
    assertNotNull(fp);

		Function_c testCase = Function_c.getOneS_SYNCOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(fp), new ClassQueryInterface_c() {

			public boolean evaluate(
					Object candidate) {
				return ((Function_c) candidate).getName().equals("testFunction");
			}

		});
		assertNotNull(testCase);

		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(testCase);

		ActivityEditor editor = DebugUITestUtilities
		.openActivityEditorForSelectedElement();

	    DebugUITestUtilities.setBreakpointAtLine(editor, 2);

      for (int i=0; i < 10; i++){
    	  performTerminateAndRelaunchTest(testCase);
      }
    }
    
    private void performTerminateAndRelaunchTest(Function_c testCase) {
    	Component_c comp = Component_c.getOneC_COnR8003(
    	    PackageableElement_c.getOnePE_PEOnR8001(
    	        Package_c.getOneEP_PKGOnR8000(
    	            PackageableElement_c.getOnePE_PEOnR8001(
    	                Package_c.getOneEP_PKGOnR8000(
    	                   PackageableElement_c.getOnePE_PEOnR8001(testCase))))));
    	
		BPDebugUtils.executeElement(testCase);

		DebugUITestUtilities.waitForExecution();
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(comp);
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
		Menu menu = DebugUITestUtilities.getMenuForSelectionInDebugView();
		if (menu == null) {
		  fail("Failed to get tree viewer in Debug View.");
		}
		UITestingUtilities.activateMenuItem(menu, "Terminate and Relaunch");
		DebugUITestUtilities.processDebugEvents();
		TestingUtilities.processPlatformJobs();
		DebugUITestUtilities.waitForExecution();
		DebugUITestUtilities.waitForBPThreads(m_sys);
    }
    
    public static void assertTrue(String message, boolean result) {
      if (!result ) {
        System.err.println(message);
        boolean proceed = false;
        while (!proceed) {
          Display.getCurrent().readAndDispatch();
        }
      }
    }
    
    public static void assertNotNull(Object obj) {
      assertTrue("Object is null.", obj != null);
    }
}