package org.xtuml.bp.xtext.masl.tests.typesystem

import org.junit.Test
import org.xtuml.bp.xtext.masl.tests.AbstractMaslModelTest

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
	
	protected def void assertNoError(CharSequence modFile, CharSequence extFile) {
		getElementsAtCarets('dummy.mod' -> modFile, 'dummy.ext' -> extFile) 
	}
}