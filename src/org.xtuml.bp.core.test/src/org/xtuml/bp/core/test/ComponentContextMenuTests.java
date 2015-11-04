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
package org.xtuml.bp.core.test;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.widgets.Menu;
import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ImportedProvision_c;
import org.xtuml.bp.core.ImportedRequirement_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.Requirement_c;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;

public class ComponentContextMenuTests extends BaseTest {
	/**
	 * The editor upon which these tests operate.
	 */
	private static GraphicalEditor editor = null;

	/**
	 * A boolean to determine whether the test shall be performed
	 * in a read only environment.
	 */
	public boolean m_readonly;

	static protected boolean first_time = true;

	/**
	 * Constructor.
	 */

	public ComponentContextMenuTests(String name) {
		super(null, name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();

		if (first_time) {
			Ooaofooa.setPersistEnabled(true);
			CorePlugin.disableParseAllOnResourceChange();
			loadProject("ComponentContextMenuTests");
			modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(
					m_sys.getName(), "Component Context Menu Test", true));
			// open a class diagram editor on any subsystem
			ModelClass_c clazz = ModelClass_c
					.getOneO_OBJOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000(Package_c
									.getManyEP_PKGsOnR1405(m_sys)));
			Package_c uut = Package_c.getOneEP_PKGOnR8000(PackageableElement_c
					.getOnePE_PEOnR8001(clazz));
			CanvasTestUtils.openDiagramEditor(uut);
			editor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
			
			first_time = false;
		}
	}
	  public void testContextMenuDisconnectActionOnCL_IR() {
			ImportedRequirement_c obj = ImportedRequirement_c.ImportedRequirementInstance(modelRoot);

				IFile file = obj.getFile();
				TestUtil.changeFileReadonlyStatus(m_readonly, file);

	    	UITestingUtilities.clearGraphicalSelection();
	    	editor = UITestingUtilities.addElementToGraphicalSelection(obj);
	      
	    	Menu menu = null;
	    	// get the menu from the SWT Canvas
	    	menu = editor.getCanvas().getMenu();

	    	// check the status of the action
	    	assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "Disconnect", "", m_readonly));
	    }
	    public void testContextMenuDisconnectActionOnCL_IP() {
			ImportedProvision_c obj = ImportedProvision_c.ImportedProvisionInstance(modelRoot);

				IFile file = obj.getFile();
				TestUtil.changeFileReadonlyStatus(m_readonly, file);

	    	UITestingUtilities.clearGraphicalSelection();
	    	editor = UITestingUtilities.addElementToGraphicalSelection(obj);
	    	
	    	Menu menu = null;
	    	// get the menu from the SWT Canvas
	    	menu = editor.getCanvas().getMenu();

	    	// check the status of the action
	    	assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "Disconnect", "", m_readonly));
	    }
	    public void testContextMenuDisconnectActionOnC_P() {
			Provision_c obj = Provision_c.ProvisionInstance(modelRoot);

				IFile file = obj.getFile();
				TestUtil.changeFileReadonlyStatus(m_readonly, file);

	    	UITestingUtilities.clearGraphicalSelection();
	    	editor = UITestingUtilities.addElementToGraphicalSelection(obj);
	    	
	    	Menu menu = null;
	    	// get the menu from the SWT Canvas
	    	menu = editor.getCanvas().getMenu();

	    	// check the status of the action
	    	assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "Disconnect", "", m_readonly));
	    }
	    public void testContextMenuDisconnectActionOnC_R() {
			Requirement_c obj = Requirement_c.RequirementInstance(modelRoot);

				IFile file = obj.getFile();
				TestUtil.changeFileReadonlyStatus(m_readonly, file);

	    	UITestingUtilities.clearGraphicalSelection();
	    	editor = UITestingUtilities.addElementToGraphicalSelection(obj);
	    	
	    	Menu menu = null;
	    	// get the menu from the SWT Canvas
	    	menu = editor.getCanvas().getMenu();

	    	// check the status of the action
	    	assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "Disconnect", "", m_readonly));
	    }
		public void testContextMenuDeleteActionOnCL_IC() {
			ComponentReference_c obj = ComponentReference_c
					.ComponentReferenceInstance(modelRoot);

			IFile file = obj.getFile();
			TestUtil.changeFileReadonlyStatus(m_readonly, file);

			UITestingUtilities.clearGraphicalSelection();
			editor = UITestingUtilities.addElementToGraphicalSelection(obj);

			// get the menu from the SWT Canvas
			Menu menu = editor.getCanvas().getMenu();

			// check the status of the action
			assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu,
					"Delete", "", m_readonly));
		}
	public void testContextMenuUnassignActionOnCL_IC() {
		ComponentReference_c obj = ComponentReference_c
				.ComponentReferenceInstance(modelRoot);

		IFile file = obj.getFile();
		TestUtil.changeFileReadonlyStatus(m_readonly, file);

		UITestingUtilities.clearGraphicalSelection();
		editor = UITestingUtilities.addElementToGraphicalSelection(obj);

		// get the menu from the SWT Canvas
		Menu menu = editor.getCanvas().getMenu();

		// check the status of the action
		assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu,
				"Unassign", "", m_readonly));
	}
	public void testContextMenuDeleteActionOnIP_IP() {
		Package_c obj = Package_c
				.PackageInstance(modelRoot);
		IFile file = obj.getFile();
		TestUtil.changeFileReadonlyStatus(m_readonly, file);

		editor = UITestingUtilities.addElementToGraphicalSelection(obj);

		// get the menu from the SWT Canvas
		Menu menu = editor.getCanvas().getMenu();

		// check the status of the action
		assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu,
				"Delete", "", m_readonly));
	}
	public void testContextMenuRenameActionOnEP_PKG() {
		Package_c obj = Package_c
				.PackageInstance(modelRoot);

		IFile file = obj.getFile();
		TestUtil.changeFileReadonlyStatus(m_readonly, file);

		editor = UITestingUtilities.addElementToGraphicalSelection(obj);

		// get the menu from the SWT Canvas
		Menu menu = editor.getCanvas().getMenu();

		// check the status of the action
		assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu,
				"Rename", "", m_readonly));
	}
   
}
