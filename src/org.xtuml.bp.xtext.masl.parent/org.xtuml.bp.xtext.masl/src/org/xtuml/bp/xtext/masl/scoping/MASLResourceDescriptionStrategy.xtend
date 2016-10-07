package org.xtuml.bp.xtext.masl.scoping

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy
import org.eclipse.xtext.util.IAcceptor
import org.xtuml.bp.xtext.masl.masl.structure.DomainDefinition
import org.xtuml.bp.xtext.masl.masl.structure.MaslModel
import org.xtuml.bp.xtext.masl.masl.types.BuiltinTypeDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectDefinition

class MASLResourceDescriptionStrategy extends DefaultResourceDescriptionStrategy {
	
	override createEObjectDescriptions(EObject eObject, IAcceptor<IEObjectDescription> acceptor) {
		if(eObject instanceof BuiltinTypeDeclaration)
			if(eObject.anonymous)
				return false
		super.createEObjectDescriptions(eObject, acceptor)
		return eObject instanceof ObjectDefinition 
			|| eObject instanceof DomainDefinition 
			|| eObject instanceof MaslModel
	}
	
}