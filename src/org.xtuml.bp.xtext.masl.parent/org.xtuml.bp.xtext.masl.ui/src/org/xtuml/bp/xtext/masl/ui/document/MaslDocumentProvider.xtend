package org.xtuml.bp.xtext.masl.ui.document

import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.jface.text.IDocument
import org.eclipse.ui.IEditorInput
import org.eclipse.xtext.ui.editor.model.XtextDocument
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider
import org.eclipse.xtext.util.StringInputStream
import org.xtuml.bp.core.CorePlugin
import org.xtuml.bp.core.Ooaofooa
import org.xtuml.bp.core.UserDataType_c
import org.xtuml.bp.core.common.NonRootModelElement
import org.xtuml.bp.core.util.TransactionUtil
import org.xtuml.bp.ui.text.typedefinition.TypeDefinitionEditorInput

class MaslDocumentProvider extends XtextDocumentProvider {
	
	override getEncoding(Object element) {
		if(element instanceof TypeDefinitionEditorInput) 
			element.file.charset
		else
			super.getEncoding(element)
	}
	
	override protected setDocumentContent(IDocument document, IEditorInput editorInput, String encoding) throws CoreException {
		if(editorInput instanceof TypeDefinitionEditorInput) {
			super.setDocumentContent(document, editorInput.extendedInputStream, encoding)
			setDocumentResource(document as XtextDocument, editorInput, encoding)
			true
		} else {
			super.setDocumentContent(document, editorInput, encoding)
		} 
	}
	
	override isDeleted(Object element) {
		if(element instanceof TypeDefinitionEditorInput)
			return false 
		else 
			super.isDeleted(element)
	}
	
	def getExtendedInputStream(TypeDefinitionEditorInput editorInput) {
		return new StringInputStream('''
			«editorInput.prefix»
			«editorInput.editable»
			«editorInput.suffix»
		''')
	}
	
	private def getDomain(NonRootModelElement context) {
		val fullPath = context.file.fullPath
		return if(fullPath.segmentCount > 2)
			fullPath.segment(2)
		else
			fullPath.segment(0) 
	}
	
	def getPrefix(TypeDefinitionEditorInput editorInput) '''
		domain «editorInput.modelElement.domain» is
			type __dummy__;
			type __dummy__ is 
	'''
	
	def getEditable(TypeDefinitionEditorInput editorInput) {
		(editorInput.modelElement as UserDataType_c).definition
	}
	
	def getSuffix(TypeDefinitionEditorInput editorInput) '''
		;
		end domain;
	'''
	
	override protected doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite) throws CoreException {
		if(element instanceof TypeDefinitionEditorInput) {
			val type = element.modelElement as UserDataType_c
			val prefixLength = element.prefix.length
			val newDefinition = document.get(prefixLength, document.length - element.suffix.length - prefixLength)
            document.set(newDefinition);
            element.doSaveDocument(monitor, element, document, overwrite);
		} else {
			super.doSaveDocument(monitor, element, document, overwrite)
		}
	}
}
