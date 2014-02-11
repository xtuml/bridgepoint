//=====================================================================
//
//File:      $RCSfile: CanvasCutTests.java,v $
//Version:   $Revision: 1.17 $
//Modified:  $Date: 2013/05/10 05:41:51 $
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

import org.eclipse.core.resources.IFileState;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.graphics.actions.CanvasCutAction;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public class CanvasCutTests extends CanvasTest {

	private String testModelName = "CutTestModel";
	private static boolean initialized;
	private String test_id;
	public static boolean generateResults = false;

	public CanvasCutTests(String name) {
		super(null, name);
	}

	public void setUp() throws Exception {
		super.setUp();
		if(!initialized) {
			CorePlugin.disableParseAllOnResourceChange();
			loadProject(testModelName);
			initialized = true;
		}
	}

	public void testCutRemovesElementAndUndoRestores() {
		test_id = "1";
		
		Package_c rootPkg = CanvasTestUtils.createNewPackageInSystem(m_sys, "CutTestPackage");
		Package_c subPkg = CanvasTestUtils.createNewPackageInPackage(rootPkg, "TestSubPkg");        	        

		assertNotNull(subPkg);

		// Create a class in the nested package
		boolean originalPersistValue = subPkg.getModelRoot().persistEnabled();
		Ooaofooa.setPersistEnabled(true);
		Transaction t = Cl_c.Starttransaction(subPkg, "Create Class");
		subPkg.Newclass();
		Cl_c.Endtransaction(subPkg, t);
		Ooaofooa.setPersistEnabled(originalPersistValue);
		
		// Now we can open the parent package and select the subpackage
		CanvasUtilities.openCanvasEditor(rootPkg);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		addElementToSelection(true, subPkg);

		CanvasCutAction canvascutaction = new CanvasCutAction(ce);
		canvascutaction.run();
		waitForTransaction();	
		
		// Verifiy that the package was removed
		validateOrGenerateResults(ce, generateResults);
		
		// Now undo the cut and verify the subpackage is returned
		test_id = "2";
		m_sys.getTransactionManager().getUndoAction().run();
		waitForTransaction();
		validateOrGenerateResults(ce, generateResults);
	}

	public void testCutAvailableAfterReload() {
		// Part 1:  We will verify the cut is still NOT on the element in the
		//          specialized package
		Package_c ss = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((Package_c)candidate).getName().equals("SS With A Class");
			}

		});
		CanvasUtilities.openCanvasEditor(ss);
		ModelClass_c clazz = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(ss));
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		try {
			ss.getPersistableComponent().persist();
			IFileState[] history = ss.getPersistableComponent().getFile().getHistory(new NullProgressMonitor());
			ss.getPersistableComponent().getFile().setContents(history[0], 0, new NullProgressMonitor());
		} catch (CoreException e) {
			fail("Unable to touch test file.");
		}
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		Selection.getInstance().clear(); Selection.getInstance().addToSelection(clazz);
		assertTrue("Cut was not available after a file reload.", UITestingUtilities.checkItemStatusInContextMenu(ce.getCanvas().getMenu(), "Cut", "", false));
		
		
		// Part 2: Now verify that cut is available on the element under the 
		//         package that was create in the previous test.

		Package_c cutTestPkg = null;
		Package_c subPkg = null;
		Package_c pkgs[] = Package_c.getManyEP_PKGsOnR1405(m_sys);
		assertTrue(pkgs.length == 5);
		for (int i = 0; i < pkgs.length; i++) {
			if (pkgs[i].getName().equals("CutTestPackage")) {
				cutTestPkg = pkgs[i];
			}

			if (pkgs[i].getName().equals("TestSubPkg")) {
				subPkg = pkgs[i];
			}
		}
		assertNotNull(cutTestPkg);
		assertNotNull(subPkg);
		CanvasUtilities.openCanvasEditor(cutTestPkg);
		ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		Selection.getInstance().clear(); 
		Selection.getInstance().addToSelection(subPkg);

		assertTrue("Cut was not available after a file reload.", UITestingUtilities.checkItemStatusInContextMenu(ce.getCanvas().getMenu(), "Cut", "", false));
	}

	private void addElementToSelection(boolean makeLoneSelection, NonRootModelElement element) {
		if(makeLoneSelection)
			UITestingUtilities.clearGraphicalSelection();
		UITestingUtilities.addElementToGraphicalSelection(element);
	}

	protected String getResultName() {
		return "CutTests" + "_"  + test_id;
	}
}
