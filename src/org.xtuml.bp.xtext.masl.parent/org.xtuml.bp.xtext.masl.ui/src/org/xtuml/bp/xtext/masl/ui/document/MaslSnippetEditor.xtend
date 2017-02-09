package org.xtuml.bp.xtext.masl.ui.document

import com.google.inject.Inject
import org.eclipse.jface.resource.JFaceResources
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Label
import org.eclipse.xtext.ui.IImageHelper
import org.eclipse.xtext.ui.editor.XtextEditor
import org.xtuml.bp.ui.text.AbstractModelElementPropertyEditorInput

class MaslSnippetEditor extends XtextEditor {
	
	@Inject IImageHelper imageHelper
	 
	override isSaveAsAllowed() {
		false
	}
	
	override createPartControl(Composite parent) {
		val input = editorInput
		if(input instanceof AbstractModelElementPropertyEditorInput) {
			val maslDocumentProvider = documentProvider as MaslDocumentProvider
			val header = maslDocumentProvider.getHeader(input)
			if(header != null) {
				val comp = new Composite(parent, SWT.NONE);
				comp.layout = new GridLayout => [
					numColumns = 1
				]
				val label = new Label(comp, SWT.WRAP)
				label.font = JFaceResources.getFont(JFaceResources.TEXT_FONT)
				label.text = header
				super.createPartControl(comp)
				comp.children.get(1).layoutData = new GridData => [
					horizontalAlignment = GridData.FILL
					verticalAlignment = GridData.FILL
					grabExcessHorizontalSpace = true
					grabExcessVerticalSpace = true
				]
			} else {
				super.createPartControl(parent)
			}
			val prefix = maslDocumentProvider.getPrefix(input)
			val editable = maslDocumentProvider.getEditable(input)
			sourceViewer.setVisibleRegion(prefix.length, editable.length)
		} else {
			super.createPartControl(parent)
		}
	}	
	
	
	override getDefaultImage() {
		if(editorInput instanceof AbstractModelElementPropertyEditorInput) 
			imageHelper.getImage(editorInput.imageDescriptor)
		else
			super.getDefaultImage()
	}
}
	