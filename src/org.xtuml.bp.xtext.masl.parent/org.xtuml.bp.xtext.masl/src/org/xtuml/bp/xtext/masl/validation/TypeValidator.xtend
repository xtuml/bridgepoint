package org.xtuml.bp.xtext.masl.validation

import java.util.List
import javax.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar
import org.xtuml.bp.xtext.masl.MASLExtensions
import org.xtuml.bp.xtext.masl.linking.RankedCandidate
import org.xtuml.bp.xtext.masl.masl.behavior.ActionCall
import org.xtuml.bp.xtext.masl.masl.behavior.BehaviorPackage
import org.xtuml.bp.xtext.masl.masl.behavior.Expression
import org.xtuml.bp.xtext.masl.masl.behavior.GenerateStatement
import org.xtuml.bp.xtext.masl.masl.behavior.IndexedExpression
import org.xtuml.bp.xtext.masl.masl.behavior.LinkExpression
import org.xtuml.bp.xtext.masl.masl.behavior.NavigateExpression
import org.xtuml.bp.xtext.masl.masl.behavior.SimpleFeatureCall
import org.xtuml.bp.xtext.masl.masl.behavior.StatementList
import org.xtuml.bp.xtext.masl.masl.behavior.TerminatorActionCall
import org.xtuml.bp.xtext.masl.masl.structure.AbstractActionDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.AssocRelationshipDefinition
import org.xtuml.bp.xtext.masl.masl.structure.ObjectServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectServiceDefinition
import org.xtuml.bp.xtext.masl.masl.structure.Parameterized
import org.xtuml.bp.xtext.masl.masl.structure.StructurePackage
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorDefinition
import org.xtuml.bp.xtext.masl.masl.structure.Visualized
import org.xtuml.bp.xtext.masl.masl.types.AbstractTypeReference
import org.xtuml.bp.xtext.masl.masl.types.ConstrainedArrayTypeReference
import org.xtuml.bp.xtext.masl.masl.types.TypeDeclaration
import org.xtuml.bp.xtext.masl.masl.types.TypesPackage
import org.xtuml.bp.xtext.masl.masl.types.UnconstrainedArrayDefinition
import org.xtuml.bp.xtext.masl.scoping.ProjectScopeIndexProvider
import org.xtuml.bp.xtext.masl.typesystem.BuiltinType
import org.xtuml.bp.xtext.masl.typesystem.CollectionType
import org.xtuml.bp.xtext.masl.typesystem.EnumType
import org.xtuml.bp.xtext.masl.typesystem.InstanceType
import org.xtuml.bp.xtext.masl.typesystem.MaslExpectedTypeProvider
import org.xtuml.bp.xtext.masl.typesystem.MaslType
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeConformanceComputer
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider
import org.xtuml.bp.xtext.masl.typesystem.StructureType
import org.xtuml.bp.xtext.masl.typesystem.TypeOfType

