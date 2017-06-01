package org.xtuml.bp.xtext.masl.ui.index

import java.util.HashSet
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.ui.containers.WorkspaceProjectsState
import org.xtuml.bp.xtext.masl.lib.MASLLibraryProvider

import static org.xtuml.bp.xtext.masl.lib.MASLContainerManager.*
import org.eclipse.xtext.resource.containers.IAllContainersState
import org.eclipse.xtext.resource.IResourceDescriptions
import com.google.inject.Inject

class MaslWorkspaceProjectsState extends WorkspaceProjectsState {
	
	static class Provider implements IAllContainersState.Provider {
		@Inject MaslWorkspaceProjectsState state
		
		override get(IResourceDescriptions context) {
			state	
		}
	} 
	
	override protected doInitContainedURIs(String containerHandle) {
		if(containerHandle == BUILTIN_LIBRARY_CONTAINER_HANDLE) {
			#[MASLLibraryProvider.MODEL_URI]		
		} else {
			val segments = containerHandle.split(CONTAINER_HANDLE_SEPARATOR)
			val projectHandle = segments.head
			val projectContainedURIs = super.doInitContainedURIs(projectHandle)
			val containedURIs = newArrayList
			containedURIs += projectContainedURIs.filter[it.containerHandle == containerHandle]
			return new HashSet(containedURIs)
		} 
	}

	override protected doInitHandle(URI uri) {
		if(uri == MASLLibraryProvider.MODEL_URI) {
			return BUILTIN_LIBRARY_CONTAINER_HANDLE
		} else {
			val projectHandle = super.doInitHandle(uri)
			val pathSegments = if(uri.fileExtension != null)
					uri.trimFragment.trimSegments(1).segments
				else
					uri.trimFragment.segments 
			val index = pathSegments.indexOf(projectHandle)
			if(index == -1 || index + 2 > pathSegments.size)
				return projectHandle
			else
				return projectHandle + CONTAINER_HANDLE_SEPARATOR + pathSegments.get(index + 1)
		}
	}
	
	override protected doInitVisibleHandles(String handle) {
		if(handle == BUILTIN_LIBRARY_CONTAINER_HANDLE)
			return #[handle]
		else 
			return #[handle, BUILTIN_LIBRARY_CONTAINER_HANDLE]
	}
	
}