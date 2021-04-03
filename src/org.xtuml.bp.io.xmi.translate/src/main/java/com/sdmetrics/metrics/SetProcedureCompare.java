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
import com.sdmetrics.math.ExpressionParser;
import com.sdmetrics.model.MetaModelElement;
import com.sdmetrics.model.ModelElement;

/**
 * Calculates the deprecated "compare" set procedure.
 * <p>
 * Element comparisons are now just a special case of projections, using the
 * principal element in the condition expression.
 */

public class SetProcedureCompare extends SetProcedure {

	private static final ExpressionNode SELF_NODE = new ExpressionNode("_self");
	private static final ExpressionNode TERM_EQUALS_WITH_NODE = new ExpressionParser()
			.parseExpression("term=with");

	@Override
	public Collection<?> calculate(ModelElement e, Set m)
			throws SDMetricsException {
		return setCompare(e, m, false);
	}

	/**
	 * Calculate a set defined by the "compare" procedure.
	 * 
	 * @param element The model element to calculate the set for.
	 * @param set The set definition.
	 * @param quitOnFirstMatch If <code>true</code>, only the first matching
	 *        element will be returned, else all matching elements will be
	 *        returned.
	 * @return Set with the elements in the projection.
	 * @throws SDMetricsException An error occurred during calculation of the
	 *         set.
	 */
	@SuppressWarnings("unchecked")
	Collection<?> setCompare(ModelElement element, Set set,
			boolean quitOnFirstMatch) throws SDMetricsException {
		ProcedureAttributes attributes = set.getAttributes();
		Variables vars = new Variables(element);

		// evaluate term expression
		ExpressionNode term = attributes.getExpression("term");
		Object termValue = (term == null) ? element : evalExpression(element,
				term, vars);
		vars.setVariable("term", termValue);

		// get elements to compare via target or set attribute
		Collection<ModelElement> comparisonSet;
		String targetElement = attributes.getStringValue("target");
		if (targetElement == null) {
			ExpressionNode setExpression = attributes.getExpression("set");
			if (setExpression == null) {
				throw new SDMetricsException(element, set,
						"Either the 'target' or the 'set' attribute must be specified.");
			}
			comparisonSet = evalElementSetExpression(element, setExpression,
					vars);
		} else {
			MetaModelElement targetType = getMetaModel().getType(targetElement);
			if (targetType == null) {
				throw new SDMetricsException(element, set,
						"Unknown target model element type '" + targetElement
								+ "'.");
			}
			comparisonSet = getModel().getAcceptedElements(targetType);
		}

		ExpressionNode withExpression = attributes.getExpression("with");
		ExpressionNode compareExpression = attributes.getExpression("comp");
		if (withExpression == null) {
			withExpression = SELF_NODE;
		}
		if (compareExpression == null) {
			compareExpression = TERM_EQUALS_WITH_NODE;
		}

		@SuppressWarnings("rawtypes")
		Collection result = MetricTools.createHashSet(set.isMultiSet());

		boolean excludeSelf = attributes.getBooleanValue("exclude_self", true);
		FilterAttributeProcessor fap = getFilterAttributeProcessor(attributes);
		for (ModelElement elem : fap.validIteration(comparisonSet, vars)) {
			if (!excludeSelf || elem != element) {
				Object withValue = evalExpression(elem, withExpression, vars);
				vars.setVariable("with", withValue);
				if (evalBooleanExpression(element, compareExpression, vars)) {
					result.add(elem);
					if (quitOnFirstMatch) {
						return result;
					}
				}
			}
		}
		return result;
	}
}
