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
 * Calculates the "subelements" set procedure.
 */
public class SetProcedureSubelements extends SetProcedure {

	@Override
	public Collection<?> calculate(ModelElement element, Set set)
			throws SDMetricsException {
		ProcedureAttributes attributes = set.getAttributes();
		FilterAttributeProcessor fa = getFilterAttributeProcessor(attributes);
		SetSummationHelper summer = new SetSummationHelper(getMetricsEngine(),
				set, "set");
		Variables vars = new Variables(element);
		gatherSubelementSet(element, fa, summer, vars);
		return summer.getResultSet();
	}

	/*
	 * Recursively collect the owned elements that satisfy the filter
	 * attributes.
	 */
	private void gatherSubelementSet(ModelElement element,
			FilterAttributeProcessor fap, SetSummationHelper summer,
			Variables vars) throws SDMetricsException {

		Collection<ModelElement> ownedElements = element.getOwnedElements();
		if (ownedElements == null) {
			return;
		}
		for (ModelElement child : fap.fullIteration(ownedElements, vars)) {
			if (fap.isValid()) {
				summer.add(child, vars);
			}

			gatherSubelementSet(child, fap, summer, vars);
		}
	}
}
