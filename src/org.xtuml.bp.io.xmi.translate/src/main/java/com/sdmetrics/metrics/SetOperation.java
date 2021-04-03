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
import com.sdmetrics.model.ModelElement;

/**
 * Base class for set operations in expressions. Set operations return sets or
 * multisets of values or model elements.
 * 
 * @since 2.3
 */
public abstract class SetOperation extends AbstractProcedure {

	/**
	 * Calculates the value for the set operations.
	 * 
	 * @param element Model element for which to calculate the operation
	 * @param node The expression node with the operands for the set operation.
	 * @param vars The variables for the calculation.
	 * 
	 * @return Result of the operation.
	 * @throws SDMetricsException Problems while calculating the operation
	 *         value.
	 */
	public abstract Collection<?> calculateValue(ModelElement element,
			ExpressionNode node, Variables vars) throws SDMetricsException;
}
