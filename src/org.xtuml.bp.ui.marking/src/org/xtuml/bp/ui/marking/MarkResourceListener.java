package org.xtuml.bp.ui.marking;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.XtUMLNature;
import org.xtuml.bp.core.common.ComponentResourceListener;

/**
 * Keep marking cache in sync with file operations.
 */
public class MarkResourceListener implements IResourceChangeListener {

	@Override
	public void resourceChanged(IResourceChangeEvent event) {

		if (event == null || event.getDelta() == null) {
			return;
		}

		// do not want to do anything in response to normal brigepoint ops
		if (!ComponentResourceListener.ignoreResourceChanges()
				&& !ComponentResourceListener.isIgnoreResourceChangesMarkerSet()) {
			IResourceDelta rootDelta = event.getDelta();
			IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
				public boolean visit(IResourceDelta delta) {
					IResource resource = delta.getResource();
					if (((resource.getType() & IResource.PROJECT) != 0) && resource.getProject().isOpen()
							&& delta.getKind() == IResourceDelta.CHANGED
							&& ((delta.getFlags() & IResourceDelta.OPEN) != 0)) {
						IProject project = resource.getProject();
						if (XtUMLNature.hasNature(project)) {
							MarkingDataManager.initialize(List.of(project).toArray(new IProject[0]));
						}
					}
					if (((resource.getType() & IResource.PROJECT) != 0) && !resource.getProject().isOpen()
							&& delta.getKind() == IResourceDelta.CHANGED) {
						MarkingDataManager.removeMarkingData(List.of(resource.getProject()));
					}
					if (((resource.getType() & IResource.PROJECT) != 0) && delta.getKind() == IResourceDelta.REMOVED) {
						MarkingDataManager.removeMarkingData(List.of(resource.getProject()));
					}
					return true;
				}
			};
			try {
				rootDelta.accept(visitor);
			} catch (CoreException e) {
				CorePlugin.logError("Unable to handle project open/close", e);
			}
		}
	}

}
