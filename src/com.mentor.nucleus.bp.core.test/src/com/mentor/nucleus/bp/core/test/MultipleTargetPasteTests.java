package com.mentor.nucleus.bp.core.test;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.actions.ActionFactory;

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public class MultipleTargetPasteTests extends BaseTest {

	@Override
	protected void initialSetup() throws Exception {
		// turn off auto build
		WorkspaceUtil.setAutobuilding(false);
	}

	public void testPasteIntoMultipleSelection() {
		// create test instances
		m_sys.Newpackage();
		Package_c[] packages = Package_c.getManyEP_PKGsOnR1405(m_sys);
		Package_c testPackage = packages[packages.length - 1];
		testPackage.setName("MutlipleTargetPasteTests");
		testPackage.Newclass();
		testPackage.Newclass();
		testPackage.Newclass();
		testPackage.Newclass();
		testPackage.Newcomponent();
		ModelClass_c[] testClasses = ModelClass_c
				.getManyO_OBJsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPackage));
		// add an attribute to the first test class
		testClasses[0].Newattribute();
		// copy the attribute
		Attribute_c attribute = Attribute_c.getOneO_ATTROnR102(testClasses[0]);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(attribute);
		UITestingUtilities.copyElementInExplorer(getExplorerView());
		Component_c testComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPackage));
		// add all test elements to the selection
		Selection.getInstance().clear();
		for (int i = 0; i < testClasses.length; i++) {
			Selection.getInstance().addToSelection(testClasses[i]);
		}
		// remove the source class as it is not intended to
		// be part of the test
		Selection.getInstance().removeFromSelection(testClasses[0]);
		Selection.getInstance().addToSelection(testComponent);
		// verify that paste is not available as a component
		// is not a valid target
		boolean isDisabled = UITestingUtilities.checkItemStatusInContextMenu(
				getExplorerView().getTreeViewer().getTree().getMenu(), "Paste",
				"", true);
		assertTrue(
				"Paste was enabled when an invalid target was in the selection.",
				isDisabled);
		// deselect the component
		Selection.getInstance().removeFromSelection(testComponent);
		// assert that Paste is now available
		isDisabled = UITestingUtilities.checkItemStatusInContextMenu(
				getExplorerView().getTreeViewer().getTree().getMenu(), "Paste",
				"", true);
		assertFalse("Paste was not enabled with valid targets selected.",
				isDisabled);
		UITestingUtilities.pasteClipboardContentsInExplorer();
		// assert that all three classes in the selection
		// have the new attribute
		Attribute_c[] attributes = Attribute_c
				.getManyO_ATTRsOnR102(testClasses);
		// count should be 4, the three pasted plus the original
		assertTrue("Paste was not done to all in the selection.",
				attributes.length == 4);
		// undo the paste
		TransactionManager.getSingleton().getUndoAction().run();
		BaseTest.dispatchEvents(0);
		// verify that the attributes pasted were removed
		attributes = Attribute_c.getManyO_ATTRsOnR102(testClasses);
		assertTrue("Undo did not remove pasted elements from all targets.",
				attributes.length == 1);
		// now try through the graphical editor
		CanvasUtilities.openCanvasEditor(testPackage);
		// select the diagram edit part (same as right clicking the background)
		GraphicalEditor editor = (GraphicalEditor) UITestingUtilities
				.getActiveEditor();
		UITestingUtilities.addElementToGraphicalSelection(editor
				.getGraphicalViewer().getContents());
		// paste should not be available
		isDisabled = UITestingUtilities.checkItemStatusInContextMenu(editor
				.getCanvas().getMenu(), "Paste", "", true);
		assertTrue("Paste was enabled for an invalid destination.", isDisabled);
		// remove the package from the selection
		UITestingUtilities.clearGraphicalSelection();
		// now select the three test classes in the editor
		Selection.getInstance().clear();
		for (int i = 1; i < testClasses.length; i++) {
			UITestingUtilities.addElementToGraphicalSelection(testClasses[i]);
		}
		// the paste menu should now be available
		isDisabled = UITestingUtilities.checkItemStatusInContextMenu(editor
				.getCanvas().getMenu(), "Paste", "", true);
		assertFalse(
				"Paste was not available while valid targets were selected.",
				isDisabled);
		// now execute the paste
		IAction globalActionHandler = editor.getEditorSite().getActionBars()
				.getGlobalActionHandler(ActionFactory.PASTE.getId());
		globalActionHandler.run();
		BaseTest.dispatchEvents(0);
		// make sure the attributes were pasted
		attributes = Attribute_c.getManyO_ATTRsOnR102(testClasses);
		assertTrue("Paste was not done to all in the graphical selection.",
				attributes.length == 4);
	}
}
