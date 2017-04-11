package org.xtuml.bp.xtext.masl.validation

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.resource.FileExtensionProvider
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar
import org.xtuml.bp.xtext.masl.MASLExtensions
import org.xtuml.bp.xtext.masl.layout.URI2DeclarationMapper
import org.xtuml.bp.xtext.masl.masl.structure.AssocRelationshipDefinition
import org.xtuml.bp.xtext.masl.masl.structure.DomainDefinition
import org.xtuml.bp.xtext.masl.masl.structure.DomainServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.DomainServiceDefinition
import org.xtuml.bp.xtext.masl.masl.structure.IdentifierDefinition
import org.xtuml.bp.xtext.masl.masl.structure.MaslModel
import org.xtuml.bp.xtext.masl.masl.structure.ObjectDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectDefinition
import org.xtuml.bp.xtext.masl.masl.structure.ObjectServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectServiceDefinition
import org.xtuml.bp.xtext.masl.masl.structure.Parameterized
import org.xtuml.bp.xtext.masl.masl.structure.ProjectDefinition
import org.xtuml.bp.xtext.masl.masl.structure.RegularRelationshipDefinition
import org.xtuml.bp.xtext.masl.masl.structure.StateDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.StateDefinition
import org.xtuml.bp.xtext.masl.masl.structure.StructurePackage
import org.xtuml.bp.xtext.masl.masl.structure.SubtypeRelationshipDefinition
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorDefinition
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorServiceDefinition
import org.xtuml.bp.xtext.masl.masl.structure.TransitionRow
import org.xtuml.bp.xtext.masl.masl.types.EnumerationTypeDefinition
import org.xtuml.bp.xtext.masl.masl.types.StructureTypeDefinition
import org.xtuml.bp.xtext.masl.scoping.ProjectScopeIndexProvider

import static org.xtuml.bp.xtext.masl.validation.MaslIssueCodesProvider.*

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*

class StructureValidator extends AbstractMASLValidator {

	@Inject extension ProjectScopeIndexProvider
	@Inject extension MASLExtensions
	@Inject extension StructurePackage structurePackage
	@Inject extension URI2DeclarationMapper
	@Inject FileExtensionProvider fileExtensionProvider
	
	override register(EValidatorRegistrar registrar) {
	}

	@Check
	def checkFileExtension(MaslModel model) {
		val fileExtension = model.eResource.URI.fileExtension
		if(fileExtensionProvider.isValid(fileExtension)) {
			model.elements.forEach [
				val expectedFileExtensions = switch it {
					ProjectDefinition: #['prj', 'masl']
					DomainDefinition: #['mod', 'int', 'masl']
					ObjectServiceDefinition,
					DomainServiceDefinition: #['ext', 'scn', 'svc', 'fn', 'masl']
					TerminatorServiceDefinition: #['tr', 'masl']
					StateDefinition: #['al', 'masl']
				}
				if (!expectedFileExtensions.contains(
					fileExtension)) {
					addIssue('''«eClass.name» elements should be defined in a file with extension «expectedFileExtensions.map['\'.'+ it + '\''].join(' or ')».''',
						it, structurePackage.abstractNamed_Name, WRONG_STRUCTURE)
				}
			]
		}
	}

	@Check
	def void structureComponentDefs(StructureTypeDefinition it) {
		if (components.empty)
			addIssue('A structure must specify at least one component', it, null, ILLEGAL_EMPTY_LIST)
	}

	@Check
	def void enumerators(EnumerationTypeDefinition it) {
		if (enumerators.empty)
			addIssue('An enumeration must specify at least one enumerator', it, null, ILLEGAL_EMPTY_LIST)
	}

	@Check
	def void attributeReferentials(IdentifierDefinition it) {
		if (attributes.empty)
			addIssue('An attribute referential must specify at least one referential', it, null, ILLEGAL_EMPTY_LIST)
	}

	@Check
	def void identifierAttributes(IdentifierDefinition it) {
		if (attributes.empty)
			addIssue('An identifier must specify at least one attribute', it, null, ILLEGAL_EMPTY_LIST)
	}

	@Check
	def void transitionOptions(TransitionRow it) {
		if (options.empty)
			addIssue('A transition row must specify at least one option', it, null, ILLEGAL_EMPTY_LIST)
	}

	@Check
	def void subtypes(SubtypeRelationshipDefinition it) {
		if (subtypes.empty)
			addIssue('A subtype relationship must specify at least one subtype', it, null, ILLEGAL_EMPTY_LIST)
	}

	@Check
	def void domainDefinitionInProject(ProjectDefinition project) {
		project.domains.forEach [
			(objects + services + relationships + objectDefs + typeForwards + types + exceptions).forEach [
				addIssue('Only terminator definitions are allowed in a project', it,
					structurePackage.abstractNamed_Name, WRONG_STRUCTURE)
			]
		]
	}

