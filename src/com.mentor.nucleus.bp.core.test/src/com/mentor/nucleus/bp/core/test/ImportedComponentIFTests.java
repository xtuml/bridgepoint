//=====================================================================
//
//File:      $RCSfile: ImportedComponentIFTests.java,v $
//Version:   $Revision: 1.13 $
//Modified:  $Date: 2013/05/10 04:30:26 $
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

import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.ImportedReference_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.PortReference_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;
public class ImportedComponentIFTests extends BaseTest {

	private static boolean initialized = false;

	public ImportedComponentIFTests(String name) {
		super(null, name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (!initialized) {
			loadProject("SystemComponentTesting");
			modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(
					getProject().getName(), "System Component Testing", true));
			initialized = true;
		}
	}

	public void testSingleComponentInScopeInterface() {
		Interface_c iface = getInterface(true);
		assertNotNull(iface);
		Component_c compWithRef = Component_c.ComponentInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Component_c) candidate).getName().equals(
								"IF Scope Test Component");
					}

				});
		assertNotNull(compWithRef);
		Provision_c pro = Provision_c.getOneC_POnR4009(InterfaceReference_c
				.getOneC_IROnR4016(Port_c.getOneC_POOnR4010(compWithRef)));
		assertNotNull(pro);
		// formalize the provision to an interface which is in scope
		pro.Formalize(iface.getId(), false);
		runScopeMenuTest(true);
	}

	public void testMultipleComponentAtLeastOneInScopeIF() {
		Package_c compPackage = Package_c
				.PackageInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								return ((Package_c) candidate)
										.getName().equals(
												"IF Scope Test Package 1");
							}

						});
		assertNotNull(compPackage);
		compPackage.Newcomponent();
		Component_c newComp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(compPackage),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Component_c) candidate).getName().equals(
								"Unnamed Component");
					}

				});
		assertNotNull(newComp);
		compPackage.Newprovision(newComp.getId(), false, Gd_c.Null_unique_id(),
				false);
		Provision_c pro = Provision_c.getOneC_POnR4009(InterfaceReference_c
				.getOneC_IROnR4016(Port_c.getOneC_POOnR4010(newComp)));
		assertNotNull(pro);
		Interface_c iface = getInterface(false);
		pro.Formalize(iface.getId(), false);
		runScopeMenuTest(true);
	}
	
	/**
	 * Tests that re-formalizing an interface reference that is imported does
	 * not result in duplicate imported reference instances
	 * 
	 */
	public void testRemovalOfImportedReferenceOnReFormalization() {
		ModelRoot.disableChangeNotification();
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.closeAllEditors(false);
		Component_c icComp = Component_c.ComponentInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Component_c) candidate).getName().equals(
								"Imported Component Home");
					}

				});
		assertNotNull(icComp);
		ComponentReference_c ic = ComponentReference_c.getOneCL_ICOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(icComp));
		assertNotNull(ic);
		ic.Unassign();
		Component_c comp = Component_c.ComponentInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Component_c) candidate).getName().equals(
								"IF Scope Test Component");
					}

				});
		// formalize the interface reference on the component
		Provision_c pro = Provision_c.getOneC_POnR4009(InterfaceReference_c
				.getOneC_IROnR4016(Port_c.getOneC_POOnR4010(comp)));
		pro.Unformalize(false);
		Interface_c interface1 = getInterface(true);
		pro.Formalize(interface1.getId(), false);
		ic.Assigntocomp(comp.getId());
		ImportedReference_c[] irefsBefore = ImportedReference_c
				.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(ic));
		// create a new interface for formalization
		Package_c ip = Package_c.getOneEP_PKGOnR8000(PackageableElement_c.getOnePE_PEOnR8001(interface1));
		ip.Newinterface();
		Interface_c newInterface = Interface_c.getOneC_IOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(ip),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Interface_c) candidate).getName().equals(
								"Unnamed Interface");
					}

				});
		assertNotNull(newInterface);
		newInterface.setName("New Interface");
		pro.Formalize(newInterface.getId(), false);
		ImportedReference_c[] irefsAfter = ImportedReference_c
				.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(ic));
		assertTrue(
				"A duplicate imported reference was created during re-formalization.",
				irefsBefore.length == irefsAfter.length);
		ModelRoot.enableChangeNotification();
	}
	
	private void runScopeMenuTest(boolean expected) {
		Component_c comp = Component_c.ComponentInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Component_c) candidate).getName().equals(
								"Imported Component Home");
					}

				});
		assertNotNull(comp);
		CanvasUtilities.openCanvasEditor(comp);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		ComponentReference_c ic = ComponentReference_c
				.getOneCL_ICOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(comp));
		assertNotNull(ic);
		UITestingUtilities.addElementToGraphicalSelection(ic);
		if (expected) {
			assertTrue(
					"Assign menu item was not available when a usable component existed.",
					UITestingUtilities.menuItemExists(ce.getCanvas().getMenu(),
							"", "Assign Component..."));
		} else {
			assertFalse(
					"Assign menu item was available when an unusable component existed.",
					UITestingUtilities.menuItemExists(ce.getCanvas().getMenu(),
							"", "Assign Component..."));
		}
	}

	private Interface_c getInterface(boolean inScope) {
		if (inScope) {
			Interface_c iface = Interface_c.InterfaceInstance(modelRoot,
					new ClassQueryInterface_c() {

						public boolean evaluate(Object candidate) {
							return ((Interface_c) candidate).getName().equals(
									"In Scope IF");
						}

					});
			if (iface != null) {
				return iface;
			}
		} else {
			Interface_c iface = Interface_c.InterfaceInstance(modelRoot,
					new ClassQueryInterface_c() {

						public boolean evaluate(Object candidate) {
							return ((Interface_c) candidate).getName().equals(
									"Out Of Scope IF");
						}

					});
			if (iface != null) {
				return iface;
			}
		}
		return null;
	}
}
