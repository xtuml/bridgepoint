//========================================================================
//
//File:      $RCSfile: UndoRedoTestGenerics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 04:30:25 $
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

import java.awt.geom.Point2D;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.End_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.DeleteAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.OoaofooaUtil;
import com.mentor.nucleus.bp.test.common.CanvasEditorUtils;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.FloatingText_c;
import com.mentor.nucleus.bp.ui.canvas.Graphnode_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTestUtilities;
import com.mentor.nucleus.bp.ui.canvas.test.util.MouseUtil;
import com.mentor.nucleus.bp.ui.canvas.util.ConnectorUtil;
import com.mentor.nucleus.bp.ui.canvas.util.GraphNodeUtil;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

/**
 * Contains tests that exercise the functionality for the undo/redo of 
 * model changes.
 */
public class UndoRedoTestGenerics extends CanvasTest {
	/**
	 * Whether the first test of this class is the one that's currently 
	 * being run.
	 */
	private static boolean initialized = false;

	/**
	 * That used throughout these tests.
	 */
	private static Model_c model;

	/**
	 * That used throughout these tests.
	 */
	private static Selection selection = Selection.getInstance();

	/**
	 * That used throughout these tests.
	 */
	private static TransactionManager transactionManager;

	/**
	 * That used throughout these tests.
	 */
	private static Package_c subsystem;

	private static boolean generateResults = false;

	String test_id = null;

