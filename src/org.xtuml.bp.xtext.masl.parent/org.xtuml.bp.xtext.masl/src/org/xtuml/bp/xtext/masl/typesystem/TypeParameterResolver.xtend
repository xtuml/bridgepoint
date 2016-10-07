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
				new ArrayType(elementType.resolve(typeParam, replacement), anonymous)
			BagType:
				new BagType(elementType.resolve(typeParam, replacement), anonymous)
			RangeType:
				new RangeType(elementType.resolve(typeParam, replacement), anonymous)
			SequenceType:
				new SequenceType(elementType.resolve(typeParam, replacement), anonymous)
			SetType:
				new SetType(elementType.resolve(typeParam, replacement), anonymous)
			DictionaryType:
				new DictionaryType(keyType.resolve(typeParam, replacement), elementType.resolve(typeParam, replacement), anonymous)
			StructureType:
				new StructureType(structureType, components.map[new StructureComponent(name, type.resolve(typeParam, replacement))], anonymous)
		}
	}
}