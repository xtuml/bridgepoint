package org.xtuml.bp.xtext.masl.typesystem

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.util.internal.Log
import org.xtuml.bp.xtext.masl.MASLExtensions
import org.xtuml.bp.xtext.masl.masl.behavior.AdditiveExp
import org.xtuml.bp.xtext.masl.masl.behavior.AssignStatement
import org.xtuml.bp.xtext.masl.masl.behavior.BooleanLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.CancelTimerStatement
import org.xtuml.bp.xtext.masl.masl.behavior.CaseStatement
import org.xtuml.bp.xtext.masl.masl.behavior.CharacterLiteral
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
import org.xtuml.bp.xtext.masl.masl.behavior.FlushLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.ForStatement
import org.xtuml.bp.xtext.masl.masl.behavior.GenerateStatement
import org.xtuml.bp.xtext.masl.masl.behavior.IfStatement
import org.xtuml.bp.xtext.masl.masl.behavior.IntegerLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.LineNoLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.LinkExpression
import org.xtuml.bp.xtext.masl.masl.behavior.LogicalAnd
import org.xtuml.bp.xtext.masl.masl.behavior.LogicalOr
import org.xtuml.bp.xtext.masl.masl.behavior.LogicalXor
import org.xtuml.bp.xtext.masl.masl.behavior.LoopVariable
import org.xtuml.bp.xtext.masl.masl.behavior.MultExp
import org.xtuml.bp.xtext.masl.masl.behavior.NullLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.OperationCall
import org.xtuml.bp.xtext.masl.masl.behavior.RaiseStatement
import org.xtuml.bp.xtext.masl.masl.behavior.RangeExpression
import org.xtuml.bp.xtext.masl.masl.behavior.RealLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.RelationalExp
import org.xtuml.bp.xtext.masl.masl.behavior.ReturnStatement
import org.xtuml.bp.xtext.masl.masl.behavior.ScheduleStatement
import org.xtuml.bp.xtext.masl.masl.behavior.SimpleFeatureCall
import org.xtuml.bp.xtext.masl.masl.behavior.StreamExpression
import org.xtuml.bp.xtext.masl.masl.behavior.StringLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.TerminatorOperationCall
import org.xtuml.bp.xtext.masl.masl.behavior.ThisLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.TimestampLiteral
import org.xtuml.bp.xtext.masl.masl.behavior.UnaryExp
import org.xtuml.bp.xtext.masl.masl.behavior.VariableDeclaration
import org.xtuml.bp.xtext.masl.masl.behavior.WhileStatement
import org.xtuml.bp.xtext.masl.masl.structure.AbstractFeature
import org.xtuml.bp.xtext.masl.masl.structure.AttributeDefinition
import org.xtuml.bp.xtext.masl.masl.structure.DomainFunctionDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.DomainServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectFunctionDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.Parameter
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorDefinition
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorFunctionDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorServiceDeclaration
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
import org.xtuml.bp.xtext.masl.masl.types.StructureTypeDefinition
import org.xtuml.bp.xtext.masl.masl.types.TerminatorTypeReference
import org.xtuml.bp.xtext.masl.masl.types.TypeDeclaration
import org.xtuml.bp.xtext.masl.masl.types.UnconstrainedArrayDefinition

import static org.xtuml.bp.xtext.masl.typesystem.BuiltinType.*

@Log
class MaslTypeProvider {

	@Inject extension MASLExtensions
	
	def MaslType getMaslType(EObject it) {
		try {
			switch it {
				AssignStatement:
					return new BuiltinType(NO_TYPE)
				Expression:
					return maslTypeOfExpression
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
				WhileStatement:
					return new BuiltinType(NO_TYPE)
				default:
					throw new UnsupportedOperationException('Missing type for ' + eClass?.name)
			}
		} catch (Exception exc) {
			LOG.error(exc.message)
			return new BuiltinType(NO_TYPE)
		}
	}
	
	protected def MaslType getMaslTypeOfExpression(Expression it) {
		switch it {
			AbstractTypeReference:
				return maslTypeOfTypeReference
			StreamExpression:
				return new BuiltinType(DEVICE, true)
			RangeExpression:
				return new ArrayType(from.maslType, true)
			LogicalOr,
			LogicalXor,
			LogicalAnd,
			Equality,
			RelationalExp:
				return new BuiltinType(BOOLEAN, true)
			AdditiveExp:
				return typeOfAdditive
			MultExp:
				return typeOfMult
			UnaryExp:
				return operand.maslType
			LinkExpression:
				return typeOfLinkExpression
			CreateExpression:
				return new InstanceType(object)		
			OperationCall:
				return feature.callType
			SimpleFeatureCall:
				return feature.callType
			TerminatorOperationCall:
				return terminalOperation.callType
			ThisLiteral:
				return typeOfThis
			IntegerLiteral:
				return new BuiltinType(INTEGER, true)
			RealLiteral:
				return new BuiltinType(REAL, true)
			CharacterLiteral:
				return new BuiltinType(CHARACTER, true)
			StringLiteral:
				return new BuiltinType(STRING, true)
			DurationLiteral:
				return new BuiltinType(DURATION, true)
			TimestampLiteral:
				return new BuiltinType(TIMESTAMP, true)
			BooleanLiteral:
				return new BuiltinType(BOOLEAN, true)
			NullLiteral:
				return new BuiltinType(ANY_TYPE, true)
			FlushLiteral:
				return new BuiltinType(STREAM_MANIPULATOR, true)
			ConsoleLiteral:
				return new BuiltinType(DEVICE, true)
			EndlLiteral:
				return new BuiltinType(STREAM_MANIPULATOR, true)
			LineNoLiteral,
			FileNameLiteral:
				return new BuiltinType('no_type', true)
			default:
				throw new UnsupportedOperationException('Missing type for expression ' + eClass?.name)
		}
	}
	
