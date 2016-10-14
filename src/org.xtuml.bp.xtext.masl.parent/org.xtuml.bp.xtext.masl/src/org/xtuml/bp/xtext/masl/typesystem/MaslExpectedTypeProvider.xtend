package org.xtuml.bp.xtext.masl.typesystem

import com.google.inject.Inject
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.xtuml.bp.xtext.masl.masl.behavior.AssignStatement
import org.xtuml.bp.xtext.masl.masl.behavior.BehaviorPackage
import org.xtuml.bp.xtext.masl.masl.behavior.CaseAlternative
import org.xtuml.bp.xtext.masl.masl.behavior.CaseStatement
import org.xtuml.bp.xtext.masl.masl.behavior.CreateArgument
import org.xtuml.bp.xtext.masl.masl.behavior.OperationCall
import org.xtuml.bp.xtext.masl.masl.behavior.SimpleFeatureCall
import org.xtuml.bp.xtext.masl.masl.behavior.TerminatorOperationCall
import org.xtuml.bp.xtext.masl.masl.behavior.VariableDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.AbstractTopLevelElement
import org.xtuml.bp.xtext.masl.masl.structure.DomainFunctionDefinition
import org.xtuml.bp.xtext.masl.masl.structure.ObjectFunctionDefinition
import org.xtuml.bp.xtext.masl.masl.structure.Parameterized
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorFunctionDefinition

import static org.xtuml.bp.xtext.masl.typesystem.BuiltinType.*

import static extension org.eclipse.xtext.EcoreUtil2.*

class MaslExpectedTypeProvider {

	@Inject extension BehaviorPackage
	@Inject extension MaslTypeProvider

	def List<MaslType> getExpectedTypes(EObject context, EReference reference, int index) {
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
				ObjectFunctionDefinition:
					return #[topLevelElement.returnType.maslType]
				DomainFunctionDefinition:
					return #[topLevelElement.returnType.maslType]
				TerminatorFunctionDefinition:
					return #[topLevelElement.returnType.maslType]
				default:
					return #[NO_TYPE]
			}
		}
		if(reference == operationCall_Arguments && context instanceof OperationCall && index != -1) {
			val operation = (context as OperationCall).receiver
			if(operation instanceof SimpleFeatureCall)
				return operation.feature.getParameterType(index)
		}
		if(reference == terminatorOperationCall_Arguments && context instanceof TerminatorOperationCall && index != -1) 
			return (context as TerminatorOperationCall).terminatorOperation.getParameterType(index)
		return #[]
	}

	private def List<MaslType> getParameterType(EObject parameterized, int index) {
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
	
	def List<MaslType> getExpectedType(EObject element) {
		val container = element.eContainer
		val reference = element.eContainmentFeature
		if(reference.isMany) 
			container.getExpectedTypes(reference, (container.eGet(reference) as List<?>).indexOf(element))
		else
			container.getExpectedTypes(reference, -1)
	}
	
}
