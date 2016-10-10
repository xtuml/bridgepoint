package org.xtuml.bp.xtext.masl.doc

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider
import org.eclipse.xtext.serializer.ISerializer
import org.xtuml.bp.xtext.masl.masl.structure.Parameterized
import org.xtuml.bp.xtext.masl.typesystem.MaslType
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider

import static org.xtuml.bp.xtext.masl.typesystem.BuiltinType.*
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.naming.IQualifiedNameConverter

class MaslEObjectDocumentationProvider implements IEObjectDocumentationProvider {

	@Inject extension IQualifiedNameProvider
	@Inject extension IQualifiedNameConverter
	@Inject extension MaslTypeProvider
	@Inject extension ISerializer
	
	override getDocumentation(EObject o) {
		val qualifiedName = o.fullyQualifiedName
		val type = o.maslType
		return '''
			<dl>
				«IF qualifiedName != null»
					<dt>«toString(qualifiedName)»</dt>
				«ENDIF»
				«IF o instanceof Parameterized»
					<dt>Parameters:</dt>
					«FOR p: o.parameters»
						<dd>«p.name»: «p.mode» <em>«p.type.serialize»</em></dd>
					«ENDFOR»
				«ENDIF»
				«IF type != NO_TYPE && type != MISSING_TYPE»
					<dt>Type:</dt><dd><em>«type.toHtmlString»</em></dd>
				«ENDIF»
			</dl>
		'''
	}
	
	private def String toHtmlString(MaslType type) {
		type.toString.replace('\n','</br>').replace(' ', '&nbsp;')
	}
}