//========================================================================
//
//File:      ModelMergeTests.java
//
//Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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
package com.mentor.nucleus.bp.model.compare.test;

import java.io.InputStream;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ActionNode_c;
import com.mentor.nucleus.bp.core.ActivityDiagramAction_c;
import com.mentor.nucleus.bp.core.ActivityEdge_c;
import com.mentor.nucleus.bp.core.ActivityNode_c;
import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.ClassAsAssociatedOneSide_c;
import com.mentor.nucleus.bp.core.ClassAsAssociatedOtherSide_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.LinkedAssociation_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.NewStateTransition_c;
import com.mentor.nucleus.bp.core.NoEventTransition_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SemEvent_c;
import com.mentor.nucleus.bp.core.StateEventMatrixEntry_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.inspector.ObjectElement;
import com.mentor.nucleus.bp.model.compare.ComparableTreeObject;
import com.mentor.nucleus.bp.model.compare.ITreeDifferencerProvider;
import com.mentor.nucleus.bp.model.compare.TreeDifference;
import com.mentor.nucleus.bp.model.compare.TreeDifferencer;
import com.mentor.nucleus.bp.model.compare.contentmergeviewer.ModelContentMergeViewer;
import com.mentor.nucleus.bp.model.compare.contentmergeviewer.SynchronizedTreeViewer;
import com.mentor.nucleus.bp.model.compare.contentmergeviewer.TextualAttributeCompareElementType;
import com.mentor.nucleus.bp.model.compare.providers.ModelCompareContentProvider;
import com.mentor.nucleus.bp.model.compare.providers.ObjectElementComparable;
import com.mentor.nucleus.bp.model.compare.providers.custom.AssociationSubtypeComparable;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.CompareTestUtilities;
import com.mentor.nucleus.bp.test.common.GitUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.ZipUtil;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;

public class ModelMergeTests extends BaseTest {

	private String test_repositories = Platform.getInstanceLocation().getURL()
			.getFile()
			+ "/" + "test_repositories";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mentor.nucleus.bp.test.common.BaseTest#initialSetup()
	 */
	@Override
	protected void initialSetup() throws Exception {
		CorePlugin
				.getDefault()
				.getPreferenceStore()
				.setValue(
						BridgePointPreferencesStore.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE,
						false);
		String test_repository_location = System
				.getenv("XTUML_TEST_MODEL_REPOSITORY");
		if (test_repository_location == null
				|| test_repository_location.equals("")) {
			// use the default location
			test_repository_location = BaseTest.DEFAULT_XTUML_TEST_MODEL_REPOSITORY;
		}
		test_repository_location = new Path(test_repository_location)
				.removeLastSegments(1).toString();
		ZipUtil.unzipFileContents(
				test_repository_location + "/"
						+ "test_repositories"
						+ "/" + "204.zip",
				test_repositories);
		ZipUtil.unzipFileContents(
				test_repository_location + "/"
						+ "test_repositories"
						+ "/" + "dts0101054289.zip",
				test_repositories);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		loadProject("HierarchyTestModel");
		project = getProjectHandle("HierarchyTestModel");
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.closeAllEditors(false);
	}

	public void testMergeOfTransitionNoOrphanedTransitions() throws Exception {
		String projectName = "dts0101042909";
		// import git repository from models repo
		GitUtil.loadRepository(test_repositories
				+ "/" + projectName);
		// import test project
		GitUtil.loadProject(projectName, projectName);
		// merge the test branch
		GitUtil.mergeBranch("slave", projectName);
		// start the merge tool
		GitUtil.startMergeTool(projectName);
		// perform test
		CompareTestUtilities.copyAllNonConflictingChangesFromRightToLeft();
		CompareTestUtilities.copyConflictingChangesFromRightToLeft();
		// validate
		assertTrue("Found conflicting changes remaining.", CompareTestUtilities
				.getConflictingChanges().size() == 0);
		assertTrue("Found incoming changes remaining.", CompareTestUtilities
				.getIncomingChanges().size() == 0);
		// save and close
		CompareTestUtilities.flushMergeEditor();
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.closeAllEditors(false);
		// validate
		// Look for orphaned transitions
		Ooaofooa[] roots = Ooaofooa.getInstancesUnderSystem(projectName);
		for (Ooaofooa root : roots) {
			Transition_c[] transitions = Transition_c.TransitionInstances(root);
			for (Transition_c transition : transitions) {
				// make sure there is a state and event associated
				// unless it is a no event transition
				NoEventTransition_c net = NoEventTransition_c
						.getOneSM_NETXNOnR507(transition);
				if (net == null) {
					NewStateTransition_c nst = NewStateTransition_c
							.getOneSM_NSTXNOnR507(transition);
					if (nst != null) {
						StateMachineState_c state = StateMachineState_c
								.getOneSM_STATEOnR503(StateEventMatrixEntry_c
										.getOneSM_SEMEOnR504(nst));
						assertNotNull(
								"Transition after merge did not have a destination state.",
								state);
						StateMachineEvent_c event = StateMachineEvent_c
								.getOneSM_EVTOnR525(SemEvent_c
										.getOneSM_SEVTOnR503(StateEventMatrixEntry_c
												.getOneSM_SEMEOnR504(nst)));
						assertNotNull(
								"Transition after merge did not have an event assigned.",
								event);
					}
				}
			}
		}
		// delete test project if no failures/errors
		// and reset the repository
		TestUtil.deleteProject(getProjectHandle(projectName));
	}

