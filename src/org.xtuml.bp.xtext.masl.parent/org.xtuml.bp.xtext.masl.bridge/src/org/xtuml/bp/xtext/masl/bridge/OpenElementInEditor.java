package org.xtuml.bp.xtext.masl.bridge;

import java.util.Iterator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.ui.shared.Access;

public class OpenElementInEditor {

	public void openElement(EClass elementType, String name) {
		// tokenize name
		QualifiedName qualifiedName = QualifiedName.create(name.split("\\.|::"));
		// get the index 
		IResourceDescriptions index = Access.getIResourceDescriptions().get();
		// lookup element in index
		Iterator<IEObjectDescription> indexEntries = index.getExportedObjects(elementType, qualifiedName, false).iterator();
		if(indexEntries.hasNext()) {
			IEObjectDescription indexEntry = indexEntries.next();
			// open editor and reveal element
			Access.getIURIEditorOpener().get().open(indexEntry.getEObjectURI(), true);
		}
	}
}
