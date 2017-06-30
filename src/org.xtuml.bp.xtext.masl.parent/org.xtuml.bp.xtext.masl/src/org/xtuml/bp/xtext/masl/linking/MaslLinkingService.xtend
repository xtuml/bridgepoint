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
import org.xtuml.bp.xtext.masl.masl.behavior.ActionCall
import org.xtuml.bp.xtext.masl.masl.behavior.BehaviorPackage
import org.xtuml.bp.xtext.masl.masl.behavior.CharacteristicCall
import org.xtuml.bp.xtext.masl.masl.behavior.SimpleFeatureCall
import org.xtuml.bp.xtext.masl.masl.structure.AbstractFeature
import org.xtuml.bp.xtext.masl.masl.structure.Characteristic
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider

class MaslLinkingService extends DefaultLinkingService {

	@Inject extension BehaviorPackage
	@Inject extension IQualifiedNameConverter
	@Inject extension MaslTypeProvider
	@Inject extension RankedCandidate.Factory
	
	override getLinkedObjects(EObject context, EReference ref, INode node) throws IllegalNodeException {
		if (ref === featureCall_Feature && context instanceof SimpleFeatureCall) {
			val container = context.eContainer
			if(container instanceof ActionCall)
				return getLinkedAction(container, context as SimpleFeatureCall, ref, node)
		}
		if(ref === characteristicCall_Characteristic && context instanceof CharacteristicCall) {
			return getLinkedCharacteristic(context as CharacteristicCall, node)
		}
		return super.getLinkedObjects(context, ref, node)
	}

	private def List<EObject> getLinkedAction(ActionCall actionCall, SimpleFeatureCall featureCall, EReference reference, INode node) {
		val needsReturnType = actionCall.eContainmentFeature.EType !== statement
		val crossRefString = node.crossRefNodeAsString
		if (crossRefString !== null && !crossRefString.equals("")) {
			val scope = getScope(featureCall, reference)
			var qualifiedLinkName = crossRefString.toQualifiedName
			var eObjectDescriptions = scope.getElements(qualifiedLinkName)
			val argumentTypes = actionCall.arguments.map[maslType]
			val candidates = eObjectDescriptions.map[actionCall.eResource.resourceSet.getEObject(EObjectURI, true)]
			var RankedCandidate currentBestMatch = null
			var int numAcceptableMatches 
			for(candidate: candidates.filter(AbstractFeature)) {
				val ranked = candidate.rank(argumentTypes, needsReturnType)
				if(ranked.isAcceptable) 
					numAcceptableMatches ++
				if(ranked > currentBestMatch) {
					currentBestMatch = ranked				
				}
			}
			if(currentBestMatch != null) {
				if(!currentBestMatch.exact && numAcceptableMatches > 1) {
					val diagnostic = new XtextLinkingDiagnostic(node, 'Ambiguous action call.', Diagnostic.LINKING_DIAGNOSTIC)
					actionCall.eResource.errors.add(diagnostic)	
				}
				return #[currentBestMatch.candidate]
			}
		}
		return emptyList()
	}
	
	private def List<EObject> getLinkedCharacteristic(CharacteristicCall characteristicCall, INode node) {
		val crossRefString = node.crossRefNodeAsString
		if (crossRefString !== null && !crossRefString.equals("")) {
			val scope = getScope(characteristicCall, characteristicCall_Characteristic)
			var qualifiedLinkName = crossRefString.toQualifiedName
			var eObjectDescriptions = scope.getElements(qualifiedLinkName)
			val candidates = eObjectDescriptions
				.map[characteristicCall.eResource.resourceSet.getEObject(EObjectURI, true)]
				.filter(Characteristic)
				.filter[parameters.size == characteristicCall.arguments.size]
			return newArrayList(candidates)
		}
		return emptyList()
	}
}
