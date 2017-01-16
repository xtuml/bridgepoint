package org.xtuml.bp.xtext.masl.scoping

import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionManager
import org.eclipse.xtext.resource.IResourceDescription.Delta
import org.eclipse.xtext.resource.IResourceDescription
import java.util.Collection
import org.eclipse.xtext.resource.IResourceDescriptions

class MaslResourceDescriptionManager extends DefaultResourceDescriptionManager implements IResourceDescription.Manager.AllChangeAware {
	
	override protected hasChanges(Delta delta, IResourceDescription candidate) {
		// Rebuild dependent resources on all changes in the resource.
		// This is necessary, as we're linking against elements which are not 
		// indexed, e.g. action signatures.  
		super.hasChanges(delta, candidate)
	}
	
	override isAffectedByAny(Collection<Delta> deltas, IResourceDescription candidate, IResourceDescriptions context) throws IllegalArgumentException {
		if(candidate.URI.fileExtension == 'mod')
			return true
		isAffected(deltas, candidate, context)
	}
	
}