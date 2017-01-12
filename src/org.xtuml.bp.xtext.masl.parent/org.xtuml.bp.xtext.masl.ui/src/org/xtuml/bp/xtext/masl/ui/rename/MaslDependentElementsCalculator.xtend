package org.xtuml.bp.xtext.masl.ui.rename

import com.google.inject.Inject
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.ui.refactoring.impl.DefaultDependentElementsCalculator
import org.xtuml.bp.xtext.masl.MASLExtensions
import org.xtuml.bp.xtext.masl.scoping.ProjectScopeIndexProvider
import java.util.List
import org.eclipse.core.runtime.OperationCanceledException
import com.google.common.collect.Lists
import org.eclipse.emf.common.util.URI
import java.util.Iterator
import org.eclipse.core.runtime.SubMonitor
import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import org.eclipse.xtext.naming.IQualifiedNameProvider

class MaslDependentElementsCalculator extends DefaultDependentElementsCalculator {
	
	@Inject extension MASLExtensions
	@Inject extension ProjectScopeIndexProvider
	@Inject extension IQualifiedNameProvider
	
	override getDependentElementURIs(EObject target, IProgressMonitor monitor) {
		#[]
//		val allTargets = target.getDeclarations(target.declarationClass, target.index) 
//				+ target.getDefinitions(target.definitionClass, target.index)
//				+ #[target]
//		val progress = SubMonitor.convert(monitor, allTargets.size)
//		allTargets.map[doGetDependentElementURIs(it, progress)].flatten.toList
	}
	
	private def List<URI> doGetDependentElementURIs(EObject baseElement, IProgressMonitor monitor) {
		val elementURIs = newArrayList
		val baseName = baseElement.fullyQualifiedName
		for (var iterator = baseElement.getAllProperContents(false); iterator.hasNext();) {
			val childElement = iterator.next
			if (childElement.fullyQualifiedName?.startsWith(baseName)) {
				val childURI = childElement.URI
				if (childURI != null) 
					elementURIs += childURI
			}
		}
		monitor.worked(1)
		return elementURIs;
	}
	
}