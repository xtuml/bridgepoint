package org.xtuml.bp.xtext.masl.typesystem

import com.google.inject.Inject
import org.xtuml.bp.xtext.masl.masl.AbstractTypeReference
import org.xtuml.bp.xtext.masl.masl.NamedTypeReference
import org.xtuml.bp.xtext.masl.masl.impl.NamedTypeReferenceImpl
import org.xtuml.bp.xtext.masl.masl.InstanceTypeReference
import org.xtuml.bp.xtext.masl.MASLExtensions
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider

class MaslTypeConformanceComputer {
	
	@Inject extension MASLExtensions
	@Inject extension MaslTypeExtensions
	@Inject extension MaslPrimitiveTypeProvider 
	@Inject extension ResourceDescriptionsProvider 
	
	def isAssignableTo(AbstractTypeReference source, AbstractTypeReference target) {
		if(source instanceof NamedTypeReference) {
			if(source.primitiveType == 'builtin any_type')
				return true
			if(target instanceof NamedTypeReferenceImpl) 
				return source.type == target.type
			if(target.anonymous)
				return source.primitiveType == target.primitiveType
		}
		if(source instanceof InstanceTypeReference) {
			if(target instanceof InstanceTypeReference) {
				val index = source.instance.eResource.resourceDescriptions
				val definition = source.instance.getObjectDefinition(index)
				if(definition != null) 
					return definition.getAllSupertypes(index).exists[it == target.instance.getObjectDefinition(index)]
				return 
					false
			}
		}
		if(source.anonymous) {
			if(target instanceof NamedTypeReference)
				return source.primitiveType == target.primitiveType
			if(source.isInteger) 
				return target.primitiveType == 'builtin real'
			if(source.isCollection)
				return source.primitiveType == target.primitiveType
			if(source.isStructure)
				return source.primitiveType == target.primitiveType
			if(source.primitiveType.wrapInSequence == target.primitiveType)
				return true
			if(source.primitiveType.wrapInStructure == target.primitiveType)
				return true
		}
		return false
	}
}