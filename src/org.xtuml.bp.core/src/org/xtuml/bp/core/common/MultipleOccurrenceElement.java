//========================================================================
//
//File:      $RCSfile: MultipleOccurrenceElement.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 23:15:44 $
//
//Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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
//
package org.xtuml.bp.core.common;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;

import org.xtuml.bp.core.common.NonRootModelElement;

public class MultipleOccurrenceElement implements IAdaptable {

	// This static map maintains all of the instances of MultipleOccurrenceElement
	// in the system, mapped to the element itself and its parent element. The integer
	// key here is produced by multiplying the hashCode() value of the element instance
	// by 31 and adding the hashCode() value of the parent element. This technique of
	// combining two hash values together into one hash value is covered in the book
	// "Effective Java" by Joshua Bloch (https://of7vtvi79.qnssl.com/Effective%20Java%202nd.pdf)
	// the static accesor method 'getElement' assures that for any unique pairing of
	// 'element' and 'parentElement', the same instance of MultipleOccurrenceElement
	// will be returned each time it is called.
	private static Map<Integer, MultipleOccurrenceElement> multipleOccurrenceElements = new HashMap<>();

	private NonRootModelElement element;

	private MultipleOccurrenceElement(NonRootModelElement element) {
		this.element = element;
	}
	
	@Override
	public Object getAdapter(Class adapter) {
		if(adapter == NonRootModelElement.class) {
			return element;
		}
		return null;
	}

	public NonRootModelElement getElement() {
		return element;
	}
	
	public static MultipleOccurrenceElement getElement( NonRootModelElement element, Object parentElement ) {
		Integer key = new Integer( ( element.hashCode() * 31 ) + parentElement.hashCode() ); // create unique integer hash of two elements
		if ( multipleOccurrenceElements.containsKey(key) ) {
			return multipleOccurrenceElements.get(key);
		}
		else {
			MultipleOccurrenceElement newElement = new MultipleOccurrenceElement(element);
			multipleOccurrenceElements.put(key, newElement );
			return newElement;
		}
	}
	
}
