package org.xtuml.bp.xtext.masl.validation

import com.google.inject.Inject
import org.xtuml.bp.xtext.masl.MASLExtensions
import org.xtuml.bp.xtext.masl.masl.behavior.SimpleFeatureCall
import org.xtuml.bp.xtext.masl.masl.structure.AbstractActionDefinition
import org.xtuml.bp.xtext.masl.masl.structure.DomainFunctionDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.DomainServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectFunctionDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectFunctionDefinition
import org.xtuml.bp.xtext.masl.masl.structure.ObjectServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectServiceDefinition
import org.xtuml.bp.xtext.masl.masl.structure.StateDefinition
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorFunctionDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.Visibility
import org.xtuml.bp.xtext.masl.masl.structure.Visualized
import org.xtuml.bp.xtext.masl.scoping.ProjectScopeIndexProvider

import static extension org.eclipse.xtext.EcoreUtil2.*
import org.xtuml.bp.xtext.masl.masl.behavior.TerminatorOperationCall
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorServiceDefinition
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorFunctionDefinition

class VisibilityProvider {
	
	@Inject extension MASLExtensions
	@Inject extension ProjectScopeIndexProvider
	
	def boolean isVisible(SimpleFeatureCall call) {
		val feature = call.feature
		val callersActionDefinition = call.getContainerOfType(AbstractActionDefinition)
		switch feature {
			Visualized case feature.visibility == Visibility.PUBLIC:
				true  
			DomainFunctionDeclaration,
			DomainServiceDeclaration:
				feature.eContainer == callersActionDefinition.domain
			ObjectFunctionDeclaration,
			ObjectServiceDeclaration: {
				switch callersActionDefinition {
					ObjectServiceDefinition:
						callersActionDefinition.object.getObjectDefinition(feature.index) == feature.eContainer
					ObjectFunctionDefinition:
						callersActionDefinition.object.getObjectDefinition(feature.index) == feature.eContainer
					StateDefinition:
						callersActionDefinition.object.getObjectDefinition(feature.index) == feature.eContainer
					default: false
				}
			}
			default: 
				true
		}
	}
	
	def boolean isVisible(TerminatorOperationCall call) {
		val feature = call.terminatorOperation
		val callersActionDefinition = call.getContainerOfType(AbstractActionDefinition)
		switch feature {
			Visualized case feature.visibility == Visibility.PUBLIC:
				true  
			TerminatorFunctionDeclaration,
			TerminatorServiceDeclaration:
				switch callersActionDefinition {
					TerminatorServiceDefinition:
						feature.eContainer == callersActionDefinition.terminator
					TerminatorFunctionDefinition:
						feature.eContainer == callersActionDefinition.terminator
					default:
						false
				}
			default: 
				true
		}
	}
}