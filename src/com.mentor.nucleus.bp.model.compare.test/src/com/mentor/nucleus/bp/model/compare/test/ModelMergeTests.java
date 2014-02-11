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

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.ClassAsAssociatedOneSide_c;
import com.mentor.nucleus.bp.core.ClassAsAssociatedOtherSide_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.LinkedAssociation_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.NewStateTransition_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SemEvent_c;
import com.mentor.nucleus.bp.core.StateEventMatrixEntry_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.Transition_c;
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
import com.mentor.nucleus.bp.model.compare.providers.custom.AssociationComparable;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CompareTestUtilities;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class ModelMergeTests extends BaseTest {

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
	
	public void testValueModificationOfDescriptionThroughCompareDialog() throws CoreException {
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
		TreeItem item = leftViewer.getMatchingItem(
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
		AssociationComparable comparable1 = new AssociationComparable(oneSide);
		AssociationComparable comparable2 = new AssociationComparable(otherSide);
		assertTrue(
				"A class as associated one side and a class as associated other side were considered identical.",
				!comparable1.equals(comparable2));
	}
	
	public void testLocationDetection() throws CoreException {
		modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(
				getProject(), "Classes", true));
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot, new ClassQueryInterface_c() {
			@Override
			public boolean evaluate(Object candidate) {
				return ((ModelClass_c) candidate).getName().equals("Other");
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
		ModelContentMergeViewer viewer = ModelContentMergeViewer.getInstance(null);
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
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
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
	
	public void testFloatValueMerge() throws Exception {
		final Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								"Classes");
					}
				});
		Model_c model = Model_c.ModelInstance(Ooaofgraphics.getInstance(pkg.getModelRoot().getId()), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Model_c) candidate).getOoa_id().equals(pkg.getPackage_id());
			}
		});
		final Graphelement_c elem = Graphelement_c.getOneDIM_GEOnR23(GraphicalElement_c.getManyGD_GEsOnR1(model));
		Transaction transaction = startTransaction();
		elem.setPositionx(100f);
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
		Graphelement_c compareGraph = Graphelement_c.GraphelementInstance(
				Ooaofgraphics.getInstance(viewer.getLeftCompareRoot().getId()),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Graphelement_c) candidate).getElementid()
								.equals(elem.getElementid());
					}
				});
		assertTrue(
				"Float value was not updated by the copy difference button.",
				compareGraph.getPositionx() != 100f);
	}
	
	public void testCantHappenCreationOnNewState() throws Exception {
		modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(
				getProject(), "Classes", true));
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((ModelClass_c) candidate).getName().equals("ModelClass");
			}
		});
		InstanceStateMachine_c ism = InstanceStateMachine_c.getOneSM_ISMOnR518(clazz);
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
		ModelContentMergeViewer viewer = ModelContentMergeViewer.getInstance(null);
		TreeDifferencer differencer = viewer.getDifferencer();
		assertTrue("No differences created for state addition.", differencer
				.getLeftDifferences().size() != 0);
		ism = InstanceStateMachine_c.InstanceStateMachineInstance(viewer
				.getLeftCompareRoot());
		StateMachineEvent_c[] events = StateMachineEvent_c
				.getManySM_EVTsOnR502(StateMachine_c.getManySM_SMsOnR517(ism));
		int existingSemeCount = 0;
		for(StateMachineEvent_c event : events) {
			StateEventMatrixEntry_c[] semes = StateEventMatrixEntry_c
					.getManySM_SEMEsOnR503(SemEvent_c
							.getManySM_SEVTsOnR525(event));
			existingSemeCount = existingSemeCount + semes.length;
		}
		viewer.setCopySelection(false);
		viewer.copy(false);
		int newSemeCount = 0;
		for(StateMachineEvent_c event : events) {
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
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((ModelClass_c) candidate).getName().equals("ModelClass");
			}
		});
		InstanceStateMachine_c ism = InstanceStateMachine_c.getOneSM_ISMOnR518(clazz);
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
		ModelContentMergeViewer viewer = ModelContentMergeViewer.getInstance(null);
		TreeDifferencer differencer = viewer.getDifferencer();
		assertTrue("No differences created for state addition.", differencer
				.getLeftDifferences().size() != 0);
		ism = InstanceStateMachine_c.InstanceStateMachineInstance(viewer
				.getLeftCompareRoot());
		StateMachineState_c[] states = StateMachineState_c
				.getManySM_STATEsOnR501(StateMachine_c.getManySM_SMsOnR517(ism));
		int existingSemeCount = 0;
		for(StateMachineState_c state : states) {
			StateEventMatrixEntry_c[] semes = StateEventMatrixEntry_c
					.getManySM_SEMEsOnR503(state);
			existingSemeCount = existingSemeCount + semes.length;
		}
		viewer.setCopySelection(false);
		viewer.copy(false);
		int newSemeCount = 0;
		for(StateMachineState_c state : states) {
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
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		assertTrue(
				"Differences were not removed on copy for unassigning an event from a transition",
				viewer.getDifferencer().getLeftDifferences().size() == 0);
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
		return (NonRootModelElement) ((ComparableTreeObject) element).getRealElement();
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
