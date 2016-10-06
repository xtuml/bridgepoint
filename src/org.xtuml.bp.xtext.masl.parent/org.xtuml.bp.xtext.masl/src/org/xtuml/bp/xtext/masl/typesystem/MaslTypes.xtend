package org.xtuml.bp.xtext.masl.typesystem

import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.xtuml.bp.xtext.masl.masl.structure.ObjectDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorDefinition
import org.xtuml.bp.xtext.masl.masl.types.EnumerationTypeDefinition
import org.xtuml.bp.xtext.masl.masl.types.TypeDeclaration

/**
 * Dealing with EMF objects is tedious, so we define our own types
 */
interface MaslType {

	def MaslType getPrimitiveType()

	def boolean isAnonymous()
}

@FinalFieldsConstructor
@Accessors(PUBLIC_GETTER)
abstract class AbstractMaslType implements MaslType {

	val boolean anonymous

	new() {
		anonymous = false
	}

	protected def prefix() {
		(if (anonymous) 'anonymous ' else '') 
	}
}

@Data
@FinalFieldsConstructor
class BuiltinType extends AbstractMaslType {
	val String name

	public static val CHARACTER = 'character'

	public static val STRING =  'string'

	public static val BOOLEAN = 'boolean'

	public static val BYTE = 'byte'

	public static val INTEGER = 'integer'

	public static val LONG_INTEGER = 'long_integer'

	public static val REAL = 'real'

	public static val DEVICE = 'device'

	public static val DURATION = 'duration'

	public static val TIMESTAMP = 'timestamp'

	public static val TIMER = 'timer'

	// needed by the typesystem
	public static val ANY_TYPE = 'any_type'

	public static val NO_TYPE = 'no_type'

	public static val STREAM_MANIPULATOR = 'stream_manipulator_type'

	new(String name, boolean anonymous) {
		super(anonymous)
		this.name = name
	}

	override BuiltinType getPrimitiveType() {
		switch name {
			case 'integer',
			case 'byte': new BuiltinType('long_integer')
			default: 
				new BuiltinType(name)
		}
	}

	override String toString() {
		prefix + 'builtin ' + name
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

	override String toString() {
		prefix + 'type ' + name + ' is ' + type
	}
}

@Data
@FinalFieldsConstructor
abstract class CollectionType extends AbstractMaslType {
	val MaslType elementType

	new(MaslType type, boolean anonymous) {
		super(anonymous)
		this.elementType = type
	}

	override getPrimitiveType() {
		new SequenceType(elementType)
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
		prefix + 'set of ' + elementType
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
		prefix + 'bag of ' + elementType
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
		prefix + 'sequence of ' + elementType
	}
}

class ArrayType extends CollectionType {
	new(MaslType elementType) {
		super(elementType)
	}

	new(MaslType elementType, boolean anonymous) {
		super(elementType, anonymous)
	}

	override String toString() {
		prefix + 'array of ' + elementType
	}
}

@Data
@FinalFieldsConstructor
class StructureType extends AbstractMaslType {
	List<? extends StructureComponent> components

	new(List<? extends StructureComponent> components, boolean anonymous) {
		super(anonymous)
		this.components = components
	}

	override getPrimitiveType() {
		new StructureType(components.map [
			new StructureComponent(null, type.primitiveType)
		])
	}

	override String toString() '''
		«prefix»structure
			«FOR c : components»
				«c»;
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
		new EnumType(enumType)
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
		new InstanceType(instance)
	}

	override toString() {
		prefix + 'instance of ' + instance.name
	}
}

@Data
class TerminatorType extends AbstractMaslType {
	TerminatorDefinition terminator

	override getPrimitiveType() {
		new TerminatorType(terminator)
	}

	override toString() {
		prefix + 'terminator ' + terminator.name
	}
}

@Data
@FinalFieldsConstructor
class DictionaryType extends AbstractMaslType {
	MaslType keyType
	MaslType elementType

	new(MaslType keyType, MaslType elementType, boolean anonymous) {
		super(anonymous)
		this.keyType = keyType
		this.elementType = elementType
	}
	
	override getPrimitiveType() {
		new DictionaryType(keyType.primitiveType, elementType.primitiveType)
	}

	override toString() {
		prefix + 'dictionary ' + keyType.toString + ' of ' + elementType.toString 
	}
}
