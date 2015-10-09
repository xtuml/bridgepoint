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
import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.canvas.CanvasTransactionListener;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.ui.graphics.editor.ModelEditor;

public class StateCreationTests extends CanvasTest {
	public static boolean generateResults = false;
	private static String test_id = "";
	private static boolean initialized;
	
	public StateCreationTests(String arg0) {
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

    /* (non-Javadoc)
     * @see org.xtuml.bp.ui.canvas.test.CanvasTest#validateOrGenerateResultsGenerics(GraphicalEditor, boolean)
     */
    public void validateOrGenerateResultsGenerics(GraphicalEditor editor, boolean generate)
    {
        // we want the diagram's values restored each time
        super.validateOrGenerateResultsGenerics(editor, generate, true);
    }
}
