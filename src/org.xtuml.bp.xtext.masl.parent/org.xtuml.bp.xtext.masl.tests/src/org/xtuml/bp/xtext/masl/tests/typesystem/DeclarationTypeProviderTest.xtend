package org.xtuml.bp.xtext.masl.tests.typesystem

import com.google.inject.Inject
import org.junit.Test
import org.xtuml.bp.xtext.masl.tests.AbstractMaslModelTest
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider

import static org.junit.Assert.*

class DeclarationTypeProviderTest extends AbstractMaslModelTest {
	
	@Inject extension MaslTypeProvider
	
	@Test
	def void testCreateExpression() {
		assertType('object Foo; object Foo is end;', 'create Foo()', 'instance of Foo')
	}

	@Test
	def void testEnumerationCall() {
		doAssertType('''
			domain dom is
				type Foo is enum (BAR, BAZ);
			end;
		''', '''
			service dom::scv() is
				f: Foo;
			begin
				^f := ^BAR;
			end;
		''', 'type Foo is enum Foo')
	}

	@Test
	def void testStructureCall() {
		assertType('type Foo is structure a: integer; end;', 'Foo', '''
			type Foo is structure
				a : builtin integer;
			end
		''')
	}

	@Test
	def void testObjectCall() {
		'''
			object Foo;
			object Foo is 
				f: instance of Foo;
			end;
		'''.assertType('^Foo; ^(Foo.f)', 'instance of Foo')
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
		assertType('type Foo is integer;', 'Foo', 'type Foo is builtin integer')
	}

	@Test
	def void testInstanceObjectFunctionCall() {
		doAssertType('''
			domain dom is
				object Foo; 
				object Foo is
					function foo() return integer;
				end;
			end;
		''', '''
			service dom::scv() is
				f: instance of Foo;
			begin
				^(f.foo());
			end;
		''', 'builtin integer')
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
		'''.assertType('Foo::foo()', 'builtin no_type')
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
		'''.assertType('foo()', 'builtin no_type')
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
		'''.assertType('Arnold~>foo()', 'builtin no_type')
	}

	protected def assertType(CharSequence domainDeclaration, CharSequence expression, String expected) {
		doAssertType('''
			domain dom is
				service svc();
				«domainDeclaration»
			end;
		''', '''
			service dom::svc() is
			begin
				«IF !expression.toString.contains('^')»^(«expression»)«ELSE»«expression»«ENDIF»;
			end;
		''', expected);
	}
	
	protected def doAssertType(CharSequence modFile, CharSequence extFile, String expected) {
		for (expr: getElementsAtCarets('dummy.mod' -> modFile, 'dummy.ext' -> extFile)) 
			assertEquals(expected, getMaslType(expr)?.toString)
	}
}