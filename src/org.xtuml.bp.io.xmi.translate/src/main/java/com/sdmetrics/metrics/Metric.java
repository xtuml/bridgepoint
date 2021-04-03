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
 * Represents the definition of metric in the metric definition file.
 */
public class Metric extends MetricEntry {
	/** Type of the element for which the metric is defined. */
	private MetaModelElement type;
	/** Indicates if the metric is internal, or externally visible. */
	private boolean internal;
	/** The category (structural property) measured by the metric. */
	private String category;

	/**
	 * Creates a new metric definition.
	 * 
	 * @param name Name of the metric.
	 * @param type Metamodel element type of the elements for which this metric
	 *        is defined.
	 * @param category The structural property measured by the metric.
	 */
	public Metric(String name, MetaModelElement type, String category) {
		super(name);
		this.type = type;
		this.category = category;
	}

	/**
	 * Creates a copy of a metric definition for another type.
	 * 
	 * @param original The metric definition to copy.
	 * @param newType Element type for the newly created copy.
	 */
	Metric(Metric original, MetaModelElement newType) {
		super(original);
		this.type = newType;
		this.internal = original.internal;
		this.category = original.category;
	}

	/**
	 * Retrieves the type of elements for which the metric is defined.
	 * 
	 * @return Element type of this metric.
	 */
	public MetaModelElement getType() {
		return type;
	}

	/**
	 * Gets the category (structural property) measured by the metric.
	 * 
	 * @return This metric's category.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Tests if this metric is internal or externally visible in the output.
	 * 
	 * @return <code>true</code> if the metric is internal.
	 */
	public boolean isInternal() {
		return internal;
	}

	/**
	 * Marks this metric as internal or external.
	 * 
	 * @param internal <code>true</code> to make the metric internal,
	 *        <code>false</code> to have it appear in metric data output.
	 */
	void setInternal(boolean internal) {
		this.internal = internal;
	}

	/**
	 * Returns a string representation of the metric.
	 * 
	 * @return String with the name of metric, its type, and the line number of
	 *         the definition
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("metric ");
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
