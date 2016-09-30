package org.xtuml.bp.xtext.masl.typesystem

import com.google.inject.Inject
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.xtuml.bp.xtext.masl.lib.MASLLibraryProvider
import org.xtuml.bp.xtext.masl.masl.AbstractCollectionTypeReference
import org.xtuml.bp.xtext.masl.masl.AbstractTypeReference
import org.xtuml.bp.xtext.masl.masl.ArrayTypeReference
import org.xtuml.bp.xtext.masl.masl.BagTypeReference
import org.xtuml.bp.xtext.masl.masl.EnumerationTypeDefinition
import org.xtuml.bp.xtext.masl.masl.InstanceTypeReference
import org.xtuml.bp.xtext.masl.masl.NamedTypeReference
import org.xtuml.bp.xtext.masl.masl.SequenceTypeReference
import org.xtuml.bp.xtext.masl.masl.SetTypeReference
import org.xtuml.bp.xtext.masl.masl.StructureTypeDefinition
import org.xtuml.bp.xtext.masl.masl.TerminatorTypeReference

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*

class MaslTypeExtensions {

	@Inject extension MASLLibraryProvider  
	
	def String asString(AbstractTypeReference it) {
		if(anonymous)
			'anonymous ' + asStringInternal
		else 
			asStringInternal	
	}
	
	private def String asStringInternal(AbstractTypeReference it) {
		val root = switch it {
			NamedTypeReference: 
				if(type.definition instanceof EnumerationTypeDefinition) 
					return 'enum ' + type.name
				else
					type
			InstanceTypeReference: 
				return 'instance ' + instance.name
			ArrayTypeReference case eResource==null: 
				return 'array of ' + elementType.asString
			TerminatorTypeReference:
				return 'terminator ' + terminator.name  
			default: it
		}
		val text = NodeModelUtils
				.getNode(root)
				.leafNodes
				.filter[!hidden]
				.map[text].join(' ')
		if(text.endsWith(';'))
			text.substring(0, text.length-1).trim
		else
			text
	}
	
	def boolean isInteger(AbstractTypeReference it) {
		if (it instanceof NamedTypeReference)
			getBuiltinTypeURI('integer') == type.URI
		else
			false
	}
	
	def boolean isString(AbstractTypeReference it) {
		if (it instanceof NamedTypeReference)
			getBuiltinTypeURI('string') == type.URI
		else
			false
	}
	
	def boolean isCollection(AbstractTypeReference it) {
		switch it {
			SequenceTypeReference, 
			SetTypeReference, 
			BagTypeReference:
				true
			default:
				false
		}
	}
	
	def boolean isStructure(AbstractTypeReference it) {
		if (it instanceof NamedTypeReference)
			type.definition instanceof StructureTypeDefinition
	}
	
	def AbstractTypeReference getComponentType(AbstractTypeReference it) {
		if(it instanceof AbstractCollectionTypeReference)
			elementType
		else
			it
	}
	
	def isAnonymous(AbstractTypeReference it) {
		switch it {
			AbstractCollectionTypeReference:
				anonymous
			InstanceTypeReference:
				anonymous
			NamedTypeReference:
				anonymous
			default:
				false
		}
	}
}
