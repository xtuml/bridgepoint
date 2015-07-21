package org.xtuml.bp.core.test;

//=====================================================================
//
//File:      $RCSfile: HierarchyTests.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/17 03:36:39 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
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
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.InterfaceReference_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Port_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.inspector.ObjectElement;
import org.xtuml.bp.core.inspector.ProvisionInspector;
import org.xtuml.bp.model.compare.providers.ModelCompareLabelProvider;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.canvas.Cl_c;
import org.xtuml.bp.ui.canvas.test.CanvasTestUtilities;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;

public class HierarchyTests extends BaseTest {

	@Override
	protected void initialSetup() throws Exception {
		// make sure the system is the Default Project
		m_sys = getSystemModel(defaultProjectName);
		// disable initial name dialog
		CorePlugin
				.getDefault()
				.getPreferenceStore()
				.setValue(
						BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,
						true);
		createInterfaceTestElements();
	}

	private void createInterfaceTestElements() {
		// create an interface, with an interface reference
		m_sys.Newpackage();
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(m_sys);
		Package_c pkg = pkgs[pkgs.length - 1];
		CanvasTestUtilities.openDiagramEditor(pkg);
		GraphicalEditor editor = (GraphicalEditor) UITestingUtilities
				.getActiveEditor();
		UITestingUtilities.createShapeInDiagram(editor, new Rectangle(100, 100,
				200, 200), "Components", "Interface");
		m_sys.Newpackage();
		pkgs = Package_c.getManyEP_PKGsOnR1401(m_sys);
		pkg = pkgs[pkgs.length - 1];
		CanvasTestUtilities.openDiagramEditor(pkg);
		editor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		UITestingUtilities.createShapeInDiagram(editor, new Rectangle(100, 100,
				200, 200), "Components", "Component");
		UITestingUtilities.createConnectorInDiagram(editor,
				new Point(150, 150), new Point(375, 150), "Components",
				"Provided Interface");
	}

	public void testInterfacePathCreation() {
		Interface_c iface = Interface_c.getOneC_IOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1401(m_sys)));
		String path = Cl_c.Getpath(iface);
		assertEquals("Path creation for an interface was not correct",
				"Default Project::Unnamed Package::Unnamed Interface", path);
	}

	public void testInterfaceReferenceChildren() {
		Provision_c provision = Provision_c
				.getOneC_POnR4009(InterfaceReference_c
						.getManyC_IRsOnR4016(Port_c
								.getManyC_POsOnR4010(Component_c
										.getManyC_CsOnR8001(PackageableElement_c
												.getManyPE_PEsOnR8000(Package_c
														.getManyEP_PKGsOnR1401(m_sys))))));
		Interface_c iface = Interface_c.getOneC_IOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1401(m_sys)));
		provision.Formalize(iface.getId(), false);
		ProvisionInspector proInspector = new ProvisionInspector(null);
		ObjectElement[] childRelations = proInspector
				.getChildRelations(provision);
		for (int i = 0; i < childRelations.length; i++) {
			if (childRelations[i].getValue() instanceof Interface_c) {
				fail("Interface was found as a child of the interface reference");
			}
		}
		ObjectElement[] referentials = proInspector.getReferentials(provision);
		Object referetial = null;
		for (int i = 0; i < referentials.length; i++) {
			if (referentials[i].getName().equals(
					"referential_Referenced_Interface")) {
				referetial = referentials[i];
				break;
			}
		}
		assertNotNull(
				"Did not find the interface reference under an provision.",
				referetial);
		ModelCompareLabelProvider provider = new ModelCompareLabelProvider();
		String path = provider.getColumnText(referetial, 1);
		assertEquals("Name for referential was not correct.",
				"Unnamed Interface", path);
	}

}