	protected def MaslType getMaslTypeOfTypeReference(AbstractTypeReference it) {
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
				return new DictionaryType(keyType.maslTypeOfTypeReference, elementType.maslTypeOfTypeReference, anonymous)
			ConstrainedArrayTypeReference:
				return new ArrayType(elementType.maslTypeOfTypeDeclaration)
				// TODO
//			DeprecatedTypeReference:
//				return new InstanceType(null, false)
			InstanceTypeReference:
				return new InstanceType(instance, anonymous)
			NamedTypeReference: {
				if(type instanceof BuiltinTypeDeclaration)
					return new BuiltinType(type.name, anonymous)
				else 
					return new NamedType(type.name, type.maslTypeOfTypeDeclaration, anonymous)
			}
			TerminatorTypeReference:
				return new TerminatorType(terminator)
			default:
				throw new UnsupportedOperationException('Missing type for type ref ' + eClass?.name)
		}
	}
	
	protected def MaslType getMaslTypeOfTypeDeclaration(TypeDeclaration declaration) {
		if(declaration instanceof BuiltinTypeDeclaration)
			return new BuiltinType(declaration.name)
		else 
			new NamedType(declaration.name, declaration.definition.maslTypeOfTypeDefinition)
	}
	
	protected def MaslType getMaslTypeOfTypeDefinition(AbstractTypeDefinition definition) {
		switch definition {
			AbstractTypeReference:
				return definition.maslTypeOfTypeReference
			ConstrainedTypeDefinition:
				return definition.type.maslTypeOfTypeReference
			EnumerationTypeDefinition:
				return new EnumType(definition)
			StructureTypeDefinition: {
				val structureComponents = definition.components.map[new StructureComponent(name, type.maslTypeOfTypeReference)]
				return new StructureType(structureComponents)
			}
			UnconstrainedArrayDefinition:
				return definition.elementType.maslTypeOfTypeReference
			default:
				throw new UnsupportedOperationException('Missing type for type definition ' + definition?.eClass?.name)
		}
	}
	
	protected def MaslType getCallType(AbstractFeature feature) {
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
			TypeDeclaration:
				return feature.maslTypeOfTypeDeclaration
			ObjectFunctionDeclaration:
				return feature.returnType.maslTypeOfTypeReference
			DomainFunctionDeclaration:
				return feature.returnType.maslTypeOfTypeReference
			TerminatorFunctionDeclaration:
				return feature.returnType.maslTypeOfTypeReference
			ObjectServiceDeclaration,
			DomainServiceDeclaration,
			TerminatorServiceDeclaration:
				return new BuiltinType(NO_TYPE)
			default:
				throw new UnsupportedOperationException('Missing type for feature ' + feature?.eClass?.name)
		}
	}
	
	protected def MaslType getTypeOfLinkExpression(LinkExpression it) {
		// TODO
		null
	}
	
	protected def MaslType getTypeOfThis(ThisLiteral it) {
		val object = containerObject
		if(containerObject != null)
			return new InstanceType(object)
		else 
			throw new UnsupportedOperationException('Cannot determine type of \'this\'.')
	}
	
	protected def MaslType getTypeOfAdditive(AdditiveExp it) {
		val lType = lhs.maslType
		switch operator {
			case '+', case '-':
				return getCommonNumericType(lType, rhs.maslType)
			case '&':
				if(lType == new BuiltinType(STRING) || lType instanceof CollectionType) 
					return lType
				else 
					return new BuiltinType(NO_TYPE)
			case 'union', case 'not_in':
				if(lType instanceof CollectionType) 
					lType 
				else 
					new BuiltinType(NO_TYPE)
			default:
				throw new UnsupportedOperationException('Missing type for additive expression ' + eClass?.name)
		}
	}
	
	protected def MaslType getTypeOfMult(MultExp it) {
		val lType = lhs.maslType
		switch operator {
			case '*', case '/':
				return getCommonNumericType(lType, rhs.maslType)
			case 'mod', case 'rem':
				return new BuiltinType(INTEGER, true)
			case '**':
				return new BuiltinType(REAL, true)
			case 'disunion', case 'intersection':
				if(lType instanceof CollectionType) 
					lType 
				else 
					new BuiltinType(NO_TYPE)
			default:
				throw new UnsupportedOperationException('Missing type for multiplicative expression ' + eClass?.name)
		}
	}
	
	private def MaslType getCommonNumericType(EObject it, MaslType a, MaslType b) {
		if(a == new BuiltinType(INTEGER) && b == new BuiltinType(INTEGER))
			new BuiltinType(INTEGER, true)
		else 
			new BuiltinType(REAL, true)
	}
	
	private def MaslType getComponentType(MaslType type) {
		if(type instanceof CollectionType)
			type.elementType
		else 
			type
	} 
}
