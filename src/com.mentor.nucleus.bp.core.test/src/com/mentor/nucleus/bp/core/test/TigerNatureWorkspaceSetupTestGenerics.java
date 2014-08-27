// ========================================================================
//
//File: $RCSfile: TigerNatureWorkspaceSetupTestGenerics.java,v $
//Version: $Revision: 1.13 $
//Modified: $Date: 2013/03/14 02:37:43 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
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
package com.mentor.nucleus.bp.core.test;

import java.io.File;
import java.lang.reflect.Method;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.osgi.framework.Bundle;
import org.osgi.service.prefs.Preferences;

import com.mentor.nucleus.bp.core.ActorParticipant_c;
import com.mentor.nucleus.bp.core.ClassInstanceParticipant_c;
import com.mentor.nucleus.bp.core.ClassParticipant_c;
import com.mentor.nucleus.bp.core.ComponentParticipant_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.ExternalEntityParticipant_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.InteractionParticipant_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.PackageParticipant_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.ui.NewSystemWizard;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.preferences.BridgePointProjectPreferences;
import com.mentor.nucleus.bp.core.ui.preferences.BridgePointProjectReferencesPreferences;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTestUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.description.ShowDescriptionAction;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public class TigerNatureWorkspaceSetupTestGenerics extends CanvasTest {

	String test_id = null;
	private boolean generateResults = getGenerateResults();
	private static IProject projectOne;
	private static IProject projectTwo;

	public TigerNatureWorkspaceSetupTestGenerics(String name) throws CoreException {
		super("com.mentor.nucleus.bp.core.test", name);

        // turn off autobuild to stop MC-3020 builders from running
		WorkspaceUtil.setAutobuilding(false);   // throws CoreException
		CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,true);
	}

	@Override
	protected void initialSetup() throws Exception {
		// create the two test projects
		projectOne = createXtUMLProject("test_project_referencing");
		projectTwo = createXtUMLProject("test_project_referenced");
	}
	
	private static boolean getGenerateResults() {
		String env = System.getenv("generateResults");
		if(env == null) {
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
		while (d.readAndDispatch())
			;
		Ooaofooa.setPersistEnabled(true);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	private IProject createXtUMLProject(String name) {
		NewSystemWizard nsw = new NewSystemWizard();
		nsw.init(PlatformUI.getWorkbench(), null);
        nsw.setIsCreatedByUnitTest();
        
        WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), nsw);
		WizardNewProjectCreationPage wnpcp = (WizardNewProjectCreationPage) nsw
				.getStartingPage();
		wnpcp.setInitialProjectName(name);
        dialog.create();
		wnpcp.useDefaults();
		nsw.performFinish();
		dialog.close();
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				name);
		return project;
	}

	public static void createNewPackage(String name, SystemModel_c systemModel) {
        
		Transaction t = Cl_c.Starttransaction(systemModel, "Create Package");
		systemModel.Newpackage();
		Cl_c.Endtransaction(systemModel, t);
		
		 Package_c pkgs[]= Package_c.getManyEP_PKGsOnR1401(systemModel);
          for (int i=0 ;i<pkgs.length;i++ )
          { 
        	  if (pkgs[i].getName().equals("Unnamed Package"))
        	  {
        		    Transaction t2 = Cl_c.Starttransaction(pkgs[i], "Rename Package");
        		    pkgs[i].setName(name);
        	        Cl_c.Endtransaction(pkgs[i], t2);
        		  
        	  }
        	  
          }
	}

	static public void openDescriptionEditor( final Object uut )
	{

		try
		{
		  IWorkspaceRunnable r = new IWorkspaceRunnable()
		  {
			public void run(IProgressMonitor monitor) throws CoreException
			{
				IStructuredSelection ss = new StructuredSelection(uut);
				ShowDescriptionAction sda = new ShowDescriptionAction();
				Action a = new Action(){};
				sda.selectionChanged(a, ss);
				sda.run( a );
			}
		  };
		  CanvasUtilities.getWorkspace().run(r, null);
		}
		catch (CoreException x)
		{
		  fail("open editor problem");
		}

	}

	public File getDomainFile(IProject project, String domainName) {
		File modelFile = new File(project.getLocation() + File.separator
				+ Ooaofooa.MODELS_DIRNAME + File.separator + domainName + "." + Ooaofooa.MODELS_EXT);
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
	 * The tests below (ending with IRPs) need to be first otherwise
	 * the expected results are not correct
	 */

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
		Package_c referenced = Package_c
				.getOneEP_PKGOnR8001(PackageableElement_c
						.getOnePE_PEOnR8000(referencedPkg));
		TestUtil.executeInTransaction(referenced, "setName", new Object[] {"referenced"});
		// create a container package, and the test element
		TestUtil.executeInTransaction(referencingSystem, "Newpackage", new Object[0]);
		pkgs = Package_c.getManyEP_PKGsOnR1401(referencingSystem);
		Package_c containerPkg = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(containerPkg, "setName", new Object[] {"package_part_container"});
		TestUtil.executeInTransaction(containerPkg, "Newpackageparticipant", new Object[0]);
		PackageParticipant_c part = PackageParticipant_c
				.getOneSQ_PPOnR930(InteractionParticipant_c
						.getOneSQ_POnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(containerPkg)));
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(part);
		assertTrue(
				"Formalize menu item was not present when accessible elements were.",
				UITestingUtilities.menuItemExists(getExplorerView()
						.getTreeViewer().getTree().getMenu(), "",
						"Formalize..."));
		TestUtil.chooseItemInDialog(500, "referenced", true, true);
		TestUtil.cancelDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer()
				.getTree().getMenu(), "Formalize...");
		// enable IPRs
		IScopeContext projectScope = new ProjectScope(projectOne);
		Preferences projectNode = projectScope
				.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.putBoolean(
				BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, true);
		TestUtil.chooseItemInDialog(500, "referenced");
		TestUtil.okToDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer()
				.getTree().getMenu(), "Formalize...");
		// assert participant is associated with IPR element
		Package_c formalizedTo = Package_c.getOneEP_PKGOnR956(part);
		assertTrue("Formalization did not occur properly.",
				formalizedTo == referenced);

		// open the container diagram, and zoom to fit
		CanvasTestUtilities.openDiagramEditor(containerPkg);
		GraphicalEditor editor = (GraphicalEditor) UITestingUtilities
				.getActiveEditor();
		editor.zoomAll();
	}

	/**
	 * Test formalization to an element that is in a different system
	 */
	public void testComponentParticipantFormalizeWithIPRs() {
		IScopeContext projectScope = new ProjectScope(projectOne);
		Preferences projectNode = projectScope
				.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.putBoolean(
				BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, false);
		SystemModel_c referencingSystem = getSystemModel(projectOne.getName());
		SystemModel_c referencedSystem = getSystemModel(projectTwo.getName());
		// create a container package, and the formalize to element
		TestUtil.executeInTransaction(referencedSystem, "Newpackage", new Object[0]);
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(referencedSystem);
		Package_c referencedPkg = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(referencedPkg, "Newcomponent", new Object[0]);
		Component_c referenced = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(referencedPkg));
		TestUtil.executeInTransaction(referenced, "setName", new Object[] {"referenced"});
		// create a container package, and the test element
		TestUtil.executeInTransaction(referencingSystem, "Newpackage", new Object[0]);
		pkgs = Package_c.getManyEP_PKGsOnR1401(referencingSystem);
		Package_c containerPkg = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(containerPkg, "setName", new Object[] {"component_part_container"});
		TestUtil.executeInTransaction(containerPkg, "Newcomponentparticipant", new Object[0]);
		ComponentParticipant_c part = ComponentParticipant_c
				.getOneSQ_COPOnR930(InteractionParticipant_c
						.getManySQ_PsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(containerPkg)));
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(part);
		assertFalse(
				"Formalize menu item was present when accessible elements were not.",
				UITestingUtilities.menuItemExists(getExplorerView()
						.getTreeViewer().getTree().getMenu(), "",
						"Formalize..."));
		// add a local possibility
		TestUtil.executeInTransaction(containerPkg, "Newcomponent", new Object[0]);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(part);
		assertTrue(
				"Formalize menu item was not present when accessible elements were.",
				UITestingUtilities.menuItemExists(getExplorerView()
						.getTreeViewer().getTree().getMenu(), "",
						"Formalize..."));
		TestUtil.chooseItemInDialog(500, "referenced", true, true);
		TestUtil.chooseItemInDialog(500, "Unnamed Component", true, false);
		TestUtil.cancelDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer()
				.getTree().getMenu(), "Formalize...");
		// delete local possibility
		Component_c comp = Component_c.getOneC_COnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(containerPkg));
		TestUtil.executeInTransaction(comp, "Dispose", new Object[0]);
		// enable IPRs
		projectNode.putBoolean(
				BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, true);
		assertTrue(
				"Formalize menu item was not present when accessible elements were.",
				UITestingUtilities.menuItemExists(getExplorerView()
						.getTreeViewer().getTree().getMenu(), "",
						"Formalize..."));
		TestUtil.chooseItemInDialog(500, "referenced");
		TestUtil.okToDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer()
				.getTree().getMenu(), "Formalize...");
		// assert participant is associated with IPR element
		Component_c formalizedTo = Component_c.getOneC_COnR955(part);
		assertTrue("Formalization did not occur properly.",
				formalizedTo == referenced);

		// open the container diagram, and zoom to fit
		CanvasTestUtilities.openDiagramEditor(containerPkg);
		GraphicalEditor editor = (GraphicalEditor) UITestingUtilities
				.getActiveEditor();
		editor.zoomAll();
	}

	/**
	 * Test formalization to an element that is in a different system
	 */
	public void testExternalEntityParticipantFormalizeWithIPRs() {
		IScopeContext projectScope = new ProjectScope(projectOne);
		Preferences projectNode = projectScope
				.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.putBoolean(
				BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, false);
		SystemModel_c referencingSystem = getSystemModel(projectOne.getName());
		SystemModel_c referencedSystem = getSystemModel(projectTwo.getName());
		// create a container package, and the formalize to element
		TestUtil.executeInTransaction(referencedSystem, "Newpackage", new Object[0]);
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(referencedSystem);
		Package_c referencedPkg = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(referencedPkg, "Newexternalentity", new Object[0]);
		ExternalEntity_c referenced = ExternalEntity_c
				.getOneS_EEOnR8001(PackageableElement_c
						.getOnePE_PEOnR8000(referencedPkg));
		TestUtil.executeInTransaction(referenced, "setName", new Object[] {"referenced"});
		// create a container package, and the test element
		TestUtil.executeInTransaction(referencingSystem, "Newpackage", new Object[0]);
		pkgs = Package_c.getManyEP_PKGsOnR1401(referencingSystem);
		Package_c containerPkg = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(containerPkg, "setName", new Object[] {"ee_part_container"});
		TestUtil.executeInTransaction(containerPkg, "Newexternalentityparticipant", new Object[0]);
		ExternalEntityParticipant_c part = ExternalEntityParticipant_c
				.getOneSQ_EEPOnR930(InteractionParticipant_c
						.getManySQ_PsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(containerPkg)));
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(part);
		assertFalse(
				"Formalize menu item was present when accessible elements were not.",
				UITestingUtilities.menuItemExists(getExplorerView()
						.getTreeViewer().getTree().getMenu(), "",
						"Formalize..."));
		// add a local possibility
		containerPkg.Newexternalentity();
		assertTrue(
				"Formalize menu item was not present when accessible elements were.",
				UITestingUtilities.menuItemExists(getExplorerView()
						.getTreeViewer().getTree().getMenu(), "",
						"Formalize..."));
		TestUtil.chooseItemInDialog(500, "referenced", true, true);
		TestUtil.chooseItemInDialog(500, "Unnamed Component", true, false);
		TestUtil.cancelDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer()
				.getTree().getMenu(), "Formalize...");
		// delete local possibility
		ExternalEntity_c ee = ExternalEntity_c.getOneS_EEOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(containerPkg));
		TestUtil.executeInTransaction(ee, "Dispose", new Object[0]);
		// enable IPRs
		projectNode.putBoolean(
				BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, true);
		assertTrue(
				"Formalize menu item was not present when accessible elements were.",
				UITestingUtilities.menuItemExists(getExplorerView()
						.getTreeViewer().getTree().getMenu(), "",
						"Formalize..."));
		TestUtil.chooseItemInDialog(500, "referenced");
		TestUtil.okToDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer()
				.getTree().getMenu(), "Formalize...");
		// assert participant is associated with IPR element
		ExternalEntity_c formalizedTo = ExternalEntity_c.getOneS_EEOnR933(part);
		assertTrue("Formalization did not occur properly.",
				formalizedTo == referenced);

		// open the container diagram, and zoom to fit
		CanvasTestUtilities.openDiagramEditor(containerPkg);
		GraphicalEditor editor = (GraphicalEditor) UITestingUtilities
				.getActiveEditor();
		editor.zoomAll();
	}

	/**
	 * Test formalization to an element that is in a different system
	 */
	public void testClassParticipantFormalizeWithIPRs() {
		IScopeContext projectScope = new ProjectScope(projectOne);
		Preferences projectNode = projectScope
				.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.putBoolean(
				BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, false);
		SystemModel_c referencingSystem = getSystemModel(projectOne.getName());
		SystemModel_c referencedSystem = getSystemModel(projectTwo.getName());
		// create a container package, and the formalize to element
		TestUtil.executeInTransaction(referencedSystem, "Newpackage", new Object[0]);
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(referencedSystem);
		Package_c referencedPkg = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(referencedPkg, "Newclass", new Object[0]);
		ModelClass_c referenced = ModelClass_c
				.getOneO_OBJOnR8001(PackageableElement_c
						.getOnePE_PEOnR8000(referencedPkg));
		TestUtil.executeInTransaction(referenced, "setName", new Object[] {"referenced1"} );
		// create a container package, and the test element
		TestUtil.executeInTransaction(referencingSystem, "Newpackage", new Object[0]);
		pkgs = Package_c.getManyEP_PKGsOnR1401(referencingSystem);
		Package_c containerPkg = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(containerPkg, "setName", new Object[] {"class_part_container"} );
		TestUtil.executeInTransaction(containerPkg, "Newclassparticipant", new Object[0]);
		ClassParticipant_c part = ClassParticipant_c
				.getOneSQ_CPOnR930(InteractionParticipant_c
						.getManySQ_PsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(containerPkg)));
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(part);
		assertFalse(
				"Formalize menu item was present when accessible elements were not.",
				UITestingUtilities.menuItemExists(getExplorerView()
						.getTreeViewer().getTree().getMenu(), "",
						"Formalize..."));
		// add a local possibility
		TestUtil.executeInTransaction(containerPkg, "Newclass", new Object[0]);
		assertTrue(
				"Formalize menu item was not present when accessible elements were.",
				UITestingUtilities.menuItemExists(getExplorerView()
						.getTreeViewer().getTree().getMenu(), "",
						"Formalize..."));
		TestUtil.chooseItemInDialog(500, "referenced1", true, true);
		TestUtil.chooseItemInDialog(500, "Unnamed Component", true, false);
		TestUtil.cancelDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer()
				.getTree().getMenu(), "Formalize...");
		// delete local possibility
		ModelClass_c clazz = ModelClass_c.getOneO_OBJOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(containerPkg));
		TestUtil.executeInTransaction(clazz, "Dispose", new Object[0]);
		// enable IPRs
		projectNode.putBoolean(
				BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, true);
		assertTrue(
				"Formalize menu item was not present when accessible elements were.",
				UITestingUtilities.menuItemExists(getExplorerView()
						.getTreeViewer().getTree().getMenu(), "",
						"Formalize..."));
		TestUtil.chooseItemInDialog(500, "referenced1");
		TestUtil.okToDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer()
				.getTree().getMenu(), "Formalize...");
		// assert participant is associated with IPR element
		ModelClass_c formalizedTo = ModelClass_c.getOneO_OBJOnR939(part);
		assertTrue("Formalization did not occur properly.",
				formalizedTo == referenced);

		// open the container diagram, and zoom to fit
		CanvasTestUtilities.openDiagramEditor(containerPkg);
		GraphicalEditor editor = (GraphicalEditor) UITestingUtilities
				.getActiveEditor();
		editor.zoomAll();
	}

	/**
	 * Test formalization to an element that is in a different system
	 */
	public void testClassInstanceParticipantFormalizeWithIPRs() {
		IScopeContext projectScope = new ProjectScope(projectOne);
		Preferences projectNode = projectScope
				.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.putBoolean(
				BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, false);
		SystemModel_c referencingSystem = getSystemModel(projectOne.getName());
		SystemModel_c referencedSystem = getSystemModel(projectTwo.getName());
		// create a container package, and the formalize to element
		TestUtil.executeInTransaction(referencedSystem, "Newpackage", new Object[0]);
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(referencedSystem);
		Package_c referencedPkg = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(referencedPkg, "Newclass", new Object[0]);
		ModelClass_c referenced = ModelClass_c
				.getOneO_OBJOnR8001(PackageableElement_c
						.getOnePE_PEOnR8000(referencedPkg));
		TestUtil.executeInTransaction(referenced, "setName", new Object[] {"referenced2"});
		// create a container package, and the test element
		TestUtil.executeInTransaction(referencingSystem, "Newpackage", new Object[0]);
		pkgs = Package_c.getManyEP_PKGsOnR1401(referencingSystem);
		Package_c containerPkg = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(containerPkg, "setName", new Object[] {"classinst_part_container"});
		TestUtil.executeInTransaction(containerPkg, "Newclassinstance", new Object[0]);
		ClassInstanceParticipant_c part = ClassInstanceParticipant_c
				.getOneSQ_CIPOnR930(InteractionParticipant_c
						.getManySQ_PsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(containerPkg)));
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(part);
		assertFalse(
				"Formalize menu item was present when accessible elements were not.",
				UITestingUtilities.menuItemExists(getExplorerView()
						.getTreeViewer().getTree().getMenu(), "",
						"Formalize..."));
		// add a local possibility
		TestUtil.executeInTransaction(containerPkg, "Newclass", new Object[0]);
		assertTrue(
				"Formalize menu item was not present when accessible elements were.",
				UITestingUtilities.menuItemExists(getExplorerView()
						.getTreeViewer().getTree().getMenu(), "",
						"Formalize..."));
		TestUtil.chooseItemInDialog(500, "referenced2", true, true);
		TestUtil.chooseItemInDialog(500, "Unnamed Component", true, false);
		TestUtil.cancelDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer()
				.getTree().getMenu(), "Formalize...");
		// delete local possibility
		ModelClass_c clazz = ModelClass_c.getOneO_OBJOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(containerPkg));
		TestUtil.executeInTransaction(clazz, "Dispose", new Object[0]);
		// enable IPRs
		projectNode.putBoolean(
				BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, true);
		assertTrue(
				"Formalize menu item was not present when accessible elements were.",
				UITestingUtilities.menuItemExists(getExplorerView()
						.getTreeViewer().getTree().getMenu(), "",
						"Formalize..."));
		TestUtil.chooseItemInDialog(500, "referenced2");
		TestUtil.okToDialog(1000);
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer()
				.getTree().getMenu(), "Formalize...");
		// assert participant is associated with IPR element
		ModelClass_c formalizedTo = ModelClass_c.getOneO_OBJOnR934(part);
		assertTrue("Formalization did not occur properly.",
				formalizedTo == referenced);

		// open the container diagram, and zoom to fit
		CanvasTestUtilities.openDiagramEditor(containerPkg);
		GraphicalEditor editor = (GraphicalEditor) UITestingUtilities
				.getActiveEditor();
		editor.zoomAll();
	}

	public void testSetupInitialProjects() {
		Ooaofooa.setPersistEnabled(true);
		IProject testproject1 = createXtUMLProject("Test Project 1");
		SystemModel_c sysModProject1 = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals("Test Project 1");
			}
		});
		createNewPackage("testDomain1", sysModProject1);
		String modelRootId = Ooaofooa.createModelRootId(
				testproject1, "testDomain1", true);
		Ooaofooa domainModelRoot = Ooaofooa.getInstance(modelRootId, true);
		Ooaofgraphics graphicsDomainModelRoot = Ooaofgraphics
				.getInstance(modelRootId);
		Package_c dom = Package_c.PackageInstance(domainModelRoot);
		// Open new domain
		CanvasTestUtils.openCanvasEditor(dom);
		// Create Subsystem in domain
		AbstractTool tool = UITestingUtilities.getTool("Package");
		UITestingUtilities.activateTool(tool);
		CanvasTestUtils.createMouseEvent(100, 100, "MouseDown");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseMove");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseUp");
		UITestingUtilities.deactivateTool(tool);
		Package_c subsystem = Package_c.PackageInstance(domainModelRoot,new Package_by_name_c("Unnamed Package"));
        Transaction t = Cl_c.Starttransaction(subsystem, "Rename subsystem");
		subsystem.setName("TestSS");
        Cl_c.Endtransaction(subsystem, t);
		while(Display.getCurrent().readAndDispatch());
		CanvasTestUtils.openCanvasEditor(subsystem);
		// Create Class in Subsystem
		tool = UITestingUtilities.getTool("Classes","Class");
		UITestingUtilities.activateTool(tool);
		CanvasTestUtils.createMouseEvent(100, 100, "MouseDown");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseMove");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseUp");
		UITestingUtilities.deactivateTool(tool);
		PackageableElement_c pes[] = PackageableElement_c.getManyPE_PEsOnR8000(subsystem);
		ModelClass_c mc = ModelClass_c.getOneO_OBJOnR8001(pes);
        t = Cl_c.Starttransaction(subsystem, "Rename model class");
		mc.setName("TestClass1");
        Cl_c.Endtransaction(subsystem, t);
		while(Display.getCurrent().readAndDispatch());
		openDescriptionEditor(mc);

		//Adding markers as part of i673 test
		Class uiTextTestClass = loadClassFromPlugin("com.mentor.nucleus.bp.ui.text.test", "UITextTest");//$NON-NLS-2$ //$NON-NLS-1$
		assertNotNull("Class UITextTest could not be loaded", uiTextTestClass);
		IFile mcFile = callGetExistingPlaceHolderFromManager(uiTextTestClass, mc, DescriptionEditorInputFactory.PLACEHOLDER_EXTENSION);
		assertNotNull(mcFile);
		IMarker marker = callCreateNewMarker(uiTextTestClass, mcFile);
		assertNotNull(marker);

        t = Cl_c.Starttransaction(mc, "New operation");
		mc.Newoperation();
        Cl_c.Endtransaction(mc, t);
		Operation_c op = Operation_c.OperationInstance(mc.getModelRoot());
        t = Cl_c.Starttransaction(mc, "Rename operation");
		op.setName("testOp");
        Cl_c.Endtransaction(op, t);
		while(Display.getCurrent().readAndDispatch());
		CanvasTestUtils.openActivityEditor(op);
		ActivityEditor ae = (ActivityEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		IDocument doc = ae.getDocumentProvider().getDocument(
				ae.getEditorInput());

		//Adding markers as part of i673 test
		IFile opFile = callGetExistingPlaceHolderFromManager(uiTextTestClass, op, ActivityEditorInputFactory.PLACEHOLDER_EXTENSION);
		assertNotNull(opFile);
		marker = callCreateNewMarker(uiTextTestClass, opFile);
		assertNotNull(marker);

		File modelFile = getDomainFile(testproject1,
				"testDomain1");
		long modelFileLength = modelFile.length();
		try {
			doc.replace(0, 0, "select any test from instances of KEY;");
		} catch (BadLocationException e) {
			fail("Bad Location Exception");
		}

		assertTrue(ae.isSaveOnCloseNeeded());
		assertTrue(ae.isDirty());
		ae.doSave(new NullProgressMonitor());
		while(Display.getCurrent().readAndDispatch());
		IProject testproject2 = createXtUMLProject("Test Project 2");
		SystemModel_c sysModProject2 = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals("Test Project 2");
			}
		});
		createNewPackage("testDomain2", sysModProject2);

        // create a sequence diagram to test that drawing a shape
        // after restart works correctly
        CanvasTestUtils.openCanvasEditor(subsystem);
        tool = UITestingUtilities.getTool("Actor");
        UITestingUtilities.activateTool(tool);
        CanvasTestUtils.createMouseEvent(400, 400, "MouseDown");
        CanvasTestUtils.createMouseEvent(500, 500, "MouseMove");
        CanvasTestUtils.createMouseEvent(500, 500, "MouseUp");
        UITestingUtilities.deactivateTool(tool);
  
        ActorParticipant_c sequence = ActorParticipant_c.ActorParticipantInstance(mc.getModelRoot());
        CanvasTestUtils.openCanvasEditor(sequence);
    }

	public void testCreatePackage() {
		test_id = "CreatePackage";
		final String projectName = "Package Test Project";
		IProject project = createXtUMLProject(projectName);
		assertNotNull(project);
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(projectName);
			}
		});
		assertNotNull(system);

		// create a package under the system
		CanvasUtilities.openCanvasEditor(system);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100,
				200, 200), "Package");

		Package_c newPackage = Package_c.getOneEP_PKGOnR1401(system);
		assertNotNull("Package was not successfully created.", newPackage);
        Transaction t = Cl_c.Starttransaction(newPackage, "Rename test element");
		newPackage.setName(test_id);
		Cl_c.Endtransaction(newPackage, t);

		graphicsModelRoot = Ooaofgraphics.getDefaultInstance();
		validateOrGenerateResults(ce, generateResults, true);
	}

	public void testCreatePackageInPackage() {
		test_id = "CreatePackageInPackage";
		final String projectName = "Package Test Project";
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(projectName);
			}
		});
		assertNotNull(system);

		Package_c newPackage = Package_c.getOneEP_PKGOnR1401(system);
		assertNotNull("Unable to find parent Package.", newPackage);

		// create a package under a package
		CanvasUtilities.openCanvasEditor(newPackage);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100,
				200, 200), "Package");

		Package_c nestedPackage = Package_c
				.getOneEP_PKGOnR8001(PackageableElement_c
						.getOnePE_PEOnR8000(newPackage));
		assertNotNull("Package was not successfully created.", nestedPackage);
        Transaction t = Cl_c.Starttransaction(nestedPackage, "Rename test element");
		nestedPackage.setName(test_id);
		Cl_c.Endtransaction(nestedPackage, t);
		graphicsModelRoot = Ooaofgraphics.getInstance(newPackage.getModelRoot().getId());
		validateOrGenerateResults(ce, generateResults, true);
	}

	public void testCreateSequenceInPackage() {
		test_id = "CreateSequenceInPackage";
		final String projectName = "Package Test Project";
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(projectName);
			}
		});
		assertNotNull(system);
	 //  createNewPackage("Sequence Diagram", system);
		Package_c newPackage = Package_c.getOneEP_PKGOnR1401(system);
		assertNotNull("Unable to find parent Package.", newPackage);

		// create a package under a package
		CanvasUtilities.openCanvasEditor(newPackage);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(300, 300,
				200, 200), "Package");

		Package_c seq = Package_c.getOneEP_PKGOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(newPackage) ,new Package_by_name_c("Unnamed Package")); 

		assertNotNull("Sequence was not successfully created.",
				seq);
        Transaction t = Cl_c.Starttransaction(seq, "Rename test element");
		seq.setName("Sequence Diagram");
		Cl_c.Endtransaction(seq, t);
		CanvasUtilities.openCanvasEditor(seq);
		graphicsModelRoot = Ooaofgraphics.getInstance(newPackage.getModelRoot().getId());
		validateOrGenerateResults(ce, generateResults, true);
	}

	public void testCreateUseCaseDiagramInSystem() {
		test_id = "CreateUseCaseDiagramInSystem";
		final String projectName = "Package Test Project";
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(projectName);
			}
		});
		assertNotNull(system);

		// create a package under a package
		CanvasUtilities.openCanvasEditor(system);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(500, 300,
				200, 200), "Package");

		Package_c[] packages = Package_c.getManyEP_PKGsOnR1401(system);
		Package_c newPackage = packages[packages.length - 1];
		assertNotNull("Use Case package was not successfully created.",
				newPackage);
        Transaction t = Cl_c.Starttransaction(newPackage, "Rename test element");
		newPackage.setName(test_id);
		Cl_c.Endtransaction(newPackage, t);

		graphicsModelRoot = Ooaofgraphics.getInstance(system.getModelRoot().getId());
		try {
		  validateOrGenerateResults(ce, generateResults, true);
		}
		finally {
		  // now create one of every element under the new package
		  CanvasUtilities.openCanvasEditor(newPackage);
		  ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		  UITestingUtilities.createShapeInDiagram(ce, new Rectangle(10, 10, 150,
				100), "Use Case", "Use Case");
		  UITestingUtilities.createShapeInDiagram(ce, new Rectangle(260, 10, 150,
				100), "Use Case", "Use Case");
		  UITestingUtilities.createShapeInDiagram(ce, new Rectangle(200, 210,
				150, 100), "Interaction", "Actor");
		  UITestingUtilities.createConnectorInDiagram(ce, new Point(80, 20),
				new Point(280, 20), "Use Case", "Generalization");
		  UITestingUtilities.createConnectorInDiagram(ce, new Point(80, 40),
				new Point(280, 40), "Use Case", "Include");
		  UITestingUtilities.createConnectorInDiagram(ce, new Point(80, 60),
				new Point(280, 60), "Use Case", "Extend");
		  UITestingUtilities.createConnectorInDiagram(ce, new Point(300, 280),
				new Point(280, 80), "Use Case", "Association");
		
		test_id = "CreateUseCasePackageContents";
		// graphically check creations
		graphicsModelRoot = Ooaofgraphics.getInstance(newPackage.getModelRoot().getId());
		validateOrGenerateResults(ce, generateResults, true);
	  }
	}

	public void testCreatePackageInPackageInPackage() {
		test_id = "CreatePackageInPackageInPackage";
		final String projectName = "Package Test Project";
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(projectName);
			}
		});
		assertNotNull(system);

		Package_c parentPackage = Package_c.getOneEP_PKGOnR1401(system);
		assertNotNull("Unable to find parent Package.", parentPackage);

		parentPackage = Package_c.getOneEP_PKGOnR8001(PackageableElement_c
				.getOnePE_PEOnR8000(parentPackage));
		assertNotNull("Unable to find parent Package.", parentPackage);
		
		// create a package under a package
		CanvasUtilities.openCanvasEditor(parentPackage);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100,
				200, 200), "Package");

		Package_c nestedPackage = Package_c
				.getOneEP_PKGOnR8001(PackageableElement_c
						.getOnePE_PEOnR8000(parentPackage));
		assertNotNull("Package was not successfully created.", nestedPackage);
        Transaction t = Cl_c.Starttransaction(nestedPackage, "Rename test element");
		nestedPackage.setName(test_id);
		Cl_c.Endtransaction(nestedPackage, t);
		graphicsModelRoot = Ooaofgraphics.getInstance(parentPackage.getModelRoot().getId());
		validateOrGenerateResults(ce, generateResults, true);
	}


	public void testSetupForInterfaceAssignment() {
		// create an interface in one package
		test_id = "InterfaceAssignmentSetup-1";
		final String projectName = "Package Test Project";
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(projectName);
			}
		});
		assertNotNull(system);

		Package_c parentPackage = Package_c.getOneEP_PKGOnR1401(system);
		assertNotNull("Unable to find parent Package.", parentPackage);

		Package_c ifacePkg = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(parentPackage))));
		assertNotNull("Unable to find Interface Package for setup", ifacePkg);

		CanvasUtilities.openCanvasEditor(ifacePkg);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100,
				200, 200), "Interface");

		Interface_c iface = Interface_c.getOneC_IOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(ifacePkg));
		assertNotNull("Interface was not successfully created.", iface);
        Transaction t = Cl_c.Starttransaction(iface, "Rename test element");
		iface.setName(test_id);
		Cl_c.Endtransaction(iface, t);

		graphicsModelRoot = Ooaofgraphics.getInstance(ifacePkg.getModelRoot().getId());
		validateOrGenerateResults(ce, generateResults, true);

		// create an interface reference in another
		// package
		test_id = "InterfaceAssignmentSetup-2";
		// create the package
		CanvasUtilities.openCanvasEditor(system);
		ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 400,
				200, 200), "Package");
		Package_c otherPackage = Package_c.getOneEP_PKGOnR1401(system, new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Unnamed Package");
			}

		});
		assertNotNull("Other package was not created successfully.", otherPackage);
        t = Cl_c.Starttransaction(otherPackage, "Rename test element");
		otherPackage.setName(test_id);
		Cl_c.Endtransaction(otherPackage, t);

		dispatchEvents(0);

		// create a component package
		CanvasUtilities.openCanvasEditor(otherPackage);
		ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100,
				200, 200), "Package");
		Package_c compPkg = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000((otherPackage)));
		assertNotNull("Package was not created successfully.", compPkg);
		Transaction tt = Cl_c.Starttransaction(compPkg, "Rename test element");
		compPkg.setName("Component Package");
		Cl_c.Endtransaction(compPkg, tt);
  
		
		// create a component
		CanvasUtilities.openCanvasEditor(compPkg);
		ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		
		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100,200, 200), "Components", "Component");
		Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(compPkg));
		assertNotNull("Component was not successfully created.", component);

		// create an interface reference
		UITestingUtilities.createConnectorInDiagram(ce, new Point(150, 150),
				new Point(450, 150), "Provided Interface");

		graphicsModelRoot = Ooaofgraphics.getInstance(compPkg.getModelRoot().getId());
		validateOrGenerateResults(ce, generateResults, true);
	}

	public void testSetupForComponentAssignment() {
		// create a component in one package
		test_id = "ComponentAssignmentSetup-1";
		final String projectName = "Package Test Project";
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(projectName);
			}
		});
		assertNotNull(system);

		Package_c parentPackage = Package_c.getOneEP_PKGOnR1401(system);
		assertNotNull("Unable to find parent Package.", parentPackage);

		Package_c compPkg = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(parentPackage))));
		assertNotNull("Unable to find Component Package for setup", compPkg);

		
