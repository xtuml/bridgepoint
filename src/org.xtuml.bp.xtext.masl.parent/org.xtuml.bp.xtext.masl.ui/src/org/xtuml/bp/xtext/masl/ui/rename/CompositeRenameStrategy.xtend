package org.xtuml.bp.xtext.masl.ui.rename

import java.util.List
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.ui.refactoring.IRefactoringUpdateAcceptor
import org.eclipse.xtext.ui.refactoring.IRenameStrategy

@FinalFieldsConstructor
class CompositeRenameStrategy implements IRenameStrategy {
	
	val List<IRenameStrategy> strategies
	
	override applyDeclarationChange(String newName, ResourceSet resourceSet) {
		strategies.forEach[
			applyDeclarationChange(newName, resourceSet)
		]
	}
	
	override createDeclarationUpdates(String newName, ResourceSet resourceSet, IRefactoringUpdateAcceptor updateAcceptor) {
		strategies.forEach[
			createDeclarationUpdates(newName, resourceSet, updateAcceptor)
		]
	}
	
	override getOriginalName() {
		strategies.head.originalName
	}
	
	override revertDeclarationChange(ResourceSet resourceSet) {
		strategies.forEach[
			revertDeclarationChange(resourceSet)
		]
	}
	
	override validateNewName(String newName) {
		strategies.head.validateNewName(newName)
	}
}