package org.xtuml.bp.xtext.masl.typesystem

import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.xtuml.bp.xtext.masl.masl.structure.ObjectDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorDefinition
import org.xtuml.bp.xtext.masl.masl.types.EnumerationTypeDefinition
import org.xtuml.bp.xtext.masl.masl.types.StructureTypeDefinition
import org.xtuml.bp.xtext.masl.masl.types.TypeDeclaration

/**
 * Dealing with EMF objects is tedious, so we define our own types
 */
interface MaslType {

	def MaslType getPrimitiveType()

	def boolean isAnonymous()
	
	def MaslType getComponentType()
	
	def MaslType stripName()
}

@FinalFieldsConstructor
@Accessors(PUBLIC_GETTER)
abstract class AbstractMaslType implements MaslType {

	transient val boolean anonymous

	new() {
		anonymous = false
	}

	override MaslType getComponentType() {
		this
	}
	
	protected def prefix() {
		(if (anonymous) 'anonymous ' else '') 
	}
	
	override stripName() {
		this
	}
	
	override abstract toString()	
}

@Data
@FinalFieldsConstructor
class BuiltinType extends AbstractMaslType {
	val String name

	public static val CHARACTER = new BuiltinType('character')
	public static val STRING = new BuiltinType('string')
	public static val BOOLEAN = new BuiltinType('boolean')
	public static val BYTE = new BuiltinType('byte')
	public static val INTEGER = new BuiltinType('integer')
	public static val LONG_INTEGER = new BuiltinType('long_integer')
	public static val REAL = new BuiltinType('real')
	public static val DEVICE = new BuiltinType('device')
	public static val DURATION = new BuiltinType('duration')
	public static val TIMESTAMP = new BuiltinType('timestamp')
	public static val TIMER = new BuiltinType('timer')

	public static val ANONYMOUS_CHARACTER = new BuiltinType('character', true)
	public static val ANONYMOUS_STRING = new BuiltinType('string', true)
	public static val ANONYMOUS_BOOLEAN = new BuiltinType('boolean', true)
	public static val ANONYMOUS_BYTE = new BuiltinType('byte', true)
	public static val ANONYMOUS_INTEGER = new BuiltinType('integer', true)
	public static val ANONYMOUS_LONG_INTEGER = new BuiltinType('long_integer', true)
	public static val ANONYMOUS_REAL = new BuiltinType('real', true)
	public static val ANONYMOUS_DEVICE = new BuiltinType('device', true)
	public static val ANONYMOUS_DURATION = new BuiltinType('duration', true)
	public static val ANONYMOUS_TIMESTAMP = new BuiltinType('timestamp', true)
	public static val ANONYMOUS_TIMER = new BuiltinType('timer', true)

	// needed by the typesystem
	public static val ANY_TYPE = new BuiltinType('any_type', true)
	public static val NO_TYPE = new BuiltinType('no_type', true)
	public static val STREAM_MANIPULATOR = new BuiltinType('stream_manipulator_type', true)
	public static val MISSING_TYPE = new BuiltinType('missing_type', true)

	new(String name, boolean anonymous) {
		super(anonymous)
		this.name = name
	}

	override MaslType getPrimitiveType() {
		switch name {
			case 'integer',
			case 'byte': ANONYMOUS_LONG_INTEGER
			case 'string': new SequenceType(CHARACTER, true)
			default: 
				new BuiltinType(name, true)
		}
	}

	override toString() {
		return prefix + name
	}
}

@Data
@FinalFieldsConstructor
class NamedType extends AbstractMaslType {
	String name
	transient MaslType type

	new(String name, MaslType type, boolean anonymous) {
		super(anonymous)
		this.name = name
		this.type = type
	}

	override getPrimitiveType() {
		type.primitiveType
	}

	override getComponentType() {
		type.componentType
	}
	
	public def getType() {
		type
	}
	
	override stripName() {
		type.stripName
	}

	override toString() {
		prefix + 'type ' + name
	}	
}

@Data
@FinalFieldsConstructor
class RangeType extends AbstractMaslType {
	val MaslType elementType

	new(MaslType type, boolean anonymous) {
		super(anonymous)
		this.elementType = type
	}

	override getPrimitiveType() {
		if(anonymous) 
			this 
		else 
			new RangeType(elementType, true)
	}
	
	override getComponentType() {
		elementType
	}
	
	override toString() {
		prefix + 'range of ' + elementType.toString
	}
}

@Data
@FinalFieldsConstructor
class TypeParameterType extends AbstractMaslType {
	val String name
	val boolean enumeration

	new(String name, boolean enumeration, boolean anonymous) {
		super(anonymous)
		this.name = name
		this.enumeration = enumeration
	}

	override getPrimitiveType() {
		if(anonymous)
			this
		else
			new TypeParameterType(name, enumeration, true)
	}
	
