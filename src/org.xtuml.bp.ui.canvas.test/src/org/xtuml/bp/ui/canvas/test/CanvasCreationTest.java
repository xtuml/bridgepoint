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
package org.xtuml.bp.ui.canvas.test;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.IdAssigner;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.io.mdl.ExportModel;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.canvas.CanvasTransactionListener;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.ui.graphics.editor.ModelEditor;
import org.xtuml.bp.utilities.ui.CanvasUtilities;

@RunWith(OrderedRunner.class)
public class CanvasCreationTest extends CanvasTest {
	public static boolean generateResults = false;
	private static String test_id = "";
	private static boolean initialized;
	
	@Rule public TestName name = new TestName();
	private static boolean isFirstTime = true;
	
	
	public String getName(){
		return name.getMethodName();
	}
	public CanvasCreationTest() {
		super(null, null);
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Override
//	@Before
	public void initialSetup() throws Exception {
		if (!isFirstTime)
			return;
		isFirstTime = false;
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

	@After
	public void tearDown() throws Exception {
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

	@Test
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
	@Test
	public void testCreateISM() {
		test_id = "test_5";
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot);
		mc.Create_sm("ISM");
	}
	@Test
	public void testCreateCSM() throws Exception {
		test_id = "test_6";
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot);
		mc.Create_sm("ASM");
		// export the model to test ooa_type setup
		exportModel();
	}
	@Test
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

	@Test
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

	@Test
	public void testCreateActivityElements() {
		test_id = "test_23";

		
		SystemModel_c systemModel = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals("org.xtuml.bp.ui.canvas.test");
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
     * @see org.xtuml.bp.ui.canvas.test.CanvasTest#validateOrGenerateResultsGenerics(GraphicalEditor, boolean)
     */
    public void validateOrGenerateResultsGenerics(GraphicalEditor editor, boolean generate)
    {
        // we want the diagram's values restored each time
        super.validateOrGenerateResultsGenerics(editor, generate, true);
    }
}
