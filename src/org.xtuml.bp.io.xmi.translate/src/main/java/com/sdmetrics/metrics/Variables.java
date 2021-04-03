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

import java.util.HashMap;

import com.sdmetrics.model.ModelElement;

/**
 * Contains the names and values of variables to use when evaluating metric,
 * set, or condition expressions.
 * <p>
 * A set of variables always defines a principal, that is, the model element for
 * which a metric/set/rule is calculated. The principal is not necessarily the
 * same element for which the expression is evaluated.
 */
public class Variables {

	/** Name of the variable holding the principal. */
	static final String PRINCIPAL_NAME = "_principal";

	/** Stores the variables and their values. */
	private final HashMap<String, Object> map = new HashMap<>(2);

	/**
	 * Creates a new set of variables.
	 * 
	 * @param principal Principal for this set.
	 */
	public Variables(ModelElement principal) {
		map.put(PRINCIPAL_NAME, principal);
	}

	/**
	 * Sets the value of a variable. Adds the variable if it is not already
	 * defined in the set. Otherwise, replaces the value of the existing
	 * variable with the new value.
	 * 
	 * @param name Name of the variable.
	 * @param value Value of the variable.
	 */
	public void setVariable(String name, Object value) {
		map.put(name, value);
	}

	/**
	 * Gets the value of a variable.
	 * 
	 * @param name Name of the variable.
	 * @return Value of the variable.
	 */
	public Object getVariable(String name) {
		return map.get(name);
	}

	/**
	 * Tests whether a variable of a specified name is defined in this set.
	 * 
	 * @param name Name of the variable to look up.
	 * @return <code>true</code> if the variable is defined.
	 */
	public boolean hasVariable(String name) {
		return map.containsKey(name);
	}

	/**
	 * Gets the principal of this set.
	 * 
	 * @return The principal.
	 */
	ModelElement getPrincipal() {
		return (ModelElement) map.get(PRINCIPAL_NAME);
	}
}
