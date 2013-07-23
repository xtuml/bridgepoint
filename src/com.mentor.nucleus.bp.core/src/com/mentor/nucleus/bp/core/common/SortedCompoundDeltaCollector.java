//=====================================================================
//
//File:      $RCSfile: SortedCompoundDeltaCollector.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 22:54:10 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
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
