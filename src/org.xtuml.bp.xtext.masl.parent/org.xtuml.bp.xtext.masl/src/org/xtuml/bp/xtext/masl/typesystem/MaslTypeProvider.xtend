package org.xtuml.bp.xtext.masl.typesystem

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.xtuml.bp.xtext.masl.lib.MASLLibraryProvider
import org.xtuml.bp.xtext.masl.masl.AbstractFeature
import org.xtuml.bp.xtext.masl.masl.AbstractTypeReference
import org.xtuml.bp.xtext.masl.masl.AdditiveExp
import org.xtuml.bp.xtext.masl.masl.AssignStatement
import org.xtuml.bp.xtext.masl.masl.AttributeDefinition
import org.xtuml.bp.xtext.masl.masl.BooleanLiteral
import org.xtuml.bp.xtext.masl.masl.CancelTimerStatement
import org.xtuml.bp.xtext.masl.masl.CaseStatement
import org.xtuml.bp.xtext.masl.masl.CharacterLiteral
import org.xtuml.bp.xtext.masl.masl.CodeBlockStatement
import org.xtuml.bp.xtext.masl.masl.ConsoleLiteral
import org.xtuml.bp.xtext.masl.masl.CreateExpression
import org.xtuml.bp.xtext.masl.masl.DelayStatement
import org.xtuml.bp.xtext.masl.masl.DeleteStatement
import org.xtuml.bp.xtext.masl.masl.DomainFunctionDeclaration
import org.xtuml.bp.xtext.masl.masl.DomainServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.DurationLiteral
import org.xtuml.bp.xtext.masl.masl.EndlLiteral
import org.xtuml.bp.xtext.masl.masl.Enumerator
import org.xtuml.bp.xtext.masl.masl.Equality
import org.xtuml.bp.xtext.masl.masl.EraseStatement
import org.xtuml.bp.xtext.masl.masl.ExitStatement
import org.xtuml.bp.xtext.masl.masl.Expression
import org.xtuml.bp.xtext.masl.masl.FileNameLiteral
import org.xtuml.bp.xtext.masl.masl.FlushLiteral
import org.xtuml.bp.xtext.masl.masl.ForStatement
import org.xtuml.bp.xtext.masl.masl.GenerateStatement
import org.xtuml.bp.xtext.masl.masl.IfStatement
import org.xtuml.bp.xtext.masl.masl.IntegerLiteral
import org.xtuml.bp.xtext.masl.masl.LineNoLiteral
import org.xtuml.bp.xtext.masl.masl.LinkExpression
import org.xtuml.bp.xtext.masl.masl.LogicalAnd
import org.xtuml.bp.xtext.masl.masl.LogicalOr
import org.xtuml.bp.xtext.masl.masl.LogicalXor
import org.xtuml.bp.xtext.masl.masl.MaslFactory
import org.xtuml.bp.xtext.masl.masl.MultExp
import org.xtuml.bp.xtext.masl.masl.NullLiteral
import org.xtuml.bp.xtext.masl.masl.ObjectDeclaration
import org.xtuml.bp.xtext.masl.masl.ObjectFunctionDeclaration
import org.xtuml.bp.xtext.masl.masl.ObjectServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.OperationCall
import org.xtuml.bp.xtext.masl.masl.Parameter
import org.xtuml.bp.xtext.masl.masl.RaiseStatement
import org.xtuml.bp.xtext.masl.masl.RangeExpression
import org.xtuml.bp.xtext.masl.masl.RealLiteral
import org.xtuml.bp.xtext.masl.masl.RelationalExp
import org.xtuml.bp.xtext.masl.masl.ReturnStatement
import org.xtuml.bp.xtext.masl.masl.ScheduleStatement
import org.xtuml.bp.xtext.masl.masl.SimpleFeatureCall
import org.xtuml.bp.xtext.masl.masl.StreamExpression
import org.xtuml.bp.xtext.masl.masl.StringLiteral
import org.xtuml.bp.xtext.masl.masl.TerminatorFunctionDeclaration
import org.xtuml.bp.xtext.masl.masl.ThisLiteral
import org.xtuml.bp.xtext.masl.masl.TimestampLiteral
import org.xtuml.bp.xtext.masl.masl.TypeDeclaration
import org.xtuml.bp.xtext.masl.masl.UnaryExp
import org.xtuml.bp.xtext.masl.masl.VariableDeclaration
import org.xtuml.bp.xtext.masl.masl.WhileStatement
import org.xtuml.bp.xtext.masl.masl.TerminatorServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.TerminatorDefinition
import org.xtuml.bp.xtext.masl.masl.LoopVariable
import org.xtuml.bp.xtext.masl.masl.TerminatorOperationCall
import org.xtuml.bp.xtext.masl.masl.BuiltinTypeDeclaration

class MaslTypeProvider {

	@Inject extension MASLLibraryProvider
	@Inject extension MaslTypeExtensions