	@Check
	def void relationshipEndsAreOpposites(RegularRelationshipDefinition it) {
		if (forwards.from != backwards.to) {
			addIssue('Relationship objects do not correlate', forwards, relationshipEnd_From,
				INCONSISTENT_RELATIONSHIP_ENDS)
			addIssue('Relationship objects do not correlate', backwards, relationshipEnd_To,
				INCONSISTENT_RELATIONSHIP_ENDS)
		}
		if (forwards.to != backwards.from) {
			addIssue('Relationship objects do not correlate', forwards, relationshipEnd_To,
				INCONSISTENT_RELATIONSHIP_ENDS)
			addIssue('Relationship objects do not correlate', backwards, relationshipEnd_From,
				INCONSISTENT_RELATIONSHIP_ENDS)
		}
	}

	@Check
	def void relationshipEndsAreOpposites(AssocRelationshipDefinition it) {
		if (forwards.from != backwards.to) {
			addIssue('Relationship objects do not correlate', forwards, relationshipEnd_From,
				INCONSISTENT_RELATIONSHIP_ENDS)
			addIssue('Relationship objects do not correlate', backwards, relationshipEnd_To,
				INCONSISTENT_RELATIONSHIP_ENDS)
		}
		if (forwards.to != backwards.from) {
			addIssue('Relationship objects do not correlate', forwards, relationshipEnd_To,
				INCONSISTENT_RELATIONSHIP_ENDS)
			addIssue('Relationship objects do not correlate', backwards, relationshipEnd_From,
				INCONSISTENT_RELATIONSHIP_ENDS)
		}
	}

	@Check
	def declarationPresent(ObjectDefinition it) {
		if (getDeclarations(objectDeclaration, index).empty)
			addIssue('Object is has not been declared', it, structurePackage.abstractNamed_Name, MISSING_DECLARATION)
	}

	@Check
	def declarationPresent(ObjectServiceDefinition it) {
		if (getDeclarations(objectServiceDeclaration, index).empty)
			addIssue('Object service has not been declared', it, structurePackage.abstractNamed_Name,
				MISSING_DECLARATION)
	}

	@Check
	def declarationPresent(StateDefinition it) {
		if (getDeclarations(stateDeclaration, index).empty)
			addIssue('State has not been declared', it, structurePackage.abstractNamed_Name, MISSING_DECLARATION)
	}

	@Check
	def declarationPresent(DomainServiceDefinition it) {
		if (getDeclarations(domainServiceDeclaration, index).empty)
			addIssue('Domain service has not been declared', it, structurePackage.abstractNamed_Name,
				MISSING_DECLARATION)
	}

	@Check
	def declarationPresent(TerminatorServiceDefinition it) {
		if (getDeclarations(terminatorServiceDeclaration, index).empty)
			addIssue('Terminator service has not been declared', it, structurePackage.abstractNamed_Name,
				MISSING_DECLARATION)
	}

	@Check
	def definitionPresent(ObjectDeclaration it) {
		if (getDefinitions(objectDefinition, index).empty)
			addIssue('Object has not been defined', it, structurePackage.abstractNamed_Name, MISSING_IMPLEMENTATION)
	}

	@Check
	def definitionPresent(ObjectServiceDeclaration it) {
		if (getDefinitions(objectServiceDefinition, index).empty)
			addIssue('Object service has not been implemented', it, structurePackage.abstractNamed_Name,
				MISSING_IMPLEMENTATION)
	}

	@Check
	def definitionPresent(StateDeclaration it) {
		if (getDefinitions(stateDefinition, index).empty)
			addIssue('State has not been implemented', it, structurePackage.abstractNamed_Name, MISSING_IMPLEMENTATION)
	}

	@Check
	def definitionPresent(DomainServiceDeclaration it) {
		if (!isInterfaceFile && getDefinitions(domainServiceDefinition, index).empty)
			addIssue('Domain service has not been implemented', it, structurePackage.abstractNamed_Name,
				MISSING_IMPLEMENTATION)
	}

	@Check
	def definitionPresent(TerminatorServiceDeclaration it) {
		if (!isInterfaceFile && getDefinitions(terminatorServiceDefinition, index).empty)
			addIssue('Terminator service has not been implemented', it, structurePackage.abstractNamed_Name,
				MISSING_IMPLEMENTATION)
	}

	@Check
	def checkDefinitionsPresent(MaslModel model) {
		val declarationsToImplement = model.declarationsToImplement
		val implementedDeclarations = model.elements.map[getDeclarations(declarationClass, index)].flatten.filter(Parameterized)
		implementedDeclarations.forEach [
			declarationsToImplement -= it
		]
		if(!declarationsToImplement.isEmpty) {
			addIssue('Missing implementation', model, null, MISSING_IMPLEMENTATION, declarationsToImplement.map[URI.toString])
		}		
	}
	
	private def getDeclarationsToImplement(MaslModel model) {
		val definition = getContainerDefinition(model)
		val result = <Parameterized>newHashSet
		switch definition {
			ObjectDefinition:
				if(model.isStateImplementation)
					result += definition.states
				else {
					result += definition.services
				}
			DomainDefinition: {
				result += definition.services
			} 
			TerminatorDefinition: {
				result += definition.services
			}
		}
		return result
	}
	
	private def boolean isInterfaceFile(EObject element) {
		element?.eResource?.URI?.fileExtension == 'int'
	}
}
