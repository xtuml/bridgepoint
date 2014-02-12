//=====================================================================
//
//File:      $RCSfile: CompoundDeltaCollector.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 22:54:09 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
