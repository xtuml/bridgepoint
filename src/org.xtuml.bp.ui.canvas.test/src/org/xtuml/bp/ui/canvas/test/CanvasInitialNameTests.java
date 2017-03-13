package org.xtuml.bp.ui.canvas.test;
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
import java.lang.reflect.Method;

import org.eclipse.core.internal.utils.Messages;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.ConstantSpecification_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.Message_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.StructuredDataType_c;
import org.xtuml.bp.core.SynchronousMessage_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ModelElement;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionException;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.canvas.Cl_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;

@RunWith(OrderedRunner.class)
public class CanvasInitialNameTests extends BaseTest {

	@Rule public TestName name = new TestName();
	
	private static final String INVALID_CHAR_ERROR = "* is an invalid character in resource name '*'.";
	private static final String INVALID_UNIX_CHAR_ERROR = "/ is an invalid character in resource name '/'.";
	private static final String EXISTING_FOLDER_ERROR = "A model element with the same name already exists.\r\n\r\n"
			+ "This is most likely due to uncommitted renames or deletions."
			+ "  If this is the case, please commit any outstanding changes before proceeding.\r\n\r\n"
			+ "If the project is not connected to a configuration management system"
			+ ", you will need to remove the target manually.";
	private static Package_c testPackage;
	private static GraphicalEditor editor;

