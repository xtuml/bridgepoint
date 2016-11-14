package org.xtuml.bp.xtext.masl.scoping

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.resource.IContainer
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.ISelectable
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider

class ProjectScopeIndexProvider {

	@Inject extension ResourceDescriptionsProvider
	@Inject extension IResourceDescription.Manager 
	@Inject IContainer.Manager containerManager

	def ISelectable getIndex(Resource resource) {
		val resourceIndex = getResourceDescription(resource)
		val workspaceIndex = getResourceDescriptions(resource)
		containerManager.getContainer(resourceIndex, workspaceIndex)
	}
	
	def ISelectable getIndex(EObject element) {
		getIndex(element.eResource)
	}
}