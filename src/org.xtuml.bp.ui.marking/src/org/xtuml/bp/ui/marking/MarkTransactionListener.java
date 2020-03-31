package org.xtuml.bp.ui.marking;

import org.eclipse.core.resources.IProject;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Modeleventnotification_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.AttributeChangeModelDelta;
import org.xtuml.bp.core.common.IModelDelta;
import org.xtuml.bp.core.common.ITransactionListener;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.RelationshipChangeModelDelta;
import org.xtuml.bp.core.common.Transaction;

public class MarkTransactionListener implements ITransactionListener {

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
                   (deltaKind == Modeleventnotification_c.DELTA_MODEL_ELEMENT_MOVE) ||
                   isDataTypeChange(deltaToHandle)
                  ) {
                  NonRootModelElement nrme = ((NonRootModelElement) deltaToHandle.getModelElement());
                  NonRootModelElement sys_nrme = nrme.getRoot();
                  if (sys_nrme instanceof SystemModel_c) {
                     IProject project = (IProject) ((SystemModel_c) sys_nrme).getAdapter(IProject.class);
                     if ( project != null ) {
                        MarkingData md = MarkingDataManager.getMarkingData(project);
                        if (md.verifyMarkingData()) {
                        boolean rpk = false;
                        // Update a key letter change here, as recalculatePathKeys doesn't handle it.
                        if ( deltaKind == Modeleventnotification_c.DELTA_ATTRIBUTE_CHANGE ) { 
                           AttributeChangeModelDelta change = (AttributeChangeModelDelta)deltaToHandle;
                           if ("Key_lett" == change.getAttributeName()) {
                               rpk = md.updateKeyletterData(change.getNewValue().toString(), change.getOldValue().toString());
                           } else {
                              if ( ("Name" == change.getAttributeName()) && 
                                  (change.getModelElement() instanceof ModelClass_c) ) {
                                 rpk = md.updateClassNameData(change.getNewValue().toString(), change.getOldValue().toString());
                              }
                           }
                        }
                        if (rpk || md.recalculatePathKeys()) {
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
    
    /**
     * @return  True if the delta is for a model element changing datatype, otherwise false.
     */
    public boolean isDataTypeChange(IModelDelta delta) {
      if ( (delta.getKind() == Modeleventnotification_c.DELTA_ELEMENT_RELATED)  ||
           (delta.getKind() == Modeleventnotification_c.DELTA_ELEMENT_UNRELATED) 
        ) { 
         RelationshipChangeModelDelta rcmd = (RelationshipChangeModelDelta) delta;
         Object dest = rcmd.getDestinationModelElement();
         if (dest instanceof DataType_c) {
            return true;
         }
      }
      return false;
    }
    
}
