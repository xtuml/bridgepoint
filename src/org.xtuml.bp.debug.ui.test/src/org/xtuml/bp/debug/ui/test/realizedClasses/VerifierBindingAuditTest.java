package org.xtuml.bp.debug.ui.test.realizedClasses;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.dialogs.ScrolledTextDialog;
import org.xtuml.bp.core.ui.perspective.BridgePointPerspective;
import org.xtuml.bp.core.util.UIUtil;
import org.xtuml.bp.debug.ui.test.DebugUITestUtilities;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.test.common.UITestingUtilities;

@RunWith(OrderedRunner.class)
public class VerifierBindingAuditTest extends BaseTest {

	private static String projectName = "VerifierBindingTest";

	private boolean initialized = false;

	public VerifierBindingAuditTest() throws Exception {
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

			// initialize test model
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
	}
	
	@Test
	public void testVerifyReadyToRun() {
        File checkFile = new File(getProject().getLocation().toString() + "/bin/externalcodebindingtest/library/Realized.class");
        assertTrue("This test requires that you build " + projectName + " in your workspace before it will succeed.",
        		checkFile.exists());

	}

	@Test
	public void testMenuEntryAbsent() {
		final String nonrealizedPackageName = "NonRealized";
		Package_c nonRealizedcomponentPkg = Package_c.getOneEP_PKGOnR1405(m_sys,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								nonrealizedPackageName);
					}

				});
		assertNotNull(nonRealizedcomponentPkg);

		openPerspectiveAndView(
				"org.xtuml.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);

		Selection.getInstance().setSelection(new StructuredSelection(nonRealizedcomponentPkg));

		Menu menu = getExplorerView().getTreeViewer().getTree().getMenu();

		UIUtil.dispatchAll();

		assertFalse(
				"The audit menu item was available on a package without realized components.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Audit Realized Bindings", "", false));
	}

	static String actualResults = null;

	@Test
	public void testRealizedCodeAudit() {
		final String realizedPackageName = "External Code Binding Test";
		Package_c realizedComponentPkg = Package_c.getOneEP_PKGOnR1405(m_sys,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								realizedPackageName);
					}

				});
		assertNotNull(realizedComponentPkg);

		openPerspectiveAndView(
				"org.xtuml.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);

		Selection.getInstance().setSelection(new StructuredSelection(realizedComponentPkg));

		Menu menu = getExplorerView().getTreeViewer().getTree().getMenu();

		UIUtil.dispatchAll();

		assertTrue(
				"The audit menu item was not available on a package with realized components.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Audit Realized Bindings", "", false));
		Runnable verifyBindings = new Runnable () {
			public void run () {
				Shell[] shells = PlatformUI.getWorkbench().getDisplay().getShells();
				ScrolledTextDialog dialog = null;
				for(int i = 0; i < shells.length; i++) {
					if(shells[i].getData() instanceof ScrolledTextDialog) {
						dialog = (ScrolledTextDialog) shells[i].getData();
					}
				}
				if(dialog != null) {
					Control[] children = dialog.getShell().getChildren();
					Text textField = null;
					for(int i = 0; i < children.length; i++) {
						textField = getTextFromControl(children[i]);
						if(textField != null) {
							break;
						}
					}
					actualResults = textField.getText();
					dialog.close();
				}
			}
		};
		
		PlatformUI.getWorkbench().getDisplay().timerExec(500, verifyBindings);
		UITestingUtilities.activateMenuItem(menu, "Audit Realized Bindings");
		// Wait for dialog to complete
		while (actualResults == null) {
			Display.getDefault().readAndDispatch();
		};
		File debug_tree_expected_results = new File(m_workspace_path + "expected_results/binding/expected_audit_results.txt");
		String expectedResults = TestUtil.getTextFileContents(debug_tree_expected_results);
		assertEquals(expectedResults, actualResults);
	}

		protected static Text getTextFromControl(Control control) {
			if(control instanceof Text) {
				return (Text) control;
			}
			if(control instanceof Composite) {
				Composite composite = (Composite) control;
				Control[] children = composite.getChildren();
				for(int i = 0; i < children.length; i++) {
					Text text = getTextFromControl(children[i]);
					if(text != null) {
						return text;
					}
				}
			}
			return null;
		}



}
