//========================================================================
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
package org.xtuml.bp.core.test;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.prefs.Preferences;
import org.xtuml.bp.core.ClassInstanceParticipant_c;
import org.xtuml.bp.core.ClassParticipant_c;
import org.xtuml.bp.core.ComponentParticipant_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ExternalEntityParticipant_c;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.InteractionParticipant_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.PackageParticipant_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.preferences.BridgePointProjectPreferences;
import org.xtuml.bp.core.ui.preferences.BridgePointProjectReferencesPreferences;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.canvas.Cl_c;
import org.xtuml.bp.ui.canvas.test.CanvasTest;
import org.xtuml.bp.ui.canvas.test.CanvasTestUtilities;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;

public class IPRSetupTests extends CanvasTest {

	String test_id = null;
	private boolean generateResults = getGenerateResults();
	private static IProject projectOne;
	private static IProject projectTwo;

	public IPRSetupTests(String name) throws CoreException {
		super("org.xtuml.bp.core.test", name);

		CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,
				true);
	}

	@Override
	protected void initialSetup() throws Exception {
		// create the two test projects
		projectOne = TestingUtilities.createProject("test_project_referencing");
		projectTwo = TestingUtilities.createProject("test_project_referenced");
	}

	private static boolean getGenerateResults() {
		String env = System.getenv("generateResults");
		if (env == null) {
			return false;
		} else {
			boolean result = Boolean.parseBoolean(env);
			return result;
		}
	}

	protected String getResultName() {
		return "TigerNatureTestSetup" + "_" + test_id;
	}

	protected void setUp() throws Exception {
		super.setUp();
		Display d = Display.getCurrent();
		while (d.readAndDispatch());
		Ooaofooa.setPersistEnabled(true);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public static void createNewPackage(String name, SystemModel_c systemModel) {

		Transaction t = Cl_c.Starttransaction(systemModel, "Create Package");
		systemModel.Newpackage();
		Cl_c.Endtransaction(systemModel, t);

		Package_c pkgs[] = Package_c.getManyEP_PKGsOnR1401(systemModel);
		for (int i = 0; i < pkgs.length; i++) {
			if (pkgs[i].getName().equals("Unnamed Package")) {
				Transaction t2 = Cl_c.Starttransaction(pkgs[i], "Rename Package");
				pkgs[i].setName(name);
				Cl_c.Endtransaction(pkgs[i], t2);

			}

		}
	}

	public File getDomainFile(IProject project, String domainName) {
		File modelFile = new File(project.getLocation() + File.separator + Ooaofooa.MODELS_DIRNAME + File.separator
				+ domainName + "." + Ooaofooa.MODELS_EXT);
		return modelFile;
	}
	public class Package_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			Package_c selected = (Package_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public Package_by_name_c(String name) {
			m_name = name;
		}
		private String m_name;
	}

	/**
	 * Test formalization to an element that is in a different system
	 */
	public void testPackageParticipantFormalizeWithIPRs() {
		SystemModel_c referencingSystem = getSystemModel(projectOne.getName());
		SystemModel_c referencedSystem = getSystemModel(projectTwo.getName());
		// create a container package, and the formalize to element
		TestUtil.executeInTransaction(referencedSystem, "Newpackage", new Object[0]);
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(referencedSystem);
		Package_c referencedPkg = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(referencedPkg, "Newpackage", new Object[0]);
		Package_c referenced = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getOnePE_PEOnR8000(referencedPkg));
		TestUtil.executeInTransaction(referenced, "setName", new Object[]{"referenced"});
		// create a container package, and the test element
		TestUtil.executeInTransaction(referencingSystem, "Newpackage", new Object[0]);
		pkgs = Package_c.getManyEP_PKGsOnR1401(referencingSystem);
		Package_c containerPkg = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(containerPkg, "setName", new Object[]{"package_part_container"});
		TestUtil.executeInTransaction(containerPkg, "Newpackageparticipant", new Object[0]);
		PackageParticipant_c part = PackageParticipant_c.getOneSQ_PPOnR930(
				InteractionParticipant_c.getOneSQ_POnR8001(PackageableElement_c.getManyPE_PEsOnR8000(containerPkg)));
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(part);
		assertTrue("Formalize menu item was not present when accessible elements were.", UITestingUtilities
				.menuItemExists(getExplorerView().getTreeViewer().getTree().getMenu(), "", "Formalize..."));
		TestUtil.chooseItemInDialog(500, "referenced", true, true);
		TestUtil.cancelDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer().getTree().getMenu(), "Formalize...");
		// enable IPRs
		IScopeContext projectScope = new ProjectScope(projectOne);
		Preferences projectNode = projectScope.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.putBoolean(BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, true);
		TestUtil.chooseItemInDialog(500, "referenced");
		TestUtil.okToDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer().getTree().getMenu(), "Formalize...");
		// assert participant is associated with IPR element
		Package_c formalizedTo = Package_c.getOneEP_PKGOnR956(part);
		assertTrue("Formalization did not occur properly.", formalizedTo == referenced);

		// open the container diagram, and zoom to fit
		CanvasTestUtilities.openDiagramEditor(containerPkg);
		GraphicalEditor editor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		editor.zoomAll();
	}

	/**
	 * Test formalization to an element that is in a different system
	 */
	public void testComponentParticipantFormalizeWithIPRs() {
		IScopeContext projectScope = new ProjectScope(projectOne);
		Preferences projectNode = projectScope.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.putBoolean(BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, false);
		SystemModel_c referencingSystem = getSystemModel(projectOne.getName());
		SystemModel_c referencedSystem = getSystemModel(projectTwo.getName());
		// create a container package, and the formalize to element
		TestUtil.executeInTransaction(referencedSystem, "Newpackage", new Object[0]);
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(referencedSystem);
		Package_c referencedPkg = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(referencedPkg, "Newcomponent", new Object[0]);
		Component_c referenced = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(referencedPkg));
		TestUtil.executeInTransaction(referenced, "setName", new Object[]{"referenced"});
		// create a container package, and the test element
		TestUtil.executeInTransaction(referencingSystem, "Newpackage", new Object[0]);
		pkgs = Package_c.getManyEP_PKGsOnR1401(referencingSystem);
		Package_c containerPkg = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(containerPkg, "setName", new Object[]{"component_part_container"});
		TestUtil.executeInTransaction(containerPkg, "Newcomponentparticipant", new Object[0]);
		ComponentParticipant_c part = ComponentParticipant_c.getOneSQ_COPOnR930(
				InteractionParticipant_c.getManySQ_PsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(containerPkg)));
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(part);
		assertFalse("Formalize menu item was present when accessible elements were not.", UITestingUtilities
				.menuItemExists(getExplorerView().getTreeViewer().getTree().getMenu(), "", "Formalize..."));
		// add a local possibility
		TestUtil.executeInTransaction(containerPkg, "Newcomponent", new Object[0]);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(part);
		assertTrue("Formalize menu item was not present when accessible elements were.", UITestingUtilities
				.menuItemExists(getExplorerView().getTreeViewer().getTree().getMenu(), "", "Formalize..."));
		TestUtil.chooseItemInDialog(500, "referenced", true, true);
		TestUtil.chooseItemInDialog(500, "Unnamed Component", true, false);
		TestUtil.cancelDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer().getTree().getMenu(), "Formalize...");
		// delete local possibility
		Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(containerPkg));
		TestUtil.executeInTransaction(comp, "Dispose", new Object[0]);
		// enable IPRs
		projectNode.putBoolean(BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, true);
		assertTrue("Formalize menu item was not present when accessible elements were.", UITestingUtilities
				.menuItemExists(getExplorerView().getTreeViewer().getTree().getMenu(), "", "Formalize..."));
		TestUtil.chooseItemInDialog(500, "referenced");
		TestUtil.okToDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer().getTree().getMenu(), "Formalize...");
		// assert participant is associated with IPR element
		Component_c formalizedTo = Component_c.getOneC_COnR955(part);
		assertTrue("Formalization did not occur properly.", formalizedTo == referenced);

		// open the container diagram, and zoom to fit
		CanvasTestUtilities.openDiagramEditor(containerPkg);
		GraphicalEditor editor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		editor.zoomAll();
	}

	/**
	 * Test formalization to an element that is in a different system
	 */
	public void testExternalEntityParticipantFormalizeWithIPRs() {
		IScopeContext projectScope = new ProjectScope(projectOne);
		Preferences projectNode = projectScope.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.putBoolean(BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, false);
		SystemModel_c referencingSystem = getSystemModel(projectOne.getName());
		SystemModel_c referencedSystem = getSystemModel(projectTwo.getName());
		// create a container package, and the formalize to element
		TestUtil.executeInTransaction(referencedSystem, "Newpackage", new Object[0]);
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(referencedSystem);
		Package_c referencedPkg = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(referencedPkg, "Newexternalentity", new Object[0]);
		ExternalEntity_c referenced = ExternalEntity_c
				.getOneS_EEOnR8001(PackageableElement_c.getOnePE_PEOnR8000(referencedPkg));
		TestUtil.executeInTransaction(referenced, "setName", new Object[]{"referenced"});
		// create a container package, and the test element
		TestUtil.executeInTransaction(referencingSystem, "Newpackage", new Object[0]);
		pkgs = Package_c.getManyEP_PKGsOnR1401(referencingSystem);
		Package_c containerPkg = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(containerPkg, "setName", new Object[]{"ee_part_container"});
		TestUtil.executeInTransaction(containerPkg, "Newexternalentityparticipant", new Object[0]);
		ExternalEntityParticipant_c part = ExternalEntityParticipant_c.getOneSQ_EEPOnR930(
				InteractionParticipant_c.getManySQ_PsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(containerPkg)));
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(part);
		assertFalse("Formalize menu item was present when accessible elements were not.", UITestingUtilities
				.menuItemExists(getExplorerView().getTreeViewer().getTree().getMenu(), "", "Formalize..."));
		// add a local possibility
		containerPkg.Newexternalentity();
		assertTrue("Formalize menu item was not present when accessible elements were.", UITestingUtilities
				.menuItemExists(getExplorerView().getTreeViewer().getTree().getMenu(), "", "Formalize..."));
		TestUtil.chooseItemInDialog(500, "referenced", true, true);
		TestUtil.chooseItemInDialog(500, "Unnamed Component", true, false);
		TestUtil.cancelDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer().getTree().getMenu(), "Formalize...");
		// delete local possibility
		ExternalEntity_c ee = ExternalEntity_c
				.getOneS_EEOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(containerPkg));
		TestUtil.executeInTransaction(ee, "Dispose", new Object[0]);
		// enable IPRs
		projectNode.putBoolean(BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, true);
		assertTrue("Formalize menu item was not present when accessible elements were.", UITestingUtilities
				.menuItemExists(getExplorerView().getTreeViewer().getTree().getMenu(), "", "Formalize..."));
		TestUtil.chooseItemInDialog(500, "referenced");
		TestUtil.okToDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer().getTree().getMenu(), "Formalize...");
		// assert participant is associated with IPR element
		ExternalEntity_c formalizedTo = ExternalEntity_c.getOneS_EEOnR933(part);
		assertTrue("Formalization did not occur properly.", formalizedTo == referenced);

		// open the container diagram, and zoom to fit
		CanvasTestUtilities.openDiagramEditor(containerPkg);
		GraphicalEditor editor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		editor.zoomAll();
	}

	/**
	 * Test formalization to an element that is in a different system
	 */
	public void testClassParticipantFormalizeWithIPRs() {
		IScopeContext projectScope = new ProjectScope(projectOne);
		Preferences projectNode = projectScope.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.putBoolean(BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, false);
		SystemModel_c referencingSystem = getSystemModel(projectOne.getName());
		SystemModel_c referencedSystem = getSystemModel(projectTwo.getName());
		// create a container package, and the formalize to element
		TestUtil.executeInTransaction(referencedSystem, "Newpackage", new Object[0]);
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(referencedSystem);
		Package_c referencedPkg = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(referencedPkg, "Newclass", new Object[0]);
		ModelClass_c referenced = ModelClass_c
				.getOneO_OBJOnR8001(PackageableElement_c.getOnePE_PEOnR8000(referencedPkg));
		TestUtil.executeInTransaction(referenced, "setName", new Object[]{"referenced1"});
		// create a container package, and the test element
		TestUtil.executeInTransaction(referencingSystem, "Newpackage", new Object[0]);
		pkgs = Package_c.getManyEP_PKGsOnR1401(referencingSystem);
		Package_c containerPkg = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(containerPkg, "setName", new Object[]{"class_part_container"});
		TestUtil.executeInTransaction(containerPkg, "Newclassparticipant", new Object[0]);
		ClassParticipant_c part = ClassParticipant_c.getOneSQ_CPOnR930(
				InteractionParticipant_c.getManySQ_PsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(containerPkg)));
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(part);
		assertFalse("Formalize menu item was present when accessible elements were not.", UITestingUtilities
				.menuItemExists(getExplorerView().getTreeViewer().getTree().getMenu(), "", "Formalize..."));
		// add a local possibility
		TestUtil.executeInTransaction(containerPkg, "Newclass", new Object[0]);
		assertTrue("Formalize menu item was not present when accessible elements were.", UITestingUtilities
				.menuItemExists(getExplorerView().getTreeViewer().getTree().getMenu(), "", "Formalize..."));
		TestUtil.chooseItemInDialog(500, "referenced1", true, true);
		TestUtil.chooseItemInDialog(500, "Unnamed Component", true, false);
		TestUtil.cancelDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer().getTree().getMenu(), "Formalize...");
		// delete local possibility
		ModelClass_c clazz = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(containerPkg));
		TestUtil.executeInTransaction(clazz, "Dispose", new Object[0]);
		// enable IPRs
		projectNode.putBoolean(BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, true);
		assertTrue("Formalize menu item was not present when accessible elements were.", UITestingUtilities
				.menuItemExists(getExplorerView().getTreeViewer().getTree().getMenu(), "", "Formalize..."));
		TestUtil.chooseItemInDialog(500, "referenced1");
		TestUtil.okToDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer().getTree().getMenu(), "Formalize...");
		// assert participant is associated with IPR element
		ModelClass_c formalizedTo = ModelClass_c.getOneO_OBJOnR939(part);
		assertTrue("Formalization did not occur properly.", formalizedTo == referenced);

		// open the container diagram, and zoom to fit
		CanvasTestUtilities.openDiagramEditor(containerPkg);
		GraphicalEditor editor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		editor.zoomAll();
	}

	/**
	 * Test formalization to an element that is in a different system
	 */
	public void testClassInstanceParticipantFormalizeWithIPRs() {
		IScopeContext projectScope = new ProjectScope(projectOne);
		Preferences projectNode = projectScope.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.putBoolean(BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, false);
		SystemModel_c referencingSystem = getSystemModel(projectOne.getName());
		SystemModel_c referencedSystem = getSystemModel(projectTwo.getName());
		// create a container package, and the formalize to element
		TestUtil.executeInTransaction(referencedSystem, "Newpackage", new Object[0]);
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(referencedSystem);
		Package_c referencedPkg = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(referencedPkg, "Newclass", new Object[0]);
		ModelClass_c referenced = ModelClass_c
				.getOneO_OBJOnR8001(PackageableElement_c.getOnePE_PEOnR8000(referencedPkg));
		TestUtil.executeInTransaction(referenced, "setName", new Object[]{"referenced2"});
		// create a container package, and the test element
		TestUtil.executeInTransaction(referencingSystem, "Newpackage", new Object[0]);
		pkgs = Package_c.getManyEP_PKGsOnR1401(referencingSystem);
		Package_c containerPkg = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(containerPkg, "setName", new Object[]{"classinst_part_container"});
		TestUtil.executeInTransaction(containerPkg, "Newclassinstance", new Object[0]);
		ClassInstanceParticipant_c part = ClassInstanceParticipant_c.getOneSQ_CIPOnR930(
				InteractionParticipant_c.getManySQ_PsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(containerPkg)));
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(part);
		assertFalse("Formalize menu item was present when accessible elements were not.", UITestingUtilities
				.menuItemExists(getExplorerView().getTreeViewer().getTree().getMenu(), "", "Formalize..."));
		// add a local possibility
		TestUtil.executeInTransaction(containerPkg, "Newclass", new Object[0]);
		assertTrue("Formalize menu item was not present when accessible elements were.", UITestingUtilities
				.menuItemExists(getExplorerView().getTreeViewer().getTree().getMenu(), "", "Formalize..."));
		TestUtil.chooseItemInDialog(500, "referenced2", true, true);
		TestUtil.chooseItemInDialog(500, "Unnamed Component", true, false);
		TestUtil.cancelDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer().getTree().getMenu(), "Formalize...");
		// delete local possibility
		ModelClass_c clazz = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(containerPkg));
		TestUtil.executeInTransaction(clazz, "Dispose", new Object[0]);
		// enable IPRs
		projectNode.putBoolean(BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, true);
		assertTrue("Formalize menu item was not present when accessible elements were.", UITestingUtilities
				.menuItemExists(getExplorerView().getTreeViewer().getTree().getMenu(), "", "Formalize..."));
		TestUtil.chooseItemInDialog(500, "referenced2");
		TestUtil.okToDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer().getTree().getMenu(), "Formalize...");
		// assert participant is associated with IPR element
		ModelClass_c formalizedTo = ModelClass_c.getOneO_OBJOnR934(part);
		assertTrue("Formalization did not occur properly.", formalizedTo == referenced);

		// open the container diagram, and zoom to fit
		CanvasTestUtilities.openDiagramEditor(containerPkg);
		GraphicalEditor editor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		editor.zoomAll();
	}

}
