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

import java.util.Collection;

import com.sdmetrics.model.MetaModelElement;

/**
 * Represents a design rule. An instance of this class corresponds to one rule
 * element in the metrics definition file.
 */
public class Rule extends MetricEntry {
	/** The element type for which the rule is defined. */
	private final MetaModelElement type;
	/** The category the rule belongs to. */
	private final String cat;
	/** The final criticality of the rule. */
	private final String crit;
	/** Indicates whether the rule is currently enabled. */
	private final boolean enabled;
	/** The application areas (development phases) of the rule. */
	private final Collection<String> applicationAreas;

	/**
	 * Creates a new rule.
	 * 
	 * @param name Name of the rule.
	 * @param type Metamodel element type of the elements for which this rule is
	 *        defined.
	 * @param category The category of the rule.
	 * @param criticality The criticality of the rule.
	 * @param appAreas The application areas of the rule.
	 * @param enabled Indicates whether or not this rule is enabled and should
	 *        be checked.
	 */
	public Rule(String name, MetaModelElement type, String category,
			String criticality, Collection<String> appAreas, boolean enabled) {
		super(name);
		this.type = type;
		this.cat = category;
		this.crit = criticality;
		this.applicationAreas = appAreas;
		this.enabled = enabled;
	}

	/**
	 * Creates a copy of a rule definition for another type. 
	 * 
	 * @param original The rule definition to copy.
	 * @param newType Element type for the newly created copy.
	 */
	Rule(Rule original, MetaModelElement newType) {
		super(original);
		this.type = newType;
		this.cat = original.cat;
		this.crit = original.crit;
		this.applicationAreas = original.applicationAreas;
		this.enabled = original.enabled;
	}

	/**
	 * Gets the category of this rule.
	 * 
	 * @return The category of the rule.
	 */
	public String getCategory() {
		return cat;
	}

	/**
	 * Gets the criticality of this rule.
	 * 
	 * @return The criticality of the rule.
	 */
	public String getCriticality() {
		return crit;
	}

	/**
	 * Gets the element type for which this rule is defined.
	 * 
	 * @return The element type of this rule.
	 */
	public MetaModelElement getType() {
		return type;
	}

	/**
	 * Tests whether this rule is enabled to not. When a rule is disabled, it is
	 * not checked for rule violations.
	 * 
	 * @return <code>true</code> if this rule is enabled.
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Gets the application areas of the rule.
	 * 
	 * @return The application areas of the rule, or <code>null</code> if none
	 *         have been specified.
	 */
	public Collection<String> getApplicableAreas() {
		return applicationAreas;
	}

	/**
	 * Returns a string representation of the rule.
	 * 
	 * @return String with the name of rule, its type, and the line number of
	 *         the definition
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("rule ");
		sb.append(name);
		sb.append(" for elements of type ");
		sb.append(type.getName());
		if (location > 0) {
			sb.append(" (line ");
			sb.append(location);
			sb.append(")");
		}
		return sb.toString();
	}

}
