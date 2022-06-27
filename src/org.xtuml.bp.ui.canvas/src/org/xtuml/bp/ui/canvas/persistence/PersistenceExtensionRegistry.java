package org.xtuml.bp.ui.canvas.persistence;

import java.util.ArrayList;
import java.util.List;

public class PersistenceExtensionRegistry {
	List<PersistenceExtension> registeredExtensions = new ArrayList<>();
	
	public List<PersistenceExtension> getExtensions() {
		return registeredExtensions;
	}
	
	public boolean isHandledResourceExtension(String resourceExtension) {
		return registeredExtensions.stream().anyMatch(pe -> pe.getResourceExtension().equals(resourceExtension));
	}
	
	public void addPersistenceExtension(PersistenceExtension extension) {
		registeredExtensions.add(extension);
	}
}
