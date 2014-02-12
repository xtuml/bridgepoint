// ========================================================================
//
//File: $RCSfile: TigerNatureExistingProjectsTestGenerics.java,v $
//Version: $Revision: 1.12 $
//Modified: $Date: 2013/05/10 04:30:26 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.core.test;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
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

import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.ExecutableProperty_c;
import com.mentor.nucleus.bp.core.InterfaceOperation_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.PropertyParameter_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTestResult;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTestUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditorInput;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;
import com.mentor.nucleus.bp.ui.properties.ChooserPropertyDescriptor;
import com.mentor.nucleus.bp.ui.properties.OperationsC_IOPropertySource;
import com.mentor.nucleus.bp.ui.properties.ParametersC_PPPropertySource;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditorInputFactory;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public class TigerNatureExistingProjectsTestGenerics extends CanvasTest {

	String test_id = null;
	boolean generateResults = getGenerateResults();

	private static boolean getGenerateResults() {
		String env = System.getenv("generateResults");
		if (env == null) {
			return false;
		} else {
			boolean result = Boolean.parseBoolean(env);
			return result;
		}
	}

	public TigerNatureExistingProjectsTestGenerics(String name) {
		super("com.mentor.nucleus.bp.core.test", name);
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
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				"Test Project 3");
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
		IEditorReference x[] = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		for (int i = 0; i < x.length; ++i) {

			String editor = x[i].getName();

			if (editor.equals(editorName)) {
				return x[i].getEditor(true);
			}
		}
		return null;
	}

	String[] expected_string = new String[]{
			"drawRectangle(16416, 12384, 384, 432)",
			"drawText(" + String.valueOf('"') + "Unnamed ..."
					+ String.valueOf('"') + ", 16432, 12397, true)",
			"drawline(16416, 12492, 16800, 12492)"};
	/**
	 * Create a new shape on an existing sequence diagram, testing
	 * that it is drawn correctly.
	 * @throws PartInitException 
	 */
	public void testNewShapeAfterRestartOnSequenceDiagram()
			throws PartInitException {
		IEditorReference[] editorReferences = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		IEditorInput editorInput = null;
		String id = "";
		for (int i = 0; i < editorReferences.length; i++) {
			if (editorReferences[i].getName().equals(
					"Sequence Diagram: Package Diagram")) {
				editorInput = editorReferences[i].getEditorInput();
				id = editorReferences[i].getId();
			}
		}
		assertNotNull(editorInput);

		Object editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().openEditor(editorInput, id);
		GraphicalEditor ce = ((ModelEditor) editor).getGraphicalEditor();

		graphicsModelRoot = Ooaofgraphics.getInstance(ce.getModel()
				.getModelRoot().getId());

		AbstractTool tool = UITestingUtilities.getTool("Instance");
		UITestingUtilities.activateTool(tool);

		CanvasTestUtilities.doMouseMove(100, 100);
		CanvasTestUtilities.doMousePress(100, 100);
		CanvasTestUtilities.doMouseMove(200, 200);
		CanvasTestUtilities.doMouseRelease(200, 200);

		UITestingUtilities.deactivateTool(tool);

		CanvasTestResult result = drawDiagram(ce, true, false, false,
				new Rectangle(0, 0, 1231, 861));

		for (int i = 0; i < expected_string.length; i++) {
			assertTrue("Diagram draw did not generate correct results:\n"
					+ "expected: " + expected_string[i] + "\n" + "actual: "
					+ result.transcript[i], expected_string[i]
					.equals(result.transcript[i]));
		}
	}

	// this test needs to be first in order to test that the
	// tree is opened to the selected class

	public void testLinkWithEditor() {
		IEditorPart ss = checkForOpenEditors("TestSS: Package Diagram");
		assertNotNull(ss);

		// bring the canvas editor to the front
		ss.getEditorSite().getPage().activate(ss);
		Package_c ss_obj = (Package_c) ((GraphicalEditorInput) ss
				.getEditorInput()).getInput().getRepresents();
		modelRoot = (Ooaofooa) ss_obj.getModelRoot();

		IWorkbenchPage page = TestUtil.showBridgePointPerspective();
		ExplorerUtil.showModelExplorer();
		ExplorerUtil.setLinkWithEditor(true);

		//Getting Class TestClass1
		ModelClass_c obj = ModelClass_c.ModelClassInstance(modelRoot,
				new ClassQueryInterface_c() {
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
		
		assertTrue(
				"Did not find existing class, TestClass1, in the explorer view",
				checkForTreeItemInTree("TestClass1"));

	}

	public void testXTUMLProjectsExist() {
		assertTrue(
				"Did not find existing project, Test Project 1, in the explorer view",
				checkForTreeItem("Test Project 1"));
		assertTrue(
				"Did not find existing domain, testDomain1, in the  explorer view",
				checkForTreeItem("testDomain1"));
		assertTrue(
				"Did not find existing project, Test Project 2, in the explorer view",
				checkForTreeItem("Test Project 2"));
		assertTrue(
				"Did not find existing domain, testDomain2, in the  explorer view",
				checkForTreeItem("testDomain2"));
	}

	public void testEditorsRemainOpenAfterClose() {
		assertNotNull(
				"Editor, TestSS: Class Diagram, did not correctly restore",
				checkForOpenEditors("TestSS: Package Diagram"));
		assertNotNull(
				"Editor, testOp: Operation Activity, did not correctly restore",
				checkForOpenEditors("testOp: Operation Activity"));
		assertNotNull(
				"Editor, TestClass1: Model Class Description, did not correctly restore",
				checkForOpenEditors("TestClass1: Model Class Description"));
	}

	public void testPlacholderInstances() {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				"Test Project 1");

		String modelRootId = Ooaofooa.createModelRootId(project, "testDomain1",
				true);
		modelRoot = Ooaofooa.getInstance(modelRootId, true);
		//Getting subsystem
		Package_c ss = Package_c.PackageInstance(modelRoot,
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						Package_c ss = (Package_c) candidate;
						return ss.getName().equals("TestSS");
					}
				});

		assertNotNull(ss);

		//Getting Class TestClass1
		ModelClass_c obj = ModelClass_c.ModelClassInstance(modelRoot,
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						ModelClass_c uut = (ModelClass_c) candidate;
						return uut.getName().equals("TestClass1");
					}
				});
		assertNotNull(obj);

		//Getting operation
		Operation_c op = Operation_c.OperationInstance(modelRoot,
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						Operation_c uut = (Operation_c) candidate;
						return uut.getName().equals("testOp");
					}
				});
		assertNotNull(op);

		Class clazz = TigerNatureWorkspaceSetupTestGenerics.loadClassFromPlugin(
				"com.mentor.nucleus.bp.ui.text.test", "UITextTest"); //$NON-NLS-2$ //$NON-NLS-1$
		IFile objFile = TigerNatureWorkspaceSetupTestGenerics
				.callGetExistingPlaceHolderFromManager(clazz, obj,
						DescriptionEditorInputFactory.PLACEHOLDER_EXTENSION);
		assertNotNull(objFile);
		IFile opFile = TigerNatureWorkspaceSetupTestGenerics
				.callGetExistingPlaceHolderFromManager(clazz, op,
						ActivityEditorInputFactory.PLACEHOLDER_EXTENSION);
		assertNotNull(opFile);

		//The underlying files should exist
		assertTrue(objFile.exists());
		assertTrue(opFile.exists());

	}

	public void testInterfaceAssignmentInterfaceContainedInDifferentPackageRoot() {
		test_id = "InterfaceAssignment";
		// open the pre-created component package
		final String projectName = "Package Test Project";
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(projectName);
			}
		});
		assertNotNull(system);

		Package_c containingPackage = Package_c.getOneEP_PKGOnR1405(system,
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								"InterfaceAssignmentSetup-2");
					}
				});
		assertNotNull("Unable to locate containing package.", containingPackage);

		Package_c compPkg = Package_c.PackageInstance(containingPackage
				.getModelRoot(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals(
						"Component Package");
			}
		});

		assertNotNull(
				"Unable to location component package for interface reference.",
				compPkg);

		Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(compPkg));
		assertNotNull(
				"Unable to location component which the interface reference connects to.",
				comp);

		Provision_c provision = Provision_c
				.getOneC_POnR4009(InterfaceReference_c.getOneC_IROnR4016(Port_c
						.getOneC_POOnR4010(comp)));
		assertNotNull("Unable to location interface reference.", provision);

		CanvasUtilities.openCanvasEditor(compPkg);
		UITestingUtilities.clearGraphicalSelection();
		GraphicalEditor ce = UITestingUtilities
				.addElementToGraphicalSelection(provision);
		assertTrue("Formalize was not available.", UITestingUtilities
				.checkItemStatusInContextMenu(ce.getCanvas().getMenu(),
						"Formalize...", "", false));

		// this selection is done after the check above
		// to test that loading properly occurs
		Interface_c iface = Interface_c.getOneC_IOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(system)),
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((Interface_c) candidate).getName().equals(
								"InterfaceAssignmentSetup-1");
					}
				});
		assertNotNull("Unable to locate interface for assignment.", iface);

		provision.Formalize(iface.getId(), false);

		validateOrGenerateResults(ce, generateResults, true);
	}

	public void testComponentAssignmentComponentContainedInDifferentPackageRoot() {
		test_id = "ComponentAssignment";
		// open the pre-created component package
		final String projectName = "Package Test Project";
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(projectName);
			}
		});
		assertNotNull(system);

		Package_c containingPackage = Package_c.getOneEP_PKGOnR1405(system,
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								"ComponentAssignmentSetup-2");
					}
				});
		assertNotNull("Unable to locate containing package.", containingPackage);

		Package_c compPkg = Package_c.PackageInstance(containingPackage
				.getModelRoot(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals(
						"Component Reference Package");
			}
		});
		assertNotNull(
				"Unable to location component package for interface reference.",
				compPkg);

		ComponentReference_c icomp = ComponentReference_c
				.getOneCL_ICOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(Package_c
								.getManyEP_PKGsOnR1405(system)));
		assertNotNull(
				"Unable to location component which the interface reference connects to.",
				icomp);

		CanvasUtilities.openCanvasEditor(compPkg);
		UITestingUtilities.clearGraphicalSelection();

		GraphicalEditor ce = UITestingUtilities
				.addElementToGraphicalSelection(icomp);
		assertTrue("Assign Component was not available.", UITestingUtilities
				.checkItemStatusInContextMenu(ce.getCanvas().getMenu(),
						"Assign Component...", "", false));

		// this selection is done after the check above
		// to test that loading properly occurs
		Component_c component = Component_c.getOneC_COnR8001(

		PackageableElement_c.getManyPE_PEsOnR8000(Package_c
				.getManyEP_PKGsOnR1405(system)),

		new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((Component_c) candidate).getName().equals(
						"ComponentAssignmentSetup-1");
			}

		});
		assertNotNull("Unable to locate component for assignment.", component);

		icomp.Assigntocomp(component.getId());

		validateOrGenerateResults(ce, generateResults, true);
	}

	public void testDataTypeAssignmentInterfaceOperationInDifferentPackageRoot() {
		test_id = "DataTypeAssignmentInterfaceOperation";
		// open the pre-created component package
		final String projectName = "Package Test Project";
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(projectName);
			}
		});
		assertNotNull(system);
		Interface_c iface = Interface_c.getOneC_IOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(system)),
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((Interface_c) candidate).getName().equals(
								"InterfaceAssignmentSetup-1");
					}
				});

		assertNotNull("Unable to find interface to add operation to.", iface);
		iface.Newexecutableproperty(false);
		InterfaceOperation_c io = InterfaceOperation_c
				.getOneC_IOOnR4004(ExecutableProperty_c
						.getOneC_EPOnR4003(iface));
		assertNotNull("Unable to create interface operation.", io);
		Transaction t = Cl_c.Starttransaction(io, "Rename test element");
		io.setName(test_id);
		Cl_c.Endtransaction(io, t);

		TestUtil.showBridgePointPerspective();
		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch());

		// verify that dt defined in another package can
		// be used as return type
		// create and select a synchronous message
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(io);

		OperationsC_IOPropertySource propertySource = new OperationsC_IOPropertySource(
				io);
		int result = -1;
		IPropertyDescriptor[] propertyDescriptors = propertySource
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			if (propertyDescriptors[i].getDisplayName().equals("Return Type")) {
				if (propertyDescriptors[i] instanceof ChooserPropertyDescriptor) {
					ChooserPropertyDescriptor pd = (ChooserPropertyDescriptor) propertyDescriptors[i];
					int numDtsAvailable = pd.numValues();
					for (int j = 0; j < numDtsAvailable; j++) {
						String val = pd.enumValue(j);
						if (val.equals("DataTypeAssignmentSetup")) {
							result = j;
							break;
						}
					}
				}
			}
		}
		//jj
		assertTrue(
				"Data Type defined in another package is not available for assignment.",
				result != -1);
		propertySource.setPropertyValue("DataType", "DataTypeAssignmentSetup");
		DataType_c dt = DataType_c.getOneS_DTOnR4008(io);
		assertNotNull(dt);
		assertTrue(
				"Data Type defined in another package was not allowed as return type.",
				dt.getName().equals("DataTypeAssignmentSetup"));
	}

	public void testDataTypeAssignmentPropertyParameterInDifferentPackageRoot() {
		test_id = "DataTypeAssignmentPropertyParameter";
		// open the pre-created component package
		final String projectName = "Package Test Project";
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(projectName);
			}
		});
		assertNotNull(system);
		Interface_c iface = Interface_c.getOneC_IOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(system)),
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((Interface_c) candidate).getName().equals(
								"InterfaceAssignmentSetup-1");
					}
				});
		assertNotNull("Unable to find interface with operation", iface);
		InterfaceOperation_c io = InterfaceOperation_c
				.getOneC_IOOnR4004(ExecutableProperty_c
						.getOneC_EPOnR4003(iface));
		assertNotNull("Unable to find interface operation.", io);

		io.Newparameter();
		PropertyParameter_c pp = PropertyParameter_c
				.getOneC_PPOnR4006(ExecutableProperty_c
						.getOneC_EPOnR4003(iface));
		assertNotNull("Property parameter was not created.", pp);
		Transaction t = Cl_c.Starttransaction(pp, "Rename test element");
		pp.setName(test_id);
		Cl_c.Endtransaction(pp, t);

		TestUtil.showBridgePointPerspective();
		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch());

		// verify that dt defined in another package can
		// be used as return type
		// create and select a synchronous message
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(pp);

		ParametersC_PPPropertySource propertySource = new ParametersC_PPPropertySource(
				pp);
		int result = -1;
		IPropertyDescriptor[] propertyDescriptors = propertySource
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			if (propertyDescriptors[i].getDisplayName().equals("Type")) {
				if (propertyDescriptors[i] instanceof ChooserPropertyDescriptor) {
					ChooserPropertyDescriptor pd = (ChooserPropertyDescriptor) propertyDescriptors[i];
					int numDtsAvailable = pd.numValues();
					for (int j = 0; j < numDtsAvailable; j++) {
						String val = pd.enumValue(j);
						if (val.equals("DataTypeAssignmentSetup")) {
							result = j;
							break;
						}
					}
				}
			}
		}
		assertTrue(
				"Data Type defined in another package is not available for assignment.",
				result != -1);
		propertySource.setPropertyValue("DataType", result);
		DataType_c dt = DataType_c.getOneS_DTOnR4008(io);
		assertNotNull(dt);
		assertTrue(
				"Data Type defined in another package was not allowed as parameter type.",
				dt.getName().equals("DataTypeAssignmentSetup"));
	}

