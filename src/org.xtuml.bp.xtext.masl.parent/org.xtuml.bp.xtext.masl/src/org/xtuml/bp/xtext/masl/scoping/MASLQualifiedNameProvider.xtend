package org.xtuml.bp.xtext.masl.scoping

import com.google.inject.Inject
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName
import org.xtuml.bp.xtext.masl.MASLExtensions
import org.xtuml.bp.xtext.masl.masl.DomainFunctionDefinition
import org.xtuml.bp.xtext.masl.masl.DomainServiceDefinition
import org.xtuml.bp.xtext.masl.masl.ObjectFunctionDefinition
import org.xtuml.bp.xtext.masl.masl.ObjectServiceDefinition
import org.xtuml.bp.xtext.masl.masl.TerminatorFunctionDefinition
import org.xtuml.bp.xtext.masl.masl.TerminatorServiceDefinition
import org.xtuml.bp.xtext.masl.masl.StateDefinition
import org.xtuml.bp.xtext.masl.masl.DomainDefinition

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
	
	def protected qualifiedName(DomainFunctionDefinition it) {
		QualifiedName.create(domainName, name)
	}
	
	def protected qualifiedName(DomainServiceDefinition it) {
		QualifiedName.create(domainName, name)		
	}
	
	def protected qualifiedName(ObjectFunctionDefinition it) {
		QualifiedName.create(domainName, objectName, name)
	}
	
	def protected qualifiedName(ObjectServiceDefinition it) {
		QualifiedName.create(domainName, objectName, name)
	}
	
	def protected qualifiedName(StateDefinition it) {
		QualifiedName.create(domainName, objectName, name)
	}
	
	def protected qualifiedName(TerminatorFunctionDefinition it) {
		QualifiedName.create(domainName, terminatorName, name)
	}
	
	def protected qualifiedName(TerminatorServiceDefinition it) {
		QualifiedName.create(domainName, terminatorName, name)
	}
	
}