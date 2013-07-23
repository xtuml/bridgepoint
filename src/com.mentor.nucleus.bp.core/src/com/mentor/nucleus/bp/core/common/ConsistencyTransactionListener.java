//========================================================================
//
//File:      $RCSfile: ConsistencyTransactionListener.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 22:54:09 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
package com.mentor.nucleus.bp.core.common;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;

public class ConsistencyTransactionListener implements ITransactionListener{
	
	public void transactionEnded(Transaction transaction) { 
		if (Ooaofooa.getConsistencyEnabled() == false){
			return;
		}
        ModelRoot[] modelRoots = transaction.getParticipatingModelRoots();
       	Set<ModelElement> modelSet = new HashSet<ModelElement>();
    	Set<ModelElement> deleteSet = new HashSet<ModelElement>();        
        for (int i = 0; i < modelRoots.length; i++){
             IModelDelta[] modelDeltas = transaction.getDeltas(modelRoots[i]); 
            for (int j = 0; j < modelDeltas.length; j++){
                IModelDelta aDelta = modelDeltas[j];   
                if (aDelta.getKind() == Modeleventnotification_c.DELTA_DELETE){
                	deleteSet.add(aDelta.getModelElement());
                }
                else{
                    if(aDelta instanceof RelationshipChangeModelDelta){
                	    RelationshipChangeModelDelta rc_md = (RelationshipChangeModelDelta) aDelta;
                	    ModelElement src_rc_me = rc_md.getSourceModelElement();
                	    if (src_rc_me != null){
                	    	if(!((NonRootModelElement)src_rc_me).isProxy())
                	    		modelSet.add(src_rc_me);
                	    }
                	    ModelElement dst_rc_me = rc_md.getDestinationModelElement();
                	    if (dst_rc_me != null){
                	    	if(!((NonRootModelElement)dst_rc_me).isProxy())
                	    		modelSet.add(dst_rc_me);
                	    }
                    }
                    else{
                	    ModelElement me = aDelta.getModelElement();
               	    	if ( me != null){
               	    		if(!((NonRootModelElement)me).isProxy())
               	    			modelSet.add(me);
               	    	}
                    }
                }
            }
            modelSet.removeAll(deleteSet);
            for (Iterator k = modelSet.iterator(); k.hasNext(); ){
            	ModelElement elem = (ModelElement) k.next();
            	elem.checkConsistency();
            
            }
            modelSet.clear();
            deleteSet.clear();
        }
	}
	
	public void transactionStarted(Transaction transaction) {        
    }
	
	public void transactionCancelled(Transaction transaction) {        
    }
}