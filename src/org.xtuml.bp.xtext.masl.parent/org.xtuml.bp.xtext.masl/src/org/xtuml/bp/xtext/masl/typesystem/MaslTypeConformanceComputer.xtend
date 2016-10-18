package org.xtuml.bp.xtext.masl.typesystem

import com.google.inject.Inject
import org.xtuml.bp.xtext.masl.MASLExtensions
import org.xtuml.bp.xtext.masl.masl.types.NamedTypeReference
import org.xtuml.bp.xtext.masl.scoping.ProjectScopeIndexProvider

import static org.xtuml.bp.xtext.masl.typesystem.BuiltinType.*

class MaslTypeConformanceComputer {

	@Inject extension MASLExtensions
	@Inject extension ProjectScopeIndexProvider

	def boolean isAssignableTo(MaslType source, MaslType target) {
		// covers 1) 
		// A named type is assignable to the same named type
		if (source == target)
			return true

		val sourcePrimitive = source.primitiveType
		val targetPrimitive = target.primitiveType 
		// covers 3) An named type is assignable to an anonymous type if they have the same primitive type
		if (source instanceof NamedType && target.anonymous)
			return sourcePrimitive == targetPrimitive

		if (source instanceof InstanceType) {
			if (target instanceof InstanceType) {
				val index = source.instance.index
				val definition = source.instance.getObjectDefinition(index)
				if (definition != null)
					return definition.getAllSupertypes(index).exists[it == target.instance.getObjectDefinition(index)]
				return false
			}
		}
		
		if (source.anonymous) {
			// covers 2) 
			// An anonymous type is assignable to a named type if they have the same primitive type
			if (target instanceof NamedTypeReference)
				return sourcePrimitive == targetPrimitive

			// covers 4) 
			// An anonymous integer type is assignable to any type with a primitive type of real
			if (sourcePrimitive == LONG_INTEGER && (target == REAL || target == BYTE))
				return true

			if (source == ANY_TYPE) 
				return target instanceof InstanceType

			// covers 5) 
			// An anonymous structure is assignable to a type with a primitive type of a structure
			// if the individual components are assignable. If the primitive type of the expression to be 
			// assigned is not a structure, it should be promoted to an anonymous structure with a single 
			// component of the original expression before trying this test.
			if (targetPrimitive instanceof StructureType) {
				if(sourcePrimitive == targetPrimitive || sourcePrimitive.wrapInStructure.isAssignableTo(targetPrimitive))
					return true
			}
				
			// covers 6) 
			// An anonymous collection (set, array, bag or sequence) is assignable to a type with a
			// primitive type of sequence if the contained elements are assignable. If the primitive
			// type of the expression to be assigned is not a sequence, it should be promoted to an 
			// anonymous sequence with a single element of the original expression before trying this test.
			if (targetPrimitive instanceof CollectionType) {
				if(sourcePrimitive == targetPrimitive 
					|| sourcePrimitive.wrapInSequence.isAssignableTo(targetPrimitive))
					return true
			}
		}
		if(sourcePrimitive instanceof CollectionType && targetPrimitive instanceof CollectionType)
			return sourcePrimitive.componentType.isAssignableTo(targetPrimitive.componentType)
				|| targetPrimitive.componentType.isAssignableTo(sourcePrimitive.componentType)
		return false
	}
	
	protected def wrapInStructure(MaslType source) {
		new StructureType(#[new StructureComponent(null, source.primitiveType)])
	}
	
	protected def wrapInSequence(MaslType source) {
		new SequenceType(source)
	}
}
