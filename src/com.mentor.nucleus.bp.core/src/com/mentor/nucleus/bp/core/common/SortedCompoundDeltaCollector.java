//=====================================================================
//
//File:      $RCSfile: SortedCompoundDeltaCollector.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 22:54:10 $
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

import java.util.Arrays;
import java.util.Comparator;

import com.mentor.nucleus.bp.core.Ooaofooa;

public class SortedCompoundDeltaCollector extends CompoundDeltaCollector {

		
	public SortedCompoundDeltaCollector(ModelElement[] elements) {
		super(elements);	
	}
	
	public SortedCompoundDeltaCollector(ModelRoot[] modelRoot) {
		super(modelRoot);
	}
	
	protected void sort(Object[] array){
		Arrays.sort(array, new DeltaCollectorComparatorForModelElements());
	}
	 
	 
    class DeltaCollectorComparatorForModelElements implements Comparator {
        public int compare(Object object, Object objectWith) {
        	ModelRoot first;  
    		ModelRoot second; 
    		
    		if (object instanceof NonRootModelElement){
    			first = ((NonRootModelElement)object).getModelRoot();
    		}else{
    			first = (ModelRoot)object;
    		}
    		
    		if (objectWith instanceof NonRootModelElement){
    			second = ((NonRootModelElement)objectWith).getModelRoot();
    		}else{
    			second = (ModelRoot)objectWith;
    		}

            int compareResult = first.getId().compareTo(second.getId());
            if (compareResult == 0) {
                if (first instanceof Ooaofooa) {
                    compareResult = -1;
                } else {
                    compareResult = +1;
                }
            }
            return compareResult;
        }
    }
}
