//=====================================================================
//
//File:      $RCSfile: ModelTransactionTestGenerics.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 04:30:26 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.ClassIdentifier_c;
import com.mentor.nucleus.bp.core.EventIgnored_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.BinaryFormalizeOnR_RELAction;
import com.mentor.nucleus.bp.core.ui.BinaryFormalizeOnR_RELWizardPage1;
import com.mentor.nucleus.bp.core.ui.BinaryFormalizeOnR_RELWizardPage2;
import com.mentor.nucleus.bp.core.ui.DeleteAction;
import com.mentor.nucleus.bp.core.ui.RemoveFromIdentifierOnO_ATTRWizard;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.marker.DelayedMarkerJob;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.TransactionListener;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.CanvasTransactionListener;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.properties.ClassO_OBJPropertySource;
import com.mentor.nucleus.bp.ui.properties.EventIgnoredSM_EIGNPropertySource;
import com.mentor.nucleus.bp.ui.properties.ModelPropertySourceProvider;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditor;

public class ModelTransactionTestGenerics extends BaseTest {

	private static String result_folder;
	private Ooaofooa thisModelRoot;
	private static boolean initialized = false;

	public ModelTransactionTestGenerics(String name) {
		super(null, name); //$NON-NLS-1$
	}

	protected void setUp() throws Exception {
		super.setUp();

		if (!initialized) {
			loadProject("testTransaction");
			initialized = true;
		}
		IdAssigner.setSeedOfAllInstances(getName().hashCode());

		thisModelRoot = modelRoot;
		result_folder = new String(m_workspace_path
				+ "/expected_results/TransactionTest/"); //$NON-NLS-1$

		CanvasTransactionListener.disableReconciler();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		CanvasTransactionListener.enableReconciler();
	}

	public void testEarlyReturnExitTransactionWizard() {
		ModelClass_c mclass = getModelClassByName("EarlyExitA");

		assertNotNull(mclass);
		Attribute_c attr = Attribute_c.getOneO_ATTROnR102(mclass);
		assertNotNull(attr);

		Selection.getInstance().clear();
		StructuredSelection sel = new StructuredSelection(attr);

		RemoveFromIdentifierOnO_ATTRWizard wizard = new RemoveFromIdentifierOnO_ATTRWizard();
		wizard.v_Identifier = ClassIdentifier_c.getOneO_IDOnR104(mclass);
		wizard.init(PlatformUI.getWorkbench(), sel, m_bp_tree);
		// close the warning dialog
		TestUtil.dismissDialog(500);
		wizard.performFinish();

		// verify that the active transaction is null, meaning that
		// the transaction was cancelled
		assertNull("The transaction was not cancelled after an early exit.",
				getSystemModel().getTransactionManager().getActiveTransaction());
	}

	public void testEarlyReturnExitTransaction() {
		DeleteAction action = new DeleteAction(null);

		ModelClass_c mclass = getModelClassByName("EarlyExitA");

		// assumes that the first attribute for the above class
		// is used to formalize an association
		Attribute_c attribute = Attribute_c.getOneO_ATTROnR102(mclass);

		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(attribute);

		// close the warning dialog
		TestUtil.dismissDialog(500);

		action.run();

		assertNull("Transaction was not cancelled after an early return.",
				getSystemModel().getTransactionManager().getActiveTransaction());

	}

	public void testEarlyExceptionExitTransactionInProperties() {
		// pass a null property value so that an exception will occur
		EventIgnoredSM_EIGNPropertySource source = new EventIgnoredSM_EIGNPropertySource(
				new EventIgnored_c(modelRoot));
		source.setPropertyValue("Descrip", null);

		assertTrue("Log file was not present as expected.", logFileExists());

		assertNull(
				"Transaction was not cancelled after an early exit due to an exception.",
				getSystemModel().getTransactionManager().getActiveTransaction());
	}

