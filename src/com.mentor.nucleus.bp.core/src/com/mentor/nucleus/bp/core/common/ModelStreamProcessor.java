//=====================================================================
//
//File:      $RCSfile: ModelStreamProcessor.java,v $
//Version:   $Revision: 1.25 $
//Modified:  $Date: 2013/01/10 22:54:09 $
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
package com.mentor.nucleus.bp.core.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.eclipse.core.runtime.IProgressMonitor;

import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.AsynchronousMessage_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BaseAttribute_c;
import com.mentor.nucleus.bp.core.Body_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.ClassAsLink_c;
import com.mentor.nucleus.bp.core.ClassAsSubtype_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.CreationTransition_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.DerivedAttributeBody_c;
import com.mentor.nucleus.bp.core.DerivedBaseAttribute_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.EnumerationDataType_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.ImportedProvision_c;
import com.mentor.nucleus.bp.core.ImportedReference_c;
import com.mentor.nucleus.bp.core.ImportedRequirement_c;
import com.mentor.nucleus.bp.core.InteractionParticipant_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.Lifespan_c;
import com.mentor.nucleus.bp.core.LinkedAssociation_c;
import com.mentor.nucleus.bp.core.Message_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.MooreActionHome_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.PortReference_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.ReturnMessage_c;
import com.mentor.nucleus.bp.core.Satisfaction_c;
import com.mentor.nucleus.bp.core.StateActionBody_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.StructuredDataType_c;
import com.mentor.nucleus.bp.core.SubsystemInDomain_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.SubtypeSupertypeAssociation_c;
import com.mentor.nucleus.bp.core.SynchronousMessage_c;
import com.mentor.nucleus.bp.core.SystemDatatypePackage_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.TimeSpan_c;
import com.mentor.nucleus.bp.core.TimingMark_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.ui.IModelImport;
import com.mentor.nucleus.bp.core.util.OoaofgraphicsUtil;
import com.mentor.nucleus.bp.core.util.SupertypeSubtypeUtil;

public class ModelStreamProcessor {

	private IModelImport fImporter;
	private Object[] fActivities;
	private NonRootModelElement[] fExportedElements;
	public static HashMap<String, ArrayList<String>> fProblemElements = new HashMap<String, ArrayList<String>>();
	private NonRootModelElement fDestinationElement;
	private String fContents;
	private static String fHeaderId = "root-types-contained";

	public void setContents(String contents) {
		fContents = contents;
	}

	public static void addElementToProblemsList(String message,
			String elementName) {
		ArrayList<String> existingList = fProblemElements.get(message);
		if (existingList == null) {
			existingList = new ArrayList<String>();
		}
		existingList.add(elementName);
		fProblemElements.put(message, existingList);
	}

	public void parseImportedElements(IProgressMonitor pm) {
		if (fActivities.length != 0) {
			CorePlugin.parseAllActions(fDestinationElement.getModelRoot(),
					fActivities, pm);
			Body_c[] bodies = getBodyInstances();
			for (int i = 0; i < bodies.length; i++) {
				if (bodies[i] != null) {
					bodies[i].Initialize();
					// all events that occur during a parse
					// are marked to not notify of changes
					// in order to trigger persistence of
					// the newly created OAL instances
					// we fire a created event for each body
					Ooaofooa.getDefaultInstance().fireModelElementCreated(
							new BaseModelDelta(
									Modeleventnotification_c.DELTA_NEW,
									bodies[i]));
				}
			}
		}
	}

