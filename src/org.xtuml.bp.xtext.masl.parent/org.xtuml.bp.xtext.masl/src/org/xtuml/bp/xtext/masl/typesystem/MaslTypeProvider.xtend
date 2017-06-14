package org.xtuml.bp.xtext.masl.typesystem

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.xtuml.bp.xtext.masl.MASLExtensions
import org.xtuml.bp.xtext.masl.masl.behavior.ActionCall
import org.xtuml.bp.xtext.masl.masl.behavior.AdditiveExp
import org.xtuml.bp.xtext.masl.masl.behavior.AssignStatement
import org.xtuml.bp.xtext.masl.masl.behavior.BooleanLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.CancelTimerStatement
import org.xtuml.bp.xtext.masl.masl.behavior.CaseStatement
import org.xtuml.bp.xtext.masl.masl.behavior.CharacterLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.CharacteristicCall
import org.xtuml.bp.xtext.masl.masl.behavior.CodeBlockStatement
import org.xtuml.bp.xtext.masl.masl.behavior.ConsoleLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.CreateExpression
import org.xtuml.bp.xtext.masl.masl.behavior.DelayStatement
import org.xtuml.bp.xtext.masl.masl.behavior.DeleteStatement
import org.xtuml.bp.xtext.masl.masl.behavior.DurationLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.EndlLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.Equality
import org.xtuml.bp.xtext.masl.masl.behavior.EraseStatement
import org.xtuml.bp.xtext.masl.masl.behavior.ExitStatement
import org.xtuml.bp.xtext.masl.masl.behavior.Expression
import org.xtuml.bp.xtext.masl.masl.behavior.FileNameLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.FindExpression
import org.xtuml.bp.xtext.masl.masl.behavior.FlushLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.ForStatement
import org.xtuml.bp.xtext.masl.masl.behavior.GenerateStatement
import org.xtuml.bp.xtext.masl.masl.behavior.IfStatement
import org.xtuml.bp.xtext.masl.masl.behavior.IndexedExpression
import org.xtuml.bp.xtext.masl.masl.behavior.IntegerLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.LineNoLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.LinkExpression
import org.xtuml.bp.xtext.masl.masl.behavior.LogicalAnd
import org.xtuml.bp.xtext.masl.masl.behavior.LogicalOr
import org.xtuml.bp.xtext.masl.masl.behavior.LogicalXor
import org.xtuml.bp.xtext.masl.masl.behavior.LoopVariable
import org.xtuml.bp.xtext.masl.masl.behavior.MultExp
import org.xtuml.bp.xtext.masl.masl.behavior.NavigateExpression
import org.xtuml.bp.xtext.masl.masl.behavior.NullLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.RaiseStatement
import org.xtuml.bp.xtext.masl.masl.behavior.RangeExpression
import org.xtuml.bp.xtext.masl.masl.behavior.RealLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.RelationalExp
import org.xtuml.bp.xtext.masl.masl.behavior.ReturnStatement
import org.xtuml.bp.xtext.masl.masl.behavior.ScheduleStatement
import org.xtuml.bp.xtext.masl.masl.behavior.SimpleFeatureCall
import org.xtuml.bp.xtext.masl.masl.behavior.StreamExpression
import org.xtuml.bp.xtext.masl.masl.behavior.StringLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.StructureAggregateExpression
import org.xtuml.bp.xtext.masl.masl.behavior.TerminatorActionCall
import org.xtuml.bp.xtext.masl.masl.behavior.ThisLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.TimestampLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.UnaryExp
import org.xtuml.bp.xtext.masl.masl.behavior.VariableDeclaration
import org.xtuml.bp.xtext.masl.masl.behavior.WhileStatement
import org.xtuml.bp.xtext.masl.masl.structure.AbstractActionDefinition
import org.xtuml.bp.xtext.masl.masl.structure.AbstractFeature
import org.xtuml.bp.xtext.masl.masl.structure.AbstractService
import org.xtuml.bp.xtext.masl.masl.structure.AssocRelationshipDefinition
import org.xtuml.bp.xtext.masl.masl.structure.AttributeDefinition
import org.xtuml.bp.xtext.masl.masl.structure.EventDefinition
import org.xtuml.bp.xtext.masl.masl.structure.Multiplicity
import org.xtuml.bp.xtext.masl.masl.structure.ObjectDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.Parameter
import org.xtuml.bp.xtext.masl.masl.structure.RangeTypeReference
import org.xtuml.bp.xtext.masl.masl.structure.RegularRelationshipDefinition
import org.xtuml.bp.xtext.masl.masl.structure.RelationshipEnd
import org.xtuml.bp.xtext.masl.masl.structure.RelationshipNavigation
import org.xtuml.bp.xtext.masl.masl.structure.StateDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.StructurePackage
import org.xtuml.bp.xtext.masl.masl.structure.SubtypeRelationshipDefinition
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorDefinition
import org.xtuml.bp.xtext.masl.masl.structure.TypeParameter
import org.xtuml.bp.xtext.masl.masl.types.AbstractTypeDefinition
import org.xtuml.bp.xtext.masl.masl.types.AbstractTypeReference
import org.xtuml.bp.xtext.masl.masl.types.ArrayTypeReference
import org.xtuml.bp.xtext.masl.masl.types.BagTypeReference
import org.xtuml.bp.xtext.masl.masl.types.BuiltinTypeDeclaration
import org.xtuml.bp.xtext.masl.masl.types.ConstrainedArrayTypeReference
import org.xtuml.bp.xtext.masl.masl.types.ConstrainedTypeDefinition
import org.xtuml.bp.xtext.masl.masl.types.DictionaryTypeReference
import org.xtuml.bp.xtext.masl.masl.types.EnumerationTypeDefinition
import org.xtuml.bp.xtext.masl.masl.types.Enumerator
import org.xtuml.bp.xtext.masl.masl.types.InstanceTypeReference
import org.xtuml.bp.xtext.masl.masl.types.NamedTypeReference
import org.xtuml.bp.xtext.masl.masl.types.SequenceTypeReference
import org.xtuml.bp.xtext.masl.masl.types.SetTypeReference
import org.xtuml.bp.xtext.masl.masl.types.StructureComponentDefinition
import org.xtuml.bp.xtext.masl.masl.types.StructureTypeDefinition
import org.xtuml.bp.xtext.masl.masl.types.TerminatorTypeReference
import org.xtuml.bp.xtext.masl.masl.types.TypeDeclaration
import org.xtuml.bp.xtext.masl.masl.types.UnconstrainedArrayDefinition

