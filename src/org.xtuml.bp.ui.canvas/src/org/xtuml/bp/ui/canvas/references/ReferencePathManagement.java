package org.xtuml.bp.ui.canvas.references;

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
			ref.setElement_id(element.Get_ooa_id());
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

	public static Referencepath_c getPath(String represents) {
		return Referencepath_c.ReferencepathInstance(Ooaofgraphics.getDefaultInstance(),
				rp -> ((Referencepath_c) rp).getPath().equals(represents));
	}
}
