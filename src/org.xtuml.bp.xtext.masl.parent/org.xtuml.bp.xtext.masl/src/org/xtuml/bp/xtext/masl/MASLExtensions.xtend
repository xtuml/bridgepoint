package org.xtuml.bp.xtext.masl

import com.google.inject.Inject
import java.util.Set
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.resource.ISelectable
import org.xtuml.bp.xtext.masl.masl.behavior.LinkExpression
import org.xtuml.bp.xtext.masl.masl.behavior.NavigateExpression
import org.xtuml.bp.xtext.masl.masl.structure.AbstractActionDefinition
import org.xtuml.bp.xtext.masl.masl.structure.AbstractNamed
import org.xtuml.bp.xtext.masl.masl.structure.AbstractService
import org.xtuml.bp.xtext.masl.masl.structure.AttributeDefinition
import org.xtuml.bp.xtext.masl.masl.structure.DomainDefinition
import org.xtuml.bp.xtext.masl.masl.structure.DomainServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectDefinition
import org.xtuml.bp.xtext.masl.masl.structure.ObjectServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectServiceDefinition
import org.xtuml.bp.xtext.masl.masl.structure.RelationshipEnd
import org.xtuml.bp.xtext.masl.masl.structure.RelationshipNavigation
import org.xtuml.bp.xtext.masl.masl.structure.StateDefinition
import org.xtuml.bp.xtext.masl.masl.structure.StructurePackage
import org.xtuml.bp.xtext.masl.masl.structure.SubtypeRelationshipDefinition
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorServiceDefinition

/**
 * Utility methods for MASL model elements.
 */
class MASLExtensions {

	@Inject extension IQualifiedNameProvider
	@Inject extension StructurePackage maslPackage

	/**
	 * @return the name of the domain the argument is contained in without resolving cross-references. 
	 */
	def String getDomainName(EObject it) {
		switch it {
			DomainDefinition:
				name
			AbstractActionDefinition:
				getReferredElementName(abstractActionDefinition_Domain)
			default:
				eContainer?.domainName
		}
	}

	/**
	 * @return the name of the object the argument is contained in without resolving cross-references. 
	 */
	def String getObjectName(EObject it) {
		switch it {
			ObjectServiceDefinition:
				getReferredElementName(objectServiceDefinition_Object)
			StateDefinition:
				getReferredElementName(stateDefinition_Object)
			default:
				eContainer?.objectName
		}
	}

	/**
	 * @return the name of the terminator the argument is contained in without resolving cross-references. 
	 */
	def String getTerminatorName(EObject it) {
		switch it {
			TerminatorServiceDefinition:
				getReferredElementName(terminatorServiceDefinition_Terminator)
			default:
				eContainer?.terminatorName
		}
	}

	private def String getReferredElementName(EObject it, EReference ref) {
		val value = eGet(ref, false)
		if (value instanceof AbstractNamed) {
			if (value.eIsProxy) {
				NodeModelUtils.findNodesForFeature(it, ref).map[leafNodes].flatten.filter[!hidden].map[text].join
			} else {
				value.eGet(abstractNamed_Name) as String
			}
		}
	}

	/**
	 * @return all supertypes including the object itself.
	 */
	def Iterable<ObjectDefinition> getAllSupertypes(ObjectDefinition object, ISelectable  index) {
		val result = newHashSet
		internalGetAllSupertypes(object, index, result)
		result
	}

	private def void internalGetAllSupertypes(ObjectDefinition object, ISelectable index,
		Set<ObjectDefinition> result) {
		if (object == null || !result.add(object))
			return // definition already captured
		val domain = object.eContainer as DomainDefinition
		domain.relationships.filter(SubtypeRelationshipDefinition).filter[subtypes.contains(object)].map [
			supertype?.getObjectDefinition(index)
		].forEach [
			internalGetAllSupertypes(index, result)
		]
	}

	/**
	 * @return the object definition associated with an object declaration, by means of index lookup. 
	 */
	def ObjectDefinition getObjectDefinition(ObjectDeclaration it, ISelectable index) {
		val defs = getDefinitions(objectDefinition, index) 
		if(defs.size != 1)
			return null
		else 
			return defs.head as ObjectDefinition
	}
	
	def EClass getDefinitionClass(EObject declaration) {
		if (declaration != null) {
			val declarationClassName = declaration.eClass.name
			val index = declarationClassName.lastIndexOf('Declaration')
			if(index != -1) {
				val definitionClass = maslPackage.getEClassifier(declarationClassName.substring(0, index) + 'Definition')
				if(definitionClass instanceof EClass)
					return definitionClass
			}
		}
		return null
	}
	
	def EClass getDeclarationClass(EObject definition) {
		if (definition != null) {
			val definitionClassName = definition.eClass.name
			val index = definitionClassName.lastIndexOf('Definition')
			if(index != -1) {
				val declarationClass = maslPackage.getEClassifier(definitionClassName.substring(0, index) + 'Declaration')
				if(declarationClass instanceof EClass)
					return declarationClass
			}				
		}
		return null
	}
	
	def Iterable<EObject> getDefinitions(EObject declaration, EClass definitionClass, ISelectable index) {
		if (declaration != null && definitionClass != null) {
			val entries = index.getExportedObjects(definitionClass, declaration.fullyQualifiedName, false)
			return entries.map[EcoreUtil.resolve(EObjectOrProxy, declaration)]
		}
		return emptyList
	}

	def Iterable<EObject> getDeclarations(EObject definition, EClass declarationClass, ISelectable index) {
		if (definition != null && declarationClass != null) {
			val entries = index.getExportedObjects(declarationClass, definition.fullyQualifiedName, false)
			return entries.map[EcoreUtil.resolve(EObjectOrProxy, definition)] 
		}
		return emptyList
	}

	/**
	 * @return the object declaration this relationship navigation points to.
	 */
	def ObjectDeclaration getReferredObject(RelationshipNavigation it) {
		if (it != null) {
			if (relationship instanceof SubtypeRelationshipDefinition) {
				if(objectOrRole instanceof ObjectDeclaration)				
					return objectOrRole as ObjectDeclaration
				else 
					return (relationship as SubtypeRelationshipDefinition).supertype
			}
			else {
				switch objectOrRole {
					ObjectDeclaration:
						return (objectOrRole as ObjectDeclaration)
					RelationshipEnd:
						return (objectOrRole as RelationshipEnd).to
				}
			}
		}
		return null
	}

	def boolean isAction(EObject it) {
		switch it {
			DomainServiceDeclaration,
			ObjectServiceDeclaration,
			TerminatorServiceDeclaration:
				true
			default:
				false		
		}
	}
		
	def ObjectDeclaration getContainerObject(EObject expr) {
		var parent = expr.eContainer
		while (parent != null) {
			switch parent {
				ObjectServiceDefinition:
					return parent.getObject
				StateDefinition:
					return parent.object
			}
			parent = parent.eContainer
		}
		return null
	}
		
	def boolean isIdentifier(AttributeDefinition attr) {
		val isIdentifier = (attr.eContainer as ObjectDefinition)
			.identifiers
			.exists[attributes.contains(attr)]
		isIdentifier
	}
	
	def boolean hasReturnType(EObject action) {
		return action instanceof AbstractService && (action as AbstractService).returnType !== null
	}
	
	def getReceiver(RelationshipNavigation navigation) {
		val parent = navigation.eContainer
		return switch parent {
			NavigateExpression: parent.lhs
			LinkExpression:  parent.lhs
			default: null
		}
	}
}
