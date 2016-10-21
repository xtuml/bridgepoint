package org.xtuml.bp.xtext.masl.ide.highlighting

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.ide.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator
import org.eclipse.xtext.ide.editor.syntaxcoloring.HighlightingStyles
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor
import org.eclipse.xtext.util.CancelIndicator
import org.xtuml.bp.xtext.masl.masl.behavior.BehaviorPackage
import org.xtuml.bp.xtext.masl.masl.behavior.CharacteristicCall

class MaslSemanticHighligtingCalculator extends DefaultSemanticHighlightingCalculator {

	@Inject extension BehaviorPackage 
	
	override protected highlightElement(EObject element, IHighlightedPositionAcceptor acceptor, CancelIndicator cancelIndicator) {
		if(element instanceof CharacteristicCall) {
			highlightFeature(acceptor, element, characteristicCall_Characteristic, HighlightingStyles.DEFAULT_ID)
		}
		return false
	}
}