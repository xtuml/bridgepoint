package org.xtuml.bp.xtext.masl.tests.lib

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IContainer
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.junit.Test
import org.junit.runner.RunWith
import org.xtuml.bp.xtext.masl.lib.MASLLibraryProvider
import org.xtuml.bp.xtext.masl.masl.structure.DomainDefinition
import org.xtuml.bp.xtext.masl.masl.structure.MaslModel
import org.xtuml.bp.xtext.masl.masl.types.BuiltinTypeDeclaration
import org.xtuml.bp.xtext.masl.masl.types.NamedTypeReference
import org.xtuml.bp.xtext.masl.tests.MASLInjectorProvider

import static org.junit.Assert.*
import org.xtuml.bp.xtext.masl.masl.types.TypesPackage

@RunWith(XtextRunner)
@InjectWith(MASLInjectorProvider)
class LibraryTest {
	
	@Inject Provider<ResourceSet> resourceSetProvider
	@Inject IContainer.Manager containerManager
	@Inject IResourceDescription.Manager resourceDescriptionManager
	@Inject ResourceDescriptionsProvider resourceDescriptionsProvider
	
	@Inject extension ParseHelper<MaslModel> 
	@Inject extension TypesPackage
	
	@Inject MASLLibraryProvider libProvider
	
	@Test
	def void testTypeMap() {
		assertNotNull(libProvider.getBuiltinTypeURI('integer'))
		assertNotNull(libProvider.getBuiltinTypeURI('any_type'))
	}
	
	@Test
	def void testResourceDecriptions() {
		assertNotNull(libProvider.resourceDescriptions.map[getExportedObjects(builtinTypeDeclaration, QualifiedName.create('integer'), false)].flatten.head)
		assertNull(libProvider.resourceDescriptions.map[getExportedObjects(builtinTypeDeclaration, QualifiedName.create('any_type'), false)].flatten.head)
	}
	
	@Test
	def void testLibraryLoadableAndConsistent() {
		val rs = resourceSetProvider.get
		val resource = rs.getResource(MASLLibraryProvider.MODEL_URI, true)
		assertTrue(resource.loaded)
		assertFalse(resource.contents.empty)
		assertTrue(resource.errors.map[message].join('\n'), resource.errors.empty)
		assertTrue(resource.warnings.empty)
	}	
	
	@Test
	def void testLibraryIndexedAndLinkable() {
		val model = '''
			domain myDomain is 
				type x is integer;
			emd
		'''.parse
		val resource = model.eResource
		val rds = resourceDescriptionsProvider.getResourceDescriptions(resource.resourceSet)
		val rd = resourceDescriptionManager.getResourceDescription(resource)
		val containers = containerManager.getVisibleContainers(rd, rds)
		assertEquals(2, containers.size)
		val primitive = containers
			.map[getExportedObjects(builtinTypeDeclaration, QualifiedName.create('integer'), false)]
			.flatten
			.head
		assertNotNull(primitive)
		val integer = ((model.elements.head as DomainDefinition).types.head.definition as NamedTypeReference).type
		assertTrue(integer instanceof BuiltinTypeDeclaration)
		assertEquals('integer', integer.name)
		assertEquals(MASLLibraryProvider.MODEL_URI, integer.eResource.URI)
	}
}