import static org.xtuml.bp.xtext.masl.typesystem.BuiltinType.*
import static org.xtuml.bp.xtext.masl.validation.MaslIssueCodesProvider.*
import org.xtuml.bp.xtext.masl.masl.behavior.CharacteristicCall
import org.xtuml.bp.xtext.masl.typesystem.DictionaryType
import org.xtuml.bp.xtext.masl.masl.types.Enumerator

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
	@Inject extension RankedCandidate.Factory
	@Inject extension VisibilityProvider
	
	@Check
	def simpleFeatureCall(SimpleFeatureCall it) {
		if(receiver != null && !receiver.eIsProxy) {
			val primitiveType = receiver.maslType.primitiveType
			switch primitiveType {
				InstanceType, 
				StructureType,
				TypeOfType case primitiveType.type instanceof EnumType: {
					// noop
				} 
				default:	
					addIssue('Cannot call a feature on ' + receiver?.eClass?.name + ' ' + receiver.fullyQualifiedName?.lastSegment, it, featureCall_Receiver, INVALID_FEATURE_CALL)
			}
		} 
		if(feature != null && !feature.eIsProxy ) {
			if(feature instanceof AbstractActionDeclaration && !(eContainer instanceof ActionCall)) 
				addIssue('Action ' + feature.fullyQualifiedName?.lastSegment + ' must be called with parentheses', it, featureCall_Feature, INVALID_FEATURE_CALL)
			if(!isVisible)
				addIssue(feature?.eClass?.name + ' ' + feature.name + ' is not visible in this context.', it, featureCall_Feature, INVISIBLE_FEATURE) 
		}
	}

	@Check
	def actionCall(ActionCall it) {
		val receiver = it.receiver
		if(receiver != null && !receiver.eIsProxy) {
			switch receiver {
				SimpleFeatureCall: {
					if(receiver.feature.isAction) {
						val parameterized = receiver.feature as Parameterized
						val expectedNumParameters = (parameterized).parameters.size
						val isStatement = eContainmentFeature.EType === statement
						if(expectedNumParameters != arguments.size) 
							addIssue('''The action «
								parameterized.fullyQualifiedName»«parameterized.parametersAsString
								» cannot be called with arguments («
									arguments.map[maslType.toString].join(', ')
								»)''', it, actionCall_Receiver, WRONG_NUMBER_OF_ARGUMENTS)
						else if(isStatement && parameterized.hasReturnType) 
							addIssue('''Cannot call function «
								parameterized.fullyQualifiedName»«parameterized.parametersAsString
								» with return value in statement context.''',
								it, actionCall_Receiver, FUNCTION_CALLED_AS_SERVICE)
					} else if(receiver.feature instanceof TypeDeclaration) {
						if(arguments.size != 1) 
							addIssue('Type cast must have exactly one argument', it, actionCall_Receiver, WRONG_NUMBER_OF_ARGUMENTS)
					}
					return
				}
				AbstractTypeReference: return
			}
			addIssue('Cannot call ' + receiver.eClass.name + ' with parentheses', it, actionCall_Receiver, INVALID_ACTION_CALL)
		}
	}
	
	@Check
	def terminatorActionCall(TerminatorActionCall it) {
		switch receiver {
			TerminatorDefinition, 
			SimpleFeatureCall: {
				// noop
			}
			default:
				addIssue('Cannot call terminator action on ' + receiver.eClass.name, receiver, null)
		}
		if(terminatorAction != null && !terminatorAction.eIsProxy && !isVisible) {
			addIssue(terminatorAction?.eClass?.name + ' ' + terminatorAction.name + ' is not visible in this context.', it, terminatorActionCall_TerminatorAction, INVISIBLE_FEATURE)
		}
	}
	
	@Check
	def characteristicCall(CharacteristicCall it) {
		if(arguments.size !== characteristic.parameters.size) {
			addIssue('''The characteristic «
				characteristic.name»«characteristic.parametersAsString
				» cannot be called with arguments («
					arguments.map[maslType.toString].join(', ')
				»)''', it, characteristicCall_Characteristic, WRONG_NUMBER_OF_ARGUMENTS)
		}
	}
	
	@Check
	def indexedExpression(IndexedExpression it) {
		if (receiver != null && !receiver.eIsProxy) {
			val primitiveType = receiver.maslType.primitiveType
			switch primitiveType {
				CollectionType,
				DictionaryType,
				BuiltinType case STRING: {
					// noop
				}
				default:
					addIssue('Cannot use ' + receiver.eClass.name + ' as indexed element', receiver, INVALID_INDEXED_EXPRESSION)
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
			if(!expectedTypes.exists[realType.isAssignableTo(it)]) {
				if(realType.primitiveType instanceof EnumType 
					&& (owner instanceof ActionCall || owner instanceof TerminatorActionCall || owner instanceof GenerateStatement) 
					&& element instanceof SimpleFeatureCall) {
					val enumeratorCandidate = (element as SimpleFeatureCall).feature
					if(enumeratorCandidate instanceof Enumerator) {
						val enumeratorName = enumeratorCandidate.name
						for(expectedType: expectedTypes) {
							val enumTypeCandidate = expectedType.primitiveType 
							if(enumTypeCandidate instanceof EnumType) {
								if(enumTypeCandidate.enumType.enumerators.exists[name == enumeratorName])
									// TODO re-link the enumerator in this case
									return
							}						
						}
					}
				}			
				addIssue('''Expected «expectedTypes.map[toString].join(' or ')» but was «realType».''', owner, reference, index, WRONG_TYPE)
			}
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
							.map[rankParameterized(defTypes, definition.hasReturnType)]
			if(allDeclarations.empty) 
				return
			val bestMatch = allDeclarations.maxBy[score]
			val bestDeclaration = bestMatch.candidate as Parameterized
			if(!bestMatch.exact)
				addIssue("Parameter types " + definition.parametersAsString + " do not match declared parameters " + bestDeclaration.parametersAsString,
					definition, structurePackage.abstractNamed_Name, DECLARATION_MISSMATCH)
			else {
				val defType = definition.maslType
				val declType = bestDeclaration.maslType
				if(defType != declType) {
					addIssue("Return type " + defType + " does not match declared return type " + declType,
						definition, structurePackage.abstractNamed_Name, DECLARATION_MISSMATCH)
				} else {
					val defParams = definition.parameters
					val declParams = bestDeclaration.parameters
					for(var i=0; i<definition.parameters.size; i++) {
						val defParamMode = defParams.get(i).mode
						val declParamMode = declParams.get(i).mode
						if(defParamMode != declParamMode) 
							addIssue("Parameter mode '" + defParamMode.toString.toLowerCase 
								+ "' does not match declared mode '" + declParamMode.toString.toLowerCase + "'",
								defParams.get(i), parameter_Mode, DECLARATION_MISSMATCH)
					}
					switch definition {
						ObjectServiceDefinition:
							if(definition.isInstance.xor((bestDeclaration as ObjectServiceDeclaration).isInstance))
								addIssue("Definition scope does not match declaration scope ('instance')", definition, structurePackage.abstractNamed_Name, DECLARATION_MISSMATCH)
					}
					if(definition instanceof Visualized) {
						if(definition.visibility != (bestDeclaration as Visualized).visibility) {
							addIssue("Visibility does not match declaration.", definition, visualized_Visibility, DECLARATION_MISSMATCH)
						}
					}			
				}
			} 
		}
	}
	
	@Check
	def void checkLinkExpressionHasAssociativeRelationship(LinkExpression it) {
		// link statement can have any type of relationship 
		if(eContainer instanceof StatementList || navigation?.relationship instanceof AssocRelationshipDefinition)
			return 
		else
			addIssue("Relationship '" + navigation?.relationship?.name + "' in link expression must be associative.", navigation, structurePackage.relationshipNavigation_Relationship, INVALID_LINK_EXPRESSION)
	}
}