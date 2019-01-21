//======================================================================
//
// File: org.xtuml.bp.ui.text.masl/MASLEditorInputFactory.java
//
//======================================================================
//
// This class serves as factory for editor input.
//
package org.xtuml.bp.ui.text.masl;

import java.util.ArrayList;
import java.util.List;

import org.xtuml.bp.core.ActionHome_c;
import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.BaseAttribute_c;
import org.xtuml.bp.core.CreationTransition_c;
import org.xtuml.bp.core.DerivedBaseAttribute_c;
import org.xtuml.bp.core.MooreActionHome_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.TransitionActionHome_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.ui.text.AbstractModelElementEditorInputFactory;

public class MASLEditorInputFactory extends AbstractModelElementEditorInputFactory {
	public static final String PLACEHOLDER_EXTENSION = "tpr"; //$NON-NLS-1$
	
	private static List<String> supportedList = null;

	protected List<String> getSupportedModelElementList() {
		if (supportedList == null) {
			supportedList = new ArrayList<String>();
			supportedList.add("org.xtuml.bp.core.RequiredOperation_c"); //$NON-NLS-1$
			supportedList.add("org.xtuml.bp.core.RequiredSignal_c"); //$NON-NLS-1$
			supportedList.add("org.xtuml.bp.core.ProvidedOperation_c"); //$NON-NLS-1$
			supportedList.add("org.xtuml.bp.core.ProvidedSignal_c"); //$NON-NLS-1$
			supportedList.add("org.xtuml.bp.core.Bridge_c"); //$NON-NLS-1$
			supportedList.add("org.xtuml.bp.core.Function_c"); //$NON-NLS-1$
			supportedList.add("org.xtuml.bp.core.Operation_c"); //$NON-NLS-1$
			supportedList.add("org.xtuml.bp.core.StateMachineState_c"); //$NON-NLS-1$
			supportedList.add("org.xtuml.bp.core.TerminatorService_c"); //$NON-NLS-1$
		}
		return supportedList;
	}
	
	public String getType() {
		return PLACEHOLDER_EXTENSION;
	}

	public Class getEditorInputClass() {
		return MASLEditorInput.class;
	}

	public NonRootModelElement getEditableModelElement(NonRootModelElement modelElement) {
		if (modelElement instanceof Action_c) {
			StateMachineState_c varStateMachineState = StateMachineState_c.getOneSM_STATEOnR511(
					MooreActionHome_c.getOneSM_MOAHOnR513(ActionHome_c.getOneSM_AHOnR514((Action_c) modelElement)));

			if (varStateMachineState != null) {
				return varStateMachineState;
			}
			Transition_c varTransition = Transition_c.getOneSM_TXNOnR530(
					TransitionActionHome_c.getOneSM_TAHOnR513(ActionHome_c.getOneSM_AHOnR514((Action_c) modelElement)));

			if (varTransition != null) {
				return varTransition;
			}
			CreationTransition_c varCreationTransition = CreationTransition_c
					.getOneSM_CRTXNOnR507(Transition_c.getOneSM_TXNOnR530(TransitionActionHome_c
							.getOneSM_TAHOnR513(ActionHome_c.getOneSM_AHOnR514((Action_c) modelElement))));

			if (varCreationTransition != null) {
				return varCreationTransition;
			}
		} else if (modelElement instanceof DerivedBaseAttribute_c) {
			Attribute_c varAttribute = Attribute_c
					.getOneO_ATTROnR106(BaseAttribute_c.getOneO_BATTROnR107((DerivedBaseAttribute_c) modelElement));

			if (varAttribute != null) {
				return varAttribute;
			}
		} else if (getSupportedModelElementList().contains(modelElement.getClass().getName())) {
			return modelElement;
		}

		return null;
	}

	public NonRootModelElement getRequiredModelElement(NonRootModelElement modelElement) {
		if (modelElement instanceof StateMachineState_c) {
			Action_c source = Action_c.getOneSM_ACTOnR514(ActionHome_c
					.getOneSM_AHOnR513(MooreActionHome_c.getOneSM_MOAHOnR511((StateMachineState_c) modelElement)));

			if (source != null) {
				return source;
			}
		} else if (modelElement instanceof Transition_c) {
			Action_c source = Action_c.getOneSM_ACTOnR514(ActionHome_c
					.getOneSM_AHOnR513(TransitionActionHome_c.getOneSM_TAHOnR530((Transition_c) modelElement)));

			if (source != null) {
				return source;
			}
		} else if (modelElement instanceof CreationTransition_c) {
			Action_c source = Action_c.getOneSM_ACTOnR514(ActionHome_c.getOneSM_AHOnR513(TransitionActionHome_c
					.getOneSM_TAHOnR530(Transition_c.getOneSM_TXNOnR507((CreationTransition_c) modelElement))));

			if (source != null) {
				return source;
			}
		} else if (modelElement instanceof Attribute_c) {
			DerivedBaseAttribute_c source = DerivedBaseAttribute_c
					.getOneO_DBATTROnR107(BaseAttribute_c.getOneO_BATTROnR106((Attribute_c) modelElement));

			if (source != null) {
				return source;
			}
		} else if (getSupportedModelElementList().contains(modelElement.getClass().getName())) {
			return modelElement;
		}

		return null;
	}

	static MASLEditorInputFactory defaultInstance;

	public static MASLEditorInputFactory getDefaultInstance() {
		if (defaultInstance == null) {
			defaultInstance = new MASLEditorInputFactory();
		}
		return defaultInstance;
	}
}
