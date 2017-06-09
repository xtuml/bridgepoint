package org.xtuml.bp.xtext.masl.typesystem

import com.google.inject.Inject
import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.xtuml.bp.xtext.masl.masl.behavior.ActionCall
import org.xtuml.bp.xtext.masl.masl.behavior.AssignStatement
import org.xtuml.bp.xtext.masl.masl.behavior.BehaviorPackage
import org.xtuml.bp.xtext.masl.masl.behavior.CaseAlternative
import org.xtuml.bp.xtext.masl.masl.behavior.CaseStatement
import org.xtuml.bp.xtext.masl.masl.behavior.CharacteristicCall
import org.xtuml.bp.xtext.masl.masl.behavior.CreateArgument
import org.xtuml.bp.xtext.masl.masl.behavior.IndexedExpression
import org.xtuml.bp.xtext.masl.masl.behavior.NavigateExpression
import org.xtuml.bp.xtext.masl.masl.behavior.ScheduleStatement
import org.xtuml.bp.xtext.masl.masl.behavior.SimpleFeatureCall
import org.xtuml.bp.xtext.masl.masl.behavior.TerminatorActionCall
import org.xtuml.bp.xtext.masl.masl.behavior.VariableDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.AbstractService
import org.xtuml.bp.xtext.masl.masl.structure.AbstractTopLevelElement
import org.xtuml.bp.xtext.masl.masl.structure.AssocRelationshipDefinition
import org.xtuml.bp.xtext.masl.masl.structure.ObjectDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.Parameterized
import org.xtuml.bp.xtext.masl.masl.structure.RegularRelationshipDefinition
import org.xtuml.bp.xtext.masl.masl.structure.RelationshipEnd
import org.xtuml.bp.xtext.masl.masl.structure.SubtypeRelationshipDefinition

import static org.xtuml.bp.xtext.masl.typesystem.BuiltinType.*

import static extension org.eclipse.xtext.EcoreUtil2.*

class MaslExpectedTypeProvider {

	@Inject extension BehaviorPackage
	@Inject extension MaslTypeProvider

	def Iterable<MaslType> getExpectedTypes(EObject context, EReference reference, int index) {
		if (reference == assignStatement_Rhs && context instanceof AssignStatement)
			return #[(context as AssignStatement).lhs.maslType]
		if (reference == createArgument_Value && context instanceof CreateArgument)
			return #[(context as CreateArgument).attribute.maslType]
		if (reference == caseAlternative_Choices && context instanceof CaseAlternative)
			return #[((context as CaseAlternative).eContainer as CaseStatement).value.maslType]
		if (reference == variableDeclaration_Expression && context instanceof VariableDeclaration)
			return #[(context as VariableDeclaration).type.maslType]
		if (reference == returnStatement_Value) {
			val topLevelElement = context.getContainerOfType(AbstractTopLevelElement)
			switch topLevelElement {
				AbstractService:
					return #[topLevelElement.getReturnType.maslTypeOfTypeReference]
				default:
					return #[NO_TYPE]
			}
		}
		if(reference == actionCall_Arguments && context instanceof ActionCall && index != -1) {
			val action = (context as ActionCall).receiver
			if(action instanceof SimpleFeatureCall)
				return action.feature.getParameterType(index)
		}
		if(reference == indexedExpression_Brackets && context instanceof IndexedExpression) {
			val receiverType = (context as IndexedExpression).receiver.maslType.stripName
			if(receiverType instanceof DictionaryType)
				return #[receiverType.keyType]
			else
				return #[INTEGER, new RangeType(INTEGER)]
		}
		if(reference == terminatorActionCall_Arguments && context instanceof TerminatorActionCall && index != -1) 
			return (context as TerminatorActionCall).terminatorAction.getParameterType(index)
		if(reference == navigateExpression_Lhs && context instanceof NavigateExpression) 
			return getRelationshipNavigationLhsExpectation(context as NavigateExpression)
		if(reference == navigateExpression_With && context instanceof NavigateExpression) 
			return getRelationshipNavigationWithExpectation(context as NavigateExpression)
		if(reference == exitStatement_Condition 
			|| reference == ifStatement_Condition
			|| reference == elsifBlock_Condition
			|| reference == whileStatement_Condition)
			return #[BOOLEAN]
		if(reference == delayStatement_Value)
			return #[DURATION]
		if(reference == scheduleStatement_TimerId 
			||reference == cancelTimerStatement_TimerId)
			return #[TIMER]
		if(reference == scheduleStatement_Time && context instanceof ScheduleStatement) {
			switch (context as ScheduleStatement).type {
				case AT: return #[TIMESTAMP]
				case DELAY: return #[DURATION] 
			}
		}
		if(reference == characteristicCall_Arguments && context instanceof CharacteristicCall) 
			return getParameterType(context as CharacteristicCall, index)	
		
		return #[]
	}
	
