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
					instance service iFunc() return instance of Foo;
					instance service iSvc();
					service func() return instance of Foo;
					service svc();
				end;
				service dFunc() return boolean
				service dSvc();
			end;
		''')
		load('''
			instance service dom::Foo.iFunc() return instance of Foo is
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
			service dom::Foo.func() return instance of Foo is
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
			service dom::dFunc() return boolean is
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
				service foo() return integer;
			end;
			
			service dom::foo() return integer is
			begin
				return "";
			end;
		''').elements.last.assertError(returnStatement, WRONG_TYPE)
	}

	@Test
	def void testUnreachableCode() {
		load('''
			domain dom is 
				service foo() return integer;
			end;
			
			service dom::foo() return integer is
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
				public service func() return integer;
				private service serv();
			end;
		''').assertNoErrors()
		load('''	
			private service dom::func() return integer 
			is
			begin
			end;
		''').assertError(domainServiceDefinition, DECLARATION_MISSMATCH)
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
				service func() return integer;
			end;
			
			service dom::func() return integer 
			is
			begin
			end;
		''').elements.last.assertNoError(DECLARATION_MISSMATCH)
	}

	@Test 
	def void testParameterValidation1() { 
		load('''
			domain dom is 
				service func(p: in integer) return integer;
			end;
			
			service dom::func() return integer 
			is
			begin
			end;
		''').elements.last.assertError(domainServiceDefinition, DECLARATION_MISSMATCH)
	}

	@Test 
	def void testParameterValidation2() { 
		load('''
			domain dom is 
				service func() return integer;
			end;
			
			service dom::func(p: in integer) return integer 
			is
			begin
			end;
		''').elements.last.assertError(domainServiceDefinition, DECLARATION_MISSMATCH)
	}

	@Test 
	def void testParameterValidation3() { 
		load('''
			domain dom is 
				service func(p: in string) return integer;
			end;
			
			service dom::func(p: in integer) return integer 
			is
			begin
			end;
		''').elements.last.assertError(domainServiceDefinition, DECLARATION_MISSMATCH)
	}

	@Test 
	def void testParameterValidation4() { 
		load('''
			domain dom is 
				service func(p: in integer, p: in string) return integer;
			end;
			
			service dom::func(p: in integer) return integer 
			is
			begin
			end;
		''').elements.last.assertError(domainServiceDefinition, DECLARATION_MISSMATCH)
	}

	@Test 
	def void testParameterValidation5() { 
		load('''
			domain dom is 
				service func(p: in integer) return integer;
			end;
			
			service dom::func(p: in integer) return string
			is
			begin
			end;
		''').elements.last.assertError(domainServiceDefinition, DECLARATION_MISSMATCH)
	}

	@Test 
	def void testParameterValidation6() { 
		load('''
			domain dom is 
				service func(p: in integer) return integer;
			end;
			
			service dom::func(p: out integer) return integer
			is
			begin
			end;
		''').elements.last.assertError(parameter, DECLARATION_MISSMATCH)
	}

	@Test 
	def void testParameterValidation7() { 
		load('''
			domain dom is 
				service func(p: in integer) return integer;
				service func(p: in real) return integer;
			end;
			
			service dom::func(p: in real) return integer
			is
			begin
			end;
		''').elements.last.assertNoError(DECLARATION_MISSMATCH)
	}

	@Test
	def void testDomainActionVisibilities() {
		load('''
			domain dom0 is 
				private service func() return integer;
				private service serv();
				public service func0() return integer;
				public service serv0();
			end;
			
			domain dom1 is 
				service func() return integer;
				service serv();
				service serv1();
			end;
		''').assertNoErrors
		load('''
			service dom1::func() return integer is
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
					private service func() return integer;
					private service serv();
					public service func0() return integer;
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
	def void testActionOverloading() {
		load('''
			domain dom is
				service f(x: in integer) return integer;
				service f(x: in string) return integer;
			end;
		''').assertNoErrors(DUPLICATE_NAME)
	}
	
	@Test
	def void testActionOverloading1() {
		load('''
			domain dom is
				service f(x: in integer) return integer;
				service f(y: in integer) return integer;
			end;
		''').assertError(domainServiceDeclaration, DUPLICATE_NAME)
	}
	
	@Test
	def void testActionOverloading2() {
		load('''
			domain dom is
				service f(x: in integer) return integer;
				service f(x: in string) return string;
			end;
		''').assertNoErrors(DUPLICATE_NAME)
	}
	
	@Test
	def void testActionOverloading3() {
		load('''
			service dom::f(x: in integer) return integer is begin end;
			service dom::f(x: in string) return integer is begin end;
		''').assertNoErrors(DUPLICATE_NAME)
	}
	
	@Test
	def void testActionOverloading4() {
		load('''
			service dom::f(x: in integer) return integer is begin end;
			service dom::f(y: in integer) return integer is begin end;
		''').assertError(domainServiceDefinition, DUPLICATE_NAME)
	}
	
	@Test
	def void testActionOverloading5() {
		load('''
			service dom::f(x: in integer) return integer is begin end;
			service dom::f(x: in string) return string is begin end;
		''').assertNoErrors(DUPLICATE_NAME)
	}
	
	@Test
	def void testTerminatorActionVisibilities() {
		load('''
			domain dom is
				terminator Foo is
					service func() return integer;
					service serv();
				end;
			end;
			
			domain dom0 is
				service caller();
				service caller0();
			end;
		''').assertNoErrors
		load('''
			service dom::Foo~>serv() is
			begin
				Foo~>func();
				Foo~>serv();
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
	}
	
	@Test 
	def void testLinkExpression() {
		load('''
			domain dom is
				object Foo;
				object Foo is end;
				relationship R1 is 
					Foo conditionally there one Foo,
					Foo conditionally back one Foo
					using Foo;
				service serv(foo: in instance of Foo);				
			end;
		''').assertNoErrors()
		load('''	
			service dom::serv(foo: in instance of Foo) is
				foo2: instance of Foo;
			begin
				foo2 = link foo R1 foo;
			end
		''').assertNoErrors(INVALID_LINK_EXPRESSION)
	}

	@Test 
	def void testLinkExpression1() {
		load('''
			domain dom is
				object Foo;
				object Foo is end;
				relationship R1 is 
					Foo conditionally there one Foo,
					Foo conditionally back one Foo;
				service serv(foo: in instance of Foo);				
			end;
		''').assertNoErrors()
		load('''	
			service dom::serv(foo: in instance of Foo) is
				foo2: instance of Foo;
			begin
				foo2 = link foo R1 foo;
			end
		''').assertError(relationshipNavigation, INVALID_LINK_EXPRESSION)
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
