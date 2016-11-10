package org.xtuml.bp.xtext.masl.scoping

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy
import org.eclipse.xtext.util.IAcceptor
import org.xtuml.bp.xtext.masl.masl.structure.DomainDefinition
import org.xtuml.bp.xtext.masl.masl.structure.MaslModel
import org.xtuml.bp.xtext.masl.masl.types.BuiltinTypeDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectDefinition
import org.xtuml.bp.xtext.masl.masl.types.TypeDeclaration
import org.xtuml.bp.xtext.masl.masl.types.EnumerationTypeDefinition
import org.eclipse.xtext.resource.EObjectDescription
import org.xtuml.bp.xtext.masl.MASLExtensions
import com.google.inject.Inject
import org.eclipse.xtext.naming.QualifiedName
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorDefinition

class MASLResourceDescriptionStrategy extends DefaultResourceDescriptionStrategy {
	
	@Inject extension MASLExtensions
	
	override createEObjectDescriptions(EObject eObject, IAcceptor<IEObjectDescription> acceptor) {
		switch eObject {
			BuiltinTypeDeclaration:
				if(eObject.anonymous)
					return false
			TypeDeclaration: {
				val definition = eObject.definition			
				if (definition instanceof EnumerationTypeDefinition) {
					definition.enumerators.forEach[
						acceptor.accept(EObjectDescription.create(QualifiedName.create(domainName, name), it))
					]		
				}
			}
		}
		super.createEObjectDescriptions(eObject, acceptor)
		return eObject instanceof ObjectDefinition 
			|| eObject instanceof DomainDefinition 
			|| eObject instanceof MaslModel
			|| eObject instanceof TerminatorDefinition
	}
	
}