package org.xtuml.bp.xtext.masl.lib

import org.eclipse.xtext.resource.containers.StateBasedContainerManager
import org.eclipse.xtext.resource.IResourceDescriptions
import org.eclipse.xtext.resource.impl.AbstractContainer
import com.google.inject.Inject
import org.xtuml.bp.xtext.masl.dependency.MaslDependencyProvider

/**
 * Provides a container (<=> archive) containing the MASL library 
 * to all MASL projects.
 * 
 * @see MASLLibraryProvider 
 */
class MASLContainerManager extends StateBasedContainerManager {

	public static val BUILTIN_LIBRARY_CONTAINER_HANDLE = 'MASL_BuiltinLibrary'

	public static val CONTAINER_HANDLE_SEPARATOR = '/'

	@Inject MASLLibraryProvider libraryProvider
    @Inject MaslDependencyProvider dependencyProvider

	override protected createContainer(String handle, IResourceDescriptions resourceDescriptions) {
		if (handle == BUILTIN_LIBRARY_CONTAINER_HANDLE)
			new AbstractContainer() {
				override getResourceDescriptions() {
					libraryProvider.resourceDescriptions
				}
			}
        else if ( dependencyProvider.dependencyHandles.contains( handle ) )
            new AbstractContainer() {
                override getResourceDescriptions() {
                    dependencyProvider.getResourceDescriptions( handle )
                }
            }
		else
			super.createContainer(handle, resourceDescriptions)
	}

}
