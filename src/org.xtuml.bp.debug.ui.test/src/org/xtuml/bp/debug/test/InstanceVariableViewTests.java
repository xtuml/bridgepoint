package org.xtuml.bp.debug.test;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.ComponentInstance_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.perspective.BridgePointPerspective;
import org.xtuml.bp.debug.ui.launch.BPDebugUtils;
import org.xtuml.bp.debug.ui.test.DebugUITestUtilities;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.text.activity.ActivityEditor;

@RunWith(OrderedRunner.class)
public class InstanceVariableViewTests extends BaseTest {

	private static String projectName = "135_dts0100895768";

	private static boolean testComplete = false;

	private static boolean isFirstTime = true;

	public InstanceVariableViewTests() throws Exception {
		super(projectName, null);
	}

	@Override
	@Before
	public void initialSetup() throws Exception {
			if (!isFirstTime)
				return;
			isFirstTime  = false;
			delayGlobalUpgrade = true;
			CorePlugin.disableParseAllOnResourceChange();

			// set perspective switch dialog on launch
			DebugUIPlugin.getDefault().getPluginPreferences().setValue(
					IDebugUIConstants.PLUGIN_ID + ".switch_to_perspective",
					"always");

			CorePlugin
					.getDefault()
					.getPluginPreferences()
					.setDefault(
							BridgePointPreferencesStore.ALLOW_IMPLICIT_COMPONENT_ADDRESSING,
							true);

			// initialize test model
			final IProject project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(projectName);

			loadProject(projectName);
			
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
            PrepareTest();
	}

	@After
	public void tearDown() throws Exception {
		if (!testComplete )
			return;
		// terminate all launches
		DebugUITestUtilities.terminateAllProcesses(m_sys);
		// clear the any console output
		DebugUITestUtilities.clearConsoleOutput();
		DebugUITestUtilities.clearDebugView();
		// remove all breakpoints
		DebugUITestUtilities.removeAllBreakpoints();
		// wait for display events to complete
		TestingUtilities.processDisplayEvents();

		TestingUtilities.waitForThread("Verifier (" + projectName + ")");	
	}

	public void PrepareTest(){
		DebugUITestUtilities.waitForBPThreads(m_sys);
		BaseTest.dispatchEvents(0);
		initTest();
	}
	
	@Test
	public void testDisplayPendingEvent() {
		TreeItem[] children = DebugUITestUtilities.expandValueinVariablesView("student1");
		
		String value = DebugUITestUtilities.getValueForVariable(children, "Pending Events");
		assertEquals("Student1: success", value);
		
	}
	
	@Test
	public void testDisplayCurrentState() {
		TreeItem[] children = DebugUITestUtilities.expandValueinVariablesView("student1");
		
		String value = DebugUITestUtilities.getValueForVariable(children, "Current State");
		assertEquals("First Year", value);
		
	}
	
	@Test
	public void testDisplayBinaryRelatedIsntances() {
		TreeItem[] children = DebugUITestUtilities.expandValueinVariablesView("student1");
		
		String value = DebugUITestUtilities.getValueForVariable(children, "R5.'Contains'");
		assertEquals("1:Classes", value);
		
	}
	
	
	@Test
	public void testDisplayAssociativeRelatedIsntances() {
		TreeItem[] children = DebugUITestUtilities.expandValueinVariablesView("student1");
		
		String value= DebugUITestUtilities.getValueForVariable(children, "R1.'Teaches'");
		assertEquals("[3]  3:Subject, 4:Subject, 5:Subject", value );
		
	}

	private void initTest() {
		Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(m_sys)), new ClassQueryInterface_c() {
			
			public boolean evaluate(Object candidate) {
				return ((Component_c) candidate).getName().equals("framework");
			}
			
		});
		
		assertNotNull(component);
		
		// launch the component
		
		Selection.getInstance().setSelection(new StructuredSelection(component));
		
		Menu menu = m_bp_tree.getControl().getMenu();
		assertTrue(
				"The Launch Verifier action was not present for a component.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Launch Verifier", "", false));
		MenuItem launchVerifierItem = DebugUITestUtilities.getLaunchVerifierItem(menu);
		assertNotNull(launchVerifierItem);
		
		ComponentInstance_c[] engines = ComponentInstance_c.ComponentInstanceInstances(component.getModelRoot());
		assertTrue("Unexpected test state, there should be no Component Instances.", engines.length == 0);
		TestUtil.debugToDialog(200);
		launchVerifierItem.notifyListeners(SWT.Selection, null);
		TestingUtilities.processDisplayEvents();
		
		menu = m_bp_tree.getControl().getMenu();
		assertFalse(
				"The Launch Verifier action was present for an unassigned imported component.",
				UITestingUtilities.menuItemExists(menu, "", "Launch Verifier"));
		
		
		
		
		Function_c testFunc = Function_c.getOneS_SYNCOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(
						Package_c.getManyEP_PKGsOnR8001(
								PackageableElement_c.getManyPE_PEsOnR8003(component)))
								, new ClassQueryInterface_c() {
					public boolean evaluate( Object candidate) {
						return ((Function_c) candidate) .getName().equals( "runTes"); 
					}
				}
				);
		assertNotNull(testFunc);
		
		openPerspectiveAndView("org.xtuml.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(testFunc);
		
		ActivityEditor editor = DebugUITestUtilities.openActivityEditorForSelectedElement();
		DebugUITestUtilities.setBreakpointAtLine(editor, 237);
		
		BPDebugUtils.executeElement(testFunc);
		
		DebugUITestUtilities.waitForExecution();
		
		ComponentInstance_c engine = ComponentInstance_c
				.getOneI_EXEOnR2955(component);
		assertNotNull(engine);
		
		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);
		
		// check that execution was suspended
		IProcess process = DebugUITestUtilities.getProcessForEngine(engine);
		assertNotNull(process);
		
		IDebugTarget target = process.getLaunch().getDebugTarget();
		DebugUITestUtilities.waitForExecution();
		DebugUITestUtilities.waitForBPThreads(m_sys);
		assertTrue("Process was not suspended by breakpoint in provided operation.", target
				.isSuspended());
	}
	
	
	
	
	
}