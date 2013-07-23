package com.mentor.nucleus.bp.core.ui.marker;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.mentor.nucleus.bp.core.common.IPersistenceHierarchyMetaData;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;

public class ProxyCreatedEvent extends MarkerEvent {

    private NonRootModelElement rto;

    public ProxyCreatedEvent(NonRootModelElement rto) {
        this.rto = rto;
    }

    public void process() throws CoreException {
        PersistableModelComponent pmc = PersistenceManager.findComponent(rto.getContent());
        if (pmc != null){
            if (pmc.getStatus() != PersistableModelComponent.STATUS_LOADED
                    && pmc.getStatus() != PersistableModelComponent.STATUS_NOTLOADED) {
                //Element may be converted to proxt during unload, wait until 
                //component has been unloaded properly
                DelayedMarkerJob.addJob(this);
                return ;
            }
        }
        if (UmlProblem.isDangling(rto, false)) {
            IPersistenceHierarchyMetaData hmd = PersistenceManager
                    .getHierarchyMetaData();
            List rgos = hmd.findExternalRGOs(rto);
            for (Iterator iter = rgos.iterator(); iter.hasNext();) {
                NonRootModelElement rgo = (NonRootModelElement) iter
                        .next();
                DelayedMarkerJob.addFirst(new RelationshipChangeEvent(rgo, rto));
            }
        }
    }

    public void run(IProgressMonitor monitor) throws CoreException {

    }

}
