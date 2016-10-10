package org.xtuml.bp.xtext.masl.typesystem

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.xtuml.bp.xtext.masl.masl.behavior.AssignStatement
import org.xtuml.bp.xtext.masl.masl.behavior.BehaviorPackage
import org.xtuml.bp.xtext.masl.masl.behavior.CaseAlternative
import org.xtuml.bp.xtext.masl.masl.behavior.CaseStatement
import org.xtuml.bp.xtext.masl.masl.behavior.CreateArgument
import org.xtuml.bp.xtext.masl.masl.behavior.VariableDeclaration

import static org.xtuml.bp.xtext.masl.typesystem.BuiltinType.*

class MaslExpectedTypeProvider {
	
	@Inject extension BehaviorPackage
	@Inject extension MaslTypeProvider
	
	def MaslType getExpectedType(EObject context, EReference reference) {
		if (reference == assignStatement_Rhs && context instanceof AssignStatement)
			return (context as AssignStatement).lhs.maslType
		if (reference == createArgument_Value && context instanceof CreateArgument)
			return (context as CreateArgument).attribute.maslType
		if (reference == caseAlternative_Choices && context instanceof CaseAlternative)
			return ((context as CaseAlternative).eContainer as CaseStatement).value.maslType
		if (reference == variableDeclaration_Expression && context instanceof VariableDeclaration) 
			return (context as VariableDeclaration).type.maslType
		return ANY_TYPE
	}
	
	def MaslType getExpectedType(EObject element) {
		element.eContainer.getExpectedType(element.eContainmentFeature)
	}
}