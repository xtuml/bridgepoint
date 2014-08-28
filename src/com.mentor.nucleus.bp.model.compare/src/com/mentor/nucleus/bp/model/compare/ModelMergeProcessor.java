//========================================================================
//
//File:      $RCSfile: ModelMergeProcessor.java,v $
//Version:   $Revision: 1.5.14.5 $
//Modified:  $Date: 2013/07/23 15:06:35 $
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
//
package com.mentor.nucleus.bp.model.compare;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.CantHappen_c;
import com.mentor.nucleus.bp.core.ClassAsAssociatedOneSide_c;
import com.mentor.nucleus.bp.core.ClassAsSimpleParticipant_c;
import com.mentor.nucleus.bp.core.ClassAsSubtype_c;
import com.mentor.nucleus.bp.core.ClassInAssociation_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.CreationTransition_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.Elementtypeconstants_c;
import com.mentor.nucleus.bp.core.EventIgnored_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.LinkedAssociation_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.NewStateTransition_c;
import com.mentor.nucleus.bp.core.NoEventTransition_c;
import com.mentor.nucleus.bp.core.NonLocalEvent_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.ReferringClassInAssoc_c;
import com.mentor.nucleus.bp.core.SemEvent_c;
import com.mentor.nucleus.bp.core.SignalEvent_c;
import com.mentor.nucleus.bp.core.SimpleAssociation_c;
import com.mentor.nucleus.bp.core.StateEventMatrixEntry_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.SubtypeSupertypeAssociation_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.BaseModelDelta;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IPersistableElementParentDetails;
import com.mentor.nucleus.bp.core.common.InstanceList;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.ModelStreamProcessor;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.inspector.IModelClassInspector;
import com.mentor.nucleus.bp.core.inspector.ModelInspector;
import com.mentor.nucleus.bp.core.inspector.ObjectElement;
import com.mentor.nucleus.bp.core.sorter.MetadataSortingManager;
import com.mentor.nucleus.bp.core.ui.IModelImport;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.SupertypeSubtypeUtil;
import com.mentor.nucleus.bp.io.core.CoreExport;
import com.mentor.nucleus.bp.io.core.CoreImport;
import com.mentor.nucleus.bp.model.compare.providers.ComparableProvider;
import com.mentor.nucleus.bp.model.compare.providers.NonRootModelElementComparable;
import com.mentor.nucleus.bp.model.compare.providers.ObjectElementComparable;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Layer_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.inspector.GraphicalModelInspector;

public class ModelMergeProcessor {

	public static boolean merge(TreeDifferencer differencer,
			TreeDifference difference, boolean rightToLeft,
			ITreeDifferencerProvider contentProvider, ITableLabelProvider labelProvider, Ooaofooa destinationModelRoot)
			throws IOException {
		// this is a removal
		if (difference.getElement() instanceof EmptyElement
				&& difference.getMatchingDifference().getElement() instanceof NonRootModelElementComparable) {
			NonRootModelElementComparable localComparable = (NonRootModelElementComparable) difference
					.getMatchingDifference().getElement();
			disposeElement(localComparable);
			return true;
		}
		Object diffElement = difference.getElement();
		// if the difference is name based
		if (difference.getType() == TreeDifference.NAME_DIFFERENCE) {
			// find the child's difference that actually effects the
			// element name
			String text = labelProvider.getColumnText(diffElement, 0);
			difference = getDifferenceForNameChange(contentProvider,
					diffElement, text, rightToLeft, differencer);
			if (difference == null) {
				return false;
			}
			diffElement = difference.getElement();
		}
		if (diffElement instanceof NonRootModelElementComparable
				&& difference.getElement() != null
				&& difference.getMatchingDifference().getElement() != null
				&& difference.getLocation() != difference
						.getMatchingDifference().getLocation()
				&& !(difference.getMatchingDifference().getElement() instanceof EmptyElement)) {
			// this is a positional change
			return handlePositionChange(difference, contentProvider);
		}
		if (diffElement instanceof NonRootModelElementComparable) {
			// do not allow the merge if there is no parent
			// this is a case where the left element is null and
			// merging would result in the creation of a new element
			// This new element could however result in a case where
			// a duplicate element is created (rename) which the tool
			// currently does not behave well with
			if (difference.getElement() == null
					&& difference.getMatchingDifference().getParent() == null) {
				return false;
			}
			return handleNewElement(difference, contentProvider, diffElement,
					destinationModelRoot, differencer, rightToLeft);
		} else if (difference.getElement() instanceof ObjectElementComparable) {
			// just copy the value over, but create deltas so that they
			// can be applied to the real local copy
			ObjectElementComparable comparable = (ObjectElementComparable) difference
					.getElement();
			ObjectElementComparable localComparable = (ObjectElementComparable) difference
					.getMatchingDifference().getElement();
			ObjectElement element = (ObjectElement) comparable.getRealElement();
			ObjectElement localElement = (ObjectElement) localComparable
					.getRealElement();
			if (element.getType() == ObjectElement.ATTRIBUTE_ELEMENT) {
				return handleAttributeChange(localElement, element);
			} else if (element.getType() == ObjectElement.REFERENTIAL_ATTRIBUTE_ELEMENT) {
				handleReferential(element, localElement, contentProvider,
						destinationModelRoot, differencer, rightToLeft, difference);
				return true;
			}
		}
		return false;
	}

	/**
	 * This method will move the element to the same location as 
	 * determined by the difference
	 * 
	 * @param difference
	 * @return boolean, true if a change was made
	 */
	private static boolean handlePositionChange(TreeDifference difference, ITreeContentProvider contentProvider) {
		ComparableTreeObject comparable = (ComparableTreeObject) difference
				.getElement();
		Object destinationElement = difference.getMatchingDifference()
				.getElement();
		Object destinationParent = contentProvider.getParent(destinationElement);
		Object localParent = contentProvider.getParent(comparable);
		int location = TreeDifferencer.getLocationOfElement(localParent,
				comparable,
				(ITreeDifferencerProvider) contentProvider);
		int destinationLocation = TreeDifferencer.getLocationOfElement(
				destinationParent, destinationElement,
				(ITreeDifferencerProvider) contentProvider);
		if(location == destinationLocation) {
			// another difference has already handled this
			return false;
		}
		// if this is a change that does not have user ordering
		// then modify the persistence ordering
		NonRootModelElement element = (NonRootModelElement) comparable
				.getRealElement();
		if(!MetadataSortingManager.isOrderedElement(element)) {
			IPersistableElementParentDetails parentDetails = PersistenceManager
					.getHierarchyMetaData().getParentDetails(
							element);
			Object[] localDetails = new Object[] {
						parentDetails.getParent(), parentDetails.getChild(),
						parentDetails.getAssociationNumber(),
						parentDetails.getAssociationPhrase(),
						parentDetails.getChildKeyLetters() };
			callSetOrderOperation(location, localDetails);
			return true;
		}
		boolean up = location < destinationLocation;
		if(up) {
			insertElementAt(location, difference, contentProvider, true);
		} else {
			insertElementAt(location, difference, contentProvider, false);
		}
		return true;
	}
	
