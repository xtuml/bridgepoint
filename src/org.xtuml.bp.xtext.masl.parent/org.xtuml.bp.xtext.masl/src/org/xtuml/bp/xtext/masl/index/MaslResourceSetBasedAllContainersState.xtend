package org.xtuml.bp.xtext.masl.index

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtext.resource.containers.FlatResourceSetBasedAllContainersState
import org.eclipse.xtext.resource.containers.ResourceSetBasedAllContainersStateProvider
import org.xtuml.bp.xtext.masl.lib.MASLLibraryProvider
import static org.xtuml.bp.xtext.masl.lib.MASLContainerManager.*

class MaslResourceSetBasedAllContainersState extends FlatResourceSetBasedAllContainersState {
	
	static class Provider extends ResourceSetBasedAllContainersStateProvider {
		override protected handleAdapterNotFound(ResourceSet resourceSet) {
		 	new MaslResourceSetBasedAllContainersState(resourceSet)
		}
 	}

	new(ResourceSet resourceSet) {
		super(resourceSet)
	}
	
	override getVisibleContainerHandles(String handle) {
		val newVisibleHandles = newArrayList
		newVisibleHandles += handle
		newVisibleHandles += BUILTIN_LIBRARY_CONTAINER_HANDLE
		return newVisibleHandles
	}
	
	override getContainerHandle(URI uri) {
		if(uri == MASLLibraryProvider.MODEL_URI) {
			return BUILTIN_LIBRARY_CONTAINER_HANDLE
		} else {
			val delegateHandle = super.getContainerHandle(uri)
			val pathSegments = if(uri.fileExtension != null)
					uri.trimFragment.trimSegments(1).segments
				else
					uri.trimFragment.segments 
			val index = pathSegments.indexOf(delegateHandle)
			if(index == -1 || index + 2 > pathSegments.size)
				return delegateHandle
			else
				return delegateHandle + CONTAINER_HANDLE_SEPARATOR + pathSegments.get(index + 1)
		}
	}
	
	override getContainedURIs(String containerHandle) {
		if(containerHandle == BUILTIN_LIBRARY_CONTAINER_HANDLE) {
			#[MASLLibraryProvider.MODEL_URI]		
		} else {
			val segments = containerHandle.split(CONTAINER_HANDLE_SEPARATOR)
			val delegateHandle = segments.head
			val delegateContainedURIs = super.getContainedURIs(delegateHandle)
			val containedURIs = newArrayList
			containedURIs += delegateContainedURIs.filter[it.containerHandle == containerHandle].toSet
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