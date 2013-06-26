package com.mentor.nucleus.bp.debug.ui.locator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.debug.core.sourcelookup.containers.FolderSourceContainer;

public class BPSourceContainer extends FolderSourceContainer {

	public BPSourceContainer(IFolder folder) {
		super(folder, false);
	}

	public String getName() {
		return "models";
	}

}
