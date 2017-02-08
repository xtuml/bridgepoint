package org.xtuml.bp.xtext.masl.ui.document

import org.eclipse.ui.IFileEditorInput
import org.xtuml.bp.core.common.NonRootModelElement
import org.eclipse.ui.IEditorInput
import org.eclipse.jface.text.IDocument
import org.eclipse.core.runtime.IProgressMonitor

/**
 * An editor input that refers to an xtUML element.
 * 
 * The implementation should use an ID to store the reference to still be lightweight
 * as required by the {@link IEditorInput} contract.
 */
interface IXtumlElementEditorInput extends IFileEditorInput {
	
	def NonRootModelElement getModelElement()
	
	def void doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite)

}
