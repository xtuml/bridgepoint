//========================================================================
//
//File:      $RCSfile: TransactionListener.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:21:32 $
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
package com.mentor.nucleus.bp.test.common;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.AttributeChangeModelDelta;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.ITransactionListener;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.RelationshipChangeModelDelta;
import com.mentor.nucleus.bp.core.common.Transaction;

public class TransactionListener implements ITransactionListener {

    volatile boolean transactionUnderReview = false; 
    public void transactionStarted(Transaction transaction) {        
        BaseTest.resultLogger.addToLog("Transaction started: " + transaction.getDisplayName());         //$NON-NLS-1$
    }
    
    public void transactionCancelled(Transaction transaction){
    	logTransaction(transaction, true);
    }

    public void transactionEnded(Transaction transaction) {
    	logTransaction(transaction, false);
    }
    
    private void logTransaction(Transaction transaction, boolean cancelled){
        transactionUnderReview = true;
        ModelRoot[] modelRoots = transaction.getParticipatingModelRoots();
        
        //Ordering the model roots so taht the ooaofgraphics succeeds its
        //associated ooaofooa
        Arrays.sort(modelRoots, new Comparator(){
            public int compare(Object arg0, Object arg1) {
                ModelRoot first = (ModelRoot)arg0;
                ModelRoot second = (ModelRoot)arg1;
                int compareResult = first.getId().compareTo(second.getId());
                if (compareResult == 0) {
                    if (first instanceof Ooaofooa) {
                        compareResult = -1;
                    } else {
                        compareResult = +1;
                    }
                }
                return compareResult;                
            }});
        
        int displayCount = 1;
        for (int i = 0; i < modelRoots.length; i++ ){
            IModelDelta[] modelDeltas = transaction.getDeltas(modelRoots[i]);
            for (int j = 0; j < modelDeltas.length; j++) {
                IModelDelta delta = modelDeltas[j];
                
                BaseTest.resultLogger.addToLog(" ", new ModelDeltaLogable(delta));  //$NON-NLS-1$//$NON-NLS-2$
                displayCount++;
            }
        }            
        
        String mode = "Transaction Ended: ";
        if(cancelled){
        	mode = "Transaction Cancelled: ";
        }
        
        BaseTest.resultLogger.addToLog(mode + transaction.getDisplayName() + " with delta size: " + (displayCount-1)); //$NON-NLS-1$ //$NON-NLS-2$
        
        transactionUnderReview = false;
    }
    
    public void WaitForTransactionUnderReview(){
        while (transactionUnderReview){
			try {
				Thread.sleep(200);				
			} catch (InterruptedException e) {			    
			}
		}
    }    
    
    class ModelDeltaLogable implements ILogable{
        
        IModelDelta deltaToLog;
        public ModelDeltaLogable(IModelDelta modelDelta) {
            deltaToLog = modelDelta;
        }
        
        public String getLogString() {
            String logString = ""; //$NON-NLS-1$
            if (deltaToLog instanceof RelationshipChangeModelDelta){
                RelationshipChangeModelDelta relDelta = (RelationshipChangeModelDelta) deltaToLog;
                if(relDelta.getKind() == Modeleventnotification_c.DELTA_ELEMENT_RELATED){
                    logString = getStringValue(relDelta.getModelElement())
                			+ " RelatedAcross R" //$NON-NLS-1$
                			+ relDelta.getRelationName()
                			+ " To: " //$NON-NLS-1$
                			+ getStringValue(relDelta.getDestinationModelElement());
                }else{
                    logString = getStringValue(relDelta.getModelElement())
                			+ " UN-RelatedAcross R" //$NON-NLS-1$
                			+ relDelta.getRelationName()
                			+ " To: " //$NON-NLS-1$
                			+ getStringValue(relDelta.getDestinationModelElement());
                }
            } else if (deltaToLog instanceof AttributeChangeModelDelta){
                AttributeChangeModelDelta attrDelta = (AttributeChangeModelDelta) deltaToLog;
				
                String oldValue = getStringValue(attrDelta.getOldValue());
                String newValue = getStringValue(attrDelta.getNewValue());

                logString = new String( "Attribute '" //$NON-NLS-1$
                                        + attrDelta.getAttributeName()
										+ "' of "
                        				+ getClassName(attrDelta.getModelElement())
                        				+ " changed (" //$NON-NLS-1$
										+ " '" + oldValue + "' -> '"
										+ newValue + "')");
            }else{	//BaseModelDelta
            	if(deltaToLog.getKind() == Modeleventnotification_c.DELTA_NEW){
                    logString = new String ("Creation of instance of type: "  //$NON-NLS-1$
             				+ getClassName(deltaToLog.getModelElement()));
            	}else{
                    logString = new String ("Deletion of instance of type: "  //$NON-NLS-1$
             				+ getClassName(deltaToLog.getModelElement()));
            	}
            }
            return logString;
        }        
    }
    
    private String getStringValue(Object value){
    	if(value == null){
    		return "null";
    	}
    	
    	try {
			Method toStringMethod = value.getClass().getMethod("toString", null);

			if(toStringMethod.getDeclaringClass().equals(Object.class)){
                return " instance of [" + getClassName(value) + "]";
			}
		} catch (Exception e) {
		}
		
		return String.valueOf(value);
    }
    
    private String getClassName(Object object){
		String className = object.getClass().getName();
		return className.substring(22, className.length()-2);
    }
}