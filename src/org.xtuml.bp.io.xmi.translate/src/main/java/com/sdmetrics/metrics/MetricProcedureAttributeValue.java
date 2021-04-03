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

import com.sdmetrics.model.ModelElement;

/**
 * Calculates an "attributevalue" metric procedure.
 */
public class MetricProcedureAttributeValue extends MetricProcedure {

	/**
	 * {@inheritDoc}
	 * @return Value of the specified attribute for the specified model element.
	 *         If the attribute is a cross-reference, the referenced
	 *         {@link ModelElement} object is returned.
	 * @throws SDMetricsException if required attributes are missing or have the
	 *         wrong type.
	 */
	@Override
	protected Object calculate(ModelElement element, Metric metric)
			throws SDMetricsException {
		ProcedureAttributes attributes = metric.getAttributes();
		String attrName = attributes.getRequiredStringValue("attr");
		FilterAttributeProcessor fap = getFilterAttributeProcessor(attributes);
		ModelElement filtered = fap.applyFilters(null, element, new Variables(
				element));
		if (filtered == null) {
			return "";
		}
		if (!fap.isValid()) {
			return "";
		}

		if (!filtered.getType().hasAttribute(attrName)) {
			throw new SDMetricsException(element, metric, "Unknown attribute '"
					+ attrName + "' for elements of type '"
					+ filtered.getType().getName() + "'.");
		}
		if (filtered.getType().isSetAttribute(attrName)) {
			throw new SDMetricsException(
					element,
					metric,
					"Attribute '"
							+ attrName
							+ "' is not a single-valued attribute for elements of type '"
							+ filtered.getType().getName() + "'.");
		}

		if (filtered.getType().isRefAttribute(attrName)) {
			// handle cross-reference attribute: get referenced element
			ModelElement result = filtered.getRefAttribute(attrName);
			if (result == null) {
				return ""; // no element referenced -> empty string
			}
			return result;
		}
		// plain data attribute, return its value
		return filtered.getPlainAttribute(attrName);
	}
}
