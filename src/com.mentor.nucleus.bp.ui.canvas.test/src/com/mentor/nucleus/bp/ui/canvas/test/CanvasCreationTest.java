//=====================================================================
//
//File:      $RCSfile: CanvasCreationTest.java,v $
//Version:   $Revision: 1.30 $
//Modified:  $Date: 2013/05/10 05:41:51 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.EnumerationDataType_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.ImportedClass_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.io.mdl.ExportModel;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.CanvasTransactionListener;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public class CanvasCreationTest extends CanvasTest {
	public static boolean generateResults = false;
	private static String test_id = "";
	private static boolean initialized;
	
	public CanvasCreationTest(String arg0) {
		super(null, arg0);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Override
	protected void initialSetup() throws Exception {
		setModelName("CanvasCreationModel");

		Display d = Display.getCurrent();
		while ( d.readAndDispatch() ) ;
			
        if ( !initialized )
			{
            initialized = true;
			}
		
        loadProject(getModelName());
        CanvasTransactionListener.disableReconciler();
			}

	protected void tearDown() throws Exception {
		Ooaofooa.setPersistEnabled(false);
		super.tearDown();
		CanvasTransactionListener.enableReconciler();
	}
	protected String getResultName() {
		return "CanvasCreation" + test_id;
	}
	public void setGenerateResults() {
		try {
			generateResults = true;
			this.setUp();
		} catch (Exception e) {
			System.out.println(
				"Exception encountered by test result creator: " + e);
		}

	}
	private void exportModel() throws Exception {
		// create the necessary folders in the
		// expected export path
		String modelsDir = m_workspace_path+"actual_results/" + Ooaofooa.MODELS_DIRNAME + "/";
        BaseTest.ensureFolderExists(modelsDir);
		String modelToExport = "CanvasCreationTestModel." + Ooaofooa.MODELS_EXT;
		String exportPath = modelsDir + modelToExport;
		
		// export the model to the expected location
		ExportModel expMod = new ExportModel(modelRoot, exportPath, true); //$NON-NLS-1$//$NON-NLS-2$
			expMod.run(new NullProgressMonitor());

	}

	public void testCreatePackageWithOneClass() throws Exception {
		IdAssigner.setSeedOfAllInstances(getName().hashCode());
		test_id = "test_1";
		Package_c dom = Package_c.PackageInstance(modelRoot);
        Transaction t = dom.getTransactionManager().startTransaction("Create SS and a Class",Ooaofooa.getDefaultInstance());
		dom.Newpackage();
		Package_c ss = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Unnamed Package");
			}
		});
		ss.setName("Test Subsystem 1");
		ss.Newclass();
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot);
		mc.setDescrip("Test class to hold ISM and CSM");
		mc.setName("Test Class 1");
 		mc.setKey_lett("T_CLS1");
        dom.getTransactionManager().endTransaction(t);
	}
	public void testCreateISM() {
		test_id = "test_5";
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot);
		mc.Create_sm("ISM");
	}
	public void testCreateCSM() throws Exception {
		test_id = "test_6";
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot);
		mc.Create_sm("ASM");
		// export the model to test ooa_type setup
		exportModel();
	}
	public void testCreateModelClassInPackage() {
		test_id = "test_8";
		Package_c ss = Package_c.PackageInstance(modelRoot);
		assertNotNull(ss);
		CanvasTestUtils.openCanvasEditor(ss);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		CanvasTestUtils.matchCanvasSpaceToModelSpace(ce.getModel());
		AbstractTool tool = UITestingUtilities.getTool("Class");
		UITestingUtilities.activateTool(tool);
		
		ModelClass_c mcs[] = ModelClass_c.ModelClassInstances(modelRoot);
		int mc_count = mcs.length;
		
		CanvasTestUtils.createMouseEvent(200, 200, "MouseDown");
		CanvasTestUtils.createMouseEvent(400, 400, "MouseMove");
		CanvasTestUtils.createMouseEvent(400, 400, "MouseUp");
		
		mcs = ModelClass_c.ModelClassInstances(modelRoot);
		int mc_count_after = mcs.length;
		assertTrue("Number of ModelClass instances found: " + Integer.toString(mc_count_after) + "Expected: " + Integer.toString(mc_count + 1), (mc_count + 1) == mc_count_after );
		
        validateOrGenerateResultsGenerics(ce, generateResults);
        UITestingUtilities.deactivateTool(tool);
	}
	public void testCreateImportedModelClassInSubsystem() {
		test_id = "test_9";
		Package_c ss = Package_c.PackageInstance(modelRoot);
		assertNotNull(ss);
		CanvasTestUtils.openCanvasEditor(ss);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		CanvasTestUtils.matchCanvasSpaceToModelSpace(ce.getModel());
		AbstractTool tool = UITestingUtilities.getTool("Imported Class");
		UITestingUtilities.activateTool(tool);
		
		ImportedClass_c ics[] = ImportedClass_c.ImportedClassInstances(modelRoot);
		int ic_count = ics.length;
		
		CanvasTestUtils.createMouseEvent(200, 200, "MouseDown");
		CanvasTestUtils.createMouseEvent(400, 400, "MouseMove");
		CanvasTestUtils.createMouseEvent(400, 400, "MouseUp");
		
		ics = ImportedClass_c.ImportedClassInstances(modelRoot);
		int ic_count_after = ics.length;
		assertTrue("Number of Imported Class instances found: " + Integer.toString(ic_count_after) + "Expected: " + Integer.toString(ic_count + 1), (ic_count + 1) == ic_count_after );
		
        validateOrGenerateResultsGenerics(ce, generateResults); 
        UITestingUtilities.deactivateTool(tool);
	}
	public void testCreatePackageInFunctionPackage() {
		test_id = "test_10";
		Package_c fpk = Package_c.PackageInstance(modelRoot);
		assertNotNull(fpk);
		CanvasTestUtils.openCanvasEditor(fpk);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		CanvasTestUtils.matchCanvasSpaceToModelSpace(ce.getModel());
		AbstractTool tool = UITestingUtilities.getTool("Function Package");
		assertNull(tool );
	}
	public void testCreateUserDatatypeInDataTypePackage() {
		test_id = "test_12";
		Package_c []dpks = Package_c.PackageInstances(modelRoot);
		assertEquals(2, dpks.length);
		// Take second package, first was created by default
		Package_c dpk = dpks[1];
		assertNotNull(dpk);
		CanvasTestUtils.openCanvasEditor(dpk);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		CanvasTestUtils.matchCanvasSpaceToModelSpace(ce.getModel());
		AbstractTool tool = UITestingUtilities.getTool("User Data Type");
		UITestingUtilities.activateTool(tool);
		
		UserDataType_c udts[] = UserDataType_c.UserDataTypeInstances(modelRoot);
		int udt_count = udts.length;
		
		CanvasTestUtils.createMouseEvent(800, 800, "MouseDown");
		CanvasTestUtils.createMouseEvent(1200, 1200, "MouseMove");
		CanvasTestUtils.createMouseEvent(1200, 1200, "MouseUp");
		
		udts = UserDataType_c.UserDataTypeInstances(modelRoot);
		int udt_count_after = udts.length;
		assertTrue("Number of Imported Class instances found: " + Integer.toString(udt_count_after) + "Expected: " + Integer.toString(udt_count + 1), (udt_count + 1) == udt_count_after );
		
        validateOrGenerateResultsGenerics(ce, generateResults); 
        UITestingUtilities.deactivateTool(tool);
	}
	public void testCreateEnumDatatypeInDataTypePackage() {
		test_id = "test_13";
		Package_c []dpks = Package_c.PackageInstances(modelRoot);
		assertEquals(2, dpks.length);
		// Take second package, first was created by default
		Package_c dpk = dpks[1];
		assertNotNull(dpk);
		CanvasTestUtils.openCanvasEditor(dpk);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		CanvasTestUtils.matchCanvasSpaceToModelSpace(ce.getModel());
		AbstractTool tool = UITestingUtilities.getTool("Enumeration Data Type");
		UITestingUtilities.activateTool(tool);
		
		EnumerationDataType_c edts[] = EnumerationDataType_c.EnumerationDataTypeInstances(modelRoot);
		int edt_count = edts.length;
		
		CanvasTestUtils.createMouseEvent(1400, 1400, "MouseDown");
		CanvasTestUtils.createMouseEvent(1800, 1800, "MouseMove");
		CanvasTestUtils.createMouseEvent(1800, 1800, "MouseUp");
		
		edts = EnumerationDataType_c.EnumerationDataTypeInstances(modelRoot);
		int edt_count_after = edts.length;
		assertTrue("Number of Imported Class instances found: " + Integer.toString(edt_count_after) + "Expected: " + Integer.toString(edt_count + 1), (edt_count + 1) == edt_count_after );
		
        validateOrGenerateResultsGenerics(ce, generateResults);
        UITestingUtilities.deactivateTool(tool);
	}
	public void testCreateEEInEEPackage() {
		test_id = "test_14";
		Package_c epk = Package_c.PackageInstance(modelRoot);
		assertNotNull(epk);
		CanvasTestUtils.openCanvasEditor(epk);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		CanvasTestUtils.matchCanvasSpaceToModelSpace(ce.getModel());
		AbstractTool tool = UITestingUtilities.getTool("External Entity");
		UITestingUtilities.activateTool(tool);
		
		ExternalEntity_c ees[] = ExternalEntity_c.ExternalEntityInstances(modelRoot);
		int ee_count = ees.length;
		
		CanvasTestUtils.createMouseEvent(200, 200, "MouseDown");
		CanvasTestUtils.createMouseEvent(400, 400, "MouseMove");
		CanvasTestUtils.createMouseEvent(400, 400, "MouseUp");
		
		ees =  ExternalEntity_c.ExternalEntityInstances(modelRoot);
		int ee_count_after = ees.length;
		assertTrue("Number of Imported Class instances found: " + Integer.toString(ee_count_after) + "Expected: " + Integer.toString(ee_count + 1), (ee_count + 1) == ee_count_after );
		
        validateOrGenerateResultsGenerics(ce, generateResults);
        UITestingUtilities.deactivateTool(tool);
	}

	public void testCreateStateInISM() {
		test_id = "test_16";
		InstanceStateMachine_c ism = InstanceStateMachine_c.InstanceStateMachineInstance(modelRoot);
		assertNotNull(ism);
		CanvasTestUtils.openCanvasEditor(ism);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		AbstractTool tool = UITestingUtilities.getTool("State");
		UITestingUtilities.activateTool(tool);
		
		StateMachineState_c sts[] = StateMachineState_c.getManySM_STATEsOnR501(StateMachine_c.getOneSM_SMOnR517(ism));
		int st_count = sts.length;
		
		CanvasTestUtils.createMouseEvent(200, 200, "MouseDown");
		CanvasTestUtils.createMouseEvent(400, 400, "MouseMove");
		CanvasTestUtils.createMouseEvent(400, 400, "MouseUp");
		
		sts =  StateMachineState_c.getManySM_STATEsOnR501(StateMachine_c.getOneSM_SMOnR517(ism));
		int st_count_after = sts.length;
		assertTrue("Number of Imported Class instances found: " + Integer.toString(st_count_after) + "Expected: " + Integer.toString(st_count + 1), (st_count + 1) == st_count_after );
		
        validateOrGenerateResultsGenerics(ce, generateResults);
        UITestingUtilities.deactivateTool(tool);
	}
	public void testCreateStateInCSM() {
		test_id = "test_17";
		ClassStateMachine_c csm = ClassStateMachine_c.ClassStateMachineInstance(modelRoot);
		assertNotNull(csm);
		CanvasTestUtils.openCanvasEditor(csm);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		CanvasTestUtils.matchCanvasSpaceToModelSpace(ce.getModel());
		AbstractTool tool = UITestingUtilities.getTool("State");
		UITestingUtilities.activateTool(tool);
		
		StateMachineState_c sts[] = StateMachineState_c.getManySM_STATEsOnR501(StateMachine_c.getOneSM_SMOnR517(csm));
		int st_count = sts.length;
		
		CanvasTestUtils.createMouseEvent(200, 200, "MouseDown");
		CanvasTestUtils.createMouseEvent(400, 400, "MouseMove");
		CanvasTestUtils.createMouseEvent(400, 400, "MouseUp");
		
		sts =  StateMachineState_c.getManySM_STATEsOnR501(StateMachine_c.getOneSM_SMOnR517(csm));
		int st_count_after = sts.length;
		assertTrue("Number of Imported Class instances found: " + Integer.toString(st_count_after) + "Expected: " + Integer.toString(st_count + 1), (st_count + 1) == st_count_after );
		
        validateOrGenerateResultsGenerics(ce, generateResults);
        UITestingUtilities.deactivateTool(tool);
	}
	public void testCreateActivityInPackage() {
		test_id = "test_20";
		IdAssigner.setSeedOfAllInstances(test_id.hashCode());
		Package_c dom = Package_c.PackageInstance(modelRoot);
		assertNotNull(dom);
		CanvasUtilities.openCanvasEditor(dom);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		CanvasTestUtilities.matchCanvasSpaceToModelSpace(ce.getModel());
		AbstractTool tool = UITestingUtilities.getTool("Activity Diagram");
		assertNull(tool );
	}

	public void testCreateActivityElements() {
		test_id = "test_23";

		
		SystemModel_c systemModel = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals("com.mentor.nucleus.bp.ui.canvas.test");
			}
		});
		Package_c pkg = CanvasTestUtils.createNewPackageInSystem(systemModel, "ActivityPackage");
		assertNotNull(pkg);
		CanvasUtilities.openCanvasEditor(pkg);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		CanvasTestUtilities.matchCanvasSpaceToModelSpace(ce.getModel());
		
		createConnector(new Point[] {new Point(120, 300), new Point(120, 500)}, "Activity Partition");
		createShape(new Point(320, 300), 200, 200, "Action");
		createShape(new Point(620, 300), 200, 200, "Object Node");
		createShape(new Point(920, 300), 200, 36, "Accept Event Action");
		createConnector(new Point[] {new Point(630, 318), new Point(930, 318)}, "Activity Edge");
		createShape(new Point(20, 600), 200, 200, "Send Signal Action");
		createShape(new Point(320, 600), 200, 200, "Accept Time Event Action");
		createConnector(new Point[] {new Point(620, 700), new Point(820, 700)}, "Fork/Join Node");
		createShape(new Point(920, 600), 200, 200, "Decision/Merge Node");
		createShape(new Point(20, 900), 200, 200, "Initial Node");
		createShape(new Point(320, 900), 200, 200, "Activity Final Node");
		createShape(new Point(620, 900), 200, 200, "Flow Final Node");

        validateOrGenerateResultsGenerics(ce, generateResults);
	}
	public void createShape(Point location, int width, int height, String toolName) {
		AbstractTool tool = UITestingUtilities.getTool(toolName);
		UITestingUtilities.activateTool(tool);
		CanvasTestUtilities.doMouseMove(location.x, location.y);
		CanvasTestUtilities.doMousePress(location.x, location.y);
		CanvasTestUtilities.doMouseMove(location.x + width, location.y + height);
		CanvasTestUtilities.doMouseRelease(location.x + width, location.y + height);
		UITestingUtilities.deactivateTool(tool);
	}
	public void createConnector(Point[] points, String toolName) {
		AbstractTool tool = UITestingUtilities.getTool(toolName);
		UITestingUtilities.activateTool(tool);
		int count = 0;
		for(int i = 0; i < points.length; i++) {
			count = count + 1;
			if(count == 1)
				CanvasTestUtilities.doMouseMove(points[i].x, points[i].y);
			if((count != points.length) && (i + 1 <= points.length)) {
				CanvasTestUtilities.doMousePress(points[i].x, points[i].y);
				CanvasTestUtilities.doMouseMove(points[i + 1].x, points[i + 1].y);
			}
			else
				CanvasTestUtilities.doMouseRelease(points[i].x, points[i].y);
		}
		UITestingUtilities.deactivateTool(tool);
	}
	
	public void createAndvalidateOrGenerateResultsGenerics(Object diagram) {
		CanvasUtilities.openCanvasEditor(diagram);
	}
	
	
    /* (non-Javadoc)
     * @see com.mentor.nucleus.bp.ui.canvas.test.CanvasTest#validateOrGenerateResultsGenerics(GraphicalEditor, boolean)
     */
    public void validateOrGenerateResultsGenerics(GraphicalEditor editor, boolean generate)
    {
        // we want the diagram's values restored each time
        super.validateOrGenerateResultsGenerics(editor, generate, true);
    }
}
