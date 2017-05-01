package org.xtuml.bp.xtext.masl.layout

import com.google.inject.Inject
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.QualifiedName
import org.xtuml.bp.xtext.masl.MASLExtensions
import org.xtuml.bp.xtext.masl.masl.structure.DomainDefinition
import org.xtuml.bp.xtext.masl.masl.structure.MaslModel
import org.xtuml.bp.xtext.masl.masl.structure.ObjectDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectDefinition
import org.xtuml.bp.xtext.masl.masl.structure.StateDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.StructurePackage
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorDefinition
import org.xtuml.bp.xtext.masl.scoping.ProjectScopeIndexProvider
import org.xtuml.bp.xtext.masl.masl.structure.DomainServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectServiceDeclaration

class URI2DeclarationMapper {
	
	@Inject extension MASLExtensions
	@Inject extension ProjectScopeIndexProvider
	@Inject extension StructurePackage
	
	def boolean isStateImplementation(MaslModel model) {
		model.eResource.URI.lastSegment == 'InstanceStateMachine.masl'
	}
	
	def EObject getContainerDefinition(MaslModel implementationModel) {
		val uri = implementationModel.eResource.URI
		if(uri.fileExtension != 'masl' || uri.segmentCount < 4) 
			return null
		val index = implementationModel.index
		val domainName = uri.segments.get(uri.segmentCount - 4) 
		val parentFolderName = uri.segments.get(uri.segmentCount - 3)
		val folderName = uri.segments.get(uri.segmentCount - 2)
		val fileName = uri.lastSegment
		val description = switch fileName {
			case 'functions.masl':
					index.getExportedObjects(domainDefinition, 
						QualifiedName.create(domainName), false)
			case 'InstanceStateMachine.masl':
					index.getExportedObjects(objectDefinition, 
						QualifiedName.create(domainName, parentFolderName), false)
			default:
				if(uri.segment(uri.segmentCount - 3) == 'Shared') {
					index.getExportedObjects(terminatorDefinition,
						QualifiedName.create(domainName, folderName.substring(domainName.length)), false)
				} else {
					index.getExportedObjects(objectDefinition,
						QualifiedName.create(domainName, parentFolderName), false)
				}
		}.filter[EObjectURI.fileExtension == 'mod'].head
		if(description != null) 
			return implementationModel.eResource.resourceSet.getEObject(description.EObjectURI, true)
		else 
			return null
	}
	
	def URI getImplementationURI(EObject declaration) {
		val declarationResourceURI = declaration.eResource.URI
		if(declarationResourceURI.fileExtension != 'mod'  
			|| declarationResourceURI.segment(declarationResourceURI.segmentCount - 3) != 'masl')
			return null
		val domainBaseURI = declarationResourceURI
			.trimSegments(3)
			.appendSegment('models')
			.appendSegment(declaration.domainName)
			.appendSegment(declaration.domainName)
		switch declaration {
			DomainDefinition,
			DomainServiceDeclaration:
				return domainBaseURI
					.appendSegment(declaration.domainDefinition.name)
					.appendSegment('functions')
					.appendSegment('functions.masl')
			ObjectDeclaration,
			ObjectServiceDeclaration: 
				return domainBaseURI
					.appendSegment(declaration.domainName)
					.appendSegment(declaration.domainName)
					.appendSegment(declaration.objectDefinition.name)
					.appendSegment(declaration.objectDefinition.name + '.masl')
			StateDeclaration:
				return domainBaseURI
					.appendSegment(declaration.domainName)
					.appendSegment(declaration.domainName)
					.appendSegment(declaration.objectDefinition.name)
					.appendSegment('InstanceStateMachine')
					.appendSegment('InstanceStateMachine.masl')
			TerminatorDefinition,
			TerminatorServiceDeclaration: {
				val terminator = declaration.terminatorDefinition				
				return domainBaseURI
					.appendSegment('Shared')
					.appendSegment(terminator.domainName + terminator.name)
					.appendSegment(terminator.domainName + terminator.name + '.masl')
			}
		}
	}
	
	private def ObjectDefinition getObjectDefinition(EObject declaration) {
		switch declaration {
			ObjectDefinition:
				declaration
			ObjectServiceDeclaration,
			StateDeclaration:
				declaration.eContainer as ObjectDefinition
		}
	}
	
	private def DomainDefinition getDomainDefinition(EObject declaration) {
		switch declaration {
			DomainDefinition: declaration
			DomainServiceDeclaration:
				declaration.eContainer as DomainDefinition
		}	
	}
	
	private def TerminatorDefinition getTerminatorDefinition(EObject declaration) {
		switch declaration {
			TerminatorDefinition:
				declaration
			TerminatorServiceDeclaration:
				declaration.eContainer as TerminatorDefinition
		}
	}
}