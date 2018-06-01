package org.xtuml.bp.core.common;

public interface IReferentialAttributeManager {
	
	// Create a new UUID for every identifying attribute of type UUID
	// that is not a referential attribute
	public void generateUUIDs( NonRootModelElement element );
	
	// Propagate the value of every identifying attribute that is referred
	// to by another class
	public void propagateIDs( NonRootModelElement element );
	
	// Replace every referential attribute with the type-specific NULL value
	public void clearReferentialAttributes( NonRootModelElement element );

}
