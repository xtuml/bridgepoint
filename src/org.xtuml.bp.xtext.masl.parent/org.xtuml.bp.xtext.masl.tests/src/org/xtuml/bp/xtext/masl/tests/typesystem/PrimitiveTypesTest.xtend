package org.xtuml.bp.xtext.masl.tests.typesystem

import com.google.inject.Inject
import org.junit.Test
import org.xtuml.bp.xtext.masl.tests.AbstractMaslModelTest
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider

import static org.junit.Assert.*

class PrimitiveTypesTest extends AbstractMaslModelTest {

	@Inject extension MaslTypeProvider

	@Test
	def testEnum() {
		assertPrimitiveType('type bar is enum (BAR, BAZ);', '', 'BAR', 'enum bar')		
		assertPrimitiveType('type bar is enum (BAR, BAZ);', 'b: bar', 'b', 'enum bar')		
	}
	
	@Test
	def testIntegerType() {
		assertPrimitiveType('type Bar is integer;', 'b: Bar', 'Bar', 'builtin long_integer')		
		assertPrimitiveType('type Bar is integer;', 'b: Bar', 'b', 'builtin long_integer')		
		assertPrimitiveType('type Bar is integer; type Baz is Bar;', 'b: Baz', 'Baz', 'builtin long_integer')		
		assertPrimitiveType('type Bar is integer; type Baz is Bar;', 'b: Baz', 'b', 'builtin long_integer')		
	}
	
	@Test
	def testByteType() {
		assertPrimitiveType('type Bar is byte;', 'b: Bar', 'Bar', 'builtin long_integer')		
		assertPrimitiveType('type Bar is byte;', 'b: Bar', 'b', 'builtin long_integer')		
		assertPrimitiveType('type Bar is byte; type Baz is Bar;', 'b: Baz', 'Baz', 'builtin long_integer')		
		assertPrimitiveType('type Bar is byte; type Baz is Bar;', 'b: Baz', 'b', 'builtin long_integer')		
	}
	
	@Test
	def testRealType() {
		assertPrimitiveType('type Bar is real;', 'b: Bar', 'Bar', 'builtin real')		
		assertPrimitiveType('type Bar is real;', 'b: Bar', 'b', 'builtin real')		
	}
	
	@Test
	def testCharacterType() {
		assertPrimitiveType('type Bar is character;', 'b: Bar', 'Bar', 'builtin character')		
		assertPrimitiveType('type Bar is character;', 'b: Bar', 'b', 'builtin character')		
	}
	
	@Test
	def testObjectType() {
		assertPrimitiveType('object Foo; object Foo is end;', 'f: instance of Foo', 'f', 'instance of Foo')		
	}
	
	@Test
	def testIntegerLiteral() {
		assertPrimitiveType('', '', '1', 'builtin long_integer')		
	}
	
	@Test
	def testCollections() {
		assertPrimitiveType('', 'b: bag of integer', 'b', 'sequence of builtin integer')		
		assertPrimitiveType('', 'b: sequence of integer', 'b', 'sequence of builtin integer')		
		assertPrimitiveType('', 'b: set of integer', 'b', 'sequence of builtin integer')		
		assertPrimitiveType('', 'b: array (1..1) of integer', 'b', 'sequence of builtin integer')		
		assertPrimitiveType('type Con is integer (1..1)', 'c: Con', 'c', 'sequence of builtin integer')		
		assertPrimitiveType('type Con is integer (1..1)', 'c: Con', 'c', 'sequence of builtin integer')		
	}
	
	@Test
	def testTerminator() {
		assertPrimitiveType('terminator Arnold is end;', '', 'Arnold', 'terminator Arnold')		
	}
	
	@Test 
	def void testStruct() {
		assertPrimitiveType('''
			type i is integer;
			type j is i;
			type s is structure  
				b: integer := 1 
				c: i
				d: j
			end;
		''', 'x: s', 'x', '''
			structure
				builtin long_integer;
				builtin long_integer;
				builtin long_integer;
			end
		''')		
	}
	
	protected def assertPrimitiveType(CharSequence domainDeclaration, CharSequence variableDeclaration, CharSequence expression, String expectedPrimitiveType) {
		val expr = getElement(domainDeclaration, variableDeclaration, expression)
		assertEquals(expectedPrimitiveType, expr.maslType.primitiveType.toString)
	}
}