//		
//		Transaction tt = null;
//		PackageableElement_c pes[] =PackageableElement_c.getManyPE_PEsOnR8000(compPkg);
//         for (int i = 0; i< pes.length ; i++)
//         {
//        	 tt = Cl_c.Starttransaction(pes[i], "Delete pckageable element");
//        	 pes[i].Dispose();
//        	 Cl_c.Endtransaction(compPkg,tt);
//         }
//         
		
		CanvasUtilities.openCanvasEditor(compPkg);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		
		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(500, 500,
				1300, 1300), "Components", "Component");
		

		Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(compPkg));
		assertNotNull("Component was not successfully created.", comp);
        Transaction t = Cl_c.Starttransaction(comp, "Rename test element");
		comp.setName(test_id);
		Cl_c.Endtransaction(comp, t);

		graphicsModelRoot = Ooaofgraphics.getInstance(compPkg.getModelRoot().getId());
		validateOrGenerateResults(ce, generateResults, true);

		// create an imported component in another
		// package
		test_id = "ComponentAssignmentSetup-2";
		// create the package
		CanvasUtilities.openCanvasEditor(system);
		ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 400,
				200, 200), "Package");
		Package_c otherPackage = Package_c.getOneEP_PKGOnR1401(system, new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Unnamed Package");
			}

		});
		assertNotNull("Other package was not created successfully.", otherPackage);
        t = Cl_c.Starttransaction(otherPackage, "Rename test element");
		otherPackage.setName(test_id);
		Cl_c.Endtransaction(otherPackage, t);

		dispatchEvents(0);

		// create a component package
		CanvasUtilities.openCanvasEditor(otherPackage);
		ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100,
				200, 200), "Package");
		compPkg = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000((otherPackage)));
		assertNotNull("Component Package was not created successfully.", compPkg);
		
		Transaction tt = Cl_c.Starttransaction(compPkg, "Rename test element");
		compPkg.setName("Component Reference Package");
		Cl_c.Endtransaction(compPkg, tt);

		

		// create an imported component
		CanvasUtilities.openCanvasEditor(compPkg);
		ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100,
				200, 200), "Components","Component Reference");
		ComponentReference_c icomp = ComponentReference_c.getOneCL_ICOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(compPkg));
		assertNotNull("Imported Component was not successfully created.", icomp);

		graphicsModelRoot = Ooaofgraphics.getInstance(compPkg.getModelRoot().getId());
		validateOrGenerateResults(ce, generateResults, true);
	}

	public void testSetupForDataTypeAssignment() {
		test_id = "DataTypeAssignmentSetup";
		// create a user data type under a package
		final String projectName = "Package Test Project";
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(projectName);
			}
		});
		assertNotNull(system);

		Package_c parentPackage = Package_c.getOneEP_PKGOnR1401(system);
		assertNotNull("Unable to find parent Package.", parentPackage);

		Package_c dtPkg = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(parentPackage))));
		assertNotNull("Unable to find Data Type Package for setup.", dtPkg);

		CanvasUtilities.openCanvasEditor(dtPkg);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100,
				200, 200), "User Data Type");

		UserDataType_c udt = UserDataType_c.getOneS_UDTOnR17(DataType_c
				.getOneS_DTOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(dtPkg)));
		assertNotNull("User Data Type was not successfully created.",
				udt);
        DataType_c dt = DataType_c.getOneS_DTOnR17(udt);
        Transaction t = Cl_c.Starttransaction(udt, "Rename test element");
		dt.setName(test_id);
		Cl_c.Endtransaction(udt, t);

		graphicsModelRoot = Ooaofgraphics.getInstance(dtPkg.getModelRoot().getId());
		validateOrGenerateResults(ce, generateResults, true);
	}

	static Class<?> loadClassFromPlugin(String pluginName, String className){
		//get the required class from the given plugin
        Bundle bundle = Platform.getBundle(pluginName);
        Class<?> clazz = null;
        String packageName = pluginName;
        try {
            clazz = bundle.loadClass(
                packageName + "." + className); //$NON-NLS-1$
        } catch (ClassNotFoundException e) {
            fail("Could not retrieve " + className + " class : " + e.toString());
        }
        return clazz;
	}

	static IFile callGetExistingPlaceHolderFromManager(Class<?> clazz, NonRootModelElement uut, String type){
		Object ret = null;
		try {
            Method method = clazz.getMethod(
                "getExistingPlaceHolderFromManager", //$NON-NLS-1$
                new Class[] {NonRootModelElement.class, type.getClass(), Object.class});
            ret = method.invoke(null, new Object[] {uut, type, new Object()});
        }
        catch (Exception e) {
            fail("Problem during call to getExistingPlaceHolderFromManager() " + e.toString());//$NON-NLS-1$
        }

        IFile file = null;
        if (ret instanceof IFile){
        	file = (IFile)ret;
        }
        return file;
	}

	static IMarker callCreateNewMarker(Class<?> clazz, IFile file){
		Object ret = null;
		try {
            Method method = clazz.getMethod(
                "createNewMarker", //$NON-NLS-1$
                new Class[] {IFile.class, int.class, String.class, String.class});
            ret = method.invoke(null, new Object[] {file, new Integer(1), "This is a test marker", IMarker.BOOKMARK}); //$NON-NLS-1$
        }
        catch (Exception e) {
            fail("Problem during call to CreateNewMarker()" + e.toString()); //$NON-NLS-1$
        }

        IMarker marker = null;
        if (ret instanceof IMarker){
        	marker = (IMarker)ret;
        }
        return marker;
	}
}
