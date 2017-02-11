package org.xtuml.bp.xtext.masl.validation

import com.google.inject.Inject
import org.xtuml.bp.xtext.masl.MASLExtensions
import org.xtuml.bp.xtext.masl.masl.behavior.SimpleFeatureCall
import org.xtuml.bp.xtext.masl.masl.behavior.TerminatorActionCall
import org.xtuml.bp.xtext.masl.masl.structure.AbstractActionDefinition
import org.xtuml.bp.xtext.masl.masl.structure.AttributeDefinition
import org.xtuml.bp.xtext.masl.masl.structure.StateDefinition
import org.xtuml.bp.xtext.masl.masl.structure.Visibility
import org.xtuml.bp.xtext.masl.masl.structure.Visualized
import org.xtuml.bp.xtext.masl.scoping.ProjectScopeIndexProvider

import static extension org.eclipse.xtext.EcoreUtil2.*
import org.xtuml.bp.xtext.masl.masl.structure.DomainServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectServiceDefinition

class VisibilityProvider {
	
	@Inject extension MASLExtensions
	@Inject extension ProjectScopeIndexProvider
	
	def boolean isVisible(SimpleFeatureCall call) {
		val feature = call.feature
		val callersActionDefinition = call.getContainerOfType(AbstractActionDefinition)
		switch feature {
			Visualized case feature.visibility == Visibility.PUBLIC:
				true  
			DomainServiceDeclaration:
				feature.eContainer.domainName == callersActionDefinition.domain.name
			AttributeDefinition:
				feature.domainName == callersActionDefinition.domain.name
			ObjectServiceDeclaration: {
				switch callersActionDefinition {
					ObjectServiceDefinition:
						callersActionDefinition.getObject.getObjectDefinition(feature.index) == feature.eContainer
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
			TerminatorServiceDeclaration:
				feature.eContainer.eContainer.domainName == callersDomain.name
			default: 
				true
		}
	}
}