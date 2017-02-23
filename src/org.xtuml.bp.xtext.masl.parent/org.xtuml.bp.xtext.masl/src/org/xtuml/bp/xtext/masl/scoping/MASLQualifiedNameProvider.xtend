package org.xtuml.bp.xtext.masl.scoping

import com.google.inject.Inject
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName
import org.xtuml.bp.xtext.masl.MASLExtensions
import org.xtuml.bp.xtext.masl.masl.structure.DomainDefinition
import org.xtuml.bp.xtext.masl.masl.structure.StateDefinition
import org.xtuml.bp.xtext.masl.masl.structure.DomainServiceDefinition
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorServiceDefinition
import org.xtuml.bp.xtext.masl.masl.structure.ObjectServiceDefinition
import org.xtuml.bp.xtext.masl.masl.structure.RelationshipDefinition

/** 
 * The qualified names of some top-level elements depend on the domain x-ref.
 * This is not allowed in Xtext, so we use the node model (=parser trace) to 
 * access the string pointing to the domain, without resolving it to the domain 
 * definition element.
 */
class MASLQualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider{
	
	@Inject extension MASLExtensions  
	
	def protected qualifiedName(DomainDefinition it) {
		QualifiedName.create(name)
	}
	
	def protected qualifiedName(DomainServiceDefinition it) {
		QualifiedName.create(domainName, name)
	}
	
	def protected qualifiedName(ObjectServiceDefinition it) {
		QualifiedName.create(domainName, objectName, name)
	}
	
	def protected qualifiedName(StateDefinition it) {
		QualifiedName.create(domainName, objectName, name)
	}
	
	def protected qualifiedName(TerminatorServiceDefinition it) {
		QualifiedName.create(domainName, terminatorName, name)
	}

	def protected qualifiedName(RelationshipDefinition it) {
		QualifiedName.create(domainName, name)
	}

}
