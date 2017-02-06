package org.xtuml.bp.xtext.masl.tests

import com.google.inject.Inject
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith
import org.xtuml.bp.xtext.masl.masl.behavior.ActionCall
import org.xtuml.bp.xtext.masl.masl.behavior.CharacteristicCall
import org.xtuml.bp.xtext.masl.masl.behavior.Expression
import org.xtuml.bp.xtext.masl.masl.behavior.FindExpression
import org.xtuml.bp.xtext.masl.masl.behavior.SimpleFeatureCall
import org.xtuml.bp.xtext.masl.masl.structure.MaslModel

import static org.junit.Assert.*
import org.xtuml.bp.xtext.masl.masl.structure.DomainServiceDefinition

@RunWith(XtextRunner)
@InjectWith(MASLInjectorProvider)
class SyntacticPredicateTest {
	
	@Inject extension ParseHelper<MaslModel>
	
	@Test
	def void testFindExpression() {
		val expression = 'find foo(a=b);'.parseExpression
		assertTrue(expression.eClass.name, expression instanceof FindExpression)
		assertTrue((expression as FindExpression).where != null)
	} 

	@Test
	def void testFindExpression2() {
		val expression = '(find foo(a=b))\'length;'.parseExpression
		assertTrue(expression.eClass.name, expression instanceof CharacteristicCall)
		assertTrue((expression as CharacteristicCall).receiver instanceof FindExpression)
	} 

	@Test
	def void testParenthesizedExpression() {
		val expression = '(foo);'.parseExpression
		assertTrue(expression.eClass.name, expression instanceof SimpleFeatureCall)
	} 

	@Test
	def void testActionCall() {
		val expression = 'foo();'.parseExpression
		assertTrue(expression.eClass.name, expression instanceof ActionCall)
	} 

	@Test
	def void testActionCall2() {
		val expression = 'this.foo();'.parseExpression
		assertTrue(expression.eClass.name, expression instanceof ActionCall)
	} 
	
	@Test
	def void testSimpleFeatureCall() {
		val expression = 'this.foo;'.parseExpression
		assertTrue(expression.eClass.name, expression instanceof SimpleFeatureCall)
	} 
	
	@Test
	def void testCharacteristicCall() {
		val expression = 'this\'foo;'.parseExpression
		assertTrue(expression.eClass.name, expression instanceof CharacteristicCall)
	} 
	
	@Test
	def void testCharacteristicCall2() {
		val expression = 'this\'foo();'.parseExpression
		assertTrue(expression.eClass.name, expression instanceof CharacteristicCall)
	} 
	
	private def parseExpression(CharSequence expression) {
		val model = '''
			service foo::foo() is
			begin
				«expression»
			end;
		'''.parse;
		val body = (model.elements.head as DomainServiceDefinition).body
		assertEquals(1, body.statements.size)
		(body.statements.head) as Expression
	}
}