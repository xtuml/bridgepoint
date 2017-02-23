package org.xtuml.bp.debug.ui.test.realizedClasses;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.ModelRoot;
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

@RunWith(OrderedRunner.class)
public class VerifierRealizedUDTTest extends BaseTest {

	private static String projectName = "VerifierRealizedUDTTest";

	private static boolean initialized = false;

	private boolean deterministicState = true;
	public VerifierRealizedUDTTest() throws Exception {
		super(null, null);
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		if (!initialized) {
			CorePlugin.disableParseAllOnResourceChange();

			// set perspective switch dialog on launch
			DebugUIPlugin.getDefault().getPluginPreferences().setValue(
					IDebugUIConstants.PLUGIN_ID + ".switch_to_perspective",
					"always");
			deterministicState = CorePlugin.getDefault().getPreferenceStore().
            getBoolean(BridgePointPreferencesStore.
		                                         ENABLE_DETERMINISTIC_VERIFIER);
			CorePlugin.getDefault().getPreferenceStore().
			              setValue(BridgePointPreferencesStore.
			        		              ENABLE_DETERMINISTIC_VERIFIER, false);

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

			TestingUtilities.allowJobCompletion();
			while (!ResourcesPlugin.getWorkspace().getRoot().isSynchronized(
					IProject.DEPTH_INFINITE)) {
				ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
						IProject.DEPTH_INFINITE, new NullProgressMonitor());
				while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
					;
			}

			Ooaofooa.setPersistEnabled(true);

			initialized = true;
		}
	}

	@After
	public void tearDown() throws Exception {
		// terminate all launches
		DebugUITestUtilities.terminateAllProcesses(m_sys);
		// remove all launch configurations
		DebugUITestUtilities.removeAllConfigurations();
		// clear the any console output
		DebugUITestUtilities.clearConsoleOutput();
		DebugUITestUtilities.clearDebugView();
		// remove all breakpoints
		DebugUITestUtilities.removeAllBreakpoints();
		// wait for display events to complete
		TestingUtilities.processDisplayEvents();
		CorePlugin.getDefault().getPreferenceStore().
        setValue(BridgePointPreferencesStore.
  		              ENABLE_DETERMINISTIC_VERIFIER, deterministicState);
	}

	static String actualResults = null;

	@Test
	public void testVerifyReadyToRun() {
        File checkFile = new File(getProject().getLocation().toString() + "/bin/library/RealizedUDTTest.class");
        assertTrue("This test requires that you build " + projectName + " in your workspace before it will succeed.",
        		checkFile.exists());

	}
	
	@Test
	public void testRealizedUDTsInInterfaceOperations() {
		String actual_results = performRealizedUDTTest("OperationTest");
		File expectedResults = new File(m_workspace_path
			       + "expected_results/binding/OperationTest.result");
	    String expected_results = TestUtil.getTextFileContents(expectedResults);

	    assertEquals(expected_results, actual_results);
	}
	
	@Test
	public void testRealizedUDTsInSignals() {
		String actual_results = performRealizedUDTTest("SignalTest");
		// Can't use comparison file with signals in not deterministic mode.
		// There is a reference result file in expected_results/binding for this
		// test which MUST be updated if the expected results change.
		int failure = actual_results.toLowerCase().indexOf("fail");
		assertTrue("Failure reported in Signals test", failure == -1);
		int unexpected = actual_results.toLowerCase().indexOf("unexpected");
		assertTrue("Unexpected value reported in Signals test",
                                                              unexpected == -1);
		String [] passSplit = actual_results.split("PASSED");
		assertTrue("Incorrect number of pass reports, expected 21 was " +
                                      passSplit.length, passSplit.length == 21);
	}
	
	private String performRealizedUDTTest(String functionName) {
		
		ModelRoot [] roots = Ooaofooa.getInstancesUnderSystem(projectName);
		Package_c testPkg = null;
		for (ModelRoot root: roots) {
			if (root.getId().equals("/VerifierRealizedUDTTest/models/VerifierRealizedUDTTest/System/System.xtuml")) {
			  testPkg = Package_c.PackageInstance(root, new Package_by_name_c("System"));
			  break;
			}
		}
		
		assertNotNull(testPkg);
		
        // Launch Verifier
    	openPerspectiveAndView("org.xtuml.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);    	  
        Selection.getInstance().setSelection(new StructuredSelection(testPkg));
    	Menu menu = m_bp_tree.getControl().getMenu();
    	assertTrue(
    			"The Launch Verifier action was not present for a component.",
    			UITestingUtilities.checkItemStatusInContextMenu(menu,
    					"Launch Verifier", "", false));
    	MenuItem launchVerifierItem = DebugUITestUtilities.getLaunchVerifierItem(menu);
    	assertNotNull(launchVerifierItem);
    	TestUtil.debugToDialog(200);
    	launchVerifierItem.notifyListeners(SWT.Selection, null);
    	TestingUtilities.processDisplayEvents();
    	Function_c testFunc =  null;
		for (ModelRoot root: roots) {
			if (root.getId().equals("/VerifierRealizedUDTTest/models/VerifierRealizedUDTTest/Library/Library.xtuml")) {
		      testFunc = Function_c.FunctionInstance(root,
                new Function_by_name_c("perform" + functionName));
		      break;
			}
		}
        assertNotNull(testFunc);
		
		BPDebugUtils.executeElement(testFunc);

		
		DebugUITestUtilities.waitForExecution();

		DebugUITestUtilities.waitForBPThreads(m_sys);
		DebugUITestUtilities.waitForExecution();

		return DebugUITestUtilities.getConsoleText();

	}

}
