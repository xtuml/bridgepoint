package org.xtuml.bp.xtext.masl.validation

import java.util.List
import javax.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar
import org.xtuml.bp.xtext.masl.MASLExtensions
import org.xtuml.bp.xtext.masl.masl.behavior.BehaviorPackage
import org.xtuml.bp.xtext.masl.masl.behavior.Expression
import org.xtuml.bp.xtext.masl.masl.behavior.IndexedExpression
import org.xtuml.bp.xtext.masl.masl.behavior.OperationCall
import org.xtuml.bp.xtext.masl.masl.behavior.SimpleFeatureCall
import org.xtuml.bp.xtext.masl.masl.behavior.TerminatorOperationCall
import org.xtuml.bp.xtext.masl.masl.structure.Parameterized
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorDefinition
import org.xtuml.bp.xtext.masl.masl.types.AbstractTypeReference
import org.xtuml.bp.xtext.masl.masl.types.ConstrainedArrayTypeReference
import org.xtuml.bp.xtext.masl.masl.types.TypeDeclaration
import org.xtuml.bp.xtext.masl.masl.types.TypesPackage
import org.xtuml.bp.xtext.masl.masl.types.UnconstrainedArrayDefinition
import org.xtuml.bp.xtext.masl.typesystem.BuiltinType
import org.xtuml.bp.xtext.masl.typesystem.CollectionType
import org.xtuml.bp.xtext.masl.typesystem.InstanceType
import org.xtuml.bp.xtext.masl.typesystem.MaslExpectedTypeProvider
import org.xtuml.bp.xtext.masl.typesystem.MaslType
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeConformanceComputer
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider
import org.xtuml.bp.xtext.masl.typesystem.RangeType
import org.xtuml.bp.xtext.masl.typesystem.StructureType

import static org.xtuml.bp.xtext.masl.typesystem.BuiltinType.*
import static org.xtuml.bp.xtext.masl.validation.MaslIssueCodesProvider.*

class TypeValidator extends AbstractMASLValidator {
	
	override register(EValidatorRegistrar registrar) {
	}
	
	@Inject extension MaslTypeProvider
	@Inject extension MASLExtensions
	@Inject extension MaslExpectedTypeProvider
	@Inject extension MaslTypeConformanceComputer
	@Inject extension SignatureProvider
	@Inject extension BehaviorPackage
	@Inject extension TypesPackage
	@Inject extension IQualifiedNameProvider
	
	@Check
	def simpleFeatureCall(SimpleFeatureCall it) {
		if(receiver != null && !receiver.eIsProxy) {
			val primitiveType = receiver.maslType.primitiveType
			switch primitiveType {
				InstanceType, StructureType: {
					// noop
				} 
				default:	
					addIssue('Cannot call a feature on ' + receiver?.eClass?.name + ' ' + receiver.fullyQualifiedName?.lastSegment, it, featureCall_Receiver, INVALID_FEATURE_CALL)
			}
		} 
		if(feature != null && !feature.eIsProxy && feature.isOperation && !(eContainer instanceof OperationCall)) 
			addIssue('Action ' + feature.fullyQualifiedName?.lastSegment + ' must be called with parentheses', it, featureCall_Feature, INVALID_FEATURE_CALL)
	}

	@Check
	def operationCall(OperationCall it) {
		val receiver = it.receiver
		if(receiver != null && !receiver.eIsProxy) {
			switch receiver {
				SimpleFeatureCall: {
					if(receiver.feature.isOperation) {
						val parameterized = receiver.feature as Parameterized
						val expectedNumParameters = (parameterized).parameters.size
						if(expectedNumParameters != arguments.size) 
							addIssue('''The action «
								parameterized.fullyQualifiedName»«parameterized.parametersAsString
								» cannot be called with arguments («
									arguments.map[maslType.toString].join(', ')
								»)''', it, operationCall_Receiver, WRONG_NUMBER_OF_ARGUMENTS)
					} else if(receiver.feature instanceof TypeDeclaration) {
						if(arguments.size != 1) 
							addIssue('Type cast must have exactly one argument', it, operationCall_Receiver, WRONG_NUMBER_OF_ARGUMENTS)
					}
					return
				}
				AbstractTypeReference: return
			}
			addIssue('Cannot call ' + receiver.eClass.name + ' with parentheses', it, operationCall_Receiver, INVALID_OPERATION_CALL)
		}
	}
	
	@Check
	def terminatorOperationCall(TerminatorOperationCall it) {
		switch receiver {
			TerminatorDefinition, 
			SimpleFeatureCall: {
				// noop
			}
			default:
				addIssue('Cannot call terminator operation on ' + receiver.eClass.name, receiver, null)
		}
	}
	
	@Check
	def indexedExpression(IndexedExpression it) {
		if (receiver != null && !receiver.eIsProxy) {
			val primitiveType = receiver.maslType.primitiveType
			switch primitiveType {
				CollectionType,
				BuiltinType case STRING: {
					// noop
				}
				default:
					addIssue('Cannot use ' + receiver.eClass.name + ' as indexed element', receiver, null)
			}
		}
	}
	
	@Check
	def checkTypeExpectations(EObject it) {
		eClass.EAllReferences.forEach [ ref |
			if(eIsSet(ref)) {
				if(ref.many) {
					(eGet(ref) as List<? extends EObject>).forEach[ value, index |
						val expectedTypes = it.getExpectedTypes(ref, index)
						value.checkTypeExpectation(expectedTypes, it, ref, index)							
					]
					
				} else {
					val expectedTypes = it.getExpectedTypes(ref, -1)
					(eGet(ref) as EObject).checkTypeExpectation(expectedTypes, it, ref, INSIGNIFICANT_INDEX)	
				}
			}
		]
		if(it instanceof Expression && maslType == MISSING_TYPE) {
			error("Cannot determine type of " + eClass.name, null)
		}
	} 
	
	private def checkTypeExpectation(EObject element, List<MaslType> expectedTypes, EObject owner, EReference reference, int index) {
		if(!expectedTypes.empty) {
			val realType = element.maslType
			if(!expectedTypes.exists[realType.isAssignableTo(it)]) 
				addIssue('''Expected «expectedTypes.map[toString].join(' or ')» but was «realType».''', owner, reference, index, WRONG_TYPE)
		}
	}
	
	@Check 
	def checkIndexType(IndexedExpression it) {
		val indexType = brackets.maslType
		if(indexType != INTEGER && !(indexType.primitiveType instanceof RangeType)) 
			addIssue('''Index type should be integer or range but is «indexType».''', it, indexedExpression_Brackets, WRONG_TYPE)
	}
	
	@Check
	def checkConstrainedArrayTypeReference(ConstrainedArrayTypeReference it) {
		if(!(unconstrained?.definition instanceof UnconstrainedArrayDefinition)) {
			addIssue("The constrained type '" + unconstrained.name  + "' must be an unconstrained array type", it, constrainedArrayTypeReference_Unconstrained, WRONG_TYPE)
		} 
	}
}