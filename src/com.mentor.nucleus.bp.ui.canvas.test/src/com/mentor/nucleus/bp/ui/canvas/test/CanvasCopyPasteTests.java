//=====================================================================
//
//File:      $RCSfile: CanvasCopyPasteTests.java,v $
//Version:   $Revision: 1.21 $
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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionException;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.actions.CanvasCopyAction;
import com.mentor.nucleus.bp.ui.graphics.actions.CanvasPasteAction;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public class CanvasCopyPasteTests extends CanvasTest {
	
	private String testModelName = "CopyPasteTestModel";
	private static boolean initialized;
	private String test_id;
	public static boolean generateResults = false;

	public CanvasCopyPasteTests(String name) {
		super(null, name);	
	}

	public void setUp() throws Exception {
		super.setUp();
		if(!initialized) {
			Ooaofooa.setPersistEnabled(true);
			loadProject(testModelName);
			initialized = true;
		}
	}
	
	public void testCopyPackageToSystem() {
		test_id = "1";
		Package_c domain = Package_c.getOneEP_PKGOnR1401(m_sys);
		assertNotNull(domain);
		CanvasUtilities.openCanvasEditor(domain);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Package_c dtPackage = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
		
			public boolean evaluate(Object candidate) {
				return ((Package_c)candidate).getName().equals("DT Package");
			}
		
		});
		assertNotNull(dtPackage);
		addElementToSelection(true, dtPackage);
		copySelection(ce);
		addElementToSelection(true, m_sys);
		CanvasUtilities.openCanvasEditor(m_sys);
		ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		UITestingUtilities.pasteClipboardContents(UITestingUtilities.getClearPoint(ce), ce);
		validateOrGenerateResults(ce, generateResults);
		Package_c newDtPackage = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
		
			public boolean evaluate(Object candidate) {
				return ((Package_c)candidate).getName().equals("DT Package");
			}
		
		});
		// test that datatypes of package are copied correctly
		assertNotNull("Package was not created along with paste.", newDtPackage);
		addElementToSelection(true, newDtPackage);
		CanvasUtilities.openCanvasEditor(newDtPackage);
		ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		test_id = "2";
		validateOrGenerateResults(ce, generateResults);
	}
	
	public void testCopySSWithNonSimpleAssociations() {
// TODO: dts0100656082
		test_id = "3";
	    Package_c subsystem = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {
		
			public boolean evaluate(Object candidate) {
				return ((Package_c)candidate).getName().equals("Test SS");
			}
		
		});
		assertNotNull(subsystem);
		CanvasUtilities.openCanvasEditor(subsystem);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		ce.getSelectAllAction().run();
		copySelection(ce);
		CanvasTestUtilities.doMouseMove(600, 100);
		CanvasTestUtilities.doMousePress(600, 100);
		CanvasTestUtilities.doMouseRelease(600, 100);
		pasteClipboardElements(ce);
		validateOrGenerateResults(ce,generateResults);
	}
	
	public void testCopyClassesWithoutSelectingAssociationBetween() {
// TODO: dts0100656082
		test_id = "4";
	    Package_c subsystem = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {
			
			public boolean evaluate(Object candidate) {
				return ((Package_c)candidate).getName().equals("Test SS");
			}
		
		});
		assertNotNull(subsystem);
		CanvasUtilities.openCanvasEditor(subsystem);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		ModelClass_c class1 = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(subsystem), new ClassQueryInterface_c() {
		
			public boolean evaluate(Object candidate) {
				return ((ModelClass_c)candidate).getName().equals("Supertype");
			}
		
		});
		ModelClass_c class2 = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(subsystem), new ClassQueryInterface_c() {
			
			public boolean evaluate(Object candidate) {
				return ((ModelClass_c)candidate).getName().equals("Subtype");
			}
		
		});
		addElementToSelection(true, class1);
		addElementToSelection(false, class2);
		copySelection(ce);
		CanvasTestUtilities.doMouseMove(600, 600);
		CanvasTestUtilities.doMousePress(600, 600);
		CanvasTestUtilities.doMouseRelease(600, 600);
		pasteClipboardElements(ce);
		validateOrGenerateResults(ce,generateResults);		
	}
	
	public void testUndoRedoRestoresPastedElements() throws CoreException {
// TODO: dts0100656082
//		ensureAvailableAndLoaded("Models", "microwave", false, true);
//		Domain_c domain = Domain_c.DomainInstance(modelRoot, new ClassQueryInterface_c() {
//		
//			public boolean evaluate(Object candidate) {
//				return ((Domain_c)candidate).getName().equals("microwave");
//			}
//		
//		});
//		assertNotNull(domain);
//		CanvasTestUtilities.openCanvasEditor(domain);
//		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
//				.getGraphicalEditor();
//		ce.getSelectAllAction().run();
//		DataTypePackage_c dtPackage = DataTypePackage_c.DataTypePackageInstance(modelRoot, new ClassQueryInterface_c() {
//		
//			public boolean evaluate(Object candidate) {
//				return ((DataTypePackage_c)candidate).getName().equals(Ooaofooa.Getcoredatatypespackagename(modelRoot));
//			}
//		
//		});
//		assertNotNull(dtPackage);
//		selection.removeFromSelection(dtPackage);
//		copySelection(ce);
//		CanvasTestUtilities.doMouseMove(100, 100);
//		CanvasTestUtilities.doMousePress(100, 100);
//		CanvasTestUtilities.doMouseRelease(100, 100);
//		pasteClipboardElements(ce);
//		m_sys.getTransactionManager().getUndoAction().run();
//		m_sys.getTransactionManager().getRedoAction().run();
//		test_id = "5";
//      if(BaseTest.testGlobals) {
//          test_id = "5Globals";
//	    }
//		validateOrGenerateResults(ce, generateResults);
	}
	
	public void testPasteOutsideOfGraphicsAllowsUpdateOfGraphicalElementModelRoots() {
		// create a new package, with two classes
		// do this inside of a transaction
		Package_c testPackage = null;
		try {
			Transaction transaction = TransactionManager.getSingleton()
					.startTransaction("Create test elements.",
							Ooaofooa.getDefaultInstance());
			m_sys.Newpackage();
			Package_c[] packages = Package_c.getManyEP_PKGsOnR1401(m_sys);
			testPackage = packages[packages.length - 1];
			testPackage.setName("PasteOutsideGraphicsTest");
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (TransactionException e) {
			fail(e.getLocalizedMessage());
		}
		assertNotNull(testPackage);
		// create two classes graphically
		CanvasUtilities.openCanvasEditor(testPackage);
		AbstractTool tool = UITestingUtilities.getTool("Classes", "Class");
		UITestingUtilities.activateTool(tool);
		CanvasTestUtilities.doMouseMove(100, 100);
		CanvasTestUtilities.doMousePress(100, 100);
		CanvasTestUtilities.doMouseMove(200, 200);
		CanvasTestUtilities.doMouseRelease(200, 200);
		UITestingUtilities.deactivateTool(tool);
		UITestingUtilities.activateTool(tool);
		CanvasTestUtilities.doMouseMove(300, 100);
		CanvasTestUtilities.doMousePress(300, 100);
		CanvasTestUtilities.doMouseMove(400, 200);
		CanvasTestUtilities.doMouseRelease(400, 200);
		UITestingUtilities.deactivateTool(tool);
		// now copy the package in ME and paste the package
		// into itself in ME
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(testPackage);
		UITestingUtilities.copyElementInExplorer(getExplorerView());
		UITestingUtilities.pasteClipboardContentsInExplorer(testPackage);
		// now open the pasted diagram and draw an association from one class
		// to another
		Package_c pastedPackage = Package_c
				.getOneEP_PKGOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPackage));
		CanvasUtilities.openCanvasEditor(pastedPackage);
		GraphicalEditor editor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		ZoomManager zoomManager = (ZoomManager) editor.getAdapter(ZoomManager.class);
		zoomManager.setZoom(1);
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		// get the two shapes on the diagram
		Model_c model = editor.getModel();
		Shape_c[] shapes = Shape_c.getManyGD_SHPsOnR2(GraphicalElement_c.getManyGD_GEsOnR1(model));
		tool = UITestingUtilities.getTool("Classes", "Association");
		UITestingUtilities.activateTool(tool);
		Point shapeCenter = CanvasUtilities.getShapeCenter(shapes[0]);
		shapeCenter = CanvasTestUtilities.convertToMouseCoor(shapeCenter, model);
		CanvasTestUtilities.doMouseMove(shapeCenter.x, shapeCenter.y);
		CanvasTestUtilities.doMousePress(shapeCenter.x, shapeCenter.y);
		shapeCenter = CanvasUtilities.getShapeCenter(shapes[1]);
		shapeCenter = CanvasTestUtilities.convertToMouseCoor(shapeCenter, model);
		CanvasTestUtilities.doMouseMove(shapeCenter.x, shapeCenter.y);
		CanvasTestUtilities.doMouseRelease(shapeCenter.x, shapeCenter.y);
		UITestingUtilities.deactivateTool(tool);
		// assert that an assocation exists
		Association_c association = Association_c
				.getOneR_RELOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(pastedPackage));
		assertNotNull(
				"Association could not be created, therefore the paste listener did appropriately move graphical elements to the destination.",
				association);
	}
	
	private void pasteClipboardElements(GraphicalEditor ce) {
		CanvasPasteAction canvaspasteaction = new CanvasPasteAction(ce);
		canvaspasteaction.run();
		waitForTransaction();
		waitForDecorator();
	}

	private void copySelection(GraphicalEditor ce) {
		CanvasCopyAction canvascopyaction = new CanvasCopyAction(ce);
		canvascopyaction.run();
		waitForTransaction();
	}
	
	private void addElementToSelection(boolean makeLoneSelection, NonRootModelElement element) {
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		if(makeLoneSelection)
			UITestingUtilities.clearGraphicalSelection();
		UITestingUtilities.addElementToGraphicalSelection(element);
	}

	protected String getResultName() {
		return "CopyPasteTests" + "_"  + test_id;
	}
}