	public void testNoGraphicalElementCopiedWithoutSemanticCopy()
			throws Exception {
		String projectName = "dts0101042915";
		// import git repository from models repo
		GitUtil.loadRepository(test_repositories
				+ "/" + projectName);
		// import test project
		GitUtil.loadProject(projectName, projectName);
		// merge the test branch
		GitUtil.mergeBranch("slave", projectName);
		// start the merge tool
		GitUtil.startMergeTool(projectName);
		// perform test
		CompareTestUtilities.copyAllNonConflictingChangesFromRightToLeft();
		// validate
		assertTrue("Found conflicting changes remaining.", CompareTestUtilities
				.getConflictingChanges().size() == 1);
		assertTrue("Found incoming changes remaining.", CompareTestUtilities
				.getIncomingChanges().size() == 0);
		// save and close
		CompareTestUtilities.flushMergeEditor();
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.closeAllEditors(false);
		// validate
		BaseTest.dispatchEvents(0);
		// Look for orphaned graphics
		Ooaofooa[] roots = Ooaofooa.getInstancesUnderSystem(projectName);
		for (Ooaofooa root : roots) {
			Ooaofgraphics graphicsRoot = Ooaofgraphics
					.getInstance(root.getId());
			GraphicalElement_c[] elements = GraphicalElement_c
					.GraphicalElementInstances(graphicsRoot);
			for (GraphicalElement_c element : elements) {
				// make sure no graphical elements exist that have no
				// represented semantic
				assertTrue(
						"Found an orphaned graphical element after merging.",
						element.getRepresents() != null);
			}
		}
		// delete test project if no failures/errors
		// and reset the repository
		TestUtil.deleteProject(getProjectHandle(projectName));
	}

	public void testGraphicalElementDifferencesOnlyCausesDirtyEditor() {
		String projectName = "dts0101054289";
		// import git repository from models repo
		GitUtil.loadRepository(test_repositories
				+ "/" + projectName);
		// import test project
		GitUtil.loadProject(projectName, projectName);
		// merge the test branch
		GitUtil.mergeBranch("slave", projectName);
		// start the merge tool
		GitUtil.startMergeTool(projectName);
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		// check that the merge tool is dirty
		ModelContentMergeViewer viewer = ModelContentMergeViewer
				.getInstance(null);
		assertTrue("Graphical changes only did not dirty the editor on open.",
				viewer.internalIsLeftDirty());
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.closeAllEditors(false);
		TestUtil.deleteProject(getProjectHandle(projectName));
	}
	
