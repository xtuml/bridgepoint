//=====================================================================
//
//File:      $RCSfile: AccumulatorInterface.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2012/01/23 21:27:40 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.core.util;

import java.util.List;
import java.util.Vector;
import java.util.Observable;
import java.util.Collections;;

/**
 * The purpose of this interface is provide a generic way to help process
 * events received in batch-mode.  The Accumulator stores objects until
 * some threshhold is reached and then allows the user to access these objects
 * all at once.
 */
public class AccumulatorInterface extends Observable {
	private List<Object> m_elementsReceived = new Vector<Object>();

	/**
	 * Push an element on this Accumulator's collection of objects.
	 */
	public void addElement(Object element) {
		m_elementsReceived.add(element);
	}

	public synchronized boolean accumulationInProgress() {
		// As long as there are elements still in the list we are not done
		return !m_elementsReceived.isEmpty();
	}

	/**
	 * This routines notifies the observers that the threshold has been met and
	 * it passes the observes the list of objects that were collected.  Note that
	 * if there are no objects the observeres are not called (we don't bother
	 * passing an empty list).
	 */
	public synchronized void notifyObservers() {
		if ( !m_elementsReceived.isEmpty() ) {
			List<Object> tempList = new Vector<Object>(m_elementsReceived);
			// Mark this Accumulator as changed
			this.setChanged();
			this.notifyObservers(tempList);
			m_elementsReceived.clear();
		}
	}
}
