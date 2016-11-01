package org.xtuml.bp.xtext.masl.validation

import com.google.inject.Inject
import org.xtuml.bp.xtext.masl.MASLExtensions
import org.xtuml.bp.xtext.masl.masl.behavior.SimpleFeatureCall
import org.xtuml.bp.xtext.masl.masl.behavior.TerminatorActionCall
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
				feature.eContainer.domainName == callersActionDefinition.domain.name
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
	
	def boolean isVisible(TerminatorActionCall call) {
		val feature = call.terminatorAction
		val callersDomain = call.getContainerOfType(AbstractActionDefinition).domain
		switch feature {
			Visualized case feature.visibility == Visibility.PUBLIC:
				true  
			TerminatorFunctionDeclaration,
			TerminatorServiceDeclaration:
				feature.eContainer.eContainer.domainName == callersDomain.name
			default: 
				true
		}
	}
}