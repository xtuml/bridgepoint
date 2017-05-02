package org.xtuml.bp.xtext.masl.typesystem

import java.util.List
import java.util.Map
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
	
	def String toString(Map<MaslType, String> seenTypes)
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
	
	override toString() {
		toStringRecursionSafe
	}
	
	protected def toStringRecursionSafe() {
		toString(newHashMap)
	}
	
	override String toString(Map<MaslType, String> seenTypes) {
		val seenName = seenTypes.get(this)
		if(seenName !== null)
			return seenName
		else
			return toStringInternal(seenTypes)
	}
	
	protected def String toStringInternal(Map<MaslType, String> map)
	
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
			case 'byte': new BuiltinType('long_integer')
			case 'string': new SequenceType(CHARACTER)
			default: 
				new BuiltinType(name)
		}
	}

	override String toStringInternal(Map<MaslType, String> seenTypes) {
		return prefix + name
	}
	
	override toString() {
		toStringRecursionSafe
	}
}

@Data
@FinalFieldsConstructor
class NamedType extends AbstractMaslType {
	String name
	MaslType type

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
	
	override String toStringInternal(Map<MaslType, String> seenTypes) {
		seenTypes.put(this, name)
		prefix + 'type ' + name + ' is ' + type.toString(seenTypes)
	}
	
	override stripName() {
		type.stripName
	}

	override toString() {
		toStringRecursionSafe
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
		this
	}
	
	override getComponentType() {
		elementType
	}
	
	override String toStringInternal(Map<MaslType, String> seenTypes) {
		prefix + 'range of ' + elementType.toString(seenTypes)
	}
	
	override toString() {
		toStringRecursionSafe
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
		this
	}
	
	override String toStringInternal(Map<MaslType, String> seenTypes) {
		prefix + 'type parameter type ' + name 
	}
	
	override toString() {
		toStringRecursionSafe
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
		new SequenceType(componentType.primitiveType)
	}
	
	override toString() {
		toStringRecursionSafe
	}
}

class SetType extends CollectionType {
	new(MaslType elementType) {
		super(elementType)
	}

	new(MaslType elementType, boolean anonymous) {
		super(elementType, anonymous)
	}

	override String toStringInternal(Map<MaslType, String> seenTypes) {
		prefix + 'set of ' + componentType.toString(seenTypes)
	}
}

class BagType extends CollectionType {
	new(MaslType elementType) {
		super(elementType)
	}

	new(MaslType elementType, boolean anonymous) {
		super(elementType, anonymous)
	}

	override String toStringInternal(Map<MaslType, String> seenTypes) {
		prefix + 'bag of ' + componentType.toString(seenTypes)
	}
}

class SequenceType extends CollectionType {
	new(MaslType elementType) {
		super(elementType)
	}

	new(MaslType elementType, boolean anonymous) {
		super(elementType, anonymous)
	}

	override String toStringInternal(Map<MaslType, String> seenTypes) {
		prefix + 'sequence of ' + componentType.toString(seenTypes)
	}
}

class ArrayType extends CollectionType {
	new(MaslType elementType) {
		super(elementType)
	}

	new(MaslType elementType, boolean anonymous) {
		super(elementType, anonymous)
	}

	override String toStringInternal(Map<MaslType, String> seenTypes) {
		prefix + 'array of ' + componentType.toString(seenTypes)
	}
}

@Data
@FinalFieldsConstructor
class TypeOfType extends AbstractMaslType {
	MaslType type
	
	override getPrimitiveType() {
		this
	}
	
	override String toStringInternal(Map<MaslType, String> seenTypes) {
		prefix + 'typeof ' + type.toString(seenTypes)
	}

	override toString() {
		toStringRecursionSafe
	}
}

class StructureType extends AbstractMaslType {
	@Accessors(PUBLIC_GETTER) val List<? extends StructureComponent> components
	@Accessors(PUBLIC_GETTER) val StructureTypeDefinition structureType

	new(List<? extends StructureComponent> components) {
		this(null, components, false)
	}

	new(StructureTypeDefinition structureType, List<? extends StructureComponent> components, boolean anonymous) {
		super(anonymous)
		this.structureType = structureType
		this.components = components
	}

	override getPrimitiveType() {
		new StructureType(components.map [
			new StructureComponent(null, type.primitiveType)
		])
	}

	override String toStringInternal(Map<MaslType, String> seenTypes) '''
		«prefix»structure
			«FOR c : components»
				«c.toString(seenTypes)»;
			«ENDFOR»	
		end'''

	override toString() {
		toStringRecursionSafe
	}
		
	override equals(Object obj) {
		if(obj === this)
			return true
		if (obj === null)
			return false
		if (class != obj.class)
			return false
		val other = obj as StructureType
		if (this.structureType == other.structureType && this.structureType != null)
			return true
		if (this.components == null) {
			if (other.components != null)
				return false
		} else if (!this.components.equals(other.components))
			return false
		return true
	}
	
	@Pure
	override hashCode() {
		if(structureType == null)
			return 31
		else 
    	return structureType.hashCode
	}
}

@Data
class StructureComponent {
	val String name
	val MaslType type

	def toString(Map<MaslType, String> seenTypes) {
		(if(name != null) name + ' : ' else '') + type.toString(seenTypes)
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
		new EnumType(enumType)
	}

	override toStringInternal(Map<MaslType, String> seenTypes) {
		prefix + 'enum ' + (enumType.eContainer as TypeDeclaration).name
	}

	override toString() {
		toStringRecursionSafe
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
		new InstanceType(instance)
	}

	override toStringInternal(Map<MaslType, String> seenTypes) {
		prefix + 'instance of ' + instance.name
	}

	override toString() {
		toStringRecursionSafe
	}
}

@Data
class TerminatorType extends AbstractMaslType {
	TerminatorDefinition terminator

	override getPrimitiveType() {
		new TerminatorType(terminator)
	}

	override toStringInternal(Map<MaslType, String> seenTypes) {
		prefix + 'terminator ' + terminator.name
	}

	override toString() {
		toStringRecursionSafe
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
	
	override getPrimitiveType() {
		new DictionaryType(keyType.primitiveType, valueType.primitiveType)
	}

	override toStringInternal(Map<MaslType, String> seenTypes) {
		prefix + 'dictionary ' + keyType.toString(seenTypes) + ' of ' + valueType.toString(seenTypes)
	}

	override toString() {
		toStringRecursionSafe
	}
}