//	public void testDataTypeAssignmentBridgeUnderFormalComponent() {
//		test_id = "DataTypeAssignmentFormalComponentBridge";
//		final String projectName = "Package Test Project";
//		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
//				.getDefaultInstance(), new ClassQueryInterface_c() {
//			public boolean evaluate(Object candidate) {
//				SystemModel_c selected = (SystemModel_c) candidate;
//				return selected.getName().equals(projectName);
//			}
//		});
//		assertNotNull(system);
//
//		CanvasTestUtilities.openCanvasEditor(system);
//		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities
//				.getActiveEditor();
//
//		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 400,
//				200, 200), "Package");
//		Package_c otherPackage = Package_c.getOneEP_PKGOnR1401(system,
//				new ClassQueryInterface_c() {
//
//					public boolean evaluate(Object candidate) {
//						return ((Package_c) candidate).getName().equals(
//								"Unnamed Package");
//					}
//
//				});
//		assertNotNull("Other package was not created successfully.",
//				otherPackage);
//		Transaction t = Cl_c.Starttransaction(otherPackage,
//				"Rename test element");
//		otherPackage.setName(test_id);
//		Cl_c.Endtransaction(otherPackage, t);
//
//		dispatchEvents(0);
//
//		// create a component package
//		CanvasTestUtilities.openCanvasEditor(otherPackage);
//		ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();
//
//		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100,
//				200, 200), "Package");
//
//		Package_c compPkg = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(otherPackage)
//				, new ClassQueryInterface_c() {
//			public boolean evaluate(Object candidate) {
//				return ((Package_c) candidate).getName().equals(
//						"Unnamed Package");
//			}
//		});
//
//		
//		assertNotNull("Component Package was not created successfully.",
//				compPkg);
//
//		Transaction tt = Cl_c.Starttransaction(compPkg, "Rename test element");
//		compPkg.setName("Component Package Diagram");
//		Cl_c.Endtransaction(compPkg, tt);
//
//		// create a formal component
//		CanvasTestUtilities.openCanvasEditor(compPkg);
//		ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();
//
//		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100,
//				200, 200), "Components","Component");
//		Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c
//				.getManyPE_PEsOnR8000(compPkg));
//		assertNotNull("Component was not successfully created.", comp);
//
//		dispatchEvents(0);
//
//		TigerNatureWorkspaceSetupTestGenerics.createNewPackage(test_id, system);
//
//		Package_c domain = Package_c.getOneEP_PKGOnR1401(system ,new ClassQueryInterface_c() {
//			public boolean evaluate(Object candidate) {
//				return ((Package_c) candidate).getName().equals(test_id);
//			}
//		});
//		assertNotNull(domain);
//		comp.Formalize(domain.getPackage_id());
//
//		Bridge_c brg = Bridge_c.getOneS_BRGOnR19(ExternalEntity_c
//				.getOneS_EEOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(domain)));
//		assertNotNull(brg);
//
//		Selection.getInstance().clear();
//		Selection.getInstance().addToSelection(brg);
//
//		BridgeS_BRGPropertySource propertySource = new BridgeS_BRGPropertySource(
//				brg);
//		int result = -1;
//		IPropertyDescriptor[] propertyDescriptors = propertySource
//				.getPropertyDescriptors();
//		for (int i = 0; i < propertyDescriptors.length; i++) {
//			if (propertyDescriptors[i].getDisplayName().equals("Return Type")) {
//				if (propertyDescriptors[i] instanceof EnumPropertyDescriptor) {
//					EnumPropertyDescriptor pd = (EnumPropertyDescriptor) propertyDescriptors[i];
//					int numDtsAvailable = pd.numValues();
//					for (int j = 0; j < numDtsAvailable; j++) {
//						String val = pd.enumValue(j);
//						if (val.equals("DataTypeAssignmentSetup")) {
//							result = j;
//							break;
//						}
//					}
//				}
//			}
//		}
//		assertTrue(
//				"Data Type defined in another package is not available for assignment.",
//				result != -1);
//		propertySource.setPropertyValue("DataType", result);
//		DataType_c dt = DataType_c.getOneS_DTOnR20(brg);
//		assertNotNull(dt);
//		assertTrue(
//				"Data Type defined in another package was not allowed as formal component bridge type.",
//				dt.getName().equals("DataTypeAssignmentSetup"));
//	}