	private static void insertElementAt(int location, TreeDifference difference, ITreeContentProvider contentProvider, boolean moveUp) {
		NonRootModelElementComparable comparable = null;
		if(difference.getMatchingDifference().getElement() instanceof EmptyElement) {
			// find the newly copied over comparable
			NonRootModelElementComparable remoteComparable = (NonRootModelElementComparable) difference
					.getElement();
			NonRootModelElement remoteElement = (NonRootModelElement) remoteComparable
					.getRealElement();
			EmptyElement localEmptyElement = ((EmptyElement) difference
					.getMatchingDifference().getElement());
			NonRootModelElement parent = (NonRootModelElement) localEmptyElement
					.getParent();
			Object newElementInDestination = findObjectInDestination(
					parent.getModelRoot(), remoteElement);
			comparable = (NonRootModelElementComparable) ComparableProvider
					.getComparableTreeObject(newElementInDestination);
			
		} else {
			comparable = (NonRootModelElementComparable) difference
					.getMatchingDifference().getElement();
		}
		NonRootModelElement element = (NonRootModelElement) comparable
				.getRealElement();
		Object[] existingChildren = ((ITreeDifferencerProvider) contentProvider)
				.getChildren(contentProvider.getParent(element));
		if (!moveUp && location >= existingChildren.length) {
			location = existingChildren.length - 1;
		}
		Object existingElementAtNewLocation = existingChildren[location];
		if(existingElementAtNewLocation != null) {
			existingElementAtNewLocation = ((ComparableTreeObject) existingElementAtNewLocation).getRealElement();
		}
		String associationNumber = MetadataSortingManager
				.getAssociationNumber(element);
		String associationPhrase = MetadataSortingManager
				.getAssociationPhrase(element);
		
		if (moveUp) {
			NonRootModelElement previousElementToExisting = (NonRootModelElement) MetadataSortingManager
						.getPreviousElement((NonRootModelElement) existingElementAtNewLocation);
			if(previousElementToExisting != null) {
				Method unrelate = findMethod("unrelateAcrossR" + associationNumber
						+ "From" + associationPhrase, existingElementAtNewLocation.getClass(),
						new Class[] { existingElementAtNewLocation.getClass() });
				invokeMethod(unrelate, existingElementAtNewLocation,
						new Object[] { previousElementToExisting });
			}
			NonRootModelElement previousToSelf = (NonRootModelElement) MetadataSortingManager
					.getPreviousElement(element);
			if(previousToSelf != null) {		
				Method unrelate = findMethod("unrelateAcrossR" + associationNumber
						+ "From" + associationPhrase, element.getClass(),
						new Class[] { element.getClass() });
				invokeMethod(unrelate, element,
						new Object[] { previousToSelf });
			}
			NonRootModelElement nextToSelf = (NonRootModelElement) MetadataSortingManager
					.getNextElement(element);
			if(nextToSelf != null) {
				Method unrelate = findMethod("unrelateAcrossR" + associationNumber
						+ "From" + associationPhrase, nextToSelf.getClass(),
						new Class[] { nextToSelf.getClass() });
				invokeMethod(unrelate, nextToSelf,
						new Object[] { element });
				Method relate = findMethod("relateAcrossR" + associationNumber
						+ "To" + associationPhrase, nextToSelf.getClass(),
						new Class[] { nextToSelf.getClass() });
				invokeMethod(relate, nextToSelf,
						new Object[] { previousToSelf });
			}
			Method relate = findMethod("relateAcrossR" + associationNumber
					+ "To" + associationPhrase, existingElementAtNewLocation.getClass(),
					new Class[] { existingElementAtNewLocation.getClass() });
			invokeMethod(relate, existingElementAtNewLocation,
					new Object[] { element });
			if(previousElementToExisting != null) {
				relate = findMethod("relateAcrossR" + associationNumber
						+ "To" + associationPhrase, element.getClass(),
						new Class[] { element.getClass() });
				invokeMethod(relate, element,
						new Object[] { previousElementToExisting });
			}
		} else {
			NonRootModelElement nextElementToExisting = (NonRootModelElement) MetadataSortingManager
					.getNextElement((NonRootModelElement) existingElementAtNewLocation);
			if(nextElementToExisting != null) {
				Method unrelate = findMethod("unrelateAcrossR" + associationNumber
						+ "From" + associationPhrase, nextElementToExisting.getClass(),
						new Class[] { nextElementToExisting.getClass() });
				invokeMethod(unrelate, nextElementToExisting,
						new Object[] { existingElementAtNewLocation });
			}
			NonRootModelElement nextToSelf = (NonRootModelElement) MetadataSortingManager
					.getNextElement(element);
			if(nextToSelf != null) {
				Method unrelate = findMethod("unrelateAcrossR" + associationNumber
						+ "From" + associationPhrase, nextToSelf.getClass(),
						new Class[] { nextToSelf.getClass() });
				invokeMethod(unrelate, nextToSelf,
						new Object[] { element });
			}
			NonRootModelElement previousToSelf = (NonRootModelElement) MetadataSortingManager
					.getPreviousElement(element);
			if(previousToSelf != null) {
				Method unrelate = findMethod("unrelateAcrossR" + associationNumber
						+ "From" + associationPhrase, element.getClass(),
						new Class[] { element.getClass() });
				invokeMethod(unrelate, element,
						new Object[] { previousToSelf });
			}
			if(nextToSelf != null && previousToSelf != null) {	
				Method relate = findMethod("relateAcrossR" + associationNumber
						+ "To" + associationPhrase, nextToSelf.getClass(),
						new Class[] { nextToSelf.getClass() });
				invokeMethod(relate, nextToSelf,
						new Object[] { previousToSelf });
			}
			Method relate = findMethod("relateAcrossR" + associationNumber
					+ "To" + associationPhrase, element.getClass(),
					new Class[] { element.getClass() });
			invokeMethod(relate, element,
					new Object[] { existingElementAtNewLocation });
			if(nextElementToExisting != null) {
				relate = findMethod("relateAcrossR" + associationNumber
						+ "To" + associationPhrase, nextElementToExisting.getClass(),
						new Class[] { nextElementToExisting.getClass() });
				invokeMethod(relate, nextElementToExisting,
						new Object[] { element });
			}
		}
	}

	private static boolean handleAttributeChange(ObjectElement localElement, ObjectElement element) {
		// skip graphical.represents()
		if (element.getName().equals("represents")
				&& (element.getParent() instanceof GraphicalElement_c || element
						.getParent() instanceof Model_c)) {
			return false;
		}
		String attributeName = localElement.getValueAccessor().replaceAll(
				"get", "");
		if (attributeName.contains("Action_semantics")) {
			attributeName = attributeName + "_internal";
		}
		Object attributeOwner = localElement.getAttributeOwner();
		if(attributeOwner == null) {
			attributeOwner = localElement.getParent();
		}
		Class<?> paramClass = element.getValue().getClass();
		if(paramClass == Integer.class) {
			// use the int class
			paramClass = Integer.TYPE;
		} else if(paramClass == Float.class) {
			paramClass = Float.TYPE;
		}
		Method setMethod = findMethod("set" + attributeName,
				attributeOwner.getClass(), new Class[] { paramClass });
		invokeMethod(setMethod, attributeOwner, new Object[] {element.getValue()});
		return true;
	}

