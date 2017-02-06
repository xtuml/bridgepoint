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
		''', 'integer')
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
		''', 'anonymous character')
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
			typeof type Foo is structure
				a : integer;
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
		'''.assertType('Foo::bar', 'byte')
	}

	@Test
	def void testTypeCall() {
		assertType('type Foo is integer;', 'Foo', 'typeof type Foo is integer')
	}

	@Test
	def void testInstanceObjectServiceCall() {
		doAssertType('''
			domain dom is
				service svc();
				object Foo; 
				object Foo is
					service foo() return integer;
				end;
			end;
		''', '''
			service dom::svc() is
				f: instance of Foo;
			begin
				^(f.foo());
			end;
		''', 'integer')
	}

	@Test
	def void testObjectServiceCall() {
		'''
			object Foo; 
			object Foo is
				service foo() return integer;
			end;
		'''.assertType('Foo::foo()', 'integer')
	}

	@Test
	def void testObjectServiceCall_1() {
		'''
			object Foo; 
			object Foo is
				service foo();
			end;
		'''.assertType('Foo::foo()', 'anonymous no_type')
	}

	@Test
	def void testDomainServiceCall() {
		'''
			service foo() return string;
		'''.assertType('foo()', 'string')
	}

	@Test
	def void testDomainServiceCall_1() {
		'''
			service foo();
		'''.assertType('foo()', 'anonymous no_type')
	}

	@Test
	def void testTerminatorServiceCall() {
		'''
			terminator Arnold is 
				service foo() return string;
			end;
		'''.assertType('Arnold~>foo()', 'string')
	}

	@Test
	def void testTerminatorServiceCall_1() {
		'''
			terminator Arnold is 
				service foo();
			end;
		'''.assertType('Arnold~>foo()', 'anonymous no_type')
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
		''', 'integer')
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
				^(f'keys);
			end;
		''', 'set of integer')
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
				^(f'values);
			end;
		''', 'bag of string')
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
				^(f'contains(1));
			end;
		''', 'boolean')
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
				^(f'keys);
			end;
		''', 'set of anonymous string')
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
				^(f'values);
			end;
		''', 'bag of anonymous string')
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
		''', 'type array3 is array of string')
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
		''', 'array of string')
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
		''', 'type myTimestamp is timestamp')
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
		''', 'type myDuration is duration')
	}

	@Test
	def void testOverloadedMethod() {
		doAssertType('''
			domain dom is 
				service foo(i:in string) return string;
				service foo(i:in integer) return integer;
				service svc();
			end;
		''', '''
			service dom::svc() is
			begin
				^(foo(1));
			end;
		''', 'integer')	
	}

	@Test
	def void testEnumCharacteristics() {
		doAssertType('''
			domain dom is
				type e is enum (FOO, BAR);
				service svc();
			end;
		''', '''
			service dom::svc() is
			begin
				^(e'value(""));
			end;
		''', 'enum e')	
	}

	@Test 
	def void testRelationshipNavigation() {
		doAssertType('''
			domain doms is 
				object Foo;
				object Foo is end;
				object Bar;
				object Bar is end;
				relationship R1 is
					Foo conditionally bar one Bar,
					Bar conditionally foo one Foo;
			end;
		''', '''
			service doms::svc() is
				foo: instance of Foo;
				bar: instance of Bar;
			begin
				^(foo->R1);
				^(foo->R1.Bar);
				^(foo->R1.bar);
				^(foo->R1.bar.Bar);
				^(foo->R1.bar->R1.foo->R1);
			end;
		''', 'anonymous instance of Bar') 
	}

	@Test 
	def void testRelationshipNavigation1() {
		doAssertType('''
			domain doms is 
				object Foo;
				object Foo is end;
				object Bar;
				object Bar is end;
				relationship R1 is
					Foo conditionally bar many Bar,
					Bar conditionally foo one Foo;
				relationship R2 is
					Foo conditionally bar one Bar,
					Bar conditionally foo one Foo;
				end;
		''', '''
			service doms::svc() is
				foos: set of instance of Foo;
			begin
				^(foos->R1);
				^(foos->R2);
			end;
		''', 'anonymous bag of instance of Bar') 
	}
	
	@Test 
	def void testAssociativeRelationshipNavigation() {
		doAssertType('''
			domain doms is
				object Foo;
				object Foo is
				end;
				object Bar;
				object Bar is
				end;
				object Baz;
				object Baz is
				end;
				relationship R1 is
					Foo conditionally bar one Bar,
					Bar conditionally foo one Foo
					using Baz;
			end;
		''', '''
			service doms::svc() is
				baz: instance of Baz; 
			begin
				^(baz->R1.foo);
			end;
		''', "anonymous instance of Foo")
	}

	@Test 
	def void testAssociativeRelationshipNavigation1() {
		doAssertType('''
			domain doms is
				object Foo;
				object Foo is
				end;
				object Bar;
				object Bar is
				end;
				object Baz;
				object Baz is
				end;
				relationship R1 is
					Foo conditionally bar one Bar,
					Bar conditionally foo many Foo
					using Baz;
			end;
		''', '''	
			service doms::svc() is
				foo: set of instance of Foo;
			begin
				^(foo->R1.Baz);
			end;
		''', 'anonymous instance of Baz')
	}

	@Test 
	def void testAssociativeRelationshipNavigation2() {
		doAssertType('''
			domain doms is
				object Foo;
				object Foo is
				end;
				object Bar;
				object Bar is
				end;
				object Baz;
				object Baz is
				end;
				relationship R1 is
					Foo conditionally bar many Bar,
					Bar conditionally foo one Foo
					using Baz;
			end;
		''', '''	
			service doms::svc() is
				foo: set of instance of Foo;
			begin
				^(foo->R1.Baz);
			end;
		''', 'anonymous bag of instance of Baz')
	}

	@Test 
	def void testAssociativeRelationshipNavigation3() {
		doAssertType('''
			domain doms is
				object Foo;
				object Foo is
				end;
				object Bar;
				object Bar is
				end;
				object Baz;
				object Baz is
				end;
				relationship R1 is
					Foo conditionally bar one Bar,
					Bar conditionally foo many Foo
					using Baz;
			end;
		''', '''
			service doms::svc() is
				baz: set of instance of Baz;
			begin
				^(baz->R1.foo);
			end;
		''', 'anonymous instance of Foo')
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