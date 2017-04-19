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
		'1'.assertType('anonymous integer')
		'1.1'.assertType('anonymous real')
		"'c'".assertType('anonymous character')
		'"foo"'.assertType('anonymous string')
		'@Pxxx@'.assertType('anonymous duration')
		'@xxx@'.assertType('anonymous timestamp')
		'true'.assertType('anonymous boolean')
		'false'.assertType('anonymous boolean')
		'null'.assertType('anonymous any_type')
		'flush'.assertType('anonymous stream_manipulator_type')
		'console'.assertType('anonymous device')
		'endl'.assertType('anonymous stream_manipulator_type')
		'#LINE#'.assertType('anonymous integer') 
		'#FILE#'.assertType('anonymous string') 
	}
	
	@Test 
	def void testStreams() {
		'console << endl'.assertType('anonymous device')
		'console <<< ""'.assertType('anonymous device')
		'console << endl << flush'.assertType('anonymous device')
		'console >> "foo"'.assertType('anonymous device')
		'console >> "foo" >> "bar"'.assertType('anonymous device')
		'console >>> "foo"'.assertType('anonymous device')
	}
	
	@Test
	def void testRange() {
		'1..2'.assertType('anonymous range of anonymous integer')
	}
	
	@Test 
	def void testLogicalOperators() {
		'true or false'.assertType('anonymous boolean')
		'true xor false'.assertType('anonymous boolean')
		'true and false'.assertType('anonymous boolean')
	}

	@Test 
	def void testEqualityOperators() {
		'1 = 1'.assertType('anonymous boolean')
		'1 /= 1'.assertType('anonymous boolean')
	}
	
	@Test 
	def void testRelationalOperators() {
		'1 < 1'.assertType('anonymous boolean')
		'1 <= 1'.assertType('anonymous boolean')
		'1 > 1'.assertType('anonymous boolean')
		'1 >= 1'.assertType('anonymous boolean')
	}

	@Test 
	def void testAdditiveOperators() {
		'1 + 1'.assertType('anonymous integer')
		'1.0 + 1'.assertType('anonymous real')
		'1 + 1.0'.assertType('anonymous real')
		'1.0 + 1.0'.assertType('anonymous real')
		'1 - 1'.assertType('anonymous integer')
		'1.0 - 1'.assertType('anonymous real')
		'1 - 1.0'.assertType('anonymous real')
		'1.0 - 1.0'.assertType('anonymous real')
		'"a" & "b"'.assertType('anonymous string')
	}

	@Test 
	def void testAdditiveCollectionOperators() {
		'b & b'.assertType('b: bag of integer', 'bag of integer')
		's & s'.assertType('s: sequence of integer', 'sequence of integer')
		's & s'.assertType('s: set of integer', 'set of integer')
		'b union b'.assertType('b: bag of integer', 'bag of integer')
		's union s'.assertType('s: sequence of integer', 'sequence of integer')
		's union s'.assertType('s: set of integer', 'set of integer')
		'b not_in b'.assertType('b: bag of integer', 'bag of integer')
		's not_in s'.assertType('s: sequence of integer', 'sequence of integer')
		's not_in s'.assertType('s: set of integer', 'set of integer')
	}
	
	@Test 
	def void testMultiplicativeOperators() {
		'1 * 1'.assertType('anonymous integer')
		'1.0 * 1'.assertType('anonymous real')
		'1 * 1.0'.assertType('anonymous real')
		'1.0 * 1.0'.assertType('anonymous real')
		'1 / 1'.assertType('anonymous integer')
		'1.0 / 1'.assertType('anonymous real')
		'1 / 1.0'.assertType('anonymous real')
		'1.0 / 1.0'.assertType('anonymous real')
		'1 rem 1'.assertType('anonymous integer')
		'1 mod 1'.assertType('anonymous integer')
		'1 ** 1'.assertType('anonymous real')
	}
	
	@Test 
	def void testBug9184() {
		assertType('bar / 2', 'bar: long_integer', 'anonymous long_integer')
	}
	
	@Test 
	def void testMultiplicativeCollectionOperators() {
		'b intersection b'.assertType('b: bag of integer', 'bag of integer')
		's intersection s'.assertType('s: sequence of integer', 'sequence of integer')
		's intersection s'.assertType('s: set of integer', 'set of integer')
		'b disunion b'.assertType('b: bag of integer', 'bag of integer')
		's disunion s'.assertType('s: sequence of integer', 'sequence of integer')
		's disunion s'.assertType('s: set of integer', 'set of integer')
	}
	
	@Test 
	def void testUnaryOperators() {
		'-1'.assertType('anonymous integer')
		'-1.0'.assertType('anonymous real')
		'+1'.assertType('anonymous integer')
		'+1.0'.assertType('anonymous real')
		'abs 1'.assertType('anonymous integer')
		'abs 1.0'.assertType('anonymous real')
		'not true'.assertType('anonymous boolean')
	}
	
	@Test 
	def void testVariable() {
		assertType('bar', 'bar: integer', 'integer')
	}
	
	@Test 
	def void testLoopVariable() {
		assertType("for baz in bar'elements loop ^baz; end", 'bar: bag of integer', 'integer')
	}
	
	@Test 
	def void testParameter() {
		assertType('param', 'integer')
	}
	
	@Test 
	def void testCastExpression() {
		assertType('integer(0.0)', 'integer')
		assertType('bag of integer(null)', 'bag of integer')
	}
	
	@Test 
	def void testStringIndex() {
		assertType('"foo"[1..2]', 'anonymous sequence of anonymous character')
		assertType('"foo"[1]', 'anonymous character')
	}
	
	@Test 
	def void testCollectionIndex() {
		's[0]'.assertType('s: sequence of integer', 'integer')
		's[0..1]'.assertType('s: sequence of integer', 'anonymous sequence of integer')
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