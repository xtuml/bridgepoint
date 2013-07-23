//=====================================================================
//
//File:      $RCSfile: AccumulatorInterface.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2012/01/23 21:27:40 $
//
//(c) Copyright 2006-2012 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
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