	private static boolean isFirstTime = true;
	@Override
	@Before
	public void initialSetup() throws CoreException {
		// create test system
		if (!isFirstTime)
			return;
		isFirstTime = false;
		project = TestingUtilities.createProject("CanvasInitialNameTests");
		m_sys = getSystemModel(project.getName());
		m_sys.Newpackage();
		Package_c[] testPackages = Package_c.getManyEP_PKGsOnR1401(m_sys);
		testPackage = testPackages[testPackages.length - 1];
		CanvasTestUtilities.openDiagramEditor(testPackage);
		editor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION, false);
		Ooaofooa.setPersistEnabled(true);
	}
	
	@Override
	@After
	public void tearDown() {
		errorTxt = "";
		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
			;
		UITestingUtilities.clearGraphicalSelection();
		CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,true);
		Ooaofooa.setPersistEnabled(false);
	}
	
	public String getName(){
		return name.getMethodName();
	}

	@Test
	public void testOperationWithSpaces() {
		createElement("Newclass", testPackage);
		ModelClass_c[] classes = ModelClass_c
				.getManyO_OBJsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPackage));
		ModelClass_c clazz = classes[classes.length - 1];
		UITestingUtilities.addElementToGraphicalSelection(clazz);
		doNewCMETest("Operation", CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testBridgeWithSpaces() {
		createElement("Newexternalentity", testPackage);
		ExternalEntity_c[] ees = ExternalEntity_c
				.getManyS_EEsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPackage));
		ExternalEntity_c ee = ees[ees.length - 1];
		UITestingUtilities.addElementToGraphicalSelection(ee);
		doNewCMETest("Bridge Operation", CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testEnumeratorWithSpaces() {
		createElement("Newenumeration", testPackage);
		EnumerationDataType_c[] edts = EnumerationDataType_c
				.getManyS_EDTsOnR17(DataType_c
						.getManyS_DTsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(testPackage)));
		EnumerationDataType_c edt = edts[edts.length - 1];
		UITestingUtilities.addElementToGraphicalSelection(edt);
		doNewCMETest("Enumerator", CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testMemberWithSpaces() {
		createElement("Newstructureddatatype", testPackage);
		StructuredDataType_c[] sdts = StructuredDataType_c
				.getManyS_SDTsOnR17(DataType_c
						.getManyS_DTsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(testPackage)));
		StructuredDataType_c sdt = sdts[sdts.length - 1];
		UITestingUtilities.addElementToGraphicalSelection(sdt);
		doNewCMETest("Member", CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testLiteralSymbolicConstantWithSpaces() {
		createElement("Newconstantspecification", testPackage);
		ConstantSpecification_c[] cs = ConstantSpecification_c
				.getManyCNST_CSPsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPackage));
		ConstantSpecification_c c = cs[cs.length - 1];
		UITestingUtilities.addElementToGraphicalSelection(c);
		doNewCMETest("Constant", CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testInterfaceOperationWithSpaces() {
		createElement("Newinterface", testPackage);
		Interface_c[] ifaces = Interface_c
				.getManyC_IsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPackage));
		Interface_c iface = ifaces[ifaces.length - 1];
		UITestingUtilities.addElementToGraphicalSelection(iface);
		doNewCMETest("Operation", CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testMessageArgumentWithSpaces() {
		createElementInEditor("Interaction::Synchronous Message", editor);
		Message_c[] msgs = Message_c.getManyMSG_MsOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(testPackage));
		Message_c msg = msgs[msgs.length - 1];
		SynchronousMessage_c sync = SynchronousMessage_c
				.getOneMSG_SMOnR1018(msg);
		UITestingUtilities.addElementToGraphicalSelection(sync);
		doNewCMETest("Argument", CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testInterfaceSignalWithSpaces() {
		createElement("Newinterface", testPackage);
		Interface_c[] ifaces = Interface_c
				.getManyC_IsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPackage));
		Interface_c iface = ifaces[ifaces.length - 1];
		UITestingUtilities.addElementToGraphicalSelection(iface);
		doNewCMETest("Signal", CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testPackageWithInvalidCharacter() {
		if (Platform.getOS().contains("win")) {
			doNewCMETest("Package", INVALID_CHAR_ERROR, "*");
		}
		else {
			doNewCMETest("Package", INVALID_UNIX_CHAR_ERROR, "/");
		}
	}

	@Test
	public void testClassWithInvalidCharacter() {
		if (Platform.getOS().contains("win")) {
		    doNewCMETest("Classes::Class", INVALID_CHAR_ERROR, "*");
		}
		else {
			doNewCMETest("Classes::Class", INVALID_UNIX_CHAR_ERROR, "/");
		}
	}

	@Test
	public void testComponentWithInvalidCharacter() {
		if (Platform.getOS().contains("win")) {
		    doNewCMETest("Components::Component", INVALID_CHAR_ERROR, "*");
		}
		else {
			doNewCMETest("Components::Component", INVALID_UNIX_CHAR_ERROR, "/");
		}
	}

	@Test
	public void testInterfaceWithInvalidCharacter() {
		if (Platform.getOS().contains("win")) {
		    doNewCMETest("Components::Interface", INVALID_CHAR_ERROR, "*");
		}
		else {
			doNewCMETest("Components::Interface", INVALID_UNIX_CHAR_ERROR, "/");
		}
	}

	@Test
	public void testPackageGraphicalToolWithInvalidCharacter() {
		doGraphicalCreationTest("Package", INVALID_CHAR_ERROR, "*");
	}

	@Test
	public void testClassGraphicalToolWithInvalidCharacter() {
		doGraphicalCreationTest("Classes::Class", INVALID_CHAR_ERROR, "*");
	}

	@Test
	public void testComponentGraphicalToolWithInvalidCharacter() {
		doGraphicalCreationTest("Components::Component", INVALID_CHAR_ERROR,
				"*");
	}

	@Test
	public void testInterfaceGraphicalToolWithInvalidCharacter() {
		doGraphicalCreationTest("Components::Interface", INVALID_CHAR_ERROR,
				"*");
	}

	@Test
	public void testPackageRenameWithInvalidCharacter() {
		createElement("Newpackage", testPackage);
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(testPackage));
		Package_c pkg = pkgs[pkgs.length - 1];
		UITestingUtilities.addElementToGraphicalSelection(pkg);
		doRenameTest(INVALID_CHAR_ERROR, "*");
	}

	@Test
	public void testClassRenameWithInvalidCharacter() {
		createElement("Newclass", testPackage);
		ModelClass_c[] objs = ModelClass_c
				.getManyO_OBJsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPackage));
		ModelClass_c obj = objs[objs.length - 1];
		UITestingUtilities.addElementToGraphicalSelection(obj);
		doRenameTest(INVALID_CHAR_ERROR, "*");
	}

	@Test
	public void testComponentRenameWithInvalidCharacter() {
		createElement("Newcomponent", testPackage);
		Component_c[] cs = Component_c.getManyC_CsOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(testPackage));
		Component_c c = cs[cs.length - 1];
		UITestingUtilities.addElementToGraphicalSelection(c);
		doRenameTest(INVALID_CHAR_ERROR, "*");
	}

	@Test
	public void testInterfaceRenameWithInvalidCharacter() {
		createElement("Newinterface", testPackage);
		Interface_c[] is = Interface_c.getManyC_IsOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(testPackage));
		Interface_c i = is[is.length - 1];
		UITestingUtilities.addElementToGraphicalSelection(i);
		doRenameTest(INVALID_CHAR_ERROR, "*");
	}

	@Test
	public void testNewPackageCMENoName() {
		doNewCMETest("Package", Messages.resources_nameEmpty, "");
	}

	@Test
	public void testNewClassCMENoName() {
		doNewCMETest("Classes::Class", Messages.resources_nameEmpty, "");
	}

	@Test
	public void testNewComponentCMENoName() {
		doNewCMETest("Components::Component", Messages.resources_nameEmpty, "");
	}

	@Test
	public void testNewInterfaceCMENoName() {
		doNewCMETest("Components::Interface", Messages.resources_nameEmpty, "");
	}

	@Test
	public void testRenamePackageNoName() {
		createElement("Newpackage", testPackage);
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(testPackage));
		Package_c pkg = pkgs[pkgs.length - 1];
		UITestingUtilities.addElementToGraphicalSelection(pkg);
		doRenameTest(Messages.resources_nameEmpty, "");
	}

	@Test
	public void testRenameClassNoName() {
		createElement("Newclass", testPackage);
		ModelClass_c[] objs = ModelClass_c
				.getManyO_OBJsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPackage));
		ModelClass_c obj = objs[objs.length - 1];
		UITestingUtilities.addElementToGraphicalSelection(obj);
		doRenameTest(Messages.resources_nameEmpty, "");
	}

	@Test
	public void testRenameComponentNoName() {
		createElement("Newcomponent", testPackage);
		Component_c[] cs = Component_c.getManyC_CsOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(testPackage));
		Component_c c = cs[cs.length - 1];
		UITestingUtilities.addElementToGraphicalSelection(c);
		doRenameTest(Messages.resources_nameEmpty, "");
	}

	@Test
	public void testRenameInterfaceNoName() {
		createElement("Newinterface", testPackage);
		Interface_c[] is = Interface_c.getManyC_IsOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(testPackage));
		Interface_c i = is[is.length - 1];
		UITestingUtilities.addElementToGraphicalSelection(i);
		doRenameTest(Messages.resources_nameEmpty, "");
	}

	@Test
	public void testNewPackageEditorNoName() {
		doGraphicalCreationTest("Package", Messages.resources_nameEmpty, "");
	}

	@Test
	public void testNewClassEditorNoName() {
		doGraphicalCreationTest("Classes::Class", Messages.resources_nameEmpty,
				"");
	}

	@Test
	public void testNewComponentEditorNoName() {
		doGraphicalCreationTest("Components::Component",
				Messages.resources_nameEmpty, "");
	}

	@Test
	public void testNewInterfaceEditorNoName() {
		doGraphicalCreationTest("Components::Interface",
				Messages.resources_nameEmpty, "");
	}

	@Test
	public void testNewPackageCMEExistingFolder() throws CoreException {
		// create existing folder first
		ResourcesPlugin.getWorkspace().getRoot().getFolder(
				new Path(testPackage.getFile().getParent().getFullPath() + "/"
						+ getName())).create(true, true,
				new NullProgressMonitor());
		BaseTest.dispatchEvents(0);
		String expectedError = EXISTING_FOLDER_ERROR;
		if (!Platform.getOS().contains("win")) {
			expectedError = expectedError.replace("\r", "");
		}
		doNewCMETest("Package", expectedError, getName());
	}

	@Test
	public void testNewClassCMEExistingFolder() throws CoreException {
		// create existing folder first
		ResourcesPlugin.getWorkspace().getRoot().getFolder(
				new Path(testPackage.getFile().getParent().getFullPath() + "/"
						+ getName())).create(true, true,
				new NullProgressMonitor());
		BaseTest.dispatchEvents(0);
		String expectedError = EXISTING_FOLDER_ERROR;
		if (!Platform.getOS().contains("win")) {
			expectedError = expectedError.replace("\r", "");
		}
		doNewCMETest("Classes::Class", expectedError, getName());
	}

	@Test
	public void testNewComponentCMEExistingFolder() throws CoreException {
		// create existing folder first
		ResourcesPlugin.getWorkspace().getRoot().getFolder(
				new Path(testPackage.getFile().getParent().getFullPath() + "/"
						+ getName())).create(true, true,
				new NullProgressMonitor());
		BaseTest.dispatchEvents(0);
		String expectedError = EXISTING_FOLDER_ERROR;
		if (!Platform.getOS().contains("win")) {
			expectedError = expectedError.replace("\r", "");
		}
		doNewCMETest("Components::Component", expectedError, getName());
	}

	@Test
	public void testNewInterfaceCMEExistingFolder() throws CoreException {
		// create existing folder first
		ResourcesPlugin.getWorkspace().getRoot().getFolder(
				new Path(testPackage.getFile().getParent().getFullPath() + "/"
						+ getName())).create(true, true,
				new NullProgressMonitor());
		BaseTest.dispatchEvents(0);
		String expectedError = EXISTING_FOLDER_ERROR;
		if (!Platform.getOS().contains("win")) {
			expectedError = expectedError.replace("\r", "");
		}
		doNewCMETest("Components::Interface", expectedError, getName());
	}

	@Test
	public void testRenamePackageExistingFolder() throws CoreException {
		// create existing folder first
		ResourcesPlugin.getWorkspace().getRoot().getFolder(
				new Path(testPackage.getFile().getParent().getFullPath() + "/"
						+ getName())).create(true, true,
				new NullProgressMonitor());
		BaseTest.dispatchEvents(0);
		createElement("Newpackage", testPackage);
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(testPackage));
		Package_c pkg = pkgs[pkgs.length - 1];
		UITestingUtilities.addElementToGraphicalSelection(pkg);
		doRenameTest(EXISTING_FOLDER_ERROR, getName());
	}

	@Test
	public void testRenameClassExistingFolder() throws CoreException {
		// create existing folder first
		ResourcesPlugin.getWorkspace().getRoot().getFolder(
				new Path(testPackage.getFile().getParent().getFullPath() + "/"
						+ getName())).create(true, true,
				new NullProgressMonitor());
		BaseTest.dispatchEvents(0);
		createElement("Newclass", testPackage);
		ModelClass_c[] objs = ModelClass_c
				.getManyO_OBJsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPackage));
		ModelClass_c obj = objs[objs.length - 1];
		UITestingUtilities.addElementToGraphicalSelection(obj);
		doRenameTest(EXISTING_FOLDER_ERROR, getName());
	}

	@Test
	public void testRenameComponentExistingFolder() throws CoreException {
		// create existing folder first
		ResourcesPlugin.getWorkspace().getRoot().getFolder(
				new Path(testPackage.getFile().getParent().getFullPath() + "/"
						+ getName())).create(true, true,
				new NullProgressMonitor());
		BaseTest.dispatchEvents(0);
		createElement("Newcomponent", testPackage);
		Component_c[] cs = Component_c.getManyC_CsOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(testPackage));
		Component_c c = cs[cs.length - 1];
		UITestingUtilities.addElementToGraphicalSelection(c);
		doRenameTest(EXISTING_FOLDER_ERROR, getName());
	}

	@Test
	public void testRenameInterfaceExistingFolder() throws CoreException {
		// create existing folder first
		ResourcesPlugin.getWorkspace().getRoot().getFolder(
				new Path(testPackage.getFile().getParent().getFullPath() + "/"
						+ getName())).create(true, true,
				new NullProgressMonitor());
		BaseTest.dispatchEvents(0);
		createElement("Newinterface", testPackage);
		Interface_c[] is = Interface_c.getManyC_IsOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(testPackage));
		Interface_c i = is[is.length - 1];
		UITestingUtilities.addElementToGraphicalSelection(i);
		doRenameTest(EXISTING_FOLDER_ERROR, getName());
	}

	@Test
	public void testNewPackageEditorExistingFolder() throws CoreException {
		// create existing folder first
		ResourcesPlugin.getWorkspace().getRoot().getFolder(
				new Path(testPackage.getFile().getParent().getFullPath() + "/"
						+ getName())).create(true, true,
				new NullProgressMonitor());
		BaseTest.dispatchEvents(0);
		doGraphicalCreationTest("Package", EXISTING_FOLDER_ERROR, getName());
	}

	@Test
	public void testNewClassEditorExistingFolder() throws CoreException {
		// create existing folder first
		ResourcesPlugin.getWorkspace().getRoot().getFolder(
				new Path(testPackage.getFile().getParent().getFullPath() + "/"
						+ getName())).create(true, true,
				new NullProgressMonitor());
		BaseTest.dispatchEvents(0);
		doGraphicalCreationTest("Classes::Class", EXISTING_FOLDER_ERROR,
				getName());
	}

	@Test
	public void testNewComponentEditorExistingFolder() throws CoreException {
		// create existing folder first
		ResourcesPlugin.getWorkspace().getRoot().getFolder(
				new Path(testPackage.getFile().getParent().getFullPath() + "/"
						+ getName())).create(true, true,
				new NullProgressMonitor());
		BaseTest.dispatchEvents(0);
		doGraphicalCreationTest("Components::Component", EXISTING_FOLDER_ERROR,
				getName());
	}

	@Test
	public void testNewInterfaceEditorExistingFolder() throws CoreException {
		// create existing folder first
		ResourcesPlugin.getWorkspace().getRoot().getFolder(
				new Path(testPackage.getFile().getParent().getFullPath() + "/"
						+ getName())).create(true, true,
				new NullProgressMonitor());
		BaseTest.dispatchEvents(0);
		doGraphicalCreationTest("Components::Interface", EXISTING_FOLDER_ERROR,
				getName());
	}

	private void doRenameTest(String expectedResult, String name) {
		validateErrorMessage(expectedResult, "*");
		UITestingUtilities.activateMenuItem(editor.getCanvas().getMenu(),
				"Rename");
	}

	private void doGraphicalCreationTest(String toolPath,
			String expectedResult, String value) {
		validateErrorMessage(expectedResult, value);
		createElementInEditor(toolPath, editor);
	}

	private void createElementInEditor(String tool, GraphicalEditor editor) {
		Point clearPoint = UITestingUtilities.getClearPoint(editor);
		String[] toolParts = tool.split("::");
		String toolSet = toolParts[0];
		if (toolParts.length > 1) {
			String toolName = toolParts[1];
			UITestingUtilities.createConnectorInDiagram(editor, clearPoint,
					new Point(clearPoint.x + 100, clearPoint.y), toolSet,
					toolName);
		} else {
			UITestingUtilities.createConnectorInDiagram(editor, clearPoint,
					new Point(clearPoint.x + 100, clearPoint.y), toolSet);
		}
	}

	private void createElement(String creationMethod, Object container) {
		// new a test Model Class
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Create test element.",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			Method method = Cl_c.findMethod(container, creationMethod,
					new Class<?>[0]);
			Cl_c.doMethod(method, container, new Object[0]);
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (TransactionException e) {
			if (transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(
						transaction, e);
			}
		}
		// allow reconciler to kick in
		BaseTest.dispatchEvents(0);
	}

	private void doNewCMETest(String elementType, String expectedResult) {
		doNewCMETest(elementType, expectedResult, "empty");
	}

	private void doNewCMETest(String menuPath, String expectedResult,
			String value) {
		validateErrorMessage(expectedResult, value);
		UITestingUtilities.activateMenuItem(editor.getCanvas().getMenu(),
				"New::" + menuPath);
		assertEquals("Did not find expected error text in input dialog.",
				expectedResult, errorTxt);
	}

	static String errorTxt = "";

	private void validateErrorMessage(final String expectedResult,
			final String value) {
		PlatformUI.getWorkbench().getDisplay().timerExec(1000, new Runnable() {

			@Override
			public void run() {
				Shell activeShell = PlatformUI.getWorkbench().getDisplay()
						.getActiveShell();
				if (activeShell != null
						&& activeShell.getData() instanceof InputDialog) {
					if (!value.equals("empty")) {
						Text text = UITestingUtilities
								.findInputDialogTextField((InputDialog) activeShell
										.getData());
						text.setText(value);
						text.notifyListeners(SWT.Traverse, new Event());
					}
					errorTxt = UITestingUtilities
							.findInputDialogErrorText((InputDialog) activeShell
									.getData());
					((InputDialog) activeShell.getData()).close();
				} else {
					errorTxt = "";
				}
			}
		});
	}

}
