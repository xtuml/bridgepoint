package org.xtuml.bp.xtext.masl.parser

import org.eclipse.xtext.conversion.ValueConverterException
import org.eclipse.xtext.conversion.impl.AbstractValueConverter
import org.eclipse.xtext.nodemodel.INode
import org.xtuml.bp.xtext.masl.masl.structure.StateType
import static org.xtuml.bp.xtext.masl.masl.structure.StateType.*

/**
 * In Xtext, an enum literal cannot be represented by two keywords, like 'assigner' 'start'.
 * This is why we need a value converter.
 */
class StateTypeValueConverter extends AbstractValueConverter<StateType> {
	
	override toString(StateType value) throws ValueConverterException {
		switch value {
			case ASSIGNER_START:
				'assigner start'
			case ASSIGNER:
				'assigner'
			case CREATION:
				'creation'
			case TERMINAL:
				'terminal'	
		}
	}
	
	override toValue(String string, INode node) throws ValueConverterException {
		val text = node.leafNodes.filter[!hidden].map[text].join(':')
		switch text {
			case 'assigner:start':
				ASSIGNER_START
			case 'assigner':
				ASSIGNER
			case 'creation':
				CREATION
			case 'terminal':
				TERMINAL
			default:
				throw new ValueConverterException('Illegal state type ' + string, node, null)	
		}
	}
}