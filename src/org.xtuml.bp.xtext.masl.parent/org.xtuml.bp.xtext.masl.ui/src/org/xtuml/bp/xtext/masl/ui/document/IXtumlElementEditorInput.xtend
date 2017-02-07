package org.xtuml.bp.xtext.masl.ui.document

import org.eclipse.ui.IFileEditorInput
import org.xtuml.bp.core.common.NonRootModelElement
import org.eclipse.ui.IEditorInput

/**
 * An editor input that refers to an xtUML element.
 * 
 * The implementation should use an ID to store the reference to still be lightweight
 * as required by the {@link IEditorInput} contract.
 */
interface IXtumlElementEditorInput extends IFileEditorInput {
	
	def NonRootModelElement getModelElement()
	
}