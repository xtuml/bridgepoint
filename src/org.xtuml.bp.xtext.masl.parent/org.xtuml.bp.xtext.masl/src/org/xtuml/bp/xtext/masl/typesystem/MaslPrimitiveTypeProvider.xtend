package org.xtuml.bp.xtext.masl.typesystem

import org.eclipse.emf.ecore.EObject
import org.xtuml.bp.xtext.masl.masl.AbstractCollectionTypeReference
import org.xtuml.bp.xtext.masl.masl.AbstractTypeDefinition
import org.xtuml.bp.xtext.masl.masl.AbstractTypeReference
import org.xtuml.bp.xtext.masl.masl.BuiltinTypeDeclaration
import org.xtuml.bp.xtext.masl.masl.ConstrainedArrayTypeReference
import org.xtuml.bp.xtext.masl.masl.ConstrainedTypeDefinition
import org.xtuml.bp.xtext.masl.masl.DeprecatedTypeReference
import org.xtuml.bp.xtext.masl.masl.EnumerationTypeDefinition
import org.xtuml.bp.xtext.masl.masl.InstanceTypeReference
import org.xtuml.bp.xtext.masl.masl.NamedTypeReference
import org.xtuml.bp.xtext.masl.masl.StructureTypeDefinition
import org.xtuml.bp.xtext.masl.masl.TerminatorTypeReference
import org.xtuml.bp.xtext.masl.masl.TypeDeclaration
import org.xtuml.bp.xtext.masl.masl.UnconstrainedArrayDefinition

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*

class MaslPrimitiveTypeProvider {
	
	def wrapInSequence(String primitiveType) {
		'sequence of ' + primitiveType
	}

	def wrapInStructure(String primitiveType) '''
		{
			«primitiveType»
		}
	'''

	def dispatch String getPrimitiveType(AbstractTypeReference reference) {
		switch reference {
			NamedTypeReference:
				reference.type.primitiveType
			AbstractCollectionTypeReference:
				'sequence of ' + reference.elementType.primitiveType
			ConstrainedArrayTypeReference:
				'sequence of ' + reference.elementType.primitiveType
			InstanceTypeReference:
				'instance ' + reference.instance.name
			DeprecatedTypeReference:
				reference.typeName
			TerminatorTypeReference:
				'terminator ' + reference.terminator.name
			default:
				throw new UnsupportedOperationException('Missing primitive type implementation for ' + reference.eClass.name)
		}
	}
	
	def dispatch String getPrimitiveType(TypeDeclaration declaration) {
		switch declaration {
			BuiltinTypeDeclaration:
				'builtin ' + declaration.name
			default:
				declaration.definition.primitiveType
		}
	}
	
	def dispatch String getPrimitiveType(AbstractTypeDefinition definition) {
		switch definition {
			AbstractTypeReference:
				definition.primitiveType
			ConstrainedTypeDefinition:
				definition.type.primitiveType
			EnumerationTypeDefinition:
				'enum' + definition.URI.fragment  // enums are their own primitive types
			StructureTypeDefinition:
				'''
					{
						«FOR c: definition.components»
							«c.type.primitiveType»
						«ENDFOR»
					}
				'''
			UnconstrainedArrayDefinition:
				'sequence of ' + definition.elementType.primitiveType
			default:
				throw new UnsupportedOperationException('Missing primitive type implementation for ' + definition.eClass.name)
		}
	}
	
	def dispatch String getPrimitiveType(EObject structuralComponent) {
		throw new UnsupportedOperationException('Missing primitive type implementation for ' + structuralComponent.eClass.name)
	}	
}
