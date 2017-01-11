package org.xtuml.bp.xtext.masl.ui.rename

import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtext.ui.refactoring.ui.IRenameElementContext

@Data
class XtumlRenameElementContext implements IRenameElementContext {
	
	val URI modTargetURI
	val URI intTargetURI
	
	val EClass modTargetEClass
	val EClass intTargetEClass

	new(List<Pair<URI, EClass>> targetURI2EClass) {
		val modEntry = targetURI2EClass.filter[key.fileExtension == 'mod'].head
		modTargetURI = modEntry?.key
		modTargetEClass = modEntry?.value
		val intEntry = targetURI2EClass.filter[key.fileExtension == 'int'].head
		intTargetURI = intEntry?.key
		intTargetEClass = intEntry?.value
	}

	def boolean isEmpty() {
		targetElementURI === null
	}

	override getTargetElementURI() {
		modTargetURI
	}
	
	override getTargetElementEClass() {
		modTargetEClass
	}
	
	override getContextResourceURI() {
		null
	}
	
	override getTriggeringEditor() {
		null
	}
	
	override getTriggeringEditorSelection() {
		null
	}
}