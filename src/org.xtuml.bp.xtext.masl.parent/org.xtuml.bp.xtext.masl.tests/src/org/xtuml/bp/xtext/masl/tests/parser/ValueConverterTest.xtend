package org.xtuml.bp.xtext.masl.tests.parser

import com.google.inject.Inject
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.conversion.IValueConverterService
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.nodemodel.impl.CompositeNode
import org.eclipse.xtext.nodemodel.impl.LeafNode
import org.junit.Test
import org.junit.runner.RunWith
import org.xtuml.bp.xtext.masl.masl.structure.StateType
import org.xtuml.bp.xtext.masl.tests.MASLInjectorProvider
import org.xtuml.bp.xtext.masl.tests.parser.ValueConverterTest.DummyLeafNode

import static extension org.junit.Assert.*

@RunWith(XtextRunner)
@InjectWith(MASLInjectorProvider)
class ValueConverterTest {
	
	@Inject extension IValueConverterService
	
	@Test
	def void testStateType() {
		'assigner start'.assertEquals(StateType.ASSIGNER_START.toString('org.xtuml.bp.xtext.masl.MASL.StateTypeSyntax'))		
		'assigner'.assertEquals(StateType.ASSIGNER.toString('org.xtuml.bp.xtext.masl.MASL.StateTypeSyntax'))		
		'creation'.assertEquals(StateType.CREATION.toString('org.xtuml.bp.xtext.masl.MASL.StateTypeSyntax'))		
		'terminal'.assertEquals(StateType.TERMINAL.toString('org.xtuml.bp.xtext.masl.MASL.StateTypeSyntax'))
		
		StateType.ASSIGNER_START.assertEquals('assigner start'.toValue('org.xtuml.bp.xtext.masl.MASL.StateTypeSyntax', new CompositeNode {
			override getLeafNodes() {
				#[new DummyLeafNode('assigner'), new DummyLeafNode('start')]
			}
		}))		
		StateType.ASSIGNER.assertEquals('assigner'.toValue('org.xtuml.bp.xtext.masl.MASL.StateTypeSyntax', new DummyLeafNode('assigner')))		
		StateType.CREATION.assertEquals('creation'.toValue('org.xtuml.bp.xtext.masl.MASL.StateTypeSyntax', new DummyLeafNode('creation')))		
		StateType.TERMINAL.assertEquals('terminal'.toValue('org.xtuml.bp.xtext.masl.MASL.StateTypeSyntax', new DummyLeafNode('terminal')))
	}
	
	
	@FinalFieldsConstructor
	@Accessors
	private static class DummyLeafNode extends LeafNode {
		val String text
	}
}