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
 * Calculates a "compound" metric procedure.
 */
public class MetricProcedureCompound extends MetricProcedure {

	@Override
	public Object calculate(ModelElement element, Metric metric)
			throws SDMetricsException {
		ProcedureAttributes attributes = metric.getAttributes();
		String expressionToUse = "term";
		Integer defaultValue = MetricTools.ONE;

		ExpressionNode condition = attributes.getExpression("condition");
		if (condition != null) {
			if (!evalBooleanExpression(element, condition, null)) {
				expressionToUse = "alt";
				defaultValue = MetricTools.ZERO;
			}
		}

		ExpressionNode expr = attributes.getExpression(expressionToUse);
		if (expr == null) {
			return defaultValue;
		}

		Object result = evalExpression(element, expr, null);
		if (result instanceof Float) {
			Float f = (Float) result;
			if (f.isNaN() || f.isInfinite()) {
				ExpressionNode fallBack = attributes.getExpression("fallback");
				if (fallBack != null) {
					result = evalExpression(element, fallBack, null);
				}
			}
		}
		return result;
	}
}
