//======================================================================
//
// File: org.xtuml.bp.ui.text.masl/MASLEditorInputFactory.java
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//======================================================================
//
// This class serves as factory for editor input.
//
package org.xtuml.bp.ui.text.masl;

import java.util.List;
import java.util.Vector;

import org.xtuml.bp.core.*;
import org.xtuml.bp.core.common.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInputFactory;
import org.xtuml.bp.ui.text.ModelElementID;
import org.xtuml.bp.core.inspector.IModelClassInspector;
import org.xtuml.bp.core.inspector.ModelInspector;

public class MASLEditorInputFactory extends FileEditorInputFactory{
    private static List supportedList = null;

    protected List getSupportedModelElementList() {
        if(supportedList == null){
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
        if (modelElement instanceof Action_c){
            StateMachineState_c varStateMachineState = StateMachineState_c.getOneSM_STATEOnR511(MooreActionHome_c.getOneSM_MOAHOnR513(ActionHome_c.getOneSM_AHOnR514((Action_c)modelElement)));

            if (varStateMachineState != null){
                return varStateMachineState;
            }
            Transition_c varTransition = Transition_c.getOneSM_TXNOnR530(TransitionActionHome_c.getOneSM_TAHOnR513(ActionHome_c.getOneSM_AHOnR514((Action_c)modelElement)));

            if (varTransition != null){
                return varTransition;
            }
            CreationTransition_c varCreationTransition = CreationTransition_c.getOneSM_CRTXNOnR507(Transition_c.getOneSM_TXNOnR530(TransitionActionHome_c.getOneSM_TAHOnR513(ActionHome_c.getOneSM_AHOnR514((Action_c)modelElement))));

            if (varCreationTransition != null){
                return varCreationTransition;
            }
        }else if (modelElement instanceof DerivedBaseAttribute_c){
            Attribute_c varAttribute = Attribute_c.getOneO_ATTROnR106(BaseAttribute_c.getOneO_BATTROnR107((DerivedBaseAttribute_c)modelElement));

            if (varAttribute != null){
                return varAttribute;
            }
        }else if(getSupportedModelElementList().contains(modelElement.getClass().getName())){
            return modelElement;
        }

        return null;
    }

    public NonRootModelElement getRequiredModelElement(NonRootModelElement modelElement){
        if (modelElement instanceof StateMachineState_c){
            Action_c source = 
                Action_c.getOneSM_ACTOnR514(
                        ActionHome_c.getOneSM_AHOnR513(
                            MooreActionHome_c.getOneSM_MOAHOnR511(
                                (StateMachineState_c)modelElement)
                            )
                        )
                ;




            if (source != null){
                return source;
            }
        }else if (modelElement instanceof Transition_c){
            Action_c source = 
                Action_c.getOneSM_ACTOnR514(
                        ActionHome_c.getOneSM_AHOnR513(
                            TransitionActionHome_c.getOneSM_TAHOnR530(
                                (Transition_c)modelElement)
                            )
                        )
                ;




            if (source != null){
                return source;
            }
        }else if (modelElement instanceof CreationTransition_c){
            Action_c source = 
                Action_c.getOneSM_ACTOnR514(
                        ActionHome_c.getOneSM_AHOnR513(
                            TransitionActionHome_c.getOneSM_TAHOnR530(
                                Transition_c.getOneSM_TXNOnR507(
                                    (CreationTransition_c)modelElement)
                                )
                            )
                        )
                ;




            if (source != null){
                return source;
            }
        }else if (modelElement instanceof Attribute_c){
            DerivedBaseAttribute_c source = 
                DerivedBaseAttribute_c.getOneO_DBATTROnR107(
                        BaseAttribute_c.getOneO_BATTROnR106(
                            (Attribute_c)modelElement)
                        )
                ;




            if (source != null){
                return source;
            }
        }else if(getSupportedModelElementList().contains(modelElement.getClass().getName())){
            return modelElement;
        }

        return null;
    }

    static MASLEditorInputFactory defaultInstance;
    public static MASLEditorInputFactory getDefaultInstance(){
        if(defaultInstance == null){
            defaultInstance = new MASLEditorInputFactory();
        }
        return defaultInstance;
    }

    /**
     * @return true if 
     * - Argument is a model element whose type or type of its related model 
     *   element is present in supported list.
     * - Argument is instance of IFile representing an existing place holder 
     *   file
     * - Argument is model element id which can used to resolve a model element.
     * 
     * present in the list returned by getSupportedModelElementList()
     * @see getSupportedModelElementRelatedTo(NonRootModelElement)
     * @see org.xtuml.bp.core.ui.IModelElementEditorInputFactory#isSupported(java.lang.Object)
     */
    public boolean isSupported(Object inputObject){
        if(inputObject instanceof NonRootModelElement){
            NonRootModelElement modelElement = (NonRootModelElement)inputObject;

            List  supportedModelElementList = getSupportedModelElementList();
            boolean supported = supportedModelElementList.contains(modelElement.getClass().getName());
            if(!supported){
                modelElement = getEditableModelElement(modelElement);
                if(modelElement == null){
                    supported = false;
                }else{
                    supported = supportedModelElementList.contains(modelElement.getClass().getName());
                }
            }else if(getRequiredModelElement(modelElement) == null){
                supported = false;
            }
            return supported;
        }else if(inputObject instanceof IFile){
            return true;
        }else if(inputObject instanceof ModelElementID){
            // Since we keep a reference of the main model element only, rather 
            // than the related model element, ModelElementID is created for
            // main classes only and hence we do not need to keep a list of
            // supported model elements.
            List supportedModelElementList = getSupportedModelElementList();
            return supportedModelElementList.contains(((ModelElementID)inputObject).getType());
        }
        return false;
    }

    /**
     * Creates an instance of IEditorInput from model element, model element id 
     * or place holder file. 
     * @see org.xtuml.bp.core.ui.IModelElementEditorInputFactory#createInstance(java.lang.Object)
     */
    public IEditorInput createInstance(Object inputObject) throws CoreException {

        if ( inputObject == null ) {
            return null;
        }

        if (!isSupported(inputObject)) {
            String type = inputObject.getClass().getName();
            if(inputObject instanceof ModelElementID){
                type = ((ModelElementID)inputObject).getType();
            }

            throw new PartInitException(
                    "Does not support input object of type " + type);
        }

        NonRootModelElement modelElement = null;
        IFile file = null;

        // If editor input is created from model element
        if(inputObject instanceof NonRootModelElement){
            modelElement = (NonRootModelElement)inputObject;
        }
        else if(inputObject instanceof IFile) {
            file = (IFile)inputObject;
        }else if(inputObject instanceof ModelElementID){
            ModelElementID modelElementID = (ModelElementID)inputObject;
            modelElement = modelElementID.resolve();
        }

        if ( file == null && modelElement != null ) {
            file = getFileForModelElement( modelElement );
        }

        IEditorInput input = new MASLEditorInput(file);

        return input;
    }

    private IFile getFileForModelElement( NonRootModelElement modelElement ) throws CoreException {
        IFile file;

        // get name
        String name;
        if ( ( modelElement instanceof RequiredOperation_c || modelElement instanceof RequiredSignal_c || 
               modelElement instanceof ProvidedOperation_c || modelElement instanceof ProvidedSignal_c ) &&
                getParent(getParent(modelElement)) instanceof Port_c ) {
            name = getParent(getParent(modelElement)).getName() + "_" + modelElement.getName();
        }
        else if ( modelElement instanceof Bridge_c && getParent(modelElement) instanceof ExternalEntity_c ) {
            name = getParent(modelElement).getName() + "_" + modelElement.getName();
        }
        else {
            name = modelElement.getName();
        }
        IPath path = modelElement.getFile().getFullPath();
        IWorkspaceRoot workspace = ResourcesPlugin.getWorkspace().getRoot();
        IProject project = workspace.getProject( path.segment(0) );
        IFolder folder = project.getFolder( path.removeFirstSegments(1).removeLastSegments(1).toOSString() );
        file = folder.getFile( name + ".masl" );
        if ( !file.exists() ) {
            byte[] bytes = "".getBytes();
            InputStream source = new ByteArrayInputStream(bytes);
            file.create(source, IResource.NONE, null);
        }

        return file;
    }

    private NonRootModelElement getParent( NonRootModelElement element ) {
        if ( element == null ) return null;
        ModelInspector inspector = new ModelInspector();
        IModelClassInspector elementInspector = inspector.getInspector(element.getClass());
        return (NonRootModelElement)elementInspector.getParent(element);
    }

}
