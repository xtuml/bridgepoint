package com.mentor.nucleus.bp.debug.ui.locator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant;

import com.mentor.nucleus.bp.debug.ui.model.BPStackFrame;

public class BPSourceLookupDirector extends AbstractSourceLookupDirector {

	public BPSourceLookupDirector() {
		super();
	}

	public void initializeParticipants() {
		addParticipants(new ISourceLookupParticipant[]{new BPSourceLookupParticipant()});
	}

}
