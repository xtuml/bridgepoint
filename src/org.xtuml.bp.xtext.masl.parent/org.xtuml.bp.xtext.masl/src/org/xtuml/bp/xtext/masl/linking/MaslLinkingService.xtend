package org.xtuml.bp.xtext.masl.linking

import com.google.inject.Inject
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.diagnostics.Diagnostic
import org.eclipse.xtext.linking.impl.DefaultLinkingService
import org.eclipse.xtext.linking.impl.IllegalNodeException
import org.eclipse.xtext.linking.impl.XtextLinkingDiagnostic
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.nodemodel.INode
import org.xtuml.bp.xtext.masl.masl.behavior.BehaviorPackage
import org.xtuml.bp.xtext.masl.masl.behavior.OperationCall
import org.xtuml.bp.xtext.masl.masl.behavior.SimpleFeatureCall
import org.xtuml.bp.xtext.masl.masl.structure.AbstractFeature
import org.xtuml.bp.xtext.masl.masl.structure.Parameterized
import org.xtuml.bp.xtext.masl.masl.types.TypeDeclaration
import org.xtuml.bp.xtext.masl.typesystem.MaslType
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider

import static org.xtuml.bp.xtext.masl.linking.SignatureRanker.*

class MaslLinkingService extends DefaultLinkingService {

	@Inject extension BehaviorPackage
	@Inject extension IQualifiedNameConverter
	@Inject extension MaslTypeProvider
	@Inject extension SignatureRanker
	
	override getLinkedObjects(EObject context, EReference ref, INode node) throws IllegalNodeException {
		if (ref == featureCall_Feature && context instanceof SimpleFeatureCall) {
			val container = context.eContainer
			if(container instanceof OperationCall)
				return getLinkedOperation(container, context as SimpleFeatureCall, ref, node)
		}
		return super.getLinkedObjects(context, ref, node)
	}

	private def List<EObject> getLinkedOperation(OperationCall operationCall, SimpleFeatureCall featureCall, EReference reference, INode node) {
		val crossRefString = node.crossRefNodeAsString
		if (crossRefString !== null && !crossRefString.equals("")) {
			val scope = getScope(featureCall, reference)
			var qualifiedLinkName = crossRefString.toQualifiedName
			var eObjectDescriptions = scope.getElements(qualifiedLinkName)
			val argumentTypes = operationCall.arguments.map[maslType]
			val candidates = eObjectDescriptions.map[operationCall.eResource.resourceSet.getEObject(EObjectURI, true)]
			var AbstractFeature currentBestMatch = null
			val acceptableMatches = newArrayList
			var currentBestRank = NO_MATCH
			for(candidate: candidates.filter(AbstractFeature)) {
				val rank = candidate.getMatchRank(argumentTypes)
				if(rank >= ACCEPTABLE_MATCH) 
					acceptableMatches += candidate
				if(rank > currentBestRank) {
					currentBestRank = rank					
					currentBestMatch = candidate
				}
			}
			if(currentBestMatch != null) {
				if(currentBestRank != PERFECT_MATCH && acceptableMatches.size > 1) {
					val diagnostic = new XtextLinkingDiagnostic(node, 'Ambiguous operation call.', Diagnostic.LINKING_DIAGNOSTIC)
					operationCall.eResource.errors.add(diagnostic)	
				}
				return #[currentBestMatch]
			}
		}
		return emptyList()
	}
	
	
	private def int getMatchRank(AbstractFeature feature, List<MaslType> argumentTypes) {
		switch feature {
			TypeDeclaration:
				if(argumentTypes.size === 1)
					return PERFECT_MATCH
				else 
					return JUST_NAME_MATCH
			Parameterized:
				return feature.getRank(argumentTypes)
			default:
				return JUST_NAME_MATCH
		}
	}
}
