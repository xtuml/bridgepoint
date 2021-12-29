package org.xtuml.bp.ui.canvas.references;

import java.util.Collection;

import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.ui.canvas.Objectreference_c;
import org.xtuml.bp.ui.canvas.Objectreferenceidentifyingattribute_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.Referencepath_c;

public class ReferencePathManagement {

	public static Referencepath_c createOrGetReferencePath(NonRootModelElement represents) {
		Referencepath_c referencePath = Referencepath_c.ReferencepathInstance(Ooaofgraphics.getDefaultInstance(),
				rp -> ((Referencepath_c) rp).getPath().equals(represents.getPath()));
		if(referencePath == null) {
			referencePath = new Referencepath_c(Ooaofgraphics.getDefaultInstance());
			referencePath.setElement(represents);
		} else {
			return referencePath;
		}
		referencePath.Initialize();
		String[] parts = represents.getPath().split("::");
		int count = parts.length;
		Objectreference_c last = null;
		for (String part : parts) {
			NonRootModelElement element = represents;
			for (int i = 1; i < count; i++) {
				element = PersistenceManager.getHierarchyMetaData().getParent(element);
			}
			Objectreference_c ref = new Objectreference_c(Ooaofgraphics.getDefaultInstance());
			if(last != null) {
				ref.relateAcrossR503ToPrecedes(last);
			}
			ref.setElement(element);
			Objectreferenceidentifyingattribute_c oria = new Objectreferenceidentifyingattribute_c(
					Ooaofgraphics.getDefaultInstance());
			oria.setValue(part);
			oria.relateAcrossR501To(ref);
			ref.relateAcrossR500To(referencePath);
			last = ref;
			count--;
		}
		return referencePath;
	}

	public static void deleteReferencePath(String path) {
		Referencepath_c referencePath = Referencepath_c.ReferencepathInstance(Ooaofgraphics.getDefaultInstance(),
				rp -> ((Referencepath_c) rp).getPath().equals(path));
		if (referencePath != null) {
			referencePath.Dispose();
		}
	}

	public static String getPath(NonRootModelElement nrme) {
		Referencepath_c referencePath = createOrGetReferencePath(nrme);
		return referencePath.getPath();
	}

	public static Referencepath_c getPath(String represents, NonRootModelElement parent) {
		// find child by path given parent
		return createOrGetReferencePath(getElement(represents, parent));
	}
	
	public static NonRootModelElement getElement(String represents, NonRootModelElement parent) {
		Collection<?> children = PersistenceManager.getHierarchyMetaData().getChildren(parent, false);
		for(Object child : children) {
			if(child instanceof NonRootModelElement) {
				NonRootModelElement nrme = (NonRootModelElement) child;
				String id = nrme.getPath();
				if(id.equals(represents)) {
					return nrme;
				} else {
					NonRootModelElement element = getElement(represents, nrme);
					if(element != null) {
						return element;
					}
				}
			}
		}
		if(parent.getPath().equals(represents)) {
			// looking at a container, need to return self
			return parent;
		}
		return null;
	}
}
