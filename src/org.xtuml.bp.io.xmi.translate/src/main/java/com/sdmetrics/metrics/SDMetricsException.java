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
 * Exception to deal with problems that can occur during metrics calculation.
 * <p>
 * If this exception is thrown, it is always due to a bug in the metric
 * definition file (typos, a required attribute is missing, an unknown metric is
 * requested etc.) The only way to fix the problem is then to edit the metric
 * definition file.
 * <p>
 * For a correct metric definition file, this exception will never be thrown. So
 * unless users write their own metric definitions (and invariably make mistakes
 * in the process), they should never experience any such errors during
 * calculation.
 */

public class SDMetricsException extends RuntimeException {
	private static final long serialVersionUID = 6113288972487051364L;

	/** Model element for which the calculation failed. */
	private ModelElement element;
	/** Metric, set, matrix or rule for which the calculation failed. */
	private MetricEntry entry;

	/**
	 * Constructor with message.
	 * 
	 * @param element The model element that caused the problem.
	 * @param entry The metric/set/rule that caused the problem.
	 * @param message A message describing the problem.
	 */
	public SDMetricsException(ModelElement element, MetricEntry entry,
			String message) {
		super(message);
		this.element = element;
		this.entry = entry;
	}

	/**
	 * Constructor with an exception to be chained.
	 * 
	 * @param element The model element that caused the problem.
	 * @param entry The metric/set/rule that caused the problem.
	 * @param cause The exception causing the problem.
	 */
	public SDMetricsException(ModelElement element, MetricEntry entry,
			Exception cause) {
		super("Internal metrics engine failure: " + cause.getMessage(), cause);
		this.element = element;
		this.entry = entry;
	}

	/**
	 * Gets the model element that caused the problem.
	 * 
	 * @return Model element that caused the problem.
	 */
	public ModelElement getElement() {
		return element;
	}

	/**
	 * Gets the metric, set, rule, or matrix that caused the problem.
	 * 
	 * @return Metric entry that caused the problem.
	 */
	public MetricEntry getMetricEntry() {
		return entry;
	}

	/**
	 * Sets the model element and/or metric entry that caused the problem, if
	 * not already set. Problems can arise in places where the model element or
	 * metric is not known. The metrics engine then adds this information later.
	 * 
	 * @param elem The model element that caused the problem.
	 * @param culprit The metric/set/rule that caused the problem.
	 */
	void fillInPerpetrators(ModelElement elem, MetricEntry culprit) {
		if (element == null) {
			element = elem;
		}
		if (entry == null) {
			entry = culprit;
		}
	}
}
