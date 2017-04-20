package org.xtuml.bp.xtext.masl.linking

import com.google.inject.Inject
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Data
import org.xtuml.bp.xtext.masl.masl.structure.AbstractFeature
import org.xtuml.bp.xtext.masl.masl.structure.AbstractService
import org.xtuml.bp.xtext.masl.masl.structure.Parameterized
import org.xtuml.bp.xtext.masl.masl.types.TypeDeclaration
import org.xtuml.bp.xtext.masl.typesystem.MaslType
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeConformanceComputer
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider

import static java.lang.Math.*

@Data
class RankedCandidate implements Comparable<RankedCandidate> {
	static val EXACT_MATCH = Integer.MAX_VALUE - 1
	static val ACCEPTABLE_MATCH = Integer.MAX_VALUE / 2
	static val JUST_NUMBER_OF_ARGUMENTS_MATCH = 0
	static val JUST_TYPE_MATCH = Integer.MIN_VALUE / 2
	static val JUST_NAME_MATCH = Integer.MIN_VALUE + 1

	EObject candidate
	int score

	private new(EObject candidate, int score) {
		this.candidate = candidate
		if(candidate.eResource.URI.fileExtension != 'int')
			this.score = score + 1
		else 
			this.score = score
	}
		
	def boolean isExact() {
		score >= EXACT_MATCH
	}
	
	def boolean isAcceptable() {
		score >= ACCEPTABLE_MATCH
	}
	
	override compareTo(RankedCandidate other) {
		if(other == null)
			1
		else if (score < other.score) // compare to avoid integer overflow
			-1
		else if (score == other.score) 
			0
		else 
			1
	}
	
	static class Factory {
		@Inject extension MaslTypeProvider
		@Inject extension MaslTypeConformanceComputer
	
		def RankedCandidate rank(AbstractFeature feature, List<MaslType> argumentTypes, boolean expectReturnType) {
			switch feature {
				TypeDeclaration:
					if(argumentTypes.size === 1 && !expectReturnType)
						return new RankedCandidate(feature, EXACT_MATCH)
					else 
						return new RankedCandidate(feature, JUST_NAME_MATCH)
				Parameterized:
					return feature.rankParameterized(argumentTypes, expectReturnType)
				default:
					return new RankedCandidate(feature, JUST_NAME_MATCH)
			}
		}
		
		def RankedCandidate rankParameterized(Parameterized elementToRank, List<MaslType> givenTypes, boolean expectReturnType) {
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
				val hasReturnType = elementToRank instanceof AbstractService && (elementToRank as AbstractService).returnType != null
				if(expectReturnType === hasReturnType) {
					if (numSameType == givenTypes.size)
						return new RankedCandidate(elementToRank, EXACT_MATCH)
					if (numSameType + numAssignableType == givenTypes.size)
						return new RankedCandidate(elementToRank, ACCEPTABLE_MATCH + numSameType)
				}
				return new RankedCandidate(elementToRank, JUST_NUMBER_OF_ARGUMENTS_MATCH + numSameType * 1000 + numAssignableType)
			} else {
				return new RankedCandidate(elementToRank, JUST_TYPE_MATCH + numSameType * 1000 + numAssignableType)
			}
		}
	}
}