//	public void testDataTypeAssignmentFormalComponentTypeInterfaceOperation() {
//		test_id = "DataTypeAssignmentFormalComponentTypeInterfaceOperation";
//		final String projectName = "Package Test Project";
//		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
//				.getDefaultInstance(), new ClassQueryInterface_c() {
//			public boolean evaluate(Object candidate) {
//				SystemModel_c selected = (SystemModel_c) candidate;
//				return selected.getName().equals(projectName);
//			}
//		});
//		assertNotNull(system);
//		Component_c formalComp = Component_c
//				.getOneC_COnR4204(DomainAsComponent_c
//						.getManyCN_DCsOnR4204(Component_c
//								.getManyC_CsOnR4608(ComponentPackage_c
//										.getManyCP_CPsOnR4606(system))));
//		assertNotNull(formalComp);
//
//		// create a data type under the formal component
//		DataTypePackage_c dtPkg = DataTypePackage_c.getOneS_DPKOnR40(Domain_c
//				.getOneS_DOMOnR4204(DomainAsComponent_c
//						.getOneCN_DCOnR4204(formalComp)));
//		CanvasTestUtilities.openCanvasEditor(dtPkg);
//		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities
//				.getActiveEditor();
//
//		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100,
//				200, 200), "User Data Type");
//
//		UserDataType_c udt = UserDataType_c.getOneS_UDTOnR17(DataType_c
//				.getOneS_DTOnR39(DataTypeInPackage_c.getOneS_DIPOnR39(dtPkg)));
//		assertNotNull("User Data Type was not successfully created.", udt);
//		DataType_c dt = DataType_c.getOneS_DTOnR17(udt);
//		Transaction t = Cl_c.Starttransaction(udt, "Rename test element");
//		dt.setName("DataTypeAssignmentFormalComponentType");
//		Cl_c.Endtransaction(udt, t);
//
//		Interface_c iface = Interface_c.getOneC_IOnR4303(InterfacePackage_c
//				.getManyIP_IPsOnR4304(system), new ClassQueryInterface_c() {
//			public boolean evaluate(Object candidate) {
//				return ((Interface_c) candidate).getName().equals(
//						"InterfaceAssignmentSetup-1");
//			}
//		});
//		assertNotNull("Unable to find interface to add operation to.", iface);
//		InterfaceOperation_c io = InterfaceOperation_c
//				.getOneC_IOOnR4004(ExecutableProperty_c
//						.getOneC_EPOnR4003(iface));
//
//		TestUtil.showBridgePointPerspective();
//		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch());
//
//		// verify that dt defined in another package can
//		// be used as return type
//		// create and select a synchronous message
//		Selection.getInstance().clear();
//		Selection.getInstance().addToSelection(io);
//
//		OperationsC_IOPropertySource propertySource = new OperationsC_IOPropertySource(
//				io);
//		int result = -1;
//		IPropertyDescriptor[] propertyDescriptors = propertySource
//				.getPropertyDescriptors();
//		for (int i = 0; i < propertyDescriptors.length; i++) {
//			if (propertyDescriptors[i].getDisplayName().equals("Return Type")) {
//				if (propertyDescriptors[i] instanceof EnumPropertyDescriptor) {
//					EnumPropertyDescriptor pd = (EnumPropertyDescriptor) propertyDescriptors[i];
//					int numDtsAvailable = pd.numValues();
//					for (int j = 0; j < numDtsAvailable; j++) {
//						String val = pd.enumValue(j);
//						if (val.equals("DataTypeAssignmentFormalComponentType")) {
//							result = j;
//							break;
//						}
//					}
//				}
//			}
//		}
//		assertFalse(
//				"Data Type defined in formal component was available for assignment.",
//				result != -1);
//	}