	private static boolean handleNewElement(TreeDifference difference,
			ITreeDifferencerProvider contentProvider, Object diffElement,
			Ooaofooa modelRoot, TreeDifferencer differencer, boolean rightToLeft) throws IOException {
		Object matchingDiffElement = difference.getMatchingDifference()
				.getElement();
		if(!(matchingDiffElement instanceof EmptyElement) && !matchingDiffElement.getClass().isInstance(diffElement)) {
			// not supported
			return false;
		}
		Object remoteParent = contentProvider.getParent(difference
				.getElement());
		int newElementLocation = difference.getLocation();
		NonRootModelElement parent = null;
		if(matchingDiffElement instanceof EmptyElement) {
			EmptyElement empty = (EmptyElement) matchingDiffElement;
			parent = (NonRootModelElement) empty.getParent();
			newElementLocation = empty.getLocation();
		} else {
			ComparableTreeObject parentObject = (ComparableTreeObject) contentProvider
					.getParent(matchingDiffElement);
			parent = (NonRootModelElement) parentObject
					.getRealElement();
		}
		// grab the existing children, so that ordering can be
		// fixed after the batch relate calls
		Object[] existingChildren = ((ITreeDifferencerProvider) contentProvider)
				.getChildren(parent);
		// create deltas for the creation of a new element
		NonRootModelElement newObject = null;
		Object realElement = ((ComparableTreeObject) diffElement)
				.getRealElement();
		List<Object> orderList = new ArrayList<Object>();
		if (MetadataSortingManager.isOrderedElement(realElement)) {
			// create a list to capture the existing order
			for(Object child : existingChildren) {
				if (((ComparableTreeObject) child).getRealElement().getClass() == ((ComparableTreeObject) diffElement)
						.getRealElement().getClass()) {
					orderList.add(child);
				}
			}
		}
		if (realElement instanceof NonRootModelElement) {
			newObject = (NonRootModelElement) realElement;
		} else if(realElement instanceof ObjectElement) {
			newObject = (NonRootModelElement) ((ObjectElement) realElement).getValue();
		}
		if (matchingDiffElement != null
				&& !(matchingDiffElement instanceof EmptyElement)) {
			Object existingDiffElement = ((ComparableTreeObject) matchingDiffElement).getRealElement();
			// first adjust so that the ordering matches for undo
			// this will place the element at the end position
			// and allow a restored deletion to get placed at
			// the proper location
			int length = getPersistenceLocation(parent, existingDiffElement,
					contentProvider, true);
			adjustPersistenceOrdering(existingDiffElement, length - 1);
			// this is a value difference, we need to dispose the current value
			// first
			// disable listeners as we do not want graphics deleted
			// for this case, they will have their own differences if
			// deletion is required
			try {
				Ooaofgraphics.disableChangeNotification();
				disposeElement(matchingDiffElement);
			} finally {
				Ooaofgraphics.enableChangeNotification();
			}
		}
		// look for the element locally, certain differences that
		// were
		// before this one may have created it, or it may be a
		// location difference in the file
		Object object = findObjectInDestination(modelRoot, newObject);
		if (object != null && !((NonRootModelElement) object).isProxy()) {
			newElementLocation = getPersistenceLocation(parent, newObject, contentProvider, false);
			return adjustPersistenceOrdering(object, newElementLocation);
		}
		// some situations required other data to be created first
		handleCopyNew(newObject, differencer, contentProvider, modelRoot, rightToLeft);
		// export the element
		String export = copyExternal(modelRoot, newObject, false, false);
		newObject = importExternal(newObject, export, parent, modelRoot, getPersistenceLocation(remoteParent,
				difference.getElement(), contentProvider, false));
		// if this element is a graphical element we need to
		// associate it with an element specification so the
		// proper ooa_type is exported
		if(newObject instanceof GraphicalElement_c) {
			final GraphicalElement_c element = (GraphicalElement_c) newObject;
			ElementSpecification_c spec = ElementSpecification_c
					.ElementSpecificationInstance(Ooaofgraphics
							.getDefaultInstance(), new ClassQueryInterface_c() {

						@Override
						public boolean evaluate(Object candidate) {
							return ((ElementSpecification_c) candidate)
									.getOoa_type() == element
									.getOoa_typeCachedValue();
						}
					});
			if (spec != null) {
				spec.relateAcrossR10To(element);
			}
			// additionally if the Model_c instance is null
			// the graphical element came from a situation where
			// the semantic elements were different (one case is
			// a state machine being created in two different branches)
			if(Model_c.getOneGD_MDOnR1(element) == null) {
				Model_c newModel = Model_c.ModelInstance(Ooaofgraphics
						.getInstance(modelRoot.getId()));
				element.relateAcrossR1To(newModel);
			}
		}
		// as above we may need to reassociate graphical layers with
		// the new Model instance
		if(newObject instanceof Layer_c) {
			Layer_c layer = (Layer_c) newObject;
			if(Model_c.getOneGD_MDOnR34(layer) == null) {
				Model_c newModel = Model_c.ModelInstance(Ooaofgraphics
						.getInstance(modelRoot.getId()));
				layer.relateAcrossR34To(newModel);
			}
		}
		Object[] children = contentProvider.getChildren(contentProvider
				.getParent(diffElement));
		// we need to look at all the referentials for this element
		// as they may not yet exist in the target, if that is the
		// case we create a copy now and associate
		children = contentProvider.getChildren(newObject);
		recursivelyCopyChildren(children, contentProvider, diffElement, modelRoot, differencer, rightToLeft);
		// if this is an ordered element we need to adjust the ordering accordingly
		if(MetadataSortingManager.isOrderedElement(realElement)) {
			// reset the order in the destination, placing the
			// new element at the end
			String associationNumber = MetadataSortingManager.getAssociationNumber(newObject);
			String associationPhrase = MetadataSortingManager.getAssociationPhrase(newObject);
			for(Object element : orderList) {
				NonRootModelElementComparable comparable = (NonRootModelElementComparable) element;
				NonRootModelElement orderedElement = (NonRootModelElement) comparable
						.getRealElement();
				ModelElement nextElement = MetadataSortingManager.getNextElement(orderedElement);
				if(nextElement != null) {
					Method unrelate = findMethod("unrelateAcrossR"
							+ associationNumber + "From" + associationPhrase,
							orderedElement.getClass(),
							new Class[] { orderedElement.getClass() });
					invokeMethod(unrelate, nextElement,
							new Object[] { orderedElement });
				} else {
					// in some cases we may need to
					// check the previous element
					ModelElement previous = MetadataSortingManager.getPreviousElement(orderedElement);
					if(previous != null) {
						Method unrelate = findMethod("unrelateAcrossR"
								+ associationNumber + "From" + associationPhrase,
								orderedElement.getClass(),
								new Class[] { orderedElement.getClass() });
						invokeMethod(unrelate, orderedElement,
								new Object[] { previous });						
					}
				}
			}
			int count = 0;
			for(Object element : orderList) {
				Object relateTo = null;
				NonRootModelElementComparable comparable = (NonRootModelElementComparable) element;
				NonRootModelElement orderedElement = (NonRootModelElement) comparable
					.getRealElement();
				if(count + 1 == orderList.size()) {
					// associate the new element
					relateTo = newObject;
				} else {
					relateTo = orderList.get(count + 1);
					NonRootModelElementComparable relateComparable = (NonRootModelElementComparable) relateTo;
					relateTo = (NonRootModelElement) relateComparable.getRealElement();
				}
				// relate each
				Method relate = findMethod("relateAcrossR"
						+ associationNumber + "To" + associationPhrase,
						orderedElement.getClass(),
						new Class[] { orderedElement.getClass() });
				invokeMethod(relate, relateTo,
						new Object[] { orderedElement });
				count++;
			}
			int currentLocation = TreeDifferencer.getLocationOfElement(parent,
					ComparableProvider.getComparableTreeObject(newObject), contentProvider);
			// the element will be at the end of the destination
			// list, now call the moveUp and moveDown accordingly
			if(currentLocation > newElementLocation) {
				// move up
				insertElementAt(newElementLocation, difference,
						contentProvider, true);
			}
			if(currentLocation < newElementLocation) {
				// move down
				insertElementAt(newElementLocation, difference,
						contentProvider, false);
			}
		} else {
			newElementLocation = getPersistenceLocation(remoteParent,
					difference.getElement(), contentProvider, false);
			adjustPersistenceOrdering(newObject, newElementLocation);
			batchRelateAll(newObject, modelRoot, contentProvider);
		}
		handlePostCreation(newObject, (NonRootModelElement) realElement);
		return true;
	}

	private static void batchRelateAll(NonRootModelElement newObject, ModelRoot modelRoot, ITreeContentProvider provider) {
		newObject.batchRelate(modelRoot, false, false);
		Object[] children = provider.getChildren(newObject);
		for(Object element : children) {
			if(element instanceof NonRootModelElementComparable) {
				NonRootModelElementComparable comparable = (NonRootModelElementComparable) element;
				batchRelateAll((NonRootModelElement) comparable.getRealElement(), modelRoot, provider);
			}
		}
	}
	
	private static int getPersistenceLocation(Object parent,
			Object newObject, ITreeDifferencerProvider contentProvider, boolean returnLength) {
		newObject = ComparableProvider.getComparableTreeObject(newObject);
		int location = 0;
		Object[] children = contentProvider.getChildren(parent);
		for(Object child : children) { 
			if(child.equals(newObject) && !returnLength) {
				break;
			}
			Object realChild = child;
			if(realChild instanceof ComparableTreeObject) {
				realChild = ((ComparableTreeObject) realChild).getRealElement();
			}
			Object realNewObject = newObject;
			if(newObject instanceof ComparableTreeObject) {
				realNewObject = ((ComparableTreeObject) realNewObject).getRealElement();
			}
			if(realChild.getClass() == realNewObject.getClass()) {
				location++;
			}
		}
		return location;
	}

	private static boolean adjustPersistenceOrdering(Object object, int newElementLocation) {
		Object lastSupertype = getLastSupertype((NonRootModelElement) object);
		// assure that the persisted location is correct
		IPersistableElementParentDetails parentDetails = PersistenceManager
				.getHierarchyMetaData().getParentDetails(
						(NonRootModelElement) lastSupertype);
		if (parentDetails.isMany()) {
			Object[] localDetails = new Object[] {
					parentDetails.getParent(), parentDetails.getChild(),
					parentDetails.getAssociationNumber(),
					parentDetails.getAssociationPhrase(),
					parentDetails.getChildKeyLetters() };
			int localLocation = getLocationForElementInSource(localDetails);
			if(newElementLocation != localLocation) {
				callSetOrderOperation(newElementLocation, localDetails);
				return true;
			}
		}
		return false;

	}

