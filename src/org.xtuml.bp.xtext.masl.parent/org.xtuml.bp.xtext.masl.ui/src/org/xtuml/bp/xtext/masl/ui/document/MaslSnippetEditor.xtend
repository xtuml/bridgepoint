package org.xtuml.bp.xtext.masl.ui.document

import com.google.inject.Inject
import org.eclipse.swt.widgets.Composite
import org.eclipse.xtext.ui.IImageHelper
import org.eclipse.xtext.ui.editor.XtextEditor

class MaslSnippetEditor extends XtextEditor {
	
	@Inject IImageHelper imageHelper
	 
	override isSaveAsAllowed() {
		false
	}
	
	override createPartControl(Composite parent) {
		super.createPartControl(parent)
		val input = editorInput
		if(input instanceof IMaslSnippetEditorInput) {
			val prefix = (documentProvider as MaslDocumentProvider).getPrefix(input)
			val editable = (documentProvider as MaslDocumentProvider).getEditable(input)
			sourceViewer.setVisibleRegion(prefix.length, editable.length)
		}
	}	
	
	
	override getDefaultImage() {
		if(editorInput instanceof IMaslSnippetEditorInput) 
			imageHelper.getImage(editorInput.imageDescriptor)
		else
			super.getDefaultImage()
	}
}
	