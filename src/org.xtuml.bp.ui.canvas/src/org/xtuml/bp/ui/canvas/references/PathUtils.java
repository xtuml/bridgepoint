package org.xtuml.bp.ui.canvas.references;

import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.ui.canvas.Referencepath_c;

public class PathUtils {

	public static NonRootModelElement getElementByPath(String represents, NonRootModelElement parent) {
		Referencepath_c path = ReferencePathManagement.getPath(represents, parent);
		if(path != null) {
			return (NonRootModelElement) path.getElement();
		}
		return null;
	}

	public static String getLastSegment(String represents) {
		String[] parts = represents.split("::");
		return parts[parts.length - 1];
	}

//	public static NonRootModelElement getSubtypeRepresentation(String represents, NonRootModelElement supertype,
//			NonRootModelElement parent) {
//		List<NonRootModelElement> subtypes = SupertypeSubtypeUtil.getSubtypes(supertype);
//		for (NonRootModelElement subtype : subtypes) {
//			if (ReferencePathManagement.getPath(subtype).equals(represents)) {
//				return subtype;
//			} else {
//				NonRootModelElement element = getSubtypeRepresentation(represents, subtype, parent);
//				if (element != null) {
//					return element;
//				}
//			}
//		}
//		return null;
//	}

//	public static String ReferencePathManagement.getPath(NonRootModelElement ele, NonRootModelElement parent) {
//		if (ele instanceof PackageableElement_c) {
//			List<NonRootModelElement> subtypes = SupertypeSubtypeUtil.getSubtypes(ele);
//			if (subtypes != null) {
//				return subtypes.get(0).ReferencePathManagement.getPath();
//			}
//		}
//		// Special case for unnamed elements
//		if (ele instanceof TimingMark_c || ele instanceof TimeSpan_c || ele.ReferencePathManagement.getPath().equals(parent.ReferencePathManagement.getPath())) {
//			String name = ele.getClass().getSimpleName().replaceAll("_c", "")
//					+ ele.Get_ooa_id().getLeastSignificantBits();
//			return name;
//		}
//		return ele.ReferencePathManagement.getPath();
//	}
}
