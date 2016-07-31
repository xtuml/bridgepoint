package org.xtuml.bp.debug.ui.test.execute;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.ui.perspective.BridgePointPerspective;
import org.xtuml.bp.debug.ui.launch.BPDebugUtils;
import org.xtuml.bp.debug.ui.test.DebugUITestUtilities;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;

@RunWith(OrderedRunner.class)
public class VerifierExecuteFragmentTest extends BaseTest {
	private static String projectName = "VerifierFragmentExecutionTest";

	private boolean initialized = false;

	public VerifierExecuteFragmentTest() throws Exception {
		super(null, null);
	}

	@Override
	@Before
	public void setUp() throws Exception {
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

	@Test
	public void testtopLevelFragment() throws Exception {
		Package_c p1 = Package_c.getOneEP_PKGOnR1405(m_sys,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals("P1");
					}
				});
		assertNotNull(p1);

		DebugUITestUtilities
				.launchElement(p1, m_bp_tree.getControl().getMenu());

		openPerspectiveAndView(
				"org.xtuml.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);

		Component_c cp1 = Component_c.getOneC_COnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(p1), new ClassQueryInterface_c() {

			@Override
			public boolean evaluate(Object candidate) {
				return ((Component_c) candidate).getName().equals("CP1");
			}
		});
		assertNotNull(cp1);

		Package_c cp_p1 = Package_c.getOneEP_PKGOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8003(cp1), new ClassQueryInterface_c() {

			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("CP_P1");
			}
		});
		assertNotNull(cp_p1);
		Function_c uf1 = Function_c.getOneS_SYNCOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(cp_p1), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				return ((Function_c) candidate).getName().equals("UF1");
			}
		});
		assertNotNull(uf1);

		BPDebugUtils.openSessionExplorerView(true);

		BPDebugUtils.executeElement(uf1);
		
		DebugUITestUtilities.waitForExecution();

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		File expectedResults = new File(m_workspace_path
				+ "expected_results/verifier/" + projectName + "testtopLevelFragment.result");
		String expected_results = TestUtil.getTextFileContents(expectedResults);
		String actual_results = DebugUITestUtilities
				.getConsoleText(expected_results);
		
		String regex = "\r\n";
		String regex1 = "\n";
		
		String replacement = "\n";
		
		expected_results = expected_results.replaceAll(regex, replacement);
		expected_results = expected_results.replaceAll(regex1, replacement);
		
		actual_results = actual_results.replaceAll(regex, replacement);
		actual_results = actual_results.replaceAll(regex1, replacement);
		
		
		assertEquals(expected_results, actual_results);
	}

	@Test
	public void testnestedFragment() throws Exception {
		Package_c p1 = Package_c.getOneEP_PKGOnR1405(m_sys,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals("P1");
					}
				});
		assertNotNull(p1);

		DebugUITestUtilities
				.launchElement(p1, m_bp_tree.getControl().getMenu());

		openPerspectiveAndView(
				"org.xtuml.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);

		Component_c cp1 = Component_c.getOneC_COnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(p1), new ClassQueryInterface_c() {

			@Override
			public boolean evaluate(Object candidate) {
				return ((Component_c) candidate).getName().equals("CP1");
			}
		});
		assertNotNull(cp1);

		Package_c cp_p1 = Package_c.getOneEP_PKGOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8003(cp1), new ClassQueryInterface_c() {

			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("CP_P1");
			}
		});
		assertNotNull(cp_p1);
		
		Package_c cp_p2 = Package_c.getOneEP_PKGOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(cp_p1), new ClassQueryInterface_c() {

			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("CP_P2");
			}
		});
		assertNotNull(cp_p2);
		
		Function_c uf2 = Function_c.getOneS_SYNCOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(cp_p2), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				return ((Function_c) candidate).getName().equals("UF2");
			}
		});
		assertNotNull(uf2);

		BPDebugUtils.openSessionExplorerView(true);

		BPDebugUtils.executeElement(uf2);
		
		DebugUITestUtilities.waitForExecution();

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		File expectedResults = new File(m_workspace_path
				+ "expected_results/verifier/" + projectName + "testnestedFragment.result");
		String expected_results = TestUtil.getTextFileContents(expectedResults);
		String actual_results = DebugUITestUtilities
				.getConsoleText(expected_results);
		
		String regex = "\r\n";
		String regex1 = "\n";
		
		String replacement = "\n";
		
		expected_results = expected_results.replaceAll(regex, replacement);
		expected_results = expected_results.replaceAll(regex1, replacement);
		
		actual_results = actual_results.replaceAll(regex, replacement);
		actual_results = actual_results.replaceAll(regex1, replacement);
		
		
		assertEquals(expected_results, actual_results);
	}

	@After
	public void tearDown() throws Exception {
		DebugUITestUtilities.stopSession(m_sys, projectName);
	}

}