	private Body_c[] getBodyInstances() {
		ArrayList<Body_c> bodies = new ArrayList<Body_c>();
		for (int i = 0; i < fActivities.length; i++) {
			Body_c body = null;
			if (fActivities[i] instanceof Attribute_c) {
				body = Body_c
						.getOneACT_ACTOnR698(DerivedAttributeBody_c
								.getOneACT_DABOnR693(DerivedBaseAttribute_c
										.getOneO_DBATTROnR107(BaseAttribute_c
												.getOneO_BATTROnR106((Attribute_c) fActivities[i]))));
			} else if (fActivities[i] instanceof StateMachineState_c) {
				body = Body_c
						.getOneACT_ACTOnR698(StateActionBody_c
								.getOneACT_SABOnR691(Action_c
										.getOneSM_ACTOnR514(ActionHome_c
												.getOneSM_AHOnR513(MooreActionHome_c
														.getOneSM_MOAHOnR511((StateMachineState_c) fActivities[i])))));
			} else if (fActivities[i] instanceof ModelElement) {
				body = OALPersistenceUtil
						.getOALModelElement((ModelElement) fActivities[i]);
			}

			if (body != null) {
				bodies.add(body);
			}
		}
		return bodies.toArray(new Body_c[bodies.size()]);
	}

	private Object[] getActivityElements() {
		ModelRoot root = Ooaofooa
				.getInstance(Ooaofooa.CLIPBOARD_MODEL_ROOT_NAME);
		ArrayList<Object> objects = new ArrayList<Object>();
		Function_c[] functions = Function_c.FunctionInstances(root);
		for (int i = 0; i < functions.length; i++) {
			objects.add((Object) functions[i]);
		}
		DerivedBaseAttribute_c[] dbas = DerivedBaseAttribute_c
				.DerivedBaseAttributeInstances(root);
		for (int i = 0; i < dbas.length; i++) {
			Attribute_c attr = Attribute_c.getOneO_ATTROnR106(BaseAttribute_c
					.getOneO_BATTROnR107(dbas[i]));
			objects.add((Object) attr);
		}
		StateMachineState_c[] states = StateMachineState_c
				.StateMachineStateInstances(root);
		for (int i = 0; i < states.length; i++) {
			objects.add((Object) states[i]);
		}
		Operation_c[] operations = Operation_c.OperationInstances(root);
		for (int i = 0; i < operations.length; i++) {
			objects.add((Object) operations[i]);
		}
		Bridge_c[] bridges = Bridge_c.BridgeInstances(root);
		for (int i = 0; i < bridges.length; i++) {
			objects.add((Object) bridges[i]);
		}
		Transition_c[] transitions = Transition_c.TransitionInstances(root);
		for (int i = 0; i < transitions.length; i++) {
			objects.add((Object) transitions[i]);
		}
		return objects.toArray();
	}

	private void callResolutionOperations(IProgressMonitor monitor) {
		NonRootModelElement[] elements = getProxies();
		monitor.beginTask("Resolving referred to elements...", elements.length
				* 2 + fImporter.getLoadedInstances().length);
		for (int i = 0; i < elements.length; i++) {
			List<?> list = PersistenceManager.getHierarchyMetaData()
					.findExternalRGOs(elements[i], false, false);
			for (int j = 0; j < list.size(); j++) {
				String refClassName = elements[i].getClass().getSimpleName()
						.replaceAll("_c", "").toLowerCase();
				try {
					Method resolutionMethod = list.get(j).getClass().getMethod(
							"Resolve" + refClassName, new Class[] {});
					resolutionMethod.invoke(list.get(j), new Object[] {});
				} catch (SecurityException e) {
					// do nothing
				} catch (NoSuchMethodException e) {
					// do nothing
				} catch (IllegalArgumentException e) {
					// do nothing
				} catch (IllegalAccessException e) {
					// do nothing
				} catch (InvocationTargetException e) {
					CorePlugin.logError("Unable to invoke resolution method.", e);
				}
			}
			monitor.worked(1);
		}
		// resolve RGOs
		NonRootModelElement[] exportedElements = fImporter.getLoadedInstances();
		for (int i = 0; i < exportedElements.length; i++) {
			NonRootModelElement element = getRTOElementForResolution(exportedElements[i]);
			// if the old proxy map contains an element
			// we must use it to find the RGOs
			NonRootModelElement oldProxy = fOldProxyMap
					.get(exportedElements[i]);
			HashMap<String, List<NonRootModelElement>> list = null;
			if (oldProxy != null) {
				list = PersistenceManager.getHierarchyMetaData()
						.getAssociationMapOfExternalRGOs(oldProxy);
			} else {
				list = PersistenceManager.getHierarchyMetaData()
						.getAssociationMapOfExternalRGOs(element);
			}
			Set<String> keySet = list.keySet();
			for(String key : keySet) {
				List<NonRootModelElement> elementList = list.get(key);
	 			for (int j = 0; j < elementList.size(); j++) {
					NonRootModelElement proxyElement = (NonRootModelElement) elementList
							.get(j);
					if (proxyElement.isProxy()) {
						String refClassName = proxyElement.getClass()
								.getSimpleName().replaceAll("_c", "").toLowerCase();
						try {
							Method resolutionMethod = element.getClass().getMethod(
									"Resolve" + refClassName + "for" + key,
									new Class[] { NonRootModelElement.class });
							resolutionMethod.invoke(element,
									new Object[] { proxyElement });
						} catch (SecurityException e) {
							// do nothing
						} catch (NoSuchMethodException e) {
							// do nothing
						} catch (IllegalArgumentException e) {
							// do nothing
						} catch (IllegalAccessException e) {
							// do nothing
						} catch (InvocationTargetException e) {
							// do nothing
						}
					}
				}
			}
			monitor.worked(1);
		}
		for (int i = 0; i < elements.length; i++) {
			// clean up all proxies
			elements[i].batchUnrelate();
			elements[i].delete_unchecked();
			monitor.worked(1);
		}
	}

