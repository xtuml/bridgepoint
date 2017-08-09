package org.xtuml.bp.xtext.masl.typesystem

import static org.xtuml.bp.xtext.masl.typesystem.BuiltinType.*

class MaslTypeConformanceComputer {

	def boolean isAssignableTo(MaslType source, MaslType target) {
		if (source == target) 
			return true;

		val sourcePrimitive = source.primitiveType
		val targetPrimitive = target.primitiveType
		if (source.anonymous || target.anonymous) {
			if (sourcePrimitive == targetPrimitive) 
				return true;

			if (sourcePrimitive == LONG_INTEGER && targetPrimitive == REAL) 
				return true;

			if (targetPrimitive instanceof StructureType 
				&& sourcePrimitive instanceof StructureType 
				&& areComponentsAssignable(
					sourcePrimitive as StructureType,
					targetPrimitive as StructureType)) 
				return true;

			if (targetPrimitive instanceof CollectionType 
				&& sourcePrimitive instanceof CollectionType 
				&& isAssignableTo(sourcePrimitive.componentType, targetPrimitive.componentType)) 
				return true;
				
			if (targetPrimitive instanceof RangeType 
				&& sourcePrimitive instanceof RangeType 
				&& isAssignableTo(sourcePrimitive.componentType, targetPrimitive.componentType)) 
				return true;
		}

		if (targetPrimitive instanceof StructureType) {
			val targetComponents = targetPrimitive.components
			if(targetComponents.size == 1 && isAssignableTo(source, targetComponents.head.type)) 
				return true;
		}
	
		if (targetPrimitive instanceof CollectionType 
			&& isAssignableTo(source, targetPrimitive.componentType)) 
			return true;

		if (targetPrimitive instanceof InstanceType && sourcePrimitive == ANY_TYPE)
			return true
			
		return false;
	}

	private def boolean areComponentsAssignable(StructureType source, StructureType target) {
		val sourceComponents = source.components
		val targetComponents = target.components
		if (sourceComponents.size != targetComponents.size)
			return false
		for (i : 0 ..< sourceComponents.size) {
			if (!sourceComponents.get(i).type.isAssignableTo(targetComponents.get(i).type))
				return false
		}
		return true
	}
}
	