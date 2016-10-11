package org.xtuml.bp.xtext.masl.validation

import java.util.List
import javax.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar
import org.xtuml.bp.xtext.masl.masl.behavior.BehaviorPackage
import org.xtuml.bp.xtext.masl.masl.behavior.Expression
import org.xtuml.bp.xtext.masl.masl.behavior.IndexedExpression
import org.xtuml.bp.xtext.masl.typesystem.MaslExpectedTypeProvider
import org.xtuml.bp.xtext.masl.typesystem.MaslType
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeConformanceComputer
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider
import org.xtuml.bp.xtext.masl.typesystem.RangeType

import static org.xtuml.bp.xtext.masl.typesystem.BuiltinType.*
import static org.xtuml.bp.xtext.masl.validation.MaslIssueCodesProvider.*

class MASLTypeValidator extends AbstractMASLValidator {
	
	override register(EValidatorRegistrar registrar) {
	}
	
	@Inject extension MaslTypeProvider
	@Inject extension MaslExpectedTypeProvider
	@Inject extension MaslTypeConformanceComputer
	@Inject extension BehaviorPackage
	
	@Check
	def checkTypeExpectations(EObject it) {
		eClass.EAllReferences.forEach [ ref |
			if(eIsSet(ref)) {
				val expectedType = it.getExpectedType(ref)
				if(expectedType != ANY_TYPE) {
					if(ref.many)
						(eGet(ref) as List<? extends EObject>).forEach[ value, index |
							value.checkTypeExpectation(expectedType, it, ref, index)							
						]
					else
						(eGet(ref) as EObject).checkTypeExpectation(expectedType, it, ref, INSIGNIFICANT_INDEX)	
				}
			}
		]
		if(it instanceof Expression && maslType == MISSING_TYPE) {
			warning('Missing type', null)
		}
	} 
	
	private def checkTypeExpectation(EObject element, MaslType expectedType, EObject owner, EReference reference, int index) {
		val realType = element.maslType
		if(!realType.isAssignableTo(expectedType)) {
			error('''Expected «expectedType» but was «realType».''', owner, reference, index, WRONG_TYPE)
		}
	}
	
	@Check def checkIndexType(IndexedExpression it) {
		val indexType = brackets.maslType
		if(indexType != INTEGER && !(indexType.primitiveType instanceof RangeType)) 
			error('''Index type should be integer or range but is «indexType».''', it, indexedExpression_Brackets, WRONG_TYPE)
	}
}