	private NonRootModelElement getRTOElementForResolution(
			NonRootModelElement element) {
		if (element instanceof UserDataType_c) {
			DataType_c dt = DataType_c
					.getOneS_DTOnR17((UserDataType_c) element);
			return dt;
		} else if (element instanceof StructuredDataType_c) {
			DataType_c dt = DataType_c
					.getOneS_DTOnR17((StructuredDataType_c) element);
			return dt;
		} else if (element instanceof EnumerationDataType_c) {
			DataType_c dt = DataType_c
					.getOneS_DTOnR17((EnumerationDataType_c) element);
			return dt;
		} else {
			return element;
		}
	}

	public NonRootModelElement[] getProxies() {
		NonRootModelElement[] loadedInstances = fImporter.getLoadedInstances();
		ArrayList<NonRootModelElement> list = new ArrayList<NonRootModelElement>();
		for (int i = 0; i < loadedInstances.length; i++) {
			if (loadedInstances[i].isProxy()) {
				list.add(loadedInstances[i]);
			}
		}
		return list.toArray(new NonRootModelElement[list.size()]);
	}

	private void setComponents() {
		NonRootModelElement[] elements = fImporter.getLoadedInstances();
		for (int i = 0; i < elements.length; i++) {
			if (!elements[i].isProxy())
				elements[i].setComponent(null);
		}
		// set the components for graphical instances
		elements = fImporter.getLoadedGraphicalInstances();
		for (int i = 0; i < elements.length; i++) {
			if (!elements[i].isProxy()) {
				// need to use the model root from the represented
				// element
				setModelRootForGraphicalElement(elements[i]);
				elements[i].setComponent(null);
			}
		}
	}