	extension MaslFactory = MaslFactory.eINSTANCE

	def AbstractTypeReference getType(EObject it) {
		switch it {
			AssignStatement:
				getBuiltinType('none')
			Expression:
				typeOfExpression
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
				getBuiltinType('no_type')
			default:
				null
		}
	}
	
	protected def AbstractTypeReference getTypeOfExpression(Expression it) {
		switch it {
			StreamExpression:
				getBuiltinType('stream_type')
			RangeExpression:
				createArrayTypeReference => [ ref |
					ref.elementType = from.type
					ref.expression = it
				]
			LogicalOr,
			LogicalXor,
			LogicalAnd,
			Equality,
			RelationalExp:
				getBuiltinType('boolean')
			AdditiveExp:
				typeOfAdditive
			MultExp:
				typeOfMult
			UnaryExp:
				operand.type
			LinkExpression:
				typeOfLinkExpression
			CreateExpression:
				createInstanceTypeReference => [ ref |
					ref.instance = object		
				]
			OperationCall:
				feature.callType
			SimpleFeatureCall:
				feature.callType
			TerminatorOperationCall:
				terminalOperation.callType
			ThisLiteral:
				typeOfThis
			IntegerLiteral:
				getBuiltinType('integer')
			RealLiteral:
				getBuiltinType('real')
			CharacterLiteral:
				getBuiltinType('character')
			StringLiteral:
				getBuiltinType('string')
			DurationLiteral:
				getBuiltinType('duration')
			TimestampLiteral:
				getBuiltinType('timestamp')
			BooleanLiteral:
				getBuiltinType('boolean')
			NullLiteral:
				getBuiltinType('any_type')
			FlushLiteral:
				getBuiltinType('stream_manipulator_type')
			ConsoleLiteral:
				getBuiltinType('stream_type')
			EndlLiteral:
				getBuiltinType('stream_manipulator_type')
			LineNoLiteral:
				getBuiltinType('no_type')
			FileNameLiteral:
				getBuiltinType('no_type')
			default:
				null
		}
	}
	
	protected def AbstractTypeReference getCallType(AbstractFeature feature) {
		switch feature {
			Enumerator:
				createNamedTypeReference => [ 
					type = feature.eContainer.eContainer as TypeDeclaration
				]
			VariableDeclaration:
				feature.type
			ObjectDeclaration:
				createInstanceTypeReference => [ 
					instance = feature		
				]
			TerminatorDefinition:
				createTerminatorTypeReference => [
					terminator = feature
				]
			LoopVariable:
				(feature.eContainer as ForStatement).expression.type.componentType
			Parameter:
				feature.type
			AttributeDefinition:
				feature.type
			TypeDeclaration:
				createNamedTypeReference => [
					type = feature
				]
			ObjectFunctionDeclaration:
				feature.returnType
			DomainFunctionDeclaration:
				feature.returnType
			TerminatorFunctionDeclaration:
				feature.returnType
			ObjectServiceDeclaration,
			DomainServiceDeclaration,
			TerminatorServiceDeclaration:
				getBuiltinType(feature, 'no_type')
		}
	}
	
	protected def AbstractTypeReference getTypeOfLinkExpression(LinkExpression it) {
		// TODO
		null
	}
	
	protected def AbstractTypeReference getTypeOfThis(ThisLiteral it) {
		// TODO
		null
	}
	
	protected def AbstractTypeReference getTypeOfAdditive(AdditiveExp it) {
		val lType = lhs.type
		switch operator {
			case '+', case '-':
					getCommonNumericType(lType, rhs.type)
			case '&':
				if(lType.isString || lType.isCollection) 
					lType
				else 
					getBuiltinType('no_type')
			case 'union', case 'not_in':
				if(lType.isCollection) 
					lType 
				else 
					getBuiltinType('no_type')
		}
	}
	
	protected def AbstractTypeReference getTypeOfMult(MultExp it) {
		val lType = lhs.type
		switch operator {
			case '*', case '/':
				getCommonNumericType(lType, rhs.type)
			case 'mod', case 'rem':
				getBuiltinType('integer')
			case '**':
				getBuiltinType('real')
			case 'disunion', case 'intersection':
				if(lType.isCollection) 
					lType 
				else 
					getBuiltinType('no_type')
		}
	}
	
	private def getCommonNumericType(EObject it, AbstractTypeReference a, AbstractTypeReference b) {
		if(a.isInteger && b.isInteger)
			getBuiltinType('integer')
		else 
			getBuiltinType('real')
		
	} 

	private def AbstractTypeReference getBuiltinType(EObject context, String name) {
		val declaration = context.eResource.resourceSet.getEObject(getBuiltinTypeURI(name), true) as BuiltinTypeDeclaration
		if (declaration == null)
			throw new IllegalArgumentException('''No built-in type named '«name»' ''')
		createNamedTypeReference => [
			type = declaration
			anonymous = true
		]
	}
}
