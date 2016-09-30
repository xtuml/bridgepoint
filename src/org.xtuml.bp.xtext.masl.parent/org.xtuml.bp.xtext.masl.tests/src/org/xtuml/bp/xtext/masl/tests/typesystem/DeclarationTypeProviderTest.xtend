package org.xtuml.bp.xtext.masl.tests.typesystem

import com.google.inject.Inject
import org.junit.Test
import org.xtuml.bp.xtext.masl.tests.AbstractMaslModelTest
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeExtensions
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider

import static org.junit.Assert.*

class DeclarationTypeProviderTest extends AbstractMaslModelTest {
	
	@Inject extension MaslTypeProvider
	@Inject extension MaslTypeExtensions
	
	@Test
	def void testCreateExpression() {
		assertType('object Foo; object Foo is end;', 'create Foo()', 'instance Foo')
	}

	@Test
	def void testEnumerationCall() {
		assertType('type Foo is enum (BAR, BAZ);', 'BAR', 'enum Foo')
	}

	@Test
	def void testObjectCall() {
		'''
			object Foo; 
			object Foo is end;
		'''.assertType('Foo', 'instance Foo')
	}

	@Test
	def void testTerminatorCall() {
		assertType('terminator Arnold is end;', 'Arnold', 'terminator Arnold')
	}

	@Test
	def void testAttributeCall() {
		'''
			object Foo; 
			object Foo is 
				bar: byte; 
			end;
		'''.assertType('Foo::bar', 'builtin byte')
	}

	@Test
	def void testTypeCall() {
		assertType('type Foo is integer;', 'Foo', 'type Foo is integer')
	}

	@Test
	def void testObjectFunctionCall() {
		'''
			object Foo; 
			object Foo is
				function foo() return integer;
			end;
		'''.assertType('Foo::foo()', 'builtin integer')
	}

	@Test
	def void testObjectServiceCall() {
		'''
			object Foo; 
			object Foo is
				service foo();
			end;
		'''.assertType('Foo::foo()', 'anonymous builtin no_type')
	}

	@Test
	def void testDomainFunctionCall() {
		'''
			function foo() return string;
		'''.assertType('foo()', 'builtin string')
	}

	@Test
	def void testDomainServiceCall() {
		'''
			service foo();
		'''.assertType('foo()', 'anonymous builtin no_type')
	}

	@Test
	def void testTerminatorFunctionCall() {
		'''
			terminator Arnold is 
				function foo() return string;
			end;
		'''.assertType('Arnold~>foo()', 'builtin string')
	}

	@Test
	def void testTerminatorServiceCall() {
		'''
			terminator Arnold is 
				service foo();
			end;
		'''.assertType('Arnold~>foo()', 'anonymous builtin no_type')
	}

	protected def assertType(CharSequence domainDeclaration, CharSequence expression, String expected) {
		val expr = getElement(domainDeclaration, '', expression)
		assertEquals(expected, getType(expr)?.asString)
	}
}