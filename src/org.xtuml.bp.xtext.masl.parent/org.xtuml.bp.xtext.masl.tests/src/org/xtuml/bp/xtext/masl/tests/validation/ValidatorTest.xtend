package org.xtuml.bp.xtext.masl.tests.validation

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith
import org.xtuml.bp.xtext.masl.masl.structure.DomainDefinition
import org.xtuml.bp.xtext.masl.masl.structure.MaslModel
import org.xtuml.bp.xtext.masl.masl.structure.StructurePackage
import org.xtuml.bp.xtext.masl.tests.MASLInjectorProvider

import static org.xtuml.bp.xtext.masl.validation.IssueCodes.*

@RunWith(XtextRunner)
@InjectWith(MASLInjectorProvider)
class ValidatorTest {
	
	@Inject extension ParseHelper<MaslModel>
	@Inject extension ValidationTestHelper
	@Inject extension StructurePackage

	@Test
	def void testInheritanceCycle() {
		val objects = ('''
			domain inheritance is
			
				object A;
				object B;
				object C;
				 
				relationship R0 is C is_a (A);
				relationship R1 is B is_a (A);
				relationship R2 is A is_a (C); 
				
			end;
		'''.parse.elements.head as DomainDefinition).objects
		objects.get(0).assertError(objectDeclaration, CYCLIC_INHERITANCE)
		objects.get(1).assertNoError(CYCLIC_INHERITANCE)
		objects.get(2).assertError(objectDeclaration, CYCLIC_INHERITANCE)
	}
	
	private def assertNoError(EObject element, String type) {
		!element.validate.exists[
			uriToProblem == EcoreUtil.getURI(element) && code == type
		]
	}
}
