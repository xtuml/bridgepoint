package org.xtuml.bp.xtext.masl.scoping

import com.google.common.base.Charsets
import com.google.common.hash.Hashing
import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy
import org.eclipse.xtext.util.IAcceptor
import org.xtuml.bp.xtext.masl.MASLExtensions
import org.xtuml.bp.xtext.masl.masl.structure.DomainDefinition
import org.xtuml.bp.xtext.masl.masl.structure.MaslModel
import org.xtuml.bp.xtext.masl.masl.structure.ObjectDefinition
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorDefinition
import org.xtuml.bp.xtext.masl.masl.types.BuiltinTypeDeclaration
import org.xtuml.bp.xtext.masl.masl.types.EnumerationTypeDefinition
import org.xtuml.bp.xtext.masl.masl.types.TypeDeclaration
import org.apache.log4j.Logger
import org.xtuml.bp.xtext.masl.masl.structure.AbstractActionDefinition
import org.xtuml.bp.xtext.masl.masl.structure.AbstractActionDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.RelationshipDefinition
import org.xtuml.bp.xtext.masl.masl.structure.EventDefinition
import org.xtuml.bp.xtext.masl.masl.structure.ProjectDefinition

class MASLResourceDescriptionStrategy extends DefaultResourceDescriptionStrategy {

	static val String HASH_USER_DATA_KEY = 'HASH'

	static val LOG = Logger.getLogger(MASLResourceDescriptionStrategy)

	@Inject extension MASLExtensions

	override createEObjectDescriptions(EObject eObject, IAcceptor<IEObjectDescription> acceptor) {
		switch eObject {
			BuiltinTypeDeclaration:
				if (eObject.anonymous)
					return false
			TypeDeclaration: {
				val definition = eObject.definition
				if (definition instanceof EnumerationTypeDefinition) {
					definition.enumerators.forEach [
						acceptor.accept(EObjectDescription.create(QualifiedName.create(domainName, name), it))
					]
				}
			}
		}
		doCreateEObjectDescriptions(eObject, acceptor)
		return eObject instanceof ObjectDefinition 
			|| eObject instanceof DomainDefinition 
			|| eObject instanceof MaslModel 
			|| eObject instanceof TerminatorDefinition
			|| eObject instanceof AbstractActionDeclaration
			|| eObject instanceof AbstractActionDefinition
			|| eObject instanceof RelationshipDefinition
			|| eObject instanceof EventDefinition
			|| eObject instanceof ProjectDefinition
	}

	private def doCreateEObjectDescriptions(EObject eObject, IAcceptor<IEObjectDescription> acceptor) {
		try {
			val qualifiedName = qualifiedNameProvider.getFullyQualifiedName(eObject)
			if (qualifiedName !== null) {
				val hash = eObject.hash
				if(hash != null) 
					acceptor.accept(EObjectDescription.create(qualifiedName, eObject, #{HASH_USER_DATA_KEY->hash}))
				else 
					acceptor.accept(EObjectDescription.create(qualifiedName, eObject))
			}
		} catch (Exception exc) {
			LOG.error(exc.getMessage(), exc)
		}
		return true
	}

	/**
	 * We have to make sure the downstream dependencies get rebuilt when a signature changes. 
	 * This is why we add a hash of the signature to the eobject description. 
	 * But we are not allowed to resolve anything in the signatures during indexing.
	 * So we use a rough approach hashing the concrete syntax without comments and whitespaces.
	 */
	private def hash(EObject element) {
		if (element === null)
			return null
		val node = NodeModelUtils.findActualNodeFor(element)
		if (node === null)
			return null
		val hasher = Hashing.md5.newHasher
		node?.leafNodes.filter[!hidden].forEach [
			hasher.putString(text + " ", Charsets.UTF_8)
		]
		hasher.hash.toString
	}

}
