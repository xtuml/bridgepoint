package org.xtuml.bp.xtext.masl.tests.typesystem

import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.util.StringInputStream
import org.junit.Test
import org.xtuml.bp.xtext.masl.masl.behavior.AssignStatement
import org.xtuml.bp.xtext.masl.tests.AbstractMaslModelTest

import static org.junit.Assert.*

class TypeConformanceTest extends AbstractMaslModelTest {
	
	@Test 
	def void testStructure() {
		assertNoError('''
			domain dom is
				type s_type is structure
					int_val: integer;
					string_val: string;
				end;
			end;
		''', '''
			service dom::svc() is
				s: s_type;
				s_list: bag of s_type;
			begin
				s := (1, "");
				s_list := (1, "");
			end;
		''')
	}
	
	@Test
	def void testIntegerAssignment() {
		assertNoError('''
			domain IntegerAssignment is
			  public service test();
			end domain;
		''', '''
			public service IntegerAssignment::test() is
			  l : long_integer;
			  i : integer;
			  r : real;
			begin
			  l := 0;
			  i := 0;
			  r := 0;
			end;
		''')
	}
	
	
	@Test 
	def void bug9472ErrorsTest() {
		val rs = resourceSetProvider.get
		val r0 = rs.createResource(URI.createURI('dummy.mod'))
		r0.load(new StringInputStream('''
			domain dom9472 is
			  public service test();
			end domain;
		'''), null)
		
		val r1 = rs.createResource(URI.createURI('dummy.ext'))
		r1.load(new StringInputStream('''
			service dom9472::test() is
			    r  : real;
			    l  : long_integer;
			    i  : integer;
			    ar : anonymous real;
			    al : anonymous long_integer;
			    ai : anonymous integer;
			begin
			    // Invalid assignments - all the following should fail (when uncommented!)
			    r  := l;
			    r  := i;
			    l  := r;
			    l  := i;
			    l  := ar;
			    i  := r;
			    i  := l;
			    i  := ar;
			    al := r;
			    al := ar;
			    ai := r;
			    ai := ar;
			end;
		'''), null)
		val issues = validate(r1)
		assertEquals(12, issues.size)
		issues.forEach [
			val element = r1.getEObject(uriToProblem.fragment)
			assertNotNull(element)
			assertTrue(element instanceof AssignStatement)
			assertTrue(message.contains('Expected'))
			assertTrue(message.contains('but was'))
		]
	}
	
	
	protected def void assertNoError(CharSequence modFile, CharSequence extFile) {
		getElementsAtCarets('dummy.mod' -> modFile, 'dummy.ext' -> extFile) 
	}
}