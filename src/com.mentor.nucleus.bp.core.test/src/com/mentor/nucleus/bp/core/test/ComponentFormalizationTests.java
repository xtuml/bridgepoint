//=====================================================================
//
//File:      $RCSfile: ComponentFormalizationTests.java,v $
//Version:   $Revision: 1.13 $
//Modified:  $Date: 2013/01/10 22:49:05 $
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
package com.mentor.nucleus.bp.core.test;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.ExternalEntityPackage_c;
import com.mentor.nucleus.bp.core.GlobalElementInSystem_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SystemDatatypeInPackage_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionException;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;
import com.mentor.nucleus.bp.ui.properties.OperationO_TFRPropertySource;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;
public class ComponentFormalizationTests extends BaseTest {

	protected static boolean initialized = false;
	private static Domain_c testDomain;

	public ComponentFormalizationTests() {
	  	super("Models", null);
	}
			
	protected void setUp() throws Exception {
		super.setUp();
		if (!initialized) {
			m_sys = getSystemModel("Models");
			ensureAvailableAndLoaded("Models",
					"Imported Component IF Scope Testing", false, true,
					"Component Package");
			Ooaofooa componentRoot = modelRoot;
			ensureAvailableAndLoaded("Models", "sdt_test", false, false,
					"Domain");
			testDomain = Domain_c.DomainInstance(modelRoot);
			modelRoot = componentRoot;
			initialized = true;
		}
		CorePlugin.disableParseAllOnResourceChange();
	}

	public void testFormalizedComponentClassOperationDTAssignement()
			throws CoreException {
		Component_c component = Component_c.ComponentInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Component_c) candidate).getName().equals(
								"IF Scope Test Component");
					}

				});
		assertNotNull(component);
		if (!component.Isformal())
			formalizeComponent(component);
		Operation_c op = Operation_c.OperationInstance(modelRoot);
		OperationO_TFRPropertySource ps = new OperationO_TFRPropertySource(op);
		ps.getPropertyDescriptors();
		ps.setPropertyValue("DataType", "component_ref");
		DataType_c dt = DataType_c.getOneS_DTOnR116(op);
		SystemModel_c testSys = null;
		if (testGlobals) {
		  testSys = SystemModel_c.getOneS_SYSOnR9100(GlobalElementInSystem_c.
			   getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt)));
		}
		else {
		  testSys = SystemModel_c
				.getOneS_SYSOnR4402(SystemDatatypeInPackage_c
						.getOneSLD_SDINPOnR4401(dt));
		}
		assertTrue("The chosen dt was not a system level one as expected.",
				testSys != null);
	}

	public void testLinkWithEditorForNewlyFormalizedComponent() {
		Component_c component = Component_c.ComponentInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Component_c) candidate).getName().equals(
								testDomain.getName());
					}

				});
		assertNotNull(component);
		m_bp_tree.collapseAll();
		if (!component.Isformal())
			formalizeComponent(component);
		waitForTransaction();
		m_bp_view.setLinkWithEditor(true);
		ExternalEntityPackage_c eepkg = ExternalEntityPackage_c
				.getOneS_EEPKOnR36(testDomain);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(eepkg);
		m_bp_view.setFocus();
		m_bp_tree.refresh();
		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		Object[] elementsAfterSelection = m_bp_tree.getExpandedElements();
		boolean result = false;
		for (int i = 0; i < elementsAfterSelection.length; i++) {
			if (elementsAfterSelection[i].equals(component)) {
				result = true;
			}
		}
		assertTrue(
				"The tree was not expanded for formal component children when link with editor was set.",
				result);
	}

	public void testFormalizedComponentNames() {
		Component_c component = Component_c.ComponentInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Component_c) candidate).getName().equals(
								testDomain.getName());
					}

				});
		assertNotNull(component);
		if (!component.Isformal())
			formalizeComponent(component);
		TreeItem item = ExplorerUtil.findItem(component);
		TreeItem fileFacade = item.getItem(0);
		assertTrue(
				"The file element in Model Explorer was not named correctly for a formal component.",
				fileFacade.getText().equals(
						testDomain.getName() + ": Component Diagram"));
		// open the component diagram
		CanvasUtilities.openCanvasEditor(component);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		assertTrue(
				"The opened diagram does not represent the formal contents of the component.",
				ce.getModel().getRepresents() instanceof Domain_c);
		assertTrue(
				"The editor title did not contain component diagram.",
				((IEditorPart) ce).getTitle().indexOf("Component Diagram") != -1);
	}

	private void formalizeComponent(Component_c component) {
		TransactionManager tm = getSystemModel().getTransactionManager();
		try {
			Transaction tr = tm.startTransaction(
					"Component Test Formalization", Ooaofooa
							.getDefaultInstance());
			component.Formalize(testDomain.getDom_id());
			tm.endTransaction(tr);
		} catch (TransactionException e) {
			fail("Unable to start component formalization transaction.");
		}
	}

}
