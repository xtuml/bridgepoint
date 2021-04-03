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
 * Represents a literature reference in the metric definition file. Uses two
 * spaces to separate the brief description (bibliographic citation) from the
 * full description (optional annotations).
 */
public class Reference extends MetricEntry {
	/**
	 * Constructor.
	 * @param tag Tag of the reference entry.
	 */
	Reference(String tag) {
		super(tag);
		description.setSeparator("  ");
	}

	/**
	 * Returns a string representation of the reference.
	 * 
	 * @return String with the tag of the reference in square brackets
	 */
	@Override
	public String toString() {
		return "[" + name + "]";
	}
}