	private void setModelRootForGraphicalElement(
			NonRootModelElement nonRootModelElement) {
		NonRootModelElement modelClass = null;
		if (nonRootModelElement.getClass().getSimpleName().equals("Model_c")) { //$NON-NLS-1$
			modelClass = nonRootModelElement;
		} else {
			modelClass = PersistenceManager.getHierarchyMetaData().getParent(
					nonRootModelElement);
			Object lastClass = modelClass;
			while (modelClass != null
					&& (!modelClass.getClass().getSimpleName()
							.equals("Model_c") || modelClass.getThisRoot() == null)) { //$NON-NLS-1$
				// generated getParent() method returns the child
				// DataTypePackage for
				// persistence reasons, we are only concerned in true
				// parent/child hierarchy
				// so return SystemModel in case of SystemDatatypePackage
				if (modelClass instanceof SystemDatatypePackage_c) {
					modelClass = SystemModel_c
							.getOneS_SYSOnR4400((SystemDatatypePackage_c) modelClass);
				} else if(modelClass instanceof SubsystemInDomain_c) {
					modelClass = Domain_c.getOneS_DOMOnR43((SubsystemInDomain_c) modelClass);
				}
				modelClass = PersistenceManager.getHierarchyMetaData()
						.getParent(modelClass);
				if(modelClass == lastClass) {
					// this is a catch to prevent infinite loops
					// log an error as we should always be able to
					// return something its likely that we need to
					// add another special case
					CorePlugin
							.logError(
									"Infinite loop prevention occurred, examine graphical element model root location code.",
									null);
					break;
				} else {
					lastClass = modelClass;
				}
			}
			if(modelClass == null) {
				// system level element, there will be no need to update the root
				return;
			}
			if (modelClass.getThisRoot() != null) {
				nonRootModelElement.updateModelRoot(modelClass.getThisRoot());
				return;
			}
		}
		try {
			Method method = modelClass.getClass().getMethod("getRepresents",
					new Class[0]);
			NonRootModelElement represents = (NonRootModelElement) method
					.invoke(modelClass, new Object[0]);
			modelClass.updateModelRoot((ModelRoot) OoaofgraphicsUtil
					.getGraphicsRoot(represents.getModelRoot().getId(),
							OoaofgraphicsUtil.getGraphicsClass()));
		} catch (SecurityException e) {
			CorePlugin
					.logError("Unable to look for getRepresents() method.", e);
		} catch (NoSuchMethodException e) {
			// no logging an error, as only a few will have the method
		} catch (IllegalArgumentException e) {
			CorePlugin.logError("Unable to invoke getRepresents() method.", e);
		} catch (IllegalAccessException e) {
			CorePlugin.logError("Unable to invoke getRepresents() method.", e);
		} catch (InvocationTargetException e) {
			CorePlugin.logError("Unable to invoke getRepresents() method.", e);
		}
	}

	public static String[] getExportedTypes(String contents) {
		String[] types = new String[] {};
		if (contents.indexOf(fHeaderId) != -1) { //$NON-NLS-1$
			// now also check that the first line in the
			// string is the header we are looking for
			if (contents.startsWith("-- " + fHeaderId)) {
				String typesOnClipboard = contents.substring(contents
						.indexOf(":") + 2, contents.indexOf("\n"));
				types = typesOnClipboard.trim().split(",");
			}
		}
		return types;
	}

	private void moveElementsToDestinationRoot(IProgressMonitor monitor) {
		NonRootModelElement[] elements = fImporter.getLoadedInstances();
		monitor.beginTask("Connecting elements to destination...",
				elements.length);
		for (int i = 0; i < elements.length; i++) {
			// Special case to allow model class proxies to move
			// to the destination root. This is required to allow
			// polymorphic event resolution when the supertype is
			// imported. These will be removed when the pasting
			// is complete.
			if (elements[i].isProxy() && !(elements[i] instanceof ModelClass_c))
				continue;
			elements[i].updateModelRoot(fDestinationElement.getModelRoot());
			monitor.worked(1);
		}
	}

	public boolean isTypePartOfExport(NonRootModelElement element) {
		String className = element.getClass().getSimpleName();
		String[] types = getExportedTypes(fContents);
		for (int i = 0; i < types.length; i++) {
			if (types[i].equals(className) && !element.isProxy()) {
				// if the element has no parent then it was definitely
				// exported, or if the parent is a proxy
				NonRootModelElement parent = PersistenceManager
						.getHierarchyMetaData().getParent(element);
				if (parent == null || parent.isProxy()
						|| elementToBeImported(element))
					return true;
			}
		}
		return false;
	}

