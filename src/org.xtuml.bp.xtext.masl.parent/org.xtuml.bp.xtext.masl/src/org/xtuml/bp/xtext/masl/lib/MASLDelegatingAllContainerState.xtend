package org.xtuml.bp.xtext.masl.lib

import com.google.inject.Inject
import com.google.inject.name.Named
import org.eclipse.emf.common.util.URI
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.resource.IResourceDescriptions
import org.eclipse.xtext.resource.containers.IAllContainersState

/**
 * Adds an implicit dependency to the MASL library to all projects containing MASL models.
 * 
 * @see MASLContainerManager
 * @see MASLLibraryProvider
 */
@FinalFieldsConstructor
class MASLDelegatingAllContainerState implements IAllContainersState {
	
	public static val DELEGATE_BINDING = 'delegateBinding'

	public static val BUILTIN_LIBRARY_CONTAINER_HANDLE = 'MASL_BuiltinLibrary'
	
	static class Provider implements IAllContainersState.Provider {
		
		@Inject@Named(DELEGATE_BINDING) IAllContainersState.Provider delegateProvider
		
		override get(IResourceDescriptions context) {
			new MASLDelegatingAllContainerState(delegateProvider.get(context))
		}
	}
	
	val IAllContainersState delegate
	
	static val SEPARATOR = '/'

	override getVisibleContainerHandles(String handle) {
		val segments = handle.split(SEPARATOR)
		val delegateHandle = segments.head
		val rootFolder = segments.last
		val visibleHandles = newHashSet
		visibleHandles += delegate.getVisibleContainerHandles(delegateHandle)
		visibleHandles.remove(delegateHandle)
		visibleHandles.remove(BUILTIN_LIBRARY_CONTAINER_HANDLE)
		val newVisibleHandles = newArrayList
		newVisibleHandles += handle
		newVisibleHandles += BUILTIN_LIBRARY_CONTAINER_HANDLE
		newVisibleHandles += visibleHandles.map[it + SEPARATOR + rootFolder]
		return newVisibleHandles
	}
	
	override getContainerHandle(URI uri) {
		if(uri == MASLLibraryProvider.MODEL_URI) {
			return BUILTIN_LIBRARY_CONTAINER_HANDLE
		} else {
			val delegateHandle = delegate.getContainerHandle(uri)
			val pathSegments = if(uri.fileExtension != null)
					uri.trimFragment.trimSegments(1).segments
				else
					uri.trimFragment.segments 
			val index = pathSegments.indexOf(delegateHandle)
			if(index == -1 || index + 1 > pathSegments.size)
				return delegateHandle
			else
				return delegateHandle + SEPARATOR + pathSegments.get(index + 1)
		}
	}
	
	override getContainedURIs(String containerHandle) {
		if(containerHandle == BUILTIN_LIBRARY_CONTAINER_HANDLE) {
			#[MASLLibraryProvider.MODEL_URI]		
		} else {
			val segments = containerHandle.split(SEPARATOR)
			val delegateHandle = segments.head
			val delegateContainedURIs = delegate.getContainedURIs(delegateHandle)
			val containedURIs = newArrayList
			containedURIs += delegateContainedURIs.filter[it.containerHandle == containerHandle]
			return containedURIs
		} 
	}
	
	override isEmpty(String containerHandle) {
		if(containerHandle == BUILTIN_LIBRARY_CONTAINER_HANDLE)
			false
		else
			containerHandle.getContainedURIs.isEmpty
	}
}