	private def Iterable<MaslType> getRelationshipNavigationWithExpectation(NavigateExpression context) {
		val relationship = context?.navigation?.relationship
		val types = switch relationship {
			RegularRelationshipDefinition:
				newArrayList(relationship.forwards.from, relationship.backwards.from).allCollectionTypes
			AssocRelationshipDefinition:
				newArrayList(relationship.forwards.from, relationship.backwards.from).allCollectionTypes
			SubtypeRelationshipDefinition:
				return #[]
		}
		return types
	}
	
	private def Iterable<MaslType> getRelationshipNavigationLhsExpectation(NavigateExpression context) {
		val navigation = context.navigation
		val relationship = navigation.relationship
		if(relationship instanceof SubtypeRelationshipDefinition) {
			if (navigation.objectOrRole == null) 
				return (relationship.subtypes + #[relationship.supertype]).allCollectionTypes
			else if (navigation.objectOrRole == relationship.supertype)
				return relationship.subtypes.allCollectionTypes
			else 
				return #[relationship.supertype].allCollectionTypes
		}
		val endObjects = switch relationship {
			RegularRelationshipDefinition:
				newArrayList(relationship.forwards.from, relationship.backwards.from)
			AssocRelationshipDefinition:
				newArrayList(relationship.forwards.from, relationship.backwards.from, relationship.object)
			default: 
				throw new UnsupportedOperationException('Cannot determine relationship ends of ' + relationship?.eClass?.name)
		}
		val objectOrRole = navigation.objectOrRole 
		if(objectOrRole instanceof RelationshipEnd) {
			endObjects.remove(objectOrRole.to)
			return allCollectionTypes(endObjects)
		}
		if(objectOrRole instanceof ObjectDeclaration) {
			endObjects.remove(objectOrRole)
		}
		return allCollectionTypes(endObjects)
	}

	private def Set<MaslType> allCollectionTypes(Iterable<ObjectDeclaration> objectsToMultiply) {
		val result = <MaslType>newHashSet
		for(o: objectsToMultiply) {
			val type = o.maslType
			result += type
			result += new ArrayType(type, false)
			result += new BagType(type, false)
			result += new SequenceType(type, false)
			result += new SetType(type, false)
		}
		result
	}

	private def Iterable<MaslType> getParameterType(EObject parameterized, int index) {
		if(parameterized instanceof Parameterized) {
			val parameters = parameterized.parameters
			if(parameters.size > index) {
				val parameter = (parameters).get(index)
				return #[parameter.maslType]
			}
		} else {
			return #[]
		}
	} 
	
	def Iterable<MaslType> getExpectedType(EObject element) {
		val container = element.eContainer
		val reference = element.eContainmentFeature
		if(reference.isMany) 
			container.getExpectedTypes(reference, (container.eGet(reference) as List<?>).indexOf(element))
		else
			container.getExpectedTypes(reference, -1)
	}
	
	private def getParameterType(CharacteristicCall call, int index) {
		val parameters = call.characteristic.parameters
		if(parameters.size > index) {
			val paramType = parameters.get(index).maslType
			if(paramType instanceof TypeParameterType) {
				val receiverType = call.receiver.maslType.stripName
				switch receiverType {
					TypeOfType: 
						return #[receiverType.type]							
					CollectionType:
						return #[receiverType.componentType]
					DictionaryType: {
						val typeParams = call.characteristic.typeParams
						if(typeParams.head().name == paramType.name)
							return #[receiverType.keyType]
						else 
							return #[receiverType.valueType] 
					}
				}
			}
			return #[paramType]
		} else {
			return #[]
		}
	}
}
