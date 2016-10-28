package org.xtuml.bp.xtext.masl.parser

import com.google.inject.Inject
import org.eclipse.xtext.common.services.DefaultTerminalConverters
import org.eclipse.xtext.conversion.IValueConverter
import org.eclipse.xtext.conversion.ValueConverter
import org.xtuml.bp.xtext.masl.masl.structure.StateType

/**
 * @see StateTypeValueConverter
 */
class MASLValueConverters extends DefaultTerminalConverters {

	@Inject
	private StateTypeValueConverter stateTypeValueConverter
	
	@ValueConverter(rule = "org.xtuml.bp.xtext.masl.MASL.StateTypeSyntax")
	def IValueConverter<StateType> MASLStateTypeSyntax() {
		return stateTypeValueConverter
	}
}