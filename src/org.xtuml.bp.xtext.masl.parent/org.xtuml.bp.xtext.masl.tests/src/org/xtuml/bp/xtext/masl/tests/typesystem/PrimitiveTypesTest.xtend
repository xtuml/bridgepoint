package org.xtuml.bp.xtext.masl.tests.typesystem

import com.google.inject.Inject
import org.junit.Test
import org.xtuml.bp.xtext.masl.tests.AbstractMaslModelTest
import org.xtuml.bp.xtext.masl.typesystem.MaslPrimitiveTypeProvider
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider

import static org.junit.Assert.*

class PrimitiveTypesTest extends AbstractMaslModelTest {

	@Inject extension MaslTypeProvider 
	@Inject extension MaslPrimitiveTypeProvider 

	@Test
	def testEnum() {
		assertPrimitiveType('type bar is enum (BAR, BAZ);', '', 'BAR', 'enum//@elements.0/@types.0/@definition')		
		assertPrimitiveType('type bar is enum (BAR, BAZ);', 'b: bar', 'b', 'enum//@elements.0/@types.0/@definition')		
	}
	
	@Test
	def testIntegerType() {
		assertPrimitiveType('type Bar is integer;', 'b: Bar', 'Bar', 'builtin integer')		
		assertPrimitiveType('type Bar is integer;', 'b: Bar', 'b', 'builtin integer')		
		assertPrimitiveType('type Bar is integer; type Baz is Bar;', 'b: Baz', 'Baz', 'builtin integer')		
		assertPrimitiveType('type Bar is integer; type Baz is Bar;', 'b: Baz', 'b', 'builtin integer')		
	}
	
	@Test
	def testObjectType() {
		assertPrimitiveType('object Foo; object Foo is end;', 'f: instance of Foo', 'f', 'instance Foo')		
	}
	
	@Test
	def testIntegerLiteral() {
		assertPrimitiveType('', '', '1', 'builtin integer')		
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
			{
				builtin integer
				builtin integer
				builtin integer
			}
		''')		
	}
	
	protected def assertPrimitiveType(CharSequence domainDeclaration, CharSequence variableDeclaration, CharSequence expression, String expectedPrimitiveType) {
		val expr = getElement(domainDeclaration, variableDeclaration, expression)
		assertEquals(expectedPrimitiveType, expr.type.primitiveType)
	}
}