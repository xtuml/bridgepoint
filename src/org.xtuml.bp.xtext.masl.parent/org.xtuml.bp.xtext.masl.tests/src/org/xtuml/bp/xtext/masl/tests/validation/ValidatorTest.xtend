package org.xtuml.bp.xtext.masl.tests.validation

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.eclipse.xtext.resource.XtextResourceSet
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.xtuml.bp.xtext.masl.masl.behavior.BehaviorPackage
import org.xtuml.bp.xtext.masl.masl.structure.DomainDefinition
import org.xtuml.bp.xtext.masl.masl.structure.MaslModel
import org.xtuml.bp.xtext.masl.masl.structure.StructurePackage
import org.xtuml.bp.xtext.masl.masl.types.TypesPackage
import org.xtuml.bp.xtext.masl.tests.MASLInjectorProvider

import static org.xtuml.bp.xtext.masl.validation.MaslIssueCodesProvider.*

@RunWith(XtextRunner)
@InjectWith(MASLInjectorProvider)
class ValidatorTest {
	
	@Inject extension ParseHelper<MaslModel>
	@Inject extension ValidationTestHelper
	@Inject extension StructurePackage
	@Inject extension BehaviorPackage
	@Inject extension TypesPackage
	@Inject Provider<XtextResourceSet> resourceSetProvider

	ResourceSet rs
	int modelCount
	
	@Before
	def void setUp() {
		rs = resourceSetProvider.get
	}
	
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
	def void testInvalidActionCall() {
		'1()'.model.assertError(actionCall, INVALID_ACTION_CALL)
	}
	
	@Test 
	def void testConstrainedTypeReference() {
		val model = load('''
			domain dom is
				type array2 is array ( integer range<> ) of string;
				type array3 is array2 ( 1..10 );
				type array4 is array3 ( 1..10 );
			end;
		''')
		val types = ((model.elements.head) as DomainDefinition).types
		assertNoError(types.get(0), WRONG_TYPE)
		assertNoError(types.get(1), WRONG_TYPE)
		assertError(types.get(2), constrainedArrayTypeReference, WRONG_TYPE)
	}

	@Test 
	def void testInvalidThis() {
		load('''
			domain dom is
				object Foo;
				object Foo is 
					instance function iFunc() return instance of Foo;
					instance service iSvc();
					function func() return instance of Foo;
					service svc();
				end;
				function dFunc() return boolean
				service dSvc();
			end;
		''')
		load('''
			instance function dom::Foo.iFunc() return instance of Foo is
			begin
				return this;
			end; 
		''').assertNoError(INVALID_THIS)
		load('''
			instance service dom::Foo.iSvc() is
			begin
				this;
			end; 
		''').assertNoError(INVALID_THIS)
		load('''
			function dom::Foo.func() return instance of Foo is
			begin
				return this;
			end;
		''').assertError(thisLiteral, INVALID_THIS)
		load('''
			service dom::Foo.svc() is
			begin
				this;
			end;
		''').assertError(thisLiteral, INVALID_THIS)
		load('''
			function dom::dFunc() return boolean is
			begin
				return this;
			end;
		''').assertError(thisLiteral, INVALID_THIS)
		load('''
			service dom::dServ() is
			begin
				this;
			end;
		''').assertError(thisLiteral, INVALID_THIS)
	}

	@Test
	def void testReturnTypeValidation() {
		load('''
			domain dom is 
				function foo() return integer;
			end;
			
			function dom::foo() return integer is
			begin
				return "";
			end;
		''').elements.last.assertError(returnStatement, WRONG_TYPE)
	}

	@Test
	def void testUnreachableCode() {
		load('''
			domain dom is 
				function foo() return integer;
			end;
			
			function dom::foo() return integer is
			begin
				return 1;
				delay 1;
			end;
		''').elements.last.assertError(delayStatement, UNREACHABLE_CODE)
	}

	@Test 
	def void testVisibilityValidation() { 
		load('''
			domain dom is 
				public function func() return integer;
				private service serv();
			end;
		''').assertNoErrors()
		load('''	
			private function dom::func() return integer 
			is
			begin
			end;
		''').assertError(domainFunctionDefinition, DECLARATION_MISSMATCH)
		load('''	
			private service dom::serv() 
			is
			begin
			end;
		''').assertNoErrors(DECLARATION_MISSMATCH)
	}

	@Test 
	def void testParameterValidation() { 
		load('''
			domain dom is 
				function func() return integer;
			end;
			
			function dom::func() return integer 
			is
			begin
			end;
		''').elements.last.assertNoError(DECLARATION_MISSMATCH)
	}

	@Test 
	def void testParameterValidation1() { 
		load('''
			domain dom is 
				function func(p: in integer) return integer;
			end;
			
			function dom::func() return integer 
			is
			begin
			end;
		''').elements.last.assertError(domainFunctionDefinition, DECLARATION_MISSMATCH)
	}

	@Test 
	def void testParameterValidation2() { 
		load('''
			domain dom is 
				function func() return integer;
			end;
			
			function dom::func(p: in integer) return integer 
			is
			begin
			end;
		''').elements.last.assertError(domainFunctionDefinition, DECLARATION_MISSMATCH)
	}