	public void testMergeWithStateMachineAddedInSeparateBranches()
			throws Exception {
		String projectName = "dts0101009925";
		// import git repository from models repo
		GitUtil.loadRepository(test_repositories
				+ "/" + projectName);
		// import test project
		GitUtil.loadProject(projectName, projectName);
		// merge the test branch
		GitUtil.mergeBranch("slave", projectName);
		// start the merge tool
		GitUtil.startMergeTool(projectName);
		// perform test
		CompareTestUtilities.copyAllNonConflictingChangesFromRightToLeft();
		// test for github issue 244, undo then try the merge again
		CompareTestUtilities.undoMerge();
		// merge all changes again then validate the merge
		CompareTestUtilities.copyAllNonConflictingChangesFromRightToLeft();
		// (For github issue 224) Test that the slave event is before the
		// master event
		String[] orderedElements = new String[] { "SlaveStateA", "SlaveStateB", "MasterStateB",
				"MasterStateA", "KEY_A1: slave", "KEY_A1: master" };
		SystemModel_c system = getSystemModel(projectName);
		Package_c pkg = Package_c.getOneEP_PKGOnR1401(system);
		assertNotNull(pkg);
		ClassStateMachine_c[] csms = ClassStateMachine_c
				.getManySM_ASMsOnR519(ModelClass_c
						.getManyO_OBJsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(pkg)));
		// validate
		assertTrue("Found conflicting changes remaining.", CompareTestUtilities
				.getConflictingChanges().size() == 0);
		assertTrue("Found incoming changes remaining.", CompareTestUtilities
				.getIncomingChanges().size() == 0);
		// save and close
		CompareTestUtilities.flushMergeEditor(false);
		ModelMergeTests2.verifyOrder(orderedElements, csms[0]);
		// switch to next editor and copy all changes
		GitUtil.switchToFile("InstanceStateMachine.xtuml");
		CompareTestUtilities.copyAllNonConflictingChangesFromRightToLeft();
		// validate
		assertTrue("Found conflicting changes remaining.", CompareTestUtilities
				.getConflictingChanges().size() == 0);
		assertTrue("Found incoming changes remaining.", CompareTestUtilities
				.getIncomingChanges().size() == 0);
		// save and close
		CompareTestUtilities.flushMergeEditor();
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.closeAllEditors(false);
		// validate
		BaseTest.dispatchEvents(0);
		// just need to account for 8 states and 4 transitions
		// and as an extra check make sure there are only one ISM
		// and ASM in the test package
		InstanceStateMachine_c[] isms = InstanceStateMachine_c
				.getManySM_ISMsOnR518(ModelClass_c
						.getManyO_OBJsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(pkg)));
		csms = ClassStateMachine_c
				.getManySM_ASMsOnR519(ModelClass_c
						.getManyO_OBJsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(pkg)));
		assertTrue(
				"Test data is not valid, should only be one instance state machine.",
				isms.length == 1);
		assertTrue(
				"Test data is not valid, should only be one class state machine.",
				csms.length == 1);
		StateMachineState_c[] states = StateMachineState_c
				.getManySM_STATEsOnR501(StateMachine_c
						.getManySM_SMsOnR517(isms));
		assertTrue(
				"Did not find a valid number of states in the instance state machine.",
				states.length == 4);
		states = StateMachineState_c.getManySM_STATEsOnR501(StateMachine_c
				.getManySM_SMsOnR517(csms));
		assertTrue(
				"Did not find a valid number of states in the class state machine.",
				states.length == 4);
		Transition_c[] transitions = Transition_c
				.getManySM_TXNsOnR505(StateMachine_c.getManySM_SMsOnR517(isms));
		assertTrue(
				"Did not find a valid number of transitions in the instance state machine.",
				transitions.length == 4);
		transitions = Transition_c.getManySM_TXNsOnR505(StateMachine_c
				.getManySM_SMsOnR517(csms));
		assertTrue(
				"Did not find a valid number of transitions in the class state machine.",
				transitions.length == 4);
		// delete test project if no failures/errors
		// and reset the repository
		TestUtil.deleteProject(getProjectHandle(projectName));
	}

	public void testNoGraphicalDataInCompareEditor() throws CoreException {
		TestingUtilities.createProject("testNoGraphics");
		m_sys = getSystemModel("testNoGraphics");
		TestUtil.executeInTransaction(m_sys, "Newpackage", new Object[0]);
		Package_c testPackage = Package_c.getOneEP_PKGOnR1401(m_sys);
		TestUtil.executeInTransaction(testPackage, "setName",
				new Object[] { "testNoGraphics" });
		modelRoot = (Ooaofooa) testPackage.getModelRoot();
		TestUtil.executeInTransaction(testPackage, "Newexternalentity",
				new Object[0]);
		BaseTest.dispatchEvents(0);
		final ExternalEntity_c ee = ExternalEntity_c
				.ExternalEntityInstance(modelRoot);
		GraphicalElement_c gElem = GraphicalElement_c.getOneGD_GEOnR1(Model_c
				.ModelInstances(Ooaofgraphics.getInstance(modelRoot.getId())),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((GraphicalElement_c) candidate).getRepresents() == ee;
					}
				});
		CanvasTestUtils.openCanvasEditor(testPackage);
		Point shapeCenter = CanvasTestUtils.getShapeCenter(Shape_c
				.getOneGD_SHPOnR2(gElem));
		CanvasTestUtils.doMousePress(shapeCenter.x, shapeCenter.y);
		CanvasTestUtils.doMouseMove(shapeCenter.x + 100, shapeCenter.y + 100);
		CanvasTestUtils
				.doMouseRelease(shapeCenter.x + 100, shapeCenter.y + 100);
		IFile file = ee.getFile();
		IFileState[] history = file.getHistory(new NullProgressMonitor());
		IFileState state = history[0];
		InputStream contents = state.getContents();
		IFile copy = file.getProject().getFile(file.getName());
		copy.create(contents, true, new NullProgressMonitor());
		CompareTestUtilities.compareElementWithLocalHistory(file, copy);
		ModelContentMergeViewer viewer = ModelContentMergeViewer
				.getInstance(null);
		List<TreeDifference> leftDifferences = viewer.getDifferencer()
				.getLeftDifferences();
		for (TreeDifference difference : leftDifferences) {
			if (!SynchronizedTreeViewer.differenceIsGraphical(difference)) {
				fail("Difference found in editor when only graphical differences should be found.");
			}
		}
	}

	public void testAutomaticGraphicalMergeElementDeletion() throws Exception {
		String projectName = "AutomaticGraphicalMerge";
		// import git repository from models repo
		GitUtil.loadRepository(test_repositories
				+ "/" + projectName);
		// import test project
		GitUtil.loadProject(projectName, projectName);
		// merge the test branch
		GitUtil.mergeBranch("slave", projectName);
		// start the merge tool
		GitUtil.startMergeTool(projectName);
		// copy name change
		m_sys = getSystemModel(projectName);
		Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys);
		ActivityDiagramAction_c an = ActivityDiagramAction_c.getOneA_GAOnR1107(
				ActionNode_c.getManyA_ACTsOnR1105(ActivityNode_c
						.getManyA_NsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(pkg))),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((ActivityDiagramAction_c) candidate).getName()
								.equals("ActionOneRenamed");
					}
				});
		CompareTestUtilities.selectElementInTree(false, an);
		CompareTestUtilities.mergeSelection();
		CompareTestUtilities.flushMergeEditor();
		// make sure that the connection was not removed
		// as the change to remove the edge was not merged
		m_sys = getSystemModel(projectName);
		pkg = Package_c.getOneEP_PKGOnR1401(m_sys);
		an = ActivityDiagramAction_c.getOneA_GAOnR1107(ActionNode_c
				.getManyA_ACTsOnR1105(ActivityNode_c
						.getManyA_NsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(pkg))),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((ActivityDiagramAction_c) candidate).getName()
								.equals("ActionOne");
					}
				});
		assertNotNull(an);
		ActivityEdge_c edge = ActivityEdge_c.getOneA_EOnR1104(ActivityNode_c
				.getOneA_NOnR1105(ActionNode_c.getOneA_ACTOnR1107(an)));
		assertNotNull(
				"The edge was removed during merge, the test data is not valid.",
				edge);
		Ooaofgraphics graphicsRoot = Ooaofgraphics.getInstance(an
				.getModelRoot().getId());
		Connector_c connector = Connector_c.ConnectorInstance(graphicsRoot);
		assertNotNull(
				"The graphical connector was removed even though the semantic elements was not.",
				connector);
		GitUtil.startMergeTool(projectName);
		// now copy the semantic removal
		CompareTestUtilities.selectElementInTree(true, edge);
		CompareTestUtilities.mergeSelection();
		CompareTestUtilities.flushMergeEditor();
		m_sys = getSystemModel(projectName);
		pkg = Package_c.getOneEP_PKGOnR1401(m_sys);
		an = ActivityDiagramAction_c.getOneA_GAOnR1107(ActionNode_c
				.getManyA_ACTsOnR1105(ActivityNode_c
						.getManyA_NsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(pkg))),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((ActivityDiagramAction_c) candidate).getName()
								.equals("ActionOneRenamed");
					}
				});
		edge = ActivityEdge_c.getOneA_EOnR1104(ActivityNode_c
				.getOneA_NOnR1105(ActionNode_c.getOneA_ACTOnR1107(an)));
		assertNull(
				"The edge was not removed during merge, the test data is not valid.",
				edge);
		connector = Connector_c.ConnectorInstance(graphicsRoot);
		assertNull(
				"The graphical connector was not removed even though the semantic elements was.",
				connector);
		// delete test project if no failures/errors
		// and reset the repository
		TestUtil.deleteProject(getProjectHandle(projectName));
	}

	public void testAutomaticGraphicalMergeElementAdded() throws Exception {
		String projectName = "AutomaticGraphicalMergeAddition";
		// import git repository from models repo
		GitUtil.loadRepository(test_repositories
				+ "/" + projectName);
		// import test project
		GitUtil.loadProject(projectName, projectName);
		// merge the test branch
		GitUtil.mergeBranch("slave", projectName);
		// start the merge tool
		GitUtil.startMergeTool(projectName);
		// copy name change
		m_sys = getSystemModel(projectName);
		Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys);
		ActivityEdge_c edge = ActivityEdge_c.ActivityEdgeInstance(pkg
				.getModelRoot());
		CompareTestUtilities.selectElementInTree(true, edge);
		CompareTestUtilities.mergeSelection();
		CompareTestUtilities.flushMergeEditor();
		// make sure that the connection was not added
		// as the change to add the edge was not merged
		m_sys = getSystemModel(projectName);
		pkg = Package_c.getOneEP_PKGOnR1401(m_sys);
		edge = ActivityEdge_c.ActivityEdgeInstance(pkg.getModelRoot());
		assertNull(
				"The edge was added during merge, the test data is not valid.",
				edge);
		Ooaofgraphics graphicsRoot = Ooaofgraphics.getInstance(pkg
				.getModelRoot().getId());
		Connector_c connector = Connector_c.ConnectorInstance(graphicsRoot);
		assertNull(
				"The graphical connector was added even though the semantic elements was not.",
				connector);
		GitUtil.startMergeTool(projectName);
		// now copy the semantic addition
		ModelContentMergeViewer viewer = ModelContentMergeViewer
				.getInstance(null);
		edge = ActivityEdge_c
				.ActivityEdgeInstance(viewer.getRightCompareRoot());
		CompareTestUtilities.selectElementInTree(false, edge);
		CompareTestUtilities.mergeSelection();
		CompareTestUtilities.flushMergeEditor();
		m_sys = getSystemModel(projectName);
		pkg = Package_c.getOneEP_PKGOnR1401(m_sys);
		ActivityEdge_c[] edges = ActivityEdge_c.ActivityEdgeInstances(pkg
				.getModelRoot());
		assertTrue(
				"The edge was not added during merge, the test data is not valid.",
				edges.length == 2);
		Connector_c[] connectors = Connector_c.ConnectorInstances(graphicsRoot);
		assertTrue(
				"The graphical connector was not added even though the semantic elements was.",
				connectors.length == 2);
		// delete test project if no failures/errors
		// and reset the repository
		TestUtil.deleteProject(getProjectHandle(projectName));
	}

	public void testAddStatesAndTransitionsNoExceptions() throws Exception {
		String projectName = "dts0101009924";
		// import git repository from models repo
		GitUtil.loadRepository(test_repositories
				+ "/" + projectName);
		// import test project
		GitUtil.loadProject(projectName, projectName);
		// merge the test branch
		GitUtil.mergeBranch("slave", projectName);
		// start the merge tool
		GitUtil.startMergeTool(projectName);
		// just need to close now and make sure there
		// were no exceptions
		CompareTestUtilities.flushMergeEditor();
		// delete test project if no failures/errors
		// and reset the repository
		TestUtil.deleteProject(getProjectHandle(projectName));
	}

	public void testConnectorTextDoesNotDisappear() throws Exception {
		String projectName = "dts0101039702";
		// import git repository from models repo
		GitUtil.loadRepository(test_repositories
				+ "/" + projectName);
		// import test project
		GitUtil.loadProject(projectName, projectName);
		// switch to slave and make sure the provision and
		// requirement have a valid represents
		GitUtil.switchToBranch("slave", projectName);
		String modelRootId = "/" + projectName + "/" + Ooaofooa.MODELS_DIRNAME
				+ "/" + projectName + "/" + "Components" + "/" + "Components"
				+ "." + Ooaofooa.MODELS_EXT;
		Connector_c[] connectors = Connector_c.ConnectorInstances(Ooaofgraphics
				.getInstance(modelRootId));
		assertTrue("Could not locate connectors in the model.",
				connectors.length > 0);
		for (Connector_c connector : connectors) {
			GraphicalElement_c ge = GraphicalElement_c
					.getOneGD_GEOnR2(connector);
			NonRootModelElement nrme = (NonRootModelElement) ge.getRepresents();
			assertFalse("Found an orphaned connector represents value.",
					nrme.isOrphaned());
		}
		// switch to master and make sure the text is not
		// missing
		GitUtil.switchToBranch("master", projectName);
		connectors = Connector_c.ConnectorInstances(Ooaofgraphics
				.getInstance(modelRootId));
		assertTrue("Could not locate connectors in the model.",
				connectors.length > 0);
		for (Connector_c connector : connectors) {
			GraphicalElement_c ge = GraphicalElement_c
					.getOneGD_GEOnR2(connector);
			NonRootModelElement nrme = (NonRootModelElement) ge.getRepresents();
			assertFalse("Found an orphaned connector represents value.",
					nrme.isOrphaned());
		}
		// delete test project if no failures/errors
		// and reset the repository
		TestUtil.deleteProject(getProjectHandle(projectName));
	}

	public void testValueModificationOfDescriptionThroughCompareDialog()
			throws CoreException {
		modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(
				getProject(), "Classes", true));
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot);
		Transaction transaction = startTransaction();
		clazz.setDescrip("Test Description");
		endTransaction(transaction);
		CompareTestUtilities.openCompareEditor(clazz.getFile());
		ModelContentMergeViewer viewer = ModelContentMergeViewer
				.getInstance(null);
		SynchronizedTreeViewer leftViewer = viewer.getLeftViewer();
		ModelCompareContentProvider provider = new ModelCompareContentProvider();
		Object[] children = provider.getChildren(clazz);
		TreeItem item = SynchronizedTreeViewer.getMatchingItem(
				((ITreeDifferencerProvider) leftViewer.getContentProvider())
						.getComparableTreeObject(children[3]), leftViewer);
		clazz = ModelClass_c.ModelClassInstance(viewer.getLeftCompareRoot());
		ObjectElement objEle = (ObjectElement) ((ObjectElementComparable) item
				.getData()).getRealElement();
		TextualAttributeCompareElementType type = new TextualAttributeCompareElementType(
				objEle, leftViewer, true, null, null);
		type.setContent("".getBytes());
		assertTrue(
				"Textual compare dialog did not properly set the value for a class description.",
				clazz.getDescrip().equals(""));
	}

	public void testAssociationComparable() {
		modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(
				getProject(), "Classes", true));
		Association_c association = Association_c.AssociationInstance(
				modelRoot, new ClassQueryInterface_c() {
					@Override
					public boolean evaluate(Object candidate) {
						return ((Association_c) candidate).getNumb() == 4;
					}
				});
		association.Unformalize();
		ClassAsAssociatedOneSide_c oneSide = ClassAsAssociatedOneSide_c
				.getOneR_AONEOnR209(LinkedAssociation_c
						.getManyR_ASSOCsOnR206(association));
		ClassAsAssociatedOtherSide_c otherSide = ClassAsAssociatedOtherSide_c
				.getOneR_AOTHOnR210(LinkedAssociation_c
						.getManyR_ASSOCsOnR206(association));
		AssociationSubtypeComparable comparable1 = new AssociationSubtypeComparable(oneSide);
		AssociationSubtypeComparable comparable2 = new AssociationSubtypeComparable(otherSide);
		assertTrue(
				"A class as associated one side and a class as associated other side were considered identical.",
				!comparable1.equals(comparable2));
	}

	public void testLocationDetection() throws CoreException {
		modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(
				getProject(), "Classes", true));
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot,
				new ClassQueryInterface_c() {
					@Override
					public boolean evaluate(Object candidate) {
						return ((ModelClass_c) candidate).getName().equals(
								"Other");
					}
				});
		Transaction transaction = startTransaction();
		clazz.Newattribute();
		Attribute_c one = getLastCreatedAttribute(clazz);
		one.setRoot_nam("one");
		clazz.Newattribute();
		Attribute_c two = getLastCreatedAttribute(clazz);
		two.setRoot_nam("two");
		clazz.Newattribute();
		Attribute_c three = getLastCreatedAttribute(clazz);
		three.setRoot_nam("three");
		clazz.Newoperation();
		Operation_c oneOp = getLastCreatedOperation(clazz);
		oneOp.setName("one");
		clazz.Newoperation();
		Operation_c twoOp = getLastCreatedOperation(clazz);
		twoOp.setName("two");
		clazz.Newoperation();
		Operation_c threeOp = getLastCreatedOperation(clazz);
		threeOp.setName("three");
		endTransaction(transaction);
		transaction = startTransaction();
		two.Dispose();
		twoOp.Dispose();
		threeOp.Moveup();
		endTransaction(transaction);
		// verify all of the difference locations
		CompareTestUtilities.openCompareEditor(clazz.getFile(), 0);
		ModelContentMergeViewer viewer = ModelContentMergeViewer
				.getInstance(null);
		TreeDifferencer differencer = viewer.getDifferencer();
		List<TreeDifference> leftDifferences = differencer.getLeftDifferences();
		assertTrue(
				"Expected four differences and found " + leftDifferences.size(),
				leftDifferences.size() == 4);
		validateDifference(leftDifferences.get(0));
		validateDifference(leftDifferences.get(1));
		validateDifference(leftDifferences.get(2));
		validateDifference(leftDifferences.get(3));
		viewer.setCopySelection(false);
		viewer.copy(false);
		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
			;
		assertTrue("Not all differences were removed by the copy all button.",
				viewer.getDifferencer().getLeftDifferences().size() == 0);
	}

	public void testIntegerValueMerge() throws Exception {
		Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								"Classes");
					}
				});
		final Association_c assoc = Association_c
				.getOneR_RELOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(pkg));
		Transaction transaction = startTransaction();
		assoc.setNumb(22);
		endTransaction(transaction);
		CompareTestUtilities.openCompareEditor(pkg.getFile());
		ModelContentMergeViewer viewer = ModelContentMergeViewer
				.getInstance(null);
		TreeDifferencer differencer = viewer.getDifferencer();
		assertEquals("Incorrect number of differences found", differencer
				.getLeftDifferences().size(), 1);
		viewer.setCopySelection(false);
		viewer.copy(false);
		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
			;
		assertTrue("Not all differences were removed by the copy all button",
				viewer.getDifferencer().getLeftDifferences().size() == 0);
		Association_c compareAssoc = Association_c.AssociationInstance(
				viewer.getLeftCompareRoot(), new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Association_c) candidate).getRel_id().equals(
								assoc.getRel_id());
					}
				});
		assertTrue(
				"Association number was not updated by the copy difference button.",
				compareAssoc.getNumb() != 22);
	}

	public void testCantHappenCreationOnNewState() throws Exception {
		modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(
				getProject(), "Classes", true));
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((ModelClass_c) candidate).getName().equals(
								"ModelClass");
					}
				});
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR518(clazz);
		Transaction transaction = startTransaction();
		ism.Newstate();
		StateMachineState_c[] states = StateMachineState_c
				.getManySM_STATEsOnR501(StateMachine_c.getManySM_SMsOnR517(ism));
		StateMachineState_c state = states[states.length - 1];
		endTransaction(transaction);
		transaction = startTransaction();
		state.Dispose();
		endTransaction(transaction);
		CompareTestUtilities.openCompareEditor(ism.getFile());
		ModelContentMergeViewer viewer = ModelContentMergeViewer
				.getInstance(null);
		TreeDifferencer differencer = viewer.getDifferencer();
		assertTrue("No differences created for state addition.", differencer
				.getLeftDifferences().size() != 0);
		ism = InstanceStateMachine_c.InstanceStateMachineInstance(viewer
				.getLeftCompareRoot());
		StateMachineEvent_c[] events = StateMachineEvent_c
				.getManySM_EVTsOnR502(StateMachine_c.getManySM_SMsOnR517(ism));
		int existingSemeCount = 0;
		for (StateMachineEvent_c event : events) {
			StateEventMatrixEntry_c[] semes = StateEventMatrixEntry_c
					.getManySM_SEMEsOnR503(SemEvent_c
							.getManySM_SEVTsOnR525(event));
			existingSemeCount = existingSemeCount + semes.length;
		}
		viewer.setCopySelection(false);
		viewer.copy(false);
		int newSemeCount = 0;
		for (StateMachineEvent_c event : events) {
			StateEventMatrixEntry_c[] semes = StateEventMatrixEntry_c
					.getManySM_SEMEsOnR503(SemEvent_c
							.getManySM_SEVTsOnR525(event));
			newSemeCount = newSemeCount + semes.length;
		}
		assertTrue(
				"Event matrix entries were not added on merge of a new state.",
				newSemeCount > existingSemeCount);
	}

	public void testCantHappenCreationOnNewEvent() throws Exception {
		modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(
				getProject(), "Classes", true));
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((ModelClass_c) candidate).getName().equals(
								"ModelClass");
					}
				});
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR518(clazz);
		Transaction transaction = startTransaction();
		ism.Newevent();
		StateMachineEvent_c[] events = StateMachineEvent_c
				.getManySM_EVTsOnR502(StateMachine_c.getManySM_SMsOnR517(ism));
		StateMachineEvent_c event = events[events.length - 1];
		endTransaction(transaction);
		transaction = startTransaction();
		event.Dispose();
		endTransaction(transaction);
		CompareTestUtilities.openCompareEditor(ism.getFile());
		ModelContentMergeViewer viewer = ModelContentMergeViewer
				.getInstance(null);
		TreeDifferencer differencer = viewer.getDifferencer();
		assertTrue("No differences created for state addition.", differencer
				.getLeftDifferences().size() != 0);
		ism = InstanceStateMachine_c.InstanceStateMachineInstance(viewer
				.getLeftCompareRoot());
		StateMachineState_c[] states = StateMachineState_c
				.getManySM_STATEsOnR501(StateMachine_c.getManySM_SMsOnR517(ism));
		int existingSemeCount = 0;
		for (StateMachineState_c state : states) {
			StateEventMatrixEntry_c[] semes = StateEventMatrixEntry_c
					.getManySM_SEMEsOnR503(state);
			existingSemeCount = existingSemeCount + semes.length;
		}
		viewer.setCopySelection(false);
		viewer.copy(false);
		int newSemeCount = 0;
		for (StateMachineState_c state : states) {
			StateEventMatrixEntry_c[] semes = StateEventMatrixEntry_c
					.getManySM_SEMEsOnR503(state);
			newSemeCount = newSemeCount + semes.length;
		}
		assertTrue(
				"Event matrix entries were not added on merge of a new event.",
				newSemeCount > existingSemeCount);
	}

	public void testUnassignEventFromTransitionMerge() throws Exception {
		modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(
				getProject(), "Classes", true));
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((ModelClass_c) candidate).getName().equals(
								"ModelClass");
					}
				});
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR518(clazz);
		Transition_c transition = Transition_c.getOneSM_TXNOnR505(
				StateMachine_c.getOneSM_SMOnR517(ism),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						Transition_c transition = (Transition_c) candidate;
						StateEventMatrixEntry_c seme = StateEventMatrixEntry_c
								.getOneSM_SEMEOnR504(NewStateTransition_c
										.getOneSM_NSTXNOnR507(transition));
						return seme != null;
					}
				});
		Transaction transaction = startTransaction();
		transition.Removeevent();
		endTransaction(transaction);
		transaction = startTransaction();
		transition.Addevent(
				StateMachineEvent_c.getOneSM_EVTOnR502(
						StateMachine_c.getOneSM_SMOnR517(ism)).getSmevt_id(),
				ism.getSm_id());
		endTransaction(transaction);
		CompareTestUtilities.openCompareEditor(ism.getFile());
		ModelContentMergeViewer viewer = ModelContentMergeViewer
				.getInstance(null);
		TreeDifferencer differencer = viewer.getDifferencer();
		assertTrue("No differences created for event addition.", differencer
				.getLeftDifferences().size() != 0);
		viewer.setCopySelection(false);
		viewer.copy(false);
		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
			;
		assertTrue(
				"Differences were not removed on copy for unassigning an event from a transition",
				viewer.getDifferencer().getLeftDifferences().size() == 0);
	}

	public void testGitConflictAnnotationRemoval() throws Exception {
		String projectName = "GitAnnotationRemovalTest";
		// import git repository from models repo
		GitUtil.loadRepository(test_repositories
				+ "/" + projectName);
		// import test project
		GitUtil.loadProject(projectName, projectName);
		// merge the test branch
		GitUtil.mergeBranch("slave", projectName);
		// start the merge tool
		GitUtil.startMergeTool(projectName);
		// perform test
		GitUtil.switchToFile("ClassStateMachine.xtuml");
		CompareTestUtilities.copyAllNonConflictingChangesFromRightToLeft();
		CompareTestUtilities.flushMergeEditor(true);
		m_sys = getSystemModel(projectName);
		Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys);
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.InstanceStateMachineInstance(pkg.getModelRoot());
		ClassStateMachine_c csm = ClassStateMachine_c
				.ClassStateMachineInstance(pkg.getModelRoot());
		ModelClass_c clazz = ModelClass_c
				.ModelClassInstance(pkg.getModelRoot());
		String ismContents = TestUtil.getTextFileContents(ism.getFile()
				.getLocation().toFile());
		String csmContents = TestUtil.getTextFileContents(csm.getFile()
				.getLocation().toFile());
		String classContents = TestUtil.getTextFileContents(clazz.getFile()
				.getLocation().toFile());
		assertTrue(
				"Did not find the git annotation markers in an unviewed file.",
				ismContents.contains(">>>"));
		assertFalse("Found git annotation markers in a viewed and saved file.",
				csmContents.contains(">>>"));
		assertFalse("Found git annotation markers in a viewed file.",
				classContents.contains(">>>"));
		GitUtil.startMergeTool(projectName);
		GitUtil.switchToFile("InstanceStateMachine.xtuml");
		CompareTestUtilities.copyAllNonConflictingChangesFromRightToLeft();
		CompareTestUtilities.closeMergeEditor(false);
		ism = InstanceStateMachine_c.InstanceStateMachineInstance(pkg
				.getModelRoot());
		ismContents = TestUtil.getTextFileContents(ism.getFile().getLocation()
				.toFile());
		assertFalse("Found git annotation markers in an viewed file.",
				ismContents.contains(">>>"));
		StateMachineState_c[] states = StateMachineState_c
				.getManySM_STATEsOnR501(StateMachine_c.getOneSM_SMOnR517(ism));
		assertTrue(
				"Changes were merged even when the merge editor was not saved.",
				states.length == 2);
		// delete test project if no failures/errors
		// and reset the repository
		TestUtil.deleteProject(getProjectHandle(projectName));
	}
	
	public void testAutocrlfOption() throws Exception {

	}

	private void validateDifference(TreeDifference difference) {
		NonRootModelElement realElement = getRealElement((ComparableTreeObject) difference
				.getMatchingDifference().getElement());
		if (difference.getElement() == null
				&& realElement.getName().equals("two")) {
			if (realElement instanceof Attribute_c) {
				assertTrue(
						"Location was not correctly determined for attribute removal.",
						difference.getLocation() == 6);
			} else {
				assertTrue(
						"Location was not correctly deterined for operation removal",
						difference.getLocation() == 8);
			}
		}
	}

	private NonRootModelElement getRealElement(ComparableTreeObject element) {
		return (NonRootModelElement) ((ComparableTreeObject) element)
				.getRealElement();
	}

	private Operation_c getLastCreatedOperation(ModelClass_c clazz) {
		Operation_c[] ops = Operation_c.getManyO_TFRsOnR115(clazz);
		return ops[ops.length - 1];
	}

	private Attribute_c getLastCreatedAttribute(ModelClass_c clazz) {
		Attribute_c[] attrs = Attribute_c.getManyO_ATTRsOnR102(clazz);
		return attrs[attrs.length - 1];
	}
}
