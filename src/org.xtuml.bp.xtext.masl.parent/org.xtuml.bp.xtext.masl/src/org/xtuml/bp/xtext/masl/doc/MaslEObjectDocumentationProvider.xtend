package org.xtuml.bp.xtext.masl.doc

import org.eclipse.xtext.documentation.IEObjectDocumentationProvider
import org.eclipse.emf.ecore.EObject
import com.google.inject.Inject
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider

class MaslEObjectDocumentationProvider implements IEObjectDocumentationProvider {

	@Inject extension MaslTypeProvider
	
	override getDocumentation(EObject o) {
		o.maslType.toString 
	}
	
	
}