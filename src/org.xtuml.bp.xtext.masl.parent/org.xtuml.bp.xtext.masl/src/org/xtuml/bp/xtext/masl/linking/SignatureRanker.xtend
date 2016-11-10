package org.xtuml.bp.xtext.masl.linking

import com.google.inject.Inject
import java.util.List
import org.xtuml.bp.xtext.masl.masl.structure.Parameterized
import org.xtuml.bp.xtext.masl.typesystem.MaslType
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeConformanceComputer
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider

import static java.lang.Math.*

class SignatureRanker {
	@Inject extension MaslTypeProvider
	@Inject extension MaslTypeConformanceComputer

	public static val PERFECT_MATCH = Integer.MAX_VALUE
	public static val ACCEPTABLE_MATCH = Integer.MAX_VALUE / 2
	public static val JUST_NUMBER_OF_ARGUMENTS_MATCH = 0
	public static val JUST_TYPE_MATCH = Integer.MIN_VALUE / 2
	public static val JUST_NAME_MATCH = Integer.MIN_VALUE + 1
	public static val NO_MATCH = Integer.MIN_VALUE

	def int getRank(Parameterized elementToRank, List<MaslType> givenTypes) {
		val parameters = elementToRank.parameters
		var numSameType = 0
		var numAssignableType = 0
		for (var i = 0; i < min(parameters.size, givenTypes.size); i++) {
			val paramType = parameters.get(i).maslType
			val argumentType = givenTypes.get(i)
			if (argumentType == paramType)
				numSameType++
			else if (argumentType.isAssignableTo(paramType))
				numAssignableType++
		}
		if (givenTypes.size == parameters.size) {
			if (numSameType == givenTypes.size)
				return PERFECT_MATCH
			if (numSameType + numAssignableType == givenTypes.size)
				return ACCEPTABLE_MATCH + numSameType
			else
				return JUST_NUMBER_OF_ARGUMENTS_MATCH + numSameType * 1000 + numAssignableType
		} else {
			return JUST_TYPE_MATCH + numSameType * 1000 + numAssignableType
		}
	}
}