	@Test 
	def void testParameterValidation3() { 
		load('''
			domain dom is 
				function func(p: in string) return integer;
			end;
			
			function dom::func(p: in integer) return integer 
			is
			begin
			end;
		''').elements.last.assertError(domainFunctionDefinition, DECLARATION_MISSMATCH)
	}

	@Test 
	def void testParameterValidation4() { 
		load('''
			domain dom is 
				function func(p: in integer, p: in string) return integer;
			end;
			
			function dom::func(p: in integer) return integer 
			is
			begin
			end;
		''').elements.last.assertError(domainFunctionDefinition, DECLARATION_MISSMATCH)
	}

	@Test 
	def void testParameterValidation5() { 
		load('''
			domain dom is 
				function func(p: in integer) return integer;
			end;
			
			function dom::func(p: in integer) return string
			is
			begin
			end;
		''').elements.last.assertError(domainFunctionDefinition, DECLARATION_MISSMATCH)
	}

	@Test 
	def void testParameterValidation6() { 
		load('''
			domain dom is 
				function func(p: in integer) return integer;
			end;
			
			function dom::func(p: out integer) return integer
			is
			begin
			end;
		''').elements.last.assertError(parameter, DECLARATION_MISSMATCH)
	}

	@Test 
	def void testParameterValidation7() { 
		load('''
			domain dom is 
				function func(p: in integer) return integer;
				function func(p: in real) return integer;
			end;
			
			function dom::func(p: in real) return integer
			is
			begin
			end;
		''').elements.last.assertNoError(DECLARATION_MISSMATCH)
	}

	@Test
	def void testDomainActionVisibilities() {
		load('''
			domain dom0 is 
				private function func() return integer;
				private service serv();
				public function func0() return integer;
				public service serv0();
			end;
			
			domain dom1 is 
				function func() return integer;
				service serv();
				service serv1();
			end;
		''').assertNoErrors
		load('''
			function dom1::func() return integer is
			begin
				return dom0::func();
			end
		''').assertError(simpleFeatureCall, INVISIBLE_FEATURE)
		load('''
			service dom1::serv() is
			begin 
				dom0::serv();
			end
		''').assertError(simpleFeatureCall, INVISIBLE_FEATURE)
		load('''
			service dom1::serv1() is
			begin 
				dom0::serv0();
				dom0::func0();
			end
		''').assertNoErrors(INVISIBLE_FEATURE)
	}

	@Test
	def void testObjectActionVisibilities() {
		load('''
			domain dom is
				object Foo;
				object Foo is
					private function func() return integer;
					private service serv();
					public function func0() return integer;
					public service serv0();
					state s();
				end;
				service caller(f: in instance of Foo);
				service caller0(f: in instance of Foo);
				service caller1(f: in instance of Foo);
			end;
		''').assertNoErrors
		load('''
			service dom::Foo.serv() is
			begin
				func();
				serv();
				func0();
				serv0();
			end
		''').assertNoErrors(INVISIBLE_FEATURE)
		load('''
			state dom::Foo.s() is
			begin
				func();
				serv();
				func0();
				serv0();
			end
		''').assertNoErrors(INVISIBLE_FEATURE)
		load('''
			service dom::caller(f: in instance of Foo) is
			begin
				f.func();
			end
		''').assertError(simpleFeatureCall, INVISIBLE_FEATURE)
		load('''
			service dom::caller0(f: in instance of Foo) is
			begin
				f.serv();
			end
		''').assertError(simpleFeatureCall, INVISIBLE_FEATURE)
		load('''
			service dom::caller1(f: in instance of Foo) is
			begin
				f.serv0();
				f.func0();
			end
		''').assertNoErrors(INVISIBLE_FEATURE)
	}

	@Test
	def void testTerminatorActionVisibilities() {
		load('''
			domain dom is
				terminator Foo is
					private function func() return integer;
					private service serv();
					public function func0() return integer;
					public service serv0();
				end;
			end;
			
			domain dom0 is
				service caller();
				service caller0();
				service caller1();
			end;
		''').assertNoErrors
		load('''
			service dom::Foo~>serv() is
			begin
				Foo~>func();
				Foo~>serv();
				Foo~>func0();
				Foo~>serv0();
			end
		''').assertNoErrors(INVISIBLE_FEATURE)
		load('''
			service dom0::caller() is
			begin
				dom::Foo~>func();
			end
		''').assertError(terminatorActionCall, INVISIBLE_FEATURE)
		load('''
			service dom0::caller0() is
			begin
				dom::Foo~>serv();
			end
		''').assertError(terminatorActionCall, INVISIBLE_FEATURE)
		load('''
			service dom0::caller1() is
			begin
				dom::Foo~>serv0();
				dom::Foo~>func0();
			end
		''').assertNoErrors(INVISIBLE_FEATURE)
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
		models.forEach[model | pairs += 'dummy' + modelCount++ + '.masl' -> model]
		load(pairs)
	}		
	
	private def MaslModel load(Pair<String, CharSequence>... fileName2content) {
		var MaslModel model
		for(pair: fileName2content) {
			model = pair.value.parse(URI.createURI(pair.key), rs)
		}
		return model
	}  
	
}
