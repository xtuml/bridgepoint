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
 * Represents a glossary term entry in the metric definition file. The name of
 * the entry is the term being defined, the description of the entry is the
 * term's definition.
 */
public class Glossary extends MetricEntry {

	/**
	 * Creates a new glossary entry.
	 * 
	 * @param term Name of the term being defined by this glossary entry.
	 */
	Glossary(String term) {
		super(term);
	}

	/**
	 * Returns a string representation of the glossary entry.
	 * 
	 * @return String with the term being defined
	 */
	@Override
	public String toString() {
		return "glossary entry " + name;
	}
}
