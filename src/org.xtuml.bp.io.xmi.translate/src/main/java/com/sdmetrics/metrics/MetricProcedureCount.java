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
 * Calculates the deprecated "count" metric procedure.
 * <p>
 * Counts are now just a special case of projections, using the principal
 * element in the condition expression.
 */
public class MetricProcedureCount extends MetricProcedure {

	@Override
	public Integer calculate(ModelElement element, Metric metric)
			throws SDMetricsException {
		ProcedureAttributes attributes = metric.getAttributes();
		Variables vars = new Variables(element);
		Object term = evalExpression(element,
				attributes.getRequiredExpression("term"), vars);
		vars.setVariable("term", term);
		Collection<ModelElement> set = evalElementSetExpression(element,
				attributes.getRequiredExpression("set"), vars);
		if (set.isEmpty()) {
			return MetricTools.ZERO;
		}

		FilterAttributeProcessor fap = getFilterAttributeProcessor(attributes);
		ExpressionNode relset = attributes.getExpression("relset");
		if (relset == null) {
			return Integer.valueOf(MetricTools.elementCount(set, term));
		}
		int result = 0;
		vars.setVariable("set", set);

		// Iterate elements in the result set
		for (ModelElement elem : fap.validIteration(set, vars)) {
			Collection<?> c = evalSetExpression(elem, relset, vars);
			result += MetricTools.elementCount(c, term);
		}
		return Integer.valueOf(result);
	}
}
