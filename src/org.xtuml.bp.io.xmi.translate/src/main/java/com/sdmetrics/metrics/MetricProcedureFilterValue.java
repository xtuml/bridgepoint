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
import java.util.Iterator;

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.model.ModelElement;

/**
 * Calculates a "filtervalue" metric procedure.
 */
public class MetricProcedureFilterValue extends MetricProcedure {

	@Override
	public Object calculate(ModelElement element, Metric m)
			throws SDMetricsException {
		ProcedureAttributes attributes = m.getAttributes();

		Variables vars = new Variables(element);
		Collection<ModelElement> set = getRelationOrSet(element, attributes,
				vars);
		if (set == null || set.isEmpty()) {
			return "";
		}

		FilterAttributeProcessor fa = getFilterAttributeProcessor(attributes);
		ExpressionNode valueExp = attributes.getExpression("value");

		// Return the first element produced by the filter attributes
		Iterator<ModelElement> it = fa.validIteration(set, vars).iterator();
		if (it.hasNext()) {
			if (valueExp != null) {
				return evalExpression(it.next(), valueExp, vars);
			}
			return it.next();
		}

		return "";
	}
}
