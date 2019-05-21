package org.xtuml.bp.ui.marking;

import org.eclipse.core.resources.IProject;
import org.xtuml.bp.core.Modeleventnotification_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.XtUMLNature;
import org.xtuml.bp.core.common.IModelDelta;
import org.xtuml.bp.core.common.ITransactionListener;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.Transaction;

public class MarkTransactionListener implements ITransactionListener {

    public static void initialize(IProject[] projects){
    	for (IProject project : projects) {
    		if (XtUMLNature.hasNature(project)) {
    			// Trigger the marking data for the project to load
    			MarkingDataManager.getMarkingData(project);
    		}
    	}
    }

    public void transactionStarted(Transaction transaction) {        
    }
    
    public void transactionCancelled(Transaction transaction){
    }

    public void transactionEnded(Transaction transaction) {
    	if (transaction.getType().equals(Transaction.AUTORECONCILE_TYPE)) { return; }
    	
        ModelRoot[] modelRoots = transaction.getParticipatingModelRoots();
        for (int i = 0; i < modelRoots.length; i++ ){
            IModelDelta[] modelDeltas = transaction.getDeltas(modelRoots[i]);
            for (IModelDelta deltaToHandle : modelDeltas) {
            	int deltaKind = deltaToHandle.getKind();
            	if ( (deltaKind == Modeleventnotification_c.DELTA_NEW) ||
            		 (deltaKind == Modeleventnotification_c.DELTA_DELETE) ||
            		 (deltaKind == Modeleventnotification_c.DELTA_ATTRIBUTE_CHANGE) ||
            		 (deltaKind == Modeleventnotification_c.DELTA_MODEL_ELEMENT_MOVE) 
            	   ) {
            		NonRootModelElement nrme = ((NonRootModelElement) deltaToHandle.getModelElement());
            		NonRootModelElement sys_nrme = nrme.getRoot();
            		if (sys_nrme instanceof SystemModel_c) {
            			IProject project = (IProject) ((SystemModel_c) sys_nrme).getAdapter(IProject.class);
            			if ( project != null ) {
            				MarkingData md = MarkingDataManager.getMarkingData(project);
            				if (md.recalculatePathKeys()) {
            					// If the marks were updated then persist them
            					md.persist();
            				}
            			}
            		}
            	}
            }
        }
    }
    
}