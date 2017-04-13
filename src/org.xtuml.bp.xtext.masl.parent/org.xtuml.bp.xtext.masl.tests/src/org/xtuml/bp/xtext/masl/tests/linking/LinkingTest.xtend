package org.xtuml.bp.xtext.masl.tests.linking

import org.xtuml.bp.xtext.masl.tests.AbstractMaslModelTest
import org.junit.Test
import org.eclipse.xtext.naming.IQualifiedNameProvider
import com.google.inject.Inject
import static org.junit.Assert.*
import org.eclipse.emf.ecore.EObject
import org.xtuml.bp.xtext.masl.masl.behavior.SimpleFeatureCall

class LinkingTest extends AbstractMaslModelTest {
	
	@Inject extension IQualifiedNameProvider
	
	@Test
	def void testAmgibuousEnumLiterals() {
		val elements = getElementsAtCarets('dom.mod' -> ''' 
			domain dom is
				type PrimaryColour is enum ( RED, BLUE, GREEN );
				type RainbowColour is enum ( RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET);
				service foo();				
			end
		''', 
		'service.masl' ->'''
			service dom::foo() is
				p : PrimaryColour;
				r : RainbowColour;
				i : integer;
			begin
				p := ^RED; // OK, implicit disambiguation, assigning to variable of type PrimaryColor
				r := ^RED; // OK, implicit disambiguation, assigning to variable of type RainbowColor
				p := PrimaryColour::^RED; // OK, explicit disambiguation
				r := RainbowColour::^RED; // OK, explicit disambiguation
				i := PrimaryColour::^RED'value; // OK - explicit disambiguation				
				i := RainbowColour::^RED'value; // OK - explicit disambiguation
			end
		''')
		assertEnumTypeLinked('PrimaryColour', elements.get(0))
		assertEnumTypeLinked('RainbowColour', elements.get(1))
		assertEnumTypeLinked('PrimaryColour', elements.get(2))
		assertEnumTypeLinked('RainbowColour', elements.get(3))
		assertEnumTypeLinked('PrimaryColour', elements.get(4))
		assertEnumTypeLinked('RainbowColour', elements.get(5))
	}
	
	protected def assertEnumTypeLinked(String enumerationTypeName, EObject element) {
		assertTrue(element instanceof SimpleFeatureCall)
		assertEquals(enumerationTypeName, (element as SimpleFeatureCall).feature.fullyQualifiedName.getSegment(1))
	}
}