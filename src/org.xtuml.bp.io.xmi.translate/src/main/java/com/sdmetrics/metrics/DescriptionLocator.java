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

/**
 * Enumerates the locators for metrics, rules, and so forth in descriptions in
 * the metric definition files.
 * <p>
 * Locators are used to cross-reference metrics, rules, matrices, literature
 * references and glossary items in the documentation of these items. Because of
 * their hyperlink character, locators are constructed like URLs. The string
 * "metric://class/NumOps/" for example references the design metric "NumOps"
 * for elements of type "class".
 * <p>
 * We call the "metric://" part of the locator its prefix (rather than protocol
 * as there is no communication protocol implied). The "class/NumOps/" part are
 * the parameters of the locator. Each locator has a fixed number of parameters.
 */
public enum DescriptionLocator {

	/**
	 * Locator for metrics. Has two parameters "element type" and "metric name".
	 */
	METRIC("metric://", 2),
	/** Locator for rules. Has two parameters "element type" and "rule name". */
	RULE("rule://", 2),
	/** Locator for matrices. Has one parameter "matrix name". */
	MATRIX("matrix://", 1),
	/** Locator for literature references. Has one parameter "citation handle". */
	REFERENCE("ref://", 1),
	/** Locator for glossary entries. Has two parameters "term" and "link text". */
	GLOSSARY("glossary://", 2);

	private final String prefix;
	private final int parameterCount;

	private DescriptionLocator(String prefix, int parameterCount) {
		this.prefix = prefix;
		this.parameterCount = parameterCount;
	}

	/**
	 * Gets the prefix of this locator.
	 * 
	 * @return Prefix of the locators.
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Gets the number of parameters this locator expects following the prefix.
	 * 
	 * @return Number of parameters of the locator.
	 */
	public int getParameterCount() {
		return parameterCount;
	}
}
