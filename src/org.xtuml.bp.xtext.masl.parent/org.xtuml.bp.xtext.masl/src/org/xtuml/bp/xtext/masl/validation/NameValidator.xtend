package org.xtuml.bp.xtext.masl.validation

import com.google.common.collect.HashMultimap
import com.google.inject.Inject
import java.util.regex.Pattern
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.resource.FileExtensionProvider
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar
import org.xtuml.bp.xtext.masl.masl.behavior.CodeBlock
import org.xtuml.bp.xtext.masl.masl.structure.AbstractActionDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.AbstractActionDefinition
import org.xtuml.bp.xtext.masl.masl.structure.DomainDefinition
import org.xtuml.bp.xtext.masl.masl.structure.MaslModel
import org.xtuml.bp.xtext.masl.masl.structure.ObjectDefinition
import org.xtuml.bp.xtext.masl.masl.structure.Parameterized
import org.xtuml.bp.xtext.masl.masl.structure.ProjectDefinition
import org.xtuml.bp.xtext.masl.masl.structure.RegularRelationshipDefinition
import org.xtuml.bp.xtext.masl.masl.structure.RelationshipDefinition
import org.xtuml.bp.xtext.masl.masl.structure.StateDefinition
import org.xtuml.bp.xtext.masl.masl.structure.StructurePackage
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorDefinition
import org.xtuml.bp.xtext.masl.masl.types.TypesPackage
import org.xtuml.bp.xtext.masl.scoping.ProjectScopeIndexProvider

import static org.xtuml.bp.xtext.masl.validation.MaslIssueCodesProvider.*
import org.xtuml.bp.xtext.masl.masl.structure.DomainServiceDefinition
import org.xtuml.bp.xtext.masl.masl.structure.ObjectServiceDefinition
import org.xtuml.bp.xtext.masl.MASLExtensions

class NameValidator extends AbstractMASLValidator {
	
	override register(EValidatorRegistrar registrar) {
	}
	
	@Inject extension MASLExtensions
	@Inject extension TypesPackage
	@Inject extension SignatureProvider
	@Inject extension StructurePackage structurePackage
	@Inject extension IQualifiedNameProvider
	@Inject extension ProjectScopeIndexProvider
	@Inject FileExtensionProvider fileExtensionProvider
	
	static val INT_PATTERN = Pattern.compile('[0-9]+')
	
	@Check
	def relationName(RelationshipDefinition it) {
		if(!name.startsWith('R'))
			addIssue("Relationship name should start with an 'R'", it, structurePackage.abstractNamed_Name, NAMING_CONVENTION)
		if(!INT_PATTERN.matcher(name.substring(1)).matches)
			addIssue("Relationship name should end with an integer number", it, structurePackage.abstractNamed_Name, NAMING_CONVENTION)
	} 
	
	@Check
	def modelNamesAreUnique(MaslModel it) {
		if(fileExtensionProvider.isValid(eResource.URI.fileExtension)) {
			val allDomainsInFile = elements.filter(DomainDefinition) 
				+ elements.filter(ProjectDefinition).map[domains].flatten
			checkNamesAreGloballyUnique(allDomainsInFile, domainDefinition)
			checkNamesAreGloballyUnique(elements.filter(ProjectDefinition), projectDefinition)
			checkNamesAreGloballyUnique(elements.filter(ObjectServiceDefinition), objectServiceDefinition)
			checkNamesAreGloballyUnique(elements.filter(StateDefinition), stateDefinition)
			checkNamesAreGloballyUnique(elements.filter(DomainServiceDefinition), domainServiceDefinition)			
		}
	}
	
	@Check
	def domainNamesAreUnique(DomainDefinition it) {
		if(fileExtensionProvider.isValid(eResource.URI.fileExtension)) {
			checkNamesAreGloballyUnique(objects, objectDeclaration)
			checkNamesAreGloballyUnique(services, domainServiceDeclaration)
			checkNamesAreGloballyUnique(terminators, terminatorDefinition)
			checkNamesAreGloballyUnique(relationships, relationshipDefinition)
			checkNamesAreGloballyUnique(objectDefs, objectDefinition)
			checkNamesAreGloballyUnique(typeForwards, typeForwardDeclaration)
			checkNamesAreGloballyUnique(types, typeDeclaration)
			checkNamesAreGloballyUnique(exceptions, exceptionDeclaration)
		}
	}
	
