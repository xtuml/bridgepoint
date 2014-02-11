//=====================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.ui.canvas.test;

import java.io.File;
import java.io.FileFilter;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.ModelStreamProcessor;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CompareTestUtilities;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.graphics.actions.CanvasCopyAction;
import com.mentor.nucleus.bp.ui.graphics.actions.CanvasPasteAction;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;
import com.mentor.nucleus.bp.utilities.ui.ProjectUtilities;

public class ModelRecreationTests extends CanvasTest {

	private static String testProjectName = "ModelRecreationTest";
	private String copiedModelSuffix = "-Copy";
	public static boolean initialized;
	private Selection selection = Selection.getInstance();
	private String test_id;
	private boolean recreate_passed;
	public File testModel;
	public static boolean generateResults = false;
	public int modelNumber = 0;
	private SystemModel_c destSys;

	public static File[] getTestModelNames() {
		String repository_location = System.getenv("XTUML_TEST_MODEL_REPOSITORY");
		if (repository_location == null || repository_location.equals("")) {
			// use the default location
			repository_location = BaseTest.DEFAULT_XTUML_TEST_MODEL_REPOSITORY;
		}
		
		File testFolder = new File(repository_location);
		File[] testModels = testFolder.listFiles(new FileFilter() {
			public boolean accept(File arg0) {
				// This is how we skip some models and keep others.
				if(ModelRecreationTests.isModelToTest(arg0.getName().replaceAll(Ooaofooa.MODELS_EXT, ""))) {
					return true;
				}
				return false;
			}
		});
		return testModels;
	}
	
 	// These are all models in our git models/test folder.  It is easy to
	// add to this list as long as the model is in git/test you add it to this list and 
	// it will be included.
	private static String[] testsToRun = {
		"BP50_evt",
		"bridges",
		"canvastest",
		"cl",
		"CopyPasteTestModel",
		"CreationTransitionTestModel",
		"CutTestModel",
		"dogs",
		"dts0100655323",
		"EE_Test",
		"enum1",
		"enum2",
		"enum3",
		"enum4",
		"ex1",
		"ex2",
		"ex3",
		"FreeFloatingConnectorTestModel",
		"G_ALL_G_EVT_LE_precreated",
		"G_ALL_multiple_exit_return",
		"G_ALL_performance_test2",
		"G_ALL_performance_test3",
		"G_ALL_performance_test4",
		"G_ALL_performance_test5",
		"G_ALL_performance_test6",
		"G_ALL_performance_test7",
		"G_ALL_R_BRG_tim",
		"G_ALL_select_where_enum",
		"G_BRG_G_ALL_interop",
		"G_COP_R_ALL_interop",
		"G_IOP_R_ALL_interop",
		"G_MDA_R_ALL_interop",
		"G_STE_assoc_rel",
		"G_STE_del_inst_mult",
		"G_STE_G_COP_compare_date",
		"i592_ProcessAllActivitiesTest",
		"im1",
		"im2",
		"im3",
		"im4",
		"ims",
		"ims2",
		"imx",
		"init1",
		"init2",
		"interop_otherdom",
		"Loop Disconnect Telephone",
		"memleak",
		"mt1",
		"MultipleSupertypeTest",
		"no_inst",
		"reflexive",
		"Relaxed Same Data",
		"sdt_test",
		"select",
		"self",
		"sm",
		"small",
		"sync",
		"syntax",
		"TestConnectorsAsAnchors",
		"testDescrip1",
		"TimerTest-i1702",
		"trans",
		"udt_assignment",
		"wim2",
		"wim3",
		"wims",
		"wimx"
	};
	
	public ModelRecreationTests(String name) {
		super(testProjectName , name);	
	}

	public void setUp() throws Exception {
		super.setUp();
		if(!initialized) {
			Ooaofooa.setPersistEnabled(true);
			ModelRecreationTests.initialized = true;
		}
	}
	
	public void tearDown() throws Exception {
		super.tearDown();
		if ( getName().contains("testRecreateModel")) {
			if (isModelToTest(testModel.getName())) {
				String msg = getName()  + " #" + test_id + " with " + testModel.getName();
				if (recreate_passed) {
					msg = msg + " (passed)";
				} else {
					msg = "Error! " + msg + " (failed)";
				}
				System.out.println(msg);
			}
		}
	}
	
	private void initializeOriginalModel() throws CoreException {
		loadProject(getDomainName(testModel));
	}
	
	public void testRecreateModel() throws CoreException {
		if (!isModelToTest(testModel.getName())) {
			recreate_passed = true;
			return;
		}
		recreate_passed = false;
		test_id = String.valueOf(modelNumber + 1);
		// load the original model being tested
		initializeOriginalModel();
		final String domainName = getDomainName(testModel);
		// open the original test model's package
		Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys);
		// open the package diagram
		openCanvasEditor(pkg);
		// create the new domain
		createNewModel(domainName);
		Package_c newPkg = Package_c.getOneEP_PKGOnR1401(destSys);
		graphicsModelRoot = Ooaofgraphics.getInstance(newPkg.getModelRoot()
				.getId());
		GraphicalEditor original_ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		// select everything for the copy
		original_ce.getSelectAllAction().run();
		// now copy the selection
		copySelection(original_ce);
		openCanvasEditor(newPkg);
		GraphicalEditor new_ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		ModelStreamProcessor.setParseDisabled(true);
		pasteClipboardElements(new_ce);
		ModelStreamProcessor.setParseDisabled(false);
		CompareTestUtilities.compareModels((IFolder) pkg.getFile().getParent(),
				(IFolder) newPkg.getFile().getParent(), domainName);
		// now verify that all elements were pasted as
		// expected
		validateOrGenerateResults(new_ce, generateResults);
		// close both editors
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.closeAllEditors(false);
		recreate_passed = true;
	}

	private void openCanvasEditor(Object element) {
		CanvasUtilities.openCanvasEditor(element);
		selection.removeFromSelection(element);
	}

	public static String getDomainName(File file) {
		return file.getName().replaceAll("." + Ooaofooa.MODELS_EXT, "");
	}
	
	private void createNewModel(String domainName) throws CoreException {
		ProjectUtilities.createProject(domainName + copiedModelSuffix);
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Create test package",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			destSys = getSystemModel(domainName + copiedModelSuffix);
			destSys.Newpackage();
			Package_c pkg = Package_c.getOneEP_PKGOnR1401(destSys);
			pkg.setName(domainName + copiedModelSuffix);
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction, e);
			}
			CorePlugin.logError("Unable to create test package", e);
		}
		dispatchEvents(0);
	}

	private void pasteClipboardElements(GraphicalEditor ce) {
		CanvasTestUtilities.doMouseMove(10, 10);
		CanvasTestUtilities.doMouseContextPress(10, 10);
		CanvasPasteAction canvaspasteaction = new CanvasPasteAction(ce);
		if(canvaspasteaction.clipboardContainsPastableModelElements()) {
			canvaspasteaction.run();
			waitForTransaction();
			waitForJobs();
			waitForDecorator();
		}
	}

	private void copySelection(GraphicalEditor ce) {
		if(!selection.getStructuredSelection().isEmpty()) {
			CanvasCopyAction canvascopyaction = new CanvasCopyAction(ce);
			canvascopyaction.run();
			waitForTransaction();
		}
	}

	protected String getResultName() {
		return "ModelRecreation" + "_"  + test_id;
	}
	
	public static boolean isModelToTest(String modelName) {
		for(int i = 0; i < testsToRun.length; i++) {
			if(modelName.equalsIgnoreCase(testsToRun[i])) {
				return true;
			} 
		}			
		return false;
	}
	

}
