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
 * Calculates a "nesting" metric procedure.
 */
public class MetricProcedureNesting extends MetricProcedure {

	/**
	 * {@inheritDoc}
	 * @return Nesting level as defined by the metric.
	 */
	@Override
	public Integer calculate(ModelElement element, Metric metric)
			throws SDMetricsException {
		ProcedureAttributes attributes = metric.getAttributes();
		ExpressionNode rel = attributes.getRequiredExpression("relation");
		int result = 0;

		// Get parent in the relation that defines the nesting
		ModelElement parent = getMetricsEngine().evalModelElementExpression(
				element, rel, new Variables(element));
		if (parent != null) {
			if (isCompatible(element, metric, parent)) {
				// Compatible parent, nesting is parent's nesting level plus 1
				result = 1 + ((Integer) getMetricsEngine().getMetricValue(
						parent, metric)).intValue();
			}
		}
		return Integer.valueOf(result);
	}
}
