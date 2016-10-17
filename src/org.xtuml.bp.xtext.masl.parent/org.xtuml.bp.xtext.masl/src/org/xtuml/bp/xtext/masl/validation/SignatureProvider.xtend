package org.xtuml.bp.xtext.masl.validation

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.xtuml.bp.xtext.masl.masl.structure.Parameterized
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider

class SignatureProvider {
	
	@Inject extension MaslTypeProvider

	def getParametersAsString(EObject operation) {
		if (operation instanceof Parameterized)
			'(' + (operation as Parameterized).parameters.map[maslType.toString].join(',') + ')'
		else
			''
	}
}
