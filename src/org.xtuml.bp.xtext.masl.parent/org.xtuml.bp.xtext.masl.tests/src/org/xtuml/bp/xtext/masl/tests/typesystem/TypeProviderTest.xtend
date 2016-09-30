package org.xtuml.bp.xtext.masl.tests.typesystem

import com.google.inject.Inject
import org.junit.Test
import org.xtuml.bp.xtext.masl.tests.AbstractMaslModelTest
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeExtensions
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider

import static org.junit.Assert.*

class TypeProviderTest extends AbstractMaslModelTest {
	
	@Inject extension MaslTypeProvider
	@Inject extension MaslTypeExtensions
	
	@Test
	def void testLiterals() {
		'1'.assertType('builtin integer')
		'1.1'.assertType('builtin real')
		"'c'".assertType('builtin character')
		'"foo"'.assertType('builtin string')
		'@Pxxx@'.assertType('builtin duration')
		'@xxx@'.assertType('builtin timestamp')
		'true'.assertType('builtin boolean')
		'false'.assertType('builtin boolean')
		'null'.assertType('anonymous builtin any_type')
		'flush'.assertType('anonymous builtin stream_manipulator_type')
		'console'.assertType('anonymous builtin stream_type')
		'endl'.assertType('anonymous builtin stream_manipulator_type')
		'#LINE#'.assertType('anonymous builtin no_type') 
		'#FILE#'.assertType('anonymous builtin no_type') 
	}
	
	@Test 
	def void testStreams() {
		'console << endl'.assertType('anonymous builtin stream_type')
		'console <<< ""'.assertType('anonymous builtin stream_type')
		'console << endl << flush'.assertType('anonymous builtin stream_type')
		'console >> foo'.assertType('anonymous builtin stream_type')
		'console >> foo >> bar'.assertType('anonymous builtin stream_type')
		'console >>> foo'.assertType('anonymous builtin stream_type')
	}
	
	@Test
	def void testRange() {
		'1..2'.assertType('array of builtin integer')
	}
	
	@Test 
	def void testLogicalOperators() {
		'true or false'.assertType('builtin boolean')
		'true xor false'.assertType('builtin boolean')
		'true and false'.assertType('builtin boolean')
	}

	@Test 
	def void testEqualityOperators() {
		'1 = 1'.assertType('builtin boolean')
		'1 /= 1'.assertType('builtin boolean')
	}
	
	@Test 
	def void testRelationalOperators() {
		'1 < 1'.assertType('builtin boolean')
		'1 <= 1'.assertType('builtin boolean')
		'1 > 1'.assertType('builtin boolean')
		'1 >= 1'.assertType('builtin boolean')
	}

	@Test 
	def void testAdditiveOperators() {
		'1 + 1'.assertType('builtin integer')
		'1.0 + 1'.assertType('builtin real')
		'1 + 1.0'.assertType('builtin real')
		'1.0 + 1.0'.assertType('builtin real')
		'1 - 1'.assertType('builtin integer')
		'1.0 - 1'.assertType('builtin real')
		'1 - 1.0'.assertType('builtin real')
		'1.0 - 1.0'.assertType('builtin real')
		'"a" & "b"'.assertType('builtin string')
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
		'1 * 1'.assertType('builtin integer')
		'1.0 * 1'.assertType('builtin real')
		'1 * 1.0'.assertType('builtin real')
		'1.0 * 1.0'.assertType('builtin real')
		'1 / 1'.assertType('builtin integer')
		'1.0 / 1'.assertType('builtin real')
		'1 / 1.0'.assertType('builtin real')
		'1.0 / 1.0'.assertType('builtin real')
		'1 rem 1'.assertType('builtin integer')
		'1 mod 1'.assertType('builtin integer')
		'1 ** 1'.assertType('builtin real')
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
		'-1'.assertType('builtin integer')
		'-1.0'.assertType('builtin real')
		'+1'.assertType('builtin integer')
		'+1.0'.assertType('builtin real')
		'abs 1'.assertType('builtin integer')
		'abs 1.0'.assertType('builtin real')
		'not true'.assertType('builtin boolean')
	}
	
	@Test 
	def void testVariable() {
		assertType('bar', 'bar: integer', 'builtin integer')
	}
	
	@Test 
	def void testLoopVariable() {
		assertType('for baz in bar loop ^baz; end', 'bar: bag of integer', 'builtin integer')
	}
	
	@Test 
	def void testParameter() {
		assertType('param', 'builtin integer')
	}
	
	protected def assertType(CharSequence expression, String expected) {
		expression.assertType('', expected)
	}

	protected def assertType(CharSequence expression, CharSequence varDeclaration, String expected) {
		val expr = getElement('', varDeclaration, expression)
		assertEquals(expected, getType(expr)?.asString)
	}
}