	@Check
	def terminatorNamesAreUnique(TerminatorDefinition it) {
		checkNamesAreGloballyUnique(services, terminatorServiceDeclaration)
	}
	
	@Check
	def parameterNamesAreUnique(Parameterized it) {
		checkNamesAreLocallyUnique(parameters)
	}
	
	@Check 
	def objectNamesAreUnique(ObjectDefinition it) {
		checkNamesAreLocallyUnique(attributes)
		checkNamesAreLocallyUnique(services)
		checkNamesAreLocallyUnique(events)
		checkNamesAreLocallyUnique(states)
	}
	
	@Check
	def relationNamesAreUnique(RegularRelationshipDefinition it) {
		if(forwards.name == backwards.name 
			&& forwards.to == forwards.from) {
			error('Duplicate role name ' + forwards.name, forwards, structurePackage.abstractNamed_Name, DUPLICATE_NAME)
			error('Duplicate role name ' + backwards.name, backwards, structurePackage.abstractNamed_Name, DUPLICATE_NAME)
		}
	}
	
	@Check 
	def codeBlockNamesAreUnique(CodeBlock it) {
		// TODO more checks when feature scopes are implemented
		checkNamesAreLocallyUnique(variables)
	}

	private def checkNamesAreLocallyUnique(Iterable<? extends EObject> elements) {
		val name2element = HashMultimap.create
		for(element: elements) {
			val name = element.eGet(structurePackage.abstractNamed_Name) + element.parametersAsString
			name2element.put(name, element)
			val duplicates = name2element.get(name)
			switch duplicates.size {
				case 1: {
					// noop
				}
				case 2: 
					duplicates.forEach[
						error('Duplicate name ' + name, it, structurePackage.abstractNamed_Name, DUPLICATE_NAME)
					]
				default:
					error('''Duplicate «element.eClass.name» named '«name»'«»''', element, structurePackage.abstractNamed_Name, DUPLICATE_NAME)
			}
		}
	}
	
	private def checkNamesAreGloballyUnique(Iterable<? extends EObject> elements, EClass... eClasses) {  
		if(!elements.empty) {
			val resource = elements.head.eResource
			val uri = resource.URI
			val fileExtension = uri.fileExtension
			val index = elements.head.index
			for(element: elements) {
				val elementName = element.fullyQualifiedName
				val siblings = eClasses.map[
					index.getExportedObjects(it, elementName, false)
				].flatten.filter[
					((fileExtension == 'int' || fileExtension == 'prj') && uri == EObjectURI.trimFragment)
					|| (fileExtension != 'int' && fileExtension != 'prj' && EObjectURI.fileExtension != 'int' && EObjectURI.fileExtension != 'prj')
				]
				if(element instanceof AbstractActionDeclaration || element instanceof AbstractActionDefinition) {
					val signature = element.parametersAsString
					for(sibling: siblings) {
						val resolved = element.eResource.resourceSet.getEObject(sibling.EObjectURI, true)
						if(resolved != null && resolved != element) {
							if(resolved.parametersAsString == signature && (resolved.hasReturnType === element.hasReturnType))
								error('''Duplicate «eClasses.map[name].join('/')» named '«elementName.toString('::')»'«»''', 
								element, structurePackage.abstractNamed_Name, DUPLICATE_NAME)
						}
					}
				} else {
					val siblingIterator = siblings.iterator
					if(siblingIterator.hasNext) {
						siblingIterator.next
						if(siblingIterator.hasNext) {
							error('''Duplicate «eClasses.map[name].join('/')» named '«elementName.toString('::')»'«»''', 
								element, structurePackage.abstractNamed_Name, DUPLICATE_NAME)
						}
					}
				}
			}
		}		
	}
	
}