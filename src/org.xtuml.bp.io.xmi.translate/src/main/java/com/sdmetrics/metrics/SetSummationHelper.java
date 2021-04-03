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

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.model.ModelElement;

/**
 * Helps set calculation procedures to build up their cumulative result sets.
 */
public class SetSummationHelper {

	/** Expression that yields the sets to be accumulated. */
	private final ExpressionNode cumulativeSetExpression;
	/** Expression that yields values to be accumulated in a set. */
	private final ExpressionNode valueSetExpression;
	/** The resulting cumulative set. */
	@SuppressWarnings("rawtypes")
	private final Collection resultSet;
	/** Indicates if the principal must be removed from the result set. */
	private final boolean excludeSelf;
	/** Metrics engine to use for evaluations of expressions. */
	private final MetricsEngine engine;

	/**
	 * Constructor.
	 * @param engine metrics engine for expression evaluation
	 * @param set The definition of the set to process
	 * @param setAttributeName The name of the attribute defining the sets to
	 *        accumulate.
	 * @throws SDMetricsException Summation attributes have illegal values
	 */
	public SetSummationHelper(MetricsEngine engine, Set set,
			String setAttributeName) throws SDMetricsException {
		this.engine = engine;
		ProcedureAttributes attributes = set.getAttributes();
		cumulativeSetExpression = attributes.getExpression(setAttributeName);
		valueSetExpression = attributes.getExpression("valueset");
		excludeSelf = attributes.getBooleanValue("exclude_self", false);

		resultSet = MetricTools.createHashSet(set.isMultiSet());
	}

	/**
	 * Processes the cumulative or valueset expression for a model element and
	 * adds its contents to the result set.
	 * <ul>
	 * <li>If the "valueset" attribute is set, it is evaluated for the model
	 * element and the expression result added to the result set.</li>
	 * <li>If the cumulative set expression is set, it is evaluated and its
	 * contents added to the result set.
	 * <li>Otherwise, the model element itself is added to the result set.
	 * </ul>
	 * 
	 * @param element The model element to process.
	 * @param vars Variables for the evaluation of expressions
	 * @throws SDMetricsException An error occurred evaluating the set
	 *         expressions.
	 */
	@SuppressWarnings("unchecked")
	public void add(ModelElement element, Variables vars)
			throws SDMetricsException {
		if (valueSetExpression != null) {
			resultSet.add(engine.evalExpression(element, valueSetExpression,
					vars));
		} else if (cumulativeSetExpression != null) {
			resultSet.addAll(engine.evalSetExpression(element,
					cumulativeSetExpression, vars));
		} else {
			resultSet.add(element);
		}
	}

	/**
	 * Adds the contents of a collection to the result set.
	 * 
	 * @param c Collection to add.
	 */
	@SuppressWarnings("unchecked")
	public void add(Collection<?> c) {
		resultSet.addAll(c);
	}

	/**
	 * Removes a model element from the result set if the "exclude_self"
	 * attribute is set to true. If not, calling this method has no effect.
	 * 
	 * @param element Element to remove.
	 */
	public void excludeSelf(ModelElement element) {
		if (excludeSelf) {
			MetricTools.removeAny(resultSet, element);
		}
	}

	/**
	 * Gets the result set.
	 * 
	 * @return The current result set.
	 */
	public Collection<?> getResultSet() {
		return resultSet;
	}
}
