package org.xtuml.bp.xtext.masl.validation

import java.util.List
import javax.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar
import org.xtuml.bp.xtext.masl.MASLExtensions
import org.xtuml.bp.xtext.masl.linking.SignatureRanker
import org.xtuml.bp.xtext.masl.masl.behavior.BehaviorPackage
import org.xtuml.bp.xtext.masl.masl.behavior.Expression
import org.xtuml.bp.xtext.masl.masl.behavior.IndexedExpression
import org.xtuml.bp.xtext.masl.masl.behavior.NavigateExpression
import org.xtuml.bp.xtext.masl.masl.behavior.OperationCall
import org.xtuml.bp.xtext.masl.masl.behavior.SimpleFeatureCall
import org.xtuml.bp.xtext.masl.masl.behavior.TerminatorOperationCall
import org.xtuml.bp.xtext.masl.masl.structure.AssocRelationshipDefinition
import org.xtuml.bp.xtext.masl.masl.structure.Parameterized
import org.xtuml.bp.xtext.masl.masl.structure.StructurePackage
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorDefinition
import org.xtuml.bp.xtext.masl.masl.types.AbstractTypeReference
import org.xtuml.bp.xtext.masl.masl.types.ConstrainedArrayTypeReference
import org.xtuml.bp.xtext.masl.masl.types.TypeDeclaration
import org.xtuml.bp.xtext.masl.masl.types.TypesPackage
import org.xtuml.bp.xtext.masl.masl.types.UnconstrainedArrayDefinition
import org.xtuml.bp.xtext.masl.scoping.ProjectScopeIndexProvider
import org.xtuml.bp.xtext.masl.typesystem.BuiltinType
import org.xtuml.bp.xtext.masl.typesystem.CollectionType
import org.xtuml.bp.xtext.masl.typesystem.InstanceType
import org.xtuml.bp.xtext.masl.typesystem.MaslExpectedTypeProvider
import org.xtuml.bp.xtext.masl.typesystem.MaslType
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeConformanceComputer
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider
import org.xtuml.bp.xtext.masl.typesystem.StructureType

import static org.xtuml.bp.xtext.masl.linking.SignatureRanker.*
import static org.xtuml.bp.xtext.masl.typesystem.BuiltinType.*
import static org.xtuml.bp.xtext.masl.validation.MaslIssueCodesProvider.*
import org.xtuml.bp.xtext.masl.masl.structure.ObjectFunctionDefinition
import org.xtuml.bp.xtext.masl.masl.structure.ObjectServiceDefinition
import org.xtuml.bp.xtext.masl.masl.structure.ObjectFunctionDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectServiceDeclaration

class TypeValidator extends AbstractMASLValidator {
	
	override register(EValidatorRegistrar registrar) {
	}
	
	@Inject extension MaslTypeProvider
	@Inject extension MASLExtensions
	@Inject extension MaslExpectedTypeProvider
	@Inject extension MaslTypeConformanceComputer
	@Inject extension SignatureProvider
	@Inject extension StructurePackage structurePackage
	@Inject extension BehaviorPackage 
	@Inject extension TypesPackage
	@Inject extension IQualifiedNameProvider
	@Inject extension ProjectScopeIndexProvider
	@Inject extension SignatureRanker
	
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
	
	private def checkTypeExpectation(EObject element, Iterable<MaslType> expectedTypes, EObject owner, EReference reference, int index) {
		if(!expectedTypes.empty) {
			val realType = element.maslType
			if(!expectedTypes.exists[realType.isAssignableTo(it)]) 
				addIssue('''Expected «expectedTypes.map[toString].join(' or ')» but was «realType».''', owner, reference, index, WRONG_TYPE)
		}
	}
	
	@Check
	def checkConstrainedArrayTypeReference(ConstrainedArrayTypeReference it) {
		if(!(unconstrained?.definition instanceof UnconstrainedArrayDefinition)) {
			addIssue("The constrained type '" + unconstrained.name  + "' must be an unconstrained array type", it, constrainedArrayTypeReference_Unconstrained, WRONG_TYPE)
		} 
	}
	
	@Check
	def checkNavigateExpression(NavigateExpression expr) {
		if(expr.with != null) {
			val relationship = expr.navigation.relationship
			if(!(relationship instanceof AssocRelationshipDefinition))
				addIssue("Navigation expression with 'with' can only use an association relationship", expr.navigation, structurePackage.relationshipNavigation_Relationship, INCONSISTENT_RELATIONSHIP_NAVIGATION)
			if(expr.navigation.object != null) 
				addIssue("Navigation expression with 'with' can only use an association class", expr.navigation, structurePackage.relationshipNavigation_Object, INCONSISTENT_RELATIONSHIP_NAVIGATION)
			if(expr.navigation.objectOrRole != (relationship as AssocRelationshipDefinition).object)
				addIssue("Navigation expression with 'with' can only use an association class", expr.navigation, structurePackage.relationshipNavigation_ObjectOrRole, INCONSISTENT_RELATIONSHIP_NAVIGATION)
		}
	}
	
	@Check 
	def checkDefinitionMatchesDeclaration(Parameterized definition) {
		val declarationClass = definition.declarationClass
		if(declarationClass != null) {
			val defTypes = definition.parameters.map[maslType]
			val allDeclarations = definition
							.getDeclarations(declarationClass, definition.index)
							.filter(Parameterized)
							.map[it -> getRank(defTypes)]
			if(allDeclarations.empty) 
				return
			val bestDeclaration = allDeclarations.maxBy[value]
			if(bestDeclaration.value != PERFECT_MATCH)
				addIssue("Parameter types " + definition.parametersAsString + " do not match declared parameters " + bestDeclaration.key.parametersAsString,
					definition, structurePackage.abstractNamed_Name, DECLARATION_MISSMATCH)
			else {
				val defType = definition.maslType
				val declType = bestDeclaration.key.maslType
				if(defType != declType) {
					addIssue("Return type " + defType + " does not match declared return type " + declType,
						definition, structurePackage.abstractNamed_Name, DECLARATION_MISSMATCH)
				} else {
					val defParams = definition.parameters
					val declParams = bestDeclaration.key.parameters
					for(var i=0; i<definition.parameters.size; i++) {
						val defParamMode = defParams.get(i).mode
						val declParamMode = declParams.get(i).mode
						if(defParamMode != declParamMode) 
							addIssue("Parameter mode '" + defParamMode.toString.toLowerCase 
								+ "' does not match declared mode '" + declParamMode.toString.toLowerCase + "'",
								defParams.get(i), parameter_Mode, DECLARATION_MISSMATCH)
					}
					switch definition {
						ObjectFunctionDefinition:
							if(definition.instance.xor((bestDeclaration.key as ObjectFunctionDeclaration).instance))
								addIssue("Definition scope does not match declaration scope ('instance')", definition, structurePackage.abstractNamed_Name, DECLARATION_MISSMATCH)
						ObjectServiceDefinition:
							if(definition.instance.xor((bestDeclaration.key as ObjectServiceDeclaration).instance))
								addIssue("Definition scope does not match declaration scope ('instance')", definition, structurePackage.abstractNamed_Name, DECLARATION_MISSMATCH)
					}				
				}
			} 
		}
	}
}