	private static void handlePostCreation(NonRootModelElement newObject, NonRootModelElement originalObject) {
		// for events we must create can't happens for all pre-existing
		// states
		if(newObject instanceof StateMachineEvent_c) {
			final StateMachineEvent_c event = (StateMachineEvent_c) newObject;
			StateMachine_c machine = StateMachine_c.getOneSM_SMOnR502(event);
			StateMachineState_c[] states = StateMachineState_c
					.getManySM_STATEsOnR501(machine);
			for (StateMachineState_c state : states) {
				StateEventMatrixEntry_c entry = StateEventMatrixEntry_c
						.getOneSM_SEMEOnR503(state,
								new ClassQueryInterface_c() {

									@Override
									public boolean evaluate(Object candidate) {
										return ((StateEventMatrixEntry_c) candidate)
												.getSmevt_id().equals(
														event.getSmevt_id());
									}
								});
				if(entry == null) {
					SemEvent_c sem = SemEvent_c.getOneSM_SEVTOnR525(event);
					createSEME(sem, state);
				}
			}
		}
		if (newObject instanceof StateMachineState_c) {
			final StateMachineState_c state = (StateMachineState_c) newObject;
			StateMachine_c machine = StateMachine_c.getOneSM_SMOnR501(state);
			StateMachineEvent_c[] events = StateMachineEvent_c
					.getManySM_EVTsOnR502(machine);
			for (StateMachineEvent_c event : events) {
				SemEvent_c sem = SemEvent_c.getOneSM_SEVTOnR525(event);
				StateEventMatrixEntry_c entry = StateEventMatrixEntry_c
						.getOneSM_SEMEOnR503(sem, new ClassQueryInterface_c() {

							@Override
							public boolean evaluate(Object candidate) {
								return ((StateEventMatrixEntry_c) candidate)
										.getSmstt_id().equals(
												state.getSmstt_id());
							}
						});
				if(entry == null) {
					createSEME(sem, state);
				}
			}
			// also check all events assigned to transitions leaving this state
			// as they may have been created as part of this merge
			Transition_c[] outgoing = Transition_c
					.getManySM_TXNsOnR507(NewStateTransition_c
							.getManySM_NSTXNsOnR504(StateEventMatrixEntry_c
									.getManySM_SEMEsOnR503(state)));
			for(Transition_c transition : outgoing) {
				final StateMachineEvent_c evt = StateMachineEvent_c
						.getOneSM_EVTOnR525(SemEvent_c.getOneSM_SEVTOnR503(StateEventMatrixEntry_c
								.getOneSM_SEMEOnR504(NewStateTransition_c
										.getOneSM_NSTXNOnR507(transition))));
				StateMachineState_c[] states = StateMachineState_c
						.getManySM_STATEsOnR501(machine);
				for(StateMachineState_c otherState : states) {
					StateEventMatrixEntry_c entry = StateEventMatrixEntry_c
							.getOneSM_SEMEOnR503(otherState,
									new ClassQueryInterface_c() {

										@Override
										public boolean evaluate(Object candidate) {
											return ((StateEventMatrixEntry_c) candidate)
													.getSmevt_id().equals(
															evt.getSmevt_id());
										}
									});
					if(entry == null) {
						SemEvent_c sem = SemEvent_c.getOneSM_SEVTOnR525(evt);
						createSEME(sem, otherState);
					}

				}
			}
		}
		if(newObject instanceof Transition_c) {
			// here we need to create a new SEME entry
			// or convert an existing one
			Transition_c transition = (Transition_c) newObject;
			// only do so if we are dealing with a transition with
			// an assigned event
			NoEventTransition_c net = NoEventTransition_c.getOneSM_NETXNOnR507(transition);
			if(net != null) {
				return;
			}
			// this includes creation transitions as well even if an
			// event is assigned
			CreationTransition_c crt = CreationTransition_c.getOneSM_CRTXNOnR507(transition);
			if(crt != null) {
				return;
			}
			StateEventMatrixEntry_c seme = StateEventMatrixEntry_c
					.getOneSM_SEMEOnR504(NewStateTransition_c
							.getOneSM_NSTXNOnR507((Transition_c) newObject));
			if(seme == null) {
				// look for the source state
				StateEventMatrixEntry_c originalSEM = StateEventMatrixEntry_c
						.getOneSM_SEMEOnR504(NewStateTransition_c
								.getOneSM_NSTXNOnR507((Transition_c) originalObject));
				StateMachineState_c originalState = StateMachineState_c
						.getOneSM_STATEOnR503(originalSEM);
				SemEvent_c originalEvent = SemEvent_c
						.getOneSM_SEVTOnR503(originalSEM);
				SemEvent_c destEvent = (SemEvent_c) getMatchingElement(originalEvent);
				StateMachineState_c destState = (StateMachineState_c) getMatchingElement(originalState);
				StateEventMatrixEntry_c newEntry = new StateEventMatrixEntry_c(
							transition.getModelRoot());
				destState.relateAcrossR503To(newEntry);
				destEvent.relateAcrossR503To(newEntry);
				newEntry.relateAcrossR504To(NewStateTransition_c
						.getOneSM_NSTXNOnR507(transition));
			} else {
				// convert the existing SEM
				CantHappen_c ch = CantHappen_c.getOneSM_CHOnR504(seme);
				if(ch != null) {
					ch.Dispose();
				}
				EventIgnored_c ei = EventIgnored_c.getOneSM_EIGNOnR504(seme);
				if(ei != null) {
					ei.unrelateAcrossR504From(seme);
					ei.delete();
				}
				seme.relateAcrossR504To(NewStateTransition_c
						.getOneSM_NSTXNOnR507(transition));
			}
		}
	}
	
	private static void createSEME(SemEvent_c sem, StateMachineState_c state) {
		StateEventMatrixEntry_c newEntry = new StateEventMatrixEntry_c(state.getModelRoot());
		CantHappen_c ch = new CantHappen_c(state.getModelRoot());
		ch.relateAcrossR504To(newEntry);
		newEntry.relateAcrossR503To(sem);
		newEntry.relateAcrossR503To(state);
	}

	private static NonRootModelElement importExternal(NonRootModelElement remoteObject, String export, NonRootModelElement parent, Ooaofooa modelRoot, int newElementLocation) {
		NonRootModelElement newObject = null;
		ModelStreamProcessor processor = new ModelStreamProcessor();
		processor.setContents(export);
		processor.setDestinationElement(parent);
		ByteArrayInputStream in = new ByteArrayInputStream(export.getBytes());
		try {
			CoreImport.createUniqueIds = false;
			IModelImport importer = CorePlugin.getStreamImportFactory().create(
					in, modelRoot, false, Path.EMPTY);
			processor.runImporter(importer, new NullProgressMonitor());
			// locate the new object from the loaded instances
			NonRootModelElement[] loadedInstances = importer
					.getLoadedInstances();
			if (parent.getModelRoot() instanceof Ooaofgraphics) {
				loadedInstances = importer.getLoadedGraphicalInstances();
			}
			for (NonRootModelElement elem : loadedInstances) {
				if(!elem.isProxy()) {
					IPersistableElementParentDetails parentDetails = PersistenceManager.getHierarchyMetaData().getParentDetails(elem);
					if(parentDetails == null) {
						// old data type that has a packageable element with
						// no package or component, these have PEs so that they
						// can be used in the chooser dialog
						if (elem.cachedIdentityEquals(remoteObject)) {
							newObject = elem;
						}
						continue;
					}
					if(parentDetails.isMany()) {
						Object[] localDetails = new Object[] { parentDetails.getParent(),
								parentDetails.getChild(),
								parentDetails.getAssociationNumber(),
								parentDetails.getAssociationPhrase(),
								parentDetails.getChildKeyLetters() };
						if(newElementLocation == -1) {
							IPersistableElementParentDetails remoteParentDetails = PersistenceManager.getHierarchyMetaData().getParentDetails(getMatchingElement(elem));
							Object[] remoteDetails = new Object[] { remoteParentDetails.getParent(),
									remoteParentDetails.getChild(),
									remoteParentDetails.getAssociationNumber(),
									remoteParentDetails.getAssociationPhrase(),
									remoteParentDetails.getChildKeyLetters() };
							newElementLocation = getLocationForElementInSource(remoteDetails);
						}
						callSetOrderOperation(newElementLocation, localDetails);
						NonRootModelElement matchingElement = getMatchingElement(elem);
						if(matchingElement != null) {
							Method getLocationInOrdering = findMethod("Getlocationinordering", matchingElement.getClass(), new Class[0]);
							if(getLocationInOrdering != null) {
								// adjust user configurable order, to do this we unassociate the new
								// element from the ordering association first
								int order = (Integer) invokeMethod(getLocationInOrdering, getMatchingElement(elem), new Object[0]);
								Method mergeOrdering = findMethod("Mergeordering", elem.getClass(), new Class[] {Integer.TYPE});
								if(mergeOrdering != null) {
									invokeMethod(mergeOrdering, elem, new Object[] {order});
								}
							}
						}
					}
					if (elem.equals(remoteObject)) {
						newObject = elem;
					} else {
						if (elem.cachedIdentityEquals(remoteObject)) {
							newObject = elem;
						}
					}
				}
			}
		} catch (IOException e) {
			CorePlugin.logError("Unable to import external merge data.", e);
		} finally {
			CoreImport.createUniqueIds = true;
		}
		return newObject;
	}

	private static void handleCopyNew(NonRootModelElement newObject,
			TreeDifferencer differencer, ITreeDifferencerProvider contentProvider,
			Ooaofooa modelRoot, boolean rightToLeft) throws IOException {
		Object[] children = contentProvider.getChildren(newObject);
		for (Object child : children) {
			if (child instanceof ObjectElementComparable) {
				ObjectElement element = (ObjectElement) ((ObjectElementComparable) child)
						.getRealElement();
				if(element.getValue() instanceof NonRootModelElement) {
					NonRootModelElementComparable value = (NonRootModelElementComparable) ComparableProvider
							.getComparableTreeObject((NonRootModelElement) element
									.getValue());
					List<TreeDifference> differences = differencer.getDifferences(
							value, !rightToLeft);
					if (!differences.isEmpty()) {
						TreeDifference difference = differences.get(0);
						if (difference.getMatchingDifference().getElement() == null) {
							// copy over
							handleNewElement(difference, contentProvider, difference.getElement(),
									modelRoot, differencer, rightToLeft);
						}
					}
				}
			}
		}
		// For SEME instances we may need to copy the SemEvent first
		if(newObject instanceof StateEventMatrixEntry_c) {
			SemEvent_c sem = SemEvent_c.getOneSM_SEVTOnR503((StateEventMatrixEntry_c) newObject);
			if(sem != null) {
				StateMachineEvent_c evt = StateMachineEvent_c
						.getOneSM_EVTOnR525(sem);
				NonRootModelElementComparable value = (NonRootModelElementComparable) ComparableProvider
						.getComparableTreeObject(evt);
				List<TreeDifference> differences = differencer.getDifferences(value, !rightToLeft);
				if(!differences.isEmpty()) {
					TreeDifference difference = differences.get(0);
					if (difference.getMatchingDifference().getElement() == null) {
						// copy over
						handleNewElement(difference, contentProvider, difference.getElement(),
								modelRoot, differencer, rightToLeft);
					}
				}
			}
		}
	}

