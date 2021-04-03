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
import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.ModelElement;

/**
 * Checks rules based on set procedures.
 * <p>
 * Every set procedure can also be used to define a rule: the set is evaluated
 * for the model element to check, and any elements it contains are reported as
 * violation.
 */
public class RuleProcedureSet extends RuleProcedure {

	@Override
	public void checkRule(ModelElement element, Rule rule)
			throws SDMetricsException {

		// Check the precondition of the rule
		ProcedureAttributes attributes = rule.getAttributes();
		ExpressionNode precondition = attributes.getExpression("precondition");
		if (precondition != null) {
			if (!evalBooleanExpression(element, precondition, null)) {
				return;
			}
		}

		// calculate the set of the rule
		Collection<?> set;
		if ("valueset".equals(rule.getProcedureName())) {
			set = evalSetExpression(element,
					attributes.getRequiredExpression("set"), null);
		} else {
			Set proxy = new Set("proxy", getMetaModel().getType(
					MetaModel.BASE_ELEMENT));
			proxy.setMultiSet(true);
			proxy.setAttributes(attributes);
			proxy.setProcedureName(rule.getProcedureName());
			set = getMetricsEngine().computeSet(element, proxy);
		}

		Iterator<?> it = MetricTools.getFlatIterator(set);
		int minCount = getMinExpressionValue(element, attributes, "mincnt",
				null);
		// report any elements in the set that occur at least "mincnt" times
		while (it.hasNext()) {
			Object setElem = it.next();
			int multiplicity = MetricTools.elementCount(set, setElem);
			if (multiplicity >= minCount) {
				Object value = null;
				if (setElem instanceof ModelElement) {
					value = getRuleValue((ModelElement) setElem, attributes,
							null);
				} else {
					value = String.valueOf(setElem);
				}

				reportViolation(element, rule, value);
			}
		}
	}
}
