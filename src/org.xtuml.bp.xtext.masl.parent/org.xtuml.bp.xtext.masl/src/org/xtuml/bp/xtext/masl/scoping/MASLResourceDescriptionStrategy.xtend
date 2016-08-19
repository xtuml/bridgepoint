package org.xtuml.bp.xtext.masl.scoping

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy
import org.eclipse.xtext.util.IAcceptor
import org.xtuml.bp.xtext.masl.masl.Enumerator

class MASLResourceDescriptionStrategy extends DefaultResourceDescriptionStrategy {
	
	override createEObjectDescriptions(EObject eObject, IAcceptor<IEObjectDescription> acceptor) {
		if(eObject instanceof Enumerator) {
			// workaround for enumerators being visible with their simple name 
			// until we have no type system in place 
			acceptor.accept(EObjectDescription.create(QualifiedName.create(eObject.name), eObject))
		}
		super.createEObjectDescriptions(eObject, acceptor)
	}
	
}