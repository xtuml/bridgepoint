//======================================================================
//
// File: org.xtuml.bp.ui.text.masl/MASLEditorInputFactory.java
//
//======================================================================
//
// This class serves as factory for editor input.
//
package org.xtuml.bp.ui.text.masl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInputFactory;
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
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.ui.text.ModelAdapter;
import org.xtuml.bp.ui.text.ModelElementID;
import org.xtuml.bp.ui.text.TextPlugin;

public class MASLEditorInputFactory extends FileEditorInputFactory {
	private static List supportedList = null;

	protected List getSupportedModelElementList() {
		if (supportedList == null) {
			supportedList = new Vector();
			supportedList.add("org.xtuml.bp.core.RequiredOperation_c"); //$NON-NLS-1$
			supportedList.add("org.xtuml.bp.core.RequiredSignal_c"); //$NON-NLS-1$
			supportedList.add("org.xtuml.bp.core.ProvidedOperation_c"); //$NON-NLS-1$
			supportedList.add("org.xtuml.bp.core.ProvidedSignal_c"); //$NON-NLS-1$
			supportedList.add("org.xtuml.bp.core.Bridge_c"); //$NON-NLS-1$
			supportedList.add("org.xtuml.bp.core.Function_c"); //$NON-NLS-1$
			supportedList.add("org.xtuml.bp.core.Operation_c"); //$NON-NLS-1$
			supportedList.add("org.xtuml.bp.core.StateMachineState_c"); //$NON-NLS-1$
		}
		return supportedList;
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

	/**
	 * @return true if - Argument is a model element whose type or type of its
	 *         related model element is present in supported list. - Argument is
	 *         instance of IFile representing an existing place holder file -
	 *         Argument is model element id which can used to resolve a model
	 *         element.
	 * 
	 *         present in the list returned by getSupportedModelElementList()
	 * @see getSupportedModelElementRelatedTo(NonRootModelElement)
	 * @see org.xtuml.bp.core.ui.IModelElementEditorInputFactory#isSupported(java.lang.Object)
	 */
	public boolean isSupported(Object inputObject) {
		if (inputObject instanceof NonRootModelElement) {
			NonRootModelElement modelElement = (NonRootModelElement) inputObject;

			List supportedModelElementList = getSupportedModelElementList();
			boolean supported = supportedModelElementList.contains(modelElement.getClass().getName());
			if (!supported) {
				modelElement = getEditableModelElement(modelElement);
				if (modelElement == null) {
					supported = false;
				} else {
					supported = supportedModelElementList.contains(modelElement.getClass().getName());
				}
			} else if (getRequiredModelElement(modelElement) == null) {
				supported = false;
			}
			return supported;
		} else if (inputObject instanceof IFile) {
			return true;
		} else if (inputObject instanceof ModelElementID) {
			// Since we keep a reference of the main model element only, rather
			// than the related model element, ModelElementID is created for
			// main classes only and hence we do not need to keep a list of
			// supported model elements.
			List supportedModelElementList = getSupportedModelElementList();
			return supportedModelElementList.contains(((ModelElementID) inputObject).getType());
		}
		return false;
	}

	/**
	 * Creates an instance of IEditorInput from model element, model element id
	 * or place holder file.
	 * 
	 * @see org.xtuml.bp.core.ui.IModelElementEditorInputFactory#createInstance(java.lang.Object)
	 */
	public IEditorInput createInstance(Object inputObject) throws CoreException {

		if (inputObject == null) {
			return null;
		}

		if (!isSupported(inputObject)) {
			String type = inputObject.getClass().getName();
			if (inputObject instanceof ModelElementID) {
				type = ((ModelElementID) inputObject).getType();
			}

			throw new PartInitException("Does not support input object of type " + type);
		}

		NonRootModelElement modelElement = null;
		ModelElementID modelElementID = null;
		IFile file = null;

		// If editor input is created from model element
		if (inputObject instanceof NonRootModelElement) {
			modelElement = (NonRootModelElement) inputObject;
			modelElementID = ModelAdapter
					.getModelElementAdapter(modelElement)
					.createModelElementID(modelElement);
		} else if (inputObject instanceof IFile) {
			file = (IFile) inputObject;
		} else if (inputObject instanceof ModelElementID) {
			modelElementID = (ModelElementID) inputObject;
			modelElement = modelElementID.resolve();
		}

		if (file == null && modelElement != null) {
			file = getFileForModelElement(modelElement);
		}

		IEditorInput input = new MASLEditorInput(modelElementID, file);

		return input;
	}

    private IFile getFileForModelElement(NonRootModelElement modelElement) throws CoreException {
        IFile file = null;

        NonRootModelElement dialectObj = modelElement;
        if (dialectObj instanceof StateMachineState_c) {
            StateMachineState_c state = (StateMachineState_c) dialectObj;
            Action_c action = Action_c.getOneSM_ACTOnR514(ActionHome_c
                    .getOneSM_AHOnR513((MooreActionHome_c.getOneSM_MOAHOnR511(state))));
            if (action != null) {
                dialectObj = action;
            }
        }

        // get the dialect
        try {
            Method getDialectMethod = dialectObj.getClass().getMethod("getDialect");
            int dialect = (int) getDialectMethod.invoke(dialectObj);

            PersistableModelComponent pmc = dialectObj.getPersistableComponent();
            file = pmc.getActionFile( dialect );

        } catch ( NoSuchMethodException e ) {
            TextPlugin.logError( "Could not get element dialect", e );
        } catch ( NullPointerException e ) {
            TextPlugin.logError( "Could not get element dialect", e );
        } catch ( SecurityException e ) {
            TextPlugin.logError( "Could not get element dialect", e );
        } catch ( IllegalAccessException e ) {
            TextPlugin.logError( "Could not get element dialect", e );
        } catch ( IllegalArgumentException e ) {
            TextPlugin.logError( "Could not get element dialect", e );
        } catch ( InvocationTargetException e ) {
            TextPlugin.logError( "Could not get element dialect", e );
        } catch ( ExceptionInInitializerError e ) {
            TextPlugin.logError( "Could not get element dialect", e );
        }

        return file;
    }

}