	private boolean elementToBeImported(NonRootModelElement element) {
		if (element instanceof ClassAsSubtype_c) {
			Association_c association = Association_c
					.getOneR_RELOnR206(SubtypeSupertypeAssociation_c
							.getOneR_SUBSUPOnR213((ClassAsSubtype_c) element));
			Subsystem_c subsystem = Subsystem_c.getOneS_SSOnR4(association);
			return subsystem == null || subsystem.isProxy();
		} else if (element instanceof ClassAsLink_c) {
			Association_c association = Association_c
					.getOneR_RELOnR206(LinkedAssociation_c
							.getOneR_ASSOCOnR211((ClassAsLink_c) element));
			Subsystem_c subsystem = Subsystem_c.getOneS_SSOnR4(association);
			return subsystem == null || subsystem.isProxy();
		} else if (element instanceof CreationTransition_c) {
			Transition_c transition = Transition_c
					.getOneSM_TXNOnR507((CreationTransition_c) element);
			StateMachine_c sm = StateMachine_c.getOneSM_SMOnR505(transition);
			return sm == null || sm.isProxy();
		}
		// if the parent is a supertype, then traverse up
		// the hierarchy until a non-supertype is found
		// that is the parent we will check for null
		if (checkParentAsSupertype(element)) {
			return true;
		}
		// for provisions and requirements the direct
		// parent must always be copied as well, we
		// need to check the attached to element to see if
		// its parent is null
		if (element instanceof Provision_c) {
			Port_c port = Port_c.getOneC_POOnR4016(InterfaceReference_c
					.getOneC_IROnR4009((Provision_c) element));
			Component_c component = Component_c.getOneC_COnR4010(port);
			if (PersistenceManager.getHierarchyMetaData().getParent(component) == null
					|| PersistenceManager.getHierarchyMetaData().getParent(
							component).isProxy()) {
				return true;
			}
		}
		if (element instanceof Requirement_c) {
			Port_c port = Port_c.getOneC_POOnR4016(InterfaceReference_c
					.getOneC_IROnR4009((Requirement_c) element));
			Component_c component = Component_c.getOneC_COnR4010(port);
			if (PersistenceManager.getHierarchyMetaData().getParent(component) == null
					|| PersistenceManager.getHierarchyMetaData().getParent(
							component).isProxy()) {
				return true;
			}
		}
		if (element instanceof ImportedProvision_c) {
			ComponentReference_c ref = ComponentReference_c
					.getOneCL_ICOnR4707(PortReference_c.getOneCL_POROnR4708(ImportedReference_c
							.getOneCL_IIROnR4703((ImportedProvision_c) element)));
			if (PersistenceManager.getHierarchyMetaData().getParent(ref) == null
					|| PersistenceManager.getHierarchyMetaData().getParent(ref)
							.isProxy()) {
				return true;
			}
		}
		if (element instanceof ImportedRequirement_c) {
			ComponentReference_c ref = ComponentReference_c
					.getOneCL_ICOnR4707(PortReference_c.getOneCL_POROnR4708(ImportedReference_c
							.getOneCL_IIROnR4703((ImportedRequirement_c) element)));
			if (PersistenceManager.getHierarchyMetaData().getParent(ref) == null
					|| PersistenceManager.getHierarchyMetaData().getParent(ref)
							.isProxy()) {
				return true;
			}
		}
		if (checkInteractionElements(element)) {
			return true;
		}
		// for 1.5.0 we know systems are not exported
		if (PersistenceManager.getHierarchyMetaData().getParent(element) instanceof SystemModel_c) {
			return true;
		}
		return false;
	}

