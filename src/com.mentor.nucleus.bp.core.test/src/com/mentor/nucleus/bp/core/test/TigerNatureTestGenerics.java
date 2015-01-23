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
package com.mentor.nucleus.bp.core.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
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

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionException;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.AddToIdentifierOnO_ATTRAction;
import com.mentor.nucleus.bp.core.ui.DeleteAction;
import com.mentor.nucleus.bp.core.ui.NewDomainWizard;
import com.mentor.nucleus.bp.core.ui.NewSystemWizard;
import com.mentor.nucleus.bp.core.ui.RenameAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.WizardNewDomainCreationPage;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CVSUtils;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.properties.ClassO_OBJPropertySource;
import com.mentor.nucleus.bp.ui.properties.ModelPropertySourceProvider;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditor;
import com.mentor.nucleus.bp.ui.text.description.ShowDescriptionAction;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public class TigerNatureTestGenerics extends CanvasTest {

	String test_id = null;

	private static Selection selection = Selection.getInstance();

	public TigerNatureTestGenerics(String name) {
		super("Default Project", name);
	}

	protected String getResultName() {
		return "TigerNatureTest" + "_" + test_id;
	}

	public static PersistableModelComponent createNewDomain(String name, SystemModel_c systemModel) {
		NewDomainWizard ndw = new NewDomainWizard();
		ndw.init(PlatformUI.getWorkbench(),
				new StructuredSelection(systemModel));
		ndw.addPages();
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), ndw);
		dialog.create();
		WizardNewDomainCreationPage wndcp = (WizardNewDomainCreationPage) ndw
				.getStartingPage();
		String fieldValue = wndcp.getSystemNameFieldValue();
		assertTrue("Project field is not filled in", fieldValue
				.equals(systemModel.getName()));
		wndcp.setDomainNameFieldValue(name);
        wndcp.setUseTemplate(false);
		wndcp.setPageComplete(true);
		ndw.performFinish();
		
		PersistableModelComponent sysComponent = PersistenceManager.getComponent(systemModel);
		IPath path = sysComponent.getContainingDirectoryPath().append(name + "/" + name + "." + Ooaofooa.MODELS_EXT);

		return PersistenceManager.findComponent(path);
		
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

	protected void setUp() throws Exception {
		super.setUp();
		Display d = Display.getCurrent();
		while (d.readAndDispatch());
		BaseTest.dispatchEvents(0);
	}

	protected void tearDown() throws Exception {
		Display d = Display.getCurrent();
		while (d.readAndDispatch());

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
		return project.hasNature("com.mentor.nucleus.bp.core.xtumlnature");
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

	public boolean checkIfPersisted(IProject project, NonRootModelElement me,
			String statement) throws IOException {
		IFile file = me.getFile();

		File diskModelFile = file.getLocation().toFile();
		boolean result = searchForInsertStatement(statement, diskModelFile);
		return result;
	}

	public String getNewSubsystemString(Subsystem_c subsystem) {
		String newSubsystem = "S_SS\n\tVALUES (" + '"' + subsystem.getSs_id()
				+ '"' + ",\n\t'" + subsystem.getName() + "',\n\t'"
				+ subsystem.getDescrip() + "',\n\t'" + subsystem.getPrefix()
				+ "',\n\t" + Integer.toString(subsystem.getNum_rng()) + ",\n\t"
				+ '"' + subsystem.getDom_id() + '"' + ",\n\t" + '"'
				+ subsystem.getConfig_id() + '"' + ");";
		return newSubsystem;
	}
	private String getNewPackageString(Package_c pkg) {
		String newPackage = "EP_PKG\n\tVALUES (" + "\"" + pkg.getPackage_id()
				+ "\"" + ",\n\t" + "\"" + pkg.getSys_id() + "\"" + ",\n\t"
				+ "\"" + pkg.getDirect_sys_id() + "\"" + ",\n\t" + "'"
				+ pkg.getName().replaceAll("'", "''") + "'" + ",\n\t" + "'"
				+ pkg.getDescrip().replaceAll("'", "''") + "'" + ",\n\t"
				+ pkg.getNum_rng() + ");";
		return newPackage;

	}

	public String getClassString(ModelClass_c modelClass) {
		String classInsert = "O_OBJ\n\tVALUES (" + '"' + modelClass.getObj_id()
				+ '"' + ",\n\t'" + modelClass.getName() + "',\n\t"
				+ modelClass.getNumb() + ",\n\t'" + modelClass.getKey_lett()
				+ "',\n\t'" + modelClass.getDescrip() + "',\n\t" + '"'
				+ modelClass.getSs_id() + '"' + ");";
		return classInsert;
	}

	public String getOperationString(Operation_c activity) {
		String activityInsert = "O_TFR\n\tVALUES (" + '"'
				+ activity.getTfr_id() + '"' + ",\n\t" + '"'
				+ activity.getObj_id() + '"' + ",\n\t'" + activity.getName()
				+ "',\n\t'" + activity.getDescrip() + "',\n\t" + '"'
				+ activity.getDt_id() + '"' + ",\n\t"
				+ activity.getInstance_based() + ",\n\t'"
				+ activity.getAction_semantics() + "',\n\t"
				+ activity.getSuc_pars() + ",\n\t'"
				+ activity.getReturn_dimensions() + "',\n\t" + '"'
				+ activity.getPrevious_tfr_id() + '"' + ");";
		return activityInsert;
	}

	public String getConnectorString(Connector_c con) {
		String conInsert = "GD_CON\n\tVALUES (" + '"' + con.getElementid()
				+ '"' + ",\n\t" + '"' + con.getAssoc_elementid() + '"' + ");";
		return conInsert;
	}

	public String getGraphicalElementString(GraphicalElement_c ge) {
		String geInsert = "GD_GE\n\tVALUES (" + '"' + ge.getElementid() + '"'
				+ ",\n\t" + '"' + ge.getDiagramid() + '"' + ",\n\t" + '"'
				+ ge.getOoa_id() + '"' + ",\n\t" + ge.getOoa_type() + ",\n\t"
				+ 0 + ",\n\t" + "'" + ge.getRepresents_path() + "'" + ");";
		return geInsert;
	}

	public boolean searchForInsertStatement(String statement, File file)
			throws IOException {
		FileInputStream fis = new FileInputStream(file);
		String startOf = "INSERT INTO ";
		String endOf = ";";
		byte[] byteBeingProcessed = new byte[1];
		byte[] startOfStatement = new byte[startOf.toCharArray().length];
		String startOfStatementString = "";
		String statementReadString = "";
		boolean result = false;
		int x = 0;
		int n = 0;
		x = processLineUntilNL(fis, x);
		while (!startOfStatementString.equals(startOf)) {
			fis.read(startOfStatement);
			startOfStatementString = new String(startOfStatement);
			if (startOfStatementString.equals(startOf)) {
				x = x + startOfStatement.length;
				byte statementRead[] = new byte[statement.length()];
				int y = 0;
				String stringBeingProcessed = "";
				while (!stringBeingProcessed.equals(endOf)) {
					byteBeingProcessed = new byte[1];
					x = x + 1;
					fis.read(byteBeingProcessed);
					if (y < statementRead.length) {
						statementRead[y] = byteBeingProcessed[0];
					}
					y = y + 1;
					statementReadString = new String(statementRead);
					stringBeingProcessed = new String(byteBeingProcessed);
					if (stringBeingProcessed.equals(endOf)) {
						// Check next chars for \nINSERT INTO
						String nextStartOfStatementString = "";
						byte[] nextStartOfStatement = new byte[startOfStatement.length + 1];
						n = fis.read(nextStartOfStatement);
						nextStartOfStatementString = new String(
								nextStartOfStatement);
						String startOfStmt = new String("\n" + startOf);
						if (!nextStartOfStatementString.equals(startOfStmt)) {
							if ((x + nextStartOfStatement.length) < file
									.length()) {
								// found ; in action language keep processing
								stringBeingProcessed = "";
								fis.close();
								fis = new FileInputStream(file);
								fis.skip(x);
							}
						}
					}
				}
				if (statementReadString.equals(statement)) {
					fis.close();
					result = true;
					break;
				} else {
					startOfStatementString = "";
					if (n == 1) {
						fis.close();
						break;
					}
				}
			} else {
				fis.close();
				fis = new FileInputStream(file);
				fis.skip(x);
				x = processLineUntilNL(fis, x);
			}
		}
		//}
		return result;
	}


	private static int processLineUntilNL(FileInputStream fis, int x)
			throws IOException {
		String stringBeingProcessed = "";
		byte[] byteBeingProcessed = new byte[1];
		while (!stringBeingProcessed.equals("\n")) {
			x = x + 1;
			fis.read(byteBeingProcessed);
			stringBeingProcessed = new String(byteBeingProcessed);
		}
		return x;
	}


	private File getDomainFile(IProject project, String domainName) {
		PersistableModelComponent root = PersistenceManager.getRootComponent(project);

		IPath path = root.getContainingDirectoryPath().append(
				domainName + "/" + domainName + "." + Ooaofooa.MODELS_EXT);
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		return file.getLocation().toFile();
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

	private boolean checkForDirtyFlag(String pkg)
			throws OperationCanceledException, InterruptedException {
		ExplorerUtil.expandAll();
		ExplorerUtil.getTreeViewer().getTree().selectAll();
		TreeItem items[] = ExplorerUtil.getTreeViewer().getTree()
				.getSelection();
		for (int i = 0; i < items.length; i++) {
			if (items[i].getText().startsWith(">" + pkg)) {
				return true;
			}
		}
		return false;
	}

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

	public void testNewProjectNonDefaultPath() throws Exception {
		String location = "c:\\tiger_test";
		File loc = new File(location);
		if (loc.exists()) {
			if (loc.isDirectory()) {
				deleteDirContents(loc);
			}
			assertTrue("Couldn't delete test directory", loc.delete());
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
		gc2.setText("c:\\tiger_test");

		nsw.performFinish();
		dialog.close();
		// wait on any previous events to process
		BaseTest.dispatchEvents(0);

		assertTrue(
				"Did not find new project, Test Project Non-Defaults, in the explorer view",
				checkForTreeItem(ExplorerUtil.getTreeViewer(),
						"Test Project Non-Defaults"));
		assertTrue(
				"Project is not shown in the resource view as a xtUML project",
				checkResourceNavForxtUMLProject("Test Project Non-Defaults"));

		assertTrue("Project not created where specified", loc.exists());
	}

	//		public void testNewPackageWithProjectSelected() throws Exception {
	//			NewDomainWizard ndw = new NewDomainWizard();
	//	    	Ooaofooa mr = Ooaofooa.getDefaultInstance();
	//			SystemModel_c sysMod = SystemModel_c.SystemModelInstance(mr);
	//			String projectName = sysMod.getName();
	//			ndw.init(PlatformUI.getWorkbench(), new StructuredSelection(sysMod));
	//			ndw.addPages();
	//			WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
	//					.getActiveWorkbenchWindow().getShell(), ndw);
	//			dialog.create();
	//			WizardNewDomainCreationPage wndcp = (WizardNewDomainCreationPage) ndw
	//					.getStartingPage();
	//			String fieldValue = wndcp.getSystemNameFieldValue();
	//			assertTrue("Project field is not filled in", fieldValue
	//					.equals(projectName));
	//			wndcp.dispose();
	//			ndw.dispose();
	//		}
	//
	//	public void testNewPackageWithNothingSelected() throws Exception {
	//		NewDomainWizard ndw = new NewDomainWizard();
	//		ndw.init(PlatformUI.getWorkbench(), new StructuredSelection());
	//		ndw.addPages();
	//		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
	//				.getActiveWorkbenchWindow().getShell(), ndw);
	//		dialog.create();
	//		WizardNewDomainCreationPage wndcp = (WizardNewDomainCreationPage) ndw
	//				.getStartingPage();
	//		String fieldValue = wndcp.getSystemNameFieldValue();
	//		assertTrue("Project field is filled in", fieldValue.equals(""));
	//		Combo systemField = wndcp.getSystemNameFieldCombo();
	//		String items[] = systemField.getItems();
	//		SystemModel_c sysMods[] = SystemModel_c.SystemModelInstances(Ooaofooa
	//				.getDefaultInstance());
	//		for (int i = 0; i < sysMods.length; i++) {
	//			assertTrue("Could not find " + sysMods[i].getName()
	//					+ " in the domain wizard combo box",
	//					checkForSysModInDomainWizard(items, sysMods[i].getName()));
	//		}
	//		wndcp.dispose();
	//		ndw.dispose();
	//	}
	//
	//	public void testNewDomainWithProjectSelectedDefaultTemplate()
	//			throws Exception {
	//		NewDomainWizard ndw = new NewDomainWizard();
	//		SystemModel_c sysMod = SystemModel_c.SystemModelInstance(Ooaofooa
	//				.getDefaultInstance(), new ClassQueryInterface_c() {
	//			public boolean evaluate(Object candidate) {
	//				SystemModel_c selected = (SystemModel_c) candidate;
	//				return selected.getName().equals("Test Project Defaults");
	//			}
	//		});
	//		String projectName = sysMod.getName();
	//		ndw.init(PlatformUI.getWorkbench(), new StructuredSelection(sysMod));
	//		ndw.addPages();
	//		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
	//				.getActiveWorkbenchWindow().getShell(), ndw);
	//		dialog.create();
	//		WizardNewDomainCreationPage wndcp = (WizardNewDomainCreationPage) ndw
	//				.getStartingPage();
	//		String fieldValue = wndcp.getSystemNameFieldValue();
	//		assertTrue("Project field is not filled in", fieldValue
	//				.equals(projectName));
	//		wndcp.setDomainNameFieldValue("testUnique1");
	//		wndcp.setPageComplete(true);
	//		assertTrue("Page cannot finish with unique domain name", ndw
	//				.canFinish());
	//		ndw.performFinish();
	//		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
	//		assertTrue("Could not find newly created domain, testUnique1",
	//				checkForTreeItem(ExplorerUtil.getTreeViewer(), "testUnique1"));
	//		String cdts[] = getCoreDatatypes();
	//		IProject testProject = ResourcesPlugin.getWorkspace().getRoot()
	//				.getProject(projectName);
	//		
	//
	//		
	//		
	//		PersistenceManager manager = PersistenceManager.getDefaultInstance(); 
	//		PersistableModelComponent sysComponent = manager.getRootComponent(testProject);
	//		
	//		IPath path = sysComponent.getContainingDirectoryPath().append("testUnique1" + "/" + "testUnique1" + "." + Ooaofooa.MODELS_EXT);
	//
	//		PersistableModelComponent domainComponent = PersistenceManager.findComponent(path);
	//		domainComponent.loadComponentAndChildren(new NullProgressMonitor());
	//		
	//		Ooaofooa domainModelRoot = (Ooaofooa)domainComponent.getRootModelElement().getModelRoot();
	//		for (int i = 0; i < cdts.length; i++) {
	//			final String exp_dt = cdts[i];
	//			DataType_c dt = DataType_c.DataTypeInstance(domainModelRoot,
	//					new ClassQueryInterface_c() {
	//						public boolean evaluate(Object candidate) {
	//							DataType_c selected = (DataType_c) candidate;
	//							return selected.getName().equals(exp_dt);
	//						}
	//					});
	//			assertNotNull("Could not find data type with name: " + exp_dt, dt);
	//		}
	//	}
	//
	//	public void testNewDomainWithProjectSelectedNonUniqueName() {
	//		NewDomainWizard ndw = new NewDomainWizard();
	//		SystemModel_c sysMod = SystemModel_c.SystemModelInstance(Ooaofooa
	//				.getDefaultInstance(), new ClassQueryInterface_c() {
	//			public boolean evaluate(Object candidate) {
	//				SystemModel_c selected = (SystemModel_c) candidate;
	//				return selected.getName().equals("Test Project Defaults");
	//			}
	//		});
	//		ndw.init(PlatformUI.getWorkbench(), new StructuredSelection(sysMod));
	//		ndw.addPages();
	//		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
	//				.getActiveWorkbenchWindow().getShell(), ndw);
	//		dialog.create();
	//		WizardNewDomainCreationPage wndcp = (WizardNewDomainCreationPage) ndw
	//				.getStartingPage();
	//		wndcp.setDomainNameFieldValue("testUnique1");
	//		assertFalse("Page can finish with out unique domain name", ndw
	//				.canFinish());
	//		wndcp.dispose();
	//		ndw.dispose();
	//	}
	//
	//	public void testNewDomainWithProjectSelectedModelTemplate()
	//			throws Exception {
	//		String templateFileName = m_workspace_path + 
	//	    	"../com.mentor.nucleus.bp.io.mdl.test/" + Ooaofooa.MODELS_DIRNAME + 
	//			"/odms." + Ooaofooa.MODELS_EXT;
	//		createModelWithTemplate("testModelTemplate", templateFileName);
	//		assertTrue(
	//				"Did not find newly created domain using model template, testModelTemplate",
	//				checkForTreeItem(ExplorerUtil.getTreeViewer(), "testModelTemplate"));
	//	}
	//
	//	public void testNewDomainWithProjectSelectedSqlTemplate() throws Exception {
	//		String templateFileName = m_workspace_path + 
	//		    "../com.mentor.nucleus.bp.io.sql.test/" + Ooaofooa.MODELS_DIRNAME + "/odms.sql";
	//		createModelWithTemplate("testSqlTemplate", templateFileName);
	//		assertTrue(
	//				"Did not find newly created domain using sql template, testSqlTemplate",
	//				checkForTreeItem(ExplorerUtil.getTreeViewer(), "testSqlTemplate"));
	//	}
	//
	//	public void testNewDomainWithDuplicatesInSubsystem() throws Exception {
	//		String templateFileName = m_workspace_path + Ooaofooa.MODELS_DIRNAME + 
	//			"/DuplicateNames." + Ooaofooa.MODELS_EXT;
	//		TestUtil.dismissDialog(1000);
	//	    createModelWithTemplate("Duplicates", templateFileName, true);
	//		assertTrue(
	//				"Did not find newly created domain using xtuml template, Duplicates",
	//				checkForTreeItem(ExplorerUtil.getTreeViewer(), "Duplicates"));
	//		String temp = TestUtil.dialogText;
	//        BaseTest.ensureFolderExists(m_workspace_path+"actual_results/DuplicatesTest");
	//        FileWriter writer = new FileWriter(m_workspace_path+"actual_results/DuplicatesTest/DuplicatesRenamed1.txt");
	//        writer.write(temp);
	//        writer.flush();
	//        TestingUtilities.fileContentsCompare( 
	//        	m_workspace_path+"expected_results/DuplicatesTest/DuplicatesRenamed1.txt", 
	//        	m_workspace_path+"actual_results/DuplicatesTest/DuplicatesRenamed1.txt" ); 
	//	}
	//	
	//	public void testNewDomainWithDuplicatesInMultipleSubsystems() throws Exception {
	//		String templateFileName = m_workspace_path + Ooaofooa.MODELS_DIRNAME + 
	//			"/DuplicateNames2." + Ooaofooa.MODELS_EXT;
	//		TestUtil.dismissDialog(1000);
	//	    createModelWithTemplate("Duplicates2", templateFileName, true);
	//		assertTrue(
	//				"Did not find newly created domain using xtuml template, Duplicates2",
	//				checkForTreeItem(ExplorerUtil.getTreeViewer(), "Duplicates2"));
	//		String temp = TestUtil.dialogText;
	//        BaseTest.ensureFolderExists(m_workspace_path+"actual_results/DuplicatesTest");
	//        FileWriter writer = new FileWriter(m_workspace_path+"actual_results/DuplicatesTest/DuplicatesRenamed2.txt");
	//        writer.write(temp);
	//        writer.flush();
	//        TestingUtilities.fileContentsCompare( 
	//        	m_workspace_path+"expected_results/DuplicatesTest/DuplicatesRenamed2.txt", 
	//        	m_workspace_path+"actual_results/DuplicatesTest/DuplicatesRenamed2.txt" ); 
	//	}
	//	
	//	
	//	private void createModelWithTemplate(String modelName, String templateFileName, boolean hasDuplicates) {
	//		NewDomainWizard ndw = new NewDomainWizard();
	//		SystemModel_c sysMod = SystemModel_c.SystemModelInstance(Ooaofooa
	//				.getDefaultInstance(), new ClassQueryInterface_c() {
	//			public boolean evaluate(Object candidate) {
	//				SystemModel_c selected = (SystemModel_c) candidate;
	//				return selected.getName().equals("Test Project Defaults");
	//			}
	//		});
	//		ndw.init(PlatformUI.getWorkbench(), new StructuredSelection(sysMod));
	//		ndw.addPages();
	//		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
	//				.getActiveWorkbenchWindow().getShell(), ndw);
	//		dialog.create();
	//		WizardNewDomainCreationPage wndcp = (WizardNewDomainCreationPage) ndw
	//				.getStartingPage();
	//		wndcp.setDomainNameFieldValue(modelName);
	//		wndcp.setTemplateLocationFieldValue(templateFileName);
	//		ndw.performFinish();
	//		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
	//	}
	//	
	//	private void createModelWithTemplate(String modelName, String templateFileName) {
	//		createModelWithTemplate(modelName, templateFileName, false);
	//	}
	//
	//	public void testNewDomainWithProjectSelectedBadTemplate() throws Exception {
	//		NewDomainWizard ndw = new NewDomainWizard();
	//		SystemModel_c sysMod = SystemModel_c.SystemModelInstance(Ooaofooa
	//				.getDefaultInstance(), new ClassQueryInterface_c() {
	//			public boolean evaluate(Object candidate) {
	//				SystemModel_c selected = (SystemModel_c) candidate;
	//				return selected.getName().equals("Test Project Defaults");
	//			}
	//		});
	//		ndw.init(PlatformUI.getWorkbench(), new StructuredSelection(sysMod));
	//		ndw.addPages();
	//		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
	//				.getActiveWorkbenchWindow().getShell(), ndw);
	//		dialog.create();
	//		WizardNewDomainCreationPage wndcp = (WizardNewDomainCreationPage) ndw
	//				.getStartingPage();
	//		CoreTest.waitForJobs();
	//		wndcp.setDomainNameFieldValue("testBadTemplate");
	//		wndcp.setTemplateLocationFieldValue(m_workspace_path
	//				+ "../com.mentor.nucleus.bp.io.mdl.test/" + Ooaofooa.MODELS_DIRNAME + "/bad." + Ooaofooa.MODELS_EXT);
	//		TestUtil.dismissDialog(200);
	//		ndw.performFinish();
	//		dialog.close();
	//        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
	//        
	//		assertFalse(
	//				"Found newly created domain using bad template, testBadTemplate",
	//				checkForTreeItem(ExplorerUtil.getTreeViewer(), "testBadTemplate"));
	//		
	//		// the log file should exist with the import error logged
	//        File in_fh = Platform.getLogFileLocation().toFile();
	//        assertTrue("Log file doesn't exist", in_fh.exists());
	//        in_fh.delete();
	//	}
	//
	//	public void testNewDomainWithProjectSelectedModelMatchesTemplate() throws Exception {
	//		String mdlTemplateFileName = m_workspace_path + 
	//    	"../com.mentor.nucleus.bp.io.mdl.test/" + Ooaofooa.MODELS_DIRNAME + 
	//		"/odms." + Ooaofooa.MODELS_EXT;
	//		String sqlTemplateFileName = m_workspace_path + 
	//		    "../com.mentor.nucleus.bp.io.sql.test/" + Ooaofooa.MODELS_DIRNAME + "/odms.sql";
	//		
	//		// The value of S_DOM.Name is "odms2" in the template files
	//		
	//		createModelWithTemplate("odms2", mdlTemplateFileName);
	//		assertTrue(
	//				"Did not find newly created domain using xtuml template, odms2",
	//				checkForTreeItem(ExplorerUtil.getTreeViewer(), "odms2"));
	//
	//		createModelWithTemplate("odms3", mdlTemplateFileName);
	//		assertTrue(
	//				"Did not find newly created domain using xtuml template, odms2",
	//				checkForTreeItem(ExplorerUtil.getTreeViewer(), "odms2"));
	//		assertTrue(
	//				"Did not find newly created domain using xtuml template, odms3",
	//				checkForTreeItem(ExplorerUtil.getTreeViewer(), "odms3"));
	//
	//		createModelWithTemplate("odms4", sqlTemplateFileName);
	//		assertTrue(
	//				"Did not find newly created domain using xtuml template, odms2",
	//				checkForTreeItem(ExplorerUtil.getTreeViewer(), "odms2"));
	//		assertTrue(
	//				"Did not find newly created domain using xtuml template, odms3",
	//				checkForTreeItem(ExplorerUtil.getTreeViewer(), "odms3"));
	//		assertTrue(
	//				"Did not find newly created domain using sql template, odms4",
	//				checkForTreeItem(ExplorerUtil.getTreeViewer(), "odms4"));
	//		
	//	}
	//
	//	
	public void testPKGPersistence() throws Exception {
		final IProject corePersistenceProject = createXtUMLProject("CorePersistenceTestProject");
		SystemModel_c sysMod = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(
						corePersistenceProject.getName());
			}
		});
		Ooaofooa.setPersistEnabled(true);
		PersistableModelComponent PackageComponent = createNewPackage(
				"CorePersistencePkg1", sysMod);

		PackageComponent.loadComponentAndChildren(new NullProgressMonitor());

		Ooaofooa packageModelRoot = (Ooaofooa) PackageComponent
				.getRootModelElement().getModelRoot();
		Package_c dom = Package_c.PackageInstance(packageModelRoot);
		// Open new domain
		CanvasTestUtils.openCanvasEditor(dom);
		// Create Subsystem in domain
		AbstractTool tool = UITestingUtilities.getTool("Package");
		UITestingUtilities.activateTool(tool);
		CanvasTestUtils.createMouseEvent(100, 100, "MouseDown");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseMove");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseUp");
		Package_c subsystem = Package_c.PackageInstance(packageModelRoot);
		Ooaofooa.setPersistEnabled(false);
		assertTrue("Newly created Package was not persisted to disk",
				checkIfPersisted(corePersistenceProject, subsystem,
						getNewPackageString(subsystem)));
		UITestingUtilities.deactivateTool(tool);
	}

	public void testActivityPersistence() throws Exception {
		final IProject activityPersistenceProject = createXtUMLProject("ActivityPersistenceTestProject");
		SystemModel_c sysMod = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(
						activityPersistenceProject.getName());
			}
		});
		Ooaofooa.setPersistEnabled(true);
		PersistableModelComponent PackageComponent = createNewPackage(
				"ActivityPersistencePkg1", sysMod);
		PackageComponent.loadComponentAndChildren(new NullProgressMonitor());
		Ooaofooa packageModelRoot = (Ooaofooa) PackageComponent
				.getRootModelElement().getModelRoot();
		Package_c dom = Package_c.PackageInstance(packageModelRoot);
		// Open new domain
		CanvasTestUtils.openCanvasEditor(dom);
		// Create Subsystem in domain
		AbstractTool tool = UITestingUtilities.getTool("Package");
		UITestingUtilities.activateTool(tool);
		CanvasTestUtils.createMouseEvent(100, 100, "MouseDown");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseMove");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseUp");
		Package_c subsystem = Package_c.PackageInstance(packageModelRoot);

		TransactionManager manager = dom.getTransactionManager();
		Transaction t = manager
				.startTransaction("creating a class and operation", Ooaofooa
						.getDefaultInstance());
		subsystem.setName("Activity Test SS");
		subsystem.Newclass();
		PackageableElement_c[] temp = PackageableElement_c
				.getManyPE_PEsOnR8000(subsystem);
		ModelClass_c testclass = ModelClass_c.getOneO_OBJOnR8001(temp);
		testclass.setName("TestActivityClass 1");
		testclass.Newoperation();
		Operation_c activity = Operation_c.getOneO_TFROnR115(testclass);
		activity.setName("testOperation");
		manager.endTransaction(t);

		CanvasTestUtils.openActivityEditor(activity);
		ActivityEditor ae = (ActivityEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		IDocument doc = ae.getDocumentProvider().getDocument(
				ae.getEditorInput());
		File modelFile = getDomainFile(activityPersistenceProject,
				"ActivityPersistenceDom1");
		long modelFileLength = modelFile.length();
		try {
			doc.replace(0, 0, "x = 0;");
		} catch (BadLocationException e) {
			fail("Bad Location Exception");
		}

		assertTrue(ae.isSaveOnCloseNeeded());
		assertTrue(ae.isDirty());

		File diskModelFile = getDomainFile(activityPersistenceProject,
				"ActivityPersistenceDom1");
		assertTrue("Changes were persisted to disk",
				modelFileLength == diskModelFile.length());
		ae.doSave(new NullProgressMonitor());
		Ooaofooa.setPersistEnabled(false);
		ae.close(false);
		assertTrue("Saved changes to activity were not persisted",
				checkIfPersisted(activityPersistenceProject, activity,
						getOperationString(activity)));
	}

	public void testDescriptionPersistence() throws Exception {
		final IProject descriptionPersistenceProject = createXtUMLProject("DescriptionPersistenceTestProject");
		SystemModel_c sysMod = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(
						descriptionPersistenceProject.getName());
			}
		});
		Ooaofooa.setPersistEnabled(true);
		createNewPackage(
				"DescriptionPersistencePkg1", sysMod);
		
		Package_c dom = Package_c.getOneEP_PKGOnR1405(sysMod,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Package_c selected = (Package_c) candidate;
						return selected.getName().equals(
								"DescriptionPersistencePkg1");
					}

				});

		TransactionManager manager = dom.getTransactionManager();
		Transaction t = manager.startTransaction(
				"creating a sub-system and model class", Ooaofooa
						.getDefaultInstance());

		dom.Newpackage();
		PackageableElement_c[] temp = PackageableElement_c
				.getManyPE_PEsOnR8000(dom);
		Package_c subsystem = Package_c.getOneEP_PKGOnR8001(temp);
		subsystem.setName("Description Test SS");
		subsystem.Newclass();
		temp = PackageableElement_c.getManyPE_PEsOnR8000(subsystem);
		ModelClass_c testclass = ModelClass_c.getOneO_OBJOnR8001(temp);
		testclass.setName("TestDescriptionClass 1");
		manager.endTransaction(t);

		openDescriptionEditor(testclass);
		DescriptionEditor de = (DescriptionEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		IDocument doc = de.getDocumentProvider().getDocument(
				de.getEditorInput());
		File modelFile = getDomainFile(descriptionPersistenceProject,
				"DescriptionPersistenceDom1");
		long modelFileLength = modelFile.length();
		try {
			doc.replace(0, 0, "Test description");
		} catch (BadLocationException e) {
			fail("Bad Location Exception");
		}
		assertTrue(de.isSaveOnCloseNeeded());
		assertTrue(de.isDirty());

		File diskModelFile = getDomainFile(descriptionPersistenceProject,
				"DescriptionPersistenceDom1");
		assertTrue("Changes were persisted to disk",
				modelFileLength == diskModelFile.length());
		de.doSave(new NullProgressMonitor());
		Ooaofooa.setPersistEnabled(false);
		de.close(false);
		assertTrue("Saved changes to activity were not persisted",
				checkIfPersisted(descriptionPersistenceProject, testclass,
						getClassString(testclass)));
	}
	public void testCancelAddToIdentifier() throws Exception {

		IProject descriptionPersistenceProject = ResourcesPlugin.getWorkspace()
				.getRoot().getProject("DescriptionPersistenceTestProject");
		String modelRootId = Ooaofooa.createModelRootId(
				descriptionPersistenceProject, "DescriptionPersistencePkg1",
				true);

		Ooaofooa packageModelRoot = Ooaofooa.getInstance(modelRootId);
		Package_c dom = Package_c.PackageInstance(packageModelRoot);
		PackageableElement_c[] temp = PackageableElement_c
				.getManyPE_PEsOnR8000(dom);

		Package_c subsystem = Package_c.getOneEP_PKGOnR8001(temp);
		temp = PackageableElement_c.getManyPE_PEsOnR8000(subsystem);

		ModelClass_c testclass = ModelClass_c.getOneO_OBJOnR8001(temp,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						ModelClass_c selected = (ModelClass_c) candidate;
						return selected.getName().equals(
								"TestDescriptionClass 1");
					}

				});
		testclass.Newattribute();
		Attribute_c attr = Attribute_c.getOneO_ATTROnR102(testclass);

		IFile mrFile = packageModelRoot.getFile();
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
		Ooaofooa.setPersistEnabled(true);
		TestUtil.cancelDialog(200);
		atia.run(a);
		long end = mrFile.getModificationStamp();
		Ooaofooa.setPersistEnabled(false);
		assertTrue("Model file modified on cancel", start == end);

		assertTrue(attr.Actionfilter("id", "not all"));
		assertFalse(attr.Actionfilter("id", "some"));

	}

	public void testDeletePersistence() throws Exception {
		Ooaofooa.setPersistEnabled(true);
		final IProject deletePersistenceProject = createXtUMLProject("DeletePersistenceTestProject");
		SystemModel_c sysMod = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(
						deletePersistenceProject.getName());
			}
		});
		PersistableModelComponent packageComponent = createNewPackage(
				"DeletePersistencePkg1", sysMod);

		Ooaofooa packageModelRoot = (Ooaofooa) packageComponent
				.getRootModelElement().getModelRoot();

		Package_c dom = Package_c.getOneEP_PKGOnR1405(sysMod,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Package_c selected = (Package_c) candidate;
						return selected.getName().equals(
								"DeletePersistencePkg1");
					}

				});

		TransactionManager manager = dom.getTransactionManager();
		Transaction t = manager.startTransaction("creating a sub-system",
				Ooaofooa.getDefaultInstance());

		dom.Newpackage();
		PackageableElement_c[] temp = PackageableElement_c
				.getManyPE_PEsOnR8000(dom);
		Package_c subsystem = Package_c.getOneEP_PKGOnR8001(temp);
		subsystem.setName("Delete Persistence Test SS");

		manager.endTransaction(t);

		CanvasTestUtils.openCanvasEditor(subsystem);
		// Create Class in Subsystem
		AbstractTool tool = UITestingUtilities.getTool("Classes", "Class");
		UITestingUtilities.activateTool(tool);
		CanvasTestUtils.createMouseEvent(100, 100, "MouseDown");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseMove");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseUp");
		UITestingUtilities.deactivateTool(tool);
		temp = PackageableElement_c.getManyPE_PEsOnR8000(subsystem);
		ModelClass_c testclass = ModelClass_c.getOneO_OBJOnR8001(temp);
		IFile classFile = testclass.getFile();
		assertNotNull(testclass);
		// Check to make sure the change was first persisted
		String testclassStmt = getClassString(testclass);
		assertTrue("Newly created class was not persisted", checkIfPersisted(
				deletePersistenceProject, testclass, testclassStmt));
		Shape_c shp = CanvasTestUtils.getModelClassShape(packageModelRoot,
				testclass.getName());
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		selection.addToSelection(ge.getRepresents());
		DeleteAction da = new DeleteAction(CorePlugin
				.getImageDescriptor("delete_edit.gif"));
		da.run();

		Ooaofooa.setPersistEnabled(false);

		assertFalse("Model component of deleted class not deleted", classFile
				.exists());
	}

	public void testConnectorPersistence() throws IOException,
			TransactionException {
		Ooaofooa.setPersistEnabled(true);
		final IProject connectorPersistenceProject = createXtUMLProject("ConnectorPersistenceTestProject");
		SystemModel_c sysMod = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(
						connectorPersistenceProject.getName());
			}
		});
		PersistableModelComponent packageComponent = createNewPackage(
				"ConnectorPersistencePkg1", sysMod);

		Ooaofooa packageModelRoot = (Ooaofooa) packageComponent
				.getRootModelElement().getModelRoot();
		Ooaofgraphics graphicsPackageModelRoot = Ooaofgraphics
				.getInstance(packageModelRoot.getId());
		Package_c dom = Package_c.getOneEP_PKGOnR1405(sysMod,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Package_c selected = (Package_c) candidate;
						return selected.getName().equals(
								"ConnectorPersistencePkg1");
					}

				});

		TransactionManager manager = dom.getTransactionManager();
		Transaction t = manager.startTransaction("new subsystem", Ooaofooa
				.getDefaultInstance());

		dom.Newpackage();
		PackageableElement_c[] temp = PackageableElement_c
				.getManyPE_PEsOnR8000(dom);
		Package_c subsystem = Package_c.getOneEP_PKGOnR8001(temp);
		subsystem.setName("Connector Persistence Test SS");
		manager.endTransaction(t);

		Rectangle firstShape = new Rectangle(100, 100, 100, 100);
		Rectangle secondShape = new Rectangle(300, 300, 100, 100);

		CanvasTestUtils.openCanvasEditor(subsystem);
		// Create Classes in Subsystem
		AbstractTool tool = UITestingUtilities.getTool("Classes", "Class");
		UITestingUtilities.activateTool(tool);
		CanvasTestUtils.createMouseEvent(firstShape.x, firstShape.y,
				"MouseDown");
		CanvasTestUtils.createMouseEvent(firstShape.x + firstShape.width,
				firstShape.y + firstShape.height, "MouseMove");
		CanvasTestUtils.createMouseEvent(firstShape.x + firstShape.width,
				firstShape.y + firstShape.height, "MouseUp");
		UITestingUtilities.activateTool(tool);
		CanvasTestUtils.createMouseEvent(secondShape.x, secondShape.y,
				"MouseDown");
		CanvasTestUtils.createMouseEvent(secondShape.x + secondShape.width,
				secondShape.y + secondShape.height, "MouseMove");
		CanvasTestUtils.createMouseEvent(secondShape.x + secondShape.width,
				secondShape.y + secondShape.height, "MouseUp");
		UITestingUtilities.deactivateTool(tool);

		GraphicalEditor editor = (GraphicalEditor) UITestingUtilities
				.getActiveEditor();
		editor.zoomAll();
		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch());

		firstShape.x = firstShape.x + 4000;
		firstShape.y = firstShape.y + 3000;
		secondShape.x = secondShape.x + 4000;
		secondShape.y = secondShape.y + 3000;

		CanvasTestUtils.translate(firstShape, editor.getModel());
		CanvasTestUtils.translate(secondShape, editor.getModel());

		tool = UITestingUtilities.getTool("Classes", "Association");
		UITestingUtilities.activateTool(tool);
		CanvasTestUtils.createMouseEvent(firstShape.getCenter().x, firstShape
				.getCenter().y, "MouseDown");
		CanvasTestUtils.createMouseEvent(secondShape.getCenter().x, secondShape
				.getCenter().y, "MouseMove");
		CanvasTestUtils.createMouseEvent(secondShape.getCenter().x, secondShape
				.getCenter().y, "MouseUp");
		UITestingUtilities.deactivateTool(tool);
		Connector_c con = Connector_c
				.ConnectorInstance(graphicsPackageModelRoot);

		assertNotNull(con);
		String conStmt = getConnectorString(con);
		assertTrue("Newly drawn connector was not persisted", checkIfPersisted(
				connectorPersistenceProject, con, conStmt));
	}

	public void testRenamePersistence() throws IOException,
			TransactionException {
		Ooaofooa.setPersistEnabled(true);
		final IProject renamePersistenceProject = createXtUMLProject("RenamePersistenceTestProject");
		SystemModel_c sysMod = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(
						renamePersistenceProject.getName());
			}
		});
		PersistableModelComponent pkgComponent = createNewPackage(
				"RenamePersistencePkg1", sysMod);

		Ooaofooa packageModelRoot = (Ooaofooa) pkgComponent
				.getRootModelElement().getModelRoot();

		Package_c pkg = Package_c.PackageInstance(packageModelRoot);

		TransactionManager manager = pkg.getTransactionManager();
		Transaction t = manager.startTransaction("new subsystem", Ooaofooa
				.getDefaultInstance());
		pkg.Newpackage();
		PackageableElement_c temp = PackageableElement_c
				.getOnePE_PEOnR8000(pkg);
		Package_c subsystem = Package_c.getOneEP_PKGOnR8001(temp);
		subsystem.setName("Rename Persistence Test SS");
		manager.endTransaction(t);
		CanvasTestUtils.openCanvasEditor(subsystem);
		AbstractTool tool = UITestingUtilities.getTool("Classes", "Class");
		UITestingUtilities.activateTool(tool);
		CanvasTestUtils.createMouseEvent(100, 100, "MouseDown");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseMove");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseUp");
		UITestingUtilities.deactivateTool(tool);
		ModelClass_c testclass = ModelClass_c.ModelClassInstance(subsystem
				.getModelRoot());
		assertNotNull(testclass);
		StructuredSelection sel = new StructuredSelection(testclass);
		Selection.getInstance().setSelection(sel);
		ExplorerUtil.expandAll();
		ExplorerUtil.getTreeViewer().getTree().selectAll();
		TreeItem[] items = ExplorerUtil.getTreeViewer().getTree()
				.getSelection();
		for (int i = 0; i < items.length; i++) {
			if (items[i].getData() == testclass) {
				TreeItem[] x_set = {items[i]};
				ExplorerUtil.getTreeViewer().getTree().setSelection(x_set);
				break;
			}
		}
		RenameAction t2 = (RenameAction) CorePlugin
				.getRenameAction(ExplorerUtil.getTreeViewer());
		t2.run();
		t2.getTextEditor().setText("RenamePersistenceTestClass");
		Event e = new Event();
		e.type = SWT.Traverse;
		e.detail = SWT.TRAVERSE_RETURN;
		e.widget = t2.getTextEditor();
		t2.getTextEditor().notifyListeners(e.type, e);
		Display d = Display.getCurrent();
		while (d.readAndDispatch());
		String testclassStmt = getClassString(testclass);
		assertTrue("Class rename was not persisted to disk", checkIfPersisted(
				renamePersistenceProject, testclass, testclassStmt));
	}

	public void testSelectionToolPersistence() throws IOException,
			TransactionException {
		Ooaofooa.setPersistEnabled(true);
		final IProject selectiontoolPersistenceProject = createXtUMLProject("SelectionToolPersistenceTestProject");
		SystemModel_c sysMod = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(
						selectiontoolPersistenceProject.getName());
			}
		});
		PersistableModelComponent packageComponent = createNewPackage(
				"SelectionToolPersistencePkg1", sysMod);

		Ooaofooa packageModelRoot = (Ooaofooa) packageComponent
				.getRootModelElement().getModelRoot();
		Package_c dom = Package_c.getOneEP_PKGOnR1405(sysMod,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Package_c selected = (Package_c) candidate;
						return selected.getName().equals(
								"SelectionToolPersistencePkg1");
					}

				});
		TransactionManager manager = dom.getTransactionManager();
		Transaction t = manager.startTransaction("new subsystem", Ooaofooa
				.getDefaultInstance());
		dom.Newpackage();
		PackageableElement_c[] temp = PackageableElement_c
				.getManyPE_PEsOnR8000(dom);
		Package_c subsystem = Package_c.getOneEP_PKGOnR8001(temp);
		subsystem.setName("SelectionTool Persistence Test SS");
		manager.endTransaction(t);

		CanvasTestUtils.openCanvasEditor(subsystem);
		// Create Class in Subsystem
		AbstractTool tool = UITestingUtilities.getTool("Classes", "Class");
		UITestingUtilities.activateTool(tool);
		CanvasTestUtils.createMouseEvent(100, 100, "MouseDown");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseMove");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseUp");
		UITestingUtilities.deactivateTool(tool);
		temp = PackageableElement_c.getManyPE_PEsOnR8000(subsystem);
		ModelClass_c testclass = ModelClass_c.getOneO_OBJOnR8001(temp);

		assertNotNull(testclass);
		// Check to make sure the change was first persisted
		String testclassStmt = getClassString(testclass);
		assertTrue("Newly created class was not persisted", checkIfPersisted(
				selectiontoolPersistenceProject, testclass, testclassStmt));
		CanvasTestUtils.createMouseEvent(150, 150, "MouseDown");
		CanvasTestUtils.createMouseEvent(400, 400, "MouseMove");
		CanvasTestUtils.createMouseEvent(400, 400, "MouseUp");
		Shape_c shp = CanvasTestUtils.getModelClassShape(packageModelRoot,
				testclass.getName());
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		String geStmt = getGraphicalElementString(ge);
		assertTrue("Class move was not persisted to disk", checkIfPersisted(
				selectiontoolPersistenceProject, ge, geStmt));
	}

	public void testPropertiesViewPersistence() throws IOException,
			PartInitException, TransactionException {
		Ooaofooa.setPersistEnabled(true);
		final IProject propertiesPersistenceProject = createXtUMLProject("PropertiesPersistenceTestProject");
		SystemModel_c sysMod = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(
						propertiesPersistenceProject.getName());
			}
		});
		createNewPackage(
				"PropertiesPersistencePkg1", sysMod);
		
		Package_c dom = Package_c.getOneEP_PKGOnR1405(sysMod,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Package_c selected = (Package_c) candidate;
						return selected.getName().equals(
								"PropertiesPersistencePkg1");
					}

				});

		TransactionManager manager = dom.getTransactionManager();
		Transaction t = manager.startTransaction("new subsystem", Ooaofooa
				.getDefaultInstance());
		dom.Newpackage();
		PackageableElement_c[] temp = PackageableElement_c
				.getManyPE_PEsOnR8000(dom);
		Package_c subsystem = Package_c.getOneEP_PKGOnR8001(temp);
		subsystem.setName("Properties Persistence Test SS");
		manager.endTransaction(t);

		CanvasTestUtils.openCanvasEditor(subsystem);
		// Create Class in Subsystem
		AbstractTool tool = UITestingUtilities.getTool("Classes", "Class");
		UITestingUtilities.activateTool(tool);
		CanvasTestUtils.createMouseEvent(100, 100, "MouseDown");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseMove");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseUp");
		UITestingUtilities.deactivateTool(tool);
		temp = PackageableElement_c.getManyPE_PEsOnR8000(subsystem);
		ModelClass_c testclass = ModelClass_c.getOneO_OBJOnR8001(temp);
		assertNotNull(testclass);
		ModelPropertySourceProvider mpsp = getPropertiesSheet();
		ClassO_OBJPropertySource mcps = (ClassO_OBJPropertySource) mpsp
				.getPropertySource(testclass);
		mcps.setPropertyValue("Name", "PropertiesTestClass");
		String testclassStmt = getClassString(testclass);
		assertTrue("Class rename was not persisted to disk", checkIfPersisted(
				propertiesPersistenceProject, testclass, testclassStmt));
	}

}