	public UndoRedoTestGenerics(String name) {
		super(null, name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		// if it's the first test of this class that's being setup
		if (!initialized) {
			// turn model persistence off for these tests, as it isn't necessary
			Ooaofooa.setPersistEnabled(false);
			CorePlugin.disableParseAllOnResourceChange();

			// setup the test project and import our test model from it
			//ensureAvailableAndLoaded("Models", "odms1", false, true);
			loadProject("odms");
			initialized = true;
			subsystem = Package_c.PackageInstance(modelRoot,
					new Package_by_name_c("Odms"));
			transactionManager = subsystem.getTransactionManager();

			// open the odms subsystem diagram
			GraphicalEditor editor = CanvasTestUtils
					.openPackageCanvasEditor(subsystem);
			model = editor.getModel();

		}
	}

	protected void tearDown() throws Exception {
		super.tearDown();

		// turn model persistence off for these tests
		// if a test that turned on persistence fails, make sure it's 
		// turned off
		Ooaofooa.setPersistEnabled(false);

	}

	/**
	 * Deletes in one action a pair of classes connected by an association, 
	 * as well as an association not connected to either class (to ensure
	 * that deletion of multiple model element types is being tested), 
	 * then undoes the deletion, checking that the elements are reinstated.  
	 * Then, this test redoes the deletion, checking that the elements
	 * are once again gone.  These steps test the code for reverting model 
	 * element creations and deletions (including deletions of multiple
	 * model element types), as well as for reverting relates/unrelates.
	 */
	public void testUndoRedoOfDeletionOfMultipleModelElementTypes() {
		// select the classes we are going to delete in the odms subsystem
		final String className1 = "Robot";
		ModelClass_c clazz1 = OoaofooaUtil.getClass(subsystem, className1);
		final String className2 = "Disk Transfer";
		ModelClass_c clazz2 = OoaofooaUtil.getClass(subsystem, className2);
		selection.clear();
		selection.addToSelection(clazz1);
		selection.addToSelection(clazz2);

		// find (but don't select) the R9 association, which connects the two 
		// classes found above
		Association_c r9 = OoaofooaUtil.getAssociation(modelRoot, 9);

		// find and select the R5 association, which is connected to neither 
		// of the above classes
		Association_c r5 = OoaofooaUtil.getAssociation(modelRoot, 5);
		selection.addToSelection(r5);

		// remember how many attributes the first class has, which we'll 
		// test against, below
		int numAttributes = Attribute_c.getManyO_ATTRsOnR102(clazz1).length;

		// delete the classes and associations
		new DeleteAction(null).run();

		// check that the classes are now deleted
		assertTrue("Class(es) not deleted", OoaofooaUtil.getClass(subsystem,
				className1) == null
				&& OoaofooaUtil.getClass(subsystem, className2) == null);

		// check that the R9 association was also deleted, since it was
		// connected to the deleted classes
		assertTrue("R9 not deleted",
				OoaofooaUtil.getAssociation(modelRoot, 9) == null);

		// check that the R5 association was deleted
		assertTrue("R5 not deleted",
				OoaofooaUtil.getAssociation(modelRoot, 5) == null);

		// undo the deletion 
		transactionManager.getUndoAction().run();

		// check that the classes are now part of the subsystem again
		assertTrue("Undo of deletion did not bring back class(es)",
				OoaofooaUtil.getClass(subsystem, className1).equals(clazz1)
						&& OoaofooaUtil.getClass(subsystem, className2).equals(
								clazz2));

		// check that the first class has the same number of attributes 
		// as it did before the deletion
		assertEquals("Number of attributes not the same as before", Attribute_c
				.getManyO_ATTRsOnR102(clazz1).length, numAttributes);

		// check that the R9 association is now part of the subsystem again,
		// and that it applies to the two classes
		assertTrue("Undo of deletion did not bring back R9", OoaofooaUtil
				.getAssociation(clazz1, 9).equals(r9)
				&& OoaofooaUtil.getAssociation(clazz2, 9).equals(r9));

		// check that the R9 association's connector texts are intact
		FloatingText_c startText = ConnectorUtil.getText(graphicsModelRoot, r9,
				End_c.Start), middleText = ConnectorUtil.getText(
				graphicsModelRoot, r9, End_c.Middle), endText = ConnectorUtil
				.getText(graphicsModelRoot, r9, End_c.End);
		assertTrue("R9 connector's texts not intact after undo of deletion",
				startText != null && middleText != null && endText != null
						&& startText != middleText && middleText != endText
						&& endText != startText);
		while (Display.getCurrent().readAndDispatch());
		// check that the R5 association is now part of the subsystem again
		assertTrue("Undo of deletion did not bring back R5", OoaofooaUtil
				.getAssociation(modelRoot, 5).equals(r5));

		// redo the deletion 
		transactionManager.getRedoAction().run();

		// check that the classes are now gone again
		assertTrue("Deletion redo did not delete class(es)", OoaofooaUtil
				.getClass(subsystem, className1) == null
				&& OoaofooaUtil.getClass(subsystem, className2) == null);

		// check that the R9 and R5 associations are now gone again
		assertTrue("Deletion redo did not delete association(s)", OoaofooaUtil
				.getAssociation(modelRoot, 9) == null
				&& OoaofooaUtil.getAssociation(modelRoot, 5) == null);
	}

	/**
	 * Moves a class a certain distance across the canvas, then undoes
	 * the movement, checking that the class is moved back to its 
	 * original position.  Then, this test redoes the movement, 
	 * checking that the class finishes at its moved position.  These
	 * steps test the code for reverting attribute-value changes 
	 * that occur to graphics instances.
	 */
	public void testUndoRedoOfClassMovement() {
		// record where the class we are going to move currently is 
		// on the canvas
		final String className = "Online Location";
		ModelClass_c clazz = OoaofooaUtil.getClass(subsystem, className);
		Graphnode_c node = GraphNodeUtil.getNode(graphicsModelRoot, clazz);
		Point2D.Float originalPosition = GraphNodeUtil.getPosition(node);

		UITestingUtilities.revealElementInGraphicalEditor(Shape_c
				.getOneGD_SHPOnR19(node));

		// drag the class a certain distance across the canvas
		Point2D.Float center = GraphNodeUtil.getCenter(node);
		MouseUtil.doPress(model, center);

		Point2D.Float moveTo = new Point2D.Float(center.x + 50, center.y + 50);
		MouseUtil.doMove(model, moveTo);
		MouseUtil.doRelease(model, moveTo);

		// record the class's new position on the canvas
		Point2D.Float movedPosition = GraphNodeUtil.getPosition(node);

		// check that an actual movement occurred, to help validate this test
		assertTrue("Movement had no effect", !movedPosition
				.equals(originalPosition));

		// undo the movement of the class
		transactionManager.getUndoAction().run();

		// check that the class is now back at its original position
		Point2D.Float undoPosition = GraphNodeUtil.getPosition(node);
		assertEquals("Undo did not move class back to its original position",
				undoPosition, originalPosition);

		// redo the movement of the class
		transactionManager.getRedoAction().run();

		// check that the class is now back at its moved position
		Point2D.Float redoPosition = GraphNodeUtil.getPosition(node);
		assertEquals("Redo did not move class back to its moved position",
				redoPosition, movedPosition);
	}

	/**
	 * Tests that the undo and redo stacks are cleared for a model-root
	 * whose persistence file has been changed outside of normal tool use.
	 */
	public void testClearingOfUndoRedoStacksOnFileChange() throws CoreException {
		// undo the movement of the class done during the last test,
		// to get both the undo and redo actions enabled
		transactionManager.getUndoAction().run();

		// check that both the undo and redo actions are enabled
		Action undoAction = transactionManager.getUndoAction();
		Action redoAction = transactionManager.getRedoAction();
		assertTrue("Undo and redo actions aren't both enabled", undoAction
				.isEnabled()
				&& redoAction.isEnabled());

		// touch the model's persistence file, to get Eclipse to think it's
		// changed somehow
		((IFile) modelRoot.getPersistenceFile()).touch(null);

		// check that both the undo and redo actions are disabled
		assertTrue("Undo and redo actions aren't both disabled", !undoAction
				.isEnabled()
				&& !redoAction.isEnabled());
	}
	/**
	 * Test that reverts are persisted
	 */
	public void testRevertPersisted() throws CoreException {
		// persistence is needed for this test
		Ooaofooa.setPersistEnabled(true);

		AbstractTool tool = UITestingUtilities.getTool("Classes", "Class");

		UITestingUtilities.activateTool(tool);

		CanvasTestUtilities.doMouseMove(100, 100);
		CanvasTestUtilities.doMousePress(100, 100);
		CanvasTestUtilities.doMouseMove(200, 200);
		CanvasTestUtilities.doMouseRelease(200, 200);

		ModelClass_c modelClass = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Unnamed Class"));
		assertNotNull(modelClass);

		IFile classFile = modelClass.getFile();
		// make sure the class was persisted
		TigerNatureTestGenerics tnt = new TigerNatureTestGenerics("tmp");
		try {
			assertTrue("create was not persisted.", tnt.checkIfPersisted(
					project, modelClass, tnt.getClassString(modelClass)));
		} catch (IOException e) {
			fail("Unable to open class file to check persistence");
		}

		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(modelClass);

		DeleteAction da = (DeleteAction) CorePlugin.getDeleteAction();

		da.run();

		// make sure the class deletion was persisted

		assertFalse("Delete was not persisted.", classFile.exists());

		// undo the class deletion
		transactionManager.getUndoAction().run();

		try {
			assertTrue("Undo revert was not persisted.", tnt.checkIfPersisted(
					project, modelClass, tnt.getClassString(modelClass)));
		} catch (IOException e) {
			fail("Unable to open class file to check persistence "
					+ e.getMessage());
		}

		// redo the class deletion
		transactionManager.getRedoAction().run();

		assertFalse("Delete was not persisted.", classFile.exists());

		// Persistence isn't needed for other tests
		Ooaofooa.setPersistEnabled(false);

	}
	/** Issue 2443 test
	 * Delete Subsystem, then undo the deletion, checking that the elements are 
	 * reinstated. Then, this test redo the deletion, checking that the elements
	 * are once again gone.  These steps test the code for reverting model 
	 * element creation and deletion (including deletion of graphical elements)
	 */
	public void testUndoDeletionOfSubSystem() {
		test_id = "1";
		// select the Subsystem we are going to delete in the odms1 Domain
		Package_c dom = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("odms"));
		Package_c ss = Package_c.PackageInstance(dom.getModelRoot(),
				new Package_by_name_c("Odms"));
		GraphicalEditor baseEditor = CanvasEditorUtils
				.openEditorWithShapeOf(ss);

		resultNamePostFix = "before_delete-1";
		validateOrGenerateResults(baseEditor, generateResults);

		selection.clear();
		selection.addToSelection(ss);

		// delete the SubSystem
		CorePlugin.getDeleteAction().run();

		// check that the SubSystem is now deleted
		assertTrue("Subsystem not deleted", Package_c.PackageInstance(dom
				.getModelRoot(), new Package_by_name_c("Odms")) == null);
		resultNamePostFix = "after_delete";
		validateOrGenerateResults(baseEditor, generateResults);

		// undo the deletion 
		transactionManager.getUndoAction().run();

		// check that the SubSystemis is restored
		assertTrue("Undo of deletion did not bring back subsystem", Package_c
				.PackageInstance(dom.getModelRoot(),
						new Package_by_name_c("Odms")).equals(ss));

		resultNamePostFix = "before_delete-2";
		validateOrGenerateResults(baseEditor, generateResults);

		//Redo Subsystem delete

		transactionManager.getRedoAction().run();

		assertTrue("Subsystem not deleted", Package_c.PackageInstance(dom
				.getModelRoot(), new Package_by_name_c("Odms")) == null);
		resultNamePostFix = "after_delete2";
		validateOrGenerateResults(baseEditor, generateResults);
		resultNamePostFix = "";
	}

	protected String getResultName() {
		return "UndoRedo" + "_" + test_id;
	}

}