import static org.xtuml.bp.xtext.masl.typesystem.BuiltinType.*
import org.eclipse.xtext.util.IResourceScopeCache

class MaslTypeProvider {
	
	@Inject extension MASLExtensions
	@Inject extension TypeParameterResolver
	@Inject extension StructurePackage
	@Inject IResourceScopeCache cache
	@Inject extension MaslTypeConformanceComputer
	
	private static val CACHE_KEY = 'masltype'
	private static val CACHE_KEY_TYPEREF = 'masltyperef'
	
	def MaslType getMaslType(EObject it) {
		if(it === null) 
			return MISSING_TYPE 
		cache.get(CACHE_KEY -> it, eResource, [
			try {
				switch it {
					AssignStatement:
						return NO_TYPE
					Expression:
						return maslTypeOfExpression
					AttributeDefinition:
						return type.maslTypeOfTypeReference
					StructureComponentDefinition:
						return type.maslTypeOfTypeReference
					AbstractTypeReference:
						return maslTypeOfTypeReference
					RelationshipNavigation:
						return maslTypeOfRelationshipNavigation
					AbstractTypeDefinition:
						return maslTypeOfTypeDefinition
					AbstractFeature:
						return maslTypeOfFeature
					AbstractService:
						if(getReturnType == null)
							return NO_TYPE
						else 
							return getReturnType.maslTypeOfTypeReference
					CodeBlockStatement,
					ExitStatement,
					ReturnStatement,
					DelayStatement,
					RaiseStatement,
					DeleteStatement,
					EraseStatement,
					ScheduleStatement,
					CancelTimerStatement,
					GenerateStatement,
					IfStatement,
					CaseStatement,
					ForStatement,
					WhileStatement,
					AbstractActionDefinition,
					StateDeclaration,
					EventDefinition:
						return NO_TYPE
					default:
						throw new UnsupportedOperationException('Missing type for ' + eClass?.name)
				}
			} catch (Exception exc) {
				return MISSING_TYPE
			}
		])
	}
	