	private static void recursivelyCopyChildren(Object[] children,
			ITreeDifferencerProvider contentProvider, Object diffElement,
			Ooaofooa modelRoot, TreeDifferencer differencer, boolean rightToLeft)
			throws IOException {
		for (Object child : children) {
			Object[] otherChildren = contentProvider.getChildren(diffElement);
			ComparableTreeObject matchingComparable = null;
			for (Object otherChild : otherChildren) {
				if (otherChild.equals(child)) {
					matchingComparable = (ComparableTreeObject) otherChild;
					break;
				}
			}
			if (child instanceof ObjectElementComparable) {
				ObjectElement localElement = (ObjectElement) ((ObjectElementComparable) child)
						.getRealElement();
				if (localElement.getType() == ObjectElement.REFERENTIAL_ATTRIBUTE_ELEMENT) {
					if (matchingComparable != null) {
						ObjectElement element = (ObjectElement) matchingComparable
								.getRealElement();
						NonRootModelElementComparable localValue = (NonRootModelElementComparable) ComparableProvider
								.getComparableTreeObject((NonRootModelElement) localElement
										.getValue());
						NonRootModelElementComparable value = (NonRootModelElementComparable) ComparableProvider
								.getComparableTreeObject((NonRootModelElement) element
										.getValue());
						if (localValue == null && value == null) {
							continue;
						}
						NonRootModelElementComparable localComp = (NonRootModelElementComparable) ComparableProvider
								.getComparableTreeObject(localValue);
						NonRootModelElementComparable remoteComp = (NonRootModelElementComparable) ComparableProvider
								.getComparableTreeObject(value);
						if(localComp != null && localComp.equals(remoteComp)) {
							continue;
						}
						handleReferential(element, localElement,
								contentProvider, modelRoot, differencer,
								rightToLeft, null);
					}
				}
			} else {
				Object[] newChildren = contentProvider.getChildren(child);
				recursivelyCopyChildren(newChildren, contentProvider, matchingComparable, modelRoot, differencer, rightToLeft);
			}
		}		
	}

	/**
	 * Using the details generated in the corresponding inspector class we determine
	 * the actual association between the container and the new element.  The details
	 * are as follows
	 * 
	 * details[0] = the parent object
	 * details[1] = the child object
	 * details[2] = the association number
	 * details[3] = an association phrase if present
	 * details[4] = the key letters of the child class
	 */
	private static int getLocationForElementInSource(Object[] details) {
		int location = 0;
		String methodName = "getMany" + (String) details[4] + "sOnR"
				+ (String) details[2] + (String) details[3];
		Method method = findMethod(methodName, details[1]
				.getClass(), new Class[] { details[0].getClass() });
		if(method == null) {
			CorePlugin.logError("Could not locate method with name: "
					+ methodName + " in "
					+ details[0].getClass().getSimpleName(), null);
		}
		Object[] children = (Object[]) invokeMethod(method, details[1],
				new Object[] { details[0] });
		if(children == null) return 0;
		for (int i = 0; i < children.length; i++) {
			// if we are dealing with PEs they have been moved such
			// that they are persisted in the same file as the subtype
			// we do not want to include such PEs
			if(!considerChild(children[i])) {
				continue;
			}
			if (children[i] == details[1]) {
				return location;
			}
			location++;
		}
		// return location putting it at the end, should not happen
		// though
		return location;
	}

	private static boolean considerChild(Object object) {
		if(object instanceof PackageableElement_c) {
			PackageableElement_c pe = (PackageableElement_c) object;
			if(pe.getType() == Elementtypeconstants_c.CLASS) {
				return false;
			} else if(pe.getType() == Elementtypeconstants_c.COMPONENT) {
				return false;
			} else if(pe.getType() == Elementtypeconstants_c.INTERFACE) {
				return false;
			} else if(pe.getType() == Elementtypeconstants_c.PACKAGE) {
				return false;
			}
		}
		return true;
	}

	private static Method findMethod(String methodName, Class<?> owner,
			Class<?>[] parameters) {
		try {
			Method method = owner.getMethod(methodName, parameters);
			return method;
		} catch (SecurityException e) {
			ComparePlugin.writeToLog("Unable to locate method.", e, owner);
		} catch (NoSuchMethodException e) {
			// do not log anything this is expected
		}
		return null;
	}

	private static Object invokeMethod(Method method, Object element,
			Object[] parameters) {
		if(method == null) {
			return null;
		}
		try {
			return method.invoke(element, parameters);
		} catch (IllegalArgumentException e) {
			ComparePlugin.writeToLog("Unable to execute method.", e, element
					.getClass());
		} catch (IllegalAccessException e) {
			ComparePlugin.writeToLog("Unable to execute method.", e, element
					.getClass());
		} catch (InvocationTargetException e) {
			ComparePlugin.writeToLog("Unable to execute method.", e, element
					.getClass());
		}
		return null;
	}

	private static void disposeElement(Object element) {
		// this is a removal
		NonRootModelElementComparable localComparable = (NonRootModelElementComparable) element;
		NonRootModelElement realElement = (NonRootModelElement) localComparable
				.getRealElement();
		// look for a special case merge disposal before
		// calling Dispose directly
		Method method = findMethod("Mergedispose", realElement.getClass(),
				new Class[0]);
		if (method != null) {
			invokeMethod(method, realElement, new Object[0]);
			return;
		}
		method = findMethod("Dispose", realElement.getClass(), new Class[0]);
		if (method != null) {
			invokeMethod(method, realElement, new Object[0]);
			return;
		} else {
			realElement.batchUnrelate(true);
			realElement.delete_unchecked();
			BaseModelDelta delete = new BaseModelDelta(Modeleventnotification_c.DELTA_DELETE, realElement);
			if(realElement.getModelRoot() instanceof Ooaofooa) {
				Ooaofooa.getDefaultInstance().fireModelElementDeleted(delete);
			} else {
				Ooaofgraphics.getDefaultInstance().fireModelElementDeleted(delete);
			}
		}
	}

