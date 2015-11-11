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
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.ImportedClass_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.ui.graphics.editor.ModelEditor;

public class CanvasCreationTest2 extends CanvasTest {
	public static boolean generateResults = false;
	private static String test_id = "";
	
	public CanvasCreationTest2(String arg0) {
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
	}

	protected void tearDown() throws Exception {
		Ooaofooa.setPersistEnabled(false);
		super.tearDown();
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

	// Because of the way the draw logs are created, these tests need to
	// run in a consistent order
	public void testCanvasCreationTests2() {
		dotestCreateModelClassInPackage();
		dotestCreateImportedModelClassInSubsystem();
		dotestCreateUserDatatypeInDataTypePackage();
		dotestCreateEnumDatatypeInDataTypePackage();
		dotestCreateEEInEEPackage();
	}
	
	public void dotestCreateModelClassInPackage() {
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
	public void dotestCreateImportedModelClassInSubsystem() {
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
	public void dotestCreateUserDatatypeInDataTypePackage() {
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

	public void dotestCreateEnumDatatypeInDataTypePackage() {
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
	
	public void dotestCreateEEInEEPackage() {
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

    /* (non-Javadoc)
     * @see org.xtuml.bp.ui.canvas.test.CanvasTest#validateOrGenerateResultsGenerics(GraphicalEditor, boolean)
     */
    public void validateOrGenerateResultsGenerics(GraphicalEditor editor, boolean generate)
    {
        // we want the diagram's values restored each time
        super.validateOrGenerateResultsGenerics(editor, generate, true);
    }
}
