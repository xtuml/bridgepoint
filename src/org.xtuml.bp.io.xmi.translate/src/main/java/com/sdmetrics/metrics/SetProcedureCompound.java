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
 * Calculates the deprecated "compoundset" procedure for sets.
 * <p>
 * Compound sets are now just a special case of projections with "relset"
 * attribute.
 */
public class SetProcedureCompound extends SetProcedure {

	@Override
	public Collection<?> calculate(ModelElement element, Set set)
			throws SDMetricsException {
		// Evaluate the set expression
		ProcedureAttributes attributes = set.getAttributes();
		Variables vars = new Variables(element);
		Collection<ModelElement> intermediateSet = evalElementSetExpression(
				element, attributes.getRequiredExpression("set"), vars);
		FilterAttributeProcessor fap = getFilterAttributeProcessor(attributes);
		SetSummationHelper summer = new SetSummationHelper(getMetricsEngine(),
				set, "cum");

		// Iterate over intermediate set, applying filter attributes to
		// calculate the final result set
		for (ModelElement pal : fap.validIteration(intermediateSet, vars)) {
			summer.add(pal, vars);
		}
		summer.excludeSelf(element);
		return summer.getResultSet();
	}
}