	/**
	 * This method makes use of referentail details that are generated in the respective
	 * inspector classes.  The details are as follows:
	 * 
	 * details[0] = the referred to object
	 * details[1] = the referring object
	 * details[2] = the association number
	 * details[3] = association phrase if necessary
	 * 
	 */
	private static void handleReferential(ObjectElement element,
			ObjectElement localElement, ITreeDifferencerProvider contentProvider,
			Ooaofooa modelRoot, TreeDifferencer differencer, boolean rightToLeft, TreeDifference difference) throws IOException {
		IModelClassInspector insp = new ModelInspector();
		if (((NonRootModelElement) element.getParent()).getModelRoot() instanceof Ooaofgraphics) {
			insp = new GraphicalModelInspector();
		}
		Class<?> clazz = null;
		if (element.getValue() != null) {
			clazz = element.getValue().getClass();
		} else {
			clazz = localElement.getValue().getClass();
		}
		// allow pre-processing to prepare for merge
		if(element.getValue() != null) {
			String preprocessor = "Premerge"
				+ element.getValue().getClass().getSimpleName()
				.replaceAll("_c", "").toLowerCase()
				+ element.getName().replaceAll("referential_", "").replaceAll(
						"_", "").toLowerCase();
			Method preprocessMethod = findMethod(preprocessor, localElement.getParent().getClass(),
					new Class[0]);
			if (preprocessMethod != null) {
				invokeMethod(preprocessMethod, localElement.getParent(), new Object[0]);
			}
		}
		Object[] remoteReferentialData = insp.getReferentialDetails(clazz,
				element.getParent());
		Object[] localReferentialData = insp.getReferentialDetails(clazz,
				localElement.getParent());
		// referred to local element and referring local element
		Object referredLocal = localReferentialData[0];
		Object originalReferredLocal = referredLocal;
		Object referringLocal = localReferentialData[1];
		// referred to remote element
		Object referredRemote = remoteReferentialData[0];
		if (referredRemote != null && referringLocal != null) {
			// we need to make a copy here, unless it exists
			// in the target
			Object object = findObjectInDestination(modelRoot,
					(NonRootModelElement) referredRemote);
			if(((NonRootModelElement) referredRemote).isProxy()) {
				// this is a referential only difference, we need to copy the proxy
				// value over
				object = handleNewProxy((NonRootModelElement) referredRemote, modelRoot);
				// this batch relate may have handled hook up
				batchRelateSelfAndSupertypes((NonRootModelElement) referringLocal, modelRoot);
				localReferentialData = insp.getReferentialDetails(clazz, localElement.getParent());
				if(localReferentialData[0] != null) {
					return;
				}
			}
			if (object == null) {
				NonRootModelElementComparable comparable = (NonRootModelElementComparable) ComparableProvider.getComparableTreeObject(
						getElement((NonRootModelElement) element.getValue()));
				List<TreeDifference> differences = differencer.getDifferences(comparable,
						!rightToLeft);
				TreeDifference newDifference = differences.get(0);
				handleNewElement(newDifference, contentProvider, comparable,
						modelRoot, differencer, rightToLeft);
				Object newObject = null;
				NonRootModelElementComparable parentComparable = (NonRootModelElementComparable) ComparableProvider.getComparableTreeObject(
						(NonRootModelElement) localElement.getParent());
				// locate the new ObjectElement
				ObjectElement newObjEle = null;
				Object[] newChildren = contentProvider.getChildren(parentComparable);
				for(Object child : newChildren) {
					if(child instanceof ObjectElementComparable) {
						ObjectElementComparable oec = (ObjectElementComparable) child;
						ObjectElement objEle = (ObjectElement) oec.getRealElement();
						if(objEle.equals(element)) {
							newObjEle = objEle;
							break;
						}
					}
				}
				if(newObjEle == null) {
					newObjEle = localElement;
				}
				if(newObjEle != null) {
					if(handleSpecialCase(element, localElement, rightToLeft, differencer, modelRoot, contentProvider)) {
						return;
					}
					localElement = newObjEle;
					Object parent = contentProvider.getParent(localElement.getValue());
					Object[] children = contentProvider.getChildren(parent);
					for (Object child : children) {
						if (child instanceof NonRootModelElementComparable) {
							NonRootModelElement childEle = (NonRootModelElement) ((NonRootModelElementComparable) child)
							.getRealElement();

							if (childEle.cachedIdentityEquals(element.getValue())) {
								newObject = childEle;
								break;
							}
						}
					}
					if (newObject != null) {
						localReferentialData = insp.getReferentialDetails(clazz,
								localElement.getParent());
						// referred to local element and referring local element
						originalReferredLocal = localReferentialData[0];
						referringLocal = localReferentialData[1];
						referredLocal = newObject;
					}
				}
			} else {
				if (handleSpecialCase(element, localElement, rightToLeft,
						differencer, modelRoot, contentProvider)) {
					return;
				}
				// if the element exists set it as the referred
				referredLocal = object;
			}
		}
		if(referredLocal == null && referredRemote == null) {
			// nothing to do here
			return;
		}
		// look for a special case operation to handle the referential
		// merge before attempting the generic approach
		// if the referential is null, then we will pass in an empty
		// UUID
		if (referringLocal != null) {
			handlePreSpecialCase(element, contentProvider, modelRoot,
					rightToLeft, differencer, localElement, difference);
			UUID newId = Gd_c.Null_unique_id();
			if(referredRemote != null && referredLocal != null) {
				newId = ((NonRootModelElement) referredLocal).Get_ooa_id();
			}
			Class<?> referredClass = null;
			if(referredRemote != null) {
				referredClass = referredRemote.getClass();
			} else {
				referredClass = referredLocal.getClass();
			}
			String methodName = "Merge"
					+ referredClass.getSimpleName().replaceAll(
							"_c", "").toLowerCase()
					+ element.getName().replaceAll("referential_", "")
							.replaceAll("_", "").toLowerCase();
			Method method = findMethod(methodName, referringLocal
					.getClass(), new Class[] { UUID.class });
			if (method != null) {
				Object result = invokeMethod(method, referringLocal,
						new Object[] { newId });
				if(result != null && result instanceof Boolean) {
					if((Boolean) result) {
						return;
					}
				} else { 
					return;
				}
			}
		}
		// another merge action has already handled this
		if(referredLocal == originalReferredLocal && referredRemote != null) {
			return;
		}
		// if the referred local element is null do not bother
		// with an unrelate
		if(originalReferredLocal != null) {
			Method unrelateMethod = findMethod("unrelateAcrossR"
					+ (String) localReferentialData[2] + "From"
					+ (String) localReferentialData[3], referringLocal.getClass(),
					new Class[] { originalReferredLocal.getClass() });
			invokeMethod(unrelateMethod, referringLocal, new Object[] {originalReferredLocal});
		}
		if (referredRemote == null) {
			// only need the unrelate
			return;
		}
		Method relateMethod = findMethod("relateAcrossR"
				+ (String) localReferentialData[2] + "To"
				+ (String) localReferentialData[3], referringLocal.getClass(),
				new Class[] { referredLocal.getClass() });
		invokeMethod(relateMethod, referringLocal, new Object[] {referredLocal});
	}

	private static void handlePreSpecialCase(ObjectElement element,
			ITreeDifferencerProvider contentProvider, Ooaofooa modelRoot,
			boolean rightToLeft, TreeDifferencer differencer, ObjectElement localElement, TreeDifference originalDifference) {
		if (element.getName().equals("referential_To")
				&& element.getParent() instanceof Transition_c && originalDifference != null) {
			// in this case we are treating two different transitions as a
			// conflict
			// the reason is that a state cannot have two transitions out of it
			// that
			// are assigned to the same event
			// To merge this we need to copy over the new transition
			ComparableTreeObject localOwner = ComparableProvider
					.getComparableTreeObject(contentProvider
							.getParent(localElement.getParent()));
			TreeDifference difference = new TreeDifference(element.getParent(),
					TreeDifference.LOCATION_DIFFERENCE, true,
					TreeDifferencer.RIGHT | TreeDifferencer.ADDITION,
					TreeDifferencer.getPathForElement(ComparableProvider
							.getComparableTreeObject(element.getParent()),
							contentProvider), false, null);
			// locate the new empty element's parent
			Object otherElement = originalDifference.getMatchingDifference().getElement();
			// the parent is this element's parent's parent
			Object parent = contentProvider.getParent(contentProvider
					.getParent(otherElement));
			parent = ((ComparableTreeObject) parent).getRealElement();
			TreeDifference matchingDifference = new TreeDifference(
					new EmptyElement(element.getParent(), parent,
							TreeDifferencer.getLocationOfElement(
									contentProvider.getParent(element
											.getParent()), element.getParent(),
									contentProvider)),
					TreeDifference.LOCATION_DIFFERENCE, true,
					TreeDifferencer.RIGHT | TreeDifferencer.ADDITION,
					TreeDifferencer.getPathForElement(localOwner,
							contentProvider), false, null);
			matchingDifference.setParent(localOwner);
			difference.setMatchingDifference(matchingDifference);
			matchingDifference.setMatchingDifference(difference);
			try {
				handleNewElement(difference, contentProvider,
						ComparableProvider.getComparableTreeObject(element
								.getParent()), modelRoot, differencer,
						rightToLeft);
				if ((originalDifference.getKind() & Differencer.DIRECTION_MASK) != Differencer.CONFLICTING) {
					// for non-conflicting differences we need to get rid of the old
					// transition here
					disposeElement(ComparableProvider
							.getComparableTreeObject(localElement.getParent()));
				} else {
					// we need to migrate the old transition
					Transition_c transition = (Transition_c) localElement.getParent();
					StateEventMatrixEntry_c seme = StateEventMatrixEntry_c
							.getOneSM_SEMEOnR504(NewStateTransition_c
									.getOneSM_NSTXNOnR507(transition));
					SignalEvent_c sigEvt = SignalEvent_c
							.getOneSM_SGEVTOnR526(SemEvent_c
									.getOneSM_SEVTOnR503(seme));
					if(sigEvt != null) {
						transition.Removesignal();
					} else {
						transition.Removeevent();
					}
					// need to remove the CH that was created
					CantHappen_c ch = CantHappen_c.getOneSM_CHOnR504(seme);
					ch.Dispose();
				}
			} catch (IOException e) {
				CorePlugin.logError("Unable to merge new element.", e);
			}
		}
	}

	private static void batchRelateSelfAndSupertypes(NonRootModelElement element, ModelRoot modelRoot) {
		element.batchRelate(modelRoot, true, false);
		IPersistableElementParentDetails parentDetails = PersistenceManager.getHierarchyMetaData().getParentDetails(element);
		NonRootModelElement parent = parentDetails.getParent();
		if(parentDetails.getChild() instanceof PackageableElement_c) {
			parent = parentDetails.getChild();
		}
		NonRootModelElement supertype = null;
		if(parent != null && SupertypeSubtypeUtil.isSupertypeOf(element, parent)) {
			supertype = parent;
		}
		while(supertype != null) {
			supertype.batchRelate(modelRoot, true, false);
			parentDetails = PersistenceManager.getHierarchyMetaData().getParentDetails(supertype);
			parent = parentDetails.getParent();
			if(parent != null && SupertypeSubtypeUtil.isSupertypeOf(supertype, parent)) {
				supertype = parent;
			} else {
				supertype = null;
			}
		}
	}