	private def MaslType getMaslTypeOfExpression(Expression it) {
		switch it {
			AbstractTypeReference:
				return new TypeOfType(maslTypeOfTypeReference)
			NavigateExpression:
				return maslTypeOfNavigateExpression
			FindExpression:
				return maslTypeOfFindExpression 
			StreamExpression:
				return ANONYMOUS_DEVICE
			RangeExpression:
				return new RangeType(from.maslType, true)
			LogicalOr,
			LogicalXor,
			LogicalAnd,
			Equality,
			RelationalExp:
				return ANONYMOUS_BOOLEAN
			AdditiveExp:
				return maslTypeOfAdditive
			MultExp:
				return maslTypeOfMult
			UnaryExp:
				return operand.maslType
			LinkExpression:
				return maslTypeOfLinkExpression
			CreateExpression:
				return new InstanceType(object)		
			ActionCall: {
				val receiverType = receiver.maslTypeOfExpression
				if(receiverType instanceof TypeOfType) 
					// cast expression
					return receiverType.type
				else
					return receiverType			
			}
			SimpleFeatureCall:
				return feature.maslTypeOfFeature
			IndexedExpression: {
				val maslType = receiver.maslTypeOfExpression
				val componentType = if (maslType == STRING)
						ANONYMOUS_CHARACTER
					else
						maslType.componentType
				if(brackets.maslTypeOfExpression instanceof RangeType) 
					return new SequenceType(componentType, true)
				else 
					return componentType
			}
			TerminatorActionCall:
				return terminatorAction.maslTypeOfFeature
			CharacteristicCall:
				return maslTypeOfCharacteristicCall
			ThisLiteral:
				return maslTypeOfThis
			IntegerLiteral:
				return ANONYMOUS_INTEGER
			RealLiteral:
				return ANONYMOUS_REAL
			CharacterLiteral:
				return ANONYMOUS_CHARACTER
			StringLiteral:
				return ANONYMOUS_STRING
			DurationLiteral:
				return ANONYMOUS_DURATION
			TimestampLiteral:
				return ANONYMOUS_TIMESTAMP
			BooleanLiteral:
				return ANONYMOUS_BOOLEAN
			NullLiteral:
				return ANY_TYPE
			FlushLiteral,
			EndlLiteral:
				return STREAM_MANIPULATOR
			ConsoleLiteral:
				return ANONYMOUS_DEVICE
			LineNoLiteral:
				return ANONYMOUS_INTEGER
			FileNameLiteral:
				return ANONYMOUS_STRING
			StructureAggregateExpression:
				return new StructureType(null, elements.map[new StructureComponent(null, maslTypeOfExpression)],true)
			default:
				throw new UnsupportedOperationException('Missing type for expression ' + eClass?.name)
		}
	}
	
	def MaslType getMaslTypeOfTypeReference(AbstractTypeReference it) {
		cache.get(CACHE_KEY_TYPEREF -> it, eResource, [
			switch it {
				ArrayTypeReference:
					return new ArrayType(elementType.maslTypeOfTypeReference, anonymous)
				BagTypeReference:
					return new BagType(elementType.maslTypeOfTypeReference, anonymous)
				SequenceTypeReference:
					return new SequenceType(elementType.maslTypeOfTypeReference, anonymous)
				SetTypeReference:
					return new SetType(elementType.maslTypeOfTypeReference, anonymous)
				DictionaryTypeReference: 
					return new DictionaryType(
						keyType?.maslTypeOfTypeReference ?: ANONYMOUS_STRING, 
						elementType?.maslTypeOfTypeReference ?: ANONYMOUS_STRING, anonymous)
				RangeTypeReference:
					return new RangeType(elementType.maslTypeOfTypeReference, true)
				ConstrainedArrayTypeReference: {
					val unconstrainedMaslType = unconstrained.getMaslTypeOfTypeDeclaration(false)
					if(unconstrainedMaslType instanceof NamedType)
						return unconstrainedMaslType.type
					else				
						return unconstrainedMaslType
				}
				InstanceTypeReference:
					return new InstanceType(instance, anonymous)
				NamedTypeReference: {
					return type.getMaslTypeOfTypeDeclaration(anonymous)
				}
				TerminatorTypeReference:
					return new TerminatorType(terminator)
				default:
					throw new UnsupportedOperationException('Missing type for type ref ' + it?.eClass?.name)
			}
		])
	}
	