	private boolean checkInteractionElements(NonRootModelElement element) {
		// connectors must be copied with there source
		// and or destination, in these cases the parent
		// may not be null
		// test those cases here
		if (element instanceof SynchronousMessage_c) {
			// get the target interction part
			SynchronousMessage_c sync = (SynchronousMessage_c) element;
			InteractionParticipant_c part = InteractionParticipant_c
					.getOneSQ_POnR1007(Message_c.getOneMSG_MOnR1018(sync));
			if (PersistenceManager.getHierarchyMetaData().getParent(part) == null) {
				return true;
			}
		}
		if (element instanceof AsynchronousMessage_c) {
			// get the target interction part
			AsynchronousMessage_c sync = (AsynchronousMessage_c) element;
			InteractionParticipant_c part = InteractionParticipant_c
					.getOneSQ_POnR1007(Message_c.getOneMSG_MOnR1018(sync));
			if (PersistenceManager.getHierarchyMetaData().getParent(part) == null) {
				return true;
			}
		}
		if (element instanceof ReturnMessage_c) {
			// get the target interction part
			ReturnMessage_c sync = (ReturnMessage_c) element;
			InteractionParticipant_c part = InteractionParticipant_c
					.getOneSQ_POnR1007(Message_c.getOneMSG_MOnR1018(sync));
			if (PersistenceManager.getHierarchyMetaData().getParent(part) == null) {
				return true;
			}
		}
		if (element instanceof TimingMark_c) {
			TimingMark_c mark = (TimingMark_c) element;
			InteractionParticipant_c part = InteractionParticipant_c
					.getOneSQ_POnR930(Lifespan_c.getOneSQ_LSOnR931(mark));
			if (PersistenceManager.getHierarchyMetaData().getParent(part) == null) {
				return true;
			}
		}
		if (element instanceof TimeSpan_c) {
			TimeSpan_c span = (TimeSpan_c) element;
			InteractionParticipant_c part = InteractionParticipant_c
					.getOneSQ_POnR930(Lifespan_c.getOneSQ_LSOnR931(TimingMark_c
							.getOneSQ_TMOnR941(span)));
			if (PersistenceManager.getHierarchyMetaData().getParent(part) == null) {
				return true;
			}
		}
		return false;
	}

	private boolean checkParentAsSupertype(NonRootModelElement element) {
		NonRootModelElement child = element;
		NonRootModelElement parent = PersistenceManager.getHierarchyMetaData()
				.getParent(element);
		while (parent != null
				&& SupertypeSubtypeUtil.isSupertypeOf(child, parent)) {
			child = parent;
			parent = PersistenceManager.getHierarchyMetaData()
					.getParent(parent);
		}
		if (parent == null || parent.isProxy()) {
			// if the parent is null, then
			// this element is part of the export
			return true;
		}
		return false;
	}

	public void callPasteOperations(IProgressMonitor monitor) {
		monitor.beginTask("Connecting elements to destination...",
				fExportedElements.length);
		fOldProxyMap.clear();
		for (int i = 0; i < fExportedElements.length; i++) {
			try {
				storeProxyElement(fExportedElements[i]);
				Class<?> clazz = fDestinationElement.getClass();
				String simpleClassName = fExportedElements[i].getClass()
						.getSimpleName();
				String classPart = simpleClassName.substring(0,
						simpleClassName.length() - 2).toLowerCase();
				String opName = "Paste" + classPart; //$NON-NLS-1$
				Method pasteMethod = clazz.getMethod(opName,
						new Class[] { UUID.class });
				pasteMethod.invoke(fDestinationElement,
						new Object[] { fExportedElements[i].Get_ooa_id() });
				monitor.worked(1);
			} catch (SecurityException e) {
				CorePlugin.logError("Unable to execute paste operation.", e); //$NON-NLS-1$
			} catch (NoSuchMethodException e) {
				CorePlugin.logError("Unable to execute paste operation.", e); //$NON-NLS-1$
			} catch (IllegalArgumentException e) {
				CorePlugin.logError("Unable to execute paste operation.", e); //$NON-NLS-1$
			} catch (IllegalAccessException e) {
				CorePlugin.logError("Unable to execute paste operation.", e); //$NON-NLS-1$
			} catch (InvocationTargetException e) {
				CorePlugin.logError("Unable to execute paste operation.", e); //$NON-NLS-1$
			}
		}
		// special case for satisfactions, they are not selectable in the UI and
		// are not considered as being exported
		NonRootModelElement[] loadedInstances = fImporter.getLoadedInstances();
		for(NonRootModelElement instance : loadedInstances) {
			if(instance instanceof Satisfaction_c) {
				// see if its current parent is null or a proxy
				// if so we need to hook this instance up with
				// the destination
				NonRootModelElement parent = PersistenceManager
						.getHierarchyMetaData().getParent(instance);
				if(parent == null || parent.isProxy()) {
					PackageableElement_c pe = PackageableElement_c.getOnePE_PEOnR8001((Satisfaction_c) instance);
					if(pe != null) {
						Package_c oldPackage = Package_c.getOneEP_PKGOnR8000(pe);
						if(oldPackage != null) {
							pe.unrelateAcrossR8000From(oldPackage);
						}
						Component_c oldComp = Component_c.getOneC_COnR8003(pe);
						if(oldComp != null) {
							oldComp.unrelateAcrossR8003From(pe);
						}
						if(fDestinationElement instanceof Package_c) {
							pe.relateAcrossR8000To((Package_c) fDestinationElement);
						} else if(fDestinationElement instanceof Component_c) {
							pe.relateAcrossR8003To((Component_c) fDestinationElement);
						}
					}
				}
			}
		}
	}

