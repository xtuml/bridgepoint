//========================================================================
//
//File:      $RCSfile: ConsistencyTransactionListener.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 22:54:09 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
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