	private def MaslType getMaslTypeOfTypeDeclaration(TypeDeclaration declaration, boolean anonymous) {
		switch declaration {
			BuiltinTypeDeclaration:
				return new BuiltinType(declaration.name, anonymous)
			TypeParameter:
				return new TypeParameterType(declaration.name, declaration.enum, anonymous)
			default: {
				val definitionType = declaration.definition.maslTypeOfTypeDefinition
				if(definitionType instanceof EnumType) 
					return definitionType
				else 
					return new NamedType(declaration.name, definitionType, anonymous)
			}
		}
	}
	
	private def MaslType getMaslTypeOfTypeDefinition(AbstractTypeDefinition definition) {
		switch definition {
			AbstractTypeReference:
				return definition.maslTypeOfTypeReference
			ConstrainedTypeDefinition:
				return definition.type.maslTypeOfTypeReference
			EnumerationTypeDefinition:
				return new EnumType(definition)
			StructureTypeDefinition: {
				val structureComponents = definition.components.map[new StructureComponent(name, type.maslTypeOfTypeReference)]
				return new StructureType(definition, structureComponents, false)
			}
			UnconstrainedArrayDefinition:
				return new ArrayType(definition.elementType.maslTypeOfTypeReference, false)
			default:
				throw new UnsupportedOperationException('Missing type for type definition ' + definition?.eClass?.name)
		}
	}
	
	private def MaslType getMaslTypeOfFeature(AbstractFeature feature) {
		if(feature == null ||feature.eIsProxy)
			return MISSING_TYPE
		switch feature {
			Enumerator:
				return new EnumType(feature.eContainer as EnumerationTypeDefinition)
			VariableDeclaration:
				return feature.type.maslTypeOfTypeReference
			ObjectDeclaration:
				return new InstanceType(feature)
			TerminatorDefinition:
				return new TerminatorType(feature)
			LoopVariable:
				return (feature.eContainer as ForStatement).expression.maslType.componentType
			Parameter:
				return feature.type.maslTypeOfTypeReference
			AttributeDefinition:
				return feature.type.maslTypeOfTypeReference
			StructureComponentDefinition:
				return feature.type.maslTypeOfTypeReference
			TypeDeclaration:
				return new TypeOfType(feature.getMaslTypeOfTypeDeclaration(false))
			AbstractService:
				if (feature.getReturnType == null)
					return NO_TYPE
				else
					return feature.getReturnType.maslTypeOfTypeReference
			default:
				throw new UnsupportedOperationException('Missing type for feature ' + feature?.eClass?.name)
		}
	}
	
	private def MaslType getMaslTypeOfLinkExpression(LinkExpression it) {
		val relationship = navigation.relationship
		if(relationship instanceof AssocRelationshipDefinition) {
			val assocClassType = relationship.object.maslTypeOfFeature
			val lhsMaslType = lhs.maslType
			if(lhsMaslType instanceof CollectionType) 
				return lhsMaslType.getSameCollectionOfDifferentComponentType(assocClassType)
			val rhsMaslType = rhs.maslType
			if(rhsMaslType instanceof CollectionType) 
				return rhsMaslType.getSameCollectionOfDifferentComponentType(assocClassType)
			val navigationType = navigation.maslType
			if(navigationType instanceof CollectionType && rhs == null) 
				return new SequenceType(assocClassType, true)			
			return assocClassType
		}
		return NO_TYPE
	}
	
