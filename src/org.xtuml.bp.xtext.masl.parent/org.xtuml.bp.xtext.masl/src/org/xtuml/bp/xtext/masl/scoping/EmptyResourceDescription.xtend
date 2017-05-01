package org.xtuml.bp.xtext.masl.scoping

import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.emf.common.util.URI

@Data
class EmptyResourceDescription implements IResourceDescription {
	
	val URI URI
	
	override getExportedObjects() {
		emptyList
	}
	
	override getImportedNames() {
		emptyList
	}
	
	override getReferenceDescriptions() {
		emptyList
	}
	
	override getExportedObjects(EClass type, QualifiedName name, boolean ignoreCase) {
		emptyList
	}
	
	override getExportedObjectsByObject(EObject object) {
		emptyList
	}
	
	override getExportedObjectsByType(EClass type) {
		emptyList
	}
	
	override isEmpty() {
		true
	}
}