package org.xtuml.bp.xtext.masl.ui.rename

import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.ui.refactoring.impl.DefaultDependentElementsCalculator

class MaslDependentElementsCalculator extends DefaultDependentElementsCalculator {
	
	override getDependentElementURIs(EObject target, IProgressMonitor monitor) {
		#[]
	}
}