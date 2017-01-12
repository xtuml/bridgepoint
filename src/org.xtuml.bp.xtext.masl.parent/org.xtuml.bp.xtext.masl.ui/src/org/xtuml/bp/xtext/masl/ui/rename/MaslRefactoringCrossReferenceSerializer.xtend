package org.xtuml.bp.xtext.masl.ui.rename

import org.eclipse.emf.ecore.EObject
import org.eclipse.ltk.core.refactoring.RefactoringStatus
import org.eclipse.xtext.CrossReference
import org.eclipse.xtext.ui.refactoring.impl.RefactoringCrossReferenceSerializer
import org.eclipse.xtext.ui.refactoring.impl.RefactoringCrossReferenceSerializer.RefTextEvaluator
import org.eclipse.xtext.ui.refactoring.impl.StatusWrapper
import org.eclipse.xtext.util.ITextRegion

class MaslRefactoringCrossReferenceSerializer extends RefactoringCrossReferenceSerializer {
	
	override getCrossRefText(EObject owner, CrossReference crossref, EObject target, RefTextEvaluator refTextEvaluator, ITextRegion linkTextRegion, StatusWrapper status) {
		super.getCrossRefText(owner, crossref, target, refTextEvaluator, linkTextRegion, new StatusWrapper {
			override add(int severity, String message, EObject element, ITextRegion region) {
				// Don't warn about misconfigured language.
				// Xtext will try to insert fully qualified names as cross-references which are not
				// allowed by the MASL grammar, yielding these warnings. 
				if(severity != RefactoringStatus.WARNING)
					super.add(severity, message, element, region)
			}
		})
	}
}