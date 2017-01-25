package org.xtuml.bp.xtext.masl.ui.rename

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.ui.refactoring.impl.DefaultRenameStrategyProvider
import org.eclipse.xtext.ui.refactoring.ui.IRenameElementContext
import org.xtuml.bp.xtext.masl.MASLExtensions
import org.xtuml.bp.xtext.masl.masl.structure.Parameter
import org.xtuml.bp.xtext.masl.masl.structure.Parameterized
import org.xtuml.bp.xtext.masl.scoping.ProjectScopeIndexProvider

class MaslRenameStrategyProvider extends DefaultRenameStrategyProvider {
	
	@Inject extension MASLExtensions
	@Inject extension ProjectScopeIndexProvider
	
	override get(EObject target, IRenameElementContext renameElementContext) throws NoSuchStrategyException {
		val allTargets = newArrayList
		allTargets += target
		if(target instanceof Parameter) {
			val container = target.eContainer
			allTargets += container.getDefinitions(container.definitionClass, target.index)
				.filter(Parameterized)
				.map[parameters.findFirst[name == target.name]]
			allTargets += container.getDeclarations(container.declarationClass, target.index)
				.filter(Parameterized)
				.map[parameters.findFirst[name == target.name]]
		} else {
			allTargets += target.getDeclarations(target.declarationClass, target.index) 
			allTargets += target.getDefinitions(target.definitionClass, target.index)
		}
		if(renameElementContext instanceof XtumlRenameElementContext) {
			for(uri2EClass: renameElementContext.allTargetURI2EClass) {
				if(uri2EClass.key != renameElementContext.targetElementURI) {
					val intTarget = target.eResource.resourceSet.getEObject(uri2EClass.key, true)
					if (intTarget != null && intTarget.eClass == uri2EClass.value) {
						allTargets += intTarget
					}
				}
			}
		}
		val strategies = newArrayList
		allTargets.forEach [
			strategies += super.get(it, renameElementContext)
		]	
		new CompositeRenameStrategy(strategies)
	}
}