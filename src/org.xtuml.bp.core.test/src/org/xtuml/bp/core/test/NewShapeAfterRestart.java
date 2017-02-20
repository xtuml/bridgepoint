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

import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.test.CanvasTest;
import org.xtuml.bp.ui.canvas.test.CanvasTestResult;
import org.xtuml.bp.ui.canvas.test.CanvasTestUtilities;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.ui.graphics.editor.ModelEditor;

@RunWith(OrderedRunner.class)
public class NewShapeAfterRestart extends CanvasTest {

	String test_id = null;
	boolean generateResults = getGenerateResults();
	String[] expected_string;

	private static boolean getGenerateResults() {
		String env = System.getenv("generateResults");
		if (env == null) {
			return false;
		} else {
			boolean result = Boolean.parseBoolean(env);
			return result;
		}
	}

	public NewShapeAfterRestart() {
		super("org.xtuml.bp.core.test", null);
		expected_string = new String[] { "drawRectangle(...)",
				"drawText(" + String.valueOf('"') + "Unname..." + String.valueOf('"') + ", ...)", "drawline(...)" };
	}

	protected String getResultName() {
		return "TigerNatureTest" + "_" + test_id;
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		BaseTest.dispatchEvents(0);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Create a new shape on an existing sequence diagram, testing
	 * that it is drawn correctly.
	 * @throws PartInitException 
	 */
	@Test
	public void testNewShapeAfterRestartOnSequenceDiagram() throws PartInitException {
		IEditorReference[] editorReferences = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		IEditorInput editorInput = null;
		String id = "";
		for (int i = 0; i < editorReferences.length; i++) {
			if (editorReferences[i].getName().equals("Sequence Diagram")) {
				editorInput = editorReferences[i].getEditorInput();
				id = editorReferences[i].getId();
			}
		}
		assertNotNull(editorInput);

		Object editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(editorInput,
				id);
		GraphicalEditor ce = ((ModelEditor) editor).getGraphicalEditor();

		graphicsModelRoot = Ooaofgraphics.getInstance(ce.getModel().getModelRoot().getId());

		AbstractTool tool = UITestingUtilities.getTool("Instance");
		UITestingUtilities.activateTool(tool);

		CanvasTestUtilities.doMouseMove(100, 100);
		CanvasTestUtilities.doMousePress(100, 100);
		CanvasTestUtilities.doMouseMove(200, 200);
		CanvasTestUtilities.doMouseRelease(200, 200);

		UITestingUtilities.deactivateTool(tool);

		CanvasTestResult result = drawDiagram(ce, true, false, false, new Rectangle(0, 0, 1231, 861));

		for (int i = 0; i < expected_string.length; i++) {
			assertTrue("Diagram draw did not generate correct results:\n" + "expected: " + expected_string[i] + "\n"
					+ "actual: " + result.transcript[i], expected_string[i].equals(result.transcript[i]));
		}
	}

}
