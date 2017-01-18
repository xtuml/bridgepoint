package org.xtuml.bp.xtext.masl.ui.document

import org.eclipse.ui.IEditorInput
import org.eclipse.xtext.ui.editor.model.ResourceForIEditorInputFactory
import org.xtuml.bp.ui.text.typedefinition.TypeDefinitionEditorInput

class MaslResourceForIEditorInputFactory extends ResourceForIEditorInputFactory {

	override createResource(IEditorInput editorInput) {
		if(editorInput instanceof TypeDefinitionEditorInput) 
			super.createResource(editorInput.file)
		else 
			super.createResource(editorInput)
	}
}