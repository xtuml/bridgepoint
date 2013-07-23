package com.mentor.nucleus.bp.core.ui.marker;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.mentor.nucleus.bp.core.util.UIUtil;

public class RemoveAllEvent extends MarkerEvent {

    private IFile file;

    private String markerID;

    public RemoveAllEvent(IFile file, String markerID) {
        this.file = file;
        this.markerID = markerID;
    }

    public void process() throws CoreException {
        if (file == null || !file.isAccessible())
            return;
        if (UmlProblem.hasXtUMLMarker(file, IResource.DEPTH_ZERO)) {
            internalProcess(file.getParent());
        }
    }

    public void run(IProgressMonitor monitor) throws CoreException {
    	if(!file.exists()) {
    		   return;
    	}
    	
    	if (markerID == null) {
            file.deleteMarkers(UmlProblem.xtUMLMarker, false,
                    IResource.DEPTH_ZERO);
        } else {
            IMarker[] markers = file.findMarkers(UmlProblem.xtUMLMarker, false,
                    IResource.DEPTH_ZERO);
            for (int i = 0; i < markers.length; i++) {
                IMarker marker = markers[i];
                String mID = (String) marker
                        .getAttribute(UmlProblem.ATTR_XT_ID);
                if (mID != null && mID.startsWith(markerID)) {
                    marker.delete();
                }
            }
        }
        UIUtil.refresh(file.getProject());
    }

}
