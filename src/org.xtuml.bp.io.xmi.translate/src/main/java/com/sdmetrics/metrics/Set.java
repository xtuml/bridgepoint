/*
 * SDMetrics Open Core for UML design measurement
 * Copyright (c) Juergen Wuest
 * To contact the author, see <http://www.sdmetrics.com/Contact.html>.
 * 
 * This file is part of the SDMetrics Open Core.
 * 
 * SDMetrics Open Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
    
 * SDMetrics Open Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with SDMetrics Open Core.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.sdmetrics.metrics;

import com.sdmetrics.model.MetaModelElement;

/**
 * Represents the definition of a set in the metric definition file.
 */
public class Set extends MetricEntry {
	/** Type of the element for which the set is defined. */
	private MetaModelElement type;
	/** Indicates if this is a multiset or a regular set. */
	private boolean multiset;

	/**
	 * Creates a new set definition.
	 * 
	 * @param name Name of the set.
	 * @param type Metamodel element type of the elements for which this set is
	 *        defined.
	 */
	public Set(String name, MetaModelElement type) {
		super(name);
		this.type = type;
	}

	/**
	 * Creates a copy of a set definition for another type. 
	 * 
	 * @param original The set definition to copy.
	 * @param newType Element type for the newly created copy.
	 */
	Set(Set original, MetaModelElement newType) {
		super(original);
		this.type = newType;
		this.multiset = original.multiset;
	}

	/**
	 * Retrieves the type of elements for which the set is defined.
	 * 
	 * @return Element type of this set.
	 */
	public MetaModelElement getType() {
		return type;
	}

	/**
	 * Checks if this entry defines a regular set or a multiset. Regular sets
	 * contain at most one occurrence of any element, multisets can contain more
	 * than one occurrence of the same element.
	 * 
	 * @return <code>true</code> if set defines a multiset.
	 */
	public boolean isMultiSet() {
		return multiset;
	}

	/**
	 * Makes this set a multiset.
	 * 
	 * @param multi <code>true</code> if this set is to be a multiset, else
	 *        <code>false</code>.
	 */
	void setMultiSet(boolean multi) {
		multiset = multi;
	}

	/**
	 * Returns a string representation of the set.
	 * 
	 * @return String with the name of the set and its type
	 */
	@Override
	public String toString() {
		return "set " + name + " for elements of type " + type.getName();
	}
}
