package org.xtuml.bp.xtext.masl.ui.document

import com.google.inject.Inject
import org.eclipse.ui.IEditorPart
import org.eclipse.xtext.resource.ILocationInFileProvider
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.ui.editor.XtextEditor
import org.xtuml.bp.xtext.masl.ui.internal.MaslActivator
import org.xtuml.bp.xtext.masl.ui.rename.XtumlToMaslMapper
import org.eclipse.ui.IEditorInput
import org.eclipse.ui.IWorkbenchPage

/**
 * Selects and scrolls to the MASL element that corresponds to the xtUML element from the editor input.
 * 
 * This has is not integrated into the Xtext editor directly, as it should also work if the respective
 * file is already open and there is no callback for such action when the client only calls 
 * {@link IWorkbenchPage#openEditor()}.
 */
class MaslElementRevelator {
	
	@Inject extension XtumlToMaslMapper
	@Inject extension IResourceDescription.Manager
	@Inject extension ILocationInFileProvider
	
	static def revealMaslElement(IEditorPart editor, IEditorInput editorInput) {
		if(editor instanceof XtextEditor && editorInput instanceof IXtumlElementEditorInput) {
			MaslActivator
				.instance
				.getInjector(MaslActivator.ORG_XTUML_BP_XTEXT_MASL_MASL)
				.getInstance(MaslElementRevelator)
				.revealMaslElement(editor as XtextEditor, editorInput as IXtumlElementEditorInput)
		}
	}
	
	def revealMaslElement(XtextEditor editor, IXtumlElementEditorInput editorInput) {
		val xtumlElement = editorInput.modelElement
		val maslQName = xtumlElement.maslQualifiedName
		val maslEClasses = xtumlElement.maslDefinitionEClasses
		editor.document.readOnly[ resource |
			val resourceDescription = resource.resourceDescription
			val maslElementURI = maslEClasses
				.map[resourceDescription.getExportedObjects(it, maslQName, false)]
				.flatten
				.head
				?.EObjectURI
			if(maslElementURI != null) {
				val element = resource.resourceSet.getEObject(maslElementURI, false)
				if(element != null) {
					val region = element.significantTextRegion
					editor.selectAndReveal(region.offset, region.length)
					editor.reveal(editor.document.getLineInformationOfOffset(region.offset).offset, 0)
				}
			}
			null
		]
	}
}