package org.xtuml.bp.xtext.masl.ui.hyperlink

import com.google.inject.Inject
import java.util.List
import java.util.TreeSet
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
import org.xtuml.bp.xtext.masl.masl.structure.Parameterized
import org.xtuml.bp.xtext.masl.scoping.ProjectScopeIndexProvider
import org.xtuml.bp.xtext.masl.typesystem.MaslType
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider

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
		if(resource.URI.fileExtension == 'mod' || element.eResource.URI.fileExtension != 'mod') {
			if(element instanceof Parameterized) {
				val region = element.significantTextRegion
				if(region.contains(offset)) {
					val paramTypes = element.parameters.map[maslType]
					val index = resource.index
					val declarationClass = element.declarationClass
					if(element.declarationClass != null) {
						getDeclarations(element, declarationClass, index).rankByParameters(paramTypes, element.hasReturnType).forEach[
							createHyperlink('Go to declaration', region, acceptor)
						]
					} else {
						val definitionClass = element.definitionClass
						if(definitionClass != null) {
							getDefinitions(element, definitionClass, index).rankByParameters(paramTypes, element.hasReturnType).forEach[
								createHyperlink('Go to definition', region, acceptor)
							]
						}
					}
				}
			}
		}
	}
	
	private def rankByParameters(Iterable<EObject> elements, List<MaslType> paramTypes, boolean needsReturnType) {
		val exactCandidates = new TreeSet<RankedCandidate>()
		val acceptableCandidates = new TreeSet<RankedCandidate>()
		for(element: elements.filter(Parameterized)) {
			val ranked = element.rankParameterized(paramTypes, needsReturnType)
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
		val targetResourceURI = target.eResource.URI
		if(targetResourceURI.scheme != 'classpath' && (from.URI.fileExtension == 'mod' || targetResourceURI.fileExtension != 'mod'))
			super.createHyperlinksTo(from, region, target, acceptor)
	}
}