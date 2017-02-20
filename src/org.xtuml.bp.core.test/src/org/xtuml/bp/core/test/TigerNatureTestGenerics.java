//========================================================================
//
//File: $RCSfile: TigerNatureTestGenerics.java,v $
//Version: $Revision: 1.10 $
//Modified: $Date: 2013/05/10 04:30:27 $
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
package org.xtuml.bp.core.test;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.XtUMLNature;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionException;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.ui.AddToIdentifierOnO_ATTRAction;
import org.xtuml.bp.core.ui.NewSystemWizard;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.ExplorerUtil;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.ui.canvas.test.CanvasTest;
import org.xtuml.bp.ui.properties.ModelPropertySourceProvider;
import org.xtuml.bp.ui.text.description.ShowDescriptionAction;
import org.xtuml.bp.utilities.ui.CanvasUtilities;

import junit.framework.TestCase;

@RunWith(OrderedRunner.class)
public class TigerNatureTestGenerics extends CanvasTest {

	String test_id = null;

	private static Selection selection = Selection.getInstance();

	public TigerNatureTestGenerics() {
		super("Default Project", null);
	}

	protected String getResultName() {
		return "TigerNatureTest" + "_" + test_id;
	}

	public static IProject createXtUMLProject(String name) {
		NewSystemWizard nsw = new NewSystemWizard();
		nsw.init(PlatformUI.getWorkbench(), null);
		nsw.setIsCreatedByUnitTest();
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), nsw);
		WizardNewProjectCreationPage wnpcp = (WizardNewProjectCreationPage) nsw
				.getStartingPage();
		wnpcp.setInitialProjectName(name);
		dialog.create();
		wnpcp.useDefaults();
		nsw.performFinish();
		dialog.close();
		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				name);
		return project;
	}

	public static PersistableModelComponent createNewPackage(String name,
			SystemModel_c systemModel) throws TransactionException {
		TransactionManager manager = systemModel.getTransactionManager();
		Transaction t = manager.startTransaction("new package", Ooaofooa
				.getDefaultInstance());
		systemModel.Newpackage();
		Package_c pkg = Package_c.getOneEP_PKGOnR1405(systemModel);
		pkg.setName(name);
		manager.endTransaction(t);
		PersistableModelComponent sysComponent = PersistenceManager
				.getComponent(systemModel);
		IPath path = sysComponent.getContainingDirectoryPath().append(
				name + "/" + name + "." + Ooaofooa.MODELS_EXT);
		PersistenceManager.findComponent(path);
		return PersistenceManager.findComponent(path);

	}

	@Override
	protected void initialSetup() throws Exception {
		CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION, true);
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		Display d = Display.getCurrent();
		while (d.readAndDispatch());
		BaseTest.dispatchEvents(0);
	}

	@After
	public void tearDown() throws Exception {
		BaseTest.dispatchEvents(0);
		super.tearDown();
	}

	private boolean checkForTreeItem(TreeViewer tree, String itemName) {
		tree.expandAll();
		tree.getTree().selectAll();
		TreeItem x[] = tree.getTree().getSelection();
		assertNotNull("Tree is empty", x);
		for (int i = 0; i < x.length; ++i) {

			String item = x[i].getText();

			if (item.equals(itemName)) {
				return true;
			}
		}
		return false;
	}

	private boolean checkForTigerNatureEntry(IProject project) throws Exception {
		return project.hasNature(XtUMLNature.getNatureId());
	}

	private boolean checkResourceNavForxtUMLProject(String string)
			throws Exception {
		IWorkbenchPage rp = PlatformUI.getWorkbench().showPerspective(
				"org.eclipse.ui.resourcePerspective",
				PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		CommonNavigator rn_view = (CommonNavigator) rp
				.findView("org.eclipse.ui.navigator.ProjectExplorer");
		TreeItem[] items = rn_view.getCommonViewer().getTree().getItems();
		for(int i = 0; i < items.length; i++) {
			if(items[i].getText().equals(string)) {
				return true;
			}
		}
		return false;
	}

	static public void openDescriptionEditor(final Object uut) {

		try {
			IWorkspaceRunnable r = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					IStructuredSelection ss = new StructuredSelection(uut);
					ShowDescriptionAction sda = new ShowDescriptionAction();
					Action a = new Action() {
					};
					sda.selectionChanged(a, ss);
					sda.run(a);
				}
			};
			CanvasUtilities.getWorkspace().run(r, null);
		} catch (CoreException x) {
			TestCase.fail("open editor problem");
		}

	}

	public ModelPropertySourceProvider getPropertiesSheet()
			throws PartInitException {
		ModelPropertySourceProvider mpsp = new ModelPropertySourceProvider();
		IViewReference refs[] = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getViewReferences();
		for (int j = 0; j < refs.length; j++) {
			if (refs[j].getPartName().equals("Properties")) {
				PropertySheet propertySheetMain = (PropertySheet) refs[j]
						.getPart(true);
				PropertySheetPage psp = (PropertySheetPage) propertySheetMain
						.getCurrentPage();
				psp.setPropertySourceProvider(mpsp);
				break;
			}
		}
		return mpsp;
	}

	private void deleteDirContents(File parent) {
		File[] children = parent.listFiles();
		for (int i = 0; i < children.length; ++i) {
			if (!children[i].isDirectory()) {
				assertTrue("Couldn't delete test directory", children[i]
						.delete());
			} else {
				deleteDirContents(children[i]);
				assertTrue("Couldn't delete test directory", children[i]
						.delete());
			}
		}
	}

	@Test
	public void testNewProjectDefaultPath() throws Exception {
		IProject testProject = createXtUMLProject("Test Project Defaults");
		// wait on any previous events to process
		BaseTest.dispatchEvents(0);
		assertTrue("Tiger nature not found for project",
				checkForTigerNatureEntry(testProject));
		assertTrue(
				"Did not find new project, Test Project Defaults, in the explorer view",
				checkForTreeItem(ExplorerUtil.getTreeViewer(),
						"Test Project Defaults"));
		assertTrue(
				"Project is not shown in the resource view as a xtUML project",
				checkResourceNavForxtUMLProject("Test Project Defaults"));
	}

	@Test
	public void testNewProjectNonDefaultPath() throws Exception {
		String location = "c:\\tiger_test";
		if(!Platform.getOS().contains("win")) {
			location = Platform.getInstanceLocation().getURL().toString()
					.replaceAll("file:", "")
					+ "../tiger_test";
		}
		File loc = new File(location);
		if (loc.exists()) {
			if (loc.isDirectory()) {
				deleteDirContents(loc);
			}
			assertTrue("Couldn't delete test directory", loc.delete());
		} else {
			loc.mkdir();
		}
		NewSystemWizard nsw = new NewSystemWizard();
		nsw.init(PlatformUI.getWorkbench(), null);
		nsw.setIsCreatedByUnitTest();
		WizardNewProjectCreationPage wnpcp = (WizardNewProjectCreationPage) nsw
				.getStartingPage();
		wnpcp.setInitialProjectName("Test Project Defaults");
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), nsw);
		dialog.create();
		assertFalse("Page was able to finish without a unique project name",
				nsw.canFinish());
		Composite widget = (Composite) wnpcp.getControl().getParent()
				.getChildren()[0];
		Control[] children = widget.getChildren();
		Control[] pc = ((Composite) children[0]).getChildren();
		Text pc1 = (Text) pc[1];
		pc1.setText("Test Project Non-Defaults");
		assertTrue("Page can't finish with a unique project name", wnpcp
				.isPageComplete());

		Composite g = (Composite) children[1];
		Control[] gc = g.getChildren();
		Button gc0 = (Button) gc[0];
		gc0.setSelection(false);
		gc0.notifyListeners(SWT.Selection, null);
		Text gc2 = (Text) gc[2];
		if (Platform.getOS().contains("win")) {
			gc2.setText("c:\\tiger_test");
		}
		else {
			gc2.setText(Platform.getInstanceLocation().getURL().toString()
					.replaceAll("file:", "")
					+ "../tiger_test");
		}

		nsw.performFinish();
		dialog.close();
		// wait on any previous events to process
		BaseTest.dispatchEvents(0);
		for (int i = 0; i < 100; i++) {
			while (Display.getCurrent().readAndDispatch()) {
				i = 0; // Reset outer loop
			}
		}

		assertTrue(
				"Did not find new project, Test Project Non-Defaults, in the explorer view",
				checkForTreeItem(ExplorerUtil.getTreeViewer(),
						"Test Project Non-Defaults"));
		assertTrue(
				"Project is not shown in the resource view as a xtUML project",
				checkResourceNavForxtUMLProject("Test Project Non-Defaults"));

		assertTrue("Project not created where specified", loc.exists());
	}

	@Test
	public void testCancelAddToIdentifier() throws Exception {

		TestUtil.executeInTransaction(m_sys, "Newpackage", new Object[0]);
		Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys);
		
		TestUtil.executeInTransaction(pkg, "Newclass", new Object[0]);
		
		
		ModelClass_c testclass = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg));
		
		testclass.Newattribute();
		Attribute_c attr = Attribute_c.getOneO_ATTROnR102(testclass);

		IFile mrFile = pkg.getFile();
		assertNotNull(mrFile);
		long start = mrFile.getModificationStamp();

		assertTrue(attr.Actionfilter("id", "not all"));
		assertFalse(attr.Actionfilter("id", "some"));
		selection.clear();
		selection.addToSelection(attr);

		Action a = new Action() {
		};
		AddToIdentifierOnO_ATTRAction atia = new AddToIdentifierOnO_ATTRAction();
		atia.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		BaseTest.dispatchEvents(0);
		Ooaofooa.setPersistEnabled(true);
		TestUtil.cancelDialog(200);
		atia.run(a);
		long end = mrFile.getModificationStamp();
		Ooaofooa.setPersistEnabled(false);
		assertTrue("Model file modified on cancel", start == end);

		assertTrue(attr.Actionfilter("id", "not all"));
		assertFalse(attr.Actionfilter("id", "some"));

	}
}