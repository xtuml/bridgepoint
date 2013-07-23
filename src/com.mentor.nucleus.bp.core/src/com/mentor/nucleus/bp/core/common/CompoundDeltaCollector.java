//=====================================================================
//
//File:      $RCSfile: CompoundDeltaCollector.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 22:54:09 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.core.common;

import java.util.ArrayList;
import java.util.List;

public class CompoundDeltaCollector implements IDeltaCollector {

	protected IDeltaCollector[] deltaCollectors;
	
	ModelRoot.SynchronousLock lock = new ModelRoot.SynchronousLock();
	
	public CompoundDeltaCollector(){
		
	}
	public CompoundDeltaCollector(ModelRoot[] modelRoot){
		sort(modelRoot);
		List deltas = new ArrayList(5);
		for (int i = 0; i < modelRoot.length; i++){
			if (!deltas.contains(modelRoot[i].getDeltaCollector())){
				deltas.add(i, modelRoot[i].getDeltaCollector());
			}			
		}
		initializeDeltaCollectors(deltas);
		deltas.clear();
	}
	
	public CompoundDeltaCollector(ModelElement[] elements) {
		sort(elements);
		List deltas = new ArrayList(5);
		IDeltaCollector temp = null;
		for (int i = 0 ; i < elements.length; i++){
			if (elements[i] instanceof ModelRoot ){
    			temp = ((ModelRoot)elements[i]).getDeltaCollector();
    		}else{ 
    			temp = (((NonRootModelElement)elements[i]).getModelRoot()).getDeltaCollector();
    		}
			if (!deltas.contains(temp)){
				deltas.add(i, temp);
			}
		}
		initializeDeltaCollectors(deltas);
		deltas.clear();
	}
	
	public void startCollecting(Transaction transaction) throws TransactionException {
		for (int i = 0; i < deltaCollectors.length; i++){
			deltaCollectors[i].startCollecting(transaction);			
		}   		
	}

	public void endCollecting() {
		for (int i = 0; i < deltaCollectors.length; i++){
			deltaCollectors[i].endCollecting();
		}

	}

	public void waitIfLocked() {
		lock.waitOnLock();
	}
	
	protected void setDeltaCollectors(IDeltaCollector[] aDeltaCollectors){
		deltaCollectors = aDeltaCollectors;
	}
	
	protected void sort(Object[] array){}
		
	private void initializeDeltaCollectors(List deltas){
		deltaCollectors = new IDeltaCollector[deltas.size()];
		for (int i = 0; i < deltas.size(); i++){
			deltaCollectors[i] = (IDeltaCollector)deltas.get(i);
		}
	}	
}
