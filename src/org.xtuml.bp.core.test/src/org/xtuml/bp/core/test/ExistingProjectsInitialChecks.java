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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.ExecutableProperty_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.InterfaceReference_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Port_c;
import org.xtuml.bp.core.PropertyParameter_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.ExplorerUtil;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.canvas.Cl_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.test.CanvasTest;
import org.xtuml.bp.ui.canvas.test.CanvasTestResult;
import org.xtuml.bp.ui.canvas.test.CanvasTestUtilities;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditorInput;
import org.xtuml.bp.ui.graphics.editor.ModelEditor;
import org.xtuml.bp.ui.properties.ChooserPropertyDescriptor;
import org.xtuml.bp.ui.properties.OperationsC_IOPropertySource;
import org.xtuml.bp.ui.properties.ParametersC_PPPropertySource;
import org.xtuml.bp.ui.text.activity.ActivityEditorInputFactory;
import org.xtuml.bp.ui.text.description.DescriptionEditorInputFactory;
import org.xtuml.bp.utilities.ui.CanvasUtilities;

public class ExistingProjectsInitialChecks extends CanvasTest {

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

	public ExistingProjectsInitialChecks(String name) {
		super("org.xtuml.bp.core.test", name);
		if (Platform.getOS().contains("win")) {
			expected_string = new String[]{"drawRectangle(16416, 12384, 384, 432)",
					"drawText(" + String.valueOf('"') + "Unnamed ..." + String.valueOf('"') + ", 16432, 12397, true)",
					"drawline(16416, 12492, 16800, 12492)"};
		} else {
			expected_string = new String[]{"drawRectangle(...)",
					"drawText(" + String.valueOf('"') + "Unname..." + String.valueOf('"') + ", ...)", "drawline(...)"};
		}
	}

	protected String getResultName() {
		return "TigerNatureTest" + "_" + test_id;
	}

	protected void setUp() throws Exception {
		super.setUp();
		Display d = Display.getCurrent();
		while (d.readAndDispatch());
	}

	protected void tearDown() throws Exception {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("Test Project 3");
		if (project != null) {
			project.delete(true, true, new NullProgressMonitor());
		}
		super.tearDown();
	}

	private boolean checkForTreeItem(String itemName) {
		ExplorerUtil.expandAll();
		return checkForTreeItemInTree(itemName);
	}

	private boolean checkForTreeItemInTree(String itemName) {
		ExplorerUtil.getTreeViewer().getTree().selectAll();
		TreeItem x[] = ExplorerUtil.getTreeViewer().getTree().getSelection();
		assertNotNull("Tree is empty", x);
		for (int i = 0; i < x.length; ++i) {

			String item = x[i].getText();

			if (item.equals(itemName)) {
				Display d = Display.getCurrent();
				while (d.readAndDispatch());
				return true;
			}
		}
		return false;
	}

	private IEditorPart checkForOpenEditors(String editorName) {
		IEditorReference x[] = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		for (int i = 0; i < x.length; ++i) {

			String editor = x[i].getName();

			if (editor.equals(editorName)) {
				return x[i].getEditor(true);
			}
		}
		return null;
	}

	public void testXTUMLProjectsExist() {
		assertTrue("Did not find existing project, Test Project 1, in the explorer view",
				checkForTreeItem("Test Project 1"));
		assertTrue("Did not find existing domain, testDomain1, in the  explorer view", checkForTreeItem("testDomain1"));
		assertTrue("Did not find existing project, Test Project 2, in the explorer view",
				checkForTreeItem("Test Project 2"));
		assertTrue("Did not find existing domain, testDomain2, in the  explorer view", checkForTreeItem("testDomain2"));
	}

	// Test that the tree is opened to the selected class
	public void testLinkWithEditor() {
		IEditorPart ss = checkForOpenEditors("TestSS");
		assertNotNull(ss);

		// bring the canvas editor to the front
		ss.getEditorSite().getPage().activate(ss);
		Package_c ss_obj = (Package_c) ((GraphicalEditorInput) ss.getEditorInput()).getInput().getRepresents();
		modelRoot = (Ooaofooa) ss_obj.getModelRoot();

		IWorkbenchPage page = TestUtil.showBridgePointPerspective();
		ExplorerUtil.showModelExplorer();
		ExplorerUtil.setLinkWithEditor(true);

		//Getting Class TestClass1
		ModelClass_c obj = ModelClass_c.ModelClassInstance(modelRoot, new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				ModelClass_c uut = (ModelClass_c) candidate;
				return uut.getName().equals("TestClass1");
			}
		});
		assertNotNull(obj);

		// link with editor will only get processed when the
		// explorer view is not the active part, so activate some
		// other view
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.showView("org.eclipse.ui.views.PropertySheet");
		} catch (PartInitException e) {
			fail(e.getMessage());
		}

		// select the class on the diagram
		UITestingUtilities.addElementToGraphicalSelection(obj);

		assertTrue("Did not find existing class, TestClass1, in the explorer view",
				checkForTreeItemInTree("TestClass1"));

	}

}