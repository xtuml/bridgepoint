package com.mentor.nucleus.bp.core.ui.marker;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.util.UIUtil;

public class ProxyResolvedEvent extends MarkerEvent {

    private NonRootModelElement rto;

    public ProxyResolvedEvent(NonRootModelElement rto) {
        this.rto = rto;
    }

    public void process() throws CoreException {

        if (UmlProblem.hasXtUMLMarkerAsRTO(rto.getFile(),rto.getCompUniqueID())) {
            internalProcess(rto.getFile().getProject());
        }
    }

    public void run(IProgressMonitor monitor) throws CoreException {
        IFile file = rto.getFile();
        if (file == null) {
            return;
        }
        String markerID = rto.getCompUniqueID();
        IProject proj = file.getProject();
        if (proj != null) {
            IMarker[] markers;
            markers = proj.findMarkers(UmlProblem.xtUMLMarker, true,
                    IResource.DEPTH_INFINITE);
            for (int i = 0; i < markers.length; i++) {
                IMarker marker = markers[i];
                Object rtoPath = marker.getAttribute(UmlProblem.ATTR_RTO_PATH);
                String mID = (String) marker.getAttribute(UmlProblem.ATTR_XT_ID);
                if (rtoPath != null
                        && rtoPath.equals(file.getFullPath().toString())) {
                    if(mID!=null && mID.endsWith(markerID))
                    marker.delete();
                }
            }
        }
        UIUtil.refresh(proj);
    }

}