	// Testing action/transaction that will result in both ooaofooa and ooaofgraphics changes
	public void testShapeCreationTransactionThruCanvas() throws Exception {
		TransactionListener listener = new TransactionListener();
		getSystemModel().getTransactionManager().addTransactionListener(
				listener);

		Package_c[] pkgs = Package_c.PackageInstances(modelRoot);
		Package_c dom = null;
		for (Package_c pkg : pkgs) {
			if (pkg.getName().equalsIgnoreCase("A Subsystem"))
				dom = pkg;
		}
		CanvasTestUtils.openCanvasEditor(dom);

		// Create Subsystem in domain, as this forces 
		// execution of ModelSpecification_c.Elementcreated() (the one native operation that
		// generates model change events)
		AbstractTool tool = UITestingUtilities.getTool("Package"); //$NON-NLS-1$
		Ooaofooa.setPersistEnabled(true);
		UITestingUtilities.activateTool(tool);
		CanvasTestUtils.createMouseEvent(100, 100, "MouseDown"); //$NON-NLS-1$
		CanvasTestUtils.createMouseEvent(200, 200, "MouseMove"); //$NON-NLS-1$
		CanvasTestUtils.createMouseEvent(200, 200, "MouseUp"); //$NON-NLS-1$
		UITestingUtilities.deactivateTool(tool);

		listener.WaitForTransactionUnderReview();
		getSystemModel().getTransactionManager().removeTransactionListener(
				listener);

		BaseTest.compareAndOutputResults(result_folder
				+ "shape_creation_transaction_generics.exp"); //$NON-NLS-1$
	}