	private def getSameCollectionOfDifferentComponentType(CollectionType collectionType, MaslType newComponentType) {
		return switch collectionType {
			SequenceType:
				new SequenceType(newComponentType)
			SetType:
				new SetType(newComponentType)
			BagType:
				new BagType(newComponentType)
			ArrayType:
				new ArrayType(newComponentType)
		}
	}
	
	private def MaslType getMaslTypeOfFindExpression(FindExpression find) {
		val componentType = find.expression.maslType.componentType
		switch find.type {
			case FIND:
				new SequenceType(componentType, true)
			case FIND_ONE, 
			case FIND_ONLY:
				componentType
			default: 
				throw new UnsupportedOperationException("Unknown find type '" + find.type + "'")
		}
	}
	
	private def MaslType getMaslTypeOfNavigateExpression(NavigateExpression navigate) {
		if(navigate.navigation != null) {
			if(navigate.with != null) {
				val relationship = navigate.navigation.relationship
				if(relationship instanceof AssocRelationshipDefinition) {
					val componentType = relationship.object.maslType
					if(navigate.lhs.maslType instanceof CollectionType || navigate.with.maslType instanceof CollectionType)
						return new SequenceType(componentType, true)
					else
						return componentType
									
				}
			} else {
				navigate.navigation.maslTypeOfRelationshipNavigation
			}
		} else {
			navigate.lhs.maslTypeOfExpression
		}
	}
	
	private def MaslType getMaslTypeOfRelationshipNavigation(RelationshipNavigation navigation) {
		val receiverType = navigation.receiver.maslType.stripName
		val relatedObject = getRelatedObject(navigation, receiverType.componentType)
		val componentType = relatedObject.declaration.maslType
		switch relatedObject.multiplicity {
			case ONE:
				if(receiverType instanceof CollectionType)
					return new BagType(componentType, true)
				else 
					return new InstanceType(relatedObject.declaration, true)
			case MANY:
				if(receiverType instanceof CollectionType)
					return new BagType(componentType, true)
				else
					return new SetType(componentType, true)
		}
	}
	
	@Data
	private static class RelatedObject {
		val ObjectDeclaration declaration
		val Multiplicity multiplicity
		val boolean associationClass
	} 

	private def toRelatedObject(RelationshipEnd end, boolean isAssociationObject) {
		new RelatedObject(end.to, if(isAssociationObject) Multiplicity.ONE else end.multiplicity, isAssociationObject)
	}
	
	private def RelatedObject getRelatedObject(RelationshipNavigation navigation, MaslType receiverType) {
		val relationship = navigation.relationship
		val isAssociationObject = relationship instanceof AssocRelationshipDefinition
				&& (relationship as AssocRelationshipDefinition).object.maslType == receiverType 
		val ends = switch relationship {
			RegularRelationshipDefinition:
				#[relationship.forwards, relationship.backwards]
			AssocRelationshipDefinition:
				#[relationship.forwards, relationship.backwards]
			SubtypeRelationshipDefinition:
				#[]
			default: 
				throw new UnsupportedOperationException('Cannot determine relationship ends of ' + relationship?.eClass?.name)
		}
		val objectOrRole = navigation.objectOrRole 
		if(objectOrRole instanceof RelationshipEnd)
			return objectOrRole.toRelatedObject(isAssociationObject)
		if(objectOrRole instanceof ObjectDeclaration) {
			switch relationship {
				AssocRelationshipDefinition: 
					if(relationship.object == objectOrRole) {
						val nodes = NodeModelUtils.findNodesForFeature(navigation, relationshipNavigation_ObjectOrRole)
						val refNameSegments = nodes.map[leafNodes].flatten.filter[!hidden]
						val firstRefNameSegment = if(refNameSegments.size > 1) 
								refNameSegments.head.text
							else
								null
							
						val relationEnd = ends.findFirst[
							from.maslType == receiverType 
							&& (firstRefNameSegment == null || firstRefNameSegment == name) 
						]		
						return new RelatedObject(objectOrRole, relationEnd.multiplicity, true)
					}
				SubtypeRelationshipDefinition: {
					return new RelatedObject(objectOrRole, Multiplicity.ONE, false)
				} 
			}
			return ends.findFirst[to == objectOrRole].toRelatedObject(isAssociationObject)
		}
		switch relationship {
			RegularRelationshipDefinition, 
			AssocRelationshipDefinition: 
				return ends.findFirst[from.maslType == receiverType].toRelatedObject(isAssociationObject)
			SubtypeRelationshipDefinition: {
				val superType = relationship.supertype.maslType
				if(receiverType != superType) 
					return new RelatedObject(relationship.supertype, Multiplicity.ONE, false)
				else if(relationship.subtypes.size == 1)
					return new RelatedObject(relationship.subtypes.head, Multiplicity.ONE, false)
			}
		}
		throw new UnsupportedOperationException('Cannot determine relationship ends of ' + relationship?.eClass?.name)
	}
	
