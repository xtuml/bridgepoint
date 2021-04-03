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

import com.sdmetrics.model.ModelElement;

/**
 * Calculates a "projection" metric procedure.
 */
public class MetricProcedureProjection extends MetricProcedure {

	@Override
	public Number calculate(ModelElement element, Metric metric)
			throws SDMetricsException {
		ProcedureAttributes attributes = metric.getAttributes();

		Variables vars = new Variables(element);
		Collection<ModelElement> rel = getRelationOrSet(element, attributes,
				vars);
		if (rel == null || rel.isEmpty()) {
			return MetricTools.ZERO;
		}

		// Get the remainder of the projection attributes.
		FilterAttributeProcessor fap = getFilterAttributeProcessor(attributes);
		SummationHelper sum = new SummationHelper(getMetricsEngine(),
				attributes);

		boolean recurse = attributes.getBooleanValue("recurse", false);
		boolean nesting = attributes.getBooleanValue("nesting", false);

		for (ModelElement projElem : fap.fullIteration(rel, vars)) {
			if (fap.isValid()) {
				if (nesting) {
					if (isCompatible(element, metric, projElem)) {
						sum.raiseTo(1 + ((Number) getMetricsEngine()
								.getMetricValue(projElem, metric)).floatValue());
						continue;
					}
				}
				sum.add(projElem, vars);
			}

			// recursively add the measure for compatible type elements in the
			// projection
			if (recurse && isCompatible(element, metric, projElem)) {
				sum.add(((Number) getMetricsEngine().getMetricValue(projElem,
						metric)).floatValue());
			}
		}

		return sum.getTotal();
	}
}
