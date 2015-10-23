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

import java.io.File;
import java.lang.reflect.Method;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import org.xtuml.bp.core.ActorParticipant_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.test.common.TextEditorUtils;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.canvas.Cl_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.test.CanvasTest;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.ui.text.activity.ActivityEditor;
import org.xtuml.bp.ui.text.activity.ActivityEditorInputFactory;
import org.xtuml.bp.ui.text.description.DescriptionEditorInputFactory;
import org.xtuml.bp.utilities.ui.CanvasUtilities;

public class SetupCreationTests extends CanvasTest {

	String test_id = null;
	private boolean generateResults = getGenerateResults();

	public SetupCreationTests(String name) throws CoreException {
		super("org.xtuml.bp.core.test", name);

		CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,
				true);
	}

	private static boolean getGenerateResults() {
		String env = System.getenv("generateResults");
		if (env == null) {
			return false;
		} else {
			boolean result = Boolean.parseBoolean(env);
			return result;
		}
	}

	protected String getResultName() {
		return "TigerNatureTestSetup" + "_" + test_id;
	}

	protected void setUp() throws Exception {
		super.setUp();
		Display d = Display.getCurrent();
		while (d.readAndDispatch());
		Ooaofooa.setPersistEnabled(true);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public static void createNewPackage(String name, SystemModel_c systemModel) {

		Transaction t = Cl_c.Starttransaction(systemModel, "Create Package");
		systemModel.Newpackage();
		Cl_c.Endtransaction(systemModel, t);

		Package_c pkgs[] = Package_c.getManyEP_PKGsOnR1401(systemModel);
		for (int i = 0; i < pkgs.length; i++) {
			if (pkgs[i].getName().equals("Unnamed Package")) {
				Transaction t2 = Cl_c.Starttransaction(pkgs[i], "Rename Package");
				pkgs[i].setName(name);
				Cl_c.Endtransaction(pkgs[i], t2);

			}

		}
	}

	public File getDomainFile(IProject project, String domainName) {
		File modelFile = new File(project.getLocation() + File.separator + Ooaofooa.MODELS_DIRNAME + File.separator
				+ domainName + "." + Ooaofooa.MODELS_EXT);
		return modelFile;
	}
	public class Package_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			Package_c selected = (Package_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public Package_by_name_c(String name) {
			m_name = name;
		}
		private String m_name;
	}

	/**
	 * The tests below (ending with IPRs) need to be first otherwise
	 * the expected results are not correct
	 */
	// Enforce ordering of the test functions
	public void testSetupCreationTests() throws CoreException {
		dotestSetupInitialProjects();
		dotestCreatePackage();
		dotestCreatePackageInPackage();
		dotestCreateSequenceInPackage();
		dotestCreateUseCaseDiagramInSystem();
		dotestCreatePackageInPackageInPackage();
	}

	public void dotestSetupInitialProjects() throws CoreException {
		Ooaofooa.setPersistEnabled(true);
		IProject testproject1 = TestingUtilities.createProject("Test Project 1");
		SystemModel_c sysModProject1 = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(),
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						SystemModel_c selected = (SystemModel_c) candidate;
						return selected.getName().equals("Test Project 1");
					}
				});
		createNewPackage("testDomain1", sysModProject1);
		String modelRootId = Ooaofooa.createModelRootId(testproject1, "testDomain1", true);
		Ooaofooa domainModelRoot = Ooaofooa.getInstance(modelRootId, true);
		Ooaofgraphics graphicsDomainModelRoot = Ooaofgraphics.getInstance(modelRootId);
		Package_c dom = Package_c.PackageInstance(domainModelRoot);
		// Open new domain
		CanvasTestUtils.openCanvasEditor(dom);
		// Create Subsystem in domain
		AbstractTool tool = UITestingUtilities.getTool("Package");
		UITestingUtilities.activateTool(tool);
		CanvasTestUtils.createMouseEvent(100, 100, "MouseDown");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseMove");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseUp");
		UITestingUtilities.deactivateTool(tool);
		Package_c subsystem = Package_c.PackageInstance(domainModelRoot, new Package_by_name_c("Unnamed Package"));
		Transaction t = Cl_c.Starttransaction(subsystem, "Rename subsystem");
		subsystem.setName("TestSS");
		Cl_c.Endtransaction(subsystem, t);
		while (Display.getCurrent().readAndDispatch());
		CanvasTestUtils.openCanvasEditor(subsystem);
		// Create Class in Subsystem
		tool = UITestingUtilities.getTool("Classes", "Class");
		UITestingUtilities.activateTool(tool);
		CanvasTestUtils.createMouseEvent(100, 100, "MouseDown");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseMove");
		CanvasTestUtils.createMouseEvent(200, 200, "MouseUp");
		UITestingUtilities.deactivateTool(tool);
		PackageableElement_c pes[] = PackageableElement_c.getManyPE_PEsOnR8000(subsystem);
		ModelClass_c mc = ModelClass_c.getOneO_OBJOnR8001(pes);
		t = Cl_c.Starttransaction(subsystem, "Rename model class");
		mc.setName("TestClass1");
		Cl_c.Endtransaction(subsystem, t);
		while (Display.getCurrent().readAndDispatch());
		TextEditorUtils.openDescriptionEditor(mc);

		//Adding markers as part of i673 test
		Class uiTextTestClass = loadClassFromPlugin("org.xtuml.bp.ui.text.test", "UITextTest");//$NON-NLS-2$ //$NON-NLS-1$
		assertNotNull("Class UITextTest could not be loaded", uiTextTestClass);
		IFile mcFile = callGetExistingPlaceHolderFromManager(uiTextTestClass, mc,
				DescriptionEditorInputFactory.PLACEHOLDER_EXTENSION);
		assertNotNull(mcFile);
		IMarker marker = callCreateNewMarker(uiTextTestClass, mcFile);
		assertNotNull(marker);

		t = Cl_c.Starttransaction(mc, "New operation");
		mc.Newoperation();
		Cl_c.Endtransaction(mc, t);
		Operation_c op = Operation_c.OperationInstance(mc.getModelRoot());
		t = Cl_c.Starttransaction(mc, "Rename operation");
		op.setName("testOp");
		Cl_c.Endtransaction(op, t);
		while (Display.getCurrent().readAndDispatch());
		CanvasTestUtils.openActivityEditor(op);
		ActivityEditor ae = (ActivityEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		IDocument doc = ae.getDocumentProvider().getDocument(ae.getEditorInput());

		//Adding markers as part of i673 test
		IFile opFile = callGetExistingPlaceHolderFromManager(uiTextTestClass, op,
				ActivityEditorInputFactory.PLACEHOLDER_EXTENSION);
		assertNotNull(opFile);
		marker = callCreateNewMarker(uiTextTestClass, opFile);
		assertNotNull(marker);

		File modelFile = getDomainFile(testproject1, "testDomain1");
		try {
			doc.replace(0, 0, "select any test from instances of KEY;");
		} catch (BadLocationException e) {
			fail("Bad Location Exception");
		}

		assertTrue(ae.isSaveOnCloseNeeded());
		assertTrue(ae.isDirty());
		ae.doSave(new NullProgressMonitor());
		while (Display.getCurrent().readAndDispatch());
		IProject testproject2 = TestingUtilities.createProject("Test Project 2");
		SystemModel_c sysModProject2 = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(),
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						SystemModel_c selected = (SystemModel_c) candidate;
						return selected.getName().equals("Test Project 2");
					}
				});
		createNewPackage("testDomain2", sysModProject2);

		// create a sequence diagram to test that drawing a shape
		// after restart works correctly
		CanvasTestUtils.openCanvasEditor(subsystem);
		tool = UITestingUtilities.getTool("Actor");
		UITestingUtilities.activateTool(tool);
		CanvasTestUtils.createMouseEvent(400, 400, "MouseDown");
		CanvasTestUtils.createMouseEvent(500, 500, "MouseMove");
		CanvasTestUtils.createMouseEvent(500, 500, "MouseUp");
		UITestingUtilities.deactivateTool(tool);

		ActorParticipant_c sequence = ActorParticipant_c.ActorParticipantInstance(mc.getModelRoot());
		CanvasTestUtils.openCanvasEditor(sequence);
	}

	public void dotestCreatePackage() throws CoreException {
		test_id = "CreatePackage";
		final String projectName = "Package Test Project";
		IProject project = TestingUtilities.createProject(projectName);
		assertNotNull(project);
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(),
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						SystemModel_c selected = (SystemModel_c) candidate;
						return selected.getName().equals(projectName);
					}
				});
		assertNotNull(system);

		// create a package under the system
		CanvasUtilities.openCanvasEditor(system);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100, 200, 200), "Package");

		Package_c newPackage = Package_c.getOneEP_PKGOnR1401(system);
		assertNotNull("Package was not successfully created.", newPackage);
		Transaction t = Cl_c.Starttransaction(newPackage, "Rename test element");
		newPackage.setName(test_id);
		Cl_c.Endtransaction(newPackage, t);

		graphicsModelRoot = Ooaofgraphics.getDefaultInstance();
		validateOrGenerateResults(ce, generateResults, true);
	}

	public void dotestCreatePackageInPackage() {
		test_id = "CreatePackageInPackage";
		final String projectName = "Package Test Project";
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(),
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						SystemModel_c selected = (SystemModel_c) candidate;
						return selected.getName().equals(projectName);
					}
				});
		assertNotNull(system);

		Package_c newPackage = Package_c.getOneEP_PKGOnR1401(system);
		assertNotNull("Unable to find parent Package.", newPackage);

		// create a package under a package
		CanvasUtilities.openCanvasEditor(newPackage);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100, 200, 200), "Package");

		Package_c nestedPackage = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getOnePE_PEOnR8000(newPackage));
		assertNotNull("Package was not successfully created.", nestedPackage);
		Transaction t = Cl_c.Starttransaction(nestedPackage, "Rename test element");
		nestedPackage.setName(test_id);
		Cl_c.Endtransaction(nestedPackage, t);
		graphicsModelRoot = Ooaofgraphics.getInstance(newPackage.getModelRoot().getId());
		validateOrGenerateResults(ce, generateResults, true);
	}

	public void dotestCreateSequenceInPackage() {
		test_id = "CreateSequenceInPackage";
		final String projectName = "Package Test Project";
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(),
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						SystemModel_c selected = (SystemModel_c) candidate;
						return selected.getName().equals(projectName);
					}
				});
		assertNotNull(system);
		//  createNewPackage("Sequence Diagram", system);
		Package_c newPackage = Package_c.getOneEP_PKGOnR1401(system);
		assertNotNull("Unable to find parent Package.", newPackage);

		// create a package under a package
		CanvasUtilities.openCanvasEditor(newPackage);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(300, 300, 200, 200), "Package");

		Package_c seq = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(newPackage),
				new Package_by_name_c("Unnamed Package"));

		assertNotNull("Sequence was not successfully created.", seq);
		Transaction t = Cl_c.Starttransaction(seq, "Rename test element");
		seq.setName("Sequence Diagram");
		Cl_c.Endtransaction(seq, t);
		CanvasUtilities.openCanvasEditor(seq);
		graphicsModelRoot = Ooaofgraphics.getInstance(newPackage.getModelRoot().getId());
		validateOrGenerateResults(ce, generateResults, true);
	}

	public void dotestCreateUseCaseDiagramInSystem() {
		test_id = "CreateUseCaseDiagramInSystem";
		final String projectName = "Package Test Project";
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(),
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						SystemModel_c selected = (SystemModel_c) candidate;
						return selected.getName().equals(projectName);
					}
				});
		assertNotNull(system);

		// create a package under a package
		CanvasUtilities.openCanvasEditor(system);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(500, 300, 200, 200), "Package");

		Package_c[] packages = Package_c.getManyEP_PKGsOnR1401(system);
		Package_c newPackage = packages[packages.length - 1];
		assertNotNull("Use Case package was not successfully created.", newPackage);
		Transaction t = Cl_c.Starttransaction(newPackage, "Rename test element");
		newPackage.setName(test_id);
		Cl_c.Endtransaction(newPackage, t);

		graphicsModelRoot = Ooaofgraphics.getInstance(system.getModelRoot().getId());
		try {
			validateOrGenerateResults(ce, generateResults, true);
		} finally {
			// now create one of every element under the new package
			CanvasUtilities.openCanvasEditor(newPackage);
			ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

			UITestingUtilities.createShapeInDiagram(ce, new Rectangle(10, 10, 150, 100), "Use Case", "Use Case");
			UITestingUtilities.createShapeInDiagram(ce, new Rectangle(260, 10, 150, 100), "Use Case", "Use Case");
			UITestingUtilities.createShapeInDiagram(ce, new Rectangle(200, 210, 150, 100), "Interaction", "Actor");
			UITestingUtilities.createConnectorInDiagram(ce, new Point(80, 20), new Point(280, 20), "Use Case",
					"Generalization");
			UITestingUtilities.createConnectorInDiagram(ce, new Point(80, 40), new Point(280, 40), "Use Case",
					"Include");
			UITestingUtilities.createConnectorInDiagram(ce, new Point(80, 60), new Point(280, 60), "Use Case",
					"Extend");
			UITestingUtilities.createConnectorInDiagram(ce, new Point(300, 280), new Point(280, 80), "Use Case",
					"Association");

			test_id = "CreateUseCasePackageContents";
			// graphically check creations
			graphicsModelRoot = Ooaofgraphics.getInstance(newPackage.getModelRoot().getId());
			validateOrGenerateResults(ce, generateResults, true);
		}
	}

	public void dotestCreatePackageInPackageInPackage() {
		test_id = "CreatePackageInPackageInPackage";
		final String projectName = "Package Test Project";
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(),
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						SystemModel_c selected = (SystemModel_c) candidate;
						return selected.getName().equals(projectName);
					}
				});
		assertNotNull(system);

		Package_c parentPackage = Package_c.getOneEP_PKGOnR1401(system);
		assertNotNull("Unable to find parent Package.", parentPackage);

		parentPackage = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getOnePE_PEOnR8000(parentPackage));
		assertNotNull("Unable to find parent Package.", parentPackage);

		// create a package under a package
		CanvasUtilities.openCanvasEditor(parentPackage);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();

		UITestingUtilities.createShapeInDiagram(ce, new Rectangle(100, 100, 200, 200), "Package");

		Package_c nestedPackage = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getOnePE_PEOnR8000(parentPackage));
		assertNotNull("Package was not successfully created.", nestedPackage);
		Transaction t = Cl_c.Starttransaction(nestedPackage, "Rename test element");
		nestedPackage.setName(test_id);
		Cl_c.Endtransaction(nestedPackage, t);
		graphicsModelRoot = Ooaofgraphics.getInstance(parentPackage.getModelRoot().getId());
		validateOrGenerateResults(ce, generateResults, true);
	}

	static Class<?> loadClassFromPlugin(String pluginName, String className) {
		//get the required class from the given plugin
		Bundle bundle = Platform.getBundle(pluginName);
		Class<?> clazz = null;
		String packageName = pluginName;
		try {
			clazz = bundle.loadClass(packageName + "." + className); //$NON-NLS-1$
		} catch (ClassNotFoundException e) {
			fail("Could not retrieve " + className + " class : " + e.toString());
		}
		return clazz;
	}

	static IFile callGetExistingPlaceHolderFromManager(Class<?> clazz, NonRootModelElement uut, String type) {
		Object ret = null;
		try {
			Method method = clazz.getMethod("getExistingPlaceHolderFromManager", //$NON-NLS-1$
					new Class[]{NonRootModelElement.class, type.getClass(), Object.class});
			ret = method.invoke(null, new Object[]{uut, type, new Object()});
		} catch (Exception e) {
			fail("Problem during call to getExistingPlaceHolderFromManager() " + e.toString());//$NON-NLS-1$
		}

		IFile file = null;
		if (ret instanceof IFile) {
			file = (IFile) ret;
		}
		return file;
	}

	static IMarker callCreateNewMarker(Class<?> clazz, IFile file) {
		Object ret = null;
		try {
			Method method = clazz.getMethod("createNewMarker", //$NON-NLS-1$
					new Class[]{IFile.class, int.class, String.class, String.class});
			ret = method.invoke(null, new Object[]{file, new Integer(1), "This is a test marker", IMarker.BOOKMARK}); //$NON-NLS-1$
		} catch (Exception e) {
			fail("Problem during call to CreateNewMarker()" + e.toString()); //$NON-NLS-1$
		}

		IMarker marker = null;
		if (ret instanceof IMarker) {
			marker = (IMarker) ret;
		}
		return marker;
	}

}
