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
				service svc();
				type Foo is enum (BAR, BAZ);
			end;
		''', '''
			service dom::svc() is
				f: Foo;
			begin
				^f := ^BAR;
			end;
		''', 'enum Foo')
	}

	@Test
	def void testIndexedCall() {
		doAssertType('''
			domain dom is
				service svc();
				type Foo is array(1..2) of integer;
			end;
		''', '''
			service dom::svc() is
				f: Foo;
			begin
				^(f[1]);
			end;
		''', 'builtin integer')
	}

	@Test
	def void testIndexedCall1() {
		doAssertType('''
			domain dom is
				service svc();
				object Foo;
				object Foo is
					foo: instance of Foo;
				end;
			end;
		''', '''
			service dom::svc() is
				arr: array (1..10) of instance of Foo;
			begin
				^(arr[1]);
				^(arr[1].foo);
			end;
		''', 'instance of Foo')
	}

	@Test
	def void testIndexedCall2() {
		doAssertType('''
			domain dom is
				service svc();
			end;
		''', '''
			service dom::svc() is
				s: string;
			begin
				^(s[1]);
			end;
		''', 'anonymous builtin character')
	}

	@Test
	def void testNavigateExpression() {
		doAssertType('''
			domain dom is
				object Foo;
				object Foo is end;
				object Bar;
				object Bar is end;
				relationship R1 is
					Foo unconditionally bar one Bar,
					Bar unconditionally foo one Foo;
				service svc();
			end;
		''', '''
			service dom::svc() is
				f: instance of Foo;
				b: instance of Bar;
			begin
				^(f->R1.bar);
				^(f->R1.Bar);
				^(f->R1);
			end;
		''', 'anonymous instance of Bar')
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
				service svc();
				object Foo; 
				object Foo is
					function foo() return integer;
				end;
			end;
		''', '''
			service dom::svc() is
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

	@Test
	def void testEnumTypeCharacteristics() {
		doAssertType('''
			domain dom is
				service svc();
				type Foo is enum(BAR, BAZ);
			end;
		''','''
			service dom::svc() is
				f: Foo;
			begin
				^(BAR'pred);
				^(Foo'value(""));
				f := f'succ;
			end;
		''', 'enum Foo')
	}

	@Test
	def void testEnumTypeCharacteristics1() {
		assertType('type Foo is enum(BAR, BAZ);', '''
			^(Foo'first);			
			^(Foo'last);
			^(Foo'size());
			^(Foo'pos(BAR))
		''', 'builtin integer')
	}

	@Test
	def void testDictionaryTypeCharacteristics() {
		doAssertType('''
			domain dom is
				service svc();
				type Foo is dictionary integer of string;
			end;
		''','''
			service dom::svc() is
				f: Foo;
			begin
				^(Foo'keys);
			end;
		''', 'set of builtin integer')
	}

	@Test
	def void testDictionaryTypeCharacteristics1() {
		doAssertType('''
			domain dom is
				service svc();
				type Foo is dictionary integer of string;
			end;
		''','''
			service dom::svc() is
				f: Foo;
			begin
				^(Foo'values);
			end;
		''', 'bag of builtin string')
	}

	@Test
	def void testDictionaryTypeCharacteristics2() {
		doAssertType('''
			domain dom is
				service svc();
				type Foo is dictionary integer of string;
			end;
		''','''
			service dom::svc() is
				f: Foo;
			begin
				^(Foo'contains(1));
			end;
		''', 'builtin boolean')
	}

	@Test
	def void testDictionaryTypeCharacteristics3() {
		doAssertType('''
			domain dom is
				service svc();
				type Foo is dictionary;
			end;
		''','''
			service dom::svc() is
				f: Foo;
			begin
				^(Foo'keys);
			end;
		''', 'set of anonymous builtin string')
	}

	@Test
	def void testDictionaryTypeCharacteristics4() {
		doAssertType('''
			domain dom is
				service svc();
				type Foo is dictionary;
			end;
		''','''
			service dom::svc() is
				f: Foo;
			begin
				^(Foo'values);
			end;
		''', 'bag of anonymous builtin string')
	}
	
	@Test
	def void testUnconstrainedArrayType() {
		doAssertType('''
			domain dom is
				service svc();
				type array2 is array ( integer range<> ) of string;
				type array3 is array2 ( 1..10 ); 
			end;
		''','''
			service dom::svc() is
				a: array3;
			begin
				^a;
			end;
		''', 'type array3 is array of builtin string')
	}

	@Test
	def void testUnconstrainedArrayType2() {
		doAssertType('''
			domain dom is
				service svc();
				type array2 is array ( integer range<> ) of string;
			end;
		''','''
			service dom::svc() is
				b: array2 (1..20);
			begin
				^b;
			end;
		''', 'array of builtin string')
	}

	@Test
	def void testTimestampOperators() {
		doAssertType('''
			domain dom is
				service svc();
				type myTimestamp is timestamp;
			end;
		''','''
			service dom::svc() is
				t: myTimestamp;
				d: duration;
			begin
				^(t+d);
				^(d+t);
				^(t-d);
			end;
		''', 'type myTimestamp is builtin timestamp')
	}

	@Test
	def void testDurationOperators() {
		doAssertType('''
			domain dom is
				service svc();
				type myDuration is duration;
			end;
		''','''
			service dom::svc() is
				myD: myDuration;
				d: duration;
			begin
				^(myD+@P1D@);
				^(myD+myD);
				^(@P1D@+myD);				
				^(myD-@P1D@);
				^(myD-myD);
				^(@P1D@-myD);
				^(myD*2);
				^(2*myD);
				^(myD/2);
			end;
		''', 'type myDuration is builtin duration')
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
			assertEquals(expected.trim, getMaslType(expr)?.toString.trim)
	}
}