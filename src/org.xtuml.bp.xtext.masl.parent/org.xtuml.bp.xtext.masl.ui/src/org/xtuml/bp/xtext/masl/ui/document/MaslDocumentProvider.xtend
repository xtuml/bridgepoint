package org.xtuml.bp.xtext.masl.ui.document

import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.jface.text.IDocument
import org.eclipse.swt.widgets.Display
import org.eclipse.ui.IEditorInput
import org.eclipse.xtext.ui.editor.model.XtextDocument
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider
import org.eclipse.xtext.util.StringInputStream
import org.xtuml.bp.core.Ooaofooa
import org.xtuml.bp.core.UserDataType_c
import org.xtuml.bp.core.common.NonRootModelElement
import org.xtuml.bp.ui.text.AbstractModelElementPropertyEditorInput

class MaslDocumentProvider extends XtextDocumentProvider {
	
	override getEncoding(Object element) {
		if(element instanceof AbstractModelElementPropertyEditorInput) 
			element.file.charset
		else
			super.getEncoding(element)
	}
	
	override protected setDocumentContent(IDocument document, IEditorInput editorInput, String encoding) throws CoreException {
		if(editorInput instanceof AbstractModelElementPropertyEditorInput) {
			super.setDocumentContent(document, editorInput.extendedInputStream, encoding)
			setDocumentResource(document as XtextDocument, editorInput, encoding)
			true
		} else {
			super.setDocumentContent(document, editorInput, encoding)
		} 
	}
	
	override isDeleted(Object element) {
		if(element instanceof AbstractModelElementPropertyEditorInput)
			return false 
		else 
			super.isDeleted(element)
	}
	
	def getExtendedInputStream(AbstractModelElementPropertyEditorInput editorInput) {
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
	
	def String getHeader(AbstractModelElementPropertyEditorInput editorInput) {
		val modelElement = editorInput.modelElement
		switch modelElement {
			UserDataType_c: null
			default: editorInput.prefix.trim
		}
	}
	
	def String getPrefix(AbstractModelElementPropertyEditorInput editorInput) {
		val modelElement = editorInput.modelElement
		switch modelElement {
			UserDataType_c: '''
				domain «editorInput.modelElement.domain» is
					type «modelElement.name»;
					type «modelElement.name» is 
			'''
			default: '''
				«Ooaofooa.Get_formatted_body(modelElement.modelRoot, modelElement, true)» 
			'''
		}
	}
	
	def String getEditable(AbstractModelElementPropertyEditorInput editorInput) {
		editorInput.propertyValue
	}
	
	def String getSuffix(AbstractModelElementPropertyEditorInput editorInput) {
		val modelElement = editorInput.modelElement
		switch modelElement {
			UserDataType_c: '''
				;
				end;
			'''
			default: ''
		}
	}
	
	override protected doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite) throws CoreException {
		if(element instanceof AbstractModelElementPropertyEditorInput) {
			// we have the workspace lock which is makes the transaction manager deadlock
			val prefixLength = element.prefix.length
			val editable = document.get(prefixLength, document.length - element.suffix.length - prefixLength).trim
			val newDefinition = if(element.modelElement instanceof UserDataType_c && editable.endsWith(';')) 
					editable.substring(0, editable.length - 1)
				else
					editable
			Display.^default.asyncExec [
	            element.propertyValue = newDefinition
			]
		} else {
			super.doSaveDocument(monitor, element, document, overwrite)
		}
	}
}
