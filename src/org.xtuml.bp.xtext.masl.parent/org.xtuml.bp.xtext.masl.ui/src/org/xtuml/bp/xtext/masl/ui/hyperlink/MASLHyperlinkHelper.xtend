package org.xtuml.bp.xtext.masl.ui.hyperlink

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.jface.text.Region
import org.eclipse.xtext.resource.ILocationInFileProvider
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkHelper
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkAcceptor
import org.eclipse.xtext.util.ITextRegion
import org.xtuml.bp.xtext.masl.MASLExtensions
import org.xtuml.bp.xtext.masl.linking.RankedCandidate
import org.xtuml.bp.xtext.masl.scoping.ProjectScopeIndexProvider
import org.xtuml.bp.xtext.masl.masl.structure.Parameterized
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider
import java.util.List
import org.xtuml.bp.xtext.masl.typesystem.MaslType
import java.util.TreeSet

/**
 * Allows to navigate between definitions and declarations by means of a hyperlink
 * (press F3 key or CTRL-click).
 */
class MASLHyperlinkHelper extends HyperlinkHelper {
	
	@Inject extension MASLExtensions
	@Inject extension MaslTypeProvider
	@Inject extension ILocationInFileProvider
	@Inject extension ProjectScopeIndexProvider
	@Inject extension RankedCandidate.Factory
	 
	override createHyperlinksByOffset(XtextResource resource, int offset, IHyperlinkAcceptor acceptor) {
		super.createHyperlinksByOffset(resource, offset, acceptor)
		val element = EObjectAtOffsetHelper.resolveElementAt(resource, offset)
		if(element instanceof Parameterized) {
			val region = element.significantTextRegion
			if(region.contains(offset)) {
				val paramTypes = element.parameters.map[maslType]
				val index = resource.index
				val declarationClass = element.declarationClass
				if(element.declarationClass != null) {
					getDeclarations(element, declarationClass, index).rankByParameters(paramTypes).forEach[
						createHyperlink('Go to declaration', region, acceptor)
					]
				} else {
					val definitionClass = element.definitionClass
					if(definitionClass != null) {
						getDefinitions(element, definitionClass, index).rankByParameters(paramTypes).forEach[
							createHyperlink('Go to definition', region, acceptor)
						]
					}
				}
			}
		}
	}
	
	private def rankByParameters(Iterable<EObject> elements, List<MaslType> paramTypes) {
		val exactCandidates = new TreeSet<RankedCandidate>()
		val acceptableCandidates = new TreeSet<RankedCandidate>()
		for(element: elements.filter(Parameterized)) {
			val ranked = element.rankParameterized(paramTypes)
			if(ranked.isExact)
				exactCandidates += ranked	
			else if(ranked.isAcceptable)
				acceptableCandidates += ranked	
		}
		if(exactCandidates.empty) 
			acceptableCandidates
		else 
			exactCandidates
	}
	
	private def createHyperlink(RankedCandidate target, String text, ITextRegion region, IHyperlinkAcceptor acceptor) {
		acceptor.accept(
			hyperlinkProvider.get => [
				hyperlinkText = text + ' in ' + target.candidate.eResource.URI.lastSegment
				hyperlinkRegion = new Region(region.offset, region.length)
				URI = EcoreUtil.getURI(target.candidate)
			])
	}
	
	override createHyperlinksTo(XtextResource from, Region region, EObject target, IHyperlinkAcceptor acceptor) {
		if(target.eResource.URI.scheme!='classpath')
			super.createHyperlinksTo(from, region, target, acceptor)
	}
}