	private def MaslType getMaslTypeOfCharacteristicCall(CharacteristicCall call) {
		val characteristic = call.characteristic
		val returnType = characteristic.returnType.maslTypeOfTypeReference
		val typeParams = call.characteristic.typeParams
		switch typeParams.size {
			case 0:
				return returnType
			case 1: {
				val replace = new TypeParameterType(typeParams.head.name, typeParams.head.enum, true)
				val receiverType = call.receiver.maslType.stripName
				val replacement = switch receiverType {
					TypeOfType:
						receiverType.type
					CollectionType:
						receiverType.componentType
					default:
						receiverType
				}
				val returnValue = returnType.resolve(replace, replacement)
				return returnValue			
			}
			case 2: {
				val receiverType = call.receiver.maslType.stripName
				val replacement = switch receiverType {
					TypeOfType:
						receiverType.type
					default:
						receiverType
				}
				if(replacement instanceof DictionaryType) {
					val returnValue = returnType
						.resolve(new TypeParameterType(typeParams.head.name, true), replacement.keyType)
						.resolve(new TypeParameterType(typeParams.last.name, true), replacement.valueType)
					return returnValue			
				} else {
					throw new UnsupportedOperationException("Two type parameters are only supported for dictionary types")
				}
			}
			default:
				throw new UnsupportedOperationException("More than two type parameters are not supported")
		}
	}
	
	private def MaslType getMaslTypeOfThis(ThisLiteral it) {
		val object = containerObject
		if(containerObject != null)
			return new InstanceType(object)
		else 
			return MISSING_TYPE
	}
	
	private def MaslType getMaslTypeOfAdditive(AdditiveExp it) {
		val lType = lhs.maslType
		val rType = rhs.maslType
		switch operator {
			case '+':
				return getMaslTypeOfPlus(lType, rType)
			case '-':
				return getMaslTypeOfMinus(lType, rType)
			case '&':
				return getMaslTypeOfCollectionOperation(lType, rType) 
			case 'union', case 'not_in':
				return getMaslTypeOfCollectionOperation(lType, rType)
			default:
				throw new UnsupportedOperationException('Missing type for additive expression ' + eClass?.name)
		}
	}
	
	enum Priority {
		LEFT, RIGHT, NONE
	}
	
	private def getMaslTypeOfCollectionOperation(MaslType lType, MaslType rType) {
		// Decide which side of the expression has priority
		var priority = if (rType.anonymous === lType.anonymous)
				Priority.NONE
			else if (lType.anonymous)
				Priority.RIGHT
			else
				Priority.LEFT

		// Ensure both sides are collections and have the same depth
		var lComp = lType.primitiveType.componentTypeOrNull
		var rComp = rType.primitiveType.componentTypeOrNull
		var lhs = lType
		var rhs = rType
		do {
			if (lComp === null) {
				lhs = new SequenceType(lhs, true)
				if (priority === Priority.NONE)
					priority = Priority.RIGHT
			} else {
				lComp = lComp.primitiveType.componentTypeOrNull
			}

			if (rComp === null) {
				rhs = new SequenceType(rhs, true)
				if (priority === Priority.NONE)
					priority = Priority.LEFT;
			} else {
				rComp = rComp.primitiveType.componentTypeOrNull
			}
		} while (lComp !== null || rComp !== null)

		// return a type that can be assigned to 
		if (priority === Priority.LEFT && rhs.isAssignableTo(lhs))
			return lhs
		else if (priority === Priority.RIGHT && lhs.isAssignableTo(rhs))
			return rhs
		else if (rhs.isAssignableTo(lhs))
			return lhs
		else if(lhs.isAssignableTo(rhs)) 
			return rhs 
		else return MISSING_TYPE
	}

