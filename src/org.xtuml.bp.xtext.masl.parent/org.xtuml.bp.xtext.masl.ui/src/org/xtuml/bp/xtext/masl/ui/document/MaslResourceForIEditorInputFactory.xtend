package org.xtuml.bp.xtext.masl.ui.document

import org.eclipse.ui.IEditorInput
import org.eclipse.xtext.ui.editor.model.ResourceForIEditorInputFactory

class MaslResourceForIEditorInputFactory extends ResourceForIEditorInputFactory {

	override createResource(IEditorInput editorInput) {
		if(editorInput instanceof IMaslSnippetEditorInput) 
			super.createResource(editorInput.file)
		else 
			super.createResource(editorInput)
	}
}