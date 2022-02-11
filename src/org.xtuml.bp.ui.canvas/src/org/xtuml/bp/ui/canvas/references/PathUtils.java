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

}
