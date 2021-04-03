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

import com.sdmetrics.model.ModelElement;

/**
 * Stores details about the violation of one specific rule by one specific model
 * element.
 */
public class RuleViolation {
	/** The violating model element. */
	private final ModelElement element;
	/** The violated rule. */
	private final Rule rule;
	/** The value of the rule describing the violation. */
	private final Object value;

	/**
	 * Constructor.
	 * @param element The violating model element.
	 * @param rule The violated rule.
	 * @param value Value of the rule violation.
	 */
	public RuleViolation(ModelElement element, Rule rule, Object value) {
		this.element = element;
		this.rule = rule;
		this.value = value;
	}

	/**
	 * Gets the model element of this rule violation.
	 * 
	 * @return The violating model element.
	 */
	public ModelElement getModelElement() {
		return element;
	}

	/**
	 * Gets the rule of this rule violation.
	 * 
	 * @return The violated rule.
	 */
	public Rule getRule() {
		return rule;
	}

	/**
	 * Gets the value for this rule violation.
	 * 
	 * @return Value of the rule describing the violation.
	 */
	public Object getValue() {
		return value;
	}

}