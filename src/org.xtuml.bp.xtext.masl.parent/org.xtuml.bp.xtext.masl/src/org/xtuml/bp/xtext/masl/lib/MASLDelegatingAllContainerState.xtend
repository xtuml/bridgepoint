package org.xtuml.bp.xtext.masl.lib

import com.google.inject.Inject
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.resource.containers.IAllContainersState
import com.google.inject.name.Named
import org.eclipse.xtext.resource.IResourceDescriptions
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import java.util.ArrayList

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
	
	override getVisibleContainerHandles(String handle) {
		val visibleHandles = delegate.getVisibleContainerHandles(handle) 
		if(!visibleHandles.contains(BUILTIN_LIBRARY_CONTAINER_HANDLE)) {
			val newVisibleHandles = new ArrayList(visibleHandles)
			newVisibleHandles += BUILTIN_LIBRARY_CONTAINER_HANDLE
			return newVisibleHandles
		}
		return visibleHandles
	}
	
	override getContainerHandle(URI uri) {
		if(uri == BUILTIN_LIBRARY_CONTAINER_HANDLE)
			BUILTIN_LIBRARY_CONTAINER_HANDLE
		else
			delegate.getContainerHandle(uri)
	}
	
	override getContainedURIs(String containerHandle) {
		if(containerHandle == BUILTIN_LIBRARY_CONTAINER_HANDLE) 
			#[MASLLibraryProvider.MODEL_URI]		
		else 
			delegate.getContainedURIs(containerHandle)
	}
	
	override isEmpty(String containerHandle) {
		if(containerHandle == BUILTIN_LIBRARY_CONTAINER_HANDLE)
			false
		else
			delegate.isEmpty(containerHandle)
	}
}