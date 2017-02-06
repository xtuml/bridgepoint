package org.xtuml.bp.ui.text.masl;
//====================================================================
//
// File:      MASLEditorInput.java
//
//====================================================================
//
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.ui.text.IModelElementEditorInputFactory;
import org.xtuml.bp.ui.text.ModelElementID;
import org.xtuml.bp.xtext.masl.ui.document.IXtumlElementEditorInput;

public class MASLEditorInput extends FileEditorInput implements IXtumlElementEditorInput {

	public final static String EDITOR_ID = "org.xtuml.bp.xtext.masl.MASL"; //$NON-NLS-1$
	public final static String FACTORY_ID = "org.xtuml.bp.ui.text.masl.factory"; //$NON-NLS-1$
	private ModelElementID modelElementID;
	
	
	public MASLEditorInput(ModelElementID modelElementID, IFile file) {
		super(file);
		this.modelElementID = modelElementID;
	}

    /**
	 * @param modelElementObject
	 * @return Instance of MASLEditorInput created from instanceof NonModelRootElement  
	 * @throws PartInitException
	 * @deprecated Should use Factory methods instead 
	 * @see IModelElementEditorInputFactory
	 */
	public static MASLEditorInput createInstance(Object modelElementObject) throws CoreException {
		MASLEditorInputFactory factory = (MASLEditorInputFactory)PlatformUI.getWorkbench().getElementFactory(FACTORY_ID);
		return (MASLEditorInput)factory.createInstance(modelElementObject);
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
    public static boolean isSupported(Object inputObject){
    	MASLEditorInputFactory factory = (MASLEditorInputFactory)PlatformUI.getWorkbench().getElementFactory(FACTORY_ID);
		return factory.isSupported(inputObject);
    }

    public NonRootModelElement getModelElement() {
		return modelElementID != null ? modelElementID.resolve() : null;
	}
}