	private HashMap<NonRootModelElement, NonRootModelElement> fOldProxyMap = new HashMap<NonRootModelElement, NonRootModelElement>();
	private static boolean parseDisabled;

	private void storeProxyElement(NonRootModelElement element) {
		// if the element is a subtype of data type
		// store the dt proxy, so that RGO resolution
		// may work
		if (element instanceof UserDataType_c) {
			DataType_c dt = DataType_c
					.getOneS_DTOnR17((UserDataType_c) element);
			fOldProxyMap.put(element, dt);
		} else if (element instanceof StructuredDataType_c) {
			DataType_c dt = DataType_c
					.getOneS_DTOnR17((StructuredDataType_c) element);
			fOldProxyMap.put(element, dt);
		} else if (element instanceof EnumerationDataType_c) {
			DataType_c dt = DataType_c
					.getOneS_DTOnR17((EnumerationDataType_c) element);
			fOldProxyMap.put(element, dt);
		}
	}

	public NonRootModelElement[] getExportedElements() {
		NonRootModelElement[] elements = fImporter.getLoadedInstances();
		ArrayList<NonRootModelElement> list = new ArrayList<NonRootModelElement>();
		for (int i = 0; i < elements.length; i++) {
			if (isTypePartOfExport(elements[i])) {
				list.add(elements[i]);
			}
		}
		// we need to remove any creation transition parents
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof CreationTransition_c) {
				CreationTransition_c ct = (CreationTransition_c) list.get(i);
				Transition_c tran = Transition_c.getOneSM_TXNOnR507(ct);
				list.remove(tran);
			}
		}
		return list.toArray(new NonRootModelElement[list.size()]);
	}

	public NonRootModelElement[] getCachedExportedElements() {
		return fExportedElements;
	}

	public void setImporter(IModelImport importer) {
		fImporter = importer;
	}
	
	public void runImporter(IModelImport importer, IProgressMonitor monitor) {
		fImporter = importer;
		try {
			fImporter.run(monitor);
		} catch (InvocationTargetException e) {
			CorePlugin.logError("Unable to import model stream.", e);
		} catch (InterruptedException e) {
			CorePlugin.logError("Unable to import model stream.", e);
		}
	}

	public void processFirstStep(IProgressMonitor monitor) {
		fExportedElements = getExportedElements();
		fActivities = getActivityElements();
		moveElementsToDestinationRoot(monitor);
	}

	public void processSecondStep(IProgressMonitor monitor) {
		callPasteOperations(monitor);
		setComponents();
		callResolutionOperations(monitor);
	}

	public IModelImport getImporter() {
		return fImporter;
	}

	public void setDestinationElement(NonRootModelElement destinationElement) {
		fDestinationElement = destinationElement;
	}

	public static void setParseDisabled(boolean value) {
		parseDisabled = value;
	}

	public void runParseOnImportedElements(TransactionManager manager,
			IProgressMonitor monitor) {
		if (!parseDisabled) {
			parseImportedElements(monitor);
		}
	}
}
