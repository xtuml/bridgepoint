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

import static org.xtuml.bp.xtext.masl.validation.MaslIssueCodesProvider.*
import org.eclipse.xtext.resource.XtextResourceSet
import com.google.inject.Provider
import org.eclipse.emf.common.util.URI
import org.xtuml.bp.xtext.masl.masl.behavior.BehaviorPackage

@RunWith(XtextRunner)
@InjectWith(MASLInjectorProvider)
class ValidatorTest {
	
	@Inject extension ParseHelper<MaslModel>
	@Inject extension ValidationTestHelper
	@Inject extension StructurePackage
	@Inject extension BehaviorPackage
	@Inject Provider<XtextResourceSet> resourceSetProvider

	@Test
	def void testInheritanceCycle() {
		val objects = (load('''
			domain inheritance is
			
				object A;
				object B;
				object C;
				 
				relationship R0 is C is_a (A);
				relationship R1 is B is_a (A);
				relationship R2 is A is_a (C); 
				
			end;
		''').elements.head as DomainDefinition).objects
		objects.get(0).assertError(objectDeclaration, CYCLIC_INHERITANCE)
		objects.get(1).assertNoError(CYCLIC_INHERITANCE)
		objects.get(2).assertError(objectDeclaration, CYCLIC_INHERITANCE)
	}
	
	@Test
	def void testInvalidOperationCall() {
		'1()'.model.assertError(operationCall, INVALID_OPERATION_CALL)
	}
	
	private def assertNoError(EObject element, String type) {
		!element.validate.exists[
			uriToProblem == EcoreUtil.getURI(element) && code == type
		]
	}

	private def MaslModel getModel(CharSequence model) {
		load('''
			domain dom is
				service svc(); 
			end;
		''', '''
			service dom::svc() is
			begin
				«model»
			end;
		''')
	}

	private def MaslModel load(CharSequence... models) {
		val pairs = newArrayList 
		models.forEach[model, i | pairs += 'dummy' + i + '.masl' -> model]
		load(pairs)
	}		
	
	private def MaslModel load(Pair<String, CharSequence>... fileName2content) {
		val rs = resourceSetProvider.get
		var MaslModel model
		for(pair: fileName2content) {
			model = pair.value.parse(URI.createURI(pair.key), rs)
		}
		return model
	}  
	
}
