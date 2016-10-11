package org.xtuml.bp.xtext.masl.tests.typesystem

import com.google.inject.Inject
import org.junit.Test
import org.xtuml.bp.xtext.masl.tests.AbstractMaslModelTest
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider

import static org.junit.Assert.*

class TypeProviderTest extends AbstractMaslModelTest {
	
	@Inject extension MaslTypeProvider
	
	@Test
	def void testLiterals() {
		'1'.assertType('anonymous builtin integer')
		'1.1'.assertType('anonymous builtin real')
		"'c'".assertType('anonymous builtin character')
		'"foo"'.assertType('anonymous builtin string')
		'@Pxxx@'.assertType('anonymous builtin duration')
		'@xxx@'.assertType('anonymous builtin timestamp')
		'true'.assertType('anonymous builtin boolean')
		'false'.assertType('anonymous builtin boolean')
		'null'.assertType('anonymous builtin any_type')
		'flush'.assertType('anonymous builtin stream_manipulator_type')
		'console'.assertType('anonymous builtin device')
		'endl'.assertType('anonymous builtin stream_manipulator_type')
		'#LINE#'.assertType('anonymous builtin no_type') 
		'#FILE#'.assertType('anonymous builtin no_type') 
	}
	
	@Test 
	def void testStreams() {
		'console << endl'.assertType('anonymous builtin device')
		'console <<< ""'.assertType('anonymous builtin device')
		'console << endl << flush'.assertType('anonymous builtin device')
		'console >> "foo"'.assertType('anonymous builtin device')
		'console >> "foo" >> "bar"'.assertType('anonymous builtin device')
		'console >>> "foo"'.assertType('anonymous builtin device')
	}
	
	@Test
	def void testRange() {
		'1..2'.assertType('anonymous range of anonymous builtin integer')
	}
	
	@Test 
	def void testLogicalOperators() {
		'true or false'.assertType('anonymous builtin boolean')
		'true xor false'.assertType('anonymous builtin boolean')
		'true and false'.assertType('anonymous builtin boolean')
	}

	@Test 
	def void testEqualityOperators() {
		'1 = 1'.assertType('anonymous builtin boolean')
		'1 /= 1'.assertType('anonymous builtin boolean')
	}
	
	@Test 
	def void testRelationalOperators() {
		'1 < 1'.assertType('anonymous builtin boolean')
		'1 <= 1'.assertType('anonymous builtin boolean')
		'1 > 1'.assertType('anonymous builtin boolean')
		'1 >= 1'.assertType('anonymous builtin boolean')
	}

	@Test 
	def void testAdditiveOperators() {
		'1 + 1'.assertType('anonymous builtin integer')
		'1.0 + 1'.assertType('anonymous builtin real')
		'1 + 1.0'.assertType('anonymous builtin real')
		'1.0 + 1.0'.assertType('anonymous builtin real')
		'1 - 1'.assertType('anonymous builtin integer')
		'1.0 - 1'.assertType('anonymous builtin real')
		'1 - 1.0'.assertType('anonymous builtin real')
		'1.0 - 1.0'.assertType('anonymous builtin real')
		'"a" & "b"'.assertType('anonymous builtin string')
	}

	@Test 
	def void testAdditiveCollectionOperators() {
		'b & b'.assertType('b: bag of integer', 'bag of builtin integer')
		's & s'.assertType('s: sequence of integer', 'sequence of builtin integer')
		's & s'.assertType('s: set of integer', 'set of builtin integer')
		'b union b'.assertType('b: bag of integer', 'bag of builtin integer')
		's union s'.assertType('s: sequence of integer', 'sequence of builtin integer')
		's union s'.assertType('s: set of integer', 'set of builtin integer')
		'b not_in b'.assertType('b: bag of integer', 'bag of builtin integer')
		's not_in s'.assertType('s: sequence of integer', 'sequence of builtin integer')
		's not_in s'.assertType('s: set of integer', 'set of builtin integer')
	}
	
	@Test 
	def void testMultiplicativeOperators() {
		'1 * 1'.assertType('anonymous builtin integer')
		'1.0 * 1'.assertType('anonymous builtin real')
		'1 * 1.0'.assertType('anonymous builtin real')
		'1.0 * 1.0'.assertType('anonymous builtin real')
		'1 / 1'.assertType('anonymous builtin integer')
		'1.0 / 1'.assertType('anonymous builtin real')
		'1 / 1.0'.assertType('anonymous builtin real')
		'1.0 / 1.0'.assertType('anonymous builtin real')
		'1 rem 1'.assertType('anonymous builtin integer')
		'1 mod 1'.assertType('anonymous builtin integer')
		'1 ** 1'.assertType('anonymous builtin real')
	}
	
	@Test 
	def void testMultiplicativeCollectionOperators() {
		'b intersection b'.assertType('b: bag of integer', 'bag of builtin integer')
		's intersection s'.assertType('s: sequence of integer', 'sequence of builtin integer')
		's intersection s'.assertType('s: set of integer', 'set of builtin integer')
		'b disunion b'.assertType('b: bag of integer', 'bag of builtin integer')
		's disunion s'.assertType('s: sequence of integer', 'sequence of builtin integer')
		's disunion s'.assertType('s: set of integer', 'set of builtin integer')
	}
	
	@Test 
	def void testUnaryOperators() {
		'-1'.assertType('anonymous builtin integer')
		'-1.0'.assertType('anonymous builtin real')
		'+1'.assertType('anonymous builtin integer')
		'+1.0'.assertType('anonymous builtin real')
		'abs 1'.assertType('anonymous builtin integer')
		'abs 1.0'.assertType('anonymous builtin real')
		'not true'.assertType('anonymous builtin boolean')
	}
	
	@Test 
	def void testVariable() {
		assertType('bar', 'bar: integer', 'builtin integer')
	}
	
	@Test 
	def void testLoopVariable() {
		assertType("for baz in bar'elements loop ^baz; end", 'bar: bag of integer', 'builtin integer')
	}
	
	@Test 
	def void testParameter() {
		assertType('param', 'builtin integer')
	}
	
	@Test 
	def void testCastExpression() {
		assertType('integer(0.0)', 'builtin integer')
		assertType('bag of integer(null)', 'bag of builtin integer')
	}
	
	protected def assertType(CharSequence expression, String expected) {
		expression.assertType('', expected)
	}

	protected def assertType(CharSequence expression, CharSequence varDeclaration, String expected) {
		val expr = getElementAtCaret('dummy.mod' -> '''
			domain foo is 
				service foo(param: in integer);
			end domain;
		''',
			'dummy.svc' -> '''
			service foo::foo(param: in integer) 
			is
				«IF !varDeclaration.toString.empty»«varDeclaration»;«ENDIF»
			begin
				«IF !expression.toString.contains('^')»^(«expression»)«ELSE»«expression»«ENDIF»;
			end;
		''')
		assertEquals(expected, getMaslType(expr)?.toString)
	}
}