	/**
	 * Special case to handle certain elements that currently have no generic
	 * way to address
	 * 
	 * @param element
	 * @param localElement
	 * @return
	 * @throws IOException
	 */
	private static boolean handleSpecialCase(ObjectElement element,
			ObjectElement localElement, boolean rightToLeft,
			TreeDifferencer differencer, Ooaofooa modelRoot,
			ITreeContentProvider contentProvider) throws IOException {
		if (localElement.getName().startsWith("referential_Assigned")) { //$NON-NLS-1$
			Object value = element.getValue();
			if (value != null
					&& (value instanceof StateMachineEvent_c
							|| value instanceof NonLocalEvent_c || value instanceof SignalEvent_c)) {
				final Object remoteTransition = element.getParent();
				Object localTransition = localElement.getParent();
				if (remoteTransition instanceof Transition_c) {
					final StateMachineEvent_c newEvent = StateMachineEvent_c
							.getOneSM_EVTOnR525(SemEvent_c
									.getOneSM_SEVTOnR503(StateEventMatrixEntry_c
											.getOneSM_SEMEOnR504(NewStateTransition_c
													.getOneSM_NSTXNOnR507((Transition_c) remoteTransition))));
					// dispose the old transition and any SEME associated
					// with the event
					StateMachineState_c state = StateMachineState_c
							.getOneSM_STATEOnR503(StateEventMatrixEntry_c
									.getOneSM_SEMEOnR504(NewStateTransition_c
											.getOneSM_NSTXNOnR507((Transition_c) localTransition)));
					if (state == null) {
						state = StateMachineState_c
								.getOneSM_STATEOnR508(NoEventTransition_c
										.getOneSM_NETXNOnR507((Transition_c) localTransition));
					}
					StateEventMatrixEntry_c[] semes = StateEventMatrixEntry_c
							.getManySM_SEMEsOnR503(state,
									new ClassQueryInterface_c() {

										@Override
										public boolean evaluate(Object candidate) {
											return ((StateEventMatrixEntry_c) candidate)
													.getSmevt_idCachedValue()
													.equals(
															newEvent
																	.getSmevt_id());
										}
									});
					for (StateEventMatrixEntry_c entry : semes) {
						entry.Mergedispose();
					}
					try {
						Ooaofooa.disableChangeNotification();
						((Transition_c) localTransition).Mergedispose();
					} finally {
						Ooaofooa.enableChangeNotification();
					}
					// the event will have already been copied over
					// now we must copy the transition over
					StateEventMatrixEntry_c seme = StateEventMatrixEntry_c
							.getOneSM_SEMEOnR504(NewStateTransition_c
									.getOneSM_NSTXNOnR507((Transition_c) remoteTransition));
					String remoteSEMEData = copyExternal(modelRoot, seme, false, false);
					NonRootModelElement newSEME = importExternal(seme,
							remoteSEMEData, state, modelRoot, -1);
					newSEME.batchRelate(modelRoot, true, false);
					String remoteTransitionData = copyExternal(modelRoot,
							(Transition_c) remoteTransition, false, false);
					final NonRootModelElement newObject = importExternal(
							(Transition_c) remoteTransition,
							remoteTransitionData, state, modelRoot, -1);
					// locate the GD_GE associated with the new transition
					// and update the path to the represented element
					GraphicalElement_c remoteGelem = GraphicalElement_c
							.GraphicalElementInstance(Ooaofgraphics
									.getInstance(seme.getModelRoot().getId()),
									new ClassQueryInterface_c() {

										@Override
										public boolean evaluate(Object candidate) {
											return ((GraphicalElement_c) candidate)
													.getOoa_id()
													.equals(
															((Transition_c) remoteTransition)
																	.getTrans_id());
										}
									});
					GraphicalElement_c gelem = GraphicalElement_c
							.GraphicalElementInstance(Ooaofgraphics
									.getInstance(modelRoot.getId()),
									new ClassQueryInterface_c() {

										@Override
										public boolean evaluate(Object candidate) {
											return ((GraphicalElement_c) candidate)
													.getOoa_id()
													.equals(
															((Transition_c) newObject)
																	.getTrans_id());
										}
									});
					gelem.Setcachedrepresentspath(remoteGelem
							.Getcachedrepresentspath());
					newObject.batchRelate(modelRoot, true, false);
					return true;
				}
			}
		}
		return false;
	}

	private static Object findObjectInDestination(ModelRoot modelRoot, NonRootModelElement remoteElement) {
		Object object = modelRoot.getInstanceList(
				remoteElement.getClass()).get(
				remoteElement.getInstanceKey());
		if(object == null) {
			// see if its a global element
			object = Ooaofooa.getDefaultInstance().getInstanceList(
					remoteElement.getClass())
					.get(
							((NonRootModelElement) remoteElement)
									.getInstanceKey());
		}
		if(object == null) {
			// consider graphical element
			object = Ooaofgraphics.getInstance(modelRoot.getId())
					.getInstanceList(remoteElement.getClass()).get(
							remoteElement.getInstanceKey());
		}
		if (remoteElement instanceof StateEventMatrixEntry_c && object != null) {
			// only consider this as existing if the subtypes are the same
			StateEventMatrixEntry_c remoteSeme = (StateEventMatrixEntry_c) remoteElement;
			StateEventMatrixEntry_c localSeme = (StateEventMatrixEntry_c) object;
			List<NonRootModelElement> remoteSubtypes = SupertypeSubtypeUtil
					.getSubtypes(remoteSeme);
			List<NonRootModelElement> localSubtypes = SupertypeSubtypeUtil
					.getSubtypes(localSeme);
			if (localSubtypes.isEmpty() || !remoteSubtypes.get(0).getClass().isInstance(
					localSubtypes.get(0))) {
				object = null;
			}
		}
		return object;
	}

	private static Object handleNewProxy(NonRootModelElement referredRemote, ModelRoot modelRoot) {
		Object newObject = null;
		String export = copyExternal(null, referredRemote, true, true);
		// now paste the external data
		ModelStreamProcessor processor = new ModelStreamProcessor();
		processor.setContents(export);
		ByteArrayInputStream in = new ByteArrayInputStream(export.getBytes());
		try {
			CoreImport.createUniqueIds = false;
			IModelImport importer = CorePlugin.getStreamImportFactory().create(
					in, Ooaofooa.getInstance(modelRoot.getId()), false, Path.EMPTY);
			processor.runImporter(importer, new NullProgressMonitor());
			// locate the new object from the loaded instances
			NonRootModelElement[] loadedInstances = importer
					.getLoadedInstances();
			if (modelRoot instanceof Ooaofgraphics) {
				loadedInstances = importer.getLoadedGraphicalInstances();
			}
			for (NonRootModelElement elem : loadedInstances) {
				if (elem.cachedIdentityEquals(referredRemote)) {
					newObject = elem;
					break;
				}
			}
		} catch (IOException e) {
            CorePlugin.logError("Unable to import copy", e);
		} finally {
			CoreImport.createUniqueIds = true;
		}
        return newObject;
	}

	private static NonRootModelElement getMatchingElement(
			NonRootModelElement elem) {
		ModelRoot otherRoot = elem.getModelRoot();
		if (elem.getModelRoot().getId().startsWith(Ooaofooa.leftCompareRootId)) {
			if (otherRoot instanceof Ooaofooa) {
				otherRoot = Ooaofooa.getInstance(elem.getModelRoot().getId()
						.replaceAll(Ooaofooa.leftCompareRootId,
								Ooaofooa.rightCompareRootId));
			} else {
				otherRoot = Ooaofgraphics.getInstance(elem.getModelRoot()
						.getId().replaceAll(Ooaofooa.leftCompareRootId,
								Ooaofooa.rightCompareRootId));
			}
		} else {
			if (otherRoot instanceof Ooaofooa) {
				otherRoot = Ooaofooa.getInstance(elem.getModelRoot().getId()
						.replaceAll(Ooaofooa.rightCompareRootId,
								Ooaofooa.leftCompareRootId));
			} else {
				otherRoot = Ooaofgraphics.getInstance(elem.getModelRoot()
						.getId().replaceAll(Ooaofooa.rightCompareRootId,
								Ooaofooa.leftCompareRootId));
			}
		}
		InstanceList instanceList = otherRoot.getInstanceList(elem.getClass());
		for(Object instance : instanceList) {
			NonRootModelElement other = (NonRootModelElement) instance;
			if(other.equals(elem)) {
				return other;
			}
			if(other.cachedIdentityEquals(elem)) {
				return other;
			}
		}
		return null;
	}

	private static NonRootModelElement getElement(NonRootModelElement value) {
		if(value instanceof DataType_c) {
			return SupertypeSubtypeUtil.getSubtypes(value).get(0);
		}
		return value;
	}

