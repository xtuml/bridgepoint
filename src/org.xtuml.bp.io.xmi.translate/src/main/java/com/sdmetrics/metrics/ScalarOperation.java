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
 * Base class for scalar operations in expressions. Scalar operations return
 * strings, numbers (Float or Integer), or model elements.
 * 
 * @since 2.3
 */
public abstract class ScalarOperation extends AbstractProcedure {

	/**
	 * Calculates the value for the scalar operation.
	 * 
	 * @param element Model element for which to calculate the operation
	 * @param node The expression node with the operands for the scalar
	 *        operation.
	 * @param vars The variables for the calculation.
	 * 
	 * @return Result of the operation.
	 * @throws SDMetricsException Problems while calculating the operation
	 *         value.
	 */
	public abstract Object calculateValue(ModelElement element,
			ExpressionNode node, Variables vars) throws SDMetricsException;

	/**
	 * Evaluates an expression that yields a float value.
	 * 
	 * @param element The model element to evaluate the expression for.
	 * @param node Root node of the expression to evaluate
	 * @param vars Variables for the expression evaluation
	 * @return The value of the expression
	 * @throws SDMetricsException Problems while evaluating the expression.
	 */
	protected float getFloatValue(ModelElement element, ExpressionNode node,
			Variables vars) throws SDMetricsException {
		Object value = evalExpression(element, node, vars);
		return getFloatValue(value, element);
	}

	/**
	 * Converts a number value to float.
	 * 
	 * @param value Value to convert (must be an instance of
	 *        <code>java.lang.Number</code>).
	 * @param element Model element for which to calculate the operation
	 * @return floatValue of the Number
	 * @throws SDMetricsException Value is not a number
	 */
	protected float getFloatValue(Object value, ModelElement element)
			throws SDMetricsException {
		if (value instanceof Number) {
			return ((Number) value).floatValue();
		}
		throw new SDMetricsException(element, null, "Operator '" + getName()
				+ "' requires numerical operand.");
	}
}
