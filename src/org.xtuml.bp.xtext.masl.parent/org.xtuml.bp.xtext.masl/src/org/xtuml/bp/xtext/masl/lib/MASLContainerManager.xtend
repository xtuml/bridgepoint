package org.xtuml.bp.xtext.masl.lib

import org.eclipse.xtext.resource.containers.StateBasedContainerManager
import org.eclipse.xtext.resource.IResourceDescriptions
import org.eclipse.xtext.resource.impl.AbstractContainer
import com.google.inject.Inject

/**
 * Provides a container (<=> archive) containing the MASL library 
 * to all MASL projects.
 * 
 * @see MASLLibraryProvider 
 */
class MASLContainerManager extends StateBasedContainerManager {
	
	@Inject MASLLibraryProvider libraryProvider
	
	override protected createContainer(String handle, IResourceDescriptions resourceDescriptions) {
		if(handle==MASLDelegatingAllContainerState.BUILTIN_LIBRARY_CONTAINER_HANDLE)
			new AbstractContainer() {
				override getResourceDescriptions() {
					libraryProvider.resourceDescriptions
				}
			}
		else
			super.createContainer(handle, resourceDescriptions)
	}
	
}