	private static TreeDifference getDifferenceForNameChange(
			ITreeContentProvider contentProvider, Object diffElement,
			String text, boolean rightToLeft, TreeDifferencer differencer) {
		Object[] children = contentProvider.getChildren(diffElement);
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof ObjectElementComparable) {
				ObjectElement objEle = (ObjectElement) ((ObjectElementComparable) children[i])
						.getRealElement();
				if (objEle.getValue().equals(text)) {
					List<TreeDifference> differences = differencer
							.getDifferences(children[i], !rightToLeft);
					if (differences.size() != 0) {
						return differences.get(0);
					}
				}
			} else {
				TreeDifference diff = getDifferenceForNameChange(
						contentProvider, children[i], text, rightToLeft, differencer);
				if (diff != null) {
					return diff;
				}
			}
		}
		return null;
	}

	/**
	 * This method moves the copied element into the appropriate association
	 * location, allowing a complete match with the source it was copied from.
	 * 
	 * It makes use of details generated in the corresponding inspector class for
	 * the new element.
	 * 
	 * See {@code ModelMergeProcessor#getLocationForElementInSource(Object[])} for
	 * more details.
	 */
	private static void callSetOrderOperation(	int position, Object[] details) {
		String className = details[1].getClass().getSimpleName().replaceAll(
				"_c", "");
		if (className.equals("FunctionInPackage")) {
			className = "FunctioninPackage";
		}
		String methodName = "set" + className + "OrderInChildListR"
				+ details[2];
		Method method = findMethod(methodName, details[0].getClass(),
				new Class[] { details[1].getClass(), Integer.TYPE });
		if (method != null) {
			invokeMethod(method, details[0], new Object[] { details[1],
					position });
		}
		// do not log an error as not all
		// elements support reordering (reflexives and
		// linked associations)
	}

	private static NonRootModelElement getLastSupertype(NonRootModelElement element) {
		IPersistableElementParentDetails parentDetails = PersistenceManager.getHierarchyMetaData().getParentDetails(element);
		NonRootModelElement parent = parentDetails.getParent();
		// special case for PE_PE, its considered a child of its subtype for
		// persistence reasons
		if(parentDetails.getChild() instanceof PackageableElement_c) {
			parent = parentDetails.getChild();
		}
		NonRootModelElement supertype = null;
		NonRootModelElement lastSupertype = null;
		if(parent != null && SupertypeSubtypeUtil.isSupertypeOf((NonRootModelElement) element, parent)) {
			lastSupertype = parent;
			supertype = parent;
		}
		while(supertype != null) {
			parentDetails = PersistenceManager.getHierarchyMetaData().getParentDetails(supertype);
			parent = parentDetails.getParent();
			// special case for PE_PE, its considered a child of its subtype for
			// persistence reasons
			if(parentDetails.getChild() instanceof PackageableElement_c) {
				parent = parentDetails.getChild();
			}
			if(parent != null && SupertypeSubtypeUtil.isSupertypeOf(supertype, parent)) {
				supertype = parent;
				lastSupertype = parent;
			} else {
				supertype = null;
			}
		}
		if(lastSupertype != null) {
			element = lastSupertype;
		}
		return element;
	}
	
	private static String copyExternal(ModelRoot modelRoot, Object element, boolean writeAsProxy, boolean forceProxies) {
		if (element instanceof NonRootModelElement) {
			// there is a strange special case situation
			// for Supertype/Subtype associations, it exists
			// because during a copy with the supertype included
			// the Association must be included, which in turn
			// includes the subtype parts even if not selected for
			// a copy Therefore we must add the O_OBJ to the selection
			// to get around this limitation here if the element is
			// the subtype object
			ModelClass_c[] clazzes = null;
			if (element instanceof Association_c) {
				ClassAsSubtype_c[] subtypes = ClassAsSubtype_c
						.getManyR_SUBsOnR213(SubtypeSupertypeAssociation_c
								.getOneR_SUBSUPOnR206((Association_c) element));
				if (subtypes != null && subtypes.length != 0) {
					clazzes = ModelClass_c.getManyO_OBJsOnR201(ClassInAssociation_c
							.getManyR_OIRsOnR203(ReferringClassInAssoc_c
									.getManyR_RGOsOnR205(subtypes)));
					for(ModelClass_c clazz : clazzes) {
						Selection.getInstance().addToSelection(clazz);
					}
				}
			} else if(element instanceof ClassAsSubtype_c) {
				ModelClass_c clazz = ModelClass_c
						.getOneO_OBJOnR201(ClassInAssociation_c
								.getOneR_OIROnR203(ReferringClassInAssoc_c
										.getOneR_RGOOnR205((ClassAsSubtype_c) element)));
				Selection.getInstance().addToSelection(clazz);
				clazzes = new ModelClass_c[] {clazz};
			}
			NonRootModelElement[] elements = new NonRootModelElement[] {(NonRootModelElement) element};
			if(!writeAsProxy) {
				NonRootModelElement[] specialElements = includeSpecialElements(element, modelRoot);
				// the stream export logic writes the supertype for
				// an element if that element is the one selected
				// we actually need to export (not just write the sql statement)
				// the supertype, so if this element has a supertype use it instead
				// however, only do this if the supertype does not already exist
				NonRootModelElement supertype = getLastSupertype((NonRootModelElement) element);
				Object existingObject = findObjectInDestination(modelRoot, supertype);
				if(existingObject == null) {
					// include the supertype
					element = supertype;
				}
				NonRootModelElement[] originalElements = new NonRootModelElement[] {(NonRootModelElement) element};
			    elements = new NonRootModelElement[originalElements.length + specialElements.length];
				System.arraycopy(originalElements, 0, elements, 0,
						originalElements.length);
				if(specialElements.length != 0) {
					System.arraycopy(specialElements, 0, elements,
							originalElements.length,
							specialElements.length);
				}
			}
			CoreExport.ignoreAlternateChildren = true;
			CoreExport.exportSupertypes = false;
			CoreExport.ignoreMissingPMCErrors = true;
			if(writeAsProxy) {
			     CoreExport.forceWriteAsProxy = true;
			}
			try {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				String streamContents = "";
				;
				IRunnableWithProgress progress = CorePlugin
						.getStreamExportFactory()
						.create(
								out,
								elements,
								true, false, true);
				progress.run(new NullProgressMonitor());
				out.close();
				streamContents = new String(out.toByteArray());
				return streamContents;
			} catch (InvocationTargetException e) {
				ComparePlugin.writeToLog("Unable to copy external data.", e,
						CorePlugin.class);
			} catch (InterruptedException e) {
				ComparePlugin.writeToLog("Unable to copy external data.", e,
						CorePlugin.class);
			} catch (IOException e) {
				ComparePlugin.writeToLog("Unable to copy external data.", e,
						CorePlugin.class);
			} finally {
				// if the clazz is not null then remove from the
				// selection
				if (clazzes != null && clazzes.length != 0) {
					for(ModelClass_c clazz : clazzes) {
						Selection.getInstance().removeFromSelection(clazz);
					}
				}
				CoreExport.ignoreAlternateChildren = false;
				CoreExport.exportSupertypes = true;
				CoreExport.ignoreMissingPMCErrors = false;
				if(writeAsProxy) {
				     CoreExport.forceWriteAsProxy = false;
				}
			}
		}
		return "";
	}

	private static NonRootModelElement[] includeSpecialElements(Object element, ModelRoot destinationRoot) {
		List<NonRootModelElement> specialElements = new ArrayList<NonRootModelElement>();
		if(element instanceof ClassAsSimpleParticipant_c) {
			// must include the Simple Association instance
			SimpleAssociation_c simp = SimpleAssociation_c.getOneR_SIMPOnR207((ClassAsSimpleParticipant_c) element);
			if(simp != null) {
				return new NonRootModelElement[] {simp};
			}
		}
		if(element instanceof ClassAsAssociatedOneSide_c) {
			// must include the Linked Association instance
			LinkedAssociation_c link = LinkedAssociation_c.getOneR_ASSOCOnR209((ClassAsAssociatedOneSide_c) element);
			if(link != null) {
				return new NonRootModelElement[] {link};
			}
		}
		// for states we need to also include all transitions out of the state
		if(element instanceof StateMachineState_c) {
			Transition_c[] transitions = Transition_c
					.getManySM_TXNsOnR507(NewStateTransition_c
							.getManySM_NSTXNsOnR504(StateEventMatrixEntry_c
									.getManySM_SEMEsOnR503((StateMachineState_c) element)));
			Transition_c[] noEvents = Transition_c
					.getManySM_TXNsOnR507(NoEventTransition_c
							.getManySM_NETXNsOnR508((StateMachineState_c) element));
			for(int i = 0; i < transitions.length; i++) {
				Transition_c transition = transitions[i];
				StateMachineEvent_c event = StateMachineEvent_c
						.getOneSM_EVTOnR525(SemEvent_c.getOneSM_SEVTOnR503(StateEventMatrixEntry_c
								.getOneSM_SEMEOnR504(NewStateTransition_c
										.getOneSM_NSTXNOnR507(transition))));
				if(findObjectInDestination(destinationRoot, event) == null) {
					// we need to include the event as well
					specialElements.add(event);
				}
				specialElements.add(transition);
			}
			for(int i = 0; i < noEvents.length; i++) {
				specialElements.add(noEvents[i]);
			}
		}
		return specialElements.toArray(new NonRootModelElement[specialElements
				.size()]);
	}

}
