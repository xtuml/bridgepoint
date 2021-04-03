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
 * Calculates a "subelements" metric procedure.
 */
public class MetricProcedureSubelements extends MetricProcedure {

	@Override
	public Number calculate(ModelElement element, Metric metric)
			throws SDMetricsException {
		ProcedureAttributes attributes = metric.getAttributes();
		FilterAttributeProcessor fap = getFilterAttributeProcessor(attributes);
		SummationHelper sum = new SummationHelper(getMetricsEngine(),
				attributes);
		gatherSubelements(element, fap, sum, new Variables(element));
		return sum.getTotal();
	}

	/*
	 * Recursively collect the elements owned by a model element that satisfy
	 * filter attributes.
	 */
	private void gatherSubelements(ModelElement element,
			FilterAttributeProcessor fap, SummationHelper sum, Variables vars)
			throws SDMetricsException {
		Collection<ModelElement> ownedElements = element.getOwnedElements();
		if (ownedElements == null) {
			return;
		}
		for (ModelElement projElem : fap.fullIteration(ownedElements, vars)) {
			if (fap.isValid()) {
				sum.add(projElem, vars);
			}

			gatherSubelements(projElem, fap, sum, vars);
		}
	}
}
