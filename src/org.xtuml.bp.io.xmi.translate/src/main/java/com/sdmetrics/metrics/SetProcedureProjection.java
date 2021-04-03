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
import java.util.Collections;
import java.util.HashSet;

import com.sdmetrics.math.HashMultiSet;
import com.sdmetrics.model.ModelElement;

/**
 * Calculates the "projection" set procedure.
 */
public class SetProcedureProjection extends SetProcedure {

	/** Empty regular set to return. */
	private static final Collection<?> EMPTYSET = Collections
			.unmodifiableSet(new HashSet<>(1));
	/** Empty multiset to return. */
	private static final Collection<?> EMPTYMULTISET = Collections
			.unmodifiableCollection(new HashMultiSet<>(1));

	@Override
	public Collection<?> calculate(ModelElement element, Set set)
			throws SDMetricsException {
		ProcedureAttributes attributes = set.getAttributes();

		// retrieve set of elements related via the specified relation
		Variables vars = new Variables(element);
		Collection<ModelElement> projection = getRelationOrSet(element,
				attributes, vars);
		if (projection == null) {
			return set.isMultiSet() ? EMPTYMULTISET : EMPTYSET;
		}

		// Get remainder of the set projection attributes
		FilterAttributeProcessor fap = getFilterAttributeProcessor(attributes);
		SetSummationHelper summer = new SetSummationHelper(getMetricsEngine(),
				set, "set");
		boolean recurse = attributes.getBooleanValue("recurse", false);

		for (ModelElement projElem : fap.fullIteration(projection, vars)) {
			if (fap.isValid()) { // element is suitable to be considered
				summer.add(projElem, vars);
			}

			// recursively add the sets for compatible elements.
			if (recurse && isCompatible(element, set, projElem)) {
				summer.add(getMetricsEngine().getSet(projElem, set));
			}
		}
		summer.excludeSelf(element);

		Collection<?> result = summer.getResultSet();
		if (!set.isMultiSet() && projection.equals(result)) {
			return projection; // reuse project set if it was not changed
		}
		return result;
	}
}
