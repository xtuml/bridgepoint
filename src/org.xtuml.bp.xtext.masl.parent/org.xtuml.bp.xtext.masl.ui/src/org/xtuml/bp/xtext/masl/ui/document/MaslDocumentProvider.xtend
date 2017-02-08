package org.xtuml.bp.xtext.masl.ui.document

import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.jface.text.IDocument
import org.eclipse.ui.IEditorInput
import org.eclipse.xtext.ui.editor.model.XtextDocument
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider
import org.eclipse.xtext.util.StringInputStream
import org.xtuml.bp.core.Ooaofooa
import org.xtuml.bp.core.UserDataType_c
import org.xtuml.bp.core.common.NonRootModelElement
import org.eclipse.swt.widgets.Display

class MaslDocumentProvider extends XtextDocumentProvider {
	
	override getEncoding(Object element) {
		if(element instanceof IMaslSnippetEditorInput) 
			element.file.charset
		else
			super.getEncoding(element)
	}
	
	override protected setDocumentContent(IDocument document, IEditorInput editorInput, String encoding) throws CoreException {
		if(editorInput instanceof IMaslSnippetEditorInput) {
			super.setDocumentContent(document, editorInput.extendedInputStream, encoding)
			setDocumentResource(document as XtextDocument, editorInput, encoding)
			true
		} else {
			super.setDocumentContent(document, editorInput, encoding)
		} 
	}
	
	override isDeleted(Object element) {
		if(element instanceof IMaslSnippetEditorInput)
			return false 
		else 
			super.isDeleted(element)
	}
	
	def getExtendedInputStream(IMaslSnippetEditorInput editorInput) {
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
	
	def String getHeader(IMaslSnippetEditorInput editorInput) {
		val modelElement = editorInput.modelElement
		switch modelElement {
			UserDataType_c: null
			default: editorInput.prefix.trim
		}
	}
	
	def String getPrefix(IMaslSnippetEditorInput editorInput) {
		val modelElement = editorInput.modelElement
		switch modelElement {
			UserDataType_c: '''
				domain «editorInput.modelElement.domain» is
					type __dummy__;
					type __dummy__ is 
			'''
			default: '''
				«Ooaofooa.Get_formatted_body(modelElement.modelRoot, modelElement, true)» 
			'''
		}
	}
	
	def String getEditable(IMaslSnippetEditorInput editorInput) {
		editorInput.propertyValue
	}
	
	def String getSuffix(IMaslSnippetEditorInput editorInput) {
		val modelElement = editorInput.modelElement
		switch modelElement {
			UserDataType_c: '''
				;
				end domain;
			'''
			default: ''
		}
	}
	
	override protected doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite) throws CoreException {
		if(element instanceof IMaslSnippetEditorInput) {
			// we have the workspace lock which is makes the transaction manager deadlock
			Display.^default.asyncExec [
				val prefixLength = element.prefix.length
				val newDefinition = document.get(prefixLength, document.length - element.suffix.length - prefixLength)
	            element.propertyValue = newDefinition
			]
		} else {
			super.doSaveDocument(monitor, element, document, overwrite)
		}
	}
}
