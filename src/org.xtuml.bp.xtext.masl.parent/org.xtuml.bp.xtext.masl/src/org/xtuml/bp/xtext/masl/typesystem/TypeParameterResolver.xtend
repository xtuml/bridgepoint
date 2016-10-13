package org.xtuml.bp.xtext.masl.typesystem

class TypeParameterResolver {
	
	def MaslType resolve(MaslType it, TypeParameterType typeParam, MaslType replacement) {
		switch it {
			BuiltinType,
			EnumType,
			InstanceType,
			TerminatorType:
				it
			TypeParameterType: 
				if(name == typeParam.name)
					replacement
				else
					it
			NamedType:
				new NamedType(name, type.resolve(typeParam, replacement), anonymous)
			ArrayType:
				new ArrayType(componentType.resolve(typeParam, replacement), anonymous)
			BagType:
				new BagType(componentType.resolve(typeParam, replacement), anonymous)
			RangeType:
				new RangeType(elementType.resolve(typeParam, replacement), anonymous)
			SequenceType:
				new SequenceType(componentType.resolve(typeParam, replacement), anonymous)
			SetType:
				new SetType(componentType.resolve(typeParam, replacement), anonymous)
			DictionaryType:
				new DictionaryType(keyType.resolve(typeParam, replacement), valueType.resolve(typeParam, replacement), anonymous)
			StructureType:
				new StructureType(structureType, components.map[new StructureComponent(name, type.resolve(typeParam, replacement))], anonymous)
		}
	}
	
	def boolean matchesParameterized(MaslType candidate, MaslType target) {
		val targetUnnamed = target.stripName
		val candidateUnnamed = candidate.stripName
		switch targetUnnamed {
			case candidateUnnamed:
				return true
			TypeParameterType:
				if(targetUnnamed.isEnumeration)
					return candidateUnnamed instanceof EnumType 
				else
					return true
			CollectionType:
				return candidateUnnamed.class == targetUnnamed.class 
					&& (candidateUnnamed as CollectionType).componentType.matchesParameterized(targetUnnamed.componentType)
			DictionaryType:
				return candidateUnnamed instanceof DictionaryType 
					&& (candidateUnnamed as DictionaryType).keyType.matchesParameterized(targetUnnamed.keyType)
					&& (candidateUnnamed as DictionaryType).valueType.matchesParameterized(targetUnnamed.valueType)
			TypeOfType:
				return candidateUnnamed instanceof TypeOfType 
					&& (candidateUnnamed as TypeOfType).type.matchesParameterized(targetUnnamed.type)
		}
	}
}