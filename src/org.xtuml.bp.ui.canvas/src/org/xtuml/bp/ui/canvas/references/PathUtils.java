package org.xtuml.bp.ui.canvas.references;

import java.io.File;
import java.util.StringJoiner;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.xtuml.bp.core.Ooaofooa;
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
	
	public static String getFilePath(String modelPath) {
		String[] parts = modelPath.split("::");
		StringJoiner joiner = new StringJoiner(File.separator);
		joiner.add(parts[0]).add(Ooaofooa.MODELS_DIRNAME);
		for(String part : parts) {
			joiner.add(part);
		}
		joiner.add(parts[parts.length - 1]);
		return joiner.toString();
	}
	
	public static IFile getFile(String modelPath, String fileExtension) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(getFilePath(modelPath)).addFileExtension(fileExtension));
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
