package org.xtuml.bp.xtext.masl.ui.tests.container

import com.google.inject.Inject
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.resource.containers.IAllContainersState
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.ui.XtextProjectHelper
import org.eclipse.xtext.ui.resource.IStorage2UriMapper
import org.eclipse.xtext.ui.resource.XtextResourceSetProvider
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.xtuml.bp.xtext.masl.ui.tests.MASLUiInjectorProvider

import static org.eclipse.xtext.junit4.ui.util.IResourcesSetupUtil.*
import static org.junit.Assert.*
import static org.xtuml.bp.xtext.masl.lib.MASLContainerManager.*

@RunWith(XtextRunner)
@InjectWith(MASLUiInjectorProvider)
class MaslAllContainerStatesTest {
	
	@Inject IAllContainersState.Provider containerStateProvider
	@Inject ResourceDescriptionsProvider resourceDescriptionsProvider	
	@Inject XtextResourceSetProvider resourceSetProvider
	@Inject IStorage2UriMapper storage2UriMapper
	
	@After
	def void cleanUp() {
		cleanWorkspace
	}
	
	@Test
	def void testContainer() {
		val p = createProject('test')
		addNature(p, XtextProjectHelper.NATURE_ID)
		addBuilder(p, XtextProjectHelper.BUILDER_ID)
		val foo0 = createFileURI('test/masl/foo.masl')
		val bar0 = createFileURI('test/masl/bar.masl')
		val foo1 = createFileURI('test/models/foo.masl')
		val bar1 = createFileURI('test/models/bar.masl')
		createFolder('test/empty')
		fullBuild
		
		val rs = resourceSetProvider.get(p)
		val index = resourceDescriptionsProvider.getResourceDescriptions(rs)
		val containerState = containerStateProvider.get(index)
		
		assertEquals('test/masl', containerState.getContainerHandle(foo0))
		assertEquals('test/models', containerState.getContainerHandle(foo1))
		
		assertEquals(#{foo0, bar0}, containerState.getContainedURIs('test/masl').toSet)
		assertEquals(#{foo1, bar1}, containerState.getContainedURIs('test/models').toSet)
		
		assertFalse(containerState.isEmpty('test/masl'))
		assertFalse(containerState.isEmpty('test/models'))
		assertTrue(containerState.isEmpty('test/empty'))
		assertTrue(containerState.isEmpty('test/unknown'))
		
		assertEquals(#{'test/masl', BUILTIN_LIBRARY_CONTAINER_HANDLE}, containerState.getVisibleContainerHandles('test/masl').toSet)
		assertEquals(#{'test/models', BUILTIN_LIBRARY_CONTAINER_HANDLE}, containerState.getVisibleContainerHandles('test/models').toSet)
		assertEquals(#{'test/empty', BUILTIN_LIBRARY_CONTAINER_HANDLE}, containerState.getVisibleContainerHandles('test/empty').toSet)
	} 
	
	@Test
	def void testContainerMultiProject() {
		val p0 = createProject('test')
		addNature(p0, XtextProjectHelper.NATURE_ID)
		addBuilder(p0, XtextProjectHelper.BUILDER_ID)
		val p1 = createProject('test1')
		addNature(p1, XtextProjectHelper.NATURE_ID)
		addBuilder(p1, XtextProjectHelper.BUILDER_ID)
		val p1desc = p1.description
		p1desc.referencedProjects = #[p0]
		p1.setDescription(p1desc, new NullProgressMonitor)
		fullBuild
		
		val rs = resourceSetProvider.get(p1)
		val index = resourceDescriptionsProvider.getResourceDescriptions(rs)
		val containerState = containerStateProvider.get(index)
		
		assertEquals(#{'test/masl', BUILTIN_LIBRARY_CONTAINER_HANDLE}, containerState.getVisibleContainerHandles('test/masl').toSet)
		assertEquals(#{'test1/masl', BUILTIN_LIBRARY_CONTAINER_HANDLE}, containerState.getVisibleContainerHandles('test1/masl').toSet)
		assertEquals(#{'test1/models', BUILTIN_LIBRARY_CONTAINER_HANDLE}, containerState.getVisibleContainerHandles('test1/models').toSet)
	} 
	
	private def createFileURI(String workspaceRelativePath) {
		val file = createFile(workspaceRelativePath, '')
		storage2UriMapper.getUri(file)
	} 
}