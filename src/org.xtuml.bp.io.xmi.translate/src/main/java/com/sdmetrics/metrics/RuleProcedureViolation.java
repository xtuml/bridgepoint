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

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.model.ModelElement;

/**
 * Checks a "violation" rule with a simple rule condition.
 */
public class RuleProcedureViolation extends RuleProcedure {

	@Override
	public void checkRule(ModelElement element, Rule rule)
			throws SDMetricsException {

		// Evaluate the condition expression
		ProcedureAttributes attributes = rule.getAttributes();
		ExpressionNode condition = attributes
				.getRequiredExpression("condition");
		if (evalBooleanExpression(element, condition, null)) {
			// Get rule value and report violation
			Object value = getRuleValue(element, attributes, null);
			reportViolation(element, rule, value);
		}
	}
}
