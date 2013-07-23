package com.mentor.nucleus.bp.core.ui.marker;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.util.UIUtil;

public class RemoveMarkerEvent extends MarkerEvent {

    private NonRootModelElement rto;

    private NonRootModelElement rgo;

    public RemoveMarkerEvent(NonRootModelElement rgo, NonRootModelElement rto) {
        this.rgo = rgo;
        this.rto = rto;

    }

    public void process() throws CoreException {
        String markerID = UmlProblem.createMarkerID(rgo, rto);
        IMarker marker = UmlProblem.findMarker(rgo.getFile(), markerID);
        if (marker == null) {
            return;
        }
        internalProcess(rgo.getFile().getParent());
    }

    public void run(IProgressMonitor monitor) throws CoreException {
        String markerID = UmlProblem.createMarkerID(rgo, rto);
        IMarker marker = UmlProblem.findMarker(rgo.getFile(), markerID);
        if (marker != null) {
            marker.delete();
            UIUtil.refresh(rgo.getFile().getProject());
        }
        
    }

}
