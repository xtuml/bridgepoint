package org.xtuml.bp.xtext.masl.lib

import com.google.inject.Inject
import com.google.inject.name.Named
import java.util.ArrayList
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
	
	override getVisibleContainerHandles(String handle) {
		val delegateHandle = handle.substring(0, handle.length - 2)
		val visibleHandles = newHashSet
		visibleHandles += delegate.getVisibleContainerHandles(delegateHandle)
		visibleHandles.remove(delegateHandle)
		visibleHandles.remove(BUILTIN_LIBRARY_CONTAINER_HANDLE)
		val newVisibleHandles = newArrayList
		newVisibleHandles += handle
		newVisibleHandles += BUILTIN_LIBRARY_CONTAINER_HANDLE
		newVisibleHandles += visibleHandles.map[it + SHARED_HANDLE_SUFFIX]
		return newVisibleHandles
	}
	
	static val SHARED_HANDLE_SUFFIX = '/s'
	static val EXPORTED_HANDLE_SUFFIX = '/e'
	static val IMPORTED_HANDLE_SUFFIX = '/i'
	
	static val SHARED_FILE_EXTENSIONS = #{
		'mod', 'int'
	}
	
	static val EXPORTED_FILE_EXTENSIONS = #{
		'svc', 'al', 'mod', 'int', 'tr', 'scn', 'ext', 'prj', 'fn'
	}
	
	static val IMPORTED_FILE_EXTENSIONS = #{
		'mod', 'int', 'masl'
	}
	
	override getContainerHandle(URI uri) {
		if(uri == MASLLibraryProvider.MODEL_URI) {
			return BUILTIN_LIBRARY_CONTAINER_HANDLE
		} else {
			val fileExtension = uri.fileExtension
			val suffix = if(SHARED_FILE_EXTENSIONS.contains(fileExtension)) 
				SHARED_HANDLE_SUFFIX
			else if(EXPORTED_FILE_EXTENSIONS.contains(fileExtension))
				EXPORTED_HANDLE_SUFFIX
			else if(IMPORTED_FILE_EXTENSIONS.contains(fileExtension))
				IMPORTED_HANDLE_SUFFIX
			else 
				SHARED_HANDLE_SUFFIX 
			return delegate.getContainerHandle(uri) + suffix
		}
	}
	
	override getContainedURIs(String containerHandle) {
		if(containerHandle == BUILTIN_LIBRARY_CONTAINER_HANDLE) {
			#[MASLLibraryProvider.MODEL_URI]		
		} else {
			val delegateHandle = containerHandle.substring(0, containerHandle.length - 2)
			val suffix = containerHandle.substring(containerHandle.length-2)
			val containedURIs = delegate.getContainedURIs(delegateHandle)
			switch suffix {
				case EXPORTED_HANDLE_SUFFIX: {
					val toList = containedURIs
										 	.filter[EXPORTED_FILE_EXTENSIONS.contains(fileExtension)]
										 	.toList
					 return toList
				}
				case IMPORTED_HANDLE_SUFFIX:
					 return containedURIs
					 	.filter[IMPORTED_FILE_EXTENSIONS.contains(fileExtension)]
					 	.toList
			}
			return containedURIs
		} 
	}
	
	override isEmpty(String containerHandle) {
		if(containerHandle == BUILTIN_LIBRARY_CONTAINER_HANDLE)
			false
		else
			delegate.isEmpty(containerHandle.substring(0, containerHandle.length - 2))
	}
}