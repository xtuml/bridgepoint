package org.xtuml.bp.ui.text.asl;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.ui.text.AbstractModelElementPropertyEditorInput;
import org.xtuml.bp.ui.text.IModelElementEditorInputFactory;
import org.xtuml.bp.ui.text.ModelElementID;
import org.xtuml.bp.ui.text.ModelElementPropertyStorage;

public class ASLActivityEditorInput extends AbstractModelElementPropertyEditorInput {

	public final static String EDITOR_ID = "org.xtuml.bp.ui.text.asl.ASLActivityEditor"; //$NON-NLS-1$
	public final static String FACTORY_ID = "org.xtuml.bp.ui.text.asl.factory"; //$NON-NLS-1$

	/**
	 * 
	 * @param modelElement
	 * @param placeHolderFile
	 *            Requires place holder file to be created prior to creation of
	 *            editor input
	 * @throws PartInitException
	 */
	public ASLActivityEditorInput(ModelElementID modelElementID, IFile placeHolderFile) throws PartInitException {
		super(modelElementID, placeHolderFile);
	}

	/**
	 * @param modelElementObject
	 * @return Instance of ActivityEditorInput created from instanceof
	 *         NonModelRootElement
	 * @throws PartInitException
	 * @deprecated Should use Factory methods instead
	 * @see IModelElementEditorInputFactory
	 */
	public static ASLActivityEditorInput createInstance(Object modelElementObject) throws CoreException {
		IModelElementEditorInputFactory factory = (IModelElementEditorInputFactory) PlatformUI.getWorkbench()
				.getElementFactory(FACTORY_ID);
		return (ASLActivityEditorInput) factory.createInstance(modelElementObject);
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#getName()
	 */
	public String getName() {
		return super.getName();
	}

	/**
	 * @return the id of factory that is used to create instance of this editor
	 *         input.
	 * @see org.eclipse.ui.IPersistableElement#getFactoryId()
	 */
	public String getFactoryId() {
		return FACTORY_ID;
	}

	/**
	 * @return id of editor which supports this editor input.
	 */
	public String getEditorId() {
		return EDITOR_ID;
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
	public static boolean isSupported(Object inputObject) {
		ASLActivityEditorInputFactory factory = (ASLActivityEditorInputFactory) PlatformUI.getWorkbench().getElementFactory(FACTORY_ID);
		return factory.isSupported(inputObject);
	}

	/**
	 * @see org.xtuml.bp.ui.text.AbstractModelElementPropertyEditorInput#createStorage()
	 */
	protected ModelElementPropertyStorage createStorage() {
		return new ModelElementPropertyStorage(this, "Action_semantics_internal"); //$NON-NLS-1$
	}

	public void doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite) {
		if (element instanceof NonRootModelElement) {
			try {
				ModelRoot.disableChangeNotification();
				// Not super critical to grab the save semaphore here, but is
				// good practice to prevent potential save conflicts (especially if 
				// ASL is ever supported by verifier).
				Ooaofooa.beginSaveOperation();
				super.doSaveDocument(monitor, element, document, overwrite);
				Ooaofooa.endSaveOperation();
			} finally {
				ModelRoot.enableChangeNotification();
			}
		}
	}

}