//	public void testDataTypeAssignmentFormalComponentTypePropertyParameter() {
//		test_id = "DataTypeAssignmentFormalComponentTypePropertyParameter";
//		final String projectName = "Package Test Project";
//		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
//				.getDefaultInstance(), new ClassQueryInterface_c() {
//			public boolean evaluate(Object candidate) {
//				SystemModel_c selected = (SystemModel_c) candidate;
//				return selected.getName().equals(projectName);
//			}
//		});
//		assertNotNull(system);
//		Component_c formalComp = Component_c
//				.getOneC_COnR4204(DomainAsComponent_c
//						.getManyCN_DCsOnR4204(Component_c
//								.getManyC_CsOnR4608(ComponentPackage_c
//										.getManyCP_CPsOnR4606(system))));
//		assertNotNull(formalComp);
//
//		Interface_c iface = Interface_c.getOneC_IOnR4303(InterfacePackage_c
//				.getManyIP_IPsOnR4304(system), new ClassQueryInterface_c() {
//			public boolean evaluate(Object candidate) {
//				return ((Interface_c) candidate).getName().equals(
//						"InterfaceAssignmentSetup-1");
//			}
//		});
//		assertNotNull("Unable to find interface to add operation to.", iface);
//		PropertyParameter_c pp = PropertyParameter_c
//				.getOneC_PPOnR4006(ExecutableProperty_c
//						.getOneC_EPOnR4003(iface));
//		assertNotNull(pp);
//
//		TestUtil.showBridgePointPerspective();
//		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch());
//
//		// verify that dt defined in another package can
//		// be used as return type
//		// create and select a synchronous message
//		Selection.getInstance().clear();
//		Selection.getInstance().addToSelection(pp);
//
//		ParametersC_PPPropertySource propertySource = new ParametersC_PPPropertySource(
//				pp);
//		int result = -1;
//		IPropertyDescriptor[] propertyDescriptors = propertySource
//				.getPropertyDescriptors();
//		for (int i = 0; i < propertyDescriptors.length; i++) {
//			if (propertyDescriptors[i].getDisplayName().equals("Type")) {
//				if (propertyDescriptors[i] instanceof EnumPropertyDescriptor) {
//					EnumPropertyDescriptor pd = (EnumPropertyDescriptor) propertyDescriptors[i];
//					int numDtsAvailable = pd.numValues();
//					for (int j = 0; j < numDtsAvailable; j++) {
//						String val = pd.enumValue(j);
//						if (val.equals("DataTypeAssignmentFormalComponentType")) {
//							result = j;
//							break;
//						}
//					}
//				}
//			}
//		}
//		assertFalse(
//				"Data Type defined in formal component was available for assignment.",
//				result != -1);
//	}
	
	public void testUseCasePackageOnRestart() {
		test_id = "LoadUseCaseDiagramOnRestart";
		final String projectName = "Package Test Project";
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				SystemModel_c selected = (SystemModel_c) candidate;
				return selected.getName().equals(projectName);
			}
		});
		assertNotNull(system);

		// create a package under a package
		Package_c usecasePackage = Package_c.getOneEP_PKGOnR1401(system, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("CreateUseCaseDiagramInSystem");
			}
		});
		assertNotNull("Use Case package was not successfully loaded.",
				usecasePackage);

		// graphically compare the editor after restart
		CanvasUtilities.openCanvasEditor(usecasePackage);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		graphicsModelRoot = Ooaofgraphics.getInstance(usecasePackage.getModelRoot().getId());
		validateOrGenerateResults(ce, generateResults, true);
	}
	
	public void testPackageParticipantFormalizedToIPREditorRestoration() {
		test_id = "PackageParticipantFormalizationIPR";
		// locate the editors in question
		IEditorReference[] editorReferences = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		IEditorReference editorReference = null;
		for(IEditorReference reference : editorReferences) {
			if(reference.getName().contains("package_part_container")) {
				editorReference = reference;
			}
		}
		assertNotNull("Could not locate package diagram for participant.", editorReference);
		IEditorPart editor = editorReference.getEditor(true);
		assertTrue("Found the incorrect editor.", editor instanceof ModelEditor);
		GraphicalEditor gEditor = (GraphicalEditor) ((ModelEditor) editor).getActivePart();
		validateOrGenerateResults(gEditor, generateResults);
	}

	public void testComponentParticipantFormalizedToIPREditorRestoration() {
		test_id = "ComponentParticipantFormalizationIPR";
		// locate the editors in question
		IEditorReference[] editorReferences = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		IEditorReference editorReference = null;
		for(IEditorReference reference : editorReferences) {
			if(reference.getName().contains("component_part_container")) {
				editorReference = reference;
			}
		}
		assertNotNull("Could not locate package diagram for participant.", editorReference);
		IEditorPart editor = editorReference.getEditor(true);
		assertTrue("Found the incorrect editor.", editor instanceof ModelEditor);
		GraphicalEditor gEditor = (GraphicalEditor) ((ModelEditor) editor).getActivePart();
		validateOrGenerateResults(gEditor, generateResults);
	}
	
	public void testExternalEntityParticipantFormalizedToIPREditorRestoration() {
		test_id = "EEParticipantFormalizationIPR";
		// locate the editors in question
		IEditorReference[] editorReferences = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		IEditorReference editorReference = null;
		for(IEditorReference reference : editorReferences) {
			if(reference.getName().contains("ee_part_container")) {
				editorReference = reference;
			}
		}
		assertNotNull("Could not locate package diagram for participant.", editorReference);
		IEditorPart editor = editorReference.getEditor(true);
		assertTrue("Found the incorrect editor.", editor instanceof ModelEditor);
		GraphicalEditor gEditor = (GraphicalEditor) ((ModelEditor) editor).getActivePart();
		validateOrGenerateResults(gEditor, generateResults);
	}
	
	public void testClassParticipantFormalizedToIPREditorRestoration() {
		test_id = "ClassParticipantFormalizationIPR";
		// locate the editors in question
		IEditorReference[] editorReferences = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		IEditorReference editorReference = null;
		for(IEditorReference reference : editorReferences) {
			if(reference.getName().contains("class_part_container")) {
				editorReference = reference;
			}
		}
		assertNotNull("Could not locate package diagram for participant.", editorReference);
		IEditorPart editor = editorReference.getEditor(true);
		assertTrue("Found the incorrect editor.", editor instanceof ModelEditor);
		GraphicalEditor gEditor = (GraphicalEditor) ((ModelEditor) editor).getActivePart();
		validateOrGenerateResults(gEditor, generateResults);
	}
	
	public void testClassInstanceParticipantFormalizedToIPREditorRestoration() {
		test_id = "ClassInstParticipantFormalizationIPR";
		// locate the editors in question
		IEditorReference[] editorReferences = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		IEditorReference editorReference = null;
		for(IEditorReference reference : editorReferences) {
			if(reference.getName().contains("classinst_part_container")) {
				editorReference = reference;
			}
		}
		assertNotNull("Could not locate package diagram for participant.", editorReference);
		IEditorPart editor = editorReference.getEditor(true);
		assertTrue("Found the incorrect editor.", editor instanceof ModelEditor);
		GraphicalEditor gEditor = (GraphicalEditor) ((ModelEditor) editor).getActivePart();
		validateOrGenerateResults(gEditor, generateResults);
	}
}