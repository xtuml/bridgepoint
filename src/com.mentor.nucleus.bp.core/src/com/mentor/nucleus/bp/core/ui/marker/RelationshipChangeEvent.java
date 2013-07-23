package com.mentor.nucleus.bp.core.ui.marker;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;

public class RelationshipChangeEvent extends MarkerEvent {
    public RelationshipChangeEvent(NonRootModelElement rgo, NonRootModelElement rto) {
this.rgo=rgo;
this.rto=rto;
    }

    private NonRootModelElement rgo;

    private NonRootModelElement rto;

    public void process() {
        MarkerEvent event=null;
        PersistableModelComponent rtoComp = rto.getPersistableComponent();
        while(rtoComp!=null && rtoComp.getStatus()!=PersistableModelComponent.STATUS_LOADED 
              && rtoComp.getStatus()!=PersistableModelComponent.STATUS_NOTLOADED){
            //reschedule this, load/unload in process
            DelayedMarkerJob.addFirst(this);
            return;
        }
        if (UmlProblem.isDangling(rto, false)) {
            event=new CreateMarkerEvent(rto,rgo);
        } else {
            event = new RemoveMarkerEvent(rgo,rto);
        }        
        DelayedMarkerJob.addFirst(event);
    }

    public void run(IProgressMonitor monitor) throws CoreException {
        
    }

}