	private def getComponentTypeOrNull(MaslType type) {
		if(type instanceof CollectionType)
			type.componentType
		else 
			null
	}
	
	private def MaslType getMaslTypeOfPlus(MaslType lType, MaslType rType) {
		switch lType.primitiveType {
			case REAL,
			case LONG_INTEGER:
				return getCommonNumericType(lType, rType)
			case TIMESTAMP:
				if(rType.primitiveType == DURATION)
					return lType
			case DURATION:
				if(lType.anonymous && lType == DURATION) {
					if (rType.primitiveType == DURATION)
						return rType
					else if(rType == DURATION && rType.anonymous)
						return ANONYMOUS_DURATION
				} else if(rType == lType || (rType == DURATION && rType.anonymous)) {
					return lType
				} else if(rType.primitiveType == TIMESTAMP) {
					return rType
				}
		}
		return MISSING_TYPE
	}
	
	private def MaslType getMaslTypeOfMinus(MaslType lType, MaslType rType) {
		switch lType.primitiveType {
			case REAL,
			case LONG_INTEGER:
				return getCommonNumericType(lType, rType)
			case TIMESTAMP:
				if(rType.primitiveType == TIMESTAMP) 
					return ANONYMOUS_DURATION
				else if(rType.primitiveType == DURATION)
					return lType
			case DURATION:
				if(lType.anonymous && lType == DURATION) {
					if (rType.primitiveType == DURATION)
						return rType
					else if(rType == DURATION && rType.anonymous)
						return ANONYMOUS_DURATION
				} else if(rType.primitiveType == DURATION) {
					return lType
				}
		}
		return MISSING_TYPE
	}
	
	private def MaslType getMaslTypeOfMult(MultExp it) {
		val lType = lhs.maslType
		val rType = rhs.maslType
		switch operator {
			case '*':
				return getMaslTypeOfTimes(lType, rType)
			 case '/':
				return getMaslTypeOfDivide(lType, rType)
			case 'mod', case 'rem':
				return ANONYMOUS_INTEGER
			case '**':
				return ANONYMOUS_REAL
			case 'disunion', case 'intersection':
				return getMaslTypeOfCollectionOperation(lType, rType)
			default:
				throw new UnsupportedOperationException('Missing type for multiplicative expression ' + eClass?.name)
		}
	}
	
	private def MaslType getMaslTypeOfTimes(MaslType lType, MaslType rType) {
		switch lType.primitiveType {
			case REAL,
			case LONG_INTEGER:
				if(rType.primitiveType == DURATION)
					return rType
				else
					return getCommonNumericType(lType, rType)
			case DURATION:
				switch rType {
					case BYTE,
					case INTEGER,
					case LONG_INTEGER,
					case REAL:
						return lType
				}
		}
		return MISSING_TYPE
	}
	
	private def MaslType getMaslTypeOfDivide(MaslType lType, MaslType rType) {
		switch lType.primitiveType {
			case REAL,
			case LONG_INTEGER:
				return getCommonNumericType(lType, rType)
			case DURATION:
				switch rType.primitiveType {
					case BYTE,
					case INTEGER,
					case LONG_INTEGER,
					case REAL:
						return lType
					case DURATION:
						return REAL
				}
		}
		return MISSING_TYPE
	}
	
	
	private def MaslType getCommonNumericType(MaslType a, MaslType b) {
		switch a.primitiveType {
			case LONG_INTEGER:
				if (a == b)
					return a
				else if (b.primitiveType == LONG_INTEGER)
					return ANONYMOUS_LONG_INTEGER 
				else if (b.primitiveType == REAL) 
					return ANONYMOUS_REAL
			case REAL: {
				if (a == b || b.primitiveType == LONG_INTEGER) 
					return ANONYMOUS_REAL
			}
		}
		return MISSING_TYPE
	}
	
}