	override toString() {
		prefix + 'type parameter type ' + name 
	}
}

@Data
@FinalFieldsConstructor
abstract class CollectionType extends AbstractMaslType {
	val MaslType componentType

	new(MaslType type, boolean anonymous) {
		super(anonymous)
		this.componentType = type
	}

	override getPrimitiveType() {
		new SequenceType(componentType, true)
	}
}

class SetType extends CollectionType {
	new(MaslType elementType) {
		super(elementType)
	}

	new(MaslType elementType, boolean anonymous) {
		super(elementType, anonymous)
	}

	override String toString() {
		prefix + 'set of ' + componentType.toString
	}
}

class BagType extends CollectionType {
	new(MaslType elementType) {
		super(elementType)
	}

	new(MaslType elementType, boolean anonymous) {
		super(elementType, anonymous)
	}

	override String toString() {
		prefix + 'bag of ' + componentType.toString
	}
}

class SequenceType extends CollectionType {
	new(MaslType elementType) {
		super(elementType)
	}

	new(MaslType elementType, boolean anonymous) {
		super(elementType, anonymous)
	}

	override String toString() {
		prefix + 'sequence of ' + componentType.toString
	}
}

@Data
@FinalFieldsConstructor
class ArrayType extends CollectionType {
	val RangeType indexType
	
	new(MaslType elementType) {
		this(elementType, false, new RangeType(BuiltinType.ANONYMOUS_INTEGER))
	}

	new(MaslType elementType, boolean anonymous) {
		this(elementType, anonymous, new RangeType(BuiltinType.ANONYMOUS_INTEGER))
	}

	override String toString() {
		prefix + 'array of ' + componentType.toString
	}
}

@Data
@FinalFieldsConstructor
class TypeOfType extends AbstractMaslType {
	MaslType type

	new(MaslType type, boolean anonymous) {
		super(anonymous)
		this.type = type
	}	
	
	override getPrimitiveType() {
		if(anonymous) 
			this
		else 
			new TypeOfType(type, true)
	}
	
	override String toString() {
		prefix + 'typeof ' + type.toString
	}
}

@Data
class StructureType extends AbstractMaslType {
	@Accessors(PUBLIC_GETTER) transient val List<? extends StructureComponent> components
	@Accessors(PUBLIC_GETTER) val StructureTypeDefinition structureType

	new(StructureTypeDefinition structureType, List<? extends StructureComponent> components, boolean anonymous) {
		super(anonymous)
		this.structureType = structureType
		this.components = components
	}

	override getPrimitiveType() {
		new StructureType(structureType, 
			components.map [
				new StructureComponent(null, type)
			], true)
	}

	override String toString() '''
		«prefix»structure
			«FOR c : components»
				«c.toString»;
			«ENDFOR»	
		end
	'''
}

@Data
class StructureComponent {
	val String name
	val MaslType type

	override toString() {
		(if(name != null) name + ' : ' else '') + type.toString
	}  
}

@Data
@FinalFieldsConstructor
class EnumType extends AbstractMaslType {
	EnumerationTypeDefinition enumType

	new(EnumerationTypeDefinition enumType, boolean anonymous) {
		super(anonymous)
		this.enumType = enumType
	}
	
	override getPrimitiveType() {
		if(anonymous) 
			this 
		else 
			new EnumType(enumType, true)
	}

	override toString() {
		prefix + 'enum ' + (enumType.eContainer as TypeDeclaration).name
	}
}

@Data
@FinalFieldsConstructor
class InstanceType extends AbstractMaslType {
	ObjectDeclaration instance

	new(ObjectDeclaration instance, boolean anonymous) {
		super(anonymous)
		this.instance = instance
	}

	override getPrimitiveType() {
		if(anonymous) 
			this
		else
			new InstanceType(instance, true)
	}

	override toString() {
		prefix + 'instance of ' + instance.name
	}
}

@Data
@FinalFieldsConstructor
class TerminatorType extends AbstractMaslType {
	TerminatorDefinition terminator

	new(TerminatorDefinition terminator, boolean anonymous) {
		super(anonymous)
		this.terminator = terminator
	}

	override getPrimitiveType() {
		if(anonymous) 
			this
		else 
			new TerminatorType(terminator, true)
	}

	override toString() {
		prefix + 'terminator ' + terminator.name
	}
}

@Data
@FinalFieldsConstructor
class DictionaryType extends AbstractMaslType {
	MaslType keyType
	MaslType valueType

	new(MaslType keyType, MaslType elementType, boolean anonymous) {
		super(anonymous)
		this.keyType = keyType
		this.valueType = elementType
	}
	
	override getComponentType() {
		valueType
	}
	
	override getPrimitiveType() {
		new DictionaryType(keyType.primitiveType, valueType.primitiveType, true)
	}

	override toString() {
		prefix + 'dictionary ' + keyType.toString + ' of ' + valueType.toString
	}
}

