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

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.model.ModelElement;

/**
 * Processes the "sum" and "stat" attributes of metric calculation procedures.
 */
public class SummationHelper {
	/** Metrics engine for expression evaluations. */
	private final MetricsEngine engine;
	/** Sum expression that yields the values to be accumulated. */
	private final ExpressionNode sumExpression;
	/** The resulting total. */
	private float total = 0;
	private boolean processMin = false;
	private boolean processMax = false;
	private boolean firstCall = true;

	/**
	 * Constructor.
	 * @param engine metrics engine for expression evaluation
	 * @param attributes Attributes of the calculation procedure to process.
	 * @throws SDMetricsException That "stat" attribute is invalid
	 */
	public SummationHelper(MetricsEngine engine, ProcedureAttributes attributes)
			throws SDMetricsException {
		this.engine = engine;
		sumExpression = attributes.getExpression("sum");
		String statistic = attributes.getStringValue("stat");
		if (statistic != null) {
			if ("min".equals(statistic)) {
				processMin = true;
			} else if ("max".equals(statistic)) {
				processMax = true;
			} else if (!"sum".equals(statistic)) {
				throw new SDMetricsException(null, null, "Illegal value '"
						+ statistic + "' for attribute 'stat'.");
			}
		}
	}

	/**
	 * Processes the "sum" and "stat" attributes for a model element and updates
	 * the total accordingly.
	 * 
	 * @param element The model element to process.
	 * @param vars Variables for the evaluation of expressions
	 * @throws SDMetricsException An error occurred evaluating the sum
	 *         expression.
	 */
	public void add(ModelElement element, Variables vars)
			throws SDMetricsException {
		// just register the element if "sum" is not set.
		if (sumExpression == null) {
			total += 1.0;
			return;
		}

		// evaluate the "sum" expression and update the total
		float sumVal = ((Number) engine
				.evalExpression(element, sumExpression, vars)).floatValue();
		if (processMax) {
			if (firstCall || sumVal > total) {
				total = sumVal;
			}
		} else if (processMin) {
			if (firstCall || sumVal < total) {
				total = sumVal;
			}
		} else {
			total += sumVal;
		}
		firstCall = false;
	}

	/**
	 * Adds a value to the current total.
	 * 
	 * @param value Value to add
	 */
	public void add(float value) {
		total += value;
	}

	/**
	 * Raises the total to a specified value if it currently is below that
	 * value. Leaves the total unaffected if it is already greater than the
	 * specified value.
	 * 
	 * @param value new total value to raise to
	 */
	public void raiseTo(float value) {
		if (value > total) {
			total = value;
		}
	}

	/**
	 * Gets the current total.
	 * 
	 * @return The current total.
	 */
	public Number getTotal() {
		return MetricTools.getNumber(total);
	}
}