	// Testing action/transaction that will result in only ooaofgraphics changes
	public void testShapeMoveTransactionThruCanvas() throws Exception {
		TransactionListener listener = new TransactionListener();
		getSystemModel().getTransactionManager().addTransactionListener(
				listener);
		Ooaofgraphics graphicsDomainModelRoot = Ooaofgraphics
				.getInstance(modelRoot.getId());

		Package_c dom = getPackageByName("testTransaction");

		CanvasTestUtils.openCanvasEditor(dom);

		GraphicalEditor ce = CanvasTestUtils
				.getCanvasEditor("testTransaction: Package Diagram");

		Ooaofooa.setPersistEnabled(true);

		final Package_c dtPackage = Package_c.getOneEP_PKGOnR1405(m_sys,
				new Package_by_name_c("Datatypes"));

		//graphicsDomainModelRoot = (Ooaofgraphics
		//				.getInstance(dtPackage.getModelRoot().getId()));

		GraphicalElement_c meGE = GraphicalElement_c.GraphicalElementInstance(
				graphicsDomainModelRoot, new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return (((GraphicalElement_c) candidate).getOoa_id()
								.equals(dtPackage.getPackage_id())); //$NON-NLS-1$
					}
				});

		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(dtPackage);
		
		Shape_c meShape = Shape_c.getOneGD_SHPOnR2(meGE);

		Point waypoint = CanvasTestUtils.getShapeCenter(meShape);
		Point mouse = CanvasTestUtils.convertToMouseCoor(waypoint, ce
				.getModel());
		
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		
		CanvasTestUtils.createMouseEvent(mouse.x, mouse.y, "MouseMove");

		CanvasTestUtils.createMouseEvent(mouse.x, mouse.y, "MouseDown");

		CanvasTestUtils.createMouseEvent(mouse.x, mouse.y + 200, "MouseMove");
		CanvasTestUtils.createMouseEvent(mouse.x, mouse.y + 200, "MouseUp");
		CanvasTestUtils.createMouseEvent(mouse.x, mouse.y, "MouseMove");
		
		listener.WaitForTransactionUnderReview();
		getSystemModel().getTransactionManager().removeTransactionListener(
				listener);

		BaseTest.compareAndOutputResults(result_folder
				+ "shape_move_transaction_generics.exp"); //$NON-NLS-1$
	}

	public void testPropertyChangeTransaction() throws Exception {
		TransactionListener listener = new TransactionListener();
		getSystemModel().getTransactionManager().addTransactionListener(
				listener);

		TigerNatureTestGenerics tnt = new TigerNatureTestGenerics(
				"Nature test for property transaction"); //$NON-NLS-1$
		ModelClass_c testclass = ModelClass_c.ModelClassInstance(modelRoot,
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return (((ModelClass_c) candidate).getName()
								.equals("Test Class")); //$NON-NLS-1$
					}
				});
		assertNotNull(testclass);
		ModelPropertySourceProvider mpsp = tnt.getPropertiesSheet();
		ClassO_OBJPropertySource mcps = (ClassO_OBJPropertySource) mpsp
				.getPropertySource(testclass);
		String oldName = testclass.getName();
		mcps.setPropertyValue("Name", "PropertiesTestClass"); //$NON-NLS-1$//$NON-NLS-2$

		listener.WaitForTransactionUnderReview();
		getSystemModel().getTransactionManager().removeTransactionListener(
				listener);

		BaseTest.compareAndOutputResults(result_folder
				+ "property_transaction.exp"); //$NON-NLS-1$

		//Putting the old value back
		testclass.Rename(oldName);
	}

	public void testDescriptorEditorChangeTransaction() throws Exception {
		TransactionListener listener = new TransactionListener();
		getSystemModel().getTransactionManager().addTransactionListener(
				listener);

		ModelClass_c testclass = ModelClass_c.ModelClassInstance(modelRoot);
		TigerNatureTestGenerics.openDescriptionEditor(testclass);
		DescriptionEditor de = (DescriptionEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		IDocument doc = de.getDocumentProvider().getDocument(
				de.getEditorInput());
		try {
			doc.replace(0, 0, "Test description"); //$NON-NLS-1$
		} catch (BadLocationException e) {
			fail("Bad Location Exception"); //$NON-NLS-1$
		}
		assertTrue(de.isSaveOnCloseNeeded());
		assertTrue(de.isDirty());
		de.doSave(new NullProgressMonitor());

		listener.WaitForTransactionUnderReview();
		getSystemModel().getTransactionManager().removeTransactionListener(
				listener);

		BaseTest.compareAndOutputResults(result_folder
				+ "editor_transaction.exp"); //$NON-NLS-1$
	}

	public void testActivityEditorChangeTransaction() throws Exception {
		TransactionListener listener = new TransactionListener();
		getSystemModel().getTransactionManager().addTransactionListener(
				listener);

		Function_c function = Function_c.FunctionInstance(modelRoot);
		CanvasTestUtils.openActivityEditor(function);

		ActivityEditor ae = (ActivityEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		IDocument doc = ae.getDocumentProvider().getDocument(
				ae.getEditorInput());
		try {
			doc.replace(0, 0, "select any a from instances of D;"); //$NON-NLS-1$
		} catch (BadLocationException e) {
			fail("Bad Location Exception"); //$NON-NLS-1$
		}
		assertTrue(ae.isSaveOnCloseNeeded());
		assertTrue(ae.isDirty());
		ae.doSave(new NullProgressMonitor());

		listener.WaitForTransactionUnderReview();
		getSystemModel().getTransactionManager().removeTransactionListener(
				listener);

		BaseTest.compareAndOutputResults(result_folder
				+ "activity_editor_transaction.exp"); //$NON-NLS-1$
	}

	public void testRenamingTransactionOnExplorer() throws Exception {
		/**
		 * Load Rename test model
		 */
		Ooaofooa.setPersistEnabled(false);
		TransactionListener listener = new TransactionListener();
		RenameTestGenerics renTest = new RenameTestGenerics();

		//super.defaultProjectName = "testTransaction";
		getSystemModel("Default Project").getTransactionManager()
				.addTransactionListener(listener);

		//	renTest.setUp();

		SystemModel_c arr[] = SystemModel_c.SystemModelInstances(Ooaofooa
				.getDefaultInstance());

		renTest.testRenameS_SYS();

		listener.WaitForTransactionUnderReview();
		getSystemModel("Default Project").getTransactionManager()
				.removeTransactionListener(listener);

		BaseTest.compareAndOutputResults(result_folder
				+ "rename_transaction.exp"); //$NON-NLS-1$
	}

	public void testDeleteClassTransactionFromExplorer() throws Exception {
		// disable the problem marker thread
		DelayedMarkerJob.disableProblemMarkerJob();
		TransactionListener listener = new TransactionListener();
		getSystemModel().getTransactionManager().addTransactionListener(
				listener);

		/**
		 * Get class by the name of 'Class E' 
		 */
		ModelClass_c obj = ModelClass_c.ModelClassInstance(modelRoot,
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((ModelClass_c) candidate).getName().equals(
								"Class E"); //$NON-NLS-1$
					}
				});

		assertNotNull(obj);

		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		
		StructuredSelection sel = new StructuredSelection(obj);
		Selection.getInstance().setSelection(sel);
		DeleteAction t2 = new DeleteAction(null);
		t2.run();

		listener.WaitForTransactionUnderReview();
		getSystemModel().getTransactionManager().removeTransactionListener(
				listener);

		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		
		BaseTest.compareAndOutputResults(result_folder
				+ "delete_transaction_generics.exp"); //$NON-NLS-1$
		DelayedMarkerJob.enableProblemMarkerJob();
	}

	public void testFormalizeSimpleAssociationTransaction() throws Exception {
		modelRoot = thisModelRoot;
		graphicsModelRoot = Ooaofgraphics.getInstance(modelRoot.getId());
		TransactionListener listener = new TransactionListener();
		TransactionManager manager = getSystemModel().getTransactionManager();
		manager.addTransactionListener(listener);

		Package_c uut = Package_c.PackageInstance(modelRoot,
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						Package_c selected = (Package_c) candidate;
						return (selected.getName().equals("test"));
					}
				});
		CanvasTestUtils.openCanvasEditor(uut);
		final Association_c assoc = Association_c.AssociationInstance(
				modelRoot, new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						Association_c selected = (Association_c) candidate;
						return (selected.getNumb() == 109);
					}
				});
		assertNotNull(assoc);
		GraphicalElement_c ge2 = GraphicalElement_c.GraphicalElementInstance(
				graphicsModelRoot, new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						GraphicalElement_c selected = (GraphicalElement_c) candidate;
						return (selected.getRepresents() == assoc);
					}
				});
		assertNotNull(ge2);
		Cl_c.Clearselection();
		Selection.getInstance().addToSelection(ge2.getRepresents());
		BinaryFormalizeOnR_RELAction fa = (BinaryFormalizeOnR_RELAction) new BinaryFormalizeOnR_RELAction();
		Action a = new Action() {
		};
		fa.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) Selection
				.getInstance().getSelection();
		WizardDialog wd = fa.R_REL_BinaryFormalize(structuredSelection);
		BinaryFormalizeOnR_RELWizardPage1 page1 = (BinaryFormalizeOnR_RELWizardPage1) wd
				.getCurrentPage();
		BinaryFormalizeOnR_RELWizardPage2 page2 = (BinaryFormalizeOnR_RELWizardPage2) page1
				.getNextPage();
		String strings[] = page1.Non_formalizerCombo.getItems();
		for (int i = 0; i < strings.length; ++i) {
			if (strings[i].equals("Class C")) {
				page1.Non_formalizerCombo.select(i);
			}
		}
		page1.updateSelectedNon_formalizer();
		page2.onPageEntry();
		page2.IdentifierCombo.select(0);
		page2.updateSelectedIdentifier();
		page1.onPageEntry();
		IWizard w = page1.getWizard();
		w.performFinish();
		wd.close();
		listener.WaitForTransactionUnderReview();
		manager.removeTransactionListener(listener);

		BaseTest.compareAndOutputResults(result_folder
				+ "formalize_transaction.exp"); //$NON-NLS-1$

	}

	public void testMenuItemUserAction() throws Exception {
		AttributeMenuItemTestGenerics attrMenuItemTest = new AttributeMenuItemTestGenerics(
				"Menu Item user action"); //$NON-NLS-1$
		attrMenuItemTest.setReloadModel(true);
		attrMenuItemTest.setUp();
		TransactionListener listener = new TransactionListener();
		TransactionManager manager = getSystemModel("AttributeMenuItemTests")
				.getTransactionManager();
		manager.addTransactionListener(listener);

		// we skip graphical validation here, as it 
		// has 1 pixel differences due to GEF and
		// revalidation
		// The graphical validation is not necessary
		// as it has been tested with the original
		// run.
		attrMenuItemTest.fSkipValidate = true;
		attrMenuItemTest.testMoveWithTwoAttributes();
		attrMenuItemTest.testMoveWithThreeAttributes();
		attrMenuItemTest.fSkipValidate = false;

		listener.WaitForTransactionUnderReview();
		manager.removeTransactionListener(listener);

		BaseTest.compareAndOutputResults(result_folder
				+ "menu_item_useraction_transaction.exp"); //$NON-NLS-1$

		attrMenuItemTest.tearDown();
	}

	public void setGenerateResults() throws Exception {
		BaseTest.doCreateResults = true;

		testShapeCreationTransactionThruCanvas();
		testShapeMoveTransactionThruCanvas();

		setUp();

		testShapeMoveTransactionThruCanvas();

		testShapeCreationTransactionThruCanvas();

		testPropertyChangeTransaction();

		testDescriptorEditorChangeTransaction();
		testActivityEditorChangeTransaction();

		testRenamingTransactionOnExplorer();
		testDeleteClassTransactionFromExplorer();

		testFormalizeSimpleAssociationTransaction();
		testMenuItemUserAction();

		BaseTest.doCreateResults = false;
	}

	private boolean logFileExists() {
		IPath logfile_path = new Path(m_logfile_path);
		File logfile = logfile_path.toFile();
		if (logfile.exists()) {
			logfile.delete();
			return true;
		} else {
			return false;
		}
	}

	private ModelClass_c getModelClassByName(final String name) {
		ModelClass_c mclass = ModelClass_c.ModelClassInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						ModelClass_c mclass = (ModelClass_c) candidate;
						if (mclass.getName().equals(name)) {
							return true;
						}
						return false;
					}

				});
		assertNotNull(mclass);

		return mclass;
	}

	private Package_c getPackageByName(final String name) {
		Package_c pkg = Package_c.PackageInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Package_c mclass = (Package_c) candidate;
						if (mclass.getName().equals(name)) {
							return true;
						}
						return false;
					}

				});
		assertNotNull(pkg);

		return pkg;
	}

}
